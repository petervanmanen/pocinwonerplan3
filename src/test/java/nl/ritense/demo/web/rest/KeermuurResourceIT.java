package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KeermuurAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Keermuur;
import nl.ritense.demo.repository.KeermuurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KeermuurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KeermuurResourceIT {

    private static final String DEFAULT_BELASTINGKLASSENIEUW = "AAAAAAAAAA";
    private static final String UPDATED_BELASTINGKLASSENIEUW = "BBBBBBBBBB";

    private static final String DEFAULT_BELASTINGKLASSEOUD = "AAAAAAAAAA";
    private static final String UPDATED_BELASTINGKLASSEOUD = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/keermuurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KeermuurRepository keermuurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKeermuurMockMvc;

    private Keermuur keermuur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Keermuur createEntity(EntityManager em) {
        Keermuur keermuur = new Keermuur()
            .belastingklassenieuw(DEFAULT_BELASTINGKLASSENIEUW)
            .belastingklasseoud(DEFAULT_BELASTINGKLASSEOUD)
            .type(DEFAULT_TYPE);
        return keermuur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Keermuur createUpdatedEntity(EntityManager em) {
        Keermuur keermuur = new Keermuur()
            .belastingklassenieuw(UPDATED_BELASTINGKLASSENIEUW)
            .belastingklasseoud(UPDATED_BELASTINGKLASSEOUD)
            .type(UPDATED_TYPE);
        return keermuur;
    }

    @BeforeEach
    public void initTest() {
        keermuur = createEntity(em);
    }

    @Test
    @Transactional
    void createKeermuur() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Keermuur
        var returnedKeermuur = om.readValue(
            restKeermuurMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(keermuur)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Keermuur.class
        );

        // Validate the Keermuur in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKeermuurUpdatableFieldsEquals(returnedKeermuur, getPersistedKeermuur(returnedKeermuur));
    }

    @Test
    @Transactional
    void createKeermuurWithExistingId() throws Exception {
        // Create the Keermuur with an existing ID
        keermuur.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKeermuurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(keermuur)))
            .andExpect(status().isBadRequest());

        // Validate the Keermuur in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKeermuurs() throws Exception {
        // Initialize the database
        keermuurRepository.saveAndFlush(keermuur);

        // Get all the keermuurList
        restKeermuurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keermuur.getId().intValue())))
            .andExpect(jsonPath("$.[*].belastingklassenieuw").value(hasItem(DEFAULT_BELASTINGKLASSENIEUW)))
            .andExpect(jsonPath("$.[*].belastingklasseoud").value(hasItem(DEFAULT_BELASTINGKLASSEOUD)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getKeermuur() throws Exception {
        // Initialize the database
        keermuurRepository.saveAndFlush(keermuur);

        // Get the keermuur
        restKeermuurMockMvc
            .perform(get(ENTITY_API_URL_ID, keermuur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(keermuur.getId().intValue()))
            .andExpect(jsonPath("$.belastingklassenieuw").value(DEFAULT_BELASTINGKLASSENIEUW))
            .andExpect(jsonPath("$.belastingklasseoud").value(DEFAULT_BELASTINGKLASSEOUD))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingKeermuur() throws Exception {
        // Get the keermuur
        restKeermuurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKeermuur() throws Exception {
        // Initialize the database
        keermuurRepository.saveAndFlush(keermuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the keermuur
        Keermuur updatedKeermuur = keermuurRepository.findById(keermuur.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKeermuur are not directly saved in db
        em.detach(updatedKeermuur);
        updatedKeermuur
            .belastingklassenieuw(UPDATED_BELASTINGKLASSENIEUW)
            .belastingklasseoud(UPDATED_BELASTINGKLASSEOUD)
            .type(UPDATED_TYPE);

        restKeermuurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKeermuur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKeermuur))
            )
            .andExpect(status().isOk());

        // Validate the Keermuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKeermuurToMatchAllProperties(updatedKeermuur);
    }

    @Test
    @Transactional
    void putNonExistingKeermuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keermuur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKeermuurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, keermuur.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(keermuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Keermuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKeermuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keermuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKeermuurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(keermuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Keermuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKeermuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keermuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKeermuurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(keermuur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Keermuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKeermuurWithPatch() throws Exception {
        // Initialize the database
        keermuurRepository.saveAndFlush(keermuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the keermuur using partial update
        Keermuur partialUpdatedKeermuur = new Keermuur();
        partialUpdatedKeermuur.setId(keermuur.getId());

        partialUpdatedKeermuur.type(UPDATED_TYPE);

        restKeermuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKeermuur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKeermuur))
            )
            .andExpect(status().isOk());

        // Validate the Keermuur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKeermuurUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedKeermuur, keermuur), getPersistedKeermuur(keermuur));
    }

    @Test
    @Transactional
    void fullUpdateKeermuurWithPatch() throws Exception {
        // Initialize the database
        keermuurRepository.saveAndFlush(keermuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the keermuur using partial update
        Keermuur partialUpdatedKeermuur = new Keermuur();
        partialUpdatedKeermuur.setId(keermuur.getId());

        partialUpdatedKeermuur
            .belastingklassenieuw(UPDATED_BELASTINGKLASSENIEUW)
            .belastingklasseoud(UPDATED_BELASTINGKLASSEOUD)
            .type(UPDATED_TYPE);

        restKeermuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKeermuur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKeermuur))
            )
            .andExpect(status().isOk());

        // Validate the Keermuur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKeermuurUpdatableFieldsEquals(partialUpdatedKeermuur, getPersistedKeermuur(partialUpdatedKeermuur));
    }

    @Test
    @Transactional
    void patchNonExistingKeermuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keermuur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKeermuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, keermuur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(keermuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Keermuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKeermuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keermuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKeermuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(keermuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Keermuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKeermuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keermuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKeermuurMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(keermuur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Keermuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKeermuur() throws Exception {
        // Initialize the database
        keermuurRepository.saveAndFlush(keermuur);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the keermuur
        restKeermuurMockMvc
            .perform(delete(ENTITY_API_URL_ID, keermuur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return keermuurRepository.count();
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

    protected Keermuur getPersistedKeermuur(Keermuur keermuur) {
        return keermuurRepository.findById(keermuur.getId()).orElseThrow();
    }

    protected void assertPersistedKeermuurToMatchAllProperties(Keermuur expectedKeermuur) {
        assertKeermuurAllPropertiesEquals(expectedKeermuur, getPersistedKeermuur(expectedKeermuur));
    }

    protected void assertPersistedKeermuurToMatchUpdatableProperties(Keermuur expectedKeermuur) {
        assertKeermuurAllUpdatablePropertiesEquals(expectedKeermuur, getPersistedKeermuur(expectedKeermuur));
    }
}
