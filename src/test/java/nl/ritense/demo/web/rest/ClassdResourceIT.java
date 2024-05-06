package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ClassdAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Classd;
import nl.ritense.demo.repository.ClassdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClassdResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClassdResourceIT {

    private static final String ENTITY_API_URL = "/api/classds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ClassdRepository classdRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassdMockMvc;

    private Classd classd;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classd createEntity(EntityManager em) {
        Classd classd = new Classd();
        return classd;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classd createUpdatedEntity(EntityManager em) {
        Classd classd = new Classd();
        return classd;
    }

    @BeforeEach
    public void initTest() {
        classd = createEntity(em);
    }

    @Test
    @Transactional
    void createClassd() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Classd
        var returnedClassd = om.readValue(
            restClassdMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classd)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Classd.class
        );

        // Validate the Classd in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertClassdUpdatableFieldsEquals(returnedClassd, getPersistedClassd(returnedClassd));
    }

    @Test
    @Transactional
    void createClassdWithExistingId() throws Exception {
        // Create the Classd with an existing ID
        classd.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classd)))
            .andExpect(status().isBadRequest());

        // Validate the Classd in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClassds() throws Exception {
        // Initialize the database
        classdRepository.saveAndFlush(classd);

        // Get all the classdList
        restClassdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classd.getId().intValue())));
    }

    @Test
    @Transactional
    void getClassd() throws Exception {
        // Initialize the database
        classdRepository.saveAndFlush(classd);

        // Get the classd
        restClassdMockMvc
            .perform(get(ENTITY_API_URL_ID, classd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classd.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingClassd() throws Exception {
        // Get the classd
        restClassdMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteClassd() throws Exception {
        // Initialize the database
        classdRepository.saveAndFlush(classd);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the classd
        restClassdMockMvc
            .perform(delete(ENTITY_API_URL_ID, classd.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return classdRepository.count();
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

    protected Classd getPersistedClassd(Classd classd) {
        return classdRepository.findById(classd.getId()).orElseThrow();
    }

    protected void assertPersistedClassdToMatchAllProperties(Classd expectedClassd) {
        assertClassdAllPropertiesEquals(expectedClassd, getPersistedClassd(expectedClassd));
    }

    protected void assertPersistedClassdToMatchUpdatableProperties(Classd expectedClassd) {
        assertClassdAllUpdatablePropertiesEquals(expectedClassd, getPersistedClassd(expectedClassd));
    }
}
