package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FraudegegevensAsserts.*;
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
import nl.ritense.demo.domain.Fraudegegevens;
import nl.ritense.demo.repository.FraudegegevensRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FraudegegevensResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FraudegegevensResourceIT {

    private static final String DEFAULT_BEDRAGFRAUDE = "AAAAAAAAAA";
    private static final String UPDATED_BEDRAGFRAUDE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDEFRAUDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDEFRAUDE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMGECONSTATEERD = "AAAAAAAAAA";
    private static final String UPDATED_DATUMGECONSTATEERD = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMSTARTFRAUDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSTARTFRAUDE = "BBBBBBBBBB";

    private static final String DEFAULT_VERREKENING = "AAAAAAAAAA";
    private static final String UPDATED_VERREKENING = "BBBBBBBBBB";

    private static final String DEFAULT_VORDERINGEN = "AAAAAAAAAA";
    private static final String UPDATED_VORDERINGEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fraudegegevens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FraudegegevensRepository fraudegegevensRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFraudegegevensMockMvc;

    private Fraudegegevens fraudegegevens;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fraudegegevens createEntity(EntityManager em) {
        Fraudegegevens fraudegegevens = new Fraudegegevens()
            .bedragfraude(DEFAULT_BEDRAGFRAUDE)
            .datumeindefraude(DEFAULT_DATUMEINDEFRAUDE)
            .datumgeconstateerd(DEFAULT_DATUMGECONSTATEERD)
            .datumstartfraude(DEFAULT_DATUMSTARTFRAUDE)
            .verrekening(DEFAULT_VERREKENING)
            .vorderingen(DEFAULT_VORDERINGEN);
        return fraudegegevens;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fraudegegevens createUpdatedEntity(EntityManager em) {
        Fraudegegevens fraudegegevens = new Fraudegegevens()
            .bedragfraude(UPDATED_BEDRAGFRAUDE)
            .datumeindefraude(UPDATED_DATUMEINDEFRAUDE)
            .datumgeconstateerd(UPDATED_DATUMGECONSTATEERD)
            .datumstartfraude(UPDATED_DATUMSTARTFRAUDE)
            .verrekening(UPDATED_VERREKENING)
            .vorderingen(UPDATED_VORDERINGEN);
        return fraudegegevens;
    }

    @BeforeEach
    public void initTest() {
        fraudegegevens = createEntity(em);
    }

    @Test
    @Transactional
    void createFraudegegevens() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Fraudegegevens
        var returnedFraudegegevens = om.readValue(
            restFraudegegevensMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fraudegegevens)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Fraudegegevens.class
        );

        // Validate the Fraudegegevens in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFraudegegevensUpdatableFieldsEquals(returnedFraudegegevens, getPersistedFraudegegevens(returnedFraudegegevens));
    }

    @Test
    @Transactional
    void createFraudegegevensWithExistingId() throws Exception {
        // Create the Fraudegegevens with an existing ID
        fraudegegevens.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFraudegegevensMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fraudegegevens)))
            .andExpect(status().isBadRequest());

        // Validate the Fraudegegevens in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFraudegegevens() throws Exception {
        // Initialize the database
        fraudegegevensRepository.saveAndFlush(fraudegegevens);

        // Get all the fraudegegevensList
        restFraudegegevensMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fraudegegevens.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedragfraude").value(hasItem(DEFAULT_BEDRAGFRAUDE)))
            .andExpect(jsonPath("$.[*].datumeindefraude").value(hasItem(DEFAULT_DATUMEINDEFRAUDE)))
            .andExpect(jsonPath("$.[*].datumgeconstateerd").value(hasItem(DEFAULT_DATUMGECONSTATEERD)))
            .andExpect(jsonPath("$.[*].datumstartfraude").value(hasItem(DEFAULT_DATUMSTARTFRAUDE)))
            .andExpect(jsonPath("$.[*].verrekening").value(hasItem(DEFAULT_VERREKENING)))
            .andExpect(jsonPath("$.[*].vorderingen").value(hasItem(DEFAULT_VORDERINGEN)));
    }

    @Test
    @Transactional
    void getFraudegegevens() throws Exception {
        // Initialize the database
        fraudegegevensRepository.saveAndFlush(fraudegegevens);

        // Get the fraudegegevens
        restFraudegegevensMockMvc
            .perform(get(ENTITY_API_URL_ID, fraudegegevens.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fraudegegevens.getId().intValue()))
            .andExpect(jsonPath("$.bedragfraude").value(DEFAULT_BEDRAGFRAUDE))
            .andExpect(jsonPath("$.datumeindefraude").value(DEFAULT_DATUMEINDEFRAUDE))
            .andExpect(jsonPath("$.datumgeconstateerd").value(DEFAULT_DATUMGECONSTATEERD))
            .andExpect(jsonPath("$.datumstartfraude").value(DEFAULT_DATUMSTARTFRAUDE))
            .andExpect(jsonPath("$.verrekening").value(DEFAULT_VERREKENING))
            .andExpect(jsonPath("$.vorderingen").value(DEFAULT_VORDERINGEN));
    }

    @Test
    @Transactional
    void getNonExistingFraudegegevens() throws Exception {
        // Get the fraudegegevens
        restFraudegegevensMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFraudegegevens() throws Exception {
        // Initialize the database
        fraudegegevensRepository.saveAndFlush(fraudegegevens);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fraudegegevens
        Fraudegegevens updatedFraudegegevens = fraudegegevensRepository.findById(fraudegegevens.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFraudegegevens are not directly saved in db
        em.detach(updatedFraudegegevens);
        updatedFraudegegevens
            .bedragfraude(UPDATED_BEDRAGFRAUDE)
            .datumeindefraude(UPDATED_DATUMEINDEFRAUDE)
            .datumgeconstateerd(UPDATED_DATUMGECONSTATEERD)
            .datumstartfraude(UPDATED_DATUMSTARTFRAUDE)
            .verrekening(UPDATED_VERREKENING)
            .vorderingen(UPDATED_VORDERINGEN);

        restFraudegegevensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFraudegegevens.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFraudegegevens))
            )
            .andExpect(status().isOk());

        // Validate the Fraudegegevens in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFraudegegevensToMatchAllProperties(updatedFraudegegevens);
    }

    @Test
    @Transactional
    void putNonExistingFraudegegevens() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fraudegegevens.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraudegegevensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fraudegegevens.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fraudegegevens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fraudegegevens in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFraudegegevens() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fraudegegevens.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraudegegevensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fraudegegevens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fraudegegevens in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFraudegegevens() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fraudegegevens.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraudegegevensMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fraudegegevens)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fraudegegevens in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFraudegegevensWithPatch() throws Exception {
        // Initialize the database
        fraudegegevensRepository.saveAndFlush(fraudegegevens);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fraudegegevens using partial update
        Fraudegegevens partialUpdatedFraudegegevens = new Fraudegegevens();
        partialUpdatedFraudegegevens.setId(fraudegegevens.getId());

        partialUpdatedFraudegegevens
            .bedragfraude(UPDATED_BEDRAGFRAUDE)
            .datumeindefraude(UPDATED_DATUMEINDEFRAUDE)
            .datumstartfraude(UPDATED_DATUMSTARTFRAUDE);

        restFraudegegevensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFraudegegevens.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFraudegegevens))
            )
            .andExpect(status().isOk());

        // Validate the Fraudegegevens in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFraudegegevensUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFraudegegevens, fraudegegevens),
            getPersistedFraudegegevens(fraudegegevens)
        );
    }

    @Test
    @Transactional
    void fullUpdateFraudegegevensWithPatch() throws Exception {
        // Initialize the database
        fraudegegevensRepository.saveAndFlush(fraudegegevens);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fraudegegevens using partial update
        Fraudegegevens partialUpdatedFraudegegevens = new Fraudegegevens();
        partialUpdatedFraudegegevens.setId(fraudegegevens.getId());

        partialUpdatedFraudegegevens
            .bedragfraude(UPDATED_BEDRAGFRAUDE)
            .datumeindefraude(UPDATED_DATUMEINDEFRAUDE)
            .datumgeconstateerd(UPDATED_DATUMGECONSTATEERD)
            .datumstartfraude(UPDATED_DATUMSTARTFRAUDE)
            .verrekening(UPDATED_VERREKENING)
            .vorderingen(UPDATED_VORDERINGEN);

        restFraudegegevensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFraudegegevens.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFraudegegevens))
            )
            .andExpect(status().isOk());

        // Validate the Fraudegegevens in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFraudegegevensUpdatableFieldsEquals(partialUpdatedFraudegegevens, getPersistedFraudegegevens(partialUpdatedFraudegegevens));
    }

    @Test
    @Transactional
    void patchNonExistingFraudegegevens() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fraudegegevens.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraudegegevensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fraudegegevens.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fraudegegevens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fraudegegevens in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFraudegegevens() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fraudegegevens.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraudegegevensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fraudegegevens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fraudegegevens in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFraudegegevens() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fraudegegevens.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraudegegevensMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fraudegegevens)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fraudegegevens in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFraudegegevens() throws Exception {
        // Initialize the database
        fraudegegevensRepository.saveAndFlush(fraudegegevens);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fraudegegevens
        restFraudegegevensMockMvc
            .perform(delete(ENTITY_API_URL_ID, fraudegegevens.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fraudegegevensRepository.count();
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

    protected Fraudegegevens getPersistedFraudegegevens(Fraudegegevens fraudegegevens) {
        return fraudegegevensRepository.findById(fraudegegevens.getId()).orElseThrow();
    }

    protected void assertPersistedFraudegegevensToMatchAllProperties(Fraudegegevens expectedFraudegegevens) {
        assertFraudegegevensAllPropertiesEquals(expectedFraudegegevens, getPersistedFraudegegevens(expectedFraudegegevens));
    }

    protected void assertPersistedFraudegegevensToMatchUpdatableProperties(Fraudegegevens expectedFraudegegevens) {
        assertFraudegegevensAllUpdatablePropertiesEquals(expectedFraudegegevens, getPersistedFraudegegevens(expectedFraudegegevens));
    }
}
