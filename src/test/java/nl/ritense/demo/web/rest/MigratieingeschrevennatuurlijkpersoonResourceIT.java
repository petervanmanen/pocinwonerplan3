package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MigratieingeschrevennatuurlijkpersoonAsserts.*;
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
import nl.ritense.demo.domain.Migratieingeschrevennatuurlijkpersoon;
import nl.ritense.demo.repository.MigratieingeschrevennatuurlijkpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MigratieingeschrevennatuurlijkpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MigratieingeschrevennatuurlijkpersoonResourceIT {

    private static final String DEFAULT_AANGEVERMIGRATIE = "AAAAAAAAAA";
    private static final String UPDATED_AANGEVERMIGRATIE = "BBBBBBBBBB";

    private static final String DEFAULT_REDENWIJZIGINGMIGRATIE = "AAAAAAAAAA";
    private static final String UPDATED_REDENWIJZIGINGMIGRATIE = "BBBBBBBBBB";

    private static final String DEFAULT_SOORTMIGRATIE = "AAAAAAAAAA";
    private static final String UPDATED_SOORTMIGRATIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/migratieingeschrevennatuurlijkpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MigratieingeschrevennatuurlijkpersoonRepository migratieingeschrevennatuurlijkpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMigratieingeschrevennatuurlijkpersoonMockMvc;

    private Migratieingeschrevennatuurlijkpersoon migratieingeschrevennatuurlijkpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Migratieingeschrevennatuurlijkpersoon createEntity(EntityManager em) {
        Migratieingeschrevennatuurlijkpersoon migratieingeschrevennatuurlijkpersoon = new Migratieingeschrevennatuurlijkpersoon()
            .aangevermigratie(DEFAULT_AANGEVERMIGRATIE)
            .redenwijzigingmigratie(DEFAULT_REDENWIJZIGINGMIGRATIE)
            .soortmigratie(DEFAULT_SOORTMIGRATIE);
        return migratieingeschrevennatuurlijkpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Migratieingeschrevennatuurlijkpersoon createUpdatedEntity(EntityManager em) {
        Migratieingeschrevennatuurlijkpersoon migratieingeschrevennatuurlijkpersoon = new Migratieingeschrevennatuurlijkpersoon()
            .aangevermigratie(UPDATED_AANGEVERMIGRATIE)
            .redenwijzigingmigratie(UPDATED_REDENWIJZIGINGMIGRATIE)
            .soortmigratie(UPDATED_SOORTMIGRATIE);
        return migratieingeschrevennatuurlijkpersoon;
    }

    @BeforeEach
    public void initTest() {
        migratieingeschrevennatuurlijkpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createMigratieingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Migratieingeschrevennatuurlijkpersoon
        var returnedMigratieingeschrevennatuurlijkpersoon = om.readValue(
            restMigratieingeschrevennatuurlijkpersoonMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(migratieingeschrevennatuurlijkpersoon))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Migratieingeschrevennatuurlijkpersoon.class
        );

        // Validate the Migratieingeschrevennatuurlijkpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMigratieingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            returnedMigratieingeschrevennatuurlijkpersoon,
            getPersistedMigratieingeschrevennatuurlijkpersoon(returnedMigratieingeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void createMigratieingeschrevennatuurlijkpersoonWithExistingId() throws Exception {
        // Create the Migratieingeschrevennatuurlijkpersoon with an existing ID
        migratieingeschrevennatuurlijkpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMigratieingeschrevennatuurlijkpersoonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(migratieingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Migratieingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMigratieingeschrevennatuurlijkpersoons() throws Exception {
        // Initialize the database
        migratieingeschrevennatuurlijkpersoonRepository.saveAndFlush(migratieingeschrevennatuurlijkpersoon);

        // Get all the migratieingeschrevennatuurlijkpersoonList
        restMigratieingeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(migratieingeschrevennatuurlijkpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].aangevermigratie").value(hasItem(DEFAULT_AANGEVERMIGRATIE)))
            .andExpect(jsonPath("$.[*].redenwijzigingmigratie").value(hasItem(DEFAULT_REDENWIJZIGINGMIGRATIE)))
            .andExpect(jsonPath("$.[*].soortmigratie").value(hasItem(DEFAULT_SOORTMIGRATIE)));
    }

    @Test
    @Transactional
    void getMigratieingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        migratieingeschrevennatuurlijkpersoonRepository.saveAndFlush(migratieingeschrevennatuurlijkpersoon);

        // Get the migratieingeschrevennatuurlijkpersoon
        restMigratieingeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, migratieingeschrevennatuurlijkpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(migratieingeschrevennatuurlijkpersoon.getId().intValue()))
            .andExpect(jsonPath("$.aangevermigratie").value(DEFAULT_AANGEVERMIGRATIE))
            .andExpect(jsonPath("$.redenwijzigingmigratie").value(DEFAULT_REDENWIJZIGINGMIGRATIE))
            .andExpect(jsonPath("$.soortmigratie").value(DEFAULT_SOORTMIGRATIE));
    }

    @Test
    @Transactional
    void getNonExistingMigratieingeschrevennatuurlijkpersoon() throws Exception {
        // Get the migratieingeschrevennatuurlijkpersoon
        restMigratieingeschrevennatuurlijkpersoonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMigratieingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        migratieingeschrevennatuurlijkpersoonRepository.saveAndFlush(migratieingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the migratieingeschrevennatuurlijkpersoon
        Migratieingeschrevennatuurlijkpersoon updatedMigratieingeschrevennatuurlijkpersoon = migratieingeschrevennatuurlijkpersoonRepository
            .findById(migratieingeschrevennatuurlijkpersoon.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedMigratieingeschrevennatuurlijkpersoon are not directly saved in db
        em.detach(updatedMigratieingeschrevennatuurlijkpersoon);
        updatedMigratieingeschrevennatuurlijkpersoon
            .aangevermigratie(UPDATED_AANGEVERMIGRATIE)
            .redenwijzigingmigratie(UPDATED_REDENWIJZIGINGMIGRATIE)
            .soortmigratie(UPDATED_SOORTMIGRATIE);

        restMigratieingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMigratieingeschrevennatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMigratieingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Migratieingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMigratieingeschrevennatuurlijkpersoonToMatchAllProperties(updatedMigratieingeschrevennatuurlijkpersoon);
    }

    @Test
    @Transactional
    void putNonExistingMigratieingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        migratieingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMigratieingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, migratieingeschrevennatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(migratieingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Migratieingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMigratieingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        migratieingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMigratieingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(migratieingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Migratieingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMigratieingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        migratieingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMigratieingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(migratieingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Migratieingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMigratieingeschrevennatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        migratieingeschrevennatuurlijkpersoonRepository.saveAndFlush(migratieingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the migratieingeschrevennatuurlijkpersoon using partial update
        Migratieingeschrevennatuurlijkpersoon partialUpdatedMigratieingeschrevennatuurlijkpersoon =
            new Migratieingeschrevennatuurlijkpersoon();
        partialUpdatedMigratieingeschrevennatuurlijkpersoon.setId(migratieingeschrevennatuurlijkpersoon.getId());

        partialUpdatedMigratieingeschrevennatuurlijkpersoon
            .aangevermigratie(UPDATED_AANGEVERMIGRATIE)
            .redenwijzigingmigratie(UPDATED_REDENWIJZIGINGMIGRATIE)
            .soortmigratie(UPDATED_SOORTMIGRATIE);

        restMigratieingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMigratieingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMigratieingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Migratieingeschrevennatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMigratieingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMigratieingeschrevennatuurlijkpersoon, migratieingeschrevennatuurlijkpersoon),
            getPersistedMigratieingeschrevennatuurlijkpersoon(migratieingeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateMigratieingeschrevennatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        migratieingeschrevennatuurlijkpersoonRepository.saveAndFlush(migratieingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the migratieingeschrevennatuurlijkpersoon using partial update
        Migratieingeschrevennatuurlijkpersoon partialUpdatedMigratieingeschrevennatuurlijkpersoon =
            new Migratieingeschrevennatuurlijkpersoon();
        partialUpdatedMigratieingeschrevennatuurlijkpersoon.setId(migratieingeschrevennatuurlijkpersoon.getId());

        partialUpdatedMigratieingeschrevennatuurlijkpersoon
            .aangevermigratie(UPDATED_AANGEVERMIGRATIE)
            .redenwijzigingmigratie(UPDATED_REDENWIJZIGINGMIGRATIE)
            .soortmigratie(UPDATED_SOORTMIGRATIE);

        restMigratieingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMigratieingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMigratieingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Migratieingeschrevennatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMigratieingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            partialUpdatedMigratieingeschrevennatuurlijkpersoon,
            getPersistedMigratieingeschrevennatuurlijkpersoon(partialUpdatedMigratieingeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMigratieingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        migratieingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMigratieingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, migratieingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(migratieingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Migratieingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMigratieingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        migratieingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMigratieingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(migratieingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Migratieingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMigratieingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        migratieingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMigratieingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(migratieingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Migratieingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMigratieingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        migratieingeschrevennatuurlijkpersoonRepository.saveAndFlush(migratieingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the migratieingeschrevennatuurlijkpersoon
        restMigratieingeschrevennatuurlijkpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, migratieingeschrevennatuurlijkpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return migratieingeschrevennatuurlijkpersoonRepository.count();
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

    protected Migratieingeschrevennatuurlijkpersoon getPersistedMigratieingeschrevennatuurlijkpersoon(
        Migratieingeschrevennatuurlijkpersoon migratieingeschrevennatuurlijkpersoon
    ) {
        return migratieingeschrevennatuurlijkpersoonRepository.findById(migratieingeschrevennatuurlijkpersoon.getId()).orElseThrow();
    }

    protected void assertPersistedMigratieingeschrevennatuurlijkpersoonToMatchAllProperties(
        Migratieingeschrevennatuurlijkpersoon expectedMigratieingeschrevennatuurlijkpersoon
    ) {
        assertMigratieingeschrevennatuurlijkpersoonAllPropertiesEquals(
            expectedMigratieingeschrevennatuurlijkpersoon,
            getPersistedMigratieingeschrevennatuurlijkpersoon(expectedMigratieingeschrevennatuurlijkpersoon)
        );
    }

    protected void assertPersistedMigratieingeschrevennatuurlijkpersoonToMatchUpdatableProperties(
        Migratieingeschrevennatuurlijkpersoon expectedMigratieingeschrevennatuurlijkpersoon
    ) {
        assertMigratieingeschrevennatuurlijkpersoonAllUpdatablePropertiesEquals(
            expectedMigratieingeschrevennatuurlijkpersoon,
            getPersistedMigratieingeschrevennatuurlijkpersoon(expectedMigratieingeschrevennatuurlijkpersoon)
        );
    }
}
