package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AppartementsrechtAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Appartementsrecht;
import nl.ritense.demo.repository.AppartementsrechtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AppartementsrechtResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppartementsrechtResourceIT {

    private static final String ENTITY_API_URL = "/api/appartementsrechts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AppartementsrechtRepository appartementsrechtRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppartementsrechtMockMvc;

    private Appartementsrecht appartementsrecht;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appartementsrecht createEntity(EntityManager em) {
        Appartementsrecht appartementsrecht = new Appartementsrecht();
        return appartementsrecht;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appartementsrecht createUpdatedEntity(EntityManager em) {
        Appartementsrecht appartementsrecht = new Appartementsrecht();
        return appartementsrecht;
    }

    @BeforeEach
    public void initTest() {
        appartementsrecht = createEntity(em);
    }

    @Test
    @Transactional
    void createAppartementsrecht() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Appartementsrecht
        var returnedAppartementsrecht = om.readValue(
            restAppartementsrechtMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appartementsrecht)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Appartementsrecht.class
        );

        // Validate the Appartementsrecht in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAppartementsrechtUpdatableFieldsEquals(returnedAppartementsrecht, getPersistedAppartementsrecht(returnedAppartementsrecht));
    }

    @Test
    @Transactional
    void createAppartementsrechtWithExistingId() throws Exception {
        // Create the Appartementsrecht with an existing ID
        appartementsrecht.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppartementsrechtMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appartementsrecht)))
            .andExpect(status().isBadRequest());

        // Validate the Appartementsrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppartementsrechts() throws Exception {
        // Initialize the database
        appartementsrechtRepository.saveAndFlush(appartementsrecht);

        // Get all the appartementsrechtList
        restAppartementsrechtMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appartementsrecht.getId().intValue())));
    }

    @Test
    @Transactional
    void getAppartementsrecht() throws Exception {
        // Initialize the database
        appartementsrechtRepository.saveAndFlush(appartementsrecht);

        // Get the appartementsrecht
        restAppartementsrechtMockMvc
            .perform(get(ENTITY_API_URL_ID, appartementsrecht.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appartementsrecht.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAppartementsrecht() throws Exception {
        // Get the appartementsrecht
        restAppartementsrechtMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteAppartementsrecht() throws Exception {
        // Initialize the database
        appartementsrechtRepository.saveAndFlush(appartementsrecht);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the appartementsrecht
        restAppartementsrechtMockMvc
            .perform(delete(ENTITY_API_URL_ID, appartementsrecht.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return appartementsrechtRepository.count();
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

    protected Appartementsrecht getPersistedAppartementsrecht(Appartementsrecht appartementsrecht) {
        return appartementsrechtRepository.findById(appartementsrecht.getId()).orElseThrow();
    }

    protected void assertPersistedAppartementsrechtToMatchAllProperties(Appartementsrecht expectedAppartementsrecht) {
        assertAppartementsrechtAllPropertiesEquals(expectedAppartementsrecht, getPersistedAppartementsrecht(expectedAppartementsrecht));
    }

    protected void assertPersistedAppartementsrechtToMatchUpdatableProperties(Appartementsrecht expectedAppartementsrecht) {
        assertAppartementsrechtAllUpdatablePropertiesEquals(
            expectedAppartementsrecht,
            getPersistedAppartementsrecht(expectedAppartementsrecht)
        );
    }
}
