package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ProjectsoortAsserts.*;
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
import nl.ritense.demo.domain.Projectsoort;
import nl.ritense.demo.repository.ProjectsoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProjectsoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectsoortResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/projectsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProjectsoortRepository projectsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectsoortMockMvc;

    private Projectsoort projectsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projectsoort createEntity(EntityManager em) {
        Projectsoort projectsoort = new Projectsoort().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return projectsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projectsoort createUpdatedEntity(EntityManager em) {
        Projectsoort projectsoort = new Projectsoort().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return projectsoort;
    }

    @BeforeEach
    public void initTest() {
        projectsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Projectsoort
        var returnedProjectsoort = om.readValue(
            restProjectsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(projectsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Projectsoort.class
        );

        // Validate the Projectsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProjectsoortUpdatableFieldsEquals(returnedProjectsoort, getPersistedProjectsoort(returnedProjectsoort));
    }

    @Test
    @Transactional
    void createProjectsoortWithExistingId() throws Exception {
        // Create the Projectsoort with an existing ID
        projectsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(projectsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Projectsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectsoorts() throws Exception {
        // Initialize the database
        projectsoortRepository.saveAndFlush(projectsoort);

        // Get all the projectsoortList
        restProjectsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectsoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getProjectsoort() throws Exception {
        // Initialize the database
        projectsoortRepository.saveAndFlush(projectsoort);

        // Get the projectsoort
        restProjectsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, projectsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectsoort.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingProjectsoort() throws Exception {
        // Get the projectsoort
        restProjectsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjectsoort() throws Exception {
        // Initialize the database
        projectsoortRepository.saveAndFlush(projectsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the projectsoort
        Projectsoort updatedProjectsoort = projectsoortRepository.findById(projectsoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProjectsoort are not directly saved in db
        em.detach(updatedProjectsoort);
        updatedProjectsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restProjectsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProjectsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProjectsoort))
            )
            .andExpect(status().isOk());

        // Validate the Projectsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProjectsoortToMatchAllProperties(updatedProjectsoort);
    }

    @Test
    @Transactional
    void putNonExistingProjectsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(projectsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(projectsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(projectsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projectsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectsoortWithPatch() throws Exception {
        // Initialize the database
        projectsoortRepository.saveAndFlush(projectsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the projectsoort using partial update
        Projectsoort partialUpdatedProjectsoort = new Projectsoort();
        partialUpdatedProjectsoort.setId(projectsoort.getId());

        partialUpdatedProjectsoort.omschrijving(UPDATED_OMSCHRIJVING);

        restProjectsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProjectsoort))
            )
            .andExpect(status().isOk());

        // Validate the Projectsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProjectsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProjectsoort, projectsoort),
            getPersistedProjectsoort(projectsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateProjectsoortWithPatch() throws Exception {
        // Initialize the database
        projectsoortRepository.saveAndFlush(projectsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the projectsoort using partial update
        Projectsoort partialUpdatedProjectsoort = new Projectsoort();
        partialUpdatedProjectsoort.setId(projectsoort.getId());

        partialUpdatedProjectsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restProjectsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProjectsoort))
            )
            .andExpect(status().isOk());

        // Validate the Projectsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProjectsoortUpdatableFieldsEquals(partialUpdatedProjectsoort, getPersistedProjectsoort(partialUpdatedProjectsoort));
    }

    @Test
    @Transactional
    void patchNonExistingProjectsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(projectsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(projectsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectsoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(projectsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projectsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectsoort() throws Exception {
        // Initialize the database
        projectsoortRepository.saveAndFlush(projectsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the projectsoort
        restProjectsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return projectsoortRepository.count();
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

    protected Projectsoort getPersistedProjectsoort(Projectsoort projectsoort) {
        return projectsoortRepository.findById(projectsoort.getId()).orElseThrow();
    }

    protected void assertPersistedProjectsoortToMatchAllProperties(Projectsoort expectedProjectsoort) {
        assertProjectsoortAllPropertiesEquals(expectedProjectsoort, getPersistedProjectsoort(expectedProjectsoort));
    }

    protected void assertPersistedProjectsoortToMatchUpdatableProperties(Projectsoort expectedProjectsoort) {
        assertProjectsoortAllUpdatablePropertiesEquals(expectedProjectsoort, getPersistedProjectsoort(expectedProjectsoort));
    }
}
