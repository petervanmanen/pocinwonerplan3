package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FaseopleveringAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Faseoplevering;
import nl.ritense.demo.repository.FaseopleveringRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FaseopleveringResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FaseopleveringResourceIT {

    private static final String ENTITY_API_URL = "/api/faseopleverings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FaseopleveringRepository faseopleveringRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFaseopleveringMockMvc;

    private Faseoplevering faseoplevering;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Faseoplevering createEntity(EntityManager em) {
        Faseoplevering faseoplevering = new Faseoplevering();
        return faseoplevering;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Faseoplevering createUpdatedEntity(EntityManager em) {
        Faseoplevering faseoplevering = new Faseoplevering();
        return faseoplevering;
    }

    @BeforeEach
    public void initTest() {
        faseoplevering = createEntity(em);
    }

    @Test
    @Transactional
    void createFaseoplevering() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Faseoplevering
        var returnedFaseoplevering = om.readValue(
            restFaseopleveringMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(faseoplevering)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Faseoplevering.class
        );

        // Validate the Faseoplevering in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFaseopleveringUpdatableFieldsEquals(returnedFaseoplevering, getPersistedFaseoplevering(returnedFaseoplevering));
    }

    @Test
    @Transactional
    void createFaseopleveringWithExistingId() throws Exception {
        // Create the Faseoplevering with an existing ID
        faseoplevering.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFaseopleveringMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(faseoplevering)))
            .andExpect(status().isBadRequest());

        // Validate the Faseoplevering in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFaseopleverings() throws Exception {
        // Initialize the database
        faseopleveringRepository.saveAndFlush(faseoplevering);

        // Get all the faseopleveringList
        restFaseopleveringMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faseoplevering.getId().intValue())));
    }

    @Test
    @Transactional
    void getFaseoplevering() throws Exception {
        // Initialize the database
        faseopleveringRepository.saveAndFlush(faseoplevering);

        // Get the faseoplevering
        restFaseopleveringMockMvc
            .perform(get(ENTITY_API_URL_ID, faseoplevering.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(faseoplevering.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingFaseoplevering() throws Exception {
        // Get the faseoplevering
        restFaseopleveringMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteFaseoplevering() throws Exception {
        // Initialize the database
        faseopleveringRepository.saveAndFlush(faseoplevering);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the faseoplevering
        restFaseopleveringMockMvc
            .perform(delete(ENTITY_API_URL_ID, faseoplevering.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return faseopleveringRepository.count();
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

    protected Faseoplevering getPersistedFaseoplevering(Faseoplevering faseoplevering) {
        return faseopleveringRepository.findById(faseoplevering.getId()).orElseThrow();
    }

    protected void assertPersistedFaseopleveringToMatchAllProperties(Faseoplevering expectedFaseoplevering) {
        assertFaseopleveringAllPropertiesEquals(expectedFaseoplevering, getPersistedFaseoplevering(expectedFaseoplevering));
    }

    protected void assertPersistedFaseopleveringToMatchUpdatableProperties(Faseoplevering expectedFaseoplevering) {
        assertFaseopleveringAllUpdatablePropertiesEquals(expectedFaseoplevering, getPersistedFaseoplevering(expectedFaseoplevering));
    }
}
