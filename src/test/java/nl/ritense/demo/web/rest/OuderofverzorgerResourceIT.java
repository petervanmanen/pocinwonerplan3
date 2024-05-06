package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OuderofverzorgerAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Ouderofverzorger;
import nl.ritense.demo.repository.OuderofverzorgerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OuderofverzorgerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OuderofverzorgerResourceIT {

    private static final String ENTITY_API_URL = "/api/ouderofverzorgers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OuderofverzorgerRepository ouderofverzorgerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOuderofverzorgerMockMvc;

    private Ouderofverzorger ouderofverzorger;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ouderofverzorger createEntity(EntityManager em) {
        Ouderofverzorger ouderofverzorger = new Ouderofverzorger();
        return ouderofverzorger;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ouderofverzorger createUpdatedEntity(EntityManager em) {
        Ouderofverzorger ouderofverzorger = new Ouderofverzorger();
        return ouderofverzorger;
    }

    @BeforeEach
    public void initTest() {
        ouderofverzorger = createEntity(em);
    }

    @Test
    @Transactional
    void createOuderofverzorger() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ouderofverzorger
        var returnedOuderofverzorger = om.readValue(
            restOuderofverzorgerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ouderofverzorger)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Ouderofverzorger.class
        );

        // Validate the Ouderofverzorger in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOuderofverzorgerUpdatableFieldsEquals(returnedOuderofverzorger, getPersistedOuderofverzorger(returnedOuderofverzorger));
    }

    @Test
    @Transactional
    void createOuderofverzorgerWithExistingId() throws Exception {
        // Create the Ouderofverzorger with an existing ID
        ouderofverzorger.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOuderofverzorgerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ouderofverzorger)))
            .andExpect(status().isBadRequest());

        // Validate the Ouderofverzorger in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOuderofverzorgers() throws Exception {
        // Initialize the database
        ouderofverzorgerRepository.saveAndFlush(ouderofverzorger);

        // Get all the ouderofverzorgerList
        restOuderofverzorgerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ouderofverzorger.getId().intValue())));
    }

    @Test
    @Transactional
    void getOuderofverzorger() throws Exception {
        // Initialize the database
        ouderofverzorgerRepository.saveAndFlush(ouderofverzorger);

        // Get the ouderofverzorger
        restOuderofverzorgerMockMvc
            .perform(get(ENTITY_API_URL_ID, ouderofverzorger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ouderofverzorger.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOuderofverzorger() throws Exception {
        // Get the ouderofverzorger
        restOuderofverzorgerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteOuderofverzorger() throws Exception {
        // Initialize the database
        ouderofverzorgerRepository.saveAndFlush(ouderofverzorger);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ouderofverzorger
        restOuderofverzorgerMockMvc
            .perform(delete(ENTITY_API_URL_ID, ouderofverzorger.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ouderofverzorgerRepository.count();
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

    protected Ouderofverzorger getPersistedOuderofverzorger(Ouderofverzorger ouderofverzorger) {
        return ouderofverzorgerRepository.findById(ouderofverzorger.getId()).orElseThrow();
    }

    protected void assertPersistedOuderofverzorgerToMatchAllProperties(Ouderofverzorger expectedOuderofverzorger) {
        assertOuderofverzorgerAllPropertiesEquals(expectedOuderofverzorger, getPersistedOuderofverzorger(expectedOuderofverzorger));
    }

    protected void assertPersistedOuderofverzorgerToMatchUpdatableProperties(Ouderofverzorger expectedOuderofverzorger) {
        assertOuderofverzorgerAllUpdatablePropertiesEquals(
            expectedOuderofverzorger,
            getPersistedOuderofverzorger(expectedOuderofverzorger)
        );
    }
}
