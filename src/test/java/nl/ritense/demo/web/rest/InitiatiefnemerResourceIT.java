package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.InitiatiefnemerAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Initiatiefnemer;
import nl.ritense.demo.repository.InitiatiefnemerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InitiatiefnemerResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class InitiatiefnemerResourceIT {

    private static final String ENTITY_API_URL = "/api/initiatiefnemers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InitiatiefnemerRepository initiatiefnemerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInitiatiefnemerMockMvc;

    private Initiatiefnemer initiatiefnemer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Initiatiefnemer createEntity(EntityManager em) {
        Initiatiefnemer initiatiefnemer = new Initiatiefnemer();
        return initiatiefnemer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Initiatiefnemer createUpdatedEntity(EntityManager em) {
        Initiatiefnemer initiatiefnemer = new Initiatiefnemer();
        return initiatiefnemer;
    }

    @BeforeEach
    public void initTest() {
        initiatiefnemer = createEntity(em);
    }

    @Test
    @Transactional
    void createInitiatiefnemer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Initiatiefnemer
        var returnedInitiatiefnemer = om.readValue(
            restInitiatiefnemerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(initiatiefnemer)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Initiatiefnemer.class
        );

        // Validate the Initiatiefnemer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInitiatiefnemerUpdatableFieldsEquals(returnedInitiatiefnemer, getPersistedInitiatiefnemer(returnedInitiatiefnemer));
    }

    @Test
    @Transactional
    void createInitiatiefnemerWithExistingId() throws Exception {
        // Create the Initiatiefnemer with an existing ID
        initiatiefnemer.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInitiatiefnemerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(initiatiefnemer)))
            .andExpect(status().isBadRequest());

        // Validate the Initiatiefnemer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInitiatiefnemers() throws Exception {
        // Initialize the database
        initiatiefnemerRepository.saveAndFlush(initiatiefnemer);

        // Get all the initiatiefnemerList
        restInitiatiefnemerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(initiatiefnemer.getId().intValue())));
    }

    @Test
    @Transactional
    void getInitiatiefnemer() throws Exception {
        // Initialize the database
        initiatiefnemerRepository.saveAndFlush(initiatiefnemer);

        // Get the initiatiefnemer
        restInitiatiefnemerMockMvc
            .perform(get(ENTITY_API_URL_ID, initiatiefnemer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(initiatiefnemer.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingInitiatiefnemer() throws Exception {
        // Get the initiatiefnemer
        restInitiatiefnemerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteInitiatiefnemer() throws Exception {
        // Initialize the database
        initiatiefnemerRepository.saveAndFlush(initiatiefnemer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the initiatiefnemer
        restInitiatiefnemerMockMvc
            .perform(delete(ENTITY_API_URL_ID, initiatiefnemer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return initiatiefnemerRepository.count();
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

    protected Initiatiefnemer getPersistedInitiatiefnemer(Initiatiefnemer initiatiefnemer) {
        return initiatiefnemerRepository.findById(initiatiefnemer.getId()).orElseThrow();
    }

    protected void assertPersistedInitiatiefnemerToMatchAllProperties(Initiatiefnemer expectedInitiatiefnemer) {
        assertInitiatiefnemerAllPropertiesEquals(expectedInitiatiefnemer, getPersistedInitiatiefnemer(expectedInitiatiefnemer));
    }

    protected void assertPersistedInitiatiefnemerToMatchUpdatableProperties(Initiatiefnemer expectedInitiatiefnemer) {
        assertInitiatiefnemerAllUpdatablePropertiesEquals(expectedInitiatiefnemer, getPersistedInitiatiefnemer(expectedInitiatiefnemer));
    }
}
