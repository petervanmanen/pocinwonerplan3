package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerhuurbaareenheidAsserts.*;
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
import nl.ritense.demo.domain.Verhuurbaareenheid;
import nl.ritense.demo.repository.VerhuurbaareenheidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerhuurbaareenheidResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerhuurbaareenheidResourceIT {

    private static final String DEFAULT_ADRES = "AAAAAAAAAA";
    private static final String UPDATED_ADRES = "BBBBBBBBBB";

    private static final String DEFAULT_AFMETING = "AAAAAAAAAA";
    private static final String UPDATED_AFMETING = "BBBBBBBBBB";

    private static final String DEFAULT_BEZETTING = "AAAAAAAAAA";
    private static final String UPDATED_BEZETTING = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMWERKELIJKBEGIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMWERKELIJKBEGIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMWERKELIJKEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMWERKELIJKEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HUURPRIJS = "AAAAAAAAAA";
    private static final String UPDATED_HUURPRIJS = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_NETTOOMTREK = "AAAAAAAAAA";
    private static final String UPDATED_NETTOOMTREK = "BBBBBBBBBB";

    private static final String DEFAULT_NETTOOPPERVLAK = "AAAAAAAAAA";
    private static final String UPDATED_NETTOOPPERVLAK = "BBBBBBBBBB";

    private static final String DEFAULT_OPMERKINGEN = "AAAAAAAAAA";
    private static final String UPDATED_OPMERKINGEN = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verhuurbaareenheids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerhuurbaareenheidRepository verhuurbaareenheidRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerhuurbaareenheidMockMvc;

    private Verhuurbaareenheid verhuurbaareenheid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verhuurbaareenheid createEntity(EntityManager em) {
        Verhuurbaareenheid verhuurbaareenheid = new Verhuurbaareenheid()
            .adres(DEFAULT_ADRES)
            .afmeting(DEFAULT_AFMETING)
            .bezetting(DEFAULT_BEZETTING)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .datumwerkelijkbegin(DEFAULT_DATUMWERKELIJKBEGIN)
            .datumwerkelijkeinde(DEFAULT_DATUMWERKELIJKEINDE)
            .huurprijs(DEFAULT_HUURPRIJS)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .naam(DEFAULT_NAAM)
            .nettoomtrek(DEFAULT_NETTOOMTREK)
            .nettooppervlak(DEFAULT_NETTOOPPERVLAK)
            .opmerkingen(DEFAULT_OPMERKINGEN)
            .type(DEFAULT_TYPE);
        return verhuurbaareenheid;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verhuurbaareenheid createUpdatedEntity(EntityManager em) {
        Verhuurbaareenheid verhuurbaareenheid = new Verhuurbaareenheid()
            .adres(UPDATED_ADRES)
            .afmeting(UPDATED_AFMETING)
            .bezetting(UPDATED_BEZETTING)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumwerkelijkbegin(UPDATED_DATUMWERKELIJKBEGIN)
            .datumwerkelijkeinde(UPDATED_DATUMWERKELIJKEINDE)
            .huurprijs(UPDATED_HUURPRIJS)
            .identificatie(UPDATED_IDENTIFICATIE)
            .naam(UPDATED_NAAM)
            .nettoomtrek(UPDATED_NETTOOMTREK)
            .nettooppervlak(UPDATED_NETTOOPPERVLAK)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .type(UPDATED_TYPE);
        return verhuurbaareenheid;
    }

    @BeforeEach
    public void initTest() {
        verhuurbaareenheid = createEntity(em);
    }

    @Test
    @Transactional
    void createVerhuurbaareenheid() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verhuurbaareenheid
        var returnedVerhuurbaareenheid = om.readValue(
            restVerhuurbaareenheidMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verhuurbaareenheid)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verhuurbaareenheid.class
        );

        // Validate the Verhuurbaareenheid in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerhuurbaareenheidUpdatableFieldsEquals(
            returnedVerhuurbaareenheid,
            getPersistedVerhuurbaareenheid(returnedVerhuurbaareenheid)
        );
    }

    @Test
    @Transactional
    void createVerhuurbaareenheidWithExistingId() throws Exception {
        // Create the Verhuurbaareenheid with an existing ID
        verhuurbaareenheid.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerhuurbaareenheidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verhuurbaareenheid)))
            .andExpect(status().isBadRequest());

        // Validate the Verhuurbaareenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerhuurbaareenheids() throws Exception {
        // Initialize the database
        verhuurbaareenheidRepository.saveAndFlush(verhuurbaareenheid);

        // Get all the verhuurbaareenheidList
        restVerhuurbaareenheidMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verhuurbaareenheid.getId().intValue())))
            .andExpect(jsonPath("$.[*].adres").value(hasItem(DEFAULT_ADRES)))
            .andExpect(jsonPath("$.[*].afmeting").value(hasItem(DEFAULT_AFMETING)))
            .andExpect(jsonPath("$.[*].bezetting").value(hasItem(DEFAULT_BEZETTING)))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].datumwerkelijkbegin").value(hasItem(DEFAULT_DATUMWERKELIJKBEGIN.toString())))
            .andExpect(jsonPath("$.[*].datumwerkelijkeinde").value(hasItem(DEFAULT_DATUMWERKELIJKEINDE.toString())))
            .andExpect(jsonPath("$.[*].huurprijs").value(hasItem(DEFAULT_HUURPRIJS)))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].nettoomtrek").value(hasItem(DEFAULT_NETTOOMTREK)))
            .andExpect(jsonPath("$.[*].nettooppervlak").value(hasItem(DEFAULT_NETTOOPPERVLAK)))
            .andExpect(jsonPath("$.[*].opmerkingen").value(hasItem(DEFAULT_OPMERKINGEN)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getVerhuurbaareenheid() throws Exception {
        // Initialize the database
        verhuurbaareenheidRepository.saveAndFlush(verhuurbaareenheid);

        // Get the verhuurbaareenheid
        restVerhuurbaareenheidMockMvc
            .perform(get(ENTITY_API_URL_ID, verhuurbaareenheid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verhuurbaareenheid.getId().intValue()))
            .andExpect(jsonPath("$.adres").value(DEFAULT_ADRES))
            .andExpect(jsonPath("$.afmeting").value(DEFAULT_AFMETING))
            .andExpect(jsonPath("$.bezetting").value(DEFAULT_BEZETTING))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.datumwerkelijkbegin").value(DEFAULT_DATUMWERKELIJKBEGIN.toString()))
            .andExpect(jsonPath("$.datumwerkelijkeinde").value(DEFAULT_DATUMWERKELIJKEINDE.toString()))
            .andExpect(jsonPath("$.huurprijs").value(DEFAULT_HUURPRIJS))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.nettoomtrek").value(DEFAULT_NETTOOMTREK))
            .andExpect(jsonPath("$.nettooppervlak").value(DEFAULT_NETTOOPPERVLAK))
            .andExpect(jsonPath("$.opmerkingen").value(DEFAULT_OPMERKINGEN))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingVerhuurbaareenheid() throws Exception {
        // Get the verhuurbaareenheid
        restVerhuurbaareenheidMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerhuurbaareenheid() throws Exception {
        // Initialize the database
        verhuurbaareenheidRepository.saveAndFlush(verhuurbaareenheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verhuurbaareenheid
        Verhuurbaareenheid updatedVerhuurbaareenheid = verhuurbaareenheidRepository.findById(verhuurbaareenheid.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerhuurbaareenheid are not directly saved in db
        em.detach(updatedVerhuurbaareenheid);
        updatedVerhuurbaareenheid
            .adres(UPDATED_ADRES)
            .afmeting(UPDATED_AFMETING)
            .bezetting(UPDATED_BEZETTING)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumwerkelijkbegin(UPDATED_DATUMWERKELIJKBEGIN)
            .datumwerkelijkeinde(UPDATED_DATUMWERKELIJKEINDE)
            .huurprijs(UPDATED_HUURPRIJS)
            .identificatie(UPDATED_IDENTIFICATIE)
            .naam(UPDATED_NAAM)
            .nettoomtrek(UPDATED_NETTOOMTREK)
            .nettooppervlak(UPDATED_NETTOOPPERVLAK)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .type(UPDATED_TYPE);

        restVerhuurbaareenheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerhuurbaareenheid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerhuurbaareenheid))
            )
            .andExpect(status().isOk());

        // Validate the Verhuurbaareenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerhuurbaareenheidToMatchAllProperties(updatedVerhuurbaareenheid);
    }

    @Test
    @Transactional
    void putNonExistingVerhuurbaareenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verhuurbaareenheid.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerhuurbaareenheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verhuurbaareenheid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verhuurbaareenheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verhuurbaareenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerhuurbaareenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verhuurbaareenheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerhuurbaareenheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verhuurbaareenheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verhuurbaareenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerhuurbaareenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verhuurbaareenheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerhuurbaareenheidMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verhuurbaareenheid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verhuurbaareenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerhuurbaareenheidWithPatch() throws Exception {
        // Initialize the database
        verhuurbaareenheidRepository.saveAndFlush(verhuurbaareenheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verhuurbaareenheid using partial update
        Verhuurbaareenheid partialUpdatedVerhuurbaareenheid = new Verhuurbaareenheid();
        partialUpdatedVerhuurbaareenheid.setId(verhuurbaareenheid.getId());

        partialUpdatedVerhuurbaareenheid
            .adres(UPDATED_ADRES)
            .bezetting(UPDATED_BEZETTING)
            .datumwerkelijkbegin(UPDATED_DATUMWERKELIJKBEGIN)
            .datumwerkelijkeinde(UPDATED_DATUMWERKELIJKEINDE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .naam(UPDATED_NAAM)
            .nettoomtrek(UPDATED_NETTOOMTREK)
            .opmerkingen(UPDATED_OPMERKINGEN);

        restVerhuurbaareenheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerhuurbaareenheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerhuurbaareenheid))
            )
            .andExpect(status().isOk());

        // Validate the Verhuurbaareenheid in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerhuurbaareenheidUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerhuurbaareenheid, verhuurbaareenheid),
            getPersistedVerhuurbaareenheid(verhuurbaareenheid)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerhuurbaareenheidWithPatch() throws Exception {
        // Initialize the database
        verhuurbaareenheidRepository.saveAndFlush(verhuurbaareenheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verhuurbaareenheid using partial update
        Verhuurbaareenheid partialUpdatedVerhuurbaareenheid = new Verhuurbaareenheid();
        partialUpdatedVerhuurbaareenheid.setId(verhuurbaareenheid.getId());

        partialUpdatedVerhuurbaareenheid
            .adres(UPDATED_ADRES)
            .afmeting(UPDATED_AFMETING)
            .bezetting(UPDATED_BEZETTING)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumwerkelijkbegin(UPDATED_DATUMWERKELIJKBEGIN)
            .datumwerkelijkeinde(UPDATED_DATUMWERKELIJKEINDE)
            .huurprijs(UPDATED_HUURPRIJS)
            .identificatie(UPDATED_IDENTIFICATIE)
            .naam(UPDATED_NAAM)
            .nettoomtrek(UPDATED_NETTOOMTREK)
            .nettooppervlak(UPDATED_NETTOOPPERVLAK)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .type(UPDATED_TYPE);

        restVerhuurbaareenheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerhuurbaareenheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerhuurbaareenheid))
            )
            .andExpect(status().isOk());

        // Validate the Verhuurbaareenheid in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerhuurbaareenheidUpdatableFieldsEquals(
            partialUpdatedVerhuurbaareenheid,
            getPersistedVerhuurbaareenheid(partialUpdatedVerhuurbaareenheid)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVerhuurbaareenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verhuurbaareenheid.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerhuurbaareenheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verhuurbaareenheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verhuurbaareenheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verhuurbaareenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerhuurbaareenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verhuurbaareenheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerhuurbaareenheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verhuurbaareenheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verhuurbaareenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerhuurbaareenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verhuurbaareenheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerhuurbaareenheidMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verhuurbaareenheid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verhuurbaareenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerhuurbaareenheid() throws Exception {
        // Initialize the database
        verhuurbaareenheidRepository.saveAndFlush(verhuurbaareenheid);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verhuurbaareenheid
        restVerhuurbaareenheidMockMvc
            .perform(delete(ENTITY_API_URL_ID, verhuurbaareenheid.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verhuurbaareenheidRepository.count();
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

    protected Verhuurbaareenheid getPersistedVerhuurbaareenheid(Verhuurbaareenheid verhuurbaareenheid) {
        return verhuurbaareenheidRepository.findById(verhuurbaareenheid.getId()).orElseThrow();
    }

    protected void assertPersistedVerhuurbaareenheidToMatchAllProperties(Verhuurbaareenheid expectedVerhuurbaareenheid) {
        assertVerhuurbaareenheidAllPropertiesEquals(expectedVerhuurbaareenheid, getPersistedVerhuurbaareenheid(expectedVerhuurbaareenheid));
    }

    protected void assertPersistedVerhuurbaareenheidToMatchUpdatableProperties(Verhuurbaareenheid expectedVerhuurbaareenheid) {
        assertVerhuurbaareenheidAllUpdatablePropertiesEquals(
            expectedVerhuurbaareenheid,
            getPersistedVerhuurbaareenheid(expectedVerhuurbaareenheid)
        );
    }
}
