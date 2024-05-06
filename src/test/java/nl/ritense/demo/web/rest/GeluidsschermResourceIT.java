package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GeluidsschermAsserts.*;
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
import nl.ritense.demo.domain.Geluidsscherm;
import nl.ritense.demo.repository.GeluidsschermRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GeluidsschermResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GeluidsschermResourceIT {

    private static final String DEFAULT_AANTALDEUREN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALDEUREN = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALPANELEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALPANELEN = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/geluidsscherms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GeluidsschermRepository geluidsschermRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeluidsschermMockMvc;

    private Geluidsscherm geluidsscherm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Geluidsscherm createEntity(EntityManager em) {
        Geluidsscherm geluidsscherm = new Geluidsscherm()
            .aantaldeuren(DEFAULT_AANTALDEUREN)
            .aantalpanelen(DEFAULT_AANTALPANELEN)
            .type(DEFAULT_TYPE);
        return geluidsscherm;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Geluidsscherm createUpdatedEntity(EntityManager em) {
        Geluidsscherm geluidsscherm = new Geluidsscherm()
            .aantaldeuren(UPDATED_AANTALDEUREN)
            .aantalpanelen(UPDATED_AANTALPANELEN)
            .type(UPDATED_TYPE);
        return geluidsscherm;
    }

    @BeforeEach
    public void initTest() {
        geluidsscherm = createEntity(em);
    }

    @Test
    @Transactional
    void createGeluidsscherm() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Geluidsscherm
        var returnedGeluidsscherm = om.readValue(
            restGeluidsschermMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(geluidsscherm)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Geluidsscherm.class
        );

        // Validate the Geluidsscherm in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGeluidsschermUpdatableFieldsEquals(returnedGeluidsscherm, getPersistedGeluidsscherm(returnedGeluidsscherm));
    }

    @Test
    @Transactional
    void createGeluidsschermWithExistingId() throws Exception {
        // Create the Geluidsscherm with an existing ID
        geluidsscherm.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeluidsschermMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(geluidsscherm)))
            .andExpect(status().isBadRequest());

        // Validate the Geluidsscherm in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGeluidsscherms() throws Exception {
        // Initialize the database
        geluidsschermRepository.saveAndFlush(geluidsscherm);

        // Get all the geluidsschermList
        restGeluidsschermMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geluidsscherm.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantaldeuren").value(hasItem(DEFAULT_AANTALDEUREN)))
            .andExpect(jsonPath("$.[*].aantalpanelen").value(hasItem(DEFAULT_AANTALPANELEN)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getGeluidsscherm() throws Exception {
        // Initialize the database
        geluidsschermRepository.saveAndFlush(geluidsscherm);

        // Get the geluidsscherm
        restGeluidsschermMockMvc
            .perform(get(ENTITY_API_URL_ID, geluidsscherm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(geluidsscherm.getId().intValue()))
            .andExpect(jsonPath("$.aantaldeuren").value(DEFAULT_AANTALDEUREN))
            .andExpect(jsonPath("$.aantalpanelen").value(DEFAULT_AANTALPANELEN))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingGeluidsscherm() throws Exception {
        // Get the geluidsscherm
        restGeluidsschermMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGeluidsscherm() throws Exception {
        // Initialize the database
        geluidsschermRepository.saveAndFlush(geluidsscherm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the geluidsscherm
        Geluidsscherm updatedGeluidsscherm = geluidsschermRepository.findById(geluidsscherm.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGeluidsscherm are not directly saved in db
        em.detach(updatedGeluidsscherm);
        updatedGeluidsscherm.aantaldeuren(UPDATED_AANTALDEUREN).aantalpanelen(UPDATED_AANTALPANELEN).type(UPDATED_TYPE);

        restGeluidsschermMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGeluidsscherm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGeluidsscherm))
            )
            .andExpect(status().isOk());

        // Validate the Geluidsscherm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGeluidsschermToMatchAllProperties(updatedGeluidsscherm);
    }

    @Test
    @Transactional
    void putNonExistingGeluidsscherm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geluidsscherm.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeluidsschermMockMvc
            .perform(
                put(ENTITY_API_URL_ID, geluidsscherm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(geluidsscherm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geluidsscherm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGeluidsscherm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geluidsscherm.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeluidsschermMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(geluidsscherm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geluidsscherm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGeluidsscherm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geluidsscherm.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeluidsschermMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(geluidsscherm)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Geluidsscherm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGeluidsschermWithPatch() throws Exception {
        // Initialize the database
        geluidsschermRepository.saveAndFlush(geluidsscherm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the geluidsscherm using partial update
        Geluidsscherm partialUpdatedGeluidsscherm = new Geluidsscherm();
        partialUpdatedGeluidsscherm.setId(geluidsscherm.getId());

        partialUpdatedGeluidsscherm.aantaldeuren(UPDATED_AANTALDEUREN);

        restGeluidsschermMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeluidsscherm.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGeluidsscherm))
            )
            .andExpect(status().isOk());

        // Validate the Geluidsscherm in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGeluidsschermUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGeluidsscherm, geluidsscherm),
            getPersistedGeluidsscherm(geluidsscherm)
        );
    }

    @Test
    @Transactional
    void fullUpdateGeluidsschermWithPatch() throws Exception {
        // Initialize the database
        geluidsschermRepository.saveAndFlush(geluidsscherm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the geluidsscherm using partial update
        Geluidsscherm partialUpdatedGeluidsscherm = new Geluidsscherm();
        partialUpdatedGeluidsscherm.setId(geluidsscherm.getId());

        partialUpdatedGeluidsscherm.aantaldeuren(UPDATED_AANTALDEUREN).aantalpanelen(UPDATED_AANTALPANELEN).type(UPDATED_TYPE);

        restGeluidsschermMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeluidsscherm.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGeluidsscherm))
            )
            .andExpect(status().isOk());

        // Validate the Geluidsscherm in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGeluidsschermUpdatableFieldsEquals(partialUpdatedGeluidsscherm, getPersistedGeluidsscherm(partialUpdatedGeluidsscherm));
    }

    @Test
    @Transactional
    void patchNonExistingGeluidsscherm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geluidsscherm.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeluidsschermMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, geluidsscherm.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(geluidsscherm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geluidsscherm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGeluidsscherm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geluidsscherm.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeluidsschermMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(geluidsscherm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geluidsscherm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGeluidsscherm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geluidsscherm.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeluidsschermMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(geluidsscherm)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Geluidsscherm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGeluidsscherm() throws Exception {
        // Initialize the database
        geluidsschermRepository.saveAndFlush(geluidsscherm);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the geluidsscherm
        restGeluidsschermMockMvc
            .perform(delete(ENTITY_API_URL_ID, geluidsscherm.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return geluidsschermRepository.count();
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

    protected Geluidsscherm getPersistedGeluidsscherm(Geluidsscherm geluidsscherm) {
        return geluidsschermRepository.findById(geluidsscherm.getId()).orElseThrow();
    }

    protected void assertPersistedGeluidsschermToMatchAllProperties(Geluidsscherm expectedGeluidsscherm) {
        assertGeluidsschermAllPropertiesEquals(expectedGeluidsscherm, getPersistedGeluidsscherm(expectedGeluidsscherm));
    }

    protected void assertPersistedGeluidsschermToMatchUpdatableProperties(Geluidsscherm expectedGeluidsscherm) {
        assertGeluidsschermAllUpdatablePropertiesEquals(expectedGeluidsscherm, getPersistedGeluidsscherm(expectedGeluidsscherm));
    }
}
