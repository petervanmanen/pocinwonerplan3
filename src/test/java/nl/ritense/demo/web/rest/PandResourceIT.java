package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PandAsserts.*;
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
import nl.ritense.demo.domain.Pand;
import nl.ritense.demo.repository.PandRepository;
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
 * Integration tests for the {@link PandResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class PandResourceIT {

    private static final String DEFAULT_BRUTOINHOUDPAND = "AAAAAAAAAA";
    private static final String UPDATED_BRUTOINHOUDPAND = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINGANG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINGANG = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_GECONSTATEERD = false;
    private static final Boolean UPDATED_GECONSTATEERD = true;

    private static final String DEFAULT_GEOMETRIEBOVENAANZICHT = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEBOVENAANZICHT = "BBBBBBBBBB";

    private static final String DEFAULT_GEOMETRIEMAAIVELD = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEMAAIVELD = "BBBBBBBBBB";

    private static final String DEFAULT_GEOMETRIEPUNT = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEPUNT = "BBBBBBBBBB";

    private static final String DEFAULT_HOOGSTEBOUWLAAGPAND = "AAAAAAAAAA";
    private static final String UPDATED_HOOGSTEBOUWLAAGPAND = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INONDERZOEK = false;
    private static final Boolean UPDATED_INONDERZOEK = true;

    private static final String DEFAULT_LAAGSTEBOUWLAAGPAND = "AAAAAAAAAA";
    private static final String UPDATED_LAAGSTEBOUWLAAGPAND = "BBBBBBBBBB";

    private static final String DEFAULT_OORSPRONKELIJKBOUWJAAR = "AAAAAAAAAA";
    private static final String UPDATED_OORSPRONKELIJKBOUWJAAR = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIEVEHOOGTELIGGINGPAND = "AAAAAAAAAA";
    private static final String UPDATED_RELATIEVEHOOGTELIGGINGPAND = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSVOORTGANGBOUW = "AAAAAAAAAA";
    private static final String UPDATED_STATUSVOORTGANGBOUW = "BBBBBBBBBB";

    private static final String DEFAULT_VERSIE = "AAAAAAAAAA";
    private static final String UPDATED_VERSIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PandRepository pandRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPandMockMvc;

    private Pand pand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pand createEntity(EntityManager em) {
        Pand pand = new Pand()
            .brutoinhoudpand(DEFAULT_BRUTOINHOUDPAND)
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .datumingang(DEFAULT_DATUMINGANG)
            .geconstateerd(DEFAULT_GECONSTATEERD)
            .geometriebovenaanzicht(DEFAULT_GEOMETRIEBOVENAANZICHT)
            .geometriemaaiveld(DEFAULT_GEOMETRIEMAAIVELD)
            .geometriepunt(DEFAULT_GEOMETRIEPUNT)
            .hoogstebouwlaagpand(DEFAULT_HOOGSTEBOUWLAAGPAND)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .inonderzoek(DEFAULT_INONDERZOEK)
            .laagstebouwlaagpand(DEFAULT_LAAGSTEBOUWLAAGPAND)
            .oorspronkelijkbouwjaar(DEFAULT_OORSPRONKELIJKBOUWJAAR)
            .oppervlakte(DEFAULT_OPPERVLAKTE)
            .relatievehoogteliggingpand(DEFAULT_RELATIEVEHOOGTELIGGINGPAND)
            .status(DEFAULT_STATUS)
            .statusvoortgangbouw(DEFAULT_STATUSVOORTGANGBOUW)
            .versie(DEFAULT_VERSIE);
        return pand;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pand createUpdatedEntity(EntityManager em) {
        Pand pand = new Pand()
            .brutoinhoudpand(UPDATED_BRUTOINHOUDPAND)
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometriebovenaanzicht(UPDATED_GEOMETRIEBOVENAANZICHT)
            .geometriemaaiveld(UPDATED_GEOMETRIEMAAIVELD)
            .geometriepunt(UPDATED_GEOMETRIEPUNT)
            .hoogstebouwlaagpand(UPDATED_HOOGSTEBOUWLAAGPAND)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .laagstebouwlaagpand(UPDATED_LAAGSTEBOUWLAAGPAND)
            .oorspronkelijkbouwjaar(UPDATED_OORSPRONKELIJKBOUWJAAR)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .relatievehoogteliggingpand(UPDATED_RELATIEVEHOOGTELIGGINGPAND)
            .status(UPDATED_STATUS)
            .statusvoortgangbouw(UPDATED_STATUSVOORTGANGBOUW)
            .versie(UPDATED_VERSIE);
        return pand;
    }

    @BeforeEach
    public void initTest() {
        pand = createEntity(em);
    }

    @Test
    @Transactional
    void createPand() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Pand
        var returnedPand = om.readValue(
            restPandMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pand)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Pand.class
        );

        // Validate the Pand in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPandUpdatableFieldsEquals(returnedPand, getPersistedPand(returnedPand));
    }

    @Test
    @Transactional
    void createPandWithExistingId() throws Exception {
        // Create the Pand with an existing ID
        pand.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pand)))
            .andExpect(status().isBadRequest());

        // Validate the Pand in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPands() throws Exception {
        // Initialize the database
        pandRepository.saveAndFlush(pand);

        // Get all the pandList
        restPandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pand.getId().intValue())))
            .andExpect(jsonPath("$.[*].brutoinhoudpand").value(hasItem(DEFAULT_BRUTOINHOUDPAND)))
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumingang").value(hasItem(DEFAULT_DATUMINGANG.toString())))
            .andExpect(jsonPath("$.[*].geconstateerd").value(hasItem(DEFAULT_GECONSTATEERD.booleanValue())))
            .andExpect(jsonPath("$.[*].geometriebovenaanzicht").value(hasItem(DEFAULT_GEOMETRIEBOVENAANZICHT)))
            .andExpect(jsonPath("$.[*].geometriemaaiveld").value(hasItem(DEFAULT_GEOMETRIEMAAIVELD)))
            .andExpect(jsonPath("$.[*].geometriepunt").value(hasItem(DEFAULT_GEOMETRIEPUNT)))
            .andExpect(jsonPath("$.[*].hoogstebouwlaagpand").value(hasItem(DEFAULT_HOOGSTEBOUWLAAGPAND)))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].inonderzoek").value(hasItem(DEFAULT_INONDERZOEK.booleanValue())))
            .andExpect(jsonPath("$.[*].laagstebouwlaagpand").value(hasItem(DEFAULT_LAAGSTEBOUWLAAGPAND)))
            .andExpect(jsonPath("$.[*].oorspronkelijkbouwjaar").value(hasItem(DEFAULT_OORSPRONKELIJKBOUWJAAR)))
            .andExpect(jsonPath("$.[*].oppervlakte").value(hasItem(DEFAULT_OPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].relatievehoogteliggingpand").value(hasItem(DEFAULT_RELATIEVEHOOGTELIGGINGPAND)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].statusvoortgangbouw").value(hasItem(DEFAULT_STATUSVOORTGANGBOUW)))
            .andExpect(jsonPath("$.[*].versie").value(hasItem(DEFAULT_VERSIE)));
    }

    @Test
    @Transactional
    void getPand() throws Exception {
        // Initialize the database
        pandRepository.saveAndFlush(pand);

        // Get the pand
        restPandMockMvc
            .perform(get(ENTITY_API_URL_ID, pand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pand.getId().intValue()))
            .andExpect(jsonPath("$.brutoinhoudpand").value(DEFAULT_BRUTOINHOUDPAND))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumingang").value(DEFAULT_DATUMINGANG.toString()))
            .andExpect(jsonPath("$.geconstateerd").value(DEFAULT_GECONSTATEERD.booleanValue()))
            .andExpect(jsonPath("$.geometriebovenaanzicht").value(DEFAULT_GEOMETRIEBOVENAANZICHT))
            .andExpect(jsonPath("$.geometriemaaiveld").value(DEFAULT_GEOMETRIEMAAIVELD))
            .andExpect(jsonPath("$.geometriepunt").value(DEFAULT_GEOMETRIEPUNT))
            .andExpect(jsonPath("$.hoogstebouwlaagpand").value(DEFAULT_HOOGSTEBOUWLAAGPAND))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.inonderzoek").value(DEFAULT_INONDERZOEK.booleanValue()))
            .andExpect(jsonPath("$.laagstebouwlaagpand").value(DEFAULT_LAAGSTEBOUWLAAGPAND))
            .andExpect(jsonPath("$.oorspronkelijkbouwjaar").value(DEFAULT_OORSPRONKELIJKBOUWJAAR))
            .andExpect(jsonPath("$.oppervlakte").value(DEFAULT_OPPERVLAKTE))
            .andExpect(jsonPath("$.relatievehoogteliggingpand").value(DEFAULT_RELATIEVEHOOGTELIGGINGPAND))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.statusvoortgangbouw").value(DEFAULT_STATUSVOORTGANGBOUW))
            .andExpect(jsonPath("$.versie").value(DEFAULT_VERSIE));
    }

    @Test
    @Transactional
    void getNonExistingPand() throws Exception {
        // Get the pand
        restPandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPand() throws Exception {
        // Initialize the database
        pandRepository.saveAndFlush(pand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pand
        Pand updatedPand = pandRepository.findById(pand.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPand are not directly saved in db
        em.detach(updatedPand);
        updatedPand
            .brutoinhoudpand(UPDATED_BRUTOINHOUDPAND)
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometriebovenaanzicht(UPDATED_GEOMETRIEBOVENAANZICHT)
            .geometriemaaiveld(UPDATED_GEOMETRIEMAAIVELD)
            .geometriepunt(UPDATED_GEOMETRIEPUNT)
            .hoogstebouwlaagpand(UPDATED_HOOGSTEBOUWLAAGPAND)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .laagstebouwlaagpand(UPDATED_LAAGSTEBOUWLAAGPAND)
            .oorspronkelijkbouwjaar(UPDATED_OORSPRONKELIJKBOUWJAAR)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .relatievehoogteliggingpand(UPDATED_RELATIEVEHOOGTELIGGINGPAND)
            .status(UPDATED_STATUS)
            .statusvoortgangbouw(UPDATED_STATUSVOORTGANGBOUW)
            .versie(UPDATED_VERSIE);

        restPandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPand.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPand))
            )
            .andExpect(status().isOk());

        // Validate the Pand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPandToMatchAllProperties(updatedPand);
    }

    @Test
    @Transactional
    void putNonExistingPand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pand.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPandMockMvc
            .perform(put(ENTITY_API_URL_ID, pand.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pand)))
            .andExpect(status().isBadRequest());

        // Validate the Pand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pand.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pand.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pand)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePandWithPatch() throws Exception {
        // Initialize the database
        pandRepository.saveAndFlush(pand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pand using partial update
        Pand partialUpdatedPand = new Pand();
        partialUpdatedPand.setId(pand.getId());

        partialUpdatedPand
            .brutoinhoudpand(UPDATED_BRUTOINHOUDPAND)
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .inonderzoek(UPDATED_INONDERZOEK)
            .laagstebouwlaagpand(UPDATED_LAAGSTEBOUWLAAGPAND)
            .oorspronkelijkbouwjaar(UPDATED_OORSPRONKELIJKBOUWJAAR)
            .status(UPDATED_STATUS)
            .statusvoortgangbouw(UPDATED_STATUSVOORTGANGBOUW)
            .versie(UPDATED_VERSIE);

        restPandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPand.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPand))
            )
            .andExpect(status().isOk());

        // Validate the Pand in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPandUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPand, pand), getPersistedPand(pand));
    }

    @Test
    @Transactional
    void fullUpdatePandWithPatch() throws Exception {
        // Initialize the database
        pandRepository.saveAndFlush(pand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pand using partial update
        Pand partialUpdatedPand = new Pand();
        partialUpdatedPand.setId(pand.getId());

        partialUpdatedPand
            .brutoinhoudpand(UPDATED_BRUTOINHOUDPAND)
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometriebovenaanzicht(UPDATED_GEOMETRIEBOVENAANZICHT)
            .geometriemaaiveld(UPDATED_GEOMETRIEMAAIVELD)
            .geometriepunt(UPDATED_GEOMETRIEPUNT)
            .hoogstebouwlaagpand(UPDATED_HOOGSTEBOUWLAAGPAND)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .laagstebouwlaagpand(UPDATED_LAAGSTEBOUWLAAGPAND)
            .oorspronkelijkbouwjaar(UPDATED_OORSPRONKELIJKBOUWJAAR)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .relatievehoogteliggingpand(UPDATED_RELATIEVEHOOGTELIGGINGPAND)
            .status(UPDATED_STATUS)
            .statusvoortgangbouw(UPDATED_STATUSVOORTGANGBOUW)
            .versie(UPDATED_VERSIE);

        restPandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPand.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPand))
            )
            .andExpect(status().isOk());

        // Validate the Pand in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPandUpdatableFieldsEquals(partialUpdatedPand, getPersistedPand(partialUpdatedPand));
    }

    @Test
    @Transactional
    void patchNonExistingPand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pand.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPandMockMvc
            .perform(patch(ENTITY_API_URL_ID, pand.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pand)))
            .andExpect(status().isBadRequest());

        // Validate the Pand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pand.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pand.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPandMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pand)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePand() throws Exception {
        // Initialize the database
        pandRepository.saveAndFlush(pand);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pand
        restPandMockMvc
            .perform(delete(ENTITY_API_URL_ID, pand.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pandRepository.count();
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

    protected Pand getPersistedPand(Pand pand) {
        return pandRepository.findById(pand.getId()).orElseThrow();
    }

    protected void assertPersistedPandToMatchAllProperties(Pand expectedPand) {
        assertPandAllPropertiesEquals(expectedPand, getPersistedPand(expectedPand));
    }

    protected void assertPersistedPandToMatchUpdatableProperties(Pand expectedPand) {
        assertPandAllUpdatablePropertiesEquals(expectedPand, getPersistedPand(expectedPand));
    }
}
