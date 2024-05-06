package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ProductofdienstAsserts.*;
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
import nl.ritense.demo.domain.Productofdienst;
import nl.ritense.demo.repository.ProductofdienstRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductofdienstResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductofdienstResourceIT {

    private static final String DEFAULT_AFHANDELTIJD = "AAAAAAAAAA";
    private static final String UPDATED_AFHANDELTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_INGEBRUIK = "AAAAAAAAAA";
    private static final String UPDATED_INGEBRUIK = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/productofdiensts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProductofdienstRepository productofdienstRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductofdienstMockMvc;

    private Productofdienst productofdienst;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Productofdienst createEntity(EntityManager em) {
        Productofdienst productofdienst = new Productofdienst()
            .afhandeltijd(DEFAULT_AFHANDELTIJD)
            .ingebruik(DEFAULT_INGEBRUIK)
            .naam(DEFAULT_NAAM);
        return productofdienst;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Productofdienst createUpdatedEntity(EntityManager em) {
        Productofdienst productofdienst = new Productofdienst()
            .afhandeltijd(UPDATED_AFHANDELTIJD)
            .ingebruik(UPDATED_INGEBRUIK)
            .naam(UPDATED_NAAM);
        return productofdienst;
    }

    @BeforeEach
    public void initTest() {
        productofdienst = createEntity(em);
    }

    @Test
    @Transactional
    void createProductofdienst() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Productofdienst
        var returnedProductofdienst = om.readValue(
            restProductofdienstMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productofdienst)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Productofdienst.class
        );

        // Validate the Productofdienst in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProductofdienstUpdatableFieldsEquals(returnedProductofdienst, getPersistedProductofdienst(returnedProductofdienst));
    }

    @Test
    @Transactional
    void createProductofdienstWithExistingId() throws Exception {
        // Create the Productofdienst with an existing ID
        productofdienst.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductofdienstMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productofdienst)))
            .andExpect(status().isBadRequest());

        // Validate the Productofdienst in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductofdiensts() throws Exception {
        // Initialize the database
        productofdienstRepository.saveAndFlush(productofdienst);

        // Get all the productofdienstList
        restProductofdienstMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productofdienst.getId().intValue())))
            .andExpect(jsonPath("$.[*].afhandeltijd").value(hasItem(DEFAULT_AFHANDELTIJD)))
            .andExpect(jsonPath("$.[*].ingebruik").value(hasItem(DEFAULT_INGEBRUIK)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getProductofdienst() throws Exception {
        // Initialize the database
        productofdienstRepository.saveAndFlush(productofdienst);

        // Get the productofdienst
        restProductofdienstMockMvc
            .perform(get(ENTITY_API_URL_ID, productofdienst.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productofdienst.getId().intValue()))
            .andExpect(jsonPath("$.afhandeltijd").value(DEFAULT_AFHANDELTIJD))
            .andExpect(jsonPath("$.ingebruik").value(DEFAULT_INGEBRUIK))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingProductofdienst() throws Exception {
        // Get the productofdienst
        restProductofdienstMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductofdienst() throws Exception {
        // Initialize the database
        productofdienstRepository.saveAndFlush(productofdienst);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productofdienst
        Productofdienst updatedProductofdienst = productofdienstRepository.findById(productofdienst.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProductofdienst are not directly saved in db
        em.detach(updatedProductofdienst);
        updatedProductofdienst.afhandeltijd(UPDATED_AFHANDELTIJD).ingebruik(UPDATED_INGEBRUIK).naam(UPDATED_NAAM);

        restProductofdienstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductofdienst.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProductofdienst))
            )
            .andExpect(status().isOk());

        // Validate the Productofdienst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProductofdienstToMatchAllProperties(updatedProductofdienst);
    }

    @Test
    @Transactional
    void putNonExistingProductofdienst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productofdienst.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductofdienstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productofdienst.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productofdienst))
            )
            .andExpect(status().isBadRequest());

        // Validate the Productofdienst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductofdienst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productofdienst.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductofdienstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productofdienst))
            )
            .andExpect(status().isBadRequest());

        // Validate the Productofdienst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductofdienst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productofdienst.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductofdienstMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productofdienst)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Productofdienst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductofdienstWithPatch() throws Exception {
        // Initialize the database
        productofdienstRepository.saveAndFlush(productofdienst);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productofdienst using partial update
        Productofdienst partialUpdatedProductofdienst = new Productofdienst();
        partialUpdatedProductofdienst.setId(productofdienst.getId());

        partialUpdatedProductofdienst.ingebruik(UPDATED_INGEBRUIK);

        restProductofdienstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductofdienst.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProductofdienst))
            )
            .andExpect(status().isOk());

        // Validate the Productofdienst in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProductofdienstUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProductofdienst, productofdienst),
            getPersistedProductofdienst(productofdienst)
        );
    }

    @Test
    @Transactional
    void fullUpdateProductofdienstWithPatch() throws Exception {
        // Initialize the database
        productofdienstRepository.saveAndFlush(productofdienst);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productofdienst using partial update
        Productofdienst partialUpdatedProductofdienst = new Productofdienst();
        partialUpdatedProductofdienst.setId(productofdienst.getId());

        partialUpdatedProductofdienst.afhandeltijd(UPDATED_AFHANDELTIJD).ingebruik(UPDATED_INGEBRUIK).naam(UPDATED_NAAM);

        restProductofdienstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductofdienst.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProductofdienst))
            )
            .andExpect(status().isOk());

        // Validate the Productofdienst in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProductofdienstUpdatableFieldsEquals(
            partialUpdatedProductofdienst,
            getPersistedProductofdienst(partialUpdatedProductofdienst)
        );
    }

    @Test
    @Transactional
    void patchNonExistingProductofdienst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productofdienst.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductofdienstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productofdienst.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(productofdienst))
            )
            .andExpect(status().isBadRequest());

        // Validate the Productofdienst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductofdienst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productofdienst.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductofdienstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(productofdienst))
            )
            .andExpect(status().isBadRequest());

        // Validate the Productofdienst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductofdienst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productofdienst.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductofdienstMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(productofdienst)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Productofdienst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductofdienst() throws Exception {
        // Initialize the database
        productofdienstRepository.saveAndFlush(productofdienst);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the productofdienst
        restProductofdienstMockMvc
            .perform(delete(ENTITY_API_URL_ID, productofdienst.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return productofdienstRepository.count();
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

    protected Productofdienst getPersistedProductofdienst(Productofdienst productofdienst) {
        return productofdienstRepository.findById(productofdienst.getId()).orElseThrow();
    }

    protected void assertPersistedProductofdienstToMatchAllProperties(Productofdienst expectedProductofdienst) {
        assertProductofdienstAllPropertiesEquals(expectedProductofdienst, getPersistedProductofdienst(expectedProductofdienst));
    }

    protected void assertPersistedProductofdienstToMatchUpdatableProperties(Productofdienst expectedProductofdienst) {
        assertProductofdienstAllUpdatablePropertiesEquals(expectedProductofdienst, getPersistedProductofdienst(expectedProductofdienst));
    }
}
