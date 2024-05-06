package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FraudesoortAsserts.*;
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
import nl.ritense.demo.domain.Fraudesoort;
import nl.ritense.demo.repository.FraudesoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FraudesoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FraudesoortResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fraudesoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FraudesoortRepository fraudesoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFraudesoortMockMvc;

    private Fraudesoort fraudesoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fraudesoort createEntity(EntityManager em) {
        Fraudesoort fraudesoort = new Fraudesoort().naam(DEFAULT_NAAM);
        return fraudesoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fraudesoort createUpdatedEntity(EntityManager em) {
        Fraudesoort fraudesoort = new Fraudesoort().naam(UPDATED_NAAM);
        return fraudesoort;
    }

    @BeforeEach
    public void initTest() {
        fraudesoort = createEntity(em);
    }

    @Test
    @Transactional
    void createFraudesoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Fraudesoort
        var returnedFraudesoort = om.readValue(
            restFraudesoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fraudesoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Fraudesoort.class
        );

        // Validate the Fraudesoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFraudesoortUpdatableFieldsEquals(returnedFraudesoort, getPersistedFraudesoort(returnedFraudesoort));
    }

    @Test
    @Transactional
    void createFraudesoortWithExistingId() throws Exception {
        // Create the Fraudesoort with an existing ID
        fraudesoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFraudesoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fraudesoort)))
            .andExpect(status().isBadRequest());

        // Validate the Fraudesoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFraudesoorts() throws Exception {
        // Initialize the database
        fraudesoortRepository.saveAndFlush(fraudesoort);

        // Get all the fraudesoortList
        restFraudesoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fraudesoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getFraudesoort() throws Exception {
        // Initialize the database
        fraudesoortRepository.saveAndFlush(fraudesoort);

        // Get the fraudesoort
        restFraudesoortMockMvc
            .perform(get(ENTITY_API_URL_ID, fraudesoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fraudesoort.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingFraudesoort() throws Exception {
        // Get the fraudesoort
        restFraudesoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFraudesoort() throws Exception {
        // Initialize the database
        fraudesoortRepository.saveAndFlush(fraudesoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fraudesoort
        Fraudesoort updatedFraudesoort = fraudesoortRepository.findById(fraudesoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFraudesoort are not directly saved in db
        em.detach(updatedFraudesoort);
        updatedFraudesoort.naam(UPDATED_NAAM);

        restFraudesoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFraudesoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFraudesoort))
            )
            .andExpect(status().isOk());

        // Validate the Fraudesoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFraudesoortToMatchAllProperties(updatedFraudesoort);
    }

    @Test
    @Transactional
    void putNonExistingFraudesoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fraudesoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraudesoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fraudesoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fraudesoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fraudesoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFraudesoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fraudesoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraudesoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fraudesoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fraudesoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFraudesoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fraudesoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraudesoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fraudesoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fraudesoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFraudesoortWithPatch() throws Exception {
        // Initialize the database
        fraudesoortRepository.saveAndFlush(fraudesoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fraudesoort using partial update
        Fraudesoort partialUpdatedFraudesoort = new Fraudesoort();
        partialUpdatedFraudesoort.setId(fraudesoort.getId());

        partialUpdatedFraudesoort.naam(UPDATED_NAAM);

        restFraudesoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFraudesoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFraudesoort))
            )
            .andExpect(status().isOk());

        // Validate the Fraudesoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFraudesoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFraudesoort, fraudesoort),
            getPersistedFraudesoort(fraudesoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateFraudesoortWithPatch() throws Exception {
        // Initialize the database
        fraudesoortRepository.saveAndFlush(fraudesoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fraudesoort using partial update
        Fraudesoort partialUpdatedFraudesoort = new Fraudesoort();
        partialUpdatedFraudesoort.setId(fraudesoort.getId());

        partialUpdatedFraudesoort.naam(UPDATED_NAAM);

        restFraudesoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFraudesoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFraudesoort))
            )
            .andExpect(status().isOk());

        // Validate the Fraudesoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFraudesoortUpdatableFieldsEquals(partialUpdatedFraudesoort, getPersistedFraudesoort(partialUpdatedFraudesoort));
    }

    @Test
    @Transactional
    void patchNonExistingFraudesoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fraudesoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraudesoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fraudesoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fraudesoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fraudesoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFraudesoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fraudesoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraudesoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fraudesoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fraudesoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFraudesoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fraudesoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraudesoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fraudesoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fraudesoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFraudesoort() throws Exception {
        // Initialize the database
        fraudesoortRepository.saveAndFlush(fraudesoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fraudesoort
        restFraudesoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, fraudesoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fraudesoortRepository.count();
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

    protected Fraudesoort getPersistedFraudesoort(Fraudesoort fraudesoort) {
        return fraudesoortRepository.findById(fraudesoort.getId()).orElseThrow();
    }

    protected void assertPersistedFraudesoortToMatchAllProperties(Fraudesoort expectedFraudesoort) {
        assertFraudesoortAllPropertiesEquals(expectedFraudesoort, getPersistedFraudesoort(expectedFraudesoort));
    }

    protected void assertPersistedFraudesoortToMatchUpdatableProperties(Fraudesoort expectedFraudesoort) {
        assertFraudesoortAllUpdatablePropertiesEquals(expectedFraudesoort, getPersistedFraudesoort(expectedFraudesoort));
    }
}
