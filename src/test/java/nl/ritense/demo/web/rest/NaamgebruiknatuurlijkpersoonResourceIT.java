package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NaamgebruiknatuurlijkpersoonAsserts.*;
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
import nl.ritense.demo.domain.Naamgebruiknatuurlijkpersoon;
import nl.ritense.demo.repository.NaamgebruiknatuurlijkpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NaamgebruiknatuurlijkpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NaamgebruiknatuurlijkpersoonResourceIT {

    private static final String DEFAULT_AANHEFAANSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_AANHEFAANSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_ADELLIJKETITELNAAMGEBRUIK = "AAAAAAAAAA";
    private static final String UPDATED_ADELLIJKETITELNAAMGEBRUIK = "BBBBBBBBBB";

    private static final String DEFAULT_GESLACHTSNAAMSTAMNAAMGEBRUIK = "AAAAAAAAAA";
    private static final String UPDATED_GESLACHTSNAAMSTAMNAAMGEBRUIK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/naamgebruiknatuurlijkpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NaamgebruiknatuurlijkpersoonRepository naamgebruiknatuurlijkpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNaamgebruiknatuurlijkpersoonMockMvc;

    private Naamgebruiknatuurlijkpersoon naamgebruiknatuurlijkpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Naamgebruiknatuurlijkpersoon createEntity(EntityManager em) {
        Naamgebruiknatuurlijkpersoon naamgebruiknatuurlijkpersoon = new Naamgebruiknatuurlijkpersoon()
            .aanhefaanschrijving(DEFAULT_AANHEFAANSCHRIJVING)
            .adellijketitelnaamgebruik(DEFAULT_ADELLIJKETITELNAAMGEBRUIK)
            .geslachtsnaamstamnaamgebruik(DEFAULT_GESLACHTSNAAMSTAMNAAMGEBRUIK);
        return naamgebruiknatuurlijkpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Naamgebruiknatuurlijkpersoon createUpdatedEntity(EntityManager em) {
        Naamgebruiknatuurlijkpersoon naamgebruiknatuurlijkpersoon = new Naamgebruiknatuurlijkpersoon()
            .aanhefaanschrijving(UPDATED_AANHEFAANSCHRIJVING)
            .adellijketitelnaamgebruik(UPDATED_ADELLIJKETITELNAAMGEBRUIK)
            .geslachtsnaamstamnaamgebruik(UPDATED_GESLACHTSNAAMSTAMNAAMGEBRUIK);
        return naamgebruiknatuurlijkpersoon;
    }

    @BeforeEach
    public void initTest() {
        naamgebruiknatuurlijkpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createNaamgebruiknatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Naamgebruiknatuurlijkpersoon
        var returnedNaamgebruiknatuurlijkpersoon = om.readValue(
            restNaamgebruiknatuurlijkpersoonMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(naamgebruiknatuurlijkpersoon))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Naamgebruiknatuurlijkpersoon.class
        );

        // Validate the Naamgebruiknatuurlijkpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNaamgebruiknatuurlijkpersoonUpdatableFieldsEquals(
            returnedNaamgebruiknatuurlijkpersoon,
            getPersistedNaamgebruiknatuurlijkpersoon(returnedNaamgebruiknatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void createNaamgebruiknatuurlijkpersoonWithExistingId() throws Exception {
        // Create the Naamgebruiknatuurlijkpersoon with an existing ID
        naamgebruiknatuurlijkpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNaamgebruiknatuurlijkpersoonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(naamgebruiknatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naamgebruiknatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNaamgebruiknatuurlijkpersoons() throws Exception {
        // Initialize the database
        naamgebruiknatuurlijkpersoonRepository.saveAndFlush(naamgebruiknatuurlijkpersoon);

        // Get all the naamgebruiknatuurlijkpersoonList
        restNaamgebruiknatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naamgebruiknatuurlijkpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanhefaanschrijving").value(hasItem(DEFAULT_AANHEFAANSCHRIJVING)))
            .andExpect(jsonPath("$.[*].adellijketitelnaamgebruik").value(hasItem(DEFAULT_ADELLIJKETITELNAAMGEBRUIK)))
            .andExpect(jsonPath("$.[*].geslachtsnaamstamnaamgebruik").value(hasItem(DEFAULT_GESLACHTSNAAMSTAMNAAMGEBRUIK)));
    }

    @Test
    @Transactional
    void getNaamgebruiknatuurlijkpersoon() throws Exception {
        // Initialize the database
        naamgebruiknatuurlijkpersoonRepository.saveAndFlush(naamgebruiknatuurlijkpersoon);

        // Get the naamgebruiknatuurlijkpersoon
        restNaamgebruiknatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, naamgebruiknatuurlijkpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(naamgebruiknatuurlijkpersoon.getId().intValue()))
            .andExpect(jsonPath("$.aanhefaanschrijving").value(DEFAULT_AANHEFAANSCHRIJVING))
            .andExpect(jsonPath("$.adellijketitelnaamgebruik").value(DEFAULT_ADELLIJKETITELNAAMGEBRUIK))
            .andExpect(jsonPath("$.geslachtsnaamstamnaamgebruik").value(DEFAULT_GESLACHTSNAAMSTAMNAAMGEBRUIK));
    }

    @Test
    @Transactional
    void getNonExistingNaamgebruiknatuurlijkpersoon() throws Exception {
        // Get the naamgebruiknatuurlijkpersoon
        restNaamgebruiknatuurlijkpersoonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNaamgebruiknatuurlijkpersoon() throws Exception {
        // Initialize the database
        naamgebruiknatuurlijkpersoonRepository.saveAndFlush(naamgebruiknatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the naamgebruiknatuurlijkpersoon
        Naamgebruiknatuurlijkpersoon updatedNaamgebruiknatuurlijkpersoon = naamgebruiknatuurlijkpersoonRepository
            .findById(naamgebruiknatuurlijkpersoon.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedNaamgebruiknatuurlijkpersoon are not directly saved in db
        em.detach(updatedNaamgebruiknatuurlijkpersoon);
        updatedNaamgebruiknatuurlijkpersoon
            .aanhefaanschrijving(UPDATED_AANHEFAANSCHRIJVING)
            .adellijketitelnaamgebruik(UPDATED_ADELLIJKETITELNAAMGEBRUIK)
            .geslachtsnaamstamnaamgebruik(UPDATED_GESLACHTSNAAMSTAMNAAMGEBRUIK);

        restNaamgebruiknatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNaamgebruiknatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNaamgebruiknatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Naamgebruiknatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNaamgebruiknatuurlijkpersoonToMatchAllProperties(updatedNaamgebruiknatuurlijkpersoon);
    }

    @Test
    @Transactional
    void putNonExistingNaamgebruiknatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamgebruiknatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNaamgebruiknatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, naamgebruiknatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(naamgebruiknatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naamgebruiknatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNaamgebruiknatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamgebruiknatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaamgebruiknatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(naamgebruiknatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naamgebruiknatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNaamgebruiknatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamgebruiknatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaamgebruiknatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(naamgebruiknatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Naamgebruiknatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNaamgebruiknatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        naamgebruiknatuurlijkpersoonRepository.saveAndFlush(naamgebruiknatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the naamgebruiknatuurlijkpersoon using partial update
        Naamgebruiknatuurlijkpersoon partialUpdatedNaamgebruiknatuurlijkpersoon = new Naamgebruiknatuurlijkpersoon();
        partialUpdatedNaamgebruiknatuurlijkpersoon.setId(naamgebruiknatuurlijkpersoon.getId());

        partialUpdatedNaamgebruiknatuurlijkpersoon
            .aanhefaanschrijving(UPDATED_AANHEFAANSCHRIJVING)
            .adellijketitelnaamgebruik(UPDATED_ADELLIJKETITELNAAMGEBRUIK)
            .geslachtsnaamstamnaamgebruik(UPDATED_GESLACHTSNAAMSTAMNAAMGEBRUIK);

        restNaamgebruiknatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNaamgebruiknatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNaamgebruiknatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Naamgebruiknatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNaamgebruiknatuurlijkpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNaamgebruiknatuurlijkpersoon, naamgebruiknatuurlijkpersoon),
            getPersistedNaamgebruiknatuurlijkpersoon(naamgebruiknatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateNaamgebruiknatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        naamgebruiknatuurlijkpersoonRepository.saveAndFlush(naamgebruiknatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the naamgebruiknatuurlijkpersoon using partial update
        Naamgebruiknatuurlijkpersoon partialUpdatedNaamgebruiknatuurlijkpersoon = new Naamgebruiknatuurlijkpersoon();
        partialUpdatedNaamgebruiknatuurlijkpersoon.setId(naamgebruiknatuurlijkpersoon.getId());

        partialUpdatedNaamgebruiknatuurlijkpersoon
            .aanhefaanschrijving(UPDATED_AANHEFAANSCHRIJVING)
            .adellijketitelnaamgebruik(UPDATED_ADELLIJKETITELNAAMGEBRUIK)
            .geslachtsnaamstamnaamgebruik(UPDATED_GESLACHTSNAAMSTAMNAAMGEBRUIK);

        restNaamgebruiknatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNaamgebruiknatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNaamgebruiknatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Naamgebruiknatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNaamgebruiknatuurlijkpersoonUpdatableFieldsEquals(
            partialUpdatedNaamgebruiknatuurlijkpersoon,
            getPersistedNaamgebruiknatuurlijkpersoon(partialUpdatedNaamgebruiknatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNaamgebruiknatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamgebruiknatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNaamgebruiknatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, naamgebruiknatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(naamgebruiknatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naamgebruiknatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNaamgebruiknatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamgebruiknatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaamgebruiknatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(naamgebruiknatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naamgebruiknatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNaamgebruiknatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamgebruiknatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaamgebruiknatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(naamgebruiknatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Naamgebruiknatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNaamgebruiknatuurlijkpersoon() throws Exception {
        // Initialize the database
        naamgebruiknatuurlijkpersoonRepository.saveAndFlush(naamgebruiknatuurlijkpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the naamgebruiknatuurlijkpersoon
        restNaamgebruiknatuurlijkpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, naamgebruiknatuurlijkpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return naamgebruiknatuurlijkpersoonRepository.count();
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

    protected Naamgebruiknatuurlijkpersoon getPersistedNaamgebruiknatuurlijkpersoon(
        Naamgebruiknatuurlijkpersoon naamgebruiknatuurlijkpersoon
    ) {
        return naamgebruiknatuurlijkpersoonRepository.findById(naamgebruiknatuurlijkpersoon.getId()).orElseThrow();
    }

    protected void assertPersistedNaamgebruiknatuurlijkpersoonToMatchAllProperties(
        Naamgebruiknatuurlijkpersoon expectedNaamgebruiknatuurlijkpersoon
    ) {
        assertNaamgebruiknatuurlijkpersoonAllPropertiesEquals(
            expectedNaamgebruiknatuurlijkpersoon,
            getPersistedNaamgebruiknatuurlijkpersoon(expectedNaamgebruiknatuurlijkpersoon)
        );
    }

    protected void assertPersistedNaamgebruiknatuurlijkpersoonToMatchUpdatableProperties(
        Naamgebruiknatuurlijkpersoon expectedNaamgebruiknatuurlijkpersoon
    ) {
        assertNaamgebruiknatuurlijkpersoonAllUpdatablePropertiesEquals(
            expectedNaamgebruiknatuurlijkpersoon,
            getPersistedNaamgebruiknatuurlijkpersoon(expectedNaamgebruiknatuurlijkpersoon)
        );
    }
}
