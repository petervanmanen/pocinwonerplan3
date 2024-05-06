package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OfferteAsserts.*;
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
import nl.ritense.demo.domain.Offerte;
import nl.ritense.demo.repository.OfferteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OfferteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OfferteResourceIT {

    private static final String ENTITY_API_URL = "/api/offertes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OfferteRepository offerteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfferteMockMvc;

    private Offerte offerte;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offerte createEntity(EntityManager em) {
        Offerte offerte = new Offerte();
        return offerte;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offerte createUpdatedEntity(EntityManager em) {
        Offerte offerte = new Offerte();
        return offerte;
    }

    @BeforeEach
    public void initTest() {
        offerte = createEntity(em);
    }

    @Test
    @Transactional
    void createOfferte() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Offerte
        var returnedOfferte = om.readValue(
            restOfferteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerte)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Offerte.class
        );

        // Validate the Offerte in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOfferteUpdatableFieldsEquals(returnedOfferte, getPersistedOfferte(returnedOfferte));
    }

    @Test
    @Transactional
    void createOfferteWithExistingId() throws Exception {
        // Create the Offerte with an existing ID
        offerte.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerte)))
            .andExpect(status().isBadRequest());

        // Validate the Offerte in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOffertes() throws Exception {
        // Initialize the database
        offerteRepository.saveAndFlush(offerte);

        // Get all the offerteList
        restOfferteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offerte.getId().intValue())));
    }

    @Test
    @Transactional
    void getOfferte() throws Exception {
        // Initialize the database
        offerteRepository.saveAndFlush(offerte);

        // Get the offerte
        restOfferteMockMvc
            .perform(get(ENTITY_API_URL_ID, offerte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offerte.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOfferte() throws Exception {
        // Get the offerte
        restOfferteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOfferte() throws Exception {
        // Initialize the database
        offerteRepository.saveAndFlush(offerte);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the offerte
        Offerte updatedOfferte = offerteRepository.findById(offerte.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOfferte are not directly saved in db
        em.detach(updatedOfferte);

        restOfferteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOfferte.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOfferte))
            )
            .andExpect(status().isOk());

        // Validate the Offerte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOfferteToMatchAllProperties(updatedOfferte);
    }

    @Test
    @Transactional
    void putNonExistingOfferte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offerte.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferteMockMvc
            .perform(put(ENTITY_API_URL_ID, offerte.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerte)))
            .andExpect(status().isBadRequest());

        // Validate the Offerte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOfferte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offerte.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(offerte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offerte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOfferte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offerte.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerte)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offerte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOfferteWithPatch() throws Exception {
        // Initialize the database
        offerteRepository.saveAndFlush(offerte);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the offerte using partial update
        Offerte partialUpdatedOfferte = new Offerte();
        partialUpdatedOfferte.setId(offerte.getId());

        restOfferteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOfferte.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOfferte))
            )
            .andExpect(status().isOk());

        // Validate the Offerte in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOfferteUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedOfferte, offerte), getPersistedOfferte(offerte));
    }

    @Test
    @Transactional
    void fullUpdateOfferteWithPatch() throws Exception {
        // Initialize the database
        offerteRepository.saveAndFlush(offerte);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the offerte using partial update
        Offerte partialUpdatedOfferte = new Offerte();
        partialUpdatedOfferte.setId(offerte.getId());

        restOfferteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOfferte.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOfferte))
            )
            .andExpect(status().isOk());

        // Validate the Offerte in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOfferteUpdatableFieldsEquals(partialUpdatedOfferte, getPersistedOfferte(partialUpdatedOfferte));
    }

    @Test
    @Transactional
    void patchNonExistingOfferte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offerte.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, offerte.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(offerte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offerte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOfferte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offerte.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(offerte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offerte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOfferte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offerte.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(offerte)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offerte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOfferte() throws Exception {
        // Initialize the database
        offerteRepository.saveAndFlush(offerte);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the offerte
        restOfferteMockMvc
            .perform(delete(ENTITY_API_URL_ID, offerte.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return offerteRepository.count();
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

    protected Offerte getPersistedOfferte(Offerte offerte) {
        return offerteRepository.findById(offerte.getId()).orElseThrow();
    }

    protected void assertPersistedOfferteToMatchAllProperties(Offerte expectedOfferte) {
        assertOfferteAllPropertiesEquals(expectedOfferte, getPersistedOfferte(expectedOfferte));
    }

    protected void assertPersistedOfferteToMatchUpdatableProperties(Offerte expectedOfferte) {
        assertOfferteAllUpdatablePropertiesEquals(expectedOfferte, getPersistedOfferte(expectedOfferte));
    }
}
