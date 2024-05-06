package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OmzetgroepAsserts.*;
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
import nl.ritense.demo.domain.Omzetgroep;
import nl.ritense.demo.repository.OmzetgroepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OmzetgroepResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OmzetgroepResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/omzetgroeps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OmzetgroepRepository omzetgroepRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOmzetgroepMockMvc;

    private Omzetgroep omzetgroep;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Omzetgroep createEntity(EntityManager em) {
        Omzetgroep omzetgroep = new Omzetgroep().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return omzetgroep;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Omzetgroep createUpdatedEntity(EntityManager em) {
        Omzetgroep omzetgroep = new Omzetgroep().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return omzetgroep;
    }

    @BeforeEach
    public void initTest() {
        omzetgroep = createEntity(em);
    }

    @Test
    @Transactional
    void createOmzetgroep() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Omzetgroep
        var returnedOmzetgroep = om.readValue(
            restOmzetgroepMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omzetgroep)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Omzetgroep.class
        );

        // Validate the Omzetgroep in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOmzetgroepUpdatableFieldsEquals(returnedOmzetgroep, getPersistedOmzetgroep(returnedOmzetgroep));
    }

    @Test
    @Transactional
    void createOmzetgroepWithExistingId() throws Exception {
        // Create the Omzetgroep with an existing ID
        omzetgroep.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOmzetgroepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omzetgroep)))
            .andExpect(status().isBadRequest());

        // Validate the Omzetgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOmzetgroeps() throws Exception {
        // Initialize the database
        omzetgroepRepository.saveAndFlush(omzetgroep);

        // Get all the omzetgroepList
        restOmzetgroepMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(omzetgroep.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getOmzetgroep() throws Exception {
        // Initialize the database
        omzetgroepRepository.saveAndFlush(omzetgroep);

        // Get the omzetgroep
        restOmzetgroepMockMvc
            .perform(get(ENTITY_API_URL_ID, omzetgroep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(omzetgroep.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingOmzetgroep() throws Exception {
        // Get the omzetgroep
        restOmzetgroepMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOmzetgroep() throws Exception {
        // Initialize the database
        omzetgroepRepository.saveAndFlush(omzetgroep);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the omzetgroep
        Omzetgroep updatedOmzetgroep = omzetgroepRepository.findById(omzetgroep.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOmzetgroep are not directly saved in db
        em.detach(updatedOmzetgroep);
        updatedOmzetgroep.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restOmzetgroepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOmzetgroep.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOmzetgroep))
            )
            .andExpect(status().isOk());

        // Validate the Omzetgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOmzetgroepToMatchAllProperties(updatedOmzetgroep);
    }

    @Test
    @Transactional
    void putNonExistingOmzetgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omzetgroep.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOmzetgroepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, omzetgroep.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omzetgroep))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omzetgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOmzetgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omzetgroep.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmzetgroepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(omzetgroep))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omzetgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOmzetgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omzetgroep.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmzetgroepMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omzetgroep)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Omzetgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOmzetgroepWithPatch() throws Exception {
        // Initialize the database
        omzetgroepRepository.saveAndFlush(omzetgroep);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the omzetgroep using partial update
        Omzetgroep partialUpdatedOmzetgroep = new Omzetgroep();
        partialUpdatedOmzetgroep.setId(omzetgroep.getId());

        restOmzetgroepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOmzetgroep.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOmzetgroep))
            )
            .andExpect(status().isOk());

        // Validate the Omzetgroep in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOmzetgroepUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOmzetgroep, omzetgroep),
            getPersistedOmzetgroep(omzetgroep)
        );
    }

    @Test
    @Transactional
    void fullUpdateOmzetgroepWithPatch() throws Exception {
        // Initialize the database
        omzetgroepRepository.saveAndFlush(omzetgroep);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the omzetgroep using partial update
        Omzetgroep partialUpdatedOmzetgroep = new Omzetgroep();
        partialUpdatedOmzetgroep.setId(omzetgroep.getId());

        partialUpdatedOmzetgroep.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restOmzetgroepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOmzetgroep.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOmzetgroep))
            )
            .andExpect(status().isOk());

        // Validate the Omzetgroep in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOmzetgroepUpdatableFieldsEquals(partialUpdatedOmzetgroep, getPersistedOmzetgroep(partialUpdatedOmzetgroep));
    }

    @Test
    @Transactional
    void patchNonExistingOmzetgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omzetgroep.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOmzetgroepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, omzetgroep.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(omzetgroep))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omzetgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOmzetgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omzetgroep.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmzetgroepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(omzetgroep))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omzetgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOmzetgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omzetgroep.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmzetgroepMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(omzetgroep)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Omzetgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOmzetgroep() throws Exception {
        // Initialize the database
        omzetgroepRepository.saveAndFlush(omzetgroep);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the omzetgroep
        restOmzetgroepMockMvc
            .perform(delete(ENTITY_API_URL_ID, omzetgroep.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return omzetgroepRepository.count();
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

    protected Omzetgroep getPersistedOmzetgroep(Omzetgroep omzetgroep) {
        return omzetgroepRepository.findById(omzetgroep.getId()).orElseThrow();
    }

    protected void assertPersistedOmzetgroepToMatchAllProperties(Omzetgroep expectedOmzetgroep) {
        assertOmzetgroepAllPropertiesEquals(expectedOmzetgroep, getPersistedOmzetgroep(expectedOmzetgroep));
    }

    protected void assertPersistedOmzetgroepToMatchUpdatableProperties(Omzetgroep expectedOmzetgroep) {
        assertOmzetgroepAllUpdatablePropertiesEquals(expectedOmzetgroep, getPersistedOmzetgroep(expectedOmzetgroep));
    }
}
