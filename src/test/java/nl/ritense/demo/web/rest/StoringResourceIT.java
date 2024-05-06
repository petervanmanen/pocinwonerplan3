package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.StoringAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Storing;
import nl.ritense.demo.repository.StoringRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StoringResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StoringResourceIT {

    private static final String ENTITY_API_URL = "/api/storings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StoringRepository storingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStoringMockMvc;

    private Storing storing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Storing createEntity(EntityManager em) {
        Storing storing = new Storing();
        return storing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Storing createUpdatedEntity(EntityManager em) {
        Storing storing = new Storing();
        return storing;
    }

    @BeforeEach
    public void initTest() {
        storing = createEntity(em);
    }

    @Test
    @Transactional
    void createStoring() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Storing
        var returnedStoring = om.readValue(
            restStoringMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(storing)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Storing.class
        );

        // Validate the Storing in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStoringUpdatableFieldsEquals(returnedStoring, getPersistedStoring(returnedStoring));
    }

    @Test
    @Transactional
    void createStoringWithExistingId() throws Exception {
        // Create the Storing with an existing ID
        storing.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoringMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(storing)))
            .andExpect(status().isBadRequest());

        // Validate the Storing in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStorings() throws Exception {
        // Initialize the database
        storingRepository.saveAndFlush(storing);

        // Get all the storingList
        restStoringMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storing.getId().intValue())));
    }

    @Test
    @Transactional
    void getStoring() throws Exception {
        // Initialize the database
        storingRepository.saveAndFlush(storing);

        // Get the storing
        restStoringMockMvc
            .perform(get(ENTITY_API_URL_ID, storing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(storing.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingStoring() throws Exception {
        // Get the storing
        restStoringMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteStoring() throws Exception {
        // Initialize the database
        storingRepository.saveAndFlush(storing);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the storing
        restStoringMockMvc
            .perform(delete(ENTITY_API_URL_ID, storing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return storingRepository.count();
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

    protected Storing getPersistedStoring(Storing storing) {
        return storingRepository.findById(storing.getId()).orElseThrow();
    }

    protected void assertPersistedStoringToMatchAllProperties(Storing expectedStoring) {
        assertStoringAllPropertiesEquals(expectedStoring, getPersistedStoring(expectedStoring));
    }

    protected void assertPersistedStoringToMatchUpdatableProperties(Storing expectedStoring) {
        assertStoringAllUpdatablePropertiesEquals(expectedStoring, getPersistedStoring(expectedStoring));
    }
}
