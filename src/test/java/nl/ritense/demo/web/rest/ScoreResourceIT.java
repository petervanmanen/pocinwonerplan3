package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ScoreAsserts.*;
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
import nl.ritense.demo.domain.Score;
import nl.ritense.demo.repository.ScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ScoreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ScoreResourceIT {

    private static final String DEFAULT_DATUM = "AAAAAAAAAA";
    private static final String UPDATED_DATUM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/scores";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScoreMockMvc;

    private Score score;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Score createEntity(EntityManager em) {
        Score score = new Score().datum(DEFAULT_DATUM);
        return score;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Score createUpdatedEntity(EntityManager em) {
        Score score = new Score().datum(UPDATED_DATUM);
        return score;
    }

    @BeforeEach
    public void initTest() {
        score = createEntity(em);
    }

    @Test
    @Transactional
    void createScore() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Score
        var returnedScore = om.readValue(
            restScoreMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(score)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Score.class
        );

        // Validate the Score in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertScoreUpdatableFieldsEquals(returnedScore, getPersistedScore(returnedScore));
    }

    @Test
    @Transactional
    void createScoreWithExistingId() throws Exception {
        // Create the Score with an existing ID
        score.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScoreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(score)))
            .andExpect(status().isBadRequest());

        // Validate the Score in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllScores() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList
        restScoreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(score.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM)));
    }

    @Test
    @Transactional
    void getScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get the score
        restScoreMockMvc
            .perform(get(ENTITY_API_URL_ID, score.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(score.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM));
    }

    @Test
    @Transactional
    void getNonExistingScore() throws Exception {
        // Get the score
        restScoreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the score
        Score updatedScore = scoreRepository.findById(score.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedScore are not directly saved in db
        em.detach(updatedScore);
        updatedScore.datum(UPDATED_DATUM);

        restScoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedScore.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedScore))
            )
            .andExpect(status().isOk());

        // Validate the Score in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedScoreToMatchAllProperties(updatedScore);
    }

    @Test
    @Transactional
    void putNonExistingScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        score.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScoreMockMvc
            .perform(put(ENTITY_API_URL_ID, score.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(score)))
            .andExpect(status().isBadRequest());

        // Validate the Score in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        score.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(score))
            )
            .andExpect(status().isBadRequest());

        // Validate the Score in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        score.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScoreMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(score)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Score in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateScoreWithPatch() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the score using partial update
        Score partialUpdatedScore = new Score();
        partialUpdatedScore.setId(score.getId());

        restScoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScore.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedScore))
            )
            .andExpect(status().isOk());

        // Validate the Score in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertScoreUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedScore, score), getPersistedScore(score));
    }

    @Test
    @Transactional
    void fullUpdateScoreWithPatch() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the score using partial update
        Score partialUpdatedScore = new Score();
        partialUpdatedScore.setId(score.getId());

        partialUpdatedScore.datum(UPDATED_DATUM);

        restScoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScore.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedScore))
            )
            .andExpect(status().isOk());

        // Validate the Score in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertScoreUpdatableFieldsEquals(partialUpdatedScore, getPersistedScore(partialUpdatedScore));
    }

    @Test
    @Transactional
    void patchNonExistingScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        score.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, score.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(score))
            )
            .andExpect(status().isBadRequest());

        // Validate the Score in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        score.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(score))
            )
            .andExpect(status().isBadRequest());

        // Validate the Score in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        score.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScoreMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(score)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Score in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the score
        restScoreMockMvc
            .perform(delete(ENTITY_API_URL_ID, score.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return scoreRepository.count();
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

    protected Score getPersistedScore(Score score) {
        return scoreRepository.findById(score.getId()).orElseThrow();
    }

    protected void assertPersistedScoreToMatchAllProperties(Score expectedScore) {
        assertScoreAllPropertiesEquals(expectedScore, getPersistedScore(expectedScore));
    }

    protected void assertPersistedScoreToMatchUpdatableProperties(Score expectedScore) {
        assertScoreAllUpdatablePropertiesEquals(expectedScore, getPersistedScore(expectedScore));
    }
}
