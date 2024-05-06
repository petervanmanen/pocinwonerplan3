package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NotarielestatusAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Notarielestatus;
import nl.ritense.demo.repository.NotarielestatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NotarielestatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotarielestatusResourceIT {

    private static final String ENTITY_API_URL = "/api/notarielestatuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NotarielestatusRepository notarielestatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotarielestatusMockMvc;

    private Notarielestatus notarielestatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notarielestatus createEntity(EntityManager em) {
        Notarielestatus notarielestatus = new Notarielestatus();
        return notarielestatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notarielestatus createUpdatedEntity(EntityManager em) {
        Notarielestatus notarielestatus = new Notarielestatus();
        return notarielestatus;
    }

    @BeforeEach
    public void initTest() {
        notarielestatus = createEntity(em);
    }

    @Test
    @Transactional
    void createNotarielestatus() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Notarielestatus
        var returnedNotarielestatus = om.readValue(
            restNotarielestatusMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notarielestatus)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Notarielestatus.class
        );

        // Validate the Notarielestatus in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNotarielestatusUpdatableFieldsEquals(returnedNotarielestatus, getPersistedNotarielestatus(returnedNotarielestatus));
    }

    @Test
    @Transactional
    void createNotarielestatusWithExistingId() throws Exception {
        // Create the Notarielestatus with an existing ID
        notarielestatus.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotarielestatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notarielestatus)))
            .andExpect(status().isBadRequest());

        // Validate the Notarielestatus in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNotarielestatuses() throws Exception {
        // Initialize the database
        notarielestatusRepository.saveAndFlush(notarielestatus);

        // Get all the notarielestatusList
        restNotarielestatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notarielestatus.getId().intValue())));
    }

    @Test
    @Transactional
    void getNotarielestatus() throws Exception {
        // Initialize the database
        notarielestatusRepository.saveAndFlush(notarielestatus);

        // Get the notarielestatus
        restNotarielestatusMockMvc
            .perform(get(ENTITY_API_URL_ID, notarielestatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notarielestatus.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingNotarielestatus() throws Exception {
        // Get the notarielestatus
        restNotarielestatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteNotarielestatus() throws Exception {
        // Initialize the database
        notarielestatusRepository.saveAndFlush(notarielestatus);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the notarielestatus
        restNotarielestatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, notarielestatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return notarielestatusRepository.count();
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

    protected Notarielestatus getPersistedNotarielestatus(Notarielestatus notarielestatus) {
        return notarielestatusRepository.findById(notarielestatus.getId()).orElseThrow();
    }

    protected void assertPersistedNotarielestatusToMatchAllProperties(Notarielestatus expectedNotarielestatus) {
        assertNotarielestatusAllPropertiesEquals(expectedNotarielestatus, getPersistedNotarielestatus(expectedNotarielestatus));
    }

    protected void assertPersistedNotarielestatusToMatchUpdatableProperties(Notarielestatus expectedNotarielestatus) {
        assertNotarielestatusAllUpdatablePropertiesEquals(expectedNotarielestatus, getPersistedNotarielestatus(expectedNotarielestatus));
    }
}
