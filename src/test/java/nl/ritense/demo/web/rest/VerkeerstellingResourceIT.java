package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerkeerstellingAsserts.*;
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
import nl.ritense.demo.domain.Verkeerstelling;
import nl.ritense.demo.repository.VerkeerstellingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerkeerstellingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerkeerstellingResourceIT {

    private static final String DEFAULT_AANTAL = "AAAAAAAAAA";
    private static final String UPDATED_AANTAL = "BBBBBBBBBB";

    private static final String DEFAULT_TIJDTOT = "AAAAAAAAAA";
    private static final String UPDATED_TIJDTOT = "BBBBBBBBBB";

    private static final String DEFAULT_TIJDVANAF = "AAAAAAAAAA";
    private static final String UPDATED_TIJDVANAF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verkeerstellings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerkeerstellingRepository verkeerstellingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerkeerstellingMockMvc;

    private Verkeerstelling verkeerstelling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verkeerstelling createEntity(EntityManager em) {
        Verkeerstelling verkeerstelling = new Verkeerstelling()
            .aantal(DEFAULT_AANTAL)
            .tijdtot(DEFAULT_TIJDTOT)
            .tijdvanaf(DEFAULT_TIJDVANAF);
        return verkeerstelling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verkeerstelling createUpdatedEntity(EntityManager em) {
        Verkeerstelling verkeerstelling = new Verkeerstelling()
            .aantal(UPDATED_AANTAL)
            .tijdtot(UPDATED_TIJDTOT)
            .tijdvanaf(UPDATED_TIJDVANAF);
        return verkeerstelling;
    }

    @BeforeEach
    public void initTest() {
        verkeerstelling = createEntity(em);
    }

    @Test
    @Transactional
    void createVerkeerstelling() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verkeerstelling
        var returnedVerkeerstelling = om.readValue(
            restVerkeerstellingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verkeerstelling)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verkeerstelling.class
        );

        // Validate the Verkeerstelling in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerkeerstellingUpdatableFieldsEquals(returnedVerkeerstelling, getPersistedVerkeerstelling(returnedVerkeerstelling));
    }

    @Test
    @Transactional
    void createVerkeerstellingWithExistingId() throws Exception {
        // Create the Verkeerstelling with an existing ID
        verkeerstelling.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerkeerstellingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verkeerstelling)))
            .andExpect(status().isBadRequest());

        // Validate the Verkeerstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerkeerstellings() throws Exception {
        // Initialize the database
        verkeerstellingRepository.saveAndFlush(verkeerstelling);

        // Get all the verkeerstellingList
        restVerkeerstellingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verkeerstelling.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantal").value(hasItem(DEFAULT_AANTAL)))
            .andExpect(jsonPath("$.[*].tijdtot").value(hasItem(DEFAULT_TIJDTOT)))
            .andExpect(jsonPath("$.[*].tijdvanaf").value(hasItem(DEFAULT_TIJDVANAF)));
    }

    @Test
    @Transactional
    void getVerkeerstelling() throws Exception {
        // Initialize the database
        verkeerstellingRepository.saveAndFlush(verkeerstelling);

        // Get the verkeerstelling
        restVerkeerstellingMockMvc
            .perform(get(ENTITY_API_URL_ID, verkeerstelling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verkeerstelling.getId().intValue()))
            .andExpect(jsonPath("$.aantal").value(DEFAULT_AANTAL))
            .andExpect(jsonPath("$.tijdtot").value(DEFAULT_TIJDTOT))
            .andExpect(jsonPath("$.tijdvanaf").value(DEFAULT_TIJDVANAF));
    }

    @Test
    @Transactional
    void getNonExistingVerkeerstelling() throws Exception {
        // Get the verkeerstelling
        restVerkeerstellingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerkeerstelling() throws Exception {
        // Initialize the database
        verkeerstellingRepository.saveAndFlush(verkeerstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verkeerstelling
        Verkeerstelling updatedVerkeerstelling = verkeerstellingRepository.findById(verkeerstelling.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerkeerstelling are not directly saved in db
        em.detach(updatedVerkeerstelling);
        updatedVerkeerstelling.aantal(UPDATED_AANTAL).tijdtot(UPDATED_TIJDTOT).tijdvanaf(UPDATED_TIJDVANAF);

        restVerkeerstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerkeerstelling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerkeerstelling))
            )
            .andExpect(status().isOk());

        // Validate the Verkeerstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerkeerstellingToMatchAllProperties(updatedVerkeerstelling);
    }

    @Test
    @Transactional
    void putNonExistingVerkeerstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeerstelling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerkeerstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verkeerstelling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verkeerstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verkeerstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerkeerstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeerstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerkeerstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verkeerstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verkeerstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerkeerstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeerstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerkeerstellingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verkeerstelling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verkeerstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerkeerstellingWithPatch() throws Exception {
        // Initialize the database
        verkeerstellingRepository.saveAndFlush(verkeerstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verkeerstelling using partial update
        Verkeerstelling partialUpdatedVerkeerstelling = new Verkeerstelling();
        partialUpdatedVerkeerstelling.setId(verkeerstelling.getId());

        partialUpdatedVerkeerstelling.tijdtot(UPDATED_TIJDTOT).tijdvanaf(UPDATED_TIJDVANAF);

        restVerkeerstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerkeerstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerkeerstelling))
            )
            .andExpect(status().isOk());

        // Validate the Verkeerstelling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerkeerstellingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerkeerstelling, verkeerstelling),
            getPersistedVerkeerstelling(verkeerstelling)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerkeerstellingWithPatch() throws Exception {
        // Initialize the database
        verkeerstellingRepository.saveAndFlush(verkeerstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verkeerstelling using partial update
        Verkeerstelling partialUpdatedVerkeerstelling = new Verkeerstelling();
        partialUpdatedVerkeerstelling.setId(verkeerstelling.getId());

        partialUpdatedVerkeerstelling.aantal(UPDATED_AANTAL).tijdtot(UPDATED_TIJDTOT).tijdvanaf(UPDATED_TIJDVANAF);

        restVerkeerstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerkeerstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerkeerstelling))
            )
            .andExpect(status().isOk());

        // Validate the Verkeerstelling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerkeerstellingUpdatableFieldsEquals(
            partialUpdatedVerkeerstelling,
            getPersistedVerkeerstelling(partialUpdatedVerkeerstelling)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVerkeerstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeerstelling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerkeerstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verkeerstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verkeerstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verkeerstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerkeerstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeerstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerkeerstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verkeerstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verkeerstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerkeerstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeerstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerkeerstellingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verkeerstelling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verkeerstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerkeerstelling() throws Exception {
        // Initialize the database
        verkeerstellingRepository.saveAndFlush(verkeerstelling);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verkeerstelling
        restVerkeerstellingMockMvc
            .perform(delete(ENTITY_API_URL_ID, verkeerstelling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verkeerstellingRepository.count();
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

    protected Verkeerstelling getPersistedVerkeerstelling(Verkeerstelling verkeerstelling) {
        return verkeerstellingRepository.findById(verkeerstelling.getId()).orElseThrow();
    }

    protected void assertPersistedVerkeerstellingToMatchAllProperties(Verkeerstelling expectedVerkeerstelling) {
        assertVerkeerstellingAllPropertiesEquals(expectedVerkeerstelling, getPersistedVerkeerstelling(expectedVerkeerstelling));
    }

    protected void assertPersistedVerkeerstellingToMatchUpdatableProperties(Verkeerstelling expectedVerkeerstelling) {
        assertVerkeerstellingAllUpdatablePropertiesEquals(expectedVerkeerstelling, getPersistedVerkeerstelling(expectedVerkeerstelling));
    }
}
