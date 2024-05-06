package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FlyoverAsserts.*;
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
import nl.ritense.demo.domain.Flyover;
import nl.ritense.demo.repository.FlyoverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FlyoverResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FlyoverResourceIT {

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

    private static final String DEFAULT_ZWAARSTEVOERTUIG = "AAAAAAAAAA";
    private static final String UPDATED_ZWAARSTEVOERTUIG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/flyovers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FlyoverRepository flyoverRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFlyoverMockMvc;

    private Flyover flyover;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Flyover createEntity(EntityManager em) {
        Flyover flyover = new Flyover()
            .aantaloverspanningen(DEFAULT_AANTALOVERSPANNINGEN)
            .belastingklassenieuw(DEFAULT_BELASTINGKLASSENIEUW)
            .belastingklasseoud(DEFAULT_BELASTINGKLASSEOUD)
            .draagvermogen(DEFAULT_DRAAGVERMOGEN)
            .maximaaltoelaatbaarvoertuiggewicht(DEFAULT_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)
            .maximaleasbelasting(DEFAULT_MAXIMALEASBELASTING)
            .maximaleoverspanning(DEFAULT_MAXIMALEOVERSPANNING)
            .overbruggingsobjectdoorrijopening(DEFAULT_OVERBRUGGINGSOBJECTDOORRIJOPENING)
            .type(DEFAULT_TYPE)
            .zwaarstevoertuig(DEFAULT_ZWAARSTEVOERTUIG);
        return flyover;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Flyover createUpdatedEntity(EntityManager em) {
        Flyover flyover = new Flyover()
            .aantaloverspanningen(UPDATED_AANTALOVERSPANNINGEN)
            .belastingklassenieuw(UPDATED_BELASTINGKLASSENIEUW)
            .belastingklasseoud(UPDATED_BELASTINGKLASSEOUD)
            .draagvermogen(UPDATED_DRAAGVERMOGEN)
            .maximaaltoelaatbaarvoertuiggewicht(UPDATED_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)
            .maximaleasbelasting(UPDATED_MAXIMALEASBELASTING)
            .maximaleoverspanning(UPDATED_MAXIMALEOVERSPANNING)
            .overbruggingsobjectdoorrijopening(UPDATED_OVERBRUGGINGSOBJECTDOORRIJOPENING)
            .type(UPDATED_TYPE)
            .zwaarstevoertuig(UPDATED_ZWAARSTEVOERTUIG);
        return flyover;
    }

    @BeforeEach
    public void initTest() {
        flyover = createEntity(em);
    }

    @Test
    @Transactional
    void createFlyover() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Flyover
        var returnedFlyover = om.readValue(
            restFlyoverMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(flyover)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Flyover.class
        );

        // Validate the Flyover in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFlyoverUpdatableFieldsEquals(returnedFlyover, getPersistedFlyover(returnedFlyover));
    }

    @Test
    @Transactional
    void createFlyoverWithExistingId() throws Exception {
        // Create the Flyover with an existing ID
        flyover.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFlyoverMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(flyover)))
            .andExpect(status().isBadRequest());

        // Validate the Flyover in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFlyovers() throws Exception {
        // Initialize the database
        flyoverRepository.saveAndFlush(flyover);

        // Get all the flyoverList
        restFlyoverMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flyover.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantaloverspanningen").value(hasItem(DEFAULT_AANTALOVERSPANNINGEN)))
            .andExpect(jsonPath("$.[*].belastingklassenieuw").value(hasItem(DEFAULT_BELASTINGKLASSENIEUW)))
            .andExpect(jsonPath("$.[*].belastingklasseoud").value(hasItem(DEFAULT_BELASTINGKLASSEOUD)))
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
    void getFlyover() throws Exception {
        // Initialize the database
        flyoverRepository.saveAndFlush(flyover);

        // Get the flyover
        restFlyoverMockMvc
            .perform(get(ENTITY_API_URL_ID, flyover.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(flyover.getId().intValue()))
            .andExpect(jsonPath("$.aantaloverspanningen").value(DEFAULT_AANTALOVERSPANNINGEN))
            .andExpect(jsonPath("$.belastingklassenieuw").value(DEFAULT_BELASTINGKLASSENIEUW))
            .andExpect(jsonPath("$.belastingklasseoud").value(DEFAULT_BELASTINGKLASSEOUD))
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
    void getNonExistingFlyover() throws Exception {
        // Get the flyover
        restFlyoverMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFlyover() throws Exception {
        // Initialize the database
        flyoverRepository.saveAndFlush(flyover);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the flyover
        Flyover updatedFlyover = flyoverRepository.findById(flyover.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFlyover are not directly saved in db
        em.detach(updatedFlyover);
        updatedFlyover
            .aantaloverspanningen(UPDATED_AANTALOVERSPANNINGEN)
            .belastingklassenieuw(UPDATED_BELASTINGKLASSENIEUW)
            .belastingklasseoud(UPDATED_BELASTINGKLASSEOUD)
            .draagvermogen(UPDATED_DRAAGVERMOGEN)
            .maximaaltoelaatbaarvoertuiggewicht(UPDATED_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)
            .maximaleasbelasting(UPDATED_MAXIMALEASBELASTING)
            .maximaleoverspanning(UPDATED_MAXIMALEOVERSPANNING)
            .overbruggingsobjectdoorrijopening(UPDATED_OVERBRUGGINGSOBJECTDOORRIJOPENING)
            .type(UPDATED_TYPE)
            .zwaarstevoertuig(UPDATED_ZWAARSTEVOERTUIG);

        restFlyoverMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFlyover.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFlyover))
            )
            .andExpect(status().isOk());

        // Validate the Flyover in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFlyoverToMatchAllProperties(updatedFlyover);
    }

    @Test
    @Transactional
    void putNonExistingFlyover() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        flyover.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlyoverMockMvc
            .perform(put(ENTITY_API_URL_ID, flyover.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(flyover)))
            .andExpect(status().isBadRequest());

        // Validate the Flyover in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFlyover() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        flyover.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlyoverMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(flyover))
            )
            .andExpect(status().isBadRequest());

        // Validate the Flyover in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFlyover() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        flyover.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlyoverMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(flyover)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Flyover in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFlyoverWithPatch() throws Exception {
        // Initialize the database
        flyoverRepository.saveAndFlush(flyover);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the flyover using partial update
        Flyover partialUpdatedFlyover = new Flyover();
        partialUpdatedFlyover.setId(flyover.getId());

        partialUpdatedFlyover
            .aantaloverspanningen(UPDATED_AANTALOVERSPANNINGEN)
            .belastingklassenieuw(UPDATED_BELASTINGKLASSENIEUW)
            .draagvermogen(UPDATED_DRAAGVERMOGEN)
            .maximaleoverspanning(UPDATED_MAXIMALEOVERSPANNING)
            .zwaarstevoertuig(UPDATED_ZWAARSTEVOERTUIG);

        restFlyoverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFlyover.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFlyover))
            )
            .andExpect(status().isOk());

        // Validate the Flyover in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFlyoverUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedFlyover, flyover), getPersistedFlyover(flyover));
    }

    @Test
    @Transactional
    void fullUpdateFlyoverWithPatch() throws Exception {
        // Initialize the database
        flyoverRepository.saveAndFlush(flyover);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the flyover using partial update
        Flyover partialUpdatedFlyover = new Flyover();
        partialUpdatedFlyover.setId(flyover.getId());

        partialUpdatedFlyover
            .aantaloverspanningen(UPDATED_AANTALOVERSPANNINGEN)
            .belastingklassenieuw(UPDATED_BELASTINGKLASSENIEUW)
            .belastingklasseoud(UPDATED_BELASTINGKLASSEOUD)
            .draagvermogen(UPDATED_DRAAGVERMOGEN)
            .maximaaltoelaatbaarvoertuiggewicht(UPDATED_MAXIMAALTOELAATBAARVOERTUIGGEWICHT)
            .maximaleasbelasting(UPDATED_MAXIMALEASBELASTING)
            .maximaleoverspanning(UPDATED_MAXIMALEOVERSPANNING)
            .overbruggingsobjectdoorrijopening(UPDATED_OVERBRUGGINGSOBJECTDOORRIJOPENING)
            .type(UPDATED_TYPE)
            .zwaarstevoertuig(UPDATED_ZWAARSTEVOERTUIG);

        restFlyoverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFlyover.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFlyover))
            )
            .andExpect(status().isOk());

        // Validate the Flyover in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFlyoverUpdatableFieldsEquals(partialUpdatedFlyover, getPersistedFlyover(partialUpdatedFlyover));
    }

    @Test
    @Transactional
    void patchNonExistingFlyover() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        flyover.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlyoverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, flyover.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(flyover))
            )
            .andExpect(status().isBadRequest());

        // Validate the Flyover in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFlyover() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        flyover.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlyoverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(flyover))
            )
            .andExpect(status().isBadRequest());

        // Validate the Flyover in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFlyover() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        flyover.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlyoverMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(flyover)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Flyover in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFlyover() throws Exception {
        // Initialize the database
        flyoverRepository.saveAndFlush(flyover);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the flyover
        restFlyoverMockMvc
            .perform(delete(ENTITY_API_URL_ID, flyover.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return flyoverRepository.count();
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

    protected Flyover getPersistedFlyover(Flyover flyover) {
        return flyoverRepository.findById(flyover.getId()).orElseThrow();
    }

    protected void assertPersistedFlyoverToMatchAllProperties(Flyover expectedFlyover) {
        assertFlyoverAllPropertiesEquals(expectedFlyover, getPersistedFlyover(expectedFlyover));
    }

    protected void assertPersistedFlyoverToMatchUpdatableProperties(Flyover expectedFlyover) {
        assertFlyoverAllUpdatablePropertiesEquals(expectedFlyover, getPersistedFlyover(expectedFlyover));
    }
}
