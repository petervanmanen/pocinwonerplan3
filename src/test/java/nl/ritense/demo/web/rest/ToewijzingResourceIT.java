package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ToewijzingAsserts.*;
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
import nl.ritense.demo.domain.Toewijzing;
import nl.ritense.demo.repository.ToewijzingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ToewijzingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ToewijzingResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAAR = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAAR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMAANSCHAF = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANSCHAF = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDETOEWIJZING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDETOEWIJZING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTARTTOEWIJZING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTARTTOEWIJZING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMTOEWIJZING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMTOEWIJZING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EENHEID = "AAAAAAAAAA";
    private static final String UPDATED_EENHEID = "BBBBBBBBBB";

    private static final String DEFAULT_FREQUENTIE = "AAAAAAAAAA";
    private static final String UPDATED_FREQUENTIE = "BBBBBBBBBB";

    private static final String DEFAULT_OMVANG = "AAAAAAAAAA";
    private static final String UPDATED_OMVANG = "BBBBBBBBBB";

    private static final String DEFAULT_REDENWIJZIGING = "AAAAAAAAAA";
    private static final String UPDATED_REDENWIJZIGING = "BBBBBBBBBB";

    private static final String DEFAULT_TOEWIJZINGNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_TOEWIJZINGNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_WET = "AAAAAAAAAA";
    private static final String UPDATED_WET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/toewijzings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ToewijzingRepository toewijzingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restToewijzingMockMvc;

    private Toewijzing toewijzing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Toewijzing createEntity(EntityManager em) {
        Toewijzing toewijzing = new Toewijzing()
            .code(DEFAULT_CODE)
            .commentaar(DEFAULT_COMMENTAAR)
            .datumaanschaf(DEFAULT_DATUMAANSCHAF)
            .datumeindetoewijzing(DEFAULT_DATUMEINDETOEWIJZING)
            .datumstarttoewijzing(DEFAULT_DATUMSTARTTOEWIJZING)
            .datumtoewijzing(DEFAULT_DATUMTOEWIJZING)
            .eenheid(DEFAULT_EENHEID)
            .frequentie(DEFAULT_FREQUENTIE)
            .omvang(DEFAULT_OMVANG)
            .redenwijziging(DEFAULT_REDENWIJZIGING)
            .toewijzingnummer(DEFAULT_TOEWIJZINGNUMMER)
            .wet(DEFAULT_WET);
        return toewijzing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Toewijzing createUpdatedEntity(EntityManager em) {
        Toewijzing toewijzing = new Toewijzing()
            .code(UPDATED_CODE)
            .commentaar(UPDATED_COMMENTAAR)
            .datumaanschaf(UPDATED_DATUMAANSCHAF)
            .datumeindetoewijzing(UPDATED_DATUMEINDETOEWIJZING)
            .datumstarttoewijzing(UPDATED_DATUMSTARTTOEWIJZING)
            .datumtoewijzing(UPDATED_DATUMTOEWIJZING)
            .eenheid(UPDATED_EENHEID)
            .frequentie(UPDATED_FREQUENTIE)
            .omvang(UPDATED_OMVANG)
            .redenwijziging(UPDATED_REDENWIJZIGING)
            .toewijzingnummer(UPDATED_TOEWIJZINGNUMMER)
            .wet(UPDATED_WET);
        return toewijzing;
    }

    @BeforeEach
    public void initTest() {
        toewijzing = createEntity(em);
    }

    @Test
    @Transactional
    void createToewijzing() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Toewijzing
        var returnedToewijzing = om.readValue(
            restToewijzingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(toewijzing)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Toewijzing.class
        );

        // Validate the Toewijzing in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertToewijzingUpdatableFieldsEquals(returnedToewijzing, getPersistedToewijzing(returnedToewijzing));
    }

    @Test
    @Transactional
    void createToewijzingWithExistingId() throws Exception {
        // Create the Toewijzing with an existing ID
        toewijzing.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restToewijzingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(toewijzing)))
            .andExpect(status().isBadRequest());

        // Validate the Toewijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllToewijzings() throws Exception {
        // Initialize the database
        toewijzingRepository.saveAndFlush(toewijzing);

        // Get all the toewijzingList
        restToewijzingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(toewijzing.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].commentaar").value(hasItem(DEFAULT_COMMENTAAR)))
            .andExpect(jsonPath("$.[*].datumaanschaf").value(hasItem(DEFAULT_DATUMAANSCHAF.toString())))
            .andExpect(jsonPath("$.[*].datumeindetoewijzing").value(hasItem(DEFAULT_DATUMEINDETOEWIJZING.toString())))
            .andExpect(jsonPath("$.[*].datumstarttoewijzing").value(hasItem(DEFAULT_DATUMSTARTTOEWIJZING.toString())))
            .andExpect(jsonPath("$.[*].datumtoewijzing").value(hasItem(DEFAULT_DATUMTOEWIJZING.toString())))
            .andExpect(jsonPath("$.[*].eenheid").value(hasItem(DEFAULT_EENHEID)))
            .andExpect(jsonPath("$.[*].frequentie").value(hasItem(DEFAULT_FREQUENTIE)))
            .andExpect(jsonPath("$.[*].omvang").value(hasItem(DEFAULT_OMVANG)))
            .andExpect(jsonPath("$.[*].redenwijziging").value(hasItem(DEFAULT_REDENWIJZIGING)))
            .andExpect(jsonPath("$.[*].toewijzingnummer").value(hasItem(DEFAULT_TOEWIJZINGNUMMER)))
            .andExpect(jsonPath("$.[*].wet").value(hasItem(DEFAULT_WET)));
    }

    @Test
    @Transactional
    void getToewijzing() throws Exception {
        // Initialize the database
        toewijzingRepository.saveAndFlush(toewijzing);

        // Get the toewijzing
        restToewijzingMockMvc
            .perform(get(ENTITY_API_URL_ID, toewijzing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(toewijzing.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.commentaar").value(DEFAULT_COMMENTAAR))
            .andExpect(jsonPath("$.datumaanschaf").value(DEFAULT_DATUMAANSCHAF.toString()))
            .andExpect(jsonPath("$.datumeindetoewijzing").value(DEFAULT_DATUMEINDETOEWIJZING.toString()))
            .andExpect(jsonPath("$.datumstarttoewijzing").value(DEFAULT_DATUMSTARTTOEWIJZING.toString()))
            .andExpect(jsonPath("$.datumtoewijzing").value(DEFAULT_DATUMTOEWIJZING.toString()))
            .andExpect(jsonPath("$.eenheid").value(DEFAULT_EENHEID))
            .andExpect(jsonPath("$.frequentie").value(DEFAULT_FREQUENTIE))
            .andExpect(jsonPath("$.omvang").value(DEFAULT_OMVANG))
            .andExpect(jsonPath("$.redenwijziging").value(DEFAULT_REDENWIJZIGING))
            .andExpect(jsonPath("$.toewijzingnummer").value(DEFAULT_TOEWIJZINGNUMMER))
            .andExpect(jsonPath("$.wet").value(DEFAULT_WET));
    }

    @Test
    @Transactional
    void getNonExistingToewijzing() throws Exception {
        // Get the toewijzing
        restToewijzingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingToewijzing() throws Exception {
        // Initialize the database
        toewijzingRepository.saveAndFlush(toewijzing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the toewijzing
        Toewijzing updatedToewijzing = toewijzingRepository.findById(toewijzing.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedToewijzing are not directly saved in db
        em.detach(updatedToewijzing);
        updatedToewijzing
            .code(UPDATED_CODE)
            .commentaar(UPDATED_COMMENTAAR)
            .datumaanschaf(UPDATED_DATUMAANSCHAF)
            .datumeindetoewijzing(UPDATED_DATUMEINDETOEWIJZING)
            .datumstarttoewijzing(UPDATED_DATUMSTARTTOEWIJZING)
            .datumtoewijzing(UPDATED_DATUMTOEWIJZING)
            .eenheid(UPDATED_EENHEID)
            .frequentie(UPDATED_FREQUENTIE)
            .omvang(UPDATED_OMVANG)
            .redenwijziging(UPDATED_REDENWIJZIGING)
            .toewijzingnummer(UPDATED_TOEWIJZINGNUMMER)
            .wet(UPDATED_WET);

        restToewijzingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedToewijzing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedToewijzing))
            )
            .andExpect(status().isOk());

        // Validate the Toewijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedToewijzingToMatchAllProperties(updatedToewijzing);
    }

    @Test
    @Transactional
    void putNonExistingToewijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toewijzing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restToewijzingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, toewijzing.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(toewijzing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Toewijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchToewijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toewijzing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToewijzingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(toewijzing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Toewijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamToewijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toewijzing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToewijzingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(toewijzing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Toewijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateToewijzingWithPatch() throws Exception {
        // Initialize the database
        toewijzingRepository.saveAndFlush(toewijzing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the toewijzing using partial update
        Toewijzing partialUpdatedToewijzing = new Toewijzing();
        partialUpdatedToewijzing.setId(toewijzing.getId());

        partialUpdatedToewijzing
            .datumeindetoewijzing(UPDATED_DATUMEINDETOEWIJZING)
            .datumstarttoewijzing(UPDATED_DATUMSTARTTOEWIJZING)
            .datumtoewijzing(UPDATED_DATUMTOEWIJZING)
            .eenheid(UPDATED_EENHEID)
            .frequentie(UPDATED_FREQUENTIE)
            .omvang(UPDATED_OMVANG)
            .wet(UPDATED_WET);

        restToewijzingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedToewijzing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedToewijzing))
            )
            .andExpect(status().isOk());

        // Validate the Toewijzing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertToewijzingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedToewijzing, toewijzing),
            getPersistedToewijzing(toewijzing)
        );
    }

    @Test
    @Transactional
    void fullUpdateToewijzingWithPatch() throws Exception {
        // Initialize the database
        toewijzingRepository.saveAndFlush(toewijzing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the toewijzing using partial update
        Toewijzing partialUpdatedToewijzing = new Toewijzing();
        partialUpdatedToewijzing.setId(toewijzing.getId());

        partialUpdatedToewijzing
            .code(UPDATED_CODE)
            .commentaar(UPDATED_COMMENTAAR)
            .datumaanschaf(UPDATED_DATUMAANSCHAF)
            .datumeindetoewijzing(UPDATED_DATUMEINDETOEWIJZING)
            .datumstarttoewijzing(UPDATED_DATUMSTARTTOEWIJZING)
            .datumtoewijzing(UPDATED_DATUMTOEWIJZING)
            .eenheid(UPDATED_EENHEID)
            .frequentie(UPDATED_FREQUENTIE)
            .omvang(UPDATED_OMVANG)
            .redenwijziging(UPDATED_REDENWIJZIGING)
            .toewijzingnummer(UPDATED_TOEWIJZINGNUMMER)
            .wet(UPDATED_WET);

        restToewijzingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedToewijzing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedToewijzing))
            )
            .andExpect(status().isOk());

        // Validate the Toewijzing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertToewijzingUpdatableFieldsEquals(partialUpdatedToewijzing, getPersistedToewijzing(partialUpdatedToewijzing));
    }

    @Test
    @Transactional
    void patchNonExistingToewijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toewijzing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restToewijzingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, toewijzing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(toewijzing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Toewijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchToewijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toewijzing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToewijzingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(toewijzing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Toewijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamToewijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toewijzing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToewijzingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(toewijzing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Toewijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteToewijzing() throws Exception {
        // Initialize the database
        toewijzingRepository.saveAndFlush(toewijzing);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the toewijzing
        restToewijzingMockMvc
            .perform(delete(ENTITY_API_URL_ID, toewijzing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return toewijzingRepository.count();
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

    protected Toewijzing getPersistedToewijzing(Toewijzing toewijzing) {
        return toewijzingRepository.findById(toewijzing.getId()).orElseThrow();
    }

    protected void assertPersistedToewijzingToMatchAllProperties(Toewijzing expectedToewijzing) {
        assertToewijzingAllPropertiesEquals(expectedToewijzing, getPersistedToewijzing(expectedToewijzing));
    }

    protected void assertPersistedToewijzingToMatchUpdatableProperties(Toewijzing expectedToewijzing) {
        assertToewijzingAllUpdatablePropertiesEquals(expectedToewijzing, getPersistedToewijzing(expectedToewijzing));
    }
}
