package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.IdealisatieAsserts.*;
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
import nl.ritense.demo.domain.Idealisatie;
import nl.ritense.demo.repository.IdealisatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IdealisatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IdealisatieResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/idealisaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IdealisatieRepository idealisatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIdealisatieMockMvc;

    private Idealisatie idealisatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Idealisatie createEntity(EntityManager em) {
        Idealisatie idealisatie = new Idealisatie().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return idealisatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Idealisatie createUpdatedEntity(EntityManager em) {
        Idealisatie idealisatie = new Idealisatie().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return idealisatie;
    }

    @BeforeEach
    public void initTest() {
        idealisatie = createEntity(em);
    }

    @Test
    @Transactional
    void createIdealisatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Idealisatie
        var returnedIdealisatie = om.readValue(
            restIdealisatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(idealisatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Idealisatie.class
        );

        // Validate the Idealisatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertIdealisatieUpdatableFieldsEquals(returnedIdealisatie, getPersistedIdealisatie(returnedIdealisatie));
    }

    @Test
    @Transactional
    void createIdealisatieWithExistingId() throws Exception {
        // Create the Idealisatie with an existing ID
        idealisatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdealisatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(idealisatie)))
            .andExpect(status().isBadRequest());

        // Validate the Idealisatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIdealisaties() throws Exception {
        // Initialize the database
        idealisatieRepository.saveAndFlush(idealisatie);

        // Get all the idealisatieList
        restIdealisatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idealisatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getIdealisatie() throws Exception {
        // Initialize the database
        idealisatieRepository.saveAndFlush(idealisatie);

        // Get the idealisatie
        restIdealisatieMockMvc
            .perform(get(ENTITY_API_URL_ID, idealisatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(idealisatie.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingIdealisatie() throws Exception {
        // Get the idealisatie
        restIdealisatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIdealisatie() throws Exception {
        // Initialize the database
        idealisatieRepository.saveAndFlush(idealisatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the idealisatie
        Idealisatie updatedIdealisatie = idealisatieRepository.findById(idealisatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIdealisatie are not directly saved in db
        em.detach(updatedIdealisatie);
        updatedIdealisatie.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restIdealisatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIdealisatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedIdealisatie))
            )
            .andExpect(status().isOk());

        // Validate the Idealisatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIdealisatieToMatchAllProperties(updatedIdealisatie);
    }

    @Test
    @Transactional
    void putNonExistingIdealisatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        idealisatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdealisatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, idealisatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(idealisatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idealisatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIdealisatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        idealisatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdealisatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(idealisatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idealisatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIdealisatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        idealisatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdealisatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(idealisatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Idealisatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIdealisatieWithPatch() throws Exception {
        // Initialize the database
        idealisatieRepository.saveAndFlush(idealisatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the idealisatie using partial update
        Idealisatie partialUpdatedIdealisatie = new Idealisatie();
        partialUpdatedIdealisatie.setId(idealisatie.getId());

        partialUpdatedIdealisatie.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restIdealisatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdealisatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIdealisatie))
            )
            .andExpect(status().isOk());

        // Validate the Idealisatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIdealisatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedIdealisatie, idealisatie),
            getPersistedIdealisatie(idealisatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateIdealisatieWithPatch() throws Exception {
        // Initialize the database
        idealisatieRepository.saveAndFlush(idealisatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the idealisatie using partial update
        Idealisatie partialUpdatedIdealisatie = new Idealisatie();
        partialUpdatedIdealisatie.setId(idealisatie.getId());

        partialUpdatedIdealisatie.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restIdealisatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdealisatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIdealisatie))
            )
            .andExpect(status().isOk());

        // Validate the Idealisatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIdealisatieUpdatableFieldsEquals(partialUpdatedIdealisatie, getPersistedIdealisatie(partialUpdatedIdealisatie));
    }

    @Test
    @Transactional
    void patchNonExistingIdealisatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        idealisatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdealisatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, idealisatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(idealisatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idealisatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIdealisatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        idealisatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdealisatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(idealisatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idealisatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIdealisatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        idealisatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdealisatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(idealisatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Idealisatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIdealisatie() throws Exception {
        // Initialize the database
        idealisatieRepository.saveAndFlush(idealisatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the idealisatie
        restIdealisatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, idealisatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return idealisatieRepository.count();
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

    protected Idealisatie getPersistedIdealisatie(Idealisatie idealisatie) {
        return idealisatieRepository.findById(idealisatie.getId()).orElseThrow();
    }

    protected void assertPersistedIdealisatieToMatchAllProperties(Idealisatie expectedIdealisatie) {
        assertIdealisatieAllPropertiesEquals(expectedIdealisatie, getPersistedIdealisatie(expectedIdealisatie));
    }

    protected void assertPersistedIdealisatieToMatchUpdatableProperties(Idealisatie expectedIdealisatie) {
        assertIdealisatieAllUpdatablePropertiesEquals(expectedIdealisatie, getPersistedIdealisatie(expectedIdealisatie));
    }
}
