package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OmgevingswaardeAsserts.*;
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
import nl.ritense.demo.domain.Omgevingswaarde;
import nl.ritense.demo.repository.OmgevingswaardeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OmgevingswaardeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OmgevingswaardeResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMGEVINGSWAARDEGROEP = "AAAAAAAAAA";
    private static final String UPDATED_OMGEVINGSWAARDEGROEP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/omgevingswaardes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OmgevingswaardeRepository omgevingswaardeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOmgevingswaardeMockMvc;

    private Omgevingswaarde omgevingswaarde;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Omgevingswaarde createEntity(EntityManager em) {
        Omgevingswaarde omgevingswaarde = new Omgevingswaarde().naam(DEFAULT_NAAM).omgevingswaardegroep(DEFAULT_OMGEVINGSWAARDEGROEP);
        return omgevingswaarde;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Omgevingswaarde createUpdatedEntity(EntityManager em) {
        Omgevingswaarde omgevingswaarde = new Omgevingswaarde().naam(UPDATED_NAAM).omgevingswaardegroep(UPDATED_OMGEVINGSWAARDEGROEP);
        return omgevingswaarde;
    }

    @BeforeEach
    public void initTest() {
        omgevingswaarde = createEntity(em);
    }

    @Test
    @Transactional
    void createOmgevingswaarde() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Omgevingswaarde
        var returnedOmgevingswaarde = om.readValue(
            restOmgevingswaardeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omgevingswaarde)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Omgevingswaarde.class
        );

        // Validate the Omgevingswaarde in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOmgevingswaardeUpdatableFieldsEquals(returnedOmgevingswaarde, getPersistedOmgevingswaarde(returnedOmgevingswaarde));
    }

    @Test
    @Transactional
    void createOmgevingswaardeWithExistingId() throws Exception {
        // Create the Omgevingswaarde with an existing ID
        omgevingswaarde.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOmgevingswaardeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omgevingswaarde)))
            .andExpect(status().isBadRequest());

        // Validate the Omgevingswaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOmgevingswaardes() throws Exception {
        // Initialize the database
        omgevingswaardeRepository.saveAndFlush(omgevingswaarde);

        // Get all the omgevingswaardeList
        restOmgevingswaardeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(omgevingswaarde.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omgevingswaardegroep").value(hasItem(DEFAULT_OMGEVINGSWAARDEGROEP)));
    }

    @Test
    @Transactional
    void getOmgevingswaarde() throws Exception {
        // Initialize the database
        omgevingswaardeRepository.saveAndFlush(omgevingswaarde);

        // Get the omgevingswaarde
        restOmgevingswaardeMockMvc
            .perform(get(ENTITY_API_URL_ID, omgevingswaarde.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(omgevingswaarde.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omgevingswaardegroep").value(DEFAULT_OMGEVINGSWAARDEGROEP));
    }

    @Test
    @Transactional
    void getNonExistingOmgevingswaarde() throws Exception {
        // Get the omgevingswaarde
        restOmgevingswaardeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOmgevingswaarde() throws Exception {
        // Initialize the database
        omgevingswaardeRepository.saveAndFlush(omgevingswaarde);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the omgevingswaarde
        Omgevingswaarde updatedOmgevingswaarde = omgevingswaardeRepository.findById(omgevingswaarde.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOmgevingswaarde are not directly saved in db
        em.detach(updatedOmgevingswaarde);
        updatedOmgevingswaarde.naam(UPDATED_NAAM).omgevingswaardegroep(UPDATED_OMGEVINGSWAARDEGROEP);

        restOmgevingswaardeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOmgevingswaarde.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOmgevingswaarde))
            )
            .andExpect(status().isOk());

        // Validate the Omgevingswaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOmgevingswaardeToMatchAllProperties(updatedOmgevingswaarde);
    }

    @Test
    @Transactional
    void putNonExistingOmgevingswaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingswaarde.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOmgevingswaardeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, omgevingswaarde.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(omgevingswaarde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omgevingswaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOmgevingswaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingswaarde.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmgevingswaardeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(omgevingswaarde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omgevingswaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOmgevingswaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingswaarde.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmgevingswaardeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omgevingswaarde)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Omgevingswaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOmgevingswaardeWithPatch() throws Exception {
        // Initialize the database
        omgevingswaardeRepository.saveAndFlush(omgevingswaarde);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the omgevingswaarde using partial update
        Omgevingswaarde partialUpdatedOmgevingswaarde = new Omgevingswaarde();
        partialUpdatedOmgevingswaarde.setId(omgevingswaarde.getId());

        partialUpdatedOmgevingswaarde.naam(UPDATED_NAAM).omgevingswaardegroep(UPDATED_OMGEVINGSWAARDEGROEP);

        restOmgevingswaardeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOmgevingswaarde.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOmgevingswaarde))
            )
            .andExpect(status().isOk());

        // Validate the Omgevingswaarde in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOmgevingswaardeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOmgevingswaarde, omgevingswaarde),
            getPersistedOmgevingswaarde(omgevingswaarde)
        );
    }

    @Test
    @Transactional
    void fullUpdateOmgevingswaardeWithPatch() throws Exception {
        // Initialize the database
        omgevingswaardeRepository.saveAndFlush(omgevingswaarde);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the omgevingswaarde using partial update
        Omgevingswaarde partialUpdatedOmgevingswaarde = new Omgevingswaarde();
        partialUpdatedOmgevingswaarde.setId(omgevingswaarde.getId());

        partialUpdatedOmgevingswaarde.naam(UPDATED_NAAM).omgevingswaardegroep(UPDATED_OMGEVINGSWAARDEGROEP);

        restOmgevingswaardeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOmgevingswaarde.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOmgevingswaarde))
            )
            .andExpect(status().isOk());

        // Validate the Omgevingswaarde in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOmgevingswaardeUpdatableFieldsEquals(
            partialUpdatedOmgevingswaarde,
            getPersistedOmgevingswaarde(partialUpdatedOmgevingswaarde)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOmgevingswaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingswaarde.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOmgevingswaardeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, omgevingswaarde.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(omgevingswaarde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omgevingswaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOmgevingswaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingswaarde.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmgevingswaardeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(omgevingswaarde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omgevingswaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOmgevingswaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingswaarde.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmgevingswaardeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(omgevingswaarde)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Omgevingswaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOmgevingswaarde() throws Exception {
        // Initialize the database
        omgevingswaardeRepository.saveAndFlush(omgevingswaarde);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the omgevingswaarde
        restOmgevingswaardeMockMvc
            .perform(delete(ENTITY_API_URL_ID, omgevingswaarde.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return omgevingswaardeRepository.count();
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

    protected Omgevingswaarde getPersistedOmgevingswaarde(Omgevingswaarde omgevingswaarde) {
        return omgevingswaardeRepository.findById(omgevingswaarde.getId()).orElseThrow();
    }

    protected void assertPersistedOmgevingswaardeToMatchAllProperties(Omgevingswaarde expectedOmgevingswaarde) {
        assertOmgevingswaardeAllPropertiesEquals(expectedOmgevingswaarde, getPersistedOmgevingswaarde(expectedOmgevingswaarde));
    }

    protected void assertPersistedOmgevingswaardeToMatchUpdatableProperties(Omgevingswaarde expectedOmgevingswaarde) {
        assertOmgevingswaardeAllUpdatablePropertiesEquals(expectedOmgevingswaarde, getPersistedOmgevingswaarde(expectedOmgevingswaarde));
    }
}
