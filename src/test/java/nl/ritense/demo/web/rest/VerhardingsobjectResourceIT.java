package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerhardingsobjectAsserts.*;
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
import nl.ritense.demo.domain.Verhardingsobject;
import nl.ritense.demo.repository.VerhardingsobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerhardingsobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerhardingsobjectResourceIT {

    private static final String DEFAULT_AANLEGHOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_AANLEGHOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_AANOFVRIJLIGGEND = "AAAAAAAAAA";
    private static final String UPDATED_AANOFVRIJLIGGEND = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALDEKLAGEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALDEKLAGEN = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALONDERLAGEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALONDERLAGEN = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALTUSSENLAGEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALTUSSENLAGEN = "BBBBBBBBBB";

    private static final String DEFAULT_AFMETING = "AAAAAAAAAA";
    private static final String UPDATED_AFMETING = "BBBBBBBBBB";

    private static final String DEFAULT_BELASTING = "AAAAAAAAAA";
    private static final String UPDATED_BELASTING = "BBBBBBBBBB";

    private static final String DEFAULT_BERGENDVERMOGEN = "AAAAAAAAAA";
    private static final String UPDATED_BERGENDVERMOGEN = "BBBBBBBBBB";

    private static final String DEFAULT_BGTFYSIEKVOORKOMEN = "AAAAAAAAAA";
    private static final String UPDATED_BGTFYSIEKVOORKOMEN = "BBBBBBBBBB";

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_DIKTECONSTRUCTIE = "AAAAAAAAAA";
    private static final String UPDATED_DIKTECONSTRUCTIE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DRAAGKRACHTIG = false;
    private static final Boolean UPDATED_DRAAGKRACHTIG = true;

    private static final String DEFAULT_FORMAAT = "AAAAAAAAAA";
    private static final String UPDATED_FORMAAT = "BBBBBBBBBB";

    private static final String DEFAULT_FYSIEKVOORKOMENIMGEO = "AAAAAAAAAA";
    private static final String UPDATED_FYSIEKVOORKOMENIMGEO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GELUIDSREDUCEREND = false;
    private static final Boolean UPDATED_GELUIDSREDUCEREND = true;

    private static final String DEFAULT_JAARCONSERVEREN = "AAAAAAAAAA";
    private static final String UPDATED_JAARCONSERVEREN = "BBBBBBBBBB";

    private static final String DEFAULT_JAARONDERHOUDUITGEVOERD = "AAAAAAAAAA";
    private static final String UPDATED_JAARONDERHOUDUITGEVOERD = "BBBBBBBBBB";

    private static final String DEFAULT_JAARPRAKTISCHEINDE = "AAAAAAAAAA";
    private static final String UPDATED_JAARPRAKTISCHEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_KLEUR = "AAAAAAAAAA";
    private static final String UPDATED_KLEUR = "BBBBBBBBBB";

    private static final String DEFAULT_KWALITEITSNIVEAUACTUEEL = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEITSNIVEAUACTUEEL = "BBBBBBBBBB";

    private static final String DEFAULT_KWALITEITSNIVEAUGEWENST = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEITSNIVEAUGEWENST = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTE = "AAAAAAAAAA";
    private static final String UPDATED_LENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTEKUNSTGRAS = "AAAAAAAAAA";
    private static final String UPDATED_LENGTEKUNSTGRAS = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTEVOEGEN = "AAAAAAAAAA";
    private static final String UPDATED_LENGTEVOEGEN = "BBBBBBBBBB";

    private static final String DEFAULT_LEVENSDUUR = "AAAAAAAAAA";
    private static final String UPDATED_LEVENSDUUR = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAAL = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAAL = "BBBBBBBBBB";

    private static final String DEFAULT_MAXIMALEVALHOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_MAXIMALEVALHOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_OMTREK = "AAAAAAAAAA";
    private static final String UPDATED_OMTREK = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERGRONDCODE = "AAAAAAAAAA";
    private static final String UPDATED_ONDERGRONDCODE = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_OPTALUD = "AAAAAAAAAA";
    private static final String UPDATED_OPTALUD = "BBBBBBBBBB";

    private static final String DEFAULT_PLAATSORIENTATIE = "AAAAAAAAAA";
    private static final String UPDATED_PLAATSORIENTATIE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIJSAANSCHAF = "AAAAAAAAAA";
    private static final String UPDATED_PRIJSAANSCHAF = "BBBBBBBBBB";

    private static final String DEFAULT_RIJSTROOK = "AAAAAAAAAA";
    private static final String UPDATED_RIJSTROOK = "BBBBBBBBBB";

    private static final String DEFAULT_SOORTVOEG = "AAAAAAAAAA";
    private static final String UPDATED_SOORTVOEG = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTINGGEMENGDEBESTRATING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTINGGEMENGDEBESTRATING = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPECONSTRUCTIE = "AAAAAAAAAA";
    private static final String UPDATED_TYPECONSTRUCTIE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEFUNDERING = "AAAAAAAAAA";
    private static final String UPDATED_TYPEFUNDERING = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS_2 = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_TYPERIJSTROOK = "AAAAAAAAAA";
    private static final String UPDATED_TYPERIJSTROOK = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEVOEG = "AAAAAAAAAA";
    private static final String UPDATED_TYPEVOEG = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEVOEGVULLING = "AAAAAAAAAA";
    private static final String UPDATED_TYPEVOEGVULLING = "BBBBBBBBBB";

    private static final String DEFAULT_VEGEN = "AAAAAAAAAA";
    private static final String UPDATED_VEGEN = "BBBBBBBBBB";

    private static final String DEFAULT_VERHARDINGSOBJECTCONSTRUCTIELAAG = "AAAAAAAAAA";
    private static final String UPDATED_VERHARDINGSOBJECTCONSTRUCTIELAAG = "BBBBBBBBBB";

    private static final String DEFAULT_VERHARDINGSOBJECTMODALITEIT = "AAAAAAAAAA";
    private static final String UPDATED_VERHARDINGSOBJECTMODALITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_VERHARDINGSOBJECTRAND = "AAAAAAAAAA";
    private static final String UPDATED_VERHARDINGSOBJECTRAND = "BBBBBBBBBB";

    private static final String DEFAULT_VERHARDINGSOBJECTWEGFUNCTIE = "AAAAAAAAAA";
    private static final String UPDATED_VERHARDINGSOBJECTWEGFUNCTIE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VERHOOGDELIGGING = false;
    private static final Boolean UPDATED_VERHOOGDELIGGING = true;

    private static final String DEFAULT_VULMATERIAALKUNSTGRAS = "AAAAAAAAAA";
    private static final String UPDATED_VULMATERIAALKUNSTGRAS = "BBBBBBBBBB";

    private static final String DEFAULT_WATERDOORLATENDHEID = "AAAAAAAAAA";
    private static final String UPDATED_WATERDOORLATENDHEID = "BBBBBBBBBB";

    private static final String DEFAULT_WEGAS = "AAAAAAAAAA";
    private static final String UPDATED_WEGAS = "BBBBBBBBBB";

    private static final String DEFAULT_WEGCATEGORIEDV = "AAAAAAAAAA";
    private static final String UPDATED_WEGCATEGORIEDV = "BBBBBBBBBB";

    private static final String DEFAULT_WEGCATEGORIEDVPLUS = "AAAAAAAAAA";
    private static final String UPDATED_WEGCATEGORIEDVPLUS = "BBBBBBBBBB";

    private static final String DEFAULT_WEGNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_WEGNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_WEGTYPEBESTAAND = "AAAAAAAAAA";
    private static final String UPDATED_WEGTYPEBESTAAND = "BBBBBBBBBB";

    private static final String DEFAULT_WEGVAK = "AAAAAAAAAA";
    private static final String UPDATED_WEGVAK = "BBBBBBBBBB";

    private static final String DEFAULT_WEGVAKNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_WEGVAKNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verhardingsobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerhardingsobjectRepository verhardingsobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerhardingsobjectMockMvc;

    private Verhardingsobject verhardingsobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verhardingsobject createEntity(EntityManager em) {
        Verhardingsobject verhardingsobject = new Verhardingsobject()
            .aanleghoogte(DEFAULT_AANLEGHOOGTE)
            .aanofvrijliggend(DEFAULT_AANOFVRIJLIGGEND)
            .aantaldeklagen(DEFAULT_AANTALDEKLAGEN)
            .aantalonderlagen(DEFAULT_AANTALONDERLAGEN)
            .aantaltussenlagen(DEFAULT_AANTALTUSSENLAGEN)
            .afmeting(DEFAULT_AFMETING)
            .belasting(DEFAULT_BELASTING)
            .bergendvermogen(DEFAULT_BERGENDVERMOGEN)
            .bgtfysiekvoorkomen(DEFAULT_BGTFYSIEKVOORKOMEN)
            .breedte(DEFAULT_BREEDTE)
            .dikteconstructie(DEFAULT_DIKTECONSTRUCTIE)
            .draagkrachtig(DEFAULT_DRAAGKRACHTIG)
            .formaat(DEFAULT_FORMAAT)
            .fysiekvoorkomenimgeo(DEFAULT_FYSIEKVOORKOMENIMGEO)
            .geluidsreducerend(DEFAULT_GELUIDSREDUCEREND)
            .jaarconserveren(DEFAULT_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(DEFAULT_JAARONDERHOUDUITGEVOERD)
            .jaarpraktischeinde(DEFAULT_JAARPRAKTISCHEINDE)
            .kleur(DEFAULT_KLEUR)
            .kwaliteitsniveauactueel(DEFAULT_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(DEFAULT_KWALITEITSNIVEAUGEWENST)
            .lengte(DEFAULT_LENGTE)
            .lengtekunstgras(DEFAULT_LENGTEKUNSTGRAS)
            .lengtevoegen(DEFAULT_LENGTEVOEGEN)
            .levensduur(DEFAULT_LEVENSDUUR)
            .materiaal(DEFAULT_MATERIAAL)
            .maximalevalhoogte(DEFAULT_MAXIMALEVALHOOGTE)
            .omtrek(DEFAULT_OMTREK)
            .ondergrondcode(DEFAULT_ONDERGRONDCODE)
            .oppervlakte(DEFAULT_OPPERVLAKTE)
            .optalud(DEFAULT_OPTALUD)
            .plaatsorientatie(DEFAULT_PLAATSORIENTATIE)
            .prijsaanschaf(DEFAULT_PRIJSAANSCHAF)
            .rijstrook(DEFAULT_RIJSTROOK)
            .soortvoeg(DEFAULT_SOORTVOEG)
            .toelichtinggemengdebestrating(DEFAULT_TOELICHTINGGEMENGDEBESTRATING)
            .type(DEFAULT_TYPE)
            .typeconstructie(DEFAULT_TYPECONSTRUCTIE)
            .typefundering(DEFAULT_TYPEFUNDERING)
            .typeplus(DEFAULT_TYPEPLUS)
            .typeplus2(DEFAULT_TYPEPLUS_2)
            .typerijstrook(DEFAULT_TYPERIJSTROOK)
            .typevoeg(DEFAULT_TYPEVOEG)
            .typevoegvulling(DEFAULT_TYPEVOEGVULLING)
            .vegen(DEFAULT_VEGEN)
            .verhardingsobjectconstructielaag(DEFAULT_VERHARDINGSOBJECTCONSTRUCTIELAAG)
            .verhardingsobjectmodaliteit(DEFAULT_VERHARDINGSOBJECTMODALITEIT)
            .verhardingsobjectrand(DEFAULT_VERHARDINGSOBJECTRAND)
            .verhardingsobjectwegfunctie(DEFAULT_VERHARDINGSOBJECTWEGFUNCTIE)
            .verhoogdeligging(DEFAULT_VERHOOGDELIGGING)
            .vulmateriaalkunstgras(DEFAULT_VULMATERIAALKUNSTGRAS)
            .waterdoorlatendheid(DEFAULT_WATERDOORLATENDHEID)
            .wegas(DEFAULT_WEGAS)
            .wegcategoriedv(DEFAULT_WEGCATEGORIEDV)
            .wegcategoriedvplus(DEFAULT_WEGCATEGORIEDVPLUS)
            .wegnummer(DEFAULT_WEGNUMMER)
            .wegtypebestaand(DEFAULT_WEGTYPEBESTAAND)
            .wegvak(DEFAULT_WEGVAK)
            .wegvaknummer(DEFAULT_WEGVAKNUMMER);
        return verhardingsobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verhardingsobject createUpdatedEntity(EntityManager em) {
        Verhardingsobject verhardingsobject = new Verhardingsobject()
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .aanofvrijliggend(UPDATED_AANOFVRIJLIGGEND)
            .aantaldeklagen(UPDATED_AANTALDEKLAGEN)
            .aantalonderlagen(UPDATED_AANTALONDERLAGEN)
            .aantaltussenlagen(UPDATED_AANTALTUSSENLAGEN)
            .afmeting(UPDATED_AFMETING)
            .belasting(UPDATED_BELASTING)
            .bergendvermogen(UPDATED_BERGENDVERMOGEN)
            .bgtfysiekvoorkomen(UPDATED_BGTFYSIEKVOORKOMEN)
            .breedte(UPDATED_BREEDTE)
            .dikteconstructie(UPDATED_DIKTECONSTRUCTIE)
            .draagkrachtig(UPDATED_DRAAGKRACHTIG)
            .formaat(UPDATED_FORMAAT)
            .fysiekvoorkomenimgeo(UPDATED_FYSIEKVOORKOMENIMGEO)
            .geluidsreducerend(UPDATED_GELUIDSREDUCEREND)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .jaarpraktischeinde(UPDATED_JAARPRAKTISCHEINDE)
            .kleur(UPDATED_KLEUR)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .lengtekunstgras(UPDATED_LENGTEKUNSTGRAS)
            .lengtevoegen(UPDATED_LENGTEVOEGEN)
            .levensduur(UPDATED_LEVENSDUUR)
            .materiaal(UPDATED_MATERIAAL)
            .maximalevalhoogte(UPDATED_MAXIMALEVALHOOGTE)
            .omtrek(UPDATED_OMTREK)
            .ondergrondcode(UPDATED_ONDERGRONDCODE)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .optalud(UPDATED_OPTALUD)
            .plaatsorientatie(UPDATED_PLAATSORIENTATIE)
            .prijsaanschaf(UPDATED_PRIJSAANSCHAF)
            .rijstrook(UPDATED_RIJSTROOK)
            .soortvoeg(UPDATED_SOORTVOEG)
            .toelichtinggemengdebestrating(UPDATED_TOELICHTINGGEMENGDEBESTRATING)
            .type(UPDATED_TYPE)
            .typeconstructie(UPDATED_TYPECONSTRUCTIE)
            .typefundering(UPDATED_TYPEFUNDERING)
            .typeplus(UPDATED_TYPEPLUS)
            .typeplus2(UPDATED_TYPEPLUS_2)
            .typerijstrook(UPDATED_TYPERIJSTROOK)
            .typevoeg(UPDATED_TYPEVOEG)
            .typevoegvulling(UPDATED_TYPEVOEGVULLING)
            .vegen(UPDATED_VEGEN)
            .verhardingsobjectconstructielaag(UPDATED_VERHARDINGSOBJECTCONSTRUCTIELAAG)
            .verhardingsobjectmodaliteit(UPDATED_VERHARDINGSOBJECTMODALITEIT)
            .verhardingsobjectrand(UPDATED_VERHARDINGSOBJECTRAND)
            .verhardingsobjectwegfunctie(UPDATED_VERHARDINGSOBJECTWEGFUNCTIE)
            .verhoogdeligging(UPDATED_VERHOOGDELIGGING)
            .vulmateriaalkunstgras(UPDATED_VULMATERIAALKUNSTGRAS)
            .waterdoorlatendheid(UPDATED_WATERDOORLATENDHEID)
            .wegas(UPDATED_WEGAS)
            .wegcategoriedv(UPDATED_WEGCATEGORIEDV)
            .wegcategoriedvplus(UPDATED_WEGCATEGORIEDVPLUS)
            .wegnummer(UPDATED_WEGNUMMER)
            .wegtypebestaand(UPDATED_WEGTYPEBESTAAND)
            .wegvak(UPDATED_WEGVAK)
            .wegvaknummer(UPDATED_WEGVAKNUMMER);
        return verhardingsobject;
    }

    @BeforeEach
    public void initTest() {
        verhardingsobject = createEntity(em);
    }

    @Test
    @Transactional
    void createVerhardingsobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verhardingsobject
        var returnedVerhardingsobject = om.readValue(
            restVerhardingsobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verhardingsobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verhardingsobject.class
        );

        // Validate the Verhardingsobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerhardingsobjectUpdatableFieldsEquals(returnedVerhardingsobject, getPersistedVerhardingsobject(returnedVerhardingsobject));
    }

    @Test
    @Transactional
    void createVerhardingsobjectWithExistingId() throws Exception {
        // Create the Verhardingsobject with an existing ID
        verhardingsobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerhardingsobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verhardingsobject)))
            .andExpect(status().isBadRequest());

        // Validate the Verhardingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerhardingsobjects() throws Exception {
        // Initialize the database
        verhardingsobjectRepository.saveAndFlush(verhardingsobject);

        // Get all the verhardingsobjectList
        restVerhardingsobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verhardingsobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanleghoogte").value(hasItem(DEFAULT_AANLEGHOOGTE)))
            .andExpect(jsonPath("$.[*].aanofvrijliggend").value(hasItem(DEFAULT_AANOFVRIJLIGGEND)))
            .andExpect(jsonPath("$.[*].aantaldeklagen").value(hasItem(DEFAULT_AANTALDEKLAGEN)))
            .andExpect(jsonPath("$.[*].aantalonderlagen").value(hasItem(DEFAULT_AANTALONDERLAGEN)))
            .andExpect(jsonPath("$.[*].aantaltussenlagen").value(hasItem(DEFAULT_AANTALTUSSENLAGEN)))
            .andExpect(jsonPath("$.[*].afmeting").value(hasItem(DEFAULT_AFMETING)))
            .andExpect(jsonPath("$.[*].belasting").value(hasItem(DEFAULT_BELASTING)))
            .andExpect(jsonPath("$.[*].bergendvermogen").value(hasItem(DEFAULT_BERGENDVERMOGEN)))
            .andExpect(jsonPath("$.[*].bgtfysiekvoorkomen").value(hasItem(DEFAULT_BGTFYSIEKVOORKOMEN)))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].dikteconstructie").value(hasItem(DEFAULT_DIKTECONSTRUCTIE)))
            .andExpect(jsonPath("$.[*].draagkrachtig").value(hasItem(DEFAULT_DRAAGKRACHTIG.booleanValue())))
            .andExpect(jsonPath("$.[*].formaat").value(hasItem(DEFAULT_FORMAAT)))
            .andExpect(jsonPath("$.[*].fysiekvoorkomenimgeo").value(hasItem(DEFAULT_FYSIEKVOORKOMENIMGEO)))
            .andExpect(jsonPath("$.[*].geluidsreducerend").value(hasItem(DEFAULT_GELUIDSREDUCEREND.booleanValue())))
            .andExpect(jsonPath("$.[*].jaarconserveren").value(hasItem(DEFAULT_JAARCONSERVEREN)))
            .andExpect(jsonPath("$.[*].jaaronderhouduitgevoerd").value(hasItem(DEFAULT_JAARONDERHOUDUITGEVOERD)))
            .andExpect(jsonPath("$.[*].jaarpraktischeinde").value(hasItem(DEFAULT_JAARPRAKTISCHEINDE)))
            .andExpect(jsonPath("$.[*].kleur").value(hasItem(DEFAULT_KLEUR)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveauactueel").value(hasItem(DEFAULT_KWALITEITSNIVEAUACTUEEL)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveaugewenst").value(hasItem(DEFAULT_KWALITEITSNIVEAUGEWENST)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].lengtekunstgras").value(hasItem(DEFAULT_LENGTEKUNSTGRAS)))
            .andExpect(jsonPath("$.[*].lengtevoegen").value(hasItem(DEFAULT_LENGTEVOEGEN)))
            .andExpect(jsonPath("$.[*].levensduur").value(hasItem(DEFAULT_LEVENSDUUR)))
            .andExpect(jsonPath("$.[*].materiaal").value(hasItem(DEFAULT_MATERIAAL)))
            .andExpect(jsonPath("$.[*].maximalevalhoogte").value(hasItem(DEFAULT_MAXIMALEVALHOOGTE)))
            .andExpect(jsonPath("$.[*].omtrek").value(hasItem(DEFAULT_OMTREK)))
            .andExpect(jsonPath("$.[*].ondergrondcode").value(hasItem(DEFAULT_ONDERGRONDCODE)))
            .andExpect(jsonPath("$.[*].oppervlakte").value(hasItem(DEFAULT_OPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].optalud").value(hasItem(DEFAULT_OPTALUD)))
            .andExpect(jsonPath("$.[*].plaatsorientatie").value(hasItem(DEFAULT_PLAATSORIENTATIE)))
            .andExpect(jsonPath("$.[*].prijsaanschaf").value(hasItem(DEFAULT_PRIJSAANSCHAF)))
            .andExpect(jsonPath("$.[*].rijstrook").value(hasItem(DEFAULT_RIJSTROOK)))
            .andExpect(jsonPath("$.[*].soortvoeg").value(hasItem(DEFAULT_SOORTVOEG)))
            .andExpect(jsonPath("$.[*].toelichtinggemengdebestrating").value(hasItem(DEFAULT_TOELICHTINGGEMENGDEBESTRATING)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].typeconstructie").value(hasItem(DEFAULT_TYPECONSTRUCTIE)))
            .andExpect(jsonPath("$.[*].typefundering").value(hasItem(DEFAULT_TYPEFUNDERING)))
            .andExpect(jsonPath("$.[*].typeplus").value(hasItem(DEFAULT_TYPEPLUS)))
            .andExpect(jsonPath("$.[*].typeplus2").value(hasItem(DEFAULT_TYPEPLUS_2)))
            .andExpect(jsonPath("$.[*].typerijstrook").value(hasItem(DEFAULT_TYPERIJSTROOK)))
            .andExpect(jsonPath("$.[*].typevoeg").value(hasItem(DEFAULT_TYPEVOEG)))
            .andExpect(jsonPath("$.[*].typevoegvulling").value(hasItem(DEFAULT_TYPEVOEGVULLING)))
            .andExpect(jsonPath("$.[*].vegen").value(hasItem(DEFAULT_VEGEN)))
            .andExpect(jsonPath("$.[*].verhardingsobjectconstructielaag").value(hasItem(DEFAULT_VERHARDINGSOBJECTCONSTRUCTIELAAG)))
            .andExpect(jsonPath("$.[*].verhardingsobjectmodaliteit").value(hasItem(DEFAULT_VERHARDINGSOBJECTMODALITEIT)))
            .andExpect(jsonPath("$.[*].verhardingsobjectrand").value(hasItem(DEFAULT_VERHARDINGSOBJECTRAND)))
            .andExpect(jsonPath("$.[*].verhardingsobjectwegfunctie").value(hasItem(DEFAULT_VERHARDINGSOBJECTWEGFUNCTIE)))
            .andExpect(jsonPath("$.[*].verhoogdeligging").value(hasItem(DEFAULT_VERHOOGDELIGGING.booleanValue())))
            .andExpect(jsonPath("$.[*].vulmateriaalkunstgras").value(hasItem(DEFAULT_VULMATERIAALKUNSTGRAS)))
            .andExpect(jsonPath("$.[*].waterdoorlatendheid").value(hasItem(DEFAULT_WATERDOORLATENDHEID)))
            .andExpect(jsonPath("$.[*].wegas").value(hasItem(DEFAULT_WEGAS)))
            .andExpect(jsonPath("$.[*].wegcategoriedv").value(hasItem(DEFAULT_WEGCATEGORIEDV)))
            .andExpect(jsonPath("$.[*].wegcategoriedvplus").value(hasItem(DEFAULT_WEGCATEGORIEDVPLUS)))
            .andExpect(jsonPath("$.[*].wegnummer").value(hasItem(DEFAULT_WEGNUMMER)))
            .andExpect(jsonPath("$.[*].wegtypebestaand").value(hasItem(DEFAULT_WEGTYPEBESTAAND)))
            .andExpect(jsonPath("$.[*].wegvak").value(hasItem(DEFAULT_WEGVAK)))
            .andExpect(jsonPath("$.[*].wegvaknummer").value(hasItem(DEFAULT_WEGVAKNUMMER)));
    }

    @Test
    @Transactional
    void getVerhardingsobject() throws Exception {
        // Initialize the database
        verhardingsobjectRepository.saveAndFlush(verhardingsobject);

        // Get the verhardingsobject
        restVerhardingsobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, verhardingsobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verhardingsobject.getId().intValue()))
            .andExpect(jsonPath("$.aanleghoogte").value(DEFAULT_AANLEGHOOGTE))
            .andExpect(jsonPath("$.aanofvrijliggend").value(DEFAULT_AANOFVRIJLIGGEND))
            .andExpect(jsonPath("$.aantaldeklagen").value(DEFAULT_AANTALDEKLAGEN))
            .andExpect(jsonPath("$.aantalonderlagen").value(DEFAULT_AANTALONDERLAGEN))
            .andExpect(jsonPath("$.aantaltussenlagen").value(DEFAULT_AANTALTUSSENLAGEN))
            .andExpect(jsonPath("$.afmeting").value(DEFAULT_AFMETING))
            .andExpect(jsonPath("$.belasting").value(DEFAULT_BELASTING))
            .andExpect(jsonPath("$.bergendvermogen").value(DEFAULT_BERGENDVERMOGEN))
            .andExpect(jsonPath("$.bgtfysiekvoorkomen").value(DEFAULT_BGTFYSIEKVOORKOMEN))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.dikteconstructie").value(DEFAULT_DIKTECONSTRUCTIE))
            .andExpect(jsonPath("$.draagkrachtig").value(DEFAULT_DRAAGKRACHTIG.booleanValue()))
            .andExpect(jsonPath("$.formaat").value(DEFAULT_FORMAAT))
            .andExpect(jsonPath("$.fysiekvoorkomenimgeo").value(DEFAULT_FYSIEKVOORKOMENIMGEO))
            .andExpect(jsonPath("$.geluidsreducerend").value(DEFAULT_GELUIDSREDUCEREND.booleanValue()))
            .andExpect(jsonPath("$.jaarconserveren").value(DEFAULT_JAARCONSERVEREN))
            .andExpect(jsonPath("$.jaaronderhouduitgevoerd").value(DEFAULT_JAARONDERHOUDUITGEVOERD))
            .andExpect(jsonPath("$.jaarpraktischeinde").value(DEFAULT_JAARPRAKTISCHEINDE))
            .andExpect(jsonPath("$.kleur").value(DEFAULT_KLEUR))
            .andExpect(jsonPath("$.kwaliteitsniveauactueel").value(DEFAULT_KWALITEITSNIVEAUACTUEEL))
            .andExpect(jsonPath("$.kwaliteitsniveaugewenst").value(DEFAULT_KWALITEITSNIVEAUGEWENST))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.lengtekunstgras").value(DEFAULT_LENGTEKUNSTGRAS))
            .andExpect(jsonPath("$.lengtevoegen").value(DEFAULT_LENGTEVOEGEN))
            .andExpect(jsonPath("$.levensduur").value(DEFAULT_LEVENSDUUR))
            .andExpect(jsonPath("$.materiaal").value(DEFAULT_MATERIAAL))
            .andExpect(jsonPath("$.maximalevalhoogte").value(DEFAULT_MAXIMALEVALHOOGTE))
            .andExpect(jsonPath("$.omtrek").value(DEFAULT_OMTREK))
            .andExpect(jsonPath("$.ondergrondcode").value(DEFAULT_ONDERGRONDCODE))
            .andExpect(jsonPath("$.oppervlakte").value(DEFAULT_OPPERVLAKTE))
            .andExpect(jsonPath("$.optalud").value(DEFAULT_OPTALUD))
            .andExpect(jsonPath("$.plaatsorientatie").value(DEFAULT_PLAATSORIENTATIE))
            .andExpect(jsonPath("$.prijsaanschaf").value(DEFAULT_PRIJSAANSCHAF))
            .andExpect(jsonPath("$.rijstrook").value(DEFAULT_RIJSTROOK))
            .andExpect(jsonPath("$.soortvoeg").value(DEFAULT_SOORTVOEG))
            .andExpect(jsonPath("$.toelichtinggemengdebestrating").value(DEFAULT_TOELICHTINGGEMENGDEBESTRATING))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.typeconstructie").value(DEFAULT_TYPECONSTRUCTIE))
            .andExpect(jsonPath("$.typefundering").value(DEFAULT_TYPEFUNDERING))
            .andExpect(jsonPath("$.typeplus").value(DEFAULT_TYPEPLUS))
            .andExpect(jsonPath("$.typeplus2").value(DEFAULT_TYPEPLUS_2))
            .andExpect(jsonPath("$.typerijstrook").value(DEFAULT_TYPERIJSTROOK))
            .andExpect(jsonPath("$.typevoeg").value(DEFAULT_TYPEVOEG))
            .andExpect(jsonPath("$.typevoegvulling").value(DEFAULT_TYPEVOEGVULLING))
            .andExpect(jsonPath("$.vegen").value(DEFAULT_VEGEN))
            .andExpect(jsonPath("$.verhardingsobjectconstructielaag").value(DEFAULT_VERHARDINGSOBJECTCONSTRUCTIELAAG))
            .andExpect(jsonPath("$.verhardingsobjectmodaliteit").value(DEFAULT_VERHARDINGSOBJECTMODALITEIT))
            .andExpect(jsonPath("$.verhardingsobjectrand").value(DEFAULT_VERHARDINGSOBJECTRAND))
            .andExpect(jsonPath("$.verhardingsobjectwegfunctie").value(DEFAULT_VERHARDINGSOBJECTWEGFUNCTIE))
            .andExpect(jsonPath("$.verhoogdeligging").value(DEFAULT_VERHOOGDELIGGING.booleanValue()))
            .andExpect(jsonPath("$.vulmateriaalkunstgras").value(DEFAULT_VULMATERIAALKUNSTGRAS))
            .andExpect(jsonPath("$.waterdoorlatendheid").value(DEFAULT_WATERDOORLATENDHEID))
            .andExpect(jsonPath("$.wegas").value(DEFAULT_WEGAS))
            .andExpect(jsonPath("$.wegcategoriedv").value(DEFAULT_WEGCATEGORIEDV))
            .andExpect(jsonPath("$.wegcategoriedvplus").value(DEFAULT_WEGCATEGORIEDVPLUS))
            .andExpect(jsonPath("$.wegnummer").value(DEFAULT_WEGNUMMER))
            .andExpect(jsonPath("$.wegtypebestaand").value(DEFAULT_WEGTYPEBESTAAND))
            .andExpect(jsonPath("$.wegvak").value(DEFAULT_WEGVAK))
            .andExpect(jsonPath("$.wegvaknummer").value(DEFAULT_WEGVAKNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingVerhardingsobject() throws Exception {
        // Get the verhardingsobject
        restVerhardingsobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerhardingsobject() throws Exception {
        // Initialize the database
        verhardingsobjectRepository.saveAndFlush(verhardingsobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verhardingsobject
        Verhardingsobject updatedVerhardingsobject = verhardingsobjectRepository.findById(verhardingsobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerhardingsobject are not directly saved in db
        em.detach(updatedVerhardingsobject);
        updatedVerhardingsobject
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .aanofvrijliggend(UPDATED_AANOFVRIJLIGGEND)
            .aantaldeklagen(UPDATED_AANTALDEKLAGEN)
            .aantalonderlagen(UPDATED_AANTALONDERLAGEN)
            .aantaltussenlagen(UPDATED_AANTALTUSSENLAGEN)
            .afmeting(UPDATED_AFMETING)
            .belasting(UPDATED_BELASTING)
            .bergendvermogen(UPDATED_BERGENDVERMOGEN)
            .bgtfysiekvoorkomen(UPDATED_BGTFYSIEKVOORKOMEN)
            .breedte(UPDATED_BREEDTE)
            .dikteconstructie(UPDATED_DIKTECONSTRUCTIE)
            .draagkrachtig(UPDATED_DRAAGKRACHTIG)
            .formaat(UPDATED_FORMAAT)
            .fysiekvoorkomenimgeo(UPDATED_FYSIEKVOORKOMENIMGEO)
            .geluidsreducerend(UPDATED_GELUIDSREDUCEREND)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .jaarpraktischeinde(UPDATED_JAARPRAKTISCHEINDE)
            .kleur(UPDATED_KLEUR)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .lengtekunstgras(UPDATED_LENGTEKUNSTGRAS)
            .lengtevoegen(UPDATED_LENGTEVOEGEN)
            .levensduur(UPDATED_LEVENSDUUR)
            .materiaal(UPDATED_MATERIAAL)
            .maximalevalhoogte(UPDATED_MAXIMALEVALHOOGTE)
            .omtrek(UPDATED_OMTREK)
            .ondergrondcode(UPDATED_ONDERGRONDCODE)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .optalud(UPDATED_OPTALUD)
            .plaatsorientatie(UPDATED_PLAATSORIENTATIE)
            .prijsaanschaf(UPDATED_PRIJSAANSCHAF)
            .rijstrook(UPDATED_RIJSTROOK)
            .soortvoeg(UPDATED_SOORTVOEG)
            .toelichtinggemengdebestrating(UPDATED_TOELICHTINGGEMENGDEBESTRATING)
            .type(UPDATED_TYPE)
            .typeconstructie(UPDATED_TYPECONSTRUCTIE)
            .typefundering(UPDATED_TYPEFUNDERING)
            .typeplus(UPDATED_TYPEPLUS)
            .typeplus2(UPDATED_TYPEPLUS_2)
            .typerijstrook(UPDATED_TYPERIJSTROOK)
            .typevoeg(UPDATED_TYPEVOEG)
            .typevoegvulling(UPDATED_TYPEVOEGVULLING)
            .vegen(UPDATED_VEGEN)
            .verhardingsobjectconstructielaag(UPDATED_VERHARDINGSOBJECTCONSTRUCTIELAAG)
            .verhardingsobjectmodaliteit(UPDATED_VERHARDINGSOBJECTMODALITEIT)
            .verhardingsobjectrand(UPDATED_VERHARDINGSOBJECTRAND)
            .verhardingsobjectwegfunctie(UPDATED_VERHARDINGSOBJECTWEGFUNCTIE)
            .verhoogdeligging(UPDATED_VERHOOGDELIGGING)
            .vulmateriaalkunstgras(UPDATED_VULMATERIAALKUNSTGRAS)
            .waterdoorlatendheid(UPDATED_WATERDOORLATENDHEID)
            .wegas(UPDATED_WEGAS)
            .wegcategoriedv(UPDATED_WEGCATEGORIEDV)
            .wegcategoriedvplus(UPDATED_WEGCATEGORIEDVPLUS)
            .wegnummer(UPDATED_WEGNUMMER)
            .wegtypebestaand(UPDATED_WEGTYPEBESTAAND)
            .wegvak(UPDATED_WEGVAK)
            .wegvaknummer(UPDATED_WEGVAKNUMMER);

        restVerhardingsobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerhardingsobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerhardingsobject))
            )
            .andExpect(status().isOk());

        // Validate the Verhardingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerhardingsobjectToMatchAllProperties(updatedVerhardingsobject);
    }

    @Test
    @Transactional
    void putNonExistingVerhardingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verhardingsobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerhardingsobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verhardingsobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verhardingsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verhardingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerhardingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verhardingsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerhardingsobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verhardingsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verhardingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerhardingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verhardingsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerhardingsobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verhardingsobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verhardingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerhardingsobjectWithPatch() throws Exception {
        // Initialize the database
        verhardingsobjectRepository.saveAndFlush(verhardingsobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verhardingsobject using partial update
        Verhardingsobject partialUpdatedVerhardingsobject = new Verhardingsobject();
        partialUpdatedVerhardingsobject.setId(verhardingsobject.getId());

        partialUpdatedVerhardingsobject
            .aantaldeklagen(UPDATED_AANTALDEKLAGEN)
            .aantalonderlagen(UPDATED_AANTALONDERLAGEN)
            .aantaltussenlagen(UPDATED_AANTALTUSSENLAGEN)
            .bergendvermogen(UPDATED_BERGENDVERMOGEN)
            .bgtfysiekvoorkomen(UPDATED_BGTFYSIEKVOORKOMEN)
            .breedte(UPDATED_BREEDTE)
            .dikteconstructie(UPDATED_DIKTECONSTRUCTIE)
            .draagkrachtig(UPDATED_DRAAGKRACHTIG)
            .fysiekvoorkomenimgeo(UPDATED_FYSIEKVOORKOMENIMGEO)
            .geluidsreducerend(UPDATED_GELUIDSREDUCEREND)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .jaarpraktischeinde(UPDATED_JAARPRAKTISCHEINDE)
            .lengte(UPDATED_LENGTE)
            .levensduur(UPDATED_LEVENSDUUR)
            .materiaal(UPDATED_MATERIAAL)
            .maximalevalhoogte(UPDATED_MAXIMALEVALHOOGTE)
            .omtrek(UPDATED_OMTREK)
            .ondergrondcode(UPDATED_ONDERGRONDCODE)
            .plaatsorientatie(UPDATED_PLAATSORIENTATIE)
            .prijsaanschaf(UPDATED_PRIJSAANSCHAF)
            .typeconstructie(UPDATED_TYPECONSTRUCTIE)
            .typevoeg(UPDATED_TYPEVOEG)
            .vulmateriaalkunstgras(UPDATED_VULMATERIAALKUNSTGRAS)
            .waterdoorlatendheid(UPDATED_WATERDOORLATENDHEID)
            .wegnummer(UPDATED_WEGNUMMER)
            .wegtypebestaand(UPDATED_WEGTYPEBESTAAND)
            .wegvaknummer(UPDATED_WEGVAKNUMMER);

        restVerhardingsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerhardingsobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerhardingsobject))
            )
            .andExpect(status().isOk());

        // Validate the Verhardingsobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerhardingsobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerhardingsobject, verhardingsobject),
            getPersistedVerhardingsobject(verhardingsobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerhardingsobjectWithPatch() throws Exception {
        // Initialize the database
        verhardingsobjectRepository.saveAndFlush(verhardingsobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verhardingsobject using partial update
        Verhardingsobject partialUpdatedVerhardingsobject = new Verhardingsobject();
        partialUpdatedVerhardingsobject.setId(verhardingsobject.getId());

        partialUpdatedVerhardingsobject
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .aanofvrijliggend(UPDATED_AANOFVRIJLIGGEND)
            .aantaldeklagen(UPDATED_AANTALDEKLAGEN)
            .aantalonderlagen(UPDATED_AANTALONDERLAGEN)
            .aantaltussenlagen(UPDATED_AANTALTUSSENLAGEN)
            .afmeting(UPDATED_AFMETING)
            .belasting(UPDATED_BELASTING)
            .bergendvermogen(UPDATED_BERGENDVERMOGEN)
            .bgtfysiekvoorkomen(UPDATED_BGTFYSIEKVOORKOMEN)
            .breedte(UPDATED_BREEDTE)
            .dikteconstructie(UPDATED_DIKTECONSTRUCTIE)
            .draagkrachtig(UPDATED_DRAAGKRACHTIG)
            .formaat(UPDATED_FORMAAT)
            .fysiekvoorkomenimgeo(UPDATED_FYSIEKVOORKOMENIMGEO)
            .geluidsreducerend(UPDATED_GELUIDSREDUCEREND)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .jaarpraktischeinde(UPDATED_JAARPRAKTISCHEINDE)
            .kleur(UPDATED_KLEUR)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .lengtekunstgras(UPDATED_LENGTEKUNSTGRAS)
            .lengtevoegen(UPDATED_LENGTEVOEGEN)
            .levensduur(UPDATED_LEVENSDUUR)
            .materiaal(UPDATED_MATERIAAL)
            .maximalevalhoogte(UPDATED_MAXIMALEVALHOOGTE)
            .omtrek(UPDATED_OMTREK)
            .ondergrondcode(UPDATED_ONDERGRONDCODE)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .optalud(UPDATED_OPTALUD)
            .plaatsorientatie(UPDATED_PLAATSORIENTATIE)
            .prijsaanschaf(UPDATED_PRIJSAANSCHAF)
            .rijstrook(UPDATED_RIJSTROOK)
            .soortvoeg(UPDATED_SOORTVOEG)
            .toelichtinggemengdebestrating(UPDATED_TOELICHTINGGEMENGDEBESTRATING)
            .type(UPDATED_TYPE)
            .typeconstructie(UPDATED_TYPECONSTRUCTIE)
            .typefundering(UPDATED_TYPEFUNDERING)
            .typeplus(UPDATED_TYPEPLUS)
            .typeplus2(UPDATED_TYPEPLUS_2)
            .typerijstrook(UPDATED_TYPERIJSTROOK)
            .typevoeg(UPDATED_TYPEVOEG)
            .typevoegvulling(UPDATED_TYPEVOEGVULLING)
            .vegen(UPDATED_VEGEN)
            .verhardingsobjectconstructielaag(UPDATED_VERHARDINGSOBJECTCONSTRUCTIELAAG)
            .verhardingsobjectmodaliteit(UPDATED_VERHARDINGSOBJECTMODALITEIT)
            .verhardingsobjectrand(UPDATED_VERHARDINGSOBJECTRAND)
            .verhardingsobjectwegfunctie(UPDATED_VERHARDINGSOBJECTWEGFUNCTIE)
            .verhoogdeligging(UPDATED_VERHOOGDELIGGING)
            .vulmateriaalkunstgras(UPDATED_VULMATERIAALKUNSTGRAS)
            .waterdoorlatendheid(UPDATED_WATERDOORLATENDHEID)
            .wegas(UPDATED_WEGAS)
            .wegcategoriedv(UPDATED_WEGCATEGORIEDV)
            .wegcategoriedvplus(UPDATED_WEGCATEGORIEDVPLUS)
            .wegnummer(UPDATED_WEGNUMMER)
            .wegtypebestaand(UPDATED_WEGTYPEBESTAAND)
            .wegvak(UPDATED_WEGVAK)
            .wegvaknummer(UPDATED_WEGVAKNUMMER);

        restVerhardingsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerhardingsobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerhardingsobject))
            )
            .andExpect(status().isOk());

        // Validate the Verhardingsobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerhardingsobjectUpdatableFieldsEquals(
            partialUpdatedVerhardingsobject,
            getPersistedVerhardingsobject(partialUpdatedVerhardingsobject)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVerhardingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verhardingsobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerhardingsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verhardingsobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verhardingsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verhardingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerhardingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verhardingsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerhardingsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verhardingsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verhardingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerhardingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verhardingsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerhardingsobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verhardingsobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verhardingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerhardingsobject() throws Exception {
        // Initialize the database
        verhardingsobjectRepository.saveAndFlush(verhardingsobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verhardingsobject
        restVerhardingsobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, verhardingsobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verhardingsobjectRepository.count();
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

    protected Verhardingsobject getPersistedVerhardingsobject(Verhardingsobject verhardingsobject) {
        return verhardingsobjectRepository.findById(verhardingsobject.getId()).orElseThrow();
    }

    protected void assertPersistedVerhardingsobjectToMatchAllProperties(Verhardingsobject expectedVerhardingsobject) {
        assertVerhardingsobjectAllPropertiesEquals(expectedVerhardingsobject, getPersistedVerhardingsobject(expectedVerhardingsobject));
    }

    protected void assertPersistedVerhardingsobjectToMatchUpdatableProperties(Verhardingsobject expectedVerhardingsobject) {
        assertVerhardingsobjectAllUpdatablePropertiesEquals(
            expectedVerhardingsobject,
            getPersistedVerhardingsobject(expectedVerhardingsobject)
        );
    }
}
