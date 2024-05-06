package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ParticipatiecomponentAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Participatiecomponent;
import nl.ritense.demo.repository.ParticipatiecomponentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ParticipatiecomponentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParticipatiecomponentResourceIT {

    private static final String ENTITY_API_URL = "/api/participatiecomponents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ParticipatiecomponentRepository participatiecomponentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParticipatiecomponentMockMvc;

    private Participatiecomponent participatiecomponent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participatiecomponent createEntity(EntityManager em) {
        Participatiecomponent participatiecomponent = new Participatiecomponent();
        return participatiecomponent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participatiecomponent createUpdatedEntity(EntityManager em) {
        Participatiecomponent participatiecomponent = new Participatiecomponent();
        return participatiecomponent;
    }

    @BeforeEach
    public void initTest() {
        participatiecomponent = createEntity(em);
    }

    @Test
    @Transactional
    void createParticipatiecomponent() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Participatiecomponent
        var returnedParticipatiecomponent = om.readValue(
            restParticipatiecomponentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(participatiecomponent)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Participatiecomponent.class
        );

        // Validate the Participatiecomponent in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertParticipatiecomponentUpdatableFieldsEquals(
            returnedParticipatiecomponent,
            getPersistedParticipatiecomponent(returnedParticipatiecomponent)
        );
    }

    @Test
    @Transactional
    void createParticipatiecomponentWithExistingId() throws Exception {
        // Create the Participatiecomponent with an existing ID
        participatiecomponent.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParticipatiecomponentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(participatiecomponent)))
            .andExpect(status().isBadRequest());

        // Validate the Participatiecomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParticipatiecomponents() throws Exception {
        // Initialize the database
        participatiecomponentRepository.saveAndFlush(participatiecomponent);

        // Get all the participatiecomponentList
        restParticipatiecomponentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(participatiecomponent.getId().intValue())));
    }

    @Test
    @Transactional
    void getParticipatiecomponent() throws Exception {
        // Initialize the database
        participatiecomponentRepository.saveAndFlush(participatiecomponent);

        // Get the participatiecomponent
        restParticipatiecomponentMockMvc
            .perform(get(ENTITY_API_URL_ID, participatiecomponent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(participatiecomponent.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingParticipatiecomponent() throws Exception {
        // Get the participatiecomponent
        restParticipatiecomponentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteParticipatiecomponent() throws Exception {
        // Initialize the database
        participatiecomponentRepository.saveAndFlush(participatiecomponent);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the participatiecomponent
        restParticipatiecomponentMockMvc
            .perform(delete(ENTITY_API_URL_ID, participatiecomponent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return participatiecomponentRepository.count();
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

    protected Participatiecomponent getPersistedParticipatiecomponent(Participatiecomponent participatiecomponent) {
        return participatiecomponentRepository.findById(participatiecomponent.getId()).orElseThrow();
    }

    protected void assertPersistedParticipatiecomponentToMatchAllProperties(Participatiecomponent expectedParticipatiecomponent) {
        assertParticipatiecomponentAllPropertiesEquals(
            expectedParticipatiecomponent,
            getPersistedParticipatiecomponent(expectedParticipatiecomponent)
        );
    }

    protected void assertPersistedParticipatiecomponentToMatchUpdatableProperties(Participatiecomponent expectedParticipatiecomponent) {
        assertParticipatiecomponentAllUpdatablePropertiesEquals(
            expectedParticipatiecomponent,
            getPersistedParticipatiecomponent(expectedParticipatiecomponent)
        );
    }
}
