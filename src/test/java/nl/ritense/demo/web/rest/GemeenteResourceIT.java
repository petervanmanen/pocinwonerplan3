package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GemeenteAsserts.*;
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
import nl.ritense.demo.domain.Gemeente;
import nl.ritense.demo.repository.GemeenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GemeenteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GemeenteResourceIT {

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

    private static final String DEFAULT_GEMEENTECODE = "AAAAAAAAAA";
    private static final String UPDATED_GEMEENTECODE = "BBBBBBBBBB";

    private static final String DEFAULT_GEMEENTENAAM = "AAAAAAAAAA";
    private static final String UPDATED_GEMEENTENAAM = "BBBBBBBBBB";

    private static final String DEFAULT_GEMEENTENAAMNEN = "AAAAAAAAAA";
    private static final String UPDATED_GEMEENTENAAMNEN = "BBBBBBBBBB";

    private static final String DEFAULT_GEOMETRIE = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIE = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INONDERZOEK = false;
    private static final Boolean UPDATED_INONDERZOEK = true;

    private static final String DEFAULT_VERSIE = "AAAAAAAAAA";
    private static final String UPDATED_VERSIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gemeentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GemeenteRepository gemeenteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGemeenteMockMvc;

    private Gemeente gemeente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gemeente createEntity(EntityManager em) {
        Gemeente gemeente = new Gemeente()
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .datumingang(DEFAULT_DATUMINGANG)
            .geconstateerd(DEFAULT_GECONSTATEERD)
            .gemeentecode(DEFAULT_GEMEENTECODE)
            .gemeentenaam(DEFAULT_GEMEENTENAAM)
            .gemeentenaamnen(DEFAULT_GEMEENTENAAMNEN)
            .geometrie(DEFAULT_GEOMETRIE)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .inonderzoek(DEFAULT_INONDERZOEK)
            .versie(DEFAULT_VERSIE);
        return gemeente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gemeente createUpdatedEntity(EntityManager em) {
        Gemeente gemeente = new Gemeente()
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .gemeentecode(UPDATED_GEMEENTECODE)
            .gemeentenaam(UPDATED_GEMEENTENAAM)
            .gemeentenaamnen(UPDATED_GEMEENTENAAMNEN)
            .geometrie(UPDATED_GEOMETRIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .versie(UPDATED_VERSIE);
        return gemeente;
    }

    @BeforeEach
    public void initTest() {
        gemeente = createEntity(em);
    }

    @Test
    @Transactional
    void createGemeente() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Gemeente
        var returnedGemeente = om.readValue(
            restGemeenteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gemeente)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Gemeente.class
        );

        // Validate the Gemeente in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGemeenteUpdatableFieldsEquals(returnedGemeente, getPersistedGemeente(returnedGemeente));
    }

    @Test
    @Transactional
    void createGemeenteWithExistingId() throws Exception {
        // Create the Gemeente with an existing ID
        gemeente.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGemeenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gemeente)))
            .andExpect(status().isBadRequest());

        // Validate the Gemeente in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGemeentes() throws Exception {
        // Initialize the database
        gemeenteRepository.saveAndFlush(gemeente);

        // Get all the gemeenteList
        restGemeenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gemeente.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumingang").value(hasItem(DEFAULT_DATUMINGANG.toString())))
            .andExpect(jsonPath("$.[*].geconstateerd").value(hasItem(DEFAULT_GECONSTATEERD.booleanValue())))
            .andExpect(jsonPath("$.[*].gemeentecode").value(hasItem(DEFAULT_GEMEENTECODE)))
            .andExpect(jsonPath("$.[*].gemeentenaam").value(hasItem(DEFAULT_GEMEENTENAAM)))
            .andExpect(jsonPath("$.[*].gemeentenaamnen").value(hasItem(DEFAULT_GEMEENTENAAMNEN)))
            .andExpect(jsonPath("$.[*].geometrie").value(hasItem(DEFAULT_GEOMETRIE)))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].inonderzoek").value(hasItem(DEFAULT_INONDERZOEK.booleanValue())))
            .andExpect(jsonPath("$.[*].versie").value(hasItem(DEFAULT_VERSIE)));
    }

    @Test
    @Transactional
    void getGemeente() throws Exception {
        // Initialize the database
        gemeenteRepository.saveAndFlush(gemeente);

        // Get the gemeente
        restGemeenteMockMvc
            .perform(get(ENTITY_API_URL_ID, gemeente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gemeente.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumingang").value(DEFAULT_DATUMINGANG.toString()))
            .andExpect(jsonPath("$.geconstateerd").value(DEFAULT_GECONSTATEERD.booleanValue()))
            .andExpect(jsonPath("$.gemeentecode").value(DEFAULT_GEMEENTECODE))
            .andExpect(jsonPath("$.gemeentenaam").value(DEFAULT_GEMEENTENAAM))
            .andExpect(jsonPath("$.gemeentenaamnen").value(DEFAULT_GEMEENTENAAMNEN))
            .andExpect(jsonPath("$.geometrie").value(DEFAULT_GEOMETRIE))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.inonderzoek").value(DEFAULT_INONDERZOEK.booleanValue()))
            .andExpect(jsonPath("$.versie").value(DEFAULT_VERSIE));
    }

    @Test
    @Transactional
    void getNonExistingGemeente() throws Exception {
        // Get the gemeente
        restGemeenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGemeente() throws Exception {
        // Initialize the database
        gemeenteRepository.saveAndFlush(gemeente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gemeente
        Gemeente updatedGemeente = gemeenteRepository.findById(gemeente.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGemeente are not directly saved in db
        em.detach(updatedGemeente);
        updatedGemeente
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .gemeentecode(UPDATED_GEMEENTECODE)
            .gemeentenaam(UPDATED_GEMEENTENAAM)
            .gemeentenaamnen(UPDATED_GEMEENTENAAMNEN)
            .geometrie(UPDATED_GEOMETRIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .versie(UPDATED_VERSIE);

        restGemeenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGemeente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGemeente))
            )
            .andExpect(status().isOk());

        // Validate the Gemeente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGemeenteToMatchAllProperties(updatedGemeente);
    }

    @Test
    @Transactional
    void putNonExistingGemeente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemeente.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGemeenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gemeente.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gemeente))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gemeente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGemeente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemeente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemeenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gemeente))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gemeente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGemeente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemeente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemeenteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gemeente)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gemeente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGemeenteWithPatch() throws Exception {
        // Initialize the database
        gemeenteRepository.saveAndFlush(gemeente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gemeente using partial update
        Gemeente partialUpdatedGemeente = new Gemeente();
        partialUpdatedGemeente.setId(gemeente.getId());

        partialUpdatedGemeente
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .gemeentecode(UPDATED_GEMEENTECODE)
            .geometrie(UPDATED_GEOMETRIE)
            .versie(UPDATED_VERSIE);

        restGemeenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGemeente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGemeente))
            )
            .andExpect(status().isOk());

        // Validate the Gemeente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGemeenteUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedGemeente, gemeente), getPersistedGemeente(gemeente));
    }

    @Test
    @Transactional
    void fullUpdateGemeenteWithPatch() throws Exception {
        // Initialize the database
        gemeenteRepository.saveAndFlush(gemeente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gemeente using partial update
        Gemeente partialUpdatedGemeente = new Gemeente();
        partialUpdatedGemeente.setId(gemeente.getId());

        partialUpdatedGemeente
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .gemeentecode(UPDATED_GEMEENTECODE)
            .gemeentenaam(UPDATED_GEMEENTENAAM)
            .gemeentenaamnen(UPDATED_GEMEENTENAAMNEN)
            .geometrie(UPDATED_GEOMETRIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .versie(UPDATED_VERSIE);

        restGemeenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGemeente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGemeente))
            )
            .andExpect(status().isOk());

        // Validate the Gemeente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGemeenteUpdatableFieldsEquals(partialUpdatedGemeente, getPersistedGemeente(partialUpdatedGemeente));
    }

    @Test
    @Transactional
    void patchNonExistingGemeente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemeente.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGemeenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gemeente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gemeente))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gemeente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGemeente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemeente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemeenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gemeente))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gemeente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGemeente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemeente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemeenteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gemeente)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gemeente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGemeente() throws Exception {
        // Initialize the database
        gemeenteRepository.saveAndFlush(gemeente);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gemeente
        restGemeenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, gemeente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gemeenteRepository.count();
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

    protected Gemeente getPersistedGemeente(Gemeente gemeente) {
        return gemeenteRepository.findById(gemeente.getId()).orElseThrow();
    }

    protected void assertPersistedGemeenteToMatchAllProperties(Gemeente expectedGemeente) {
        assertGemeenteAllPropertiesEquals(expectedGemeente, getPersistedGemeente(expectedGemeente));
    }

    protected void assertPersistedGemeenteToMatchUpdatableProperties(Gemeente expectedGemeente) {
        assertGemeenteAllUpdatablePropertiesEquals(expectedGemeente, getPersistedGemeente(expectedGemeente));
    }
}
