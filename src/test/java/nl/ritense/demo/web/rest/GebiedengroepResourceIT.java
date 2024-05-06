package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GebiedengroepAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Gebiedengroep;
import nl.ritense.demo.repository.GebiedengroepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GebiedengroepResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GebiedengroepResourceIT {

    private static final String ENTITY_API_URL = "/api/gebiedengroeps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GebiedengroepRepository gebiedengroepRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGebiedengroepMockMvc;

    private Gebiedengroep gebiedengroep;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gebiedengroep createEntity(EntityManager em) {
        Gebiedengroep gebiedengroep = new Gebiedengroep();
        return gebiedengroep;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gebiedengroep createUpdatedEntity(EntityManager em) {
        Gebiedengroep gebiedengroep = new Gebiedengroep();
        return gebiedengroep;
    }

    @BeforeEach
    public void initTest() {
        gebiedengroep = createEntity(em);
    }

    @Test
    @Transactional
    void createGebiedengroep() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Gebiedengroep
        var returnedGebiedengroep = om.readValue(
            restGebiedengroepMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebiedengroep)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Gebiedengroep.class
        );

        // Validate the Gebiedengroep in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGebiedengroepUpdatableFieldsEquals(returnedGebiedengroep, getPersistedGebiedengroep(returnedGebiedengroep));
    }

    @Test
    @Transactional
    void createGebiedengroepWithExistingId() throws Exception {
        // Create the Gebiedengroep with an existing ID
        gebiedengroep.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGebiedengroepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebiedengroep)))
            .andExpect(status().isBadRequest());

        // Validate the Gebiedengroep in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGebiedengroeps() throws Exception {
        // Initialize the database
        gebiedengroepRepository.saveAndFlush(gebiedengroep);

        // Get all the gebiedengroepList
        restGebiedengroepMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gebiedengroep.getId().intValue())));
    }

    @Test
    @Transactional
    void getGebiedengroep() throws Exception {
        // Initialize the database
        gebiedengroepRepository.saveAndFlush(gebiedengroep);

        // Get the gebiedengroep
        restGebiedengroepMockMvc
            .perform(get(ENTITY_API_URL_ID, gebiedengroep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gebiedengroep.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingGebiedengroep() throws Exception {
        // Get the gebiedengroep
        restGebiedengroepMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteGebiedengroep() throws Exception {
        // Initialize the database
        gebiedengroepRepository.saveAndFlush(gebiedengroep);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gebiedengroep
        restGebiedengroepMockMvc
            .perform(delete(ENTITY_API_URL_ID, gebiedengroep.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gebiedengroepRepository.count();
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

    protected Gebiedengroep getPersistedGebiedengroep(Gebiedengroep gebiedengroep) {
        return gebiedengroepRepository.findById(gebiedengroep.getId()).orElseThrow();
    }

    protected void assertPersistedGebiedengroepToMatchAllProperties(Gebiedengroep expectedGebiedengroep) {
        assertGebiedengroepAllPropertiesEquals(expectedGebiedengroep, getPersistedGebiedengroep(expectedGebiedengroep));
    }

    protected void assertPersistedGebiedengroepToMatchUpdatableProperties(Gebiedengroep expectedGebiedengroep) {
        assertGebiedengroepAllUpdatablePropertiesEquals(expectedGebiedengroep, getPersistedGebiedengroep(expectedGebiedengroep));
    }
}
