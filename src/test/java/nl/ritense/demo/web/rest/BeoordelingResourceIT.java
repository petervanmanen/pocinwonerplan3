package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BeoordelingAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Beoordeling;
import nl.ritense.demo.repository.BeoordelingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BeoordelingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BeoordelingResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_OORDEEL = "AAAAAAAAAA";
    private static final String UPDATED_OORDEEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/beoordelings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BeoordelingRepository beoordelingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeoordelingMockMvc;

    private Beoordeling beoordeling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beoordeling createEntity(EntityManager em) {
        Beoordeling beoordeling = new Beoordeling().datum(DEFAULT_DATUM).omschrijving(DEFAULT_OMSCHRIJVING).oordeel(DEFAULT_OORDEEL);
        return beoordeling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beoordeling createUpdatedEntity(EntityManager em) {
        Beoordeling beoordeling = new Beoordeling().datum(UPDATED_DATUM).omschrijving(UPDATED_OMSCHRIJVING).oordeel(UPDATED_OORDEEL);
        return beoordeling;
    }

    @BeforeEach
    public void initTest() {
        beoordeling = createEntity(em);
    }

    @Test
    @Transactional
    void createBeoordeling() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Beoordeling
        var returnedBeoordeling = om.readValue(
            restBeoordelingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beoordeling)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Beoordeling.class
        );

        // Validate the Beoordeling in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBeoordelingUpdatableFieldsEquals(returnedBeoordeling, getPersistedBeoordeling(returnedBeoordeling));
    }

    @Test
    @Transactional
    void createBeoordelingWithExistingId() throws Exception {
        // Create the Beoordeling with an existing ID
        beoordeling.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeoordelingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beoordeling)))
            .andExpect(status().isBadRequest());

        // Validate the Beoordeling in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBeoordelings() throws Exception {
        // Initialize the database
        beoordelingRepository.saveAndFlush(beoordeling);

        // Get all the beoordelingList
        restBeoordelingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beoordeling.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].oordeel").value(hasItem(DEFAULT_OORDEEL)));
    }

    @Test
    @Transactional
    void getBeoordeling() throws Exception {
        // Initialize the database
        beoordelingRepository.saveAndFlush(beoordeling);

        // Get the beoordeling
        restBeoordelingMockMvc
            .perform(get(ENTITY_API_URL_ID, beoordeling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beoordeling.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.oordeel").value(DEFAULT_OORDEEL));
    }

    @Test
    @Transactional
    void getNonExistingBeoordeling() throws Exception {
        // Get the beoordeling
        restBeoordelingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBeoordeling() throws Exception {
        // Initialize the database
        beoordelingRepository.saveAndFlush(beoordeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beoordeling
        Beoordeling updatedBeoordeling = beoordelingRepository.findById(beoordeling.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBeoordeling are not directly saved in db
        em.detach(updatedBeoordeling);
        updatedBeoordeling.datum(UPDATED_DATUM).omschrijving(UPDATED_OMSCHRIJVING).oordeel(UPDATED_OORDEEL);

        restBeoordelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBeoordeling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBeoordeling))
            )
            .andExpect(status().isOk());

        // Validate the Beoordeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBeoordelingToMatchAllProperties(updatedBeoordeling);
    }

    @Test
    @Transactional
    void putNonExistingBeoordeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beoordeling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeoordelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, beoordeling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beoordeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beoordeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBeoordeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beoordeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeoordelingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beoordeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beoordeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBeoordeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beoordeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeoordelingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beoordeling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beoordeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBeoordelingWithPatch() throws Exception {
        // Initialize the database
        beoordelingRepository.saveAndFlush(beoordeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beoordeling using partial update
        Beoordeling partialUpdatedBeoordeling = new Beoordeling();
        partialUpdatedBeoordeling.setId(beoordeling.getId());

        partialUpdatedBeoordeling.omschrijving(UPDATED_OMSCHRIJVING);

        restBeoordelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeoordeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeoordeling))
            )
            .andExpect(status().isOk());

        // Validate the Beoordeling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeoordelingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBeoordeling, beoordeling),
            getPersistedBeoordeling(beoordeling)
        );
    }

    @Test
    @Transactional
    void fullUpdateBeoordelingWithPatch() throws Exception {
        // Initialize the database
        beoordelingRepository.saveAndFlush(beoordeling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beoordeling using partial update
        Beoordeling partialUpdatedBeoordeling = new Beoordeling();
        partialUpdatedBeoordeling.setId(beoordeling.getId());

        partialUpdatedBeoordeling.datum(UPDATED_DATUM).omschrijving(UPDATED_OMSCHRIJVING).oordeel(UPDATED_OORDEEL);

        restBeoordelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeoordeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeoordeling))
            )
            .andExpect(status().isOk());

        // Validate the Beoordeling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeoordelingUpdatableFieldsEquals(partialUpdatedBeoordeling, getPersistedBeoordeling(partialUpdatedBeoordeling));
    }

    @Test
    @Transactional
    void patchNonExistingBeoordeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beoordeling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeoordelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, beoordeling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beoordeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beoordeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBeoordeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beoordeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeoordelingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beoordeling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beoordeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBeoordeling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beoordeling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeoordelingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(beoordeling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beoordeling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBeoordeling() throws Exception {
        // Initialize the database
        beoordelingRepository.saveAndFlush(beoordeling);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the beoordeling
        restBeoordelingMockMvc
            .perform(delete(ENTITY_API_URL_ID, beoordeling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return beoordelingRepository.count();
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

    protected Beoordeling getPersistedBeoordeling(Beoordeling beoordeling) {
        return beoordelingRepository.findById(beoordeling.getId()).orElseThrow();
    }

    protected void assertPersistedBeoordelingToMatchAllProperties(Beoordeling expectedBeoordeling) {
        assertBeoordelingAllPropertiesEquals(expectedBeoordeling, getPersistedBeoordeling(expectedBeoordeling));
    }

    protected void assertPersistedBeoordelingToMatchUpdatableProperties(Beoordeling expectedBeoordeling) {
        assertBeoordelingAllUpdatablePropertiesEquals(expectedBeoordeling, getPersistedBeoordeling(expectedBeoordeling));
    }
}
