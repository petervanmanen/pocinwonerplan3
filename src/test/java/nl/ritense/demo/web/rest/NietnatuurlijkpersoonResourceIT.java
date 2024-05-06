package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NietnatuurlijkpersoonAsserts.*;
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
import nl.ritense.demo.domain.Nietnatuurlijkpersoon;
import nl.ritense.demo.repository.NietnatuurlijkpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NietnatuurlijkpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NietnatuurlijkpersoonResourceIT {

    private static final LocalDate DEFAULT_DATUMAANVANG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANVANG = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMUITSCHRIJVING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMUITSCHRIJVING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMVOORTZETTING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMVOORTZETTING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FAXNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_FAXNUMMER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INGESCHREVEN = false;
    private static final Boolean UPDATED_INGESCHREVEN = true;

    private static final Boolean DEFAULT_INOPRICHTING = false;
    private static final Boolean UPDATED_INOPRICHTING = true;

    private static final String DEFAULT_KVKNUMMER = "AAAAAAAA";
    private static final String UPDATED_KVKNUMMER = "BBBBBBBB";

    private static final String DEFAULT_NNPID = "AAAAAAAAAA";
    private static final String UPDATED_NNPID = "BBBBBBBBBB";

    private static final String DEFAULT_RECHTSVORM = "AAAAAAAAAA";
    private static final String UPDATED_RECHTSVORM = "BBBBBBBBBB";

    private static final String DEFAULT_RSINNUMMER = "AAAAAAAA";
    private static final String UPDATED_RSINNUMMER = "BBBBBBBB";

    private static final String DEFAULT_STATUTAIRENAAM = "AAAAAAAAAA";
    private static final String UPDATED_STATUTAIRENAAM = "BBBBBBBBBB";

    private static final String DEFAULT_STATUTAIREZETEL = "AAAAAAAAAA";
    private static final String UPDATED_STATUTAIREZETEL = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITEURL = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITEURL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nietnatuurlijkpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NietnatuurlijkpersoonRepository nietnatuurlijkpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNietnatuurlijkpersoonMockMvc;

    private Nietnatuurlijkpersoon nietnatuurlijkpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nietnatuurlijkpersoon createEntity(EntityManager em) {
        Nietnatuurlijkpersoon nietnatuurlijkpersoon = new Nietnatuurlijkpersoon()
            .datumaanvang(DEFAULT_DATUMAANVANG)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumuitschrijving(DEFAULT_DATUMUITSCHRIJVING)
            .datumvoortzetting(DEFAULT_DATUMVOORTZETTING)
            .faxnummer(DEFAULT_FAXNUMMER)
            .ingeschreven(DEFAULT_INGESCHREVEN)
            .inoprichting(DEFAULT_INOPRICHTING)
            .kvknummer(DEFAULT_KVKNUMMER)
            .nnpid(DEFAULT_NNPID)
            .rechtsvorm(DEFAULT_RECHTSVORM)
            .rsinnummer(DEFAULT_RSINNUMMER)
            .statutairenaam(DEFAULT_STATUTAIRENAAM)
            .statutairezetel(DEFAULT_STATUTAIREZETEL)
            .websiteurl(DEFAULT_WEBSITEURL);
        return nietnatuurlijkpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nietnatuurlijkpersoon createUpdatedEntity(EntityManager em) {
        Nietnatuurlijkpersoon nietnatuurlijkpersoon = new Nietnatuurlijkpersoon()
            .datumaanvang(UPDATED_DATUMAANVANG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumuitschrijving(UPDATED_DATUMUITSCHRIJVING)
            .datumvoortzetting(UPDATED_DATUMVOORTZETTING)
            .faxnummer(UPDATED_FAXNUMMER)
            .ingeschreven(UPDATED_INGESCHREVEN)
            .inoprichting(UPDATED_INOPRICHTING)
            .kvknummer(UPDATED_KVKNUMMER)
            .nnpid(UPDATED_NNPID)
            .rechtsvorm(UPDATED_RECHTSVORM)
            .rsinnummer(UPDATED_RSINNUMMER)
            .statutairenaam(UPDATED_STATUTAIRENAAM)
            .statutairezetel(UPDATED_STATUTAIREZETEL)
            .websiteurl(UPDATED_WEBSITEURL);
        return nietnatuurlijkpersoon;
    }

    @BeforeEach
    public void initTest() {
        nietnatuurlijkpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createNietnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Nietnatuurlijkpersoon
        var returnedNietnatuurlijkpersoon = om.readValue(
            restNietnatuurlijkpersoonMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nietnatuurlijkpersoon)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Nietnatuurlijkpersoon.class
        );

        // Validate the Nietnatuurlijkpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNietnatuurlijkpersoonUpdatableFieldsEquals(
            returnedNietnatuurlijkpersoon,
            getPersistedNietnatuurlijkpersoon(returnedNietnatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void createNietnatuurlijkpersoonWithExistingId() throws Exception {
        // Create the Nietnatuurlijkpersoon with an existing ID
        nietnatuurlijkpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNietnatuurlijkpersoonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nietnatuurlijkpersoon)))
            .andExpect(status().isBadRequest());

        // Validate the Nietnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNietnatuurlijkpersoons() throws Exception {
        // Initialize the database
        nietnatuurlijkpersoonRepository.saveAndFlush(nietnatuurlijkpersoon);

        // Get all the nietnatuurlijkpersoonList
        restNietnatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nietnatuurlijkpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumaanvang").value(hasItem(DEFAULT_DATUMAANVANG.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumuitschrijving").value(hasItem(DEFAULT_DATUMUITSCHRIJVING.toString())))
            .andExpect(jsonPath("$.[*].datumvoortzetting").value(hasItem(DEFAULT_DATUMVOORTZETTING.toString())))
            .andExpect(jsonPath("$.[*].faxnummer").value(hasItem(DEFAULT_FAXNUMMER)))
            .andExpect(jsonPath("$.[*].ingeschreven").value(hasItem(DEFAULT_INGESCHREVEN.booleanValue())))
            .andExpect(jsonPath("$.[*].inoprichting").value(hasItem(DEFAULT_INOPRICHTING.booleanValue())))
            .andExpect(jsonPath("$.[*].kvknummer").value(hasItem(DEFAULT_KVKNUMMER)))
            .andExpect(jsonPath("$.[*].nnpid").value(hasItem(DEFAULT_NNPID)))
            .andExpect(jsonPath("$.[*].rechtsvorm").value(hasItem(DEFAULT_RECHTSVORM)))
            .andExpect(jsonPath("$.[*].rsinnummer").value(hasItem(DEFAULT_RSINNUMMER)))
            .andExpect(jsonPath("$.[*].statutairenaam").value(hasItem(DEFAULT_STATUTAIRENAAM)))
            .andExpect(jsonPath("$.[*].statutairezetel").value(hasItem(DEFAULT_STATUTAIREZETEL)))
            .andExpect(jsonPath("$.[*].websiteurl").value(hasItem(DEFAULT_WEBSITEURL)));
    }

    @Test
    @Transactional
    void getNietnatuurlijkpersoon() throws Exception {
        // Initialize the database
        nietnatuurlijkpersoonRepository.saveAndFlush(nietnatuurlijkpersoon);

        // Get the nietnatuurlijkpersoon
        restNietnatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, nietnatuurlijkpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nietnatuurlijkpersoon.getId().intValue()))
            .andExpect(jsonPath("$.datumaanvang").value(DEFAULT_DATUMAANVANG.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumuitschrijving").value(DEFAULT_DATUMUITSCHRIJVING.toString()))
            .andExpect(jsonPath("$.datumvoortzetting").value(DEFAULT_DATUMVOORTZETTING.toString()))
            .andExpect(jsonPath("$.faxnummer").value(DEFAULT_FAXNUMMER))
            .andExpect(jsonPath("$.ingeschreven").value(DEFAULT_INGESCHREVEN.booleanValue()))
            .andExpect(jsonPath("$.inoprichting").value(DEFAULT_INOPRICHTING.booleanValue()))
            .andExpect(jsonPath("$.kvknummer").value(DEFAULT_KVKNUMMER))
            .andExpect(jsonPath("$.nnpid").value(DEFAULT_NNPID))
            .andExpect(jsonPath("$.rechtsvorm").value(DEFAULT_RECHTSVORM))
            .andExpect(jsonPath("$.rsinnummer").value(DEFAULT_RSINNUMMER))
            .andExpect(jsonPath("$.statutairenaam").value(DEFAULT_STATUTAIRENAAM))
            .andExpect(jsonPath("$.statutairezetel").value(DEFAULT_STATUTAIREZETEL))
            .andExpect(jsonPath("$.websiteurl").value(DEFAULT_WEBSITEURL));
    }

    @Test
    @Transactional
    void getNonExistingNietnatuurlijkpersoon() throws Exception {
        // Get the nietnatuurlijkpersoon
        restNietnatuurlijkpersoonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNietnatuurlijkpersoon() throws Exception {
        // Initialize the database
        nietnatuurlijkpersoonRepository.saveAndFlush(nietnatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nietnatuurlijkpersoon
        Nietnatuurlijkpersoon updatedNietnatuurlijkpersoon = nietnatuurlijkpersoonRepository
            .findById(nietnatuurlijkpersoon.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedNietnatuurlijkpersoon are not directly saved in db
        em.detach(updatedNietnatuurlijkpersoon);
        updatedNietnatuurlijkpersoon
            .datumaanvang(UPDATED_DATUMAANVANG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumuitschrijving(UPDATED_DATUMUITSCHRIJVING)
            .datumvoortzetting(UPDATED_DATUMVOORTZETTING)
            .faxnummer(UPDATED_FAXNUMMER)
            .ingeschreven(UPDATED_INGESCHREVEN)
            .inoprichting(UPDATED_INOPRICHTING)
            .kvknummer(UPDATED_KVKNUMMER)
            .nnpid(UPDATED_NNPID)
            .rechtsvorm(UPDATED_RECHTSVORM)
            .rsinnummer(UPDATED_RSINNUMMER)
            .statutairenaam(UPDATED_STATUTAIRENAAM)
            .statutairezetel(UPDATED_STATUTAIREZETEL)
            .websiteurl(UPDATED_WEBSITEURL);

        restNietnatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNietnatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNietnatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Nietnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNietnatuurlijkpersoonToMatchAllProperties(updatedNietnatuurlijkpersoon);
    }

    @Test
    @Transactional
    void putNonExistingNietnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nietnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNietnatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nietnatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nietnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nietnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNietnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nietnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNietnatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nietnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nietnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNietnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nietnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNietnatuurlijkpersoonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nietnatuurlijkpersoon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nietnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNietnatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        nietnatuurlijkpersoonRepository.saveAndFlush(nietnatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nietnatuurlijkpersoon using partial update
        Nietnatuurlijkpersoon partialUpdatedNietnatuurlijkpersoon = new Nietnatuurlijkpersoon();
        partialUpdatedNietnatuurlijkpersoon.setId(nietnatuurlijkpersoon.getId());

        partialUpdatedNietnatuurlijkpersoon
            .datumaanvang(UPDATED_DATUMAANVANG)
            .datumuitschrijving(UPDATED_DATUMUITSCHRIJVING)
            .faxnummer(UPDATED_FAXNUMMER)
            .inoprichting(UPDATED_INOPRICHTING)
            .kvknummer(UPDATED_KVKNUMMER)
            .nnpid(UPDATED_NNPID)
            .rechtsvorm(UPDATED_RECHTSVORM)
            .websiteurl(UPDATED_WEBSITEURL);

        restNietnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNietnatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNietnatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Nietnatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNietnatuurlijkpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNietnatuurlijkpersoon, nietnatuurlijkpersoon),
            getPersistedNietnatuurlijkpersoon(nietnatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateNietnatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        nietnatuurlijkpersoonRepository.saveAndFlush(nietnatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nietnatuurlijkpersoon using partial update
        Nietnatuurlijkpersoon partialUpdatedNietnatuurlijkpersoon = new Nietnatuurlijkpersoon();
        partialUpdatedNietnatuurlijkpersoon.setId(nietnatuurlijkpersoon.getId());

        partialUpdatedNietnatuurlijkpersoon
            .datumaanvang(UPDATED_DATUMAANVANG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumuitschrijving(UPDATED_DATUMUITSCHRIJVING)
            .datumvoortzetting(UPDATED_DATUMVOORTZETTING)
            .faxnummer(UPDATED_FAXNUMMER)
            .ingeschreven(UPDATED_INGESCHREVEN)
            .inoprichting(UPDATED_INOPRICHTING)
            .kvknummer(UPDATED_KVKNUMMER)
            .nnpid(UPDATED_NNPID)
            .rechtsvorm(UPDATED_RECHTSVORM)
            .rsinnummer(UPDATED_RSINNUMMER)
            .statutairenaam(UPDATED_STATUTAIRENAAM)
            .statutairezetel(UPDATED_STATUTAIREZETEL)
            .websiteurl(UPDATED_WEBSITEURL);

        restNietnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNietnatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNietnatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Nietnatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNietnatuurlijkpersoonUpdatableFieldsEquals(
            partialUpdatedNietnatuurlijkpersoon,
            getPersistedNietnatuurlijkpersoon(partialUpdatedNietnatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNietnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nietnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNietnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nietnatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nietnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nietnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNietnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nietnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNietnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nietnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nietnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNietnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nietnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNietnatuurlijkpersoonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(nietnatuurlijkpersoon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nietnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNietnatuurlijkpersoon() throws Exception {
        // Initialize the database
        nietnatuurlijkpersoonRepository.saveAndFlush(nietnatuurlijkpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the nietnatuurlijkpersoon
        restNietnatuurlijkpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, nietnatuurlijkpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return nietnatuurlijkpersoonRepository.count();
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

    protected Nietnatuurlijkpersoon getPersistedNietnatuurlijkpersoon(Nietnatuurlijkpersoon nietnatuurlijkpersoon) {
        return nietnatuurlijkpersoonRepository.findById(nietnatuurlijkpersoon.getId()).orElseThrow();
    }

    protected void assertPersistedNietnatuurlijkpersoonToMatchAllProperties(Nietnatuurlijkpersoon expectedNietnatuurlijkpersoon) {
        assertNietnatuurlijkpersoonAllPropertiesEquals(
            expectedNietnatuurlijkpersoon,
            getPersistedNietnatuurlijkpersoon(expectedNietnatuurlijkpersoon)
        );
    }

    protected void assertPersistedNietnatuurlijkpersoonToMatchUpdatableProperties(Nietnatuurlijkpersoon expectedNietnatuurlijkpersoon) {
        assertNietnatuurlijkpersoonAllUpdatablePropertiesEquals(
            expectedNietnatuurlijkpersoon,
            getPersistedNietnatuurlijkpersoon(expectedNietnatuurlijkpersoon)
        );
    }
}
