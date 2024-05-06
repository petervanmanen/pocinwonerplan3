package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BetalingAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Betaling;
import nl.ritense.demo.repository.BetalingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BetalingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BetalingResourceIT {

    private static final BigDecimal DEFAULT_BEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAG = new BigDecimal(2);

    private static final String DEFAULT_DATUMTIJD = "AAAAAAAAAA";
    private static final String UPDATED_DATUMTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_VALUTA = "AAAAAAAAAA";
    private static final String UPDATED_VALUTA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/betalings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BetalingRepository betalingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBetalingMockMvc;

    private Betaling betaling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Betaling createEntity(EntityManager em) {
        Betaling betaling = new Betaling()
            .bedrag(DEFAULT_BEDRAG)
            .datumtijd(DEFAULT_DATUMTIJD)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .valuta(DEFAULT_VALUTA);
        return betaling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Betaling createUpdatedEntity(EntityManager em) {
        Betaling betaling = new Betaling()
            .bedrag(UPDATED_BEDRAG)
            .datumtijd(UPDATED_DATUMTIJD)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .valuta(UPDATED_VALUTA);
        return betaling;
    }

    @BeforeEach
    public void initTest() {
        betaling = createEntity(em);
    }

    @Test
    @Transactional
    void createBetaling() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Betaling
        var returnedBetaling = om.readValue(
            restBetalingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(betaling)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Betaling.class
        );

        // Validate the Betaling in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBetalingUpdatableFieldsEquals(returnedBetaling, getPersistedBetaling(returnedBetaling));
    }

    @Test
    @Transactional
    void createBetalingWithExistingId() throws Exception {
        // Create the Betaling with an existing ID
        betaling.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBetalingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(betaling)))
            .andExpect(status().isBadRequest());

        // Validate the Betaling in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBetalings() throws Exception {
        // Initialize the database
        betalingRepository.saveAndFlush(betaling);

        // Get all the betalingList
        restBetalingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(betaling.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(sameNumber(DEFAULT_BEDRAG))))
            .andExpect(jsonPath("$.[*].datumtijd").value(hasItem(DEFAULT_DATUMTIJD)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].valuta").value(hasItem(DEFAULT_VALUTA)));
    }

    @Test
    @Transactional
    void getBetaling() throws Exception {
        // Initialize the database
        betalingRepository.saveAndFlush(betaling);

        // Get the betaling
        restBetalingMockMvc
            .perform(get(ENTITY_API_URL_ID, betaling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(betaling.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(sameNumber(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.datumtijd").value(DEFAULT_DATUMTIJD))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.valuta").value(DEFAULT_VALUTA));
    }

    @Test
    @Transactional
    void getNonExistingBetaling() throws Exception {
        // Get the betaling
        restBetalingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBetaling() throws Exception {
        // Initialize the database
        betalingRepository.saveAndFlush(betaling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the betaling
        Betaling updatedBetaling = betalingRepository.findById(betaling.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBetaling are not directly saved in db
        em.detach(updatedBetaling);
        updatedBetaling.bedrag(UPDATED_BEDRAG).datumtijd(UPDATED_DATUMTIJD).omschrijving(UPDATED_OMSCHRIJVING).valuta(UPDATED_VALUTA);

        restBetalingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBetaling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBetaling))
            )
            .andExpect(status().isOk());

        // Validate the Betaling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBetalingToMatchAllProperties(updatedBetaling);
    }

    @Test
    @Transactional
    void putNonExistingBetaling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betaling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBetalingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, betaling.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(betaling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Betaling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBetaling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betaling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBetalingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(betaling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Betaling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBetaling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betaling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBetalingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(betaling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Betaling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBetalingWithPatch() throws Exception {
        // Initialize the database
        betalingRepository.saveAndFlush(betaling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the betaling using partial update
        Betaling partialUpdatedBetaling = new Betaling();
        partialUpdatedBetaling.setId(betaling.getId());

        partialUpdatedBetaling.bedrag(UPDATED_BEDRAG).omschrijving(UPDATED_OMSCHRIJVING).valuta(UPDATED_VALUTA);

        restBetalingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBetaling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBetaling))
            )
            .andExpect(status().isOk());

        // Validate the Betaling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBetalingUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBetaling, betaling), getPersistedBetaling(betaling));
    }

    @Test
    @Transactional
    void fullUpdateBetalingWithPatch() throws Exception {
        // Initialize the database
        betalingRepository.saveAndFlush(betaling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the betaling using partial update
        Betaling partialUpdatedBetaling = new Betaling();
        partialUpdatedBetaling.setId(betaling.getId());

        partialUpdatedBetaling
            .bedrag(UPDATED_BEDRAG)
            .datumtijd(UPDATED_DATUMTIJD)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .valuta(UPDATED_VALUTA);

        restBetalingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBetaling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBetaling))
            )
            .andExpect(status().isOk());

        // Validate the Betaling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBetalingUpdatableFieldsEquals(partialUpdatedBetaling, getPersistedBetaling(partialUpdatedBetaling));
    }

    @Test
    @Transactional
    void patchNonExistingBetaling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betaling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBetalingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, betaling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(betaling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Betaling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBetaling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betaling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBetalingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(betaling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Betaling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBetaling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betaling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBetalingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(betaling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Betaling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBetaling() throws Exception {
        // Initialize the database
        betalingRepository.saveAndFlush(betaling);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the betaling
        restBetalingMockMvc
            .perform(delete(ENTITY_API_URL_ID, betaling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return betalingRepository.count();
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

    protected Betaling getPersistedBetaling(Betaling betaling) {
        return betalingRepository.findById(betaling.getId()).orElseThrow();
    }

    protected void assertPersistedBetalingToMatchAllProperties(Betaling expectedBetaling) {
        assertBetalingAllPropertiesEquals(expectedBetaling, getPersistedBetaling(expectedBetaling));
    }

    protected void assertPersistedBetalingToMatchUpdatableProperties(Betaling expectedBetaling) {
        assertBetalingAllUpdatablePropertiesEquals(expectedBetaling, getPersistedBetaling(expectedBetaling));
    }
}
