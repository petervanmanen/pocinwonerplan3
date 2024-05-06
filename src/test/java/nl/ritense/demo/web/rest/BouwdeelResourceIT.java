package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BouwdeelAsserts.*;
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
import nl.ritense.demo.domain.Bouwdeel;
import nl.ritense.demo.repository.BouwdeelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BouwdeelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BouwdeelResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bouwdeels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BouwdeelRepository bouwdeelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBouwdeelMockMvc;

    private Bouwdeel bouwdeel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bouwdeel createEntity(EntityManager em) {
        Bouwdeel bouwdeel = new Bouwdeel().code(DEFAULT_CODE).omschrijving(DEFAULT_OMSCHRIJVING);
        return bouwdeel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bouwdeel createUpdatedEntity(EntityManager em) {
        Bouwdeel bouwdeel = new Bouwdeel().code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING);
        return bouwdeel;
    }

    @BeforeEach
    public void initTest() {
        bouwdeel = createEntity(em);
    }

    @Test
    @Transactional
    void createBouwdeel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bouwdeel
        var returnedBouwdeel = om.readValue(
            restBouwdeelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwdeel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bouwdeel.class
        );

        // Validate the Bouwdeel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBouwdeelUpdatableFieldsEquals(returnedBouwdeel, getPersistedBouwdeel(returnedBouwdeel));
    }

    @Test
    @Transactional
    void createBouwdeelWithExistingId() throws Exception {
        // Create the Bouwdeel with an existing ID
        bouwdeel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBouwdeelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwdeel)))
            .andExpect(status().isBadRequest());

        // Validate the Bouwdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBouwdeels() throws Exception {
        // Initialize the database
        bouwdeelRepository.saveAndFlush(bouwdeel);

        // Get all the bouwdeelList
        restBouwdeelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bouwdeel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getBouwdeel() throws Exception {
        // Initialize the database
        bouwdeelRepository.saveAndFlush(bouwdeel);

        // Get the bouwdeel
        restBouwdeelMockMvc
            .perform(get(ENTITY_API_URL_ID, bouwdeel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bouwdeel.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingBouwdeel() throws Exception {
        // Get the bouwdeel
        restBouwdeelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBouwdeel() throws Exception {
        // Initialize the database
        bouwdeelRepository.saveAndFlush(bouwdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwdeel
        Bouwdeel updatedBouwdeel = bouwdeelRepository.findById(bouwdeel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBouwdeel are not directly saved in db
        em.detach(updatedBouwdeel);
        updatedBouwdeel.code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING);

        restBouwdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBouwdeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBouwdeel))
            )
            .andExpect(status().isOk());

        // Validate the Bouwdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBouwdeelToMatchAllProperties(updatedBouwdeel);
    }

    @Test
    @Transactional
    void putNonExistingBouwdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwdeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBouwdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bouwdeel.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBouwdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bouwdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBouwdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwdeelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwdeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bouwdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBouwdeelWithPatch() throws Exception {
        // Initialize the database
        bouwdeelRepository.saveAndFlush(bouwdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwdeel using partial update
        Bouwdeel partialUpdatedBouwdeel = new Bouwdeel();
        partialUpdatedBouwdeel.setId(bouwdeel.getId());

        partialUpdatedBouwdeel.code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING);

        restBouwdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBouwdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBouwdeel))
            )
            .andExpect(status().isOk());

        // Validate the Bouwdeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBouwdeelUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBouwdeel, bouwdeel), getPersistedBouwdeel(bouwdeel));
    }

    @Test
    @Transactional
    void fullUpdateBouwdeelWithPatch() throws Exception {
        // Initialize the database
        bouwdeelRepository.saveAndFlush(bouwdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwdeel using partial update
        Bouwdeel partialUpdatedBouwdeel = new Bouwdeel();
        partialUpdatedBouwdeel.setId(bouwdeel.getId());

        partialUpdatedBouwdeel.code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING);

        restBouwdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBouwdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBouwdeel))
            )
            .andExpect(status().isOk());

        // Validate the Bouwdeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBouwdeelUpdatableFieldsEquals(partialUpdatedBouwdeel, getPersistedBouwdeel(partialUpdatedBouwdeel));
    }

    @Test
    @Transactional
    void patchNonExistingBouwdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwdeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBouwdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bouwdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bouwdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBouwdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bouwdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBouwdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwdeelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bouwdeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bouwdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBouwdeel() throws Exception {
        // Initialize the database
        bouwdeelRepository.saveAndFlush(bouwdeel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bouwdeel
        restBouwdeelMockMvc
            .perform(delete(ENTITY_API_URL_ID, bouwdeel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bouwdeelRepository.count();
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

    protected Bouwdeel getPersistedBouwdeel(Bouwdeel bouwdeel) {
        return bouwdeelRepository.findById(bouwdeel.getId()).orElseThrow();
    }

    protected void assertPersistedBouwdeelToMatchAllProperties(Bouwdeel expectedBouwdeel) {
        assertBouwdeelAllPropertiesEquals(expectedBouwdeel, getPersistedBouwdeel(expectedBouwdeel));
    }

    protected void assertPersistedBouwdeelToMatchUpdatableProperties(Bouwdeel expectedBouwdeel) {
        assertBouwdeelAllUpdatablePropertiesEquals(expectedBouwdeel, getPersistedBouwdeel(expectedBouwdeel));
    }
}
