package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BeschikkingleerlingenvervoerAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Beschikkingleerlingenvervoer;
import nl.ritense.demo.repository.BeschikkingleerlingenvervoerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BeschikkingleerlingenvervoerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BeschikkingleerlingenvervoerResourceIT {

    private static final String ENTITY_API_URL = "/api/beschikkingleerlingenvervoers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BeschikkingleerlingenvervoerRepository beschikkingleerlingenvervoerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeschikkingleerlingenvervoerMockMvc;

    private Beschikkingleerlingenvervoer beschikkingleerlingenvervoer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beschikkingleerlingenvervoer createEntity(EntityManager em) {
        Beschikkingleerlingenvervoer beschikkingleerlingenvervoer = new Beschikkingleerlingenvervoer();
        return beschikkingleerlingenvervoer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beschikkingleerlingenvervoer createUpdatedEntity(EntityManager em) {
        Beschikkingleerlingenvervoer beschikkingleerlingenvervoer = new Beschikkingleerlingenvervoer();
        return beschikkingleerlingenvervoer;
    }

    @BeforeEach
    public void initTest() {
        beschikkingleerlingenvervoer = createEntity(em);
    }

    @Test
    @Transactional
    void createBeschikkingleerlingenvervoer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Beschikkingleerlingenvervoer
        var returnedBeschikkingleerlingenvervoer = om.readValue(
            restBeschikkingleerlingenvervoerMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beschikkingleerlingenvervoer))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Beschikkingleerlingenvervoer.class
        );

        // Validate the Beschikkingleerlingenvervoer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBeschikkingleerlingenvervoerUpdatableFieldsEquals(
            returnedBeschikkingleerlingenvervoer,
            getPersistedBeschikkingleerlingenvervoer(returnedBeschikkingleerlingenvervoer)
        );
    }

    @Test
    @Transactional
    void createBeschikkingleerlingenvervoerWithExistingId() throws Exception {
        // Create the Beschikkingleerlingenvervoer with an existing ID
        beschikkingleerlingenvervoer.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeschikkingleerlingenvervoerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beschikkingleerlingenvervoer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beschikkingleerlingenvervoer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBeschikkingleerlingenvervoers() throws Exception {
        // Initialize the database
        beschikkingleerlingenvervoerRepository.saveAndFlush(beschikkingleerlingenvervoer);

        // Get all the beschikkingleerlingenvervoerList
        restBeschikkingleerlingenvervoerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beschikkingleerlingenvervoer.getId().intValue())));
    }

    @Test
    @Transactional
    void getBeschikkingleerlingenvervoer() throws Exception {
        // Initialize the database
        beschikkingleerlingenvervoerRepository.saveAndFlush(beschikkingleerlingenvervoer);

        // Get the beschikkingleerlingenvervoer
        restBeschikkingleerlingenvervoerMockMvc
            .perform(get(ENTITY_API_URL_ID, beschikkingleerlingenvervoer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beschikkingleerlingenvervoer.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBeschikkingleerlingenvervoer() throws Exception {
        // Get the beschikkingleerlingenvervoer
        restBeschikkingleerlingenvervoerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteBeschikkingleerlingenvervoer() throws Exception {
        // Initialize the database
        beschikkingleerlingenvervoerRepository.saveAndFlush(beschikkingleerlingenvervoer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the beschikkingleerlingenvervoer
        restBeschikkingleerlingenvervoerMockMvc
            .perform(delete(ENTITY_API_URL_ID, beschikkingleerlingenvervoer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return beschikkingleerlingenvervoerRepository.count();
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

    protected Beschikkingleerlingenvervoer getPersistedBeschikkingleerlingenvervoer(
        Beschikkingleerlingenvervoer beschikkingleerlingenvervoer
    ) {
        return beschikkingleerlingenvervoerRepository.findById(beschikkingleerlingenvervoer.getId()).orElseThrow();
    }

    protected void assertPersistedBeschikkingleerlingenvervoerToMatchAllProperties(
        Beschikkingleerlingenvervoer expectedBeschikkingleerlingenvervoer
    ) {
        assertBeschikkingleerlingenvervoerAllPropertiesEquals(
            expectedBeschikkingleerlingenvervoer,
            getPersistedBeschikkingleerlingenvervoer(expectedBeschikkingleerlingenvervoer)
        );
    }

    protected void assertPersistedBeschikkingleerlingenvervoerToMatchUpdatableProperties(
        Beschikkingleerlingenvervoer expectedBeschikkingleerlingenvervoer
    ) {
        assertBeschikkingleerlingenvervoerAllUpdatablePropertiesEquals(
            expectedBeschikkingleerlingenvervoer,
            getPersistedBeschikkingleerlingenvervoer(expectedBeschikkingleerlingenvervoer)
        );
    }
}
