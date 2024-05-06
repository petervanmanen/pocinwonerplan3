package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GrondbeheerderAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Grondbeheerder;
import nl.ritense.demo.repository.GrondbeheerderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GrondbeheerderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GrondbeheerderResourceIT {

    private static final String ENTITY_API_URL = "/api/grondbeheerders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GrondbeheerderRepository grondbeheerderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrondbeheerderMockMvc;

    private Grondbeheerder grondbeheerder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grondbeheerder createEntity(EntityManager em) {
        Grondbeheerder grondbeheerder = new Grondbeheerder();
        return grondbeheerder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grondbeheerder createUpdatedEntity(EntityManager em) {
        Grondbeheerder grondbeheerder = new Grondbeheerder();
        return grondbeheerder;
    }

    @BeforeEach
    public void initTest() {
        grondbeheerder = createEntity(em);
    }

    @Test
    @Transactional
    void createGrondbeheerder() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Grondbeheerder
        var returnedGrondbeheerder = om.readValue(
            restGrondbeheerderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grondbeheerder)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Grondbeheerder.class
        );

        // Validate the Grondbeheerder in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGrondbeheerderUpdatableFieldsEquals(returnedGrondbeheerder, getPersistedGrondbeheerder(returnedGrondbeheerder));
    }

    @Test
    @Transactional
    void createGrondbeheerderWithExistingId() throws Exception {
        // Create the Grondbeheerder with an existing ID
        grondbeheerder.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrondbeheerderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grondbeheerder)))
            .andExpect(status().isBadRequest());

        // Validate the Grondbeheerder in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGrondbeheerders() throws Exception {
        // Initialize the database
        grondbeheerderRepository.saveAndFlush(grondbeheerder);

        // Get all the grondbeheerderList
        restGrondbeheerderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grondbeheerder.getId().intValue())));
    }

    @Test
    @Transactional
    void getGrondbeheerder() throws Exception {
        // Initialize the database
        grondbeheerderRepository.saveAndFlush(grondbeheerder);

        // Get the grondbeheerder
        restGrondbeheerderMockMvc
            .perform(get(ENTITY_API_URL_ID, grondbeheerder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grondbeheerder.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingGrondbeheerder() throws Exception {
        // Get the grondbeheerder
        restGrondbeheerderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteGrondbeheerder() throws Exception {
        // Initialize the database
        grondbeheerderRepository.saveAndFlush(grondbeheerder);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the grondbeheerder
        restGrondbeheerderMockMvc
            .perform(delete(ENTITY_API_URL_ID, grondbeheerder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return grondbeheerderRepository.count();
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

    protected Grondbeheerder getPersistedGrondbeheerder(Grondbeheerder grondbeheerder) {
        return grondbeheerderRepository.findById(grondbeheerder.getId()).orElseThrow();
    }

    protected void assertPersistedGrondbeheerderToMatchAllProperties(Grondbeheerder expectedGrondbeheerder) {
        assertGrondbeheerderAllPropertiesEquals(expectedGrondbeheerder, getPersistedGrondbeheerder(expectedGrondbeheerder));
    }

    protected void assertPersistedGrondbeheerderToMatchUpdatableProperties(Grondbeheerder expectedGrondbeheerder) {
        assertGrondbeheerderAllUpdatablePropertiesEquals(expectedGrondbeheerder, getPersistedGrondbeheerder(expectedGrondbeheerder));
    }
}
