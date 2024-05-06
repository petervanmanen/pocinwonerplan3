package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BoringAsserts.*;
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
import nl.ritense.demo.domain.Boring;
import nl.ritense.demo.repository.BoringRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BoringResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BoringResourceIT {

    private static final String ENTITY_API_URL = "/api/borings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BoringRepository boringRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBoringMockMvc;

    private Boring boring;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Boring createEntity(EntityManager em) {
        Boring boring = new Boring();
        return boring;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Boring createUpdatedEntity(EntityManager em) {
        Boring boring = new Boring();
        return boring;
    }

    @BeforeEach
    public void initTest() {
        boring = createEntity(em);
    }

    @Test
    @Transactional
    void createBoring() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Boring
        var returnedBoring = om.readValue(
            restBoringMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boring)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Boring.class
        );

        // Validate the Boring in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBoringUpdatableFieldsEquals(returnedBoring, getPersistedBoring(returnedBoring));
    }

    @Test
    @Transactional
    void createBoringWithExistingId() throws Exception {
        // Create the Boring with an existing ID
        boring.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoringMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boring)))
            .andExpect(status().isBadRequest());

        // Validate the Boring in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBorings() throws Exception {
        // Initialize the database
        boringRepository.saveAndFlush(boring);

        // Get all the boringList
        restBoringMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boring.getId().intValue())));
    }

    @Test
    @Transactional
    void getBoring() throws Exception {
        // Initialize the database
        boringRepository.saveAndFlush(boring);

        // Get the boring
        restBoringMockMvc
            .perform(get(ENTITY_API_URL_ID, boring.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(boring.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBoring() throws Exception {
        // Get the boring
        restBoringMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBoring() throws Exception {
        // Initialize the database
        boringRepository.saveAndFlush(boring);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the boring
        Boring updatedBoring = boringRepository.findById(boring.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBoring are not directly saved in db
        em.detach(updatedBoring);

        restBoringMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBoring.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBoring))
            )
            .andExpect(status().isOk());

        // Validate the Boring in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBoringToMatchAllProperties(updatedBoring);
    }

    @Test
    @Transactional
    void putNonExistingBoring() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boring.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoringMockMvc
            .perform(put(ENTITY_API_URL_ID, boring.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boring)))
            .andExpect(status().isBadRequest());

        // Validate the Boring in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBoring() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boring.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoringMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(boring))
            )
            .andExpect(status().isBadRequest());

        // Validate the Boring in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBoring() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boring.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoringMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boring)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Boring in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBoringWithPatch() throws Exception {
        // Initialize the database
        boringRepository.saveAndFlush(boring);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the boring using partial update
        Boring partialUpdatedBoring = new Boring();
        partialUpdatedBoring.setId(boring.getId());

        restBoringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBoring.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBoring))
            )
            .andExpect(status().isOk());

        // Validate the Boring in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBoringUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBoring, boring), getPersistedBoring(boring));
    }

    @Test
    @Transactional
    void fullUpdateBoringWithPatch() throws Exception {
        // Initialize the database
        boringRepository.saveAndFlush(boring);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the boring using partial update
        Boring partialUpdatedBoring = new Boring();
        partialUpdatedBoring.setId(boring.getId());

        restBoringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBoring.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBoring))
            )
            .andExpect(status().isOk());

        // Validate the Boring in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBoringUpdatableFieldsEquals(partialUpdatedBoring, getPersistedBoring(partialUpdatedBoring));
    }

    @Test
    @Transactional
    void patchNonExistingBoring() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boring.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, boring.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(boring))
            )
            .andExpect(status().isBadRequest());

        // Validate the Boring in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBoring() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boring.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(boring))
            )
            .andExpect(status().isBadRequest());

        // Validate the Boring in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBoring() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boring.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoringMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(boring)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Boring in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBoring() throws Exception {
        // Initialize the database
        boringRepository.saveAndFlush(boring);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the boring
        restBoringMockMvc
            .perform(delete(ENTITY_API_URL_ID, boring.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return boringRepository.count();
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

    protected Boring getPersistedBoring(Boring boring) {
        return boringRepository.findById(boring.getId()).orElseThrow();
    }

    protected void assertPersistedBoringToMatchAllProperties(Boring expectedBoring) {
        assertBoringAllPropertiesEquals(expectedBoring, getPersistedBoring(expectedBoring));
    }

    protected void assertPersistedBoringToMatchUpdatableProperties(Boring expectedBoring) {
        assertBoringAllUpdatablePropertiesEquals(expectedBoring, getPersistedBoring(expectedBoring));
    }
}
