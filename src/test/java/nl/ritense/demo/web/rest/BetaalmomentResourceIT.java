package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BetaalmomentAsserts.*;
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
import nl.ritense.demo.domain.Betaalmoment;
import nl.ritense.demo.repository.BetaalmomentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BetaalmomentResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class BetaalmomentResourceIT {

    private static final BigDecimal DEFAULT_BEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAG = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_VOORSCHOT = false;
    private static final Boolean UPDATED_VOORSCHOT = true;

    private static final String ENTITY_API_URL = "/api/betaalmoments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BetaalmomentRepository betaalmomentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBetaalmomentMockMvc;

    private Betaalmoment betaalmoment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Betaalmoment createEntity(EntityManager em) {
        Betaalmoment betaalmoment = new Betaalmoment().bedrag(DEFAULT_BEDRAG).datum(DEFAULT_DATUM).voorschot(DEFAULT_VOORSCHOT);
        return betaalmoment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Betaalmoment createUpdatedEntity(EntityManager em) {
        Betaalmoment betaalmoment = new Betaalmoment().bedrag(UPDATED_BEDRAG).datum(UPDATED_DATUM).voorschot(UPDATED_VOORSCHOT);
        return betaalmoment;
    }

    @BeforeEach
    public void initTest() {
        betaalmoment = createEntity(em);
    }

    @Test
    @Transactional
    void createBetaalmoment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Betaalmoment
        var returnedBetaalmoment = om.readValue(
            restBetaalmomentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(betaalmoment)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Betaalmoment.class
        );

        // Validate the Betaalmoment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBetaalmomentUpdatableFieldsEquals(returnedBetaalmoment, getPersistedBetaalmoment(returnedBetaalmoment));
    }

    @Test
    @Transactional
    void createBetaalmomentWithExistingId() throws Exception {
        // Create the Betaalmoment with an existing ID
        betaalmoment.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBetaalmomentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(betaalmoment)))
            .andExpect(status().isBadRequest());

        // Validate the Betaalmoment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBetaalmoments() throws Exception {
        // Initialize the database
        betaalmomentRepository.saveAndFlush(betaalmoment);

        // Get all the betaalmomentList
        restBetaalmomentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(betaalmoment.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(sameNumber(DEFAULT_BEDRAG))))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].voorschot").value(hasItem(DEFAULT_VOORSCHOT.booleanValue())));
    }

    @Test
    @Transactional
    void getBetaalmoment() throws Exception {
        // Initialize the database
        betaalmomentRepository.saveAndFlush(betaalmoment);

        // Get the betaalmoment
        restBetaalmomentMockMvc
            .perform(get(ENTITY_API_URL_ID, betaalmoment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(betaalmoment.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(sameNumber(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.voorschot").value(DEFAULT_VOORSCHOT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingBetaalmoment() throws Exception {
        // Get the betaalmoment
        restBetaalmomentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBetaalmoment() throws Exception {
        // Initialize the database
        betaalmomentRepository.saveAndFlush(betaalmoment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the betaalmoment
        Betaalmoment updatedBetaalmoment = betaalmomentRepository.findById(betaalmoment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBetaalmoment are not directly saved in db
        em.detach(updatedBetaalmoment);
        updatedBetaalmoment.bedrag(UPDATED_BEDRAG).datum(UPDATED_DATUM).voorschot(UPDATED_VOORSCHOT);

        restBetaalmomentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBetaalmoment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBetaalmoment))
            )
            .andExpect(status().isOk());

        // Validate the Betaalmoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBetaalmomentToMatchAllProperties(updatedBetaalmoment);
    }

    @Test
    @Transactional
    void putNonExistingBetaalmoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betaalmoment.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBetaalmomentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, betaalmoment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(betaalmoment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Betaalmoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBetaalmoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betaalmoment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBetaalmomentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(betaalmoment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Betaalmoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBetaalmoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betaalmoment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBetaalmomentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(betaalmoment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Betaalmoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBetaalmomentWithPatch() throws Exception {
        // Initialize the database
        betaalmomentRepository.saveAndFlush(betaalmoment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the betaalmoment using partial update
        Betaalmoment partialUpdatedBetaalmoment = new Betaalmoment();
        partialUpdatedBetaalmoment.setId(betaalmoment.getId());

        partialUpdatedBetaalmoment.voorschot(UPDATED_VOORSCHOT);

        restBetaalmomentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBetaalmoment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBetaalmoment))
            )
            .andExpect(status().isOk());

        // Validate the Betaalmoment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBetaalmomentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBetaalmoment, betaalmoment),
            getPersistedBetaalmoment(betaalmoment)
        );
    }

    @Test
    @Transactional
    void fullUpdateBetaalmomentWithPatch() throws Exception {
        // Initialize the database
        betaalmomentRepository.saveAndFlush(betaalmoment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the betaalmoment using partial update
        Betaalmoment partialUpdatedBetaalmoment = new Betaalmoment();
        partialUpdatedBetaalmoment.setId(betaalmoment.getId());

        partialUpdatedBetaalmoment.bedrag(UPDATED_BEDRAG).datum(UPDATED_DATUM).voorschot(UPDATED_VOORSCHOT);

        restBetaalmomentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBetaalmoment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBetaalmoment))
            )
            .andExpect(status().isOk());

        // Validate the Betaalmoment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBetaalmomentUpdatableFieldsEquals(partialUpdatedBetaalmoment, getPersistedBetaalmoment(partialUpdatedBetaalmoment));
    }

    @Test
    @Transactional
    void patchNonExistingBetaalmoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betaalmoment.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBetaalmomentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, betaalmoment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(betaalmoment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Betaalmoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBetaalmoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betaalmoment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBetaalmomentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(betaalmoment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Betaalmoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBetaalmoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betaalmoment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBetaalmomentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(betaalmoment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Betaalmoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBetaalmoment() throws Exception {
        // Initialize the database
        betaalmomentRepository.saveAndFlush(betaalmoment);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the betaalmoment
        restBetaalmomentMockMvc
            .perform(delete(ENTITY_API_URL_ID, betaalmoment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return betaalmomentRepository.count();
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

    protected Betaalmoment getPersistedBetaalmoment(Betaalmoment betaalmoment) {
        return betaalmomentRepository.findById(betaalmoment.getId()).orElseThrow();
    }

    protected void assertPersistedBetaalmomentToMatchAllProperties(Betaalmoment expectedBetaalmoment) {
        assertBetaalmomentAllPropertiesEquals(expectedBetaalmoment, getPersistedBetaalmoment(expectedBetaalmoment));
    }

    protected void assertPersistedBetaalmomentToMatchUpdatableProperties(Betaalmoment expectedBetaalmoment) {
        assertBetaalmomentAllUpdatablePropertiesEquals(expectedBetaalmoment, getPersistedBetaalmoment(expectedBetaalmoment));
    }
}
