package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FactuurAsserts.*;
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
import nl.ritense.demo.domain.Factuur;
import nl.ritense.demo.repository.FactuurRepository;
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
 * Integration tests for the {@link FactuurResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class FactuurResourceIT {

    private static final String DEFAULT_BETAALBAARPER = "AAAAAAAAAA";
    private static final String UPDATED_BETAALBAARPER = "BBBBBBBBBB";

    private static final String DEFAULT_BETAALTERMIJN = "AAAAAAAAAA";
    private static final String UPDATED_BETAALTERMIJN = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMFACTUUR = "AAAAAAAAAA";
    private static final String UPDATED_DATUMFACTUUR = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_FACTUURBEDRAGBTW = new BigDecimal(1);
    private static final BigDecimal UPDATED_FACTUURBEDRAGBTW = new BigDecimal(2);

    private static final String DEFAULT_FACTUURBEDRAGEXCLUSIEFBTW = "AAAAAAAAAA";
    private static final String UPDATED_FACTUURBEDRAGEXCLUSIEFBTW = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/factuurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FactuurRepository factuurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFactuurMockMvc;

    private Factuur factuur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Factuur createEntity(EntityManager em) {
        Factuur factuur = new Factuur()
            .betaalbaarper(DEFAULT_BETAALBAARPER)
            .betaaltermijn(DEFAULT_BETAALTERMIJN)
            .code(DEFAULT_CODE)
            .datumfactuur(DEFAULT_DATUMFACTUUR)
            .factuurbedragbtw(DEFAULT_FACTUURBEDRAGBTW)
            .factuurbedragexclusiefbtw(DEFAULT_FACTUURBEDRAGEXCLUSIEFBTW)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return factuur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Factuur createUpdatedEntity(EntityManager em) {
        Factuur factuur = new Factuur()
            .betaalbaarper(UPDATED_BETAALBAARPER)
            .betaaltermijn(UPDATED_BETAALTERMIJN)
            .code(UPDATED_CODE)
            .datumfactuur(UPDATED_DATUMFACTUUR)
            .factuurbedragbtw(UPDATED_FACTUURBEDRAGBTW)
            .factuurbedragexclusiefbtw(UPDATED_FACTUURBEDRAGEXCLUSIEFBTW)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return factuur;
    }

    @BeforeEach
    public void initTest() {
        factuur = createEntity(em);
    }

    @Test
    @Transactional
    void createFactuur() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Factuur
        var returnedFactuur = om.readValue(
            restFactuurMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(factuur)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Factuur.class
        );

        // Validate the Factuur in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFactuurUpdatableFieldsEquals(returnedFactuur, getPersistedFactuur(returnedFactuur));
    }

    @Test
    @Transactional
    void createFactuurWithExistingId() throws Exception {
        // Create the Factuur with an existing ID
        factuur.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactuurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(factuur)))
            .andExpect(status().isBadRequest());

        // Validate the Factuur in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFactuurs() throws Exception {
        // Initialize the database
        factuurRepository.saveAndFlush(factuur);

        // Get all the factuurList
        restFactuurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factuur.getId().intValue())))
            .andExpect(jsonPath("$.[*].betaalbaarper").value(hasItem(DEFAULT_BETAALBAARPER)))
            .andExpect(jsonPath("$.[*].betaaltermijn").value(hasItem(DEFAULT_BETAALTERMIJN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].datumfactuur").value(hasItem(DEFAULT_DATUMFACTUUR)))
            .andExpect(jsonPath("$.[*].factuurbedragbtw").value(hasItem(sameNumber(DEFAULT_FACTUURBEDRAGBTW))))
            .andExpect(jsonPath("$.[*].factuurbedragexclusiefbtw").value(hasItem(DEFAULT_FACTUURBEDRAGEXCLUSIEFBTW)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getFactuur() throws Exception {
        // Initialize the database
        factuurRepository.saveAndFlush(factuur);

        // Get the factuur
        restFactuurMockMvc
            .perform(get(ENTITY_API_URL_ID, factuur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(factuur.getId().intValue()))
            .andExpect(jsonPath("$.betaalbaarper").value(DEFAULT_BETAALBAARPER))
            .andExpect(jsonPath("$.betaaltermijn").value(DEFAULT_BETAALTERMIJN))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.datumfactuur").value(DEFAULT_DATUMFACTUUR))
            .andExpect(jsonPath("$.factuurbedragbtw").value(sameNumber(DEFAULT_FACTUURBEDRAGBTW)))
            .andExpect(jsonPath("$.factuurbedragexclusiefbtw").value(DEFAULT_FACTUURBEDRAGEXCLUSIEFBTW))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingFactuur() throws Exception {
        // Get the factuur
        restFactuurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFactuur() throws Exception {
        // Initialize the database
        factuurRepository.saveAndFlush(factuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the factuur
        Factuur updatedFactuur = factuurRepository.findById(factuur.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFactuur are not directly saved in db
        em.detach(updatedFactuur);
        updatedFactuur
            .betaalbaarper(UPDATED_BETAALBAARPER)
            .betaaltermijn(UPDATED_BETAALTERMIJN)
            .code(UPDATED_CODE)
            .datumfactuur(UPDATED_DATUMFACTUUR)
            .factuurbedragbtw(UPDATED_FACTUURBEDRAGBTW)
            .factuurbedragexclusiefbtw(UPDATED_FACTUURBEDRAGEXCLUSIEFBTW)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restFactuurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFactuur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFactuur))
            )
            .andExpect(status().isOk());

        // Validate the Factuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFactuurToMatchAllProperties(updatedFactuur);
    }

    @Test
    @Transactional
    void putNonExistingFactuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factuur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactuurMockMvc
            .perform(put(ENTITY_API_URL_ID, factuur.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(factuur)))
            .andExpect(status().isBadRequest());

        // Validate the Factuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFactuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactuurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(factuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Factuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFactuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactuurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(factuur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Factuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFactuurWithPatch() throws Exception {
        // Initialize the database
        factuurRepository.saveAndFlush(factuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the factuur using partial update
        Factuur partialUpdatedFactuur = new Factuur();
        partialUpdatedFactuur.setId(factuur.getId());

        partialUpdatedFactuur
            .betaalbaarper(UPDATED_BETAALBAARPER)
            .code(UPDATED_CODE)
            .factuurbedragbtw(UPDATED_FACTUURBEDRAGBTW)
            .factuurbedragexclusiefbtw(UPDATED_FACTUURBEDRAGEXCLUSIEFBTW);

        restFactuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactuur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFactuur))
            )
            .andExpect(status().isOk());

        // Validate the Factuur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFactuurUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedFactuur, factuur), getPersistedFactuur(factuur));
    }

    @Test
    @Transactional
    void fullUpdateFactuurWithPatch() throws Exception {
        // Initialize the database
        factuurRepository.saveAndFlush(factuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the factuur using partial update
        Factuur partialUpdatedFactuur = new Factuur();
        partialUpdatedFactuur.setId(factuur.getId());

        partialUpdatedFactuur
            .betaalbaarper(UPDATED_BETAALBAARPER)
            .betaaltermijn(UPDATED_BETAALTERMIJN)
            .code(UPDATED_CODE)
            .datumfactuur(UPDATED_DATUMFACTUUR)
            .factuurbedragbtw(UPDATED_FACTUURBEDRAGBTW)
            .factuurbedragexclusiefbtw(UPDATED_FACTUURBEDRAGEXCLUSIEFBTW)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restFactuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactuur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFactuur))
            )
            .andExpect(status().isOk());

        // Validate the Factuur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFactuurUpdatableFieldsEquals(partialUpdatedFactuur, getPersistedFactuur(partialUpdatedFactuur));
    }

    @Test
    @Transactional
    void patchNonExistingFactuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factuur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, factuur.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(factuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Factuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFactuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(factuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Factuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFactuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactuurMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(factuur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Factuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFactuur() throws Exception {
        // Initialize the database
        factuurRepository.saveAndFlush(factuur);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the factuur
        restFactuurMockMvc
            .perform(delete(ENTITY_API_URL_ID, factuur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return factuurRepository.count();
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

    protected Factuur getPersistedFactuur(Factuur factuur) {
        return factuurRepository.findById(factuur.getId()).orElseThrow();
    }

    protected void assertPersistedFactuurToMatchAllProperties(Factuur expectedFactuur) {
        assertFactuurAllPropertiesEquals(expectedFactuur, getPersistedFactuur(expectedFactuur));
    }

    protected void assertPersistedFactuurToMatchUpdatableProperties(Factuur expectedFactuur) {
        assertFactuurAllUpdatablePropertiesEquals(expectedFactuur, getPersistedFactuur(expectedFactuur));
    }
}
