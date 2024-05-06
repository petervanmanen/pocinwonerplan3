package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.UitgeverAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Uitgever;
import nl.ritense.demo.repository.UitgeverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UitgeverResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UitgeverResourceIT {

    private static final String ENTITY_API_URL = "/api/uitgevers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UitgeverRepository uitgeverRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUitgeverMockMvc;

    private Uitgever uitgever;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitgever createEntity(EntityManager em) {
        Uitgever uitgever = new Uitgever();
        return uitgever;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitgever createUpdatedEntity(EntityManager em) {
        Uitgever uitgever = new Uitgever();
        return uitgever;
    }

    @BeforeEach
    public void initTest() {
        uitgever = createEntity(em);
    }

    @Test
    @Transactional
    void createUitgever() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Uitgever
        var returnedUitgever = om.readValue(
            restUitgeverMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitgever)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Uitgever.class
        );

        // Validate the Uitgever in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertUitgeverUpdatableFieldsEquals(returnedUitgever, getPersistedUitgever(returnedUitgever));
    }

    @Test
    @Transactional
    void createUitgeverWithExistingId() throws Exception {
        // Create the Uitgever with an existing ID
        uitgever.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUitgeverMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitgever)))
            .andExpect(status().isBadRequest());

        // Validate the Uitgever in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUitgevers() throws Exception {
        // Initialize the database
        uitgeverRepository.saveAndFlush(uitgever);

        // Get all the uitgeverList
        restUitgeverMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uitgever.getId().intValue())));
    }

    @Test
    @Transactional
    void getUitgever() throws Exception {
        // Initialize the database
        uitgeverRepository.saveAndFlush(uitgever);

        // Get the uitgever
        restUitgeverMockMvc
            .perform(get(ENTITY_API_URL_ID, uitgever.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uitgever.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingUitgever() throws Exception {
        // Get the uitgever
        restUitgeverMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteUitgever() throws Exception {
        // Initialize the database
        uitgeverRepository.saveAndFlush(uitgever);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the uitgever
        restUitgeverMockMvc
            .perform(delete(ENTITY_API_URL_ID, uitgever.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return uitgeverRepository.count();
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

    protected Uitgever getPersistedUitgever(Uitgever uitgever) {
        return uitgeverRepository.findById(uitgever.getId()).orElseThrow();
    }

    protected void assertPersistedUitgeverToMatchAllProperties(Uitgever expectedUitgever) {
        assertUitgeverAllPropertiesEquals(expectedUitgever, getPersistedUitgever(expectedUitgever));
    }

    protected void assertPersistedUitgeverToMatchUpdatableProperties(Uitgever expectedUitgever) {
        assertUitgeverAllUpdatablePropertiesEquals(expectedUitgever, getPersistedUitgever(expectedUitgever));
    }
}
