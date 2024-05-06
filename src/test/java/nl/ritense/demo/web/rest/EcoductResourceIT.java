package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EcoductAsserts.*;
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
import nl.ritense.demo.domain.Ecoduct;
import nl.ritense.demo.repository.EcoductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EcoductResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EcoductResourceIT {

    private static final String DEFAULT_AANTALOVERSPANNINGEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALOVERSPANNINGEN = "BBBBBBBBBB";

    private static final String DEFAULT_DRAAGVERMOGEN = "AAAAAAAAAA";
    private static final String UPDATED_DRAAGVERMOGEN = "BBBBBBBBBB";

    private static final String DEFAULT_MAXIMAALTOELAATBAARVOERTUIGGEWICHT = "AAAAAAAAAA";
    private static final String UPDATED_MAXIMAALTOELAATBAARVOERTUIGGEWICHT = "BBBBBBBBBB";

    private static final String DEFAULT_MAXIMALEASBELASTING = "AAAAAAAAAA";
    private static final String UPDATED_MAXIMALEASBELASTING = "BBBBBBBBBB";

    private static final String DEFAULT_MAXIMALEOVERSPANNING = "AAAAAAAAAA";
    private static final String UPDATED_MAXIMALEOVERSPANNING = "BBBBBBBBBB";

    private static final String DEFAULT_OVERBRUGGINGSOBJECTDOORRIJOPENING = "AAAAAAAAAA";
    private static final String UPDATED_OVERBRUGGINGSOBJECTDOORRIJOPENING = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ZWAARSTEVOERTUIG = "AAAAAAAAAA";
    private static final String UPDATED_ZWAARSTEVOERTUIG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ecoducts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EcoductRepository ecoductRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEcoductMockMvc;

    private Ecoduct ecoduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ecoduct createEntity(EntityManager em) {
        Ecoduct ecoduct = new Ecoduct()
            .aantaloverspanningen(DEFAULT_AANTALOVERSPANNINGEN)
            .draagvermogen(DEFAULT_DRAAGVERMOGEN)
            .maximaaltoelaatbaarvoertuiggewicht(DEFAULT_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)
            .maximaleasbelasting(DEFAULT_MAXIMALEASBELASTING)
            .maximaleoverspanning(DEFAULT_MAXIMALEOVERSPANNING)
            .overbruggingsobjectdoorrijopening(DEFAULT_OVERBRUGGINGSOBJECTDOORRIJOPENING)
            .type(DEFAULT_TYPE)
            .zwaarstevoertuig(DEFAULT_ZWAARSTEVOERTUIG);
        return ecoduct;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ecoduct createUpdatedEntity(EntityManager em) {
        Ecoduct ecoduct = new Ecoduct()
            .aantaloverspanningen(UPDATED_AANTALOVERSPANNINGEN)
            .draagvermogen(UPDATED_DRAAGVERMOGEN)
            .maximaaltoelaatbaarvoertuiggewicht(UPDATED_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)
            .maximaleasbelasting(UPDATED_MAXIMALEASBELASTING)
            .maximaleoverspanning(UPDATED_MAXIMALEOVERSPANNING)
            .overbruggingsobjectdoorrijopening(UPDATED_OVERBRUGGINGSOBJECTDOORRIJOPENING)
            .type(UPDATED_TYPE)
            .zwaarstevoertuig(UPDATED_ZWAARSTEVOERTUIG);
        return ecoduct;
    }

    @BeforeEach
    public void initTest() {
        ecoduct = createEntity(em);
    }

    @Test
    @Transactional
    void createEcoduct() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ecoduct
        var returnedEcoduct = om.readValue(
            restEcoductMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ecoduct)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Ecoduct.class
        );

        // Validate the Ecoduct in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEcoductUpdatableFieldsEquals(returnedEcoduct, getPersistedEcoduct(returnedEcoduct));
    }

    @Test
    @Transactional
    void createEcoductWithExistingId() throws Exception {
        // Create the Ecoduct with an existing ID
        ecoduct.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEcoductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ecoduct)))
            .andExpect(status().isBadRequest());

        // Validate the Ecoduct in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEcoducts() throws Exception {
        // Initialize the database
        ecoductRepository.saveAndFlush(ecoduct);

        // Get all the ecoductList
        restEcoductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ecoduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantaloverspanningen").value(hasItem(DEFAULT_AANTALOVERSPANNINGEN)))
            .andExpect(jsonPath("$.[*].draagvermogen").value(hasItem(DEFAULT_DRAAGVERMOGEN)))
            .andExpect(jsonPath("$.[*].maximaaltoelaatbaarvoertuiggewicht").value(hasItem(DEFAULT_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)))
            .andExpect(jsonPath("$.[*].maximaleasbelasting").value(hasItem(DEFAULT_MAXIMALEASBELASTING)))
            .andExpect(jsonPath("$.[*].maximaleoverspanning").value(hasItem(DEFAULT_MAXIMALEOVERSPANNING)))
            .andExpect(jsonPath("$.[*].overbruggingsobjectdoorrijopening").value(hasItem(DEFAULT_OVERBRUGGINGSOBJECTDOORRIJOPENING)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].zwaarstevoertuig").value(hasItem(DEFAULT_ZWAARSTEVOERTUIG)));
    }

    @Test
    @Transactional
    void getEcoduct() throws Exception {
        // Initialize the database
        ecoductRepository.saveAndFlush(ecoduct);

        // Get the ecoduct
        restEcoductMockMvc
            .perform(get(ENTITY_API_URL_ID, ecoduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ecoduct.getId().intValue()))
            .andExpect(jsonPath("$.aantaloverspanningen").value(DEFAULT_AANTALOVERSPANNINGEN))
            .andExpect(jsonPath("$.draagvermogen").value(DEFAULT_DRAAGVERMOGEN))
            .andExpect(jsonPath("$.maximaaltoelaatbaarvoertuiggewicht").value(DEFAULT_MAXIMAALTOELAATBAARVOERTUIGGEWICHT))
            .andExpect(jsonPath("$.maximaleasbelasting").value(DEFAULT_MAXIMALEASBELASTING))
            .andExpect(jsonPath("$.maximaleoverspanning").value(DEFAULT_MAXIMALEOVERSPANNING))
            .andExpect(jsonPath("$.overbruggingsobjectdoorrijopening").value(DEFAULT_OVERBRUGGINGSOBJECTDOORRIJOPENING))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.zwaarstevoertuig").value(DEFAULT_ZWAARSTEVOERTUIG));
    }

    @Test
    @Transactional
    void getNonExistingEcoduct() throws Exception {
        // Get the ecoduct
        restEcoductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEcoduct() throws Exception {
        // Initialize the database
        ecoductRepository.saveAndFlush(ecoduct);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ecoduct
        Ecoduct updatedEcoduct = ecoductRepository.findById(ecoduct.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEcoduct are not directly saved in db
        em.detach(updatedEcoduct);
        updatedEcoduct
            .aantaloverspanningen(UPDATED_AANTALOVERSPANNINGEN)
            .draagvermogen(UPDATED_DRAAGVERMOGEN)
            .maximaaltoelaatbaarvoertuiggewicht(UPDATED_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)
            .maximaleasbelasting(UPDATED_MAXIMALEASBELASTING)
            .maximaleoverspanning(UPDATED_MAXIMALEOVERSPANNING)
            .overbruggingsobjectdoorrijopening(UPDATED_OVERBRUGGINGSOBJECTDOORRIJOPENING)
            .type(UPDATED_TYPE)
            .zwaarstevoertuig(UPDATED_ZWAARSTEVOERTUIG);

        restEcoductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEcoduct.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEcoduct))
            )
            .andExpect(status().isOk());

        // Validate the Ecoduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEcoductToMatchAllProperties(updatedEcoduct);
    }

    @Test
    @Transactional
    void putNonExistingEcoduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecoduct.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcoductMockMvc
            .perform(put(ENTITY_API_URL_ID, ecoduct.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ecoduct)))
            .andExpect(status().isBadRequest());

        // Validate the Ecoduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEcoduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecoduct.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcoductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ecoduct))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecoduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEcoduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecoduct.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcoductMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ecoduct)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ecoduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEcoductWithPatch() throws Exception {
        // Initialize the database
        ecoductRepository.saveAndFlush(ecoduct);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ecoduct using partial update
        Ecoduct partialUpdatedEcoduct = new Ecoduct();
        partialUpdatedEcoduct.setId(ecoduct.getId());

        partialUpdatedEcoduct
            .aantaloverspanningen(UPDATED_AANTALOVERSPANNINGEN)
            .maximaleasbelasting(UPDATED_MAXIMALEASBELASTING)
            .maximaleoverspanning(UPDATED_MAXIMALEOVERSPANNING)
            .overbruggingsobjectdoorrijopening(UPDATED_OVERBRUGGINGSOBJECTDOORRIJOPENING);

        restEcoductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcoduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEcoduct))
            )
            .andExpect(status().isOk());

        // Validate the Ecoduct in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEcoductUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEcoduct, ecoduct), getPersistedEcoduct(ecoduct));
    }

    @Test
    @Transactional
    void fullUpdateEcoductWithPatch() throws Exception {
        // Initialize the database
        ecoductRepository.saveAndFlush(ecoduct);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ecoduct using partial update
        Ecoduct partialUpdatedEcoduct = new Ecoduct();
        partialUpdatedEcoduct.setId(ecoduct.getId());

        partialUpdatedEcoduct
            .aantaloverspanningen(UPDATED_AANTALOVERSPANNINGEN)
            .draagvermogen(UPDATED_DRAAGVERMOGEN)
            .maximaaltoelaatbaarvoertuiggewicht(UPDATED_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)
            .maximaleasbelasting(UPDATED_MAXIMALEASBELASTING)
            .maximaleoverspanning(UPDATED_MAXIMALEOVERSPANNING)
            .overbruggingsobjectdoorrijopening(UPDATED_OVERBRUGGINGSOBJECTDOORRIJOPENING)
            .type(UPDATED_TYPE)
            .zwaarstevoertuig(UPDATED_ZWAARSTEVOERTUIG);

        restEcoductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcoduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEcoduct))
            )
            .andExpect(status().isOk());

        // Validate the Ecoduct in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEcoductUpdatableFieldsEquals(partialUpdatedEcoduct, getPersistedEcoduct(partialUpdatedEcoduct));
    }

    @Test
    @Transactional
    void patchNonExistingEcoduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecoduct.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcoductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ecoduct.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ecoduct))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecoduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEcoduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecoduct.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcoductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ecoduct))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecoduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEcoduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecoduct.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcoductMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ecoduct)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ecoduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEcoduct() throws Exception {
        // Initialize the database
        ecoductRepository.saveAndFlush(ecoduct);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ecoduct
        restEcoductMockMvc
            .perform(delete(ENTITY_API_URL_ID, ecoduct.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ecoductRepository.count();
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

    protected Ecoduct getPersistedEcoduct(Ecoduct ecoduct) {
        return ecoductRepository.findById(ecoduct.getId()).orElseThrow();
    }

    protected void assertPersistedEcoductToMatchAllProperties(Ecoduct expectedEcoduct) {
        assertEcoductAllPropertiesEquals(expectedEcoduct, getPersistedEcoduct(expectedEcoduct));
    }

    protected void assertPersistedEcoductToMatchUpdatableProperties(Ecoduct expectedEcoduct) {
        assertEcoductAllUpdatablePropertiesEquals(expectedEcoduct, getPersistedEcoduct(expectedEcoduct));
    }
}
