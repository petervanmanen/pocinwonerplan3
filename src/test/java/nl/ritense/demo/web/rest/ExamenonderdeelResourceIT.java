package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ExamenonderdeelAsserts.*;
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
import nl.ritense.demo.domain.Examenonderdeel;
import nl.ritense.demo.repository.ExamenonderdeelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ExamenonderdeelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExamenonderdeelResourceIT {

    private static final String ENTITY_API_URL = "/api/examenonderdeels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ExamenonderdeelRepository examenonderdeelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExamenonderdeelMockMvc;

    private Examenonderdeel examenonderdeel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Examenonderdeel createEntity(EntityManager em) {
        Examenonderdeel examenonderdeel = new Examenonderdeel();
        return examenonderdeel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Examenonderdeel createUpdatedEntity(EntityManager em) {
        Examenonderdeel examenonderdeel = new Examenonderdeel();
        return examenonderdeel;
    }

    @BeforeEach
    public void initTest() {
        examenonderdeel = createEntity(em);
    }

    @Test
    @Transactional
    void createExamenonderdeel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Examenonderdeel
        var returnedExamenonderdeel = om.readValue(
            restExamenonderdeelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(examenonderdeel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Examenonderdeel.class
        );

        // Validate the Examenonderdeel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertExamenonderdeelUpdatableFieldsEquals(returnedExamenonderdeel, getPersistedExamenonderdeel(returnedExamenonderdeel));
    }

    @Test
    @Transactional
    void createExamenonderdeelWithExistingId() throws Exception {
        // Create the Examenonderdeel with an existing ID
        examenonderdeel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamenonderdeelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(examenonderdeel)))
            .andExpect(status().isBadRequest());

        // Validate the Examenonderdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllExamenonderdeels() throws Exception {
        // Initialize the database
        examenonderdeelRepository.saveAndFlush(examenonderdeel);

        // Get all the examenonderdeelList
        restExamenonderdeelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examenonderdeel.getId().intValue())));
    }

    @Test
    @Transactional
    void getExamenonderdeel() throws Exception {
        // Initialize the database
        examenonderdeelRepository.saveAndFlush(examenonderdeel);

        // Get the examenonderdeel
        restExamenonderdeelMockMvc
            .perform(get(ENTITY_API_URL_ID, examenonderdeel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(examenonderdeel.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingExamenonderdeel() throws Exception {
        // Get the examenonderdeel
        restExamenonderdeelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingExamenonderdeel() throws Exception {
        // Initialize the database
        examenonderdeelRepository.saveAndFlush(examenonderdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the examenonderdeel
        Examenonderdeel updatedExamenonderdeel = examenonderdeelRepository.findById(examenonderdeel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedExamenonderdeel are not directly saved in db
        em.detach(updatedExamenonderdeel);

        restExamenonderdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedExamenonderdeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedExamenonderdeel))
            )
            .andExpect(status().isOk());

        // Validate the Examenonderdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedExamenonderdeelToMatchAllProperties(updatedExamenonderdeel);
    }

    @Test
    @Transactional
    void putNonExistingExamenonderdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examenonderdeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamenonderdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, examenonderdeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(examenonderdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Examenonderdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExamenonderdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examenonderdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamenonderdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(examenonderdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Examenonderdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExamenonderdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examenonderdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamenonderdeelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(examenonderdeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Examenonderdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExamenonderdeelWithPatch() throws Exception {
        // Initialize the database
        examenonderdeelRepository.saveAndFlush(examenonderdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the examenonderdeel using partial update
        Examenonderdeel partialUpdatedExamenonderdeel = new Examenonderdeel();
        partialUpdatedExamenonderdeel.setId(examenonderdeel.getId());

        restExamenonderdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExamenonderdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExamenonderdeel))
            )
            .andExpect(status().isOk());

        // Validate the Examenonderdeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExamenonderdeelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedExamenonderdeel, examenonderdeel),
            getPersistedExamenonderdeel(examenonderdeel)
        );
    }

    @Test
    @Transactional
    void fullUpdateExamenonderdeelWithPatch() throws Exception {
        // Initialize the database
        examenonderdeelRepository.saveAndFlush(examenonderdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the examenonderdeel using partial update
        Examenonderdeel partialUpdatedExamenonderdeel = new Examenonderdeel();
        partialUpdatedExamenonderdeel.setId(examenonderdeel.getId());

        restExamenonderdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExamenonderdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExamenonderdeel))
            )
            .andExpect(status().isOk());

        // Validate the Examenonderdeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExamenonderdeelUpdatableFieldsEquals(
            partialUpdatedExamenonderdeel,
            getPersistedExamenonderdeel(partialUpdatedExamenonderdeel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingExamenonderdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examenonderdeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamenonderdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, examenonderdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(examenonderdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Examenonderdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExamenonderdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examenonderdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamenonderdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(examenonderdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Examenonderdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExamenonderdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examenonderdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamenonderdeelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(examenonderdeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Examenonderdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExamenonderdeel() throws Exception {
        // Initialize the database
        examenonderdeelRepository.saveAndFlush(examenonderdeel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the examenonderdeel
        restExamenonderdeelMockMvc
            .perform(delete(ENTITY_API_URL_ID, examenonderdeel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return examenonderdeelRepository.count();
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

    protected Examenonderdeel getPersistedExamenonderdeel(Examenonderdeel examenonderdeel) {
        return examenonderdeelRepository.findById(examenonderdeel.getId()).orElseThrow();
    }

    protected void assertPersistedExamenonderdeelToMatchAllProperties(Examenonderdeel expectedExamenonderdeel) {
        assertExamenonderdeelAllPropertiesEquals(expectedExamenonderdeel, getPersistedExamenonderdeel(expectedExamenonderdeel));
    }

    protected void assertPersistedExamenonderdeelToMatchUpdatableProperties(Examenonderdeel expectedExamenonderdeel) {
        assertExamenonderdeelAllUpdatablePropertiesEquals(expectedExamenonderdeel, getPersistedExamenonderdeel(expectedExamenonderdeel));
    }
}
