package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerzoekomtoewijzingAsserts.*;
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
import nl.ritense.demo.domain.Verzoekomtoewijzing;
import nl.ritense.demo.repository.VerzoekomtoewijzingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerzoekomtoewijzingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerzoekomtoewijzingResourceIT {

    private static final String DEFAULT_BESCHIKKINGSNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_BESCHIKKINGSNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAAR = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAAR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMEINDETOEWIJZING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDETOEWIJZING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINGANGBESCHIKKING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINGANGBESCHIKKING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINGANGTOEWIJZING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINGANGTOEWIJZING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMONTVANGST = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMONTVANGST = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EENHEID = "AAAAAAAAAA";
    private static final String UPDATED_EENHEID = "BBBBBBBBBB";

    private static final String DEFAULT_FREQUENTIE = "AAAAAAAAAA";
    private static final String UPDATED_FREQUENTIE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RAAMCONTRACT = false;
    private static final Boolean UPDATED_RAAMCONTRACT = true;

    private static final String DEFAULT_REFERENTIEAANBIEDER = "AAAAAAAAAA";
    private static final String UPDATED_REFERENTIEAANBIEDER = "BBBBBBBBBB";

    private static final String DEFAULT_SOORTVERWIJZER = "AAAAAAAAAA";
    private static final String UPDATED_SOORTVERWIJZER = "BBBBBBBBBB";

    private static final String DEFAULT_VERWIJZER = "AAAAAAAAAA";
    private static final String UPDATED_VERWIJZER = "BBBBBBBBBB";

    private static final String DEFAULT_VOLUME = "AAAAAAAAAA";
    private static final String UPDATED_VOLUME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verzoekomtoewijzings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerzoekomtoewijzingRepository verzoekomtoewijzingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerzoekomtoewijzingMockMvc;

    private Verzoekomtoewijzing verzoekomtoewijzing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verzoekomtoewijzing createEntity(EntityManager em) {
        Verzoekomtoewijzing verzoekomtoewijzing = new Verzoekomtoewijzing()
            .beschikkingsnummer(DEFAULT_BESCHIKKINGSNUMMER)
            .commentaar(DEFAULT_COMMENTAAR)
            .datumeindetoewijzing(DEFAULT_DATUMEINDETOEWIJZING)
            .datumingangbeschikking(DEFAULT_DATUMINGANGBESCHIKKING)
            .datumingangtoewijzing(DEFAULT_DATUMINGANGTOEWIJZING)
            .datumontvangst(DEFAULT_DATUMONTVANGST)
            .eenheid(DEFAULT_EENHEID)
            .frequentie(DEFAULT_FREQUENTIE)
            .raamcontract(DEFAULT_RAAMCONTRACT)
            .referentieaanbieder(DEFAULT_REFERENTIEAANBIEDER)
            .soortverwijzer(DEFAULT_SOORTVERWIJZER)
            .verwijzer(DEFAULT_VERWIJZER)
            .volume(DEFAULT_VOLUME);
        return verzoekomtoewijzing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verzoekomtoewijzing createUpdatedEntity(EntityManager em) {
        Verzoekomtoewijzing verzoekomtoewijzing = new Verzoekomtoewijzing()
            .beschikkingsnummer(UPDATED_BESCHIKKINGSNUMMER)
            .commentaar(UPDATED_COMMENTAAR)
            .datumeindetoewijzing(UPDATED_DATUMEINDETOEWIJZING)
            .datumingangbeschikking(UPDATED_DATUMINGANGBESCHIKKING)
            .datumingangtoewijzing(UPDATED_DATUMINGANGTOEWIJZING)
            .datumontvangst(UPDATED_DATUMONTVANGST)
            .eenheid(UPDATED_EENHEID)
            .frequentie(UPDATED_FREQUENTIE)
            .raamcontract(UPDATED_RAAMCONTRACT)
            .referentieaanbieder(UPDATED_REFERENTIEAANBIEDER)
            .soortverwijzer(UPDATED_SOORTVERWIJZER)
            .verwijzer(UPDATED_VERWIJZER)
            .volume(UPDATED_VOLUME);
        return verzoekomtoewijzing;
    }

    @BeforeEach
    public void initTest() {
        verzoekomtoewijzing = createEntity(em);
    }

    @Test
    @Transactional
    void createVerzoekomtoewijzing() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verzoekomtoewijzing
        var returnedVerzoekomtoewijzing = om.readValue(
            restVerzoekomtoewijzingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verzoekomtoewijzing)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verzoekomtoewijzing.class
        );

        // Validate the Verzoekomtoewijzing in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerzoekomtoewijzingUpdatableFieldsEquals(
            returnedVerzoekomtoewijzing,
            getPersistedVerzoekomtoewijzing(returnedVerzoekomtoewijzing)
        );
    }

    @Test
    @Transactional
    void createVerzoekomtoewijzingWithExistingId() throws Exception {
        // Create the Verzoekomtoewijzing with an existing ID
        verzoekomtoewijzing.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerzoekomtoewijzingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verzoekomtoewijzing)))
            .andExpect(status().isBadRequest());

        // Validate the Verzoekomtoewijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerzoekomtoewijzings() throws Exception {
        // Initialize the database
        verzoekomtoewijzingRepository.saveAndFlush(verzoekomtoewijzing);

        // Get all the verzoekomtoewijzingList
        restVerzoekomtoewijzingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verzoekomtoewijzing.getId().intValue())))
            .andExpect(jsonPath("$.[*].beschikkingsnummer").value(hasItem(DEFAULT_BESCHIKKINGSNUMMER)))
            .andExpect(jsonPath("$.[*].commentaar").value(hasItem(DEFAULT_COMMENTAAR)))
            .andExpect(jsonPath("$.[*].datumeindetoewijzing").value(hasItem(DEFAULT_DATUMEINDETOEWIJZING.toString())))
            .andExpect(jsonPath("$.[*].datumingangbeschikking").value(hasItem(DEFAULT_DATUMINGANGBESCHIKKING.toString())))
            .andExpect(jsonPath("$.[*].datumingangtoewijzing").value(hasItem(DEFAULT_DATUMINGANGTOEWIJZING.toString())))
            .andExpect(jsonPath("$.[*].datumontvangst").value(hasItem(DEFAULT_DATUMONTVANGST.toString())))
            .andExpect(jsonPath("$.[*].eenheid").value(hasItem(DEFAULT_EENHEID)))
            .andExpect(jsonPath("$.[*].frequentie").value(hasItem(DEFAULT_FREQUENTIE)))
            .andExpect(jsonPath("$.[*].raamcontract").value(hasItem(DEFAULT_RAAMCONTRACT.booleanValue())))
            .andExpect(jsonPath("$.[*].referentieaanbieder").value(hasItem(DEFAULT_REFERENTIEAANBIEDER)))
            .andExpect(jsonPath("$.[*].soortverwijzer").value(hasItem(DEFAULT_SOORTVERWIJZER)))
            .andExpect(jsonPath("$.[*].verwijzer").value(hasItem(DEFAULT_VERWIJZER)))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME)));
    }

    @Test
    @Transactional
    void getVerzoekomtoewijzing() throws Exception {
        // Initialize the database
        verzoekomtoewijzingRepository.saveAndFlush(verzoekomtoewijzing);

        // Get the verzoekomtoewijzing
        restVerzoekomtoewijzingMockMvc
            .perform(get(ENTITY_API_URL_ID, verzoekomtoewijzing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verzoekomtoewijzing.getId().intValue()))
            .andExpect(jsonPath("$.beschikkingsnummer").value(DEFAULT_BESCHIKKINGSNUMMER))
            .andExpect(jsonPath("$.commentaar").value(DEFAULT_COMMENTAAR))
            .andExpect(jsonPath("$.datumeindetoewijzing").value(DEFAULT_DATUMEINDETOEWIJZING.toString()))
            .andExpect(jsonPath("$.datumingangbeschikking").value(DEFAULT_DATUMINGANGBESCHIKKING.toString()))
            .andExpect(jsonPath("$.datumingangtoewijzing").value(DEFAULT_DATUMINGANGTOEWIJZING.toString()))
            .andExpect(jsonPath("$.datumontvangst").value(DEFAULT_DATUMONTVANGST.toString()))
            .andExpect(jsonPath("$.eenheid").value(DEFAULT_EENHEID))
            .andExpect(jsonPath("$.frequentie").value(DEFAULT_FREQUENTIE))
            .andExpect(jsonPath("$.raamcontract").value(DEFAULT_RAAMCONTRACT.booleanValue()))
            .andExpect(jsonPath("$.referentieaanbieder").value(DEFAULT_REFERENTIEAANBIEDER))
            .andExpect(jsonPath("$.soortverwijzer").value(DEFAULT_SOORTVERWIJZER))
            .andExpect(jsonPath("$.verwijzer").value(DEFAULT_VERWIJZER))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME));
    }

    @Test
    @Transactional
    void getNonExistingVerzoekomtoewijzing() throws Exception {
        // Get the verzoekomtoewijzing
        restVerzoekomtoewijzingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerzoekomtoewijzing() throws Exception {
        // Initialize the database
        verzoekomtoewijzingRepository.saveAndFlush(verzoekomtoewijzing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verzoekomtoewijzing
        Verzoekomtoewijzing updatedVerzoekomtoewijzing = verzoekomtoewijzingRepository.findById(verzoekomtoewijzing.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerzoekomtoewijzing are not directly saved in db
        em.detach(updatedVerzoekomtoewijzing);
        updatedVerzoekomtoewijzing
            .beschikkingsnummer(UPDATED_BESCHIKKINGSNUMMER)
            .commentaar(UPDATED_COMMENTAAR)
            .datumeindetoewijzing(UPDATED_DATUMEINDETOEWIJZING)
            .datumingangbeschikking(UPDATED_DATUMINGANGBESCHIKKING)
            .datumingangtoewijzing(UPDATED_DATUMINGANGTOEWIJZING)
            .datumontvangst(UPDATED_DATUMONTVANGST)
            .eenheid(UPDATED_EENHEID)
            .frequentie(UPDATED_FREQUENTIE)
            .raamcontract(UPDATED_RAAMCONTRACT)
            .referentieaanbieder(UPDATED_REFERENTIEAANBIEDER)
            .soortverwijzer(UPDATED_SOORTVERWIJZER)
            .verwijzer(UPDATED_VERWIJZER)
            .volume(UPDATED_VOLUME);

        restVerzoekomtoewijzingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerzoekomtoewijzing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerzoekomtoewijzing))
            )
            .andExpect(status().isOk());

        // Validate the Verzoekomtoewijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerzoekomtoewijzingToMatchAllProperties(updatedVerzoekomtoewijzing);
    }

    @Test
    @Transactional
    void putNonExistingVerzoekomtoewijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzoekomtoewijzing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerzoekomtoewijzingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verzoekomtoewijzing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verzoekomtoewijzing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzoekomtoewijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerzoekomtoewijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzoekomtoewijzing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzoekomtoewijzingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verzoekomtoewijzing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzoekomtoewijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerzoekomtoewijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzoekomtoewijzing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzoekomtoewijzingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verzoekomtoewijzing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verzoekomtoewijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerzoekomtoewijzingWithPatch() throws Exception {
        // Initialize the database
        verzoekomtoewijzingRepository.saveAndFlush(verzoekomtoewijzing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verzoekomtoewijzing using partial update
        Verzoekomtoewijzing partialUpdatedVerzoekomtoewijzing = new Verzoekomtoewijzing();
        partialUpdatedVerzoekomtoewijzing.setId(verzoekomtoewijzing.getId());

        partialUpdatedVerzoekomtoewijzing
            .beschikkingsnummer(UPDATED_BESCHIKKINGSNUMMER)
            .commentaar(UPDATED_COMMENTAAR)
            .eenheid(UPDATED_EENHEID)
            .frequentie(UPDATED_FREQUENTIE)
            .raamcontract(UPDATED_RAAMCONTRACT)
            .referentieaanbieder(UPDATED_REFERENTIEAANBIEDER)
            .verwijzer(UPDATED_VERWIJZER)
            .volume(UPDATED_VOLUME);

        restVerzoekomtoewijzingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerzoekomtoewijzing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerzoekomtoewijzing))
            )
            .andExpect(status().isOk());

        // Validate the Verzoekomtoewijzing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerzoekomtoewijzingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerzoekomtoewijzing, verzoekomtoewijzing),
            getPersistedVerzoekomtoewijzing(verzoekomtoewijzing)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerzoekomtoewijzingWithPatch() throws Exception {
        // Initialize the database
        verzoekomtoewijzingRepository.saveAndFlush(verzoekomtoewijzing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verzoekomtoewijzing using partial update
        Verzoekomtoewijzing partialUpdatedVerzoekomtoewijzing = new Verzoekomtoewijzing();
        partialUpdatedVerzoekomtoewijzing.setId(verzoekomtoewijzing.getId());

        partialUpdatedVerzoekomtoewijzing
            .beschikkingsnummer(UPDATED_BESCHIKKINGSNUMMER)
            .commentaar(UPDATED_COMMENTAAR)
            .datumeindetoewijzing(UPDATED_DATUMEINDETOEWIJZING)
            .datumingangbeschikking(UPDATED_DATUMINGANGBESCHIKKING)
            .datumingangtoewijzing(UPDATED_DATUMINGANGTOEWIJZING)
            .datumontvangst(UPDATED_DATUMONTVANGST)
            .eenheid(UPDATED_EENHEID)
            .frequentie(UPDATED_FREQUENTIE)
            .raamcontract(UPDATED_RAAMCONTRACT)
            .referentieaanbieder(UPDATED_REFERENTIEAANBIEDER)
            .soortverwijzer(UPDATED_SOORTVERWIJZER)
            .verwijzer(UPDATED_VERWIJZER)
            .volume(UPDATED_VOLUME);

        restVerzoekomtoewijzingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerzoekomtoewijzing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerzoekomtoewijzing))
            )
            .andExpect(status().isOk());

        // Validate the Verzoekomtoewijzing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerzoekomtoewijzingUpdatableFieldsEquals(
            partialUpdatedVerzoekomtoewijzing,
            getPersistedVerzoekomtoewijzing(partialUpdatedVerzoekomtoewijzing)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVerzoekomtoewijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzoekomtoewijzing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerzoekomtoewijzingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verzoekomtoewijzing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verzoekomtoewijzing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzoekomtoewijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerzoekomtoewijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzoekomtoewijzing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzoekomtoewijzingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verzoekomtoewijzing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzoekomtoewijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerzoekomtoewijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzoekomtoewijzing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzoekomtoewijzingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verzoekomtoewijzing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verzoekomtoewijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerzoekomtoewijzing() throws Exception {
        // Initialize the database
        verzoekomtoewijzingRepository.saveAndFlush(verzoekomtoewijzing);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verzoekomtoewijzing
        restVerzoekomtoewijzingMockMvc
            .perform(delete(ENTITY_API_URL_ID, verzoekomtoewijzing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verzoekomtoewijzingRepository.count();
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

    protected Verzoekomtoewijzing getPersistedVerzoekomtoewijzing(Verzoekomtoewijzing verzoekomtoewijzing) {
        return verzoekomtoewijzingRepository.findById(verzoekomtoewijzing.getId()).orElseThrow();
    }

    protected void assertPersistedVerzoekomtoewijzingToMatchAllProperties(Verzoekomtoewijzing expectedVerzoekomtoewijzing) {
        assertVerzoekomtoewijzingAllPropertiesEquals(
            expectedVerzoekomtoewijzing,
            getPersistedVerzoekomtoewijzing(expectedVerzoekomtoewijzing)
        );
    }

    protected void assertPersistedVerzoekomtoewijzingToMatchUpdatableProperties(Verzoekomtoewijzing expectedVerzoekomtoewijzing) {
        assertVerzoekomtoewijzingAllUpdatablePropertiesEquals(
            expectedVerzoekomtoewijzing,
            getPersistedVerzoekomtoewijzing(expectedVerzoekomtoewijzing)
        );
    }
}
