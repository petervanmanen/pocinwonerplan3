package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GemaalAsserts.*;
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
import nl.ritense.demo.domain.Gemaal;
import nl.ritense.demo.repository.GemaalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GemaalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GemaalResourceIT {

    private static final String DEFAULT_AANTALBEDRIJFSAANSLUITINGEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALBEDRIJFSAANSLUITINGEN = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALHUISAANSLUITINGEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALHUISAANSLUITINGEN = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALPOMPEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALPOMPEN = "BBBBBBBBBB";

    private static final String DEFAULT_BEDIENAAR = "AAAAAAAAAA";
    private static final String UPDATED_BEDIENAAR = "BBBBBBBBBB";

    private static final String DEFAULT_EFFECTIEVEGEMAALCAPACITEIT = "AAAAAAAAAA";
    private static final String UPDATED_EFFECTIEVEGEMAALCAPACITEIT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HIJSINRICHTING = false;
    private static final Boolean UPDATED_HIJSINRICHTING = true;

    private static final Boolean DEFAULT_LANCEERINRICHTING = false;
    private static final Boolean UPDATED_LANCEERINRICHTING = true;

    private static final Boolean DEFAULT_POMPENINSAMENLOOP = false;
    private static final Boolean UPDATED_POMPENINSAMENLOOP = true;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VEILIGHEIDSROOSTER = false;
    private static final Boolean UPDATED_VEILIGHEIDSROOSTER = true;

    private static final String ENTITY_API_URL = "/api/gemaals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GemaalRepository gemaalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGemaalMockMvc;

    private Gemaal gemaal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gemaal createEntity(EntityManager em) {
        Gemaal gemaal = new Gemaal()
            .aantalbedrijfsaansluitingen(DEFAULT_AANTALBEDRIJFSAANSLUITINGEN)
            .aantalhuisaansluitingen(DEFAULT_AANTALHUISAANSLUITINGEN)
            .aantalpompen(DEFAULT_AANTALPOMPEN)
            .bedienaar(DEFAULT_BEDIENAAR)
            .effectievegemaalcapaciteit(DEFAULT_EFFECTIEVEGEMAALCAPACITEIT)
            .hijsinrichting(DEFAULT_HIJSINRICHTING)
            .lanceerinrichting(DEFAULT_LANCEERINRICHTING)
            .pompeninsamenloop(DEFAULT_POMPENINSAMENLOOP)
            .type(DEFAULT_TYPE)
            .veiligheidsrooster(DEFAULT_VEILIGHEIDSROOSTER);
        return gemaal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gemaal createUpdatedEntity(EntityManager em) {
        Gemaal gemaal = new Gemaal()
            .aantalbedrijfsaansluitingen(UPDATED_AANTALBEDRIJFSAANSLUITINGEN)
            .aantalhuisaansluitingen(UPDATED_AANTALHUISAANSLUITINGEN)
            .aantalpompen(UPDATED_AANTALPOMPEN)
            .bedienaar(UPDATED_BEDIENAAR)
            .effectievegemaalcapaciteit(UPDATED_EFFECTIEVEGEMAALCAPACITEIT)
            .hijsinrichting(UPDATED_HIJSINRICHTING)
            .lanceerinrichting(UPDATED_LANCEERINRICHTING)
            .pompeninsamenloop(UPDATED_POMPENINSAMENLOOP)
            .type(UPDATED_TYPE)
            .veiligheidsrooster(UPDATED_VEILIGHEIDSROOSTER);
        return gemaal;
    }

    @BeforeEach
    public void initTest() {
        gemaal = createEntity(em);
    }

    @Test
    @Transactional
    void createGemaal() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Gemaal
        var returnedGemaal = om.readValue(
            restGemaalMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gemaal)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Gemaal.class
        );

        // Validate the Gemaal in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGemaalUpdatableFieldsEquals(returnedGemaal, getPersistedGemaal(returnedGemaal));
    }

    @Test
    @Transactional
    void createGemaalWithExistingId() throws Exception {
        // Create the Gemaal with an existing ID
        gemaal.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGemaalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gemaal)))
            .andExpect(status().isBadRequest());

        // Validate the Gemaal in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGemaals() throws Exception {
        // Initialize the database
        gemaalRepository.saveAndFlush(gemaal);

        // Get all the gemaalList
        restGemaalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gemaal.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantalbedrijfsaansluitingen").value(hasItem(DEFAULT_AANTALBEDRIJFSAANSLUITINGEN)))
            .andExpect(jsonPath("$.[*].aantalhuisaansluitingen").value(hasItem(DEFAULT_AANTALHUISAANSLUITINGEN)))
            .andExpect(jsonPath("$.[*].aantalpompen").value(hasItem(DEFAULT_AANTALPOMPEN)))
            .andExpect(jsonPath("$.[*].bedienaar").value(hasItem(DEFAULT_BEDIENAAR)))
            .andExpect(jsonPath("$.[*].effectievegemaalcapaciteit").value(hasItem(DEFAULT_EFFECTIEVEGEMAALCAPACITEIT)))
            .andExpect(jsonPath("$.[*].hijsinrichting").value(hasItem(DEFAULT_HIJSINRICHTING.booleanValue())))
            .andExpect(jsonPath("$.[*].lanceerinrichting").value(hasItem(DEFAULT_LANCEERINRICHTING.booleanValue())))
            .andExpect(jsonPath("$.[*].pompeninsamenloop").value(hasItem(DEFAULT_POMPENINSAMENLOOP.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].veiligheidsrooster").value(hasItem(DEFAULT_VEILIGHEIDSROOSTER.booleanValue())));
    }

    @Test
    @Transactional
    void getGemaal() throws Exception {
        // Initialize the database
        gemaalRepository.saveAndFlush(gemaal);

        // Get the gemaal
        restGemaalMockMvc
            .perform(get(ENTITY_API_URL_ID, gemaal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gemaal.getId().intValue()))
            .andExpect(jsonPath("$.aantalbedrijfsaansluitingen").value(DEFAULT_AANTALBEDRIJFSAANSLUITINGEN))
            .andExpect(jsonPath("$.aantalhuisaansluitingen").value(DEFAULT_AANTALHUISAANSLUITINGEN))
            .andExpect(jsonPath("$.aantalpompen").value(DEFAULT_AANTALPOMPEN))
            .andExpect(jsonPath("$.bedienaar").value(DEFAULT_BEDIENAAR))
            .andExpect(jsonPath("$.effectievegemaalcapaciteit").value(DEFAULT_EFFECTIEVEGEMAALCAPACITEIT))
            .andExpect(jsonPath("$.hijsinrichting").value(DEFAULT_HIJSINRICHTING.booleanValue()))
            .andExpect(jsonPath("$.lanceerinrichting").value(DEFAULT_LANCEERINRICHTING.booleanValue()))
            .andExpect(jsonPath("$.pompeninsamenloop").value(DEFAULT_POMPENINSAMENLOOP.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.veiligheidsrooster").value(DEFAULT_VEILIGHEIDSROOSTER.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingGemaal() throws Exception {
        // Get the gemaal
        restGemaalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGemaal() throws Exception {
        // Initialize the database
        gemaalRepository.saveAndFlush(gemaal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gemaal
        Gemaal updatedGemaal = gemaalRepository.findById(gemaal.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGemaal are not directly saved in db
        em.detach(updatedGemaal);
        updatedGemaal
            .aantalbedrijfsaansluitingen(UPDATED_AANTALBEDRIJFSAANSLUITINGEN)
            .aantalhuisaansluitingen(UPDATED_AANTALHUISAANSLUITINGEN)
            .aantalpompen(UPDATED_AANTALPOMPEN)
            .bedienaar(UPDATED_BEDIENAAR)
            .effectievegemaalcapaciteit(UPDATED_EFFECTIEVEGEMAALCAPACITEIT)
            .hijsinrichting(UPDATED_HIJSINRICHTING)
            .lanceerinrichting(UPDATED_LANCEERINRICHTING)
            .pompeninsamenloop(UPDATED_POMPENINSAMENLOOP)
            .type(UPDATED_TYPE)
            .veiligheidsrooster(UPDATED_VEILIGHEIDSROOSTER);

        restGemaalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGemaal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGemaal))
            )
            .andExpect(status().isOk());

        // Validate the Gemaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGemaalToMatchAllProperties(updatedGemaal);
    }

    @Test
    @Transactional
    void putNonExistingGemaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemaal.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGemaalMockMvc
            .perform(put(ENTITY_API_URL_ID, gemaal.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gemaal)))
            .andExpect(status().isBadRequest());

        // Validate the Gemaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGemaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemaal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemaalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gemaal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gemaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGemaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemaal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemaalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gemaal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gemaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGemaalWithPatch() throws Exception {
        // Initialize the database
        gemaalRepository.saveAndFlush(gemaal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gemaal using partial update
        Gemaal partialUpdatedGemaal = new Gemaal();
        partialUpdatedGemaal.setId(gemaal.getId());

        partialUpdatedGemaal
            .aantalhuisaansluitingen(UPDATED_AANTALHUISAANSLUITINGEN)
            .bedienaar(UPDATED_BEDIENAAR)
            .effectievegemaalcapaciteit(UPDATED_EFFECTIEVEGEMAALCAPACITEIT)
            .pompeninsamenloop(UPDATED_POMPENINSAMENLOOP);

        restGemaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGemaal.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGemaal))
            )
            .andExpect(status().isOk());

        // Validate the Gemaal in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGemaalUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedGemaal, gemaal), getPersistedGemaal(gemaal));
    }

    @Test
    @Transactional
    void fullUpdateGemaalWithPatch() throws Exception {
        // Initialize the database
        gemaalRepository.saveAndFlush(gemaal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gemaal using partial update
        Gemaal partialUpdatedGemaal = new Gemaal();
        partialUpdatedGemaal.setId(gemaal.getId());

        partialUpdatedGemaal
            .aantalbedrijfsaansluitingen(UPDATED_AANTALBEDRIJFSAANSLUITINGEN)
            .aantalhuisaansluitingen(UPDATED_AANTALHUISAANSLUITINGEN)
            .aantalpompen(UPDATED_AANTALPOMPEN)
            .bedienaar(UPDATED_BEDIENAAR)
            .effectievegemaalcapaciteit(UPDATED_EFFECTIEVEGEMAALCAPACITEIT)
            .hijsinrichting(UPDATED_HIJSINRICHTING)
            .lanceerinrichting(UPDATED_LANCEERINRICHTING)
            .pompeninsamenloop(UPDATED_POMPENINSAMENLOOP)
            .type(UPDATED_TYPE)
            .veiligheidsrooster(UPDATED_VEILIGHEIDSROOSTER);

        restGemaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGemaal.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGemaal))
            )
            .andExpect(status().isOk());

        // Validate the Gemaal in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGemaalUpdatableFieldsEquals(partialUpdatedGemaal, getPersistedGemaal(partialUpdatedGemaal));
    }

    @Test
    @Transactional
    void patchNonExistingGemaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemaal.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGemaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gemaal.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gemaal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gemaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGemaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemaal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gemaal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gemaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGemaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gemaal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemaalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gemaal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gemaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGemaal() throws Exception {
        // Initialize the database
        gemaalRepository.saveAndFlush(gemaal);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gemaal
        restGemaalMockMvc
            .perform(delete(ENTITY_API_URL_ID, gemaal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gemaalRepository.count();
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

    protected Gemaal getPersistedGemaal(Gemaal gemaal) {
        return gemaalRepository.findById(gemaal.getId()).orElseThrow();
    }

    protected void assertPersistedGemaalToMatchAllProperties(Gemaal expectedGemaal) {
        assertGemaalAllPropertiesEquals(expectedGemaal, getPersistedGemaal(expectedGemaal));
    }

    protected void assertPersistedGemaalToMatchUpdatableProperties(Gemaal expectedGemaal) {
        assertGemaalAllUpdatablePropertiesEquals(expectedGemaal, getPersistedGemaal(expectedGemaal));
    }
}
