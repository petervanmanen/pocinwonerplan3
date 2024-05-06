package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ClassjAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Classj;
import nl.ritense.demo.repository.ClassjRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClassjResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClassjResourceIT {

    private static final String ENTITY_API_URL = "/api/classjs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ClassjRepository classjRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassjMockMvc;

    private Classj classj;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classj createEntity(EntityManager em) {
        Classj classj = new Classj();
        return classj;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classj createUpdatedEntity(EntityManager em) {
        Classj classj = new Classj();
        return classj;
    }

    @BeforeEach
    public void initTest() {
        classj = createEntity(em);
    }

    @Test
    @Transactional
    void createClassj() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Classj
        var returnedClassj = om.readValue(
            restClassjMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classj)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Classj.class
        );

        // Validate the Classj in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertClassjUpdatableFieldsEquals(returnedClassj, getPersistedClassj(returnedClassj));
    }

    @Test
    @Transactional
    void createClassjWithExistingId() throws Exception {
        // Create the Classj with an existing ID
        classj.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassjMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classj)))
            .andExpect(status().isBadRequest());

        // Validate the Classj in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClassjs() throws Exception {
        // Initialize the database
        classjRepository.saveAndFlush(classj);

        // Get all the classjList
        restClassjMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classj.getId().intValue())));
    }

    @Test
    @Transactional
    void getClassj() throws Exception {
        // Initialize the database
        classjRepository.saveAndFlush(classj);

        // Get the classj
        restClassjMockMvc
            .perform(get(ENTITY_API_URL_ID, classj.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classj.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingClassj() throws Exception {
        // Get the classj
        restClassjMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteClassj() throws Exception {
        // Initialize the database
        classjRepository.saveAndFlush(classj);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the classj
        restClassjMockMvc
            .perform(delete(ENTITY_API_URL_ID, classj.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return classjRepository.count();
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

    protected Classj getPersistedClassj(Classj classj) {
        return classjRepository.findById(classj.getId()).orElseThrow();
    }

    protected void assertPersistedClassjToMatchAllProperties(Classj expectedClassj) {
        assertClassjAllPropertiesEquals(expectedClassj, getPersistedClassj(expectedClassj));
    }

    protected void assertPersistedClassjToMatchUpdatableProperties(Classj expectedClassj) {
        assertClassjAllUpdatablePropertiesEquals(expectedClassj, getPersistedClassj(expectedClassj));
    }
}
