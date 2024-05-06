package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FietsregistratieAsserts.*;
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
import nl.ritense.demo.domain.Fietsregistratie;
import nl.ritense.demo.repository.FietsregistratieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FietsregistratieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FietsregistratieResourceIT {

    private static final String DEFAULT_GELABELD = "AAAAAAAAAA";
    private static final String UPDATED_GELABELD = "BBBBBBBBBB";

    private static final String DEFAULT_VERWIJDERD = "AAAAAAAAAA";
    private static final String UPDATED_VERWIJDERD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fietsregistraties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FietsregistratieRepository fietsregistratieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFietsregistratieMockMvc;

    private Fietsregistratie fietsregistratie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fietsregistratie createEntity(EntityManager em) {
        Fietsregistratie fietsregistratie = new Fietsregistratie().gelabeld(DEFAULT_GELABELD).verwijderd(DEFAULT_VERWIJDERD);
        return fietsregistratie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fietsregistratie createUpdatedEntity(EntityManager em) {
        Fietsregistratie fietsregistratie = new Fietsregistratie().gelabeld(UPDATED_GELABELD).verwijderd(UPDATED_VERWIJDERD);
        return fietsregistratie;
    }

    @BeforeEach
    public void initTest() {
        fietsregistratie = createEntity(em);
    }

    @Test
    @Transactional
    void createFietsregistratie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Fietsregistratie
        var returnedFietsregistratie = om.readValue(
            restFietsregistratieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fietsregistratie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Fietsregistratie.class
        );

        // Validate the Fietsregistratie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFietsregistratieUpdatableFieldsEquals(returnedFietsregistratie, getPersistedFietsregistratie(returnedFietsregistratie));
    }

    @Test
    @Transactional
    void createFietsregistratieWithExistingId() throws Exception {
        // Create the Fietsregistratie with an existing ID
        fietsregistratie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFietsregistratieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fietsregistratie)))
            .andExpect(status().isBadRequest());

        // Validate the Fietsregistratie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFietsregistraties() throws Exception {
        // Initialize the database
        fietsregistratieRepository.saveAndFlush(fietsregistratie);

        // Get all the fietsregistratieList
        restFietsregistratieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fietsregistratie.getId().intValue())))
            .andExpect(jsonPath("$.[*].gelabeld").value(hasItem(DEFAULT_GELABELD)))
            .andExpect(jsonPath("$.[*].verwijderd").value(hasItem(DEFAULT_VERWIJDERD)));
    }

    @Test
    @Transactional
    void getFietsregistratie() throws Exception {
        // Initialize the database
        fietsregistratieRepository.saveAndFlush(fietsregistratie);

        // Get the fietsregistratie
        restFietsregistratieMockMvc
            .perform(get(ENTITY_API_URL_ID, fietsregistratie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fietsregistratie.getId().intValue()))
            .andExpect(jsonPath("$.gelabeld").value(DEFAULT_GELABELD))
            .andExpect(jsonPath("$.verwijderd").value(DEFAULT_VERWIJDERD));
    }

    @Test
    @Transactional
    void getNonExistingFietsregistratie() throws Exception {
        // Get the fietsregistratie
        restFietsregistratieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFietsregistratie() throws Exception {
        // Initialize the database
        fietsregistratieRepository.saveAndFlush(fietsregistratie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fietsregistratie
        Fietsregistratie updatedFietsregistratie = fietsregistratieRepository.findById(fietsregistratie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFietsregistratie are not directly saved in db
        em.detach(updatedFietsregistratie);
        updatedFietsregistratie.gelabeld(UPDATED_GELABELD).verwijderd(UPDATED_VERWIJDERD);

        restFietsregistratieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFietsregistratie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFietsregistratie))
            )
            .andExpect(status().isOk());

        // Validate the Fietsregistratie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFietsregistratieToMatchAllProperties(updatedFietsregistratie);
    }

    @Test
    @Transactional
    void putNonExistingFietsregistratie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fietsregistratie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFietsregistratieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fietsregistratie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fietsregistratie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fietsregistratie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFietsregistratie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fietsregistratie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFietsregistratieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fietsregistratie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fietsregistratie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFietsregistratie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fietsregistratie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFietsregistratieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fietsregistratie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fietsregistratie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFietsregistratieWithPatch() throws Exception {
        // Initialize the database
        fietsregistratieRepository.saveAndFlush(fietsregistratie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fietsregistratie using partial update
        Fietsregistratie partialUpdatedFietsregistratie = new Fietsregistratie();
        partialUpdatedFietsregistratie.setId(fietsregistratie.getId());

        partialUpdatedFietsregistratie.verwijderd(UPDATED_VERWIJDERD);

        restFietsregistratieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFietsregistratie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFietsregistratie))
            )
            .andExpect(status().isOk());

        // Validate the Fietsregistratie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFietsregistratieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFietsregistratie, fietsregistratie),
            getPersistedFietsregistratie(fietsregistratie)
        );
    }

    @Test
    @Transactional
    void fullUpdateFietsregistratieWithPatch() throws Exception {
        // Initialize the database
        fietsregistratieRepository.saveAndFlush(fietsregistratie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fietsregistratie using partial update
        Fietsregistratie partialUpdatedFietsregistratie = new Fietsregistratie();
        partialUpdatedFietsregistratie.setId(fietsregistratie.getId());

        partialUpdatedFietsregistratie.gelabeld(UPDATED_GELABELD).verwijderd(UPDATED_VERWIJDERD);

        restFietsregistratieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFietsregistratie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFietsregistratie))
            )
            .andExpect(status().isOk());

        // Validate the Fietsregistratie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFietsregistratieUpdatableFieldsEquals(
            partialUpdatedFietsregistratie,
            getPersistedFietsregistratie(partialUpdatedFietsregistratie)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFietsregistratie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fietsregistratie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFietsregistratieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fietsregistratie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fietsregistratie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fietsregistratie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFietsregistratie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fietsregistratie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFietsregistratieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fietsregistratie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fietsregistratie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFietsregistratie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fietsregistratie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFietsregistratieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fietsregistratie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fietsregistratie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFietsregistratie() throws Exception {
        // Initialize the database
        fietsregistratieRepository.saveAndFlush(fietsregistratie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fietsregistratie
        restFietsregistratieMockMvc
            .perform(delete(ENTITY_API_URL_ID, fietsregistratie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fietsregistratieRepository.count();
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

    protected Fietsregistratie getPersistedFietsregistratie(Fietsregistratie fietsregistratie) {
        return fietsregistratieRepository.findById(fietsregistratie.getId()).orElseThrow();
    }

    protected void assertPersistedFietsregistratieToMatchAllProperties(Fietsregistratie expectedFietsregistratie) {
        assertFietsregistratieAllPropertiesEquals(expectedFietsregistratie, getPersistedFietsregistratie(expectedFietsregistratie));
    }

    protected void assertPersistedFietsregistratieToMatchUpdatableProperties(Fietsregistratie expectedFietsregistratie) {
        assertFietsregistratieAllUpdatablePropertiesEquals(
            expectedFietsregistratie,
            getPersistedFietsregistratie(expectedFietsregistratie)
        );
    }
}
