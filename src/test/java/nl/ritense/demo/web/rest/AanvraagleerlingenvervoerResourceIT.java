package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AanvraagleerlingenvervoerAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Aanvraagleerlingenvervoer;
import nl.ritense.demo.repository.AanvraagleerlingenvervoerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AanvraagleerlingenvervoerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AanvraagleerlingenvervoerResourceIT {

    private static final String ENTITY_API_URL = "/api/aanvraagleerlingenvervoers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AanvraagleerlingenvervoerRepository aanvraagleerlingenvervoerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAanvraagleerlingenvervoerMockMvc;

    private Aanvraagleerlingenvervoer aanvraagleerlingenvervoer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanvraagleerlingenvervoer createEntity(EntityManager em) {
        Aanvraagleerlingenvervoer aanvraagleerlingenvervoer = new Aanvraagleerlingenvervoer();
        return aanvraagleerlingenvervoer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanvraagleerlingenvervoer createUpdatedEntity(EntityManager em) {
        Aanvraagleerlingenvervoer aanvraagleerlingenvervoer = new Aanvraagleerlingenvervoer();
        return aanvraagleerlingenvervoer;
    }

    @BeforeEach
    public void initTest() {
        aanvraagleerlingenvervoer = createEntity(em);
    }

    @Test
    @Transactional
    void createAanvraagleerlingenvervoer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aanvraagleerlingenvervoer
        var returnedAanvraagleerlingenvervoer = om.readValue(
            restAanvraagleerlingenvervoerMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraagleerlingenvervoer))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aanvraagleerlingenvervoer.class
        );

        // Validate the Aanvraagleerlingenvervoer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAanvraagleerlingenvervoerUpdatableFieldsEquals(
            returnedAanvraagleerlingenvervoer,
            getPersistedAanvraagleerlingenvervoer(returnedAanvraagleerlingenvervoer)
        );
    }

    @Test
    @Transactional
    void createAanvraagleerlingenvervoerWithExistingId() throws Exception {
        // Create the Aanvraagleerlingenvervoer with an existing ID
        aanvraagleerlingenvervoer.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAanvraagleerlingenvervoerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraagleerlingenvervoer)))
            .andExpect(status().isBadRequest());

        // Validate the Aanvraagleerlingenvervoer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAanvraagleerlingenvervoers() throws Exception {
        // Initialize the database
        aanvraagleerlingenvervoerRepository.saveAndFlush(aanvraagleerlingenvervoer);

        // Get all the aanvraagleerlingenvervoerList
        restAanvraagleerlingenvervoerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aanvraagleerlingenvervoer.getId().intValue())));
    }

    @Test
    @Transactional
    void getAanvraagleerlingenvervoer() throws Exception {
        // Initialize the database
        aanvraagleerlingenvervoerRepository.saveAndFlush(aanvraagleerlingenvervoer);

        // Get the aanvraagleerlingenvervoer
        restAanvraagleerlingenvervoerMockMvc
            .perform(get(ENTITY_API_URL_ID, aanvraagleerlingenvervoer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aanvraagleerlingenvervoer.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAanvraagleerlingenvervoer() throws Exception {
        // Get the aanvraagleerlingenvervoer
        restAanvraagleerlingenvervoerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteAanvraagleerlingenvervoer() throws Exception {
        // Initialize the database
        aanvraagleerlingenvervoerRepository.saveAndFlush(aanvraagleerlingenvervoer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aanvraagleerlingenvervoer
        restAanvraagleerlingenvervoerMockMvc
            .perform(delete(ENTITY_API_URL_ID, aanvraagleerlingenvervoer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aanvraagleerlingenvervoerRepository.count();
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

    protected Aanvraagleerlingenvervoer getPersistedAanvraagleerlingenvervoer(Aanvraagleerlingenvervoer aanvraagleerlingenvervoer) {
        return aanvraagleerlingenvervoerRepository.findById(aanvraagleerlingenvervoer.getId()).orElseThrow();
    }

    protected void assertPersistedAanvraagleerlingenvervoerToMatchAllProperties(
        Aanvraagleerlingenvervoer expectedAanvraagleerlingenvervoer
    ) {
        assertAanvraagleerlingenvervoerAllPropertiesEquals(
            expectedAanvraagleerlingenvervoer,
            getPersistedAanvraagleerlingenvervoer(expectedAanvraagleerlingenvervoer)
        );
    }

    protected void assertPersistedAanvraagleerlingenvervoerToMatchUpdatableProperties(
        Aanvraagleerlingenvervoer expectedAanvraagleerlingenvervoer
    ) {
        assertAanvraagleerlingenvervoerAllUpdatablePropertiesEquals(
            expectedAanvraagleerlingenvervoer,
            getPersistedAanvraagleerlingenvervoer(expectedAanvraagleerlingenvervoer)
        );
    }
}
