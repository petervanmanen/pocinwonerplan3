package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EobjecttypeeAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Eobjecttypee;
import nl.ritense.demo.repository.EobjecttypeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EobjecttypeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EobjecttypeeResourceIT {

    private static final String ENTITY_API_URL = "/api/eobjecttypees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EobjecttypeeRepository eobjecttypeeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEobjecttypeeMockMvc;

    private Eobjecttypee eobjecttypee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjecttypee createEntity(EntityManager em) {
        Eobjecttypee eobjecttypee = new Eobjecttypee();
        return eobjecttypee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjecttypee createUpdatedEntity(EntityManager em) {
        Eobjecttypee eobjecttypee = new Eobjecttypee();
        return eobjecttypee;
    }

    @BeforeEach
    public void initTest() {
        eobjecttypee = createEntity(em);
    }

    @Test
    @Transactional
    void createEobjecttypee() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Eobjecttypee
        var returnedEobjecttypee = om.readValue(
            restEobjecttypeeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjecttypee)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Eobjecttypee.class
        );

        // Validate the Eobjecttypee in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEobjecttypeeUpdatableFieldsEquals(returnedEobjecttypee, getPersistedEobjecttypee(returnedEobjecttypee));
    }

    @Test
    @Transactional
    void createEobjecttypeeWithExistingId() throws Exception {
        // Create the Eobjecttypee with an existing ID
        eobjecttypee.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEobjecttypeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjecttypee)))
            .andExpect(status().isBadRequest());

        // Validate the Eobjecttypee in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEobjecttypees() throws Exception {
        // Initialize the database
        eobjecttypeeRepository.saveAndFlush(eobjecttypee);

        // Get all the eobjecttypeeList
        restEobjecttypeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eobjecttypee.getId().intValue())));
    }

    @Test
    @Transactional
    void getEobjecttypee() throws Exception {
        // Initialize the database
        eobjecttypeeRepository.saveAndFlush(eobjecttypee);

        // Get the eobjecttypee
        restEobjecttypeeMockMvc
            .perform(get(ENTITY_API_URL_ID, eobjecttypee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eobjecttypee.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingEobjecttypee() throws Exception {
        // Get the eobjecttypee
        restEobjecttypeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteEobjecttypee() throws Exception {
        // Initialize the database
        eobjecttypeeRepository.saveAndFlush(eobjecttypee);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eobjecttypee
        restEobjecttypeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, eobjecttypee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return eobjecttypeeRepository.count();
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

    protected Eobjecttypee getPersistedEobjecttypee(Eobjecttypee eobjecttypee) {
        return eobjecttypeeRepository.findById(eobjecttypee.getId()).orElseThrow();
    }

    protected void assertPersistedEobjecttypeeToMatchAllProperties(Eobjecttypee expectedEobjecttypee) {
        assertEobjecttypeeAllPropertiesEquals(expectedEobjecttypee, getPersistedEobjecttypee(expectedEobjecttypee));
    }

    protected void assertPersistedEobjecttypeeToMatchUpdatableProperties(Eobjecttypee expectedEobjecttypee) {
        assertEobjecttypeeAllUpdatablePropertiesEquals(expectedEobjecttypee, getPersistedEobjecttypee(expectedEobjecttypee));
    }
}
