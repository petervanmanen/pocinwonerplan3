package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GeweldsincidentAsserts.*;
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
import nl.ritense.demo.domain.Geweldsincident;
import nl.ritense.demo.repository.GeweldsincidentRepository;
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
 * Integration tests for the {@link GeweldsincidentResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class GeweldsincidentResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/geweldsincidents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GeweldsincidentRepository geweldsincidentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeweldsincidentMockMvc;

    private Geweldsincident geweldsincident;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Geweldsincident createEntity(EntityManager em) {
        Geweldsincident geweldsincident = new Geweldsincident().datum(DEFAULT_DATUM).omschrijving(DEFAULT_OMSCHRIJVING).type(DEFAULT_TYPE);
        return geweldsincident;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Geweldsincident createUpdatedEntity(EntityManager em) {
        Geweldsincident geweldsincident = new Geweldsincident().datum(UPDATED_DATUM).omschrijving(UPDATED_OMSCHRIJVING).type(UPDATED_TYPE);
        return geweldsincident;
    }

    @BeforeEach
    public void initTest() {
        geweldsincident = createEntity(em);
    }

    @Test
    @Transactional
    void createGeweldsincident() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Geweldsincident
        var returnedGeweldsincident = om.readValue(
            restGeweldsincidentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(geweldsincident)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Geweldsincident.class
        );

        // Validate the Geweldsincident in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGeweldsincidentUpdatableFieldsEquals(returnedGeweldsincident, getPersistedGeweldsincident(returnedGeweldsincident));
    }

    @Test
    @Transactional
    void createGeweldsincidentWithExistingId() throws Exception {
        // Create the Geweldsincident with an existing ID
        geweldsincident.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeweldsincidentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(geweldsincident)))
            .andExpect(status().isBadRequest());

        // Validate the Geweldsincident in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGeweldsincidents() throws Exception {
        // Initialize the database
        geweldsincidentRepository.saveAndFlush(geweldsincident);

        // Get all the geweldsincidentList
        restGeweldsincidentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geweldsincident.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getGeweldsincident() throws Exception {
        // Initialize the database
        geweldsincidentRepository.saveAndFlush(geweldsincident);

        // Get the geweldsincident
        restGeweldsincidentMockMvc
            .perform(get(ENTITY_API_URL_ID, geweldsincident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(geweldsincident.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingGeweldsincident() throws Exception {
        // Get the geweldsincident
        restGeweldsincidentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGeweldsincident() throws Exception {
        // Initialize the database
        geweldsincidentRepository.saveAndFlush(geweldsincident);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the geweldsincident
        Geweldsincident updatedGeweldsincident = geweldsincidentRepository.findById(geweldsincident.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGeweldsincident are not directly saved in db
        em.detach(updatedGeweldsincident);
        updatedGeweldsincident.datum(UPDATED_DATUM).omschrijving(UPDATED_OMSCHRIJVING).type(UPDATED_TYPE);

        restGeweldsincidentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGeweldsincident.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGeweldsincident))
            )
            .andExpect(status().isOk());

        // Validate the Geweldsincident in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGeweldsincidentToMatchAllProperties(updatedGeweldsincident);
    }

    @Test
    @Transactional
    void putNonExistingGeweldsincident() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geweldsincident.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeweldsincidentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, geweldsincident.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(geweldsincident))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geweldsincident in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGeweldsincident() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geweldsincident.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeweldsincidentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(geweldsincident))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geweldsincident in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGeweldsincident() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geweldsincident.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeweldsincidentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(geweldsincident)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Geweldsincident in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGeweldsincidentWithPatch() throws Exception {
        // Initialize the database
        geweldsincidentRepository.saveAndFlush(geweldsincident);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the geweldsincident using partial update
        Geweldsincident partialUpdatedGeweldsincident = new Geweldsincident();
        partialUpdatedGeweldsincident.setId(geweldsincident.getId());

        partialUpdatedGeweldsincident.datum(UPDATED_DATUM).omschrijving(UPDATED_OMSCHRIJVING).type(UPDATED_TYPE);

        restGeweldsincidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeweldsincident.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGeweldsincident))
            )
            .andExpect(status().isOk());

        // Validate the Geweldsincident in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGeweldsincidentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGeweldsincident, geweldsincident),
            getPersistedGeweldsincident(geweldsincident)
        );
    }

    @Test
    @Transactional
    void fullUpdateGeweldsincidentWithPatch() throws Exception {
        // Initialize the database
        geweldsincidentRepository.saveAndFlush(geweldsincident);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the geweldsincident using partial update
        Geweldsincident partialUpdatedGeweldsincident = new Geweldsincident();
        partialUpdatedGeweldsincident.setId(geweldsincident.getId());

        partialUpdatedGeweldsincident.datum(UPDATED_DATUM).omschrijving(UPDATED_OMSCHRIJVING).type(UPDATED_TYPE);

        restGeweldsincidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeweldsincident.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGeweldsincident))
            )
            .andExpect(status().isOk());

        // Validate the Geweldsincident in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGeweldsincidentUpdatableFieldsEquals(
            partialUpdatedGeweldsincident,
            getPersistedGeweldsincident(partialUpdatedGeweldsincident)
        );
    }

    @Test
    @Transactional
    void patchNonExistingGeweldsincident() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geweldsincident.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeweldsincidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, geweldsincident.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(geweldsincident))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geweldsincident in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGeweldsincident() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geweldsincident.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeweldsincidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(geweldsincident))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geweldsincident in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGeweldsincident() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geweldsincident.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeweldsincidentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(geweldsincident)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Geweldsincident in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGeweldsincident() throws Exception {
        // Initialize the database
        geweldsincidentRepository.saveAndFlush(geweldsincident);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the geweldsincident
        restGeweldsincidentMockMvc
            .perform(delete(ENTITY_API_URL_ID, geweldsincident.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return geweldsincidentRepository.count();
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

    protected Geweldsincident getPersistedGeweldsincident(Geweldsincident geweldsincident) {
        return geweldsincidentRepository.findById(geweldsincident.getId()).orElseThrow();
    }

    protected void assertPersistedGeweldsincidentToMatchAllProperties(Geweldsincident expectedGeweldsincident) {
        assertGeweldsincidentAllPropertiesEquals(expectedGeweldsincident, getPersistedGeweldsincident(expectedGeweldsincident));
    }

    protected void assertPersistedGeweldsincidentToMatchUpdatableProperties(Geweldsincident expectedGeweldsincident) {
        assertGeweldsincidentAllUpdatablePropertiesEquals(expectedGeweldsincident, getPersistedGeweldsincident(expectedGeweldsincident));
    }
}
