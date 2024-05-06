package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NaamaanschrijvingnatuurlijkpersoonAsserts.*;
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
import nl.ritense.demo.domain.Naamaanschrijvingnatuurlijkpersoon;
import nl.ritense.demo.repository.NaamaanschrijvingnatuurlijkpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NaamaanschrijvingnatuurlijkpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NaamaanschrijvingnatuurlijkpersoonResourceIT {

    private static final String DEFAULT_AANHEFAANSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_AANHEFAANSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_GESLACHTSNAAMAANSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_GESLACHTSNAAMAANSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_VOORLETTERSAANSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_VOORLETTERSAANSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_VOORNAMENAANSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_VOORNAMENAANSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/naamaanschrijvingnatuurlijkpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NaamaanschrijvingnatuurlijkpersoonRepository naamaanschrijvingnatuurlijkpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNaamaanschrijvingnatuurlijkpersoonMockMvc;

    private Naamaanschrijvingnatuurlijkpersoon naamaanschrijvingnatuurlijkpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Naamaanschrijvingnatuurlijkpersoon createEntity(EntityManager em) {
        Naamaanschrijvingnatuurlijkpersoon naamaanschrijvingnatuurlijkpersoon = new Naamaanschrijvingnatuurlijkpersoon()
            .aanhefaanschrijving(DEFAULT_AANHEFAANSCHRIJVING)
            .geslachtsnaamaanschrijving(DEFAULT_GESLACHTSNAAMAANSCHRIJVING)
            .voorlettersaanschrijving(DEFAULT_VOORLETTERSAANSCHRIJVING)
            .voornamenaanschrijving(DEFAULT_VOORNAMENAANSCHRIJVING);
        return naamaanschrijvingnatuurlijkpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Naamaanschrijvingnatuurlijkpersoon createUpdatedEntity(EntityManager em) {
        Naamaanschrijvingnatuurlijkpersoon naamaanschrijvingnatuurlijkpersoon = new Naamaanschrijvingnatuurlijkpersoon()
            .aanhefaanschrijving(UPDATED_AANHEFAANSCHRIJVING)
            .geslachtsnaamaanschrijving(UPDATED_GESLACHTSNAAMAANSCHRIJVING)
            .voorlettersaanschrijving(UPDATED_VOORLETTERSAANSCHRIJVING)
            .voornamenaanschrijving(UPDATED_VOORNAMENAANSCHRIJVING);
        return naamaanschrijvingnatuurlijkpersoon;
    }

    @BeforeEach
    public void initTest() {
        naamaanschrijvingnatuurlijkpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createNaamaanschrijvingnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Naamaanschrijvingnatuurlijkpersoon
        var returnedNaamaanschrijvingnatuurlijkpersoon = om.readValue(
            restNaamaanschrijvingnatuurlijkpersoonMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(naamaanschrijvingnatuurlijkpersoon))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Naamaanschrijvingnatuurlijkpersoon.class
        );

        // Validate the Naamaanschrijvingnatuurlijkpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNaamaanschrijvingnatuurlijkpersoonUpdatableFieldsEquals(
            returnedNaamaanschrijvingnatuurlijkpersoon,
            getPersistedNaamaanschrijvingnatuurlijkpersoon(returnedNaamaanschrijvingnatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void createNaamaanschrijvingnatuurlijkpersoonWithExistingId() throws Exception {
        // Create the Naamaanschrijvingnatuurlijkpersoon with an existing ID
        naamaanschrijvingnatuurlijkpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNaamaanschrijvingnatuurlijkpersoonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(naamaanschrijvingnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naamaanschrijvingnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNaamaanschrijvingnatuurlijkpersoons() throws Exception {
        // Initialize the database
        naamaanschrijvingnatuurlijkpersoonRepository.saveAndFlush(naamaanschrijvingnatuurlijkpersoon);

        // Get all the naamaanschrijvingnatuurlijkpersoonList
        restNaamaanschrijvingnatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naamaanschrijvingnatuurlijkpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanhefaanschrijving").value(hasItem(DEFAULT_AANHEFAANSCHRIJVING)))
            .andExpect(jsonPath("$.[*].geslachtsnaamaanschrijving").value(hasItem(DEFAULT_GESLACHTSNAAMAANSCHRIJVING)))
            .andExpect(jsonPath("$.[*].voorlettersaanschrijving").value(hasItem(DEFAULT_VOORLETTERSAANSCHRIJVING)))
            .andExpect(jsonPath("$.[*].voornamenaanschrijving").value(hasItem(DEFAULT_VOORNAMENAANSCHRIJVING)));
    }

    @Test
    @Transactional
    void getNaamaanschrijvingnatuurlijkpersoon() throws Exception {
        // Initialize the database
        naamaanschrijvingnatuurlijkpersoonRepository.saveAndFlush(naamaanschrijvingnatuurlijkpersoon);

        // Get the naamaanschrijvingnatuurlijkpersoon
        restNaamaanschrijvingnatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, naamaanschrijvingnatuurlijkpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(naamaanschrijvingnatuurlijkpersoon.getId().intValue()))
            .andExpect(jsonPath("$.aanhefaanschrijving").value(DEFAULT_AANHEFAANSCHRIJVING))
            .andExpect(jsonPath("$.geslachtsnaamaanschrijving").value(DEFAULT_GESLACHTSNAAMAANSCHRIJVING))
            .andExpect(jsonPath("$.voorlettersaanschrijving").value(DEFAULT_VOORLETTERSAANSCHRIJVING))
            .andExpect(jsonPath("$.voornamenaanschrijving").value(DEFAULT_VOORNAMENAANSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingNaamaanschrijvingnatuurlijkpersoon() throws Exception {
        // Get the naamaanschrijvingnatuurlijkpersoon
        restNaamaanschrijvingnatuurlijkpersoonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNaamaanschrijvingnatuurlijkpersoon() throws Exception {
        // Initialize the database
        naamaanschrijvingnatuurlijkpersoonRepository.saveAndFlush(naamaanschrijvingnatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the naamaanschrijvingnatuurlijkpersoon
        Naamaanschrijvingnatuurlijkpersoon updatedNaamaanschrijvingnatuurlijkpersoon = naamaanschrijvingnatuurlijkpersoonRepository
            .findById(naamaanschrijvingnatuurlijkpersoon.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedNaamaanschrijvingnatuurlijkpersoon are not directly saved in db
        em.detach(updatedNaamaanschrijvingnatuurlijkpersoon);
        updatedNaamaanschrijvingnatuurlijkpersoon
            .aanhefaanschrijving(UPDATED_AANHEFAANSCHRIJVING)
            .geslachtsnaamaanschrijving(UPDATED_GESLACHTSNAAMAANSCHRIJVING)
            .voorlettersaanschrijving(UPDATED_VOORLETTERSAANSCHRIJVING)
            .voornamenaanschrijving(UPDATED_VOORNAMENAANSCHRIJVING);

        restNaamaanschrijvingnatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNaamaanschrijvingnatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNaamaanschrijvingnatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Naamaanschrijvingnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNaamaanschrijvingnatuurlijkpersoonToMatchAllProperties(updatedNaamaanschrijvingnatuurlijkpersoon);
    }

    @Test
    @Transactional
    void putNonExistingNaamaanschrijvingnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamaanschrijvingnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNaamaanschrijvingnatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, naamaanschrijvingnatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(naamaanschrijvingnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naamaanschrijvingnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNaamaanschrijvingnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamaanschrijvingnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaamaanschrijvingnatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(naamaanschrijvingnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naamaanschrijvingnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNaamaanschrijvingnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamaanschrijvingnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaamaanschrijvingnatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(naamaanschrijvingnatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Naamaanschrijvingnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNaamaanschrijvingnatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        naamaanschrijvingnatuurlijkpersoonRepository.saveAndFlush(naamaanschrijvingnatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the naamaanschrijvingnatuurlijkpersoon using partial update
        Naamaanschrijvingnatuurlijkpersoon partialUpdatedNaamaanschrijvingnatuurlijkpersoon = new Naamaanschrijvingnatuurlijkpersoon();
        partialUpdatedNaamaanschrijvingnatuurlijkpersoon.setId(naamaanschrijvingnatuurlijkpersoon.getId());

        partialUpdatedNaamaanschrijvingnatuurlijkpersoon
            .aanhefaanschrijving(UPDATED_AANHEFAANSCHRIJVING)
            .voorlettersaanschrijving(UPDATED_VOORLETTERSAANSCHRIJVING);

        restNaamaanschrijvingnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNaamaanschrijvingnatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNaamaanschrijvingnatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Naamaanschrijvingnatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNaamaanschrijvingnatuurlijkpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNaamaanschrijvingnatuurlijkpersoon, naamaanschrijvingnatuurlijkpersoon),
            getPersistedNaamaanschrijvingnatuurlijkpersoon(naamaanschrijvingnatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateNaamaanschrijvingnatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        naamaanschrijvingnatuurlijkpersoonRepository.saveAndFlush(naamaanschrijvingnatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the naamaanschrijvingnatuurlijkpersoon using partial update
        Naamaanschrijvingnatuurlijkpersoon partialUpdatedNaamaanschrijvingnatuurlijkpersoon = new Naamaanschrijvingnatuurlijkpersoon();
        partialUpdatedNaamaanschrijvingnatuurlijkpersoon.setId(naamaanschrijvingnatuurlijkpersoon.getId());

        partialUpdatedNaamaanschrijvingnatuurlijkpersoon
            .aanhefaanschrijving(UPDATED_AANHEFAANSCHRIJVING)
            .geslachtsnaamaanschrijving(UPDATED_GESLACHTSNAAMAANSCHRIJVING)
            .voorlettersaanschrijving(UPDATED_VOORLETTERSAANSCHRIJVING)
            .voornamenaanschrijving(UPDATED_VOORNAMENAANSCHRIJVING);

        restNaamaanschrijvingnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNaamaanschrijvingnatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNaamaanschrijvingnatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Naamaanschrijvingnatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNaamaanschrijvingnatuurlijkpersoonUpdatableFieldsEquals(
            partialUpdatedNaamaanschrijvingnatuurlijkpersoon,
            getPersistedNaamaanschrijvingnatuurlijkpersoon(partialUpdatedNaamaanschrijvingnatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNaamaanschrijvingnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamaanschrijvingnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNaamaanschrijvingnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, naamaanschrijvingnatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(naamaanschrijvingnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naamaanschrijvingnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNaamaanschrijvingnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamaanschrijvingnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaamaanschrijvingnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(naamaanschrijvingnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Naamaanschrijvingnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNaamaanschrijvingnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        naamaanschrijvingnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNaamaanschrijvingnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(naamaanschrijvingnatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Naamaanschrijvingnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNaamaanschrijvingnatuurlijkpersoon() throws Exception {
        // Initialize the database
        naamaanschrijvingnatuurlijkpersoonRepository.saveAndFlush(naamaanschrijvingnatuurlijkpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the naamaanschrijvingnatuurlijkpersoon
        restNaamaanschrijvingnatuurlijkpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, naamaanschrijvingnatuurlijkpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return naamaanschrijvingnatuurlijkpersoonRepository.count();
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

    protected Naamaanschrijvingnatuurlijkpersoon getPersistedNaamaanschrijvingnatuurlijkpersoon(
        Naamaanschrijvingnatuurlijkpersoon naamaanschrijvingnatuurlijkpersoon
    ) {
        return naamaanschrijvingnatuurlijkpersoonRepository.findById(naamaanschrijvingnatuurlijkpersoon.getId()).orElseThrow();
    }

    protected void assertPersistedNaamaanschrijvingnatuurlijkpersoonToMatchAllProperties(
        Naamaanschrijvingnatuurlijkpersoon expectedNaamaanschrijvingnatuurlijkpersoon
    ) {
        assertNaamaanschrijvingnatuurlijkpersoonAllPropertiesEquals(
            expectedNaamaanschrijvingnatuurlijkpersoon,
            getPersistedNaamaanschrijvingnatuurlijkpersoon(expectedNaamaanschrijvingnatuurlijkpersoon)
        );
    }

    protected void assertPersistedNaamaanschrijvingnatuurlijkpersoonToMatchUpdatableProperties(
        Naamaanschrijvingnatuurlijkpersoon expectedNaamaanschrijvingnatuurlijkpersoon
    ) {
        assertNaamaanschrijvingnatuurlijkpersoonAllUpdatablePropertiesEquals(
            expectedNaamaanschrijvingnatuurlijkpersoon,
            getPersistedNaamaanschrijvingnatuurlijkpersoon(expectedNaamaanschrijvingnatuurlijkpersoon)
        );
    }
}
