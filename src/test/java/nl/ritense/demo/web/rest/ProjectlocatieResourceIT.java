package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ProjectlocatieAsserts.*;
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
import nl.ritense.demo.domain.Projectlocatie;
import nl.ritense.demo.repository.ProjectlocatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProjectlocatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectlocatieResourceIT {

    private static final String DEFAULT_ADRES = "AAAAAAAAAA";
    private static final String UPDATED_ADRES = "BBBBBBBBBB";

    private static final String DEFAULT_KADASTRAALPERCEEL = "AAAAAAAAAA";
    private static final String UPDATED_KADASTRAALPERCEEL = "BBBBBBBBBB";

    private static final String DEFAULT_KADASTRALEGEMEENTE = "AAAAAAAAAA";
    private static final String UPDATED_KADASTRALEGEMEENTE = "BBBBBBBBBB";

    private static final String DEFAULT_KADASTRALESECTIE = "AAAAAAAAAA";
    private static final String UPDATED_KADASTRALESECTIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/projectlocaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProjectlocatieRepository projectlocatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectlocatieMockMvc;

    private Projectlocatie projectlocatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projectlocatie createEntity(EntityManager em) {
        Projectlocatie projectlocatie = new Projectlocatie()
            .adres(DEFAULT_ADRES)
            .kadastraalperceel(DEFAULT_KADASTRAALPERCEEL)
            .kadastralegemeente(DEFAULT_KADASTRALEGEMEENTE)
            .kadastralesectie(DEFAULT_KADASTRALESECTIE);
        return projectlocatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projectlocatie createUpdatedEntity(EntityManager em) {
        Projectlocatie projectlocatie = new Projectlocatie()
            .adres(UPDATED_ADRES)
            .kadastraalperceel(UPDATED_KADASTRAALPERCEEL)
            .kadastralegemeente(UPDATED_KADASTRALEGEMEENTE)
            .kadastralesectie(UPDATED_KADASTRALESECTIE);
        return projectlocatie;
    }

    @BeforeEach
    public void initTest() {
        projectlocatie = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectlocatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Projectlocatie
        var returnedProjectlocatie = om.readValue(
            restProjectlocatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(projectlocatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Projectlocatie.class
        );

        // Validate the Projectlocatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProjectlocatieUpdatableFieldsEquals(returnedProjectlocatie, getPersistedProjectlocatie(returnedProjectlocatie));
    }

    @Test
    @Transactional
    void createProjectlocatieWithExistingId() throws Exception {
        // Create the Projectlocatie with an existing ID
        projectlocatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectlocatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(projectlocatie)))
            .andExpect(status().isBadRequest());

        // Validate the Projectlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectlocaties() throws Exception {
        // Initialize the database
        projectlocatieRepository.saveAndFlush(projectlocatie);

        // Get all the projectlocatieList
        restProjectlocatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectlocatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].adres").value(hasItem(DEFAULT_ADRES)))
            .andExpect(jsonPath("$.[*].kadastraalperceel").value(hasItem(DEFAULT_KADASTRAALPERCEEL)))
            .andExpect(jsonPath("$.[*].kadastralegemeente").value(hasItem(DEFAULT_KADASTRALEGEMEENTE)))
            .andExpect(jsonPath("$.[*].kadastralesectie").value(hasItem(DEFAULT_KADASTRALESECTIE)));
    }

    @Test
    @Transactional
    void getProjectlocatie() throws Exception {
        // Initialize the database
        projectlocatieRepository.saveAndFlush(projectlocatie);

        // Get the projectlocatie
        restProjectlocatieMockMvc
            .perform(get(ENTITY_API_URL_ID, projectlocatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectlocatie.getId().intValue()))
            .andExpect(jsonPath("$.adres").value(DEFAULT_ADRES))
            .andExpect(jsonPath("$.kadastraalperceel").value(DEFAULT_KADASTRAALPERCEEL))
            .andExpect(jsonPath("$.kadastralegemeente").value(DEFAULT_KADASTRALEGEMEENTE))
            .andExpect(jsonPath("$.kadastralesectie").value(DEFAULT_KADASTRALESECTIE));
    }

    @Test
    @Transactional
    void getNonExistingProjectlocatie() throws Exception {
        // Get the projectlocatie
        restProjectlocatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjectlocatie() throws Exception {
        // Initialize the database
        projectlocatieRepository.saveAndFlush(projectlocatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the projectlocatie
        Projectlocatie updatedProjectlocatie = projectlocatieRepository.findById(projectlocatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProjectlocatie are not directly saved in db
        em.detach(updatedProjectlocatie);
        updatedProjectlocatie
            .adres(UPDATED_ADRES)
            .kadastraalperceel(UPDATED_KADASTRAALPERCEEL)
            .kadastralegemeente(UPDATED_KADASTRALEGEMEENTE)
            .kadastralesectie(UPDATED_KADASTRALESECTIE);

        restProjectlocatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProjectlocatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProjectlocatie))
            )
            .andExpect(status().isOk());

        // Validate the Projectlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProjectlocatieToMatchAllProperties(updatedProjectlocatie);
    }

    @Test
    @Transactional
    void putNonExistingProjectlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectlocatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectlocatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectlocatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(projectlocatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectlocatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectlocatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(projectlocatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectlocatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectlocatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(projectlocatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projectlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectlocatieWithPatch() throws Exception {
        // Initialize the database
        projectlocatieRepository.saveAndFlush(projectlocatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the projectlocatie using partial update
        Projectlocatie partialUpdatedProjectlocatie = new Projectlocatie();
        partialUpdatedProjectlocatie.setId(projectlocatie.getId());

        partialUpdatedProjectlocatie.kadastraalperceel(UPDATED_KADASTRAALPERCEEL).kadastralesectie(UPDATED_KADASTRALESECTIE);

        restProjectlocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectlocatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProjectlocatie))
            )
            .andExpect(status().isOk());

        // Validate the Projectlocatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProjectlocatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProjectlocatie, projectlocatie),
            getPersistedProjectlocatie(projectlocatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateProjectlocatieWithPatch() throws Exception {
        // Initialize the database
        projectlocatieRepository.saveAndFlush(projectlocatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the projectlocatie using partial update
        Projectlocatie partialUpdatedProjectlocatie = new Projectlocatie();
        partialUpdatedProjectlocatie.setId(projectlocatie.getId());

        partialUpdatedProjectlocatie
            .adres(UPDATED_ADRES)
            .kadastraalperceel(UPDATED_KADASTRAALPERCEEL)
            .kadastralegemeente(UPDATED_KADASTRALEGEMEENTE)
            .kadastralesectie(UPDATED_KADASTRALESECTIE);

        restProjectlocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectlocatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProjectlocatie))
            )
            .andExpect(status().isOk());

        // Validate the Projectlocatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProjectlocatieUpdatableFieldsEquals(partialUpdatedProjectlocatie, getPersistedProjectlocatie(partialUpdatedProjectlocatie));
    }

    @Test
    @Transactional
    void patchNonExistingProjectlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectlocatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectlocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectlocatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(projectlocatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectlocatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectlocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(projectlocatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectlocatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectlocatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(projectlocatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projectlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectlocatie() throws Exception {
        // Initialize the database
        projectlocatieRepository.saveAndFlush(projectlocatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the projectlocatie
        restProjectlocatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectlocatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return projectlocatieRepository.count();
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

    protected Projectlocatie getPersistedProjectlocatie(Projectlocatie projectlocatie) {
        return projectlocatieRepository.findById(projectlocatie.getId()).orElseThrow();
    }

    protected void assertPersistedProjectlocatieToMatchAllProperties(Projectlocatie expectedProjectlocatie) {
        assertProjectlocatieAllPropertiesEquals(expectedProjectlocatie, getPersistedProjectlocatie(expectedProjectlocatie));
    }

    protected void assertPersistedProjectlocatieToMatchUpdatableProperties(Projectlocatie expectedProjectlocatie) {
        assertProjectlocatieAllUpdatablePropertiesEquals(expectedProjectlocatie, getPersistedProjectlocatie(expectedProjectlocatie));
    }
}
