package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerblijfbuitenlandAsserts.*;
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
import nl.ritense.demo.domain.Verblijfbuitenland;
import nl.ritense.demo.repository.VerblijfbuitenlandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerblijfbuitenlandResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerblijfbuitenlandResourceIT {

    private static final String DEFAULT_ADRESREGELBUITENLAND_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESREGELBUITENLAND_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESREGELBUITENLAND_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESREGELBUITENLAND_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESREGELBUITENLAND_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESREGELBUITENLAND_3 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESREGELBUITENLAND_4 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESREGELBUITENLAND_4 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESREGELBUITENLAND_5 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESREGELBUITENLAND_5 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESREGELBUITENLAND_6 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESREGELBUITENLAND_6 = "BBBBBBBBBB";

    private static final String DEFAULT_LANDOFGEBIEDVERBLIJFADRES = "AAAAAAAAAA";
    private static final String UPDATED_LANDOFGEBIEDVERBLIJFADRES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verblijfbuitenlands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerblijfbuitenlandRepository verblijfbuitenlandRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerblijfbuitenlandMockMvc;

    private Verblijfbuitenland verblijfbuitenland;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfbuitenland createEntity(EntityManager em) {
        Verblijfbuitenland verblijfbuitenland = new Verblijfbuitenland()
            .adresregelbuitenland1(DEFAULT_ADRESREGELBUITENLAND_1)
            .adresregelbuitenland2(DEFAULT_ADRESREGELBUITENLAND_2)
            .adresregelbuitenland3(DEFAULT_ADRESREGELBUITENLAND_3)
            .adresregelbuitenland4(DEFAULT_ADRESREGELBUITENLAND_4)
            .adresregelbuitenland5(DEFAULT_ADRESREGELBUITENLAND_5)
            .adresregelbuitenland6(DEFAULT_ADRESREGELBUITENLAND_6)
            .landofgebiedverblijfadres(DEFAULT_LANDOFGEBIEDVERBLIJFADRES);
        return verblijfbuitenland;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfbuitenland createUpdatedEntity(EntityManager em) {
        Verblijfbuitenland verblijfbuitenland = new Verblijfbuitenland()
            .adresregelbuitenland1(UPDATED_ADRESREGELBUITENLAND_1)
            .adresregelbuitenland2(UPDATED_ADRESREGELBUITENLAND_2)
            .adresregelbuitenland3(UPDATED_ADRESREGELBUITENLAND_3)
            .adresregelbuitenland4(UPDATED_ADRESREGELBUITENLAND_4)
            .adresregelbuitenland5(UPDATED_ADRESREGELBUITENLAND_5)
            .adresregelbuitenland6(UPDATED_ADRESREGELBUITENLAND_6)
            .landofgebiedverblijfadres(UPDATED_LANDOFGEBIEDVERBLIJFADRES);
        return verblijfbuitenland;
    }

    @BeforeEach
    public void initTest() {
        verblijfbuitenland = createEntity(em);
    }

    @Test
    @Transactional
    void createVerblijfbuitenland() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verblijfbuitenland
        var returnedVerblijfbuitenland = om.readValue(
            restVerblijfbuitenlandMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfbuitenland)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verblijfbuitenland.class
        );

        // Validate the Verblijfbuitenland in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerblijfbuitenlandUpdatableFieldsEquals(
            returnedVerblijfbuitenland,
            getPersistedVerblijfbuitenland(returnedVerblijfbuitenland)
        );
    }

    @Test
    @Transactional
    void createVerblijfbuitenlandWithExistingId() throws Exception {
        // Create the Verblijfbuitenland with an existing ID
        verblijfbuitenland.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerblijfbuitenlandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfbuitenland)))
            .andExpect(status().isBadRequest());

        // Validate the Verblijfbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerblijfbuitenlands() throws Exception {
        // Initialize the database
        verblijfbuitenlandRepository.saveAndFlush(verblijfbuitenland);

        // Get all the verblijfbuitenlandList
        restVerblijfbuitenlandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verblijfbuitenland.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresregelbuitenland1").value(hasItem(DEFAULT_ADRESREGELBUITENLAND_1)))
            .andExpect(jsonPath("$.[*].adresregelbuitenland2").value(hasItem(DEFAULT_ADRESREGELBUITENLAND_2)))
            .andExpect(jsonPath("$.[*].adresregelbuitenland3").value(hasItem(DEFAULT_ADRESREGELBUITENLAND_3)))
            .andExpect(jsonPath("$.[*].adresregelbuitenland4").value(hasItem(DEFAULT_ADRESREGELBUITENLAND_4)))
            .andExpect(jsonPath("$.[*].adresregelbuitenland5").value(hasItem(DEFAULT_ADRESREGELBUITENLAND_5)))
            .andExpect(jsonPath("$.[*].adresregelbuitenland6").value(hasItem(DEFAULT_ADRESREGELBUITENLAND_6)))
            .andExpect(jsonPath("$.[*].landofgebiedverblijfadres").value(hasItem(DEFAULT_LANDOFGEBIEDVERBLIJFADRES)));
    }

    @Test
    @Transactional
    void getVerblijfbuitenland() throws Exception {
        // Initialize the database
        verblijfbuitenlandRepository.saveAndFlush(verblijfbuitenland);

        // Get the verblijfbuitenland
        restVerblijfbuitenlandMockMvc
            .perform(get(ENTITY_API_URL_ID, verblijfbuitenland.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verblijfbuitenland.getId().intValue()))
            .andExpect(jsonPath("$.adresregelbuitenland1").value(DEFAULT_ADRESREGELBUITENLAND_1))
            .andExpect(jsonPath("$.adresregelbuitenland2").value(DEFAULT_ADRESREGELBUITENLAND_2))
            .andExpect(jsonPath("$.adresregelbuitenland3").value(DEFAULT_ADRESREGELBUITENLAND_3))
            .andExpect(jsonPath("$.adresregelbuitenland4").value(DEFAULT_ADRESREGELBUITENLAND_4))
            .andExpect(jsonPath("$.adresregelbuitenland5").value(DEFAULT_ADRESREGELBUITENLAND_5))
            .andExpect(jsonPath("$.adresregelbuitenland6").value(DEFAULT_ADRESREGELBUITENLAND_6))
            .andExpect(jsonPath("$.landofgebiedverblijfadres").value(DEFAULT_LANDOFGEBIEDVERBLIJFADRES));
    }

    @Test
    @Transactional
    void getNonExistingVerblijfbuitenland() throws Exception {
        // Get the verblijfbuitenland
        restVerblijfbuitenlandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerblijfbuitenland() throws Exception {
        // Initialize the database
        verblijfbuitenlandRepository.saveAndFlush(verblijfbuitenland);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfbuitenland
        Verblijfbuitenland updatedVerblijfbuitenland = verblijfbuitenlandRepository.findById(verblijfbuitenland.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerblijfbuitenland are not directly saved in db
        em.detach(updatedVerblijfbuitenland);
        updatedVerblijfbuitenland
            .adresregelbuitenland1(UPDATED_ADRESREGELBUITENLAND_1)
            .adresregelbuitenland2(UPDATED_ADRESREGELBUITENLAND_2)
            .adresregelbuitenland3(UPDATED_ADRESREGELBUITENLAND_3)
            .adresregelbuitenland4(UPDATED_ADRESREGELBUITENLAND_4)
            .adresregelbuitenland5(UPDATED_ADRESREGELBUITENLAND_5)
            .adresregelbuitenland6(UPDATED_ADRESREGELBUITENLAND_6)
            .landofgebiedverblijfadres(UPDATED_LANDOFGEBIEDVERBLIJFADRES);

        restVerblijfbuitenlandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerblijfbuitenland.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerblijfbuitenland))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerblijfbuitenlandToMatchAllProperties(updatedVerblijfbuitenland);
    }

    @Test
    @Transactional
    void putNonExistingVerblijfbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfbuitenland.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerblijfbuitenlandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verblijfbuitenland.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfbuitenland))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerblijfbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfbuitenland.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfbuitenlandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfbuitenland))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerblijfbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfbuitenland.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfbuitenlandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfbuitenland)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verblijfbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerblijfbuitenlandWithPatch() throws Exception {
        // Initialize the database
        verblijfbuitenlandRepository.saveAndFlush(verblijfbuitenland);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfbuitenland using partial update
        Verblijfbuitenland partialUpdatedVerblijfbuitenland = new Verblijfbuitenland();
        partialUpdatedVerblijfbuitenland.setId(verblijfbuitenland.getId());

        partialUpdatedVerblijfbuitenland
            .adresregelbuitenland1(UPDATED_ADRESREGELBUITENLAND_1)
            .adresregelbuitenland2(UPDATED_ADRESREGELBUITENLAND_2)
            .adresregelbuitenland4(UPDATED_ADRESREGELBUITENLAND_4)
            .adresregelbuitenland5(UPDATED_ADRESREGELBUITENLAND_5)
            .adresregelbuitenland6(UPDATED_ADRESREGELBUITENLAND_6)
            .landofgebiedverblijfadres(UPDATED_LANDOFGEBIEDVERBLIJFADRES);

        restVerblijfbuitenlandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerblijfbuitenland.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerblijfbuitenland))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfbuitenland in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerblijfbuitenlandUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerblijfbuitenland, verblijfbuitenland),
            getPersistedVerblijfbuitenland(verblijfbuitenland)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerblijfbuitenlandWithPatch() throws Exception {
        // Initialize the database
        verblijfbuitenlandRepository.saveAndFlush(verblijfbuitenland);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfbuitenland using partial update
        Verblijfbuitenland partialUpdatedVerblijfbuitenland = new Verblijfbuitenland();
        partialUpdatedVerblijfbuitenland.setId(verblijfbuitenland.getId());

        partialUpdatedVerblijfbuitenland
            .adresregelbuitenland1(UPDATED_ADRESREGELBUITENLAND_1)
            .adresregelbuitenland2(UPDATED_ADRESREGELBUITENLAND_2)
            .adresregelbuitenland3(UPDATED_ADRESREGELBUITENLAND_3)
            .adresregelbuitenland4(UPDATED_ADRESREGELBUITENLAND_4)
            .adresregelbuitenland5(UPDATED_ADRESREGELBUITENLAND_5)
            .adresregelbuitenland6(UPDATED_ADRESREGELBUITENLAND_6)
            .landofgebiedverblijfadres(UPDATED_LANDOFGEBIEDVERBLIJFADRES);

        restVerblijfbuitenlandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerblijfbuitenland.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerblijfbuitenland))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfbuitenland in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerblijfbuitenlandUpdatableFieldsEquals(
            partialUpdatedVerblijfbuitenland,
            getPersistedVerblijfbuitenland(partialUpdatedVerblijfbuitenland)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVerblijfbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfbuitenland.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerblijfbuitenlandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verblijfbuitenland.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verblijfbuitenland))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerblijfbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfbuitenland.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfbuitenlandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verblijfbuitenland))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerblijfbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfbuitenland.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfbuitenlandMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verblijfbuitenland)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verblijfbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerblijfbuitenland() throws Exception {
        // Initialize the database
        verblijfbuitenlandRepository.saveAndFlush(verblijfbuitenland);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verblijfbuitenland
        restVerblijfbuitenlandMockMvc
            .perform(delete(ENTITY_API_URL_ID, verblijfbuitenland.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verblijfbuitenlandRepository.count();
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

    protected Verblijfbuitenland getPersistedVerblijfbuitenland(Verblijfbuitenland verblijfbuitenland) {
        return verblijfbuitenlandRepository.findById(verblijfbuitenland.getId()).orElseThrow();
    }

    protected void assertPersistedVerblijfbuitenlandToMatchAllProperties(Verblijfbuitenland expectedVerblijfbuitenland) {
        assertVerblijfbuitenlandAllPropertiesEquals(expectedVerblijfbuitenland, getPersistedVerblijfbuitenland(expectedVerblijfbuitenland));
    }

    protected void assertPersistedVerblijfbuitenlandToMatchUpdatableProperties(Verblijfbuitenland expectedVerblijfbuitenland) {
        assertVerblijfbuitenlandAllUpdatablePropertiesEquals(
            expectedVerblijfbuitenland,
            getPersistedVerblijfbuitenland(expectedVerblijfbuitenland)
        );
    }
}
