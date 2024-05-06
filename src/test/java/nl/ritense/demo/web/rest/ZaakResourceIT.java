package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ZaakAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Heffing;
import nl.ritense.demo.domain.Klantbeoordeling;
import nl.ritense.demo.domain.Zaak;
import nl.ritense.demo.repository.ZaakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ZaakResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ZaakResourceIT {

    private static final String DEFAULT_EMPTY = "AAAAAAAAAA";
    private static final String UPDATED_EMPTY = "BBBBBBBBBB";

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

    private static final String DEFAULT_DOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_DUURVERLENGING = "AAAAAAAAAA";
    private static final String UPDATED_DUURVERLENGING = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIEBETALING = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEBETALING = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIEDEELZAKEN = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEDEELZAKEN = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIEOPSCHORTING = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEOPSCHORTING = "BBBBBBBBBB";

    private static final String DEFAULT_LEGES = "AAAAAAAAAA";
    private static final String UPDATED_LEGES = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVINGRESULTAAT = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVINGRESULTAAT = "BBBBBBBBBB";

    private static final String DEFAULT_REDENOPSCHORTING = "AAAAAAAAAA";
    private static final String UPDATED_REDENOPSCHORTING = "BBBBBBBBBB";

    private static final String DEFAULT_REDENVERLENGING = "AAAAAAAAAA";
    private static final String UPDATED_REDENVERLENGING = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTINGRESULTAAT = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTINGRESULTAAT = "BBBBBBBBBB";

    private static final String DEFAULT_VERTROUWELIJKHEID = "AAAAAAAAAA";
    private static final String UPDATED_VERTROUWELIJKHEID = "BBBBBBBBBB";

    private static final String DEFAULT_ZAAKIDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_ZAAKIDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_ZAAKNIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_ZAAKNIVEAU = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/zaaks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ZaakRepository zaakRepository;

    @Mock
    private ZaakRepository zaakRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZaakMockMvc;

    private Zaak zaak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zaak createEntity(EntityManager em) {
        Zaak zaak = new Zaak()
            .empty(DEFAULT_EMPTY)
            .archiefnominatie(DEFAULT_ARCHIEFNOMINATIE)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumeindegepland(DEFAULT_DATUMEINDEGEPLAND)
            .datumeindeuiterlijkeafdoening(DEFAULT_DATUMEINDEUITERLIJKEAFDOENING)
            .datumlaatstebetaling(DEFAULT_DATUMLAATSTEBETALING)
            .datumpublicatie(DEFAULT_DATUMPUBLICATIE)
            .datumregistratie(DEFAULT_DATUMREGISTRATIE)
            .datumstart(DEFAULT_DATUMSTART)
            .datumvernietigingdossier(DEFAULT_DATUMVERNIETIGINGDOSSIER)
            .document(DEFAULT_DOCUMENT)
            .duurverlenging(DEFAULT_DUURVERLENGING)
            .indicatiebetaling(DEFAULT_INDICATIEBETALING)
            .indicatiedeelzaken(DEFAULT_INDICATIEDEELZAKEN)
            .indicatieopschorting(DEFAULT_INDICATIEOPSCHORTING)
            .leges(DEFAULT_LEGES)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .omschrijvingresultaat(DEFAULT_OMSCHRIJVINGRESULTAAT)
            .redenopschorting(DEFAULT_REDENOPSCHORTING)
            .redenverlenging(DEFAULT_REDENVERLENGING)
            .toelichting(DEFAULT_TOELICHTING)
            .toelichtingresultaat(DEFAULT_TOELICHTINGRESULTAAT)
            .vertrouwelijkheid(DEFAULT_VERTROUWELIJKHEID)
            .zaakidentificatie(DEFAULT_ZAAKIDENTIFICATIE)
            .zaakniveau(DEFAULT_ZAAKNIVEAU);
        // Add required entity
        Klantbeoordeling klantbeoordeling;
        if (TestUtil.findAll(em, Klantbeoordeling.class).isEmpty()) {
            klantbeoordeling = KlantbeoordelingResourceIT.createEntity(em);
            em.persist(klantbeoordeling);
            em.flush();
        } else {
            klantbeoordeling = TestUtil.findAll(em, Klantbeoordeling.class).get(0);
        }
        zaak.setHeeftKlantbeoordeling(klantbeoordeling);
        // Add required entity
        Heffing heffing;
        if (TestUtil.findAll(em, Heffing.class).isEmpty()) {
            heffing = HeffingResourceIT.createEntity(em);
            em.persist(heffing);
            em.flush();
        } else {
            heffing = TestUtil.findAll(em, Heffing.class).get(0);
        }
        zaak.setHeeftHeffing(heffing);
        return zaak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zaak createUpdatedEntity(EntityManager em) {
        Zaak zaak = new Zaak()
            .empty(UPDATED_EMPTY)
            .archiefnominatie(UPDATED_ARCHIEFNOMINATIE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegepland(UPDATED_DATUMEINDEGEPLAND)
            .datumeindeuiterlijkeafdoening(UPDATED_DATUMEINDEUITERLIJKEAFDOENING)
            .datumlaatstebetaling(UPDATED_DATUMLAATSTEBETALING)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumregistratie(UPDATED_DATUMREGISTRATIE)
            .datumstart(UPDATED_DATUMSTART)
            .datumvernietigingdossier(UPDATED_DATUMVERNIETIGINGDOSSIER)
            .document(UPDATED_DOCUMENT)
            .duurverlenging(UPDATED_DUURVERLENGING)
            .indicatiebetaling(UPDATED_INDICATIEBETALING)
            .indicatiedeelzaken(UPDATED_INDICATIEDEELZAKEN)
            .indicatieopschorting(UPDATED_INDICATIEOPSCHORTING)
            .leges(UPDATED_LEGES)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .omschrijvingresultaat(UPDATED_OMSCHRIJVINGRESULTAAT)
            .redenopschorting(UPDATED_REDENOPSCHORTING)
            .redenverlenging(UPDATED_REDENVERLENGING)
            .toelichting(UPDATED_TOELICHTING)
            .toelichtingresultaat(UPDATED_TOELICHTINGRESULTAAT)
            .vertrouwelijkheid(UPDATED_VERTROUWELIJKHEID)
            .zaakidentificatie(UPDATED_ZAAKIDENTIFICATIE)
            .zaakniveau(UPDATED_ZAAKNIVEAU);
        // Add required entity
        Klantbeoordeling klantbeoordeling;
        if (TestUtil.findAll(em, Klantbeoordeling.class).isEmpty()) {
            klantbeoordeling = KlantbeoordelingResourceIT.createUpdatedEntity(em);
            em.persist(klantbeoordeling);
            em.flush();
        } else {
            klantbeoordeling = TestUtil.findAll(em, Klantbeoordeling.class).get(0);
        }
        zaak.setHeeftKlantbeoordeling(klantbeoordeling);
        // Add required entity
        Heffing heffing;
        if (TestUtil.findAll(em, Heffing.class).isEmpty()) {
            heffing = HeffingResourceIT.createUpdatedEntity(em);
            em.persist(heffing);
            em.flush();
        } else {
            heffing = TestUtil.findAll(em, Heffing.class).get(0);
        }
        zaak.setHeeftHeffing(heffing);
        return zaak;
    }

    @BeforeEach
    public void initTest() {
        zaak = createEntity(em);
    }

    @Test
    @Transactional
    void createZaak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Zaak
        var returnedZaak = om.readValue(
            restZaakMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zaak)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Zaak.class
        );

        // Validate the Zaak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertZaakUpdatableFieldsEquals(returnedZaak, getPersistedZaak(returnedZaak));
    }

    @Test
    @Transactional
    void createZaakWithExistingId() throws Exception {
        // Create the Zaak with an existing ID
        zaak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restZaakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zaak)))
            .andExpect(status().isBadRequest());

        // Validate the Zaak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllZaaks() throws Exception {
        // Initialize the database
        zaakRepository.saveAndFlush(zaak);

        // Get all the zaakList
        restZaakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zaak.getId().intValue())))
            .andExpect(jsonPath("$.[*].empty").value(hasItem(DEFAULT_EMPTY)))
            .andExpect(jsonPath("$.[*].archiefnominatie").value(hasItem(DEFAULT_ARCHIEFNOMINATIE)))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE)))
            .andExpect(jsonPath("$.[*].datumeindegepland").value(hasItem(DEFAULT_DATUMEINDEGEPLAND)))
            .andExpect(jsonPath("$.[*].datumeindeuiterlijkeafdoening").value(hasItem(DEFAULT_DATUMEINDEUITERLIJKEAFDOENING)))
            .andExpect(jsonPath("$.[*].datumlaatstebetaling").value(hasItem(DEFAULT_DATUMLAATSTEBETALING)))
            .andExpect(jsonPath("$.[*].datumpublicatie").value(hasItem(DEFAULT_DATUMPUBLICATIE)))
            .andExpect(jsonPath("$.[*].datumregistratie").value(hasItem(DEFAULT_DATUMREGISTRATIE)))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART)))
            .andExpect(jsonPath("$.[*].datumvernietigingdossier").value(hasItem(DEFAULT_DATUMVERNIETIGINGDOSSIER)))
            .andExpect(jsonPath("$.[*].document").value(hasItem(DEFAULT_DOCUMENT)))
            .andExpect(jsonPath("$.[*].duurverlenging").value(hasItem(DEFAULT_DUURVERLENGING)))
            .andExpect(jsonPath("$.[*].indicatiebetaling").value(hasItem(DEFAULT_INDICATIEBETALING)))
            .andExpect(jsonPath("$.[*].indicatiedeelzaken").value(hasItem(DEFAULT_INDICATIEDEELZAKEN)))
            .andExpect(jsonPath("$.[*].indicatieopschorting").value(hasItem(DEFAULT_INDICATIEOPSCHORTING)))
            .andExpect(jsonPath("$.[*].leges").value(hasItem(DEFAULT_LEGES)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].omschrijvingresultaat").value(hasItem(DEFAULT_OMSCHRIJVINGRESULTAAT)))
            .andExpect(jsonPath("$.[*].redenopschorting").value(hasItem(DEFAULT_REDENOPSCHORTING)))
            .andExpect(jsonPath("$.[*].redenverlenging").value(hasItem(DEFAULT_REDENVERLENGING)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)))
            .andExpect(jsonPath("$.[*].toelichtingresultaat").value(hasItem(DEFAULT_TOELICHTINGRESULTAAT)))
            .andExpect(jsonPath("$.[*].vertrouwelijkheid").value(hasItem(DEFAULT_VERTROUWELIJKHEID)))
            .andExpect(jsonPath("$.[*].zaakidentificatie").value(hasItem(DEFAULT_ZAAKIDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].zaakniveau").value(hasItem(DEFAULT_ZAAKNIVEAU)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllZaaksWithEagerRelationshipsIsEnabled() throws Exception {
        when(zaakRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restZaakMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(zaakRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllZaaksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(zaakRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restZaakMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(zaakRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getZaak() throws Exception {
        // Initialize the database
        zaakRepository.saveAndFlush(zaak);

        // Get the zaak
        restZaakMockMvc
            .perform(get(ENTITY_API_URL_ID, zaak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(zaak.getId().intValue()))
            .andExpect(jsonPath("$.empty").value(DEFAULT_EMPTY))
            .andExpect(jsonPath("$.archiefnominatie").value(DEFAULT_ARCHIEFNOMINATIE))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE))
            .andExpect(jsonPath("$.datumeindegepland").value(DEFAULT_DATUMEINDEGEPLAND))
            .andExpect(jsonPath("$.datumeindeuiterlijkeafdoening").value(DEFAULT_DATUMEINDEUITERLIJKEAFDOENING))
            .andExpect(jsonPath("$.datumlaatstebetaling").value(DEFAULT_DATUMLAATSTEBETALING))
            .andExpect(jsonPath("$.datumpublicatie").value(DEFAULT_DATUMPUBLICATIE))
            .andExpect(jsonPath("$.datumregistratie").value(DEFAULT_DATUMREGISTRATIE))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART))
            .andExpect(jsonPath("$.datumvernietigingdossier").value(DEFAULT_DATUMVERNIETIGINGDOSSIER))
            .andExpect(jsonPath("$.document").value(DEFAULT_DOCUMENT))
            .andExpect(jsonPath("$.duurverlenging").value(DEFAULT_DUURVERLENGING))
            .andExpect(jsonPath("$.indicatiebetaling").value(DEFAULT_INDICATIEBETALING))
            .andExpect(jsonPath("$.indicatiedeelzaken").value(DEFAULT_INDICATIEDEELZAKEN))
            .andExpect(jsonPath("$.indicatieopschorting").value(DEFAULT_INDICATIEOPSCHORTING))
            .andExpect(jsonPath("$.leges").value(DEFAULT_LEGES))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.omschrijvingresultaat").value(DEFAULT_OMSCHRIJVINGRESULTAAT))
            .andExpect(jsonPath("$.redenopschorting").value(DEFAULT_REDENOPSCHORTING))
            .andExpect(jsonPath("$.redenverlenging").value(DEFAULT_REDENVERLENGING))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING))
            .andExpect(jsonPath("$.toelichtingresultaat").value(DEFAULT_TOELICHTINGRESULTAAT))
            .andExpect(jsonPath("$.vertrouwelijkheid").value(DEFAULT_VERTROUWELIJKHEID))
            .andExpect(jsonPath("$.zaakidentificatie").value(DEFAULT_ZAAKIDENTIFICATIE))
            .andExpect(jsonPath("$.zaakniveau").value(DEFAULT_ZAAKNIVEAU));
    }

    @Test
    @Transactional
    void getNonExistingZaak() throws Exception {
        // Get the zaak
        restZaakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingZaak() throws Exception {
        // Initialize the database
        zaakRepository.saveAndFlush(zaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zaak
        Zaak updatedZaak = zaakRepository.findById(zaak.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedZaak are not directly saved in db
        em.detach(updatedZaak);
        updatedZaak
            .empty(UPDATED_EMPTY)
            .archiefnominatie(UPDATED_ARCHIEFNOMINATIE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegepland(UPDATED_DATUMEINDEGEPLAND)
            .datumeindeuiterlijkeafdoening(UPDATED_DATUMEINDEUITERLIJKEAFDOENING)
            .datumlaatstebetaling(UPDATED_DATUMLAATSTEBETALING)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumregistratie(UPDATED_DATUMREGISTRATIE)
            .datumstart(UPDATED_DATUMSTART)
            .datumvernietigingdossier(UPDATED_DATUMVERNIETIGINGDOSSIER)
            .document(UPDATED_DOCUMENT)
            .duurverlenging(UPDATED_DUURVERLENGING)
            .indicatiebetaling(UPDATED_INDICATIEBETALING)
            .indicatiedeelzaken(UPDATED_INDICATIEDEELZAKEN)
            .indicatieopschorting(UPDATED_INDICATIEOPSCHORTING)
            .leges(UPDATED_LEGES)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .omschrijvingresultaat(UPDATED_OMSCHRIJVINGRESULTAAT)
            .redenopschorting(UPDATED_REDENOPSCHORTING)
            .redenverlenging(UPDATED_REDENVERLENGING)
            .toelichting(UPDATED_TOELICHTING)
            .toelichtingresultaat(UPDATED_TOELICHTINGRESULTAAT)
            .vertrouwelijkheid(UPDATED_VERTROUWELIJKHEID)
            .zaakidentificatie(UPDATED_ZAAKIDENTIFICATIE)
            .zaakniveau(UPDATED_ZAAKNIVEAU);

        restZaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedZaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedZaak))
            )
            .andExpect(status().isOk());

        // Validate the Zaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedZaakToMatchAllProperties(updatedZaak);
    }

    @Test
    @Transactional
    void putNonExistingZaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZaakMockMvc
            .perform(put(ENTITY_API_URL_ID, zaak.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zaak)))
            .andExpect(status().isBadRequest());

        // Validate the Zaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchZaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(zaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamZaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZaakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zaak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateZaakWithPatch() throws Exception {
        // Initialize the database
        zaakRepository.saveAndFlush(zaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zaak using partial update
        Zaak partialUpdatedZaak = new Zaak();
        partialUpdatedZaak.setId(zaak.getId());

        partialUpdatedZaak
            .datumeindegepland(UPDATED_DATUMEINDEGEPLAND)
            .datumeindeuiterlijkeafdoening(UPDATED_DATUMEINDEUITERLIJKEAFDOENING)
            .datumlaatstebetaling(UPDATED_DATUMLAATSTEBETALING)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .indicatiebetaling(UPDATED_INDICATIEBETALING)
            .omschrijvingresultaat(UPDATED_OMSCHRIJVINGRESULTAAT)
            .vertrouwelijkheid(UPDATED_VERTROUWELIJKHEID)
            .zaakidentificatie(UPDATED_ZAAKIDENTIFICATIE);

        restZaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZaak))
            )
            .andExpect(status().isOk());

        // Validate the Zaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZaakUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedZaak, zaak), getPersistedZaak(zaak));
    }

    @Test
    @Transactional
    void fullUpdateZaakWithPatch() throws Exception {
        // Initialize the database
        zaakRepository.saveAndFlush(zaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zaak using partial update
        Zaak partialUpdatedZaak = new Zaak();
        partialUpdatedZaak.setId(zaak.getId());

        partialUpdatedZaak
            .empty(UPDATED_EMPTY)
            .archiefnominatie(UPDATED_ARCHIEFNOMINATIE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegepland(UPDATED_DATUMEINDEGEPLAND)
            .datumeindeuiterlijkeafdoening(UPDATED_DATUMEINDEUITERLIJKEAFDOENING)
            .datumlaatstebetaling(UPDATED_DATUMLAATSTEBETALING)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumregistratie(UPDATED_DATUMREGISTRATIE)
            .datumstart(UPDATED_DATUMSTART)
            .datumvernietigingdossier(UPDATED_DATUMVERNIETIGINGDOSSIER)
            .document(UPDATED_DOCUMENT)
            .duurverlenging(UPDATED_DUURVERLENGING)
            .indicatiebetaling(UPDATED_INDICATIEBETALING)
            .indicatiedeelzaken(UPDATED_INDICATIEDEELZAKEN)
            .indicatieopschorting(UPDATED_INDICATIEOPSCHORTING)
            .leges(UPDATED_LEGES)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .omschrijvingresultaat(UPDATED_OMSCHRIJVINGRESULTAAT)
            .redenopschorting(UPDATED_REDENOPSCHORTING)
            .redenverlenging(UPDATED_REDENVERLENGING)
            .toelichting(UPDATED_TOELICHTING)
            .toelichtingresultaat(UPDATED_TOELICHTINGRESULTAAT)
            .vertrouwelijkheid(UPDATED_VERTROUWELIJKHEID)
            .zaakidentificatie(UPDATED_ZAAKIDENTIFICATIE)
            .zaakniveau(UPDATED_ZAAKNIVEAU);

        restZaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZaak))
            )
            .andExpect(status().isOk());

        // Validate the Zaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZaakUpdatableFieldsEquals(partialUpdatedZaak, getPersistedZaak(partialUpdatedZaak));
    }

    @Test
    @Transactional
    void patchNonExistingZaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZaakMockMvc
            .perform(patch(ENTITY_API_URL_ID, zaak.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(zaak)))
            .andExpect(status().isBadRequest());

        // Validate the Zaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchZaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(zaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamZaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZaakMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(zaak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteZaak() throws Exception {
        // Initialize the database
        zaakRepository.saveAndFlush(zaak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the zaak
        restZaakMockMvc
            .perform(delete(ENTITY_API_URL_ID, zaak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return zaakRepository.count();
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

    protected Zaak getPersistedZaak(Zaak zaak) {
        return zaakRepository.findById(zaak.getId()).orElseThrow();
    }

    protected void assertPersistedZaakToMatchAllProperties(Zaak expectedZaak) {
        assertZaakAllPropertiesEquals(expectedZaak, getPersistedZaak(expectedZaak));
    }

    protected void assertPersistedZaakToMatchUpdatableProperties(Zaak expectedZaak) {
        assertZaakAllUpdatablePropertiesEquals(expectedZaak, getPersistedZaak(expectedZaak));
    }
}
