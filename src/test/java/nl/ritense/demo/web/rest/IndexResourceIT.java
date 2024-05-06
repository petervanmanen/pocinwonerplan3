package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.IndexAsserts.*;
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
import nl.ritense.demo.domain.Index;
import nl.ritense.demo.repository.IndexRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IndexResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndexResourceIT {

    private static final String DEFAULT_INDEXNAAM = "AAAAAAAAAA";
    private static final String UPDATED_INDEXNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_INDEXWAARDE = "AAAAAAAAAA";
    private static final String UPDATED_INDEXWAARDE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/indices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IndexRepository indexRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndexMockMvc;

    private Index index;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Index createEntity(EntityManager em) {
        Index index = new Index().indexnaam(DEFAULT_INDEXNAAM).indexwaarde(DEFAULT_INDEXWAARDE);
        return index;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Index createUpdatedEntity(EntityManager em) {
        Index index = new Index().indexnaam(UPDATED_INDEXNAAM).indexwaarde(UPDATED_INDEXWAARDE);
        return index;
    }

    @BeforeEach
    public void initTest() {
        index = createEntity(em);
    }

    @Test
    @Transactional
    void createIndex() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Index
        var returnedIndex = om.readValue(
            restIndexMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(index)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Index.class
        );

        // Validate the Index in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertIndexUpdatableFieldsEquals(returnedIndex, getPersistedIndex(returnedIndex));
    }

    @Test
    @Transactional
    void createIndexWithExistingId() throws Exception {
        // Create the Index with an existing ID
        index.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndexMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(index)))
            .andExpect(status().isBadRequest());

        // Validate the Index in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIndices() throws Exception {
        // Initialize the database
        indexRepository.saveAndFlush(index);

        // Get all the indexList
        restIndexMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(index.getId().intValue())))
            .andExpect(jsonPath("$.[*].indexnaam").value(hasItem(DEFAULT_INDEXNAAM)))
            .andExpect(jsonPath("$.[*].indexwaarde").value(hasItem(DEFAULT_INDEXWAARDE)));
    }

    @Test
    @Transactional
    void getIndex() throws Exception {
        // Initialize the database
        indexRepository.saveAndFlush(index);

        // Get the index
        restIndexMockMvc
            .perform(get(ENTITY_API_URL_ID, index.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(index.getId().intValue()))
            .andExpect(jsonPath("$.indexnaam").value(DEFAULT_INDEXNAAM))
            .andExpect(jsonPath("$.indexwaarde").value(DEFAULT_INDEXWAARDE));
    }

    @Test
    @Transactional
    void getNonExistingIndex() throws Exception {
        // Get the index
        restIndexMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIndex() throws Exception {
        // Initialize the database
        indexRepository.saveAndFlush(index);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the index
        Index updatedIndex = indexRepository.findById(index.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIndex are not directly saved in db
        em.detach(updatedIndex);
        updatedIndex.indexnaam(UPDATED_INDEXNAAM).indexwaarde(UPDATED_INDEXWAARDE);

        restIndexMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIndex.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedIndex))
            )
            .andExpect(status().isOk());

        // Validate the Index in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIndexToMatchAllProperties(updatedIndex);
    }

    @Test
    @Transactional
    void putNonExistingIndex() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        index.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndexMockMvc
            .perform(put(ENTITY_API_URL_ID, index.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(index)))
            .andExpect(status().isBadRequest());

        // Validate the Index in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndex() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        index.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndexMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(index))
            )
            .andExpect(status().isBadRequest());

        // Validate the Index in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndex() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        index.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndexMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(index)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Index in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndexWithPatch() throws Exception {
        // Initialize the database
        indexRepository.saveAndFlush(index);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the index using partial update
        Index partialUpdatedIndex = new Index();
        partialUpdatedIndex.setId(index.getId());

        restIndexMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndex.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIndex))
            )
            .andExpect(status().isOk());

        // Validate the Index in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIndexUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedIndex, index), getPersistedIndex(index));
    }

    @Test
    @Transactional
    void fullUpdateIndexWithPatch() throws Exception {
        // Initialize the database
        indexRepository.saveAndFlush(index);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the index using partial update
        Index partialUpdatedIndex = new Index();
        partialUpdatedIndex.setId(index.getId());

        partialUpdatedIndex.indexnaam(UPDATED_INDEXNAAM).indexwaarde(UPDATED_INDEXWAARDE);

        restIndexMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndex.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIndex))
            )
            .andExpect(status().isOk());

        // Validate the Index in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIndexUpdatableFieldsEquals(partialUpdatedIndex, getPersistedIndex(partialUpdatedIndex));
    }

    @Test
    @Transactional
    void patchNonExistingIndex() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        index.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndexMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, index.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(index))
            )
            .andExpect(status().isBadRequest());

        // Validate the Index in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndex() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        index.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndexMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(index))
            )
            .andExpect(status().isBadRequest());

        // Validate the Index in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndex() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        index.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndexMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(index)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Index in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndex() throws Exception {
        // Initialize the database
        indexRepository.saveAndFlush(index);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the index
        restIndexMockMvc
            .perform(delete(ENTITY_API_URL_ID, index.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return indexRepository.count();
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

    protected Index getPersistedIndex(Index index) {
        return indexRepository.findById(index.getId()).orElseThrow();
    }

    protected void assertPersistedIndexToMatchAllProperties(Index expectedIndex) {
        assertIndexAllPropertiesEquals(expectedIndex, getPersistedIndex(expectedIndex));
    }

    protected void assertPersistedIndexToMatchUpdatableProperties(Index expectedIndex) {
        assertIndexAllUpdatablePropertiesEquals(expectedIndex, getPersistedIndex(expectedIndex));
    }
}
