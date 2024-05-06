package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VloginfoAsserts.*;
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
import nl.ritense.demo.domain.Vloginfo;
import nl.ritense.demo.repository.VloginfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VloginfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VloginfoResourceIT {

    private static final String DEFAULT_DETECTIEVERKEER = "AAAAAAAAAA";
    private static final String UPDATED_DETECTIEVERKEER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EINDEGROEN = false;
    private static final Boolean UPDATED_EINDEGROEN = true;

    private static final String DEFAULT_SNELHEID = "AAAAAAAAAA";
    private static final String UPDATED_SNELHEID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STARTGROEN = false;
    private static final Boolean UPDATED_STARTGROEN = true;

    private static final String DEFAULT_TIJDSTIP = "AAAAAAAAAA";
    private static final String UPDATED_TIJDSTIP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VERKEERWILGROEN = false;
    private static final Boolean UPDATED_VERKEERWILGROEN = true;

    private static final String DEFAULT_WACHTTIJD = "AAAAAAAAAA";
    private static final String UPDATED_WACHTTIJD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vloginfos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VloginfoRepository vloginfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVloginfoMockMvc;

    private Vloginfo vloginfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vloginfo createEntity(EntityManager em) {
        Vloginfo vloginfo = new Vloginfo()
            .detectieverkeer(DEFAULT_DETECTIEVERKEER)
            .eindegroen(DEFAULT_EINDEGROEN)
            .snelheid(DEFAULT_SNELHEID)
            .startgroen(DEFAULT_STARTGROEN)
            .tijdstip(DEFAULT_TIJDSTIP)
            .verkeerwilgroen(DEFAULT_VERKEERWILGROEN)
            .wachttijd(DEFAULT_WACHTTIJD);
        return vloginfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vloginfo createUpdatedEntity(EntityManager em) {
        Vloginfo vloginfo = new Vloginfo()
            .detectieverkeer(UPDATED_DETECTIEVERKEER)
            .eindegroen(UPDATED_EINDEGROEN)
            .snelheid(UPDATED_SNELHEID)
            .startgroen(UPDATED_STARTGROEN)
            .tijdstip(UPDATED_TIJDSTIP)
            .verkeerwilgroen(UPDATED_VERKEERWILGROEN)
            .wachttijd(UPDATED_WACHTTIJD);
        return vloginfo;
    }

    @BeforeEach
    public void initTest() {
        vloginfo = createEntity(em);
    }

    @Test
    @Transactional
    void createVloginfo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vloginfo
        var returnedVloginfo = om.readValue(
            restVloginfoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vloginfo)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vloginfo.class
        );

        // Validate the Vloginfo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVloginfoUpdatableFieldsEquals(returnedVloginfo, getPersistedVloginfo(returnedVloginfo));
    }

    @Test
    @Transactional
    void createVloginfoWithExistingId() throws Exception {
        // Create the Vloginfo with an existing ID
        vloginfo.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVloginfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vloginfo)))
            .andExpect(status().isBadRequest());

        // Validate the Vloginfo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVloginfos() throws Exception {
        // Initialize the database
        vloginfoRepository.saveAndFlush(vloginfo);

        // Get all the vloginfoList
        restVloginfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vloginfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].detectieverkeer").value(hasItem(DEFAULT_DETECTIEVERKEER)))
            .andExpect(jsonPath("$.[*].eindegroen").value(hasItem(DEFAULT_EINDEGROEN.booleanValue())))
            .andExpect(jsonPath("$.[*].snelheid").value(hasItem(DEFAULT_SNELHEID)))
            .andExpect(jsonPath("$.[*].startgroen").value(hasItem(DEFAULT_STARTGROEN.booleanValue())))
            .andExpect(jsonPath("$.[*].tijdstip").value(hasItem(DEFAULT_TIJDSTIP)))
            .andExpect(jsonPath("$.[*].verkeerwilgroen").value(hasItem(DEFAULT_VERKEERWILGROEN.booleanValue())))
            .andExpect(jsonPath("$.[*].wachttijd").value(hasItem(DEFAULT_WACHTTIJD)));
    }

    @Test
    @Transactional
    void getVloginfo() throws Exception {
        // Initialize the database
        vloginfoRepository.saveAndFlush(vloginfo);

        // Get the vloginfo
        restVloginfoMockMvc
            .perform(get(ENTITY_API_URL_ID, vloginfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vloginfo.getId().intValue()))
            .andExpect(jsonPath("$.detectieverkeer").value(DEFAULT_DETECTIEVERKEER))
            .andExpect(jsonPath("$.eindegroen").value(DEFAULT_EINDEGROEN.booleanValue()))
            .andExpect(jsonPath("$.snelheid").value(DEFAULT_SNELHEID))
            .andExpect(jsonPath("$.startgroen").value(DEFAULT_STARTGROEN.booleanValue()))
            .andExpect(jsonPath("$.tijdstip").value(DEFAULT_TIJDSTIP))
            .andExpect(jsonPath("$.verkeerwilgroen").value(DEFAULT_VERKEERWILGROEN.booleanValue()))
            .andExpect(jsonPath("$.wachttijd").value(DEFAULT_WACHTTIJD));
    }

    @Test
    @Transactional
    void getNonExistingVloginfo() throws Exception {
        // Get the vloginfo
        restVloginfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVloginfo() throws Exception {
        // Initialize the database
        vloginfoRepository.saveAndFlush(vloginfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vloginfo
        Vloginfo updatedVloginfo = vloginfoRepository.findById(vloginfo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVloginfo are not directly saved in db
        em.detach(updatedVloginfo);
        updatedVloginfo
            .detectieverkeer(UPDATED_DETECTIEVERKEER)
            .eindegroen(UPDATED_EINDEGROEN)
            .snelheid(UPDATED_SNELHEID)
            .startgroen(UPDATED_STARTGROEN)
            .tijdstip(UPDATED_TIJDSTIP)
            .verkeerwilgroen(UPDATED_VERKEERWILGROEN)
            .wachttijd(UPDATED_WACHTTIJD);

        restVloginfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVloginfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVloginfo))
            )
            .andExpect(status().isOk());

        // Validate the Vloginfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVloginfoToMatchAllProperties(updatedVloginfo);
    }

    @Test
    @Transactional
    void putNonExistingVloginfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vloginfo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVloginfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vloginfo.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vloginfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vloginfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVloginfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vloginfo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVloginfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vloginfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vloginfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVloginfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vloginfo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVloginfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vloginfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vloginfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVloginfoWithPatch() throws Exception {
        // Initialize the database
        vloginfoRepository.saveAndFlush(vloginfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vloginfo using partial update
        Vloginfo partialUpdatedVloginfo = new Vloginfo();
        partialUpdatedVloginfo.setId(vloginfo.getId());

        partialUpdatedVloginfo.tijdstip(UPDATED_TIJDSTIP).wachttijd(UPDATED_WACHTTIJD);

        restVloginfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVloginfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVloginfo))
            )
            .andExpect(status().isOk());

        // Validate the Vloginfo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVloginfoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVloginfo, vloginfo), getPersistedVloginfo(vloginfo));
    }

    @Test
    @Transactional
    void fullUpdateVloginfoWithPatch() throws Exception {
        // Initialize the database
        vloginfoRepository.saveAndFlush(vloginfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vloginfo using partial update
        Vloginfo partialUpdatedVloginfo = new Vloginfo();
        partialUpdatedVloginfo.setId(vloginfo.getId());

        partialUpdatedVloginfo
            .detectieverkeer(UPDATED_DETECTIEVERKEER)
            .eindegroen(UPDATED_EINDEGROEN)
            .snelheid(UPDATED_SNELHEID)
            .startgroen(UPDATED_STARTGROEN)
            .tijdstip(UPDATED_TIJDSTIP)
            .verkeerwilgroen(UPDATED_VERKEERWILGROEN)
            .wachttijd(UPDATED_WACHTTIJD);

        restVloginfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVloginfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVloginfo))
            )
            .andExpect(status().isOk());

        // Validate the Vloginfo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVloginfoUpdatableFieldsEquals(partialUpdatedVloginfo, getPersistedVloginfo(partialUpdatedVloginfo));
    }

    @Test
    @Transactional
    void patchNonExistingVloginfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vloginfo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVloginfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vloginfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vloginfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vloginfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVloginfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vloginfo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVloginfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vloginfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vloginfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVloginfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vloginfo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVloginfoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vloginfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vloginfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVloginfo() throws Exception {
        // Initialize the database
        vloginfoRepository.saveAndFlush(vloginfo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vloginfo
        restVloginfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, vloginfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vloginfoRepository.count();
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

    protected Vloginfo getPersistedVloginfo(Vloginfo vloginfo) {
        return vloginfoRepository.findById(vloginfo.getId()).orElseThrow();
    }

    protected void assertPersistedVloginfoToMatchAllProperties(Vloginfo expectedVloginfo) {
        assertVloginfoAllPropertiesEquals(expectedVloginfo, getPersistedVloginfo(expectedVloginfo));
    }

    protected void assertPersistedVloginfoToMatchUpdatableProperties(Vloginfo expectedVloginfo) {
        assertVloginfoAllUpdatablePropertiesEquals(expectedVloginfo, getPersistedVloginfo(expectedVloginfo));
    }
}
