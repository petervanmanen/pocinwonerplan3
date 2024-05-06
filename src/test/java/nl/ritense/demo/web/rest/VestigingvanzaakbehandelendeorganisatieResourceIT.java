package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VestigingvanzaakbehandelendeorganisatieAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Vestigingvanzaakbehandelendeorganisatie;
import nl.ritense.demo.repository.VestigingvanzaakbehandelendeorganisatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VestigingvanzaakbehandelendeorganisatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VestigingvanzaakbehandelendeorganisatieResourceIT {

    private static final String ENTITY_API_URL = "/api/vestigingvanzaakbehandelendeorganisaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VestigingvanzaakbehandelendeorganisatieRepository vestigingvanzaakbehandelendeorganisatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVestigingvanzaakbehandelendeorganisatieMockMvc;

    private Vestigingvanzaakbehandelendeorganisatie vestigingvanzaakbehandelendeorganisatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vestigingvanzaakbehandelendeorganisatie createEntity(EntityManager em) {
        Vestigingvanzaakbehandelendeorganisatie vestigingvanzaakbehandelendeorganisatie = new Vestigingvanzaakbehandelendeorganisatie();
        return vestigingvanzaakbehandelendeorganisatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vestigingvanzaakbehandelendeorganisatie createUpdatedEntity(EntityManager em) {
        Vestigingvanzaakbehandelendeorganisatie vestigingvanzaakbehandelendeorganisatie = new Vestigingvanzaakbehandelendeorganisatie();
        return vestigingvanzaakbehandelendeorganisatie;
    }

    @BeforeEach
    public void initTest() {
        vestigingvanzaakbehandelendeorganisatie = createEntity(em);
    }

    @Test
    @Transactional
    void createVestigingvanzaakbehandelendeorganisatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vestigingvanzaakbehandelendeorganisatie
        var returnedVestigingvanzaakbehandelendeorganisatie = om.readValue(
            restVestigingvanzaakbehandelendeorganisatieMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(vestigingvanzaakbehandelendeorganisatie))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vestigingvanzaakbehandelendeorganisatie.class
        );

        // Validate the Vestigingvanzaakbehandelendeorganisatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVestigingvanzaakbehandelendeorganisatieUpdatableFieldsEquals(
            returnedVestigingvanzaakbehandelendeorganisatie,
            getPersistedVestigingvanzaakbehandelendeorganisatie(returnedVestigingvanzaakbehandelendeorganisatie)
        );
    }

    @Test
    @Transactional
    void createVestigingvanzaakbehandelendeorganisatieWithExistingId() throws Exception {
        // Create the Vestigingvanzaakbehandelendeorganisatie with an existing ID
        vestigingvanzaakbehandelendeorganisatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVestigingvanzaakbehandelendeorganisatieMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vestigingvanzaakbehandelendeorganisatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vestigingvanzaakbehandelendeorganisatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVestigingvanzaakbehandelendeorganisaties() throws Exception {
        // Initialize the database
        vestigingvanzaakbehandelendeorganisatieRepository.saveAndFlush(vestigingvanzaakbehandelendeorganisatie);

        // Get all the vestigingvanzaakbehandelendeorganisatieList
        restVestigingvanzaakbehandelendeorganisatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vestigingvanzaakbehandelendeorganisatie.getId().intValue())));
    }

    @Test
    @Transactional
    void getVestigingvanzaakbehandelendeorganisatie() throws Exception {
        // Initialize the database
        vestigingvanzaakbehandelendeorganisatieRepository.saveAndFlush(vestigingvanzaakbehandelendeorganisatie);

        // Get the vestigingvanzaakbehandelendeorganisatie
        restVestigingvanzaakbehandelendeorganisatieMockMvc
            .perform(get(ENTITY_API_URL_ID, vestigingvanzaakbehandelendeorganisatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vestigingvanzaakbehandelendeorganisatie.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingVestigingvanzaakbehandelendeorganisatie() throws Exception {
        // Get the vestigingvanzaakbehandelendeorganisatie
        restVestigingvanzaakbehandelendeorganisatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteVestigingvanzaakbehandelendeorganisatie() throws Exception {
        // Initialize the database
        vestigingvanzaakbehandelendeorganisatieRepository.saveAndFlush(vestigingvanzaakbehandelendeorganisatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vestigingvanzaakbehandelendeorganisatie
        restVestigingvanzaakbehandelendeorganisatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, vestigingvanzaakbehandelendeorganisatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vestigingvanzaakbehandelendeorganisatieRepository.count();
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

    protected Vestigingvanzaakbehandelendeorganisatie getPersistedVestigingvanzaakbehandelendeorganisatie(
        Vestigingvanzaakbehandelendeorganisatie vestigingvanzaakbehandelendeorganisatie
    ) {
        return vestigingvanzaakbehandelendeorganisatieRepository.findById(vestigingvanzaakbehandelendeorganisatie.getId()).orElseThrow();
    }

    protected void assertPersistedVestigingvanzaakbehandelendeorganisatieToMatchAllProperties(
        Vestigingvanzaakbehandelendeorganisatie expectedVestigingvanzaakbehandelendeorganisatie
    ) {
        assertVestigingvanzaakbehandelendeorganisatieAllPropertiesEquals(
            expectedVestigingvanzaakbehandelendeorganisatie,
            getPersistedVestigingvanzaakbehandelendeorganisatie(expectedVestigingvanzaakbehandelendeorganisatie)
        );
    }

    protected void assertPersistedVestigingvanzaakbehandelendeorganisatieToMatchUpdatableProperties(
        Vestigingvanzaakbehandelendeorganisatie expectedVestigingvanzaakbehandelendeorganisatie
    ) {
        assertVestigingvanzaakbehandelendeorganisatieAllUpdatablePropertiesEquals(
            expectedVestigingvanzaakbehandelendeorganisatie,
            getPersistedVestigingvanzaakbehandelendeorganisatie(expectedVestigingvanzaakbehandelendeorganisatie)
        );
    }
}
