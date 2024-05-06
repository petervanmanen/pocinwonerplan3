package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BankrekeningAsserts.*;
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
import nl.ritense.demo.domain.Bankrekening;
import nl.ritense.demo.repository.BankrekeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BankrekeningResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankrekeningResourceIT {

    private static final String DEFAULT_BANK = "AAAAAAAAAA";
    private static final String UPDATED_BANK = "BBBBBBBBBB";

    private static final String DEFAULT_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_TENNAAMSTELLING = "AAAAAAAAAA";
    private static final String UPDATED_TENNAAMSTELLING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bankrekenings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BankrekeningRepository bankrekeningRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankrekeningMockMvc;

    private Bankrekening bankrekening;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bankrekening createEntity(EntityManager em) {
        Bankrekening bankrekening = new Bankrekening().bank(DEFAULT_BANK).nummer(DEFAULT_NUMMER).tennaamstelling(DEFAULT_TENNAAMSTELLING);
        return bankrekening;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bankrekening createUpdatedEntity(EntityManager em) {
        Bankrekening bankrekening = new Bankrekening().bank(UPDATED_BANK).nummer(UPDATED_NUMMER).tennaamstelling(UPDATED_TENNAAMSTELLING);
        return bankrekening;
    }

    @BeforeEach
    public void initTest() {
        bankrekening = createEntity(em);
    }

    @Test
    @Transactional
    void createBankrekening() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bankrekening
        var returnedBankrekening = om.readValue(
            restBankrekeningMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankrekening)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bankrekening.class
        );

        // Validate the Bankrekening in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBankrekeningUpdatableFieldsEquals(returnedBankrekening, getPersistedBankrekening(returnedBankrekening));
    }

    @Test
    @Transactional
    void createBankrekeningWithExistingId() throws Exception {
        // Create the Bankrekening with an existing ID
        bankrekening.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankrekeningMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankrekening)))
            .andExpect(status().isBadRequest());

        // Validate the Bankrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankrekenings() throws Exception {
        // Initialize the database
        bankrekeningRepository.saveAndFlush(bankrekening);

        // Get all the bankrekeningList
        restBankrekeningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankrekening.getId().intValue())))
            .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK)))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].tennaamstelling").value(hasItem(DEFAULT_TENNAAMSTELLING)));
    }

    @Test
    @Transactional
    void getBankrekening() throws Exception {
        // Initialize the database
        bankrekeningRepository.saveAndFlush(bankrekening);

        // Get the bankrekening
        restBankrekeningMockMvc
            .perform(get(ENTITY_API_URL_ID, bankrekening.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankrekening.getId().intValue()))
            .andExpect(jsonPath("$.bank").value(DEFAULT_BANK))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.tennaamstelling").value(DEFAULT_TENNAAMSTELLING));
    }

    @Test
    @Transactional
    void getNonExistingBankrekening() throws Exception {
        // Get the bankrekening
        restBankrekeningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBankrekening() throws Exception {
        // Initialize the database
        bankrekeningRepository.saveAndFlush(bankrekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankrekening
        Bankrekening updatedBankrekening = bankrekeningRepository.findById(bankrekening.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBankrekening are not directly saved in db
        em.detach(updatedBankrekening);
        updatedBankrekening.bank(UPDATED_BANK).nummer(UPDATED_NUMMER).tennaamstelling(UPDATED_TENNAAMSTELLING);

        restBankrekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBankrekening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBankrekening))
            )
            .andExpect(status().isOk());

        // Validate the Bankrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBankrekeningToMatchAllProperties(updatedBankrekening);
    }

    @Test
    @Transactional
    void putNonExistingBankrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankrekening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankrekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankrekening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankrekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankrekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankrekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankrekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankrekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankrekeningMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankrekening)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bankrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankrekeningWithPatch() throws Exception {
        // Initialize the database
        bankrekeningRepository.saveAndFlush(bankrekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankrekening using partial update
        Bankrekening partialUpdatedBankrekening = new Bankrekening();
        partialUpdatedBankrekening.setId(bankrekening.getId());

        partialUpdatedBankrekening.bank(UPDATED_BANK).nummer(UPDATED_NUMMER).tennaamstelling(UPDATED_TENNAAMSTELLING);

        restBankrekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankrekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankrekening))
            )
            .andExpect(status().isOk());

        // Validate the Bankrekening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankrekeningUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBankrekening, bankrekening),
            getPersistedBankrekening(bankrekening)
        );
    }

    @Test
    @Transactional
    void fullUpdateBankrekeningWithPatch() throws Exception {
        // Initialize the database
        bankrekeningRepository.saveAndFlush(bankrekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankrekening using partial update
        Bankrekening partialUpdatedBankrekening = new Bankrekening();
        partialUpdatedBankrekening.setId(bankrekening.getId());

        partialUpdatedBankrekening.bank(UPDATED_BANK).nummer(UPDATED_NUMMER).tennaamstelling(UPDATED_TENNAAMSTELLING);

        restBankrekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankrekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankrekening))
            )
            .andExpect(status().isOk());

        // Validate the Bankrekening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankrekeningUpdatableFieldsEquals(partialUpdatedBankrekening, getPersistedBankrekening(partialUpdatedBankrekening));
    }

    @Test
    @Transactional
    void patchNonExistingBankrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankrekening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankrekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankrekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankrekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankrekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankrekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankrekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankrekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankrekeningMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bankrekening)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bankrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankrekening() throws Exception {
        // Initialize the database
        bankrekeningRepository.saveAndFlush(bankrekening);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bankrekening
        restBankrekeningMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankrekening.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bankrekeningRepository.count();
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

    protected Bankrekening getPersistedBankrekening(Bankrekening bankrekening) {
        return bankrekeningRepository.findById(bankrekening.getId()).orElseThrow();
    }

    protected void assertPersistedBankrekeningToMatchAllProperties(Bankrekening expectedBankrekening) {
        assertBankrekeningAllPropertiesEquals(expectedBankrekening, getPersistedBankrekening(expectedBankrekening));
    }

    protected void assertPersistedBankrekeningToMatchUpdatableProperties(Bankrekening expectedBankrekening) {
        assertBankrekeningAllUpdatablePropertiesEquals(expectedBankrekening, getPersistedBankrekening(expectedBankrekening));
    }
}
