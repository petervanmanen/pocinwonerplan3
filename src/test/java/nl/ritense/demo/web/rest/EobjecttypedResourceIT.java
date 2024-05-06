package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EobjecttypedAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Eobjecttyped;
import nl.ritense.demo.repository.EobjecttypedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EobjecttypedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EobjecttypedResourceIT {

    private static final String ENTITY_API_URL = "/api/eobjecttypeds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EobjecttypedRepository eobjecttypedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEobjecttypedMockMvc;

    private Eobjecttyped eobjecttyped;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjecttyped createEntity(EntityManager em) {
        Eobjecttyped eobjecttyped = new Eobjecttyped();
        return eobjecttyped;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjecttyped createUpdatedEntity(EntityManager em) {
        Eobjecttyped eobjecttyped = new Eobjecttyped();
        return eobjecttyped;
    }

    @BeforeEach
    public void initTest() {
        eobjecttyped = createEntity(em);
    }

    @Test
    @Transactional
    void createEobjecttyped() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Eobjecttyped
        var returnedEobjecttyped = om.readValue(
            restEobjecttypedMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjecttyped)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Eobjecttyped.class
        );

        // Validate the Eobjecttyped in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEobjecttypedUpdatableFieldsEquals(returnedEobjecttyped, getPersistedEobjecttyped(returnedEobjecttyped));
    }

    @Test
    @Transactional
    void createEobjecttypedWithExistingId() throws Exception {
        // Create the Eobjecttyped with an existing ID
        eobjecttyped.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEobjecttypedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjecttyped)))
            .andExpect(status().isBadRequest());

        // Validate the Eobjecttyped in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEobjecttypeds() throws Exception {
        // Initialize the database
        eobjecttypedRepository.saveAndFlush(eobjecttyped);

        // Get all the eobjecttypedList
        restEobjecttypedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eobjecttyped.getId().intValue())));
    }

    @Test
    @Transactional
    void getEobjecttyped() throws Exception {
        // Initialize the database
        eobjecttypedRepository.saveAndFlush(eobjecttyped);

        // Get the eobjecttyped
        restEobjecttypedMockMvc
            .perform(get(ENTITY_API_URL_ID, eobjecttyped.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eobjecttyped.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingEobjecttyped() throws Exception {
        // Get the eobjecttyped
        restEobjecttypedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteEobjecttyped() throws Exception {
        // Initialize the database
        eobjecttypedRepository.saveAndFlush(eobjecttyped);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eobjecttyped
        restEobjecttypedMockMvc
            .perform(delete(ENTITY_API_URL_ID, eobjecttyped.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return eobjecttypedRepository.count();
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

    protected Eobjecttyped getPersistedEobjecttyped(Eobjecttyped eobjecttyped) {
        return eobjecttypedRepository.findById(eobjecttyped.getId()).orElseThrow();
    }

    protected void assertPersistedEobjecttypedToMatchAllProperties(Eobjecttyped expectedEobjecttyped) {
        assertEobjecttypedAllPropertiesEquals(expectedEobjecttyped, getPersistedEobjecttyped(expectedEobjecttyped));
    }

    protected void assertPersistedEobjecttypedToMatchUpdatableProperties(Eobjecttyped expectedEobjecttyped) {
        assertEobjecttypedAllUpdatablePropertiesEquals(expectedEobjecttyped, getPersistedEobjecttyped(expectedEobjecttyped));
    }
}
