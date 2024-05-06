package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.UitvoerdergraafwerkzaamhedenAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Uitvoerdergraafwerkzaamheden;
import nl.ritense.demo.repository.UitvoerdergraafwerkzaamhedenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UitvoerdergraafwerkzaamhedenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UitvoerdergraafwerkzaamhedenResourceIT {

    private static final String ENTITY_API_URL = "/api/uitvoerdergraafwerkzaamhedens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UitvoerdergraafwerkzaamhedenRepository uitvoerdergraafwerkzaamhedenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUitvoerdergraafwerkzaamhedenMockMvc;

    private Uitvoerdergraafwerkzaamheden uitvoerdergraafwerkzaamheden;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitvoerdergraafwerkzaamheden createEntity(EntityManager em) {
        Uitvoerdergraafwerkzaamheden uitvoerdergraafwerkzaamheden = new Uitvoerdergraafwerkzaamheden();
        return uitvoerdergraafwerkzaamheden;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitvoerdergraafwerkzaamheden createUpdatedEntity(EntityManager em) {
        Uitvoerdergraafwerkzaamheden uitvoerdergraafwerkzaamheden = new Uitvoerdergraafwerkzaamheden();
        return uitvoerdergraafwerkzaamheden;
    }

    @BeforeEach
    public void initTest() {
        uitvoerdergraafwerkzaamheden = createEntity(em);
    }

    @Test
    @Transactional
    void createUitvoerdergraafwerkzaamheden() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Uitvoerdergraafwerkzaamheden
        var returnedUitvoerdergraafwerkzaamheden = om.readValue(
            restUitvoerdergraafwerkzaamhedenMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitvoerdergraafwerkzaamheden))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Uitvoerdergraafwerkzaamheden.class
        );

        // Validate the Uitvoerdergraafwerkzaamheden in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertUitvoerdergraafwerkzaamhedenUpdatableFieldsEquals(
            returnedUitvoerdergraafwerkzaamheden,
            getPersistedUitvoerdergraafwerkzaamheden(returnedUitvoerdergraafwerkzaamheden)
        );
    }

    @Test
    @Transactional
    void createUitvoerdergraafwerkzaamhedenWithExistingId() throws Exception {
        // Create the Uitvoerdergraafwerkzaamheden with an existing ID
        uitvoerdergraafwerkzaamheden.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUitvoerdergraafwerkzaamhedenMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitvoerdergraafwerkzaamheden))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitvoerdergraafwerkzaamheden in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUitvoerdergraafwerkzaamhedens() throws Exception {
        // Initialize the database
        uitvoerdergraafwerkzaamhedenRepository.saveAndFlush(uitvoerdergraafwerkzaamheden);

        // Get all the uitvoerdergraafwerkzaamhedenList
        restUitvoerdergraafwerkzaamhedenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uitvoerdergraafwerkzaamheden.getId().intValue())));
    }

    @Test
    @Transactional
    void getUitvoerdergraafwerkzaamheden() throws Exception {
        // Initialize the database
        uitvoerdergraafwerkzaamhedenRepository.saveAndFlush(uitvoerdergraafwerkzaamheden);

        // Get the uitvoerdergraafwerkzaamheden
        restUitvoerdergraafwerkzaamhedenMockMvc
            .perform(get(ENTITY_API_URL_ID, uitvoerdergraafwerkzaamheden.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uitvoerdergraafwerkzaamheden.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingUitvoerdergraafwerkzaamheden() throws Exception {
        // Get the uitvoerdergraafwerkzaamheden
        restUitvoerdergraafwerkzaamhedenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteUitvoerdergraafwerkzaamheden() throws Exception {
        // Initialize the database
        uitvoerdergraafwerkzaamhedenRepository.saveAndFlush(uitvoerdergraafwerkzaamheden);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the uitvoerdergraafwerkzaamheden
        restUitvoerdergraafwerkzaamhedenMockMvc
            .perform(delete(ENTITY_API_URL_ID, uitvoerdergraafwerkzaamheden.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return uitvoerdergraafwerkzaamhedenRepository.count();
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

    protected Uitvoerdergraafwerkzaamheden getPersistedUitvoerdergraafwerkzaamheden(
        Uitvoerdergraafwerkzaamheden uitvoerdergraafwerkzaamheden
    ) {
        return uitvoerdergraafwerkzaamhedenRepository.findById(uitvoerdergraafwerkzaamheden.getId()).orElseThrow();
    }

    protected void assertPersistedUitvoerdergraafwerkzaamhedenToMatchAllProperties(
        Uitvoerdergraafwerkzaamheden expectedUitvoerdergraafwerkzaamheden
    ) {
        assertUitvoerdergraafwerkzaamhedenAllPropertiesEquals(
            expectedUitvoerdergraafwerkzaamheden,
            getPersistedUitvoerdergraafwerkzaamheden(expectedUitvoerdergraafwerkzaamheden)
        );
    }

    protected void assertPersistedUitvoerdergraafwerkzaamhedenToMatchUpdatableProperties(
        Uitvoerdergraafwerkzaamheden expectedUitvoerdergraafwerkzaamheden
    ) {
        assertUitvoerdergraafwerkzaamhedenAllUpdatablePropertiesEquals(
            expectedUitvoerdergraafwerkzaamheden,
            getPersistedUitvoerdergraafwerkzaamheden(expectedUitvoerdergraafwerkzaamheden)
        );
    }
}
