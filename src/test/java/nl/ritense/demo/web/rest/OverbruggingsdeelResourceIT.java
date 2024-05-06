package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OverbruggingsdeelAsserts.*;
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
import nl.ritense.demo.domain.Overbruggingsdeel;
import nl.ritense.demo.repository.OverbruggingsdeelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OverbruggingsdeelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OverbruggingsdeelResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDOVERBRUGGINGSDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDOVERBRUGGINGSDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDOVERBRUGGINGSDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDOVERBRUGGINGSDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEOMETRIEOVERBRUGGINGSDEEL = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEOVERBRUGGINGSDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_HOORTBIJTYPEOVERBRUGGING = "AAAAAAAAAA";
    private static final String UPDATED_HOORTBIJTYPEOVERBRUGGING = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIEOVERBRUGGINGSDEEL = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIEOVERBRUGGINGSDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_0_GEOMETRIEOVERBRUGGINGSDEEL = "AAAAAAAAAA";
    private static final String UPDATED_LOD_0_GEOMETRIEOVERBRUGGINGSDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_OVERBRUGGINGISBEWEEGBAAR = "AAAAAAAAAA";
    private static final String UPDATED_OVERBRUGGINGISBEWEEGBAAR = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIEVEHOOGTELIGGINGOVERBRUGGINGSDEEL = "AAAAAAAAAA";
    private static final String UPDATED_RELATIEVEHOOGTELIGGINGOVERBRUGGINGSDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSOVERBRUGGINGSDEEL = "AAAAAAAAAA";
    private static final String UPDATED_STATUSOVERBRUGGINGSDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEOVERBRUGGINGSDEEL = "AAAAAAAAAA";
    private static final String UPDATED_TYPEOVERBRUGGINGSDEEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/overbruggingsdeels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OverbruggingsdeelRepository overbruggingsdeelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOverbruggingsdeelMockMvc;

    private Overbruggingsdeel overbruggingsdeel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overbruggingsdeel createEntity(EntityManager em) {
        Overbruggingsdeel overbruggingsdeel = new Overbruggingsdeel()
            .datumbegingeldigheidoverbruggingsdeel(DEFAULT_DATUMBEGINGELDIGHEIDOVERBRUGGINGSDEEL)
            .datumeindegeldigheidoverbruggingsdeel(DEFAULT_DATUMEINDEGELDIGHEIDOVERBRUGGINGSDEEL)
            .geometrieoverbruggingsdeel(DEFAULT_GEOMETRIEOVERBRUGGINGSDEEL)
            .hoortbijtypeoverbrugging(DEFAULT_HOORTBIJTYPEOVERBRUGGING)
            .identificatieoverbruggingsdeel(DEFAULT_IDENTIFICATIEOVERBRUGGINGSDEEL)
            .lod0geometrieoverbruggingsdeel(DEFAULT_LOD_0_GEOMETRIEOVERBRUGGINGSDEEL)
            .overbruggingisbeweegbaar(DEFAULT_OVERBRUGGINGISBEWEEGBAAR)
            .relatievehoogteliggingoverbruggingsdeel(DEFAULT_RELATIEVEHOOGTELIGGINGOVERBRUGGINGSDEEL)
            .statusoverbruggingsdeel(DEFAULT_STATUSOVERBRUGGINGSDEEL)
            .typeoverbruggingsdeel(DEFAULT_TYPEOVERBRUGGINGSDEEL);
        return overbruggingsdeel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overbruggingsdeel createUpdatedEntity(EntityManager em) {
        Overbruggingsdeel overbruggingsdeel = new Overbruggingsdeel()
            .datumbegingeldigheidoverbruggingsdeel(UPDATED_DATUMBEGINGELDIGHEIDOVERBRUGGINGSDEEL)
            .datumeindegeldigheidoverbruggingsdeel(UPDATED_DATUMEINDEGELDIGHEIDOVERBRUGGINGSDEEL)
            .geometrieoverbruggingsdeel(UPDATED_GEOMETRIEOVERBRUGGINGSDEEL)
            .hoortbijtypeoverbrugging(UPDATED_HOORTBIJTYPEOVERBRUGGING)
            .identificatieoverbruggingsdeel(UPDATED_IDENTIFICATIEOVERBRUGGINGSDEEL)
            .lod0geometrieoverbruggingsdeel(UPDATED_LOD_0_GEOMETRIEOVERBRUGGINGSDEEL)
            .overbruggingisbeweegbaar(UPDATED_OVERBRUGGINGISBEWEEGBAAR)
            .relatievehoogteliggingoverbruggingsdeel(UPDATED_RELATIEVEHOOGTELIGGINGOVERBRUGGINGSDEEL)
            .statusoverbruggingsdeel(UPDATED_STATUSOVERBRUGGINGSDEEL)
            .typeoverbruggingsdeel(UPDATED_TYPEOVERBRUGGINGSDEEL);
        return overbruggingsdeel;
    }

    @BeforeEach
    public void initTest() {
        overbruggingsdeel = createEntity(em);
    }

    @Test
    @Transactional
    void createOverbruggingsdeel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Overbruggingsdeel
        var returnedOverbruggingsdeel = om.readValue(
            restOverbruggingsdeelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overbruggingsdeel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Overbruggingsdeel.class
        );

        // Validate the Overbruggingsdeel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOverbruggingsdeelUpdatableFieldsEquals(returnedOverbruggingsdeel, getPersistedOverbruggingsdeel(returnedOverbruggingsdeel));
    }

    @Test
    @Transactional
    void createOverbruggingsdeelWithExistingId() throws Exception {
        // Create the Overbruggingsdeel with an existing ID
        overbruggingsdeel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOverbruggingsdeelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overbruggingsdeel)))
            .andExpect(status().isBadRequest());

        // Validate the Overbruggingsdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOverbruggingsdeels() throws Exception {
        // Initialize the database
        overbruggingsdeelRepository.saveAndFlush(overbruggingsdeel);

        // Get all the overbruggingsdeelList
        restOverbruggingsdeelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overbruggingsdeel.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidoverbruggingsdeel").value(
                    hasItem(DEFAULT_DATUMBEGINGELDIGHEIDOVERBRUGGINGSDEEL.toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidoverbruggingsdeel").value(
                    hasItem(DEFAULT_DATUMEINDEGELDIGHEIDOVERBRUGGINGSDEEL.toString())
                )
            )
            .andExpect(jsonPath("$.[*].geometrieoverbruggingsdeel").value(hasItem(DEFAULT_GEOMETRIEOVERBRUGGINGSDEEL)))
            .andExpect(jsonPath("$.[*].hoortbijtypeoverbrugging").value(hasItem(DEFAULT_HOORTBIJTYPEOVERBRUGGING)))
            .andExpect(jsonPath("$.[*].identificatieoverbruggingsdeel").value(hasItem(DEFAULT_IDENTIFICATIEOVERBRUGGINGSDEEL)))
            .andExpect(jsonPath("$.[*].lod0geometrieoverbruggingsdeel").value(hasItem(DEFAULT_LOD_0_GEOMETRIEOVERBRUGGINGSDEEL)))
            .andExpect(jsonPath("$.[*].overbruggingisbeweegbaar").value(hasItem(DEFAULT_OVERBRUGGINGISBEWEEGBAAR)))
            .andExpect(
                jsonPath("$.[*].relatievehoogteliggingoverbruggingsdeel").value(hasItem(DEFAULT_RELATIEVEHOOGTELIGGINGOVERBRUGGINGSDEEL))
            )
            .andExpect(jsonPath("$.[*].statusoverbruggingsdeel").value(hasItem(DEFAULT_STATUSOVERBRUGGINGSDEEL)))
            .andExpect(jsonPath("$.[*].typeoverbruggingsdeel").value(hasItem(DEFAULT_TYPEOVERBRUGGINGSDEEL)));
    }

    @Test
    @Transactional
    void getOverbruggingsdeel() throws Exception {
        // Initialize the database
        overbruggingsdeelRepository.saveAndFlush(overbruggingsdeel);

        // Get the overbruggingsdeel
        restOverbruggingsdeelMockMvc
            .perform(get(ENTITY_API_URL_ID, overbruggingsdeel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(overbruggingsdeel.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheidoverbruggingsdeel").value(DEFAULT_DATUMBEGINGELDIGHEIDOVERBRUGGINGSDEEL.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidoverbruggingsdeel").value(DEFAULT_DATUMEINDEGELDIGHEIDOVERBRUGGINGSDEEL.toString()))
            .andExpect(jsonPath("$.geometrieoverbruggingsdeel").value(DEFAULT_GEOMETRIEOVERBRUGGINGSDEEL))
            .andExpect(jsonPath("$.hoortbijtypeoverbrugging").value(DEFAULT_HOORTBIJTYPEOVERBRUGGING))
            .andExpect(jsonPath("$.identificatieoverbruggingsdeel").value(DEFAULT_IDENTIFICATIEOVERBRUGGINGSDEEL))
            .andExpect(jsonPath("$.lod0geometrieoverbruggingsdeel").value(DEFAULT_LOD_0_GEOMETRIEOVERBRUGGINGSDEEL))
            .andExpect(jsonPath("$.overbruggingisbeweegbaar").value(DEFAULT_OVERBRUGGINGISBEWEEGBAAR))
            .andExpect(jsonPath("$.relatievehoogteliggingoverbruggingsdeel").value(DEFAULT_RELATIEVEHOOGTELIGGINGOVERBRUGGINGSDEEL))
            .andExpect(jsonPath("$.statusoverbruggingsdeel").value(DEFAULT_STATUSOVERBRUGGINGSDEEL))
            .andExpect(jsonPath("$.typeoverbruggingsdeel").value(DEFAULT_TYPEOVERBRUGGINGSDEEL));
    }

    @Test
    @Transactional
    void getNonExistingOverbruggingsdeel() throws Exception {
        // Get the overbruggingsdeel
        restOverbruggingsdeelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOverbruggingsdeel() throws Exception {
        // Initialize the database
        overbruggingsdeelRepository.saveAndFlush(overbruggingsdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overbruggingsdeel
        Overbruggingsdeel updatedOverbruggingsdeel = overbruggingsdeelRepository.findById(overbruggingsdeel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOverbruggingsdeel are not directly saved in db
        em.detach(updatedOverbruggingsdeel);
        updatedOverbruggingsdeel
            .datumbegingeldigheidoverbruggingsdeel(UPDATED_DATUMBEGINGELDIGHEIDOVERBRUGGINGSDEEL)
            .datumeindegeldigheidoverbruggingsdeel(UPDATED_DATUMEINDEGELDIGHEIDOVERBRUGGINGSDEEL)
            .geometrieoverbruggingsdeel(UPDATED_GEOMETRIEOVERBRUGGINGSDEEL)
            .hoortbijtypeoverbrugging(UPDATED_HOORTBIJTYPEOVERBRUGGING)
            .identificatieoverbruggingsdeel(UPDATED_IDENTIFICATIEOVERBRUGGINGSDEEL)
            .lod0geometrieoverbruggingsdeel(UPDATED_LOD_0_GEOMETRIEOVERBRUGGINGSDEEL)
            .overbruggingisbeweegbaar(UPDATED_OVERBRUGGINGISBEWEEGBAAR)
            .relatievehoogteliggingoverbruggingsdeel(UPDATED_RELATIEVEHOOGTELIGGINGOVERBRUGGINGSDEEL)
            .statusoverbruggingsdeel(UPDATED_STATUSOVERBRUGGINGSDEEL)
            .typeoverbruggingsdeel(UPDATED_TYPEOVERBRUGGINGSDEEL);

        restOverbruggingsdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOverbruggingsdeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOverbruggingsdeel))
            )
            .andExpect(status().isOk());

        // Validate the Overbruggingsdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOverbruggingsdeelToMatchAllProperties(updatedOverbruggingsdeel);
    }

    @Test
    @Transactional
    void putNonExistingOverbruggingsdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overbruggingsdeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverbruggingsdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, overbruggingsdeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overbruggingsdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overbruggingsdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOverbruggingsdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overbruggingsdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverbruggingsdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overbruggingsdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overbruggingsdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOverbruggingsdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overbruggingsdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverbruggingsdeelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overbruggingsdeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overbruggingsdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOverbruggingsdeelWithPatch() throws Exception {
        // Initialize the database
        overbruggingsdeelRepository.saveAndFlush(overbruggingsdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overbruggingsdeel using partial update
        Overbruggingsdeel partialUpdatedOverbruggingsdeel = new Overbruggingsdeel();
        partialUpdatedOverbruggingsdeel.setId(overbruggingsdeel.getId());

        partialUpdatedOverbruggingsdeel
            .datumbegingeldigheidoverbruggingsdeel(UPDATED_DATUMBEGINGELDIGHEIDOVERBRUGGINGSDEEL)
            .datumeindegeldigheidoverbruggingsdeel(UPDATED_DATUMEINDEGELDIGHEIDOVERBRUGGINGSDEEL)
            .geometrieoverbruggingsdeel(UPDATED_GEOMETRIEOVERBRUGGINGSDEEL)
            .identificatieoverbruggingsdeel(UPDATED_IDENTIFICATIEOVERBRUGGINGSDEEL)
            .relatievehoogteliggingoverbruggingsdeel(UPDATED_RELATIEVEHOOGTELIGGINGOVERBRUGGINGSDEEL)
            .typeoverbruggingsdeel(UPDATED_TYPEOVERBRUGGINGSDEEL);

        restOverbruggingsdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverbruggingsdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverbruggingsdeel))
            )
            .andExpect(status().isOk());

        // Validate the Overbruggingsdeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverbruggingsdeelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOverbruggingsdeel, overbruggingsdeel),
            getPersistedOverbruggingsdeel(overbruggingsdeel)
        );
    }

    @Test
    @Transactional
    void fullUpdateOverbruggingsdeelWithPatch() throws Exception {
        // Initialize the database
        overbruggingsdeelRepository.saveAndFlush(overbruggingsdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overbruggingsdeel using partial update
        Overbruggingsdeel partialUpdatedOverbruggingsdeel = new Overbruggingsdeel();
        partialUpdatedOverbruggingsdeel.setId(overbruggingsdeel.getId());

        partialUpdatedOverbruggingsdeel
            .datumbegingeldigheidoverbruggingsdeel(UPDATED_DATUMBEGINGELDIGHEIDOVERBRUGGINGSDEEL)
            .datumeindegeldigheidoverbruggingsdeel(UPDATED_DATUMEINDEGELDIGHEIDOVERBRUGGINGSDEEL)
            .geometrieoverbruggingsdeel(UPDATED_GEOMETRIEOVERBRUGGINGSDEEL)
            .hoortbijtypeoverbrugging(UPDATED_HOORTBIJTYPEOVERBRUGGING)
            .identificatieoverbruggingsdeel(UPDATED_IDENTIFICATIEOVERBRUGGINGSDEEL)
            .lod0geometrieoverbruggingsdeel(UPDATED_LOD_0_GEOMETRIEOVERBRUGGINGSDEEL)
            .overbruggingisbeweegbaar(UPDATED_OVERBRUGGINGISBEWEEGBAAR)
            .relatievehoogteliggingoverbruggingsdeel(UPDATED_RELATIEVEHOOGTELIGGINGOVERBRUGGINGSDEEL)
            .statusoverbruggingsdeel(UPDATED_STATUSOVERBRUGGINGSDEEL)
            .typeoverbruggingsdeel(UPDATED_TYPEOVERBRUGGINGSDEEL);

        restOverbruggingsdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverbruggingsdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverbruggingsdeel))
            )
            .andExpect(status().isOk());

        // Validate the Overbruggingsdeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverbruggingsdeelUpdatableFieldsEquals(
            partialUpdatedOverbruggingsdeel,
            getPersistedOverbruggingsdeel(partialUpdatedOverbruggingsdeel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOverbruggingsdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overbruggingsdeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverbruggingsdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, overbruggingsdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overbruggingsdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overbruggingsdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOverbruggingsdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overbruggingsdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverbruggingsdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overbruggingsdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overbruggingsdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOverbruggingsdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overbruggingsdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverbruggingsdeelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(overbruggingsdeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overbruggingsdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOverbruggingsdeel() throws Exception {
        // Initialize the database
        overbruggingsdeelRepository.saveAndFlush(overbruggingsdeel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the overbruggingsdeel
        restOverbruggingsdeelMockMvc
            .perform(delete(ENTITY_API_URL_ID, overbruggingsdeel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return overbruggingsdeelRepository.count();
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

    protected Overbruggingsdeel getPersistedOverbruggingsdeel(Overbruggingsdeel overbruggingsdeel) {
        return overbruggingsdeelRepository.findById(overbruggingsdeel.getId()).orElseThrow();
    }

    protected void assertPersistedOverbruggingsdeelToMatchAllProperties(Overbruggingsdeel expectedOverbruggingsdeel) {
        assertOverbruggingsdeelAllPropertiesEquals(expectedOverbruggingsdeel, getPersistedOverbruggingsdeel(expectedOverbruggingsdeel));
    }

    protected void assertPersistedOverbruggingsdeelToMatchUpdatableProperties(Overbruggingsdeel expectedOverbruggingsdeel) {
        assertOverbruggingsdeelAllUpdatablePropertiesEquals(
            expectedOverbruggingsdeel,
            getPersistedOverbruggingsdeel(expectedOverbruggingsdeel)
        );
    }
}
