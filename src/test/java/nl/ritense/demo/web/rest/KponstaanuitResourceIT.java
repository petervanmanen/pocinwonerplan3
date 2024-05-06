package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KponstaanuitAsserts.*;
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
import nl.ritense.demo.domain.Kponstaanuit;
import nl.ritense.demo.repository.KponstaanuitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KponstaanuitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KponstaanuitResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/kponstaanuits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KponstaanuitRepository kponstaanuitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKponstaanuitMockMvc;

    private Kponstaanuit kponstaanuit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kponstaanuit createEntity(EntityManager em) {
        Kponstaanuit kponstaanuit = new Kponstaanuit()
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID);
        return kponstaanuit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kponstaanuit createUpdatedEntity(EntityManager em) {
        Kponstaanuit kponstaanuit = new Kponstaanuit()
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID);
        return kponstaanuit;
    }

    @BeforeEach
    public void initTest() {
        kponstaanuit = createEntity(em);
    }

    @Test
    @Transactional
    void createKponstaanuit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kponstaanuit
        var returnedKponstaanuit = om.readValue(
            restKponstaanuitMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kponstaanuit)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kponstaanuit.class
        );

        // Validate the Kponstaanuit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKponstaanuitUpdatableFieldsEquals(returnedKponstaanuit, getPersistedKponstaanuit(returnedKponstaanuit));
    }

    @Test
    @Transactional
    void createKponstaanuitWithExistingId() throws Exception {
        // Create the Kponstaanuit with an existing ID
        kponstaanuit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKponstaanuitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kponstaanuit)))
            .andExpect(status().isBadRequest());

        // Validate the Kponstaanuit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKponstaanuits() throws Exception {
        // Initialize the database
        kponstaanuitRepository.saveAndFlush(kponstaanuit);

        // Get all the kponstaanuitList
        restKponstaanuitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kponstaanuit.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())));
    }

    @Test
    @Transactional
    void getKponstaanuit() throws Exception {
        // Initialize the database
        kponstaanuitRepository.saveAndFlush(kponstaanuit);

        // Get the kponstaanuit
        restKponstaanuitMockMvc
            .perform(get(ENTITY_API_URL_ID, kponstaanuit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kponstaanuit.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()));
    }

    @Test
    @Transactional
    void getNonExistingKponstaanuit() throws Exception {
        // Get the kponstaanuit
        restKponstaanuitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKponstaanuit() throws Exception {
        // Initialize the database
        kponstaanuitRepository.saveAndFlush(kponstaanuit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kponstaanuit
        Kponstaanuit updatedKponstaanuit = kponstaanuitRepository.findById(kponstaanuit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKponstaanuit are not directly saved in db
        em.detach(updatedKponstaanuit);
        updatedKponstaanuit.datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID).datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID);

        restKponstaanuitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKponstaanuit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKponstaanuit))
            )
            .andExpect(status().isOk());

        // Validate the Kponstaanuit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKponstaanuitToMatchAllProperties(updatedKponstaanuit);
    }

    @Test
    @Transactional
    void putNonExistingKponstaanuit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kponstaanuit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKponstaanuitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kponstaanuit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kponstaanuit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kponstaanuit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKponstaanuit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kponstaanuit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKponstaanuitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kponstaanuit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kponstaanuit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKponstaanuit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kponstaanuit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKponstaanuitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kponstaanuit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kponstaanuit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKponstaanuitWithPatch() throws Exception {
        // Initialize the database
        kponstaanuitRepository.saveAndFlush(kponstaanuit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kponstaanuit using partial update
        Kponstaanuit partialUpdatedKponstaanuit = new Kponstaanuit();
        partialUpdatedKponstaanuit.setId(kponstaanuit.getId());

        partialUpdatedKponstaanuit.datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID).datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID);

        restKponstaanuitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKponstaanuit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKponstaanuit))
            )
            .andExpect(status().isOk());

        // Validate the Kponstaanuit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKponstaanuitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKponstaanuit, kponstaanuit),
            getPersistedKponstaanuit(kponstaanuit)
        );
    }

    @Test
    @Transactional
    void fullUpdateKponstaanuitWithPatch() throws Exception {
        // Initialize the database
        kponstaanuitRepository.saveAndFlush(kponstaanuit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kponstaanuit using partial update
        Kponstaanuit partialUpdatedKponstaanuit = new Kponstaanuit();
        partialUpdatedKponstaanuit.setId(kponstaanuit.getId());

        partialUpdatedKponstaanuit.datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID).datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID);

        restKponstaanuitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKponstaanuit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKponstaanuit))
            )
            .andExpect(status().isOk());

        // Validate the Kponstaanuit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKponstaanuitUpdatableFieldsEquals(partialUpdatedKponstaanuit, getPersistedKponstaanuit(partialUpdatedKponstaanuit));
    }

    @Test
    @Transactional
    void patchNonExistingKponstaanuit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kponstaanuit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKponstaanuitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kponstaanuit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kponstaanuit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kponstaanuit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKponstaanuit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kponstaanuit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKponstaanuitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kponstaanuit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kponstaanuit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKponstaanuit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kponstaanuit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKponstaanuitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kponstaanuit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kponstaanuit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKponstaanuit() throws Exception {
        // Initialize the database
        kponstaanuitRepository.saveAndFlush(kponstaanuit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kponstaanuit
        restKponstaanuitMockMvc
            .perform(delete(ENTITY_API_URL_ID, kponstaanuit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kponstaanuitRepository.count();
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

    protected Kponstaanuit getPersistedKponstaanuit(Kponstaanuit kponstaanuit) {
        return kponstaanuitRepository.findById(kponstaanuit.getId()).orElseThrow();
    }

    protected void assertPersistedKponstaanuitToMatchAllProperties(Kponstaanuit expectedKponstaanuit) {
        assertKponstaanuitAllPropertiesEquals(expectedKponstaanuit, getPersistedKponstaanuit(expectedKponstaanuit));
    }

    protected void assertPersistedKponstaanuitToMatchUpdatableProperties(Kponstaanuit expectedKponstaanuit) {
        assertKponstaanuitAllUpdatablePropertiesEquals(expectedKponstaanuit, getPersistedKponstaanuit(expectedKponstaanuit));
    }
}
