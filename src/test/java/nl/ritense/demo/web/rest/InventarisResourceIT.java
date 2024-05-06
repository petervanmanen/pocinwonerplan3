package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.InventarisAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Inventaris;
import nl.ritense.demo.repository.InventarisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InventarisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InventarisResourceIT {

    private static final String ENTITY_API_URL = "/api/inventarises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InventarisRepository inventarisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventarisMockMvc;

    private Inventaris inventaris;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventaris createEntity(EntityManager em) {
        Inventaris inventaris = new Inventaris();
        return inventaris;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventaris createUpdatedEntity(EntityManager em) {
        Inventaris inventaris = new Inventaris();
        return inventaris;
    }

    @BeforeEach
    public void initTest() {
        inventaris = createEntity(em);
    }

    @Test
    @Transactional
    void createInventaris() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Inventaris
        var returnedInventaris = om.readValue(
            restInventarisMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventaris)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Inventaris.class
        );

        // Validate the Inventaris in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInventarisUpdatableFieldsEquals(returnedInventaris, getPersistedInventaris(returnedInventaris));
    }

    @Test
    @Transactional
    void createInventarisWithExistingId() throws Exception {
        // Create the Inventaris with an existing ID
        inventaris.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventarisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventaris)))
            .andExpect(status().isBadRequest());

        // Validate the Inventaris in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInventarises() throws Exception {
        // Initialize the database
        inventarisRepository.saveAndFlush(inventaris);

        // Get all the inventarisList
        restInventarisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventaris.getId().intValue())));
    }

    @Test
    @Transactional
    void getInventaris() throws Exception {
        // Initialize the database
        inventarisRepository.saveAndFlush(inventaris);

        // Get the inventaris
        restInventarisMockMvc
            .perform(get(ENTITY_API_URL_ID, inventaris.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventaris.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingInventaris() throws Exception {
        // Get the inventaris
        restInventarisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteInventaris() throws Exception {
        // Initialize the database
        inventarisRepository.saveAndFlush(inventaris);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inventaris
        restInventarisMockMvc
            .perform(delete(ENTITY_API_URL_ID, inventaris.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inventarisRepository.count();
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

    protected Inventaris getPersistedInventaris(Inventaris inventaris) {
        return inventarisRepository.findById(inventaris.getId()).orElseThrow();
    }

    protected void assertPersistedInventarisToMatchAllProperties(Inventaris expectedInventaris) {
        assertInventarisAllPropertiesEquals(expectedInventaris, getPersistedInventaris(expectedInventaris));
    }

    protected void assertPersistedInventarisToMatchUpdatableProperties(Inventaris expectedInventaris) {
        assertInventarisAllUpdatablePropertiesEquals(expectedInventaris, getPersistedInventaris(expectedInventaris));
    }
}
