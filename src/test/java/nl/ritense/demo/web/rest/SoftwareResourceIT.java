package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SoftwareAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Software;
import nl.ritense.demo.repository.SoftwareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SoftwareResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SoftwareResourceIT {

    private static final String ENTITY_API_URL = "/api/software";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SoftwareRepository softwareRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoftwareMockMvc;

    private Software software;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Software createEntity(EntityManager em) {
        Software software = new Software();
        return software;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Software createUpdatedEntity(EntityManager em) {
        Software software = new Software();
        return software;
    }

    @BeforeEach
    public void initTest() {
        software = createEntity(em);
    }

    @Test
    @Transactional
    void createSoftware() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Software
        var returnedSoftware = om.readValue(
            restSoftwareMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(software)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Software.class
        );

        // Validate the Software in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSoftwareUpdatableFieldsEquals(returnedSoftware, getPersistedSoftware(returnedSoftware));
    }

    @Test
    @Transactional
    void createSoftwareWithExistingId() throws Exception {
        // Create the Software with an existing ID
        software.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoftwareMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(software)))
            .andExpect(status().isBadRequest());

        // Validate the Software in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSoftware() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList
        restSoftwareMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(software.getId().intValue())));
    }

    @Test
    @Transactional
    void getSoftware() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get the software
        restSoftwareMockMvc
            .perform(get(ENTITY_API_URL_ID, software.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(software.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingSoftware() throws Exception {
        // Get the software
        restSoftwareMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteSoftware() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the software
        restSoftwareMockMvc
            .perform(delete(ENTITY_API_URL_ID, software.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return softwareRepository.count();
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

    protected Software getPersistedSoftware(Software software) {
        return softwareRepository.findById(software.getId()).orElseThrow();
    }

    protected void assertPersistedSoftwareToMatchAllProperties(Software expectedSoftware) {
        assertSoftwareAllPropertiesEquals(expectedSoftware, getPersistedSoftware(expectedSoftware));
    }

    protected void assertPersistedSoftwareToMatchUpdatableProperties(Software expectedSoftware) {
        assertSoftwareAllUpdatablePropertiesEquals(expectedSoftware, getPersistedSoftware(expectedSoftware));
    }
}
