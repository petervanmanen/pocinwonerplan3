package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SportterreinAsserts.*;
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
import nl.ritense.demo.domain.Sportterrein;
import nl.ritense.demo.repository.SportterreinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SportterreinResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SportterreinResourceIT {

    private static final Boolean DEFAULT_DRAINAGE = false;
    private static final Boolean UPDATED_DRAINAGE = true;

    private static final String DEFAULT_GEBRUIKSVORM = "AAAAAAAAAA";
    private static final String UPDATED_GEBRUIKSVORM = "BBBBBBBBBB";

    private static final String DEFAULT_SPORTCOMPLEX = "AAAAAAAAAA";
    private static final String UPDATED_SPORTCOMPLEX = "BBBBBBBBBB";

    private static final String DEFAULT_SPORTTERREINTYPESPORT = "AAAAAAAAAA";
    private static final String UPDATED_SPORTTERREINTYPESPORT = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS = "BBBBBBBBBB";

    private static final String DEFAULT_VELDNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_VELDNUMMER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VERLICHT = false;
    private static final Boolean UPDATED_VERLICHT = true;

    private static final String ENTITY_API_URL = "/api/sportterreins";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SportterreinRepository sportterreinRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSportterreinMockMvc;

    private Sportterrein sportterrein;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sportterrein createEntity(EntityManager em) {
        Sportterrein sportterrein = new Sportterrein()
            .drainage(DEFAULT_DRAINAGE)
            .gebruiksvorm(DEFAULT_GEBRUIKSVORM)
            .sportcomplex(DEFAULT_SPORTCOMPLEX)
            .sportterreintypesport(DEFAULT_SPORTTERREINTYPESPORT)
            .type(DEFAULT_TYPE)
            .typeplus(DEFAULT_TYPEPLUS)
            .veldnummer(DEFAULT_VELDNUMMER)
            .verlicht(DEFAULT_VERLICHT);
        return sportterrein;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sportterrein createUpdatedEntity(EntityManager em) {
        Sportterrein sportterrein = new Sportterrein()
            .drainage(UPDATED_DRAINAGE)
            .gebruiksvorm(UPDATED_GEBRUIKSVORM)
            .sportcomplex(UPDATED_SPORTCOMPLEX)
            .sportterreintypesport(UPDATED_SPORTTERREINTYPESPORT)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS)
            .veldnummer(UPDATED_VELDNUMMER)
            .verlicht(UPDATED_VERLICHT);
        return sportterrein;
    }

    @BeforeEach
    public void initTest() {
        sportterrein = createEntity(em);
    }

    @Test
    @Transactional
    void createSportterrein() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sportterrein
        var returnedSportterrein = om.readValue(
            restSportterreinMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sportterrein)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Sportterrein.class
        );

        // Validate the Sportterrein in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSportterreinUpdatableFieldsEquals(returnedSportterrein, getPersistedSportterrein(returnedSportterrein));
    }

    @Test
    @Transactional
    void createSportterreinWithExistingId() throws Exception {
        // Create the Sportterrein with an existing ID
        sportterrein.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSportterreinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sportterrein)))
            .andExpect(status().isBadRequest());

        // Validate the Sportterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSportterreins() throws Exception {
        // Initialize the database
        sportterreinRepository.saveAndFlush(sportterrein);

        // Get all the sportterreinList
        restSportterreinMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sportterrein.getId().intValue())))
            .andExpect(jsonPath("$.[*].drainage").value(hasItem(DEFAULT_DRAINAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].gebruiksvorm").value(hasItem(DEFAULT_GEBRUIKSVORM)))
            .andExpect(jsonPath("$.[*].sportcomplex").value(hasItem(DEFAULT_SPORTCOMPLEX)))
            .andExpect(jsonPath("$.[*].sportterreintypesport").value(hasItem(DEFAULT_SPORTTERREINTYPESPORT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].typeplus").value(hasItem(DEFAULT_TYPEPLUS)))
            .andExpect(jsonPath("$.[*].veldnummer").value(hasItem(DEFAULT_VELDNUMMER)))
            .andExpect(jsonPath("$.[*].verlicht").value(hasItem(DEFAULT_VERLICHT.booleanValue())));
    }

    @Test
    @Transactional
    void getSportterrein() throws Exception {
        // Initialize the database
        sportterreinRepository.saveAndFlush(sportterrein);

        // Get the sportterrein
        restSportterreinMockMvc
            .perform(get(ENTITY_API_URL_ID, sportterrein.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sportterrein.getId().intValue()))
            .andExpect(jsonPath("$.drainage").value(DEFAULT_DRAINAGE.booleanValue()))
            .andExpect(jsonPath("$.gebruiksvorm").value(DEFAULT_GEBRUIKSVORM))
            .andExpect(jsonPath("$.sportcomplex").value(DEFAULT_SPORTCOMPLEX))
            .andExpect(jsonPath("$.sportterreintypesport").value(DEFAULT_SPORTTERREINTYPESPORT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.typeplus").value(DEFAULT_TYPEPLUS))
            .andExpect(jsonPath("$.veldnummer").value(DEFAULT_VELDNUMMER))
            .andExpect(jsonPath("$.verlicht").value(DEFAULT_VERLICHT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingSportterrein() throws Exception {
        // Get the sportterrein
        restSportterreinMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSportterrein() throws Exception {
        // Initialize the database
        sportterreinRepository.saveAndFlush(sportterrein);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sportterrein
        Sportterrein updatedSportterrein = sportterreinRepository.findById(sportterrein.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSportterrein are not directly saved in db
        em.detach(updatedSportterrein);
        updatedSportterrein
            .drainage(UPDATED_DRAINAGE)
            .gebruiksvorm(UPDATED_GEBRUIKSVORM)
            .sportcomplex(UPDATED_SPORTCOMPLEX)
            .sportterreintypesport(UPDATED_SPORTTERREINTYPESPORT)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS)
            .veldnummer(UPDATED_VELDNUMMER)
            .verlicht(UPDATED_VERLICHT);

        restSportterreinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSportterrein.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSportterrein))
            )
            .andExpect(status().isOk());

        // Validate the Sportterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSportterreinToMatchAllProperties(updatedSportterrein);
    }

    @Test
    @Transactional
    void putNonExistingSportterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportterrein.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSportterreinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sportterrein.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sportterrein))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSportterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportterrein.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportterreinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sportterrein))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSportterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportterrein.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportterreinMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sportterrein)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sportterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSportterreinWithPatch() throws Exception {
        // Initialize the database
        sportterreinRepository.saveAndFlush(sportterrein);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sportterrein using partial update
        Sportterrein partialUpdatedSportterrein = new Sportterrein();
        partialUpdatedSportterrein.setId(sportterrein.getId());

        partialUpdatedSportterrein.drainage(UPDATED_DRAINAGE).sportterreintypesport(UPDATED_SPORTTERREINTYPESPORT);

        restSportterreinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSportterrein.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSportterrein))
            )
            .andExpect(status().isOk());

        // Validate the Sportterrein in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSportterreinUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSportterrein, sportterrein),
            getPersistedSportterrein(sportterrein)
        );
    }

    @Test
    @Transactional
    void fullUpdateSportterreinWithPatch() throws Exception {
        // Initialize the database
        sportterreinRepository.saveAndFlush(sportterrein);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sportterrein using partial update
        Sportterrein partialUpdatedSportterrein = new Sportterrein();
        partialUpdatedSportterrein.setId(sportterrein.getId());

        partialUpdatedSportterrein
            .drainage(UPDATED_DRAINAGE)
            .gebruiksvorm(UPDATED_GEBRUIKSVORM)
            .sportcomplex(UPDATED_SPORTCOMPLEX)
            .sportterreintypesport(UPDATED_SPORTTERREINTYPESPORT)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS)
            .veldnummer(UPDATED_VELDNUMMER)
            .verlicht(UPDATED_VERLICHT);

        restSportterreinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSportterrein.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSportterrein))
            )
            .andExpect(status().isOk());

        // Validate the Sportterrein in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSportterreinUpdatableFieldsEquals(partialUpdatedSportterrein, getPersistedSportterrein(partialUpdatedSportterrein));
    }

    @Test
    @Transactional
    void patchNonExistingSportterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportterrein.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSportterreinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sportterrein.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sportterrein))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSportterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportterrein.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportterreinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sportterrein))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSportterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportterrein.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportterreinMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sportterrein)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sportterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSportterrein() throws Exception {
        // Initialize the database
        sportterreinRepository.saveAndFlush(sportterrein);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sportterrein
        restSportterreinMockMvc
            .perform(delete(ENTITY_API_URL_ID, sportterrein.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sportterreinRepository.count();
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

    protected Sportterrein getPersistedSportterrein(Sportterrein sportterrein) {
        return sportterreinRepository.findById(sportterrein.getId()).orElseThrow();
    }

    protected void assertPersistedSportterreinToMatchAllProperties(Sportterrein expectedSportterrein) {
        assertSportterreinAllPropertiesEquals(expectedSportterrein, getPersistedSportterrein(expectedSportterrein));
    }

    protected void assertPersistedSportterreinToMatchUpdatableProperties(Sportterrein expectedSportterrein) {
        assertSportterreinAllUpdatablePropertiesEquals(expectedSportterrein, getPersistedSportterrein(expectedSportterrein));
    }
}
