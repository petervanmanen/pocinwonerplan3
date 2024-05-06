package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BezoekerAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Bezoeker;
import nl.ritense.demo.repository.BezoekerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BezoekerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BezoekerResourceIT {

    private static final String ENTITY_API_URL = "/api/bezoekers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BezoekerRepository bezoekerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBezoekerMockMvc;

    private Bezoeker bezoeker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bezoeker createEntity(EntityManager em) {
        Bezoeker bezoeker = new Bezoeker();
        return bezoeker;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bezoeker createUpdatedEntity(EntityManager em) {
        Bezoeker bezoeker = new Bezoeker();
        return bezoeker;
    }

    @BeforeEach
    public void initTest() {
        bezoeker = createEntity(em);
    }

    @Test
    @Transactional
    void createBezoeker() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bezoeker
        var returnedBezoeker = om.readValue(
            restBezoekerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bezoeker)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bezoeker.class
        );

        // Validate the Bezoeker in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBezoekerUpdatableFieldsEquals(returnedBezoeker, getPersistedBezoeker(returnedBezoeker));
    }

    @Test
    @Transactional
    void createBezoekerWithExistingId() throws Exception {
        // Create the Bezoeker with an existing ID
        bezoeker.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBezoekerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bezoeker)))
            .andExpect(status().isBadRequest());

        // Validate the Bezoeker in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBezoekers() throws Exception {
        // Initialize the database
        bezoekerRepository.saveAndFlush(bezoeker);

        // Get all the bezoekerList
        restBezoekerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bezoeker.getId().intValue())));
    }

    @Test
    @Transactional
    void getBezoeker() throws Exception {
        // Initialize the database
        bezoekerRepository.saveAndFlush(bezoeker);

        // Get the bezoeker
        restBezoekerMockMvc
            .perform(get(ENTITY_API_URL_ID, bezoeker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bezoeker.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBezoeker() throws Exception {
        // Get the bezoeker
        restBezoekerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteBezoeker() throws Exception {
        // Initialize the database
        bezoekerRepository.saveAndFlush(bezoeker);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bezoeker
        restBezoekerMockMvc
            .perform(delete(ENTITY_API_URL_ID, bezoeker.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bezoekerRepository.count();
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

    protected Bezoeker getPersistedBezoeker(Bezoeker bezoeker) {
        return bezoekerRepository.findById(bezoeker.getId()).orElseThrow();
    }

    protected void assertPersistedBezoekerToMatchAllProperties(Bezoeker expectedBezoeker) {
        assertBezoekerAllPropertiesEquals(expectedBezoeker, getPersistedBezoeker(expectedBezoeker));
    }

    protected void assertPersistedBezoekerToMatchUpdatableProperties(Bezoeker expectedBezoeker) {
        assertBezoekerAllUpdatablePropertiesEquals(expectedBezoeker, getPersistedBezoeker(expectedBezoeker));
    }
}
