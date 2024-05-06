package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GebiedAsserts.*;
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
import nl.ritense.demo.domain.Gebied;
import nl.ritense.demo.repository.GebiedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GebiedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GebiedResourceIT {

    private static final String DEFAULT_GEBIED = "AAAAAAAAAA";
    private static final String UPDATED_GEBIED = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gebieds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GebiedRepository gebiedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGebiedMockMvc;

    private Gebied gebied;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gebied createEntity(EntityManager em) {
        Gebied gebied = new Gebied().gebied(DEFAULT_GEBIED);
        return gebied;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gebied createUpdatedEntity(EntityManager em) {
        Gebied gebied = new Gebied().gebied(UPDATED_GEBIED);
        return gebied;
    }

    @BeforeEach
    public void initTest() {
        gebied = createEntity(em);
    }

    @Test
    @Transactional
    void createGebied() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Gebied
        var returnedGebied = om.readValue(
            restGebiedMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebied)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Gebied.class
        );

        // Validate the Gebied in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGebiedUpdatableFieldsEquals(returnedGebied, getPersistedGebied(returnedGebied));
    }

    @Test
    @Transactional
    void createGebiedWithExistingId() throws Exception {
        // Create the Gebied with an existing ID
        gebied.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGebiedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebied)))
            .andExpect(status().isBadRequest());

        // Validate the Gebied in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGebieds() throws Exception {
        // Initialize the database
        gebiedRepository.saveAndFlush(gebied);

        // Get all the gebiedList
        restGebiedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gebied.getId().intValue())))
            .andExpect(jsonPath("$.[*].gebied").value(hasItem(DEFAULT_GEBIED)));
    }

    @Test
    @Transactional
    void getGebied() throws Exception {
        // Initialize the database
        gebiedRepository.saveAndFlush(gebied);

        // Get the gebied
        restGebiedMockMvc
            .perform(get(ENTITY_API_URL_ID, gebied.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gebied.getId().intValue()))
            .andExpect(jsonPath("$.gebied").value(DEFAULT_GEBIED));
    }

    @Test
    @Transactional
    void getNonExistingGebied() throws Exception {
        // Get the gebied
        restGebiedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGebied() throws Exception {
        // Initialize the database
        gebiedRepository.saveAndFlush(gebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebied
        Gebied updatedGebied = gebiedRepository.findById(gebied.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGebied are not directly saved in db
        em.detach(updatedGebied);
        updatedGebied.gebied(UPDATED_GEBIED);

        restGebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGebied.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGebied))
            )
            .andExpect(status().isOk());

        // Validate the Gebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGebiedToMatchAllProperties(updatedGebied);
    }

    @Test
    @Transactional
    void putNonExistingGebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGebiedMockMvc
            .perform(put(ENTITY_API_URL_ID, gebied.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebied)))
            .andExpect(status().isBadRequest());

        // Validate the Gebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebiedMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebied)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGebiedWithPatch() throws Exception {
        // Initialize the database
        gebiedRepository.saveAndFlush(gebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebied using partial update
        Gebied partialUpdatedGebied = new Gebied();
        partialUpdatedGebied.setId(gebied.getId());

        restGebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGebied))
            )
            .andExpect(status().isOk());

        // Validate the Gebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGebiedUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedGebied, gebied), getPersistedGebied(gebied));
    }

    @Test
    @Transactional
    void fullUpdateGebiedWithPatch() throws Exception {
        // Initialize the database
        gebiedRepository.saveAndFlush(gebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebied using partial update
        Gebied partialUpdatedGebied = new Gebied();
        partialUpdatedGebied.setId(gebied.getId());

        partialUpdatedGebied.gebied(UPDATED_GEBIED);

        restGebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGebied))
            )
            .andExpect(status().isOk());

        // Validate the Gebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGebiedUpdatableFieldsEquals(partialUpdatedGebied, getPersistedGebied(partialUpdatedGebied));
    }

    @Test
    @Transactional
    void patchNonExistingGebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gebied.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebiedMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gebied)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGebied() throws Exception {
        // Initialize the database
        gebiedRepository.saveAndFlush(gebied);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gebied
        restGebiedMockMvc
            .perform(delete(ENTITY_API_URL_ID, gebied.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gebiedRepository.count();
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

    protected Gebied getPersistedGebied(Gebied gebied) {
        return gebiedRepository.findById(gebied.getId()).orElseThrow();
    }

    protected void assertPersistedGebiedToMatchAllProperties(Gebied expectedGebied) {
        assertGebiedAllPropertiesEquals(expectedGebied, getPersistedGebied(expectedGebied));
    }

    protected void assertPersistedGebiedToMatchUpdatableProperties(Gebied expectedGebied) {
        assertGebiedAllUpdatablePropertiesEquals(expectedGebied, getPersistedGebied(expectedGebied));
    }
}
