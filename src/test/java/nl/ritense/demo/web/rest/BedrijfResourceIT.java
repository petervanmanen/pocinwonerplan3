package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BedrijfAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Bedrijf;
import nl.ritense.demo.repository.BedrijfRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BedrijfResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BedrijfResourceIT {

    private static final String ENTITY_API_URL = "/api/bedrijfs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BedrijfRepository bedrijfRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBedrijfMockMvc;

    private Bedrijf bedrijf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bedrijf createEntity(EntityManager em) {
        Bedrijf bedrijf = new Bedrijf();
        return bedrijf;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bedrijf createUpdatedEntity(EntityManager em) {
        Bedrijf bedrijf = new Bedrijf();
        return bedrijf;
    }

    @BeforeEach
    public void initTest() {
        bedrijf = createEntity(em);
    }

    @Test
    @Transactional
    void createBedrijf() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bedrijf
        var returnedBedrijf = om.readValue(
            restBedrijfMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bedrijf)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bedrijf.class
        );

        // Validate the Bedrijf in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBedrijfUpdatableFieldsEquals(returnedBedrijf, getPersistedBedrijf(returnedBedrijf));
    }

    @Test
    @Transactional
    void createBedrijfWithExistingId() throws Exception {
        // Create the Bedrijf with an existing ID
        bedrijf.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBedrijfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bedrijf)))
            .andExpect(status().isBadRequest());

        // Validate the Bedrijf in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBedrijfs() throws Exception {
        // Initialize the database
        bedrijfRepository.saveAndFlush(bedrijf);

        // Get all the bedrijfList
        restBedrijfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bedrijf.getId().intValue())));
    }

    @Test
    @Transactional
    void getBedrijf() throws Exception {
        // Initialize the database
        bedrijfRepository.saveAndFlush(bedrijf);

        // Get the bedrijf
        restBedrijfMockMvc
            .perform(get(ENTITY_API_URL_ID, bedrijf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bedrijf.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBedrijf() throws Exception {
        // Get the bedrijf
        restBedrijfMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteBedrijf() throws Exception {
        // Initialize the database
        bedrijfRepository.saveAndFlush(bedrijf);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bedrijf
        restBedrijfMockMvc
            .perform(delete(ENTITY_API_URL_ID, bedrijf.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bedrijfRepository.count();
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

    protected Bedrijf getPersistedBedrijf(Bedrijf bedrijf) {
        return bedrijfRepository.findById(bedrijf.getId()).orElseThrow();
    }

    protected void assertPersistedBedrijfToMatchAllProperties(Bedrijf expectedBedrijf) {
        assertBedrijfAllPropertiesEquals(expectedBedrijf, getPersistedBedrijf(expectedBedrijf));
    }

    protected void assertPersistedBedrijfToMatchUpdatableProperties(Bedrijf expectedBedrijf) {
        assertBedrijfAllUpdatablePropertiesEquals(expectedBedrijf, getPersistedBedrijf(expectedBedrijf));
    }
}
