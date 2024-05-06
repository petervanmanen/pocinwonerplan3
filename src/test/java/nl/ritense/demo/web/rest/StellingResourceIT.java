package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.StellingAsserts.*;
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
import nl.ritense.demo.domain.Stelling;
import nl.ritense.demo.repository.StellingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StellingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StellingResourceIT {

    private static final String DEFAULT_INHOUD = "AAAAAAAAAA";
    private static final String UPDATED_INHOUD = "BBBBBBBBBB";

    private static final String DEFAULT_STELLINGCODE = "AAAAAAAAAA";
    private static final String UPDATED_STELLINGCODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/stellings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StellingRepository stellingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStellingMockMvc;

    private Stelling stelling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stelling createEntity(EntityManager em) {
        Stelling stelling = new Stelling().inhoud(DEFAULT_INHOUD).stellingcode(DEFAULT_STELLINGCODE);
        return stelling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stelling createUpdatedEntity(EntityManager em) {
        Stelling stelling = new Stelling().inhoud(UPDATED_INHOUD).stellingcode(UPDATED_STELLINGCODE);
        return stelling;
    }

    @BeforeEach
    public void initTest() {
        stelling = createEntity(em);
    }

    @Test
    @Transactional
    void createStelling() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Stelling
        var returnedStelling = om.readValue(
            restStellingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stelling)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Stelling.class
        );

        // Validate the Stelling in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStellingUpdatableFieldsEquals(returnedStelling, getPersistedStelling(returnedStelling));
    }

    @Test
    @Transactional
    void createStellingWithExistingId() throws Exception {
        // Create the Stelling with an existing ID
        stelling.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStellingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stelling)))
            .andExpect(status().isBadRequest());

        // Validate the Stelling in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStellings() throws Exception {
        // Initialize the database
        stellingRepository.saveAndFlush(stelling);

        // Get all the stellingList
        restStellingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stelling.getId().intValue())))
            .andExpect(jsonPath("$.[*].inhoud").value(hasItem(DEFAULT_INHOUD)))
            .andExpect(jsonPath("$.[*].stellingcode").value(hasItem(DEFAULT_STELLINGCODE)));
    }

    @Test
    @Transactional
    void getStelling() throws Exception {
        // Initialize the database
        stellingRepository.saveAndFlush(stelling);

        // Get the stelling
        restStellingMockMvc
            .perform(get(ENTITY_API_URL_ID, stelling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stelling.getId().intValue()))
            .andExpect(jsonPath("$.inhoud").value(DEFAULT_INHOUD))
            .andExpect(jsonPath("$.stellingcode").value(DEFAULT_STELLINGCODE));
    }

    @Test
    @Transactional
    void getNonExistingStelling() throws Exception {
        // Get the stelling
        restStellingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStelling() throws Exception {
        // Initialize the database
        stellingRepository.saveAndFlush(stelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stelling
        Stelling updatedStelling = stellingRepository.findById(stelling.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStelling are not directly saved in db
        em.detach(updatedStelling);
        updatedStelling.inhoud(UPDATED_INHOUD).stellingcode(UPDATED_STELLINGCODE);

        restStellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStelling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStelling))
            )
            .andExpect(status().isOk());

        // Validate the Stelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStellingToMatchAllProperties(updatedStelling);
    }

    @Test
    @Transactional
    void putNonExistingStelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stelling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stelling.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(stelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStellingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stelling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStellingWithPatch() throws Exception {
        // Initialize the database
        stellingRepository.saveAndFlush(stelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stelling using partial update
        Stelling partialUpdatedStelling = new Stelling();
        partialUpdatedStelling.setId(stelling.getId());

        restStellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStelling))
            )
            .andExpect(status().isOk());

        // Validate the Stelling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStellingUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedStelling, stelling), getPersistedStelling(stelling));
    }

    @Test
    @Transactional
    void fullUpdateStellingWithPatch() throws Exception {
        // Initialize the database
        stellingRepository.saveAndFlush(stelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stelling using partial update
        Stelling partialUpdatedStelling = new Stelling();
        partialUpdatedStelling.setId(stelling.getId());

        partialUpdatedStelling.inhoud(UPDATED_INHOUD).stellingcode(UPDATED_STELLINGCODE);

        restStellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStelling))
            )
            .andExpect(status().isOk());

        // Validate the Stelling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStellingUpdatableFieldsEquals(partialUpdatedStelling, getPersistedStelling(partialUpdatedStelling));
    }

    @Test
    @Transactional
    void patchNonExistingStelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stelling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(stelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(stelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStellingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(stelling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStelling() throws Exception {
        // Initialize the database
        stellingRepository.saveAndFlush(stelling);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the stelling
        restStellingMockMvc
            .perform(delete(ENTITY_API_URL_ID, stelling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return stellingRepository.count();
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

    protected Stelling getPersistedStelling(Stelling stelling) {
        return stellingRepository.findById(stelling.getId()).orElseThrow();
    }

    protected void assertPersistedStellingToMatchAllProperties(Stelling expectedStelling) {
        assertStellingAllPropertiesEquals(expectedStelling, getPersistedStelling(expectedStelling));
    }

    protected void assertPersistedStellingToMatchUpdatableProperties(Stelling expectedStelling) {
        assertStellingAllUpdatablePropertiesEquals(expectedStelling, getPersistedStelling(expectedStelling));
    }
}
