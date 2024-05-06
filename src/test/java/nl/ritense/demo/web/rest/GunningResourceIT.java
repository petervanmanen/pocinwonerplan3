package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GunningAsserts.*;
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
import nl.ritense.demo.domain.Gunning;
import nl.ritense.demo.repository.GunningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GunningResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GunningResourceIT {

    private static final String DEFAULT_BERICHT = "AAAAAAAAAA";
    private static final String UPDATED_BERICHT = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMGUNNING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMGUNNING = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMPUBLICATIE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMPUBLICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMVOORLOPIGEGUNNING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMVOORLOPIGEGUNNING = "BBBBBBBBBB";

    private static final String DEFAULT_GEGUNDEPRIJS = "AAAAAAAAAA";
    private static final String UPDATED_GEGUNDEPRIJS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gunnings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GunningRepository gunningRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGunningMockMvc;

    private Gunning gunning;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gunning createEntity(EntityManager em) {
        Gunning gunning = new Gunning()
            .bericht(DEFAULT_BERICHT)
            .datumgunning(DEFAULT_DATUMGUNNING)
            .datumpublicatie(DEFAULT_DATUMPUBLICATIE)
            .datumvoorlopigegunning(DEFAULT_DATUMVOORLOPIGEGUNNING)
            .gegundeprijs(DEFAULT_GEGUNDEPRIJS);
        return gunning;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gunning createUpdatedEntity(EntityManager em) {
        Gunning gunning = new Gunning()
            .bericht(UPDATED_BERICHT)
            .datumgunning(UPDATED_DATUMGUNNING)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumvoorlopigegunning(UPDATED_DATUMVOORLOPIGEGUNNING)
            .gegundeprijs(UPDATED_GEGUNDEPRIJS);
        return gunning;
    }

    @BeforeEach
    public void initTest() {
        gunning = createEntity(em);
    }

    @Test
    @Transactional
    void createGunning() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Gunning
        var returnedGunning = om.readValue(
            restGunningMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gunning)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Gunning.class
        );

        // Validate the Gunning in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGunningUpdatableFieldsEquals(returnedGunning, getPersistedGunning(returnedGunning));
    }

    @Test
    @Transactional
    void createGunningWithExistingId() throws Exception {
        // Create the Gunning with an existing ID
        gunning.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGunningMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gunning)))
            .andExpect(status().isBadRequest());

        // Validate the Gunning in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGunnings() throws Exception {
        // Initialize the database
        gunningRepository.saveAndFlush(gunning);

        // Get all the gunningList
        restGunningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gunning.getId().intValue())))
            .andExpect(jsonPath("$.[*].bericht").value(hasItem(DEFAULT_BERICHT)))
            .andExpect(jsonPath("$.[*].datumgunning").value(hasItem(DEFAULT_DATUMGUNNING)))
            .andExpect(jsonPath("$.[*].datumpublicatie").value(hasItem(DEFAULT_DATUMPUBLICATIE)))
            .andExpect(jsonPath("$.[*].datumvoorlopigegunning").value(hasItem(DEFAULT_DATUMVOORLOPIGEGUNNING)))
            .andExpect(jsonPath("$.[*].gegundeprijs").value(hasItem(DEFAULT_GEGUNDEPRIJS)));
    }

    @Test
    @Transactional
    void getGunning() throws Exception {
        // Initialize the database
        gunningRepository.saveAndFlush(gunning);

        // Get the gunning
        restGunningMockMvc
            .perform(get(ENTITY_API_URL_ID, gunning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gunning.getId().intValue()))
            .andExpect(jsonPath("$.bericht").value(DEFAULT_BERICHT))
            .andExpect(jsonPath("$.datumgunning").value(DEFAULT_DATUMGUNNING))
            .andExpect(jsonPath("$.datumpublicatie").value(DEFAULT_DATUMPUBLICATIE))
            .andExpect(jsonPath("$.datumvoorlopigegunning").value(DEFAULT_DATUMVOORLOPIGEGUNNING))
            .andExpect(jsonPath("$.gegundeprijs").value(DEFAULT_GEGUNDEPRIJS));
    }

    @Test
    @Transactional
    void getNonExistingGunning() throws Exception {
        // Get the gunning
        restGunningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGunning() throws Exception {
        // Initialize the database
        gunningRepository.saveAndFlush(gunning);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gunning
        Gunning updatedGunning = gunningRepository.findById(gunning.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGunning are not directly saved in db
        em.detach(updatedGunning);
        updatedGunning
            .bericht(UPDATED_BERICHT)
            .datumgunning(UPDATED_DATUMGUNNING)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumvoorlopigegunning(UPDATED_DATUMVOORLOPIGEGUNNING)
            .gegundeprijs(UPDATED_GEGUNDEPRIJS);

        restGunningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGunning.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGunning))
            )
            .andExpect(status().isOk());

        // Validate the Gunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGunningToMatchAllProperties(updatedGunning);
    }

    @Test
    @Transactional
    void putNonExistingGunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gunning.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGunningMockMvc
            .perform(put(ENTITY_API_URL_ID, gunning.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gunning)))
            .andExpect(status().isBadRequest());

        // Validate the Gunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gunning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGunningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gunning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gunning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGunningMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gunning)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGunningWithPatch() throws Exception {
        // Initialize the database
        gunningRepository.saveAndFlush(gunning);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gunning using partial update
        Gunning partialUpdatedGunning = new Gunning();
        partialUpdatedGunning.setId(gunning.getId());

        partialUpdatedGunning
            .datumgunning(UPDATED_DATUMGUNNING)
            .datumvoorlopigegunning(UPDATED_DATUMVOORLOPIGEGUNNING)
            .gegundeprijs(UPDATED_GEGUNDEPRIJS);

        restGunningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGunning.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGunning))
            )
            .andExpect(status().isOk());

        // Validate the Gunning in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGunningUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedGunning, gunning), getPersistedGunning(gunning));
    }

    @Test
    @Transactional
    void fullUpdateGunningWithPatch() throws Exception {
        // Initialize the database
        gunningRepository.saveAndFlush(gunning);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gunning using partial update
        Gunning partialUpdatedGunning = new Gunning();
        partialUpdatedGunning.setId(gunning.getId());

        partialUpdatedGunning
            .bericht(UPDATED_BERICHT)
            .datumgunning(UPDATED_DATUMGUNNING)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumvoorlopigegunning(UPDATED_DATUMVOORLOPIGEGUNNING)
            .gegundeprijs(UPDATED_GEGUNDEPRIJS);

        restGunningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGunning.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGunning))
            )
            .andExpect(status().isOk());

        // Validate the Gunning in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGunningUpdatableFieldsEquals(partialUpdatedGunning, getPersistedGunning(partialUpdatedGunning));
    }

    @Test
    @Transactional
    void patchNonExistingGunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gunning.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGunningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gunning.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gunning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gunning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGunningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gunning))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGunning() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gunning.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGunningMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gunning)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gunning in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGunning() throws Exception {
        // Initialize the database
        gunningRepository.saveAndFlush(gunning);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gunning
        restGunningMockMvc
            .perform(delete(ENTITY_API_URL_ID, gunning.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gunningRepository.count();
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

    protected Gunning getPersistedGunning(Gunning gunning) {
        return gunningRepository.findById(gunning.getId()).orElseThrow();
    }

    protected void assertPersistedGunningToMatchAllProperties(Gunning expectedGunning) {
        assertGunningAllPropertiesEquals(expectedGunning, getPersistedGunning(expectedGunning));
    }

    protected void assertPersistedGunningToMatchUpdatableProperties(Gunning expectedGunning) {
        assertGunningAllUpdatablePropertiesEquals(expectedGunning, getPersistedGunning(expectedGunning));
    }
}
