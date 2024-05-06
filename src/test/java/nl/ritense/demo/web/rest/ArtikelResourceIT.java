package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ArtikelAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Artikel;
import nl.ritense.demo.repository.ArtikelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ArtikelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArtikelResourceIT {

    private static final String ENTITY_API_URL = "/api/artikels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ArtikelRepository artikelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtikelMockMvc;

    private Artikel artikel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artikel createEntity(EntityManager em) {
        Artikel artikel = new Artikel();
        return artikel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artikel createUpdatedEntity(EntityManager em) {
        Artikel artikel = new Artikel();
        return artikel;
    }

    @BeforeEach
    public void initTest() {
        artikel = createEntity(em);
    }

    @Test
    @Transactional
    void createArtikel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Artikel
        var returnedArtikel = om.readValue(
            restArtikelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artikel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Artikel.class
        );

        // Validate the Artikel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertArtikelUpdatableFieldsEquals(returnedArtikel, getPersistedArtikel(returnedArtikel));
    }

    @Test
    @Transactional
    void createArtikelWithExistingId() throws Exception {
        // Create the Artikel with an existing ID
        artikel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtikelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artikel)))
            .andExpect(status().isBadRequest());

        // Validate the Artikel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArtikels() throws Exception {
        // Initialize the database
        artikelRepository.saveAndFlush(artikel);

        // Get all the artikelList
        restArtikelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artikel.getId().intValue())));
    }

    @Test
    @Transactional
    void getArtikel() throws Exception {
        // Initialize the database
        artikelRepository.saveAndFlush(artikel);

        // Get the artikel
        restArtikelMockMvc
            .perform(get(ENTITY_API_URL_ID, artikel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artikel.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingArtikel() throws Exception {
        // Get the artikel
        restArtikelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteArtikel() throws Exception {
        // Initialize the database
        artikelRepository.saveAndFlush(artikel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the artikel
        restArtikelMockMvc
            .perform(delete(ENTITY_API_URL_ID, artikel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return artikelRepository.count();
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

    protected Artikel getPersistedArtikel(Artikel artikel) {
        return artikelRepository.findById(artikel.getId()).orElseThrow();
    }

    protected void assertPersistedArtikelToMatchAllProperties(Artikel expectedArtikel) {
        assertArtikelAllPropertiesEquals(expectedArtikel, getPersistedArtikel(expectedArtikel));
    }

    protected void assertPersistedArtikelToMatchUpdatableProperties(Artikel expectedArtikel) {
        assertArtikelAllUpdatablePropertiesEquals(expectedArtikel, getPersistedArtikel(expectedArtikel));
    }
}
