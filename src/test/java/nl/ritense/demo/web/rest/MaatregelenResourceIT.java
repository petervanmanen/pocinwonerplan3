package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MaatregelenAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Maatregelen;
import nl.ritense.demo.repository.MaatregelenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MaatregelenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaatregelenResourceIT {

    private static final String ENTITY_API_URL = "/api/maatregelens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MaatregelenRepository maatregelenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaatregelenMockMvc;

    private Maatregelen maatregelen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Maatregelen createEntity(EntityManager em) {
        Maatregelen maatregelen = new Maatregelen();
        return maatregelen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Maatregelen createUpdatedEntity(EntityManager em) {
        Maatregelen maatregelen = new Maatregelen();
        return maatregelen;
    }

    @BeforeEach
    public void initTest() {
        maatregelen = createEntity(em);
    }

    @Test
    @Transactional
    void createMaatregelen() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Maatregelen
        var returnedMaatregelen = om.readValue(
            restMaatregelenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(maatregelen)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Maatregelen.class
        );

        // Validate the Maatregelen in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMaatregelenUpdatableFieldsEquals(returnedMaatregelen, getPersistedMaatregelen(returnedMaatregelen));
    }

    @Test
    @Transactional
    void createMaatregelenWithExistingId() throws Exception {
        // Create the Maatregelen with an existing ID
        maatregelen.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaatregelenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(maatregelen)))
            .andExpect(status().isBadRequest());

        // Validate the Maatregelen in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMaatregelens() throws Exception {
        // Initialize the database
        maatregelenRepository.saveAndFlush(maatregelen);

        // Get all the maatregelenList
        restMaatregelenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maatregelen.getId().intValue())));
    }

    @Test
    @Transactional
    void getMaatregelen() throws Exception {
        // Initialize the database
        maatregelenRepository.saveAndFlush(maatregelen);

        // Get the maatregelen
        restMaatregelenMockMvc
            .perform(get(ENTITY_API_URL_ID, maatregelen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(maatregelen.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingMaatregelen() throws Exception {
        // Get the maatregelen
        restMaatregelenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteMaatregelen() throws Exception {
        // Initialize the database
        maatregelenRepository.saveAndFlush(maatregelen);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the maatregelen
        restMaatregelenMockMvc
            .perform(delete(ENTITY_API_URL_ID, maatregelen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return maatregelenRepository.count();
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

    protected Maatregelen getPersistedMaatregelen(Maatregelen maatregelen) {
        return maatregelenRepository.findById(maatregelen.getId()).orElseThrow();
    }

    protected void assertPersistedMaatregelenToMatchAllProperties(Maatregelen expectedMaatregelen) {
        assertMaatregelenAllPropertiesEquals(expectedMaatregelen, getPersistedMaatregelen(expectedMaatregelen));
    }

    protected void assertPersistedMaatregelenToMatchUpdatableProperties(Maatregelen expectedMaatregelen) {
        assertMaatregelenAllUpdatablePropertiesEquals(expectedMaatregelen, getPersistedMaatregelen(expectedMaatregelen));
    }
}
