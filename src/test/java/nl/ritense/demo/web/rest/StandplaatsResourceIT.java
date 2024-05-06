package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.StandplaatsAsserts.*;
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
import nl.ritense.demo.domain.Standplaats;
import nl.ritense.demo.repository.StandplaatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StandplaatsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StandplaatsResourceIT {

    private static final String DEFAULT_ADRES = "AAAAAAAAAA";
    private static final String UPDATED_ADRES = "BBBBBBBBBB";

    private static final String DEFAULT_BESCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_BESCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_NAAMINSTELLING = "AAAAAAAAAA";
    private static final String UPDATED_NAAMINSTELLING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/standplaats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StandplaatsRepository standplaatsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStandplaatsMockMvc;

    private Standplaats standplaats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Standplaats createEntity(EntityManager em) {
        Standplaats standplaats = new Standplaats()
            .adres(DEFAULT_ADRES)
            .beschrijving(DEFAULT_BESCHRIJVING)
            .naaminstelling(DEFAULT_NAAMINSTELLING);
        return standplaats;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Standplaats createUpdatedEntity(EntityManager em) {
        Standplaats standplaats = new Standplaats()
            .adres(UPDATED_ADRES)
            .beschrijving(UPDATED_BESCHRIJVING)
            .naaminstelling(UPDATED_NAAMINSTELLING);
        return standplaats;
    }

    @BeforeEach
    public void initTest() {
        standplaats = createEntity(em);
    }

    @Test
    @Transactional
    void createStandplaats() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Standplaats
        var returnedStandplaats = om.readValue(
            restStandplaatsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standplaats)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Standplaats.class
        );

        // Validate the Standplaats in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStandplaatsUpdatableFieldsEquals(returnedStandplaats, getPersistedStandplaats(returnedStandplaats));
    }

    @Test
    @Transactional
    void createStandplaatsWithExistingId() throws Exception {
        // Create the Standplaats with an existing ID
        standplaats.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStandplaatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standplaats)))
            .andExpect(status().isBadRequest());

        // Validate the Standplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStandplaats() throws Exception {
        // Initialize the database
        standplaatsRepository.saveAndFlush(standplaats);

        // Get all the standplaatsList
        restStandplaatsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(standplaats.getId().intValue())))
            .andExpect(jsonPath("$.[*].adres").value(hasItem(DEFAULT_ADRES)))
            .andExpect(jsonPath("$.[*].beschrijving").value(hasItem(DEFAULT_BESCHRIJVING)))
            .andExpect(jsonPath("$.[*].naaminstelling").value(hasItem(DEFAULT_NAAMINSTELLING)));
    }

    @Test
    @Transactional
    void getStandplaats() throws Exception {
        // Initialize the database
        standplaatsRepository.saveAndFlush(standplaats);

        // Get the standplaats
        restStandplaatsMockMvc
            .perform(get(ENTITY_API_URL_ID, standplaats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(standplaats.getId().intValue()))
            .andExpect(jsonPath("$.adres").value(DEFAULT_ADRES))
            .andExpect(jsonPath("$.beschrijving").value(DEFAULT_BESCHRIJVING))
            .andExpect(jsonPath("$.naaminstelling").value(DEFAULT_NAAMINSTELLING));
    }

    @Test
    @Transactional
    void getNonExistingStandplaats() throws Exception {
        // Get the standplaats
        restStandplaatsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStandplaats() throws Exception {
        // Initialize the database
        standplaatsRepository.saveAndFlush(standplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standplaats
        Standplaats updatedStandplaats = standplaatsRepository.findById(standplaats.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStandplaats are not directly saved in db
        em.detach(updatedStandplaats);
        updatedStandplaats.adres(UPDATED_ADRES).beschrijving(UPDATED_BESCHRIJVING).naaminstelling(UPDATED_NAAMINSTELLING);

        restStandplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStandplaats.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStandplaats))
            )
            .andExpect(status().isOk());

        // Validate the Standplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStandplaatsToMatchAllProperties(updatedStandplaats);
    }

    @Test
    @Transactional
    void putNonExistingStandplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standplaats.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStandplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, standplaats.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(standplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Standplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStandplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(standplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Standplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStandplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandplaatsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standplaats)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Standplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStandplaatsWithPatch() throws Exception {
        // Initialize the database
        standplaatsRepository.saveAndFlush(standplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standplaats using partial update
        Standplaats partialUpdatedStandplaats = new Standplaats();
        partialUpdatedStandplaats.setId(standplaats.getId());

        partialUpdatedStandplaats.beschrijving(UPDATED_BESCHRIJVING);

        restStandplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStandplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStandplaats))
            )
            .andExpect(status().isOk());

        // Validate the Standplaats in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStandplaatsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStandplaats, standplaats),
            getPersistedStandplaats(standplaats)
        );
    }

    @Test
    @Transactional
    void fullUpdateStandplaatsWithPatch() throws Exception {
        // Initialize the database
        standplaatsRepository.saveAndFlush(standplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standplaats using partial update
        Standplaats partialUpdatedStandplaats = new Standplaats();
        partialUpdatedStandplaats.setId(standplaats.getId());

        partialUpdatedStandplaats.adres(UPDATED_ADRES).beschrijving(UPDATED_BESCHRIJVING).naaminstelling(UPDATED_NAAMINSTELLING);

        restStandplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStandplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStandplaats))
            )
            .andExpect(status().isOk());

        // Validate the Standplaats in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStandplaatsUpdatableFieldsEquals(partialUpdatedStandplaats, getPersistedStandplaats(partialUpdatedStandplaats));
    }

    @Test
    @Transactional
    void patchNonExistingStandplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standplaats.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStandplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, standplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(standplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Standplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStandplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(standplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Standplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStandplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandplaatsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(standplaats)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Standplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStandplaats() throws Exception {
        // Initialize the database
        standplaatsRepository.saveAndFlush(standplaats);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the standplaats
        restStandplaatsMockMvc
            .perform(delete(ENTITY_API_URL_ID, standplaats.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return standplaatsRepository.count();
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

    protected Standplaats getPersistedStandplaats(Standplaats standplaats) {
        return standplaatsRepository.findById(standplaats.getId()).orElseThrow();
    }

    protected void assertPersistedStandplaatsToMatchAllProperties(Standplaats expectedStandplaats) {
        assertStandplaatsAllPropertiesEquals(expectedStandplaats, getPersistedStandplaats(expectedStandplaats));
    }

    protected void assertPersistedStandplaatsToMatchUpdatableProperties(Standplaats expectedStandplaats) {
        assertStandplaatsAllUpdatablePropertiesEquals(expectedStandplaats, getPersistedStandplaats(expectedStandplaats));
    }
}
