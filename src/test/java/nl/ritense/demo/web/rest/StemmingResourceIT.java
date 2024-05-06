package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.StemmingAsserts.*;
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
import nl.ritense.demo.domain.Stemming;
import nl.ritense.demo.repository.StemmingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StemmingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StemmingResourceIT {

    private static final String DEFAULT_RESULTAAT = "AAAAAAAAAA";
    private static final String UPDATED_RESULTAAT = "BBBBBBBBBB";

    private static final String DEFAULT_STEMMINGSTYPE = "AAAAAAAAAA";
    private static final String UPDATED_STEMMINGSTYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/stemmings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StemmingRepository stemmingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStemmingMockMvc;

    private Stemming stemming;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stemming createEntity(EntityManager em) {
        Stemming stemming = new Stemming().resultaat(DEFAULT_RESULTAAT).stemmingstype(DEFAULT_STEMMINGSTYPE);
        return stemming;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stemming createUpdatedEntity(EntityManager em) {
        Stemming stemming = new Stemming().resultaat(UPDATED_RESULTAAT).stemmingstype(UPDATED_STEMMINGSTYPE);
        return stemming;
    }

    @BeforeEach
    public void initTest() {
        stemming = createEntity(em);
    }

    @Test
    @Transactional
    void createStemming() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Stemming
        var returnedStemming = om.readValue(
            restStemmingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stemming)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Stemming.class
        );

        // Validate the Stemming in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStemmingUpdatableFieldsEquals(returnedStemming, getPersistedStemming(returnedStemming));
    }

    @Test
    @Transactional
    void createStemmingWithExistingId() throws Exception {
        // Create the Stemming with an existing ID
        stemming.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStemmingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stemming)))
            .andExpect(status().isBadRequest());

        // Validate the Stemming in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStemmings() throws Exception {
        // Initialize the database
        stemmingRepository.saveAndFlush(stemming);

        // Get all the stemmingList
        restStemmingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stemming.getId().intValue())))
            .andExpect(jsonPath("$.[*].resultaat").value(hasItem(DEFAULT_RESULTAAT)))
            .andExpect(jsonPath("$.[*].stemmingstype").value(hasItem(DEFAULT_STEMMINGSTYPE)));
    }

    @Test
    @Transactional
    void getStemming() throws Exception {
        // Initialize the database
        stemmingRepository.saveAndFlush(stemming);

        // Get the stemming
        restStemmingMockMvc
            .perform(get(ENTITY_API_URL_ID, stemming.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stemming.getId().intValue()))
            .andExpect(jsonPath("$.resultaat").value(DEFAULT_RESULTAAT))
            .andExpect(jsonPath("$.stemmingstype").value(DEFAULT_STEMMINGSTYPE));
    }

    @Test
    @Transactional
    void getNonExistingStemming() throws Exception {
        // Get the stemming
        restStemmingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStemming() throws Exception {
        // Initialize the database
        stemmingRepository.saveAndFlush(stemming);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stemming
        Stemming updatedStemming = stemmingRepository.findById(stemming.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStemming are not directly saved in db
        em.detach(updatedStemming);
        updatedStemming.resultaat(UPDATED_RESULTAAT).stemmingstype(UPDATED_STEMMINGSTYPE);

        restStemmingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStemming.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStemming))
            )
            .andExpect(status().isOk());

        // Validate the Stemming in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStemmingToMatchAllProperties(updatedStemming);
    }

    @Test
    @Transactional
    void putNonExistingStemming() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stemming.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStemmingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stemming.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stemming))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stemming in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStemming() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stemming.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStemmingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(stemming))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stemming in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStemming() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stemming.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStemmingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stemming)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stemming in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStemmingWithPatch() throws Exception {
        // Initialize the database
        stemmingRepository.saveAndFlush(stemming);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stemming using partial update
        Stemming partialUpdatedStemming = new Stemming();
        partialUpdatedStemming.setId(stemming.getId());

        restStemmingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStemming.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStemming))
            )
            .andExpect(status().isOk());

        // Validate the Stemming in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStemmingUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedStemming, stemming), getPersistedStemming(stemming));
    }

    @Test
    @Transactional
    void fullUpdateStemmingWithPatch() throws Exception {
        // Initialize the database
        stemmingRepository.saveAndFlush(stemming);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stemming using partial update
        Stemming partialUpdatedStemming = new Stemming();
        partialUpdatedStemming.setId(stemming.getId());

        partialUpdatedStemming.resultaat(UPDATED_RESULTAAT).stemmingstype(UPDATED_STEMMINGSTYPE);

        restStemmingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStemming.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStemming))
            )
            .andExpect(status().isOk());

        // Validate the Stemming in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStemmingUpdatableFieldsEquals(partialUpdatedStemming, getPersistedStemming(partialUpdatedStemming));
    }

    @Test
    @Transactional
    void patchNonExistingStemming() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stemming.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStemmingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stemming.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(stemming))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stemming in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStemming() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stemming.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStemmingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(stemming))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stemming in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStemming() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stemming.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStemmingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(stemming)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stemming in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStemming() throws Exception {
        // Initialize the database
        stemmingRepository.saveAndFlush(stemming);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the stemming
        restStemmingMockMvc
            .perform(delete(ENTITY_API_URL_ID, stemming.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return stemmingRepository.count();
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

    protected Stemming getPersistedStemming(Stemming stemming) {
        return stemmingRepository.findById(stemming.getId()).orElseThrow();
    }

    protected void assertPersistedStemmingToMatchAllProperties(Stemming expectedStemming) {
        assertStemmingAllPropertiesEquals(expectedStemming, getPersistedStemming(expectedStemming));
    }

    protected void assertPersistedStemmingToMatchUpdatableProperties(Stemming expectedStemming) {
        assertStemmingAllUpdatablePropertiesEquals(expectedStemming, getPersistedStemming(expectedStemming));
    }
}
