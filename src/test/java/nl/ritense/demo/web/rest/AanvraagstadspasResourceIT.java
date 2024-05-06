package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AanvraagstadspasAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Aanvraagstadspas;
import nl.ritense.demo.repository.AanvraagstadspasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AanvraagstadspasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AanvraagstadspasResourceIT {

    private static final String ENTITY_API_URL = "/api/aanvraagstadspas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AanvraagstadspasRepository aanvraagstadspasRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAanvraagstadspasMockMvc;

    private Aanvraagstadspas aanvraagstadspas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanvraagstadspas createEntity(EntityManager em) {
        Aanvraagstadspas aanvraagstadspas = new Aanvraagstadspas();
        return aanvraagstadspas;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanvraagstadspas createUpdatedEntity(EntityManager em) {
        Aanvraagstadspas aanvraagstadspas = new Aanvraagstadspas();
        return aanvraagstadspas;
    }

    @BeforeEach
    public void initTest() {
        aanvraagstadspas = createEntity(em);
    }

    @Test
    @Transactional
    void createAanvraagstadspas() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aanvraagstadspas
        var returnedAanvraagstadspas = om.readValue(
            restAanvraagstadspasMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraagstadspas)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aanvraagstadspas.class
        );

        // Validate the Aanvraagstadspas in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAanvraagstadspasUpdatableFieldsEquals(returnedAanvraagstadspas, getPersistedAanvraagstadspas(returnedAanvraagstadspas));
    }

    @Test
    @Transactional
    void createAanvraagstadspasWithExistingId() throws Exception {
        // Create the Aanvraagstadspas with an existing ID
        aanvraagstadspas.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAanvraagstadspasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraagstadspas)))
            .andExpect(status().isBadRequest());

        // Validate the Aanvraagstadspas in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAanvraagstadspas() throws Exception {
        // Initialize the database
        aanvraagstadspasRepository.saveAndFlush(aanvraagstadspas);

        // Get all the aanvraagstadspasList
        restAanvraagstadspasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aanvraagstadspas.getId().intValue())));
    }

    @Test
    @Transactional
    void getAanvraagstadspas() throws Exception {
        // Initialize the database
        aanvraagstadspasRepository.saveAndFlush(aanvraagstadspas);

        // Get the aanvraagstadspas
        restAanvraagstadspasMockMvc
            .perform(get(ENTITY_API_URL_ID, aanvraagstadspas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aanvraagstadspas.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAanvraagstadspas() throws Exception {
        // Get the aanvraagstadspas
        restAanvraagstadspasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteAanvraagstadspas() throws Exception {
        // Initialize the database
        aanvraagstadspasRepository.saveAndFlush(aanvraagstadspas);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aanvraagstadspas
        restAanvraagstadspasMockMvc
            .perform(delete(ENTITY_API_URL_ID, aanvraagstadspas.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aanvraagstadspasRepository.count();
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

    protected Aanvraagstadspas getPersistedAanvraagstadspas(Aanvraagstadspas aanvraagstadspas) {
        return aanvraagstadspasRepository.findById(aanvraagstadspas.getId()).orElseThrow();
    }

    protected void assertPersistedAanvraagstadspasToMatchAllProperties(Aanvraagstadspas expectedAanvraagstadspas) {
        assertAanvraagstadspasAllPropertiesEquals(expectedAanvraagstadspas, getPersistedAanvraagstadspas(expectedAanvraagstadspas));
    }

    protected void assertPersistedAanvraagstadspasToMatchUpdatableProperties(Aanvraagstadspas expectedAanvraagstadspas) {
        assertAanvraagstadspasAllUpdatablePropertiesEquals(
            expectedAanvraagstadspas,
            getPersistedAanvraagstadspas(expectedAanvraagstadspas)
        );
    }
}
