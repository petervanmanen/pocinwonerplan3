package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MaatschappelijkeactiviteitAsserts.*;
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
import nl.ritense.demo.domain.Maatschappelijkeactiviteit;
import nl.ritense.demo.repository.MaatschappelijkeactiviteitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MaatschappelijkeactiviteitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaatschappelijkeactiviteitResourceIT {

    private static final String DEFAULT_ADRESBINNENLAND = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBINNENLAND = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESCORRESPONDENTIE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESCORRESPONDENTIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMAANVANG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANVANG = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIG = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMFAILLISEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMFAILLISEMENT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_INDICATIEECONOMISCHACTIEF = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEECONOMISCHACTIEF = "BBBBBBBBBB";

    private static final String DEFAULT_KVKNUMMER = "AAAAAAAA";
    private static final String UPDATED_KVKNUMMER = "BBBBBBBB";

    private static final String DEFAULT_RECHTSVORM = "AAAAAAAAAA";
    private static final String UPDATED_RECHTSVORM = "BBBBBBBBBB";

    private static final String DEFAULT_RSIN = "AAAAAAAAAA";
    private static final String UPDATED_RSIN = "BBBBBBBBBB";

    private static final String DEFAULT_STATUTAIRENAAM = "AAAAAAAAAA";
    private static final String UPDATED_STATUTAIRENAAM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFOONNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_TELEFOONNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/maatschappelijkeactiviteits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MaatschappelijkeactiviteitRepository maatschappelijkeactiviteitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaatschappelijkeactiviteitMockMvc;

    private Maatschappelijkeactiviteit maatschappelijkeactiviteit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Maatschappelijkeactiviteit createEntity(EntityManager em) {
        Maatschappelijkeactiviteit maatschappelijkeactiviteit = new Maatschappelijkeactiviteit()
            .adresbinnenland(DEFAULT_ADRESBINNENLAND)
            .adrescorrespondentie(DEFAULT_ADRESCORRESPONDENTIE)
            .datumaanvang(DEFAULT_DATUMAANVANG)
            .datumeindegeldig(DEFAULT_DATUMEINDEGELDIG)
            .datumfaillisement(DEFAULT_DATUMFAILLISEMENT)
            .indicatieeconomischactief(DEFAULT_INDICATIEECONOMISCHACTIEF)
            .kvknummer(DEFAULT_KVKNUMMER)
            .rechtsvorm(DEFAULT_RECHTSVORM)
            .rsin(DEFAULT_RSIN)
            .statutairenaam(DEFAULT_STATUTAIRENAAM)
            .telefoonnummer(DEFAULT_TELEFOONNUMMER)
            .url(DEFAULT_URL);
        return maatschappelijkeactiviteit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Maatschappelijkeactiviteit createUpdatedEntity(EntityManager em) {
        Maatschappelijkeactiviteit maatschappelijkeactiviteit = new Maatschappelijkeactiviteit()
            .adresbinnenland(UPDATED_ADRESBINNENLAND)
            .adrescorrespondentie(UPDATED_ADRESCORRESPONDENTIE)
            .datumaanvang(UPDATED_DATUMAANVANG)
            .datumeindegeldig(UPDATED_DATUMEINDEGELDIG)
            .datumfaillisement(UPDATED_DATUMFAILLISEMENT)
            .indicatieeconomischactief(UPDATED_INDICATIEECONOMISCHACTIEF)
            .kvknummer(UPDATED_KVKNUMMER)
            .rechtsvorm(UPDATED_RECHTSVORM)
            .rsin(UPDATED_RSIN)
            .statutairenaam(UPDATED_STATUTAIRENAAM)
            .telefoonnummer(UPDATED_TELEFOONNUMMER)
            .url(UPDATED_URL);
        return maatschappelijkeactiviteit;
    }

    @BeforeEach
    public void initTest() {
        maatschappelijkeactiviteit = createEntity(em);
    }

    @Test
    @Transactional
    void createMaatschappelijkeactiviteit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Maatschappelijkeactiviteit
        var returnedMaatschappelijkeactiviteit = om.readValue(
            restMaatschappelijkeactiviteitMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(maatschappelijkeactiviteit))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Maatschappelijkeactiviteit.class
        );

        // Validate the Maatschappelijkeactiviteit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMaatschappelijkeactiviteitUpdatableFieldsEquals(
            returnedMaatschappelijkeactiviteit,
            getPersistedMaatschappelijkeactiviteit(returnedMaatschappelijkeactiviteit)
        );
    }

    @Test
    @Transactional
    void createMaatschappelijkeactiviteitWithExistingId() throws Exception {
        // Create the Maatschappelijkeactiviteit with an existing ID
        maatschappelijkeactiviteit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaatschappelijkeactiviteitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(maatschappelijkeactiviteit)))
            .andExpect(status().isBadRequest());

        // Validate the Maatschappelijkeactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMaatschappelijkeactiviteits() throws Exception {
        // Initialize the database
        maatschappelijkeactiviteitRepository.saveAndFlush(maatschappelijkeactiviteit);

        // Get all the maatschappelijkeactiviteitList
        restMaatschappelijkeactiviteitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maatschappelijkeactiviteit.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresbinnenland").value(hasItem(DEFAULT_ADRESBINNENLAND)))
            .andExpect(jsonPath("$.[*].adrescorrespondentie").value(hasItem(DEFAULT_ADRESCORRESPONDENTIE)))
            .andExpect(jsonPath("$.[*].datumaanvang").value(hasItem(DEFAULT_DATUMAANVANG.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldig").value(hasItem(DEFAULT_DATUMEINDEGELDIG.toString())))
            .andExpect(jsonPath("$.[*].datumfaillisement").value(hasItem(DEFAULT_DATUMFAILLISEMENT.toString())))
            .andExpect(jsonPath("$.[*].indicatieeconomischactief").value(hasItem(DEFAULT_INDICATIEECONOMISCHACTIEF)))
            .andExpect(jsonPath("$.[*].kvknummer").value(hasItem(DEFAULT_KVKNUMMER)))
            .andExpect(jsonPath("$.[*].rechtsvorm").value(hasItem(DEFAULT_RECHTSVORM)))
            .andExpect(jsonPath("$.[*].rsin").value(hasItem(DEFAULT_RSIN)))
            .andExpect(jsonPath("$.[*].statutairenaam").value(hasItem(DEFAULT_STATUTAIRENAAM)))
            .andExpect(jsonPath("$.[*].telefoonnummer").value(hasItem(DEFAULT_TELEFOONNUMMER)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }

    @Test
    @Transactional
    void getMaatschappelijkeactiviteit() throws Exception {
        // Initialize the database
        maatschappelijkeactiviteitRepository.saveAndFlush(maatschappelijkeactiviteit);

        // Get the maatschappelijkeactiviteit
        restMaatschappelijkeactiviteitMockMvc
            .perform(get(ENTITY_API_URL_ID, maatschappelijkeactiviteit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(maatschappelijkeactiviteit.getId().intValue()))
            .andExpect(jsonPath("$.adresbinnenland").value(DEFAULT_ADRESBINNENLAND))
            .andExpect(jsonPath("$.adrescorrespondentie").value(DEFAULT_ADRESCORRESPONDENTIE))
            .andExpect(jsonPath("$.datumaanvang").value(DEFAULT_DATUMAANVANG.toString()))
            .andExpect(jsonPath("$.datumeindegeldig").value(DEFAULT_DATUMEINDEGELDIG.toString()))
            .andExpect(jsonPath("$.datumfaillisement").value(DEFAULT_DATUMFAILLISEMENT.toString()))
            .andExpect(jsonPath("$.indicatieeconomischactief").value(DEFAULT_INDICATIEECONOMISCHACTIEF))
            .andExpect(jsonPath("$.kvknummer").value(DEFAULT_KVKNUMMER))
            .andExpect(jsonPath("$.rechtsvorm").value(DEFAULT_RECHTSVORM))
            .andExpect(jsonPath("$.rsin").value(DEFAULT_RSIN))
            .andExpect(jsonPath("$.statutairenaam").value(DEFAULT_STATUTAIRENAAM))
            .andExpect(jsonPath("$.telefoonnummer").value(DEFAULT_TELEFOONNUMMER))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }

    @Test
    @Transactional
    void getNonExistingMaatschappelijkeactiviteit() throws Exception {
        // Get the maatschappelijkeactiviteit
        restMaatschappelijkeactiviteitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMaatschappelijkeactiviteit() throws Exception {
        // Initialize the database
        maatschappelijkeactiviteitRepository.saveAndFlush(maatschappelijkeactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the maatschappelijkeactiviteit
        Maatschappelijkeactiviteit updatedMaatschappelijkeactiviteit = maatschappelijkeactiviteitRepository
            .findById(maatschappelijkeactiviteit.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedMaatschappelijkeactiviteit are not directly saved in db
        em.detach(updatedMaatschappelijkeactiviteit);
        updatedMaatschappelijkeactiviteit
            .adresbinnenland(UPDATED_ADRESBINNENLAND)
            .adrescorrespondentie(UPDATED_ADRESCORRESPONDENTIE)
            .datumaanvang(UPDATED_DATUMAANVANG)
            .datumeindegeldig(UPDATED_DATUMEINDEGELDIG)
            .datumfaillisement(UPDATED_DATUMFAILLISEMENT)
            .indicatieeconomischactief(UPDATED_INDICATIEECONOMISCHACTIEF)
            .kvknummer(UPDATED_KVKNUMMER)
            .rechtsvorm(UPDATED_RECHTSVORM)
            .rsin(UPDATED_RSIN)
            .statutairenaam(UPDATED_STATUTAIRENAAM)
            .telefoonnummer(UPDATED_TELEFOONNUMMER)
            .url(UPDATED_URL);

        restMaatschappelijkeactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMaatschappelijkeactiviteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMaatschappelijkeactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Maatschappelijkeactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMaatschappelijkeactiviteitToMatchAllProperties(updatedMaatschappelijkeactiviteit);
    }

    @Test
    @Transactional
    void putNonExistingMaatschappelijkeactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maatschappelijkeactiviteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaatschappelijkeactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, maatschappelijkeactiviteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(maatschappelijkeactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Maatschappelijkeactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaatschappelijkeactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maatschappelijkeactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaatschappelijkeactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(maatschappelijkeactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Maatschappelijkeactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaatschappelijkeactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maatschappelijkeactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaatschappelijkeactiviteitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(maatschappelijkeactiviteit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Maatschappelijkeactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaatschappelijkeactiviteitWithPatch() throws Exception {
        // Initialize the database
        maatschappelijkeactiviteitRepository.saveAndFlush(maatschappelijkeactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the maatschappelijkeactiviteit using partial update
        Maatschappelijkeactiviteit partialUpdatedMaatschappelijkeactiviteit = new Maatschappelijkeactiviteit();
        partialUpdatedMaatschappelijkeactiviteit.setId(maatschappelijkeactiviteit.getId());

        partialUpdatedMaatschappelijkeactiviteit
            .adrescorrespondentie(UPDATED_ADRESCORRESPONDENTIE)
            .datumaanvang(UPDATED_DATUMAANVANG)
            .datumeindegeldig(UPDATED_DATUMEINDEGELDIG)
            .indicatieeconomischactief(UPDATED_INDICATIEECONOMISCHACTIEF)
            .kvknummer(UPDATED_KVKNUMMER)
            .statutairenaam(UPDATED_STATUTAIRENAAM)
            .telefoonnummer(UPDATED_TELEFOONNUMMER);

        restMaatschappelijkeactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaatschappelijkeactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMaatschappelijkeactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Maatschappelijkeactiviteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMaatschappelijkeactiviteitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMaatschappelijkeactiviteit, maatschappelijkeactiviteit),
            getPersistedMaatschappelijkeactiviteit(maatschappelijkeactiviteit)
        );
    }

    @Test
    @Transactional
    void fullUpdateMaatschappelijkeactiviteitWithPatch() throws Exception {
        // Initialize the database
        maatschappelijkeactiviteitRepository.saveAndFlush(maatschappelijkeactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the maatschappelijkeactiviteit using partial update
        Maatschappelijkeactiviteit partialUpdatedMaatschappelijkeactiviteit = new Maatschappelijkeactiviteit();
        partialUpdatedMaatschappelijkeactiviteit.setId(maatschappelijkeactiviteit.getId());

        partialUpdatedMaatschappelijkeactiviteit
            .adresbinnenland(UPDATED_ADRESBINNENLAND)
            .adrescorrespondentie(UPDATED_ADRESCORRESPONDENTIE)
            .datumaanvang(UPDATED_DATUMAANVANG)
            .datumeindegeldig(UPDATED_DATUMEINDEGELDIG)
            .datumfaillisement(UPDATED_DATUMFAILLISEMENT)
            .indicatieeconomischactief(UPDATED_INDICATIEECONOMISCHACTIEF)
            .kvknummer(UPDATED_KVKNUMMER)
            .rechtsvorm(UPDATED_RECHTSVORM)
            .rsin(UPDATED_RSIN)
            .statutairenaam(UPDATED_STATUTAIRENAAM)
            .telefoonnummer(UPDATED_TELEFOONNUMMER)
            .url(UPDATED_URL);

        restMaatschappelijkeactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaatschappelijkeactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMaatschappelijkeactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Maatschappelijkeactiviteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMaatschappelijkeactiviteitUpdatableFieldsEquals(
            partialUpdatedMaatschappelijkeactiviteit,
            getPersistedMaatschappelijkeactiviteit(partialUpdatedMaatschappelijkeactiviteit)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMaatschappelijkeactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maatschappelijkeactiviteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaatschappelijkeactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, maatschappelijkeactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(maatschappelijkeactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Maatschappelijkeactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaatschappelijkeactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maatschappelijkeactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaatschappelijkeactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(maatschappelijkeactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Maatschappelijkeactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaatschappelijkeactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maatschappelijkeactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaatschappelijkeactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(maatschappelijkeactiviteit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Maatschappelijkeactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaatschappelijkeactiviteit() throws Exception {
        // Initialize the database
        maatschappelijkeactiviteitRepository.saveAndFlush(maatschappelijkeactiviteit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the maatschappelijkeactiviteit
        restMaatschappelijkeactiviteitMockMvc
            .perform(delete(ENTITY_API_URL_ID, maatschappelijkeactiviteit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return maatschappelijkeactiviteitRepository.count();
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

    protected Maatschappelijkeactiviteit getPersistedMaatschappelijkeactiviteit(Maatschappelijkeactiviteit maatschappelijkeactiviteit) {
        return maatschappelijkeactiviteitRepository.findById(maatschappelijkeactiviteit.getId()).orElseThrow();
    }

    protected void assertPersistedMaatschappelijkeactiviteitToMatchAllProperties(
        Maatschappelijkeactiviteit expectedMaatschappelijkeactiviteit
    ) {
        assertMaatschappelijkeactiviteitAllPropertiesEquals(
            expectedMaatschappelijkeactiviteit,
            getPersistedMaatschappelijkeactiviteit(expectedMaatschappelijkeactiviteit)
        );
    }

    protected void assertPersistedMaatschappelijkeactiviteitToMatchUpdatableProperties(
        Maatschappelijkeactiviteit expectedMaatschappelijkeactiviteit
    ) {
        assertMaatschappelijkeactiviteitAllUpdatablePropertiesEquals(
            expectedMaatschappelijkeactiviteit,
            getPersistedMaatschappelijkeactiviteit(expectedMaatschappelijkeactiviteit)
        );
    }
}
