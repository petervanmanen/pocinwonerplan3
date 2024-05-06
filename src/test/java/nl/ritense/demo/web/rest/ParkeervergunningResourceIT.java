package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ParkeervergunningAsserts.*;
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
import nl.ritense.demo.domain.Parkeervergunning;
import nl.ritense.demo.repository.ParkeervergunningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ParkeervergunningResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParkeervergunningResourceIT {

    private static final String DEFAULT_DATUMEINDEGELDIGHEID = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDEGELDIGHEID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMRESERVERING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMRESERVERING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DATUMSTART = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSTART = "BBBBBBBBBB";

    private static final String DEFAULT_KENTEKEN = "AAAAAAAAAA";
    private static final String UPDATED_KENTEKEN = "BBBBBBBBBB";

    private static final String DEFAULT_MINUTENAFGESCHREVEN = "AAAAAAAAAA";
    private static final String UPDATED_MINUTENAFGESCHREVEN = "BBBBBBBBBB";

    private static final String DEFAULT_MINUTENGELDIG = "AAAAAAAAAA";
    private static final String UPDATED_MINUTENGELDIG = "BBBBBBBBBB";

    private static final String DEFAULT_MINUTENRESTEREND = "AAAAAAAAAA";
    private static final String UPDATED_MINUTENRESTEREND = "BBBBBBBBBB";

    private static final String DEFAULT_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/parkeervergunnings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ParkeervergunningRepository parkeervergunningRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParkeervergunningMockMvc;

    private Parkeervergunning parkeervergunning;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parkeervergunning createEntity(EntityManager em) {
        Parkeervergunning parkeervergunning = new Parkeervergunning()
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .datumreservering(DEFAULT_DATUMRESERVERING)
            .datumstart(DEFAULT_DATUMSTART)
            .kenteken(DEFAULT_KENTEKEN)
            .minutenafgeschreven(DEFAULT_MINUTENAFGESCHREVEN)
            .minutengeldig(DEFAULT_MINUTENGELDIG)
            .minutenresterend(DEFAULT_MINUTENRESTEREND)
            .nummer(DEFAULT_NUMMER)
            .type(DEFAULT_TYPE);
        return parkeervergunning;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parkeervergunning createUpdatedEntity(EntityManager em) {
        Parkeervergunning parkeervergunning = new Parkeervergunning()
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumreservering(UPDATED_DATUMRESERVERING)
            .datumstart(UPDATED_DATUMSTART)
            .kenteken(UPDATED_KENTEKEN)
            .minutenafgeschreven(UPDATED_MINUTENAFGESCHREVEN)
            .minutengeldig(UPDATED_MINUTENGELDIG)
            .minutenresterend(UPDATED_MINUTENRESTEREND)
            .nummer(UPDATED_NUMMER)
            .type(UPDATED_TYPE);
        return parkeervergunning;
    }

    @BeforeEach
    public void initTest() {
        parkeervergunning = createEntity(em);
    }

    @Test
    @Transactional
    void createParkeervergunning() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Parkeervergunning
        var returnedParkeervergunning = om.readValue(
            restParkeervergunningMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parkeervergunning)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Parkeervergunning.class
        );

        // Validate the Parkeervergunning in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertParkeervergunningUpdatableFieldsEquals(returnedParkeervergunning, getPersistedParkeervergunning(returnedParkeervergunning));
    }

    @Test
    @Transactional
    void createParkeervergunningWithExistingId() throws Exception {
        // Create the Parkeervergunning with an existing ID
        parkeervergunning.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParkeervergunningMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parkeervergunning)))
            .andExpect(status().isBadRequest());

        // Validate the Parkeervergunning in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParkeervergunnings() throws Exception {
        // Initialize the database
        parkeervergunningRepository.saveAndFlush(parkeervergunning);

        // Get all the parkeervergunningList
        restParkeervergunningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parkeervergunning.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID)))
            .andExpect(jsonPath("$.[*].datumreservering").value(hasItem(DEFAULT_DATUMRESERVERING.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART)))
            .andExpect(jsonPath("$.[*].kenteken").value(hasItem(DEFAULT_KENTEKEN)))
            .andExpect(jsonPath("$.[*].minutenafgeschreven").value(hasItem(DEFAULT_MINUTENAFGESCHREVEN)))
            .andExpect(jsonPath("$.[*].minutengeldig").value(hasItem(DEFAULT_MINUTENGELDIG)))
            .andExpect(jsonPath("$.[*].minutenresterend").value(hasItem(DEFAULT_MINUTENRESTEREND)))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getParkeervergunning() throws Exception {
        // Initialize the database
        parkeervergunningRepository.saveAndFlush(parkeervergunning);

        // Get the parkeervergunning
        restParkeervergunningMockMvc
            .perform(get(ENTITY_API_URL_ID, parkeervergunning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parkeervergunning.getId().intValue()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID))
            .andExpect(jsonPath("$.datumreservering").value(DEFAULT_DATUMRESERVERING.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART))
            .andExpect(jsonPath("$.kenteken").value(DEFAULT_KENTEKEN))
            .andExpect(jsonPath("$.minutenafgeschreven").value(DEFAULT_MINUTENAFGESCHREVEN))
            .andExpect(jsonPath("$.minutengeldig").value(DEFAULT_MINUTENGELDIG))
            .andExpect(jsonPath("$.minutenresterend").value(DEFAULT_MINUTENRESTEREND))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingParkeervergunning() throws Exception {
        // Get the parkeervergunning
        restParkeervergunningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingParkeervergunning() throws Exception {
        // Initialize the database
        parkeervergunningRepository.saveAndFlush(parkeervergunning);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parkeervergunning
        Parkeervergunning updatedParkeervergunning = parkeervergunningRepository.findById(parkeervergunning.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedParkeervergunning are not directly saved in db
        em.detach(updatedParkeervergunning);
        updatedParkeervergunning
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumreservering(UPDATED_DATUMRESERVERING)
            .datumstart(UPDATED_DATUMSTART)
            .kenteken(UPDATED_KENTEKEN)
            .minutenafgeschreven(UPDATED_MINUTENAFGESCHREVEN)
            .minutengeldig(UPDATED_MINUTENGELDIG)
            .minutenresterend(UPDATED_MINUTENRESTEREND)
            .nummer(UPDATED_NUMMER)
            .type(UPDATED_TYPE);

        restParkeervergunningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedParkeervergunning.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedParkeervergunning))
            )
            .andExpect(status().isOk());

        // Validate the Parkeervergunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedParkeervergunningToMatchAllProperties(updatedParkeervergunning);
    }

    @Test
    @Transactional
    void putNonExistingParkeervergunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeervergunning.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkeervergunningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parkeervergunning.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parkeervergunning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeervergunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParkeervergunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeervergunning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeervergunningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parkeervergunning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeervergunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParkeervergunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeervergunning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeervergunningMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parkeervergunning)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parkeervergunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParkeervergunningWithPatch() throws Exception {
        // Initialize the database
        parkeervergunningRepository.saveAndFlush(parkeervergunning);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parkeervergunning using partial update
        Parkeervergunning partialUpdatedParkeervergunning = new Parkeervergunning();
        partialUpdatedParkeervergunning.setId(parkeervergunning.getId());

        partialUpdatedParkeervergunning
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumreservering(UPDATED_DATUMRESERVERING)
            .minutenafgeschreven(UPDATED_MINUTENAFGESCHREVEN)
            .minutengeldig(UPDATED_MINUTENGELDIG)
            .minutenresterend(UPDATED_MINUTENRESTEREND)
            .type(UPDATED_TYPE);

        restParkeervergunningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParkeervergunning.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParkeervergunning))
            )
            .andExpect(status().isOk());

        // Validate the Parkeervergunning in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParkeervergunningUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedParkeervergunning, parkeervergunning),
            getPersistedParkeervergunning(parkeervergunning)
        );
    }

    @Test
    @Transactional
    void fullUpdateParkeervergunningWithPatch() throws Exception {
        // Initialize the database
        parkeervergunningRepository.saveAndFlush(parkeervergunning);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parkeervergunning using partial update
        Parkeervergunning partialUpdatedParkeervergunning = new Parkeervergunning();
        partialUpdatedParkeervergunning.setId(parkeervergunning.getId());

        partialUpdatedParkeervergunning
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumreservering(UPDATED_DATUMRESERVERING)
            .datumstart(UPDATED_DATUMSTART)
            .kenteken(UPDATED_KENTEKEN)
            .minutenafgeschreven(UPDATED_MINUTENAFGESCHREVEN)
            .minutengeldig(UPDATED_MINUTENGELDIG)
            .minutenresterend(UPDATED_MINUTENRESTEREND)
            .nummer(UPDATED_NUMMER)
            .type(UPDATED_TYPE);

        restParkeervergunningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParkeervergunning.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParkeervergunning))
            )
            .andExpect(status().isOk());

        // Validate the Parkeervergunning in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParkeervergunningUpdatableFieldsEquals(
            partialUpdatedParkeervergunning,
            getPersistedParkeervergunning(partialUpdatedParkeervergunning)
        );
    }

    @Test
    @Transactional
    void patchNonExistingParkeervergunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeervergunning.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkeervergunningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parkeervergunning.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parkeervergunning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeervergunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParkeervergunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeervergunning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeervergunningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parkeervergunning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeervergunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParkeervergunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeervergunning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeervergunningMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(parkeervergunning)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parkeervergunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParkeervergunning() throws Exception {
        // Initialize the database
        parkeervergunningRepository.saveAndFlush(parkeervergunning);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the parkeervergunning
        restParkeervergunningMockMvc
            .perform(delete(ENTITY_API_URL_ID, parkeervergunning.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return parkeervergunningRepository.count();
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

    protected Parkeervergunning getPersistedParkeervergunning(Parkeervergunning parkeervergunning) {
        return parkeervergunningRepository.findById(parkeervergunning.getId()).orElseThrow();
    }

    protected void assertPersistedParkeervergunningToMatchAllProperties(Parkeervergunning expectedParkeervergunning) {
        assertParkeervergunningAllPropertiesEquals(expectedParkeervergunning, getPersistedParkeervergunning(expectedParkeervergunning));
    }

    protected void assertPersistedParkeervergunningToMatchUpdatableProperties(Parkeervergunning expectedParkeervergunning) {
        assertParkeervergunningAllUpdatablePropertiesEquals(
            expectedParkeervergunning,
            getPersistedParkeervergunning(expectedParkeervergunning)
        );
    }
}
