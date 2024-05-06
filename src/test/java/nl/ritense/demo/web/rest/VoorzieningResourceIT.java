package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VoorzieningAsserts.*;
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
import nl.ritense.demo.domain.Voorziening;
import nl.ritense.demo.repository.VoorzieningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VoorzieningResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class VoorzieningResourceIT {

    private static final String DEFAULT_AANTALBESCHIKBAAR = "AAAAAAAAAA";
    private static final String UPDATED_AANTALBESCHIKBAAR = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/voorzienings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VoorzieningRepository voorzieningRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoorzieningMockMvc;

    private Voorziening voorziening;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voorziening createEntity(EntityManager em) {
        Voorziening voorziening = new Voorziening()
            .aantalbeschikbaar(DEFAULT_AANTALBESCHIKBAAR)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return voorziening;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voorziening createUpdatedEntity(EntityManager em) {
        Voorziening voorziening = new Voorziening()
            .aantalbeschikbaar(UPDATED_AANTALBESCHIKBAAR)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return voorziening;
    }

    @BeforeEach
    public void initTest() {
        voorziening = createEntity(em);
    }

    @Test
    @Transactional
    void createVoorziening() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Voorziening
        var returnedVoorziening = om.readValue(
            restVoorzieningMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voorziening)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Voorziening.class
        );

        // Validate the Voorziening in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVoorzieningUpdatableFieldsEquals(returnedVoorziening, getPersistedVoorziening(returnedVoorziening));
    }

    @Test
    @Transactional
    void createVoorzieningWithExistingId() throws Exception {
        // Create the Voorziening with an existing ID
        voorziening.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoorzieningMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voorziening)))
            .andExpect(status().isBadRequest());

        // Validate the Voorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVoorzienings() throws Exception {
        // Initialize the database
        voorzieningRepository.saveAndFlush(voorziening);

        // Get all the voorzieningList
        restVoorzieningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voorziening.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantalbeschikbaar").value(hasItem(DEFAULT_AANTALBESCHIKBAAR)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getVoorziening() throws Exception {
        // Initialize the database
        voorzieningRepository.saveAndFlush(voorziening);

        // Get the voorziening
        restVoorzieningMockMvc
            .perform(get(ENTITY_API_URL_ID, voorziening.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voorziening.getId().intValue()))
            .andExpect(jsonPath("$.aantalbeschikbaar").value(DEFAULT_AANTALBESCHIKBAAR))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingVoorziening() throws Exception {
        // Get the voorziening
        restVoorzieningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVoorziening() throws Exception {
        // Initialize the database
        voorzieningRepository.saveAndFlush(voorziening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voorziening
        Voorziening updatedVoorziening = voorzieningRepository.findById(voorziening.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVoorziening are not directly saved in db
        em.detach(updatedVoorziening);
        updatedVoorziening.aantalbeschikbaar(UPDATED_AANTALBESCHIKBAAR).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restVoorzieningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVoorziening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVoorziening))
            )
            .andExpect(status().isOk());

        // Validate the Voorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVoorzieningToMatchAllProperties(updatedVoorziening);
    }

    @Test
    @Transactional
    void putNonExistingVoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voorziening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoorzieningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voorziening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(voorziening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voorziening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoorzieningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(voorziening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voorziening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoorzieningMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voorziening)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoorzieningWithPatch() throws Exception {
        // Initialize the database
        voorzieningRepository.saveAndFlush(voorziening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voorziening using partial update
        Voorziening partialUpdatedVoorziening = new Voorziening();
        partialUpdatedVoorziening.setId(voorziening.getId());

        partialUpdatedVoorziening.aantalbeschikbaar(UPDATED_AANTALBESCHIKBAAR).omschrijving(UPDATED_OMSCHRIJVING);

        restVoorzieningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoorziening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVoorziening))
            )
            .andExpect(status().isOk());

        // Validate the Voorziening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVoorzieningUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVoorziening, voorziening),
            getPersistedVoorziening(voorziening)
        );
    }

    @Test
    @Transactional
    void fullUpdateVoorzieningWithPatch() throws Exception {
        // Initialize the database
        voorzieningRepository.saveAndFlush(voorziening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voorziening using partial update
        Voorziening partialUpdatedVoorziening = new Voorziening();
        partialUpdatedVoorziening.setId(voorziening.getId());

        partialUpdatedVoorziening.aantalbeschikbaar(UPDATED_AANTALBESCHIKBAAR).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restVoorzieningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoorziening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVoorziening))
            )
            .andExpect(status().isOk());

        // Validate the Voorziening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVoorzieningUpdatableFieldsEquals(partialUpdatedVoorziening, getPersistedVoorziening(partialUpdatedVoorziening));
    }

    @Test
    @Transactional
    void patchNonExistingVoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voorziening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoorzieningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voorziening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(voorziening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voorziening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoorzieningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(voorziening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoorziening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voorziening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoorzieningMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(voorziening)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voorziening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoorziening() throws Exception {
        // Initialize the database
        voorzieningRepository.saveAndFlush(voorziening);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the voorziening
        restVoorzieningMockMvc
            .perform(delete(ENTITY_API_URL_ID, voorziening.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return voorzieningRepository.count();
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

    protected Voorziening getPersistedVoorziening(Voorziening voorziening) {
        return voorzieningRepository.findById(voorziening.getId()).orElseThrow();
    }

    protected void assertPersistedVoorzieningToMatchAllProperties(Voorziening expectedVoorziening) {
        assertVoorzieningAllPropertiesEquals(expectedVoorziening, getPersistedVoorziening(expectedVoorziening));
    }

    protected void assertPersistedVoorzieningToMatchUpdatableProperties(Voorziening expectedVoorziening) {
        assertVoorzieningAllUpdatablePropertiesEquals(expectedVoorziening, getPersistedVoorziening(expectedVoorziening));
    }
}
