package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RechthebbendeAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Rechthebbende;
import nl.ritense.demo.repository.RechthebbendeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RechthebbendeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RechthebbendeResourceIT {

    private static final String ENTITY_API_URL = "/api/rechthebbendes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RechthebbendeRepository rechthebbendeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRechthebbendeMockMvc;

    private Rechthebbende rechthebbende;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rechthebbende createEntity(EntityManager em) {
        Rechthebbende rechthebbende = new Rechthebbende();
        return rechthebbende;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rechthebbende createUpdatedEntity(EntityManager em) {
        Rechthebbende rechthebbende = new Rechthebbende();
        return rechthebbende;
    }

    @BeforeEach
    public void initTest() {
        rechthebbende = createEntity(em);
    }

    @Test
    @Transactional
    void createRechthebbende() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Rechthebbende
        var returnedRechthebbende = om.readValue(
            restRechthebbendeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rechthebbende)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Rechthebbende.class
        );

        // Validate the Rechthebbende in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRechthebbendeUpdatableFieldsEquals(returnedRechthebbende, getPersistedRechthebbende(returnedRechthebbende));
    }

    @Test
    @Transactional
    void createRechthebbendeWithExistingId() throws Exception {
        // Create the Rechthebbende with an existing ID
        rechthebbende.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRechthebbendeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rechthebbende)))
            .andExpect(status().isBadRequest());

        // Validate the Rechthebbende in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRechthebbendes() throws Exception {
        // Initialize the database
        rechthebbendeRepository.saveAndFlush(rechthebbende);

        // Get all the rechthebbendeList
        restRechthebbendeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rechthebbende.getId().intValue())));
    }

    @Test
    @Transactional
    void getRechthebbende() throws Exception {
        // Initialize the database
        rechthebbendeRepository.saveAndFlush(rechthebbende);

        // Get the rechthebbende
        restRechthebbendeMockMvc
            .perform(get(ENTITY_API_URL_ID, rechthebbende.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rechthebbende.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingRechthebbende() throws Exception {
        // Get the rechthebbende
        restRechthebbendeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteRechthebbende() throws Exception {
        // Initialize the database
        rechthebbendeRepository.saveAndFlush(rechthebbende);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rechthebbende
        restRechthebbendeMockMvc
            .perform(delete(ENTITY_API_URL_ID, rechthebbende.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rechthebbendeRepository.count();
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

    protected Rechthebbende getPersistedRechthebbende(Rechthebbende rechthebbende) {
        return rechthebbendeRepository.findById(rechthebbende.getId()).orElseThrow();
    }

    protected void assertPersistedRechthebbendeToMatchAllProperties(Rechthebbende expectedRechthebbende) {
        assertRechthebbendeAllPropertiesEquals(expectedRechthebbende, getPersistedRechthebbende(expectedRechthebbende));
    }

    protected void assertPersistedRechthebbendeToMatchUpdatableProperties(Rechthebbende expectedRechthebbende) {
        assertRechthebbendeAllUpdatablePropertiesEquals(expectedRechthebbende, getPersistedRechthebbende(expectedRechthebbende));
    }
}
