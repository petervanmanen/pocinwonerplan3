package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WaarnemingAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Waarneming;
import nl.ritense.demo.repository.WaarnemingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WaarnemingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WaarnemingResourceIT {

    private static final String ENTITY_API_URL = "/api/waarnemings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WaarnemingRepository waarnemingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWaarnemingMockMvc;

    private Waarneming waarneming;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Waarneming createEntity(EntityManager em) {
        Waarneming waarneming = new Waarneming();
        return waarneming;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Waarneming createUpdatedEntity(EntityManager em) {
        Waarneming waarneming = new Waarneming();
        return waarneming;
    }

    @BeforeEach
    public void initTest() {
        waarneming = createEntity(em);
    }

    @Test
    @Transactional
    void createWaarneming() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Waarneming
        var returnedWaarneming = om.readValue(
            restWaarnemingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(waarneming)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Waarneming.class
        );

        // Validate the Waarneming in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWaarnemingUpdatableFieldsEquals(returnedWaarneming, getPersistedWaarneming(returnedWaarneming));
    }

    @Test
    @Transactional
    void createWaarnemingWithExistingId() throws Exception {
        // Create the Waarneming with an existing ID
        waarneming.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWaarnemingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(waarneming)))
            .andExpect(status().isBadRequest());

        // Validate the Waarneming in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWaarnemings() throws Exception {
        // Initialize the database
        waarnemingRepository.saveAndFlush(waarneming);

        // Get all the waarnemingList
        restWaarnemingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(waarneming.getId().intValue())));
    }

    @Test
    @Transactional
    void getWaarneming() throws Exception {
        // Initialize the database
        waarnemingRepository.saveAndFlush(waarneming);

        // Get the waarneming
        restWaarnemingMockMvc
            .perform(get(ENTITY_API_URL_ID, waarneming.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(waarneming.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingWaarneming() throws Exception {
        // Get the waarneming
        restWaarnemingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteWaarneming() throws Exception {
        // Initialize the database
        waarnemingRepository.saveAndFlush(waarneming);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the waarneming
        restWaarnemingMockMvc
            .perform(delete(ENTITY_API_URL_ID, waarneming.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return waarnemingRepository.count();
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

    protected Waarneming getPersistedWaarneming(Waarneming waarneming) {
        return waarnemingRepository.findById(waarneming.getId()).orElseThrow();
    }

    protected void assertPersistedWaarnemingToMatchAllProperties(Waarneming expectedWaarneming) {
        assertWaarnemingAllPropertiesEquals(expectedWaarneming, getPersistedWaarneming(expectedWaarneming));
    }

    protected void assertPersistedWaarnemingToMatchUpdatableProperties(Waarneming expectedWaarneming) {
        assertWaarnemingAllUpdatablePropertiesEquals(expectedWaarneming, getPersistedWaarneming(expectedWaarneming));
    }
}
