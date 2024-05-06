package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VindplaatsAsserts.*;
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
import nl.ritense.demo.domain.Vindplaats;
import nl.ritense.demo.repository.VindplaatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VindplaatsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VindplaatsResourceIT {

    private static final String DEFAULT_AARD = "AAAAAAAAAA";
    private static final String UPDATED_AARD = "BBBBBBBBBB";

    private static final String DEFAULT_BEGINDATERING = "AAAAAAAAAA";
    private static final String UPDATED_BEGINDATERING = "BBBBBBBBBB";

    private static final String DEFAULT_BESCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_BESCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_BIBLIOGRAFIE = "AAAAAAAAAA";
    private static final String UPDATED_BIBLIOGRAFIE = "BBBBBBBBBB";

    private static final String DEFAULT_DATERING = "AAAAAAAAAA";
    private static final String UPDATED_DATERING = "BBBBBBBBBB";

    private static final String DEFAULT_DEPOT = "AAAAAAAAAA";
    private static final String UPDATED_DEPOT = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTATIE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTATIE = "BBBBBBBBBB";

    private static final String DEFAULT_EINDDATERING = "AAAAAAAAAA";
    private static final String UPDATED_EINDDATERING = "BBBBBBBBBB";

    private static final String DEFAULT_GEMEENTE = "AAAAAAAAAA";
    private static final String UPDATED_GEMEENTE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILIA = "AAAAAAAAAA";
    private static final String UPDATED_MOBILIA = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERZOEK = "AAAAAAAAAA";
    private static final String UPDATED_ONDERZOEK = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECTCODE = "AAAAAAAAAA";
    private static final String UPDATED_PROJECTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_VINDPLAATS = "AAAAAAAAAA";
    private static final String UPDATED_VINDPLAATS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vindplaats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VindplaatsRepository vindplaatsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVindplaatsMockMvc;

    private Vindplaats vindplaats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vindplaats createEntity(EntityManager em) {
        Vindplaats vindplaats = new Vindplaats()
            .aard(DEFAULT_AARD)
            .begindatering(DEFAULT_BEGINDATERING)
            .beschrijving(DEFAULT_BESCHRIJVING)
            .bibliografie(DEFAULT_BIBLIOGRAFIE)
            .datering(DEFAULT_DATERING)
            .depot(DEFAULT_DEPOT)
            .documentatie(DEFAULT_DOCUMENTATIE)
            .einddatering(DEFAULT_EINDDATERING)
            .gemeente(DEFAULT_GEMEENTE)
            .locatie(DEFAULT_LOCATIE)
            .mobilia(DEFAULT_MOBILIA)
            .onderzoek(DEFAULT_ONDERZOEK)
            .projectcode(DEFAULT_PROJECTCODE)
            .vindplaats(DEFAULT_VINDPLAATS);
        return vindplaats;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vindplaats createUpdatedEntity(EntityManager em) {
        Vindplaats vindplaats = new Vindplaats()
            .aard(UPDATED_AARD)
            .begindatering(UPDATED_BEGINDATERING)
            .beschrijving(UPDATED_BESCHRIJVING)
            .bibliografie(UPDATED_BIBLIOGRAFIE)
            .datering(UPDATED_DATERING)
            .depot(UPDATED_DEPOT)
            .documentatie(UPDATED_DOCUMENTATIE)
            .einddatering(UPDATED_EINDDATERING)
            .gemeente(UPDATED_GEMEENTE)
            .locatie(UPDATED_LOCATIE)
            .mobilia(UPDATED_MOBILIA)
            .onderzoek(UPDATED_ONDERZOEK)
            .projectcode(UPDATED_PROJECTCODE)
            .vindplaats(UPDATED_VINDPLAATS);
        return vindplaats;
    }

    @BeforeEach
    public void initTest() {
        vindplaats = createEntity(em);
    }

    @Test
    @Transactional
    void createVindplaats() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vindplaats
        var returnedVindplaats = om.readValue(
            restVindplaatsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vindplaats)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vindplaats.class
        );

        // Validate the Vindplaats in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVindplaatsUpdatableFieldsEquals(returnedVindplaats, getPersistedVindplaats(returnedVindplaats));
    }

    @Test
    @Transactional
    void createVindplaatsWithExistingId() throws Exception {
        // Create the Vindplaats with an existing ID
        vindplaats.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVindplaatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vindplaats)))
            .andExpect(status().isBadRequest());

        // Validate the Vindplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVindplaats() throws Exception {
        // Initialize the database
        vindplaatsRepository.saveAndFlush(vindplaats);

        // Get all the vindplaatsList
        restVindplaatsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vindplaats.getId().intValue())))
            .andExpect(jsonPath("$.[*].aard").value(hasItem(DEFAULT_AARD)))
            .andExpect(jsonPath("$.[*].begindatering").value(hasItem(DEFAULT_BEGINDATERING)))
            .andExpect(jsonPath("$.[*].beschrijving").value(hasItem(DEFAULT_BESCHRIJVING)))
            .andExpect(jsonPath("$.[*].bibliografie").value(hasItem(DEFAULT_BIBLIOGRAFIE)))
            .andExpect(jsonPath("$.[*].datering").value(hasItem(DEFAULT_DATERING)))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].documentatie").value(hasItem(DEFAULT_DOCUMENTATIE)))
            .andExpect(jsonPath("$.[*].einddatering").value(hasItem(DEFAULT_EINDDATERING)))
            .andExpect(jsonPath("$.[*].gemeente").value(hasItem(DEFAULT_GEMEENTE)))
            .andExpect(jsonPath("$.[*].locatie").value(hasItem(DEFAULT_LOCATIE)))
            .andExpect(jsonPath("$.[*].mobilia").value(hasItem(DEFAULT_MOBILIA)))
            .andExpect(jsonPath("$.[*].onderzoek").value(hasItem(DEFAULT_ONDERZOEK)))
            .andExpect(jsonPath("$.[*].projectcode").value(hasItem(DEFAULT_PROJECTCODE)))
            .andExpect(jsonPath("$.[*].vindplaats").value(hasItem(DEFAULT_VINDPLAATS)));
    }

    @Test
    @Transactional
    void getVindplaats() throws Exception {
        // Initialize the database
        vindplaatsRepository.saveAndFlush(vindplaats);

        // Get the vindplaats
        restVindplaatsMockMvc
            .perform(get(ENTITY_API_URL_ID, vindplaats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vindplaats.getId().intValue()))
            .andExpect(jsonPath("$.aard").value(DEFAULT_AARD))
            .andExpect(jsonPath("$.begindatering").value(DEFAULT_BEGINDATERING))
            .andExpect(jsonPath("$.beschrijving").value(DEFAULT_BESCHRIJVING))
            .andExpect(jsonPath("$.bibliografie").value(DEFAULT_BIBLIOGRAFIE))
            .andExpect(jsonPath("$.datering").value(DEFAULT_DATERING))
            .andExpect(jsonPath("$.depot").value(DEFAULT_DEPOT))
            .andExpect(jsonPath("$.documentatie").value(DEFAULT_DOCUMENTATIE))
            .andExpect(jsonPath("$.einddatering").value(DEFAULT_EINDDATERING))
            .andExpect(jsonPath("$.gemeente").value(DEFAULT_GEMEENTE))
            .andExpect(jsonPath("$.locatie").value(DEFAULT_LOCATIE))
            .andExpect(jsonPath("$.mobilia").value(DEFAULT_MOBILIA))
            .andExpect(jsonPath("$.onderzoek").value(DEFAULT_ONDERZOEK))
            .andExpect(jsonPath("$.projectcode").value(DEFAULT_PROJECTCODE))
            .andExpect(jsonPath("$.vindplaats").value(DEFAULT_VINDPLAATS));
    }

    @Test
    @Transactional
    void getNonExistingVindplaats() throws Exception {
        // Get the vindplaats
        restVindplaatsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVindplaats() throws Exception {
        // Initialize the database
        vindplaatsRepository.saveAndFlush(vindplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vindplaats
        Vindplaats updatedVindplaats = vindplaatsRepository.findById(vindplaats.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVindplaats are not directly saved in db
        em.detach(updatedVindplaats);
        updatedVindplaats
            .aard(UPDATED_AARD)
            .begindatering(UPDATED_BEGINDATERING)
            .beschrijving(UPDATED_BESCHRIJVING)
            .bibliografie(UPDATED_BIBLIOGRAFIE)
            .datering(UPDATED_DATERING)
            .depot(UPDATED_DEPOT)
            .documentatie(UPDATED_DOCUMENTATIE)
            .einddatering(UPDATED_EINDDATERING)
            .gemeente(UPDATED_GEMEENTE)
            .locatie(UPDATED_LOCATIE)
            .mobilia(UPDATED_MOBILIA)
            .onderzoek(UPDATED_ONDERZOEK)
            .projectcode(UPDATED_PROJECTCODE)
            .vindplaats(UPDATED_VINDPLAATS);

        restVindplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVindplaats.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVindplaats))
            )
            .andExpect(status().isOk());

        // Validate the Vindplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVindplaatsToMatchAllProperties(updatedVindplaats);
    }

    @Test
    @Transactional
    void putNonExistingVindplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vindplaats.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVindplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vindplaats.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vindplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vindplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVindplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vindplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVindplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vindplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vindplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVindplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vindplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVindplaatsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vindplaats)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vindplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVindplaatsWithPatch() throws Exception {
        // Initialize the database
        vindplaatsRepository.saveAndFlush(vindplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vindplaats using partial update
        Vindplaats partialUpdatedVindplaats = new Vindplaats();
        partialUpdatedVindplaats.setId(vindplaats.getId());

        partialUpdatedVindplaats
            .aard(UPDATED_AARD)
            .begindatering(UPDATED_BEGINDATERING)
            .einddatering(UPDATED_EINDDATERING)
            .locatie(UPDATED_LOCATIE)
            .mobilia(UPDATED_MOBILIA)
            .projectcode(UPDATED_PROJECTCODE);

        restVindplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVindplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVindplaats))
            )
            .andExpect(status().isOk());

        // Validate the Vindplaats in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVindplaatsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVindplaats, vindplaats),
            getPersistedVindplaats(vindplaats)
        );
    }

    @Test
    @Transactional
    void fullUpdateVindplaatsWithPatch() throws Exception {
        // Initialize the database
        vindplaatsRepository.saveAndFlush(vindplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vindplaats using partial update
        Vindplaats partialUpdatedVindplaats = new Vindplaats();
        partialUpdatedVindplaats.setId(vindplaats.getId());

        partialUpdatedVindplaats
            .aard(UPDATED_AARD)
            .begindatering(UPDATED_BEGINDATERING)
            .beschrijving(UPDATED_BESCHRIJVING)
            .bibliografie(UPDATED_BIBLIOGRAFIE)
            .datering(UPDATED_DATERING)
            .depot(UPDATED_DEPOT)
            .documentatie(UPDATED_DOCUMENTATIE)
            .einddatering(UPDATED_EINDDATERING)
            .gemeente(UPDATED_GEMEENTE)
            .locatie(UPDATED_LOCATIE)
            .mobilia(UPDATED_MOBILIA)
            .onderzoek(UPDATED_ONDERZOEK)
            .projectcode(UPDATED_PROJECTCODE)
            .vindplaats(UPDATED_VINDPLAATS);

        restVindplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVindplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVindplaats))
            )
            .andExpect(status().isOk());

        // Validate the Vindplaats in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVindplaatsUpdatableFieldsEquals(partialUpdatedVindplaats, getPersistedVindplaats(partialUpdatedVindplaats));
    }

    @Test
    @Transactional
    void patchNonExistingVindplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vindplaats.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVindplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vindplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vindplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vindplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVindplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vindplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVindplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vindplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vindplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVindplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vindplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVindplaatsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vindplaats)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vindplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVindplaats() throws Exception {
        // Initialize the database
        vindplaatsRepository.saveAndFlush(vindplaats);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vindplaats
        restVindplaatsMockMvc
            .perform(delete(ENTITY_API_URL_ID, vindplaats.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vindplaatsRepository.count();
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

    protected Vindplaats getPersistedVindplaats(Vindplaats vindplaats) {
        return vindplaatsRepository.findById(vindplaats.getId()).orElseThrow();
    }

    protected void assertPersistedVindplaatsToMatchAllProperties(Vindplaats expectedVindplaats) {
        assertVindplaatsAllPropertiesEquals(expectedVindplaats, getPersistedVindplaats(expectedVindplaats));
    }

    protected void assertPersistedVindplaatsToMatchUpdatableProperties(Vindplaats expectedVindplaats) {
        assertVindplaatsAllUpdatablePropertiesEquals(expectedVindplaats, getPersistedVindplaats(expectedVindplaats));
    }
}
