package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SbiactiviteitvestigingAsserts.*;
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
import nl.ritense.demo.domain.Sbiactiviteitvestiging;
import nl.ritense.demo.repository.SbiactiviteitvestigingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SbiactiviteitvestigingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SbiactiviteitvestigingResourceIT {

    private static final String DEFAULT_INDICATIEHOOFDACTIVITEIT = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEHOOFDACTIVITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_SBICODE = "AAAAAAAAAA";
    private static final String UPDATED_SBICODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sbiactiviteitvestigings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SbiactiviteitvestigingRepository sbiactiviteitvestigingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSbiactiviteitvestigingMockMvc;

    private Sbiactiviteitvestiging sbiactiviteitvestiging;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sbiactiviteitvestiging createEntity(EntityManager em) {
        Sbiactiviteitvestiging sbiactiviteitvestiging = new Sbiactiviteitvestiging()
            .indicatiehoofdactiviteit(DEFAULT_INDICATIEHOOFDACTIVITEIT)
            .sbicode(DEFAULT_SBICODE);
        return sbiactiviteitvestiging;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sbiactiviteitvestiging createUpdatedEntity(EntityManager em) {
        Sbiactiviteitvestiging sbiactiviteitvestiging = new Sbiactiviteitvestiging()
            .indicatiehoofdactiviteit(UPDATED_INDICATIEHOOFDACTIVITEIT)
            .sbicode(UPDATED_SBICODE);
        return sbiactiviteitvestiging;
    }

    @BeforeEach
    public void initTest() {
        sbiactiviteitvestiging = createEntity(em);
    }

    @Test
    @Transactional
    void createSbiactiviteitvestiging() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sbiactiviteitvestiging
        var returnedSbiactiviteitvestiging = om.readValue(
            restSbiactiviteitvestigingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sbiactiviteitvestiging)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Sbiactiviteitvestiging.class
        );

        // Validate the Sbiactiviteitvestiging in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSbiactiviteitvestigingUpdatableFieldsEquals(
            returnedSbiactiviteitvestiging,
            getPersistedSbiactiviteitvestiging(returnedSbiactiviteitvestiging)
        );
    }

    @Test
    @Transactional
    void createSbiactiviteitvestigingWithExistingId() throws Exception {
        // Create the Sbiactiviteitvestiging with an existing ID
        sbiactiviteitvestiging.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSbiactiviteitvestigingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sbiactiviteitvestiging)))
            .andExpect(status().isBadRequest());

        // Validate the Sbiactiviteitvestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSbiactiviteitvestigings() throws Exception {
        // Initialize the database
        sbiactiviteitvestigingRepository.saveAndFlush(sbiactiviteitvestiging);

        // Get all the sbiactiviteitvestigingList
        restSbiactiviteitvestigingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sbiactiviteitvestiging.getId().intValue())))
            .andExpect(jsonPath("$.[*].indicatiehoofdactiviteit").value(hasItem(DEFAULT_INDICATIEHOOFDACTIVITEIT)))
            .andExpect(jsonPath("$.[*].sbicode").value(hasItem(DEFAULT_SBICODE)));
    }

    @Test
    @Transactional
    void getSbiactiviteitvestiging() throws Exception {
        // Initialize the database
        sbiactiviteitvestigingRepository.saveAndFlush(sbiactiviteitvestiging);

        // Get the sbiactiviteitvestiging
        restSbiactiviteitvestigingMockMvc
            .perform(get(ENTITY_API_URL_ID, sbiactiviteitvestiging.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sbiactiviteitvestiging.getId().intValue()))
            .andExpect(jsonPath("$.indicatiehoofdactiviteit").value(DEFAULT_INDICATIEHOOFDACTIVITEIT))
            .andExpect(jsonPath("$.sbicode").value(DEFAULT_SBICODE));
    }

    @Test
    @Transactional
    void getNonExistingSbiactiviteitvestiging() throws Exception {
        // Get the sbiactiviteitvestiging
        restSbiactiviteitvestigingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSbiactiviteitvestiging() throws Exception {
        // Initialize the database
        sbiactiviteitvestigingRepository.saveAndFlush(sbiactiviteitvestiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sbiactiviteitvestiging
        Sbiactiviteitvestiging updatedSbiactiviteitvestiging = sbiactiviteitvestigingRepository
            .findById(sbiactiviteitvestiging.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSbiactiviteitvestiging are not directly saved in db
        em.detach(updatedSbiactiviteitvestiging);
        updatedSbiactiviteitvestiging.indicatiehoofdactiviteit(UPDATED_INDICATIEHOOFDACTIVITEIT).sbicode(UPDATED_SBICODE);

        restSbiactiviteitvestigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSbiactiviteitvestiging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSbiactiviteitvestiging))
            )
            .andExpect(status().isOk());

        // Validate the Sbiactiviteitvestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSbiactiviteitvestigingToMatchAllProperties(updatedSbiactiviteitvestiging);
    }

    @Test
    @Transactional
    void putNonExistingSbiactiviteitvestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sbiactiviteitvestiging.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSbiactiviteitvestigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sbiactiviteitvestiging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sbiactiviteitvestiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sbiactiviteitvestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSbiactiviteitvestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sbiactiviteitvestiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSbiactiviteitvestigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sbiactiviteitvestiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sbiactiviteitvestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSbiactiviteitvestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sbiactiviteitvestiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSbiactiviteitvestigingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sbiactiviteitvestiging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sbiactiviteitvestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSbiactiviteitvestigingWithPatch() throws Exception {
        // Initialize the database
        sbiactiviteitvestigingRepository.saveAndFlush(sbiactiviteitvestiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sbiactiviteitvestiging using partial update
        Sbiactiviteitvestiging partialUpdatedSbiactiviteitvestiging = new Sbiactiviteitvestiging();
        partialUpdatedSbiactiviteitvestiging.setId(sbiactiviteitvestiging.getId());

        partialUpdatedSbiactiviteitvestiging.indicatiehoofdactiviteit(UPDATED_INDICATIEHOOFDACTIVITEIT).sbicode(UPDATED_SBICODE);

        restSbiactiviteitvestigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSbiactiviteitvestiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSbiactiviteitvestiging))
            )
            .andExpect(status().isOk());

        // Validate the Sbiactiviteitvestiging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSbiactiviteitvestigingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSbiactiviteitvestiging, sbiactiviteitvestiging),
            getPersistedSbiactiviteitvestiging(sbiactiviteitvestiging)
        );
    }

    @Test
    @Transactional
    void fullUpdateSbiactiviteitvestigingWithPatch() throws Exception {
        // Initialize the database
        sbiactiviteitvestigingRepository.saveAndFlush(sbiactiviteitvestiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sbiactiviteitvestiging using partial update
        Sbiactiviteitvestiging partialUpdatedSbiactiviteitvestiging = new Sbiactiviteitvestiging();
        partialUpdatedSbiactiviteitvestiging.setId(sbiactiviteitvestiging.getId());

        partialUpdatedSbiactiviteitvestiging.indicatiehoofdactiviteit(UPDATED_INDICATIEHOOFDACTIVITEIT).sbicode(UPDATED_SBICODE);

        restSbiactiviteitvestigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSbiactiviteitvestiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSbiactiviteitvestiging))
            )
            .andExpect(status().isOk());

        // Validate the Sbiactiviteitvestiging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSbiactiviteitvestigingUpdatableFieldsEquals(
            partialUpdatedSbiactiviteitvestiging,
            getPersistedSbiactiviteitvestiging(partialUpdatedSbiactiviteitvestiging)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSbiactiviteitvestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sbiactiviteitvestiging.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSbiactiviteitvestigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sbiactiviteitvestiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sbiactiviteitvestiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sbiactiviteitvestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSbiactiviteitvestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sbiactiviteitvestiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSbiactiviteitvestigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sbiactiviteitvestiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sbiactiviteitvestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSbiactiviteitvestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sbiactiviteitvestiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSbiactiviteitvestigingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sbiactiviteitvestiging))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sbiactiviteitvestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSbiactiviteitvestiging() throws Exception {
        // Initialize the database
        sbiactiviteitvestigingRepository.saveAndFlush(sbiactiviteitvestiging);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sbiactiviteitvestiging
        restSbiactiviteitvestigingMockMvc
            .perform(delete(ENTITY_API_URL_ID, sbiactiviteitvestiging.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sbiactiviteitvestigingRepository.count();
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

    protected Sbiactiviteitvestiging getPersistedSbiactiviteitvestiging(Sbiactiviteitvestiging sbiactiviteitvestiging) {
        return sbiactiviteitvestigingRepository.findById(sbiactiviteitvestiging.getId()).orElseThrow();
    }

    protected void assertPersistedSbiactiviteitvestigingToMatchAllProperties(Sbiactiviteitvestiging expectedSbiactiviteitvestiging) {
        assertSbiactiviteitvestigingAllPropertiesEquals(
            expectedSbiactiviteitvestiging,
            getPersistedSbiactiviteitvestiging(expectedSbiactiviteitvestiging)
        );
    }

    protected void assertPersistedSbiactiviteitvestigingToMatchUpdatableProperties(Sbiactiviteitvestiging expectedSbiactiviteitvestiging) {
        assertSbiactiviteitvestigingAllUpdatablePropertiesEquals(
            expectedSbiactiviteitvestiging,
            getPersistedSbiactiviteitvestiging(expectedSbiactiviteitvestiging)
        );
    }
}
