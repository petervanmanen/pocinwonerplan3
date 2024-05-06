package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EobjecttypebAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Eobjecttypeb;
import nl.ritense.demo.repository.EobjecttypebRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EobjecttypebResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EobjecttypebResourceIT {

    private static final String ENTITY_API_URL = "/api/eobjecttypebs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EobjecttypebRepository eobjecttypebRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEobjecttypebMockMvc;

    private Eobjecttypeb eobjecttypeb;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjecttypeb createEntity(EntityManager em) {
        Eobjecttypeb eobjecttypeb = new Eobjecttypeb();
        return eobjecttypeb;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjecttypeb createUpdatedEntity(EntityManager em) {
        Eobjecttypeb eobjecttypeb = new Eobjecttypeb();
        return eobjecttypeb;
    }

    @BeforeEach
    public void initTest() {
        eobjecttypeb = createEntity(em);
    }

    @Test
    @Transactional
    void createEobjecttypeb() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Eobjecttypeb
        var returnedEobjecttypeb = om.readValue(
            restEobjecttypebMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjecttypeb)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Eobjecttypeb.class
        );

        // Validate the Eobjecttypeb in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEobjecttypebUpdatableFieldsEquals(returnedEobjecttypeb, getPersistedEobjecttypeb(returnedEobjecttypeb));
    }

    @Test
    @Transactional
    void createEobjecttypebWithExistingId() throws Exception {
        // Create the Eobjecttypeb with an existing ID
        eobjecttypeb.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEobjecttypebMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjecttypeb)))
            .andExpect(status().isBadRequest());

        // Validate the Eobjecttypeb in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEobjecttypebs() throws Exception {
        // Initialize the database
        eobjecttypebRepository.saveAndFlush(eobjecttypeb);

        // Get all the eobjecttypebList
        restEobjecttypebMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eobjecttypeb.getId().intValue())));
    }

    @Test
    @Transactional
    void getEobjecttypeb() throws Exception {
        // Initialize the database
        eobjecttypebRepository.saveAndFlush(eobjecttypeb);

        // Get the eobjecttypeb
        restEobjecttypebMockMvc
            .perform(get(ENTITY_API_URL_ID, eobjecttypeb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eobjecttypeb.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingEobjecttypeb() throws Exception {
        // Get the eobjecttypeb
        restEobjecttypebMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteEobjecttypeb() throws Exception {
        // Initialize the database
        eobjecttypebRepository.saveAndFlush(eobjecttypeb);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eobjecttypeb
        restEobjecttypebMockMvc
            .perform(delete(ENTITY_API_URL_ID, eobjecttypeb.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return eobjecttypebRepository.count();
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

    protected Eobjecttypeb getPersistedEobjecttypeb(Eobjecttypeb eobjecttypeb) {
        return eobjecttypebRepository.findById(eobjecttypeb.getId()).orElseThrow();
    }

    protected void assertPersistedEobjecttypebToMatchAllProperties(Eobjecttypeb expectedEobjecttypeb) {
        assertEobjecttypebAllPropertiesEquals(expectedEobjecttypeb, getPersistedEobjecttypeb(expectedEobjecttypeb));
    }

    protected void assertPersistedEobjecttypebToMatchUpdatableProperties(Eobjecttypeb expectedEobjecttypeb) {
        assertEobjecttypebAllUpdatablePropertiesEquals(expectedEobjecttypeb, getPersistedEobjecttypeb(expectedEobjecttypeb));
    }
}
