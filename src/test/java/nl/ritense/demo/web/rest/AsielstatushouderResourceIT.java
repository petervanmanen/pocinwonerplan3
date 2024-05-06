package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AsielstatushouderAsserts.*;
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
import nl.ritense.demo.domain.Asielstatushouder;
import nl.ritense.demo.repository.AsielstatushouderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AsielstatushouderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AsielstatushouderResourceIT {

    private static final String DEFAULT_DIGIDAANGEVRAAGD = "AAAAAAAAAA";
    private static final String UPDATED_DIGIDAANGEVRAAGD = "BBBBBBBBBB";

    private static final String DEFAULT_EMAILADRESVERBLIJFAZC = "AAAAAAAAAA";
    private static final String UPDATED_EMAILADRESVERBLIJFAZC = "BBBBBBBBBB";

    private static final String DEFAULT_ISGEKOPPELDAAN = "AAAAAAAAAA";
    private static final String UPDATED_ISGEKOPPELDAAN = "BBBBBBBBBB";

    private static final String DEFAULT_LANDRIJBEWIJS = "AAAAAAAAAA";
    private static final String UPDATED_LANDRIJBEWIJS = "BBBBBBBBBB";

    private static final String DEFAULT_RIJBEWIJS = "AAAAAAAAAA";
    private static final String UPDATED_RIJBEWIJS = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFOONNUMMERVERBLIJFAZC = "AAAAAAAAAA";
    private static final String UPDATED_TELEFOONNUMMERVERBLIJFAZC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/asielstatushouders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AsielstatushouderRepository asielstatushouderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAsielstatushouderMockMvc;

    private Asielstatushouder asielstatushouder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asielstatushouder createEntity(EntityManager em) {
        Asielstatushouder asielstatushouder = new Asielstatushouder()
            .digidaangevraagd(DEFAULT_DIGIDAANGEVRAAGD)
            .emailadresverblijfazc(DEFAULT_EMAILADRESVERBLIJFAZC)
            .isgekoppeldaan(DEFAULT_ISGEKOPPELDAAN)
            .landrijbewijs(DEFAULT_LANDRIJBEWIJS)
            .rijbewijs(DEFAULT_RIJBEWIJS)
            .telefoonnummerverblijfazc(DEFAULT_TELEFOONNUMMERVERBLIJFAZC);
        return asielstatushouder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asielstatushouder createUpdatedEntity(EntityManager em) {
        Asielstatushouder asielstatushouder = new Asielstatushouder()
            .digidaangevraagd(UPDATED_DIGIDAANGEVRAAGD)
            .emailadresverblijfazc(UPDATED_EMAILADRESVERBLIJFAZC)
            .isgekoppeldaan(UPDATED_ISGEKOPPELDAAN)
            .landrijbewijs(UPDATED_LANDRIJBEWIJS)
            .rijbewijs(UPDATED_RIJBEWIJS)
            .telefoonnummerverblijfazc(UPDATED_TELEFOONNUMMERVERBLIJFAZC);
        return asielstatushouder;
    }

    @BeforeEach
    public void initTest() {
        asielstatushouder = createEntity(em);
    }

    @Test
    @Transactional
    void createAsielstatushouder() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Asielstatushouder
        var returnedAsielstatushouder = om.readValue(
            restAsielstatushouderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asielstatushouder)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Asielstatushouder.class
        );

        // Validate the Asielstatushouder in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAsielstatushouderUpdatableFieldsEquals(returnedAsielstatushouder, getPersistedAsielstatushouder(returnedAsielstatushouder));
    }

    @Test
    @Transactional
    void createAsielstatushouderWithExistingId() throws Exception {
        // Create the Asielstatushouder with an existing ID
        asielstatushouder.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAsielstatushouderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asielstatushouder)))
            .andExpect(status().isBadRequest());

        // Validate the Asielstatushouder in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAsielstatushouders() throws Exception {
        // Initialize the database
        asielstatushouderRepository.saveAndFlush(asielstatushouder);

        // Get all the asielstatushouderList
        restAsielstatushouderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asielstatushouder.getId().intValue())))
            .andExpect(jsonPath("$.[*].digidaangevraagd").value(hasItem(DEFAULT_DIGIDAANGEVRAAGD)))
            .andExpect(jsonPath("$.[*].emailadresverblijfazc").value(hasItem(DEFAULT_EMAILADRESVERBLIJFAZC)))
            .andExpect(jsonPath("$.[*].isgekoppeldaan").value(hasItem(DEFAULT_ISGEKOPPELDAAN)))
            .andExpect(jsonPath("$.[*].landrijbewijs").value(hasItem(DEFAULT_LANDRIJBEWIJS)))
            .andExpect(jsonPath("$.[*].rijbewijs").value(hasItem(DEFAULT_RIJBEWIJS)))
            .andExpect(jsonPath("$.[*].telefoonnummerverblijfazc").value(hasItem(DEFAULT_TELEFOONNUMMERVERBLIJFAZC)));
    }

    @Test
    @Transactional
    void getAsielstatushouder() throws Exception {
        // Initialize the database
        asielstatushouderRepository.saveAndFlush(asielstatushouder);

        // Get the asielstatushouder
        restAsielstatushouderMockMvc
            .perform(get(ENTITY_API_URL_ID, asielstatushouder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(asielstatushouder.getId().intValue()))
            .andExpect(jsonPath("$.digidaangevraagd").value(DEFAULT_DIGIDAANGEVRAAGD))
            .andExpect(jsonPath("$.emailadresverblijfazc").value(DEFAULT_EMAILADRESVERBLIJFAZC))
            .andExpect(jsonPath("$.isgekoppeldaan").value(DEFAULT_ISGEKOPPELDAAN))
            .andExpect(jsonPath("$.landrijbewijs").value(DEFAULT_LANDRIJBEWIJS))
            .andExpect(jsonPath("$.rijbewijs").value(DEFAULT_RIJBEWIJS))
            .andExpect(jsonPath("$.telefoonnummerverblijfazc").value(DEFAULT_TELEFOONNUMMERVERBLIJFAZC));
    }

    @Test
    @Transactional
    void getNonExistingAsielstatushouder() throws Exception {
        // Get the asielstatushouder
        restAsielstatushouderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAsielstatushouder() throws Exception {
        // Initialize the database
        asielstatushouderRepository.saveAndFlush(asielstatushouder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asielstatushouder
        Asielstatushouder updatedAsielstatushouder = asielstatushouderRepository.findById(asielstatushouder.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAsielstatushouder are not directly saved in db
        em.detach(updatedAsielstatushouder);
        updatedAsielstatushouder
            .digidaangevraagd(UPDATED_DIGIDAANGEVRAAGD)
            .emailadresverblijfazc(UPDATED_EMAILADRESVERBLIJFAZC)
            .isgekoppeldaan(UPDATED_ISGEKOPPELDAAN)
            .landrijbewijs(UPDATED_LANDRIJBEWIJS)
            .rijbewijs(UPDATED_RIJBEWIJS)
            .telefoonnummerverblijfazc(UPDATED_TELEFOONNUMMERVERBLIJFAZC);

        restAsielstatushouderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAsielstatushouder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAsielstatushouder))
            )
            .andExpect(status().isOk());

        // Validate the Asielstatushouder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAsielstatushouderToMatchAllProperties(updatedAsielstatushouder);
    }

    @Test
    @Transactional
    void putNonExistingAsielstatushouder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asielstatushouder.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAsielstatushouderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, asielstatushouder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(asielstatushouder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asielstatushouder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAsielstatushouder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asielstatushouder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsielstatushouderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(asielstatushouder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asielstatushouder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAsielstatushouder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asielstatushouder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsielstatushouderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asielstatushouder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Asielstatushouder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAsielstatushouderWithPatch() throws Exception {
        // Initialize the database
        asielstatushouderRepository.saveAndFlush(asielstatushouder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asielstatushouder using partial update
        Asielstatushouder partialUpdatedAsielstatushouder = new Asielstatushouder();
        partialUpdatedAsielstatushouder.setId(asielstatushouder.getId());

        partialUpdatedAsielstatushouder
            .isgekoppeldaan(UPDATED_ISGEKOPPELDAAN)
            .landrijbewijs(UPDATED_LANDRIJBEWIJS)
            .telefoonnummerverblijfazc(UPDATED_TELEFOONNUMMERVERBLIJFAZC);

        restAsielstatushouderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsielstatushouder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAsielstatushouder))
            )
            .andExpect(status().isOk());

        // Validate the Asielstatushouder in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAsielstatushouderUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAsielstatushouder, asielstatushouder),
            getPersistedAsielstatushouder(asielstatushouder)
        );
    }

    @Test
    @Transactional
    void fullUpdateAsielstatushouderWithPatch() throws Exception {
        // Initialize the database
        asielstatushouderRepository.saveAndFlush(asielstatushouder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asielstatushouder using partial update
        Asielstatushouder partialUpdatedAsielstatushouder = new Asielstatushouder();
        partialUpdatedAsielstatushouder.setId(asielstatushouder.getId());

        partialUpdatedAsielstatushouder
            .digidaangevraagd(UPDATED_DIGIDAANGEVRAAGD)
            .emailadresverblijfazc(UPDATED_EMAILADRESVERBLIJFAZC)
            .isgekoppeldaan(UPDATED_ISGEKOPPELDAAN)
            .landrijbewijs(UPDATED_LANDRIJBEWIJS)
            .rijbewijs(UPDATED_RIJBEWIJS)
            .telefoonnummerverblijfazc(UPDATED_TELEFOONNUMMERVERBLIJFAZC);

        restAsielstatushouderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsielstatushouder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAsielstatushouder))
            )
            .andExpect(status().isOk());

        // Validate the Asielstatushouder in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAsielstatushouderUpdatableFieldsEquals(
            partialUpdatedAsielstatushouder,
            getPersistedAsielstatushouder(partialUpdatedAsielstatushouder)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAsielstatushouder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asielstatushouder.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAsielstatushouderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, asielstatushouder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(asielstatushouder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asielstatushouder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAsielstatushouder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asielstatushouder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsielstatushouderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(asielstatushouder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asielstatushouder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAsielstatushouder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asielstatushouder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsielstatushouderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(asielstatushouder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Asielstatushouder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAsielstatushouder() throws Exception {
        // Initialize the database
        asielstatushouderRepository.saveAndFlush(asielstatushouder);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the asielstatushouder
        restAsielstatushouderMockMvc
            .perform(delete(ENTITY_API_URL_ID, asielstatushouder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return asielstatushouderRepository.count();
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

    protected Asielstatushouder getPersistedAsielstatushouder(Asielstatushouder asielstatushouder) {
        return asielstatushouderRepository.findById(asielstatushouder.getId()).orElseThrow();
    }

    protected void assertPersistedAsielstatushouderToMatchAllProperties(Asielstatushouder expectedAsielstatushouder) {
        assertAsielstatushouderAllPropertiesEquals(expectedAsielstatushouder, getPersistedAsielstatushouder(expectedAsielstatushouder));
    }

    protected void assertPersistedAsielstatushouderToMatchUpdatableProperties(Asielstatushouder expectedAsielstatushouder) {
        assertAsielstatushouderAllUpdatablePropertiesEquals(
            expectedAsielstatushouder,
            getPersistedAsielstatushouder(expectedAsielstatushouder)
        );
    }
}
