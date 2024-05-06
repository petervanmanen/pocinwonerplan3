package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PachterAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Pachter;
import nl.ritense.demo.repository.PachterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PachterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PachterResourceIT {

    private static final String ENTITY_API_URL = "/api/pachters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PachterRepository pachterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPachterMockMvc;

    private Pachter pachter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pachter createEntity(EntityManager em) {
        Pachter pachter = new Pachter();
        return pachter;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pachter createUpdatedEntity(EntityManager em) {
        Pachter pachter = new Pachter();
        return pachter;
    }

    @BeforeEach
    public void initTest() {
        pachter = createEntity(em);
    }

    @Test
    @Transactional
    void createPachter() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Pachter
        var returnedPachter = om.readValue(
            restPachterMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pachter)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Pachter.class
        );

        // Validate the Pachter in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPachterUpdatableFieldsEquals(returnedPachter, getPersistedPachter(returnedPachter));
    }

    @Test
    @Transactional
    void createPachterWithExistingId() throws Exception {
        // Create the Pachter with an existing ID
        pachter.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPachterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pachter)))
            .andExpect(status().isBadRequest());

        // Validate the Pachter in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPachters() throws Exception {
        // Initialize the database
        pachterRepository.saveAndFlush(pachter);

        // Get all the pachterList
        restPachterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pachter.getId().intValue())));
    }

    @Test
    @Transactional
    void getPachter() throws Exception {
        // Initialize the database
        pachterRepository.saveAndFlush(pachter);

        // Get the pachter
        restPachterMockMvc
            .perform(get(ENTITY_API_URL_ID, pachter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pachter.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPachter() throws Exception {
        // Get the pachter
        restPachterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deletePachter() throws Exception {
        // Initialize the database
        pachterRepository.saveAndFlush(pachter);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pachter
        restPachterMockMvc
            .perform(delete(ENTITY_API_URL_ID, pachter.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pachterRepository.count();
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

    protected Pachter getPersistedPachter(Pachter pachter) {
        return pachterRepository.findById(pachter.getId()).orElseThrow();
    }

    protected void assertPersistedPachterToMatchAllProperties(Pachter expectedPachter) {
        assertPachterAllPropertiesEquals(expectedPachter, getPersistedPachter(expectedPachter));
    }

    protected void assertPersistedPachterToMatchUpdatableProperties(Pachter expectedPachter) {
        assertPachterAllUpdatablePropertiesEquals(expectedPachter, getPersistedPachter(expectedPachter));
    }
}
