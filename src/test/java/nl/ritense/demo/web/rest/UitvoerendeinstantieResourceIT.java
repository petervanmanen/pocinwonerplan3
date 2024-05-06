package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.UitvoerendeinstantieAsserts.*;
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
import nl.ritense.demo.domain.Uitvoerendeinstantie;
import nl.ritense.demo.repository.UitvoerendeinstantieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UitvoerendeinstantieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UitvoerendeinstantieResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/uitvoerendeinstanties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UitvoerendeinstantieRepository uitvoerendeinstantieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUitvoerendeinstantieMockMvc;

    private Uitvoerendeinstantie uitvoerendeinstantie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitvoerendeinstantie createEntity(EntityManager em) {
        Uitvoerendeinstantie uitvoerendeinstantie = new Uitvoerendeinstantie().naam(DEFAULT_NAAM);
        return uitvoerendeinstantie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitvoerendeinstantie createUpdatedEntity(EntityManager em) {
        Uitvoerendeinstantie uitvoerendeinstantie = new Uitvoerendeinstantie().naam(UPDATED_NAAM);
        return uitvoerendeinstantie;
    }

    @BeforeEach
    public void initTest() {
        uitvoerendeinstantie = createEntity(em);
    }

    @Test
    @Transactional
    void createUitvoerendeinstantie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Uitvoerendeinstantie
        var returnedUitvoerendeinstantie = om.readValue(
            restUitvoerendeinstantieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitvoerendeinstantie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Uitvoerendeinstantie.class
        );

        // Validate the Uitvoerendeinstantie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertUitvoerendeinstantieUpdatableFieldsEquals(
            returnedUitvoerendeinstantie,
            getPersistedUitvoerendeinstantie(returnedUitvoerendeinstantie)
        );
    }

    @Test
    @Transactional
    void createUitvoerendeinstantieWithExistingId() throws Exception {
        // Create the Uitvoerendeinstantie with an existing ID
        uitvoerendeinstantie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUitvoerendeinstantieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitvoerendeinstantie)))
            .andExpect(status().isBadRequest());

        // Validate the Uitvoerendeinstantie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUitvoerendeinstanties() throws Exception {
        // Initialize the database
        uitvoerendeinstantieRepository.saveAndFlush(uitvoerendeinstantie);

        // Get all the uitvoerendeinstantieList
        restUitvoerendeinstantieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uitvoerendeinstantie.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getUitvoerendeinstantie() throws Exception {
        // Initialize the database
        uitvoerendeinstantieRepository.saveAndFlush(uitvoerendeinstantie);

        // Get the uitvoerendeinstantie
        restUitvoerendeinstantieMockMvc
            .perform(get(ENTITY_API_URL_ID, uitvoerendeinstantie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uitvoerendeinstantie.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingUitvoerendeinstantie() throws Exception {
        // Get the uitvoerendeinstantie
        restUitvoerendeinstantieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUitvoerendeinstantie() throws Exception {
        // Initialize the database
        uitvoerendeinstantieRepository.saveAndFlush(uitvoerendeinstantie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitvoerendeinstantie
        Uitvoerendeinstantie updatedUitvoerendeinstantie = uitvoerendeinstantieRepository
            .findById(uitvoerendeinstantie.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedUitvoerendeinstantie are not directly saved in db
        em.detach(updatedUitvoerendeinstantie);
        updatedUitvoerendeinstantie.naam(UPDATED_NAAM);

        restUitvoerendeinstantieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUitvoerendeinstantie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedUitvoerendeinstantie))
            )
            .andExpect(status().isOk());

        // Validate the Uitvoerendeinstantie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUitvoerendeinstantieToMatchAllProperties(updatedUitvoerendeinstantie);
    }

    @Test
    @Transactional
    void putNonExistingUitvoerendeinstantie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitvoerendeinstantie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUitvoerendeinstantieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, uitvoerendeinstantie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uitvoerendeinstantie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitvoerendeinstantie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUitvoerendeinstantie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitvoerendeinstantie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitvoerendeinstantieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uitvoerendeinstantie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitvoerendeinstantie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUitvoerendeinstantie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitvoerendeinstantie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitvoerendeinstantieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitvoerendeinstantie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uitvoerendeinstantie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUitvoerendeinstantieWithPatch() throws Exception {
        // Initialize the database
        uitvoerendeinstantieRepository.saveAndFlush(uitvoerendeinstantie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitvoerendeinstantie using partial update
        Uitvoerendeinstantie partialUpdatedUitvoerendeinstantie = new Uitvoerendeinstantie();
        partialUpdatedUitvoerendeinstantie.setId(uitvoerendeinstantie.getId());

        partialUpdatedUitvoerendeinstantie.naam(UPDATED_NAAM);

        restUitvoerendeinstantieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUitvoerendeinstantie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUitvoerendeinstantie))
            )
            .andExpect(status().isOk());

        // Validate the Uitvoerendeinstantie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUitvoerendeinstantieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUitvoerendeinstantie, uitvoerendeinstantie),
            getPersistedUitvoerendeinstantie(uitvoerendeinstantie)
        );
    }

    @Test
    @Transactional
    void fullUpdateUitvoerendeinstantieWithPatch() throws Exception {
        // Initialize the database
        uitvoerendeinstantieRepository.saveAndFlush(uitvoerendeinstantie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitvoerendeinstantie using partial update
        Uitvoerendeinstantie partialUpdatedUitvoerendeinstantie = new Uitvoerendeinstantie();
        partialUpdatedUitvoerendeinstantie.setId(uitvoerendeinstantie.getId());

        partialUpdatedUitvoerendeinstantie.naam(UPDATED_NAAM);

        restUitvoerendeinstantieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUitvoerendeinstantie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUitvoerendeinstantie))
            )
            .andExpect(status().isOk());

        // Validate the Uitvoerendeinstantie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUitvoerendeinstantieUpdatableFieldsEquals(
            partialUpdatedUitvoerendeinstantie,
            getPersistedUitvoerendeinstantie(partialUpdatedUitvoerendeinstantie)
        );
    }

    @Test
    @Transactional
    void patchNonExistingUitvoerendeinstantie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitvoerendeinstantie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUitvoerendeinstantieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, uitvoerendeinstantie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uitvoerendeinstantie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitvoerendeinstantie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUitvoerendeinstantie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitvoerendeinstantie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitvoerendeinstantieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uitvoerendeinstantie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitvoerendeinstantie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUitvoerendeinstantie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitvoerendeinstantie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitvoerendeinstantieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(uitvoerendeinstantie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uitvoerendeinstantie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUitvoerendeinstantie() throws Exception {
        // Initialize the database
        uitvoerendeinstantieRepository.saveAndFlush(uitvoerendeinstantie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the uitvoerendeinstantie
        restUitvoerendeinstantieMockMvc
            .perform(delete(ENTITY_API_URL_ID, uitvoerendeinstantie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return uitvoerendeinstantieRepository.count();
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

    protected Uitvoerendeinstantie getPersistedUitvoerendeinstantie(Uitvoerendeinstantie uitvoerendeinstantie) {
        return uitvoerendeinstantieRepository.findById(uitvoerendeinstantie.getId()).orElseThrow();
    }

    protected void assertPersistedUitvoerendeinstantieToMatchAllProperties(Uitvoerendeinstantie expectedUitvoerendeinstantie) {
        assertUitvoerendeinstantieAllPropertiesEquals(
            expectedUitvoerendeinstantie,
            getPersistedUitvoerendeinstantie(expectedUitvoerendeinstantie)
        );
    }

    protected void assertPersistedUitvoerendeinstantieToMatchUpdatableProperties(Uitvoerendeinstantie expectedUitvoerendeinstantie) {
        assertUitvoerendeinstantieAllUpdatablePropertiesEquals(
            expectedUitvoerendeinstantie,
            getPersistedUitvoerendeinstantie(expectedUitvoerendeinstantie)
        );
    }
}
