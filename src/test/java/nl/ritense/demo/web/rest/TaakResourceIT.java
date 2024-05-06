package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TaakAsserts.*;
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
import nl.ritense.demo.domain.Taak;
import nl.ritense.demo.repository.TaakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TaakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaakResourceIT {

    private static final String ENTITY_API_URL = "/api/taaks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaakRepository taakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaakMockMvc;

    private Taak taak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taak createEntity(EntityManager em) {
        Taak taak = new Taak();
        return taak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taak createUpdatedEntity(EntityManager em) {
        Taak taak = new Taak();
        return taak;
    }

    @BeforeEach
    public void initTest() {
        taak = createEntity(em);
    }

    @Test
    @Transactional
    void createTaak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Taak
        var returnedTaak = om.readValue(
            restTaakMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taak)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Taak.class
        );

        // Validate the Taak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTaakUpdatableFieldsEquals(returnedTaak, getPersistedTaak(returnedTaak));
    }

    @Test
    @Transactional
    void createTaakWithExistingId() throws Exception {
        // Create the Taak with an existing ID
        taak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taak)))
            .andExpect(status().isBadRequest());

        // Validate the Taak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaaks() throws Exception {
        // Initialize the database
        taakRepository.saveAndFlush(taak);

        // Get all the taakList
        restTaakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taak.getId().intValue())));
    }

    @Test
    @Transactional
    void getTaak() throws Exception {
        // Initialize the database
        taakRepository.saveAndFlush(taak);

        // Get the taak
        restTaakMockMvc
            .perform(get(ENTITY_API_URL_ID, taak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taak.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTaak() throws Exception {
        // Get the taak
        restTaakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTaak() throws Exception {
        // Initialize the database
        taakRepository.saveAndFlush(taak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taak
        Taak updatedTaak = taakRepository.findById(taak.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTaak are not directly saved in db
        em.detach(updatedTaak);

        restTaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTaak))
            )
            .andExpect(status().isOk());

        // Validate the Taak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTaakToMatchAllProperties(updatedTaak);
    }

    @Test
    @Transactional
    void putNonExistingTaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaakMockMvc
            .perform(put(ENTITY_API_URL_ID, taak.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taak)))
            .andExpect(status().isBadRequest());

        // Validate the Taak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(taak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Taak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaakWithPatch() throws Exception {
        // Initialize the database
        taakRepository.saveAndFlush(taak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taak using partial update
        Taak partialUpdatedTaak = new Taak();
        partialUpdatedTaak.setId(taak.getId());

        restTaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTaak))
            )
            .andExpect(status().isOk());

        // Validate the Taak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaakUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTaak, taak), getPersistedTaak(taak));
    }

    @Test
    @Transactional
    void fullUpdateTaakWithPatch() throws Exception {
        // Initialize the database
        taakRepository.saveAndFlush(taak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taak using partial update
        Taak partialUpdatedTaak = new Taak();
        partialUpdatedTaak.setId(taak.getId());

        restTaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTaak))
            )
            .andExpect(status().isOk());

        // Validate the Taak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaakUpdatableFieldsEquals(partialUpdatedTaak, getPersistedTaak(partialUpdatedTaak));
    }

    @Test
    @Transactional
    void patchNonExistingTaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaakMockMvc
            .perform(patch(ENTITY_API_URL_ID, taak.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(taak)))
            .andExpect(status().isBadRequest());

        // Validate the Taak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(taak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaakMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(taak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Taak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaak() throws Exception {
        // Initialize the database
        taakRepository.saveAndFlush(taak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the taak
        restTaakMockMvc
            .perform(delete(ENTITY_API_URL_ID, taak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return taakRepository.count();
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

    protected Taak getPersistedTaak(Taak taak) {
        return taakRepository.findById(taak.getId()).orElseThrow();
    }

    protected void assertPersistedTaakToMatchAllProperties(Taak expectedTaak) {
        assertTaakAllPropertiesEquals(expectedTaak, getPersistedTaak(expectedTaak));
    }

    protected void assertPersistedTaakToMatchUpdatableProperties(Taak expectedTaak) {
        assertTaakAllUpdatablePropertiesEquals(expectedTaak, getPersistedTaak(expectedTaak));
    }
}
