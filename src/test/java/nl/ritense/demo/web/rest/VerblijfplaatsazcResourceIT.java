package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerblijfplaatsazcAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Verblijfplaatsazc;
import nl.ritense.demo.repository.VerblijfplaatsazcRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerblijfplaatsazcResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerblijfplaatsazcResourceIT {

    private static final String ENTITY_API_URL = "/api/verblijfplaatsazcs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerblijfplaatsazcRepository verblijfplaatsazcRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerblijfplaatsazcMockMvc;

    private Verblijfplaatsazc verblijfplaatsazc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfplaatsazc createEntity(EntityManager em) {
        Verblijfplaatsazc verblijfplaatsazc = new Verblijfplaatsazc();
        return verblijfplaatsazc;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfplaatsazc createUpdatedEntity(EntityManager em) {
        Verblijfplaatsazc verblijfplaatsazc = new Verblijfplaatsazc();
        return verblijfplaatsazc;
    }

    @BeforeEach
    public void initTest() {
        verblijfplaatsazc = createEntity(em);
    }

    @Test
    @Transactional
    void createVerblijfplaatsazc() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verblijfplaatsazc
        var returnedVerblijfplaatsazc = om.readValue(
            restVerblijfplaatsazcMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfplaatsazc)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verblijfplaatsazc.class
        );

        // Validate the Verblijfplaatsazc in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerblijfplaatsazcUpdatableFieldsEquals(returnedVerblijfplaatsazc, getPersistedVerblijfplaatsazc(returnedVerblijfplaatsazc));
    }

    @Test
    @Transactional
    void createVerblijfplaatsazcWithExistingId() throws Exception {
        // Create the Verblijfplaatsazc with an existing ID
        verblijfplaatsazc.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerblijfplaatsazcMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfplaatsazc)))
            .andExpect(status().isBadRequest());

        // Validate the Verblijfplaatsazc in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerblijfplaatsazcs() throws Exception {
        // Initialize the database
        verblijfplaatsazcRepository.saveAndFlush(verblijfplaatsazc);

        // Get all the verblijfplaatsazcList
        restVerblijfplaatsazcMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verblijfplaatsazc.getId().intValue())));
    }

    @Test
    @Transactional
    void getVerblijfplaatsazc() throws Exception {
        // Initialize the database
        verblijfplaatsazcRepository.saveAndFlush(verblijfplaatsazc);

        // Get the verblijfplaatsazc
        restVerblijfplaatsazcMockMvc
            .perform(get(ENTITY_API_URL_ID, verblijfplaatsazc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verblijfplaatsazc.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingVerblijfplaatsazc() throws Exception {
        // Get the verblijfplaatsazc
        restVerblijfplaatsazcMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteVerblijfplaatsazc() throws Exception {
        // Initialize the database
        verblijfplaatsazcRepository.saveAndFlush(verblijfplaatsazc);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verblijfplaatsazc
        restVerblijfplaatsazcMockMvc
            .perform(delete(ENTITY_API_URL_ID, verblijfplaatsazc.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verblijfplaatsazcRepository.count();
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

    protected Verblijfplaatsazc getPersistedVerblijfplaatsazc(Verblijfplaatsazc verblijfplaatsazc) {
        return verblijfplaatsazcRepository.findById(verblijfplaatsazc.getId()).orElseThrow();
    }

    protected void assertPersistedVerblijfplaatsazcToMatchAllProperties(Verblijfplaatsazc expectedVerblijfplaatsazc) {
        assertVerblijfplaatsazcAllPropertiesEquals(expectedVerblijfplaatsazc, getPersistedVerblijfplaatsazc(expectedVerblijfplaatsazc));
    }

    protected void assertPersistedVerblijfplaatsazcToMatchUpdatableProperties(Verblijfplaatsazc expectedVerblijfplaatsazc) {
        assertVerblijfplaatsazcAllUpdatablePropertiesEquals(
            expectedVerblijfplaatsazc,
            getPersistedVerblijfplaatsazc(expectedVerblijfplaatsazc)
        );
    }
}
