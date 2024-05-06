package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OmgevingsnormAsserts.*;
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
import nl.ritense.demo.domain.Omgevingsnorm;
import nl.ritense.demo.repository.OmgevingsnormRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OmgevingsnormResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OmgevingsnormResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMGEVINGSNORMGROEP = "AAAAAAAAAA";
    private static final String UPDATED_OMGEVINGSNORMGROEP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/omgevingsnorms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OmgevingsnormRepository omgevingsnormRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOmgevingsnormMockMvc;

    private Omgevingsnorm omgevingsnorm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Omgevingsnorm createEntity(EntityManager em) {
        Omgevingsnorm omgevingsnorm = new Omgevingsnorm().naam(DEFAULT_NAAM).omgevingsnormgroep(DEFAULT_OMGEVINGSNORMGROEP);
        return omgevingsnorm;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Omgevingsnorm createUpdatedEntity(EntityManager em) {
        Omgevingsnorm omgevingsnorm = new Omgevingsnorm().naam(UPDATED_NAAM).omgevingsnormgroep(UPDATED_OMGEVINGSNORMGROEP);
        return omgevingsnorm;
    }

    @BeforeEach
    public void initTest() {
        omgevingsnorm = createEntity(em);
    }

    @Test
    @Transactional
    void createOmgevingsnorm() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Omgevingsnorm
        var returnedOmgevingsnorm = om.readValue(
            restOmgevingsnormMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omgevingsnorm)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Omgevingsnorm.class
        );

        // Validate the Omgevingsnorm in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOmgevingsnormUpdatableFieldsEquals(returnedOmgevingsnorm, getPersistedOmgevingsnorm(returnedOmgevingsnorm));
    }

    @Test
    @Transactional
    void createOmgevingsnormWithExistingId() throws Exception {
        // Create the Omgevingsnorm with an existing ID
        omgevingsnorm.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOmgevingsnormMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omgevingsnorm)))
            .andExpect(status().isBadRequest());

        // Validate the Omgevingsnorm in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOmgevingsnorms() throws Exception {
        // Initialize the database
        omgevingsnormRepository.saveAndFlush(omgevingsnorm);

        // Get all the omgevingsnormList
        restOmgevingsnormMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(omgevingsnorm.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omgevingsnormgroep").value(hasItem(DEFAULT_OMGEVINGSNORMGROEP)));
    }

    @Test
    @Transactional
    void getOmgevingsnorm() throws Exception {
        // Initialize the database
        omgevingsnormRepository.saveAndFlush(omgevingsnorm);

        // Get the omgevingsnorm
        restOmgevingsnormMockMvc
            .perform(get(ENTITY_API_URL_ID, omgevingsnorm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(omgevingsnorm.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omgevingsnormgroep").value(DEFAULT_OMGEVINGSNORMGROEP));
    }

    @Test
    @Transactional
    void getNonExistingOmgevingsnorm() throws Exception {
        // Get the omgevingsnorm
        restOmgevingsnormMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOmgevingsnorm() throws Exception {
        // Initialize the database
        omgevingsnormRepository.saveAndFlush(omgevingsnorm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the omgevingsnorm
        Omgevingsnorm updatedOmgevingsnorm = omgevingsnormRepository.findById(omgevingsnorm.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOmgevingsnorm are not directly saved in db
        em.detach(updatedOmgevingsnorm);
        updatedOmgevingsnorm.naam(UPDATED_NAAM).omgevingsnormgroep(UPDATED_OMGEVINGSNORMGROEP);

        restOmgevingsnormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOmgevingsnorm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOmgevingsnorm))
            )
            .andExpect(status().isOk());

        // Validate the Omgevingsnorm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOmgevingsnormToMatchAllProperties(updatedOmgevingsnorm);
    }

    @Test
    @Transactional
    void putNonExistingOmgevingsnorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingsnorm.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOmgevingsnormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, omgevingsnorm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(omgevingsnorm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omgevingsnorm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOmgevingsnorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingsnorm.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmgevingsnormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(omgevingsnorm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omgevingsnorm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOmgevingsnorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingsnorm.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmgevingsnormMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omgevingsnorm)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Omgevingsnorm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOmgevingsnormWithPatch() throws Exception {
        // Initialize the database
        omgevingsnormRepository.saveAndFlush(omgevingsnorm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the omgevingsnorm using partial update
        Omgevingsnorm partialUpdatedOmgevingsnorm = new Omgevingsnorm();
        partialUpdatedOmgevingsnorm.setId(omgevingsnorm.getId());

        partialUpdatedOmgevingsnorm.naam(UPDATED_NAAM);

        restOmgevingsnormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOmgevingsnorm.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOmgevingsnorm))
            )
            .andExpect(status().isOk());

        // Validate the Omgevingsnorm in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOmgevingsnormUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOmgevingsnorm, omgevingsnorm),
            getPersistedOmgevingsnorm(omgevingsnorm)
        );
    }

    @Test
    @Transactional
    void fullUpdateOmgevingsnormWithPatch() throws Exception {
        // Initialize the database
        omgevingsnormRepository.saveAndFlush(omgevingsnorm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the omgevingsnorm using partial update
        Omgevingsnorm partialUpdatedOmgevingsnorm = new Omgevingsnorm();
        partialUpdatedOmgevingsnorm.setId(omgevingsnorm.getId());

        partialUpdatedOmgevingsnorm.naam(UPDATED_NAAM).omgevingsnormgroep(UPDATED_OMGEVINGSNORMGROEP);

        restOmgevingsnormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOmgevingsnorm.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOmgevingsnorm))
            )
            .andExpect(status().isOk());

        // Validate the Omgevingsnorm in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOmgevingsnormUpdatableFieldsEquals(partialUpdatedOmgevingsnorm, getPersistedOmgevingsnorm(partialUpdatedOmgevingsnorm));
    }

    @Test
    @Transactional
    void patchNonExistingOmgevingsnorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingsnorm.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOmgevingsnormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, omgevingsnorm.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(omgevingsnorm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omgevingsnorm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOmgevingsnorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingsnorm.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmgevingsnormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(omgevingsnorm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omgevingsnorm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOmgevingsnorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingsnorm.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmgevingsnormMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(omgevingsnorm)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Omgevingsnorm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOmgevingsnorm() throws Exception {
        // Initialize the database
        omgevingsnormRepository.saveAndFlush(omgevingsnorm);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the omgevingsnorm
        restOmgevingsnormMockMvc
            .perform(delete(ENTITY_API_URL_ID, omgevingsnorm.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return omgevingsnormRepository.count();
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

    protected Omgevingsnorm getPersistedOmgevingsnorm(Omgevingsnorm omgevingsnorm) {
        return omgevingsnormRepository.findById(omgevingsnorm.getId()).orElseThrow();
    }

    protected void assertPersistedOmgevingsnormToMatchAllProperties(Omgevingsnorm expectedOmgevingsnorm) {
        assertOmgevingsnormAllPropertiesEquals(expectedOmgevingsnorm, getPersistedOmgevingsnorm(expectedOmgevingsnorm));
    }

    protected void assertPersistedOmgevingsnormToMatchUpdatableProperties(Omgevingsnorm expectedOmgevingsnorm) {
        assertOmgevingsnormAllUpdatablePropertiesEquals(expectedOmgevingsnorm, getPersistedOmgevingsnorm(expectedOmgevingsnorm));
    }
}
