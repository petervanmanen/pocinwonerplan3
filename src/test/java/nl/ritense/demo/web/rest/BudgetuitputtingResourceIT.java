package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BudgetuitputtingAsserts.*;
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
import nl.ritense.demo.domain.Budgetuitputting;
import nl.ritense.demo.repository.BudgetuitputtingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BudgetuitputtingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BudgetuitputtingResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_UITGENUTBEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_UITGENUTBEDRAG = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/budgetuitputtings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BudgetuitputtingRepository budgetuitputtingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBudgetuitputtingMockMvc;

    private Budgetuitputting budgetuitputting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Budgetuitputting createEntity(EntityManager em) {
        Budgetuitputting budgetuitputting = new Budgetuitputting().datum(DEFAULT_DATUM).uitgenutbedrag(DEFAULT_UITGENUTBEDRAG);
        return budgetuitputting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Budgetuitputting createUpdatedEntity(EntityManager em) {
        Budgetuitputting budgetuitputting = new Budgetuitputting().datum(UPDATED_DATUM).uitgenutbedrag(UPDATED_UITGENUTBEDRAG);
        return budgetuitputting;
    }

    @BeforeEach
    public void initTest() {
        budgetuitputting = createEntity(em);
    }

    @Test
    @Transactional
    void createBudgetuitputting() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Budgetuitputting
        var returnedBudgetuitputting = om.readValue(
            restBudgetuitputtingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(budgetuitputting)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Budgetuitputting.class
        );

        // Validate the Budgetuitputting in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBudgetuitputtingUpdatableFieldsEquals(returnedBudgetuitputting, getPersistedBudgetuitputting(returnedBudgetuitputting));
    }

    @Test
    @Transactional
    void createBudgetuitputtingWithExistingId() throws Exception {
        // Create the Budgetuitputting with an existing ID
        budgetuitputting.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBudgetuitputtingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(budgetuitputting)))
            .andExpect(status().isBadRequest());

        // Validate the Budgetuitputting in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBudgetuitputtings() throws Exception {
        // Initialize the database
        budgetuitputtingRepository.saveAndFlush(budgetuitputting);

        // Get all the budgetuitputtingList
        restBudgetuitputtingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(budgetuitputting.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].uitgenutbedrag").value(hasItem(sameNumber(DEFAULT_UITGENUTBEDRAG))));
    }

    @Test
    @Transactional
    void getBudgetuitputting() throws Exception {
        // Initialize the database
        budgetuitputtingRepository.saveAndFlush(budgetuitputting);

        // Get the budgetuitputting
        restBudgetuitputtingMockMvc
            .perform(get(ENTITY_API_URL_ID, budgetuitputting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(budgetuitputting.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.uitgenutbedrag").value(sameNumber(DEFAULT_UITGENUTBEDRAG)));
    }

    @Test
    @Transactional
    void getNonExistingBudgetuitputting() throws Exception {
        // Get the budgetuitputting
        restBudgetuitputtingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBudgetuitputting() throws Exception {
        // Initialize the database
        budgetuitputtingRepository.saveAndFlush(budgetuitputting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the budgetuitputting
        Budgetuitputting updatedBudgetuitputting = budgetuitputtingRepository.findById(budgetuitputting.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBudgetuitputting are not directly saved in db
        em.detach(updatedBudgetuitputting);
        updatedBudgetuitputting.datum(UPDATED_DATUM).uitgenutbedrag(UPDATED_UITGENUTBEDRAG);

        restBudgetuitputtingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBudgetuitputting.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBudgetuitputting))
            )
            .andExpect(status().isOk());

        // Validate the Budgetuitputting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBudgetuitputtingToMatchAllProperties(updatedBudgetuitputting);
    }

    @Test
    @Transactional
    void putNonExistingBudgetuitputting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        budgetuitputting.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBudgetuitputtingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, budgetuitputting.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(budgetuitputting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Budgetuitputting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBudgetuitputting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        budgetuitputting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBudgetuitputtingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(budgetuitputting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Budgetuitputting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBudgetuitputting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        budgetuitputting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBudgetuitputtingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(budgetuitputting)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Budgetuitputting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBudgetuitputtingWithPatch() throws Exception {
        // Initialize the database
        budgetuitputtingRepository.saveAndFlush(budgetuitputting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the budgetuitputting using partial update
        Budgetuitputting partialUpdatedBudgetuitputting = new Budgetuitputting();
        partialUpdatedBudgetuitputting.setId(budgetuitputting.getId());

        partialUpdatedBudgetuitputting.datum(UPDATED_DATUM).uitgenutbedrag(UPDATED_UITGENUTBEDRAG);

        restBudgetuitputtingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBudgetuitputting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBudgetuitputting))
            )
            .andExpect(status().isOk());

        // Validate the Budgetuitputting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBudgetuitputtingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBudgetuitputting, budgetuitputting),
            getPersistedBudgetuitputting(budgetuitputting)
        );
    }

    @Test
    @Transactional
    void fullUpdateBudgetuitputtingWithPatch() throws Exception {
        // Initialize the database
        budgetuitputtingRepository.saveAndFlush(budgetuitputting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the budgetuitputting using partial update
        Budgetuitputting partialUpdatedBudgetuitputting = new Budgetuitputting();
        partialUpdatedBudgetuitputting.setId(budgetuitputting.getId());

        partialUpdatedBudgetuitputting.datum(UPDATED_DATUM).uitgenutbedrag(UPDATED_UITGENUTBEDRAG);

        restBudgetuitputtingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBudgetuitputting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBudgetuitputting))
            )
            .andExpect(status().isOk());

        // Validate the Budgetuitputting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBudgetuitputtingUpdatableFieldsEquals(
            partialUpdatedBudgetuitputting,
            getPersistedBudgetuitputting(partialUpdatedBudgetuitputting)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBudgetuitputting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        budgetuitputting.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBudgetuitputtingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, budgetuitputting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(budgetuitputting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Budgetuitputting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBudgetuitputting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        budgetuitputting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBudgetuitputtingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(budgetuitputting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Budgetuitputting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBudgetuitputting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        budgetuitputting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBudgetuitputtingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(budgetuitputting)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Budgetuitputting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBudgetuitputting() throws Exception {
        // Initialize the database
        budgetuitputtingRepository.saveAndFlush(budgetuitputting);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the budgetuitputting
        restBudgetuitputtingMockMvc
            .perform(delete(ENTITY_API_URL_ID, budgetuitputting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return budgetuitputtingRepository.count();
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

    protected Budgetuitputting getPersistedBudgetuitputting(Budgetuitputting budgetuitputting) {
        return budgetuitputtingRepository.findById(budgetuitputting.getId()).orElseThrow();
    }

    protected void assertPersistedBudgetuitputtingToMatchAllProperties(Budgetuitputting expectedBudgetuitputting) {
        assertBudgetuitputtingAllPropertiesEquals(expectedBudgetuitputting, getPersistedBudgetuitputting(expectedBudgetuitputting));
    }

    protected void assertPersistedBudgetuitputtingToMatchUpdatableProperties(Budgetuitputting expectedBudgetuitputting) {
        assertBudgetuitputtingAllUpdatablePropertiesEquals(
            expectedBudgetuitputting,
            getPersistedBudgetuitputting(expectedBudgetuitputting)
        );
    }
}
