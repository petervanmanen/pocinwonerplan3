package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NederlandsenationaliteitingeschrevenpersoonAsserts.*;
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
import nl.ritense.demo.domain.Nederlandsenationaliteitingeschrevenpersoon;
import nl.ritense.demo.repository.NederlandsenationaliteitingeschrevenpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NederlandsenationaliteitingeschrevenpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NederlandsenationaliteitingeschrevenpersoonResourceIT {

    private static final String DEFAULT_AANDUIDINGBIJZONDERNEDERLANDERSCHAP = "AAAAAAAAAA";
    private static final String UPDATED_AANDUIDINGBIJZONDERNEDERLANDERSCHAP = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITEIT = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_REDENVERKRIJGINGNEDERLANDSENATIONALITEIT = "AAAAAAAAAA";
    private static final String UPDATED_REDENVERKRIJGINGNEDERLANDSENATIONALITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_REDENVERLIESNEDERLANDSENATIONALITEIT = "AAAAAAAAAA";
    private static final String UPDATED_REDENVERLIESNEDERLANDSENATIONALITEIT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nederlandsenationaliteitingeschrevenpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NederlandsenationaliteitingeschrevenpersoonRepository nederlandsenationaliteitingeschrevenpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNederlandsenationaliteitingeschrevenpersoonMockMvc;

    private Nederlandsenationaliteitingeschrevenpersoon nederlandsenationaliteitingeschrevenpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nederlandsenationaliteitingeschrevenpersoon createEntity(EntityManager em) {
        Nederlandsenationaliteitingeschrevenpersoon nederlandsenationaliteitingeschrevenpersoon =
            new Nederlandsenationaliteitingeschrevenpersoon()
                .aanduidingbijzondernederlanderschap(DEFAULT_AANDUIDINGBIJZONDERNEDERLANDERSCHAP)
                .nationaliteit(DEFAULT_NATIONALITEIT)
                .redenverkrijgingnederlandsenationaliteit(DEFAULT_REDENVERKRIJGINGNEDERLANDSENATIONALITEIT)
                .redenverliesnederlandsenationaliteit(DEFAULT_REDENVERLIESNEDERLANDSENATIONALITEIT);
        return nederlandsenationaliteitingeschrevenpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nederlandsenationaliteitingeschrevenpersoon createUpdatedEntity(EntityManager em) {
        Nederlandsenationaliteitingeschrevenpersoon nederlandsenationaliteitingeschrevenpersoon =
            new Nederlandsenationaliteitingeschrevenpersoon()
                .aanduidingbijzondernederlanderschap(UPDATED_AANDUIDINGBIJZONDERNEDERLANDERSCHAP)
                .nationaliteit(UPDATED_NATIONALITEIT)
                .redenverkrijgingnederlandsenationaliteit(UPDATED_REDENVERKRIJGINGNEDERLANDSENATIONALITEIT)
                .redenverliesnederlandsenationaliteit(UPDATED_REDENVERLIESNEDERLANDSENATIONALITEIT);
        return nederlandsenationaliteitingeschrevenpersoon;
    }

    @BeforeEach
    public void initTest() {
        nederlandsenationaliteitingeschrevenpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createNederlandsenationaliteitingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Nederlandsenationaliteitingeschrevenpersoon
        var returnedNederlandsenationaliteitingeschrevenpersoon = om.readValue(
            restNederlandsenationaliteitingeschrevenpersoonMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(nederlandsenationaliteitingeschrevenpersoon))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Nederlandsenationaliteitingeschrevenpersoon.class
        );

        // Validate the Nederlandsenationaliteitingeschrevenpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNederlandsenationaliteitingeschrevenpersoonUpdatableFieldsEquals(
            returnedNederlandsenationaliteitingeschrevenpersoon,
            getPersistedNederlandsenationaliteitingeschrevenpersoon(returnedNederlandsenationaliteitingeschrevenpersoon)
        );
    }

    @Test
    @Transactional
    void createNederlandsenationaliteitingeschrevenpersoonWithExistingId() throws Exception {
        // Create the Nederlandsenationaliteitingeschrevenpersoon with an existing ID
        nederlandsenationaliteitingeschrevenpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNederlandsenationaliteitingeschrevenpersoonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nederlandsenationaliteitingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nederlandsenationaliteitingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNederlandsenationaliteitingeschrevenpersoons() throws Exception {
        // Initialize the database
        nederlandsenationaliteitingeschrevenpersoonRepository.saveAndFlush(nederlandsenationaliteitingeschrevenpersoon);

        // Get all the nederlandsenationaliteitingeschrevenpersoonList
        restNederlandsenationaliteitingeschrevenpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nederlandsenationaliteitingeschrevenpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanduidingbijzondernederlanderschap").value(hasItem(DEFAULT_AANDUIDINGBIJZONDERNEDERLANDERSCHAP)))
            .andExpect(jsonPath("$.[*].nationaliteit").value(hasItem(DEFAULT_NATIONALITEIT)))
            .andExpect(
                jsonPath("$.[*].redenverkrijgingnederlandsenationaliteit").value(hasItem(DEFAULT_REDENVERKRIJGINGNEDERLANDSENATIONALITEIT))
            )
            .andExpect(jsonPath("$.[*].redenverliesnederlandsenationaliteit").value(hasItem(DEFAULT_REDENVERLIESNEDERLANDSENATIONALITEIT)));
    }

    @Test
    @Transactional
    void getNederlandsenationaliteitingeschrevenpersoon() throws Exception {
        // Initialize the database
        nederlandsenationaliteitingeschrevenpersoonRepository.saveAndFlush(nederlandsenationaliteitingeschrevenpersoon);

        // Get the nederlandsenationaliteitingeschrevenpersoon
        restNederlandsenationaliteitingeschrevenpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, nederlandsenationaliteitingeschrevenpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nederlandsenationaliteitingeschrevenpersoon.getId().intValue()))
            .andExpect(jsonPath("$.aanduidingbijzondernederlanderschap").value(DEFAULT_AANDUIDINGBIJZONDERNEDERLANDERSCHAP))
            .andExpect(jsonPath("$.nationaliteit").value(DEFAULT_NATIONALITEIT))
            .andExpect(jsonPath("$.redenverkrijgingnederlandsenationaliteit").value(DEFAULT_REDENVERKRIJGINGNEDERLANDSENATIONALITEIT))
            .andExpect(jsonPath("$.redenverliesnederlandsenationaliteit").value(DEFAULT_REDENVERLIESNEDERLANDSENATIONALITEIT));
    }

    @Test
    @Transactional
    void getNonExistingNederlandsenationaliteitingeschrevenpersoon() throws Exception {
        // Get the nederlandsenationaliteitingeschrevenpersoon
        restNederlandsenationaliteitingeschrevenpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNederlandsenationaliteitingeschrevenpersoon() throws Exception {
        // Initialize the database
        nederlandsenationaliteitingeschrevenpersoonRepository.saveAndFlush(nederlandsenationaliteitingeschrevenpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nederlandsenationaliteitingeschrevenpersoon
        Nederlandsenationaliteitingeschrevenpersoon updatedNederlandsenationaliteitingeschrevenpersoon =
            nederlandsenationaliteitingeschrevenpersoonRepository
                .findById(nederlandsenationaliteitingeschrevenpersoon.getId())
                .orElseThrow();
        // Disconnect from session so that the updates on updatedNederlandsenationaliteitingeschrevenpersoon are not directly saved in db
        em.detach(updatedNederlandsenationaliteitingeschrevenpersoon);
        updatedNederlandsenationaliteitingeschrevenpersoon
            .aanduidingbijzondernederlanderschap(UPDATED_AANDUIDINGBIJZONDERNEDERLANDERSCHAP)
            .nationaliteit(UPDATED_NATIONALITEIT)
            .redenverkrijgingnederlandsenationaliteit(UPDATED_REDENVERKRIJGINGNEDERLANDSENATIONALITEIT)
            .redenverliesnederlandsenationaliteit(UPDATED_REDENVERLIESNEDERLANDSENATIONALITEIT);

        restNederlandsenationaliteitingeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNederlandsenationaliteitingeschrevenpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNederlandsenationaliteitingeschrevenpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Nederlandsenationaliteitingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNederlandsenationaliteitingeschrevenpersoonToMatchAllProperties(updatedNederlandsenationaliteitingeschrevenpersoon);
    }

    @Test
    @Transactional
    void putNonExistingNederlandsenationaliteitingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nederlandsenationaliteitingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNederlandsenationaliteitingeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nederlandsenationaliteitingeschrevenpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nederlandsenationaliteitingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nederlandsenationaliteitingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNederlandsenationaliteitingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nederlandsenationaliteitingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNederlandsenationaliteitingeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nederlandsenationaliteitingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nederlandsenationaliteitingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNederlandsenationaliteitingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nederlandsenationaliteitingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNederlandsenationaliteitingeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nederlandsenationaliteitingeschrevenpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nederlandsenationaliteitingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNederlandsenationaliteitingeschrevenpersoonWithPatch() throws Exception {
        // Initialize the database
        nederlandsenationaliteitingeschrevenpersoonRepository.saveAndFlush(nederlandsenationaliteitingeschrevenpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nederlandsenationaliteitingeschrevenpersoon using partial update
        Nederlandsenationaliteitingeschrevenpersoon partialUpdatedNederlandsenationaliteitingeschrevenpersoon =
            new Nederlandsenationaliteitingeschrevenpersoon();
        partialUpdatedNederlandsenationaliteitingeschrevenpersoon.setId(nederlandsenationaliteitingeschrevenpersoon.getId());

        partialUpdatedNederlandsenationaliteitingeschrevenpersoon
            .aanduidingbijzondernederlanderschap(UPDATED_AANDUIDINGBIJZONDERNEDERLANDERSCHAP)
            .redenverkrijgingnederlandsenationaliteit(UPDATED_REDENVERKRIJGINGNEDERLANDSENATIONALITEIT);

        restNederlandsenationaliteitingeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNederlandsenationaliteitingeschrevenpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNederlandsenationaliteitingeschrevenpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Nederlandsenationaliteitingeschrevenpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNederlandsenationaliteitingeschrevenpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(
                partialUpdatedNederlandsenationaliteitingeschrevenpersoon,
                nederlandsenationaliteitingeschrevenpersoon
            ),
            getPersistedNederlandsenationaliteitingeschrevenpersoon(nederlandsenationaliteitingeschrevenpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateNederlandsenationaliteitingeschrevenpersoonWithPatch() throws Exception {
        // Initialize the database
        nederlandsenationaliteitingeschrevenpersoonRepository.saveAndFlush(nederlandsenationaliteitingeschrevenpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nederlandsenationaliteitingeschrevenpersoon using partial update
        Nederlandsenationaliteitingeschrevenpersoon partialUpdatedNederlandsenationaliteitingeschrevenpersoon =
            new Nederlandsenationaliteitingeschrevenpersoon();
        partialUpdatedNederlandsenationaliteitingeschrevenpersoon.setId(nederlandsenationaliteitingeschrevenpersoon.getId());

        partialUpdatedNederlandsenationaliteitingeschrevenpersoon
            .aanduidingbijzondernederlanderschap(UPDATED_AANDUIDINGBIJZONDERNEDERLANDERSCHAP)
            .nationaliteit(UPDATED_NATIONALITEIT)
            .redenverkrijgingnederlandsenationaliteit(UPDATED_REDENVERKRIJGINGNEDERLANDSENATIONALITEIT)
            .redenverliesnederlandsenationaliteit(UPDATED_REDENVERLIESNEDERLANDSENATIONALITEIT);

        restNederlandsenationaliteitingeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNederlandsenationaliteitingeschrevenpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNederlandsenationaliteitingeschrevenpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Nederlandsenationaliteitingeschrevenpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNederlandsenationaliteitingeschrevenpersoonUpdatableFieldsEquals(
            partialUpdatedNederlandsenationaliteitingeschrevenpersoon,
            getPersistedNederlandsenationaliteitingeschrevenpersoon(partialUpdatedNederlandsenationaliteitingeschrevenpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNederlandsenationaliteitingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nederlandsenationaliteitingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNederlandsenationaliteitingeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nederlandsenationaliteitingeschrevenpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nederlandsenationaliteitingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nederlandsenationaliteitingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNederlandsenationaliteitingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nederlandsenationaliteitingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNederlandsenationaliteitingeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nederlandsenationaliteitingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nederlandsenationaliteitingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNederlandsenationaliteitingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nederlandsenationaliteitingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNederlandsenationaliteitingeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nederlandsenationaliteitingeschrevenpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nederlandsenationaliteitingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNederlandsenationaliteitingeschrevenpersoon() throws Exception {
        // Initialize the database
        nederlandsenationaliteitingeschrevenpersoonRepository.saveAndFlush(nederlandsenationaliteitingeschrevenpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the nederlandsenationaliteitingeschrevenpersoon
        restNederlandsenationaliteitingeschrevenpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, nederlandsenationaliteitingeschrevenpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return nederlandsenationaliteitingeschrevenpersoonRepository.count();
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

    protected Nederlandsenationaliteitingeschrevenpersoon getPersistedNederlandsenationaliteitingeschrevenpersoon(
        Nederlandsenationaliteitingeschrevenpersoon nederlandsenationaliteitingeschrevenpersoon
    ) {
        return nederlandsenationaliteitingeschrevenpersoonRepository
            .findById(nederlandsenationaliteitingeschrevenpersoon.getId())
            .orElseThrow();
    }

    protected void assertPersistedNederlandsenationaliteitingeschrevenpersoonToMatchAllProperties(
        Nederlandsenationaliteitingeschrevenpersoon expectedNederlandsenationaliteitingeschrevenpersoon
    ) {
        assertNederlandsenationaliteitingeschrevenpersoonAllPropertiesEquals(
            expectedNederlandsenationaliteitingeschrevenpersoon,
            getPersistedNederlandsenationaliteitingeschrevenpersoon(expectedNederlandsenationaliteitingeschrevenpersoon)
        );
    }

    protected void assertPersistedNederlandsenationaliteitingeschrevenpersoonToMatchUpdatableProperties(
        Nederlandsenationaliteitingeschrevenpersoon expectedNederlandsenationaliteitingeschrevenpersoon
    ) {
        assertNederlandsenationaliteitingeschrevenpersoonAllUpdatablePropertiesEquals(
            expectedNederlandsenationaliteitingeschrevenpersoon,
            getPersistedNederlandsenationaliteitingeschrevenpersoon(expectedNederlandsenationaliteitingeschrevenpersoon)
        );
    }
}
