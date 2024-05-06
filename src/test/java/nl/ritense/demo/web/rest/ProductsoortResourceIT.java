package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ProductsoortAsserts.*;
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
import nl.ritense.demo.domain.Productsoort;
import nl.ritense.demo.repository.ProductsoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductsoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductsoortResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TARIEF = new BigDecimal(1);
    private static final BigDecimal UPDATED_TARIEF = new BigDecimal(2);

    private static final String DEFAULT_TARIEFPERIODE = "AAAAAAAAAA";
    private static final String UPDATED_TARIEFPERIODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/productsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProductsoortRepository productsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductsoortMockMvc;

    private Productsoort productsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Productsoort createEntity(EntityManager em) {
        Productsoort productsoort = new Productsoort()
            .code(DEFAULT_CODE)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .tarief(DEFAULT_TARIEF)
            .tariefperiode(DEFAULT_TARIEFPERIODE);
        return productsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Productsoort createUpdatedEntity(EntityManager em) {
        Productsoort productsoort = new Productsoort()
            .code(UPDATED_CODE)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .tarief(UPDATED_TARIEF)
            .tariefperiode(UPDATED_TARIEFPERIODE);
        return productsoort;
    }

    @BeforeEach
    public void initTest() {
        productsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createProductsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Productsoort
        var returnedProductsoort = om.readValue(
            restProductsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Productsoort.class
        );

        // Validate the Productsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProductsoortUpdatableFieldsEquals(returnedProductsoort, getPersistedProductsoort(returnedProductsoort));
    }

    @Test
    @Transactional
    void createProductsoortWithExistingId() throws Exception {
        // Create the Productsoort with an existing ID
        productsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Productsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductsoorts() throws Exception {
        // Initialize the database
        productsoortRepository.saveAndFlush(productsoort);

        // Get all the productsoortList
        restProductsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productsoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].tarief").value(hasItem(sameNumber(DEFAULT_TARIEF))))
            .andExpect(jsonPath("$.[*].tariefperiode").value(hasItem(DEFAULT_TARIEFPERIODE)));
    }

    @Test
    @Transactional
    void getProductsoort() throws Exception {
        // Initialize the database
        productsoortRepository.saveAndFlush(productsoort);

        // Get the productsoort
        restProductsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, productsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productsoort.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.tarief").value(sameNumber(DEFAULT_TARIEF)))
            .andExpect(jsonPath("$.tariefperiode").value(DEFAULT_TARIEFPERIODE));
    }

    @Test
    @Transactional
    void getNonExistingProductsoort() throws Exception {
        // Get the productsoort
        restProductsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductsoort() throws Exception {
        // Initialize the database
        productsoortRepository.saveAndFlush(productsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productsoort
        Productsoort updatedProductsoort = productsoortRepository.findById(productsoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProductsoort are not directly saved in db
        em.detach(updatedProductsoort);
        updatedProductsoort
            .code(UPDATED_CODE)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .tarief(UPDATED_TARIEF)
            .tariefperiode(UPDATED_TARIEFPERIODE);

        restProductsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProductsoort))
            )
            .andExpect(status().isOk());

        // Validate the Productsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProductsoortToMatchAllProperties(updatedProductsoort);
    }

    @Test
    @Transactional
    void putNonExistingProductsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Productsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Productsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Productsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductsoortWithPatch() throws Exception {
        // Initialize the database
        productsoortRepository.saveAndFlush(productsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productsoort using partial update
        Productsoort partialUpdatedProductsoort = new Productsoort();
        partialUpdatedProductsoort.setId(productsoort.getId());

        partialUpdatedProductsoort.tarief(UPDATED_TARIEF).tariefperiode(UPDATED_TARIEFPERIODE);

        restProductsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProductsoort))
            )
            .andExpect(status().isOk());

        // Validate the Productsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProductsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProductsoort, productsoort),
            getPersistedProductsoort(productsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateProductsoortWithPatch() throws Exception {
        // Initialize the database
        productsoortRepository.saveAndFlush(productsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productsoort using partial update
        Productsoort partialUpdatedProductsoort = new Productsoort();
        partialUpdatedProductsoort.setId(productsoort.getId());

        partialUpdatedProductsoort
            .code(UPDATED_CODE)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .tarief(UPDATED_TARIEF)
            .tariefperiode(UPDATED_TARIEFPERIODE);

        restProductsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProductsoort))
            )
            .andExpect(status().isOk());

        // Validate the Productsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProductsoortUpdatableFieldsEquals(partialUpdatedProductsoort, getPersistedProductsoort(partialUpdatedProductsoort));
    }

    @Test
    @Transactional
    void patchNonExistingProductsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(productsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Productsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(productsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Productsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductsoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(productsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Productsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductsoort() throws Exception {
        // Initialize the database
        productsoortRepository.saveAndFlush(productsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the productsoort
        restProductsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, productsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return productsoortRepository.count();
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

    protected Productsoort getPersistedProductsoort(Productsoort productsoort) {
        return productsoortRepository.findById(productsoort.getId()).orElseThrow();
    }

    protected void assertPersistedProductsoortToMatchAllProperties(Productsoort expectedProductsoort) {
        assertProductsoortAllPropertiesEquals(expectedProductsoort, getPersistedProductsoort(expectedProductsoort));
    }

    protected void assertPersistedProductsoortToMatchUpdatableProperties(Productsoort expectedProductsoort) {
        assertProductsoortAllUpdatablePropertiesEquals(expectedProductsoort, getPersistedProductsoort(expectedProductsoort));
    }
}
