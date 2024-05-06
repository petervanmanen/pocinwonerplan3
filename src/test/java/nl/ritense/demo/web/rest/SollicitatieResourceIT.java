package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SollicitatieAsserts.*;
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
import nl.ritense.demo.domain.Sollicitatie;
import nl.ritense.demo.repository.SollicitatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SollicitatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SollicitatieResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/sollicitaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SollicitatieRepository sollicitatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSollicitatieMockMvc;

    private Sollicitatie sollicitatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sollicitatie createEntity(EntityManager em) {
        Sollicitatie sollicitatie = new Sollicitatie().datum(DEFAULT_DATUM);
        return sollicitatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sollicitatie createUpdatedEntity(EntityManager em) {
        Sollicitatie sollicitatie = new Sollicitatie().datum(UPDATED_DATUM);
        return sollicitatie;
    }

    @BeforeEach
    public void initTest() {
        sollicitatie = createEntity(em);
    }

    @Test
    @Transactional
    void createSollicitatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sollicitatie
        var returnedSollicitatie = om.readValue(
            restSollicitatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sollicitatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Sollicitatie.class
        );

        // Validate the Sollicitatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSollicitatieUpdatableFieldsEquals(returnedSollicitatie, getPersistedSollicitatie(returnedSollicitatie));
    }

    @Test
    @Transactional
    void createSollicitatieWithExistingId() throws Exception {
        // Create the Sollicitatie with an existing ID
        sollicitatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSollicitatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sollicitatie)))
            .andExpect(status().isBadRequest());

        // Validate the Sollicitatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSollicitaties() throws Exception {
        // Initialize the database
        sollicitatieRepository.saveAndFlush(sollicitatie);

        // Get all the sollicitatieList
        restSollicitatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sollicitatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())));
    }

    @Test
    @Transactional
    void getSollicitatie() throws Exception {
        // Initialize the database
        sollicitatieRepository.saveAndFlush(sollicitatie);

        // Get the sollicitatie
        restSollicitatieMockMvc
            .perform(get(ENTITY_API_URL_ID, sollicitatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sollicitatie.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSollicitatie() throws Exception {
        // Get the sollicitatie
        restSollicitatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSollicitatie() throws Exception {
        // Initialize the database
        sollicitatieRepository.saveAndFlush(sollicitatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sollicitatie
        Sollicitatie updatedSollicitatie = sollicitatieRepository.findById(sollicitatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSollicitatie are not directly saved in db
        em.detach(updatedSollicitatie);
        updatedSollicitatie.datum(UPDATED_DATUM);

        restSollicitatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSollicitatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSollicitatie))
            )
            .andExpect(status().isOk());

        // Validate the Sollicitatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSollicitatieToMatchAllProperties(updatedSollicitatie);
    }

    @Test
    @Transactional
    void putNonExistingSollicitatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSollicitatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sollicitatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sollicitatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sollicitatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSollicitatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSollicitatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sollicitatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sollicitatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSollicitatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSollicitatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sollicitatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sollicitatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSollicitatieWithPatch() throws Exception {
        // Initialize the database
        sollicitatieRepository.saveAndFlush(sollicitatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sollicitatie using partial update
        Sollicitatie partialUpdatedSollicitatie = new Sollicitatie();
        partialUpdatedSollicitatie.setId(sollicitatie.getId());

        partialUpdatedSollicitatie.datum(UPDATED_DATUM);

        restSollicitatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSollicitatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSollicitatie))
            )
            .andExpect(status().isOk());

        // Validate the Sollicitatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSollicitatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSollicitatie, sollicitatie),
            getPersistedSollicitatie(sollicitatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateSollicitatieWithPatch() throws Exception {
        // Initialize the database
        sollicitatieRepository.saveAndFlush(sollicitatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sollicitatie using partial update
        Sollicitatie partialUpdatedSollicitatie = new Sollicitatie();
        partialUpdatedSollicitatie.setId(sollicitatie.getId());

        partialUpdatedSollicitatie.datum(UPDATED_DATUM);

        restSollicitatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSollicitatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSollicitatie))
            )
            .andExpect(status().isOk());

        // Validate the Sollicitatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSollicitatieUpdatableFieldsEquals(partialUpdatedSollicitatie, getPersistedSollicitatie(partialUpdatedSollicitatie));
    }

    @Test
    @Transactional
    void patchNonExistingSollicitatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSollicitatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sollicitatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sollicitatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sollicitatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSollicitatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSollicitatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sollicitatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sollicitatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSollicitatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSollicitatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sollicitatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sollicitatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSollicitatie() throws Exception {
        // Initialize the database
        sollicitatieRepository.saveAndFlush(sollicitatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sollicitatie
        restSollicitatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, sollicitatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sollicitatieRepository.count();
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

    protected Sollicitatie getPersistedSollicitatie(Sollicitatie sollicitatie) {
        return sollicitatieRepository.findById(sollicitatie.getId()).orElseThrow();
    }

    protected void assertPersistedSollicitatieToMatchAllProperties(Sollicitatie expectedSollicitatie) {
        assertSollicitatieAllPropertiesEquals(expectedSollicitatie, getPersistedSollicitatie(expectedSollicitatie));
    }

    protected void assertPersistedSollicitatieToMatchUpdatableProperties(Sollicitatie expectedSollicitatie) {
        assertSollicitatieAllUpdatablePropertiesEquals(expectedSollicitatie, getPersistedSollicitatie(expectedSollicitatie));
    }
}
