package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BezettingAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Bezetting;
import nl.ritense.demo.repository.BezettingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BezettingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BezettingResourceIT {

    private static final String ENTITY_API_URL = "/api/bezettings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BezettingRepository bezettingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBezettingMockMvc;

    private Bezetting bezetting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bezetting createEntity(EntityManager em) {
        Bezetting bezetting = new Bezetting();
        return bezetting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bezetting createUpdatedEntity(EntityManager em) {
        Bezetting bezetting = new Bezetting();
        return bezetting;
    }

    @BeforeEach
    public void initTest() {
        bezetting = createEntity(em);
    }

    @Test
    @Transactional
    void createBezetting() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bezetting
        var returnedBezetting = om.readValue(
            restBezettingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bezetting)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bezetting.class
        );

        // Validate the Bezetting in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBezettingUpdatableFieldsEquals(returnedBezetting, getPersistedBezetting(returnedBezetting));
    }

    @Test
    @Transactional
    void createBezettingWithExistingId() throws Exception {
        // Create the Bezetting with an existing ID
        bezetting.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBezettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bezetting)))
            .andExpect(status().isBadRequest());

        // Validate the Bezetting in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBezettings() throws Exception {
        // Initialize the database
        bezettingRepository.saveAndFlush(bezetting);

        // Get all the bezettingList
        restBezettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bezetting.getId().intValue())));
    }

    @Test
    @Transactional
    void getBezetting() throws Exception {
        // Initialize the database
        bezettingRepository.saveAndFlush(bezetting);

        // Get the bezetting
        restBezettingMockMvc
            .perform(get(ENTITY_API_URL_ID, bezetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bezetting.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBezetting() throws Exception {
        // Get the bezetting
        restBezettingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteBezetting() throws Exception {
        // Initialize the database
        bezettingRepository.saveAndFlush(bezetting);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bezetting
        restBezettingMockMvc
            .perform(delete(ENTITY_API_URL_ID, bezetting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bezettingRepository.count();
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

    protected Bezetting getPersistedBezetting(Bezetting bezetting) {
        return bezettingRepository.findById(bezetting.getId()).orElseThrow();
    }

    protected void assertPersistedBezettingToMatchAllProperties(Bezetting expectedBezetting) {
        assertBezettingAllPropertiesEquals(expectedBezetting, getPersistedBezetting(expectedBezetting));
    }

    protected void assertPersistedBezettingToMatchUpdatableProperties(Bezetting expectedBezetting) {
        assertBezettingAllUpdatablePropertiesEquals(expectedBezetting, getPersistedBezetting(expectedBezetting));
    }
}
