package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AanbestedingvastgoedAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Aanbestedingvastgoed;
import nl.ritense.demo.repository.AanbestedingvastgoedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AanbestedingvastgoedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AanbestedingvastgoedResourceIT {

    private static final String ENTITY_API_URL = "/api/aanbestedingvastgoeds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AanbestedingvastgoedRepository aanbestedingvastgoedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAanbestedingvastgoedMockMvc;

    private Aanbestedingvastgoed aanbestedingvastgoed;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanbestedingvastgoed createEntity(EntityManager em) {
        Aanbestedingvastgoed aanbestedingvastgoed = new Aanbestedingvastgoed();
        return aanbestedingvastgoed;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanbestedingvastgoed createUpdatedEntity(EntityManager em) {
        Aanbestedingvastgoed aanbestedingvastgoed = new Aanbestedingvastgoed();
        return aanbestedingvastgoed;
    }

    @BeforeEach
    public void initTest() {
        aanbestedingvastgoed = createEntity(em);
    }

    @Test
    @Transactional
    void createAanbestedingvastgoed() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aanbestedingvastgoed
        var returnedAanbestedingvastgoed = om.readValue(
            restAanbestedingvastgoedMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanbestedingvastgoed)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aanbestedingvastgoed.class
        );

        // Validate the Aanbestedingvastgoed in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAanbestedingvastgoedUpdatableFieldsEquals(
            returnedAanbestedingvastgoed,
            getPersistedAanbestedingvastgoed(returnedAanbestedingvastgoed)
        );
    }

    @Test
    @Transactional
    void createAanbestedingvastgoedWithExistingId() throws Exception {
        // Create the Aanbestedingvastgoed with an existing ID
        aanbestedingvastgoed.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAanbestedingvastgoedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanbestedingvastgoed)))
            .andExpect(status().isBadRequest());

        // Validate the Aanbestedingvastgoed in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAanbestedingvastgoeds() throws Exception {
        // Initialize the database
        aanbestedingvastgoedRepository.saveAndFlush(aanbestedingvastgoed);

        // Get all the aanbestedingvastgoedList
        restAanbestedingvastgoedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aanbestedingvastgoed.getId().intValue())));
    }

    @Test
    @Transactional
    void getAanbestedingvastgoed() throws Exception {
        // Initialize the database
        aanbestedingvastgoedRepository.saveAndFlush(aanbestedingvastgoed);

        // Get the aanbestedingvastgoed
        restAanbestedingvastgoedMockMvc
            .perform(get(ENTITY_API_URL_ID, aanbestedingvastgoed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aanbestedingvastgoed.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAanbestedingvastgoed() throws Exception {
        // Get the aanbestedingvastgoed
        restAanbestedingvastgoedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteAanbestedingvastgoed() throws Exception {
        // Initialize the database
        aanbestedingvastgoedRepository.saveAndFlush(aanbestedingvastgoed);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aanbestedingvastgoed
        restAanbestedingvastgoedMockMvc
            .perform(delete(ENTITY_API_URL_ID, aanbestedingvastgoed.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aanbestedingvastgoedRepository.count();
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

    protected Aanbestedingvastgoed getPersistedAanbestedingvastgoed(Aanbestedingvastgoed aanbestedingvastgoed) {
        return aanbestedingvastgoedRepository.findById(aanbestedingvastgoed.getId()).orElseThrow();
    }

    protected void assertPersistedAanbestedingvastgoedToMatchAllProperties(Aanbestedingvastgoed expectedAanbestedingvastgoed) {
        assertAanbestedingvastgoedAllPropertiesEquals(
            expectedAanbestedingvastgoed,
            getPersistedAanbestedingvastgoed(expectedAanbestedingvastgoed)
        );
    }

    protected void assertPersistedAanbestedingvastgoedToMatchUpdatableProperties(Aanbestedingvastgoed expectedAanbestedingvastgoed) {
        assertAanbestedingvastgoedAllUpdatablePropertiesEquals(
            expectedAanbestedingvastgoed,
            getPersistedAanbestedingvastgoed(expectedAanbestedingvastgoed)
        );
    }
}
