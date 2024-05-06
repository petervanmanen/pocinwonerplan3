package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.IndieningsvereistenAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Indieningsvereisten;
import nl.ritense.demo.repository.IndieningsvereistenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IndieningsvereistenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndieningsvereistenResourceIT {

    private static final String ENTITY_API_URL = "/api/indieningsvereistens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IndieningsvereistenRepository indieningsvereistenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndieningsvereistenMockMvc;

    private Indieningsvereisten indieningsvereisten;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Indieningsvereisten createEntity(EntityManager em) {
        Indieningsvereisten indieningsvereisten = new Indieningsvereisten();
        return indieningsvereisten;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Indieningsvereisten createUpdatedEntity(EntityManager em) {
        Indieningsvereisten indieningsvereisten = new Indieningsvereisten();
        return indieningsvereisten;
    }

    @BeforeEach
    public void initTest() {
        indieningsvereisten = createEntity(em);
    }

    @Test
    @Transactional
    void createIndieningsvereisten() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Indieningsvereisten
        var returnedIndieningsvereisten = om.readValue(
            restIndieningsvereistenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indieningsvereisten)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Indieningsvereisten.class
        );

        // Validate the Indieningsvereisten in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertIndieningsvereistenUpdatableFieldsEquals(
            returnedIndieningsvereisten,
            getPersistedIndieningsvereisten(returnedIndieningsvereisten)
        );
    }

    @Test
    @Transactional
    void createIndieningsvereistenWithExistingId() throws Exception {
        // Create the Indieningsvereisten with an existing ID
        indieningsvereisten.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndieningsvereistenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indieningsvereisten)))
            .andExpect(status().isBadRequest());

        // Validate the Indieningsvereisten in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIndieningsvereistens() throws Exception {
        // Initialize the database
        indieningsvereistenRepository.saveAndFlush(indieningsvereisten);

        // Get all the indieningsvereistenList
        restIndieningsvereistenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indieningsvereisten.getId().intValue())));
    }

    @Test
    @Transactional
    void getIndieningsvereisten() throws Exception {
        // Initialize the database
        indieningsvereistenRepository.saveAndFlush(indieningsvereisten);

        // Get the indieningsvereisten
        restIndieningsvereistenMockMvc
            .perform(get(ENTITY_API_URL_ID, indieningsvereisten.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indieningsvereisten.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingIndieningsvereisten() throws Exception {
        // Get the indieningsvereisten
        restIndieningsvereistenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteIndieningsvereisten() throws Exception {
        // Initialize the database
        indieningsvereistenRepository.saveAndFlush(indieningsvereisten);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the indieningsvereisten
        restIndieningsvereistenMockMvc
            .perform(delete(ENTITY_API_URL_ID, indieningsvereisten.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return indieningsvereistenRepository.count();
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

    protected Indieningsvereisten getPersistedIndieningsvereisten(Indieningsvereisten indieningsvereisten) {
        return indieningsvereistenRepository.findById(indieningsvereisten.getId()).orElseThrow();
    }

    protected void assertPersistedIndieningsvereistenToMatchAllProperties(Indieningsvereisten expectedIndieningsvereisten) {
        assertIndieningsvereistenAllPropertiesEquals(
            expectedIndieningsvereisten,
            getPersistedIndieningsvereisten(expectedIndieningsvereisten)
        );
    }

    protected void assertPersistedIndieningsvereistenToMatchUpdatableProperties(Indieningsvereisten expectedIndieningsvereisten) {
        assertIndieningsvereistenAllUpdatablePropertiesEquals(
            expectedIndieningsvereisten,
            getPersistedIndieningsvereisten(expectedIndieningsvereisten)
        );
    }
}
