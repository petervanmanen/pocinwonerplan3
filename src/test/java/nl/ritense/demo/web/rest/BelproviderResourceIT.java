package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BelproviderAsserts.*;
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
import nl.ritense.demo.domain.Belprovider;
import nl.ritense.demo.repository.BelproviderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BelproviderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BelproviderResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/belproviders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BelproviderRepository belproviderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBelproviderMockMvc;

    private Belprovider belprovider;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Belprovider createEntity(EntityManager em) {
        Belprovider belprovider = new Belprovider().code(DEFAULT_CODE);
        return belprovider;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Belprovider createUpdatedEntity(EntityManager em) {
        Belprovider belprovider = new Belprovider().code(UPDATED_CODE);
        return belprovider;
    }

    @BeforeEach
    public void initTest() {
        belprovider = createEntity(em);
    }

    @Test
    @Transactional
    void createBelprovider() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Belprovider
        var returnedBelprovider = om.readValue(
            restBelproviderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(belprovider)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Belprovider.class
        );

        // Validate the Belprovider in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBelproviderUpdatableFieldsEquals(returnedBelprovider, getPersistedBelprovider(returnedBelprovider));
    }

    @Test
    @Transactional
    void createBelproviderWithExistingId() throws Exception {
        // Create the Belprovider with an existing ID
        belprovider.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBelproviderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(belprovider)))
            .andExpect(status().isBadRequest());

        // Validate the Belprovider in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBelproviders() throws Exception {
        // Initialize the database
        belproviderRepository.saveAndFlush(belprovider);

        // Get all the belproviderList
        restBelproviderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(belprovider.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getBelprovider() throws Exception {
        // Initialize the database
        belproviderRepository.saveAndFlush(belprovider);

        // Get the belprovider
        restBelproviderMockMvc
            .perform(get(ENTITY_API_URL_ID, belprovider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(belprovider.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingBelprovider() throws Exception {
        // Get the belprovider
        restBelproviderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBelprovider() throws Exception {
        // Initialize the database
        belproviderRepository.saveAndFlush(belprovider);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the belprovider
        Belprovider updatedBelprovider = belproviderRepository.findById(belprovider.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBelprovider are not directly saved in db
        em.detach(updatedBelprovider);
        updatedBelprovider.code(UPDATED_CODE);

        restBelproviderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBelprovider.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBelprovider))
            )
            .andExpect(status().isOk());

        // Validate the Belprovider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBelproviderToMatchAllProperties(updatedBelprovider);
    }

    @Test
    @Transactional
    void putNonExistingBelprovider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belprovider.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBelproviderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, belprovider.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(belprovider))
            )
            .andExpect(status().isBadRequest());

        // Validate the Belprovider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBelprovider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belprovider.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBelproviderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(belprovider))
            )
            .andExpect(status().isBadRequest());

        // Validate the Belprovider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBelprovider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belprovider.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBelproviderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(belprovider)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Belprovider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBelproviderWithPatch() throws Exception {
        // Initialize the database
        belproviderRepository.saveAndFlush(belprovider);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the belprovider using partial update
        Belprovider partialUpdatedBelprovider = new Belprovider();
        partialUpdatedBelprovider.setId(belprovider.getId());

        partialUpdatedBelprovider.code(UPDATED_CODE);

        restBelproviderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBelprovider.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBelprovider))
            )
            .andExpect(status().isOk());

        // Validate the Belprovider in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBelproviderUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBelprovider, belprovider),
            getPersistedBelprovider(belprovider)
        );
    }

    @Test
    @Transactional
    void fullUpdateBelproviderWithPatch() throws Exception {
        // Initialize the database
        belproviderRepository.saveAndFlush(belprovider);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the belprovider using partial update
        Belprovider partialUpdatedBelprovider = new Belprovider();
        partialUpdatedBelprovider.setId(belprovider.getId());

        partialUpdatedBelprovider.code(UPDATED_CODE);

        restBelproviderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBelprovider.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBelprovider))
            )
            .andExpect(status().isOk());

        // Validate the Belprovider in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBelproviderUpdatableFieldsEquals(partialUpdatedBelprovider, getPersistedBelprovider(partialUpdatedBelprovider));
    }

    @Test
    @Transactional
    void patchNonExistingBelprovider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belprovider.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBelproviderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, belprovider.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(belprovider))
            )
            .andExpect(status().isBadRequest());

        // Validate the Belprovider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBelprovider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belprovider.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBelproviderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(belprovider))
            )
            .andExpect(status().isBadRequest());

        // Validate the Belprovider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBelprovider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belprovider.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBelproviderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(belprovider)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Belprovider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBelprovider() throws Exception {
        // Initialize the database
        belproviderRepository.saveAndFlush(belprovider);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the belprovider
        restBelproviderMockMvc
            .perform(delete(ENTITY_API_URL_ID, belprovider.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return belproviderRepository.count();
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

    protected Belprovider getPersistedBelprovider(Belprovider belprovider) {
        return belproviderRepository.findById(belprovider.getId()).orElseThrow();
    }

    protected void assertPersistedBelproviderToMatchAllProperties(Belprovider expectedBelprovider) {
        assertBelproviderAllPropertiesEquals(expectedBelprovider, getPersistedBelprovider(expectedBelprovider));
    }

    protected void assertPersistedBelproviderToMatchUpdatableProperties(Belprovider expectedBelprovider) {
        assertBelproviderAllUpdatablePropertiesEquals(expectedBelprovider, getPersistedBelprovider(expectedBelprovider));
    }
}
