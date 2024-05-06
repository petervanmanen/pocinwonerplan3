package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerzuimsoortAsserts.*;
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
import nl.ritense.demo.domain.Verzuimsoort;
import nl.ritense.demo.repository.VerzuimsoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerzuimsoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerzuimsoortResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verzuimsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerzuimsoortRepository verzuimsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerzuimsoortMockMvc;

    private Verzuimsoort verzuimsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verzuimsoort createEntity(EntityManager em) {
        Verzuimsoort verzuimsoort = new Verzuimsoort().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return verzuimsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verzuimsoort createUpdatedEntity(EntityManager em) {
        Verzuimsoort verzuimsoort = new Verzuimsoort().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return verzuimsoort;
    }

    @BeforeEach
    public void initTest() {
        verzuimsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createVerzuimsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verzuimsoort
        var returnedVerzuimsoort = om.readValue(
            restVerzuimsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verzuimsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verzuimsoort.class
        );

        // Validate the Verzuimsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerzuimsoortUpdatableFieldsEquals(returnedVerzuimsoort, getPersistedVerzuimsoort(returnedVerzuimsoort));
    }

    @Test
    @Transactional
    void createVerzuimsoortWithExistingId() throws Exception {
        // Create the Verzuimsoort with an existing ID
        verzuimsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerzuimsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verzuimsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Verzuimsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerzuimsoorts() throws Exception {
        // Initialize the database
        verzuimsoortRepository.saveAndFlush(verzuimsoort);

        // Get all the verzuimsoortList
        restVerzuimsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verzuimsoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getVerzuimsoort() throws Exception {
        // Initialize the database
        verzuimsoortRepository.saveAndFlush(verzuimsoort);

        // Get the verzuimsoort
        restVerzuimsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, verzuimsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verzuimsoort.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingVerzuimsoort() throws Exception {
        // Get the verzuimsoort
        restVerzuimsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerzuimsoort() throws Exception {
        // Initialize the database
        verzuimsoortRepository.saveAndFlush(verzuimsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verzuimsoort
        Verzuimsoort updatedVerzuimsoort = verzuimsoortRepository.findById(verzuimsoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerzuimsoort are not directly saved in db
        em.detach(updatedVerzuimsoort);
        updatedVerzuimsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restVerzuimsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerzuimsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerzuimsoort))
            )
            .andExpect(status().isOk());

        // Validate the Verzuimsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerzuimsoortToMatchAllProperties(updatedVerzuimsoort);
    }

    @Test
    @Transactional
    void putNonExistingVerzuimsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuimsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerzuimsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verzuimsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verzuimsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzuimsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerzuimsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuimsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzuimsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verzuimsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzuimsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerzuimsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuimsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzuimsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verzuimsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verzuimsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerzuimsoortWithPatch() throws Exception {
        // Initialize the database
        verzuimsoortRepository.saveAndFlush(verzuimsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verzuimsoort using partial update
        Verzuimsoort partialUpdatedVerzuimsoort = new Verzuimsoort();
        partialUpdatedVerzuimsoort.setId(verzuimsoort.getId());

        partialUpdatedVerzuimsoort.omschrijving(UPDATED_OMSCHRIJVING);

        restVerzuimsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerzuimsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerzuimsoort))
            )
            .andExpect(status().isOk());

        // Validate the Verzuimsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerzuimsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerzuimsoort, verzuimsoort),
            getPersistedVerzuimsoort(verzuimsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerzuimsoortWithPatch() throws Exception {
        // Initialize the database
        verzuimsoortRepository.saveAndFlush(verzuimsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verzuimsoort using partial update
        Verzuimsoort partialUpdatedVerzuimsoort = new Verzuimsoort();
        partialUpdatedVerzuimsoort.setId(verzuimsoort.getId());

        partialUpdatedVerzuimsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restVerzuimsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerzuimsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerzuimsoort))
            )
            .andExpect(status().isOk());

        // Validate the Verzuimsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerzuimsoortUpdatableFieldsEquals(partialUpdatedVerzuimsoort, getPersistedVerzuimsoort(partialUpdatedVerzuimsoort));
    }

    @Test
    @Transactional
    void patchNonExistingVerzuimsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuimsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerzuimsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verzuimsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verzuimsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzuimsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerzuimsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuimsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzuimsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verzuimsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzuimsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerzuimsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuimsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzuimsoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verzuimsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verzuimsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerzuimsoort() throws Exception {
        // Initialize the database
        verzuimsoortRepository.saveAndFlush(verzuimsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verzuimsoort
        restVerzuimsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, verzuimsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verzuimsoortRepository.count();
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

    protected Verzuimsoort getPersistedVerzuimsoort(Verzuimsoort verzuimsoort) {
        return verzuimsoortRepository.findById(verzuimsoort.getId()).orElseThrow();
    }

    protected void assertPersistedVerzuimsoortToMatchAllProperties(Verzuimsoort expectedVerzuimsoort) {
        assertVerzuimsoortAllPropertiesEquals(expectedVerzuimsoort, getPersistedVerzuimsoort(expectedVerzuimsoort));
    }

    protected void assertPersistedVerzuimsoortToMatchUpdatableProperties(Verzuimsoort expectedVerzuimsoort) {
        assertVerzuimsoortAllUpdatablePropertiesEquals(expectedVerzuimsoort, getPersistedVerzuimsoort(expectedVerzuimsoort));
    }
}
