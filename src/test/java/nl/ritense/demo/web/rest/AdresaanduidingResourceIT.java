package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AdresaanduidingAsserts.*;
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
import nl.ritense.demo.domain.Adresaanduiding;
import nl.ritense.demo.repository.AdresaanduidingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AdresaanduidingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdresaanduidingResourceIT {

    private static final String DEFAULT_ADRESAANDUIDING = "AAAAAAAAAA";
    private static final String UPDATED_ADRESAANDUIDING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/adresaanduidings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AdresaanduidingRepository adresaanduidingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdresaanduidingMockMvc;

    private Adresaanduiding adresaanduiding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresaanduiding createEntity(EntityManager em) {
        Adresaanduiding adresaanduiding = new Adresaanduiding().adresaanduiding(DEFAULT_ADRESAANDUIDING);
        return adresaanduiding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresaanduiding createUpdatedEntity(EntityManager em) {
        Adresaanduiding adresaanduiding = new Adresaanduiding().adresaanduiding(UPDATED_ADRESAANDUIDING);
        return adresaanduiding;
    }

    @BeforeEach
    public void initTest() {
        adresaanduiding = createEntity(em);
    }

    @Test
    @Transactional
    void createAdresaanduiding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Adresaanduiding
        var returnedAdresaanduiding = om.readValue(
            restAdresaanduidingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresaanduiding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Adresaanduiding.class
        );

        // Validate the Adresaanduiding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAdresaanduidingUpdatableFieldsEquals(returnedAdresaanduiding, getPersistedAdresaanduiding(returnedAdresaanduiding));
    }

    @Test
    @Transactional
    void createAdresaanduidingWithExistingId() throws Exception {
        // Create the Adresaanduiding with an existing ID
        adresaanduiding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdresaanduidingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresaanduiding)))
            .andExpect(status().isBadRequest());

        // Validate the Adresaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdresaanduidings() throws Exception {
        // Initialize the database
        adresaanduidingRepository.saveAndFlush(adresaanduiding);

        // Get all the adresaanduidingList
        restAdresaanduidingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adresaanduiding.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresaanduiding").value(hasItem(DEFAULT_ADRESAANDUIDING)));
    }

    @Test
    @Transactional
    void getAdresaanduiding() throws Exception {
        // Initialize the database
        adresaanduidingRepository.saveAndFlush(adresaanduiding);

        // Get the adresaanduiding
        restAdresaanduidingMockMvc
            .perform(get(ENTITY_API_URL_ID, adresaanduiding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adresaanduiding.getId().intValue()))
            .andExpect(jsonPath("$.adresaanduiding").value(DEFAULT_ADRESAANDUIDING));
    }

    @Test
    @Transactional
    void getNonExistingAdresaanduiding() throws Exception {
        // Get the adresaanduiding
        restAdresaanduidingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdresaanduiding() throws Exception {
        // Initialize the database
        adresaanduidingRepository.saveAndFlush(adresaanduiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adresaanduiding
        Adresaanduiding updatedAdresaanduiding = adresaanduidingRepository.findById(adresaanduiding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAdresaanduiding are not directly saved in db
        em.detach(updatedAdresaanduiding);
        updatedAdresaanduiding.adresaanduiding(UPDATED_ADRESAANDUIDING);

        restAdresaanduidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdresaanduiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAdresaanduiding))
            )
            .andExpect(status().isOk());

        // Validate the Adresaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAdresaanduidingToMatchAllProperties(updatedAdresaanduiding);
    }

    @Test
    @Transactional
    void putNonExistingAdresaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresaanduiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresaanduidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adresaanduiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adresaanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdresaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresaanduiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresaanduidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adresaanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdresaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresaanduiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresaanduidingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresaanduiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adresaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdresaanduidingWithPatch() throws Exception {
        // Initialize the database
        adresaanduidingRepository.saveAndFlush(adresaanduiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adresaanduiding using partial update
        Adresaanduiding partialUpdatedAdresaanduiding = new Adresaanduiding();
        partialUpdatedAdresaanduiding.setId(adresaanduiding.getId());

        restAdresaanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdresaanduiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdresaanduiding))
            )
            .andExpect(status().isOk());

        // Validate the Adresaanduiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdresaanduidingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAdresaanduiding, adresaanduiding),
            getPersistedAdresaanduiding(adresaanduiding)
        );
    }

    @Test
    @Transactional
    void fullUpdateAdresaanduidingWithPatch() throws Exception {
        // Initialize the database
        adresaanduidingRepository.saveAndFlush(adresaanduiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adresaanduiding using partial update
        Adresaanduiding partialUpdatedAdresaanduiding = new Adresaanduiding();
        partialUpdatedAdresaanduiding.setId(adresaanduiding.getId());

        partialUpdatedAdresaanduiding.adresaanduiding(UPDATED_ADRESAANDUIDING);

        restAdresaanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdresaanduiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdresaanduiding))
            )
            .andExpect(status().isOk());

        // Validate the Adresaanduiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdresaanduidingUpdatableFieldsEquals(
            partialUpdatedAdresaanduiding,
            getPersistedAdresaanduiding(partialUpdatedAdresaanduiding)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAdresaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresaanduiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresaanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adresaanduiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adresaanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdresaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresaanduiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresaanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adresaanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdresaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresaanduiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresaanduidingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(adresaanduiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adresaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdresaanduiding() throws Exception {
        // Initialize the database
        adresaanduidingRepository.saveAndFlush(adresaanduiding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the adresaanduiding
        restAdresaanduidingMockMvc
            .perform(delete(ENTITY_API_URL_ID, adresaanduiding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return adresaanduidingRepository.count();
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

    protected Adresaanduiding getPersistedAdresaanduiding(Adresaanduiding adresaanduiding) {
        return adresaanduidingRepository.findById(adresaanduiding.getId()).orElseThrow();
    }

    protected void assertPersistedAdresaanduidingToMatchAllProperties(Adresaanduiding expectedAdresaanduiding) {
        assertAdresaanduidingAllPropertiesEquals(expectedAdresaanduiding, getPersistedAdresaanduiding(expectedAdresaanduiding));
    }

    protected void assertPersistedAdresaanduidingToMatchUpdatableProperties(Adresaanduiding expectedAdresaanduiding) {
        assertAdresaanduidingAllUpdatablePropertiesEquals(expectedAdresaanduiding, getPersistedAdresaanduiding(expectedAdresaanduiding));
    }
}
