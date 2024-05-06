package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BeperkingscoresoortAsserts.*;
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
import nl.ritense.demo.domain.Beperkingscoresoort;
import nl.ritense.demo.repository.BeperkingscoresoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BeperkingscoresoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BeperkingscoresoortResourceIT {

    private static final String DEFAULT_VRAAG = "AAAAAAAAAA";
    private static final String UPDATED_VRAAG = "BBBBBBBBBB";

    private static final String DEFAULT_WET = "AAAAAAAAAA";
    private static final String UPDATED_WET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/beperkingscoresoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BeperkingscoresoortRepository beperkingscoresoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeperkingscoresoortMockMvc;

    private Beperkingscoresoort beperkingscoresoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beperkingscoresoort createEntity(EntityManager em) {
        Beperkingscoresoort beperkingscoresoort = new Beperkingscoresoort().vraag(DEFAULT_VRAAG).wet(DEFAULT_WET);
        return beperkingscoresoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beperkingscoresoort createUpdatedEntity(EntityManager em) {
        Beperkingscoresoort beperkingscoresoort = new Beperkingscoresoort().vraag(UPDATED_VRAAG).wet(UPDATED_WET);
        return beperkingscoresoort;
    }

    @BeforeEach
    public void initTest() {
        beperkingscoresoort = createEntity(em);
    }

    @Test
    @Transactional
    void createBeperkingscoresoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Beperkingscoresoort
        var returnedBeperkingscoresoort = om.readValue(
            restBeperkingscoresoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beperkingscoresoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Beperkingscoresoort.class
        );

        // Validate the Beperkingscoresoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBeperkingscoresoortUpdatableFieldsEquals(
            returnedBeperkingscoresoort,
            getPersistedBeperkingscoresoort(returnedBeperkingscoresoort)
        );
    }

    @Test
    @Transactional
    void createBeperkingscoresoortWithExistingId() throws Exception {
        // Create the Beperkingscoresoort with an existing ID
        beperkingscoresoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeperkingscoresoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beperkingscoresoort)))
            .andExpect(status().isBadRequest());

        // Validate the Beperkingscoresoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBeperkingscoresoorts() throws Exception {
        // Initialize the database
        beperkingscoresoortRepository.saveAndFlush(beperkingscoresoort);

        // Get all the beperkingscoresoortList
        restBeperkingscoresoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beperkingscoresoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].vraag").value(hasItem(DEFAULT_VRAAG)))
            .andExpect(jsonPath("$.[*].wet").value(hasItem(DEFAULT_WET)));
    }

    @Test
    @Transactional
    void getBeperkingscoresoort() throws Exception {
        // Initialize the database
        beperkingscoresoortRepository.saveAndFlush(beperkingscoresoort);

        // Get the beperkingscoresoort
        restBeperkingscoresoortMockMvc
            .perform(get(ENTITY_API_URL_ID, beperkingscoresoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beperkingscoresoort.getId().intValue()))
            .andExpect(jsonPath("$.vraag").value(DEFAULT_VRAAG))
            .andExpect(jsonPath("$.wet").value(DEFAULT_WET));
    }

    @Test
    @Transactional
    void getNonExistingBeperkingscoresoort() throws Exception {
        // Get the beperkingscoresoort
        restBeperkingscoresoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBeperkingscoresoort() throws Exception {
        // Initialize the database
        beperkingscoresoortRepository.saveAndFlush(beperkingscoresoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beperkingscoresoort
        Beperkingscoresoort updatedBeperkingscoresoort = beperkingscoresoortRepository.findById(beperkingscoresoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBeperkingscoresoort are not directly saved in db
        em.detach(updatedBeperkingscoresoort);
        updatedBeperkingscoresoort.vraag(UPDATED_VRAAG).wet(UPDATED_WET);

        restBeperkingscoresoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBeperkingscoresoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBeperkingscoresoort))
            )
            .andExpect(status().isOk());

        // Validate the Beperkingscoresoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBeperkingscoresoortToMatchAllProperties(updatedBeperkingscoresoort);
    }

    @Test
    @Transactional
    void putNonExistingBeperkingscoresoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscoresoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeperkingscoresoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, beperkingscoresoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beperkingscoresoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperkingscoresoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBeperkingscoresoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscoresoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingscoresoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beperkingscoresoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperkingscoresoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBeperkingscoresoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscoresoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingscoresoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beperkingscoresoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beperkingscoresoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBeperkingscoresoortWithPatch() throws Exception {
        // Initialize the database
        beperkingscoresoortRepository.saveAndFlush(beperkingscoresoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beperkingscoresoort using partial update
        Beperkingscoresoort partialUpdatedBeperkingscoresoort = new Beperkingscoresoort();
        partialUpdatedBeperkingscoresoort.setId(beperkingscoresoort.getId());

        restBeperkingscoresoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeperkingscoresoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeperkingscoresoort))
            )
            .andExpect(status().isOk());

        // Validate the Beperkingscoresoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeperkingscoresoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBeperkingscoresoort, beperkingscoresoort),
            getPersistedBeperkingscoresoort(beperkingscoresoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateBeperkingscoresoortWithPatch() throws Exception {
        // Initialize the database
        beperkingscoresoortRepository.saveAndFlush(beperkingscoresoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beperkingscoresoort using partial update
        Beperkingscoresoort partialUpdatedBeperkingscoresoort = new Beperkingscoresoort();
        partialUpdatedBeperkingscoresoort.setId(beperkingscoresoort.getId());

        partialUpdatedBeperkingscoresoort.vraag(UPDATED_VRAAG).wet(UPDATED_WET);

        restBeperkingscoresoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeperkingscoresoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeperkingscoresoort))
            )
            .andExpect(status().isOk());

        // Validate the Beperkingscoresoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeperkingscoresoortUpdatableFieldsEquals(
            partialUpdatedBeperkingscoresoort,
            getPersistedBeperkingscoresoort(partialUpdatedBeperkingscoresoort)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBeperkingscoresoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscoresoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeperkingscoresoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, beperkingscoresoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beperkingscoresoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperkingscoresoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBeperkingscoresoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscoresoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingscoresoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beperkingscoresoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperkingscoresoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBeperkingscoresoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscoresoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingscoresoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(beperkingscoresoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beperkingscoresoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBeperkingscoresoort() throws Exception {
        // Initialize the database
        beperkingscoresoortRepository.saveAndFlush(beperkingscoresoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the beperkingscoresoort
        restBeperkingscoresoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, beperkingscoresoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return beperkingscoresoortRepository.count();
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

    protected Beperkingscoresoort getPersistedBeperkingscoresoort(Beperkingscoresoort beperkingscoresoort) {
        return beperkingscoresoortRepository.findById(beperkingscoresoort.getId()).orElseThrow();
    }

    protected void assertPersistedBeperkingscoresoortToMatchAllProperties(Beperkingscoresoort expectedBeperkingscoresoort) {
        assertBeperkingscoresoortAllPropertiesEquals(
            expectedBeperkingscoresoort,
            getPersistedBeperkingscoresoort(expectedBeperkingscoresoort)
        );
    }

    protected void assertPersistedBeperkingscoresoortToMatchUpdatableProperties(Beperkingscoresoort expectedBeperkingscoresoort) {
        assertBeperkingscoresoortAllUpdatablePropertiesEquals(
            expectedBeperkingscoresoort,
            getPersistedBeperkingscoresoort(expectedBeperkingscoresoort)
        );
    }
}
