package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.StrooidagAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Strooidag;
import nl.ritense.demo.repository.StrooidagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StrooidagResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StrooidagResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MAXIMUMTEMPERATUUR = "AAAAAAAAAA";
    private static final String UPDATED_MAXIMUMTEMPERATUUR = "BBBBBBBBBB";

    private static final String DEFAULT_MINIMUMTEMPERATUUR = "AAAAAAAAAA";
    private static final String UPDATED_MINIMUMTEMPERATUUR = "BBBBBBBBBB";

    private static final String DEFAULT_TIJDMAXIMUMTEMPERATUUR = "AAAAAAAAAA";
    private static final String UPDATED_TIJDMAXIMUMTEMPERATUUR = "BBBBBBBBBB";

    private static final String DEFAULT_TIJDMINIMUMTEMPERATUUR = "AAAAAAAAAA";
    private static final String UPDATED_TIJDMINIMUMTEMPERATUUR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/strooidags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StrooidagRepository strooidagRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStrooidagMockMvc;

    private Strooidag strooidag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Strooidag createEntity(EntityManager em) {
        Strooidag strooidag = new Strooidag()
            .datum(DEFAULT_DATUM)
            .maximumtemperatuur(DEFAULT_MAXIMUMTEMPERATUUR)
            .minimumtemperatuur(DEFAULT_MINIMUMTEMPERATUUR)
            .tijdmaximumtemperatuur(DEFAULT_TIJDMAXIMUMTEMPERATUUR)
            .tijdminimumtemperatuur(DEFAULT_TIJDMINIMUMTEMPERATUUR);
        return strooidag;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Strooidag createUpdatedEntity(EntityManager em) {
        Strooidag strooidag = new Strooidag()
            .datum(UPDATED_DATUM)
            .maximumtemperatuur(UPDATED_MAXIMUMTEMPERATUUR)
            .minimumtemperatuur(UPDATED_MINIMUMTEMPERATUUR)
            .tijdmaximumtemperatuur(UPDATED_TIJDMAXIMUMTEMPERATUUR)
            .tijdminimumtemperatuur(UPDATED_TIJDMINIMUMTEMPERATUUR);
        return strooidag;
    }

    @BeforeEach
    public void initTest() {
        strooidag = createEntity(em);
    }

    @Test
    @Transactional
    void createStrooidag() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Strooidag
        var returnedStrooidag = om.readValue(
            restStrooidagMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(strooidag)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Strooidag.class
        );

        // Validate the Strooidag in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStrooidagUpdatableFieldsEquals(returnedStrooidag, getPersistedStrooidag(returnedStrooidag));
    }

    @Test
    @Transactional
    void createStrooidagWithExistingId() throws Exception {
        // Create the Strooidag with an existing ID
        strooidag.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStrooidagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(strooidag)))
            .andExpect(status().isBadRequest());

        // Validate the Strooidag in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStrooidags() throws Exception {
        // Initialize the database
        strooidagRepository.saveAndFlush(strooidag);

        // Get all the strooidagList
        restStrooidagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strooidag.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].maximumtemperatuur").value(hasItem(DEFAULT_MAXIMUMTEMPERATUUR)))
            .andExpect(jsonPath("$.[*].minimumtemperatuur").value(hasItem(DEFAULT_MINIMUMTEMPERATUUR)))
            .andExpect(jsonPath("$.[*].tijdmaximumtemperatuur").value(hasItem(DEFAULT_TIJDMAXIMUMTEMPERATUUR)))
            .andExpect(jsonPath("$.[*].tijdminimumtemperatuur").value(hasItem(DEFAULT_TIJDMINIMUMTEMPERATUUR)));
    }

    @Test
    @Transactional
    void getStrooidag() throws Exception {
        // Initialize the database
        strooidagRepository.saveAndFlush(strooidag);

        // Get the strooidag
        restStrooidagMockMvc
            .perform(get(ENTITY_API_URL_ID, strooidag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(strooidag.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.maximumtemperatuur").value(DEFAULT_MAXIMUMTEMPERATUUR))
            .andExpect(jsonPath("$.minimumtemperatuur").value(DEFAULT_MINIMUMTEMPERATUUR))
            .andExpect(jsonPath("$.tijdmaximumtemperatuur").value(DEFAULT_TIJDMAXIMUMTEMPERATUUR))
            .andExpect(jsonPath("$.tijdminimumtemperatuur").value(DEFAULT_TIJDMINIMUMTEMPERATUUR));
    }

    @Test
    @Transactional
    void getNonExistingStrooidag() throws Exception {
        // Get the strooidag
        restStrooidagMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStrooidag() throws Exception {
        // Initialize the database
        strooidagRepository.saveAndFlush(strooidag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the strooidag
        Strooidag updatedStrooidag = strooidagRepository.findById(strooidag.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStrooidag are not directly saved in db
        em.detach(updatedStrooidag);
        updatedStrooidag
            .datum(UPDATED_DATUM)
            .maximumtemperatuur(UPDATED_MAXIMUMTEMPERATUUR)
            .minimumtemperatuur(UPDATED_MINIMUMTEMPERATUUR)
            .tijdmaximumtemperatuur(UPDATED_TIJDMAXIMUMTEMPERATUUR)
            .tijdminimumtemperatuur(UPDATED_TIJDMINIMUMTEMPERATUUR);

        restStrooidagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStrooidag.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStrooidag))
            )
            .andExpect(status().isOk());

        // Validate the Strooidag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStrooidagToMatchAllProperties(updatedStrooidag);
    }

    @Test
    @Transactional
    void putNonExistingStrooidag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooidag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrooidagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, strooidag.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(strooidag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strooidag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStrooidag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooidag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrooidagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(strooidag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strooidag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStrooidag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooidag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrooidagMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(strooidag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Strooidag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStrooidagWithPatch() throws Exception {
        // Initialize the database
        strooidagRepository.saveAndFlush(strooidag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the strooidag using partial update
        Strooidag partialUpdatedStrooidag = new Strooidag();
        partialUpdatedStrooidag.setId(strooidag.getId());

        restStrooidagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrooidag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStrooidag))
            )
            .andExpect(status().isOk());

        // Validate the Strooidag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStrooidagUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStrooidag, strooidag),
            getPersistedStrooidag(strooidag)
        );
    }

    @Test
    @Transactional
    void fullUpdateStrooidagWithPatch() throws Exception {
        // Initialize the database
        strooidagRepository.saveAndFlush(strooidag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the strooidag using partial update
        Strooidag partialUpdatedStrooidag = new Strooidag();
        partialUpdatedStrooidag.setId(strooidag.getId());

        partialUpdatedStrooidag
            .datum(UPDATED_DATUM)
            .maximumtemperatuur(UPDATED_MAXIMUMTEMPERATUUR)
            .minimumtemperatuur(UPDATED_MINIMUMTEMPERATUUR)
            .tijdmaximumtemperatuur(UPDATED_TIJDMAXIMUMTEMPERATUUR)
            .tijdminimumtemperatuur(UPDATED_TIJDMINIMUMTEMPERATUUR);

        restStrooidagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrooidag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStrooidag))
            )
            .andExpect(status().isOk());

        // Validate the Strooidag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStrooidagUpdatableFieldsEquals(partialUpdatedStrooidag, getPersistedStrooidag(partialUpdatedStrooidag));
    }

    @Test
    @Transactional
    void patchNonExistingStrooidag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooidag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrooidagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, strooidag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(strooidag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strooidag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStrooidag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooidag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrooidagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(strooidag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strooidag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStrooidag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooidag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrooidagMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(strooidag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Strooidag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStrooidag() throws Exception {
        // Initialize the database
        strooidagRepository.saveAndFlush(strooidag);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the strooidag
        restStrooidagMockMvc
            .perform(delete(ENTITY_API_URL_ID, strooidag.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return strooidagRepository.count();
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

    protected Strooidag getPersistedStrooidag(Strooidag strooidag) {
        return strooidagRepository.findById(strooidag.getId()).orElseThrow();
    }

    protected void assertPersistedStrooidagToMatchAllProperties(Strooidag expectedStrooidag) {
        assertStrooidagAllPropertiesEquals(expectedStrooidag, getPersistedStrooidag(expectedStrooidag));
    }

    protected void assertPersistedStrooidagToMatchUpdatableProperties(Strooidag expectedStrooidag) {
        assertStrooidagAllUpdatablePropertiesEquals(expectedStrooidag, getPersistedStrooidag(expectedStrooidag));
    }
}
