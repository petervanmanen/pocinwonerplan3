package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerkeersbesluitAsserts.*;
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
import nl.ritense.demo.domain.Verkeersbesluit;
import nl.ritense.demo.repository.VerkeersbesluitRepository;
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
 * Integration tests for the {@link VerkeersbesluitResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class VerkeersbesluitResourceIT {

    private static final LocalDate DEFAULT_DATUMBESLUIT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBESLUIT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DATUMEINDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMSTART = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSTART = "BBBBBBBBBB";

    private static final String DEFAULT_HUISNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_HUISNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_POSTCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENTIENUMMER = "AAAAAAAAAA";
    private static final String UPDATED_REFERENTIENUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_STRAAT = "AAAAAAAAAA";
    private static final String UPDATED_STRAAT = "BBBBBBBBBB";

    private static final String DEFAULT_TITEL = "AAAAAAAAAA";
    private static final String UPDATED_TITEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verkeersbesluits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerkeersbesluitRepository verkeersbesluitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerkeersbesluitMockMvc;

    private Verkeersbesluit verkeersbesluit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verkeersbesluit createEntity(EntityManager em) {
        Verkeersbesluit verkeersbesluit = new Verkeersbesluit()
            .datumbesluit(DEFAULT_DATUMBESLUIT)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .huisnummer(DEFAULT_HUISNUMMER)
            .postcode(DEFAULT_POSTCODE)
            .referentienummer(DEFAULT_REFERENTIENUMMER)
            .straat(DEFAULT_STRAAT)
            .titel(DEFAULT_TITEL);
        return verkeersbesluit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verkeersbesluit createUpdatedEntity(EntityManager em) {
        Verkeersbesluit verkeersbesluit = new Verkeersbesluit()
            .datumbesluit(UPDATED_DATUMBESLUIT)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .huisnummer(UPDATED_HUISNUMMER)
            .postcode(UPDATED_POSTCODE)
            .referentienummer(UPDATED_REFERENTIENUMMER)
            .straat(UPDATED_STRAAT)
            .titel(UPDATED_TITEL);
        return verkeersbesluit;
    }

    @BeforeEach
    public void initTest() {
        verkeersbesluit = createEntity(em);
    }

    @Test
    @Transactional
    void createVerkeersbesluit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verkeersbesluit
        var returnedVerkeersbesluit = om.readValue(
            restVerkeersbesluitMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verkeersbesluit)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verkeersbesluit.class
        );

        // Validate the Verkeersbesluit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerkeersbesluitUpdatableFieldsEquals(returnedVerkeersbesluit, getPersistedVerkeersbesluit(returnedVerkeersbesluit));
    }

    @Test
    @Transactional
    void createVerkeersbesluitWithExistingId() throws Exception {
        // Create the Verkeersbesluit with an existing ID
        verkeersbesluit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerkeersbesluitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verkeersbesluit)))
            .andExpect(status().isBadRequest());

        // Validate the Verkeersbesluit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerkeersbesluits() throws Exception {
        // Initialize the database
        verkeersbesluitRepository.saveAndFlush(verkeersbesluit);

        // Get all the verkeersbesluitList
        restVerkeersbesluitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verkeersbesluit.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbesluit").value(hasItem(DEFAULT_DATUMBESLUIT.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE)))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART)))
            .andExpect(jsonPath("$.[*].huisnummer").value(hasItem(DEFAULT_HUISNUMMER)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].referentienummer").value(hasItem(DEFAULT_REFERENTIENUMMER)))
            .andExpect(jsonPath("$.[*].straat").value(hasItem(DEFAULT_STRAAT)))
            .andExpect(jsonPath("$.[*].titel").value(hasItem(DEFAULT_TITEL)));
    }

    @Test
    @Transactional
    void getVerkeersbesluit() throws Exception {
        // Initialize the database
        verkeersbesluitRepository.saveAndFlush(verkeersbesluit);

        // Get the verkeersbesluit
        restVerkeersbesluitMockMvc
            .perform(get(ENTITY_API_URL_ID, verkeersbesluit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verkeersbesluit.getId().intValue()))
            .andExpect(jsonPath("$.datumbesluit").value(DEFAULT_DATUMBESLUIT.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART))
            .andExpect(jsonPath("$.huisnummer").value(DEFAULT_HUISNUMMER))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE))
            .andExpect(jsonPath("$.referentienummer").value(DEFAULT_REFERENTIENUMMER))
            .andExpect(jsonPath("$.straat").value(DEFAULT_STRAAT))
            .andExpect(jsonPath("$.titel").value(DEFAULT_TITEL));
    }

    @Test
    @Transactional
    void getNonExistingVerkeersbesluit() throws Exception {
        // Get the verkeersbesluit
        restVerkeersbesluitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerkeersbesluit() throws Exception {
        // Initialize the database
        verkeersbesluitRepository.saveAndFlush(verkeersbesluit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verkeersbesluit
        Verkeersbesluit updatedVerkeersbesluit = verkeersbesluitRepository.findById(verkeersbesluit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerkeersbesluit are not directly saved in db
        em.detach(updatedVerkeersbesluit);
        updatedVerkeersbesluit
            .datumbesluit(UPDATED_DATUMBESLUIT)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .huisnummer(UPDATED_HUISNUMMER)
            .postcode(UPDATED_POSTCODE)
            .referentienummer(UPDATED_REFERENTIENUMMER)
            .straat(UPDATED_STRAAT)
            .titel(UPDATED_TITEL);

        restVerkeersbesluitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerkeersbesluit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerkeersbesluit))
            )
            .andExpect(status().isOk());

        // Validate the Verkeersbesluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerkeersbesluitToMatchAllProperties(updatedVerkeersbesluit);
    }

    @Test
    @Transactional
    void putNonExistingVerkeersbesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeersbesluit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerkeersbesluitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verkeersbesluit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verkeersbesluit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verkeersbesluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerkeersbesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeersbesluit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerkeersbesluitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verkeersbesluit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verkeersbesluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerkeersbesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeersbesluit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerkeersbesluitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verkeersbesluit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verkeersbesluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerkeersbesluitWithPatch() throws Exception {
        // Initialize the database
        verkeersbesluitRepository.saveAndFlush(verkeersbesluit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verkeersbesluit using partial update
        Verkeersbesluit partialUpdatedVerkeersbesluit = new Verkeersbesluit();
        partialUpdatedVerkeersbesluit.setId(verkeersbesluit.getId());

        partialUpdatedVerkeersbesluit.straat(UPDATED_STRAAT);

        restVerkeersbesluitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerkeersbesluit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerkeersbesluit))
            )
            .andExpect(status().isOk());

        // Validate the Verkeersbesluit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerkeersbesluitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerkeersbesluit, verkeersbesluit),
            getPersistedVerkeersbesluit(verkeersbesluit)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerkeersbesluitWithPatch() throws Exception {
        // Initialize the database
        verkeersbesluitRepository.saveAndFlush(verkeersbesluit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verkeersbesluit using partial update
        Verkeersbesluit partialUpdatedVerkeersbesluit = new Verkeersbesluit();
        partialUpdatedVerkeersbesluit.setId(verkeersbesluit.getId());

        partialUpdatedVerkeersbesluit
            .datumbesluit(UPDATED_DATUMBESLUIT)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .huisnummer(UPDATED_HUISNUMMER)
            .postcode(UPDATED_POSTCODE)
            .referentienummer(UPDATED_REFERENTIENUMMER)
            .straat(UPDATED_STRAAT)
            .titel(UPDATED_TITEL);

        restVerkeersbesluitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerkeersbesluit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerkeersbesluit))
            )
            .andExpect(status().isOk());

        // Validate the Verkeersbesluit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerkeersbesluitUpdatableFieldsEquals(
            partialUpdatedVerkeersbesluit,
            getPersistedVerkeersbesluit(partialUpdatedVerkeersbesluit)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVerkeersbesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeersbesluit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerkeersbesluitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verkeersbesluit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verkeersbesluit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verkeersbesluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerkeersbesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeersbesluit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerkeersbesluitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verkeersbesluit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verkeersbesluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerkeersbesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeersbesluit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerkeersbesluitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verkeersbesluit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verkeersbesluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerkeersbesluit() throws Exception {
        // Initialize the database
        verkeersbesluitRepository.saveAndFlush(verkeersbesluit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verkeersbesluit
        restVerkeersbesluitMockMvc
            .perform(delete(ENTITY_API_URL_ID, verkeersbesluit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verkeersbesluitRepository.count();
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

    protected Verkeersbesluit getPersistedVerkeersbesluit(Verkeersbesluit verkeersbesluit) {
        return verkeersbesluitRepository.findById(verkeersbesluit.getId()).orElseThrow();
    }

    protected void assertPersistedVerkeersbesluitToMatchAllProperties(Verkeersbesluit expectedVerkeersbesluit) {
        assertVerkeersbesluitAllPropertiesEquals(expectedVerkeersbesluit, getPersistedVerkeersbesluit(expectedVerkeersbesluit));
    }

    protected void assertPersistedVerkeersbesluitToMatchUpdatableProperties(Verkeersbesluit expectedVerkeersbesluit) {
        assertVerkeersbesluitAllUpdatablePropertiesEquals(expectedVerkeersbesluit, getPersistedVerkeersbesluit(expectedVerkeersbesluit));
    }
}
