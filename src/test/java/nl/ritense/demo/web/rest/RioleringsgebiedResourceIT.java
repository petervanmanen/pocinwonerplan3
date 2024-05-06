package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RioleringsgebiedAsserts.*;
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
import nl.ritense.demo.domain.Rioleringsgebied;
import nl.ritense.demo.repository.RioleringsgebiedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RioleringsgebiedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RioleringsgebiedResourceIT {

    private static final String DEFAULT_RIOLERINGSGEBIED = "AAAAAAAAAA";
    private static final String UPDATED_RIOLERINGSGEBIED = "BBBBBBBBBB";

    private static final String DEFAULT_ZUIVERINGSGEBIED = "AAAAAAAAAA";
    private static final String UPDATED_ZUIVERINGSGEBIED = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rioleringsgebieds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RioleringsgebiedRepository rioleringsgebiedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRioleringsgebiedMockMvc;

    private Rioleringsgebied rioleringsgebied;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rioleringsgebied createEntity(EntityManager em) {
        Rioleringsgebied rioleringsgebied = new Rioleringsgebied()
            .rioleringsgebied(DEFAULT_RIOLERINGSGEBIED)
            .zuiveringsgebied(DEFAULT_ZUIVERINGSGEBIED);
        return rioleringsgebied;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rioleringsgebied createUpdatedEntity(EntityManager em) {
        Rioleringsgebied rioleringsgebied = new Rioleringsgebied()
            .rioleringsgebied(UPDATED_RIOLERINGSGEBIED)
            .zuiveringsgebied(UPDATED_ZUIVERINGSGEBIED);
        return rioleringsgebied;
    }

    @BeforeEach
    public void initTest() {
        rioleringsgebied = createEntity(em);
    }

    @Test
    @Transactional
    void createRioleringsgebied() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Rioleringsgebied
        var returnedRioleringsgebied = om.readValue(
            restRioleringsgebiedMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rioleringsgebied)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Rioleringsgebied.class
        );

        // Validate the Rioleringsgebied in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRioleringsgebiedUpdatableFieldsEquals(returnedRioleringsgebied, getPersistedRioleringsgebied(returnedRioleringsgebied));
    }

    @Test
    @Transactional
    void createRioleringsgebiedWithExistingId() throws Exception {
        // Create the Rioleringsgebied with an existing ID
        rioleringsgebied.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRioleringsgebiedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rioleringsgebied)))
            .andExpect(status().isBadRequest());

        // Validate the Rioleringsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRioleringsgebieds() throws Exception {
        // Initialize the database
        rioleringsgebiedRepository.saveAndFlush(rioleringsgebied);

        // Get all the rioleringsgebiedList
        restRioleringsgebiedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rioleringsgebied.getId().intValue())))
            .andExpect(jsonPath("$.[*].rioleringsgebied").value(hasItem(DEFAULT_RIOLERINGSGEBIED)))
            .andExpect(jsonPath("$.[*].zuiveringsgebied").value(hasItem(DEFAULT_ZUIVERINGSGEBIED)));
    }

    @Test
    @Transactional
    void getRioleringsgebied() throws Exception {
        // Initialize the database
        rioleringsgebiedRepository.saveAndFlush(rioleringsgebied);

        // Get the rioleringsgebied
        restRioleringsgebiedMockMvc
            .perform(get(ENTITY_API_URL_ID, rioleringsgebied.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rioleringsgebied.getId().intValue()))
            .andExpect(jsonPath("$.rioleringsgebied").value(DEFAULT_RIOLERINGSGEBIED))
            .andExpect(jsonPath("$.zuiveringsgebied").value(DEFAULT_ZUIVERINGSGEBIED));
    }

    @Test
    @Transactional
    void getNonExistingRioleringsgebied() throws Exception {
        // Get the rioleringsgebied
        restRioleringsgebiedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRioleringsgebied() throws Exception {
        // Initialize the database
        rioleringsgebiedRepository.saveAndFlush(rioleringsgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rioleringsgebied
        Rioleringsgebied updatedRioleringsgebied = rioleringsgebiedRepository.findById(rioleringsgebied.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRioleringsgebied are not directly saved in db
        em.detach(updatedRioleringsgebied);
        updatedRioleringsgebied.rioleringsgebied(UPDATED_RIOLERINGSGEBIED).zuiveringsgebied(UPDATED_ZUIVERINGSGEBIED);

        restRioleringsgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRioleringsgebied.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRioleringsgebied))
            )
            .andExpect(status().isOk());

        // Validate the Rioleringsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRioleringsgebiedToMatchAllProperties(updatedRioleringsgebied);
    }

    @Test
    @Transactional
    void putNonExistingRioleringsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rioleringsgebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRioleringsgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rioleringsgebied.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rioleringsgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rioleringsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRioleringsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rioleringsgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRioleringsgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rioleringsgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rioleringsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRioleringsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rioleringsgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRioleringsgebiedMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rioleringsgebied)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rioleringsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRioleringsgebiedWithPatch() throws Exception {
        // Initialize the database
        rioleringsgebiedRepository.saveAndFlush(rioleringsgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rioleringsgebied using partial update
        Rioleringsgebied partialUpdatedRioleringsgebied = new Rioleringsgebied();
        partialUpdatedRioleringsgebied.setId(rioleringsgebied.getId());

        restRioleringsgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRioleringsgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRioleringsgebied))
            )
            .andExpect(status().isOk());

        // Validate the Rioleringsgebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRioleringsgebiedUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRioleringsgebied, rioleringsgebied),
            getPersistedRioleringsgebied(rioleringsgebied)
        );
    }

    @Test
    @Transactional
    void fullUpdateRioleringsgebiedWithPatch() throws Exception {
        // Initialize the database
        rioleringsgebiedRepository.saveAndFlush(rioleringsgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rioleringsgebied using partial update
        Rioleringsgebied partialUpdatedRioleringsgebied = new Rioleringsgebied();
        partialUpdatedRioleringsgebied.setId(rioleringsgebied.getId());

        partialUpdatedRioleringsgebied.rioleringsgebied(UPDATED_RIOLERINGSGEBIED).zuiveringsgebied(UPDATED_ZUIVERINGSGEBIED);

        restRioleringsgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRioleringsgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRioleringsgebied))
            )
            .andExpect(status().isOk());

        // Validate the Rioleringsgebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRioleringsgebiedUpdatableFieldsEquals(
            partialUpdatedRioleringsgebied,
            getPersistedRioleringsgebied(partialUpdatedRioleringsgebied)
        );
    }

    @Test
    @Transactional
    void patchNonExistingRioleringsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rioleringsgebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRioleringsgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rioleringsgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rioleringsgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rioleringsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRioleringsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rioleringsgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRioleringsgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rioleringsgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rioleringsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRioleringsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rioleringsgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRioleringsgebiedMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rioleringsgebied)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rioleringsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRioleringsgebied() throws Exception {
        // Initialize the database
        rioleringsgebiedRepository.saveAndFlush(rioleringsgebied);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rioleringsgebied
        restRioleringsgebiedMockMvc
            .perform(delete(ENTITY_API_URL_ID, rioleringsgebied.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rioleringsgebiedRepository.count();
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

    protected Rioleringsgebied getPersistedRioleringsgebied(Rioleringsgebied rioleringsgebied) {
        return rioleringsgebiedRepository.findById(rioleringsgebied.getId()).orElseThrow();
    }

    protected void assertPersistedRioleringsgebiedToMatchAllProperties(Rioleringsgebied expectedRioleringsgebied) {
        assertRioleringsgebiedAllPropertiesEquals(expectedRioleringsgebied, getPersistedRioleringsgebied(expectedRioleringsgebied));
    }

    protected void assertPersistedRioleringsgebiedToMatchUpdatableProperties(Rioleringsgebied expectedRioleringsgebied) {
        assertRioleringsgebiedAllUpdatablePropertiesEquals(
            expectedRioleringsgebied,
            getPersistedRioleringsgebied(expectedRioleringsgebied)
        );
    }
}
