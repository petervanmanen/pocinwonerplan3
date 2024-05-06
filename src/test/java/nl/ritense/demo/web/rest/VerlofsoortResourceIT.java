package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerlofsoortAsserts.*;
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
import nl.ritense.demo.domain.Verlofsoort;
import nl.ritense.demo.repository.VerlofsoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerlofsoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerlofsoortResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verlofsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerlofsoortRepository verlofsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerlofsoortMockMvc;

    private Verlofsoort verlofsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verlofsoort createEntity(EntityManager em) {
        Verlofsoort verlofsoort = new Verlofsoort().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return verlofsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verlofsoort createUpdatedEntity(EntityManager em) {
        Verlofsoort verlofsoort = new Verlofsoort().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return verlofsoort;
    }

    @BeforeEach
    public void initTest() {
        verlofsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createVerlofsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verlofsoort
        var returnedVerlofsoort = om.readValue(
            restVerlofsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verlofsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verlofsoort.class
        );

        // Validate the Verlofsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerlofsoortUpdatableFieldsEquals(returnedVerlofsoort, getPersistedVerlofsoort(returnedVerlofsoort));
    }

    @Test
    @Transactional
    void createVerlofsoortWithExistingId() throws Exception {
        // Create the Verlofsoort with an existing ID
        verlofsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerlofsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verlofsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Verlofsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerlofsoorts() throws Exception {
        // Initialize the database
        verlofsoortRepository.saveAndFlush(verlofsoort);

        // Get all the verlofsoortList
        restVerlofsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verlofsoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getVerlofsoort() throws Exception {
        // Initialize the database
        verlofsoortRepository.saveAndFlush(verlofsoort);

        // Get the verlofsoort
        restVerlofsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, verlofsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verlofsoort.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingVerlofsoort() throws Exception {
        // Get the verlofsoort
        restVerlofsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerlofsoort() throws Exception {
        // Initialize the database
        verlofsoortRepository.saveAndFlush(verlofsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verlofsoort
        Verlofsoort updatedVerlofsoort = verlofsoortRepository.findById(verlofsoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerlofsoort are not directly saved in db
        em.detach(updatedVerlofsoort);
        updatedVerlofsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restVerlofsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerlofsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerlofsoort))
            )
            .andExpect(status().isOk());

        // Validate the Verlofsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerlofsoortToMatchAllProperties(updatedVerlofsoort);
    }

    @Test
    @Transactional
    void putNonExistingVerlofsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlofsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerlofsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verlofsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verlofsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verlofsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerlofsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlofsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerlofsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verlofsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verlofsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerlofsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlofsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerlofsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verlofsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verlofsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerlofsoortWithPatch() throws Exception {
        // Initialize the database
        verlofsoortRepository.saveAndFlush(verlofsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verlofsoort using partial update
        Verlofsoort partialUpdatedVerlofsoort = new Verlofsoort();
        partialUpdatedVerlofsoort.setId(verlofsoort.getId());

        partialUpdatedVerlofsoort.omschrijving(UPDATED_OMSCHRIJVING);

        restVerlofsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerlofsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerlofsoort))
            )
            .andExpect(status().isOk());

        // Validate the Verlofsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerlofsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerlofsoort, verlofsoort),
            getPersistedVerlofsoort(verlofsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerlofsoortWithPatch() throws Exception {
        // Initialize the database
        verlofsoortRepository.saveAndFlush(verlofsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verlofsoort using partial update
        Verlofsoort partialUpdatedVerlofsoort = new Verlofsoort();
        partialUpdatedVerlofsoort.setId(verlofsoort.getId());

        partialUpdatedVerlofsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restVerlofsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerlofsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerlofsoort))
            )
            .andExpect(status().isOk());

        // Validate the Verlofsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerlofsoortUpdatableFieldsEquals(partialUpdatedVerlofsoort, getPersistedVerlofsoort(partialUpdatedVerlofsoort));
    }

    @Test
    @Transactional
    void patchNonExistingVerlofsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlofsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerlofsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verlofsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verlofsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verlofsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerlofsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlofsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerlofsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verlofsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verlofsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerlofsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlofsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerlofsoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verlofsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verlofsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerlofsoort() throws Exception {
        // Initialize the database
        verlofsoortRepository.saveAndFlush(verlofsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verlofsoort
        restVerlofsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, verlofsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verlofsoortRepository.count();
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

    protected Verlofsoort getPersistedVerlofsoort(Verlofsoort verlofsoort) {
        return verlofsoortRepository.findById(verlofsoort.getId()).orElseThrow();
    }

    protected void assertPersistedVerlofsoortToMatchAllProperties(Verlofsoort expectedVerlofsoort) {
        assertVerlofsoortAllPropertiesEquals(expectedVerlofsoort, getPersistedVerlofsoort(expectedVerlofsoort));
    }

    protected void assertPersistedVerlofsoortToMatchUpdatableProperties(Verlofsoort expectedVerlofsoort) {
        assertVerlofsoortAllUpdatablePropertiesEquals(expectedVerlofsoort, getPersistedVerlofsoort(expectedVerlofsoort));
    }
}
