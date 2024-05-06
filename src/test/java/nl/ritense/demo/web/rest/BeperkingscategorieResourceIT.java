package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BeperkingscategorieAsserts.*;
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
import nl.ritense.demo.domain.Beperkingscategorie;
import nl.ritense.demo.repository.BeperkingscategorieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BeperkingscategorieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BeperkingscategorieResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBB";

    private static final String DEFAULT_WET = "AAAAAAAAAA";
    private static final String UPDATED_WET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/beperkingscategories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BeperkingscategorieRepository beperkingscategorieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeperkingscategorieMockMvc;

    private Beperkingscategorie beperkingscategorie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beperkingscategorie createEntity(EntityManager em) {
        Beperkingscategorie beperkingscategorie = new Beperkingscategorie().code(DEFAULT_CODE).wet(DEFAULT_WET);
        return beperkingscategorie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beperkingscategorie createUpdatedEntity(EntityManager em) {
        Beperkingscategorie beperkingscategorie = new Beperkingscategorie().code(UPDATED_CODE).wet(UPDATED_WET);
        return beperkingscategorie;
    }

    @BeforeEach
    public void initTest() {
        beperkingscategorie = createEntity(em);
    }

    @Test
    @Transactional
    void createBeperkingscategorie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Beperkingscategorie
        var returnedBeperkingscategorie = om.readValue(
            restBeperkingscategorieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beperkingscategorie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Beperkingscategorie.class
        );

        // Validate the Beperkingscategorie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBeperkingscategorieUpdatableFieldsEquals(
            returnedBeperkingscategorie,
            getPersistedBeperkingscategorie(returnedBeperkingscategorie)
        );
    }

    @Test
    @Transactional
    void createBeperkingscategorieWithExistingId() throws Exception {
        // Create the Beperkingscategorie with an existing ID
        beperkingscategorie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeperkingscategorieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beperkingscategorie)))
            .andExpect(status().isBadRequest());

        // Validate the Beperkingscategorie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBeperkingscategories() throws Exception {
        // Initialize the database
        beperkingscategorieRepository.saveAndFlush(beperkingscategorie);

        // Get all the beperkingscategorieList
        restBeperkingscategorieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beperkingscategorie.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].wet").value(hasItem(DEFAULT_WET)));
    }

    @Test
    @Transactional
    void getBeperkingscategorie() throws Exception {
        // Initialize the database
        beperkingscategorieRepository.saveAndFlush(beperkingscategorie);

        // Get the beperkingscategorie
        restBeperkingscategorieMockMvc
            .perform(get(ENTITY_API_URL_ID, beperkingscategorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beperkingscategorie.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.wet").value(DEFAULT_WET));
    }

    @Test
    @Transactional
    void getNonExistingBeperkingscategorie() throws Exception {
        // Get the beperkingscategorie
        restBeperkingscategorieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBeperkingscategorie() throws Exception {
        // Initialize the database
        beperkingscategorieRepository.saveAndFlush(beperkingscategorie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beperkingscategorie
        Beperkingscategorie updatedBeperkingscategorie = beperkingscategorieRepository.findById(beperkingscategorie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBeperkingscategorie are not directly saved in db
        em.detach(updatedBeperkingscategorie);
        updatedBeperkingscategorie.code(UPDATED_CODE).wet(UPDATED_WET);

        restBeperkingscategorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBeperkingscategorie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBeperkingscategorie))
            )
            .andExpect(status().isOk());

        // Validate the Beperkingscategorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBeperkingscategorieToMatchAllProperties(updatedBeperkingscategorie);
    }

    @Test
    @Transactional
    void putNonExistingBeperkingscategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscategorie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeperkingscategorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, beperkingscategorie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beperkingscategorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperkingscategorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBeperkingscategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscategorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingscategorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beperkingscategorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperkingscategorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBeperkingscategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscategorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingscategorieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beperkingscategorie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beperkingscategorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBeperkingscategorieWithPatch() throws Exception {
        // Initialize the database
        beperkingscategorieRepository.saveAndFlush(beperkingscategorie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beperkingscategorie using partial update
        Beperkingscategorie partialUpdatedBeperkingscategorie = new Beperkingscategorie();
        partialUpdatedBeperkingscategorie.setId(beperkingscategorie.getId());

        partialUpdatedBeperkingscategorie.code(UPDATED_CODE).wet(UPDATED_WET);

        restBeperkingscategorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeperkingscategorie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeperkingscategorie))
            )
            .andExpect(status().isOk());

        // Validate the Beperkingscategorie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeperkingscategorieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBeperkingscategorie, beperkingscategorie),
            getPersistedBeperkingscategorie(beperkingscategorie)
        );
    }

    @Test
    @Transactional
    void fullUpdateBeperkingscategorieWithPatch() throws Exception {
        // Initialize the database
        beperkingscategorieRepository.saveAndFlush(beperkingscategorie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beperkingscategorie using partial update
        Beperkingscategorie partialUpdatedBeperkingscategorie = new Beperkingscategorie();
        partialUpdatedBeperkingscategorie.setId(beperkingscategorie.getId());

        partialUpdatedBeperkingscategorie.code(UPDATED_CODE).wet(UPDATED_WET);

        restBeperkingscategorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeperkingscategorie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeperkingscategorie))
            )
            .andExpect(status().isOk());

        // Validate the Beperkingscategorie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeperkingscategorieUpdatableFieldsEquals(
            partialUpdatedBeperkingscategorie,
            getPersistedBeperkingscategorie(partialUpdatedBeperkingscategorie)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBeperkingscategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscategorie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeperkingscategorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, beperkingscategorie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beperkingscategorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperkingscategorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBeperkingscategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscategorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingscategorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beperkingscategorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperkingscategorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBeperkingscategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingscategorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingscategorieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(beperkingscategorie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beperkingscategorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBeperkingscategorie() throws Exception {
        // Initialize the database
        beperkingscategorieRepository.saveAndFlush(beperkingscategorie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the beperkingscategorie
        restBeperkingscategorieMockMvc
            .perform(delete(ENTITY_API_URL_ID, beperkingscategorie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return beperkingscategorieRepository.count();
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

    protected Beperkingscategorie getPersistedBeperkingscategorie(Beperkingscategorie beperkingscategorie) {
        return beperkingscategorieRepository.findById(beperkingscategorie.getId()).orElseThrow();
    }

    protected void assertPersistedBeperkingscategorieToMatchAllProperties(Beperkingscategorie expectedBeperkingscategorie) {
        assertBeperkingscategorieAllPropertiesEquals(
            expectedBeperkingscategorie,
            getPersistedBeperkingscategorie(expectedBeperkingscategorie)
        );
    }

    protected void assertPersistedBeperkingscategorieToMatchUpdatableProperties(Beperkingscategorie expectedBeperkingscategorie) {
        assertBeperkingscategorieAllUpdatablePropertiesEquals(
            expectedBeperkingscategorie,
            getPersistedBeperkingscategorie(expectedBeperkingscategorie)
        );
    }
}
