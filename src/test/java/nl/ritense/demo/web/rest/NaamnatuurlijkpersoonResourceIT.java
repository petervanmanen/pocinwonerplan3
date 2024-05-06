package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NaamnatuurlijkpersoonAsserts.*;
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
import nl.ritense.demo.domain.Naamnatuurlijkpersoon;
import nl.ritense.demo.repository.NaamnatuurlijkpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NaamnatuurlijkpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NaamnatuurlijkpersoonResourceIT {

    private static final String DEFAULT_ADELLIJKETITELOFPREDIKAAT = "AAAAAAAAAA";
    private static final String UPDATED_ADELLIJKETITELOFPREDIKAAT = "BBBBBBBBBB";

    private static final String DEFAULT_GESLACHTSNAAM = "AAAAAAAAAA";
    private static final String UPDATED_GESLACHTSNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_VOORNAMEN = "AAAAAAAAAA";
    private static final String UPDATED_VOORNAMEN = "BBBBBBBBBB";

    private static final String DEFAULT_VOORVOEGSELGESLACHTSNAAM = "AAAAAAAAAA";
    private static final String UPDATED_VOORVOEGSELGESLACHTSNAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/naamnatuurlijkpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NaamnatuurlijkpersoonRepository naamnatuurlijkpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNaamnatuurlijkpersoonMockMvc;

    private Naamnatuurlijkpersoon naamnatuurlijkpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Naamnatuurlijkpersoon createEntity(EntityManager em) {
        Naamnatuurlijkpersoon naamnatuurlijkpersoon = new Naamnatuurlijkpersoon()
            .adellijketitelofpredikaat(DEFAULT_ADELLIJKETITELOFPREDIKAAT)
            .geslachtsnaam(DEFAULT_GESLACHTSNAAM)
            .voornamen(DEFAULT_VOORNAMEN)
            .voorvoegselgeslachtsnaam(DEFAULT_VOORVOEGSELGESLACHTSNAAM);
        return naamnatuurlijkpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Naamnatuurlijkpersoon createUpdatedEntity(EntityManager em) {
        Naamnatuurlijkpersoon naamnatuurlijkpersoon = new Naamnatuurlijkpersoon()
            .adellijketitelofpredikaat(UPDATED_ADELLIJKETITELOFPREDIKAAT)
            .geslachtsnaam(UPDATED_GESLACHTSNAAM)
            .voornamen(UPDATED_VOORNAMEN)
            .voorvoegselgeslachtsnaam(UPDATED_VOORVOEGSELGESLACHTSNAAM);
        return naamnatuurlijkpersoon;
    }

    @BeforeEach
    public void initTest() {
        naamnatuurlijkpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createNaamnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Naamnatuurlijkpersoon
        var returnedNaamnatuurlijkpersoon = om.readValue(
            restNaamnatuurlijkpersoonMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(naamnatuurlijkpersoon)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Naamnatuurlijkpersoon.class
        );

        // Validate the Naamnatuurlijkpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNaamnatuurlijkpersoonUpdatableFieldsEquals(
            returnedNaamnatuurlijkpersoon,
            getPersistedNaamnatuurlijkpersoon(returnedNaamnatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void createNaamnatuurlijkpersoonWithExistingId() throws Exception {
        // Create the Naamnatuurlijkpersoon with an existing ID
        naamnatuurlijkpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNaamnatuurlijkpersoonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(naamnatuurlijkpersoon)))
            .andExpect(status().isBadRequest());

        // Validate the Naamnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNaamnatuurlijkpersoons() throws Exception {
        // Initialize the database
        naamnatuurlijkpersoonRepository.saveAndFlush(naamnatuurlijkpersoon);

        // Get all the naamnatuurlijkpersoonList
        restNaamnatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naamnatuurlijkpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].adellijketitelofpredikaat").value(hasItem(DEFAULT_ADELLIJKETITELOFPREDIKAAT)))
            .andExpect(jsonPath("$.[*].geslachtsnaam").value(hasItem(DEFAULT_GESLACHTSNAAM)))
            .andExpect(jsonPath("$.[*].voornamen").value(hasItem(DEFAULT_VOORNAMEN)))
            .andExpect(jsonPath("$.[*].voorvoegselgeslachtsnaam").value(hasItem(DEFAULT_VOORVOEGSELGESLACHTSNAAM)));
    }

    @Test
    @Transactional
    void getNaamnatuurlijkpersoon() throws Exception {
        // Initialize the database
        naamnatuurlijkpersoonRepository.saveAndFlush(naamnatuurlijkpersoon);

        // Get the naamnatuurlijkpersoon
        restNaamnatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, naamnatuurlijkpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(naamnatuurlijkpersoon.getId().intValue()))
            .andExpect(jsonPath("$.adellijketitelofpredikaat").value(DEFAULT_ADELLIJKETITELOFPREDIKAAT))
            .andExpect(jsonPath("$.geslachtsnaam").value(DEFAULT_GESLACHTSNAAM))
            .andExpect(jsonPath("$.voornamen").value(DEFAULT_VOORNAMEN))
            .andExpect(jsonPath("$.voorvoegselgeslachtsnaam").value(DEFAULT_VOORVOEGSELGESLACHTSNAAM));
    }

    @Test
    @Transactional
    void getNonExistingNaamnatuurlijkpersoon() throws Exception {
        // Get the naamnatuurlijkpersoon
        restNaamnatuurlijkpersoonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNaamnatuurlijkpersoon() throws Exception {
        // Initialize the database
        naamnatuurlijkpersoonRepository.saveAndFlush(naamnatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the naamnatuurlijkpersoon
        Naamnatuurlijkpersoon updatedNaamnatuurlijkpersoon = naamnatuurlijkpersoonRepository
            .findById(naamnatuurlijkpersoon.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedNaamnatuurlijkpersoon are not directly saved in db
        em.detach(updatedNaamnatuurlijkpersoon);
        updatedNaamnatuurlijkpersoon
            .adellijketitelofpredikaat(UPDATED_ADELLIJKETITELOFPREDIKAAT)
            .geslachtsnaam(UPDATED_GESLACHTSNAAM)
            .voornamen(UPDATED_VOORNAMEN)
            .voorvoegselgeslachtsnaam(UPDATED_VOORVOEGSELGESLACHTSNAAM);

        restNaamnatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNaamnatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNaamnatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Naamnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNaamnatuurlijkpersoonToMatchAllProperties(updatedNaamnatuurlijkpersoon);
    }

    @Test
    @Transactional
    void putNonExistingNaamnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNaamnatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, naamnatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(naamnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naamnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNaamnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaamnatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(naamnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naamnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNaamnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaamnatuurlijkpersoonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(naamnatuurlijkpersoon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Naamnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNaamnatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        naamnatuurlijkpersoonRepository.saveAndFlush(naamnatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the naamnatuurlijkpersoon using partial update
        Naamnatuurlijkpersoon partialUpdatedNaamnatuurlijkpersoon = new Naamnatuurlijkpersoon();
        partialUpdatedNaamnatuurlijkpersoon.setId(naamnatuurlijkpersoon.getId());

        partialUpdatedNaamnatuurlijkpersoon
            .adellijketitelofpredikaat(UPDATED_ADELLIJKETITELOFPREDIKAAT)
            .geslachtsnaam(UPDATED_GESLACHTSNAAM)
            .voornamen(UPDATED_VOORNAMEN)
            .voorvoegselgeslachtsnaam(UPDATED_VOORVOEGSELGESLACHTSNAAM);

        restNaamnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNaamnatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNaamnatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Naamnatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNaamnatuurlijkpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNaamnatuurlijkpersoon, naamnatuurlijkpersoon),
            getPersistedNaamnatuurlijkpersoon(naamnatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateNaamnatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        naamnatuurlijkpersoonRepository.saveAndFlush(naamnatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the naamnatuurlijkpersoon using partial update
        Naamnatuurlijkpersoon partialUpdatedNaamnatuurlijkpersoon = new Naamnatuurlijkpersoon();
        partialUpdatedNaamnatuurlijkpersoon.setId(naamnatuurlijkpersoon.getId());

        partialUpdatedNaamnatuurlijkpersoon
            .adellijketitelofpredikaat(UPDATED_ADELLIJKETITELOFPREDIKAAT)
            .geslachtsnaam(UPDATED_GESLACHTSNAAM)
            .voornamen(UPDATED_VOORNAMEN)
            .voorvoegselgeslachtsnaam(UPDATED_VOORVOEGSELGESLACHTSNAAM);

        restNaamnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNaamnatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNaamnatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Naamnatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNaamnatuurlijkpersoonUpdatableFieldsEquals(
            partialUpdatedNaamnatuurlijkpersoon,
            getPersistedNaamnatuurlijkpersoon(partialUpdatedNaamnatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNaamnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNaamnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, naamnatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(naamnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naamnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNaamnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaamnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(naamnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naamnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNaamnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaamnatuurlijkpersoonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(naamnatuurlijkpersoon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Naamnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNaamnatuurlijkpersoon() throws Exception {
        // Initialize the database
        naamnatuurlijkpersoonRepository.saveAndFlush(naamnatuurlijkpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the naamnatuurlijkpersoon
        restNaamnatuurlijkpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, naamnatuurlijkpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return naamnatuurlijkpersoonRepository.count();
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

    protected Naamnatuurlijkpersoon getPersistedNaamnatuurlijkpersoon(Naamnatuurlijkpersoon naamnatuurlijkpersoon) {
        return naamnatuurlijkpersoonRepository.findById(naamnatuurlijkpersoon.getId()).orElseThrow();
    }

    protected void assertPersistedNaamnatuurlijkpersoonToMatchAllProperties(Naamnatuurlijkpersoon expectedNaamnatuurlijkpersoon) {
        assertNaamnatuurlijkpersoonAllPropertiesEquals(
            expectedNaamnatuurlijkpersoon,
            getPersistedNaamnatuurlijkpersoon(expectedNaamnatuurlijkpersoon)
        );
    }

    protected void assertPersistedNaamnatuurlijkpersoonToMatchUpdatableProperties(Naamnatuurlijkpersoon expectedNaamnatuurlijkpersoon) {
        assertNaamnatuurlijkpersoonAllUpdatablePropertiesEquals(
            expectedNaamnatuurlijkpersoon,
            getPersistedNaamnatuurlijkpersoon(expectedNaamnatuurlijkpersoon)
        );
    }
}
