package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GroenobjectAsserts.*;
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
import nl.ritense.demo.domain.Groenobject;
import nl.ritense.demo.repository.GroenobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GroenobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GroenobjectResourceIT {

    private static final String DEFAULT_AANTALOBSTAKELS = "AAAAAAAAAA";
    private static final String UPDATED_AANTALOBSTAKELS = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALZIJDEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALZIJDEN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AFVOEREN = false;
    private static final Boolean UPDATED_AFVOEREN = true;

    private static final String DEFAULT_BEREIKBAARHEID = "AAAAAAAAAA";
    private static final String UPDATED_BEREIKBAARHEID = "BBBBBBBBBB";

    private static final String DEFAULT_BERGENDVERMOGEN = "AAAAAAAAAA";
    private static final String UPDATED_BERGENDVERMOGEN = "BBBBBBBBBB";

    private static final String DEFAULT_BEWERKINGSPERCENTAGE = "AAAAAAAAAA";
    private static final String UPDATED_BEWERKINGSPERCENTAGE = "BBBBBBBBBB";

    private static final String DEFAULT_BGTFYSIEKVOORKOMEN = "AAAAAAAAAA";
    private static final String UPDATED_BGTFYSIEKVOORKOMEN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BOLLEN = false;
    private static final Boolean UPDATED_BOLLEN = true;

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_BREEDTEKLASSEHAAG = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTEKLASSEHAAG = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BVC = false;
    private static final Boolean UPDATED_BVC = true;

    private static final String DEFAULT_CULTUURHISTORISCHWAARDEVOL = "AAAAAAAAAA";
    private static final String UPDATED_CULTUURHISTORISCHWAARDEVOL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DRAAGKRACHTIG = false;
    private static final Boolean UPDATED_DRAAGKRACHTIG = true;

    private static final Boolean DEFAULT_ECOLOGISCHBEHEER = false;
    private static final Boolean UPDATED_ECOLOGISCHBEHEER = true;

    private static final String DEFAULT_FYSIEKVOORKOMENIMGEO = "AAAAAAAAAA";
    private static final String UPDATED_FYSIEKVOORKOMENIMGEO = "BBBBBBBBBB";

    private static final String DEFAULT_GEWENSTSLUITINGSPERCENTAGE = "AAAAAAAAAA";
    private static final String UPDATED_GEWENSTSLUITINGSPERCENTAGE = "BBBBBBBBBB";

    private static final String DEFAULT_GROENOBJECTBEREIKBAARHEIDPLUS = "AAAAAAAAAA";
    private static final String UPDATED_GROENOBJECTBEREIKBAARHEIDPLUS = "BBBBBBBBBB";

    private static final String DEFAULT_GROENOBJECTCONSTRUCTIELAAG = "AAAAAAAAAA";
    private static final String UPDATED_GROENOBJECTCONSTRUCTIELAAG = "BBBBBBBBBB";

    private static final String DEFAULT_GROENOBJECTRAND = "AAAAAAAAAA";
    private static final String UPDATED_GROENOBJECTRAND = "BBBBBBBBBB";

    private static final String DEFAULT_GROENOBJECTSOORTNAAM = "AAAAAAAAAA";
    private static final String UPDATED_GROENOBJECTSOORTNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_HAAGVOETLENGTE = "AAAAAAAAAA";
    private static final String UPDATED_HAAGVOETLENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_HAAGVOETOPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_HAAGVOETOPPERVLAKTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HERPLANTPLICHT = false;
    private static final Boolean UPDATED_HERPLANTPLICHT = true;

    private static final String DEFAULT_HOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_HOOGTEKLASSEHAAG = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTEKLASSEHAAG = "BBBBBBBBBB";

    private static final String DEFAULT_KNIPFREQUENTIE = "AAAAAAAAAA";
    private static final String UPDATED_KNIPFREQUENTIE = "BBBBBBBBBB";

    private static final String DEFAULT_KNIPOPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_KNIPOPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_KWALITEITSNIVEAUACTUEEL = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEITSNIVEAUACTUEEL = "BBBBBBBBBB";

    private static final String DEFAULT_KWALITEITSNIVEAUGEWENST = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEITSNIVEAUGEWENST = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTE = "AAAAAAAAAA";
    private static final String UPDATED_LENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_LEVERANCIER = "AAAAAAAAAA";
    private static final String UPDATED_LEVERANCIER = "BBBBBBBBBB";

    private static final String DEFAULT_MAAIFREQUENTIE = "AAAAAAAAAA";
    private static final String UPDATED_MAAIFREQUENTIE = "BBBBBBBBBB";

    private static final String DEFAULT_MAXIMALEVALHOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_MAXIMALEVALHOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_EOBJECTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_EOBJECTNUMMER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_OBSTAKELS = false;
    private static final Boolean UPDATED_OBSTAKELS = true;

    private static final String DEFAULT_OMTREK = "AAAAAAAAAA";
    private static final String UPDATED_OMTREK = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERGROEI = "AAAAAAAAAA";
    private static final String UPDATED_ONDERGROEI = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_OPTALUD = "AAAAAAAAAA";
    private static final String UPDATED_OPTALUD = "BBBBBBBBBB";

    private static final String DEFAULT_TALUDSTEILTE = "AAAAAAAAAA";
    private static final String UPDATED_TALUDSTEILTE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEBEWERKING = "AAAAAAAAAA";
    private static final String UPDATED_TYPEBEWERKING = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEOMGEVINGSRISICOKLASSE = "AAAAAAAAAA";
    private static final String UPDATED_TYPEOMGEVINGSRISICOKLASSE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS_2 = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_VEILIGHEIDSKLASSEBOOM = "AAAAAAAAAA";
    private static final String UPDATED_VEILIGHEIDSKLASSEBOOM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/groenobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GroenobjectRepository groenobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroenobjectMockMvc;

    private Groenobject groenobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Groenobject createEntity(EntityManager em) {
        Groenobject groenobject = new Groenobject()
            .aantalobstakels(DEFAULT_AANTALOBSTAKELS)
            .aantalzijden(DEFAULT_AANTALZIJDEN)
            .afvoeren(DEFAULT_AFVOEREN)
            .bereikbaarheid(DEFAULT_BEREIKBAARHEID)
            .bergendvermogen(DEFAULT_BERGENDVERMOGEN)
            .bewerkingspercentage(DEFAULT_BEWERKINGSPERCENTAGE)
            .bgtfysiekvoorkomen(DEFAULT_BGTFYSIEKVOORKOMEN)
            .bollen(DEFAULT_BOLLEN)
            .breedte(DEFAULT_BREEDTE)
            .breedteklassehaag(DEFAULT_BREEDTEKLASSEHAAG)
            .bvc(DEFAULT_BVC)
            .cultuurhistorischwaardevol(DEFAULT_CULTUURHISTORISCHWAARDEVOL)
            .draagkrachtig(DEFAULT_DRAAGKRACHTIG)
            .ecologischbeheer(DEFAULT_ECOLOGISCHBEHEER)
            .fysiekvoorkomenimgeo(DEFAULT_FYSIEKVOORKOMENIMGEO)
            .gewenstsluitingspercentage(DEFAULT_GEWENSTSLUITINGSPERCENTAGE)
            .groenobjectbereikbaarheidplus(DEFAULT_GROENOBJECTBEREIKBAARHEIDPLUS)
            .groenobjectconstructielaag(DEFAULT_GROENOBJECTCONSTRUCTIELAAG)
            .groenobjectrand(DEFAULT_GROENOBJECTRAND)
            .groenobjectsoortnaam(DEFAULT_GROENOBJECTSOORTNAAM)
            .haagvoetlengte(DEFAULT_HAAGVOETLENGTE)
            .haagvoetoppervlakte(DEFAULT_HAAGVOETOPPERVLAKTE)
            .herplantplicht(DEFAULT_HERPLANTPLICHT)
            .hoogte(DEFAULT_HOOGTE)
            .hoogteklassehaag(DEFAULT_HOOGTEKLASSEHAAG)
            .knipfrequentie(DEFAULT_KNIPFREQUENTIE)
            .knipoppervlakte(DEFAULT_KNIPOPPERVLAKTE)
            .kwaliteitsniveauactueel(DEFAULT_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(DEFAULT_KWALITEITSNIVEAUGEWENST)
            .lengte(DEFAULT_LENGTE)
            .leverancier(DEFAULT_LEVERANCIER)
            .maaifrequentie(DEFAULT_MAAIFREQUENTIE)
            .maximalevalhoogte(DEFAULT_MAXIMALEVALHOOGTE)
            .eobjectnummer(DEFAULT_EOBJECTNUMMER)
            .obstakels(DEFAULT_OBSTAKELS)
            .omtrek(DEFAULT_OMTREK)
            .ondergroei(DEFAULT_ONDERGROEI)
            .oppervlakte(DEFAULT_OPPERVLAKTE)
            .optalud(DEFAULT_OPTALUD)
            .taludsteilte(DEFAULT_TALUDSTEILTE)
            .type(DEFAULT_TYPE)
            .typebewerking(DEFAULT_TYPEBEWERKING)
            .typeomgevingsrisicoklasse(DEFAULT_TYPEOMGEVINGSRISICOKLASSE)
            .typeplus(DEFAULT_TYPEPLUS)
            .typeplus2(DEFAULT_TYPEPLUS_2)
            .veiligheidsklasseboom(DEFAULT_VEILIGHEIDSKLASSEBOOM);
        return groenobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Groenobject createUpdatedEntity(EntityManager em) {
        Groenobject groenobject = new Groenobject()
            .aantalobstakels(UPDATED_AANTALOBSTAKELS)
            .aantalzijden(UPDATED_AANTALZIJDEN)
            .afvoeren(UPDATED_AFVOEREN)
            .bereikbaarheid(UPDATED_BEREIKBAARHEID)
            .bergendvermogen(UPDATED_BERGENDVERMOGEN)
            .bewerkingspercentage(UPDATED_BEWERKINGSPERCENTAGE)
            .bgtfysiekvoorkomen(UPDATED_BGTFYSIEKVOORKOMEN)
            .bollen(UPDATED_BOLLEN)
            .breedte(UPDATED_BREEDTE)
            .breedteklassehaag(UPDATED_BREEDTEKLASSEHAAG)
            .bvc(UPDATED_BVC)
            .cultuurhistorischwaardevol(UPDATED_CULTUURHISTORISCHWAARDEVOL)
            .draagkrachtig(UPDATED_DRAAGKRACHTIG)
            .ecologischbeheer(UPDATED_ECOLOGISCHBEHEER)
            .fysiekvoorkomenimgeo(UPDATED_FYSIEKVOORKOMENIMGEO)
            .gewenstsluitingspercentage(UPDATED_GEWENSTSLUITINGSPERCENTAGE)
            .groenobjectbereikbaarheidplus(UPDATED_GROENOBJECTBEREIKBAARHEIDPLUS)
            .groenobjectconstructielaag(UPDATED_GROENOBJECTCONSTRUCTIELAAG)
            .groenobjectrand(UPDATED_GROENOBJECTRAND)
            .groenobjectsoortnaam(UPDATED_GROENOBJECTSOORTNAAM)
            .haagvoetlengte(UPDATED_HAAGVOETLENGTE)
            .haagvoetoppervlakte(UPDATED_HAAGVOETOPPERVLAKTE)
            .herplantplicht(UPDATED_HERPLANTPLICHT)
            .hoogte(UPDATED_HOOGTE)
            .hoogteklassehaag(UPDATED_HOOGTEKLASSEHAAG)
            .knipfrequentie(UPDATED_KNIPFREQUENTIE)
            .knipoppervlakte(UPDATED_KNIPOPPERVLAKTE)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .maaifrequentie(UPDATED_MAAIFREQUENTIE)
            .maximalevalhoogte(UPDATED_MAXIMALEVALHOOGTE)
            .eobjectnummer(UPDATED_EOBJECTNUMMER)
            .obstakels(UPDATED_OBSTAKELS)
            .omtrek(UPDATED_OMTREK)
            .ondergroei(UPDATED_ONDERGROEI)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .optalud(UPDATED_OPTALUD)
            .taludsteilte(UPDATED_TALUDSTEILTE)
            .type(UPDATED_TYPE)
            .typebewerking(UPDATED_TYPEBEWERKING)
            .typeomgevingsrisicoklasse(UPDATED_TYPEOMGEVINGSRISICOKLASSE)
            .typeplus(UPDATED_TYPEPLUS)
            .typeplus2(UPDATED_TYPEPLUS_2)
            .veiligheidsklasseboom(UPDATED_VEILIGHEIDSKLASSEBOOM);
        return groenobject;
    }

    @BeforeEach
    public void initTest() {
        groenobject = createEntity(em);
    }

    @Test
    @Transactional
    void createGroenobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Groenobject
        var returnedGroenobject = om.readValue(
            restGroenobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(groenobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Groenobject.class
        );

        // Validate the Groenobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGroenobjectUpdatableFieldsEquals(returnedGroenobject, getPersistedGroenobject(returnedGroenobject));
    }

    @Test
    @Transactional
    void createGroenobjectWithExistingId() throws Exception {
        // Create the Groenobject with an existing ID
        groenobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroenobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(groenobject)))
            .andExpect(status().isBadRequest());

        // Validate the Groenobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGroenobjects() throws Exception {
        // Initialize the database
        groenobjectRepository.saveAndFlush(groenobject);

        // Get all the groenobjectList
        restGroenobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groenobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantalobstakels").value(hasItem(DEFAULT_AANTALOBSTAKELS)))
            .andExpect(jsonPath("$.[*].aantalzijden").value(hasItem(DEFAULT_AANTALZIJDEN)))
            .andExpect(jsonPath("$.[*].afvoeren").value(hasItem(DEFAULT_AFVOEREN.booleanValue())))
            .andExpect(jsonPath("$.[*].bereikbaarheid").value(hasItem(DEFAULT_BEREIKBAARHEID)))
            .andExpect(jsonPath("$.[*].bergendvermogen").value(hasItem(DEFAULT_BERGENDVERMOGEN)))
            .andExpect(jsonPath("$.[*].bewerkingspercentage").value(hasItem(DEFAULT_BEWERKINGSPERCENTAGE)))
            .andExpect(jsonPath("$.[*].bgtfysiekvoorkomen").value(hasItem(DEFAULT_BGTFYSIEKVOORKOMEN)))
            .andExpect(jsonPath("$.[*].bollen").value(hasItem(DEFAULT_BOLLEN.booleanValue())))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].breedteklassehaag").value(hasItem(DEFAULT_BREEDTEKLASSEHAAG)))
            .andExpect(jsonPath("$.[*].bvc").value(hasItem(DEFAULT_BVC.booleanValue())))
            .andExpect(jsonPath("$.[*].cultuurhistorischwaardevol").value(hasItem(DEFAULT_CULTUURHISTORISCHWAARDEVOL)))
            .andExpect(jsonPath("$.[*].draagkrachtig").value(hasItem(DEFAULT_DRAAGKRACHTIG.booleanValue())))
            .andExpect(jsonPath("$.[*].ecologischbeheer").value(hasItem(DEFAULT_ECOLOGISCHBEHEER.booleanValue())))
            .andExpect(jsonPath("$.[*].fysiekvoorkomenimgeo").value(hasItem(DEFAULT_FYSIEKVOORKOMENIMGEO)))
            .andExpect(jsonPath("$.[*].gewenstsluitingspercentage").value(hasItem(DEFAULT_GEWENSTSLUITINGSPERCENTAGE)))
            .andExpect(jsonPath("$.[*].groenobjectbereikbaarheidplus").value(hasItem(DEFAULT_GROENOBJECTBEREIKBAARHEIDPLUS)))
            .andExpect(jsonPath("$.[*].groenobjectconstructielaag").value(hasItem(DEFAULT_GROENOBJECTCONSTRUCTIELAAG)))
            .andExpect(jsonPath("$.[*].groenobjectrand").value(hasItem(DEFAULT_GROENOBJECTRAND)))
            .andExpect(jsonPath("$.[*].groenobjectsoortnaam").value(hasItem(DEFAULT_GROENOBJECTSOORTNAAM)))
            .andExpect(jsonPath("$.[*].haagvoetlengte").value(hasItem(DEFAULT_HAAGVOETLENGTE)))
            .andExpect(jsonPath("$.[*].haagvoetoppervlakte").value(hasItem(DEFAULT_HAAGVOETOPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].herplantplicht").value(hasItem(DEFAULT_HERPLANTPLICHT.booleanValue())))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].hoogteklassehaag").value(hasItem(DEFAULT_HOOGTEKLASSEHAAG)))
            .andExpect(jsonPath("$.[*].knipfrequentie").value(hasItem(DEFAULT_KNIPFREQUENTIE)))
            .andExpect(jsonPath("$.[*].knipoppervlakte").value(hasItem(DEFAULT_KNIPOPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveauactueel").value(hasItem(DEFAULT_KWALITEITSNIVEAUACTUEEL)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveaugewenst").value(hasItem(DEFAULT_KWALITEITSNIVEAUGEWENST)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].leverancier").value(hasItem(DEFAULT_LEVERANCIER)))
            .andExpect(jsonPath("$.[*].maaifrequentie").value(hasItem(DEFAULT_MAAIFREQUENTIE)))
            .andExpect(jsonPath("$.[*].maximalevalhoogte").value(hasItem(DEFAULT_MAXIMALEVALHOOGTE)))
            .andExpect(jsonPath("$.[*].eobjectnummer").value(hasItem(DEFAULT_EOBJECTNUMMER)))
            .andExpect(jsonPath("$.[*].obstakels").value(hasItem(DEFAULT_OBSTAKELS.booleanValue())))
            .andExpect(jsonPath("$.[*].omtrek").value(hasItem(DEFAULT_OMTREK)))
            .andExpect(jsonPath("$.[*].ondergroei").value(hasItem(DEFAULT_ONDERGROEI)))
            .andExpect(jsonPath("$.[*].oppervlakte").value(hasItem(DEFAULT_OPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].optalud").value(hasItem(DEFAULT_OPTALUD)))
            .andExpect(jsonPath("$.[*].taludsteilte").value(hasItem(DEFAULT_TALUDSTEILTE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].typebewerking").value(hasItem(DEFAULT_TYPEBEWERKING)))
            .andExpect(jsonPath("$.[*].typeomgevingsrisicoklasse").value(hasItem(DEFAULT_TYPEOMGEVINGSRISICOKLASSE)))
            .andExpect(jsonPath("$.[*].typeplus").value(hasItem(DEFAULT_TYPEPLUS)))
            .andExpect(jsonPath("$.[*].typeplus2").value(hasItem(DEFAULT_TYPEPLUS_2)))
            .andExpect(jsonPath("$.[*].veiligheidsklasseboom").value(hasItem(DEFAULT_VEILIGHEIDSKLASSEBOOM)));
    }

    @Test
    @Transactional
    void getGroenobject() throws Exception {
        // Initialize the database
        groenobjectRepository.saveAndFlush(groenobject);

        // Get the groenobject
        restGroenobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, groenobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groenobject.getId().intValue()))
            .andExpect(jsonPath("$.aantalobstakels").value(DEFAULT_AANTALOBSTAKELS))
            .andExpect(jsonPath("$.aantalzijden").value(DEFAULT_AANTALZIJDEN))
            .andExpect(jsonPath("$.afvoeren").value(DEFAULT_AFVOEREN.booleanValue()))
            .andExpect(jsonPath("$.bereikbaarheid").value(DEFAULT_BEREIKBAARHEID))
            .andExpect(jsonPath("$.bergendvermogen").value(DEFAULT_BERGENDVERMOGEN))
            .andExpect(jsonPath("$.bewerkingspercentage").value(DEFAULT_BEWERKINGSPERCENTAGE))
            .andExpect(jsonPath("$.bgtfysiekvoorkomen").value(DEFAULT_BGTFYSIEKVOORKOMEN))
            .andExpect(jsonPath("$.bollen").value(DEFAULT_BOLLEN.booleanValue()))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.breedteklassehaag").value(DEFAULT_BREEDTEKLASSEHAAG))
            .andExpect(jsonPath("$.bvc").value(DEFAULT_BVC.booleanValue()))
            .andExpect(jsonPath("$.cultuurhistorischwaardevol").value(DEFAULT_CULTUURHISTORISCHWAARDEVOL))
            .andExpect(jsonPath("$.draagkrachtig").value(DEFAULT_DRAAGKRACHTIG.booleanValue()))
            .andExpect(jsonPath("$.ecologischbeheer").value(DEFAULT_ECOLOGISCHBEHEER.booleanValue()))
            .andExpect(jsonPath("$.fysiekvoorkomenimgeo").value(DEFAULT_FYSIEKVOORKOMENIMGEO))
            .andExpect(jsonPath("$.gewenstsluitingspercentage").value(DEFAULT_GEWENSTSLUITINGSPERCENTAGE))
            .andExpect(jsonPath("$.groenobjectbereikbaarheidplus").value(DEFAULT_GROENOBJECTBEREIKBAARHEIDPLUS))
            .andExpect(jsonPath("$.groenobjectconstructielaag").value(DEFAULT_GROENOBJECTCONSTRUCTIELAAG))
            .andExpect(jsonPath("$.groenobjectrand").value(DEFAULT_GROENOBJECTRAND))
            .andExpect(jsonPath("$.groenobjectsoortnaam").value(DEFAULT_GROENOBJECTSOORTNAAM))
            .andExpect(jsonPath("$.haagvoetlengte").value(DEFAULT_HAAGVOETLENGTE))
            .andExpect(jsonPath("$.haagvoetoppervlakte").value(DEFAULT_HAAGVOETOPPERVLAKTE))
            .andExpect(jsonPath("$.herplantplicht").value(DEFAULT_HERPLANTPLICHT.booleanValue()))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.hoogteklassehaag").value(DEFAULT_HOOGTEKLASSEHAAG))
            .andExpect(jsonPath("$.knipfrequentie").value(DEFAULT_KNIPFREQUENTIE))
            .andExpect(jsonPath("$.knipoppervlakte").value(DEFAULT_KNIPOPPERVLAKTE))
            .andExpect(jsonPath("$.kwaliteitsniveauactueel").value(DEFAULT_KWALITEITSNIVEAUACTUEEL))
            .andExpect(jsonPath("$.kwaliteitsniveaugewenst").value(DEFAULT_KWALITEITSNIVEAUGEWENST))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.leverancier").value(DEFAULT_LEVERANCIER))
            .andExpect(jsonPath("$.maaifrequentie").value(DEFAULT_MAAIFREQUENTIE))
            .andExpect(jsonPath("$.maximalevalhoogte").value(DEFAULT_MAXIMALEVALHOOGTE))
            .andExpect(jsonPath("$.eobjectnummer").value(DEFAULT_EOBJECTNUMMER))
            .andExpect(jsonPath("$.obstakels").value(DEFAULT_OBSTAKELS.booleanValue()))
            .andExpect(jsonPath("$.omtrek").value(DEFAULT_OMTREK))
            .andExpect(jsonPath("$.ondergroei").value(DEFAULT_ONDERGROEI))
            .andExpect(jsonPath("$.oppervlakte").value(DEFAULT_OPPERVLAKTE))
            .andExpect(jsonPath("$.optalud").value(DEFAULT_OPTALUD))
            .andExpect(jsonPath("$.taludsteilte").value(DEFAULT_TALUDSTEILTE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.typebewerking").value(DEFAULT_TYPEBEWERKING))
            .andExpect(jsonPath("$.typeomgevingsrisicoklasse").value(DEFAULT_TYPEOMGEVINGSRISICOKLASSE))
            .andExpect(jsonPath("$.typeplus").value(DEFAULT_TYPEPLUS))
            .andExpect(jsonPath("$.typeplus2").value(DEFAULT_TYPEPLUS_2))
            .andExpect(jsonPath("$.veiligheidsklasseboom").value(DEFAULT_VEILIGHEIDSKLASSEBOOM));
    }

    @Test
    @Transactional
    void getNonExistingGroenobject() throws Exception {
        // Get the groenobject
        restGroenobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGroenobject() throws Exception {
        // Initialize the database
        groenobjectRepository.saveAndFlush(groenobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the groenobject
        Groenobject updatedGroenobject = groenobjectRepository.findById(groenobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGroenobject are not directly saved in db
        em.detach(updatedGroenobject);
        updatedGroenobject
            .aantalobstakels(UPDATED_AANTALOBSTAKELS)
            .aantalzijden(UPDATED_AANTALZIJDEN)
            .afvoeren(UPDATED_AFVOEREN)
            .bereikbaarheid(UPDATED_BEREIKBAARHEID)
            .bergendvermogen(UPDATED_BERGENDVERMOGEN)
            .bewerkingspercentage(UPDATED_BEWERKINGSPERCENTAGE)
            .bgtfysiekvoorkomen(UPDATED_BGTFYSIEKVOORKOMEN)
            .bollen(UPDATED_BOLLEN)
            .breedte(UPDATED_BREEDTE)
            .breedteklassehaag(UPDATED_BREEDTEKLASSEHAAG)
            .bvc(UPDATED_BVC)
            .cultuurhistorischwaardevol(UPDATED_CULTUURHISTORISCHWAARDEVOL)
            .draagkrachtig(UPDATED_DRAAGKRACHTIG)
            .ecologischbeheer(UPDATED_ECOLOGISCHBEHEER)
            .fysiekvoorkomenimgeo(UPDATED_FYSIEKVOORKOMENIMGEO)
            .gewenstsluitingspercentage(UPDATED_GEWENSTSLUITINGSPERCENTAGE)
            .groenobjectbereikbaarheidplus(UPDATED_GROENOBJECTBEREIKBAARHEIDPLUS)
            .groenobjectconstructielaag(UPDATED_GROENOBJECTCONSTRUCTIELAAG)
            .groenobjectrand(UPDATED_GROENOBJECTRAND)
            .groenobjectsoortnaam(UPDATED_GROENOBJECTSOORTNAAM)
            .haagvoetlengte(UPDATED_HAAGVOETLENGTE)
            .haagvoetoppervlakte(UPDATED_HAAGVOETOPPERVLAKTE)
            .herplantplicht(UPDATED_HERPLANTPLICHT)
            .hoogte(UPDATED_HOOGTE)
            .hoogteklassehaag(UPDATED_HOOGTEKLASSEHAAG)
            .knipfrequentie(UPDATED_KNIPFREQUENTIE)
            .knipoppervlakte(UPDATED_KNIPOPPERVLAKTE)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .maaifrequentie(UPDATED_MAAIFREQUENTIE)
            .maximalevalhoogte(UPDATED_MAXIMALEVALHOOGTE)
            .eobjectnummer(UPDATED_EOBJECTNUMMER)
            .obstakels(UPDATED_OBSTAKELS)
            .omtrek(UPDATED_OMTREK)
            .ondergroei(UPDATED_ONDERGROEI)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .optalud(UPDATED_OPTALUD)
            .taludsteilte(UPDATED_TALUDSTEILTE)
            .type(UPDATED_TYPE)
            .typebewerking(UPDATED_TYPEBEWERKING)
            .typeomgevingsrisicoklasse(UPDATED_TYPEOMGEVINGSRISICOKLASSE)
            .typeplus(UPDATED_TYPEPLUS)
            .typeplus2(UPDATED_TYPEPLUS_2)
            .veiligheidsklasseboom(UPDATED_VEILIGHEIDSKLASSEBOOM);

        restGroenobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGroenobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGroenobject))
            )
            .andExpect(status().isOk());

        // Validate the Groenobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGroenobjectToMatchAllProperties(updatedGroenobject);
    }

    @Test
    @Transactional
    void putNonExistingGroenobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        groenobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroenobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, groenobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(groenobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Groenobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGroenobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        groenobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroenobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(groenobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Groenobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGroenobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        groenobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroenobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(groenobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Groenobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGroenobjectWithPatch() throws Exception {
        // Initialize the database
        groenobjectRepository.saveAndFlush(groenobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the groenobject using partial update
        Groenobject partialUpdatedGroenobject = new Groenobject();
        partialUpdatedGroenobject.setId(groenobject.getId());

        partialUpdatedGroenobject
            .aantalobstakels(UPDATED_AANTALOBSTAKELS)
            .aantalzijden(UPDATED_AANTALZIJDEN)
            .bereikbaarheid(UPDATED_BEREIKBAARHEID)
            .bergendvermogen(UPDATED_BERGENDVERMOGEN)
            .bgtfysiekvoorkomen(UPDATED_BGTFYSIEKVOORKOMEN)
            .draagkrachtig(UPDATED_DRAAGKRACHTIG)
            .ecologischbeheer(UPDATED_ECOLOGISCHBEHEER)
            .fysiekvoorkomenimgeo(UPDATED_FYSIEKVOORKOMENIMGEO)
            .groenobjectbereikbaarheidplus(UPDATED_GROENOBJECTBEREIKBAARHEIDPLUS)
            .haagvoetlengte(UPDATED_HAAGVOETLENGTE)
            .hoogte(UPDATED_HOOGTE)
            .hoogteklassehaag(UPDATED_HOOGTEKLASSEHAAG)
            .knipfrequentie(UPDATED_KNIPFREQUENTIE)
            .knipoppervlakte(UPDATED_KNIPOPPERVLAKTE)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .eobjectnummer(UPDATED_EOBJECTNUMMER)
            .obstakels(UPDATED_OBSTAKELS)
            .ondergroei(UPDATED_ONDERGROEI)
            .optalud(UPDATED_OPTALUD)
            .typeplus(UPDATED_TYPEPLUS)
            .veiligheidsklasseboom(UPDATED_VEILIGHEIDSKLASSEBOOM);

        restGroenobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroenobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGroenobject))
            )
            .andExpect(status().isOk());

        // Validate the Groenobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGroenobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGroenobject, groenobject),
            getPersistedGroenobject(groenobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateGroenobjectWithPatch() throws Exception {
        // Initialize the database
        groenobjectRepository.saveAndFlush(groenobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the groenobject using partial update
        Groenobject partialUpdatedGroenobject = new Groenobject();
        partialUpdatedGroenobject.setId(groenobject.getId());

        partialUpdatedGroenobject
            .aantalobstakels(UPDATED_AANTALOBSTAKELS)
            .aantalzijden(UPDATED_AANTALZIJDEN)
            .afvoeren(UPDATED_AFVOEREN)
            .bereikbaarheid(UPDATED_BEREIKBAARHEID)
            .bergendvermogen(UPDATED_BERGENDVERMOGEN)
            .bewerkingspercentage(UPDATED_BEWERKINGSPERCENTAGE)
            .bgtfysiekvoorkomen(UPDATED_BGTFYSIEKVOORKOMEN)
            .bollen(UPDATED_BOLLEN)
            .breedte(UPDATED_BREEDTE)
            .breedteklassehaag(UPDATED_BREEDTEKLASSEHAAG)
            .bvc(UPDATED_BVC)
            .cultuurhistorischwaardevol(UPDATED_CULTUURHISTORISCHWAARDEVOL)
            .draagkrachtig(UPDATED_DRAAGKRACHTIG)
            .ecologischbeheer(UPDATED_ECOLOGISCHBEHEER)
            .fysiekvoorkomenimgeo(UPDATED_FYSIEKVOORKOMENIMGEO)
            .gewenstsluitingspercentage(UPDATED_GEWENSTSLUITINGSPERCENTAGE)
            .groenobjectbereikbaarheidplus(UPDATED_GROENOBJECTBEREIKBAARHEIDPLUS)
            .groenobjectconstructielaag(UPDATED_GROENOBJECTCONSTRUCTIELAAG)
            .groenobjectrand(UPDATED_GROENOBJECTRAND)
            .groenobjectsoortnaam(UPDATED_GROENOBJECTSOORTNAAM)
            .haagvoetlengte(UPDATED_HAAGVOETLENGTE)
            .haagvoetoppervlakte(UPDATED_HAAGVOETOPPERVLAKTE)
            .herplantplicht(UPDATED_HERPLANTPLICHT)
            .hoogte(UPDATED_HOOGTE)
            .hoogteklassehaag(UPDATED_HOOGTEKLASSEHAAG)
            .knipfrequentie(UPDATED_KNIPFREQUENTIE)
            .knipoppervlakte(UPDATED_KNIPOPPERVLAKTE)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .maaifrequentie(UPDATED_MAAIFREQUENTIE)
            .maximalevalhoogte(UPDATED_MAXIMALEVALHOOGTE)
            .eobjectnummer(UPDATED_EOBJECTNUMMER)
            .obstakels(UPDATED_OBSTAKELS)
            .omtrek(UPDATED_OMTREK)
            .ondergroei(UPDATED_ONDERGROEI)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .optalud(UPDATED_OPTALUD)
            .taludsteilte(UPDATED_TALUDSTEILTE)
            .type(UPDATED_TYPE)
            .typebewerking(UPDATED_TYPEBEWERKING)
            .typeomgevingsrisicoklasse(UPDATED_TYPEOMGEVINGSRISICOKLASSE)
            .typeplus(UPDATED_TYPEPLUS)
            .typeplus2(UPDATED_TYPEPLUS_2)
            .veiligheidsklasseboom(UPDATED_VEILIGHEIDSKLASSEBOOM);

        restGroenobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroenobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGroenobject))
            )
            .andExpect(status().isOk());

        // Validate the Groenobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGroenobjectUpdatableFieldsEquals(partialUpdatedGroenobject, getPersistedGroenobject(partialUpdatedGroenobject));
    }

    @Test
    @Transactional
    void patchNonExistingGroenobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        groenobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroenobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, groenobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(groenobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Groenobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGroenobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        groenobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroenobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(groenobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Groenobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGroenobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        groenobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroenobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(groenobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Groenobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGroenobject() throws Exception {
        // Initialize the database
        groenobjectRepository.saveAndFlush(groenobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the groenobject
        restGroenobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, groenobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return groenobjectRepository.count();
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

    protected Groenobject getPersistedGroenobject(Groenobject groenobject) {
        return groenobjectRepository.findById(groenobject.getId()).orElseThrow();
    }

    protected void assertPersistedGroenobjectToMatchAllProperties(Groenobject expectedGroenobject) {
        assertGroenobjectAllPropertiesEquals(expectedGroenobject, getPersistedGroenobject(expectedGroenobject));
    }

    protected void assertPersistedGroenobjectToMatchUpdatableProperties(Groenobject expectedGroenobject) {
        assertGroenobjectAllUpdatablePropertiesEquals(expectedGroenobject, getPersistedGroenobject(expectedGroenobject));
    }
}
