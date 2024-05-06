package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PuntengroepAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Puntengroep;
import nl.ritense.demo.repository.PuntengroepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PuntengroepResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PuntengroepResourceIT {

    private static final String ENTITY_API_URL = "/api/puntengroeps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PuntengroepRepository puntengroepRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPuntengroepMockMvc;

    private Puntengroep puntengroep;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Puntengroep createEntity(EntityManager em) {
        Puntengroep puntengroep = new Puntengroep();
        return puntengroep;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Puntengroep createUpdatedEntity(EntityManager em) {
        Puntengroep puntengroep = new Puntengroep();
        return puntengroep;
    }

    @BeforeEach
    public void initTest() {
        puntengroep = createEntity(em);
    }

    @Test
    @Transactional
    void createPuntengroep() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Puntengroep
        var returnedPuntengroep = om.readValue(
            restPuntengroepMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(puntengroep)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Puntengroep.class
        );

        // Validate the Puntengroep in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPuntengroepUpdatableFieldsEquals(returnedPuntengroep, getPersistedPuntengroep(returnedPuntengroep));
    }

    @Test
    @Transactional
    void createPuntengroepWithExistingId() throws Exception {
        // Create the Puntengroep with an existing ID
        puntengroep.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPuntengroepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(puntengroep)))
            .andExpect(status().isBadRequest());

        // Validate the Puntengroep in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPuntengroeps() throws Exception {
        // Initialize the database
        puntengroepRepository.saveAndFlush(puntengroep);

        // Get all the puntengroepList
        restPuntengroepMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(puntengroep.getId().intValue())));
    }

    @Test
    @Transactional
    void getPuntengroep() throws Exception {
        // Initialize the database
        puntengroepRepository.saveAndFlush(puntengroep);

        // Get the puntengroep
        restPuntengroepMockMvc
            .perform(get(ENTITY_API_URL_ID, puntengroep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(puntengroep.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPuntengroep() throws Exception {
        // Get the puntengroep
        restPuntengroepMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deletePuntengroep() throws Exception {
        // Initialize the database
        puntengroepRepository.saveAndFlush(puntengroep);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the puntengroep
        restPuntengroepMockMvc
            .perform(delete(ENTITY_API_URL_ID, puntengroep.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return puntengroepRepository.count();
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

    protected Puntengroep getPersistedPuntengroep(Puntengroep puntengroep) {
        return puntengroepRepository.findById(puntengroep.getId()).orElseThrow();
    }

    protected void assertPersistedPuntengroepToMatchAllProperties(Puntengroep expectedPuntengroep) {
        assertPuntengroepAllPropertiesEquals(expectedPuntengroep, getPersistedPuntengroep(expectedPuntengroep));
    }

    protected void assertPersistedPuntengroepToMatchUpdatableProperties(Puntengroep expectedPuntengroep) {
        assertPuntengroepAllUpdatablePropertiesEquals(expectedPuntengroep, getPersistedPuntengroep(expectedPuntengroep));
    }
}
