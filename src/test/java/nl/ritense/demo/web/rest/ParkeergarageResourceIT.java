package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ParkeergarageAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Parkeergarage;
import nl.ritense.demo.repository.ParkeergarageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ParkeergarageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParkeergarageResourceIT {

    private static final String ENTITY_API_URL = "/api/parkeergarages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ParkeergarageRepository parkeergarageRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParkeergarageMockMvc;

    private Parkeergarage parkeergarage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parkeergarage createEntity(EntityManager em) {
        Parkeergarage parkeergarage = new Parkeergarage();
        return parkeergarage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parkeergarage createUpdatedEntity(EntityManager em) {
        Parkeergarage parkeergarage = new Parkeergarage();
        return parkeergarage;
    }

    @BeforeEach
    public void initTest() {
        parkeergarage = createEntity(em);
    }

    @Test
    @Transactional
    void createParkeergarage() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Parkeergarage
        var returnedParkeergarage = om.readValue(
            restParkeergarageMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parkeergarage)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Parkeergarage.class
        );

        // Validate the Parkeergarage in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertParkeergarageUpdatableFieldsEquals(returnedParkeergarage, getPersistedParkeergarage(returnedParkeergarage));
    }

    @Test
    @Transactional
    void createParkeergarageWithExistingId() throws Exception {
        // Create the Parkeergarage with an existing ID
        parkeergarage.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParkeergarageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parkeergarage)))
            .andExpect(status().isBadRequest());

        // Validate the Parkeergarage in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParkeergarages() throws Exception {
        // Initialize the database
        parkeergarageRepository.saveAndFlush(parkeergarage);

        // Get all the parkeergarageList
        restParkeergarageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parkeergarage.getId().intValue())));
    }

    @Test
    @Transactional
    void getParkeergarage() throws Exception {
        // Initialize the database
        parkeergarageRepository.saveAndFlush(parkeergarage);

        // Get the parkeergarage
        restParkeergarageMockMvc
            .perform(get(ENTITY_API_URL_ID, parkeergarage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parkeergarage.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingParkeergarage() throws Exception {
        // Get the parkeergarage
        restParkeergarageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteParkeergarage() throws Exception {
        // Initialize the database
        parkeergarageRepository.saveAndFlush(parkeergarage);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the parkeergarage
        restParkeergarageMockMvc
            .perform(delete(ENTITY_API_URL_ID, parkeergarage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return parkeergarageRepository.count();
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

    protected Parkeergarage getPersistedParkeergarage(Parkeergarage parkeergarage) {
        return parkeergarageRepository.findById(parkeergarage.getId()).orElseThrow();
    }

    protected void assertPersistedParkeergarageToMatchAllProperties(Parkeergarage expectedParkeergarage) {
        assertParkeergarageAllPropertiesEquals(expectedParkeergarage, getPersistedParkeergarage(expectedParkeergarage));
    }

    protected void assertPersistedParkeergarageToMatchUpdatableProperties(Parkeergarage expectedParkeergarage) {
        assertParkeergarageAllUpdatablePropertiesEquals(expectedParkeergarage, getPersistedParkeergarage(expectedParkeergarage));
    }
}
