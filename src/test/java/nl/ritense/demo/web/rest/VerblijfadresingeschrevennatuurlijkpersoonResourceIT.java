package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerblijfadresingeschrevennatuurlijkpersoonAsserts.*;
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
import nl.ritense.demo.domain.Verblijfadresingeschrevennatuurlijkpersoon;
import nl.ritense.demo.repository.VerblijfadresingeschrevennatuurlijkpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerblijfadresingeschrevennatuurlijkpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerblijfadresingeschrevennatuurlijkpersoonResourceIT {

    private static final String DEFAULT_ADRESHERKOMST = "AAAAAAAAAA";
    private static final String UPDATED_ADRESHERKOMST = "BBBBBBBBBB";

    private static final String DEFAULT_BESCHRIJVINGLOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_BESCHRIJVINGLOCATIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verblijfadresingeschrevennatuurlijkpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerblijfadresingeschrevennatuurlijkpersoonRepository verblijfadresingeschrevennatuurlijkpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerblijfadresingeschrevennatuurlijkpersoonMockMvc;

    private Verblijfadresingeschrevennatuurlijkpersoon verblijfadresingeschrevennatuurlijkpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfadresingeschrevennatuurlijkpersoon createEntity(EntityManager em) {
        Verblijfadresingeschrevennatuurlijkpersoon verblijfadresingeschrevennatuurlijkpersoon =
            new Verblijfadresingeschrevennatuurlijkpersoon()
                .adresherkomst(DEFAULT_ADRESHERKOMST)
                .beschrijvinglocatie(DEFAULT_BESCHRIJVINGLOCATIE);
        return verblijfadresingeschrevennatuurlijkpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfadresingeschrevennatuurlijkpersoon createUpdatedEntity(EntityManager em) {
        Verblijfadresingeschrevennatuurlijkpersoon verblijfadresingeschrevennatuurlijkpersoon =
            new Verblijfadresingeschrevennatuurlijkpersoon()
                .adresherkomst(UPDATED_ADRESHERKOMST)
                .beschrijvinglocatie(UPDATED_BESCHRIJVINGLOCATIE);
        return verblijfadresingeschrevennatuurlijkpersoon;
    }

    @BeforeEach
    public void initTest() {
        verblijfadresingeschrevennatuurlijkpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createVerblijfadresingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verblijfadresingeschrevennatuurlijkpersoon
        var returnedVerblijfadresingeschrevennatuurlijkpersoon = om.readValue(
            restVerblijfadresingeschrevennatuurlijkpersoonMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(verblijfadresingeschrevennatuurlijkpersoon))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verblijfadresingeschrevennatuurlijkpersoon.class
        );

        // Validate the Verblijfadresingeschrevennatuurlijkpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerblijfadresingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            returnedVerblijfadresingeschrevennatuurlijkpersoon,
            getPersistedVerblijfadresingeschrevennatuurlijkpersoon(returnedVerblijfadresingeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void createVerblijfadresingeschrevennatuurlijkpersoonWithExistingId() throws Exception {
        // Create the Verblijfadresingeschrevennatuurlijkpersoon with an existing ID
        verblijfadresingeschrevennatuurlijkpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerblijfadresingeschrevennatuurlijkpersoonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfadresingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfadresingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerblijfadresingeschrevennatuurlijkpersoons() throws Exception {
        // Initialize the database
        verblijfadresingeschrevennatuurlijkpersoonRepository.saveAndFlush(verblijfadresingeschrevennatuurlijkpersoon);

        // Get all the verblijfadresingeschrevennatuurlijkpersoonList
        restVerblijfadresingeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verblijfadresingeschrevennatuurlijkpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresherkomst").value(hasItem(DEFAULT_ADRESHERKOMST)))
            .andExpect(jsonPath("$.[*].beschrijvinglocatie").value(hasItem(DEFAULT_BESCHRIJVINGLOCATIE)));
    }

    @Test
    @Transactional
    void getVerblijfadresingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        verblijfadresingeschrevennatuurlijkpersoonRepository.saveAndFlush(verblijfadresingeschrevennatuurlijkpersoon);

        // Get the verblijfadresingeschrevennatuurlijkpersoon
        restVerblijfadresingeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, verblijfadresingeschrevennatuurlijkpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verblijfadresingeschrevennatuurlijkpersoon.getId().intValue()))
            .andExpect(jsonPath("$.adresherkomst").value(DEFAULT_ADRESHERKOMST))
            .andExpect(jsonPath("$.beschrijvinglocatie").value(DEFAULT_BESCHRIJVINGLOCATIE));
    }

    @Test
    @Transactional
    void getNonExistingVerblijfadresingeschrevennatuurlijkpersoon() throws Exception {
        // Get the verblijfadresingeschrevennatuurlijkpersoon
        restVerblijfadresingeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerblijfadresingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        verblijfadresingeschrevennatuurlijkpersoonRepository.saveAndFlush(verblijfadresingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfadresingeschrevennatuurlijkpersoon
        Verblijfadresingeschrevennatuurlijkpersoon updatedVerblijfadresingeschrevennatuurlijkpersoon =
            verblijfadresingeschrevennatuurlijkpersoonRepository.findById(verblijfadresingeschrevennatuurlijkpersoon.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerblijfadresingeschrevennatuurlijkpersoon are not directly saved in db
        em.detach(updatedVerblijfadresingeschrevennatuurlijkpersoon);
        updatedVerblijfadresingeschrevennatuurlijkpersoon
            .adresherkomst(UPDATED_ADRESHERKOMST)
            .beschrijvinglocatie(UPDATED_BESCHRIJVINGLOCATIE);

        restVerblijfadresingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerblijfadresingeschrevennatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerblijfadresingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfadresingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerblijfadresingeschrevennatuurlijkpersoonToMatchAllProperties(updatedVerblijfadresingeschrevennatuurlijkpersoon);
    }

    @Test
    @Transactional
    void putNonExistingVerblijfadresingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfadresingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerblijfadresingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verblijfadresingeschrevennatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfadresingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfadresingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerblijfadresingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfadresingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfadresingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfadresingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfadresingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerblijfadresingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfadresingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfadresingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfadresingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verblijfadresingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerblijfadresingeschrevennatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        verblijfadresingeschrevennatuurlijkpersoonRepository.saveAndFlush(verblijfadresingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfadresingeschrevennatuurlijkpersoon using partial update
        Verblijfadresingeschrevennatuurlijkpersoon partialUpdatedVerblijfadresingeschrevennatuurlijkpersoon =
            new Verblijfadresingeschrevennatuurlijkpersoon();
        partialUpdatedVerblijfadresingeschrevennatuurlijkpersoon.setId(verblijfadresingeschrevennatuurlijkpersoon.getId());

        partialUpdatedVerblijfadresingeschrevennatuurlijkpersoon.adresherkomst(UPDATED_ADRESHERKOMST);

        restVerblijfadresingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerblijfadresingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerblijfadresingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfadresingeschrevennatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerblijfadresingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerblijfadresingeschrevennatuurlijkpersoon, verblijfadresingeschrevennatuurlijkpersoon),
            getPersistedVerblijfadresingeschrevennatuurlijkpersoon(verblijfadresingeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerblijfadresingeschrevennatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        verblijfadresingeschrevennatuurlijkpersoonRepository.saveAndFlush(verblijfadresingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfadresingeschrevennatuurlijkpersoon using partial update
        Verblijfadresingeschrevennatuurlijkpersoon partialUpdatedVerblijfadresingeschrevennatuurlijkpersoon =
            new Verblijfadresingeschrevennatuurlijkpersoon();
        partialUpdatedVerblijfadresingeschrevennatuurlijkpersoon.setId(verblijfadresingeschrevennatuurlijkpersoon.getId());

        partialUpdatedVerblijfadresingeschrevennatuurlijkpersoon
            .adresherkomst(UPDATED_ADRESHERKOMST)
            .beschrijvinglocatie(UPDATED_BESCHRIJVINGLOCATIE);

        restVerblijfadresingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerblijfadresingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerblijfadresingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfadresingeschrevennatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerblijfadresingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            partialUpdatedVerblijfadresingeschrevennatuurlijkpersoon,
            getPersistedVerblijfadresingeschrevennatuurlijkpersoon(partialUpdatedVerblijfadresingeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVerblijfadresingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfadresingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerblijfadresingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verblijfadresingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verblijfadresingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfadresingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerblijfadresingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfadresingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfadresingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verblijfadresingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfadresingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerblijfadresingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfadresingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfadresingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verblijfadresingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verblijfadresingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerblijfadresingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        verblijfadresingeschrevennatuurlijkpersoonRepository.saveAndFlush(verblijfadresingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verblijfadresingeschrevennatuurlijkpersoon
        restVerblijfadresingeschrevennatuurlijkpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, verblijfadresingeschrevennatuurlijkpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verblijfadresingeschrevennatuurlijkpersoonRepository.count();
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

    protected Verblijfadresingeschrevennatuurlijkpersoon getPersistedVerblijfadresingeschrevennatuurlijkpersoon(
        Verblijfadresingeschrevennatuurlijkpersoon verblijfadresingeschrevennatuurlijkpersoon
    ) {
        return verblijfadresingeschrevennatuurlijkpersoonRepository
            .findById(verblijfadresingeschrevennatuurlijkpersoon.getId())
            .orElseThrow();
    }

    protected void assertPersistedVerblijfadresingeschrevennatuurlijkpersoonToMatchAllProperties(
        Verblijfadresingeschrevennatuurlijkpersoon expectedVerblijfadresingeschrevennatuurlijkpersoon
    ) {
        assertVerblijfadresingeschrevennatuurlijkpersoonAllPropertiesEquals(
            expectedVerblijfadresingeschrevennatuurlijkpersoon,
            getPersistedVerblijfadresingeschrevennatuurlijkpersoon(expectedVerblijfadresingeschrevennatuurlijkpersoon)
        );
    }

    protected void assertPersistedVerblijfadresingeschrevennatuurlijkpersoonToMatchUpdatableProperties(
        Verblijfadresingeschrevennatuurlijkpersoon expectedVerblijfadresingeschrevennatuurlijkpersoon
    ) {
        assertVerblijfadresingeschrevennatuurlijkpersoonAllUpdatablePropertiesEquals(
            expectedVerblijfadresingeschrevennatuurlijkpersoon,
            getPersistedVerblijfadresingeschrevennatuurlijkpersoon(expectedVerblijfadresingeschrevennatuurlijkpersoon)
        );
    }
}
