package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DienstverbandAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Dienstverband;
import nl.ritense.demo.repository.DienstverbandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DienstverbandResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class DienstverbandResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PERIODIEK = "AAAAAAAAAA";
    private static final String UPDATED_PERIODIEK = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SALARIS = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALARIS = new BigDecimal(2);

    private static final String DEFAULT_SCHAAL = "AAAAAAAAAA";
    private static final String UPDATED_SCHAAL = "BBBBBBBBBB";

    private static final String DEFAULT_URENPERWEEK = "AAAAAAAAAA";
    private static final String UPDATED_URENPERWEEK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dienstverbands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DienstverbandRepository dienstverbandRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDienstverbandMockMvc;

    private Dienstverband dienstverband;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dienstverband createEntity(EntityManager em) {
        Dienstverband dienstverband = new Dienstverband()
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .periodiek(DEFAULT_PERIODIEK)
            .salaris(DEFAULT_SALARIS)
            .schaal(DEFAULT_SCHAAL)
            .urenperweek(DEFAULT_URENPERWEEK);
        return dienstverband;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dienstverband createUpdatedEntity(EntityManager em) {
        Dienstverband dienstverband = new Dienstverband()
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .periodiek(UPDATED_PERIODIEK)
            .salaris(UPDATED_SALARIS)
            .schaal(UPDATED_SCHAAL)
            .urenperweek(UPDATED_URENPERWEEK);
        return dienstverband;
    }

    @BeforeEach
    public void initTest() {
        dienstverband = createEntity(em);
    }

    @Test
    @Transactional
    void createDienstverband() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Dienstverband
        var returnedDienstverband = om.readValue(
            restDienstverbandMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dienstverband)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Dienstverband.class
        );

        // Validate the Dienstverband in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDienstverbandUpdatableFieldsEquals(returnedDienstverband, getPersistedDienstverband(returnedDienstverband));
    }

    @Test
    @Transactional
    void createDienstverbandWithExistingId() throws Exception {
        // Create the Dienstverband with an existing ID
        dienstverband.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDienstverbandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dienstverband)))
            .andExpect(status().isBadRequest());

        // Validate the Dienstverband in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDienstverbands() throws Exception {
        // Initialize the database
        dienstverbandRepository.saveAndFlush(dienstverband);

        // Get all the dienstverbandList
        restDienstverbandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dienstverband.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].periodiek").value(hasItem(DEFAULT_PERIODIEK)))
            .andExpect(jsonPath("$.[*].salaris").value(hasItem(sameNumber(DEFAULT_SALARIS))))
            .andExpect(jsonPath("$.[*].schaal").value(hasItem(DEFAULT_SCHAAL)))
            .andExpect(jsonPath("$.[*].urenperweek").value(hasItem(DEFAULT_URENPERWEEK)));
    }

    @Test
    @Transactional
    void getDienstverband() throws Exception {
        // Initialize the database
        dienstverbandRepository.saveAndFlush(dienstverband);

        // Get the dienstverband
        restDienstverbandMockMvc
            .perform(get(ENTITY_API_URL_ID, dienstverband.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dienstverband.getId().intValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.periodiek").value(DEFAULT_PERIODIEK))
            .andExpect(jsonPath("$.salaris").value(sameNumber(DEFAULT_SALARIS)))
            .andExpect(jsonPath("$.schaal").value(DEFAULT_SCHAAL))
            .andExpect(jsonPath("$.urenperweek").value(DEFAULT_URENPERWEEK));
    }

    @Test
    @Transactional
    void getNonExistingDienstverband() throws Exception {
        // Get the dienstverband
        restDienstverbandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDienstverband() throws Exception {
        // Initialize the database
        dienstverbandRepository.saveAndFlush(dienstverband);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dienstverband
        Dienstverband updatedDienstverband = dienstverbandRepository.findById(dienstverband.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDienstverband are not directly saved in db
        em.detach(updatedDienstverband);
        updatedDienstverband
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .periodiek(UPDATED_PERIODIEK)
            .salaris(UPDATED_SALARIS)
            .schaal(UPDATED_SCHAAL)
            .urenperweek(UPDATED_URENPERWEEK);

        restDienstverbandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDienstverband.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDienstverband))
            )
            .andExpect(status().isOk());

        // Validate the Dienstverband in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDienstverbandToMatchAllProperties(updatedDienstverband);
    }

    @Test
    @Transactional
    void putNonExistingDienstverband() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dienstverband.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDienstverbandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dienstverband.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dienstverband))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dienstverband in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDienstverband() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dienstverband.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDienstverbandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dienstverband))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dienstverband in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDienstverband() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dienstverband.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDienstverbandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dienstverband)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dienstverband in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDienstverbandWithPatch() throws Exception {
        // Initialize the database
        dienstverbandRepository.saveAndFlush(dienstverband);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dienstverband using partial update
        Dienstverband partialUpdatedDienstverband = new Dienstverband();
        partialUpdatedDienstverband.setId(dienstverband.getId());

        partialUpdatedDienstverband
            .periodiek(UPDATED_PERIODIEK)
            .salaris(UPDATED_SALARIS)
            .schaal(UPDATED_SCHAAL)
            .urenperweek(UPDATED_URENPERWEEK);

        restDienstverbandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDienstverband.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDienstverband))
            )
            .andExpect(status().isOk());

        // Validate the Dienstverband in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDienstverbandUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDienstverband, dienstverband),
            getPersistedDienstverband(dienstverband)
        );
    }

    @Test
    @Transactional
    void fullUpdateDienstverbandWithPatch() throws Exception {
        // Initialize the database
        dienstverbandRepository.saveAndFlush(dienstverband);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dienstverband using partial update
        Dienstverband partialUpdatedDienstverband = new Dienstverband();
        partialUpdatedDienstverband.setId(dienstverband.getId());

        partialUpdatedDienstverband
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .periodiek(UPDATED_PERIODIEK)
            .salaris(UPDATED_SALARIS)
            .schaal(UPDATED_SCHAAL)
            .urenperweek(UPDATED_URENPERWEEK);

        restDienstverbandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDienstverband.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDienstverband))
            )
            .andExpect(status().isOk());

        // Validate the Dienstverband in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDienstverbandUpdatableFieldsEquals(partialUpdatedDienstverband, getPersistedDienstverband(partialUpdatedDienstverband));
    }

    @Test
    @Transactional
    void patchNonExistingDienstverband() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dienstverband.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDienstverbandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dienstverband.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dienstverband))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dienstverband in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDienstverband() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dienstverband.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDienstverbandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dienstverband))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dienstverband in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDienstverband() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dienstverband.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDienstverbandMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(dienstverband)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dienstverband in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDienstverband() throws Exception {
        // Initialize the database
        dienstverbandRepository.saveAndFlush(dienstverband);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the dienstverband
        restDienstverbandMockMvc
            .perform(delete(ENTITY_API_URL_ID, dienstverband.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return dienstverbandRepository.count();
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

    protected Dienstverband getPersistedDienstverband(Dienstverband dienstverband) {
        return dienstverbandRepository.findById(dienstverband.getId()).orElseThrow();
    }

    protected void assertPersistedDienstverbandToMatchAllProperties(Dienstverband expectedDienstverband) {
        assertDienstverbandAllPropertiesEquals(expectedDienstverband, getPersistedDienstverband(expectedDienstverband));
    }

    protected void assertPersistedDienstverbandToMatchUpdatableProperties(Dienstverband expectedDienstverband) {
        assertDienstverbandAllUpdatablePropertiesEquals(expectedDienstverband, getPersistedDienstverband(expectedDienstverband));
    }
}
