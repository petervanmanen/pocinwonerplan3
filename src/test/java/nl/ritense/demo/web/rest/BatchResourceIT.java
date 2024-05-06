package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BatchAsserts.*;
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
import nl.ritense.demo.domain.Batch;
import nl.ritense.demo.repository.BatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BatchResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BatchResourceIT {

    private static final String DEFAULT_DATUM = "AAAAAAAAAA";
    private static final String UPDATED_DATUM = "BBBBBBBBBB";

    private static final String DEFAULT_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_TIJD = "AAAAAAAAAA";
    private static final String UPDATED_TIJD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/batches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBatchMockMvc;

    private Batch batch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Batch createEntity(EntityManager em) {
        Batch batch = new Batch().datum(DEFAULT_DATUM).nummer(DEFAULT_NUMMER).tijd(DEFAULT_TIJD);
        return batch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Batch createUpdatedEntity(EntityManager em) {
        Batch batch = new Batch().datum(UPDATED_DATUM).nummer(UPDATED_NUMMER).tijd(UPDATED_TIJD);
        return batch;
    }

    @BeforeEach
    public void initTest() {
        batch = createEntity(em);
    }

    @Test
    @Transactional
    void createBatch() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Batch
        var returnedBatch = om.readValue(
            restBatchMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batch)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Batch.class
        );

        // Validate the Batch in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBatchUpdatableFieldsEquals(returnedBatch, getPersistedBatch(returnedBatch));
    }

    @Test
    @Transactional
    void createBatchWithExistingId() throws Exception {
        // Create the Batch with an existing ID
        batch.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBatchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batch)))
            .andExpect(status().isBadRequest());

        // Validate the Batch in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBatches() throws Exception {
        // Initialize the database
        batchRepository.saveAndFlush(batch);

        // Get all the batchList
        restBatchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(batch.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM)))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].tijd").value(hasItem(DEFAULT_TIJD)));
    }

    @Test
    @Transactional
    void getBatch() throws Exception {
        // Initialize the database
        batchRepository.saveAndFlush(batch);

        // Get the batch
        restBatchMockMvc
            .perform(get(ENTITY_API_URL_ID, batch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(batch.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.tijd").value(DEFAULT_TIJD));
    }

    @Test
    @Transactional
    void getNonExistingBatch() throws Exception {
        // Get the batch
        restBatchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBatch() throws Exception {
        // Initialize the database
        batchRepository.saveAndFlush(batch);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the batch
        Batch updatedBatch = batchRepository.findById(batch.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBatch are not directly saved in db
        em.detach(updatedBatch);
        updatedBatch.datum(UPDATED_DATUM).nummer(UPDATED_NUMMER).tijd(UPDATED_TIJD);

        restBatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBatch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBatch))
            )
            .andExpect(status().isOk());

        // Validate the Batch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBatchToMatchAllProperties(updatedBatch);
    }

    @Test
    @Transactional
    void putNonExistingBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batch.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBatchMockMvc
            .perform(put(ENTITY_API_URL_ID, batch.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batch)))
            .andExpect(status().isBadRequest());

        // Validate the Batch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batch.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(batch))
            )
            .andExpect(status().isBadRequest());

        // Validate the Batch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batch.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batch)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Batch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBatchWithPatch() throws Exception {
        // Initialize the database
        batchRepository.saveAndFlush(batch);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the batch using partial update
        Batch partialUpdatedBatch = new Batch();
        partialUpdatedBatch.setId(batch.getId());

        partialUpdatedBatch.tijd(UPDATED_TIJD);

        restBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBatch))
            )
            .andExpect(status().isOk());

        // Validate the Batch in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBatchUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBatch, batch), getPersistedBatch(batch));
    }

    @Test
    @Transactional
    void fullUpdateBatchWithPatch() throws Exception {
        // Initialize the database
        batchRepository.saveAndFlush(batch);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the batch using partial update
        Batch partialUpdatedBatch = new Batch();
        partialUpdatedBatch.setId(batch.getId());

        partialUpdatedBatch.datum(UPDATED_DATUM).nummer(UPDATED_NUMMER).tijd(UPDATED_TIJD);

        restBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBatch))
            )
            .andExpect(status().isOk());

        // Validate the Batch in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBatchUpdatableFieldsEquals(partialUpdatedBatch, getPersistedBatch(partialUpdatedBatch));
    }

    @Test
    @Transactional
    void patchNonExistingBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batch.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, batch.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(batch))
            )
            .andExpect(status().isBadRequest());

        // Validate the Batch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batch.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(batch))
            )
            .andExpect(status().isBadRequest());

        // Validate the Batch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batch.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(batch)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Batch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBatch() throws Exception {
        // Initialize the database
        batchRepository.saveAndFlush(batch);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the batch
        restBatchMockMvc
            .perform(delete(ENTITY_API_URL_ID, batch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return batchRepository.count();
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

    protected Batch getPersistedBatch(Batch batch) {
        return batchRepository.findById(batch.getId()).orElseThrow();
    }

    protected void assertPersistedBatchToMatchAllProperties(Batch expectedBatch) {
        assertBatchAllPropertiesEquals(expectedBatch, getPersistedBatch(expectedBatch));
    }

    protected void assertPersistedBatchToMatchUpdatableProperties(Batch expectedBatch) {
        assertBatchAllUpdatablePropertiesEquals(expectedBatch, getPersistedBatch(expectedBatch));
    }
}
