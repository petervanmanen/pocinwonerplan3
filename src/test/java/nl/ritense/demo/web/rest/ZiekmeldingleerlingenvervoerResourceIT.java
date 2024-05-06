package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ZiekmeldingleerlingenvervoerAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Ziekmeldingleerlingenvervoer;
import nl.ritense.demo.repository.ZiekmeldingleerlingenvervoerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ZiekmeldingleerlingenvervoerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ZiekmeldingleerlingenvervoerResourceIT {

    private static final String ENTITY_API_URL = "/api/ziekmeldingleerlingenvervoers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ZiekmeldingleerlingenvervoerRepository ziekmeldingleerlingenvervoerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZiekmeldingleerlingenvervoerMockMvc;

    private Ziekmeldingleerlingenvervoer ziekmeldingleerlingenvervoer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ziekmeldingleerlingenvervoer createEntity(EntityManager em) {
        Ziekmeldingleerlingenvervoer ziekmeldingleerlingenvervoer = new Ziekmeldingleerlingenvervoer();
        return ziekmeldingleerlingenvervoer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ziekmeldingleerlingenvervoer createUpdatedEntity(EntityManager em) {
        Ziekmeldingleerlingenvervoer ziekmeldingleerlingenvervoer = new Ziekmeldingleerlingenvervoer();
        return ziekmeldingleerlingenvervoer;
    }

    @BeforeEach
    public void initTest() {
        ziekmeldingleerlingenvervoer = createEntity(em);
    }

    @Test
    @Transactional
    void createZiekmeldingleerlingenvervoer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ziekmeldingleerlingenvervoer
        var returnedZiekmeldingleerlingenvervoer = om.readValue(
            restZiekmeldingleerlingenvervoerMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ziekmeldingleerlingenvervoer))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Ziekmeldingleerlingenvervoer.class
        );

        // Validate the Ziekmeldingleerlingenvervoer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertZiekmeldingleerlingenvervoerUpdatableFieldsEquals(
            returnedZiekmeldingleerlingenvervoer,
            getPersistedZiekmeldingleerlingenvervoer(returnedZiekmeldingleerlingenvervoer)
        );
    }

    @Test
    @Transactional
    void createZiekmeldingleerlingenvervoerWithExistingId() throws Exception {
        // Create the Ziekmeldingleerlingenvervoer with an existing ID
        ziekmeldingleerlingenvervoer.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restZiekmeldingleerlingenvervoerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ziekmeldingleerlingenvervoer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ziekmeldingleerlingenvervoer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllZiekmeldingleerlingenvervoers() throws Exception {
        // Initialize the database
        ziekmeldingleerlingenvervoerRepository.saveAndFlush(ziekmeldingleerlingenvervoer);

        // Get all the ziekmeldingleerlingenvervoerList
        restZiekmeldingleerlingenvervoerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ziekmeldingleerlingenvervoer.getId().intValue())));
    }

    @Test
    @Transactional
    void getZiekmeldingleerlingenvervoer() throws Exception {
        // Initialize the database
        ziekmeldingleerlingenvervoerRepository.saveAndFlush(ziekmeldingleerlingenvervoer);

        // Get the ziekmeldingleerlingenvervoer
        restZiekmeldingleerlingenvervoerMockMvc
            .perform(get(ENTITY_API_URL_ID, ziekmeldingleerlingenvervoer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ziekmeldingleerlingenvervoer.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingZiekmeldingleerlingenvervoer() throws Exception {
        // Get the ziekmeldingleerlingenvervoer
        restZiekmeldingleerlingenvervoerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteZiekmeldingleerlingenvervoer() throws Exception {
        // Initialize the database
        ziekmeldingleerlingenvervoerRepository.saveAndFlush(ziekmeldingleerlingenvervoer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ziekmeldingleerlingenvervoer
        restZiekmeldingleerlingenvervoerMockMvc
            .perform(delete(ENTITY_API_URL_ID, ziekmeldingleerlingenvervoer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ziekmeldingleerlingenvervoerRepository.count();
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

    protected Ziekmeldingleerlingenvervoer getPersistedZiekmeldingleerlingenvervoer(
        Ziekmeldingleerlingenvervoer ziekmeldingleerlingenvervoer
    ) {
        return ziekmeldingleerlingenvervoerRepository.findById(ziekmeldingleerlingenvervoer.getId()).orElseThrow();
    }

    protected void assertPersistedZiekmeldingleerlingenvervoerToMatchAllProperties(
        Ziekmeldingleerlingenvervoer expectedZiekmeldingleerlingenvervoer
    ) {
        assertZiekmeldingleerlingenvervoerAllPropertiesEquals(
            expectedZiekmeldingleerlingenvervoer,
            getPersistedZiekmeldingleerlingenvervoer(expectedZiekmeldingleerlingenvervoer)
        );
    }

    protected void assertPersistedZiekmeldingleerlingenvervoerToMatchUpdatableProperties(
        Ziekmeldingleerlingenvervoer expectedZiekmeldingleerlingenvervoer
    ) {
        assertZiekmeldingleerlingenvervoerAllUpdatablePropertiesEquals(
            expectedZiekmeldingleerlingenvervoer,
            getPersistedZiekmeldingleerlingenvervoer(expectedZiekmeldingleerlingenvervoer)
        );
    }
}
