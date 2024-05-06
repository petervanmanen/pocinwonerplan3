package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NormAsserts.*;
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
import nl.ritense.demo.domain.Norm;
import nl.ritense.demo.repository.NormRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NormResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class NormResourceIT {

    private static final String DEFAULT_NEN_3610_ID = "AAAAAAAAAA";
    private static final String UPDATED_NEN_3610_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/norms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NormRepository normRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNormMockMvc;

    private Norm norm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Norm createEntity(EntityManager em) {
        Norm norm = new Norm().nen3610id(DEFAULT_NEN_3610_ID);
        return norm;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Norm createUpdatedEntity(EntityManager em) {
        Norm norm = new Norm().nen3610id(UPDATED_NEN_3610_ID);
        return norm;
    }

    @BeforeEach
    public void initTest() {
        norm = createEntity(em);
    }

    @Test
    @Transactional
    void createNorm() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Norm
        var returnedNorm = om.readValue(
            restNormMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(norm)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Norm.class
        );

        // Validate the Norm in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNormUpdatableFieldsEquals(returnedNorm, getPersistedNorm(returnedNorm));
    }

    @Test
    @Transactional
    void createNormWithExistingId() throws Exception {
        // Create the Norm with an existing ID
        norm.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNormMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(norm)))
            .andExpect(status().isBadRequest());

        // Validate the Norm in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNorms() throws Exception {
        // Initialize the database
        normRepository.saveAndFlush(norm);

        // Get all the normList
        restNormMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(norm.getId().intValue())))
            .andExpect(jsonPath("$.[*].nen3610id").value(hasItem(DEFAULT_NEN_3610_ID)));
    }

    @Test
    @Transactional
    void getNorm() throws Exception {
        // Initialize the database
        normRepository.saveAndFlush(norm);

        // Get the norm
        restNormMockMvc
            .perform(get(ENTITY_API_URL_ID, norm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(norm.getId().intValue()))
            .andExpect(jsonPath("$.nen3610id").value(DEFAULT_NEN_3610_ID));
    }

    @Test
    @Transactional
    void getNonExistingNorm() throws Exception {
        // Get the norm
        restNormMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNorm() throws Exception {
        // Initialize the database
        normRepository.saveAndFlush(norm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the norm
        Norm updatedNorm = normRepository.findById(norm.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNorm are not directly saved in db
        em.detach(updatedNorm);
        updatedNorm.nen3610id(UPDATED_NEN_3610_ID);

        restNormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNorm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNorm))
            )
            .andExpect(status().isOk());

        // Validate the Norm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNormToMatchAllProperties(updatedNorm);
    }

    @Test
    @Transactional
    void putNonExistingNorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        norm.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNormMockMvc
            .perform(put(ENTITY_API_URL_ID, norm.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(norm)))
            .andExpect(status().isBadRequest());

        // Validate the Norm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        norm.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(norm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Norm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        norm.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNormMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(norm)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Norm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNormWithPatch() throws Exception {
        // Initialize the database
        normRepository.saveAndFlush(norm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the norm using partial update
        Norm partialUpdatedNorm = new Norm();
        partialUpdatedNorm.setId(norm.getId());

        partialUpdatedNorm.nen3610id(UPDATED_NEN_3610_ID);

        restNormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNorm.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNorm))
            )
            .andExpect(status().isOk());

        // Validate the Norm in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNormUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedNorm, norm), getPersistedNorm(norm));
    }

    @Test
    @Transactional
    void fullUpdateNormWithPatch() throws Exception {
        // Initialize the database
        normRepository.saveAndFlush(norm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the norm using partial update
        Norm partialUpdatedNorm = new Norm();
        partialUpdatedNorm.setId(norm.getId());

        partialUpdatedNorm.nen3610id(UPDATED_NEN_3610_ID);

        restNormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNorm.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNorm))
            )
            .andExpect(status().isOk());

        // Validate the Norm in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNormUpdatableFieldsEquals(partialUpdatedNorm, getPersistedNorm(partialUpdatedNorm));
    }

    @Test
    @Transactional
    void patchNonExistingNorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        norm.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNormMockMvc
            .perform(patch(ENTITY_API_URL_ID, norm.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(norm)))
            .andExpect(status().isBadRequest());

        // Validate the Norm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        norm.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(norm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Norm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        norm.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNormMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(norm)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Norm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNorm() throws Exception {
        // Initialize the database
        normRepository.saveAndFlush(norm);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the norm
        restNormMockMvc
            .perform(delete(ENTITY_API_URL_ID, norm.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return normRepository.count();
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

    protected Norm getPersistedNorm(Norm norm) {
        return normRepository.findById(norm.getId()).orElseThrow();
    }

    protected void assertPersistedNormToMatchAllProperties(Norm expectedNorm) {
        assertNormAllPropertiesEquals(expectedNorm, getPersistedNorm(expectedNorm));
    }

    protected void assertPersistedNormToMatchUpdatableProperties(Norm expectedNorm) {
        assertNormAllUpdatablePropertiesEquals(expectedNorm, getPersistedNorm(expectedNorm));
    }
}
