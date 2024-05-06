package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BehandelsoortAsserts.*;
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
import nl.ritense.demo.domain.Behandelsoort;
import nl.ritense.demo.repository.BehandelsoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BehandelsoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BehandelsoortResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/behandelsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BehandelsoortRepository behandelsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBehandelsoortMockMvc;

    private Behandelsoort behandelsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Behandelsoort createEntity(EntityManager em) {
        Behandelsoort behandelsoort = new Behandelsoort().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return behandelsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Behandelsoort createUpdatedEntity(EntityManager em) {
        Behandelsoort behandelsoort = new Behandelsoort().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return behandelsoort;
    }

    @BeforeEach
    public void initTest() {
        behandelsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createBehandelsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Behandelsoort
        var returnedBehandelsoort = om.readValue(
            restBehandelsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(behandelsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Behandelsoort.class
        );

        // Validate the Behandelsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBehandelsoortUpdatableFieldsEquals(returnedBehandelsoort, getPersistedBehandelsoort(returnedBehandelsoort));
    }

    @Test
    @Transactional
    void createBehandelsoortWithExistingId() throws Exception {
        // Create the Behandelsoort with an existing ID
        behandelsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBehandelsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(behandelsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Behandelsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBehandelsoorts() throws Exception {
        // Initialize the database
        behandelsoortRepository.saveAndFlush(behandelsoort);

        // Get all the behandelsoortList
        restBehandelsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(behandelsoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getBehandelsoort() throws Exception {
        // Initialize the database
        behandelsoortRepository.saveAndFlush(behandelsoort);

        // Get the behandelsoort
        restBehandelsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, behandelsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(behandelsoort.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingBehandelsoort() throws Exception {
        // Get the behandelsoort
        restBehandelsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBehandelsoort() throws Exception {
        // Initialize the database
        behandelsoortRepository.saveAndFlush(behandelsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the behandelsoort
        Behandelsoort updatedBehandelsoort = behandelsoortRepository.findById(behandelsoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBehandelsoort are not directly saved in db
        em.detach(updatedBehandelsoort);
        updatedBehandelsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restBehandelsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBehandelsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBehandelsoort))
            )
            .andExpect(status().isOk());

        // Validate the Behandelsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBehandelsoortToMatchAllProperties(updatedBehandelsoort);
    }

    @Test
    @Transactional
    void putNonExistingBehandelsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        behandelsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBehandelsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, behandelsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(behandelsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Behandelsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBehandelsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        behandelsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBehandelsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(behandelsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Behandelsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBehandelsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        behandelsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBehandelsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(behandelsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Behandelsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBehandelsoortWithPatch() throws Exception {
        // Initialize the database
        behandelsoortRepository.saveAndFlush(behandelsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the behandelsoort using partial update
        Behandelsoort partialUpdatedBehandelsoort = new Behandelsoort();
        partialUpdatedBehandelsoort.setId(behandelsoort.getId());

        restBehandelsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBehandelsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBehandelsoort))
            )
            .andExpect(status().isOk());

        // Validate the Behandelsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBehandelsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBehandelsoort, behandelsoort),
            getPersistedBehandelsoort(behandelsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateBehandelsoortWithPatch() throws Exception {
        // Initialize the database
        behandelsoortRepository.saveAndFlush(behandelsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the behandelsoort using partial update
        Behandelsoort partialUpdatedBehandelsoort = new Behandelsoort();
        partialUpdatedBehandelsoort.setId(behandelsoort.getId());

        partialUpdatedBehandelsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restBehandelsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBehandelsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBehandelsoort))
            )
            .andExpect(status().isOk());

        // Validate the Behandelsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBehandelsoortUpdatableFieldsEquals(partialUpdatedBehandelsoort, getPersistedBehandelsoort(partialUpdatedBehandelsoort));
    }

    @Test
    @Transactional
    void patchNonExistingBehandelsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        behandelsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBehandelsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, behandelsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(behandelsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Behandelsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBehandelsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        behandelsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBehandelsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(behandelsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Behandelsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBehandelsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        behandelsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBehandelsoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(behandelsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Behandelsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBehandelsoort() throws Exception {
        // Initialize the database
        behandelsoortRepository.saveAndFlush(behandelsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the behandelsoort
        restBehandelsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, behandelsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return behandelsoortRepository.count();
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

    protected Behandelsoort getPersistedBehandelsoort(Behandelsoort behandelsoort) {
        return behandelsoortRepository.findById(behandelsoort.getId()).orElseThrow();
    }

    protected void assertPersistedBehandelsoortToMatchAllProperties(Behandelsoort expectedBehandelsoort) {
        assertBehandelsoortAllPropertiesEquals(expectedBehandelsoort, getPersistedBehandelsoort(expectedBehandelsoort));
    }

    protected void assertPersistedBehandelsoortToMatchUpdatableProperties(Behandelsoort expectedBehandelsoort) {
        assertBehandelsoortAllUpdatablePropertiesEquals(expectedBehandelsoort, getPersistedBehandelsoort(expectedBehandelsoort));
    }
}
