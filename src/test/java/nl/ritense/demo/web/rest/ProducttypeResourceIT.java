package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ProducttypeAsserts.*;
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
import nl.ritense.demo.domain.Producttype;
import nl.ritense.demo.repository.ProducttypeRepository;
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
 * Integration tests for the {@link ProducttypeResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class ProducttypeResourceIT {

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/producttypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProducttypeRepository producttypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProducttypeMockMvc;

    private Producttype producttype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producttype createEntity(EntityManager em) {
        Producttype producttype = new Producttype().omschrijving(DEFAULT_OMSCHRIJVING);
        return producttype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producttype createUpdatedEntity(EntityManager em) {
        Producttype producttype = new Producttype().omschrijving(UPDATED_OMSCHRIJVING);
        return producttype;
    }

    @BeforeEach
    public void initTest() {
        producttype = createEntity(em);
    }

    @Test
    @Transactional
    void createProducttype() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Producttype
        var returnedProducttype = om.readValue(
            restProducttypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(producttype)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Producttype.class
        );

        // Validate the Producttype in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProducttypeUpdatableFieldsEquals(returnedProducttype, getPersistedProducttype(returnedProducttype));
    }

    @Test
    @Transactional
    void createProducttypeWithExistingId() throws Exception {
        // Create the Producttype with an existing ID
        producttype.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProducttypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(producttype)))
            .andExpect(status().isBadRequest());

        // Validate the Producttype in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProducttypes() throws Exception {
        // Initialize the database
        producttypeRepository.saveAndFlush(producttype);

        // Get all the producttypeList
        restProducttypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producttype.getId().intValue())))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getProducttype() throws Exception {
        // Initialize the database
        producttypeRepository.saveAndFlush(producttype);

        // Get the producttype
        restProducttypeMockMvc
            .perform(get(ENTITY_API_URL_ID, producttype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(producttype.getId().intValue()))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingProducttype() throws Exception {
        // Get the producttype
        restProducttypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProducttype() throws Exception {
        // Initialize the database
        producttypeRepository.saveAndFlush(producttype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the producttype
        Producttype updatedProducttype = producttypeRepository.findById(producttype.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProducttype are not directly saved in db
        em.detach(updatedProducttype);
        updatedProducttype.omschrijving(UPDATED_OMSCHRIJVING);

        restProducttypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProducttype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProducttype))
            )
            .andExpect(status().isOk());

        // Validate the Producttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProducttypeToMatchAllProperties(updatedProducttype);
    }

    @Test
    @Transactional
    void putNonExistingProducttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        producttype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProducttypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, producttype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(producttype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProducttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        producttype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProducttypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(producttype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProducttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        producttype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProducttypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(producttype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Producttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProducttypeWithPatch() throws Exception {
        // Initialize the database
        producttypeRepository.saveAndFlush(producttype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the producttype using partial update
        Producttype partialUpdatedProducttype = new Producttype();
        partialUpdatedProducttype.setId(producttype.getId());

        partialUpdatedProducttype.omschrijving(UPDATED_OMSCHRIJVING);

        restProducttypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProducttype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProducttype))
            )
            .andExpect(status().isOk());

        // Validate the Producttype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProducttypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProducttype, producttype),
            getPersistedProducttype(producttype)
        );
    }

    @Test
    @Transactional
    void fullUpdateProducttypeWithPatch() throws Exception {
        // Initialize the database
        producttypeRepository.saveAndFlush(producttype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the producttype using partial update
        Producttype partialUpdatedProducttype = new Producttype();
        partialUpdatedProducttype.setId(producttype.getId());

        partialUpdatedProducttype.omschrijving(UPDATED_OMSCHRIJVING);

        restProducttypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProducttype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProducttype))
            )
            .andExpect(status().isOk());

        // Validate the Producttype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProducttypeUpdatableFieldsEquals(partialUpdatedProducttype, getPersistedProducttype(partialUpdatedProducttype));
    }

    @Test
    @Transactional
    void patchNonExistingProducttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        producttype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProducttypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, producttype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(producttype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProducttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        producttype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProducttypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(producttype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProducttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        producttype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProducttypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(producttype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Producttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProducttype() throws Exception {
        // Initialize the database
        producttypeRepository.saveAndFlush(producttype);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the producttype
        restProducttypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, producttype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return producttypeRepository.count();
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

    protected Producttype getPersistedProducttype(Producttype producttype) {
        return producttypeRepository.findById(producttype.getId()).orElseThrow();
    }

    protected void assertPersistedProducttypeToMatchAllProperties(Producttype expectedProducttype) {
        assertProducttypeAllPropertiesEquals(expectedProducttype, getPersistedProducttype(expectedProducttype));
    }

    protected void assertPersistedProducttypeToMatchUpdatableProperties(Producttype expectedProducttype) {
        assertProducttypeAllUpdatablePropertiesEquals(expectedProducttype, getPersistedProducttype(expectedProducttype));
    }
}
