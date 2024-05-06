package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DomeinoftaakveldAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Domeinoftaakveld;
import nl.ritense.demo.repository.DomeinoftaakveldRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DomeinoftaakveldResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DomeinoftaakveldResourceIT {

    private static final String ENTITY_API_URL = "/api/domeinoftaakvelds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DomeinoftaakveldRepository domeinoftaakveldRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDomeinoftaakveldMockMvc;

    private Domeinoftaakveld domeinoftaakveld;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Domeinoftaakveld createEntity(EntityManager em) {
        Domeinoftaakveld domeinoftaakveld = new Domeinoftaakveld();
        return domeinoftaakveld;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Domeinoftaakveld createUpdatedEntity(EntityManager em) {
        Domeinoftaakveld domeinoftaakveld = new Domeinoftaakveld();
        return domeinoftaakveld;
    }

    @BeforeEach
    public void initTest() {
        domeinoftaakveld = createEntity(em);
    }

    @Test
    @Transactional
    void createDomeinoftaakveld() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Domeinoftaakveld
        var returnedDomeinoftaakveld = om.readValue(
            restDomeinoftaakveldMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(domeinoftaakveld)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Domeinoftaakveld.class
        );

        // Validate the Domeinoftaakveld in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDomeinoftaakveldUpdatableFieldsEquals(returnedDomeinoftaakveld, getPersistedDomeinoftaakveld(returnedDomeinoftaakveld));
    }

    @Test
    @Transactional
    void createDomeinoftaakveldWithExistingId() throws Exception {
        // Create the Domeinoftaakveld with an existing ID
        domeinoftaakveld.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDomeinoftaakveldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(domeinoftaakveld)))
            .andExpect(status().isBadRequest());

        // Validate the Domeinoftaakveld in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDomeinoftaakvelds() throws Exception {
        // Initialize the database
        domeinoftaakveldRepository.saveAndFlush(domeinoftaakveld);

        // Get all the domeinoftaakveldList
        restDomeinoftaakveldMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domeinoftaakveld.getId().intValue())));
    }

    @Test
    @Transactional
    void getDomeinoftaakveld() throws Exception {
        // Initialize the database
        domeinoftaakveldRepository.saveAndFlush(domeinoftaakveld);

        // Get the domeinoftaakveld
        restDomeinoftaakveldMockMvc
            .perform(get(ENTITY_API_URL_ID, domeinoftaakveld.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(domeinoftaakveld.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingDomeinoftaakveld() throws Exception {
        // Get the domeinoftaakveld
        restDomeinoftaakveldMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteDomeinoftaakveld() throws Exception {
        // Initialize the database
        domeinoftaakveldRepository.saveAndFlush(domeinoftaakveld);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the domeinoftaakveld
        restDomeinoftaakveldMockMvc
            .perform(delete(ENTITY_API_URL_ID, domeinoftaakveld.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return domeinoftaakveldRepository.count();
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

    protected Domeinoftaakveld getPersistedDomeinoftaakveld(Domeinoftaakveld domeinoftaakveld) {
        return domeinoftaakveldRepository.findById(domeinoftaakveld.getId()).orElseThrow();
    }

    protected void assertPersistedDomeinoftaakveldToMatchAllProperties(Domeinoftaakveld expectedDomeinoftaakveld) {
        assertDomeinoftaakveldAllPropertiesEquals(expectedDomeinoftaakveld, getPersistedDomeinoftaakveld(expectedDomeinoftaakveld));
    }

    protected void assertPersistedDomeinoftaakveldToMatchUpdatableProperties(Domeinoftaakveld expectedDomeinoftaakveld) {
        assertDomeinoftaakveldAllUpdatablePropertiesEquals(
            expectedDomeinoftaakveld,
            getPersistedDomeinoftaakveld(expectedDomeinoftaakveld)
        );
    }
}
