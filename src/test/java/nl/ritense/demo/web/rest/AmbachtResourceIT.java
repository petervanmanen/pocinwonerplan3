package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AmbachtAsserts.*;
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
import nl.ritense.demo.domain.Ambacht;
import nl.ritense.demo.repository.AmbachtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AmbachtResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AmbachtResourceIT {

    private static final String DEFAULT_AMBACHTSOORT = "AAAAAAAAAA";
    private static final String UPDATED_AMBACHTSOORT = "BBBBBBBBBB";

    private static final String DEFAULT_JAARAMBACHTTOT = "AAAAAAAAAA";
    private static final String UPDATED_JAARAMBACHTTOT = "BBBBBBBBBB";

    private static final String DEFAULT_JAARAMBACHTVANAF = "AAAAAAAAAA";
    private static final String UPDATED_JAARAMBACHTVANAF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ambachts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AmbachtRepository ambachtRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAmbachtMockMvc;

    private Ambacht ambacht;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ambacht createEntity(EntityManager em) {
        Ambacht ambacht = new Ambacht()
            .ambachtsoort(DEFAULT_AMBACHTSOORT)
            .jaarambachttot(DEFAULT_JAARAMBACHTTOT)
            .jaarambachtvanaf(DEFAULT_JAARAMBACHTVANAF);
        return ambacht;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ambacht createUpdatedEntity(EntityManager em) {
        Ambacht ambacht = new Ambacht()
            .ambachtsoort(UPDATED_AMBACHTSOORT)
            .jaarambachttot(UPDATED_JAARAMBACHTTOT)
            .jaarambachtvanaf(UPDATED_JAARAMBACHTVANAF);
        return ambacht;
    }

    @BeforeEach
    public void initTest() {
        ambacht = createEntity(em);
    }

    @Test
    @Transactional
    void createAmbacht() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ambacht
        var returnedAmbacht = om.readValue(
            restAmbachtMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ambacht)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Ambacht.class
        );

        // Validate the Ambacht in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAmbachtUpdatableFieldsEquals(returnedAmbacht, getPersistedAmbacht(returnedAmbacht));
    }

    @Test
    @Transactional
    void createAmbachtWithExistingId() throws Exception {
        // Create the Ambacht with an existing ID
        ambacht.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmbachtMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ambacht)))
            .andExpect(status().isBadRequest());

        // Validate the Ambacht in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAmbachts() throws Exception {
        // Initialize the database
        ambachtRepository.saveAndFlush(ambacht);

        // Get all the ambachtList
        restAmbachtMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ambacht.getId().intValue())))
            .andExpect(jsonPath("$.[*].ambachtsoort").value(hasItem(DEFAULT_AMBACHTSOORT)))
            .andExpect(jsonPath("$.[*].jaarambachttot").value(hasItem(DEFAULT_JAARAMBACHTTOT)))
            .andExpect(jsonPath("$.[*].jaarambachtvanaf").value(hasItem(DEFAULT_JAARAMBACHTVANAF)));
    }

    @Test
    @Transactional
    void getAmbacht() throws Exception {
        // Initialize the database
        ambachtRepository.saveAndFlush(ambacht);

        // Get the ambacht
        restAmbachtMockMvc
            .perform(get(ENTITY_API_URL_ID, ambacht.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ambacht.getId().intValue()))
            .andExpect(jsonPath("$.ambachtsoort").value(DEFAULT_AMBACHTSOORT))
            .andExpect(jsonPath("$.jaarambachttot").value(DEFAULT_JAARAMBACHTTOT))
            .andExpect(jsonPath("$.jaarambachtvanaf").value(DEFAULT_JAARAMBACHTVANAF));
    }

    @Test
    @Transactional
    void getNonExistingAmbacht() throws Exception {
        // Get the ambacht
        restAmbachtMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAmbacht() throws Exception {
        // Initialize the database
        ambachtRepository.saveAndFlush(ambacht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ambacht
        Ambacht updatedAmbacht = ambachtRepository.findById(ambacht.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAmbacht are not directly saved in db
        em.detach(updatedAmbacht);
        updatedAmbacht.ambachtsoort(UPDATED_AMBACHTSOORT).jaarambachttot(UPDATED_JAARAMBACHTTOT).jaarambachtvanaf(UPDATED_JAARAMBACHTVANAF);

        restAmbachtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAmbacht.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAmbacht))
            )
            .andExpect(status().isOk());

        // Validate the Ambacht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAmbachtToMatchAllProperties(updatedAmbacht);
    }

    @Test
    @Transactional
    void putNonExistingAmbacht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ambacht.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmbachtMockMvc
            .perform(put(ENTITY_API_URL_ID, ambacht.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ambacht)))
            .andExpect(status().isBadRequest());

        // Validate the Ambacht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAmbacht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ambacht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmbachtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ambacht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ambacht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAmbacht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ambacht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmbachtMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ambacht)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ambacht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAmbachtWithPatch() throws Exception {
        // Initialize the database
        ambachtRepository.saveAndFlush(ambacht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ambacht using partial update
        Ambacht partialUpdatedAmbacht = new Ambacht();
        partialUpdatedAmbacht.setId(ambacht.getId());

        partialUpdatedAmbacht.jaarambachtvanaf(UPDATED_JAARAMBACHTVANAF);

        restAmbachtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAmbacht.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAmbacht))
            )
            .andExpect(status().isOk());

        // Validate the Ambacht in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAmbachtUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAmbacht, ambacht), getPersistedAmbacht(ambacht));
    }

    @Test
    @Transactional
    void fullUpdateAmbachtWithPatch() throws Exception {
        // Initialize the database
        ambachtRepository.saveAndFlush(ambacht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ambacht using partial update
        Ambacht partialUpdatedAmbacht = new Ambacht();
        partialUpdatedAmbacht.setId(ambacht.getId());

        partialUpdatedAmbacht
            .ambachtsoort(UPDATED_AMBACHTSOORT)
            .jaarambachttot(UPDATED_JAARAMBACHTTOT)
            .jaarambachtvanaf(UPDATED_JAARAMBACHTVANAF);

        restAmbachtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAmbacht.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAmbacht))
            )
            .andExpect(status().isOk());

        // Validate the Ambacht in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAmbachtUpdatableFieldsEquals(partialUpdatedAmbacht, getPersistedAmbacht(partialUpdatedAmbacht));
    }

    @Test
    @Transactional
    void patchNonExistingAmbacht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ambacht.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmbachtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ambacht.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ambacht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ambacht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAmbacht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ambacht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmbachtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ambacht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ambacht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAmbacht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ambacht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmbachtMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ambacht)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ambacht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAmbacht() throws Exception {
        // Initialize the database
        ambachtRepository.saveAndFlush(ambacht);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ambacht
        restAmbachtMockMvc
            .perform(delete(ENTITY_API_URL_ID, ambacht.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ambachtRepository.count();
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

    protected Ambacht getPersistedAmbacht(Ambacht ambacht) {
        return ambachtRepository.findById(ambacht.getId()).orElseThrow();
    }

    protected void assertPersistedAmbachtToMatchAllProperties(Ambacht expectedAmbacht) {
        assertAmbachtAllPropertiesEquals(expectedAmbacht, getPersistedAmbacht(expectedAmbacht));
    }

    protected void assertPersistedAmbachtToMatchUpdatableProperties(Ambacht expectedAmbacht) {
        assertAmbachtAllUpdatablePropertiesEquals(expectedAmbacht, getPersistedAmbacht(expectedAmbacht));
    }
}
