package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EobjecttypeaAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Eobjecttypea;
import nl.ritense.demo.repository.EobjecttypeaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EobjecttypeaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EobjecttypeaResourceIT {

    private static final String ENTITY_API_URL = "/api/eobjecttypeas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EobjecttypeaRepository eobjecttypeaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEobjecttypeaMockMvc;

    private Eobjecttypea eobjecttypea;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjecttypea createEntity(EntityManager em) {
        Eobjecttypea eobjecttypea = new Eobjecttypea();
        return eobjecttypea;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjecttypea createUpdatedEntity(EntityManager em) {
        Eobjecttypea eobjecttypea = new Eobjecttypea();
        return eobjecttypea;
    }

    @BeforeEach
    public void initTest() {
        eobjecttypea = createEntity(em);
    }

    @Test
    @Transactional
    void createEobjecttypea() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Eobjecttypea
        var returnedEobjecttypea = om.readValue(
            restEobjecttypeaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjecttypea)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Eobjecttypea.class
        );

        // Validate the Eobjecttypea in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEobjecttypeaUpdatableFieldsEquals(returnedEobjecttypea, getPersistedEobjecttypea(returnedEobjecttypea));
    }

    @Test
    @Transactional
    void createEobjecttypeaWithExistingId() throws Exception {
        // Create the Eobjecttypea with an existing ID
        eobjecttypea.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEobjecttypeaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjecttypea)))
            .andExpect(status().isBadRequest());

        // Validate the Eobjecttypea in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEobjecttypeas() throws Exception {
        // Initialize the database
        eobjecttypeaRepository.saveAndFlush(eobjecttypea);

        // Get all the eobjecttypeaList
        restEobjecttypeaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eobjecttypea.getId().intValue())));
    }

    @Test
    @Transactional
    void getEobjecttypea() throws Exception {
        // Initialize the database
        eobjecttypeaRepository.saveAndFlush(eobjecttypea);

        // Get the eobjecttypea
        restEobjecttypeaMockMvc
            .perform(get(ENTITY_API_URL_ID, eobjecttypea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eobjecttypea.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingEobjecttypea() throws Exception {
        // Get the eobjecttypea
        restEobjecttypeaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteEobjecttypea() throws Exception {
        // Initialize the database
        eobjecttypeaRepository.saveAndFlush(eobjecttypea);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eobjecttypea
        restEobjecttypeaMockMvc
            .perform(delete(ENTITY_API_URL_ID, eobjecttypea.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return eobjecttypeaRepository.count();
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

    protected Eobjecttypea getPersistedEobjecttypea(Eobjecttypea eobjecttypea) {
        return eobjecttypeaRepository.findById(eobjecttypea.getId()).orElseThrow();
    }

    protected void assertPersistedEobjecttypeaToMatchAllProperties(Eobjecttypea expectedEobjecttypea) {
        assertEobjecttypeaAllPropertiesEquals(expectedEobjecttypea, getPersistedEobjecttypea(expectedEobjecttypea));
    }

    protected void assertPersistedEobjecttypeaToMatchUpdatableProperties(Eobjecttypea expectedEobjecttypea) {
        assertEobjecttypeaAllUpdatablePropertiesEquals(expectedEobjecttypea, getPersistedEobjecttypea(expectedEobjecttypea));
    }
}
