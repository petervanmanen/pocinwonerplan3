package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SoortoverigbouwwerkAsserts.*;
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
import nl.ritense.demo.domain.Soortoverigbouwwerk;
import nl.ritense.demo.repository.SoortoverigbouwwerkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SoortoverigbouwwerkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SoortoverigbouwwerkResourceIT {

    private static final String DEFAULT_INDICATIEPLUSBRPOPULATIE = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEPLUSBRPOPULATIE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEOVERIGBOUWWERK = "AAAAAAAAAA";
    private static final String UPDATED_TYPEOVERIGBOUWWERK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/soortoverigbouwwerks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SoortoverigbouwwerkRepository soortoverigbouwwerkRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoortoverigbouwwerkMockMvc;

    private Soortoverigbouwwerk soortoverigbouwwerk;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soortoverigbouwwerk createEntity(EntityManager em) {
        Soortoverigbouwwerk soortoverigbouwwerk = new Soortoverigbouwwerk()
            .indicatieplusbrpopulatie(DEFAULT_INDICATIEPLUSBRPOPULATIE)
            .typeoverigbouwwerk(DEFAULT_TYPEOVERIGBOUWWERK);
        return soortoverigbouwwerk;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soortoverigbouwwerk createUpdatedEntity(EntityManager em) {
        Soortoverigbouwwerk soortoverigbouwwerk = new Soortoverigbouwwerk()
            .indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE)
            .typeoverigbouwwerk(UPDATED_TYPEOVERIGBOUWWERK);
        return soortoverigbouwwerk;
    }

    @BeforeEach
    public void initTest() {
        soortoverigbouwwerk = createEntity(em);
    }

    @Test
    @Transactional
    void createSoortoverigbouwwerk() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Soortoverigbouwwerk
        var returnedSoortoverigbouwwerk = om.readValue(
            restSoortoverigbouwwerkMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortoverigbouwwerk)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Soortoverigbouwwerk.class
        );

        // Validate the Soortoverigbouwwerk in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSoortoverigbouwwerkUpdatableFieldsEquals(
            returnedSoortoverigbouwwerk,
            getPersistedSoortoverigbouwwerk(returnedSoortoverigbouwwerk)
        );
    }

    @Test
    @Transactional
    void createSoortoverigbouwwerkWithExistingId() throws Exception {
        // Create the Soortoverigbouwwerk with an existing ID
        soortoverigbouwwerk.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoortoverigbouwwerkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortoverigbouwwerk)))
            .andExpect(status().isBadRequest());

        // Validate the Soortoverigbouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSoortoverigbouwwerks() throws Exception {
        // Initialize the database
        soortoverigbouwwerkRepository.saveAndFlush(soortoverigbouwwerk);

        // Get all the soortoverigbouwwerkList
        restSoortoverigbouwwerkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soortoverigbouwwerk.getId().intValue())))
            .andExpect(jsonPath("$.[*].indicatieplusbrpopulatie").value(hasItem(DEFAULT_INDICATIEPLUSBRPOPULATIE)))
            .andExpect(jsonPath("$.[*].typeoverigbouwwerk").value(hasItem(DEFAULT_TYPEOVERIGBOUWWERK)));
    }

    @Test
    @Transactional
    void getSoortoverigbouwwerk() throws Exception {
        // Initialize the database
        soortoverigbouwwerkRepository.saveAndFlush(soortoverigbouwwerk);

        // Get the soortoverigbouwwerk
        restSoortoverigbouwwerkMockMvc
            .perform(get(ENTITY_API_URL_ID, soortoverigbouwwerk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(soortoverigbouwwerk.getId().intValue()))
            .andExpect(jsonPath("$.indicatieplusbrpopulatie").value(DEFAULT_INDICATIEPLUSBRPOPULATIE))
            .andExpect(jsonPath("$.typeoverigbouwwerk").value(DEFAULT_TYPEOVERIGBOUWWERK));
    }

    @Test
    @Transactional
    void getNonExistingSoortoverigbouwwerk() throws Exception {
        // Get the soortoverigbouwwerk
        restSoortoverigbouwwerkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSoortoverigbouwwerk() throws Exception {
        // Initialize the database
        soortoverigbouwwerkRepository.saveAndFlush(soortoverigbouwwerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortoverigbouwwerk
        Soortoverigbouwwerk updatedSoortoverigbouwwerk = soortoverigbouwwerkRepository.findById(soortoverigbouwwerk.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSoortoverigbouwwerk are not directly saved in db
        em.detach(updatedSoortoverigbouwwerk);
        updatedSoortoverigbouwwerk
            .indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE)
            .typeoverigbouwwerk(UPDATED_TYPEOVERIGBOUWWERK);

        restSoortoverigbouwwerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSoortoverigbouwwerk.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSoortoverigbouwwerk))
            )
            .andExpect(status().isOk());

        // Validate the Soortoverigbouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSoortoverigbouwwerkToMatchAllProperties(updatedSoortoverigbouwwerk);
    }

    @Test
    @Transactional
    void putNonExistingSoortoverigbouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortoverigbouwwerk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoortoverigbouwwerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soortoverigbouwwerk.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(soortoverigbouwwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortoverigbouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSoortoverigbouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortoverigbouwwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortoverigbouwwerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(soortoverigbouwwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortoverigbouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSoortoverigbouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortoverigbouwwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortoverigbouwwerkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortoverigbouwwerk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soortoverigbouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSoortoverigbouwwerkWithPatch() throws Exception {
        // Initialize the database
        soortoverigbouwwerkRepository.saveAndFlush(soortoverigbouwwerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortoverigbouwwerk using partial update
        Soortoverigbouwwerk partialUpdatedSoortoverigbouwwerk = new Soortoverigbouwwerk();
        partialUpdatedSoortoverigbouwwerk.setId(soortoverigbouwwerk.getId());

        partialUpdatedSoortoverigbouwwerk.indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE);

        restSoortoverigbouwwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoortoverigbouwwerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSoortoverigbouwwerk))
            )
            .andExpect(status().isOk());

        // Validate the Soortoverigbouwwerk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSoortoverigbouwwerkUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSoortoverigbouwwerk, soortoverigbouwwerk),
            getPersistedSoortoverigbouwwerk(soortoverigbouwwerk)
        );
    }

    @Test
    @Transactional
    void fullUpdateSoortoverigbouwwerkWithPatch() throws Exception {
        // Initialize the database
        soortoverigbouwwerkRepository.saveAndFlush(soortoverigbouwwerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortoverigbouwwerk using partial update
        Soortoverigbouwwerk partialUpdatedSoortoverigbouwwerk = new Soortoverigbouwwerk();
        partialUpdatedSoortoverigbouwwerk.setId(soortoverigbouwwerk.getId());

        partialUpdatedSoortoverigbouwwerk
            .indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE)
            .typeoverigbouwwerk(UPDATED_TYPEOVERIGBOUWWERK);

        restSoortoverigbouwwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoortoverigbouwwerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSoortoverigbouwwerk))
            )
            .andExpect(status().isOk());

        // Validate the Soortoverigbouwwerk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSoortoverigbouwwerkUpdatableFieldsEquals(
            partialUpdatedSoortoverigbouwwerk,
            getPersistedSoortoverigbouwwerk(partialUpdatedSoortoverigbouwwerk)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSoortoverigbouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortoverigbouwwerk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoortoverigbouwwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, soortoverigbouwwerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(soortoverigbouwwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortoverigbouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSoortoverigbouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortoverigbouwwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortoverigbouwwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(soortoverigbouwwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortoverigbouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSoortoverigbouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortoverigbouwwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortoverigbouwwerkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(soortoverigbouwwerk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soortoverigbouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSoortoverigbouwwerk() throws Exception {
        // Initialize the database
        soortoverigbouwwerkRepository.saveAndFlush(soortoverigbouwwerk);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the soortoverigbouwwerk
        restSoortoverigbouwwerkMockMvc
            .perform(delete(ENTITY_API_URL_ID, soortoverigbouwwerk.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return soortoverigbouwwerkRepository.count();
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

    protected Soortoverigbouwwerk getPersistedSoortoverigbouwwerk(Soortoverigbouwwerk soortoverigbouwwerk) {
        return soortoverigbouwwerkRepository.findById(soortoverigbouwwerk.getId()).orElseThrow();
    }

    protected void assertPersistedSoortoverigbouwwerkToMatchAllProperties(Soortoverigbouwwerk expectedSoortoverigbouwwerk) {
        assertSoortoverigbouwwerkAllPropertiesEquals(
            expectedSoortoverigbouwwerk,
            getPersistedSoortoverigbouwwerk(expectedSoortoverigbouwwerk)
        );
    }

    protected void assertPersistedSoortoverigbouwwerkToMatchUpdatableProperties(Soortoverigbouwwerk expectedSoortoverigbouwwerk) {
        assertSoortoverigbouwwerkAllUpdatablePropertiesEquals(
            expectedSoortoverigbouwwerk,
            getPersistedSoortoverigbouwwerk(expectedSoortoverigbouwwerk)
        );
    }
}
