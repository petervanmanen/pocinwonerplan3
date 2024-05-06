package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KlantbeoordelingredenAsserts.*;
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
import nl.ritense.demo.domain.Klantbeoordelingreden;
import nl.ritense.demo.repository.KlantbeoordelingredenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KlantbeoordelingredenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KlantbeoordelingredenResourceIT {

    private static final String DEFAULT_REDEN = "AAAAAAAAAA";
    private static final String UPDATED_REDEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/klantbeoordelingredens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KlantbeoordelingredenRepository klantbeoordelingredenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKlantbeoordelingredenMockMvc;

    private Klantbeoordelingreden klantbeoordelingreden;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Klantbeoordelingreden createEntity(EntityManager em) {
        Klantbeoordelingreden klantbeoordelingreden = new Klantbeoordelingreden().reden(DEFAULT_REDEN);
        return klantbeoordelingreden;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Klantbeoordelingreden createUpdatedEntity(EntityManager em) {
        Klantbeoordelingreden klantbeoordelingreden = new Klantbeoordelingreden().reden(UPDATED_REDEN);
        return klantbeoordelingreden;
    }

    @BeforeEach
    public void initTest() {
        klantbeoordelingreden = createEntity(em);
    }

    @Test
    @Transactional
    void createKlantbeoordelingreden() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Klantbeoordelingreden
        var returnedKlantbeoordelingreden = om.readValue(
            restKlantbeoordelingredenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(klantbeoordelingreden)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Klantbeoordelingreden.class
        );

        // Validate the Klantbeoordelingreden in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKlantbeoordelingredenUpdatableFieldsEquals(
            returnedKlantbeoordelingreden,
            getPersistedKlantbeoordelingreden(returnedKlantbeoordelingreden)
        );
    }

    @Test
    @Transactional
    void createKlantbeoordelingredenWithExistingId() throws Exception {
        // Create the Klantbeoordelingreden with an existing ID
        klantbeoordelingreden.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKlantbeoordelingredenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(klantbeoordelingreden)))
            .andExpect(status().isBadRequest());

        // Validate the Klantbeoordelingreden in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKlantbeoordelingredens() throws Exception {
        // Initialize the database
        klantbeoordelingredenRepository.saveAndFlush(klantbeoordelingreden);

        // Get all the klantbeoordelingredenList
        restKlantbeoordelingredenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(klantbeoordelingreden.getId().intValue())))
            .andExpect(jsonPath("$.[*].reden").value(hasItem(DEFAULT_REDEN)));
    }

    @Test
    @Transactional
    void getKlantbeoordelingreden() throws Exception {
        // Initialize the database
        klantbeoordelingredenRepository.saveAndFlush(klantbeoordelingreden);

        // Get the klantbeoordelingreden
        restKlantbeoordelingredenMockMvc
            .perform(get(ENTITY_API_URL_ID, klantbeoordelingreden.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(klantbeoordelingreden.getId().intValue()))
            .andExpect(jsonPath("$.reden").value(DEFAULT_REDEN));
    }

    @Test
    @Transactional
    void getNonExistingKlantbeoordelingreden() throws Exception {
        // Get the klantbeoordelingreden
        restKlantbeoordelingredenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKlantbeoordelingreden() throws Exception {
        // Initialize the database
        klantbeoordelingredenRepository.saveAndFlush(klantbeoordelingreden);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the klantbeoordelingreden
        Klantbeoordelingreden updatedKlantbeoordelingreden = klantbeoordelingredenRepository
            .findById(klantbeoordelingreden.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedKlantbeoordelingreden are not directly saved in db
        em.detach(updatedKlantbeoordelingreden);
        updatedKlantbeoordelingreden.reden(UPDATED_REDEN);

        restKlantbeoordelingredenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKlantbeoordelingreden.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKlantbeoordelingreden))
            )
            .andExpect(status().isOk());

        // Validate the Klantbeoordelingreden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKlantbeoordelingredenToMatchAllProperties(updatedKlantbeoordelingreden);
    }

    @Test
    @Transactional
    void putNonExistingKlantbeoordelingreden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantbeoordelingreden.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKlantbeoordelingredenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, klantbeoordelingreden.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(klantbeoordelingreden))
            )
            .andExpect(status().isBadRequest());

        // Validate the Klantbeoordelingreden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKlantbeoordelingreden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantbeoordelingreden.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlantbeoordelingredenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(klantbeoordelingreden))
            )
            .andExpect(status().isBadRequest());

        // Validate the Klantbeoordelingreden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKlantbeoordelingreden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantbeoordelingreden.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlantbeoordelingredenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(klantbeoordelingreden)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Klantbeoordelingreden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKlantbeoordelingredenWithPatch() throws Exception {
        // Initialize the database
        klantbeoordelingredenRepository.saveAndFlush(klantbeoordelingreden);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the klantbeoordelingreden using partial update
        Klantbeoordelingreden partialUpdatedKlantbeoordelingreden = new Klantbeoordelingreden();
        partialUpdatedKlantbeoordelingreden.setId(klantbeoordelingreden.getId());

        partialUpdatedKlantbeoordelingreden.reden(UPDATED_REDEN);

        restKlantbeoordelingredenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKlantbeoordelingreden.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKlantbeoordelingreden))
            )
            .andExpect(status().isOk());

        // Validate the Klantbeoordelingreden in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKlantbeoordelingredenUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKlantbeoordelingreden, klantbeoordelingreden),
            getPersistedKlantbeoordelingreden(klantbeoordelingreden)
        );
    }

    @Test
    @Transactional
    void fullUpdateKlantbeoordelingredenWithPatch() throws Exception {
        // Initialize the database
        klantbeoordelingredenRepository.saveAndFlush(klantbeoordelingreden);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the klantbeoordelingreden using partial update
        Klantbeoordelingreden partialUpdatedKlantbeoordelingreden = new Klantbeoordelingreden();
        partialUpdatedKlantbeoordelingreden.setId(klantbeoordelingreden.getId());

        partialUpdatedKlantbeoordelingreden.reden(UPDATED_REDEN);

        restKlantbeoordelingredenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKlantbeoordelingreden.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKlantbeoordelingreden))
            )
            .andExpect(status().isOk());

        // Validate the Klantbeoordelingreden in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKlantbeoordelingredenUpdatableFieldsEquals(
            partialUpdatedKlantbeoordelingreden,
            getPersistedKlantbeoordelingreden(partialUpdatedKlantbeoordelingreden)
        );
    }

    @Test
    @Transactional
    void patchNonExistingKlantbeoordelingreden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantbeoordelingreden.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKlantbeoordelingredenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, klantbeoordelingreden.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(klantbeoordelingreden))
            )
            .andExpect(status().isBadRequest());

        // Validate the Klantbeoordelingreden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKlantbeoordelingreden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantbeoordelingreden.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlantbeoordelingredenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(klantbeoordelingreden))
            )
            .andExpect(status().isBadRequest());

        // Validate the Klantbeoordelingreden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKlantbeoordelingreden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantbeoordelingreden.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlantbeoordelingredenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(klantbeoordelingreden)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Klantbeoordelingreden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKlantbeoordelingreden() throws Exception {
        // Initialize the database
        klantbeoordelingredenRepository.saveAndFlush(klantbeoordelingreden);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the klantbeoordelingreden
        restKlantbeoordelingredenMockMvc
            .perform(delete(ENTITY_API_URL_ID, klantbeoordelingreden.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return klantbeoordelingredenRepository.count();
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

    protected Klantbeoordelingreden getPersistedKlantbeoordelingreden(Klantbeoordelingreden klantbeoordelingreden) {
        return klantbeoordelingredenRepository.findById(klantbeoordelingreden.getId()).orElseThrow();
    }

    protected void assertPersistedKlantbeoordelingredenToMatchAllProperties(Klantbeoordelingreden expectedKlantbeoordelingreden) {
        assertKlantbeoordelingredenAllPropertiesEquals(
            expectedKlantbeoordelingreden,
            getPersistedKlantbeoordelingreden(expectedKlantbeoordelingreden)
        );
    }

    protected void assertPersistedKlantbeoordelingredenToMatchUpdatableProperties(Klantbeoordelingreden expectedKlantbeoordelingreden) {
        assertKlantbeoordelingredenAllUpdatablePropertiesEquals(
            expectedKlantbeoordelingreden,
            getPersistedKlantbeoordelingreden(expectedKlantbeoordelingreden)
        );
    }
}
