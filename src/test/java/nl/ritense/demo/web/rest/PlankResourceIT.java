package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PlankAsserts.*;
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
import nl.ritense.demo.domain.Plank;
import nl.ritense.demo.repository.PlankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PlankResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlankResourceIT {

    private static final String DEFAULT_PLANKNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_PLANKNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/planks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PlankRepository plankRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlankMockMvc;

    private Plank plank;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plank createEntity(EntityManager em) {
        Plank plank = new Plank().planknummer(DEFAULT_PLANKNUMMER);
        return plank;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plank createUpdatedEntity(EntityManager em) {
        Plank plank = new Plank().planknummer(UPDATED_PLANKNUMMER);
        return plank;
    }

    @BeforeEach
    public void initTest() {
        plank = createEntity(em);
    }

    @Test
    @Transactional
    void createPlank() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Plank
        var returnedPlank = om.readValue(
            restPlankMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plank)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Plank.class
        );

        // Validate the Plank in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPlankUpdatableFieldsEquals(returnedPlank, getPersistedPlank(returnedPlank));
    }

    @Test
    @Transactional
    void createPlankWithExistingId() throws Exception {
        // Create the Plank with an existing ID
        plank.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plank)))
            .andExpect(status().isBadRequest());

        // Validate the Plank in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlanks() throws Exception {
        // Initialize the database
        plankRepository.saveAndFlush(plank);

        // Get all the plankList
        restPlankMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plank.getId().intValue())))
            .andExpect(jsonPath("$.[*].planknummer").value(hasItem(DEFAULT_PLANKNUMMER)));
    }

    @Test
    @Transactional
    void getPlank() throws Exception {
        // Initialize the database
        plankRepository.saveAndFlush(plank);

        // Get the plank
        restPlankMockMvc
            .perform(get(ENTITY_API_URL_ID, plank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plank.getId().intValue()))
            .andExpect(jsonPath("$.planknummer").value(DEFAULT_PLANKNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingPlank() throws Exception {
        // Get the plank
        restPlankMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlank() throws Exception {
        // Initialize the database
        plankRepository.saveAndFlush(plank);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the plank
        Plank updatedPlank = plankRepository.findById(plank.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPlank are not directly saved in db
        em.detach(updatedPlank);
        updatedPlank.planknummer(UPDATED_PLANKNUMMER);

        restPlankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlank.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPlank))
            )
            .andExpect(status().isOk());

        // Validate the Plank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPlankToMatchAllProperties(updatedPlank);
    }

    @Test
    @Transactional
    void putNonExistingPlank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plank.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlankMockMvc
            .perform(put(ENTITY_API_URL_ID, plank.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plank)))
            .andExpect(status().isBadRequest());

        // Validate the Plank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plank.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(plank))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plank.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlankMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plank)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlankWithPatch() throws Exception {
        // Initialize the database
        plankRepository.saveAndFlush(plank);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the plank using partial update
        Plank partialUpdatedPlank = new Plank();
        partialUpdatedPlank.setId(plank.getId());

        restPlankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlank.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlank))
            )
            .andExpect(status().isOk());

        // Validate the Plank in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlankUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPlank, plank), getPersistedPlank(plank));
    }

    @Test
    @Transactional
    void fullUpdatePlankWithPatch() throws Exception {
        // Initialize the database
        plankRepository.saveAndFlush(plank);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the plank using partial update
        Plank partialUpdatedPlank = new Plank();
        partialUpdatedPlank.setId(plank.getId());

        partialUpdatedPlank.planknummer(UPDATED_PLANKNUMMER);

        restPlankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlank.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlank))
            )
            .andExpect(status().isOk());

        // Validate the Plank in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlankUpdatableFieldsEquals(partialUpdatedPlank, getPersistedPlank(partialUpdatedPlank));
    }

    @Test
    @Transactional
    void patchNonExistingPlank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plank.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plank.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(plank))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plank.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(plank))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plank.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlankMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(plank)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlank() throws Exception {
        // Initialize the database
        plankRepository.saveAndFlush(plank);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the plank
        restPlankMockMvc
            .perform(delete(ENTITY_API_URL_ID, plank.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return plankRepository.count();
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

    protected Plank getPersistedPlank(Plank plank) {
        return plankRepository.findById(plank.getId()).orElseThrow();
    }

    protected void assertPersistedPlankToMatchAllProperties(Plank expectedPlank) {
        assertPlankAllPropertiesEquals(expectedPlank, getPersistedPlank(expectedPlank));
    }

    protected void assertPersistedPlankToMatchUpdatableProperties(Plank expectedPlank) {
        assertPlankAllUpdatablePropertiesEquals(expectedPlank, getPersistedPlank(expectedPlank));
    }
}
