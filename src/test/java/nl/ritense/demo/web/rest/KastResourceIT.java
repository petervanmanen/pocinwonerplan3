package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KastAsserts.*;
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
import nl.ritense.demo.domain.Kast;
import nl.ritense.demo.repository.KastRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KastResourceIT {

    private static final String DEFAULT_KASTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_KASTNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kasts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KastRepository kastRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKastMockMvc;

    private Kast kast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kast createEntity(EntityManager em) {
        Kast kast = new Kast().kastnummer(DEFAULT_KASTNUMMER);
        return kast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kast createUpdatedEntity(EntityManager em) {
        Kast kast = new Kast().kastnummer(UPDATED_KASTNUMMER);
        return kast;
    }

    @BeforeEach
    public void initTest() {
        kast = createEntity(em);
    }

    @Test
    @Transactional
    void createKast() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kast
        var returnedKast = om.readValue(
            restKastMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kast)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kast.class
        );

        // Validate the Kast in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKastUpdatableFieldsEquals(returnedKast, getPersistedKast(returnedKast));
    }

    @Test
    @Transactional
    void createKastWithExistingId() throws Exception {
        // Create the Kast with an existing ID
        kast.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKastMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kast)))
            .andExpect(status().isBadRequest());

        // Validate the Kast in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKasts() throws Exception {
        // Initialize the database
        kastRepository.saveAndFlush(kast);

        // Get all the kastList
        restKastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kast.getId().intValue())))
            .andExpect(jsonPath("$.[*].kastnummer").value(hasItem(DEFAULT_KASTNUMMER)));
    }

    @Test
    @Transactional
    void getKast() throws Exception {
        // Initialize the database
        kastRepository.saveAndFlush(kast);

        // Get the kast
        restKastMockMvc
            .perform(get(ENTITY_API_URL_ID, kast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kast.getId().intValue()))
            .andExpect(jsonPath("$.kastnummer").value(DEFAULT_KASTNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingKast() throws Exception {
        // Get the kast
        restKastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKast() throws Exception {
        // Initialize the database
        kastRepository.saveAndFlush(kast);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kast
        Kast updatedKast = kastRepository.findById(kast.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKast are not directly saved in db
        em.detach(updatedKast);
        updatedKast.kastnummer(UPDATED_KASTNUMMER);

        restKastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKast.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKast))
            )
            .andExpect(status().isOk());

        // Validate the Kast in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKastToMatchAllProperties(updatedKast);
    }

    @Test
    @Transactional
    void putNonExistingKast() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kast.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKastMockMvc
            .perform(put(ENTITY_API_URL_ID, kast.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kast)))
            .andExpect(status().isBadRequest());

        // Validate the Kast in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKast() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kast.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kast))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kast in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKast() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kast.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKastMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kast)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kast in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKastWithPatch() throws Exception {
        // Initialize the database
        kastRepository.saveAndFlush(kast);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kast using partial update
        Kast partialUpdatedKast = new Kast();
        partialUpdatedKast.setId(kast.getId());

        partialUpdatedKast.kastnummer(UPDATED_KASTNUMMER);

        restKastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKast.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKast))
            )
            .andExpect(status().isOk());

        // Validate the Kast in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKastUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedKast, kast), getPersistedKast(kast));
    }

    @Test
    @Transactional
    void fullUpdateKastWithPatch() throws Exception {
        // Initialize the database
        kastRepository.saveAndFlush(kast);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kast using partial update
        Kast partialUpdatedKast = new Kast();
        partialUpdatedKast.setId(kast.getId());

        partialUpdatedKast.kastnummer(UPDATED_KASTNUMMER);

        restKastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKast.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKast))
            )
            .andExpect(status().isOk());

        // Validate the Kast in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKastUpdatableFieldsEquals(partialUpdatedKast, getPersistedKast(partialUpdatedKast));
    }

    @Test
    @Transactional
    void patchNonExistingKast() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kast.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKastMockMvc
            .perform(patch(ENTITY_API_URL_ID, kast.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kast)))
            .andExpect(status().isBadRequest());

        // Validate the Kast in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKast() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kast.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kast))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kast in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKast() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kast.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKastMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kast)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kast in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKast() throws Exception {
        // Initialize the database
        kastRepository.saveAndFlush(kast);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kast
        restKastMockMvc
            .perform(delete(ENTITY_API_URL_ID, kast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kastRepository.count();
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

    protected Kast getPersistedKast(Kast kast) {
        return kastRepository.findById(kast.getId()).orElseThrow();
    }

    protected void assertPersistedKastToMatchAllProperties(Kast expectedKast) {
        assertKastAllPropertiesEquals(expectedKast, getPersistedKast(expectedKast));
    }

    protected void assertPersistedKastToMatchUpdatableProperties(Kast expectedKast) {
        assertKastAllUpdatablePropertiesEquals(expectedKast, getPersistedKast(expectedKast));
    }
}
