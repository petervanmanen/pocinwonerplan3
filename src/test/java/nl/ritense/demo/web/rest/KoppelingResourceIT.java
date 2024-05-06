package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KoppelingAsserts.*;
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
import nl.ritense.demo.domain.Koppeling;
import nl.ritense.demo.repository.KoppelingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KoppelingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KoppelingResourceIT {

    private static final String DEFAULT_BESCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_BESCHRIJVING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DIRECT = false;
    private static final Boolean UPDATED_DIRECT = true;

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/koppelings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KoppelingRepository koppelingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKoppelingMockMvc;

    private Koppeling koppeling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Koppeling createEntity(EntityManager em) {
        Koppeling koppeling = new Koppeling().beschrijving(DEFAULT_BESCHRIJVING).direct(DEFAULT_DIRECT).toelichting(DEFAULT_TOELICHTING);
        return koppeling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Koppeling createUpdatedEntity(EntityManager em) {
        Koppeling koppeling = new Koppeling().beschrijving(UPDATED_BESCHRIJVING).direct(UPDATED_DIRECT).toelichting(UPDATED_TOELICHTING);
        return koppeling;
    }

    @BeforeEach
    public void initTest() {
        koppeling = createEntity(em);
    }

    @Test
    @Transactional
    void createKoppeling() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Koppeling
        var returnedKoppeling = om.readValue(
            restKoppelingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(koppeling)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Koppeling.class
        );

        // Validate the Koppeling in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKoppelingUpdatableFieldsEquals(returnedKoppeling, getPersistedKoppeling(returnedKoppeling));
    }

    @Test
    @Transactional
    void createKoppelingWithExistingId() throws Exception {
        // Create the Koppeling with an existing ID
        koppeling.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKoppelingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(koppeling)))
            .andExpect(status().isBadRequest());

        // Validate the Koppeling in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKoppelings() throws Exception {
        // Initialize the database
        koppelingRepository.saveAndFlush(koppeling);

        // Get all the koppelingList
        restKoppelingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(koppeling.getId().intValue())))
            .andExpect(jsonPath("$.[*].beschrijving").value(hasItem(DEFAULT_BESCHRIJVING)))
            .andExpect(jsonPath("$.[*].direct").value(hasItem(DEFAULT_DIRECT.booleanValue())))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)));
    }

    @Test
    @Transactional
    void getKoppeling() throws Exception {
        // Initialize the database
        koppelingRepository.saveAndFlush(koppeling);

        // Get the koppeling
        restKoppelingMockMvc
            .perform(get(ENTITY_API_URL_ID, koppeling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(koppeling.getId().intValue()))
            .andExpect(jsonPath("$.beschrijving").value(DEFAULT_BESCHRIJVING))
            .andExpect(jsonPath("$.direct").value(DEFAULT_DIRECT.booleanValue()))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING));
    }

    @Test
    @Transactional
    void getNonExistingKoppeling() throws Exception {
        // Get the koppeling
        restKoppelingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKoppeling() throws Exception {
        // Initialize the database
        koppelingRepository.saveAndFlush(koppeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the koppeling
        Koppeling updatedKoppeling = koppelingRepository.findById(koppeling.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKoppeling are not directly saved in db
        em.detach(updatedKoppeling);
        updatedKoppeling.beschrijving(UPDATED_BESCHRIJVING).direct(UPDATED_DIRECT).toelichting(UPDATED_TOELICHTING);

        restKoppelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKoppeling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKoppeling))
            )
            .andExpect(status().isOk());

        // Validate the Koppeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKoppelingToMatchAllProperties(updatedKoppeling);
    }

    @Test
    @Transactional
    void putNonExistingKoppeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koppeling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKoppelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, koppeling.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(koppeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Koppeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKoppeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koppeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoppelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(koppeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Koppeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKoppeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koppeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoppelingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(koppeling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Koppeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKoppelingWithPatch() throws Exception {
        // Initialize the database
        koppelingRepository.saveAndFlush(koppeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the koppeling using partial update
        Koppeling partialUpdatedKoppeling = new Koppeling();
        partialUpdatedKoppeling.setId(koppeling.getId());

        partialUpdatedKoppeling.beschrijving(UPDATED_BESCHRIJVING).direct(UPDATED_DIRECT);

        restKoppelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKoppeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKoppeling))
            )
            .andExpect(status().isOk());

        // Validate the Koppeling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKoppelingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKoppeling, koppeling),
            getPersistedKoppeling(koppeling)
        );
    }

    @Test
    @Transactional
    void fullUpdateKoppelingWithPatch() throws Exception {
        // Initialize the database
        koppelingRepository.saveAndFlush(koppeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the koppeling using partial update
        Koppeling partialUpdatedKoppeling = new Koppeling();
        partialUpdatedKoppeling.setId(koppeling.getId());

        partialUpdatedKoppeling.beschrijving(UPDATED_BESCHRIJVING).direct(UPDATED_DIRECT).toelichting(UPDATED_TOELICHTING);

        restKoppelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKoppeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKoppeling))
            )
            .andExpect(status().isOk());

        // Validate the Koppeling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKoppelingUpdatableFieldsEquals(partialUpdatedKoppeling, getPersistedKoppeling(partialUpdatedKoppeling));
    }

    @Test
    @Transactional
    void patchNonExistingKoppeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koppeling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKoppelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, koppeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(koppeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Koppeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKoppeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koppeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoppelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(koppeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Koppeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKoppeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koppeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoppelingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(koppeling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Koppeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKoppeling() throws Exception {
        // Initialize the database
        koppelingRepository.saveAndFlush(koppeling);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the koppeling
        restKoppelingMockMvc
            .perform(delete(ENTITY_API_URL_ID, koppeling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return koppelingRepository.count();
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

    protected Koppeling getPersistedKoppeling(Koppeling koppeling) {
        return koppelingRepository.findById(koppeling.getId()).orElseThrow();
    }

    protected void assertPersistedKoppelingToMatchAllProperties(Koppeling expectedKoppeling) {
        assertKoppelingAllPropertiesEquals(expectedKoppeling, getPersistedKoppeling(expectedKoppeling));
    }

    protected void assertPersistedKoppelingToMatchUpdatableProperties(Koppeling expectedKoppeling) {
        assertKoppelingAllUpdatablePropertiesEquals(expectedKoppeling, getPersistedKoppeling(expectedKoppeling));
    }
}
