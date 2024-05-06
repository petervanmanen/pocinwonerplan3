package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LocatieonroerendezaakAsserts.*;
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
import nl.ritense.demo.domain.Locatieonroerendezaak;
import nl.ritense.demo.repository.LocatieonroerendezaakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LocatieonroerendezaakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocatieonroerendezaakResourceIT {

    private static final String DEFAULT_ADRESTYPE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CULTUURCODEBEBOUWD = "AAAAAAAAAA";
    private static final String UPDATED_CULTUURCODEBEBOUWD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEOMETRIE = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/locatieonroerendezaaks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LocatieonroerendezaakRepository locatieonroerendezaakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocatieonroerendezaakMockMvc;

    private Locatieonroerendezaak locatieonroerendezaak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locatieonroerendezaak createEntity(EntityManager em) {
        Locatieonroerendezaak locatieonroerendezaak = new Locatieonroerendezaak()
            .adrestype(DEFAULT_ADRESTYPE)
            .cultuurcodebebouwd(DEFAULT_CULTUURCODEBEBOUWD)
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .geometrie(DEFAULT_GEOMETRIE)
            .locatieomschrijving(DEFAULT_LOCATIEOMSCHRIJVING);
        return locatieonroerendezaak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locatieonroerendezaak createUpdatedEntity(EntityManager em) {
        Locatieonroerendezaak locatieonroerendezaak = new Locatieonroerendezaak()
            .adrestype(UPDATED_ADRESTYPE)
            .cultuurcodebebouwd(UPDATED_CULTUURCODEBEBOUWD)
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .geometrie(UPDATED_GEOMETRIE)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING);
        return locatieonroerendezaak;
    }

    @BeforeEach
    public void initTest() {
        locatieonroerendezaak = createEntity(em);
    }

    @Test
    @Transactional
    void createLocatieonroerendezaak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Locatieonroerendezaak
        var returnedLocatieonroerendezaak = om.readValue(
            restLocatieonroerendezaakMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locatieonroerendezaak)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Locatieonroerendezaak.class
        );

        // Validate the Locatieonroerendezaak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLocatieonroerendezaakUpdatableFieldsEquals(
            returnedLocatieonroerendezaak,
            getPersistedLocatieonroerendezaak(returnedLocatieonroerendezaak)
        );
    }

    @Test
    @Transactional
    void createLocatieonroerendezaakWithExistingId() throws Exception {
        // Create the Locatieonroerendezaak with an existing ID
        locatieonroerendezaak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocatieonroerendezaakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locatieonroerendezaak)))
            .andExpect(status().isBadRequest());

        // Validate the Locatieonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLocatieonroerendezaaks() throws Exception {
        // Initialize the database
        locatieonroerendezaakRepository.saveAndFlush(locatieonroerendezaak);

        // Get all the locatieonroerendezaakList
        restLocatieonroerendezaakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locatieonroerendezaak.getId().intValue())))
            .andExpect(jsonPath("$.[*].adrestype").value(hasItem(DEFAULT_ADRESTYPE)))
            .andExpect(jsonPath("$.[*].cultuurcodebebouwd").value(hasItem(DEFAULT_CULTUURCODEBEBOUWD)))
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].geometrie").value(hasItem(DEFAULT_GEOMETRIE)))
            .andExpect(jsonPath("$.[*].locatieomschrijving").value(hasItem(DEFAULT_LOCATIEOMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getLocatieonroerendezaak() throws Exception {
        // Initialize the database
        locatieonroerendezaakRepository.saveAndFlush(locatieonroerendezaak);

        // Get the locatieonroerendezaak
        restLocatieonroerendezaakMockMvc
            .perform(get(ENTITY_API_URL_ID, locatieonroerendezaak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locatieonroerendezaak.getId().intValue()))
            .andExpect(jsonPath("$.adrestype").value(DEFAULT_ADRESTYPE))
            .andExpect(jsonPath("$.cultuurcodebebouwd").value(DEFAULT_CULTUURCODEBEBOUWD))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.geometrie").value(DEFAULT_GEOMETRIE))
            .andExpect(jsonPath("$.locatieomschrijving").value(DEFAULT_LOCATIEOMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingLocatieonroerendezaak() throws Exception {
        // Get the locatieonroerendezaak
        restLocatieonroerendezaakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocatieonroerendezaak() throws Exception {
        // Initialize the database
        locatieonroerendezaakRepository.saveAndFlush(locatieonroerendezaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locatieonroerendezaak
        Locatieonroerendezaak updatedLocatieonroerendezaak = locatieonroerendezaakRepository
            .findById(locatieonroerendezaak.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedLocatieonroerendezaak are not directly saved in db
        em.detach(updatedLocatieonroerendezaak);
        updatedLocatieonroerendezaak
            .adrestype(UPDATED_ADRESTYPE)
            .cultuurcodebebouwd(UPDATED_CULTUURCODEBEBOUWD)
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .geometrie(UPDATED_GEOMETRIE)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING);

        restLocatieonroerendezaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocatieonroerendezaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLocatieonroerendezaak))
            )
            .andExpect(status().isOk());

        // Validate the Locatieonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLocatieonroerendezaakToMatchAllProperties(updatedLocatieonroerendezaak);
    }

    @Test
    @Transactional
    void putNonExistingLocatieonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieonroerendezaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocatieonroerendezaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locatieonroerendezaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locatieonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatieonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocatieonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieonroerendezaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatieonroerendezaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locatieonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatieonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocatieonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieonroerendezaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatieonroerendezaakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locatieonroerendezaak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locatieonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocatieonroerendezaakWithPatch() throws Exception {
        // Initialize the database
        locatieonroerendezaakRepository.saveAndFlush(locatieonroerendezaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locatieonroerendezaak using partial update
        Locatieonroerendezaak partialUpdatedLocatieonroerendezaak = new Locatieonroerendezaak();
        partialUpdatedLocatieonroerendezaak.setId(locatieonroerendezaak.getId());

        partialUpdatedLocatieonroerendezaak
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING);

        restLocatieonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocatieonroerendezaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocatieonroerendezaak))
            )
            .andExpect(status().isOk());

        // Validate the Locatieonroerendezaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocatieonroerendezaakUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLocatieonroerendezaak, locatieonroerendezaak),
            getPersistedLocatieonroerendezaak(locatieonroerendezaak)
        );
    }

    @Test
    @Transactional
    void fullUpdateLocatieonroerendezaakWithPatch() throws Exception {
        // Initialize the database
        locatieonroerendezaakRepository.saveAndFlush(locatieonroerendezaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locatieonroerendezaak using partial update
        Locatieonroerendezaak partialUpdatedLocatieonroerendezaak = new Locatieonroerendezaak();
        partialUpdatedLocatieonroerendezaak.setId(locatieonroerendezaak.getId());

        partialUpdatedLocatieonroerendezaak
            .adrestype(UPDATED_ADRESTYPE)
            .cultuurcodebebouwd(UPDATED_CULTUURCODEBEBOUWD)
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .geometrie(UPDATED_GEOMETRIE)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING);

        restLocatieonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocatieonroerendezaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocatieonroerendezaak))
            )
            .andExpect(status().isOk());

        // Validate the Locatieonroerendezaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocatieonroerendezaakUpdatableFieldsEquals(
            partialUpdatedLocatieonroerendezaak,
            getPersistedLocatieonroerendezaak(partialUpdatedLocatieonroerendezaak)
        );
    }

    @Test
    @Transactional
    void patchNonExistingLocatieonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieonroerendezaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocatieonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locatieonroerendezaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locatieonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatieonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocatieonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieonroerendezaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatieonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locatieonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatieonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocatieonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieonroerendezaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatieonroerendezaakMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(locatieonroerendezaak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locatieonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocatieonroerendezaak() throws Exception {
        // Initialize the database
        locatieonroerendezaakRepository.saveAndFlush(locatieonroerendezaak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the locatieonroerendezaak
        restLocatieonroerendezaakMockMvc
            .perform(delete(ENTITY_API_URL_ID, locatieonroerendezaak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return locatieonroerendezaakRepository.count();
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

    protected Locatieonroerendezaak getPersistedLocatieonroerendezaak(Locatieonroerendezaak locatieonroerendezaak) {
        return locatieonroerendezaakRepository.findById(locatieonroerendezaak.getId()).orElseThrow();
    }

    protected void assertPersistedLocatieonroerendezaakToMatchAllProperties(Locatieonroerendezaak expectedLocatieonroerendezaak) {
        assertLocatieonroerendezaakAllPropertiesEquals(
            expectedLocatieonroerendezaak,
            getPersistedLocatieonroerendezaak(expectedLocatieonroerendezaak)
        );
    }

    protected void assertPersistedLocatieonroerendezaakToMatchUpdatableProperties(Locatieonroerendezaak expectedLocatieonroerendezaak) {
        assertLocatieonroerendezaakAllUpdatablePropertiesEquals(
            expectedLocatieonroerendezaak,
            getPersistedLocatieonroerendezaak(expectedLocatieonroerendezaak)
        );
    }
}
