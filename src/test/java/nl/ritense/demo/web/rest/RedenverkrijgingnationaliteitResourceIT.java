package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RedenverkrijgingnationaliteitAsserts.*;
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
import nl.ritense.demo.domain.Redenverkrijgingnationaliteit;
import nl.ritense.demo.repository.RedenverkrijgingnationaliteitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RedenverkrijgingnationaliteitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RedenverkrijgingnationaliteitResourceIT {

    private static final LocalDate DEFAULT_DATUMAANVANGGELDIGHEIDVERKRIJGING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANVANGGELDIGHEIDVERKRIJGING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDVERKRIJGING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDVERKRIJGING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OMSCHRIJVINGVERKRIJGING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVINGVERKRIJGING = "BBBBBBBBBB";

    private static final String DEFAULT_REDENNUMMERVERKRIJGING = "AAAAAAAAAA";
    private static final String UPDATED_REDENNUMMERVERKRIJGING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/redenverkrijgingnationaliteits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RedenverkrijgingnationaliteitRepository redenverkrijgingnationaliteitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRedenverkrijgingnationaliteitMockMvc;

    private Redenverkrijgingnationaliteit redenverkrijgingnationaliteit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Redenverkrijgingnationaliteit createEntity(EntityManager em) {
        Redenverkrijgingnationaliteit redenverkrijgingnationaliteit = new Redenverkrijgingnationaliteit()
            .datumaanvanggeldigheidverkrijging(DEFAULT_DATUMAANVANGGELDIGHEIDVERKRIJGING)
            .datumeindegeldigheidverkrijging(DEFAULT_DATUMEINDEGELDIGHEIDVERKRIJGING)
            .omschrijvingverkrijging(DEFAULT_OMSCHRIJVINGVERKRIJGING)
            .redennummerverkrijging(DEFAULT_REDENNUMMERVERKRIJGING);
        return redenverkrijgingnationaliteit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Redenverkrijgingnationaliteit createUpdatedEntity(EntityManager em) {
        Redenverkrijgingnationaliteit redenverkrijgingnationaliteit = new Redenverkrijgingnationaliteit()
            .datumaanvanggeldigheidverkrijging(UPDATED_DATUMAANVANGGELDIGHEIDVERKRIJGING)
            .datumeindegeldigheidverkrijging(UPDATED_DATUMEINDEGELDIGHEIDVERKRIJGING)
            .omschrijvingverkrijging(UPDATED_OMSCHRIJVINGVERKRIJGING)
            .redennummerverkrijging(UPDATED_REDENNUMMERVERKRIJGING);
        return redenverkrijgingnationaliteit;
    }

    @BeforeEach
    public void initTest() {
        redenverkrijgingnationaliteit = createEntity(em);
    }

    @Test
    @Transactional
    void createRedenverkrijgingnationaliteit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Redenverkrijgingnationaliteit
        var returnedRedenverkrijgingnationaliteit = om.readValue(
            restRedenverkrijgingnationaliteitMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(redenverkrijgingnationaliteit))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Redenverkrijgingnationaliteit.class
        );

        // Validate the Redenverkrijgingnationaliteit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRedenverkrijgingnationaliteitUpdatableFieldsEquals(
            returnedRedenverkrijgingnationaliteit,
            getPersistedRedenverkrijgingnationaliteit(returnedRedenverkrijgingnationaliteit)
        );
    }

    @Test
    @Transactional
    void createRedenverkrijgingnationaliteitWithExistingId() throws Exception {
        // Create the Redenverkrijgingnationaliteit with an existing ID
        redenverkrijgingnationaliteit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRedenverkrijgingnationaliteitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(redenverkrijgingnationaliteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Redenverkrijgingnationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRedenverkrijgingnationaliteits() throws Exception {
        // Initialize the database
        redenverkrijgingnationaliteitRepository.saveAndFlush(redenverkrijgingnationaliteit);

        // Get all the redenverkrijgingnationaliteitList
        restRedenverkrijgingnationaliteitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(redenverkrijgingnationaliteit.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].datumaanvanggeldigheidverkrijging").value(hasItem(DEFAULT_DATUMAANVANGGELDIGHEIDVERKRIJGING.toString()))
            )
            .andExpect(jsonPath("$.[*].datumeindegeldigheidverkrijging").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDVERKRIJGING.toString())))
            .andExpect(jsonPath("$.[*].omschrijvingverkrijging").value(hasItem(DEFAULT_OMSCHRIJVINGVERKRIJGING)))
            .andExpect(jsonPath("$.[*].redennummerverkrijging").value(hasItem(DEFAULT_REDENNUMMERVERKRIJGING)));
    }

    @Test
    @Transactional
    void getRedenverkrijgingnationaliteit() throws Exception {
        // Initialize the database
        redenverkrijgingnationaliteitRepository.saveAndFlush(redenverkrijgingnationaliteit);

        // Get the redenverkrijgingnationaliteit
        restRedenverkrijgingnationaliteitMockMvc
            .perform(get(ENTITY_API_URL_ID, redenverkrijgingnationaliteit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(redenverkrijgingnationaliteit.getId().intValue()))
            .andExpect(jsonPath("$.datumaanvanggeldigheidverkrijging").value(DEFAULT_DATUMAANVANGGELDIGHEIDVERKRIJGING.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidverkrijging").value(DEFAULT_DATUMEINDEGELDIGHEIDVERKRIJGING.toString()))
            .andExpect(jsonPath("$.omschrijvingverkrijging").value(DEFAULT_OMSCHRIJVINGVERKRIJGING))
            .andExpect(jsonPath("$.redennummerverkrijging").value(DEFAULT_REDENNUMMERVERKRIJGING));
    }

    @Test
    @Transactional
    void getNonExistingRedenverkrijgingnationaliteit() throws Exception {
        // Get the redenverkrijgingnationaliteit
        restRedenverkrijgingnationaliteitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRedenverkrijgingnationaliteit() throws Exception {
        // Initialize the database
        redenverkrijgingnationaliteitRepository.saveAndFlush(redenverkrijgingnationaliteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the redenverkrijgingnationaliteit
        Redenverkrijgingnationaliteit updatedRedenverkrijgingnationaliteit = redenverkrijgingnationaliteitRepository
            .findById(redenverkrijgingnationaliteit.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedRedenverkrijgingnationaliteit are not directly saved in db
        em.detach(updatedRedenverkrijgingnationaliteit);
        updatedRedenverkrijgingnationaliteit
            .datumaanvanggeldigheidverkrijging(UPDATED_DATUMAANVANGGELDIGHEIDVERKRIJGING)
            .datumeindegeldigheidverkrijging(UPDATED_DATUMEINDEGELDIGHEIDVERKRIJGING)
            .omschrijvingverkrijging(UPDATED_OMSCHRIJVINGVERKRIJGING)
            .redennummerverkrijging(UPDATED_REDENNUMMERVERKRIJGING);

        restRedenverkrijgingnationaliteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRedenverkrijgingnationaliteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRedenverkrijgingnationaliteit))
            )
            .andExpect(status().isOk());

        // Validate the Redenverkrijgingnationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRedenverkrijgingnationaliteitToMatchAllProperties(updatedRedenverkrijgingnationaliteit);
    }

    @Test
    @Transactional
    void putNonExistingRedenverkrijgingnationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenverkrijgingnationaliteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRedenverkrijgingnationaliteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, redenverkrijgingnationaliteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(redenverkrijgingnationaliteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Redenverkrijgingnationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRedenverkrijgingnationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenverkrijgingnationaliteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedenverkrijgingnationaliteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(redenverkrijgingnationaliteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Redenverkrijgingnationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRedenverkrijgingnationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenverkrijgingnationaliteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedenverkrijgingnationaliteitMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(redenverkrijgingnationaliteit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Redenverkrijgingnationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRedenverkrijgingnationaliteitWithPatch() throws Exception {
        // Initialize the database
        redenverkrijgingnationaliteitRepository.saveAndFlush(redenverkrijgingnationaliteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the redenverkrijgingnationaliteit using partial update
        Redenverkrijgingnationaliteit partialUpdatedRedenverkrijgingnationaliteit = new Redenverkrijgingnationaliteit();
        partialUpdatedRedenverkrijgingnationaliteit.setId(redenverkrijgingnationaliteit.getId());

        partialUpdatedRedenverkrijgingnationaliteit
            .datumaanvanggeldigheidverkrijging(UPDATED_DATUMAANVANGGELDIGHEIDVERKRIJGING)
            .datumeindegeldigheidverkrijging(UPDATED_DATUMEINDEGELDIGHEIDVERKRIJGING)
            .omschrijvingverkrijging(UPDATED_OMSCHRIJVINGVERKRIJGING);

        restRedenverkrijgingnationaliteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRedenverkrijgingnationaliteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRedenverkrijgingnationaliteit))
            )
            .andExpect(status().isOk());

        // Validate the Redenverkrijgingnationaliteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRedenverkrijgingnationaliteitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRedenverkrijgingnationaliteit, redenverkrijgingnationaliteit),
            getPersistedRedenverkrijgingnationaliteit(redenverkrijgingnationaliteit)
        );
    }

    @Test
    @Transactional
    void fullUpdateRedenverkrijgingnationaliteitWithPatch() throws Exception {
        // Initialize the database
        redenverkrijgingnationaliteitRepository.saveAndFlush(redenverkrijgingnationaliteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the redenverkrijgingnationaliteit using partial update
        Redenverkrijgingnationaliteit partialUpdatedRedenverkrijgingnationaliteit = new Redenverkrijgingnationaliteit();
        partialUpdatedRedenverkrijgingnationaliteit.setId(redenverkrijgingnationaliteit.getId());

        partialUpdatedRedenverkrijgingnationaliteit
            .datumaanvanggeldigheidverkrijging(UPDATED_DATUMAANVANGGELDIGHEIDVERKRIJGING)
            .datumeindegeldigheidverkrijging(UPDATED_DATUMEINDEGELDIGHEIDVERKRIJGING)
            .omschrijvingverkrijging(UPDATED_OMSCHRIJVINGVERKRIJGING)
            .redennummerverkrijging(UPDATED_REDENNUMMERVERKRIJGING);

        restRedenverkrijgingnationaliteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRedenverkrijgingnationaliteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRedenverkrijgingnationaliteit))
            )
            .andExpect(status().isOk());

        // Validate the Redenverkrijgingnationaliteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRedenverkrijgingnationaliteitUpdatableFieldsEquals(
            partialUpdatedRedenverkrijgingnationaliteit,
            getPersistedRedenverkrijgingnationaliteit(partialUpdatedRedenverkrijgingnationaliteit)
        );
    }

    @Test
    @Transactional
    void patchNonExistingRedenverkrijgingnationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenverkrijgingnationaliteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRedenverkrijgingnationaliteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, redenverkrijgingnationaliteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(redenverkrijgingnationaliteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Redenverkrijgingnationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRedenverkrijgingnationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenverkrijgingnationaliteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedenverkrijgingnationaliteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(redenverkrijgingnationaliteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Redenverkrijgingnationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRedenverkrijgingnationaliteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        redenverkrijgingnationaliteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRedenverkrijgingnationaliteitMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(redenverkrijgingnationaliteit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Redenverkrijgingnationaliteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRedenverkrijgingnationaliteit() throws Exception {
        // Initialize the database
        redenverkrijgingnationaliteitRepository.saveAndFlush(redenverkrijgingnationaliteit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the redenverkrijgingnationaliteit
        restRedenverkrijgingnationaliteitMockMvc
            .perform(delete(ENTITY_API_URL_ID, redenverkrijgingnationaliteit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return redenverkrijgingnationaliteitRepository.count();
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

    protected Redenverkrijgingnationaliteit getPersistedRedenverkrijgingnationaliteit(
        Redenverkrijgingnationaliteit redenverkrijgingnationaliteit
    ) {
        return redenverkrijgingnationaliteitRepository.findById(redenverkrijgingnationaliteit.getId()).orElseThrow();
    }

    protected void assertPersistedRedenverkrijgingnationaliteitToMatchAllProperties(
        Redenverkrijgingnationaliteit expectedRedenverkrijgingnationaliteit
    ) {
        assertRedenverkrijgingnationaliteitAllPropertiesEquals(
            expectedRedenverkrijgingnationaliteit,
            getPersistedRedenverkrijgingnationaliteit(expectedRedenverkrijgingnationaliteit)
        );
    }

    protected void assertPersistedRedenverkrijgingnationaliteitToMatchUpdatableProperties(
        Redenverkrijgingnationaliteit expectedRedenverkrijgingnationaliteit
    ) {
        assertRedenverkrijgingnationaliteitAllUpdatablePropertiesEquals(
            expectedRedenverkrijgingnationaliteit,
            getPersistedRedenverkrijgingnationaliteit(expectedRedenverkrijgingnationaliteit)
        );
    }
}
