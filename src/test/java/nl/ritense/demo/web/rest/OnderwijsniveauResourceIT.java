package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OnderwijsniveauAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Onderwijsniveau;
import nl.ritense.demo.repository.OnderwijsniveauRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OnderwijsniveauResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OnderwijsniveauResourceIT {

    private static final String ENTITY_API_URL = "/api/onderwijsniveaus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OnderwijsniveauRepository onderwijsniveauRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOnderwijsniveauMockMvc;

    private Onderwijsniveau onderwijsniveau;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderwijsniveau createEntity(EntityManager em) {
        Onderwijsniveau onderwijsniveau = new Onderwijsniveau();
        return onderwijsniveau;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderwijsniveau createUpdatedEntity(EntityManager em) {
        Onderwijsniveau onderwijsniveau = new Onderwijsniveau();
        return onderwijsniveau;
    }

    @BeforeEach
    public void initTest() {
        onderwijsniveau = createEntity(em);
    }

    @Test
    @Transactional
    void createOnderwijsniveau() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Onderwijsniveau
        var returnedOnderwijsniveau = om.readValue(
            restOnderwijsniveauMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderwijsniveau)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Onderwijsniveau.class
        );

        // Validate the Onderwijsniveau in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOnderwijsniveauUpdatableFieldsEquals(returnedOnderwijsniveau, getPersistedOnderwijsniveau(returnedOnderwijsniveau));
    }

    @Test
    @Transactional
    void createOnderwijsniveauWithExistingId() throws Exception {
        // Create the Onderwijsniveau with an existing ID
        onderwijsniveau.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOnderwijsniveauMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderwijsniveau)))
            .andExpect(status().isBadRequest());

        // Validate the Onderwijsniveau in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOnderwijsniveaus() throws Exception {
        // Initialize the database
        onderwijsniveauRepository.saveAndFlush(onderwijsniveau);

        // Get all the onderwijsniveauList
        restOnderwijsniveauMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(onderwijsniveau.getId().intValue())));
    }

    @Test
    @Transactional
    void getOnderwijsniveau() throws Exception {
        // Initialize the database
        onderwijsniveauRepository.saveAndFlush(onderwijsniveau);

        // Get the onderwijsniveau
        restOnderwijsniveauMockMvc
            .perform(get(ENTITY_API_URL_ID, onderwijsniveau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(onderwijsniveau.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOnderwijsniveau() throws Exception {
        // Get the onderwijsniveau
        restOnderwijsniveauMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteOnderwijsniveau() throws Exception {
        // Initialize the database
        onderwijsniveauRepository.saveAndFlush(onderwijsniveau);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the onderwijsniveau
        restOnderwijsniveauMockMvc
            .perform(delete(ENTITY_API_URL_ID, onderwijsniveau.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return onderwijsniveauRepository.count();
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

    protected Onderwijsniveau getPersistedOnderwijsniveau(Onderwijsniveau onderwijsniveau) {
        return onderwijsniveauRepository.findById(onderwijsniveau.getId()).orElseThrow();
    }

    protected void assertPersistedOnderwijsniveauToMatchAllProperties(Onderwijsniveau expectedOnderwijsniveau) {
        assertOnderwijsniveauAllPropertiesEquals(expectedOnderwijsniveau, getPersistedOnderwijsniveau(expectedOnderwijsniveau));
    }

    protected void assertPersistedOnderwijsniveauToMatchUpdatableProperties(Onderwijsniveau expectedOnderwijsniveau) {
        assertOnderwijsniveauAllUpdatablePropertiesEquals(expectedOnderwijsniveau, getPersistedOnderwijsniveau(expectedOnderwijsniveau));
    }
}
