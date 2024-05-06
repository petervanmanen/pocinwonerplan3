package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OndersteunendwegdeelAsserts.*;
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
import nl.ritense.demo.domain.Ondersteunendwegdeel;
import nl.ritense.demo.repository.OndersteunendwegdeelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OndersteunendwegdeelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OndersteunendwegdeelResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDONDERSTEUNENDWEGDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDONDERSTEUNENDWEGDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDONDERSTEUNENDWEGDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDONDERSTEUNENDWEGDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FUNCTIEONDERSTEUNENDWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTIEONDERSTEUNENDWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_FYSIEKVOORKOMENONDERSTEUNENDWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_FYSIEKVOORKOMENONDERSTEUNENDWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_GEOMETRIEONDERSTEUNENDWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEONDERSTEUNENDWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIEONDERSTEUNENDWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIEONDERSTEUNENDWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_KRUINLIJNGEOMETRIEONDERSTEUNENDWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_KRUINLIJNGEOMETRIEONDERSTEUNENDWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_0_GEOMETRIEONDERSTEUNENDWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_LOD_0_GEOMETRIEONDERSTEUNENDWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERSTEUNENDWEGDEELOPTALUD = "AAAAAAAAAA";
    private static final String UPDATED_ONDERSTEUNENDWEGDEELOPTALUD = "BBBBBBBBBB";

    private static final String DEFAULT_PLUSFUNCTIEONDERSTEUNENDWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_PLUSFUNCTIEONDERSTEUNENDWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_PLUSFYSIEKVOORKOMENONDERSTEUNENDWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_PLUSFYSIEKVOORKOMENONDERSTEUNENDWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIEVEHOOGTELIGGINGONDERSTEUNENDWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_RELATIEVEHOOGTELIGGINGONDERSTEUNENDWEGDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSONDERSTEUNENDWEGDEEL = "AAAAAAAAAA";
    private static final String UPDATED_STATUSONDERSTEUNENDWEGDEEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ondersteunendwegdeels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OndersteunendwegdeelRepository ondersteunendwegdeelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOndersteunendwegdeelMockMvc;

    private Ondersteunendwegdeel ondersteunendwegdeel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ondersteunendwegdeel createEntity(EntityManager em) {
        Ondersteunendwegdeel ondersteunendwegdeel = new Ondersteunendwegdeel()
            .datumbegingeldigheidondersteunendwegdeel(DEFAULT_DATUMBEGINGELDIGHEIDONDERSTEUNENDWEGDEEL)
            .datumeindegeldigheidondersteunendwegdeel(DEFAULT_DATUMEINDEGELDIGHEIDONDERSTEUNENDWEGDEEL)
            .functieondersteunendwegdeel(DEFAULT_FUNCTIEONDERSTEUNENDWEGDEEL)
            .fysiekvoorkomenondersteunendwegdeel(DEFAULT_FYSIEKVOORKOMENONDERSTEUNENDWEGDEEL)
            .geometrieondersteunendwegdeel(DEFAULT_GEOMETRIEONDERSTEUNENDWEGDEEL)
            .identificatieondersteunendwegdeel(DEFAULT_IDENTIFICATIEONDERSTEUNENDWEGDEEL)
            .kruinlijngeometrieondersteunendwegdeel(DEFAULT_KRUINLIJNGEOMETRIEONDERSTEUNENDWEGDEEL)
            .lod0geometrieondersteunendwegdeel(DEFAULT_LOD_0_GEOMETRIEONDERSTEUNENDWEGDEEL)
            .ondersteunendwegdeeloptalud(DEFAULT_ONDERSTEUNENDWEGDEELOPTALUD)
            .plusfunctieondersteunendwegdeel(DEFAULT_PLUSFUNCTIEONDERSTEUNENDWEGDEEL)
            .plusfysiekvoorkomenondersteunendwegdeel(DEFAULT_PLUSFYSIEKVOORKOMENONDERSTEUNENDWEGDEEL)
            .relatievehoogteliggingondersteunendwegdeel(DEFAULT_RELATIEVEHOOGTELIGGINGONDERSTEUNENDWEGDEEL)
            .statusondersteunendwegdeel(DEFAULT_STATUSONDERSTEUNENDWEGDEEL);
        return ondersteunendwegdeel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ondersteunendwegdeel createUpdatedEntity(EntityManager em) {
        Ondersteunendwegdeel ondersteunendwegdeel = new Ondersteunendwegdeel()
            .datumbegingeldigheidondersteunendwegdeel(UPDATED_DATUMBEGINGELDIGHEIDONDERSTEUNENDWEGDEEL)
            .datumeindegeldigheidondersteunendwegdeel(UPDATED_DATUMEINDEGELDIGHEIDONDERSTEUNENDWEGDEEL)
            .functieondersteunendwegdeel(UPDATED_FUNCTIEONDERSTEUNENDWEGDEEL)
            .fysiekvoorkomenondersteunendwegdeel(UPDATED_FYSIEKVOORKOMENONDERSTEUNENDWEGDEEL)
            .geometrieondersteunendwegdeel(UPDATED_GEOMETRIEONDERSTEUNENDWEGDEEL)
            .identificatieondersteunendwegdeel(UPDATED_IDENTIFICATIEONDERSTEUNENDWEGDEEL)
            .kruinlijngeometrieondersteunendwegdeel(UPDATED_KRUINLIJNGEOMETRIEONDERSTEUNENDWEGDEEL)
            .lod0geometrieondersteunendwegdeel(UPDATED_LOD_0_GEOMETRIEONDERSTEUNENDWEGDEEL)
            .ondersteunendwegdeeloptalud(UPDATED_ONDERSTEUNENDWEGDEELOPTALUD)
            .plusfunctieondersteunendwegdeel(UPDATED_PLUSFUNCTIEONDERSTEUNENDWEGDEEL)
            .plusfysiekvoorkomenondersteunendwegdeel(UPDATED_PLUSFYSIEKVOORKOMENONDERSTEUNENDWEGDEEL)
            .relatievehoogteliggingondersteunendwegdeel(UPDATED_RELATIEVEHOOGTELIGGINGONDERSTEUNENDWEGDEEL)
            .statusondersteunendwegdeel(UPDATED_STATUSONDERSTEUNENDWEGDEEL);
        return ondersteunendwegdeel;
    }

    @BeforeEach
    public void initTest() {
        ondersteunendwegdeel = createEntity(em);
    }

    @Test
    @Transactional
    void createOndersteunendwegdeel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ondersteunendwegdeel
        var returnedOndersteunendwegdeel = om.readValue(
            restOndersteunendwegdeelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ondersteunendwegdeel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Ondersteunendwegdeel.class
        );

        // Validate the Ondersteunendwegdeel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOndersteunendwegdeelUpdatableFieldsEquals(
            returnedOndersteunendwegdeel,
            getPersistedOndersteunendwegdeel(returnedOndersteunendwegdeel)
        );
    }

    @Test
    @Transactional
    void createOndersteunendwegdeelWithExistingId() throws Exception {
        // Create the Ondersteunendwegdeel with an existing ID
        ondersteunendwegdeel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOndersteunendwegdeelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ondersteunendwegdeel)))
            .andExpect(status().isBadRequest());

        // Validate the Ondersteunendwegdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOndersteunendwegdeels() throws Exception {
        // Initialize the database
        ondersteunendwegdeelRepository.saveAndFlush(ondersteunendwegdeel);

        // Get all the ondersteunendwegdeelList
        restOndersteunendwegdeelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ondersteunendwegdeel.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidondersteunendwegdeel").value(
                    hasItem(DEFAULT_DATUMBEGINGELDIGHEIDONDERSTEUNENDWEGDEEL.toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidondersteunendwegdeel").value(
                    hasItem(DEFAULT_DATUMEINDEGELDIGHEIDONDERSTEUNENDWEGDEEL.toString())
                )
            )
            .andExpect(jsonPath("$.[*].functieondersteunendwegdeel").value(hasItem(DEFAULT_FUNCTIEONDERSTEUNENDWEGDEEL)))
            .andExpect(jsonPath("$.[*].fysiekvoorkomenondersteunendwegdeel").value(hasItem(DEFAULT_FYSIEKVOORKOMENONDERSTEUNENDWEGDEEL)))
            .andExpect(jsonPath("$.[*].geometrieondersteunendwegdeel").value(hasItem(DEFAULT_GEOMETRIEONDERSTEUNENDWEGDEEL)))
            .andExpect(jsonPath("$.[*].identificatieondersteunendwegdeel").value(hasItem(DEFAULT_IDENTIFICATIEONDERSTEUNENDWEGDEEL)))
            .andExpect(
                jsonPath("$.[*].kruinlijngeometrieondersteunendwegdeel").value(hasItem(DEFAULT_KRUINLIJNGEOMETRIEONDERSTEUNENDWEGDEEL))
            )
            .andExpect(jsonPath("$.[*].lod0geometrieondersteunendwegdeel").value(hasItem(DEFAULT_LOD_0_GEOMETRIEONDERSTEUNENDWEGDEEL)))
            .andExpect(jsonPath("$.[*].ondersteunendwegdeeloptalud").value(hasItem(DEFAULT_ONDERSTEUNENDWEGDEELOPTALUD)))
            .andExpect(jsonPath("$.[*].plusfunctieondersteunendwegdeel").value(hasItem(DEFAULT_PLUSFUNCTIEONDERSTEUNENDWEGDEEL)))
            .andExpect(
                jsonPath("$.[*].plusfysiekvoorkomenondersteunendwegdeel").value(hasItem(DEFAULT_PLUSFYSIEKVOORKOMENONDERSTEUNENDWEGDEEL))
            )
            .andExpect(
                jsonPath("$.[*].relatievehoogteliggingondersteunendwegdeel").value(
                    hasItem(DEFAULT_RELATIEVEHOOGTELIGGINGONDERSTEUNENDWEGDEEL)
                )
            )
            .andExpect(jsonPath("$.[*].statusondersteunendwegdeel").value(hasItem(DEFAULT_STATUSONDERSTEUNENDWEGDEEL)));
    }

    @Test
    @Transactional
    void getOndersteunendwegdeel() throws Exception {
        // Initialize the database
        ondersteunendwegdeelRepository.saveAndFlush(ondersteunendwegdeel);

        // Get the ondersteunendwegdeel
        restOndersteunendwegdeelMockMvc
            .perform(get(ENTITY_API_URL_ID, ondersteunendwegdeel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ondersteunendwegdeel.getId().intValue()))
            .andExpect(
                jsonPath("$.datumbegingeldigheidondersteunendwegdeel").value(DEFAULT_DATUMBEGINGELDIGHEIDONDERSTEUNENDWEGDEEL.toString())
            )
            .andExpect(
                jsonPath("$.datumeindegeldigheidondersteunendwegdeel").value(DEFAULT_DATUMEINDEGELDIGHEIDONDERSTEUNENDWEGDEEL.toString())
            )
            .andExpect(jsonPath("$.functieondersteunendwegdeel").value(DEFAULT_FUNCTIEONDERSTEUNENDWEGDEEL))
            .andExpect(jsonPath("$.fysiekvoorkomenondersteunendwegdeel").value(DEFAULT_FYSIEKVOORKOMENONDERSTEUNENDWEGDEEL))
            .andExpect(jsonPath("$.geometrieondersteunendwegdeel").value(DEFAULT_GEOMETRIEONDERSTEUNENDWEGDEEL))
            .andExpect(jsonPath("$.identificatieondersteunendwegdeel").value(DEFAULT_IDENTIFICATIEONDERSTEUNENDWEGDEEL))
            .andExpect(jsonPath("$.kruinlijngeometrieondersteunendwegdeel").value(DEFAULT_KRUINLIJNGEOMETRIEONDERSTEUNENDWEGDEEL))
            .andExpect(jsonPath("$.lod0geometrieondersteunendwegdeel").value(DEFAULT_LOD_0_GEOMETRIEONDERSTEUNENDWEGDEEL))
            .andExpect(jsonPath("$.ondersteunendwegdeeloptalud").value(DEFAULT_ONDERSTEUNENDWEGDEELOPTALUD))
            .andExpect(jsonPath("$.plusfunctieondersteunendwegdeel").value(DEFAULT_PLUSFUNCTIEONDERSTEUNENDWEGDEEL))
            .andExpect(jsonPath("$.plusfysiekvoorkomenondersteunendwegdeel").value(DEFAULT_PLUSFYSIEKVOORKOMENONDERSTEUNENDWEGDEEL))
            .andExpect(jsonPath("$.relatievehoogteliggingondersteunendwegdeel").value(DEFAULT_RELATIEVEHOOGTELIGGINGONDERSTEUNENDWEGDEEL))
            .andExpect(jsonPath("$.statusondersteunendwegdeel").value(DEFAULT_STATUSONDERSTEUNENDWEGDEEL));
    }

    @Test
    @Transactional
    void getNonExistingOndersteunendwegdeel() throws Exception {
        // Get the ondersteunendwegdeel
        restOndersteunendwegdeelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOndersteunendwegdeel() throws Exception {
        // Initialize the database
        ondersteunendwegdeelRepository.saveAndFlush(ondersteunendwegdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ondersteunendwegdeel
        Ondersteunendwegdeel updatedOndersteunendwegdeel = ondersteunendwegdeelRepository
            .findById(ondersteunendwegdeel.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOndersteunendwegdeel are not directly saved in db
        em.detach(updatedOndersteunendwegdeel);
        updatedOndersteunendwegdeel
            .datumbegingeldigheidondersteunendwegdeel(UPDATED_DATUMBEGINGELDIGHEIDONDERSTEUNENDWEGDEEL)
            .datumeindegeldigheidondersteunendwegdeel(UPDATED_DATUMEINDEGELDIGHEIDONDERSTEUNENDWEGDEEL)
            .functieondersteunendwegdeel(UPDATED_FUNCTIEONDERSTEUNENDWEGDEEL)
            .fysiekvoorkomenondersteunendwegdeel(UPDATED_FYSIEKVOORKOMENONDERSTEUNENDWEGDEEL)
            .geometrieondersteunendwegdeel(UPDATED_GEOMETRIEONDERSTEUNENDWEGDEEL)
            .identificatieondersteunendwegdeel(UPDATED_IDENTIFICATIEONDERSTEUNENDWEGDEEL)
            .kruinlijngeometrieondersteunendwegdeel(UPDATED_KRUINLIJNGEOMETRIEONDERSTEUNENDWEGDEEL)
            .lod0geometrieondersteunendwegdeel(UPDATED_LOD_0_GEOMETRIEONDERSTEUNENDWEGDEEL)
            .ondersteunendwegdeeloptalud(UPDATED_ONDERSTEUNENDWEGDEELOPTALUD)
            .plusfunctieondersteunendwegdeel(UPDATED_PLUSFUNCTIEONDERSTEUNENDWEGDEEL)
            .plusfysiekvoorkomenondersteunendwegdeel(UPDATED_PLUSFYSIEKVOORKOMENONDERSTEUNENDWEGDEEL)
            .relatievehoogteliggingondersteunendwegdeel(UPDATED_RELATIEVEHOOGTELIGGINGONDERSTEUNENDWEGDEEL)
            .statusondersteunendwegdeel(UPDATED_STATUSONDERSTEUNENDWEGDEEL);

        restOndersteunendwegdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOndersteunendwegdeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOndersteunendwegdeel))
            )
            .andExpect(status().isOk());

        // Validate the Ondersteunendwegdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOndersteunendwegdeelToMatchAllProperties(updatedOndersteunendwegdeel);
    }

    @Test
    @Transactional
    void putNonExistingOndersteunendwegdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ondersteunendwegdeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOndersteunendwegdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ondersteunendwegdeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ondersteunendwegdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ondersteunendwegdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOndersteunendwegdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ondersteunendwegdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOndersteunendwegdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ondersteunendwegdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ondersteunendwegdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOndersteunendwegdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ondersteunendwegdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOndersteunendwegdeelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ondersteunendwegdeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ondersteunendwegdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOndersteunendwegdeelWithPatch() throws Exception {
        // Initialize the database
        ondersteunendwegdeelRepository.saveAndFlush(ondersteunendwegdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ondersteunendwegdeel using partial update
        Ondersteunendwegdeel partialUpdatedOndersteunendwegdeel = new Ondersteunendwegdeel();
        partialUpdatedOndersteunendwegdeel.setId(ondersteunendwegdeel.getId());

        partialUpdatedOndersteunendwegdeel
            .datumeindegeldigheidondersteunendwegdeel(UPDATED_DATUMEINDEGELDIGHEIDONDERSTEUNENDWEGDEEL)
            .functieondersteunendwegdeel(UPDATED_FUNCTIEONDERSTEUNENDWEGDEEL)
            .plusfunctieondersteunendwegdeel(UPDATED_PLUSFUNCTIEONDERSTEUNENDWEGDEEL);

        restOndersteunendwegdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOndersteunendwegdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOndersteunendwegdeel))
            )
            .andExpect(status().isOk());

        // Validate the Ondersteunendwegdeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOndersteunendwegdeelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOndersteunendwegdeel, ondersteunendwegdeel),
            getPersistedOndersteunendwegdeel(ondersteunendwegdeel)
        );
    }

    @Test
    @Transactional
    void fullUpdateOndersteunendwegdeelWithPatch() throws Exception {
        // Initialize the database
        ondersteunendwegdeelRepository.saveAndFlush(ondersteunendwegdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ondersteunendwegdeel using partial update
        Ondersteunendwegdeel partialUpdatedOndersteunendwegdeel = new Ondersteunendwegdeel();
        partialUpdatedOndersteunendwegdeel.setId(ondersteunendwegdeel.getId());

        partialUpdatedOndersteunendwegdeel
            .datumbegingeldigheidondersteunendwegdeel(UPDATED_DATUMBEGINGELDIGHEIDONDERSTEUNENDWEGDEEL)
            .datumeindegeldigheidondersteunendwegdeel(UPDATED_DATUMEINDEGELDIGHEIDONDERSTEUNENDWEGDEEL)
            .functieondersteunendwegdeel(UPDATED_FUNCTIEONDERSTEUNENDWEGDEEL)
            .fysiekvoorkomenondersteunendwegdeel(UPDATED_FYSIEKVOORKOMENONDERSTEUNENDWEGDEEL)
            .geometrieondersteunendwegdeel(UPDATED_GEOMETRIEONDERSTEUNENDWEGDEEL)
            .identificatieondersteunendwegdeel(UPDATED_IDENTIFICATIEONDERSTEUNENDWEGDEEL)
            .kruinlijngeometrieondersteunendwegdeel(UPDATED_KRUINLIJNGEOMETRIEONDERSTEUNENDWEGDEEL)
            .lod0geometrieondersteunendwegdeel(UPDATED_LOD_0_GEOMETRIEONDERSTEUNENDWEGDEEL)
            .ondersteunendwegdeeloptalud(UPDATED_ONDERSTEUNENDWEGDEELOPTALUD)
            .plusfunctieondersteunendwegdeel(UPDATED_PLUSFUNCTIEONDERSTEUNENDWEGDEEL)
            .plusfysiekvoorkomenondersteunendwegdeel(UPDATED_PLUSFYSIEKVOORKOMENONDERSTEUNENDWEGDEEL)
            .relatievehoogteliggingondersteunendwegdeel(UPDATED_RELATIEVEHOOGTELIGGINGONDERSTEUNENDWEGDEEL)
            .statusondersteunendwegdeel(UPDATED_STATUSONDERSTEUNENDWEGDEEL);

        restOndersteunendwegdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOndersteunendwegdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOndersteunendwegdeel))
            )
            .andExpect(status().isOk());

        // Validate the Ondersteunendwegdeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOndersteunendwegdeelUpdatableFieldsEquals(
            partialUpdatedOndersteunendwegdeel,
            getPersistedOndersteunendwegdeel(partialUpdatedOndersteunendwegdeel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOndersteunendwegdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ondersteunendwegdeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOndersteunendwegdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ondersteunendwegdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ondersteunendwegdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ondersteunendwegdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOndersteunendwegdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ondersteunendwegdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOndersteunendwegdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ondersteunendwegdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ondersteunendwegdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOndersteunendwegdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ondersteunendwegdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOndersteunendwegdeelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ondersteunendwegdeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ondersteunendwegdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOndersteunendwegdeel() throws Exception {
        // Initialize the database
        ondersteunendwegdeelRepository.saveAndFlush(ondersteunendwegdeel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ondersteunendwegdeel
        restOndersteunendwegdeelMockMvc
            .perform(delete(ENTITY_API_URL_ID, ondersteunendwegdeel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ondersteunendwegdeelRepository.count();
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

    protected Ondersteunendwegdeel getPersistedOndersteunendwegdeel(Ondersteunendwegdeel ondersteunendwegdeel) {
        return ondersteunendwegdeelRepository.findById(ondersteunendwegdeel.getId()).orElseThrow();
    }

    protected void assertPersistedOndersteunendwegdeelToMatchAllProperties(Ondersteunendwegdeel expectedOndersteunendwegdeel) {
        assertOndersteunendwegdeelAllPropertiesEquals(
            expectedOndersteunendwegdeel,
            getPersistedOndersteunendwegdeel(expectedOndersteunendwegdeel)
        );
    }

    protected void assertPersistedOndersteunendwegdeelToMatchUpdatableProperties(Ondersteunendwegdeel expectedOndersteunendwegdeel) {
        assertOndersteunendwegdeelAllUpdatablePropertiesEquals(
            expectedOndersteunendwegdeel,
            getPersistedOndersteunendwegdeel(expectedOndersteunendwegdeel)
        );
    }
}
