package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AuteurAsserts.*;
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
import nl.ritense.demo.domain.Auteur;
import nl.ritense.demo.repository.AuteurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AuteurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuteurResourceIT {

    private static final LocalDate DEFAULT_DATUMGEBOORTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMGEBOORTE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMOVERLIJDEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMOVERLIJDEN = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/auteurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AuteurRepository auteurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuteurMockMvc;

    private Auteur auteur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auteur createEntity(EntityManager em) {
        Auteur auteur = new Auteur().datumgeboorte(DEFAULT_DATUMGEBOORTE).datumoverlijden(DEFAULT_DATUMOVERLIJDEN);
        return auteur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auteur createUpdatedEntity(EntityManager em) {
        Auteur auteur = new Auteur().datumgeboorte(UPDATED_DATUMGEBOORTE).datumoverlijden(UPDATED_DATUMOVERLIJDEN);
        return auteur;
    }

    @BeforeEach
    public void initTest() {
        auteur = createEntity(em);
    }

    @Test
    @Transactional
    void createAuteur() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Auteur
        var returnedAuteur = om.readValue(
            restAuteurMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(auteur)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Auteur.class
        );

        // Validate the Auteur in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAuteurUpdatableFieldsEquals(returnedAuteur, getPersistedAuteur(returnedAuteur));
    }

    @Test
    @Transactional
    void createAuteurWithExistingId() throws Exception {
        // Create the Auteur with an existing ID
        auteur.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuteurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(auteur)))
            .andExpect(status().isBadRequest());

        // Validate the Auteur in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAuteurs() throws Exception {
        // Initialize the database
        auteurRepository.saveAndFlush(auteur);

        // Get all the auteurList
        restAuteurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auteur.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumgeboorte").value(hasItem(DEFAULT_DATUMGEBOORTE.toString())))
            .andExpect(jsonPath("$.[*].datumoverlijden").value(hasItem(DEFAULT_DATUMOVERLIJDEN.toString())));
    }

    @Test
    @Transactional
    void getAuteur() throws Exception {
        // Initialize the database
        auteurRepository.saveAndFlush(auteur);

        // Get the auteur
        restAuteurMockMvc
            .perform(get(ENTITY_API_URL_ID, auteur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(auteur.getId().intValue()))
            .andExpect(jsonPath("$.datumgeboorte").value(DEFAULT_DATUMGEBOORTE.toString()))
            .andExpect(jsonPath("$.datumoverlijden").value(DEFAULT_DATUMOVERLIJDEN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAuteur() throws Exception {
        // Get the auteur
        restAuteurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAuteur() throws Exception {
        // Initialize the database
        auteurRepository.saveAndFlush(auteur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the auteur
        Auteur updatedAuteur = auteurRepository.findById(auteur.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAuteur are not directly saved in db
        em.detach(updatedAuteur);
        updatedAuteur.datumgeboorte(UPDATED_DATUMGEBOORTE).datumoverlijden(UPDATED_DATUMOVERLIJDEN);

        restAuteurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAuteur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAuteur))
            )
            .andExpect(status().isOk());

        // Validate the Auteur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAuteurToMatchAllProperties(updatedAuteur);
    }

    @Test
    @Transactional
    void putNonExistingAuteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        auteur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuteurMockMvc
            .perform(put(ENTITY_API_URL_ID, auteur.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(auteur)))
            .andExpect(status().isBadRequest());

        // Validate the Auteur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        auteur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuteurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(auteur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auteur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        auteur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuteurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(auteur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Auteur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuteurWithPatch() throws Exception {
        // Initialize the database
        auteurRepository.saveAndFlush(auteur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the auteur using partial update
        Auteur partialUpdatedAuteur = new Auteur();
        partialUpdatedAuteur.setId(auteur.getId());

        partialUpdatedAuteur.datumgeboorte(UPDATED_DATUMGEBOORTE).datumoverlijden(UPDATED_DATUMOVERLIJDEN);

        restAuteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuteur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAuteur))
            )
            .andExpect(status().isOk());

        // Validate the Auteur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAuteurUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAuteur, auteur), getPersistedAuteur(auteur));
    }

    @Test
    @Transactional
    void fullUpdateAuteurWithPatch() throws Exception {
        // Initialize the database
        auteurRepository.saveAndFlush(auteur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the auteur using partial update
        Auteur partialUpdatedAuteur = new Auteur();
        partialUpdatedAuteur.setId(auteur.getId());

        partialUpdatedAuteur.datumgeboorte(UPDATED_DATUMGEBOORTE).datumoverlijden(UPDATED_DATUMOVERLIJDEN);

        restAuteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuteur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAuteur))
            )
            .andExpect(status().isOk());

        // Validate the Auteur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAuteurUpdatableFieldsEquals(partialUpdatedAuteur, getPersistedAuteur(partialUpdatedAuteur));
    }

    @Test
    @Transactional
    void patchNonExistingAuteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        auteur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, auteur.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(auteur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auteur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        auteur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(auteur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auteur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        auteur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuteurMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(auteur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Auteur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuteur() throws Exception {
        // Initialize the database
        auteurRepository.saveAndFlush(auteur);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the auteur
        restAuteurMockMvc
            .perform(delete(ENTITY_API_URL_ID, auteur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return auteurRepository.count();
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

    protected Auteur getPersistedAuteur(Auteur auteur) {
        return auteurRepository.findById(auteur.getId()).orElseThrow();
    }

    protected void assertPersistedAuteurToMatchAllProperties(Auteur expectedAuteur) {
        assertAuteurAllPropertiesEquals(expectedAuteur, getPersistedAuteur(expectedAuteur));
    }

    protected void assertPersistedAuteurToMatchUpdatableProperties(Auteur expectedAuteur) {
        assertAuteurAllUpdatablePropertiesEquals(expectedAuteur, getPersistedAuteur(expectedAuteur));
    }
}
