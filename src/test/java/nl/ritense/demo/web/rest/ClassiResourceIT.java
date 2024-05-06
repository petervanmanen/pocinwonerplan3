package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ClassiAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Classi;
import nl.ritense.demo.repository.ClassiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClassiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClassiResourceIT {

    private static final String ENTITY_API_URL = "/api/classis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ClassiRepository classiRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassiMockMvc;

    private Classi classi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classi createEntity(EntityManager em) {
        Classi classi = new Classi();
        return classi;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classi createUpdatedEntity(EntityManager em) {
        Classi classi = new Classi();
        return classi;
    }

    @BeforeEach
    public void initTest() {
        classi = createEntity(em);
    }

    @Test
    @Transactional
    void createClassi() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Classi
        var returnedClassi = om.readValue(
            restClassiMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classi)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Classi.class
        );

        // Validate the Classi in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertClassiUpdatableFieldsEquals(returnedClassi, getPersistedClassi(returnedClassi));
    }

    @Test
    @Transactional
    void createClassiWithExistingId() throws Exception {
        // Create the Classi with an existing ID
        classi.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classi)))
            .andExpect(status().isBadRequest());

        // Validate the Classi in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClassis() throws Exception {
        // Initialize the database
        classiRepository.saveAndFlush(classi);

        // Get all the classiList
        restClassiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classi.getId().intValue())));
    }

    @Test
    @Transactional
    void getClassi() throws Exception {
        // Initialize the database
        classiRepository.saveAndFlush(classi);

        // Get the classi
        restClassiMockMvc
            .perform(get(ENTITY_API_URL_ID, classi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classi.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingClassi() throws Exception {
        // Get the classi
        restClassiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteClassi() throws Exception {
        // Initialize the database
        classiRepository.saveAndFlush(classi);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the classi
        restClassiMockMvc
            .perform(delete(ENTITY_API_URL_ID, classi.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return classiRepository.count();
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

    protected Classi getPersistedClassi(Classi classi) {
        return classiRepository.findById(classi.getId()).orElseThrow();
    }

    protected void assertPersistedClassiToMatchAllProperties(Classi expectedClassi) {
        assertClassiAllPropertiesEquals(expectedClassi, getPersistedClassi(expectedClassi));
    }

    protected void assertPersistedClassiToMatchUpdatableProperties(Classi expectedClassi) {
        assertClassiAllUpdatablePropertiesEquals(expectedClassi, getPersistedClassi(expectedClassi));
    }
}
