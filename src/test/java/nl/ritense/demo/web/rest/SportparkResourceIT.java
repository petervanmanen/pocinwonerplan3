package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SportparkAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Sportpark;
import nl.ritense.demo.repository.SportparkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SportparkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SportparkResourceIT {

    private static final String ENTITY_API_URL = "/api/sportparks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SportparkRepository sportparkRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSportparkMockMvc;

    private Sportpark sportpark;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sportpark createEntity(EntityManager em) {
        Sportpark sportpark = new Sportpark();
        return sportpark;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sportpark createUpdatedEntity(EntityManager em) {
        Sportpark sportpark = new Sportpark();
        return sportpark;
    }

    @BeforeEach
    public void initTest() {
        sportpark = createEntity(em);
    }

    @Test
    @Transactional
    void createSportpark() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sportpark
        var returnedSportpark = om.readValue(
            restSportparkMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sportpark)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Sportpark.class
        );

        // Validate the Sportpark in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSportparkUpdatableFieldsEquals(returnedSportpark, getPersistedSportpark(returnedSportpark));
    }

    @Test
    @Transactional
    void createSportparkWithExistingId() throws Exception {
        // Create the Sportpark with an existing ID
        sportpark.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSportparkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sportpark)))
            .andExpect(status().isBadRequest());

        // Validate the Sportpark in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSportparks() throws Exception {
        // Initialize the database
        sportparkRepository.saveAndFlush(sportpark);

        // Get all the sportparkList
        restSportparkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sportpark.getId().intValue())));
    }

    @Test
    @Transactional
    void getSportpark() throws Exception {
        // Initialize the database
        sportparkRepository.saveAndFlush(sportpark);

        // Get the sportpark
        restSportparkMockMvc
            .perform(get(ENTITY_API_URL_ID, sportpark.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sportpark.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingSportpark() throws Exception {
        // Get the sportpark
        restSportparkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteSportpark() throws Exception {
        // Initialize the database
        sportparkRepository.saveAndFlush(sportpark);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sportpark
        restSportparkMockMvc
            .perform(delete(ENTITY_API_URL_ID, sportpark.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sportparkRepository.count();
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

    protected Sportpark getPersistedSportpark(Sportpark sportpark) {
        return sportparkRepository.findById(sportpark.getId()).orElseThrow();
    }

    protected void assertPersistedSportparkToMatchAllProperties(Sportpark expectedSportpark) {
        assertSportparkAllPropertiesEquals(expectedSportpark, getPersistedSportpark(expectedSportpark));
    }

    protected void assertPersistedSportparkToMatchUpdatableProperties(Sportpark expectedSportpark) {
        assertSportparkAllUpdatablePropertiesEquals(expectedSportpark, getPersistedSportpark(expectedSportpark));
    }
}
