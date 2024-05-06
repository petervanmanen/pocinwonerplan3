package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VideoopnameAsserts.*;
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
import nl.ritense.demo.domain.Videoopname;
import nl.ritense.demo.repository.VideoopnameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VideoopnameResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VideoopnameResourceIT {

    private static final String DEFAULT_BESTANDSGROOTTE = "AAAAAAAAAA";
    private static final String UPDATED_BESTANDSGROOTTE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMTIJD = "AAAAAAAAAA";
    private static final String UPDATED_DATUMTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTE = "AAAAAAAAAA";
    private static final String UPDATED_LENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEOFORMAAT = "AAAAAAAAAA";
    private static final String UPDATED_VIDEOFORMAAT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/videoopnames";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VideoopnameRepository videoopnameRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVideoopnameMockMvc;

    private Videoopname videoopname;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Videoopname createEntity(EntityManager em) {
        Videoopname videoopname = new Videoopname()
            .bestandsgrootte(DEFAULT_BESTANDSGROOTTE)
            .datumtijd(DEFAULT_DATUMTIJD)
            .lengte(DEFAULT_LENGTE)
            .videoformaat(DEFAULT_VIDEOFORMAAT);
        return videoopname;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Videoopname createUpdatedEntity(EntityManager em) {
        Videoopname videoopname = new Videoopname()
            .bestandsgrootte(UPDATED_BESTANDSGROOTTE)
            .datumtijd(UPDATED_DATUMTIJD)
            .lengte(UPDATED_LENGTE)
            .videoformaat(UPDATED_VIDEOFORMAAT);
        return videoopname;
    }

    @BeforeEach
    public void initTest() {
        videoopname = createEntity(em);
    }

    @Test
    @Transactional
    void createVideoopname() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Videoopname
        var returnedVideoopname = om.readValue(
            restVideoopnameMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(videoopname)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Videoopname.class
        );

        // Validate the Videoopname in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVideoopnameUpdatableFieldsEquals(returnedVideoopname, getPersistedVideoopname(returnedVideoopname));
    }

    @Test
    @Transactional
    void createVideoopnameWithExistingId() throws Exception {
        // Create the Videoopname with an existing ID
        videoopname.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVideoopnameMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(videoopname)))
            .andExpect(status().isBadRequest());

        // Validate the Videoopname in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVideoopnames() throws Exception {
        // Initialize the database
        videoopnameRepository.saveAndFlush(videoopname);

        // Get all the videoopnameList
        restVideoopnameMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(videoopname.getId().intValue())))
            .andExpect(jsonPath("$.[*].bestandsgrootte").value(hasItem(DEFAULT_BESTANDSGROOTTE)))
            .andExpect(jsonPath("$.[*].datumtijd").value(hasItem(DEFAULT_DATUMTIJD)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].videoformaat").value(hasItem(DEFAULT_VIDEOFORMAAT)));
    }

    @Test
    @Transactional
    void getVideoopname() throws Exception {
        // Initialize the database
        videoopnameRepository.saveAndFlush(videoopname);

        // Get the videoopname
        restVideoopnameMockMvc
            .perform(get(ENTITY_API_URL_ID, videoopname.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(videoopname.getId().intValue()))
            .andExpect(jsonPath("$.bestandsgrootte").value(DEFAULT_BESTANDSGROOTTE))
            .andExpect(jsonPath("$.datumtijd").value(DEFAULT_DATUMTIJD))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.videoformaat").value(DEFAULT_VIDEOFORMAAT));
    }

    @Test
    @Transactional
    void getNonExistingVideoopname() throws Exception {
        // Get the videoopname
        restVideoopnameMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVideoopname() throws Exception {
        // Initialize the database
        videoopnameRepository.saveAndFlush(videoopname);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the videoopname
        Videoopname updatedVideoopname = videoopnameRepository.findById(videoopname.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVideoopname are not directly saved in db
        em.detach(updatedVideoopname);
        updatedVideoopname
            .bestandsgrootte(UPDATED_BESTANDSGROOTTE)
            .datumtijd(UPDATED_DATUMTIJD)
            .lengte(UPDATED_LENGTE)
            .videoformaat(UPDATED_VIDEOFORMAAT);

        restVideoopnameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVideoopname.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVideoopname))
            )
            .andExpect(status().isOk());

        // Validate the Videoopname in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVideoopnameToMatchAllProperties(updatedVideoopname);
    }

    @Test
    @Transactional
    void putNonExistingVideoopname() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        videoopname.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideoopnameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, videoopname.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(videoopname))
            )
            .andExpect(status().isBadRequest());

        // Validate the Videoopname in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVideoopname() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        videoopname.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoopnameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(videoopname))
            )
            .andExpect(status().isBadRequest());

        // Validate the Videoopname in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVideoopname() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        videoopname.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoopnameMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(videoopname)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Videoopname in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVideoopnameWithPatch() throws Exception {
        // Initialize the database
        videoopnameRepository.saveAndFlush(videoopname);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the videoopname using partial update
        Videoopname partialUpdatedVideoopname = new Videoopname();
        partialUpdatedVideoopname.setId(videoopname.getId());

        partialUpdatedVideoopname
            .bestandsgrootte(UPDATED_BESTANDSGROOTTE)
            .datumtijd(UPDATED_DATUMTIJD)
            .lengte(UPDATED_LENGTE)
            .videoformaat(UPDATED_VIDEOFORMAAT);

        restVideoopnameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVideoopname.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVideoopname))
            )
            .andExpect(status().isOk());

        // Validate the Videoopname in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVideoopnameUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVideoopname, videoopname),
            getPersistedVideoopname(videoopname)
        );
    }

    @Test
    @Transactional
    void fullUpdateVideoopnameWithPatch() throws Exception {
        // Initialize the database
        videoopnameRepository.saveAndFlush(videoopname);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the videoopname using partial update
        Videoopname partialUpdatedVideoopname = new Videoopname();
        partialUpdatedVideoopname.setId(videoopname.getId());

        partialUpdatedVideoopname
            .bestandsgrootte(UPDATED_BESTANDSGROOTTE)
            .datumtijd(UPDATED_DATUMTIJD)
            .lengte(UPDATED_LENGTE)
            .videoformaat(UPDATED_VIDEOFORMAAT);

        restVideoopnameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVideoopname.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVideoopname))
            )
            .andExpect(status().isOk());

        // Validate the Videoopname in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVideoopnameUpdatableFieldsEquals(partialUpdatedVideoopname, getPersistedVideoopname(partialUpdatedVideoopname));
    }

    @Test
    @Transactional
    void patchNonExistingVideoopname() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        videoopname.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideoopnameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, videoopname.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(videoopname))
            )
            .andExpect(status().isBadRequest());

        // Validate the Videoopname in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVideoopname() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        videoopname.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoopnameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(videoopname))
            )
            .andExpect(status().isBadRequest());

        // Validate the Videoopname in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVideoopname() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        videoopname.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoopnameMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(videoopname)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Videoopname in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVideoopname() throws Exception {
        // Initialize the database
        videoopnameRepository.saveAndFlush(videoopname);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the videoopname
        restVideoopnameMockMvc
            .perform(delete(ENTITY_API_URL_ID, videoopname.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return videoopnameRepository.count();
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

    protected Videoopname getPersistedVideoopname(Videoopname videoopname) {
        return videoopnameRepository.findById(videoopname.getId()).orElseThrow();
    }

    protected void assertPersistedVideoopnameToMatchAllProperties(Videoopname expectedVideoopname) {
        assertVideoopnameAllPropertiesEquals(expectedVideoopname, getPersistedVideoopname(expectedVideoopname));
    }

    protected void assertPersistedVideoopnameToMatchUpdatableProperties(Videoopname expectedVideoopname) {
        assertVideoopnameAllUpdatablePropertiesEquals(expectedVideoopname, getPersistedVideoopname(expectedVideoopname));
    }
}
