package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KwaliteitskenmerkenAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Kwaliteitskenmerken;
import nl.ritense.demo.repository.KwaliteitskenmerkenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KwaliteitskenmerkenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KwaliteitskenmerkenResourceIT {

    private static final String ENTITY_API_URL = "/api/kwaliteitskenmerkens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KwaliteitskenmerkenRepository kwaliteitskenmerkenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKwaliteitskenmerkenMockMvc;

    private Kwaliteitskenmerken kwaliteitskenmerken;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kwaliteitskenmerken createEntity(EntityManager em) {
        Kwaliteitskenmerken kwaliteitskenmerken = new Kwaliteitskenmerken();
        return kwaliteitskenmerken;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kwaliteitskenmerken createUpdatedEntity(EntityManager em) {
        Kwaliteitskenmerken kwaliteitskenmerken = new Kwaliteitskenmerken();
        return kwaliteitskenmerken;
    }

    @BeforeEach
    public void initTest() {
        kwaliteitskenmerken = createEntity(em);
    }

    @Test
    @Transactional
    void createKwaliteitskenmerken() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kwaliteitskenmerken
        var returnedKwaliteitskenmerken = om.readValue(
            restKwaliteitskenmerkenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kwaliteitskenmerken)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kwaliteitskenmerken.class
        );

        // Validate the Kwaliteitskenmerken in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKwaliteitskenmerkenUpdatableFieldsEquals(
            returnedKwaliteitskenmerken,
            getPersistedKwaliteitskenmerken(returnedKwaliteitskenmerken)
        );
    }

    @Test
    @Transactional
    void createKwaliteitskenmerkenWithExistingId() throws Exception {
        // Create the Kwaliteitskenmerken with an existing ID
        kwaliteitskenmerken.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKwaliteitskenmerkenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kwaliteitskenmerken)))
            .andExpect(status().isBadRequest());

        // Validate the Kwaliteitskenmerken in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKwaliteitskenmerkens() throws Exception {
        // Initialize the database
        kwaliteitskenmerkenRepository.saveAndFlush(kwaliteitskenmerken);

        // Get all the kwaliteitskenmerkenList
        restKwaliteitskenmerkenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kwaliteitskenmerken.getId().intValue())));
    }

    @Test
    @Transactional
    void getKwaliteitskenmerken() throws Exception {
        // Initialize the database
        kwaliteitskenmerkenRepository.saveAndFlush(kwaliteitskenmerken);

        // Get the kwaliteitskenmerken
        restKwaliteitskenmerkenMockMvc
            .perform(get(ENTITY_API_URL_ID, kwaliteitskenmerken.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kwaliteitskenmerken.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingKwaliteitskenmerken() throws Exception {
        // Get the kwaliteitskenmerken
        restKwaliteitskenmerkenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteKwaliteitskenmerken() throws Exception {
        // Initialize the database
        kwaliteitskenmerkenRepository.saveAndFlush(kwaliteitskenmerken);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kwaliteitskenmerken
        restKwaliteitskenmerkenMockMvc
            .perform(delete(ENTITY_API_URL_ID, kwaliteitskenmerken.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kwaliteitskenmerkenRepository.count();
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

    protected Kwaliteitskenmerken getPersistedKwaliteitskenmerken(Kwaliteitskenmerken kwaliteitskenmerken) {
        return kwaliteitskenmerkenRepository.findById(kwaliteitskenmerken.getId()).orElseThrow();
    }

    protected void assertPersistedKwaliteitskenmerkenToMatchAllProperties(Kwaliteitskenmerken expectedKwaliteitskenmerken) {
        assertKwaliteitskenmerkenAllPropertiesEquals(
            expectedKwaliteitskenmerken,
            getPersistedKwaliteitskenmerken(expectedKwaliteitskenmerken)
        );
    }

    protected void assertPersistedKwaliteitskenmerkenToMatchUpdatableProperties(Kwaliteitskenmerken expectedKwaliteitskenmerken) {
        assertKwaliteitskenmerkenAllUpdatablePropertiesEquals(
            expectedKwaliteitskenmerken,
            getPersistedKwaliteitskenmerken(expectedKwaliteitskenmerken)
        );
    }
}
