package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BankafschriftAsserts.*;
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
import nl.ritense.demo.domain.Bankafschrift;
import nl.ritense.demo.repository.BankafschriftRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BankafschriftResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankafschriftResourceIT {

    private static final String DEFAULT_DATUM = "AAAAAAAAAA";
    private static final String UPDATED_DATUM = "BBBBBBBBBB";

    private static final String DEFAULT_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bankafschrifts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BankafschriftRepository bankafschriftRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankafschriftMockMvc;

    private Bankafschrift bankafschrift;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bankafschrift createEntity(EntityManager em) {
        Bankafschrift bankafschrift = new Bankafschrift().datum(DEFAULT_DATUM).nummer(DEFAULT_NUMMER);
        return bankafschrift;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bankafschrift createUpdatedEntity(EntityManager em) {
        Bankafschrift bankafschrift = new Bankafschrift().datum(UPDATED_DATUM).nummer(UPDATED_NUMMER);
        return bankafschrift;
    }

    @BeforeEach
    public void initTest() {
        bankafschrift = createEntity(em);
    }

    @Test
    @Transactional
    void createBankafschrift() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bankafschrift
        var returnedBankafschrift = om.readValue(
            restBankafschriftMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankafschrift)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bankafschrift.class
        );

        // Validate the Bankafschrift in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBankafschriftUpdatableFieldsEquals(returnedBankafschrift, getPersistedBankafschrift(returnedBankafschrift));
    }

    @Test
    @Transactional
    void createBankafschriftWithExistingId() throws Exception {
        // Create the Bankafschrift with an existing ID
        bankafschrift.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankafschriftMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankafschrift)))
            .andExpect(status().isBadRequest());

        // Validate the Bankafschrift in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankafschrifts() throws Exception {
        // Initialize the database
        bankafschriftRepository.saveAndFlush(bankafschrift);

        // Get all the bankafschriftList
        restBankafschriftMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankafschrift.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM)))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)));
    }

    @Test
    @Transactional
    void getBankafschrift() throws Exception {
        // Initialize the database
        bankafschriftRepository.saveAndFlush(bankafschrift);

        // Get the bankafschrift
        restBankafschriftMockMvc
            .perform(get(ENTITY_API_URL_ID, bankafschrift.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankafschrift.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER));
    }

    @Test
    @Transactional
    void getNonExistingBankafschrift() throws Exception {
        // Get the bankafschrift
        restBankafschriftMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBankafschrift() throws Exception {
        // Initialize the database
        bankafschriftRepository.saveAndFlush(bankafschrift);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankafschrift
        Bankafschrift updatedBankafschrift = bankafschriftRepository.findById(bankafschrift.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBankafschrift are not directly saved in db
        em.detach(updatedBankafschrift);
        updatedBankafschrift.datum(UPDATED_DATUM).nummer(UPDATED_NUMMER);

        restBankafschriftMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBankafschrift.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBankafschrift))
            )
            .andExpect(status().isOk());

        // Validate the Bankafschrift in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBankafschriftToMatchAllProperties(updatedBankafschrift);
    }

    @Test
    @Transactional
    void putNonExistingBankafschrift() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankafschrift.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankafschriftMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankafschrift.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankafschrift))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankafschrift in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankafschrift() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankafschrift.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankafschriftMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankafschrift))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankafschrift in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankafschrift() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankafschrift.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankafschriftMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankafschrift)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bankafschrift in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankafschriftWithPatch() throws Exception {
        // Initialize the database
        bankafschriftRepository.saveAndFlush(bankafschrift);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankafschrift using partial update
        Bankafschrift partialUpdatedBankafschrift = new Bankafschrift();
        partialUpdatedBankafschrift.setId(bankafschrift.getId());

        partialUpdatedBankafschrift.nummer(UPDATED_NUMMER);

        restBankafschriftMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankafschrift.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankafschrift))
            )
            .andExpect(status().isOk());

        // Validate the Bankafschrift in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankafschriftUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBankafschrift, bankafschrift),
            getPersistedBankafschrift(bankafschrift)
        );
    }

    @Test
    @Transactional
    void fullUpdateBankafschriftWithPatch() throws Exception {
        // Initialize the database
        bankafschriftRepository.saveAndFlush(bankafschrift);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankafschrift using partial update
        Bankafschrift partialUpdatedBankafschrift = new Bankafschrift();
        partialUpdatedBankafschrift.setId(bankafschrift.getId());

        partialUpdatedBankafschrift.datum(UPDATED_DATUM).nummer(UPDATED_NUMMER);

        restBankafschriftMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankafschrift.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankafschrift))
            )
            .andExpect(status().isOk());

        // Validate the Bankafschrift in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankafschriftUpdatableFieldsEquals(partialUpdatedBankafschrift, getPersistedBankafschrift(partialUpdatedBankafschrift));
    }

    @Test
    @Transactional
    void patchNonExistingBankafschrift() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankafschrift.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankafschriftMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankafschrift.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankafschrift))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankafschrift in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankafschrift() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankafschrift.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankafschriftMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankafschrift))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankafschrift in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankafschrift() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankafschrift.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankafschriftMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bankafschrift)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bankafschrift in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankafschrift() throws Exception {
        // Initialize the database
        bankafschriftRepository.saveAndFlush(bankafschrift);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bankafschrift
        restBankafschriftMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankafschrift.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bankafschriftRepository.count();
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

    protected Bankafschrift getPersistedBankafschrift(Bankafschrift bankafschrift) {
        return bankafschriftRepository.findById(bankafschrift.getId()).orElseThrow();
    }

    protected void assertPersistedBankafschriftToMatchAllProperties(Bankafschrift expectedBankafschrift) {
        assertBankafschriftAllPropertiesEquals(expectedBankafschrift, getPersistedBankafschrift(expectedBankafschrift));
    }

    protected void assertPersistedBankafschriftToMatchUpdatableProperties(Bankafschrift expectedBankafschrift) {
        assertBankafschriftAllUpdatablePropertiesEquals(expectedBankafschrift, getPersistedBankafschrift(expectedBankafschrift));
    }
}
