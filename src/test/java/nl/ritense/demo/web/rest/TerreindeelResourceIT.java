package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TerreindeelAsserts.*;
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
import nl.ritense.demo.domain.Terreindeel;
import nl.ritense.demo.repository.TerreindeelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TerreindeelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TerreindeelResourceIT {

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_CULTUURHISTORISCHWAARDEVOL = "AAAAAAAAAA";
    private static final String UPDATED_CULTUURHISTORISCHWAARDEVOL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HERPLANTPLICHT = false;
    private static final Boolean UPDATED_HERPLANTPLICHT = true;

    private static final String DEFAULT_OPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_OPTALUD = "AAAAAAAAAA";
    private static final String UPDATED_OPTALUD = "BBBBBBBBBB";

    private static final String DEFAULT_PERCENTAGELOOFBOS = "AAAAAAAAAA";
    private static final String UPDATED_PERCENTAGELOOFBOS = "BBBBBBBBBB";

    private static final String DEFAULT_TERREINDEELSOORTNAAM = "AAAAAAAAAA";
    private static final String UPDATED_TERREINDEELSOORTNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEBEWERKING = "AAAAAAAAAA";
    private static final String UPDATED_TYPEBEWERKING = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS_2 = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS_2 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/terreindeels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TerreindeelRepository terreindeelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTerreindeelMockMvc;

    private Terreindeel terreindeel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Terreindeel createEntity(EntityManager em) {
        Terreindeel terreindeel = new Terreindeel()
            .breedte(DEFAULT_BREEDTE)
            .cultuurhistorischwaardevol(DEFAULT_CULTUURHISTORISCHWAARDEVOL)
            .herplantplicht(DEFAULT_HERPLANTPLICHT)
            .oppervlakte(DEFAULT_OPPERVLAKTE)
            .optalud(DEFAULT_OPTALUD)
            .percentageloofbos(DEFAULT_PERCENTAGELOOFBOS)
            .terreindeelsoortnaam(DEFAULT_TERREINDEELSOORTNAAM)
            .type(DEFAULT_TYPE)
            .typebewerking(DEFAULT_TYPEBEWERKING)
            .typeplus(DEFAULT_TYPEPLUS)
            .typeplus2(DEFAULT_TYPEPLUS_2);
        return terreindeel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Terreindeel createUpdatedEntity(EntityManager em) {
        Terreindeel terreindeel = new Terreindeel()
            .breedte(UPDATED_BREEDTE)
            .cultuurhistorischwaardevol(UPDATED_CULTUURHISTORISCHWAARDEVOL)
            .herplantplicht(UPDATED_HERPLANTPLICHT)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .optalud(UPDATED_OPTALUD)
            .percentageloofbos(UPDATED_PERCENTAGELOOFBOS)
            .terreindeelsoortnaam(UPDATED_TERREINDEELSOORTNAAM)
            .type(UPDATED_TYPE)
            .typebewerking(UPDATED_TYPEBEWERKING)
            .typeplus(UPDATED_TYPEPLUS)
            .typeplus2(UPDATED_TYPEPLUS_2);
        return terreindeel;
    }

    @BeforeEach
    public void initTest() {
        terreindeel = createEntity(em);
    }

    @Test
    @Transactional
    void createTerreindeel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Terreindeel
        var returnedTerreindeel = om.readValue(
            restTerreindeelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(terreindeel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Terreindeel.class
        );

        // Validate the Terreindeel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTerreindeelUpdatableFieldsEquals(returnedTerreindeel, getPersistedTerreindeel(returnedTerreindeel));
    }

    @Test
    @Transactional
    void createTerreindeelWithExistingId() throws Exception {
        // Create the Terreindeel with an existing ID
        terreindeel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTerreindeelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(terreindeel)))
            .andExpect(status().isBadRequest());

        // Validate the Terreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTerreindeels() throws Exception {
        // Initialize the database
        terreindeelRepository.saveAndFlush(terreindeel);

        // Get all the terreindeelList
        restTerreindeelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terreindeel.getId().intValue())))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].cultuurhistorischwaardevol").value(hasItem(DEFAULT_CULTUURHISTORISCHWAARDEVOL)))
            .andExpect(jsonPath("$.[*].herplantplicht").value(hasItem(DEFAULT_HERPLANTPLICHT.booleanValue())))
            .andExpect(jsonPath("$.[*].oppervlakte").value(hasItem(DEFAULT_OPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].optalud").value(hasItem(DEFAULT_OPTALUD)))
            .andExpect(jsonPath("$.[*].percentageloofbos").value(hasItem(DEFAULT_PERCENTAGELOOFBOS)))
            .andExpect(jsonPath("$.[*].terreindeelsoortnaam").value(hasItem(DEFAULT_TERREINDEELSOORTNAAM)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].typebewerking").value(hasItem(DEFAULT_TYPEBEWERKING)))
            .andExpect(jsonPath("$.[*].typeplus").value(hasItem(DEFAULT_TYPEPLUS)))
            .andExpect(jsonPath("$.[*].typeplus2").value(hasItem(DEFAULT_TYPEPLUS_2)));
    }

    @Test
    @Transactional
    void getTerreindeel() throws Exception {
        // Initialize the database
        terreindeelRepository.saveAndFlush(terreindeel);

        // Get the terreindeel
        restTerreindeelMockMvc
            .perform(get(ENTITY_API_URL_ID, terreindeel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(terreindeel.getId().intValue()))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.cultuurhistorischwaardevol").value(DEFAULT_CULTUURHISTORISCHWAARDEVOL))
            .andExpect(jsonPath("$.herplantplicht").value(DEFAULT_HERPLANTPLICHT.booleanValue()))
            .andExpect(jsonPath("$.oppervlakte").value(DEFAULT_OPPERVLAKTE))
            .andExpect(jsonPath("$.optalud").value(DEFAULT_OPTALUD))
            .andExpect(jsonPath("$.percentageloofbos").value(DEFAULT_PERCENTAGELOOFBOS))
            .andExpect(jsonPath("$.terreindeelsoortnaam").value(DEFAULT_TERREINDEELSOORTNAAM))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.typebewerking").value(DEFAULT_TYPEBEWERKING))
            .andExpect(jsonPath("$.typeplus").value(DEFAULT_TYPEPLUS))
            .andExpect(jsonPath("$.typeplus2").value(DEFAULT_TYPEPLUS_2));
    }

    @Test
    @Transactional
    void getNonExistingTerreindeel() throws Exception {
        // Get the terreindeel
        restTerreindeelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTerreindeel() throws Exception {
        // Initialize the database
        terreindeelRepository.saveAndFlush(terreindeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the terreindeel
        Terreindeel updatedTerreindeel = terreindeelRepository.findById(terreindeel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTerreindeel are not directly saved in db
        em.detach(updatedTerreindeel);
        updatedTerreindeel
            .breedte(UPDATED_BREEDTE)
            .cultuurhistorischwaardevol(UPDATED_CULTUURHISTORISCHWAARDEVOL)
            .herplantplicht(UPDATED_HERPLANTPLICHT)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .optalud(UPDATED_OPTALUD)
            .percentageloofbos(UPDATED_PERCENTAGELOOFBOS)
            .terreindeelsoortnaam(UPDATED_TERREINDEELSOORTNAAM)
            .type(UPDATED_TYPE)
            .typebewerking(UPDATED_TYPEBEWERKING)
            .typeplus(UPDATED_TYPEPLUS)
            .typeplus2(UPDATED_TYPEPLUS_2);

        restTerreindeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTerreindeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTerreindeel))
            )
            .andExpect(status().isOk());

        // Validate the Terreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTerreindeelToMatchAllProperties(updatedTerreindeel);
    }

    @Test
    @Transactional
    void putNonExistingTerreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        terreindeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerreindeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, terreindeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(terreindeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Terreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTerreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        terreindeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerreindeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(terreindeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Terreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTerreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        terreindeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerreindeelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(terreindeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Terreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTerreindeelWithPatch() throws Exception {
        // Initialize the database
        terreindeelRepository.saveAndFlush(terreindeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the terreindeel using partial update
        Terreindeel partialUpdatedTerreindeel = new Terreindeel();
        partialUpdatedTerreindeel.setId(terreindeel.getId());

        partialUpdatedTerreindeel.cultuurhistorischwaardevol(UPDATED_CULTUURHISTORISCHWAARDEVOL).typeplus2(UPDATED_TYPEPLUS_2);

        restTerreindeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTerreindeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTerreindeel))
            )
            .andExpect(status().isOk());

        // Validate the Terreindeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTerreindeelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTerreindeel, terreindeel),
            getPersistedTerreindeel(terreindeel)
        );
    }

    @Test
    @Transactional
    void fullUpdateTerreindeelWithPatch() throws Exception {
        // Initialize the database
        terreindeelRepository.saveAndFlush(terreindeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the terreindeel using partial update
        Terreindeel partialUpdatedTerreindeel = new Terreindeel();
        partialUpdatedTerreindeel.setId(terreindeel.getId());

        partialUpdatedTerreindeel
            .breedte(UPDATED_BREEDTE)
            .cultuurhistorischwaardevol(UPDATED_CULTUURHISTORISCHWAARDEVOL)
            .herplantplicht(UPDATED_HERPLANTPLICHT)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .optalud(UPDATED_OPTALUD)
            .percentageloofbos(UPDATED_PERCENTAGELOOFBOS)
            .terreindeelsoortnaam(UPDATED_TERREINDEELSOORTNAAM)
            .type(UPDATED_TYPE)
            .typebewerking(UPDATED_TYPEBEWERKING)
            .typeplus(UPDATED_TYPEPLUS)
            .typeplus2(UPDATED_TYPEPLUS_2);

        restTerreindeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTerreindeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTerreindeel))
            )
            .andExpect(status().isOk());

        // Validate the Terreindeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTerreindeelUpdatableFieldsEquals(partialUpdatedTerreindeel, getPersistedTerreindeel(partialUpdatedTerreindeel));
    }

    @Test
    @Transactional
    void patchNonExistingTerreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        terreindeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerreindeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, terreindeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(terreindeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Terreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTerreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        terreindeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerreindeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(terreindeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Terreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTerreindeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        terreindeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerreindeelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(terreindeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Terreindeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTerreindeel() throws Exception {
        // Initialize the database
        terreindeelRepository.saveAndFlush(terreindeel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the terreindeel
        restTerreindeelMockMvc
            .perform(delete(ENTITY_API_URL_ID, terreindeel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return terreindeelRepository.count();
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

    protected Terreindeel getPersistedTerreindeel(Terreindeel terreindeel) {
        return terreindeelRepository.findById(terreindeel.getId()).orElseThrow();
    }

    protected void assertPersistedTerreindeelToMatchAllProperties(Terreindeel expectedTerreindeel) {
        assertTerreindeelAllPropertiesEquals(expectedTerreindeel, getPersistedTerreindeel(expectedTerreindeel));
    }

    protected void assertPersistedTerreindeelToMatchUpdatableProperties(Terreindeel expectedTerreindeel) {
        assertTerreindeelAllUpdatablePropertiesEquals(expectedTerreindeel, getPersistedTerreindeel(expectedTerreindeel));
    }
}
