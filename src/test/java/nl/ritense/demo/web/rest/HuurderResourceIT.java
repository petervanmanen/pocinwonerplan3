package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.HuurderAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Huurder;
import nl.ritense.demo.repository.HuurderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HuurderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HuurderResourceIT {

    private static final String ENTITY_API_URL = "/api/huurders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HuurderRepository huurderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHuurderMockMvc;

    private Huurder huurder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Huurder createEntity(EntityManager em) {
        Huurder huurder = new Huurder();
        return huurder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Huurder createUpdatedEntity(EntityManager em) {
        Huurder huurder = new Huurder();
        return huurder;
    }

    @BeforeEach
    public void initTest() {
        huurder = createEntity(em);
    }

    @Test
    @Transactional
    void createHuurder() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Huurder
        var returnedHuurder = om.readValue(
            restHuurderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(huurder)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Huurder.class
        );

        // Validate the Huurder in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHuurderUpdatableFieldsEquals(returnedHuurder, getPersistedHuurder(returnedHuurder));
    }

    @Test
    @Transactional
    void createHuurderWithExistingId() throws Exception {
        // Create the Huurder with an existing ID
        huurder.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHuurderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(huurder)))
            .andExpect(status().isBadRequest());

        // Validate the Huurder in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHuurders() throws Exception {
        // Initialize the database
        huurderRepository.saveAndFlush(huurder);

        // Get all the huurderList
        restHuurderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(huurder.getId().intValue())));
    }

    @Test
    @Transactional
    void getHuurder() throws Exception {
        // Initialize the database
        huurderRepository.saveAndFlush(huurder);

        // Get the huurder
        restHuurderMockMvc
            .perform(get(ENTITY_API_URL_ID, huurder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(huurder.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingHuurder() throws Exception {
        // Get the huurder
        restHuurderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteHuurder() throws Exception {
        // Initialize the database
        huurderRepository.saveAndFlush(huurder);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the huurder
        restHuurderMockMvc
            .perform(delete(ENTITY_API_URL_ID, huurder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return huurderRepository.count();
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

    protected Huurder getPersistedHuurder(Huurder huurder) {
        return huurderRepository.findById(huurder.getId()).orElseThrow();
    }

    protected void assertPersistedHuurderToMatchAllProperties(Huurder expectedHuurder) {
        assertHuurderAllPropertiesEquals(expectedHuurder, getPersistedHuurder(expectedHuurder));
    }

    protected void assertPersistedHuurderToMatchUpdatableProperties(Huurder expectedHuurder) {
        assertHuurderAllUpdatablePropertiesEquals(expectedHuurder, getPersistedHuurder(expectedHuurder));
    }
}
