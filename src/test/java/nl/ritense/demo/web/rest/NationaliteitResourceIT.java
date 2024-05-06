package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NationaliteitAsserts.*;
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
import nl.ritense.demo.domain.Nationaliteit;
import nl.ritense.demo.repository.NationaliteitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NationaliteitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NationaliteitResourceIT {

    private static final Boolean DEFAULT_BUITENLANDSENATIONALITEIT = false;
    private static final Boolean UPDATED_BUITENLANDSENATIONALITEIT = true;

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINGANGGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINGANGGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DATUMOPNAMEN = "AAAAAAAAAA";
    private static final String UPDATED_DATUMOPNAMEN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMVERLIESNATIONALITEIT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMVERLIESNATIONALITEIT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NATIONALITEIT = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITEITCODE = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITEITCODE = "BBBBBBBBBB";

    private static final String DEFAULT_REDENVERKRIJGINGNEDERLANDSENATIONALITEIT = "AAAAAAAAAA";
    private static final String UPDATED_REDENVERKRIJGINGNEDERLANDSENATIONALITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_REDENVERLIESNEDERLANDSENATIONALITEIT = "AAAAAAAAAA";
    private static final String UPDATED_REDENVERLIESNEDERLANDSENATIONALITEIT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nationaliteits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NationaliteitRepository nationaliteitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNationaliteitMockMvc;

    private Nationaliteit nationaliteit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nationaliteit createEntity(EntityManager em) {
        Nationaliteit nationaliteit = new Nationaliteit()
            .buitenlandsenationaliteit(DEFAULT_BUITENLANDSENATIONALITEIT)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .datuminganggeldigheid(DEFAULT_DATUMINGANGGELDIGHEID)
            .datumopnamen(DEFAULT_DATUMOPNAMEN)
            .datumverliesnationaliteit(DEFAULT_DATUMVERLIESNATIONALITEIT)
            .nationaliteit(DEFAULT_NATIONALITEIT)
            .nationaliteitcode(DEFAULT_NATIONALITEITCODE)
            .redenverkrijgingnederlandsenationaliteit(DEFAULT_REDENVERKRIJGINGNEDERLANDSENATIONALITEIT)
            .redenverliesnederlandsenationaliteit(DEFAULT_REDENVERLIESNEDERLANDSENATIONALITEIT);
        return nationaliteit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nationaliteit createUpdatedEntity(EntityManager em) {
        Nationaliteit nationaliteit = new Nationaliteit()
            .buitenlandsenationaliteit(UPDATED_BUITENLANDSENATIONALITEIT)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datuminganggeldigheid(UPDATED_DATUMINGANGGELDIGHEID)
            .datumopnamen(UPDATED_DATUMOPNAMEN)
            .datumverliesnationaliteit(UPDATED_DATUMVERLIESNATIONALITEIT)
            .nationaliteit(UPDATED_NATIONALITEIT)
            .nationaliteitcode(UPDATED_NATIONALITEITCODE)
            .redenverkrijgingnederlandsenationaliteit(UPDATED_REDENVERKRIJGINGNEDERLANDSENATIONALITEIT)
            .redenverliesnederlandsenationaliteit(UPDATED_REDENVERLIESNEDERLANDSENATIONALITEIT);
        return nationaliteit;
    }

    @BeforeEach
    public void initTest() {
        nationaliteit = createEntity(em);
    }

    @Test
    @Transactional
    void createNationaliteit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Nationaliteit
        var returnedNationaliteit = om.readValue(
            restNationaliteitMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nationaliteit)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Nationaliteit.class
        );

        // Validate the Nationaliteit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNationaliteitUpdatableFieldsEquals(returnedNationaliteit, getPersistedNationaliteit(returnedNationaliteit));
    }

    @Test
    @Transactional
    void createNationaliteitWithExistingId() throws Exception {
        // Create the Nationaliteit with an existing ID
        nationaliteit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNationaliteitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nationaliteit)))
            .andExpect(status().isBadRequest());

        // Validate the Nationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNationaliteits() throws Exception {
        // Initialize the database
        nationaliteitRepository.saveAndFlush(nationaliteit);

        // Get all the nationaliteitList
        restNationaliteitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nationaliteit.getId().intValue())))
            .andExpect(jsonPath("$.[*].buitenlandsenationaliteit").value(hasItem(DEFAULT_BUITENLANDSENATIONALITEIT.booleanValue())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datuminganggeldigheid").value(hasItem(DEFAULT_DATUMINGANGGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumopnamen").value(hasItem(DEFAULT_DATUMOPNAMEN)))
            .andExpect(jsonPath("$.[*].datumverliesnationaliteit").value(hasItem(DEFAULT_DATUMVERLIESNATIONALITEIT.toString())))
            .andExpect(jsonPath("$.[*].nationaliteit").value(hasItem(DEFAULT_NATIONALITEIT)))
            .andExpect(jsonPath("$.[*].nationaliteitcode").value(hasItem(DEFAULT_NATIONALITEITCODE)))
            .andExpect(
                jsonPath("$.[*].redenverkrijgingnederlandsenationaliteit").value(hasItem(DEFAULT_REDENVERKRIJGINGNEDERLANDSENATIONALITEIT))
            )
            .andExpect(jsonPath("$.[*].redenverliesnederlandsenationaliteit").value(hasItem(DEFAULT_REDENVERLIESNEDERLANDSENATIONALITEIT)));
    }

    @Test
    @Transactional
    void getNationaliteit() throws Exception {
        // Initialize the database
        nationaliteitRepository.saveAndFlush(nationaliteit);

        // Get the nationaliteit
        restNationaliteitMockMvc
            .perform(get(ENTITY_API_URL_ID, nationaliteit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nationaliteit.getId().intValue()))
            .andExpect(jsonPath("$.buitenlandsenationaliteit").value(DEFAULT_BUITENLANDSENATIONALITEIT.booleanValue()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datuminganggeldigheid").value(DEFAULT_DATUMINGANGGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumopnamen").value(DEFAULT_DATUMOPNAMEN))
            .andExpect(jsonPath("$.datumverliesnationaliteit").value(DEFAULT_DATUMVERLIESNATIONALITEIT.toString()))
            .andExpect(jsonPath("$.nationaliteit").value(DEFAULT_NATIONALITEIT))
            .andExpect(jsonPath("$.nationaliteitcode").value(DEFAULT_NATIONALITEITCODE))
            .andExpect(jsonPath("$.redenverkrijgingnederlandsenationaliteit").value(DEFAULT_REDENVERKRIJGINGNEDERLANDSENATIONALITEIT))
            .andExpect(jsonPath("$.redenverliesnederlandsenationaliteit").value(DEFAULT_REDENVERLIESNEDERLANDSENATIONALITEIT));
    }

    @Test
    @Transactional
    void getNonExistingNationaliteit() throws Exception {
        // Get the nationaliteit
        restNationaliteitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNationaliteit() throws Exception {
        // Initialize the database
        nationaliteitRepository.saveAndFlush(nationaliteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nationaliteit
        Nationaliteit updatedNationaliteit = nationaliteitRepository.findById(nationaliteit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNationaliteit are not directly saved in db
        em.detach(updatedNationaliteit);
        updatedNationaliteit
            .buitenlandsenationaliteit(UPDATED_BUITENLANDSENATIONALITEIT)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datuminganggeldigheid(UPDATED_DATUMINGANGGELDIGHEID)
            .datumopnamen(UPDATED_DATUMOPNAMEN)
            .datumverliesnationaliteit(UPDATED_DATUMVERLIESNATIONALITEIT)
            .nationaliteit(UPDATED_NATIONALITEIT)
            .nationaliteitcode(UPDATED_NATIONALITEITCODE)
            .redenverkrijgingnederlandsenationaliteit(UPDATED_REDENVERKRIJGINGNEDERLANDSENATIONALITEIT)
            .redenverliesnederlandsenationaliteit(UPDATED_REDENVERLIESNEDERLANDSENATIONALITEIT);

        restNationaliteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNationaliteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNationaliteit))
            )
            .andExpect(status().isOk());

        // Validate the Nationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNationaliteitToMatchAllProperties(updatedNationaliteit);
    }

    @Test
    @Transactional
    void putNonExistingNationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nationaliteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNationaliteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nationaliteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nationaliteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nationaliteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNationaliteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nationaliteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nationaliteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNationaliteitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nationaliteit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNationaliteitWithPatch() throws Exception {
        // Initialize the database
        nationaliteitRepository.saveAndFlush(nationaliteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nationaliteit using partial update
        Nationaliteit partialUpdatedNationaliteit = new Nationaliteit();
        partialUpdatedNationaliteit.setId(nationaliteit.getId());

        partialUpdatedNationaliteit
            .datuminganggeldigheid(UPDATED_DATUMINGANGGELDIGHEID)
            .nationaliteit(UPDATED_NATIONALITEIT)
            .nationaliteitcode(UPDATED_NATIONALITEITCODE)
            .redenverliesnederlandsenationaliteit(UPDATED_REDENVERLIESNEDERLANDSENATIONALITEIT);

        restNationaliteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNationaliteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNationaliteit))
            )
            .andExpect(status().isOk());

        // Validate the Nationaliteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNationaliteitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNationaliteit, nationaliteit),
            getPersistedNationaliteit(nationaliteit)
        );
    }

    @Test
    @Transactional
    void fullUpdateNationaliteitWithPatch() throws Exception {
        // Initialize the database
        nationaliteitRepository.saveAndFlush(nationaliteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nationaliteit using partial update
        Nationaliteit partialUpdatedNationaliteit = new Nationaliteit();
        partialUpdatedNationaliteit.setId(nationaliteit.getId());

        partialUpdatedNationaliteit
            .buitenlandsenationaliteit(UPDATED_BUITENLANDSENATIONALITEIT)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datuminganggeldigheid(UPDATED_DATUMINGANGGELDIGHEID)
            .datumopnamen(UPDATED_DATUMOPNAMEN)
            .datumverliesnationaliteit(UPDATED_DATUMVERLIESNATIONALITEIT)
            .nationaliteit(UPDATED_NATIONALITEIT)
            .nationaliteitcode(UPDATED_NATIONALITEITCODE)
            .redenverkrijgingnederlandsenationaliteit(UPDATED_REDENVERKRIJGINGNEDERLANDSENATIONALITEIT)
            .redenverliesnederlandsenationaliteit(UPDATED_REDENVERLIESNEDERLANDSENATIONALITEIT);

        restNationaliteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNationaliteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNationaliteit))
            )
            .andExpect(status().isOk());

        // Validate the Nationaliteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNationaliteitUpdatableFieldsEquals(partialUpdatedNationaliteit, getPersistedNationaliteit(partialUpdatedNationaliteit));
    }

    @Test
    @Transactional
    void patchNonExistingNationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nationaliteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNationaliteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nationaliteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nationaliteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nationaliteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNationaliteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nationaliteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nationaliteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNationaliteitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(nationaliteit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNationaliteit() throws Exception {
        // Initialize the database
        nationaliteitRepository.saveAndFlush(nationaliteit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the nationaliteit
        restNationaliteitMockMvc
            .perform(delete(ENTITY_API_URL_ID, nationaliteit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return nationaliteitRepository.count();
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

    protected Nationaliteit getPersistedNationaliteit(Nationaliteit nationaliteit) {
        return nationaliteitRepository.findById(nationaliteit.getId()).orElseThrow();
    }

    protected void assertPersistedNationaliteitToMatchAllProperties(Nationaliteit expectedNationaliteit) {
        assertNationaliteitAllPropertiesEquals(expectedNationaliteit, getPersistedNationaliteit(expectedNationaliteit));
    }

    protected void assertPersistedNationaliteitToMatchUpdatableProperties(Nationaliteit expectedNationaliteit) {
        assertNationaliteitAllUpdatablePropertiesEquals(expectedNationaliteit, getPersistedNationaliteit(expectedNationaliteit));
    }
}
