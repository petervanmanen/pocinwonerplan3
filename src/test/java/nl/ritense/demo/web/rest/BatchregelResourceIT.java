package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BatchregelAsserts.*;
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
import nl.ritense.demo.domain.Batchregel;
import nl.ritense.demo.repository.BatchregelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BatchregelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BatchregelResourceIT {

    private static final BigDecimal DEFAULT_BEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAG = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATUMBETALING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBETALING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_REKENINGNAAR = "AAAAAAAAAA";
    private static final String UPDATED_REKENINGNAAR = "BBBBBBBBBB";

    private static final String DEFAULT_REKENINGVAN = "AAAAAAAAAA";
    private static final String UPDATED_REKENINGVAN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/batchregels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BatchregelRepository batchregelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBatchregelMockMvc;

    private Batchregel batchregel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Batchregel createEntity(EntityManager em) {
        Batchregel batchregel = new Batchregel()
            .bedrag(DEFAULT_BEDRAG)
            .datumbetaling(DEFAULT_DATUMBETALING)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .rekeningnaar(DEFAULT_REKENINGNAAR)
            .rekeningvan(DEFAULT_REKENINGVAN);
        return batchregel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Batchregel createUpdatedEntity(EntityManager em) {
        Batchregel batchregel = new Batchregel()
            .bedrag(UPDATED_BEDRAG)
            .datumbetaling(UPDATED_DATUMBETALING)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .rekeningnaar(UPDATED_REKENINGNAAR)
            .rekeningvan(UPDATED_REKENINGVAN);
        return batchregel;
    }

    @BeforeEach
    public void initTest() {
        batchregel = createEntity(em);
    }

    @Test
    @Transactional
    void createBatchregel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Batchregel
        var returnedBatchregel = om.readValue(
            restBatchregelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batchregel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Batchregel.class
        );

        // Validate the Batchregel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBatchregelUpdatableFieldsEquals(returnedBatchregel, getPersistedBatchregel(returnedBatchregel));
    }

    @Test
    @Transactional
    void createBatchregelWithExistingId() throws Exception {
        // Create the Batchregel with an existing ID
        batchregel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBatchregelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batchregel)))
            .andExpect(status().isBadRequest());

        // Validate the Batchregel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBatchregels() throws Exception {
        // Initialize the database
        batchregelRepository.saveAndFlush(batchregel);

        // Get all the batchregelList
        restBatchregelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(batchregel.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(sameNumber(DEFAULT_BEDRAG))))
            .andExpect(jsonPath("$.[*].datumbetaling").value(hasItem(DEFAULT_DATUMBETALING.toString())))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].rekeningnaar").value(hasItem(DEFAULT_REKENINGNAAR)))
            .andExpect(jsonPath("$.[*].rekeningvan").value(hasItem(DEFAULT_REKENINGVAN)));
    }

    @Test
    @Transactional
    void getBatchregel() throws Exception {
        // Initialize the database
        batchregelRepository.saveAndFlush(batchregel);

        // Get the batchregel
        restBatchregelMockMvc
            .perform(get(ENTITY_API_URL_ID, batchregel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(batchregel.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(sameNumber(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.datumbetaling").value(DEFAULT_DATUMBETALING.toString()))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.rekeningnaar").value(DEFAULT_REKENINGNAAR))
            .andExpect(jsonPath("$.rekeningvan").value(DEFAULT_REKENINGVAN));
    }

    @Test
    @Transactional
    void getNonExistingBatchregel() throws Exception {
        // Get the batchregel
        restBatchregelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBatchregel() throws Exception {
        // Initialize the database
        batchregelRepository.saveAndFlush(batchregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the batchregel
        Batchregel updatedBatchregel = batchregelRepository.findById(batchregel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBatchregel are not directly saved in db
        em.detach(updatedBatchregel);
        updatedBatchregel
            .bedrag(UPDATED_BEDRAG)
            .datumbetaling(UPDATED_DATUMBETALING)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .rekeningnaar(UPDATED_REKENINGNAAR)
            .rekeningvan(UPDATED_REKENINGVAN);

        restBatchregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBatchregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBatchregel))
            )
            .andExpect(status().isOk());

        // Validate the Batchregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBatchregelToMatchAllProperties(updatedBatchregel);
    }

    @Test
    @Transactional
    void putNonExistingBatchregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBatchregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, batchregel.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batchregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Batchregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBatchregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(batchregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Batchregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBatchregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchregelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(batchregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Batchregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBatchregelWithPatch() throws Exception {
        // Initialize the database
        batchregelRepository.saveAndFlush(batchregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the batchregel using partial update
        Batchregel partialUpdatedBatchregel = new Batchregel();
        partialUpdatedBatchregel.setId(batchregel.getId());

        partialUpdatedBatchregel
            .datumbetaling(UPDATED_DATUMBETALING)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .rekeningnaar(UPDATED_REKENINGNAAR)
            .rekeningvan(UPDATED_REKENINGVAN);

        restBatchregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBatchregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBatchregel))
            )
            .andExpect(status().isOk());

        // Validate the Batchregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBatchregelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBatchregel, batchregel),
            getPersistedBatchregel(batchregel)
        );
    }

    @Test
    @Transactional
    void fullUpdateBatchregelWithPatch() throws Exception {
        // Initialize the database
        batchregelRepository.saveAndFlush(batchregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the batchregel using partial update
        Batchregel partialUpdatedBatchregel = new Batchregel();
        partialUpdatedBatchregel.setId(batchregel.getId());

        partialUpdatedBatchregel
            .bedrag(UPDATED_BEDRAG)
            .datumbetaling(UPDATED_DATUMBETALING)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .rekeningnaar(UPDATED_REKENINGNAAR)
            .rekeningvan(UPDATED_REKENINGVAN);

        restBatchregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBatchregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBatchregel))
            )
            .andExpect(status().isOk());

        // Validate the Batchregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBatchregelUpdatableFieldsEquals(partialUpdatedBatchregel, getPersistedBatchregel(partialUpdatedBatchregel));
    }

    @Test
    @Transactional
    void patchNonExistingBatchregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBatchregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, batchregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(batchregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Batchregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBatchregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(batchregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Batchregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBatchregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        batchregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchregelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(batchregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Batchregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBatchregel() throws Exception {
        // Initialize the database
        batchregelRepository.saveAndFlush(batchregel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the batchregel
        restBatchregelMockMvc
            .perform(delete(ENTITY_API_URL_ID, batchregel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return batchregelRepository.count();
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

    protected Batchregel getPersistedBatchregel(Batchregel batchregel) {
        return batchregelRepository.findById(batchregel.getId()).orElseThrow();
    }

    protected void assertPersistedBatchregelToMatchAllProperties(Batchregel expectedBatchregel) {
        assertBatchregelAllPropertiesEquals(expectedBatchregel, getPersistedBatchregel(expectedBatchregel));
    }

    protected void assertPersistedBatchregelToMatchUpdatableProperties(Batchregel expectedBatchregel) {
        assertBatchregelAllUpdatablePropertiesEquals(expectedBatchregel, getPersistedBatchregel(expectedBatchregel));
    }
}
