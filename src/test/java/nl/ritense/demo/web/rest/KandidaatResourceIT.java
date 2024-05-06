package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KandidaatAsserts.*;
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
import nl.ritense.demo.domain.Kandidaat;
import nl.ritense.demo.repository.KandidaatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KandidaatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KandidaatResourceIT {

    private static final String DEFAULT_DATUMINGESTUURD = "AAAAAAAAAA";
    private static final String UPDATED_DATUMINGESTUURD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kandidaats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KandidaatRepository kandidaatRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKandidaatMockMvc;

    private Kandidaat kandidaat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kandidaat createEntity(EntityManager em) {
        Kandidaat kandidaat = new Kandidaat().datumingestuurd(DEFAULT_DATUMINGESTUURD);
        return kandidaat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kandidaat createUpdatedEntity(EntityManager em) {
        Kandidaat kandidaat = new Kandidaat().datumingestuurd(UPDATED_DATUMINGESTUURD);
        return kandidaat;
    }

    @BeforeEach
    public void initTest() {
        kandidaat = createEntity(em);
    }

    @Test
    @Transactional
    void createKandidaat() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kandidaat
        var returnedKandidaat = om.readValue(
            restKandidaatMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kandidaat)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kandidaat.class
        );

        // Validate the Kandidaat in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKandidaatUpdatableFieldsEquals(returnedKandidaat, getPersistedKandidaat(returnedKandidaat));
    }

    @Test
    @Transactional
    void createKandidaatWithExistingId() throws Exception {
        // Create the Kandidaat with an existing ID
        kandidaat.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKandidaatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kandidaat)))
            .andExpect(status().isBadRequest());

        // Validate the Kandidaat in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKandidaats() throws Exception {
        // Initialize the database
        kandidaatRepository.saveAndFlush(kandidaat);

        // Get all the kandidaatList
        restKandidaatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kandidaat.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumingestuurd").value(hasItem(DEFAULT_DATUMINGESTUURD)));
    }

    @Test
    @Transactional
    void getKandidaat() throws Exception {
        // Initialize the database
        kandidaatRepository.saveAndFlush(kandidaat);

        // Get the kandidaat
        restKandidaatMockMvc
            .perform(get(ENTITY_API_URL_ID, kandidaat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kandidaat.getId().intValue()))
            .andExpect(jsonPath("$.datumingestuurd").value(DEFAULT_DATUMINGESTUURD));
    }

    @Test
    @Transactional
    void getNonExistingKandidaat() throws Exception {
        // Get the kandidaat
        restKandidaatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKandidaat() throws Exception {
        // Initialize the database
        kandidaatRepository.saveAndFlush(kandidaat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kandidaat
        Kandidaat updatedKandidaat = kandidaatRepository.findById(kandidaat.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKandidaat are not directly saved in db
        em.detach(updatedKandidaat);
        updatedKandidaat.datumingestuurd(UPDATED_DATUMINGESTUURD);

        restKandidaatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKandidaat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKandidaat))
            )
            .andExpect(status().isOk());

        // Validate the Kandidaat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKandidaatToMatchAllProperties(updatedKandidaat);
    }

    @Test
    @Transactional
    void putNonExistingKandidaat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kandidaat.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKandidaatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kandidaat.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kandidaat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kandidaat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKandidaat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kandidaat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKandidaatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kandidaat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kandidaat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKandidaat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kandidaat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKandidaatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kandidaat)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kandidaat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKandidaatWithPatch() throws Exception {
        // Initialize the database
        kandidaatRepository.saveAndFlush(kandidaat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kandidaat using partial update
        Kandidaat partialUpdatedKandidaat = new Kandidaat();
        partialUpdatedKandidaat.setId(kandidaat.getId());

        partialUpdatedKandidaat.datumingestuurd(UPDATED_DATUMINGESTUURD);

        restKandidaatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKandidaat.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKandidaat))
            )
            .andExpect(status().isOk());

        // Validate the Kandidaat in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKandidaatUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKandidaat, kandidaat),
            getPersistedKandidaat(kandidaat)
        );
    }

    @Test
    @Transactional
    void fullUpdateKandidaatWithPatch() throws Exception {
        // Initialize the database
        kandidaatRepository.saveAndFlush(kandidaat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kandidaat using partial update
        Kandidaat partialUpdatedKandidaat = new Kandidaat();
        partialUpdatedKandidaat.setId(kandidaat.getId());

        partialUpdatedKandidaat.datumingestuurd(UPDATED_DATUMINGESTUURD);

        restKandidaatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKandidaat.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKandidaat))
            )
            .andExpect(status().isOk());

        // Validate the Kandidaat in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKandidaatUpdatableFieldsEquals(partialUpdatedKandidaat, getPersistedKandidaat(partialUpdatedKandidaat));
    }

    @Test
    @Transactional
    void patchNonExistingKandidaat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kandidaat.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKandidaatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kandidaat.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kandidaat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kandidaat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKandidaat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kandidaat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKandidaatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kandidaat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kandidaat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKandidaat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kandidaat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKandidaatMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kandidaat)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kandidaat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKandidaat() throws Exception {
        // Initialize the database
        kandidaatRepository.saveAndFlush(kandidaat);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kandidaat
        restKandidaatMockMvc
            .perform(delete(ENTITY_API_URL_ID, kandidaat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kandidaatRepository.count();
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

    protected Kandidaat getPersistedKandidaat(Kandidaat kandidaat) {
        return kandidaatRepository.findById(kandidaat.getId()).orElseThrow();
    }

    protected void assertPersistedKandidaatToMatchAllProperties(Kandidaat expectedKandidaat) {
        assertKandidaatAllPropertiesEquals(expectedKandidaat, getPersistedKandidaat(expectedKandidaat));
    }

    protected void assertPersistedKandidaatToMatchUpdatableProperties(Kandidaat expectedKandidaat) {
        assertKandidaatAllUpdatablePropertiesEquals(expectedKandidaat, getPersistedKandidaat(expectedKandidaat));
    }
}
