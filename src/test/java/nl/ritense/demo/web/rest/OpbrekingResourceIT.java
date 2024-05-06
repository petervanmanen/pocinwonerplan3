package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OpbrekingAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Opbreking;
import nl.ritense.demo.repository.OpbrekingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OpbrekingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OpbrekingResourceIT {

    private static final String ENTITY_API_URL = "/api/opbrekings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OpbrekingRepository opbrekingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpbrekingMockMvc;

    private Opbreking opbreking;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opbreking createEntity(EntityManager em) {
        Opbreking opbreking = new Opbreking();
        return opbreking;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opbreking createUpdatedEntity(EntityManager em) {
        Opbreking opbreking = new Opbreking();
        return opbreking;
    }

    @BeforeEach
    public void initTest() {
        opbreking = createEntity(em);
    }

    @Test
    @Transactional
    void createOpbreking() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Opbreking
        var returnedOpbreking = om.readValue(
            restOpbrekingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opbreking)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Opbreking.class
        );

        // Validate the Opbreking in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOpbrekingUpdatableFieldsEquals(returnedOpbreking, getPersistedOpbreking(returnedOpbreking));
    }

    @Test
    @Transactional
    void createOpbrekingWithExistingId() throws Exception {
        // Create the Opbreking with an existing ID
        opbreking.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpbrekingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opbreking)))
            .andExpect(status().isBadRequest());

        // Validate the Opbreking in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOpbrekings() throws Exception {
        // Initialize the database
        opbrekingRepository.saveAndFlush(opbreking);

        // Get all the opbrekingList
        restOpbrekingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opbreking.getId().intValue())));
    }

    @Test
    @Transactional
    void getOpbreking() throws Exception {
        // Initialize the database
        opbrekingRepository.saveAndFlush(opbreking);

        // Get the opbreking
        restOpbrekingMockMvc
            .perform(get(ENTITY_API_URL_ID, opbreking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(opbreking.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOpbreking() throws Exception {
        // Get the opbreking
        restOpbrekingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteOpbreking() throws Exception {
        // Initialize the database
        opbrekingRepository.saveAndFlush(opbreking);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the opbreking
        restOpbrekingMockMvc
            .perform(delete(ENTITY_API_URL_ID, opbreking.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return opbrekingRepository.count();
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

    protected Opbreking getPersistedOpbreking(Opbreking opbreking) {
        return opbrekingRepository.findById(opbreking.getId()).orElseThrow();
    }

    protected void assertPersistedOpbrekingToMatchAllProperties(Opbreking expectedOpbreking) {
        assertOpbrekingAllPropertiesEquals(expectedOpbreking, getPersistedOpbreking(expectedOpbreking));
    }

    protected void assertPersistedOpbrekingToMatchUpdatableProperties(Opbreking expectedOpbreking) {
        assertOpbrekingAllUpdatablePropertiesEquals(expectedOpbreking, getPersistedOpbreking(expectedOpbreking));
    }
}
