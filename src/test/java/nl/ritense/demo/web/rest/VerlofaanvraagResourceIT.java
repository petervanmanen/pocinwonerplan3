package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerlofaanvraagAsserts.*;
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
import nl.ritense.demo.domain.Verlofaanvraag;
import nl.ritense.demo.repository.VerlofaanvraagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerlofaanvraagResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerlofaanvraagResourceIT {

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMTOT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMTOT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SOORTVERLOF = "AAAAAAAAAA";
    private static final String UPDATED_SOORTVERLOF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verlofaanvraags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerlofaanvraagRepository verlofaanvraagRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerlofaanvraagMockMvc;

    private Verlofaanvraag verlofaanvraag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verlofaanvraag createEntity(EntityManager em) {
        Verlofaanvraag verlofaanvraag = new Verlofaanvraag()
            .datumstart(DEFAULT_DATUMSTART)
            .datumtot(DEFAULT_DATUMTOT)
            .soortverlof(DEFAULT_SOORTVERLOF);
        return verlofaanvraag;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verlofaanvraag createUpdatedEntity(EntityManager em) {
        Verlofaanvraag verlofaanvraag = new Verlofaanvraag()
            .datumstart(UPDATED_DATUMSTART)
            .datumtot(UPDATED_DATUMTOT)
            .soortverlof(UPDATED_SOORTVERLOF);
        return verlofaanvraag;
    }

    @BeforeEach
    public void initTest() {
        verlofaanvraag = createEntity(em);
    }

    @Test
    @Transactional
    void createVerlofaanvraag() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verlofaanvraag
        var returnedVerlofaanvraag = om.readValue(
            restVerlofaanvraagMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verlofaanvraag)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verlofaanvraag.class
        );

        // Validate the Verlofaanvraag in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerlofaanvraagUpdatableFieldsEquals(returnedVerlofaanvraag, getPersistedVerlofaanvraag(returnedVerlofaanvraag));
    }

    @Test
    @Transactional
    void createVerlofaanvraagWithExistingId() throws Exception {
        // Create the Verlofaanvraag with an existing ID
        verlofaanvraag.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerlofaanvraagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verlofaanvraag)))
            .andExpect(status().isBadRequest());

        // Validate the Verlofaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerlofaanvraags() throws Exception {
        // Initialize the database
        verlofaanvraagRepository.saveAndFlush(verlofaanvraag);

        // Get all the verlofaanvraagList
        restVerlofaanvraagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verlofaanvraag.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].datumtot").value(hasItem(DEFAULT_DATUMTOT.toString())))
            .andExpect(jsonPath("$.[*].soortverlof").value(hasItem(DEFAULT_SOORTVERLOF)));
    }

    @Test
    @Transactional
    void getVerlofaanvraag() throws Exception {
        // Initialize the database
        verlofaanvraagRepository.saveAndFlush(verlofaanvraag);

        // Get the verlofaanvraag
        restVerlofaanvraagMockMvc
            .perform(get(ENTITY_API_URL_ID, verlofaanvraag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verlofaanvraag.getId().intValue()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.datumtot").value(DEFAULT_DATUMTOT.toString()))
            .andExpect(jsonPath("$.soortverlof").value(DEFAULT_SOORTVERLOF));
    }

    @Test
    @Transactional
    void getNonExistingVerlofaanvraag() throws Exception {
        // Get the verlofaanvraag
        restVerlofaanvraagMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerlofaanvraag() throws Exception {
        // Initialize the database
        verlofaanvraagRepository.saveAndFlush(verlofaanvraag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verlofaanvraag
        Verlofaanvraag updatedVerlofaanvraag = verlofaanvraagRepository.findById(verlofaanvraag.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerlofaanvraag are not directly saved in db
        em.detach(updatedVerlofaanvraag);
        updatedVerlofaanvraag.datumstart(UPDATED_DATUMSTART).datumtot(UPDATED_DATUMTOT).soortverlof(UPDATED_SOORTVERLOF);

        restVerlofaanvraagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerlofaanvraag.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerlofaanvraag))
            )
            .andExpect(status().isOk());

        // Validate the Verlofaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerlofaanvraagToMatchAllProperties(updatedVerlofaanvraag);
    }

    @Test
    @Transactional
    void putNonExistingVerlofaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlofaanvraag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerlofaanvraagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verlofaanvraag.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verlofaanvraag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verlofaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerlofaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlofaanvraag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerlofaanvraagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verlofaanvraag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verlofaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerlofaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlofaanvraag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerlofaanvraagMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verlofaanvraag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verlofaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerlofaanvraagWithPatch() throws Exception {
        // Initialize the database
        verlofaanvraagRepository.saveAndFlush(verlofaanvraag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verlofaanvraag using partial update
        Verlofaanvraag partialUpdatedVerlofaanvraag = new Verlofaanvraag();
        partialUpdatedVerlofaanvraag.setId(verlofaanvraag.getId());

        partialUpdatedVerlofaanvraag.soortverlof(UPDATED_SOORTVERLOF);

        restVerlofaanvraagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerlofaanvraag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerlofaanvraag))
            )
            .andExpect(status().isOk());

        // Validate the Verlofaanvraag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerlofaanvraagUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerlofaanvraag, verlofaanvraag),
            getPersistedVerlofaanvraag(verlofaanvraag)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerlofaanvraagWithPatch() throws Exception {
        // Initialize the database
        verlofaanvraagRepository.saveAndFlush(verlofaanvraag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verlofaanvraag using partial update
        Verlofaanvraag partialUpdatedVerlofaanvraag = new Verlofaanvraag();
        partialUpdatedVerlofaanvraag.setId(verlofaanvraag.getId());

        partialUpdatedVerlofaanvraag.datumstart(UPDATED_DATUMSTART).datumtot(UPDATED_DATUMTOT).soortverlof(UPDATED_SOORTVERLOF);

        restVerlofaanvraagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerlofaanvraag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerlofaanvraag))
            )
            .andExpect(status().isOk());

        // Validate the Verlofaanvraag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerlofaanvraagUpdatableFieldsEquals(partialUpdatedVerlofaanvraag, getPersistedVerlofaanvraag(partialUpdatedVerlofaanvraag));
    }

    @Test
    @Transactional
    void patchNonExistingVerlofaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlofaanvraag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerlofaanvraagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verlofaanvraag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verlofaanvraag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verlofaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerlofaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlofaanvraag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerlofaanvraagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verlofaanvraag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verlofaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerlofaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verlofaanvraag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerlofaanvraagMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verlofaanvraag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verlofaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerlofaanvraag() throws Exception {
        // Initialize the database
        verlofaanvraagRepository.saveAndFlush(verlofaanvraag);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verlofaanvraag
        restVerlofaanvraagMockMvc
            .perform(delete(ENTITY_API_URL_ID, verlofaanvraag.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verlofaanvraagRepository.count();
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

    protected Verlofaanvraag getPersistedVerlofaanvraag(Verlofaanvraag verlofaanvraag) {
        return verlofaanvraagRepository.findById(verlofaanvraag.getId()).orElseThrow();
    }

    protected void assertPersistedVerlofaanvraagToMatchAllProperties(Verlofaanvraag expectedVerlofaanvraag) {
        assertVerlofaanvraagAllPropertiesEquals(expectedVerlofaanvraag, getPersistedVerlofaanvraag(expectedVerlofaanvraag));
    }

    protected void assertPersistedVerlofaanvraagToMatchUpdatableProperties(Verlofaanvraag expectedVerlofaanvraag) {
        assertVerlofaanvraagAllUpdatablePropertiesEquals(expectedVerlofaanvraag, getPersistedVerlofaanvraag(expectedVerlofaanvraag));
    }
}
