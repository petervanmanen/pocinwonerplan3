package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GemeentebegrafenisAsserts.*;
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
import nl.ritense.demo.domain.Gemeentebegrafenis;
import nl.ritense.demo.repository.GemeentebegrafenisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GemeentebegrafenisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GemeentebegrafenisResourceIT {

    private static final String DEFAULT_ACHTERGRONDMELDING = "AAAAAAAAAA";
    private static final String UPDATED_ACHTERGRONDMELDING = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BEGRAFENISKOSTEN = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEGRAFENISKOSTEN = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATUMAFGEDAAN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAFGEDAAN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMBEGRAFENIS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGRAFENIS = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMGEMELD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMGEMELD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMRUIMINGGRAF = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMRUIMINGGRAF = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DOODSOORZAAK = "AAAAAAAAAA";
    private static final String UPDATED_DOODSOORZAAK = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_GEMEENTELIJKEKOSTEN = new BigDecimal(1);
    private static final BigDecimal UPDATED_GEMEENTELIJKEKOSTEN = new BigDecimal(2);

    private static final String DEFAULT_INKOOPORDERNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_INKOOPORDERNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_MELDER = "AAAAAAAAAA";
    private static final String UPDATED_MELDER = "BBBBBBBBBB";

    private static final String DEFAULT_URENGEMEENTE = "AAAAAAAAAA";
    private static final String UPDATED_URENGEMEENTE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VERHAALDBEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_VERHAALDBEDRAG = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/gemeentebegrafenis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GemeentebegrafenisRepository gemeentebegrafenisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGemeentebegrafenisMockMvc;

    private Gemeentebegrafenis gemeentebegrafenis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gemeentebegrafenis createEntity(EntityManager em) {
        Gemeentebegrafenis gemeentebegrafenis = new Gemeentebegrafenis()
            .achtergrondmelding(DEFAULT_ACHTERGRONDMELDING)
            .begrafeniskosten(DEFAULT_BEGRAFENISKOSTEN)
            .datumafgedaan(DEFAULT_DATUMAFGEDAAN)
            .datumbegrafenis(DEFAULT_DATUMBEGRAFENIS)
            .datumgemeld(DEFAULT_DATUMGEMELD)
            .datumruiminggraf(DEFAULT_DATUMRUIMINGGRAF)
            .doodsoorzaak(DEFAULT_DOODSOORZAAK)
            .gemeentelijkekosten(DEFAULT_GEMEENTELIJKEKOSTEN)
            .inkoopordernummer(DEFAULT_INKOOPORDERNUMMER)
            .melder(DEFAULT_MELDER)
            .urengemeente(DEFAULT_URENGEMEENTE)
            .verhaaldbedrag(DEFAULT_VERHAALDBEDRAG);
        return gemeentebegrafenis;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gemeentebegrafenis createUpdatedEntity(EntityManager em) {
        Gemeentebegrafenis gemeentebegrafenis = new Gemeentebegrafenis()
            .achtergrondmelding(UPDATED_ACHTERGRONDMELDING)
            .begrafeniskosten(UPDATED_BEGRAFENISKOSTEN)
            .datumafgedaan(UPDATED_DATUMAFGEDAAN)
            .datumbegrafenis(UPDATED_DATUMBEGRAFENIS)
            .datumgemeld(UPDATED_DATUMGEMELD)
            .datumruiminggraf(UPDATED_DATUMRUIMINGGRAF)
            .doodsoorzaak(UPDATED_DOODSOORZAAK)
            .gemeentelijkekosten(UPDATED_GEMEENTELIJKEKOSTEN)
            .inkoopordernummer(UPDATED_INKOOPORDERNUMMER)
            .melder(UPDATED_MELDER)
            .urengemeente(UPDATED_URENGEMEENTE)
            .verhaaldbedrag(UPDATED_VERHAALDBEDRAG);
        return gemeentebegrafenis;
    }

    @BeforeEach
    public void initTest() {
        gemeentebegrafenis = createEntity(em);
    }

    @Test
    @Transactional
    void createGemeentebegrafenis() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Gemeentebegrafenis
        var returnedGemeentebegrafenis = om.readValue(
            restGemeentebegrafenisMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gemeentebegrafenis)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Gemeentebegrafenis.class
        );

        // Validate the Gemeentebegrafenis in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGemeentebegrafenisUpdatableFieldsEquals(
            returnedGemeentebegrafenis,
            getPersistedGemeentebegrafenis(returnedGemeentebegrafenis)
        );
    }

    @Test
    @Transactional
    void createGemeentebegrafenisWithExistingId() throws Exception {
        // Create the Gemeentebegrafenis with an existing ID
        gemeentebegrafenis.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGemeentebegrafenisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gemeentebegrafenis)))
            .andExpect(status().isBadRequest());

        // Validate the Gemeentebegrafenis in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGemeentebegrafenis() throws Exception {
        // Initialize the database
        gemeentebegrafenisRepository.saveAndFlush(gemeentebegrafenis);

        // Get all the gemeentebegrafenisList
        restGemeentebegrafenisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gemeentebegrafenis.getId().intValue())))
            .andExpect(jsonPath("$.[*].achtergrondmelding").value(hasItem(DEFAULT_ACHTERGRONDMELDING)))
            .andExpect(jsonPath("$.[*].begrafeniskosten").value(hasItem(sameNumber(DEFAULT_BEGRAFENISKOSTEN))))
            .andExpect(jsonPath("$.[*].datumafgedaan").value(hasItem(DEFAULT_DATUMAFGEDAAN.toString())))
            .andExpect(jsonPath("$.[*].datumbegrafenis").value(hasItem(DEFAULT_DATUMBEGRAFENIS.toString())))
            .andExpect(jsonPath("$.[*].datumgemeld").value(hasItem(DEFAULT_DATUMGEMELD.toString())))
            .andExpect(jsonPath("$.[*].datumruiminggraf").value(hasItem(DEFAULT_DATUMRUIMINGGRAF.toString())))
            .andExpect(jsonPath("$.[*].doodsoorzaak").value(hasItem(DEFAULT_DOODSOORZAAK)))
            .andExpect(jsonPath("$.[*].gemeentelijkekosten").value(hasItem(sameNumber(DEFAULT_GEMEENTELIJKEKOSTEN))))
            .andExpect(jsonPath("$.[*].inkoopordernummer").value(hasItem(DEFAULT_INKOOPORDERNUMMER)))
            .andExpect(jsonPath("$.[*].melder").value(hasItem(DEFAULT_MELDER)))
            .andExpect(jsonPath("$.[*].urengemeente").value(hasItem(DEFAULT_URENGEMEENTE)))
            .andExpect(jsonPath("$.[*].verhaaldbedrag").value(hasItem(sameNumber(DEFAULT_VERHAALDBEDRAG))));
    }

    @Test
    @Transactional
    void getGemeentebegrafenis() throws Exception {
        // Initialize the database
        gemeentebegrafenisRepository.saveAndFlush(gemeentebegrafenis);

        // Get the gemeentebegrafenis
        restGemeentebegrafenisMockMvc
            .perform(get(ENTITY_API_URL_ID, gemeentebegrafenis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gemeentebegrafenis.getId().intValue()))
            .andExpect(jsonPath("$.achtergrondmelding").value(DEFAULT_ACHTERGRONDMELDING))
            .andExpect(jsonPath("$.begrafeniskosten").value(sameNumber(DEFAULT_BEGRAFENISKOSTEN)))
            .andExpect(jsonPath("$.datumafgedaan").value(DEFAULT_DATUMAFGEDAAN.toString()))
            .andExpect(jsonPath("$.datumbegrafenis").value(DEFAULT_DATUMBEGRAFENIS.toString()))
            .andExpect(jsonPath("$.datumgemeld").value(DEFAULT_DATUMGEMELD.toString()))
            .andExpect(jsonPath("$.datumruiminggraf").value(DEFAULT_DATUMRUIMINGGRAF.toString()))
            .andExpect(jsonPath("$.doodsoorzaak").value(DEFAULT_DOODSOORZAAK))
            .andExpect(jsonPath("$.gemeentelijkekosten").value(sameNumber(DEFAULT_GEMEENTELIJKEKOSTEN)))
            .andExpect(jsonPath("$.inkoopordernummer").value(DEFAULT_INKOOPORDERNUMMER))
            .andExpect(jsonPath("$.melder").value(DEFAULT_MELDER))
            .andExpect(jsonPath("$.urengemeente").value(DEFAULT_URENGEMEENTE))
            .andExpect(jsonPath("$.verhaaldbedrag").value(sameNumber(DEFAULT_VERHAALDBEDRAG)));
    }

    @Test
    @Transactional
    void getNonExistingGemeentebegrafenis() throws Exception {
        // Get the gemeentebegrafenis
        restGemeentebegrafenisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGemeentebegrafenis() throws Exception {
        // Initialize the database
        gemeentebegrafenisRepository.saveAndFlush(gemeentebegrafenis);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gemeentebegrafenis
        Gemeentebegrafenis updatedGemeentebegrafenis = gemeentebegrafenisRepository.findById(gemeentebegrafenis.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGemeentebegrafenis are not directly saved in db
        em.detach(updatedGemeentebegrafenis);
        updatedGemeentebegrafenis
            .achtergrondmelding(UPDATED_ACHTERGRONDMELDING)
            .begrafeniskosten(UPDATED_BEGRAFENISKOSTEN)
            .datumafgedaan(UPDATED_DATUMAFGEDAAN)
            .datumbegrafenis(UPDATED_DATUMBEGRAFENIS)
            .datumgemeld(UPDATED_DATUMGEMELD)
            .datumruiminggraf(UPDATED_DATUMRUIMINGGRAF)
            .doodsoorzaak(UPDATED_DOODSOORZAAK)
            .gemeentelijkekosten(UPDATED_GEMEENTELIJKEKOSTEN)
            .inkoopordernummer(UPDATED_INKOOPORDERNUMMER)
            .melder(UPDATED_MELDER)
            .urengemeente(UPDATED_URENGEMEENTE)
            .verhaaldbedrag(UPDATED_VERHAALDBEDRAG);

        restGemeentebegrafenisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGemeentebegrafenis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGemeentebegrafenis))
            )
            .andExpect(status().isOk());

        // Validate the Gemeentebegrafenis in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGemeentebegrafenisToMatchAllProperties(updatedGemeentebegrafenis);
    }

    @Test
    @Transactional
    void putNonExistingGemeentebegrafenis() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemeentebegrafenis.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGemeentebegrafenisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gemeentebegrafenis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gemeentebegrafenis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gemeentebegrafenis in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGemeentebegrafenis() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemeentebegrafenis.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemeentebegrafenisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gemeentebegrafenis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gemeentebegrafenis in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGemeentebegrafenis() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemeentebegrafenis.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemeentebegrafenisMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gemeentebegrafenis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gemeentebegrafenis in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGemeentebegrafenisWithPatch() throws Exception {
        // Initialize the database
        gemeentebegrafenisRepository.saveAndFlush(gemeentebegrafenis);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gemeentebegrafenis using partial update
        Gemeentebegrafenis partialUpdatedGemeentebegrafenis = new Gemeentebegrafenis();
        partialUpdatedGemeentebegrafenis.setId(gemeentebegrafenis.getId());

        partialUpdatedGemeentebegrafenis
            .achtergrondmelding(UPDATED_ACHTERGRONDMELDING)
            .begrafeniskosten(UPDATED_BEGRAFENISKOSTEN)
            .datumgemeld(UPDATED_DATUMGEMELD)
            .datumruiminggraf(UPDATED_DATUMRUIMINGGRAF)
            .inkoopordernummer(UPDATED_INKOOPORDERNUMMER)
            .verhaaldbedrag(UPDATED_VERHAALDBEDRAG);

        restGemeentebegrafenisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGemeentebegrafenis.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGemeentebegrafenis))
            )
            .andExpect(status().isOk());

        // Validate the Gemeentebegrafenis in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGemeentebegrafenisUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGemeentebegrafenis, gemeentebegrafenis),
            getPersistedGemeentebegrafenis(gemeentebegrafenis)
        );
    }

    @Test
    @Transactional
    void fullUpdateGemeentebegrafenisWithPatch() throws Exception {
        // Initialize the database
        gemeentebegrafenisRepository.saveAndFlush(gemeentebegrafenis);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gemeentebegrafenis using partial update
        Gemeentebegrafenis partialUpdatedGemeentebegrafenis = new Gemeentebegrafenis();
        partialUpdatedGemeentebegrafenis.setId(gemeentebegrafenis.getId());

        partialUpdatedGemeentebegrafenis
            .achtergrondmelding(UPDATED_ACHTERGRONDMELDING)
            .begrafeniskosten(UPDATED_BEGRAFENISKOSTEN)
            .datumafgedaan(UPDATED_DATUMAFGEDAAN)
            .datumbegrafenis(UPDATED_DATUMBEGRAFENIS)
            .datumgemeld(UPDATED_DATUMGEMELD)
            .datumruiminggraf(UPDATED_DATUMRUIMINGGRAF)
            .doodsoorzaak(UPDATED_DOODSOORZAAK)
            .gemeentelijkekosten(UPDATED_GEMEENTELIJKEKOSTEN)
            .inkoopordernummer(UPDATED_INKOOPORDERNUMMER)
            .melder(UPDATED_MELDER)
            .urengemeente(UPDATED_URENGEMEENTE)
            .verhaaldbedrag(UPDATED_VERHAALDBEDRAG);

        restGemeentebegrafenisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGemeentebegrafenis.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGemeentebegrafenis))
            )
            .andExpect(status().isOk());

        // Validate the Gemeentebegrafenis in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGemeentebegrafenisUpdatableFieldsEquals(
            partialUpdatedGemeentebegrafenis,
            getPersistedGemeentebegrafenis(partialUpdatedGemeentebegrafenis)
        );
    }

    @Test
    @Transactional
    void patchNonExistingGemeentebegrafenis() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemeentebegrafenis.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGemeentebegrafenisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gemeentebegrafenis.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gemeentebegrafenis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gemeentebegrafenis in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGemeentebegrafenis() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemeentebegrafenis.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemeentebegrafenisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gemeentebegrafenis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gemeentebegrafenis in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGemeentebegrafenis() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemeentebegrafenis.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemeentebegrafenisMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gemeentebegrafenis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gemeentebegrafenis in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGemeentebegrafenis() throws Exception {
        // Initialize the database
        gemeentebegrafenisRepository.saveAndFlush(gemeentebegrafenis);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gemeentebegrafenis
        restGemeentebegrafenisMockMvc
            .perform(delete(ENTITY_API_URL_ID, gemeentebegrafenis.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gemeentebegrafenisRepository.count();
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

    protected Gemeentebegrafenis getPersistedGemeentebegrafenis(Gemeentebegrafenis gemeentebegrafenis) {
        return gemeentebegrafenisRepository.findById(gemeentebegrafenis.getId()).orElseThrow();
    }

    protected void assertPersistedGemeentebegrafenisToMatchAllProperties(Gemeentebegrafenis expectedGemeentebegrafenis) {
        assertGemeentebegrafenisAllPropertiesEquals(expectedGemeentebegrafenis, getPersistedGemeentebegrafenis(expectedGemeentebegrafenis));
    }

    protected void assertPersistedGemeentebegrafenisToMatchUpdatableProperties(Gemeentebegrafenis expectedGemeentebegrafenis) {
        assertGemeentebegrafenisAllUpdatablePropertiesEquals(
            expectedGemeentebegrafenis,
            getPersistedGemeentebegrafenis(expectedGemeentebegrafenis)
        );
    }
}
