package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RedenverliesnationaliteitAsserts.*;
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
import nl.ritense.demo.domain.Redenverliesnationaliteit;
import nl.ritense.demo.repository.RedenverliesnationaliteitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RedenverliesnationaliteitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RedenverliesnationaliteitResourceIT {

    private static final LocalDate DEFAULT_DATUMAANVANGGELDIGHEIDVERLIES = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANVANGGELDIGHEIDVERLIES = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDVERLIES = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDVERLIES = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OMSCHRIJVINGVERLIES = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVINGVERLIES = "BBBBBBBBBB";

    private static final String DEFAULT_REDENNUMMERVERLIES = "AAAAAAAAAA";
    private static final String UPDATED_REDENNUMMERVERLIES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/redenverliesnationaliteits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RedenverliesnationaliteitRepository redenverliesnationaliteitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRedenverliesnationaliteitMockMvc;

    private Redenverliesnationaliteit redenverliesnationaliteit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Redenverliesnationaliteit createEntity(EntityManager em) {
        Redenverliesnationaliteit redenverliesnationaliteit = new Redenverliesnationaliteit()
            .datumaanvanggeldigheidverlies(DEFAULT_DATUMAANVANGGELDIGHEIDVERLIES)
            .datumeindegeldigheidverlies(DEFAULT_DATUMEINDEGELDIGHEIDVERLIES)
            .omschrijvingverlies(DEFAULT_OMSCHRIJVINGVERLIES)
            .redennummerverlies(DEFAULT_REDENNUMMERVERLIES);
        return redenverliesnationaliteit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Redenverliesnationaliteit createUpdatedEntity(EntityManager em) {
        Redenverliesnationaliteit redenverliesnationaliteit = new Redenverliesnationaliteit()
            .datumaanvanggeldigheidverlies(UPDATED_DATUMAANVANGGELDIGHEIDVERLIES)
            .datumeindegeldigheidverlies(UPDATED_DATUMEINDEGELDIGHEIDVERLIES)
            .omschrijvingverlies(UPDATED_OMSCHRIJVINGVERLIES)
            .redennummerverlies(UPDATED_REDENNUMMERVERLIES);
        return redenverliesnationaliteit;
    }

    @BeforeEach
    public void initTest() {
        redenverliesnationaliteit = createEntity(em);
    }

    @Test
    @Transactional
    void createRedenverliesnationaliteit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Redenverliesnationaliteit
        var returnedRedenverliesnationaliteit = om.readValue(
            restRedenverliesnationaliteitMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(redenverliesnationaliteit))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Redenverliesnationaliteit.class
        );

        // Validate the Redenverliesnationaliteit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRedenverliesnationaliteitUpdatableFieldsEquals(
            returnedRedenverliesnationaliteit,
            getPersistedRedenverliesnationaliteit(returnedRedenverliesnationaliteit)
        );
    }

    @Test
    @Transactional
    void createRedenverliesnationaliteitWithExistingId() throws Exception {
        // Create the Redenverliesnationaliteit with an existing ID
        redenverliesnationaliteit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRedenverliesnationaliteitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(redenverliesnationaliteit)))
            .andExpect(status().isBadRequest());

        // Validate the Redenverliesnationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRedenverliesnationaliteits() throws Exception {
        // Initialize the database
        redenverliesnationaliteitRepository.saveAndFlush(redenverliesnationaliteit);

        // Get all the redenverliesnationaliteitList
        restRedenverliesnationaliteitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(redenverliesnationaliteit.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumaanvanggeldigheidverlies").value(hasItem(DEFAULT_DATUMAANVANGGELDIGHEIDVERLIES.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheidverlies").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDVERLIES.toString())))
            .andExpect(jsonPath("$.[*].omschrijvingverlies").value(hasItem(DEFAULT_OMSCHRIJVINGVERLIES)))
            .andExpect(jsonPath("$.[*].redennummerverlies").value(hasItem(DEFAULT_REDENNUMMERVERLIES)));
    }

    @Test
    @Transactional
    void getRedenverliesnationaliteit() throws Exception {
        // Initialize the database
        redenverliesnationaliteitRepository.saveAndFlush(redenverliesnationaliteit);

        // Get the redenverliesnationaliteit
        restRedenverliesnationaliteitMockMvc
            .perform(get(ENTITY_API_URL_ID, redenverliesnationaliteit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(redenverliesnationaliteit.getId().intValue()))
            .andExpect(jsonPath("$.datumaanvanggeldigheidverlies").value(DEFAULT_DATUMAANVANGGELDIGHEIDVERLIES.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidverlies").value(DEFAULT_DATUMEINDEGELDIGHEIDVERLIES.toString()))
            .andExpect(jsonPath("$.omschrijvingverlies").value(DEFAULT_OMSCHRIJVINGVERLIES))
            .andExpect(jsonPath("$.redennummerverlies").value(DEFAULT_REDENNUMMERVERLIES));
    }

    @Test
    @Transactional
    void getNonExistingRedenverliesnationaliteit() throws Exception {
        // Get the redenverliesnationaliteit
        restRedenverliesnationaliteitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRedenverliesnationaliteit() throws Exception {
        // Initialize the database
        redenverliesnationaliteitRepository.saveAndFlush(redenverliesnationaliteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the redenverliesnationaliteit
        Redenverliesnationaliteit updatedRedenverliesnationaliteit = redenverliesnationaliteitRepository
            .findById(redenverliesnationaliteit.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedRedenverliesnationaliteit are not directly saved in db
        em.detach(updatedRedenverliesnationaliteit);
        updatedRedenverliesnationaliteit
            .datumaanvanggeldigheidverlies(UPDATED_DATUMAANVANGGELDIGHEIDVERLIES)
            .datumeindegeldigheidverlies(UPDATED_DATUMEINDEGELDIGHEIDVERLIES)
            .omschrijvingverlies(UPDATED_OMSCHRIJVINGVERLIES)
            .redennummerverlies(UPDATED_REDENNUMMERVERLIES);

        restRedenverliesnationaliteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRedenverliesnationaliteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRedenverliesnationaliteit))
            )
            .andExpect(status().isOk());

        // Validate the Redenverliesnationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRedenverliesnationaliteitToMatchAllProperties(updatedRedenverliesnationaliteit);
    }

    @Test
    @Transactional
    void putNonExistingRedenverliesnationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenverliesnationaliteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRedenverliesnationaliteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, redenverliesnationaliteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(redenverliesnationaliteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Redenverliesnationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRedenverliesnationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenverliesnationaliteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedenverliesnationaliteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(redenverliesnationaliteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Redenverliesnationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRedenverliesnationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenverliesnationaliteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedenverliesnationaliteitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(redenverliesnationaliteit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Redenverliesnationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRedenverliesnationaliteitWithPatch() throws Exception {
        // Initialize the database
        redenverliesnationaliteitRepository.saveAndFlush(redenverliesnationaliteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the redenverliesnationaliteit using partial update
        Redenverliesnationaliteit partialUpdatedRedenverliesnationaliteit = new Redenverliesnationaliteit();
        partialUpdatedRedenverliesnationaliteit.setId(redenverliesnationaliteit.getId());

        partialUpdatedRedenverliesnationaliteit.datumeindegeldigheidverlies(UPDATED_DATUMEINDEGELDIGHEIDVERLIES);

        restRedenverliesnationaliteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRedenverliesnationaliteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRedenverliesnationaliteit))
            )
            .andExpect(status().isOk());

        // Validate the Redenverliesnationaliteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRedenverliesnationaliteitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRedenverliesnationaliteit, redenverliesnationaliteit),
            getPersistedRedenverliesnationaliteit(redenverliesnationaliteit)
        );
    }

    @Test
    @Transactional
    void fullUpdateRedenverliesnationaliteitWithPatch() throws Exception {
        // Initialize the database
        redenverliesnationaliteitRepository.saveAndFlush(redenverliesnationaliteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the redenverliesnationaliteit using partial update
        Redenverliesnationaliteit partialUpdatedRedenverliesnationaliteit = new Redenverliesnationaliteit();
        partialUpdatedRedenverliesnationaliteit.setId(redenverliesnationaliteit.getId());

        partialUpdatedRedenverliesnationaliteit
            .datumaanvanggeldigheidverlies(UPDATED_DATUMAANVANGGELDIGHEIDVERLIES)
            .datumeindegeldigheidverlies(UPDATED_DATUMEINDEGELDIGHEIDVERLIES)
            .omschrijvingverlies(UPDATED_OMSCHRIJVINGVERLIES)
            .redennummerverlies(UPDATED_REDENNUMMERVERLIES);

        restRedenverliesnationaliteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRedenverliesnationaliteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRedenverliesnationaliteit))
            )
            .andExpect(status().isOk());

        // Validate the Redenverliesnationaliteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRedenverliesnationaliteitUpdatableFieldsEquals(
            partialUpdatedRedenverliesnationaliteit,
            getPersistedRedenverliesnationaliteit(partialUpdatedRedenverliesnationaliteit)
        );
    }

    @Test
    @Transactional
    void patchNonExistingRedenverliesnationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenverliesnationaliteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRedenverliesnationaliteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, redenverliesnationaliteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(redenverliesnationaliteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Redenverliesnationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRedenverliesnationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenverliesnationaliteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedenverliesnationaliteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(redenverliesnationaliteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Redenverliesnationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRedenverliesnationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenverliesnationaliteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedenverliesnationaliteitMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(redenverliesnationaliteit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Redenverliesnationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRedenverliesnationaliteit() throws Exception {
        // Initialize the database
        redenverliesnationaliteitRepository.saveAndFlush(redenverliesnationaliteit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the redenverliesnationaliteit
        restRedenverliesnationaliteitMockMvc
            .perform(delete(ENTITY_API_URL_ID, redenverliesnationaliteit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return redenverliesnationaliteitRepository.count();
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

    protected Redenverliesnationaliteit getPersistedRedenverliesnationaliteit(Redenverliesnationaliteit redenverliesnationaliteit) {
        return redenverliesnationaliteitRepository.findById(redenverliesnationaliteit.getId()).orElseThrow();
    }

    protected void assertPersistedRedenverliesnationaliteitToMatchAllProperties(
        Redenverliesnationaliteit expectedRedenverliesnationaliteit
    ) {
        assertRedenverliesnationaliteitAllPropertiesEquals(
            expectedRedenverliesnationaliteit,
            getPersistedRedenverliesnationaliteit(expectedRedenverliesnationaliteit)
        );
    }

    protected void assertPersistedRedenverliesnationaliteitToMatchUpdatableProperties(
        Redenverliesnationaliteit expectedRedenverliesnationaliteit
    ) {
        assertRedenverliesnationaliteitAllUpdatablePropertiesEquals(
            expectedRedenverliesnationaliteit,
            getPersistedRedenverliesnationaliteit(expectedRedenverliesnationaliteit)
        );
    }
}
