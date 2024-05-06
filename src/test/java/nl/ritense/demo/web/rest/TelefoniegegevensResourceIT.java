package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TelefoniegegevensAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Telefoniegegevens;
import nl.ritense.demo.repository.TelefoniegegevensRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TelefoniegegevensResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TelefoniegegevensResourceIT {

    private static final String ENTITY_API_URL = "/api/telefoniegegevens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TelefoniegegevensRepository telefoniegegevensRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTelefoniegegevensMockMvc;

    private Telefoniegegevens telefoniegegevens;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefoniegegevens createEntity(EntityManager em) {
        Telefoniegegevens telefoniegegevens = new Telefoniegegevens();
        return telefoniegegevens;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefoniegegevens createUpdatedEntity(EntityManager em) {
        Telefoniegegevens telefoniegegevens = new Telefoniegegevens();
        return telefoniegegevens;
    }

    @BeforeEach
    public void initTest() {
        telefoniegegevens = createEntity(em);
    }

    @Test
    @Transactional
    void createTelefoniegegevens() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Telefoniegegevens
        var returnedTelefoniegegevens = om.readValue(
            restTelefoniegegevensMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telefoniegegevens)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Telefoniegegevens.class
        );

        // Validate the Telefoniegegevens in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTelefoniegegevensUpdatableFieldsEquals(returnedTelefoniegegevens, getPersistedTelefoniegegevens(returnedTelefoniegegevens));
    }

    @Test
    @Transactional
    void createTelefoniegegevensWithExistingId() throws Exception {
        // Create the Telefoniegegevens with an existing ID
        telefoniegegevens.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelefoniegegevensMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telefoniegegevens)))
            .andExpect(status().isBadRequest());

        // Validate the Telefoniegegevens in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTelefoniegegevens() throws Exception {
        // Initialize the database
        telefoniegegevensRepository.saveAndFlush(telefoniegegevens);

        // Get all the telefoniegegevensList
        restTelefoniegegevensMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telefoniegegevens.getId().intValue())));
    }

    @Test
    @Transactional
    void getTelefoniegegevens() throws Exception {
        // Initialize the database
        telefoniegegevensRepository.saveAndFlush(telefoniegegevens);

        // Get the telefoniegegevens
        restTelefoniegegevensMockMvc
            .perform(get(ENTITY_API_URL_ID, telefoniegegevens.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(telefoniegegevens.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTelefoniegegevens() throws Exception {
        // Get the telefoniegegevens
        restTelefoniegegevensMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteTelefoniegegevens() throws Exception {
        // Initialize the database
        telefoniegegevensRepository.saveAndFlush(telefoniegegevens);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the telefoniegegevens
        restTelefoniegegevensMockMvc
            .perform(delete(ENTITY_API_URL_ID, telefoniegegevens.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return telefoniegegevensRepository.count();
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

    protected Telefoniegegevens getPersistedTelefoniegegevens(Telefoniegegevens telefoniegegevens) {
        return telefoniegegevensRepository.findById(telefoniegegevens.getId()).orElseThrow();
    }

    protected void assertPersistedTelefoniegegevensToMatchAllProperties(Telefoniegegevens expectedTelefoniegegevens) {
        assertTelefoniegegevensAllPropertiesEquals(expectedTelefoniegegevens, getPersistedTelefoniegegevens(expectedTelefoniegegevens));
    }

    protected void assertPersistedTelefoniegegevensToMatchUpdatableProperties(Telefoniegegevens expectedTelefoniegegevens) {
        assertTelefoniegegevensAllUpdatablePropertiesEquals(
            expectedTelefoniegegevens,
            getPersistedTelefoniegegevens(expectedTelefoniegegevens)
        );
    }
}
