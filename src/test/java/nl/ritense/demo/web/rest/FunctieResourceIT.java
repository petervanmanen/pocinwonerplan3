package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FunctieAsserts.*;
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
import nl.ritense.demo.domain.Functie;
import nl.ritense.demo.repository.FunctieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FunctieResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class FunctieResourceIT {

    private static final String DEFAULT_GROEP = "AAAAAAAAAA";
    private static final String UPDATED_GROEP = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/functies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FunctieRepository functieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFunctieMockMvc;

    private Functie functie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Functie createEntity(EntityManager em) {
        Functie functie = new Functie().groep(DEFAULT_GROEP).naam(DEFAULT_NAAM);
        return functie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Functie createUpdatedEntity(EntityManager em) {
        Functie functie = new Functie().groep(UPDATED_GROEP).naam(UPDATED_NAAM);
        return functie;
    }

    @BeforeEach
    public void initTest() {
        functie = createEntity(em);
    }

    @Test
    @Transactional
    void createFunctie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Functie
        var returnedFunctie = om.readValue(
            restFunctieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(functie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Functie.class
        );

        // Validate the Functie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFunctieUpdatableFieldsEquals(returnedFunctie, getPersistedFunctie(returnedFunctie));
    }

    @Test
    @Transactional
    void createFunctieWithExistingId() throws Exception {
        // Create the Functie with an existing ID
        functie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFunctieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(functie)))
            .andExpect(status().isBadRequest());

        // Validate the Functie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuncties() throws Exception {
        // Initialize the database
        functieRepository.saveAndFlush(functie);

        // Get all the functieList
        restFunctieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(functie.getId().intValue())))
            .andExpect(jsonPath("$.[*].groep").value(hasItem(DEFAULT_GROEP)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getFunctie() throws Exception {
        // Initialize the database
        functieRepository.saveAndFlush(functie);

        // Get the functie
        restFunctieMockMvc
            .perform(get(ENTITY_API_URL_ID, functie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(functie.getId().intValue()))
            .andExpect(jsonPath("$.groep").value(DEFAULT_GROEP))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingFunctie() throws Exception {
        // Get the functie
        restFunctieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFunctie() throws Exception {
        // Initialize the database
        functieRepository.saveAndFlush(functie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the functie
        Functie updatedFunctie = functieRepository.findById(functie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFunctie are not directly saved in db
        em.detach(updatedFunctie);
        updatedFunctie.groep(UPDATED_GROEP).naam(UPDATED_NAAM);

        restFunctieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFunctie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFunctie))
            )
            .andExpect(status().isOk());

        // Validate the Functie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFunctieToMatchAllProperties(updatedFunctie);
    }

    @Test
    @Transactional
    void putNonExistingFunctie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFunctieMockMvc
            .perform(put(ENTITY_API_URL_ID, functie.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(functie)))
            .andExpect(status().isBadRequest());

        // Validate the Functie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFunctie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(functie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFunctie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(functie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Functie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFunctieWithPatch() throws Exception {
        // Initialize the database
        functieRepository.saveAndFlush(functie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the functie using partial update
        Functie partialUpdatedFunctie = new Functie();
        partialUpdatedFunctie.setId(functie.getId());

        restFunctieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFunctie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFunctie))
            )
            .andExpect(status().isOk());

        // Validate the Functie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFunctieUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedFunctie, functie), getPersistedFunctie(functie));
    }

    @Test
    @Transactional
    void fullUpdateFunctieWithPatch() throws Exception {
        // Initialize the database
        functieRepository.saveAndFlush(functie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the functie using partial update
        Functie partialUpdatedFunctie = new Functie();
        partialUpdatedFunctie.setId(functie.getId());

        partialUpdatedFunctie.groep(UPDATED_GROEP).naam(UPDATED_NAAM);

        restFunctieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFunctie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFunctie))
            )
            .andExpect(status().isOk());

        // Validate the Functie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFunctieUpdatableFieldsEquals(partialUpdatedFunctie, getPersistedFunctie(partialUpdatedFunctie));
    }

    @Test
    @Transactional
    void patchNonExistingFunctie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFunctieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, functie.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(functie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFunctie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(functie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFunctie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(functie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Functie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFunctie() throws Exception {
        // Initialize the database
        functieRepository.saveAndFlush(functie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the functie
        restFunctieMockMvc
            .perform(delete(ENTITY_API_URL_ID, functie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return functieRepository.count();
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

    protected Functie getPersistedFunctie(Functie functie) {
        return functieRepository.findById(functie.getId()).orElseThrow();
    }

    protected void assertPersistedFunctieToMatchAllProperties(Functie expectedFunctie) {
        assertFunctieAllPropertiesEquals(expectedFunctie, getPersistedFunctie(expectedFunctie));
    }

    protected void assertPersistedFunctieToMatchUpdatableProperties(Functie expectedFunctie) {
        assertFunctieAllUpdatablePropertiesEquals(expectedFunctie, getPersistedFunctie(expectedFunctie));
    }
}
