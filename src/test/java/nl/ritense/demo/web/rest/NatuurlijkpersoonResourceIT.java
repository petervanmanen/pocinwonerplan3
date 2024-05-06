package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NatuurlijkpersoonAsserts.*;
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
import nl.ritense.demo.domain.Natuurlijkpersoon;
import nl.ritense.demo.repository.NatuurlijkpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NatuurlijkpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NatuurlijkpersoonResourceIT {

    private static final String DEFAULT_EMPTY = "AAAAAAAAAA";
    private static final String UPDATED_EMPTY = "BBBBBBBBBB";

    private static final String DEFAULT_AANDUIDINGBIJZONDERNEDERLANDERSCHAPPERSOON = "AAAAAAAAAA";
    private static final String UPDATED_AANDUIDINGBIJZONDERNEDERLANDERSCHAPPERSOON = "BBBBBBBBBB";

    private static final String DEFAULT_AANDUIDINGNAAMGEBRUIK = "AAAAAAAAAA";
    private static final String UPDATED_AANDUIDINGNAAMGEBRUIK = "BBBBBBBBBB";

    private static final String DEFAULT_AANHEFAANSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_AANHEFAANSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_ACADEMISCHETITEL = "AAAAAAAAAA";
    private static final String UPDATED_ACADEMISCHETITEL = "BBBBBBBBBB";

    private static final String DEFAULT_ACHTERNAAM = "AAAAAAAAAA";
    private static final String UPDATED_ACHTERNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_ADELLIJKETITELOFPREDIKAAT = "AAAAAAAAAA";
    private static final String UPDATED_ADELLIJKETITELOFPREDIKAAT = "BBBBBBBBBB";

    private static final String DEFAULT_ANUMMER = "AAAAAAAAAA";
    private static final String UPDATED_ANUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_BURGERSERVICENUMMER = "AAAAAAAAAA";
    private static final String UPDATED_BURGERSERVICENUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMGEBOORTE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMGEBOORTE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMOVERLIJDEN = "AAAAAAAAAA";
    private static final String UPDATED_DATUMOVERLIJDEN = "BBBBBBBBBB";

    private static final String DEFAULT_GEBOORTELAND = "AAAAAAAAAA";
    private static final String UPDATED_GEBOORTELAND = "BBBBBBBBBB";

    private static final String DEFAULT_GEBOORTEPLAATS = "AAAAAAAAAA";
    private static final String UPDATED_GEBOORTEPLAATS = "BBBBBBBBBB";

    private static final String DEFAULT_GESLACHTSAANDUIDING = "AAAAAAAAAA";
    private static final String UPDATED_GESLACHTSAANDUIDING = "BBBBBBBBBB";

    private static final String DEFAULT_GESLACHTSNAAM = "AAAAAAAAAA";
    private static final String UPDATED_GESLACHTSNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_GESLACHTSNAAMAANSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_GESLACHTSNAAMAANSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_HANDLICHTING = "AAAAAAAAAA";
    private static final String UPDATED_HANDLICHTING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INDICATIEAFSCHERMINGPERSOONSGEGEVENS = false;
    private static final Boolean UPDATED_INDICATIEAFSCHERMINGPERSOONSGEGEVENS = true;

    private static final Boolean DEFAULT_INDICATIEOVERLEDEN = false;
    private static final Boolean UPDATED_INDICATIEOVERLEDEN = true;

    private static final String DEFAULT_LANDOVERLIJDEN = "AAAAAAAAAA";
    private static final String UPDATED_LANDOVERLIJDEN = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITEIT = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_OVERLIJDENSPLAATS = "AAAAAAAAAA";
    private static final String UPDATED_OVERLIJDENSPLAATS = "BBBBBBBBBB";

    private static final String DEFAULT_VOORLETTERSAANSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_VOORLETTERSAANSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_VOORNAMEN = "AAAAAAAAAA";
    private static final String UPDATED_VOORNAMEN = "BBBBBBBBBB";

    private static final String DEFAULT_VOORNAMENAANSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_VOORNAMENAANSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_VOORVOEGSELGESLACHTSNAAM = "AAAAAAAAAA";
    private static final String UPDATED_VOORVOEGSELGESLACHTSNAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/natuurlijkpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NatuurlijkpersoonRepository natuurlijkpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNatuurlijkpersoonMockMvc;

    private Natuurlijkpersoon natuurlijkpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Natuurlijkpersoon createEntity(EntityManager em) {
        Natuurlijkpersoon natuurlijkpersoon = new Natuurlijkpersoon()
            .empty(DEFAULT_EMPTY)
            .aanduidingbijzondernederlanderschappersoon(DEFAULT_AANDUIDINGBIJZONDERNEDERLANDERSCHAPPERSOON)
            .aanduidingnaamgebruik(DEFAULT_AANDUIDINGNAAMGEBRUIK)
            .aanhefaanschrijving(DEFAULT_AANHEFAANSCHRIJVING)
            .academischetitel(DEFAULT_ACADEMISCHETITEL)
            .achternaam(DEFAULT_ACHTERNAAM)
            .adellijketitelofpredikaat(DEFAULT_ADELLIJKETITELOFPREDIKAAT)
            .anummer(DEFAULT_ANUMMER)
            .burgerservicenummer(DEFAULT_BURGERSERVICENUMMER)
            .datumgeboorte(DEFAULT_DATUMGEBOORTE)
            .datumoverlijden(DEFAULT_DATUMOVERLIJDEN)
            .geboorteland(DEFAULT_GEBOORTELAND)
            .geboorteplaats(DEFAULT_GEBOORTEPLAATS)
            .geslachtsaanduiding(DEFAULT_GESLACHTSAANDUIDING)
            .geslachtsnaam(DEFAULT_GESLACHTSNAAM)
            .geslachtsnaamaanschrijving(DEFAULT_GESLACHTSNAAMAANSCHRIJVING)
            .handlichting(DEFAULT_HANDLICHTING)
            .indicatieafschermingpersoonsgegevens(DEFAULT_INDICATIEAFSCHERMINGPERSOONSGEGEVENS)
            .indicatieoverleden(DEFAULT_INDICATIEOVERLEDEN)
            .landoverlijden(DEFAULT_LANDOVERLIJDEN)
            .nationaliteit(DEFAULT_NATIONALITEIT)
            .overlijdensplaats(DEFAULT_OVERLIJDENSPLAATS)
            .voorlettersaanschrijving(DEFAULT_VOORLETTERSAANSCHRIJVING)
            .voornamen(DEFAULT_VOORNAMEN)
            .voornamenaanschrijving(DEFAULT_VOORNAMENAANSCHRIJVING)
            .voorvoegselgeslachtsnaam(DEFAULT_VOORVOEGSELGESLACHTSNAAM);
        return natuurlijkpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Natuurlijkpersoon createUpdatedEntity(EntityManager em) {
        Natuurlijkpersoon natuurlijkpersoon = new Natuurlijkpersoon()
            .empty(UPDATED_EMPTY)
            .aanduidingbijzondernederlanderschappersoon(UPDATED_AANDUIDINGBIJZONDERNEDERLANDERSCHAPPERSOON)
            .aanduidingnaamgebruik(UPDATED_AANDUIDINGNAAMGEBRUIK)
            .aanhefaanschrijving(UPDATED_AANHEFAANSCHRIJVING)
            .academischetitel(UPDATED_ACADEMISCHETITEL)
            .achternaam(UPDATED_ACHTERNAAM)
            .adellijketitelofpredikaat(UPDATED_ADELLIJKETITELOFPREDIKAAT)
            .anummer(UPDATED_ANUMMER)
            .burgerservicenummer(UPDATED_BURGERSERVICENUMMER)
            .datumgeboorte(UPDATED_DATUMGEBOORTE)
            .datumoverlijden(UPDATED_DATUMOVERLIJDEN)
            .geboorteland(UPDATED_GEBOORTELAND)
            .geboorteplaats(UPDATED_GEBOORTEPLAATS)
            .geslachtsaanduiding(UPDATED_GESLACHTSAANDUIDING)
            .geslachtsnaam(UPDATED_GESLACHTSNAAM)
            .geslachtsnaamaanschrijving(UPDATED_GESLACHTSNAAMAANSCHRIJVING)
            .handlichting(UPDATED_HANDLICHTING)
            .indicatieafschermingpersoonsgegevens(UPDATED_INDICATIEAFSCHERMINGPERSOONSGEGEVENS)
            .indicatieoverleden(UPDATED_INDICATIEOVERLEDEN)
            .landoverlijden(UPDATED_LANDOVERLIJDEN)
            .nationaliteit(UPDATED_NATIONALITEIT)
            .overlijdensplaats(UPDATED_OVERLIJDENSPLAATS)
            .voorlettersaanschrijving(UPDATED_VOORLETTERSAANSCHRIJVING)
            .voornamen(UPDATED_VOORNAMEN)
            .voornamenaanschrijving(UPDATED_VOORNAMENAANSCHRIJVING)
            .voorvoegselgeslachtsnaam(UPDATED_VOORVOEGSELGESLACHTSNAAM);
        return natuurlijkpersoon;
    }

    @BeforeEach
    public void initTest() {
        natuurlijkpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createNatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Natuurlijkpersoon
        var returnedNatuurlijkpersoon = om.readValue(
            restNatuurlijkpersoonMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(natuurlijkpersoon)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Natuurlijkpersoon.class
        );

        // Validate the Natuurlijkpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNatuurlijkpersoonUpdatableFieldsEquals(returnedNatuurlijkpersoon, getPersistedNatuurlijkpersoon(returnedNatuurlijkpersoon));
    }

    @Test
    @Transactional
    void createNatuurlijkpersoonWithExistingId() throws Exception {
        // Create the Natuurlijkpersoon with an existing ID
        natuurlijkpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNatuurlijkpersoonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(natuurlijkpersoon)))
            .andExpect(status().isBadRequest());

        // Validate the Natuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNatuurlijkpersoons() throws Exception {
        // Initialize the database
        natuurlijkpersoonRepository.saveAndFlush(natuurlijkpersoon);

        // Get all the natuurlijkpersoonList
        restNatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(natuurlijkpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].empty").value(hasItem(DEFAULT_EMPTY)))
            .andExpect(
                jsonPath("$.[*].aanduidingbijzondernederlanderschappersoon").value(
                    hasItem(DEFAULT_AANDUIDINGBIJZONDERNEDERLANDERSCHAPPERSOON)
                )
            )
            .andExpect(jsonPath("$.[*].aanduidingnaamgebruik").value(hasItem(DEFAULT_AANDUIDINGNAAMGEBRUIK)))
            .andExpect(jsonPath("$.[*].aanhefaanschrijving").value(hasItem(DEFAULT_AANHEFAANSCHRIJVING)))
            .andExpect(jsonPath("$.[*].academischetitel").value(hasItem(DEFAULT_ACADEMISCHETITEL)))
            .andExpect(jsonPath("$.[*].achternaam").value(hasItem(DEFAULT_ACHTERNAAM)))
            .andExpect(jsonPath("$.[*].adellijketitelofpredikaat").value(hasItem(DEFAULT_ADELLIJKETITELOFPREDIKAAT)))
            .andExpect(jsonPath("$.[*].anummer").value(hasItem(DEFAULT_ANUMMER)))
            .andExpect(jsonPath("$.[*].burgerservicenummer").value(hasItem(DEFAULT_BURGERSERVICENUMMER)))
            .andExpect(jsonPath("$.[*].datumgeboorte").value(hasItem(DEFAULT_DATUMGEBOORTE)))
            .andExpect(jsonPath("$.[*].datumoverlijden").value(hasItem(DEFAULT_DATUMOVERLIJDEN)))
            .andExpect(jsonPath("$.[*].geboorteland").value(hasItem(DEFAULT_GEBOORTELAND)))
            .andExpect(jsonPath("$.[*].geboorteplaats").value(hasItem(DEFAULT_GEBOORTEPLAATS)))
            .andExpect(jsonPath("$.[*].geslachtsaanduiding").value(hasItem(DEFAULT_GESLACHTSAANDUIDING)))
            .andExpect(jsonPath("$.[*].geslachtsnaam").value(hasItem(DEFAULT_GESLACHTSNAAM)))
            .andExpect(jsonPath("$.[*].geslachtsnaamaanschrijving").value(hasItem(DEFAULT_GESLACHTSNAAMAANSCHRIJVING)))
            .andExpect(jsonPath("$.[*].handlichting").value(hasItem(DEFAULT_HANDLICHTING)))
            .andExpect(
                jsonPath("$.[*].indicatieafschermingpersoonsgegevens").value(
                    hasItem(DEFAULT_INDICATIEAFSCHERMINGPERSOONSGEGEVENS.booleanValue())
                )
            )
            .andExpect(jsonPath("$.[*].indicatieoverleden").value(hasItem(DEFAULT_INDICATIEOVERLEDEN.booleanValue())))
            .andExpect(jsonPath("$.[*].landoverlijden").value(hasItem(DEFAULT_LANDOVERLIJDEN)))
            .andExpect(jsonPath("$.[*].nationaliteit").value(hasItem(DEFAULT_NATIONALITEIT)))
            .andExpect(jsonPath("$.[*].overlijdensplaats").value(hasItem(DEFAULT_OVERLIJDENSPLAATS)))
            .andExpect(jsonPath("$.[*].voorlettersaanschrijving").value(hasItem(DEFAULT_VOORLETTERSAANSCHRIJVING)))
            .andExpect(jsonPath("$.[*].voornamen").value(hasItem(DEFAULT_VOORNAMEN)))
            .andExpect(jsonPath("$.[*].voornamenaanschrijving").value(hasItem(DEFAULT_VOORNAMENAANSCHRIJVING)))
            .andExpect(jsonPath("$.[*].voorvoegselgeslachtsnaam").value(hasItem(DEFAULT_VOORVOEGSELGESLACHTSNAAM)));
    }

    @Test
    @Transactional
    void getNatuurlijkpersoon() throws Exception {
        // Initialize the database
        natuurlijkpersoonRepository.saveAndFlush(natuurlijkpersoon);

        // Get the natuurlijkpersoon
        restNatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, natuurlijkpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(natuurlijkpersoon.getId().intValue()))
            .andExpect(jsonPath("$.empty").value(DEFAULT_EMPTY))
            .andExpect(jsonPath("$.aanduidingbijzondernederlanderschappersoon").value(DEFAULT_AANDUIDINGBIJZONDERNEDERLANDERSCHAPPERSOON))
            .andExpect(jsonPath("$.aanduidingnaamgebruik").value(DEFAULT_AANDUIDINGNAAMGEBRUIK))
            .andExpect(jsonPath("$.aanhefaanschrijving").value(DEFAULT_AANHEFAANSCHRIJVING))
            .andExpect(jsonPath("$.academischetitel").value(DEFAULT_ACADEMISCHETITEL))
            .andExpect(jsonPath("$.achternaam").value(DEFAULT_ACHTERNAAM))
            .andExpect(jsonPath("$.adellijketitelofpredikaat").value(DEFAULT_ADELLIJKETITELOFPREDIKAAT))
            .andExpect(jsonPath("$.anummer").value(DEFAULT_ANUMMER))
            .andExpect(jsonPath("$.burgerservicenummer").value(DEFAULT_BURGERSERVICENUMMER))
            .andExpect(jsonPath("$.datumgeboorte").value(DEFAULT_DATUMGEBOORTE))
            .andExpect(jsonPath("$.datumoverlijden").value(DEFAULT_DATUMOVERLIJDEN))
            .andExpect(jsonPath("$.geboorteland").value(DEFAULT_GEBOORTELAND))
            .andExpect(jsonPath("$.geboorteplaats").value(DEFAULT_GEBOORTEPLAATS))
            .andExpect(jsonPath("$.geslachtsaanduiding").value(DEFAULT_GESLACHTSAANDUIDING))
            .andExpect(jsonPath("$.geslachtsnaam").value(DEFAULT_GESLACHTSNAAM))
            .andExpect(jsonPath("$.geslachtsnaamaanschrijving").value(DEFAULT_GESLACHTSNAAMAANSCHRIJVING))
            .andExpect(jsonPath("$.handlichting").value(DEFAULT_HANDLICHTING))
            .andExpect(
                jsonPath("$.indicatieafschermingpersoonsgegevens").value(DEFAULT_INDICATIEAFSCHERMINGPERSOONSGEGEVENS.booleanValue())
            )
            .andExpect(jsonPath("$.indicatieoverleden").value(DEFAULT_INDICATIEOVERLEDEN.booleanValue()))
            .andExpect(jsonPath("$.landoverlijden").value(DEFAULT_LANDOVERLIJDEN))
            .andExpect(jsonPath("$.nationaliteit").value(DEFAULT_NATIONALITEIT))
            .andExpect(jsonPath("$.overlijdensplaats").value(DEFAULT_OVERLIJDENSPLAATS))
            .andExpect(jsonPath("$.voorlettersaanschrijving").value(DEFAULT_VOORLETTERSAANSCHRIJVING))
            .andExpect(jsonPath("$.voornamen").value(DEFAULT_VOORNAMEN))
            .andExpect(jsonPath("$.voornamenaanschrijving").value(DEFAULT_VOORNAMENAANSCHRIJVING))
            .andExpect(jsonPath("$.voorvoegselgeslachtsnaam").value(DEFAULT_VOORVOEGSELGESLACHTSNAAM));
    }

    @Test
    @Transactional
    void getNonExistingNatuurlijkpersoon() throws Exception {
        // Get the natuurlijkpersoon
        restNatuurlijkpersoonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNatuurlijkpersoon() throws Exception {
        // Initialize the database
        natuurlijkpersoonRepository.saveAndFlush(natuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the natuurlijkpersoon
        Natuurlijkpersoon updatedNatuurlijkpersoon = natuurlijkpersoonRepository.findById(natuurlijkpersoon.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNatuurlijkpersoon are not directly saved in db
        em.detach(updatedNatuurlijkpersoon);
        updatedNatuurlijkpersoon
            .empty(UPDATED_EMPTY)
            .aanduidingbijzondernederlanderschappersoon(UPDATED_AANDUIDINGBIJZONDERNEDERLANDERSCHAPPERSOON)
            .aanduidingnaamgebruik(UPDATED_AANDUIDINGNAAMGEBRUIK)
            .aanhefaanschrijving(UPDATED_AANHEFAANSCHRIJVING)
            .academischetitel(UPDATED_ACADEMISCHETITEL)
            .achternaam(UPDATED_ACHTERNAAM)
            .adellijketitelofpredikaat(UPDATED_ADELLIJKETITELOFPREDIKAAT)
            .anummer(UPDATED_ANUMMER)
            .burgerservicenummer(UPDATED_BURGERSERVICENUMMER)
            .datumgeboorte(UPDATED_DATUMGEBOORTE)
            .datumoverlijden(UPDATED_DATUMOVERLIJDEN)
            .geboorteland(UPDATED_GEBOORTELAND)
            .geboorteplaats(UPDATED_GEBOORTEPLAATS)
            .geslachtsaanduiding(UPDATED_GESLACHTSAANDUIDING)
            .geslachtsnaam(UPDATED_GESLACHTSNAAM)
            .geslachtsnaamaanschrijving(UPDATED_GESLACHTSNAAMAANSCHRIJVING)
            .handlichting(UPDATED_HANDLICHTING)
            .indicatieafschermingpersoonsgegevens(UPDATED_INDICATIEAFSCHERMINGPERSOONSGEGEVENS)
            .indicatieoverleden(UPDATED_INDICATIEOVERLEDEN)
            .landoverlijden(UPDATED_LANDOVERLIJDEN)
            .nationaliteit(UPDATED_NATIONALITEIT)
            .overlijdensplaats(UPDATED_OVERLIJDENSPLAATS)
            .voorlettersaanschrijving(UPDATED_VOORLETTERSAANSCHRIJVING)
            .voornamen(UPDATED_VOORNAMEN)
            .voornamenaanschrijving(UPDATED_VOORNAMENAANSCHRIJVING)
            .voorvoegselgeslachtsnaam(UPDATED_VOORVOEGSELGESLACHTSNAAM);

        restNatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Natuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNatuurlijkpersoonToMatchAllProperties(updatedNatuurlijkpersoon);
    }

    @Test
    @Transactional
    void putNonExistingNatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        natuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(natuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Natuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        natuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(natuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Natuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        natuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatuurlijkpersoonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(natuurlijkpersoon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Natuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        natuurlijkpersoonRepository.saveAndFlush(natuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the natuurlijkpersoon using partial update
        Natuurlijkpersoon partialUpdatedNatuurlijkpersoon = new Natuurlijkpersoon();
        partialUpdatedNatuurlijkpersoon.setId(natuurlijkpersoon.getId());

        partialUpdatedNatuurlijkpersoon
            .empty(UPDATED_EMPTY)
            .aanduidingbijzondernederlanderschappersoon(UPDATED_AANDUIDINGBIJZONDERNEDERLANDERSCHAPPERSOON)
            .academischetitel(UPDATED_ACADEMISCHETITEL)
            .achternaam(UPDATED_ACHTERNAAM)
            .adellijketitelofpredikaat(UPDATED_ADELLIJKETITELOFPREDIKAAT)
            .anummer(UPDATED_ANUMMER)
            .datumgeboorte(UPDATED_DATUMGEBOORTE)
            .datumoverlijden(UPDATED_DATUMOVERLIJDEN)
            .geboorteland(UPDATED_GEBOORTELAND)
            .geslachtsaanduiding(UPDATED_GESLACHTSAANDUIDING)
            .geslachtsnaam(UPDATED_GESLACHTSNAAM)
            .geslachtsnaamaanschrijving(UPDATED_GESLACHTSNAAMAANSCHRIJVING)
            .handlichting(UPDATED_HANDLICHTING)
            .landoverlijden(UPDATED_LANDOVERLIJDEN)
            .nationaliteit(UPDATED_NATIONALITEIT)
            .voorlettersaanschrijving(UPDATED_VOORLETTERSAANSCHRIJVING)
            .voornamen(UPDATED_VOORNAMEN)
            .voornamenaanschrijving(UPDATED_VOORNAMENAANSCHRIJVING)
            .voorvoegselgeslachtsnaam(UPDATED_VOORVOEGSELGESLACHTSNAAM);

        restNatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Natuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNatuurlijkpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNatuurlijkpersoon, natuurlijkpersoon),
            getPersistedNatuurlijkpersoon(natuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateNatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        natuurlijkpersoonRepository.saveAndFlush(natuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the natuurlijkpersoon using partial update
        Natuurlijkpersoon partialUpdatedNatuurlijkpersoon = new Natuurlijkpersoon();
        partialUpdatedNatuurlijkpersoon.setId(natuurlijkpersoon.getId());

        partialUpdatedNatuurlijkpersoon
            .empty(UPDATED_EMPTY)
            .aanduidingbijzondernederlanderschappersoon(UPDATED_AANDUIDINGBIJZONDERNEDERLANDERSCHAPPERSOON)
            .aanduidingnaamgebruik(UPDATED_AANDUIDINGNAAMGEBRUIK)
            .aanhefaanschrijving(UPDATED_AANHEFAANSCHRIJVING)
            .academischetitel(UPDATED_ACADEMISCHETITEL)
            .achternaam(UPDATED_ACHTERNAAM)
            .adellijketitelofpredikaat(UPDATED_ADELLIJKETITELOFPREDIKAAT)
            .anummer(UPDATED_ANUMMER)
            .burgerservicenummer(UPDATED_BURGERSERVICENUMMER)
            .datumgeboorte(UPDATED_DATUMGEBOORTE)
            .datumoverlijden(UPDATED_DATUMOVERLIJDEN)
            .geboorteland(UPDATED_GEBOORTELAND)
            .geboorteplaats(UPDATED_GEBOORTEPLAATS)
            .geslachtsaanduiding(UPDATED_GESLACHTSAANDUIDING)
            .geslachtsnaam(UPDATED_GESLACHTSNAAM)
            .geslachtsnaamaanschrijving(UPDATED_GESLACHTSNAAMAANSCHRIJVING)
            .handlichting(UPDATED_HANDLICHTING)
            .indicatieafschermingpersoonsgegevens(UPDATED_INDICATIEAFSCHERMINGPERSOONSGEGEVENS)
            .indicatieoverleden(UPDATED_INDICATIEOVERLEDEN)
            .landoverlijden(UPDATED_LANDOVERLIJDEN)
            .nationaliteit(UPDATED_NATIONALITEIT)
            .overlijdensplaats(UPDATED_OVERLIJDENSPLAATS)
            .voorlettersaanschrijving(UPDATED_VOORLETTERSAANSCHRIJVING)
            .voornamen(UPDATED_VOORNAMEN)
            .voornamenaanschrijving(UPDATED_VOORNAMENAANSCHRIJVING)
            .voorvoegselgeslachtsnaam(UPDATED_VOORVOEGSELGESLACHTSNAAM);

        restNatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Natuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNatuurlijkpersoonUpdatableFieldsEquals(
            partialUpdatedNatuurlijkpersoon,
            getPersistedNatuurlijkpersoon(partialUpdatedNatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        natuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, natuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(natuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Natuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        natuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(natuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Natuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        natuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatuurlijkpersoonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(natuurlijkpersoon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Natuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNatuurlijkpersoon() throws Exception {
        // Initialize the database
        natuurlijkpersoonRepository.saveAndFlush(natuurlijkpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the natuurlijkpersoon
        restNatuurlijkpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, natuurlijkpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return natuurlijkpersoonRepository.count();
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

    protected Natuurlijkpersoon getPersistedNatuurlijkpersoon(Natuurlijkpersoon natuurlijkpersoon) {
        return natuurlijkpersoonRepository.findById(natuurlijkpersoon.getId()).orElseThrow();
    }

    protected void assertPersistedNatuurlijkpersoonToMatchAllProperties(Natuurlijkpersoon expectedNatuurlijkpersoon) {
        assertNatuurlijkpersoonAllPropertiesEquals(expectedNatuurlijkpersoon, getPersistedNatuurlijkpersoon(expectedNatuurlijkpersoon));
    }

    protected void assertPersistedNatuurlijkpersoonToMatchUpdatableProperties(Natuurlijkpersoon expectedNatuurlijkpersoon) {
        assertNatuurlijkpersoonAllUpdatablePropertiesEquals(
            expectedNatuurlijkpersoon,
            getPersistedNatuurlijkpersoon(expectedNatuurlijkpersoon)
        );
    }
}
