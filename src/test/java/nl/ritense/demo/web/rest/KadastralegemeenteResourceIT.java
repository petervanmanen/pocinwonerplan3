package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KadastralegemeenteAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Kadastralegemeente;
import nl.ritense.demo.repository.KadastralegemeenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KadastralegemeenteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KadastralegemeenteResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDKADASTRALEGEMEENTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDKADASTRALEGEMEENTE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDKADASTRALEGEMEENTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDKADASTRALEGEMEENTE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_KADASTRALEGEMEENTECODE = "AAAAAAAAAA";
    private static final String UPDATED_KADASTRALEGEMEENTECODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kadastralegemeentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KadastralegemeenteRepository kadastralegemeenteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKadastralegemeenteMockMvc;

    private Kadastralegemeente kadastralegemeente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kadastralegemeente createEntity(EntityManager em) {
        Kadastralegemeente kadastralegemeente = new Kadastralegemeente()
            .datumbegingeldigheidkadastralegemeente(DEFAULT_DATUMBEGINGELDIGHEIDKADASTRALEGEMEENTE)
            .datumeindegeldigheidkadastralegemeente(DEFAULT_DATUMEINDEGELDIGHEIDKADASTRALEGEMEENTE)
            .kadastralegemeentecode(DEFAULT_KADASTRALEGEMEENTECODE)
            .naam(DEFAULT_NAAM);
        return kadastralegemeente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kadastralegemeente createUpdatedEntity(EntityManager em) {
        Kadastralegemeente kadastralegemeente = new Kadastralegemeente()
            .datumbegingeldigheidkadastralegemeente(UPDATED_DATUMBEGINGELDIGHEIDKADASTRALEGEMEENTE)
            .datumeindegeldigheidkadastralegemeente(UPDATED_DATUMEINDEGELDIGHEIDKADASTRALEGEMEENTE)
            .kadastralegemeentecode(UPDATED_KADASTRALEGEMEENTECODE)
            .naam(UPDATED_NAAM);
        return kadastralegemeente;
    }

    @BeforeEach
    public void initTest() {
        kadastralegemeente = createEntity(em);
    }

    @Test
    @Transactional
    void createKadastralegemeente() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kadastralegemeente
        var returnedKadastralegemeente = om.readValue(
            restKadastralegemeenteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kadastralegemeente)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kadastralegemeente.class
        );

        // Validate the Kadastralegemeente in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKadastralegemeenteUpdatableFieldsEquals(
            returnedKadastralegemeente,
            getPersistedKadastralegemeente(returnedKadastralegemeente)
        );
    }

    @Test
    @Transactional
    void createKadastralegemeenteWithExistingId() throws Exception {
        // Create the Kadastralegemeente with an existing ID
        kadastralegemeente.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKadastralegemeenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kadastralegemeente)))
            .andExpect(status().isBadRequest());

        // Validate the Kadastralegemeente in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKadastralegemeentes() throws Exception {
        // Initialize the database
        kadastralegemeenteRepository.saveAndFlush(kadastralegemeente);

        // Get all the kadastralegemeenteList
        restKadastralegemeenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kadastralegemeente.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidkadastralegemeente").value(
                    hasItem(DEFAULT_DATUMBEGINGELDIGHEIDKADASTRALEGEMEENTE.toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidkadastralegemeente").value(
                    hasItem(DEFAULT_DATUMEINDEGELDIGHEIDKADASTRALEGEMEENTE.toString())
                )
            )
            .andExpect(jsonPath("$.[*].kadastralegemeentecode").value(hasItem(DEFAULT_KADASTRALEGEMEENTECODE)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getKadastralegemeente() throws Exception {
        // Initialize the database
        kadastralegemeenteRepository.saveAndFlush(kadastralegemeente);

        // Get the kadastralegemeente
        restKadastralegemeenteMockMvc
            .perform(get(ENTITY_API_URL_ID, kadastralegemeente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kadastralegemeente.getId().intValue()))
            .andExpect(
                jsonPath("$.datumbegingeldigheidkadastralegemeente").value(DEFAULT_DATUMBEGINGELDIGHEIDKADASTRALEGEMEENTE.toString())
            )
            .andExpect(
                jsonPath("$.datumeindegeldigheidkadastralegemeente").value(DEFAULT_DATUMEINDEGELDIGHEIDKADASTRALEGEMEENTE.toString())
            )
            .andExpect(jsonPath("$.kadastralegemeentecode").value(DEFAULT_KADASTRALEGEMEENTECODE))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingKadastralegemeente() throws Exception {
        // Get the kadastralegemeente
        restKadastralegemeenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKadastralegemeente() throws Exception {
        // Initialize the database
        kadastralegemeenteRepository.saveAndFlush(kadastralegemeente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kadastralegemeente
        Kadastralegemeente updatedKadastralegemeente = kadastralegemeenteRepository.findById(kadastralegemeente.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKadastralegemeente are not directly saved in db
        em.detach(updatedKadastralegemeente);
        updatedKadastralegemeente
            .datumbegingeldigheidkadastralegemeente(UPDATED_DATUMBEGINGELDIGHEIDKADASTRALEGEMEENTE)
            .datumeindegeldigheidkadastralegemeente(UPDATED_DATUMEINDEGELDIGHEIDKADASTRALEGEMEENTE)
            .kadastralegemeentecode(UPDATED_KADASTRALEGEMEENTECODE)
            .naam(UPDATED_NAAM);

        restKadastralegemeenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKadastralegemeente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKadastralegemeente))
            )
            .andExpect(status().isOk());

        // Validate the Kadastralegemeente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKadastralegemeenteToMatchAllProperties(updatedKadastralegemeente);
    }

    @Test
    @Transactional
    void putNonExistingKadastralegemeente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastralegemeente.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKadastralegemeenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kadastralegemeente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kadastralegemeente))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastralegemeente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKadastralegemeente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastralegemeente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastralegemeenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kadastralegemeente))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastralegemeente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKadastralegemeente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastralegemeente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastralegemeenteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kadastralegemeente)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kadastralegemeente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKadastralegemeenteWithPatch() throws Exception {
        // Initialize the database
        kadastralegemeenteRepository.saveAndFlush(kadastralegemeente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kadastralegemeente using partial update
        Kadastralegemeente partialUpdatedKadastralegemeente = new Kadastralegemeente();
        partialUpdatedKadastralegemeente.setId(kadastralegemeente.getId());

        partialUpdatedKadastralegemeente.datumbegingeldigheidkadastralegemeente(UPDATED_DATUMBEGINGELDIGHEIDKADASTRALEGEMEENTE);

        restKadastralegemeenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKadastralegemeente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKadastralegemeente))
            )
            .andExpect(status().isOk());

        // Validate the Kadastralegemeente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKadastralegemeenteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKadastralegemeente, kadastralegemeente),
            getPersistedKadastralegemeente(kadastralegemeente)
        );
    }

    @Test
    @Transactional
    void fullUpdateKadastralegemeenteWithPatch() throws Exception {
        // Initialize the database
        kadastralegemeenteRepository.saveAndFlush(kadastralegemeente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kadastralegemeente using partial update
        Kadastralegemeente partialUpdatedKadastralegemeente = new Kadastralegemeente();
        partialUpdatedKadastralegemeente.setId(kadastralegemeente.getId());

        partialUpdatedKadastralegemeente
            .datumbegingeldigheidkadastralegemeente(UPDATED_DATUMBEGINGELDIGHEIDKADASTRALEGEMEENTE)
            .datumeindegeldigheidkadastralegemeente(UPDATED_DATUMEINDEGELDIGHEIDKADASTRALEGEMEENTE)
            .kadastralegemeentecode(UPDATED_KADASTRALEGEMEENTECODE)
            .naam(UPDATED_NAAM);

        restKadastralegemeenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKadastralegemeente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKadastralegemeente))
            )
            .andExpect(status().isOk());

        // Validate the Kadastralegemeente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKadastralegemeenteUpdatableFieldsEquals(
            partialUpdatedKadastralegemeente,
            getPersistedKadastralegemeente(partialUpdatedKadastralegemeente)
        );
    }

    @Test
    @Transactional
    void patchNonExistingKadastralegemeente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastralegemeente.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKadastralegemeenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kadastralegemeente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kadastralegemeente))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastralegemeente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKadastralegemeente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastralegemeente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastralegemeenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kadastralegemeente))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastralegemeente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKadastralegemeente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastralegemeente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastralegemeenteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kadastralegemeente)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kadastralegemeente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKadastralegemeente() throws Exception {
        // Initialize the database
        kadastralegemeenteRepository.saveAndFlush(kadastralegemeente);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kadastralegemeente
        restKadastralegemeenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, kadastralegemeente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kadastralegemeenteRepository.count();
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

    protected Kadastralegemeente getPersistedKadastralegemeente(Kadastralegemeente kadastralegemeente) {
        return kadastralegemeenteRepository.findById(kadastralegemeente.getId()).orElseThrow();
    }

    protected void assertPersistedKadastralegemeenteToMatchAllProperties(Kadastralegemeente expectedKadastralegemeente) {
        assertKadastralegemeenteAllPropertiesEquals(expectedKadastralegemeente, getPersistedKadastralegemeente(expectedKadastralegemeente));
    }

    protected void assertPersistedKadastralegemeenteToMatchUpdatableProperties(Kadastralegemeente expectedKadastralegemeente) {
        assertKadastralegemeenteAllUpdatablePropertiesEquals(
            expectedKadastralegemeente,
            getPersistedKadastralegemeente(expectedKadastralegemeente)
        );
    }
}
