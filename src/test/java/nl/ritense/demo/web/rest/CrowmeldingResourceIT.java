package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.CrowmeldingAsserts.*;
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
import nl.ritense.demo.domain.Crowmelding;
import nl.ritense.demo.repository.CrowmeldingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CrowmeldingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CrowmeldingResourceIT {

    private static final String DEFAULT_KWALITEITSNIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEITSNIVEAU = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/crowmeldings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CrowmeldingRepository crowmeldingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCrowmeldingMockMvc;

    private Crowmelding crowmelding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Crowmelding createEntity(EntityManager em) {
        Crowmelding crowmelding = new Crowmelding().kwaliteitsniveau(DEFAULT_KWALITEITSNIVEAU);
        return crowmelding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Crowmelding createUpdatedEntity(EntityManager em) {
        Crowmelding crowmelding = new Crowmelding().kwaliteitsniveau(UPDATED_KWALITEITSNIVEAU);
        return crowmelding;
    }

    @BeforeEach
    public void initTest() {
        crowmelding = createEntity(em);
    }

    @Test
    @Transactional
    void createCrowmelding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Crowmelding
        var returnedCrowmelding = om.readValue(
            restCrowmeldingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(crowmelding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Crowmelding.class
        );

        // Validate the Crowmelding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCrowmeldingUpdatableFieldsEquals(returnedCrowmelding, getPersistedCrowmelding(returnedCrowmelding));
    }

    @Test
    @Transactional
    void createCrowmeldingWithExistingId() throws Exception {
        // Create the Crowmelding with an existing ID
        crowmelding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrowmeldingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(crowmelding)))
            .andExpect(status().isBadRequest());

        // Validate the Crowmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCrowmeldings() throws Exception {
        // Initialize the database
        crowmeldingRepository.saveAndFlush(crowmelding);

        // Get all the crowmeldingList
        restCrowmeldingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crowmelding.getId().intValue())))
            .andExpect(jsonPath("$.[*].kwaliteitsniveau").value(hasItem(DEFAULT_KWALITEITSNIVEAU)));
    }

    @Test
    @Transactional
    void getCrowmelding() throws Exception {
        // Initialize the database
        crowmeldingRepository.saveAndFlush(crowmelding);

        // Get the crowmelding
        restCrowmeldingMockMvc
            .perform(get(ENTITY_API_URL_ID, crowmelding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(crowmelding.getId().intValue()))
            .andExpect(jsonPath("$.kwaliteitsniveau").value(DEFAULT_KWALITEITSNIVEAU));
    }

    @Test
    @Transactional
    void getNonExistingCrowmelding() throws Exception {
        // Get the crowmelding
        restCrowmeldingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCrowmelding() throws Exception {
        // Initialize the database
        crowmeldingRepository.saveAndFlush(crowmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the crowmelding
        Crowmelding updatedCrowmelding = crowmeldingRepository.findById(crowmelding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCrowmelding are not directly saved in db
        em.detach(updatedCrowmelding);
        updatedCrowmelding.kwaliteitsniveau(UPDATED_KWALITEITSNIVEAU);

        restCrowmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCrowmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCrowmelding))
            )
            .andExpect(status().isOk());

        // Validate the Crowmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCrowmeldingToMatchAllProperties(updatedCrowmelding);
    }

    @Test
    @Transactional
    void putNonExistingCrowmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crowmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrowmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, crowmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(crowmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crowmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCrowmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crowmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrowmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(crowmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crowmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCrowmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crowmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrowmeldingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(crowmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Crowmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCrowmeldingWithPatch() throws Exception {
        // Initialize the database
        crowmeldingRepository.saveAndFlush(crowmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the crowmelding using partial update
        Crowmelding partialUpdatedCrowmelding = new Crowmelding();
        partialUpdatedCrowmelding.setId(crowmelding.getId());

        restCrowmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrowmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCrowmelding))
            )
            .andExpect(status().isOk());

        // Validate the Crowmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCrowmeldingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCrowmelding, crowmelding),
            getPersistedCrowmelding(crowmelding)
        );
    }

    @Test
    @Transactional
    void fullUpdateCrowmeldingWithPatch() throws Exception {
        // Initialize the database
        crowmeldingRepository.saveAndFlush(crowmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the crowmelding using partial update
        Crowmelding partialUpdatedCrowmelding = new Crowmelding();
        partialUpdatedCrowmelding.setId(crowmelding.getId());

        partialUpdatedCrowmelding.kwaliteitsniveau(UPDATED_KWALITEITSNIVEAU);

        restCrowmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrowmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCrowmelding))
            )
            .andExpect(status().isOk());

        // Validate the Crowmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCrowmeldingUpdatableFieldsEquals(partialUpdatedCrowmelding, getPersistedCrowmelding(partialUpdatedCrowmelding));
    }

    @Test
    @Transactional
    void patchNonExistingCrowmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crowmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrowmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, crowmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(crowmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crowmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCrowmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crowmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrowmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(crowmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crowmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCrowmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crowmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrowmeldingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(crowmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Crowmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCrowmelding() throws Exception {
        // Initialize the database
        crowmeldingRepository.saveAndFlush(crowmelding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the crowmelding
        restCrowmeldingMockMvc
            .perform(delete(ENTITY_API_URL_ID, crowmelding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return crowmeldingRepository.count();
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

    protected Crowmelding getPersistedCrowmelding(Crowmelding crowmelding) {
        return crowmeldingRepository.findById(crowmelding.getId()).orElseThrow();
    }

    protected void assertPersistedCrowmeldingToMatchAllProperties(Crowmelding expectedCrowmelding) {
        assertCrowmeldingAllPropertiesEquals(expectedCrowmelding, getPersistedCrowmelding(expectedCrowmelding));
    }

    protected void assertPersistedCrowmeldingToMatchUpdatableProperties(Crowmelding expectedCrowmelding) {
        assertCrowmeldingAllUpdatablePropertiesEquals(expectedCrowmelding, getPersistedCrowmelding(expectedCrowmelding));
    }
}
