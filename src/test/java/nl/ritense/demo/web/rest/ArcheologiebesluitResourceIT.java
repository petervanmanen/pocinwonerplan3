package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ArcheologiebesluitAsserts.*;
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
import nl.ritense.demo.domain.Archeologiebesluit;
import nl.ritense.demo.repository.ArcheologiebesluitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ArcheologiebesluitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArcheologiebesluitResourceIT {

    private static final String ENTITY_API_URL = "/api/archeologiebesluits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ArcheologiebesluitRepository archeologiebesluitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArcheologiebesluitMockMvc;

    private Archeologiebesluit archeologiebesluit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Archeologiebesluit createEntity(EntityManager em) {
        Archeologiebesluit archeologiebesluit = new Archeologiebesluit();
        return archeologiebesluit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Archeologiebesluit createUpdatedEntity(EntityManager em) {
        Archeologiebesluit archeologiebesluit = new Archeologiebesluit();
        return archeologiebesluit;
    }

    @BeforeEach
    public void initTest() {
        archeologiebesluit = createEntity(em);
    }

    @Test
    @Transactional
    void createArcheologiebesluit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Archeologiebesluit
        var returnedArcheologiebesluit = om.readValue(
            restArcheologiebesluitMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archeologiebesluit)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Archeologiebesluit.class
        );

        // Validate the Archeologiebesluit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertArcheologiebesluitUpdatableFieldsEquals(
            returnedArcheologiebesluit,
            getPersistedArcheologiebesluit(returnedArcheologiebesluit)
        );
    }

    @Test
    @Transactional
    void createArcheologiebesluitWithExistingId() throws Exception {
        // Create the Archeologiebesluit with an existing ID
        archeologiebesluit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArcheologiebesluitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archeologiebesluit)))
            .andExpect(status().isBadRequest());

        // Validate the Archeologiebesluit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArcheologiebesluits() throws Exception {
        // Initialize the database
        archeologiebesluitRepository.saveAndFlush(archeologiebesluit);

        // Get all the archeologiebesluitList
        restArcheologiebesluitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(archeologiebesluit.getId().intValue())));
    }

    @Test
    @Transactional
    void getArcheologiebesluit() throws Exception {
        // Initialize the database
        archeologiebesluitRepository.saveAndFlush(archeologiebesluit);

        // Get the archeologiebesluit
        restArcheologiebesluitMockMvc
            .perform(get(ENTITY_API_URL_ID, archeologiebesluit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(archeologiebesluit.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingArcheologiebesluit() throws Exception {
        // Get the archeologiebesluit
        restArcheologiebesluitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArcheologiebesluit() throws Exception {
        // Initialize the database
        archeologiebesluitRepository.saveAndFlush(archeologiebesluit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the archeologiebesluit
        Archeologiebesluit updatedArcheologiebesluit = archeologiebesluitRepository.findById(archeologiebesluit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArcheologiebesluit are not directly saved in db
        em.detach(updatedArcheologiebesluit);

        restArcheologiebesluitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedArcheologiebesluit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedArcheologiebesluit))
            )
            .andExpect(status().isOk());

        // Validate the Archeologiebesluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedArcheologiebesluitToMatchAllProperties(updatedArcheologiebesluit);
    }

    @Test
    @Transactional
    void putNonExistingArcheologiebesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archeologiebesluit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArcheologiebesluitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, archeologiebesluit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(archeologiebesluit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archeologiebesluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArcheologiebesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archeologiebesluit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArcheologiebesluitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(archeologiebesluit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archeologiebesluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArcheologiebesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archeologiebesluit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArcheologiebesluitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archeologiebesluit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Archeologiebesluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArcheologiebesluitWithPatch() throws Exception {
        // Initialize the database
        archeologiebesluitRepository.saveAndFlush(archeologiebesluit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the archeologiebesluit using partial update
        Archeologiebesluit partialUpdatedArcheologiebesluit = new Archeologiebesluit();
        partialUpdatedArcheologiebesluit.setId(archeologiebesluit.getId());

        restArcheologiebesluitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArcheologiebesluit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArcheologiebesluit))
            )
            .andExpect(status().isOk());

        // Validate the Archeologiebesluit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArcheologiebesluitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedArcheologiebesluit, archeologiebesluit),
            getPersistedArcheologiebesluit(archeologiebesluit)
        );
    }

    @Test
    @Transactional
    void fullUpdateArcheologiebesluitWithPatch() throws Exception {
        // Initialize the database
        archeologiebesluitRepository.saveAndFlush(archeologiebesluit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the archeologiebesluit using partial update
        Archeologiebesluit partialUpdatedArcheologiebesluit = new Archeologiebesluit();
        partialUpdatedArcheologiebesluit.setId(archeologiebesluit.getId());

        restArcheologiebesluitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArcheologiebesluit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArcheologiebesluit))
            )
            .andExpect(status().isOk());

        // Validate the Archeologiebesluit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArcheologiebesluitUpdatableFieldsEquals(
            partialUpdatedArcheologiebesluit,
            getPersistedArcheologiebesluit(partialUpdatedArcheologiebesluit)
        );
    }

    @Test
    @Transactional
    void patchNonExistingArcheologiebesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archeologiebesluit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArcheologiebesluitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, archeologiebesluit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(archeologiebesluit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archeologiebesluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArcheologiebesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archeologiebesluit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArcheologiebesluitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(archeologiebesluit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archeologiebesluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArcheologiebesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archeologiebesluit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArcheologiebesluitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(archeologiebesluit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Archeologiebesluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArcheologiebesluit() throws Exception {
        // Initialize the database
        archeologiebesluitRepository.saveAndFlush(archeologiebesluit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the archeologiebesluit
        restArcheologiebesluitMockMvc
            .perform(delete(ENTITY_API_URL_ID, archeologiebesluit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return archeologiebesluitRepository.count();
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

    protected Archeologiebesluit getPersistedArcheologiebesluit(Archeologiebesluit archeologiebesluit) {
        return archeologiebesluitRepository.findById(archeologiebesluit.getId()).orElseThrow();
    }

    protected void assertPersistedArcheologiebesluitToMatchAllProperties(Archeologiebesluit expectedArcheologiebesluit) {
        assertArcheologiebesluitAllPropertiesEquals(expectedArcheologiebesluit, getPersistedArcheologiebesluit(expectedArcheologiebesluit));
    }

    protected void assertPersistedArcheologiebesluitToMatchUpdatableProperties(Archeologiebesluit expectedArcheologiebesluit) {
        assertArcheologiebesluitAllUpdatablePropertiesEquals(
            expectedArcheologiebesluit,
            getPersistedArcheologiebesluit(expectedArcheologiebesluit)
        );
    }
}
