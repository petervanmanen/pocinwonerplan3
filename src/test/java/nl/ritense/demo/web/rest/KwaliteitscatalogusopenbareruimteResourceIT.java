package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KwaliteitscatalogusopenbareruimteAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Kwaliteitscatalogusopenbareruimte;
import nl.ritense.demo.repository.KwaliteitscatalogusopenbareruimteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KwaliteitscatalogusopenbareruimteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KwaliteitscatalogusopenbareruimteResourceIT {

    private static final String ENTITY_API_URL = "/api/kwaliteitscatalogusopenbareruimtes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KwaliteitscatalogusopenbareruimteRepository kwaliteitscatalogusopenbareruimteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKwaliteitscatalogusopenbareruimteMockMvc;

    private Kwaliteitscatalogusopenbareruimte kwaliteitscatalogusopenbareruimte;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kwaliteitscatalogusopenbareruimte createEntity(EntityManager em) {
        Kwaliteitscatalogusopenbareruimte kwaliteitscatalogusopenbareruimte = new Kwaliteitscatalogusopenbareruimte();
        return kwaliteitscatalogusopenbareruimte;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kwaliteitscatalogusopenbareruimte createUpdatedEntity(EntityManager em) {
        Kwaliteitscatalogusopenbareruimte kwaliteitscatalogusopenbareruimte = new Kwaliteitscatalogusopenbareruimte();
        return kwaliteitscatalogusopenbareruimte;
    }

    @BeforeEach
    public void initTest() {
        kwaliteitscatalogusopenbareruimte = createEntity(em);
    }

    @Test
    @Transactional
    void createKwaliteitscatalogusopenbareruimte() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kwaliteitscatalogusopenbareruimte
        var returnedKwaliteitscatalogusopenbareruimte = om.readValue(
            restKwaliteitscatalogusopenbareruimteMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(kwaliteitscatalogusopenbareruimte))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kwaliteitscatalogusopenbareruimte.class
        );

        // Validate the Kwaliteitscatalogusopenbareruimte in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKwaliteitscatalogusopenbareruimteUpdatableFieldsEquals(
            returnedKwaliteitscatalogusopenbareruimte,
            getPersistedKwaliteitscatalogusopenbareruimte(returnedKwaliteitscatalogusopenbareruimte)
        );
    }

    @Test
    @Transactional
    void createKwaliteitscatalogusopenbareruimteWithExistingId() throws Exception {
        // Create the Kwaliteitscatalogusopenbareruimte with an existing ID
        kwaliteitscatalogusopenbareruimte.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKwaliteitscatalogusopenbareruimteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kwaliteitscatalogusopenbareruimte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kwaliteitscatalogusopenbareruimte in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKwaliteitscatalogusopenbareruimtes() throws Exception {
        // Initialize the database
        kwaliteitscatalogusopenbareruimteRepository.saveAndFlush(kwaliteitscatalogusopenbareruimte);

        // Get all the kwaliteitscatalogusopenbareruimteList
        restKwaliteitscatalogusopenbareruimteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kwaliteitscatalogusopenbareruimte.getId().intValue())));
    }

    @Test
    @Transactional
    void getKwaliteitscatalogusopenbareruimte() throws Exception {
        // Initialize the database
        kwaliteitscatalogusopenbareruimteRepository.saveAndFlush(kwaliteitscatalogusopenbareruimte);

        // Get the kwaliteitscatalogusopenbareruimte
        restKwaliteitscatalogusopenbareruimteMockMvc
            .perform(get(ENTITY_API_URL_ID, kwaliteitscatalogusopenbareruimte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kwaliteitscatalogusopenbareruimte.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingKwaliteitscatalogusopenbareruimte() throws Exception {
        // Get the kwaliteitscatalogusopenbareruimte
        restKwaliteitscatalogusopenbareruimteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteKwaliteitscatalogusopenbareruimte() throws Exception {
        // Initialize the database
        kwaliteitscatalogusopenbareruimteRepository.saveAndFlush(kwaliteitscatalogusopenbareruimte);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kwaliteitscatalogusopenbareruimte
        restKwaliteitscatalogusopenbareruimteMockMvc
            .perform(delete(ENTITY_API_URL_ID, kwaliteitscatalogusopenbareruimte.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kwaliteitscatalogusopenbareruimteRepository.count();
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

    protected Kwaliteitscatalogusopenbareruimte getPersistedKwaliteitscatalogusopenbareruimte(
        Kwaliteitscatalogusopenbareruimte kwaliteitscatalogusopenbareruimte
    ) {
        return kwaliteitscatalogusopenbareruimteRepository.findById(kwaliteitscatalogusopenbareruimte.getId()).orElseThrow();
    }

    protected void assertPersistedKwaliteitscatalogusopenbareruimteToMatchAllProperties(
        Kwaliteitscatalogusopenbareruimte expectedKwaliteitscatalogusopenbareruimte
    ) {
        assertKwaliteitscatalogusopenbareruimteAllPropertiesEquals(
            expectedKwaliteitscatalogusopenbareruimte,
            getPersistedKwaliteitscatalogusopenbareruimte(expectedKwaliteitscatalogusopenbareruimte)
        );
    }

    protected void assertPersistedKwaliteitscatalogusopenbareruimteToMatchUpdatableProperties(
        Kwaliteitscatalogusopenbareruimte expectedKwaliteitscatalogusopenbareruimte
    ) {
        assertKwaliteitscatalogusopenbareruimteAllUpdatablePropertiesEquals(
            expectedKwaliteitscatalogusopenbareruimte,
            getPersistedKwaliteitscatalogusopenbareruimte(expectedKwaliteitscatalogusopenbareruimte)
        );
    }
}
