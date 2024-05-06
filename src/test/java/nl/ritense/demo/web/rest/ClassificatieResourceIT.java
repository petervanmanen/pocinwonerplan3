package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ClassificatieAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Classificatie;
import nl.ritense.demo.repository.ClassificatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClassificatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClassificatieResourceIT {

    private static final String DEFAULT_BEVATPERSOONSGEGEVENS = "AAAAAAAAAA";
    private static final String UPDATED_BEVATPERSOONSGEGEVENS = "BBBBBBBBBB";

    private static final String DEFAULT_GERELATEERDPERSOONSGEGEVENS = "AAAAAAAAAA";
    private static final String UPDATED_GERELATEERDPERSOONSGEGEVENS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/classificaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ClassificatieRepository classificatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassificatieMockMvc;

    private Classificatie classificatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classificatie createEntity(EntityManager em) {
        Classificatie classificatie = new Classificatie()
            .bevatpersoonsgegevens(DEFAULT_BEVATPERSOONSGEGEVENS)
            .gerelateerdpersoonsgegevens(DEFAULT_GERELATEERDPERSOONSGEGEVENS);
        return classificatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classificatie createUpdatedEntity(EntityManager em) {
        Classificatie classificatie = new Classificatie()
            .bevatpersoonsgegevens(UPDATED_BEVATPERSOONSGEGEVENS)
            .gerelateerdpersoonsgegevens(UPDATED_GERELATEERDPERSOONSGEGEVENS);
        return classificatie;
    }

    @BeforeEach
    public void initTest() {
        classificatie = createEntity(em);
    }

    @Test
    @Transactional
    void createClassificatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Classificatie
        var returnedClassificatie = om.readValue(
            restClassificatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classificatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Classificatie.class
        );

        // Validate the Classificatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertClassificatieUpdatableFieldsEquals(returnedClassificatie, getPersistedClassificatie(returnedClassificatie));
    }

    @Test
    @Transactional
    void createClassificatieWithExistingId() throws Exception {
        // Create the Classificatie with an existing ID
        classificatie.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassificatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classificatie)))
            .andExpect(status().isBadRequest());

        // Validate the Classificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClassificaties() throws Exception {
        // Initialize the database
        classificatieRepository.saveAndFlush(classificatie);

        // Get all the classificatieList
        restClassificatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classificatie.getId())))
            .andExpect(jsonPath("$.[*].bevatpersoonsgegevens").value(hasItem(DEFAULT_BEVATPERSOONSGEGEVENS)))
            .andExpect(jsonPath("$.[*].gerelateerdpersoonsgegevens").value(hasItem(DEFAULT_GERELATEERDPERSOONSGEGEVENS)));
    }

    @Test
    @Transactional
    void getClassificatie() throws Exception {
        // Initialize the database
        classificatieRepository.saveAndFlush(classificatie);

        // Get the classificatie
        restClassificatieMockMvc
            .perform(get(ENTITY_API_URL_ID, classificatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classificatie.getId()))
            .andExpect(jsonPath("$.bevatpersoonsgegevens").value(DEFAULT_BEVATPERSOONSGEGEVENS))
            .andExpect(jsonPath("$.gerelateerdpersoonsgegevens").value(DEFAULT_GERELATEERDPERSOONSGEGEVENS));
    }

    @Test
    @Transactional
    void getNonExistingClassificatie() throws Exception {
        // Get the classificatie
        restClassificatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClassificatie() throws Exception {
        // Initialize the database
        classificatieRepository.saveAndFlush(classificatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classificatie
        Classificatie updatedClassificatie = classificatieRepository.findById(classificatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedClassificatie are not directly saved in db
        em.detach(updatedClassificatie);
        updatedClassificatie
            .bevatpersoonsgegevens(UPDATED_BEVATPERSOONSGEGEVENS)
            .gerelateerdpersoonsgegevens(UPDATED_GERELATEERDPERSOONSGEGEVENS);

        restClassificatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClassificatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedClassificatie))
            )
            .andExpect(status().isOk());

        // Validate the Classificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedClassificatieToMatchAllProperties(updatedClassificatie);
    }

    @Test
    @Transactional
    void putNonExistingClassificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classificatie.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassificatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classificatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(classificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClassificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classificatie.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassificatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(classificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClassificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classificatie.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassificatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classificatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Classificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClassificatieWithPatch() throws Exception {
        // Initialize the database
        classificatieRepository.saveAndFlush(classificatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classificatie using partial update
        Classificatie partialUpdatedClassificatie = new Classificatie();
        partialUpdatedClassificatie.setId(classificatie.getId());

        partialUpdatedClassificatie.gerelateerdpersoonsgegevens(UPDATED_GERELATEERDPERSOONSGEGEVENS);

        restClassificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassificatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClassificatie))
            )
            .andExpect(status().isOk());

        // Validate the Classificatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClassificatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedClassificatie, classificatie),
            getPersistedClassificatie(classificatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateClassificatieWithPatch() throws Exception {
        // Initialize the database
        classificatieRepository.saveAndFlush(classificatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classificatie using partial update
        Classificatie partialUpdatedClassificatie = new Classificatie();
        partialUpdatedClassificatie.setId(classificatie.getId());

        partialUpdatedClassificatie
            .bevatpersoonsgegevens(UPDATED_BEVATPERSOONSGEGEVENS)
            .gerelateerdpersoonsgegevens(UPDATED_GERELATEERDPERSOONSGEGEVENS);

        restClassificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassificatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClassificatie))
            )
            .andExpect(status().isOk());

        // Validate the Classificatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClassificatieUpdatableFieldsEquals(partialUpdatedClassificatie, getPersistedClassificatie(partialUpdatedClassificatie));
    }

    @Test
    @Transactional
    void patchNonExistingClassificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classificatie.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, classificatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(classificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClassificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classificatie.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(classificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClassificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classificatie.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassificatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(classificatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Classificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClassificatie() throws Exception {
        // Initialize the database
        classificatieRepository.saveAndFlush(classificatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the classificatie
        restClassificatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, classificatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return classificatieRepository.count();
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

    protected Classificatie getPersistedClassificatie(Classificatie classificatie) {
        return classificatieRepository.findById(classificatie.getId()).orElseThrow();
    }

    protected void assertPersistedClassificatieToMatchAllProperties(Classificatie expectedClassificatie) {
        assertClassificatieAllPropertiesEquals(expectedClassificatie, getPersistedClassificatie(expectedClassificatie));
    }

    protected void assertPersistedClassificatieToMatchUpdatableProperties(Classificatie expectedClassificatie) {
        assertClassificatieAllUpdatablePropertiesEquals(expectedClassificatie, getPersistedClassificatie(expectedClassificatie));
    }
}
