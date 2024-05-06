package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ProgrammasoortAsserts.*;
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
import nl.ritense.demo.domain.Programmasoort;
import nl.ritense.demo.repository.ProgrammasoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProgrammasoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProgrammasoortResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/programmasoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProgrammasoortRepository programmasoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgrammasoortMockMvc;

    private Programmasoort programmasoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programmasoort createEntity(EntityManager em) {
        Programmasoort programmasoort = new Programmasoort().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return programmasoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programmasoort createUpdatedEntity(EntityManager em) {
        Programmasoort programmasoort = new Programmasoort().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return programmasoort;
    }

    @BeforeEach
    public void initTest() {
        programmasoort = createEntity(em);
    }

    @Test
    @Transactional
    void createProgrammasoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Programmasoort
        var returnedProgrammasoort = om.readValue(
            restProgrammasoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programmasoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Programmasoort.class
        );

        // Validate the Programmasoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProgrammasoortUpdatableFieldsEquals(returnedProgrammasoort, getPersistedProgrammasoort(returnedProgrammasoort));
    }

    @Test
    @Transactional
    void createProgrammasoortWithExistingId() throws Exception {
        // Create the Programmasoort with an existing ID
        programmasoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgrammasoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programmasoort)))
            .andExpect(status().isBadRequest());

        // Validate the Programmasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProgrammasoorts() throws Exception {
        // Initialize the database
        programmasoortRepository.saveAndFlush(programmasoort);

        // Get all the programmasoortList
        restProgrammasoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programmasoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getProgrammasoort() throws Exception {
        // Initialize the database
        programmasoortRepository.saveAndFlush(programmasoort);

        // Get the programmasoort
        restProgrammasoortMockMvc
            .perform(get(ENTITY_API_URL_ID, programmasoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(programmasoort.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingProgrammasoort() throws Exception {
        // Get the programmasoort
        restProgrammasoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProgrammasoort() throws Exception {
        // Initialize the database
        programmasoortRepository.saveAndFlush(programmasoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the programmasoort
        Programmasoort updatedProgrammasoort = programmasoortRepository.findById(programmasoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProgrammasoort are not directly saved in db
        em.detach(updatedProgrammasoort);
        updatedProgrammasoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restProgrammasoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProgrammasoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProgrammasoort))
            )
            .andExpect(status().isOk());

        // Validate the Programmasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProgrammasoortToMatchAllProperties(updatedProgrammasoort);
    }

    @Test
    @Transactional
    void putNonExistingProgrammasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programmasoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgrammasoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, programmasoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(programmasoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programmasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProgrammasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programmasoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgrammasoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(programmasoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programmasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProgrammasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programmasoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgrammasoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programmasoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Programmasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProgrammasoortWithPatch() throws Exception {
        // Initialize the database
        programmasoortRepository.saveAndFlush(programmasoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the programmasoort using partial update
        Programmasoort partialUpdatedProgrammasoort = new Programmasoort();
        partialUpdatedProgrammasoort.setId(programmasoort.getId());

        partialUpdatedProgrammasoort.naam(UPDATED_NAAM);

        restProgrammasoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgrammasoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProgrammasoort))
            )
            .andExpect(status().isOk());

        // Validate the Programmasoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProgrammasoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProgrammasoort, programmasoort),
            getPersistedProgrammasoort(programmasoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateProgrammasoortWithPatch() throws Exception {
        // Initialize the database
        programmasoortRepository.saveAndFlush(programmasoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the programmasoort using partial update
        Programmasoort partialUpdatedProgrammasoort = new Programmasoort();
        partialUpdatedProgrammasoort.setId(programmasoort.getId());

        partialUpdatedProgrammasoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restProgrammasoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgrammasoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProgrammasoort))
            )
            .andExpect(status().isOk());

        // Validate the Programmasoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProgrammasoortUpdatableFieldsEquals(partialUpdatedProgrammasoort, getPersistedProgrammasoort(partialUpdatedProgrammasoort));
    }

    @Test
    @Transactional
    void patchNonExistingProgrammasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programmasoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgrammasoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, programmasoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(programmasoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programmasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProgrammasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programmasoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgrammasoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(programmasoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programmasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProgrammasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programmasoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgrammasoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(programmasoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Programmasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProgrammasoort() throws Exception {
        // Initialize the database
        programmasoortRepository.saveAndFlush(programmasoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the programmasoort
        restProgrammasoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, programmasoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return programmasoortRepository.count();
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

    protected Programmasoort getPersistedProgrammasoort(Programmasoort programmasoort) {
        return programmasoortRepository.findById(programmasoort.getId()).orElseThrow();
    }

    protected void assertPersistedProgrammasoortToMatchAllProperties(Programmasoort expectedProgrammasoort) {
        assertProgrammasoortAllPropertiesEquals(expectedProgrammasoort, getPersistedProgrammasoort(expectedProgrammasoort));
    }

    protected void assertPersistedProgrammasoortToMatchUpdatableProperties(Programmasoort expectedProgrammasoort) {
        assertProgrammasoortAllUpdatablePropertiesEquals(expectedProgrammasoort, getPersistedProgrammasoort(expectedProgrammasoort));
    }
}
