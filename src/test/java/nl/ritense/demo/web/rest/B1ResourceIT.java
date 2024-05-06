package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.B1Asserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.B1;
import nl.ritense.demo.repository.B1Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link B1Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class B1ResourceIT {

    private static final String ENTITY_API_URL = "/api/b-1-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private B1Repository b1Repository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restB1MockMvc;

    private B1 b1;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static B1 createEntity(EntityManager em) {
        B1 b1 = new B1();
        return b1;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static B1 createUpdatedEntity(EntityManager em) {
        B1 b1 = new B1();
        return b1;
    }

    @BeforeEach
    public void initTest() {
        b1 = createEntity(em);
    }

    @Test
    @Transactional
    void createB1() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the B1
        var returnedB1 = om.readValue(
            restB1MockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(b1)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            B1.class
        );

        // Validate the B1 in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertB1UpdatableFieldsEquals(returnedB1, getPersistedB1(returnedB1));
    }

    @Test
    @Transactional
    void createB1WithExistingId() throws Exception {
        // Create the B1 with an existing ID
        b1.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restB1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(b1)))
            .andExpect(status().isBadRequest());

        // Validate the B1 in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllB1s() throws Exception {
        // Initialize the database
        b1Repository.saveAndFlush(b1);

        // Get all the b1List
        restB1MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(b1.getId().intValue())));
    }

    @Test
    @Transactional
    void getB1() throws Exception {
        // Initialize the database
        b1Repository.saveAndFlush(b1);

        // Get the b1
        restB1MockMvc
            .perform(get(ENTITY_API_URL_ID, b1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(b1.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingB1() throws Exception {
        // Get the b1
        restB1MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteB1() throws Exception {
        // Initialize the database
        b1Repository.saveAndFlush(b1);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the b1
        restB1MockMvc.perform(delete(ENTITY_API_URL_ID, b1.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return b1Repository.count();
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

    protected B1 getPersistedB1(B1 b1) {
        return b1Repository.findById(b1.getId()).orElseThrow();
    }

    protected void assertPersistedB1ToMatchAllProperties(B1 expectedB1) {
        assertB1AllPropertiesEquals(expectedB1, getPersistedB1(expectedB1));
    }

    protected void assertPersistedB1ToMatchUpdatableProperties(B1 expectedB1) {
        assertB1AllUpdatablePropertiesEquals(expectedB1, getPersistedB1(expectedB1));
    }
}
