package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KlantcontactAsserts.*;
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
import nl.ritense.demo.domain.Klantcontact;
import nl.ritense.demo.repository.KlantcontactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KlantcontactResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KlantcontactResourceIT {

    private static final String DEFAULT_EINDTIJD = "AAAAAAAAAA";
    private static final String UPDATED_EINDTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_KANAAL = "AAAAAAAAAA";
    private static final String UPDATED_KANAAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOTITIE = "AAAAAAAAAA";
    private static final String UPDATED_NOTITIE = "BBBBBBBBBB";

    private static final String DEFAULT_STARTTIJD = "AAAAAAAAAA";
    private static final String UPDATED_STARTTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_TIJDSDUUR = "AAAAAAAAAA";
    private static final String UPDATED_TIJDSDUUR = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String DEFAULT_WACHTTIJDTOTAAL = "AAAAAAAAAA";
    private static final String UPDATED_WACHTTIJDTOTAAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/klantcontacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KlantcontactRepository klantcontactRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKlantcontactMockMvc;

    private Klantcontact klantcontact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Klantcontact createEntity(EntityManager em) {
        Klantcontact klantcontact = new Klantcontact()
            .eindtijd(DEFAULT_EINDTIJD)
            .kanaal(DEFAULT_KANAAL)
            .notitie(DEFAULT_NOTITIE)
            .starttijd(DEFAULT_STARTTIJD)
            .tijdsduur(DEFAULT_TIJDSDUUR)
            .toelichting(DEFAULT_TOELICHTING)
            .wachttijdtotaal(DEFAULT_WACHTTIJDTOTAAL);
        return klantcontact;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Klantcontact createUpdatedEntity(EntityManager em) {
        Klantcontact klantcontact = new Klantcontact()
            .eindtijd(UPDATED_EINDTIJD)
            .kanaal(UPDATED_KANAAL)
            .notitie(UPDATED_NOTITIE)
            .starttijd(UPDATED_STARTTIJD)
            .tijdsduur(UPDATED_TIJDSDUUR)
            .toelichting(UPDATED_TOELICHTING)
            .wachttijdtotaal(UPDATED_WACHTTIJDTOTAAL);
        return klantcontact;
    }

    @BeforeEach
    public void initTest() {
        klantcontact = createEntity(em);
    }

    @Test
    @Transactional
    void createKlantcontact() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Klantcontact
        var returnedKlantcontact = om.readValue(
            restKlantcontactMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(klantcontact)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Klantcontact.class
        );

        // Validate the Klantcontact in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKlantcontactUpdatableFieldsEquals(returnedKlantcontact, getPersistedKlantcontact(returnedKlantcontact));
    }

    @Test
    @Transactional
    void createKlantcontactWithExistingId() throws Exception {
        // Create the Klantcontact with an existing ID
        klantcontact.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKlantcontactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(klantcontact)))
            .andExpect(status().isBadRequest());

        // Validate the Klantcontact in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKlantcontacts() throws Exception {
        // Initialize the database
        klantcontactRepository.saveAndFlush(klantcontact);

        // Get all the klantcontactList
        restKlantcontactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(klantcontact.getId().intValue())))
            .andExpect(jsonPath("$.[*].eindtijd").value(hasItem(DEFAULT_EINDTIJD)))
            .andExpect(jsonPath("$.[*].kanaal").value(hasItem(DEFAULT_KANAAL)))
            .andExpect(jsonPath("$.[*].notitie").value(hasItem(DEFAULT_NOTITIE)))
            .andExpect(jsonPath("$.[*].starttijd").value(hasItem(DEFAULT_STARTTIJD)))
            .andExpect(jsonPath("$.[*].tijdsduur").value(hasItem(DEFAULT_TIJDSDUUR)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)))
            .andExpect(jsonPath("$.[*].wachttijdtotaal").value(hasItem(DEFAULT_WACHTTIJDTOTAAL)));
    }

    @Test
    @Transactional
    void getKlantcontact() throws Exception {
        // Initialize the database
        klantcontactRepository.saveAndFlush(klantcontact);

        // Get the klantcontact
        restKlantcontactMockMvc
            .perform(get(ENTITY_API_URL_ID, klantcontact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(klantcontact.getId().intValue()))
            .andExpect(jsonPath("$.eindtijd").value(DEFAULT_EINDTIJD))
            .andExpect(jsonPath("$.kanaal").value(DEFAULT_KANAAL))
            .andExpect(jsonPath("$.notitie").value(DEFAULT_NOTITIE))
            .andExpect(jsonPath("$.starttijd").value(DEFAULT_STARTTIJD))
            .andExpect(jsonPath("$.tijdsduur").value(DEFAULT_TIJDSDUUR))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING))
            .andExpect(jsonPath("$.wachttijdtotaal").value(DEFAULT_WACHTTIJDTOTAAL));
    }

    @Test
    @Transactional
    void getNonExistingKlantcontact() throws Exception {
        // Get the klantcontact
        restKlantcontactMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKlantcontact() throws Exception {
        // Initialize the database
        klantcontactRepository.saveAndFlush(klantcontact);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the klantcontact
        Klantcontact updatedKlantcontact = klantcontactRepository.findById(klantcontact.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKlantcontact are not directly saved in db
        em.detach(updatedKlantcontact);
        updatedKlantcontact
            .eindtijd(UPDATED_EINDTIJD)
            .kanaal(UPDATED_KANAAL)
            .notitie(UPDATED_NOTITIE)
            .starttijd(UPDATED_STARTTIJD)
            .tijdsduur(UPDATED_TIJDSDUUR)
            .toelichting(UPDATED_TOELICHTING)
            .wachttijdtotaal(UPDATED_WACHTTIJDTOTAAL);

        restKlantcontactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKlantcontact.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKlantcontact))
            )
            .andExpect(status().isOk());

        // Validate the Klantcontact in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKlantcontactToMatchAllProperties(updatedKlantcontact);
    }

    @Test
    @Transactional
    void putNonExistingKlantcontact() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantcontact.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKlantcontactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, klantcontact.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(klantcontact))
            )
            .andExpect(status().isBadRequest());

        // Validate the Klantcontact in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKlantcontact() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantcontact.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlantcontactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(klantcontact))
            )
            .andExpect(status().isBadRequest());

        // Validate the Klantcontact in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKlantcontact() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantcontact.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlantcontactMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(klantcontact)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Klantcontact in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKlantcontactWithPatch() throws Exception {
        // Initialize the database
        klantcontactRepository.saveAndFlush(klantcontact);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the klantcontact using partial update
        Klantcontact partialUpdatedKlantcontact = new Klantcontact();
        partialUpdatedKlantcontact.setId(klantcontact.getId());

        partialUpdatedKlantcontact.notitie(UPDATED_NOTITIE).wachttijdtotaal(UPDATED_WACHTTIJDTOTAAL);

        restKlantcontactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKlantcontact.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKlantcontact))
            )
            .andExpect(status().isOk());

        // Validate the Klantcontact in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKlantcontactUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKlantcontact, klantcontact),
            getPersistedKlantcontact(klantcontact)
        );
    }

    @Test
    @Transactional
    void fullUpdateKlantcontactWithPatch() throws Exception {
        // Initialize the database
        klantcontactRepository.saveAndFlush(klantcontact);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the klantcontact using partial update
        Klantcontact partialUpdatedKlantcontact = new Klantcontact();
        partialUpdatedKlantcontact.setId(klantcontact.getId());

        partialUpdatedKlantcontact
            .eindtijd(UPDATED_EINDTIJD)
            .kanaal(UPDATED_KANAAL)
            .notitie(UPDATED_NOTITIE)
            .starttijd(UPDATED_STARTTIJD)
            .tijdsduur(UPDATED_TIJDSDUUR)
            .toelichting(UPDATED_TOELICHTING)
            .wachttijdtotaal(UPDATED_WACHTTIJDTOTAAL);

        restKlantcontactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKlantcontact.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKlantcontact))
            )
            .andExpect(status().isOk());

        // Validate the Klantcontact in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKlantcontactUpdatableFieldsEquals(partialUpdatedKlantcontact, getPersistedKlantcontact(partialUpdatedKlantcontact));
    }

    @Test
    @Transactional
    void patchNonExistingKlantcontact() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantcontact.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKlantcontactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, klantcontact.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(klantcontact))
            )
            .andExpect(status().isBadRequest());

        // Validate the Klantcontact in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKlantcontact() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantcontact.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlantcontactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(klantcontact))
            )
            .andExpect(status().isBadRequest());

        // Validate the Klantcontact in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKlantcontact() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantcontact.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlantcontactMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(klantcontact)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Klantcontact in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKlantcontact() throws Exception {
        // Initialize the database
        klantcontactRepository.saveAndFlush(klantcontact);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the klantcontact
        restKlantcontactMockMvc
            .perform(delete(ENTITY_API_URL_ID, klantcontact.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return klantcontactRepository.count();
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

    protected Klantcontact getPersistedKlantcontact(Klantcontact klantcontact) {
        return klantcontactRepository.findById(klantcontact.getId()).orElseThrow();
    }

    protected void assertPersistedKlantcontactToMatchAllProperties(Klantcontact expectedKlantcontact) {
        assertKlantcontactAllPropertiesEquals(expectedKlantcontact, getPersistedKlantcontact(expectedKlantcontact));
    }

    protected void assertPersistedKlantcontactToMatchUpdatableProperties(Klantcontact expectedKlantcontact) {
        assertKlantcontactAllUpdatablePropertiesEquals(expectedKlantcontact, getPersistedKlantcontact(expectedKlantcontact));
    }
}
