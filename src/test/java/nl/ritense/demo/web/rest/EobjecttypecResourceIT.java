package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EobjecttypecAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Eobjecttypec;
import nl.ritense.demo.repository.EobjecttypecRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EobjecttypecResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EobjecttypecResourceIT {

    private static final String ENTITY_API_URL = "/api/eobjecttypecs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EobjecttypecRepository eobjecttypecRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEobjecttypecMockMvc;

    private Eobjecttypec eobjecttypec;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjecttypec createEntity(EntityManager em) {
        Eobjecttypec eobjecttypec = new Eobjecttypec();
        return eobjecttypec;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjecttypec createUpdatedEntity(EntityManager em) {
        Eobjecttypec eobjecttypec = new Eobjecttypec();
        return eobjecttypec;
    }

    @BeforeEach
    public void initTest() {
        eobjecttypec = createEntity(em);
    }

    @Test
    @Transactional
    void createEobjecttypec() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Eobjecttypec
        var returnedEobjecttypec = om.readValue(
            restEobjecttypecMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjecttypec)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Eobjecttypec.class
        );

        // Validate the Eobjecttypec in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEobjecttypecUpdatableFieldsEquals(returnedEobjecttypec, getPersistedEobjecttypec(returnedEobjecttypec));
    }

    @Test
    @Transactional
    void createEobjecttypecWithExistingId() throws Exception {
        // Create the Eobjecttypec with an existing ID
        eobjecttypec.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEobjecttypecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjecttypec)))
            .andExpect(status().isBadRequest());

        // Validate the Eobjecttypec in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEobjecttypecs() throws Exception {
        // Initialize the database
        eobjecttypecRepository.saveAndFlush(eobjecttypec);

        // Get all the eobjecttypecList
        restEobjecttypecMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eobjecttypec.getId().intValue())));
    }

    @Test
    @Transactional
    void getEobjecttypec() throws Exception {
        // Initialize the database
        eobjecttypecRepository.saveAndFlush(eobjecttypec);

        // Get the eobjecttypec
        restEobjecttypecMockMvc
            .perform(get(ENTITY_API_URL_ID, eobjecttypec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eobjecttypec.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingEobjecttypec() throws Exception {
        // Get the eobjecttypec
        restEobjecttypecMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteEobjecttypec() throws Exception {
        // Initialize the database
        eobjecttypecRepository.saveAndFlush(eobjecttypec);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eobjecttypec
        restEobjecttypecMockMvc
            .perform(delete(ENTITY_API_URL_ID, eobjecttypec.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return eobjecttypecRepository.count();
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

    protected Eobjecttypec getPersistedEobjecttypec(Eobjecttypec eobjecttypec) {
        return eobjecttypecRepository.findById(eobjecttypec.getId()).orElseThrow();
    }

    protected void assertPersistedEobjecttypecToMatchAllProperties(Eobjecttypec expectedEobjecttypec) {
        assertEobjecttypecAllPropertiesEquals(expectedEobjecttypec, getPersistedEobjecttypec(expectedEobjecttypec));
    }

    protected void assertPersistedEobjecttypecToMatchUpdatableProperties(Eobjecttypec expectedEobjecttypec) {
        assertEobjecttypecAllUpdatablePropertiesEquals(expectedEobjecttypec, getPersistedEobjecttypec(expectedEobjecttypec));
    }
}
