package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GebouwAsserts.*;
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
import nl.ritense.demo.domain.Gebouw;
import nl.ritense.demo.repository.GebouwRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GebouwResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class GebouwResourceIT {

    private static final String DEFAULT_AANTAL = "AAAAAAAAAA";
    private static final String UPDATED_AANTAL = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALADRESSEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALADRESSEN = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALKAMERS = "AAAAAAAAAA";
    private static final String UPDATED_AANTALKAMERS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AARDGASLOOS = false;
    private static final Boolean UPDATED_AARDGASLOOS = true;

    private static final Boolean DEFAULT_DUURZAAM = false;
    private static final Boolean UPDATED_DUURZAAM = true;

    private static final String DEFAULT_ENERGIELABEL = "AAAAAAAAAA";
    private static final String UPDATED_ENERGIELABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NATUURINCLUSIEF = false;
    private static final Boolean UPDATED_NATUURINCLUSIEF = true;

    private static final String DEFAULT_OPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REGENWATER = false;
    private static final Boolean UPDATED_REGENWATER = true;

    private static final String ENTITY_API_URL = "/api/gebouws";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GebouwRepository gebouwRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGebouwMockMvc;

    private Gebouw gebouw;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gebouw createEntity(EntityManager em) {
        Gebouw gebouw = new Gebouw()
            .aantal(DEFAULT_AANTAL)
            .aantaladressen(DEFAULT_AANTALADRESSEN)
            .aantalkamers(DEFAULT_AANTALKAMERS)
            .aardgasloos(DEFAULT_AARDGASLOOS)
            .duurzaam(DEFAULT_DUURZAAM)
            .energielabel(DEFAULT_ENERGIELABEL)
            .natuurinclusief(DEFAULT_NATUURINCLUSIEF)
            .oppervlakte(DEFAULT_OPPERVLAKTE)
            .regenwater(DEFAULT_REGENWATER);
        return gebouw;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gebouw createUpdatedEntity(EntityManager em) {
        Gebouw gebouw = new Gebouw()
            .aantal(UPDATED_AANTAL)
            .aantaladressen(UPDATED_AANTALADRESSEN)
            .aantalkamers(UPDATED_AANTALKAMERS)
            .aardgasloos(UPDATED_AARDGASLOOS)
            .duurzaam(UPDATED_DUURZAAM)
            .energielabel(UPDATED_ENERGIELABEL)
            .natuurinclusief(UPDATED_NATUURINCLUSIEF)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .regenwater(UPDATED_REGENWATER);
        return gebouw;
    }

    @BeforeEach
    public void initTest() {
        gebouw = createEntity(em);
    }

    @Test
    @Transactional
    void createGebouw() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Gebouw
        var returnedGebouw = om.readValue(
            restGebouwMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebouw)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Gebouw.class
        );

        // Validate the Gebouw in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGebouwUpdatableFieldsEquals(returnedGebouw, getPersistedGebouw(returnedGebouw));
    }

    @Test
    @Transactional
    void createGebouwWithExistingId() throws Exception {
        // Create the Gebouw with an existing ID
        gebouw.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGebouwMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebouw)))
            .andExpect(status().isBadRequest());

        // Validate the Gebouw in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGebouws() throws Exception {
        // Initialize the database
        gebouwRepository.saveAndFlush(gebouw);

        // Get all the gebouwList
        restGebouwMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gebouw.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantal").value(hasItem(DEFAULT_AANTAL)))
            .andExpect(jsonPath("$.[*].aantaladressen").value(hasItem(DEFAULT_AANTALADRESSEN)))
            .andExpect(jsonPath("$.[*].aantalkamers").value(hasItem(DEFAULT_AANTALKAMERS)))
            .andExpect(jsonPath("$.[*].aardgasloos").value(hasItem(DEFAULT_AARDGASLOOS.booleanValue())))
            .andExpect(jsonPath("$.[*].duurzaam").value(hasItem(DEFAULT_DUURZAAM.booleanValue())))
            .andExpect(jsonPath("$.[*].energielabel").value(hasItem(DEFAULT_ENERGIELABEL)))
            .andExpect(jsonPath("$.[*].natuurinclusief").value(hasItem(DEFAULT_NATUURINCLUSIEF.booleanValue())))
            .andExpect(jsonPath("$.[*].oppervlakte").value(hasItem(DEFAULT_OPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].regenwater").value(hasItem(DEFAULT_REGENWATER.booleanValue())));
    }

    @Test
    @Transactional
    void getGebouw() throws Exception {
        // Initialize the database
        gebouwRepository.saveAndFlush(gebouw);

        // Get the gebouw
        restGebouwMockMvc
            .perform(get(ENTITY_API_URL_ID, gebouw.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gebouw.getId().intValue()))
            .andExpect(jsonPath("$.aantal").value(DEFAULT_AANTAL))
            .andExpect(jsonPath("$.aantaladressen").value(DEFAULT_AANTALADRESSEN))
            .andExpect(jsonPath("$.aantalkamers").value(DEFAULT_AANTALKAMERS))
            .andExpect(jsonPath("$.aardgasloos").value(DEFAULT_AARDGASLOOS.booleanValue()))
            .andExpect(jsonPath("$.duurzaam").value(DEFAULT_DUURZAAM.booleanValue()))
            .andExpect(jsonPath("$.energielabel").value(DEFAULT_ENERGIELABEL))
            .andExpect(jsonPath("$.natuurinclusief").value(DEFAULT_NATUURINCLUSIEF.booleanValue()))
            .andExpect(jsonPath("$.oppervlakte").value(DEFAULT_OPPERVLAKTE))
            .andExpect(jsonPath("$.regenwater").value(DEFAULT_REGENWATER.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingGebouw() throws Exception {
        // Get the gebouw
        restGebouwMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGebouw() throws Exception {
        // Initialize the database
        gebouwRepository.saveAndFlush(gebouw);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebouw
        Gebouw updatedGebouw = gebouwRepository.findById(gebouw.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGebouw are not directly saved in db
        em.detach(updatedGebouw);
        updatedGebouw
            .aantal(UPDATED_AANTAL)
            .aantaladressen(UPDATED_AANTALADRESSEN)
            .aantalkamers(UPDATED_AANTALKAMERS)
            .aardgasloos(UPDATED_AARDGASLOOS)
            .duurzaam(UPDATED_DUURZAAM)
            .energielabel(UPDATED_ENERGIELABEL)
            .natuurinclusief(UPDATED_NATUURINCLUSIEF)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .regenwater(UPDATED_REGENWATER);

        restGebouwMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGebouw.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGebouw))
            )
            .andExpect(status().isOk());

        // Validate the Gebouw in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGebouwToMatchAllProperties(updatedGebouw);
    }

    @Test
    @Transactional
    void putNonExistingGebouw() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouw.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGebouwMockMvc
            .perform(put(ENTITY_API_URL_ID, gebouw.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebouw)))
            .andExpect(status().isBadRequest());

        // Validate the Gebouw in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGebouw() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouw.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebouwMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gebouw))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebouw in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGebouw() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouw.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebouwMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebouw)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gebouw in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGebouwWithPatch() throws Exception {
        // Initialize the database
        gebouwRepository.saveAndFlush(gebouw);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebouw using partial update
        Gebouw partialUpdatedGebouw = new Gebouw();
        partialUpdatedGebouw.setId(gebouw.getId());

        partialUpdatedGebouw.aantaladressen(UPDATED_AANTALADRESSEN).aantalkamers(UPDATED_AANTALKAMERS).aardgasloos(UPDATED_AARDGASLOOS);

        restGebouwMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGebouw.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGebouw))
            )
            .andExpect(status().isOk());

        // Validate the Gebouw in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGebouwUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedGebouw, gebouw), getPersistedGebouw(gebouw));
    }

    @Test
    @Transactional
    void fullUpdateGebouwWithPatch() throws Exception {
        // Initialize the database
        gebouwRepository.saveAndFlush(gebouw);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebouw using partial update
        Gebouw partialUpdatedGebouw = new Gebouw();
        partialUpdatedGebouw.setId(gebouw.getId());

        partialUpdatedGebouw
            .aantal(UPDATED_AANTAL)
            .aantaladressen(UPDATED_AANTALADRESSEN)
            .aantalkamers(UPDATED_AANTALKAMERS)
            .aardgasloos(UPDATED_AARDGASLOOS)
            .duurzaam(UPDATED_DUURZAAM)
            .energielabel(UPDATED_ENERGIELABEL)
            .natuurinclusief(UPDATED_NATUURINCLUSIEF)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .regenwater(UPDATED_REGENWATER);

        restGebouwMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGebouw.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGebouw))
            )
            .andExpect(status().isOk());

        // Validate the Gebouw in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGebouwUpdatableFieldsEquals(partialUpdatedGebouw, getPersistedGebouw(partialUpdatedGebouw));
    }

    @Test
    @Transactional
    void patchNonExistingGebouw() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouw.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGebouwMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gebouw.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gebouw))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebouw in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGebouw() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouw.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebouwMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gebouw))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebouw in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGebouw() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouw.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebouwMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gebouw)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gebouw in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGebouw() throws Exception {
        // Initialize the database
        gebouwRepository.saveAndFlush(gebouw);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gebouw
        restGebouwMockMvc
            .perform(delete(ENTITY_API_URL_ID, gebouw.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gebouwRepository.count();
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

    protected Gebouw getPersistedGebouw(Gebouw gebouw) {
        return gebouwRepository.findById(gebouw.getId()).orElseThrow();
    }

    protected void assertPersistedGebouwToMatchAllProperties(Gebouw expectedGebouw) {
        assertGebouwAllPropertiesEquals(expectedGebouw, getPersistedGebouw(expectedGebouw));
    }

    protected void assertPersistedGebouwToMatchUpdatableProperties(Gebouw expectedGebouw) {
        assertGebouwAllUpdatablePropertiesEquals(expectedGebouw, getPersistedGebouw(expectedGebouw));
    }
}
