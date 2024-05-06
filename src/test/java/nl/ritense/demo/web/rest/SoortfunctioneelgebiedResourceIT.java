package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SoortfunctioneelgebiedAsserts.*;
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
import nl.ritense.demo.domain.Soortfunctioneelgebied;
import nl.ritense.demo.repository.SoortfunctioneelgebiedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SoortfunctioneelgebiedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SoortfunctioneelgebiedResourceIT {

    private static final String DEFAULT_INDICATIEPLUSBRPOPULATIE = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEPLUSBRPOPULATIE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEFUNCTIONEELGEBIED = "AAAAAAAAAA";
    private static final String UPDATED_TYPEFUNCTIONEELGEBIED = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/soortfunctioneelgebieds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SoortfunctioneelgebiedRepository soortfunctioneelgebiedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoortfunctioneelgebiedMockMvc;

    private Soortfunctioneelgebied soortfunctioneelgebied;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soortfunctioneelgebied createEntity(EntityManager em) {
        Soortfunctioneelgebied soortfunctioneelgebied = new Soortfunctioneelgebied()
            .indicatieplusbrpopulatie(DEFAULT_INDICATIEPLUSBRPOPULATIE)
            .typefunctioneelgebied(DEFAULT_TYPEFUNCTIONEELGEBIED);
        return soortfunctioneelgebied;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soortfunctioneelgebied createUpdatedEntity(EntityManager em) {
        Soortfunctioneelgebied soortfunctioneelgebied = new Soortfunctioneelgebied()
            .indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE)
            .typefunctioneelgebied(UPDATED_TYPEFUNCTIONEELGEBIED);
        return soortfunctioneelgebied;
    }

    @BeforeEach
    public void initTest() {
        soortfunctioneelgebied = createEntity(em);
    }

    @Test
    @Transactional
    void createSoortfunctioneelgebied() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Soortfunctioneelgebied
        var returnedSoortfunctioneelgebied = om.readValue(
            restSoortfunctioneelgebiedMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortfunctioneelgebied)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Soortfunctioneelgebied.class
        );

        // Validate the Soortfunctioneelgebied in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSoortfunctioneelgebiedUpdatableFieldsEquals(
            returnedSoortfunctioneelgebied,
            getPersistedSoortfunctioneelgebied(returnedSoortfunctioneelgebied)
        );
    }

    @Test
    @Transactional
    void createSoortfunctioneelgebiedWithExistingId() throws Exception {
        // Create the Soortfunctioneelgebied with an existing ID
        soortfunctioneelgebied.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoortfunctioneelgebiedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortfunctioneelgebied)))
            .andExpect(status().isBadRequest());

        // Validate the Soortfunctioneelgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSoortfunctioneelgebieds() throws Exception {
        // Initialize the database
        soortfunctioneelgebiedRepository.saveAndFlush(soortfunctioneelgebied);

        // Get all the soortfunctioneelgebiedList
        restSoortfunctioneelgebiedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soortfunctioneelgebied.getId().intValue())))
            .andExpect(jsonPath("$.[*].indicatieplusbrpopulatie").value(hasItem(DEFAULT_INDICATIEPLUSBRPOPULATIE)))
            .andExpect(jsonPath("$.[*].typefunctioneelgebied").value(hasItem(DEFAULT_TYPEFUNCTIONEELGEBIED)));
    }

    @Test
    @Transactional
    void getSoortfunctioneelgebied() throws Exception {
        // Initialize the database
        soortfunctioneelgebiedRepository.saveAndFlush(soortfunctioneelgebied);

        // Get the soortfunctioneelgebied
        restSoortfunctioneelgebiedMockMvc
            .perform(get(ENTITY_API_URL_ID, soortfunctioneelgebied.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(soortfunctioneelgebied.getId().intValue()))
            .andExpect(jsonPath("$.indicatieplusbrpopulatie").value(DEFAULT_INDICATIEPLUSBRPOPULATIE))
            .andExpect(jsonPath("$.typefunctioneelgebied").value(DEFAULT_TYPEFUNCTIONEELGEBIED));
    }

    @Test
    @Transactional
    void getNonExistingSoortfunctioneelgebied() throws Exception {
        // Get the soortfunctioneelgebied
        restSoortfunctioneelgebiedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSoortfunctioneelgebied() throws Exception {
        // Initialize the database
        soortfunctioneelgebiedRepository.saveAndFlush(soortfunctioneelgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortfunctioneelgebied
        Soortfunctioneelgebied updatedSoortfunctioneelgebied = soortfunctioneelgebiedRepository
            .findById(soortfunctioneelgebied.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSoortfunctioneelgebied are not directly saved in db
        em.detach(updatedSoortfunctioneelgebied);
        updatedSoortfunctioneelgebied
            .indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE)
            .typefunctioneelgebied(UPDATED_TYPEFUNCTIONEELGEBIED);

        restSoortfunctioneelgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSoortfunctioneelgebied.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSoortfunctioneelgebied))
            )
            .andExpect(status().isOk());

        // Validate the Soortfunctioneelgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSoortfunctioneelgebiedToMatchAllProperties(updatedSoortfunctioneelgebied);
    }

    @Test
    @Transactional
    void putNonExistingSoortfunctioneelgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortfunctioneelgebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoortfunctioneelgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soortfunctioneelgebied.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(soortfunctioneelgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortfunctioneelgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSoortfunctioneelgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortfunctioneelgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortfunctioneelgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(soortfunctioneelgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortfunctioneelgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSoortfunctioneelgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortfunctioneelgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortfunctioneelgebiedMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortfunctioneelgebied)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soortfunctioneelgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSoortfunctioneelgebiedWithPatch() throws Exception {
        // Initialize the database
        soortfunctioneelgebiedRepository.saveAndFlush(soortfunctioneelgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortfunctioneelgebied using partial update
        Soortfunctioneelgebied partialUpdatedSoortfunctioneelgebied = new Soortfunctioneelgebied();
        partialUpdatedSoortfunctioneelgebied.setId(soortfunctioneelgebied.getId());

        partialUpdatedSoortfunctioneelgebied
            .indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE)
            .typefunctioneelgebied(UPDATED_TYPEFUNCTIONEELGEBIED);

        restSoortfunctioneelgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoortfunctioneelgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSoortfunctioneelgebied))
            )
            .andExpect(status().isOk());

        // Validate the Soortfunctioneelgebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSoortfunctioneelgebiedUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSoortfunctioneelgebied, soortfunctioneelgebied),
            getPersistedSoortfunctioneelgebied(soortfunctioneelgebied)
        );
    }

    @Test
    @Transactional
    void fullUpdateSoortfunctioneelgebiedWithPatch() throws Exception {
        // Initialize the database
        soortfunctioneelgebiedRepository.saveAndFlush(soortfunctioneelgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortfunctioneelgebied using partial update
        Soortfunctioneelgebied partialUpdatedSoortfunctioneelgebied = new Soortfunctioneelgebied();
        partialUpdatedSoortfunctioneelgebied.setId(soortfunctioneelgebied.getId());

        partialUpdatedSoortfunctioneelgebied
            .indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE)
            .typefunctioneelgebied(UPDATED_TYPEFUNCTIONEELGEBIED);

        restSoortfunctioneelgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoortfunctioneelgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSoortfunctioneelgebied))
            )
            .andExpect(status().isOk());

        // Validate the Soortfunctioneelgebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSoortfunctioneelgebiedUpdatableFieldsEquals(
            partialUpdatedSoortfunctioneelgebied,
            getPersistedSoortfunctioneelgebied(partialUpdatedSoortfunctioneelgebied)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSoortfunctioneelgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortfunctioneelgebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoortfunctioneelgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, soortfunctioneelgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(soortfunctioneelgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortfunctioneelgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSoortfunctioneelgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortfunctioneelgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortfunctioneelgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(soortfunctioneelgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortfunctioneelgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSoortfunctioneelgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortfunctioneelgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortfunctioneelgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(soortfunctioneelgebied))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soortfunctioneelgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSoortfunctioneelgebied() throws Exception {
        // Initialize the database
        soortfunctioneelgebiedRepository.saveAndFlush(soortfunctioneelgebied);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the soortfunctioneelgebied
        restSoortfunctioneelgebiedMockMvc
            .perform(delete(ENTITY_API_URL_ID, soortfunctioneelgebied.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return soortfunctioneelgebiedRepository.count();
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

    protected Soortfunctioneelgebied getPersistedSoortfunctioneelgebied(Soortfunctioneelgebied soortfunctioneelgebied) {
        return soortfunctioneelgebiedRepository.findById(soortfunctioneelgebied.getId()).orElseThrow();
    }

    protected void assertPersistedSoortfunctioneelgebiedToMatchAllProperties(Soortfunctioneelgebied expectedSoortfunctioneelgebied) {
        assertSoortfunctioneelgebiedAllPropertiesEquals(
            expectedSoortfunctioneelgebied,
            getPersistedSoortfunctioneelgebied(expectedSoortfunctioneelgebied)
        );
    }

    protected void assertPersistedSoortfunctioneelgebiedToMatchUpdatableProperties(Soortfunctioneelgebied expectedSoortfunctioneelgebied) {
        assertSoortfunctioneelgebiedAllUpdatablePropertiesEquals(
            expectedSoortfunctioneelgebied,
            getPersistedSoortfunctioneelgebied(expectedSoortfunctioneelgebied)
        );
    }
}
