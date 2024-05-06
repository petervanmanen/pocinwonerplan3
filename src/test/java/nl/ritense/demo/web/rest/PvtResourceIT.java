package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PvtAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Pvt;
import nl.ritense.demo.repository.PvtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PvtResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PvtResourceIT {

    private static final String ENTITY_API_URL = "/api/pvts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PvtRepository pvtRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPvtMockMvc;

    private Pvt pvt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pvt createEntity(EntityManager em) {
        Pvt pvt = new Pvt();
        return pvt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pvt createUpdatedEntity(EntityManager em) {
        Pvt pvt = new Pvt();
        return pvt;
    }

    @BeforeEach
    public void initTest() {
        pvt = createEntity(em);
    }

    @Test
    @Transactional
    void createPvt() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Pvt
        var returnedPvt = om.readValue(
            restPvtMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pvt)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Pvt.class
        );

        // Validate the Pvt in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPvtUpdatableFieldsEquals(returnedPvt, getPersistedPvt(returnedPvt));
    }

    @Test
    @Transactional
    void createPvtWithExistingId() throws Exception {
        // Create the Pvt with an existing ID
        pvt.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPvtMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pvt)))
            .andExpect(status().isBadRequest());

        // Validate the Pvt in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPvts() throws Exception {
        // Initialize the database
        pvtRepository.saveAndFlush(pvt);

        // Get all the pvtList
        restPvtMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pvt.getId().intValue())));
    }

    @Test
    @Transactional
    void getPvt() throws Exception {
        // Initialize the database
        pvtRepository.saveAndFlush(pvt);

        // Get the pvt
        restPvtMockMvc
            .perform(get(ENTITY_API_URL_ID, pvt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pvt.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPvt() throws Exception {
        // Get the pvt
        restPvtMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deletePvt() throws Exception {
        // Initialize the database
        pvtRepository.saveAndFlush(pvt);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pvt
        restPvtMockMvc.perform(delete(ENTITY_API_URL_ID, pvt.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pvtRepository.count();
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

    protected Pvt getPersistedPvt(Pvt pvt) {
        return pvtRepository.findById(pvt.getId()).orElseThrow();
    }

    protected void assertPersistedPvtToMatchAllProperties(Pvt expectedPvt) {
        assertPvtAllPropertiesEquals(expectedPvt, getPersistedPvt(expectedPvt));
    }

    protected void assertPersistedPvtToMatchUpdatableProperties(Pvt expectedPvt) {
        assertPvtAllUpdatablePropertiesEquals(expectedPvt, getPersistedPvt(expectedPvt));
    }
}
