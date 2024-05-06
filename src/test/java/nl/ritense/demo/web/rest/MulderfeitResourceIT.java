package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MulderfeitAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Mulderfeit;
import nl.ritense.demo.repository.MulderfeitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MulderfeitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MulderfeitResourceIT {

    private static final BigDecimal DEFAULT_BEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAG = new BigDecimal(2);

    private static final LocalDate DEFAULT_BEZWAARAFGEHANDELD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BEZWAARAFGEHANDELD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_BEZWAARINGETROKKEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BEZWAARINGETROKKEN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_BEZWAARTOEGEWEZEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BEZWAARTOEGEWEZEN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_BONNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_BONNUMMER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBETALING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBETALING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMBEZWAAR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEZWAAR = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMGESEPONEERD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMGESEPONEERD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINDIENING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINDIENING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DIENSTCD = "AAAAAAAAAA";
    private static final String UPDATED_DIENSTCD = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISATIE = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISATIE = "BBBBBBBBBB";

    private static final String DEFAULT_OVERTREDING = "AAAAAAAAAA";
    private static final String UPDATED_OVERTREDING = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PARKEERTARIEF = new BigDecimal(1);
    private static final BigDecimal UPDATED_PARKEERTARIEF = new BigDecimal(2);

    private static final String DEFAULT_REDENSEPONEREN = "AAAAAAAAAA";
    private static final String UPDATED_REDENSEPONEREN = "BBBBBBBBBB";

    private static final String DEFAULT_VORDERINGNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_VORDERINGNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mulderfeits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MulderfeitRepository mulderfeitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMulderfeitMockMvc;

    private Mulderfeit mulderfeit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mulderfeit createEntity(EntityManager em) {
        Mulderfeit mulderfeit = new Mulderfeit()
            .bedrag(DEFAULT_BEDRAG)
            .bezwaarafgehandeld(DEFAULT_BEZWAARAFGEHANDELD)
            .bezwaaringetrokken(DEFAULT_BEZWAARINGETROKKEN)
            .bezwaartoegewezen(DEFAULT_BEZWAARTOEGEWEZEN)
            .bonnummer(DEFAULT_BONNUMMER)
            .datumbetaling(DEFAULT_DATUMBETALING)
            .datumbezwaar(DEFAULT_DATUMBEZWAAR)
            .datumgeseponeerd(DEFAULT_DATUMGESEPONEERD)
            .datumindiening(DEFAULT_DATUMINDIENING)
            .dienstcd(DEFAULT_DIENSTCD)
            .organisatie(DEFAULT_ORGANISATIE)
            .overtreding(DEFAULT_OVERTREDING)
            .parkeertarief(DEFAULT_PARKEERTARIEF)
            .redenseponeren(DEFAULT_REDENSEPONEREN)
            .vorderingnummer(DEFAULT_VORDERINGNUMMER);
        return mulderfeit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mulderfeit createUpdatedEntity(EntityManager em) {
        Mulderfeit mulderfeit = new Mulderfeit()
            .bedrag(UPDATED_BEDRAG)
            .bezwaarafgehandeld(UPDATED_BEZWAARAFGEHANDELD)
            .bezwaaringetrokken(UPDATED_BEZWAARINGETROKKEN)
            .bezwaartoegewezen(UPDATED_BEZWAARTOEGEWEZEN)
            .bonnummer(UPDATED_BONNUMMER)
            .datumbetaling(UPDATED_DATUMBETALING)
            .datumbezwaar(UPDATED_DATUMBEZWAAR)
            .datumgeseponeerd(UPDATED_DATUMGESEPONEERD)
            .datumindiening(UPDATED_DATUMINDIENING)
            .dienstcd(UPDATED_DIENSTCD)
            .organisatie(UPDATED_ORGANISATIE)
            .overtreding(UPDATED_OVERTREDING)
            .parkeertarief(UPDATED_PARKEERTARIEF)
            .redenseponeren(UPDATED_REDENSEPONEREN)
            .vorderingnummer(UPDATED_VORDERINGNUMMER);
        return mulderfeit;
    }

    @BeforeEach
    public void initTest() {
        mulderfeit = createEntity(em);
    }

    @Test
    @Transactional
    void createMulderfeit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Mulderfeit
        var returnedMulderfeit = om.readValue(
            restMulderfeitMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mulderfeit)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Mulderfeit.class
        );

        // Validate the Mulderfeit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMulderfeitUpdatableFieldsEquals(returnedMulderfeit, getPersistedMulderfeit(returnedMulderfeit));
    }

    @Test
    @Transactional
    void createMulderfeitWithExistingId() throws Exception {
        // Create the Mulderfeit with an existing ID
        mulderfeit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMulderfeitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mulderfeit)))
            .andExpect(status().isBadRequest());

        // Validate the Mulderfeit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMulderfeits() throws Exception {
        // Initialize the database
        mulderfeitRepository.saveAndFlush(mulderfeit);

        // Get all the mulderfeitList
        restMulderfeitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mulderfeit.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(sameNumber(DEFAULT_BEDRAG))))
            .andExpect(jsonPath("$.[*].bezwaarafgehandeld").value(hasItem(DEFAULT_BEZWAARAFGEHANDELD.toString())))
            .andExpect(jsonPath("$.[*].bezwaaringetrokken").value(hasItem(DEFAULT_BEZWAARINGETROKKEN.toString())))
            .andExpect(jsonPath("$.[*].bezwaartoegewezen").value(hasItem(DEFAULT_BEZWAARTOEGEWEZEN.toString())))
            .andExpect(jsonPath("$.[*].bonnummer").value(hasItem(DEFAULT_BONNUMMER)))
            .andExpect(jsonPath("$.[*].datumbetaling").value(hasItem(DEFAULT_DATUMBETALING.toString())))
            .andExpect(jsonPath("$.[*].datumbezwaar").value(hasItem(DEFAULT_DATUMBEZWAAR.toString())))
            .andExpect(jsonPath("$.[*].datumgeseponeerd").value(hasItem(DEFAULT_DATUMGESEPONEERD.toString())))
            .andExpect(jsonPath("$.[*].datumindiening").value(hasItem(DEFAULT_DATUMINDIENING.toString())))
            .andExpect(jsonPath("$.[*].dienstcd").value(hasItem(DEFAULT_DIENSTCD)))
            .andExpect(jsonPath("$.[*].organisatie").value(hasItem(DEFAULT_ORGANISATIE)))
            .andExpect(jsonPath("$.[*].overtreding").value(hasItem(DEFAULT_OVERTREDING)))
            .andExpect(jsonPath("$.[*].parkeertarief").value(hasItem(sameNumber(DEFAULT_PARKEERTARIEF))))
            .andExpect(jsonPath("$.[*].redenseponeren").value(hasItem(DEFAULT_REDENSEPONEREN)))
            .andExpect(jsonPath("$.[*].vorderingnummer").value(hasItem(DEFAULT_VORDERINGNUMMER)));
    }

    @Test
    @Transactional
    void getMulderfeit() throws Exception {
        // Initialize the database
        mulderfeitRepository.saveAndFlush(mulderfeit);

        // Get the mulderfeit
        restMulderfeitMockMvc
            .perform(get(ENTITY_API_URL_ID, mulderfeit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mulderfeit.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(sameNumber(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.bezwaarafgehandeld").value(DEFAULT_BEZWAARAFGEHANDELD.toString()))
            .andExpect(jsonPath("$.bezwaaringetrokken").value(DEFAULT_BEZWAARINGETROKKEN.toString()))
            .andExpect(jsonPath("$.bezwaartoegewezen").value(DEFAULT_BEZWAARTOEGEWEZEN.toString()))
            .andExpect(jsonPath("$.bonnummer").value(DEFAULT_BONNUMMER))
            .andExpect(jsonPath("$.datumbetaling").value(DEFAULT_DATUMBETALING.toString()))
            .andExpect(jsonPath("$.datumbezwaar").value(DEFAULT_DATUMBEZWAAR.toString()))
            .andExpect(jsonPath("$.datumgeseponeerd").value(DEFAULT_DATUMGESEPONEERD.toString()))
            .andExpect(jsonPath("$.datumindiening").value(DEFAULT_DATUMINDIENING.toString()))
            .andExpect(jsonPath("$.dienstcd").value(DEFAULT_DIENSTCD))
            .andExpect(jsonPath("$.organisatie").value(DEFAULT_ORGANISATIE))
            .andExpect(jsonPath("$.overtreding").value(DEFAULT_OVERTREDING))
            .andExpect(jsonPath("$.parkeertarief").value(sameNumber(DEFAULT_PARKEERTARIEF)))
            .andExpect(jsonPath("$.redenseponeren").value(DEFAULT_REDENSEPONEREN))
            .andExpect(jsonPath("$.vorderingnummer").value(DEFAULT_VORDERINGNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingMulderfeit() throws Exception {
        // Get the mulderfeit
        restMulderfeitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMulderfeit() throws Exception {
        // Initialize the database
        mulderfeitRepository.saveAndFlush(mulderfeit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mulderfeit
        Mulderfeit updatedMulderfeit = mulderfeitRepository.findById(mulderfeit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMulderfeit are not directly saved in db
        em.detach(updatedMulderfeit);
        updatedMulderfeit
            .bedrag(UPDATED_BEDRAG)
            .bezwaarafgehandeld(UPDATED_BEZWAARAFGEHANDELD)
            .bezwaaringetrokken(UPDATED_BEZWAARINGETROKKEN)
            .bezwaartoegewezen(UPDATED_BEZWAARTOEGEWEZEN)
            .bonnummer(UPDATED_BONNUMMER)
            .datumbetaling(UPDATED_DATUMBETALING)
            .datumbezwaar(UPDATED_DATUMBEZWAAR)
            .datumgeseponeerd(UPDATED_DATUMGESEPONEERD)
            .datumindiening(UPDATED_DATUMINDIENING)
            .dienstcd(UPDATED_DIENSTCD)
            .organisatie(UPDATED_ORGANISATIE)
            .overtreding(UPDATED_OVERTREDING)
            .parkeertarief(UPDATED_PARKEERTARIEF)
            .redenseponeren(UPDATED_REDENSEPONEREN)
            .vorderingnummer(UPDATED_VORDERINGNUMMER);

        restMulderfeitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMulderfeit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMulderfeit))
            )
            .andExpect(status().isOk());

        // Validate the Mulderfeit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMulderfeitToMatchAllProperties(updatedMulderfeit);
    }

    @Test
    @Transactional
    void putNonExistingMulderfeit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mulderfeit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMulderfeitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mulderfeit.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mulderfeit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mulderfeit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMulderfeit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mulderfeit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMulderfeitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mulderfeit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mulderfeit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMulderfeit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mulderfeit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMulderfeitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mulderfeit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mulderfeit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMulderfeitWithPatch() throws Exception {
        // Initialize the database
        mulderfeitRepository.saveAndFlush(mulderfeit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mulderfeit using partial update
        Mulderfeit partialUpdatedMulderfeit = new Mulderfeit();
        partialUpdatedMulderfeit.setId(mulderfeit.getId());

        partialUpdatedMulderfeit
            .bezwaarafgehandeld(UPDATED_BEZWAARAFGEHANDELD)
            .bezwaaringetrokken(UPDATED_BEZWAARINGETROKKEN)
            .datumbezwaar(UPDATED_DATUMBEZWAAR)
            .datumgeseponeerd(UPDATED_DATUMGESEPONEERD)
            .overtreding(UPDATED_OVERTREDING);

        restMulderfeitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMulderfeit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMulderfeit))
            )
            .andExpect(status().isOk());

        // Validate the Mulderfeit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMulderfeitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMulderfeit, mulderfeit),
            getPersistedMulderfeit(mulderfeit)
        );
    }

    @Test
    @Transactional
    void fullUpdateMulderfeitWithPatch() throws Exception {
        // Initialize the database
        mulderfeitRepository.saveAndFlush(mulderfeit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mulderfeit using partial update
        Mulderfeit partialUpdatedMulderfeit = new Mulderfeit();
        partialUpdatedMulderfeit.setId(mulderfeit.getId());

        partialUpdatedMulderfeit
            .bedrag(UPDATED_BEDRAG)
            .bezwaarafgehandeld(UPDATED_BEZWAARAFGEHANDELD)
            .bezwaaringetrokken(UPDATED_BEZWAARINGETROKKEN)
            .bezwaartoegewezen(UPDATED_BEZWAARTOEGEWEZEN)
            .bonnummer(UPDATED_BONNUMMER)
            .datumbetaling(UPDATED_DATUMBETALING)
            .datumbezwaar(UPDATED_DATUMBEZWAAR)
            .datumgeseponeerd(UPDATED_DATUMGESEPONEERD)
            .datumindiening(UPDATED_DATUMINDIENING)
            .dienstcd(UPDATED_DIENSTCD)
            .organisatie(UPDATED_ORGANISATIE)
            .overtreding(UPDATED_OVERTREDING)
            .parkeertarief(UPDATED_PARKEERTARIEF)
            .redenseponeren(UPDATED_REDENSEPONEREN)
            .vorderingnummer(UPDATED_VORDERINGNUMMER);

        restMulderfeitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMulderfeit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMulderfeit))
            )
            .andExpect(status().isOk());

        // Validate the Mulderfeit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMulderfeitUpdatableFieldsEquals(partialUpdatedMulderfeit, getPersistedMulderfeit(partialUpdatedMulderfeit));
    }

    @Test
    @Transactional
    void patchNonExistingMulderfeit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mulderfeit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMulderfeitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mulderfeit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mulderfeit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mulderfeit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMulderfeit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mulderfeit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMulderfeitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mulderfeit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mulderfeit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMulderfeit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mulderfeit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMulderfeitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(mulderfeit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mulderfeit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMulderfeit() throws Exception {
        // Initialize the database
        mulderfeitRepository.saveAndFlush(mulderfeit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the mulderfeit
        restMulderfeitMockMvc
            .perform(delete(ENTITY_API_URL_ID, mulderfeit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return mulderfeitRepository.count();
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

    protected Mulderfeit getPersistedMulderfeit(Mulderfeit mulderfeit) {
        return mulderfeitRepository.findById(mulderfeit.getId()).orElseThrow();
    }

    protected void assertPersistedMulderfeitToMatchAllProperties(Mulderfeit expectedMulderfeit) {
        assertMulderfeitAllPropertiesEquals(expectedMulderfeit, getPersistedMulderfeit(expectedMulderfeit));
    }

    protected void assertPersistedMulderfeitToMatchUpdatableProperties(Mulderfeit expectedMulderfeit) {
        assertMulderfeitAllUpdatablePropertiesEquals(expectedMulderfeit, getPersistedMulderfeit(expectedMulderfeit));
    }
}
