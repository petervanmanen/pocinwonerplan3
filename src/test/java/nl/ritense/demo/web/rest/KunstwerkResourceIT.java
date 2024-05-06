package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KunstwerkAsserts.*;
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
import nl.ritense.demo.domain.Kunstwerk;
import nl.ritense.demo.repository.KunstwerkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KunstwerkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KunstwerkResourceIT {

    private static final String DEFAULT_AANLEGHOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_AANLEGHOOGTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ANTIGRAFFITIVOORZIENING = false;
    private static final Boolean UPDATED_ANTIGRAFFITIVOORZIENING = true;

    private static final String DEFAULT_BEREIKBAARHEID = "AAAAAAAAAA";
    private static final String UPDATED_BEREIKBAARHEID = "BBBBBBBBBB";

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_CONSTRUCTIETYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONSTRUCTIETYPE = "BBBBBBBBBB";

    private static final String DEFAULT_GEWICHT = "AAAAAAAAAA";
    private static final String UPDATED_GEWICHT = "BBBBBBBBBB";

    private static final String DEFAULT_HOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_INSTALLATEUR = "AAAAAAAAAA";
    private static final String UPDATED_INSTALLATEUR = "BBBBBBBBBB";

    private static final String DEFAULT_JAARCONSERVEREN = "AAAAAAAAAA";
    private static final String UPDATED_JAARCONSERVEREN = "BBBBBBBBBB";

    private static final String DEFAULT_JAARONDERHOUDUITGEVOERD = "AAAAAAAAAA";
    private static final String UPDATED_JAARONDERHOUDUITGEVOERD = "BBBBBBBBBB";

    private static final String DEFAULT_JAARRENOVATIE = "AAAAAAAAAA";
    private static final String UPDATED_JAARRENOVATIE = "BBBBBBBBBB";

    private static final String DEFAULT_JAARVERVANGING = "AAAAAAAAAA";
    private static final String UPDATED_JAARVERVANGING = "BBBBBBBBBB";

    private static final String DEFAULT_KILOMETRERINGBEGIN = "AAAAAAAAAA";
    private static final String UPDATED_KILOMETRERINGBEGIN = "BBBBBBBBBB";

    private static final String DEFAULT_KILOMETRERINGEINDE = "AAAAAAAAAA";
    private static final String UPDATED_KILOMETRERINGEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_KLEUR = "AAAAAAAAAA";
    private static final String UPDATED_KLEUR = "BBBBBBBBBB";

    private static final String DEFAULT_KUNSTWERKBEREIKBAARHEIDPLUS = "AAAAAAAAAA";
    private static final String UPDATED_KUNSTWERKBEREIKBAARHEIDPLUS = "BBBBBBBBBB";

    private static final String DEFAULT_KUNSTWERKMATERIAAL = "AAAAAAAAAA";
    private static final String UPDATED_KUNSTWERKMATERIAAL = "BBBBBBBBBB";

    private static final String DEFAULT_KWALITEITSNIVEAUACTUEEL = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEITSNIVEAUACTUEEL = "BBBBBBBBBB";

    private static final String DEFAULT_KWALITEITSNIVEAUGEWENST = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEITSNIVEAUGEWENST = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTE = "AAAAAAAAAA";
    private static final String UPDATED_LENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_LEVERANCIER = "AAAAAAAAAA";
    private static final String UPDATED_LEVERANCIER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LOOPRICHEL = false;
    private static final Boolean UPDATED_LOOPRICHEL = true;

    private static final String DEFAULT_MINIMUMCONDITIESCORE = "AAAAAAAAAA";
    private static final String UPDATED_MINIMUMCONDITIESCORE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MONUMENT = false;
    private static final Boolean UPDATED_MONUMENT = true;

    private static final String DEFAULT_MONUMENTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_MONUMENTNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_EOBJECTNAAM = "AAAAAAAAAA";
    private static final String UPDATED_EOBJECTNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_EOBJECTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_EOBJECTNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERHOUDSREGIME = "AAAAAAAAAA";
    private static final String UPDATED_ONDERHOUDSREGIME = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_ORIENTATIE = "AAAAAAAAAA";
    private static final String UPDATED_ORIENTATIE = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNISCHELEVENSDUUR = "AAAAAAAAAA";
    private static final String UPDATED_TECHNISCHELEVENSDUUR = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEFUNDERING = "AAAAAAAAAA";
    private static final String UPDATED_TYPEFUNDERING = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEMONUMENT = "AAAAAAAAAA";
    private static final String UPDATED_TYPEMONUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_VERVANGINGSWAARDE = "AAAAAAAAAA";
    private static final String UPDATED_VERVANGINGSWAARDE = "BBBBBBBBBB";

    private static final String DEFAULT_WEGNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_WEGNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kunstwerks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KunstwerkRepository kunstwerkRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKunstwerkMockMvc;

    private Kunstwerk kunstwerk;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kunstwerk createEntity(EntityManager em) {
        Kunstwerk kunstwerk = new Kunstwerk()
            .aanleghoogte(DEFAULT_AANLEGHOOGTE)
            .antigraffitivoorziening(DEFAULT_ANTIGRAFFITIVOORZIENING)
            .bereikbaarheid(DEFAULT_BEREIKBAARHEID)
            .breedte(DEFAULT_BREEDTE)
            .constructietype(DEFAULT_CONSTRUCTIETYPE)
            .gewicht(DEFAULT_GEWICHT)
            .hoogte(DEFAULT_HOOGTE)
            .installateur(DEFAULT_INSTALLATEUR)
            .jaarconserveren(DEFAULT_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(DEFAULT_JAARONDERHOUDUITGEVOERD)
            .jaarrenovatie(DEFAULT_JAARRENOVATIE)
            .jaarvervanging(DEFAULT_JAARVERVANGING)
            .kilometreringbegin(DEFAULT_KILOMETRERINGBEGIN)
            .kilometreringeinde(DEFAULT_KILOMETRERINGEINDE)
            .kleur(DEFAULT_KLEUR)
            .kunstwerkbereikbaarheidplus(DEFAULT_KUNSTWERKBEREIKBAARHEIDPLUS)
            .kunstwerkmateriaal(DEFAULT_KUNSTWERKMATERIAAL)
            .kwaliteitsniveauactueel(DEFAULT_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(DEFAULT_KWALITEITSNIVEAUGEWENST)
            .lengte(DEFAULT_LENGTE)
            .leverancier(DEFAULT_LEVERANCIER)
            .looprichel(DEFAULT_LOOPRICHEL)
            .minimumconditiescore(DEFAULT_MINIMUMCONDITIESCORE)
            .monument(DEFAULT_MONUMENT)
            .monumentnummer(DEFAULT_MONUMENTNUMMER)
            .eobjectnaam(DEFAULT_EOBJECTNAAM)
            .eobjectnummer(DEFAULT_EOBJECTNUMMER)
            .onderhoudsregime(DEFAULT_ONDERHOUDSREGIME)
            .oppervlakte(DEFAULT_OPPERVLAKTE)
            .orientatie(DEFAULT_ORIENTATIE)
            .technischelevensduur(DEFAULT_TECHNISCHELEVENSDUUR)
            .typefundering(DEFAULT_TYPEFUNDERING)
            .typemonument(DEFAULT_TYPEMONUMENT)
            .vervangingswaarde(DEFAULT_VERVANGINGSWAARDE)
            .wegnummer(DEFAULT_WEGNUMMER);
        return kunstwerk;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kunstwerk createUpdatedEntity(EntityManager em) {
        Kunstwerk kunstwerk = new Kunstwerk()
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .antigraffitivoorziening(UPDATED_ANTIGRAFFITIVOORZIENING)
            .bereikbaarheid(UPDATED_BEREIKBAARHEID)
            .breedte(UPDATED_BREEDTE)
            .constructietype(UPDATED_CONSTRUCTIETYPE)
            .gewicht(UPDATED_GEWICHT)
            .hoogte(UPDATED_HOOGTE)
            .installateur(UPDATED_INSTALLATEUR)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .jaarrenovatie(UPDATED_JAARRENOVATIE)
            .jaarvervanging(UPDATED_JAARVERVANGING)
            .kilometreringbegin(UPDATED_KILOMETRERINGBEGIN)
            .kilometreringeinde(UPDATED_KILOMETRERINGEINDE)
            .kleur(UPDATED_KLEUR)
            .kunstwerkbereikbaarheidplus(UPDATED_KUNSTWERKBEREIKBAARHEIDPLUS)
            .kunstwerkmateriaal(UPDATED_KUNSTWERKMATERIAAL)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .looprichel(UPDATED_LOOPRICHEL)
            .minimumconditiescore(UPDATED_MINIMUMCONDITIESCORE)
            .monument(UPDATED_MONUMENT)
            .monumentnummer(UPDATED_MONUMENTNUMMER)
            .eobjectnaam(UPDATED_EOBJECTNAAM)
            .eobjectnummer(UPDATED_EOBJECTNUMMER)
            .onderhoudsregime(UPDATED_ONDERHOUDSREGIME)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .orientatie(UPDATED_ORIENTATIE)
            .technischelevensduur(UPDATED_TECHNISCHELEVENSDUUR)
            .typefundering(UPDATED_TYPEFUNDERING)
            .typemonument(UPDATED_TYPEMONUMENT)
            .vervangingswaarde(UPDATED_VERVANGINGSWAARDE)
            .wegnummer(UPDATED_WEGNUMMER);
        return kunstwerk;
    }

    @BeforeEach
    public void initTest() {
        kunstwerk = createEntity(em);
    }

    @Test
    @Transactional
    void createKunstwerk() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kunstwerk
        var returnedKunstwerk = om.readValue(
            restKunstwerkMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kunstwerk)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kunstwerk.class
        );

        // Validate the Kunstwerk in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKunstwerkUpdatableFieldsEquals(returnedKunstwerk, getPersistedKunstwerk(returnedKunstwerk));
    }

    @Test
    @Transactional
    void createKunstwerkWithExistingId() throws Exception {
        // Create the Kunstwerk with an existing ID
        kunstwerk.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKunstwerkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kunstwerk)))
            .andExpect(status().isBadRequest());

        // Validate the Kunstwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKunstwerks() throws Exception {
        // Initialize the database
        kunstwerkRepository.saveAndFlush(kunstwerk);

        // Get all the kunstwerkList
        restKunstwerkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kunstwerk.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanleghoogte").value(hasItem(DEFAULT_AANLEGHOOGTE)))
            .andExpect(jsonPath("$.[*].antigraffitivoorziening").value(hasItem(DEFAULT_ANTIGRAFFITIVOORZIENING.booleanValue())))
            .andExpect(jsonPath("$.[*].bereikbaarheid").value(hasItem(DEFAULT_BEREIKBAARHEID)))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].constructietype").value(hasItem(DEFAULT_CONSTRUCTIETYPE)))
            .andExpect(jsonPath("$.[*].gewicht").value(hasItem(DEFAULT_GEWICHT)))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].installateur").value(hasItem(DEFAULT_INSTALLATEUR)))
            .andExpect(jsonPath("$.[*].jaarconserveren").value(hasItem(DEFAULT_JAARCONSERVEREN)))
            .andExpect(jsonPath("$.[*].jaaronderhouduitgevoerd").value(hasItem(DEFAULT_JAARONDERHOUDUITGEVOERD)))
            .andExpect(jsonPath("$.[*].jaarrenovatie").value(hasItem(DEFAULT_JAARRENOVATIE)))
            .andExpect(jsonPath("$.[*].jaarvervanging").value(hasItem(DEFAULT_JAARVERVANGING)))
            .andExpect(jsonPath("$.[*].kilometreringbegin").value(hasItem(DEFAULT_KILOMETRERINGBEGIN)))
            .andExpect(jsonPath("$.[*].kilometreringeinde").value(hasItem(DEFAULT_KILOMETRERINGEINDE)))
            .andExpect(jsonPath("$.[*].kleur").value(hasItem(DEFAULT_KLEUR)))
            .andExpect(jsonPath("$.[*].kunstwerkbereikbaarheidplus").value(hasItem(DEFAULT_KUNSTWERKBEREIKBAARHEIDPLUS)))
            .andExpect(jsonPath("$.[*].kunstwerkmateriaal").value(hasItem(DEFAULT_KUNSTWERKMATERIAAL)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveauactueel").value(hasItem(DEFAULT_KWALITEITSNIVEAUACTUEEL)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveaugewenst").value(hasItem(DEFAULT_KWALITEITSNIVEAUGEWENST)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].leverancier").value(hasItem(DEFAULT_LEVERANCIER)))
            .andExpect(jsonPath("$.[*].looprichel").value(hasItem(DEFAULT_LOOPRICHEL.booleanValue())))
            .andExpect(jsonPath("$.[*].minimumconditiescore").value(hasItem(DEFAULT_MINIMUMCONDITIESCORE)))
            .andExpect(jsonPath("$.[*].monument").value(hasItem(DEFAULT_MONUMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].monumentnummer").value(hasItem(DEFAULT_MONUMENTNUMMER)))
            .andExpect(jsonPath("$.[*].eobjectnaam").value(hasItem(DEFAULT_EOBJECTNAAM)))
            .andExpect(jsonPath("$.[*].eobjectnummer").value(hasItem(DEFAULT_EOBJECTNUMMER)))
            .andExpect(jsonPath("$.[*].onderhoudsregime").value(hasItem(DEFAULT_ONDERHOUDSREGIME)))
            .andExpect(jsonPath("$.[*].oppervlakte").value(hasItem(DEFAULT_OPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].orientatie").value(hasItem(DEFAULT_ORIENTATIE)))
            .andExpect(jsonPath("$.[*].technischelevensduur").value(hasItem(DEFAULT_TECHNISCHELEVENSDUUR)))
            .andExpect(jsonPath("$.[*].typefundering").value(hasItem(DEFAULT_TYPEFUNDERING)))
            .andExpect(jsonPath("$.[*].typemonument").value(hasItem(DEFAULT_TYPEMONUMENT)))
            .andExpect(jsonPath("$.[*].vervangingswaarde").value(hasItem(DEFAULT_VERVANGINGSWAARDE)))
            .andExpect(jsonPath("$.[*].wegnummer").value(hasItem(DEFAULT_WEGNUMMER)));
    }

    @Test
    @Transactional
    void getKunstwerk() throws Exception {
        // Initialize the database
        kunstwerkRepository.saveAndFlush(kunstwerk);

        // Get the kunstwerk
        restKunstwerkMockMvc
            .perform(get(ENTITY_API_URL_ID, kunstwerk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kunstwerk.getId().intValue()))
            .andExpect(jsonPath("$.aanleghoogte").value(DEFAULT_AANLEGHOOGTE))
            .andExpect(jsonPath("$.antigraffitivoorziening").value(DEFAULT_ANTIGRAFFITIVOORZIENING.booleanValue()))
            .andExpect(jsonPath("$.bereikbaarheid").value(DEFAULT_BEREIKBAARHEID))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.constructietype").value(DEFAULT_CONSTRUCTIETYPE))
            .andExpect(jsonPath("$.gewicht").value(DEFAULT_GEWICHT))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.installateur").value(DEFAULT_INSTALLATEUR))
            .andExpect(jsonPath("$.jaarconserveren").value(DEFAULT_JAARCONSERVEREN))
            .andExpect(jsonPath("$.jaaronderhouduitgevoerd").value(DEFAULT_JAARONDERHOUDUITGEVOERD))
            .andExpect(jsonPath("$.jaarrenovatie").value(DEFAULT_JAARRENOVATIE))
            .andExpect(jsonPath("$.jaarvervanging").value(DEFAULT_JAARVERVANGING))
            .andExpect(jsonPath("$.kilometreringbegin").value(DEFAULT_KILOMETRERINGBEGIN))
            .andExpect(jsonPath("$.kilometreringeinde").value(DEFAULT_KILOMETRERINGEINDE))
            .andExpect(jsonPath("$.kleur").value(DEFAULT_KLEUR))
            .andExpect(jsonPath("$.kunstwerkbereikbaarheidplus").value(DEFAULT_KUNSTWERKBEREIKBAARHEIDPLUS))
            .andExpect(jsonPath("$.kunstwerkmateriaal").value(DEFAULT_KUNSTWERKMATERIAAL))
            .andExpect(jsonPath("$.kwaliteitsniveauactueel").value(DEFAULT_KWALITEITSNIVEAUACTUEEL))
            .andExpect(jsonPath("$.kwaliteitsniveaugewenst").value(DEFAULT_KWALITEITSNIVEAUGEWENST))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.leverancier").value(DEFAULT_LEVERANCIER))
            .andExpect(jsonPath("$.looprichel").value(DEFAULT_LOOPRICHEL.booleanValue()))
            .andExpect(jsonPath("$.minimumconditiescore").value(DEFAULT_MINIMUMCONDITIESCORE))
            .andExpect(jsonPath("$.monument").value(DEFAULT_MONUMENT.booleanValue()))
            .andExpect(jsonPath("$.monumentnummer").value(DEFAULT_MONUMENTNUMMER))
            .andExpect(jsonPath("$.eobjectnaam").value(DEFAULT_EOBJECTNAAM))
            .andExpect(jsonPath("$.eobjectnummer").value(DEFAULT_EOBJECTNUMMER))
            .andExpect(jsonPath("$.onderhoudsregime").value(DEFAULT_ONDERHOUDSREGIME))
            .andExpect(jsonPath("$.oppervlakte").value(DEFAULT_OPPERVLAKTE))
            .andExpect(jsonPath("$.orientatie").value(DEFAULT_ORIENTATIE))
            .andExpect(jsonPath("$.technischelevensduur").value(DEFAULT_TECHNISCHELEVENSDUUR))
            .andExpect(jsonPath("$.typefundering").value(DEFAULT_TYPEFUNDERING))
            .andExpect(jsonPath("$.typemonument").value(DEFAULT_TYPEMONUMENT))
            .andExpect(jsonPath("$.vervangingswaarde").value(DEFAULT_VERVANGINGSWAARDE))
            .andExpect(jsonPath("$.wegnummer").value(DEFAULT_WEGNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingKunstwerk() throws Exception {
        // Get the kunstwerk
        restKunstwerkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKunstwerk() throws Exception {
        // Initialize the database
        kunstwerkRepository.saveAndFlush(kunstwerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kunstwerk
        Kunstwerk updatedKunstwerk = kunstwerkRepository.findById(kunstwerk.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKunstwerk are not directly saved in db
        em.detach(updatedKunstwerk);
        updatedKunstwerk
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .antigraffitivoorziening(UPDATED_ANTIGRAFFITIVOORZIENING)
            .bereikbaarheid(UPDATED_BEREIKBAARHEID)
            .breedte(UPDATED_BREEDTE)
            .constructietype(UPDATED_CONSTRUCTIETYPE)
            .gewicht(UPDATED_GEWICHT)
            .hoogte(UPDATED_HOOGTE)
            .installateur(UPDATED_INSTALLATEUR)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .jaarrenovatie(UPDATED_JAARRENOVATIE)
            .jaarvervanging(UPDATED_JAARVERVANGING)
            .kilometreringbegin(UPDATED_KILOMETRERINGBEGIN)
            .kilometreringeinde(UPDATED_KILOMETRERINGEINDE)
            .kleur(UPDATED_KLEUR)
            .kunstwerkbereikbaarheidplus(UPDATED_KUNSTWERKBEREIKBAARHEIDPLUS)
            .kunstwerkmateriaal(UPDATED_KUNSTWERKMATERIAAL)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .looprichel(UPDATED_LOOPRICHEL)
            .minimumconditiescore(UPDATED_MINIMUMCONDITIESCORE)
            .monument(UPDATED_MONUMENT)
            .monumentnummer(UPDATED_MONUMENTNUMMER)
            .eobjectnaam(UPDATED_EOBJECTNAAM)
            .eobjectnummer(UPDATED_EOBJECTNUMMER)
            .onderhoudsregime(UPDATED_ONDERHOUDSREGIME)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .orientatie(UPDATED_ORIENTATIE)
            .technischelevensduur(UPDATED_TECHNISCHELEVENSDUUR)
            .typefundering(UPDATED_TYPEFUNDERING)
            .typemonument(UPDATED_TYPEMONUMENT)
            .vervangingswaarde(UPDATED_VERVANGINGSWAARDE)
            .wegnummer(UPDATED_WEGNUMMER);

        restKunstwerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKunstwerk.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKunstwerk))
            )
            .andExpect(status().isOk());

        // Validate the Kunstwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKunstwerkToMatchAllProperties(updatedKunstwerk);
    }

    @Test
    @Transactional
    void putNonExistingKunstwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kunstwerk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKunstwerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kunstwerk.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kunstwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kunstwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKunstwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kunstwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKunstwerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kunstwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kunstwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKunstwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kunstwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKunstwerkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kunstwerk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kunstwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKunstwerkWithPatch() throws Exception {
        // Initialize the database
        kunstwerkRepository.saveAndFlush(kunstwerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kunstwerk using partial update
        Kunstwerk partialUpdatedKunstwerk = new Kunstwerk();
        partialUpdatedKunstwerk.setId(kunstwerk.getId());

        partialUpdatedKunstwerk
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .bereikbaarheid(UPDATED_BEREIKBAARHEID)
            .breedte(UPDATED_BREEDTE)
            .gewicht(UPDATED_GEWICHT)
            .hoogte(UPDATED_HOOGTE)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .leverancier(UPDATED_LEVERANCIER)
            .looprichel(UPDATED_LOOPRICHEL)
            .monument(UPDATED_MONUMENT)
            .monumentnummer(UPDATED_MONUMENTNUMMER)
            .eobjectnaam(UPDATED_EOBJECTNAAM)
            .onderhoudsregime(UPDATED_ONDERHOUDSREGIME)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .orientatie(UPDATED_ORIENTATIE)
            .typefundering(UPDATED_TYPEFUNDERING)
            .typemonument(UPDATED_TYPEMONUMENT)
            .vervangingswaarde(UPDATED_VERVANGINGSWAARDE)
            .wegnummer(UPDATED_WEGNUMMER);

        restKunstwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKunstwerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKunstwerk))
            )
            .andExpect(status().isOk());

        // Validate the Kunstwerk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKunstwerkUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKunstwerk, kunstwerk),
            getPersistedKunstwerk(kunstwerk)
        );
    }

    @Test
    @Transactional
    void fullUpdateKunstwerkWithPatch() throws Exception {
        // Initialize the database
        kunstwerkRepository.saveAndFlush(kunstwerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kunstwerk using partial update
        Kunstwerk partialUpdatedKunstwerk = new Kunstwerk();
        partialUpdatedKunstwerk.setId(kunstwerk.getId());

        partialUpdatedKunstwerk
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .antigraffitivoorziening(UPDATED_ANTIGRAFFITIVOORZIENING)
            .bereikbaarheid(UPDATED_BEREIKBAARHEID)
            .breedte(UPDATED_BREEDTE)
            .constructietype(UPDATED_CONSTRUCTIETYPE)
            .gewicht(UPDATED_GEWICHT)
            .hoogte(UPDATED_HOOGTE)
            .installateur(UPDATED_INSTALLATEUR)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .jaarrenovatie(UPDATED_JAARRENOVATIE)
            .jaarvervanging(UPDATED_JAARVERVANGING)
            .kilometreringbegin(UPDATED_KILOMETRERINGBEGIN)
            .kilometreringeinde(UPDATED_KILOMETRERINGEINDE)
            .kleur(UPDATED_KLEUR)
            .kunstwerkbereikbaarheidplus(UPDATED_KUNSTWERKBEREIKBAARHEIDPLUS)
            .kunstwerkmateriaal(UPDATED_KUNSTWERKMATERIAAL)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .looprichel(UPDATED_LOOPRICHEL)
            .minimumconditiescore(UPDATED_MINIMUMCONDITIESCORE)
            .monument(UPDATED_MONUMENT)
            .monumentnummer(UPDATED_MONUMENTNUMMER)
            .eobjectnaam(UPDATED_EOBJECTNAAM)
            .eobjectnummer(UPDATED_EOBJECTNUMMER)
            .onderhoudsregime(UPDATED_ONDERHOUDSREGIME)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .orientatie(UPDATED_ORIENTATIE)
            .technischelevensduur(UPDATED_TECHNISCHELEVENSDUUR)
            .typefundering(UPDATED_TYPEFUNDERING)
            .typemonument(UPDATED_TYPEMONUMENT)
            .vervangingswaarde(UPDATED_VERVANGINGSWAARDE)
            .wegnummer(UPDATED_WEGNUMMER);

        restKunstwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKunstwerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKunstwerk))
            )
            .andExpect(status().isOk());

        // Validate the Kunstwerk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKunstwerkUpdatableFieldsEquals(partialUpdatedKunstwerk, getPersistedKunstwerk(partialUpdatedKunstwerk));
    }

    @Test
    @Transactional
    void patchNonExistingKunstwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kunstwerk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKunstwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kunstwerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kunstwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kunstwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKunstwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kunstwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKunstwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kunstwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kunstwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKunstwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kunstwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKunstwerkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kunstwerk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kunstwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKunstwerk() throws Exception {
        // Initialize the database
        kunstwerkRepository.saveAndFlush(kunstwerk);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kunstwerk
        restKunstwerkMockMvc
            .perform(delete(ENTITY_API_URL_ID, kunstwerk.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kunstwerkRepository.count();
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

    protected Kunstwerk getPersistedKunstwerk(Kunstwerk kunstwerk) {
        return kunstwerkRepository.findById(kunstwerk.getId()).orElseThrow();
    }

    protected void assertPersistedKunstwerkToMatchAllProperties(Kunstwerk expectedKunstwerk) {
        assertKunstwerkAllPropertiesEquals(expectedKunstwerk, getPersistedKunstwerk(expectedKunstwerk));
    }

    protected void assertPersistedKunstwerkToMatchUpdatableProperties(Kunstwerk expectedKunstwerk) {
        assertKunstwerkAllUpdatablePropertiesEquals(expectedKunstwerk, getPersistedKunstwerk(expectedKunstwerk));
    }
}
