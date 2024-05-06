package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KademuurAsserts.*;
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
import nl.ritense.demo.domain.Kademuur;
import nl.ritense.demo.repository.KademuurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KademuurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KademuurResourceIT {

    private static final String DEFAULT_BELASTINGKLASSENIEUW = "AAAAAAAAAA";
    private static final String UPDATED_BELASTINGKLASSENIEUW = "BBBBBBBBBB";

    private static final String DEFAULT_BELASTINGKLASSEOUD = "AAAAAAAAAA";
    private static final String UPDATED_BELASTINGKLASSEOUD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GRIJPSTENEN = false;
    private static final Boolean UPDATED_GRIJPSTENEN = true;

    private static final String DEFAULT_HOOGTEBOVENKANTKADEMUUR = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTEBOVENKANTKADEMUUR = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAALBOVENKANTKADEMUUR = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAALBOVENKANTKADEMUUR = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTEBOVENKANTKADEMUUR = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTEBOVENKANTKADEMUUR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REDDINGSLIJN = false;
    private static final Boolean UPDATED_REDDINGSLIJN = true;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEBOVENKANTKADEMUUR = "AAAAAAAAAA";
    private static final String UPDATED_TYPEBOVENKANTKADEMUUR = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEFUNDERING = "AAAAAAAAAA";
    private static final String UPDATED_TYPEFUNDERING = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEVERANKERING = "AAAAAAAAAA";
    private static final String UPDATED_TYPEVERANKERING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kademuurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KademuurRepository kademuurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKademuurMockMvc;

    private Kademuur kademuur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kademuur createEntity(EntityManager em) {
        Kademuur kademuur = new Kademuur()
            .belastingklassenieuw(DEFAULT_BELASTINGKLASSENIEUW)
            .belastingklasseoud(DEFAULT_BELASTINGKLASSEOUD)
            .grijpstenen(DEFAULT_GRIJPSTENEN)
            .hoogtebovenkantkademuur(DEFAULT_HOOGTEBOVENKANTKADEMUUR)
            .materiaalbovenkantkademuur(DEFAULT_MATERIAALBOVENKANTKADEMUUR)
            .oppervlaktebovenkantkademuur(DEFAULT_OPPERVLAKTEBOVENKANTKADEMUUR)
            .reddingslijn(DEFAULT_REDDINGSLIJN)
            .type(DEFAULT_TYPE)
            .typebovenkantkademuur(DEFAULT_TYPEBOVENKANTKADEMUUR)
            .typefundering(DEFAULT_TYPEFUNDERING)
            .typeverankering(DEFAULT_TYPEVERANKERING);
        return kademuur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kademuur createUpdatedEntity(EntityManager em) {
        Kademuur kademuur = new Kademuur()
            .belastingklassenieuw(UPDATED_BELASTINGKLASSENIEUW)
            .belastingklasseoud(UPDATED_BELASTINGKLASSEOUD)
            .grijpstenen(UPDATED_GRIJPSTENEN)
            .hoogtebovenkantkademuur(UPDATED_HOOGTEBOVENKANTKADEMUUR)
            .materiaalbovenkantkademuur(UPDATED_MATERIAALBOVENKANTKADEMUUR)
            .oppervlaktebovenkantkademuur(UPDATED_OPPERVLAKTEBOVENKANTKADEMUUR)
            .reddingslijn(UPDATED_REDDINGSLIJN)
            .type(UPDATED_TYPE)
            .typebovenkantkademuur(UPDATED_TYPEBOVENKANTKADEMUUR)
            .typefundering(UPDATED_TYPEFUNDERING)
            .typeverankering(UPDATED_TYPEVERANKERING);
        return kademuur;
    }

    @BeforeEach
    public void initTest() {
        kademuur = createEntity(em);
    }

    @Test
    @Transactional
    void createKademuur() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kademuur
        var returnedKademuur = om.readValue(
            restKademuurMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kademuur)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kademuur.class
        );

        // Validate the Kademuur in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKademuurUpdatableFieldsEquals(returnedKademuur, getPersistedKademuur(returnedKademuur));
    }

    @Test
    @Transactional
    void createKademuurWithExistingId() throws Exception {
        // Create the Kademuur with an existing ID
        kademuur.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKademuurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kademuur)))
            .andExpect(status().isBadRequest());

        // Validate the Kademuur in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKademuurs() throws Exception {
        // Initialize the database
        kademuurRepository.saveAndFlush(kademuur);

        // Get all the kademuurList
        restKademuurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kademuur.getId().intValue())))
            .andExpect(jsonPath("$.[*].belastingklassenieuw").value(hasItem(DEFAULT_BELASTINGKLASSENIEUW)))
            .andExpect(jsonPath("$.[*].belastingklasseoud").value(hasItem(DEFAULT_BELASTINGKLASSEOUD)))
            .andExpect(jsonPath("$.[*].grijpstenen").value(hasItem(DEFAULT_GRIJPSTENEN.booleanValue())))
            .andExpect(jsonPath("$.[*].hoogtebovenkantkademuur").value(hasItem(DEFAULT_HOOGTEBOVENKANTKADEMUUR)))
            .andExpect(jsonPath("$.[*].materiaalbovenkantkademuur").value(hasItem(DEFAULT_MATERIAALBOVENKANTKADEMUUR)))
            .andExpect(jsonPath("$.[*].oppervlaktebovenkantkademuur").value(hasItem(DEFAULT_OPPERVLAKTEBOVENKANTKADEMUUR)))
            .andExpect(jsonPath("$.[*].reddingslijn").value(hasItem(DEFAULT_REDDINGSLIJN.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].typebovenkantkademuur").value(hasItem(DEFAULT_TYPEBOVENKANTKADEMUUR)))
            .andExpect(jsonPath("$.[*].typefundering").value(hasItem(DEFAULT_TYPEFUNDERING)))
            .andExpect(jsonPath("$.[*].typeverankering").value(hasItem(DEFAULT_TYPEVERANKERING)));
    }

    @Test
    @Transactional
    void getKademuur() throws Exception {
        // Initialize the database
        kademuurRepository.saveAndFlush(kademuur);

        // Get the kademuur
        restKademuurMockMvc
            .perform(get(ENTITY_API_URL_ID, kademuur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kademuur.getId().intValue()))
            .andExpect(jsonPath("$.belastingklassenieuw").value(DEFAULT_BELASTINGKLASSENIEUW))
            .andExpect(jsonPath("$.belastingklasseoud").value(DEFAULT_BELASTINGKLASSEOUD))
            .andExpect(jsonPath("$.grijpstenen").value(DEFAULT_GRIJPSTENEN.booleanValue()))
            .andExpect(jsonPath("$.hoogtebovenkantkademuur").value(DEFAULT_HOOGTEBOVENKANTKADEMUUR))
            .andExpect(jsonPath("$.materiaalbovenkantkademuur").value(DEFAULT_MATERIAALBOVENKANTKADEMUUR))
            .andExpect(jsonPath("$.oppervlaktebovenkantkademuur").value(DEFAULT_OPPERVLAKTEBOVENKANTKADEMUUR))
            .andExpect(jsonPath("$.reddingslijn").value(DEFAULT_REDDINGSLIJN.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.typebovenkantkademuur").value(DEFAULT_TYPEBOVENKANTKADEMUUR))
            .andExpect(jsonPath("$.typefundering").value(DEFAULT_TYPEFUNDERING))
            .andExpect(jsonPath("$.typeverankering").value(DEFAULT_TYPEVERANKERING));
    }

    @Test
    @Transactional
    void getNonExistingKademuur() throws Exception {
        // Get the kademuur
        restKademuurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKademuur() throws Exception {
        // Initialize the database
        kademuurRepository.saveAndFlush(kademuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kademuur
        Kademuur updatedKademuur = kademuurRepository.findById(kademuur.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKademuur are not directly saved in db
        em.detach(updatedKademuur);
        updatedKademuur
            .belastingklassenieuw(UPDATED_BELASTINGKLASSENIEUW)
            .belastingklasseoud(UPDATED_BELASTINGKLASSEOUD)
            .grijpstenen(UPDATED_GRIJPSTENEN)
            .hoogtebovenkantkademuur(UPDATED_HOOGTEBOVENKANTKADEMUUR)
            .materiaalbovenkantkademuur(UPDATED_MATERIAALBOVENKANTKADEMUUR)
            .oppervlaktebovenkantkademuur(UPDATED_OPPERVLAKTEBOVENKANTKADEMUUR)
            .reddingslijn(UPDATED_REDDINGSLIJN)
            .type(UPDATED_TYPE)
            .typebovenkantkademuur(UPDATED_TYPEBOVENKANTKADEMUUR)
            .typefundering(UPDATED_TYPEFUNDERING)
            .typeverankering(UPDATED_TYPEVERANKERING);

        restKademuurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKademuur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKademuur))
            )
            .andExpect(status().isOk());

        // Validate the Kademuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKademuurToMatchAllProperties(updatedKademuur);
    }

    @Test
    @Transactional
    void putNonExistingKademuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kademuur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKademuurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kademuur.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kademuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kademuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKademuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kademuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKademuurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kademuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kademuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKademuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kademuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKademuurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kademuur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kademuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKademuurWithPatch() throws Exception {
        // Initialize the database
        kademuurRepository.saveAndFlush(kademuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kademuur using partial update
        Kademuur partialUpdatedKademuur = new Kademuur();
        partialUpdatedKademuur.setId(kademuur.getId());

        partialUpdatedKademuur
            .belastingklasseoud(UPDATED_BELASTINGKLASSEOUD)
            .grijpstenen(UPDATED_GRIJPSTENEN)
            .oppervlaktebovenkantkademuur(UPDATED_OPPERVLAKTEBOVENKANTKADEMUUR)
            .reddingslijn(UPDATED_REDDINGSLIJN)
            .typefundering(UPDATED_TYPEFUNDERING);

        restKademuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKademuur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKademuur))
            )
            .andExpect(status().isOk());

        // Validate the Kademuur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKademuurUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedKademuur, kademuur), getPersistedKademuur(kademuur));
    }

    @Test
    @Transactional
    void fullUpdateKademuurWithPatch() throws Exception {
        // Initialize the database
        kademuurRepository.saveAndFlush(kademuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kademuur using partial update
        Kademuur partialUpdatedKademuur = new Kademuur();
        partialUpdatedKademuur.setId(kademuur.getId());

        partialUpdatedKademuur
            .belastingklassenieuw(UPDATED_BELASTINGKLASSENIEUW)
            .belastingklasseoud(UPDATED_BELASTINGKLASSEOUD)
            .grijpstenen(UPDATED_GRIJPSTENEN)
            .hoogtebovenkantkademuur(UPDATED_HOOGTEBOVENKANTKADEMUUR)
            .materiaalbovenkantkademuur(UPDATED_MATERIAALBOVENKANTKADEMUUR)
            .oppervlaktebovenkantkademuur(UPDATED_OPPERVLAKTEBOVENKANTKADEMUUR)
            .reddingslijn(UPDATED_REDDINGSLIJN)
            .type(UPDATED_TYPE)
            .typebovenkantkademuur(UPDATED_TYPEBOVENKANTKADEMUUR)
            .typefundering(UPDATED_TYPEFUNDERING)
            .typeverankering(UPDATED_TYPEVERANKERING);

        restKademuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKademuur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKademuur))
            )
            .andExpect(status().isOk());

        // Validate the Kademuur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKademuurUpdatableFieldsEquals(partialUpdatedKademuur, getPersistedKademuur(partialUpdatedKademuur));
    }

    @Test
    @Transactional
    void patchNonExistingKademuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kademuur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKademuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kademuur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kademuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kademuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKademuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kademuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKademuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kademuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kademuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKademuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kademuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKademuurMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kademuur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kademuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKademuur() throws Exception {
        // Initialize the database
        kademuurRepository.saveAndFlush(kademuur);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kademuur
        restKademuurMockMvc
            .perform(delete(ENTITY_API_URL_ID, kademuur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kademuurRepository.count();
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

    protected Kademuur getPersistedKademuur(Kademuur kademuur) {
        return kademuurRepository.findById(kademuur.getId()).orElseThrow();
    }

    protected void assertPersistedKademuurToMatchAllProperties(Kademuur expectedKademuur) {
        assertKademuurAllPropertiesEquals(expectedKademuur, getPersistedKademuur(expectedKademuur));
    }

    protected void assertPersistedKademuurToMatchUpdatableProperties(Kademuur expectedKademuur) {
        assertKademuurAllUpdatablePropertiesEquals(expectedKademuur, getPersistedKademuur(expectedKademuur));
    }
}
