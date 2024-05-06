package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ViaductAsserts.*;
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
import nl.ritense.demo.domain.Viaduct;
import nl.ritense.demo.repository.ViaductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ViaductResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ViaductResourceIT {

    private static final String DEFAULT_AANTALOVERSPANNINGEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALOVERSPANNINGEN = "BBBBBBBBBB";

    private static final String DEFAULT_BELASTINGKLASSENIEUW = "AAAAAAAAAA";
    private static final String UPDATED_BELASTINGKLASSENIEUW = "BBBBBBBBBB";

    private static final String DEFAULT_BELASTINGKLASSEOUD = "AAAAAAAAAA";
    private static final String UPDATED_BELASTINGKLASSEOUD = "BBBBBBBBBB";

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

    private static final String DEFAULT_WATEROBJECT = "AAAAAAAAAA";
    private static final String UPDATED_WATEROBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_ZWAARSTEVOERTUIG = "AAAAAAAAAA";
    private static final String UPDATED_ZWAARSTEVOERTUIG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/viaducts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ViaductRepository viaductRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restViaductMockMvc;

    private Viaduct viaduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Viaduct createEntity(EntityManager em) {
        Viaduct viaduct = new Viaduct()
            .aantaloverspanningen(DEFAULT_AANTALOVERSPANNINGEN)
            .belastingklassenieuw(DEFAULT_BELASTINGKLASSENIEUW)
            .belastingklasseoud(DEFAULT_BELASTINGKLASSEOUD)
            .draagvermogen(DEFAULT_DRAAGVERMOGEN)
            .maximaaltoelaatbaarvoertuiggewicht(DEFAULT_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)
            .maximaleasbelasting(DEFAULT_MAXIMALEASBELASTING)
            .maximaleoverspanning(DEFAULT_MAXIMALEOVERSPANNING)
            .overbruggingsobjectdoorrijopening(DEFAULT_OVERBRUGGINGSOBJECTDOORRIJOPENING)
            .type(DEFAULT_TYPE)
            .waterobject(DEFAULT_WATEROBJECT)
            .zwaarstevoertuig(DEFAULT_ZWAARSTEVOERTUIG);
        return viaduct;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Viaduct createUpdatedEntity(EntityManager em) {
        Viaduct viaduct = new Viaduct()
            .aantaloverspanningen(UPDATED_AANTALOVERSPANNINGEN)
            .belastingklassenieuw(UPDATED_BELASTINGKLASSENIEUW)
            .belastingklasseoud(UPDATED_BELASTINGKLASSEOUD)
            .draagvermogen(UPDATED_DRAAGVERMOGEN)
            .maximaaltoelaatbaarvoertuiggewicht(UPDATED_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)
            .maximaleasbelasting(UPDATED_MAXIMALEASBELASTING)
            .maximaleoverspanning(UPDATED_MAXIMALEOVERSPANNING)
            .overbruggingsobjectdoorrijopening(UPDATED_OVERBRUGGINGSOBJECTDOORRIJOPENING)
            .type(UPDATED_TYPE)
            .waterobject(UPDATED_WATEROBJECT)
            .zwaarstevoertuig(UPDATED_ZWAARSTEVOERTUIG);
        return viaduct;
    }

    @BeforeEach
    public void initTest() {
        viaduct = createEntity(em);
    }

    @Test
    @Transactional
    void createViaduct() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Viaduct
        var returnedViaduct = om.readValue(
            restViaductMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(viaduct)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Viaduct.class
        );

        // Validate the Viaduct in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertViaductUpdatableFieldsEquals(returnedViaduct, getPersistedViaduct(returnedViaduct));
    }

    @Test
    @Transactional
    void createViaductWithExistingId() throws Exception {
        // Create the Viaduct with an existing ID
        viaduct.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restViaductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(viaduct)))
            .andExpect(status().isBadRequest());

        // Validate the Viaduct in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllViaducts() throws Exception {
        // Initialize the database
        viaductRepository.saveAndFlush(viaduct);

        // Get all the viaductList
        restViaductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viaduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantaloverspanningen").value(hasItem(DEFAULT_AANTALOVERSPANNINGEN)))
            .andExpect(jsonPath("$.[*].belastingklassenieuw").value(hasItem(DEFAULT_BELASTINGKLASSENIEUW)))
            .andExpect(jsonPath("$.[*].belastingklasseoud").value(hasItem(DEFAULT_BELASTINGKLASSEOUD)))
            .andExpect(jsonPath("$.[*].draagvermogen").value(hasItem(DEFAULT_DRAAGVERMOGEN)))
            .andExpect(jsonPath("$.[*].maximaaltoelaatbaarvoertuiggewicht").value(hasItem(DEFAULT_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)))
            .andExpect(jsonPath("$.[*].maximaleasbelasting").value(hasItem(DEFAULT_MAXIMALEASBELASTING)))
            .andExpect(jsonPath("$.[*].maximaleoverspanning").value(hasItem(DEFAULT_MAXIMALEOVERSPANNING)))
            .andExpect(jsonPath("$.[*].overbruggingsobjectdoorrijopening").value(hasItem(DEFAULT_OVERBRUGGINGSOBJECTDOORRIJOPENING)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].waterobject").value(hasItem(DEFAULT_WATEROBJECT)))
            .andExpect(jsonPath("$.[*].zwaarstevoertuig").value(hasItem(DEFAULT_ZWAARSTEVOERTUIG)));
    }

    @Test
    @Transactional
    void getViaduct() throws Exception {
        // Initialize the database
        viaductRepository.saveAndFlush(viaduct);

        // Get the viaduct
        restViaductMockMvc
            .perform(get(ENTITY_API_URL_ID, viaduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(viaduct.getId().intValue()))
            .andExpect(jsonPath("$.aantaloverspanningen").value(DEFAULT_AANTALOVERSPANNINGEN))
            .andExpect(jsonPath("$.belastingklassenieuw").value(DEFAULT_BELASTINGKLASSENIEUW))
            .andExpect(jsonPath("$.belastingklasseoud").value(DEFAULT_BELASTINGKLASSEOUD))
            .andExpect(jsonPath("$.draagvermogen").value(DEFAULT_DRAAGVERMOGEN))
            .andExpect(jsonPath("$.maximaaltoelaatbaarvoertuiggewicht").value(DEFAULT_MAXIMAALTOELAATBAARVOERTUIGGEWICHT))
            .andExpect(jsonPath("$.maximaleasbelasting").value(DEFAULT_MAXIMALEASBELASTING))
            .andExpect(jsonPath("$.maximaleoverspanning").value(DEFAULT_MAXIMALEOVERSPANNING))
            .andExpect(jsonPath("$.overbruggingsobjectdoorrijopening").value(DEFAULT_OVERBRUGGINGSOBJECTDOORRIJOPENING))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.waterobject").value(DEFAULT_WATEROBJECT))
            .andExpect(jsonPath("$.zwaarstevoertuig").value(DEFAULT_ZWAARSTEVOERTUIG));
    }

    @Test
    @Transactional
    void getNonExistingViaduct() throws Exception {
        // Get the viaduct
        restViaductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingViaduct() throws Exception {
        // Initialize the database
        viaductRepository.saveAndFlush(viaduct);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the viaduct
        Viaduct updatedViaduct = viaductRepository.findById(viaduct.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedViaduct are not directly saved in db
        em.detach(updatedViaduct);
        updatedViaduct
            .aantaloverspanningen(UPDATED_AANTALOVERSPANNINGEN)
            .belastingklassenieuw(UPDATED_BELASTINGKLASSENIEUW)
            .belastingklasseoud(UPDATED_BELASTINGKLASSEOUD)
            .draagvermogen(UPDATED_DRAAGVERMOGEN)
            .maximaaltoelaatbaarvoertuiggewicht(UPDATED_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)
            .maximaleasbelasting(UPDATED_MAXIMALEASBELASTING)
            .maximaleoverspanning(UPDATED_MAXIMALEOVERSPANNING)
            .overbruggingsobjectdoorrijopening(UPDATED_OVERBRUGGINGSOBJECTDOORRIJOPENING)
            .type(UPDATED_TYPE)
            .waterobject(UPDATED_WATEROBJECT)
            .zwaarstevoertuig(UPDATED_ZWAARSTEVOERTUIG);

        restViaductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedViaduct.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedViaduct))
            )
            .andExpect(status().isOk());

        // Validate the Viaduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedViaductToMatchAllProperties(updatedViaduct);
    }

    @Test
    @Transactional
    void putNonExistingViaduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        viaduct.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restViaductMockMvc
            .perform(put(ENTITY_API_URL_ID, viaduct.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(viaduct)))
            .andExpect(status().isBadRequest());

        // Validate the Viaduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchViaduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        viaduct.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViaductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(viaduct))
            )
            .andExpect(status().isBadRequest());

        // Validate the Viaduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamViaduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        viaduct.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViaductMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(viaduct)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Viaduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateViaductWithPatch() throws Exception {
        // Initialize the database
        viaductRepository.saveAndFlush(viaduct);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the viaduct using partial update
        Viaduct partialUpdatedViaduct = new Viaduct();
        partialUpdatedViaduct.setId(viaduct.getId());

        partialUpdatedViaduct
            .maximaaltoelaatbaarvoertuiggewicht(UPDATED_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)
            .maximaleasbelasting(UPDATED_MAXIMALEASBELASTING)
            .maximaleoverspanning(UPDATED_MAXIMALEOVERSPANNING)
            .overbruggingsobjectdoorrijopening(UPDATED_OVERBRUGGINGSOBJECTDOORRIJOPENING)
            .waterobject(UPDATED_WATEROBJECT);

        restViaductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedViaduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedViaduct))
            )
            .andExpect(status().isOk());

        // Validate the Viaduct in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertViaductUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedViaduct, viaduct), getPersistedViaduct(viaduct));
    }

    @Test
    @Transactional
    void fullUpdateViaductWithPatch() throws Exception {
        // Initialize the database
        viaductRepository.saveAndFlush(viaduct);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the viaduct using partial update
        Viaduct partialUpdatedViaduct = new Viaduct();
        partialUpdatedViaduct.setId(viaduct.getId());

        partialUpdatedViaduct
            .aantaloverspanningen(UPDATED_AANTALOVERSPANNINGEN)
            .belastingklassenieuw(UPDATED_BELASTINGKLASSENIEUW)
            .belastingklasseoud(UPDATED_BELASTINGKLASSEOUD)
            .draagvermogen(UPDATED_DRAAGVERMOGEN)
            .maximaaltoelaatbaarvoertuiggewicht(UPDATED_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)
            .maximaleasbelasting(UPDATED_MAXIMALEASBELASTING)
            .maximaleoverspanning(UPDATED_MAXIMALEOVERSPANNING)
            .overbruggingsobjectdoorrijopening(UPDATED_OVERBRUGGINGSOBJECTDOORRIJOPENING)
            .type(UPDATED_TYPE)
            .waterobject(UPDATED_WATEROBJECT)
            .zwaarstevoertuig(UPDATED_ZWAARSTEVOERTUIG);

        restViaductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedViaduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedViaduct))
            )
            .andExpect(status().isOk());

        // Validate the Viaduct in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertViaductUpdatableFieldsEquals(partialUpdatedViaduct, getPersistedViaduct(partialUpdatedViaduct));
    }

    @Test
    @Transactional
    void patchNonExistingViaduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        viaduct.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restViaductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, viaduct.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(viaduct))
            )
            .andExpect(status().isBadRequest());

        // Validate the Viaduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchViaduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        viaduct.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViaductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(viaduct))
            )
            .andExpect(status().isBadRequest());

        // Validate the Viaduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamViaduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        viaduct.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViaductMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(viaduct)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Viaduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteViaduct() throws Exception {
        // Initialize the database
        viaductRepository.saveAndFlush(viaduct);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the viaduct
        restViaductMockMvc
            .perform(delete(ENTITY_API_URL_ID, viaduct.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return viaductRepository.count();
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

    protected Viaduct getPersistedViaduct(Viaduct viaduct) {
        return viaductRepository.findById(viaduct.getId()).orElseThrow();
    }

    protected void assertPersistedViaductToMatchAllProperties(Viaduct expectedViaduct) {
        assertViaductAllPropertiesEquals(expectedViaduct, getPersistedViaduct(expectedViaduct));
    }

    protected void assertPersistedViaductToMatchUpdatableProperties(Viaduct expectedViaduct) {
        assertViaductAllUpdatablePropertiesEquals(expectedViaduct, getPersistedViaduct(expectedViaduct));
    }
}
