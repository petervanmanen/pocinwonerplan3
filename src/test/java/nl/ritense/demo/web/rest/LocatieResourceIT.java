package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LocatieAsserts.*;
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
import nl.ritense.demo.domain.Gebiedsaanwijzing;
import nl.ritense.demo.domain.Locatie;
import nl.ritense.demo.domain.Project;
import nl.ritense.demo.domain.Put;
import nl.ritense.demo.repository.LocatieRepository;
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
 * Integration tests for the {@link LocatieResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class LocatieResourceIT {

    private static final String DEFAULT_ADRES = "AAAAAAAAAA";
    private static final String UPDATED_ADRES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/locaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LocatieRepository locatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocatieMockMvc;

    private Locatie locatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locatie createEntity(EntityManager em) {
        Locatie locatie = new Locatie().adres(DEFAULT_ADRES);
        // Add required entity
        Put put;
        if (TestUtil.findAll(em, Put.class).isEmpty()) {
            put = PutResourceIT.createEntity(em);
            em.persist(put);
            em.flush();
        } else {
            put = TestUtil.findAll(em, Put.class).get(0);
        }
        locatie.getHeeftlocatiePuts().add(put);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        locatie.getWordtbegrensddoorProjects().add(project);
        // Add required entity
        Gebiedsaanwijzing gebiedsaanwijzing;
        if (TestUtil.findAll(em, Gebiedsaanwijzing.class).isEmpty()) {
            gebiedsaanwijzing = GebiedsaanwijzingResourceIT.createEntity(em);
            em.persist(gebiedsaanwijzing);
            em.flush();
        } else {
            gebiedsaanwijzing = TestUtil.findAll(em, Gebiedsaanwijzing.class).get(0);
        }
        locatie.getVerwijstnaarGebiedsaanwijzings().add(gebiedsaanwijzing);
        return locatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locatie createUpdatedEntity(EntityManager em) {
        Locatie locatie = new Locatie().adres(UPDATED_ADRES);
        // Add required entity
        Put put;
        if (TestUtil.findAll(em, Put.class).isEmpty()) {
            put = PutResourceIT.createUpdatedEntity(em);
            em.persist(put);
            em.flush();
        } else {
            put = TestUtil.findAll(em, Put.class).get(0);
        }
        locatie.getHeeftlocatiePuts().add(put);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createUpdatedEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        locatie.getWordtbegrensddoorProjects().add(project);
        // Add required entity
        Gebiedsaanwijzing gebiedsaanwijzing;
        if (TestUtil.findAll(em, Gebiedsaanwijzing.class).isEmpty()) {
            gebiedsaanwijzing = GebiedsaanwijzingResourceIT.createUpdatedEntity(em);
            em.persist(gebiedsaanwijzing);
            em.flush();
        } else {
            gebiedsaanwijzing = TestUtil.findAll(em, Gebiedsaanwijzing.class).get(0);
        }
        locatie.getVerwijstnaarGebiedsaanwijzings().add(gebiedsaanwijzing);
        return locatie;
    }

    @BeforeEach
    public void initTest() {
        locatie = createEntity(em);
    }

    @Test
    @Transactional
    void createLocatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Locatie
        var returnedLocatie = om.readValue(
            restLocatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Locatie.class
        );

        // Validate the Locatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLocatieUpdatableFieldsEquals(returnedLocatie, getPersistedLocatie(returnedLocatie));
    }

    @Test
    @Transactional
    void createLocatieWithExistingId() throws Exception {
        // Create the Locatie with an existing ID
        locatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locatie)))
            .andExpect(status().isBadRequest());

        // Validate the Locatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLocaties() throws Exception {
        // Initialize the database
        locatieRepository.saveAndFlush(locatie);

        // Get all the locatieList
        restLocatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].adres").value(hasItem(DEFAULT_ADRES)));
    }

    @Test
    @Transactional
    void getLocatie() throws Exception {
        // Initialize the database
        locatieRepository.saveAndFlush(locatie);

        // Get the locatie
        restLocatieMockMvc
            .perform(get(ENTITY_API_URL_ID, locatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locatie.getId().intValue()))
            .andExpect(jsonPath("$.adres").value(DEFAULT_ADRES));
    }

    @Test
    @Transactional
    void getNonExistingLocatie() throws Exception {
        // Get the locatie
        restLocatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocatie() throws Exception {
        // Initialize the database
        locatieRepository.saveAndFlush(locatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locatie
        Locatie updatedLocatie = locatieRepository.findById(locatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLocatie are not directly saved in db
        em.detach(updatedLocatie);
        updatedLocatie.adres(UPDATED_ADRES);

        restLocatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLocatie))
            )
            .andExpect(status().isOk());

        // Validate the Locatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLocatieToMatchAllProperties(updatedLocatie);
    }

    @Test
    @Transactional
    void putNonExistingLocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocatieMockMvc
            .perform(put(ENTITY_API_URL_ID, locatie.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locatie)))
            .andExpect(status().isBadRequest());

        // Validate the Locatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocatieWithPatch() throws Exception {
        // Initialize the database
        locatieRepository.saveAndFlush(locatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locatie using partial update
        Locatie partialUpdatedLocatie = new Locatie();
        partialUpdatedLocatie.setId(locatie.getId());

        partialUpdatedLocatie.adres(UPDATED_ADRES);

        restLocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocatie))
            )
            .andExpect(status().isOk());

        // Validate the Locatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocatieUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLocatie, locatie), getPersistedLocatie(locatie));
    }

    @Test
    @Transactional
    void fullUpdateLocatieWithPatch() throws Exception {
        // Initialize the database
        locatieRepository.saveAndFlush(locatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locatie using partial update
        Locatie partialUpdatedLocatie = new Locatie();
        partialUpdatedLocatie.setId(locatie.getId());

        partialUpdatedLocatie.adres(UPDATED_ADRES);

        restLocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocatie))
            )
            .andExpect(status().isOk());

        // Validate the Locatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocatieUpdatableFieldsEquals(partialUpdatedLocatie, getPersistedLocatie(partialUpdatedLocatie));
    }

    @Test
    @Transactional
    void patchNonExistingLocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locatie.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(locatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(locatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocatie() throws Exception {
        // Initialize the database
        locatieRepository.saveAndFlush(locatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the locatie
        restLocatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, locatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return locatieRepository.count();
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

    protected Locatie getPersistedLocatie(Locatie locatie) {
        return locatieRepository.findById(locatie.getId()).orElseThrow();
    }

    protected void assertPersistedLocatieToMatchAllProperties(Locatie expectedLocatie) {
        assertLocatieAllPropertiesEquals(expectedLocatie, getPersistedLocatie(expectedLocatie));
    }

    protected void assertPersistedLocatieToMatchUpdatableProperties(Locatie expectedLocatie) {
        assertLocatieAllUpdatablePropertiesEquals(expectedLocatie, getPersistedLocatie(expectedLocatie));
    }
}
