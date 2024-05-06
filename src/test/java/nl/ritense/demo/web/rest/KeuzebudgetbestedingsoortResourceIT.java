package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KeuzebudgetbestedingsoortAsserts.*;
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
import nl.ritense.demo.domain.Keuzebudgetbestedingsoort;
import nl.ritense.demo.repository.KeuzebudgetbestedingsoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KeuzebudgetbestedingsoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KeuzebudgetbestedingsoortResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/keuzebudgetbestedingsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KeuzebudgetbestedingsoortRepository keuzebudgetbestedingsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKeuzebudgetbestedingsoortMockMvc;

    private Keuzebudgetbestedingsoort keuzebudgetbestedingsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Keuzebudgetbestedingsoort createEntity(EntityManager em) {
        Keuzebudgetbestedingsoort keuzebudgetbestedingsoort = new Keuzebudgetbestedingsoort()
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return keuzebudgetbestedingsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Keuzebudgetbestedingsoort createUpdatedEntity(EntityManager em) {
        Keuzebudgetbestedingsoort keuzebudgetbestedingsoort = new Keuzebudgetbestedingsoort()
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return keuzebudgetbestedingsoort;
    }

    @BeforeEach
    public void initTest() {
        keuzebudgetbestedingsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createKeuzebudgetbestedingsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Keuzebudgetbestedingsoort
        var returnedKeuzebudgetbestedingsoort = om.readValue(
            restKeuzebudgetbestedingsoortMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(keuzebudgetbestedingsoort))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Keuzebudgetbestedingsoort.class
        );

        // Validate the Keuzebudgetbestedingsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKeuzebudgetbestedingsoortUpdatableFieldsEquals(
            returnedKeuzebudgetbestedingsoort,
            getPersistedKeuzebudgetbestedingsoort(returnedKeuzebudgetbestedingsoort)
        );
    }

    @Test
    @Transactional
    void createKeuzebudgetbestedingsoortWithExistingId() throws Exception {
        // Create the Keuzebudgetbestedingsoort with an existing ID
        keuzebudgetbestedingsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKeuzebudgetbestedingsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(keuzebudgetbestedingsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Keuzebudgetbestedingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKeuzebudgetbestedingsoorts() throws Exception {
        // Initialize the database
        keuzebudgetbestedingsoortRepository.saveAndFlush(keuzebudgetbestedingsoort);

        // Get all the keuzebudgetbestedingsoortList
        restKeuzebudgetbestedingsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keuzebudgetbestedingsoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getKeuzebudgetbestedingsoort() throws Exception {
        // Initialize the database
        keuzebudgetbestedingsoortRepository.saveAndFlush(keuzebudgetbestedingsoort);

        // Get the keuzebudgetbestedingsoort
        restKeuzebudgetbestedingsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, keuzebudgetbestedingsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(keuzebudgetbestedingsoort.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingKeuzebudgetbestedingsoort() throws Exception {
        // Get the keuzebudgetbestedingsoort
        restKeuzebudgetbestedingsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKeuzebudgetbestedingsoort() throws Exception {
        // Initialize the database
        keuzebudgetbestedingsoortRepository.saveAndFlush(keuzebudgetbestedingsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the keuzebudgetbestedingsoort
        Keuzebudgetbestedingsoort updatedKeuzebudgetbestedingsoort = keuzebudgetbestedingsoortRepository
            .findById(keuzebudgetbestedingsoort.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedKeuzebudgetbestedingsoort are not directly saved in db
        em.detach(updatedKeuzebudgetbestedingsoort);
        updatedKeuzebudgetbestedingsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restKeuzebudgetbestedingsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKeuzebudgetbestedingsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKeuzebudgetbestedingsoort))
            )
            .andExpect(status().isOk());

        // Validate the Keuzebudgetbestedingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKeuzebudgetbestedingsoortToMatchAllProperties(updatedKeuzebudgetbestedingsoort);
    }

    @Test
    @Transactional
    void putNonExistingKeuzebudgetbestedingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keuzebudgetbestedingsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKeuzebudgetbestedingsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, keuzebudgetbestedingsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(keuzebudgetbestedingsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Keuzebudgetbestedingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKeuzebudgetbestedingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keuzebudgetbestedingsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKeuzebudgetbestedingsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(keuzebudgetbestedingsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Keuzebudgetbestedingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKeuzebudgetbestedingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keuzebudgetbestedingsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKeuzebudgetbestedingsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(keuzebudgetbestedingsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Keuzebudgetbestedingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKeuzebudgetbestedingsoortWithPatch() throws Exception {
        // Initialize the database
        keuzebudgetbestedingsoortRepository.saveAndFlush(keuzebudgetbestedingsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the keuzebudgetbestedingsoort using partial update
        Keuzebudgetbestedingsoort partialUpdatedKeuzebudgetbestedingsoort = new Keuzebudgetbestedingsoort();
        partialUpdatedKeuzebudgetbestedingsoort.setId(keuzebudgetbestedingsoort.getId());

        partialUpdatedKeuzebudgetbestedingsoort.omschrijving(UPDATED_OMSCHRIJVING);

        restKeuzebudgetbestedingsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKeuzebudgetbestedingsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKeuzebudgetbestedingsoort))
            )
            .andExpect(status().isOk());

        // Validate the Keuzebudgetbestedingsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKeuzebudgetbestedingsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKeuzebudgetbestedingsoort, keuzebudgetbestedingsoort),
            getPersistedKeuzebudgetbestedingsoort(keuzebudgetbestedingsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateKeuzebudgetbestedingsoortWithPatch() throws Exception {
        // Initialize the database
        keuzebudgetbestedingsoortRepository.saveAndFlush(keuzebudgetbestedingsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the keuzebudgetbestedingsoort using partial update
        Keuzebudgetbestedingsoort partialUpdatedKeuzebudgetbestedingsoort = new Keuzebudgetbestedingsoort();
        partialUpdatedKeuzebudgetbestedingsoort.setId(keuzebudgetbestedingsoort.getId());

        partialUpdatedKeuzebudgetbestedingsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restKeuzebudgetbestedingsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKeuzebudgetbestedingsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKeuzebudgetbestedingsoort))
            )
            .andExpect(status().isOk());

        // Validate the Keuzebudgetbestedingsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKeuzebudgetbestedingsoortUpdatableFieldsEquals(
            partialUpdatedKeuzebudgetbestedingsoort,
            getPersistedKeuzebudgetbestedingsoort(partialUpdatedKeuzebudgetbestedingsoort)
        );
    }

    @Test
    @Transactional
    void patchNonExistingKeuzebudgetbestedingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keuzebudgetbestedingsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKeuzebudgetbestedingsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, keuzebudgetbestedingsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(keuzebudgetbestedingsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Keuzebudgetbestedingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKeuzebudgetbestedingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keuzebudgetbestedingsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKeuzebudgetbestedingsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(keuzebudgetbestedingsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Keuzebudgetbestedingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKeuzebudgetbestedingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keuzebudgetbestedingsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKeuzebudgetbestedingsoortMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(keuzebudgetbestedingsoort))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Keuzebudgetbestedingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKeuzebudgetbestedingsoort() throws Exception {
        // Initialize the database
        keuzebudgetbestedingsoortRepository.saveAndFlush(keuzebudgetbestedingsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the keuzebudgetbestedingsoort
        restKeuzebudgetbestedingsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, keuzebudgetbestedingsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return keuzebudgetbestedingsoortRepository.count();
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

    protected Keuzebudgetbestedingsoort getPersistedKeuzebudgetbestedingsoort(Keuzebudgetbestedingsoort keuzebudgetbestedingsoort) {
        return keuzebudgetbestedingsoortRepository.findById(keuzebudgetbestedingsoort.getId()).orElseThrow();
    }

    protected void assertPersistedKeuzebudgetbestedingsoortToMatchAllProperties(
        Keuzebudgetbestedingsoort expectedKeuzebudgetbestedingsoort
    ) {
        assertKeuzebudgetbestedingsoortAllPropertiesEquals(
            expectedKeuzebudgetbestedingsoort,
            getPersistedKeuzebudgetbestedingsoort(expectedKeuzebudgetbestedingsoort)
        );
    }

    protected void assertPersistedKeuzebudgetbestedingsoortToMatchUpdatableProperties(
        Keuzebudgetbestedingsoort expectedKeuzebudgetbestedingsoort
    ) {
        assertKeuzebudgetbestedingsoortAllUpdatablePropertiesEquals(
            expectedKeuzebudgetbestedingsoort,
            getPersistedKeuzebudgetbestedingsoort(expectedKeuzebudgetbestedingsoort)
        );
    }
}
