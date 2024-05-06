package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ScoresoortAsserts.*;
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
import nl.ritense.demo.domain.Scoresoort;
import nl.ritense.demo.repository.ScoresoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ScoresoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ScoresoortResourceIT {

    private static final String DEFAULT_NIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_NIVEAU = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/scoresoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ScoresoortRepository scoresoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScoresoortMockMvc;

    private Scoresoort scoresoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scoresoort createEntity(EntityManager em) {
        Scoresoort scoresoort = new Scoresoort().niveau(DEFAULT_NIVEAU);
        return scoresoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scoresoort createUpdatedEntity(EntityManager em) {
        Scoresoort scoresoort = new Scoresoort().niveau(UPDATED_NIVEAU);
        return scoresoort;
    }

    @BeforeEach
    public void initTest() {
        scoresoort = createEntity(em);
    }

    @Test
    @Transactional
    void createScoresoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Scoresoort
        var returnedScoresoort = om.readValue(
            restScoresoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(scoresoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Scoresoort.class
        );

        // Validate the Scoresoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertScoresoortUpdatableFieldsEquals(returnedScoresoort, getPersistedScoresoort(returnedScoresoort));
    }

    @Test
    @Transactional
    void createScoresoortWithExistingId() throws Exception {
        // Create the Scoresoort with an existing ID
        scoresoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScoresoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(scoresoort)))
            .andExpect(status().isBadRequest());

        // Validate the Scoresoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllScoresoorts() throws Exception {
        // Initialize the database
        scoresoortRepository.saveAndFlush(scoresoort);

        // Get all the scoresoortList
        restScoresoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scoresoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU)));
    }

    @Test
    @Transactional
    void getScoresoort() throws Exception {
        // Initialize the database
        scoresoortRepository.saveAndFlush(scoresoort);

        // Get the scoresoort
        restScoresoortMockMvc
            .perform(get(ENTITY_API_URL_ID, scoresoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scoresoort.getId().intValue()))
            .andExpect(jsonPath("$.niveau").value(DEFAULT_NIVEAU));
    }

    @Test
    @Transactional
    void getNonExistingScoresoort() throws Exception {
        // Get the scoresoort
        restScoresoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingScoresoort() throws Exception {
        // Initialize the database
        scoresoortRepository.saveAndFlush(scoresoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the scoresoort
        Scoresoort updatedScoresoort = scoresoortRepository.findById(scoresoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedScoresoort are not directly saved in db
        em.detach(updatedScoresoort);
        updatedScoresoort.niveau(UPDATED_NIVEAU);

        restScoresoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedScoresoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedScoresoort))
            )
            .andExpect(status().isOk());

        // Validate the Scoresoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedScoresoortToMatchAllProperties(updatedScoresoort);
    }

    @Test
    @Transactional
    void putNonExistingScoresoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scoresoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScoresoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scoresoort.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(scoresoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scoresoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchScoresoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scoresoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScoresoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(scoresoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scoresoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamScoresoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scoresoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScoresoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(scoresoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Scoresoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateScoresoortWithPatch() throws Exception {
        // Initialize the database
        scoresoortRepository.saveAndFlush(scoresoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the scoresoort using partial update
        Scoresoort partialUpdatedScoresoort = new Scoresoort();
        partialUpdatedScoresoort.setId(scoresoort.getId());

        partialUpdatedScoresoort.niveau(UPDATED_NIVEAU);

        restScoresoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScoresoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedScoresoort))
            )
            .andExpect(status().isOk());

        // Validate the Scoresoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertScoresoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedScoresoort, scoresoort),
            getPersistedScoresoort(scoresoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateScoresoortWithPatch() throws Exception {
        // Initialize the database
        scoresoortRepository.saveAndFlush(scoresoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the scoresoort using partial update
        Scoresoort partialUpdatedScoresoort = new Scoresoort();
        partialUpdatedScoresoort.setId(scoresoort.getId());

        partialUpdatedScoresoort.niveau(UPDATED_NIVEAU);

        restScoresoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScoresoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedScoresoort))
            )
            .andExpect(status().isOk());

        // Validate the Scoresoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertScoresoortUpdatableFieldsEquals(partialUpdatedScoresoort, getPersistedScoresoort(partialUpdatedScoresoort));
    }

    @Test
    @Transactional
    void patchNonExistingScoresoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scoresoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScoresoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, scoresoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(scoresoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scoresoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchScoresoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scoresoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScoresoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(scoresoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scoresoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamScoresoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scoresoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScoresoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(scoresoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Scoresoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteScoresoort() throws Exception {
        // Initialize the database
        scoresoortRepository.saveAndFlush(scoresoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the scoresoort
        restScoresoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, scoresoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return scoresoortRepository.count();
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

    protected Scoresoort getPersistedScoresoort(Scoresoort scoresoort) {
        return scoresoortRepository.findById(scoresoort.getId()).orElseThrow();
    }

    protected void assertPersistedScoresoortToMatchAllProperties(Scoresoort expectedScoresoort) {
        assertScoresoortAllPropertiesEquals(expectedScoresoort, getPersistedScoresoort(expectedScoresoort));
    }

    protected void assertPersistedScoresoortToMatchUpdatableProperties(Scoresoort expectedScoresoort) {
        assertScoresoortAllUpdatablePropertiesEquals(expectedScoresoort, getPersistedScoresoort(expectedScoresoort));
    }
}
