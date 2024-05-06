package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KwalificatieAsserts.*;
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
import nl.ritense.demo.domain.Kwalificatie;
import nl.ritense.demo.repository.KwalificatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KwalificatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KwalificatieResourceIT {

    private static final LocalDate DEFAULT_EINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_STARTGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/kwalificaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KwalificatieRepository kwalificatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKwalificatieMockMvc;

    private Kwalificatie kwalificatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kwalificatie createEntity(EntityManager em) {
        Kwalificatie kwalificatie = new Kwalificatie().eindegeldigheid(DEFAULT_EINDEGELDIGHEID).startgeldigheid(DEFAULT_STARTGELDIGHEID);
        return kwalificatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kwalificatie createUpdatedEntity(EntityManager em) {
        Kwalificatie kwalificatie = new Kwalificatie().eindegeldigheid(UPDATED_EINDEGELDIGHEID).startgeldigheid(UPDATED_STARTGELDIGHEID);
        return kwalificatie;
    }

    @BeforeEach
    public void initTest() {
        kwalificatie = createEntity(em);
    }

    @Test
    @Transactional
    void createKwalificatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kwalificatie
        var returnedKwalificatie = om.readValue(
            restKwalificatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kwalificatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kwalificatie.class
        );

        // Validate the Kwalificatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKwalificatieUpdatableFieldsEquals(returnedKwalificatie, getPersistedKwalificatie(returnedKwalificatie));
    }

    @Test
    @Transactional
    void createKwalificatieWithExistingId() throws Exception {
        // Create the Kwalificatie with an existing ID
        kwalificatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKwalificatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kwalificatie)))
            .andExpect(status().isBadRequest());

        // Validate the Kwalificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKwalificaties() throws Exception {
        // Initialize the database
        kwalificatieRepository.saveAndFlush(kwalificatie);

        // Get all the kwalificatieList
        restKwalificatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kwalificatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].eindegeldigheid").value(hasItem(DEFAULT_EINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].startgeldigheid").value(hasItem(DEFAULT_STARTGELDIGHEID.toString())));
    }

    @Test
    @Transactional
    void getKwalificatie() throws Exception {
        // Initialize the database
        kwalificatieRepository.saveAndFlush(kwalificatie);

        // Get the kwalificatie
        restKwalificatieMockMvc
            .perform(get(ENTITY_API_URL_ID, kwalificatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kwalificatie.getId().intValue()))
            .andExpect(jsonPath("$.eindegeldigheid").value(DEFAULT_EINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.startgeldigheid").value(DEFAULT_STARTGELDIGHEID.toString()));
    }

    @Test
    @Transactional
    void getNonExistingKwalificatie() throws Exception {
        // Get the kwalificatie
        restKwalificatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKwalificatie() throws Exception {
        // Initialize the database
        kwalificatieRepository.saveAndFlush(kwalificatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kwalificatie
        Kwalificatie updatedKwalificatie = kwalificatieRepository.findById(kwalificatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKwalificatie are not directly saved in db
        em.detach(updatedKwalificatie);
        updatedKwalificatie.eindegeldigheid(UPDATED_EINDEGELDIGHEID).startgeldigheid(UPDATED_STARTGELDIGHEID);

        restKwalificatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKwalificatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKwalificatie))
            )
            .andExpect(status().isOk());

        // Validate the Kwalificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKwalificatieToMatchAllProperties(updatedKwalificatie);
    }

    @Test
    @Transactional
    void putNonExistingKwalificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kwalificatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKwalificatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kwalificatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kwalificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kwalificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKwalificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kwalificatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKwalificatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kwalificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kwalificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKwalificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kwalificatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKwalificatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kwalificatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kwalificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKwalificatieWithPatch() throws Exception {
        // Initialize the database
        kwalificatieRepository.saveAndFlush(kwalificatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kwalificatie using partial update
        Kwalificatie partialUpdatedKwalificatie = new Kwalificatie();
        partialUpdatedKwalificatie.setId(kwalificatie.getId());

        partialUpdatedKwalificatie.eindegeldigheid(UPDATED_EINDEGELDIGHEID);

        restKwalificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKwalificatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKwalificatie))
            )
            .andExpect(status().isOk());

        // Validate the Kwalificatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKwalificatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKwalificatie, kwalificatie),
            getPersistedKwalificatie(kwalificatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateKwalificatieWithPatch() throws Exception {
        // Initialize the database
        kwalificatieRepository.saveAndFlush(kwalificatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kwalificatie using partial update
        Kwalificatie partialUpdatedKwalificatie = new Kwalificatie();
        partialUpdatedKwalificatie.setId(kwalificatie.getId());

        partialUpdatedKwalificatie.eindegeldigheid(UPDATED_EINDEGELDIGHEID).startgeldigheid(UPDATED_STARTGELDIGHEID);

        restKwalificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKwalificatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKwalificatie))
            )
            .andExpect(status().isOk());

        // Validate the Kwalificatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKwalificatieUpdatableFieldsEquals(partialUpdatedKwalificatie, getPersistedKwalificatie(partialUpdatedKwalificatie));
    }

    @Test
    @Transactional
    void patchNonExistingKwalificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kwalificatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKwalificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kwalificatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kwalificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kwalificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKwalificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kwalificatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKwalificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kwalificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kwalificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKwalificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kwalificatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKwalificatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kwalificatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kwalificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKwalificatie() throws Exception {
        // Initialize the database
        kwalificatieRepository.saveAndFlush(kwalificatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kwalificatie
        restKwalificatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, kwalificatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kwalificatieRepository.count();
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

    protected Kwalificatie getPersistedKwalificatie(Kwalificatie kwalificatie) {
        return kwalificatieRepository.findById(kwalificatie.getId()).orElseThrow();
    }

    protected void assertPersistedKwalificatieToMatchAllProperties(Kwalificatie expectedKwalificatie) {
        assertKwalificatieAllPropertiesEquals(expectedKwalificatie, getPersistedKwalificatie(expectedKwalificatie));
    }

    protected void assertPersistedKwalificatieToMatchUpdatableProperties(Kwalificatie expectedKwalificatie) {
        assertKwalificatieAllUpdatablePropertiesEquals(expectedKwalificatie, getPersistedKwalificatie(expectedKwalificatie));
    }
}
