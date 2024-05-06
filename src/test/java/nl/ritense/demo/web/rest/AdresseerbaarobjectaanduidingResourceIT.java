package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AdresseerbaarobjectaanduidingAsserts.*;
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
import nl.ritense.demo.domain.Adresseerbaarobjectaanduiding;
import nl.ritense.demo.repository.AdresseerbaarobjectaanduidingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AdresseerbaarobjectaanduidingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdresseerbaarobjectaanduidingResourceIT {

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/adresseerbaarobjectaanduidings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AdresseerbaarobjectaanduidingRepository adresseerbaarobjectaanduidingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdresseerbaarobjectaanduidingMockMvc;

    private Adresseerbaarobjectaanduiding adresseerbaarobjectaanduiding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresseerbaarobjectaanduiding createEntity(EntityManager em) {
        Adresseerbaarobjectaanduiding adresseerbaarobjectaanduiding = new Adresseerbaarobjectaanduiding()
            .identificatie(DEFAULT_IDENTIFICATIE);
        return adresseerbaarobjectaanduiding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresseerbaarobjectaanduiding createUpdatedEntity(EntityManager em) {
        Adresseerbaarobjectaanduiding adresseerbaarobjectaanduiding = new Adresseerbaarobjectaanduiding()
            .identificatie(UPDATED_IDENTIFICATIE);
        return adresseerbaarobjectaanduiding;
    }

    @BeforeEach
    public void initTest() {
        adresseerbaarobjectaanduiding = createEntity(em);
    }

    @Test
    @Transactional
    void createAdresseerbaarobjectaanduiding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Adresseerbaarobjectaanduiding
        var returnedAdresseerbaarobjectaanduiding = om.readValue(
            restAdresseerbaarobjectaanduidingMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(adresseerbaarobjectaanduiding))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Adresseerbaarobjectaanduiding.class
        );

        // Validate the Adresseerbaarobjectaanduiding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAdresseerbaarobjectaanduidingUpdatableFieldsEquals(
            returnedAdresseerbaarobjectaanduiding,
            getPersistedAdresseerbaarobjectaanduiding(returnedAdresseerbaarobjectaanduiding)
        );
    }

    @Test
    @Transactional
    void createAdresseerbaarobjectaanduidingWithExistingId() throws Exception {
        // Create the Adresseerbaarobjectaanduiding with an existing ID
        adresseerbaarobjectaanduiding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdresseerbaarobjectaanduidingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseerbaarobjectaanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresseerbaarobjectaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdresseerbaarobjectaanduidings() throws Exception {
        // Initialize the database
        adresseerbaarobjectaanduidingRepository.saveAndFlush(adresseerbaarobjectaanduiding);

        // Get all the adresseerbaarobjectaanduidingList
        restAdresseerbaarobjectaanduidingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adresseerbaarobjectaanduiding.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)));
    }

    @Test
    @Transactional
    void getAdresseerbaarobjectaanduiding() throws Exception {
        // Initialize the database
        adresseerbaarobjectaanduidingRepository.saveAndFlush(adresseerbaarobjectaanduiding);

        // Get the adresseerbaarobjectaanduiding
        restAdresseerbaarobjectaanduidingMockMvc
            .perform(get(ENTITY_API_URL_ID, adresseerbaarobjectaanduiding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adresseerbaarobjectaanduiding.getId().intValue()))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE));
    }

    @Test
    @Transactional
    void getNonExistingAdresseerbaarobjectaanduiding() throws Exception {
        // Get the adresseerbaarobjectaanduiding
        restAdresseerbaarobjectaanduidingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdresseerbaarobjectaanduiding() throws Exception {
        // Initialize the database
        adresseerbaarobjectaanduidingRepository.saveAndFlush(adresseerbaarobjectaanduiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adresseerbaarobjectaanduiding
        Adresseerbaarobjectaanduiding updatedAdresseerbaarobjectaanduiding = adresseerbaarobjectaanduidingRepository
            .findById(adresseerbaarobjectaanduiding.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAdresseerbaarobjectaanduiding are not directly saved in db
        em.detach(updatedAdresseerbaarobjectaanduiding);
        updatedAdresseerbaarobjectaanduiding.identificatie(UPDATED_IDENTIFICATIE);

        restAdresseerbaarobjectaanduidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdresseerbaarobjectaanduiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAdresseerbaarobjectaanduiding))
            )
            .andExpect(status().isOk());

        // Validate the Adresseerbaarobjectaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAdresseerbaarobjectaanduidingToMatchAllProperties(updatedAdresseerbaarobjectaanduiding);
    }

    @Test
    @Transactional
    void putNonExistingAdresseerbaarobjectaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresseerbaarobjectaanduiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresseerbaarobjectaanduidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adresseerbaarobjectaanduiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adresseerbaarobjectaanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresseerbaarobjectaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdresseerbaarobjectaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresseerbaarobjectaanduiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseerbaarobjectaanduidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adresseerbaarobjectaanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresseerbaarobjectaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdresseerbaarobjectaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresseerbaarobjectaanduiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseerbaarobjectaanduidingMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseerbaarobjectaanduiding))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adresseerbaarobjectaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdresseerbaarobjectaanduidingWithPatch() throws Exception {
        // Initialize the database
        adresseerbaarobjectaanduidingRepository.saveAndFlush(adresseerbaarobjectaanduiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adresseerbaarobjectaanduiding using partial update
        Adresseerbaarobjectaanduiding partialUpdatedAdresseerbaarobjectaanduiding = new Adresseerbaarobjectaanduiding();
        partialUpdatedAdresseerbaarobjectaanduiding.setId(adresseerbaarobjectaanduiding.getId());

        restAdresseerbaarobjectaanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdresseerbaarobjectaanduiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdresseerbaarobjectaanduiding))
            )
            .andExpect(status().isOk());

        // Validate the Adresseerbaarobjectaanduiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdresseerbaarobjectaanduidingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAdresseerbaarobjectaanduiding, adresseerbaarobjectaanduiding),
            getPersistedAdresseerbaarobjectaanduiding(adresseerbaarobjectaanduiding)
        );
    }

    @Test
    @Transactional
    void fullUpdateAdresseerbaarobjectaanduidingWithPatch() throws Exception {
        // Initialize the database
        adresseerbaarobjectaanduidingRepository.saveAndFlush(adresseerbaarobjectaanduiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adresseerbaarobjectaanduiding using partial update
        Adresseerbaarobjectaanduiding partialUpdatedAdresseerbaarobjectaanduiding = new Adresseerbaarobjectaanduiding();
        partialUpdatedAdresseerbaarobjectaanduiding.setId(adresseerbaarobjectaanduiding.getId());

        partialUpdatedAdresseerbaarobjectaanduiding.identificatie(UPDATED_IDENTIFICATIE);

        restAdresseerbaarobjectaanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdresseerbaarobjectaanduiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdresseerbaarobjectaanduiding))
            )
            .andExpect(status().isOk());

        // Validate the Adresseerbaarobjectaanduiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdresseerbaarobjectaanduidingUpdatableFieldsEquals(
            partialUpdatedAdresseerbaarobjectaanduiding,
            getPersistedAdresseerbaarobjectaanduiding(partialUpdatedAdresseerbaarobjectaanduiding)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAdresseerbaarobjectaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresseerbaarobjectaanduiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresseerbaarobjectaanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adresseerbaarobjectaanduiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adresseerbaarobjectaanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresseerbaarobjectaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdresseerbaarobjectaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresseerbaarobjectaanduiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseerbaarobjectaanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adresseerbaarobjectaanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresseerbaarobjectaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdresseerbaarobjectaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresseerbaarobjectaanduiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseerbaarobjectaanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adresseerbaarobjectaanduiding))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adresseerbaarobjectaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdresseerbaarobjectaanduiding() throws Exception {
        // Initialize the database
        adresseerbaarobjectaanduidingRepository.saveAndFlush(adresseerbaarobjectaanduiding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the adresseerbaarobjectaanduiding
        restAdresseerbaarobjectaanduidingMockMvc
            .perform(delete(ENTITY_API_URL_ID, adresseerbaarobjectaanduiding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return adresseerbaarobjectaanduidingRepository.count();
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

    protected Adresseerbaarobjectaanduiding getPersistedAdresseerbaarobjectaanduiding(
        Adresseerbaarobjectaanduiding adresseerbaarobjectaanduiding
    ) {
        return adresseerbaarobjectaanduidingRepository.findById(adresseerbaarobjectaanduiding.getId()).orElseThrow();
    }

    protected void assertPersistedAdresseerbaarobjectaanduidingToMatchAllProperties(
        Adresseerbaarobjectaanduiding expectedAdresseerbaarobjectaanduiding
    ) {
        assertAdresseerbaarobjectaanduidingAllPropertiesEquals(
            expectedAdresseerbaarobjectaanduiding,
            getPersistedAdresseerbaarobjectaanduiding(expectedAdresseerbaarobjectaanduiding)
        );
    }

    protected void assertPersistedAdresseerbaarobjectaanduidingToMatchUpdatableProperties(
        Adresseerbaarobjectaanduiding expectedAdresseerbaarobjectaanduiding
    ) {
        assertAdresseerbaarobjectaanduidingAllUpdatablePropertiesEquals(
            expectedAdresseerbaarobjectaanduiding,
            getPersistedAdresseerbaarobjectaanduiding(expectedAdresseerbaarobjectaanduiding)
        );
    }
}
