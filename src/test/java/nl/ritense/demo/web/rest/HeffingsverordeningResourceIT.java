package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.HeffingsverordeningAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Heffingsverordening;
import nl.ritense.demo.repository.HeffingsverordeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HeffingsverordeningResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HeffingsverordeningResourceIT {

    private static final String ENTITY_API_URL = "/api/heffingsverordenings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HeffingsverordeningRepository heffingsverordeningRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHeffingsverordeningMockMvc;

    private Heffingsverordening heffingsverordening;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Heffingsverordening createEntity(EntityManager em) {
        Heffingsverordening heffingsverordening = new Heffingsverordening();
        return heffingsverordening;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Heffingsverordening createUpdatedEntity(EntityManager em) {
        Heffingsverordening heffingsverordening = new Heffingsverordening();
        return heffingsverordening;
    }

    @BeforeEach
    public void initTest() {
        heffingsverordening = createEntity(em);
    }

    @Test
    @Transactional
    void createHeffingsverordening() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Heffingsverordening
        var returnedHeffingsverordening = om.readValue(
            restHeffingsverordeningMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(heffingsverordening)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Heffingsverordening.class
        );

        // Validate the Heffingsverordening in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHeffingsverordeningUpdatableFieldsEquals(
            returnedHeffingsverordening,
            getPersistedHeffingsverordening(returnedHeffingsverordening)
        );
    }

    @Test
    @Transactional
    void createHeffingsverordeningWithExistingId() throws Exception {
        // Create the Heffingsverordening with an existing ID
        heffingsverordening.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHeffingsverordeningMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(heffingsverordening)))
            .andExpect(status().isBadRequest());

        // Validate the Heffingsverordening in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHeffingsverordenings() throws Exception {
        // Initialize the database
        heffingsverordeningRepository.saveAndFlush(heffingsverordening);

        // Get all the heffingsverordeningList
        restHeffingsverordeningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(heffingsverordening.getId().intValue())));
    }

    @Test
    @Transactional
    void getHeffingsverordening() throws Exception {
        // Initialize the database
        heffingsverordeningRepository.saveAndFlush(heffingsverordening);

        // Get the heffingsverordening
        restHeffingsverordeningMockMvc
            .perform(get(ENTITY_API_URL_ID, heffingsverordening.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(heffingsverordening.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingHeffingsverordening() throws Exception {
        // Get the heffingsverordening
        restHeffingsverordeningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteHeffingsverordening() throws Exception {
        // Initialize the database
        heffingsverordeningRepository.saveAndFlush(heffingsverordening);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the heffingsverordening
        restHeffingsverordeningMockMvc
            .perform(delete(ENTITY_API_URL_ID, heffingsverordening.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return heffingsverordeningRepository.count();
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

    protected Heffingsverordening getPersistedHeffingsverordening(Heffingsverordening heffingsverordening) {
        return heffingsverordeningRepository.findById(heffingsverordening.getId()).orElseThrow();
    }

    protected void assertPersistedHeffingsverordeningToMatchAllProperties(Heffingsverordening expectedHeffingsverordening) {
        assertHeffingsverordeningAllPropertiesEquals(
            expectedHeffingsverordening,
            getPersistedHeffingsverordening(expectedHeffingsverordening)
        );
    }

    protected void assertPersistedHeffingsverordeningToMatchUpdatableProperties(Heffingsverordening expectedHeffingsverordening) {
        assertHeffingsverordeningAllUpdatablePropertiesEquals(
            expectedHeffingsverordening,
            getPersistedHeffingsverordening(expectedHeffingsverordening)
        );
    }
}
