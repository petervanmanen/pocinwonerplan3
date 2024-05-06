package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ErouteAsserts.*;
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
import nl.ritense.demo.domain.Eroute;
import nl.ritense.demo.repository.ErouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ErouteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ErouteResourceIT {

    private static final String DEFAULT_GEOMETRIE = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIE = "BBBBBBBBBB";

    private static final String DEFAULT_EROUTECODE = "AAAAAAAAAA";
    private static final String UPDATED_EROUTECODE = "BBBBBBBBBB";

    private static final String DEFAULT_EROUTESOORT = "AAAAAAAAAA";
    private static final String UPDATED_EROUTESOORT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/eroutes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ErouteRepository erouteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restErouteMockMvc;

    private Eroute eroute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eroute createEntity(EntityManager em) {
        Eroute eroute = new Eroute().geometrie(DEFAULT_GEOMETRIE).eroutecode(DEFAULT_EROUTECODE).eroutesoort(DEFAULT_EROUTESOORT);
        return eroute;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eroute createUpdatedEntity(EntityManager em) {
        Eroute eroute = new Eroute().geometrie(UPDATED_GEOMETRIE).eroutecode(UPDATED_EROUTECODE).eroutesoort(UPDATED_EROUTESOORT);
        return eroute;
    }

    @BeforeEach
    public void initTest() {
        eroute = createEntity(em);
    }

    @Test
    @Transactional
    void createEroute() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Eroute
        var returnedEroute = om.readValue(
            restErouteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eroute)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Eroute.class
        );

        // Validate the Eroute in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertErouteUpdatableFieldsEquals(returnedEroute, getPersistedEroute(returnedEroute));
    }

    @Test
    @Transactional
    void createErouteWithExistingId() throws Exception {
        // Create the Eroute with an existing ID
        eroute.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restErouteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eroute)))
            .andExpect(status().isBadRequest());

        // Validate the Eroute in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEroutes() throws Exception {
        // Initialize the database
        erouteRepository.saveAndFlush(eroute);

        // Get all the erouteList
        restErouteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eroute.getId().intValue())))
            .andExpect(jsonPath("$.[*].geometrie").value(hasItem(DEFAULT_GEOMETRIE)))
            .andExpect(jsonPath("$.[*].eroutecode").value(hasItem(DEFAULT_EROUTECODE)))
            .andExpect(jsonPath("$.[*].eroutesoort").value(hasItem(DEFAULT_EROUTESOORT)));
    }

    @Test
    @Transactional
    void getEroute() throws Exception {
        // Initialize the database
        erouteRepository.saveAndFlush(eroute);

        // Get the eroute
        restErouteMockMvc
            .perform(get(ENTITY_API_URL_ID, eroute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eroute.getId().intValue()))
            .andExpect(jsonPath("$.geometrie").value(DEFAULT_GEOMETRIE))
            .andExpect(jsonPath("$.eroutecode").value(DEFAULT_EROUTECODE))
            .andExpect(jsonPath("$.eroutesoort").value(DEFAULT_EROUTESOORT));
    }

    @Test
    @Transactional
    void getNonExistingEroute() throws Exception {
        // Get the eroute
        restErouteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEroute() throws Exception {
        // Initialize the database
        erouteRepository.saveAndFlush(eroute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eroute
        Eroute updatedEroute = erouteRepository.findById(eroute.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEroute are not directly saved in db
        em.detach(updatedEroute);
        updatedEroute.geometrie(UPDATED_GEOMETRIE).eroutecode(UPDATED_EROUTECODE).eroutesoort(UPDATED_EROUTESOORT);

        restErouteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEroute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEroute))
            )
            .andExpect(status().isOk());

        // Validate the Eroute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedErouteToMatchAllProperties(updatedEroute);
    }

    @Test
    @Transactional
    void putNonExistingEroute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eroute.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restErouteMockMvc
            .perform(put(ENTITY_API_URL_ID, eroute.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eroute)))
            .andExpect(status().isBadRequest());

        // Validate the Eroute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEroute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eroute.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErouteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eroute))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eroute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEroute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eroute.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErouteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eroute)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eroute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateErouteWithPatch() throws Exception {
        // Initialize the database
        erouteRepository.saveAndFlush(eroute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eroute using partial update
        Eroute partialUpdatedEroute = new Eroute();
        partialUpdatedEroute.setId(eroute.getId());

        partialUpdatedEroute.eroutecode(UPDATED_EROUTECODE).eroutesoort(UPDATED_EROUTESOORT);

        restErouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEroute.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEroute))
            )
            .andExpect(status().isOk());

        // Validate the Eroute in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertErouteUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEroute, eroute), getPersistedEroute(eroute));
    }

    @Test
    @Transactional
    void fullUpdateErouteWithPatch() throws Exception {
        // Initialize the database
        erouteRepository.saveAndFlush(eroute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eroute using partial update
        Eroute partialUpdatedEroute = new Eroute();
        partialUpdatedEroute.setId(eroute.getId());

        partialUpdatedEroute.geometrie(UPDATED_GEOMETRIE).eroutecode(UPDATED_EROUTECODE).eroutesoort(UPDATED_EROUTESOORT);

        restErouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEroute.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEroute))
            )
            .andExpect(status().isOk());

        // Validate the Eroute in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertErouteUpdatableFieldsEquals(partialUpdatedEroute, getPersistedEroute(partialUpdatedEroute));
    }

    @Test
    @Transactional
    void patchNonExistingEroute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eroute.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restErouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eroute.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(eroute))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eroute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEroute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eroute.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eroute))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eroute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEroute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eroute.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErouteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(eroute)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eroute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEroute() throws Exception {
        // Initialize the database
        erouteRepository.saveAndFlush(eroute);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eroute
        restErouteMockMvc
            .perform(delete(ENTITY_API_URL_ID, eroute.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return erouteRepository.count();
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

    protected Eroute getPersistedEroute(Eroute eroute) {
        return erouteRepository.findById(eroute.getId()).orElseThrow();
    }

    protected void assertPersistedErouteToMatchAllProperties(Eroute expectedEroute) {
        assertErouteAllPropertiesEquals(expectedEroute, getPersistedEroute(expectedEroute));
    }

    protected void assertPersistedErouteToMatchUpdatableProperties(Eroute expectedEroute) {
        assertErouteAllUpdatablePropertiesEquals(expectedEroute, getPersistedEroute(expectedEroute));
    }
}
