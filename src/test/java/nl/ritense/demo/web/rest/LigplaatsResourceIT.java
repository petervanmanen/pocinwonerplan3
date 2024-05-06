package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LigplaatsAsserts.*;
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
import nl.ritense.demo.domain.Ligplaats;
import nl.ritense.demo.repository.LigplaatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LigplaatsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LigplaatsResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINGANG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINGANG = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DOCUMENTDATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOCUMENTDATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DOCUMENTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_GECONSTATEERD = "AAAAAAAAAA";
    private static final String UPDATED_GECONSTATEERD = "BBBBBBBBBB";

    private static final String DEFAULT_GEOMETRIE = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIE = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INONDERZOEK = false;
    private static final Boolean UPDATED_INONDERZOEK = true;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_VERSIE = "AAAAAAAAAA";
    private static final String UPDATED_VERSIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ligplaats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LigplaatsRepository ligplaatsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLigplaatsMockMvc;

    private Ligplaats ligplaats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ligplaats createEntity(EntityManager em) {
        Ligplaats ligplaats = new Ligplaats()
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .datumingang(DEFAULT_DATUMINGANG)
            .documentdatum(DEFAULT_DOCUMENTDATUM)
            .documentnummer(DEFAULT_DOCUMENTNUMMER)
            .geconstateerd(DEFAULT_GECONSTATEERD)
            .geometrie(DEFAULT_GEOMETRIE)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .inonderzoek(DEFAULT_INONDERZOEK)
            .status(DEFAULT_STATUS)
            .versie(DEFAULT_VERSIE);
        return ligplaats;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ligplaats createUpdatedEntity(EntityManager em) {
        Ligplaats ligplaats = new Ligplaats()
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .documentdatum(UPDATED_DOCUMENTDATUM)
            .documentnummer(UPDATED_DOCUMENTNUMMER)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .status(UPDATED_STATUS)
            .versie(UPDATED_VERSIE);
        return ligplaats;
    }

    @BeforeEach
    public void initTest() {
        ligplaats = createEntity(em);
    }

    @Test
    @Transactional
    void createLigplaats() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ligplaats
        var returnedLigplaats = om.readValue(
            restLigplaatsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ligplaats)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Ligplaats.class
        );

        // Validate the Ligplaats in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLigplaatsUpdatableFieldsEquals(returnedLigplaats, getPersistedLigplaats(returnedLigplaats));
    }

    @Test
    @Transactional
    void createLigplaatsWithExistingId() throws Exception {
        // Create the Ligplaats with an existing ID
        ligplaats.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLigplaatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ligplaats)))
            .andExpect(status().isBadRequest());

        // Validate the Ligplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLigplaats() throws Exception {
        // Initialize the database
        ligplaatsRepository.saveAndFlush(ligplaats);

        // Get all the ligplaatsList
        restLigplaatsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ligplaats.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumingang").value(hasItem(DEFAULT_DATUMINGANG.toString())))
            .andExpect(jsonPath("$.[*].documentdatum").value(hasItem(DEFAULT_DOCUMENTDATUM.toString())))
            .andExpect(jsonPath("$.[*].documentnummer").value(hasItem(DEFAULT_DOCUMENTNUMMER)))
            .andExpect(jsonPath("$.[*].geconstateerd").value(hasItem(DEFAULT_GECONSTATEERD)))
            .andExpect(jsonPath("$.[*].geometrie").value(hasItem(DEFAULT_GEOMETRIE)))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].inonderzoek").value(hasItem(DEFAULT_INONDERZOEK.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].versie").value(hasItem(DEFAULT_VERSIE)));
    }

    @Test
    @Transactional
    void getLigplaats() throws Exception {
        // Initialize the database
        ligplaatsRepository.saveAndFlush(ligplaats);

        // Get the ligplaats
        restLigplaatsMockMvc
            .perform(get(ENTITY_API_URL_ID, ligplaats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ligplaats.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumingang").value(DEFAULT_DATUMINGANG.toString()))
            .andExpect(jsonPath("$.documentdatum").value(DEFAULT_DOCUMENTDATUM.toString()))
            .andExpect(jsonPath("$.documentnummer").value(DEFAULT_DOCUMENTNUMMER))
            .andExpect(jsonPath("$.geconstateerd").value(DEFAULT_GECONSTATEERD))
            .andExpect(jsonPath("$.geometrie").value(DEFAULT_GEOMETRIE))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.inonderzoek").value(DEFAULT_INONDERZOEK.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.versie").value(DEFAULT_VERSIE));
    }

    @Test
    @Transactional
    void getNonExistingLigplaats() throws Exception {
        // Get the ligplaats
        restLigplaatsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLigplaats() throws Exception {
        // Initialize the database
        ligplaatsRepository.saveAndFlush(ligplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ligplaats
        Ligplaats updatedLigplaats = ligplaatsRepository.findById(ligplaats.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLigplaats are not directly saved in db
        em.detach(updatedLigplaats);
        updatedLigplaats
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .documentdatum(UPDATED_DOCUMENTDATUM)
            .documentnummer(UPDATED_DOCUMENTNUMMER)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .status(UPDATED_STATUS)
            .versie(UPDATED_VERSIE);

        restLigplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLigplaats.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLigplaats))
            )
            .andExpect(status().isOk());

        // Validate the Ligplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLigplaatsToMatchAllProperties(updatedLigplaats);
    }

    @Test
    @Transactional
    void putNonExistingLigplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligplaats.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ligplaats.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ligplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ligplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLigplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ligplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ligplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLigplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigplaatsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ligplaats)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ligplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLigplaatsWithPatch() throws Exception {
        // Initialize the database
        ligplaatsRepository.saveAndFlush(ligplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ligplaats using partial update
        Ligplaats partialUpdatedLigplaats = new Ligplaats();
        partialUpdatedLigplaats.setId(ligplaats.getId());

        partialUpdatedLigplaats
            .datumingang(UPDATED_DATUMINGANG)
            .documentnummer(UPDATED_DOCUMENTNUMMER)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .status(UPDATED_STATUS)
            .versie(UPDATED_VERSIE);

        restLigplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLigplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLigplaats))
            )
            .andExpect(status().isOk());

        // Validate the Ligplaats in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLigplaatsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLigplaats, ligplaats),
            getPersistedLigplaats(ligplaats)
        );
    }

    @Test
    @Transactional
    void fullUpdateLigplaatsWithPatch() throws Exception {
        // Initialize the database
        ligplaatsRepository.saveAndFlush(ligplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ligplaats using partial update
        Ligplaats partialUpdatedLigplaats = new Ligplaats();
        partialUpdatedLigplaats.setId(ligplaats.getId());

        partialUpdatedLigplaats
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .documentdatum(UPDATED_DOCUMENTDATUM)
            .documentnummer(UPDATED_DOCUMENTNUMMER)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .status(UPDATED_STATUS)
            .versie(UPDATED_VERSIE);

        restLigplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLigplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLigplaats))
            )
            .andExpect(status().isOk());

        // Validate the Ligplaats in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLigplaatsUpdatableFieldsEquals(partialUpdatedLigplaats, getPersistedLigplaats(partialUpdatedLigplaats));
    }

    @Test
    @Transactional
    void patchNonExistingLigplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligplaats.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ligplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ligplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ligplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLigplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ligplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ligplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLigplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigplaatsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ligplaats)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ligplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLigplaats() throws Exception {
        // Initialize the database
        ligplaatsRepository.saveAndFlush(ligplaats);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ligplaats
        restLigplaatsMockMvc
            .perform(delete(ENTITY_API_URL_ID, ligplaats.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ligplaatsRepository.count();
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

    protected Ligplaats getPersistedLigplaats(Ligplaats ligplaats) {
        return ligplaatsRepository.findById(ligplaats.getId()).orElseThrow();
    }

    protected void assertPersistedLigplaatsToMatchAllProperties(Ligplaats expectedLigplaats) {
        assertLigplaatsAllPropertiesEquals(expectedLigplaats, getPersistedLigplaats(expectedLigplaats));
    }

    protected void assertPersistedLigplaatsToMatchUpdatableProperties(Ligplaats expectedLigplaats) {
        assertLigplaatsAllUpdatablePropertiesEquals(expectedLigplaats, getPersistedLigplaats(expectedLigplaats));
    }
}
