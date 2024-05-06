package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GeboorteingeschrevenpersoonAsserts.*;
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
import nl.ritense.demo.domain.Geboorteingeschrevenpersoon;
import nl.ritense.demo.repository.GeboorteingeschrevenpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GeboorteingeschrevenpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GeboorteingeschrevenpersoonResourceIT {

    private static final String DEFAULT_DATUMGEBOORTE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMGEBOORTE = "BBBBBBBBBB";

    private static final String DEFAULT_GEBOORTELAND = "AAAAAAAAAA";
    private static final String UPDATED_GEBOORTELAND = "BBBBBBBBBB";

    private static final String DEFAULT_GEBOORTEPLAATS = "AAAAAAAAAA";
    private static final String UPDATED_GEBOORTEPLAATS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/geboorteingeschrevenpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GeboorteingeschrevenpersoonRepository geboorteingeschrevenpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeboorteingeschrevenpersoonMockMvc;

    private Geboorteingeschrevenpersoon geboorteingeschrevenpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Geboorteingeschrevenpersoon createEntity(EntityManager em) {
        Geboorteingeschrevenpersoon geboorteingeschrevenpersoon = new Geboorteingeschrevenpersoon()
            .datumgeboorte(DEFAULT_DATUMGEBOORTE)
            .geboorteland(DEFAULT_GEBOORTELAND)
            .geboorteplaats(DEFAULT_GEBOORTEPLAATS);
        return geboorteingeschrevenpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Geboorteingeschrevenpersoon createUpdatedEntity(EntityManager em) {
        Geboorteingeschrevenpersoon geboorteingeschrevenpersoon = new Geboorteingeschrevenpersoon()
            .datumgeboorte(UPDATED_DATUMGEBOORTE)
            .geboorteland(UPDATED_GEBOORTELAND)
            .geboorteplaats(UPDATED_GEBOORTEPLAATS);
        return geboorteingeschrevenpersoon;
    }

    @BeforeEach
    public void initTest() {
        geboorteingeschrevenpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createGeboorteingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Geboorteingeschrevenpersoon
        var returnedGeboorteingeschrevenpersoon = om.readValue(
            restGeboorteingeschrevenpersoonMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(geboorteingeschrevenpersoon))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Geboorteingeschrevenpersoon.class
        );

        // Validate the Geboorteingeschrevenpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGeboorteingeschrevenpersoonUpdatableFieldsEquals(
            returnedGeboorteingeschrevenpersoon,
            getPersistedGeboorteingeschrevenpersoon(returnedGeboorteingeschrevenpersoon)
        );
    }

    @Test
    @Transactional
    void createGeboorteingeschrevenpersoonWithExistingId() throws Exception {
        // Create the Geboorteingeschrevenpersoon with an existing ID
        geboorteingeschrevenpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeboorteingeschrevenpersoonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(geboorteingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geboorteingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGeboorteingeschrevenpersoons() throws Exception {
        // Initialize the database
        geboorteingeschrevenpersoonRepository.saveAndFlush(geboorteingeschrevenpersoon);

        // Get all the geboorteingeschrevenpersoonList
        restGeboorteingeschrevenpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geboorteingeschrevenpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumgeboorte").value(hasItem(DEFAULT_DATUMGEBOORTE)))
            .andExpect(jsonPath("$.[*].geboorteland").value(hasItem(DEFAULT_GEBOORTELAND)))
            .andExpect(jsonPath("$.[*].geboorteplaats").value(hasItem(DEFAULT_GEBOORTEPLAATS)));
    }

    @Test
    @Transactional
    void getGeboorteingeschrevenpersoon() throws Exception {
        // Initialize the database
        geboorteingeschrevenpersoonRepository.saveAndFlush(geboorteingeschrevenpersoon);

        // Get the geboorteingeschrevenpersoon
        restGeboorteingeschrevenpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, geboorteingeschrevenpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(geboorteingeschrevenpersoon.getId().intValue()))
            .andExpect(jsonPath("$.datumgeboorte").value(DEFAULT_DATUMGEBOORTE))
            .andExpect(jsonPath("$.geboorteland").value(DEFAULT_GEBOORTELAND))
            .andExpect(jsonPath("$.geboorteplaats").value(DEFAULT_GEBOORTEPLAATS));
    }

    @Test
    @Transactional
    void getNonExistingGeboorteingeschrevenpersoon() throws Exception {
        // Get the geboorteingeschrevenpersoon
        restGeboorteingeschrevenpersoonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGeboorteingeschrevenpersoon() throws Exception {
        // Initialize the database
        geboorteingeschrevenpersoonRepository.saveAndFlush(geboorteingeschrevenpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the geboorteingeschrevenpersoon
        Geboorteingeschrevenpersoon updatedGeboorteingeschrevenpersoon = geboorteingeschrevenpersoonRepository
            .findById(geboorteingeschrevenpersoon.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedGeboorteingeschrevenpersoon are not directly saved in db
        em.detach(updatedGeboorteingeschrevenpersoon);
        updatedGeboorteingeschrevenpersoon
            .datumgeboorte(UPDATED_DATUMGEBOORTE)
            .geboorteland(UPDATED_GEBOORTELAND)
            .geboorteplaats(UPDATED_GEBOORTEPLAATS);

        restGeboorteingeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGeboorteingeschrevenpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGeboorteingeschrevenpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Geboorteingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGeboorteingeschrevenpersoonToMatchAllProperties(updatedGeboorteingeschrevenpersoon);
    }

    @Test
    @Transactional
    void putNonExistingGeboorteingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geboorteingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeboorteingeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, geboorteingeschrevenpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(geboorteingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geboorteingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGeboorteingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geboorteingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeboorteingeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(geboorteingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geboorteingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGeboorteingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geboorteingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeboorteingeschrevenpersoonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(geboorteingeschrevenpersoon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Geboorteingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGeboorteingeschrevenpersoonWithPatch() throws Exception {
        // Initialize the database
        geboorteingeschrevenpersoonRepository.saveAndFlush(geboorteingeschrevenpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the geboorteingeschrevenpersoon using partial update
        Geboorteingeschrevenpersoon partialUpdatedGeboorteingeschrevenpersoon = new Geboorteingeschrevenpersoon();
        partialUpdatedGeboorteingeschrevenpersoon.setId(geboorteingeschrevenpersoon.getId());

        partialUpdatedGeboorteingeschrevenpersoon.datumgeboorte(UPDATED_DATUMGEBOORTE).geboorteland(UPDATED_GEBOORTELAND);

        restGeboorteingeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeboorteingeschrevenpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGeboorteingeschrevenpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Geboorteingeschrevenpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGeboorteingeschrevenpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGeboorteingeschrevenpersoon, geboorteingeschrevenpersoon),
            getPersistedGeboorteingeschrevenpersoon(geboorteingeschrevenpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateGeboorteingeschrevenpersoonWithPatch() throws Exception {
        // Initialize the database
        geboorteingeschrevenpersoonRepository.saveAndFlush(geboorteingeschrevenpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the geboorteingeschrevenpersoon using partial update
        Geboorteingeschrevenpersoon partialUpdatedGeboorteingeschrevenpersoon = new Geboorteingeschrevenpersoon();
        partialUpdatedGeboorteingeschrevenpersoon.setId(geboorteingeschrevenpersoon.getId());

        partialUpdatedGeboorteingeschrevenpersoon
            .datumgeboorte(UPDATED_DATUMGEBOORTE)
            .geboorteland(UPDATED_GEBOORTELAND)
            .geboorteplaats(UPDATED_GEBOORTEPLAATS);

        restGeboorteingeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeboorteingeschrevenpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGeboorteingeschrevenpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Geboorteingeschrevenpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGeboorteingeschrevenpersoonUpdatableFieldsEquals(
            partialUpdatedGeboorteingeschrevenpersoon,
            getPersistedGeboorteingeschrevenpersoon(partialUpdatedGeboorteingeschrevenpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingGeboorteingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geboorteingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeboorteingeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, geboorteingeschrevenpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(geboorteingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geboorteingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGeboorteingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geboorteingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeboorteingeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(geboorteingeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geboorteingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGeboorteingeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geboorteingeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeboorteingeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(geboorteingeschrevenpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Geboorteingeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGeboorteingeschrevenpersoon() throws Exception {
        // Initialize the database
        geboorteingeschrevenpersoonRepository.saveAndFlush(geboorteingeschrevenpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the geboorteingeschrevenpersoon
        restGeboorteingeschrevenpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, geboorteingeschrevenpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return geboorteingeschrevenpersoonRepository.count();
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

    protected Geboorteingeschrevenpersoon getPersistedGeboorteingeschrevenpersoon(Geboorteingeschrevenpersoon geboorteingeschrevenpersoon) {
        return geboorteingeschrevenpersoonRepository.findById(geboorteingeschrevenpersoon.getId()).orElseThrow();
    }

    protected void assertPersistedGeboorteingeschrevenpersoonToMatchAllProperties(
        Geboorteingeschrevenpersoon expectedGeboorteingeschrevenpersoon
    ) {
        assertGeboorteingeschrevenpersoonAllPropertiesEquals(
            expectedGeboorteingeschrevenpersoon,
            getPersistedGeboorteingeschrevenpersoon(expectedGeboorteingeschrevenpersoon)
        );
    }

    protected void assertPersistedGeboorteingeschrevenpersoonToMatchUpdatableProperties(
        Geboorteingeschrevenpersoon expectedGeboorteingeschrevenpersoon
    ) {
        assertGeboorteingeschrevenpersoonAllUpdatablePropertiesEquals(
            expectedGeboorteingeschrevenpersoon,
            getPersistedGeboorteingeschrevenpersoon(expectedGeboorteingeschrevenpersoon)
        );
    }
}
