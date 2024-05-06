package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ProjectactiviteitAsserts.*;
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
import nl.ritense.demo.domain.Projectactiviteit;
import nl.ritense.demo.repository.ProjectactiviteitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProjectactiviteitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectactiviteitResourceIT {

    private static final String ENTITY_API_URL = "/api/projectactiviteits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProjectactiviteitRepository projectactiviteitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectactiviteitMockMvc;

    private Projectactiviteit projectactiviteit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projectactiviteit createEntity(EntityManager em) {
        Projectactiviteit projectactiviteit = new Projectactiviteit();
        return projectactiviteit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projectactiviteit createUpdatedEntity(EntityManager em) {
        Projectactiviteit projectactiviteit = new Projectactiviteit();
        return projectactiviteit;
    }

    @BeforeEach
    public void initTest() {
        projectactiviteit = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectactiviteit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Projectactiviteit
        var returnedProjectactiviteit = om.readValue(
            restProjectactiviteitMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(projectactiviteit)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Projectactiviteit.class
        );

        // Validate the Projectactiviteit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProjectactiviteitUpdatableFieldsEquals(returnedProjectactiviteit, getPersistedProjectactiviteit(returnedProjectactiviteit));
    }

    @Test
    @Transactional
    void createProjectactiviteitWithExistingId() throws Exception {
        // Create the Projectactiviteit with an existing ID
        projectactiviteit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectactiviteitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(projectactiviteit)))
            .andExpect(status().isBadRequest());

        // Validate the Projectactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectactiviteits() throws Exception {
        // Initialize the database
        projectactiviteitRepository.saveAndFlush(projectactiviteit);

        // Get all the projectactiviteitList
        restProjectactiviteitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectactiviteit.getId().intValue())));
    }

    @Test
    @Transactional
    void getProjectactiviteit() throws Exception {
        // Initialize the database
        projectactiviteitRepository.saveAndFlush(projectactiviteit);

        // Get the projectactiviteit
        restProjectactiviteitMockMvc
            .perform(get(ENTITY_API_URL_ID, projectactiviteit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectactiviteit.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingProjectactiviteit() throws Exception {
        // Get the projectactiviteit
        restProjectactiviteitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjectactiviteit() throws Exception {
        // Initialize the database
        projectactiviteitRepository.saveAndFlush(projectactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the projectactiviteit
        Projectactiviteit updatedProjectactiviteit = projectactiviteitRepository.findById(projectactiviteit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProjectactiviteit are not directly saved in db
        em.detach(updatedProjectactiviteit);

        restProjectactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProjectactiviteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProjectactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Projectactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProjectactiviteitToMatchAllProperties(updatedProjectactiviteit);
    }

    @Test
    @Transactional
    void putNonExistingProjectactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectactiviteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectactiviteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(projectactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(projectactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectactiviteitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(projectactiviteit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projectactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectactiviteitWithPatch() throws Exception {
        // Initialize the database
        projectactiviteitRepository.saveAndFlush(projectactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the projectactiviteit using partial update
        Projectactiviteit partialUpdatedProjectactiviteit = new Projectactiviteit();
        partialUpdatedProjectactiviteit.setId(projectactiviteit.getId());

        restProjectactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProjectactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Projectactiviteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProjectactiviteitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProjectactiviteit, projectactiviteit),
            getPersistedProjectactiviteit(projectactiviteit)
        );
    }

    @Test
    @Transactional
    void fullUpdateProjectactiviteitWithPatch() throws Exception {
        // Initialize the database
        projectactiviteitRepository.saveAndFlush(projectactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the projectactiviteit using partial update
        Projectactiviteit partialUpdatedProjectactiviteit = new Projectactiviteit();
        partialUpdatedProjectactiviteit.setId(projectactiviteit.getId());

        restProjectactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProjectactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Projectactiviteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProjectactiviteitUpdatableFieldsEquals(
            partialUpdatedProjectactiviteit,
            getPersistedProjectactiviteit(partialUpdatedProjectactiviteit)
        );
    }

    @Test
    @Transactional
    void patchNonExistingProjectactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectactiviteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(projectactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(projectactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectactiviteitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(projectactiviteit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projectactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectactiviteit() throws Exception {
        // Initialize the database
        projectactiviteitRepository.saveAndFlush(projectactiviteit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the projectactiviteit
        restProjectactiviteitMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectactiviteit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return projectactiviteitRepository.count();
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

    protected Projectactiviteit getPersistedProjectactiviteit(Projectactiviteit projectactiviteit) {
        return projectactiviteitRepository.findById(projectactiviteit.getId()).orElseThrow();
    }

    protected void assertPersistedProjectactiviteitToMatchAllProperties(Projectactiviteit expectedProjectactiviteit) {
        assertProjectactiviteitAllPropertiesEquals(expectedProjectactiviteit, getPersistedProjectactiviteit(expectedProjectactiviteit));
    }

    protected void assertPersistedProjectactiviteitToMatchUpdatableProperties(Projectactiviteit expectedProjectactiviteit) {
        assertProjectactiviteitAllUpdatablePropertiesEquals(
            expectedProjectactiviteit,
            getPersistedProjectactiviteit(expectedProjectactiviteit)
        );
    }
}
