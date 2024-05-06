package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.IndelingAsserts.*;
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
import nl.ritense.demo.domain.Indeling;
import nl.ritense.demo.repository.IndelingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IndelingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndelingResourceIT {

    private static final String DEFAULT_INDELINGSOORT = "AAAAAAAAAA";
    private static final String UPDATED_INDELINGSOORT = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/indelings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IndelingRepository indelingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndelingMockMvc;

    private Indeling indeling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Indeling createEntity(EntityManager em) {
        Indeling indeling = new Indeling()
            .indelingsoort(DEFAULT_INDELINGSOORT)
            .naam(DEFAULT_NAAM)
            .nummer(DEFAULT_NUMMER)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return indeling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Indeling createUpdatedEntity(EntityManager em) {
        Indeling indeling = new Indeling()
            .indelingsoort(UPDATED_INDELINGSOORT)
            .naam(UPDATED_NAAM)
            .nummer(UPDATED_NUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return indeling;
    }

    @BeforeEach
    public void initTest() {
        indeling = createEntity(em);
    }

    @Test
    @Transactional
    void createIndeling() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Indeling
        var returnedIndeling = om.readValue(
            restIndelingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indeling)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Indeling.class
        );

        // Validate the Indeling in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertIndelingUpdatableFieldsEquals(returnedIndeling, getPersistedIndeling(returnedIndeling));
    }

    @Test
    @Transactional
    void createIndelingWithExistingId() throws Exception {
        // Create the Indeling with an existing ID
        indeling.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndelingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indeling)))
            .andExpect(status().isBadRequest());

        // Validate the Indeling in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIndelings() throws Exception {
        // Initialize the database
        indelingRepository.saveAndFlush(indeling);

        // Get all the indelingList
        restIndelingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indeling.getId().intValue())))
            .andExpect(jsonPath("$.[*].indelingsoort").value(hasItem(DEFAULT_INDELINGSOORT)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getIndeling() throws Exception {
        // Initialize the database
        indelingRepository.saveAndFlush(indeling);

        // Get the indeling
        restIndelingMockMvc
            .perform(get(ENTITY_API_URL_ID, indeling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indeling.getId().intValue()))
            .andExpect(jsonPath("$.indelingsoort").value(DEFAULT_INDELINGSOORT))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingIndeling() throws Exception {
        // Get the indeling
        restIndelingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIndeling() throws Exception {
        // Initialize the database
        indelingRepository.saveAndFlush(indeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the indeling
        Indeling updatedIndeling = indelingRepository.findById(indeling.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIndeling are not directly saved in db
        em.detach(updatedIndeling);
        updatedIndeling.indelingsoort(UPDATED_INDELINGSOORT).naam(UPDATED_NAAM).nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING);

        restIndelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIndeling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedIndeling))
            )
            .andExpect(status().isOk());

        // Validate the Indeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIndelingToMatchAllProperties(updatedIndeling);
    }

    @Test
    @Transactional
    void putNonExistingIndeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indeling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indeling.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(indeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndelingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indeling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Indeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndelingWithPatch() throws Exception {
        // Initialize the database
        indelingRepository.saveAndFlush(indeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the indeling using partial update
        Indeling partialUpdatedIndeling = new Indeling();
        partialUpdatedIndeling.setId(indeling.getId());

        partialUpdatedIndeling.nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING);

        restIndelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIndeling))
            )
            .andExpect(status().isOk());

        // Validate the Indeling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIndelingUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedIndeling, indeling), getPersistedIndeling(indeling));
    }

    @Test
    @Transactional
    void fullUpdateIndelingWithPatch() throws Exception {
        // Initialize the database
        indelingRepository.saveAndFlush(indeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the indeling using partial update
        Indeling partialUpdatedIndeling = new Indeling();
        partialUpdatedIndeling.setId(indeling.getId());

        partialUpdatedIndeling
            .indelingsoort(UPDATED_INDELINGSOORT)
            .naam(UPDATED_NAAM)
            .nummer(UPDATED_NUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restIndelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIndeling))
            )
            .andExpect(status().isOk());

        // Validate the Indeling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIndelingUpdatableFieldsEquals(partialUpdatedIndeling, getPersistedIndeling(partialUpdatedIndeling));
    }

    @Test
    @Transactional
    void patchNonExistingIndeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indeling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(indeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(indeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndelingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(indeling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Indeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndeling() throws Exception {
        // Initialize the database
        indelingRepository.saveAndFlush(indeling);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the indeling
        restIndelingMockMvc
            .perform(delete(ENTITY_API_URL_ID, indeling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return indelingRepository.count();
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

    protected Indeling getPersistedIndeling(Indeling indeling) {
        return indelingRepository.findById(indeling.getId()).orElseThrow();
    }

    protected void assertPersistedIndelingToMatchAllProperties(Indeling expectedIndeling) {
        assertIndelingAllPropertiesEquals(expectedIndeling, getPersistedIndeling(expectedIndeling));
    }

    protected void assertPersistedIndelingToMatchUpdatableProperties(Indeling expectedIndeling) {
        assertIndelingAllUpdatablePropertiesEquals(expectedIndeling, getPersistedIndeling(expectedIndeling));
    }
}
