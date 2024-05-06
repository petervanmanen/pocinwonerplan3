package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WijzigingsverzoekAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Wijzigingsverzoek;
import nl.ritense.demo.repository.WijzigingsverzoekRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WijzigingsverzoekResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WijzigingsverzoekResourceIT {

    private static final String ENTITY_API_URL = "/api/wijzigingsverzoeks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WijzigingsverzoekRepository wijzigingsverzoekRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWijzigingsverzoekMockMvc;

    private Wijzigingsverzoek wijzigingsverzoek;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wijzigingsverzoek createEntity(EntityManager em) {
        Wijzigingsverzoek wijzigingsverzoek = new Wijzigingsverzoek();
        return wijzigingsverzoek;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wijzigingsverzoek createUpdatedEntity(EntityManager em) {
        Wijzigingsverzoek wijzigingsverzoek = new Wijzigingsverzoek();
        return wijzigingsverzoek;
    }

    @BeforeEach
    public void initTest() {
        wijzigingsverzoek = createEntity(em);
    }

    @Test
    @Transactional
    void createWijzigingsverzoek() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Wijzigingsverzoek
        var returnedWijzigingsverzoek = om.readValue(
            restWijzigingsverzoekMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wijzigingsverzoek)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Wijzigingsverzoek.class
        );

        // Validate the Wijzigingsverzoek in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWijzigingsverzoekUpdatableFieldsEquals(returnedWijzigingsverzoek, getPersistedWijzigingsverzoek(returnedWijzigingsverzoek));
    }

    @Test
    @Transactional
    void createWijzigingsverzoekWithExistingId() throws Exception {
        // Create the Wijzigingsverzoek with an existing ID
        wijzigingsverzoek.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWijzigingsverzoekMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wijzigingsverzoek)))
            .andExpect(status().isBadRequest());

        // Validate the Wijzigingsverzoek in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWijzigingsverzoeks() throws Exception {
        // Initialize the database
        wijzigingsverzoekRepository.saveAndFlush(wijzigingsverzoek);

        // Get all the wijzigingsverzoekList
        restWijzigingsverzoekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wijzigingsverzoek.getId().intValue())));
    }

    @Test
    @Transactional
    void getWijzigingsverzoek() throws Exception {
        // Initialize the database
        wijzigingsverzoekRepository.saveAndFlush(wijzigingsverzoek);

        // Get the wijzigingsverzoek
        restWijzigingsverzoekMockMvc
            .perform(get(ENTITY_API_URL_ID, wijzigingsverzoek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wijzigingsverzoek.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingWijzigingsverzoek() throws Exception {
        // Get the wijzigingsverzoek
        restWijzigingsverzoekMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteWijzigingsverzoek() throws Exception {
        // Initialize the database
        wijzigingsverzoekRepository.saveAndFlush(wijzigingsverzoek);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the wijzigingsverzoek
        restWijzigingsverzoekMockMvc
            .perform(delete(ENTITY_API_URL_ID, wijzigingsverzoek.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return wijzigingsverzoekRepository.count();
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

    protected Wijzigingsverzoek getPersistedWijzigingsverzoek(Wijzigingsverzoek wijzigingsverzoek) {
        return wijzigingsverzoekRepository.findById(wijzigingsverzoek.getId()).orElseThrow();
    }

    protected void assertPersistedWijzigingsverzoekToMatchAllProperties(Wijzigingsverzoek expectedWijzigingsverzoek) {
        assertWijzigingsverzoekAllPropertiesEquals(expectedWijzigingsverzoek, getPersistedWijzigingsverzoek(expectedWijzigingsverzoek));
    }

    protected void assertPersistedWijzigingsverzoekToMatchUpdatableProperties(Wijzigingsverzoek expectedWijzigingsverzoek) {
        assertWijzigingsverzoekAllUpdatablePropertiesEquals(
            expectedWijzigingsverzoek,
            getPersistedWijzigingsverzoek(expectedWijzigingsverzoek)
        );
    }
}
