package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VoertuigAsserts.*;
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
import nl.ritense.demo.domain.Voertuig;
import nl.ritense.demo.repository.VoertuigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VoertuigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VoertuigResourceIT {

    private static final String DEFAULT_KENTEKEN = "AAAAAAAAAA";
    private static final String UPDATED_KENTEKEN = "BBBBBBBBBB";

    private static final String DEFAULT_KLEUR = "AAAAAAAAAA";
    private static final String UPDATED_KLEUR = "BBBBBBBBBB";

    private static final String DEFAULT_LAND = "AAAAAAAAAA";
    private static final String UPDATED_LAND = "BBBBBBBBBB";

    private static final String DEFAULT_MERK = "AAAAAAAAAA";
    private static final String UPDATED_MERK = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/voertuigs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VoertuigRepository voertuigRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoertuigMockMvc;

    private Voertuig voertuig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voertuig createEntity(EntityManager em) {
        Voertuig voertuig = new Voertuig()
            .kenteken(DEFAULT_KENTEKEN)
            .kleur(DEFAULT_KLEUR)
            .land(DEFAULT_LAND)
            .merk(DEFAULT_MERK)
            .type(DEFAULT_TYPE);
        return voertuig;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voertuig createUpdatedEntity(EntityManager em) {
        Voertuig voertuig = new Voertuig()
            .kenteken(UPDATED_KENTEKEN)
            .kleur(UPDATED_KLEUR)
            .land(UPDATED_LAND)
            .merk(UPDATED_MERK)
            .type(UPDATED_TYPE);
        return voertuig;
    }

    @BeforeEach
    public void initTest() {
        voertuig = createEntity(em);
    }

    @Test
    @Transactional
    void createVoertuig() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Voertuig
        var returnedVoertuig = om.readValue(
            restVoertuigMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voertuig)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Voertuig.class
        );

        // Validate the Voertuig in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVoertuigUpdatableFieldsEquals(returnedVoertuig, getPersistedVoertuig(returnedVoertuig));
    }

    @Test
    @Transactional
    void createVoertuigWithExistingId() throws Exception {
        // Create the Voertuig with an existing ID
        voertuig.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoertuigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voertuig)))
            .andExpect(status().isBadRequest());

        // Validate the Voertuig in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVoertuigs() throws Exception {
        // Initialize the database
        voertuigRepository.saveAndFlush(voertuig);

        // Get all the voertuigList
        restVoertuigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voertuig.getId().intValue())))
            .andExpect(jsonPath("$.[*].kenteken").value(hasItem(DEFAULT_KENTEKEN)))
            .andExpect(jsonPath("$.[*].kleur").value(hasItem(DEFAULT_KLEUR)))
            .andExpect(jsonPath("$.[*].land").value(hasItem(DEFAULT_LAND)))
            .andExpect(jsonPath("$.[*].merk").value(hasItem(DEFAULT_MERK)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getVoertuig() throws Exception {
        // Initialize the database
        voertuigRepository.saveAndFlush(voertuig);

        // Get the voertuig
        restVoertuigMockMvc
            .perform(get(ENTITY_API_URL_ID, voertuig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voertuig.getId().intValue()))
            .andExpect(jsonPath("$.kenteken").value(DEFAULT_KENTEKEN))
            .andExpect(jsonPath("$.kleur").value(DEFAULT_KLEUR))
            .andExpect(jsonPath("$.land").value(DEFAULT_LAND))
            .andExpect(jsonPath("$.merk").value(DEFAULT_MERK))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingVoertuig() throws Exception {
        // Get the voertuig
        restVoertuigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVoertuig() throws Exception {
        // Initialize the database
        voertuigRepository.saveAndFlush(voertuig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voertuig
        Voertuig updatedVoertuig = voertuigRepository.findById(voertuig.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVoertuig are not directly saved in db
        em.detach(updatedVoertuig);
        updatedVoertuig.kenteken(UPDATED_KENTEKEN).kleur(UPDATED_KLEUR).land(UPDATED_LAND).merk(UPDATED_MERK).type(UPDATED_TYPE);

        restVoertuigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVoertuig.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVoertuig))
            )
            .andExpect(status().isOk());

        // Validate the Voertuig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVoertuigToMatchAllProperties(updatedVoertuig);
    }

    @Test
    @Transactional
    void putNonExistingVoertuig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voertuig.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoertuigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voertuig.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voertuig))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voertuig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoertuig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voertuig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoertuigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(voertuig))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voertuig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoertuig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voertuig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoertuigMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voertuig)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voertuig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoertuigWithPatch() throws Exception {
        // Initialize the database
        voertuigRepository.saveAndFlush(voertuig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voertuig using partial update
        Voertuig partialUpdatedVoertuig = new Voertuig();
        partialUpdatedVoertuig.setId(voertuig.getId());

        partialUpdatedVoertuig.kenteken(UPDATED_KENTEKEN).land(UPDATED_LAND).merk(UPDATED_MERK).type(UPDATED_TYPE);

        restVoertuigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoertuig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVoertuig))
            )
            .andExpect(status().isOk());

        // Validate the Voertuig in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVoertuigUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVoertuig, voertuig), getPersistedVoertuig(voertuig));
    }

    @Test
    @Transactional
    void fullUpdateVoertuigWithPatch() throws Exception {
        // Initialize the database
        voertuigRepository.saveAndFlush(voertuig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voertuig using partial update
        Voertuig partialUpdatedVoertuig = new Voertuig();
        partialUpdatedVoertuig.setId(voertuig.getId());

        partialUpdatedVoertuig.kenteken(UPDATED_KENTEKEN).kleur(UPDATED_KLEUR).land(UPDATED_LAND).merk(UPDATED_MERK).type(UPDATED_TYPE);

        restVoertuigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoertuig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVoertuig))
            )
            .andExpect(status().isOk());

        // Validate the Voertuig in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVoertuigUpdatableFieldsEquals(partialUpdatedVoertuig, getPersistedVoertuig(partialUpdatedVoertuig));
    }

    @Test
    @Transactional
    void patchNonExistingVoertuig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voertuig.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoertuigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voertuig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(voertuig))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voertuig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoertuig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voertuig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoertuigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(voertuig))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voertuig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoertuig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voertuig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoertuigMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(voertuig)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voertuig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoertuig() throws Exception {
        // Initialize the database
        voertuigRepository.saveAndFlush(voertuig);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the voertuig
        restVoertuigMockMvc
            .perform(delete(ENTITY_API_URL_ID, voertuig.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return voertuigRepository.count();
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

    protected Voertuig getPersistedVoertuig(Voertuig voertuig) {
        return voertuigRepository.findById(voertuig.getId()).orElseThrow();
    }

    protected void assertPersistedVoertuigToMatchAllProperties(Voertuig expectedVoertuig) {
        assertVoertuigAllPropertiesEquals(expectedVoertuig, getPersistedVoertuig(expectedVoertuig));
    }

    protected void assertPersistedVoertuigToMatchUpdatableProperties(Voertuig expectedVoertuig) {
        assertVoertuigAllUpdatablePropertiesEquals(expectedVoertuig, getPersistedVoertuig(expectedVoertuig));
    }
}
