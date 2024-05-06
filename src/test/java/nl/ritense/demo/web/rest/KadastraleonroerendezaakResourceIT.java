package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KadastraleonroerendezaakAsserts.*;
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
import nl.ritense.demo.domain.Kadastraleonroerendezaak;
import nl.ritense.demo.repository.KadastraleonroerendezaakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KadastraleonroerendezaakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KadastraleonroerendezaakResourceIT {

    private static final String DEFAULT_EMPTY = "AAAAAAAAAA";
    private static final String UPDATED_EMPTY = "BBBBBBBBBB";

    private static final String DEFAULT_APPARTEMENTSRECHTVOLGNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_APPARTEMENTSRECHTVOLGNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_BEGRENZING = "AAAAAAAAAA";
    private static final String UPDATED_BEGRENZING = "BBBBBBBBBB";

    private static final String DEFAULT_CULTUURCODEONBEBOUWD = "AAAAAAAAAA";
    private static final String UPDATED_CULTUURCODEONBEBOUWD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDKADASTRALEONROERENDEZAAK = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDKADASTRALEONROERENDEZAAK = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDKADASTRALEONROERENDEZAAK = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDKADASTRALEONROERENDEZAAK = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_KADASTRALEGEMEENTE = "AAAAAAAAAA";
    private static final String UPDATED_KADASTRALEGEMEENTE = "BBBBBBBBBB";

    private static final String DEFAULT_KADASTRALEGEMEENTECODE = "AAAAAAAAAA";
    private static final String UPDATED_KADASTRALEGEMEENTECODE = "BBBBBBBBBB";

    private static final String DEFAULT_KOOPJAAR = "AAAAAAAAAA";
    private static final String UPDATED_KOOPJAAR = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_KOOPSOM = new BigDecimal(1);
    private static final BigDecimal UPDATED_KOOPSOM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LANDINRICHTINGRENTEBEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_LANDINRICHTINGRENTEBEDRAG = new BigDecimal(2);

    private static final String DEFAULT_LANDINRICHTINGRENTEEINDEJAAR = "AAAAAAAAAA";
    private static final String UPDATED_LANDINRICHTINGRENTEEINDEJAAR = "BBBBBBBBBB";

    private static final String DEFAULT_LIGGING = "AAAAAAAAAA";
    private static final String UPDATED_LIGGING = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_OUD = "AAAAAAAAAA";
    private static final String UPDATED_OUD = "BBBBBBBBBB";

    private static final String DEFAULT_PERCEELNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_PERCEELNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_SECTIE = "AAAAAAAAAA";
    private static final String UPDATED_SECTIE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUTACODE = "AAAAAAAAAA";
    private static final String UPDATED_VALUTACODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kadastraleonroerendezaaks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KadastraleonroerendezaakRepository kadastraleonroerendezaakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKadastraleonroerendezaakMockMvc;

    private Kadastraleonroerendezaak kadastraleonroerendezaak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kadastraleonroerendezaak createEntity(EntityManager em) {
        Kadastraleonroerendezaak kadastraleonroerendezaak = new Kadastraleonroerendezaak()
            .empty(DEFAULT_EMPTY)
            .appartementsrechtvolgnummer(DEFAULT_APPARTEMENTSRECHTVOLGNUMMER)
            .begrenzing(DEFAULT_BEGRENZING)
            .cultuurcodeonbebouwd(DEFAULT_CULTUURCODEONBEBOUWD)
            .datumbegingeldigheidkadastraleonroerendezaak(DEFAULT_DATUMBEGINGELDIGHEIDKADASTRALEONROERENDEZAAK)
            .datumeindegeldigheidkadastraleonroerendezaak(DEFAULT_DATUMEINDEGELDIGHEIDKADASTRALEONROERENDEZAAK)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .kadastralegemeente(DEFAULT_KADASTRALEGEMEENTE)
            .kadastralegemeentecode(DEFAULT_KADASTRALEGEMEENTECODE)
            .koopjaar(DEFAULT_KOOPJAAR)
            .koopsom(DEFAULT_KOOPSOM)
            .landinrichtingrentebedrag(DEFAULT_LANDINRICHTINGRENTEBEDRAG)
            .landinrichtingrenteeindejaar(DEFAULT_LANDINRICHTINGRENTEEINDEJAAR)
            .ligging(DEFAULT_LIGGING)
            .locatieomschrijving(DEFAULT_LOCATIEOMSCHRIJVING)
            .oppervlakte(DEFAULT_OPPERVLAKTE)
            .oud(DEFAULT_OUD)
            .perceelnummer(DEFAULT_PERCEELNUMMER)
            .sectie(DEFAULT_SECTIE)
            .valutacode(DEFAULT_VALUTACODE);
        return kadastraleonroerendezaak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kadastraleonroerendezaak createUpdatedEntity(EntityManager em) {
        Kadastraleonroerendezaak kadastraleonroerendezaak = new Kadastraleonroerendezaak()
            .empty(UPDATED_EMPTY)
            .appartementsrechtvolgnummer(UPDATED_APPARTEMENTSRECHTVOLGNUMMER)
            .begrenzing(UPDATED_BEGRENZING)
            .cultuurcodeonbebouwd(UPDATED_CULTUURCODEONBEBOUWD)
            .datumbegingeldigheidkadastraleonroerendezaak(UPDATED_DATUMBEGINGELDIGHEIDKADASTRALEONROERENDEZAAK)
            .datumeindegeldigheidkadastraleonroerendezaak(UPDATED_DATUMEINDEGELDIGHEIDKADASTRALEONROERENDEZAAK)
            .identificatie(UPDATED_IDENTIFICATIE)
            .kadastralegemeente(UPDATED_KADASTRALEGEMEENTE)
            .kadastralegemeentecode(UPDATED_KADASTRALEGEMEENTECODE)
            .koopjaar(UPDATED_KOOPJAAR)
            .koopsom(UPDATED_KOOPSOM)
            .landinrichtingrentebedrag(UPDATED_LANDINRICHTINGRENTEBEDRAG)
            .landinrichtingrenteeindejaar(UPDATED_LANDINRICHTINGRENTEEINDEJAAR)
            .ligging(UPDATED_LIGGING)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .oud(UPDATED_OUD)
            .perceelnummer(UPDATED_PERCEELNUMMER)
            .sectie(UPDATED_SECTIE)
            .valutacode(UPDATED_VALUTACODE);
        return kadastraleonroerendezaak;
    }

    @BeforeEach
    public void initTest() {
        kadastraleonroerendezaak = createEntity(em);
    }

    @Test
    @Transactional
    void createKadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kadastraleonroerendezaak
        var returnedKadastraleonroerendezaak = om.readValue(
            restKadastraleonroerendezaakMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kadastraleonroerendezaak))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kadastraleonroerendezaak.class
        );

        // Validate the Kadastraleonroerendezaak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKadastraleonroerendezaakUpdatableFieldsEquals(
            returnedKadastraleonroerendezaak,
            getPersistedKadastraleonroerendezaak(returnedKadastraleonroerendezaak)
        );
    }

    @Test
    @Transactional
    void createKadastraleonroerendezaakWithExistingId() throws Exception {
        // Create the Kadastraleonroerendezaak with an existing ID
        kadastraleonroerendezaak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKadastraleonroerendezaakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kadastraleonroerendezaak)))
            .andExpect(status().isBadRequest());

        // Validate the Kadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKadastraleonroerendezaaks() throws Exception {
        // Initialize the database
        kadastraleonroerendezaakRepository.saveAndFlush(kadastraleonroerendezaak);

        // Get all the kadastraleonroerendezaakList
        restKadastraleonroerendezaakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kadastraleonroerendezaak.getId().intValue())))
            .andExpect(jsonPath("$.[*].empty").value(hasItem(DEFAULT_EMPTY)))
            .andExpect(jsonPath("$.[*].appartementsrechtvolgnummer").value(hasItem(DEFAULT_APPARTEMENTSRECHTVOLGNUMMER)))
            .andExpect(jsonPath("$.[*].begrenzing").value(hasItem(DEFAULT_BEGRENZING)))
            .andExpect(jsonPath("$.[*].cultuurcodeonbebouwd").value(hasItem(DEFAULT_CULTUURCODEONBEBOUWD)))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidkadastraleonroerendezaak").value(
                    hasItem(DEFAULT_DATUMBEGINGELDIGHEIDKADASTRALEONROERENDEZAAK.toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidkadastraleonroerendezaak").value(
                    hasItem(DEFAULT_DATUMEINDEGELDIGHEIDKADASTRALEONROERENDEZAAK.toString())
                )
            )
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].kadastralegemeente").value(hasItem(DEFAULT_KADASTRALEGEMEENTE)))
            .andExpect(jsonPath("$.[*].kadastralegemeentecode").value(hasItem(DEFAULT_KADASTRALEGEMEENTECODE)))
            .andExpect(jsonPath("$.[*].koopjaar").value(hasItem(DEFAULT_KOOPJAAR)))
            .andExpect(jsonPath("$.[*].koopsom").value(hasItem(sameNumber(DEFAULT_KOOPSOM))))
            .andExpect(jsonPath("$.[*].landinrichtingrentebedrag").value(hasItem(sameNumber(DEFAULT_LANDINRICHTINGRENTEBEDRAG))))
            .andExpect(jsonPath("$.[*].landinrichtingrenteeindejaar").value(hasItem(DEFAULT_LANDINRICHTINGRENTEEINDEJAAR)))
            .andExpect(jsonPath("$.[*].ligging").value(hasItem(DEFAULT_LIGGING)))
            .andExpect(jsonPath("$.[*].locatieomschrijving").value(hasItem(DEFAULT_LOCATIEOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].oppervlakte").value(hasItem(DEFAULT_OPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].oud").value(hasItem(DEFAULT_OUD)))
            .andExpect(jsonPath("$.[*].perceelnummer").value(hasItem(DEFAULT_PERCEELNUMMER)))
            .andExpect(jsonPath("$.[*].sectie").value(hasItem(DEFAULT_SECTIE)))
            .andExpect(jsonPath("$.[*].valutacode").value(hasItem(DEFAULT_VALUTACODE)));
    }

    @Test
    @Transactional
    void getKadastraleonroerendezaak() throws Exception {
        // Initialize the database
        kadastraleonroerendezaakRepository.saveAndFlush(kadastraleonroerendezaak);

        // Get the kadastraleonroerendezaak
        restKadastraleonroerendezaakMockMvc
            .perform(get(ENTITY_API_URL_ID, kadastraleonroerendezaak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kadastraleonroerendezaak.getId().intValue()))
            .andExpect(jsonPath("$.empty").value(DEFAULT_EMPTY))
            .andExpect(jsonPath("$.appartementsrechtvolgnummer").value(DEFAULT_APPARTEMENTSRECHTVOLGNUMMER))
            .andExpect(jsonPath("$.begrenzing").value(DEFAULT_BEGRENZING))
            .andExpect(jsonPath("$.cultuurcodeonbebouwd").value(DEFAULT_CULTUURCODEONBEBOUWD))
            .andExpect(
                jsonPath("$.datumbegingeldigheidkadastraleonroerendezaak").value(
                    DEFAULT_DATUMBEGINGELDIGHEIDKADASTRALEONROERENDEZAAK.toString()
                )
            )
            .andExpect(
                jsonPath("$.datumeindegeldigheidkadastraleonroerendezaak").value(
                    DEFAULT_DATUMEINDEGELDIGHEIDKADASTRALEONROERENDEZAAK.toString()
                )
            )
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.kadastralegemeente").value(DEFAULT_KADASTRALEGEMEENTE))
            .andExpect(jsonPath("$.kadastralegemeentecode").value(DEFAULT_KADASTRALEGEMEENTECODE))
            .andExpect(jsonPath("$.koopjaar").value(DEFAULT_KOOPJAAR))
            .andExpect(jsonPath("$.koopsom").value(sameNumber(DEFAULT_KOOPSOM)))
            .andExpect(jsonPath("$.landinrichtingrentebedrag").value(sameNumber(DEFAULT_LANDINRICHTINGRENTEBEDRAG)))
            .andExpect(jsonPath("$.landinrichtingrenteeindejaar").value(DEFAULT_LANDINRICHTINGRENTEEINDEJAAR))
            .andExpect(jsonPath("$.ligging").value(DEFAULT_LIGGING))
            .andExpect(jsonPath("$.locatieomschrijving").value(DEFAULT_LOCATIEOMSCHRIJVING))
            .andExpect(jsonPath("$.oppervlakte").value(DEFAULT_OPPERVLAKTE))
            .andExpect(jsonPath("$.oud").value(DEFAULT_OUD))
            .andExpect(jsonPath("$.perceelnummer").value(DEFAULT_PERCEELNUMMER))
            .andExpect(jsonPath("$.sectie").value(DEFAULT_SECTIE))
            .andExpect(jsonPath("$.valutacode").value(DEFAULT_VALUTACODE));
    }

    @Test
    @Transactional
    void getNonExistingKadastraleonroerendezaak() throws Exception {
        // Get the kadastraleonroerendezaak
        restKadastraleonroerendezaakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKadastraleonroerendezaak() throws Exception {
        // Initialize the database
        kadastraleonroerendezaakRepository.saveAndFlush(kadastraleonroerendezaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kadastraleonroerendezaak
        Kadastraleonroerendezaak updatedKadastraleonroerendezaak = kadastraleonroerendezaakRepository
            .findById(kadastraleonroerendezaak.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedKadastraleonroerendezaak are not directly saved in db
        em.detach(updatedKadastraleonroerendezaak);
        updatedKadastraleonroerendezaak
            .empty(UPDATED_EMPTY)
            .appartementsrechtvolgnummer(UPDATED_APPARTEMENTSRECHTVOLGNUMMER)
            .begrenzing(UPDATED_BEGRENZING)
            .cultuurcodeonbebouwd(UPDATED_CULTUURCODEONBEBOUWD)
            .datumbegingeldigheidkadastraleonroerendezaak(UPDATED_DATUMBEGINGELDIGHEIDKADASTRALEONROERENDEZAAK)
            .datumeindegeldigheidkadastraleonroerendezaak(UPDATED_DATUMEINDEGELDIGHEIDKADASTRALEONROERENDEZAAK)
            .identificatie(UPDATED_IDENTIFICATIE)
            .kadastralegemeente(UPDATED_KADASTRALEGEMEENTE)
            .kadastralegemeentecode(UPDATED_KADASTRALEGEMEENTECODE)
            .koopjaar(UPDATED_KOOPJAAR)
            .koopsom(UPDATED_KOOPSOM)
            .landinrichtingrentebedrag(UPDATED_LANDINRICHTINGRENTEBEDRAG)
            .landinrichtingrenteeindejaar(UPDATED_LANDINRICHTINGRENTEEINDEJAAR)
            .ligging(UPDATED_LIGGING)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .oud(UPDATED_OUD)
            .perceelnummer(UPDATED_PERCEELNUMMER)
            .sectie(UPDATED_SECTIE)
            .valutacode(UPDATED_VALUTACODE);

        restKadastraleonroerendezaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKadastraleonroerendezaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKadastraleonroerendezaak))
            )
            .andExpect(status().isOk());

        // Validate the Kadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKadastraleonroerendezaakToMatchAllProperties(updatedKadastraleonroerendezaak);
    }

    @Test
    @Transactional
    void putNonExistingKadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKadastraleonroerendezaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kadastraleonroerendezaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kadastraleonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastraleonroerendezaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kadastraleonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastraleonroerendezaakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kadastraleonroerendezaak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKadastraleonroerendezaakWithPatch() throws Exception {
        // Initialize the database
        kadastraleonroerendezaakRepository.saveAndFlush(kadastraleonroerendezaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kadastraleonroerendezaak using partial update
        Kadastraleonroerendezaak partialUpdatedKadastraleonroerendezaak = new Kadastraleonroerendezaak();
        partialUpdatedKadastraleonroerendezaak.setId(kadastraleonroerendezaak.getId());

        partialUpdatedKadastraleonroerendezaak
            .empty(UPDATED_EMPTY)
            .datumbegingeldigheidkadastraleonroerendezaak(UPDATED_DATUMBEGINGELDIGHEIDKADASTRALEONROERENDEZAAK)
            .datumeindegeldigheidkadastraleonroerendezaak(UPDATED_DATUMEINDEGELDIGHEIDKADASTRALEONROERENDEZAAK)
            .kadastralegemeente(UPDATED_KADASTRALEGEMEENTE)
            .landinrichtingrenteeindejaar(UPDATED_LANDINRICHTINGRENTEEINDEJAAR)
            .ligging(UPDATED_LIGGING)
            .oppervlakte(UPDATED_OPPERVLAKTE);

        restKadastraleonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKadastraleonroerendezaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKadastraleonroerendezaak))
            )
            .andExpect(status().isOk());

        // Validate the Kadastraleonroerendezaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKadastraleonroerendezaakUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKadastraleonroerendezaak, kadastraleonroerendezaak),
            getPersistedKadastraleonroerendezaak(kadastraleonroerendezaak)
        );
    }

    @Test
    @Transactional
    void fullUpdateKadastraleonroerendezaakWithPatch() throws Exception {
        // Initialize the database
        kadastraleonroerendezaakRepository.saveAndFlush(kadastraleonroerendezaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kadastraleonroerendezaak using partial update
        Kadastraleonroerendezaak partialUpdatedKadastraleonroerendezaak = new Kadastraleonroerendezaak();
        partialUpdatedKadastraleonroerendezaak.setId(kadastraleonroerendezaak.getId());

        partialUpdatedKadastraleonroerendezaak
            .empty(UPDATED_EMPTY)
            .appartementsrechtvolgnummer(UPDATED_APPARTEMENTSRECHTVOLGNUMMER)
            .begrenzing(UPDATED_BEGRENZING)
            .cultuurcodeonbebouwd(UPDATED_CULTUURCODEONBEBOUWD)
            .datumbegingeldigheidkadastraleonroerendezaak(UPDATED_DATUMBEGINGELDIGHEIDKADASTRALEONROERENDEZAAK)
            .datumeindegeldigheidkadastraleonroerendezaak(UPDATED_DATUMEINDEGELDIGHEIDKADASTRALEONROERENDEZAAK)
            .identificatie(UPDATED_IDENTIFICATIE)
            .kadastralegemeente(UPDATED_KADASTRALEGEMEENTE)
            .kadastralegemeentecode(UPDATED_KADASTRALEGEMEENTECODE)
            .koopjaar(UPDATED_KOOPJAAR)
            .koopsom(UPDATED_KOOPSOM)
            .landinrichtingrentebedrag(UPDATED_LANDINRICHTINGRENTEBEDRAG)
            .landinrichtingrenteeindejaar(UPDATED_LANDINRICHTINGRENTEEINDEJAAR)
            .ligging(UPDATED_LIGGING)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .oud(UPDATED_OUD)
            .perceelnummer(UPDATED_PERCEELNUMMER)
            .sectie(UPDATED_SECTIE)
            .valutacode(UPDATED_VALUTACODE);

        restKadastraleonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKadastraleonroerendezaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKadastraleonroerendezaak))
            )
            .andExpect(status().isOk());

        // Validate the Kadastraleonroerendezaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKadastraleonroerendezaakUpdatableFieldsEquals(
            partialUpdatedKadastraleonroerendezaak,
            getPersistedKadastraleonroerendezaak(partialUpdatedKadastraleonroerendezaak)
        );
    }

    @Test
    @Transactional
    void patchNonExistingKadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKadastraleonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kadastraleonroerendezaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kadastraleonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastraleonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kadastraleonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastraleonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kadastraleonroerendezaak))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKadastraleonroerendezaak() throws Exception {
        // Initialize the database
        kadastraleonroerendezaakRepository.saveAndFlush(kadastraleonroerendezaak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kadastraleonroerendezaak
        restKadastraleonroerendezaakMockMvc
            .perform(delete(ENTITY_API_URL_ID, kadastraleonroerendezaak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kadastraleonroerendezaakRepository.count();
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

    protected Kadastraleonroerendezaak getPersistedKadastraleonroerendezaak(Kadastraleonroerendezaak kadastraleonroerendezaak) {
        return kadastraleonroerendezaakRepository.findById(kadastraleonroerendezaak.getId()).orElseThrow();
    }

    protected void assertPersistedKadastraleonroerendezaakToMatchAllProperties(Kadastraleonroerendezaak expectedKadastraleonroerendezaak) {
        assertKadastraleonroerendezaakAllPropertiesEquals(
            expectedKadastraleonroerendezaak,
            getPersistedKadastraleonroerendezaak(expectedKadastraleonroerendezaak)
        );
    }

    protected void assertPersistedKadastraleonroerendezaakToMatchUpdatableProperties(
        Kadastraleonroerendezaak expectedKadastraleonroerendezaak
    ) {
        assertKadastraleonroerendezaakAllUpdatablePropertiesEquals(
            expectedKadastraleonroerendezaak,
            getPersistedKadastraleonroerendezaak(expectedKadastraleonroerendezaak)
        );
    }
}
