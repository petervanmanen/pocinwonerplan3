package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SportlocatieAsserts.*;
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
import nl.ritense.demo.domain.Sportlocatie;
import nl.ritense.demo.repository.SportlocatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SportlocatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SportlocatieResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sportlocaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SportlocatieRepository sportlocatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSportlocatieMockMvc;

    private Sportlocatie sportlocatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sportlocatie createEntity(EntityManager em) {
        Sportlocatie sportlocatie = new Sportlocatie().naam(DEFAULT_NAAM);
        return sportlocatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sportlocatie createUpdatedEntity(EntityManager em) {
        Sportlocatie sportlocatie = new Sportlocatie().naam(UPDATED_NAAM);
        return sportlocatie;
    }

    @BeforeEach
    public void initTest() {
        sportlocatie = createEntity(em);
    }

    @Test
    @Transactional
    void createSportlocatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sportlocatie
        var returnedSportlocatie = om.readValue(
            restSportlocatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sportlocatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Sportlocatie.class
        );

        // Validate the Sportlocatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSportlocatieUpdatableFieldsEquals(returnedSportlocatie, getPersistedSportlocatie(returnedSportlocatie));
    }

    @Test
    @Transactional
    void createSportlocatieWithExistingId() throws Exception {
        // Create the Sportlocatie with an existing ID
        sportlocatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSportlocatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sportlocatie)))
            .andExpect(status().isBadRequest());

        // Validate the Sportlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSportlocaties() throws Exception {
        // Initialize the database
        sportlocatieRepository.saveAndFlush(sportlocatie);

        // Get all the sportlocatieList
        restSportlocatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sportlocatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getSportlocatie() throws Exception {
        // Initialize the database
        sportlocatieRepository.saveAndFlush(sportlocatie);

        // Get the sportlocatie
        restSportlocatieMockMvc
            .perform(get(ENTITY_API_URL_ID, sportlocatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sportlocatie.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingSportlocatie() throws Exception {
        // Get the sportlocatie
        restSportlocatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSportlocatie() throws Exception {
        // Initialize the database
        sportlocatieRepository.saveAndFlush(sportlocatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sportlocatie
        Sportlocatie updatedSportlocatie = sportlocatieRepository.findById(sportlocatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSportlocatie are not directly saved in db
        em.detach(updatedSportlocatie);
        updatedSportlocatie.naam(UPDATED_NAAM);

        restSportlocatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSportlocatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSportlocatie))
            )
            .andExpect(status().isOk());

        // Validate the Sportlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSportlocatieToMatchAllProperties(updatedSportlocatie);
    }

    @Test
    @Transactional
    void putNonExistingSportlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportlocatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSportlocatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sportlocatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sportlocatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSportlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportlocatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportlocatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sportlocatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSportlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportlocatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportlocatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sportlocatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sportlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSportlocatieWithPatch() throws Exception {
        // Initialize the database
        sportlocatieRepository.saveAndFlush(sportlocatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sportlocatie using partial update
        Sportlocatie partialUpdatedSportlocatie = new Sportlocatie();
        partialUpdatedSportlocatie.setId(sportlocatie.getId());

        partialUpdatedSportlocatie.naam(UPDATED_NAAM);

        restSportlocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSportlocatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSportlocatie))
            )
            .andExpect(status().isOk());

        // Validate the Sportlocatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSportlocatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSportlocatie, sportlocatie),
            getPersistedSportlocatie(sportlocatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateSportlocatieWithPatch() throws Exception {
        // Initialize the database
        sportlocatieRepository.saveAndFlush(sportlocatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sportlocatie using partial update
        Sportlocatie partialUpdatedSportlocatie = new Sportlocatie();
        partialUpdatedSportlocatie.setId(sportlocatie.getId());

        partialUpdatedSportlocatie.naam(UPDATED_NAAM);

        restSportlocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSportlocatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSportlocatie))
            )
            .andExpect(status().isOk());

        // Validate the Sportlocatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSportlocatieUpdatableFieldsEquals(partialUpdatedSportlocatie, getPersistedSportlocatie(partialUpdatedSportlocatie));
    }

    @Test
    @Transactional
    void patchNonExistingSportlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportlocatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSportlocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sportlocatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sportlocatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSportlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportlocatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportlocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sportlocatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSportlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportlocatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportlocatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sportlocatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sportlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSportlocatie() throws Exception {
        // Initialize the database
        sportlocatieRepository.saveAndFlush(sportlocatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sportlocatie
        restSportlocatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, sportlocatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sportlocatieRepository.count();
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

    protected Sportlocatie getPersistedSportlocatie(Sportlocatie sportlocatie) {
        return sportlocatieRepository.findById(sportlocatie.getId()).orElseThrow();
    }

    protected void assertPersistedSportlocatieToMatchAllProperties(Sportlocatie expectedSportlocatie) {
        assertSportlocatieAllPropertiesEquals(expectedSportlocatie, getPersistedSportlocatie(expectedSportlocatie));
    }

    protected void assertPersistedSportlocatieToMatchUpdatableProperties(Sportlocatie expectedSportlocatie) {
        assertSportlocatieAllUpdatablePropertiesEquals(expectedSportlocatie, getPersistedSportlocatie(expectedSportlocatie));
    }
}
