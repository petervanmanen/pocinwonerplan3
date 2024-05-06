package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ExamenAsserts.*;
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
import nl.ritense.demo.domain.Examen;
import nl.ritense.demo.repository.ExamenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ExamenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExamenResourceIT {

    private static final String ENTITY_API_URL = "/api/examen";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ExamenRepository examenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExamenMockMvc;

    private Examen examen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Examen createEntity(EntityManager em) {
        Examen examen = new Examen();
        return examen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Examen createUpdatedEntity(EntityManager em) {
        Examen examen = new Examen();
        return examen;
    }

    @BeforeEach
    public void initTest() {
        examen = createEntity(em);
    }

    @Test
    @Transactional
    void createExamen() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Examen
        var returnedExamen = om.readValue(
            restExamenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(examen)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Examen.class
        );

        // Validate the Examen in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertExamenUpdatableFieldsEquals(returnedExamen, getPersistedExamen(returnedExamen));
    }

    @Test
    @Transactional
    void createExamenWithExistingId() throws Exception {
        // Create the Examen with an existing ID
        examen.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(examen)))
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        // Get all the examenList
        restExamenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examen.getId().intValue())));
    }

    @Test
    @Transactional
    void getExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        // Get the examen
        restExamenMockMvc
            .perform(get(ENTITY_API_URL_ID, examen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(examen.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingExamen() throws Exception {
        // Get the examen
        restExamenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the examen
        Examen updatedExamen = examenRepository.findById(examen.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedExamen are not directly saved in db
        em.detach(updatedExamen);

        restExamenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedExamen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedExamen))
            )
            .andExpect(status().isOk());

        // Validate the Examen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedExamenToMatchAllProperties(updatedExamen);
    }

    @Test
    @Transactional
    void putNonExistingExamen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examen.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamenMockMvc
            .perform(put(ENTITY_API_URL_ID, examen.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(examen)))
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExamen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(examen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExamen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(examen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Examen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExamenWithPatch() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the examen using partial update
        Examen partialUpdatedExamen = new Examen();
        partialUpdatedExamen.setId(examen.getId());

        restExamenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExamen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExamen))
            )
            .andExpect(status().isOk());

        // Validate the Examen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExamenUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedExamen, examen), getPersistedExamen(examen));
    }

    @Test
    @Transactional
    void fullUpdateExamenWithPatch() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the examen using partial update
        Examen partialUpdatedExamen = new Examen();
        partialUpdatedExamen.setId(examen.getId());

        restExamenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExamen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExamen))
            )
            .andExpect(status().isOk());

        // Validate the Examen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExamenUpdatableFieldsEquals(partialUpdatedExamen, getPersistedExamen(partialUpdatedExamen));
    }

    @Test
    @Transactional
    void patchNonExistingExamen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examen.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, examen.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(examen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExamen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(examen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExamen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        examen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(examen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Examen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the examen
        restExamenMockMvc
            .perform(delete(ENTITY_API_URL_ID, examen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return examenRepository.count();
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

    protected Examen getPersistedExamen(Examen examen) {
        return examenRepository.findById(examen.getId()).orElseThrow();
    }

    protected void assertPersistedExamenToMatchAllProperties(Examen expectedExamen) {
        assertExamenAllPropertiesEquals(expectedExamen, getPersistedExamen(expectedExamen));
    }

    protected void assertPersistedExamenToMatchUpdatableProperties(Examen expectedExamen) {
        assertExamenAllUpdatablePropertiesEquals(expectedExamen, getPersistedExamen(expectedExamen));
    }
}
