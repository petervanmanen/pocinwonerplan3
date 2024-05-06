package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WegdeelAsserts.*;
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
import nl.ritense.demo.domain.Wegdeel;
import nl.ritense.demo.repository.WegdeelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WegdeelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WegdeelResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDWEGDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDWEGDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDWEGDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDWEGDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FUNCTIEWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTIEWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_FYSIEKVOORKOMENWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_FYSIEKVOORKOMENWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_GEOMETRIEWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIEWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIEWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_KRUINLIJNGEOMETRIEWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_KRUINLIJNGEOMETRIEWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_0_GEOMETRIEWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_LOD_0_GEOMETRIEWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_PLUSFUNCTIEWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_PLUSFUNCTIEWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_PLUSFYSIEKVOORKOMENWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_PLUSFYSIEKVOORKOMENWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIEVEHOOGTELIGGINGWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_RELATIEVEHOOGTELIGGINGWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_STATUSWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_WEGDEELOPTALUD = "AAAAAAAAAA";
    private static final String UPDATED_WEGDEELOPTALUD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/wegdeels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WegdeelRepository wegdeelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWegdeelMockMvc;

    private Wegdeel wegdeel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wegdeel createEntity(EntityManager em) {
        Wegdeel wegdeel = new Wegdeel()
            .datumbegingeldigheidwegdeel(DEFAULT_DATUMBEGINGELDIGHEIDWEGDEEL)
            .datumeindegeldigheidwegdeel(DEFAULT_DATUMEINDEGELDIGHEIDWEGDEEL)
            .functiewegdeel(DEFAULT_FUNCTIEWEGDEEL)
            .fysiekvoorkomenwegdeel(DEFAULT_FYSIEKVOORKOMENWEGDEEL)
            .geometriewegdeel(DEFAULT_GEOMETRIEWEGDEEL)
            .identificatiewegdeel(DEFAULT_IDENTIFICATIEWEGDEEL)
            .kruinlijngeometriewegdeel(DEFAULT_KRUINLIJNGEOMETRIEWEGDEEL)
            .lod0geometriewegdeel(DEFAULT_LOD_0_GEOMETRIEWEGDEEL)
            .plusfunctiewegdeel(DEFAULT_PLUSFUNCTIEWEGDEEL)
            .plusfysiekvoorkomenwegdeel(DEFAULT_PLUSFYSIEKVOORKOMENWEGDEEL)
            .relatievehoogteliggingwegdeel(DEFAULT_RELATIEVEHOOGTELIGGINGWEGDEEL)
            .statuswegdeel(DEFAULT_STATUSWEGDEEL)
            .wegdeeloptalud(DEFAULT_WEGDEELOPTALUD);
        return wegdeel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wegdeel createUpdatedEntity(EntityManager em) {
        Wegdeel wegdeel = new Wegdeel()
            .datumbegingeldigheidwegdeel(UPDATED_DATUMBEGINGELDIGHEIDWEGDEEL)
            .datumeindegeldigheidwegdeel(UPDATED_DATUMEINDEGELDIGHEIDWEGDEEL)
            .functiewegdeel(UPDATED_FUNCTIEWEGDEEL)
            .fysiekvoorkomenwegdeel(UPDATED_FYSIEKVOORKOMENWEGDEEL)
            .geometriewegdeel(UPDATED_GEOMETRIEWEGDEEL)
            .identificatiewegdeel(UPDATED_IDENTIFICATIEWEGDEEL)
            .kruinlijngeometriewegdeel(UPDATED_KRUINLIJNGEOMETRIEWEGDEEL)
            .lod0geometriewegdeel(UPDATED_LOD_0_GEOMETRIEWEGDEEL)
            .plusfunctiewegdeel(UPDATED_PLUSFUNCTIEWEGDEEL)
            .plusfysiekvoorkomenwegdeel(UPDATED_PLUSFYSIEKVOORKOMENWEGDEEL)
            .relatievehoogteliggingwegdeel(UPDATED_RELATIEVEHOOGTELIGGINGWEGDEEL)
            .statuswegdeel(UPDATED_STATUSWEGDEEL)
            .wegdeeloptalud(UPDATED_WEGDEELOPTALUD);
        return wegdeel;
    }

    @BeforeEach
    public void initTest() {
        wegdeel = createEntity(em);
    }

    @Test
    @Transactional
    void createWegdeel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Wegdeel
        var returnedWegdeel = om.readValue(
            restWegdeelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wegdeel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Wegdeel.class
        );

        // Validate the Wegdeel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWegdeelUpdatableFieldsEquals(returnedWegdeel, getPersistedWegdeel(returnedWegdeel));
    }

    @Test
    @Transactional
    void createWegdeelWithExistingId() throws Exception {
        // Create the Wegdeel with an existing ID
        wegdeel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWegdeelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wegdeel)))
            .andExpect(status().isBadRequest());

        // Validate the Wegdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWegdeels() throws Exception {
        // Initialize the database
        wegdeelRepository.saveAndFlush(wegdeel);

        // Get all the wegdeelList
        restWegdeelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wegdeel.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheidwegdeel").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDWEGDEEL.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheidwegdeel").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDWEGDEEL.toString())))
            .andExpect(jsonPath("$.[*].functiewegdeel").value(hasItem(DEFAULT_FUNCTIEWEGDEEL)))
            .andExpect(jsonPath("$.[*].fysiekvoorkomenwegdeel").value(hasItem(DEFAULT_FYSIEKVOORKOMENWEGDEEL)))
            .andExpect(jsonPath("$.[*].geometriewegdeel").value(hasItem(DEFAULT_GEOMETRIEWEGDEEL)))
            .andExpect(jsonPath("$.[*].identificatiewegdeel").value(hasItem(DEFAULT_IDENTIFICATIEWEGDEEL)))
            .andExpect(jsonPath("$.[*].kruinlijngeometriewegdeel").value(hasItem(DEFAULT_KRUINLIJNGEOMETRIEWEGDEEL)))
            .andExpect(jsonPath("$.[*].lod0geometriewegdeel").value(hasItem(DEFAULT_LOD_0_GEOMETRIEWEGDEEL)))
            .andExpect(jsonPath("$.[*].plusfunctiewegdeel").value(hasItem(DEFAULT_PLUSFUNCTIEWEGDEEL)))
            .andExpect(jsonPath("$.[*].plusfysiekvoorkomenwegdeel").value(hasItem(DEFAULT_PLUSFYSIEKVOORKOMENWEGDEEL)))
            .andExpect(jsonPath("$.[*].relatievehoogteliggingwegdeel").value(hasItem(DEFAULT_RELATIEVEHOOGTELIGGINGWEGDEEL)))
            .andExpect(jsonPath("$.[*].statuswegdeel").value(hasItem(DEFAULT_STATUSWEGDEEL)))
            .andExpect(jsonPath("$.[*].wegdeeloptalud").value(hasItem(DEFAULT_WEGDEELOPTALUD)));
    }

    @Test
    @Transactional
    void getWegdeel() throws Exception {
        // Initialize the database
        wegdeelRepository.saveAndFlush(wegdeel);

        // Get the wegdeel
        restWegdeelMockMvc
            .perform(get(ENTITY_API_URL_ID, wegdeel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wegdeel.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheidwegdeel").value(DEFAULT_DATUMBEGINGELDIGHEIDWEGDEEL.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidwegdeel").value(DEFAULT_DATUMEINDEGELDIGHEIDWEGDEEL.toString()))
            .andExpect(jsonPath("$.functiewegdeel").value(DEFAULT_FUNCTIEWEGDEEL))
            .andExpect(jsonPath("$.fysiekvoorkomenwegdeel").value(DEFAULT_FYSIEKVOORKOMENWEGDEEL))
            .andExpect(jsonPath("$.geometriewegdeel").value(DEFAULT_GEOMETRIEWEGDEEL))
            .andExpect(jsonPath("$.identificatiewegdeel").value(DEFAULT_IDENTIFICATIEWEGDEEL))
            .andExpect(jsonPath("$.kruinlijngeometriewegdeel").value(DEFAULT_KRUINLIJNGEOMETRIEWEGDEEL))
            .andExpect(jsonPath("$.lod0geometriewegdeel").value(DEFAULT_LOD_0_GEOMETRIEWEGDEEL))
            .andExpect(jsonPath("$.plusfunctiewegdeel").value(DEFAULT_PLUSFUNCTIEWEGDEEL))
            .andExpect(jsonPath("$.plusfysiekvoorkomenwegdeel").value(DEFAULT_PLUSFYSIEKVOORKOMENWEGDEEL))
            .andExpect(jsonPath("$.relatievehoogteliggingwegdeel").value(DEFAULT_RELATIEVEHOOGTELIGGINGWEGDEEL))
            .andExpect(jsonPath("$.statuswegdeel").value(DEFAULT_STATUSWEGDEEL))
            .andExpect(jsonPath("$.wegdeeloptalud").value(DEFAULT_WEGDEELOPTALUD));
    }

    @Test
    @Transactional
    void getNonExistingWegdeel() throws Exception {
        // Get the wegdeel
        restWegdeelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWegdeel() throws Exception {
        // Initialize the database
        wegdeelRepository.saveAndFlush(wegdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wegdeel
        Wegdeel updatedWegdeel = wegdeelRepository.findById(wegdeel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWegdeel are not directly saved in db
        em.detach(updatedWegdeel);
        updatedWegdeel
            .datumbegingeldigheidwegdeel(UPDATED_DATUMBEGINGELDIGHEIDWEGDEEL)
            .datumeindegeldigheidwegdeel(UPDATED_DATUMEINDEGELDIGHEIDWEGDEEL)
            .functiewegdeel(UPDATED_FUNCTIEWEGDEEL)
            .fysiekvoorkomenwegdeel(UPDATED_FYSIEKVOORKOMENWEGDEEL)
            .geometriewegdeel(UPDATED_GEOMETRIEWEGDEEL)
            .identificatiewegdeel(UPDATED_IDENTIFICATIEWEGDEEL)
            .kruinlijngeometriewegdeel(UPDATED_KRUINLIJNGEOMETRIEWEGDEEL)
            .lod0geometriewegdeel(UPDATED_LOD_0_GEOMETRIEWEGDEEL)
            .plusfunctiewegdeel(UPDATED_PLUSFUNCTIEWEGDEEL)
            .plusfysiekvoorkomenwegdeel(UPDATED_PLUSFYSIEKVOORKOMENWEGDEEL)
            .relatievehoogteliggingwegdeel(UPDATED_RELATIEVEHOOGTELIGGINGWEGDEEL)
            .statuswegdeel(UPDATED_STATUSWEGDEEL)
            .wegdeeloptalud(UPDATED_WEGDEELOPTALUD);

        restWegdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWegdeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWegdeel))
            )
            .andExpect(status().isOk());

        // Validate the Wegdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWegdeelToMatchAllProperties(updatedWegdeel);
    }

    @Test
    @Transactional
    void putNonExistingWegdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wegdeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWegdeelMockMvc
            .perform(put(ENTITY_API_URL_ID, wegdeel.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wegdeel)))
            .andExpect(status().isBadRequest());

        // Validate the Wegdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWegdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wegdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWegdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(wegdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wegdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWegdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wegdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWegdeelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wegdeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wegdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWegdeelWithPatch() throws Exception {
        // Initialize the database
        wegdeelRepository.saveAndFlush(wegdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wegdeel using partial update
        Wegdeel partialUpdatedWegdeel = new Wegdeel();
        partialUpdatedWegdeel.setId(wegdeel.getId());

        partialUpdatedWegdeel
            .datumbegingeldigheidwegdeel(UPDATED_DATUMBEGINGELDIGHEIDWEGDEEL)
            .datumeindegeldigheidwegdeel(UPDATED_DATUMEINDEGELDIGHEIDWEGDEEL)
            .fysiekvoorkomenwegdeel(UPDATED_FYSIEKVOORKOMENWEGDEEL)
            .geometriewegdeel(UPDATED_GEOMETRIEWEGDEEL)
            .identificatiewegdeel(UPDATED_IDENTIFICATIEWEGDEEL)
            .kruinlijngeometriewegdeel(UPDATED_KRUINLIJNGEOMETRIEWEGDEEL)
            .lod0geometriewegdeel(UPDATED_LOD_0_GEOMETRIEWEGDEEL)
            .plusfunctiewegdeel(UPDATED_PLUSFUNCTIEWEGDEEL)
            .plusfysiekvoorkomenwegdeel(UPDATED_PLUSFYSIEKVOORKOMENWEGDEEL)
            .wegdeeloptalud(UPDATED_WEGDEELOPTALUD);

        restWegdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWegdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWegdeel))
            )
            .andExpect(status().isOk());

        // Validate the Wegdeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWegdeelUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedWegdeel, wegdeel), getPersistedWegdeel(wegdeel));
    }

    @Test
    @Transactional
    void fullUpdateWegdeelWithPatch() throws Exception {
        // Initialize the database
        wegdeelRepository.saveAndFlush(wegdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wegdeel using partial update
        Wegdeel partialUpdatedWegdeel = new Wegdeel();
        partialUpdatedWegdeel.setId(wegdeel.getId());

        partialUpdatedWegdeel
            .datumbegingeldigheidwegdeel(UPDATED_DATUMBEGINGELDIGHEIDWEGDEEL)
            .datumeindegeldigheidwegdeel(UPDATED_DATUMEINDEGELDIGHEIDWEGDEEL)
            .functiewegdeel(UPDATED_FUNCTIEWEGDEEL)
            .fysiekvoorkomenwegdeel(UPDATED_FYSIEKVOORKOMENWEGDEEL)
            .geometriewegdeel(UPDATED_GEOMETRIEWEGDEEL)
            .identificatiewegdeel(UPDATED_IDENTIFICATIEWEGDEEL)
            .kruinlijngeometriewegdeel(UPDATED_KRUINLIJNGEOMETRIEWEGDEEL)
            .lod0geometriewegdeel(UPDATED_LOD_0_GEOMETRIEWEGDEEL)
            .plusfunctiewegdeel(UPDATED_PLUSFUNCTIEWEGDEEL)
            .plusfysiekvoorkomenwegdeel(UPDATED_PLUSFYSIEKVOORKOMENWEGDEEL)
            .relatievehoogteliggingwegdeel(UPDATED_RELATIEVEHOOGTELIGGINGWEGDEEL)
            .statuswegdeel(UPDATED_STATUSWEGDEEL)
            .wegdeeloptalud(UPDATED_WEGDEELOPTALUD);

        restWegdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWegdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWegdeel))
            )
            .andExpect(status().isOk());

        // Validate the Wegdeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWegdeelUpdatableFieldsEquals(partialUpdatedWegdeel, getPersistedWegdeel(partialUpdatedWegdeel));
    }

    @Test
    @Transactional
    void patchNonExistingWegdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wegdeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWegdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wegdeel.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(wegdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wegdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWegdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wegdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWegdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(wegdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wegdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWegdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wegdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWegdeelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(wegdeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wegdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWegdeel() throws Exception {
        // Initialize the database
        wegdeelRepository.saveAndFlush(wegdeel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the wegdeel
        restWegdeelMockMvc
            .perform(delete(ENTITY_API_URL_ID, wegdeel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return wegdeelRepository.count();
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

    protected Wegdeel getPersistedWegdeel(Wegdeel wegdeel) {
        return wegdeelRepository.findById(wegdeel.getId()).orElseThrow();
    }

    protected void assertPersistedWegdeelToMatchAllProperties(Wegdeel expectedWegdeel) {
        assertWegdeelAllPropertiesEquals(expectedWegdeel, getPersistedWegdeel(expectedWegdeel));
    }

    protected void assertPersistedWegdeelToMatchUpdatableProperties(Wegdeel expectedWegdeel) {
        assertWegdeelAllUpdatablePropertiesEquals(expectedWegdeel, getPersistedWegdeel(expectedWegdeel));
    }
}
