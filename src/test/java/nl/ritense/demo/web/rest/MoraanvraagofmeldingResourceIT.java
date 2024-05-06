package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MoraanvraagofmeldingAsserts.*;
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
import nl.ritense.demo.domain.Moraanvraagofmelding;
import nl.ritense.demo.repository.MoraanvraagofmeldingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MoraanvraagofmeldingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MoraanvraagofmeldingResourceIT {

    private static final String DEFAULT_CROW = "AAAAAAAAAA";
    private static final String UPDATED_CROW = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_MELDINGOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_MELDINGOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_MELDINGTEKST = "AAAAAAAAAA";
    private static final String UPDATED_MELDINGTEKST = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/moraanvraagofmeldings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MoraanvraagofmeldingRepository moraanvraagofmeldingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMoraanvraagofmeldingMockMvc;

    private Moraanvraagofmelding moraanvraagofmelding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Moraanvraagofmelding createEntity(EntityManager em) {
        Moraanvraagofmelding moraanvraagofmelding = new Moraanvraagofmelding()
            .crow(DEFAULT_CROW)
            .locatie(DEFAULT_LOCATIE)
            .locatieomschrijving(DEFAULT_LOCATIEOMSCHRIJVING)
            .meldingomschrijving(DEFAULT_MELDINGOMSCHRIJVING)
            .meldingtekst(DEFAULT_MELDINGTEKST);
        return moraanvraagofmelding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Moraanvraagofmelding createUpdatedEntity(EntityManager em) {
        Moraanvraagofmelding moraanvraagofmelding = new Moraanvraagofmelding()
            .crow(UPDATED_CROW)
            .locatie(UPDATED_LOCATIE)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .meldingomschrijving(UPDATED_MELDINGOMSCHRIJVING)
            .meldingtekst(UPDATED_MELDINGTEKST);
        return moraanvraagofmelding;
    }

    @BeforeEach
    public void initTest() {
        moraanvraagofmelding = createEntity(em);
    }

    @Test
    @Transactional
    void createMoraanvraagofmelding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Moraanvraagofmelding
        var returnedMoraanvraagofmelding = om.readValue(
            restMoraanvraagofmeldingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(moraanvraagofmelding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Moraanvraagofmelding.class
        );

        // Validate the Moraanvraagofmelding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMoraanvraagofmeldingUpdatableFieldsEquals(
            returnedMoraanvraagofmelding,
            getPersistedMoraanvraagofmelding(returnedMoraanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void createMoraanvraagofmeldingWithExistingId() throws Exception {
        // Create the Moraanvraagofmelding with an existing ID
        moraanvraagofmelding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoraanvraagofmeldingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(moraanvraagofmelding)))
            .andExpect(status().isBadRequest());

        // Validate the Moraanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMoraanvraagofmeldings() throws Exception {
        // Initialize the database
        moraanvraagofmeldingRepository.saveAndFlush(moraanvraagofmelding);

        // Get all the moraanvraagofmeldingList
        restMoraanvraagofmeldingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moraanvraagofmelding.getId().intValue())))
            .andExpect(jsonPath("$.[*].crow").value(hasItem(DEFAULT_CROW)))
            .andExpect(jsonPath("$.[*].locatie").value(hasItem(DEFAULT_LOCATIE)))
            .andExpect(jsonPath("$.[*].locatieomschrijving").value(hasItem(DEFAULT_LOCATIEOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].meldingomschrijving").value(hasItem(DEFAULT_MELDINGOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].meldingtekst").value(hasItem(DEFAULT_MELDINGTEKST)));
    }

    @Test
    @Transactional
    void getMoraanvraagofmelding() throws Exception {
        // Initialize the database
        moraanvraagofmeldingRepository.saveAndFlush(moraanvraagofmelding);

        // Get the moraanvraagofmelding
        restMoraanvraagofmeldingMockMvc
            .perform(get(ENTITY_API_URL_ID, moraanvraagofmelding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(moraanvraagofmelding.getId().intValue()))
            .andExpect(jsonPath("$.crow").value(DEFAULT_CROW))
            .andExpect(jsonPath("$.locatie").value(DEFAULT_LOCATIE))
            .andExpect(jsonPath("$.locatieomschrijving").value(DEFAULT_LOCATIEOMSCHRIJVING))
            .andExpect(jsonPath("$.meldingomschrijving").value(DEFAULT_MELDINGOMSCHRIJVING))
            .andExpect(jsonPath("$.meldingtekst").value(DEFAULT_MELDINGTEKST));
    }

    @Test
    @Transactional
    void getNonExistingMoraanvraagofmelding() throws Exception {
        // Get the moraanvraagofmelding
        restMoraanvraagofmeldingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMoraanvraagofmelding() throws Exception {
        // Initialize the database
        moraanvraagofmeldingRepository.saveAndFlush(moraanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the moraanvraagofmelding
        Moraanvraagofmelding updatedMoraanvraagofmelding = moraanvraagofmeldingRepository
            .findById(moraanvraagofmelding.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedMoraanvraagofmelding are not directly saved in db
        em.detach(updatedMoraanvraagofmelding);
        updatedMoraanvraagofmelding
            .crow(UPDATED_CROW)
            .locatie(UPDATED_LOCATIE)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .meldingomschrijving(UPDATED_MELDINGOMSCHRIJVING)
            .meldingtekst(UPDATED_MELDINGTEKST);

        restMoraanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMoraanvraagofmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMoraanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Moraanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMoraanvraagofmeldingToMatchAllProperties(updatedMoraanvraagofmelding);
    }

    @Test
    @Transactional
    void putNonExistingMoraanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moraanvraagofmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoraanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moraanvraagofmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(moraanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Moraanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMoraanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moraanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoraanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(moraanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Moraanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMoraanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moraanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoraanvraagofmeldingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(moraanvraagofmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Moraanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMoraanvraagofmeldingWithPatch() throws Exception {
        // Initialize the database
        moraanvraagofmeldingRepository.saveAndFlush(moraanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the moraanvraagofmelding using partial update
        Moraanvraagofmelding partialUpdatedMoraanvraagofmelding = new Moraanvraagofmelding();
        partialUpdatedMoraanvraagofmelding.setId(moraanvraagofmelding.getId());

        partialUpdatedMoraanvraagofmelding.crow(UPDATED_CROW).locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING);

        restMoraanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMoraanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMoraanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Moraanvraagofmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMoraanvraagofmeldingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMoraanvraagofmelding, moraanvraagofmelding),
            getPersistedMoraanvraagofmelding(moraanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void fullUpdateMoraanvraagofmeldingWithPatch() throws Exception {
        // Initialize the database
        moraanvraagofmeldingRepository.saveAndFlush(moraanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the moraanvraagofmelding using partial update
        Moraanvraagofmelding partialUpdatedMoraanvraagofmelding = new Moraanvraagofmelding();
        partialUpdatedMoraanvraagofmelding.setId(moraanvraagofmelding.getId());

        partialUpdatedMoraanvraagofmelding
            .crow(UPDATED_CROW)
            .locatie(UPDATED_LOCATIE)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .meldingomschrijving(UPDATED_MELDINGOMSCHRIJVING)
            .meldingtekst(UPDATED_MELDINGTEKST);

        restMoraanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMoraanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMoraanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Moraanvraagofmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMoraanvraagofmeldingUpdatableFieldsEquals(
            partialUpdatedMoraanvraagofmelding,
            getPersistedMoraanvraagofmelding(partialUpdatedMoraanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMoraanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moraanvraagofmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoraanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, moraanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(moraanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Moraanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMoraanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moraanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoraanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(moraanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Moraanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMoraanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moraanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoraanvraagofmeldingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(moraanvraagofmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Moraanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMoraanvraagofmelding() throws Exception {
        // Initialize the database
        moraanvraagofmeldingRepository.saveAndFlush(moraanvraagofmelding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the moraanvraagofmelding
        restMoraanvraagofmeldingMockMvc
            .perform(delete(ENTITY_API_URL_ID, moraanvraagofmelding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return moraanvraagofmeldingRepository.count();
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

    protected Moraanvraagofmelding getPersistedMoraanvraagofmelding(Moraanvraagofmelding moraanvraagofmelding) {
        return moraanvraagofmeldingRepository.findById(moraanvraagofmelding.getId()).orElseThrow();
    }

    protected void assertPersistedMoraanvraagofmeldingToMatchAllProperties(Moraanvraagofmelding expectedMoraanvraagofmelding) {
        assertMoraanvraagofmeldingAllPropertiesEquals(
            expectedMoraanvraagofmelding,
            getPersistedMoraanvraagofmelding(expectedMoraanvraagofmelding)
        );
    }

    protected void assertPersistedMoraanvraagofmeldingToMatchUpdatableProperties(Moraanvraagofmelding expectedMoraanvraagofmelding) {
        assertMoraanvraagofmeldingAllUpdatablePropertiesEquals(
            expectedMoraanvraagofmelding,
            getPersistedMoraanvraagofmelding(expectedMoraanvraagofmelding)
        );
    }
}
