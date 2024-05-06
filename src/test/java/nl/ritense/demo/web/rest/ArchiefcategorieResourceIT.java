package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ArchiefcategorieAsserts.*;
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
import nl.ritense.demo.domain.Archiefcategorie;
import nl.ritense.demo.repository.ArchiefcategorieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ArchiefcategorieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArchiefcategorieResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/archiefcategories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ArchiefcategorieRepository archiefcategorieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArchiefcategorieMockMvc;

    private Archiefcategorie archiefcategorie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Archiefcategorie createEntity(EntityManager em) {
        Archiefcategorie archiefcategorie = new Archiefcategorie()
            .naam(DEFAULT_NAAM)
            .nummer(DEFAULT_NUMMER)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return archiefcategorie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Archiefcategorie createUpdatedEntity(EntityManager em) {
        Archiefcategorie archiefcategorie = new Archiefcategorie()
            .naam(UPDATED_NAAM)
            .nummer(UPDATED_NUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return archiefcategorie;
    }

    @BeforeEach
    public void initTest() {
        archiefcategorie = createEntity(em);
    }

    @Test
    @Transactional
    void createArchiefcategorie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Archiefcategorie
        var returnedArchiefcategorie = om.readValue(
            restArchiefcategorieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archiefcategorie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Archiefcategorie.class
        );

        // Validate the Archiefcategorie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertArchiefcategorieUpdatableFieldsEquals(returnedArchiefcategorie, getPersistedArchiefcategorie(returnedArchiefcategorie));
    }

    @Test
    @Transactional
    void createArchiefcategorieWithExistingId() throws Exception {
        // Create the Archiefcategorie with an existing ID
        archiefcategorie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArchiefcategorieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archiefcategorie)))
            .andExpect(status().isBadRequest());

        // Validate the Archiefcategorie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArchiefcategories() throws Exception {
        // Initialize the database
        archiefcategorieRepository.saveAndFlush(archiefcategorie);

        // Get all the archiefcategorieList
        restArchiefcategorieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(archiefcategorie.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getArchiefcategorie() throws Exception {
        // Initialize the database
        archiefcategorieRepository.saveAndFlush(archiefcategorie);

        // Get the archiefcategorie
        restArchiefcategorieMockMvc
            .perform(get(ENTITY_API_URL_ID, archiefcategorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(archiefcategorie.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingArchiefcategorie() throws Exception {
        // Get the archiefcategorie
        restArchiefcategorieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArchiefcategorie() throws Exception {
        // Initialize the database
        archiefcategorieRepository.saveAndFlush(archiefcategorie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the archiefcategorie
        Archiefcategorie updatedArchiefcategorie = archiefcategorieRepository.findById(archiefcategorie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArchiefcategorie are not directly saved in db
        em.detach(updatedArchiefcategorie);
        updatedArchiefcategorie.naam(UPDATED_NAAM).nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING);

        restArchiefcategorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedArchiefcategorie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedArchiefcategorie))
            )
            .andExpect(status().isOk());

        // Validate the Archiefcategorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedArchiefcategorieToMatchAllProperties(updatedArchiefcategorie);
    }

    @Test
    @Transactional
    void putNonExistingArchiefcategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archiefcategorie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArchiefcategorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, archiefcategorie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(archiefcategorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archiefcategorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArchiefcategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archiefcategorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchiefcategorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(archiefcategorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archiefcategorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArchiefcategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archiefcategorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchiefcategorieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archiefcategorie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Archiefcategorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArchiefcategorieWithPatch() throws Exception {
        // Initialize the database
        archiefcategorieRepository.saveAndFlush(archiefcategorie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the archiefcategorie using partial update
        Archiefcategorie partialUpdatedArchiefcategorie = new Archiefcategorie();
        partialUpdatedArchiefcategorie.setId(archiefcategorie.getId());

        partialUpdatedArchiefcategorie.naam(UPDATED_NAAM);

        restArchiefcategorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArchiefcategorie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArchiefcategorie))
            )
            .andExpect(status().isOk());

        // Validate the Archiefcategorie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArchiefcategorieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedArchiefcategorie, archiefcategorie),
            getPersistedArchiefcategorie(archiefcategorie)
        );
    }

    @Test
    @Transactional
    void fullUpdateArchiefcategorieWithPatch() throws Exception {
        // Initialize the database
        archiefcategorieRepository.saveAndFlush(archiefcategorie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the archiefcategorie using partial update
        Archiefcategorie partialUpdatedArchiefcategorie = new Archiefcategorie();
        partialUpdatedArchiefcategorie.setId(archiefcategorie.getId());

        partialUpdatedArchiefcategorie.naam(UPDATED_NAAM).nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING);

        restArchiefcategorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArchiefcategorie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArchiefcategorie))
            )
            .andExpect(status().isOk());

        // Validate the Archiefcategorie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArchiefcategorieUpdatableFieldsEquals(
            partialUpdatedArchiefcategorie,
            getPersistedArchiefcategorie(partialUpdatedArchiefcategorie)
        );
    }

    @Test
    @Transactional
    void patchNonExistingArchiefcategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archiefcategorie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArchiefcategorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, archiefcategorie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(archiefcategorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archiefcategorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArchiefcategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archiefcategorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchiefcategorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(archiefcategorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archiefcategorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArchiefcategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archiefcategorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchiefcategorieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(archiefcategorie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Archiefcategorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArchiefcategorie() throws Exception {
        // Initialize the database
        archiefcategorieRepository.saveAndFlush(archiefcategorie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the archiefcategorie
        restArchiefcategorieMockMvc
            .perform(delete(ENTITY_API_URL_ID, archiefcategorie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return archiefcategorieRepository.count();
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

    protected Archiefcategorie getPersistedArchiefcategorie(Archiefcategorie archiefcategorie) {
        return archiefcategorieRepository.findById(archiefcategorie.getId()).orElseThrow();
    }

    protected void assertPersistedArchiefcategorieToMatchAllProperties(Archiefcategorie expectedArchiefcategorie) {
        assertArchiefcategorieAllPropertiesEquals(expectedArchiefcategorie, getPersistedArchiefcategorie(expectedArchiefcategorie));
    }

    protected void assertPersistedArchiefcategorieToMatchUpdatableProperties(Archiefcategorie expectedArchiefcategorie) {
        assertArchiefcategorieAllUpdatablePropertiesEquals(
            expectedArchiefcategorie,
            getPersistedArchiefcategorie(expectedArchiefcategorie)
        );
    }
}
