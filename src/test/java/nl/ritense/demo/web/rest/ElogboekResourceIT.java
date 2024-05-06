package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ElogboekAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Elogboek;
import nl.ritense.demo.repository.ElogboekRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ElogboekResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ElogboekResourceIT {

    private static final String ENTITY_API_URL = "/api/elogboeks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ElogboekRepository elogboekRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restElogboekMockMvc;

    private Elogboek elogboek;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Elogboek createEntity(EntityManager em) {
        Elogboek elogboek = new Elogboek();
        return elogboek;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Elogboek createUpdatedEntity(EntityManager em) {
        Elogboek elogboek = new Elogboek();
        return elogboek;
    }

    @BeforeEach
    public void initTest() {
        elogboek = createEntity(em);
    }

    @Test
    @Transactional
    void createElogboek() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Elogboek
        var returnedElogboek = om.readValue(
            restElogboekMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(elogboek)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Elogboek.class
        );

        // Validate the Elogboek in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertElogboekUpdatableFieldsEquals(returnedElogboek, getPersistedElogboek(returnedElogboek));
    }

    @Test
    @Transactional
    void createElogboekWithExistingId() throws Exception {
        // Create the Elogboek with an existing ID
        elogboek.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restElogboekMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(elogboek)))
            .andExpect(status().isBadRequest());

        // Validate the Elogboek in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllElogboeks() throws Exception {
        // Initialize the database
        elogboekRepository.saveAndFlush(elogboek);

        // Get all the elogboekList
        restElogboekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elogboek.getId().intValue())));
    }

    @Test
    @Transactional
    void getElogboek() throws Exception {
        // Initialize the database
        elogboekRepository.saveAndFlush(elogboek);

        // Get the elogboek
        restElogboekMockMvc
            .perform(get(ENTITY_API_URL_ID, elogboek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(elogboek.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingElogboek() throws Exception {
        // Get the elogboek
        restElogboekMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteElogboek() throws Exception {
        // Initialize the database
        elogboekRepository.saveAndFlush(elogboek);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the elogboek
        restElogboekMockMvc
            .perform(delete(ENTITY_API_URL_ID, elogboek.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return elogboekRepository.count();
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

    protected Elogboek getPersistedElogboek(Elogboek elogboek) {
        return elogboekRepository.findById(elogboek.getId()).orElseThrow();
    }

    protected void assertPersistedElogboekToMatchAllProperties(Elogboek expectedElogboek) {
        assertElogboekAllPropertiesEquals(expectedElogboek, getPersistedElogboek(expectedElogboek));
    }

    protected void assertPersistedElogboekToMatchUpdatableProperties(Elogboek expectedElogboek) {
        assertElogboekAllUpdatablePropertiesEquals(expectedElogboek, getPersistedElogboek(expectedElogboek));
    }
}
