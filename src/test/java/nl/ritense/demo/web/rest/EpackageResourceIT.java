package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EpackageAsserts.*;
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
import nl.ritense.demo.domain.Epackage;
import nl.ritense.demo.repository.EpackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EpackageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EpackageResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_PROCES = "AAAAAAAAAA";
    private static final String UPDATED_PROCES = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/epackages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EpackageRepository epackageRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEpackageMockMvc;

    private Epackage epackage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Epackage createEntity(EntityManager em) {
        Epackage epackage = new Epackage()
            .naam(DEFAULT_NAAM)
            .proces(DEFAULT_PROCES)
            .project(DEFAULT_PROJECT)
            .status(DEFAULT_STATUS)
            .toelichting(DEFAULT_TOELICHTING);
        return epackage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Epackage createUpdatedEntity(EntityManager em) {
        Epackage epackage = new Epackage()
            .naam(UPDATED_NAAM)
            .proces(UPDATED_PROCES)
            .project(UPDATED_PROJECT)
            .status(UPDATED_STATUS)
            .toelichting(UPDATED_TOELICHTING);
        return epackage;
    }

    @BeforeEach
    public void initTest() {
        epackage = createEntity(em);
    }

    @Test
    @Transactional
    void createEpackage() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Epackage
        var returnedEpackage = om.readValue(
            restEpackageMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(epackage)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Epackage.class
        );

        // Validate the Epackage in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEpackageUpdatableFieldsEquals(returnedEpackage, getPersistedEpackage(returnedEpackage));
    }

    @Test
    @Transactional
    void createEpackageWithExistingId() throws Exception {
        // Create the Epackage with an existing ID
        epackage.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEpackageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(epackage)))
            .andExpect(status().isBadRequest());

        // Validate the Epackage in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEpackages() throws Exception {
        // Initialize the database
        epackageRepository.saveAndFlush(epackage);

        // Get all the epackageList
        restEpackageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(epackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].proces").value(hasItem(DEFAULT_PROCES)))
            .andExpect(jsonPath("$.[*].project").value(hasItem(DEFAULT_PROJECT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)));
    }

    @Test
    @Transactional
    void getEpackage() throws Exception {
        // Initialize the database
        epackageRepository.saveAndFlush(epackage);

        // Get the epackage
        restEpackageMockMvc
            .perform(get(ENTITY_API_URL_ID, epackage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(epackage.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.proces").value(DEFAULT_PROCES))
            .andExpect(jsonPath("$.project").value(DEFAULT_PROJECT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING));
    }

    @Test
    @Transactional
    void getNonExistingEpackage() throws Exception {
        // Get the epackage
        restEpackageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEpackage() throws Exception {
        // Initialize the database
        epackageRepository.saveAndFlush(epackage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the epackage
        Epackage updatedEpackage = epackageRepository.findById(epackage.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEpackage are not directly saved in db
        em.detach(updatedEpackage);
        updatedEpackage
            .naam(UPDATED_NAAM)
            .proces(UPDATED_PROCES)
            .project(UPDATED_PROJECT)
            .status(UPDATED_STATUS)
            .toelichting(UPDATED_TOELICHTING);

        restEpackageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEpackage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEpackage))
            )
            .andExpect(status().isOk());

        // Validate the Epackage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEpackageToMatchAllProperties(updatedEpackage);
    }

    @Test
    @Transactional
    void putNonExistingEpackage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        epackage.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEpackageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, epackage.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(epackage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Epackage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEpackage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        epackage.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEpackageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(epackage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Epackage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEpackage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        epackage.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEpackageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(epackage)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Epackage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEpackageWithPatch() throws Exception {
        // Initialize the database
        epackageRepository.saveAndFlush(epackage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the epackage using partial update
        Epackage partialUpdatedEpackage = new Epackage();
        partialUpdatedEpackage.setId(epackage.getId());

        partialUpdatedEpackage.naam(UPDATED_NAAM).proces(UPDATED_PROCES).status(UPDATED_STATUS);

        restEpackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEpackage.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEpackage))
            )
            .andExpect(status().isOk());

        // Validate the Epackage in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEpackageUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEpackage, epackage), getPersistedEpackage(epackage));
    }

    @Test
    @Transactional
    void fullUpdateEpackageWithPatch() throws Exception {
        // Initialize the database
        epackageRepository.saveAndFlush(epackage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the epackage using partial update
        Epackage partialUpdatedEpackage = new Epackage();
        partialUpdatedEpackage.setId(epackage.getId());

        partialUpdatedEpackage
            .naam(UPDATED_NAAM)
            .proces(UPDATED_PROCES)
            .project(UPDATED_PROJECT)
            .status(UPDATED_STATUS)
            .toelichting(UPDATED_TOELICHTING);

        restEpackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEpackage.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEpackage))
            )
            .andExpect(status().isOk());

        // Validate the Epackage in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEpackageUpdatableFieldsEquals(partialUpdatedEpackage, getPersistedEpackage(partialUpdatedEpackage));
    }

    @Test
    @Transactional
    void patchNonExistingEpackage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        epackage.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEpackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, epackage.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(epackage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Epackage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEpackage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        epackage.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEpackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(epackage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Epackage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEpackage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        epackage.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEpackageMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(epackage)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Epackage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEpackage() throws Exception {
        // Initialize the database
        epackageRepository.saveAndFlush(epackage);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the epackage
        restEpackageMockMvc
            .perform(delete(ENTITY_API_URL_ID, epackage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return epackageRepository.count();
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

    protected Epackage getPersistedEpackage(Epackage epackage) {
        return epackageRepository.findById(epackage.getId()).orElseThrow();
    }

    protected void assertPersistedEpackageToMatchAllProperties(Epackage expectedEpackage) {
        assertEpackageAllPropertiesEquals(expectedEpackage, getPersistedEpackage(expectedEpackage));
    }

    protected void assertPersistedEpackageToMatchUpdatableProperties(Epackage expectedEpackage) {
        assertEpackageAllUpdatablePropertiesEquals(expectedEpackage, getPersistedEpackage(expectedEpackage));
    }
}
