package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.CombibonAsserts.*;
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
import nl.ritense.demo.domain.Combibon;
import nl.ritense.demo.repository.CombibonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CombibonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CombibonResourceIT {

    private static final String DEFAULT_SANCTIE = "AAAAAAAAAA";
    private static final String UPDATED_SANCTIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/combibons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CombibonRepository combibonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCombibonMockMvc;

    private Combibon combibon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Combibon createEntity(EntityManager em) {
        Combibon combibon = new Combibon().sanctie(DEFAULT_SANCTIE);
        return combibon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Combibon createUpdatedEntity(EntityManager em) {
        Combibon combibon = new Combibon().sanctie(UPDATED_SANCTIE);
        return combibon;
    }

    @BeforeEach
    public void initTest() {
        combibon = createEntity(em);
    }

    @Test
    @Transactional
    void createCombibon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Combibon
        var returnedCombibon = om.readValue(
            restCombibonMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(combibon)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Combibon.class
        );

        // Validate the Combibon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCombibonUpdatableFieldsEquals(returnedCombibon, getPersistedCombibon(returnedCombibon));
    }

    @Test
    @Transactional
    void createCombibonWithExistingId() throws Exception {
        // Create the Combibon with an existing ID
        combibon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCombibonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(combibon)))
            .andExpect(status().isBadRequest());

        // Validate the Combibon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCombibons() throws Exception {
        // Initialize the database
        combibonRepository.saveAndFlush(combibon);

        // Get all the combibonList
        restCombibonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(combibon.getId().intValue())))
            .andExpect(jsonPath("$.[*].sanctie").value(hasItem(DEFAULT_SANCTIE)));
    }

    @Test
    @Transactional
    void getCombibon() throws Exception {
        // Initialize the database
        combibonRepository.saveAndFlush(combibon);

        // Get the combibon
        restCombibonMockMvc
            .perform(get(ENTITY_API_URL_ID, combibon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(combibon.getId().intValue()))
            .andExpect(jsonPath("$.sanctie").value(DEFAULT_SANCTIE));
    }

    @Test
    @Transactional
    void getNonExistingCombibon() throws Exception {
        // Get the combibon
        restCombibonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCombibon() throws Exception {
        // Initialize the database
        combibonRepository.saveAndFlush(combibon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the combibon
        Combibon updatedCombibon = combibonRepository.findById(combibon.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCombibon are not directly saved in db
        em.detach(updatedCombibon);
        updatedCombibon.sanctie(UPDATED_SANCTIE);

        restCombibonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCombibon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCombibon))
            )
            .andExpect(status().isOk());

        // Validate the Combibon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCombibonToMatchAllProperties(updatedCombibon);
    }

    @Test
    @Transactional
    void putNonExistingCombibon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        combibon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCombibonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, combibon.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(combibon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Combibon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCombibon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        combibon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCombibonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(combibon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Combibon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCombibon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        combibon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCombibonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(combibon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Combibon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCombibonWithPatch() throws Exception {
        // Initialize the database
        combibonRepository.saveAndFlush(combibon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the combibon using partial update
        Combibon partialUpdatedCombibon = new Combibon();
        partialUpdatedCombibon.setId(combibon.getId());

        restCombibonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCombibon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCombibon))
            )
            .andExpect(status().isOk());

        // Validate the Combibon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCombibonUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCombibon, combibon), getPersistedCombibon(combibon));
    }

    @Test
    @Transactional
    void fullUpdateCombibonWithPatch() throws Exception {
        // Initialize the database
        combibonRepository.saveAndFlush(combibon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the combibon using partial update
        Combibon partialUpdatedCombibon = new Combibon();
        partialUpdatedCombibon.setId(combibon.getId());

        partialUpdatedCombibon.sanctie(UPDATED_SANCTIE);

        restCombibonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCombibon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCombibon))
            )
            .andExpect(status().isOk());

        // Validate the Combibon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCombibonUpdatableFieldsEquals(partialUpdatedCombibon, getPersistedCombibon(partialUpdatedCombibon));
    }

    @Test
    @Transactional
    void patchNonExistingCombibon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        combibon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCombibonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, combibon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(combibon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Combibon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCombibon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        combibon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCombibonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(combibon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Combibon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCombibon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        combibon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCombibonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(combibon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Combibon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCombibon() throws Exception {
        // Initialize the database
        combibonRepository.saveAndFlush(combibon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the combibon
        restCombibonMockMvc
            .perform(delete(ENTITY_API_URL_ID, combibon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return combibonRepository.count();
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

    protected Combibon getPersistedCombibon(Combibon combibon) {
        return combibonRepository.findById(combibon.getId()).orElseThrow();
    }

    protected void assertPersistedCombibonToMatchAllProperties(Combibon expectedCombibon) {
        assertCombibonAllPropertiesEquals(expectedCombibon, getPersistedCombibon(expectedCombibon));
    }

    protected void assertPersistedCombibonToMatchUpdatableProperties(Combibon expectedCombibon) {
        assertCombibonAllUpdatablePropertiesEquals(expectedCombibon, getPersistedCombibon(expectedCombibon));
    }
}
