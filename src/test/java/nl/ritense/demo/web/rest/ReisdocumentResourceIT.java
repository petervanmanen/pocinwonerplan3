package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ReisdocumentAsserts.*;
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
import nl.ritense.demo.domain.Reisdocument;
import nl.ritense.demo.repository.ReisdocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ReisdocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReisdocumentResourceIT {

    private static final String DEFAULT_AANDUIDINGINHOUDINGVERMISSING = "AAAAAAAAAA";
    private static final String UPDATED_AANDUIDINGINHOUDINGVERMISSING = "BBBBBBBBBB";

    private static final String DEFAULT_AUTORITEITVANAFGIFTE = "AAAAAAAAAA";
    private static final String UPDATED_AUTORITEITVANAFGIFTE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDEGELDIGHEIDDOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDEGELDIGHEIDDOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMINGANGDOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_DATUMINGANGDOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMINHOUDINGOFVERMISSING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMINHOUDINGOFVERMISSING = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMUITGIFTE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMUITGIFTE = "BBBBBBBBBB";

    private static final String DEFAULT_REISDOCUMENTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_REISDOCUMENTNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_SOORT = "AAAAAAAAAA";
    private static final String UPDATED_SOORT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/reisdocuments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ReisdocumentRepository reisdocumentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReisdocumentMockMvc;

    private Reisdocument reisdocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reisdocument createEntity(EntityManager em) {
        Reisdocument reisdocument = new Reisdocument()
            .aanduidinginhoudingvermissing(DEFAULT_AANDUIDINGINHOUDINGVERMISSING)
            .autoriteitvanafgifte(DEFAULT_AUTORITEITVANAFGIFTE)
            .datumeindegeldigheiddocument(DEFAULT_DATUMEINDEGELDIGHEIDDOCUMENT)
            .datumingangdocument(DEFAULT_DATUMINGANGDOCUMENT)
            .datuminhoudingofvermissing(DEFAULT_DATUMINHOUDINGOFVERMISSING)
            .datumuitgifte(DEFAULT_DATUMUITGIFTE)
            .reisdocumentnummer(DEFAULT_REISDOCUMENTNUMMER)
            .soort(DEFAULT_SOORT);
        return reisdocument;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reisdocument createUpdatedEntity(EntityManager em) {
        Reisdocument reisdocument = new Reisdocument()
            .aanduidinginhoudingvermissing(UPDATED_AANDUIDINGINHOUDINGVERMISSING)
            .autoriteitvanafgifte(UPDATED_AUTORITEITVANAFGIFTE)
            .datumeindegeldigheiddocument(UPDATED_DATUMEINDEGELDIGHEIDDOCUMENT)
            .datumingangdocument(UPDATED_DATUMINGANGDOCUMENT)
            .datuminhoudingofvermissing(UPDATED_DATUMINHOUDINGOFVERMISSING)
            .datumuitgifte(UPDATED_DATUMUITGIFTE)
            .reisdocumentnummer(UPDATED_REISDOCUMENTNUMMER)
            .soort(UPDATED_SOORT);
        return reisdocument;
    }

    @BeforeEach
    public void initTest() {
        reisdocument = createEntity(em);
    }

    @Test
    @Transactional
    void createReisdocument() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Reisdocument
        var returnedReisdocument = om.readValue(
            restReisdocumentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reisdocument)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Reisdocument.class
        );

        // Validate the Reisdocument in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertReisdocumentUpdatableFieldsEquals(returnedReisdocument, getPersistedReisdocument(returnedReisdocument));
    }

    @Test
    @Transactional
    void createReisdocumentWithExistingId() throws Exception {
        // Create the Reisdocument with an existing ID
        reisdocument.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReisdocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reisdocument)))
            .andExpect(status().isBadRequest());

        // Validate the Reisdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReisdocuments() throws Exception {
        // Initialize the database
        reisdocumentRepository.saveAndFlush(reisdocument);

        // Get all the reisdocumentList
        restReisdocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reisdocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanduidinginhoudingvermissing").value(hasItem(DEFAULT_AANDUIDINGINHOUDINGVERMISSING)))
            .andExpect(jsonPath("$.[*].autoriteitvanafgifte").value(hasItem(DEFAULT_AUTORITEITVANAFGIFTE)))
            .andExpect(jsonPath("$.[*].datumeindegeldigheiddocument").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDDOCUMENT)))
            .andExpect(jsonPath("$.[*].datumingangdocument").value(hasItem(DEFAULT_DATUMINGANGDOCUMENT)))
            .andExpect(jsonPath("$.[*].datuminhoudingofvermissing").value(hasItem(DEFAULT_DATUMINHOUDINGOFVERMISSING)))
            .andExpect(jsonPath("$.[*].datumuitgifte").value(hasItem(DEFAULT_DATUMUITGIFTE)))
            .andExpect(jsonPath("$.[*].reisdocumentnummer").value(hasItem(DEFAULT_REISDOCUMENTNUMMER)))
            .andExpect(jsonPath("$.[*].soort").value(hasItem(DEFAULT_SOORT)));
    }

    @Test
    @Transactional
    void getReisdocument() throws Exception {
        // Initialize the database
        reisdocumentRepository.saveAndFlush(reisdocument);

        // Get the reisdocument
        restReisdocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, reisdocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reisdocument.getId().intValue()))
            .andExpect(jsonPath("$.aanduidinginhoudingvermissing").value(DEFAULT_AANDUIDINGINHOUDINGVERMISSING))
            .andExpect(jsonPath("$.autoriteitvanafgifte").value(DEFAULT_AUTORITEITVANAFGIFTE))
            .andExpect(jsonPath("$.datumeindegeldigheiddocument").value(DEFAULT_DATUMEINDEGELDIGHEIDDOCUMENT))
            .andExpect(jsonPath("$.datumingangdocument").value(DEFAULT_DATUMINGANGDOCUMENT))
            .andExpect(jsonPath("$.datuminhoudingofvermissing").value(DEFAULT_DATUMINHOUDINGOFVERMISSING))
            .andExpect(jsonPath("$.datumuitgifte").value(DEFAULT_DATUMUITGIFTE))
            .andExpect(jsonPath("$.reisdocumentnummer").value(DEFAULT_REISDOCUMENTNUMMER))
            .andExpect(jsonPath("$.soort").value(DEFAULT_SOORT));
    }

    @Test
    @Transactional
    void getNonExistingReisdocument() throws Exception {
        // Get the reisdocument
        restReisdocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReisdocument() throws Exception {
        // Initialize the database
        reisdocumentRepository.saveAndFlush(reisdocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reisdocument
        Reisdocument updatedReisdocument = reisdocumentRepository.findById(reisdocument.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedReisdocument are not directly saved in db
        em.detach(updatedReisdocument);
        updatedReisdocument
            .aanduidinginhoudingvermissing(UPDATED_AANDUIDINGINHOUDINGVERMISSING)
            .autoriteitvanafgifte(UPDATED_AUTORITEITVANAFGIFTE)
            .datumeindegeldigheiddocument(UPDATED_DATUMEINDEGELDIGHEIDDOCUMENT)
            .datumingangdocument(UPDATED_DATUMINGANGDOCUMENT)
            .datuminhoudingofvermissing(UPDATED_DATUMINHOUDINGOFVERMISSING)
            .datumuitgifte(UPDATED_DATUMUITGIFTE)
            .reisdocumentnummer(UPDATED_REISDOCUMENTNUMMER)
            .soort(UPDATED_SOORT);

        restReisdocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReisdocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedReisdocument))
            )
            .andExpect(status().isOk());

        // Validate the Reisdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedReisdocumentToMatchAllProperties(updatedReisdocument);
    }

    @Test
    @Transactional
    void putNonExistingReisdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reisdocument.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReisdocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reisdocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reisdocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reisdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReisdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reisdocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReisdocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reisdocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reisdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReisdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reisdocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReisdocumentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reisdocument)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reisdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReisdocumentWithPatch() throws Exception {
        // Initialize the database
        reisdocumentRepository.saveAndFlush(reisdocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reisdocument using partial update
        Reisdocument partialUpdatedReisdocument = new Reisdocument();
        partialUpdatedReisdocument.setId(reisdocument.getId());

        partialUpdatedReisdocument
            .aanduidinginhoudingvermissing(UPDATED_AANDUIDINGINHOUDINGVERMISSING)
            .autoriteitvanafgifte(UPDATED_AUTORITEITVANAFGIFTE)
            .datumeindegeldigheiddocument(UPDATED_DATUMEINDEGELDIGHEIDDOCUMENT)
            .datumuitgifte(UPDATED_DATUMUITGIFTE)
            .soort(UPDATED_SOORT);

        restReisdocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReisdocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReisdocument))
            )
            .andExpect(status().isOk());

        // Validate the Reisdocument in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReisdocumentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedReisdocument, reisdocument),
            getPersistedReisdocument(reisdocument)
        );
    }

    @Test
    @Transactional
    void fullUpdateReisdocumentWithPatch() throws Exception {
        // Initialize the database
        reisdocumentRepository.saveAndFlush(reisdocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reisdocument using partial update
        Reisdocument partialUpdatedReisdocument = new Reisdocument();
        partialUpdatedReisdocument.setId(reisdocument.getId());

        partialUpdatedReisdocument
            .aanduidinginhoudingvermissing(UPDATED_AANDUIDINGINHOUDINGVERMISSING)
            .autoriteitvanafgifte(UPDATED_AUTORITEITVANAFGIFTE)
            .datumeindegeldigheiddocument(UPDATED_DATUMEINDEGELDIGHEIDDOCUMENT)
            .datumingangdocument(UPDATED_DATUMINGANGDOCUMENT)
            .datuminhoudingofvermissing(UPDATED_DATUMINHOUDINGOFVERMISSING)
            .datumuitgifte(UPDATED_DATUMUITGIFTE)
            .reisdocumentnummer(UPDATED_REISDOCUMENTNUMMER)
            .soort(UPDATED_SOORT);

        restReisdocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReisdocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReisdocument))
            )
            .andExpect(status().isOk());

        // Validate the Reisdocument in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReisdocumentUpdatableFieldsEquals(partialUpdatedReisdocument, getPersistedReisdocument(partialUpdatedReisdocument));
    }

    @Test
    @Transactional
    void patchNonExistingReisdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reisdocument.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReisdocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reisdocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(reisdocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reisdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReisdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reisdocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReisdocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(reisdocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reisdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReisdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reisdocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReisdocumentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(reisdocument)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reisdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReisdocument() throws Exception {
        // Initialize the database
        reisdocumentRepository.saveAndFlush(reisdocument);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the reisdocument
        restReisdocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, reisdocument.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return reisdocumentRepository.count();
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

    protected Reisdocument getPersistedReisdocument(Reisdocument reisdocument) {
        return reisdocumentRepository.findById(reisdocument.getId()).orElseThrow();
    }

    protected void assertPersistedReisdocumentToMatchAllProperties(Reisdocument expectedReisdocument) {
        assertReisdocumentAllPropertiesEquals(expectedReisdocument, getPersistedReisdocument(expectedReisdocument));
    }

    protected void assertPersistedReisdocumentToMatchUpdatableProperties(Reisdocument expectedReisdocument) {
        assertReisdocumentAllUpdatablePropertiesEquals(expectedReisdocument, getPersistedReisdocument(expectedReisdocument));
    }
}
