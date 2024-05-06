package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PgbtoekenningAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Pgbtoekenning;
import nl.ritense.demo.repository.PgbtoekenningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PgbtoekenningResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PgbtoekenningResourceIT {

    private static final BigDecimal DEFAULT_BUDGET = new BigDecimal(1);
    private static final BigDecimal UPDATED_BUDGET = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMTOEKENNING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMTOEKENNING = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/pgbtoekennings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PgbtoekenningRepository pgbtoekenningRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPgbtoekenningMockMvc;

    private Pgbtoekenning pgbtoekenning;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pgbtoekenning createEntity(EntityManager em) {
        Pgbtoekenning pgbtoekenning = new Pgbtoekenning()
            .budget(DEFAULT_BUDGET)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumtoekenning(DEFAULT_DATUMTOEKENNING);
        return pgbtoekenning;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pgbtoekenning createUpdatedEntity(EntityManager em) {
        Pgbtoekenning pgbtoekenning = new Pgbtoekenning()
            .budget(UPDATED_BUDGET)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumtoekenning(UPDATED_DATUMTOEKENNING);
        return pgbtoekenning;
    }

    @BeforeEach
    public void initTest() {
        pgbtoekenning = createEntity(em);
    }

    @Test
    @Transactional
    void createPgbtoekenning() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Pgbtoekenning
        var returnedPgbtoekenning = om.readValue(
            restPgbtoekenningMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pgbtoekenning)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Pgbtoekenning.class
        );

        // Validate the Pgbtoekenning in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPgbtoekenningUpdatableFieldsEquals(returnedPgbtoekenning, getPersistedPgbtoekenning(returnedPgbtoekenning));
    }

    @Test
    @Transactional
    void createPgbtoekenningWithExistingId() throws Exception {
        // Create the Pgbtoekenning with an existing ID
        pgbtoekenning.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgbtoekenningMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pgbtoekenning)))
            .andExpect(status().isBadRequest());

        // Validate the Pgbtoekenning in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPgbtoekennings() throws Exception {
        // Initialize the database
        pgbtoekenningRepository.saveAndFlush(pgbtoekenning);

        // Get all the pgbtoekenningList
        restPgbtoekenningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgbtoekenning.getId().intValue())))
            .andExpect(jsonPath("$.[*].budget").value(hasItem(sameNumber(DEFAULT_BUDGET))))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumtoekenning").value(hasItem(DEFAULT_DATUMTOEKENNING.toString())));
    }

    @Test
    @Transactional
    void getPgbtoekenning() throws Exception {
        // Initialize the database
        pgbtoekenningRepository.saveAndFlush(pgbtoekenning);

        // Get the pgbtoekenning
        restPgbtoekenningMockMvc
            .perform(get(ENTITY_API_URL_ID, pgbtoekenning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pgbtoekenning.getId().intValue()))
            .andExpect(jsonPath("$.budget").value(sameNumber(DEFAULT_BUDGET)))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumtoekenning").value(DEFAULT_DATUMTOEKENNING.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPgbtoekenning() throws Exception {
        // Get the pgbtoekenning
        restPgbtoekenningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPgbtoekenning() throws Exception {
        // Initialize the database
        pgbtoekenningRepository.saveAndFlush(pgbtoekenning);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pgbtoekenning
        Pgbtoekenning updatedPgbtoekenning = pgbtoekenningRepository.findById(pgbtoekenning.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPgbtoekenning are not directly saved in db
        em.detach(updatedPgbtoekenning);
        updatedPgbtoekenning.budget(UPDATED_BUDGET).datumeinde(UPDATED_DATUMEINDE).datumtoekenning(UPDATED_DATUMTOEKENNING);

        restPgbtoekenningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPgbtoekenning.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPgbtoekenning))
            )
            .andExpect(status().isOk());

        // Validate the Pgbtoekenning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPgbtoekenningToMatchAllProperties(updatedPgbtoekenning);
    }

    @Test
    @Transactional
    void putNonExistingPgbtoekenning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pgbtoekenning.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgbtoekenningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pgbtoekenning.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pgbtoekenning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pgbtoekenning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPgbtoekenning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pgbtoekenning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPgbtoekenningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pgbtoekenning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pgbtoekenning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPgbtoekenning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pgbtoekenning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPgbtoekenningMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pgbtoekenning)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pgbtoekenning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePgbtoekenningWithPatch() throws Exception {
        // Initialize the database
        pgbtoekenningRepository.saveAndFlush(pgbtoekenning);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pgbtoekenning using partial update
        Pgbtoekenning partialUpdatedPgbtoekenning = new Pgbtoekenning();
        partialUpdatedPgbtoekenning.setId(pgbtoekenning.getId());

        partialUpdatedPgbtoekenning.budget(UPDATED_BUDGET).datumtoekenning(UPDATED_DATUMTOEKENNING);

        restPgbtoekenningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPgbtoekenning.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPgbtoekenning))
            )
            .andExpect(status().isOk());

        // Validate the Pgbtoekenning in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPgbtoekenningUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPgbtoekenning, pgbtoekenning),
            getPersistedPgbtoekenning(pgbtoekenning)
        );
    }

    @Test
    @Transactional
    void fullUpdatePgbtoekenningWithPatch() throws Exception {
        // Initialize the database
        pgbtoekenningRepository.saveAndFlush(pgbtoekenning);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pgbtoekenning using partial update
        Pgbtoekenning partialUpdatedPgbtoekenning = new Pgbtoekenning();
        partialUpdatedPgbtoekenning.setId(pgbtoekenning.getId());

        partialUpdatedPgbtoekenning.budget(UPDATED_BUDGET).datumeinde(UPDATED_DATUMEINDE).datumtoekenning(UPDATED_DATUMTOEKENNING);

        restPgbtoekenningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPgbtoekenning.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPgbtoekenning))
            )
            .andExpect(status().isOk());

        // Validate the Pgbtoekenning in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPgbtoekenningUpdatableFieldsEquals(partialUpdatedPgbtoekenning, getPersistedPgbtoekenning(partialUpdatedPgbtoekenning));
    }

    @Test
    @Transactional
    void patchNonExistingPgbtoekenning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pgbtoekenning.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgbtoekenningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pgbtoekenning.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pgbtoekenning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pgbtoekenning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPgbtoekenning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pgbtoekenning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPgbtoekenningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pgbtoekenning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pgbtoekenning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPgbtoekenning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pgbtoekenning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPgbtoekenningMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pgbtoekenning)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pgbtoekenning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePgbtoekenning() throws Exception {
        // Initialize the database
        pgbtoekenningRepository.saveAndFlush(pgbtoekenning);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pgbtoekenning
        restPgbtoekenningMockMvc
            .perform(delete(ENTITY_API_URL_ID, pgbtoekenning.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pgbtoekenningRepository.count();
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

    protected Pgbtoekenning getPersistedPgbtoekenning(Pgbtoekenning pgbtoekenning) {
        return pgbtoekenningRepository.findById(pgbtoekenning.getId()).orElseThrow();
    }

    protected void assertPersistedPgbtoekenningToMatchAllProperties(Pgbtoekenning expectedPgbtoekenning) {
        assertPgbtoekenningAllPropertiesEquals(expectedPgbtoekenning, getPersistedPgbtoekenning(expectedPgbtoekenning));
    }

    protected void assertPersistedPgbtoekenningToMatchUpdatableProperties(Pgbtoekenning expectedPgbtoekenning) {
        assertPgbtoekenningAllUpdatablePropertiesEquals(expectedPgbtoekenning, getPersistedPgbtoekenning(expectedPgbtoekenning));
    }
}
