package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.InspectieAsserts.*;
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
import nl.ritense.demo.domain.Inspectie;
import nl.ritense.demo.repository.InspectieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InspectieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InspectieResourceIT {

    private static final String DEFAULT_AANGEMAAKTDOOR = "AAAAAAAAAA";
    private static final String UPDATED_AANGEMAAKTDOOR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMAANMAAK = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANMAAK = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMGEPLAND = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMGEPLAND = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINSPECTIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINSPECTIE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMMUTATIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMMUTATIE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEMUTEERDDOOR = "AAAAAAAAAA";
    private static final String UPDATED_GEMUTEERDDOOR = "BBBBBBBBBB";

    private static final String DEFAULT_INSPECTIETYPE = "AAAAAAAAAA";
    private static final String UPDATED_INSPECTIETYPE = "BBBBBBBBBB";

    private static final String DEFAULT_KENMERK = "AAAAAAAAAA";
    private static final String UPDATED_KENMERK = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_OPMERKINGEN = "AAAAAAAAAA";
    private static final String UPDATED_OPMERKINGEN = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inspecties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InspectieRepository inspectieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInspectieMockMvc;

    private Inspectie inspectie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inspectie createEntity(EntityManager em) {
        Inspectie inspectie = new Inspectie()
            .aangemaaktdoor(DEFAULT_AANGEMAAKTDOOR)
            .datumaanmaak(DEFAULT_DATUMAANMAAK)
            .datumgepland(DEFAULT_DATUMGEPLAND)
            .datuminspectie(DEFAULT_DATUMINSPECTIE)
            .datummutatie(DEFAULT_DATUMMUTATIE)
            .gemuteerddoor(DEFAULT_GEMUTEERDDOOR)
            .inspectietype(DEFAULT_INSPECTIETYPE)
            .kenmerk(DEFAULT_KENMERK)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .opmerkingen(DEFAULT_OPMERKINGEN)
            .status(DEFAULT_STATUS);
        return inspectie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inspectie createUpdatedEntity(EntityManager em) {
        Inspectie inspectie = new Inspectie()
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .datumaanmaak(UPDATED_DATUMAANMAAK)
            .datumgepland(UPDATED_DATUMGEPLAND)
            .datuminspectie(UPDATED_DATUMINSPECTIE)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .inspectietype(UPDATED_INSPECTIETYPE)
            .kenmerk(UPDATED_KENMERK)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .status(UPDATED_STATUS);
        return inspectie;
    }

    @BeforeEach
    public void initTest() {
        inspectie = createEntity(em);
    }

    @Test
    @Transactional
    void createInspectie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Inspectie
        var returnedInspectie = om.readValue(
            restInspectieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Inspectie.class
        );

        // Validate the Inspectie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInspectieUpdatableFieldsEquals(returnedInspectie, getPersistedInspectie(returnedInspectie));
    }

    @Test
    @Transactional
    void createInspectieWithExistingId() throws Exception {
        // Create the Inspectie with an existing ID
        inspectie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspectieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectie)))
            .andExpect(status().isBadRequest());

        // Validate the Inspectie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInspecties() throws Exception {
        // Initialize the database
        inspectieRepository.saveAndFlush(inspectie);

        // Get all the inspectieList
        restInspectieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspectie.getId().intValue())))
            .andExpect(jsonPath("$.[*].aangemaaktdoor").value(hasItem(DEFAULT_AANGEMAAKTDOOR)))
            .andExpect(jsonPath("$.[*].datumaanmaak").value(hasItem(DEFAULT_DATUMAANMAAK.toString())))
            .andExpect(jsonPath("$.[*].datumgepland").value(hasItem(DEFAULT_DATUMGEPLAND.toString())))
            .andExpect(jsonPath("$.[*].datuminspectie").value(hasItem(DEFAULT_DATUMINSPECTIE.toString())))
            .andExpect(jsonPath("$.[*].datummutatie").value(hasItem(DEFAULT_DATUMMUTATIE.toString())))
            .andExpect(jsonPath("$.[*].gemuteerddoor").value(hasItem(DEFAULT_GEMUTEERDDOOR)))
            .andExpect(jsonPath("$.[*].inspectietype").value(hasItem(DEFAULT_INSPECTIETYPE)))
            .andExpect(jsonPath("$.[*].kenmerk").value(hasItem(DEFAULT_KENMERK)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].opmerkingen").value(hasItem(DEFAULT_OPMERKINGEN)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getInspectie() throws Exception {
        // Initialize the database
        inspectieRepository.saveAndFlush(inspectie);

        // Get the inspectie
        restInspectieMockMvc
            .perform(get(ENTITY_API_URL_ID, inspectie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inspectie.getId().intValue()))
            .andExpect(jsonPath("$.aangemaaktdoor").value(DEFAULT_AANGEMAAKTDOOR))
            .andExpect(jsonPath("$.datumaanmaak").value(DEFAULT_DATUMAANMAAK.toString()))
            .andExpect(jsonPath("$.datumgepland").value(DEFAULT_DATUMGEPLAND.toString()))
            .andExpect(jsonPath("$.datuminspectie").value(DEFAULT_DATUMINSPECTIE.toString()))
            .andExpect(jsonPath("$.datummutatie").value(DEFAULT_DATUMMUTATIE.toString()))
            .andExpect(jsonPath("$.gemuteerddoor").value(DEFAULT_GEMUTEERDDOOR))
            .andExpect(jsonPath("$.inspectietype").value(DEFAULT_INSPECTIETYPE))
            .andExpect(jsonPath("$.kenmerk").value(DEFAULT_KENMERK))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.opmerkingen").value(DEFAULT_OPMERKINGEN))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingInspectie() throws Exception {
        // Get the inspectie
        restInspectieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInspectie() throws Exception {
        // Initialize the database
        inspectieRepository.saveAndFlush(inspectie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectie
        Inspectie updatedInspectie = inspectieRepository.findById(inspectie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInspectie are not directly saved in db
        em.detach(updatedInspectie);
        updatedInspectie
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .datumaanmaak(UPDATED_DATUMAANMAAK)
            .datumgepland(UPDATED_DATUMGEPLAND)
            .datuminspectie(UPDATED_DATUMINSPECTIE)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .inspectietype(UPDATED_INSPECTIETYPE)
            .kenmerk(UPDATED_KENMERK)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .status(UPDATED_STATUS);

        restInspectieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInspectie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInspectie))
            )
            .andExpect(status().isOk());

        // Validate the Inspectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInspectieToMatchAllProperties(updatedInspectie);
    }

    @Test
    @Transactional
    void putNonExistingInspectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inspectie.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inspectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInspectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inspectie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inspectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInspectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inspectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInspectieWithPatch() throws Exception {
        // Initialize the database
        inspectieRepository.saveAndFlush(inspectie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectie using partial update
        Inspectie partialUpdatedInspectie = new Inspectie();
        partialUpdatedInspectie.setId(inspectie.getId());

        partialUpdatedInspectie
            .datumgepland(UPDATED_DATUMGEPLAND)
            .datuminspectie(UPDATED_DATUMINSPECTIE)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR);

        restInspectieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInspectie))
            )
            .andExpect(status().isOk());

        // Validate the Inspectie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInspectieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInspectie, inspectie),
            getPersistedInspectie(inspectie)
        );
    }

    @Test
    @Transactional
    void fullUpdateInspectieWithPatch() throws Exception {
        // Initialize the database
        inspectieRepository.saveAndFlush(inspectie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectie using partial update
        Inspectie partialUpdatedInspectie = new Inspectie();
        partialUpdatedInspectie.setId(inspectie.getId());

        partialUpdatedInspectie
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .datumaanmaak(UPDATED_DATUMAANMAAK)
            .datumgepland(UPDATED_DATUMGEPLAND)
            .datuminspectie(UPDATED_DATUMINSPECTIE)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .inspectietype(UPDATED_INSPECTIETYPE)
            .kenmerk(UPDATED_KENMERK)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .status(UPDATED_STATUS);

        restInspectieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInspectie))
            )
            .andExpect(status().isOk());

        // Validate the Inspectie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInspectieUpdatableFieldsEquals(partialUpdatedInspectie, getPersistedInspectie(partialUpdatedInspectie));
    }

    @Test
    @Transactional
    void patchNonExistingInspectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inspectie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inspectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInspectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inspectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInspectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inspectie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inspectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInspectie() throws Exception {
        // Initialize the database
        inspectieRepository.saveAndFlush(inspectie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inspectie
        restInspectieMockMvc
            .perform(delete(ENTITY_API_URL_ID, inspectie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inspectieRepository.count();
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

    protected Inspectie getPersistedInspectie(Inspectie inspectie) {
        return inspectieRepository.findById(inspectie.getId()).orElseThrow();
    }

    protected void assertPersistedInspectieToMatchAllProperties(Inspectie expectedInspectie) {
        assertInspectieAllPropertiesEquals(expectedInspectie, getPersistedInspectie(expectedInspectie));
    }

    protected void assertPersistedInspectieToMatchUpdatableProperties(Inspectie expectedInspectie) {
        assertInspectieAllUpdatablePropertiesEquals(expectedInspectie, getPersistedInspectie(expectedInspectie));
    }
}
