package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DoelgroepAsserts.*;
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
import nl.ritense.demo.domain.Doelgroep;
import nl.ritense.demo.repository.DoelgroepRepository;
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
 * Integration tests for the {@link DoelgroepResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class DoelgroepResourceIT {

    private static final String DEFAULT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_SEGMENT = "AAAAAAAAAA";
    private static final String UPDATED_SEGMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/doelgroeps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DoelgroepRepository doelgroepRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoelgroepMockMvc;

    private Doelgroep doelgroep;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doelgroep createEntity(EntityManager em) {
        Doelgroep doelgroep = new Doelgroep()
            .branch(DEFAULT_BRANCH)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .segment(DEFAULT_SEGMENT);
        return doelgroep;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doelgroep createUpdatedEntity(EntityManager em) {
        Doelgroep doelgroep = new Doelgroep()
            .branch(UPDATED_BRANCH)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .segment(UPDATED_SEGMENT);
        return doelgroep;
    }

    @BeforeEach
    public void initTest() {
        doelgroep = createEntity(em);
    }

    @Test
    @Transactional
    void createDoelgroep() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Doelgroep
        var returnedDoelgroep = om.readValue(
            restDoelgroepMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doelgroep)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Doelgroep.class
        );

        // Validate the Doelgroep in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDoelgroepUpdatableFieldsEquals(returnedDoelgroep, getPersistedDoelgroep(returnedDoelgroep));
    }

    @Test
    @Transactional
    void createDoelgroepWithExistingId() throws Exception {
        // Create the Doelgroep with an existing ID
        doelgroep.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoelgroepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doelgroep)))
            .andExpect(status().isBadRequest());

        // Validate the Doelgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDoelgroeps() throws Exception {
        // Initialize the database
        doelgroepRepository.saveAndFlush(doelgroep);

        // Get all the doelgroepList
        restDoelgroepMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doelgroep.getId().intValue())))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].segment").value(hasItem(DEFAULT_SEGMENT)));
    }

    @Test
    @Transactional
    void getDoelgroep() throws Exception {
        // Initialize the database
        doelgroepRepository.saveAndFlush(doelgroep);

        // Get the doelgroep
        restDoelgroepMockMvc
            .perform(get(ENTITY_API_URL_ID, doelgroep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doelgroep.getId().intValue()))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.segment").value(DEFAULT_SEGMENT));
    }

    @Test
    @Transactional
    void getNonExistingDoelgroep() throws Exception {
        // Get the doelgroep
        restDoelgroepMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDoelgroep() throws Exception {
        // Initialize the database
        doelgroepRepository.saveAndFlush(doelgroep);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doelgroep
        Doelgroep updatedDoelgroep = doelgroepRepository.findById(doelgroep.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDoelgroep are not directly saved in db
        em.detach(updatedDoelgroep);
        updatedDoelgroep.branch(UPDATED_BRANCH).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING).segment(UPDATED_SEGMENT);

        restDoelgroepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDoelgroep.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDoelgroep))
            )
            .andExpect(status().isOk());

        // Validate the Doelgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDoelgroepToMatchAllProperties(updatedDoelgroep);
    }

    @Test
    @Transactional
    void putNonExistingDoelgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelgroep.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoelgroepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doelgroep.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doelgroep))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doelgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDoelgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelgroep.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoelgroepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(doelgroep))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doelgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDoelgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelgroep.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoelgroepMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doelgroep)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doelgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDoelgroepWithPatch() throws Exception {
        // Initialize the database
        doelgroepRepository.saveAndFlush(doelgroep);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doelgroep using partial update
        Doelgroep partialUpdatedDoelgroep = new Doelgroep();
        partialUpdatedDoelgroep.setId(doelgroep.getId());

        partialUpdatedDoelgroep.branch(UPDATED_BRANCH).naam(UPDATED_NAAM).segment(UPDATED_SEGMENT);

        restDoelgroepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoelgroep.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDoelgroep))
            )
            .andExpect(status().isOk());

        // Validate the Doelgroep in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDoelgroepUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDoelgroep, doelgroep),
            getPersistedDoelgroep(doelgroep)
        );
    }

    @Test
    @Transactional
    void fullUpdateDoelgroepWithPatch() throws Exception {
        // Initialize the database
        doelgroepRepository.saveAndFlush(doelgroep);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doelgroep using partial update
        Doelgroep partialUpdatedDoelgroep = new Doelgroep();
        partialUpdatedDoelgroep.setId(doelgroep.getId());

        partialUpdatedDoelgroep.branch(UPDATED_BRANCH).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING).segment(UPDATED_SEGMENT);

        restDoelgroepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoelgroep.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDoelgroep))
            )
            .andExpect(status().isOk());

        // Validate the Doelgroep in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDoelgroepUpdatableFieldsEquals(partialUpdatedDoelgroep, getPersistedDoelgroep(partialUpdatedDoelgroep));
    }

    @Test
    @Transactional
    void patchNonExistingDoelgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelgroep.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoelgroepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, doelgroep.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(doelgroep))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doelgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDoelgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelgroep.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoelgroepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(doelgroep))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doelgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDoelgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelgroep.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoelgroepMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(doelgroep)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doelgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDoelgroep() throws Exception {
        // Initialize the database
        doelgroepRepository.saveAndFlush(doelgroep);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the doelgroep
        restDoelgroepMockMvc
            .perform(delete(ENTITY_API_URL_ID, doelgroep.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return doelgroepRepository.count();
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

    protected Doelgroep getPersistedDoelgroep(Doelgroep doelgroep) {
        return doelgroepRepository.findById(doelgroep.getId()).orElseThrow();
    }

    protected void assertPersistedDoelgroepToMatchAllProperties(Doelgroep expectedDoelgroep) {
        assertDoelgroepAllPropertiesEquals(expectedDoelgroep, getPersistedDoelgroep(expectedDoelgroep));
    }

    protected void assertPersistedDoelgroepToMatchUpdatableProperties(Doelgroep expectedDoelgroep) {
        assertDoelgroepAllUpdatablePropertiesEquals(expectedDoelgroep, getPersistedDoelgroep(expectedDoelgroep));
    }
}
