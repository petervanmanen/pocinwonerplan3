package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WinkelverkoopgroepAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Winkelverkoopgroep;
import nl.ritense.demo.repository.WinkelverkoopgroepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WinkelverkoopgroepResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WinkelverkoopgroepResourceIT {

    private static final String ENTITY_API_URL = "/api/winkelverkoopgroeps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WinkelverkoopgroepRepository winkelverkoopgroepRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWinkelverkoopgroepMockMvc;

    private Winkelverkoopgroep winkelverkoopgroep;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Winkelverkoopgroep createEntity(EntityManager em) {
        Winkelverkoopgroep winkelverkoopgroep = new Winkelverkoopgroep();
        return winkelverkoopgroep;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Winkelverkoopgroep createUpdatedEntity(EntityManager em) {
        Winkelverkoopgroep winkelverkoopgroep = new Winkelverkoopgroep();
        return winkelverkoopgroep;
    }

    @BeforeEach
    public void initTest() {
        winkelverkoopgroep = createEntity(em);
    }

    @Test
    @Transactional
    void createWinkelverkoopgroep() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Winkelverkoopgroep
        var returnedWinkelverkoopgroep = om.readValue(
            restWinkelverkoopgroepMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(winkelverkoopgroep)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Winkelverkoopgroep.class
        );

        // Validate the Winkelverkoopgroep in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWinkelverkoopgroepUpdatableFieldsEquals(
            returnedWinkelverkoopgroep,
            getPersistedWinkelverkoopgroep(returnedWinkelverkoopgroep)
        );
    }

    @Test
    @Transactional
    void createWinkelverkoopgroepWithExistingId() throws Exception {
        // Create the Winkelverkoopgroep with an existing ID
        winkelverkoopgroep.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWinkelverkoopgroepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(winkelverkoopgroep)))
            .andExpect(status().isBadRequest());

        // Validate the Winkelverkoopgroep in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWinkelverkoopgroeps() throws Exception {
        // Initialize the database
        winkelverkoopgroepRepository.saveAndFlush(winkelverkoopgroep);

        // Get all the winkelverkoopgroepList
        restWinkelverkoopgroepMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(winkelverkoopgroep.getId().intValue())));
    }

    @Test
    @Transactional
    void getWinkelverkoopgroep() throws Exception {
        // Initialize the database
        winkelverkoopgroepRepository.saveAndFlush(winkelverkoopgroep);

        // Get the winkelverkoopgroep
        restWinkelverkoopgroepMockMvc
            .perform(get(ENTITY_API_URL_ID, winkelverkoopgroep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(winkelverkoopgroep.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingWinkelverkoopgroep() throws Exception {
        // Get the winkelverkoopgroep
        restWinkelverkoopgroepMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteWinkelverkoopgroep() throws Exception {
        // Initialize the database
        winkelverkoopgroepRepository.saveAndFlush(winkelverkoopgroep);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the winkelverkoopgroep
        restWinkelverkoopgroepMockMvc
            .perform(delete(ENTITY_API_URL_ID, winkelverkoopgroep.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return winkelverkoopgroepRepository.count();
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

    protected Winkelverkoopgroep getPersistedWinkelverkoopgroep(Winkelverkoopgroep winkelverkoopgroep) {
        return winkelverkoopgroepRepository.findById(winkelverkoopgroep.getId()).orElseThrow();
    }

    protected void assertPersistedWinkelverkoopgroepToMatchAllProperties(Winkelverkoopgroep expectedWinkelverkoopgroep) {
        assertWinkelverkoopgroepAllPropertiesEquals(expectedWinkelverkoopgroep, getPersistedWinkelverkoopgroep(expectedWinkelverkoopgroep));
    }

    protected void assertPersistedWinkelverkoopgroepToMatchUpdatableProperties(Winkelverkoopgroep expectedWinkelverkoopgroep) {
        assertWinkelverkoopgroepAllUpdatablePropertiesEquals(
            expectedWinkelverkoopgroep,
            getPersistedWinkelverkoopgroep(expectedWinkelverkoopgroep)
        );
    }
}
