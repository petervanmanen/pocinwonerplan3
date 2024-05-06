package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DebiteurAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Debiteur;
import nl.ritense.demo.repository.DebiteurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DebiteurResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class DebiteurResourceIT {

    private static final String ENTITY_API_URL = "/api/debiteurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DebiteurRepository debiteurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDebiteurMockMvc;

    private Debiteur debiteur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Debiteur createEntity(EntityManager em) {
        Debiteur debiteur = new Debiteur();
        return debiteur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Debiteur createUpdatedEntity(EntityManager em) {
        Debiteur debiteur = new Debiteur();
        return debiteur;
    }

    @BeforeEach
    public void initTest() {
        debiteur = createEntity(em);
    }

    @Test
    @Transactional
    void createDebiteur() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Debiteur
        var returnedDebiteur = om.readValue(
            restDebiteurMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(debiteur)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Debiteur.class
        );

        // Validate the Debiteur in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDebiteurUpdatableFieldsEquals(returnedDebiteur, getPersistedDebiteur(returnedDebiteur));
    }

    @Test
    @Transactional
    void createDebiteurWithExistingId() throws Exception {
        // Create the Debiteur with an existing ID
        debiteur.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDebiteurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(debiteur)))
            .andExpect(status().isBadRequest());

        // Validate the Debiteur in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDebiteurs() throws Exception {
        // Initialize the database
        debiteurRepository.saveAndFlush(debiteur);

        // Get all the debiteurList
        restDebiteurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(debiteur.getId().intValue())));
    }

    @Test
    @Transactional
    void getDebiteur() throws Exception {
        // Initialize the database
        debiteurRepository.saveAndFlush(debiteur);

        // Get the debiteur
        restDebiteurMockMvc
            .perform(get(ENTITY_API_URL_ID, debiteur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(debiteur.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingDebiteur() throws Exception {
        // Get the debiteur
        restDebiteurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteDebiteur() throws Exception {
        // Initialize the database
        debiteurRepository.saveAndFlush(debiteur);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the debiteur
        restDebiteurMockMvc
            .perform(delete(ENTITY_API_URL_ID, debiteur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return debiteurRepository.count();
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

    protected Debiteur getPersistedDebiteur(Debiteur debiteur) {
        return debiteurRepository.findById(debiteur.getId()).orElseThrow();
    }

    protected void assertPersistedDebiteurToMatchAllProperties(Debiteur expectedDebiteur) {
        assertDebiteurAllPropertiesEquals(expectedDebiteur, getPersistedDebiteur(expectedDebiteur));
    }

    protected void assertPersistedDebiteurToMatchUpdatableProperties(Debiteur expectedDebiteur) {
        assertDebiteurAllUpdatablePropertiesEquals(expectedDebiteur, getPersistedDebiteur(expectedDebiteur));
    }
}
