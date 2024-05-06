package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OverbruggingsobjectAsserts.*;
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
import nl.ritense.demo.domain.Overbruggingsobject;
import nl.ritense.demo.repository.OverbruggingsobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OverbruggingsobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OverbruggingsobjectResourceIT {

    private static final String DEFAULT_AANLEGHOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_AANLEGHOOGTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ANTIGRAFFITIVOORZIENING = false;
    private static final Boolean UPDATED_ANTIGRAFFITIVOORZIENING = true;

    private static final String DEFAULT_BEREIKBAARHEID = "AAAAAAAAAA";
    private static final String UPDATED_BEREIKBAARHEID = "BBBBBBBBBB";

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

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

    private static final String DEFAULT_KLEUR = "AAAAAAAAAA";
    private static final String UPDATED_KLEUR = "BBBBBBBBBB";

    private static final String DEFAULT_KWALITEITSNIVEAUACTUEEL = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEITSNIVEAUACTUEEL = "BBBBBBBBBB";

    private static final String DEFAULT_KWALITEITSNIVEAUGEWENST = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEITSNIVEAUGEWENST = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTE = "AAAAAAAAAA";
    private static final String UPDATED_LENGTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LOOPRICHEL = false;
    private static final Boolean UPDATED_LOOPRICHEL = true;

    private static final String DEFAULT_MINIMUMCONDITIESCORE = "AAAAAAAAAA";
    private static final String UPDATED_MINIMUMCONDITIESCORE = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERHOUDSREGIME = "AAAAAAAAAA";
    private static final String UPDATED_ONDERHOUDSREGIME = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_OVERBRUGGINGSOBJECTMATERIAAL = "AAAAAAAAAA";
    private static final String UPDATED_OVERBRUGGINGSOBJECTMATERIAAL = "BBBBBBBBBB";

    private static final String DEFAULT_OVERBRUGGINGSOBJECTMODALITEIT = "AAAAAAAAAA";
    private static final String UPDATED_OVERBRUGGINGSOBJECTMODALITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNISCHELEVENSDUUR = "AAAAAAAAAA";
    private static final String UPDATED_TECHNISCHELEVENSDUUR = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEFUNDERING = "AAAAAAAAAA";
    private static final String UPDATED_TYPEFUNDERING = "BBBBBBBBBB";

    private static final String DEFAULT_VERVANGINGSWAARDE = "AAAAAAAAAA";
    private static final String UPDATED_VERVANGINGSWAARDE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/overbruggingsobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OverbruggingsobjectRepository overbruggingsobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOverbruggingsobjectMockMvc;

    private Overbruggingsobject overbruggingsobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overbruggingsobject createEntity(EntityManager em) {
        Overbruggingsobject overbruggingsobject = new Overbruggingsobject()
            .aanleghoogte(DEFAULT_AANLEGHOOGTE)
            .antigraffitivoorziening(DEFAULT_ANTIGRAFFITIVOORZIENING)
            .bereikbaarheid(DEFAULT_BEREIKBAARHEID)
            .breedte(DEFAULT_BREEDTE)
            .hoogte(DEFAULT_HOOGTE)
            .installateur(DEFAULT_INSTALLATEUR)
            .jaarconserveren(DEFAULT_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(DEFAULT_JAARONDERHOUDUITGEVOERD)
            .jaarrenovatie(DEFAULT_JAARRENOVATIE)
            .jaarvervanging(DEFAULT_JAARVERVANGING)
            .kleur(DEFAULT_KLEUR)
            .kwaliteitsniveauactueel(DEFAULT_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(DEFAULT_KWALITEITSNIVEAUGEWENST)
            .lengte(DEFAULT_LENGTE)
            .looprichel(DEFAULT_LOOPRICHEL)
            .minimumconditiescore(DEFAULT_MINIMUMCONDITIESCORE)
            .onderhoudsregime(DEFAULT_ONDERHOUDSREGIME)
            .oppervlakte(DEFAULT_OPPERVLAKTE)
            .overbruggingsobjectmateriaal(DEFAULT_OVERBRUGGINGSOBJECTMATERIAAL)
            .overbruggingsobjectmodaliteit(DEFAULT_OVERBRUGGINGSOBJECTMODALITEIT)
            .technischelevensduur(DEFAULT_TECHNISCHELEVENSDUUR)
            .typefundering(DEFAULT_TYPEFUNDERING)
            .vervangingswaarde(DEFAULT_VERVANGINGSWAARDE);
        return overbruggingsobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overbruggingsobject createUpdatedEntity(EntityManager em) {
        Overbruggingsobject overbruggingsobject = new Overbruggingsobject()
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .antigraffitivoorziening(UPDATED_ANTIGRAFFITIVOORZIENING)
            .bereikbaarheid(UPDATED_BEREIKBAARHEID)
            .breedte(UPDATED_BREEDTE)
            .hoogte(UPDATED_HOOGTE)
            .installateur(UPDATED_INSTALLATEUR)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .jaarrenovatie(UPDATED_JAARRENOVATIE)
            .jaarvervanging(UPDATED_JAARVERVANGING)
            .kleur(UPDATED_KLEUR)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .looprichel(UPDATED_LOOPRICHEL)
            .minimumconditiescore(UPDATED_MINIMUMCONDITIESCORE)
            .onderhoudsregime(UPDATED_ONDERHOUDSREGIME)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .overbruggingsobjectmateriaal(UPDATED_OVERBRUGGINGSOBJECTMATERIAAL)
            .overbruggingsobjectmodaliteit(UPDATED_OVERBRUGGINGSOBJECTMODALITEIT)
            .technischelevensduur(UPDATED_TECHNISCHELEVENSDUUR)
            .typefundering(UPDATED_TYPEFUNDERING)
            .vervangingswaarde(UPDATED_VERVANGINGSWAARDE);
        return overbruggingsobject;
    }

    @BeforeEach
    public void initTest() {
        overbruggingsobject = createEntity(em);
    }

    @Test
    @Transactional
    void createOverbruggingsobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Overbruggingsobject
        var returnedOverbruggingsobject = om.readValue(
            restOverbruggingsobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overbruggingsobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Overbruggingsobject.class
        );

        // Validate the Overbruggingsobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOverbruggingsobjectUpdatableFieldsEquals(
            returnedOverbruggingsobject,
            getPersistedOverbruggingsobject(returnedOverbruggingsobject)
        );
    }

    @Test
    @Transactional
    void createOverbruggingsobjectWithExistingId() throws Exception {
        // Create the Overbruggingsobject with an existing ID
        overbruggingsobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOverbruggingsobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overbruggingsobject)))
            .andExpect(status().isBadRequest());

        // Validate the Overbruggingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOverbruggingsobjects() throws Exception {
        // Initialize the database
        overbruggingsobjectRepository.saveAndFlush(overbruggingsobject);

        // Get all the overbruggingsobjectList
        restOverbruggingsobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overbruggingsobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanleghoogte").value(hasItem(DEFAULT_AANLEGHOOGTE)))
            .andExpect(jsonPath("$.[*].antigraffitivoorziening").value(hasItem(DEFAULT_ANTIGRAFFITIVOORZIENING.booleanValue())))
            .andExpect(jsonPath("$.[*].bereikbaarheid").value(hasItem(DEFAULT_BEREIKBAARHEID)))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].installateur").value(hasItem(DEFAULT_INSTALLATEUR)))
            .andExpect(jsonPath("$.[*].jaarconserveren").value(hasItem(DEFAULT_JAARCONSERVEREN)))
            .andExpect(jsonPath("$.[*].jaaronderhouduitgevoerd").value(hasItem(DEFAULT_JAARONDERHOUDUITGEVOERD)))
            .andExpect(jsonPath("$.[*].jaarrenovatie").value(hasItem(DEFAULT_JAARRENOVATIE)))
            .andExpect(jsonPath("$.[*].jaarvervanging").value(hasItem(DEFAULT_JAARVERVANGING)))
            .andExpect(jsonPath("$.[*].kleur").value(hasItem(DEFAULT_KLEUR)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveauactueel").value(hasItem(DEFAULT_KWALITEITSNIVEAUACTUEEL)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveaugewenst").value(hasItem(DEFAULT_KWALITEITSNIVEAUGEWENST)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].looprichel").value(hasItem(DEFAULT_LOOPRICHEL.booleanValue())))
            .andExpect(jsonPath("$.[*].minimumconditiescore").value(hasItem(DEFAULT_MINIMUMCONDITIESCORE)))
            .andExpect(jsonPath("$.[*].onderhoudsregime").value(hasItem(DEFAULT_ONDERHOUDSREGIME)))
            .andExpect(jsonPath("$.[*].oppervlakte").value(hasItem(DEFAULT_OPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].overbruggingsobjectmateriaal").value(hasItem(DEFAULT_OVERBRUGGINGSOBJECTMATERIAAL)))
            .andExpect(jsonPath("$.[*].overbruggingsobjectmodaliteit").value(hasItem(DEFAULT_OVERBRUGGINGSOBJECTMODALITEIT)))
            .andExpect(jsonPath("$.[*].technischelevensduur").value(hasItem(DEFAULT_TECHNISCHELEVENSDUUR)))
            .andExpect(jsonPath("$.[*].typefundering").value(hasItem(DEFAULT_TYPEFUNDERING)))
            .andExpect(jsonPath("$.[*].vervangingswaarde").value(hasItem(DEFAULT_VERVANGINGSWAARDE)));
    }

    @Test
    @Transactional
    void getOverbruggingsobject() throws Exception {
        // Initialize the database
        overbruggingsobjectRepository.saveAndFlush(overbruggingsobject);

        // Get the overbruggingsobject
        restOverbruggingsobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, overbruggingsobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(overbruggingsobject.getId().intValue()))
            .andExpect(jsonPath("$.aanleghoogte").value(DEFAULT_AANLEGHOOGTE))
            .andExpect(jsonPath("$.antigraffitivoorziening").value(DEFAULT_ANTIGRAFFITIVOORZIENING.booleanValue()))
            .andExpect(jsonPath("$.bereikbaarheid").value(DEFAULT_BEREIKBAARHEID))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.installateur").value(DEFAULT_INSTALLATEUR))
            .andExpect(jsonPath("$.jaarconserveren").value(DEFAULT_JAARCONSERVEREN))
            .andExpect(jsonPath("$.jaaronderhouduitgevoerd").value(DEFAULT_JAARONDERHOUDUITGEVOERD))
            .andExpect(jsonPath("$.jaarrenovatie").value(DEFAULT_JAARRENOVATIE))
            .andExpect(jsonPath("$.jaarvervanging").value(DEFAULT_JAARVERVANGING))
            .andExpect(jsonPath("$.kleur").value(DEFAULT_KLEUR))
            .andExpect(jsonPath("$.kwaliteitsniveauactueel").value(DEFAULT_KWALITEITSNIVEAUACTUEEL))
            .andExpect(jsonPath("$.kwaliteitsniveaugewenst").value(DEFAULT_KWALITEITSNIVEAUGEWENST))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.looprichel").value(DEFAULT_LOOPRICHEL.booleanValue()))
            .andExpect(jsonPath("$.minimumconditiescore").value(DEFAULT_MINIMUMCONDITIESCORE))
            .andExpect(jsonPath("$.onderhoudsregime").value(DEFAULT_ONDERHOUDSREGIME))
            .andExpect(jsonPath("$.oppervlakte").value(DEFAULT_OPPERVLAKTE))
            .andExpect(jsonPath("$.overbruggingsobjectmateriaal").value(DEFAULT_OVERBRUGGINGSOBJECTMATERIAAL))
            .andExpect(jsonPath("$.overbruggingsobjectmodaliteit").value(DEFAULT_OVERBRUGGINGSOBJECTMODALITEIT))
            .andExpect(jsonPath("$.technischelevensduur").value(DEFAULT_TECHNISCHELEVENSDUUR))
            .andExpect(jsonPath("$.typefundering").value(DEFAULT_TYPEFUNDERING))
            .andExpect(jsonPath("$.vervangingswaarde").value(DEFAULT_VERVANGINGSWAARDE));
    }

    @Test
    @Transactional
    void getNonExistingOverbruggingsobject() throws Exception {
        // Get the overbruggingsobject
        restOverbruggingsobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOverbruggingsobject() throws Exception {
        // Initialize the database
        overbruggingsobjectRepository.saveAndFlush(overbruggingsobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overbruggingsobject
        Overbruggingsobject updatedOverbruggingsobject = overbruggingsobjectRepository.findById(overbruggingsobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOverbruggingsobject are not directly saved in db
        em.detach(updatedOverbruggingsobject);
        updatedOverbruggingsobject
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .antigraffitivoorziening(UPDATED_ANTIGRAFFITIVOORZIENING)
            .bereikbaarheid(UPDATED_BEREIKBAARHEID)
            .breedte(UPDATED_BREEDTE)
            .hoogte(UPDATED_HOOGTE)
            .installateur(UPDATED_INSTALLATEUR)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .jaarrenovatie(UPDATED_JAARRENOVATIE)
            .jaarvervanging(UPDATED_JAARVERVANGING)
            .kleur(UPDATED_KLEUR)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .looprichel(UPDATED_LOOPRICHEL)
            .minimumconditiescore(UPDATED_MINIMUMCONDITIESCORE)
            .onderhoudsregime(UPDATED_ONDERHOUDSREGIME)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .overbruggingsobjectmateriaal(UPDATED_OVERBRUGGINGSOBJECTMATERIAAL)
            .overbruggingsobjectmodaliteit(UPDATED_OVERBRUGGINGSOBJECTMODALITEIT)
            .technischelevensduur(UPDATED_TECHNISCHELEVENSDUUR)
            .typefundering(UPDATED_TYPEFUNDERING)
            .vervangingswaarde(UPDATED_VERVANGINGSWAARDE);

        restOverbruggingsobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOverbruggingsobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOverbruggingsobject))
            )
            .andExpect(status().isOk());

        // Validate the Overbruggingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOverbruggingsobjectToMatchAllProperties(updatedOverbruggingsobject);
    }

    @Test
    @Transactional
    void putNonExistingOverbruggingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overbruggingsobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverbruggingsobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, overbruggingsobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overbruggingsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overbruggingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOverbruggingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overbruggingsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverbruggingsobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overbruggingsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overbruggingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOverbruggingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overbruggingsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverbruggingsobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overbruggingsobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overbruggingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOverbruggingsobjectWithPatch() throws Exception {
        // Initialize the database
        overbruggingsobjectRepository.saveAndFlush(overbruggingsobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overbruggingsobject using partial update
        Overbruggingsobject partialUpdatedOverbruggingsobject = new Overbruggingsobject();
        partialUpdatedOverbruggingsobject.setId(overbruggingsobject.getId());

        partialUpdatedOverbruggingsobject
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .antigraffitivoorziening(UPDATED_ANTIGRAFFITIVOORZIENING)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .jaarrenovatie(UPDATED_JAARRENOVATIE)
            .jaarvervanging(UPDATED_JAARVERVANGING)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .looprichel(UPDATED_LOOPRICHEL)
            .minimumconditiescore(UPDATED_MINIMUMCONDITIESCORE)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .overbruggingsobjectmodaliteit(UPDATED_OVERBRUGGINGSOBJECTMODALITEIT)
            .technischelevensduur(UPDATED_TECHNISCHELEVENSDUUR);

        restOverbruggingsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverbruggingsobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverbruggingsobject))
            )
            .andExpect(status().isOk());

        // Validate the Overbruggingsobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverbruggingsobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOverbruggingsobject, overbruggingsobject),
            getPersistedOverbruggingsobject(overbruggingsobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateOverbruggingsobjectWithPatch() throws Exception {
        // Initialize the database
        overbruggingsobjectRepository.saveAndFlush(overbruggingsobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overbruggingsobject using partial update
        Overbruggingsobject partialUpdatedOverbruggingsobject = new Overbruggingsobject();
        partialUpdatedOverbruggingsobject.setId(overbruggingsobject.getId());

        partialUpdatedOverbruggingsobject
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .antigraffitivoorziening(UPDATED_ANTIGRAFFITIVOORZIENING)
            .bereikbaarheid(UPDATED_BEREIKBAARHEID)
            .breedte(UPDATED_BREEDTE)
            .hoogte(UPDATED_HOOGTE)
            .installateur(UPDATED_INSTALLATEUR)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .jaarrenovatie(UPDATED_JAARRENOVATIE)
            .jaarvervanging(UPDATED_JAARVERVANGING)
            .kleur(UPDATED_KLEUR)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .looprichel(UPDATED_LOOPRICHEL)
            .minimumconditiescore(UPDATED_MINIMUMCONDITIESCORE)
            .onderhoudsregime(UPDATED_ONDERHOUDSREGIME)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .overbruggingsobjectmateriaal(UPDATED_OVERBRUGGINGSOBJECTMATERIAAL)
            .overbruggingsobjectmodaliteit(UPDATED_OVERBRUGGINGSOBJECTMODALITEIT)
            .technischelevensduur(UPDATED_TECHNISCHELEVENSDUUR)
            .typefundering(UPDATED_TYPEFUNDERING)
            .vervangingswaarde(UPDATED_VERVANGINGSWAARDE);

        restOverbruggingsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverbruggingsobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverbruggingsobject))
            )
            .andExpect(status().isOk());

        // Validate the Overbruggingsobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverbruggingsobjectUpdatableFieldsEquals(
            partialUpdatedOverbruggingsobject,
            getPersistedOverbruggingsobject(partialUpdatedOverbruggingsobject)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOverbruggingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overbruggingsobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverbruggingsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, overbruggingsobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overbruggingsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overbruggingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOverbruggingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overbruggingsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverbruggingsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overbruggingsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overbruggingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOverbruggingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overbruggingsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverbruggingsobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(overbruggingsobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overbruggingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOverbruggingsobject() throws Exception {
        // Initialize the database
        overbruggingsobjectRepository.saveAndFlush(overbruggingsobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the overbruggingsobject
        restOverbruggingsobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, overbruggingsobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return overbruggingsobjectRepository.count();
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

    protected Overbruggingsobject getPersistedOverbruggingsobject(Overbruggingsobject overbruggingsobject) {
        return overbruggingsobjectRepository.findById(overbruggingsobject.getId()).orElseThrow();
    }

    protected void assertPersistedOverbruggingsobjectToMatchAllProperties(Overbruggingsobject expectedOverbruggingsobject) {
        assertOverbruggingsobjectAllPropertiesEquals(
            expectedOverbruggingsobject,
            getPersistedOverbruggingsobject(expectedOverbruggingsobject)
        );
    }

    protected void assertPersistedOverbruggingsobjectToMatchUpdatableProperties(Overbruggingsobject expectedOverbruggingsobject) {
        assertOverbruggingsobjectAllUpdatablePropertiesEquals(
            expectedOverbruggingsobject,
            getPersistedOverbruggingsobject(expectedOverbruggingsobject)
        );
    }
}
