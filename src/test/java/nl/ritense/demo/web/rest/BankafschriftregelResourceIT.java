package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BankafschriftregelAsserts.*;
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
import nl.ritense.demo.domain.Bankafschriftregel;
import nl.ritense.demo.repository.BankafschriftregelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BankafschriftregelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankafschriftregelResourceIT {

    private static final BigDecimal DEFAULT_BEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAG = new BigDecimal(2);

    private static final Boolean DEFAULT_BIJ = false;
    private static final Boolean UPDATED_BIJ = true;

    private static final String DEFAULT_DATUM = "AAAAAAAAAA";
    private static final String UPDATED_DATUM = "BBBBBBBBBB";

    private static final String DEFAULT_REKENINGVAN = "AAAAAAAAAA";
    private static final String UPDATED_REKENINGVAN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bankafschriftregels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BankafschriftregelRepository bankafschriftregelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankafschriftregelMockMvc;

    private Bankafschriftregel bankafschriftregel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bankafschriftregel createEntity(EntityManager em) {
        Bankafschriftregel bankafschriftregel = new Bankafschriftregel()
            .bedrag(DEFAULT_BEDRAG)
            .bij(DEFAULT_BIJ)
            .datum(DEFAULT_DATUM)
            .rekeningvan(DEFAULT_REKENINGVAN);
        return bankafschriftregel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bankafschriftregel createUpdatedEntity(EntityManager em) {
        Bankafschriftregel bankafschriftregel = new Bankafschriftregel()
            .bedrag(UPDATED_BEDRAG)
            .bij(UPDATED_BIJ)
            .datum(UPDATED_DATUM)
            .rekeningvan(UPDATED_REKENINGVAN);
        return bankafschriftregel;
    }

    @BeforeEach
    public void initTest() {
        bankafschriftregel = createEntity(em);
    }

    @Test
    @Transactional
    void createBankafschriftregel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bankafschriftregel
        var returnedBankafschriftregel = om.readValue(
            restBankafschriftregelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankafschriftregel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bankafschriftregel.class
        );

        // Validate the Bankafschriftregel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBankafschriftregelUpdatableFieldsEquals(
            returnedBankafschriftregel,
            getPersistedBankafschriftregel(returnedBankafschriftregel)
        );
    }

    @Test
    @Transactional
    void createBankafschriftregelWithExistingId() throws Exception {
        // Create the Bankafschriftregel with an existing ID
        bankafschriftregel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankafschriftregelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankafschriftregel)))
            .andExpect(status().isBadRequest());

        // Validate the Bankafschriftregel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankafschriftregels() throws Exception {
        // Initialize the database
        bankafschriftregelRepository.saveAndFlush(bankafschriftregel);

        // Get all the bankafschriftregelList
        restBankafschriftregelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankafschriftregel.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(sameNumber(DEFAULT_BEDRAG))))
            .andExpect(jsonPath("$.[*].bij").value(hasItem(DEFAULT_BIJ.booleanValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM)))
            .andExpect(jsonPath("$.[*].rekeningvan").value(hasItem(DEFAULT_REKENINGVAN)));
    }

    @Test
    @Transactional
    void getBankafschriftregel() throws Exception {
        // Initialize the database
        bankafschriftregelRepository.saveAndFlush(bankafschriftregel);

        // Get the bankafschriftregel
        restBankafschriftregelMockMvc
            .perform(get(ENTITY_API_URL_ID, bankafschriftregel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankafschriftregel.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(sameNumber(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.bij").value(DEFAULT_BIJ.booleanValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM))
            .andExpect(jsonPath("$.rekeningvan").value(DEFAULT_REKENINGVAN));
    }

    @Test
    @Transactional
    void getNonExistingBankafschriftregel() throws Exception {
        // Get the bankafschriftregel
        restBankafschriftregelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBankafschriftregel() throws Exception {
        // Initialize the database
        bankafschriftregelRepository.saveAndFlush(bankafschriftregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankafschriftregel
        Bankafschriftregel updatedBankafschriftregel = bankafschriftregelRepository.findById(bankafschriftregel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBankafschriftregel are not directly saved in db
        em.detach(updatedBankafschriftregel);
        updatedBankafschriftregel.bedrag(UPDATED_BEDRAG).bij(UPDATED_BIJ).datum(UPDATED_DATUM).rekeningvan(UPDATED_REKENINGVAN);

        restBankafschriftregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBankafschriftregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBankafschriftregel))
            )
            .andExpect(status().isOk());

        // Validate the Bankafschriftregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBankafschriftregelToMatchAllProperties(updatedBankafschriftregel);
    }

    @Test
    @Transactional
    void putNonExistingBankafschriftregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankafschriftregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankafschriftregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankafschriftregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankafschriftregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankafschriftregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankafschriftregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankafschriftregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankafschriftregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankafschriftregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankafschriftregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankafschriftregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankafschriftregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankafschriftregelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankafschriftregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bankafschriftregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankafschriftregelWithPatch() throws Exception {
        // Initialize the database
        bankafschriftregelRepository.saveAndFlush(bankafschriftregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankafschriftregel using partial update
        Bankafschriftregel partialUpdatedBankafschriftregel = new Bankafschriftregel();
        partialUpdatedBankafschriftregel.setId(bankafschriftregel.getId());

        partialUpdatedBankafschriftregel.bij(UPDATED_BIJ).datum(UPDATED_DATUM);

        restBankafschriftregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankafschriftregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankafschriftregel))
            )
            .andExpect(status().isOk());

        // Validate the Bankafschriftregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankafschriftregelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBankafschriftregel, bankafschriftregel),
            getPersistedBankafschriftregel(bankafschriftregel)
        );
    }

    @Test
    @Transactional
    void fullUpdateBankafschriftregelWithPatch() throws Exception {
        // Initialize the database
        bankafschriftregelRepository.saveAndFlush(bankafschriftregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankafschriftregel using partial update
        Bankafschriftregel partialUpdatedBankafschriftregel = new Bankafschriftregel();
        partialUpdatedBankafschriftregel.setId(bankafschriftregel.getId());

        partialUpdatedBankafschriftregel.bedrag(UPDATED_BEDRAG).bij(UPDATED_BIJ).datum(UPDATED_DATUM).rekeningvan(UPDATED_REKENINGVAN);

        restBankafschriftregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankafschriftregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankafschriftregel))
            )
            .andExpect(status().isOk());

        // Validate the Bankafschriftregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankafschriftregelUpdatableFieldsEquals(
            partialUpdatedBankafschriftregel,
            getPersistedBankafschriftregel(partialUpdatedBankafschriftregel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBankafschriftregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankafschriftregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankafschriftregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankafschriftregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankafschriftregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankafschriftregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankafschriftregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankafschriftregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankafschriftregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankafschriftregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankafschriftregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankafschriftregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankafschriftregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankafschriftregelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bankafschriftregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bankafschriftregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankafschriftregel() throws Exception {
        // Initialize the database
        bankafschriftregelRepository.saveAndFlush(bankafschriftregel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bankafschriftregel
        restBankafschriftregelMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankafschriftregel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bankafschriftregelRepository.count();
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

    protected Bankafschriftregel getPersistedBankafschriftregel(Bankafschriftregel bankafschriftregel) {
        return bankafschriftregelRepository.findById(bankafschriftregel.getId()).orElseThrow();
    }

    protected void assertPersistedBankafschriftregelToMatchAllProperties(Bankafschriftregel expectedBankafschriftregel) {
        assertBankafschriftregelAllPropertiesEquals(expectedBankafschriftregel, getPersistedBankafschriftregel(expectedBankafschriftregel));
    }

    protected void assertPersistedBankafschriftregelToMatchUpdatableProperties(Bankafschriftregel expectedBankafschriftregel) {
        assertBankafschriftregelAllUpdatablePropertiesEquals(
            expectedBankafschriftregel,
            getPersistedBankafschriftregel(expectedBankafschriftregel)
        );
    }
}
