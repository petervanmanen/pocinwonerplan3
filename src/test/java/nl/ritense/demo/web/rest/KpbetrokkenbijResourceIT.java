package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KpbetrokkenbijAsserts.*;
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
import nl.ritense.demo.domain.Kpbetrokkenbij;
import nl.ritense.demo.repository.KpbetrokkenbijRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KpbetrokkenbijResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KpbetrokkenbijResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/kpbetrokkenbijs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KpbetrokkenbijRepository kpbetrokkenbijRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKpbetrokkenbijMockMvc;

    private Kpbetrokkenbij kpbetrokkenbij;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kpbetrokkenbij createEntity(EntityManager em) {
        Kpbetrokkenbij kpbetrokkenbij = new Kpbetrokkenbij()
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID);
        return kpbetrokkenbij;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kpbetrokkenbij createUpdatedEntity(EntityManager em) {
        Kpbetrokkenbij kpbetrokkenbij = new Kpbetrokkenbij()
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID);
        return kpbetrokkenbij;
    }

    @BeforeEach
    public void initTest() {
        kpbetrokkenbij = createEntity(em);
    }

    @Test
    @Transactional
    void createKpbetrokkenbij() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kpbetrokkenbij
        var returnedKpbetrokkenbij = om.readValue(
            restKpbetrokkenbijMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kpbetrokkenbij)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kpbetrokkenbij.class
        );

        // Validate the Kpbetrokkenbij in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKpbetrokkenbijUpdatableFieldsEquals(returnedKpbetrokkenbij, getPersistedKpbetrokkenbij(returnedKpbetrokkenbij));
    }

    @Test
    @Transactional
    void createKpbetrokkenbijWithExistingId() throws Exception {
        // Create the Kpbetrokkenbij with an existing ID
        kpbetrokkenbij.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKpbetrokkenbijMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kpbetrokkenbij)))
            .andExpect(status().isBadRequest());

        // Validate the Kpbetrokkenbij in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKpbetrokkenbijs() throws Exception {
        // Initialize the database
        kpbetrokkenbijRepository.saveAndFlush(kpbetrokkenbij);

        // Get all the kpbetrokkenbijList
        restKpbetrokkenbijMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kpbetrokkenbij.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())));
    }

    @Test
    @Transactional
    void getKpbetrokkenbij() throws Exception {
        // Initialize the database
        kpbetrokkenbijRepository.saveAndFlush(kpbetrokkenbij);

        // Get the kpbetrokkenbij
        restKpbetrokkenbijMockMvc
            .perform(get(ENTITY_API_URL_ID, kpbetrokkenbij.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kpbetrokkenbij.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()));
    }

    @Test
    @Transactional
    void getNonExistingKpbetrokkenbij() throws Exception {
        // Get the kpbetrokkenbij
        restKpbetrokkenbijMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKpbetrokkenbij() throws Exception {
        // Initialize the database
        kpbetrokkenbijRepository.saveAndFlush(kpbetrokkenbij);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kpbetrokkenbij
        Kpbetrokkenbij updatedKpbetrokkenbij = kpbetrokkenbijRepository.findById(kpbetrokkenbij.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKpbetrokkenbij are not directly saved in db
        em.detach(updatedKpbetrokkenbij);
        updatedKpbetrokkenbij.datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID).datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID);

        restKpbetrokkenbijMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKpbetrokkenbij.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKpbetrokkenbij))
            )
            .andExpect(status().isOk());

        // Validate the Kpbetrokkenbij in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKpbetrokkenbijToMatchAllProperties(updatedKpbetrokkenbij);
    }

    @Test
    @Transactional
    void putNonExistingKpbetrokkenbij() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kpbetrokkenbij.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKpbetrokkenbijMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kpbetrokkenbij.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kpbetrokkenbij))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kpbetrokkenbij in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKpbetrokkenbij() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kpbetrokkenbij.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpbetrokkenbijMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kpbetrokkenbij))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kpbetrokkenbij in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKpbetrokkenbij() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kpbetrokkenbij.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpbetrokkenbijMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kpbetrokkenbij)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kpbetrokkenbij in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKpbetrokkenbijWithPatch() throws Exception {
        // Initialize the database
        kpbetrokkenbijRepository.saveAndFlush(kpbetrokkenbij);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kpbetrokkenbij using partial update
        Kpbetrokkenbij partialUpdatedKpbetrokkenbij = new Kpbetrokkenbij();
        partialUpdatedKpbetrokkenbij.setId(kpbetrokkenbij.getId());

        partialUpdatedKpbetrokkenbij.datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID).datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID);

        restKpbetrokkenbijMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKpbetrokkenbij.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKpbetrokkenbij))
            )
            .andExpect(status().isOk());

        // Validate the Kpbetrokkenbij in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKpbetrokkenbijUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKpbetrokkenbij, kpbetrokkenbij),
            getPersistedKpbetrokkenbij(kpbetrokkenbij)
        );
    }

    @Test
    @Transactional
    void fullUpdateKpbetrokkenbijWithPatch() throws Exception {
        // Initialize the database
        kpbetrokkenbijRepository.saveAndFlush(kpbetrokkenbij);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kpbetrokkenbij using partial update
        Kpbetrokkenbij partialUpdatedKpbetrokkenbij = new Kpbetrokkenbij();
        partialUpdatedKpbetrokkenbij.setId(kpbetrokkenbij.getId());

        partialUpdatedKpbetrokkenbij.datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID).datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID);

        restKpbetrokkenbijMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKpbetrokkenbij.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKpbetrokkenbij))
            )
            .andExpect(status().isOk());

        // Validate the Kpbetrokkenbij in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKpbetrokkenbijUpdatableFieldsEquals(partialUpdatedKpbetrokkenbij, getPersistedKpbetrokkenbij(partialUpdatedKpbetrokkenbij));
    }

    @Test
    @Transactional
    void patchNonExistingKpbetrokkenbij() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kpbetrokkenbij.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKpbetrokkenbijMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kpbetrokkenbij.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kpbetrokkenbij))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kpbetrokkenbij in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKpbetrokkenbij() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kpbetrokkenbij.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpbetrokkenbijMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kpbetrokkenbij))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kpbetrokkenbij in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKpbetrokkenbij() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kpbetrokkenbij.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpbetrokkenbijMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kpbetrokkenbij)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kpbetrokkenbij in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKpbetrokkenbij() throws Exception {
        // Initialize the database
        kpbetrokkenbijRepository.saveAndFlush(kpbetrokkenbij);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kpbetrokkenbij
        restKpbetrokkenbijMockMvc
            .perform(delete(ENTITY_API_URL_ID, kpbetrokkenbij.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kpbetrokkenbijRepository.count();
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

    protected Kpbetrokkenbij getPersistedKpbetrokkenbij(Kpbetrokkenbij kpbetrokkenbij) {
        return kpbetrokkenbijRepository.findById(kpbetrokkenbij.getId()).orElseThrow();
    }

    protected void assertPersistedKpbetrokkenbijToMatchAllProperties(Kpbetrokkenbij expectedKpbetrokkenbij) {
        assertKpbetrokkenbijAllPropertiesEquals(expectedKpbetrokkenbij, getPersistedKpbetrokkenbij(expectedKpbetrokkenbij));
    }

    protected void assertPersistedKpbetrokkenbijToMatchUpdatableProperties(Kpbetrokkenbij expectedKpbetrokkenbij) {
        assertKpbetrokkenbijAllUpdatablePropertiesEquals(expectedKpbetrokkenbij, getPersistedKpbetrokkenbij(expectedKpbetrokkenbij));
    }
}
