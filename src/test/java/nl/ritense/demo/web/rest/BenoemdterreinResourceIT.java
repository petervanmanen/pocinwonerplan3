package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BenoemdterreinAsserts.*;
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
import nl.ritense.demo.domain.Benoemdterrein;
import nl.ritense.demo.repository.BenoemdterreinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BenoemdterreinResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BenoemdterreinResourceIT {

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/benoemdterreins";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BenoemdterreinRepository benoemdterreinRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBenoemdterreinMockMvc;

    private Benoemdterrein benoemdterrein;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Benoemdterrein createEntity(EntityManager em) {
        Benoemdterrein benoemdterrein = new Benoemdterrein().identificatie(DEFAULT_IDENTIFICATIE);
        return benoemdterrein;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Benoemdterrein createUpdatedEntity(EntityManager em) {
        Benoemdterrein benoemdterrein = new Benoemdterrein().identificatie(UPDATED_IDENTIFICATIE);
        return benoemdterrein;
    }

    @BeforeEach
    public void initTest() {
        benoemdterrein = createEntity(em);
    }

    @Test
    @Transactional
    void createBenoemdterrein() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Benoemdterrein
        var returnedBenoemdterrein = om.readValue(
            restBenoemdterreinMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(benoemdterrein)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Benoemdterrein.class
        );

        // Validate the Benoemdterrein in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBenoemdterreinUpdatableFieldsEquals(returnedBenoemdterrein, getPersistedBenoemdterrein(returnedBenoemdterrein));
    }

    @Test
    @Transactional
    void createBenoemdterreinWithExistingId() throws Exception {
        // Create the Benoemdterrein with an existing ID
        benoemdterrein.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBenoemdterreinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(benoemdterrein)))
            .andExpect(status().isBadRequest());

        // Validate the Benoemdterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBenoemdterreins() throws Exception {
        // Initialize the database
        benoemdterreinRepository.saveAndFlush(benoemdterrein);

        // Get all the benoemdterreinList
        restBenoemdterreinMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(benoemdterrein.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)));
    }

    @Test
    @Transactional
    void getBenoemdterrein() throws Exception {
        // Initialize the database
        benoemdterreinRepository.saveAndFlush(benoemdterrein);

        // Get the benoemdterrein
        restBenoemdterreinMockMvc
            .perform(get(ENTITY_API_URL_ID, benoemdterrein.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(benoemdterrein.getId().intValue()))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE));
    }

    @Test
    @Transactional
    void getNonExistingBenoemdterrein() throws Exception {
        // Get the benoemdterrein
        restBenoemdterreinMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBenoemdterrein() throws Exception {
        // Initialize the database
        benoemdterreinRepository.saveAndFlush(benoemdterrein);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the benoemdterrein
        Benoemdterrein updatedBenoemdterrein = benoemdterreinRepository.findById(benoemdterrein.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBenoemdterrein are not directly saved in db
        em.detach(updatedBenoemdterrein);
        updatedBenoemdterrein.identificatie(UPDATED_IDENTIFICATIE);

        restBenoemdterreinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBenoemdterrein.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBenoemdterrein))
            )
            .andExpect(status().isOk());

        // Validate the Benoemdterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBenoemdterreinToMatchAllProperties(updatedBenoemdterrein);
    }

    @Test
    @Transactional
    void putNonExistingBenoemdterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        benoemdterrein.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenoemdterreinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, benoemdterrein.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(benoemdterrein))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benoemdterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBenoemdterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        benoemdterrein.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenoemdterreinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(benoemdterrein))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benoemdterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBenoemdterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        benoemdterrein.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenoemdterreinMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(benoemdterrein)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Benoemdterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBenoemdterreinWithPatch() throws Exception {
        // Initialize the database
        benoemdterreinRepository.saveAndFlush(benoemdterrein);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the benoemdterrein using partial update
        Benoemdterrein partialUpdatedBenoemdterrein = new Benoemdterrein();
        partialUpdatedBenoemdterrein.setId(benoemdterrein.getId());

        restBenoemdterreinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenoemdterrein.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBenoemdterrein))
            )
            .andExpect(status().isOk());

        // Validate the Benoemdterrein in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBenoemdterreinUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBenoemdterrein, benoemdterrein),
            getPersistedBenoemdterrein(benoemdterrein)
        );
    }

    @Test
    @Transactional
    void fullUpdateBenoemdterreinWithPatch() throws Exception {
        // Initialize the database
        benoemdterreinRepository.saveAndFlush(benoemdterrein);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the benoemdterrein using partial update
        Benoemdterrein partialUpdatedBenoemdterrein = new Benoemdterrein();
        partialUpdatedBenoemdterrein.setId(benoemdterrein.getId());

        partialUpdatedBenoemdterrein.identificatie(UPDATED_IDENTIFICATIE);

        restBenoemdterreinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenoemdterrein.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBenoemdterrein))
            )
            .andExpect(status().isOk());

        // Validate the Benoemdterrein in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBenoemdterreinUpdatableFieldsEquals(partialUpdatedBenoemdterrein, getPersistedBenoemdterrein(partialUpdatedBenoemdterrein));
    }

    @Test
    @Transactional
    void patchNonExistingBenoemdterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        benoemdterrein.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenoemdterreinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, benoemdterrein.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(benoemdterrein))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benoemdterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBenoemdterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        benoemdterrein.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenoemdterreinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(benoemdterrein))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benoemdterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBenoemdterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        benoemdterrein.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenoemdterreinMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(benoemdterrein)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Benoemdterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBenoemdterrein() throws Exception {
        // Initialize the database
        benoemdterreinRepository.saveAndFlush(benoemdterrein);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the benoemdterrein
        restBenoemdterreinMockMvc
            .perform(delete(ENTITY_API_URL_ID, benoemdterrein.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return benoemdterreinRepository.count();
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

    protected Benoemdterrein getPersistedBenoemdterrein(Benoemdterrein benoemdterrein) {
        return benoemdterreinRepository.findById(benoemdterrein.getId()).orElseThrow();
    }

    protected void assertPersistedBenoemdterreinToMatchAllProperties(Benoemdterrein expectedBenoemdterrein) {
        assertBenoemdterreinAllPropertiesEquals(expectedBenoemdterrein, getPersistedBenoemdterrein(expectedBenoemdterrein));
    }

    protected void assertPersistedBenoemdterreinToMatchUpdatableProperties(Benoemdterrein expectedBenoemdterrein) {
        assertBenoemdterreinAllUpdatablePropertiesEquals(expectedBenoemdterrein, getPersistedBenoemdterrein(expectedBenoemdterrein));
    }
}
