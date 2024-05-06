package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OmgevingsdocumentAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Omgevingsdocument;
import nl.ritense.demo.repository.OmgevingsdocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OmgevingsdocumentResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class OmgevingsdocumentResourceIT {

    private static final String ENTITY_API_URL = "/api/omgevingsdocuments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OmgevingsdocumentRepository omgevingsdocumentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOmgevingsdocumentMockMvc;

    private Omgevingsdocument omgevingsdocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Omgevingsdocument createEntity(EntityManager em) {
        Omgevingsdocument omgevingsdocument = new Omgevingsdocument();
        return omgevingsdocument;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Omgevingsdocument createUpdatedEntity(EntityManager em) {
        Omgevingsdocument omgevingsdocument = new Omgevingsdocument();
        return omgevingsdocument;
    }

    @BeforeEach
    public void initTest() {
        omgevingsdocument = createEntity(em);
    }

    @Test
    @Transactional
    void createOmgevingsdocument() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Omgevingsdocument
        var returnedOmgevingsdocument = om.readValue(
            restOmgevingsdocumentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omgevingsdocument)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Omgevingsdocument.class
        );

        // Validate the Omgevingsdocument in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOmgevingsdocumentUpdatableFieldsEquals(returnedOmgevingsdocument, getPersistedOmgevingsdocument(returnedOmgevingsdocument));
    }

    @Test
    @Transactional
    void createOmgevingsdocumentWithExistingId() throws Exception {
        // Create the Omgevingsdocument with an existing ID
        omgevingsdocument.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOmgevingsdocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omgevingsdocument)))
            .andExpect(status().isBadRequest());

        // Validate the Omgevingsdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOmgevingsdocuments() throws Exception {
        // Initialize the database
        omgevingsdocumentRepository.saveAndFlush(omgevingsdocument);

        // Get all the omgevingsdocumentList
        restOmgevingsdocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(omgevingsdocument.getId().intValue())));
    }

    @Test
    @Transactional
    void getOmgevingsdocument() throws Exception {
        // Initialize the database
        omgevingsdocumentRepository.saveAndFlush(omgevingsdocument);

        // Get the omgevingsdocument
        restOmgevingsdocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, omgevingsdocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(omgevingsdocument.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOmgevingsdocument() throws Exception {
        // Get the omgevingsdocument
        restOmgevingsdocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteOmgevingsdocument() throws Exception {
        // Initialize the database
        omgevingsdocumentRepository.saveAndFlush(omgevingsdocument);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the omgevingsdocument
        restOmgevingsdocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, omgevingsdocument.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return omgevingsdocumentRepository.count();
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

    protected Omgevingsdocument getPersistedOmgevingsdocument(Omgevingsdocument omgevingsdocument) {
        return omgevingsdocumentRepository.findById(omgevingsdocument.getId()).orElseThrow();
    }

    protected void assertPersistedOmgevingsdocumentToMatchAllProperties(Omgevingsdocument expectedOmgevingsdocument) {
        assertOmgevingsdocumentAllPropertiesEquals(expectedOmgevingsdocument, getPersistedOmgevingsdocument(expectedOmgevingsdocument));
    }

    protected void assertPersistedOmgevingsdocumentToMatchUpdatableProperties(Omgevingsdocument expectedOmgevingsdocument) {
        assertOmgevingsdocumentAllUpdatablePropertiesEquals(
            expectedOmgevingsdocument,
            getPersistedOmgevingsdocument(expectedOmgevingsdocument)
        );
    }
}
