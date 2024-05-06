package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VervoerderAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Vervoerder;
import nl.ritense.demo.repository.VervoerderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VervoerderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VervoerderResourceIT {

    private static final String ENTITY_API_URL = "/api/vervoerders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VervoerderRepository vervoerderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVervoerderMockMvc;

    private Vervoerder vervoerder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vervoerder createEntity(EntityManager em) {
        Vervoerder vervoerder = new Vervoerder();
        return vervoerder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vervoerder createUpdatedEntity(EntityManager em) {
        Vervoerder vervoerder = new Vervoerder();
        return vervoerder;
    }

    @BeforeEach
    public void initTest() {
        vervoerder = createEntity(em);
    }

    @Test
    @Transactional
    void createVervoerder() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vervoerder
        var returnedVervoerder = om.readValue(
            restVervoerderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vervoerder)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vervoerder.class
        );

        // Validate the Vervoerder in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVervoerderUpdatableFieldsEquals(returnedVervoerder, getPersistedVervoerder(returnedVervoerder));
    }

    @Test
    @Transactional
    void createVervoerderWithExistingId() throws Exception {
        // Create the Vervoerder with an existing ID
        vervoerder.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVervoerderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vervoerder)))
            .andExpect(status().isBadRequest());

        // Validate the Vervoerder in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVervoerders() throws Exception {
        // Initialize the database
        vervoerderRepository.saveAndFlush(vervoerder);

        // Get all the vervoerderList
        restVervoerderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vervoerder.getId().intValue())));
    }

    @Test
    @Transactional
    void getVervoerder() throws Exception {
        // Initialize the database
        vervoerderRepository.saveAndFlush(vervoerder);

        // Get the vervoerder
        restVervoerderMockMvc
            .perform(get(ENTITY_API_URL_ID, vervoerder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vervoerder.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingVervoerder() throws Exception {
        // Get the vervoerder
        restVervoerderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteVervoerder() throws Exception {
        // Initialize the database
        vervoerderRepository.saveAndFlush(vervoerder);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vervoerder
        restVervoerderMockMvc
            .perform(delete(ENTITY_API_URL_ID, vervoerder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vervoerderRepository.count();
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

    protected Vervoerder getPersistedVervoerder(Vervoerder vervoerder) {
        return vervoerderRepository.findById(vervoerder.getId()).orElseThrow();
    }

    protected void assertPersistedVervoerderToMatchAllProperties(Vervoerder expectedVervoerder) {
        assertVervoerderAllPropertiesEquals(expectedVervoerder, getPersistedVervoerder(expectedVervoerder));
    }

    protected void assertPersistedVervoerderToMatchUpdatableProperties(Vervoerder expectedVervoerder) {
        assertVervoerderAllUpdatablePropertiesEquals(expectedVervoerder, getPersistedVervoerder(expectedVervoerder));
    }
}
