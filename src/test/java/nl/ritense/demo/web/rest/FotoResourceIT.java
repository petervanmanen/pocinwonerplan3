package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FotoAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Foto;
import nl.ritense.demo.repository.FotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FotoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FotoResourceIT {

    private static final String DEFAULT_BESTANDSGROOTTE = "AAAAAAAAAA";
    private static final String UPDATED_BESTANDSGROOTTE = "BBBBBBBBBB";

    private static final String DEFAULT_BESTANDSNAAM = "AAAAAAAAAA";
    private static final String UPDATED_BESTANDSNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_BESTANDSTYPE = "AAAAAAAAAA";
    private static final String UPDATED_BESTANDSTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMTIJD = "AAAAAAAAAA";
    private static final String UPDATED_DATUMTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIE = "BBBBBBBBBB";

    private static final String DEFAULT_PIXELSX = "AAAAAAAAAA";
    private static final String UPDATED_PIXELSX = "BBBBBBBBBB";

    private static final String DEFAULT_PIXELSY = "AAAAAAAAAA";
    private static final String UPDATED_PIXELSY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fotos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FotoRepository fotoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFotoMockMvc;

    private Foto foto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Foto createEntity(EntityManager em) {
        Foto foto = new Foto()
            .bestandsgrootte(DEFAULT_BESTANDSGROOTTE)
            .bestandsnaam(DEFAULT_BESTANDSNAAM)
            .bestandstype(DEFAULT_BESTANDSTYPE)
            .datumtijd(DEFAULT_DATUMTIJD)
            .locatie(DEFAULT_LOCATIE)
            .pixelsx(DEFAULT_PIXELSX)
            .pixelsy(DEFAULT_PIXELSY);
        return foto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Foto createUpdatedEntity(EntityManager em) {
        Foto foto = new Foto()
            .bestandsgrootte(UPDATED_BESTANDSGROOTTE)
            .bestandsnaam(UPDATED_BESTANDSNAAM)
            .bestandstype(UPDATED_BESTANDSTYPE)
            .datumtijd(UPDATED_DATUMTIJD)
            .locatie(UPDATED_LOCATIE)
            .pixelsx(UPDATED_PIXELSX)
            .pixelsy(UPDATED_PIXELSY);
        return foto;
    }

    @BeforeEach
    public void initTest() {
        foto = createEntity(em);
    }

    @Test
    @Transactional
    void createFoto() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Foto
        var returnedFoto = om.readValue(
            restFotoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(foto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Foto.class
        );

        // Validate the Foto in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFotoUpdatableFieldsEquals(returnedFoto, getPersistedFoto(returnedFoto));
    }

    @Test
    @Transactional
    void createFotoWithExistingId() throws Exception {
        // Create the Foto with an existing ID
        foto.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFotoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(foto)))
            .andExpect(status().isBadRequest());

        // Validate the Foto in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFotos() throws Exception {
        // Initialize the database
        fotoRepository.saveAndFlush(foto);

        // Get all the fotoList
        restFotoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foto.getId().intValue())))
            .andExpect(jsonPath("$.[*].bestandsgrootte").value(hasItem(DEFAULT_BESTANDSGROOTTE)))
            .andExpect(jsonPath("$.[*].bestandsnaam").value(hasItem(DEFAULT_BESTANDSNAAM)))
            .andExpect(jsonPath("$.[*].bestandstype").value(hasItem(DEFAULT_BESTANDSTYPE)))
            .andExpect(jsonPath("$.[*].datumtijd").value(hasItem(DEFAULT_DATUMTIJD)))
            .andExpect(jsonPath("$.[*].locatie").value(hasItem(DEFAULT_LOCATIE)))
            .andExpect(jsonPath("$.[*].pixelsx").value(hasItem(DEFAULT_PIXELSX)))
            .andExpect(jsonPath("$.[*].pixelsy").value(hasItem(DEFAULT_PIXELSY)));
    }

    @Test
    @Transactional
    void getFoto() throws Exception {
        // Initialize the database
        fotoRepository.saveAndFlush(foto);

        // Get the foto
        restFotoMockMvc
            .perform(get(ENTITY_API_URL_ID, foto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(foto.getId().intValue()))
            .andExpect(jsonPath("$.bestandsgrootte").value(DEFAULT_BESTANDSGROOTTE))
            .andExpect(jsonPath("$.bestandsnaam").value(DEFAULT_BESTANDSNAAM))
            .andExpect(jsonPath("$.bestandstype").value(DEFAULT_BESTANDSTYPE))
            .andExpect(jsonPath("$.datumtijd").value(DEFAULT_DATUMTIJD))
            .andExpect(jsonPath("$.locatie").value(DEFAULT_LOCATIE))
            .andExpect(jsonPath("$.pixelsx").value(DEFAULT_PIXELSX))
            .andExpect(jsonPath("$.pixelsy").value(DEFAULT_PIXELSY));
    }

    @Test
    @Transactional
    void getNonExistingFoto() throws Exception {
        // Get the foto
        restFotoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFoto() throws Exception {
        // Initialize the database
        fotoRepository.saveAndFlush(foto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the foto
        Foto updatedFoto = fotoRepository.findById(foto.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFoto are not directly saved in db
        em.detach(updatedFoto);
        updatedFoto
            .bestandsgrootte(UPDATED_BESTANDSGROOTTE)
            .bestandsnaam(UPDATED_BESTANDSNAAM)
            .bestandstype(UPDATED_BESTANDSTYPE)
            .datumtijd(UPDATED_DATUMTIJD)
            .locatie(UPDATED_LOCATIE)
            .pixelsx(UPDATED_PIXELSX)
            .pixelsy(UPDATED_PIXELSY);

        restFotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFoto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFoto))
            )
            .andExpect(status().isOk());

        // Validate the Foto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFotoToMatchAllProperties(updatedFoto);
    }

    @Test
    @Transactional
    void putNonExistingFoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        foto.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoMockMvc
            .perform(put(ENTITY_API_URL_ID, foto.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(foto)))
            .andExpect(status().isBadRequest());

        // Validate the Foto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        foto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(foto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Foto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        foto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(foto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Foto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFotoWithPatch() throws Exception {
        // Initialize the database
        fotoRepository.saveAndFlush(foto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the foto using partial update
        Foto partialUpdatedFoto = new Foto();
        partialUpdatedFoto.setId(foto.getId());

        partialUpdatedFoto.bestandsnaam(UPDATED_BESTANDSNAAM).locatie(UPDATED_LOCATIE).pixelsx(UPDATED_PIXELSX).pixelsy(UPDATED_PIXELSY);

        restFotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFoto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFoto))
            )
            .andExpect(status().isOk());

        // Validate the Foto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFotoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedFoto, foto), getPersistedFoto(foto));
    }

    @Test
    @Transactional
    void fullUpdateFotoWithPatch() throws Exception {
        // Initialize the database
        fotoRepository.saveAndFlush(foto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the foto using partial update
        Foto partialUpdatedFoto = new Foto();
        partialUpdatedFoto.setId(foto.getId());

        partialUpdatedFoto
            .bestandsgrootte(UPDATED_BESTANDSGROOTTE)
            .bestandsnaam(UPDATED_BESTANDSNAAM)
            .bestandstype(UPDATED_BESTANDSTYPE)
            .datumtijd(UPDATED_DATUMTIJD)
            .locatie(UPDATED_LOCATIE)
            .pixelsx(UPDATED_PIXELSX)
            .pixelsy(UPDATED_PIXELSY);

        restFotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFoto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFoto))
            )
            .andExpect(status().isOk());

        // Validate the Foto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFotoUpdatableFieldsEquals(partialUpdatedFoto, getPersistedFoto(partialUpdatedFoto));
    }

    @Test
    @Transactional
    void patchNonExistingFoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        foto.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoMockMvc
            .perform(patch(ENTITY_API_URL_ID, foto.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(foto)))
            .andExpect(status().isBadRequest());

        // Validate the Foto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        foto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(foto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Foto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        foto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(foto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Foto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFoto() throws Exception {
        // Initialize the database
        fotoRepository.saveAndFlush(foto);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the foto
        restFotoMockMvc
            .perform(delete(ENTITY_API_URL_ID, foto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fotoRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Foto getPersistedFoto(Foto foto) {
        return fotoRepository.findById(foto.getId()).orElseThrow();
    }

    protected void assertPersistedFotoToMatchAllProperties(Foto expectedFoto) {
        assertFotoAllPropertiesEquals(expectedFoto, getPersistedFoto(expectedFoto));
    }

    protected void assertPersistedFotoToMatchUpdatableProperties(Foto expectedFoto) {
        assertFotoAllUpdatablePropertiesEquals(expectedFoto, getPersistedFoto(expectedFoto));
    }
}
