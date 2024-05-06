package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OmgevingsvergunningAsserts.*;
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
import nl.ritense.demo.domain.Omgevingsvergunning;
import nl.ritense.demo.repository.OmgevingsvergunningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OmgevingsvergunningResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OmgevingsvergunningResourceIT {

    private static final String ENTITY_API_URL = "/api/omgevingsvergunnings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OmgevingsvergunningRepository omgevingsvergunningRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOmgevingsvergunningMockMvc;

    private Omgevingsvergunning omgevingsvergunning;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Omgevingsvergunning createEntity(EntityManager em) {
        Omgevingsvergunning omgevingsvergunning = new Omgevingsvergunning();
        return omgevingsvergunning;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Omgevingsvergunning createUpdatedEntity(EntityManager em) {
        Omgevingsvergunning omgevingsvergunning = new Omgevingsvergunning();
        return omgevingsvergunning;
    }

    @BeforeEach
    public void initTest() {
        omgevingsvergunning = createEntity(em);
    }

    @Test
    @Transactional
    void createOmgevingsvergunning() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Omgevingsvergunning
        var returnedOmgevingsvergunning = om.readValue(
            restOmgevingsvergunningMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omgevingsvergunning)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Omgevingsvergunning.class
        );

        // Validate the Omgevingsvergunning in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOmgevingsvergunningUpdatableFieldsEquals(
            returnedOmgevingsvergunning,
            getPersistedOmgevingsvergunning(returnedOmgevingsvergunning)
        );
    }

    @Test
    @Transactional
    void createOmgevingsvergunningWithExistingId() throws Exception {
        // Create the Omgevingsvergunning with an existing ID
        omgevingsvergunning.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOmgevingsvergunningMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omgevingsvergunning)))
            .andExpect(status().isBadRequest());

        // Validate the Omgevingsvergunning in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOmgevingsvergunnings() throws Exception {
        // Initialize the database
        omgevingsvergunningRepository.saveAndFlush(omgevingsvergunning);

        // Get all the omgevingsvergunningList
        restOmgevingsvergunningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(omgevingsvergunning.getId().intValue())));
    }

    @Test
    @Transactional
    void getOmgevingsvergunning() throws Exception {
        // Initialize the database
        omgevingsvergunningRepository.saveAndFlush(omgevingsvergunning);

        // Get the omgevingsvergunning
        restOmgevingsvergunningMockMvc
            .perform(get(ENTITY_API_URL_ID, omgevingsvergunning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(omgevingsvergunning.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOmgevingsvergunning() throws Exception {
        // Get the omgevingsvergunning
        restOmgevingsvergunningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOmgevingsvergunning() throws Exception {
        // Initialize the database
        omgevingsvergunningRepository.saveAndFlush(omgevingsvergunning);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the omgevingsvergunning
        Omgevingsvergunning updatedOmgevingsvergunning = omgevingsvergunningRepository.findById(omgevingsvergunning.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOmgevingsvergunning are not directly saved in db
        em.detach(updatedOmgevingsvergunning);

        restOmgevingsvergunningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOmgevingsvergunning.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOmgevingsvergunning))
            )
            .andExpect(status().isOk());

        // Validate the Omgevingsvergunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOmgevingsvergunningToMatchAllProperties(updatedOmgevingsvergunning);
    }

    @Test
    @Transactional
    void putNonExistingOmgevingsvergunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingsvergunning.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOmgevingsvergunningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, omgevingsvergunning.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(omgevingsvergunning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omgevingsvergunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOmgevingsvergunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingsvergunning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmgevingsvergunningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(omgevingsvergunning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omgevingsvergunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOmgevingsvergunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingsvergunning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmgevingsvergunningMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omgevingsvergunning)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Omgevingsvergunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOmgevingsvergunningWithPatch() throws Exception {
        // Initialize the database
        omgevingsvergunningRepository.saveAndFlush(omgevingsvergunning);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the omgevingsvergunning using partial update
        Omgevingsvergunning partialUpdatedOmgevingsvergunning = new Omgevingsvergunning();
        partialUpdatedOmgevingsvergunning.setId(omgevingsvergunning.getId());

        restOmgevingsvergunningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOmgevingsvergunning.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOmgevingsvergunning))
            )
            .andExpect(status().isOk());

        // Validate the Omgevingsvergunning in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOmgevingsvergunningUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOmgevingsvergunning, omgevingsvergunning),
            getPersistedOmgevingsvergunning(omgevingsvergunning)
        );
    }

    @Test
    @Transactional
    void fullUpdateOmgevingsvergunningWithPatch() throws Exception {
        // Initialize the database
        omgevingsvergunningRepository.saveAndFlush(omgevingsvergunning);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the omgevingsvergunning using partial update
        Omgevingsvergunning partialUpdatedOmgevingsvergunning = new Omgevingsvergunning();
        partialUpdatedOmgevingsvergunning.setId(omgevingsvergunning.getId());

        restOmgevingsvergunningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOmgevingsvergunning.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOmgevingsvergunning))
            )
            .andExpect(status().isOk());

        // Validate the Omgevingsvergunning in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOmgevingsvergunningUpdatableFieldsEquals(
            partialUpdatedOmgevingsvergunning,
            getPersistedOmgevingsvergunning(partialUpdatedOmgevingsvergunning)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOmgevingsvergunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingsvergunning.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOmgevingsvergunningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, omgevingsvergunning.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(omgevingsvergunning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omgevingsvergunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOmgevingsvergunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingsvergunning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmgevingsvergunningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(omgevingsvergunning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omgevingsvergunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOmgevingsvergunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingsvergunning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmgevingsvergunningMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(omgevingsvergunning)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Omgevingsvergunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOmgevingsvergunning() throws Exception {
        // Initialize the database
        omgevingsvergunningRepository.saveAndFlush(omgevingsvergunning);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the omgevingsvergunning
        restOmgevingsvergunningMockMvc
            .perform(delete(ENTITY_API_URL_ID, omgevingsvergunning.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return omgevingsvergunningRepository.count();
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

    protected Omgevingsvergunning getPersistedOmgevingsvergunning(Omgevingsvergunning omgevingsvergunning) {
        return omgevingsvergunningRepository.findById(omgevingsvergunning.getId()).orElseThrow();
    }

    protected void assertPersistedOmgevingsvergunningToMatchAllProperties(Omgevingsvergunning expectedOmgevingsvergunning) {
        assertOmgevingsvergunningAllPropertiesEquals(
            expectedOmgevingsvergunning,
            getPersistedOmgevingsvergunning(expectedOmgevingsvergunning)
        );
    }

    protected void assertPersistedOmgevingsvergunningToMatchUpdatableProperties(Omgevingsvergunning expectedOmgevingsvergunning) {
        assertOmgevingsvergunningAllUpdatablePropertiesEquals(
            expectedOmgevingsvergunning,
            getPersistedOmgevingsvergunning(expectedOmgevingsvergunning)
        );
    }
}
