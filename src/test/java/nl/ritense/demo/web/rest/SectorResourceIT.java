package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SectorAsserts.*;
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
import nl.ritense.demo.domain.Sector;
import nl.ritense.demo.repository.SectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SectorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SectorResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sectors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSectorMockMvc;

    private Sector sector;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sector createEntity(EntityManager em) {
        Sector sector = new Sector().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return sector;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sector createUpdatedEntity(EntityManager em) {
        Sector sector = new Sector().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return sector;
    }

    @BeforeEach
    public void initTest() {
        sector = createEntity(em);
    }

    @Test
    @Transactional
    void createSector() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sector
        var returnedSector = om.readValue(
            restSectorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sector)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Sector.class
        );

        // Validate the Sector in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSectorUpdatableFieldsEquals(returnedSector, getPersistedSector(returnedSector));
    }

    @Test
    @Transactional
    void createSectorWithExistingId() throws Exception {
        // Create the Sector with an existing ID
        sector.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSectorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sector)))
            .andExpect(status().isBadRequest());

        // Validate the Sector in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSectors() throws Exception {
        // Initialize the database
        sectorRepository.saveAndFlush(sector);

        // Get all the sectorList
        restSectorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sector.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getSector() throws Exception {
        // Initialize the database
        sectorRepository.saveAndFlush(sector);

        // Get the sector
        restSectorMockMvc
            .perform(get(ENTITY_API_URL_ID, sector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sector.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingSector() throws Exception {
        // Get the sector
        restSectorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSector() throws Exception {
        // Initialize the database
        sectorRepository.saveAndFlush(sector);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sector
        Sector updatedSector = sectorRepository.findById(sector.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSector are not directly saved in db
        em.detach(updatedSector);
        updatedSector.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restSectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSector.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSector))
            )
            .andExpect(status().isOk());

        // Validate the Sector in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSectorToMatchAllProperties(updatedSector);
    }

    @Test
    @Transactional
    void putNonExistingSector() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sector.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSectorMockMvc
            .perform(put(ENTITY_API_URL_ID, sector.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sector)))
            .andExpect(status().isBadRequest());

        // Validate the Sector in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSector() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sector.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sector))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sector in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSector() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sector.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSectorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sector)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sector in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSectorWithPatch() throws Exception {
        // Initialize the database
        sectorRepository.saveAndFlush(sector);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sector using partial update
        Sector partialUpdatedSector = new Sector();
        partialUpdatedSector.setId(sector.getId());

        partialUpdatedSector.omschrijving(UPDATED_OMSCHRIJVING);

        restSectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSector.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSector))
            )
            .andExpect(status().isOk());

        // Validate the Sector in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSectorUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSector, sector), getPersistedSector(sector));
    }

    @Test
    @Transactional
    void fullUpdateSectorWithPatch() throws Exception {
        // Initialize the database
        sectorRepository.saveAndFlush(sector);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sector using partial update
        Sector partialUpdatedSector = new Sector();
        partialUpdatedSector.setId(sector.getId());

        partialUpdatedSector.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restSectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSector.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSector))
            )
            .andExpect(status().isOk());

        // Validate the Sector in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSectorUpdatableFieldsEquals(partialUpdatedSector, getPersistedSector(partialUpdatedSector));
    }

    @Test
    @Transactional
    void patchNonExistingSector() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sector.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sector.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sector))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sector in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSector() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sector.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sector))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sector in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSector() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sector.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSectorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sector)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sector in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSector() throws Exception {
        // Initialize the database
        sectorRepository.saveAndFlush(sector);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sector
        restSectorMockMvc
            .perform(delete(ENTITY_API_URL_ID, sector.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sectorRepository.count();
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

    protected Sector getPersistedSector(Sector sector) {
        return sectorRepository.findById(sector.getId()).orElseThrow();
    }

    protected void assertPersistedSectorToMatchAllProperties(Sector expectedSector) {
        assertSectorAllPropertiesEquals(expectedSector, getPersistedSector(expectedSector));
    }

    protected void assertPersistedSectorToMatchUpdatableProperties(Sector expectedSector) {
        assertSectorAllUpdatablePropertiesEquals(expectedSector, getPersistedSector(expectedSector));
    }
}
