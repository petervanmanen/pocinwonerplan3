package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SubsidieAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Subsidie;
import nl.ritense.demo.repository.SubsidieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SubsidieResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class SubsidieResourceIT {

    private static final String DEFAULT_ACCOUNTANTSCONTROLE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNTANTSCONTROLE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_COFINANCIERING = new BigDecimal(1);
    private static final BigDecimal UPDATED_COFINANCIERING = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATUMBEHANDELTERMIJN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEHANDELTERMIJN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMBEWAARTERMIJN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEWAARTERMIJN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSUBSIDIEVASTSTELLING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSUBSIDIEVASTSTELLING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMVERZENDINGEINDEAFREKENING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMVERZENDINGEINDEAFREKENING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DEADLINEINDIENING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEADLINEINDIENING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DOELSTELLING = "AAAAAAAAAA";
    private static final String UPDATED_DOELSTELLING = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_GEREALISEERDEPROJECTKOSTEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GEREALISEERDEPROJECTKOSTEN = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_HOOGTESUBSIDIE = new BigDecimal(1);
    private static final BigDecimal UPDATED_HOOGTESUBSIDIE = new BigDecimal(2);

    private static final String DEFAULT_NIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_NIVEAU = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERWERP = "AAAAAAAAAA";
    private static final String UPDATED_ONDERWERP = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ONTVANGENBEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_ONTVANGENBEDRAG = new BigDecimal(2);

    private static final String DEFAULT_OPMERKINGEN = "AAAAAAAAAA";
    private static final String UPDATED_OPMERKINGEN = "BBBBBBBBBB";

    private static final String DEFAULT_OPMERKINGENVOORSCHOTTEN = "AAAAAAAAAA";
    private static final String UPDATED_OPMERKINGENVOORSCHOTTEN = "BBBBBBBBBB";

    private static final String DEFAULT_PRESTATIESUBSIDIE = "AAAAAAAAAA";
    private static final String UPDATED_PRESTATIESUBSIDIE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SOCIALRETURNBEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_SOCIALRETURNBEDRAG = new BigDecimal(2);

    private static final String DEFAULT_SOCIALRETURNNAGEKOMEN = "AAAAAAAAAA";
    private static final String UPDATED_SOCIALRETURNNAGEKOMEN = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIALRETURNVERPLICHTING = "AAAAAAAAAA";
    private static final String UPDATED_SOCIALRETURNVERPLICHTING = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SUBSIDIEBEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUBSIDIEBEDRAG = new BigDecimal(2);

    private static final String DEFAULT_SUBSIDIESOORT = "AAAAAAAAAA";
    private static final String UPDATED_SUBSIDIESOORT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SUBSIDIEVASTSTELLINGBEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUBSIDIEVASTSTELLINGBEDRAG = new BigDecimal(2);

    private static final String DEFAULT_UITGAANDESUBSIDIE = "AAAAAAAAAA";
    private static final String UPDATED_UITGAANDESUBSIDIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VERANTWOORDENOP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VERANTWOORDENOP = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/subsidies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SubsidieRepository subsidieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubsidieMockMvc;

    private Subsidie subsidie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subsidie createEntity(EntityManager em) {
        Subsidie subsidie = new Subsidie()
            .accountantscontrole(DEFAULT_ACCOUNTANTSCONTROLE)
            .cofinanciering(DEFAULT_COFINANCIERING)
            .datumbehandeltermijn(DEFAULT_DATUMBEHANDELTERMIJN)
            .datumbewaartermijn(DEFAULT_DATUMBEWAARTERMIJN)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .datumsubsidievaststelling(DEFAULT_DATUMSUBSIDIEVASTSTELLING)
            .datumverzendingeindeafrekening(DEFAULT_DATUMVERZENDINGEINDEAFREKENING)
            .deadlineindiening(DEFAULT_DEADLINEINDIENING)
            .doelstelling(DEFAULT_DOELSTELLING)
            .gerealiseerdeprojectkosten(DEFAULT_GEREALISEERDEPROJECTKOSTEN)
            .hoogtesubsidie(DEFAULT_HOOGTESUBSIDIE)
            .niveau(DEFAULT_NIVEAU)
            .onderwerp(DEFAULT_ONDERWERP)
            .ontvangenbedrag(DEFAULT_ONTVANGENBEDRAG)
            .opmerkingen(DEFAULT_OPMERKINGEN)
            .opmerkingenvoorschotten(DEFAULT_OPMERKINGENVOORSCHOTTEN)
            .prestatiesubsidie(DEFAULT_PRESTATIESUBSIDIE)
            .socialreturnbedrag(DEFAULT_SOCIALRETURNBEDRAG)
            .socialreturnnagekomen(DEFAULT_SOCIALRETURNNAGEKOMEN)
            .socialreturnverplichting(DEFAULT_SOCIALRETURNVERPLICHTING)
            .status(DEFAULT_STATUS)
            .subsidiebedrag(DEFAULT_SUBSIDIEBEDRAG)
            .subsidiesoort(DEFAULT_SUBSIDIESOORT)
            .subsidievaststellingbedrag(DEFAULT_SUBSIDIEVASTSTELLINGBEDRAG)
            .uitgaandesubsidie(DEFAULT_UITGAANDESUBSIDIE)
            .verantwoordenop(DEFAULT_VERANTWOORDENOP);
        return subsidie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subsidie createUpdatedEntity(EntityManager em) {
        Subsidie subsidie = new Subsidie()
            .accountantscontrole(UPDATED_ACCOUNTANTSCONTROLE)
            .cofinanciering(UPDATED_COFINANCIERING)
            .datumbehandeltermijn(UPDATED_DATUMBEHANDELTERMIJN)
            .datumbewaartermijn(UPDATED_DATUMBEWAARTERMIJN)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumsubsidievaststelling(UPDATED_DATUMSUBSIDIEVASTSTELLING)
            .datumverzendingeindeafrekening(UPDATED_DATUMVERZENDINGEINDEAFREKENING)
            .deadlineindiening(UPDATED_DEADLINEINDIENING)
            .doelstelling(UPDATED_DOELSTELLING)
            .gerealiseerdeprojectkosten(UPDATED_GEREALISEERDEPROJECTKOSTEN)
            .hoogtesubsidie(UPDATED_HOOGTESUBSIDIE)
            .niveau(UPDATED_NIVEAU)
            .onderwerp(UPDATED_ONDERWERP)
            .ontvangenbedrag(UPDATED_ONTVANGENBEDRAG)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .opmerkingenvoorschotten(UPDATED_OPMERKINGENVOORSCHOTTEN)
            .prestatiesubsidie(UPDATED_PRESTATIESUBSIDIE)
            .socialreturnbedrag(UPDATED_SOCIALRETURNBEDRAG)
            .socialreturnnagekomen(UPDATED_SOCIALRETURNNAGEKOMEN)
            .socialreturnverplichting(UPDATED_SOCIALRETURNVERPLICHTING)
            .status(UPDATED_STATUS)
            .subsidiebedrag(UPDATED_SUBSIDIEBEDRAG)
            .subsidiesoort(UPDATED_SUBSIDIESOORT)
            .subsidievaststellingbedrag(UPDATED_SUBSIDIEVASTSTELLINGBEDRAG)
            .uitgaandesubsidie(UPDATED_UITGAANDESUBSIDIE)
            .verantwoordenop(UPDATED_VERANTWOORDENOP);
        return subsidie;
    }

    @BeforeEach
    public void initTest() {
        subsidie = createEntity(em);
    }

    @Test
    @Transactional
    void createSubsidie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Subsidie
        var returnedSubsidie = om.readValue(
            restSubsidieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subsidie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Subsidie.class
        );

        // Validate the Subsidie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSubsidieUpdatableFieldsEquals(returnedSubsidie, getPersistedSubsidie(returnedSubsidie));
    }

    @Test
    @Transactional
    void createSubsidieWithExistingId() throws Exception {
        // Create the Subsidie with an existing ID
        subsidie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubsidieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subsidie)))
            .andExpect(status().isBadRequest());

        // Validate the Subsidie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubsidies() throws Exception {
        // Initialize the database
        subsidieRepository.saveAndFlush(subsidie);

        // Get all the subsidieList
        restSubsidieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subsidie.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountantscontrole").value(hasItem(DEFAULT_ACCOUNTANTSCONTROLE)))
            .andExpect(jsonPath("$.[*].cofinanciering").value(hasItem(sameNumber(DEFAULT_COFINANCIERING))))
            .andExpect(jsonPath("$.[*].datumbehandeltermijn").value(hasItem(DEFAULT_DATUMBEHANDELTERMIJN.toString())))
            .andExpect(jsonPath("$.[*].datumbewaartermijn").value(hasItem(DEFAULT_DATUMBEWAARTERMIJN.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].datumsubsidievaststelling").value(hasItem(DEFAULT_DATUMSUBSIDIEVASTSTELLING.toString())))
            .andExpect(jsonPath("$.[*].datumverzendingeindeafrekening").value(hasItem(DEFAULT_DATUMVERZENDINGEINDEAFREKENING.toString())))
            .andExpect(jsonPath("$.[*].deadlineindiening").value(hasItem(DEFAULT_DEADLINEINDIENING.toString())))
            .andExpect(jsonPath("$.[*].doelstelling").value(hasItem(DEFAULT_DOELSTELLING)))
            .andExpect(jsonPath("$.[*].gerealiseerdeprojectkosten").value(hasItem(DEFAULT_GEREALISEERDEPROJECTKOSTEN.toString())))
            .andExpect(jsonPath("$.[*].hoogtesubsidie").value(hasItem(sameNumber(DEFAULT_HOOGTESUBSIDIE))))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU)))
            .andExpect(jsonPath("$.[*].onderwerp").value(hasItem(DEFAULT_ONDERWERP)))
            .andExpect(jsonPath("$.[*].ontvangenbedrag").value(hasItem(sameNumber(DEFAULT_ONTVANGENBEDRAG))))
            .andExpect(jsonPath("$.[*].opmerkingen").value(hasItem(DEFAULT_OPMERKINGEN)))
            .andExpect(jsonPath("$.[*].opmerkingenvoorschotten").value(hasItem(DEFAULT_OPMERKINGENVOORSCHOTTEN)))
            .andExpect(jsonPath("$.[*].prestatiesubsidie").value(hasItem(DEFAULT_PRESTATIESUBSIDIE)))
            .andExpect(jsonPath("$.[*].socialreturnbedrag").value(hasItem(sameNumber(DEFAULT_SOCIALRETURNBEDRAG))))
            .andExpect(jsonPath("$.[*].socialreturnnagekomen").value(hasItem(DEFAULT_SOCIALRETURNNAGEKOMEN)))
            .andExpect(jsonPath("$.[*].socialreturnverplichting").value(hasItem(DEFAULT_SOCIALRETURNVERPLICHTING)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].subsidiebedrag").value(hasItem(sameNumber(DEFAULT_SUBSIDIEBEDRAG))))
            .andExpect(jsonPath("$.[*].subsidiesoort").value(hasItem(DEFAULT_SUBSIDIESOORT)))
            .andExpect(jsonPath("$.[*].subsidievaststellingbedrag").value(hasItem(sameNumber(DEFAULT_SUBSIDIEVASTSTELLINGBEDRAG))))
            .andExpect(jsonPath("$.[*].uitgaandesubsidie").value(hasItem(DEFAULT_UITGAANDESUBSIDIE)))
            .andExpect(jsonPath("$.[*].verantwoordenop").value(hasItem(DEFAULT_VERANTWOORDENOP.toString())));
    }

    @Test
    @Transactional
    void getSubsidie() throws Exception {
        // Initialize the database
        subsidieRepository.saveAndFlush(subsidie);

        // Get the subsidie
        restSubsidieMockMvc
            .perform(get(ENTITY_API_URL_ID, subsidie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subsidie.getId().intValue()))
            .andExpect(jsonPath("$.accountantscontrole").value(DEFAULT_ACCOUNTANTSCONTROLE))
            .andExpect(jsonPath("$.cofinanciering").value(sameNumber(DEFAULT_COFINANCIERING)))
            .andExpect(jsonPath("$.datumbehandeltermijn").value(DEFAULT_DATUMBEHANDELTERMIJN.toString()))
            .andExpect(jsonPath("$.datumbewaartermijn").value(DEFAULT_DATUMBEWAARTERMIJN.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.datumsubsidievaststelling").value(DEFAULT_DATUMSUBSIDIEVASTSTELLING.toString()))
            .andExpect(jsonPath("$.datumverzendingeindeafrekening").value(DEFAULT_DATUMVERZENDINGEINDEAFREKENING.toString()))
            .andExpect(jsonPath("$.deadlineindiening").value(DEFAULT_DEADLINEINDIENING.toString()))
            .andExpect(jsonPath("$.doelstelling").value(DEFAULT_DOELSTELLING))
            .andExpect(jsonPath("$.gerealiseerdeprojectkosten").value(DEFAULT_GEREALISEERDEPROJECTKOSTEN.toString()))
            .andExpect(jsonPath("$.hoogtesubsidie").value(sameNumber(DEFAULT_HOOGTESUBSIDIE)))
            .andExpect(jsonPath("$.niveau").value(DEFAULT_NIVEAU))
            .andExpect(jsonPath("$.onderwerp").value(DEFAULT_ONDERWERP))
            .andExpect(jsonPath("$.ontvangenbedrag").value(sameNumber(DEFAULT_ONTVANGENBEDRAG)))
            .andExpect(jsonPath("$.opmerkingen").value(DEFAULT_OPMERKINGEN))
            .andExpect(jsonPath("$.opmerkingenvoorschotten").value(DEFAULT_OPMERKINGENVOORSCHOTTEN))
            .andExpect(jsonPath("$.prestatiesubsidie").value(DEFAULT_PRESTATIESUBSIDIE))
            .andExpect(jsonPath("$.socialreturnbedrag").value(sameNumber(DEFAULT_SOCIALRETURNBEDRAG)))
            .andExpect(jsonPath("$.socialreturnnagekomen").value(DEFAULT_SOCIALRETURNNAGEKOMEN))
            .andExpect(jsonPath("$.socialreturnverplichting").value(DEFAULT_SOCIALRETURNVERPLICHTING))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.subsidiebedrag").value(sameNumber(DEFAULT_SUBSIDIEBEDRAG)))
            .andExpect(jsonPath("$.subsidiesoort").value(DEFAULT_SUBSIDIESOORT))
            .andExpect(jsonPath("$.subsidievaststellingbedrag").value(sameNumber(DEFAULT_SUBSIDIEVASTSTELLINGBEDRAG)))
            .andExpect(jsonPath("$.uitgaandesubsidie").value(DEFAULT_UITGAANDESUBSIDIE))
            .andExpect(jsonPath("$.verantwoordenop").value(DEFAULT_VERANTWOORDENOP.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSubsidie() throws Exception {
        // Get the subsidie
        restSubsidieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubsidie() throws Exception {
        // Initialize the database
        subsidieRepository.saveAndFlush(subsidie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subsidie
        Subsidie updatedSubsidie = subsidieRepository.findById(subsidie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSubsidie are not directly saved in db
        em.detach(updatedSubsidie);
        updatedSubsidie
            .accountantscontrole(UPDATED_ACCOUNTANTSCONTROLE)
            .cofinanciering(UPDATED_COFINANCIERING)
            .datumbehandeltermijn(UPDATED_DATUMBEHANDELTERMIJN)
            .datumbewaartermijn(UPDATED_DATUMBEWAARTERMIJN)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumsubsidievaststelling(UPDATED_DATUMSUBSIDIEVASTSTELLING)
            .datumverzendingeindeafrekening(UPDATED_DATUMVERZENDINGEINDEAFREKENING)
            .deadlineindiening(UPDATED_DEADLINEINDIENING)
            .doelstelling(UPDATED_DOELSTELLING)
            .gerealiseerdeprojectkosten(UPDATED_GEREALISEERDEPROJECTKOSTEN)
            .hoogtesubsidie(UPDATED_HOOGTESUBSIDIE)
            .niveau(UPDATED_NIVEAU)
            .onderwerp(UPDATED_ONDERWERP)
            .ontvangenbedrag(UPDATED_ONTVANGENBEDRAG)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .opmerkingenvoorschotten(UPDATED_OPMERKINGENVOORSCHOTTEN)
            .prestatiesubsidie(UPDATED_PRESTATIESUBSIDIE)
            .socialreturnbedrag(UPDATED_SOCIALRETURNBEDRAG)
            .socialreturnnagekomen(UPDATED_SOCIALRETURNNAGEKOMEN)
            .socialreturnverplichting(UPDATED_SOCIALRETURNVERPLICHTING)
            .status(UPDATED_STATUS)
            .subsidiebedrag(UPDATED_SUBSIDIEBEDRAG)
            .subsidiesoort(UPDATED_SUBSIDIESOORT)
            .subsidievaststellingbedrag(UPDATED_SUBSIDIEVASTSTELLINGBEDRAG)
            .uitgaandesubsidie(UPDATED_UITGAANDESUBSIDIE)
            .verantwoordenop(UPDATED_VERANTWOORDENOP);

        restSubsidieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubsidie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSubsidie))
            )
            .andExpect(status().isOk());

        // Validate the Subsidie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSubsidieToMatchAllProperties(updatedSubsidie);
    }

    @Test
    @Transactional
    void putNonExistingSubsidie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubsidieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subsidie.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subsidie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubsidie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subsidie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubsidie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subsidie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subsidie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubsidieWithPatch() throws Exception {
        // Initialize the database
        subsidieRepository.saveAndFlush(subsidie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subsidie using partial update
        Subsidie partialUpdatedSubsidie = new Subsidie();
        partialUpdatedSubsidie.setId(subsidie.getId());

        partialUpdatedSubsidie
            .datumeinde(UPDATED_DATUMEINDE)
            .datumsubsidievaststelling(UPDATED_DATUMSUBSIDIEVASTSTELLING)
            .gerealiseerdeprojectkosten(UPDATED_GEREALISEERDEPROJECTKOSTEN)
            .hoogtesubsidie(UPDATED_HOOGTESUBSIDIE)
            .niveau(UPDATED_NIVEAU)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .opmerkingenvoorschotten(UPDATED_OPMERKINGENVOORSCHOTTEN)
            .prestatiesubsidie(UPDATED_PRESTATIESUBSIDIE)
            .socialreturnnagekomen(UPDATED_SOCIALRETURNNAGEKOMEN)
            .subsidiebedrag(UPDATED_SUBSIDIEBEDRAG)
            .subsidievaststellingbedrag(UPDATED_SUBSIDIEVASTSTELLINGBEDRAG)
            .verantwoordenop(UPDATED_VERANTWOORDENOP);

        restSubsidieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubsidie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubsidie))
            )
            .andExpect(status().isOk());

        // Validate the Subsidie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubsidieUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSubsidie, subsidie), getPersistedSubsidie(subsidie));
    }

    @Test
    @Transactional
    void fullUpdateSubsidieWithPatch() throws Exception {
        // Initialize the database
        subsidieRepository.saveAndFlush(subsidie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subsidie using partial update
        Subsidie partialUpdatedSubsidie = new Subsidie();
        partialUpdatedSubsidie.setId(subsidie.getId());

        partialUpdatedSubsidie
            .accountantscontrole(UPDATED_ACCOUNTANTSCONTROLE)
            .cofinanciering(UPDATED_COFINANCIERING)
            .datumbehandeltermijn(UPDATED_DATUMBEHANDELTERMIJN)
            .datumbewaartermijn(UPDATED_DATUMBEWAARTERMIJN)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumsubsidievaststelling(UPDATED_DATUMSUBSIDIEVASTSTELLING)
            .datumverzendingeindeafrekening(UPDATED_DATUMVERZENDINGEINDEAFREKENING)
            .deadlineindiening(UPDATED_DEADLINEINDIENING)
            .doelstelling(UPDATED_DOELSTELLING)
            .gerealiseerdeprojectkosten(UPDATED_GEREALISEERDEPROJECTKOSTEN)
            .hoogtesubsidie(UPDATED_HOOGTESUBSIDIE)
            .niveau(UPDATED_NIVEAU)
            .onderwerp(UPDATED_ONDERWERP)
            .ontvangenbedrag(UPDATED_ONTVANGENBEDRAG)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .opmerkingenvoorschotten(UPDATED_OPMERKINGENVOORSCHOTTEN)
            .prestatiesubsidie(UPDATED_PRESTATIESUBSIDIE)
            .socialreturnbedrag(UPDATED_SOCIALRETURNBEDRAG)
            .socialreturnnagekomen(UPDATED_SOCIALRETURNNAGEKOMEN)
            .socialreturnverplichting(UPDATED_SOCIALRETURNVERPLICHTING)
            .status(UPDATED_STATUS)
            .subsidiebedrag(UPDATED_SUBSIDIEBEDRAG)
            .subsidiesoort(UPDATED_SUBSIDIESOORT)
            .subsidievaststellingbedrag(UPDATED_SUBSIDIEVASTSTELLINGBEDRAG)
            .uitgaandesubsidie(UPDATED_UITGAANDESUBSIDIE)
            .verantwoordenop(UPDATED_VERANTWOORDENOP);

        restSubsidieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubsidie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubsidie))
            )
            .andExpect(status().isOk());

        // Validate the Subsidie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubsidieUpdatableFieldsEquals(partialUpdatedSubsidie, getPersistedSubsidie(partialUpdatedSubsidie));
    }

    @Test
    @Transactional
    void patchNonExistingSubsidie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubsidieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subsidie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subsidie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubsidie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subsidie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubsidie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(subsidie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subsidie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubsidie() throws Exception {
        // Initialize the database
        subsidieRepository.saveAndFlush(subsidie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the subsidie
        restSubsidieMockMvc
            .perform(delete(ENTITY_API_URL_ID, subsidie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return subsidieRepository.count();
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

    protected Subsidie getPersistedSubsidie(Subsidie subsidie) {
        return subsidieRepository.findById(subsidie.getId()).orElseThrow();
    }

    protected void assertPersistedSubsidieToMatchAllProperties(Subsidie expectedSubsidie) {
        assertSubsidieAllPropertiesEquals(expectedSubsidie, getPersistedSubsidie(expectedSubsidie));
    }

    protected void assertPersistedSubsidieToMatchUpdatableProperties(Subsidie expectedSubsidie) {
        assertSubsidieAllUpdatablePropertiesEquals(expectedSubsidie, getPersistedSubsidie(expectedSubsidie));
    }
}
