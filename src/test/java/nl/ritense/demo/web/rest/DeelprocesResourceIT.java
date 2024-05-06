package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DeelprocesAsserts.*;
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
import nl.ritense.demo.domain.Deelproces;
import nl.ritense.demo.repository.DeelprocesRepository;
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
 * Integration tests for the {@link DeelprocesResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class DeelprocesResourceIT {

    private static final LocalDate DEFAULT_DATUMAFGEHANDELD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAFGEHANDELD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMGEPLAND = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMGEPLAND = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/deelproces";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DeelprocesRepository deelprocesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeelprocesMockMvc;

    private Deelproces deelproces;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deelproces createEntity(EntityManager em) {
        Deelproces deelproces = new Deelproces().datumafgehandeld(DEFAULT_DATUMAFGEHANDELD).datumgepland(DEFAULT_DATUMGEPLAND);
        return deelproces;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deelproces createUpdatedEntity(EntityManager em) {
        Deelproces deelproces = new Deelproces().datumafgehandeld(UPDATED_DATUMAFGEHANDELD).datumgepland(UPDATED_DATUMGEPLAND);
        return deelproces;
    }

    @BeforeEach
    public void initTest() {
        deelproces = createEntity(em);
    }

    @Test
    @Transactional
    void createDeelproces() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Deelproces
        var returnedDeelproces = om.readValue(
            restDeelprocesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(deelproces)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Deelproces.class
        );

        // Validate the Deelproces in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDeelprocesUpdatableFieldsEquals(returnedDeelproces, getPersistedDeelproces(returnedDeelproces));
    }

    @Test
    @Transactional
    void createDeelprocesWithExistingId() throws Exception {
        // Create the Deelproces with an existing ID
        deelproces.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeelprocesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(deelproces)))
            .andExpect(status().isBadRequest());

        // Validate the Deelproces in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeelproces() throws Exception {
        // Initialize the database
        deelprocesRepository.saveAndFlush(deelproces);

        // Get all the deelprocesList
        restDeelprocesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deelproces.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumafgehandeld").value(hasItem(DEFAULT_DATUMAFGEHANDELD.toString())))
            .andExpect(jsonPath("$.[*].datumgepland").value(hasItem(DEFAULT_DATUMGEPLAND.toString())));
    }

    @Test
    @Transactional
    void getDeelproces() throws Exception {
        // Initialize the database
        deelprocesRepository.saveAndFlush(deelproces);

        // Get the deelproces
        restDeelprocesMockMvc
            .perform(get(ENTITY_API_URL_ID, deelproces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deelproces.getId().intValue()))
            .andExpect(jsonPath("$.datumafgehandeld").value(DEFAULT_DATUMAFGEHANDELD.toString()))
            .andExpect(jsonPath("$.datumgepland").value(DEFAULT_DATUMGEPLAND.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDeelproces() throws Exception {
        // Get the deelproces
        restDeelprocesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDeelproces() throws Exception {
        // Initialize the database
        deelprocesRepository.saveAndFlush(deelproces);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the deelproces
        Deelproces updatedDeelproces = deelprocesRepository.findById(deelproces.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDeelproces are not directly saved in db
        em.detach(updatedDeelproces);
        updatedDeelproces.datumafgehandeld(UPDATED_DATUMAFGEHANDELD).datumgepland(UPDATED_DATUMGEPLAND);

        restDeelprocesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDeelproces.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDeelproces))
            )
            .andExpect(status().isOk());

        // Validate the Deelproces in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDeelprocesToMatchAllProperties(updatedDeelproces);
    }

    @Test
    @Transactional
    void putNonExistingDeelproces() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deelproces.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeelprocesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deelproces.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(deelproces))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deelproces in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeelproces() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deelproces.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeelprocesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(deelproces))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deelproces in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeelproces() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deelproces.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeelprocesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(deelproces)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deelproces in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeelprocesWithPatch() throws Exception {
        // Initialize the database
        deelprocesRepository.saveAndFlush(deelproces);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the deelproces using partial update
        Deelproces partialUpdatedDeelproces = new Deelproces();
        partialUpdatedDeelproces.setId(deelproces.getId());

        partialUpdatedDeelproces.datumafgehandeld(UPDATED_DATUMAFGEHANDELD).datumgepland(UPDATED_DATUMGEPLAND);

        restDeelprocesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeelproces.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDeelproces))
            )
            .andExpect(status().isOk());

        // Validate the Deelproces in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDeelprocesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDeelproces, deelproces),
            getPersistedDeelproces(deelproces)
        );
    }

    @Test
    @Transactional
    void fullUpdateDeelprocesWithPatch() throws Exception {
        // Initialize the database
        deelprocesRepository.saveAndFlush(deelproces);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the deelproces using partial update
        Deelproces partialUpdatedDeelproces = new Deelproces();
        partialUpdatedDeelproces.setId(deelproces.getId());

        partialUpdatedDeelproces.datumafgehandeld(UPDATED_DATUMAFGEHANDELD).datumgepland(UPDATED_DATUMGEPLAND);

        restDeelprocesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeelproces.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDeelproces))
            )
            .andExpect(status().isOk());

        // Validate the Deelproces in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDeelprocesUpdatableFieldsEquals(partialUpdatedDeelproces, getPersistedDeelproces(partialUpdatedDeelproces));
    }

    @Test
    @Transactional
    void patchNonExistingDeelproces() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deelproces.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeelprocesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deelproces.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(deelproces))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deelproces in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeelproces() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deelproces.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeelprocesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(deelproces))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deelproces in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeelproces() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deelproces.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeelprocesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(deelproces)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deelproces in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeelproces() throws Exception {
        // Initialize the database
        deelprocesRepository.saveAndFlush(deelproces);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the deelproces
        restDeelprocesMockMvc
            .perform(delete(ENTITY_API_URL_ID, deelproces.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return deelprocesRepository.count();
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

    protected Deelproces getPersistedDeelproces(Deelproces deelproces) {
        return deelprocesRepository.findById(deelproces.getId()).orElseThrow();
    }

    protected void assertPersistedDeelprocesToMatchAllProperties(Deelproces expectedDeelproces) {
        assertDeelprocesAllPropertiesEquals(expectedDeelproces, getPersistedDeelproces(expectedDeelproces));
    }

    protected void assertPersistedDeelprocesToMatchUpdatableProperties(Deelproces expectedDeelproces) {
        assertDeelprocesAllUpdatablePropertiesEquals(expectedDeelproces, getPersistedDeelproces(expectedDeelproces));
    }
}
