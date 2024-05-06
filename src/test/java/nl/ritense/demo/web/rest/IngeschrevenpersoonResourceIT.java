package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.IngeschrevenpersoonAsserts.*;
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
import nl.ritense.demo.domain.Ingeschrevenpersoon;
import nl.ritense.demo.repository.IngeschrevenpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IngeschrevenpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IngeschrevenpersoonResourceIT {

    private static final String DEFAULT_ADRESHERKOMST = "AAAAAAAAAA";
    private static final String UPDATED_ADRESHERKOMST = "BBBBBBBBBB";

    private static final String DEFAULT_ANUMMER = "AAAAAAAAAA";
    private static final String UPDATED_ANUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_BESCHRIJVINGLOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_BESCHRIJVINGLOCATIE = "BBBBBBBBBB";

    private static final String DEFAULT_BUITENLANDSREISDOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_BUITENLANDSREISDOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_BURGERLIJKESTAAT = "AAAAAAAAAA";
    private static final String UPDATED_BURGERLIJKESTAAT = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMBEGINGELDIGHEIDVERBLIJFPLAATS = "AAAAAAAAAA";
    private static final String UPDATED_DATUMBEGINGELDIGHEIDVERBLIJFPLAATS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDVERBLIJFSPLAATS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDVERBLIJFSPLAATS = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DATUMINSCHRIJVINGGEMEENTE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMINSCHRIJVINGGEMEENTE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMOPSCHORTINGBIJHOUDING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMOPSCHORTINGBIJHOUDING = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMVERTREKUITNEDERLAND = "AAAAAAAAAA";
    private static final String UPDATED_DATUMVERTREKUITNEDERLAND = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMVESTIGINGNEDERLAND = "AAAAAAAAAA";
    private static final String UPDATED_DATUMVESTIGINGNEDERLAND = "BBBBBBBBBB";

    private static final String DEFAULT_GEMEENTEVANINSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_GEMEENTEVANINSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_GEZINSRELATIE = "AAAAAAAAAA";
    private static final String UPDATED_GEZINSRELATIE = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIEGEHEIM = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEGEHEIM = "BBBBBBBBBB";

    private static final String DEFAULT_INGEZETENE = "AAAAAAAAAA";
    private static final String UPDATED_INGEZETENE = "BBBBBBBBBB";

    private static final String DEFAULT_LANDWAARNAARVERTROKKEN = "AAAAAAAAAA";
    private static final String UPDATED_LANDWAARNAARVERTROKKEN = "BBBBBBBBBB";

    private static final String DEFAULT_LANDWAARVANDAANINGESCHREVEN = "AAAAAAAAAA";
    private static final String UPDATED_LANDWAARVANDAANINGESCHREVEN = "BBBBBBBBBB";

    private static final String DEFAULT_OUDER_1 = "AAAAAAAAAA";
    private static final String UPDATED_OUDER_1 = "BBBBBBBBBB";

    private static final String DEFAULT_OUDER_2 = "AAAAAAAAAA";
    private static final String UPDATED_OUDER_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNERID = "AAAAAAAAAA";
    private static final String UPDATED_PARTNERID = "BBBBBBBBBB";

    private static final String DEFAULT_REDENEINDEBEWONING = "AAAAAAAAAA";
    private static final String UPDATED_REDENEINDEBEWONING = "BBBBBBBBBB";

    private static final String DEFAULT_REDENOPSCHORTINGBIJHOUDING = "AAAAAAAAAA";
    private static final String UPDATED_REDENOPSCHORTINGBIJHOUDING = "BBBBBBBBBB";

    private static final String DEFAULT_SIGNALERINGREISDOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_SIGNALERINGREISDOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_VERBLIJFSTITEL = "AAAAAAAAAA";
    private static final String UPDATED_VERBLIJFSTITEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ingeschrevenpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IngeschrevenpersoonRepository ingeschrevenpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIngeschrevenpersoonMockMvc;

    private Ingeschrevenpersoon ingeschrevenpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ingeschrevenpersoon createEntity(EntityManager em) {
        Ingeschrevenpersoon ingeschrevenpersoon = new Ingeschrevenpersoon()
            .adresherkomst(DEFAULT_ADRESHERKOMST)
            .anummer(DEFAULT_ANUMMER)
            .beschrijvinglocatie(DEFAULT_BESCHRIJVINGLOCATIE)
            .buitenlandsreisdocument(DEFAULT_BUITENLANDSREISDOCUMENT)
            .burgerlijkestaat(DEFAULT_BURGERLIJKESTAAT)
            .datumbegingeldigheidverblijfplaats(DEFAULT_DATUMBEGINGELDIGHEIDVERBLIJFPLAATS)
            .datumeindegeldigheidverblijfsplaats(DEFAULT_DATUMEINDEGELDIGHEIDVERBLIJFSPLAATS)
            .datuminschrijvinggemeente(DEFAULT_DATUMINSCHRIJVINGGEMEENTE)
            .datumopschortingbijhouding(DEFAULT_DATUMOPSCHORTINGBIJHOUDING)
            .datumvertrekuitnederland(DEFAULT_DATUMVERTREKUITNEDERLAND)
            .datumvestigingnederland(DEFAULT_DATUMVESTIGINGNEDERLAND)
            .gemeentevaninschrijving(DEFAULT_GEMEENTEVANINSCHRIJVING)
            .gezinsrelatie(DEFAULT_GEZINSRELATIE)
            .indicatiegeheim(DEFAULT_INDICATIEGEHEIM)
            .ingezetene(DEFAULT_INGEZETENE)
            .landwaarnaarvertrokken(DEFAULT_LANDWAARNAARVERTROKKEN)
            .landwaarvandaaningeschreven(DEFAULT_LANDWAARVANDAANINGESCHREVEN)
            .ouder1(DEFAULT_OUDER_1)
            .ouder2(DEFAULT_OUDER_2)
            .partnerid(DEFAULT_PARTNERID)
            .redeneindebewoning(DEFAULT_REDENEINDEBEWONING)
            .redenopschortingbijhouding(DEFAULT_REDENOPSCHORTINGBIJHOUDING)
            .signaleringreisdocument(DEFAULT_SIGNALERINGREISDOCUMENT)
            .verblijfstitel(DEFAULT_VERBLIJFSTITEL);
        return ingeschrevenpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ingeschrevenpersoon createUpdatedEntity(EntityManager em) {
        Ingeschrevenpersoon ingeschrevenpersoon = new Ingeschrevenpersoon()
            .adresherkomst(UPDATED_ADRESHERKOMST)
            .anummer(UPDATED_ANUMMER)
            .beschrijvinglocatie(UPDATED_BESCHRIJVINGLOCATIE)
            .buitenlandsreisdocument(UPDATED_BUITENLANDSREISDOCUMENT)
            .burgerlijkestaat(UPDATED_BURGERLIJKESTAAT)
            .datumbegingeldigheidverblijfplaats(UPDATED_DATUMBEGINGELDIGHEIDVERBLIJFPLAATS)
            .datumeindegeldigheidverblijfsplaats(UPDATED_DATUMEINDEGELDIGHEIDVERBLIJFSPLAATS)
            .datuminschrijvinggemeente(UPDATED_DATUMINSCHRIJVINGGEMEENTE)
            .datumopschortingbijhouding(UPDATED_DATUMOPSCHORTINGBIJHOUDING)
            .datumvertrekuitnederland(UPDATED_DATUMVERTREKUITNEDERLAND)
            .datumvestigingnederland(UPDATED_DATUMVESTIGINGNEDERLAND)
            .gemeentevaninschrijving(UPDATED_GEMEENTEVANINSCHRIJVING)
            .gezinsrelatie(UPDATED_GEZINSRELATIE)
            .indicatiegeheim(UPDATED_INDICATIEGEHEIM)
            .ingezetene(UPDATED_INGEZETENE)
            .landwaarnaarvertrokken(UPDATED_LANDWAARNAARVERTROKKEN)
            .landwaarvandaaningeschreven(UPDATED_LANDWAARVANDAANINGESCHREVEN)
            .ouder1(UPDATED_OUDER_1)
            .ouder2(UPDATED_OUDER_2)
            .partnerid(UPDATED_PARTNERID)
            .redeneindebewoning(UPDATED_REDENEINDEBEWONING)
            .redenopschortingbijhouding(UPDATED_REDENOPSCHORTINGBIJHOUDING)
            .signaleringreisdocument(UPDATED_SIGNALERINGREISDOCUMENT)
            .verblijfstitel(UPDATED_VERBLIJFSTITEL);
        return ingeschrevenpersoon;
    }

    @BeforeEach
    public void initTest() {
        ingeschrevenpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createIngeschrevenpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ingeschrevenpersoon
        var returnedIngeschrevenpersoon = om.readValue(
            restIngeschrevenpersoonMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ingeschrevenpersoon)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Ingeschrevenpersoon.class
        );

        // Validate the Ingeschrevenpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertIngeschrevenpersoonUpdatableFieldsEquals(
            returnedIngeschrevenpersoon,
            getPersistedIngeschrevenpersoon(returnedIngeschrevenpersoon)
        );
    }

    @Test
    @Transactional
    void createIngeschrevenpersoonWithExistingId() throws Exception {
        // Create the Ingeschrevenpersoon with an existing ID
        ingeschrevenpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngeschrevenpersoonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ingeschrevenpersoon)))
            .andExpect(status().isBadRequest());

        // Validate the Ingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIngeschrevenpersoons() throws Exception {
        // Initialize the database
        ingeschrevenpersoonRepository.saveAndFlush(ingeschrevenpersoon);

        // Get all the ingeschrevenpersoonList
        restIngeschrevenpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingeschrevenpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresherkomst").value(hasItem(DEFAULT_ADRESHERKOMST)))
            .andExpect(jsonPath("$.[*].anummer").value(hasItem(DEFAULT_ANUMMER)))
            .andExpect(jsonPath("$.[*].beschrijvinglocatie").value(hasItem(DEFAULT_BESCHRIJVINGLOCATIE)))
            .andExpect(jsonPath("$.[*].buitenlandsreisdocument").value(hasItem(DEFAULT_BUITENLANDSREISDOCUMENT)))
            .andExpect(jsonPath("$.[*].burgerlijkestaat").value(hasItem(DEFAULT_BURGERLIJKESTAAT)))
            .andExpect(jsonPath("$.[*].datumbegingeldigheidverblijfplaats").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDVERBLIJFPLAATS)))
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidverblijfsplaats").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDVERBLIJFSPLAATS.toString()))
            )
            .andExpect(jsonPath("$.[*].datuminschrijvinggemeente").value(hasItem(DEFAULT_DATUMINSCHRIJVINGGEMEENTE)))
            .andExpect(jsonPath("$.[*].datumopschortingbijhouding").value(hasItem(DEFAULT_DATUMOPSCHORTINGBIJHOUDING)))
            .andExpect(jsonPath("$.[*].datumvertrekuitnederland").value(hasItem(DEFAULT_DATUMVERTREKUITNEDERLAND)))
            .andExpect(jsonPath("$.[*].datumvestigingnederland").value(hasItem(DEFAULT_DATUMVESTIGINGNEDERLAND)))
            .andExpect(jsonPath("$.[*].gemeentevaninschrijving").value(hasItem(DEFAULT_GEMEENTEVANINSCHRIJVING)))
            .andExpect(jsonPath("$.[*].gezinsrelatie").value(hasItem(DEFAULT_GEZINSRELATIE)))
            .andExpect(jsonPath("$.[*].indicatiegeheim").value(hasItem(DEFAULT_INDICATIEGEHEIM)))
            .andExpect(jsonPath("$.[*].ingezetene").value(hasItem(DEFAULT_INGEZETENE)))
            .andExpect(jsonPath("$.[*].landwaarnaarvertrokken").value(hasItem(DEFAULT_LANDWAARNAARVERTROKKEN)))
            .andExpect(jsonPath("$.[*].landwaarvandaaningeschreven").value(hasItem(DEFAULT_LANDWAARVANDAANINGESCHREVEN)))
            .andExpect(jsonPath("$.[*].ouder1").value(hasItem(DEFAULT_OUDER_1)))
            .andExpect(jsonPath("$.[*].ouder2").value(hasItem(DEFAULT_OUDER_2)))
            .andExpect(jsonPath("$.[*].partnerid").value(hasItem(DEFAULT_PARTNERID)))
            .andExpect(jsonPath("$.[*].redeneindebewoning").value(hasItem(DEFAULT_REDENEINDEBEWONING)))
            .andExpect(jsonPath("$.[*].redenopschortingbijhouding").value(hasItem(DEFAULT_REDENOPSCHORTINGBIJHOUDING)))
            .andExpect(jsonPath("$.[*].signaleringreisdocument").value(hasItem(DEFAULT_SIGNALERINGREISDOCUMENT)))
            .andExpect(jsonPath("$.[*].verblijfstitel").value(hasItem(DEFAULT_VERBLIJFSTITEL)));
    }

    @Test
    @Transactional
    void getIngeschrevenpersoon() throws Exception {
        // Initialize the database
        ingeschrevenpersoonRepository.saveAndFlush(ingeschrevenpersoon);

        // Get the ingeschrevenpersoon
        restIngeschrevenpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, ingeschrevenpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ingeschrevenpersoon.getId().intValue()))
            .andExpect(jsonPath("$.adresherkomst").value(DEFAULT_ADRESHERKOMST))
            .andExpect(jsonPath("$.anummer").value(DEFAULT_ANUMMER))
            .andExpect(jsonPath("$.beschrijvinglocatie").value(DEFAULT_BESCHRIJVINGLOCATIE))
            .andExpect(jsonPath("$.buitenlandsreisdocument").value(DEFAULT_BUITENLANDSREISDOCUMENT))
            .andExpect(jsonPath("$.burgerlijkestaat").value(DEFAULT_BURGERLIJKESTAAT))
            .andExpect(jsonPath("$.datumbegingeldigheidverblijfplaats").value(DEFAULT_DATUMBEGINGELDIGHEIDVERBLIJFPLAATS))
            .andExpect(jsonPath("$.datumeindegeldigheidverblijfsplaats").value(DEFAULT_DATUMEINDEGELDIGHEIDVERBLIJFSPLAATS.toString()))
            .andExpect(jsonPath("$.datuminschrijvinggemeente").value(DEFAULT_DATUMINSCHRIJVINGGEMEENTE))
            .andExpect(jsonPath("$.datumopschortingbijhouding").value(DEFAULT_DATUMOPSCHORTINGBIJHOUDING))
            .andExpect(jsonPath("$.datumvertrekuitnederland").value(DEFAULT_DATUMVERTREKUITNEDERLAND))
            .andExpect(jsonPath("$.datumvestigingnederland").value(DEFAULT_DATUMVESTIGINGNEDERLAND))
            .andExpect(jsonPath("$.gemeentevaninschrijving").value(DEFAULT_GEMEENTEVANINSCHRIJVING))
            .andExpect(jsonPath("$.gezinsrelatie").value(DEFAULT_GEZINSRELATIE))
            .andExpect(jsonPath("$.indicatiegeheim").value(DEFAULT_INDICATIEGEHEIM))
            .andExpect(jsonPath("$.ingezetene").value(DEFAULT_INGEZETENE))
            .andExpect(jsonPath("$.landwaarnaarvertrokken").value(DEFAULT_LANDWAARNAARVERTROKKEN))
            .andExpect(jsonPath("$.landwaarvandaaningeschreven").value(DEFAULT_LANDWAARVANDAANINGESCHREVEN))
            .andExpect(jsonPath("$.ouder1").value(DEFAULT_OUDER_1))
            .andExpect(jsonPath("$.ouder2").value(DEFAULT_OUDER_2))
            .andExpect(jsonPath("$.partnerid").value(DEFAULT_PARTNERID))
            .andExpect(jsonPath("$.redeneindebewoning").value(DEFAULT_REDENEINDEBEWONING))
            .andExpect(jsonPath("$.redenopschortingbijhouding").value(DEFAULT_REDENOPSCHORTINGBIJHOUDING))
            .andExpect(jsonPath("$.signaleringreisdocument").value(DEFAULT_SIGNALERINGREISDOCUMENT))
            .andExpect(jsonPath("$.verblijfstitel").value(DEFAULT_VERBLIJFSTITEL));
    }

    @Test
    @Transactional
    void getNonExistingIngeschrevenpersoon() throws Exception {
        // Get the ingeschrevenpersoon
        restIngeschrevenpersoonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIngeschrevenpersoon() throws Exception {
        // Initialize the database
        ingeschrevenpersoonRepository.saveAndFlush(ingeschrevenpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ingeschrevenpersoon
        Ingeschrevenpersoon updatedIngeschrevenpersoon = ingeschrevenpersoonRepository.findById(ingeschrevenpersoon.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIngeschrevenpersoon are not directly saved in db
        em.detach(updatedIngeschrevenpersoon);
        updatedIngeschrevenpersoon
            .adresherkomst(UPDATED_ADRESHERKOMST)
            .anummer(UPDATED_ANUMMER)
            .beschrijvinglocatie(UPDATED_BESCHRIJVINGLOCATIE)
            .buitenlandsreisdocument(UPDATED_BUITENLANDSREISDOCUMENT)
            .burgerlijkestaat(UPDATED_BURGERLIJKESTAAT)
            .datumbegingeldigheidverblijfplaats(UPDATED_DATUMBEGINGELDIGHEIDVERBLIJFPLAATS)
            .datumeindegeldigheidverblijfsplaats(UPDATED_DATUMEINDEGELDIGHEIDVERBLIJFSPLAATS)
            .datuminschrijvinggemeente(UPDATED_DATUMINSCHRIJVINGGEMEENTE)
            .datumopschortingbijhouding(UPDATED_DATUMOPSCHORTINGBIJHOUDING)
            .datumvertrekuitnederland(UPDATED_DATUMVERTREKUITNEDERLAND)
            .datumvestigingnederland(UPDATED_DATUMVESTIGINGNEDERLAND)
            .gemeentevaninschrijving(UPDATED_GEMEENTEVANINSCHRIJVING)
            .gezinsrelatie(UPDATED_GEZINSRELATIE)
            .indicatiegeheim(UPDATED_INDICATIEGEHEIM)
            .ingezetene(UPDATED_INGEZETENE)
            .landwaarnaarvertrokken(UPDATED_LANDWAARNAARVERTROKKEN)
            .landwaarvandaaningeschreven(UPDATED_LANDWAARVANDAANINGESCHREVEN)
            .ouder1(UPDATED_OUDER_1)
            .ouder2(UPDATED_OUDER_2)
            .partnerid(UPDATED_PARTNERID)
            .redeneindebewoning(UPDATED_REDENEINDEBEWONING)
            .redenopschortingbijhouding(UPDATED_REDENOPSCHORTINGBIJHOUDING)
            .signaleringreisdocument(UPDATED_SIGNALERINGREISDOCUMENT)
            .verblijfstitel(UPDATED_VERBLIJFSTITEL);

        restIngeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIngeschrevenpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedIngeschrevenpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Ingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIngeschrevenpersoonToMatchAllProperties(updatedIngeschrevenpersoon);
    }

    @Test
    @Transactional
    void putNonExistingIngeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ingeschrevenpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIngeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIngeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngeschrevenpersoonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ingeschrevenpersoon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIngeschrevenpersoonWithPatch() throws Exception {
        // Initialize the database
        ingeschrevenpersoonRepository.saveAndFlush(ingeschrevenpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ingeschrevenpersoon using partial update
        Ingeschrevenpersoon partialUpdatedIngeschrevenpersoon = new Ingeschrevenpersoon();
        partialUpdatedIngeschrevenpersoon.setId(ingeschrevenpersoon.getId());

        partialUpdatedIngeschrevenpersoon
            .adresherkomst(UPDATED_ADRESHERKOMST)
            .beschrijvinglocatie(UPDATED_BESCHRIJVINGLOCATIE)
            .datuminschrijvinggemeente(UPDATED_DATUMINSCHRIJVINGGEMEENTE)
            .datumvertrekuitnederland(UPDATED_DATUMVERTREKUITNEDERLAND)
            .datumvestigingnederland(UPDATED_DATUMVESTIGINGNEDERLAND)
            .landwaarvandaaningeschreven(UPDATED_LANDWAARVANDAANINGESCHREVEN)
            .verblijfstitel(UPDATED_VERBLIJFSTITEL);

        restIngeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIngeschrevenpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIngeschrevenpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Ingeschrevenpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIngeschrevenpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedIngeschrevenpersoon, ingeschrevenpersoon),
            getPersistedIngeschrevenpersoon(ingeschrevenpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateIngeschrevenpersoonWithPatch() throws Exception {
        // Initialize the database
        ingeschrevenpersoonRepository.saveAndFlush(ingeschrevenpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ingeschrevenpersoon using partial update
        Ingeschrevenpersoon partialUpdatedIngeschrevenpersoon = new Ingeschrevenpersoon();
        partialUpdatedIngeschrevenpersoon.setId(ingeschrevenpersoon.getId());

        partialUpdatedIngeschrevenpersoon
            .adresherkomst(UPDATED_ADRESHERKOMST)
            .anummer(UPDATED_ANUMMER)
            .beschrijvinglocatie(UPDATED_BESCHRIJVINGLOCATIE)
            .buitenlandsreisdocument(UPDATED_BUITENLANDSREISDOCUMENT)
            .burgerlijkestaat(UPDATED_BURGERLIJKESTAAT)
            .datumbegingeldigheidverblijfplaats(UPDATED_DATUMBEGINGELDIGHEIDVERBLIJFPLAATS)
            .datumeindegeldigheidverblijfsplaats(UPDATED_DATUMEINDEGELDIGHEIDVERBLIJFSPLAATS)
            .datuminschrijvinggemeente(UPDATED_DATUMINSCHRIJVINGGEMEENTE)
            .datumopschortingbijhouding(UPDATED_DATUMOPSCHORTINGBIJHOUDING)
            .datumvertrekuitnederland(UPDATED_DATUMVERTREKUITNEDERLAND)
            .datumvestigingnederland(UPDATED_DATUMVESTIGINGNEDERLAND)
            .gemeentevaninschrijving(UPDATED_GEMEENTEVANINSCHRIJVING)
            .gezinsrelatie(UPDATED_GEZINSRELATIE)
            .indicatiegeheim(UPDATED_INDICATIEGEHEIM)
            .ingezetene(UPDATED_INGEZETENE)
            .landwaarnaarvertrokken(UPDATED_LANDWAARNAARVERTROKKEN)
            .landwaarvandaaningeschreven(UPDATED_LANDWAARVANDAANINGESCHREVEN)
            .ouder1(UPDATED_OUDER_1)
            .ouder2(UPDATED_OUDER_2)
            .partnerid(UPDATED_PARTNERID)
            .redeneindebewoning(UPDATED_REDENEINDEBEWONING)
            .redenopschortingbijhouding(UPDATED_REDENOPSCHORTINGBIJHOUDING)
            .signaleringreisdocument(UPDATED_SIGNALERINGREISDOCUMENT)
            .verblijfstitel(UPDATED_VERBLIJFSTITEL);

        restIngeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIngeschrevenpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIngeschrevenpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Ingeschrevenpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIngeschrevenpersoonUpdatableFieldsEquals(
            partialUpdatedIngeschrevenpersoon,
            getPersistedIngeschrevenpersoon(partialUpdatedIngeschrevenpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingIngeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ingeschrevenpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIngeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIngeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngeschrevenpersoonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ingeschrevenpersoon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIngeschrevenpersoon() throws Exception {
        // Initialize the database
        ingeschrevenpersoonRepository.saveAndFlush(ingeschrevenpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ingeschrevenpersoon
        restIngeschrevenpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, ingeschrevenpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ingeschrevenpersoonRepository.count();
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

    protected Ingeschrevenpersoon getPersistedIngeschrevenpersoon(Ingeschrevenpersoon ingeschrevenpersoon) {
        return ingeschrevenpersoonRepository.findById(ingeschrevenpersoon.getId()).orElseThrow();
    }

    protected void assertPersistedIngeschrevenpersoonToMatchAllProperties(Ingeschrevenpersoon expectedIngeschrevenpersoon) {
        assertIngeschrevenpersoonAllPropertiesEquals(
            expectedIngeschrevenpersoon,
            getPersistedIngeschrevenpersoon(expectedIngeschrevenpersoon)
        );
    }

    protected void assertPersistedIngeschrevenpersoonToMatchUpdatableProperties(Ingeschrevenpersoon expectedIngeschrevenpersoon) {
        assertIngeschrevenpersoonAllUpdatablePropertiesEquals(
            expectedIngeschrevenpersoon,
            getPersistedIngeschrevenpersoon(expectedIngeschrevenpersoon)
        );
    }
}
