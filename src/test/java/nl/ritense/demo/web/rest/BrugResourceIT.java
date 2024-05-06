package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BrugAsserts.*;
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
import nl.ritense.demo.domain.Brug;
import nl.ritense.demo.repository.BrugRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BrugResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BrugResourceIT {

    private static final String DEFAULT_AANTALOVERSPANNINGEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALOVERSPANNINGEN = "BBBBBBBBBB";

    private static final String DEFAULT_BEDIENAAR = "AAAAAAAAAA";
    private static final String UPDATED_BEDIENAAR = "BBBBBBBBBB";

    private static final String DEFAULT_BEDIENINGSTIJDEN = "AAAAAAAAAA";
    private static final String UPDATED_BEDIENINGSTIJDEN = "BBBBBBBBBB";

    private static final String DEFAULT_BELASTINGKLASSENIEUW = "AAAAAAAAAA";
    private static final String UPDATED_BELASTINGKLASSENIEUW = "BBBBBBBBBB";

    private static final String DEFAULT_BELASTINGKLASSEOUD = "AAAAAAAAAA";
    private static final String UPDATED_BELASTINGKLASSEOUD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BEWEEGBAAR = false;
    private static final Boolean UPDATED_BEWEEGBAAR = true;

    private static final String DEFAULT_DOORRIJBREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_DOORRIJBREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_DRAAGVERMOGEN = "AAAAAAAAAA";
    private static final String UPDATED_DRAAGVERMOGEN = "BBBBBBBBBB";

    private static final String DEFAULT_HOOFDROUTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOFDROUTE = "BBBBBBBBBB";

    private static final String DEFAULT_HOOFDVAARROUTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOFDVAARROUTE = "BBBBBBBBBB";

    private static final String DEFAULT_MAXIMAALTOELAATBAARVOERTUIGGEWICHT = "AAAAAAAAAA";
    private static final String UPDATED_MAXIMAALTOELAATBAARVOERTUIGGEWICHT = "BBBBBBBBBB";

    private static final String DEFAULT_MAXIMALEASBELASTING = "AAAAAAAAAA";
    private static final String UPDATED_MAXIMALEASBELASTING = "BBBBBBBBBB";

    private static final String DEFAULT_MAXIMALEOVERSPANNING = "AAAAAAAAAA";
    private static final String UPDATED_MAXIMALEOVERSPANNING = "BBBBBBBBBB";

    private static final String DEFAULT_STATISCHMOMENT = "AAAAAAAAAA";
    private static final String UPDATED_STATISCHMOMENT = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS = "BBBBBBBBBB";

    private static final String DEFAULT_ZWAARSTEVOERTUIG = "AAAAAAAAAA";
    private static final String UPDATED_ZWAARSTEVOERTUIG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/brugs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BrugRepository brugRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBrugMockMvc;

    private Brug brug;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Brug createEntity(EntityManager em) {
        Brug brug = new Brug()
            .aantaloverspanningen(DEFAULT_AANTALOVERSPANNINGEN)
            .bedienaar(DEFAULT_BEDIENAAR)
            .bedieningstijden(DEFAULT_BEDIENINGSTIJDEN)
            .belastingklassenieuw(DEFAULT_BELASTINGKLASSENIEUW)
            .belastingklasseoud(DEFAULT_BELASTINGKLASSEOUD)
            .beweegbaar(DEFAULT_BEWEEGBAAR)
            .doorrijbreedte(DEFAULT_DOORRIJBREEDTE)
            .draagvermogen(DEFAULT_DRAAGVERMOGEN)
            .hoofdroute(DEFAULT_HOOFDROUTE)
            .hoofdvaarroute(DEFAULT_HOOFDVAARROUTE)
            .maximaaltoelaatbaarvoertuiggewicht(DEFAULT_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)
            .maximaleasbelasting(DEFAULT_MAXIMALEASBELASTING)
            .maximaleoverspanning(DEFAULT_MAXIMALEOVERSPANNING)
            .statischmoment(DEFAULT_STATISCHMOMENT)
            .type(DEFAULT_TYPE)
            .typeplus(DEFAULT_TYPEPLUS)
            .zwaarstevoertuig(DEFAULT_ZWAARSTEVOERTUIG);
        return brug;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Brug createUpdatedEntity(EntityManager em) {
        Brug brug = new Brug()
            .aantaloverspanningen(UPDATED_AANTALOVERSPANNINGEN)
            .bedienaar(UPDATED_BEDIENAAR)
            .bedieningstijden(UPDATED_BEDIENINGSTIJDEN)
            .belastingklassenieuw(UPDATED_BELASTINGKLASSENIEUW)
            .belastingklasseoud(UPDATED_BELASTINGKLASSEOUD)
            .beweegbaar(UPDATED_BEWEEGBAAR)
            .doorrijbreedte(UPDATED_DOORRIJBREEDTE)
            .draagvermogen(UPDATED_DRAAGVERMOGEN)
            .hoofdroute(UPDATED_HOOFDROUTE)
            .hoofdvaarroute(UPDATED_HOOFDVAARROUTE)
            .maximaaltoelaatbaarvoertuiggewicht(UPDATED_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)
            .maximaleasbelasting(UPDATED_MAXIMALEASBELASTING)
            .maximaleoverspanning(UPDATED_MAXIMALEOVERSPANNING)
            .statischmoment(UPDATED_STATISCHMOMENT)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS)
            .zwaarstevoertuig(UPDATED_ZWAARSTEVOERTUIG);
        return brug;
    }

    @BeforeEach
    public void initTest() {
        brug = createEntity(em);
    }

    @Test
    @Transactional
    void createBrug() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Brug
        var returnedBrug = om.readValue(
            restBrugMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brug)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Brug.class
        );

        // Validate the Brug in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBrugUpdatableFieldsEquals(returnedBrug, getPersistedBrug(returnedBrug));
    }

    @Test
    @Transactional
    void createBrugWithExistingId() throws Exception {
        // Create the Brug with an existing ID
        brug.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrugMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brug)))
            .andExpect(status().isBadRequest());

        // Validate the Brug in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBrugs() throws Exception {
        // Initialize the database
        brugRepository.saveAndFlush(brug);

        // Get all the brugList
        restBrugMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brug.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantaloverspanningen").value(hasItem(DEFAULT_AANTALOVERSPANNINGEN)))
            .andExpect(jsonPath("$.[*].bedienaar").value(hasItem(DEFAULT_BEDIENAAR)))
            .andExpect(jsonPath("$.[*].bedieningstijden").value(hasItem(DEFAULT_BEDIENINGSTIJDEN)))
            .andExpect(jsonPath("$.[*].belastingklassenieuw").value(hasItem(DEFAULT_BELASTINGKLASSENIEUW)))
            .andExpect(jsonPath("$.[*].belastingklasseoud").value(hasItem(DEFAULT_BELASTINGKLASSEOUD)))
            .andExpect(jsonPath("$.[*].beweegbaar").value(hasItem(DEFAULT_BEWEEGBAAR.booleanValue())))
            .andExpect(jsonPath("$.[*].doorrijbreedte").value(hasItem(DEFAULT_DOORRIJBREEDTE)))
            .andExpect(jsonPath("$.[*].draagvermogen").value(hasItem(DEFAULT_DRAAGVERMOGEN)))
            .andExpect(jsonPath("$.[*].hoofdroute").value(hasItem(DEFAULT_HOOFDROUTE)))
            .andExpect(jsonPath("$.[*].hoofdvaarroute").value(hasItem(DEFAULT_HOOFDVAARROUTE)))
            .andExpect(jsonPath("$.[*].maximaaltoelaatbaarvoertuiggewicht").value(hasItem(DEFAULT_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)))
            .andExpect(jsonPath("$.[*].maximaleasbelasting").value(hasItem(DEFAULT_MAXIMALEASBELASTING)))
            .andExpect(jsonPath("$.[*].maximaleoverspanning").value(hasItem(DEFAULT_MAXIMALEOVERSPANNING)))
            .andExpect(jsonPath("$.[*].statischmoment").value(hasItem(DEFAULT_STATISCHMOMENT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].typeplus").value(hasItem(DEFAULT_TYPEPLUS)))
            .andExpect(jsonPath("$.[*].zwaarstevoertuig").value(hasItem(DEFAULT_ZWAARSTEVOERTUIG)));
    }

    @Test
    @Transactional
    void getBrug() throws Exception {
        // Initialize the database
        brugRepository.saveAndFlush(brug);

        // Get the brug
        restBrugMockMvc
            .perform(get(ENTITY_API_URL_ID, brug.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(brug.getId().intValue()))
            .andExpect(jsonPath("$.aantaloverspanningen").value(DEFAULT_AANTALOVERSPANNINGEN))
            .andExpect(jsonPath("$.bedienaar").value(DEFAULT_BEDIENAAR))
            .andExpect(jsonPath("$.bedieningstijden").value(DEFAULT_BEDIENINGSTIJDEN))
            .andExpect(jsonPath("$.belastingklassenieuw").value(DEFAULT_BELASTINGKLASSENIEUW))
            .andExpect(jsonPath("$.belastingklasseoud").value(DEFAULT_BELASTINGKLASSEOUD))
            .andExpect(jsonPath("$.beweegbaar").value(DEFAULT_BEWEEGBAAR.booleanValue()))
            .andExpect(jsonPath("$.doorrijbreedte").value(DEFAULT_DOORRIJBREEDTE))
            .andExpect(jsonPath("$.draagvermogen").value(DEFAULT_DRAAGVERMOGEN))
            .andExpect(jsonPath("$.hoofdroute").value(DEFAULT_HOOFDROUTE))
            .andExpect(jsonPath("$.hoofdvaarroute").value(DEFAULT_HOOFDVAARROUTE))
            .andExpect(jsonPath("$.maximaaltoelaatbaarvoertuiggewicht").value(DEFAULT_MAXIMAALTOELAATBAARVOERTUIGGEWICHT))
            .andExpect(jsonPath("$.maximaleasbelasting").value(DEFAULT_MAXIMALEASBELASTING))
            .andExpect(jsonPath("$.maximaleoverspanning").value(DEFAULT_MAXIMALEOVERSPANNING))
            .andExpect(jsonPath("$.statischmoment").value(DEFAULT_STATISCHMOMENT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.typeplus").value(DEFAULT_TYPEPLUS))
            .andExpect(jsonPath("$.zwaarstevoertuig").value(DEFAULT_ZWAARSTEVOERTUIG));
    }

    @Test
    @Transactional
    void getNonExistingBrug() throws Exception {
        // Get the brug
        restBrugMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBrug() throws Exception {
        // Initialize the database
        brugRepository.saveAndFlush(brug);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the brug
        Brug updatedBrug = brugRepository.findById(brug.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBrug are not directly saved in db
        em.detach(updatedBrug);
        updatedBrug
            .aantaloverspanningen(UPDATED_AANTALOVERSPANNINGEN)
            .bedienaar(UPDATED_BEDIENAAR)
            .bedieningstijden(UPDATED_BEDIENINGSTIJDEN)
            .belastingklassenieuw(UPDATED_BELASTINGKLASSENIEUW)
            .belastingklasseoud(UPDATED_BELASTINGKLASSEOUD)
            .beweegbaar(UPDATED_BEWEEGBAAR)
            .doorrijbreedte(UPDATED_DOORRIJBREEDTE)
            .draagvermogen(UPDATED_DRAAGVERMOGEN)
            .hoofdroute(UPDATED_HOOFDROUTE)
            .hoofdvaarroute(UPDATED_HOOFDVAARROUTE)
            .maximaaltoelaatbaarvoertuiggewicht(UPDATED_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)
            .maximaleasbelasting(UPDATED_MAXIMALEASBELASTING)
            .maximaleoverspanning(UPDATED_MAXIMALEOVERSPANNING)
            .statischmoment(UPDATED_STATISCHMOMENT)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS)
            .zwaarstevoertuig(UPDATED_ZWAARSTEVOERTUIG);

        restBrugMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBrug.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBrug))
            )
            .andExpect(status().isOk());

        // Validate the Brug in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBrugToMatchAllProperties(updatedBrug);
    }

    @Test
    @Transactional
    void putNonExistingBrug() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brug.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBrugMockMvc
            .perform(put(ENTITY_API_URL_ID, brug.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brug)))
            .andExpect(status().isBadRequest());

        // Validate the Brug in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBrug() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brug.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrugMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(brug))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brug in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBrug() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brug.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrugMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brug)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Brug in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBrugWithPatch() throws Exception {
        // Initialize the database
        brugRepository.saveAndFlush(brug);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the brug using partial update
        Brug partialUpdatedBrug = new Brug();
        partialUpdatedBrug.setId(brug.getId());

        partialUpdatedBrug
            .beweegbaar(UPDATED_BEWEEGBAAR)
            .hoofdroute(UPDATED_HOOFDROUTE)
            .hoofdvaarroute(UPDATED_HOOFDVAARROUTE)
            .maximaleasbelasting(UPDATED_MAXIMALEASBELASTING)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS)
            .zwaarstevoertuig(UPDATED_ZWAARSTEVOERTUIG);

        restBrugMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBrug.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBrug))
            )
            .andExpect(status().isOk());

        // Validate the Brug in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBrugUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBrug, brug), getPersistedBrug(brug));
    }

    @Test
    @Transactional
    void fullUpdateBrugWithPatch() throws Exception {
        // Initialize the database
        brugRepository.saveAndFlush(brug);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the brug using partial update
        Brug partialUpdatedBrug = new Brug();
        partialUpdatedBrug.setId(brug.getId());

        partialUpdatedBrug
            .aantaloverspanningen(UPDATED_AANTALOVERSPANNINGEN)
            .bedienaar(UPDATED_BEDIENAAR)
            .bedieningstijden(UPDATED_BEDIENINGSTIJDEN)
            .belastingklassenieuw(UPDATED_BELASTINGKLASSENIEUW)
            .belastingklasseoud(UPDATED_BELASTINGKLASSEOUD)
            .beweegbaar(UPDATED_BEWEEGBAAR)
            .doorrijbreedte(UPDATED_DOORRIJBREEDTE)
            .draagvermogen(UPDATED_DRAAGVERMOGEN)
            .hoofdroute(UPDATED_HOOFDROUTE)
            .hoofdvaarroute(UPDATED_HOOFDVAARROUTE)
            .maximaaltoelaatbaarvoertuiggewicht(UPDATED_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)
            .maximaleasbelasting(UPDATED_MAXIMALEASBELASTING)
            .maximaleoverspanning(UPDATED_MAXIMALEOVERSPANNING)
            .statischmoment(UPDATED_STATISCHMOMENT)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS)
            .zwaarstevoertuig(UPDATED_ZWAARSTEVOERTUIG);

        restBrugMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBrug.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBrug))
            )
            .andExpect(status().isOk());

        // Validate the Brug in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBrugUpdatableFieldsEquals(partialUpdatedBrug, getPersistedBrug(partialUpdatedBrug));
    }

    @Test
    @Transactional
    void patchNonExistingBrug() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brug.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBrugMockMvc
            .perform(patch(ENTITY_API_URL_ID, brug.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(brug)))
            .andExpect(status().isBadRequest());

        // Validate the Brug in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBrug() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brug.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrugMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(brug))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brug in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBrug() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brug.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrugMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(brug)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Brug in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBrug() throws Exception {
        // Initialize the database
        brugRepository.saveAndFlush(brug);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the brug
        restBrugMockMvc
            .perform(delete(ENTITY_API_URL_ID, brug.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return brugRepository.count();
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

    protected Brug getPersistedBrug(Brug brug) {
        return brugRepository.findById(brug.getId()).orElseThrow();
    }

    protected void assertPersistedBrugToMatchAllProperties(Brug expectedBrug) {
        assertBrugAllPropertiesEquals(expectedBrug, getPersistedBrug(expectedBrug));
    }

    protected void assertPersistedBrugToMatchUpdatableProperties(Brug expectedBrug) {
        assertBrugAllUpdatablePropertiesEquals(expectedBrug, getPersistedBrug(expectedBrug));
    }
}
