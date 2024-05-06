package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MastAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Mast;
import nl.ritense.demo.repository.MastRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MastResourceIT {

    private static final String ENTITY_API_URL = "/api/masts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MastRepository mastRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMastMockMvc;

    private Mast mast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mast createEntity(EntityManager em) {
        Mast mast = new Mast();
        return mast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mast createUpdatedEntity(EntityManager em) {
        Mast mast = new Mast();
        return mast;
    }

    @BeforeEach
    public void initTest() {
        mast = createEntity(em);
    }

    @Test
    @Transactional
    void createMast() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Mast
        var returnedMast = om.readValue(
            restMastMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mast)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Mast.class
        );

        // Validate the Mast in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMastUpdatableFieldsEquals(returnedMast, getPersistedMast(returnedMast));
    }

    @Test
    @Transactional
    void createMastWithExistingId() throws Exception {
        // Create the Mast with an existing ID
        mast.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMastMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mast)))
            .andExpect(status().isBadRequest());

        // Validate the Mast in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMasts() throws Exception {
        // Initialize the database
        mastRepository.saveAndFlush(mast);

        // Get all the mastList
        restMastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mast.getId().intValue())));
    }

    @Test
    @Transactional
    void getMast() throws Exception {
        // Initialize the database
        mastRepository.saveAndFlush(mast);

        // Get the mast
        restMastMockMvc
            .perform(get(ENTITY_API_URL_ID, mast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mast.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingMast() throws Exception {
        // Get the mast
        restMastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteMast() throws Exception {
        // Initialize the database
        mastRepository.saveAndFlush(mast);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the mast
        restMastMockMvc
            .perform(delete(ENTITY_API_URL_ID, mast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return mastRepository.count();
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

    protected Mast getPersistedMast(Mast mast) {
        return mastRepository.findById(mast.getId()).orElseThrow();
    }

    protected void assertPersistedMastToMatchAllProperties(Mast expectedMast) {
        assertMastAllPropertiesEquals(expectedMast, getPersistedMast(expectedMast));
    }

    protected void assertPersistedMastToMatchUpdatableProperties(Mast expectedMast) {
        assertMastAllUpdatablePropertiesEquals(expectedMast, getPersistedMast(expectedMast));
    }
}
