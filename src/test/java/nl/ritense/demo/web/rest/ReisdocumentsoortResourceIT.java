package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ReisdocumentsoortAsserts.*;
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
import nl.ritense.demo.domain.Reisdocumentsoort;
import nl.ritense.demo.repository.ReisdocumentsoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ReisdocumentsoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReisdocumentsoortResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDREISDOCUMENTSOORT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDREISDOCUMENTSOORT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDREISDOCUMENTSOORT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDREISDOCUMENTSOORT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REISDOCUMENTCODE = "AAAAAAAAAA";
    private static final String UPDATED_REISDOCUMENTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_REISDOCUMENTOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_REISDOCUMENTOMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/reisdocumentsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ReisdocumentsoortRepository reisdocumentsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReisdocumentsoortMockMvc;

    private Reisdocumentsoort reisdocumentsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reisdocumentsoort createEntity(EntityManager em) {
        Reisdocumentsoort reisdocumentsoort = new Reisdocumentsoort()
            .datumbegingeldigheidreisdocumentsoort(DEFAULT_DATUMBEGINGELDIGHEIDREISDOCUMENTSOORT)
            .datumeindegeldigheidreisdocumentsoort(DEFAULT_DATUMEINDEGELDIGHEIDREISDOCUMENTSOORT)
            .reisdocumentcode(DEFAULT_REISDOCUMENTCODE)
            .reisdocumentomschrijving(DEFAULT_REISDOCUMENTOMSCHRIJVING);
        return reisdocumentsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reisdocumentsoort createUpdatedEntity(EntityManager em) {
        Reisdocumentsoort reisdocumentsoort = new Reisdocumentsoort()
            .datumbegingeldigheidreisdocumentsoort(UPDATED_DATUMBEGINGELDIGHEIDREISDOCUMENTSOORT)
            .datumeindegeldigheidreisdocumentsoort(UPDATED_DATUMEINDEGELDIGHEIDREISDOCUMENTSOORT)
            .reisdocumentcode(UPDATED_REISDOCUMENTCODE)
            .reisdocumentomschrijving(UPDATED_REISDOCUMENTOMSCHRIJVING);
        return reisdocumentsoort;
    }

    @BeforeEach
    public void initTest() {
        reisdocumentsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createReisdocumentsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Reisdocumentsoort
        var returnedReisdocumentsoort = om.readValue(
            restReisdocumentsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reisdocumentsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Reisdocumentsoort.class
        );

        // Validate the Reisdocumentsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertReisdocumentsoortUpdatableFieldsEquals(returnedReisdocumentsoort, getPersistedReisdocumentsoort(returnedReisdocumentsoort));
    }

    @Test
    @Transactional
    void createReisdocumentsoortWithExistingId() throws Exception {
        // Create the Reisdocumentsoort with an existing ID
        reisdocumentsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReisdocumentsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reisdocumentsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Reisdocumentsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReisdocumentsoorts() throws Exception {
        // Initialize the database
        reisdocumentsoortRepository.saveAndFlush(reisdocumentsoort);

        // Get all the reisdocumentsoortList
        restReisdocumentsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reisdocumentsoort.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidreisdocumentsoort").value(
                    hasItem(DEFAULT_DATUMBEGINGELDIGHEIDREISDOCUMENTSOORT.toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidreisdocumentsoort").value(
                    hasItem(DEFAULT_DATUMEINDEGELDIGHEIDREISDOCUMENTSOORT.toString())
                )
            )
            .andExpect(jsonPath("$.[*].reisdocumentcode").value(hasItem(DEFAULT_REISDOCUMENTCODE)))
            .andExpect(jsonPath("$.[*].reisdocumentomschrijving").value(hasItem(DEFAULT_REISDOCUMENTOMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getReisdocumentsoort() throws Exception {
        // Initialize the database
        reisdocumentsoortRepository.saveAndFlush(reisdocumentsoort);

        // Get the reisdocumentsoort
        restReisdocumentsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, reisdocumentsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reisdocumentsoort.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheidreisdocumentsoort").value(DEFAULT_DATUMBEGINGELDIGHEIDREISDOCUMENTSOORT.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidreisdocumentsoort").value(DEFAULT_DATUMEINDEGELDIGHEIDREISDOCUMENTSOORT.toString()))
            .andExpect(jsonPath("$.reisdocumentcode").value(DEFAULT_REISDOCUMENTCODE))
            .andExpect(jsonPath("$.reisdocumentomschrijving").value(DEFAULT_REISDOCUMENTOMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingReisdocumentsoort() throws Exception {
        // Get the reisdocumentsoort
        restReisdocumentsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReisdocumentsoort() throws Exception {
        // Initialize the database
        reisdocumentsoortRepository.saveAndFlush(reisdocumentsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reisdocumentsoort
        Reisdocumentsoort updatedReisdocumentsoort = reisdocumentsoortRepository.findById(reisdocumentsoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedReisdocumentsoort are not directly saved in db
        em.detach(updatedReisdocumentsoort);
        updatedReisdocumentsoort
            .datumbegingeldigheidreisdocumentsoort(UPDATED_DATUMBEGINGELDIGHEIDREISDOCUMENTSOORT)
            .datumeindegeldigheidreisdocumentsoort(UPDATED_DATUMEINDEGELDIGHEIDREISDOCUMENTSOORT)
            .reisdocumentcode(UPDATED_REISDOCUMENTCODE)
            .reisdocumentomschrijving(UPDATED_REISDOCUMENTOMSCHRIJVING);

        restReisdocumentsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReisdocumentsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedReisdocumentsoort))
            )
            .andExpect(status().isOk());

        // Validate the Reisdocumentsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedReisdocumentsoortToMatchAllProperties(updatedReisdocumentsoort);
    }

    @Test
    @Transactional
    void putNonExistingReisdocumentsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reisdocumentsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReisdocumentsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reisdocumentsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reisdocumentsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reisdocumentsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReisdocumentsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reisdocumentsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReisdocumentsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reisdocumentsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reisdocumentsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReisdocumentsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reisdocumentsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReisdocumentsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reisdocumentsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reisdocumentsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReisdocumentsoortWithPatch() throws Exception {
        // Initialize the database
        reisdocumentsoortRepository.saveAndFlush(reisdocumentsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reisdocumentsoort using partial update
        Reisdocumentsoort partialUpdatedReisdocumentsoort = new Reisdocumentsoort();
        partialUpdatedReisdocumentsoort.setId(reisdocumentsoort.getId());

        partialUpdatedReisdocumentsoort
            .datumbegingeldigheidreisdocumentsoort(UPDATED_DATUMBEGINGELDIGHEIDREISDOCUMENTSOORT)
            .datumeindegeldigheidreisdocumentsoort(UPDATED_DATUMEINDEGELDIGHEIDREISDOCUMENTSOORT)
            .reisdocumentcode(UPDATED_REISDOCUMENTCODE);

        restReisdocumentsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReisdocumentsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReisdocumentsoort))
            )
            .andExpect(status().isOk());

        // Validate the Reisdocumentsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReisdocumentsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedReisdocumentsoort, reisdocumentsoort),
            getPersistedReisdocumentsoort(reisdocumentsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateReisdocumentsoortWithPatch() throws Exception {
        // Initialize the database
        reisdocumentsoortRepository.saveAndFlush(reisdocumentsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reisdocumentsoort using partial update
        Reisdocumentsoort partialUpdatedReisdocumentsoort = new Reisdocumentsoort();
        partialUpdatedReisdocumentsoort.setId(reisdocumentsoort.getId());

        partialUpdatedReisdocumentsoort
            .datumbegingeldigheidreisdocumentsoort(UPDATED_DATUMBEGINGELDIGHEIDREISDOCUMENTSOORT)
            .datumeindegeldigheidreisdocumentsoort(UPDATED_DATUMEINDEGELDIGHEIDREISDOCUMENTSOORT)
            .reisdocumentcode(UPDATED_REISDOCUMENTCODE)
            .reisdocumentomschrijving(UPDATED_REISDOCUMENTOMSCHRIJVING);

        restReisdocumentsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReisdocumentsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReisdocumentsoort))
            )
            .andExpect(status().isOk());

        // Validate the Reisdocumentsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReisdocumentsoortUpdatableFieldsEquals(
            partialUpdatedReisdocumentsoort,
            getPersistedReisdocumentsoort(partialUpdatedReisdocumentsoort)
        );
    }

    @Test
    @Transactional
    void patchNonExistingReisdocumentsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reisdocumentsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReisdocumentsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reisdocumentsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(reisdocumentsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reisdocumentsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReisdocumentsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reisdocumentsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReisdocumentsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(reisdocumentsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reisdocumentsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReisdocumentsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reisdocumentsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReisdocumentsoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(reisdocumentsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reisdocumentsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReisdocumentsoort() throws Exception {
        // Initialize the database
        reisdocumentsoortRepository.saveAndFlush(reisdocumentsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the reisdocumentsoort
        restReisdocumentsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, reisdocumentsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return reisdocumentsoortRepository.count();
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

    protected Reisdocumentsoort getPersistedReisdocumentsoort(Reisdocumentsoort reisdocumentsoort) {
        return reisdocumentsoortRepository.findById(reisdocumentsoort.getId()).orElseThrow();
    }

    protected void assertPersistedReisdocumentsoortToMatchAllProperties(Reisdocumentsoort expectedReisdocumentsoort) {
        assertReisdocumentsoortAllPropertiesEquals(expectedReisdocumentsoort, getPersistedReisdocumentsoort(expectedReisdocumentsoort));
    }

    protected void assertPersistedReisdocumentsoortToMatchUpdatableProperties(Reisdocumentsoort expectedReisdocumentsoort) {
        assertReisdocumentsoortAllUpdatablePropertiesEquals(
            expectedReisdocumentsoort,
            getPersistedReisdocumentsoort(expectedReisdocumentsoort)
        );
    }
}
