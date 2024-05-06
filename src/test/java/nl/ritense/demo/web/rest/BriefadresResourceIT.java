package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BriefadresAsserts.*;
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
import nl.ritense.demo.domain.Briefadres;
import nl.ritense.demo.repository.BriefadresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BriefadresResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BriefadresResourceIT {

    private static final String DEFAULT_ADRESFUNCTIE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESFUNCTIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMAANVANG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANVANG = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OMSCHRIJVINGAANGIFTE = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVINGAANGIFTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/briefadres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BriefadresRepository briefadresRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBriefadresMockMvc;

    private Briefadres briefadres;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Briefadres createEntity(EntityManager em) {
        Briefadres briefadres = new Briefadres()
            .adresfunctie(DEFAULT_ADRESFUNCTIE)
            .datumaanvang(DEFAULT_DATUMAANVANG)
            .datumeinde(DEFAULT_DATUMEINDE)
            .omschrijvingaangifte(DEFAULT_OMSCHRIJVINGAANGIFTE);
        return briefadres;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Briefadres createUpdatedEntity(EntityManager em) {
        Briefadres briefadres = new Briefadres()
            .adresfunctie(UPDATED_ADRESFUNCTIE)
            .datumaanvang(UPDATED_DATUMAANVANG)
            .datumeinde(UPDATED_DATUMEINDE)
            .omschrijvingaangifte(UPDATED_OMSCHRIJVINGAANGIFTE);
        return briefadres;
    }

    @BeforeEach
    public void initTest() {
        briefadres = createEntity(em);
    }

    @Test
    @Transactional
    void createBriefadres() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Briefadres
        var returnedBriefadres = om.readValue(
            restBriefadresMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(briefadres)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Briefadres.class
        );

        // Validate the Briefadres in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBriefadresUpdatableFieldsEquals(returnedBriefadres, getPersistedBriefadres(returnedBriefadres));
    }

    @Test
    @Transactional
    void createBriefadresWithExistingId() throws Exception {
        // Create the Briefadres with an existing ID
        briefadres.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBriefadresMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(briefadres)))
            .andExpect(status().isBadRequest());

        // Validate the Briefadres in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBriefadres() throws Exception {
        // Initialize the database
        briefadresRepository.saveAndFlush(briefadres);

        // Get all the briefadresList
        restBriefadresMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(briefadres.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresfunctie").value(hasItem(DEFAULT_ADRESFUNCTIE)))
            .andExpect(jsonPath("$.[*].datumaanvang").value(hasItem(DEFAULT_DATUMAANVANG.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].omschrijvingaangifte").value(hasItem(DEFAULT_OMSCHRIJVINGAANGIFTE)));
    }

    @Test
    @Transactional
    void getBriefadres() throws Exception {
        // Initialize the database
        briefadresRepository.saveAndFlush(briefadres);

        // Get the briefadres
        restBriefadresMockMvc
            .perform(get(ENTITY_API_URL_ID, briefadres.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(briefadres.getId().intValue()))
            .andExpect(jsonPath("$.adresfunctie").value(DEFAULT_ADRESFUNCTIE))
            .andExpect(jsonPath("$.datumaanvang").value(DEFAULT_DATUMAANVANG.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.omschrijvingaangifte").value(DEFAULT_OMSCHRIJVINGAANGIFTE));
    }

    @Test
    @Transactional
    void getNonExistingBriefadres() throws Exception {
        // Get the briefadres
        restBriefadresMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBriefadres() throws Exception {
        // Initialize the database
        briefadresRepository.saveAndFlush(briefadres);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the briefadres
        Briefadres updatedBriefadres = briefadresRepository.findById(briefadres.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBriefadres are not directly saved in db
        em.detach(updatedBriefadres);
        updatedBriefadres
            .adresfunctie(UPDATED_ADRESFUNCTIE)
            .datumaanvang(UPDATED_DATUMAANVANG)
            .datumeinde(UPDATED_DATUMEINDE)
            .omschrijvingaangifte(UPDATED_OMSCHRIJVINGAANGIFTE);

        restBriefadresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBriefadres.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBriefadres))
            )
            .andExpect(status().isOk());

        // Validate the Briefadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBriefadresToMatchAllProperties(updatedBriefadres);
    }

    @Test
    @Transactional
    void putNonExistingBriefadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        briefadres.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBriefadresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, briefadres.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(briefadres))
            )
            .andExpect(status().isBadRequest());

        // Validate the Briefadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBriefadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        briefadres.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBriefadresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(briefadres))
            )
            .andExpect(status().isBadRequest());

        // Validate the Briefadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBriefadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        briefadres.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBriefadresMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(briefadres)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Briefadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBriefadresWithPatch() throws Exception {
        // Initialize the database
        briefadresRepository.saveAndFlush(briefadres);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the briefadres using partial update
        Briefadres partialUpdatedBriefadres = new Briefadres();
        partialUpdatedBriefadres.setId(briefadres.getId());

        partialUpdatedBriefadres
            .datumaanvang(UPDATED_DATUMAANVANG)
            .datumeinde(UPDATED_DATUMEINDE)
            .omschrijvingaangifte(UPDATED_OMSCHRIJVINGAANGIFTE);

        restBriefadresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBriefadres.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBriefadres))
            )
            .andExpect(status().isOk());

        // Validate the Briefadres in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBriefadresUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBriefadres, briefadres),
            getPersistedBriefadres(briefadres)
        );
    }

    @Test
    @Transactional
    void fullUpdateBriefadresWithPatch() throws Exception {
        // Initialize the database
        briefadresRepository.saveAndFlush(briefadres);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the briefadres using partial update
        Briefadres partialUpdatedBriefadres = new Briefadres();
        partialUpdatedBriefadres.setId(briefadres.getId());

        partialUpdatedBriefadres
            .adresfunctie(UPDATED_ADRESFUNCTIE)
            .datumaanvang(UPDATED_DATUMAANVANG)
            .datumeinde(UPDATED_DATUMEINDE)
            .omschrijvingaangifte(UPDATED_OMSCHRIJVINGAANGIFTE);

        restBriefadresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBriefadres.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBriefadres))
            )
            .andExpect(status().isOk());

        // Validate the Briefadres in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBriefadresUpdatableFieldsEquals(partialUpdatedBriefadres, getPersistedBriefadres(partialUpdatedBriefadres));
    }

    @Test
    @Transactional
    void patchNonExistingBriefadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        briefadres.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBriefadresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, briefadres.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(briefadres))
            )
            .andExpect(status().isBadRequest());

        // Validate the Briefadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBriefadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        briefadres.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBriefadresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(briefadres))
            )
            .andExpect(status().isBadRequest());

        // Validate the Briefadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBriefadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        briefadres.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBriefadresMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(briefadres)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Briefadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBriefadres() throws Exception {
        // Initialize the database
        briefadresRepository.saveAndFlush(briefadres);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the briefadres
        restBriefadresMockMvc
            .perform(delete(ENTITY_API_URL_ID, briefadres.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return briefadresRepository.count();
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

    protected Briefadres getPersistedBriefadres(Briefadres briefadres) {
        return briefadresRepository.findById(briefadres.getId()).orElseThrow();
    }

    protected void assertPersistedBriefadresToMatchAllProperties(Briefadres expectedBriefadres) {
        assertBriefadresAllPropertiesEquals(expectedBriefadres, getPersistedBriefadres(expectedBriefadres));
    }

    protected void assertPersistedBriefadresToMatchUpdatableProperties(Briefadres expectedBriefadres) {
        assertBriefadresAllUpdatablePropertiesEquals(expectedBriefadres, getPersistedBriefadres(expectedBriefadres));
    }
}
