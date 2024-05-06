package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BeperkingscoreAsserts.*;
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
import nl.ritense.demo.domain.Beperkingscore;
import nl.ritense.demo.repository.BeperkingscoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BeperkingscoreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BeperkingscoreResourceIT {

    private static final String DEFAULT_COMMENTAAR = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAAR = "BBBBBBBBBB";

    private static final String DEFAULT_SCORE = "AAAAAAAAAA";
    private static final String UPDATED_SCORE = "BBBBBBBBBB";

    private static final String DEFAULT_WET = "AAAAAAAAAA";
    private static final String UPDATED_WET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/beperkingscores";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BeperkingscoreRepository beperkingscoreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeperkingscoreMockMvc;

    private Beperkingscore beperkingscore;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beperkingscore createEntity(EntityManager em) {
        Beperkingscore beperkingscore = new Beperkingscore().commentaar(DEFAULT_COMMENTAAR).score(DEFAULT_SCORE).wet(DEFAULT_WET);
        return beperkingscore;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beperkingscore createUpdatedEntity(EntityManager em) {
        Beperkingscore beperkingscore = new Beperkingscore().commentaar(UPDATED_COMMENTAAR).score(UPDATED_SCORE).wet(UPDATED_WET);
        return beperkingscore;
    }

    @BeforeEach
    public void initTest() {
        beperkingscore = createEntity(em);
    }

    @Test
    @Transactional
    void createBeperkingscore() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Beperkingscore
        var returnedBeperkingscore = om.readValue(
            restBeperkingscoreMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beperkingscore)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Beperkingscore.class
        );

        // Validate the Beperkingscore in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBeperkingscoreUpdatableFieldsEquals(returnedBeperkingscore, getPersistedBeperkingscore(returnedBeperkingscore));
    }

    @Test
    @Transactional
    void createBeperkingscoreWithExistingId() throws Exception {
        // Create the Beperkingscore with an existing ID
        beperkingscore.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeperkingscoreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beperkingscore)))
            .andExpect(status().isBadRequest());

        // Validate the Beperkingscore in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBeperkingscores() throws Exception {
        // Initialize the database
        beperkingscoreRepository.saveAndFlush(beperkingscore);

        // Get all the beperkingscoreList
        restBeperkingscoreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beperkingscore.getId().intValue())))
            .andExpect(jsonPath("$.[*].commentaar").value(hasItem(DEFAULT_COMMENTAAR)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].wet").value(hasItem(DEFAULT_WET)));
    }

    @Test
    @Transactional
    void getBeperkingscore() throws Exception {
        // Initialize the database
        beperkingscoreRepository.saveAndFlush(beperkingscore);

        // Get the beperkingscore
        restBeperkingscoreMockMvc
            .perform(get(ENTITY_API_URL_ID, beperkingscore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beperkingscore.getId().intValue()))
            .andExpect(jsonPath("$.commentaar").value(DEFAULT_COMMENTAAR))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.wet").value(DEFAULT_WET));
    }

    @Test
    @Transactional
    void getNonExistingBeperkingscore() throws Exception {
        // Get the beperkingscore
        restBeperkingscoreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBeperkingscore() throws Exception {
        // Initialize the database
        beperkingscoreRepository.saveAndFlush(beperkingscore);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beperkingscore
        Beperkingscore updatedBeperkingscore = beperkingscoreRepository.findById(beperkingscore.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBeperkingscore are not directly saved in db
        em.detach(updatedBeperkingscore);
        updatedBeperkingscore.commentaar(UPDATED_COMMENTAAR).score(UPDATED_SCORE).wet(UPDATED_WET);

        restBeperkingscoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBeperkingscore.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBeperkingscore))
            )
            .andExpect(status().isOk());

        // Validate the Beperkingscore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBeperkingscoreToMatchAllProperties(updatedBeperkingscore);
    }

    @Test
    @Transactional
    void putNonExistingBeperkingscore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscore.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeperkingscoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, beperkingscore.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beperkingscore))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperkingscore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBeperkingscore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscore.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingscoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beperkingscore))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperkingscore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBeperkingscore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscore.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingscoreMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beperkingscore)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beperkingscore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBeperkingscoreWithPatch() throws Exception {
        // Initialize the database
        beperkingscoreRepository.saveAndFlush(beperkingscore);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beperkingscore using partial update
        Beperkingscore partialUpdatedBeperkingscore = new Beperkingscore();
        partialUpdatedBeperkingscore.setId(beperkingscore.getId());

        partialUpdatedBeperkingscore.commentaar(UPDATED_COMMENTAAR).wet(UPDATED_WET);

        restBeperkingscoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeperkingscore.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeperkingscore))
            )
            .andExpect(status().isOk());

        // Validate the Beperkingscore in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeperkingscoreUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBeperkingscore, beperkingscore),
            getPersistedBeperkingscore(beperkingscore)
        );
    }

    @Test
    @Transactional
    void fullUpdateBeperkingscoreWithPatch() throws Exception {
        // Initialize the database
        beperkingscoreRepository.saveAndFlush(beperkingscore);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beperkingscore using partial update
        Beperkingscore partialUpdatedBeperkingscore = new Beperkingscore();
        partialUpdatedBeperkingscore.setId(beperkingscore.getId());

        partialUpdatedBeperkingscore.commentaar(UPDATED_COMMENTAAR).score(UPDATED_SCORE).wet(UPDATED_WET);

        restBeperkingscoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeperkingscore.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeperkingscore))
            )
            .andExpect(status().isOk());

        // Validate the Beperkingscore in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeperkingscoreUpdatableFieldsEquals(partialUpdatedBeperkingscore, getPersistedBeperkingscore(partialUpdatedBeperkingscore));
    }

    @Test
    @Transactional
    void patchNonExistingBeperkingscore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscore.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeperkingscoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, beperkingscore.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beperkingscore))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperkingscore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBeperkingscore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscore.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingscoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beperkingscore))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperkingscore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBeperkingscore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscore.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingscoreMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(beperkingscore)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beperkingscore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBeperkingscore() throws Exception {
        // Initialize the database
        beperkingscoreRepository.saveAndFlush(beperkingscore);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the beperkingscore
        restBeperkingscoreMockMvc
            .perform(delete(ENTITY_API_URL_ID, beperkingscore.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return beperkingscoreRepository.count();
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

    protected Beperkingscore getPersistedBeperkingscore(Beperkingscore beperkingscore) {
        return beperkingscoreRepository.findById(beperkingscore.getId()).orElseThrow();
    }

    protected void assertPersistedBeperkingscoreToMatchAllProperties(Beperkingscore expectedBeperkingscore) {
        assertBeperkingscoreAllPropertiesEquals(expectedBeperkingscore, getPersistedBeperkingscore(expectedBeperkingscore));
    }

    protected void assertPersistedBeperkingscoreToMatchUpdatableProperties(Beperkingscore expectedBeperkingscore) {
        assertBeperkingscoreAllUpdatablePropertiesEquals(expectedBeperkingscore, getPersistedBeperkingscore(expectedBeperkingscore));
    }
}
