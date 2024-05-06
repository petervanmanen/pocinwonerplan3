package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SchouwrondeAsserts.*;
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
import nl.ritense.demo.domain.Schouwronde;
import nl.ritense.demo.repository.SchouwrondeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SchouwrondeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SchouwrondeResourceIT {

    private static final String ENTITY_API_URL = "/api/schouwrondes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SchouwrondeRepository schouwrondeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchouwrondeMockMvc;

    private Schouwronde schouwronde;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Schouwronde createEntity(EntityManager em) {
        Schouwronde schouwronde = new Schouwronde();
        return schouwronde;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Schouwronde createUpdatedEntity(EntityManager em) {
        Schouwronde schouwronde = new Schouwronde();
        return schouwronde;
    }

    @BeforeEach
    public void initTest() {
        schouwronde = createEntity(em);
    }

    @Test
    @Transactional
    void createSchouwronde() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Schouwronde
        var returnedSchouwronde = om.readValue(
            restSchouwrondeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(schouwronde)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Schouwronde.class
        );

        // Validate the Schouwronde in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSchouwrondeUpdatableFieldsEquals(returnedSchouwronde, getPersistedSchouwronde(returnedSchouwronde));
    }

    @Test
    @Transactional
    void createSchouwrondeWithExistingId() throws Exception {
        // Create the Schouwronde with an existing ID
        schouwronde.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchouwrondeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(schouwronde)))
            .andExpect(status().isBadRequest());

        // Validate the Schouwronde in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSchouwrondes() throws Exception {
        // Initialize the database
        schouwrondeRepository.saveAndFlush(schouwronde);

        // Get all the schouwrondeList
        restSchouwrondeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schouwronde.getId().intValue())));
    }

    @Test
    @Transactional
    void getSchouwronde() throws Exception {
        // Initialize the database
        schouwrondeRepository.saveAndFlush(schouwronde);

        // Get the schouwronde
        restSchouwrondeMockMvc
            .perform(get(ENTITY_API_URL_ID, schouwronde.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schouwronde.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingSchouwronde() throws Exception {
        // Get the schouwronde
        restSchouwrondeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSchouwronde() throws Exception {
        // Initialize the database
        schouwrondeRepository.saveAndFlush(schouwronde);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the schouwronde
        Schouwronde updatedSchouwronde = schouwrondeRepository.findById(schouwronde.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSchouwronde are not directly saved in db
        em.detach(updatedSchouwronde);

        restSchouwrondeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSchouwronde.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSchouwronde))
            )
            .andExpect(status().isOk());

        // Validate the Schouwronde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSchouwrondeToMatchAllProperties(updatedSchouwronde);
    }

    @Test
    @Transactional
    void putNonExistingSchouwronde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        schouwronde.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchouwrondeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, schouwronde.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(schouwronde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Schouwronde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSchouwronde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        schouwronde.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchouwrondeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(schouwronde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Schouwronde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSchouwronde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        schouwronde.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchouwrondeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(schouwronde)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Schouwronde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSchouwrondeWithPatch() throws Exception {
        // Initialize the database
        schouwrondeRepository.saveAndFlush(schouwronde);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the schouwronde using partial update
        Schouwronde partialUpdatedSchouwronde = new Schouwronde();
        partialUpdatedSchouwronde.setId(schouwronde.getId());

        restSchouwrondeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchouwronde.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSchouwronde))
            )
            .andExpect(status().isOk());

        // Validate the Schouwronde in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSchouwrondeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSchouwronde, schouwronde),
            getPersistedSchouwronde(schouwronde)
        );
    }

    @Test
    @Transactional
    void fullUpdateSchouwrondeWithPatch() throws Exception {
        // Initialize the database
        schouwrondeRepository.saveAndFlush(schouwronde);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the schouwronde using partial update
        Schouwronde partialUpdatedSchouwronde = new Schouwronde();
        partialUpdatedSchouwronde.setId(schouwronde.getId());

        restSchouwrondeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchouwronde.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSchouwronde))
            )
            .andExpect(status().isOk());

        // Validate the Schouwronde in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSchouwrondeUpdatableFieldsEquals(partialUpdatedSchouwronde, getPersistedSchouwronde(partialUpdatedSchouwronde));
    }

    @Test
    @Transactional
    void patchNonExistingSchouwronde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        schouwronde.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchouwrondeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, schouwronde.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(schouwronde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Schouwronde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSchouwronde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        schouwronde.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchouwrondeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(schouwronde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Schouwronde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSchouwronde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        schouwronde.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchouwrondeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(schouwronde)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Schouwronde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSchouwronde() throws Exception {
        // Initialize the database
        schouwrondeRepository.saveAndFlush(schouwronde);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the schouwronde
        restSchouwrondeMockMvc
            .perform(delete(ENTITY_API_URL_ID, schouwronde.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return schouwrondeRepository.count();
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

    protected Schouwronde getPersistedSchouwronde(Schouwronde schouwronde) {
        return schouwrondeRepository.findById(schouwronde.getId()).orElseThrow();
    }

    protected void assertPersistedSchouwrondeToMatchAllProperties(Schouwronde expectedSchouwronde) {
        assertSchouwrondeAllPropertiesEquals(expectedSchouwronde, getPersistedSchouwronde(expectedSchouwronde));
    }

    protected void assertPersistedSchouwrondeToMatchUpdatableProperties(Schouwronde expectedSchouwronde) {
        assertSchouwrondeAllUpdatablePropertiesEquals(expectedSchouwronde, getPersistedSchouwronde(expectedSchouwronde));
    }
}
