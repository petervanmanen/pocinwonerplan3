package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerkeerslichtAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Verkeerslicht;
import nl.ritense.demo.repository.VerkeerslichtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerkeerslichtResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerkeerslichtResourceIT {

    private static final String ENTITY_API_URL = "/api/verkeerslichts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerkeerslichtRepository verkeerslichtRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerkeerslichtMockMvc;

    private Verkeerslicht verkeerslicht;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verkeerslicht createEntity(EntityManager em) {
        Verkeerslicht verkeerslicht = new Verkeerslicht();
        return verkeerslicht;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verkeerslicht createUpdatedEntity(EntityManager em) {
        Verkeerslicht verkeerslicht = new Verkeerslicht();
        return verkeerslicht;
    }

    @BeforeEach
    public void initTest() {
        verkeerslicht = createEntity(em);
    }

    @Test
    @Transactional
    void createVerkeerslicht() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verkeerslicht
        var returnedVerkeerslicht = om.readValue(
            restVerkeerslichtMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verkeerslicht)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verkeerslicht.class
        );

        // Validate the Verkeerslicht in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerkeerslichtUpdatableFieldsEquals(returnedVerkeerslicht, getPersistedVerkeerslicht(returnedVerkeerslicht));
    }

    @Test
    @Transactional
    void createVerkeerslichtWithExistingId() throws Exception {
        // Create the Verkeerslicht with an existing ID
        verkeerslicht.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerkeerslichtMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verkeerslicht)))
            .andExpect(status().isBadRequest());

        // Validate the Verkeerslicht in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerkeerslichts() throws Exception {
        // Initialize the database
        verkeerslichtRepository.saveAndFlush(verkeerslicht);

        // Get all the verkeerslichtList
        restVerkeerslichtMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verkeerslicht.getId().intValue())));
    }

    @Test
    @Transactional
    void getVerkeerslicht() throws Exception {
        // Initialize the database
        verkeerslichtRepository.saveAndFlush(verkeerslicht);

        // Get the verkeerslicht
        restVerkeerslichtMockMvc
            .perform(get(ENTITY_API_URL_ID, verkeerslicht.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verkeerslicht.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingVerkeerslicht() throws Exception {
        // Get the verkeerslicht
        restVerkeerslichtMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteVerkeerslicht() throws Exception {
        // Initialize the database
        verkeerslichtRepository.saveAndFlush(verkeerslicht);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verkeerslicht
        restVerkeerslichtMockMvc
            .perform(delete(ENTITY_API_URL_ID, verkeerslicht.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verkeerslichtRepository.count();
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

    protected Verkeerslicht getPersistedVerkeerslicht(Verkeerslicht verkeerslicht) {
        return verkeerslichtRepository.findById(verkeerslicht.getId()).orElseThrow();
    }

    protected void assertPersistedVerkeerslichtToMatchAllProperties(Verkeerslicht expectedVerkeerslicht) {
        assertVerkeerslichtAllPropertiesEquals(expectedVerkeerslicht, getPersistedVerkeerslicht(expectedVerkeerslicht));
    }

    protected void assertPersistedVerkeerslichtToMatchUpdatableProperties(Verkeerslicht expectedVerkeerslicht) {
        assertVerkeerslichtAllUpdatablePropertiesEquals(expectedVerkeerslicht, getPersistedVerkeerslicht(expectedVerkeerslicht));
    }
}
