package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ProjectleiderAsserts.*;
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
import nl.ritense.demo.domain.Projectleider;
import nl.ritense.demo.repository.ProjectleiderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProjectleiderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectleiderResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/projectleiders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProjectleiderRepository projectleiderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectleiderMockMvc;

    private Projectleider projectleider;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projectleider createEntity(EntityManager em) {
        Projectleider projectleider = new Projectleider().naam(DEFAULT_NAAM);
        return projectleider;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projectleider createUpdatedEntity(EntityManager em) {
        Projectleider projectleider = new Projectleider().naam(UPDATED_NAAM);
        return projectleider;
    }

    @BeforeEach
    public void initTest() {
        projectleider = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectleider() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Projectleider
        var returnedProjectleider = om.readValue(
            restProjectleiderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(projectleider)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Projectleider.class
        );

        // Validate the Projectleider in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProjectleiderUpdatableFieldsEquals(returnedProjectleider, getPersistedProjectleider(returnedProjectleider));
    }

    @Test
    @Transactional
    void createProjectleiderWithExistingId() throws Exception {
        // Create the Projectleider with an existing ID
        projectleider.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectleiderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(projectleider)))
            .andExpect(status().isBadRequest());

        // Validate the Projectleider in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectleiders() throws Exception {
        // Initialize the database
        projectleiderRepository.saveAndFlush(projectleider);

        // Get all the projectleiderList
        restProjectleiderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectleider.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getProjectleider() throws Exception {
        // Initialize the database
        projectleiderRepository.saveAndFlush(projectleider);

        // Get the projectleider
        restProjectleiderMockMvc
            .perform(get(ENTITY_API_URL_ID, projectleider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectleider.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingProjectleider() throws Exception {
        // Get the projectleider
        restProjectleiderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjectleider() throws Exception {
        // Initialize the database
        projectleiderRepository.saveAndFlush(projectleider);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the projectleider
        Projectleider updatedProjectleider = projectleiderRepository.findById(projectleider.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProjectleider are not directly saved in db
        em.detach(updatedProjectleider);
        updatedProjectleider.naam(UPDATED_NAAM);

        restProjectleiderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProjectleider.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProjectleider))
            )
            .andExpect(status().isOk());

        // Validate the Projectleider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProjectleiderToMatchAllProperties(updatedProjectleider);
    }

    @Test
    @Transactional
    void putNonExistingProjectleider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectleider.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectleiderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectleider.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(projectleider))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectleider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectleider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectleider.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectleiderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(projectleider))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectleider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectleider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectleider.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectleiderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(projectleider)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projectleider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectleiderWithPatch() throws Exception {
        // Initialize the database
        projectleiderRepository.saveAndFlush(projectleider);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the projectleider using partial update
        Projectleider partialUpdatedProjectleider = new Projectleider();
        partialUpdatedProjectleider.setId(projectleider.getId());

        partialUpdatedProjectleider.naam(UPDATED_NAAM);

        restProjectleiderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectleider.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProjectleider))
            )
            .andExpect(status().isOk());

        // Validate the Projectleider in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProjectleiderUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProjectleider, projectleider),
            getPersistedProjectleider(projectleider)
        );
    }

    @Test
    @Transactional
    void fullUpdateProjectleiderWithPatch() throws Exception {
        // Initialize the database
        projectleiderRepository.saveAndFlush(projectleider);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the projectleider using partial update
        Projectleider partialUpdatedProjectleider = new Projectleider();
        partialUpdatedProjectleider.setId(projectleider.getId());

        partialUpdatedProjectleider.naam(UPDATED_NAAM);

        restProjectleiderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectleider.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProjectleider))
            )
            .andExpect(status().isOk());

        // Validate the Projectleider in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProjectleiderUpdatableFieldsEquals(partialUpdatedProjectleider, getPersistedProjectleider(partialUpdatedProjectleider));
    }

    @Test
    @Transactional
    void patchNonExistingProjectleider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectleider.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectleiderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectleider.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(projectleider))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectleider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectleider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectleider.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectleiderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(projectleider))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectleider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectleider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectleider.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectleiderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(projectleider)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projectleider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectleider() throws Exception {
        // Initialize the database
        projectleiderRepository.saveAndFlush(projectleider);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the projectleider
        restProjectleiderMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectleider.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return projectleiderRepository.count();
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

    protected Projectleider getPersistedProjectleider(Projectleider projectleider) {
        return projectleiderRepository.findById(projectleider.getId()).orElseThrow();
    }

    protected void assertPersistedProjectleiderToMatchAllProperties(Projectleider expectedProjectleider) {
        assertProjectleiderAllPropertiesEquals(expectedProjectleider, getPersistedProjectleider(expectedProjectleider));
    }

    protected void assertPersistedProjectleiderToMatchUpdatableProperties(Projectleider expectedProjectleider) {
        assertProjectleiderAllUpdatablePropertiesEquals(expectedProjectleider, getPersistedProjectleider(expectedProjectleider));
    }
}
