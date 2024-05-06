package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerlichtingsobjectAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Verlichtingsobject;
import nl.ritense.demo.repository.VerlichtingsobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerlichtingsobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerlichtingsobjectResourceIT {

    private static final String ENTITY_API_URL = "/api/verlichtingsobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerlichtingsobjectRepository verlichtingsobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerlichtingsobjectMockMvc;

    private Verlichtingsobject verlichtingsobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verlichtingsobject createEntity(EntityManager em) {
        Verlichtingsobject verlichtingsobject = new Verlichtingsobject();
        return verlichtingsobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verlichtingsobject createUpdatedEntity(EntityManager em) {
        Verlichtingsobject verlichtingsobject = new Verlichtingsobject();
        return verlichtingsobject;
    }

    @BeforeEach
    public void initTest() {
        verlichtingsobject = createEntity(em);
    }

    @Test
    @Transactional
    void createVerlichtingsobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verlichtingsobject
        var returnedVerlichtingsobject = om.readValue(
            restVerlichtingsobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verlichtingsobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verlichtingsobject.class
        );

        // Validate the Verlichtingsobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerlichtingsobjectUpdatableFieldsEquals(
            returnedVerlichtingsobject,
            getPersistedVerlichtingsobject(returnedVerlichtingsobject)
        );
    }

    @Test
    @Transactional
    void createVerlichtingsobjectWithExistingId() throws Exception {
        // Create the Verlichtingsobject with an existing ID
        verlichtingsobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerlichtingsobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verlichtingsobject)))
            .andExpect(status().isBadRequest());

        // Validate the Verlichtingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerlichtingsobjects() throws Exception {
        // Initialize the database
        verlichtingsobjectRepository.saveAndFlush(verlichtingsobject);

        // Get all the verlichtingsobjectList
        restVerlichtingsobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verlichtingsobject.getId().intValue())));
    }

    @Test
    @Transactional
    void getVerlichtingsobject() throws Exception {
        // Initialize the database
        verlichtingsobjectRepository.saveAndFlush(verlichtingsobject);

        // Get the verlichtingsobject
        restVerlichtingsobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, verlichtingsobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verlichtingsobject.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingVerlichtingsobject() throws Exception {
        // Get the verlichtingsobject
        restVerlichtingsobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteVerlichtingsobject() throws Exception {
        // Initialize the database
        verlichtingsobjectRepository.saveAndFlush(verlichtingsobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verlichtingsobject
        restVerlichtingsobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, verlichtingsobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verlichtingsobjectRepository.count();
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

    protected Verlichtingsobject getPersistedVerlichtingsobject(Verlichtingsobject verlichtingsobject) {
        return verlichtingsobjectRepository.findById(verlichtingsobject.getId()).orElseThrow();
    }

    protected void assertPersistedVerlichtingsobjectToMatchAllProperties(Verlichtingsobject expectedVerlichtingsobject) {
        assertVerlichtingsobjectAllPropertiesEquals(expectedVerlichtingsobject, getPersistedVerlichtingsobject(expectedVerlichtingsobject));
    }

    protected void assertPersistedVerlichtingsobjectToMatchUpdatableProperties(Verlichtingsobject expectedVerlichtingsobject) {
        assertVerlichtingsobjectAllUpdatablePropertiesEquals(
            expectedVerlichtingsobject,
            getPersistedVerlichtingsobject(expectedVerlichtingsobject)
        );
    }
}
