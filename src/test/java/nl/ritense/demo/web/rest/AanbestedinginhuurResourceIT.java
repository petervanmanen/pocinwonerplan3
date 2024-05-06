package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AanbestedinginhuurAsserts.*;
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
import nl.ritense.demo.domain.Aanbestedinginhuur;
import nl.ritense.demo.repository.AanbestedinginhuurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AanbestedinginhuurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AanbestedinginhuurResourceIT {

    private static final String DEFAULT_AANVRAAGGESLOTEN = "AAAAAAAAAA";
    private static final String UPDATED_AANVRAAGGESLOTEN = "BBBBBBBBBB";

    private static final String DEFAULT_AANVRAAGNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_AANVRAAGNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMCREATIE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMCREATIE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMOPENINGKLUIS = "AAAAAAAAAA";
    private static final String UPDATED_DATUMOPENINGKLUIS = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMSLUITING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSLUITING = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMVERZENDING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMVERZENDING = "BBBBBBBBBB";

    private static final String DEFAULT_FASE = "AAAAAAAAAA";
    private static final String UPDATED_FASE = "BBBBBBBBBB";

    private static final String DEFAULT_HOOGSTETARIEF = "AAAAAAAAAA";
    private static final String UPDATED_HOOGSTETARIEF = "BBBBBBBBBB";

    private static final String DEFAULT_LAAGSTETARIEF = "AAAAAAAAAA";
    private static final String UPDATED_LAAGSTETARIEF = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_PERCEEL = "AAAAAAAAAA";
    private static final String UPDATED_PERCEEL = "BBBBBBBBBB";

    private static final String DEFAULT_PROCEDURE = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDURE = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECTNAAM = "AAAAAAAAAA";
    private static final String UPDATED_PROJECTNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECTREFERENTIE = "AAAAAAAAAA";
    private static final String UPDATED_PROJECTREFERENTIE = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLICATIE = "AAAAAAAAAA";
    private static final String UPDATED_PUBLICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENTIE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENTIE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TITEL = "AAAAAAAAAA";
    private static final String UPDATED_TITEL = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aanbestedinginhuurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AanbestedinginhuurRepository aanbestedinginhuurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAanbestedinginhuurMockMvc;

    private Aanbestedinginhuur aanbestedinginhuur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanbestedinginhuur createEntity(EntityManager em) {
        Aanbestedinginhuur aanbestedinginhuur = new Aanbestedinginhuur()
            .aanvraaggesloten(DEFAULT_AANVRAAGGESLOTEN)
            .aanvraagnummer(DEFAULT_AANVRAAGNUMMER)
            .datumcreatie(DEFAULT_DATUMCREATIE)
            .datumopeningkluis(DEFAULT_DATUMOPENINGKLUIS)
            .datumsluiting(DEFAULT_DATUMSLUITING)
            .datumverzending(DEFAULT_DATUMVERZENDING)
            .fase(DEFAULT_FASE)
            .hoogstetarief(DEFAULT_HOOGSTETARIEF)
            .laagstetarief(DEFAULT_LAAGSTETARIEF)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .perceel(DEFAULT_PERCEEL)
            .procedure(DEFAULT_PROCEDURE)
            .projectnaam(DEFAULT_PROJECTNAAM)
            .projectreferentie(DEFAULT_PROJECTREFERENTIE)
            .publicatie(DEFAULT_PUBLICATIE)
            .referentie(DEFAULT_REFERENTIE)
            .status(DEFAULT_STATUS)
            .titel(DEFAULT_TITEL)
            .type(DEFAULT_TYPE);
        return aanbestedinginhuur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanbestedinginhuur createUpdatedEntity(EntityManager em) {
        Aanbestedinginhuur aanbestedinginhuur = new Aanbestedinginhuur()
            .aanvraaggesloten(UPDATED_AANVRAAGGESLOTEN)
            .aanvraagnummer(UPDATED_AANVRAAGNUMMER)
            .datumcreatie(UPDATED_DATUMCREATIE)
            .datumopeningkluis(UPDATED_DATUMOPENINGKLUIS)
            .datumsluiting(UPDATED_DATUMSLUITING)
            .datumverzending(UPDATED_DATUMVERZENDING)
            .fase(UPDATED_FASE)
            .hoogstetarief(UPDATED_HOOGSTETARIEF)
            .laagstetarief(UPDATED_LAAGSTETARIEF)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .perceel(UPDATED_PERCEEL)
            .procedure(UPDATED_PROCEDURE)
            .projectnaam(UPDATED_PROJECTNAAM)
            .projectreferentie(UPDATED_PROJECTREFERENTIE)
            .publicatie(UPDATED_PUBLICATIE)
            .referentie(UPDATED_REFERENTIE)
            .status(UPDATED_STATUS)
            .titel(UPDATED_TITEL)
            .type(UPDATED_TYPE);
        return aanbestedinginhuur;
    }

    @BeforeEach
    public void initTest() {
        aanbestedinginhuur = createEntity(em);
    }

    @Test
    @Transactional
    void createAanbestedinginhuur() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aanbestedinginhuur
        var returnedAanbestedinginhuur = om.readValue(
            restAanbestedinginhuurMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanbestedinginhuur)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aanbestedinginhuur.class
        );

        // Validate the Aanbestedinginhuur in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAanbestedinginhuurUpdatableFieldsEquals(
            returnedAanbestedinginhuur,
            getPersistedAanbestedinginhuur(returnedAanbestedinginhuur)
        );
    }

    @Test
    @Transactional
    void createAanbestedinginhuurWithExistingId() throws Exception {
        // Create the Aanbestedinginhuur with an existing ID
        aanbestedinginhuur.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAanbestedinginhuurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanbestedinginhuur)))
            .andExpect(status().isBadRequest());

        // Validate the Aanbestedinginhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAanbestedinginhuurs() throws Exception {
        // Initialize the database
        aanbestedinginhuurRepository.saveAndFlush(aanbestedinginhuur);

        // Get all the aanbestedinginhuurList
        restAanbestedinginhuurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aanbestedinginhuur.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanvraaggesloten").value(hasItem(DEFAULT_AANVRAAGGESLOTEN)))
            .andExpect(jsonPath("$.[*].aanvraagnummer").value(hasItem(DEFAULT_AANVRAAGNUMMER)))
            .andExpect(jsonPath("$.[*].datumcreatie").value(hasItem(DEFAULT_DATUMCREATIE)))
            .andExpect(jsonPath("$.[*].datumopeningkluis").value(hasItem(DEFAULT_DATUMOPENINGKLUIS)))
            .andExpect(jsonPath("$.[*].datumsluiting").value(hasItem(DEFAULT_DATUMSLUITING)))
            .andExpect(jsonPath("$.[*].datumverzending").value(hasItem(DEFAULT_DATUMVERZENDING)))
            .andExpect(jsonPath("$.[*].fase").value(hasItem(DEFAULT_FASE)))
            .andExpect(jsonPath("$.[*].hoogstetarief").value(hasItem(DEFAULT_HOOGSTETARIEF)))
            .andExpect(jsonPath("$.[*].laagstetarief").value(hasItem(DEFAULT_LAAGSTETARIEF)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].perceel").value(hasItem(DEFAULT_PERCEEL)))
            .andExpect(jsonPath("$.[*].procedure").value(hasItem(DEFAULT_PROCEDURE)))
            .andExpect(jsonPath("$.[*].projectnaam").value(hasItem(DEFAULT_PROJECTNAAM)))
            .andExpect(jsonPath("$.[*].projectreferentie").value(hasItem(DEFAULT_PROJECTREFERENTIE)))
            .andExpect(jsonPath("$.[*].publicatie").value(hasItem(DEFAULT_PUBLICATIE)))
            .andExpect(jsonPath("$.[*].referentie").value(hasItem(DEFAULT_REFERENTIE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].titel").value(hasItem(DEFAULT_TITEL)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getAanbestedinginhuur() throws Exception {
        // Initialize the database
        aanbestedinginhuurRepository.saveAndFlush(aanbestedinginhuur);

        // Get the aanbestedinginhuur
        restAanbestedinginhuurMockMvc
            .perform(get(ENTITY_API_URL_ID, aanbestedinginhuur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aanbestedinginhuur.getId().intValue()))
            .andExpect(jsonPath("$.aanvraaggesloten").value(DEFAULT_AANVRAAGGESLOTEN))
            .andExpect(jsonPath("$.aanvraagnummer").value(DEFAULT_AANVRAAGNUMMER))
            .andExpect(jsonPath("$.datumcreatie").value(DEFAULT_DATUMCREATIE))
            .andExpect(jsonPath("$.datumopeningkluis").value(DEFAULT_DATUMOPENINGKLUIS))
            .andExpect(jsonPath("$.datumsluiting").value(DEFAULT_DATUMSLUITING))
            .andExpect(jsonPath("$.datumverzending").value(DEFAULT_DATUMVERZENDING))
            .andExpect(jsonPath("$.fase").value(DEFAULT_FASE))
            .andExpect(jsonPath("$.hoogstetarief").value(DEFAULT_HOOGSTETARIEF))
            .andExpect(jsonPath("$.laagstetarief").value(DEFAULT_LAAGSTETARIEF))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.perceel").value(DEFAULT_PERCEEL))
            .andExpect(jsonPath("$.procedure").value(DEFAULT_PROCEDURE))
            .andExpect(jsonPath("$.projectnaam").value(DEFAULT_PROJECTNAAM))
            .andExpect(jsonPath("$.projectreferentie").value(DEFAULT_PROJECTREFERENTIE))
            .andExpect(jsonPath("$.publicatie").value(DEFAULT_PUBLICATIE))
            .andExpect(jsonPath("$.referentie").value(DEFAULT_REFERENTIE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.titel").value(DEFAULT_TITEL))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingAanbestedinginhuur() throws Exception {
        // Get the aanbestedinginhuur
        restAanbestedinginhuurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAanbestedinginhuur() throws Exception {
        // Initialize the database
        aanbestedinginhuurRepository.saveAndFlush(aanbestedinginhuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanbestedinginhuur
        Aanbestedinginhuur updatedAanbestedinginhuur = aanbestedinginhuurRepository.findById(aanbestedinginhuur.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAanbestedinginhuur are not directly saved in db
        em.detach(updatedAanbestedinginhuur);
        updatedAanbestedinginhuur
            .aanvraaggesloten(UPDATED_AANVRAAGGESLOTEN)
            .aanvraagnummer(UPDATED_AANVRAAGNUMMER)
            .datumcreatie(UPDATED_DATUMCREATIE)
            .datumopeningkluis(UPDATED_DATUMOPENINGKLUIS)
            .datumsluiting(UPDATED_DATUMSLUITING)
            .datumverzending(UPDATED_DATUMVERZENDING)
            .fase(UPDATED_FASE)
            .hoogstetarief(UPDATED_HOOGSTETARIEF)
            .laagstetarief(UPDATED_LAAGSTETARIEF)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .perceel(UPDATED_PERCEEL)
            .procedure(UPDATED_PROCEDURE)
            .projectnaam(UPDATED_PROJECTNAAM)
            .projectreferentie(UPDATED_PROJECTREFERENTIE)
            .publicatie(UPDATED_PUBLICATIE)
            .referentie(UPDATED_REFERENTIE)
            .status(UPDATED_STATUS)
            .titel(UPDATED_TITEL)
            .type(UPDATED_TYPE);

        restAanbestedinginhuurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAanbestedinginhuur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAanbestedinginhuur))
            )
            .andExpect(status().isOk());

        // Validate the Aanbestedinginhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAanbestedinginhuurToMatchAllProperties(updatedAanbestedinginhuur);
    }

    @Test
    @Transactional
    void putNonExistingAanbestedinginhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanbestedinginhuur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanbestedinginhuurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aanbestedinginhuur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aanbestedinginhuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanbestedinginhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAanbestedinginhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanbestedinginhuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanbestedinginhuurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aanbestedinginhuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanbestedinginhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAanbestedinginhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanbestedinginhuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanbestedinginhuurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanbestedinginhuur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanbestedinginhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAanbestedinginhuurWithPatch() throws Exception {
        // Initialize the database
        aanbestedinginhuurRepository.saveAndFlush(aanbestedinginhuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanbestedinginhuur using partial update
        Aanbestedinginhuur partialUpdatedAanbestedinginhuur = new Aanbestedinginhuur();
        partialUpdatedAanbestedinginhuur.setId(aanbestedinginhuur.getId());

        partialUpdatedAanbestedinginhuur
            .aanvraagnummer(UPDATED_AANVRAAGNUMMER)
            .datumcreatie(UPDATED_DATUMCREATIE)
            .hoogstetarief(UPDATED_HOOGSTETARIEF)
            .procedure(UPDATED_PROCEDURE)
            .projectnaam(UPDATED_PROJECTNAAM)
            .projectreferentie(UPDATED_PROJECTREFERENTIE)
            .referentie(UPDATED_REFERENTIE)
            .status(UPDATED_STATUS)
            .titel(UPDATED_TITEL);

        restAanbestedinginhuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanbestedinginhuur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanbestedinginhuur))
            )
            .andExpect(status().isOk());

        // Validate the Aanbestedinginhuur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanbestedinginhuurUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAanbestedinginhuur, aanbestedinginhuur),
            getPersistedAanbestedinginhuur(aanbestedinginhuur)
        );
    }

    @Test
    @Transactional
    void fullUpdateAanbestedinginhuurWithPatch() throws Exception {
        // Initialize the database
        aanbestedinginhuurRepository.saveAndFlush(aanbestedinginhuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanbestedinginhuur using partial update
        Aanbestedinginhuur partialUpdatedAanbestedinginhuur = new Aanbestedinginhuur();
        partialUpdatedAanbestedinginhuur.setId(aanbestedinginhuur.getId());

        partialUpdatedAanbestedinginhuur
            .aanvraaggesloten(UPDATED_AANVRAAGGESLOTEN)
            .aanvraagnummer(UPDATED_AANVRAAGNUMMER)
            .datumcreatie(UPDATED_DATUMCREATIE)
            .datumopeningkluis(UPDATED_DATUMOPENINGKLUIS)
            .datumsluiting(UPDATED_DATUMSLUITING)
            .datumverzending(UPDATED_DATUMVERZENDING)
            .fase(UPDATED_FASE)
            .hoogstetarief(UPDATED_HOOGSTETARIEF)
            .laagstetarief(UPDATED_LAAGSTETARIEF)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .perceel(UPDATED_PERCEEL)
            .procedure(UPDATED_PROCEDURE)
            .projectnaam(UPDATED_PROJECTNAAM)
            .projectreferentie(UPDATED_PROJECTREFERENTIE)
            .publicatie(UPDATED_PUBLICATIE)
            .referentie(UPDATED_REFERENTIE)
            .status(UPDATED_STATUS)
            .titel(UPDATED_TITEL)
            .type(UPDATED_TYPE);

        restAanbestedinginhuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanbestedinginhuur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanbestedinginhuur))
            )
            .andExpect(status().isOk());

        // Validate the Aanbestedinginhuur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanbestedinginhuurUpdatableFieldsEquals(
            partialUpdatedAanbestedinginhuur,
            getPersistedAanbestedinginhuur(partialUpdatedAanbestedinginhuur)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAanbestedinginhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanbestedinginhuur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanbestedinginhuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aanbestedinginhuur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanbestedinginhuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanbestedinginhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAanbestedinginhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanbestedinginhuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanbestedinginhuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanbestedinginhuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanbestedinginhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAanbestedinginhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanbestedinginhuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanbestedinginhuurMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aanbestedinginhuur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanbestedinginhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAanbestedinginhuur() throws Exception {
        // Initialize the database
        aanbestedinginhuurRepository.saveAndFlush(aanbestedinginhuur);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aanbestedinginhuur
        restAanbestedinginhuurMockMvc
            .perform(delete(ENTITY_API_URL_ID, aanbestedinginhuur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aanbestedinginhuurRepository.count();
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

    protected Aanbestedinginhuur getPersistedAanbestedinginhuur(Aanbestedinginhuur aanbestedinginhuur) {
        return aanbestedinginhuurRepository.findById(aanbestedinginhuur.getId()).orElseThrow();
    }

    protected void assertPersistedAanbestedinginhuurToMatchAllProperties(Aanbestedinginhuur expectedAanbestedinginhuur) {
        assertAanbestedinginhuurAllPropertiesEquals(expectedAanbestedinginhuur, getPersistedAanbestedinginhuur(expectedAanbestedinginhuur));
    }

    protected void assertPersistedAanbestedinginhuurToMatchUpdatableProperties(Aanbestedinginhuur expectedAanbestedinginhuur) {
        assertAanbestedinginhuurAllUpdatablePropertiesEquals(
            expectedAanbestedinginhuur,
            getPersistedAanbestedinginhuur(expectedAanbestedinginhuur)
        );
    }
}
