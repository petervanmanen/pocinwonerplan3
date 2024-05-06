package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ActieAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Actie;
import nl.ritense.demo.repository.ActieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ActieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActieResourceIT {

    private static final String ENTITY_API_URL = "/api/acties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ActieRepository actieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActieMockMvc;

    private Actie actie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Actie createEntity(EntityManager em) {
        Actie actie = new Actie();
        return actie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Actie createUpdatedEntity(EntityManager em) {
        Actie actie = new Actie();
        return actie;
    }

    @BeforeEach
    public void initTest() {
        actie = createEntity(em);
    }

    @Test
    @Transactional
    void createActie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Actie
        var returnedActie = om.readValue(
            restActieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(actie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Actie.class
        );

        // Validate the Actie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertActieUpdatableFieldsEquals(returnedActie, getPersistedActie(returnedActie));
    }

    @Test
    @Transactional
    void createActieWithExistingId() throws Exception {
        // Create the Actie with an existing ID
        actie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(actie)))
            .andExpect(status().isBadRequest());

        // Validate the Actie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActies() throws Exception {
        // Initialize the database
        actieRepository.saveAndFlush(actie);

        // Get all the actieList
        restActieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actie.getId().intValue())));
    }

    @Test
    @Transactional
    void getActie() throws Exception {
        // Initialize the database
        actieRepository.saveAndFlush(actie);

        // Get the actie
        restActieMockMvc
            .perform(get(ENTITY_API_URL_ID, actie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(actie.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingActie() throws Exception {
        // Get the actie
        restActieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteActie() throws Exception {
        // Initialize the database
        actieRepository.saveAndFlush(actie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the actie
        restActieMockMvc
            .perform(delete(ENTITY_API_URL_ID, actie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return actieRepository.count();
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

    protected Actie getPersistedActie(Actie actie) {
        return actieRepository.findById(actie.getId()).orElseThrow();
    }

    protected void assertPersistedActieToMatchAllProperties(Actie expectedActie) {
        assertActieAllPropertiesEquals(expectedActie, getPersistedActie(expectedActie));
    }

    protected void assertPersistedActieToMatchUpdatableProperties(Actie expectedActie) {
        assertActieAllUpdatablePropertiesEquals(expectedActie, getPersistedActie(expectedActie));
    }
}
