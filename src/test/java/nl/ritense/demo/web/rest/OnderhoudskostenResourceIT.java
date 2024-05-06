package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OnderhoudskostenAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Onderhoudskosten;
import nl.ritense.demo.repository.OnderhoudskostenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OnderhoudskostenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OnderhoudskostenResourceIT {

    private static final String ENTITY_API_URL = "/api/onderhoudskostens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OnderhoudskostenRepository onderhoudskostenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOnderhoudskostenMockMvc;

    private Onderhoudskosten onderhoudskosten;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderhoudskosten createEntity(EntityManager em) {
        Onderhoudskosten onderhoudskosten = new Onderhoudskosten();
        return onderhoudskosten;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderhoudskosten createUpdatedEntity(EntityManager em) {
        Onderhoudskosten onderhoudskosten = new Onderhoudskosten();
        return onderhoudskosten;
    }

    @BeforeEach
    public void initTest() {
        onderhoudskosten = createEntity(em);
    }

    @Test
    @Transactional
    void createOnderhoudskosten() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Onderhoudskosten
        var returnedOnderhoudskosten = om.readValue(
            restOnderhoudskostenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderhoudskosten)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Onderhoudskosten.class
        );

        // Validate the Onderhoudskosten in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOnderhoudskostenUpdatableFieldsEquals(returnedOnderhoudskosten, getPersistedOnderhoudskosten(returnedOnderhoudskosten));
    }

    @Test
    @Transactional
    void createOnderhoudskostenWithExistingId() throws Exception {
        // Create the Onderhoudskosten with an existing ID
        onderhoudskosten.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOnderhoudskostenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderhoudskosten)))
            .andExpect(status().isBadRequest());

        // Validate the Onderhoudskosten in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOnderhoudskostens() throws Exception {
        // Initialize the database
        onderhoudskostenRepository.saveAndFlush(onderhoudskosten);

        // Get all the onderhoudskostenList
        restOnderhoudskostenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(onderhoudskosten.getId().intValue())));
    }

    @Test
    @Transactional
    void getOnderhoudskosten() throws Exception {
        // Initialize the database
        onderhoudskostenRepository.saveAndFlush(onderhoudskosten);

        // Get the onderhoudskosten
        restOnderhoudskostenMockMvc
            .perform(get(ENTITY_API_URL_ID, onderhoudskosten.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(onderhoudskosten.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOnderhoudskosten() throws Exception {
        // Get the onderhoudskosten
        restOnderhoudskostenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteOnderhoudskosten() throws Exception {
        // Initialize the database
        onderhoudskostenRepository.saveAndFlush(onderhoudskosten);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the onderhoudskosten
        restOnderhoudskostenMockMvc
            .perform(delete(ENTITY_API_URL_ID, onderhoudskosten.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return onderhoudskostenRepository.count();
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

    protected Onderhoudskosten getPersistedOnderhoudskosten(Onderhoudskosten onderhoudskosten) {
        return onderhoudskostenRepository.findById(onderhoudskosten.getId()).orElseThrow();
    }

    protected void assertPersistedOnderhoudskostenToMatchAllProperties(Onderhoudskosten expectedOnderhoudskosten) {
        assertOnderhoudskostenAllPropertiesEquals(expectedOnderhoudskosten, getPersistedOnderhoudskosten(expectedOnderhoudskosten));
    }

    protected void assertPersistedOnderhoudskostenToMatchUpdatableProperties(Onderhoudskosten expectedOnderhoudskosten) {
        assertOnderhoudskostenAllUpdatablePropertiesEquals(
            expectedOnderhoudskosten,
            getPersistedOnderhoudskosten(expectedOnderhoudskosten)
        );
    }
}
