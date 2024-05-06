package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GezinsmigrantenoverigemigrantAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Gezinsmigrantenoverigemigrant;
import nl.ritense.demo.repository.GezinsmigrantenoverigemigrantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GezinsmigrantenoverigemigrantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GezinsmigrantenoverigemigrantResourceIT {

    private static final String ENTITY_API_URL = "/api/gezinsmigrantenoverigemigrants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GezinsmigrantenoverigemigrantRepository gezinsmigrantenoverigemigrantRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGezinsmigrantenoverigemigrantMockMvc;

    private Gezinsmigrantenoverigemigrant gezinsmigrantenoverigemigrant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gezinsmigrantenoverigemigrant createEntity(EntityManager em) {
        Gezinsmigrantenoverigemigrant gezinsmigrantenoverigemigrant = new Gezinsmigrantenoverigemigrant();
        return gezinsmigrantenoverigemigrant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gezinsmigrantenoverigemigrant createUpdatedEntity(EntityManager em) {
        Gezinsmigrantenoverigemigrant gezinsmigrantenoverigemigrant = new Gezinsmigrantenoverigemigrant();
        return gezinsmigrantenoverigemigrant;
    }

    @BeforeEach
    public void initTest() {
        gezinsmigrantenoverigemigrant = createEntity(em);
    }

    @Test
    @Transactional
    void createGezinsmigrantenoverigemigrant() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Gezinsmigrantenoverigemigrant
        var returnedGezinsmigrantenoverigemigrant = om.readValue(
            restGezinsmigrantenoverigemigrantMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(gezinsmigrantenoverigemigrant))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Gezinsmigrantenoverigemigrant.class
        );

        // Validate the Gezinsmigrantenoverigemigrant in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGezinsmigrantenoverigemigrantUpdatableFieldsEquals(
            returnedGezinsmigrantenoverigemigrant,
            getPersistedGezinsmigrantenoverigemigrant(returnedGezinsmigrantenoverigemigrant)
        );
    }

    @Test
    @Transactional
    void createGezinsmigrantenoverigemigrantWithExistingId() throws Exception {
        // Create the Gezinsmigrantenoverigemigrant with an existing ID
        gezinsmigrantenoverigemigrant.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGezinsmigrantenoverigemigrantMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gezinsmigrantenoverigemigrant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gezinsmigrantenoverigemigrant in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGezinsmigrantenoverigemigrants() throws Exception {
        // Initialize the database
        gezinsmigrantenoverigemigrantRepository.saveAndFlush(gezinsmigrantenoverigemigrant);

        // Get all the gezinsmigrantenoverigemigrantList
        restGezinsmigrantenoverigemigrantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gezinsmigrantenoverigemigrant.getId().intValue())));
    }

    @Test
    @Transactional
    void getGezinsmigrantenoverigemigrant() throws Exception {
        // Initialize the database
        gezinsmigrantenoverigemigrantRepository.saveAndFlush(gezinsmigrantenoverigemigrant);

        // Get the gezinsmigrantenoverigemigrant
        restGezinsmigrantenoverigemigrantMockMvc
            .perform(get(ENTITY_API_URL_ID, gezinsmigrantenoverigemigrant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gezinsmigrantenoverigemigrant.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingGezinsmigrantenoverigemigrant() throws Exception {
        // Get the gezinsmigrantenoverigemigrant
        restGezinsmigrantenoverigemigrantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteGezinsmigrantenoverigemigrant() throws Exception {
        // Initialize the database
        gezinsmigrantenoverigemigrantRepository.saveAndFlush(gezinsmigrantenoverigemigrant);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gezinsmigrantenoverigemigrant
        restGezinsmigrantenoverigemigrantMockMvc
            .perform(delete(ENTITY_API_URL_ID, gezinsmigrantenoverigemigrant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gezinsmigrantenoverigemigrantRepository.count();
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

    protected Gezinsmigrantenoverigemigrant getPersistedGezinsmigrantenoverigemigrant(
        Gezinsmigrantenoverigemigrant gezinsmigrantenoverigemigrant
    ) {
        return gezinsmigrantenoverigemigrantRepository.findById(gezinsmigrantenoverigemigrant.getId()).orElseThrow();
    }

    protected void assertPersistedGezinsmigrantenoverigemigrantToMatchAllProperties(
        Gezinsmigrantenoverigemigrant expectedGezinsmigrantenoverigemigrant
    ) {
        assertGezinsmigrantenoverigemigrantAllPropertiesEquals(
            expectedGezinsmigrantenoverigemigrant,
            getPersistedGezinsmigrantenoverigemigrant(expectedGezinsmigrantenoverigemigrant)
        );
    }

    protected void assertPersistedGezinsmigrantenoverigemigrantToMatchUpdatableProperties(
        Gezinsmigrantenoverigemigrant expectedGezinsmigrantenoverigemigrant
    ) {
        assertGezinsmigrantenoverigemigrantAllUpdatablePropertiesEquals(
            expectedGezinsmigrantenoverigemigrant,
            getPersistedGezinsmigrantenoverigemigrant(expectedGezinsmigrantenoverigemigrant)
        );
    }
}
