package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DienstAsserts.*;
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
import nl.ritense.demo.domain.Dienst;
import nl.ritense.demo.repository.DienstRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DienstResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DienstResourceIT {

    private static final String ENTITY_API_URL = "/api/diensts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DienstRepository dienstRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDienstMockMvc;

    private Dienst dienst;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dienst createEntity(EntityManager em) {
        Dienst dienst = new Dienst();
        return dienst;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dienst createUpdatedEntity(EntityManager em) {
        Dienst dienst = new Dienst();
        return dienst;
    }

    @BeforeEach
    public void initTest() {
        dienst = createEntity(em);
    }

    @Test
    @Transactional
    void createDienst() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Dienst
        var returnedDienst = om.readValue(
            restDienstMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dienst)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Dienst.class
        );

        // Validate the Dienst in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDienstUpdatableFieldsEquals(returnedDienst, getPersistedDienst(returnedDienst));
    }

    @Test
    @Transactional
    void createDienstWithExistingId() throws Exception {
        // Create the Dienst with an existing ID
        dienst.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDienstMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dienst)))
            .andExpect(status().isBadRequest());

        // Validate the Dienst in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDiensts() throws Exception {
        // Initialize the database
        dienstRepository.saveAndFlush(dienst);

        // Get all the dienstList
        restDienstMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dienst.getId().intValue())));
    }

    @Test
    @Transactional
    void getDienst() throws Exception {
        // Initialize the database
        dienstRepository.saveAndFlush(dienst);

        // Get the dienst
        restDienstMockMvc
            .perform(get(ENTITY_API_URL_ID, dienst.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dienst.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingDienst() throws Exception {
        // Get the dienst
        restDienstMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDienst() throws Exception {
        // Initialize the database
        dienstRepository.saveAndFlush(dienst);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dienst
        Dienst updatedDienst = dienstRepository.findById(dienst.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDienst are not directly saved in db
        em.detach(updatedDienst);

        restDienstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDienst.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDienst))
            )
            .andExpect(status().isOk());

        // Validate the Dienst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDienstToMatchAllProperties(updatedDienst);
    }

    @Test
    @Transactional
    void putNonExistingDienst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dienst.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDienstMockMvc
            .perform(put(ENTITY_API_URL_ID, dienst.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dienst)))
            .andExpect(status().isBadRequest());

        // Validate the Dienst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDienst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dienst.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDienstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dienst))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dienst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDienst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dienst.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDienstMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dienst)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dienst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDienstWithPatch() throws Exception {
        // Initialize the database
        dienstRepository.saveAndFlush(dienst);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dienst using partial update
        Dienst partialUpdatedDienst = new Dienst();
        partialUpdatedDienst.setId(dienst.getId());

        restDienstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDienst.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDienst))
            )
            .andExpect(status().isOk());

        // Validate the Dienst in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDienstUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDienst, dienst), getPersistedDienst(dienst));
    }

    @Test
    @Transactional
    void fullUpdateDienstWithPatch() throws Exception {
        // Initialize the database
        dienstRepository.saveAndFlush(dienst);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dienst using partial update
        Dienst partialUpdatedDienst = new Dienst();
        partialUpdatedDienst.setId(dienst.getId());

        restDienstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDienst.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDienst))
            )
            .andExpect(status().isOk());

        // Validate the Dienst in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDienstUpdatableFieldsEquals(partialUpdatedDienst, getPersistedDienst(partialUpdatedDienst));
    }

    @Test
    @Transactional
    void patchNonExistingDienst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dienst.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDienstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dienst.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(dienst))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dienst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDienst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dienst.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDienstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dienst))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dienst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDienst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dienst.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDienstMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(dienst)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dienst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDienst() throws Exception {
        // Initialize the database
        dienstRepository.saveAndFlush(dienst);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the dienst
        restDienstMockMvc
            .perform(delete(ENTITY_API_URL_ID, dienst.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return dienstRepository.count();
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

    protected Dienst getPersistedDienst(Dienst dienst) {
        return dienstRepository.findById(dienst.getId()).orElseThrow();
    }

    protected void assertPersistedDienstToMatchAllProperties(Dienst expectedDienst) {
        assertDienstAllPropertiesEquals(expectedDienst, getPersistedDienst(expectedDienst));
    }

    protected void assertPersistedDienstToMatchUpdatableProperties(Dienst expectedDienst) {
        assertDienstAllUpdatablePropertiesEquals(expectedDienst, getPersistedDienst(expectedDienst));
    }
}
