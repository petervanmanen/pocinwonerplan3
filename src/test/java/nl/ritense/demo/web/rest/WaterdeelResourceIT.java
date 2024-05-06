package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WaterdeelAsserts.*;
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
import nl.ritense.demo.domain.Waterdeel;
import nl.ritense.demo.repository.WaterdeelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WaterdeelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WaterdeelResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDWATERDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDWATERDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDWATERDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDWATERDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEOMETRIEWATERDEEL = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEWATERDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIEWATERDEEL = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIEWATERDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_PLUSTYPEWATERDEEL = "AAAAAAAAAA";
    private static final String UPDATED_PLUSTYPEWATERDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIEVEHOOGTELIGGINGWATERDEEL = "AAAAAAAAAA";
    private static final String UPDATED_RELATIEVEHOOGTELIGGINGWATERDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSWATERDEEL = "AAAAAAAAAA";
    private static final String UPDATED_STATUSWATERDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEWATERDEEL = "AAAAAAAAAA";
    private static final String UPDATED_TYPEWATERDEEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/waterdeels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WaterdeelRepository waterdeelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWaterdeelMockMvc;

    private Waterdeel waterdeel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Waterdeel createEntity(EntityManager em) {
        Waterdeel waterdeel = new Waterdeel()
            .datumbegingeldigheidwaterdeel(DEFAULT_DATUMBEGINGELDIGHEIDWATERDEEL)
            .datumeindegeldigheidwaterdeel(DEFAULT_DATUMEINDEGELDIGHEIDWATERDEEL)
            .geometriewaterdeel(DEFAULT_GEOMETRIEWATERDEEL)
            .identificatiewaterdeel(DEFAULT_IDENTIFICATIEWATERDEEL)
            .plustypewaterdeel(DEFAULT_PLUSTYPEWATERDEEL)
            .relatievehoogteliggingwaterdeel(DEFAULT_RELATIEVEHOOGTELIGGINGWATERDEEL)
            .statuswaterdeel(DEFAULT_STATUSWATERDEEL)
            .typewaterdeel(DEFAULT_TYPEWATERDEEL);
        return waterdeel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Waterdeel createUpdatedEntity(EntityManager em) {
        Waterdeel waterdeel = new Waterdeel()
            .datumbegingeldigheidwaterdeel(UPDATED_DATUMBEGINGELDIGHEIDWATERDEEL)
            .datumeindegeldigheidwaterdeel(UPDATED_DATUMEINDEGELDIGHEIDWATERDEEL)
            .geometriewaterdeel(UPDATED_GEOMETRIEWATERDEEL)
            .identificatiewaterdeel(UPDATED_IDENTIFICATIEWATERDEEL)
            .plustypewaterdeel(UPDATED_PLUSTYPEWATERDEEL)
            .relatievehoogteliggingwaterdeel(UPDATED_RELATIEVEHOOGTELIGGINGWATERDEEL)
            .statuswaterdeel(UPDATED_STATUSWATERDEEL)
            .typewaterdeel(UPDATED_TYPEWATERDEEL);
        return waterdeel;
    }

    @BeforeEach
    public void initTest() {
        waterdeel = createEntity(em);
    }

    @Test
    @Transactional
    void createWaterdeel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Waterdeel
        var returnedWaterdeel = om.readValue(
            restWaterdeelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(waterdeel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Waterdeel.class
        );

        // Validate the Waterdeel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWaterdeelUpdatableFieldsEquals(returnedWaterdeel, getPersistedWaterdeel(returnedWaterdeel));
    }

    @Test
    @Transactional
    void createWaterdeelWithExistingId() throws Exception {
        // Create the Waterdeel with an existing ID
        waterdeel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWaterdeelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(waterdeel)))
            .andExpect(status().isBadRequest());

        // Validate the Waterdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWaterdeels() throws Exception {
        // Initialize the database
        waterdeelRepository.saveAndFlush(waterdeel);

        // Get all the waterdeelList
        restWaterdeelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(waterdeel.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheidwaterdeel").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDWATERDEEL.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheidwaterdeel").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDWATERDEEL.toString())))
            .andExpect(jsonPath("$.[*].geometriewaterdeel").value(hasItem(DEFAULT_GEOMETRIEWATERDEEL)))
            .andExpect(jsonPath("$.[*].identificatiewaterdeel").value(hasItem(DEFAULT_IDENTIFICATIEWATERDEEL)))
            .andExpect(jsonPath("$.[*].plustypewaterdeel").value(hasItem(DEFAULT_PLUSTYPEWATERDEEL)))
            .andExpect(jsonPath("$.[*].relatievehoogteliggingwaterdeel").value(hasItem(DEFAULT_RELATIEVEHOOGTELIGGINGWATERDEEL)))
            .andExpect(jsonPath("$.[*].statuswaterdeel").value(hasItem(DEFAULT_STATUSWATERDEEL)))
            .andExpect(jsonPath("$.[*].typewaterdeel").value(hasItem(DEFAULT_TYPEWATERDEEL)));
    }

    @Test
    @Transactional
    void getWaterdeel() throws Exception {
        // Initialize the database
        waterdeelRepository.saveAndFlush(waterdeel);

        // Get the waterdeel
        restWaterdeelMockMvc
            .perform(get(ENTITY_API_URL_ID, waterdeel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(waterdeel.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheidwaterdeel").value(DEFAULT_DATUMBEGINGELDIGHEIDWATERDEEL.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidwaterdeel").value(DEFAULT_DATUMEINDEGELDIGHEIDWATERDEEL.toString()))
            .andExpect(jsonPath("$.geometriewaterdeel").value(DEFAULT_GEOMETRIEWATERDEEL))
            .andExpect(jsonPath("$.identificatiewaterdeel").value(DEFAULT_IDENTIFICATIEWATERDEEL))
            .andExpect(jsonPath("$.plustypewaterdeel").value(DEFAULT_PLUSTYPEWATERDEEL))
            .andExpect(jsonPath("$.relatievehoogteliggingwaterdeel").value(DEFAULT_RELATIEVEHOOGTELIGGINGWATERDEEL))
            .andExpect(jsonPath("$.statuswaterdeel").value(DEFAULT_STATUSWATERDEEL))
            .andExpect(jsonPath("$.typewaterdeel").value(DEFAULT_TYPEWATERDEEL));
    }

    @Test
    @Transactional
    void getNonExistingWaterdeel() throws Exception {
        // Get the waterdeel
        restWaterdeelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWaterdeel() throws Exception {
        // Initialize the database
        waterdeelRepository.saveAndFlush(waterdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the waterdeel
        Waterdeel updatedWaterdeel = waterdeelRepository.findById(waterdeel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWaterdeel are not directly saved in db
        em.detach(updatedWaterdeel);
        updatedWaterdeel
            .datumbegingeldigheidwaterdeel(UPDATED_DATUMBEGINGELDIGHEIDWATERDEEL)
            .datumeindegeldigheidwaterdeel(UPDATED_DATUMEINDEGELDIGHEIDWATERDEEL)
            .geometriewaterdeel(UPDATED_GEOMETRIEWATERDEEL)
            .identificatiewaterdeel(UPDATED_IDENTIFICATIEWATERDEEL)
            .plustypewaterdeel(UPDATED_PLUSTYPEWATERDEEL)
            .relatievehoogteliggingwaterdeel(UPDATED_RELATIEVEHOOGTELIGGINGWATERDEEL)
            .statuswaterdeel(UPDATED_STATUSWATERDEEL)
            .typewaterdeel(UPDATED_TYPEWATERDEEL);

        restWaterdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWaterdeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWaterdeel))
            )
            .andExpect(status().isOk());

        // Validate the Waterdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWaterdeelToMatchAllProperties(updatedWaterdeel);
    }

    @Test
    @Transactional
    void putNonExistingWaterdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterdeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWaterdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, waterdeel.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(waterdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waterdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWaterdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaterdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(waterdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waterdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWaterdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaterdeelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(waterdeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Waterdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWaterdeelWithPatch() throws Exception {
        // Initialize the database
        waterdeelRepository.saveAndFlush(waterdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the waterdeel using partial update
        Waterdeel partialUpdatedWaterdeel = new Waterdeel();
        partialUpdatedWaterdeel.setId(waterdeel.getId());

        partialUpdatedWaterdeel
            .datumbegingeldigheidwaterdeel(UPDATED_DATUMBEGINGELDIGHEIDWATERDEEL)
            .geometriewaterdeel(UPDATED_GEOMETRIEWATERDEEL)
            .identificatiewaterdeel(UPDATED_IDENTIFICATIEWATERDEEL)
            .statuswaterdeel(UPDATED_STATUSWATERDEEL)
            .typewaterdeel(UPDATED_TYPEWATERDEEL);

        restWaterdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWaterdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWaterdeel))
            )
            .andExpect(status().isOk());

        // Validate the Waterdeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWaterdeelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWaterdeel, waterdeel),
            getPersistedWaterdeel(waterdeel)
        );
    }

    @Test
    @Transactional
    void fullUpdateWaterdeelWithPatch() throws Exception {
        // Initialize the database
        waterdeelRepository.saveAndFlush(waterdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the waterdeel using partial update
        Waterdeel partialUpdatedWaterdeel = new Waterdeel();
        partialUpdatedWaterdeel.setId(waterdeel.getId());

        partialUpdatedWaterdeel
            .datumbegingeldigheidwaterdeel(UPDATED_DATUMBEGINGELDIGHEIDWATERDEEL)
            .datumeindegeldigheidwaterdeel(UPDATED_DATUMEINDEGELDIGHEIDWATERDEEL)
            .geometriewaterdeel(UPDATED_GEOMETRIEWATERDEEL)
            .identificatiewaterdeel(UPDATED_IDENTIFICATIEWATERDEEL)
            .plustypewaterdeel(UPDATED_PLUSTYPEWATERDEEL)
            .relatievehoogteliggingwaterdeel(UPDATED_RELATIEVEHOOGTELIGGINGWATERDEEL)
            .statuswaterdeel(UPDATED_STATUSWATERDEEL)
            .typewaterdeel(UPDATED_TYPEWATERDEEL);

        restWaterdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWaterdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWaterdeel))
            )
            .andExpect(status().isOk());

        // Validate the Waterdeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWaterdeelUpdatableFieldsEquals(partialUpdatedWaterdeel, getPersistedWaterdeel(partialUpdatedWaterdeel));
    }

    @Test
    @Transactional
    void patchNonExistingWaterdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterdeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWaterdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, waterdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(waterdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waterdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWaterdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaterdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(waterdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waterdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWaterdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaterdeelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(waterdeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Waterdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWaterdeel() throws Exception {
        // Initialize the database
        waterdeelRepository.saveAndFlush(waterdeel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the waterdeel
        restWaterdeelMockMvc
            .perform(delete(ENTITY_API_URL_ID, waterdeel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return waterdeelRepository.count();
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

    protected Waterdeel getPersistedWaterdeel(Waterdeel waterdeel) {
        return waterdeelRepository.findById(waterdeel.getId()).orElseThrow();
    }

    protected void assertPersistedWaterdeelToMatchAllProperties(Waterdeel expectedWaterdeel) {
        assertWaterdeelAllPropertiesEquals(expectedWaterdeel, getPersistedWaterdeel(expectedWaterdeel));
    }

    protected void assertPersistedWaterdeelToMatchUpdatableProperties(Waterdeel expectedWaterdeel) {
        assertWaterdeelAllUpdatablePropertiesEquals(expectedWaterdeel, getPersistedWaterdeel(expectedWaterdeel));
    }
}
