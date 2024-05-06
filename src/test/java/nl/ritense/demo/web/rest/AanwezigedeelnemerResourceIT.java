package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AanwezigedeelnemerAsserts.*;
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
import nl.ritense.demo.domain.Aanwezigedeelnemer;
import nl.ritense.demo.repository.AanwezigedeelnemerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AanwezigedeelnemerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AanwezigedeelnemerResourceIT {

    private static final String DEFAULT_AANVANGAANWEZIGHEID = "AAAAAAAAAA";
    private static final String UPDATED_AANVANGAANWEZIGHEID = "BBBBBBBBBB";

    private static final String DEFAULT_EINDEAANWEZIGHEID = "AAAAAAAAAA";
    private static final String UPDATED_EINDEAANWEZIGHEID = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_ROL = "AAAAAAAAAA";
    private static final String UPDATED_ROL = "BBBBBBBBBB";

    private static final String DEFAULT_VERTEGENWOORDIGTORGANISATIE = "AAAAAAAAAA";
    private static final String UPDATED_VERTEGENWOORDIGTORGANISATIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aanwezigedeelnemers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AanwezigedeelnemerRepository aanwezigedeelnemerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAanwezigedeelnemerMockMvc;

    private Aanwezigedeelnemer aanwezigedeelnemer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanwezigedeelnemer createEntity(EntityManager em) {
        Aanwezigedeelnemer aanwezigedeelnemer = new Aanwezigedeelnemer()
            .aanvangaanwezigheid(DEFAULT_AANVANGAANWEZIGHEID)
            .eindeaanwezigheid(DEFAULT_EINDEAANWEZIGHEID)
            .naam(DEFAULT_NAAM)
            .rol(DEFAULT_ROL)
            .vertegenwoordigtorganisatie(DEFAULT_VERTEGENWOORDIGTORGANISATIE);
        return aanwezigedeelnemer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanwezigedeelnemer createUpdatedEntity(EntityManager em) {
        Aanwezigedeelnemer aanwezigedeelnemer = new Aanwezigedeelnemer()
            .aanvangaanwezigheid(UPDATED_AANVANGAANWEZIGHEID)
            .eindeaanwezigheid(UPDATED_EINDEAANWEZIGHEID)
            .naam(UPDATED_NAAM)
            .rol(UPDATED_ROL)
            .vertegenwoordigtorganisatie(UPDATED_VERTEGENWOORDIGTORGANISATIE);
        return aanwezigedeelnemer;
    }

    @BeforeEach
    public void initTest() {
        aanwezigedeelnemer = createEntity(em);
    }

    @Test
    @Transactional
    void createAanwezigedeelnemer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aanwezigedeelnemer
        var returnedAanwezigedeelnemer = om.readValue(
            restAanwezigedeelnemerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanwezigedeelnemer)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aanwezigedeelnemer.class
        );

        // Validate the Aanwezigedeelnemer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAanwezigedeelnemerUpdatableFieldsEquals(
            returnedAanwezigedeelnemer,
            getPersistedAanwezigedeelnemer(returnedAanwezigedeelnemer)
        );
    }

    @Test
    @Transactional
    void createAanwezigedeelnemerWithExistingId() throws Exception {
        // Create the Aanwezigedeelnemer with an existing ID
        aanwezigedeelnemer.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAanwezigedeelnemerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanwezigedeelnemer)))
            .andExpect(status().isBadRequest());

        // Validate the Aanwezigedeelnemer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAanwezigedeelnemers() throws Exception {
        // Initialize the database
        aanwezigedeelnemerRepository.saveAndFlush(aanwezigedeelnemer);

        // Get all the aanwezigedeelnemerList
        restAanwezigedeelnemerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aanwezigedeelnemer.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanvangaanwezigheid").value(hasItem(DEFAULT_AANVANGAANWEZIGHEID)))
            .andExpect(jsonPath("$.[*].eindeaanwezigheid").value(hasItem(DEFAULT_EINDEAANWEZIGHEID)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].rol").value(hasItem(DEFAULT_ROL)))
            .andExpect(jsonPath("$.[*].vertegenwoordigtorganisatie").value(hasItem(DEFAULT_VERTEGENWOORDIGTORGANISATIE)));
    }

    @Test
    @Transactional
    void getAanwezigedeelnemer() throws Exception {
        // Initialize the database
        aanwezigedeelnemerRepository.saveAndFlush(aanwezigedeelnemer);

        // Get the aanwezigedeelnemer
        restAanwezigedeelnemerMockMvc
            .perform(get(ENTITY_API_URL_ID, aanwezigedeelnemer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aanwezigedeelnemer.getId().intValue()))
            .andExpect(jsonPath("$.aanvangaanwezigheid").value(DEFAULT_AANVANGAANWEZIGHEID))
            .andExpect(jsonPath("$.eindeaanwezigheid").value(DEFAULT_EINDEAANWEZIGHEID))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.rol").value(DEFAULT_ROL))
            .andExpect(jsonPath("$.vertegenwoordigtorganisatie").value(DEFAULT_VERTEGENWOORDIGTORGANISATIE));
    }

    @Test
    @Transactional
    void getNonExistingAanwezigedeelnemer() throws Exception {
        // Get the aanwezigedeelnemer
        restAanwezigedeelnemerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAanwezigedeelnemer() throws Exception {
        // Initialize the database
        aanwezigedeelnemerRepository.saveAndFlush(aanwezigedeelnemer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanwezigedeelnemer
        Aanwezigedeelnemer updatedAanwezigedeelnemer = aanwezigedeelnemerRepository.findById(aanwezigedeelnemer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAanwezigedeelnemer are not directly saved in db
        em.detach(updatedAanwezigedeelnemer);
        updatedAanwezigedeelnemer
            .aanvangaanwezigheid(UPDATED_AANVANGAANWEZIGHEID)
            .eindeaanwezigheid(UPDATED_EINDEAANWEZIGHEID)
            .naam(UPDATED_NAAM)
            .rol(UPDATED_ROL)
            .vertegenwoordigtorganisatie(UPDATED_VERTEGENWOORDIGTORGANISATIE);

        restAanwezigedeelnemerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAanwezigedeelnemer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAanwezigedeelnemer))
            )
            .andExpect(status().isOk());

        // Validate the Aanwezigedeelnemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAanwezigedeelnemerToMatchAllProperties(updatedAanwezigedeelnemer);
    }

    @Test
    @Transactional
    void putNonExistingAanwezigedeelnemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanwezigedeelnemer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanwezigedeelnemerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aanwezigedeelnemer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aanwezigedeelnemer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanwezigedeelnemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAanwezigedeelnemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanwezigedeelnemer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanwezigedeelnemerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aanwezigedeelnemer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanwezigedeelnemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAanwezigedeelnemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanwezigedeelnemer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanwezigedeelnemerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanwezigedeelnemer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanwezigedeelnemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAanwezigedeelnemerWithPatch() throws Exception {
        // Initialize the database
        aanwezigedeelnemerRepository.saveAndFlush(aanwezigedeelnemer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanwezigedeelnemer using partial update
        Aanwezigedeelnemer partialUpdatedAanwezigedeelnemer = new Aanwezigedeelnemer();
        partialUpdatedAanwezigedeelnemer.setId(aanwezigedeelnemer.getId());

        partialUpdatedAanwezigedeelnemer
            .naam(UPDATED_NAAM)
            .rol(UPDATED_ROL)
            .vertegenwoordigtorganisatie(UPDATED_VERTEGENWOORDIGTORGANISATIE);

        restAanwezigedeelnemerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanwezigedeelnemer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanwezigedeelnemer))
            )
            .andExpect(status().isOk());

        // Validate the Aanwezigedeelnemer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanwezigedeelnemerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAanwezigedeelnemer, aanwezigedeelnemer),
            getPersistedAanwezigedeelnemer(aanwezigedeelnemer)
        );
    }

    @Test
    @Transactional
    void fullUpdateAanwezigedeelnemerWithPatch() throws Exception {
        // Initialize the database
        aanwezigedeelnemerRepository.saveAndFlush(aanwezigedeelnemer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanwezigedeelnemer using partial update
        Aanwezigedeelnemer partialUpdatedAanwezigedeelnemer = new Aanwezigedeelnemer();
        partialUpdatedAanwezigedeelnemer.setId(aanwezigedeelnemer.getId());

        partialUpdatedAanwezigedeelnemer
            .aanvangaanwezigheid(UPDATED_AANVANGAANWEZIGHEID)
            .eindeaanwezigheid(UPDATED_EINDEAANWEZIGHEID)
            .naam(UPDATED_NAAM)
            .rol(UPDATED_ROL)
            .vertegenwoordigtorganisatie(UPDATED_VERTEGENWOORDIGTORGANISATIE);

        restAanwezigedeelnemerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanwezigedeelnemer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanwezigedeelnemer))
            )
            .andExpect(status().isOk());

        // Validate the Aanwezigedeelnemer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanwezigedeelnemerUpdatableFieldsEquals(
            partialUpdatedAanwezigedeelnemer,
            getPersistedAanwezigedeelnemer(partialUpdatedAanwezigedeelnemer)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAanwezigedeelnemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanwezigedeelnemer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanwezigedeelnemerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aanwezigedeelnemer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanwezigedeelnemer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanwezigedeelnemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAanwezigedeelnemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanwezigedeelnemer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanwezigedeelnemerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanwezigedeelnemer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanwezigedeelnemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAanwezigedeelnemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanwezigedeelnemer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanwezigedeelnemerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aanwezigedeelnemer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanwezigedeelnemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAanwezigedeelnemer() throws Exception {
        // Initialize the database
        aanwezigedeelnemerRepository.saveAndFlush(aanwezigedeelnemer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aanwezigedeelnemer
        restAanwezigedeelnemerMockMvc
            .perform(delete(ENTITY_API_URL_ID, aanwezigedeelnemer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aanwezigedeelnemerRepository.count();
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

    protected Aanwezigedeelnemer getPersistedAanwezigedeelnemer(Aanwezigedeelnemer aanwezigedeelnemer) {
        return aanwezigedeelnemerRepository.findById(aanwezigedeelnemer.getId()).orElseThrow();
    }

    protected void assertPersistedAanwezigedeelnemerToMatchAllProperties(Aanwezigedeelnemer expectedAanwezigedeelnemer) {
        assertAanwezigedeelnemerAllPropertiesEquals(expectedAanwezigedeelnemer, getPersistedAanwezigedeelnemer(expectedAanwezigedeelnemer));
    }

    protected void assertPersistedAanwezigedeelnemerToMatchUpdatableProperties(Aanwezigedeelnemer expectedAanwezigedeelnemer) {
        assertAanwezigedeelnemerAllUpdatablePropertiesEquals(
            expectedAanwezigedeelnemer,
            getPersistedAanwezigedeelnemer(expectedAanwezigedeelnemer)
        );
    }
}
