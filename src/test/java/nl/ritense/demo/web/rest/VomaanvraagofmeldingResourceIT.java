package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VomaanvraagofmeldingAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Vomaanvraagofmelding;
import nl.ritense.demo.repository.VomaanvraagofmeldingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VomaanvraagofmeldingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VomaanvraagofmeldingResourceIT {

    private static final String DEFAULT_ACTIVITEITEN = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITEITEN = "BBBBBBBBBB";

    private static final String DEFAULT_ADRES = "AAAAAAAAAA";
    private static final String UPDATED_ADRES = "BBBBBBBBBB";

    private static final String DEFAULT_BAGID = "AAAAAAAAAA";
    private static final String UPDATED_BAGID = "BBBBBBBBBB";

    private static final String DEFAULT_DOSSIERNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_DOSSIERNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_INTAKETYPE = "AAAAAAAAAA";
    private static final String UPDATED_INTAKETYPE = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_INTERNNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_KADASTRALEAANDUIDING = "AAAAAAAAAA";
    private static final String UPDATED_KADASTRALEAANDUIDING = "BBBBBBBBBB";

    private static final String DEFAULT_KENMERK = "AAAAAAAAAA";
    private static final String UPDATED_KENMERK = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vomaanvraagofmeldings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VomaanvraagofmeldingRepository vomaanvraagofmeldingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVomaanvraagofmeldingMockMvc;

    private Vomaanvraagofmelding vomaanvraagofmelding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vomaanvraagofmelding createEntity(EntityManager em) {
        Vomaanvraagofmelding vomaanvraagofmelding = new Vomaanvraagofmelding()
            .activiteiten(DEFAULT_ACTIVITEITEN)
            .adres(DEFAULT_ADRES)
            .bagid(DEFAULT_BAGID)
            .dossiernummer(DEFAULT_DOSSIERNUMMER)
            .intaketype(DEFAULT_INTAKETYPE)
            .internnummer(DEFAULT_INTERNNUMMER)
            .kadastraleaanduiding(DEFAULT_KADASTRALEAANDUIDING)
            .kenmerk(DEFAULT_KENMERK)
            .locatie(DEFAULT_LOCATIE)
            .locatieomschrijving(DEFAULT_LOCATIEOMSCHRIJVING)
            .toelichting(DEFAULT_TOELICHTING);
        return vomaanvraagofmelding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vomaanvraagofmelding createUpdatedEntity(EntityManager em) {
        Vomaanvraagofmelding vomaanvraagofmelding = new Vomaanvraagofmelding()
            .activiteiten(UPDATED_ACTIVITEITEN)
            .adres(UPDATED_ADRES)
            .bagid(UPDATED_BAGID)
            .dossiernummer(UPDATED_DOSSIERNUMMER)
            .intaketype(UPDATED_INTAKETYPE)
            .internnummer(UPDATED_INTERNNUMMER)
            .kadastraleaanduiding(UPDATED_KADASTRALEAANDUIDING)
            .kenmerk(UPDATED_KENMERK)
            .locatie(UPDATED_LOCATIE)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .toelichting(UPDATED_TOELICHTING);
        return vomaanvraagofmelding;
    }

    @BeforeEach
    public void initTest() {
        vomaanvraagofmelding = createEntity(em);
    }

    @Test
    @Transactional
    void createVomaanvraagofmelding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vomaanvraagofmelding
        var returnedVomaanvraagofmelding = om.readValue(
            restVomaanvraagofmeldingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vomaanvraagofmelding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vomaanvraagofmelding.class
        );

        // Validate the Vomaanvraagofmelding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVomaanvraagofmeldingUpdatableFieldsEquals(
            returnedVomaanvraagofmelding,
            getPersistedVomaanvraagofmelding(returnedVomaanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void createVomaanvraagofmeldingWithExistingId() throws Exception {
        // Create the Vomaanvraagofmelding with an existing ID
        vomaanvraagofmelding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVomaanvraagofmeldingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vomaanvraagofmelding)))
            .andExpect(status().isBadRequest());

        // Validate the Vomaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVomaanvraagofmeldings() throws Exception {
        // Initialize the database
        vomaanvraagofmeldingRepository.saveAndFlush(vomaanvraagofmelding);

        // Get all the vomaanvraagofmeldingList
        restVomaanvraagofmeldingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vomaanvraagofmelding.getId().intValue())))
            .andExpect(jsonPath("$.[*].activiteiten").value(hasItem(DEFAULT_ACTIVITEITEN)))
            .andExpect(jsonPath("$.[*].adres").value(hasItem(DEFAULT_ADRES)))
            .andExpect(jsonPath("$.[*].bagid").value(hasItem(DEFAULT_BAGID)))
            .andExpect(jsonPath("$.[*].dossiernummer").value(hasItem(DEFAULT_DOSSIERNUMMER)))
            .andExpect(jsonPath("$.[*].intaketype").value(hasItem(DEFAULT_INTAKETYPE)))
            .andExpect(jsonPath("$.[*].internnummer").value(hasItem(DEFAULT_INTERNNUMMER)))
            .andExpect(jsonPath("$.[*].kadastraleaanduiding").value(hasItem(DEFAULT_KADASTRALEAANDUIDING)))
            .andExpect(jsonPath("$.[*].kenmerk").value(hasItem(DEFAULT_KENMERK)))
            .andExpect(jsonPath("$.[*].locatie").value(hasItem(DEFAULT_LOCATIE)))
            .andExpect(jsonPath("$.[*].locatieomschrijving").value(hasItem(DEFAULT_LOCATIEOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)));
    }

    @Test
    @Transactional
    void getVomaanvraagofmelding() throws Exception {
        // Initialize the database
        vomaanvraagofmeldingRepository.saveAndFlush(vomaanvraagofmelding);

        // Get the vomaanvraagofmelding
        restVomaanvraagofmeldingMockMvc
            .perform(get(ENTITY_API_URL_ID, vomaanvraagofmelding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vomaanvraagofmelding.getId().intValue()))
            .andExpect(jsonPath("$.activiteiten").value(DEFAULT_ACTIVITEITEN))
            .andExpect(jsonPath("$.adres").value(DEFAULT_ADRES))
            .andExpect(jsonPath("$.bagid").value(DEFAULT_BAGID))
            .andExpect(jsonPath("$.dossiernummer").value(DEFAULT_DOSSIERNUMMER))
            .andExpect(jsonPath("$.intaketype").value(DEFAULT_INTAKETYPE))
            .andExpect(jsonPath("$.internnummer").value(DEFAULT_INTERNNUMMER))
            .andExpect(jsonPath("$.kadastraleaanduiding").value(DEFAULT_KADASTRALEAANDUIDING))
            .andExpect(jsonPath("$.kenmerk").value(DEFAULT_KENMERK))
            .andExpect(jsonPath("$.locatie").value(DEFAULT_LOCATIE))
            .andExpect(jsonPath("$.locatieomschrijving").value(DEFAULT_LOCATIEOMSCHRIJVING))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING));
    }

    @Test
    @Transactional
    void getNonExistingVomaanvraagofmelding() throws Exception {
        // Get the vomaanvraagofmelding
        restVomaanvraagofmeldingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVomaanvraagofmelding() throws Exception {
        // Initialize the database
        vomaanvraagofmeldingRepository.saveAndFlush(vomaanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vomaanvraagofmelding
        Vomaanvraagofmelding updatedVomaanvraagofmelding = vomaanvraagofmeldingRepository
            .findById(vomaanvraagofmelding.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedVomaanvraagofmelding are not directly saved in db
        em.detach(updatedVomaanvraagofmelding);
        updatedVomaanvraagofmelding
            .activiteiten(UPDATED_ACTIVITEITEN)
            .adres(UPDATED_ADRES)
            .bagid(UPDATED_BAGID)
            .dossiernummer(UPDATED_DOSSIERNUMMER)
            .intaketype(UPDATED_INTAKETYPE)
            .internnummer(UPDATED_INTERNNUMMER)
            .kadastraleaanduiding(UPDATED_KADASTRALEAANDUIDING)
            .kenmerk(UPDATED_KENMERK)
            .locatie(UPDATED_LOCATIE)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .toelichting(UPDATED_TOELICHTING);

        restVomaanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVomaanvraagofmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVomaanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Vomaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVomaanvraagofmeldingToMatchAllProperties(updatedVomaanvraagofmelding);
    }

    @Test
    @Transactional
    void putNonExistingVomaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vomaanvraagofmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVomaanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vomaanvraagofmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vomaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vomaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVomaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vomaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVomaanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vomaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vomaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVomaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vomaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVomaanvraagofmeldingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vomaanvraagofmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vomaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVomaanvraagofmeldingWithPatch() throws Exception {
        // Initialize the database
        vomaanvraagofmeldingRepository.saveAndFlush(vomaanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vomaanvraagofmelding using partial update
        Vomaanvraagofmelding partialUpdatedVomaanvraagofmelding = new Vomaanvraagofmelding();
        partialUpdatedVomaanvraagofmelding.setId(vomaanvraagofmelding.getId());

        partialUpdatedVomaanvraagofmelding
            .intaketype(UPDATED_INTAKETYPE)
            .internnummer(UPDATED_INTERNNUMMER)
            .kadastraleaanduiding(UPDATED_KADASTRALEAANDUIDING)
            .kenmerk(UPDATED_KENMERK)
            .locatie(UPDATED_LOCATIE)
            .toelichting(UPDATED_TOELICHTING);

        restVomaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVomaanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVomaanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Vomaanvraagofmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVomaanvraagofmeldingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVomaanvraagofmelding, vomaanvraagofmelding),
            getPersistedVomaanvraagofmelding(vomaanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void fullUpdateVomaanvraagofmeldingWithPatch() throws Exception {
        // Initialize the database
        vomaanvraagofmeldingRepository.saveAndFlush(vomaanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vomaanvraagofmelding using partial update
        Vomaanvraagofmelding partialUpdatedVomaanvraagofmelding = new Vomaanvraagofmelding();
        partialUpdatedVomaanvraagofmelding.setId(vomaanvraagofmelding.getId());

        partialUpdatedVomaanvraagofmelding
            .activiteiten(UPDATED_ACTIVITEITEN)
            .adres(UPDATED_ADRES)
            .bagid(UPDATED_BAGID)
            .dossiernummer(UPDATED_DOSSIERNUMMER)
            .intaketype(UPDATED_INTAKETYPE)
            .internnummer(UPDATED_INTERNNUMMER)
            .kadastraleaanduiding(UPDATED_KADASTRALEAANDUIDING)
            .kenmerk(UPDATED_KENMERK)
            .locatie(UPDATED_LOCATIE)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .toelichting(UPDATED_TOELICHTING);

        restVomaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVomaanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVomaanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Vomaanvraagofmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVomaanvraagofmeldingUpdatableFieldsEquals(
            partialUpdatedVomaanvraagofmelding,
            getPersistedVomaanvraagofmelding(partialUpdatedVomaanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVomaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vomaanvraagofmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVomaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vomaanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vomaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vomaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVomaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vomaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVomaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vomaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vomaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVomaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vomaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVomaanvraagofmeldingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vomaanvraagofmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vomaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVomaanvraagofmelding() throws Exception {
        // Initialize the database
        vomaanvraagofmeldingRepository.saveAndFlush(vomaanvraagofmelding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vomaanvraagofmelding
        restVomaanvraagofmeldingMockMvc
            .perform(delete(ENTITY_API_URL_ID, vomaanvraagofmelding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vomaanvraagofmeldingRepository.count();
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

    protected Vomaanvraagofmelding getPersistedVomaanvraagofmelding(Vomaanvraagofmelding vomaanvraagofmelding) {
        return vomaanvraagofmeldingRepository.findById(vomaanvraagofmelding.getId()).orElseThrow();
    }

    protected void assertPersistedVomaanvraagofmeldingToMatchAllProperties(Vomaanvraagofmelding expectedVomaanvraagofmelding) {
        assertVomaanvraagofmeldingAllPropertiesEquals(
            expectedVomaanvraagofmelding,
            getPersistedVomaanvraagofmelding(expectedVomaanvraagofmelding)
        );
    }

    protected void assertPersistedVomaanvraagofmeldingToMatchUpdatableProperties(Vomaanvraagofmelding expectedVomaanvraagofmelding) {
        assertVomaanvraagofmeldingAllUpdatablePropertiesEquals(
            expectedVomaanvraagofmelding,
            getPersistedVomaanvraagofmelding(expectedVomaanvraagofmelding)
        );
    }
}
