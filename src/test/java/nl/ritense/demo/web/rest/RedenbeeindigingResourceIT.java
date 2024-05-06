package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RedenbeeindigingAsserts.*;
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
import nl.ritense.demo.domain.Redenbeeindiging;
import nl.ritense.demo.repository.RedenbeeindigingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RedenbeeindigingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RedenbeeindigingResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/redenbeeindigings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RedenbeeindigingRepository redenbeeindigingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRedenbeeindigingMockMvc;

    private Redenbeeindiging redenbeeindiging;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Redenbeeindiging createEntity(EntityManager em) {
        Redenbeeindiging redenbeeindiging = new Redenbeeindiging().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return redenbeeindiging;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Redenbeeindiging createUpdatedEntity(EntityManager em) {
        Redenbeeindiging redenbeeindiging = new Redenbeeindiging().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return redenbeeindiging;
    }

    @BeforeEach
    public void initTest() {
        redenbeeindiging = createEntity(em);
    }

    @Test
    @Transactional
    void createRedenbeeindiging() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Redenbeeindiging
        var returnedRedenbeeindiging = om.readValue(
            restRedenbeeindigingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(redenbeeindiging)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Redenbeeindiging.class
        );

        // Validate the Redenbeeindiging in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRedenbeeindigingUpdatableFieldsEquals(returnedRedenbeeindiging, getPersistedRedenbeeindiging(returnedRedenbeeindiging));
    }

    @Test
    @Transactional
    void createRedenbeeindigingWithExistingId() throws Exception {
        // Create the Redenbeeindiging with an existing ID
        redenbeeindiging.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRedenbeeindigingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(redenbeeindiging)))
            .andExpect(status().isBadRequest());

        // Validate the Redenbeeindiging in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRedenbeeindigings() throws Exception {
        // Initialize the database
        redenbeeindigingRepository.saveAndFlush(redenbeeindiging);

        // Get all the redenbeeindigingList
        restRedenbeeindigingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(redenbeeindiging.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getRedenbeeindiging() throws Exception {
        // Initialize the database
        redenbeeindigingRepository.saveAndFlush(redenbeeindiging);

        // Get the redenbeeindiging
        restRedenbeeindigingMockMvc
            .perform(get(ENTITY_API_URL_ID, redenbeeindiging.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(redenbeeindiging.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingRedenbeeindiging() throws Exception {
        // Get the redenbeeindiging
        restRedenbeeindigingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRedenbeeindiging() throws Exception {
        // Initialize the database
        redenbeeindigingRepository.saveAndFlush(redenbeeindiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the redenbeeindiging
        Redenbeeindiging updatedRedenbeeindiging = redenbeeindigingRepository.findById(redenbeeindiging.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRedenbeeindiging are not directly saved in db
        em.detach(updatedRedenbeeindiging);
        updatedRedenbeeindiging.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restRedenbeeindigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRedenbeeindiging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRedenbeeindiging))
            )
            .andExpect(status().isOk());

        // Validate the Redenbeeindiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRedenbeeindigingToMatchAllProperties(updatedRedenbeeindiging);
    }

    @Test
    @Transactional
    void putNonExistingRedenbeeindiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenbeeindiging.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRedenbeeindigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, redenbeeindiging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(redenbeeindiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Redenbeeindiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRedenbeeindiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenbeeindiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedenbeeindigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(redenbeeindiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Redenbeeindiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRedenbeeindiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenbeeindiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedenbeeindigingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(redenbeeindiging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Redenbeeindiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRedenbeeindigingWithPatch() throws Exception {
        // Initialize the database
        redenbeeindigingRepository.saveAndFlush(redenbeeindiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the redenbeeindiging using partial update
        Redenbeeindiging partialUpdatedRedenbeeindiging = new Redenbeeindiging();
        partialUpdatedRedenbeeindiging.setId(redenbeeindiging.getId());

        partialUpdatedRedenbeeindiging.naam(UPDATED_NAAM);

        restRedenbeeindigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRedenbeeindiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRedenbeeindiging))
            )
            .andExpect(status().isOk());

        // Validate the Redenbeeindiging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRedenbeeindigingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRedenbeeindiging, redenbeeindiging),
            getPersistedRedenbeeindiging(redenbeeindiging)
        );
    }

    @Test
    @Transactional
    void fullUpdateRedenbeeindigingWithPatch() throws Exception {
        // Initialize the database
        redenbeeindigingRepository.saveAndFlush(redenbeeindiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the redenbeeindiging using partial update
        Redenbeeindiging partialUpdatedRedenbeeindiging = new Redenbeeindiging();
        partialUpdatedRedenbeeindiging.setId(redenbeeindiging.getId());

        partialUpdatedRedenbeeindiging.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restRedenbeeindigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRedenbeeindiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRedenbeeindiging))
            )
            .andExpect(status().isOk());

        // Validate the Redenbeeindiging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRedenbeeindigingUpdatableFieldsEquals(
            partialUpdatedRedenbeeindiging,
            getPersistedRedenbeeindiging(partialUpdatedRedenbeeindiging)
        );
    }

    @Test
    @Transactional
    void patchNonExistingRedenbeeindiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenbeeindiging.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRedenbeeindigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, redenbeeindiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(redenbeeindiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Redenbeeindiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRedenbeeindiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenbeeindiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedenbeeindigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(redenbeeindiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Redenbeeindiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRedenbeeindiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenbeeindiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedenbeeindigingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(redenbeeindiging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Redenbeeindiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRedenbeeindiging() throws Exception {
        // Initialize the database
        redenbeeindigingRepository.saveAndFlush(redenbeeindiging);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the redenbeeindiging
        restRedenbeeindigingMockMvc
            .perform(delete(ENTITY_API_URL_ID, redenbeeindiging.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return redenbeeindigingRepository.count();
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

    protected Redenbeeindiging getPersistedRedenbeeindiging(Redenbeeindiging redenbeeindiging) {
        return redenbeeindigingRepository.findById(redenbeeindiging.getId()).orElseThrow();
    }

    protected void assertPersistedRedenbeeindigingToMatchAllProperties(Redenbeeindiging expectedRedenbeeindiging) {
        assertRedenbeeindigingAllPropertiesEquals(expectedRedenbeeindiging, getPersistedRedenbeeindiging(expectedRedenbeeindiging));
    }

    protected void assertPersistedRedenbeeindigingToMatchUpdatableProperties(Redenbeeindiging expectedRedenbeeindiging) {
        assertRedenbeeindigingAllUpdatablePropertiesEquals(
            expectedRedenbeeindiging,
            getPersistedRedenbeeindiging(expectedRedenbeeindiging)
        );
    }
}
