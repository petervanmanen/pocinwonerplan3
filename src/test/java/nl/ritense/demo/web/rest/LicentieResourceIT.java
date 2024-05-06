package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LicentieAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Licentie;
import nl.ritense.demo.repository.LicentieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LicentieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LicentieResourceIT {

    private static final String ENTITY_API_URL = "/api/licenties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LicentieRepository licentieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLicentieMockMvc;

    private Licentie licentie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Licentie createEntity(EntityManager em) {
        Licentie licentie = new Licentie();
        return licentie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Licentie createUpdatedEntity(EntityManager em) {
        Licentie licentie = new Licentie();
        return licentie;
    }

    @BeforeEach
    public void initTest() {
        licentie = createEntity(em);
    }

    @Test
    @Transactional
    void createLicentie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Licentie
        var returnedLicentie = om.readValue(
            restLicentieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(licentie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Licentie.class
        );

        // Validate the Licentie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLicentieUpdatableFieldsEquals(returnedLicentie, getPersistedLicentie(returnedLicentie));
    }

    @Test
    @Transactional
    void createLicentieWithExistingId() throws Exception {
        // Create the Licentie with an existing ID
        licentie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLicentieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(licentie)))
            .andExpect(status().isBadRequest());

        // Validate the Licentie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLicenties() throws Exception {
        // Initialize the database
        licentieRepository.saveAndFlush(licentie);

        // Get all the licentieList
        restLicentieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licentie.getId().intValue())));
    }

    @Test
    @Transactional
    void getLicentie() throws Exception {
        // Initialize the database
        licentieRepository.saveAndFlush(licentie);

        // Get the licentie
        restLicentieMockMvc
            .perform(get(ENTITY_API_URL_ID, licentie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(licentie.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingLicentie() throws Exception {
        // Get the licentie
        restLicentieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteLicentie() throws Exception {
        // Initialize the database
        licentieRepository.saveAndFlush(licentie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the licentie
        restLicentieMockMvc
            .perform(delete(ENTITY_API_URL_ID, licentie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return licentieRepository.count();
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

    protected Licentie getPersistedLicentie(Licentie licentie) {
        return licentieRepository.findById(licentie.getId()).orElseThrow();
    }

    protected void assertPersistedLicentieToMatchAllProperties(Licentie expectedLicentie) {
        assertLicentieAllPropertiesEquals(expectedLicentie, getPersistedLicentie(expectedLicentie));
    }

    protected void assertPersistedLicentieToMatchUpdatableProperties(Licentie expectedLicentie) {
        assertLicentieAllUpdatablePropertiesEquals(expectedLicentie, getPersistedLicentie(expectedLicentie));
    }
}
