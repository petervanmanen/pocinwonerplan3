package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FietsparkeervoorzieningAsserts.*;
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
import nl.ritense.demo.domain.Fietsparkeervoorziening;
import nl.ritense.demo.repository.FietsparkeervoorzieningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FietsparkeervoorzieningResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FietsparkeervoorzieningResourceIT {

    private static final String DEFAULT_AANTALPARKEERPLAATSEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALPARKEERPLAATSEN = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fietsparkeervoorzienings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FietsparkeervoorzieningRepository fietsparkeervoorzieningRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFietsparkeervoorzieningMockMvc;

    private Fietsparkeervoorziening fietsparkeervoorziening;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fietsparkeervoorziening createEntity(EntityManager em) {
        Fietsparkeervoorziening fietsparkeervoorziening = new Fietsparkeervoorziening()
            .aantalparkeerplaatsen(DEFAULT_AANTALPARKEERPLAATSEN)
            .type(DEFAULT_TYPE)
            .typeplus(DEFAULT_TYPEPLUS);
        return fietsparkeervoorziening;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fietsparkeervoorziening createUpdatedEntity(EntityManager em) {
        Fietsparkeervoorziening fietsparkeervoorziening = new Fietsparkeervoorziening()
            .aantalparkeerplaatsen(UPDATED_AANTALPARKEERPLAATSEN)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS);
        return fietsparkeervoorziening;
    }

    @BeforeEach
    public void initTest() {
        fietsparkeervoorziening = createEntity(em);
    }

    @Test
    @Transactional
    void createFietsparkeervoorziening() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Fietsparkeervoorziening
        var returnedFietsparkeervoorziening = om.readValue(
            restFietsparkeervoorzieningMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fietsparkeervoorziening))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Fietsparkeervoorziening.class
        );

        // Validate the Fietsparkeervoorziening in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFietsparkeervoorzieningUpdatableFieldsEquals(
            returnedFietsparkeervoorziening,
            getPersistedFietsparkeervoorziening(returnedFietsparkeervoorziening)
        );
    }

    @Test
    @Transactional
    void createFietsparkeervoorzieningWithExistingId() throws Exception {
        // Create the Fietsparkeervoorziening with an existing ID
        fietsparkeervoorziening.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFietsparkeervoorzieningMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fietsparkeervoorziening)))
            .andExpect(status().isBadRequest());

        // Validate the Fietsparkeervoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFietsparkeervoorzienings() throws Exception {
        // Initialize the database
        fietsparkeervoorzieningRepository.saveAndFlush(fietsparkeervoorziening);

        // Get all the fietsparkeervoorzieningList
        restFietsparkeervoorzieningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fietsparkeervoorziening.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantalparkeerplaatsen").value(hasItem(DEFAULT_AANTALPARKEERPLAATSEN)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].typeplus").value(hasItem(DEFAULT_TYPEPLUS)));
    }

    @Test
    @Transactional
    void getFietsparkeervoorziening() throws Exception {
        // Initialize the database
        fietsparkeervoorzieningRepository.saveAndFlush(fietsparkeervoorziening);

        // Get the fietsparkeervoorziening
        restFietsparkeervoorzieningMockMvc
            .perform(get(ENTITY_API_URL_ID, fietsparkeervoorziening.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fietsparkeervoorziening.getId().intValue()))
            .andExpect(jsonPath("$.aantalparkeerplaatsen").value(DEFAULT_AANTALPARKEERPLAATSEN))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.typeplus").value(DEFAULT_TYPEPLUS));
    }

    @Test
    @Transactional
    void getNonExistingFietsparkeervoorziening() throws Exception {
        // Get the fietsparkeervoorziening
        restFietsparkeervoorzieningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFietsparkeervoorziening() throws Exception {
        // Initialize the database
        fietsparkeervoorzieningRepository.saveAndFlush(fietsparkeervoorziening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fietsparkeervoorziening
        Fietsparkeervoorziening updatedFietsparkeervoorziening = fietsparkeervoorzieningRepository
            .findById(fietsparkeervoorziening.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFietsparkeervoorziening are not directly saved in db
        em.detach(updatedFietsparkeervoorziening);
        updatedFietsparkeervoorziening.aantalparkeerplaatsen(UPDATED_AANTALPARKEERPLAATSEN).type(UPDATED_TYPE).typeplus(UPDATED_TYPEPLUS);

        restFietsparkeervoorzieningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFietsparkeervoorziening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFietsparkeervoorziening))
            )
            .andExpect(status().isOk());

        // Validate the Fietsparkeervoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFietsparkeervoorzieningToMatchAllProperties(updatedFietsparkeervoorziening);
    }

    @Test
    @Transactional
    void putNonExistingFietsparkeervoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fietsparkeervoorziening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFietsparkeervoorzieningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fietsparkeervoorziening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fietsparkeervoorziening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fietsparkeervoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFietsparkeervoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fietsparkeervoorziening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFietsparkeervoorzieningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fietsparkeervoorziening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fietsparkeervoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFietsparkeervoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fietsparkeervoorziening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFietsparkeervoorzieningMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fietsparkeervoorziening)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fietsparkeervoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFietsparkeervoorzieningWithPatch() throws Exception {
        // Initialize the database
        fietsparkeervoorzieningRepository.saveAndFlush(fietsparkeervoorziening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fietsparkeervoorziening using partial update
        Fietsparkeervoorziening partialUpdatedFietsparkeervoorziening = new Fietsparkeervoorziening();
        partialUpdatedFietsparkeervoorziening.setId(fietsparkeervoorziening.getId());

        partialUpdatedFietsparkeervoorziening.type(UPDATED_TYPE).typeplus(UPDATED_TYPEPLUS);

        restFietsparkeervoorzieningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFietsparkeervoorziening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFietsparkeervoorziening))
            )
            .andExpect(status().isOk());

        // Validate the Fietsparkeervoorziening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFietsparkeervoorzieningUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFietsparkeervoorziening, fietsparkeervoorziening),
            getPersistedFietsparkeervoorziening(fietsparkeervoorziening)
        );
    }

    @Test
    @Transactional
    void fullUpdateFietsparkeervoorzieningWithPatch() throws Exception {
        // Initialize the database
        fietsparkeervoorzieningRepository.saveAndFlush(fietsparkeervoorziening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fietsparkeervoorziening using partial update
        Fietsparkeervoorziening partialUpdatedFietsparkeervoorziening = new Fietsparkeervoorziening();
        partialUpdatedFietsparkeervoorziening.setId(fietsparkeervoorziening.getId());

        partialUpdatedFietsparkeervoorziening
            .aantalparkeerplaatsen(UPDATED_AANTALPARKEERPLAATSEN)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS);

        restFietsparkeervoorzieningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFietsparkeervoorziening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFietsparkeervoorziening))
            )
            .andExpect(status().isOk());

        // Validate the Fietsparkeervoorziening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFietsparkeervoorzieningUpdatableFieldsEquals(
            partialUpdatedFietsparkeervoorziening,
            getPersistedFietsparkeervoorziening(partialUpdatedFietsparkeervoorziening)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFietsparkeervoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fietsparkeervoorziening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFietsparkeervoorzieningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fietsparkeervoorziening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fietsparkeervoorziening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fietsparkeervoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFietsparkeervoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fietsparkeervoorziening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFietsparkeervoorzieningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fietsparkeervoorziening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fietsparkeervoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFietsparkeervoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fietsparkeervoorziening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFietsparkeervoorzieningMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fietsparkeervoorziening))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fietsparkeervoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFietsparkeervoorziening() throws Exception {
        // Initialize the database
        fietsparkeervoorzieningRepository.saveAndFlush(fietsparkeervoorziening);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fietsparkeervoorziening
        restFietsparkeervoorzieningMockMvc
            .perform(delete(ENTITY_API_URL_ID, fietsparkeervoorziening.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fietsparkeervoorzieningRepository.count();
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

    protected Fietsparkeervoorziening getPersistedFietsparkeervoorziening(Fietsparkeervoorziening fietsparkeervoorziening) {
        return fietsparkeervoorzieningRepository.findById(fietsparkeervoorziening.getId()).orElseThrow();
    }

    protected void assertPersistedFietsparkeervoorzieningToMatchAllProperties(Fietsparkeervoorziening expectedFietsparkeervoorziening) {
        assertFietsparkeervoorzieningAllPropertiesEquals(
            expectedFietsparkeervoorziening,
            getPersistedFietsparkeervoorziening(expectedFietsparkeervoorziening)
        );
    }

    protected void assertPersistedFietsparkeervoorzieningToMatchUpdatableProperties(
        Fietsparkeervoorziening expectedFietsparkeervoorziening
    ) {
        assertFietsparkeervoorzieningAllUpdatablePropertiesEquals(
            expectedFietsparkeervoorziening,
            getPersistedFietsparkeervoorziening(expectedFietsparkeervoorziening)
        );
    }
}
