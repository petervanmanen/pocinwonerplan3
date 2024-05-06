package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OpdrachtnemerAsserts.*;
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
import nl.ritense.demo.domain.Opdrachtnemer;
import nl.ritense.demo.repository.OpdrachtnemerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OpdrachtnemerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OpdrachtnemerResourceIT {

    private static final String DEFAULT_CLUSTERCODE = "AAAAAAAAAA";
    private static final String UPDATED_CLUSTERCODE = "BBBBBBBBBB";

    private static final String DEFAULT_CLUSTERCODEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_CLUSTERCODEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/opdrachtnemers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OpdrachtnemerRepository opdrachtnemerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpdrachtnemerMockMvc;

    private Opdrachtnemer opdrachtnemer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opdrachtnemer createEntity(EntityManager em) {
        Opdrachtnemer opdrachtnemer = new Opdrachtnemer()
            .clustercode(DEFAULT_CLUSTERCODE)
            .clustercodeomschrijving(DEFAULT_CLUSTERCODEOMSCHRIJVING)
            .naam(DEFAULT_NAAM)
            .nummer(DEFAULT_NUMMER)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return opdrachtnemer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opdrachtnemer createUpdatedEntity(EntityManager em) {
        Opdrachtnemer opdrachtnemer = new Opdrachtnemer()
            .clustercode(UPDATED_CLUSTERCODE)
            .clustercodeomschrijving(UPDATED_CLUSTERCODEOMSCHRIJVING)
            .naam(UPDATED_NAAM)
            .nummer(UPDATED_NUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return opdrachtnemer;
    }

    @BeforeEach
    public void initTest() {
        opdrachtnemer = createEntity(em);
    }

    @Test
    @Transactional
    void createOpdrachtnemer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Opdrachtnemer
        var returnedOpdrachtnemer = om.readValue(
            restOpdrachtnemerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opdrachtnemer)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Opdrachtnemer.class
        );

        // Validate the Opdrachtnemer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOpdrachtnemerUpdatableFieldsEquals(returnedOpdrachtnemer, getPersistedOpdrachtnemer(returnedOpdrachtnemer));
    }

    @Test
    @Transactional
    void createOpdrachtnemerWithExistingId() throws Exception {
        // Create the Opdrachtnemer with an existing ID
        opdrachtnemer.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpdrachtnemerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opdrachtnemer)))
            .andExpect(status().isBadRequest());

        // Validate the Opdrachtnemer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOpdrachtnemers() throws Exception {
        // Initialize the database
        opdrachtnemerRepository.saveAndFlush(opdrachtnemer);

        // Get all the opdrachtnemerList
        restOpdrachtnemerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opdrachtnemer.getId().intValue())))
            .andExpect(jsonPath("$.[*].clustercode").value(hasItem(DEFAULT_CLUSTERCODE)))
            .andExpect(jsonPath("$.[*].clustercodeomschrijving").value(hasItem(DEFAULT_CLUSTERCODEOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getOpdrachtnemer() throws Exception {
        // Initialize the database
        opdrachtnemerRepository.saveAndFlush(opdrachtnemer);

        // Get the opdrachtnemer
        restOpdrachtnemerMockMvc
            .perform(get(ENTITY_API_URL_ID, opdrachtnemer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(opdrachtnemer.getId().intValue()))
            .andExpect(jsonPath("$.clustercode").value(DEFAULT_CLUSTERCODE))
            .andExpect(jsonPath("$.clustercodeomschrijving").value(DEFAULT_CLUSTERCODEOMSCHRIJVING))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingOpdrachtnemer() throws Exception {
        // Get the opdrachtnemer
        restOpdrachtnemerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOpdrachtnemer() throws Exception {
        // Initialize the database
        opdrachtnemerRepository.saveAndFlush(opdrachtnemer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opdrachtnemer
        Opdrachtnemer updatedOpdrachtnemer = opdrachtnemerRepository.findById(opdrachtnemer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOpdrachtnemer are not directly saved in db
        em.detach(updatedOpdrachtnemer);
        updatedOpdrachtnemer
            .clustercode(UPDATED_CLUSTERCODE)
            .clustercodeomschrijving(UPDATED_CLUSTERCODEOMSCHRIJVING)
            .naam(UPDATED_NAAM)
            .nummer(UPDATED_NUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restOpdrachtnemerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOpdrachtnemer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOpdrachtnemer))
            )
            .andExpect(status().isOk());

        // Validate the Opdrachtnemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOpdrachtnemerToMatchAllProperties(updatedOpdrachtnemer);
    }

    @Test
    @Transactional
    void putNonExistingOpdrachtnemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opdrachtnemer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpdrachtnemerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opdrachtnemer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(opdrachtnemer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opdrachtnemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOpdrachtnemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opdrachtnemer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpdrachtnemerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(opdrachtnemer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opdrachtnemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOpdrachtnemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opdrachtnemer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpdrachtnemerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opdrachtnemer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Opdrachtnemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOpdrachtnemerWithPatch() throws Exception {
        // Initialize the database
        opdrachtnemerRepository.saveAndFlush(opdrachtnemer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opdrachtnemer using partial update
        Opdrachtnemer partialUpdatedOpdrachtnemer = new Opdrachtnemer();
        partialUpdatedOpdrachtnemer.setId(opdrachtnemer.getId());

        partialUpdatedOpdrachtnemer
            .clustercode(UPDATED_CLUSTERCODE)
            .clustercodeomschrijving(UPDATED_CLUSTERCODEOMSCHRIJVING)
            .naam(UPDATED_NAAM)
            .nummer(UPDATED_NUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restOpdrachtnemerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpdrachtnemer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpdrachtnemer))
            )
            .andExpect(status().isOk());

        // Validate the Opdrachtnemer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpdrachtnemerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOpdrachtnemer, opdrachtnemer),
            getPersistedOpdrachtnemer(opdrachtnemer)
        );
    }

    @Test
    @Transactional
    void fullUpdateOpdrachtnemerWithPatch() throws Exception {
        // Initialize the database
        opdrachtnemerRepository.saveAndFlush(opdrachtnemer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opdrachtnemer using partial update
        Opdrachtnemer partialUpdatedOpdrachtnemer = new Opdrachtnemer();
        partialUpdatedOpdrachtnemer.setId(opdrachtnemer.getId());

        partialUpdatedOpdrachtnemer
            .clustercode(UPDATED_CLUSTERCODE)
            .clustercodeomschrijving(UPDATED_CLUSTERCODEOMSCHRIJVING)
            .naam(UPDATED_NAAM)
            .nummer(UPDATED_NUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restOpdrachtnemerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpdrachtnemer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpdrachtnemer))
            )
            .andExpect(status().isOk());

        // Validate the Opdrachtnemer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpdrachtnemerUpdatableFieldsEquals(partialUpdatedOpdrachtnemer, getPersistedOpdrachtnemer(partialUpdatedOpdrachtnemer));
    }

    @Test
    @Transactional
    void patchNonExistingOpdrachtnemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opdrachtnemer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpdrachtnemerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, opdrachtnemer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(opdrachtnemer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opdrachtnemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOpdrachtnemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opdrachtnemer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpdrachtnemerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(opdrachtnemer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opdrachtnemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOpdrachtnemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opdrachtnemer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpdrachtnemerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(opdrachtnemer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Opdrachtnemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOpdrachtnemer() throws Exception {
        // Initialize the database
        opdrachtnemerRepository.saveAndFlush(opdrachtnemer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the opdrachtnemer
        restOpdrachtnemerMockMvc
            .perform(delete(ENTITY_API_URL_ID, opdrachtnemer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return opdrachtnemerRepository.count();
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

    protected Opdrachtnemer getPersistedOpdrachtnemer(Opdrachtnemer opdrachtnemer) {
        return opdrachtnemerRepository.findById(opdrachtnemer.getId()).orElseThrow();
    }

    protected void assertPersistedOpdrachtnemerToMatchAllProperties(Opdrachtnemer expectedOpdrachtnemer) {
        assertOpdrachtnemerAllPropertiesEquals(expectedOpdrachtnemer, getPersistedOpdrachtnemer(expectedOpdrachtnemer));
    }

    protected void assertPersistedOpdrachtnemerToMatchUpdatableProperties(Opdrachtnemer expectedOpdrachtnemer) {
        assertOpdrachtnemerAllUpdatablePropertiesEquals(expectedOpdrachtnemer, getPersistedOpdrachtnemer(expectedOpdrachtnemer));
    }
}
