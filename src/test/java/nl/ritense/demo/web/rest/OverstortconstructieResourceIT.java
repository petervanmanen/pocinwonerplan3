package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OverstortconstructieAsserts.*;
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
import nl.ritense.demo.domain.Overstortconstructie;
import nl.ritense.demo.repository.OverstortconstructieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OverstortconstructieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OverstortconstructieResourceIT {

    private static final String DEFAULT_BASSIN = "AAAAAAAAAA";
    private static final String UPDATED_BASSIN = "BBBBBBBBBB";

    private static final String DEFAULT_DREMPELBREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_DREMPELBREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_DREMPELNIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_DREMPELNIVEAU = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KLEP = false;
    private static final Boolean UPDATED_KLEP = true;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VORMDREMPEL = "AAAAAAAAAA";
    private static final String UPDATED_VORMDREMPEL = "BBBBBBBBBB";

    private static final String DEFAULT_WAKING = "AAAAAAAAAA";
    private static final String UPDATED_WAKING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/overstortconstructies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OverstortconstructieRepository overstortconstructieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOverstortconstructieMockMvc;

    private Overstortconstructie overstortconstructie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overstortconstructie createEntity(EntityManager em) {
        Overstortconstructie overstortconstructie = new Overstortconstructie()
            .bassin(DEFAULT_BASSIN)
            .drempelbreedte(DEFAULT_DREMPELBREEDTE)
            .drempelniveau(DEFAULT_DREMPELNIVEAU)
            .klep(DEFAULT_KLEP)
            .type(DEFAULT_TYPE)
            .vormdrempel(DEFAULT_VORMDREMPEL)
            .waking(DEFAULT_WAKING);
        return overstortconstructie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overstortconstructie createUpdatedEntity(EntityManager em) {
        Overstortconstructie overstortconstructie = new Overstortconstructie()
            .bassin(UPDATED_BASSIN)
            .drempelbreedte(UPDATED_DREMPELBREEDTE)
            .drempelniveau(UPDATED_DREMPELNIVEAU)
            .klep(UPDATED_KLEP)
            .type(UPDATED_TYPE)
            .vormdrempel(UPDATED_VORMDREMPEL)
            .waking(UPDATED_WAKING);
        return overstortconstructie;
    }

    @BeforeEach
    public void initTest() {
        overstortconstructie = createEntity(em);
    }

    @Test
    @Transactional
    void createOverstortconstructie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Overstortconstructie
        var returnedOverstortconstructie = om.readValue(
            restOverstortconstructieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overstortconstructie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Overstortconstructie.class
        );

        // Validate the Overstortconstructie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOverstortconstructieUpdatableFieldsEquals(
            returnedOverstortconstructie,
            getPersistedOverstortconstructie(returnedOverstortconstructie)
        );
    }

    @Test
    @Transactional
    void createOverstortconstructieWithExistingId() throws Exception {
        // Create the Overstortconstructie with an existing ID
        overstortconstructie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOverstortconstructieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overstortconstructie)))
            .andExpect(status().isBadRequest());

        // Validate the Overstortconstructie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOverstortconstructies() throws Exception {
        // Initialize the database
        overstortconstructieRepository.saveAndFlush(overstortconstructie);

        // Get all the overstortconstructieList
        restOverstortconstructieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overstortconstructie.getId().intValue())))
            .andExpect(jsonPath("$.[*].bassin").value(hasItem(DEFAULT_BASSIN)))
            .andExpect(jsonPath("$.[*].drempelbreedte").value(hasItem(DEFAULT_DREMPELBREEDTE)))
            .andExpect(jsonPath("$.[*].drempelniveau").value(hasItem(DEFAULT_DREMPELNIVEAU)))
            .andExpect(jsonPath("$.[*].klep").value(hasItem(DEFAULT_KLEP.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].vormdrempel").value(hasItem(DEFAULT_VORMDREMPEL)))
            .andExpect(jsonPath("$.[*].waking").value(hasItem(DEFAULT_WAKING)));
    }

    @Test
    @Transactional
    void getOverstortconstructie() throws Exception {
        // Initialize the database
        overstortconstructieRepository.saveAndFlush(overstortconstructie);

        // Get the overstortconstructie
        restOverstortconstructieMockMvc
            .perform(get(ENTITY_API_URL_ID, overstortconstructie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(overstortconstructie.getId().intValue()))
            .andExpect(jsonPath("$.bassin").value(DEFAULT_BASSIN))
            .andExpect(jsonPath("$.drempelbreedte").value(DEFAULT_DREMPELBREEDTE))
            .andExpect(jsonPath("$.drempelniveau").value(DEFAULT_DREMPELNIVEAU))
            .andExpect(jsonPath("$.klep").value(DEFAULT_KLEP.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.vormdrempel").value(DEFAULT_VORMDREMPEL))
            .andExpect(jsonPath("$.waking").value(DEFAULT_WAKING));
    }

    @Test
    @Transactional
    void getNonExistingOverstortconstructie() throws Exception {
        // Get the overstortconstructie
        restOverstortconstructieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOverstortconstructie() throws Exception {
        // Initialize the database
        overstortconstructieRepository.saveAndFlush(overstortconstructie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overstortconstructie
        Overstortconstructie updatedOverstortconstructie = overstortconstructieRepository
            .findById(overstortconstructie.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOverstortconstructie are not directly saved in db
        em.detach(updatedOverstortconstructie);
        updatedOverstortconstructie
            .bassin(UPDATED_BASSIN)
            .drempelbreedte(UPDATED_DREMPELBREEDTE)
            .drempelniveau(UPDATED_DREMPELNIVEAU)
            .klep(UPDATED_KLEP)
            .type(UPDATED_TYPE)
            .vormdrempel(UPDATED_VORMDREMPEL)
            .waking(UPDATED_WAKING);

        restOverstortconstructieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOverstortconstructie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOverstortconstructie))
            )
            .andExpect(status().isOk());

        // Validate the Overstortconstructie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOverstortconstructieToMatchAllProperties(updatedOverstortconstructie);
    }

    @Test
    @Transactional
    void putNonExistingOverstortconstructie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overstortconstructie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverstortconstructieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, overstortconstructie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overstortconstructie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overstortconstructie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOverstortconstructie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overstortconstructie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverstortconstructieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overstortconstructie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overstortconstructie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOverstortconstructie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overstortconstructie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverstortconstructieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overstortconstructie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overstortconstructie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOverstortconstructieWithPatch() throws Exception {
        // Initialize the database
        overstortconstructieRepository.saveAndFlush(overstortconstructie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overstortconstructie using partial update
        Overstortconstructie partialUpdatedOverstortconstructie = new Overstortconstructie();
        partialUpdatedOverstortconstructie.setId(overstortconstructie.getId());

        partialUpdatedOverstortconstructie
            .bassin(UPDATED_BASSIN)
            .drempelniveau(UPDATED_DREMPELNIVEAU)
            .klep(UPDATED_KLEP)
            .vormdrempel(UPDATED_VORMDREMPEL)
            .waking(UPDATED_WAKING);

        restOverstortconstructieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverstortconstructie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverstortconstructie))
            )
            .andExpect(status().isOk());

        // Validate the Overstortconstructie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverstortconstructieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOverstortconstructie, overstortconstructie),
            getPersistedOverstortconstructie(overstortconstructie)
        );
    }

    @Test
    @Transactional
    void fullUpdateOverstortconstructieWithPatch() throws Exception {
        // Initialize the database
        overstortconstructieRepository.saveAndFlush(overstortconstructie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overstortconstructie using partial update
        Overstortconstructie partialUpdatedOverstortconstructie = new Overstortconstructie();
        partialUpdatedOverstortconstructie.setId(overstortconstructie.getId());

        partialUpdatedOverstortconstructie
            .bassin(UPDATED_BASSIN)
            .drempelbreedte(UPDATED_DREMPELBREEDTE)
            .drempelniveau(UPDATED_DREMPELNIVEAU)
            .klep(UPDATED_KLEP)
            .type(UPDATED_TYPE)
            .vormdrempel(UPDATED_VORMDREMPEL)
            .waking(UPDATED_WAKING);

        restOverstortconstructieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverstortconstructie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverstortconstructie))
            )
            .andExpect(status().isOk());

        // Validate the Overstortconstructie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverstortconstructieUpdatableFieldsEquals(
            partialUpdatedOverstortconstructie,
            getPersistedOverstortconstructie(partialUpdatedOverstortconstructie)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOverstortconstructie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overstortconstructie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverstortconstructieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, overstortconstructie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overstortconstructie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overstortconstructie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOverstortconstructie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overstortconstructie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverstortconstructieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overstortconstructie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overstortconstructie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOverstortconstructie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overstortconstructie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverstortconstructieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(overstortconstructie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overstortconstructie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOverstortconstructie() throws Exception {
        // Initialize the database
        overstortconstructieRepository.saveAndFlush(overstortconstructie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the overstortconstructie
        restOverstortconstructieMockMvc
            .perform(delete(ENTITY_API_URL_ID, overstortconstructie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return overstortconstructieRepository.count();
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

    protected Overstortconstructie getPersistedOverstortconstructie(Overstortconstructie overstortconstructie) {
        return overstortconstructieRepository.findById(overstortconstructie.getId()).orElseThrow();
    }

    protected void assertPersistedOverstortconstructieToMatchAllProperties(Overstortconstructie expectedOverstortconstructie) {
        assertOverstortconstructieAllPropertiesEquals(
            expectedOverstortconstructie,
            getPersistedOverstortconstructie(expectedOverstortconstructie)
        );
    }

    protected void assertPersistedOverstortconstructieToMatchUpdatableProperties(Overstortconstructie expectedOverstortconstructie) {
        assertOverstortconstructieAllUpdatablePropertiesEquals(
            expectedOverstortconstructie,
            getPersistedOverstortconstructie(expectedOverstortconstructie)
        );
    }
}
