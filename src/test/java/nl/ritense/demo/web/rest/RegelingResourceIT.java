package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RegelingAsserts.*;
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
import nl.ritense.demo.domain.Regeling;
import nl.ritense.demo.repository.RegelingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RegelingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RegelingResourceIT {

    private static final String DEFAULT_DATUMEINDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMSTART = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSTART = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMTOEKENNING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMTOEKENNING = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/regelings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RegelingRepository regelingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegelingMockMvc;

    private Regeling regeling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regeling createEntity(EntityManager em) {
        Regeling regeling = new Regeling()
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .datumtoekenning(DEFAULT_DATUMTOEKENNING)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return regeling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regeling createUpdatedEntity(EntityManager em) {
        Regeling regeling = new Regeling()
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumtoekenning(UPDATED_DATUMTOEKENNING)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return regeling;
    }

    @BeforeEach
    public void initTest() {
        regeling = createEntity(em);
    }

    @Test
    @Transactional
    void createRegeling() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Regeling
        var returnedRegeling = om.readValue(
            restRegelingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regeling)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Regeling.class
        );

        // Validate the Regeling in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRegelingUpdatableFieldsEquals(returnedRegeling, getPersistedRegeling(returnedRegeling));
    }

    @Test
    @Transactional
    void createRegelingWithExistingId() throws Exception {
        // Create the Regeling with an existing ID
        regeling.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegelingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regeling)))
            .andExpect(status().isBadRequest());

        // Validate the Regeling in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRegelings() throws Exception {
        // Initialize the database
        regelingRepository.saveAndFlush(regeling);

        // Get all the regelingList
        restRegelingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regeling.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE)))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART)))
            .andExpect(jsonPath("$.[*].datumtoekenning").value(hasItem(DEFAULT_DATUMTOEKENNING)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getRegeling() throws Exception {
        // Initialize the database
        regelingRepository.saveAndFlush(regeling);

        // Get the regeling
        restRegelingMockMvc
            .perform(get(ENTITY_API_URL_ID, regeling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regeling.getId().intValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART))
            .andExpect(jsonPath("$.datumtoekenning").value(DEFAULT_DATUMTOEKENNING))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingRegeling() throws Exception {
        // Get the regeling
        restRegelingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRegeling() throws Exception {
        // Initialize the database
        regelingRepository.saveAndFlush(regeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the regeling
        Regeling updatedRegeling = regelingRepository.findById(regeling.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRegeling are not directly saved in db
        em.detach(updatedRegeling);
        updatedRegeling
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumtoekenning(UPDATED_DATUMTOEKENNING)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restRegelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRegeling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRegeling))
            )
            .andExpect(status().isOk());

        // Validate the Regeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRegelingToMatchAllProperties(updatedRegeling);
    }

    @Test
    @Transactional
    void putNonExistingRegeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regeling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regeling.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(regeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegelingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regeling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Regeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegelingWithPatch() throws Exception {
        // Initialize the database
        regelingRepository.saveAndFlush(regeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the regeling using partial update
        Regeling partialUpdatedRegeling = new Regeling();
        partialUpdatedRegeling.setId(regeling.getId());

        partialUpdatedRegeling.datumstart(UPDATED_DATUMSTART).datumtoekenning(UPDATED_DATUMTOEKENNING).omschrijving(UPDATED_OMSCHRIJVING);

        restRegelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRegeling))
            )
            .andExpect(status().isOk());

        // Validate the Regeling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRegelingUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRegeling, regeling), getPersistedRegeling(regeling));
    }

    @Test
    @Transactional
    void fullUpdateRegelingWithPatch() throws Exception {
        // Initialize the database
        regelingRepository.saveAndFlush(regeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the regeling using partial update
        Regeling partialUpdatedRegeling = new Regeling();
        partialUpdatedRegeling.setId(regeling.getId());

        partialUpdatedRegeling
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumtoekenning(UPDATED_DATUMTOEKENNING)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restRegelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRegeling))
            )
            .andExpect(status().isOk());

        // Validate the Regeling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRegelingUpdatableFieldsEquals(partialUpdatedRegeling, getPersistedRegeling(partialUpdatedRegeling));
    }

    @Test
    @Transactional
    void patchNonExistingRegeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regeling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, regeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(regeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(regeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegelingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(regeling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Regeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegeling() throws Exception {
        // Initialize the database
        regelingRepository.saveAndFlush(regeling);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the regeling
        restRegelingMockMvc
            .perform(delete(ENTITY_API_URL_ID, regeling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return regelingRepository.count();
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

    protected Regeling getPersistedRegeling(Regeling regeling) {
        return regelingRepository.findById(regeling.getId()).orElseThrow();
    }

    protected void assertPersistedRegelingToMatchAllProperties(Regeling expectedRegeling) {
        assertRegelingAllPropertiesEquals(expectedRegeling, getPersistedRegeling(expectedRegeling));
    }

    protected void assertPersistedRegelingToMatchUpdatableProperties(Regeling expectedRegeling) {
        assertRegelingAllUpdatablePropertiesEquals(expectedRegeling, getPersistedRegeling(expectedRegeling));
    }
}
