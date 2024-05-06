package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MeubilairAsserts.*;
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
import nl.ritense.demo.domain.Meubilair;
import nl.ritense.demo.repository.MeubilairRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MeubilairResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MeubilairResourceIT {

    private static final String DEFAULT_AANLEGHOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_AANLEGHOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_BOUWJAAR = "AAAAAAAAAA";
    private static final String UPDATED_BOUWJAAR = "BBBBBBBBBB";

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMAANSCHAF = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANSCHAF = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DIAMETER = "AAAAAAAAAA";
    private static final String UPDATED_DIAMETER = "BBBBBBBBBB";

    private static final String DEFAULT_FABRIKANT = "AAAAAAAAAA";
    private static final String UPDATED_FABRIKANT = "BBBBBBBBBB";

    private static final String DEFAULT_GEWICHT = "AAAAAAAAAA";
    private static final String UPDATED_GEWICHT = "BBBBBBBBBB";

    private static final String DEFAULT_HOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_INSTALLATEUR = "AAAAAAAAAA";
    private static final String UPDATED_INSTALLATEUR = "BBBBBBBBBB";

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

    private static final String DEFAULT_LEVERANCIER = "AAAAAAAAAA";
    private static final String UPDATED_LEVERANCIER = "BBBBBBBBBB";

    private static final String DEFAULT_MEUBILAIRMATERIAAL = "AAAAAAAAAA";
    private static final String UPDATED_MEUBILAIRMATERIAAL = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERGROND = "AAAAAAAAAA";
    private static final String UPDATED_ONDERGROND = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIJSAANSCHAF = "AAAAAAAAAA";
    private static final String UPDATED_PRIJSAANSCHAF = "BBBBBBBBBB";

    private static final String DEFAULT_SERIENUMMER = "AAAAAAAAAA";
    private static final String UPDATED_SERIENUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSPONDER = "AAAAAAAAAA";
    private static final String UPDATED_TRANSPONDER = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSPONDERLOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSPONDERLOCATIE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEFUNDERING = "AAAAAAAAAA";
    private static final String UPDATED_TYPEFUNDERING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TYPEPLAAT = false;
    private static final Boolean UPDATED_TYPEPLAAT = true;

    private static final String ENTITY_API_URL = "/api/meubilairs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MeubilairRepository meubilairRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMeubilairMockMvc;

    private Meubilair meubilair;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meubilair createEntity(EntityManager em) {
        Meubilair meubilair = new Meubilair()
            .aanleghoogte(DEFAULT_AANLEGHOOGTE)
            .bouwjaar(DEFAULT_BOUWJAAR)
            .breedte(DEFAULT_BREEDTE)
            .datumaanschaf(DEFAULT_DATUMAANSCHAF)
            .diameter(DEFAULT_DIAMETER)
            .fabrikant(DEFAULT_FABRIKANT)
            .gewicht(DEFAULT_GEWICHT)
            .hoogte(DEFAULT_HOOGTE)
            .installateur(DEFAULT_INSTALLATEUR)
            .jaaronderhouduitgevoerd(DEFAULT_JAARONDERHOUDUITGEVOERD)
            .jaarpraktischeinde(DEFAULT_JAARPRAKTISCHEINDE)
            .kleur(DEFAULT_KLEUR)
            .kwaliteitsniveauactueel(DEFAULT_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(DEFAULT_KWALITEITSNIVEAUGEWENST)
            .lengte(DEFAULT_LENGTE)
            .leverancier(DEFAULT_LEVERANCIER)
            .meubilairmateriaal(DEFAULT_MEUBILAIRMATERIAAL)
            .model(DEFAULT_MODEL)
            .ondergrond(DEFAULT_ONDERGROND)
            .oppervlakte(DEFAULT_OPPERVLAKTE)
            .prijsaanschaf(DEFAULT_PRIJSAANSCHAF)
            .serienummer(DEFAULT_SERIENUMMER)
            .transponder(DEFAULT_TRANSPONDER)
            .transponderlocatie(DEFAULT_TRANSPONDERLOCATIE)
            .typefundering(DEFAULT_TYPEFUNDERING)
            .typeplaat(DEFAULT_TYPEPLAAT);
        return meubilair;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meubilair createUpdatedEntity(EntityManager em) {
        Meubilair meubilair = new Meubilair()
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .bouwjaar(UPDATED_BOUWJAAR)
            .breedte(UPDATED_BREEDTE)
            .datumaanschaf(UPDATED_DATUMAANSCHAF)
            .diameter(UPDATED_DIAMETER)
            .fabrikant(UPDATED_FABRIKANT)
            .gewicht(UPDATED_GEWICHT)
            .hoogte(UPDATED_HOOGTE)
            .installateur(UPDATED_INSTALLATEUR)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .jaarpraktischeinde(UPDATED_JAARPRAKTISCHEINDE)
            .kleur(UPDATED_KLEUR)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .meubilairmateriaal(UPDATED_MEUBILAIRMATERIAAL)
            .model(UPDATED_MODEL)
            .ondergrond(UPDATED_ONDERGROND)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .prijsaanschaf(UPDATED_PRIJSAANSCHAF)
            .serienummer(UPDATED_SERIENUMMER)
            .transponder(UPDATED_TRANSPONDER)
            .transponderlocatie(UPDATED_TRANSPONDERLOCATIE)
            .typefundering(UPDATED_TYPEFUNDERING)
            .typeplaat(UPDATED_TYPEPLAAT);
        return meubilair;
    }

    @BeforeEach
    public void initTest() {
        meubilair = createEntity(em);
    }

    @Test
    @Transactional
    void createMeubilair() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Meubilair
        var returnedMeubilair = om.readValue(
            restMeubilairMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(meubilair)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Meubilair.class
        );

        // Validate the Meubilair in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMeubilairUpdatableFieldsEquals(returnedMeubilair, getPersistedMeubilair(returnedMeubilair));
    }

    @Test
    @Transactional
    void createMeubilairWithExistingId() throws Exception {
        // Create the Meubilair with an existing ID
        meubilair.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeubilairMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(meubilair)))
            .andExpect(status().isBadRequest());

        // Validate the Meubilair in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMeubilairs() throws Exception {
        // Initialize the database
        meubilairRepository.saveAndFlush(meubilair);

        // Get all the meubilairList
        restMeubilairMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meubilair.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanleghoogte").value(hasItem(DEFAULT_AANLEGHOOGTE)))
            .andExpect(jsonPath("$.[*].bouwjaar").value(hasItem(DEFAULT_BOUWJAAR)))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].datumaanschaf").value(hasItem(DEFAULT_DATUMAANSCHAF.toString())))
            .andExpect(jsonPath("$.[*].diameter").value(hasItem(DEFAULT_DIAMETER)))
            .andExpect(jsonPath("$.[*].fabrikant").value(hasItem(DEFAULT_FABRIKANT)))
            .andExpect(jsonPath("$.[*].gewicht").value(hasItem(DEFAULT_GEWICHT)))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].installateur").value(hasItem(DEFAULT_INSTALLATEUR)))
            .andExpect(jsonPath("$.[*].jaaronderhouduitgevoerd").value(hasItem(DEFAULT_JAARONDERHOUDUITGEVOERD)))
            .andExpect(jsonPath("$.[*].jaarpraktischeinde").value(hasItem(DEFAULT_JAARPRAKTISCHEINDE)))
            .andExpect(jsonPath("$.[*].kleur").value(hasItem(DEFAULT_KLEUR)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveauactueel").value(hasItem(DEFAULT_KWALITEITSNIVEAUACTUEEL)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveaugewenst").value(hasItem(DEFAULT_KWALITEITSNIVEAUGEWENST)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].leverancier").value(hasItem(DEFAULT_LEVERANCIER)))
            .andExpect(jsonPath("$.[*].meubilairmateriaal").value(hasItem(DEFAULT_MEUBILAIRMATERIAAL)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].ondergrond").value(hasItem(DEFAULT_ONDERGROND)))
            .andExpect(jsonPath("$.[*].oppervlakte").value(hasItem(DEFAULT_OPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].prijsaanschaf").value(hasItem(DEFAULT_PRIJSAANSCHAF)))
            .andExpect(jsonPath("$.[*].serienummer").value(hasItem(DEFAULT_SERIENUMMER)))
            .andExpect(jsonPath("$.[*].transponder").value(hasItem(DEFAULT_TRANSPONDER)))
            .andExpect(jsonPath("$.[*].transponderlocatie").value(hasItem(DEFAULT_TRANSPONDERLOCATIE)))
            .andExpect(jsonPath("$.[*].typefundering").value(hasItem(DEFAULT_TYPEFUNDERING)))
            .andExpect(jsonPath("$.[*].typeplaat").value(hasItem(DEFAULT_TYPEPLAAT.booleanValue())));
    }

    @Test
    @Transactional
    void getMeubilair() throws Exception {
        // Initialize the database
        meubilairRepository.saveAndFlush(meubilair);

        // Get the meubilair
        restMeubilairMockMvc
            .perform(get(ENTITY_API_URL_ID, meubilair.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(meubilair.getId().intValue()))
            .andExpect(jsonPath("$.aanleghoogte").value(DEFAULT_AANLEGHOOGTE))
            .andExpect(jsonPath("$.bouwjaar").value(DEFAULT_BOUWJAAR))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.datumaanschaf").value(DEFAULT_DATUMAANSCHAF.toString()))
            .andExpect(jsonPath("$.diameter").value(DEFAULT_DIAMETER))
            .andExpect(jsonPath("$.fabrikant").value(DEFAULT_FABRIKANT))
            .andExpect(jsonPath("$.gewicht").value(DEFAULT_GEWICHT))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.installateur").value(DEFAULT_INSTALLATEUR))
            .andExpect(jsonPath("$.jaaronderhouduitgevoerd").value(DEFAULT_JAARONDERHOUDUITGEVOERD))
            .andExpect(jsonPath("$.jaarpraktischeinde").value(DEFAULT_JAARPRAKTISCHEINDE))
            .andExpect(jsonPath("$.kleur").value(DEFAULT_KLEUR))
            .andExpect(jsonPath("$.kwaliteitsniveauactueel").value(DEFAULT_KWALITEITSNIVEAUACTUEEL))
            .andExpect(jsonPath("$.kwaliteitsniveaugewenst").value(DEFAULT_KWALITEITSNIVEAUGEWENST))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.leverancier").value(DEFAULT_LEVERANCIER))
            .andExpect(jsonPath("$.meubilairmateriaal").value(DEFAULT_MEUBILAIRMATERIAAL))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.ondergrond").value(DEFAULT_ONDERGROND))
            .andExpect(jsonPath("$.oppervlakte").value(DEFAULT_OPPERVLAKTE))
            .andExpect(jsonPath("$.prijsaanschaf").value(DEFAULT_PRIJSAANSCHAF))
            .andExpect(jsonPath("$.serienummer").value(DEFAULT_SERIENUMMER))
            .andExpect(jsonPath("$.transponder").value(DEFAULT_TRANSPONDER))
            .andExpect(jsonPath("$.transponderlocatie").value(DEFAULT_TRANSPONDERLOCATIE))
            .andExpect(jsonPath("$.typefundering").value(DEFAULT_TYPEFUNDERING))
            .andExpect(jsonPath("$.typeplaat").value(DEFAULT_TYPEPLAAT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingMeubilair() throws Exception {
        // Get the meubilair
        restMeubilairMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMeubilair() throws Exception {
        // Initialize the database
        meubilairRepository.saveAndFlush(meubilair);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the meubilair
        Meubilair updatedMeubilair = meubilairRepository.findById(meubilair.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMeubilair are not directly saved in db
        em.detach(updatedMeubilair);
        updatedMeubilair
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .bouwjaar(UPDATED_BOUWJAAR)
            .breedte(UPDATED_BREEDTE)
            .datumaanschaf(UPDATED_DATUMAANSCHAF)
            .diameter(UPDATED_DIAMETER)
            .fabrikant(UPDATED_FABRIKANT)
            .gewicht(UPDATED_GEWICHT)
            .hoogte(UPDATED_HOOGTE)
            .installateur(UPDATED_INSTALLATEUR)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .jaarpraktischeinde(UPDATED_JAARPRAKTISCHEINDE)
            .kleur(UPDATED_KLEUR)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .meubilairmateriaal(UPDATED_MEUBILAIRMATERIAAL)
            .model(UPDATED_MODEL)
            .ondergrond(UPDATED_ONDERGROND)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .prijsaanschaf(UPDATED_PRIJSAANSCHAF)
            .serienummer(UPDATED_SERIENUMMER)
            .transponder(UPDATED_TRANSPONDER)
            .transponderlocatie(UPDATED_TRANSPONDERLOCATIE)
            .typefundering(UPDATED_TYPEFUNDERING)
            .typeplaat(UPDATED_TYPEPLAAT);

        restMeubilairMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMeubilair.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMeubilair))
            )
            .andExpect(status().isOk());

        // Validate the Meubilair in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMeubilairToMatchAllProperties(updatedMeubilair);
    }

    @Test
    @Transactional
    void putNonExistingMeubilair() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meubilair.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeubilairMockMvc
            .perform(
                put(ENTITY_API_URL_ID, meubilair.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(meubilair))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meubilair in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMeubilair() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meubilair.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeubilairMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(meubilair))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meubilair in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMeubilair() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meubilair.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeubilairMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(meubilair)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Meubilair in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMeubilairWithPatch() throws Exception {
        // Initialize the database
        meubilairRepository.saveAndFlush(meubilair);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the meubilair using partial update
        Meubilair partialUpdatedMeubilair = new Meubilair();
        partialUpdatedMeubilair.setId(meubilair.getId());

        partialUpdatedMeubilair
            .breedte(UPDATED_BREEDTE)
            .diameter(UPDATED_DIAMETER)
            .installateur(UPDATED_INSTALLATEUR)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .meubilairmateriaal(UPDATED_MEUBILAIRMATERIAAL)
            .ondergrond(UPDATED_ONDERGROND)
            .serienummer(UPDATED_SERIENUMMER)
            .transponder(UPDATED_TRANSPONDER);

        restMeubilairMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMeubilair.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMeubilair))
            )
            .andExpect(status().isOk());

        // Validate the Meubilair in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMeubilairUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMeubilair, meubilair),
            getPersistedMeubilair(meubilair)
        );
    }

    @Test
    @Transactional
    void fullUpdateMeubilairWithPatch() throws Exception {
        // Initialize the database
        meubilairRepository.saveAndFlush(meubilair);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the meubilair using partial update
        Meubilair partialUpdatedMeubilair = new Meubilair();
        partialUpdatedMeubilair.setId(meubilair.getId());

        partialUpdatedMeubilair
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .bouwjaar(UPDATED_BOUWJAAR)
            .breedte(UPDATED_BREEDTE)
            .datumaanschaf(UPDATED_DATUMAANSCHAF)
            .diameter(UPDATED_DIAMETER)
            .fabrikant(UPDATED_FABRIKANT)
            .gewicht(UPDATED_GEWICHT)
            .hoogte(UPDATED_HOOGTE)
            .installateur(UPDATED_INSTALLATEUR)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .jaarpraktischeinde(UPDATED_JAARPRAKTISCHEINDE)
            .kleur(UPDATED_KLEUR)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .meubilairmateriaal(UPDATED_MEUBILAIRMATERIAAL)
            .model(UPDATED_MODEL)
            .ondergrond(UPDATED_ONDERGROND)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .prijsaanschaf(UPDATED_PRIJSAANSCHAF)
            .serienummer(UPDATED_SERIENUMMER)
            .transponder(UPDATED_TRANSPONDER)
            .transponderlocatie(UPDATED_TRANSPONDERLOCATIE)
            .typefundering(UPDATED_TYPEFUNDERING)
            .typeplaat(UPDATED_TYPEPLAAT);

        restMeubilairMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMeubilair.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMeubilair))
            )
            .andExpect(status().isOk());

        // Validate the Meubilair in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMeubilairUpdatableFieldsEquals(partialUpdatedMeubilair, getPersistedMeubilair(partialUpdatedMeubilair));
    }

    @Test
    @Transactional
    void patchNonExistingMeubilair() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meubilair.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeubilairMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, meubilair.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(meubilair))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meubilair in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMeubilair() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meubilair.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeubilairMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(meubilair))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meubilair in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMeubilair() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meubilair.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeubilairMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(meubilair)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Meubilair in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMeubilair() throws Exception {
        // Initialize the database
        meubilairRepository.saveAndFlush(meubilair);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the meubilair
        restMeubilairMockMvc
            .perform(delete(ENTITY_API_URL_ID, meubilair.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return meubilairRepository.count();
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

    protected Meubilair getPersistedMeubilair(Meubilair meubilair) {
        return meubilairRepository.findById(meubilair.getId()).orElseThrow();
    }

    protected void assertPersistedMeubilairToMatchAllProperties(Meubilair expectedMeubilair) {
        assertMeubilairAllPropertiesEquals(expectedMeubilair, getPersistedMeubilair(expectedMeubilair));
    }

    protected void assertPersistedMeubilairToMatchUpdatableProperties(Meubilair expectedMeubilair) {
        assertMeubilairAllUpdatablePropertiesEquals(expectedMeubilair, getPersistedMeubilair(expectedMeubilair));
    }
}
