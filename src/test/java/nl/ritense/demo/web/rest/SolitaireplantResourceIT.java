package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SolitaireplantAsserts.*;
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
import nl.ritense.demo.domain.Solitaireplant;
import nl.ritense.demo.repository.SolitaireplantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SolitaireplantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SolitaireplantResourceIT {

    private static final String DEFAULT_HOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/solitaireplants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SolitaireplantRepository solitaireplantRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSolitaireplantMockMvc;

    private Solitaireplant solitaireplant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solitaireplant createEntity(EntityManager em) {
        Solitaireplant solitaireplant = new Solitaireplant().hoogte(DEFAULT_HOOGTE).type(DEFAULT_TYPE);
        return solitaireplant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solitaireplant createUpdatedEntity(EntityManager em) {
        Solitaireplant solitaireplant = new Solitaireplant().hoogte(UPDATED_HOOGTE).type(UPDATED_TYPE);
        return solitaireplant;
    }

    @BeforeEach
    public void initTest() {
        solitaireplant = createEntity(em);
    }

    @Test
    @Transactional
    void createSolitaireplant() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Solitaireplant
        var returnedSolitaireplant = om.readValue(
            restSolitaireplantMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(solitaireplant)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Solitaireplant.class
        );

        // Validate the Solitaireplant in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSolitaireplantUpdatableFieldsEquals(returnedSolitaireplant, getPersistedSolitaireplant(returnedSolitaireplant));
    }

    @Test
    @Transactional
    void createSolitaireplantWithExistingId() throws Exception {
        // Create the Solitaireplant with an existing ID
        solitaireplant.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolitaireplantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(solitaireplant)))
            .andExpect(status().isBadRequest());

        // Validate the Solitaireplant in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSolitaireplants() throws Exception {
        // Initialize the database
        solitaireplantRepository.saveAndFlush(solitaireplant);

        // Get all the solitaireplantList
        restSolitaireplantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solitaireplant.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getSolitaireplant() throws Exception {
        // Initialize the database
        solitaireplantRepository.saveAndFlush(solitaireplant);

        // Get the solitaireplant
        restSolitaireplantMockMvc
            .perform(get(ENTITY_API_URL_ID, solitaireplant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(solitaireplant.getId().intValue()))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingSolitaireplant() throws Exception {
        // Get the solitaireplant
        restSolitaireplantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSolitaireplant() throws Exception {
        // Initialize the database
        solitaireplantRepository.saveAndFlush(solitaireplant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the solitaireplant
        Solitaireplant updatedSolitaireplant = solitaireplantRepository.findById(solitaireplant.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSolitaireplant are not directly saved in db
        em.detach(updatedSolitaireplant);
        updatedSolitaireplant.hoogte(UPDATED_HOOGTE).type(UPDATED_TYPE);

        restSolitaireplantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSolitaireplant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSolitaireplant))
            )
            .andExpect(status().isOk());

        // Validate the Solitaireplant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSolitaireplantToMatchAllProperties(updatedSolitaireplant);
    }

    @Test
    @Transactional
    void putNonExistingSolitaireplant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        solitaireplant.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolitaireplantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, solitaireplant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(solitaireplant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Solitaireplant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSolitaireplant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        solitaireplant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolitaireplantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(solitaireplant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Solitaireplant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSolitaireplant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        solitaireplant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolitaireplantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(solitaireplant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Solitaireplant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSolitaireplantWithPatch() throws Exception {
        // Initialize the database
        solitaireplantRepository.saveAndFlush(solitaireplant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the solitaireplant using partial update
        Solitaireplant partialUpdatedSolitaireplant = new Solitaireplant();
        partialUpdatedSolitaireplant.setId(solitaireplant.getId());

        partialUpdatedSolitaireplant.type(UPDATED_TYPE);

        restSolitaireplantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSolitaireplant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSolitaireplant))
            )
            .andExpect(status().isOk());

        // Validate the Solitaireplant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSolitaireplantUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSolitaireplant, solitaireplant),
            getPersistedSolitaireplant(solitaireplant)
        );
    }

    @Test
    @Transactional
    void fullUpdateSolitaireplantWithPatch() throws Exception {
        // Initialize the database
        solitaireplantRepository.saveAndFlush(solitaireplant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the solitaireplant using partial update
        Solitaireplant partialUpdatedSolitaireplant = new Solitaireplant();
        partialUpdatedSolitaireplant.setId(solitaireplant.getId());

        partialUpdatedSolitaireplant.hoogte(UPDATED_HOOGTE).type(UPDATED_TYPE);

        restSolitaireplantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSolitaireplant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSolitaireplant))
            )
            .andExpect(status().isOk());

        // Validate the Solitaireplant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSolitaireplantUpdatableFieldsEquals(partialUpdatedSolitaireplant, getPersistedSolitaireplant(partialUpdatedSolitaireplant));
    }

    @Test
    @Transactional
    void patchNonExistingSolitaireplant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        solitaireplant.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolitaireplantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, solitaireplant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(solitaireplant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Solitaireplant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSolitaireplant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        solitaireplant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolitaireplantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(solitaireplant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Solitaireplant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSolitaireplant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        solitaireplant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolitaireplantMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(solitaireplant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Solitaireplant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSolitaireplant() throws Exception {
        // Initialize the database
        solitaireplantRepository.saveAndFlush(solitaireplant);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the solitaireplant
        restSolitaireplantMockMvc
            .perform(delete(ENTITY_API_URL_ID, solitaireplant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return solitaireplantRepository.count();
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

    protected Solitaireplant getPersistedSolitaireplant(Solitaireplant solitaireplant) {
        return solitaireplantRepository.findById(solitaireplant.getId()).orElseThrow();
    }

    protected void assertPersistedSolitaireplantToMatchAllProperties(Solitaireplant expectedSolitaireplant) {
        assertSolitaireplantAllPropertiesEquals(expectedSolitaireplant, getPersistedSolitaireplant(expectedSolitaireplant));
    }

    protected void assertPersistedSolitaireplantToMatchUpdatableProperties(Solitaireplant expectedSolitaireplant) {
        assertSolitaireplantAllUpdatablePropertiesEquals(expectedSolitaireplant, getPersistedSolitaireplant(expectedSolitaireplant));
    }
}
