package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MeldingeigenbijdrageAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Meldingeigenbijdrage;
import nl.ritense.demo.repository.MeldingeigenbijdrageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MeldingeigenbijdrageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MeldingeigenbijdrageResourceIT {

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTOP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTOP = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/meldingeigenbijdrages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MeldingeigenbijdrageRepository meldingeigenbijdrageRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMeldingeigenbijdrageMockMvc;

    private Meldingeigenbijdrage meldingeigenbijdrage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meldingeigenbijdrage createEntity(EntityManager em) {
        Meldingeigenbijdrage meldingeigenbijdrage = new Meldingeigenbijdrage().datumstart(DEFAULT_DATUMSTART).datumstop(DEFAULT_DATUMSTOP);
        return meldingeigenbijdrage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meldingeigenbijdrage createUpdatedEntity(EntityManager em) {
        Meldingeigenbijdrage meldingeigenbijdrage = new Meldingeigenbijdrage().datumstart(UPDATED_DATUMSTART).datumstop(UPDATED_DATUMSTOP);
        return meldingeigenbijdrage;
    }

    @BeforeEach
    public void initTest() {
        meldingeigenbijdrage = createEntity(em);
    }

    @Test
    @Transactional
    void createMeldingeigenbijdrage() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Meldingeigenbijdrage
        var returnedMeldingeigenbijdrage = om.readValue(
            restMeldingeigenbijdrageMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(meldingeigenbijdrage)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Meldingeigenbijdrage.class
        );

        // Validate the Meldingeigenbijdrage in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMeldingeigenbijdrageUpdatableFieldsEquals(
            returnedMeldingeigenbijdrage,
            getPersistedMeldingeigenbijdrage(returnedMeldingeigenbijdrage)
        );
    }

    @Test
    @Transactional
    void createMeldingeigenbijdrageWithExistingId() throws Exception {
        // Create the Meldingeigenbijdrage with an existing ID
        meldingeigenbijdrage.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeldingeigenbijdrageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(meldingeigenbijdrage)))
            .andExpect(status().isBadRequest());

        // Validate the Meldingeigenbijdrage in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMeldingeigenbijdrages() throws Exception {
        // Initialize the database
        meldingeigenbijdrageRepository.saveAndFlush(meldingeigenbijdrage);

        // Get all the meldingeigenbijdrageList
        restMeldingeigenbijdrageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meldingeigenbijdrage.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].datumstop").value(hasItem(DEFAULT_DATUMSTOP.toString())));
    }

    @Test
    @Transactional
    void getMeldingeigenbijdrage() throws Exception {
        // Initialize the database
        meldingeigenbijdrageRepository.saveAndFlush(meldingeigenbijdrage);

        // Get the meldingeigenbijdrage
        restMeldingeigenbijdrageMockMvc
            .perform(get(ENTITY_API_URL_ID, meldingeigenbijdrage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(meldingeigenbijdrage.getId().intValue()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.datumstop").value(DEFAULT_DATUMSTOP.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMeldingeigenbijdrage() throws Exception {
        // Get the meldingeigenbijdrage
        restMeldingeigenbijdrageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMeldingeigenbijdrage() throws Exception {
        // Initialize the database
        meldingeigenbijdrageRepository.saveAndFlush(meldingeigenbijdrage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the meldingeigenbijdrage
        Meldingeigenbijdrage updatedMeldingeigenbijdrage = meldingeigenbijdrageRepository
            .findById(meldingeigenbijdrage.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedMeldingeigenbijdrage are not directly saved in db
        em.detach(updatedMeldingeigenbijdrage);
        updatedMeldingeigenbijdrage.datumstart(UPDATED_DATUMSTART).datumstop(UPDATED_DATUMSTOP);

        restMeldingeigenbijdrageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMeldingeigenbijdrage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMeldingeigenbijdrage))
            )
            .andExpect(status().isOk());

        // Validate the Meldingeigenbijdrage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMeldingeigenbijdrageToMatchAllProperties(updatedMeldingeigenbijdrage);
    }

    @Test
    @Transactional
    void putNonExistingMeldingeigenbijdrage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meldingeigenbijdrage.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeldingeigenbijdrageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, meldingeigenbijdrage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(meldingeigenbijdrage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meldingeigenbijdrage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMeldingeigenbijdrage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meldingeigenbijdrage.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeldingeigenbijdrageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(meldingeigenbijdrage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meldingeigenbijdrage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMeldingeigenbijdrage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meldingeigenbijdrage.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeldingeigenbijdrageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(meldingeigenbijdrage)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Meldingeigenbijdrage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMeldingeigenbijdrageWithPatch() throws Exception {
        // Initialize the database
        meldingeigenbijdrageRepository.saveAndFlush(meldingeigenbijdrage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the meldingeigenbijdrage using partial update
        Meldingeigenbijdrage partialUpdatedMeldingeigenbijdrage = new Meldingeigenbijdrage();
        partialUpdatedMeldingeigenbijdrage.setId(meldingeigenbijdrage.getId());

        partialUpdatedMeldingeigenbijdrage.datumstart(UPDATED_DATUMSTART);

        restMeldingeigenbijdrageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMeldingeigenbijdrage.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMeldingeigenbijdrage))
            )
            .andExpect(status().isOk());

        // Validate the Meldingeigenbijdrage in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMeldingeigenbijdrageUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMeldingeigenbijdrage, meldingeigenbijdrage),
            getPersistedMeldingeigenbijdrage(meldingeigenbijdrage)
        );
    }

    @Test
    @Transactional
    void fullUpdateMeldingeigenbijdrageWithPatch() throws Exception {
        // Initialize the database
        meldingeigenbijdrageRepository.saveAndFlush(meldingeigenbijdrage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the meldingeigenbijdrage using partial update
        Meldingeigenbijdrage partialUpdatedMeldingeigenbijdrage = new Meldingeigenbijdrage();
        partialUpdatedMeldingeigenbijdrage.setId(meldingeigenbijdrage.getId());

        partialUpdatedMeldingeigenbijdrage.datumstart(UPDATED_DATUMSTART).datumstop(UPDATED_DATUMSTOP);

        restMeldingeigenbijdrageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMeldingeigenbijdrage.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMeldingeigenbijdrage))
            )
            .andExpect(status().isOk());

        // Validate the Meldingeigenbijdrage in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMeldingeigenbijdrageUpdatableFieldsEquals(
            partialUpdatedMeldingeigenbijdrage,
            getPersistedMeldingeigenbijdrage(partialUpdatedMeldingeigenbijdrage)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMeldingeigenbijdrage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meldingeigenbijdrage.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeldingeigenbijdrageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, meldingeigenbijdrage.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(meldingeigenbijdrage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meldingeigenbijdrage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMeldingeigenbijdrage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meldingeigenbijdrage.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeldingeigenbijdrageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(meldingeigenbijdrage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meldingeigenbijdrage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMeldingeigenbijdrage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        meldingeigenbijdrage.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeldingeigenbijdrageMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(meldingeigenbijdrage)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Meldingeigenbijdrage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMeldingeigenbijdrage() throws Exception {
        // Initialize the database
        meldingeigenbijdrageRepository.saveAndFlush(meldingeigenbijdrage);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the meldingeigenbijdrage
        restMeldingeigenbijdrageMockMvc
            .perform(delete(ENTITY_API_URL_ID, meldingeigenbijdrage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return meldingeigenbijdrageRepository.count();
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

    protected Meldingeigenbijdrage getPersistedMeldingeigenbijdrage(Meldingeigenbijdrage meldingeigenbijdrage) {
        return meldingeigenbijdrageRepository.findById(meldingeigenbijdrage.getId()).orElseThrow();
    }

    protected void assertPersistedMeldingeigenbijdrageToMatchAllProperties(Meldingeigenbijdrage expectedMeldingeigenbijdrage) {
        assertMeldingeigenbijdrageAllPropertiesEquals(
            expectedMeldingeigenbijdrage,
            getPersistedMeldingeigenbijdrage(expectedMeldingeigenbijdrage)
        );
    }

    protected void assertPersistedMeldingeigenbijdrageToMatchUpdatableProperties(Meldingeigenbijdrage expectedMeldingeigenbijdrage) {
        assertMeldingeigenbijdrageAllUpdatablePropertiesEquals(
            expectedMeldingeigenbijdrage,
            getPersistedMeldingeigenbijdrage(expectedMeldingeigenbijdrage)
        );
    }
}
