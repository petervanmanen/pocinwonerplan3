package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FractieAsserts.*;
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
import nl.ritense.demo.domain.Fractie;
import nl.ritense.demo.domain.Storting;
import nl.ritense.demo.repository.FractieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FractieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FractieResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fracties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FractieRepository fractieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFractieMockMvc;

    private Fractie fractie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fractie createEntity(EntityManager em) {
        Fractie fractie = new Fractie().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        // Add required entity
        Storting storting;
        if (TestUtil.findAll(em, Storting.class).isEmpty()) {
            storting = StortingResourceIT.createEntity(em);
            em.persist(storting);
            em.flush();
        } else {
            storting = TestUtil.findAll(em, Storting.class).get(0);
        }
        fractie.getFractieStortings().add(storting);
        return fractie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fractie createUpdatedEntity(EntityManager em) {
        Fractie fractie = new Fractie().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        // Add required entity
        Storting storting;
        if (TestUtil.findAll(em, Storting.class).isEmpty()) {
            storting = StortingResourceIT.createUpdatedEntity(em);
            em.persist(storting);
            em.flush();
        } else {
            storting = TestUtil.findAll(em, Storting.class).get(0);
        }
        fractie.getFractieStortings().add(storting);
        return fractie;
    }

    @BeforeEach
    public void initTest() {
        fractie = createEntity(em);
    }

    @Test
    @Transactional
    void createFractie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Fractie
        var returnedFractie = om.readValue(
            restFractieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fractie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Fractie.class
        );

        // Validate the Fractie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFractieUpdatableFieldsEquals(returnedFractie, getPersistedFractie(returnedFractie));
    }

    @Test
    @Transactional
    void createFractieWithExistingId() throws Exception {
        // Create the Fractie with an existing ID
        fractie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFractieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fractie)))
            .andExpect(status().isBadRequest());

        // Validate the Fractie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFracties() throws Exception {
        // Initialize the database
        fractieRepository.saveAndFlush(fractie);

        // Get all the fractieList
        restFractieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fractie.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getFractie() throws Exception {
        // Initialize the database
        fractieRepository.saveAndFlush(fractie);

        // Get the fractie
        restFractieMockMvc
            .perform(get(ENTITY_API_URL_ID, fractie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fractie.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingFractie() throws Exception {
        // Get the fractie
        restFractieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFractie() throws Exception {
        // Initialize the database
        fractieRepository.saveAndFlush(fractie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fractie
        Fractie updatedFractie = fractieRepository.findById(fractie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFractie are not directly saved in db
        em.detach(updatedFractie);
        updatedFractie.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restFractieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFractie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFractie))
            )
            .andExpect(status().isOk());

        // Validate the Fractie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFractieToMatchAllProperties(updatedFractie);
    }

    @Test
    @Transactional
    void putNonExistingFractie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fractie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFractieMockMvc
            .perform(put(ENTITY_API_URL_ID, fractie.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fractie)))
            .andExpect(status().isBadRequest());

        // Validate the Fractie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFractie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fractie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFractieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fractie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fractie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFractie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fractie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFractieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fractie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fractie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFractieWithPatch() throws Exception {
        // Initialize the database
        fractieRepository.saveAndFlush(fractie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fractie using partial update
        Fractie partialUpdatedFractie = new Fractie();
        partialUpdatedFractie.setId(fractie.getId());

        partialUpdatedFractie.naam(UPDATED_NAAM);

        restFractieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFractie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFractie))
            )
            .andExpect(status().isOk());

        // Validate the Fractie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFractieUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedFractie, fractie), getPersistedFractie(fractie));
    }

    @Test
    @Transactional
    void fullUpdateFractieWithPatch() throws Exception {
        // Initialize the database
        fractieRepository.saveAndFlush(fractie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fractie using partial update
        Fractie partialUpdatedFractie = new Fractie();
        partialUpdatedFractie.setId(fractie.getId());

        partialUpdatedFractie.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restFractieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFractie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFractie))
            )
            .andExpect(status().isOk());

        // Validate the Fractie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFractieUpdatableFieldsEquals(partialUpdatedFractie, getPersistedFractie(partialUpdatedFractie));
    }

    @Test
    @Transactional
    void patchNonExistingFractie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fractie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFractieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fractie.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fractie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fractie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFractie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fractie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFractieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fractie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fractie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFractie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fractie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFractieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fractie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fractie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFractie() throws Exception {
        // Initialize the database
        fractieRepository.saveAndFlush(fractie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fractie
        restFractieMockMvc
            .perform(delete(ENTITY_API_URL_ID, fractie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fractieRepository.count();
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

    protected Fractie getPersistedFractie(Fractie fractie) {
        return fractieRepository.findById(fractie.getId()).orElseThrow();
    }

    protected void assertPersistedFractieToMatchAllProperties(Fractie expectedFractie) {
        assertFractieAllPropertiesEquals(expectedFractie, getPersistedFractie(expectedFractie));
    }

    protected void assertPersistedFractieToMatchUpdatableProperties(Fractie expectedFractie) {
        assertFractieAllUpdatablePropertiesEquals(expectedFractie, getPersistedFractie(expectedFractie));
    }
}
