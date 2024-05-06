package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.InkomensvoorzieningsoortAsserts.*;
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
import nl.ritense.demo.domain.Inkomensvoorzieningsoort;
import nl.ritense.demo.repository.InkomensvoorzieningsoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InkomensvoorzieningsoortResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class InkomensvoorzieningsoortResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_REGELING = "AAAAAAAAAA";
    private static final String UPDATED_REGELING = "BBBBBBBBBB";

    private static final String DEFAULT_REGELINGCODE = "AAAAAAAAAA";
    private static final String UPDATED_REGELINGCODE = "BBBBBBBBBB";

    private static final String DEFAULT_VERGOEDING = "AAAAAAAAAA";
    private static final String UPDATED_VERGOEDING = "BBBBBBBBBB";

    private static final String DEFAULT_VERGOEDINGSCODE = "AAAAAAAAAA";
    private static final String UPDATED_VERGOEDINGSCODE = "BBBBBBBBBB";

    private static final String DEFAULT_WET = "AAAAAAAAAA";
    private static final String UPDATED_WET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inkomensvoorzieningsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InkomensvoorzieningsoortRepository inkomensvoorzieningsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInkomensvoorzieningsoortMockMvc;

    private Inkomensvoorzieningsoort inkomensvoorzieningsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inkomensvoorzieningsoort createEntity(EntityManager em) {
        Inkomensvoorzieningsoort inkomensvoorzieningsoort = new Inkomensvoorzieningsoort()
            .code(DEFAULT_CODE)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .regeling(DEFAULT_REGELING)
            .regelingcode(DEFAULT_REGELINGCODE)
            .vergoeding(DEFAULT_VERGOEDING)
            .vergoedingscode(DEFAULT_VERGOEDINGSCODE)
            .wet(DEFAULT_WET);
        return inkomensvoorzieningsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inkomensvoorzieningsoort createUpdatedEntity(EntityManager em) {
        Inkomensvoorzieningsoort inkomensvoorzieningsoort = new Inkomensvoorzieningsoort()
            .code(UPDATED_CODE)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .regeling(UPDATED_REGELING)
            .regelingcode(UPDATED_REGELINGCODE)
            .vergoeding(UPDATED_VERGOEDING)
            .vergoedingscode(UPDATED_VERGOEDINGSCODE)
            .wet(UPDATED_WET);
        return inkomensvoorzieningsoort;
    }

    @BeforeEach
    public void initTest() {
        inkomensvoorzieningsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createInkomensvoorzieningsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Inkomensvoorzieningsoort
        var returnedInkomensvoorzieningsoort = om.readValue(
            restInkomensvoorzieningsoortMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inkomensvoorzieningsoort))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Inkomensvoorzieningsoort.class
        );

        // Validate the Inkomensvoorzieningsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInkomensvoorzieningsoortUpdatableFieldsEquals(
            returnedInkomensvoorzieningsoort,
            getPersistedInkomensvoorzieningsoort(returnedInkomensvoorzieningsoort)
        );
    }

    @Test
    @Transactional
    void createInkomensvoorzieningsoortWithExistingId() throws Exception {
        // Create the Inkomensvoorzieningsoort with an existing ID
        inkomensvoorzieningsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInkomensvoorzieningsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inkomensvoorzieningsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Inkomensvoorzieningsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInkomensvoorzieningsoorts() throws Exception {
        // Initialize the database
        inkomensvoorzieningsoortRepository.saveAndFlush(inkomensvoorzieningsoort);

        // Get all the inkomensvoorzieningsoortList
        restInkomensvoorzieningsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inkomensvoorzieningsoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].regeling").value(hasItem(DEFAULT_REGELING)))
            .andExpect(jsonPath("$.[*].regelingcode").value(hasItem(DEFAULT_REGELINGCODE)))
            .andExpect(jsonPath("$.[*].vergoeding").value(hasItem(DEFAULT_VERGOEDING)))
            .andExpect(jsonPath("$.[*].vergoedingscode").value(hasItem(DEFAULT_VERGOEDINGSCODE)))
            .andExpect(jsonPath("$.[*].wet").value(hasItem(DEFAULT_WET)));
    }

    @Test
    @Transactional
    void getInkomensvoorzieningsoort() throws Exception {
        // Initialize the database
        inkomensvoorzieningsoortRepository.saveAndFlush(inkomensvoorzieningsoort);

        // Get the inkomensvoorzieningsoort
        restInkomensvoorzieningsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, inkomensvoorzieningsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inkomensvoorzieningsoort.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.regeling").value(DEFAULT_REGELING))
            .andExpect(jsonPath("$.regelingcode").value(DEFAULT_REGELINGCODE))
            .andExpect(jsonPath("$.vergoeding").value(DEFAULT_VERGOEDING))
            .andExpect(jsonPath("$.vergoedingscode").value(DEFAULT_VERGOEDINGSCODE))
            .andExpect(jsonPath("$.wet").value(DEFAULT_WET));
    }

    @Test
    @Transactional
    void getNonExistingInkomensvoorzieningsoort() throws Exception {
        // Get the inkomensvoorzieningsoort
        restInkomensvoorzieningsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInkomensvoorzieningsoort() throws Exception {
        // Initialize the database
        inkomensvoorzieningsoortRepository.saveAndFlush(inkomensvoorzieningsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inkomensvoorzieningsoort
        Inkomensvoorzieningsoort updatedInkomensvoorzieningsoort = inkomensvoorzieningsoortRepository
            .findById(inkomensvoorzieningsoort.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedInkomensvoorzieningsoort are not directly saved in db
        em.detach(updatedInkomensvoorzieningsoort);
        updatedInkomensvoorzieningsoort
            .code(UPDATED_CODE)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .regeling(UPDATED_REGELING)
            .regelingcode(UPDATED_REGELINGCODE)
            .vergoeding(UPDATED_VERGOEDING)
            .vergoedingscode(UPDATED_VERGOEDINGSCODE)
            .wet(UPDATED_WET);

        restInkomensvoorzieningsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInkomensvoorzieningsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInkomensvoorzieningsoort))
            )
            .andExpect(status().isOk());

        // Validate the Inkomensvoorzieningsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInkomensvoorzieningsoortToMatchAllProperties(updatedInkomensvoorzieningsoort);
    }

    @Test
    @Transactional
    void putNonExistingInkomensvoorzieningsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkomensvoorzieningsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInkomensvoorzieningsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inkomensvoorzieningsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inkomensvoorzieningsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inkomensvoorzieningsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInkomensvoorzieningsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkomensvoorzieningsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInkomensvoorzieningsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inkomensvoorzieningsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inkomensvoorzieningsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInkomensvoorzieningsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkomensvoorzieningsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInkomensvoorzieningsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inkomensvoorzieningsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inkomensvoorzieningsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInkomensvoorzieningsoortWithPatch() throws Exception {
        // Initialize the database
        inkomensvoorzieningsoortRepository.saveAndFlush(inkomensvoorzieningsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inkomensvoorzieningsoort using partial update
        Inkomensvoorzieningsoort partialUpdatedInkomensvoorzieningsoort = new Inkomensvoorzieningsoort();
        partialUpdatedInkomensvoorzieningsoort.setId(inkomensvoorzieningsoort.getId());

        partialUpdatedInkomensvoorzieningsoort
            .code(UPDATED_CODE)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .regelingcode(UPDATED_REGELINGCODE)
            .wet(UPDATED_WET);

        restInkomensvoorzieningsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInkomensvoorzieningsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInkomensvoorzieningsoort))
            )
            .andExpect(status().isOk());

        // Validate the Inkomensvoorzieningsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInkomensvoorzieningsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInkomensvoorzieningsoort, inkomensvoorzieningsoort),
            getPersistedInkomensvoorzieningsoort(inkomensvoorzieningsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateInkomensvoorzieningsoortWithPatch() throws Exception {
        // Initialize the database
        inkomensvoorzieningsoortRepository.saveAndFlush(inkomensvoorzieningsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inkomensvoorzieningsoort using partial update
        Inkomensvoorzieningsoort partialUpdatedInkomensvoorzieningsoort = new Inkomensvoorzieningsoort();
        partialUpdatedInkomensvoorzieningsoort.setId(inkomensvoorzieningsoort.getId());

        partialUpdatedInkomensvoorzieningsoort
            .code(UPDATED_CODE)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .regeling(UPDATED_REGELING)
            .regelingcode(UPDATED_REGELINGCODE)
            .vergoeding(UPDATED_VERGOEDING)
            .vergoedingscode(UPDATED_VERGOEDINGSCODE)
            .wet(UPDATED_WET);

        restInkomensvoorzieningsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInkomensvoorzieningsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInkomensvoorzieningsoort))
            )
            .andExpect(status().isOk());

        // Validate the Inkomensvoorzieningsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInkomensvoorzieningsoortUpdatableFieldsEquals(
            partialUpdatedInkomensvoorzieningsoort,
            getPersistedInkomensvoorzieningsoort(partialUpdatedInkomensvoorzieningsoort)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInkomensvoorzieningsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkomensvoorzieningsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInkomensvoorzieningsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inkomensvoorzieningsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inkomensvoorzieningsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inkomensvoorzieningsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInkomensvoorzieningsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkomensvoorzieningsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInkomensvoorzieningsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inkomensvoorzieningsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inkomensvoorzieningsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInkomensvoorzieningsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkomensvoorzieningsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInkomensvoorzieningsoortMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inkomensvoorzieningsoort))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inkomensvoorzieningsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInkomensvoorzieningsoort() throws Exception {
        // Initialize the database
        inkomensvoorzieningsoortRepository.saveAndFlush(inkomensvoorzieningsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inkomensvoorzieningsoort
        restInkomensvoorzieningsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, inkomensvoorzieningsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inkomensvoorzieningsoortRepository.count();
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

    protected Inkomensvoorzieningsoort getPersistedInkomensvoorzieningsoort(Inkomensvoorzieningsoort inkomensvoorzieningsoort) {
        return inkomensvoorzieningsoortRepository.findById(inkomensvoorzieningsoort.getId()).orElseThrow();
    }

    protected void assertPersistedInkomensvoorzieningsoortToMatchAllProperties(Inkomensvoorzieningsoort expectedInkomensvoorzieningsoort) {
        assertInkomensvoorzieningsoortAllPropertiesEquals(
            expectedInkomensvoorzieningsoort,
            getPersistedInkomensvoorzieningsoort(expectedInkomensvoorzieningsoort)
        );
    }

    protected void assertPersistedInkomensvoorzieningsoortToMatchUpdatableProperties(
        Inkomensvoorzieningsoort expectedInkomensvoorzieningsoort
    ) {
        assertInkomensvoorzieningsoortAllUpdatablePropertiesEquals(
            expectedInkomensvoorzieningsoort,
            getPersistedInkomensvoorzieningsoort(expectedInkomensvoorzieningsoort)
        );
    }
}
