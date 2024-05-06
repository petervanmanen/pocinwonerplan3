package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WoonplaatsAsserts.*;
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
import nl.ritense.demo.domain.Woonplaats;
import nl.ritense.demo.repository.WoonplaatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WoonplaatsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WoonplaatsResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINGANG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINGANG = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_GECONSTATEERD = false;
    private static final Boolean UPDATED_GECONSTATEERD = true;

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

    private static final String DEFAULT_WOONPLAATSNAAM = "AAAAAAAAAA";
    private static final String UPDATED_WOONPLAATSNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_WOONPLAATSNAAMNEN = "AAAAAAAAAA";
    private static final String UPDATED_WOONPLAATSNAAMNEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/woonplaats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WoonplaatsRepository woonplaatsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWoonplaatsMockMvc;

    private Woonplaats woonplaats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Woonplaats createEntity(EntityManager em) {
        Woonplaats woonplaats = new Woonplaats()
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .datumingang(DEFAULT_DATUMINGANG)
            .geconstateerd(DEFAULT_GECONSTATEERD)
            .geometrie(DEFAULT_GEOMETRIE)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .inonderzoek(DEFAULT_INONDERZOEK)
            .status(DEFAULT_STATUS)
            .versie(DEFAULT_VERSIE)
            .woonplaatsnaam(DEFAULT_WOONPLAATSNAAM)
            .woonplaatsnaamnen(DEFAULT_WOONPLAATSNAAMNEN);
        return woonplaats;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Woonplaats createUpdatedEntity(EntityManager em) {
        Woonplaats woonplaats = new Woonplaats()
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .status(UPDATED_STATUS)
            .versie(UPDATED_VERSIE)
            .woonplaatsnaam(UPDATED_WOONPLAATSNAAM)
            .woonplaatsnaamnen(UPDATED_WOONPLAATSNAAMNEN);
        return woonplaats;
    }

    @BeforeEach
    public void initTest() {
        woonplaats = createEntity(em);
    }

    @Test
    @Transactional
    void createWoonplaats() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Woonplaats
        var returnedWoonplaats = om.readValue(
            restWoonplaatsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(woonplaats)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Woonplaats.class
        );

        // Validate the Woonplaats in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWoonplaatsUpdatableFieldsEquals(returnedWoonplaats, getPersistedWoonplaats(returnedWoonplaats));
    }

    @Test
    @Transactional
    void createWoonplaatsWithExistingId() throws Exception {
        // Create the Woonplaats with an existing ID
        woonplaats.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWoonplaatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(woonplaats)))
            .andExpect(status().isBadRequest());

        // Validate the Woonplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWoonplaats() throws Exception {
        // Initialize the database
        woonplaatsRepository.saveAndFlush(woonplaats);

        // Get all the woonplaatsList
        restWoonplaatsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(woonplaats.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumingang").value(hasItem(DEFAULT_DATUMINGANG.toString())))
            .andExpect(jsonPath("$.[*].geconstateerd").value(hasItem(DEFAULT_GECONSTATEERD.booleanValue())))
            .andExpect(jsonPath("$.[*].geometrie").value(hasItem(DEFAULT_GEOMETRIE)))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].inonderzoek").value(hasItem(DEFAULT_INONDERZOEK.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].versie").value(hasItem(DEFAULT_VERSIE)))
            .andExpect(jsonPath("$.[*].woonplaatsnaam").value(hasItem(DEFAULT_WOONPLAATSNAAM)))
            .andExpect(jsonPath("$.[*].woonplaatsnaamnen").value(hasItem(DEFAULT_WOONPLAATSNAAMNEN)));
    }

    @Test
    @Transactional
    void getWoonplaats() throws Exception {
        // Initialize the database
        woonplaatsRepository.saveAndFlush(woonplaats);

        // Get the woonplaats
        restWoonplaatsMockMvc
            .perform(get(ENTITY_API_URL_ID, woonplaats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(woonplaats.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumingang").value(DEFAULT_DATUMINGANG.toString()))
            .andExpect(jsonPath("$.geconstateerd").value(DEFAULT_GECONSTATEERD.booleanValue()))
            .andExpect(jsonPath("$.geometrie").value(DEFAULT_GEOMETRIE))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.inonderzoek").value(DEFAULT_INONDERZOEK.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.versie").value(DEFAULT_VERSIE))
            .andExpect(jsonPath("$.woonplaatsnaam").value(DEFAULT_WOONPLAATSNAAM))
            .andExpect(jsonPath("$.woonplaatsnaamnen").value(DEFAULT_WOONPLAATSNAAMNEN));
    }

    @Test
    @Transactional
    void getNonExistingWoonplaats() throws Exception {
        // Get the woonplaats
        restWoonplaatsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWoonplaats() throws Exception {
        // Initialize the database
        woonplaatsRepository.saveAndFlush(woonplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the woonplaats
        Woonplaats updatedWoonplaats = woonplaatsRepository.findById(woonplaats.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWoonplaats are not directly saved in db
        em.detach(updatedWoonplaats);
        updatedWoonplaats
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .status(UPDATED_STATUS)
            .versie(UPDATED_VERSIE)
            .woonplaatsnaam(UPDATED_WOONPLAATSNAAM)
            .woonplaatsnaamnen(UPDATED_WOONPLAATSNAAMNEN);

        restWoonplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWoonplaats.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWoonplaats))
            )
            .andExpect(status().isOk());

        // Validate the Woonplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWoonplaatsToMatchAllProperties(updatedWoonplaats);
    }

    @Test
    @Transactional
    void putNonExistingWoonplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonplaats.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWoonplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, woonplaats.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(woonplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Woonplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWoonplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWoonplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(woonplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Woonplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWoonplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWoonplaatsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(woonplaats)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Woonplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWoonplaatsWithPatch() throws Exception {
        // Initialize the database
        woonplaatsRepository.saveAndFlush(woonplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the woonplaats using partial update
        Woonplaats partialUpdatedWoonplaats = new Woonplaats();
        partialUpdatedWoonplaats.setId(woonplaats.getId());

        partialUpdatedWoonplaats
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geometrie(UPDATED_GEOMETRIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .versie(UPDATED_VERSIE)
            .woonplaatsnaam(UPDATED_WOONPLAATSNAAM)
            .woonplaatsnaamnen(UPDATED_WOONPLAATSNAAMNEN);

        restWoonplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWoonplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWoonplaats))
            )
            .andExpect(status().isOk());

        // Validate the Woonplaats in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWoonplaatsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWoonplaats, woonplaats),
            getPersistedWoonplaats(woonplaats)
        );
    }

    @Test
    @Transactional
    void fullUpdateWoonplaatsWithPatch() throws Exception {
        // Initialize the database
        woonplaatsRepository.saveAndFlush(woonplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the woonplaats using partial update
        Woonplaats partialUpdatedWoonplaats = new Woonplaats();
        partialUpdatedWoonplaats.setId(woonplaats.getId());

        partialUpdatedWoonplaats
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .status(UPDATED_STATUS)
            .versie(UPDATED_VERSIE)
            .woonplaatsnaam(UPDATED_WOONPLAATSNAAM)
            .woonplaatsnaamnen(UPDATED_WOONPLAATSNAAMNEN);

        restWoonplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWoonplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWoonplaats))
            )
            .andExpect(status().isOk());

        // Validate the Woonplaats in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWoonplaatsUpdatableFieldsEquals(partialUpdatedWoonplaats, getPersistedWoonplaats(partialUpdatedWoonplaats));
    }

    @Test
    @Transactional
    void patchNonExistingWoonplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonplaats.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWoonplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, woonplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(woonplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Woonplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWoonplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWoonplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(woonplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Woonplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWoonplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWoonplaatsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(woonplaats)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Woonplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWoonplaats() throws Exception {
        // Initialize the database
        woonplaatsRepository.saveAndFlush(woonplaats);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the woonplaats
        restWoonplaatsMockMvc
            .perform(delete(ENTITY_API_URL_ID, woonplaats.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return woonplaatsRepository.count();
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

    protected Woonplaats getPersistedWoonplaats(Woonplaats woonplaats) {
        return woonplaatsRepository.findById(woonplaats.getId()).orElseThrow();
    }

    protected void assertPersistedWoonplaatsToMatchAllProperties(Woonplaats expectedWoonplaats) {
        assertWoonplaatsAllPropertiesEquals(expectedWoonplaats, getPersistedWoonplaats(expectedWoonplaats));
    }

    protected void assertPersistedWoonplaatsToMatchUpdatableProperties(Woonplaats expectedWoonplaats) {
        assertWoonplaatsAllUpdatablePropertiesEquals(expectedWoonplaats, getPersistedWoonplaats(expectedWoonplaats));
    }
}
