package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NertwerkcomponentAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Nertwerkcomponent;
import nl.ritense.demo.repository.NertwerkcomponentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NertwerkcomponentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NertwerkcomponentResourceIT {

    private static final String ENTITY_API_URL = "/api/nertwerkcomponents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NertwerkcomponentRepository nertwerkcomponentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNertwerkcomponentMockMvc;

    private Nertwerkcomponent nertwerkcomponent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nertwerkcomponent createEntity(EntityManager em) {
        Nertwerkcomponent nertwerkcomponent = new Nertwerkcomponent();
        return nertwerkcomponent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nertwerkcomponent createUpdatedEntity(EntityManager em) {
        Nertwerkcomponent nertwerkcomponent = new Nertwerkcomponent();
        return nertwerkcomponent;
    }

    @BeforeEach
    public void initTest() {
        nertwerkcomponent = createEntity(em);
    }

    @Test
    @Transactional
    void createNertwerkcomponent() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Nertwerkcomponent
        var returnedNertwerkcomponent = om.readValue(
            restNertwerkcomponentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nertwerkcomponent)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Nertwerkcomponent.class
        );

        // Validate the Nertwerkcomponent in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNertwerkcomponentUpdatableFieldsEquals(returnedNertwerkcomponent, getPersistedNertwerkcomponent(returnedNertwerkcomponent));
    }

    @Test
    @Transactional
    void createNertwerkcomponentWithExistingId() throws Exception {
        // Create the Nertwerkcomponent with an existing ID
        nertwerkcomponent.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNertwerkcomponentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nertwerkcomponent)))
            .andExpect(status().isBadRequest());

        // Validate the Nertwerkcomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNertwerkcomponents() throws Exception {
        // Initialize the database
        nertwerkcomponentRepository.saveAndFlush(nertwerkcomponent);

        // Get all the nertwerkcomponentList
        restNertwerkcomponentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nertwerkcomponent.getId().intValue())));
    }

    @Test
    @Transactional
    void getNertwerkcomponent() throws Exception {
        // Initialize the database
        nertwerkcomponentRepository.saveAndFlush(nertwerkcomponent);

        // Get the nertwerkcomponent
        restNertwerkcomponentMockMvc
            .perform(get(ENTITY_API_URL_ID, nertwerkcomponent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nertwerkcomponent.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingNertwerkcomponent() throws Exception {
        // Get the nertwerkcomponent
        restNertwerkcomponentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteNertwerkcomponent() throws Exception {
        // Initialize the database
        nertwerkcomponentRepository.saveAndFlush(nertwerkcomponent);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the nertwerkcomponent
        restNertwerkcomponentMockMvc
            .perform(delete(ENTITY_API_URL_ID, nertwerkcomponent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return nertwerkcomponentRepository.count();
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

    protected Nertwerkcomponent getPersistedNertwerkcomponent(Nertwerkcomponent nertwerkcomponent) {
        return nertwerkcomponentRepository.findById(nertwerkcomponent.getId()).orElseThrow();
    }

    protected void assertPersistedNertwerkcomponentToMatchAllProperties(Nertwerkcomponent expectedNertwerkcomponent) {
        assertNertwerkcomponentAllPropertiesEquals(expectedNertwerkcomponent, getPersistedNertwerkcomponent(expectedNertwerkcomponent));
    }

    protected void assertPersistedNertwerkcomponentToMatchUpdatableProperties(Nertwerkcomponent expectedNertwerkcomponent) {
        assertNertwerkcomponentAllUpdatablePropertiesEquals(
            expectedNertwerkcomponent,
            getPersistedNertwerkcomponent(expectedNertwerkcomponent)
        );
    }
}
