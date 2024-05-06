package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BijzonderheidsoortAsserts.*;
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
import nl.ritense.demo.domain.Bijzonderheidsoort;
import nl.ritense.demo.repository.BijzonderheidsoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BijzonderheidsoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BijzonderheidsoortResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bijzonderheidsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BijzonderheidsoortRepository bijzonderheidsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBijzonderheidsoortMockMvc;

    private Bijzonderheidsoort bijzonderheidsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bijzonderheidsoort createEntity(EntityManager em) {
        Bijzonderheidsoort bijzonderheidsoort = new Bijzonderheidsoort().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return bijzonderheidsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bijzonderheidsoort createUpdatedEntity(EntityManager em) {
        Bijzonderheidsoort bijzonderheidsoort = new Bijzonderheidsoort().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return bijzonderheidsoort;
    }

    @BeforeEach
    public void initTest() {
        bijzonderheidsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createBijzonderheidsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bijzonderheidsoort
        var returnedBijzonderheidsoort = om.readValue(
            restBijzonderheidsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bijzonderheidsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bijzonderheidsoort.class
        );

        // Validate the Bijzonderheidsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBijzonderheidsoortUpdatableFieldsEquals(
            returnedBijzonderheidsoort,
            getPersistedBijzonderheidsoort(returnedBijzonderheidsoort)
        );
    }

    @Test
    @Transactional
    void createBijzonderheidsoortWithExistingId() throws Exception {
        // Create the Bijzonderheidsoort with an existing ID
        bijzonderheidsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBijzonderheidsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bijzonderheidsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Bijzonderheidsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBijzonderheidsoorts() throws Exception {
        // Initialize the database
        bijzonderheidsoortRepository.saveAndFlush(bijzonderheidsoort);

        // Get all the bijzonderheidsoortList
        restBijzonderheidsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bijzonderheidsoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getBijzonderheidsoort() throws Exception {
        // Initialize the database
        bijzonderheidsoortRepository.saveAndFlush(bijzonderheidsoort);

        // Get the bijzonderheidsoort
        restBijzonderheidsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, bijzonderheidsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bijzonderheidsoort.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingBijzonderheidsoort() throws Exception {
        // Get the bijzonderheidsoort
        restBijzonderheidsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBijzonderheidsoort() throws Exception {
        // Initialize the database
        bijzonderheidsoortRepository.saveAndFlush(bijzonderheidsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bijzonderheidsoort
        Bijzonderheidsoort updatedBijzonderheidsoort = bijzonderheidsoortRepository.findById(bijzonderheidsoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBijzonderheidsoort are not directly saved in db
        em.detach(updatedBijzonderheidsoort);
        updatedBijzonderheidsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restBijzonderheidsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBijzonderheidsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBijzonderheidsoort))
            )
            .andExpect(status().isOk());

        // Validate the Bijzonderheidsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBijzonderheidsoortToMatchAllProperties(updatedBijzonderheidsoort);
    }

    @Test
    @Transactional
    void putNonExistingBijzonderheidsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bijzonderheidsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBijzonderheidsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bijzonderheidsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bijzonderheidsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bijzonderheidsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBijzonderheidsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bijzonderheidsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBijzonderheidsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bijzonderheidsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bijzonderheidsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBijzonderheidsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bijzonderheidsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBijzonderheidsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bijzonderheidsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bijzonderheidsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBijzonderheidsoortWithPatch() throws Exception {
        // Initialize the database
        bijzonderheidsoortRepository.saveAndFlush(bijzonderheidsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bijzonderheidsoort using partial update
        Bijzonderheidsoort partialUpdatedBijzonderheidsoort = new Bijzonderheidsoort();
        partialUpdatedBijzonderheidsoort.setId(bijzonderheidsoort.getId());

        partialUpdatedBijzonderheidsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restBijzonderheidsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBijzonderheidsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBijzonderheidsoort))
            )
            .andExpect(status().isOk());

        // Validate the Bijzonderheidsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBijzonderheidsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBijzonderheidsoort, bijzonderheidsoort),
            getPersistedBijzonderheidsoort(bijzonderheidsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateBijzonderheidsoortWithPatch() throws Exception {
        // Initialize the database
        bijzonderheidsoortRepository.saveAndFlush(bijzonderheidsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bijzonderheidsoort using partial update
        Bijzonderheidsoort partialUpdatedBijzonderheidsoort = new Bijzonderheidsoort();
        partialUpdatedBijzonderheidsoort.setId(bijzonderheidsoort.getId());

        partialUpdatedBijzonderheidsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restBijzonderheidsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBijzonderheidsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBijzonderheidsoort))
            )
            .andExpect(status().isOk());

        // Validate the Bijzonderheidsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBijzonderheidsoortUpdatableFieldsEquals(
            partialUpdatedBijzonderheidsoort,
            getPersistedBijzonderheidsoort(partialUpdatedBijzonderheidsoort)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBijzonderheidsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bijzonderheidsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBijzonderheidsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bijzonderheidsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bijzonderheidsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bijzonderheidsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBijzonderheidsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bijzonderheidsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBijzonderheidsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bijzonderheidsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bijzonderheidsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBijzonderheidsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bijzonderheidsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBijzonderheidsoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bijzonderheidsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bijzonderheidsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBijzonderheidsoort() throws Exception {
        // Initialize the database
        bijzonderheidsoortRepository.saveAndFlush(bijzonderheidsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bijzonderheidsoort
        restBijzonderheidsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, bijzonderheidsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bijzonderheidsoortRepository.count();
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

    protected Bijzonderheidsoort getPersistedBijzonderheidsoort(Bijzonderheidsoort bijzonderheidsoort) {
        return bijzonderheidsoortRepository.findById(bijzonderheidsoort.getId()).orElseThrow();
    }

    protected void assertPersistedBijzonderheidsoortToMatchAllProperties(Bijzonderheidsoort expectedBijzonderheidsoort) {
        assertBijzonderheidsoortAllPropertiesEquals(expectedBijzonderheidsoort, getPersistedBijzonderheidsoort(expectedBijzonderheidsoort));
    }

    protected void assertPersistedBijzonderheidsoortToMatchUpdatableProperties(Bijzonderheidsoort expectedBijzonderheidsoort) {
        assertBijzonderheidsoortAllUpdatablePropertiesEquals(
            expectedBijzonderheidsoort,
            getPersistedBijzonderheidsoort(expectedBijzonderheidsoort)
        );
    }
}
