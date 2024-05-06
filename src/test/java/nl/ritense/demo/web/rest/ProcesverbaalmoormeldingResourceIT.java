package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ProcesverbaalmoormeldingAsserts.*;
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
import nl.ritense.demo.domain.Procesverbaalmoormelding;
import nl.ritense.demo.repository.ProcesverbaalmoormeldingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProcesverbaalmoormeldingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProcesverbaalmoormeldingResourceIT {

    private static final String DEFAULT_DATUM = "AAAAAAAAAA";
    private static final String UPDATED_DATUM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GOEDKEURING = false;
    private static final Boolean UPDATED_GOEDKEURING = true;

    private static final String DEFAULT_OPMERKINGEN = "AAAAAAAAAA";
    private static final String UPDATED_OPMERKINGEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/procesverbaalmoormeldings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProcesverbaalmoormeldingRepository procesverbaalmoormeldingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcesverbaalmoormeldingMockMvc;

    private Procesverbaalmoormelding procesverbaalmoormelding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Procesverbaalmoormelding createEntity(EntityManager em) {
        Procesverbaalmoormelding procesverbaalmoormelding = new Procesverbaalmoormelding()
            .datum(DEFAULT_DATUM)
            .goedkeuring(DEFAULT_GOEDKEURING)
            .opmerkingen(DEFAULT_OPMERKINGEN);
        return procesverbaalmoormelding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Procesverbaalmoormelding createUpdatedEntity(EntityManager em) {
        Procesverbaalmoormelding procesverbaalmoormelding = new Procesverbaalmoormelding()
            .datum(UPDATED_DATUM)
            .goedkeuring(UPDATED_GOEDKEURING)
            .opmerkingen(UPDATED_OPMERKINGEN);
        return procesverbaalmoormelding;
    }

    @BeforeEach
    public void initTest() {
        procesverbaalmoormelding = createEntity(em);
    }

    @Test
    @Transactional
    void createProcesverbaalmoormelding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Procesverbaalmoormelding
        var returnedProcesverbaalmoormelding = om.readValue(
            restProcesverbaalmoormeldingMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(procesverbaalmoormelding))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Procesverbaalmoormelding.class
        );

        // Validate the Procesverbaalmoormelding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProcesverbaalmoormeldingUpdatableFieldsEquals(
            returnedProcesverbaalmoormelding,
            getPersistedProcesverbaalmoormelding(returnedProcesverbaalmoormelding)
        );
    }

    @Test
    @Transactional
    void createProcesverbaalmoormeldingWithExistingId() throws Exception {
        // Create the Procesverbaalmoormelding with an existing ID
        procesverbaalmoormelding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcesverbaalmoormeldingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(procesverbaalmoormelding)))
            .andExpect(status().isBadRequest());

        // Validate the Procesverbaalmoormelding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProcesverbaalmoormeldings() throws Exception {
        // Initialize the database
        procesverbaalmoormeldingRepository.saveAndFlush(procesverbaalmoormelding);

        // Get all the procesverbaalmoormeldingList
        restProcesverbaalmoormeldingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procesverbaalmoormelding.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM)))
            .andExpect(jsonPath("$.[*].goedkeuring").value(hasItem(DEFAULT_GOEDKEURING.booleanValue())))
            .andExpect(jsonPath("$.[*].opmerkingen").value(hasItem(DEFAULT_OPMERKINGEN)));
    }

    @Test
    @Transactional
    void getProcesverbaalmoormelding() throws Exception {
        // Initialize the database
        procesverbaalmoormeldingRepository.saveAndFlush(procesverbaalmoormelding);

        // Get the procesverbaalmoormelding
        restProcesverbaalmoormeldingMockMvc
            .perform(get(ENTITY_API_URL_ID, procesverbaalmoormelding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(procesverbaalmoormelding.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM))
            .andExpect(jsonPath("$.goedkeuring").value(DEFAULT_GOEDKEURING.booleanValue()))
            .andExpect(jsonPath("$.opmerkingen").value(DEFAULT_OPMERKINGEN));
    }

    @Test
    @Transactional
    void getNonExistingProcesverbaalmoormelding() throws Exception {
        // Get the procesverbaalmoormelding
        restProcesverbaalmoormeldingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProcesverbaalmoormelding() throws Exception {
        // Initialize the database
        procesverbaalmoormeldingRepository.saveAndFlush(procesverbaalmoormelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the procesverbaalmoormelding
        Procesverbaalmoormelding updatedProcesverbaalmoormelding = procesverbaalmoormeldingRepository
            .findById(procesverbaalmoormelding.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedProcesverbaalmoormelding are not directly saved in db
        em.detach(updatedProcesverbaalmoormelding);
        updatedProcesverbaalmoormelding.datum(UPDATED_DATUM).goedkeuring(UPDATED_GOEDKEURING).opmerkingen(UPDATED_OPMERKINGEN);

        restProcesverbaalmoormeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProcesverbaalmoormelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProcesverbaalmoormelding))
            )
            .andExpect(status().isOk());

        // Validate the Procesverbaalmoormelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProcesverbaalmoormeldingToMatchAllProperties(updatedProcesverbaalmoormelding);
    }

    @Test
    @Transactional
    void putNonExistingProcesverbaalmoormelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        procesverbaalmoormelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcesverbaalmoormeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, procesverbaalmoormelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(procesverbaalmoormelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procesverbaalmoormelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProcesverbaalmoormelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        procesverbaalmoormelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcesverbaalmoormeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(procesverbaalmoormelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procesverbaalmoormelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProcesverbaalmoormelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        procesverbaalmoormelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcesverbaalmoormeldingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(procesverbaalmoormelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Procesverbaalmoormelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProcesverbaalmoormeldingWithPatch() throws Exception {
        // Initialize the database
        procesverbaalmoormeldingRepository.saveAndFlush(procesverbaalmoormelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the procesverbaalmoormelding using partial update
        Procesverbaalmoormelding partialUpdatedProcesverbaalmoormelding = new Procesverbaalmoormelding();
        partialUpdatedProcesverbaalmoormelding.setId(procesverbaalmoormelding.getId());

        partialUpdatedProcesverbaalmoormelding.goedkeuring(UPDATED_GOEDKEURING);

        restProcesverbaalmoormeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcesverbaalmoormelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProcesverbaalmoormelding))
            )
            .andExpect(status().isOk());

        // Validate the Procesverbaalmoormelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProcesverbaalmoormeldingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProcesverbaalmoormelding, procesverbaalmoormelding),
            getPersistedProcesverbaalmoormelding(procesverbaalmoormelding)
        );
    }

    @Test
    @Transactional
    void fullUpdateProcesverbaalmoormeldingWithPatch() throws Exception {
        // Initialize the database
        procesverbaalmoormeldingRepository.saveAndFlush(procesverbaalmoormelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the procesverbaalmoormelding using partial update
        Procesverbaalmoormelding partialUpdatedProcesverbaalmoormelding = new Procesverbaalmoormelding();
        partialUpdatedProcesverbaalmoormelding.setId(procesverbaalmoormelding.getId());

        partialUpdatedProcesverbaalmoormelding.datum(UPDATED_DATUM).goedkeuring(UPDATED_GOEDKEURING).opmerkingen(UPDATED_OPMERKINGEN);

        restProcesverbaalmoormeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcesverbaalmoormelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProcesverbaalmoormelding))
            )
            .andExpect(status().isOk());

        // Validate the Procesverbaalmoormelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProcesverbaalmoormeldingUpdatableFieldsEquals(
            partialUpdatedProcesverbaalmoormelding,
            getPersistedProcesverbaalmoormelding(partialUpdatedProcesverbaalmoormelding)
        );
    }

    @Test
    @Transactional
    void patchNonExistingProcesverbaalmoormelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        procesverbaalmoormelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcesverbaalmoormeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, procesverbaalmoormelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(procesverbaalmoormelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procesverbaalmoormelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProcesverbaalmoormelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        procesverbaalmoormelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcesverbaalmoormeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(procesverbaalmoormelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procesverbaalmoormelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProcesverbaalmoormelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        procesverbaalmoormelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcesverbaalmoormeldingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(procesverbaalmoormelding))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Procesverbaalmoormelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProcesverbaalmoormelding() throws Exception {
        // Initialize the database
        procesverbaalmoormeldingRepository.saveAndFlush(procesverbaalmoormelding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the procesverbaalmoormelding
        restProcesverbaalmoormeldingMockMvc
            .perform(delete(ENTITY_API_URL_ID, procesverbaalmoormelding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return procesverbaalmoormeldingRepository.count();
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

    protected Procesverbaalmoormelding getPersistedProcesverbaalmoormelding(Procesverbaalmoormelding procesverbaalmoormelding) {
        return procesverbaalmoormeldingRepository.findById(procesverbaalmoormelding.getId()).orElseThrow();
    }

    protected void assertPersistedProcesverbaalmoormeldingToMatchAllProperties(Procesverbaalmoormelding expectedProcesverbaalmoormelding) {
        assertProcesverbaalmoormeldingAllPropertiesEquals(
            expectedProcesverbaalmoormelding,
            getPersistedProcesverbaalmoormelding(expectedProcesverbaalmoormelding)
        );
    }

    protected void assertPersistedProcesverbaalmoormeldingToMatchUpdatableProperties(
        Procesverbaalmoormelding expectedProcesverbaalmoormelding
    ) {
        assertProcesverbaalmoormeldingAllUpdatablePropertiesEquals(
            expectedProcesverbaalmoormelding,
            getPersistedProcesverbaalmoormelding(expectedProcesverbaalmoormelding)
        );
    }
}
