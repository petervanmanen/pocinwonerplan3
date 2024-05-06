package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BoomAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Boom;
import nl.ritense.demo.repository.BoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BoomResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BoomResourceIT {

    private static final String DEFAULT_BELEIDSSTATUS = "AAAAAAAAAA";
    private static final String UPDATED_BELEIDSSTATUS = "BBBBBBBBBB";

    private static final String DEFAULT_BEOOGDEOMLOOPTIJD = "AAAAAAAAAA";
    private static final String UPDATED_BEOOGDEOMLOOPTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_BOOMBEELD = "AAAAAAAAAA";
    private static final String UPDATED_BOOMBEELD = "BBBBBBBBBB";

    private static final String DEFAULT_BOOMBESCHERMER = "AAAAAAAAAA";
    private static final String UPDATED_BOOMBESCHERMER = "BBBBBBBBBB";

    private static final String DEFAULT_BOOMGROEP = "AAAAAAAAAA";
    private static final String UPDATED_BOOMGROEP = "BBBBBBBBBB";

    private static final String DEFAULT_BOOMHOOGTEACTUEEL = "AAAAAAAAAA";
    private static final String UPDATED_BOOMHOOGTEACTUEEL = "BBBBBBBBBB";

    private static final String DEFAULT_BOOMHOOGTEKLASSEACTUEEL = "AAAAAAAAAA";
    private static final String UPDATED_BOOMHOOGTEKLASSEACTUEEL = "BBBBBBBBBB";

    private static final String DEFAULT_BOOMHOOGTEKLASSEEINDEBEELD = "AAAAAAAAAA";
    private static final String UPDATED_BOOMHOOGTEKLASSEEINDEBEELD = "BBBBBBBBBB";

    private static final String DEFAULT_BOOMSPIEGEL = "AAAAAAAAAA";
    private static final String UPDATED_BOOMSPIEGEL = "BBBBBBBBBB";

    private static final String DEFAULT_BOOMTYPEBESCHERMINGSSTATUSPLUS = "AAAAAAAAAA";
    private static final String UPDATED_BOOMTYPEBESCHERMINGSSTATUSPLUS = "BBBBBBBBBB";

    private static final String DEFAULT_BOOMVOORZIENING = "AAAAAAAAAA";
    private static final String UPDATED_BOOMVOORZIENING = "BBBBBBBBBB";

    private static final String DEFAULT_CONTROLEFREQUENTIE = "AAAAAAAAAA";
    private static final String UPDATED_CONTROLEFREQUENTIE = "BBBBBBBBBB";

    private static final String DEFAULT_FEESTVERLICHTING = "AAAAAAAAAA";
    private static final String UPDATED_FEESTVERLICHTING = "BBBBBBBBBB";

    private static final String DEFAULT_GROEIFASE = "AAAAAAAAAA";
    private static final String UPDATED_GROEIFASE = "BBBBBBBBBB";

    private static final String DEFAULT_GROEIPLAATSINRICHTING = "AAAAAAAAAA";
    private static final String UPDATED_GROEIPLAATSINRICHTING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HERPLANTPLICHT = false;
    private static final Boolean UPDATED_HERPLANTPLICHT = true;

    private static final String DEFAULT_KIEMJAAR = "AAAAAAAAAA";
    private static final String UPDATED_KIEMJAAR = "BBBBBBBBBB";

    private static final String DEFAULT_KROONDIAMETERKLASSEACTUEEL = "AAAAAAAAAA";
    private static final String UPDATED_KROONDIAMETERKLASSEACTUEEL = "BBBBBBBBBB";

    private static final String DEFAULT_KROONDIAMETERKLASSEEINDEBEELD = "AAAAAAAAAA";
    private static final String UPDATED_KROONDIAMETERKLASSEEINDEBEELD = "BBBBBBBBBB";

    private static final String DEFAULT_KROONVOLUME = "AAAAAAAAAA";
    private static final String UPDATED_KROONVOLUME = "BBBBBBBBBB";

    private static final String DEFAULT_LEEFTIJD = "AAAAAAAAAA";
    private static final String UPDATED_LEEFTIJD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MEERSTAMMIG = false;
    private static final Boolean UPDATED_MEERSTAMMIG = true;

    private static final String DEFAULT_MONETAIREBOOMWAARDE = "AAAAAAAAAA";
    private static final String UPDATED_MONETAIREBOOMWAARDE = "BBBBBBBBBB";

    private static final String DEFAULT_SNOEIFASE = "AAAAAAAAAA";
    private static final String UPDATED_SNOEIFASE = "BBBBBBBBBB";

    private static final String DEFAULT_STAMDIAMETER = "AAAAAAAAAA";
    private static final String UPDATED_STAMDIAMETER = "BBBBBBBBBB";

    private static final String DEFAULT_STAMDIAMETERKLASSE = "AAAAAAAAAA";
    private static final String UPDATED_STAMDIAMETERKLASSE = "BBBBBBBBBB";

    private static final String DEFAULT_TAKVRIJERUIMTETOTGEBOUW = "AAAAAAAAAA";
    private static final String UPDATED_TAKVRIJERUIMTETOTGEBOUW = "BBBBBBBBBB";

    private static final String DEFAULT_TAKVRIJESTAM = "AAAAAAAAAA";
    private static final String UPDATED_TAKVRIJESTAM = "BBBBBBBBBB";

    private static final String DEFAULT_TAKVRIJEZONEPRIMAIR = "AAAAAAAAAA";
    private static final String UPDATED_TAKVRIJEZONEPRIMAIR = "BBBBBBBBBB";

    private static final String DEFAULT_TAKVRIJEZONESECUNDAIR = "AAAAAAAAAA";
    private static final String UPDATED_TAKVRIJEZONESECUNDAIR = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSPONDER = "AAAAAAAAAA";
    private static final String UPDATED_TRANSPONDER = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEBESCHERMINGSSTATUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPEBESCHERMINGSSTATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEOMGEVINGSRISICOKLASSE = "AAAAAAAAAA";
    private static final String UPDATED_TYPEOMGEVINGSRISICOKLASSE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEVERMEERDERINGSVORM = "AAAAAAAAAA";
    private static final String UPDATED_TYPEVERMEERDERINGSVORM = "BBBBBBBBBB";

    private static final String DEFAULT_VEILIGHEIDSKLASSEBOOM = "AAAAAAAAAA";
    private static final String UPDATED_VEILIGHEIDSKLASSEBOOM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VERPLANT = false;
    private static final Boolean UPDATED_VERPLANT = true;

    private static final Boolean DEFAULT_VERPLANTBAAR = false;
    private static final Boolean UPDATED_VERPLANTBAAR = true;

    private static final String DEFAULT_VRIJEDOORRIJHOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_VRIJEDOORRIJHOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_VRIJEDOORRIJHOOGTEPRIMAIR = "AAAAAAAAAA";
    private static final String UPDATED_VRIJEDOORRIJHOOGTEPRIMAIR = "BBBBBBBBBB";

    private static final String DEFAULT_VRIJEDOORRIJHOOGTESECUNDAIR = "AAAAAAAAAA";
    private static final String UPDATED_VRIJEDOORRIJHOOGTESECUNDAIR = "BBBBBBBBBB";

    private static final String DEFAULT_VRIJETAKVAL = "AAAAAAAAAA";
    private static final String UPDATED_VRIJETAKVAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/booms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BoomRepository boomRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBoomMockMvc;

    private Boom boom;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Boom createEntity(EntityManager em) {
        Boom boom = new Boom()
            .beleidsstatus(DEFAULT_BELEIDSSTATUS)
            .beoogdeomlooptijd(DEFAULT_BEOOGDEOMLOOPTIJD)
            .boombeeld(DEFAULT_BOOMBEELD)
            .boombeschermer(DEFAULT_BOOMBESCHERMER)
            .boomgroep(DEFAULT_BOOMGROEP)
            .boomhoogteactueel(DEFAULT_BOOMHOOGTEACTUEEL)
            .boomhoogteklasseactueel(DEFAULT_BOOMHOOGTEKLASSEACTUEEL)
            .boomhoogteklasseeindebeeld(DEFAULT_BOOMHOOGTEKLASSEEINDEBEELD)
            .boomspiegel(DEFAULT_BOOMSPIEGEL)
            .boomtypebeschermingsstatusplus(DEFAULT_BOOMTYPEBESCHERMINGSSTATUSPLUS)
            .boomvoorziening(DEFAULT_BOOMVOORZIENING)
            .controlefrequentie(DEFAULT_CONTROLEFREQUENTIE)
            .feestverlichting(DEFAULT_FEESTVERLICHTING)
            .groeifase(DEFAULT_GROEIFASE)
            .groeiplaatsinrichting(DEFAULT_GROEIPLAATSINRICHTING)
            .herplantplicht(DEFAULT_HERPLANTPLICHT)
            .kiemjaar(DEFAULT_KIEMJAAR)
            .kroondiameterklasseactueel(DEFAULT_KROONDIAMETERKLASSEACTUEEL)
            .kroondiameterklasseeindebeeld(DEFAULT_KROONDIAMETERKLASSEEINDEBEELD)
            .kroonvolume(DEFAULT_KROONVOLUME)
            .leeftijd(DEFAULT_LEEFTIJD)
            .meerstammig(DEFAULT_MEERSTAMMIG)
            .monetaireboomwaarde(DEFAULT_MONETAIREBOOMWAARDE)
            .snoeifase(DEFAULT_SNOEIFASE)
            .stamdiameter(DEFAULT_STAMDIAMETER)
            .stamdiameterklasse(DEFAULT_STAMDIAMETERKLASSE)
            .takvrijeruimtetotgebouw(DEFAULT_TAKVRIJERUIMTETOTGEBOUW)
            .takvrijestam(DEFAULT_TAKVRIJESTAM)
            .takvrijezoneprimair(DEFAULT_TAKVRIJEZONEPRIMAIR)
            .takvrijezonesecundair(DEFAULT_TAKVRIJEZONESECUNDAIR)
            .transponder(DEFAULT_TRANSPONDER)
            .type(DEFAULT_TYPE)
            .typebeschermingsstatus(DEFAULT_TYPEBESCHERMINGSSTATUS)
            .typeomgevingsrisicoklasse(DEFAULT_TYPEOMGEVINGSRISICOKLASSE)
            .typeplus(DEFAULT_TYPEPLUS)
            .typevermeerderingsvorm(DEFAULT_TYPEVERMEERDERINGSVORM)
            .veiligheidsklasseboom(DEFAULT_VEILIGHEIDSKLASSEBOOM)
            .verplant(DEFAULT_VERPLANT)
            .verplantbaar(DEFAULT_VERPLANTBAAR)
            .vrijedoorrijhoogte(DEFAULT_VRIJEDOORRIJHOOGTE)
            .vrijedoorrijhoogteprimair(DEFAULT_VRIJEDOORRIJHOOGTEPRIMAIR)
            .vrijedoorrijhoogtesecundair(DEFAULT_VRIJEDOORRIJHOOGTESECUNDAIR)
            .vrijetakval(DEFAULT_VRIJETAKVAL);
        return boom;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Boom createUpdatedEntity(EntityManager em) {
        Boom boom = new Boom()
            .beleidsstatus(UPDATED_BELEIDSSTATUS)
            .beoogdeomlooptijd(UPDATED_BEOOGDEOMLOOPTIJD)
            .boombeeld(UPDATED_BOOMBEELD)
            .boombeschermer(UPDATED_BOOMBESCHERMER)
            .boomgroep(UPDATED_BOOMGROEP)
            .boomhoogteactueel(UPDATED_BOOMHOOGTEACTUEEL)
            .boomhoogteklasseactueel(UPDATED_BOOMHOOGTEKLASSEACTUEEL)
            .boomhoogteklasseeindebeeld(UPDATED_BOOMHOOGTEKLASSEEINDEBEELD)
            .boomspiegel(UPDATED_BOOMSPIEGEL)
            .boomtypebeschermingsstatusplus(UPDATED_BOOMTYPEBESCHERMINGSSTATUSPLUS)
            .boomvoorziening(UPDATED_BOOMVOORZIENING)
            .controlefrequentie(UPDATED_CONTROLEFREQUENTIE)
            .feestverlichting(UPDATED_FEESTVERLICHTING)
            .groeifase(UPDATED_GROEIFASE)
            .groeiplaatsinrichting(UPDATED_GROEIPLAATSINRICHTING)
            .herplantplicht(UPDATED_HERPLANTPLICHT)
            .kiemjaar(UPDATED_KIEMJAAR)
            .kroondiameterklasseactueel(UPDATED_KROONDIAMETERKLASSEACTUEEL)
            .kroondiameterklasseeindebeeld(UPDATED_KROONDIAMETERKLASSEEINDEBEELD)
            .kroonvolume(UPDATED_KROONVOLUME)
            .leeftijd(UPDATED_LEEFTIJD)
            .meerstammig(UPDATED_MEERSTAMMIG)
            .monetaireboomwaarde(UPDATED_MONETAIREBOOMWAARDE)
            .snoeifase(UPDATED_SNOEIFASE)
            .stamdiameter(UPDATED_STAMDIAMETER)
            .stamdiameterklasse(UPDATED_STAMDIAMETERKLASSE)
            .takvrijeruimtetotgebouw(UPDATED_TAKVRIJERUIMTETOTGEBOUW)
            .takvrijestam(UPDATED_TAKVRIJESTAM)
            .takvrijezoneprimair(UPDATED_TAKVRIJEZONEPRIMAIR)
            .takvrijezonesecundair(UPDATED_TAKVRIJEZONESECUNDAIR)
            .transponder(UPDATED_TRANSPONDER)
            .type(UPDATED_TYPE)
            .typebeschermingsstatus(UPDATED_TYPEBESCHERMINGSSTATUS)
            .typeomgevingsrisicoklasse(UPDATED_TYPEOMGEVINGSRISICOKLASSE)
            .typeplus(UPDATED_TYPEPLUS)
            .typevermeerderingsvorm(UPDATED_TYPEVERMEERDERINGSVORM)
            .veiligheidsklasseboom(UPDATED_VEILIGHEIDSKLASSEBOOM)
            .verplant(UPDATED_VERPLANT)
            .verplantbaar(UPDATED_VERPLANTBAAR)
            .vrijedoorrijhoogte(UPDATED_VRIJEDOORRIJHOOGTE)
            .vrijedoorrijhoogteprimair(UPDATED_VRIJEDOORRIJHOOGTEPRIMAIR)
            .vrijedoorrijhoogtesecundair(UPDATED_VRIJEDOORRIJHOOGTESECUNDAIR)
            .vrijetakval(UPDATED_VRIJETAKVAL);
        return boom;
    }

    @BeforeEach
    public void initTest() {
        boom = createEntity(em);
    }

    @Test
    @Transactional
    void createBoom() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Boom
        var returnedBoom = om.readValue(
            restBoomMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boom)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Boom.class
        );

        // Validate the Boom in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBoomUpdatableFieldsEquals(returnedBoom, getPersistedBoom(returnedBoom));
    }

    @Test
    @Transactional
    void createBoomWithExistingId() throws Exception {
        // Create the Boom with an existing ID
        boom.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boom)))
            .andExpect(status().isBadRequest());

        // Validate the Boom in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBooms() throws Exception {
        // Initialize the database
        boomRepository.saveAndFlush(boom);

        // Get all the boomList
        restBoomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boom.getId().intValue())))
            .andExpect(jsonPath("$.[*].beleidsstatus").value(hasItem(DEFAULT_BELEIDSSTATUS)))
            .andExpect(jsonPath("$.[*].beoogdeomlooptijd").value(hasItem(DEFAULT_BEOOGDEOMLOOPTIJD)))
            .andExpect(jsonPath("$.[*].boombeeld").value(hasItem(DEFAULT_BOOMBEELD)))
            .andExpect(jsonPath("$.[*].boombeschermer").value(hasItem(DEFAULT_BOOMBESCHERMER)))
            .andExpect(jsonPath("$.[*].boomgroep").value(hasItem(DEFAULT_BOOMGROEP)))
            .andExpect(jsonPath("$.[*].boomhoogteactueel").value(hasItem(DEFAULT_BOOMHOOGTEACTUEEL)))
            .andExpect(jsonPath("$.[*].boomhoogteklasseactueel").value(hasItem(DEFAULT_BOOMHOOGTEKLASSEACTUEEL)))
            .andExpect(jsonPath("$.[*].boomhoogteklasseeindebeeld").value(hasItem(DEFAULT_BOOMHOOGTEKLASSEEINDEBEELD)))
            .andExpect(jsonPath("$.[*].boomspiegel").value(hasItem(DEFAULT_BOOMSPIEGEL)))
            .andExpect(jsonPath("$.[*].boomtypebeschermingsstatusplus").value(hasItem(DEFAULT_BOOMTYPEBESCHERMINGSSTATUSPLUS)))
            .andExpect(jsonPath("$.[*].boomvoorziening").value(hasItem(DEFAULT_BOOMVOORZIENING)))
            .andExpect(jsonPath("$.[*].controlefrequentie").value(hasItem(DEFAULT_CONTROLEFREQUENTIE)))
            .andExpect(jsonPath("$.[*].feestverlichting").value(hasItem(DEFAULT_FEESTVERLICHTING)))
            .andExpect(jsonPath("$.[*].groeifase").value(hasItem(DEFAULT_GROEIFASE)))
            .andExpect(jsonPath("$.[*].groeiplaatsinrichting").value(hasItem(DEFAULT_GROEIPLAATSINRICHTING)))
            .andExpect(jsonPath("$.[*].herplantplicht").value(hasItem(DEFAULT_HERPLANTPLICHT.booleanValue())))
            .andExpect(jsonPath("$.[*].kiemjaar").value(hasItem(DEFAULT_KIEMJAAR)))
            .andExpect(jsonPath("$.[*].kroondiameterklasseactueel").value(hasItem(DEFAULT_KROONDIAMETERKLASSEACTUEEL)))
            .andExpect(jsonPath("$.[*].kroondiameterklasseeindebeeld").value(hasItem(DEFAULT_KROONDIAMETERKLASSEEINDEBEELD)))
            .andExpect(jsonPath("$.[*].kroonvolume").value(hasItem(DEFAULT_KROONVOLUME)))
            .andExpect(jsonPath("$.[*].leeftijd").value(hasItem(DEFAULT_LEEFTIJD)))
            .andExpect(jsonPath("$.[*].meerstammig").value(hasItem(DEFAULT_MEERSTAMMIG.booleanValue())))
            .andExpect(jsonPath("$.[*].monetaireboomwaarde").value(hasItem(DEFAULT_MONETAIREBOOMWAARDE)))
            .andExpect(jsonPath("$.[*].snoeifase").value(hasItem(DEFAULT_SNOEIFASE)))
            .andExpect(jsonPath("$.[*].stamdiameter").value(hasItem(DEFAULT_STAMDIAMETER)))
            .andExpect(jsonPath("$.[*].stamdiameterklasse").value(hasItem(DEFAULT_STAMDIAMETERKLASSE)))
            .andExpect(jsonPath("$.[*].takvrijeruimtetotgebouw").value(hasItem(DEFAULT_TAKVRIJERUIMTETOTGEBOUW)))
            .andExpect(jsonPath("$.[*].takvrijestam").value(hasItem(DEFAULT_TAKVRIJESTAM)))
            .andExpect(jsonPath("$.[*].takvrijezoneprimair").value(hasItem(DEFAULT_TAKVRIJEZONEPRIMAIR)))
            .andExpect(jsonPath("$.[*].takvrijezonesecundair").value(hasItem(DEFAULT_TAKVRIJEZONESECUNDAIR)))
            .andExpect(jsonPath("$.[*].transponder").value(hasItem(DEFAULT_TRANSPONDER)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].typebeschermingsstatus").value(hasItem(DEFAULT_TYPEBESCHERMINGSSTATUS)))
            .andExpect(jsonPath("$.[*].typeomgevingsrisicoklasse").value(hasItem(DEFAULT_TYPEOMGEVINGSRISICOKLASSE)))
            .andExpect(jsonPath("$.[*].typeplus").value(hasItem(DEFAULT_TYPEPLUS)))
            .andExpect(jsonPath("$.[*].typevermeerderingsvorm").value(hasItem(DEFAULT_TYPEVERMEERDERINGSVORM)))
            .andExpect(jsonPath("$.[*].veiligheidsklasseboom").value(hasItem(DEFAULT_VEILIGHEIDSKLASSEBOOM)))
            .andExpect(jsonPath("$.[*].verplant").value(hasItem(DEFAULT_VERPLANT.booleanValue())))
            .andExpect(jsonPath("$.[*].verplantbaar").value(hasItem(DEFAULT_VERPLANTBAAR.booleanValue())))
            .andExpect(jsonPath("$.[*].vrijedoorrijhoogte").value(hasItem(DEFAULT_VRIJEDOORRIJHOOGTE)))
            .andExpect(jsonPath("$.[*].vrijedoorrijhoogteprimair").value(hasItem(DEFAULT_VRIJEDOORRIJHOOGTEPRIMAIR)))
            .andExpect(jsonPath("$.[*].vrijedoorrijhoogtesecundair").value(hasItem(DEFAULT_VRIJEDOORRIJHOOGTESECUNDAIR)))
            .andExpect(jsonPath("$.[*].vrijetakval").value(hasItem(DEFAULT_VRIJETAKVAL)));
    }

    @Test
    @Transactional
    void getBoom() throws Exception {
        // Initialize the database
        boomRepository.saveAndFlush(boom);

        // Get the boom
        restBoomMockMvc
            .perform(get(ENTITY_API_URL_ID, boom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(boom.getId().intValue()))
            .andExpect(jsonPath("$.beleidsstatus").value(DEFAULT_BELEIDSSTATUS))
            .andExpect(jsonPath("$.beoogdeomlooptijd").value(DEFAULT_BEOOGDEOMLOOPTIJD))
            .andExpect(jsonPath("$.boombeeld").value(DEFAULT_BOOMBEELD))
            .andExpect(jsonPath("$.boombeschermer").value(DEFAULT_BOOMBESCHERMER))
            .andExpect(jsonPath("$.boomgroep").value(DEFAULT_BOOMGROEP))
            .andExpect(jsonPath("$.boomhoogteactueel").value(DEFAULT_BOOMHOOGTEACTUEEL))
            .andExpect(jsonPath("$.boomhoogteklasseactueel").value(DEFAULT_BOOMHOOGTEKLASSEACTUEEL))
            .andExpect(jsonPath("$.boomhoogteklasseeindebeeld").value(DEFAULT_BOOMHOOGTEKLASSEEINDEBEELD))
            .andExpect(jsonPath("$.boomspiegel").value(DEFAULT_BOOMSPIEGEL))
            .andExpect(jsonPath("$.boomtypebeschermingsstatusplus").value(DEFAULT_BOOMTYPEBESCHERMINGSSTATUSPLUS))
            .andExpect(jsonPath("$.boomvoorziening").value(DEFAULT_BOOMVOORZIENING))
            .andExpect(jsonPath("$.controlefrequentie").value(DEFAULT_CONTROLEFREQUENTIE))
            .andExpect(jsonPath("$.feestverlichting").value(DEFAULT_FEESTVERLICHTING))
            .andExpect(jsonPath("$.groeifase").value(DEFAULT_GROEIFASE))
            .andExpect(jsonPath("$.groeiplaatsinrichting").value(DEFAULT_GROEIPLAATSINRICHTING))
            .andExpect(jsonPath("$.herplantplicht").value(DEFAULT_HERPLANTPLICHT.booleanValue()))
            .andExpect(jsonPath("$.kiemjaar").value(DEFAULT_KIEMJAAR))
            .andExpect(jsonPath("$.kroondiameterklasseactueel").value(DEFAULT_KROONDIAMETERKLASSEACTUEEL))
            .andExpect(jsonPath("$.kroondiameterklasseeindebeeld").value(DEFAULT_KROONDIAMETERKLASSEEINDEBEELD))
            .andExpect(jsonPath("$.kroonvolume").value(DEFAULT_KROONVOLUME))
            .andExpect(jsonPath("$.leeftijd").value(DEFAULT_LEEFTIJD))
            .andExpect(jsonPath("$.meerstammig").value(DEFAULT_MEERSTAMMIG.booleanValue()))
            .andExpect(jsonPath("$.monetaireboomwaarde").value(DEFAULT_MONETAIREBOOMWAARDE))
            .andExpect(jsonPath("$.snoeifase").value(DEFAULT_SNOEIFASE))
            .andExpect(jsonPath("$.stamdiameter").value(DEFAULT_STAMDIAMETER))
            .andExpect(jsonPath("$.stamdiameterklasse").value(DEFAULT_STAMDIAMETERKLASSE))
            .andExpect(jsonPath("$.takvrijeruimtetotgebouw").value(DEFAULT_TAKVRIJERUIMTETOTGEBOUW))
            .andExpect(jsonPath("$.takvrijestam").value(DEFAULT_TAKVRIJESTAM))
            .andExpect(jsonPath("$.takvrijezoneprimair").value(DEFAULT_TAKVRIJEZONEPRIMAIR))
            .andExpect(jsonPath("$.takvrijezonesecundair").value(DEFAULT_TAKVRIJEZONESECUNDAIR))
            .andExpect(jsonPath("$.transponder").value(DEFAULT_TRANSPONDER))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.typebeschermingsstatus").value(DEFAULT_TYPEBESCHERMINGSSTATUS))
            .andExpect(jsonPath("$.typeomgevingsrisicoklasse").value(DEFAULT_TYPEOMGEVINGSRISICOKLASSE))
            .andExpect(jsonPath("$.typeplus").value(DEFAULT_TYPEPLUS))
            .andExpect(jsonPath("$.typevermeerderingsvorm").value(DEFAULT_TYPEVERMEERDERINGSVORM))
            .andExpect(jsonPath("$.veiligheidsklasseboom").value(DEFAULT_VEILIGHEIDSKLASSEBOOM))
            .andExpect(jsonPath("$.verplant").value(DEFAULT_VERPLANT.booleanValue()))
            .andExpect(jsonPath("$.verplantbaar").value(DEFAULT_VERPLANTBAAR.booleanValue()))
            .andExpect(jsonPath("$.vrijedoorrijhoogte").value(DEFAULT_VRIJEDOORRIJHOOGTE))
            .andExpect(jsonPath("$.vrijedoorrijhoogteprimair").value(DEFAULT_VRIJEDOORRIJHOOGTEPRIMAIR))
            .andExpect(jsonPath("$.vrijedoorrijhoogtesecundair").value(DEFAULT_VRIJEDOORRIJHOOGTESECUNDAIR))
            .andExpect(jsonPath("$.vrijetakval").value(DEFAULT_VRIJETAKVAL));
    }

    @Test
    @Transactional
    void getNonExistingBoom() throws Exception {
        // Get the boom
        restBoomMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBoom() throws Exception {
        // Initialize the database
        boomRepository.saveAndFlush(boom);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the boom
        Boom updatedBoom = boomRepository.findById(boom.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBoom are not directly saved in db
        em.detach(updatedBoom);
        updatedBoom
            .beleidsstatus(UPDATED_BELEIDSSTATUS)
            .beoogdeomlooptijd(UPDATED_BEOOGDEOMLOOPTIJD)
            .boombeeld(UPDATED_BOOMBEELD)
            .boombeschermer(UPDATED_BOOMBESCHERMER)
            .boomgroep(UPDATED_BOOMGROEP)
            .boomhoogteactueel(UPDATED_BOOMHOOGTEACTUEEL)
            .boomhoogteklasseactueel(UPDATED_BOOMHOOGTEKLASSEACTUEEL)
            .boomhoogteklasseeindebeeld(UPDATED_BOOMHOOGTEKLASSEEINDEBEELD)
            .boomspiegel(UPDATED_BOOMSPIEGEL)
            .boomtypebeschermingsstatusplus(UPDATED_BOOMTYPEBESCHERMINGSSTATUSPLUS)
            .boomvoorziening(UPDATED_BOOMVOORZIENING)
            .controlefrequentie(UPDATED_CONTROLEFREQUENTIE)
            .feestverlichting(UPDATED_FEESTVERLICHTING)
            .groeifase(UPDATED_GROEIFASE)
            .groeiplaatsinrichting(UPDATED_GROEIPLAATSINRICHTING)
            .herplantplicht(UPDATED_HERPLANTPLICHT)
            .kiemjaar(UPDATED_KIEMJAAR)
            .kroondiameterklasseactueel(UPDATED_KROONDIAMETERKLASSEACTUEEL)
            .kroondiameterklasseeindebeeld(UPDATED_KROONDIAMETERKLASSEEINDEBEELD)
            .kroonvolume(UPDATED_KROONVOLUME)
            .leeftijd(UPDATED_LEEFTIJD)
            .meerstammig(UPDATED_MEERSTAMMIG)
            .monetaireboomwaarde(UPDATED_MONETAIREBOOMWAARDE)
            .snoeifase(UPDATED_SNOEIFASE)
            .stamdiameter(UPDATED_STAMDIAMETER)
            .stamdiameterklasse(UPDATED_STAMDIAMETERKLASSE)
            .takvrijeruimtetotgebouw(UPDATED_TAKVRIJERUIMTETOTGEBOUW)
            .takvrijestam(UPDATED_TAKVRIJESTAM)
            .takvrijezoneprimair(UPDATED_TAKVRIJEZONEPRIMAIR)
            .takvrijezonesecundair(UPDATED_TAKVRIJEZONESECUNDAIR)
            .transponder(UPDATED_TRANSPONDER)
            .type(UPDATED_TYPE)
            .typebeschermingsstatus(UPDATED_TYPEBESCHERMINGSSTATUS)
            .typeomgevingsrisicoklasse(UPDATED_TYPEOMGEVINGSRISICOKLASSE)
            .typeplus(UPDATED_TYPEPLUS)
            .typevermeerderingsvorm(UPDATED_TYPEVERMEERDERINGSVORM)
            .veiligheidsklasseboom(UPDATED_VEILIGHEIDSKLASSEBOOM)
            .verplant(UPDATED_VERPLANT)
            .verplantbaar(UPDATED_VERPLANTBAAR)
            .vrijedoorrijhoogte(UPDATED_VRIJEDOORRIJHOOGTE)
            .vrijedoorrijhoogteprimair(UPDATED_VRIJEDOORRIJHOOGTEPRIMAIR)
            .vrijedoorrijhoogtesecundair(UPDATED_VRIJEDOORRIJHOOGTESECUNDAIR)
            .vrijetakval(UPDATED_VRIJETAKVAL);

        restBoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBoom.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBoom))
            )
            .andExpect(status().isOk());

        // Validate the Boom in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBoomToMatchAllProperties(updatedBoom);
    }

    @Test
    @Transactional
    void putNonExistingBoom() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boom.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoomMockMvc
            .perform(put(ENTITY_API_URL_ID, boom.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boom)))
            .andExpect(status().isBadRequest());

        // Validate the Boom in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBoom() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boom.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(boom))
            )
            .andExpect(status().isBadRequest());

        // Validate the Boom in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBoom() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boom.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoomMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boom)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Boom in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBoomWithPatch() throws Exception {
        // Initialize the database
        boomRepository.saveAndFlush(boom);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the boom using partial update
        Boom partialUpdatedBoom = new Boom();
        partialUpdatedBoom.setId(boom.getId());

        partialUpdatedBoom
            .boombeeld(UPDATED_BOOMBEELD)
            .boomtypebeschermingsstatusplus(UPDATED_BOOMTYPEBESCHERMINGSSTATUSPLUS)
            .groeifase(UPDATED_GROEIFASE)
            .herplantplicht(UPDATED_HERPLANTPLICHT)
            .kroondiameterklasseactueel(UPDATED_KROONDIAMETERKLASSEACTUEEL)
            .kroonvolume(UPDATED_KROONVOLUME)
            .monetaireboomwaarde(UPDATED_MONETAIREBOOMWAARDE)
            .stamdiameter(UPDATED_STAMDIAMETER)
            .stamdiameterklasse(UPDATED_STAMDIAMETERKLASSE)
            .takvrijestam(UPDATED_TAKVRIJESTAM)
            .type(UPDATED_TYPE)
            .typebeschermingsstatus(UPDATED_TYPEBESCHERMINGSSTATUS)
            .typeomgevingsrisicoklasse(UPDATED_TYPEOMGEVINGSRISICOKLASSE)
            .typeplus(UPDATED_TYPEPLUS)
            .veiligheidsklasseboom(UPDATED_VEILIGHEIDSKLASSEBOOM)
            .verplant(UPDATED_VERPLANT)
            .verplantbaar(UPDATED_VERPLANTBAAR)
            .vrijetakval(UPDATED_VRIJETAKVAL);

        restBoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBoom.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBoom))
            )
            .andExpect(status().isOk());

        // Validate the Boom in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBoomUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBoom, boom), getPersistedBoom(boom));
    }

    @Test
    @Transactional
    void fullUpdateBoomWithPatch() throws Exception {
        // Initialize the database
        boomRepository.saveAndFlush(boom);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the boom using partial update
        Boom partialUpdatedBoom = new Boom();
        partialUpdatedBoom.setId(boom.getId());

        partialUpdatedBoom
            .beleidsstatus(UPDATED_BELEIDSSTATUS)
            .beoogdeomlooptijd(UPDATED_BEOOGDEOMLOOPTIJD)
            .boombeeld(UPDATED_BOOMBEELD)
            .boombeschermer(UPDATED_BOOMBESCHERMER)
            .boomgroep(UPDATED_BOOMGROEP)
            .boomhoogteactueel(UPDATED_BOOMHOOGTEACTUEEL)
            .boomhoogteklasseactueel(UPDATED_BOOMHOOGTEKLASSEACTUEEL)
            .boomhoogteklasseeindebeeld(UPDATED_BOOMHOOGTEKLASSEEINDEBEELD)
            .boomspiegel(UPDATED_BOOMSPIEGEL)
            .boomtypebeschermingsstatusplus(UPDATED_BOOMTYPEBESCHERMINGSSTATUSPLUS)
            .boomvoorziening(UPDATED_BOOMVOORZIENING)
            .controlefrequentie(UPDATED_CONTROLEFREQUENTIE)
            .feestverlichting(UPDATED_FEESTVERLICHTING)
            .groeifase(UPDATED_GROEIFASE)
            .groeiplaatsinrichting(UPDATED_GROEIPLAATSINRICHTING)
            .herplantplicht(UPDATED_HERPLANTPLICHT)
            .kiemjaar(UPDATED_KIEMJAAR)
            .kroondiameterklasseactueel(UPDATED_KROONDIAMETERKLASSEACTUEEL)
            .kroondiameterklasseeindebeeld(UPDATED_KROONDIAMETERKLASSEEINDEBEELD)
            .kroonvolume(UPDATED_KROONVOLUME)
            .leeftijd(UPDATED_LEEFTIJD)
            .meerstammig(UPDATED_MEERSTAMMIG)
            .monetaireboomwaarde(UPDATED_MONETAIREBOOMWAARDE)
            .snoeifase(UPDATED_SNOEIFASE)
            .stamdiameter(UPDATED_STAMDIAMETER)
            .stamdiameterklasse(UPDATED_STAMDIAMETERKLASSE)
            .takvrijeruimtetotgebouw(UPDATED_TAKVRIJERUIMTETOTGEBOUW)
            .takvrijestam(UPDATED_TAKVRIJESTAM)
            .takvrijezoneprimair(UPDATED_TAKVRIJEZONEPRIMAIR)
            .takvrijezonesecundair(UPDATED_TAKVRIJEZONESECUNDAIR)
            .transponder(UPDATED_TRANSPONDER)
            .type(UPDATED_TYPE)
            .typebeschermingsstatus(UPDATED_TYPEBESCHERMINGSSTATUS)
            .typeomgevingsrisicoklasse(UPDATED_TYPEOMGEVINGSRISICOKLASSE)
            .typeplus(UPDATED_TYPEPLUS)
            .typevermeerderingsvorm(UPDATED_TYPEVERMEERDERINGSVORM)
            .veiligheidsklasseboom(UPDATED_VEILIGHEIDSKLASSEBOOM)
            .verplant(UPDATED_VERPLANT)
            .verplantbaar(UPDATED_VERPLANTBAAR)
            .vrijedoorrijhoogte(UPDATED_VRIJEDOORRIJHOOGTE)
            .vrijedoorrijhoogteprimair(UPDATED_VRIJEDOORRIJHOOGTEPRIMAIR)
            .vrijedoorrijhoogtesecundair(UPDATED_VRIJEDOORRIJHOOGTESECUNDAIR)
            .vrijetakval(UPDATED_VRIJETAKVAL);

        restBoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBoom.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBoom))
            )
            .andExpect(status().isOk());

        // Validate the Boom in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBoomUpdatableFieldsEquals(partialUpdatedBoom, getPersistedBoom(partialUpdatedBoom));
    }

    @Test
    @Transactional
    void patchNonExistingBoom() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boom.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoomMockMvc
            .perform(patch(ENTITY_API_URL_ID, boom.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(boom)))
            .andExpect(status().isBadRequest());

        // Validate the Boom in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBoom() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boom.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(boom))
            )
            .andExpect(status().isBadRequest());

        // Validate the Boom in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBoom() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boom.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoomMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(boom)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Boom in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBoom() throws Exception {
        // Initialize the database
        boomRepository.saveAndFlush(boom);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the boom
        restBoomMockMvc
            .perform(delete(ENTITY_API_URL_ID, boom.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return boomRepository.count();
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

    protected Boom getPersistedBoom(Boom boom) {
        return boomRepository.findById(boom.getId()).orElseThrow();
    }

    protected void assertPersistedBoomToMatchAllProperties(Boom expectedBoom) {
        assertBoomAllPropertiesEquals(expectedBoom, getPersistedBoom(expectedBoom));
    }

    protected void assertPersistedBoomToMatchUpdatableProperties(Boom expectedBoom) {
        assertBoomAllUpdatablePropertiesEquals(expectedBoom, getPersistedBoom(expectedBoom));
    }
}
