package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KolkAsserts.*;
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
import nl.ritense.demo.domain.Kolk;
import nl.ritense.demo.repository.KolkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KolkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KolkResourceIT {

    private static final String DEFAULT_BEREIKBAARHEIDKOLK = "AAAAAAAAAA";
    private static final String UPDATED_BEREIKBAARHEIDKOLK = "BBBBBBBBBB";

    private static final String DEFAULT_RISICOGEBIED = "AAAAAAAAAA";
    private static final String UPDATED_RISICOGEBIED = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kolks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KolkRepository kolkRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKolkMockMvc;

    private Kolk kolk;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kolk createEntity(EntityManager em) {
        Kolk kolk = new Kolk().bereikbaarheidkolk(DEFAULT_BEREIKBAARHEIDKOLK).risicogebied(DEFAULT_RISICOGEBIED).type(DEFAULT_TYPE);
        return kolk;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kolk createUpdatedEntity(EntityManager em) {
        Kolk kolk = new Kolk().bereikbaarheidkolk(UPDATED_BEREIKBAARHEIDKOLK).risicogebied(UPDATED_RISICOGEBIED).type(UPDATED_TYPE);
        return kolk;
    }

    @BeforeEach
    public void initTest() {
        kolk = createEntity(em);
    }

    @Test
    @Transactional
    void createKolk() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kolk
        var returnedKolk = om.readValue(
            restKolkMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kolk)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kolk.class
        );

        // Validate the Kolk in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKolkUpdatableFieldsEquals(returnedKolk, getPersistedKolk(returnedKolk));
    }

    @Test
    @Transactional
    void createKolkWithExistingId() throws Exception {
        // Create the Kolk with an existing ID
        kolk.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKolkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kolk)))
            .andExpect(status().isBadRequest());

        // Validate the Kolk in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKolks() throws Exception {
        // Initialize the database
        kolkRepository.saveAndFlush(kolk);

        // Get all the kolkList
        restKolkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kolk.getId().intValue())))
            .andExpect(jsonPath("$.[*].bereikbaarheidkolk").value(hasItem(DEFAULT_BEREIKBAARHEIDKOLK)))
            .andExpect(jsonPath("$.[*].risicogebied").value(hasItem(DEFAULT_RISICOGEBIED)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getKolk() throws Exception {
        // Initialize the database
        kolkRepository.saveAndFlush(kolk);

        // Get the kolk
        restKolkMockMvc
            .perform(get(ENTITY_API_URL_ID, kolk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kolk.getId().intValue()))
            .andExpect(jsonPath("$.bereikbaarheidkolk").value(DEFAULT_BEREIKBAARHEIDKOLK))
            .andExpect(jsonPath("$.risicogebied").value(DEFAULT_RISICOGEBIED))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingKolk() throws Exception {
        // Get the kolk
        restKolkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKolk() throws Exception {
        // Initialize the database
        kolkRepository.saveAndFlush(kolk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kolk
        Kolk updatedKolk = kolkRepository.findById(kolk.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKolk are not directly saved in db
        em.detach(updatedKolk);
        updatedKolk.bereikbaarheidkolk(UPDATED_BEREIKBAARHEIDKOLK).risicogebied(UPDATED_RISICOGEBIED).type(UPDATED_TYPE);

        restKolkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKolk.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKolk))
            )
            .andExpect(status().isOk());

        // Validate the Kolk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKolkToMatchAllProperties(updatedKolk);
    }

    @Test
    @Transactional
    void putNonExistingKolk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kolk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKolkMockMvc
            .perform(put(ENTITY_API_URL_ID, kolk.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kolk)))
            .andExpect(status().isBadRequest());

        // Validate the Kolk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKolk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kolk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKolkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kolk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kolk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKolk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kolk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKolkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kolk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kolk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKolkWithPatch() throws Exception {
        // Initialize the database
        kolkRepository.saveAndFlush(kolk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kolk using partial update
        Kolk partialUpdatedKolk = new Kolk();
        partialUpdatedKolk.setId(kolk.getId());

        partialUpdatedKolk.type(UPDATED_TYPE);

        restKolkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKolk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKolk))
            )
            .andExpect(status().isOk());

        // Validate the Kolk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKolkUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedKolk, kolk), getPersistedKolk(kolk));
    }

    @Test
    @Transactional
    void fullUpdateKolkWithPatch() throws Exception {
        // Initialize the database
        kolkRepository.saveAndFlush(kolk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kolk using partial update
        Kolk partialUpdatedKolk = new Kolk();
        partialUpdatedKolk.setId(kolk.getId());

        partialUpdatedKolk.bereikbaarheidkolk(UPDATED_BEREIKBAARHEIDKOLK).risicogebied(UPDATED_RISICOGEBIED).type(UPDATED_TYPE);

        restKolkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKolk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKolk))
            )
            .andExpect(status().isOk());

        // Validate the Kolk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKolkUpdatableFieldsEquals(partialUpdatedKolk, getPersistedKolk(partialUpdatedKolk));
    }

    @Test
    @Transactional
    void patchNonExistingKolk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kolk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKolkMockMvc
            .perform(patch(ENTITY_API_URL_ID, kolk.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kolk)))
            .andExpect(status().isBadRequest());

        // Validate the Kolk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKolk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kolk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKolkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kolk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kolk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKolk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kolk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKolkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kolk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kolk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKolk() throws Exception {
        // Initialize the database
        kolkRepository.saveAndFlush(kolk);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kolk
        restKolkMockMvc
            .perform(delete(ENTITY_API_URL_ID, kolk.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kolkRepository.count();
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

    protected Kolk getPersistedKolk(Kolk kolk) {
        return kolkRepository.findById(kolk.getId()).orElseThrow();
    }

    protected void assertPersistedKolkToMatchAllProperties(Kolk expectedKolk) {
        assertKolkAllPropertiesEquals(expectedKolk, getPersistedKolk(expectedKolk));
    }

    protected void assertPersistedKolkToMatchUpdatableProperties(Kolk expectedKolk) {
        assertKolkAllUpdatablePropertiesEquals(expectedKolk, getPersistedKolk(expectedKolk));
    }
}
