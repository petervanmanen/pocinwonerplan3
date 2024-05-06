package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.HardwareAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Hardware;
import nl.ritense.demo.repository.HardwareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HardwareResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HardwareResourceIT {

    private static final String ENTITY_API_URL = "/api/hardware";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HardwareRepository hardwareRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHardwareMockMvc;

    private Hardware hardware;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hardware createEntity(EntityManager em) {
        Hardware hardware = new Hardware();
        return hardware;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hardware createUpdatedEntity(EntityManager em) {
        Hardware hardware = new Hardware();
        return hardware;
    }

    @BeforeEach
    public void initTest() {
        hardware = createEntity(em);
    }

    @Test
    @Transactional
    void createHardware() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Hardware
        var returnedHardware = om.readValue(
            restHardwareMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hardware)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Hardware.class
        );

        // Validate the Hardware in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHardwareUpdatableFieldsEquals(returnedHardware, getPersistedHardware(returnedHardware));
    }

    @Test
    @Transactional
    void createHardwareWithExistingId() throws Exception {
        // Create the Hardware with an existing ID
        hardware.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHardwareMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hardware)))
            .andExpect(status().isBadRequest());

        // Validate the Hardware in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHardware() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList
        restHardwareMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hardware.getId().intValue())));
    }

    @Test
    @Transactional
    void getHardware() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get the hardware
        restHardwareMockMvc
            .perform(get(ENTITY_API_URL_ID, hardware.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hardware.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingHardware() throws Exception {
        // Get the hardware
        restHardwareMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteHardware() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hardware
        restHardwareMockMvc
            .perform(delete(ENTITY_API_URL_ID, hardware.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hardwareRepository.count();
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

    protected Hardware getPersistedHardware(Hardware hardware) {
        return hardwareRepository.findById(hardware.getId()).orElseThrow();
    }

    protected void assertPersistedHardwareToMatchAllProperties(Hardware expectedHardware) {
        assertHardwareAllPropertiesEquals(expectedHardware, getPersistedHardware(expectedHardware));
    }

    protected void assertPersistedHardwareToMatchUpdatableProperties(Hardware expectedHardware) {
        assertHardwareAllUpdatablePropertiesEquals(expectedHardware, getPersistedHardware(expectedHardware));
    }
}
