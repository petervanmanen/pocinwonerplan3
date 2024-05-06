package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ResultaatsoortAsserts.*;
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
import nl.ritense.demo.domain.Resultaatsoort;
import nl.ritense.demo.repository.ResultaatsoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ResultaatsoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResultaatsoortResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/resultaatsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ResultaatsoortRepository resultaatsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResultaatsoortMockMvc;

    private Resultaatsoort resultaatsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resultaatsoort createEntity(EntityManager em) {
        Resultaatsoort resultaatsoort = new Resultaatsoort().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return resultaatsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resultaatsoort createUpdatedEntity(EntityManager em) {
        Resultaatsoort resultaatsoort = new Resultaatsoort().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return resultaatsoort;
    }

    @BeforeEach
    public void initTest() {
        resultaatsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createResultaatsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Resultaatsoort
        var returnedResultaatsoort = om.readValue(
            restResultaatsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(resultaatsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Resultaatsoort.class
        );

        // Validate the Resultaatsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertResultaatsoortUpdatableFieldsEquals(returnedResultaatsoort, getPersistedResultaatsoort(returnedResultaatsoort));
    }

    @Test
    @Transactional
    void createResultaatsoortWithExistingId() throws Exception {
        // Create the Resultaatsoort with an existing ID
        resultaatsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultaatsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(resultaatsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Resultaatsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResultaatsoorts() throws Exception {
        // Initialize the database
        resultaatsoortRepository.saveAndFlush(resultaatsoort);

        // Get all the resultaatsoortList
        restResultaatsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultaatsoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getResultaatsoort() throws Exception {
        // Initialize the database
        resultaatsoortRepository.saveAndFlush(resultaatsoort);

        // Get the resultaatsoort
        restResultaatsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, resultaatsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resultaatsoort.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingResultaatsoort() throws Exception {
        // Get the resultaatsoort
        restResultaatsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResultaatsoort() throws Exception {
        // Initialize the database
        resultaatsoortRepository.saveAndFlush(resultaatsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the resultaatsoort
        Resultaatsoort updatedResultaatsoort = resultaatsoortRepository.findById(resultaatsoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedResultaatsoort are not directly saved in db
        em.detach(updatedResultaatsoort);
        updatedResultaatsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restResultaatsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResultaatsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedResultaatsoort))
            )
            .andExpect(status().isOk());

        // Validate the Resultaatsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedResultaatsoortToMatchAllProperties(updatedResultaatsoort);
    }

    @Test
    @Transactional
    void putNonExistingResultaatsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultaatsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultaatsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resultaatsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(resultaatsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resultaatsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResultaatsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultaatsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultaatsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(resultaatsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resultaatsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResultaatsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultaatsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultaatsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(resultaatsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Resultaatsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResultaatsoortWithPatch() throws Exception {
        // Initialize the database
        resultaatsoortRepository.saveAndFlush(resultaatsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the resultaatsoort using partial update
        Resultaatsoort partialUpdatedResultaatsoort = new Resultaatsoort();
        partialUpdatedResultaatsoort.setId(resultaatsoort.getId());

        partialUpdatedResultaatsoort.omschrijving(UPDATED_OMSCHRIJVING);

        restResultaatsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResultaatsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedResultaatsoort))
            )
            .andExpect(status().isOk());

        // Validate the Resultaatsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertResultaatsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedResultaatsoort, resultaatsoort),
            getPersistedResultaatsoort(resultaatsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateResultaatsoortWithPatch() throws Exception {
        // Initialize the database
        resultaatsoortRepository.saveAndFlush(resultaatsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the resultaatsoort using partial update
        Resultaatsoort partialUpdatedResultaatsoort = new Resultaatsoort();
        partialUpdatedResultaatsoort.setId(resultaatsoort.getId());

        partialUpdatedResultaatsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restResultaatsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResultaatsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedResultaatsoort))
            )
            .andExpect(status().isOk());

        // Validate the Resultaatsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertResultaatsoortUpdatableFieldsEquals(partialUpdatedResultaatsoort, getPersistedResultaatsoort(partialUpdatedResultaatsoort));
    }

    @Test
    @Transactional
    void patchNonExistingResultaatsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultaatsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultaatsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resultaatsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(resultaatsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resultaatsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResultaatsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultaatsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultaatsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(resultaatsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resultaatsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResultaatsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultaatsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultaatsoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(resultaatsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Resultaatsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResultaatsoort() throws Exception {
        // Initialize the database
        resultaatsoortRepository.saveAndFlush(resultaatsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the resultaatsoort
        restResultaatsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, resultaatsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return resultaatsoortRepository.count();
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

    protected Resultaatsoort getPersistedResultaatsoort(Resultaatsoort resultaatsoort) {
        return resultaatsoortRepository.findById(resultaatsoort.getId()).orElseThrow();
    }

    protected void assertPersistedResultaatsoortToMatchAllProperties(Resultaatsoort expectedResultaatsoort) {
        assertResultaatsoortAllPropertiesEquals(expectedResultaatsoort, getPersistedResultaatsoort(expectedResultaatsoort));
    }

    protected void assertPersistedResultaatsoortToMatchUpdatableProperties(Resultaatsoort expectedResultaatsoort) {
        assertResultaatsoortAllUpdatablePropertiesEquals(expectedResultaatsoort, getPersistedResultaatsoort(expectedResultaatsoort));
    }
}
