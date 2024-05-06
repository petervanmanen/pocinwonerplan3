package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EcomponentsoortAsserts.*;
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
import nl.ritense.demo.domain.Ecomponentsoort;
import nl.ritense.demo.repository.EcomponentsoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EcomponentsoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EcomponentsoortResourceIT {

    private static final String DEFAULT_ECOMPONENT = "AAAAAAAAAA";
    private static final String UPDATED_ECOMPONENT = "BBBBBBBBBB";

    private static final String DEFAULT_ECOMPONENTCODE = "AAAAAAAAAA";
    private static final String UPDATED_ECOMPONENTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_KOLOM = "AAAAAAAAAA";
    private static final String UPDATED_KOLOM = "BBBBBBBBBB";

    private static final String DEFAULT_KOLOMCODE = "AAAAAAAAAA";
    private static final String UPDATED_KOLOMCODE = "BBBBBBBBBB";

    private static final String DEFAULT_REGELING = "AAAAAAAAAA";
    private static final String UPDATED_REGELING = "BBBBBBBBBB";

    private static final String DEFAULT_REGELINGCODE = "AAAAAAAAAA";
    private static final String UPDATED_REGELINGCODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ecomponentsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EcomponentsoortRepository ecomponentsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEcomponentsoortMockMvc;

    private Ecomponentsoort ecomponentsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ecomponentsoort createEntity(EntityManager em) {
        Ecomponentsoort ecomponentsoort = new Ecomponentsoort()
            .ecomponent(DEFAULT_ECOMPONENT)
            .ecomponentcode(DEFAULT_ECOMPONENTCODE)
            .kolom(DEFAULT_KOLOM)
            .kolomcode(DEFAULT_KOLOMCODE)
            .regeling(DEFAULT_REGELING)
            .regelingcode(DEFAULT_REGELINGCODE);
        return ecomponentsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ecomponentsoort createUpdatedEntity(EntityManager em) {
        Ecomponentsoort ecomponentsoort = new Ecomponentsoort()
            .ecomponent(UPDATED_ECOMPONENT)
            .ecomponentcode(UPDATED_ECOMPONENTCODE)
            .kolom(UPDATED_KOLOM)
            .kolomcode(UPDATED_KOLOMCODE)
            .regeling(UPDATED_REGELING)
            .regelingcode(UPDATED_REGELINGCODE);
        return ecomponentsoort;
    }

    @BeforeEach
    public void initTest() {
        ecomponentsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createEcomponentsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ecomponentsoort
        var returnedEcomponentsoort = om.readValue(
            restEcomponentsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ecomponentsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Ecomponentsoort.class
        );

        // Validate the Ecomponentsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEcomponentsoortUpdatableFieldsEquals(returnedEcomponentsoort, getPersistedEcomponentsoort(returnedEcomponentsoort));
    }

    @Test
    @Transactional
    void createEcomponentsoortWithExistingId() throws Exception {
        // Create the Ecomponentsoort with an existing ID
        ecomponentsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEcomponentsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ecomponentsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Ecomponentsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEcomponentsoorts() throws Exception {
        // Initialize the database
        ecomponentsoortRepository.saveAndFlush(ecomponentsoort);

        // Get all the ecomponentsoortList
        restEcomponentsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ecomponentsoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].ecomponent").value(hasItem(DEFAULT_ECOMPONENT)))
            .andExpect(jsonPath("$.[*].ecomponentcode").value(hasItem(DEFAULT_ECOMPONENTCODE)))
            .andExpect(jsonPath("$.[*].kolom").value(hasItem(DEFAULT_KOLOM)))
            .andExpect(jsonPath("$.[*].kolomcode").value(hasItem(DEFAULT_KOLOMCODE)))
            .andExpect(jsonPath("$.[*].regeling").value(hasItem(DEFAULT_REGELING)))
            .andExpect(jsonPath("$.[*].regelingcode").value(hasItem(DEFAULT_REGELINGCODE)));
    }

    @Test
    @Transactional
    void getEcomponentsoort() throws Exception {
        // Initialize the database
        ecomponentsoortRepository.saveAndFlush(ecomponentsoort);

        // Get the ecomponentsoort
        restEcomponentsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, ecomponentsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ecomponentsoort.getId().intValue()))
            .andExpect(jsonPath("$.ecomponent").value(DEFAULT_ECOMPONENT))
            .andExpect(jsonPath("$.ecomponentcode").value(DEFAULT_ECOMPONENTCODE))
            .andExpect(jsonPath("$.kolom").value(DEFAULT_KOLOM))
            .andExpect(jsonPath("$.kolomcode").value(DEFAULT_KOLOMCODE))
            .andExpect(jsonPath("$.regeling").value(DEFAULT_REGELING))
            .andExpect(jsonPath("$.regelingcode").value(DEFAULT_REGELINGCODE));
    }

    @Test
    @Transactional
    void getNonExistingEcomponentsoort() throws Exception {
        // Get the ecomponentsoort
        restEcomponentsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEcomponentsoort() throws Exception {
        // Initialize the database
        ecomponentsoortRepository.saveAndFlush(ecomponentsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ecomponentsoort
        Ecomponentsoort updatedEcomponentsoort = ecomponentsoortRepository.findById(ecomponentsoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEcomponentsoort are not directly saved in db
        em.detach(updatedEcomponentsoort);
        updatedEcomponentsoort
            .ecomponent(UPDATED_ECOMPONENT)
            .ecomponentcode(UPDATED_ECOMPONENTCODE)
            .kolom(UPDATED_KOLOM)
            .kolomcode(UPDATED_KOLOMCODE)
            .regeling(UPDATED_REGELING)
            .regelingcode(UPDATED_REGELINGCODE);

        restEcomponentsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEcomponentsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEcomponentsoort))
            )
            .andExpect(status().isOk());

        // Validate the Ecomponentsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEcomponentsoortToMatchAllProperties(updatedEcomponentsoort);
    }

    @Test
    @Transactional
    void putNonExistingEcomponentsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecomponentsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcomponentsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ecomponentsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ecomponentsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecomponentsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEcomponentsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecomponentsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcomponentsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ecomponentsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecomponentsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEcomponentsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecomponentsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcomponentsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ecomponentsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ecomponentsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEcomponentsoortWithPatch() throws Exception {
        // Initialize the database
        ecomponentsoortRepository.saveAndFlush(ecomponentsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ecomponentsoort using partial update
        Ecomponentsoort partialUpdatedEcomponentsoort = new Ecomponentsoort();
        partialUpdatedEcomponentsoort.setId(ecomponentsoort.getId());

        partialUpdatedEcomponentsoort.ecomponent(UPDATED_ECOMPONENT).kolom(UPDATED_KOLOM).regelingcode(UPDATED_REGELINGCODE);

        restEcomponentsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcomponentsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEcomponentsoort))
            )
            .andExpect(status().isOk());

        // Validate the Ecomponentsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEcomponentsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEcomponentsoort, ecomponentsoort),
            getPersistedEcomponentsoort(ecomponentsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateEcomponentsoortWithPatch() throws Exception {
        // Initialize the database
        ecomponentsoortRepository.saveAndFlush(ecomponentsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ecomponentsoort using partial update
        Ecomponentsoort partialUpdatedEcomponentsoort = new Ecomponentsoort();
        partialUpdatedEcomponentsoort.setId(ecomponentsoort.getId());

        partialUpdatedEcomponentsoort
            .ecomponent(UPDATED_ECOMPONENT)
            .ecomponentcode(UPDATED_ECOMPONENTCODE)
            .kolom(UPDATED_KOLOM)
            .kolomcode(UPDATED_KOLOMCODE)
            .regeling(UPDATED_REGELING)
            .regelingcode(UPDATED_REGELINGCODE);

        restEcomponentsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcomponentsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEcomponentsoort))
            )
            .andExpect(status().isOk());

        // Validate the Ecomponentsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEcomponentsoortUpdatableFieldsEquals(
            partialUpdatedEcomponentsoort,
            getPersistedEcomponentsoort(partialUpdatedEcomponentsoort)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEcomponentsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecomponentsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcomponentsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ecomponentsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ecomponentsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecomponentsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEcomponentsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecomponentsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcomponentsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ecomponentsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecomponentsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEcomponentsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecomponentsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcomponentsoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ecomponentsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ecomponentsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEcomponentsoort() throws Exception {
        // Initialize the database
        ecomponentsoortRepository.saveAndFlush(ecomponentsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ecomponentsoort
        restEcomponentsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, ecomponentsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ecomponentsoortRepository.count();
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

    protected Ecomponentsoort getPersistedEcomponentsoort(Ecomponentsoort ecomponentsoort) {
        return ecomponentsoortRepository.findById(ecomponentsoort.getId()).orElseThrow();
    }

    protected void assertPersistedEcomponentsoortToMatchAllProperties(Ecomponentsoort expectedEcomponentsoort) {
        assertEcomponentsoortAllPropertiesEquals(expectedEcomponentsoort, getPersistedEcomponentsoort(expectedEcomponentsoort));
    }

    protected void assertPersistedEcomponentsoortToMatchUpdatableProperties(Ecomponentsoort expectedEcomponentsoort) {
        assertEcomponentsoortAllUpdatablePropertiesEquals(expectedEcomponentsoort, getPersistedEcomponentsoort(expectedEcomponentsoort));
    }
}
