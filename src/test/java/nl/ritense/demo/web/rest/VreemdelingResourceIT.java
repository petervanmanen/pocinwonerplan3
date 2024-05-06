package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VreemdelingAsserts.*;
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
import nl.ritense.demo.domain.Vreemdeling;
import nl.ritense.demo.repository.VreemdelingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VreemdelingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VreemdelingResourceIT {

    private static final String DEFAULT_SOCIAALREFERENT = "AAAAAAAAAA";
    private static final String UPDATED_SOCIAALREFERENT = "BBBBBBBBBB";

    private static final String DEFAULT_VNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_VNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vreemdelings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VreemdelingRepository vreemdelingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVreemdelingMockMvc;

    private Vreemdeling vreemdeling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vreemdeling createEntity(EntityManager em) {
        Vreemdeling vreemdeling = new Vreemdeling().sociaalreferent(DEFAULT_SOCIAALREFERENT).vnummer(DEFAULT_VNUMMER);
        return vreemdeling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vreemdeling createUpdatedEntity(EntityManager em) {
        Vreemdeling vreemdeling = new Vreemdeling().sociaalreferent(UPDATED_SOCIAALREFERENT).vnummer(UPDATED_VNUMMER);
        return vreemdeling;
    }

    @BeforeEach
    public void initTest() {
        vreemdeling = createEntity(em);
    }

    @Test
    @Transactional
    void createVreemdeling() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vreemdeling
        var returnedVreemdeling = om.readValue(
            restVreemdelingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vreemdeling)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vreemdeling.class
        );

        // Validate the Vreemdeling in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVreemdelingUpdatableFieldsEquals(returnedVreemdeling, getPersistedVreemdeling(returnedVreemdeling));
    }

    @Test
    @Transactional
    void createVreemdelingWithExistingId() throws Exception {
        // Create the Vreemdeling with an existing ID
        vreemdeling.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVreemdelingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vreemdeling)))
            .andExpect(status().isBadRequest());

        // Validate the Vreemdeling in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVreemdelings() throws Exception {
        // Initialize the database
        vreemdelingRepository.saveAndFlush(vreemdeling);

        // Get all the vreemdelingList
        restVreemdelingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vreemdeling.getId().intValue())))
            .andExpect(jsonPath("$.[*].sociaalreferent").value(hasItem(DEFAULT_SOCIAALREFERENT)))
            .andExpect(jsonPath("$.[*].vnummer").value(hasItem(DEFAULT_VNUMMER)));
    }

    @Test
    @Transactional
    void getVreemdeling() throws Exception {
        // Initialize the database
        vreemdelingRepository.saveAndFlush(vreemdeling);

        // Get the vreemdeling
        restVreemdelingMockMvc
            .perform(get(ENTITY_API_URL_ID, vreemdeling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vreemdeling.getId().intValue()))
            .andExpect(jsonPath("$.sociaalreferent").value(DEFAULT_SOCIAALREFERENT))
            .andExpect(jsonPath("$.vnummer").value(DEFAULT_VNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingVreemdeling() throws Exception {
        // Get the vreemdeling
        restVreemdelingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVreemdeling() throws Exception {
        // Initialize the database
        vreemdelingRepository.saveAndFlush(vreemdeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vreemdeling
        Vreemdeling updatedVreemdeling = vreemdelingRepository.findById(vreemdeling.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVreemdeling are not directly saved in db
        em.detach(updatedVreemdeling);
        updatedVreemdeling.sociaalreferent(UPDATED_SOCIAALREFERENT).vnummer(UPDATED_VNUMMER);

        restVreemdelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVreemdeling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVreemdeling))
            )
            .andExpect(status().isOk());

        // Validate the Vreemdeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVreemdelingToMatchAllProperties(updatedVreemdeling);
    }

    @Test
    @Transactional
    void putNonExistingVreemdeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vreemdeling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVreemdelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vreemdeling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vreemdeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vreemdeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVreemdeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vreemdeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVreemdelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vreemdeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vreemdeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVreemdeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vreemdeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVreemdelingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vreemdeling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vreemdeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVreemdelingWithPatch() throws Exception {
        // Initialize the database
        vreemdelingRepository.saveAndFlush(vreemdeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vreemdeling using partial update
        Vreemdeling partialUpdatedVreemdeling = new Vreemdeling();
        partialUpdatedVreemdeling.setId(vreemdeling.getId());

        partialUpdatedVreemdeling.sociaalreferent(UPDATED_SOCIAALREFERENT).vnummer(UPDATED_VNUMMER);

        restVreemdelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVreemdeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVreemdeling))
            )
            .andExpect(status().isOk());

        // Validate the Vreemdeling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVreemdelingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVreemdeling, vreemdeling),
            getPersistedVreemdeling(vreemdeling)
        );
    }

    @Test
    @Transactional
    void fullUpdateVreemdelingWithPatch() throws Exception {
        // Initialize the database
        vreemdelingRepository.saveAndFlush(vreemdeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vreemdeling using partial update
        Vreemdeling partialUpdatedVreemdeling = new Vreemdeling();
        partialUpdatedVreemdeling.setId(vreemdeling.getId());

        partialUpdatedVreemdeling.sociaalreferent(UPDATED_SOCIAALREFERENT).vnummer(UPDATED_VNUMMER);

        restVreemdelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVreemdeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVreemdeling))
            )
            .andExpect(status().isOk());

        // Validate the Vreemdeling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVreemdelingUpdatableFieldsEquals(partialUpdatedVreemdeling, getPersistedVreemdeling(partialUpdatedVreemdeling));
    }

    @Test
    @Transactional
    void patchNonExistingVreemdeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vreemdeling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVreemdelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vreemdeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vreemdeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vreemdeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVreemdeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vreemdeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVreemdelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vreemdeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vreemdeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVreemdeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vreemdeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVreemdelingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vreemdeling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vreemdeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVreemdeling() throws Exception {
        // Initialize the database
        vreemdelingRepository.saveAndFlush(vreemdeling);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vreemdeling
        restVreemdelingMockMvc
            .perform(delete(ENTITY_API_URL_ID, vreemdeling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vreemdelingRepository.count();
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

    protected Vreemdeling getPersistedVreemdeling(Vreemdeling vreemdeling) {
        return vreemdelingRepository.findById(vreemdeling.getId()).orElseThrow();
    }

    protected void assertPersistedVreemdelingToMatchAllProperties(Vreemdeling expectedVreemdeling) {
        assertVreemdelingAllPropertiesEquals(expectedVreemdeling, getPersistedVreemdeling(expectedVreemdeling));
    }

    protected void assertPersistedVreemdelingToMatchUpdatableProperties(Vreemdeling expectedVreemdeling) {
        assertVreemdelingAllUpdatablePropertiesEquals(expectedVreemdeling, getPersistedVreemdeling(expectedVreemdeling));
    }
}
