package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.CultuuronbebouwdAsserts.*;
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
import nl.ritense.demo.domain.Cultuuronbebouwd;
import nl.ritense.demo.repository.CultuuronbebouwdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CultuuronbebouwdResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CultuuronbebouwdResourceIT {

    private static final String DEFAULT_CULTUURCODEONBEBOUWD = "AAAAAAAAAA";
    private static final String UPDATED_CULTUURCODEONBEBOUWD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cultuuronbebouwds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CultuuronbebouwdRepository cultuuronbebouwdRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCultuuronbebouwdMockMvc;

    private Cultuuronbebouwd cultuuronbebouwd;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cultuuronbebouwd createEntity(EntityManager em) {
        Cultuuronbebouwd cultuuronbebouwd = new Cultuuronbebouwd().cultuurcodeonbebouwd(DEFAULT_CULTUURCODEONBEBOUWD);
        return cultuuronbebouwd;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cultuuronbebouwd createUpdatedEntity(EntityManager em) {
        Cultuuronbebouwd cultuuronbebouwd = new Cultuuronbebouwd().cultuurcodeonbebouwd(UPDATED_CULTUURCODEONBEBOUWD);
        return cultuuronbebouwd;
    }

    @BeforeEach
    public void initTest() {
        cultuuronbebouwd = createEntity(em);
    }

    @Test
    @Transactional
    void createCultuuronbebouwd() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Cultuuronbebouwd
        var returnedCultuuronbebouwd = om.readValue(
            restCultuuronbebouwdMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cultuuronbebouwd)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Cultuuronbebouwd.class
        );

        // Validate the Cultuuronbebouwd in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCultuuronbebouwdUpdatableFieldsEquals(returnedCultuuronbebouwd, getPersistedCultuuronbebouwd(returnedCultuuronbebouwd));
    }

    @Test
    @Transactional
    void createCultuuronbebouwdWithExistingId() throws Exception {
        // Create the Cultuuronbebouwd with an existing ID
        cultuuronbebouwd.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCultuuronbebouwdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cultuuronbebouwd)))
            .andExpect(status().isBadRequest());

        // Validate the Cultuuronbebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCultuuronbebouwds() throws Exception {
        // Initialize the database
        cultuuronbebouwdRepository.saveAndFlush(cultuuronbebouwd);

        // Get all the cultuuronbebouwdList
        restCultuuronbebouwdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cultuuronbebouwd.getId().intValue())))
            .andExpect(jsonPath("$.[*].cultuurcodeonbebouwd").value(hasItem(DEFAULT_CULTUURCODEONBEBOUWD)));
    }

    @Test
    @Transactional
    void getCultuuronbebouwd() throws Exception {
        // Initialize the database
        cultuuronbebouwdRepository.saveAndFlush(cultuuronbebouwd);

        // Get the cultuuronbebouwd
        restCultuuronbebouwdMockMvc
            .perform(get(ENTITY_API_URL_ID, cultuuronbebouwd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cultuuronbebouwd.getId().intValue()))
            .andExpect(jsonPath("$.cultuurcodeonbebouwd").value(DEFAULT_CULTUURCODEONBEBOUWD));
    }

    @Test
    @Transactional
    void getNonExistingCultuuronbebouwd() throws Exception {
        // Get the cultuuronbebouwd
        restCultuuronbebouwdMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCultuuronbebouwd() throws Exception {
        // Initialize the database
        cultuuronbebouwdRepository.saveAndFlush(cultuuronbebouwd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cultuuronbebouwd
        Cultuuronbebouwd updatedCultuuronbebouwd = cultuuronbebouwdRepository.findById(cultuuronbebouwd.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCultuuronbebouwd are not directly saved in db
        em.detach(updatedCultuuronbebouwd);
        updatedCultuuronbebouwd.cultuurcodeonbebouwd(UPDATED_CULTUURCODEONBEBOUWD);

        restCultuuronbebouwdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCultuuronbebouwd.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCultuuronbebouwd))
            )
            .andExpect(status().isOk());

        // Validate the Cultuuronbebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCultuuronbebouwdToMatchAllProperties(updatedCultuuronbebouwd);
    }

    @Test
    @Transactional
    void putNonExistingCultuuronbebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuuronbebouwd.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCultuuronbebouwdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cultuuronbebouwd.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cultuuronbebouwd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cultuuronbebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCultuuronbebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuuronbebouwd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCultuuronbebouwdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cultuuronbebouwd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cultuuronbebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCultuuronbebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuuronbebouwd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCultuuronbebouwdMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cultuuronbebouwd)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cultuuronbebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCultuuronbebouwdWithPatch() throws Exception {
        // Initialize the database
        cultuuronbebouwdRepository.saveAndFlush(cultuuronbebouwd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cultuuronbebouwd using partial update
        Cultuuronbebouwd partialUpdatedCultuuronbebouwd = new Cultuuronbebouwd();
        partialUpdatedCultuuronbebouwd.setId(cultuuronbebouwd.getId());

        partialUpdatedCultuuronbebouwd.cultuurcodeonbebouwd(UPDATED_CULTUURCODEONBEBOUWD);

        restCultuuronbebouwdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCultuuronbebouwd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCultuuronbebouwd))
            )
            .andExpect(status().isOk());

        // Validate the Cultuuronbebouwd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCultuuronbebouwdUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCultuuronbebouwd, cultuuronbebouwd),
            getPersistedCultuuronbebouwd(cultuuronbebouwd)
        );
    }

    @Test
    @Transactional
    void fullUpdateCultuuronbebouwdWithPatch() throws Exception {
        // Initialize the database
        cultuuronbebouwdRepository.saveAndFlush(cultuuronbebouwd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cultuuronbebouwd using partial update
        Cultuuronbebouwd partialUpdatedCultuuronbebouwd = new Cultuuronbebouwd();
        partialUpdatedCultuuronbebouwd.setId(cultuuronbebouwd.getId());

        partialUpdatedCultuuronbebouwd.cultuurcodeonbebouwd(UPDATED_CULTUURCODEONBEBOUWD);

        restCultuuronbebouwdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCultuuronbebouwd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCultuuronbebouwd))
            )
            .andExpect(status().isOk());

        // Validate the Cultuuronbebouwd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCultuuronbebouwdUpdatableFieldsEquals(
            partialUpdatedCultuuronbebouwd,
            getPersistedCultuuronbebouwd(partialUpdatedCultuuronbebouwd)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCultuuronbebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuuronbebouwd.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCultuuronbebouwdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cultuuronbebouwd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cultuuronbebouwd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cultuuronbebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCultuuronbebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuuronbebouwd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCultuuronbebouwdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cultuuronbebouwd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cultuuronbebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCultuuronbebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuuronbebouwd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCultuuronbebouwdMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cultuuronbebouwd)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cultuuronbebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCultuuronbebouwd() throws Exception {
        // Initialize the database
        cultuuronbebouwdRepository.saveAndFlush(cultuuronbebouwd);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cultuuronbebouwd
        restCultuuronbebouwdMockMvc
            .perform(delete(ENTITY_API_URL_ID, cultuuronbebouwd.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cultuuronbebouwdRepository.count();
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

    protected Cultuuronbebouwd getPersistedCultuuronbebouwd(Cultuuronbebouwd cultuuronbebouwd) {
        return cultuuronbebouwdRepository.findById(cultuuronbebouwd.getId()).orElseThrow();
    }

    protected void assertPersistedCultuuronbebouwdToMatchAllProperties(Cultuuronbebouwd expectedCultuuronbebouwd) {
        assertCultuuronbebouwdAllPropertiesEquals(expectedCultuuronbebouwd, getPersistedCultuuronbebouwd(expectedCultuuronbebouwd));
    }

    protected void assertPersistedCultuuronbebouwdToMatchUpdatableProperties(Cultuuronbebouwd expectedCultuuronbebouwd) {
        assertCultuuronbebouwdAllUpdatablePropertiesEquals(
            expectedCultuuronbebouwd,
            getPersistedCultuuronbebouwd(expectedCultuuronbebouwd)
        );
    }
}
