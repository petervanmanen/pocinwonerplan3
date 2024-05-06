package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SoortkunstwerkAsserts.*;
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
import nl.ritense.demo.domain.Soortkunstwerk;
import nl.ritense.demo.repository.SoortkunstwerkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SoortkunstwerkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SoortkunstwerkResourceIT {

    private static final String DEFAULT_INDICATIEPLUSBRPOPULATIE = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEPLUSBRPOPULATIE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEKUNSTWERK = "AAAAAAAAAA";
    private static final String UPDATED_TYPEKUNSTWERK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/soortkunstwerks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SoortkunstwerkRepository soortkunstwerkRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoortkunstwerkMockMvc;

    private Soortkunstwerk soortkunstwerk;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soortkunstwerk createEntity(EntityManager em) {
        Soortkunstwerk soortkunstwerk = new Soortkunstwerk()
            .indicatieplusbrpopulatie(DEFAULT_INDICATIEPLUSBRPOPULATIE)
            .typekunstwerk(DEFAULT_TYPEKUNSTWERK);
        return soortkunstwerk;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soortkunstwerk createUpdatedEntity(EntityManager em) {
        Soortkunstwerk soortkunstwerk = new Soortkunstwerk()
            .indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE)
            .typekunstwerk(UPDATED_TYPEKUNSTWERK);
        return soortkunstwerk;
    }

    @BeforeEach
    public void initTest() {
        soortkunstwerk = createEntity(em);
    }

    @Test
    @Transactional
    void createSoortkunstwerk() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Soortkunstwerk
        var returnedSoortkunstwerk = om.readValue(
            restSoortkunstwerkMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortkunstwerk)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Soortkunstwerk.class
        );

        // Validate the Soortkunstwerk in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSoortkunstwerkUpdatableFieldsEquals(returnedSoortkunstwerk, getPersistedSoortkunstwerk(returnedSoortkunstwerk));
    }

    @Test
    @Transactional
    void createSoortkunstwerkWithExistingId() throws Exception {
        // Create the Soortkunstwerk with an existing ID
        soortkunstwerk.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoortkunstwerkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortkunstwerk)))
            .andExpect(status().isBadRequest());

        // Validate the Soortkunstwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSoortkunstwerks() throws Exception {
        // Initialize the database
        soortkunstwerkRepository.saveAndFlush(soortkunstwerk);

        // Get all the soortkunstwerkList
        restSoortkunstwerkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soortkunstwerk.getId().intValue())))
            .andExpect(jsonPath("$.[*].indicatieplusbrpopulatie").value(hasItem(DEFAULT_INDICATIEPLUSBRPOPULATIE)))
            .andExpect(jsonPath("$.[*].typekunstwerk").value(hasItem(DEFAULT_TYPEKUNSTWERK)));
    }

    @Test
    @Transactional
    void getSoortkunstwerk() throws Exception {
        // Initialize the database
        soortkunstwerkRepository.saveAndFlush(soortkunstwerk);

        // Get the soortkunstwerk
        restSoortkunstwerkMockMvc
            .perform(get(ENTITY_API_URL_ID, soortkunstwerk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(soortkunstwerk.getId().intValue()))
            .andExpect(jsonPath("$.indicatieplusbrpopulatie").value(DEFAULT_INDICATIEPLUSBRPOPULATIE))
            .andExpect(jsonPath("$.typekunstwerk").value(DEFAULT_TYPEKUNSTWERK));
    }

    @Test
    @Transactional
    void getNonExistingSoortkunstwerk() throws Exception {
        // Get the soortkunstwerk
        restSoortkunstwerkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSoortkunstwerk() throws Exception {
        // Initialize the database
        soortkunstwerkRepository.saveAndFlush(soortkunstwerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortkunstwerk
        Soortkunstwerk updatedSoortkunstwerk = soortkunstwerkRepository.findById(soortkunstwerk.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSoortkunstwerk are not directly saved in db
        em.detach(updatedSoortkunstwerk);
        updatedSoortkunstwerk.indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE).typekunstwerk(UPDATED_TYPEKUNSTWERK);

        restSoortkunstwerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSoortkunstwerk.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSoortkunstwerk))
            )
            .andExpect(status().isOk());

        // Validate the Soortkunstwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSoortkunstwerkToMatchAllProperties(updatedSoortkunstwerk);
    }

    @Test
    @Transactional
    void putNonExistingSoortkunstwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortkunstwerk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoortkunstwerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soortkunstwerk.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(soortkunstwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortkunstwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSoortkunstwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortkunstwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortkunstwerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(soortkunstwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortkunstwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSoortkunstwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortkunstwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortkunstwerkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortkunstwerk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soortkunstwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSoortkunstwerkWithPatch() throws Exception {
        // Initialize the database
        soortkunstwerkRepository.saveAndFlush(soortkunstwerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortkunstwerk using partial update
        Soortkunstwerk partialUpdatedSoortkunstwerk = new Soortkunstwerk();
        partialUpdatedSoortkunstwerk.setId(soortkunstwerk.getId());

        partialUpdatedSoortkunstwerk.indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE).typekunstwerk(UPDATED_TYPEKUNSTWERK);

        restSoortkunstwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoortkunstwerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSoortkunstwerk))
            )
            .andExpect(status().isOk());

        // Validate the Soortkunstwerk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSoortkunstwerkUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSoortkunstwerk, soortkunstwerk),
            getPersistedSoortkunstwerk(soortkunstwerk)
        );
    }

    @Test
    @Transactional
    void fullUpdateSoortkunstwerkWithPatch() throws Exception {
        // Initialize the database
        soortkunstwerkRepository.saveAndFlush(soortkunstwerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortkunstwerk using partial update
        Soortkunstwerk partialUpdatedSoortkunstwerk = new Soortkunstwerk();
        partialUpdatedSoortkunstwerk.setId(soortkunstwerk.getId());

        partialUpdatedSoortkunstwerk.indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE).typekunstwerk(UPDATED_TYPEKUNSTWERK);

        restSoortkunstwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoortkunstwerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSoortkunstwerk))
            )
            .andExpect(status().isOk());

        // Validate the Soortkunstwerk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSoortkunstwerkUpdatableFieldsEquals(partialUpdatedSoortkunstwerk, getPersistedSoortkunstwerk(partialUpdatedSoortkunstwerk));
    }

    @Test
    @Transactional
    void patchNonExistingSoortkunstwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortkunstwerk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoortkunstwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, soortkunstwerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(soortkunstwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortkunstwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSoortkunstwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortkunstwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortkunstwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(soortkunstwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortkunstwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSoortkunstwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortkunstwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortkunstwerkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(soortkunstwerk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soortkunstwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSoortkunstwerk() throws Exception {
        // Initialize the database
        soortkunstwerkRepository.saveAndFlush(soortkunstwerk);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the soortkunstwerk
        restSoortkunstwerkMockMvc
            .perform(delete(ENTITY_API_URL_ID, soortkunstwerk.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return soortkunstwerkRepository.count();
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

    protected Soortkunstwerk getPersistedSoortkunstwerk(Soortkunstwerk soortkunstwerk) {
        return soortkunstwerkRepository.findById(soortkunstwerk.getId()).orElseThrow();
    }

    protected void assertPersistedSoortkunstwerkToMatchAllProperties(Soortkunstwerk expectedSoortkunstwerk) {
        assertSoortkunstwerkAllPropertiesEquals(expectedSoortkunstwerk, getPersistedSoortkunstwerk(expectedSoortkunstwerk));
    }

    protected void assertPersistedSoortkunstwerkToMatchUpdatableProperties(Soortkunstwerk expectedSoortkunstwerk) {
        assertSoortkunstwerkAllUpdatablePropertiesEquals(expectedSoortkunstwerk, getPersistedSoortkunstwerk(expectedSoortkunstwerk));
    }
}
