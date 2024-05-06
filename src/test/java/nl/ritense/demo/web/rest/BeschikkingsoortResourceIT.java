package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BeschikkingsoortAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Beschikkingsoort;
import nl.ritense.demo.repository.BeschikkingsoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BeschikkingsoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BeschikkingsoortResourceIT {

    private static final String ENTITY_API_URL = "/api/beschikkingsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BeschikkingsoortRepository beschikkingsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeschikkingsoortMockMvc;

    private Beschikkingsoort beschikkingsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beschikkingsoort createEntity(EntityManager em) {
        Beschikkingsoort beschikkingsoort = new Beschikkingsoort();
        return beschikkingsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beschikkingsoort createUpdatedEntity(EntityManager em) {
        Beschikkingsoort beschikkingsoort = new Beschikkingsoort();
        return beschikkingsoort;
    }

    @BeforeEach
    public void initTest() {
        beschikkingsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createBeschikkingsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Beschikkingsoort
        var returnedBeschikkingsoort = om.readValue(
            restBeschikkingsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beschikkingsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Beschikkingsoort.class
        );

        // Validate the Beschikkingsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBeschikkingsoortUpdatableFieldsEquals(returnedBeschikkingsoort, getPersistedBeschikkingsoort(returnedBeschikkingsoort));
    }

    @Test
    @Transactional
    void createBeschikkingsoortWithExistingId() throws Exception {
        // Create the Beschikkingsoort with an existing ID
        beschikkingsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeschikkingsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beschikkingsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Beschikkingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBeschikkingsoorts() throws Exception {
        // Initialize the database
        beschikkingsoortRepository.saveAndFlush(beschikkingsoort);

        // Get all the beschikkingsoortList
        restBeschikkingsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beschikkingsoort.getId().intValue())));
    }

    @Test
    @Transactional
    void getBeschikkingsoort() throws Exception {
        // Initialize the database
        beschikkingsoortRepository.saveAndFlush(beschikkingsoort);

        // Get the beschikkingsoort
        restBeschikkingsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, beschikkingsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beschikkingsoort.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBeschikkingsoort() throws Exception {
        // Get the beschikkingsoort
        restBeschikkingsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteBeschikkingsoort() throws Exception {
        // Initialize the database
        beschikkingsoortRepository.saveAndFlush(beschikkingsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the beschikkingsoort
        restBeschikkingsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, beschikkingsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return beschikkingsoortRepository.count();
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

    protected Beschikkingsoort getPersistedBeschikkingsoort(Beschikkingsoort beschikkingsoort) {
        return beschikkingsoortRepository.findById(beschikkingsoort.getId()).orElseThrow();
    }

    protected void assertPersistedBeschikkingsoortToMatchAllProperties(Beschikkingsoort expectedBeschikkingsoort) {
        assertBeschikkingsoortAllPropertiesEquals(expectedBeschikkingsoort, getPersistedBeschikkingsoort(expectedBeschikkingsoort));
    }

    protected void assertPersistedBeschikkingsoortToMatchUpdatableProperties(Beschikkingsoort expectedBeschikkingsoort) {
        assertBeschikkingsoortAllUpdatablePropertiesEquals(
            expectedBeschikkingsoort,
            getPersistedBeschikkingsoort(expectedBeschikkingsoort)
        );
    }
}
