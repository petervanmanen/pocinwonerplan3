package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ProductgroepAsserts.*;
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
import nl.ritense.demo.domain.Productgroep;
import nl.ritense.demo.repository.ProductgroepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductgroepResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductgroepResourceIT {

    private static final String DEFAULT_BESLISBOOM = "AAAAAAAAAA";
    private static final String UPDATED_BESLISBOOM = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/productgroeps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProductgroepRepository productgroepRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductgroepMockMvc;

    private Productgroep productgroep;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Productgroep createEntity(EntityManager em) {
        Productgroep productgroep = new Productgroep().beslisboom(DEFAULT_BESLISBOOM).code(DEFAULT_CODE).omschrijving(DEFAULT_OMSCHRIJVING);
        return productgroep;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Productgroep createUpdatedEntity(EntityManager em) {
        Productgroep productgroep = new Productgroep().beslisboom(UPDATED_BESLISBOOM).code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING);
        return productgroep;
    }

    @BeforeEach
    public void initTest() {
        productgroep = createEntity(em);
    }

    @Test
    @Transactional
    void createProductgroep() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Productgroep
        var returnedProductgroep = om.readValue(
            restProductgroepMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productgroep)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Productgroep.class
        );

        // Validate the Productgroep in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProductgroepUpdatableFieldsEquals(returnedProductgroep, getPersistedProductgroep(returnedProductgroep));
    }

    @Test
    @Transactional
    void createProductgroepWithExistingId() throws Exception {
        // Create the Productgroep with an existing ID
        productgroep.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductgroepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productgroep)))
            .andExpect(status().isBadRequest());

        // Validate the Productgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductgroeps() throws Exception {
        // Initialize the database
        productgroepRepository.saveAndFlush(productgroep);

        // Get all the productgroepList
        restProductgroepMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productgroep.getId().intValue())))
            .andExpect(jsonPath("$.[*].beslisboom").value(hasItem(DEFAULT_BESLISBOOM)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getProductgroep() throws Exception {
        // Initialize the database
        productgroepRepository.saveAndFlush(productgroep);

        // Get the productgroep
        restProductgroepMockMvc
            .perform(get(ENTITY_API_URL_ID, productgroep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productgroep.getId().intValue()))
            .andExpect(jsonPath("$.beslisboom").value(DEFAULT_BESLISBOOM))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingProductgroep() throws Exception {
        // Get the productgroep
        restProductgroepMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductgroep() throws Exception {
        // Initialize the database
        productgroepRepository.saveAndFlush(productgroep);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productgroep
        Productgroep updatedProductgroep = productgroepRepository.findById(productgroep.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProductgroep are not directly saved in db
        em.detach(updatedProductgroep);
        updatedProductgroep.beslisboom(UPDATED_BESLISBOOM).code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING);

        restProductgroepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductgroep.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProductgroep))
            )
            .andExpect(status().isOk());

        // Validate the Productgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProductgroepToMatchAllProperties(updatedProductgroep);
    }

    @Test
    @Transactional
    void putNonExistingProductgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productgroep.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductgroepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productgroep.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productgroep))
            )
            .andExpect(status().isBadRequest());

        // Validate the Productgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productgroep.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductgroepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productgroep))
            )
            .andExpect(status().isBadRequest());

        // Validate the Productgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productgroep.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductgroepMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productgroep)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Productgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductgroepWithPatch() throws Exception {
        // Initialize the database
        productgroepRepository.saveAndFlush(productgroep);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productgroep using partial update
        Productgroep partialUpdatedProductgroep = new Productgroep();
        partialUpdatedProductgroep.setId(productgroep.getId());

        partialUpdatedProductgroep.code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING);

        restProductgroepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductgroep.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProductgroep))
            )
            .andExpect(status().isOk());

        // Validate the Productgroep in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProductgroepUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProductgroep, productgroep),
            getPersistedProductgroep(productgroep)
        );
    }

    @Test
    @Transactional
    void fullUpdateProductgroepWithPatch() throws Exception {
        // Initialize the database
        productgroepRepository.saveAndFlush(productgroep);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productgroep using partial update
        Productgroep partialUpdatedProductgroep = new Productgroep();
        partialUpdatedProductgroep.setId(productgroep.getId());

        partialUpdatedProductgroep.beslisboom(UPDATED_BESLISBOOM).code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING);

        restProductgroepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductgroep.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProductgroep))
            )
            .andExpect(status().isOk());

        // Validate the Productgroep in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProductgroepUpdatableFieldsEquals(partialUpdatedProductgroep, getPersistedProductgroep(partialUpdatedProductgroep));
    }

    @Test
    @Transactional
    void patchNonExistingProductgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productgroep.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductgroepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productgroep.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(productgroep))
            )
            .andExpect(status().isBadRequest());

        // Validate the Productgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productgroep.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductgroepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(productgroep))
            )
            .andExpect(status().isBadRequest());

        // Validate the Productgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductgroep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productgroep.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductgroepMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(productgroep)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Productgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductgroep() throws Exception {
        // Initialize the database
        productgroepRepository.saveAndFlush(productgroep);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the productgroep
        restProductgroepMockMvc
            .perform(delete(ENTITY_API_URL_ID, productgroep.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return productgroepRepository.count();
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

    protected Productgroep getPersistedProductgroep(Productgroep productgroep) {
        return productgroepRepository.findById(productgroep.getId()).orElseThrow();
    }

    protected void assertPersistedProductgroepToMatchAllProperties(Productgroep expectedProductgroep) {
        assertProductgroepAllPropertiesEquals(expectedProductgroep, getPersistedProductgroep(expectedProductgroep));
    }

    protected void assertPersistedProductgroepToMatchUpdatableProperties(Productgroep expectedProductgroep) {
        assertProductgroepAllUpdatablePropertiesEquals(expectedProductgroep, getPersistedProductgroep(expectedProductgroep));
    }
}
