package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MutatieAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Mutatie;
import nl.ritense.demo.repository.MutatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MutatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MutatieResourceIT {

    private static final BigDecimal DEFAULT_BEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAG = new BigDecimal(2);

    private static final String DEFAULT_DATUM = "AAAAAAAAAA";
    private static final String UPDATED_DATUM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mutaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MutatieRepository mutatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMutatieMockMvc;

    private Mutatie mutatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mutatie createEntity(EntityManager em) {
        Mutatie mutatie = new Mutatie().bedrag(DEFAULT_BEDRAG).datum(DEFAULT_DATUM);
        return mutatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mutatie createUpdatedEntity(EntityManager em) {
        Mutatie mutatie = new Mutatie().bedrag(UPDATED_BEDRAG).datum(UPDATED_DATUM);
        return mutatie;
    }

    @BeforeEach
    public void initTest() {
        mutatie = createEntity(em);
    }

    @Test
    @Transactional
    void createMutatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Mutatie
        var returnedMutatie = om.readValue(
            restMutatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mutatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Mutatie.class
        );

        // Validate the Mutatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMutatieUpdatableFieldsEquals(returnedMutatie, getPersistedMutatie(returnedMutatie));
    }

    @Test
    @Transactional
    void createMutatieWithExistingId() throws Exception {
        // Create the Mutatie with an existing ID
        mutatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMutatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mutatie)))
            .andExpect(status().isBadRequest());

        // Validate the Mutatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMutaties() throws Exception {
        // Initialize the database
        mutatieRepository.saveAndFlush(mutatie);

        // Get all the mutatieList
        restMutatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mutatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(sameNumber(DEFAULT_BEDRAG))))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM)));
    }

    @Test
    @Transactional
    void getMutatie() throws Exception {
        // Initialize the database
        mutatieRepository.saveAndFlush(mutatie);

        // Get the mutatie
        restMutatieMockMvc
            .perform(get(ENTITY_API_URL_ID, mutatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mutatie.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(sameNumber(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM));
    }

    @Test
    @Transactional
    void getNonExistingMutatie() throws Exception {
        // Get the mutatie
        restMutatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMutatie() throws Exception {
        // Initialize the database
        mutatieRepository.saveAndFlush(mutatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mutatie
        Mutatie updatedMutatie = mutatieRepository.findById(mutatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMutatie are not directly saved in db
        em.detach(updatedMutatie);
        updatedMutatie.bedrag(UPDATED_BEDRAG).datum(UPDATED_DATUM);

        restMutatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMutatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMutatie))
            )
            .andExpect(status().isOk());

        // Validate the Mutatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMutatieToMatchAllProperties(updatedMutatie);
    }

    @Test
    @Transactional
    void putNonExistingMutatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mutatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMutatieMockMvc
            .perform(put(ENTITY_API_URL_ID, mutatie.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mutatie)))
            .andExpect(status().isBadRequest());

        // Validate the Mutatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMutatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mutatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMutatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mutatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mutatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMutatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mutatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMutatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mutatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mutatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMutatieWithPatch() throws Exception {
        // Initialize the database
        mutatieRepository.saveAndFlush(mutatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mutatie using partial update
        Mutatie partialUpdatedMutatie = new Mutatie();
        partialUpdatedMutatie.setId(mutatie.getId());

        partialUpdatedMutatie.datum(UPDATED_DATUM);

        restMutatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMutatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMutatie))
            )
            .andExpect(status().isOk());

        // Validate the Mutatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMutatieUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMutatie, mutatie), getPersistedMutatie(mutatie));
    }

    @Test
    @Transactional
    void fullUpdateMutatieWithPatch() throws Exception {
        // Initialize the database
        mutatieRepository.saveAndFlush(mutatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mutatie using partial update
        Mutatie partialUpdatedMutatie = new Mutatie();
        partialUpdatedMutatie.setId(mutatie.getId());

        partialUpdatedMutatie.bedrag(UPDATED_BEDRAG).datum(UPDATED_DATUM);

        restMutatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMutatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMutatie))
            )
            .andExpect(status().isOk());

        // Validate the Mutatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMutatieUpdatableFieldsEquals(partialUpdatedMutatie, getPersistedMutatie(partialUpdatedMutatie));
    }

    @Test
    @Transactional
    void patchNonExistingMutatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mutatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMutatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mutatie.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(mutatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mutatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMutatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mutatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMutatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mutatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mutatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMutatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mutatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMutatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(mutatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mutatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMutatie() throws Exception {
        // Initialize the database
        mutatieRepository.saveAndFlush(mutatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the mutatie
        restMutatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, mutatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return mutatieRepository.count();
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

    protected Mutatie getPersistedMutatie(Mutatie mutatie) {
        return mutatieRepository.findById(mutatie.getId()).orElseThrow();
    }

    protected void assertPersistedMutatieToMatchAllProperties(Mutatie expectedMutatie) {
        assertMutatieAllPropertiesEquals(expectedMutatie, getPersistedMutatie(expectedMutatie));
    }

    protected void assertPersistedMutatieToMatchUpdatableProperties(Mutatie expectedMutatie) {
        assertMutatieAllUpdatablePropertiesEquals(expectedMutatie, getPersistedMutatie(expectedMutatie));
    }
}
