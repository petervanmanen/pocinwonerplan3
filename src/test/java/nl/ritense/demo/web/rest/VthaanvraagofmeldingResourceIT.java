package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VthaanvraagofmeldingAsserts.*;
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
import nl.ritense.demo.domain.Vthaanvraagofmelding;
import nl.ritense.demo.repository.VthaanvraagofmeldingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VthaanvraagofmeldingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VthaanvraagofmeldingResourceIT {

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vthaanvraagofmeldings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VthaanvraagofmeldingRepository vthaanvraagofmeldingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVthaanvraagofmeldingMockMvc;

    private Vthaanvraagofmelding vthaanvraagofmelding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vthaanvraagofmelding createEntity(EntityManager em) {
        Vthaanvraagofmelding vthaanvraagofmelding = new Vthaanvraagofmelding().omschrijving(DEFAULT_OMSCHRIJVING);
        return vthaanvraagofmelding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vthaanvraagofmelding createUpdatedEntity(EntityManager em) {
        Vthaanvraagofmelding vthaanvraagofmelding = new Vthaanvraagofmelding().omschrijving(UPDATED_OMSCHRIJVING);
        return vthaanvraagofmelding;
    }

    @BeforeEach
    public void initTest() {
        vthaanvraagofmelding = createEntity(em);
    }

    @Test
    @Transactional
    void createVthaanvraagofmelding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vthaanvraagofmelding
        var returnedVthaanvraagofmelding = om.readValue(
            restVthaanvraagofmeldingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vthaanvraagofmelding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vthaanvraagofmelding.class
        );

        // Validate the Vthaanvraagofmelding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVthaanvraagofmeldingUpdatableFieldsEquals(
            returnedVthaanvraagofmelding,
            getPersistedVthaanvraagofmelding(returnedVthaanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void createVthaanvraagofmeldingWithExistingId() throws Exception {
        // Create the Vthaanvraagofmelding with an existing ID
        vthaanvraagofmelding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVthaanvraagofmeldingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vthaanvraagofmelding)))
            .andExpect(status().isBadRequest());

        // Validate the Vthaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVthaanvraagofmeldings() throws Exception {
        // Initialize the database
        vthaanvraagofmeldingRepository.saveAndFlush(vthaanvraagofmelding);

        // Get all the vthaanvraagofmeldingList
        restVthaanvraagofmeldingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vthaanvraagofmelding.getId().intValue())))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getVthaanvraagofmelding() throws Exception {
        // Initialize the database
        vthaanvraagofmeldingRepository.saveAndFlush(vthaanvraagofmelding);

        // Get the vthaanvraagofmelding
        restVthaanvraagofmeldingMockMvc
            .perform(get(ENTITY_API_URL_ID, vthaanvraagofmelding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vthaanvraagofmelding.getId().intValue()))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingVthaanvraagofmelding() throws Exception {
        // Get the vthaanvraagofmelding
        restVthaanvraagofmeldingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVthaanvraagofmelding() throws Exception {
        // Initialize the database
        vthaanvraagofmeldingRepository.saveAndFlush(vthaanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vthaanvraagofmelding
        Vthaanvraagofmelding updatedVthaanvraagofmelding = vthaanvraagofmeldingRepository
            .findById(vthaanvraagofmelding.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedVthaanvraagofmelding are not directly saved in db
        em.detach(updatedVthaanvraagofmelding);
        updatedVthaanvraagofmelding.omschrijving(UPDATED_OMSCHRIJVING);

        restVthaanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVthaanvraagofmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVthaanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Vthaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVthaanvraagofmeldingToMatchAllProperties(updatedVthaanvraagofmelding);
    }

    @Test
    @Transactional
    void putNonExistingVthaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthaanvraagofmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVthaanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vthaanvraagofmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vthaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vthaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVthaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVthaanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vthaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vthaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVthaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVthaanvraagofmeldingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vthaanvraagofmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vthaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVthaanvraagofmeldingWithPatch() throws Exception {
        // Initialize the database
        vthaanvraagofmeldingRepository.saveAndFlush(vthaanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vthaanvraagofmelding using partial update
        Vthaanvraagofmelding partialUpdatedVthaanvraagofmelding = new Vthaanvraagofmelding();
        partialUpdatedVthaanvraagofmelding.setId(vthaanvraagofmelding.getId());

        partialUpdatedVthaanvraagofmelding.omschrijving(UPDATED_OMSCHRIJVING);

        restVthaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVthaanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVthaanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Vthaanvraagofmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVthaanvraagofmeldingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVthaanvraagofmelding, vthaanvraagofmelding),
            getPersistedVthaanvraagofmelding(vthaanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void fullUpdateVthaanvraagofmeldingWithPatch() throws Exception {
        // Initialize the database
        vthaanvraagofmeldingRepository.saveAndFlush(vthaanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vthaanvraagofmelding using partial update
        Vthaanvraagofmelding partialUpdatedVthaanvraagofmelding = new Vthaanvraagofmelding();
        partialUpdatedVthaanvraagofmelding.setId(vthaanvraagofmelding.getId());

        partialUpdatedVthaanvraagofmelding.omschrijving(UPDATED_OMSCHRIJVING);

        restVthaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVthaanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVthaanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Vthaanvraagofmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVthaanvraagofmeldingUpdatableFieldsEquals(
            partialUpdatedVthaanvraagofmelding,
            getPersistedVthaanvraagofmelding(partialUpdatedVthaanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVthaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthaanvraagofmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVthaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vthaanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vthaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vthaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVthaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVthaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vthaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vthaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVthaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVthaanvraagofmeldingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vthaanvraagofmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vthaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVthaanvraagofmelding() throws Exception {
        // Initialize the database
        vthaanvraagofmeldingRepository.saveAndFlush(vthaanvraagofmelding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vthaanvraagofmelding
        restVthaanvraagofmeldingMockMvc
            .perform(delete(ENTITY_API_URL_ID, vthaanvraagofmelding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vthaanvraagofmeldingRepository.count();
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

    protected Vthaanvraagofmelding getPersistedVthaanvraagofmelding(Vthaanvraagofmelding vthaanvraagofmelding) {
        return vthaanvraagofmeldingRepository.findById(vthaanvraagofmelding.getId()).orElseThrow();
    }

    protected void assertPersistedVthaanvraagofmeldingToMatchAllProperties(Vthaanvraagofmelding expectedVthaanvraagofmelding) {
        assertVthaanvraagofmeldingAllPropertiesEquals(
            expectedVthaanvraagofmelding,
            getPersistedVthaanvraagofmelding(expectedVthaanvraagofmelding)
        );
    }

    protected void assertPersistedVthaanvraagofmeldingToMatchUpdatableProperties(Vthaanvraagofmelding expectedVthaanvraagofmelding) {
        assertVthaanvraagofmeldingAllUpdatablePropertiesEquals(
            expectedVthaanvraagofmelding,
            getPersistedVthaanvraagofmelding(expectedVthaanvraagofmelding)
        );
    }
}
