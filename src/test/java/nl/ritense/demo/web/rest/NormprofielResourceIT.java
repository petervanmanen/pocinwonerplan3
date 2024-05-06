package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NormprofielAsserts.*;
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
import nl.ritense.demo.domain.Normprofiel;
import nl.ritense.demo.repository.NormprofielRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NormprofielResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NormprofielResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_SCHAAL = "AAAAAAAAAA";
    private static final String UPDATED_SCHAAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/normprofiels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NormprofielRepository normprofielRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNormprofielMockMvc;

    private Normprofiel normprofiel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Normprofiel createEntity(EntityManager em) {
        Normprofiel normprofiel = new Normprofiel().code(DEFAULT_CODE).omschrijving(DEFAULT_OMSCHRIJVING).schaal(DEFAULT_SCHAAL);
        return normprofiel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Normprofiel createUpdatedEntity(EntityManager em) {
        Normprofiel normprofiel = new Normprofiel().code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING).schaal(UPDATED_SCHAAL);
        return normprofiel;
    }

    @BeforeEach
    public void initTest() {
        normprofiel = createEntity(em);
    }

    @Test
    @Transactional
    void createNormprofiel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Normprofiel
        var returnedNormprofiel = om.readValue(
            restNormprofielMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(normprofiel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Normprofiel.class
        );

        // Validate the Normprofiel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNormprofielUpdatableFieldsEquals(returnedNormprofiel, getPersistedNormprofiel(returnedNormprofiel));
    }

    @Test
    @Transactional
    void createNormprofielWithExistingId() throws Exception {
        // Create the Normprofiel with an existing ID
        normprofiel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNormprofielMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(normprofiel)))
            .andExpect(status().isBadRequest());

        // Validate the Normprofiel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNormprofiels() throws Exception {
        // Initialize the database
        normprofielRepository.saveAndFlush(normprofiel);

        // Get all the normprofielList
        restNormprofielMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(normprofiel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].schaal").value(hasItem(DEFAULT_SCHAAL)));
    }

    @Test
    @Transactional
    void getNormprofiel() throws Exception {
        // Initialize the database
        normprofielRepository.saveAndFlush(normprofiel);

        // Get the normprofiel
        restNormprofielMockMvc
            .perform(get(ENTITY_API_URL_ID, normprofiel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(normprofiel.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.schaal").value(DEFAULT_SCHAAL));
    }

    @Test
    @Transactional
    void getNonExistingNormprofiel() throws Exception {
        // Get the normprofiel
        restNormprofielMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNormprofiel() throws Exception {
        // Initialize the database
        normprofielRepository.saveAndFlush(normprofiel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the normprofiel
        Normprofiel updatedNormprofiel = normprofielRepository.findById(normprofiel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNormprofiel are not directly saved in db
        em.detach(updatedNormprofiel);
        updatedNormprofiel.code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING).schaal(UPDATED_SCHAAL);

        restNormprofielMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNormprofiel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNormprofiel))
            )
            .andExpect(status().isOk());

        // Validate the Normprofiel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNormprofielToMatchAllProperties(updatedNormprofiel);
    }

    @Test
    @Transactional
    void putNonExistingNormprofiel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        normprofiel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNormprofielMockMvc
            .perform(
                put(ENTITY_API_URL_ID, normprofiel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(normprofiel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Normprofiel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNormprofiel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        normprofiel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNormprofielMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(normprofiel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Normprofiel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNormprofiel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        normprofiel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNormprofielMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(normprofiel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Normprofiel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNormprofielWithPatch() throws Exception {
        // Initialize the database
        normprofielRepository.saveAndFlush(normprofiel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the normprofiel using partial update
        Normprofiel partialUpdatedNormprofiel = new Normprofiel();
        partialUpdatedNormprofiel.setId(normprofiel.getId());

        partialUpdatedNormprofiel.omschrijving(UPDATED_OMSCHRIJVING);

        restNormprofielMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNormprofiel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNormprofiel))
            )
            .andExpect(status().isOk());

        // Validate the Normprofiel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNormprofielUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNormprofiel, normprofiel),
            getPersistedNormprofiel(normprofiel)
        );
    }

    @Test
    @Transactional
    void fullUpdateNormprofielWithPatch() throws Exception {
        // Initialize the database
        normprofielRepository.saveAndFlush(normprofiel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the normprofiel using partial update
        Normprofiel partialUpdatedNormprofiel = new Normprofiel();
        partialUpdatedNormprofiel.setId(normprofiel.getId());

        partialUpdatedNormprofiel.code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING).schaal(UPDATED_SCHAAL);

        restNormprofielMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNormprofiel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNormprofiel))
            )
            .andExpect(status().isOk());

        // Validate the Normprofiel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNormprofielUpdatableFieldsEquals(partialUpdatedNormprofiel, getPersistedNormprofiel(partialUpdatedNormprofiel));
    }

    @Test
    @Transactional
    void patchNonExistingNormprofiel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        normprofiel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNormprofielMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, normprofiel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(normprofiel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Normprofiel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNormprofiel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        normprofiel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNormprofielMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(normprofiel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Normprofiel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNormprofiel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        normprofiel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNormprofielMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(normprofiel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Normprofiel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNormprofiel() throws Exception {
        // Initialize the database
        normprofielRepository.saveAndFlush(normprofiel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the normprofiel
        restNormprofielMockMvc
            .perform(delete(ENTITY_API_URL_ID, normprofiel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return normprofielRepository.count();
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

    protected Normprofiel getPersistedNormprofiel(Normprofiel normprofiel) {
        return normprofielRepository.findById(normprofiel.getId()).orElseThrow();
    }

    protected void assertPersistedNormprofielToMatchAllProperties(Normprofiel expectedNormprofiel) {
        assertNormprofielAllPropertiesEquals(expectedNormprofiel, getPersistedNormprofiel(expectedNormprofiel));
    }

    protected void assertPersistedNormprofielToMatchUpdatableProperties(Normprofiel expectedNormprofiel) {
        assertNormprofielAllUpdatablePropertiesEquals(expectedNormprofiel, getPersistedNormprofiel(expectedNormprofiel));
    }
}
