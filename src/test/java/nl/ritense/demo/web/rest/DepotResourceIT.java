package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DepotAsserts.*;
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
import nl.ritense.demo.domain.Depot;
import nl.ritense.demo.repository.DepotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DepotResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DepotResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/depots";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DepotRepository depotRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepotMockMvc;

    private Depot depot;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Depot createEntity(EntityManager em) {
        Depot depot = new Depot().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return depot;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Depot createUpdatedEntity(EntityManager em) {
        Depot depot = new Depot().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return depot;
    }

    @BeforeEach
    public void initTest() {
        depot = createEntity(em);
    }

    @Test
    @Transactional
    void createDepot() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Depot
        var returnedDepot = om.readValue(
            restDepotMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(depot)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Depot.class
        );

        // Validate the Depot in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDepotUpdatableFieldsEquals(returnedDepot, getPersistedDepot(returnedDepot));
    }

    @Test
    @Transactional
    void createDepotWithExistingId() throws Exception {
        // Create the Depot with an existing ID
        depot.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(depot)))
            .andExpect(status().isBadRequest());

        // Validate the Depot in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDepots() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList
        restDepotMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depot.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getDepot() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get the depot
        restDepotMockMvc
            .perform(get(ENTITY_API_URL_ID, depot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(depot.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingDepot() throws Exception {
        // Get the depot
        restDepotMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDepot() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the depot
        Depot updatedDepot = depotRepository.findById(depot.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDepot are not directly saved in db
        em.detach(updatedDepot);
        updatedDepot.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restDepotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDepot.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDepot))
            )
            .andExpect(status().isOk());

        // Validate the Depot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDepotToMatchAllProperties(updatedDepot);
    }

    @Test
    @Transactional
    void putNonExistingDepot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        depot.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepotMockMvc
            .perform(put(ENTITY_API_URL_ID, depot.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(depot)))
            .andExpect(status().isBadRequest());

        // Validate the Depot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDepot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        depot.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(depot))
            )
            .andExpect(status().isBadRequest());

        // Validate the Depot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDepot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        depot.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepotMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(depot)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Depot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDepotWithPatch() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the depot using partial update
        Depot partialUpdatedDepot = new Depot();
        partialUpdatedDepot.setId(depot.getId());

        restDepotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepot.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDepot))
            )
            .andExpect(status().isOk());

        // Validate the Depot in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepotUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDepot, depot), getPersistedDepot(depot));
    }

    @Test
    @Transactional
    void fullUpdateDepotWithPatch() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the depot using partial update
        Depot partialUpdatedDepot = new Depot();
        partialUpdatedDepot.setId(depot.getId());

        partialUpdatedDepot.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restDepotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepot.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDepot))
            )
            .andExpect(status().isOk());

        // Validate the Depot in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepotUpdatableFieldsEquals(partialUpdatedDepot, getPersistedDepot(partialUpdatedDepot));
    }

    @Test
    @Transactional
    void patchNonExistingDepot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        depot.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, depot.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(depot))
            )
            .andExpect(status().isBadRequest());

        // Validate the Depot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDepot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        depot.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(depot))
            )
            .andExpect(status().isBadRequest());

        // Validate the Depot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDepot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        depot.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepotMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(depot)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Depot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDepot() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the depot
        restDepotMockMvc
            .perform(delete(ENTITY_API_URL_ID, depot.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return depotRepository.count();
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

    protected Depot getPersistedDepot(Depot depot) {
        return depotRepository.findById(depot.getId()).orElseThrow();
    }

    protected void assertPersistedDepotToMatchAllProperties(Depot expectedDepot) {
        assertDepotAllPropertiesEquals(expectedDepot, getPersistedDepot(expectedDepot));
    }

    protected void assertPersistedDepotToMatchUpdatableProperties(Depot expectedDepot) {
        assertDepotAllUpdatablePropertiesEquals(expectedDepot, getPersistedDepot(expectedDepot));
    }
}
