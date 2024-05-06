package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerlofAsserts.*;
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
import nl.ritense.demo.domain.Verlof;
import nl.ritense.demo.repository.VerlofRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerlofResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerlofResourceIT {

    private static final LocalDate DEFAULT_DATUMAANVRAAG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANVRAAG = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DATUMTIJDEINDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMTIJDEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMTIJDSTART = "AAAAAAAAAA";
    private static final String UPDATED_DATUMTIJDSTART = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMTOEKENNING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMTOEKENNING = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_GOEDGEKEURD = false;
    private static final Boolean UPDATED_GOEDGEKEURD = true;

    private static final String ENTITY_API_URL = "/api/verlofs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerlofRepository verlofRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerlofMockMvc;

    private Verlof verlof;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verlof createEntity(EntityManager em) {
        Verlof verlof = new Verlof()
            .datumaanvraag(DEFAULT_DATUMAANVRAAG)
            .datumtijdeinde(DEFAULT_DATUMTIJDEINDE)
            .datumtijdstart(DEFAULT_DATUMTIJDSTART)
            .datumtoekenning(DEFAULT_DATUMTOEKENNING)
            .goedgekeurd(DEFAULT_GOEDGEKEURD);
        return verlof;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verlof createUpdatedEntity(EntityManager em) {
        Verlof verlof = new Verlof()
            .datumaanvraag(UPDATED_DATUMAANVRAAG)
            .datumtijdeinde(UPDATED_DATUMTIJDEINDE)
            .datumtijdstart(UPDATED_DATUMTIJDSTART)
            .datumtoekenning(UPDATED_DATUMTOEKENNING)
            .goedgekeurd(UPDATED_GOEDGEKEURD);
        return verlof;
    }

    @BeforeEach
    public void initTest() {
        verlof = createEntity(em);
    }

    @Test
    @Transactional
    void createVerlof() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verlof
        var returnedVerlof = om.readValue(
            restVerlofMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verlof)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verlof.class
        );

        // Validate the Verlof in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerlofUpdatableFieldsEquals(returnedVerlof, getPersistedVerlof(returnedVerlof));
    }

    @Test
    @Transactional
    void createVerlofWithExistingId() throws Exception {
        // Create the Verlof with an existing ID
        verlof.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerlofMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verlof)))
            .andExpect(status().isBadRequest());

        // Validate the Verlof in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerlofs() throws Exception {
        // Initialize the database
        verlofRepository.saveAndFlush(verlof);

        // Get all the verlofList
        restVerlofMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verlof.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumaanvraag").value(hasItem(DEFAULT_DATUMAANVRAAG.toString())))
            .andExpect(jsonPath("$.[*].datumtijdeinde").value(hasItem(DEFAULT_DATUMTIJDEINDE)))
            .andExpect(jsonPath("$.[*].datumtijdstart").value(hasItem(DEFAULT_DATUMTIJDSTART)))
            .andExpect(jsonPath("$.[*].datumtoekenning").value(hasItem(DEFAULT_DATUMTOEKENNING.toString())))
            .andExpect(jsonPath("$.[*].goedgekeurd").value(hasItem(DEFAULT_GOEDGEKEURD.booleanValue())));
    }

    @Test
    @Transactional
    void getVerlof() throws Exception {
        // Initialize the database
        verlofRepository.saveAndFlush(verlof);

        // Get the verlof
        restVerlofMockMvc
            .perform(get(ENTITY_API_URL_ID, verlof.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verlof.getId().intValue()))
            .andExpect(jsonPath("$.datumaanvraag").value(DEFAULT_DATUMAANVRAAG.toString()))
            .andExpect(jsonPath("$.datumtijdeinde").value(DEFAULT_DATUMTIJDEINDE))
            .andExpect(jsonPath("$.datumtijdstart").value(DEFAULT_DATUMTIJDSTART))
            .andExpect(jsonPath("$.datumtoekenning").value(DEFAULT_DATUMTOEKENNING.toString()))
            .andExpect(jsonPath("$.goedgekeurd").value(DEFAULT_GOEDGEKEURD.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingVerlof() throws Exception {
        // Get the verlof
        restVerlofMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerlof() throws Exception {
        // Initialize the database
        verlofRepository.saveAndFlush(verlof);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verlof
        Verlof updatedVerlof = verlofRepository.findById(verlof.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerlof are not directly saved in db
        em.detach(updatedVerlof);
        updatedVerlof
            .datumaanvraag(UPDATED_DATUMAANVRAAG)
            .datumtijdeinde(UPDATED_DATUMTIJDEINDE)
            .datumtijdstart(UPDATED_DATUMTIJDSTART)
            .datumtoekenning(UPDATED_DATUMTOEKENNING)
            .goedgekeurd(UPDATED_GOEDGEKEURD);

        restVerlofMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerlof.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerlof))
            )
            .andExpect(status().isOk());

        // Validate the Verlof in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerlofToMatchAllProperties(updatedVerlof);
    }

    @Test
    @Transactional
    void putNonExistingVerlof() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlof.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerlofMockMvc
            .perform(put(ENTITY_API_URL_ID, verlof.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verlof)))
            .andExpect(status().isBadRequest());

        // Validate the Verlof in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerlof() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlof.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerlofMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verlof))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verlof in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerlof() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlof.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerlofMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verlof)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verlof in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerlofWithPatch() throws Exception {
        // Initialize the database
        verlofRepository.saveAndFlush(verlof);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verlof using partial update
        Verlof partialUpdatedVerlof = new Verlof();
        partialUpdatedVerlof.setId(verlof.getId());

        partialUpdatedVerlof
            .datumaanvraag(UPDATED_DATUMAANVRAAG)
            .datumtijdeinde(UPDATED_DATUMTIJDEINDE)
            .datumtijdstart(UPDATED_DATUMTIJDSTART)
            .datumtoekenning(UPDATED_DATUMTOEKENNING)
            .goedgekeurd(UPDATED_GOEDGEKEURD);

        restVerlofMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerlof.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerlof))
            )
            .andExpect(status().isOk());

        // Validate the Verlof in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerlofUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVerlof, verlof), getPersistedVerlof(verlof));
    }

    @Test
    @Transactional
    void fullUpdateVerlofWithPatch() throws Exception {
        // Initialize the database
        verlofRepository.saveAndFlush(verlof);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verlof using partial update
        Verlof partialUpdatedVerlof = new Verlof();
        partialUpdatedVerlof.setId(verlof.getId());

        partialUpdatedVerlof
            .datumaanvraag(UPDATED_DATUMAANVRAAG)
            .datumtijdeinde(UPDATED_DATUMTIJDEINDE)
            .datumtijdstart(UPDATED_DATUMTIJDSTART)
            .datumtoekenning(UPDATED_DATUMTOEKENNING)
            .goedgekeurd(UPDATED_GOEDGEKEURD);

        restVerlofMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerlof.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerlof))
            )
            .andExpect(status().isOk());

        // Validate the Verlof in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerlofUpdatableFieldsEquals(partialUpdatedVerlof, getPersistedVerlof(partialUpdatedVerlof));
    }

    @Test
    @Transactional
    void patchNonExistingVerlof() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlof.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerlofMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verlof.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verlof))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verlof in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerlof() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlof.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerlofMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verlof))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verlof in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerlof() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlof.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerlofMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verlof)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verlof in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerlof() throws Exception {
        // Initialize the database
        verlofRepository.saveAndFlush(verlof);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verlof
        restVerlofMockMvc
            .perform(delete(ENTITY_API_URL_ID, verlof.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verlofRepository.count();
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

    protected Verlof getPersistedVerlof(Verlof verlof) {
        return verlofRepository.findById(verlof.getId()).orElseThrow();
    }

    protected void assertPersistedVerlofToMatchAllProperties(Verlof expectedVerlof) {
        assertVerlofAllPropertiesEquals(expectedVerlof, getPersistedVerlof(expectedVerlof));
    }

    protected void assertPersistedVerlofToMatchUpdatableProperties(Verlof expectedVerlof) {
        assertVerlofAllUpdatablePropertiesEquals(expectedVerlof, getPersistedVerlof(expectedVerlof));
    }
}
