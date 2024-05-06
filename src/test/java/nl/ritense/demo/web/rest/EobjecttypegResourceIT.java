package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EobjecttypegAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Eobjecttypeg;
import nl.ritense.demo.repository.EobjecttypegRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EobjecttypegResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EobjecttypegResourceIT {

    private static final String ENTITY_API_URL = "/api/eobjecttypegs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EobjecttypegRepository eobjecttypegRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEobjecttypegMockMvc;

    private Eobjecttypeg eobjecttypeg;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjecttypeg createEntity(EntityManager em) {
        Eobjecttypeg eobjecttypeg = new Eobjecttypeg();
        return eobjecttypeg;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjecttypeg createUpdatedEntity(EntityManager em) {
        Eobjecttypeg eobjecttypeg = new Eobjecttypeg();
        return eobjecttypeg;
    }

    @BeforeEach
    public void initTest() {
        eobjecttypeg = createEntity(em);
    }

    @Test
    @Transactional
    void createEobjecttypeg() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Eobjecttypeg
        var returnedEobjecttypeg = om.readValue(
            restEobjecttypegMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjecttypeg)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Eobjecttypeg.class
        );

        // Validate the Eobjecttypeg in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEobjecttypegUpdatableFieldsEquals(returnedEobjecttypeg, getPersistedEobjecttypeg(returnedEobjecttypeg));
    }

    @Test
    @Transactional
    void createEobjecttypegWithExistingId() throws Exception {
        // Create the Eobjecttypeg with an existing ID
        eobjecttypeg.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEobjecttypegMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjecttypeg)))
            .andExpect(status().isBadRequest());

        // Validate the Eobjecttypeg in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEobjecttypegs() throws Exception {
        // Initialize the database
        eobjecttypegRepository.saveAndFlush(eobjecttypeg);

        // Get all the eobjecttypegList
        restEobjecttypegMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eobjecttypeg.getId().intValue())));
    }

    @Test
    @Transactional
    void getEobjecttypeg() throws Exception {
        // Initialize the database
        eobjecttypegRepository.saveAndFlush(eobjecttypeg);

        // Get the eobjecttypeg
        restEobjecttypegMockMvc
            .perform(get(ENTITY_API_URL_ID, eobjecttypeg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eobjecttypeg.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingEobjecttypeg() throws Exception {
        // Get the eobjecttypeg
        restEobjecttypegMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteEobjecttypeg() throws Exception {
        // Initialize the database
        eobjecttypegRepository.saveAndFlush(eobjecttypeg);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eobjecttypeg
        restEobjecttypegMockMvc
            .perform(delete(ENTITY_API_URL_ID, eobjecttypeg.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return eobjecttypegRepository.count();
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

    protected Eobjecttypeg getPersistedEobjecttypeg(Eobjecttypeg eobjecttypeg) {
        return eobjecttypegRepository.findById(eobjecttypeg.getId()).orElseThrow();
    }

    protected void assertPersistedEobjecttypegToMatchAllProperties(Eobjecttypeg expectedEobjecttypeg) {
        assertEobjecttypegAllPropertiesEquals(expectedEobjecttypeg, getPersistedEobjecttypeg(expectedEobjecttypeg));
    }

    protected void assertPersistedEobjecttypegToMatchUpdatableProperties(Eobjecttypeg expectedEobjecttypeg) {
        assertEobjecttypegAllUpdatablePropertiesEquals(expectedEobjecttypeg, getPersistedEobjecttypeg(expectedEobjecttypeg));
    }
}
