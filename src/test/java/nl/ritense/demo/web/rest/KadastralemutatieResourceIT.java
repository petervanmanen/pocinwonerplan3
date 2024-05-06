package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KadastralemutatieAsserts.*;
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
import nl.ritense.demo.domain.Kadastralemutatie;
import nl.ritense.demo.repository.KadastralemutatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KadastralemutatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KadastralemutatieResourceIT {

    private static final String ENTITY_API_URL = "/api/kadastralemutaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KadastralemutatieRepository kadastralemutatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKadastralemutatieMockMvc;

    private Kadastralemutatie kadastralemutatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kadastralemutatie createEntity(EntityManager em) {
        Kadastralemutatie kadastralemutatie = new Kadastralemutatie();
        return kadastralemutatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kadastralemutatie createUpdatedEntity(EntityManager em) {
        Kadastralemutatie kadastralemutatie = new Kadastralemutatie();
        return kadastralemutatie;
    }

    @BeforeEach
    public void initTest() {
        kadastralemutatie = createEntity(em);
    }

    @Test
    @Transactional
    void createKadastralemutatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kadastralemutatie
        var returnedKadastralemutatie = om.readValue(
            restKadastralemutatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kadastralemutatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kadastralemutatie.class
        );

        // Validate the Kadastralemutatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKadastralemutatieUpdatableFieldsEquals(returnedKadastralemutatie, getPersistedKadastralemutatie(returnedKadastralemutatie));
    }

    @Test
    @Transactional
    void createKadastralemutatieWithExistingId() throws Exception {
        // Create the Kadastralemutatie with an existing ID
        kadastralemutatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKadastralemutatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kadastralemutatie)))
            .andExpect(status().isBadRequest());

        // Validate the Kadastralemutatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKadastralemutaties() throws Exception {
        // Initialize the database
        kadastralemutatieRepository.saveAndFlush(kadastralemutatie);

        // Get all the kadastralemutatieList
        restKadastralemutatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kadastralemutatie.getId().intValue())));
    }

    @Test
    @Transactional
    void getKadastralemutatie() throws Exception {
        // Initialize the database
        kadastralemutatieRepository.saveAndFlush(kadastralemutatie);

        // Get the kadastralemutatie
        restKadastralemutatieMockMvc
            .perform(get(ENTITY_API_URL_ID, kadastralemutatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kadastralemutatie.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingKadastralemutatie() throws Exception {
        // Get the kadastralemutatie
        restKadastralemutatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKadastralemutatie() throws Exception {
        // Initialize the database
        kadastralemutatieRepository.saveAndFlush(kadastralemutatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kadastralemutatie
        Kadastralemutatie updatedKadastralemutatie = kadastralemutatieRepository.findById(kadastralemutatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKadastralemutatie are not directly saved in db
        em.detach(updatedKadastralemutatie);

        restKadastralemutatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKadastralemutatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKadastralemutatie))
            )
            .andExpect(status().isOk());

        // Validate the Kadastralemutatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKadastralemutatieToMatchAllProperties(updatedKadastralemutatie);
    }

    @Test
    @Transactional
    void putNonExistingKadastralemutatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastralemutatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKadastralemutatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kadastralemutatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kadastralemutatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastralemutatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKadastralemutatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastralemutatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastralemutatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kadastralemutatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastralemutatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKadastralemutatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastralemutatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastralemutatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kadastralemutatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kadastralemutatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKadastralemutatieWithPatch() throws Exception {
        // Initialize the database
        kadastralemutatieRepository.saveAndFlush(kadastralemutatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kadastralemutatie using partial update
        Kadastralemutatie partialUpdatedKadastralemutatie = new Kadastralemutatie();
        partialUpdatedKadastralemutatie.setId(kadastralemutatie.getId());

        restKadastralemutatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKadastralemutatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKadastralemutatie))
            )
            .andExpect(status().isOk());

        // Validate the Kadastralemutatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKadastralemutatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKadastralemutatie, kadastralemutatie),
            getPersistedKadastralemutatie(kadastralemutatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateKadastralemutatieWithPatch() throws Exception {
        // Initialize the database
        kadastralemutatieRepository.saveAndFlush(kadastralemutatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kadastralemutatie using partial update
        Kadastralemutatie partialUpdatedKadastralemutatie = new Kadastralemutatie();
        partialUpdatedKadastralemutatie.setId(kadastralemutatie.getId());

        restKadastralemutatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKadastralemutatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKadastralemutatie))
            )
            .andExpect(status().isOk());

        // Validate the Kadastralemutatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKadastralemutatieUpdatableFieldsEquals(
            partialUpdatedKadastralemutatie,
            getPersistedKadastralemutatie(partialUpdatedKadastralemutatie)
        );
    }

    @Test
    @Transactional
    void patchNonExistingKadastralemutatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastralemutatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKadastralemutatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kadastralemutatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kadastralemutatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastralemutatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKadastralemutatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastralemutatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastralemutatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kadastralemutatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastralemutatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKadastralemutatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastralemutatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastralemutatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kadastralemutatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kadastralemutatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKadastralemutatie() throws Exception {
        // Initialize the database
        kadastralemutatieRepository.saveAndFlush(kadastralemutatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kadastralemutatie
        restKadastralemutatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, kadastralemutatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kadastralemutatieRepository.count();
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

    protected Kadastralemutatie getPersistedKadastralemutatie(Kadastralemutatie kadastralemutatie) {
        return kadastralemutatieRepository.findById(kadastralemutatie.getId()).orElseThrow();
    }

    protected void assertPersistedKadastralemutatieToMatchAllProperties(Kadastralemutatie expectedKadastralemutatie) {
        assertKadastralemutatieAllPropertiesEquals(expectedKadastralemutatie, getPersistedKadastralemutatie(expectedKadastralemutatie));
    }

    protected void assertPersistedKadastralemutatieToMatchUpdatableProperties(Kadastralemutatie expectedKadastralemutatie) {
        assertKadastralemutatieAllUpdatablePropertiesEquals(
            expectedKadastralemutatie,
            getPersistedKadastralemutatie(expectedKadastralemutatie)
        );
    }
}
