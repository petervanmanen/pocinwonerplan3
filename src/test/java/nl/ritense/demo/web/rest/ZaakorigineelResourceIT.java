package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ZaakorigineelAsserts.*;
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
import nl.ritense.demo.domain.Zaakorigineel;
import nl.ritense.demo.repository.ZaakorigineelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ZaakorigineelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ZaakorigineelResourceIT {

    private static final String DEFAULT_ANDERZAAKOBJECT = "AAAAAAAAAA";
    private static final String UPDATED_ANDERZAAKOBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_ARCHIEFNOMINATIE = "AAAAAAAAAA";
    private static final String UPDATED_ARCHIEFNOMINATIE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDEGEPLAND = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDEGEPLAND = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDEUITERLIJKEAFDOENING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDEUITERLIJKEAFDOENING = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMLAATSTEBETALING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMLAATSTEBETALING = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMPUBLICATIE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMPUBLICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMREGISTRATIE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMREGISTRATIE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMSTART = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSTART = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMVERNIETIGINGDOSSIER = "AAAAAAAAAA";
    private static final String UPDATED_DATUMVERNIETIGINGDOSSIER = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIEBETALING = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEBETALING = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIEDEELZAKEN = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEDEELZAKEN = "BBBBBBBBBB";

    private static final String DEFAULT_KENMERK = "AAAAAAAAAA";
    private static final String UPDATED_KENMERK = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVINGRESULTAAT = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVINGRESULTAAT = "BBBBBBBBBB";

    private static final String DEFAULT_OPSCHORTING = "AAAAAAAAAA";
    private static final String UPDATED_OPSCHORTING = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTINGRESULTAAT = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTINGRESULTAAT = "BBBBBBBBBB";

    private static final String DEFAULT_VERLENGING = "AAAAAAAAAA";
    private static final String UPDATED_VERLENGING = "BBBBBBBBBB";

    private static final String DEFAULT_ZAAKIDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_ZAAKIDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_ZAAKNIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_ZAAKNIVEAU = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/zaakorigineels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ZaakorigineelRepository zaakorigineelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZaakorigineelMockMvc;

    private Zaakorigineel zaakorigineel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zaakorigineel createEntity(EntityManager em) {
        Zaakorigineel zaakorigineel = new Zaakorigineel()
            .anderzaakobject(DEFAULT_ANDERZAAKOBJECT)
            .archiefnominatie(DEFAULT_ARCHIEFNOMINATIE)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumeindegepland(DEFAULT_DATUMEINDEGEPLAND)
            .datumeindeuiterlijkeafdoening(DEFAULT_DATUMEINDEUITERLIJKEAFDOENING)
            .datumlaatstebetaling(DEFAULT_DATUMLAATSTEBETALING)
            .datumpublicatie(DEFAULT_DATUMPUBLICATIE)
            .datumregistratie(DEFAULT_DATUMREGISTRATIE)
            .datumstart(DEFAULT_DATUMSTART)
            .datumvernietigingdossier(DEFAULT_DATUMVERNIETIGINGDOSSIER)
            .indicatiebetaling(DEFAULT_INDICATIEBETALING)
            .indicatiedeelzaken(DEFAULT_INDICATIEDEELZAKEN)
            .kenmerk(DEFAULT_KENMERK)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .omschrijvingresultaat(DEFAULT_OMSCHRIJVINGRESULTAAT)
            .opschorting(DEFAULT_OPSCHORTING)
            .toelichting(DEFAULT_TOELICHTING)
            .toelichtingresultaat(DEFAULT_TOELICHTINGRESULTAAT)
            .verlenging(DEFAULT_VERLENGING)
            .zaakidentificatie(DEFAULT_ZAAKIDENTIFICATIE)
            .zaakniveau(DEFAULT_ZAAKNIVEAU);
        return zaakorigineel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zaakorigineel createUpdatedEntity(EntityManager em) {
        Zaakorigineel zaakorigineel = new Zaakorigineel()
            .anderzaakobject(UPDATED_ANDERZAAKOBJECT)
            .archiefnominatie(UPDATED_ARCHIEFNOMINATIE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegepland(UPDATED_DATUMEINDEGEPLAND)
            .datumeindeuiterlijkeafdoening(UPDATED_DATUMEINDEUITERLIJKEAFDOENING)
            .datumlaatstebetaling(UPDATED_DATUMLAATSTEBETALING)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumregistratie(UPDATED_DATUMREGISTRATIE)
            .datumstart(UPDATED_DATUMSTART)
            .datumvernietigingdossier(UPDATED_DATUMVERNIETIGINGDOSSIER)
            .indicatiebetaling(UPDATED_INDICATIEBETALING)
            .indicatiedeelzaken(UPDATED_INDICATIEDEELZAKEN)
            .kenmerk(UPDATED_KENMERK)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .omschrijvingresultaat(UPDATED_OMSCHRIJVINGRESULTAAT)
            .opschorting(UPDATED_OPSCHORTING)
            .toelichting(UPDATED_TOELICHTING)
            .toelichtingresultaat(UPDATED_TOELICHTINGRESULTAAT)
            .verlenging(UPDATED_VERLENGING)
            .zaakidentificatie(UPDATED_ZAAKIDENTIFICATIE)
            .zaakniveau(UPDATED_ZAAKNIVEAU);
        return zaakorigineel;
    }

    @BeforeEach
    public void initTest() {
        zaakorigineel = createEntity(em);
    }

    @Test
    @Transactional
    void createZaakorigineel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Zaakorigineel
        var returnedZaakorigineel = om.readValue(
            restZaakorigineelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zaakorigineel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Zaakorigineel.class
        );

        // Validate the Zaakorigineel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertZaakorigineelUpdatableFieldsEquals(returnedZaakorigineel, getPersistedZaakorigineel(returnedZaakorigineel));
    }

    @Test
    @Transactional
    void createZaakorigineelWithExistingId() throws Exception {
        // Create the Zaakorigineel with an existing ID
        zaakorigineel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restZaakorigineelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zaakorigineel)))
            .andExpect(status().isBadRequest());

        // Validate the Zaakorigineel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllZaakorigineels() throws Exception {
        // Initialize the database
        zaakorigineelRepository.saveAndFlush(zaakorigineel);

        // Get all the zaakorigineelList
        restZaakorigineelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zaakorigineel.getId().intValue())))
            .andExpect(jsonPath("$.[*].anderzaakobject").value(hasItem(DEFAULT_ANDERZAAKOBJECT)))
            .andExpect(jsonPath("$.[*].archiefnominatie").value(hasItem(DEFAULT_ARCHIEFNOMINATIE)))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE)))
            .andExpect(jsonPath("$.[*].datumeindegepland").value(hasItem(DEFAULT_DATUMEINDEGEPLAND)))
            .andExpect(jsonPath("$.[*].datumeindeuiterlijkeafdoening").value(hasItem(DEFAULT_DATUMEINDEUITERLIJKEAFDOENING)))
            .andExpect(jsonPath("$.[*].datumlaatstebetaling").value(hasItem(DEFAULT_DATUMLAATSTEBETALING)))
            .andExpect(jsonPath("$.[*].datumpublicatie").value(hasItem(DEFAULT_DATUMPUBLICATIE)))
            .andExpect(jsonPath("$.[*].datumregistratie").value(hasItem(DEFAULT_DATUMREGISTRATIE)))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART)))
            .andExpect(jsonPath("$.[*].datumvernietigingdossier").value(hasItem(DEFAULT_DATUMVERNIETIGINGDOSSIER)))
            .andExpect(jsonPath("$.[*].indicatiebetaling").value(hasItem(DEFAULT_INDICATIEBETALING)))
            .andExpect(jsonPath("$.[*].indicatiedeelzaken").value(hasItem(DEFAULT_INDICATIEDEELZAKEN)))
            .andExpect(jsonPath("$.[*].kenmerk").value(hasItem(DEFAULT_KENMERK)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].omschrijvingresultaat").value(hasItem(DEFAULT_OMSCHRIJVINGRESULTAAT)))
            .andExpect(jsonPath("$.[*].opschorting").value(hasItem(DEFAULT_OPSCHORTING)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)))
            .andExpect(jsonPath("$.[*].toelichtingresultaat").value(hasItem(DEFAULT_TOELICHTINGRESULTAAT)))
            .andExpect(jsonPath("$.[*].verlenging").value(hasItem(DEFAULT_VERLENGING)))
            .andExpect(jsonPath("$.[*].zaakidentificatie").value(hasItem(DEFAULT_ZAAKIDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].zaakniveau").value(hasItem(DEFAULT_ZAAKNIVEAU)));
    }

    @Test
    @Transactional
    void getZaakorigineel() throws Exception {
        // Initialize the database
        zaakorigineelRepository.saveAndFlush(zaakorigineel);

        // Get the zaakorigineel
        restZaakorigineelMockMvc
            .perform(get(ENTITY_API_URL_ID, zaakorigineel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(zaakorigineel.getId().intValue()))
            .andExpect(jsonPath("$.anderzaakobject").value(DEFAULT_ANDERZAAKOBJECT))
            .andExpect(jsonPath("$.archiefnominatie").value(DEFAULT_ARCHIEFNOMINATIE))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE))
            .andExpect(jsonPath("$.datumeindegepland").value(DEFAULT_DATUMEINDEGEPLAND))
            .andExpect(jsonPath("$.datumeindeuiterlijkeafdoening").value(DEFAULT_DATUMEINDEUITERLIJKEAFDOENING))
            .andExpect(jsonPath("$.datumlaatstebetaling").value(DEFAULT_DATUMLAATSTEBETALING))
            .andExpect(jsonPath("$.datumpublicatie").value(DEFAULT_DATUMPUBLICATIE))
            .andExpect(jsonPath("$.datumregistratie").value(DEFAULT_DATUMREGISTRATIE))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART))
            .andExpect(jsonPath("$.datumvernietigingdossier").value(DEFAULT_DATUMVERNIETIGINGDOSSIER))
            .andExpect(jsonPath("$.indicatiebetaling").value(DEFAULT_INDICATIEBETALING))
            .andExpect(jsonPath("$.indicatiedeelzaken").value(DEFAULT_INDICATIEDEELZAKEN))
            .andExpect(jsonPath("$.kenmerk").value(DEFAULT_KENMERK))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.omschrijvingresultaat").value(DEFAULT_OMSCHRIJVINGRESULTAAT))
            .andExpect(jsonPath("$.opschorting").value(DEFAULT_OPSCHORTING))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING))
            .andExpect(jsonPath("$.toelichtingresultaat").value(DEFAULT_TOELICHTINGRESULTAAT))
            .andExpect(jsonPath("$.verlenging").value(DEFAULT_VERLENGING))
            .andExpect(jsonPath("$.zaakidentificatie").value(DEFAULT_ZAAKIDENTIFICATIE))
            .andExpect(jsonPath("$.zaakniveau").value(DEFAULT_ZAAKNIVEAU));
    }

    @Test
    @Transactional
    void getNonExistingZaakorigineel() throws Exception {
        // Get the zaakorigineel
        restZaakorigineelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingZaakorigineel() throws Exception {
        // Initialize the database
        zaakorigineelRepository.saveAndFlush(zaakorigineel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zaakorigineel
        Zaakorigineel updatedZaakorigineel = zaakorigineelRepository.findById(zaakorigineel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedZaakorigineel are not directly saved in db
        em.detach(updatedZaakorigineel);
        updatedZaakorigineel
            .anderzaakobject(UPDATED_ANDERZAAKOBJECT)
            .archiefnominatie(UPDATED_ARCHIEFNOMINATIE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegepland(UPDATED_DATUMEINDEGEPLAND)
            .datumeindeuiterlijkeafdoening(UPDATED_DATUMEINDEUITERLIJKEAFDOENING)
            .datumlaatstebetaling(UPDATED_DATUMLAATSTEBETALING)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumregistratie(UPDATED_DATUMREGISTRATIE)
            .datumstart(UPDATED_DATUMSTART)
            .datumvernietigingdossier(UPDATED_DATUMVERNIETIGINGDOSSIER)
            .indicatiebetaling(UPDATED_INDICATIEBETALING)
            .indicatiedeelzaken(UPDATED_INDICATIEDEELZAKEN)
            .kenmerk(UPDATED_KENMERK)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .omschrijvingresultaat(UPDATED_OMSCHRIJVINGRESULTAAT)
            .opschorting(UPDATED_OPSCHORTING)
            .toelichting(UPDATED_TOELICHTING)
            .toelichtingresultaat(UPDATED_TOELICHTINGRESULTAAT)
            .verlenging(UPDATED_VERLENGING)
            .zaakidentificatie(UPDATED_ZAAKIDENTIFICATIE)
            .zaakniveau(UPDATED_ZAAKNIVEAU);

        restZaakorigineelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedZaakorigineel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedZaakorigineel))
            )
            .andExpect(status().isOk());

        // Validate the Zaakorigineel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedZaakorigineelToMatchAllProperties(updatedZaakorigineel);
    }

    @Test
    @Transactional
    void putNonExistingZaakorigineel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaakorigineel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZaakorigineelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, zaakorigineel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(zaakorigineel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zaakorigineel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchZaakorigineel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaakorigineel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZaakorigineelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(zaakorigineel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zaakorigineel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamZaakorigineel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaakorigineel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZaakorigineelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zaakorigineel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zaakorigineel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateZaakorigineelWithPatch() throws Exception {
        // Initialize the database
        zaakorigineelRepository.saveAndFlush(zaakorigineel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zaakorigineel using partial update
        Zaakorigineel partialUpdatedZaakorigineel = new Zaakorigineel();
        partialUpdatedZaakorigineel.setId(zaakorigineel.getId());

        partialUpdatedZaakorigineel
            .archiefnominatie(UPDATED_ARCHIEFNOMINATIE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumlaatstebetaling(UPDATED_DATUMLAATSTEBETALING)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumregistratie(UPDATED_DATUMREGISTRATIE)
            .datumstart(UPDATED_DATUMSTART)
            .datumvernietigingdossier(UPDATED_DATUMVERNIETIGINGDOSSIER)
            .indicatiebetaling(UPDATED_INDICATIEBETALING)
            .indicatiedeelzaken(UPDATED_INDICATIEDEELZAKEN)
            .zaakniveau(UPDATED_ZAAKNIVEAU);

        restZaakorigineelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZaakorigineel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZaakorigineel))
            )
            .andExpect(status().isOk());

        // Validate the Zaakorigineel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZaakorigineelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedZaakorigineel, zaakorigineel),
            getPersistedZaakorigineel(zaakorigineel)
        );
    }

    @Test
    @Transactional
    void fullUpdateZaakorigineelWithPatch() throws Exception {
        // Initialize the database
        zaakorigineelRepository.saveAndFlush(zaakorigineel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zaakorigineel using partial update
        Zaakorigineel partialUpdatedZaakorigineel = new Zaakorigineel();
        partialUpdatedZaakorigineel.setId(zaakorigineel.getId());

        partialUpdatedZaakorigineel
            .anderzaakobject(UPDATED_ANDERZAAKOBJECT)
            .archiefnominatie(UPDATED_ARCHIEFNOMINATIE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegepland(UPDATED_DATUMEINDEGEPLAND)
            .datumeindeuiterlijkeafdoening(UPDATED_DATUMEINDEUITERLIJKEAFDOENING)
            .datumlaatstebetaling(UPDATED_DATUMLAATSTEBETALING)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumregistratie(UPDATED_DATUMREGISTRATIE)
            .datumstart(UPDATED_DATUMSTART)
            .datumvernietigingdossier(UPDATED_DATUMVERNIETIGINGDOSSIER)
            .indicatiebetaling(UPDATED_INDICATIEBETALING)
            .indicatiedeelzaken(UPDATED_INDICATIEDEELZAKEN)
            .kenmerk(UPDATED_KENMERK)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .omschrijvingresultaat(UPDATED_OMSCHRIJVINGRESULTAAT)
            .opschorting(UPDATED_OPSCHORTING)
            .toelichting(UPDATED_TOELICHTING)
            .toelichtingresultaat(UPDATED_TOELICHTINGRESULTAAT)
            .verlenging(UPDATED_VERLENGING)
            .zaakidentificatie(UPDATED_ZAAKIDENTIFICATIE)
            .zaakniveau(UPDATED_ZAAKNIVEAU);

        restZaakorigineelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZaakorigineel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZaakorigineel))
            )
            .andExpect(status().isOk());

        // Validate the Zaakorigineel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZaakorigineelUpdatableFieldsEquals(partialUpdatedZaakorigineel, getPersistedZaakorigineel(partialUpdatedZaakorigineel));
    }

    @Test
    @Transactional
    void patchNonExistingZaakorigineel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaakorigineel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZaakorigineelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, zaakorigineel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(zaakorigineel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zaakorigineel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchZaakorigineel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaakorigineel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZaakorigineelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(zaakorigineel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zaakorigineel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamZaakorigineel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaakorigineel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZaakorigineelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(zaakorigineel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zaakorigineel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteZaakorigineel() throws Exception {
        // Initialize the database
        zaakorigineelRepository.saveAndFlush(zaakorigineel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the zaakorigineel
        restZaakorigineelMockMvc
            .perform(delete(ENTITY_API_URL_ID, zaakorigineel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return zaakorigineelRepository.count();
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

    protected Zaakorigineel getPersistedZaakorigineel(Zaakorigineel zaakorigineel) {
        return zaakorigineelRepository.findById(zaakorigineel.getId()).orElseThrow();
    }

    protected void assertPersistedZaakorigineelToMatchAllProperties(Zaakorigineel expectedZaakorigineel) {
        assertZaakorigineelAllPropertiesEquals(expectedZaakorigineel, getPersistedZaakorigineel(expectedZaakorigineel));
    }

    protected void assertPersistedZaakorigineelToMatchUpdatableProperties(Zaakorigineel expectedZaakorigineel) {
        assertZaakorigineelAllUpdatablePropertiesEquals(expectedZaakorigineel, getPersistedZaakorigineel(expectedZaakorigineel));
    }
}
