package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OverigescheidingAsserts.*;
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
import nl.ritense.demo.domain.Overigescheiding;
import nl.ritense.demo.repository.OverigescheidingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OverigescheidingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OverigescheidingResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDOVERIGESCHEIDING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDOVERIGESCHEIDING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDOVERIGESCHEIDING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDOVERIGESCHEIDING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEOMETRIEOVERIGESCHEIDING = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEOVERIGESCHEIDING = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIEOVERIGESCHEIDING = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIEOVERIGESCHEIDING = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_0_GEOMETRIEOVERIGESCHEIDING = "AAAAAAAAAA";
    private static final String UPDATED_LOD_0_GEOMETRIEOVERIGESCHEIDING = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_1_GEOMETRIEOVERIGESCHEIDING = "AAAAAAAAAA";
    private static final String UPDATED_LOD_1_GEOMETRIEOVERIGESCHEIDING = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_2_GEOMETRIEOVERIGESCHEIDING = "AAAAAAAAAA";
    private static final String UPDATED_LOD_2_GEOMETRIEOVERIGESCHEIDING = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_3_GEOMETRIEOVERIGESCHEIDING = "AAAAAAAAAA";
    private static final String UPDATED_LOD_3_GEOMETRIEOVERIGESCHEIDING = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIEVEHOOGTELIGGINGOVERIGESCHEIDING = "AAAAAAAAAA";
    private static final String UPDATED_RELATIEVEHOOGTELIGGINGOVERIGESCHEIDING = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSOVERIGESCHEIDING = "AAAAAAAAAA";
    private static final String UPDATED_STATUSOVERIGESCHEIDING = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEOVERIGESCHEIDING = "AAAAAAAAAA";
    private static final String UPDATED_TYPEOVERIGESCHEIDING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/overigescheidings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OverigescheidingRepository overigescheidingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOverigescheidingMockMvc;

    private Overigescheiding overigescheiding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overigescheiding createEntity(EntityManager em) {
        Overigescheiding overigescheiding = new Overigescheiding()
            .datumbegingeldigheidoverigescheiding(DEFAULT_DATUMBEGINGELDIGHEIDOVERIGESCHEIDING)
            .datumeindegeldigheidoverigescheiding(DEFAULT_DATUMEINDEGELDIGHEIDOVERIGESCHEIDING)
            .geometrieoverigescheiding(DEFAULT_GEOMETRIEOVERIGESCHEIDING)
            .identificatieoverigescheiding(DEFAULT_IDENTIFICATIEOVERIGESCHEIDING)
            .lod0geometrieoverigescheiding(DEFAULT_LOD_0_GEOMETRIEOVERIGESCHEIDING)
            .lod1geometrieoverigescheiding(DEFAULT_LOD_1_GEOMETRIEOVERIGESCHEIDING)
            .lod2geometrieoverigescheiding(DEFAULT_LOD_2_GEOMETRIEOVERIGESCHEIDING)
            .lod3geometrieoverigescheiding(DEFAULT_LOD_3_GEOMETRIEOVERIGESCHEIDING)
            .relatievehoogteliggingoverigescheiding(DEFAULT_RELATIEVEHOOGTELIGGINGOVERIGESCHEIDING)
            .statusoverigescheiding(DEFAULT_STATUSOVERIGESCHEIDING)
            .typeoverigescheiding(DEFAULT_TYPEOVERIGESCHEIDING);
        return overigescheiding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overigescheiding createUpdatedEntity(EntityManager em) {
        Overigescheiding overigescheiding = new Overigescheiding()
            .datumbegingeldigheidoverigescheiding(UPDATED_DATUMBEGINGELDIGHEIDOVERIGESCHEIDING)
            .datumeindegeldigheidoverigescheiding(UPDATED_DATUMEINDEGELDIGHEIDOVERIGESCHEIDING)
            .geometrieoverigescheiding(UPDATED_GEOMETRIEOVERIGESCHEIDING)
            .identificatieoverigescheiding(UPDATED_IDENTIFICATIEOVERIGESCHEIDING)
            .lod0geometrieoverigescheiding(UPDATED_LOD_0_GEOMETRIEOVERIGESCHEIDING)
            .lod1geometrieoverigescheiding(UPDATED_LOD_1_GEOMETRIEOVERIGESCHEIDING)
            .lod2geometrieoverigescheiding(UPDATED_LOD_2_GEOMETRIEOVERIGESCHEIDING)
            .lod3geometrieoverigescheiding(UPDATED_LOD_3_GEOMETRIEOVERIGESCHEIDING)
            .relatievehoogteliggingoverigescheiding(UPDATED_RELATIEVEHOOGTELIGGINGOVERIGESCHEIDING)
            .statusoverigescheiding(UPDATED_STATUSOVERIGESCHEIDING)
            .typeoverigescheiding(UPDATED_TYPEOVERIGESCHEIDING);
        return overigescheiding;
    }

    @BeforeEach
    public void initTest() {
        overigescheiding = createEntity(em);
    }

    @Test
    @Transactional
    void createOverigescheiding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Overigescheiding
        var returnedOverigescheiding = om.readValue(
            restOverigescheidingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overigescheiding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Overigescheiding.class
        );

        // Validate the Overigescheiding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOverigescheidingUpdatableFieldsEquals(returnedOverigescheiding, getPersistedOverigescheiding(returnedOverigescheiding));
    }

    @Test
    @Transactional
    void createOverigescheidingWithExistingId() throws Exception {
        // Create the Overigescheiding with an existing ID
        overigescheiding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOverigescheidingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overigescheiding)))
            .andExpect(status().isBadRequest());

        // Validate the Overigescheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOverigescheidings() throws Exception {
        // Initialize the database
        overigescheidingRepository.saveAndFlush(overigescheiding);

        // Get all the overigescheidingList
        restOverigescheidingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overigescheiding.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidoverigescheiding").value(
                    hasItem(DEFAULT_DATUMBEGINGELDIGHEIDOVERIGESCHEIDING.toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidoverigescheiding").value(
                    hasItem(DEFAULT_DATUMEINDEGELDIGHEIDOVERIGESCHEIDING.toString())
                )
            )
            .andExpect(jsonPath("$.[*].geometrieoverigescheiding").value(hasItem(DEFAULT_GEOMETRIEOVERIGESCHEIDING)))
            .andExpect(jsonPath("$.[*].identificatieoverigescheiding").value(hasItem(DEFAULT_IDENTIFICATIEOVERIGESCHEIDING)))
            .andExpect(jsonPath("$.[*].lod0geometrieoverigescheiding").value(hasItem(DEFAULT_LOD_0_GEOMETRIEOVERIGESCHEIDING)))
            .andExpect(jsonPath("$.[*].lod1geometrieoverigescheiding").value(hasItem(DEFAULT_LOD_1_GEOMETRIEOVERIGESCHEIDING)))
            .andExpect(jsonPath("$.[*].lod2geometrieoverigescheiding").value(hasItem(DEFAULT_LOD_2_GEOMETRIEOVERIGESCHEIDING)))
            .andExpect(jsonPath("$.[*].lod3geometrieoverigescheiding").value(hasItem(DEFAULT_LOD_3_GEOMETRIEOVERIGESCHEIDING)))
            .andExpect(
                jsonPath("$.[*].relatievehoogteliggingoverigescheiding").value(hasItem(DEFAULT_RELATIEVEHOOGTELIGGINGOVERIGESCHEIDING))
            )
            .andExpect(jsonPath("$.[*].statusoverigescheiding").value(hasItem(DEFAULT_STATUSOVERIGESCHEIDING)))
            .andExpect(jsonPath("$.[*].typeoverigescheiding").value(hasItem(DEFAULT_TYPEOVERIGESCHEIDING)));
    }

    @Test
    @Transactional
    void getOverigescheiding() throws Exception {
        // Initialize the database
        overigescheidingRepository.saveAndFlush(overigescheiding);

        // Get the overigescheiding
        restOverigescheidingMockMvc
            .perform(get(ENTITY_API_URL_ID, overigescheiding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(overigescheiding.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheidoverigescheiding").value(DEFAULT_DATUMBEGINGELDIGHEIDOVERIGESCHEIDING.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidoverigescheiding").value(DEFAULT_DATUMEINDEGELDIGHEIDOVERIGESCHEIDING.toString()))
            .andExpect(jsonPath("$.geometrieoverigescheiding").value(DEFAULT_GEOMETRIEOVERIGESCHEIDING))
            .andExpect(jsonPath("$.identificatieoverigescheiding").value(DEFAULT_IDENTIFICATIEOVERIGESCHEIDING))
            .andExpect(jsonPath("$.lod0geometrieoverigescheiding").value(DEFAULT_LOD_0_GEOMETRIEOVERIGESCHEIDING))
            .andExpect(jsonPath("$.lod1geometrieoverigescheiding").value(DEFAULT_LOD_1_GEOMETRIEOVERIGESCHEIDING))
            .andExpect(jsonPath("$.lod2geometrieoverigescheiding").value(DEFAULT_LOD_2_GEOMETRIEOVERIGESCHEIDING))
            .andExpect(jsonPath("$.lod3geometrieoverigescheiding").value(DEFAULT_LOD_3_GEOMETRIEOVERIGESCHEIDING))
            .andExpect(jsonPath("$.relatievehoogteliggingoverigescheiding").value(DEFAULT_RELATIEVEHOOGTELIGGINGOVERIGESCHEIDING))
            .andExpect(jsonPath("$.statusoverigescheiding").value(DEFAULT_STATUSOVERIGESCHEIDING))
            .andExpect(jsonPath("$.typeoverigescheiding").value(DEFAULT_TYPEOVERIGESCHEIDING));
    }

    @Test
    @Transactional
    void getNonExistingOverigescheiding() throws Exception {
        // Get the overigescheiding
        restOverigescheidingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOverigescheiding() throws Exception {
        // Initialize the database
        overigescheidingRepository.saveAndFlush(overigescheiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overigescheiding
        Overigescheiding updatedOverigescheiding = overigescheidingRepository.findById(overigescheiding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOverigescheiding are not directly saved in db
        em.detach(updatedOverigescheiding);
        updatedOverigescheiding
            .datumbegingeldigheidoverigescheiding(UPDATED_DATUMBEGINGELDIGHEIDOVERIGESCHEIDING)
            .datumeindegeldigheidoverigescheiding(UPDATED_DATUMEINDEGELDIGHEIDOVERIGESCHEIDING)
            .geometrieoverigescheiding(UPDATED_GEOMETRIEOVERIGESCHEIDING)
            .identificatieoverigescheiding(UPDATED_IDENTIFICATIEOVERIGESCHEIDING)
            .lod0geometrieoverigescheiding(UPDATED_LOD_0_GEOMETRIEOVERIGESCHEIDING)
            .lod1geometrieoverigescheiding(UPDATED_LOD_1_GEOMETRIEOVERIGESCHEIDING)
            .lod2geometrieoverigescheiding(UPDATED_LOD_2_GEOMETRIEOVERIGESCHEIDING)
            .lod3geometrieoverigescheiding(UPDATED_LOD_3_GEOMETRIEOVERIGESCHEIDING)
            .relatievehoogteliggingoverigescheiding(UPDATED_RELATIEVEHOOGTELIGGINGOVERIGESCHEIDING)
            .statusoverigescheiding(UPDATED_STATUSOVERIGESCHEIDING)
            .typeoverigescheiding(UPDATED_TYPEOVERIGESCHEIDING);

        restOverigescheidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOverigescheiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOverigescheiding))
            )
            .andExpect(status().isOk());

        // Validate the Overigescheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOverigescheidingToMatchAllProperties(updatedOverigescheiding);
    }

    @Test
    @Transactional
    void putNonExistingOverigescheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigescheiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverigescheidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, overigescheiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overigescheiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overigescheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOverigescheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigescheiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverigescheidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overigescheiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overigescheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOverigescheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigescheiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverigescheidingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overigescheiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overigescheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOverigescheidingWithPatch() throws Exception {
        // Initialize the database
        overigescheidingRepository.saveAndFlush(overigescheiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overigescheiding using partial update
        Overigescheiding partialUpdatedOverigescheiding = new Overigescheiding();
        partialUpdatedOverigescheiding.setId(overigescheiding.getId());

        partialUpdatedOverigescheiding
            .geometrieoverigescheiding(UPDATED_GEOMETRIEOVERIGESCHEIDING)
            .identificatieoverigescheiding(UPDATED_IDENTIFICATIEOVERIGESCHEIDING)
            .lod2geometrieoverigescheiding(UPDATED_LOD_2_GEOMETRIEOVERIGESCHEIDING)
            .statusoverigescheiding(UPDATED_STATUSOVERIGESCHEIDING)
            .typeoverigescheiding(UPDATED_TYPEOVERIGESCHEIDING);

        restOverigescheidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverigescheiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverigescheiding))
            )
            .andExpect(status().isOk());

        // Validate the Overigescheiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverigescheidingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOverigescheiding, overigescheiding),
            getPersistedOverigescheiding(overigescheiding)
        );
    }

    @Test
    @Transactional
    void fullUpdateOverigescheidingWithPatch() throws Exception {
        // Initialize the database
        overigescheidingRepository.saveAndFlush(overigescheiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overigescheiding using partial update
        Overigescheiding partialUpdatedOverigescheiding = new Overigescheiding();
        partialUpdatedOverigescheiding.setId(overigescheiding.getId());

        partialUpdatedOverigescheiding
            .datumbegingeldigheidoverigescheiding(UPDATED_DATUMBEGINGELDIGHEIDOVERIGESCHEIDING)
            .datumeindegeldigheidoverigescheiding(UPDATED_DATUMEINDEGELDIGHEIDOVERIGESCHEIDING)
            .geometrieoverigescheiding(UPDATED_GEOMETRIEOVERIGESCHEIDING)
            .identificatieoverigescheiding(UPDATED_IDENTIFICATIEOVERIGESCHEIDING)
            .lod0geometrieoverigescheiding(UPDATED_LOD_0_GEOMETRIEOVERIGESCHEIDING)
            .lod1geometrieoverigescheiding(UPDATED_LOD_1_GEOMETRIEOVERIGESCHEIDING)
            .lod2geometrieoverigescheiding(UPDATED_LOD_2_GEOMETRIEOVERIGESCHEIDING)
            .lod3geometrieoverigescheiding(UPDATED_LOD_3_GEOMETRIEOVERIGESCHEIDING)
            .relatievehoogteliggingoverigescheiding(UPDATED_RELATIEVEHOOGTELIGGINGOVERIGESCHEIDING)
            .statusoverigescheiding(UPDATED_STATUSOVERIGESCHEIDING)
            .typeoverigescheiding(UPDATED_TYPEOVERIGESCHEIDING);

        restOverigescheidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverigescheiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverigescheiding))
            )
            .andExpect(status().isOk());

        // Validate the Overigescheiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverigescheidingUpdatableFieldsEquals(
            partialUpdatedOverigescheiding,
            getPersistedOverigescheiding(partialUpdatedOverigescheiding)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOverigescheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigescheiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverigescheidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, overigescheiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overigescheiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overigescheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOverigescheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigescheiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverigescheidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overigescheiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overigescheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOverigescheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigescheiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverigescheidingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(overigescheiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overigescheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOverigescheiding() throws Exception {
        // Initialize the database
        overigescheidingRepository.saveAndFlush(overigescheiding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the overigescheiding
        restOverigescheidingMockMvc
            .perform(delete(ENTITY_API_URL_ID, overigescheiding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return overigescheidingRepository.count();
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

    protected Overigescheiding getPersistedOverigescheiding(Overigescheiding overigescheiding) {
        return overigescheidingRepository.findById(overigescheiding.getId()).orElseThrow();
    }

    protected void assertPersistedOverigescheidingToMatchAllProperties(Overigescheiding expectedOverigescheiding) {
        assertOverigescheidingAllPropertiesEquals(expectedOverigescheiding, getPersistedOverigescheiding(expectedOverigescheiding));
    }

    protected void assertPersistedOverigescheidingToMatchUpdatableProperties(Overigescheiding expectedOverigescheiding) {
        assertOverigescheidingAllUpdatablePropertiesEquals(
            expectedOverigescheiding,
            getPersistedOverigescheiding(expectedOverigescheiding)
        );
    }
}
