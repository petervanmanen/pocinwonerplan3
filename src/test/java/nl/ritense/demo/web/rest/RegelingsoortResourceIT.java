package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RegelingsoortAsserts.*;
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
import nl.ritense.demo.domain.Regelingsoort;
import nl.ritense.demo.repository.RegelingsoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RegelingsoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RegelingsoortResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/regelingsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RegelingsoortRepository regelingsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegelingsoortMockMvc;

    private Regelingsoort regelingsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regelingsoort createEntity(EntityManager em) {
        Regelingsoort regelingsoort = new Regelingsoort().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return regelingsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regelingsoort createUpdatedEntity(EntityManager em) {
        Regelingsoort regelingsoort = new Regelingsoort().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return regelingsoort;
    }

    @BeforeEach
    public void initTest() {
        regelingsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createRegelingsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Regelingsoort
        var returnedRegelingsoort = om.readValue(
            restRegelingsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regelingsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Regelingsoort.class
        );

        // Validate the Regelingsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRegelingsoortUpdatableFieldsEquals(returnedRegelingsoort, getPersistedRegelingsoort(returnedRegelingsoort));
    }

    @Test
    @Transactional
    void createRegelingsoortWithExistingId() throws Exception {
        // Create the Regelingsoort with an existing ID
        regelingsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegelingsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regelingsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Regelingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRegelingsoorts() throws Exception {
        // Initialize the database
        regelingsoortRepository.saveAndFlush(regelingsoort);

        // Get all the regelingsoortList
        restRegelingsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regelingsoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getRegelingsoort() throws Exception {
        // Initialize the database
        regelingsoortRepository.saveAndFlush(regelingsoort);

        // Get the regelingsoort
        restRegelingsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, regelingsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regelingsoort.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingRegelingsoort() throws Exception {
        // Get the regelingsoort
        restRegelingsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRegelingsoort() throws Exception {
        // Initialize the database
        regelingsoortRepository.saveAndFlush(regelingsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the regelingsoort
        Regelingsoort updatedRegelingsoort = regelingsoortRepository.findById(regelingsoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRegelingsoort are not directly saved in db
        em.detach(updatedRegelingsoort);
        updatedRegelingsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restRegelingsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRegelingsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRegelingsoort))
            )
            .andExpect(status().isOk());

        // Validate the Regelingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRegelingsoortToMatchAllProperties(updatedRegelingsoort);
    }

    @Test
    @Transactional
    void putNonExistingRegelingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regelingsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegelingsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regelingsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(regelingsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regelingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegelingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regelingsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegelingsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(regelingsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regelingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegelingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regelingsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegelingsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regelingsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Regelingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegelingsoortWithPatch() throws Exception {
        // Initialize the database
        regelingsoortRepository.saveAndFlush(regelingsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the regelingsoort using partial update
        Regelingsoort partialUpdatedRegelingsoort = new Regelingsoort();
        partialUpdatedRegelingsoort.setId(regelingsoort.getId());

        partialUpdatedRegelingsoort.omschrijving(UPDATED_OMSCHRIJVING);

        restRegelingsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegelingsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRegelingsoort))
            )
            .andExpect(status().isOk());

        // Validate the Regelingsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRegelingsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRegelingsoort, regelingsoort),
            getPersistedRegelingsoort(regelingsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateRegelingsoortWithPatch() throws Exception {
        // Initialize the database
        regelingsoortRepository.saveAndFlush(regelingsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the regelingsoort using partial update
        Regelingsoort partialUpdatedRegelingsoort = new Regelingsoort();
        partialUpdatedRegelingsoort.setId(regelingsoort.getId());

        partialUpdatedRegelingsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restRegelingsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegelingsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRegelingsoort))
            )
            .andExpect(status().isOk());

        // Validate the Regelingsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRegelingsoortUpdatableFieldsEquals(partialUpdatedRegelingsoort, getPersistedRegelingsoort(partialUpdatedRegelingsoort));
    }

    @Test
    @Transactional
    void patchNonExistingRegelingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regelingsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegelingsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, regelingsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(regelingsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regelingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegelingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regelingsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegelingsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(regelingsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regelingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegelingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regelingsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegelingsoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(regelingsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Regelingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegelingsoort() throws Exception {
        // Initialize the database
        regelingsoortRepository.saveAndFlush(regelingsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the regelingsoort
        restRegelingsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, regelingsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return regelingsoortRepository.count();
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

    protected Regelingsoort getPersistedRegelingsoort(Regelingsoort regelingsoort) {
        return regelingsoortRepository.findById(regelingsoort.getId()).orElseThrow();
    }

    protected void assertPersistedRegelingsoortToMatchAllProperties(Regelingsoort expectedRegelingsoort) {
        assertRegelingsoortAllPropertiesEquals(expectedRegelingsoort, getPersistedRegelingsoort(expectedRegelingsoort));
    }

    protected void assertPersistedRegelingsoortToMatchUpdatableProperties(Regelingsoort expectedRegelingsoort) {
        assertRegelingsoortAllUpdatablePropertiesEquals(expectedRegelingsoort, getPersistedRegelingsoort(expectedRegelingsoort));
    }
}
