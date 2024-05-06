package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SociaalteamdossierAsserts.*;
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
import nl.ritense.demo.domain.Sociaalteamdossier;
import nl.ritense.demo.repository.SociaalteamdossierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SociaalteamdossierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SociaalteamdossierResourceIT {

    private static final String DEFAULT_DATUMEINDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMSTART = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSTART = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMVASTSTELLING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMVASTSTELLING = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sociaalteamdossiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SociaalteamdossierRepository sociaalteamdossierRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSociaalteamdossierMockMvc;

    private Sociaalteamdossier sociaalteamdossier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sociaalteamdossier createEntity(EntityManager em) {
        Sociaalteamdossier sociaalteamdossier = new Sociaalteamdossier()
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .datumvaststelling(DEFAULT_DATUMVASTSTELLING)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .status(DEFAULT_STATUS);
        return sociaalteamdossier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sociaalteamdossier createUpdatedEntity(EntityManager em) {
        Sociaalteamdossier sociaalteamdossier = new Sociaalteamdossier()
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumvaststelling(UPDATED_DATUMVASTSTELLING)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .status(UPDATED_STATUS);
        return sociaalteamdossier;
    }

    @BeforeEach
    public void initTest() {
        sociaalteamdossier = createEntity(em);
    }

    @Test
    @Transactional
    void createSociaalteamdossier() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sociaalteamdossier
        var returnedSociaalteamdossier = om.readValue(
            restSociaalteamdossierMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sociaalteamdossier)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Sociaalteamdossier.class
        );

        // Validate the Sociaalteamdossier in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSociaalteamdossierUpdatableFieldsEquals(
            returnedSociaalteamdossier,
            getPersistedSociaalteamdossier(returnedSociaalteamdossier)
        );
    }

    @Test
    @Transactional
    void createSociaalteamdossierWithExistingId() throws Exception {
        // Create the Sociaalteamdossier with an existing ID
        sociaalteamdossier.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSociaalteamdossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sociaalteamdossier)))
            .andExpect(status().isBadRequest());

        // Validate the Sociaalteamdossier in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSociaalteamdossiers() throws Exception {
        // Initialize the database
        sociaalteamdossierRepository.saveAndFlush(sociaalteamdossier);

        // Get all the sociaalteamdossierList
        restSociaalteamdossierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sociaalteamdossier.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE)))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART)))
            .andExpect(jsonPath("$.[*].datumvaststelling").value(hasItem(DEFAULT_DATUMVASTSTELLING)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getSociaalteamdossier() throws Exception {
        // Initialize the database
        sociaalteamdossierRepository.saveAndFlush(sociaalteamdossier);

        // Get the sociaalteamdossier
        restSociaalteamdossierMockMvc
            .perform(get(ENTITY_API_URL_ID, sociaalteamdossier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sociaalteamdossier.getId().intValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART))
            .andExpect(jsonPath("$.datumvaststelling").value(DEFAULT_DATUMVASTSTELLING))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingSociaalteamdossier() throws Exception {
        // Get the sociaalteamdossier
        restSociaalteamdossierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSociaalteamdossier() throws Exception {
        // Initialize the database
        sociaalteamdossierRepository.saveAndFlush(sociaalteamdossier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sociaalteamdossier
        Sociaalteamdossier updatedSociaalteamdossier = sociaalteamdossierRepository.findById(sociaalteamdossier.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSociaalteamdossier are not directly saved in db
        em.detach(updatedSociaalteamdossier);
        updatedSociaalteamdossier
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumvaststelling(UPDATED_DATUMVASTSTELLING)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .status(UPDATED_STATUS);

        restSociaalteamdossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSociaalteamdossier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSociaalteamdossier))
            )
            .andExpect(status().isOk());

        // Validate the Sociaalteamdossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSociaalteamdossierToMatchAllProperties(updatedSociaalteamdossier);
    }

    @Test
    @Transactional
    void putNonExistingSociaalteamdossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sociaalteamdossier.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSociaalteamdossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sociaalteamdossier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sociaalteamdossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sociaalteamdossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSociaalteamdossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sociaalteamdossier.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSociaalteamdossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sociaalteamdossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sociaalteamdossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSociaalteamdossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sociaalteamdossier.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSociaalteamdossierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sociaalteamdossier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sociaalteamdossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSociaalteamdossierWithPatch() throws Exception {
        // Initialize the database
        sociaalteamdossierRepository.saveAndFlush(sociaalteamdossier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sociaalteamdossier using partial update
        Sociaalteamdossier partialUpdatedSociaalteamdossier = new Sociaalteamdossier();
        partialUpdatedSociaalteamdossier.setId(sociaalteamdossier.getId());

        partialUpdatedSociaalteamdossier.datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART).omschrijving(UPDATED_OMSCHRIJVING);

        restSociaalteamdossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSociaalteamdossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSociaalteamdossier))
            )
            .andExpect(status().isOk());

        // Validate the Sociaalteamdossier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSociaalteamdossierUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSociaalteamdossier, sociaalteamdossier),
            getPersistedSociaalteamdossier(sociaalteamdossier)
        );
    }

    @Test
    @Transactional
    void fullUpdateSociaalteamdossierWithPatch() throws Exception {
        // Initialize the database
        sociaalteamdossierRepository.saveAndFlush(sociaalteamdossier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sociaalteamdossier using partial update
        Sociaalteamdossier partialUpdatedSociaalteamdossier = new Sociaalteamdossier();
        partialUpdatedSociaalteamdossier.setId(sociaalteamdossier.getId());

        partialUpdatedSociaalteamdossier
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumvaststelling(UPDATED_DATUMVASTSTELLING)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .status(UPDATED_STATUS);

        restSociaalteamdossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSociaalteamdossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSociaalteamdossier))
            )
            .andExpect(status().isOk());

        // Validate the Sociaalteamdossier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSociaalteamdossierUpdatableFieldsEquals(
            partialUpdatedSociaalteamdossier,
            getPersistedSociaalteamdossier(partialUpdatedSociaalteamdossier)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSociaalteamdossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sociaalteamdossier.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSociaalteamdossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sociaalteamdossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sociaalteamdossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sociaalteamdossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSociaalteamdossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sociaalteamdossier.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSociaalteamdossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sociaalteamdossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sociaalteamdossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSociaalteamdossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sociaalteamdossier.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSociaalteamdossierMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sociaalteamdossier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sociaalteamdossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSociaalteamdossier() throws Exception {
        // Initialize the database
        sociaalteamdossierRepository.saveAndFlush(sociaalteamdossier);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sociaalteamdossier
        restSociaalteamdossierMockMvc
            .perform(delete(ENTITY_API_URL_ID, sociaalteamdossier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sociaalteamdossierRepository.count();
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

    protected Sociaalteamdossier getPersistedSociaalteamdossier(Sociaalteamdossier sociaalteamdossier) {
        return sociaalteamdossierRepository.findById(sociaalteamdossier.getId()).orElseThrow();
    }

    protected void assertPersistedSociaalteamdossierToMatchAllProperties(Sociaalteamdossier expectedSociaalteamdossier) {
        assertSociaalteamdossierAllPropertiesEquals(expectedSociaalteamdossier, getPersistedSociaalteamdossier(expectedSociaalteamdossier));
    }

    protected void assertPersistedSociaalteamdossierToMatchUpdatableProperties(Sociaalteamdossier expectedSociaalteamdossier) {
        assertSociaalteamdossierAllUpdatablePropertiesEquals(
            expectedSociaalteamdossier,
            getPersistedSociaalteamdossier(expectedSociaalteamdossier)
        );
    }
}
