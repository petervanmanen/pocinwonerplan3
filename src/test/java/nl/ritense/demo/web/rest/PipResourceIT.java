package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PipAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Pip;
import nl.ritense.demo.repository.PipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PipResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PipResourceIT {

    private static final String ENTITY_API_URL = "/api/pips";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PipRepository pipRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPipMockMvc;

    private Pip pip;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pip createEntity(EntityManager em) {
        Pip pip = new Pip();
        return pip;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pip createUpdatedEntity(EntityManager em) {
        Pip pip = new Pip();
        return pip;
    }

    @BeforeEach
    public void initTest() {
        pip = createEntity(em);
    }

    @Test
    @Transactional
    void createPip() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Pip
        var returnedPip = om.readValue(
            restPipMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pip)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Pip.class
        );

        // Validate the Pip in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPipUpdatableFieldsEquals(returnedPip, getPersistedPip(returnedPip));
    }

    @Test
    @Transactional
    void createPipWithExistingId() throws Exception {
        // Create the Pip with an existing ID
        pip.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPipMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pip)))
            .andExpect(status().isBadRequest());

        // Validate the Pip in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPips() throws Exception {
        // Initialize the database
        pipRepository.saveAndFlush(pip);

        // Get all the pipList
        restPipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pip.getId().intValue())));
    }

    @Test
    @Transactional
    void getPip() throws Exception {
        // Initialize the database
        pipRepository.saveAndFlush(pip);

        // Get the pip
        restPipMockMvc
            .perform(get(ENTITY_API_URL_ID, pip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pip.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPip() throws Exception {
        // Get the pip
        restPipMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deletePip() throws Exception {
        // Initialize the database
        pipRepository.saveAndFlush(pip);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pip
        restPipMockMvc.perform(delete(ENTITY_API_URL_ID, pip.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pipRepository.count();
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

    protected Pip getPersistedPip(Pip pip) {
        return pipRepository.findById(pip.getId()).orElseThrow();
    }

    protected void assertPersistedPipToMatchAllProperties(Pip expectedPip) {
        assertPipAllPropertiesEquals(expectedPip, getPersistedPip(expectedPip));
    }

    protected void assertPersistedPipToMatchUpdatableProperties(Pip expectedPip) {
        assertPipAllUpdatablePropertiesEquals(expectedPip, getPersistedPip(expectedPip));
    }
}
