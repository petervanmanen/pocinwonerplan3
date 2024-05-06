package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RekeningnummerAsserts.*;
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
import nl.ritense.demo.domain.Rekeningnummer;
import nl.ritense.demo.repository.RekeningnummerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RekeningnummerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RekeningnummerResourceIT {

    private static final String DEFAULT_BIC = "AAAAAAAAAA";
    private static final String UPDATED_BIC = "BBBBBBBBBB";

    private static final String DEFAULT_IBAN = "AAAAAAAAAA";
    private static final String UPDATED_IBAN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rekeningnummers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RekeningnummerRepository rekeningnummerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRekeningnummerMockMvc;

    private Rekeningnummer rekeningnummer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rekeningnummer createEntity(EntityManager em) {
        Rekeningnummer rekeningnummer = new Rekeningnummer().bic(DEFAULT_BIC).iban(DEFAULT_IBAN);
        return rekeningnummer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rekeningnummer createUpdatedEntity(EntityManager em) {
        Rekeningnummer rekeningnummer = new Rekeningnummer().bic(UPDATED_BIC).iban(UPDATED_IBAN);
        return rekeningnummer;
    }

    @BeforeEach
    public void initTest() {
        rekeningnummer = createEntity(em);
    }

    @Test
    @Transactional
    void createRekeningnummer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Rekeningnummer
        var returnedRekeningnummer = om.readValue(
            restRekeningnummerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rekeningnummer)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Rekeningnummer.class
        );

        // Validate the Rekeningnummer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRekeningnummerUpdatableFieldsEquals(returnedRekeningnummer, getPersistedRekeningnummer(returnedRekeningnummer));
    }

    @Test
    @Transactional
    void createRekeningnummerWithExistingId() throws Exception {
        // Create the Rekeningnummer with an existing ID
        rekeningnummer.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRekeningnummerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rekeningnummer)))
            .andExpect(status().isBadRequest());

        // Validate the Rekeningnummer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRekeningnummers() throws Exception {
        // Initialize the database
        rekeningnummerRepository.saveAndFlush(rekeningnummer);

        // Get all the rekeningnummerList
        restRekeningnummerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rekeningnummer.getId().intValue())))
            .andExpect(jsonPath("$.[*].bic").value(hasItem(DEFAULT_BIC)))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN)));
    }

    @Test
    @Transactional
    void getRekeningnummer() throws Exception {
        // Initialize the database
        rekeningnummerRepository.saveAndFlush(rekeningnummer);

        // Get the rekeningnummer
        restRekeningnummerMockMvc
            .perform(get(ENTITY_API_URL_ID, rekeningnummer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rekeningnummer.getId().intValue()))
            .andExpect(jsonPath("$.bic").value(DEFAULT_BIC))
            .andExpect(jsonPath("$.iban").value(DEFAULT_IBAN));
    }

    @Test
    @Transactional
    void getNonExistingRekeningnummer() throws Exception {
        // Get the rekeningnummer
        restRekeningnummerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRekeningnummer() throws Exception {
        // Initialize the database
        rekeningnummerRepository.saveAndFlush(rekeningnummer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rekeningnummer
        Rekeningnummer updatedRekeningnummer = rekeningnummerRepository.findById(rekeningnummer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRekeningnummer are not directly saved in db
        em.detach(updatedRekeningnummer);
        updatedRekeningnummer.bic(UPDATED_BIC).iban(UPDATED_IBAN);

        restRekeningnummerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRekeningnummer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRekeningnummer))
            )
            .andExpect(status().isOk());

        // Validate the Rekeningnummer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRekeningnummerToMatchAllProperties(updatedRekeningnummer);
    }

    @Test
    @Transactional
    void putNonExistingRekeningnummer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rekeningnummer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRekeningnummerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rekeningnummer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rekeningnummer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rekeningnummer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRekeningnummer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rekeningnummer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRekeningnummerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rekeningnummer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rekeningnummer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRekeningnummer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rekeningnummer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRekeningnummerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rekeningnummer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rekeningnummer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRekeningnummerWithPatch() throws Exception {
        // Initialize the database
        rekeningnummerRepository.saveAndFlush(rekeningnummer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rekeningnummer using partial update
        Rekeningnummer partialUpdatedRekeningnummer = new Rekeningnummer();
        partialUpdatedRekeningnummer.setId(rekeningnummer.getId());

        partialUpdatedRekeningnummer.bic(UPDATED_BIC).iban(UPDATED_IBAN);

        restRekeningnummerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRekeningnummer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRekeningnummer))
            )
            .andExpect(status().isOk());

        // Validate the Rekeningnummer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRekeningnummerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRekeningnummer, rekeningnummer),
            getPersistedRekeningnummer(rekeningnummer)
        );
    }

    @Test
    @Transactional
    void fullUpdateRekeningnummerWithPatch() throws Exception {
        // Initialize the database
        rekeningnummerRepository.saveAndFlush(rekeningnummer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rekeningnummer using partial update
        Rekeningnummer partialUpdatedRekeningnummer = new Rekeningnummer();
        partialUpdatedRekeningnummer.setId(rekeningnummer.getId());

        partialUpdatedRekeningnummer.bic(UPDATED_BIC).iban(UPDATED_IBAN);

        restRekeningnummerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRekeningnummer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRekeningnummer))
            )
            .andExpect(status().isOk());

        // Validate the Rekeningnummer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRekeningnummerUpdatableFieldsEquals(partialUpdatedRekeningnummer, getPersistedRekeningnummer(partialUpdatedRekeningnummer));
    }

    @Test
    @Transactional
    void patchNonExistingRekeningnummer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rekeningnummer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRekeningnummerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rekeningnummer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rekeningnummer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rekeningnummer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRekeningnummer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rekeningnummer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRekeningnummerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rekeningnummer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rekeningnummer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRekeningnummer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rekeningnummer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRekeningnummerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rekeningnummer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rekeningnummer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRekeningnummer() throws Exception {
        // Initialize the database
        rekeningnummerRepository.saveAndFlush(rekeningnummer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rekeningnummer
        restRekeningnummerMockMvc
            .perform(delete(ENTITY_API_URL_ID, rekeningnummer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rekeningnummerRepository.count();
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

    protected Rekeningnummer getPersistedRekeningnummer(Rekeningnummer rekeningnummer) {
        return rekeningnummerRepository.findById(rekeningnummer.getId()).orElseThrow();
    }

    protected void assertPersistedRekeningnummerToMatchAllProperties(Rekeningnummer expectedRekeningnummer) {
        assertRekeningnummerAllPropertiesEquals(expectedRekeningnummer, getPersistedRekeningnummer(expectedRekeningnummer));
    }

    protected void assertPersistedRekeningnummerToMatchUpdatableProperties(Rekeningnummer expectedRekeningnummer) {
        assertRekeningnummerAllUpdatablePropertiesEquals(expectedRekeningnummer, getPersistedRekeningnummer(expectedRekeningnummer));
    }
}
