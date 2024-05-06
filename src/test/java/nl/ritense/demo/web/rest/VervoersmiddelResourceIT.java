package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VervoersmiddelAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Vervoersmiddel;
import nl.ritense.demo.repository.VervoersmiddelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VervoersmiddelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VervoersmiddelResourceIT {

    private static final String ENTITY_API_URL = "/api/vervoersmiddels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VervoersmiddelRepository vervoersmiddelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVervoersmiddelMockMvc;

    private Vervoersmiddel vervoersmiddel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vervoersmiddel createEntity(EntityManager em) {
        Vervoersmiddel vervoersmiddel = new Vervoersmiddel();
        return vervoersmiddel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vervoersmiddel createUpdatedEntity(EntityManager em) {
        Vervoersmiddel vervoersmiddel = new Vervoersmiddel();
        return vervoersmiddel;
    }

    @BeforeEach
    public void initTest() {
        vervoersmiddel = createEntity(em);
    }

    @Test
    @Transactional
    void createVervoersmiddel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vervoersmiddel
        var returnedVervoersmiddel = om.readValue(
            restVervoersmiddelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vervoersmiddel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vervoersmiddel.class
        );

        // Validate the Vervoersmiddel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVervoersmiddelUpdatableFieldsEquals(returnedVervoersmiddel, getPersistedVervoersmiddel(returnedVervoersmiddel));
    }

    @Test
    @Transactional
    void createVervoersmiddelWithExistingId() throws Exception {
        // Create the Vervoersmiddel with an existing ID
        vervoersmiddel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVervoersmiddelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vervoersmiddel)))
            .andExpect(status().isBadRequest());

        // Validate the Vervoersmiddel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVervoersmiddels() throws Exception {
        // Initialize the database
        vervoersmiddelRepository.saveAndFlush(vervoersmiddel);

        // Get all the vervoersmiddelList
        restVervoersmiddelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vervoersmiddel.getId().intValue())));
    }

    @Test
    @Transactional
    void getVervoersmiddel() throws Exception {
        // Initialize the database
        vervoersmiddelRepository.saveAndFlush(vervoersmiddel);

        // Get the vervoersmiddel
        restVervoersmiddelMockMvc
            .perform(get(ENTITY_API_URL_ID, vervoersmiddel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vervoersmiddel.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingVervoersmiddel() throws Exception {
        // Get the vervoersmiddel
        restVervoersmiddelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteVervoersmiddel() throws Exception {
        // Initialize the database
        vervoersmiddelRepository.saveAndFlush(vervoersmiddel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vervoersmiddel
        restVervoersmiddelMockMvc
            .perform(delete(ENTITY_API_URL_ID, vervoersmiddel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vervoersmiddelRepository.count();
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

    protected Vervoersmiddel getPersistedVervoersmiddel(Vervoersmiddel vervoersmiddel) {
        return vervoersmiddelRepository.findById(vervoersmiddel.getId()).orElseThrow();
    }

    protected void assertPersistedVervoersmiddelToMatchAllProperties(Vervoersmiddel expectedVervoersmiddel) {
        assertVervoersmiddelAllPropertiesEquals(expectedVervoersmiddel, getPersistedVervoersmiddel(expectedVervoersmiddel));
    }

    protected void assertPersistedVervoersmiddelToMatchUpdatableProperties(Vervoersmiddel expectedVervoersmiddel) {
        assertVervoersmiddelAllUpdatablePropertiesEquals(expectedVervoersmiddel, getPersistedVervoersmiddel(expectedVervoersmiddel));
    }
}
