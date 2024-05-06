package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GebouwinstallatieAsserts.*;
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
import nl.ritense.demo.domain.Gebouwinstallatie;
import nl.ritense.demo.repository.GebouwinstallatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GebouwinstallatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GebouwinstallatieResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDGEBOUWINSTALLATIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDGEBOUWINSTALLATIE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDGEBOUWINSTALLATIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDGEBOUWINSTALLATIE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEOMETRIEGEBOUWINSTALLATIE = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEGEBOUWINSTALLATIE = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIEGEBOUWINSTALLATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIEGEBOUWINSTALLATIE = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_0_GEOMETRIEGEBOUWINSTALLATIE = "AAAAAAAAAA";
    private static final String UPDATED_LOD_0_GEOMETRIEGEBOUWINSTALLATIE = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIEVEHOOGTELIGGINGGEBOUWINSTALLATIE = "AAAAAAAAAA";
    private static final String UPDATED_RELATIEVEHOOGTELIGGINGGEBOUWINSTALLATIE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSGEBOUWINSTALLATIE = "AAAAAAAAAA";
    private static final String UPDATED_STATUSGEBOUWINSTALLATIE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEGEBOUWINSTALLATIE = "AAAAAAAAAA";
    private static final String UPDATED_TYPEGEBOUWINSTALLATIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gebouwinstallaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GebouwinstallatieRepository gebouwinstallatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGebouwinstallatieMockMvc;

    private Gebouwinstallatie gebouwinstallatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gebouwinstallatie createEntity(EntityManager em) {
        Gebouwinstallatie gebouwinstallatie = new Gebouwinstallatie()
            .datumbegingeldigheidgebouwinstallatie(DEFAULT_DATUMBEGINGELDIGHEIDGEBOUWINSTALLATIE)
            .datumeindegeldigheidgebouwinstallatie(DEFAULT_DATUMEINDEGELDIGHEIDGEBOUWINSTALLATIE)
            .geometriegebouwinstallatie(DEFAULT_GEOMETRIEGEBOUWINSTALLATIE)
            .identificatiegebouwinstallatie(DEFAULT_IDENTIFICATIEGEBOUWINSTALLATIE)
            .lod0geometriegebouwinstallatie(DEFAULT_LOD_0_GEOMETRIEGEBOUWINSTALLATIE)
            .relatievehoogteligginggebouwinstallatie(DEFAULT_RELATIEVEHOOGTELIGGINGGEBOUWINSTALLATIE)
            .statusgebouwinstallatie(DEFAULT_STATUSGEBOUWINSTALLATIE)
            .typegebouwinstallatie(DEFAULT_TYPEGEBOUWINSTALLATIE);
        return gebouwinstallatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gebouwinstallatie createUpdatedEntity(EntityManager em) {
        Gebouwinstallatie gebouwinstallatie = new Gebouwinstallatie()
            .datumbegingeldigheidgebouwinstallatie(UPDATED_DATUMBEGINGELDIGHEIDGEBOUWINSTALLATIE)
            .datumeindegeldigheidgebouwinstallatie(UPDATED_DATUMEINDEGELDIGHEIDGEBOUWINSTALLATIE)
            .geometriegebouwinstallatie(UPDATED_GEOMETRIEGEBOUWINSTALLATIE)
            .identificatiegebouwinstallatie(UPDATED_IDENTIFICATIEGEBOUWINSTALLATIE)
            .lod0geometriegebouwinstallatie(UPDATED_LOD_0_GEOMETRIEGEBOUWINSTALLATIE)
            .relatievehoogteligginggebouwinstallatie(UPDATED_RELATIEVEHOOGTELIGGINGGEBOUWINSTALLATIE)
            .statusgebouwinstallatie(UPDATED_STATUSGEBOUWINSTALLATIE)
            .typegebouwinstallatie(UPDATED_TYPEGEBOUWINSTALLATIE);
        return gebouwinstallatie;
    }

    @BeforeEach
    public void initTest() {
        gebouwinstallatie = createEntity(em);
    }

    @Test
    @Transactional
    void createGebouwinstallatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Gebouwinstallatie
        var returnedGebouwinstallatie = om.readValue(
            restGebouwinstallatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebouwinstallatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Gebouwinstallatie.class
        );

        // Validate the Gebouwinstallatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGebouwinstallatieUpdatableFieldsEquals(returnedGebouwinstallatie, getPersistedGebouwinstallatie(returnedGebouwinstallatie));
    }

    @Test
    @Transactional
    void createGebouwinstallatieWithExistingId() throws Exception {
        // Create the Gebouwinstallatie with an existing ID
        gebouwinstallatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGebouwinstallatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebouwinstallatie)))
            .andExpect(status().isBadRequest());

        // Validate the Gebouwinstallatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGebouwinstallaties() throws Exception {
        // Initialize the database
        gebouwinstallatieRepository.saveAndFlush(gebouwinstallatie);

        // Get all the gebouwinstallatieList
        restGebouwinstallatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gebouwinstallatie.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidgebouwinstallatie").value(
                    hasItem(DEFAULT_DATUMBEGINGELDIGHEIDGEBOUWINSTALLATIE.toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidgebouwinstallatie").value(
                    hasItem(DEFAULT_DATUMEINDEGELDIGHEIDGEBOUWINSTALLATIE.toString())
                )
            )
            .andExpect(jsonPath("$.[*].geometriegebouwinstallatie").value(hasItem(DEFAULT_GEOMETRIEGEBOUWINSTALLATIE)))
            .andExpect(jsonPath("$.[*].identificatiegebouwinstallatie").value(hasItem(DEFAULT_IDENTIFICATIEGEBOUWINSTALLATIE)))
            .andExpect(jsonPath("$.[*].lod0geometriegebouwinstallatie").value(hasItem(DEFAULT_LOD_0_GEOMETRIEGEBOUWINSTALLATIE)))
            .andExpect(
                jsonPath("$.[*].relatievehoogteligginggebouwinstallatie").value(hasItem(DEFAULT_RELATIEVEHOOGTELIGGINGGEBOUWINSTALLATIE))
            )
            .andExpect(jsonPath("$.[*].statusgebouwinstallatie").value(hasItem(DEFAULT_STATUSGEBOUWINSTALLATIE)))
            .andExpect(jsonPath("$.[*].typegebouwinstallatie").value(hasItem(DEFAULT_TYPEGEBOUWINSTALLATIE)));
    }

    @Test
    @Transactional
    void getGebouwinstallatie() throws Exception {
        // Initialize the database
        gebouwinstallatieRepository.saveAndFlush(gebouwinstallatie);

        // Get the gebouwinstallatie
        restGebouwinstallatieMockMvc
            .perform(get(ENTITY_API_URL_ID, gebouwinstallatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gebouwinstallatie.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheidgebouwinstallatie").value(DEFAULT_DATUMBEGINGELDIGHEIDGEBOUWINSTALLATIE.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidgebouwinstallatie").value(DEFAULT_DATUMEINDEGELDIGHEIDGEBOUWINSTALLATIE.toString()))
            .andExpect(jsonPath("$.geometriegebouwinstallatie").value(DEFAULT_GEOMETRIEGEBOUWINSTALLATIE))
            .andExpect(jsonPath("$.identificatiegebouwinstallatie").value(DEFAULT_IDENTIFICATIEGEBOUWINSTALLATIE))
            .andExpect(jsonPath("$.lod0geometriegebouwinstallatie").value(DEFAULT_LOD_0_GEOMETRIEGEBOUWINSTALLATIE))
            .andExpect(jsonPath("$.relatievehoogteligginggebouwinstallatie").value(DEFAULT_RELATIEVEHOOGTELIGGINGGEBOUWINSTALLATIE))
            .andExpect(jsonPath("$.statusgebouwinstallatie").value(DEFAULT_STATUSGEBOUWINSTALLATIE))
            .andExpect(jsonPath("$.typegebouwinstallatie").value(DEFAULT_TYPEGEBOUWINSTALLATIE));
    }

    @Test
    @Transactional
    void getNonExistingGebouwinstallatie() throws Exception {
        // Get the gebouwinstallatie
        restGebouwinstallatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGebouwinstallatie() throws Exception {
        // Initialize the database
        gebouwinstallatieRepository.saveAndFlush(gebouwinstallatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebouwinstallatie
        Gebouwinstallatie updatedGebouwinstallatie = gebouwinstallatieRepository.findById(gebouwinstallatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGebouwinstallatie are not directly saved in db
        em.detach(updatedGebouwinstallatie);
        updatedGebouwinstallatie
            .datumbegingeldigheidgebouwinstallatie(UPDATED_DATUMBEGINGELDIGHEIDGEBOUWINSTALLATIE)
            .datumeindegeldigheidgebouwinstallatie(UPDATED_DATUMEINDEGELDIGHEIDGEBOUWINSTALLATIE)
            .geometriegebouwinstallatie(UPDATED_GEOMETRIEGEBOUWINSTALLATIE)
            .identificatiegebouwinstallatie(UPDATED_IDENTIFICATIEGEBOUWINSTALLATIE)
            .lod0geometriegebouwinstallatie(UPDATED_LOD_0_GEOMETRIEGEBOUWINSTALLATIE)
            .relatievehoogteligginggebouwinstallatie(UPDATED_RELATIEVEHOOGTELIGGINGGEBOUWINSTALLATIE)
            .statusgebouwinstallatie(UPDATED_STATUSGEBOUWINSTALLATIE)
            .typegebouwinstallatie(UPDATED_TYPEGEBOUWINSTALLATIE);

        restGebouwinstallatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGebouwinstallatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGebouwinstallatie))
            )
            .andExpect(status().isOk());

        // Validate the Gebouwinstallatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGebouwinstallatieToMatchAllProperties(updatedGebouwinstallatie);
    }

    @Test
    @Transactional
    void putNonExistingGebouwinstallatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouwinstallatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGebouwinstallatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gebouwinstallatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gebouwinstallatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebouwinstallatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGebouwinstallatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouwinstallatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebouwinstallatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gebouwinstallatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebouwinstallatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGebouwinstallatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouwinstallatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebouwinstallatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebouwinstallatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gebouwinstallatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGebouwinstallatieWithPatch() throws Exception {
        // Initialize the database
        gebouwinstallatieRepository.saveAndFlush(gebouwinstallatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebouwinstallatie using partial update
        Gebouwinstallatie partialUpdatedGebouwinstallatie = new Gebouwinstallatie();
        partialUpdatedGebouwinstallatie.setId(gebouwinstallatie.getId());

        partialUpdatedGebouwinstallatie
            .datumbegingeldigheidgebouwinstallatie(UPDATED_DATUMBEGINGELDIGHEIDGEBOUWINSTALLATIE)
            .identificatiegebouwinstallatie(UPDATED_IDENTIFICATIEGEBOUWINSTALLATIE)
            .lod0geometriegebouwinstallatie(UPDATED_LOD_0_GEOMETRIEGEBOUWINSTALLATIE)
            .relatievehoogteligginggebouwinstallatie(UPDATED_RELATIEVEHOOGTELIGGINGGEBOUWINSTALLATIE)
            .statusgebouwinstallatie(UPDATED_STATUSGEBOUWINSTALLATIE);

        restGebouwinstallatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGebouwinstallatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGebouwinstallatie))
            )
            .andExpect(status().isOk());

        // Validate the Gebouwinstallatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGebouwinstallatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGebouwinstallatie, gebouwinstallatie),
            getPersistedGebouwinstallatie(gebouwinstallatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateGebouwinstallatieWithPatch() throws Exception {
        // Initialize the database
        gebouwinstallatieRepository.saveAndFlush(gebouwinstallatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebouwinstallatie using partial update
        Gebouwinstallatie partialUpdatedGebouwinstallatie = new Gebouwinstallatie();
        partialUpdatedGebouwinstallatie.setId(gebouwinstallatie.getId());

        partialUpdatedGebouwinstallatie
            .datumbegingeldigheidgebouwinstallatie(UPDATED_DATUMBEGINGELDIGHEIDGEBOUWINSTALLATIE)
            .datumeindegeldigheidgebouwinstallatie(UPDATED_DATUMEINDEGELDIGHEIDGEBOUWINSTALLATIE)
            .geometriegebouwinstallatie(UPDATED_GEOMETRIEGEBOUWINSTALLATIE)
            .identificatiegebouwinstallatie(UPDATED_IDENTIFICATIEGEBOUWINSTALLATIE)
            .lod0geometriegebouwinstallatie(UPDATED_LOD_0_GEOMETRIEGEBOUWINSTALLATIE)
            .relatievehoogteligginggebouwinstallatie(UPDATED_RELATIEVEHOOGTELIGGINGGEBOUWINSTALLATIE)
            .statusgebouwinstallatie(UPDATED_STATUSGEBOUWINSTALLATIE)
            .typegebouwinstallatie(UPDATED_TYPEGEBOUWINSTALLATIE);

        restGebouwinstallatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGebouwinstallatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGebouwinstallatie))
            )
            .andExpect(status().isOk());

        // Validate the Gebouwinstallatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGebouwinstallatieUpdatableFieldsEquals(
            partialUpdatedGebouwinstallatie,
            getPersistedGebouwinstallatie(partialUpdatedGebouwinstallatie)
        );
    }

    @Test
    @Transactional
    void patchNonExistingGebouwinstallatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouwinstallatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGebouwinstallatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gebouwinstallatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gebouwinstallatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebouwinstallatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGebouwinstallatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouwinstallatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebouwinstallatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gebouwinstallatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebouwinstallatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGebouwinstallatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouwinstallatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebouwinstallatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gebouwinstallatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gebouwinstallatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGebouwinstallatie() throws Exception {
        // Initialize the database
        gebouwinstallatieRepository.saveAndFlush(gebouwinstallatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gebouwinstallatie
        restGebouwinstallatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, gebouwinstallatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gebouwinstallatieRepository.count();
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

    protected Gebouwinstallatie getPersistedGebouwinstallatie(Gebouwinstallatie gebouwinstallatie) {
        return gebouwinstallatieRepository.findById(gebouwinstallatie.getId()).orElseThrow();
    }

    protected void assertPersistedGebouwinstallatieToMatchAllProperties(Gebouwinstallatie expectedGebouwinstallatie) {
        assertGebouwinstallatieAllPropertiesEquals(expectedGebouwinstallatie, getPersistedGebouwinstallatie(expectedGebouwinstallatie));
    }

    protected void assertPersistedGebouwinstallatieToMatchUpdatableProperties(Gebouwinstallatie expectedGebouwinstallatie) {
        assertGebouwinstallatieAllUpdatablePropertiesEquals(
            expectedGebouwinstallatie,
            getPersistedGebouwinstallatie(expectedGebouwinstallatie)
        );
    }
}
