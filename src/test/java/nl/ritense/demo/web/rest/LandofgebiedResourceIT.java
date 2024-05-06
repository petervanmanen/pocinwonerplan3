package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LandofgebiedAsserts.*;
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
import nl.ritense.demo.domain.Landofgebied;
import nl.ritense.demo.repository.LandofgebiedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LandofgebiedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LandofgebiedResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDELAND = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDELAND = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINGANGLAND = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINGANGLAND = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LANDCODE = "AAAAAAAAAA";
    private static final String UPDATED_LANDCODE = "BBBBBBBBBB";

    private static final String DEFAULT_LANDCODEISO = "AAAAAAAAAA";
    private static final String UPDATED_LANDCODEISO = "BBBBBBBBBB";

    private static final String DEFAULT_LANDNAAM = "AAAAAAAAAA";
    private static final String UPDATED_LANDNAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/landofgebieds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LandofgebiedRepository landofgebiedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLandofgebiedMockMvc;

    private Landofgebied landofgebied;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Landofgebied createEntity(EntityManager em) {
        Landofgebied landofgebied = new Landofgebied()
            .datumeindeland(DEFAULT_DATUMEINDELAND)
            .datumingangland(DEFAULT_DATUMINGANGLAND)
            .landcode(DEFAULT_LANDCODE)
            .landcodeiso(DEFAULT_LANDCODEISO)
            .landnaam(DEFAULT_LANDNAAM);
        return landofgebied;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Landofgebied createUpdatedEntity(EntityManager em) {
        Landofgebied landofgebied = new Landofgebied()
            .datumeindeland(UPDATED_DATUMEINDELAND)
            .datumingangland(UPDATED_DATUMINGANGLAND)
            .landcode(UPDATED_LANDCODE)
            .landcodeiso(UPDATED_LANDCODEISO)
            .landnaam(UPDATED_LANDNAAM);
        return landofgebied;
    }

    @BeforeEach
    public void initTest() {
        landofgebied = createEntity(em);
    }

    @Test
    @Transactional
    void createLandofgebied() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Landofgebied
        var returnedLandofgebied = om.readValue(
            restLandofgebiedMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(landofgebied)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Landofgebied.class
        );

        // Validate the Landofgebied in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLandofgebiedUpdatableFieldsEquals(returnedLandofgebied, getPersistedLandofgebied(returnedLandofgebied));
    }

    @Test
    @Transactional
    void createLandofgebiedWithExistingId() throws Exception {
        // Create the Landofgebied with an existing ID
        landofgebied.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLandofgebiedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(landofgebied)))
            .andExpect(status().isBadRequest());

        // Validate the Landofgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLandofgebieds() throws Exception {
        // Initialize the database
        landofgebiedRepository.saveAndFlush(landofgebied);

        // Get all the landofgebiedList
        restLandofgebiedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(landofgebied.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeindeland").value(hasItem(DEFAULT_DATUMEINDELAND.toString())))
            .andExpect(jsonPath("$.[*].datumingangland").value(hasItem(DEFAULT_DATUMINGANGLAND.toString())))
            .andExpect(jsonPath("$.[*].landcode").value(hasItem(DEFAULT_LANDCODE)))
            .andExpect(jsonPath("$.[*].landcodeiso").value(hasItem(DEFAULT_LANDCODEISO)))
            .andExpect(jsonPath("$.[*].landnaam").value(hasItem(DEFAULT_LANDNAAM)));
    }

    @Test
    @Transactional
    void getLandofgebied() throws Exception {
        // Initialize the database
        landofgebiedRepository.saveAndFlush(landofgebied);

        // Get the landofgebied
        restLandofgebiedMockMvc
            .perform(get(ENTITY_API_URL_ID, landofgebied.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(landofgebied.getId().intValue()))
            .andExpect(jsonPath("$.datumeindeland").value(DEFAULT_DATUMEINDELAND.toString()))
            .andExpect(jsonPath("$.datumingangland").value(DEFAULT_DATUMINGANGLAND.toString()))
            .andExpect(jsonPath("$.landcode").value(DEFAULT_LANDCODE))
            .andExpect(jsonPath("$.landcodeiso").value(DEFAULT_LANDCODEISO))
            .andExpect(jsonPath("$.landnaam").value(DEFAULT_LANDNAAM));
    }

    @Test
    @Transactional
    void getNonExistingLandofgebied() throws Exception {
        // Get the landofgebied
        restLandofgebiedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLandofgebied() throws Exception {
        // Initialize the database
        landofgebiedRepository.saveAndFlush(landofgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the landofgebied
        Landofgebied updatedLandofgebied = landofgebiedRepository.findById(landofgebied.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLandofgebied are not directly saved in db
        em.detach(updatedLandofgebied);
        updatedLandofgebied
            .datumeindeland(UPDATED_DATUMEINDELAND)
            .datumingangland(UPDATED_DATUMINGANGLAND)
            .landcode(UPDATED_LANDCODE)
            .landcodeiso(UPDATED_LANDCODEISO)
            .landnaam(UPDATED_LANDNAAM);

        restLandofgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLandofgebied.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLandofgebied))
            )
            .andExpect(status().isOk());

        // Validate the Landofgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLandofgebiedToMatchAllProperties(updatedLandofgebied);
    }

    @Test
    @Transactional
    void putNonExistingLandofgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landofgebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandofgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, landofgebied.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(landofgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Landofgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLandofgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landofgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandofgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(landofgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Landofgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLandofgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landofgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandofgebiedMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(landofgebied)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Landofgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLandofgebiedWithPatch() throws Exception {
        // Initialize the database
        landofgebiedRepository.saveAndFlush(landofgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the landofgebied using partial update
        Landofgebied partialUpdatedLandofgebied = new Landofgebied();
        partialUpdatedLandofgebied.setId(landofgebied.getId());

        partialUpdatedLandofgebied
            .datumeindeland(UPDATED_DATUMEINDELAND)
            .datumingangland(UPDATED_DATUMINGANGLAND)
            .landnaam(UPDATED_LANDNAAM);

        restLandofgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLandofgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLandofgebied))
            )
            .andExpect(status().isOk());

        // Validate the Landofgebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLandofgebiedUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLandofgebied, landofgebied),
            getPersistedLandofgebied(landofgebied)
        );
    }

    @Test
    @Transactional
    void fullUpdateLandofgebiedWithPatch() throws Exception {
        // Initialize the database
        landofgebiedRepository.saveAndFlush(landofgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the landofgebied using partial update
        Landofgebied partialUpdatedLandofgebied = new Landofgebied();
        partialUpdatedLandofgebied.setId(landofgebied.getId());

        partialUpdatedLandofgebied
            .datumeindeland(UPDATED_DATUMEINDELAND)
            .datumingangland(UPDATED_DATUMINGANGLAND)
            .landcode(UPDATED_LANDCODE)
            .landcodeiso(UPDATED_LANDCODEISO)
            .landnaam(UPDATED_LANDNAAM);

        restLandofgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLandofgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLandofgebied))
            )
            .andExpect(status().isOk());

        // Validate the Landofgebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLandofgebiedUpdatableFieldsEquals(partialUpdatedLandofgebied, getPersistedLandofgebied(partialUpdatedLandofgebied));
    }

    @Test
    @Transactional
    void patchNonExistingLandofgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landofgebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandofgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, landofgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(landofgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Landofgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLandofgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landofgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandofgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(landofgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Landofgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLandofgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        landofgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLandofgebiedMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(landofgebied)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Landofgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLandofgebied() throws Exception {
        // Initialize the database
        landofgebiedRepository.saveAndFlush(landofgebied);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the landofgebied
        restLandofgebiedMockMvc
            .perform(delete(ENTITY_API_URL_ID, landofgebied.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return landofgebiedRepository.count();
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

    protected Landofgebied getPersistedLandofgebied(Landofgebied landofgebied) {
        return landofgebiedRepository.findById(landofgebied.getId()).orElseThrow();
    }

    protected void assertPersistedLandofgebiedToMatchAllProperties(Landofgebied expectedLandofgebied) {
        assertLandofgebiedAllPropertiesEquals(expectedLandofgebied, getPersistedLandofgebied(expectedLandofgebied));
    }

    protected void assertPersistedLandofgebiedToMatchUpdatableProperties(Landofgebied expectedLandofgebied) {
        assertLandofgebiedAllUpdatablePropertiesEquals(expectedLandofgebied, getPersistedLandofgebied(expectedLandofgebied));
    }
}
