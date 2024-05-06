package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PrijsafspraakAsserts.*;
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
import nl.ritense.demo.domain.Prijsafspraak;
import nl.ritense.demo.repository.PrijsafspraakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PrijsafspraakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrijsafspraakResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TITEL = "AAAAAAAAAA";
    private static final String UPDATED_TITEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/prijsafspraaks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PrijsafspraakRepository prijsafspraakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrijsafspraakMockMvc;

    private Prijsafspraak prijsafspraak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prijsafspraak createEntity(EntityManager em) {
        Prijsafspraak prijsafspraak = new Prijsafspraak()
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .titel(DEFAULT_TITEL);
        return prijsafspraak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prijsafspraak createUpdatedEntity(EntityManager em) {
        Prijsafspraak prijsafspraak = new Prijsafspraak()
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .titel(UPDATED_TITEL);
        return prijsafspraak;
    }

    @BeforeEach
    public void initTest() {
        prijsafspraak = createEntity(em);
    }

    @Test
    @Transactional
    void createPrijsafspraak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Prijsafspraak
        var returnedPrijsafspraak = om.readValue(
            restPrijsafspraakMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prijsafspraak)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Prijsafspraak.class
        );

        // Validate the Prijsafspraak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPrijsafspraakUpdatableFieldsEquals(returnedPrijsafspraak, getPersistedPrijsafspraak(returnedPrijsafspraak));
    }

    @Test
    @Transactional
    void createPrijsafspraakWithExistingId() throws Exception {
        // Create the Prijsafspraak with an existing ID
        prijsafspraak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrijsafspraakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prijsafspraak)))
            .andExpect(status().isBadRequest());

        // Validate the Prijsafspraak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrijsafspraaks() throws Exception {
        // Initialize the database
        prijsafspraakRepository.saveAndFlush(prijsafspraak);

        // Get all the prijsafspraakList
        restPrijsafspraakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prijsafspraak.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].titel").value(hasItem(DEFAULT_TITEL)));
    }

    @Test
    @Transactional
    void getPrijsafspraak() throws Exception {
        // Initialize the database
        prijsafspraakRepository.saveAndFlush(prijsafspraak);

        // Get the prijsafspraak
        restPrijsafspraakMockMvc
            .perform(get(ENTITY_API_URL_ID, prijsafspraak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prijsafspraak.getId().intValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.titel").value(DEFAULT_TITEL));
    }

    @Test
    @Transactional
    void getNonExistingPrijsafspraak() throws Exception {
        // Get the prijsafspraak
        restPrijsafspraakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrijsafspraak() throws Exception {
        // Initialize the database
        prijsafspraakRepository.saveAndFlush(prijsafspraak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prijsafspraak
        Prijsafspraak updatedPrijsafspraak = prijsafspraakRepository.findById(prijsafspraak.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPrijsafspraak are not directly saved in db
        em.detach(updatedPrijsafspraak);
        updatedPrijsafspraak.datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART).titel(UPDATED_TITEL);

        restPrijsafspraakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrijsafspraak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPrijsafspraak))
            )
            .andExpect(status().isOk());

        // Validate the Prijsafspraak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPrijsafspraakToMatchAllProperties(updatedPrijsafspraak);
    }

    @Test
    @Transactional
    void putNonExistingPrijsafspraak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijsafspraak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrijsafspraakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prijsafspraak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prijsafspraak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijsafspraak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrijsafspraak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijsafspraak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijsafspraakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prijsafspraak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijsafspraak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrijsafspraak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijsafspraak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijsafspraakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prijsafspraak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prijsafspraak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrijsafspraakWithPatch() throws Exception {
        // Initialize the database
        prijsafspraakRepository.saveAndFlush(prijsafspraak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prijsafspraak using partial update
        Prijsafspraak partialUpdatedPrijsafspraak = new Prijsafspraak();
        partialUpdatedPrijsafspraak.setId(prijsafspraak.getId());

        restPrijsafspraakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrijsafspraak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrijsafspraak))
            )
            .andExpect(status().isOk());

        // Validate the Prijsafspraak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPrijsafspraakUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPrijsafspraak, prijsafspraak),
            getPersistedPrijsafspraak(prijsafspraak)
        );
    }

    @Test
    @Transactional
    void fullUpdatePrijsafspraakWithPatch() throws Exception {
        // Initialize the database
        prijsafspraakRepository.saveAndFlush(prijsafspraak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prijsafspraak using partial update
        Prijsafspraak partialUpdatedPrijsafspraak = new Prijsafspraak();
        partialUpdatedPrijsafspraak.setId(prijsafspraak.getId());

        partialUpdatedPrijsafspraak.datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART).titel(UPDATED_TITEL);

        restPrijsafspraakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrijsafspraak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrijsafspraak))
            )
            .andExpect(status().isOk());

        // Validate the Prijsafspraak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPrijsafspraakUpdatableFieldsEquals(partialUpdatedPrijsafspraak, getPersistedPrijsafspraak(partialUpdatedPrijsafspraak));
    }

    @Test
    @Transactional
    void patchNonExistingPrijsafspraak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijsafspraak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrijsafspraakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prijsafspraak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prijsafspraak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijsafspraak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrijsafspraak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijsafspraak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijsafspraakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prijsafspraak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijsafspraak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrijsafspraak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijsafspraak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijsafspraakMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(prijsafspraak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prijsafspraak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrijsafspraak() throws Exception {
        // Initialize the database
        prijsafspraakRepository.saveAndFlush(prijsafspraak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the prijsafspraak
        restPrijsafspraakMockMvc
            .perform(delete(ENTITY_API_URL_ID, prijsafspraak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return prijsafspraakRepository.count();
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

    protected Prijsafspraak getPersistedPrijsafspraak(Prijsafspraak prijsafspraak) {
        return prijsafspraakRepository.findById(prijsafspraak.getId()).orElseThrow();
    }

    protected void assertPersistedPrijsafspraakToMatchAllProperties(Prijsafspraak expectedPrijsafspraak) {
        assertPrijsafspraakAllPropertiesEquals(expectedPrijsafspraak, getPersistedPrijsafspraak(expectedPrijsafspraak));
    }

    protected void assertPersistedPrijsafspraakToMatchUpdatableProperties(Prijsafspraak expectedPrijsafspraak) {
        assertPrijsafspraakAllUpdatablePropertiesEquals(expectedPrijsafspraak, getPersistedPrijsafspraak(expectedPrijsafspraak));
    }
}
