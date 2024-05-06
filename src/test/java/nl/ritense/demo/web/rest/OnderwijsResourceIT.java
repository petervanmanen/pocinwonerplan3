package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OnderwijsAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Onderwijs;
import nl.ritense.demo.repository.OnderwijsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OnderwijsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OnderwijsResourceIT {

    private static final String ENTITY_API_URL = "/api/onderwijs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OnderwijsRepository onderwijsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOnderwijsMockMvc;

    private Onderwijs onderwijs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderwijs createEntity(EntityManager em) {
        Onderwijs onderwijs = new Onderwijs();
        return onderwijs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderwijs createUpdatedEntity(EntityManager em) {
        Onderwijs onderwijs = new Onderwijs();
        return onderwijs;
    }

    @BeforeEach
    public void initTest() {
        onderwijs = createEntity(em);
    }

    @Test
    @Transactional
    void createOnderwijs() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Onderwijs
        var returnedOnderwijs = om.readValue(
            restOnderwijsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderwijs)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Onderwijs.class
        );

        // Validate the Onderwijs in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOnderwijsUpdatableFieldsEquals(returnedOnderwijs, getPersistedOnderwijs(returnedOnderwijs));
    }

    @Test
    @Transactional
    void createOnderwijsWithExistingId() throws Exception {
        // Create the Onderwijs with an existing ID
        onderwijs.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOnderwijsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderwijs)))
            .andExpect(status().isBadRequest());

        // Validate the Onderwijs in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOnderwijs() throws Exception {
        // Initialize the database
        onderwijsRepository.saveAndFlush(onderwijs);

        // Get all the onderwijsList
        restOnderwijsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(onderwijs.getId().intValue())));
    }

    @Test
    @Transactional
    void getOnderwijs() throws Exception {
        // Initialize the database
        onderwijsRepository.saveAndFlush(onderwijs);

        // Get the onderwijs
        restOnderwijsMockMvc
            .perform(get(ENTITY_API_URL_ID, onderwijs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(onderwijs.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOnderwijs() throws Exception {
        // Get the onderwijs
        restOnderwijsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteOnderwijs() throws Exception {
        // Initialize the database
        onderwijsRepository.saveAndFlush(onderwijs);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the onderwijs
        restOnderwijsMockMvc
            .perform(delete(ENTITY_API_URL_ID, onderwijs.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return onderwijsRepository.count();
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

    protected Onderwijs getPersistedOnderwijs(Onderwijs onderwijs) {
        return onderwijsRepository.findById(onderwijs.getId()).orElseThrow();
    }

    protected void assertPersistedOnderwijsToMatchAllProperties(Onderwijs expectedOnderwijs) {
        assertOnderwijsAllPropertiesEquals(expectedOnderwijs, getPersistedOnderwijs(expectedOnderwijs));
    }

    protected void assertPersistedOnderwijsToMatchUpdatableProperties(Onderwijs expectedOnderwijs) {
        assertOnderwijsAllUpdatablePropertiesEquals(expectedOnderwijs, getPersistedOnderwijs(expectedOnderwijs));
    }
}
