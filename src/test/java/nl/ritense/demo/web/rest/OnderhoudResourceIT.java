package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OnderhoudAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Onderhoud;
import nl.ritense.demo.repository.OnderhoudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OnderhoudResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OnderhoudResourceIT {

    private static final String ENTITY_API_URL = "/api/onderhouds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OnderhoudRepository onderhoudRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOnderhoudMockMvc;

    private Onderhoud onderhoud;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderhoud createEntity(EntityManager em) {
        Onderhoud onderhoud = new Onderhoud();
        return onderhoud;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderhoud createUpdatedEntity(EntityManager em) {
        Onderhoud onderhoud = new Onderhoud();
        return onderhoud;
    }

    @BeforeEach
    public void initTest() {
        onderhoud = createEntity(em);
    }

    @Test
    @Transactional
    void createOnderhoud() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Onderhoud
        var returnedOnderhoud = om.readValue(
            restOnderhoudMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderhoud)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Onderhoud.class
        );

        // Validate the Onderhoud in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOnderhoudUpdatableFieldsEquals(returnedOnderhoud, getPersistedOnderhoud(returnedOnderhoud));
    }

    @Test
    @Transactional
    void createOnderhoudWithExistingId() throws Exception {
        // Create the Onderhoud with an existing ID
        onderhoud.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOnderhoudMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderhoud)))
            .andExpect(status().isBadRequest());

        // Validate the Onderhoud in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOnderhouds() throws Exception {
        // Initialize the database
        onderhoudRepository.saveAndFlush(onderhoud);

        // Get all the onderhoudList
        restOnderhoudMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(onderhoud.getId().intValue())));
    }

    @Test
    @Transactional
    void getOnderhoud() throws Exception {
        // Initialize the database
        onderhoudRepository.saveAndFlush(onderhoud);

        // Get the onderhoud
        restOnderhoudMockMvc
            .perform(get(ENTITY_API_URL_ID, onderhoud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(onderhoud.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOnderhoud() throws Exception {
        // Get the onderhoud
        restOnderhoudMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteOnderhoud() throws Exception {
        // Initialize the database
        onderhoudRepository.saveAndFlush(onderhoud);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the onderhoud
        restOnderhoudMockMvc
            .perform(delete(ENTITY_API_URL_ID, onderhoud.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return onderhoudRepository.count();
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

    protected Onderhoud getPersistedOnderhoud(Onderhoud onderhoud) {
        return onderhoudRepository.findById(onderhoud.getId()).orElseThrow();
    }

    protected void assertPersistedOnderhoudToMatchAllProperties(Onderhoud expectedOnderhoud) {
        assertOnderhoudAllPropertiesEquals(expectedOnderhoud, getPersistedOnderhoud(expectedOnderhoud));
    }

    protected void assertPersistedOnderhoudToMatchUpdatableProperties(Onderhoud expectedOnderhoud) {
        assertOnderhoudAllUpdatablePropertiesEquals(expectedOnderhoud, getPersistedOnderhoud(expectedOnderhoud));
    }
}
