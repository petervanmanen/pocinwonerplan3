package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BeschiktevoorzieningAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Beschiktevoorziening;
import nl.ritense.demo.repository.BeschiktevoorzieningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BeschiktevoorzieningResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BeschiktevoorzieningResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEOORSPRONKELIJK = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEOORSPRONKELIJK = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EENHEID = "AAAAAAAAAA";
    private static final String UPDATED_EENHEID = "BBBBBBBBBB";

    private static final String DEFAULT_FREQUENTIE = "AAAAAAAAAA";
    private static final String UPDATED_FREQUENTIE = "BBBBBBBBBB";

    private static final String DEFAULT_LEVERINGSVORM = "AAAAAAAAAA";
    private static final String UPDATED_LEVERINGSVORM = "BBBBBBBBBB";

    private static final String DEFAULT_OMVANG = "AAAAAAAAAA";
    private static final String UPDATED_OMVANG = "BBBBBBBBBB";

    private static final String DEFAULT_REDENEINDE = "AAAAAAAAAA";
    private static final String UPDATED_REDENEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_WET = "AAAAAAAAAA";
    private static final String UPDATED_WET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/beschiktevoorzienings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BeschiktevoorzieningRepository beschiktevoorzieningRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeschiktevoorzieningMockMvc;

    private Beschiktevoorziening beschiktevoorziening;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beschiktevoorziening createEntity(EntityManager em) {
        Beschiktevoorziening beschiktevoorziening = new Beschiktevoorziening()
            .code(DEFAULT_CODE)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumeindeoorspronkelijk(DEFAULT_DATUMEINDEOORSPRONKELIJK)
            .datumstart(DEFAULT_DATUMSTART)
            .eenheid(DEFAULT_EENHEID)
            .frequentie(DEFAULT_FREQUENTIE)
            .leveringsvorm(DEFAULT_LEVERINGSVORM)
            .omvang(DEFAULT_OMVANG)
            .redeneinde(DEFAULT_REDENEINDE)
            .status(DEFAULT_STATUS)
            .wet(DEFAULT_WET);
        return beschiktevoorziening;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beschiktevoorziening createUpdatedEntity(EntityManager em) {
        Beschiktevoorziening beschiktevoorziening = new Beschiktevoorziening()
            .code(UPDATED_CODE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindeoorspronkelijk(UPDATED_DATUMEINDEOORSPRONKELIJK)
            .datumstart(UPDATED_DATUMSTART)
            .eenheid(UPDATED_EENHEID)
            .frequentie(UPDATED_FREQUENTIE)
            .leveringsvorm(UPDATED_LEVERINGSVORM)
            .omvang(UPDATED_OMVANG)
            .redeneinde(UPDATED_REDENEINDE)
            .status(UPDATED_STATUS)
            .wet(UPDATED_WET);
        return beschiktevoorziening;
    }

    @BeforeEach
    public void initTest() {
        beschiktevoorziening = createEntity(em);
    }

    @Test
    @Transactional
    void createBeschiktevoorziening() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Beschiktevoorziening
        var returnedBeschiktevoorziening = om.readValue(
            restBeschiktevoorzieningMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beschiktevoorziening)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Beschiktevoorziening.class
        );

        // Validate the Beschiktevoorziening in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBeschiktevoorzieningUpdatableFieldsEquals(
            returnedBeschiktevoorziening,
            getPersistedBeschiktevoorziening(returnedBeschiktevoorziening)
        );
    }

    @Test
    @Transactional
    void createBeschiktevoorzieningWithExistingId() throws Exception {
        // Create the Beschiktevoorziening with an existing ID
        beschiktevoorziening.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeschiktevoorzieningMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beschiktevoorziening)))
            .andExpect(status().isBadRequest());

        // Validate the Beschiktevoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBeschiktevoorzienings() throws Exception {
        // Initialize the database
        beschiktevoorzieningRepository.saveAndFlush(beschiktevoorziening);

        // Get all the beschiktevoorzieningList
        restBeschiktevoorzieningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beschiktevoorziening.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumeindeoorspronkelijk").value(hasItem(DEFAULT_DATUMEINDEOORSPRONKELIJK.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].eenheid").value(hasItem(DEFAULT_EENHEID)))
            .andExpect(jsonPath("$.[*].frequentie").value(hasItem(DEFAULT_FREQUENTIE)))
            .andExpect(jsonPath("$.[*].leveringsvorm").value(hasItem(DEFAULT_LEVERINGSVORM)))
            .andExpect(jsonPath("$.[*].omvang").value(hasItem(DEFAULT_OMVANG)))
            .andExpect(jsonPath("$.[*].redeneinde").value(hasItem(DEFAULT_REDENEINDE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].wet").value(hasItem(DEFAULT_WET)));
    }

    @Test
    @Transactional
    void getBeschiktevoorziening() throws Exception {
        // Initialize the database
        beschiktevoorzieningRepository.saveAndFlush(beschiktevoorziening);

        // Get the beschiktevoorziening
        restBeschiktevoorzieningMockMvc
            .perform(get(ENTITY_API_URL_ID, beschiktevoorziening.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beschiktevoorziening.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumeindeoorspronkelijk").value(DEFAULT_DATUMEINDEOORSPRONKELIJK.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.eenheid").value(DEFAULT_EENHEID))
            .andExpect(jsonPath("$.frequentie").value(DEFAULT_FREQUENTIE))
            .andExpect(jsonPath("$.leveringsvorm").value(DEFAULT_LEVERINGSVORM))
            .andExpect(jsonPath("$.omvang").value(DEFAULT_OMVANG))
            .andExpect(jsonPath("$.redeneinde").value(DEFAULT_REDENEINDE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.wet").value(DEFAULT_WET));
    }

    @Test
    @Transactional
    void getNonExistingBeschiktevoorziening() throws Exception {
        // Get the beschiktevoorziening
        restBeschiktevoorzieningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBeschiktevoorziening() throws Exception {
        // Initialize the database
        beschiktevoorzieningRepository.saveAndFlush(beschiktevoorziening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beschiktevoorziening
        Beschiktevoorziening updatedBeschiktevoorziening = beschiktevoorzieningRepository
            .findById(beschiktevoorziening.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedBeschiktevoorziening are not directly saved in db
        em.detach(updatedBeschiktevoorziening);
        updatedBeschiktevoorziening
            .code(UPDATED_CODE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindeoorspronkelijk(UPDATED_DATUMEINDEOORSPRONKELIJK)
            .datumstart(UPDATED_DATUMSTART)
            .eenheid(UPDATED_EENHEID)
            .frequentie(UPDATED_FREQUENTIE)
            .leveringsvorm(UPDATED_LEVERINGSVORM)
            .omvang(UPDATED_OMVANG)
            .redeneinde(UPDATED_REDENEINDE)
            .status(UPDATED_STATUS)
            .wet(UPDATED_WET);

        restBeschiktevoorzieningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBeschiktevoorziening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBeschiktevoorziening))
            )
            .andExpect(status().isOk());

        // Validate the Beschiktevoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBeschiktevoorzieningToMatchAllProperties(updatedBeschiktevoorziening);
    }

    @Test
    @Transactional
    void putNonExistingBeschiktevoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschiktevoorziening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeschiktevoorzieningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, beschiktevoorziening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beschiktevoorziening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beschiktevoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBeschiktevoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschiktevoorziening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeschiktevoorzieningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beschiktevoorziening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beschiktevoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBeschiktevoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschiktevoorziening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeschiktevoorzieningMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beschiktevoorziening)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beschiktevoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBeschiktevoorzieningWithPatch() throws Exception {
        // Initialize the database
        beschiktevoorzieningRepository.saveAndFlush(beschiktevoorziening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beschiktevoorziening using partial update
        Beschiktevoorziening partialUpdatedBeschiktevoorziening = new Beschiktevoorziening();
        partialUpdatedBeschiktevoorziening.setId(beschiktevoorziening.getId());

        partialUpdatedBeschiktevoorziening
            .datumeindeoorspronkelijk(UPDATED_DATUMEINDEOORSPRONKELIJK)
            .eenheid(UPDATED_EENHEID)
            .redeneinde(UPDATED_REDENEINDE);

        restBeschiktevoorzieningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeschiktevoorziening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeschiktevoorziening))
            )
            .andExpect(status().isOk());

        // Validate the Beschiktevoorziening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeschiktevoorzieningUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBeschiktevoorziening, beschiktevoorziening),
            getPersistedBeschiktevoorziening(beschiktevoorziening)
        );
    }

    @Test
    @Transactional
    void fullUpdateBeschiktevoorzieningWithPatch() throws Exception {
        // Initialize the database
        beschiktevoorzieningRepository.saveAndFlush(beschiktevoorziening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beschiktevoorziening using partial update
        Beschiktevoorziening partialUpdatedBeschiktevoorziening = new Beschiktevoorziening();
        partialUpdatedBeschiktevoorziening.setId(beschiktevoorziening.getId());

        partialUpdatedBeschiktevoorziening
            .code(UPDATED_CODE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindeoorspronkelijk(UPDATED_DATUMEINDEOORSPRONKELIJK)
            .datumstart(UPDATED_DATUMSTART)
            .eenheid(UPDATED_EENHEID)
            .frequentie(UPDATED_FREQUENTIE)
            .leveringsvorm(UPDATED_LEVERINGSVORM)
            .omvang(UPDATED_OMVANG)
            .redeneinde(UPDATED_REDENEINDE)
            .status(UPDATED_STATUS)
            .wet(UPDATED_WET);

        restBeschiktevoorzieningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeschiktevoorziening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeschiktevoorziening))
            )
            .andExpect(status().isOk());

        // Validate the Beschiktevoorziening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeschiktevoorzieningUpdatableFieldsEquals(
            partialUpdatedBeschiktevoorziening,
            getPersistedBeschiktevoorziening(partialUpdatedBeschiktevoorziening)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBeschiktevoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschiktevoorziening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeschiktevoorzieningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, beschiktevoorziening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beschiktevoorziening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beschiktevoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBeschiktevoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschiktevoorziening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeschiktevoorzieningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beschiktevoorziening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beschiktevoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBeschiktevoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschiktevoorziening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeschiktevoorzieningMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(beschiktevoorziening)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beschiktevoorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBeschiktevoorziening() throws Exception {
        // Initialize the database
        beschiktevoorzieningRepository.saveAndFlush(beschiktevoorziening);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the beschiktevoorziening
        restBeschiktevoorzieningMockMvc
            .perform(delete(ENTITY_API_URL_ID, beschiktevoorziening.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return beschiktevoorzieningRepository.count();
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

    protected Beschiktevoorziening getPersistedBeschiktevoorziening(Beschiktevoorziening beschiktevoorziening) {
        return beschiktevoorzieningRepository.findById(beschiktevoorziening.getId()).orElseThrow();
    }

    protected void assertPersistedBeschiktevoorzieningToMatchAllProperties(Beschiktevoorziening expectedBeschiktevoorziening) {
        assertBeschiktevoorzieningAllPropertiesEquals(
            expectedBeschiktevoorziening,
            getPersistedBeschiktevoorziening(expectedBeschiktevoorziening)
        );
    }

    protected void assertPersistedBeschiktevoorzieningToMatchUpdatableProperties(Beschiktevoorziening expectedBeschiktevoorziening) {
        assertBeschiktevoorzieningAllUpdatablePropertiesEquals(
            expectedBeschiktevoorziening,
            getPersistedBeschiktevoorziening(expectedBeschiktevoorziening)
        );
    }
}
