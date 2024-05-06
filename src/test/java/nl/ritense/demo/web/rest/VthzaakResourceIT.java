package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VthzaakAsserts.*;
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
import nl.ritense.demo.domain.Vthzaak;
import nl.ritense.demo.repository.VthzaakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VthzaakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VthzaakResourceIT {

    private static final String DEFAULT_BEHANDELAAR = "AAAAAAAAAA";
    private static final String UPDATED_BEHANDELAAR = "BBBBBBBBBB";

    private static final String DEFAULT_BEVOEGDGEZAG = "AAAAAAAAAA";
    private static final String UPDATED_BEVOEGDGEZAG = "BBBBBBBBBB";

    private static final String DEFAULT_PRIORITERING = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITERING = "BBBBBBBBBB";

    private static final String DEFAULT_TEAMBEHANDELAAR = "AAAAAAAAAA";
    private static final String UPDATED_TEAMBEHANDELAAR = "BBBBBBBBBB";

    private static final String DEFAULT_UITVOERENDEINSTANTIE = "AAAAAAAAAA";
    private static final String UPDATED_UITVOERENDEINSTANTIE = "BBBBBBBBBB";

    private static final String DEFAULT_VERKAMERING = "AAAAAAAAAA";
    private static final String UPDATED_VERKAMERING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vthzaaks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VthzaakRepository vthzaakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVthzaakMockMvc;

    private Vthzaak vthzaak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vthzaak createEntity(EntityManager em) {
        Vthzaak vthzaak = new Vthzaak()
            .behandelaar(DEFAULT_BEHANDELAAR)
            .bevoegdgezag(DEFAULT_BEVOEGDGEZAG)
            .prioritering(DEFAULT_PRIORITERING)
            .teambehandelaar(DEFAULT_TEAMBEHANDELAAR)
            .uitvoerendeinstantie(DEFAULT_UITVOERENDEINSTANTIE)
            .verkamering(DEFAULT_VERKAMERING);
        return vthzaak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vthzaak createUpdatedEntity(EntityManager em) {
        Vthzaak vthzaak = new Vthzaak()
            .behandelaar(UPDATED_BEHANDELAAR)
            .bevoegdgezag(UPDATED_BEVOEGDGEZAG)
            .prioritering(UPDATED_PRIORITERING)
            .teambehandelaar(UPDATED_TEAMBEHANDELAAR)
            .uitvoerendeinstantie(UPDATED_UITVOERENDEINSTANTIE)
            .verkamering(UPDATED_VERKAMERING);
        return vthzaak;
    }

    @BeforeEach
    public void initTest() {
        vthzaak = createEntity(em);
    }

    @Test
    @Transactional
    void createVthzaak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vthzaak
        var returnedVthzaak = om.readValue(
            restVthzaakMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vthzaak)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vthzaak.class
        );

        // Validate the Vthzaak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVthzaakUpdatableFieldsEquals(returnedVthzaak, getPersistedVthzaak(returnedVthzaak));
    }

    @Test
    @Transactional
    void createVthzaakWithExistingId() throws Exception {
        // Create the Vthzaak with an existing ID
        vthzaak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVthzaakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vthzaak)))
            .andExpect(status().isBadRequest());

        // Validate the Vthzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVthzaaks() throws Exception {
        // Initialize the database
        vthzaakRepository.saveAndFlush(vthzaak);

        // Get all the vthzaakList
        restVthzaakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vthzaak.getId().intValue())))
            .andExpect(jsonPath("$.[*].behandelaar").value(hasItem(DEFAULT_BEHANDELAAR)))
            .andExpect(jsonPath("$.[*].bevoegdgezag").value(hasItem(DEFAULT_BEVOEGDGEZAG)))
            .andExpect(jsonPath("$.[*].prioritering").value(hasItem(DEFAULT_PRIORITERING)))
            .andExpect(jsonPath("$.[*].teambehandelaar").value(hasItem(DEFAULT_TEAMBEHANDELAAR)))
            .andExpect(jsonPath("$.[*].uitvoerendeinstantie").value(hasItem(DEFAULT_UITVOERENDEINSTANTIE)))
            .andExpect(jsonPath("$.[*].verkamering").value(hasItem(DEFAULT_VERKAMERING)));
    }

    @Test
    @Transactional
    void getVthzaak() throws Exception {
        // Initialize the database
        vthzaakRepository.saveAndFlush(vthzaak);

        // Get the vthzaak
        restVthzaakMockMvc
            .perform(get(ENTITY_API_URL_ID, vthzaak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vthzaak.getId().intValue()))
            .andExpect(jsonPath("$.behandelaar").value(DEFAULT_BEHANDELAAR))
            .andExpect(jsonPath("$.bevoegdgezag").value(DEFAULT_BEVOEGDGEZAG))
            .andExpect(jsonPath("$.prioritering").value(DEFAULT_PRIORITERING))
            .andExpect(jsonPath("$.teambehandelaar").value(DEFAULT_TEAMBEHANDELAAR))
            .andExpect(jsonPath("$.uitvoerendeinstantie").value(DEFAULT_UITVOERENDEINSTANTIE))
            .andExpect(jsonPath("$.verkamering").value(DEFAULT_VERKAMERING));
    }

    @Test
    @Transactional
    void getNonExistingVthzaak() throws Exception {
        // Get the vthzaak
        restVthzaakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVthzaak() throws Exception {
        // Initialize the database
        vthzaakRepository.saveAndFlush(vthzaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vthzaak
        Vthzaak updatedVthzaak = vthzaakRepository.findById(vthzaak.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVthzaak are not directly saved in db
        em.detach(updatedVthzaak);
        updatedVthzaak
            .behandelaar(UPDATED_BEHANDELAAR)
            .bevoegdgezag(UPDATED_BEVOEGDGEZAG)
            .prioritering(UPDATED_PRIORITERING)
            .teambehandelaar(UPDATED_TEAMBEHANDELAAR)
            .uitvoerendeinstantie(UPDATED_UITVOERENDEINSTANTIE)
            .verkamering(UPDATED_VERKAMERING);

        restVthzaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVthzaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVthzaak))
            )
            .andExpect(status().isOk());

        // Validate the Vthzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVthzaakToMatchAllProperties(updatedVthzaak);
    }

    @Test
    @Transactional
    void putNonExistingVthzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthzaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVthzaakMockMvc
            .perform(put(ENTITY_API_URL_ID, vthzaak.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vthzaak)))
            .andExpect(status().isBadRequest());

        // Validate the Vthzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVthzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVthzaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vthzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vthzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVthzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVthzaakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vthzaak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vthzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVthzaakWithPatch() throws Exception {
        // Initialize the database
        vthzaakRepository.saveAndFlush(vthzaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vthzaak using partial update
        Vthzaak partialUpdatedVthzaak = new Vthzaak();
        partialUpdatedVthzaak.setId(vthzaak.getId());

        partialUpdatedVthzaak
            .bevoegdgezag(UPDATED_BEVOEGDGEZAG)
            .prioritering(UPDATED_PRIORITERING)
            .uitvoerendeinstantie(UPDATED_UITVOERENDEINSTANTIE)
            .verkamering(UPDATED_VERKAMERING);

        restVthzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVthzaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVthzaak))
            )
            .andExpect(status().isOk());

        // Validate the Vthzaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVthzaakUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVthzaak, vthzaak), getPersistedVthzaak(vthzaak));
    }

    @Test
    @Transactional
    void fullUpdateVthzaakWithPatch() throws Exception {
        // Initialize the database
        vthzaakRepository.saveAndFlush(vthzaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vthzaak using partial update
        Vthzaak partialUpdatedVthzaak = new Vthzaak();
        partialUpdatedVthzaak.setId(vthzaak.getId());

        partialUpdatedVthzaak
            .behandelaar(UPDATED_BEHANDELAAR)
            .bevoegdgezag(UPDATED_BEVOEGDGEZAG)
            .prioritering(UPDATED_PRIORITERING)
            .teambehandelaar(UPDATED_TEAMBEHANDELAAR)
            .uitvoerendeinstantie(UPDATED_UITVOERENDEINSTANTIE)
            .verkamering(UPDATED_VERKAMERING);

        restVthzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVthzaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVthzaak))
            )
            .andExpect(status().isOk());

        // Validate the Vthzaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVthzaakUpdatableFieldsEquals(partialUpdatedVthzaak, getPersistedVthzaak(partialUpdatedVthzaak));
    }

    @Test
    @Transactional
    void patchNonExistingVthzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthzaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVthzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vthzaak.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vthzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vthzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVthzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVthzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vthzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vthzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVthzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVthzaakMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vthzaak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vthzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVthzaak() throws Exception {
        // Initialize the database
        vthzaakRepository.saveAndFlush(vthzaak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vthzaak
        restVthzaakMockMvc
            .perform(delete(ENTITY_API_URL_ID, vthzaak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vthzaakRepository.count();
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

    protected Vthzaak getPersistedVthzaak(Vthzaak vthzaak) {
        return vthzaakRepository.findById(vthzaak.getId()).orElseThrow();
    }

    protected void assertPersistedVthzaakToMatchAllProperties(Vthzaak expectedVthzaak) {
        assertVthzaakAllPropertiesEquals(expectedVthzaak, getPersistedVthzaak(expectedVthzaak));
    }

    protected void assertPersistedVthzaakToMatchUpdatableProperties(Vthzaak expectedVthzaak) {
        assertVthzaakAllUpdatablePropertiesEquals(expectedVthzaak, getPersistedVthzaak(expectedVthzaak));
    }
}
