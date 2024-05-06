package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KnmAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Knm;
import nl.ritense.demo.repository.KnmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KnmResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KnmResourceIT {

    private static final String ENTITY_API_URL = "/api/knms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KnmRepository knmRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKnmMockMvc;

    private Knm knm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Knm createEntity(EntityManager em) {
        Knm knm = new Knm();
        return knm;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Knm createUpdatedEntity(EntityManager em) {
        Knm knm = new Knm();
        return knm;
    }

    @BeforeEach
    public void initTest() {
        knm = createEntity(em);
    }

    @Test
    @Transactional
    void createKnm() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Knm
        var returnedKnm = om.readValue(
            restKnmMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(knm)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Knm.class
        );

        // Validate the Knm in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKnmUpdatableFieldsEquals(returnedKnm, getPersistedKnm(returnedKnm));
    }

    @Test
    @Transactional
    void createKnmWithExistingId() throws Exception {
        // Create the Knm with an existing ID
        knm.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKnmMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(knm)))
            .andExpect(status().isBadRequest());

        // Validate the Knm in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKnms() throws Exception {
        // Initialize the database
        knmRepository.saveAndFlush(knm);

        // Get all the knmList
        restKnmMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(knm.getId().intValue())));
    }

    @Test
    @Transactional
    void getKnm() throws Exception {
        // Initialize the database
        knmRepository.saveAndFlush(knm);

        // Get the knm
        restKnmMockMvc
            .perform(get(ENTITY_API_URL_ID, knm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(knm.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingKnm() throws Exception {
        // Get the knm
        restKnmMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteKnm() throws Exception {
        // Initialize the database
        knmRepository.saveAndFlush(knm);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the knm
        restKnmMockMvc.perform(delete(ENTITY_API_URL_ID, knm.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return knmRepository.count();
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

    protected Knm getPersistedKnm(Knm knm) {
        return knmRepository.findById(knm.getId()).orElseThrow();
    }

    protected void assertPersistedKnmToMatchAllProperties(Knm expectedKnm) {
        assertKnmAllPropertiesEquals(expectedKnm, getPersistedKnm(expectedKnm));
    }

    protected void assertPersistedKnmToMatchUpdatableProperties(Knm expectedKnm) {
        assertKnmAllUpdatablePropertiesEquals(expectedKnm, getPersistedKnm(expectedKnm));
    }
}
