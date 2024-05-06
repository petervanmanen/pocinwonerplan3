package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PeriodeAsserts.*;
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
import nl.ritense.demo.domain.Archief;
import nl.ritense.demo.domain.Archiefstuk;
import nl.ritense.demo.domain.Periode;
import nl.ritense.demo.repository.PeriodeRepository;
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
 * Integration tests for the {@link PeriodeResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class PeriodeResourceIT {

    private static final String DEFAULT_DATUMEINDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMSTART = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSTART = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/periodes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PeriodeRepository periodeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPeriodeMockMvc;

    private Periode periode;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Periode createEntity(EntityManager em) {
        Periode periode = new Periode().datumeinde(DEFAULT_DATUMEINDE).datumstart(DEFAULT_DATUMSTART).omschrijving(DEFAULT_OMSCHRIJVING);
        // Add required entity
        Archief archief;
        if (TestUtil.findAll(em, Archief.class).isEmpty()) {
            archief = ArchiefResourceIT.createEntity(em);
            em.persist(archief);
            em.flush();
        } else {
            archief = TestUtil.findAll(em, Archief.class).get(0);
        }
        periode.getStamtuitArchiefs().add(archief);
        // Add required entity
        Archiefstuk archiefstuk;
        if (TestUtil.findAll(em, Archiefstuk.class).isEmpty()) {
            archiefstuk = ArchiefstukResourceIT.createEntity(em);
            em.persist(archiefstuk);
            em.flush();
        } else {
            archiefstuk = TestUtil.findAll(em, Archiefstuk.class).get(0);
        }
        periode.getStamtuitArchiefstuks().add(archiefstuk);
        return periode;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Periode createUpdatedEntity(EntityManager em) {
        Periode periode = new Periode().datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART).omschrijving(UPDATED_OMSCHRIJVING);
        // Add required entity
        Archief archief;
        if (TestUtil.findAll(em, Archief.class).isEmpty()) {
            archief = ArchiefResourceIT.createUpdatedEntity(em);
            em.persist(archief);
            em.flush();
        } else {
            archief = TestUtil.findAll(em, Archief.class).get(0);
        }
        periode.getStamtuitArchiefs().add(archief);
        // Add required entity
        Archiefstuk archiefstuk;
        if (TestUtil.findAll(em, Archiefstuk.class).isEmpty()) {
            archiefstuk = ArchiefstukResourceIT.createUpdatedEntity(em);
            em.persist(archiefstuk);
            em.flush();
        } else {
            archiefstuk = TestUtil.findAll(em, Archiefstuk.class).get(0);
        }
        periode.getStamtuitArchiefstuks().add(archiefstuk);
        return periode;
    }

    @BeforeEach
    public void initTest() {
        periode = createEntity(em);
    }

    @Test
    @Transactional
    void createPeriode() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Periode
        var returnedPeriode = om.readValue(
            restPeriodeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(periode)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Periode.class
        );

        // Validate the Periode in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPeriodeUpdatableFieldsEquals(returnedPeriode, getPersistedPeriode(returnedPeriode));
    }

    @Test
    @Transactional
    void createPeriodeWithExistingId() throws Exception {
        // Create the Periode with an existing ID
        periode.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(periode)))
            .andExpect(status().isBadRequest());

        // Validate the Periode in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPeriodes() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        // Get all the periodeList
        restPeriodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periode.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE)))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getPeriode() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        // Get the periode
        restPeriodeMockMvc
            .perform(get(ENTITY_API_URL_ID, periode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(periode.getId().intValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingPeriode() throws Exception {
        // Get the periode
        restPeriodeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPeriode() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the periode
        Periode updatedPeriode = periodeRepository.findById(periode.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPeriode are not directly saved in db
        em.detach(updatedPeriode);
        updatedPeriode.datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART).omschrijving(UPDATED_OMSCHRIJVING);

        restPeriodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPeriode.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPeriode))
            )
            .andExpect(status().isOk());

        // Validate the Periode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPeriodeToMatchAllProperties(updatedPeriode);
    }

    @Test
    @Transactional
    void putNonExistingPeriode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        periode.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodeMockMvc
            .perform(put(ENTITY_API_URL_ID, periode.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(periode)))
            .andExpect(status().isBadRequest());

        // Validate the Periode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPeriode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        periode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(periode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Periode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPeriode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        periode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(periode)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Periode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePeriodeWithPatch() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the periode using partial update
        Periode partialUpdatedPeriode = new Periode();
        partialUpdatedPeriode.setId(periode.getId());

        partialUpdatedPeriode.datumstart(UPDATED_DATUMSTART);

        restPeriodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPeriode))
            )
            .andExpect(status().isOk());

        // Validate the Periode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPeriodeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPeriode, periode), getPersistedPeriode(periode));
    }

    @Test
    @Transactional
    void fullUpdatePeriodeWithPatch() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the periode using partial update
        Periode partialUpdatedPeriode = new Periode();
        partialUpdatedPeriode.setId(periode.getId());

        partialUpdatedPeriode.datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART).omschrijving(UPDATED_OMSCHRIJVING);

        restPeriodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPeriode))
            )
            .andExpect(status().isOk());

        // Validate the Periode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPeriodeUpdatableFieldsEquals(partialUpdatedPeriode, getPersistedPeriode(partialUpdatedPeriode));
    }

    @Test
    @Transactional
    void patchNonExistingPeriode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        periode.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, periode.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(periode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Periode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPeriode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        periode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(periode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Periode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPeriode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        periode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(periode)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Periode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePeriode() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the periode
        restPeriodeMockMvc
            .perform(delete(ENTITY_API_URL_ID, periode.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return periodeRepository.count();
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

    protected Periode getPersistedPeriode(Periode periode) {
        return periodeRepository.findById(periode.getId()).orElseThrow();
    }

    protected void assertPersistedPeriodeToMatchAllProperties(Periode expectedPeriode) {
        assertPeriodeAllPropertiesEquals(expectedPeriode, getPersistedPeriode(expectedPeriode));
    }

    protected void assertPersistedPeriodeToMatchUpdatableProperties(Periode expectedPeriode) {
        assertPeriodeAllUpdatablePropertiesEquals(expectedPeriode, getPersistedPeriode(expectedPeriode));
    }
}
