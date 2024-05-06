package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BelijningAsserts.*;
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
import nl.ritense.demo.domain.Belijning;
import nl.ritense.demo.repository.BelijningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BelijningResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BelijningResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/belijnings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BelijningRepository belijningRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBelijningMockMvc;

    private Belijning belijning;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Belijning createEntity(EntityManager em) {
        Belijning belijning = new Belijning().naam(DEFAULT_NAAM);
        return belijning;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Belijning createUpdatedEntity(EntityManager em) {
        Belijning belijning = new Belijning().naam(UPDATED_NAAM);
        return belijning;
    }

    @BeforeEach
    public void initTest() {
        belijning = createEntity(em);
    }

    @Test
    @Transactional
    void createBelijning() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Belijning
        var returnedBelijning = om.readValue(
            restBelijningMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(belijning)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Belijning.class
        );

        // Validate the Belijning in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBelijningUpdatableFieldsEquals(returnedBelijning, getPersistedBelijning(returnedBelijning));
    }

    @Test
    @Transactional
    void createBelijningWithExistingId() throws Exception {
        // Create the Belijning with an existing ID
        belijning.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBelijningMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(belijning)))
            .andExpect(status().isBadRequest());

        // Validate the Belijning in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBelijnings() throws Exception {
        // Initialize the database
        belijningRepository.saveAndFlush(belijning);

        // Get all the belijningList
        restBelijningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(belijning.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getBelijning() throws Exception {
        // Initialize the database
        belijningRepository.saveAndFlush(belijning);

        // Get the belijning
        restBelijningMockMvc
            .perform(get(ENTITY_API_URL_ID, belijning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(belijning.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingBelijning() throws Exception {
        // Get the belijning
        restBelijningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBelijning() throws Exception {
        // Initialize the database
        belijningRepository.saveAndFlush(belijning);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the belijning
        Belijning updatedBelijning = belijningRepository.findById(belijning.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBelijning are not directly saved in db
        em.detach(updatedBelijning);
        updatedBelijning.naam(UPDATED_NAAM);

        restBelijningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBelijning.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBelijning))
            )
            .andExpect(status().isOk());

        // Validate the Belijning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBelijningToMatchAllProperties(updatedBelijning);
    }

    @Test
    @Transactional
    void putNonExistingBelijning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belijning.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBelijningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, belijning.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(belijning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Belijning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBelijning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belijning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBelijningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(belijning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Belijning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBelijning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belijning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBelijningMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(belijning)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Belijning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBelijningWithPatch() throws Exception {
        // Initialize the database
        belijningRepository.saveAndFlush(belijning);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the belijning using partial update
        Belijning partialUpdatedBelijning = new Belijning();
        partialUpdatedBelijning.setId(belijning.getId());

        partialUpdatedBelijning.naam(UPDATED_NAAM);

        restBelijningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBelijning.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBelijning))
            )
            .andExpect(status().isOk());

        // Validate the Belijning in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBelijningUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBelijning, belijning),
            getPersistedBelijning(belijning)
        );
    }

    @Test
    @Transactional
    void fullUpdateBelijningWithPatch() throws Exception {
        // Initialize the database
        belijningRepository.saveAndFlush(belijning);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the belijning using partial update
        Belijning partialUpdatedBelijning = new Belijning();
        partialUpdatedBelijning.setId(belijning.getId());

        partialUpdatedBelijning.naam(UPDATED_NAAM);

        restBelijningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBelijning.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBelijning))
            )
            .andExpect(status().isOk());

        // Validate the Belijning in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBelijningUpdatableFieldsEquals(partialUpdatedBelijning, getPersistedBelijning(partialUpdatedBelijning));
    }

    @Test
    @Transactional
    void patchNonExistingBelijning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belijning.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBelijningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, belijning.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(belijning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Belijning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBelijning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belijning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBelijningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(belijning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Belijning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBelijning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belijning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBelijningMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(belijning)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Belijning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBelijning() throws Exception {
        // Initialize the database
        belijningRepository.saveAndFlush(belijning);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the belijning
        restBelijningMockMvc
            .perform(delete(ENTITY_API_URL_ID, belijning.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return belijningRepository.count();
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

    protected Belijning getPersistedBelijning(Belijning belijning) {
        return belijningRepository.findById(belijning.getId()).orElseThrow();
    }

    protected void assertPersistedBelijningToMatchAllProperties(Belijning expectedBelijning) {
        assertBelijningAllPropertiesEquals(expectedBelijning, getPersistedBelijning(expectedBelijning));
    }

    protected void assertPersistedBelijningToMatchUpdatableProperties(Belijning expectedBelijning) {
        assertBelijningAllUpdatablePropertiesEquals(expectedBelijning, getPersistedBelijning(expectedBelijning));
    }
}
