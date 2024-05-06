package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LeerrouteAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Leerroute;
import nl.ritense.demo.repository.LeerrouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LeerrouteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeerrouteResourceIT {

    private static final String ENTITY_API_URL = "/api/leerroutes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LeerrouteRepository leerrouteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeerrouteMockMvc;

    private Leerroute leerroute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leerroute createEntity(EntityManager em) {
        Leerroute leerroute = new Leerroute();
        return leerroute;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leerroute createUpdatedEntity(EntityManager em) {
        Leerroute leerroute = new Leerroute();
        return leerroute;
    }

    @BeforeEach
    public void initTest() {
        leerroute = createEntity(em);
    }

    @Test
    @Transactional
    void createLeerroute() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Leerroute
        var returnedLeerroute = om.readValue(
            restLeerrouteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leerroute)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Leerroute.class
        );

        // Validate the Leerroute in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLeerrouteUpdatableFieldsEquals(returnedLeerroute, getPersistedLeerroute(returnedLeerroute));
    }

    @Test
    @Transactional
    void createLeerrouteWithExistingId() throws Exception {
        // Create the Leerroute with an existing ID
        leerroute.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeerrouteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leerroute)))
            .andExpect(status().isBadRequest());

        // Validate the Leerroute in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeerroutes() throws Exception {
        // Initialize the database
        leerrouteRepository.saveAndFlush(leerroute);

        // Get all the leerrouteList
        restLeerrouteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leerroute.getId().intValue())));
    }

    @Test
    @Transactional
    void getLeerroute() throws Exception {
        // Initialize the database
        leerrouteRepository.saveAndFlush(leerroute);

        // Get the leerroute
        restLeerrouteMockMvc
            .perform(get(ENTITY_API_URL_ID, leerroute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leerroute.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingLeerroute() throws Exception {
        // Get the leerroute
        restLeerrouteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteLeerroute() throws Exception {
        // Initialize the database
        leerrouteRepository.saveAndFlush(leerroute);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the leerroute
        restLeerrouteMockMvc
            .perform(delete(ENTITY_API_URL_ID, leerroute.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return leerrouteRepository.count();
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

    protected Leerroute getPersistedLeerroute(Leerroute leerroute) {
        return leerrouteRepository.findById(leerroute.getId()).orElseThrow();
    }

    protected void assertPersistedLeerrouteToMatchAllProperties(Leerroute expectedLeerroute) {
        assertLeerrouteAllPropertiesEquals(expectedLeerroute, getPersistedLeerroute(expectedLeerroute));
    }

    protected void assertPersistedLeerrouteToMatchUpdatableProperties(Leerroute expectedLeerroute) {
        assertLeerrouteAllUpdatablePropertiesEquals(expectedLeerroute, getPersistedLeerroute(expectedLeerroute));
    }
}
