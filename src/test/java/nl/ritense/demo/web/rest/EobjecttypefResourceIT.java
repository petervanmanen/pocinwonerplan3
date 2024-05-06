package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EobjecttypefAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Eobjecttypef;
import nl.ritense.demo.repository.EobjecttypefRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EobjecttypefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EobjecttypefResourceIT {

    private static final String ENTITY_API_URL = "/api/eobjecttypefs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EobjecttypefRepository eobjecttypefRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEobjecttypefMockMvc;

    private Eobjecttypef eobjecttypef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjecttypef createEntity(EntityManager em) {
        Eobjecttypef eobjecttypef = new Eobjecttypef();
        return eobjecttypef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjecttypef createUpdatedEntity(EntityManager em) {
        Eobjecttypef eobjecttypef = new Eobjecttypef();
        return eobjecttypef;
    }

    @BeforeEach
    public void initTest() {
        eobjecttypef = createEntity(em);
    }

    @Test
    @Transactional
    void createEobjecttypef() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Eobjecttypef
        var returnedEobjecttypef = om.readValue(
            restEobjecttypefMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjecttypef)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Eobjecttypef.class
        );

        // Validate the Eobjecttypef in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEobjecttypefUpdatableFieldsEquals(returnedEobjecttypef, getPersistedEobjecttypef(returnedEobjecttypef));
    }

    @Test
    @Transactional
    void createEobjecttypefWithExistingId() throws Exception {
        // Create the Eobjecttypef with an existing ID
        eobjecttypef.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEobjecttypefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjecttypef)))
            .andExpect(status().isBadRequest());

        // Validate the Eobjecttypef in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEobjecttypefs() throws Exception {
        // Initialize the database
        eobjecttypefRepository.saveAndFlush(eobjecttypef);

        // Get all the eobjecttypefList
        restEobjecttypefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eobjecttypef.getId().intValue())));
    }

    @Test
    @Transactional
    void getEobjecttypef() throws Exception {
        // Initialize the database
        eobjecttypefRepository.saveAndFlush(eobjecttypef);

        // Get the eobjecttypef
        restEobjecttypefMockMvc
            .perform(get(ENTITY_API_URL_ID, eobjecttypef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eobjecttypef.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingEobjecttypef() throws Exception {
        // Get the eobjecttypef
        restEobjecttypefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteEobjecttypef() throws Exception {
        // Initialize the database
        eobjecttypefRepository.saveAndFlush(eobjecttypef);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eobjecttypef
        restEobjecttypefMockMvc
            .perform(delete(ENTITY_API_URL_ID, eobjecttypef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return eobjecttypefRepository.count();
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

    protected Eobjecttypef getPersistedEobjecttypef(Eobjecttypef eobjecttypef) {
        return eobjecttypefRepository.findById(eobjecttypef.getId()).orElseThrow();
    }

    protected void assertPersistedEobjecttypefToMatchAllProperties(Eobjecttypef expectedEobjecttypef) {
        assertEobjecttypefAllPropertiesEquals(expectedEobjecttypef, getPersistedEobjecttypef(expectedEobjecttypef));
    }

    protected void assertPersistedEobjecttypefToMatchUpdatableProperties(Eobjecttypef expectedEobjecttypef) {
        assertEobjecttypefAllUpdatablePropertiesEquals(expectedEobjecttypef, getPersistedEobjecttypef(expectedEobjecttypef));
    }
}
