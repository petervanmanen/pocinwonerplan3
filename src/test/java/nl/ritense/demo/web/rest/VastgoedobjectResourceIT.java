package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VastgoedobjectAsserts.*;
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
import nl.ritense.demo.domain.Vastgoedobject;
import nl.ritense.demo.repository.VastgoedobjectRepository;
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
 * Integration tests for the {@link VastgoedobjectResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class VastgoedobjectResourceIT {

    private static final String DEFAULT_AANTALETAGES = "AAAAAAAAAA";
    private static final String UPDATED_AANTALETAGES = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALPARKEERPLAATSEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALPARKEERPLAATSEN = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALRIOLERINGEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALRIOLERINGEN = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESAANDUIDING = "AAAAAAAAAA";
    private static final String UPDATED_ADRESAANDUIDING = "BBBBBBBBBB";

    private static final String DEFAULT_AFGEKOCHTEERFPACHT = "AAAAAAAAAA";
    private static final String UPDATED_AFGEKOCHTEERFPACHT = "BBBBBBBBBB";

    private static final String DEFAULT_AFGESPROKENCONDITIESCORE = "AAAAAAAAAA";
    private static final String UPDATED_AFGESPROKENCONDITIESCORE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AFKOOPWAARDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_AFKOOPWAARDE = new BigDecimal(2);

    private static final String DEFAULT_ASBESTRAPPORTAGEAANWEZIG = "AAAAAAAAAA";
    private static final String UPDATED_ASBESTRAPPORTAGEAANWEZIG = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BEDRAGAANKOOP = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAGAANKOOP = new BigDecimal(2);

    private static final String DEFAULT_BESTEMMINGSPLAN = "AAAAAAAAAA";
    private static final String UPDATED_BESTEMMINGSPLAN = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BOEKWAARDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BOEKWAARDE = new BigDecimal(2);

    private static final String DEFAULT_BOUWJAAR = "AAAAAAAAAA";
    private static final String UPDATED_BOUWJAAR = "BBBBBBBBBB";

    private static final String DEFAULT_BOUWWERK = "AAAAAAAAAA";
    private static final String UPDATED_BOUWWERK = "BBBBBBBBBB";

    private static final String DEFAULT_BOVENLIGGENDNIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_BOVENLIGGENDNIVEAU = "BBBBBBBBBB";

    private static final String DEFAULT_BOVENLIGGENDNIVEAUCODE = "AAAAAAAAAA";
    private static final String UPDATED_BOVENLIGGENDNIVEAUCODE = "BBBBBBBBBB";

    private static final String DEFAULT_BRUTOVLOEROPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_BRUTOVLOEROPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_CO_2_UITSTOOT = "AAAAAAAAAA";
    private static final String UPDATED_CO_2_UITSTOOT = "BBBBBBBBBB";

    private static final String DEFAULT_CONDITIESCORE = "AAAAAAAAAA";
    private static final String UPDATED_CONDITIESCORE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMAFSTOTEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAFSTOTEN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMBEREKENINGOPPERVLAK = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEREKENINGOPPERVLAK = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEIGENDOM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEIGENDOM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMVERKOOP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMVERKOOP = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEELPORTEFEUILLE = "AAAAAAAAAA";
    private static final String UPDATED_DEELPORTEFEUILLE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ENERGIEKOSTEN = new BigDecimal(1);
    private static final BigDecimal UPDATED_ENERGIEKOSTEN = new BigDecimal(2);

    private static final String DEFAULT_ENERGIELABEL = "AAAAAAAAAA";
    private static final String UPDATED_ENERGIELABEL = "BBBBBBBBBB";

    private static final String DEFAULT_ENERGIEVERBRUIK = "AAAAAAAAAA";
    private static final String UPDATED_ENERGIEVERBRUIK = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_FISCALEWAARDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FISCALEWAARDE = new BigDecimal(2);

    private static final String DEFAULT_FOTO = "AAAAAAAAAA";
    private static final String UPDATED_FOTO = "BBBBBBBBBB";

    private static final String DEFAULT_GEARCHIVEERD = "AAAAAAAAAA";
    private static final String UPDATED_GEARCHIVEERD = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_HERBOUWWAARDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_HERBOUWWAARDE = new BigDecimal(2);

    private static final String DEFAULT_HOOFDSTUK = "AAAAAAAAAA";
    private static final String UPDATED_HOOFDSTUK = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_JAARLAATSTERENOVATIE = "AAAAAAAAAA";
    private static final String UPDATED_JAARLAATSTERENOVATIE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MARKTWAARDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MARKTWAARDE = new BigDecimal(2);

    private static final String DEFAULT_MONUMENT = "AAAAAAAAAA";
    private static final String UPDATED_MONUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_EOBJECTSTATUS = "AAAAAAAAAA";
    private static final String UPDATED_EOBJECTSTATUS = "BBBBBBBBBB";

    private static final String DEFAULT_EOBJECTSTATUSCODE = "AAAAAAAAAA";
    private static final String UPDATED_EOBJECTSTATUSCODE = "BBBBBBBBBB";

    private static final String DEFAULT_EOBJECTTYPE = "AAAAAAAAAA";
    private static final String UPDATED_EOBJECTTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EOBJECTTYPECODE = "AAAAAAAAAA";
    private static final String UPDATED_EOBJECTTYPECODE = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERHOUDSCATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_ONDERHOUDSCATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTEKANTOOR = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTEKANTOOR = "BBBBBBBBBB";

    private static final String DEFAULT_PORTEFEUILLE = "AAAAAAAAAA";
    private static final String UPDATED_PORTEFEUILLE = "BBBBBBBBBB";

    private static final String DEFAULT_PORTEFEUILLECODE = "AAAAAAAAAA";
    private static final String UPDATED_PORTEFEUILLECODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIE = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String DEFAULT_VERHUURBAARVLOEROPPERVLAK = "AAAAAAAAAA";
    private static final String UPDATED_VERHUURBAARVLOEROPPERVLAK = "BBBBBBBBBB";

    private static final String DEFAULT_VERKOOPBAARHEID = "AAAAAAAAAA";
    private static final String UPDATED_VERKOOPBAARHEID = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VERKOOPBEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_VERKOOPBEDRAG = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VERZEKERDEWAARDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VERZEKERDEWAARDE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_WAARDEGROND = new BigDecimal(1);
    private static final BigDecimal UPDATED_WAARDEGROND = new BigDecimal(2);

    private static final BigDecimal DEFAULT_WAARDEOPSTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_WAARDEOPSTAL = new BigDecimal(2);

    private static final String DEFAULT_WIJK = "AAAAAAAAAA";
    private static final String UPDATED_WIJK = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_WOZWAARDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_WOZWAARDE = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/vastgoedobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VastgoedobjectRepository vastgoedobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVastgoedobjectMockMvc;

    private Vastgoedobject vastgoedobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vastgoedobject createEntity(EntityManager em) {
        Vastgoedobject vastgoedobject = new Vastgoedobject()
            .aantaletages(DEFAULT_AANTALETAGES)
            .aantalparkeerplaatsen(DEFAULT_AANTALPARKEERPLAATSEN)
            .aantalrioleringen(DEFAULT_AANTALRIOLERINGEN)
            .adresaanduiding(DEFAULT_ADRESAANDUIDING)
            .afgekochteerfpacht(DEFAULT_AFGEKOCHTEERFPACHT)
            .afgesprokenconditiescore(DEFAULT_AFGESPROKENCONDITIESCORE)
            .afkoopwaarde(DEFAULT_AFKOOPWAARDE)
            .asbestrapportageaanwezig(DEFAULT_ASBESTRAPPORTAGEAANWEZIG)
            .bedragaankoop(DEFAULT_BEDRAGAANKOOP)
            .bestemmingsplan(DEFAULT_BESTEMMINGSPLAN)
            .boekwaarde(DEFAULT_BOEKWAARDE)
            .bouwjaar(DEFAULT_BOUWJAAR)
            .bouwwerk(DEFAULT_BOUWWERK)
            .bovenliggendniveau(DEFAULT_BOVENLIGGENDNIVEAU)
            .bovenliggendniveaucode(DEFAULT_BOVENLIGGENDNIVEAUCODE)
            .brutovloeroppervlakte(DEFAULT_BRUTOVLOEROPPERVLAKTE)
            .co2uitstoot(DEFAULT_CO_2_UITSTOOT)
            .conditiescore(DEFAULT_CONDITIESCORE)
            .datumafstoten(DEFAULT_DATUMAFSTOTEN)
            .datumberekeningoppervlak(DEFAULT_DATUMBEREKENINGOPPERVLAK)
            .datumeigendom(DEFAULT_DATUMEIGENDOM)
            .datumverkoop(DEFAULT_DATUMVERKOOP)
            .deelportefeuille(DEFAULT_DEELPORTEFEUILLE)
            .energiekosten(DEFAULT_ENERGIEKOSTEN)
            .energielabel(DEFAULT_ENERGIELABEL)
            .energieverbruik(DEFAULT_ENERGIEVERBRUIK)
            .fiscalewaarde(DEFAULT_FISCALEWAARDE)
            .foto(DEFAULT_FOTO)
            .gearchiveerd(DEFAULT_GEARCHIVEERD)
            .herbouwwaarde(DEFAULT_HERBOUWWAARDE)
            .hoofdstuk(DEFAULT_HOOFDSTUK)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .jaarlaatsterenovatie(DEFAULT_JAARLAATSTERENOVATIE)
            .locatie(DEFAULT_LOCATIE)
            .marktwaarde(DEFAULT_MARKTWAARDE)
            .monument(DEFAULT_MONUMENT)
            .naam(DEFAULT_NAAM)
            .eobjectstatus(DEFAULT_EOBJECTSTATUS)
            .eobjectstatuscode(DEFAULT_EOBJECTSTATUSCODE)
            .eobjecttype(DEFAULT_EOBJECTTYPE)
            .eobjecttypecode(DEFAULT_EOBJECTTYPECODE)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .onderhoudscategorie(DEFAULT_ONDERHOUDSCATEGORIE)
            .oppervlaktekantoor(DEFAULT_OPPERVLAKTEKANTOOR)
            .portefeuille(DEFAULT_PORTEFEUILLE)
            .portefeuillecode(DEFAULT_PORTEFEUILLECODE)
            .provincie(DEFAULT_PROVINCIE)
            .toelichting(DEFAULT_TOELICHTING)
            .verhuurbaarvloeroppervlak(DEFAULT_VERHUURBAARVLOEROPPERVLAK)
            .verkoopbaarheid(DEFAULT_VERKOOPBAARHEID)
            .verkoopbedrag(DEFAULT_VERKOOPBEDRAG)
            .verzekerdewaarde(DEFAULT_VERZEKERDEWAARDE)
            .waardegrond(DEFAULT_WAARDEGROND)
            .waardeopstal(DEFAULT_WAARDEOPSTAL)
            .wijk(DEFAULT_WIJK)
            .wozwaarde(DEFAULT_WOZWAARDE);
        return vastgoedobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vastgoedobject createUpdatedEntity(EntityManager em) {
        Vastgoedobject vastgoedobject = new Vastgoedobject()
            .aantaletages(UPDATED_AANTALETAGES)
            .aantalparkeerplaatsen(UPDATED_AANTALPARKEERPLAATSEN)
            .aantalrioleringen(UPDATED_AANTALRIOLERINGEN)
            .adresaanduiding(UPDATED_ADRESAANDUIDING)
            .afgekochteerfpacht(UPDATED_AFGEKOCHTEERFPACHT)
            .afgesprokenconditiescore(UPDATED_AFGESPROKENCONDITIESCORE)
            .afkoopwaarde(UPDATED_AFKOOPWAARDE)
            .asbestrapportageaanwezig(UPDATED_ASBESTRAPPORTAGEAANWEZIG)
            .bedragaankoop(UPDATED_BEDRAGAANKOOP)
            .bestemmingsplan(UPDATED_BESTEMMINGSPLAN)
            .boekwaarde(UPDATED_BOEKWAARDE)
            .bouwjaar(UPDATED_BOUWJAAR)
            .bouwwerk(UPDATED_BOUWWERK)
            .bovenliggendniveau(UPDATED_BOVENLIGGENDNIVEAU)
            .bovenliggendniveaucode(UPDATED_BOVENLIGGENDNIVEAUCODE)
            .brutovloeroppervlakte(UPDATED_BRUTOVLOEROPPERVLAKTE)
            .co2uitstoot(UPDATED_CO_2_UITSTOOT)
            .conditiescore(UPDATED_CONDITIESCORE)
            .datumafstoten(UPDATED_DATUMAFSTOTEN)
            .datumberekeningoppervlak(UPDATED_DATUMBEREKENINGOPPERVLAK)
            .datumeigendom(UPDATED_DATUMEIGENDOM)
            .datumverkoop(UPDATED_DATUMVERKOOP)
            .deelportefeuille(UPDATED_DEELPORTEFEUILLE)
            .energiekosten(UPDATED_ENERGIEKOSTEN)
            .energielabel(UPDATED_ENERGIELABEL)
            .energieverbruik(UPDATED_ENERGIEVERBRUIK)
            .fiscalewaarde(UPDATED_FISCALEWAARDE)
            .foto(UPDATED_FOTO)
            .gearchiveerd(UPDATED_GEARCHIVEERD)
            .herbouwwaarde(UPDATED_HERBOUWWAARDE)
            .hoofdstuk(UPDATED_HOOFDSTUK)
            .identificatie(UPDATED_IDENTIFICATIE)
            .jaarlaatsterenovatie(UPDATED_JAARLAATSTERENOVATIE)
            .locatie(UPDATED_LOCATIE)
            .marktwaarde(UPDATED_MARKTWAARDE)
            .monument(UPDATED_MONUMENT)
            .naam(UPDATED_NAAM)
            .eobjectstatus(UPDATED_EOBJECTSTATUS)
            .eobjectstatuscode(UPDATED_EOBJECTSTATUSCODE)
            .eobjecttype(UPDATED_EOBJECTTYPE)
            .eobjecttypecode(UPDATED_EOBJECTTYPECODE)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .onderhoudscategorie(UPDATED_ONDERHOUDSCATEGORIE)
            .oppervlaktekantoor(UPDATED_OPPERVLAKTEKANTOOR)
            .portefeuille(UPDATED_PORTEFEUILLE)
            .portefeuillecode(UPDATED_PORTEFEUILLECODE)
            .provincie(UPDATED_PROVINCIE)
            .toelichting(UPDATED_TOELICHTING)
            .verhuurbaarvloeroppervlak(UPDATED_VERHUURBAARVLOEROPPERVLAK)
            .verkoopbaarheid(UPDATED_VERKOOPBAARHEID)
            .verkoopbedrag(UPDATED_VERKOOPBEDRAG)
            .verzekerdewaarde(UPDATED_VERZEKERDEWAARDE)
            .waardegrond(UPDATED_WAARDEGROND)
            .waardeopstal(UPDATED_WAARDEOPSTAL)
            .wijk(UPDATED_WIJK)
            .wozwaarde(UPDATED_WOZWAARDE);
        return vastgoedobject;
    }

    @BeforeEach
    public void initTest() {
        vastgoedobject = createEntity(em);
    }

    @Test
    @Transactional
    void createVastgoedobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vastgoedobject
        var returnedVastgoedobject = om.readValue(
            restVastgoedobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vastgoedobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vastgoedobject.class
        );

        // Validate the Vastgoedobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVastgoedobjectUpdatableFieldsEquals(returnedVastgoedobject, getPersistedVastgoedobject(returnedVastgoedobject));
    }

    @Test
    @Transactional
    void createVastgoedobjectWithExistingId() throws Exception {
        // Create the Vastgoedobject with an existing ID
        vastgoedobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVastgoedobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vastgoedobject)))
            .andExpect(status().isBadRequest());

        // Validate the Vastgoedobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVastgoedobjects() throws Exception {
        // Initialize the database
        vastgoedobjectRepository.saveAndFlush(vastgoedobject);

        // Get all the vastgoedobjectList
        restVastgoedobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vastgoedobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantaletages").value(hasItem(DEFAULT_AANTALETAGES)))
            .andExpect(jsonPath("$.[*].aantalparkeerplaatsen").value(hasItem(DEFAULT_AANTALPARKEERPLAATSEN)))
            .andExpect(jsonPath("$.[*].aantalrioleringen").value(hasItem(DEFAULT_AANTALRIOLERINGEN)))
            .andExpect(jsonPath("$.[*].adresaanduiding").value(hasItem(DEFAULT_ADRESAANDUIDING)))
            .andExpect(jsonPath("$.[*].afgekochteerfpacht").value(hasItem(DEFAULT_AFGEKOCHTEERFPACHT)))
            .andExpect(jsonPath("$.[*].afgesprokenconditiescore").value(hasItem(DEFAULT_AFGESPROKENCONDITIESCORE)))
            .andExpect(jsonPath("$.[*].afkoopwaarde").value(hasItem(sameNumber(DEFAULT_AFKOOPWAARDE))))
            .andExpect(jsonPath("$.[*].asbestrapportageaanwezig").value(hasItem(DEFAULT_ASBESTRAPPORTAGEAANWEZIG)))
            .andExpect(jsonPath("$.[*].bedragaankoop").value(hasItem(sameNumber(DEFAULT_BEDRAGAANKOOP))))
            .andExpect(jsonPath("$.[*].bestemmingsplan").value(hasItem(DEFAULT_BESTEMMINGSPLAN)))
            .andExpect(jsonPath("$.[*].boekwaarde").value(hasItem(sameNumber(DEFAULT_BOEKWAARDE))))
            .andExpect(jsonPath("$.[*].bouwjaar").value(hasItem(DEFAULT_BOUWJAAR)))
            .andExpect(jsonPath("$.[*].bouwwerk").value(hasItem(DEFAULT_BOUWWERK)))
            .andExpect(jsonPath("$.[*].bovenliggendniveau").value(hasItem(DEFAULT_BOVENLIGGENDNIVEAU)))
            .andExpect(jsonPath("$.[*].bovenliggendniveaucode").value(hasItem(DEFAULT_BOVENLIGGENDNIVEAUCODE)))
            .andExpect(jsonPath("$.[*].brutovloeroppervlakte").value(hasItem(DEFAULT_BRUTOVLOEROPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].co2uitstoot").value(hasItem(DEFAULT_CO_2_UITSTOOT)))
            .andExpect(jsonPath("$.[*].conditiescore").value(hasItem(DEFAULT_CONDITIESCORE)))
            .andExpect(jsonPath("$.[*].datumafstoten").value(hasItem(DEFAULT_DATUMAFSTOTEN.toString())))
            .andExpect(jsonPath("$.[*].datumberekeningoppervlak").value(hasItem(DEFAULT_DATUMBEREKENINGOPPERVLAK.toString())))
            .andExpect(jsonPath("$.[*].datumeigendom").value(hasItem(DEFAULT_DATUMEIGENDOM.toString())))
            .andExpect(jsonPath("$.[*].datumverkoop").value(hasItem(DEFAULT_DATUMVERKOOP.toString())))
            .andExpect(jsonPath("$.[*].deelportefeuille").value(hasItem(DEFAULT_DEELPORTEFEUILLE)))
            .andExpect(jsonPath("$.[*].energiekosten").value(hasItem(sameNumber(DEFAULT_ENERGIEKOSTEN))))
            .andExpect(jsonPath("$.[*].energielabel").value(hasItem(DEFAULT_ENERGIELABEL)))
            .andExpect(jsonPath("$.[*].energieverbruik").value(hasItem(DEFAULT_ENERGIEVERBRUIK)))
            .andExpect(jsonPath("$.[*].fiscalewaarde").value(hasItem(sameNumber(DEFAULT_FISCALEWAARDE))))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.[*].gearchiveerd").value(hasItem(DEFAULT_GEARCHIVEERD)))
            .andExpect(jsonPath("$.[*].herbouwwaarde").value(hasItem(sameNumber(DEFAULT_HERBOUWWAARDE))))
            .andExpect(jsonPath("$.[*].hoofdstuk").value(hasItem(DEFAULT_HOOFDSTUK)))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].jaarlaatsterenovatie").value(hasItem(DEFAULT_JAARLAATSTERENOVATIE)))
            .andExpect(jsonPath("$.[*].locatie").value(hasItem(DEFAULT_LOCATIE)))
            .andExpect(jsonPath("$.[*].marktwaarde").value(hasItem(sameNumber(DEFAULT_MARKTWAARDE))))
            .andExpect(jsonPath("$.[*].monument").value(hasItem(DEFAULT_MONUMENT)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].eobjectstatus").value(hasItem(DEFAULT_EOBJECTSTATUS)))
            .andExpect(jsonPath("$.[*].eobjectstatuscode").value(hasItem(DEFAULT_EOBJECTSTATUSCODE)))
            .andExpect(jsonPath("$.[*].eobjecttype").value(hasItem(DEFAULT_EOBJECTTYPE)))
            .andExpect(jsonPath("$.[*].eobjecttypecode").value(hasItem(DEFAULT_EOBJECTTYPECODE)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].onderhoudscategorie").value(hasItem(DEFAULT_ONDERHOUDSCATEGORIE)))
            .andExpect(jsonPath("$.[*].oppervlaktekantoor").value(hasItem(DEFAULT_OPPERVLAKTEKANTOOR)))
            .andExpect(jsonPath("$.[*].portefeuille").value(hasItem(DEFAULT_PORTEFEUILLE)))
            .andExpect(jsonPath("$.[*].portefeuillecode").value(hasItem(DEFAULT_PORTEFEUILLECODE)))
            .andExpect(jsonPath("$.[*].provincie").value(hasItem(DEFAULT_PROVINCIE)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)))
            .andExpect(jsonPath("$.[*].verhuurbaarvloeroppervlak").value(hasItem(DEFAULT_VERHUURBAARVLOEROPPERVLAK)))
            .andExpect(jsonPath("$.[*].verkoopbaarheid").value(hasItem(DEFAULT_VERKOOPBAARHEID)))
            .andExpect(jsonPath("$.[*].verkoopbedrag").value(hasItem(sameNumber(DEFAULT_VERKOOPBEDRAG))))
            .andExpect(jsonPath("$.[*].verzekerdewaarde").value(hasItem(sameNumber(DEFAULT_VERZEKERDEWAARDE))))
            .andExpect(jsonPath("$.[*].waardegrond").value(hasItem(sameNumber(DEFAULT_WAARDEGROND))))
            .andExpect(jsonPath("$.[*].waardeopstal").value(hasItem(sameNumber(DEFAULT_WAARDEOPSTAL))))
            .andExpect(jsonPath("$.[*].wijk").value(hasItem(DEFAULT_WIJK)))
            .andExpect(jsonPath("$.[*].wozwaarde").value(hasItem(sameNumber(DEFAULT_WOZWAARDE))));
    }

    @Test
    @Transactional
    void getVastgoedobject() throws Exception {
        // Initialize the database
        vastgoedobjectRepository.saveAndFlush(vastgoedobject);

        // Get the vastgoedobject
        restVastgoedobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, vastgoedobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vastgoedobject.getId().intValue()))
            .andExpect(jsonPath("$.aantaletages").value(DEFAULT_AANTALETAGES))
            .andExpect(jsonPath("$.aantalparkeerplaatsen").value(DEFAULT_AANTALPARKEERPLAATSEN))
            .andExpect(jsonPath("$.aantalrioleringen").value(DEFAULT_AANTALRIOLERINGEN))
            .andExpect(jsonPath("$.adresaanduiding").value(DEFAULT_ADRESAANDUIDING))
            .andExpect(jsonPath("$.afgekochteerfpacht").value(DEFAULT_AFGEKOCHTEERFPACHT))
            .andExpect(jsonPath("$.afgesprokenconditiescore").value(DEFAULT_AFGESPROKENCONDITIESCORE))
            .andExpect(jsonPath("$.afkoopwaarde").value(sameNumber(DEFAULT_AFKOOPWAARDE)))
            .andExpect(jsonPath("$.asbestrapportageaanwezig").value(DEFAULT_ASBESTRAPPORTAGEAANWEZIG))
            .andExpect(jsonPath("$.bedragaankoop").value(sameNumber(DEFAULT_BEDRAGAANKOOP)))
            .andExpect(jsonPath("$.bestemmingsplan").value(DEFAULT_BESTEMMINGSPLAN))
            .andExpect(jsonPath("$.boekwaarde").value(sameNumber(DEFAULT_BOEKWAARDE)))
            .andExpect(jsonPath("$.bouwjaar").value(DEFAULT_BOUWJAAR))
            .andExpect(jsonPath("$.bouwwerk").value(DEFAULT_BOUWWERK))
            .andExpect(jsonPath("$.bovenliggendniveau").value(DEFAULT_BOVENLIGGENDNIVEAU))
            .andExpect(jsonPath("$.bovenliggendniveaucode").value(DEFAULT_BOVENLIGGENDNIVEAUCODE))
            .andExpect(jsonPath("$.brutovloeroppervlakte").value(DEFAULT_BRUTOVLOEROPPERVLAKTE))
            .andExpect(jsonPath("$.co2uitstoot").value(DEFAULT_CO_2_UITSTOOT))
            .andExpect(jsonPath("$.conditiescore").value(DEFAULT_CONDITIESCORE))
            .andExpect(jsonPath("$.datumafstoten").value(DEFAULT_DATUMAFSTOTEN.toString()))
            .andExpect(jsonPath("$.datumberekeningoppervlak").value(DEFAULT_DATUMBEREKENINGOPPERVLAK.toString()))
            .andExpect(jsonPath("$.datumeigendom").value(DEFAULT_DATUMEIGENDOM.toString()))
            .andExpect(jsonPath("$.datumverkoop").value(DEFAULT_DATUMVERKOOP.toString()))
            .andExpect(jsonPath("$.deelportefeuille").value(DEFAULT_DEELPORTEFEUILLE))
            .andExpect(jsonPath("$.energiekosten").value(sameNumber(DEFAULT_ENERGIEKOSTEN)))
            .andExpect(jsonPath("$.energielabel").value(DEFAULT_ENERGIELABEL))
            .andExpect(jsonPath("$.energieverbruik").value(DEFAULT_ENERGIEVERBRUIK))
            .andExpect(jsonPath("$.fiscalewaarde").value(sameNumber(DEFAULT_FISCALEWAARDE)))
            .andExpect(jsonPath("$.foto").value(DEFAULT_FOTO))
            .andExpect(jsonPath("$.gearchiveerd").value(DEFAULT_GEARCHIVEERD))
            .andExpect(jsonPath("$.herbouwwaarde").value(sameNumber(DEFAULT_HERBOUWWAARDE)))
            .andExpect(jsonPath("$.hoofdstuk").value(DEFAULT_HOOFDSTUK))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.jaarlaatsterenovatie").value(DEFAULT_JAARLAATSTERENOVATIE))
            .andExpect(jsonPath("$.locatie").value(DEFAULT_LOCATIE))
            .andExpect(jsonPath("$.marktwaarde").value(sameNumber(DEFAULT_MARKTWAARDE)))
            .andExpect(jsonPath("$.monument").value(DEFAULT_MONUMENT))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.eobjectstatus").value(DEFAULT_EOBJECTSTATUS))
            .andExpect(jsonPath("$.eobjectstatuscode").value(DEFAULT_EOBJECTSTATUSCODE))
            .andExpect(jsonPath("$.eobjecttype").value(DEFAULT_EOBJECTTYPE))
            .andExpect(jsonPath("$.eobjecttypecode").value(DEFAULT_EOBJECTTYPECODE))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.onderhoudscategorie").value(DEFAULT_ONDERHOUDSCATEGORIE))
            .andExpect(jsonPath("$.oppervlaktekantoor").value(DEFAULT_OPPERVLAKTEKANTOOR))
            .andExpect(jsonPath("$.portefeuille").value(DEFAULT_PORTEFEUILLE))
            .andExpect(jsonPath("$.portefeuillecode").value(DEFAULT_PORTEFEUILLECODE))
            .andExpect(jsonPath("$.provincie").value(DEFAULT_PROVINCIE))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING))
            .andExpect(jsonPath("$.verhuurbaarvloeroppervlak").value(DEFAULT_VERHUURBAARVLOEROPPERVLAK))
            .andExpect(jsonPath("$.verkoopbaarheid").value(DEFAULT_VERKOOPBAARHEID))
            .andExpect(jsonPath("$.verkoopbedrag").value(sameNumber(DEFAULT_VERKOOPBEDRAG)))
            .andExpect(jsonPath("$.verzekerdewaarde").value(sameNumber(DEFAULT_VERZEKERDEWAARDE)))
            .andExpect(jsonPath("$.waardegrond").value(sameNumber(DEFAULT_WAARDEGROND)))
            .andExpect(jsonPath("$.waardeopstal").value(sameNumber(DEFAULT_WAARDEOPSTAL)))
            .andExpect(jsonPath("$.wijk").value(DEFAULT_WIJK))
            .andExpect(jsonPath("$.wozwaarde").value(sameNumber(DEFAULT_WOZWAARDE)));
    }

    @Test
    @Transactional
    void getNonExistingVastgoedobject() throws Exception {
        // Get the vastgoedobject
        restVastgoedobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVastgoedobject() throws Exception {
        // Initialize the database
        vastgoedobjectRepository.saveAndFlush(vastgoedobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vastgoedobject
        Vastgoedobject updatedVastgoedobject = vastgoedobjectRepository.findById(vastgoedobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVastgoedobject are not directly saved in db
        em.detach(updatedVastgoedobject);
        updatedVastgoedobject
            .aantaletages(UPDATED_AANTALETAGES)
            .aantalparkeerplaatsen(UPDATED_AANTALPARKEERPLAATSEN)
            .aantalrioleringen(UPDATED_AANTALRIOLERINGEN)
            .adresaanduiding(UPDATED_ADRESAANDUIDING)
            .afgekochteerfpacht(UPDATED_AFGEKOCHTEERFPACHT)
            .afgesprokenconditiescore(UPDATED_AFGESPROKENCONDITIESCORE)
            .afkoopwaarde(UPDATED_AFKOOPWAARDE)
            .asbestrapportageaanwezig(UPDATED_ASBESTRAPPORTAGEAANWEZIG)
            .bedragaankoop(UPDATED_BEDRAGAANKOOP)
            .bestemmingsplan(UPDATED_BESTEMMINGSPLAN)
            .boekwaarde(UPDATED_BOEKWAARDE)
            .bouwjaar(UPDATED_BOUWJAAR)
            .bouwwerk(UPDATED_BOUWWERK)
            .bovenliggendniveau(UPDATED_BOVENLIGGENDNIVEAU)
            .bovenliggendniveaucode(UPDATED_BOVENLIGGENDNIVEAUCODE)
            .brutovloeroppervlakte(UPDATED_BRUTOVLOEROPPERVLAKTE)
            .co2uitstoot(UPDATED_CO_2_UITSTOOT)
            .conditiescore(UPDATED_CONDITIESCORE)
            .datumafstoten(UPDATED_DATUMAFSTOTEN)
            .datumberekeningoppervlak(UPDATED_DATUMBEREKENINGOPPERVLAK)
            .datumeigendom(UPDATED_DATUMEIGENDOM)
            .datumverkoop(UPDATED_DATUMVERKOOP)
            .deelportefeuille(UPDATED_DEELPORTEFEUILLE)
            .energiekosten(UPDATED_ENERGIEKOSTEN)
            .energielabel(UPDATED_ENERGIELABEL)
            .energieverbruik(UPDATED_ENERGIEVERBRUIK)
            .fiscalewaarde(UPDATED_FISCALEWAARDE)
            .foto(UPDATED_FOTO)
            .gearchiveerd(UPDATED_GEARCHIVEERD)
            .herbouwwaarde(UPDATED_HERBOUWWAARDE)
            .hoofdstuk(UPDATED_HOOFDSTUK)
            .identificatie(UPDATED_IDENTIFICATIE)
            .jaarlaatsterenovatie(UPDATED_JAARLAATSTERENOVATIE)
            .locatie(UPDATED_LOCATIE)
            .marktwaarde(UPDATED_MARKTWAARDE)
            .monument(UPDATED_MONUMENT)
            .naam(UPDATED_NAAM)
            .eobjectstatus(UPDATED_EOBJECTSTATUS)
            .eobjectstatuscode(UPDATED_EOBJECTSTATUSCODE)
            .eobjecttype(UPDATED_EOBJECTTYPE)
            .eobjecttypecode(UPDATED_EOBJECTTYPECODE)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .onderhoudscategorie(UPDATED_ONDERHOUDSCATEGORIE)
            .oppervlaktekantoor(UPDATED_OPPERVLAKTEKANTOOR)
            .portefeuille(UPDATED_PORTEFEUILLE)
            .portefeuillecode(UPDATED_PORTEFEUILLECODE)
            .provincie(UPDATED_PROVINCIE)
            .toelichting(UPDATED_TOELICHTING)
            .verhuurbaarvloeroppervlak(UPDATED_VERHUURBAARVLOEROPPERVLAK)
            .verkoopbaarheid(UPDATED_VERKOOPBAARHEID)
            .verkoopbedrag(UPDATED_VERKOOPBEDRAG)
            .verzekerdewaarde(UPDATED_VERZEKERDEWAARDE)
            .waardegrond(UPDATED_WAARDEGROND)
            .waardeopstal(UPDATED_WAARDEOPSTAL)
            .wijk(UPDATED_WIJK)
            .wozwaarde(UPDATED_WOZWAARDE);

        restVastgoedobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVastgoedobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVastgoedobject))
            )
            .andExpect(status().isOk());

        // Validate the Vastgoedobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVastgoedobjectToMatchAllProperties(updatedVastgoedobject);
    }

    @Test
    @Transactional
    void putNonExistingVastgoedobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVastgoedobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vastgoedobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vastgoedobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vastgoedobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVastgoedobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVastgoedobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vastgoedobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vastgoedobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVastgoedobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVastgoedobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vastgoedobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vastgoedobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVastgoedobjectWithPatch() throws Exception {
        // Initialize the database
        vastgoedobjectRepository.saveAndFlush(vastgoedobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vastgoedobject using partial update
        Vastgoedobject partialUpdatedVastgoedobject = new Vastgoedobject();
        partialUpdatedVastgoedobject.setId(vastgoedobject.getId());

        partialUpdatedVastgoedobject
            .aantaletages(UPDATED_AANTALETAGES)
            .aantalparkeerplaatsen(UPDATED_AANTALPARKEERPLAATSEN)
            .adresaanduiding(UPDATED_ADRESAANDUIDING)
            .afgekochteerfpacht(UPDATED_AFGEKOCHTEERFPACHT)
            .afkoopwaarde(UPDATED_AFKOOPWAARDE)
            .asbestrapportageaanwezig(UPDATED_ASBESTRAPPORTAGEAANWEZIG)
            .bestemmingsplan(UPDATED_BESTEMMINGSPLAN)
            .boekwaarde(UPDATED_BOEKWAARDE)
            .bouwjaar(UPDATED_BOUWJAAR)
            .bouwwerk(UPDATED_BOUWWERK)
            .bovenliggendniveaucode(UPDATED_BOVENLIGGENDNIVEAUCODE)
            .co2uitstoot(UPDATED_CO_2_UITSTOOT)
            .conditiescore(UPDATED_CONDITIESCORE)
            .datumberekeningoppervlak(UPDATED_DATUMBEREKENINGOPPERVLAK)
            .datumeigendom(UPDATED_DATUMEIGENDOM)
            .datumverkoop(UPDATED_DATUMVERKOOP)
            .deelportefeuille(UPDATED_DEELPORTEFEUILLE)
            .energiekosten(UPDATED_ENERGIEKOSTEN)
            .energielabel(UPDATED_ENERGIELABEL)
            .foto(UPDATED_FOTO)
            .herbouwwaarde(UPDATED_HERBOUWWAARDE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .locatie(UPDATED_LOCATIE)
            .monument(UPDATED_MONUMENT)
            .naam(UPDATED_NAAM)
            .eobjectstatus(UPDATED_EOBJECTSTATUS)
            .eobjecttypecode(UPDATED_EOBJECTTYPECODE)
            .oppervlaktekantoor(UPDATED_OPPERVLAKTEKANTOOR)
            .portefeuillecode(UPDATED_PORTEFEUILLECODE)
            .verhuurbaarvloeroppervlak(UPDATED_VERHUURBAARVLOEROPPERVLAK)
            .verkoopbedrag(UPDATED_VERKOOPBEDRAG)
            .waardegrond(UPDATED_WAARDEGROND)
            .waardeopstal(UPDATED_WAARDEOPSTAL)
            .wozwaarde(UPDATED_WOZWAARDE);

        restVastgoedobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVastgoedobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVastgoedobject))
            )
            .andExpect(status().isOk());

        // Validate the Vastgoedobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVastgoedobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVastgoedobject, vastgoedobject),
            getPersistedVastgoedobject(vastgoedobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateVastgoedobjectWithPatch() throws Exception {
        // Initialize the database
        vastgoedobjectRepository.saveAndFlush(vastgoedobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vastgoedobject using partial update
        Vastgoedobject partialUpdatedVastgoedobject = new Vastgoedobject();
        partialUpdatedVastgoedobject.setId(vastgoedobject.getId());

        partialUpdatedVastgoedobject
            .aantaletages(UPDATED_AANTALETAGES)
            .aantalparkeerplaatsen(UPDATED_AANTALPARKEERPLAATSEN)
            .aantalrioleringen(UPDATED_AANTALRIOLERINGEN)
            .adresaanduiding(UPDATED_ADRESAANDUIDING)
            .afgekochteerfpacht(UPDATED_AFGEKOCHTEERFPACHT)
            .afgesprokenconditiescore(UPDATED_AFGESPROKENCONDITIESCORE)
            .afkoopwaarde(UPDATED_AFKOOPWAARDE)
            .asbestrapportageaanwezig(UPDATED_ASBESTRAPPORTAGEAANWEZIG)
            .bedragaankoop(UPDATED_BEDRAGAANKOOP)
            .bestemmingsplan(UPDATED_BESTEMMINGSPLAN)
            .boekwaarde(UPDATED_BOEKWAARDE)
            .bouwjaar(UPDATED_BOUWJAAR)
            .bouwwerk(UPDATED_BOUWWERK)
            .bovenliggendniveau(UPDATED_BOVENLIGGENDNIVEAU)
            .bovenliggendniveaucode(UPDATED_BOVENLIGGENDNIVEAUCODE)
            .brutovloeroppervlakte(UPDATED_BRUTOVLOEROPPERVLAKTE)
            .co2uitstoot(UPDATED_CO_2_UITSTOOT)
            .conditiescore(UPDATED_CONDITIESCORE)
            .datumafstoten(UPDATED_DATUMAFSTOTEN)
            .datumberekeningoppervlak(UPDATED_DATUMBEREKENINGOPPERVLAK)
            .datumeigendom(UPDATED_DATUMEIGENDOM)
            .datumverkoop(UPDATED_DATUMVERKOOP)
            .deelportefeuille(UPDATED_DEELPORTEFEUILLE)
            .energiekosten(UPDATED_ENERGIEKOSTEN)
            .energielabel(UPDATED_ENERGIELABEL)
            .energieverbruik(UPDATED_ENERGIEVERBRUIK)
            .fiscalewaarde(UPDATED_FISCALEWAARDE)
            .foto(UPDATED_FOTO)
            .gearchiveerd(UPDATED_GEARCHIVEERD)
            .herbouwwaarde(UPDATED_HERBOUWWAARDE)
            .hoofdstuk(UPDATED_HOOFDSTUK)
            .identificatie(UPDATED_IDENTIFICATIE)
            .jaarlaatsterenovatie(UPDATED_JAARLAATSTERENOVATIE)
            .locatie(UPDATED_LOCATIE)
            .marktwaarde(UPDATED_MARKTWAARDE)
            .monument(UPDATED_MONUMENT)
            .naam(UPDATED_NAAM)
            .eobjectstatus(UPDATED_EOBJECTSTATUS)
            .eobjectstatuscode(UPDATED_EOBJECTSTATUSCODE)
            .eobjecttype(UPDATED_EOBJECTTYPE)
            .eobjecttypecode(UPDATED_EOBJECTTYPECODE)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .onderhoudscategorie(UPDATED_ONDERHOUDSCATEGORIE)
            .oppervlaktekantoor(UPDATED_OPPERVLAKTEKANTOOR)
            .portefeuille(UPDATED_PORTEFEUILLE)
            .portefeuillecode(UPDATED_PORTEFEUILLECODE)
            .provincie(UPDATED_PROVINCIE)
            .toelichting(UPDATED_TOELICHTING)
            .verhuurbaarvloeroppervlak(UPDATED_VERHUURBAARVLOEROPPERVLAK)
            .verkoopbaarheid(UPDATED_VERKOOPBAARHEID)
            .verkoopbedrag(UPDATED_VERKOOPBEDRAG)
            .verzekerdewaarde(UPDATED_VERZEKERDEWAARDE)
            .waardegrond(UPDATED_WAARDEGROND)
            .waardeopstal(UPDATED_WAARDEOPSTAL)
            .wijk(UPDATED_WIJK)
            .wozwaarde(UPDATED_WOZWAARDE);

        restVastgoedobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVastgoedobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVastgoedobject))
            )
            .andExpect(status().isOk());

        // Validate the Vastgoedobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVastgoedobjectUpdatableFieldsEquals(partialUpdatedVastgoedobject, getPersistedVastgoedobject(partialUpdatedVastgoedobject));
    }

    @Test
    @Transactional
    void patchNonExistingVastgoedobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVastgoedobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vastgoedobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vastgoedobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vastgoedobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVastgoedobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVastgoedobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vastgoedobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vastgoedobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVastgoedobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVastgoedobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vastgoedobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vastgoedobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVastgoedobject() throws Exception {
        // Initialize the database
        vastgoedobjectRepository.saveAndFlush(vastgoedobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vastgoedobject
        restVastgoedobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, vastgoedobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vastgoedobjectRepository.count();
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

    protected Vastgoedobject getPersistedVastgoedobject(Vastgoedobject vastgoedobject) {
        return vastgoedobjectRepository.findById(vastgoedobject.getId()).orElseThrow();
    }

    protected void assertPersistedVastgoedobjectToMatchAllProperties(Vastgoedobject expectedVastgoedobject) {
        assertVastgoedobjectAllPropertiesEquals(expectedVastgoedobject, getPersistedVastgoedobject(expectedVastgoedobject));
    }

    protected void assertPersistedVastgoedobjectToMatchUpdatableProperties(Vastgoedobject expectedVastgoedobject) {
        assertVastgoedobjectAllUpdatablePropertiesEquals(expectedVastgoedobject, getPersistedVastgoedobject(expectedVastgoedobject));
    }
}
