package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EobjectclassificatieAsserts.*;
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
import nl.ritense.demo.domain.Eobjectclassificatie;
import nl.ritense.demo.repository.EobjectclassificatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EobjectclassificatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EobjectclassificatieResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/eobjectclassificaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EobjectclassificatieRepository eobjectclassificatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEobjectclassificatieMockMvc;

    private Eobjectclassificatie eobjectclassificatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjectclassificatie createEntity(EntityManager em) {
        Eobjectclassificatie eobjectclassificatie = new Eobjectclassificatie().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return eobjectclassificatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjectclassificatie createUpdatedEntity(EntityManager em) {
        Eobjectclassificatie eobjectclassificatie = new Eobjectclassificatie().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return eobjectclassificatie;
    }

    @BeforeEach
    public void initTest() {
        eobjectclassificatie = createEntity(em);
    }

    @Test
    @Transactional
    void createEobjectclassificatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Eobjectclassificatie
        var returnedEobjectclassificatie = om.readValue(
            restEobjectclassificatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjectclassificatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Eobjectclassificatie.class
        );

        // Validate the Eobjectclassificatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEobjectclassificatieUpdatableFieldsEquals(
            returnedEobjectclassificatie,
            getPersistedEobjectclassificatie(returnedEobjectclassificatie)
        );
    }

    @Test
    @Transactional
    void createEobjectclassificatieWithExistingId() throws Exception {
        // Create the Eobjectclassificatie with an existing ID
        eobjectclassificatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEobjectclassificatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjectclassificatie)))
            .andExpect(status().isBadRequest());

        // Validate the Eobjectclassificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEobjectclassificaties() throws Exception {
        // Initialize the database
        eobjectclassificatieRepository.saveAndFlush(eobjectclassificatie);

        // Get all the eobjectclassificatieList
        restEobjectclassificatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eobjectclassificatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getEobjectclassificatie() throws Exception {
        // Initialize the database
        eobjectclassificatieRepository.saveAndFlush(eobjectclassificatie);

        // Get the eobjectclassificatie
        restEobjectclassificatieMockMvc
            .perform(get(ENTITY_API_URL_ID, eobjectclassificatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eobjectclassificatie.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingEobjectclassificatie() throws Exception {
        // Get the eobjectclassificatie
        restEobjectclassificatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEobjectclassificatie() throws Exception {
        // Initialize the database
        eobjectclassificatieRepository.saveAndFlush(eobjectclassificatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eobjectclassificatie
        Eobjectclassificatie updatedEobjectclassificatie = eobjectclassificatieRepository
            .findById(eobjectclassificatie.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedEobjectclassificatie are not directly saved in db
        em.detach(updatedEobjectclassificatie);
        updatedEobjectclassificatie.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restEobjectclassificatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEobjectclassificatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEobjectclassificatie))
            )
            .andExpect(status().isOk());

        // Validate the Eobjectclassificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEobjectclassificatieToMatchAllProperties(updatedEobjectclassificatie);
    }

    @Test
    @Transactional
    void putNonExistingEobjectclassificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjectclassificatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEobjectclassificatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eobjectclassificatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eobjectclassificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eobjectclassificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEobjectclassificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjectclassificatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEobjectclassificatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eobjectclassificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eobjectclassificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEobjectclassificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjectclassificatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEobjectclassificatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjectclassificatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eobjectclassificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEobjectclassificatieWithPatch() throws Exception {
        // Initialize the database
        eobjectclassificatieRepository.saveAndFlush(eobjectclassificatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eobjectclassificatie using partial update
        Eobjectclassificatie partialUpdatedEobjectclassificatie = new Eobjectclassificatie();
        partialUpdatedEobjectclassificatie.setId(eobjectclassificatie.getId());

        partialUpdatedEobjectclassificatie.naam(UPDATED_NAAM);

        restEobjectclassificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEobjectclassificatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEobjectclassificatie))
            )
            .andExpect(status().isOk());

        // Validate the Eobjectclassificatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEobjectclassificatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEobjectclassificatie, eobjectclassificatie),
            getPersistedEobjectclassificatie(eobjectclassificatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateEobjectclassificatieWithPatch() throws Exception {
        // Initialize the database
        eobjectclassificatieRepository.saveAndFlush(eobjectclassificatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eobjectclassificatie using partial update
        Eobjectclassificatie partialUpdatedEobjectclassificatie = new Eobjectclassificatie();
        partialUpdatedEobjectclassificatie.setId(eobjectclassificatie.getId());

        partialUpdatedEobjectclassificatie.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restEobjectclassificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEobjectclassificatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEobjectclassificatie))
            )
            .andExpect(status().isOk());

        // Validate the Eobjectclassificatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEobjectclassificatieUpdatableFieldsEquals(
            partialUpdatedEobjectclassificatie,
            getPersistedEobjectclassificatie(partialUpdatedEobjectclassificatie)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEobjectclassificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjectclassificatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEobjectclassificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eobjectclassificatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eobjectclassificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eobjectclassificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEobjectclassificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjectclassificatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEobjectclassificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eobjectclassificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eobjectclassificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEobjectclassificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjectclassificatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEobjectclassificatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(eobjectclassificatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eobjectclassificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEobjectclassificatie() throws Exception {
        // Initialize the database
        eobjectclassificatieRepository.saveAndFlush(eobjectclassificatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eobjectclassificatie
        restEobjectclassificatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, eobjectclassificatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return eobjectclassificatieRepository.count();
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

    protected Eobjectclassificatie getPersistedEobjectclassificatie(Eobjectclassificatie eobjectclassificatie) {
        return eobjectclassificatieRepository.findById(eobjectclassificatie.getId()).orElseThrow();
    }

    protected void assertPersistedEobjectclassificatieToMatchAllProperties(Eobjectclassificatie expectedEobjectclassificatie) {
        assertEobjectclassificatieAllPropertiesEquals(
            expectedEobjectclassificatie,
            getPersistedEobjectclassificatie(expectedEobjectclassificatie)
        );
    }

    protected void assertPersistedEobjectclassificatieToMatchUpdatableProperties(Eobjectclassificatie expectedEobjectclassificatie) {
        assertEobjectclassificatieAllUpdatablePropertiesEquals(
            expectedEobjectclassificatie,
            getPersistedEobjectclassificatie(expectedEobjectclassificatie)
        );
    }
}
