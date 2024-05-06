package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BehandelingAsserts.*;
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
import nl.ritense.demo.domain.Behandeling;
import nl.ritense.demo.repository.BehandelingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BehandelingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BehandelingResourceIT {

    private static final String DEFAULT_DATUMEINDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMSTART = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSTART = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/behandelings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BehandelingRepository behandelingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBehandelingMockMvc;

    private Behandeling behandeling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Behandeling createEntity(EntityManager em) {
        Behandeling behandeling = new Behandeling()
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .toelichting(DEFAULT_TOELICHTING);
        return behandeling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Behandeling createUpdatedEntity(EntityManager em) {
        Behandeling behandeling = new Behandeling()
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .toelichting(UPDATED_TOELICHTING);
        return behandeling;
    }

    @BeforeEach
    public void initTest() {
        behandeling = createEntity(em);
    }

    @Test
    @Transactional
    void createBehandeling() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Behandeling
        var returnedBehandeling = om.readValue(
            restBehandelingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(behandeling)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Behandeling.class
        );

        // Validate the Behandeling in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBehandelingUpdatableFieldsEquals(returnedBehandeling, getPersistedBehandeling(returnedBehandeling));
    }

    @Test
    @Transactional
    void createBehandelingWithExistingId() throws Exception {
        // Create the Behandeling with an existing ID
        behandeling.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBehandelingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(behandeling)))
            .andExpect(status().isBadRequest());

        // Validate the Behandeling in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBehandelings() throws Exception {
        // Initialize the database
        behandelingRepository.saveAndFlush(behandeling);

        // Get all the behandelingList
        restBehandelingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(behandeling.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE)))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)));
    }

    @Test
    @Transactional
    void getBehandeling() throws Exception {
        // Initialize the database
        behandelingRepository.saveAndFlush(behandeling);

        // Get the behandeling
        restBehandelingMockMvc
            .perform(get(ENTITY_API_URL_ID, behandeling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(behandeling.getId().intValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING));
    }

    @Test
    @Transactional
    void getNonExistingBehandeling() throws Exception {
        // Get the behandeling
        restBehandelingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBehandeling() throws Exception {
        // Initialize the database
        behandelingRepository.saveAndFlush(behandeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the behandeling
        Behandeling updatedBehandeling = behandelingRepository.findById(behandeling.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBehandeling are not directly saved in db
        em.detach(updatedBehandeling);
        updatedBehandeling.datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART).toelichting(UPDATED_TOELICHTING);

        restBehandelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBehandeling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBehandeling))
            )
            .andExpect(status().isOk());

        // Validate the Behandeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBehandelingToMatchAllProperties(updatedBehandeling);
    }

    @Test
    @Transactional
    void putNonExistingBehandeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        behandeling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBehandelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, behandeling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(behandeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Behandeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBehandeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        behandeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBehandelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(behandeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Behandeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBehandeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        behandeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBehandelingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(behandeling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Behandeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBehandelingWithPatch() throws Exception {
        // Initialize the database
        behandelingRepository.saveAndFlush(behandeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the behandeling using partial update
        Behandeling partialUpdatedBehandeling = new Behandeling();
        partialUpdatedBehandeling.setId(behandeling.getId());

        partialUpdatedBehandeling.toelichting(UPDATED_TOELICHTING);

        restBehandelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBehandeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBehandeling))
            )
            .andExpect(status().isOk());

        // Validate the Behandeling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBehandelingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBehandeling, behandeling),
            getPersistedBehandeling(behandeling)
        );
    }

    @Test
    @Transactional
    void fullUpdateBehandelingWithPatch() throws Exception {
        // Initialize the database
        behandelingRepository.saveAndFlush(behandeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the behandeling using partial update
        Behandeling partialUpdatedBehandeling = new Behandeling();
        partialUpdatedBehandeling.setId(behandeling.getId());

        partialUpdatedBehandeling.datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART).toelichting(UPDATED_TOELICHTING);

        restBehandelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBehandeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBehandeling))
            )
            .andExpect(status().isOk());

        // Validate the Behandeling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBehandelingUpdatableFieldsEquals(partialUpdatedBehandeling, getPersistedBehandeling(partialUpdatedBehandeling));
    }

    @Test
    @Transactional
    void patchNonExistingBehandeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        behandeling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBehandelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, behandeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(behandeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Behandeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBehandeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        behandeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBehandelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(behandeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Behandeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBehandeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        behandeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBehandelingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(behandeling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Behandeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBehandeling() throws Exception {
        // Initialize the database
        behandelingRepository.saveAndFlush(behandeling);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the behandeling
        restBehandelingMockMvc
            .perform(delete(ENTITY_API_URL_ID, behandeling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return behandelingRepository.count();
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

    protected Behandeling getPersistedBehandeling(Behandeling behandeling) {
        return behandelingRepository.findById(behandeling.getId()).orElseThrow();
    }

    protected void assertPersistedBehandelingToMatchAllProperties(Behandeling expectedBehandeling) {
        assertBehandelingAllPropertiesEquals(expectedBehandeling, getPersistedBehandeling(expectedBehandeling));
    }

    protected void assertPersistedBehandelingToMatchUpdatableProperties(Behandeling expectedBehandeling) {
        assertBehandelingAllUpdatablePropertiesEquals(expectedBehandeling, getPersistedBehandeling(expectedBehandeling));
    }
}
