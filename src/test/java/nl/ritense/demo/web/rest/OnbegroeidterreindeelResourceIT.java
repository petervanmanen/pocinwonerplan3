package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OnbegroeidterreindeelAsserts.*;
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
import nl.ritense.demo.domain.Onbegroeidterreindeel;
import nl.ritense.demo.repository.OnbegroeidterreindeelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OnbegroeidterreindeelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OnbegroeidterreindeelResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDONBEGROEIDTERREINDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDONBEGROEIDTERREINDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDONBEGROEIDTERREINDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDONBEGROEIDTERREINDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FYSIEKVOORKOMENONBEGROEIDTERREINDEEL = "AAAAAAAAAA";
    private static final String UPDATED_FYSIEKVOORKOMENONBEGROEIDTERREINDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_GEOMETRIEONBEGROEIDTERREINDEEL = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEONBEGROEIDTERREINDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIEONBEGROEIDTERREINDEEL = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIEONBEGROEIDTERREINDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_KRUINLIJNGEOMETRIEONBEGROEIDTERREINDEEL = "AAAAAAAAAA";
    private static final String UPDATED_KRUINLIJNGEOMETRIEONBEGROEIDTERREINDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_ONBEGROEIDTERREINDEELOPTALUD = "AAAAAAAAAA";
    private static final String UPDATED_ONBEGROEIDTERREINDEELOPTALUD = "BBBBBBBBBB";

    private static final String DEFAULT_PLUSFYSIEKVOORKOMENONBEGROEIDTERREINDEEL = "AAAAAAAAAA";
    private static final String UPDATED_PLUSFYSIEKVOORKOMENONBEGROEIDTERREINDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIEVEHOOGTELIGGINGONBEGROEIDTERREINDEEL = "AAAAAAAAAA";
    private static final String UPDATED_RELATIEVEHOOGTELIGGINGONBEGROEIDTERREINDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSONBEGROEIDTERREINDEEL = "AAAAAAAAAA";
    private static final String UPDATED_STATUSONBEGROEIDTERREINDEEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/onbegroeidterreindeels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OnbegroeidterreindeelRepository onbegroeidterreindeelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOnbegroeidterreindeelMockMvc;

    private Onbegroeidterreindeel onbegroeidterreindeel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onbegroeidterreindeel createEntity(EntityManager em) {
        Onbegroeidterreindeel onbegroeidterreindeel = new Onbegroeidterreindeel()
            .datumbegingeldigheidonbegroeidterreindeel(DEFAULT_DATUMBEGINGELDIGHEIDONBEGROEIDTERREINDEEL)
            .datumeindegeldigheidonbegroeidterreindeel(DEFAULT_DATUMEINDEGELDIGHEIDONBEGROEIDTERREINDEEL)
            .fysiekvoorkomenonbegroeidterreindeel(DEFAULT_FYSIEKVOORKOMENONBEGROEIDTERREINDEEL)
            .geometrieonbegroeidterreindeel(DEFAULT_GEOMETRIEONBEGROEIDTERREINDEEL)
            .identificatieonbegroeidterreindeel(DEFAULT_IDENTIFICATIEONBEGROEIDTERREINDEEL)
            .kruinlijngeometrieonbegroeidterreindeel(DEFAULT_KRUINLIJNGEOMETRIEONBEGROEIDTERREINDEEL)
            .onbegroeidterreindeeloptalud(DEFAULT_ONBEGROEIDTERREINDEELOPTALUD)
            .plusfysiekvoorkomenonbegroeidterreindeel(DEFAULT_PLUSFYSIEKVOORKOMENONBEGROEIDTERREINDEEL)
            .relatievehoogteliggingonbegroeidterreindeel(DEFAULT_RELATIEVEHOOGTELIGGINGONBEGROEIDTERREINDEEL)
            .statusonbegroeidterreindeel(DEFAULT_STATUSONBEGROEIDTERREINDEEL);
        return onbegroeidterreindeel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onbegroeidterreindeel createUpdatedEntity(EntityManager em) {
        Onbegroeidterreindeel onbegroeidterreindeel = new Onbegroeidterreindeel()
            .datumbegingeldigheidonbegroeidterreindeel(UPDATED_DATUMBEGINGELDIGHEIDONBEGROEIDTERREINDEEL)
            .datumeindegeldigheidonbegroeidterreindeel(UPDATED_DATUMEINDEGELDIGHEIDONBEGROEIDTERREINDEEL)
            .fysiekvoorkomenonbegroeidterreindeel(UPDATED_FYSIEKVOORKOMENONBEGROEIDTERREINDEEL)
            .geometrieonbegroeidterreindeel(UPDATED_GEOMETRIEONBEGROEIDTERREINDEEL)
            .identificatieonbegroeidterreindeel(UPDATED_IDENTIFICATIEONBEGROEIDTERREINDEEL)
            .kruinlijngeometrieonbegroeidterreindeel(UPDATED_KRUINLIJNGEOMETRIEONBEGROEIDTERREINDEEL)
            .onbegroeidterreindeeloptalud(UPDATED_ONBEGROEIDTERREINDEELOPTALUD)
            .plusfysiekvoorkomenonbegroeidterreindeel(UPDATED_PLUSFYSIEKVOORKOMENONBEGROEIDTERREINDEEL)
            .relatievehoogteliggingonbegroeidterreindeel(UPDATED_RELATIEVEHOOGTELIGGINGONBEGROEIDTERREINDEEL)
            .statusonbegroeidterreindeel(UPDATED_STATUSONBEGROEIDTERREINDEEL);
        return onbegroeidterreindeel;
    }

    @BeforeEach
    public void initTest() {
        onbegroeidterreindeel = createEntity(em);
    }

    @Test
    @Transactional
    void createOnbegroeidterreindeel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Onbegroeidterreindeel
        var returnedOnbegroeidterreindeel = om.readValue(
            restOnbegroeidterreindeelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onbegroeidterreindeel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Onbegroeidterreindeel.class
        );

        // Validate the Onbegroeidterreindeel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOnbegroeidterreindeelUpdatableFieldsEquals(
            returnedOnbegroeidterreindeel,
            getPersistedOnbegroeidterreindeel(returnedOnbegroeidterreindeel)
        );
    }

    @Test
    @Transactional
    void createOnbegroeidterreindeelWithExistingId() throws Exception {
        // Create the Onbegroeidterreindeel with an existing ID
        onbegroeidterreindeel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOnbegroeidterreindeelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onbegroeidterreindeel)))
            .andExpect(status().isBadRequest());

        // Validate the Onbegroeidterreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOnbegroeidterreindeels() throws Exception {
        // Initialize the database
        onbegroeidterreindeelRepository.saveAndFlush(onbegroeidterreindeel);

        // Get all the onbegroeidterreindeelList
        restOnbegroeidterreindeelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(onbegroeidterreindeel.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidonbegroeidterreindeel").value(
                    hasItem(DEFAULT_DATUMBEGINGELDIGHEIDONBEGROEIDTERREINDEEL.toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidonbegroeidterreindeel").value(
                    hasItem(DEFAULT_DATUMEINDEGELDIGHEIDONBEGROEIDTERREINDEEL.toString())
                )
            )
            .andExpect(jsonPath("$.[*].fysiekvoorkomenonbegroeidterreindeel").value(hasItem(DEFAULT_FYSIEKVOORKOMENONBEGROEIDTERREINDEEL)))
            .andExpect(jsonPath("$.[*].geometrieonbegroeidterreindeel").value(hasItem(DEFAULT_GEOMETRIEONBEGROEIDTERREINDEEL)))
            .andExpect(jsonPath("$.[*].identificatieonbegroeidterreindeel").value(hasItem(DEFAULT_IDENTIFICATIEONBEGROEIDTERREINDEEL)))
            .andExpect(
                jsonPath("$.[*].kruinlijngeometrieonbegroeidterreindeel").value(hasItem(DEFAULT_KRUINLIJNGEOMETRIEONBEGROEIDTERREINDEEL))
            )
            .andExpect(jsonPath("$.[*].onbegroeidterreindeeloptalud").value(hasItem(DEFAULT_ONBEGROEIDTERREINDEELOPTALUD)))
            .andExpect(
                jsonPath("$.[*].plusfysiekvoorkomenonbegroeidterreindeel").value(hasItem(DEFAULT_PLUSFYSIEKVOORKOMENONBEGROEIDTERREINDEEL))
            )
            .andExpect(
                jsonPath("$.[*].relatievehoogteliggingonbegroeidterreindeel").value(
                    hasItem(DEFAULT_RELATIEVEHOOGTELIGGINGONBEGROEIDTERREINDEEL)
                )
            )
            .andExpect(jsonPath("$.[*].statusonbegroeidterreindeel").value(hasItem(DEFAULT_STATUSONBEGROEIDTERREINDEEL)));
    }

    @Test
    @Transactional
    void getOnbegroeidterreindeel() throws Exception {
        // Initialize the database
        onbegroeidterreindeelRepository.saveAndFlush(onbegroeidterreindeel);

        // Get the onbegroeidterreindeel
        restOnbegroeidterreindeelMockMvc
            .perform(get(ENTITY_API_URL_ID, onbegroeidterreindeel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(onbegroeidterreindeel.getId().intValue()))
            .andExpect(
                jsonPath("$.datumbegingeldigheidonbegroeidterreindeel").value(DEFAULT_DATUMBEGINGELDIGHEIDONBEGROEIDTERREINDEEL.toString())
            )
            .andExpect(
                jsonPath("$.datumeindegeldigheidonbegroeidterreindeel").value(DEFAULT_DATUMEINDEGELDIGHEIDONBEGROEIDTERREINDEEL.toString())
            )
            .andExpect(jsonPath("$.fysiekvoorkomenonbegroeidterreindeel").value(DEFAULT_FYSIEKVOORKOMENONBEGROEIDTERREINDEEL))
            .andExpect(jsonPath("$.geometrieonbegroeidterreindeel").value(DEFAULT_GEOMETRIEONBEGROEIDTERREINDEEL))
            .andExpect(jsonPath("$.identificatieonbegroeidterreindeel").value(DEFAULT_IDENTIFICATIEONBEGROEIDTERREINDEEL))
            .andExpect(jsonPath("$.kruinlijngeometrieonbegroeidterreindeel").value(DEFAULT_KRUINLIJNGEOMETRIEONBEGROEIDTERREINDEEL))
            .andExpect(jsonPath("$.onbegroeidterreindeeloptalud").value(DEFAULT_ONBEGROEIDTERREINDEELOPTALUD))
            .andExpect(jsonPath("$.plusfysiekvoorkomenonbegroeidterreindeel").value(DEFAULT_PLUSFYSIEKVOORKOMENONBEGROEIDTERREINDEEL))
            .andExpect(jsonPath("$.relatievehoogteliggingonbegroeidterreindeel").value(DEFAULT_RELATIEVEHOOGTELIGGINGONBEGROEIDTERREINDEEL))
            .andExpect(jsonPath("$.statusonbegroeidterreindeel").value(DEFAULT_STATUSONBEGROEIDTERREINDEEL));
    }

    @Test
    @Transactional
    void getNonExistingOnbegroeidterreindeel() throws Exception {
        // Get the onbegroeidterreindeel
        restOnbegroeidterreindeelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOnbegroeidterreindeel() throws Exception {
        // Initialize the database
        onbegroeidterreindeelRepository.saveAndFlush(onbegroeidterreindeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onbegroeidterreindeel
        Onbegroeidterreindeel updatedOnbegroeidterreindeel = onbegroeidterreindeelRepository
            .findById(onbegroeidterreindeel.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOnbegroeidterreindeel are not directly saved in db
        em.detach(updatedOnbegroeidterreindeel);
        updatedOnbegroeidterreindeel
            .datumbegingeldigheidonbegroeidterreindeel(UPDATED_DATUMBEGINGELDIGHEIDONBEGROEIDTERREINDEEL)
            .datumeindegeldigheidonbegroeidterreindeel(UPDATED_DATUMEINDEGELDIGHEIDONBEGROEIDTERREINDEEL)
            .fysiekvoorkomenonbegroeidterreindeel(UPDATED_FYSIEKVOORKOMENONBEGROEIDTERREINDEEL)
            .geometrieonbegroeidterreindeel(UPDATED_GEOMETRIEONBEGROEIDTERREINDEEL)
            .identificatieonbegroeidterreindeel(UPDATED_IDENTIFICATIEONBEGROEIDTERREINDEEL)
            .kruinlijngeometrieonbegroeidterreindeel(UPDATED_KRUINLIJNGEOMETRIEONBEGROEIDTERREINDEEL)
            .onbegroeidterreindeeloptalud(UPDATED_ONBEGROEIDTERREINDEELOPTALUD)
            .plusfysiekvoorkomenonbegroeidterreindeel(UPDATED_PLUSFYSIEKVOORKOMENONBEGROEIDTERREINDEEL)
            .relatievehoogteliggingonbegroeidterreindeel(UPDATED_RELATIEVEHOOGTELIGGINGONBEGROEIDTERREINDEEL)
            .statusonbegroeidterreindeel(UPDATED_STATUSONBEGROEIDTERREINDEEL);

        restOnbegroeidterreindeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOnbegroeidterreindeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOnbegroeidterreindeel))
            )
            .andExpect(status().isOk());

        // Validate the Onbegroeidterreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOnbegroeidterreindeelToMatchAllProperties(updatedOnbegroeidterreindeel);
    }

    @Test
    @Transactional
    void putNonExistingOnbegroeidterreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onbegroeidterreindeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOnbegroeidterreindeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, onbegroeidterreindeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(onbegroeidterreindeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onbegroeidterreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOnbegroeidterreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onbegroeidterreindeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnbegroeidterreindeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(onbegroeidterreindeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onbegroeidterreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOnbegroeidterreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onbegroeidterreindeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnbegroeidterreindeelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onbegroeidterreindeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Onbegroeidterreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOnbegroeidterreindeelWithPatch() throws Exception {
        // Initialize the database
        onbegroeidterreindeelRepository.saveAndFlush(onbegroeidterreindeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onbegroeidterreindeel using partial update
        Onbegroeidterreindeel partialUpdatedOnbegroeidterreindeel = new Onbegroeidterreindeel();
        partialUpdatedOnbegroeidterreindeel.setId(onbegroeidterreindeel.getId());

        partialUpdatedOnbegroeidterreindeel
            .datumbegingeldigheidonbegroeidterreindeel(UPDATED_DATUMBEGINGELDIGHEIDONBEGROEIDTERREINDEEL)
            .fysiekvoorkomenonbegroeidterreindeel(UPDATED_FYSIEKVOORKOMENONBEGROEIDTERREINDEEL)
            .geometrieonbegroeidterreindeel(UPDATED_GEOMETRIEONBEGROEIDTERREINDEEL)
            .plusfysiekvoorkomenonbegroeidterreindeel(UPDATED_PLUSFYSIEKVOORKOMENONBEGROEIDTERREINDEEL)
            .relatievehoogteliggingonbegroeidterreindeel(UPDATED_RELATIEVEHOOGTELIGGINGONBEGROEIDTERREINDEEL)
            .statusonbegroeidterreindeel(UPDATED_STATUSONBEGROEIDTERREINDEEL);

        restOnbegroeidterreindeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOnbegroeidterreindeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOnbegroeidterreindeel))
            )
            .andExpect(status().isOk());

        // Validate the Onbegroeidterreindeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOnbegroeidterreindeelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOnbegroeidterreindeel, onbegroeidterreindeel),
            getPersistedOnbegroeidterreindeel(onbegroeidterreindeel)
        );
    }

    @Test
    @Transactional
    void fullUpdateOnbegroeidterreindeelWithPatch() throws Exception {
        // Initialize the database
        onbegroeidterreindeelRepository.saveAndFlush(onbegroeidterreindeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onbegroeidterreindeel using partial update
        Onbegroeidterreindeel partialUpdatedOnbegroeidterreindeel = new Onbegroeidterreindeel();
        partialUpdatedOnbegroeidterreindeel.setId(onbegroeidterreindeel.getId());

        partialUpdatedOnbegroeidterreindeel
            .datumbegingeldigheidonbegroeidterreindeel(UPDATED_DATUMBEGINGELDIGHEIDONBEGROEIDTERREINDEEL)
            .datumeindegeldigheidonbegroeidterreindeel(UPDATED_DATUMEINDEGELDIGHEIDONBEGROEIDTERREINDEEL)
            .fysiekvoorkomenonbegroeidterreindeel(UPDATED_FYSIEKVOORKOMENONBEGROEIDTERREINDEEL)
            .geometrieonbegroeidterreindeel(UPDATED_GEOMETRIEONBEGROEIDTERREINDEEL)
            .identificatieonbegroeidterreindeel(UPDATED_IDENTIFICATIEONBEGROEIDTERREINDEEL)
            .kruinlijngeometrieonbegroeidterreindeel(UPDATED_KRUINLIJNGEOMETRIEONBEGROEIDTERREINDEEL)
            .onbegroeidterreindeeloptalud(UPDATED_ONBEGROEIDTERREINDEELOPTALUD)
            .plusfysiekvoorkomenonbegroeidterreindeel(UPDATED_PLUSFYSIEKVOORKOMENONBEGROEIDTERREINDEEL)
            .relatievehoogteliggingonbegroeidterreindeel(UPDATED_RELATIEVEHOOGTELIGGINGONBEGROEIDTERREINDEEL)
            .statusonbegroeidterreindeel(UPDATED_STATUSONBEGROEIDTERREINDEEL);

        restOnbegroeidterreindeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOnbegroeidterreindeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOnbegroeidterreindeel))
            )
            .andExpect(status().isOk());

        // Validate the Onbegroeidterreindeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOnbegroeidterreindeelUpdatableFieldsEquals(
            partialUpdatedOnbegroeidterreindeel,
            getPersistedOnbegroeidterreindeel(partialUpdatedOnbegroeidterreindeel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOnbegroeidterreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onbegroeidterreindeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOnbegroeidterreindeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, onbegroeidterreindeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(onbegroeidterreindeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onbegroeidterreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOnbegroeidterreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onbegroeidterreindeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnbegroeidterreindeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(onbegroeidterreindeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onbegroeidterreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOnbegroeidterreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onbegroeidterreindeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnbegroeidterreindeelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(onbegroeidterreindeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Onbegroeidterreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOnbegroeidterreindeel() throws Exception {
        // Initialize the database
        onbegroeidterreindeelRepository.saveAndFlush(onbegroeidterreindeel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the onbegroeidterreindeel
        restOnbegroeidterreindeelMockMvc
            .perform(delete(ENTITY_API_URL_ID, onbegroeidterreindeel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return onbegroeidterreindeelRepository.count();
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

    protected Onbegroeidterreindeel getPersistedOnbegroeidterreindeel(Onbegroeidterreindeel onbegroeidterreindeel) {
        return onbegroeidterreindeelRepository.findById(onbegroeidterreindeel.getId()).orElseThrow();
    }

    protected void assertPersistedOnbegroeidterreindeelToMatchAllProperties(Onbegroeidterreindeel expectedOnbegroeidterreindeel) {
        assertOnbegroeidterreindeelAllPropertiesEquals(
            expectedOnbegroeidterreindeel,
            getPersistedOnbegroeidterreindeel(expectedOnbegroeidterreindeel)
        );
    }

    protected void assertPersistedOnbegroeidterreindeelToMatchUpdatableProperties(Onbegroeidterreindeel expectedOnbegroeidterreindeel) {
        assertOnbegroeidterreindeelAllUpdatablePropertiesEquals(
            expectedOnbegroeidterreindeel,
            getPersistedOnbegroeidterreindeel(expectedOnbegroeidterreindeel)
        );
    }
}
