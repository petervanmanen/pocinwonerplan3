package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MagazijnlocatieAsserts.*;
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
import nl.ritense.demo.domain.Magazijnlocatie;
import nl.ritense.demo.repository.MagazijnlocatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MagazijnlocatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MagazijnlocatieResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VAKNUMMER = "AAAAAAAA";
    private static final String UPDATED_VAKNUMMER = "BBBBBBBB";

    private static final String DEFAULT_VOLGLETTER = "AAAAAAAA";
    private static final String UPDATED_VOLGLETTER = "BBBBBBBB";

    private static final String ENTITY_API_URL = "/api/magazijnlocaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MagazijnlocatieRepository magazijnlocatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMagazijnlocatieMockMvc;

    private Magazijnlocatie magazijnlocatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Magazijnlocatie createEntity(EntityManager em) {
        Magazijnlocatie magazijnlocatie = new Magazijnlocatie()
            .key(DEFAULT_KEY)
            .vaknummer(DEFAULT_VAKNUMMER)
            .volgletter(DEFAULT_VOLGLETTER);
        return magazijnlocatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Magazijnlocatie createUpdatedEntity(EntityManager em) {
        Magazijnlocatie magazijnlocatie = new Magazijnlocatie()
            .key(UPDATED_KEY)
            .vaknummer(UPDATED_VAKNUMMER)
            .volgletter(UPDATED_VOLGLETTER);
        return magazijnlocatie;
    }

    @BeforeEach
    public void initTest() {
        magazijnlocatie = createEntity(em);
    }

    @Test
    @Transactional
    void createMagazijnlocatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Magazijnlocatie
        var returnedMagazijnlocatie = om.readValue(
            restMagazijnlocatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(magazijnlocatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Magazijnlocatie.class
        );

        // Validate the Magazijnlocatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMagazijnlocatieUpdatableFieldsEquals(returnedMagazijnlocatie, getPersistedMagazijnlocatie(returnedMagazijnlocatie));
    }

    @Test
    @Transactional
    void createMagazijnlocatieWithExistingId() throws Exception {
        // Create the Magazijnlocatie with an existing ID
        magazijnlocatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMagazijnlocatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(magazijnlocatie)))
            .andExpect(status().isBadRequest());

        // Validate the Magazijnlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMagazijnlocaties() throws Exception {
        // Initialize the database
        magazijnlocatieRepository.saveAndFlush(magazijnlocatie);

        // Get all the magazijnlocatieList
        restMagazijnlocatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(magazijnlocatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].vaknummer").value(hasItem(DEFAULT_VAKNUMMER)))
            .andExpect(jsonPath("$.[*].volgletter").value(hasItem(DEFAULT_VOLGLETTER)));
    }

    @Test
    @Transactional
    void getMagazijnlocatie() throws Exception {
        // Initialize the database
        magazijnlocatieRepository.saveAndFlush(magazijnlocatie);

        // Get the magazijnlocatie
        restMagazijnlocatieMockMvc
            .perform(get(ENTITY_API_URL_ID, magazijnlocatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(magazijnlocatie.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.vaknummer").value(DEFAULT_VAKNUMMER))
            .andExpect(jsonPath("$.volgletter").value(DEFAULT_VOLGLETTER));
    }

    @Test
    @Transactional
    void getNonExistingMagazijnlocatie() throws Exception {
        // Get the magazijnlocatie
        restMagazijnlocatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMagazijnlocatie() throws Exception {
        // Initialize the database
        magazijnlocatieRepository.saveAndFlush(magazijnlocatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the magazijnlocatie
        Magazijnlocatie updatedMagazijnlocatie = magazijnlocatieRepository.findById(magazijnlocatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMagazijnlocatie are not directly saved in db
        em.detach(updatedMagazijnlocatie);
        updatedMagazijnlocatie.key(UPDATED_KEY).vaknummer(UPDATED_VAKNUMMER).volgletter(UPDATED_VOLGLETTER);

        restMagazijnlocatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMagazijnlocatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMagazijnlocatie))
            )
            .andExpect(status().isOk());

        // Validate the Magazijnlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMagazijnlocatieToMatchAllProperties(updatedMagazijnlocatie);
    }

    @Test
    @Transactional
    void putNonExistingMagazijnlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        magazijnlocatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMagazijnlocatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, magazijnlocatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(magazijnlocatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Magazijnlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMagazijnlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        magazijnlocatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMagazijnlocatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(magazijnlocatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Magazijnlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMagazijnlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        magazijnlocatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMagazijnlocatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(magazijnlocatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Magazijnlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMagazijnlocatieWithPatch() throws Exception {
        // Initialize the database
        magazijnlocatieRepository.saveAndFlush(magazijnlocatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the magazijnlocatie using partial update
        Magazijnlocatie partialUpdatedMagazijnlocatie = new Magazijnlocatie();
        partialUpdatedMagazijnlocatie.setId(magazijnlocatie.getId());

        partialUpdatedMagazijnlocatie.key(UPDATED_KEY).vaknummer(UPDATED_VAKNUMMER).volgletter(UPDATED_VOLGLETTER);

        restMagazijnlocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMagazijnlocatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMagazijnlocatie))
            )
            .andExpect(status().isOk());

        // Validate the Magazijnlocatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMagazijnlocatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMagazijnlocatie, magazijnlocatie),
            getPersistedMagazijnlocatie(magazijnlocatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateMagazijnlocatieWithPatch() throws Exception {
        // Initialize the database
        magazijnlocatieRepository.saveAndFlush(magazijnlocatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the magazijnlocatie using partial update
        Magazijnlocatie partialUpdatedMagazijnlocatie = new Magazijnlocatie();
        partialUpdatedMagazijnlocatie.setId(magazijnlocatie.getId());

        partialUpdatedMagazijnlocatie.key(UPDATED_KEY).vaknummer(UPDATED_VAKNUMMER).volgletter(UPDATED_VOLGLETTER);

        restMagazijnlocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMagazijnlocatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMagazijnlocatie))
            )
            .andExpect(status().isOk());

        // Validate the Magazijnlocatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMagazijnlocatieUpdatableFieldsEquals(
            partialUpdatedMagazijnlocatie,
            getPersistedMagazijnlocatie(partialUpdatedMagazijnlocatie)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMagazijnlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        magazijnlocatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMagazijnlocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, magazijnlocatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(magazijnlocatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Magazijnlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMagazijnlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        magazijnlocatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMagazijnlocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(magazijnlocatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Magazijnlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMagazijnlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        magazijnlocatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMagazijnlocatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(magazijnlocatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Magazijnlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMagazijnlocatie() throws Exception {
        // Initialize the database
        magazijnlocatieRepository.saveAndFlush(magazijnlocatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the magazijnlocatie
        restMagazijnlocatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, magazijnlocatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return magazijnlocatieRepository.count();
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

    protected Magazijnlocatie getPersistedMagazijnlocatie(Magazijnlocatie magazijnlocatie) {
        return magazijnlocatieRepository.findById(magazijnlocatie.getId()).orElseThrow();
    }

    protected void assertPersistedMagazijnlocatieToMatchAllProperties(Magazijnlocatie expectedMagazijnlocatie) {
        assertMagazijnlocatieAllPropertiesEquals(expectedMagazijnlocatie, getPersistedMagazijnlocatie(expectedMagazijnlocatie));
    }

    protected void assertPersistedMagazijnlocatieToMatchUpdatableProperties(Magazijnlocatie expectedMagazijnlocatie) {
        assertMagazijnlocatieAllUpdatablePropertiesEquals(expectedMagazijnlocatie, getPersistedMagazijnlocatie(expectedMagazijnlocatie));
    }
}
