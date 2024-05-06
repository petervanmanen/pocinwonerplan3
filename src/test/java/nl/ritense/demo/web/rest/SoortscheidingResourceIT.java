package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SoortscheidingAsserts.*;
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
import nl.ritense.demo.domain.Soortscheiding;
import nl.ritense.demo.repository.SoortscheidingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SoortscheidingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SoortscheidingResourceIT {

    private static final String DEFAULT_INDICATIEPLUSBRPOPULATIE = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEPLUSBRPOPULATIE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPESCHEIDING = "AAAAAAAAAA";
    private static final String UPDATED_TYPESCHEIDING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/soortscheidings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SoortscheidingRepository soortscheidingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoortscheidingMockMvc;

    private Soortscheiding soortscheiding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soortscheiding createEntity(EntityManager em) {
        Soortscheiding soortscheiding = new Soortscheiding()
            .indicatieplusbrpopulatie(DEFAULT_INDICATIEPLUSBRPOPULATIE)
            .typescheiding(DEFAULT_TYPESCHEIDING);
        return soortscheiding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soortscheiding createUpdatedEntity(EntityManager em) {
        Soortscheiding soortscheiding = new Soortscheiding()
            .indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE)
            .typescheiding(UPDATED_TYPESCHEIDING);
        return soortscheiding;
    }

    @BeforeEach
    public void initTest() {
        soortscheiding = createEntity(em);
    }

    @Test
    @Transactional
    void createSoortscheiding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Soortscheiding
        var returnedSoortscheiding = om.readValue(
            restSoortscheidingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortscheiding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Soortscheiding.class
        );

        // Validate the Soortscheiding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSoortscheidingUpdatableFieldsEquals(returnedSoortscheiding, getPersistedSoortscheiding(returnedSoortscheiding));
    }

    @Test
    @Transactional
    void createSoortscheidingWithExistingId() throws Exception {
        // Create the Soortscheiding with an existing ID
        soortscheiding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoortscheidingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortscheiding)))
            .andExpect(status().isBadRequest());

        // Validate the Soortscheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSoortscheidings() throws Exception {
        // Initialize the database
        soortscheidingRepository.saveAndFlush(soortscheiding);

        // Get all the soortscheidingList
        restSoortscheidingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soortscheiding.getId().intValue())))
            .andExpect(jsonPath("$.[*].indicatieplusbrpopulatie").value(hasItem(DEFAULT_INDICATIEPLUSBRPOPULATIE)))
            .andExpect(jsonPath("$.[*].typescheiding").value(hasItem(DEFAULT_TYPESCHEIDING)));
    }

    @Test
    @Transactional
    void getSoortscheiding() throws Exception {
        // Initialize the database
        soortscheidingRepository.saveAndFlush(soortscheiding);

        // Get the soortscheiding
        restSoortscheidingMockMvc
            .perform(get(ENTITY_API_URL_ID, soortscheiding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(soortscheiding.getId().intValue()))
            .andExpect(jsonPath("$.indicatieplusbrpopulatie").value(DEFAULT_INDICATIEPLUSBRPOPULATIE))
            .andExpect(jsonPath("$.typescheiding").value(DEFAULT_TYPESCHEIDING));
    }

    @Test
    @Transactional
    void getNonExistingSoortscheiding() throws Exception {
        // Get the soortscheiding
        restSoortscheidingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSoortscheiding() throws Exception {
        // Initialize the database
        soortscheidingRepository.saveAndFlush(soortscheiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortscheiding
        Soortscheiding updatedSoortscheiding = soortscheidingRepository.findById(soortscheiding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSoortscheiding are not directly saved in db
        em.detach(updatedSoortscheiding);
        updatedSoortscheiding.indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE).typescheiding(UPDATED_TYPESCHEIDING);

        restSoortscheidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSoortscheiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSoortscheiding))
            )
            .andExpect(status().isOk());

        // Validate the Soortscheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSoortscheidingToMatchAllProperties(updatedSoortscheiding);
    }

    @Test
    @Transactional
    void putNonExistingSoortscheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortscheiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoortscheidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soortscheiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(soortscheiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortscheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSoortscheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortscheiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortscheidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(soortscheiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortscheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSoortscheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortscheiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortscheidingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortscheiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soortscheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSoortscheidingWithPatch() throws Exception {
        // Initialize the database
        soortscheidingRepository.saveAndFlush(soortscheiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortscheiding using partial update
        Soortscheiding partialUpdatedSoortscheiding = new Soortscheiding();
        partialUpdatedSoortscheiding.setId(soortscheiding.getId());

        partialUpdatedSoortscheiding.typescheiding(UPDATED_TYPESCHEIDING);

        restSoortscheidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoortscheiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSoortscheiding))
            )
            .andExpect(status().isOk());

        // Validate the Soortscheiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSoortscheidingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSoortscheiding, soortscheiding),
            getPersistedSoortscheiding(soortscheiding)
        );
    }

    @Test
    @Transactional
    void fullUpdateSoortscheidingWithPatch() throws Exception {
        // Initialize the database
        soortscheidingRepository.saveAndFlush(soortscheiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortscheiding using partial update
        Soortscheiding partialUpdatedSoortscheiding = new Soortscheiding();
        partialUpdatedSoortscheiding.setId(soortscheiding.getId());

        partialUpdatedSoortscheiding.indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE).typescheiding(UPDATED_TYPESCHEIDING);

        restSoortscheidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoortscheiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSoortscheiding))
            )
            .andExpect(status().isOk());

        // Validate the Soortscheiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSoortscheidingUpdatableFieldsEquals(partialUpdatedSoortscheiding, getPersistedSoortscheiding(partialUpdatedSoortscheiding));
    }

    @Test
    @Transactional
    void patchNonExistingSoortscheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortscheiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoortscheidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, soortscheiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(soortscheiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortscheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSoortscheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortscheiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortscheidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(soortscheiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortscheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSoortscheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortscheiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortscheidingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(soortscheiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soortscheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSoortscheiding() throws Exception {
        // Initialize the database
        soortscheidingRepository.saveAndFlush(soortscheiding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the soortscheiding
        restSoortscheidingMockMvc
            .perform(delete(ENTITY_API_URL_ID, soortscheiding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return soortscheidingRepository.count();
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

    protected Soortscheiding getPersistedSoortscheiding(Soortscheiding soortscheiding) {
        return soortscheidingRepository.findById(soortscheiding.getId()).orElseThrow();
    }

    protected void assertPersistedSoortscheidingToMatchAllProperties(Soortscheiding expectedSoortscheiding) {
        assertSoortscheidingAllPropertiesEquals(expectedSoortscheiding, getPersistedSoortscheiding(expectedSoortscheiding));
    }

    protected void assertPersistedSoortscheidingToMatchUpdatableProperties(Soortscheiding expectedSoortscheiding) {
        assertSoortscheidingAllUpdatablePropertiesEquals(expectedSoortscheiding, getPersistedSoortscheiding(expectedSoortscheiding));
    }
}
