package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KlachtleerlingenvervoerAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Klachtleerlingenvervoer;
import nl.ritense.demo.repository.KlachtleerlingenvervoerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KlachtleerlingenvervoerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KlachtleerlingenvervoerResourceIT {

    private static final String ENTITY_API_URL = "/api/klachtleerlingenvervoers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KlachtleerlingenvervoerRepository klachtleerlingenvervoerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKlachtleerlingenvervoerMockMvc;

    private Klachtleerlingenvervoer klachtleerlingenvervoer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Klachtleerlingenvervoer createEntity(EntityManager em) {
        Klachtleerlingenvervoer klachtleerlingenvervoer = new Klachtleerlingenvervoer();
        return klachtleerlingenvervoer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Klachtleerlingenvervoer createUpdatedEntity(EntityManager em) {
        Klachtleerlingenvervoer klachtleerlingenvervoer = new Klachtleerlingenvervoer();
        return klachtleerlingenvervoer;
    }

    @BeforeEach
    public void initTest() {
        klachtleerlingenvervoer = createEntity(em);
    }

    @Test
    @Transactional
    void createKlachtleerlingenvervoer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Klachtleerlingenvervoer
        var returnedKlachtleerlingenvervoer = om.readValue(
            restKlachtleerlingenvervoerMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(klachtleerlingenvervoer))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Klachtleerlingenvervoer.class
        );

        // Validate the Klachtleerlingenvervoer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKlachtleerlingenvervoerUpdatableFieldsEquals(
            returnedKlachtleerlingenvervoer,
            getPersistedKlachtleerlingenvervoer(returnedKlachtleerlingenvervoer)
        );
    }

    @Test
    @Transactional
    void createKlachtleerlingenvervoerWithExistingId() throws Exception {
        // Create the Klachtleerlingenvervoer with an existing ID
        klachtleerlingenvervoer.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKlachtleerlingenvervoerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(klachtleerlingenvervoer)))
            .andExpect(status().isBadRequest());

        // Validate the Klachtleerlingenvervoer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKlachtleerlingenvervoers() throws Exception {
        // Initialize the database
        klachtleerlingenvervoerRepository.saveAndFlush(klachtleerlingenvervoer);

        // Get all the klachtleerlingenvervoerList
        restKlachtleerlingenvervoerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(klachtleerlingenvervoer.getId().intValue())));
    }

    @Test
    @Transactional
    void getKlachtleerlingenvervoer() throws Exception {
        // Initialize the database
        klachtleerlingenvervoerRepository.saveAndFlush(klachtleerlingenvervoer);

        // Get the klachtleerlingenvervoer
        restKlachtleerlingenvervoerMockMvc
            .perform(get(ENTITY_API_URL_ID, klachtleerlingenvervoer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(klachtleerlingenvervoer.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingKlachtleerlingenvervoer() throws Exception {
        // Get the klachtleerlingenvervoer
        restKlachtleerlingenvervoerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteKlachtleerlingenvervoer() throws Exception {
        // Initialize the database
        klachtleerlingenvervoerRepository.saveAndFlush(klachtleerlingenvervoer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the klachtleerlingenvervoer
        restKlachtleerlingenvervoerMockMvc
            .perform(delete(ENTITY_API_URL_ID, klachtleerlingenvervoer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return klachtleerlingenvervoerRepository.count();
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

    protected Klachtleerlingenvervoer getPersistedKlachtleerlingenvervoer(Klachtleerlingenvervoer klachtleerlingenvervoer) {
        return klachtleerlingenvervoerRepository.findById(klachtleerlingenvervoer.getId()).orElseThrow();
    }

    protected void assertPersistedKlachtleerlingenvervoerToMatchAllProperties(Klachtleerlingenvervoer expectedKlachtleerlingenvervoer) {
        assertKlachtleerlingenvervoerAllPropertiesEquals(
            expectedKlachtleerlingenvervoer,
            getPersistedKlachtleerlingenvervoer(expectedKlachtleerlingenvervoer)
        );
    }

    protected void assertPersistedKlachtleerlingenvervoerToMatchUpdatableProperties(
        Klachtleerlingenvervoer expectedKlachtleerlingenvervoer
    ) {
        assertKlachtleerlingenvervoerAllUpdatablePropertiesEquals(
            expectedKlachtleerlingenvervoer,
            getPersistedKlachtleerlingenvervoer(expectedKlachtleerlingenvervoer)
        );
    }
}
