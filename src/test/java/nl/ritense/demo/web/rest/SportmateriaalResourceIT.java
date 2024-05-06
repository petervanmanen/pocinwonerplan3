package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SportmateriaalAsserts.*;
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
import nl.ritense.demo.domain.Sportmateriaal;
import nl.ritense.demo.repository.SportmateriaalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SportmateriaalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SportmateriaalResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sportmateriaals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SportmateriaalRepository sportmateriaalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSportmateriaalMockMvc;

    private Sportmateriaal sportmateriaal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sportmateriaal createEntity(EntityManager em) {
        Sportmateriaal sportmateriaal = new Sportmateriaal().naam(DEFAULT_NAAM);
        return sportmateriaal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sportmateriaal createUpdatedEntity(EntityManager em) {
        Sportmateriaal sportmateriaal = new Sportmateriaal().naam(UPDATED_NAAM);
        return sportmateriaal;
    }

    @BeforeEach
    public void initTest() {
        sportmateriaal = createEntity(em);
    }

    @Test
    @Transactional
    void createSportmateriaal() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sportmateriaal
        var returnedSportmateriaal = om.readValue(
            restSportmateriaalMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sportmateriaal)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Sportmateriaal.class
        );

        // Validate the Sportmateriaal in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSportmateriaalUpdatableFieldsEquals(returnedSportmateriaal, getPersistedSportmateriaal(returnedSportmateriaal));
    }

    @Test
    @Transactional
    void createSportmateriaalWithExistingId() throws Exception {
        // Create the Sportmateriaal with an existing ID
        sportmateriaal.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSportmateriaalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sportmateriaal)))
            .andExpect(status().isBadRequest());

        // Validate the Sportmateriaal in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSportmateriaals() throws Exception {
        // Initialize the database
        sportmateriaalRepository.saveAndFlush(sportmateriaal);

        // Get all the sportmateriaalList
        restSportmateriaalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sportmateriaal.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getSportmateriaal() throws Exception {
        // Initialize the database
        sportmateriaalRepository.saveAndFlush(sportmateriaal);

        // Get the sportmateriaal
        restSportmateriaalMockMvc
            .perform(get(ENTITY_API_URL_ID, sportmateriaal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sportmateriaal.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingSportmateriaal() throws Exception {
        // Get the sportmateriaal
        restSportmateriaalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSportmateriaal() throws Exception {
        // Initialize the database
        sportmateriaalRepository.saveAndFlush(sportmateriaal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sportmateriaal
        Sportmateriaal updatedSportmateriaal = sportmateriaalRepository.findById(sportmateriaal.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSportmateriaal are not directly saved in db
        em.detach(updatedSportmateriaal);
        updatedSportmateriaal.naam(UPDATED_NAAM);

        restSportmateriaalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSportmateriaal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSportmateriaal))
            )
            .andExpect(status().isOk());

        // Validate the Sportmateriaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSportmateriaalToMatchAllProperties(updatedSportmateriaal);
    }

    @Test
    @Transactional
    void putNonExistingSportmateriaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportmateriaal.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSportmateriaalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sportmateriaal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sportmateriaal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportmateriaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSportmateriaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportmateriaal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportmateriaalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sportmateriaal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportmateriaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSportmateriaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportmateriaal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportmateriaalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sportmateriaal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sportmateriaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSportmateriaalWithPatch() throws Exception {
        // Initialize the database
        sportmateriaalRepository.saveAndFlush(sportmateriaal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sportmateriaal using partial update
        Sportmateriaal partialUpdatedSportmateriaal = new Sportmateriaal();
        partialUpdatedSportmateriaal.setId(sportmateriaal.getId());

        partialUpdatedSportmateriaal.naam(UPDATED_NAAM);

        restSportmateriaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSportmateriaal.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSportmateriaal))
            )
            .andExpect(status().isOk());

        // Validate the Sportmateriaal in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSportmateriaalUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSportmateriaal, sportmateriaal),
            getPersistedSportmateriaal(sportmateriaal)
        );
    }

    @Test
    @Transactional
    void fullUpdateSportmateriaalWithPatch() throws Exception {
        // Initialize the database
        sportmateriaalRepository.saveAndFlush(sportmateriaal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sportmateriaal using partial update
        Sportmateriaal partialUpdatedSportmateriaal = new Sportmateriaal();
        partialUpdatedSportmateriaal.setId(sportmateriaal.getId());

        partialUpdatedSportmateriaal.naam(UPDATED_NAAM);

        restSportmateriaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSportmateriaal.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSportmateriaal))
            )
            .andExpect(status().isOk());

        // Validate the Sportmateriaal in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSportmateriaalUpdatableFieldsEquals(partialUpdatedSportmateriaal, getPersistedSportmateriaal(partialUpdatedSportmateriaal));
    }

    @Test
    @Transactional
    void patchNonExistingSportmateriaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportmateriaal.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSportmateriaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sportmateriaal.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sportmateriaal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportmateriaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSportmateriaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportmateriaal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportmateriaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sportmateriaal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportmateriaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSportmateriaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportmateriaal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportmateriaalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sportmateriaal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sportmateriaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSportmateriaal() throws Exception {
        // Initialize the database
        sportmateriaalRepository.saveAndFlush(sportmateriaal);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sportmateriaal
        restSportmateriaalMockMvc
            .perform(delete(ENTITY_API_URL_ID, sportmateriaal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sportmateriaalRepository.count();
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

    protected Sportmateriaal getPersistedSportmateriaal(Sportmateriaal sportmateriaal) {
        return sportmateriaalRepository.findById(sportmateriaal.getId()).orElseThrow();
    }

    protected void assertPersistedSportmateriaalToMatchAllProperties(Sportmateriaal expectedSportmateriaal) {
        assertSportmateriaalAllPropertiesEquals(expectedSportmateriaal, getPersistedSportmateriaal(expectedSportmateriaal));
    }

    protected void assertPersistedSportmateriaalToMatchUpdatableProperties(Sportmateriaal expectedSportmateriaal) {
        assertSportmateriaalAllUpdatablePropertiesEquals(expectedSportmateriaal, getPersistedSportmateriaal(expectedSportmateriaal));
    }
}
