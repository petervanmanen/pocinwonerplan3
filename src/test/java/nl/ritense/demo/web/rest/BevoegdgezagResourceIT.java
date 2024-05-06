package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BevoegdgezagAsserts.*;
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
import nl.ritense.demo.domain.Bevoegdgezag;
import nl.ritense.demo.repository.BevoegdgezagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BevoegdgezagResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BevoegdgezagResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bevoegdgezags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BevoegdgezagRepository bevoegdgezagRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBevoegdgezagMockMvc;

    private Bevoegdgezag bevoegdgezag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bevoegdgezag createEntity(EntityManager em) {
        Bevoegdgezag bevoegdgezag = new Bevoegdgezag().naam(DEFAULT_NAAM);
        return bevoegdgezag;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bevoegdgezag createUpdatedEntity(EntityManager em) {
        Bevoegdgezag bevoegdgezag = new Bevoegdgezag().naam(UPDATED_NAAM);
        return bevoegdgezag;
    }

    @BeforeEach
    public void initTest() {
        bevoegdgezag = createEntity(em);
    }

    @Test
    @Transactional
    void createBevoegdgezag() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bevoegdgezag
        var returnedBevoegdgezag = om.readValue(
            restBevoegdgezagMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bevoegdgezag)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bevoegdgezag.class
        );

        // Validate the Bevoegdgezag in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBevoegdgezagUpdatableFieldsEquals(returnedBevoegdgezag, getPersistedBevoegdgezag(returnedBevoegdgezag));
    }

    @Test
    @Transactional
    void createBevoegdgezagWithExistingId() throws Exception {
        // Create the Bevoegdgezag with an existing ID
        bevoegdgezag.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBevoegdgezagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bevoegdgezag)))
            .andExpect(status().isBadRequest());

        // Validate the Bevoegdgezag in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBevoegdgezags() throws Exception {
        // Initialize the database
        bevoegdgezagRepository.saveAndFlush(bevoegdgezag);

        // Get all the bevoegdgezagList
        restBevoegdgezagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bevoegdgezag.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getBevoegdgezag() throws Exception {
        // Initialize the database
        bevoegdgezagRepository.saveAndFlush(bevoegdgezag);

        // Get the bevoegdgezag
        restBevoegdgezagMockMvc
            .perform(get(ENTITY_API_URL_ID, bevoegdgezag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bevoegdgezag.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingBevoegdgezag() throws Exception {
        // Get the bevoegdgezag
        restBevoegdgezagMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBevoegdgezag() throws Exception {
        // Initialize the database
        bevoegdgezagRepository.saveAndFlush(bevoegdgezag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bevoegdgezag
        Bevoegdgezag updatedBevoegdgezag = bevoegdgezagRepository.findById(bevoegdgezag.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBevoegdgezag are not directly saved in db
        em.detach(updatedBevoegdgezag);
        updatedBevoegdgezag.naam(UPDATED_NAAM);

        restBevoegdgezagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBevoegdgezag.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBevoegdgezag))
            )
            .andExpect(status().isOk());

        // Validate the Bevoegdgezag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBevoegdgezagToMatchAllProperties(updatedBevoegdgezag);
    }

    @Test
    @Transactional
    void putNonExistingBevoegdgezag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bevoegdgezag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBevoegdgezagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bevoegdgezag.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bevoegdgezag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bevoegdgezag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBevoegdgezag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bevoegdgezag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBevoegdgezagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bevoegdgezag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bevoegdgezag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBevoegdgezag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bevoegdgezag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBevoegdgezagMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bevoegdgezag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bevoegdgezag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBevoegdgezagWithPatch() throws Exception {
        // Initialize the database
        bevoegdgezagRepository.saveAndFlush(bevoegdgezag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bevoegdgezag using partial update
        Bevoegdgezag partialUpdatedBevoegdgezag = new Bevoegdgezag();
        partialUpdatedBevoegdgezag.setId(bevoegdgezag.getId());

        restBevoegdgezagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBevoegdgezag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBevoegdgezag))
            )
            .andExpect(status().isOk());

        // Validate the Bevoegdgezag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBevoegdgezagUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBevoegdgezag, bevoegdgezag),
            getPersistedBevoegdgezag(bevoegdgezag)
        );
    }

    @Test
    @Transactional
    void fullUpdateBevoegdgezagWithPatch() throws Exception {
        // Initialize the database
        bevoegdgezagRepository.saveAndFlush(bevoegdgezag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bevoegdgezag using partial update
        Bevoegdgezag partialUpdatedBevoegdgezag = new Bevoegdgezag();
        partialUpdatedBevoegdgezag.setId(bevoegdgezag.getId());

        partialUpdatedBevoegdgezag.naam(UPDATED_NAAM);

        restBevoegdgezagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBevoegdgezag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBevoegdgezag))
            )
            .andExpect(status().isOk());

        // Validate the Bevoegdgezag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBevoegdgezagUpdatableFieldsEquals(partialUpdatedBevoegdgezag, getPersistedBevoegdgezag(partialUpdatedBevoegdgezag));
    }

    @Test
    @Transactional
    void patchNonExistingBevoegdgezag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bevoegdgezag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBevoegdgezagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bevoegdgezag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bevoegdgezag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bevoegdgezag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBevoegdgezag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bevoegdgezag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBevoegdgezagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bevoegdgezag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bevoegdgezag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBevoegdgezag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bevoegdgezag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBevoegdgezagMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bevoegdgezag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bevoegdgezag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBevoegdgezag() throws Exception {
        // Initialize the database
        bevoegdgezagRepository.saveAndFlush(bevoegdgezag);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bevoegdgezag
        restBevoegdgezagMockMvc
            .perform(delete(ENTITY_API_URL_ID, bevoegdgezag.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bevoegdgezagRepository.count();
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

    protected Bevoegdgezag getPersistedBevoegdgezag(Bevoegdgezag bevoegdgezag) {
        return bevoegdgezagRepository.findById(bevoegdgezag.getId()).orElseThrow();
    }

    protected void assertPersistedBevoegdgezagToMatchAllProperties(Bevoegdgezag expectedBevoegdgezag) {
        assertBevoegdgezagAllPropertiesEquals(expectedBevoegdgezag, getPersistedBevoegdgezag(expectedBevoegdgezag));
    }

    protected void assertPersistedBevoegdgezagToMatchUpdatableProperties(Bevoegdgezag expectedBevoegdgezag) {
        assertBevoegdgezagAllUpdatablePropertiesEquals(expectedBevoegdgezag, getPersistedBevoegdgezag(expectedBevoegdgezag));
    }
}
