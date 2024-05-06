package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BoaAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Boa;
import nl.ritense.demo.repository.BoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BoaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BoaResourceIT {

    private static final String ENTITY_API_URL = "/api/boas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BoaRepository boaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBoaMockMvc;

    private Boa boa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Boa createEntity(EntityManager em) {
        Boa boa = new Boa();
        return boa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Boa createUpdatedEntity(EntityManager em) {
        Boa boa = new Boa();
        return boa;
    }

    @BeforeEach
    public void initTest() {
        boa = createEntity(em);
    }

    @Test
    @Transactional
    void createBoa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Boa
        var returnedBoa = om.readValue(
            restBoaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Boa.class
        );

        // Validate the Boa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBoaUpdatableFieldsEquals(returnedBoa, getPersistedBoa(returnedBoa));
    }

    @Test
    @Transactional
    void createBoaWithExistingId() throws Exception {
        // Create the Boa with an existing ID
        boa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boa)))
            .andExpect(status().isBadRequest());

        // Validate the Boa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBoas() throws Exception {
        // Initialize the database
        boaRepository.saveAndFlush(boa);

        // Get all the boaList
        restBoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boa.getId().intValue())));
    }

    @Test
    @Transactional
    void getBoa() throws Exception {
        // Initialize the database
        boaRepository.saveAndFlush(boa);

        // Get the boa
        restBoaMockMvc
            .perform(get(ENTITY_API_URL_ID, boa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(boa.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBoa() throws Exception {
        // Get the boa
        restBoaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteBoa() throws Exception {
        // Initialize the database
        boaRepository.saveAndFlush(boa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the boa
        restBoaMockMvc.perform(delete(ENTITY_API_URL_ID, boa.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return boaRepository.count();
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

    protected Boa getPersistedBoa(Boa boa) {
        return boaRepository.findById(boa.getId()).orElseThrow();
    }

    protected void assertPersistedBoaToMatchAllProperties(Boa expectedBoa) {
        assertBoaAllPropertiesEquals(expectedBoa, getPersistedBoa(expectedBoa));
    }

    protected void assertPersistedBoaToMatchUpdatableProperties(Boa expectedBoa) {
        assertBoaAllUpdatablePropertiesEquals(expectedBoa, getPersistedBoa(expectedBoa));
    }
}
