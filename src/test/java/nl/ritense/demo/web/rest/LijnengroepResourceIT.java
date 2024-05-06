package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LijnengroepAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Lijnengroep;
import nl.ritense.demo.repository.LijnengroepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LijnengroepResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LijnengroepResourceIT {

    private static final String ENTITY_API_URL = "/api/lijnengroeps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LijnengroepRepository lijnengroepRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLijnengroepMockMvc;

    private Lijnengroep lijnengroep;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lijnengroep createEntity(EntityManager em) {
        Lijnengroep lijnengroep = new Lijnengroep();
        return lijnengroep;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lijnengroep createUpdatedEntity(EntityManager em) {
        Lijnengroep lijnengroep = new Lijnengroep();
        return lijnengroep;
    }

    @BeforeEach
    public void initTest() {
        lijnengroep = createEntity(em);
    }

    @Test
    @Transactional
    void createLijnengroep() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Lijnengroep
        var returnedLijnengroep = om.readValue(
            restLijnengroepMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lijnengroep)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Lijnengroep.class
        );

        // Validate the Lijnengroep in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLijnengroepUpdatableFieldsEquals(returnedLijnengroep, getPersistedLijnengroep(returnedLijnengroep));
    }

    @Test
    @Transactional
    void createLijnengroepWithExistingId() throws Exception {
        // Create the Lijnengroep with an existing ID
        lijnengroep.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLijnengroepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lijnengroep)))
            .andExpect(status().isBadRequest());

        // Validate the Lijnengroep in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLijnengroeps() throws Exception {
        // Initialize the database
        lijnengroepRepository.saveAndFlush(lijnengroep);

        // Get all the lijnengroepList
        restLijnengroepMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lijnengroep.getId().intValue())));
    }

    @Test
    @Transactional
    void getLijnengroep() throws Exception {
        // Initialize the database
        lijnengroepRepository.saveAndFlush(lijnengroep);

        // Get the lijnengroep
        restLijnengroepMockMvc
            .perform(get(ENTITY_API_URL_ID, lijnengroep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lijnengroep.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingLijnengroep() throws Exception {
        // Get the lijnengroep
        restLijnengroepMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteLijnengroep() throws Exception {
        // Initialize the database
        lijnengroepRepository.saveAndFlush(lijnengroep);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the lijnengroep
        restLijnengroepMockMvc
            .perform(delete(ENTITY_API_URL_ID, lijnengroep.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return lijnengroepRepository.count();
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

    protected Lijnengroep getPersistedLijnengroep(Lijnengroep lijnengroep) {
        return lijnengroepRepository.findById(lijnengroep.getId()).orElseThrow();
    }

    protected void assertPersistedLijnengroepToMatchAllProperties(Lijnengroep expectedLijnengroep) {
        assertLijnengroepAllPropertiesEquals(expectedLijnengroep, getPersistedLijnengroep(expectedLijnengroep));
    }

    protected void assertPersistedLijnengroepToMatchUpdatableProperties(Lijnengroep expectedLijnengroep) {
        assertLijnengroepAllUpdatablePropertiesEquals(expectedLijnengroep, getPersistedLijnengroep(expectedLijnengroep));
    }
}
