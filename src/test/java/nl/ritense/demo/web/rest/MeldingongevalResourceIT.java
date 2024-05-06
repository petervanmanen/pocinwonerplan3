package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MeldingongevalAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Meldingongeval;
import nl.ritense.demo.repository.MeldingongevalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MeldingongevalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MeldingongevalResourceIT {

    private static final String ENTITY_API_URL = "/api/meldingongevals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MeldingongevalRepository meldingongevalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMeldingongevalMockMvc;

    private Meldingongeval meldingongeval;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meldingongeval createEntity(EntityManager em) {
        Meldingongeval meldingongeval = new Meldingongeval();
        return meldingongeval;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meldingongeval createUpdatedEntity(EntityManager em) {
        Meldingongeval meldingongeval = new Meldingongeval();
        return meldingongeval;
    }

    @BeforeEach
    public void initTest() {
        meldingongeval = createEntity(em);
    }

    @Test
    @Transactional
    void createMeldingongeval() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Meldingongeval
        var returnedMeldingongeval = om.readValue(
            restMeldingongevalMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(meldingongeval)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Meldingongeval.class
        );

        // Validate the Meldingongeval in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMeldingongevalUpdatableFieldsEquals(returnedMeldingongeval, getPersistedMeldingongeval(returnedMeldingongeval));
    }

    @Test
    @Transactional
    void createMeldingongevalWithExistingId() throws Exception {
        // Create the Meldingongeval with an existing ID
        meldingongeval.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeldingongevalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(meldingongeval)))
            .andExpect(status().isBadRequest());

        // Validate the Meldingongeval in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMeldingongevals() throws Exception {
        // Initialize the database
        meldingongevalRepository.saveAndFlush(meldingongeval);

        // Get all the meldingongevalList
        restMeldingongevalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meldingongeval.getId().intValue())));
    }

    @Test
    @Transactional
    void getMeldingongeval() throws Exception {
        // Initialize the database
        meldingongevalRepository.saveAndFlush(meldingongeval);

        // Get the meldingongeval
        restMeldingongevalMockMvc
            .perform(get(ENTITY_API_URL_ID, meldingongeval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(meldingongeval.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingMeldingongeval() throws Exception {
        // Get the meldingongeval
        restMeldingongevalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteMeldingongeval() throws Exception {
        // Initialize the database
        meldingongevalRepository.saveAndFlush(meldingongeval);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the meldingongeval
        restMeldingongevalMockMvc
            .perform(delete(ENTITY_API_URL_ID, meldingongeval.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return meldingongevalRepository.count();
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

    protected Meldingongeval getPersistedMeldingongeval(Meldingongeval meldingongeval) {
        return meldingongevalRepository.findById(meldingongeval.getId()).orElseThrow();
    }

    protected void assertPersistedMeldingongevalToMatchAllProperties(Meldingongeval expectedMeldingongeval) {
        assertMeldingongevalAllPropertiesEquals(expectedMeldingongeval, getPersistedMeldingongeval(expectedMeldingongeval));
    }

    protected void assertPersistedMeldingongevalToMatchUpdatableProperties(Meldingongeval expectedMeldingongeval) {
        assertMeldingongevalAllUpdatablePropertiesEquals(expectedMeldingongeval, getPersistedMeldingongeval(expectedMeldingongeval));
    }
}
