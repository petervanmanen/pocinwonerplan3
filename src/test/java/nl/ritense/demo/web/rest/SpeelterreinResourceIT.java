package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SpeelterreinAsserts.*;
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
import nl.ritense.demo.domain.Speelterrein;
import nl.ritense.demo.repository.SpeelterreinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SpeelterreinResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpeelterreinResourceIT {

    private static final String DEFAULT_JAARHERINRICHTING = "AAAAAAAAAA";
    private static final String UPDATED_JAARHERINRICHTING = "BBBBBBBBBB";

    private static final String DEFAULT_SPEELTERREINLEEFTIJDDOELGROEP = "AAAAAAAAAA";
    private static final String UPDATED_SPEELTERREINLEEFTIJDDOELGROEP = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/speelterreins";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SpeelterreinRepository speelterreinRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpeelterreinMockMvc;

    private Speelterrein speelterrein;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Speelterrein createEntity(EntityManager em) {
        Speelterrein speelterrein = new Speelterrein()
            .jaarherinrichting(DEFAULT_JAARHERINRICHTING)
            .speelterreinleeftijddoelgroep(DEFAULT_SPEELTERREINLEEFTIJDDOELGROEP)
            .type(DEFAULT_TYPE)
            .typeplus(DEFAULT_TYPEPLUS);
        return speelterrein;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Speelterrein createUpdatedEntity(EntityManager em) {
        Speelterrein speelterrein = new Speelterrein()
            .jaarherinrichting(UPDATED_JAARHERINRICHTING)
            .speelterreinleeftijddoelgroep(UPDATED_SPEELTERREINLEEFTIJDDOELGROEP)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS);
        return speelterrein;
    }

    @BeforeEach
    public void initTest() {
        speelterrein = createEntity(em);
    }

    @Test
    @Transactional
    void createSpeelterrein() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Speelterrein
        var returnedSpeelterrein = om.readValue(
            restSpeelterreinMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(speelterrein)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Speelterrein.class
        );

        // Validate the Speelterrein in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSpeelterreinUpdatableFieldsEquals(returnedSpeelterrein, getPersistedSpeelterrein(returnedSpeelterrein));
    }

    @Test
    @Transactional
    void createSpeelterreinWithExistingId() throws Exception {
        // Create the Speelterrein with an existing ID
        speelterrein.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpeelterreinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(speelterrein)))
            .andExpect(status().isBadRequest());

        // Validate the Speelterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSpeelterreins() throws Exception {
        // Initialize the database
        speelterreinRepository.saveAndFlush(speelterrein);

        // Get all the speelterreinList
        restSpeelterreinMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speelterrein.getId().intValue())))
            .andExpect(jsonPath("$.[*].jaarherinrichting").value(hasItem(DEFAULT_JAARHERINRICHTING)))
            .andExpect(jsonPath("$.[*].speelterreinleeftijddoelgroep").value(hasItem(DEFAULT_SPEELTERREINLEEFTIJDDOELGROEP)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].typeplus").value(hasItem(DEFAULT_TYPEPLUS)));
    }

    @Test
    @Transactional
    void getSpeelterrein() throws Exception {
        // Initialize the database
        speelterreinRepository.saveAndFlush(speelterrein);

        // Get the speelterrein
        restSpeelterreinMockMvc
            .perform(get(ENTITY_API_URL_ID, speelterrein.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(speelterrein.getId().intValue()))
            .andExpect(jsonPath("$.jaarherinrichting").value(DEFAULT_JAARHERINRICHTING))
            .andExpect(jsonPath("$.speelterreinleeftijddoelgroep").value(DEFAULT_SPEELTERREINLEEFTIJDDOELGROEP))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.typeplus").value(DEFAULT_TYPEPLUS));
    }

    @Test
    @Transactional
    void getNonExistingSpeelterrein() throws Exception {
        // Get the speelterrein
        restSpeelterreinMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSpeelterrein() throws Exception {
        // Initialize the database
        speelterreinRepository.saveAndFlush(speelterrein);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the speelterrein
        Speelterrein updatedSpeelterrein = speelterreinRepository.findById(speelterrein.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSpeelterrein are not directly saved in db
        em.detach(updatedSpeelterrein);
        updatedSpeelterrein
            .jaarherinrichting(UPDATED_JAARHERINRICHTING)
            .speelterreinleeftijddoelgroep(UPDATED_SPEELTERREINLEEFTIJDDOELGROEP)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS);

        restSpeelterreinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSpeelterrein.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSpeelterrein))
            )
            .andExpect(status().isOk());

        // Validate the Speelterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSpeelterreinToMatchAllProperties(updatedSpeelterrein);
    }

    @Test
    @Transactional
    void putNonExistingSpeelterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        speelterrein.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpeelterreinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, speelterrein.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(speelterrein))
            )
            .andExpect(status().isBadRequest());

        // Validate the Speelterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpeelterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        speelterrein.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeelterreinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(speelterrein))
            )
            .andExpect(status().isBadRequest());

        // Validate the Speelterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpeelterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        speelterrein.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeelterreinMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(speelterrein)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Speelterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpeelterreinWithPatch() throws Exception {
        // Initialize the database
        speelterreinRepository.saveAndFlush(speelterrein);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the speelterrein using partial update
        Speelterrein partialUpdatedSpeelterrein = new Speelterrein();
        partialUpdatedSpeelterrein.setId(speelterrein.getId());

        partialUpdatedSpeelterrein
            .jaarherinrichting(UPDATED_JAARHERINRICHTING)
            .speelterreinleeftijddoelgroep(UPDATED_SPEELTERREINLEEFTIJDDOELGROEP)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS);

        restSpeelterreinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpeelterrein.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSpeelterrein))
            )
            .andExpect(status().isOk());

        // Validate the Speelterrein in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSpeelterreinUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSpeelterrein, speelterrein),
            getPersistedSpeelterrein(speelterrein)
        );
    }

    @Test
    @Transactional
    void fullUpdateSpeelterreinWithPatch() throws Exception {
        // Initialize the database
        speelterreinRepository.saveAndFlush(speelterrein);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the speelterrein using partial update
        Speelterrein partialUpdatedSpeelterrein = new Speelterrein();
        partialUpdatedSpeelterrein.setId(speelterrein.getId());

        partialUpdatedSpeelterrein
            .jaarherinrichting(UPDATED_JAARHERINRICHTING)
            .speelterreinleeftijddoelgroep(UPDATED_SPEELTERREINLEEFTIJDDOELGROEP)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS);

        restSpeelterreinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpeelterrein.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSpeelterrein))
            )
            .andExpect(status().isOk());

        // Validate the Speelterrein in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSpeelterreinUpdatableFieldsEquals(partialUpdatedSpeelterrein, getPersistedSpeelterrein(partialUpdatedSpeelterrein));
    }

    @Test
    @Transactional
    void patchNonExistingSpeelterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        speelterrein.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpeelterreinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, speelterrein.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(speelterrein))
            )
            .andExpect(status().isBadRequest());

        // Validate the Speelterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpeelterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        speelterrein.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeelterreinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(speelterrein))
            )
            .andExpect(status().isBadRequest());

        // Validate the Speelterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpeelterrein() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        speelterrein.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeelterreinMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(speelterrein)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Speelterrein in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpeelterrein() throws Exception {
        // Initialize the database
        speelterreinRepository.saveAndFlush(speelterrein);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the speelterrein
        restSpeelterreinMockMvc
            .perform(delete(ENTITY_API_URL_ID, speelterrein.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return speelterreinRepository.count();
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

    protected Speelterrein getPersistedSpeelterrein(Speelterrein speelterrein) {
        return speelterreinRepository.findById(speelterrein.getId()).orElseThrow();
    }

    protected void assertPersistedSpeelterreinToMatchAllProperties(Speelterrein expectedSpeelterrein) {
        assertSpeelterreinAllPropertiesEquals(expectedSpeelterrein, getPersistedSpeelterrein(expectedSpeelterrein));
    }

    protected void assertPersistedSpeelterreinToMatchUpdatableProperties(Speelterrein expectedSpeelterrein) {
        assertSpeelterreinAllUpdatablePropertiesEquals(expectedSpeelterrein, getPersistedSpeelterrein(expectedSpeelterrein));
    }
}
