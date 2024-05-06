package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.StraatsectieAsserts.*;
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
import nl.ritense.demo.domain.Straatsectie;
import nl.ritense.demo.repository.StraatsectieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StraatsectieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StraatsectieResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_ZONECODE = "AAAAAAAAAA";
    private static final String UPDATED_ZONECODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/straatsecties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StraatsectieRepository straatsectieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStraatsectieMockMvc;

    private Straatsectie straatsectie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Straatsectie createEntity(EntityManager em) {
        Straatsectie straatsectie = new Straatsectie().code(DEFAULT_CODE).omschrijving(DEFAULT_OMSCHRIJVING).zonecode(DEFAULT_ZONECODE);
        return straatsectie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Straatsectie createUpdatedEntity(EntityManager em) {
        Straatsectie straatsectie = new Straatsectie().code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING).zonecode(UPDATED_ZONECODE);
        return straatsectie;
    }

    @BeforeEach
    public void initTest() {
        straatsectie = createEntity(em);
    }

    @Test
    @Transactional
    void createStraatsectie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Straatsectie
        var returnedStraatsectie = om.readValue(
            restStraatsectieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(straatsectie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Straatsectie.class
        );

        // Validate the Straatsectie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStraatsectieUpdatableFieldsEquals(returnedStraatsectie, getPersistedStraatsectie(returnedStraatsectie));
    }

    @Test
    @Transactional
    void createStraatsectieWithExistingId() throws Exception {
        // Create the Straatsectie with an existing ID
        straatsectie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStraatsectieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(straatsectie)))
            .andExpect(status().isBadRequest());

        // Validate the Straatsectie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStraatsecties() throws Exception {
        // Initialize the database
        straatsectieRepository.saveAndFlush(straatsectie);

        // Get all the straatsectieList
        restStraatsectieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(straatsectie.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].zonecode").value(hasItem(DEFAULT_ZONECODE)));
    }

    @Test
    @Transactional
    void getStraatsectie() throws Exception {
        // Initialize the database
        straatsectieRepository.saveAndFlush(straatsectie);

        // Get the straatsectie
        restStraatsectieMockMvc
            .perform(get(ENTITY_API_URL_ID, straatsectie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(straatsectie.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.zonecode").value(DEFAULT_ZONECODE));
    }

    @Test
    @Transactional
    void getNonExistingStraatsectie() throws Exception {
        // Get the straatsectie
        restStraatsectieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStraatsectie() throws Exception {
        // Initialize the database
        straatsectieRepository.saveAndFlush(straatsectie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the straatsectie
        Straatsectie updatedStraatsectie = straatsectieRepository.findById(straatsectie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStraatsectie are not directly saved in db
        em.detach(updatedStraatsectie);
        updatedStraatsectie.code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING).zonecode(UPDATED_ZONECODE);

        restStraatsectieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStraatsectie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStraatsectie))
            )
            .andExpect(status().isOk());

        // Validate the Straatsectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStraatsectieToMatchAllProperties(updatedStraatsectie);
    }

    @Test
    @Transactional
    void putNonExistingStraatsectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        straatsectie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStraatsectieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, straatsectie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(straatsectie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Straatsectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStraatsectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        straatsectie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStraatsectieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(straatsectie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Straatsectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStraatsectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        straatsectie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStraatsectieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(straatsectie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Straatsectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStraatsectieWithPatch() throws Exception {
        // Initialize the database
        straatsectieRepository.saveAndFlush(straatsectie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the straatsectie using partial update
        Straatsectie partialUpdatedStraatsectie = new Straatsectie();
        partialUpdatedStraatsectie.setId(straatsectie.getId());

        partialUpdatedStraatsectie.code(UPDATED_CODE);

        restStraatsectieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStraatsectie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStraatsectie))
            )
            .andExpect(status().isOk());

        // Validate the Straatsectie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStraatsectieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStraatsectie, straatsectie),
            getPersistedStraatsectie(straatsectie)
        );
    }

    @Test
    @Transactional
    void fullUpdateStraatsectieWithPatch() throws Exception {
        // Initialize the database
        straatsectieRepository.saveAndFlush(straatsectie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the straatsectie using partial update
        Straatsectie partialUpdatedStraatsectie = new Straatsectie();
        partialUpdatedStraatsectie.setId(straatsectie.getId());

        partialUpdatedStraatsectie.code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING).zonecode(UPDATED_ZONECODE);

        restStraatsectieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStraatsectie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStraatsectie))
            )
            .andExpect(status().isOk());

        // Validate the Straatsectie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStraatsectieUpdatableFieldsEquals(partialUpdatedStraatsectie, getPersistedStraatsectie(partialUpdatedStraatsectie));
    }

    @Test
    @Transactional
    void patchNonExistingStraatsectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        straatsectie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStraatsectieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, straatsectie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(straatsectie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Straatsectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStraatsectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        straatsectie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStraatsectieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(straatsectie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Straatsectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStraatsectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        straatsectie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStraatsectieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(straatsectie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Straatsectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStraatsectie() throws Exception {
        // Initialize the database
        straatsectieRepository.saveAndFlush(straatsectie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the straatsectie
        restStraatsectieMockMvc
            .perform(delete(ENTITY_API_URL_ID, straatsectie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return straatsectieRepository.count();
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

    protected Straatsectie getPersistedStraatsectie(Straatsectie straatsectie) {
        return straatsectieRepository.findById(straatsectie.getId()).orElseThrow();
    }

    protected void assertPersistedStraatsectieToMatchAllProperties(Straatsectie expectedStraatsectie) {
        assertStraatsectieAllPropertiesEquals(expectedStraatsectie, getPersistedStraatsectie(expectedStraatsectie));
    }

    protected void assertPersistedStraatsectieToMatchUpdatableProperties(Straatsectie expectedStraatsectie) {
        assertStraatsectieAllUpdatablePropertiesEquals(expectedStraatsectie, getPersistedStraatsectie(expectedStraatsectie));
    }
}
