package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.CollegelidAsserts.*;
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
import nl.ritense.demo.domain.Collegelid;
import nl.ritense.demo.repository.CollegelidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CollegelidResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CollegelidResourceIT {

    private static final String DEFAULT_ACHTERNAAM = "AAAAAAAAAA";
    private static final String UPDATED_ACHTERNAAM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMAANSTELLING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANSTELLING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMUITTREDING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMUITTREDING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FRACTIE = "AAAAAAAAAA";
    private static final String UPDATED_FRACTIE = "BBBBBBBBBB";

    private static final String DEFAULT_PORTEFEUILLE = "AAAAAAAAAA";
    private static final String UPDATED_PORTEFEUILLE = "BBBBBBBBBB";

    private static final String DEFAULT_TITEL = "AAAAAAAAAA";
    private static final String UPDATED_TITEL = "BBBBBBBBBB";

    private static final String DEFAULT_VOORNAAM = "AAAAAAAAAA";
    private static final String UPDATED_VOORNAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/collegelids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CollegelidRepository collegelidRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCollegelidMockMvc;

    private Collegelid collegelid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collegelid createEntity(EntityManager em) {
        Collegelid collegelid = new Collegelid()
            .achternaam(DEFAULT_ACHTERNAAM)
            .datumaanstelling(DEFAULT_DATUMAANSTELLING)
            .datumuittreding(DEFAULT_DATUMUITTREDING)
            .fractie(DEFAULT_FRACTIE)
            .portefeuille(DEFAULT_PORTEFEUILLE)
            .titel(DEFAULT_TITEL)
            .voornaam(DEFAULT_VOORNAAM);
        return collegelid;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collegelid createUpdatedEntity(EntityManager em) {
        Collegelid collegelid = new Collegelid()
            .achternaam(UPDATED_ACHTERNAAM)
            .datumaanstelling(UPDATED_DATUMAANSTELLING)
            .datumuittreding(UPDATED_DATUMUITTREDING)
            .fractie(UPDATED_FRACTIE)
            .portefeuille(UPDATED_PORTEFEUILLE)
            .titel(UPDATED_TITEL)
            .voornaam(UPDATED_VOORNAAM);
        return collegelid;
    }

    @BeforeEach
    public void initTest() {
        collegelid = createEntity(em);
    }

    @Test
    @Transactional
    void createCollegelid() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Collegelid
        var returnedCollegelid = om.readValue(
            restCollegelidMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(collegelid)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Collegelid.class
        );

        // Validate the Collegelid in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCollegelidUpdatableFieldsEquals(returnedCollegelid, getPersistedCollegelid(returnedCollegelid));
    }

    @Test
    @Transactional
    void createCollegelidWithExistingId() throws Exception {
        // Create the Collegelid with an existing ID
        collegelid.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollegelidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(collegelid)))
            .andExpect(status().isBadRequest());

        // Validate the Collegelid in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCollegelids() throws Exception {
        // Initialize the database
        collegelidRepository.saveAndFlush(collegelid);

        // Get all the collegelidList
        restCollegelidMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collegelid.getId().intValue())))
            .andExpect(jsonPath("$.[*].achternaam").value(hasItem(DEFAULT_ACHTERNAAM)))
            .andExpect(jsonPath("$.[*].datumaanstelling").value(hasItem(DEFAULT_DATUMAANSTELLING.toString())))
            .andExpect(jsonPath("$.[*].datumuittreding").value(hasItem(DEFAULT_DATUMUITTREDING.toString())))
            .andExpect(jsonPath("$.[*].fractie").value(hasItem(DEFAULT_FRACTIE)))
            .andExpect(jsonPath("$.[*].portefeuille").value(hasItem(DEFAULT_PORTEFEUILLE)))
            .andExpect(jsonPath("$.[*].titel").value(hasItem(DEFAULT_TITEL)))
            .andExpect(jsonPath("$.[*].voornaam").value(hasItem(DEFAULT_VOORNAAM)));
    }

    @Test
    @Transactional
    void getCollegelid() throws Exception {
        // Initialize the database
        collegelidRepository.saveAndFlush(collegelid);

        // Get the collegelid
        restCollegelidMockMvc
            .perform(get(ENTITY_API_URL_ID, collegelid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collegelid.getId().intValue()))
            .andExpect(jsonPath("$.achternaam").value(DEFAULT_ACHTERNAAM))
            .andExpect(jsonPath("$.datumaanstelling").value(DEFAULT_DATUMAANSTELLING.toString()))
            .andExpect(jsonPath("$.datumuittreding").value(DEFAULT_DATUMUITTREDING.toString()))
            .andExpect(jsonPath("$.fractie").value(DEFAULT_FRACTIE))
            .andExpect(jsonPath("$.portefeuille").value(DEFAULT_PORTEFEUILLE))
            .andExpect(jsonPath("$.titel").value(DEFAULT_TITEL))
            .andExpect(jsonPath("$.voornaam").value(DEFAULT_VOORNAAM));
    }

    @Test
    @Transactional
    void getNonExistingCollegelid() throws Exception {
        // Get the collegelid
        restCollegelidMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCollegelid() throws Exception {
        // Initialize the database
        collegelidRepository.saveAndFlush(collegelid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the collegelid
        Collegelid updatedCollegelid = collegelidRepository.findById(collegelid.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCollegelid are not directly saved in db
        em.detach(updatedCollegelid);
        updatedCollegelid
            .achternaam(UPDATED_ACHTERNAAM)
            .datumaanstelling(UPDATED_DATUMAANSTELLING)
            .datumuittreding(UPDATED_DATUMUITTREDING)
            .fractie(UPDATED_FRACTIE)
            .portefeuille(UPDATED_PORTEFEUILLE)
            .titel(UPDATED_TITEL)
            .voornaam(UPDATED_VOORNAAM);

        restCollegelidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCollegelid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCollegelid))
            )
            .andExpect(status().isOk());

        // Validate the Collegelid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCollegelidToMatchAllProperties(updatedCollegelid);
    }

    @Test
    @Transactional
    void putNonExistingCollegelid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collegelid.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollegelidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collegelid.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(collegelid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collegelid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCollegelid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collegelid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollegelidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(collegelid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collegelid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCollegelid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collegelid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollegelidMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(collegelid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Collegelid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCollegelidWithPatch() throws Exception {
        // Initialize the database
        collegelidRepository.saveAndFlush(collegelid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the collegelid using partial update
        Collegelid partialUpdatedCollegelid = new Collegelid();
        partialUpdatedCollegelid.setId(collegelid.getId());

        partialUpdatedCollegelid.achternaam(UPDATED_ACHTERNAAM).fractie(UPDATED_FRACTIE).voornaam(UPDATED_VOORNAAM);

        restCollegelidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollegelid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCollegelid))
            )
            .andExpect(status().isOk());

        // Validate the Collegelid in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCollegelidUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCollegelid, collegelid),
            getPersistedCollegelid(collegelid)
        );
    }

    @Test
    @Transactional
    void fullUpdateCollegelidWithPatch() throws Exception {
        // Initialize the database
        collegelidRepository.saveAndFlush(collegelid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the collegelid using partial update
        Collegelid partialUpdatedCollegelid = new Collegelid();
        partialUpdatedCollegelid.setId(collegelid.getId());

        partialUpdatedCollegelid
            .achternaam(UPDATED_ACHTERNAAM)
            .datumaanstelling(UPDATED_DATUMAANSTELLING)
            .datumuittreding(UPDATED_DATUMUITTREDING)
            .fractie(UPDATED_FRACTIE)
            .portefeuille(UPDATED_PORTEFEUILLE)
            .titel(UPDATED_TITEL)
            .voornaam(UPDATED_VOORNAAM);

        restCollegelidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollegelid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCollegelid))
            )
            .andExpect(status().isOk());

        // Validate the Collegelid in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCollegelidUpdatableFieldsEquals(partialUpdatedCollegelid, getPersistedCollegelid(partialUpdatedCollegelid));
    }

    @Test
    @Transactional
    void patchNonExistingCollegelid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collegelid.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollegelidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, collegelid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(collegelid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collegelid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCollegelid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collegelid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollegelidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(collegelid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collegelid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCollegelid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collegelid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollegelidMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(collegelid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Collegelid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCollegelid() throws Exception {
        // Initialize the database
        collegelidRepository.saveAndFlush(collegelid);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the collegelid
        restCollegelidMockMvc
            .perform(delete(ENTITY_API_URL_ID, collegelid.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return collegelidRepository.count();
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

    protected Collegelid getPersistedCollegelid(Collegelid collegelid) {
        return collegelidRepository.findById(collegelid.getId()).orElseThrow();
    }

    protected void assertPersistedCollegelidToMatchAllProperties(Collegelid expectedCollegelid) {
        assertCollegelidAllPropertiesEquals(expectedCollegelid, getPersistedCollegelid(expectedCollegelid));
    }

    protected void assertPersistedCollegelidToMatchUpdatableProperties(Collegelid expectedCollegelid) {
        assertCollegelidAllUpdatablePropertiesEquals(expectedCollegelid, getPersistedCollegelid(expectedCollegelid));
    }
}
