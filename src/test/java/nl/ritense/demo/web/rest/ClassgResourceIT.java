package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ClassgAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Classg;
import nl.ritense.demo.repository.ClassgRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClassgResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClassgResourceIT {

    private static final String ENTITY_API_URL = "/api/classgs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ClassgRepository classgRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassgMockMvc;

    private Classg classg;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classg createEntity(EntityManager em) {
        Classg classg = new Classg();
        return classg;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classg createUpdatedEntity(EntityManager em) {
        Classg classg = new Classg();
        return classg;
    }

    @BeforeEach
    public void initTest() {
        classg = createEntity(em);
    }

    @Test
    @Transactional
    void createClassg() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Classg
        var returnedClassg = om.readValue(
            restClassgMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classg)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Classg.class
        );

        // Validate the Classg in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertClassgUpdatableFieldsEquals(returnedClassg, getPersistedClassg(returnedClassg));
    }

    @Test
    @Transactional
    void createClassgWithExistingId() throws Exception {
        // Create the Classg with an existing ID
        classg.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classg)))
            .andExpect(status().isBadRequest());

        // Validate the Classg in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClassgs() throws Exception {
        // Initialize the database
        classgRepository.saveAndFlush(classg);

        // Get all the classgList
        restClassgMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classg.getId().intValue())));
    }

    @Test
    @Transactional
    void getClassg() throws Exception {
        // Initialize the database
        classgRepository.saveAndFlush(classg);

        // Get the classg
        restClassgMockMvc
            .perform(get(ENTITY_API_URL_ID, classg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classg.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingClassg() throws Exception {
        // Get the classg
        restClassgMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteClassg() throws Exception {
        // Initialize the database
        classgRepository.saveAndFlush(classg);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the classg
        restClassgMockMvc
            .perform(delete(ENTITY_API_URL_ID, classg.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return classgRepository.count();
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

    protected Classg getPersistedClassg(Classg classg) {
        return classgRepository.findById(classg.getId()).orElseThrow();
    }

    protected void assertPersistedClassgToMatchAllProperties(Classg expectedClassg) {
        assertClassgAllPropertiesEquals(expectedClassg, getPersistedClassg(expectedClassg));
    }

    protected void assertPersistedClassgToMatchUpdatableProperties(Classg expectedClassg) {
        assertClassgAllUpdatablePropertiesEquals(expectedClassg, getPersistedClassg(expectedClassg));
    }
}
