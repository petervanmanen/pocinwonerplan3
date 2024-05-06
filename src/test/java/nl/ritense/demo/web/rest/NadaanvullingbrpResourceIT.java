package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NadaanvullingbrpAsserts.*;
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
import nl.ritense.demo.domain.Nadaanvullingbrp;
import nl.ritense.demo.repository.NadaanvullingbrpRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NadaanvullingbrpResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NadaanvullingbrpResourceIT {

    private static final String DEFAULT_OPMERKINGEN = "AAAAAAAAAA";
    private static final String UPDATED_OPMERKINGEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nadaanvullingbrps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NadaanvullingbrpRepository nadaanvullingbrpRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNadaanvullingbrpMockMvc;

    private Nadaanvullingbrp nadaanvullingbrp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nadaanvullingbrp createEntity(EntityManager em) {
        Nadaanvullingbrp nadaanvullingbrp = new Nadaanvullingbrp().opmerkingen(DEFAULT_OPMERKINGEN);
        return nadaanvullingbrp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nadaanvullingbrp createUpdatedEntity(EntityManager em) {
        Nadaanvullingbrp nadaanvullingbrp = new Nadaanvullingbrp().opmerkingen(UPDATED_OPMERKINGEN);
        return nadaanvullingbrp;
    }

    @BeforeEach
    public void initTest() {
        nadaanvullingbrp = createEntity(em);
    }

    @Test
    @Transactional
    void createNadaanvullingbrp() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Nadaanvullingbrp
        var returnedNadaanvullingbrp = om.readValue(
            restNadaanvullingbrpMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nadaanvullingbrp)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Nadaanvullingbrp.class
        );

        // Validate the Nadaanvullingbrp in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNadaanvullingbrpUpdatableFieldsEquals(returnedNadaanvullingbrp, getPersistedNadaanvullingbrp(returnedNadaanvullingbrp));
    }

    @Test
    @Transactional
    void createNadaanvullingbrpWithExistingId() throws Exception {
        // Create the Nadaanvullingbrp with an existing ID
        nadaanvullingbrp.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNadaanvullingbrpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nadaanvullingbrp)))
            .andExpect(status().isBadRequest());

        // Validate the Nadaanvullingbrp in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNadaanvullingbrps() throws Exception {
        // Initialize the database
        nadaanvullingbrpRepository.saveAndFlush(nadaanvullingbrp);

        // Get all the nadaanvullingbrpList
        restNadaanvullingbrpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nadaanvullingbrp.getId().intValue())))
            .andExpect(jsonPath("$.[*].opmerkingen").value(hasItem(DEFAULT_OPMERKINGEN)));
    }

    @Test
    @Transactional
    void getNadaanvullingbrp() throws Exception {
        // Initialize the database
        nadaanvullingbrpRepository.saveAndFlush(nadaanvullingbrp);

        // Get the nadaanvullingbrp
        restNadaanvullingbrpMockMvc
            .perform(get(ENTITY_API_URL_ID, nadaanvullingbrp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nadaanvullingbrp.getId().intValue()))
            .andExpect(jsonPath("$.opmerkingen").value(DEFAULT_OPMERKINGEN));
    }

    @Test
    @Transactional
    void getNonExistingNadaanvullingbrp() throws Exception {
        // Get the nadaanvullingbrp
        restNadaanvullingbrpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNadaanvullingbrp() throws Exception {
        // Initialize the database
        nadaanvullingbrpRepository.saveAndFlush(nadaanvullingbrp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nadaanvullingbrp
        Nadaanvullingbrp updatedNadaanvullingbrp = nadaanvullingbrpRepository.findById(nadaanvullingbrp.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNadaanvullingbrp are not directly saved in db
        em.detach(updatedNadaanvullingbrp);
        updatedNadaanvullingbrp.opmerkingen(UPDATED_OPMERKINGEN);

        restNadaanvullingbrpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNadaanvullingbrp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNadaanvullingbrp))
            )
            .andExpect(status().isOk());

        // Validate the Nadaanvullingbrp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNadaanvullingbrpToMatchAllProperties(updatedNadaanvullingbrp);
    }

    @Test
    @Transactional
    void putNonExistingNadaanvullingbrp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nadaanvullingbrp.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNadaanvullingbrpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nadaanvullingbrp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nadaanvullingbrp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nadaanvullingbrp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNadaanvullingbrp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nadaanvullingbrp.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNadaanvullingbrpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nadaanvullingbrp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nadaanvullingbrp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNadaanvullingbrp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nadaanvullingbrp.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNadaanvullingbrpMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nadaanvullingbrp)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nadaanvullingbrp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNadaanvullingbrpWithPatch() throws Exception {
        // Initialize the database
        nadaanvullingbrpRepository.saveAndFlush(nadaanvullingbrp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nadaanvullingbrp using partial update
        Nadaanvullingbrp partialUpdatedNadaanvullingbrp = new Nadaanvullingbrp();
        partialUpdatedNadaanvullingbrp.setId(nadaanvullingbrp.getId());

        restNadaanvullingbrpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNadaanvullingbrp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNadaanvullingbrp))
            )
            .andExpect(status().isOk());

        // Validate the Nadaanvullingbrp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNadaanvullingbrpUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNadaanvullingbrp, nadaanvullingbrp),
            getPersistedNadaanvullingbrp(nadaanvullingbrp)
        );
    }

    @Test
    @Transactional
    void fullUpdateNadaanvullingbrpWithPatch() throws Exception {
        // Initialize the database
        nadaanvullingbrpRepository.saveAndFlush(nadaanvullingbrp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nadaanvullingbrp using partial update
        Nadaanvullingbrp partialUpdatedNadaanvullingbrp = new Nadaanvullingbrp();
        partialUpdatedNadaanvullingbrp.setId(nadaanvullingbrp.getId());

        partialUpdatedNadaanvullingbrp.opmerkingen(UPDATED_OPMERKINGEN);

        restNadaanvullingbrpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNadaanvullingbrp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNadaanvullingbrp))
            )
            .andExpect(status().isOk());

        // Validate the Nadaanvullingbrp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNadaanvullingbrpUpdatableFieldsEquals(
            partialUpdatedNadaanvullingbrp,
            getPersistedNadaanvullingbrp(partialUpdatedNadaanvullingbrp)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNadaanvullingbrp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nadaanvullingbrp.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNadaanvullingbrpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nadaanvullingbrp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nadaanvullingbrp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nadaanvullingbrp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNadaanvullingbrp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nadaanvullingbrp.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNadaanvullingbrpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nadaanvullingbrp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nadaanvullingbrp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNadaanvullingbrp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nadaanvullingbrp.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNadaanvullingbrpMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(nadaanvullingbrp)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nadaanvullingbrp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNadaanvullingbrp() throws Exception {
        // Initialize the database
        nadaanvullingbrpRepository.saveAndFlush(nadaanvullingbrp);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the nadaanvullingbrp
        restNadaanvullingbrpMockMvc
            .perform(delete(ENTITY_API_URL_ID, nadaanvullingbrp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return nadaanvullingbrpRepository.count();
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

    protected Nadaanvullingbrp getPersistedNadaanvullingbrp(Nadaanvullingbrp nadaanvullingbrp) {
        return nadaanvullingbrpRepository.findById(nadaanvullingbrp.getId()).orElseThrow();
    }

    protected void assertPersistedNadaanvullingbrpToMatchAllProperties(Nadaanvullingbrp expectedNadaanvullingbrp) {
        assertNadaanvullingbrpAllPropertiesEquals(expectedNadaanvullingbrp, getPersistedNadaanvullingbrp(expectedNadaanvullingbrp));
    }

    protected void assertPersistedNadaanvullingbrpToMatchUpdatableProperties(Nadaanvullingbrp expectedNadaanvullingbrp) {
        assertNadaanvullingbrpAllUpdatablePropertiesEquals(
            expectedNadaanvullingbrp,
            getPersistedNadaanvullingbrp(expectedNadaanvullingbrp)
        );
    }
}
