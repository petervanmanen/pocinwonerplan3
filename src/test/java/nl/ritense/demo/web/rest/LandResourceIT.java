package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LandAsserts.*;
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
import nl.ritense.demo.domain.Land;
import nl.ritense.demo.repository.LandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LandResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LandResourceIT {

    private static final Boolean DEFAULT_DATUMEINDEFICTIEF = false;
    private static final Boolean UPDATED_DATUMEINDEFICTIEF = true;

    private static final String DEFAULT_DATUMEINDELAND = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDELAND = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMINGANGLAND = "AAAAAAAAAA";
    private static final String UPDATED_DATUMINGANGLAND = "BBBBBBBBBB";

    private static final String DEFAULT_LANDCODE = "AAAAAAAAAA";
    private static final String UPDATED_LANDCODE = "BBBBBBBBBB";

    private static final String DEFAULT_LANDCODEISODRIELETTERIG = "AAAAAAAAAA";
    private static final String UPDATED_LANDCODEISODRIELETTERIG = "BBBBBBBBBB";

    private static final String DEFAULT_LANDCODEISOTWEELETTERIG = "AAAAAAAAAA";
    private static final String UPDATED_LANDCODEISOTWEELETTERIG = "BBBBBBBBBB";

    private static final String DEFAULT_LANDNAAM = "AAAAAAAAAA";
    private static final String UPDATED_LANDNAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/lands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LandRepository landRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLandMockMvc;

    private Land land;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Land createEntity(EntityManager em) {
        Land land = new Land()
            .datumeindefictief(DEFAULT_DATUMEINDEFICTIEF)
            .datumeindeland(DEFAULT_DATUMEINDELAND)
            .datumingangland(DEFAULT_DATUMINGANGLAND)
            .landcode(DEFAULT_LANDCODE)
            .landcodeisodrieletterig(DEFAULT_LANDCODEISODRIELETTERIG)
            .landcodeisotweeletterig(DEFAULT_LANDCODEISOTWEELETTERIG)
            .landnaam(DEFAULT_LANDNAAM);
        return land;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Land createUpdatedEntity(EntityManager em) {
        Land land = new Land()
            .datumeindefictief(UPDATED_DATUMEINDEFICTIEF)
            .datumeindeland(UPDATED_DATUMEINDELAND)
            .datumingangland(UPDATED_DATUMINGANGLAND)
            .landcode(UPDATED_LANDCODE)
            .landcodeisodrieletterig(UPDATED_LANDCODEISODRIELETTERIG)
            .landcodeisotweeletterig(UPDATED_LANDCODEISOTWEELETTERIG)
            .landnaam(UPDATED_LANDNAAM);
        return land;
    }

    @BeforeEach
    public void initTest() {
        land = createEntity(em);
    }

    @Test
    @Transactional
    void createLand() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Land
        var returnedLand = om.readValue(
            restLandMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(land)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Land.class
        );

        // Validate the Land in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLandUpdatableFieldsEquals(returnedLand, getPersistedLand(returnedLand));
    }

    @Test
    @Transactional
    void createLandWithExistingId() throws Exception {
        // Create the Land with an existing ID
        land.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(land)))
            .andExpect(status().isBadRequest());

        // Validate the Land in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLands() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        // Get all the landList
        restLandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(land.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeindefictief").value(hasItem(DEFAULT_DATUMEINDEFICTIEF.booleanValue())))
            .andExpect(jsonPath("$.[*].datumeindeland").value(hasItem(DEFAULT_DATUMEINDELAND)))
            .andExpect(jsonPath("$.[*].datumingangland").value(hasItem(DEFAULT_DATUMINGANGLAND)))
            .andExpect(jsonPath("$.[*].landcode").value(hasItem(DEFAULT_LANDCODE)))
            .andExpect(jsonPath("$.[*].landcodeisodrieletterig").value(hasItem(DEFAULT_LANDCODEISODRIELETTERIG)))
            .andExpect(jsonPath("$.[*].landcodeisotweeletterig").value(hasItem(DEFAULT_LANDCODEISOTWEELETTERIG)))
            .andExpect(jsonPath("$.[*].landnaam").value(hasItem(DEFAULT_LANDNAAM)));
    }

    @Test
    @Transactional
    void getLand() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        // Get the land
        restLandMockMvc
            .perform(get(ENTITY_API_URL_ID, land.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(land.getId().intValue()))
            .andExpect(jsonPath("$.datumeindefictief").value(DEFAULT_DATUMEINDEFICTIEF.booleanValue()))
            .andExpect(jsonPath("$.datumeindeland").value(DEFAULT_DATUMEINDELAND))
            .andExpect(jsonPath("$.datumingangland").value(DEFAULT_DATUMINGANGLAND))
            .andExpect(jsonPath("$.landcode").value(DEFAULT_LANDCODE))
            .andExpect(jsonPath("$.landcodeisodrieletterig").value(DEFAULT_LANDCODEISODRIELETTERIG))
            .andExpect(jsonPath("$.landcodeisotweeletterig").value(DEFAULT_LANDCODEISOTWEELETTERIG))
            .andExpect(jsonPath("$.landnaam").value(DEFAULT_LANDNAAM));
    }

    @Test
    @Transactional
    void getNonExistingLand() throws Exception {
        // Get the land
        restLandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLand() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the land
        Land updatedLand = landRepository.findById(land.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLand are not directly saved in db
        em.detach(updatedLand);
        updatedLand
            .datumeindefictief(UPDATED_DATUMEINDEFICTIEF)
            .datumeindeland(UPDATED_DATUMEINDELAND)
            .datumingangland(UPDATED_DATUMINGANGLAND)
            .landcode(UPDATED_LANDCODE)
            .landcodeisodrieletterig(UPDATED_LANDCODEISODRIELETTERIG)
            .landcodeisotweeletterig(UPDATED_LANDCODEISOTWEELETTERIG)
            .landnaam(UPDATED_LANDNAAM);

        restLandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLand.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLand))
            )
            .andExpect(status().isOk());

        // Validate the Land in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLandToMatchAllProperties(updatedLand);
    }

    @Test
    @Transactional
    void putNonExistingLand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        land.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(put(ENTITY_API_URL_ID, land.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(land)))
            .andExpect(status().isBadRequest());

        // Validate the Land in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        land.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(land))
            )
            .andExpect(status().isBadRequest());

        // Validate the Land in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        land.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(land)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Land in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLandWithPatch() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the land using partial update
        Land partialUpdatedLand = new Land();
        partialUpdatedLand.setId(land.getId());

        partialUpdatedLand.datumeindefictief(UPDATED_DATUMEINDEFICTIEF).landcodeisodrieletterig(UPDATED_LANDCODEISODRIELETTERIG);

        restLandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLand.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLand))
            )
            .andExpect(status().isOk());

        // Validate the Land in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLandUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLand, land), getPersistedLand(land));
    }

    @Test
    @Transactional
    void fullUpdateLandWithPatch() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the land using partial update
        Land partialUpdatedLand = new Land();
        partialUpdatedLand.setId(land.getId());

        partialUpdatedLand
            .datumeindefictief(UPDATED_DATUMEINDEFICTIEF)
            .datumeindeland(UPDATED_DATUMEINDELAND)
            .datumingangland(UPDATED_DATUMINGANGLAND)
            .landcode(UPDATED_LANDCODE)
            .landcodeisodrieletterig(UPDATED_LANDCODEISODRIELETTERIG)
            .landcodeisotweeletterig(UPDATED_LANDCODEISOTWEELETTERIG)
            .landnaam(UPDATED_LANDNAAM);

        restLandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLand.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLand))
            )
            .andExpect(status().isOk());

        // Validate the Land in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLandUpdatableFieldsEquals(partialUpdatedLand, getPersistedLand(partialUpdatedLand));
    }

    @Test
    @Transactional
    void patchNonExistingLand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        land.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(patch(ENTITY_API_URL_ID, land.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(land)))
            .andExpect(status().isBadRequest());

        // Validate the Land in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        land.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(land))
            )
            .andExpect(status().isBadRequest());

        // Validate the Land in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        land.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(land)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Land in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLand() throws Exception {
        // Initialize the database
        landRepository.saveAndFlush(land);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the land
        restLandMockMvc
            .perform(delete(ENTITY_API_URL_ID, land.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return landRepository.count();
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

    protected Land getPersistedLand(Land land) {
        return landRepository.findById(land.getId()).orElseThrow();
    }

    protected void assertPersistedLandToMatchAllProperties(Land expectedLand) {
        assertLandAllPropertiesEquals(expectedLand, getPersistedLand(expectedLand));
    }

    protected void assertPersistedLandToMatchUpdatableProperties(Land expectedLand) {
        assertLandAllUpdatablePropertiesEquals(expectedLand, getPersistedLand(expectedLand));
    }
}
