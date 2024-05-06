package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OverigbouwwerkAsserts.*;
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
import nl.ritense.demo.domain.Overigbouwwerk;
import nl.ritense.demo.repository.OverigbouwwerkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OverigbouwwerkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OverigbouwwerkResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDOVERIGBOUWWERK = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDOVERIGBOUWWERK = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDOVERIGBOUWWERK = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDOVERIGBOUWWERK = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEOMETRIEOVERIGBOUWWERK = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEOVERIGBOUWWERK = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIEOVERIGBOUWWERK = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIEOVERIGBOUWWERK = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_0_GEOMETRIEOVERIGBOUWWERK = "AAAAAAAAAA";
    private static final String UPDATED_LOD_0_GEOMETRIEOVERIGBOUWWERK = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_1_GEOMETRIEOVERIGBOUWWERK = "AAAAAAAAAA";
    private static final String UPDATED_LOD_1_GEOMETRIEOVERIGBOUWWERK = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_2_GEOMETRIEOVERIGBOUWWERK = "AAAAAAAAAA";
    private static final String UPDATED_LOD_2_GEOMETRIEOVERIGBOUWWERK = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_3_GEOMETRIEOVERIGBOUWWERK = "AAAAAAAAAA";
    private static final String UPDATED_LOD_3_GEOMETRIEOVERIGBOUWWERK = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIEVEHOOGTELIGGINGOVERIGBOUWWERK = "AAAAAAAAAA";
    private static final String UPDATED_RELATIEVEHOOGTELIGGINGOVERIGBOUWWERK = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSOVERIGBOUWWERK = "AAAAAAAAAA";
    private static final String UPDATED_STATUSOVERIGBOUWWERK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/overigbouwwerks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OverigbouwwerkRepository overigbouwwerkRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOverigbouwwerkMockMvc;

    private Overigbouwwerk overigbouwwerk;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overigbouwwerk createEntity(EntityManager em) {
        Overigbouwwerk overigbouwwerk = new Overigbouwwerk()
            .datumbegingeldigheidoverigbouwwerk(DEFAULT_DATUMBEGINGELDIGHEIDOVERIGBOUWWERK)
            .datumeindegeldigheidoverigbouwwerk(DEFAULT_DATUMEINDEGELDIGHEIDOVERIGBOUWWERK)
            .geometrieoverigbouwwerk(DEFAULT_GEOMETRIEOVERIGBOUWWERK)
            .identificatieoverigbouwwerk(DEFAULT_IDENTIFICATIEOVERIGBOUWWERK)
            .lod0geometrieoverigbouwwerk(DEFAULT_LOD_0_GEOMETRIEOVERIGBOUWWERK)
            .lod1geometrieoverigbouwwerk(DEFAULT_LOD_1_GEOMETRIEOVERIGBOUWWERK)
            .lod2geometrieoverigbouwwerk(DEFAULT_LOD_2_GEOMETRIEOVERIGBOUWWERK)
            .lod3geometrieoverigbouwwerk(DEFAULT_LOD_3_GEOMETRIEOVERIGBOUWWERK)
            .relatievehoogteliggingoverigbouwwerk(DEFAULT_RELATIEVEHOOGTELIGGINGOVERIGBOUWWERK)
            .statusoverigbouwwerk(DEFAULT_STATUSOVERIGBOUWWERK);
        return overigbouwwerk;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overigbouwwerk createUpdatedEntity(EntityManager em) {
        Overigbouwwerk overigbouwwerk = new Overigbouwwerk()
            .datumbegingeldigheidoverigbouwwerk(UPDATED_DATUMBEGINGELDIGHEIDOVERIGBOUWWERK)
            .datumeindegeldigheidoverigbouwwerk(UPDATED_DATUMEINDEGELDIGHEIDOVERIGBOUWWERK)
            .geometrieoverigbouwwerk(UPDATED_GEOMETRIEOVERIGBOUWWERK)
            .identificatieoverigbouwwerk(UPDATED_IDENTIFICATIEOVERIGBOUWWERK)
            .lod0geometrieoverigbouwwerk(UPDATED_LOD_0_GEOMETRIEOVERIGBOUWWERK)
            .lod1geometrieoverigbouwwerk(UPDATED_LOD_1_GEOMETRIEOVERIGBOUWWERK)
            .lod2geometrieoverigbouwwerk(UPDATED_LOD_2_GEOMETRIEOVERIGBOUWWERK)
            .lod3geometrieoverigbouwwerk(UPDATED_LOD_3_GEOMETRIEOVERIGBOUWWERK)
            .relatievehoogteliggingoverigbouwwerk(UPDATED_RELATIEVEHOOGTELIGGINGOVERIGBOUWWERK)
            .statusoverigbouwwerk(UPDATED_STATUSOVERIGBOUWWERK);
        return overigbouwwerk;
    }

    @BeforeEach
    public void initTest() {
        overigbouwwerk = createEntity(em);
    }

    @Test
    @Transactional
    void createOverigbouwwerk() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Overigbouwwerk
        var returnedOverigbouwwerk = om.readValue(
            restOverigbouwwerkMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overigbouwwerk)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Overigbouwwerk.class
        );

        // Validate the Overigbouwwerk in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOverigbouwwerkUpdatableFieldsEquals(returnedOverigbouwwerk, getPersistedOverigbouwwerk(returnedOverigbouwwerk));
    }

    @Test
    @Transactional
    void createOverigbouwwerkWithExistingId() throws Exception {
        // Create the Overigbouwwerk with an existing ID
        overigbouwwerk.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOverigbouwwerkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overigbouwwerk)))
            .andExpect(status().isBadRequest());

        // Validate the Overigbouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOverigbouwwerks() throws Exception {
        // Initialize the database
        overigbouwwerkRepository.saveAndFlush(overigbouwwerk);

        // Get all the overigbouwwerkList
        restOverigbouwwerkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overigbouwwerk.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidoverigbouwwerk").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDOVERIGBOUWWERK.toString()))
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidoverigbouwwerk").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDOVERIGBOUWWERK.toString()))
            )
            .andExpect(jsonPath("$.[*].geometrieoverigbouwwerk").value(hasItem(DEFAULT_GEOMETRIEOVERIGBOUWWERK)))
            .andExpect(jsonPath("$.[*].identificatieoverigbouwwerk").value(hasItem(DEFAULT_IDENTIFICATIEOVERIGBOUWWERK)))
            .andExpect(jsonPath("$.[*].lod0geometrieoverigbouwwerk").value(hasItem(DEFAULT_LOD_0_GEOMETRIEOVERIGBOUWWERK)))
            .andExpect(jsonPath("$.[*].lod1geometrieoverigbouwwerk").value(hasItem(DEFAULT_LOD_1_GEOMETRIEOVERIGBOUWWERK)))
            .andExpect(jsonPath("$.[*].lod2geometrieoverigbouwwerk").value(hasItem(DEFAULT_LOD_2_GEOMETRIEOVERIGBOUWWERK)))
            .andExpect(jsonPath("$.[*].lod3geometrieoverigbouwwerk").value(hasItem(DEFAULT_LOD_3_GEOMETRIEOVERIGBOUWWERK)))
            .andExpect(jsonPath("$.[*].relatievehoogteliggingoverigbouwwerk").value(hasItem(DEFAULT_RELATIEVEHOOGTELIGGINGOVERIGBOUWWERK)))
            .andExpect(jsonPath("$.[*].statusoverigbouwwerk").value(hasItem(DEFAULT_STATUSOVERIGBOUWWERK)));
    }

    @Test
    @Transactional
    void getOverigbouwwerk() throws Exception {
        // Initialize the database
        overigbouwwerkRepository.saveAndFlush(overigbouwwerk);

        // Get the overigbouwwerk
        restOverigbouwwerkMockMvc
            .perform(get(ENTITY_API_URL_ID, overigbouwwerk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(overigbouwwerk.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheidoverigbouwwerk").value(DEFAULT_DATUMBEGINGELDIGHEIDOVERIGBOUWWERK.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidoverigbouwwerk").value(DEFAULT_DATUMEINDEGELDIGHEIDOVERIGBOUWWERK.toString()))
            .andExpect(jsonPath("$.geometrieoverigbouwwerk").value(DEFAULT_GEOMETRIEOVERIGBOUWWERK))
            .andExpect(jsonPath("$.identificatieoverigbouwwerk").value(DEFAULT_IDENTIFICATIEOVERIGBOUWWERK))
            .andExpect(jsonPath("$.lod0geometrieoverigbouwwerk").value(DEFAULT_LOD_0_GEOMETRIEOVERIGBOUWWERK))
            .andExpect(jsonPath("$.lod1geometrieoverigbouwwerk").value(DEFAULT_LOD_1_GEOMETRIEOVERIGBOUWWERK))
            .andExpect(jsonPath("$.lod2geometrieoverigbouwwerk").value(DEFAULT_LOD_2_GEOMETRIEOVERIGBOUWWERK))
            .andExpect(jsonPath("$.lod3geometrieoverigbouwwerk").value(DEFAULT_LOD_3_GEOMETRIEOVERIGBOUWWERK))
            .andExpect(jsonPath("$.relatievehoogteliggingoverigbouwwerk").value(DEFAULT_RELATIEVEHOOGTELIGGINGOVERIGBOUWWERK))
            .andExpect(jsonPath("$.statusoverigbouwwerk").value(DEFAULT_STATUSOVERIGBOUWWERK));
    }

    @Test
    @Transactional
    void getNonExistingOverigbouwwerk() throws Exception {
        // Get the overigbouwwerk
        restOverigbouwwerkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOverigbouwwerk() throws Exception {
        // Initialize the database
        overigbouwwerkRepository.saveAndFlush(overigbouwwerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overigbouwwerk
        Overigbouwwerk updatedOverigbouwwerk = overigbouwwerkRepository.findById(overigbouwwerk.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOverigbouwwerk are not directly saved in db
        em.detach(updatedOverigbouwwerk);
        updatedOverigbouwwerk
            .datumbegingeldigheidoverigbouwwerk(UPDATED_DATUMBEGINGELDIGHEIDOVERIGBOUWWERK)
            .datumeindegeldigheidoverigbouwwerk(UPDATED_DATUMEINDEGELDIGHEIDOVERIGBOUWWERK)
            .geometrieoverigbouwwerk(UPDATED_GEOMETRIEOVERIGBOUWWERK)
            .identificatieoverigbouwwerk(UPDATED_IDENTIFICATIEOVERIGBOUWWERK)
            .lod0geometrieoverigbouwwerk(UPDATED_LOD_0_GEOMETRIEOVERIGBOUWWERK)
            .lod1geometrieoverigbouwwerk(UPDATED_LOD_1_GEOMETRIEOVERIGBOUWWERK)
            .lod2geometrieoverigbouwwerk(UPDATED_LOD_2_GEOMETRIEOVERIGBOUWWERK)
            .lod3geometrieoverigbouwwerk(UPDATED_LOD_3_GEOMETRIEOVERIGBOUWWERK)
            .relatievehoogteliggingoverigbouwwerk(UPDATED_RELATIEVEHOOGTELIGGINGOVERIGBOUWWERK)
            .statusoverigbouwwerk(UPDATED_STATUSOVERIGBOUWWERK);

        restOverigbouwwerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOverigbouwwerk.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOverigbouwwerk))
            )
            .andExpect(status().isOk());

        // Validate the Overigbouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOverigbouwwerkToMatchAllProperties(updatedOverigbouwwerk);
    }

    @Test
    @Transactional
    void putNonExistingOverigbouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigbouwwerk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverigbouwwerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, overigbouwwerk.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overigbouwwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overigbouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOverigbouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigbouwwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverigbouwwerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overigbouwwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overigbouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOverigbouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigbouwwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverigbouwwerkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overigbouwwerk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overigbouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOverigbouwwerkWithPatch() throws Exception {
        // Initialize the database
        overigbouwwerkRepository.saveAndFlush(overigbouwwerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overigbouwwerk using partial update
        Overigbouwwerk partialUpdatedOverigbouwwerk = new Overigbouwwerk();
        partialUpdatedOverigbouwwerk.setId(overigbouwwerk.getId());

        partialUpdatedOverigbouwwerk
            .datumbegingeldigheidoverigbouwwerk(UPDATED_DATUMBEGINGELDIGHEIDOVERIGBOUWWERK)
            .geometrieoverigbouwwerk(UPDATED_GEOMETRIEOVERIGBOUWWERK)
            .lod0geometrieoverigbouwwerk(UPDATED_LOD_0_GEOMETRIEOVERIGBOUWWERK)
            .lod1geometrieoverigbouwwerk(UPDATED_LOD_1_GEOMETRIEOVERIGBOUWWERK)
            .statusoverigbouwwerk(UPDATED_STATUSOVERIGBOUWWERK);

        restOverigbouwwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverigbouwwerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverigbouwwerk))
            )
            .andExpect(status().isOk());

        // Validate the Overigbouwwerk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverigbouwwerkUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOverigbouwwerk, overigbouwwerk),
            getPersistedOverigbouwwerk(overigbouwwerk)
        );
    }

    @Test
    @Transactional
    void fullUpdateOverigbouwwerkWithPatch() throws Exception {
        // Initialize the database
        overigbouwwerkRepository.saveAndFlush(overigbouwwerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overigbouwwerk using partial update
        Overigbouwwerk partialUpdatedOverigbouwwerk = new Overigbouwwerk();
        partialUpdatedOverigbouwwerk.setId(overigbouwwerk.getId());

        partialUpdatedOverigbouwwerk
            .datumbegingeldigheidoverigbouwwerk(UPDATED_DATUMBEGINGELDIGHEIDOVERIGBOUWWERK)
            .datumeindegeldigheidoverigbouwwerk(UPDATED_DATUMEINDEGELDIGHEIDOVERIGBOUWWERK)
            .geometrieoverigbouwwerk(UPDATED_GEOMETRIEOVERIGBOUWWERK)
            .identificatieoverigbouwwerk(UPDATED_IDENTIFICATIEOVERIGBOUWWERK)
            .lod0geometrieoverigbouwwerk(UPDATED_LOD_0_GEOMETRIEOVERIGBOUWWERK)
            .lod1geometrieoverigbouwwerk(UPDATED_LOD_1_GEOMETRIEOVERIGBOUWWERK)
            .lod2geometrieoverigbouwwerk(UPDATED_LOD_2_GEOMETRIEOVERIGBOUWWERK)
            .lod3geometrieoverigbouwwerk(UPDATED_LOD_3_GEOMETRIEOVERIGBOUWWERK)
            .relatievehoogteliggingoverigbouwwerk(UPDATED_RELATIEVEHOOGTELIGGINGOVERIGBOUWWERK)
            .statusoverigbouwwerk(UPDATED_STATUSOVERIGBOUWWERK);

        restOverigbouwwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverigbouwwerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverigbouwwerk))
            )
            .andExpect(status().isOk());

        // Validate the Overigbouwwerk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverigbouwwerkUpdatableFieldsEquals(partialUpdatedOverigbouwwerk, getPersistedOverigbouwwerk(partialUpdatedOverigbouwwerk));
    }

    @Test
    @Transactional
    void patchNonExistingOverigbouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigbouwwerk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverigbouwwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, overigbouwwerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overigbouwwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overigbouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOverigbouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigbouwwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverigbouwwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overigbouwwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overigbouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOverigbouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigbouwwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverigbouwwerkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(overigbouwwerk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overigbouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOverigbouwwerk() throws Exception {
        // Initialize the database
        overigbouwwerkRepository.saveAndFlush(overigbouwwerk);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the overigbouwwerk
        restOverigbouwwerkMockMvc
            .perform(delete(ENTITY_API_URL_ID, overigbouwwerk.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return overigbouwwerkRepository.count();
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

    protected Overigbouwwerk getPersistedOverigbouwwerk(Overigbouwwerk overigbouwwerk) {
        return overigbouwwerkRepository.findById(overigbouwwerk.getId()).orElseThrow();
    }

    protected void assertPersistedOverigbouwwerkToMatchAllProperties(Overigbouwwerk expectedOverigbouwwerk) {
        assertOverigbouwwerkAllPropertiesEquals(expectedOverigbouwwerk, getPersistedOverigbouwwerk(expectedOverigbouwwerk));
    }

    protected void assertPersistedOverigbouwwerkToMatchUpdatableProperties(Overigbouwwerk expectedOverigbouwwerk) {
        assertOverigbouwwerkAllUpdatablePropertiesEquals(expectedOverigbouwwerk, getPersistedOverigbouwwerk(expectedOverigbouwwerk));
    }
}
