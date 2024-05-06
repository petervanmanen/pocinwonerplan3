package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KostenAsserts.*;
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
import nl.ritense.demo.domain.Kosten;
import nl.ritense.demo.repository.KostenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KostenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KostenResourceIT {

    private static final String DEFAULT_AANGEMAAKTDOOR = "AAAAAAAAAA";
    private static final String UPDATED_AANGEMAAKTDOOR = "BBBBBBBBBB";

    private static final String DEFAULT_AANTAL = "AAAAAAAAAA";
    private static final String UPDATED_AANTAL = "BBBBBBBBBB";

    private static final String DEFAULT_BEDRAG = "AAAAAAAAAA";
    private static final String UPDATED_BEDRAG = "BBBBBBBBBB";

    private static final String DEFAULT_BEDRAGTOTAAL = "AAAAAAAAAA";
    private static final String UPDATED_BEDRAGTOTAAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMAANMAAK = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANMAAK = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMMUTATIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMMUTATIE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EENHEID = "AAAAAAAAAA";
    private static final String UPDATED_EENHEID = "BBBBBBBBBB";

    private static final String DEFAULT_GEACCORDEERD = "AAAAAAAAAA";
    private static final String UPDATED_GEACCORDEERD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_GEFACTUREERDOP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GEFACTUREERDOP = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEMUTEERDDOOR = "AAAAAAAAAA";
    private static final String UPDATED_GEMUTEERDDOOR = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_OPBASISVANGRONDSLAG = "AAAAAAAAAA";
    private static final String UPDATED_OPBASISVANGRONDSLAG = "BBBBBBBBBB";

    private static final String DEFAULT_TARIEF = "AAAAAAAAAA";
    private static final String UPDATED_TARIEF = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VASTGESTELDBEDRAG = "AAAAAAAAAA";
    private static final String UPDATED_VASTGESTELDBEDRAG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kostens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KostenRepository kostenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKostenMockMvc;

    private Kosten kosten;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kosten createEntity(EntityManager em) {
        Kosten kosten = new Kosten()
            .aangemaaktdoor(DEFAULT_AANGEMAAKTDOOR)
            .aantal(DEFAULT_AANTAL)
            .bedrag(DEFAULT_BEDRAG)
            .bedragtotaal(DEFAULT_BEDRAGTOTAAL)
            .datumaanmaak(DEFAULT_DATUMAANMAAK)
            .datummutatie(DEFAULT_DATUMMUTATIE)
            .eenheid(DEFAULT_EENHEID)
            .geaccordeerd(DEFAULT_GEACCORDEERD)
            .gefactureerdop(DEFAULT_GEFACTUREERDOP)
            .gemuteerddoor(DEFAULT_GEMUTEERDDOOR)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .opbasisvangrondslag(DEFAULT_OPBASISVANGRONDSLAG)
            .tarief(DEFAULT_TARIEF)
            .type(DEFAULT_TYPE)
            .vastgesteldbedrag(DEFAULT_VASTGESTELDBEDRAG);
        return kosten;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kosten createUpdatedEntity(EntityManager em) {
        Kosten kosten = new Kosten()
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .aantal(UPDATED_AANTAL)
            .bedrag(UPDATED_BEDRAG)
            .bedragtotaal(UPDATED_BEDRAGTOTAAL)
            .datumaanmaak(UPDATED_DATUMAANMAAK)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .eenheid(UPDATED_EENHEID)
            .geaccordeerd(UPDATED_GEACCORDEERD)
            .gefactureerdop(UPDATED_GEFACTUREERDOP)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .opbasisvangrondslag(UPDATED_OPBASISVANGRONDSLAG)
            .tarief(UPDATED_TARIEF)
            .type(UPDATED_TYPE)
            .vastgesteldbedrag(UPDATED_VASTGESTELDBEDRAG);
        return kosten;
    }

    @BeforeEach
    public void initTest() {
        kosten = createEntity(em);
    }

    @Test
    @Transactional
    void createKosten() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kosten
        var returnedKosten = om.readValue(
            restKostenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kosten)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kosten.class
        );

        // Validate the Kosten in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKostenUpdatableFieldsEquals(returnedKosten, getPersistedKosten(returnedKosten));
    }

    @Test
    @Transactional
    void createKostenWithExistingId() throws Exception {
        // Create the Kosten with an existing ID
        kosten.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKostenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kosten)))
            .andExpect(status().isBadRequest());

        // Validate the Kosten in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKostens() throws Exception {
        // Initialize the database
        kostenRepository.saveAndFlush(kosten);

        // Get all the kostenList
        restKostenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kosten.getId().intValue())))
            .andExpect(jsonPath("$.[*].aangemaaktdoor").value(hasItem(DEFAULT_AANGEMAAKTDOOR)))
            .andExpect(jsonPath("$.[*].aantal").value(hasItem(DEFAULT_AANTAL)))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.[*].bedragtotaal").value(hasItem(DEFAULT_BEDRAGTOTAAL)))
            .andExpect(jsonPath("$.[*].datumaanmaak").value(hasItem(DEFAULT_DATUMAANMAAK.toString())))
            .andExpect(jsonPath("$.[*].datummutatie").value(hasItem(DEFAULT_DATUMMUTATIE.toString())))
            .andExpect(jsonPath("$.[*].eenheid").value(hasItem(DEFAULT_EENHEID)))
            .andExpect(jsonPath("$.[*].geaccordeerd").value(hasItem(DEFAULT_GEACCORDEERD)))
            .andExpect(jsonPath("$.[*].gefactureerdop").value(hasItem(DEFAULT_GEFACTUREERDOP.toString())))
            .andExpect(jsonPath("$.[*].gemuteerddoor").value(hasItem(DEFAULT_GEMUTEERDDOOR)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].opbasisvangrondslag").value(hasItem(DEFAULT_OPBASISVANGRONDSLAG)))
            .andExpect(jsonPath("$.[*].tarief").value(hasItem(DEFAULT_TARIEF)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].vastgesteldbedrag").value(hasItem(DEFAULT_VASTGESTELDBEDRAG)));
    }

    @Test
    @Transactional
    void getKosten() throws Exception {
        // Initialize the database
        kostenRepository.saveAndFlush(kosten);

        // Get the kosten
        restKostenMockMvc
            .perform(get(ENTITY_API_URL_ID, kosten.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kosten.getId().intValue()))
            .andExpect(jsonPath("$.aangemaaktdoor").value(DEFAULT_AANGEMAAKTDOOR))
            .andExpect(jsonPath("$.aantal").value(DEFAULT_AANTAL))
            .andExpect(jsonPath("$.bedrag").value(DEFAULT_BEDRAG))
            .andExpect(jsonPath("$.bedragtotaal").value(DEFAULT_BEDRAGTOTAAL))
            .andExpect(jsonPath("$.datumaanmaak").value(DEFAULT_DATUMAANMAAK.toString()))
            .andExpect(jsonPath("$.datummutatie").value(DEFAULT_DATUMMUTATIE.toString()))
            .andExpect(jsonPath("$.eenheid").value(DEFAULT_EENHEID))
            .andExpect(jsonPath("$.geaccordeerd").value(DEFAULT_GEACCORDEERD))
            .andExpect(jsonPath("$.gefactureerdop").value(DEFAULT_GEFACTUREERDOP.toString()))
            .andExpect(jsonPath("$.gemuteerddoor").value(DEFAULT_GEMUTEERDDOOR))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.opbasisvangrondslag").value(DEFAULT_OPBASISVANGRONDSLAG))
            .andExpect(jsonPath("$.tarief").value(DEFAULT_TARIEF))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.vastgesteldbedrag").value(DEFAULT_VASTGESTELDBEDRAG));
    }

    @Test
    @Transactional
    void getNonExistingKosten() throws Exception {
        // Get the kosten
        restKostenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKosten() throws Exception {
        // Initialize the database
        kostenRepository.saveAndFlush(kosten);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kosten
        Kosten updatedKosten = kostenRepository.findById(kosten.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKosten are not directly saved in db
        em.detach(updatedKosten);
        updatedKosten
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .aantal(UPDATED_AANTAL)
            .bedrag(UPDATED_BEDRAG)
            .bedragtotaal(UPDATED_BEDRAGTOTAAL)
            .datumaanmaak(UPDATED_DATUMAANMAAK)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .eenheid(UPDATED_EENHEID)
            .geaccordeerd(UPDATED_GEACCORDEERD)
            .gefactureerdop(UPDATED_GEFACTUREERDOP)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .opbasisvangrondslag(UPDATED_OPBASISVANGRONDSLAG)
            .tarief(UPDATED_TARIEF)
            .type(UPDATED_TYPE)
            .vastgesteldbedrag(UPDATED_VASTGESTELDBEDRAG);

        restKostenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKosten.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKosten))
            )
            .andExpect(status().isOk());

        // Validate the Kosten in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKostenToMatchAllProperties(updatedKosten);
    }

    @Test
    @Transactional
    void putNonExistingKosten() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kosten.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKostenMockMvc
            .perform(put(ENTITY_API_URL_ID, kosten.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kosten)))
            .andExpect(status().isBadRequest());

        // Validate the Kosten in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKosten() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kosten.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKostenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kosten))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kosten in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKosten() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kosten.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKostenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kosten)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kosten in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKostenWithPatch() throws Exception {
        // Initialize the database
        kostenRepository.saveAndFlush(kosten);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kosten using partial update
        Kosten partialUpdatedKosten = new Kosten();
        partialUpdatedKosten.setId(kosten.getId());

        partialUpdatedKosten
            .aantal(UPDATED_AANTAL)
            .bedragtotaal(UPDATED_BEDRAGTOTAAL)
            .datumaanmaak(UPDATED_DATUMAANMAAK)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .opbasisvangrondslag(UPDATED_OPBASISVANGRONDSLAG)
            .type(UPDATED_TYPE);

        restKostenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKosten.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKosten))
            )
            .andExpect(status().isOk());

        // Validate the Kosten in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKostenUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedKosten, kosten), getPersistedKosten(kosten));
    }

    @Test
    @Transactional
    void fullUpdateKostenWithPatch() throws Exception {
        // Initialize the database
        kostenRepository.saveAndFlush(kosten);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kosten using partial update
        Kosten partialUpdatedKosten = new Kosten();
        partialUpdatedKosten.setId(kosten.getId());

        partialUpdatedKosten
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .aantal(UPDATED_AANTAL)
            .bedrag(UPDATED_BEDRAG)
            .bedragtotaal(UPDATED_BEDRAGTOTAAL)
            .datumaanmaak(UPDATED_DATUMAANMAAK)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .eenheid(UPDATED_EENHEID)
            .geaccordeerd(UPDATED_GEACCORDEERD)
            .gefactureerdop(UPDATED_GEFACTUREERDOP)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .opbasisvangrondslag(UPDATED_OPBASISVANGRONDSLAG)
            .tarief(UPDATED_TARIEF)
            .type(UPDATED_TYPE)
            .vastgesteldbedrag(UPDATED_VASTGESTELDBEDRAG);

        restKostenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKosten.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKosten))
            )
            .andExpect(status().isOk());

        // Validate the Kosten in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKostenUpdatableFieldsEquals(partialUpdatedKosten, getPersistedKosten(partialUpdatedKosten));
    }

    @Test
    @Transactional
    void patchNonExistingKosten() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kosten.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKostenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kosten.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kosten))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kosten in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKosten() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kosten.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKostenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kosten))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kosten in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKosten() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kosten.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKostenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kosten)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kosten in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKosten() throws Exception {
        // Initialize the database
        kostenRepository.saveAndFlush(kosten);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kosten
        restKostenMockMvc
            .perform(delete(ENTITY_API_URL_ID, kosten.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kostenRepository.count();
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

    protected Kosten getPersistedKosten(Kosten kosten) {
        return kostenRepository.findById(kosten.getId()).orElseThrow();
    }

    protected void assertPersistedKostenToMatchAllProperties(Kosten expectedKosten) {
        assertKostenAllPropertiesEquals(expectedKosten, getPersistedKosten(expectedKosten));
    }

    protected void assertPersistedKostenToMatchUpdatableProperties(Kosten expectedKosten) {
        assertKostenAllUpdatablePropertiesEquals(expectedKosten, getPersistedKosten(expectedKosten));
    }
}
