package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.InburgeringstrajectAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Inburgeringstraject;
import nl.ritense.demo.repository.InburgeringstrajectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InburgeringstrajectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InburgeringstrajectResourceIT {

    private static final String ENTITY_API_URL = "/api/inburgeringstrajects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InburgeringstrajectRepository inburgeringstrajectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInburgeringstrajectMockMvc;

    private Inburgeringstraject inburgeringstraject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inburgeringstraject createEntity(EntityManager em) {
        Inburgeringstraject inburgeringstraject = new Inburgeringstraject();
        return inburgeringstraject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inburgeringstraject createUpdatedEntity(EntityManager em) {
        Inburgeringstraject inburgeringstraject = new Inburgeringstraject();
        return inburgeringstraject;
    }

    @BeforeEach
    public void initTest() {
        inburgeringstraject = createEntity(em);
    }

    @Test
    @Transactional
    void createInburgeringstraject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Inburgeringstraject
        var returnedInburgeringstraject = om.readValue(
            restInburgeringstrajectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inburgeringstraject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Inburgeringstraject.class
        );

        // Validate the Inburgeringstraject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInburgeringstrajectUpdatableFieldsEquals(
            returnedInburgeringstraject,
            getPersistedInburgeringstraject(returnedInburgeringstraject)
        );
    }

    @Test
    @Transactional
    void createInburgeringstrajectWithExistingId() throws Exception {
        // Create the Inburgeringstraject with an existing ID
        inburgeringstraject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInburgeringstrajectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inburgeringstraject)))
            .andExpect(status().isBadRequest());

        // Validate the Inburgeringstraject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInburgeringstrajects() throws Exception {
        // Initialize the database
        inburgeringstrajectRepository.saveAndFlush(inburgeringstraject);

        // Get all the inburgeringstrajectList
        restInburgeringstrajectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inburgeringstraject.getId().intValue())));
    }

    @Test
    @Transactional
    void getInburgeringstraject() throws Exception {
        // Initialize the database
        inburgeringstrajectRepository.saveAndFlush(inburgeringstraject);

        // Get the inburgeringstraject
        restInburgeringstrajectMockMvc
            .perform(get(ENTITY_API_URL_ID, inburgeringstraject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inburgeringstraject.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingInburgeringstraject() throws Exception {
        // Get the inburgeringstraject
        restInburgeringstrajectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteInburgeringstraject() throws Exception {
        // Initialize the database
        inburgeringstrajectRepository.saveAndFlush(inburgeringstraject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inburgeringstraject
        restInburgeringstrajectMockMvc
            .perform(delete(ENTITY_API_URL_ID, inburgeringstraject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inburgeringstrajectRepository.count();
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

    protected Inburgeringstraject getPersistedInburgeringstraject(Inburgeringstraject inburgeringstraject) {
        return inburgeringstrajectRepository.findById(inburgeringstraject.getId()).orElseThrow();
    }

    protected void assertPersistedInburgeringstrajectToMatchAllProperties(Inburgeringstraject expectedInburgeringstraject) {
        assertInburgeringstrajectAllPropertiesEquals(
            expectedInburgeringstraject,
            getPersistedInburgeringstraject(expectedInburgeringstraject)
        );
    }

    protected void assertPersistedInburgeringstrajectToMatchUpdatableProperties(Inburgeringstraject expectedInburgeringstraject) {
        assertInburgeringstrajectAllUpdatablePropertiesEquals(
            expectedInburgeringstraject,
            getPersistedInburgeringstraject(expectedInburgeringstraject)
        );
    }
}
