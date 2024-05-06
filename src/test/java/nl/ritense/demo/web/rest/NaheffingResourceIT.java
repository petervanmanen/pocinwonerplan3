package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NaheffingAsserts.*;
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
import nl.ritense.demo.domain.Naheffing;
import nl.ritense.demo.repository.NaheffingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NaheffingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NaheffingResourceIT {

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

    private static final Boolean DEFAULT_FISCAAL = false;
    private static final Boolean UPDATED_FISCAAL = true;

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

    private static final String ENTITY_API_URL = "/api/naheffings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NaheffingRepository naheffingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNaheffingMockMvc;

    private Naheffing naheffing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Naheffing createEntity(EntityManager em) {
        Naheffing naheffing = new Naheffing()
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
            .fiscaal(DEFAULT_FISCAAL)
            .organisatie(DEFAULT_ORGANISATIE)
            .overtreding(DEFAULT_OVERTREDING)
            .parkeertarief(DEFAULT_PARKEERTARIEF)
            .redenseponeren(DEFAULT_REDENSEPONEREN)
            .vorderingnummer(DEFAULT_VORDERINGNUMMER);
        return naheffing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Naheffing createUpdatedEntity(EntityManager em) {
        Naheffing naheffing = new Naheffing()
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
            .fiscaal(UPDATED_FISCAAL)
            .organisatie(UPDATED_ORGANISATIE)
            .overtreding(UPDATED_OVERTREDING)
            .parkeertarief(UPDATED_PARKEERTARIEF)
            .redenseponeren(UPDATED_REDENSEPONEREN)
            .vorderingnummer(UPDATED_VORDERINGNUMMER);
        return naheffing;
    }

    @BeforeEach
    public void initTest() {
        naheffing = createEntity(em);
    }

    @Test
    @Transactional
    void createNaheffing() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Naheffing
        var returnedNaheffing = om.readValue(
            restNaheffingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(naheffing)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Naheffing.class
        );

        // Validate the Naheffing in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNaheffingUpdatableFieldsEquals(returnedNaheffing, getPersistedNaheffing(returnedNaheffing));
    }

    @Test
    @Transactional
    void createNaheffingWithExistingId() throws Exception {
        // Create the Naheffing with an existing ID
        naheffing.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNaheffingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(naheffing)))
            .andExpect(status().isBadRequest());

        // Validate the Naheffing in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNaheffings() throws Exception {
        // Initialize the database
        naheffingRepository.saveAndFlush(naheffing);

        // Get all the naheffingList
        restNaheffingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naheffing.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].fiscaal").value(hasItem(DEFAULT_FISCAAL.booleanValue())))
            .andExpect(jsonPath("$.[*].organisatie").value(hasItem(DEFAULT_ORGANISATIE)))
            .andExpect(jsonPath("$.[*].overtreding").value(hasItem(DEFAULT_OVERTREDING)))
            .andExpect(jsonPath("$.[*].parkeertarief").value(hasItem(sameNumber(DEFAULT_PARKEERTARIEF))))
            .andExpect(jsonPath("$.[*].redenseponeren").value(hasItem(DEFAULT_REDENSEPONEREN)))
            .andExpect(jsonPath("$.[*].vorderingnummer").value(hasItem(DEFAULT_VORDERINGNUMMER)));
    }

    @Test
    @Transactional
    void getNaheffing() throws Exception {
        // Initialize the database
        naheffingRepository.saveAndFlush(naheffing);

        // Get the naheffing
        restNaheffingMockMvc
            .perform(get(ENTITY_API_URL_ID, naheffing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(naheffing.getId().intValue()))
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
            .andExpect(jsonPath("$.fiscaal").value(DEFAULT_FISCAAL.booleanValue()))
            .andExpect(jsonPath("$.organisatie").value(DEFAULT_ORGANISATIE))
            .andExpect(jsonPath("$.overtreding").value(DEFAULT_OVERTREDING))
            .andExpect(jsonPath("$.parkeertarief").value(sameNumber(DEFAULT_PARKEERTARIEF)))
            .andExpect(jsonPath("$.redenseponeren").value(DEFAULT_REDENSEPONEREN))
            .andExpect(jsonPath("$.vorderingnummer").value(DEFAULT_VORDERINGNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingNaheffing() throws Exception {
        // Get the naheffing
        restNaheffingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNaheffing() throws Exception {
        // Initialize the database
        naheffingRepository.saveAndFlush(naheffing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the naheffing
        Naheffing updatedNaheffing = naheffingRepository.findById(naheffing.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNaheffing are not directly saved in db
        em.detach(updatedNaheffing);
        updatedNaheffing
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
            .fiscaal(UPDATED_FISCAAL)
            .organisatie(UPDATED_ORGANISATIE)
            .overtreding(UPDATED_OVERTREDING)
            .parkeertarief(UPDATED_PARKEERTARIEF)
            .redenseponeren(UPDATED_REDENSEPONEREN)
            .vorderingnummer(UPDATED_VORDERINGNUMMER);

        restNaheffingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNaheffing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNaheffing))
            )
            .andExpect(status().isOk());

        // Validate the Naheffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNaheffingToMatchAllProperties(updatedNaheffing);
    }

    @Test
    @Transactional
    void putNonExistingNaheffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naheffing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNaheffingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, naheffing.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(naheffing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naheffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNaheffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naheffing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaheffingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(naheffing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naheffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNaheffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naheffing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaheffingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(naheffing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Naheffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNaheffingWithPatch() throws Exception {
        // Initialize the database
        naheffingRepository.saveAndFlush(naheffing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the naheffing using partial update
        Naheffing partialUpdatedNaheffing = new Naheffing();
        partialUpdatedNaheffing.setId(naheffing.getId());

        partialUpdatedNaheffing
            .bedrag(UPDATED_BEDRAG)
            .bezwaarafgehandeld(UPDATED_BEZWAARAFGEHANDELD)
            .bezwaaringetrokken(UPDATED_BEZWAARINGETROKKEN)
            .bezwaartoegewezen(UPDATED_BEZWAARTOEGEWEZEN)
            .bonnummer(UPDATED_BONNUMMER)
            .datumbezwaar(UPDATED_DATUMBEZWAAR)
            .datumindiening(UPDATED_DATUMINDIENING)
            .dienstcd(UPDATED_DIENSTCD)
            .fiscaal(UPDATED_FISCAAL)
            .parkeertarief(UPDATED_PARKEERTARIEF)
            .redenseponeren(UPDATED_REDENSEPONEREN);

        restNaheffingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNaheffing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNaheffing))
            )
            .andExpect(status().isOk());

        // Validate the Naheffing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNaheffingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNaheffing, naheffing),
            getPersistedNaheffing(naheffing)
        );
    }

    @Test
    @Transactional
    void fullUpdateNaheffingWithPatch() throws Exception {
        // Initialize the database
        naheffingRepository.saveAndFlush(naheffing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the naheffing using partial update
        Naheffing partialUpdatedNaheffing = new Naheffing();
        partialUpdatedNaheffing.setId(naheffing.getId());

        partialUpdatedNaheffing
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
            .fiscaal(UPDATED_FISCAAL)
            .organisatie(UPDATED_ORGANISATIE)
            .overtreding(UPDATED_OVERTREDING)
            .parkeertarief(UPDATED_PARKEERTARIEF)
            .redenseponeren(UPDATED_REDENSEPONEREN)
            .vorderingnummer(UPDATED_VORDERINGNUMMER);

        restNaheffingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNaheffing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNaheffing))
            )
            .andExpect(status().isOk());

        // Validate the Naheffing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNaheffingUpdatableFieldsEquals(partialUpdatedNaheffing, getPersistedNaheffing(partialUpdatedNaheffing));
    }

    @Test
    @Transactional
    void patchNonExistingNaheffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naheffing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNaheffingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, naheffing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(naheffing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naheffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNaheffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naheffing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaheffingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(naheffing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naheffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNaheffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naheffing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaheffingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(naheffing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Naheffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNaheffing() throws Exception {
        // Initialize the database
        naheffingRepository.saveAndFlush(naheffing);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the naheffing
        restNaheffingMockMvc
            .perform(delete(ENTITY_API_URL_ID, naheffing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return naheffingRepository.count();
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

    protected Naheffing getPersistedNaheffing(Naheffing naheffing) {
        return naheffingRepository.findById(naheffing.getId()).orElseThrow();
    }

    protected void assertPersistedNaheffingToMatchAllProperties(Naheffing expectedNaheffing) {
        assertNaheffingAllPropertiesEquals(expectedNaheffing, getPersistedNaheffing(expectedNaheffing));
    }

    protected void assertPersistedNaheffingToMatchUpdatableProperties(Naheffing expectedNaheffing) {
        assertNaheffingAllUpdatablePropertiesEquals(expectedNaheffing, getPersistedNaheffing(expectedNaheffing));
    }
}
