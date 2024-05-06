package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BrondocumentenAsserts.*;
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
import nl.ritense.demo.domain.Brondocumenten;
import nl.ritense.demo.repository.BrondocumentenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BrondocumentenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BrondocumentenResourceIT {

    private static final String DEFAULT_AKTEGEMEENTE = "AAAAAAAAAA";
    private static final String UPDATED_AKTEGEMEENTE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMDOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_DATUMDOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTGEMEENTE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTGEMEENTE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTIDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTIDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTOMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/brondocumentens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BrondocumentenRepository brondocumentenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBrondocumentenMockMvc;

    private Brondocumenten brondocumenten;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Brondocumenten createEntity(EntityManager em) {
        Brondocumenten brondocumenten = new Brondocumenten()
            .aktegemeente(DEFAULT_AKTEGEMEENTE)
            .datumdocument(DEFAULT_DATUMDOCUMENT)
            .documentgemeente(DEFAULT_DOCUMENTGEMEENTE)
            .documentidentificatie(DEFAULT_DOCUMENTIDENTIFICATIE)
            .documentomschrijving(DEFAULT_DOCUMENTOMSCHRIJVING);
        return brondocumenten;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Brondocumenten createUpdatedEntity(EntityManager em) {
        Brondocumenten brondocumenten = new Brondocumenten()
            .aktegemeente(UPDATED_AKTEGEMEENTE)
            .datumdocument(UPDATED_DATUMDOCUMENT)
            .documentgemeente(UPDATED_DOCUMENTGEMEENTE)
            .documentidentificatie(UPDATED_DOCUMENTIDENTIFICATIE)
            .documentomschrijving(UPDATED_DOCUMENTOMSCHRIJVING);
        return brondocumenten;
    }

    @BeforeEach
    public void initTest() {
        brondocumenten = createEntity(em);
    }

    @Test
    @Transactional
    void createBrondocumenten() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Brondocumenten
        var returnedBrondocumenten = om.readValue(
            restBrondocumentenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brondocumenten)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Brondocumenten.class
        );

        // Validate the Brondocumenten in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBrondocumentenUpdatableFieldsEquals(returnedBrondocumenten, getPersistedBrondocumenten(returnedBrondocumenten));
    }

    @Test
    @Transactional
    void createBrondocumentenWithExistingId() throws Exception {
        // Create the Brondocumenten with an existing ID
        brondocumenten.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrondocumentenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brondocumenten)))
            .andExpect(status().isBadRequest());

        // Validate the Brondocumenten in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBrondocumentens() throws Exception {
        // Initialize the database
        brondocumentenRepository.saveAndFlush(brondocumenten);

        // Get all the brondocumentenList
        restBrondocumentenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brondocumenten.getId().intValue())))
            .andExpect(jsonPath("$.[*].aktegemeente").value(hasItem(DEFAULT_AKTEGEMEENTE)))
            .andExpect(jsonPath("$.[*].datumdocument").value(hasItem(DEFAULT_DATUMDOCUMENT)))
            .andExpect(jsonPath("$.[*].documentgemeente").value(hasItem(DEFAULT_DOCUMENTGEMEENTE)))
            .andExpect(jsonPath("$.[*].documentidentificatie").value(hasItem(DEFAULT_DOCUMENTIDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].documentomschrijving").value(hasItem(DEFAULT_DOCUMENTOMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getBrondocumenten() throws Exception {
        // Initialize the database
        brondocumentenRepository.saveAndFlush(brondocumenten);

        // Get the brondocumenten
        restBrondocumentenMockMvc
            .perform(get(ENTITY_API_URL_ID, brondocumenten.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(brondocumenten.getId().intValue()))
            .andExpect(jsonPath("$.aktegemeente").value(DEFAULT_AKTEGEMEENTE))
            .andExpect(jsonPath("$.datumdocument").value(DEFAULT_DATUMDOCUMENT))
            .andExpect(jsonPath("$.documentgemeente").value(DEFAULT_DOCUMENTGEMEENTE))
            .andExpect(jsonPath("$.documentidentificatie").value(DEFAULT_DOCUMENTIDENTIFICATIE))
            .andExpect(jsonPath("$.documentomschrijving").value(DEFAULT_DOCUMENTOMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingBrondocumenten() throws Exception {
        // Get the brondocumenten
        restBrondocumentenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBrondocumenten() throws Exception {
        // Initialize the database
        brondocumentenRepository.saveAndFlush(brondocumenten);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the brondocumenten
        Brondocumenten updatedBrondocumenten = brondocumentenRepository.findById(brondocumenten.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBrondocumenten are not directly saved in db
        em.detach(updatedBrondocumenten);
        updatedBrondocumenten
            .aktegemeente(UPDATED_AKTEGEMEENTE)
            .datumdocument(UPDATED_DATUMDOCUMENT)
            .documentgemeente(UPDATED_DOCUMENTGEMEENTE)
            .documentidentificatie(UPDATED_DOCUMENTIDENTIFICATIE)
            .documentomschrijving(UPDATED_DOCUMENTOMSCHRIJVING);

        restBrondocumentenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBrondocumenten.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBrondocumenten))
            )
            .andExpect(status().isOk());

        // Validate the Brondocumenten in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBrondocumentenToMatchAllProperties(updatedBrondocumenten);
    }

    @Test
    @Transactional
    void putNonExistingBrondocumenten() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brondocumenten.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBrondocumentenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, brondocumenten.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(brondocumenten))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brondocumenten in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBrondocumenten() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brondocumenten.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrondocumentenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(brondocumenten))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brondocumenten in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBrondocumenten() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brondocumenten.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrondocumentenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brondocumenten)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Brondocumenten in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBrondocumentenWithPatch() throws Exception {
        // Initialize the database
        brondocumentenRepository.saveAndFlush(brondocumenten);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the brondocumenten using partial update
        Brondocumenten partialUpdatedBrondocumenten = new Brondocumenten();
        partialUpdatedBrondocumenten.setId(brondocumenten.getId());

        partialUpdatedBrondocumenten
            .aktegemeente(UPDATED_AKTEGEMEENTE)
            .datumdocument(UPDATED_DATUMDOCUMENT)
            .documentidentificatie(UPDATED_DOCUMENTIDENTIFICATIE)
            .documentomschrijving(UPDATED_DOCUMENTOMSCHRIJVING);

        restBrondocumentenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBrondocumenten.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBrondocumenten))
            )
            .andExpect(status().isOk());

        // Validate the Brondocumenten in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBrondocumentenUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBrondocumenten, brondocumenten),
            getPersistedBrondocumenten(brondocumenten)
        );
    }

    @Test
    @Transactional
    void fullUpdateBrondocumentenWithPatch() throws Exception {
        // Initialize the database
        brondocumentenRepository.saveAndFlush(brondocumenten);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the brondocumenten using partial update
        Brondocumenten partialUpdatedBrondocumenten = new Brondocumenten();
        partialUpdatedBrondocumenten.setId(brondocumenten.getId());

        partialUpdatedBrondocumenten
            .aktegemeente(UPDATED_AKTEGEMEENTE)
            .datumdocument(UPDATED_DATUMDOCUMENT)
            .documentgemeente(UPDATED_DOCUMENTGEMEENTE)
            .documentidentificatie(UPDATED_DOCUMENTIDENTIFICATIE)
            .documentomschrijving(UPDATED_DOCUMENTOMSCHRIJVING);

        restBrondocumentenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBrondocumenten.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBrondocumenten))
            )
            .andExpect(status().isOk());

        // Validate the Brondocumenten in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBrondocumentenUpdatableFieldsEquals(partialUpdatedBrondocumenten, getPersistedBrondocumenten(partialUpdatedBrondocumenten));
    }

    @Test
    @Transactional
    void patchNonExistingBrondocumenten() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brondocumenten.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBrondocumentenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, brondocumenten.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(brondocumenten))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brondocumenten in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBrondocumenten() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brondocumenten.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrondocumentenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(brondocumenten))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brondocumenten in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBrondocumenten() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brondocumenten.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrondocumentenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(brondocumenten)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Brondocumenten in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBrondocumenten() throws Exception {
        // Initialize the database
        brondocumentenRepository.saveAndFlush(brondocumenten);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the brondocumenten
        restBrondocumentenMockMvc
            .perform(delete(ENTITY_API_URL_ID, brondocumenten.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return brondocumentenRepository.count();
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

    protected Brondocumenten getPersistedBrondocumenten(Brondocumenten brondocumenten) {
        return brondocumentenRepository.findById(brondocumenten.getId()).orElseThrow();
    }

    protected void assertPersistedBrondocumentenToMatchAllProperties(Brondocumenten expectedBrondocumenten) {
        assertBrondocumentenAllPropertiesEquals(expectedBrondocumenten, getPersistedBrondocumenten(expectedBrondocumenten));
    }

    protected void assertPersistedBrondocumentenToMatchUpdatableProperties(Brondocumenten expectedBrondocumenten) {
        assertBrondocumentenAllUpdatablePropertiesEquals(expectedBrondocumenten, getPersistedBrondocumenten(expectedBrondocumenten));
    }
}
