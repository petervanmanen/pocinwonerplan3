package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KunstwerkdeelAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Kunstwerkdeel;
import nl.ritense.demo.repository.KunstwerkdeelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KunstwerkdeelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KunstwerkdeelResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDKUNSTWERKDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDKUNSTWERKDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDKUNSTWERKDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDKUNSTWERKDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEOMETRIEKUNSTWERKDEEL = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEKUNSTWERKDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIEKUNSTWERKDEEL = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIEKUNSTWERKDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_0_GEOMETRIEKUNSTWERKDEEL = "AAAAAAAAAA";
    private static final String UPDATED_LOD_0_GEOMETRIEKUNSTWERKDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_1_GEOMETRIEKUNSTWERKDEEL = "AAAAAAAAAA";
    private static final String UPDATED_LOD_1_GEOMETRIEKUNSTWERKDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_2_GEOMETRIEKUNSTWERKDEEL = "AAAAAAAAAA";
    private static final String UPDATED_LOD_2_GEOMETRIEKUNSTWERKDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_3_GEOMETRIEKUNSTWERKDEEL = "AAAAAAAAAA";
    private static final String UPDATED_LOD_3_GEOMETRIEKUNSTWERKDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIEVEHOOGTELIGGINGKUNSTWERKDEEL = "AAAAAAAAAA";
    private static final String UPDATED_RELATIEVEHOOGTELIGGINGKUNSTWERKDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSKUNSTWERKDEEL = "AAAAAAAAAA";
    private static final String UPDATED_STATUSKUNSTWERKDEEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kunstwerkdeels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KunstwerkdeelRepository kunstwerkdeelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKunstwerkdeelMockMvc;

    private Kunstwerkdeel kunstwerkdeel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kunstwerkdeel createEntity(EntityManager em) {
        Kunstwerkdeel kunstwerkdeel = new Kunstwerkdeel()
            .datumbegingeldigheidkunstwerkdeel(DEFAULT_DATUMBEGINGELDIGHEIDKUNSTWERKDEEL)
            .datumeindegeldigheidkunstwerkdeel(DEFAULT_DATUMEINDEGELDIGHEIDKUNSTWERKDEEL)
            .geometriekunstwerkdeel(DEFAULT_GEOMETRIEKUNSTWERKDEEL)
            .identificatiekunstwerkdeel(DEFAULT_IDENTIFICATIEKUNSTWERKDEEL)
            .lod0geometriekunstwerkdeel(DEFAULT_LOD_0_GEOMETRIEKUNSTWERKDEEL)
            .lod1geometriekunstwerkdeel(DEFAULT_LOD_1_GEOMETRIEKUNSTWERKDEEL)
            .lod2geometriekunstwerkdeel(DEFAULT_LOD_2_GEOMETRIEKUNSTWERKDEEL)
            .lod3geometriekunstwerkdeel(DEFAULT_LOD_3_GEOMETRIEKUNSTWERKDEEL)
            .relatievehoogteliggingkunstwerkdeel(DEFAULT_RELATIEVEHOOGTELIGGINGKUNSTWERKDEEL)
            .statuskunstwerkdeel(DEFAULT_STATUSKUNSTWERKDEEL);
        return kunstwerkdeel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kunstwerkdeel createUpdatedEntity(EntityManager em) {
        Kunstwerkdeel kunstwerkdeel = new Kunstwerkdeel()
            .datumbegingeldigheidkunstwerkdeel(UPDATED_DATUMBEGINGELDIGHEIDKUNSTWERKDEEL)
            .datumeindegeldigheidkunstwerkdeel(UPDATED_DATUMEINDEGELDIGHEIDKUNSTWERKDEEL)
            .geometriekunstwerkdeel(UPDATED_GEOMETRIEKUNSTWERKDEEL)
            .identificatiekunstwerkdeel(UPDATED_IDENTIFICATIEKUNSTWERKDEEL)
            .lod0geometriekunstwerkdeel(UPDATED_LOD_0_GEOMETRIEKUNSTWERKDEEL)
            .lod1geometriekunstwerkdeel(UPDATED_LOD_1_GEOMETRIEKUNSTWERKDEEL)
            .lod2geometriekunstwerkdeel(UPDATED_LOD_2_GEOMETRIEKUNSTWERKDEEL)
            .lod3geometriekunstwerkdeel(UPDATED_LOD_3_GEOMETRIEKUNSTWERKDEEL)
            .relatievehoogteliggingkunstwerkdeel(UPDATED_RELATIEVEHOOGTELIGGINGKUNSTWERKDEEL)
            .statuskunstwerkdeel(UPDATED_STATUSKUNSTWERKDEEL);
        return kunstwerkdeel;
    }

    @BeforeEach
    public void initTest() {
        kunstwerkdeel = createEntity(em);
    }

    @Test
    @Transactional
    void createKunstwerkdeel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kunstwerkdeel
        var returnedKunstwerkdeel = om.readValue(
            restKunstwerkdeelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kunstwerkdeel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kunstwerkdeel.class
        );

        // Validate the Kunstwerkdeel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKunstwerkdeelUpdatableFieldsEquals(returnedKunstwerkdeel, getPersistedKunstwerkdeel(returnedKunstwerkdeel));
    }

    @Test
    @Transactional
    void createKunstwerkdeelWithExistingId() throws Exception {
        // Create the Kunstwerkdeel with an existing ID
        kunstwerkdeel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKunstwerkdeelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kunstwerkdeel)))
            .andExpect(status().isBadRequest());

        // Validate the Kunstwerkdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKunstwerkdeels() throws Exception {
        // Initialize the database
        kunstwerkdeelRepository.saveAndFlush(kunstwerkdeel);

        // Get all the kunstwerkdeelList
        restKunstwerkdeelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kunstwerkdeel.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidkunstwerkdeel").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDKUNSTWERKDEEL.toString()))
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidkunstwerkdeel").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDKUNSTWERKDEEL.toString()))
            )
            .andExpect(jsonPath("$.[*].geometriekunstwerkdeel").value(hasItem(DEFAULT_GEOMETRIEKUNSTWERKDEEL)))
            .andExpect(jsonPath("$.[*].identificatiekunstwerkdeel").value(hasItem(DEFAULT_IDENTIFICATIEKUNSTWERKDEEL)))
            .andExpect(jsonPath("$.[*].lod0geometriekunstwerkdeel").value(hasItem(DEFAULT_LOD_0_GEOMETRIEKUNSTWERKDEEL)))
            .andExpect(jsonPath("$.[*].lod1geometriekunstwerkdeel").value(hasItem(DEFAULT_LOD_1_GEOMETRIEKUNSTWERKDEEL)))
            .andExpect(jsonPath("$.[*].lod2geometriekunstwerkdeel").value(hasItem(DEFAULT_LOD_2_GEOMETRIEKUNSTWERKDEEL)))
            .andExpect(jsonPath("$.[*].lod3geometriekunstwerkdeel").value(hasItem(DEFAULT_LOD_3_GEOMETRIEKUNSTWERKDEEL)))
            .andExpect(jsonPath("$.[*].relatievehoogteliggingkunstwerkdeel").value(hasItem(DEFAULT_RELATIEVEHOOGTELIGGINGKUNSTWERKDEEL)))
            .andExpect(jsonPath("$.[*].statuskunstwerkdeel").value(hasItem(DEFAULT_STATUSKUNSTWERKDEEL)));
    }

    @Test
    @Transactional
    void getKunstwerkdeel() throws Exception {
        // Initialize the database
        kunstwerkdeelRepository.saveAndFlush(kunstwerkdeel);

        // Get the kunstwerkdeel
        restKunstwerkdeelMockMvc
            .perform(get(ENTITY_API_URL_ID, kunstwerkdeel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kunstwerkdeel.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheidkunstwerkdeel").value(DEFAULT_DATUMBEGINGELDIGHEIDKUNSTWERKDEEL.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidkunstwerkdeel").value(DEFAULT_DATUMEINDEGELDIGHEIDKUNSTWERKDEEL.toString()))
            .andExpect(jsonPath("$.geometriekunstwerkdeel").value(DEFAULT_GEOMETRIEKUNSTWERKDEEL))
            .andExpect(jsonPath("$.identificatiekunstwerkdeel").value(DEFAULT_IDENTIFICATIEKUNSTWERKDEEL))
            .andExpect(jsonPath("$.lod0geometriekunstwerkdeel").value(DEFAULT_LOD_0_GEOMETRIEKUNSTWERKDEEL))
            .andExpect(jsonPath("$.lod1geometriekunstwerkdeel").value(DEFAULT_LOD_1_GEOMETRIEKUNSTWERKDEEL))
            .andExpect(jsonPath("$.lod2geometriekunstwerkdeel").value(DEFAULT_LOD_2_GEOMETRIEKUNSTWERKDEEL))
            .andExpect(jsonPath("$.lod3geometriekunstwerkdeel").value(DEFAULT_LOD_3_GEOMETRIEKUNSTWERKDEEL))
            .andExpect(jsonPath("$.relatievehoogteliggingkunstwerkdeel").value(DEFAULT_RELATIEVEHOOGTELIGGINGKUNSTWERKDEEL))
            .andExpect(jsonPath("$.statuskunstwerkdeel").value(DEFAULT_STATUSKUNSTWERKDEEL));
    }

    @Test
    @Transactional
    void getNonExistingKunstwerkdeel() throws Exception {
        // Get the kunstwerkdeel
        restKunstwerkdeelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKunstwerkdeel() throws Exception {
        // Initialize the database
        kunstwerkdeelRepository.saveAndFlush(kunstwerkdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kunstwerkdeel
        Kunstwerkdeel updatedKunstwerkdeel = kunstwerkdeelRepository.findById(kunstwerkdeel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKunstwerkdeel are not directly saved in db
        em.detach(updatedKunstwerkdeel);
        updatedKunstwerkdeel
            .datumbegingeldigheidkunstwerkdeel(UPDATED_DATUMBEGINGELDIGHEIDKUNSTWERKDEEL)
            .datumeindegeldigheidkunstwerkdeel(UPDATED_DATUMEINDEGELDIGHEIDKUNSTWERKDEEL)
            .geometriekunstwerkdeel(UPDATED_GEOMETRIEKUNSTWERKDEEL)
            .identificatiekunstwerkdeel(UPDATED_IDENTIFICATIEKUNSTWERKDEEL)
            .lod0geometriekunstwerkdeel(UPDATED_LOD_0_GEOMETRIEKUNSTWERKDEEL)
            .lod1geometriekunstwerkdeel(UPDATED_LOD_1_GEOMETRIEKUNSTWERKDEEL)
            .lod2geometriekunstwerkdeel(UPDATED_LOD_2_GEOMETRIEKUNSTWERKDEEL)
            .lod3geometriekunstwerkdeel(UPDATED_LOD_3_GEOMETRIEKUNSTWERKDEEL)
            .relatievehoogteliggingkunstwerkdeel(UPDATED_RELATIEVEHOOGTELIGGINGKUNSTWERKDEEL)
            .statuskunstwerkdeel(UPDATED_STATUSKUNSTWERKDEEL);

        restKunstwerkdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKunstwerkdeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKunstwerkdeel))
            )
            .andExpect(status().isOk());

        // Validate the Kunstwerkdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKunstwerkdeelToMatchAllProperties(updatedKunstwerkdeel);
    }

    @Test
    @Transactional
    void putNonExistingKunstwerkdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kunstwerkdeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKunstwerkdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kunstwerkdeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kunstwerkdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kunstwerkdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKunstwerkdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kunstwerkdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKunstwerkdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kunstwerkdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kunstwerkdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKunstwerkdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kunstwerkdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKunstwerkdeelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kunstwerkdeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kunstwerkdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKunstwerkdeelWithPatch() throws Exception {
        // Initialize the database
        kunstwerkdeelRepository.saveAndFlush(kunstwerkdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kunstwerkdeel using partial update
        Kunstwerkdeel partialUpdatedKunstwerkdeel = new Kunstwerkdeel();
        partialUpdatedKunstwerkdeel.setId(kunstwerkdeel.getId());

        partialUpdatedKunstwerkdeel
            .datumeindegeldigheidkunstwerkdeel(UPDATED_DATUMEINDEGELDIGHEIDKUNSTWERKDEEL)
            .geometriekunstwerkdeel(UPDATED_GEOMETRIEKUNSTWERKDEEL)
            .identificatiekunstwerkdeel(UPDATED_IDENTIFICATIEKUNSTWERKDEEL)
            .lod1geometriekunstwerkdeel(UPDATED_LOD_1_GEOMETRIEKUNSTWERKDEEL)
            .lod2geometriekunstwerkdeel(UPDATED_LOD_2_GEOMETRIEKUNSTWERKDEEL)
            .lod3geometriekunstwerkdeel(UPDATED_LOD_3_GEOMETRIEKUNSTWERKDEEL)
            .statuskunstwerkdeel(UPDATED_STATUSKUNSTWERKDEEL);

        restKunstwerkdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKunstwerkdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKunstwerkdeel))
            )
            .andExpect(status().isOk());

        // Validate the Kunstwerkdeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKunstwerkdeelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKunstwerkdeel, kunstwerkdeel),
            getPersistedKunstwerkdeel(kunstwerkdeel)
        );
    }

    @Test
    @Transactional
    void fullUpdateKunstwerkdeelWithPatch() throws Exception {
        // Initialize the database
        kunstwerkdeelRepository.saveAndFlush(kunstwerkdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kunstwerkdeel using partial update
        Kunstwerkdeel partialUpdatedKunstwerkdeel = new Kunstwerkdeel();
        partialUpdatedKunstwerkdeel.setId(kunstwerkdeel.getId());

        partialUpdatedKunstwerkdeel
            .datumbegingeldigheidkunstwerkdeel(UPDATED_DATUMBEGINGELDIGHEIDKUNSTWERKDEEL)
            .datumeindegeldigheidkunstwerkdeel(UPDATED_DATUMEINDEGELDIGHEIDKUNSTWERKDEEL)
            .geometriekunstwerkdeel(UPDATED_GEOMETRIEKUNSTWERKDEEL)
            .identificatiekunstwerkdeel(UPDATED_IDENTIFICATIEKUNSTWERKDEEL)
            .lod0geometriekunstwerkdeel(UPDATED_LOD_0_GEOMETRIEKUNSTWERKDEEL)
            .lod1geometriekunstwerkdeel(UPDATED_LOD_1_GEOMETRIEKUNSTWERKDEEL)
            .lod2geometriekunstwerkdeel(UPDATED_LOD_2_GEOMETRIEKUNSTWERKDEEL)
            .lod3geometriekunstwerkdeel(UPDATED_LOD_3_GEOMETRIEKUNSTWERKDEEL)
            .relatievehoogteliggingkunstwerkdeel(UPDATED_RELATIEVEHOOGTELIGGINGKUNSTWERKDEEL)
            .statuskunstwerkdeel(UPDATED_STATUSKUNSTWERKDEEL);

        restKunstwerkdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKunstwerkdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKunstwerkdeel))
            )
            .andExpect(status().isOk());

        // Validate the Kunstwerkdeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKunstwerkdeelUpdatableFieldsEquals(partialUpdatedKunstwerkdeel, getPersistedKunstwerkdeel(partialUpdatedKunstwerkdeel));
    }

    @Test
    @Transactional
    void patchNonExistingKunstwerkdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kunstwerkdeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKunstwerkdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kunstwerkdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kunstwerkdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kunstwerkdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKunstwerkdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kunstwerkdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKunstwerkdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kunstwerkdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kunstwerkdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKunstwerkdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kunstwerkdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKunstwerkdeelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kunstwerkdeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kunstwerkdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKunstwerkdeel() throws Exception {
        // Initialize the database
        kunstwerkdeelRepository.saveAndFlush(kunstwerkdeel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kunstwerkdeel
        restKunstwerkdeelMockMvc
            .perform(delete(ENTITY_API_URL_ID, kunstwerkdeel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kunstwerkdeelRepository.count();
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

    protected Kunstwerkdeel getPersistedKunstwerkdeel(Kunstwerkdeel kunstwerkdeel) {
        return kunstwerkdeelRepository.findById(kunstwerkdeel.getId()).orElseThrow();
    }

    protected void assertPersistedKunstwerkdeelToMatchAllProperties(Kunstwerkdeel expectedKunstwerkdeel) {
        assertKunstwerkdeelAllPropertiesEquals(expectedKunstwerkdeel, getPersistedKunstwerkdeel(expectedKunstwerkdeel));
    }

    protected void assertPersistedKunstwerkdeelToMatchUpdatableProperties(Kunstwerkdeel expectedKunstwerkdeel) {
        assertKunstwerkdeelAllUpdatablePropertiesEquals(expectedKunstwerkdeel, getPersistedKunstwerkdeel(expectedKunstwerkdeel));
    }
}
