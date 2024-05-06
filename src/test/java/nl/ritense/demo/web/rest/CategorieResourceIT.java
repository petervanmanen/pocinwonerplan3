package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.CategorieAsserts.*;
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
import nl.ritense.demo.domain.Categorie;
import nl.ritense.demo.repository.CategorieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CategorieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategorieResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorieMockMvc;

    private Categorie categorie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categorie createEntity(EntityManager em) {
        Categorie categorie = new Categorie().naam(DEFAULT_NAAM);
        return categorie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categorie createUpdatedEntity(EntityManager em) {
        Categorie categorie = new Categorie().naam(UPDATED_NAAM);
        return categorie;
    }

    @BeforeEach
    public void initTest() {
        categorie = createEntity(em);
    }

    @Test
    @Transactional
    void createCategorie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Categorie
        var returnedCategorie = om.readValue(
            restCategorieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categorie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Categorie.class
        );

        // Validate the Categorie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCategorieUpdatableFieldsEquals(returnedCategorie, getPersistedCategorie(returnedCategorie));
    }

    @Test
    @Transactional
    void createCategorieWithExistingId() throws Exception {
        // Create the Categorie with an existing ID
        categorie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categorie)))
            .andExpect(status().isBadRequest());

        // Validate the Categorie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategories() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList
        restCategorieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorie.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getCategorie() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get the categorie
        restCategorieMockMvc
            .perform(get(ENTITY_API_URL_ID, categorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorie.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingCategorie() throws Exception {
        // Get the categorie
        restCategorieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategorie() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categorie
        Categorie updatedCategorie = categorieRepository.findById(categorie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCategorie are not directly saved in db
        em.detach(updatedCategorie);
        updatedCategorie.naam(UPDATED_NAAM);

        restCategorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCategorie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCategorie))
            )
            .andExpect(status().isOk());

        // Validate the Categorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCategorieToMatchAllProperties(updatedCategorie);
    }

    @Test
    @Transactional
    void putNonExistingCategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorie.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(categorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categorie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Categorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategorieWithPatch() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categorie using partial update
        Categorie partialUpdatedCategorie = new Categorie();
        partialUpdatedCategorie.setId(categorie.getId());

        partialUpdatedCategorie.naam(UPDATED_NAAM);

        restCategorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCategorie))
            )
            .andExpect(status().isOk());

        // Validate the Categorie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCategorieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCategorie, categorie),
            getPersistedCategorie(categorie)
        );
    }

    @Test
    @Transactional
    void fullUpdateCategorieWithPatch() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categorie using partial update
        Categorie partialUpdatedCategorie = new Categorie();
        partialUpdatedCategorie.setId(categorie.getId());

        partialUpdatedCategorie.naam(UPDATED_NAAM);

        restCategorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCategorie))
            )
            .andExpect(status().isOk());

        // Validate the Categorie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCategorieUpdatableFieldsEquals(partialUpdatedCategorie, getPersistedCategorie(partialUpdatedCategorie));
    }

    @Test
    @Transactional
    void patchNonExistingCategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categorie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(categorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(categorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(categorie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Categorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategorie() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the categorie
        restCategorieMockMvc
            .perform(delete(ENTITY_API_URL_ID, categorie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return categorieRepository.count();
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

    protected Categorie getPersistedCategorie(Categorie categorie) {
        return categorieRepository.findById(categorie.getId()).orElseThrow();
    }

    protected void assertPersistedCategorieToMatchAllProperties(Categorie expectedCategorie) {
        assertCategorieAllPropertiesEquals(expectedCategorie, getPersistedCategorie(expectedCategorie));
    }

    protected void assertPersistedCategorieToMatchUpdatableProperties(Categorie expectedCategorie) {
        assertCategorieAllUpdatablePropertiesEquals(expectedCategorie, getPersistedCategorie(expectedCategorie));
    }
}
