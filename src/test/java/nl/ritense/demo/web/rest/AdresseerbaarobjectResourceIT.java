package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AdresseerbaarobjectAsserts.*;
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
import nl.ritense.demo.domain.Adresseerbaarobject;
import nl.ritense.demo.repository.AdresseerbaarobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AdresseerbaarobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdresseerbaarobjectResourceIT {

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEADRESSEERBAAROBJECT = "AAAAAAAAAA";
    private static final String UPDATED_TYPEADRESSEERBAAROBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_VERSIE = "AAAAAAAAAA";
    private static final String UPDATED_VERSIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/adresseerbaarobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AdresseerbaarobjectRepository adresseerbaarobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdresseerbaarobjectMockMvc;

    private Adresseerbaarobject adresseerbaarobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresseerbaarobject createEntity(EntityManager em) {
        Adresseerbaarobject adresseerbaarobject = new Adresseerbaarobject()
            .identificatie(DEFAULT_IDENTIFICATIE)
            .typeadresseerbaarobject(DEFAULT_TYPEADRESSEERBAAROBJECT)
            .versie(DEFAULT_VERSIE);
        return adresseerbaarobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresseerbaarobject createUpdatedEntity(EntityManager em) {
        Adresseerbaarobject adresseerbaarobject = new Adresseerbaarobject()
            .identificatie(UPDATED_IDENTIFICATIE)
            .typeadresseerbaarobject(UPDATED_TYPEADRESSEERBAAROBJECT)
            .versie(UPDATED_VERSIE);
        return adresseerbaarobject;
    }

    @BeforeEach
    public void initTest() {
        adresseerbaarobject = createEntity(em);
    }

    @Test
    @Transactional
    void createAdresseerbaarobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Adresseerbaarobject
        var returnedAdresseerbaarobject = om.readValue(
            restAdresseerbaarobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseerbaarobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Adresseerbaarobject.class
        );

        // Validate the Adresseerbaarobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAdresseerbaarobjectUpdatableFieldsEquals(
            returnedAdresseerbaarobject,
            getPersistedAdresseerbaarobject(returnedAdresseerbaarobject)
        );
    }

    @Test
    @Transactional
    void createAdresseerbaarobjectWithExistingId() throws Exception {
        // Create the Adresseerbaarobject with an existing ID
        adresseerbaarobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdresseerbaarobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseerbaarobject)))
            .andExpect(status().isBadRequest());

        // Validate the Adresseerbaarobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdresseerbaarobjects() throws Exception {
        // Initialize the database
        adresseerbaarobjectRepository.saveAndFlush(adresseerbaarobject);

        // Get all the adresseerbaarobjectList
        restAdresseerbaarobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adresseerbaarobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].typeadresseerbaarobject").value(hasItem(DEFAULT_TYPEADRESSEERBAAROBJECT)))
            .andExpect(jsonPath("$.[*].versie").value(hasItem(DEFAULT_VERSIE)));
    }

    @Test
    @Transactional
    void getAdresseerbaarobject() throws Exception {
        // Initialize the database
        adresseerbaarobjectRepository.saveAndFlush(adresseerbaarobject);

        // Get the adresseerbaarobject
        restAdresseerbaarobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, adresseerbaarobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adresseerbaarobject.getId().intValue()))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.typeadresseerbaarobject").value(DEFAULT_TYPEADRESSEERBAAROBJECT))
            .andExpect(jsonPath("$.versie").value(DEFAULT_VERSIE));
    }

    @Test
    @Transactional
    void getNonExistingAdresseerbaarobject() throws Exception {
        // Get the adresseerbaarobject
        restAdresseerbaarobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdresseerbaarobject() throws Exception {
        // Initialize the database
        adresseerbaarobjectRepository.saveAndFlush(adresseerbaarobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adresseerbaarobject
        Adresseerbaarobject updatedAdresseerbaarobject = adresseerbaarobjectRepository.findById(adresseerbaarobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAdresseerbaarobject are not directly saved in db
        em.detach(updatedAdresseerbaarobject);
        updatedAdresseerbaarobject
            .identificatie(UPDATED_IDENTIFICATIE)
            .typeadresseerbaarobject(UPDATED_TYPEADRESSEERBAAROBJECT)
            .versie(UPDATED_VERSIE);

        restAdresseerbaarobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdresseerbaarobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAdresseerbaarobject))
            )
            .andExpect(status().isOk());

        // Validate the Adresseerbaarobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAdresseerbaarobjectToMatchAllProperties(updatedAdresseerbaarobject);
    }

    @Test
    @Transactional
    void putNonExistingAdresseerbaarobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresseerbaarobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresseerbaarobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adresseerbaarobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adresseerbaarobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresseerbaarobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdresseerbaarobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresseerbaarobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseerbaarobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adresseerbaarobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresseerbaarobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdresseerbaarobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresseerbaarobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseerbaarobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseerbaarobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adresseerbaarobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdresseerbaarobjectWithPatch() throws Exception {
        // Initialize the database
        adresseerbaarobjectRepository.saveAndFlush(adresseerbaarobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adresseerbaarobject using partial update
        Adresseerbaarobject partialUpdatedAdresseerbaarobject = new Adresseerbaarobject();
        partialUpdatedAdresseerbaarobject.setId(adresseerbaarobject.getId());

        partialUpdatedAdresseerbaarobject.versie(UPDATED_VERSIE);

        restAdresseerbaarobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdresseerbaarobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdresseerbaarobject))
            )
            .andExpect(status().isOk());

        // Validate the Adresseerbaarobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdresseerbaarobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAdresseerbaarobject, adresseerbaarobject),
            getPersistedAdresseerbaarobject(adresseerbaarobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateAdresseerbaarobjectWithPatch() throws Exception {
        // Initialize the database
        adresseerbaarobjectRepository.saveAndFlush(adresseerbaarobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adresseerbaarobject using partial update
        Adresseerbaarobject partialUpdatedAdresseerbaarobject = new Adresseerbaarobject();
        partialUpdatedAdresseerbaarobject.setId(adresseerbaarobject.getId());

        partialUpdatedAdresseerbaarobject
            .identificatie(UPDATED_IDENTIFICATIE)
            .typeadresseerbaarobject(UPDATED_TYPEADRESSEERBAAROBJECT)
            .versie(UPDATED_VERSIE);

        restAdresseerbaarobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdresseerbaarobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdresseerbaarobject))
            )
            .andExpect(status().isOk());

        // Validate the Adresseerbaarobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdresseerbaarobjectUpdatableFieldsEquals(
            partialUpdatedAdresseerbaarobject,
            getPersistedAdresseerbaarobject(partialUpdatedAdresseerbaarobject)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAdresseerbaarobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresseerbaarobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresseerbaarobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adresseerbaarobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adresseerbaarobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresseerbaarobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdresseerbaarobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresseerbaarobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseerbaarobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adresseerbaarobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresseerbaarobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdresseerbaarobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresseerbaarobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseerbaarobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(adresseerbaarobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adresseerbaarobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdresseerbaarobject() throws Exception {
        // Initialize the database
        adresseerbaarobjectRepository.saveAndFlush(adresseerbaarobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the adresseerbaarobject
        restAdresseerbaarobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, adresseerbaarobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return adresseerbaarobjectRepository.count();
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

    protected Adresseerbaarobject getPersistedAdresseerbaarobject(Adresseerbaarobject adresseerbaarobject) {
        return adresseerbaarobjectRepository.findById(adresseerbaarobject.getId()).orElseThrow();
    }

    protected void assertPersistedAdresseerbaarobjectToMatchAllProperties(Adresseerbaarobject expectedAdresseerbaarobject) {
        assertAdresseerbaarobjectAllPropertiesEquals(
            expectedAdresseerbaarobject,
            getPersistedAdresseerbaarobject(expectedAdresseerbaarobject)
        );
    }

    protected void assertPersistedAdresseerbaarobjectToMatchUpdatableProperties(Adresseerbaarobject expectedAdresseerbaarobject) {
        assertAdresseerbaarobjectAllUpdatablePropertiesEquals(
            expectedAdresseerbaarobject,
            getPersistedAdresseerbaarobject(expectedAdresseerbaarobject)
        );
    }
}
