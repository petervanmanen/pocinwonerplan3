package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GebruiksdoelAsserts.*;
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
import nl.ritense.demo.domain.Gebruiksdoel;
import nl.ritense.demo.repository.GebruiksdoelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GebruiksdoelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GebruiksdoelResourceIT {

    private static final String DEFAULT_GEBRUIKSDOELGEBOUWDOBJECT = "AAAAAAAAAA";
    private static final String UPDATED_GEBRUIKSDOELGEBOUWDOBJECT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gebruiksdoels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GebruiksdoelRepository gebruiksdoelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGebruiksdoelMockMvc;

    private Gebruiksdoel gebruiksdoel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gebruiksdoel createEntity(EntityManager em) {
        Gebruiksdoel gebruiksdoel = new Gebruiksdoel().gebruiksdoelgebouwdobject(DEFAULT_GEBRUIKSDOELGEBOUWDOBJECT);
        return gebruiksdoel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gebruiksdoel createUpdatedEntity(EntityManager em) {
        Gebruiksdoel gebruiksdoel = new Gebruiksdoel().gebruiksdoelgebouwdobject(UPDATED_GEBRUIKSDOELGEBOUWDOBJECT);
        return gebruiksdoel;
    }

    @BeforeEach
    public void initTest() {
        gebruiksdoel = createEntity(em);
    }

    @Test
    @Transactional
    void createGebruiksdoel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Gebruiksdoel
        var returnedGebruiksdoel = om.readValue(
            restGebruiksdoelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebruiksdoel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Gebruiksdoel.class
        );

        // Validate the Gebruiksdoel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGebruiksdoelUpdatableFieldsEquals(returnedGebruiksdoel, getPersistedGebruiksdoel(returnedGebruiksdoel));
    }

    @Test
    @Transactional
    void createGebruiksdoelWithExistingId() throws Exception {
        // Create the Gebruiksdoel with an existing ID
        gebruiksdoel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGebruiksdoelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebruiksdoel)))
            .andExpect(status().isBadRequest());

        // Validate the Gebruiksdoel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGebruiksdoels() throws Exception {
        // Initialize the database
        gebruiksdoelRepository.saveAndFlush(gebruiksdoel);

        // Get all the gebruiksdoelList
        restGebruiksdoelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gebruiksdoel.getId().intValue())))
            .andExpect(jsonPath("$.[*].gebruiksdoelgebouwdobject").value(hasItem(DEFAULT_GEBRUIKSDOELGEBOUWDOBJECT)));
    }

    @Test
    @Transactional
    void getGebruiksdoel() throws Exception {
        // Initialize the database
        gebruiksdoelRepository.saveAndFlush(gebruiksdoel);

        // Get the gebruiksdoel
        restGebruiksdoelMockMvc
            .perform(get(ENTITY_API_URL_ID, gebruiksdoel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gebruiksdoel.getId().intValue()))
            .andExpect(jsonPath("$.gebruiksdoelgebouwdobject").value(DEFAULT_GEBRUIKSDOELGEBOUWDOBJECT));
    }

    @Test
    @Transactional
    void getNonExistingGebruiksdoel() throws Exception {
        // Get the gebruiksdoel
        restGebruiksdoelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGebruiksdoel() throws Exception {
        // Initialize the database
        gebruiksdoelRepository.saveAndFlush(gebruiksdoel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebruiksdoel
        Gebruiksdoel updatedGebruiksdoel = gebruiksdoelRepository.findById(gebruiksdoel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGebruiksdoel are not directly saved in db
        em.detach(updatedGebruiksdoel);
        updatedGebruiksdoel.gebruiksdoelgebouwdobject(UPDATED_GEBRUIKSDOELGEBOUWDOBJECT);

        restGebruiksdoelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGebruiksdoel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGebruiksdoel))
            )
            .andExpect(status().isOk());

        // Validate the Gebruiksdoel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGebruiksdoelToMatchAllProperties(updatedGebruiksdoel);
    }

    @Test
    @Transactional
    void putNonExistingGebruiksdoel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebruiksdoel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGebruiksdoelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gebruiksdoel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gebruiksdoel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebruiksdoel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGebruiksdoel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebruiksdoel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebruiksdoelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gebruiksdoel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebruiksdoel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGebruiksdoel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebruiksdoel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebruiksdoelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebruiksdoel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gebruiksdoel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGebruiksdoelWithPatch() throws Exception {
        // Initialize the database
        gebruiksdoelRepository.saveAndFlush(gebruiksdoel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebruiksdoel using partial update
        Gebruiksdoel partialUpdatedGebruiksdoel = new Gebruiksdoel();
        partialUpdatedGebruiksdoel.setId(gebruiksdoel.getId());

        partialUpdatedGebruiksdoel.gebruiksdoelgebouwdobject(UPDATED_GEBRUIKSDOELGEBOUWDOBJECT);

        restGebruiksdoelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGebruiksdoel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGebruiksdoel))
            )
            .andExpect(status().isOk());

        // Validate the Gebruiksdoel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGebruiksdoelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGebruiksdoel, gebruiksdoel),
            getPersistedGebruiksdoel(gebruiksdoel)
        );
    }

    @Test
    @Transactional
    void fullUpdateGebruiksdoelWithPatch() throws Exception {
        // Initialize the database
        gebruiksdoelRepository.saveAndFlush(gebruiksdoel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebruiksdoel using partial update
        Gebruiksdoel partialUpdatedGebruiksdoel = new Gebruiksdoel();
        partialUpdatedGebruiksdoel.setId(gebruiksdoel.getId());

        partialUpdatedGebruiksdoel.gebruiksdoelgebouwdobject(UPDATED_GEBRUIKSDOELGEBOUWDOBJECT);

        restGebruiksdoelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGebruiksdoel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGebruiksdoel))
            )
            .andExpect(status().isOk());

        // Validate the Gebruiksdoel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGebruiksdoelUpdatableFieldsEquals(partialUpdatedGebruiksdoel, getPersistedGebruiksdoel(partialUpdatedGebruiksdoel));
    }

    @Test
    @Transactional
    void patchNonExistingGebruiksdoel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebruiksdoel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGebruiksdoelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gebruiksdoel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gebruiksdoel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebruiksdoel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGebruiksdoel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebruiksdoel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebruiksdoelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gebruiksdoel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebruiksdoel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGebruiksdoel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebruiksdoel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebruiksdoelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gebruiksdoel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gebruiksdoel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGebruiksdoel() throws Exception {
        // Initialize the database
        gebruiksdoelRepository.saveAndFlush(gebruiksdoel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gebruiksdoel
        restGebruiksdoelMockMvc
            .perform(delete(ENTITY_API_URL_ID, gebruiksdoel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gebruiksdoelRepository.count();
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

    protected Gebruiksdoel getPersistedGebruiksdoel(Gebruiksdoel gebruiksdoel) {
        return gebruiksdoelRepository.findById(gebruiksdoel.getId()).orElseThrow();
    }

    protected void assertPersistedGebruiksdoelToMatchAllProperties(Gebruiksdoel expectedGebruiksdoel) {
        assertGebruiksdoelAllPropertiesEquals(expectedGebruiksdoel, getPersistedGebruiksdoel(expectedGebruiksdoel));
    }

    protected void assertPersistedGebruiksdoelToMatchUpdatableProperties(Gebruiksdoel expectedGebruiksdoel) {
        assertGebruiksdoelAllUpdatablePropertiesEquals(expectedGebruiksdoel, getPersistedGebruiksdoel(expectedGebruiksdoel));
    }
}
