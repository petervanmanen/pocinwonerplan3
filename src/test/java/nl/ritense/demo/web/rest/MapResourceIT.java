package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MapAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Map;
import nl.ritense.demo.repository.MapRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MapResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MapResourceIT {

    private static final String ENTITY_API_URL = "/api/maps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMapMockMvc;

    private Map map;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Map createEntity(EntityManager em) {
        Map map = new Map();
        return map;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Map createUpdatedEntity(EntityManager em) {
        Map map = new Map();
        return map;
    }

    @BeforeEach
    public void initTest() {
        map = createEntity(em);
    }

    @Test
    @Transactional
    void createMap() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Map
        var returnedMap = om.readValue(
            restMapMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(map)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Map.class
        );

        // Validate the Map in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMapUpdatableFieldsEquals(returnedMap, getPersistedMap(returnedMap));
    }

    @Test
    @Transactional
    void createMapWithExistingId() throws Exception {
        // Create the Map with an existing ID
        map.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(map)))
            .andExpect(status().isBadRequest());

        // Validate the Map in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMaps() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);

        // Get all the mapList
        restMapMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(map.getId().intValue())));
    }

    @Test
    @Transactional
    void getMap() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);

        // Get the map
        restMapMockMvc
            .perform(get(ENTITY_API_URL_ID, map.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(map.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingMap() throws Exception {
        // Get the map
        restMapMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteMap() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the map
        restMapMockMvc.perform(delete(ENTITY_API_URL_ID, map.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return mapRepository.count();
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

    protected Map getPersistedMap(Map map) {
        return mapRepository.findById(map.getId()).orElseThrow();
    }

    protected void assertPersistedMapToMatchAllProperties(Map expectedMap) {
        assertMapAllPropertiesEquals(expectedMap, getPersistedMap(expectedMap));
    }

    protected void assertPersistedMapToMatchUpdatableProperties(Map expectedMap) {
        assertMapAllUpdatablePropertiesEquals(expectedMap, getPersistedMap(expectedMap));
    }
}
