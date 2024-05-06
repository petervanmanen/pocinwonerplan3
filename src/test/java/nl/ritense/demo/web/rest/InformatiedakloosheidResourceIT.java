package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.InformatiedakloosheidAsserts.*;
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
import nl.ritense.demo.domain.Informatiedakloosheid;
import nl.ritense.demo.repository.InformatiedakloosheidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InformatiedakloosheidResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InformatiedakloosheidResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEMEENTEOORSPRONG = "AAAAAAAAAA";
    private static final String UPDATED_GEMEENTEOORSPRONG = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TOESTEMMINGGEMEENTELIJKBRIEFADRES = false;
    private static final Boolean UPDATED_TOESTEMMINGGEMEENTELIJKBRIEFADRES = true;

    private static final Boolean DEFAULT_TOESTEMMINGNACHTOPVANG = false;
    private static final Boolean UPDATED_TOESTEMMINGNACHTOPVANG = true;

    private static final String ENTITY_API_URL = "/api/informatiedakloosheids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InformatiedakloosheidRepository informatiedakloosheidRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInformatiedakloosheidMockMvc;

    private Informatiedakloosheid informatiedakloosheid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Informatiedakloosheid createEntity(EntityManager em) {
        Informatiedakloosheid informatiedakloosheid = new Informatiedakloosheid()
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .gemeenteoorsprong(DEFAULT_GEMEENTEOORSPRONG)
            .toestemminggemeentelijkbriefadres(DEFAULT_TOESTEMMINGGEMEENTELIJKBRIEFADRES)
            .toestemmingnachtopvang(DEFAULT_TOESTEMMINGNACHTOPVANG);
        return informatiedakloosheid;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Informatiedakloosheid createUpdatedEntity(EntityManager em) {
        Informatiedakloosheid informatiedakloosheid = new Informatiedakloosheid()
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .gemeenteoorsprong(UPDATED_GEMEENTEOORSPRONG)
            .toestemminggemeentelijkbriefadres(UPDATED_TOESTEMMINGGEMEENTELIJKBRIEFADRES)
            .toestemmingnachtopvang(UPDATED_TOESTEMMINGNACHTOPVANG);
        return informatiedakloosheid;
    }

    @BeforeEach
    public void initTest() {
        informatiedakloosheid = createEntity(em);
    }

    @Test
    @Transactional
    void createInformatiedakloosheid() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Informatiedakloosheid
        var returnedInformatiedakloosheid = om.readValue(
            restInformatiedakloosheidMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(informatiedakloosheid)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Informatiedakloosheid.class
        );

        // Validate the Informatiedakloosheid in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInformatiedakloosheidUpdatableFieldsEquals(
            returnedInformatiedakloosheid,
            getPersistedInformatiedakloosheid(returnedInformatiedakloosheid)
        );
    }

    @Test
    @Transactional
    void createInformatiedakloosheidWithExistingId() throws Exception {
        // Create the Informatiedakloosheid with an existing ID
        informatiedakloosheid.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInformatiedakloosheidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(informatiedakloosheid)))
            .andExpect(status().isBadRequest());

        // Validate the Informatiedakloosheid in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInformatiedakloosheids() throws Exception {
        // Initialize the database
        informatiedakloosheidRepository.saveAndFlush(informatiedakloosheid);

        // Get all the informatiedakloosheidList
        restInformatiedakloosheidMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(informatiedakloosheid.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].gemeenteoorsprong").value(hasItem(DEFAULT_GEMEENTEOORSPRONG)))
            .andExpect(
                jsonPath("$.[*].toestemminggemeentelijkbriefadres").value(hasItem(DEFAULT_TOESTEMMINGGEMEENTELIJKBRIEFADRES.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].toestemmingnachtopvang").value(hasItem(DEFAULT_TOESTEMMINGNACHTOPVANG.booleanValue())));
    }

    @Test
    @Transactional
    void getInformatiedakloosheid() throws Exception {
        // Initialize the database
        informatiedakloosheidRepository.saveAndFlush(informatiedakloosheid);

        // Get the informatiedakloosheid
        restInformatiedakloosheidMockMvc
            .perform(get(ENTITY_API_URL_ID, informatiedakloosheid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(informatiedakloosheid.getId().intValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.gemeenteoorsprong").value(DEFAULT_GEMEENTEOORSPRONG))
            .andExpect(jsonPath("$.toestemminggemeentelijkbriefadres").value(DEFAULT_TOESTEMMINGGEMEENTELIJKBRIEFADRES.booleanValue()))
            .andExpect(jsonPath("$.toestemmingnachtopvang").value(DEFAULT_TOESTEMMINGNACHTOPVANG.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingInformatiedakloosheid() throws Exception {
        // Get the informatiedakloosheid
        restInformatiedakloosheidMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInformatiedakloosheid() throws Exception {
        // Initialize the database
        informatiedakloosheidRepository.saveAndFlush(informatiedakloosheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the informatiedakloosheid
        Informatiedakloosheid updatedInformatiedakloosheid = informatiedakloosheidRepository
            .findById(informatiedakloosheid.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedInformatiedakloosheid are not directly saved in db
        em.detach(updatedInformatiedakloosheid);
        updatedInformatiedakloosheid
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .gemeenteoorsprong(UPDATED_GEMEENTEOORSPRONG)
            .toestemminggemeentelijkbriefadres(UPDATED_TOESTEMMINGGEMEENTELIJKBRIEFADRES)
            .toestemmingnachtopvang(UPDATED_TOESTEMMINGNACHTOPVANG);

        restInformatiedakloosheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInformatiedakloosheid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInformatiedakloosheid))
            )
            .andExpect(status().isOk());

        // Validate the Informatiedakloosheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInformatiedakloosheidToMatchAllProperties(updatedInformatiedakloosheid);
    }

    @Test
    @Transactional
    void putNonExistingInformatiedakloosheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        informatiedakloosheid.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInformatiedakloosheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, informatiedakloosheid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(informatiedakloosheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Informatiedakloosheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInformatiedakloosheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        informatiedakloosheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInformatiedakloosheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(informatiedakloosheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Informatiedakloosheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInformatiedakloosheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        informatiedakloosheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInformatiedakloosheidMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(informatiedakloosheid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Informatiedakloosheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInformatiedakloosheidWithPatch() throws Exception {
        // Initialize the database
        informatiedakloosheidRepository.saveAndFlush(informatiedakloosheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the informatiedakloosheid using partial update
        Informatiedakloosheid partialUpdatedInformatiedakloosheid = new Informatiedakloosheid();
        partialUpdatedInformatiedakloosheid.setId(informatiedakloosheid.getId());

        partialUpdatedInformatiedakloosheid
            .datumstart(UPDATED_DATUMSTART)
            .toestemminggemeentelijkbriefadres(UPDATED_TOESTEMMINGGEMEENTELIJKBRIEFADRES);

        restInformatiedakloosheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInformatiedakloosheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInformatiedakloosheid))
            )
            .andExpect(status().isOk());

        // Validate the Informatiedakloosheid in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInformatiedakloosheidUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInformatiedakloosheid, informatiedakloosheid),
            getPersistedInformatiedakloosheid(informatiedakloosheid)
        );
    }

    @Test
    @Transactional
    void fullUpdateInformatiedakloosheidWithPatch() throws Exception {
        // Initialize the database
        informatiedakloosheidRepository.saveAndFlush(informatiedakloosheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the informatiedakloosheid using partial update
        Informatiedakloosheid partialUpdatedInformatiedakloosheid = new Informatiedakloosheid();
        partialUpdatedInformatiedakloosheid.setId(informatiedakloosheid.getId());

        partialUpdatedInformatiedakloosheid
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .gemeenteoorsprong(UPDATED_GEMEENTEOORSPRONG)
            .toestemminggemeentelijkbriefadres(UPDATED_TOESTEMMINGGEMEENTELIJKBRIEFADRES)
            .toestemmingnachtopvang(UPDATED_TOESTEMMINGNACHTOPVANG);

        restInformatiedakloosheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInformatiedakloosheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInformatiedakloosheid))
            )
            .andExpect(status().isOk());

        // Validate the Informatiedakloosheid in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInformatiedakloosheidUpdatableFieldsEquals(
            partialUpdatedInformatiedakloosheid,
            getPersistedInformatiedakloosheid(partialUpdatedInformatiedakloosheid)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInformatiedakloosheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        informatiedakloosheid.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInformatiedakloosheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, informatiedakloosheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(informatiedakloosheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Informatiedakloosheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInformatiedakloosheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        informatiedakloosheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInformatiedakloosheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(informatiedakloosheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Informatiedakloosheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInformatiedakloosheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        informatiedakloosheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInformatiedakloosheidMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(informatiedakloosheid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Informatiedakloosheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInformatiedakloosheid() throws Exception {
        // Initialize the database
        informatiedakloosheidRepository.saveAndFlush(informatiedakloosheid);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the informatiedakloosheid
        restInformatiedakloosheidMockMvc
            .perform(delete(ENTITY_API_URL_ID, informatiedakloosheid.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return informatiedakloosheidRepository.count();
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

    protected Informatiedakloosheid getPersistedInformatiedakloosheid(Informatiedakloosheid informatiedakloosheid) {
        return informatiedakloosheidRepository.findById(informatiedakloosheid.getId()).orElseThrow();
    }

    protected void assertPersistedInformatiedakloosheidToMatchAllProperties(Informatiedakloosheid expectedInformatiedakloosheid) {
        assertInformatiedakloosheidAllPropertiesEquals(
            expectedInformatiedakloosheid,
            getPersistedInformatiedakloosheid(expectedInformatiedakloosheid)
        );
    }

    protected void assertPersistedInformatiedakloosheidToMatchUpdatableProperties(Informatiedakloosheid expectedInformatiedakloosheid) {
        assertInformatiedakloosheidAllUpdatablePropertiesEquals(
            expectedInformatiedakloosheid,
            getPersistedInformatiedakloosheid(expectedInformatiedakloosheid)
        );
    }
}
