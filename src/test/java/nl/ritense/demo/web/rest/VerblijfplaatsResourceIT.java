package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerblijfplaatsAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Verblijfplaats;
import nl.ritense.demo.repository.VerblijfplaatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerblijfplaatsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerblijfplaatsResourceIT {

    private static final String ENTITY_API_URL = "/api/verblijfplaats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerblijfplaatsRepository verblijfplaatsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerblijfplaatsMockMvc;

    private Verblijfplaats verblijfplaats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfplaats createEntity(EntityManager em) {
        Verblijfplaats verblijfplaats = new Verblijfplaats();
        return verblijfplaats;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfplaats createUpdatedEntity(EntityManager em) {
        Verblijfplaats verblijfplaats = new Verblijfplaats();
        return verblijfplaats;
    }

    @BeforeEach
    public void initTest() {
        verblijfplaats = createEntity(em);
    }

    @Test
    @Transactional
    void createVerblijfplaats() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verblijfplaats
        var returnedVerblijfplaats = om.readValue(
            restVerblijfplaatsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfplaats)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verblijfplaats.class
        );

        // Validate the Verblijfplaats in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerblijfplaatsUpdatableFieldsEquals(returnedVerblijfplaats, getPersistedVerblijfplaats(returnedVerblijfplaats));
    }

    @Test
    @Transactional
    void createVerblijfplaatsWithExistingId() throws Exception {
        // Create the Verblijfplaats with an existing ID
        verblijfplaats.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerblijfplaatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfplaats)))
            .andExpect(status().isBadRequest());

        // Validate the Verblijfplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerblijfplaats() throws Exception {
        // Initialize the database
        verblijfplaatsRepository.saveAndFlush(verblijfplaats);

        // Get all the verblijfplaatsList
        restVerblijfplaatsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verblijfplaats.getId().intValue())));
    }

    @Test
    @Transactional
    void getVerblijfplaats() throws Exception {
        // Initialize the database
        verblijfplaatsRepository.saveAndFlush(verblijfplaats);

        // Get the verblijfplaats
        restVerblijfplaatsMockMvc
            .perform(get(ENTITY_API_URL_ID, verblijfplaats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verblijfplaats.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingVerblijfplaats() throws Exception {
        // Get the verblijfplaats
        restVerblijfplaatsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteVerblijfplaats() throws Exception {
        // Initialize the database
        verblijfplaatsRepository.saveAndFlush(verblijfplaats);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verblijfplaats
        restVerblijfplaatsMockMvc
            .perform(delete(ENTITY_API_URL_ID, verblijfplaats.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verblijfplaatsRepository.count();
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

    protected Verblijfplaats getPersistedVerblijfplaats(Verblijfplaats verblijfplaats) {
        return verblijfplaatsRepository.findById(verblijfplaats.getId()).orElseThrow();
    }

    protected void assertPersistedVerblijfplaatsToMatchAllProperties(Verblijfplaats expectedVerblijfplaats) {
        assertVerblijfplaatsAllPropertiesEquals(expectedVerblijfplaats, getPersistedVerblijfplaats(expectedVerblijfplaats));
    }

    protected void assertPersistedVerblijfplaatsToMatchUpdatableProperties(Verblijfplaats expectedVerblijfplaats) {
        assertVerblijfplaatsAllUpdatablePropertiesEquals(expectedVerblijfplaats, getPersistedVerblijfplaats(expectedVerblijfplaats));
    }
}
