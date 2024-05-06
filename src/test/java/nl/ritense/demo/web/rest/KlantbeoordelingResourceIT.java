package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KlantbeoordelingAsserts.*;
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
import nl.ritense.demo.domain.Klantbeoordeling;
import nl.ritense.demo.repository.KlantbeoordelingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KlantbeoordelingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KlantbeoordelingResourceIT {

    private static final String DEFAULT_BEOORDELING = "AAAAAAAAAA";
    private static final String UPDATED_BEOORDELING = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONTACTOPNEMEN = false;
    private static final Boolean UPDATED_CONTACTOPNEMEN = true;

    private static final LocalDate DEFAULT_DDBEOORDELING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DDBEOORDELING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_KANAAL = "AAAAAAAAAA";
    private static final String UPDATED_KANAAL = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERWERP = "AAAAAAAAAA";
    private static final String UPDATED_ONDERWERP = "BBBBBBBBBB";

    private static final String DEFAULT_SUBCATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_SUBCATEGORIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/klantbeoordelings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KlantbeoordelingRepository klantbeoordelingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKlantbeoordelingMockMvc;

    private Klantbeoordeling klantbeoordeling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Klantbeoordeling createEntity(EntityManager em) {
        Klantbeoordeling klantbeoordeling = new Klantbeoordeling()
            .beoordeling(DEFAULT_BEOORDELING)
            .categorie(DEFAULT_CATEGORIE)
            .contactopnemen(DEFAULT_CONTACTOPNEMEN)
            .ddbeoordeling(DEFAULT_DDBEOORDELING)
            .kanaal(DEFAULT_KANAAL)
            .onderwerp(DEFAULT_ONDERWERP)
            .subcategorie(DEFAULT_SUBCATEGORIE);
        return klantbeoordeling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Klantbeoordeling createUpdatedEntity(EntityManager em) {
        Klantbeoordeling klantbeoordeling = new Klantbeoordeling()
            .beoordeling(UPDATED_BEOORDELING)
            .categorie(UPDATED_CATEGORIE)
            .contactopnemen(UPDATED_CONTACTOPNEMEN)
            .ddbeoordeling(UPDATED_DDBEOORDELING)
            .kanaal(UPDATED_KANAAL)
            .onderwerp(UPDATED_ONDERWERP)
            .subcategorie(UPDATED_SUBCATEGORIE);
        return klantbeoordeling;
    }

    @BeforeEach
    public void initTest() {
        klantbeoordeling = createEntity(em);
    }

    @Test
    @Transactional
    void createKlantbeoordeling() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Klantbeoordeling
        var returnedKlantbeoordeling = om.readValue(
            restKlantbeoordelingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(klantbeoordeling)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Klantbeoordeling.class
        );

        // Validate the Klantbeoordeling in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKlantbeoordelingUpdatableFieldsEquals(returnedKlantbeoordeling, getPersistedKlantbeoordeling(returnedKlantbeoordeling));
    }

    @Test
    @Transactional
    void createKlantbeoordelingWithExistingId() throws Exception {
        // Create the Klantbeoordeling with an existing ID
        klantbeoordeling.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKlantbeoordelingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(klantbeoordeling)))
            .andExpect(status().isBadRequest());

        // Validate the Klantbeoordeling in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKlantbeoordelings() throws Exception {
        // Initialize the database
        klantbeoordelingRepository.saveAndFlush(klantbeoordeling);

        // Get all the klantbeoordelingList
        restKlantbeoordelingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(klantbeoordeling.getId().intValue())))
            .andExpect(jsonPath("$.[*].beoordeling").value(hasItem(DEFAULT_BEOORDELING)))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE)))
            .andExpect(jsonPath("$.[*].contactopnemen").value(hasItem(DEFAULT_CONTACTOPNEMEN.booleanValue())))
            .andExpect(jsonPath("$.[*].ddbeoordeling").value(hasItem(DEFAULT_DDBEOORDELING.toString())))
            .andExpect(jsonPath("$.[*].kanaal").value(hasItem(DEFAULT_KANAAL)))
            .andExpect(jsonPath("$.[*].onderwerp").value(hasItem(DEFAULT_ONDERWERP)))
            .andExpect(jsonPath("$.[*].subcategorie").value(hasItem(DEFAULT_SUBCATEGORIE)));
    }

    @Test
    @Transactional
    void getKlantbeoordeling() throws Exception {
        // Initialize the database
        klantbeoordelingRepository.saveAndFlush(klantbeoordeling);

        // Get the klantbeoordeling
        restKlantbeoordelingMockMvc
            .perform(get(ENTITY_API_URL_ID, klantbeoordeling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(klantbeoordeling.getId().intValue()))
            .andExpect(jsonPath("$.beoordeling").value(DEFAULT_BEOORDELING))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE))
            .andExpect(jsonPath("$.contactopnemen").value(DEFAULT_CONTACTOPNEMEN.booleanValue()))
            .andExpect(jsonPath("$.ddbeoordeling").value(DEFAULT_DDBEOORDELING.toString()))
            .andExpect(jsonPath("$.kanaal").value(DEFAULT_KANAAL))
            .andExpect(jsonPath("$.onderwerp").value(DEFAULT_ONDERWERP))
            .andExpect(jsonPath("$.subcategorie").value(DEFAULT_SUBCATEGORIE));
    }

    @Test
    @Transactional
    void getNonExistingKlantbeoordeling() throws Exception {
        // Get the klantbeoordeling
        restKlantbeoordelingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKlantbeoordeling() throws Exception {
        // Initialize the database
        klantbeoordelingRepository.saveAndFlush(klantbeoordeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the klantbeoordeling
        Klantbeoordeling updatedKlantbeoordeling = klantbeoordelingRepository.findById(klantbeoordeling.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKlantbeoordeling are not directly saved in db
        em.detach(updatedKlantbeoordeling);
        updatedKlantbeoordeling
            .beoordeling(UPDATED_BEOORDELING)
            .categorie(UPDATED_CATEGORIE)
            .contactopnemen(UPDATED_CONTACTOPNEMEN)
            .ddbeoordeling(UPDATED_DDBEOORDELING)
            .kanaal(UPDATED_KANAAL)
            .onderwerp(UPDATED_ONDERWERP)
            .subcategorie(UPDATED_SUBCATEGORIE);

        restKlantbeoordelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKlantbeoordeling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKlantbeoordeling))
            )
            .andExpect(status().isOk());

        // Validate the Klantbeoordeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKlantbeoordelingToMatchAllProperties(updatedKlantbeoordeling);
    }

    @Test
    @Transactional
    void putNonExistingKlantbeoordeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantbeoordeling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKlantbeoordelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, klantbeoordeling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(klantbeoordeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Klantbeoordeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKlantbeoordeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantbeoordeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlantbeoordelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(klantbeoordeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Klantbeoordeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKlantbeoordeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantbeoordeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlantbeoordelingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(klantbeoordeling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Klantbeoordeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKlantbeoordelingWithPatch() throws Exception {
        // Initialize the database
        klantbeoordelingRepository.saveAndFlush(klantbeoordeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the klantbeoordeling using partial update
        Klantbeoordeling partialUpdatedKlantbeoordeling = new Klantbeoordeling();
        partialUpdatedKlantbeoordeling.setId(klantbeoordeling.getId());

        partialUpdatedKlantbeoordeling
            .beoordeling(UPDATED_BEOORDELING)
            .categorie(UPDATED_CATEGORIE)
            .contactopnemen(UPDATED_CONTACTOPNEMEN)
            .kanaal(UPDATED_KANAAL)
            .onderwerp(UPDATED_ONDERWERP);

        restKlantbeoordelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKlantbeoordeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKlantbeoordeling))
            )
            .andExpect(status().isOk());

        // Validate the Klantbeoordeling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKlantbeoordelingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKlantbeoordeling, klantbeoordeling),
            getPersistedKlantbeoordeling(klantbeoordeling)
        );
    }

    @Test
    @Transactional
    void fullUpdateKlantbeoordelingWithPatch() throws Exception {
        // Initialize the database
        klantbeoordelingRepository.saveAndFlush(klantbeoordeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the klantbeoordeling using partial update
        Klantbeoordeling partialUpdatedKlantbeoordeling = new Klantbeoordeling();
        partialUpdatedKlantbeoordeling.setId(klantbeoordeling.getId());

        partialUpdatedKlantbeoordeling
            .beoordeling(UPDATED_BEOORDELING)
            .categorie(UPDATED_CATEGORIE)
            .contactopnemen(UPDATED_CONTACTOPNEMEN)
            .ddbeoordeling(UPDATED_DDBEOORDELING)
            .kanaal(UPDATED_KANAAL)
            .onderwerp(UPDATED_ONDERWERP)
            .subcategorie(UPDATED_SUBCATEGORIE);

        restKlantbeoordelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKlantbeoordeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKlantbeoordeling))
            )
            .andExpect(status().isOk());

        // Validate the Klantbeoordeling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKlantbeoordelingUpdatableFieldsEquals(
            partialUpdatedKlantbeoordeling,
            getPersistedKlantbeoordeling(partialUpdatedKlantbeoordeling)
        );
    }

    @Test
    @Transactional
    void patchNonExistingKlantbeoordeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantbeoordeling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKlantbeoordelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, klantbeoordeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(klantbeoordeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Klantbeoordeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKlantbeoordeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantbeoordeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlantbeoordelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(klantbeoordeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Klantbeoordeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKlantbeoordeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klantbeoordeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlantbeoordelingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(klantbeoordeling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Klantbeoordeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKlantbeoordeling() throws Exception {
        // Initialize the database
        klantbeoordelingRepository.saveAndFlush(klantbeoordeling);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the klantbeoordeling
        restKlantbeoordelingMockMvc
            .perform(delete(ENTITY_API_URL_ID, klantbeoordeling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return klantbeoordelingRepository.count();
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

    protected Klantbeoordeling getPersistedKlantbeoordeling(Klantbeoordeling klantbeoordeling) {
        return klantbeoordelingRepository.findById(klantbeoordeling.getId()).orElseThrow();
    }

    protected void assertPersistedKlantbeoordelingToMatchAllProperties(Klantbeoordeling expectedKlantbeoordeling) {
        assertKlantbeoordelingAllPropertiesEquals(expectedKlantbeoordeling, getPersistedKlantbeoordeling(expectedKlantbeoordeling));
    }

    protected void assertPersistedKlantbeoordelingToMatchUpdatableProperties(Klantbeoordeling expectedKlantbeoordeling) {
        assertKlantbeoordelingAllUpdatablePropertiesEquals(
            expectedKlantbeoordeling,
            getPersistedKlantbeoordeling(expectedKlantbeoordeling)
        );
    }
}
