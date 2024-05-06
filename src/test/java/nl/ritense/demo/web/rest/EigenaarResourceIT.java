package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EigenaarAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Eigenaar;
import nl.ritense.demo.repository.EigenaarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EigenaarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EigenaarResourceIT {

    private static final String ENTITY_API_URL = "/api/eigenaars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EigenaarRepository eigenaarRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEigenaarMockMvc;

    private Eigenaar eigenaar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eigenaar createEntity(EntityManager em) {
        Eigenaar eigenaar = new Eigenaar();
        return eigenaar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eigenaar createUpdatedEntity(EntityManager em) {
        Eigenaar eigenaar = new Eigenaar();
        return eigenaar;
    }

    @BeforeEach
    public void initTest() {
        eigenaar = createEntity(em);
    }

    @Test
    @Transactional
    void createEigenaar() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Eigenaar
        var returnedEigenaar = om.readValue(
            restEigenaarMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eigenaar)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Eigenaar.class
        );

        // Validate the Eigenaar in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEigenaarUpdatableFieldsEquals(returnedEigenaar, getPersistedEigenaar(returnedEigenaar));
    }

    @Test
    @Transactional
    void createEigenaarWithExistingId() throws Exception {
        // Create the Eigenaar with an existing ID
        eigenaar.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEigenaarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eigenaar)))
            .andExpect(status().isBadRequest());

        // Validate the Eigenaar in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEigenaars() throws Exception {
        // Initialize the database
        eigenaarRepository.saveAndFlush(eigenaar);

        // Get all the eigenaarList
        restEigenaarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eigenaar.getId().intValue())));
    }

    @Test
    @Transactional
    void getEigenaar() throws Exception {
        // Initialize the database
        eigenaarRepository.saveAndFlush(eigenaar);

        // Get the eigenaar
        restEigenaarMockMvc
            .perform(get(ENTITY_API_URL_ID, eigenaar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eigenaar.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingEigenaar() throws Exception {
        // Get the eigenaar
        restEigenaarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteEigenaar() throws Exception {
        // Initialize the database
        eigenaarRepository.saveAndFlush(eigenaar);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eigenaar
        restEigenaarMockMvc
            .perform(delete(ENTITY_API_URL_ID, eigenaar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return eigenaarRepository.count();
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

    protected Eigenaar getPersistedEigenaar(Eigenaar eigenaar) {
        return eigenaarRepository.findById(eigenaar.getId()).orElseThrow();
    }

    protected void assertPersistedEigenaarToMatchAllProperties(Eigenaar expectedEigenaar) {
        assertEigenaarAllPropertiesEquals(expectedEigenaar, getPersistedEigenaar(expectedEigenaar));
    }

    protected void assertPersistedEigenaarToMatchUpdatableProperties(Eigenaar expectedEigenaar) {
        assertEigenaarAllUpdatablePropertiesEquals(expectedEigenaar, getPersistedEigenaar(expectedEigenaar));
    }
}
