package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RelatiesoortAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Relatiesoort;
import nl.ritense.demo.repository.RelatiesoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RelatiesoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RelatiesoortResourceIT {

    private static final String ENTITY_API_URL = "/api/relatiesoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RelatiesoortRepository relatiesoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRelatiesoortMockMvc;

    private Relatiesoort relatiesoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relatiesoort createEntity(EntityManager em) {
        Relatiesoort relatiesoort = new Relatiesoort();
        return relatiesoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relatiesoort createUpdatedEntity(EntityManager em) {
        Relatiesoort relatiesoort = new Relatiesoort();
        return relatiesoort;
    }

    @BeforeEach
    public void initTest() {
        relatiesoort = createEntity(em);
    }

    @Test
    @Transactional
    void createRelatiesoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Relatiesoort
        var returnedRelatiesoort = om.readValue(
            restRelatiesoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(relatiesoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Relatiesoort.class
        );

        // Validate the Relatiesoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRelatiesoortUpdatableFieldsEquals(returnedRelatiesoort, getPersistedRelatiesoort(returnedRelatiesoort));
    }

    @Test
    @Transactional
    void createRelatiesoortWithExistingId() throws Exception {
        // Create the Relatiesoort with an existing ID
        relatiesoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelatiesoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(relatiesoort)))
            .andExpect(status().isBadRequest());

        // Validate the Relatiesoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRelatiesoorts() throws Exception {
        // Initialize the database
        relatiesoortRepository.saveAndFlush(relatiesoort);

        // Get all the relatiesoortList
        restRelatiesoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relatiesoort.getId().intValue())));
    }

    @Test
    @Transactional
    void getRelatiesoort() throws Exception {
        // Initialize the database
        relatiesoortRepository.saveAndFlush(relatiesoort);

        // Get the relatiesoort
        restRelatiesoortMockMvc
            .perform(get(ENTITY_API_URL_ID, relatiesoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(relatiesoort.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingRelatiesoort() throws Exception {
        // Get the relatiesoort
        restRelatiesoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteRelatiesoort() throws Exception {
        // Initialize the database
        relatiesoortRepository.saveAndFlush(relatiesoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the relatiesoort
        restRelatiesoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, relatiesoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return relatiesoortRepository.count();
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

    protected Relatiesoort getPersistedRelatiesoort(Relatiesoort relatiesoort) {
        return relatiesoortRepository.findById(relatiesoort.getId()).orElseThrow();
    }

    protected void assertPersistedRelatiesoortToMatchAllProperties(Relatiesoort expectedRelatiesoort) {
        assertRelatiesoortAllPropertiesEquals(expectedRelatiesoort, getPersistedRelatiesoort(expectedRelatiesoort));
    }

    protected void assertPersistedRelatiesoortToMatchUpdatableProperties(Relatiesoort expectedRelatiesoort) {
        assertRelatiesoortAllUpdatablePropertiesEquals(expectedRelatiesoort, getPersistedRelatiesoort(expectedRelatiesoort));
    }
}
