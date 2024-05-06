package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LinkbaarcmdbitemAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Linkbaarcmdbitem;
import nl.ritense.demo.repository.LinkbaarcmdbitemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LinkbaarcmdbitemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LinkbaarcmdbitemResourceIT {

    private static final String ENTITY_API_URL = "/api/linkbaarcmdbitems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LinkbaarcmdbitemRepository linkbaarcmdbitemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLinkbaarcmdbitemMockMvc;

    private Linkbaarcmdbitem linkbaarcmdbitem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Linkbaarcmdbitem createEntity(EntityManager em) {
        Linkbaarcmdbitem linkbaarcmdbitem = new Linkbaarcmdbitem();
        return linkbaarcmdbitem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Linkbaarcmdbitem createUpdatedEntity(EntityManager em) {
        Linkbaarcmdbitem linkbaarcmdbitem = new Linkbaarcmdbitem();
        return linkbaarcmdbitem;
    }

    @BeforeEach
    public void initTest() {
        linkbaarcmdbitem = createEntity(em);
    }

    @Test
    @Transactional
    void createLinkbaarcmdbitem() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Linkbaarcmdbitem
        var returnedLinkbaarcmdbitem = om.readValue(
            restLinkbaarcmdbitemMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(linkbaarcmdbitem)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Linkbaarcmdbitem.class
        );

        // Validate the Linkbaarcmdbitem in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLinkbaarcmdbitemUpdatableFieldsEquals(returnedLinkbaarcmdbitem, getPersistedLinkbaarcmdbitem(returnedLinkbaarcmdbitem));
    }

    @Test
    @Transactional
    void createLinkbaarcmdbitemWithExistingId() throws Exception {
        // Create the Linkbaarcmdbitem with an existing ID
        linkbaarcmdbitem.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLinkbaarcmdbitemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(linkbaarcmdbitem)))
            .andExpect(status().isBadRequest());

        // Validate the Linkbaarcmdbitem in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLinkbaarcmdbitems() throws Exception {
        // Initialize the database
        linkbaarcmdbitemRepository.saveAndFlush(linkbaarcmdbitem);

        // Get all the linkbaarcmdbitemList
        restLinkbaarcmdbitemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(linkbaarcmdbitem.getId().intValue())));
    }

    @Test
    @Transactional
    void getLinkbaarcmdbitem() throws Exception {
        // Initialize the database
        linkbaarcmdbitemRepository.saveAndFlush(linkbaarcmdbitem);

        // Get the linkbaarcmdbitem
        restLinkbaarcmdbitemMockMvc
            .perform(get(ENTITY_API_URL_ID, linkbaarcmdbitem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(linkbaarcmdbitem.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingLinkbaarcmdbitem() throws Exception {
        // Get the linkbaarcmdbitem
        restLinkbaarcmdbitemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteLinkbaarcmdbitem() throws Exception {
        // Initialize the database
        linkbaarcmdbitemRepository.saveAndFlush(linkbaarcmdbitem);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the linkbaarcmdbitem
        restLinkbaarcmdbitemMockMvc
            .perform(delete(ENTITY_API_URL_ID, linkbaarcmdbitem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return linkbaarcmdbitemRepository.count();
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

    protected Linkbaarcmdbitem getPersistedLinkbaarcmdbitem(Linkbaarcmdbitem linkbaarcmdbitem) {
        return linkbaarcmdbitemRepository.findById(linkbaarcmdbitem.getId()).orElseThrow();
    }

    protected void assertPersistedLinkbaarcmdbitemToMatchAllProperties(Linkbaarcmdbitem expectedLinkbaarcmdbitem) {
        assertLinkbaarcmdbitemAllPropertiesEquals(expectedLinkbaarcmdbitem, getPersistedLinkbaarcmdbitem(expectedLinkbaarcmdbitem));
    }

    protected void assertPersistedLinkbaarcmdbitemToMatchUpdatableProperties(Linkbaarcmdbitem expectedLinkbaarcmdbitem) {
        assertLinkbaarcmdbitemAllUpdatablePropertiesEquals(
            expectedLinkbaarcmdbitem,
            getPersistedLinkbaarcmdbitem(expectedLinkbaarcmdbitem)
        );
    }
}
