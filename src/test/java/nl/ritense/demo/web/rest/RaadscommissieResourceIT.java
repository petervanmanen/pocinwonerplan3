package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RaadscommissieAsserts.*;
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
import nl.ritense.demo.domain.Raadscommissie;
import nl.ritense.demo.repository.RaadscommissieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RaadscommissieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RaadscommissieResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/raadscommissies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RaadscommissieRepository raadscommissieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRaadscommissieMockMvc;

    private Raadscommissie raadscommissie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Raadscommissie createEntity(EntityManager em) {
        Raadscommissie raadscommissie = new Raadscommissie().naam(DEFAULT_NAAM);
        return raadscommissie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Raadscommissie createUpdatedEntity(EntityManager em) {
        Raadscommissie raadscommissie = new Raadscommissie().naam(UPDATED_NAAM);
        return raadscommissie;
    }

    @BeforeEach
    public void initTest() {
        raadscommissie = createEntity(em);
    }

    @Test
    @Transactional
    void createRaadscommissie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Raadscommissie
        var returnedRaadscommissie = om.readValue(
            restRaadscommissieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(raadscommissie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Raadscommissie.class
        );

        // Validate the Raadscommissie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRaadscommissieUpdatableFieldsEquals(returnedRaadscommissie, getPersistedRaadscommissie(returnedRaadscommissie));
    }

    @Test
    @Transactional
    void createRaadscommissieWithExistingId() throws Exception {
        // Create the Raadscommissie with an existing ID
        raadscommissie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRaadscommissieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(raadscommissie)))
            .andExpect(status().isBadRequest());

        // Validate the Raadscommissie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRaadscommissies() throws Exception {
        // Initialize the database
        raadscommissieRepository.saveAndFlush(raadscommissie);

        // Get all the raadscommissieList
        restRaadscommissieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raadscommissie.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getRaadscommissie() throws Exception {
        // Initialize the database
        raadscommissieRepository.saveAndFlush(raadscommissie);

        // Get the raadscommissie
        restRaadscommissieMockMvc
            .perform(get(ENTITY_API_URL_ID, raadscommissie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(raadscommissie.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingRaadscommissie() throws Exception {
        // Get the raadscommissie
        restRaadscommissieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRaadscommissie() throws Exception {
        // Initialize the database
        raadscommissieRepository.saveAndFlush(raadscommissie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the raadscommissie
        Raadscommissie updatedRaadscommissie = raadscommissieRepository.findById(raadscommissie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRaadscommissie are not directly saved in db
        em.detach(updatedRaadscommissie);
        updatedRaadscommissie.naam(UPDATED_NAAM);

        restRaadscommissieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRaadscommissie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRaadscommissie))
            )
            .andExpect(status().isOk());

        // Validate the Raadscommissie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRaadscommissieToMatchAllProperties(updatedRaadscommissie);
    }

    @Test
    @Transactional
    void putNonExistingRaadscommissie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadscommissie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRaadscommissieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, raadscommissie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(raadscommissie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Raadscommissie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRaadscommissie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadscommissie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaadscommissieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(raadscommissie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Raadscommissie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRaadscommissie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadscommissie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaadscommissieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(raadscommissie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Raadscommissie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRaadscommissieWithPatch() throws Exception {
        // Initialize the database
        raadscommissieRepository.saveAndFlush(raadscommissie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the raadscommissie using partial update
        Raadscommissie partialUpdatedRaadscommissie = new Raadscommissie();
        partialUpdatedRaadscommissie.setId(raadscommissie.getId());

        restRaadscommissieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRaadscommissie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRaadscommissie))
            )
            .andExpect(status().isOk());

        // Validate the Raadscommissie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRaadscommissieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRaadscommissie, raadscommissie),
            getPersistedRaadscommissie(raadscommissie)
        );
    }

    @Test
    @Transactional
    void fullUpdateRaadscommissieWithPatch() throws Exception {
        // Initialize the database
        raadscommissieRepository.saveAndFlush(raadscommissie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the raadscommissie using partial update
        Raadscommissie partialUpdatedRaadscommissie = new Raadscommissie();
        partialUpdatedRaadscommissie.setId(raadscommissie.getId());

        partialUpdatedRaadscommissie.naam(UPDATED_NAAM);

        restRaadscommissieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRaadscommissie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRaadscommissie))
            )
            .andExpect(status().isOk());

        // Validate the Raadscommissie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRaadscommissieUpdatableFieldsEquals(partialUpdatedRaadscommissie, getPersistedRaadscommissie(partialUpdatedRaadscommissie));
    }

    @Test
    @Transactional
    void patchNonExistingRaadscommissie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadscommissie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRaadscommissieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, raadscommissie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(raadscommissie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Raadscommissie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRaadscommissie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadscommissie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaadscommissieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(raadscommissie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Raadscommissie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRaadscommissie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadscommissie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaadscommissieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(raadscommissie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Raadscommissie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRaadscommissie() throws Exception {
        // Initialize the database
        raadscommissieRepository.saveAndFlush(raadscommissie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the raadscommissie
        restRaadscommissieMockMvc
            .perform(delete(ENTITY_API_URL_ID, raadscommissie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return raadscommissieRepository.count();
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

    protected Raadscommissie getPersistedRaadscommissie(Raadscommissie raadscommissie) {
        return raadscommissieRepository.findById(raadscommissie.getId()).orElseThrow();
    }

    protected void assertPersistedRaadscommissieToMatchAllProperties(Raadscommissie expectedRaadscommissie) {
        assertRaadscommissieAllPropertiesEquals(expectedRaadscommissie, getPersistedRaadscommissie(expectedRaadscommissie));
    }

    protected void assertPersistedRaadscommissieToMatchUpdatableProperties(Raadscommissie expectedRaadscommissie) {
        assertRaadscommissieAllUpdatablePropertiesEquals(expectedRaadscommissie, getPersistedRaadscommissie(expectedRaadscommissie));
    }
}
