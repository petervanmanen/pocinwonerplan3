package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OverigbenoemdterreinAsserts.*;
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
import nl.ritense.demo.domain.Overigbenoemdterrein;
import nl.ritense.demo.repository.OverigbenoemdterreinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OverigbenoemdterreinResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OverigbenoemdterreinResourceIT {

    private static final String DEFAULT_GEBRUIKSDOELOVERIGBENOEMDTERREIN = "AAAAAAAAAA";
    private static final String UPDATED_GEBRUIKSDOELOVERIGBENOEMDTERREIN = "BBBBBBBBBB";

    private static final String DEFAULT_OVERIGBENOEMDTERREINIDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_OVERIGBENOEMDTERREINIDENTIFICATIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/overigbenoemdterreins";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OverigbenoemdterreinRepository overigbenoemdterreinRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOverigbenoemdterreinMockMvc;

    private Overigbenoemdterrein overigbenoemdterrein;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overigbenoemdterrein createEntity(EntityManager em) {
        Overigbenoemdterrein overigbenoemdterrein = new Overigbenoemdterrein()
            .gebruiksdoeloverigbenoemdterrein(DEFAULT_GEBRUIKSDOELOVERIGBENOEMDTERREIN)
            .overigbenoemdterreinidentificatie(DEFAULT_OVERIGBENOEMDTERREINIDENTIFICATIE);
        return overigbenoemdterrein;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overigbenoemdterrein createUpdatedEntity(EntityManager em) {
        Overigbenoemdterrein overigbenoemdterrein = new Overigbenoemdterrein()
            .gebruiksdoeloverigbenoemdterrein(UPDATED_GEBRUIKSDOELOVERIGBENOEMDTERREIN)
            .overigbenoemdterreinidentificatie(UPDATED_OVERIGBENOEMDTERREINIDENTIFICATIE);
        return overigbenoemdterrein;
    }

    @BeforeEach
    public void initTest() {
        overigbenoemdterrein = createEntity(em);
    }

    @Test
    @Transactional
    void createOverigbenoemdterrein() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Overigbenoemdterrein
        var returnedOverigbenoemdterrein = om.readValue(
            restOverigbenoemdterreinMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overigbenoemdterrein)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Overigbenoemdterrein.class
        );

        // Validate the Overigbenoemdterrein in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOverigbenoemdterreinUpdatableFieldsEquals(
            returnedOverigbenoemdterrein,
            getPersistedOverigbenoemdterrein(returnedOverigbenoemdterrein)
        );
    }

    @Test
    @Transactional
    void createOverigbenoemdterreinWithExistingId() throws Exception {
        // Create the Overigbenoemdterrein with an existing ID
        overigbenoemdterrein.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOverigbenoemdterreinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overigbenoemdterrein)))
            .andExpect(status().isBadRequest());

        // Validate the Overigbenoemdterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOverigbenoemdterreins() throws Exception {
        // Initialize the database
        overigbenoemdterreinRepository.saveAndFlush(overigbenoemdterrein);

        // Get all the overigbenoemdterreinList
        restOverigbenoemdterreinMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overigbenoemdterrein.getId().intValue())))
            .andExpect(jsonPath("$.[*].gebruiksdoeloverigbenoemdterrein").value(hasItem(DEFAULT_GEBRUIKSDOELOVERIGBENOEMDTERREIN)))
            .andExpect(jsonPath("$.[*].overigbenoemdterreinidentificatie").value(hasItem(DEFAULT_OVERIGBENOEMDTERREINIDENTIFICATIE)));
    }

    @Test
    @Transactional
    void getOverigbenoemdterrein() throws Exception {
        // Initialize the database
        overigbenoemdterreinRepository.saveAndFlush(overigbenoemdterrein);

        // Get the overigbenoemdterrein
        restOverigbenoemdterreinMockMvc
            .perform(get(ENTITY_API_URL_ID, overigbenoemdterrein.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(overigbenoemdterrein.getId().intValue()))
            .andExpect(jsonPath("$.gebruiksdoeloverigbenoemdterrein").value(DEFAULT_GEBRUIKSDOELOVERIGBENOEMDTERREIN))
            .andExpect(jsonPath("$.overigbenoemdterreinidentificatie").value(DEFAULT_OVERIGBENOEMDTERREINIDENTIFICATIE));
    }

    @Test
    @Transactional
    void getNonExistingOverigbenoemdterrein() throws Exception {
        // Get the overigbenoemdterrein
        restOverigbenoemdterreinMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOverigbenoemdterrein() throws Exception {
        // Initialize the database
        overigbenoemdterreinRepository.saveAndFlush(overigbenoemdterrein);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overigbenoemdterrein
        Overigbenoemdterrein updatedOverigbenoemdterrein = overigbenoemdterreinRepository
            .findById(overigbenoemdterrein.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOverigbenoemdterrein are not directly saved in db
        em.detach(updatedOverigbenoemdterrein);
        updatedOverigbenoemdterrein
            .gebruiksdoeloverigbenoemdterrein(UPDATED_GEBRUIKSDOELOVERIGBENOEMDTERREIN)
            .overigbenoemdterreinidentificatie(UPDATED_OVERIGBENOEMDTERREINIDENTIFICATIE);

        restOverigbenoemdterreinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOverigbenoemdterrein.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOverigbenoemdterrein))
            )
            .andExpect(status().isOk());

        // Validate the Overigbenoemdterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOverigbenoemdterreinToMatchAllProperties(updatedOverigbenoemdterrein);
    }

    @Test
    @Transactional
    void putNonExistingOverigbenoemdterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigbenoemdterrein.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverigbenoemdterreinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, overigbenoemdterrein.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overigbenoemdterrein))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overigbenoemdterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOverigbenoemdterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigbenoemdterrein.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverigbenoemdterreinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overigbenoemdterrein))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overigbenoemdterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOverigbenoemdterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigbenoemdterrein.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverigbenoemdterreinMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overigbenoemdterrein)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overigbenoemdterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOverigbenoemdterreinWithPatch() throws Exception {
        // Initialize the database
        overigbenoemdterreinRepository.saveAndFlush(overigbenoemdterrein);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overigbenoemdterrein using partial update
        Overigbenoemdterrein partialUpdatedOverigbenoemdterrein = new Overigbenoemdterrein();
        partialUpdatedOverigbenoemdterrein.setId(overigbenoemdterrein.getId());

        partialUpdatedOverigbenoemdterrein
            .gebruiksdoeloverigbenoemdterrein(UPDATED_GEBRUIKSDOELOVERIGBENOEMDTERREIN)
            .overigbenoemdterreinidentificatie(UPDATED_OVERIGBENOEMDTERREINIDENTIFICATIE);

        restOverigbenoemdterreinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverigbenoemdterrein.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverigbenoemdterrein))
            )
            .andExpect(status().isOk());

        // Validate the Overigbenoemdterrein in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverigbenoemdterreinUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOverigbenoemdterrein, overigbenoemdterrein),
            getPersistedOverigbenoemdterrein(overigbenoemdterrein)
        );
    }

    @Test
    @Transactional
    void fullUpdateOverigbenoemdterreinWithPatch() throws Exception {
        // Initialize the database
        overigbenoemdterreinRepository.saveAndFlush(overigbenoemdterrein);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overigbenoemdterrein using partial update
        Overigbenoemdterrein partialUpdatedOverigbenoemdterrein = new Overigbenoemdterrein();
        partialUpdatedOverigbenoemdterrein.setId(overigbenoemdterrein.getId());

        partialUpdatedOverigbenoemdterrein
            .gebruiksdoeloverigbenoemdterrein(UPDATED_GEBRUIKSDOELOVERIGBENOEMDTERREIN)
            .overigbenoemdterreinidentificatie(UPDATED_OVERIGBENOEMDTERREINIDENTIFICATIE);

        restOverigbenoemdterreinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverigbenoemdterrein.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverigbenoemdterrein))
            )
            .andExpect(status().isOk());

        // Validate the Overigbenoemdterrein in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverigbenoemdterreinUpdatableFieldsEquals(
            partialUpdatedOverigbenoemdterrein,
            getPersistedOverigbenoemdterrein(partialUpdatedOverigbenoemdterrein)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOverigbenoemdterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigbenoemdterrein.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverigbenoemdterreinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, overigbenoemdterrein.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overigbenoemdterrein))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overigbenoemdterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOverigbenoemdterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigbenoemdterrein.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverigbenoemdterreinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overigbenoemdterrein))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overigbenoemdterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOverigbenoemdterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigbenoemdterrein.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverigbenoemdterreinMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(overigbenoemdterrein)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overigbenoemdterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOverigbenoemdterrein() throws Exception {
        // Initialize the database
        overigbenoemdterreinRepository.saveAndFlush(overigbenoemdterrein);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the overigbenoemdterrein
        restOverigbenoemdterreinMockMvc
            .perform(delete(ENTITY_API_URL_ID, overigbenoemdterrein.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return overigbenoemdterreinRepository.count();
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

    protected Overigbenoemdterrein getPersistedOverigbenoemdterrein(Overigbenoemdterrein overigbenoemdterrein) {
        return overigbenoemdterreinRepository.findById(overigbenoemdterrein.getId()).orElseThrow();
    }

    protected void assertPersistedOverigbenoemdterreinToMatchAllProperties(Overigbenoemdterrein expectedOverigbenoemdterrein) {
        assertOverigbenoemdterreinAllPropertiesEquals(
            expectedOverigbenoemdterrein,
            getPersistedOverigbenoemdterrein(expectedOverigbenoemdterrein)
        );
    }

    protected void assertPersistedOverigbenoemdterreinToMatchUpdatableProperties(Overigbenoemdterrein expectedOverigbenoemdterrein) {
        assertOverigbenoemdterreinAllUpdatablePropertiesEquals(
            expectedOverigbenoemdterrein,
            getPersistedOverigbenoemdterrein(expectedOverigbenoemdterrein)
        );
    }
}
