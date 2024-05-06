package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SollicitantAsserts.*;
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
import nl.ritense.demo.domain.Sollicitant;
import nl.ritense.demo.repository.SollicitantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SollicitantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SollicitantResourceIT {

    private static final String ENTITY_API_URL = "/api/sollicitants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SollicitantRepository sollicitantRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSollicitantMockMvc;

    private Sollicitant sollicitant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sollicitant createEntity(EntityManager em) {
        Sollicitant sollicitant = new Sollicitant();
        return sollicitant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sollicitant createUpdatedEntity(EntityManager em) {
        Sollicitant sollicitant = new Sollicitant();
        return sollicitant;
    }

    @BeforeEach
    public void initTest() {
        sollicitant = createEntity(em);
    }

    @Test
    @Transactional
    void createSollicitant() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sollicitant
        var returnedSollicitant = om.readValue(
            restSollicitantMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sollicitant)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Sollicitant.class
        );

        // Validate the Sollicitant in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSollicitantUpdatableFieldsEquals(returnedSollicitant, getPersistedSollicitant(returnedSollicitant));
    }

    @Test
    @Transactional
    void createSollicitantWithExistingId() throws Exception {
        // Create the Sollicitant with an existing ID
        sollicitant.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSollicitantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sollicitant)))
            .andExpect(status().isBadRequest());

        // Validate the Sollicitant in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSollicitants() throws Exception {
        // Initialize the database
        sollicitantRepository.saveAndFlush(sollicitant);

        // Get all the sollicitantList
        restSollicitantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sollicitant.getId().intValue())));
    }

    @Test
    @Transactional
    void getSollicitant() throws Exception {
        // Initialize the database
        sollicitantRepository.saveAndFlush(sollicitant);

        // Get the sollicitant
        restSollicitantMockMvc
            .perform(get(ENTITY_API_URL_ID, sollicitant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sollicitant.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingSollicitant() throws Exception {
        // Get the sollicitant
        restSollicitantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSollicitant() throws Exception {
        // Initialize the database
        sollicitantRepository.saveAndFlush(sollicitant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sollicitant
        Sollicitant updatedSollicitant = sollicitantRepository.findById(sollicitant.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSollicitant are not directly saved in db
        em.detach(updatedSollicitant);

        restSollicitantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSollicitant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSollicitant))
            )
            .andExpect(status().isOk());

        // Validate the Sollicitant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSollicitantToMatchAllProperties(updatedSollicitant);
    }

    @Test
    @Transactional
    void putNonExistingSollicitant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitant.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSollicitantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sollicitant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sollicitant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sollicitant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSollicitant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSollicitantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sollicitant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sollicitant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSollicitant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSollicitantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sollicitant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sollicitant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSollicitantWithPatch() throws Exception {
        // Initialize the database
        sollicitantRepository.saveAndFlush(sollicitant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sollicitant using partial update
        Sollicitant partialUpdatedSollicitant = new Sollicitant();
        partialUpdatedSollicitant.setId(sollicitant.getId());

        restSollicitantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSollicitant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSollicitant))
            )
            .andExpect(status().isOk());

        // Validate the Sollicitant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSollicitantUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSollicitant, sollicitant),
            getPersistedSollicitant(sollicitant)
        );
    }

    @Test
    @Transactional
    void fullUpdateSollicitantWithPatch() throws Exception {
        // Initialize the database
        sollicitantRepository.saveAndFlush(sollicitant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sollicitant using partial update
        Sollicitant partialUpdatedSollicitant = new Sollicitant();
        partialUpdatedSollicitant.setId(sollicitant.getId());

        restSollicitantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSollicitant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSollicitant))
            )
            .andExpect(status().isOk());

        // Validate the Sollicitant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSollicitantUpdatableFieldsEquals(partialUpdatedSollicitant, getPersistedSollicitant(partialUpdatedSollicitant));
    }

    @Test
    @Transactional
    void patchNonExistingSollicitant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitant.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSollicitantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sollicitant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sollicitant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sollicitant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSollicitant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSollicitantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sollicitant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sollicitant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSollicitant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSollicitantMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sollicitant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sollicitant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSollicitant() throws Exception {
        // Initialize the database
        sollicitantRepository.saveAndFlush(sollicitant);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sollicitant
        restSollicitantMockMvc
            .perform(delete(ENTITY_API_URL_ID, sollicitant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sollicitantRepository.count();
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

    protected Sollicitant getPersistedSollicitant(Sollicitant sollicitant) {
        return sollicitantRepository.findById(sollicitant.getId()).orElseThrow();
    }

    protected void assertPersistedSollicitantToMatchAllProperties(Sollicitant expectedSollicitant) {
        assertSollicitantAllPropertiesEquals(expectedSollicitant, getPersistedSollicitant(expectedSollicitant));
    }

    protected void assertPersistedSollicitantToMatchUpdatableProperties(Sollicitant expectedSollicitant) {
        assertSollicitantAllUpdatablePropertiesEquals(expectedSollicitant, getPersistedSollicitant(expectedSollicitant));
    }
}
