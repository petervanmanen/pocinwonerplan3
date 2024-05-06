package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GemachtigdeAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Gemachtigde;
import nl.ritense.demo.repository.GemachtigdeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GemachtigdeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GemachtigdeResourceIT {

    private static final String ENTITY_API_URL = "/api/gemachtigdes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GemachtigdeRepository gemachtigdeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGemachtigdeMockMvc;

    private Gemachtigde gemachtigde;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gemachtigde createEntity(EntityManager em) {
        Gemachtigde gemachtigde = new Gemachtigde();
        return gemachtigde;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gemachtigde createUpdatedEntity(EntityManager em) {
        Gemachtigde gemachtigde = new Gemachtigde();
        return gemachtigde;
    }

    @BeforeEach
    public void initTest() {
        gemachtigde = createEntity(em);
    }

    @Test
    @Transactional
    void createGemachtigde() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Gemachtigde
        var returnedGemachtigde = om.readValue(
            restGemachtigdeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gemachtigde)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Gemachtigde.class
        );

        // Validate the Gemachtigde in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGemachtigdeUpdatableFieldsEquals(returnedGemachtigde, getPersistedGemachtigde(returnedGemachtigde));
    }

    @Test
    @Transactional
    void createGemachtigdeWithExistingId() throws Exception {
        // Create the Gemachtigde with an existing ID
        gemachtigde.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGemachtigdeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gemachtigde)))
            .andExpect(status().isBadRequest());

        // Validate the Gemachtigde in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGemachtigdes() throws Exception {
        // Initialize the database
        gemachtigdeRepository.saveAndFlush(gemachtigde);

        // Get all the gemachtigdeList
        restGemachtigdeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gemachtigde.getId().intValue())));
    }

    @Test
    @Transactional
    void getGemachtigde() throws Exception {
        // Initialize the database
        gemachtigdeRepository.saveAndFlush(gemachtigde);

        // Get the gemachtigde
        restGemachtigdeMockMvc
            .perform(get(ENTITY_API_URL_ID, gemachtigde.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gemachtigde.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingGemachtigde() throws Exception {
        // Get the gemachtigde
        restGemachtigdeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteGemachtigde() throws Exception {
        // Initialize the database
        gemachtigdeRepository.saveAndFlush(gemachtigde);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gemachtigde
        restGemachtigdeMockMvc
            .perform(delete(ENTITY_API_URL_ID, gemachtigde.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gemachtigdeRepository.count();
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

    protected Gemachtigde getPersistedGemachtigde(Gemachtigde gemachtigde) {
        return gemachtigdeRepository.findById(gemachtigde.getId()).orElseThrow();
    }

    protected void assertPersistedGemachtigdeToMatchAllProperties(Gemachtigde expectedGemachtigde) {
        assertGemachtigdeAllPropertiesEquals(expectedGemachtigde, getPersistedGemachtigde(expectedGemachtigde));
    }

    protected void assertPersistedGemachtigdeToMatchUpdatableProperties(Gemachtigde expectedGemachtigde) {
        assertGemachtigdeAllUpdatablePropertiesEquals(expectedGemachtigde, getPersistedGemachtigde(expectedGemachtigde));
    }
}
