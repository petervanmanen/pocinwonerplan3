package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SamengestelddocumentAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Samengestelddocument;
import nl.ritense.demo.repository.SamengestelddocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SamengestelddocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SamengestelddocumentResourceIT {

    private static final String ENTITY_API_URL = "/api/samengestelddocuments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SamengestelddocumentRepository samengestelddocumentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSamengestelddocumentMockMvc;

    private Samengestelddocument samengestelddocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Samengestelddocument createEntity(EntityManager em) {
        Samengestelddocument samengestelddocument = new Samengestelddocument();
        return samengestelddocument;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Samengestelddocument createUpdatedEntity(EntityManager em) {
        Samengestelddocument samengestelddocument = new Samengestelddocument();
        return samengestelddocument;
    }

    @BeforeEach
    public void initTest() {
        samengestelddocument = createEntity(em);
    }

    @Test
    @Transactional
    void createSamengestelddocument() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Samengestelddocument
        var returnedSamengestelddocument = om.readValue(
            restSamengestelddocumentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(samengestelddocument)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Samengestelddocument.class
        );

        // Validate the Samengestelddocument in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSamengestelddocumentUpdatableFieldsEquals(
            returnedSamengestelddocument,
            getPersistedSamengestelddocument(returnedSamengestelddocument)
        );
    }

    @Test
    @Transactional
    void createSamengestelddocumentWithExistingId() throws Exception {
        // Create the Samengestelddocument with an existing ID
        samengestelddocument.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSamengestelddocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(samengestelddocument)))
            .andExpect(status().isBadRequest());

        // Validate the Samengestelddocument in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSamengestelddocuments() throws Exception {
        // Initialize the database
        samengestelddocumentRepository.saveAndFlush(samengestelddocument);

        // Get all the samengestelddocumentList
        restSamengestelddocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(samengestelddocument.getId().intValue())));
    }

    @Test
    @Transactional
    void getSamengestelddocument() throws Exception {
        // Initialize the database
        samengestelddocumentRepository.saveAndFlush(samengestelddocument);

        // Get the samengestelddocument
        restSamengestelddocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, samengestelddocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(samengestelddocument.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingSamengestelddocument() throws Exception {
        // Get the samengestelddocument
        restSamengestelddocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteSamengestelddocument() throws Exception {
        // Initialize the database
        samengestelddocumentRepository.saveAndFlush(samengestelddocument);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the samengestelddocument
        restSamengestelddocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, samengestelddocument.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return samengestelddocumentRepository.count();
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

    protected Samengestelddocument getPersistedSamengestelddocument(Samengestelddocument samengestelddocument) {
        return samengestelddocumentRepository.findById(samengestelddocument.getId()).orElseThrow();
    }

    protected void assertPersistedSamengestelddocumentToMatchAllProperties(Samengestelddocument expectedSamengestelddocument) {
        assertSamengestelddocumentAllPropertiesEquals(
            expectedSamengestelddocument,
            getPersistedSamengestelddocument(expectedSamengestelddocument)
        );
    }

    protected void assertPersistedSamengestelddocumentToMatchUpdatableProperties(Samengestelddocument expectedSamengestelddocument) {
        assertSamengestelddocumentAllUpdatablePropertiesEquals(
            expectedSamengestelddocument,
            getPersistedSamengestelddocument(expectedSamengestelddocument)
        );
    }
}
