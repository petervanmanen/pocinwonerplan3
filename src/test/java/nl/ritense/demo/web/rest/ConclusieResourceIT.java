package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ConclusieAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Conclusie;
import nl.ritense.demo.repository.ConclusieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ConclusieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConclusieResourceIT {

    private static final String ENTITY_API_URL = "/api/conclusies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ConclusieRepository conclusieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConclusieMockMvc;

    private Conclusie conclusie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conclusie createEntity(EntityManager em) {
        Conclusie conclusie = new Conclusie();
        return conclusie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conclusie createUpdatedEntity(EntityManager em) {
        Conclusie conclusie = new Conclusie();
        return conclusie;
    }

    @BeforeEach
    public void initTest() {
        conclusie = createEntity(em);
    }

    @Test
    @Transactional
    void createConclusie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Conclusie
        var returnedConclusie = om.readValue(
            restConclusieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(conclusie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Conclusie.class
        );

        // Validate the Conclusie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertConclusieUpdatableFieldsEquals(returnedConclusie, getPersistedConclusie(returnedConclusie));
    }

    @Test
    @Transactional
    void createConclusieWithExistingId() throws Exception {
        // Create the Conclusie with an existing ID
        conclusie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConclusieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(conclusie)))
            .andExpect(status().isBadRequest());

        // Validate the Conclusie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConclusies() throws Exception {
        // Initialize the database
        conclusieRepository.saveAndFlush(conclusie);

        // Get all the conclusieList
        restConclusieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conclusie.getId().intValue())));
    }

    @Test
    @Transactional
    void getConclusie() throws Exception {
        // Initialize the database
        conclusieRepository.saveAndFlush(conclusie);

        // Get the conclusie
        restConclusieMockMvc
            .perform(get(ENTITY_API_URL_ID, conclusie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conclusie.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingConclusie() throws Exception {
        // Get the conclusie
        restConclusieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteConclusie() throws Exception {
        // Initialize the database
        conclusieRepository.saveAndFlush(conclusie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the conclusie
        restConclusieMockMvc
            .perform(delete(ENTITY_API_URL_ID, conclusie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return conclusieRepository.count();
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

    protected Conclusie getPersistedConclusie(Conclusie conclusie) {
        return conclusieRepository.findById(conclusie.getId()).orElseThrow();
    }

    protected void assertPersistedConclusieToMatchAllProperties(Conclusie expectedConclusie) {
        assertConclusieAllPropertiesEquals(expectedConclusie, getPersistedConclusie(expectedConclusie));
    }

    protected void assertPersistedConclusieToMatchUpdatableProperties(Conclusie expectedConclusie) {
        assertConclusieAllUpdatablePropertiesEquals(expectedConclusie, getPersistedConclusie(expectedConclusie));
    }
}
