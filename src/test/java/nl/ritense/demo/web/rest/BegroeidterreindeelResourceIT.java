package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BegroeidterreindeelAsserts.*;
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
import nl.ritense.demo.domain.Begroeidterreindeel;
import nl.ritense.demo.repository.BegroeidterreindeelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BegroeidterreindeelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BegroeidterreindeelResourceIT {

    private static final String DEFAULT_BEGROEIDTERREINDEELOPTALUD = "AAAAAAAAAA";
    private static final String UPDATED_BEGROEIDTERREINDEELOPTALUD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDBEGROEIDTERREINDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDBEGROEIDTERREINDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDBEGROEIDTERREINDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDBEGROEIDTERREINDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FYSIEKVOORKOMENBEGROEIDTERREINDEEL = "AAAAAAAAAA";
    private static final String UPDATED_FYSIEKVOORKOMENBEGROEIDTERREINDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_GEOMETRIEBEGROEIDTERREINDEEL = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEBEGROEIDTERREINDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIEBEGROEIDTERREINDEEL = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIEBEGROEIDTERREINDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_KRUINLIJNGEOMETRIEBEGROEIDTERREINDEEL = "AAAAAAAAAA";
    private static final String UPDATED_KRUINLIJNGEOMETRIEBEGROEIDTERREINDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_0_GEOMETRIEBEGROEIDTERREINDEEL = "AAAAAAAAAA";
    private static final String UPDATED_LOD_0_GEOMETRIEBEGROEIDTERREINDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_PLUSFYSIEKVOORKOMENBEGROEIDTERREINDEEL = "AAAAAAAAAA";
    private static final String UPDATED_PLUSFYSIEKVOORKOMENBEGROEIDTERREINDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIEVEHOOGTELIGGINGBEGROEIDTERREINDEEL = "AAAAAAAAAA";
    private static final String UPDATED_RELATIEVEHOOGTELIGGINGBEGROEIDTERREINDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSBEGROEIDTERREINDEEL = "AAAAAAAAAA";
    private static final String UPDATED_STATUSBEGROEIDTERREINDEEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/begroeidterreindeels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BegroeidterreindeelRepository begroeidterreindeelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBegroeidterreindeelMockMvc;

    private Begroeidterreindeel begroeidterreindeel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Begroeidterreindeel createEntity(EntityManager em) {
        Begroeidterreindeel begroeidterreindeel = new Begroeidterreindeel()
            .begroeidterreindeeloptalud(DEFAULT_BEGROEIDTERREINDEELOPTALUD)
            .datumbegingeldigheidbegroeidterreindeel(DEFAULT_DATUMBEGINGELDIGHEIDBEGROEIDTERREINDEEL)
            .datumeindegeldigheidbegroeidterreindeel(DEFAULT_DATUMEINDEGELDIGHEIDBEGROEIDTERREINDEEL)
            .fysiekvoorkomenbegroeidterreindeel(DEFAULT_FYSIEKVOORKOMENBEGROEIDTERREINDEEL)
            .geometriebegroeidterreindeel(DEFAULT_GEOMETRIEBEGROEIDTERREINDEEL)
            .identificatiebegroeidterreindeel(DEFAULT_IDENTIFICATIEBEGROEIDTERREINDEEL)
            .kruinlijngeometriebegroeidterreindeel(DEFAULT_KRUINLIJNGEOMETRIEBEGROEIDTERREINDEEL)
            .lod0geometriebegroeidterreindeel(DEFAULT_LOD_0_GEOMETRIEBEGROEIDTERREINDEEL)
            .plusfysiekvoorkomenbegroeidterreindeel(DEFAULT_PLUSFYSIEKVOORKOMENBEGROEIDTERREINDEEL)
            .relatievehoogteliggingbegroeidterreindeel(DEFAULT_RELATIEVEHOOGTELIGGINGBEGROEIDTERREINDEEL)
            .statusbegroeidterreindeel(DEFAULT_STATUSBEGROEIDTERREINDEEL);
        return begroeidterreindeel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Begroeidterreindeel createUpdatedEntity(EntityManager em) {
        Begroeidterreindeel begroeidterreindeel = new Begroeidterreindeel()
            .begroeidterreindeeloptalud(UPDATED_BEGROEIDTERREINDEELOPTALUD)
            .datumbegingeldigheidbegroeidterreindeel(UPDATED_DATUMBEGINGELDIGHEIDBEGROEIDTERREINDEEL)
            .datumeindegeldigheidbegroeidterreindeel(UPDATED_DATUMEINDEGELDIGHEIDBEGROEIDTERREINDEEL)
            .fysiekvoorkomenbegroeidterreindeel(UPDATED_FYSIEKVOORKOMENBEGROEIDTERREINDEEL)
            .geometriebegroeidterreindeel(UPDATED_GEOMETRIEBEGROEIDTERREINDEEL)
            .identificatiebegroeidterreindeel(UPDATED_IDENTIFICATIEBEGROEIDTERREINDEEL)
            .kruinlijngeometriebegroeidterreindeel(UPDATED_KRUINLIJNGEOMETRIEBEGROEIDTERREINDEEL)
            .lod0geometriebegroeidterreindeel(UPDATED_LOD_0_GEOMETRIEBEGROEIDTERREINDEEL)
            .plusfysiekvoorkomenbegroeidterreindeel(UPDATED_PLUSFYSIEKVOORKOMENBEGROEIDTERREINDEEL)
            .relatievehoogteliggingbegroeidterreindeel(UPDATED_RELATIEVEHOOGTELIGGINGBEGROEIDTERREINDEEL)
            .statusbegroeidterreindeel(UPDATED_STATUSBEGROEIDTERREINDEEL);
        return begroeidterreindeel;
    }

    @BeforeEach
    public void initTest() {
        begroeidterreindeel = createEntity(em);
    }

    @Test
    @Transactional
    void createBegroeidterreindeel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Begroeidterreindeel
        var returnedBegroeidterreindeel = om.readValue(
            restBegroeidterreindeelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(begroeidterreindeel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Begroeidterreindeel.class
        );

        // Validate the Begroeidterreindeel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBegroeidterreindeelUpdatableFieldsEquals(
            returnedBegroeidterreindeel,
            getPersistedBegroeidterreindeel(returnedBegroeidterreindeel)
        );
    }

    @Test
    @Transactional
    void createBegroeidterreindeelWithExistingId() throws Exception {
        // Create the Begroeidterreindeel with an existing ID
        begroeidterreindeel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBegroeidterreindeelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(begroeidterreindeel)))
            .andExpect(status().isBadRequest());

        // Validate the Begroeidterreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBegroeidterreindeels() throws Exception {
        // Initialize the database
        begroeidterreindeelRepository.saveAndFlush(begroeidterreindeel);

        // Get all the begroeidterreindeelList
        restBegroeidterreindeelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(begroeidterreindeel.getId().intValue())))
            .andExpect(jsonPath("$.[*].begroeidterreindeeloptalud").value(hasItem(DEFAULT_BEGROEIDTERREINDEELOPTALUD)))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidbegroeidterreindeel").value(
                    hasItem(DEFAULT_DATUMBEGINGELDIGHEIDBEGROEIDTERREINDEEL.toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidbegroeidterreindeel").value(
                    hasItem(DEFAULT_DATUMEINDEGELDIGHEIDBEGROEIDTERREINDEEL.toString())
                )
            )
            .andExpect(jsonPath("$.[*].fysiekvoorkomenbegroeidterreindeel").value(hasItem(DEFAULT_FYSIEKVOORKOMENBEGROEIDTERREINDEEL)))
            .andExpect(jsonPath("$.[*].geometriebegroeidterreindeel").value(hasItem(DEFAULT_GEOMETRIEBEGROEIDTERREINDEEL)))
            .andExpect(jsonPath("$.[*].identificatiebegroeidterreindeel").value(hasItem(DEFAULT_IDENTIFICATIEBEGROEIDTERREINDEEL)))
            .andExpect(
                jsonPath("$.[*].kruinlijngeometriebegroeidterreindeel").value(hasItem(DEFAULT_KRUINLIJNGEOMETRIEBEGROEIDTERREINDEEL))
            )
            .andExpect(jsonPath("$.[*].lod0geometriebegroeidterreindeel").value(hasItem(DEFAULT_LOD_0_GEOMETRIEBEGROEIDTERREINDEEL)))
            .andExpect(
                jsonPath("$.[*].plusfysiekvoorkomenbegroeidterreindeel").value(hasItem(DEFAULT_PLUSFYSIEKVOORKOMENBEGROEIDTERREINDEEL))
            )
            .andExpect(
                jsonPath("$.[*].relatievehoogteliggingbegroeidterreindeel").value(
                    hasItem(DEFAULT_RELATIEVEHOOGTELIGGINGBEGROEIDTERREINDEEL)
                )
            )
            .andExpect(jsonPath("$.[*].statusbegroeidterreindeel").value(hasItem(DEFAULT_STATUSBEGROEIDTERREINDEEL)));
    }

    @Test
    @Transactional
    void getBegroeidterreindeel() throws Exception {
        // Initialize the database
        begroeidterreindeelRepository.saveAndFlush(begroeidterreindeel);

        // Get the begroeidterreindeel
        restBegroeidterreindeelMockMvc
            .perform(get(ENTITY_API_URL_ID, begroeidterreindeel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(begroeidterreindeel.getId().intValue()))
            .andExpect(jsonPath("$.begroeidterreindeeloptalud").value(DEFAULT_BEGROEIDTERREINDEELOPTALUD))
            .andExpect(
                jsonPath("$.datumbegingeldigheidbegroeidterreindeel").value(DEFAULT_DATUMBEGINGELDIGHEIDBEGROEIDTERREINDEEL.toString())
            )
            .andExpect(
                jsonPath("$.datumeindegeldigheidbegroeidterreindeel").value(DEFAULT_DATUMEINDEGELDIGHEIDBEGROEIDTERREINDEEL.toString())
            )
            .andExpect(jsonPath("$.fysiekvoorkomenbegroeidterreindeel").value(DEFAULT_FYSIEKVOORKOMENBEGROEIDTERREINDEEL))
            .andExpect(jsonPath("$.geometriebegroeidterreindeel").value(DEFAULT_GEOMETRIEBEGROEIDTERREINDEEL))
            .andExpect(jsonPath("$.identificatiebegroeidterreindeel").value(DEFAULT_IDENTIFICATIEBEGROEIDTERREINDEEL))
            .andExpect(jsonPath("$.kruinlijngeometriebegroeidterreindeel").value(DEFAULT_KRUINLIJNGEOMETRIEBEGROEIDTERREINDEEL))
            .andExpect(jsonPath("$.lod0geometriebegroeidterreindeel").value(DEFAULT_LOD_0_GEOMETRIEBEGROEIDTERREINDEEL))
            .andExpect(jsonPath("$.plusfysiekvoorkomenbegroeidterreindeel").value(DEFAULT_PLUSFYSIEKVOORKOMENBEGROEIDTERREINDEEL))
            .andExpect(jsonPath("$.relatievehoogteliggingbegroeidterreindeel").value(DEFAULT_RELATIEVEHOOGTELIGGINGBEGROEIDTERREINDEEL))
            .andExpect(jsonPath("$.statusbegroeidterreindeel").value(DEFAULT_STATUSBEGROEIDTERREINDEEL));
    }

    @Test
    @Transactional
    void getNonExistingBegroeidterreindeel() throws Exception {
        // Get the begroeidterreindeel
        restBegroeidterreindeelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBegroeidterreindeel() throws Exception {
        // Initialize the database
        begroeidterreindeelRepository.saveAndFlush(begroeidterreindeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the begroeidterreindeel
        Begroeidterreindeel updatedBegroeidterreindeel = begroeidterreindeelRepository.findById(begroeidterreindeel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBegroeidterreindeel are not directly saved in db
        em.detach(updatedBegroeidterreindeel);
        updatedBegroeidterreindeel
            .begroeidterreindeeloptalud(UPDATED_BEGROEIDTERREINDEELOPTALUD)
            .datumbegingeldigheidbegroeidterreindeel(UPDATED_DATUMBEGINGELDIGHEIDBEGROEIDTERREINDEEL)
            .datumeindegeldigheidbegroeidterreindeel(UPDATED_DATUMEINDEGELDIGHEIDBEGROEIDTERREINDEEL)
            .fysiekvoorkomenbegroeidterreindeel(UPDATED_FYSIEKVOORKOMENBEGROEIDTERREINDEEL)
            .geometriebegroeidterreindeel(UPDATED_GEOMETRIEBEGROEIDTERREINDEEL)
            .identificatiebegroeidterreindeel(UPDATED_IDENTIFICATIEBEGROEIDTERREINDEEL)
            .kruinlijngeometriebegroeidterreindeel(UPDATED_KRUINLIJNGEOMETRIEBEGROEIDTERREINDEEL)
            .lod0geometriebegroeidterreindeel(UPDATED_LOD_0_GEOMETRIEBEGROEIDTERREINDEEL)
            .plusfysiekvoorkomenbegroeidterreindeel(UPDATED_PLUSFYSIEKVOORKOMENBEGROEIDTERREINDEEL)
            .relatievehoogteliggingbegroeidterreindeel(UPDATED_RELATIEVEHOOGTELIGGINGBEGROEIDTERREINDEEL)
            .statusbegroeidterreindeel(UPDATED_STATUSBEGROEIDTERREINDEEL);

        restBegroeidterreindeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBegroeidterreindeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBegroeidterreindeel))
            )
            .andExpect(status().isOk());

        // Validate the Begroeidterreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBegroeidterreindeelToMatchAllProperties(updatedBegroeidterreindeel);
    }

    @Test
    @Transactional
    void putNonExistingBegroeidterreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begroeidterreindeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBegroeidterreindeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, begroeidterreindeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(begroeidterreindeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Begroeidterreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBegroeidterreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begroeidterreindeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBegroeidterreindeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(begroeidterreindeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Begroeidterreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBegroeidterreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begroeidterreindeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBegroeidterreindeelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(begroeidterreindeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Begroeidterreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBegroeidterreindeelWithPatch() throws Exception {
        // Initialize the database
        begroeidterreindeelRepository.saveAndFlush(begroeidterreindeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the begroeidterreindeel using partial update
        Begroeidterreindeel partialUpdatedBegroeidterreindeel = new Begroeidterreindeel();
        partialUpdatedBegroeidterreindeel.setId(begroeidterreindeel.getId());

        partialUpdatedBegroeidterreindeel
            .datumbegingeldigheidbegroeidterreindeel(UPDATED_DATUMBEGINGELDIGHEIDBEGROEIDTERREINDEEL)
            .kruinlijngeometriebegroeidterreindeel(UPDATED_KRUINLIJNGEOMETRIEBEGROEIDTERREINDEEL)
            .lod0geometriebegroeidterreindeel(UPDATED_LOD_0_GEOMETRIEBEGROEIDTERREINDEEL)
            .statusbegroeidterreindeel(UPDATED_STATUSBEGROEIDTERREINDEEL);

        restBegroeidterreindeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBegroeidterreindeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBegroeidterreindeel))
            )
            .andExpect(status().isOk());

        // Validate the Begroeidterreindeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBegroeidterreindeelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBegroeidterreindeel, begroeidterreindeel),
            getPersistedBegroeidterreindeel(begroeidterreindeel)
        );
    }

    @Test
    @Transactional
    void fullUpdateBegroeidterreindeelWithPatch() throws Exception {
        // Initialize the database
        begroeidterreindeelRepository.saveAndFlush(begroeidterreindeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the begroeidterreindeel using partial update
        Begroeidterreindeel partialUpdatedBegroeidterreindeel = new Begroeidterreindeel();
        partialUpdatedBegroeidterreindeel.setId(begroeidterreindeel.getId());

        partialUpdatedBegroeidterreindeel
            .begroeidterreindeeloptalud(UPDATED_BEGROEIDTERREINDEELOPTALUD)
            .datumbegingeldigheidbegroeidterreindeel(UPDATED_DATUMBEGINGELDIGHEIDBEGROEIDTERREINDEEL)
            .datumeindegeldigheidbegroeidterreindeel(UPDATED_DATUMEINDEGELDIGHEIDBEGROEIDTERREINDEEL)
            .fysiekvoorkomenbegroeidterreindeel(UPDATED_FYSIEKVOORKOMENBEGROEIDTERREINDEEL)
            .geometriebegroeidterreindeel(UPDATED_GEOMETRIEBEGROEIDTERREINDEEL)
            .identificatiebegroeidterreindeel(UPDATED_IDENTIFICATIEBEGROEIDTERREINDEEL)
            .kruinlijngeometriebegroeidterreindeel(UPDATED_KRUINLIJNGEOMETRIEBEGROEIDTERREINDEEL)
            .lod0geometriebegroeidterreindeel(UPDATED_LOD_0_GEOMETRIEBEGROEIDTERREINDEEL)
            .plusfysiekvoorkomenbegroeidterreindeel(UPDATED_PLUSFYSIEKVOORKOMENBEGROEIDTERREINDEEL)
            .relatievehoogteliggingbegroeidterreindeel(UPDATED_RELATIEVEHOOGTELIGGINGBEGROEIDTERREINDEEL)
            .statusbegroeidterreindeel(UPDATED_STATUSBEGROEIDTERREINDEEL);

        restBegroeidterreindeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBegroeidterreindeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBegroeidterreindeel))
            )
            .andExpect(status().isOk());

        // Validate the Begroeidterreindeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBegroeidterreindeelUpdatableFieldsEquals(
            partialUpdatedBegroeidterreindeel,
            getPersistedBegroeidterreindeel(partialUpdatedBegroeidterreindeel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBegroeidterreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begroeidterreindeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBegroeidterreindeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, begroeidterreindeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(begroeidterreindeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Begroeidterreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBegroeidterreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begroeidterreindeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBegroeidterreindeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(begroeidterreindeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Begroeidterreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBegroeidterreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begroeidterreindeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBegroeidterreindeelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(begroeidterreindeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Begroeidterreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBegroeidterreindeel() throws Exception {
        // Initialize the database
        begroeidterreindeelRepository.saveAndFlush(begroeidterreindeel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the begroeidterreindeel
        restBegroeidterreindeelMockMvc
            .perform(delete(ENTITY_API_URL_ID, begroeidterreindeel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return begroeidterreindeelRepository.count();
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

    protected Begroeidterreindeel getPersistedBegroeidterreindeel(Begroeidterreindeel begroeidterreindeel) {
        return begroeidterreindeelRepository.findById(begroeidterreindeel.getId()).orElseThrow();
    }

    protected void assertPersistedBegroeidterreindeelToMatchAllProperties(Begroeidterreindeel expectedBegroeidterreindeel) {
        assertBegroeidterreindeelAllPropertiesEquals(
            expectedBegroeidterreindeel,
            getPersistedBegroeidterreindeel(expectedBegroeidterreindeel)
        );
    }

    protected void assertPersistedBegroeidterreindeelToMatchUpdatableProperties(Begroeidterreindeel expectedBegroeidterreindeel) {
        assertBegroeidterreindeelAllUpdatablePropertiesEquals(
            expectedBegroeidterreindeel,
            getPersistedBegroeidterreindeel(expectedBegroeidterreindeel)
        );
    }
}
