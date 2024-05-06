package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerlengingzaakAsserts.*;
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
import nl.ritense.demo.domain.Verlengingzaak;
import nl.ritense.demo.repository.VerlengingzaakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerlengingzaakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerlengingzaakResourceIT {

    private static final String DEFAULT_DUURVERLENGING = "AAAAAAAAAA";
    private static final String UPDATED_DUURVERLENGING = "BBBBBBBBBB";

    private static final String DEFAULT_REDENVERLENGING = "AAAAAAAAAA";
    private static final String UPDATED_REDENVERLENGING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verlengingzaaks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerlengingzaakRepository verlengingzaakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerlengingzaakMockMvc;

    private Verlengingzaak verlengingzaak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verlengingzaak createEntity(EntityManager em) {
        Verlengingzaak verlengingzaak = new Verlengingzaak()
            .duurverlenging(DEFAULT_DUURVERLENGING)
            .redenverlenging(DEFAULT_REDENVERLENGING);
        return verlengingzaak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verlengingzaak createUpdatedEntity(EntityManager em) {
        Verlengingzaak verlengingzaak = new Verlengingzaak()
            .duurverlenging(UPDATED_DUURVERLENGING)
            .redenverlenging(UPDATED_REDENVERLENGING);
        return verlengingzaak;
    }

    @BeforeEach
    public void initTest() {
        verlengingzaak = createEntity(em);
    }

    @Test
    @Transactional
    void createVerlengingzaak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verlengingzaak
        var returnedVerlengingzaak = om.readValue(
            restVerlengingzaakMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verlengingzaak)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verlengingzaak.class
        );

        // Validate the Verlengingzaak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerlengingzaakUpdatableFieldsEquals(returnedVerlengingzaak, getPersistedVerlengingzaak(returnedVerlengingzaak));
    }

    @Test
    @Transactional
    void createVerlengingzaakWithExistingId() throws Exception {
        // Create the Verlengingzaak with an existing ID
        verlengingzaak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerlengingzaakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verlengingzaak)))
            .andExpect(status().isBadRequest());

        // Validate the Verlengingzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerlengingzaaks() throws Exception {
        // Initialize the database
        verlengingzaakRepository.saveAndFlush(verlengingzaak);

        // Get all the verlengingzaakList
        restVerlengingzaakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verlengingzaak.getId().intValue())))
            .andExpect(jsonPath("$.[*].duurverlenging").value(hasItem(DEFAULT_DUURVERLENGING)))
            .andExpect(jsonPath("$.[*].redenverlenging").value(hasItem(DEFAULT_REDENVERLENGING)));
    }

    @Test
    @Transactional
    void getVerlengingzaak() throws Exception {
        // Initialize the database
        verlengingzaakRepository.saveAndFlush(verlengingzaak);

        // Get the verlengingzaak
        restVerlengingzaakMockMvc
            .perform(get(ENTITY_API_URL_ID, verlengingzaak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verlengingzaak.getId().intValue()))
            .andExpect(jsonPath("$.duurverlenging").value(DEFAULT_DUURVERLENGING))
            .andExpect(jsonPath("$.redenverlenging").value(DEFAULT_REDENVERLENGING));
    }

    @Test
    @Transactional
    void getNonExistingVerlengingzaak() throws Exception {
        // Get the verlengingzaak
        restVerlengingzaakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerlengingzaak() throws Exception {
        // Initialize the database
        verlengingzaakRepository.saveAndFlush(verlengingzaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verlengingzaak
        Verlengingzaak updatedVerlengingzaak = verlengingzaakRepository.findById(verlengingzaak.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerlengingzaak are not directly saved in db
        em.detach(updatedVerlengingzaak);
        updatedVerlengingzaak.duurverlenging(UPDATED_DUURVERLENGING).redenverlenging(UPDATED_REDENVERLENGING);

        restVerlengingzaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerlengingzaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerlengingzaak))
            )
            .andExpect(status().isOk());

        // Validate the Verlengingzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerlengingzaakToMatchAllProperties(updatedVerlengingzaak);
    }

    @Test
    @Transactional
    void putNonExistingVerlengingzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlengingzaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerlengingzaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verlengingzaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verlengingzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verlengingzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerlengingzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlengingzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerlengingzaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verlengingzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verlengingzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerlengingzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlengingzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerlengingzaakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verlengingzaak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verlengingzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerlengingzaakWithPatch() throws Exception {
        // Initialize the database
        verlengingzaakRepository.saveAndFlush(verlengingzaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verlengingzaak using partial update
        Verlengingzaak partialUpdatedVerlengingzaak = new Verlengingzaak();
        partialUpdatedVerlengingzaak.setId(verlengingzaak.getId());

        restVerlengingzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerlengingzaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerlengingzaak))
            )
            .andExpect(status().isOk());

        // Validate the Verlengingzaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerlengingzaakUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerlengingzaak, verlengingzaak),
            getPersistedVerlengingzaak(verlengingzaak)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerlengingzaakWithPatch() throws Exception {
        // Initialize the database
        verlengingzaakRepository.saveAndFlush(verlengingzaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verlengingzaak using partial update
        Verlengingzaak partialUpdatedVerlengingzaak = new Verlengingzaak();
        partialUpdatedVerlengingzaak.setId(verlengingzaak.getId());

        partialUpdatedVerlengingzaak.duurverlenging(UPDATED_DUURVERLENGING).redenverlenging(UPDATED_REDENVERLENGING);

        restVerlengingzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerlengingzaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerlengingzaak))
            )
            .andExpect(status().isOk());

        // Validate the Verlengingzaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerlengingzaakUpdatableFieldsEquals(partialUpdatedVerlengingzaak, getPersistedVerlengingzaak(partialUpdatedVerlengingzaak));
    }

    @Test
    @Transactional
    void patchNonExistingVerlengingzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlengingzaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerlengingzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verlengingzaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verlengingzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verlengingzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerlengingzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlengingzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerlengingzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verlengingzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verlengingzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerlengingzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlengingzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerlengingzaakMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verlengingzaak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verlengingzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerlengingzaak() throws Exception {
        // Initialize the database
        verlengingzaakRepository.saveAndFlush(verlengingzaak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verlengingzaak
        restVerlengingzaakMockMvc
            .perform(delete(ENTITY_API_URL_ID, verlengingzaak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verlengingzaakRepository.count();
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

    protected Verlengingzaak getPersistedVerlengingzaak(Verlengingzaak verlengingzaak) {
        return verlengingzaakRepository.findById(verlengingzaak.getId()).orElseThrow();
    }

    protected void assertPersistedVerlengingzaakToMatchAllProperties(Verlengingzaak expectedVerlengingzaak) {
        assertVerlengingzaakAllPropertiesEquals(expectedVerlengingzaak, getPersistedVerlengingzaak(expectedVerlengingzaak));
    }

    protected void assertPersistedVerlengingzaakToMatchUpdatableProperties(Verlengingzaak expectedVerlengingzaak) {
        assertVerlengingzaakAllUpdatablePropertiesEquals(expectedVerlengingzaak, getPersistedVerlengingzaak(expectedVerlengingzaak));
    }
}
