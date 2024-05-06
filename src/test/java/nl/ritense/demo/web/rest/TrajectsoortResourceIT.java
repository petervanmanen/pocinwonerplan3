package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TrajectsoortAsserts.*;
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
import nl.ritense.demo.domain.Trajectsoort;
import nl.ritense.demo.repository.TrajectsoortRepository;
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
 * Integration tests for the {@link TrajectsoortResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class TrajectsoortResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/trajectsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TrajectsoortRepository trajectsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrajectsoortMockMvc;

    private Trajectsoort trajectsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trajectsoort createEntity(EntityManager em) {
        Trajectsoort trajectsoort = new Trajectsoort().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return trajectsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trajectsoort createUpdatedEntity(EntityManager em) {
        Trajectsoort trajectsoort = new Trajectsoort().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return trajectsoort;
    }

    @BeforeEach
    public void initTest() {
        trajectsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createTrajectsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Trajectsoort
        var returnedTrajectsoort = om.readValue(
            restTrajectsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trajectsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Trajectsoort.class
        );

        // Validate the Trajectsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTrajectsoortUpdatableFieldsEquals(returnedTrajectsoort, getPersistedTrajectsoort(returnedTrajectsoort));
    }

    @Test
    @Transactional
    void createTrajectsoortWithExistingId() throws Exception {
        // Create the Trajectsoort with an existing ID
        trajectsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrajectsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trajectsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Trajectsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrajectsoorts() throws Exception {
        // Initialize the database
        trajectsoortRepository.saveAndFlush(trajectsoort);

        // Get all the trajectsoortList
        restTrajectsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trajectsoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getTrajectsoort() throws Exception {
        // Initialize the database
        trajectsoortRepository.saveAndFlush(trajectsoort);

        // Get the trajectsoort
        restTrajectsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, trajectsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trajectsoort.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingTrajectsoort() throws Exception {
        // Get the trajectsoort
        restTrajectsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrajectsoort() throws Exception {
        // Initialize the database
        trajectsoortRepository.saveAndFlush(trajectsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trajectsoort
        Trajectsoort updatedTrajectsoort = trajectsoortRepository.findById(trajectsoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTrajectsoort are not directly saved in db
        em.detach(updatedTrajectsoort);
        updatedTrajectsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restTrajectsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTrajectsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTrajectsoort))
            )
            .andExpect(status().isOk());

        // Validate the Trajectsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTrajectsoortToMatchAllProperties(updatedTrajectsoort);
    }

    @Test
    @Transactional
    void putNonExistingTrajectsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrajectsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trajectsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trajectsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trajectsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrajectsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajectsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trajectsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trajectsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrajectsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajectsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trajectsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trajectsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrajectsoortWithPatch() throws Exception {
        // Initialize the database
        trajectsoortRepository.saveAndFlush(trajectsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trajectsoort using partial update
        Trajectsoort partialUpdatedTrajectsoort = new Trajectsoort();
        partialUpdatedTrajectsoort.setId(trajectsoort.getId());

        partialUpdatedTrajectsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restTrajectsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrajectsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTrajectsoort))
            )
            .andExpect(status().isOk());

        // Validate the Trajectsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrajectsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTrajectsoort, trajectsoort),
            getPersistedTrajectsoort(trajectsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateTrajectsoortWithPatch() throws Exception {
        // Initialize the database
        trajectsoortRepository.saveAndFlush(trajectsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trajectsoort using partial update
        Trajectsoort partialUpdatedTrajectsoort = new Trajectsoort();
        partialUpdatedTrajectsoort.setId(trajectsoort.getId());

        partialUpdatedTrajectsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restTrajectsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrajectsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTrajectsoort))
            )
            .andExpect(status().isOk());

        // Validate the Trajectsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrajectsoortUpdatableFieldsEquals(partialUpdatedTrajectsoort, getPersistedTrajectsoort(partialUpdatedTrajectsoort));
    }

    @Test
    @Transactional
    void patchNonExistingTrajectsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrajectsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trajectsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trajectsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trajectsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrajectsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajectsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trajectsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trajectsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrajectsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajectsoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(trajectsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trajectsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrajectsoort() throws Exception {
        // Initialize the database
        trajectsoortRepository.saveAndFlush(trajectsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the trajectsoort
        restTrajectsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, trajectsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return trajectsoortRepository.count();
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

    protected Trajectsoort getPersistedTrajectsoort(Trajectsoort trajectsoort) {
        return trajectsoortRepository.findById(trajectsoort.getId()).orElseThrow();
    }

    protected void assertPersistedTrajectsoortToMatchAllProperties(Trajectsoort expectedTrajectsoort) {
        assertTrajectsoortAllPropertiesEquals(expectedTrajectsoort, getPersistedTrajectsoort(expectedTrajectsoort));
    }

    protected void assertPersistedTrajectsoortToMatchUpdatableProperties(Trajectsoort expectedTrajectsoort) {
        assertTrajectsoortAllUpdatablePropertiesEquals(expectedTrajectsoort, getPersistedTrajectsoort(expectedTrajectsoort));
    }
}
