package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SamengesteldenaamnatuurlijkpersoonAsserts.*;
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
import nl.ritense.demo.domain.Samengesteldenaamnatuurlijkpersoon;
import nl.ritense.demo.repository.SamengesteldenaamnatuurlijkpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SamengesteldenaamnatuurlijkpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SamengesteldenaamnatuurlijkpersoonResourceIT {

    private static final String DEFAULT_ADELLIJKETITEL = "AAAAAAAAAA";
    private static final String UPDATED_ADELLIJKETITEL = "BBBBBBBBBB";

    private static final String DEFAULT_GESLACHTSNAAMSTAM = "AAAAAAAAAA";
    private static final String UPDATED_GESLACHTSNAAMSTAM = "BBBBBBBBBB";

    private static final String DEFAULT_NAMENREEKS = "AAAAAAAAAA";
    private static final String UPDATED_NAMENREEKS = "BBBBBBBBBB";

    private static final String DEFAULT_PREDICAAT = "AAAAAAAAAA";
    private static final String UPDATED_PREDICAAT = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEIDINGSTEKEN = "AAAAAAAAAA";
    private static final String UPDATED_SCHEIDINGSTEKEN = "BBBBBBBBBB";

    private static final String DEFAULT_VOORNAMEN = "AAAAAAAAAA";
    private static final String UPDATED_VOORNAMEN = "BBBBBBBBBB";

    private static final String DEFAULT_VOORVOEGSEL = "AAAAAAAAAA";
    private static final String UPDATED_VOORVOEGSEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/samengesteldenaamnatuurlijkpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SamengesteldenaamnatuurlijkpersoonRepository samengesteldenaamnatuurlijkpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSamengesteldenaamnatuurlijkpersoonMockMvc;

    private Samengesteldenaamnatuurlijkpersoon samengesteldenaamnatuurlijkpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Samengesteldenaamnatuurlijkpersoon createEntity(EntityManager em) {
        Samengesteldenaamnatuurlijkpersoon samengesteldenaamnatuurlijkpersoon = new Samengesteldenaamnatuurlijkpersoon()
            .adellijketitel(DEFAULT_ADELLIJKETITEL)
            .geslachtsnaamstam(DEFAULT_GESLACHTSNAAMSTAM)
            .namenreeks(DEFAULT_NAMENREEKS)
            .predicaat(DEFAULT_PREDICAAT)
            .scheidingsteken(DEFAULT_SCHEIDINGSTEKEN)
            .voornamen(DEFAULT_VOORNAMEN)
            .voorvoegsel(DEFAULT_VOORVOEGSEL);
        return samengesteldenaamnatuurlijkpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Samengesteldenaamnatuurlijkpersoon createUpdatedEntity(EntityManager em) {
        Samengesteldenaamnatuurlijkpersoon samengesteldenaamnatuurlijkpersoon = new Samengesteldenaamnatuurlijkpersoon()
            .adellijketitel(UPDATED_ADELLIJKETITEL)
            .geslachtsnaamstam(UPDATED_GESLACHTSNAAMSTAM)
            .namenreeks(UPDATED_NAMENREEKS)
            .predicaat(UPDATED_PREDICAAT)
            .scheidingsteken(UPDATED_SCHEIDINGSTEKEN)
            .voornamen(UPDATED_VOORNAMEN)
            .voorvoegsel(UPDATED_VOORVOEGSEL);
        return samengesteldenaamnatuurlijkpersoon;
    }

    @BeforeEach
    public void initTest() {
        samengesteldenaamnatuurlijkpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createSamengesteldenaamnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Samengesteldenaamnatuurlijkpersoon
        var returnedSamengesteldenaamnatuurlijkpersoon = om.readValue(
            restSamengesteldenaamnatuurlijkpersoonMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(samengesteldenaamnatuurlijkpersoon))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Samengesteldenaamnatuurlijkpersoon.class
        );

        // Validate the Samengesteldenaamnatuurlijkpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSamengesteldenaamnatuurlijkpersoonUpdatableFieldsEquals(
            returnedSamengesteldenaamnatuurlijkpersoon,
            getPersistedSamengesteldenaamnatuurlijkpersoon(returnedSamengesteldenaamnatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void createSamengesteldenaamnatuurlijkpersoonWithExistingId() throws Exception {
        // Create the Samengesteldenaamnatuurlijkpersoon with an existing ID
        samengesteldenaamnatuurlijkpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSamengesteldenaamnatuurlijkpersoonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(samengesteldenaamnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Samengesteldenaamnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSamengesteldenaamnatuurlijkpersoons() throws Exception {
        // Initialize the database
        samengesteldenaamnatuurlijkpersoonRepository.saveAndFlush(samengesteldenaamnatuurlijkpersoon);

        // Get all the samengesteldenaamnatuurlijkpersoonList
        restSamengesteldenaamnatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(samengesteldenaamnatuurlijkpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].adellijketitel").value(hasItem(DEFAULT_ADELLIJKETITEL)))
            .andExpect(jsonPath("$.[*].geslachtsnaamstam").value(hasItem(DEFAULT_GESLACHTSNAAMSTAM)))
            .andExpect(jsonPath("$.[*].namenreeks").value(hasItem(DEFAULT_NAMENREEKS)))
            .andExpect(jsonPath("$.[*].predicaat").value(hasItem(DEFAULT_PREDICAAT)))
            .andExpect(jsonPath("$.[*].scheidingsteken").value(hasItem(DEFAULT_SCHEIDINGSTEKEN)))
            .andExpect(jsonPath("$.[*].voornamen").value(hasItem(DEFAULT_VOORNAMEN)))
            .andExpect(jsonPath("$.[*].voorvoegsel").value(hasItem(DEFAULT_VOORVOEGSEL)));
    }

    @Test
    @Transactional
    void getSamengesteldenaamnatuurlijkpersoon() throws Exception {
        // Initialize the database
        samengesteldenaamnatuurlijkpersoonRepository.saveAndFlush(samengesteldenaamnatuurlijkpersoon);

        // Get the samengesteldenaamnatuurlijkpersoon
        restSamengesteldenaamnatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, samengesteldenaamnatuurlijkpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(samengesteldenaamnatuurlijkpersoon.getId().intValue()))
            .andExpect(jsonPath("$.adellijketitel").value(DEFAULT_ADELLIJKETITEL))
            .andExpect(jsonPath("$.geslachtsnaamstam").value(DEFAULT_GESLACHTSNAAMSTAM))
            .andExpect(jsonPath("$.namenreeks").value(DEFAULT_NAMENREEKS))
            .andExpect(jsonPath("$.predicaat").value(DEFAULT_PREDICAAT))
            .andExpect(jsonPath("$.scheidingsteken").value(DEFAULT_SCHEIDINGSTEKEN))
            .andExpect(jsonPath("$.voornamen").value(DEFAULT_VOORNAMEN))
            .andExpect(jsonPath("$.voorvoegsel").value(DEFAULT_VOORVOEGSEL));
    }

    @Test
    @Transactional
    void getNonExistingSamengesteldenaamnatuurlijkpersoon() throws Exception {
        // Get the samengesteldenaamnatuurlijkpersoon
        restSamengesteldenaamnatuurlijkpersoonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSamengesteldenaamnatuurlijkpersoon() throws Exception {
        // Initialize the database
        samengesteldenaamnatuurlijkpersoonRepository.saveAndFlush(samengesteldenaamnatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the samengesteldenaamnatuurlijkpersoon
        Samengesteldenaamnatuurlijkpersoon updatedSamengesteldenaamnatuurlijkpersoon = samengesteldenaamnatuurlijkpersoonRepository
            .findById(samengesteldenaamnatuurlijkpersoon.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSamengesteldenaamnatuurlijkpersoon are not directly saved in db
        em.detach(updatedSamengesteldenaamnatuurlijkpersoon);
        updatedSamengesteldenaamnatuurlijkpersoon
            .adellijketitel(UPDATED_ADELLIJKETITEL)
            .geslachtsnaamstam(UPDATED_GESLACHTSNAAMSTAM)
            .namenreeks(UPDATED_NAMENREEKS)
            .predicaat(UPDATED_PREDICAAT)
            .scheidingsteken(UPDATED_SCHEIDINGSTEKEN)
            .voornamen(UPDATED_VOORNAMEN)
            .voorvoegsel(UPDATED_VOORVOEGSEL);

        restSamengesteldenaamnatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSamengesteldenaamnatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSamengesteldenaamnatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Samengesteldenaamnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSamengesteldenaamnatuurlijkpersoonToMatchAllProperties(updatedSamengesteldenaamnatuurlijkpersoon);
    }

    @Test
    @Transactional
    void putNonExistingSamengesteldenaamnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        samengesteldenaamnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSamengesteldenaamnatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, samengesteldenaamnatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(samengesteldenaamnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Samengesteldenaamnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSamengesteldenaamnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        samengesteldenaamnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSamengesteldenaamnatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(samengesteldenaamnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Samengesteldenaamnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSamengesteldenaamnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        samengesteldenaamnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSamengesteldenaamnatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(samengesteldenaamnatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Samengesteldenaamnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSamengesteldenaamnatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        samengesteldenaamnatuurlijkpersoonRepository.saveAndFlush(samengesteldenaamnatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the samengesteldenaamnatuurlijkpersoon using partial update
        Samengesteldenaamnatuurlijkpersoon partialUpdatedSamengesteldenaamnatuurlijkpersoon = new Samengesteldenaamnatuurlijkpersoon();
        partialUpdatedSamengesteldenaamnatuurlijkpersoon.setId(samengesteldenaamnatuurlijkpersoon.getId());

        partialUpdatedSamengesteldenaamnatuurlijkpersoon
            .namenreeks(UPDATED_NAMENREEKS)
            .scheidingsteken(UPDATED_SCHEIDINGSTEKEN)
            .voornamen(UPDATED_VOORNAMEN);

        restSamengesteldenaamnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSamengesteldenaamnatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSamengesteldenaamnatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Samengesteldenaamnatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSamengesteldenaamnatuurlijkpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSamengesteldenaamnatuurlijkpersoon, samengesteldenaamnatuurlijkpersoon),
            getPersistedSamengesteldenaamnatuurlijkpersoon(samengesteldenaamnatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateSamengesteldenaamnatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        samengesteldenaamnatuurlijkpersoonRepository.saveAndFlush(samengesteldenaamnatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the samengesteldenaamnatuurlijkpersoon using partial update
        Samengesteldenaamnatuurlijkpersoon partialUpdatedSamengesteldenaamnatuurlijkpersoon = new Samengesteldenaamnatuurlijkpersoon();
        partialUpdatedSamengesteldenaamnatuurlijkpersoon.setId(samengesteldenaamnatuurlijkpersoon.getId());

        partialUpdatedSamengesteldenaamnatuurlijkpersoon
            .adellijketitel(UPDATED_ADELLIJKETITEL)
            .geslachtsnaamstam(UPDATED_GESLACHTSNAAMSTAM)
            .namenreeks(UPDATED_NAMENREEKS)
            .predicaat(UPDATED_PREDICAAT)
            .scheidingsteken(UPDATED_SCHEIDINGSTEKEN)
            .voornamen(UPDATED_VOORNAMEN)
            .voorvoegsel(UPDATED_VOORVOEGSEL);

        restSamengesteldenaamnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSamengesteldenaamnatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSamengesteldenaamnatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Samengesteldenaamnatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSamengesteldenaamnatuurlijkpersoonUpdatableFieldsEquals(
            partialUpdatedSamengesteldenaamnatuurlijkpersoon,
            getPersistedSamengesteldenaamnatuurlijkpersoon(partialUpdatedSamengesteldenaamnatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSamengesteldenaamnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        samengesteldenaamnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSamengesteldenaamnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, samengesteldenaamnatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(samengesteldenaamnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Samengesteldenaamnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSamengesteldenaamnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        samengesteldenaamnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSamengesteldenaamnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(samengesteldenaamnatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Samengesteldenaamnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSamengesteldenaamnatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        samengesteldenaamnatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSamengesteldenaamnatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(samengesteldenaamnatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Samengesteldenaamnatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSamengesteldenaamnatuurlijkpersoon() throws Exception {
        // Initialize the database
        samengesteldenaamnatuurlijkpersoonRepository.saveAndFlush(samengesteldenaamnatuurlijkpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the samengesteldenaamnatuurlijkpersoon
        restSamengesteldenaamnatuurlijkpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, samengesteldenaamnatuurlijkpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return samengesteldenaamnatuurlijkpersoonRepository.count();
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

    protected Samengesteldenaamnatuurlijkpersoon getPersistedSamengesteldenaamnatuurlijkpersoon(
        Samengesteldenaamnatuurlijkpersoon samengesteldenaamnatuurlijkpersoon
    ) {
        return samengesteldenaamnatuurlijkpersoonRepository.findById(samengesteldenaamnatuurlijkpersoon.getId()).orElseThrow();
    }

    protected void assertPersistedSamengesteldenaamnatuurlijkpersoonToMatchAllProperties(
        Samengesteldenaamnatuurlijkpersoon expectedSamengesteldenaamnatuurlijkpersoon
    ) {
        assertSamengesteldenaamnatuurlijkpersoonAllPropertiesEquals(
            expectedSamengesteldenaamnatuurlijkpersoon,
            getPersistedSamengesteldenaamnatuurlijkpersoon(expectedSamengesteldenaamnatuurlijkpersoon)
        );
    }

    protected void assertPersistedSamengesteldenaamnatuurlijkpersoonToMatchUpdatableProperties(
        Samengesteldenaamnatuurlijkpersoon expectedSamengesteldenaamnatuurlijkpersoon
    ) {
        assertSamengesteldenaamnatuurlijkpersoonAllUpdatablePropertiesEquals(
            expectedSamengesteldenaamnatuurlijkpersoon,
            getPersistedSamengesteldenaamnatuurlijkpersoon(expectedSamengesteldenaamnatuurlijkpersoon)
        );
    }
}
