package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VaartuigAsserts.*;
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
import nl.ritense.demo.domain.Vaartuig;
import nl.ritense.demo.repository.VaartuigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VaartuigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VaartuigResourceIT {

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_HOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_KLEUR = "AAAAAAAAAA";
    private static final String UPDATED_KLEUR = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTE = "AAAAAAAAAA";
    private static final String UPDATED_LENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_NAAMVAARTUIG = "AAAAAAAAAA";
    private static final String UPDATED_NAAMVAARTUIG = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRATIENUMMER = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATIENUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vaartuigs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VaartuigRepository vaartuigRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVaartuigMockMvc;

    private Vaartuig vaartuig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vaartuig createEntity(EntityManager em) {
        Vaartuig vaartuig = new Vaartuig()
            .breedte(DEFAULT_BREEDTE)
            .hoogte(DEFAULT_HOOGTE)
            .kleur(DEFAULT_KLEUR)
            .lengte(DEFAULT_LENGTE)
            .naamvaartuig(DEFAULT_NAAMVAARTUIG)
            .registratienummer(DEFAULT_REGISTRATIENUMMER);
        return vaartuig;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vaartuig createUpdatedEntity(EntityManager em) {
        Vaartuig vaartuig = new Vaartuig()
            .breedte(UPDATED_BREEDTE)
            .hoogte(UPDATED_HOOGTE)
            .kleur(UPDATED_KLEUR)
            .lengte(UPDATED_LENGTE)
            .naamvaartuig(UPDATED_NAAMVAARTUIG)
            .registratienummer(UPDATED_REGISTRATIENUMMER);
        return vaartuig;
    }

    @BeforeEach
    public void initTest() {
        vaartuig = createEntity(em);
    }

    @Test
    @Transactional
    void createVaartuig() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vaartuig
        var returnedVaartuig = om.readValue(
            restVaartuigMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vaartuig)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vaartuig.class
        );

        // Validate the Vaartuig in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVaartuigUpdatableFieldsEquals(returnedVaartuig, getPersistedVaartuig(returnedVaartuig));
    }

    @Test
    @Transactional
    void createVaartuigWithExistingId() throws Exception {
        // Create the Vaartuig with an existing ID
        vaartuig.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVaartuigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vaartuig)))
            .andExpect(status().isBadRequest());

        // Validate the Vaartuig in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVaartuigs() throws Exception {
        // Initialize the database
        vaartuigRepository.saveAndFlush(vaartuig);

        // Get all the vaartuigList
        restVaartuigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vaartuig.getId().intValue())))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].kleur").value(hasItem(DEFAULT_KLEUR)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].naamvaartuig").value(hasItem(DEFAULT_NAAMVAARTUIG)))
            .andExpect(jsonPath("$.[*].registratienummer").value(hasItem(DEFAULT_REGISTRATIENUMMER)));
    }

    @Test
    @Transactional
    void getVaartuig() throws Exception {
        // Initialize the database
        vaartuigRepository.saveAndFlush(vaartuig);

        // Get the vaartuig
        restVaartuigMockMvc
            .perform(get(ENTITY_API_URL_ID, vaartuig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vaartuig.getId().intValue()))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.kleur").value(DEFAULT_KLEUR))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.naamvaartuig").value(DEFAULT_NAAMVAARTUIG))
            .andExpect(jsonPath("$.registratienummer").value(DEFAULT_REGISTRATIENUMMER));
    }

    @Test
    @Transactional
    void getNonExistingVaartuig() throws Exception {
        // Get the vaartuig
        restVaartuigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVaartuig() throws Exception {
        // Initialize the database
        vaartuigRepository.saveAndFlush(vaartuig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vaartuig
        Vaartuig updatedVaartuig = vaartuigRepository.findById(vaartuig.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVaartuig are not directly saved in db
        em.detach(updatedVaartuig);
        updatedVaartuig
            .breedte(UPDATED_BREEDTE)
            .hoogte(UPDATED_HOOGTE)
            .kleur(UPDATED_KLEUR)
            .lengte(UPDATED_LENGTE)
            .naamvaartuig(UPDATED_NAAMVAARTUIG)
            .registratienummer(UPDATED_REGISTRATIENUMMER);

        restVaartuigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVaartuig.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVaartuig))
            )
            .andExpect(status().isOk());

        // Validate the Vaartuig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVaartuigToMatchAllProperties(updatedVaartuig);
    }

    @Test
    @Transactional
    void putNonExistingVaartuig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vaartuig.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVaartuigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vaartuig.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vaartuig))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vaartuig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVaartuig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vaartuig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVaartuigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vaartuig))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vaartuig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVaartuig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vaartuig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVaartuigMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vaartuig)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vaartuig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVaartuigWithPatch() throws Exception {
        // Initialize the database
        vaartuigRepository.saveAndFlush(vaartuig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vaartuig using partial update
        Vaartuig partialUpdatedVaartuig = new Vaartuig();
        partialUpdatedVaartuig.setId(vaartuig.getId());

        partialUpdatedVaartuig.breedte(UPDATED_BREEDTE).registratienummer(UPDATED_REGISTRATIENUMMER);

        restVaartuigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVaartuig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVaartuig))
            )
            .andExpect(status().isOk());

        // Validate the Vaartuig in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVaartuigUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVaartuig, vaartuig), getPersistedVaartuig(vaartuig));
    }

    @Test
    @Transactional
    void fullUpdateVaartuigWithPatch() throws Exception {
        // Initialize the database
        vaartuigRepository.saveAndFlush(vaartuig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vaartuig using partial update
        Vaartuig partialUpdatedVaartuig = new Vaartuig();
        partialUpdatedVaartuig.setId(vaartuig.getId());

        partialUpdatedVaartuig
            .breedte(UPDATED_BREEDTE)
            .hoogte(UPDATED_HOOGTE)
            .kleur(UPDATED_KLEUR)
            .lengte(UPDATED_LENGTE)
            .naamvaartuig(UPDATED_NAAMVAARTUIG)
            .registratienummer(UPDATED_REGISTRATIENUMMER);

        restVaartuigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVaartuig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVaartuig))
            )
            .andExpect(status().isOk());

        // Validate the Vaartuig in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVaartuigUpdatableFieldsEquals(partialUpdatedVaartuig, getPersistedVaartuig(partialUpdatedVaartuig));
    }

    @Test
    @Transactional
    void patchNonExistingVaartuig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vaartuig.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVaartuigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vaartuig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vaartuig))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vaartuig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVaartuig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vaartuig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVaartuigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vaartuig))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vaartuig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVaartuig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vaartuig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVaartuigMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vaartuig)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vaartuig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVaartuig() throws Exception {
        // Initialize the database
        vaartuigRepository.saveAndFlush(vaartuig);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vaartuig
        restVaartuigMockMvc
            .perform(delete(ENTITY_API_URL_ID, vaartuig.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vaartuigRepository.count();
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

    protected Vaartuig getPersistedVaartuig(Vaartuig vaartuig) {
        return vaartuigRepository.findById(vaartuig.getId()).orElseThrow();
    }

    protected void assertPersistedVaartuigToMatchAllProperties(Vaartuig expectedVaartuig) {
        assertVaartuigAllPropertiesEquals(expectedVaartuig, getPersistedVaartuig(expectedVaartuig));
    }

    protected void assertPersistedVaartuigToMatchUpdatableProperties(Vaartuig expectedVaartuig) {
        assertVaartuigAllUpdatablePropertiesEquals(expectedVaartuig, getPersistedVaartuig(expectedVaartuig));
    }
}
