package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ToegangsmiddelAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Toegangsmiddel;
import nl.ritense.demo.repository.ToegangsmiddelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ToegangsmiddelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ToegangsmiddelResourceIT {

    private static final String ENTITY_API_URL = "/api/toegangsmiddels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ToegangsmiddelRepository toegangsmiddelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restToegangsmiddelMockMvc;

    private Toegangsmiddel toegangsmiddel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Toegangsmiddel createEntity(EntityManager em) {
        Toegangsmiddel toegangsmiddel = new Toegangsmiddel();
        return toegangsmiddel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Toegangsmiddel createUpdatedEntity(EntityManager em) {
        Toegangsmiddel toegangsmiddel = new Toegangsmiddel();
        return toegangsmiddel;
    }

    @BeforeEach
    public void initTest() {
        toegangsmiddel = createEntity(em);
    }

    @Test
    @Transactional
    void createToegangsmiddel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Toegangsmiddel
        var returnedToegangsmiddel = om.readValue(
            restToegangsmiddelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(toegangsmiddel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Toegangsmiddel.class
        );

        // Validate the Toegangsmiddel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertToegangsmiddelUpdatableFieldsEquals(returnedToegangsmiddel, getPersistedToegangsmiddel(returnedToegangsmiddel));
    }

    @Test
    @Transactional
    void createToegangsmiddelWithExistingId() throws Exception {
        // Create the Toegangsmiddel with an existing ID
        toegangsmiddel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restToegangsmiddelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(toegangsmiddel)))
            .andExpect(status().isBadRequest());

        // Validate the Toegangsmiddel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllToegangsmiddels() throws Exception {
        // Initialize the database
        toegangsmiddelRepository.saveAndFlush(toegangsmiddel);

        // Get all the toegangsmiddelList
        restToegangsmiddelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(toegangsmiddel.getId().intValue())));
    }

    @Test
    @Transactional
    void getToegangsmiddel() throws Exception {
        // Initialize the database
        toegangsmiddelRepository.saveAndFlush(toegangsmiddel);

        // Get the toegangsmiddel
        restToegangsmiddelMockMvc
            .perform(get(ENTITY_API_URL_ID, toegangsmiddel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(toegangsmiddel.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingToegangsmiddel() throws Exception {
        // Get the toegangsmiddel
        restToegangsmiddelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteToegangsmiddel() throws Exception {
        // Initialize the database
        toegangsmiddelRepository.saveAndFlush(toegangsmiddel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the toegangsmiddel
        restToegangsmiddelMockMvc
            .perform(delete(ENTITY_API_URL_ID, toegangsmiddel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return toegangsmiddelRepository.count();
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

    protected Toegangsmiddel getPersistedToegangsmiddel(Toegangsmiddel toegangsmiddel) {
        return toegangsmiddelRepository.findById(toegangsmiddel.getId()).orElseThrow();
    }

    protected void assertPersistedToegangsmiddelToMatchAllProperties(Toegangsmiddel expectedToegangsmiddel) {
        assertToegangsmiddelAllPropertiesEquals(expectedToegangsmiddel, getPersistedToegangsmiddel(expectedToegangsmiddel));
    }

    protected void assertPersistedToegangsmiddelToMatchUpdatableProperties(Toegangsmiddel expectedToegangsmiddel) {
        assertToegangsmiddelAllUpdatablePropertiesEquals(expectedToegangsmiddel, getPersistedToegangsmiddel(expectedToegangsmiddel));
    }
}
