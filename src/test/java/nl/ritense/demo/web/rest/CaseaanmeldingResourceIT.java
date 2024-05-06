package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.CaseaanmeldingAsserts.*;
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
import nl.ritense.demo.domain.Caseaanmelding;
import nl.ritense.demo.repository.CaseaanmeldingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CaseaanmeldingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CaseaanmeldingResourceIT {

    private static final String DEFAULT_DATUM = "AAAAAAAAAA";
    private static final String UPDATED_DATUM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/caseaanmeldings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CaseaanmeldingRepository caseaanmeldingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCaseaanmeldingMockMvc;

    private Caseaanmelding caseaanmelding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caseaanmelding createEntity(EntityManager em) {
        Caseaanmelding caseaanmelding = new Caseaanmelding().datum(DEFAULT_DATUM);
        return caseaanmelding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caseaanmelding createUpdatedEntity(EntityManager em) {
        Caseaanmelding caseaanmelding = new Caseaanmelding().datum(UPDATED_DATUM);
        return caseaanmelding;
    }

    @BeforeEach
    public void initTest() {
        caseaanmelding = createEntity(em);
    }

    @Test
    @Transactional
    void createCaseaanmelding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Caseaanmelding
        var returnedCaseaanmelding = om.readValue(
            restCaseaanmeldingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(caseaanmelding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Caseaanmelding.class
        );

        // Validate the Caseaanmelding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCaseaanmeldingUpdatableFieldsEquals(returnedCaseaanmelding, getPersistedCaseaanmelding(returnedCaseaanmelding));
    }

    @Test
    @Transactional
    void createCaseaanmeldingWithExistingId() throws Exception {
        // Create the Caseaanmelding with an existing ID
        caseaanmelding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaseaanmeldingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(caseaanmelding)))
            .andExpect(status().isBadRequest());

        // Validate the Caseaanmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCaseaanmeldings() throws Exception {
        // Initialize the database
        caseaanmeldingRepository.saveAndFlush(caseaanmelding);

        // Get all the caseaanmeldingList
        restCaseaanmeldingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caseaanmelding.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM)));
    }

    @Test
    @Transactional
    void getCaseaanmelding() throws Exception {
        // Initialize the database
        caseaanmeldingRepository.saveAndFlush(caseaanmelding);

        // Get the caseaanmelding
        restCaseaanmeldingMockMvc
            .perform(get(ENTITY_API_URL_ID, caseaanmelding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(caseaanmelding.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM));
    }

    @Test
    @Transactional
    void getNonExistingCaseaanmelding() throws Exception {
        // Get the caseaanmelding
        restCaseaanmeldingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCaseaanmelding() throws Exception {
        // Initialize the database
        caseaanmeldingRepository.saveAndFlush(caseaanmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the caseaanmelding
        Caseaanmelding updatedCaseaanmelding = caseaanmeldingRepository.findById(caseaanmelding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCaseaanmelding are not directly saved in db
        em.detach(updatedCaseaanmelding);
        updatedCaseaanmelding.datum(UPDATED_DATUM);

        restCaseaanmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCaseaanmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCaseaanmelding))
            )
            .andExpect(status().isOk());

        // Validate the Caseaanmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCaseaanmeldingToMatchAllProperties(updatedCaseaanmelding);
    }

    @Test
    @Transactional
    void putNonExistingCaseaanmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caseaanmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaseaanmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, caseaanmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(caseaanmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caseaanmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCaseaanmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caseaanmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaseaanmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(caseaanmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caseaanmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCaseaanmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caseaanmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaseaanmeldingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(caseaanmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Caseaanmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCaseaanmeldingWithPatch() throws Exception {
        // Initialize the database
        caseaanmeldingRepository.saveAndFlush(caseaanmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the caseaanmelding using partial update
        Caseaanmelding partialUpdatedCaseaanmelding = new Caseaanmelding();
        partialUpdatedCaseaanmelding.setId(caseaanmelding.getId());

        restCaseaanmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaseaanmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCaseaanmelding))
            )
            .andExpect(status().isOk());

        // Validate the Caseaanmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCaseaanmeldingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCaseaanmelding, caseaanmelding),
            getPersistedCaseaanmelding(caseaanmelding)
        );
    }

    @Test
    @Transactional
    void fullUpdateCaseaanmeldingWithPatch() throws Exception {
        // Initialize the database
        caseaanmeldingRepository.saveAndFlush(caseaanmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the caseaanmelding using partial update
        Caseaanmelding partialUpdatedCaseaanmelding = new Caseaanmelding();
        partialUpdatedCaseaanmelding.setId(caseaanmelding.getId());

        partialUpdatedCaseaanmelding.datum(UPDATED_DATUM);

        restCaseaanmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaseaanmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCaseaanmelding))
            )
            .andExpect(status().isOk());

        // Validate the Caseaanmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCaseaanmeldingUpdatableFieldsEquals(partialUpdatedCaseaanmelding, getPersistedCaseaanmelding(partialUpdatedCaseaanmelding));
    }

    @Test
    @Transactional
    void patchNonExistingCaseaanmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caseaanmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaseaanmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, caseaanmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(caseaanmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caseaanmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCaseaanmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caseaanmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaseaanmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(caseaanmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caseaanmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCaseaanmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caseaanmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaseaanmeldingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(caseaanmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Caseaanmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCaseaanmelding() throws Exception {
        // Initialize the database
        caseaanmeldingRepository.saveAndFlush(caseaanmelding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the caseaanmelding
        restCaseaanmeldingMockMvc
            .perform(delete(ENTITY_API_URL_ID, caseaanmelding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return caseaanmeldingRepository.count();
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

    protected Caseaanmelding getPersistedCaseaanmelding(Caseaanmelding caseaanmelding) {
        return caseaanmeldingRepository.findById(caseaanmelding.getId()).orElseThrow();
    }

    protected void assertPersistedCaseaanmeldingToMatchAllProperties(Caseaanmelding expectedCaseaanmelding) {
        assertCaseaanmeldingAllPropertiesEquals(expectedCaseaanmelding, getPersistedCaseaanmelding(expectedCaseaanmelding));
    }

    protected void assertPersistedCaseaanmeldingToMatchUpdatableProperties(Caseaanmelding expectedCaseaanmelding) {
        assertCaseaanmeldingAllUpdatablePropertiesEquals(expectedCaseaanmelding, getPersistedCaseaanmelding(expectedCaseaanmelding));
    }
}
