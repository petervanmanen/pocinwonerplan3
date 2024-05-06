package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NaderetoegangAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Naderetoegang;
import nl.ritense.demo.repository.NaderetoegangRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NaderetoegangResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NaderetoegangResourceIT {

    private static final String ENTITY_API_URL = "/api/naderetoegangs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NaderetoegangRepository naderetoegangRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNaderetoegangMockMvc;

    private Naderetoegang naderetoegang;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Naderetoegang createEntity(EntityManager em) {
        Naderetoegang naderetoegang = new Naderetoegang();
        return naderetoegang;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Naderetoegang createUpdatedEntity(EntityManager em) {
        Naderetoegang naderetoegang = new Naderetoegang();
        return naderetoegang;
    }

    @BeforeEach
    public void initTest() {
        naderetoegang = createEntity(em);
    }

    @Test
    @Transactional
    void createNaderetoegang() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Naderetoegang
        var returnedNaderetoegang = om.readValue(
            restNaderetoegangMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(naderetoegang)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Naderetoegang.class
        );

        // Validate the Naderetoegang in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNaderetoegangUpdatableFieldsEquals(returnedNaderetoegang, getPersistedNaderetoegang(returnedNaderetoegang));
    }

    @Test
    @Transactional
    void createNaderetoegangWithExistingId() throws Exception {
        // Create the Naderetoegang with an existing ID
        naderetoegang.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNaderetoegangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(naderetoegang)))
            .andExpect(status().isBadRequest());

        // Validate the Naderetoegang in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNaderetoegangs() throws Exception {
        // Initialize the database
        naderetoegangRepository.saveAndFlush(naderetoegang);

        // Get all the naderetoegangList
        restNaderetoegangMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naderetoegang.getId().intValue())));
    }

    @Test
    @Transactional
    void getNaderetoegang() throws Exception {
        // Initialize the database
        naderetoegangRepository.saveAndFlush(naderetoegang);

        // Get the naderetoegang
        restNaderetoegangMockMvc
            .perform(get(ENTITY_API_URL_ID, naderetoegang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(naderetoegang.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingNaderetoegang() throws Exception {
        // Get the naderetoegang
        restNaderetoegangMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteNaderetoegang() throws Exception {
        // Initialize the database
        naderetoegangRepository.saveAndFlush(naderetoegang);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the naderetoegang
        restNaderetoegangMockMvc
            .perform(delete(ENTITY_API_URL_ID, naderetoegang.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return naderetoegangRepository.count();
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

    protected Naderetoegang getPersistedNaderetoegang(Naderetoegang naderetoegang) {
        return naderetoegangRepository.findById(naderetoegang.getId()).orElseThrow();
    }

    protected void assertPersistedNaderetoegangToMatchAllProperties(Naderetoegang expectedNaderetoegang) {
        assertNaderetoegangAllPropertiesEquals(expectedNaderetoegang, getPersistedNaderetoegang(expectedNaderetoegang));
    }

    protected void assertPersistedNaderetoegangToMatchUpdatableProperties(Naderetoegang expectedNaderetoegang) {
        assertNaderetoegangAllUpdatablePropertiesEquals(expectedNaderetoegang, getPersistedNaderetoegang(expectedNaderetoegang));
    }
}
