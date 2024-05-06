package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BredeintakeAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Bredeintake;
import nl.ritense.demo.repository.BredeintakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BredeintakeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BredeintakeResourceIT {

    private static final String ENTITY_API_URL = "/api/bredeintakes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BredeintakeRepository bredeintakeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBredeintakeMockMvc;

    private Bredeintake bredeintake;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bredeintake createEntity(EntityManager em) {
        Bredeintake bredeintake = new Bredeintake();
        return bredeintake;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bredeintake createUpdatedEntity(EntityManager em) {
        Bredeintake bredeintake = new Bredeintake();
        return bredeintake;
    }

    @BeforeEach
    public void initTest() {
        bredeintake = createEntity(em);
    }

    @Test
    @Transactional
    void createBredeintake() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bredeintake
        var returnedBredeintake = om.readValue(
            restBredeintakeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bredeintake)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bredeintake.class
        );

        // Validate the Bredeintake in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBredeintakeUpdatableFieldsEquals(returnedBredeintake, getPersistedBredeintake(returnedBredeintake));
    }

    @Test
    @Transactional
    void createBredeintakeWithExistingId() throws Exception {
        // Create the Bredeintake with an existing ID
        bredeintake.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBredeintakeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bredeintake)))
            .andExpect(status().isBadRequest());

        // Validate the Bredeintake in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBredeintakes() throws Exception {
        // Initialize the database
        bredeintakeRepository.saveAndFlush(bredeintake);

        // Get all the bredeintakeList
        restBredeintakeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bredeintake.getId().intValue())));
    }

    @Test
    @Transactional
    void getBredeintake() throws Exception {
        // Initialize the database
        bredeintakeRepository.saveAndFlush(bredeintake);

        // Get the bredeintake
        restBredeintakeMockMvc
            .perform(get(ENTITY_API_URL_ID, bredeintake.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bredeintake.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBredeintake() throws Exception {
        // Get the bredeintake
        restBredeintakeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteBredeintake() throws Exception {
        // Initialize the database
        bredeintakeRepository.saveAndFlush(bredeintake);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bredeintake
        restBredeintakeMockMvc
            .perform(delete(ENTITY_API_URL_ID, bredeintake.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bredeintakeRepository.count();
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

    protected Bredeintake getPersistedBredeintake(Bredeintake bredeintake) {
        return bredeintakeRepository.findById(bredeintake.getId()).orElseThrow();
    }

    protected void assertPersistedBredeintakeToMatchAllProperties(Bredeintake expectedBredeintake) {
        assertBredeintakeAllPropertiesEquals(expectedBredeintake, getPersistedBredeintake(expectedBredeintake));
    }

    protected void assertPersistedBredeintakeToMatchUpdatableProperties(Bredeintake expectedBredeintake) {
        assertBredeintakeAllUpdatablePropertiesEquals(expectedBredeintake, getPersistedBredeintake(expectedBredeintake));
    }
}
