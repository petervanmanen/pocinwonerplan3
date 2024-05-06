package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerblijfadresingeschrevenpersoonAsserts.*;
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
import nl.ritense.demo.domain.Verblijfadresingeschrevenpersoon;
import nl.ritense.demo.repository.VerblijfadresingeschrevenpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerblijfadresingeschrevenpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerblijfadresingeschrevenpersoonResourceIT {

    private static final String DEFAULT_ADRESHERKOMST = "AAAAAAAAAA";
    private static final String UPDATED_ADRESHERKOMST = "BBBBBBBBBB";

    private static final String DEFAULT_BESCHRIJVINGLOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_BESCHRIJVINGLOCATIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verblijfadresingeschrevenpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerblijfadresingeschrevenpersoonRepository verblijfadresingeschrevenpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerblijfadresingeschrevenpersoonMockMvc;

    private Verblijfadresingeschrevenpersoon verblijfadresingeschrevenpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfadresingeschrevenpersoon createEntity(EntityManager em) {
        Verblijfadresingeschrevenpersoon verblijfadresingeschrevenpersoon = new Verblijfadresingeschrevenpersoon()
            .adresherkomst(DEFAULT_ADRESHERKOMST)
            .beschrijvinglocatie(DEFAULT_BESCHRIJVINGLOCATIE);
        return verblijfadresingeschrevenpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfadresingeschrevenpersoon createUpdatedEntity(EntityManager em) {
        Verblijfadresingeschrevenpersoon verblijfadresingeschrevenpersoon = new Verblijfadresingeschrevenpersoon()
            .adresherkomst(UPDATED_ADRESHERKOMST)
            .beschrijvinglocatie(UPDATED_BESCHRIJVINGLOCATIE);
        return verblijfadresingeschrevenpersoon;
    }

    @BeforeEach
    public void initTest() {
        verblijfadresingeschrevenpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createVerblijfadresingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verblijfadresingeschrevenpersoon
        var returnedVerblijfadresingeschrevenpersoon = om.readValue(
            restVerblijfadresingeschrevenpersoonMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(verblijfadresingeschrevenpersoon))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verblijfadresingeschrevenpersoon.class
        );

        // Validate the Verblijfadresingeschrevenpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerblijfadresingeschrevenpersoonUpdatableFieldsEquals(
            returnedVerblijfadresingeschrevenpersoon,
            getPersistedVerblijfadresingeschrevenpersoon(returnedVerblijfadresingeschrevenpersoon)
        );
    }

    @Test
    @Transactional
    void createVerblijfadresingeschrevenpersoonWithExistingId() throws Exception {
        // Create the Verblijfadresingeschrevenpersoon with an existing ID
        verblijfadresingeschrevenpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerblijfadresingeschrevenpersoonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfadresingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfadresingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerblijfadresingeschrevenpersoons() throws Exception {
        // Initialize the database
        verblijfadresingeschrevenpersoonRepository.saveAndFlush(verblijfadresingeschrevenpersoon);

        // Get all the verblijfadresingeschrevenpersoonList
        restVerblijfadresingeschrevenpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verblijfadresingeschrevenpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresherkomst").value(hasItem(DEFAULT_ADRESHERKOMST)))
            .andExpect(jsonPath("$.[*].beschrijvinglocatie").value(hasItem(DEFAULT_BESCHRIJVINGLOCATIE)));
    }

    @Test
    @Transactional
    void getVerblijfadresingeschrevenpersoon() throws Exception {
        // Initialize the database
        verblijfadresingeschrevenpersoonRepository.saveAndFlush(verblijfadresingeschrevenpersoon);

        // Get the verblijfadresingeschrevenpersoon
        restVerblijfadresingeschrevenpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, verblijfadresingeschrevenpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verblijfadresingeschrevenpersoon.getId().intValue()))
            .andExpect(jsonPath("$.adresherkomst").value(DEFAULT_ADRESHERKOMST))
            .andExpect(jsonPath("$.beschrijvinglocatie").value(DEFAULT_BESCHRIJVINGLOCATIE));
    }

    @Test
    @Transactional
    void getNonExistingVerblijfadresingeschrevenpersoon() throws Exception {
        // Get the verblijfadresingeschrevenpersoon
        restVerblijfadresingeschrevenpersoonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerblijfadresingeschrevenpersoon() throws Exception {
        // Initialize the database
        verblijfadresingeschrevenpersoonRepository.saveAndFlush(verblijfadresingeschrevenpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfadresingeschrevenpersoon
        Verblijfadresingeschrevenpersoon updatedVerblijfadresingeschrevenpersoon = verblijfadresingeschrevenpersoonRepository
            .findById(verblijfadresingeschrevenpersoon.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedVerblijfadresingeschrevenpersoon are not directly saved in db
        em.detach(updatedVerblijfadresingeschrevenpersoon);
        updatedVerblijfadresingeschrevenpersoon.adresherkomst(UPDATED_ADRESHERKOMST).beschrijvinglocatie(UPDATED_BESCHRIJVINGLOCATIE);

        restVerblijfadresingeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerblijfadresingeschrevenpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerblijfadresingeschrevenpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfadresingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerblijfadresingeschrevenpersoonToMatchAllProperties(updatedVerblijfadresingeschrevenpersoon);
    }

    @Test
    @Transactional
    void putNonExistingVerblijfadresingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfadresingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerblijfadresingeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verblijfadresingeschrevenpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfadresingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfadresingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerblijfadresingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfadresingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfadresingeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfadresingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfadresingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerblijfadresingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfadresingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfadresingeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfadresingeschrevenpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verblijfadresingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerblijfadresingeschrevenpersoonWithPatch() throws Exception {
        // Initialize the database
        verblijfadresingeschrevenpersoonRepository.saveAndFlush(verblijfadresingeschrevenpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfadresingeschrevenpersoon using partial update
        Verblijfadresingeschrevenpersoon partialUpdatedVerblijfadresingeschrevenpersoon = new Verblijfadresingeschrevenpersoon();
        partialUpdatedVerblijfadresingeschrevenpersoon.setId(verblijfadresingeschrevenpersoon.getId());

        restVerblijfadresingeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerblijfadresingeschrevenpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerblijfadresingeschrevenpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfadresingeschrevenpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerblijfadresingeschrevenpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerblijfadresingeschrevenpersoon, verblijfadresingeschrevenpersoon),
            getPersistedVerblijfadresingeschrevenpersoon(verblijfadresingeschrevenpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerblijfadresingeschrevenpersoonWithPatch() throws Exception {
        // Initialize the database
        verblijfadresingeschrevenpersoonRepository.saveAndFlush(verblijfadresingeschrevenpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfadresingeschrevenpersoon using partial update
        Verblijfadresingeschrevenpersoon partialUpdatedVerblijfadresingeschrevenpersoon = new Verblijfadresingeschrevenpersoon();
        partialUpdatedVerblijfadresingeschrevenpersoon.setId(verblijfadresingeschrevenpersoon.getId());

        partialUpdatedVerblijfadresingeschrevenpersoon
            .adresherkomst(UPDATED_ADRESHERKOMST)
            .beschrijvinglocatie(UPDATED_BESCHRIJVINGLOCATIE);

        restVerblijfadresingeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerblijfadresingeschrevenpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerblijfadresingeschrevenpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfadresingeschrevenpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerblijfadresingeschrevenpersoonUpdatableFieldsEquals(
            partialUpdatedVerblijfadresingeschrevenpersoon,
            getPersistedVerblijfadresingeschrevenpersoon(partialUpdatedVerblijfadresingeschrevenpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVerblijfadresingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfadresingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerblijfadresingeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verblijfadresingeschrevenpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verblijfadresingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfadresingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerblijfadresingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfadresingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfadresingeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verblijfadresingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfadresingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerblijfadresingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfadresingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfadresingeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verblijfadresingeschrevenpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verblijfadresingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerblijfadresingeschrevenpersoon() throws Exception {
        // Initialize the database
        verblijfadresingeschrevenpersoonRepository.saveAndFlush(verblijfadresingeschrevenpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verblijfadresingeschrevenpersoon
        restVerblijfadresingeschrevenpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, verblijfadresingeschrevenpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verblijfadresingeschrevenpersoonRepository.count();
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

    protected Verblijfadresingeschrevenpersoon getPersistedVerblijfadresingeschrevenpersoon(
        Verblijfadresingeschrevenpersoon verblijfadresingeschrevenpersoon
    ) {
        return verblijfadresingeschrevenpersoonRepository.findById(verblijfadresingeschrevenpersoon.getId()).orElseThrow();
    }

    protected void assertPersistedVerblijfadresingeschrevenpersoonToMatchAllProperties(
        Verblijfadresingeschrevenpersoon expectedVerblijfadresingeschrevenpersoon
    ) {
        assertVerblijfadresingeschrevenpersoonAllPropertiesEquals(
            expectedVerblijfadresingeschrevenpersoon,
            getPersistedVerblijfadresingeschrevenpersoon(expectedVerblijfadresingeschrevenpersoon)
        );
    }

    protected void assertPersistedVerblijfadresingeschrevenpersoonToMatchUpdatableProperties(
        Verblijfadresingeschrevenpersoon expectedVerblijfadresingeschrevenpersoon
    ) {
        assertVerblijfadresingeschrevenpersoonAllUpdatablePropertiesEquals(
            expectedVerblijfadresingeschrevenpersoon,
            getPersistedVerblijfadresingeschrevenpersoon(expectedVerblijfadresingeschrevenpersoon)
        );
    }
}
