package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BeheerobjectAsserts.*;
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
import nl.ritense.demo.domain.Beheerobject;
import nl.ritense.demo.domain.Melding;
import nl.ritense.demo.repository.BeheerobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BeheerobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BeheerobjectResourceIT {

    private static final String DEFAULT_AANGEMAAKTDOOR = "AAAAAAAAAA";
    private static final String UPDATED_AANGEMAAKTDOOR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BEGINGARANTIEPERIODE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BEGINGARANTIEPERIODE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_BEHEERGEBIED = "AAAAAAAAAA";
    private static final String UPDATED_BEHEERGEBIED = "BBBBBBBBBB";

    private static final String DEFAULT_BEHEEROBJECTBEHEERVAK = "AAAAAAAAAA";
    private static final String UPDATED_BEHEEROBJECTBEHEERVAK = "BBBBBBBBBB";

    private static final String DEFAULT_BEHEEROBJECTGEBRUIKSFUNCTIE = "AAAAAAAAAA";
    private static final String UPDATED_BEHEEROBJECTGEBRUIKSFUNCTIE = "BBBBBBBBBB";

    private static final String DEFAULT_BEHEEROBJECTMEMO = "AAAAAAAAAA";
    private static final String UPDATED_BEHEEROBJECTMEMO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BESCHERMDEFLORAENFAUNA = false;
    private static final Boolean UPDATED_BESCHERMDEFLORAENFAUNA = true;

    private static final String DEFAULT_BUURT = "AAAAAAAAAA";
    private static final String UPDATED_BUURT = "BBBBBBBBBB";

    private static final String DEFAULT_CONVERSIEID = "AAAAAAAAAA";
    private static final String UPDATED_CONVERSIEID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMMUTATIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMMUTATIE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMOPLEVERING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMOPLEVERING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMPUBLICATIELV = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMPUBLICATIELV = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMVERWIJDERING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMVERWIJDERING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EINDEGARANTIEPERIODE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EINDEGARANTIEPERIODE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEBIEDSTYPE = "AAAAAAAAAA";
    private static final String UPDATED_GEBIEDSTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_GEMEENTE = "AAAAAAAAAA";
    private static final String UPDATED_GEMEENTE = "BBBBBBBBBB";

    private static final String DEFAULT_GEOMETRIE = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIE = "BBBBBBBBBB";

    private static final String DEFAULT_GEWIJZIGDDOOR = "AAAAAAAAAA";
    private static final String UPDATED_GEWIJZIGDDOOR = "BBBBBBBBBB";

    private static final String DEFAULT_GRONDSOORT = "AAAAAAAAAA";
    private static final String UPDATED_GRONDSOORT = "BBBBBBBBBB";

    private static final String DEFAULT_GRONDSOORTPLUS = "AAAAAAAAAA";
    private static final String UPDATED_GRONDSOORTPLUS = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIEIMBOR = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIEIMBOR = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIEIMGEO = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIEIMGEO = "BBBBBBBBBB";

    private static final String DEFAULT_JAARVANAANLEG = "AAAAAAAAAA";
    private static final String UPDATED_JAARVANAANLEG = "BBBBBBBBBB";

    private static final String DEFAULT_EOBJECTBEGINTIJD = "AAAAAAAAAA";
    private static final String UPDATED_EOBJECTBEGINTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_EOBJECTEINDTIJD = "AAAAAAAAAA";
    private static final String UPDATED_EOBJECTEINDTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERHOUDSPLICHTIGE = "AAAAAAAAAA";
    private static final String UPDATED_ONDERHOUDSPLICHTIGE = "BBBBBBBBBB";

    private static final String DEFAULT_OPENBARERUIMTE = "AAAAAAAAAA";
    private static final String UPDATED_OPENBARERUIMTE = "BBBBBBBBBB";

    private static final String DEFAULT_POSTCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIEVEHOOGTELIGGING = "AAAAAAAAAA";
    private static final String UPDATED_RELATIEVEHOOGTELIGGING = "BBBBBBBBBB";

    private static final String DEFAULT_STADSDEEL = "AAAAAAAAAA";
    private static final String UPDATED_STADSDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_THEORETISCHEINDEJAAR = "AAAAAAAAAA";
    private static final String UPDATED_THEORETISCHEINDEJAAR = "BBBBBBBBBB";

    private static final String DEFAULT_TIJDSTIPREGISTRATIE = "AAAAAAAAAA";
    private static final String UPDATED_TIJDSTIPREGISTRATIE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEBEHEERDER = "AAAAAAAAAA";
    private static final String UPDATED_TYPEBEHEERDER = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEBEHEERDERPLUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPEBEHEERDERPLUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEEIGENAAR = "AAAAAAAAAA";
    private static final String UPDATED_TYPEEIGENAAR = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEEIGENAARPLUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPEEIGENAARPLUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPELIGGING = "AAAAAAAAAA";
    private static final String UPDATED_TYPELIGGING = "BBBBBBBBBB";

    private static final String DEFAULT_WATERSCHAP = "AAAAAAAAAA";
    private static final String UPDATED_WATERSCHAP = "BBBBBBBBBB";

    private static final String DEFAULT_WIJK = "AAAAAAAAAA";
    private static final String UPDATED_WIJK = "BBBBBBBBBB";

    private static final String DEFAULT_WOONPLAATS = "AAAAAAAAAA";
    private static final String UPDATED_WOONPLAATS = "BBBBBBBBBB";

    private static final String DEFAULT_ZETTINGSGEVOELIGHEID = "AAAAAAAAAA";
    private static final String UPDATED_ZETTINGSGEVOELIGHEID = "BBBBBBBBBB";

    private static final String DEFAULT_ZETTINGSGEVOELIGHEIDPLUS = "AAAAAAAAAA";
    private static final String UPDATED_ZETTINGSGEVOELIGHEIDPLUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/beheerobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BeheerobjectRepository beheerobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeheerobjectMockMvc;

    private Beheerobject beheerobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beheerobject createEntity(EntityManager em) {
        Beheerobject beheerobject = new Beheerobject()
            .aangemaaktdoor(DEFAULT_AANGEMAAKTDOOR)
            .begingarantieperiode(DEFAULT_BEGINGARANTIEPERIODE)
            .beheergebied(DEFAULT_BEHEERGEBIED)
            .beheerobjectbeheervak(DEFAULT_BEHEEROBJECTBEHEERVAK)
            .beheerobjectgebruiksfunctie(DEFAULT_BEHEEROBJECTGEBRUIKSFUNCTIE)
            .beheerobjectmemo(DEFAULT_BEHEEROBJECTMEMO)
            .beschermdefloraenfauna(DEFAULT_BESCHERMDEFLORAENFAUNA)
            .buurt(DEFAULT_BUURT)
            .conversieid(DEFAULT_CONVERSIEID)
            .datummutatie(DEFAULT_DATUMMUTATIE)
            .datumoplevering(DEFAULT_DATUMOPLEVERING)
            .datumpublicatielv(DEFAULT_DATUMPUBLICATIELV)
            .datumverwijdering(DEFAULT_DATUMVERWIJDERING)
            .eindegarantieperiode(DEFAULT_EINDEGARANTIEPERIODE)
            .gebiedstype(DEFAULT_GEBIEDSTYPE)
            .gemeente(DEFAULT_GEMEENTE)
            .geometrie(DEFAULT_GEOMETRIE)
            .gewijzigddoor(DEFAULT_GEWIJZIGDDOOR)
            .grondsoort(DEFAULT_GRONDSOORT)
            .grondsoortplus(DEFAULT_GRONDSOORTPLUS)
            .identificatieimbor(DEFAULT_IDENTIFICATIEIMBOR)
            .identificatieimgeo(DEFAULT_IDENTIFICATIEIMGEO)
            .jaarvanaanleg(DEFAULT_JAARVANAANLEG)
            .eobjectbegintijd(DEFAULT_EOBJECTBEGINTIJD)
            .eobjecteindtijd(DEFAULT_EOBJECTEINDTIJD)
            .onderhoudsplichtige(DEFAULT_ONDERHOUDSPLICHTIGE)
            .openbareruimte(DEFAULT_OPENBARERUIMTE)
            .postcode(DEFAULT_POSTCODE)
            .relatievehoogteligging(DEFAULT_RELATIEVEHOOGTELIGGING)
            .stadsdeel(DEFAULT_STADSDEEL)
            .status(DEFAULT_STATUS)
            .theoretischeindejaar(DEFAULT_THEORETISCHEINDEJAAR)
            .tijdstipregistratie(DEFAULT_TIJDSTIPREGISTRATIE)
            .typebeheerder(DEFAULT_TYPEBEHEERDER)
            .typebeheerderplus(DEFAULT_TYPEBEHEERDERPLUS)
            .typeeigenaar(DEFAULT_TYPEEIGENAAR)
            .typeeigenaarplus(DEFAULT_TYPEEIGENAARPLUS)
            .typeligging(DEFAULT_TYPELIGGING)
            .waterschap(DEFAULT_WATERSCHAP)
            .wijk(DEFAULT_WIJK)
            .woonplaats(DEFAULT_WOONPLAATS)
            .zettingsgevoeligheid(DEFAULT_ZETTINGSGEVOELIGHEID)
            .zettingsgevoeligheidplus(DEFAULT_ZETTINGSGEVOELIGHEIDPLUS);
        // Add required entity
        Melding melding;
        if (TestUtil.findAll(em, Melding.class).isEmpty()) {
            melding = MeldingResourceIT.createEntity(em);
            em.persist(melding);
            em.flush();
        } else {
            melding = TestUtil.findAll(em, Melding.class).get(0);
        }
        beheerobject.getBetreftMeldings().add(melding);
        return beheerobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beheerobject createUpdatedEntity(EntityManager em) {
        Beheerobject beheerobject = new Beheerobject()
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .begingarantieperiode(UPDATED_BEGINGARANTIEPERIODE)
            .beheergebied(UPDATED_BEHEERGEBIED)
            .beheerobjectbeheervak(UPDATED_BEHEEROBJECTBEHEERVAK)
            .beheerobjectgebruiksfunctie(UPDATED_BEHEEROBJECTGEBRUIKSFUNCTIE)
            .beheerobjectmemo(UPDATED_BEHEEROBJECTMEMO)
            .beschermdefloraenfauna(UPDATED_BESCHERMDEFLORAENFAUNA)
            .buurt(UPDATED_BUURT)
            .conversieid(UPDATED_CONVERSIEID)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .datumoplevering(UPDATED_DATUMOPLEVERING)
            .datumpublicatielv(UPDATED_DATUMPUBLICATIELV)
            .datumverwijdering(UPDATED_DATUMVERWIJDERING)
            .eindegarantieperiode(UPDATED_EINDEGARANTIEPERIODE)
            .gebiedstype(UPDATED_GEBIEDSTYPE)
            .gemeente(UPDATED_GEMEENTE)
            .geometrie(UPDATED_GEOMETRIE)
            .gewijzigddoor(UPDATED_GEWIJZIGDDOOR)
            .grondsoort(UPDATED_GRONDSOORT)
            .grondsoortplus(UPDATED_GRONDSOORTPLUS)
            .identificatieimbor(UPDATED_IDENTIFICATIEIMBOR)
            .identificatieimgeo(UPDATED_IDENTIFICATIEIMGEO)
            .jaarvanaanleg(UPDATED_JAARVANAANLEG)
            .eobjectbegintijd(UPDATED_EOBJECTBEGINTIJD)
            .eobjecteindtijd(UPDATED_EOBJECTEINDTIJD)
            .onderhoudsplichtige(UPDATED_ONDERHOUDSPLICHTIGE)
            .openbareruimte(UPDATED_OPENBARERUIMTE)
            .postcode(UPDATED_POSTCODE)
            .relatievehoogteligging(UPDATED_RELATIEVEHOOGTELIGGING)
            .stadsdeel(UPDATED_STADSDEEL)
            .status(UPDATED_STATUS)
            .theoretischeindejaar(UPDATED_THEORETISCHEINDEJAAR)
            .tijdstipregistratie(UPDATED_TIJDSTIPREGISTRATIE)
            .typebeheerder(UPDATED_TYPEBEHEERDER)
            .typebeheerderplus(UPDATED_TYPEBEHEERDERPLUS)
            .typeeigenaar(UPDATED_TYPEEIGENAAR)
            .typeeigenaarplus(UPDATED_TYPEEIGENAARPLUS)
            .typeligging(UPDATED_TYPELIGGING)
            .waterschap(UPDATED_WATERSCHAP)
            .wijk(UPDATED_WIJK)
            .woonplaats(UPDATED_WOONPLAATS)
            .zettingsgevoeligheid(UPDATED_ZETTINGSGEVOELIGHEID)
            .zettingsgevoeligheidplus(UPDATED_ZETTINGSGEVOELIGHEIDPLUS);
        // Add required entity
        Melding melding;
        if (TestUtil.findAll(em, Melding.class).isEmpty()) {
            melding = MeldingResourceIT.createUpdatedEntity(em);
            em.persist(melding);
            em.flush();
        } else {
            melding = TestUtil.findAll(em, Melding.class).get(0);
        }
        beheerobject.getBetreftMeldings().add(melding);
        return beheerobject;
    }

    @BeforeEach
    public void initTest() {
        beheerobject = createEntity(em);
    }

    @Test
    @Transactional
    void createBeheerobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Beheerobject
        var returnedBeheerobject = om.readValue(
            restBeheerobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beheerobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Beheerobject.class
        );

        // Validate the Beheerobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBeheerobjectUpdatableFieldsEquals(returnedBeheerobject, getPersistedBeheerobject(returnedBeheerobject));
    }

    @Test
    @Transactional
    void createBeheerobjectWithExistingId() throws Exception {
        // Create the Beheerobject with an existing ID
        beheerobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeheerobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beheerobject)))
            .andExpect(status().isBadRequest());

        // Validate the Beheerobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBeheerobjects() throws Exception {
        // Initialize the database
        beheerobjectRepository.saveAndFlush(beheerobject);

        // Get all the beheerobjectList
        restBeheerobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beheerobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].aangemaaktdoor").value(hasItem(DEFAULT_AANGEMAAKTDOOR)))
            .andExpect(jsonPath("$.[*].begingarantieperiode").value(hasItem(DEFAULT_BEGINGARANTIEPERIODE.toString())))
            .andExpect(jsonPath("$.[*].beheergebied").value(hasItem(DEFAULT_BEHEERGEBIED)))
            .andExpect(jsonPath("$.[*].beheerobjectbeheervak").value(hasItem(DEFAULT_BEHEEROBJECTBEHEERVAK)))
            .andExpect(jsonPath("$.[*].beheerobjectgebruiksfunctie").value(hasItem(DEFAULT_BEHEEROBJECTGEBRUIKSFUNCTIE)))
            .andExpect(jsonPath("$.[*].beheerobjectmemo").value(hasItem(DEFAULT_BEHEEROBJECTMEMO)))
            .andExpect(jsonPath("$.[*].beschermdefloraenfauna").value(hasItem(DEFAULT_BESCHERMDEFLORAENFAUNA.booleanValue())))
            .andExpect(jsonPath("$.[*].buurt").value(hasItem(DEFAULT_BUURT)))
            .andExpect(jsonPath("$.[*].conversieid").value(hasItem(DEFAULT_CONVERSIEID)))
            .andExpect(jsonPath("$.[*].datummutatie").value(hasItem(DEFAULT_DATUMMUTATIE.toString())))
            .andExpect(jsonPath("$.[*].datumoplevering").value(hasItem(DEFAULT_DATUMOPLEVERING.toString())))
            .andExpect(jsonPath("$.[*].datumpublicatielv").value(hasItem(DEFAULT_DATUMPUBLICATIELV.toString())))
            .andExpect(jsonPath("$.[*].datumverwijdering").value(hasItem(DEFAULT_DATUMVERWIJDERING.toString())))
            .andExpect(jsonPath("$.[*].eindegarantieperiode").value(hasItem(DEFAULT_EINDEGARANTIEPERIODE.toString())))
            .andExpect(jsonPath("$.[*].gebiedstype").value(hasItem(DEFAULT_GEBIEDSTYPE)))
            .andExpect(jsonPath("$.[*].gemeente").value(hasItem(DEFAULT_GEMEENTE)))
            .andExpect(jsonPath("$.[*].geometrie").value(hasItem(DEFAULT_GEOMETRIE)))
            .andExpect(jsonPath("$.[*].gewijzigddoor").value(hasItem(DEFAULT_GEWIJZIGDDOOR)))
            .andExpect(jsonPath("$.[*].grondsoort").value(hasItem(DEFAULT_GRONDSOORT)))
            .andExpect(jsonPath("$.[*].grondsoortplus").value(hasItem(DEFAULT_GRONDSOORTPLUS)))
            .andExpect(jsonPath("$.[*].identificatieimbor").value(hasItem(DEFAULT_IDENTIFICATIEIMBOR)))
            .andExpect(jsonPath("$.[*].identificatieimgeo").value(hasItem(DEFAULT_IDENTIFICATIEIMGEO)))
            .andExpect(jsonPath("$.[*].jaarvanaanleg").value(hasItem(DEFAULT_JAARVANAANLEG)))
            .andExpect(jsonPath("$.[*].eobjectbegintijd").value(hasItem(DEFAULT_EOBJECTBEGINTIJD)))
            .andExpect(jsonPath("$.[*].eobjecteindtijd").value(hasItem(DEFAULT_EOBJECTEINDTIJD)))
            .andExpect(jsonPath("$.[*].onderhoudsplichtige").value(hasItem(DEFAULT_ONDERHOUDSPLICHTIGE)))
            .andExpect(jsonPath("$.[*].openbareruimte").value(hasItem(DEFAULT_OPENBARERUIMTE)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].relatievehoogteligging").value(hasItem(DEFAULT_RELATIEVEHOOGTELIGGING)))
            .andExpect(jsonPath("$.[*].stadsdeel").value(hasItem(DEFAULT_STADSDEEL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].theoretischeindejaar").value(hasItem(DEFAULT_THEORETISCHEINDEJAAR)))
            .andExpect(jsonPath("$.[*].tijdstipregistratie").value(hasItem(DEFAULT_TIJDSTIPREGISTRATIE)))
            .andExpect(jsonPath("$.[*].typebeheerder").value(hasItem(DEFAULT_TYPEBEHEERDER)))
            .andExpect(jsonPath("$.[*].typebeheerderplus").value(hasItem(DEFAULT_TYPEBEHEERDERPLUS)))
            .andExpect(jsonPath("$.[*].typeeigenaar").value(hasItem(DEFAULT_TYPEEIGENAAR)))
            .andExpect(jsonPath("$.[*].typeeigenaarplus").value(hasItem(DEFAULT_TYPEEIGENAARPLUS)))
            .andExpect(jsonPath("$.[*].typeligging").value(hasItem(DEFAULT_TYPELIGGING)))
            .andExpect(jsonPath("$.[*].waterschap").value(hasItem(DEFAULT_WATERSCHAP)))
            .andExpect(jsonPath("$.[*].wijk").value(hasItem(DEFAULT_WIJK)))
            .andExpect(jsonPath("$.[*].woonplaats").value(hasItem(DEFAULT_WOONPLAATS)))
            .andExpect(jsonPath("$.[*].zettingsgevoeligheid").value(hasItem(DEFAULT_ZETTINGSGEVOELIGHEID)))
            .andExpect(jsonPath("$.[*].zettingsgevoeligheidplus").value(hasItem(DEFAULT_ZETTINGSGEVOELIGHEIDPLUS)));
    }

    @Test
    @Transactional
    void getBeheerobject() throws Exception {
        // Initialize the database
        beheerobjectRepository.saveAndFlush(beheerobject);

        // Get the beheerobject
        restBeheerobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, beheerobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beheerobject.getId().intValue()))
            .andExpect(jsonPath("$.aangemaaktdoor").value(DEFAULT_AANGEMAAKTDOOR))
            .andExpect(jsonPath("$.begingarantieperiode").value(DEFAULT_BEGINGARANTIEPERIODE.toString()))
            .andExpect(jsonPath("$.beheergebied").value(DEFAULT_BEHEERGEBIED))
            .andExpect(jsonPath("$.beheerobjectbeheervak").value(DEFAULT_BEHEEROBJECTBEHEERVAK))
            .andExpect(jsonPath("$.beheerobjectgebruiksfunctie").value(DEFAULT_BEHEEROBJECTGEBRUIKSFUNCTIE))
            .andExpect(jsonPath("$.beheerobjectmemo").value(DEFAULT_BEHEEROBJECTMEMO))
            .andExpect(jsonPath("$.beschermdefloraenfauna").value(DEFAULT_BESCHERMDEFLORAENFAUNA.booleanValue()))
            .andExpect(jsonPath("$.buurt").value(DEFAULT_BUURT))
            .andExpect(jsonPath("$.conversieid").value(DEFAULT_CONVERSIEID))
            .andExpect(jsonPath("$.datummutatie").value(DEFAULT_DATUMMUTATIE.toString()))
            .andExpect(jsonPath("$.datumoplevering").value(DEFAULT_DATUMOPLEVERING.toString()))
            .andExpect(jsonPath("$.datumpublicatielv").value(DEFAULT_DATUMPUBLICATIELV.toString()))
            .andExpect(jsonPath("$.datumverwijdering").value(DEFAULT_DATUMVERWIJDERING.toString()))
            .andExpect(jsonPath("$.eindegarantieperiode").value(DEFAULT_EINDEGARANTIEPERIODE.toString()))
            .andExpect(jsonPath("$.gebiedstype").value(DEFAULT_GEBIEDSTYPE))
            .andExpect(jsonPath("$.gemeente").value(DEFAULT_GEMEENTE))
            .andExpect(jsonPath("$.geometrie").value(DEFAULT_GEOMETRIE))
            .andExpect(jsonPath("$.gewijzigddoor").value(DEFAULT_GEWIJZIGDDOOR))
            .andExpect(jsonPath("$.grondsoort").value(DEFAULT_GRONDSOORT))
            .andExpect(jsonPath("$.grondsoortplus").value(DEFAULT_GRONDSOORTPLUS))
            .andExpect(jsonPath("$.identificatieimbor").value(DEFAULT_IDENTIFICATIEIMBOR))
            .andExpect(jsonPath("$.identificatieimgeo").value(DEFAULT_IDENTIFICATIEIMGEO))
            .andExpect(jsonPath("$.jaarvanaanleg").value(DEFAULT_JAARVANAANLEG))
            .andExpect(jsonPath("$.eobjectbegintijd").value(DEFAULT_EOBJECTBEGINTIJD))
            .andExpect(jsonPath("$.eobjecteindtijd").value(DEFAULT_EOBJECTEINDTIJD))
            .andExpect(jsonPath("$.onderhoudsplichtige").value(DEFAULT_ONDERHOUDSPLICHTIGE))
            .andExpect(jsonPath("$.openbareruimte").value(DEFAULT_OPENBARERUIMTE))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE))
            .andExpect(jsonPath("$.relatievehoogteligging").value(DEFAULT_RELATIEVEHOOGTELIGGING))
            .andExpect(jsonPath("$.stadsdeel").value(DEFAULT_STADSDEEL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.theoretischeindejaar").value(DEFAULT_THEORETISCHEINDEJAAR))
            .andExpect(jsonPath("$.tijdstipregistratie").value(DEFAULT_TIJDSTIPREGISTRATIE))
            .andExpect(jsonPath("$.typebeheerder").value(DEFAULT_TYPEBEHEERDER))
            .andExpect(jsonPath("$.typebeheerderplus").value(DEFAULT_TYPEBEHEERDERPLUS))
            .andExpect(jsonPath("$.typeeigenaar").value(DEFAULT_TYPEEIGENAAR))
            .andExpect(jsonPath("$.typeeigenaarplus").value(DEFAULT_TYPEEIGENAARPLUS))
            .andExpect(jsonPath("$.typeligging").value(DEFAULT_TYPELIGGING))
            .andExpect(jsonPath("$.waterschap").value(DEFAULT_WATERSCHAP))
            .andExpect(jsonPath("$.wijk").value(DEFAULT_WIJK))
            .andExpect(jsonPath("$.woonplaats").value(DEFAULT_WOONPLAATS))
            .andExpect(jsonPath("$.zettingsgevoeligheid").value(DEFAULT_ZETTINGSGEVOELIGHEID))
            .andExpect(jsonPath("$.zettingsgevoeligheidplus").value(DEFAULT_ZETTINGSGEVOELIGHEIDPLUS));
    }

    @Test
    @Transactional
    void getNonExistingBeheerobject() throws Exception {
        // Get the beheerobject
        restBeheerobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBeheerobject() throws Exception {
        // Initialize the database
        beheerobjectRepository.saveAndFlush(beheerobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beheerobject
        Beheerobject updatedBeheerobject = beheerobjectRepository.findById(beheerobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBeheerobject are not directly saved in db
        em.detach(updatedBeheerobject);
        updatedBeheerobject
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .begingarantieperiode(UPDATED_BEGINGARANTIEPERIODE)
            .beheergebied(UPDATED_BEHEERGEBIED)
            .beheerobjectbeheervak(UPDATED_BEHEEROBJECTBEHEERVAK)
            .beheerobjectgebruiksfunctie(UPDATED_BEHEEROBJECTGEBRUIKSFUNCTIE)
            .beheerobjectmemo(UPDATED_BEHEEROBJECTMEMO)
            .beschermdefloraenfauna(UPDATED_BESCHERMDEFLORAENFAUNA)
            .buurt(UPDATED_BUURT)
            .conversieid(UPDATED_CONVERSIEID)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .datumoplevering(UPDATED_DATUMOPLEVERING)
            .datumpublicatielv(UPDATED_DATUMPUBLICATIELV)
            .datumverwijdering(UPDATED_DATUMVERWIJDERING)
            .eindegarantieperiode(UPDATED_EINDEGARANTIEPERIODE)
            .gebiedstype(UPDATED_GEBIEDSTYPE)
            .gemeente(UPDATED_GEMEENTE)
            .geometrie(UPDATED_GEOMETRIE)
            .gewijzigddoor(UPDATED_GEWIJZIGDDOOR)
            .grondsoort(UPDATED_GRONDSOORT)
            .grondsoortplus(UPDATED_GRONDSOORTPLUS)
            .identificatieimbor(UPDATED_IDENTIFICATIEIMBOR)
            .identificatieimgeo(UPDATED_IDENTIFICATIEIMGEO)
            .jaarvanaanleg(UPDATED_JAARVANAANLEG)
            .eobjectbegintijd(UPDATED_EOBJECTBEGINTIJD)
            .eobjecteindtijd(UPDATED_EOBJECTEINDTIJD)
            .onderhoudsplichtige(UPDATED_ONDERHOUDSPLICHTIGE)
            .openbareruimte(UPDATED_OPENBARERUIMTE)
            .postcode(UPDATED_POSTCODE)
            .relatievehoogteligging(UPDATED_RELATIEVEHOOGTELIGGING)
            .stadsdeel(UPDATED_STADSDEEL)
            .status(UPDATED_STATUS)
            .theoretischeindejaar(UPDATED_THEORETISCHEINDEJAAR)
            .tijdstipregistratie(UPDATED_TIJDSTIPREGISTRATIE)
            .typebeheerder(UPDATED_TYPEBEHEERDER)
            .typebeheerderplus(UPDATED_TYPEBEHEERDERPLUS)
            .typeeigenaar(UPDATED_TYPEEIGENAAR)
            .typeeigenaarplus(UPDATED_TYPEEIGENAARPLUS)
            .typeligging(UPDATED_TYPELIGGING)
            .waterschap(UPDATED_WATERSCHAP)
            .wijk(UPDATED_WIJK)
            .woonplaats(UPDATED_WOONPLAATS)
            .zettingsgevoeligheid(UPDATED_ZETTINGSGEVOELIGHEID)
            .zettingsgevoeligheidplus(UPDATED_ZETTINGSGEVOELIGHEIDPLUS);

        restBeheerobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBeheerobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBeheerobject))
            )
            .andExpect(status().isOk());

        // Validate the Beheerobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBeheerobjectToMatchAllProperties(updatedBeheerobject);
    }

    @Test
    @Transactional
    void putNonExistingBeheerobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beheerobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeheerobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, beheerobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beheerobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beheerobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBeheerobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beheerobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeheerobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beheerobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beheerobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBeheerobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beheerobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeheerobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beheerobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beheerobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBeheerobjectWithPatch() throws Exception {
        // Initialize the database
        beheerobjectRepository.saveAndFlush(beheerobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beheerobject using partial update
        Beheerobject partialUpdatedBeheerobject = new Beheerobject();
        partialUpdatedBeheerobject.setId(beheerobject.getId());

        partialUpdatedBeheerobject
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .beheergebied(UPDATED_BEHEERGEBIED)
            .beheerobjectgebruiksfunctie(UPDATED_BEHEEROBJECTGEBRUIKSFUNCTIE)
            .beheerobjectmemo(UPDATED_BEHEEROBJECTMEMO)
            .buurt(UPDATED_BUURT)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .datumoplevering(UPDATED_DATUMOPLEVERING)
            .datumverwijdering(UPDATED_DATUMVERWIJDERING)
            .eindegarantieperiode(UPDATED_EINDEGARANTIEPERIODE)
            .eobjecteindtijd(UPDATED_EOBJECTEINDTIJD)
            .onderhoudsplichtige(UPDATED_ONDERHOUDSPLICHTIGE)
            .stadsdeel(UPDATED_STADSDEEL)
            .status(UPDATED_STATUS)
            .theoretischeindejaar(UPDATED_THEORETISCHEINDEJAAR)
            .typebeheerder(UPDATED_TYPEBEHEERDER)
            .zettingsgevoeligheid(UPDATED_ZETTINGSGEVOELIGHEID)
            .zettingsgevoeligheidplus(UPDATED_ZETTINGSGEVOELIGHEIDPLUS);

        restBeheerobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeheerobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeheerobject))
            )
            .andExpect(status().isOk());

        // Validate the Beheerobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeheerobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBeheerobject, beheerobject),
            getPersistedBeheerobject(beheerobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateBeheerobjectWithPatch() throws Exception {
        // Initialize the database
        beheerobjectRepository.saveAndFlush(beheerobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beheerobject using partial update
        Beheerobject partialUpdatedBeheerobject = new Beheerobject();
        partialUpdatedBeheerobject.setId(beheerobject.getId());

        partialUpdatedBeheerobject
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .begingarantieperiode(UPDATED_BEGINGARANTIEPERIODE)
            .beheergebied(UPDATED_BEHEERGEBIED)
            .beheerobjectbeheervak(UPDATED_BEHEEROBJECTBEHEERVAK)
            .beheerobjectgebruiksfunctie(UPDATED_BEHEEROBJECTGEBRUIKSFUNCTIE)
            .beheerobjectmemo(UPDATED_BEHEEROBJECTMEMO)
            .beschermdefloraenfauna(UPDATED_BESCHERMDEFLORAENFAUNA)
            .buurt(UPDATED_BUURT)
            .conversieid(UPDATED_CONVERSIEID)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .datumoplevering(UPDATED_DATUMOPLEVERING)
            .datumpublicatielv(UPDATED_DATUMPUBLICATIELV)
            .datumverwijdering(UPDATED_DATUMVERWIJDERING)
            .eindegarantieperiode(UPDATED_EINDEGARANTIEPERIODE)
            .gebiedstype(UPDATED_GEBIEDSTYPE)
            .gemeente(UPDATED_GEMEENTE)
            .geometrie(UPDATED_GEOMETRIE)
            .gewijzigddoor(UPDATED_GEWIJZIGDDOOR)
            .grondsoort(UPDATED_GRONDSOORT)
            .grondsoortplus(UPDATED_GRONDSOORTPLUS)
            .identificatieimbor(UPDATED_IDENTIFICATIEIMBOR)
            .identificatieimgeo(UPDATED_IDENTIFICATIEIMGEO)
            .jaarvanaanleg(UPDATED_JAARVANAANLEG)
            .eobjectbegintijd(UPDATED_EOBJECTBEGINTIJD)
            .eobjecteindtijd(UPDATED_EOBJECTEINDTIJD)
            .onderhoudsplichtige(UPDATED_ONDERHOUDSPLICHTIGE)
            .openbareruimte(UPDATED_OPENBARERUIMTE)
            .postcode(UPDATED_POSTCODE)
            .relatievehoogteligging(UPDATED_RELATIEVEHOOGTELIGGING)
            .stadsdeel(UPDATED_STADSDEEL)
            .status(UPDATED_STATUS)
            .theoretischeindejaar(UPDATED_THEORETISCHEINDEJAAR)
            .tijdstipregistratie(UPDATED_TIJDSTIPREGISTRATIE)
            .typebeheerder(UPDATED_TYPEBEHEERDER)
            .typebeheerderplus(UPDATED_TYPEBEHEERDERPLUS)
            .typeeigenaar(UPDATED_TYPEEIGENAAR)
            .typeeigenaarplus(UPDATED_TYPEEIGENAARPLUS)
            .typeligging(UPDATED_TYPELIGGING)
            .waterschap(UPDATED_WATERSCHAP)
            .wijk(UPDATED_WIJK)
            .woonplaats(UPDATED_WOONPLAATS)
            .zettingsgevoeligheid(UPDATED_ZETTINGSGEVOELIGHEID)
            .zettingsgevoeligheidplus(UPDATED_ZETTINGSGEVOELIGHEIDPLUS);

        restBeheerobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeheerobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeheerobject))
            )
            .andExpect(status().isOk());

        // Validate the Beheerobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeheerobjectUpdatableFieldsEquals(partialUpdatedBeheerobject, getPersistedBeheerobject(partialUpdatedBeheerobject));
    }

    @Test
    @Transactional
    void patchNonExistingBeheerobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beheerobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeheerobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, beheerobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beheerobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beheerobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBeheerobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beheerobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeheerobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beheerobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beheerobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBeheerobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beheerobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeheerobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(beheerobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beheerobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBeheerobject() throws Exception {
        // Initialize the database
        beheerobjectRepository.saveAndFlush(beheerobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the beheerobject
        restBeheerobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, beheerobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return beheerobjectRepository.count();
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

    protected Beheerobject getPersistedBeheerobject(Beheerobject beheerobject) {
        return beheerobjectRepository.findById(beheerobject.getId()).orElseThrow();
    }

    protected void assertPersistedBeheerobjectToMatchAllProperties(Beheerobject expectedBeheerobject) {
        assertBeheerobjectAllPropertiesEquals(expectedBeheerobject, getPersistedBeheerobject(expectedBeheerobject));
    }

    protected void assertPersistedBeheerobjectToMatchUpdatableProperties(Beheerobject expectedBeheerobject) {
        assertBeheerobjectAllUpdatablePropertiesEquals(expectedBeheerobject, getPersistedBeheerobject(expectedBeheerobject));
    }
}
