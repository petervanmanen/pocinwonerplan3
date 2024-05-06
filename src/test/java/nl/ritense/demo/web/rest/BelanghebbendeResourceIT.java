package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BelanghebbendeAsserts.*;
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
import nl.ritense.demo.domain.Belanghebbende;
import nl.ritense.demo.repository.BelanghebbendeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BelanghebbendeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BelanghebbendeResourceIT {

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMTOT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMTOT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/belanghebbendes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BelanghebbendeRepository belanghebbendeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBelanghebbendeMockMvc;

    private Belanghebbende belanghebbende;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Belanghebbende createEntity(EntityManager em) {
        Belanghebbende belanghebbende = new Belanghebbende().datumstart(DEFAULT_DATUMSTART).datumtot(DEFAULT_DATUMTOT);
        return belanghebbende;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Belanghebbende createUpdatedEntity(EntityManager em) {
        Belanghebbende belanghebbende = new Belanghebbende().datumstart(UPDATED_DATUMSTART).datumtot(UPDATED_DATUMTOT);
        return belanghebbende;
    }

    @BeforeEach
    public void initTest() {
        belanghebbende = createEntity(em);
    }

    @Test
    @Transactional
    void createBelanghebbende() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Belanghebbende
        var returnedBelanghebbende = om.readValue(
            restBelanghebbendeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(belanghebbende)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Belanghebbende.class
        );

        // Validate the Belanghebbende in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBelanghebbendeUpdatableFieldsEquals(returnedBelanghebbende, getPersistedBelanghebbende(returnedBelanghebbende));
    }

    @Test
    @Transactional
    void createBelanghebbendeWithExistingId() throws Exception {
        // Create the Belanghebbende with an existing ID
        belanghebbende.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBelanghebbendeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(belanghebbende)))
            .andExpect(status().isBadRequest());

        // Validate the Belanghebbende in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBelanghebbendes() throws Exception {
        // Initialize the database
        belanghebbendeRepository.saveAndFlush(belanghebbende);

        // Get all the belanghebbendeList
        restBelanghebbendeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(belanghebbende.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].datumtot").value(hasItem(DEFAULT_DATUMTOT.toString())));
    }

    @Test
    @Transactional
    void getBelanghebbende() throws Exception {
        // Initialize the database
        belanghebbendeRepository.saveAndFlush(belanghebbende);

        // Get the belanghebbende
        restBelanghebbendeMockMvc
            .perform(get(ENTITY_API_URL_ID, belanghebbende.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(belanghebbende.getId().intValue()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.datumtot").value(DEFAULT_DATUMTOT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBelanghebbende() throws Exception {
        // Get the belanghebbende
        restBelanghebbendeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBelanghebbende() throws Exception {
        // Initialize the database
        belanghebbendeRepository.saveAndFlush(belanghebbende);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the belanghebbende
        Belanghebbende updatedBelanghebbende = belanghebbendeRepository.findById(belanghebbende.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBelanghebbende are not directly saved in db
        em.detach(updatedBelanghebbende);
        updatedBelanghebbende.datumstart(UPDATED_DATUMSTART).datumtot(UPDATED_DATUMTOT);

        restBelanghebbendeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBelanghebbende.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBelanghebbende))
            )
            .andExpect(status().isOk());

        // Validate the Belanghebbende in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBelanghebbendeToMatchAllProperties(updatedBelanghebbende);
    }

    @Test
    @Transactional
    void putNonExistingBelanghebbende() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belanghebbende.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBelanghebbendeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, belanghebbende.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(belanghebbende))
            )
            .andExpect(status().isBadRequest());

        // Validate the Belanghebbende in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBelanghebbende() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belanghebbende.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBelanghebbendeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(belanghebbende))
            )
            .andExpect(status().isBadRequest());

        // Validate the Belanghebbende in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBelanghebbende() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belanghebbende.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBelanghebbendeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(belanghebbende)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Belanghebbende in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBelanghebbendeWithPatch() throws Exception {
        // Initialize the database
        belanghebbendeRepository.saveAndFlush(belanghebbende);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the belanghebbende using partial update
        Belanghebbende partialUpdatedBelanghebbende = new Belanghebbende();
        partialUpdatedBelanghebbende.setId(belanghebbende.getId());

        partialUpdatedBelanghebbende.datumtot(UPDATED_DATUMTOT);

        restBelanghebbendeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBelanghebbende.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBelanghebbende))
            )
            .andExpect(status().isOk());

        // Validate the Belanghebbende in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBelanghebbendeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBelanghebbende, belanghebbende),
            getPersistedBelanghebbende(belanghebbende)
        );
    }

    @Test
    @Transactional
    void fullUpdateBelanghebbendeWithPatch() throws Exception {
        // Initialize the database
        belanghebbendeRepository.saveAndFlush(belanghebbende);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the belanghebbende using partial update
        Belanghebbende partialUpdatedBelanghebbende = new Belanghebbende();
        partialUpdatedBelanghebbende.setId(belanghebbende.getId());

        partialUpdatedBelanghebbende.datumstart(UPDATED_DATUMSTART).datumtot(UPDATED_DATUMTOT);

        restBelanghebbendeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBelanghebbende.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBelanghebbende))
            )
            .andExpect(status().isOk());

        // Validate the Belanghebbende in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBelanghebbendeUpdatableFieldsEquals(partialUpdatedBelanghebbende, getPersistedBelanghebbende(partialUpdatedBelanghebbende));
    }

    @Test
    @Transactional
    void patchNonExistingBelanghebbende() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belanghebbende.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBelanghebbendeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, belanghebbende.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(belanghebbende))
            )
            .andExpect(status().isBadRequest());

        // Validate the Belanghebbende in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBelanghebbende() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belanghebbende.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBelanghebbendeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(belanghebbende))
            )
            .andExpect(status().isBadRequest());

        // Validate the Belanghebbende in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBelanghebbende() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belanghebbende.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBelanghebbendeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(belanghebbende)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Belanghebbende in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBelanghebbende() throws Exception {
        // Initialize the database
        belanghebbendeRepository.saveAndFlush(belanghebbende);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the belanghebbende
        restBelanghebbendeMockMvc
            .perform(delete(ENTITY_API_URL_ID, belanghebbende.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return belanghebbendeRepository.count();
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

    protected Belanghebbende getPersistedBelanghebbende(Belanghebbende belanghebbende) {
        return belanghebbendeRepository.findById(belanghebbende.getId()).orElseThrow();
    }

    protected void assertPersistedBelanghebbendeToMatchAllProperties(Belanghebbende expectedBelanghebbende) {
        assertBelanghebbendeAllPropertiesEquals(expectedBelanghebbende, getPersistedBelanghebbende(expectedBelanghebbende));
    }

    protected void assertPersistedBelanghebbendeToMatchUpdatableProperties(Belanghebbende expectedBelanghebbende) {
        assertBelanghebbendeAllUpdatablePropertiesEquals(expectedBelanghebbende, getPersistedBelanghebbende(expectedBelanghebbende));
    }
}
