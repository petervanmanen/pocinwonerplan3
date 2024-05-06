package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PuntAsserts.*;
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
import nl.ritense.demo.domain.Punt;
import nl.ritense.demo.repository.PuntRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PuntResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PuntResourceIT {

    private static final String DEFAULT_PUNT = "AAAAAAAAAA";
    private static final String UPDATED_PUNT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/punts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PuntRepository puntRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPuntMockMvc;

    private Punt punt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Punt createEntity(EntityManager em) {
        Punt punt = new Punt().punt(DEFAULT_PUNT);
        return punt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Punt createUpdatedEntity(EntityManager em) {
        Punt punt = new Punt().punt(UPDATED_PUNT);
        return punt;
    }

    @BeforeEach
    public void initTest() {
        punt = createEntity(em);
    }

    @Test
    @Transactional
    void createPunt() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Punt
        var returnedPunt = om.readValue(
            restPuntMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(punt)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Punt.class
        );

        // Validate the Punt in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPuntUpdatableFieldsEquals(returnedPunt, getPersistedPunt(returnedPunt));
    }

    @Test
    @Transactional
    void createPuntWithExistingId() throws Exception {
        // Create the Punt with an existing ID
        punt.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPuntMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(punt)))
            .andExpect(status().isBadRequest());

        // Validate the Punt in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPunts() throws Exception {
        // Initialize the database
        puntRepository.saveAndFlush(punt);

        // Get all the puntList
        restPuntMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(punt.getId().intValue())))
            .andExpect(jsonPath("$.[*].punt").value(hasItem(DEFAULT_PUNT)));
    }

    @Test
    @Transactional
    void getPunt() throws Exception {
        // Initialize the database
        puntRepository.saveAndFlush(punt);

        // Get the punt
        restPuntMockMvc
            .perform(get(ENTITY_API_URL_ID, punt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(punt.getId().intValue()))
            .andExpect(jsonPath("$.punt").value(DEFAULT_PUNT));
    }

    @Test
    @Transactional
    void getNonExistingPunt() throws Exception {
        // Get the punt
        restPuntMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPunt() throws Exception {
        // Initialize the database
        puntRepository.saveAndFlush(punt);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the punt
        Punt updatedPunt = puntRepository.findById(punt.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPunt are not directly saved in db
        em.detach(updatedPunt);
        updatedPunt.punt(UPDATED_PUNT);

        restPuntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPunt.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPunt))
            )
            .andExpect(status().isOk());

        // Validate the Punt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPuntToMatchAllProperties(updatedPunt);
    }

    @Test
    @Transactional
    void putNonExistingPunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        punt.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPuntMockMvc
            .perform(put(ENTITY_API_URL_ID, punt.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(punt)))
            .andExpect(status().isBadRequest());

        // Validate the Punt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        punt.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPuntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(punt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Punt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        punt.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPuntMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(punt)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Punt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePuntWithPatch() throws Exception {
        // Initialize the database
        puntRepository.saveAndFlush(punt);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the punt using partial update
        Punt partialUpdatedPunt = new Punt();
        partialUpdatedPunt.setId(punt.getId());

        partialUpdatedPunt.punt(UPDATED_PUNT);

        restPuntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPunt.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPunt))
            )
            .andExpect(status().isOk());

        // Validate the Punt in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPuntUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPunt, punt), getPersistedPunt(punt));
    }

    @Test
    @Transactional
    void fullUpdatePuntWithPatch() throws Exception {
        // Initialize the database
        puntRepository.saveAndFlush(punt);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the punt using partial update
        Punt partialUpdatedPunt = new Punt();
        partialUpdatedPunt.setId(punt.getId());

        partialUpdatedPunt.punt(UPDATED_PUNT);

        restPuntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPunt.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPunt))
            )
            .andExpect(status().isOk());

        // Validate the Punt in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPuntUpdatableFieldsEquals(partialUpdatedPunt, getPersistedPunt(partialUpdatedPunt));
    }

    @Test
    @Transactional
    void patchNonExistingPunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        punt.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPuntMockMvc
            .perform(patch(ENTITY_API_URL_ID, punt.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(punt)))
            .andExpect(status().isBadRequest());

        // Validate the Punt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        punt.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPuntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(punt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Punt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        punt.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPuntMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(punt)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Punt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePunt() throws Exception {
        // Initialize the database
        puntRepository.saveAndFlush(punt);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the punt
        restPuntMockMvc
            .perform(delete(ENTITY_API_URL_ID, punt.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return puntRepository.count();
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

    protected Punt getPersistedPunt(Punt punt) {
        return puntRepository.findById(punt.getId()).orElseThrow();
    }

    protected void assertPersistedPuntToMatchAllProperties(Punt expectedPunt) {
        assertPuntAllPropertiesEquals(expectedPunt, getPersistedPunt(expectedPunt));
    }

    protected void assertPersistedPuntToMatchUpdatableProperties(Punt expectedPunt) {
        assertPuntAllUpdatablePropertiesEquals(expectedPunt, getPersistedPunt(expectedPunt));
    }
}
