package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ProductieeenheidAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Productieeenheid;
import nl.ritense.demo.repository.ProductieeenheidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductieeenheidResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductieeenheidResourceIT {

    private static final String ENTITY_API_URL = "/api/productieeenheids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProductieeenheidRepository productieeenheidRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductieeenheidMockMvc;

    private Productieeenheid productieeenheid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Productieeenheid createEntity(EntityManager em) {
        Productieeenheid productieeenheid = new Productieeenheid();
        return productieeenheid;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Productieeenheid createUpdatedEntity(EntityManager em) {
        Productieeenheid productieeenheid = new Productieeenheid();
        return productieeenheid;
    }

    @BeforeEach
    public void initTest() {
        productieeenheid = createEntity(em);
    }

    @Test
    @Transactional
    void createProductieeenheid() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Productieeenheid
        var returnedProductieeenheid = om.readValue(
            restProductieeenheidMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productieeenheid)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Productieeenheid.class
        );

        // Validate the Productieeenheid in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProductieeenheidUpdatableFieldsEquals(returnedProductieeenheid, getPersistedProductieeenheid(returnedProductieeenheid));
    }

    @Test
    @Transactional
    void createProductieeenheidWithExistingId() throws Exception {
        // Create the Productieeenheid with an existing ID
        productieeenheid.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductieeenheidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productieeenheid)))
            .andExpect(status().isBadRequest());

        // Validate the Productieeenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductieeenheids() throws Exception {
        // Initialize the database
        productieeenheidRepository.saveAndFlush(productieeenheid);

        // Get all the productieeenheidList
        restProductieeenheidMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productieeenheid.getId().intValue())));
    }

    @Test
    @Transactional
    void getProductieeenheid() throws Exception {
        // Initialize the database
        productieeenheidRepository.saveAndFlush(productieeenheid);

        // Get the productieeenheid
        restProductieeenheidMockMvc
            .perform(get(ENTITY_API_URL_ID, productieeenheid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productieeenheid.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingProductieeenheid() throws Exception {
        // Get the productieeenheid
        restProductieeenheidMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteProductieeenheid() throws Exception {
        // Initialize the database
        productieeenheidRepository.saveAndFlush(productieeenheid);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the productieeenheid
        restProductieeenheidMockMvc
            .perform(delete(ENTITY_API_URL_ID, productieeenheid.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return productieeenheidRepository.count();
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

    protected Productieeenheid getPersistedProductieeenheid(Productieeenheid productieeenheid) {
        return productieeenheidRepository.findById(productieeenheid.getId()).orElseThrow();
    }

    protected void assertPersistedProductieeenheidToMatchAllProperties(Productieeenheid expectedProductieeenheid) {
        assertProductieeenheidAllPropertiesEquals(expectedProductieeenheid, getPersistedProductieeenheid(expectedProductieeenheid));
    }

    protected void assertPersistedProductieeenheidToMatchUpdatableProperties(Productieeenheid expectedProductieeenheid) {
        assertProductieeenheidAllUpdatablePropertiesEquals(
            expectedProductieeenheid,
            getPersistedProductieeenheid(expectedProductieeenheid)
        );
    }
}
