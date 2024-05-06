package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KenmerkenzaakAsserts.*;
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
import nl.ritense.demo.domain.Kenmerkenzaak;
import nl.ritense.demo.repository.KenmerkenzaakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KenmerkenzaakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KenmerkenzaakResourceIT {

    private static final String DEFAULT_KENMERK = "AAAAAAAAAA";
    private static final String UPDATED_KENMERK = "BBBBBBBBBB";

    private static final String DEFAULT_KENMERKBRON = "AAAAAAAAAA";
    private static final String UPDATED_KENMERKBRON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kenmerkenzaaks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KenmerkenzaakRepository kenmerkenzaakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKenmerkenzaakMockMvc;

    private Kenmerkenzaak kenmerkenzaak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kenmerkenzaak createEntity(EntityManager em) {
        Kenmerkenzaak kenmerkenzaak = new Kenmerkenzaak().kenmerk(DEFAULT_KENMERK).kenmerkbron(DEFAULT_KENMERKBRON);
        return kenmerkenzaak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kenmerkenzaak createUpdatedEntity(EntityManager em) {
        Kenmerkenzaak kenmerkenzaak = new Kenmerkenzaak().kenmerk(UPDATED_KENMERK).kenmerkbron(UPDATED_KENMERKBRON);
        return kenmerkenzaak;
    }

    @BeforeEach
    public void initTest() {
        kenmerkenzaak = createEntity(em);
    }

    @Test
    @Transactional
    void createKenmerkenzaak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kenmerkenzaak
        var returnedKenmerkenzaak = om.readValue(
            restKenmerkenzaakMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kenmerkenzaak)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kenmerkenzaak.class
        );

        // Validate the Kenmerkenzaak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKenmerkenzaakUpdatableFieldsEquals(returnedKenmerkenzaak, getPersistedKenmerkenzaak(returnedKenmerkenzaak));
    }

    @Test
    @Transactional
    void createKenmerkenzaakWithExistingId() throws Exception {
        // Create the Kenmerkenzaak with an existing ID
        kenmerkenzaak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKenmerkenzaakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kenmerkenzaak)))
            .andExpect(status().isBadRequest());

        // Validate the Kenmerkenzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKenmerkenzaaks() throws Exception {
        // Initialize the database
        kenmerkenzaakRepository.saveAndFlush(kenmerkenzaak);

        // Get all the kenmerkenzaakList
        restKenmerkenzaakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kenmerkenzaak.getId().intValue())))
            .andExpect(jsonPath("$.[*].kenmerk").value(hasItem(DEFAULT_KENMERK)))
            .andExpect(jsonPath("$.[*].kenmerkbron").value(hasItem(DEFAULT_KENMERKBRON)));
    }

    @Test
    @Transactional
    void getKenmerkenzaak() throws Exception {
        // Initialize the database
        kenmerkenzaakRepository.saveAndFlush(kenmerkenzaak);

        // Get the kenmerkenzaak
        restKenmerkenzaakMockMvc
            .perform(get(ENTITY_API_URL_ID, kenmerkenzaak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kenmerkenzaak.getId().intValue()))
            .andExpect(jsonPath("$.kenmerk").value(DEFAULT_KENMERK))
            .andExpect(jsonPath("$.kenmerkbron").value(DEFAULT_KENMERKBRON));
    }

    @Test
    @Transactional
    void getNonExistingKenmerkenzaak() throws Exception {
        // Get the kenmerkenzaak
        restKenmerkenzaakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKenmerkenzaak() throws Exception {
        // Initialize the database
        kenmerkenzaakRepository.saveAndFlush(kenmerkenzaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kenmerkenzaak
        Kenmerkenzaak updatedKenmerkenzaak = kenmerkenzaakRepository.findById(kenmerkenzaak.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKenmerkenzaak are not directly saved in db
        em.detach(updatedKenmerkenzaak);
        updatedKenmerkenzaak.kenmerk(UPDATED_KENMERK).kenmerkbron(UPDATED_KENMERKBRON);

        restKenmerkenzaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKenmerkenzaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKenmerkenzaak))
            )
            .andExpect(status().isOk());

        // Validate the Kenmerkenzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKenmerkenzaakToMatchAllProperties(updatedKenmerkenzaak);
    }

    @Test
    @Transactional
    void putNonExistingKenmerkenzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kenmerkenzaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKenmerkenzaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kenmerkenzaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kenmerkenzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kenmerkenzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKenmerkenzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kenmerkenzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKenmerkenzaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kenmerkenzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kenmerkenzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKenmerkenzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kenmerkenzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKenmerkenzaakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kenmerkenzaak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kenmerkenzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKenmerkenzaakWithPatch() throws Exception {
        // Initialize the database
        kenmerkenzaakRepository.saveAndFlush(kenmerkenzaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kenmerkenzaak using partial update
        Kenmerkenzaak partialUpdatedKenmerkenzaak = new Kenmerkenzaak();
        partialUpdatedKenmerkenzaak.setId(kenmerkenzaak.getId());

        partialUpdatedKenmerkenzaak.kenmerk(UPDATED_KENMERK);

        restKenmerkenzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKenmerkenzaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKenmerkenzaak))
            )
            .andExpect(status().isOk());

        // Validate the Kenmerkenzaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKenmerkenzaakUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKenmerkenzaak, kenmerkenzaak),
            getPersistedKenmerkenzaak(kenmerkenzaak)
        );
    }

    @Test
    @Transactional
    void fullUpdateKenmerkenzaakWithPatch() throws Exception {
        // Initialize the database
        kenmerkenzaakRepository.saveAndFlush(kenmerkenzaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kenmerkenzaak using partial update
        Kenmerkenzaak partialUpdatedKenmerkenzaak = new Kenmerkenzaak();
        partialUpdatedKenmerkenzaak.setId(kenmerkenzaak.getId());

        partialUpdatedKenmerkenzaak.kenmerk(UPDATED_KENMERK).kenmerkbron(UPDATED_KENMERKBRON);

        restKenmerkenzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKenmerkenzaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKenmerkenzaak))
            )
            .andExpect(status().isOk());

        // Validate the Kenmerkenzaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKenmerkenzaakUpdatableFieldsEquals(partialUpdatedKenmerkenzaak, getPersistedKenmerkenzaak(partialUpdatedKenmerkenzaak));
    }

    @Test
    @Transactional
    void patchNonExistingKenmerkenzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kenmerkenzaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKenmerkenzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kenmerkenzaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kenmerkenzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kenmerkenzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKenmerkenzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kenmerkenzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKenmerkenzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kenmerkenzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kenmerkenzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKenmerkenzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kenmerkenzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKenmerkenzaakMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kenmerkenzaak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kenmerkenzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKenmerkenzaak() throws Exception {
        // Initialize the database
        kenmerkenzaakRepository.saveAndFlush(kenmerkenzaak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kenmerkenzaak
        restKenmerkenzaakMockMvc
            .perform(delete(ENTITY_API_URL_ID, kenmerkenzaak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kenmerkenzaakRepository.count();
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

    protected Kenmerkenzaak getPersistedKenmerkenzaak(Kenmerkenzaak kenmerkenzaak) {
        return kenmerkenzaakRepository.findById(kenmerkenzaak.getId()).orElseThrow();
    }

    protected void assertPersistedKenmerkenzaakToMatchAllProperties(Kenmerkenzaak expectedKenmerkenzaak) {
        assertKenmerkenzaakAllPropertiesEquals(expectedKenmerkenzaak, getPersistedKenmerkenzaak(expectedKenmerkenzaak));
    }

    protected void assertPersistedKenmerkenzaakToMatchUpdatableProperties(Kenmerkenzaak expectedKenmerkenzaak) {
        assertKenmerkenzaakAllUpdatablePropertiesEquals(expectedKenmerkenzaak, getPersistedKenmerkenzaak(expectedKenmerkenzaak));
    }
}
