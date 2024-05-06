package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GeoobjectAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Geoobject;
import nl.ritense.demo.repository.GeoobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GeoobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GeoobjectResourceIT {

    private static final String DEFAULT_DATUMBEGINGELDIGHEID = "AAAAAAAAAA";
    private static final String UPDATED_DATUMBEGINGELDIGHEID = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDEGELDIGHEID = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDEGELDIGHEID = "BBBBBBBBBB";

    private static final String DEFAULT_GEOMETRIESOORT = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIESOORT = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/geoobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GeoobjectRepository geoobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeoobjectMockMvc;

    private Geoobject geoobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Geoobject createEntity(EntityManager em) {
        Geoobject geoobject = new Geoobject()
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .geometriesoort(DEFAULT_GEOMETRIESOORT)
            .identificatie(DEFAULT_IDENTIFICATIE);
        return geoobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Geoobject createUpdatedEntity(EntityManager em) {
        Geoobject geoobject = new Geoobject()
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .geometriesoort(UPDATED_GEOMETRIESOORT)
            .identificatie(UPDATED_IDENTIFICATIE);
        return geoobject;
    }

    @BeforeEach
    public void initTest() {
        geoobject = createEntity(em);
    }

    @Test
    @Transactional
    void createGeoobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Geoobject
        var returnedGeoobject = om.readValue(
            restGeoobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(geoobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Geoobject.class
        );

        // Validate the Geoobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGeoobjectUpdatableFieldsEquals(returnedGeoobject, getPersistedGeoobject(returnedGeoobject));
    }

    @Test
    @Transactional
    void createGeoobjectWithExistingId() throws Exception {
        // Create the Geoobject with an existing ID
        geoobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeoobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(geoobject)))
            .andExpect(status().isBadRequest());

        // Validate the Geoobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGeoobjects() throws Exception {
        // Initialize the database
        geoobjectRepository.saveAndFlush(geoobject);

        // Get all the geoobjectList
        restGeoobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geoobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID)))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID)))
            .andExpect(jsonPath("$.[*].geometriesoort").value(hasItem(DEFAULT_GEOMETRIESOORT)))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)));
    }

    @Test
    @Transactional
    void getGeoobject() throws Exception {
        // Initialize the database
        geoobjectRepository.saveAndFlush(geoobject);

        // Get the geoobject
        restGeoobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, geoobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(geoobject.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID))
            .andExpect(jsonPath("$.geometriesoort").value(DEFAULT_GEOMETRIESOORT))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE));
    }

    @Test
    @Transactional
    void getNonExistingGeoobject() throws Exception {
        // Get the geoobject
        restGeoobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGeoobject() throws Exception {
        // Initialize the database
        geoobjectRepository.saveAndFlush(geoobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the geoobject
        Geoobject updatedGeoobject = geoobjectRepository.findById(geoobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGeoobject are not directly saved in db
        em.detach(updatedGeoobject);
        updatedGeoobject
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .geometriesoort(UPDATED_GEOMETRIESOORT)
            .identificatie(UPDATED_IDENTIFICATIE);

        restGeoobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGeoobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGeoobject))
            )
            .andExpect(status().isOk());

        // Validate the Geoobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGeoobjectToMatchAllProperties(updatedGeoobject);
    }

    @Test
    @Transactional
    void putNonExistingGeoobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geoobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeoobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, geoobject.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(geoobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geoobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGeoobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geoobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeoobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(geoobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geoobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGeoobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geoobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeoobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(geoobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Geoobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGeoobjectWithPatch() throws Exception {
        // Initialize the database
        geoobjectRepository.saveAndFlush(geoobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the geoobject using partial update
        Geoobject partialUpdatedGeoobject = new Geoobject();
        partialUpdatedGeoobject.setId(geoobject.getId());

        partialUpdatedGeoobject.geometriesoort(UPDATED_GEOMETRIESOORT);

        restGeoobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeoobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGeoobject))
            )
            .andExpect(status().isOk());

        // Validate the Geoobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGeoobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGeoobject, geoobject),
            getPersistedGeoobject(geoobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateGeoobjectWithPatch() throws Exception {
        // Initialize the database
        geoobjectRepository.saveAndFlush(geoobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the geoobject using partial update
        Geoobject partialUpdatedGeoobject = new Geoobject();
        partialUpdatedGeoobject.setId(geoobject.getId());

        partialUpdatedGeoobject
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .geometriesoort(UPDATED_GEOMETRIESOORT)
            .identificatie(UPDATED_IDENTIFICATIE);

        restGeoobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeoobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGeoobject))
            )
            .andExpect(status().isOk());

        // Validate the Geoobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGeoobjectUpdatableFieldsEquals(partialUpdatedGeoobject, getPersistedGeoobject(partialUpdatedGeoobject));
    }

    @Test
    @Transactional
    void patchNonExistingGeoobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geoobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeoobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, geoobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(geoobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geoobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGeoobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geoobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeoobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(geoobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geoobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGeoobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geoobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeoobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(geoobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Geoobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGeoobject() throws Exception {
        // Initialize the database
        geoobjectRepository.saveAndFlush(geoobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the geoobject
        restGeoobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, geoobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return geoobjectRepository.count();
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

    protected Geoobject getPersistedGeoobject(Geoobject geoobject) {
        return geoobjectRepository.findById(geoobject.getId()).orElseThrow();
    }

    protected void assertPersistedGeoobjectToMatchAllProperties(Geoobject expectedGeoobject) {
        assertGeoobjectAllPropertiesEquals(expectedGeoobject, getPersistedGeoobject(expectedGeoobject));
    }

    protected void assertPersistedGeoobjectToMatchUpdatableProperties(Geoobject expectedGeoobject) {
        assertGeoobjectAllUpdatablePropertiesEquals(expectedGeoobject, getPersistedGeoobject(expectedGeoobject));
    }
}
