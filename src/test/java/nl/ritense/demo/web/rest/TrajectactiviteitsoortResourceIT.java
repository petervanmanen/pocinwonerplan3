package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TrajectactiviteitsoortAsserts.*;
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
import nl.ritense.demo.domain.Trajectactiviteitsoort;
import nl.ritense.demo.repository.TrajectactiviteitsoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TrajectactiviteitsoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrajectactiviteitsoortResourceIT {

    private static final String DEFAULT_AANLEVERENSRG = "AAAAAAAAAA";
    private static final String UPDATED_AANLEVERENSRG = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_TYPETRAJECTSRG = "AAAAAAAAAA";
    private static final String UPDATED_TYPETRAJECTSRG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/trajectactiviteitsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TrajectactiviteitsoortRepository trajectactiviteitsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrajectactiviteitsoortMockMvc;

    private Trajectactiviteitsoort trajectactiviteitsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trajectactiviteitsoort createEntity(EntityManager em) {
        Trajectactiviteitsoort trajectactiviteitsoort = new Trajectactiviteitsoort()
            .aanleverensrg(DEFAULT_AANLEVERENSRG)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .typetrajectsrg(DEFAULT_TYPETRAJECTSRG);
        return trajectactiviteitsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trajectactiviteitsoort createUpdatedEntity(EntityManager em) {
        Trajectactiviteitsoort trajectactiviteitsoort = new Trajectactiviteitsoort()
            .aanleverensrg(UPDATED_AANLEVERENSRG)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .typetrajectsrg(UPDATED_TYPETRAJECTSRG);
        return trajectactiviteitsoort;
    }

    @BeforeEach
    public void initTest() {
        trajectactiviteitsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createTrajectactiviteitsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Trajectactiviteitsoort
        var returnedTrajectactiviteitsoort = om.readValue(
            restTrajectactiviteitsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trajectactiviteitsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Trajectactiviteitsoort.class
        );

        // Validate the Trajectactiviteitsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTrajectactiviteitsoortUpdatableFieldsEquals(
            returnedTrajectactiviteitsoort,
            getPersistedTrajectactiviteitsoort(returnedTrajectactiviteitsoort)
        );
    }

    @Test
    @Transactional
    void createTrajectactiviteitsoortWithExistingId() throws Exception {
        // Create the Trajectactiviteitsoort with an existing ID
        trajectactiviteitsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrajectactiviteitsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trajectactiviteitsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Trajectactiviteitsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrajectactiviteitsoorts() throws Exception {
        // Initialize the database
        trajectactiviteitsoortRepository.saveAndFlush(trajectactiviteitsoort);

        // Get all the trajectactiviteitsoortList
        restTrajectactiviteitsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trajectactiviteitsoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanleverensrg").value(hasItem(DEFAULT_AANLEVERENSRG)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].typetrajectsrg").value(hasItem(DEFAULT_TYPETRAJECTSRG)));
    }

    @Test
    @Transactional
    void getTrajectactiviteitsoort() throws Exception {
        // Initialize the database
        trajectactiviteitsoortRepository.saveAndFlush(trajectactiviteitsoort);

        // Get the trajectactiviteitsoort
        restTrajectactiviteitsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, trajectactiviteitsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trajectactiviteitsoort.getId().intValue()))
            .andExpect(jsonPath("$.aanleverensrg").value(DEFAULT_AANLEVERENSRG))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.typetrajectsrg").value(DEFAULT_TYPETRAJECTSRG));
    }

    @Test
    @Transactional
    void getNonExistingTrajectactiviteitsoort() throws Exception {
        // Get the trajectactiviteitsoort
        restTrajectactiviteitsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrajectactiviteitsoort() throws Exception {
        // Initialize the database
        trajectactiviteitsoortRepository.saveAndFlush(trajectactiviteitsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trajectactiviteitsoort
        Trajectactiviteitsoort updatedTrajectactiviteitsoort = trajectactiviteitsoortRepository
            .findById(trajectactiviteitsoort.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedTrajectactiviteitsoort are not directly saved in db
        em.detach(updatedTrajectactiviteitsoort);
        updatedTrajectactiviteitsoort
            .aanleverensrg(UPDATED_AANLEVERENSRG)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .typetrajectsrg(UPDATED_TYPETRAJECTSRG);

        restTrajectactiviteitsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTrajectactiviteitsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTrajectactiviteitsoort))
            )
            .andExpect(status().isOk());

        // Validate the Trajectactiviteitsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTrajectactiviteitsoortToMatchAllProperties(updatedTrajectactiviteitsoort);
    }

    @Test
    @Transactional
    void putNonExistingTrajectactiviteitsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectactiviteitsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrajectactiviteitsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trajectactiviteitsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trajectactiviteitsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trajectactiviteitsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrajectactiviteitsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectactiviteitsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajectactiviteitsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trajectactiviteitsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trajectactiviteitsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrajectactiviteitsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectactiviteitsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajectactiviteitsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trajectactiviteitsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trajectactiviteitsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrajectactiviteitsoortWithPatch() throws Exception {
        // Initialize the database
        trajectactiviteitsoortRepository.saveAndFlush(trajectactiviteitsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trajectactiviteitsoort using partial update
        Trajectactiviteitsoort partialUpdatedTrajectactiviteitsoort = new Trajectactiviteitsoort();
        partialUpdatedTrajectactiviteitsoort.setId(trajectactiviteitsoort.getId());

        partialUpdatedTrajectactiviteitsoort.omschrijving(UPDATED_OMSCHRIJVING).typetrajectsrg(UPDATED_TYPETRAJECTSRG);

        restTrajectactiviteitsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrajectactiviteitsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTrajectactiviteitsoort))
            )
            .andExpect(status().isOk());

        // Validate the Trajectactiviteitsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrajectactiviteitsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTrajectactiviteitsoort, trajectactiviteitsoort),
            getPersistedTrajectactiviteitsoort(trajectactiviteitsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateTrajectactiviteitsoortWithPatch() throws Exception {
        // Initialize the database
        trajectactiviteitsoortRepository.saveAndFlush(trajectactiviteitsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trajectactiviteitsoort using partial update
        Trajectactiviteitsoort partialUpdatedTrajectactiviteitsoort = new Trajectactiviteitsoort();
        partialUpdatedTrajectactiviteitsoort.setId(trajectactiviteitsoort.getId());

        partialUpdatedTrajectactiviteitsoort
            .aanleverensrg(UPDATED_AANLEVERENSRG)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .typetrajectsrg(UPDATED_TYPETRAJECTSRG);

        restTrajectactiviteitsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrajectactiviteitsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTrajectactiviteitsoort))
            )
            .andExpect(status().isOk());

        // Validate the Trajectactiviteitsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrajectactiviteitsoortUpdatableFieldsEquals(
            partialUpdatedTrajectactiviteitsoort,
            getPersistedTrajectactiviteitsoort(partialUpdatedTrajectactiviteitsoort)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTrajectactiviteitsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectactiviteitsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrajectactiviteitsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trajectactiviteitsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trajectactiviteitsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trajectactiviteitsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrajectactiviteitsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectactiviteitsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajectactiviteitsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trajectactiviteitsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trajectactiviteitsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrajectactiviteitsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectactiviteitsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajectactiviteitsoortMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(trajectactiviteitsoort))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trajectactiviteitsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrajectactiviteitsoort() throws Exception {
        // Initialize the database
        trajectactiviteitsoortRepository.saveAndFlush(trajectactiviteitsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the trajectactiviteitsoort
        restTrajectactiviteitsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, trajectactiviteitsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return trajectactiviteitsoortRepository.count();
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

    protected Trajectactiviteitsoort getPersistedTrajectactiviteitsoort(Trajectactiviteitsoort trajectactiviteitsoort) {
        return trajectactiviteitsoortRepository.findById(trajectactiviteitsoort.getId()).orElseThrow();
    }

    protected void assertPersistedTrajectactiviteitsoortToMatchAllProperties(Trajectactiviteitsoort expectedTrajectactiviteitsoort) {
        assertTrajectactiviteitsoortAllPropertiesEquals(
            expectedTrajectactiviteitsoort,
            getPersistedTrajectactiviteitsoort(expectedTrajectactiviteitsoort)
        );
    }

    protected void assertPersistedTrajectactiviteitsoortToMatchUpdatableProperties(Trajectactiviteitsoort expectedTrajectactiviteitsoort) {
        assertTrajectactiviteitsoortAllUpdatablePropertiesEquals(
            expectedTrajectactiviteitsoort,
            getPersistedTrajectactiviteitsoort(expectedTrajectactiviteitsoort)
        );
    }
}
