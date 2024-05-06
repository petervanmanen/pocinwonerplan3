package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ZAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Z;
import nl.ritense.demo.repository.ZRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ZResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ZResourceIT {

    private static final String ENTITY_API_URL = "/api/zs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ZRepository zRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZMockMvc;

    private Z z;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Z createEntity(EntityManager em) {
        Z z = new Z();
        return z;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Z createUpdatedEntity(EntityManager em) {
        Z z = new Z();
        return z;
    }

    @BeforeEach
    public void initTest() {
        z = createEntity(em);
    }

    @Test
    @Transactional
    void createZ() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Z
        var returnedZ = om.readValue(
            restZMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(z)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Z.class
        );

        // Validate the Z in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertZUpdatableFieldsEquals(returnedZ, getPersistedZ(returnedZ));
    }

    @Test
    @Transactional
    void createZWithExistingId() throws Exception {
        // Create the Z with an existing ID
        z.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restZMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(z)))
            .andExpect(status().isBadRequest());

        // Validate the Z in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllZS() throws Exception {
        // Initialize the database
        zRepository.saveAndFlush(z);

        // Get all the zList
        restZMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(z.getId().intValue())));
    }

    @Test
    @Transactional
    void getZ() throws Exception {
        // Initialize the database
        zRepository.saveAndFlush(z);

        // Get the z
        restZMockMvc
            .perform(get(ENTITY_API_URL_ID, z.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(z.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingZ() throws Exception {
        // Get the z
        restZMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteZ() throws Exception {
        // Initialize the database
        zRepository.saveAndFlush(z);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the z
        restZMockMvc.perform(delete(ENTITY_API_URL_ID, z.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return zRepository.count();
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

    protected Z getPersistedZ(Z z) {
        return zRepository.findById(z.getId()).orElseThrow();
    }

    protected void assertPersistedZToMatchAllProperties(Z expectedZ) {
        assertZAllPropertiesEquals(expectedZ, getPersistedZ(expectedZ));
    }

    protected void assertPersistedZToMatchUpdatableProperties(Z expectedZ) {
        assertZAllUpdatablePropertiesEquals(expectedZ, getPersistedZ(expectedZ));
    }
}
