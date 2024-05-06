package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.InkomensvoorzieningAsserts.*;
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
import nl.ritense.demo.domain.Inkomensvoorziening;
import nl.ritense.demo.repository.InkomensvoorzieningRepository;
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
 * Integration tests for the {@link InkomensvoorzieningResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class InkomensvoorzieningResourceIT {

    private static final String DEFAULT_BEDRAG = "AAAAAAAAAA";
    private static final String UPDATED_BEDRAG = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMSTART = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSTART = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMTOEKENNING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMTOEKENNING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EENMALIG = false;
    private static final Boolean UPDATED_EENMALIG = true;

    private static final String DEFAULT_GROEP = "AAAAAAAAAA";
    private static final String UPDATED_GROEP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inkomensvoorzienings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InkomensvoorzieningRepository inkomensvoorzieningRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInkomensvoorzieningMockMvc;

    private Inkomensvoorziening inkomensvoorziening;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inkomensvoorziening createEntity(EntityManager em) {
        Inkomensvoorziening inkomensvoorziening = new Inkomensvoorziening()
            .bedrag(DEFAULT_BEDRAG)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .datumtoekenning(DEFAULT_DATUMTOEKENNING)
            .eenmalig(DEFAULT_EENMALIG)
            .groep(DEFAULT_GROEP);
        return inkomensvoorziening;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inkomensvoorziening createUpdatedEntity(EntityManager em) {
        Inkomensvoorziening inkomensvoorziening = new Inkomensvoorziening()
            .bedrag(UPDATED_BEDRAG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumtoekenning(UPDATED_DATUMTOEKENNING)
            .eenmalig(UPDATED_EENMALIG)
            .groep(UPDATED_GROEP);
        return inkomensvoorziening;
    }

    @BeforeEach
    public void initTest() {
        inkomensvoorziening = createEntity(em);
    }

    @Test
    @Transactional
    void createInkomensvoorziening() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Inkomensvoorziening
        var returnedInkomensvoorziening = om.readValue(
            restInkomensvoorzieningMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inkomensvoorziening)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Inkomensvoorziening.class
        );

        // Validate the Inkomensvoorziening in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInkomensvoorzieningUpdatableFieldsEquals(
            returnedInkomensvoorziening,
            getPersistedInkomensvoorziening(returnedInkomensvoorziening)
        );
    }

    @Test
    @Transactional
    void createInkomensvoorzieningWithExistingId() throws Exception {
        // Create the Inkomensvoorziening with an existing ID
        inkomensvoorziening.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInkomensvoorzieningMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inkomensvoorziening)))
            .andExpect(status().isBadRequest());

        // Validate the Inkomensvoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInkomensvoorzienings() throws Exception {
        // Initialize the database
        inkomensvoorzieningRepository.saveAndFlush(inkomensvoorziening);

        // Get all the inkomensvoorzieningList
        restInkomensvoorzieningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inkomensvoorziening.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE)))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART)))
            .andExpect(jsonPath("$.[*].datumtoekenning").value(hasItem(DEFAULT_DATUMTOEKENNING)))
            .andExpect(jsonPath("$.[*].eenmalig").value(hasItem(DEFAULT_EENMALIG.booleanValue())))
            .andExpect(jsonPath("$.[*].groep").value(hasItem(DEFAULT_GROEP)));
    }

    @Test
    @Transactional
    void getInkomensvoorziening() throws Exception {
        // Initialize the database
        inkomensvoorzieningRepository.saveAndFlush(inkomensvoorziening);

        // Get the inkomensvoorziening
        restInkomensvoorzieningMockMvc
            .perform(get(ENTITY_API_URL_ID, inkomensvoorziening.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inkomensvoorziening.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(DEFAULT_BEDRAG))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART))
            .andExpect(jsonPath("$.datumtoekenning").value(DEFAULT_DATUMTOEKENNING))
            .andExpect(jsonPath("$.eenmalig").value(DEFAULT_EENMALIG.booleanValue()))
            .andExpect(jsonPath("$.groep").value(DEFAULT_GROEP));
    }

    @Test
    @Transactional
    void getNonExistingInkomensvoorziening() throws Exception {
        // Get the inkomensvoorziening
        restInkomensvoorzieningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInkomensvoorziening() throws Exception {
        // Initialize the database
        inkomensvoorzieningRepository.saveAndFlush(inkomensvoorziening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inkomensvoorziening
        Inkomensvoorziening updatedInkomensvoorziening = inkomensvoorzieningRepository.findById(inkomensvoorziening.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInkomensvoorziening are not directly saved in db
        em.detach(updatedInkomensvoorziening);
        updatedInkomensvoorziening
            .bedrag(UPDATED_BEDRAG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumtoekenning(UPDATED_DATUMTOEKENNING)
            .eenmalig(UPDATED_EENMALIG)
            .groep(UPDATED_GROEP);

        restInkomensvoorzieningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInkomensvoorziening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInkomensvoorziening))
            )
            .andExpect(status().isOk());

        // Validate the Inkomensvoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInkomensvoorzieningToMatchAllProperties(updatedInkomensvoorziening);
    }

    @Test
    @Transactional
    void putNonExistingInkomensvoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkomensvoorziening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInkomensvoorzieningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inkomensvoorziening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inkomensvoorziening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inkomensvoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInkomensvoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkomensvoorziening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInkomensvoorzieningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inkomensvoorziening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inkomensvoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInkomensvoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkomensvoorziening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInkomensvoorzieningMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inkomensvoorziening)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inkomensvoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInkomensvoorzieningWithPatch() throws Exception {
        // Initialize the database
        inkomensvoorzieningRepository.saveAndFlush(inkomensvoorziening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inkomensvoorziening using partial update
        Inkomensvoorziening partialUpdatedInkomensvoorziening = new Inkomensvoorziening();
        partialUpdatedInkomensvoorziening.setId(inkomensvoorziening.getId());

        partialUpdatedInkomensvoorziening.eenmalig(UPDATED_EENMALIG).groep(UPDATED_GROEP);

        restInkomensvoorzieningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInkomensvoorziening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInkomensvoorziening))
            )
            .andExpect(status().isOk());

        // Validate the Inkomensvoorziening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInkomensvoorzieningUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInkomensvoorziening, inkomensvoorziening),
            getPersistedInkomensvoorziening(inkomensvoorziening)
        );
    }

    @Test
    @Transactional
    void fullUpdateInkomensvoorzieningWithPatch() throws Exception {
        // Initialize the database
        inkomensvoorzieningRepository.saveAndFlush(inkomensvoorziening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inkomensvoorziening using partial update
        Inkomensvoorziening partialUpdatedInkomensvoorziening = new Inkomensvoorziening();
        partialUpdatedInkomensvoorziening.setId(inkomensvoorziening.getId());

        partialUpdatedInkomensvoorziening
            .bedrag(UPDATED_BEDRAG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumtoekenning(UPDATED_DATUMTOEKENNING)
            .eenmalig(UPDATED_EENMALIG)
            .groep(UPDATED_GROEP);

        restInkomensvoorzieningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInkomensvoorziening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInkomensvoorziening))
            )
            .andExpect(status().isOk());

        // Validate the Inkomensvoorziening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInkomensvoorzieningUpdatableFieldsEquals(
            partialUpdatedInkomensvoorziening,
            getPersistedInkomensvoorziening(partialUpdatedInkomensvoorziening)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInkomensvoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkomensvoorziening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInkomensvoorzieningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inkomensvoorziening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inkomensvoorziening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inkomensvoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInkomensvoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkomensvoorziening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInkomensvoorzieningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inkomensvoorziening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inkomensvoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInkomensvoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkomensvoorziening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInkomensvoorzieningMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inkomensvoorziening)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inkomensvoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInkomensvoorziening() throws Exception {
        // Initialize the database
        inkomensvoorzieningRepository.saveAndFlush(inkomensvoorziening);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inkomensvoorziening
        restInkomensvoorzieningMockMvc
            .perform(delete(ENTITY_API_URL_ID, inkomensvoorziening.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inkomensvoorzieningRepository.count();
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

    protected Inkomensvoorziening getPersistedInkomensvoorziening(Inkomensvoorziening inkomensvoorziening) {
        return inkomensvoorzieningRepository.findById(inkomensvoorziening.getId()).orElseThrow();
    }

    protected void assertPersistedInkomensvoorzieningToMatchAllProperties(Inkomensvoorziening expectedInkomensvoorziening) {
        assertInkomensvoorzieningAllPropertiesEquals(
            expectedInkomensvoorziening,
            getPersistedInkomensvoorziening(expectedInkomensvoorziening)
        );
    }

    protected void assertPersistedInkomensvoorzieningToMatchUpdatableProperties(Inkomensvoorziening expectedInkomensvoorziening) {
        assertInkomensvoorzieningAllUpdatablePropertiesEquals(
            expectedInkomensvoorziening,
            getPersistedInkomensvoorziening(expectedInkomensvoorziening)
        );
    }
}
