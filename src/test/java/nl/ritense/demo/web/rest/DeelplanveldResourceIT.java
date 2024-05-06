package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DeelplanveldAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Deelplanveld;
import nl.ritense.demo.repository.DeelplanveldRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DeelplanveldResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeelplanveldResourceIT {

    private static final String ENTITY_API_URL = "/api/deelplanvelds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DeelplanveldRepository deelplanveldRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeelplanveldMockMvc;

    private Deelplanveld deelplanveld;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deelplanveld createEntity(EntityManager em) {
        Deelplanveld deelplanveld = new Deelplanveld();
        return deelplanveld;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deelplanveld createUpdatedEntity(EntityManager em) {
        Deelplanveld deelplanveld = new Deelplanveld();
        return deelplanveld;
    }

    @BeforeEach
    public void initTest() {
        deelplanveld = createEntity(em);
    }

    @Test
    @Transactional
    void createDeelplanveld() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Deelplanveld
        var returnedDeelplanveld = om.readValue(
            restDeelplanveldMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(deelplanveld)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Deelplanveld.class
        );

        // Validate the Deelplanveld in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDeelplanveldUpdatableFieldsEquals(returnedDeelplanveld, getPersistedDeelplanveld(returnedDeelplanveld));
    }

    @Test
    @Transactional
    void createDeelplanveldWithExistingId() throws Exception {
        // Create the Deelplanveld with an existing ID
        deelplanveld.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeelplanveldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(deelplanveld)))
            .andExpect(status().isBadRequest());

        // Validate the Deelplanveld in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeelplanvelds() throws Exception {
        // Initialize the database
        deelplanveldRepository.saveAndFlush(deelplanveld);

        // Get all the deelplanveldList
        restDeelplanveldMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deelplanveld.getId().intValue())));
    }

    @Test
    @Transactional
    void getDeelplanveld() throws Exception {
        // Initialize the database
        deelplanveldRepository.saveAndFlush(deelplanveld);

        // Get the deelplanveld
        restDeelplanveldMockMvc
            .perform(get(ENTITY_API_URL_ID, deelplanveld.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deelplanveld.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingDeelplanveld() throws Exception {
        // Get the deelplanveld
        restDeelplanveldMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteDeelplanveld() throws Exception {
        // Initialize the database
        deelplanveldRepository.saveAndFlush(deelplanveld);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the deelplanveld
        restDeelplanveldMockMvc
            .perform(delete(ENTITY_API_URL_ID, deelplanveld.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return deelplanveldRepository.count();
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

    protected Deelplanveld getPersistedDeelplanveld(Deelplanveld deelplanveld) {
        return deelplanveldRepository.findById(deelplanveld.getId()).orElseThrow();
    }

    protected void assertPersistedDeelplanveldToMatchAllProperties(Deelplanveld expectedDeelplanveld) {
        assertDeelplanveldAllPropertiesEquals(expectedDeelplanveld, getPersistedDeelplanveld(expectedDeelplanveld));
    }

    protected void assertPersistedDeelplanveldToMatchUpdatableProperties(Deelplanveld expectedDeelplanveld) {
        assertDeelplanveldAllUpdatablePropertiesEquals(expectedDeelplanveld, getPersistedDeelplanveld(expectedDeelplanveld));
    }
}
