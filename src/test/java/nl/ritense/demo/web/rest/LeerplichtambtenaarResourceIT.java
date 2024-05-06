package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LeerplichtambtenaarAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Leerplichtambtenaar;
import nl.ritense.demo.repository.LeerplichtambtenaarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LeerplichtambtenaarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeerplichtambtenaarResourceIT {

    private static final String ENTITY_API_URL = "/api/leerplichtambtenaars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LeerplichtambtenaarRepository leerplichtambtenaarRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeerplichtambtenaarMockMvc;

    private Leerplichtambtenaar leerplichtambtenaar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leerplichtambtenaar createEntity(EntityManager em) {
        Leerplichtambtenaar leerplichtambtenaar = new Leerplichtambtenaar();
        return leerplichtambtenaar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leerplichtambtenaar createUpdatedEntity(EntityManager em) {
        Leerplichtambtenaar leerplichtambtenaar = new Leerplichtambtenaar();
        return leerplichtambtenaar;
    }

    @BeforeEach
    public void initTest() {
        leerplichtambtenaar = createEntity(em);
    }

    @Test
    @Transactional
    void createLeerplichtambtenaar() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Leerplichtambtenaar
        var returnedLeerplichtambtenaar = om.readValue(
            restLeerplichtambtenaarMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leerplichtambtenaar)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Leerplichtambtenaar.class
        );

        // Validate the Leerplichtambtenaar in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLeerplichtambtenaarUpdatableFieldsEquals(
            returnedLeerplichtambtenaar,
            getPersistedLeerplichtambtenaar(returnedLeerplichtambtenaar)
        );
    }

    @Test
    @Transactional
    void createLeerplichtambtenaarWithExistingId() throws Exception {
        // Create the Leerplichtambtenaar with an existing ID
        leerplichtambtenaar.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeerplichtambtenaarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leerplichtambtenaar)))
            .andExpect(status().isBadRequest());

        // Validate the Leerplichtambtenaar in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeerplichtambtenaars() throws Exception {
        // Initialize the database
        leerplichtambtenaarRepository.saveAndFlush(leerplichtambtenaar);

        // Get all the leerplichtambtenaarList
        restLeerplichtambtenaarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leerplichtambtenaar.getId().intValue())));
    }

    @Test
    @Transactional
    void getLeerplichtambtenaar() throws Exception {
        // Initialize the database
        leerplichtambtenaarRepository.saveAndFlush(leerplichtambtenaar);

        // Get the leerplichtambtenaar
        restLeerplichtambtenaarMockMvc
            .perform(get(ENTITY_API_URL_ID, leerplichtambtenaar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leerplichtambtenaar.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingLeerplichtambtenaar() throws Exception {
        // Get the leerplichtambtenaar
        restLeerplichtambtenaarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteLeerplichtambtenaar() throws Exception {
        // Initialize the database
        leerplichtambtenaarRepository.saveAndFlush(leerplichtambtenaar);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the leerplichtambtenaar
        restLeerplichtambtenaarMockMvc
            .perform(delete(ENTITY_API_URL_ID, leerplichtambtenaar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return leerplichtambtenaarRepository.count();
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

    protected Leerplichtambtenaar getPersistedLeerplichtambtenaar(Leerplichtambtenaar leerplichtambtenaar) {
        return leerplichtambtenaarRepository.findById(leerplichtambtenaar.getId()).orElseThrow();
    }

    protected void assertPersistedLeerplichtambtenaarToMatchAllProperties(Leerplichtambtenaar expectedLeerplichtambtenaar) {
        assertLeerplichtambtenaarAllPropertiesEquals(
            expectedLeerplichtambtenaar,
            getPersistedLeerplichtambtenaar(expectedLeerplichtambtenaar)
        );
    }

    protected void assertPersistedLeerplichtambtenaarToMatchUpdatableProperties(Leerplichtambtenaar expectedLeerplichtambtenaar) {
        assertLeerplichtambtenaarAllUpdatablePropertiesEquals(
            expectedLeerplichtambtenaar,
            getPersistedLeerplichtambtenaar(expectedLeerplichtambtenaar)
        );
    }
}
