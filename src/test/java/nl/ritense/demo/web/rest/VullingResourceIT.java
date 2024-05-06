package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VullingAsserts.*;
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
import nl.ritense.demo.domain.Vulling;
import nl.ritense.demo.repository.VullingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VullingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VullingResourceIT {

    private static final String DEFAULT_GRONDSOORT = "AAAAAAAAAA";
    private static final String UPDATED_GRONDSOORT = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_KEYSPOOR = "AAAAAAAAAA";
    private static final String UPDATED_KEYSPOOR = "BBBBBBBBBB";

    private static final String DEFAULT_KLEUR = "AAAAAAAAAA";
    private static final String UPDATED_KLEUR = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECTCD = "AAAAAAAAAA";
    private static final String UPDATED_PROJECTCD = "BBBBBBBBBB";

    private static final String DEFAULT_PUTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_PUTNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_SPOORNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_SPOORNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_STRUCTUUR = "AAAAAAAAAA";
    private static final String UPDATED_STRUCTUUR = "BBBBBBBBBB";

    private static final String DEFAULT_VLAKNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_VLAKNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_VULLINGNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_VULLINGNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vullings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VullingRepository vullingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVullingMockMvc;

    private Vulling vulling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vulling createEntity(EntityManager em) {
        Vulling vulling = new Vulling()
            .grondsoort(DEFAULT_GRONDSOORT)
            .key(DEFAULT_KEY)
            .keyspoor(DEFAULT_KEYSPOOR)
            .kleur(DEFAULT_KLEUR)
            .projectcd(DEFAULT_PROJECTCD)
            .putnummer(DEFAULT_PUTNUMMER)
            .spoornummer(DEFAULT_SPOORNUMMER)
            .structuur(DEFAULT_STRUCTUUR)
            .vlaknummer(DEFAULT_VLAKNUMMER)
            .vullingnummer(DEFAULT_VULLINGNUMMER);
        return vulling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vulling createUpdatedEntity(EntityManager em) {
        Vulling vulling = new Vulling()
            .grondsoort(UPDATED_GRONDSOORT)
            .key(UPDATED_KEY)
            .keyspoor(UPDATED_KEYSPOOR)
            .kleur(UPDATED_KLEUR)
            .projectcd(UPDATED_PROJECTCD)
            .putnummer(UPDATED_PUTNUMMER)
            .spoornummer(UPDATED_SPOORNUMMER)
            .structuur(UPDATED_STRUCTUUR)
            .vlaknummer(UPDATED_VLAKNUMMER)
            .vullingnummer(UPDATED_VULLINGNUMMER);
        return vulling;
    }

    @BeforeEach
    public void initTest() {
        vulling = createEntity(em);
    }

    @Test
    @Transactional
    void createVulling() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vulling
        var returnedVulling = om.readValue(
            restVullingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vulling)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vulling.class
        );

        // Validate the Vulling in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVullingUpdatableFieldsEquals(returnedVulling, getPersistedVulling(returnedVulling));
    }

    @Test
    @Transactional
    void createVullingWithExistingId() throws Exception {
        // Create the Vulling with an existing ID
        vulling.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVullingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vulling)))
            .andExpect(status().isBadRequest());

        // Validate the Vulling in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVullings() throws Exception {
        // Initialize the database
        vullingRepository.saveAndFlush(vulling);

        // Get all the vullingList
        restVullingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vulling.getId().intValue())))
            .andExpect(jsonPath("$.[*].grondsoort").value(hasItem(DEFAULT_GRONDSOORT)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].keyspoor").value(hasItem(DEFAULT_KEYSPOOR)))
            .andExpect(jsonPath("$.[*].kleur").value(hasItem(DEFAULT_KLEUR)))
            .andExpect(jsonPath("$.[*].projectcd").value(hasItem(DEFAULT_PROJECTCD)))
            .andExpect(jsonPath("$.[*].putnummer").value(hasItem(DEFAULT_PUTNUMMER)))
            .andExpect(jsonPath("$.[*].spoornummer").value(hasItem(DEFAULT_SPOORNUMMER)))
            .andExpect(jsonPath("$.[*].structuur").value(hasItem(DEFAULT_STRUCTUUR)))
            .andExpect(jsonPath("$.[*].vlaknummer").value(hasItem(DEFAULT_VLAKNUMMER)))
            .andExpect(jsonPath("$.[*].vullingnummer").value(hasItem(DEFAULT_VULLINGNUMMER)));
    }

    @Test
    @Transactional
    void getVulling() throws Exception {
        // Initialize the database
        vullingRepository.saveAndFlush(vulling);

        // Get the vulling
        restVullingMockMvc
            .perform(get(ENTITY_API_URL_ID, vulling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vulling.getId().intValue()))
            .andExpect(jsonPath("$.grondsoort").value(DEFAULT_GRONDSOORT))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.keyspoor").value(DEFAULT_KEYSPOOR))
            .andExpect(jsonPath("$.kleur").value(DEFAULT_KLEUR))
            .andExpect(jsonPath("$.projectcd").value(DEFAULT_PROJECTCD))
            .andExpect(jsonPath("$.putnummer").value(DEFAULT_PUTNUMMER))
            .andExpect(jsonPath("$.spoornummer").value(DEFAULT_SPOORNUMMER))
            .andExpect(jsonPath("$.structuur").value(DEFAULT_STRUCTUUR))
            .andExpect(jsonPath("$.vlaknummer").value(DEFAULT_VLAKNUMMER))
            .andExpect(jsonPath("$.vullingnummer").value(DEFAULT_VULLINGNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingVulling() throws Exception {
        // Get the vulling
        restVullingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVulling() throws Exception {
        // Initialize the database
        vullingRepository.saveAndFlush(vulling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vulling
        Vulling updatedVulling = vullingRepository.findById(vulling.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVulling are not directly saved in db
        em.detach(updatedVulling);
        updatedVulling
            .grondsoort(UPDATED_GRONDSOORT)
            .key(UPDATED_KEY)
            .keyspoor(UPDATED_KEYSPOOR)
            .kleur(UPDATED_KLEUR)
            .projectcd(UPDATED_PROJECTCD)
            .putnummer(UPDATED_PUTNUMMER)
            .spoornummer(UPDATED_SPOORNUMMER)
            .structuur(UPDATED_STRUCTUUR)
            .vlaknummer(UPDATED_VLAKNUMMER)
            .vullingnummer(UPDATED_VULLINGNUMMER);

        restVullingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVulling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVulling))
            )
            .andExpect(status().isOk());

        // Validate the Vulling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVullingToMatchAllProperties(updatedVulling);
    }

    @Test
    @Transactional
    void putNonExistingVulling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVullingMockMvc
            .perform(put(ENTITY_API_URL_ID, vulling.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vulling)))
            .andExpect(status().isBadRequest());

        // Validate the Vulling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVulling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVullingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vulling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vulling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVulling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVullingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vulling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vulling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVullingWithPatch() throws Exception {
        // Initialize the database
        vullingRepository.saveAndFlush(vulling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vulling using partial update
        Vulling partialUpdatedVulling = new Vulling();
        partialUpdatedVulling.setId(vulling.getId());

        partialUpdatedVulling
            .grondsoort(UPDATED_GRONDSOORT)
            .kleur(UPDATED_KLEUR)
            .spoornummer(UPDATED_SPOORNUMMER)
            .structuur(UPDATED_STRUCTUUR)
            .vullingnummer(UPDATED_VULLINGNUMMER);

        restVullingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVulling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVulling))
            )
            .andExpect(status().isOk());

        // Validate the Vulling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVullingUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVulling, vulling), getPersistedVulling(vulling));
    }

    @Test
    @Transactional
    void fullUpdateVullingWithPatch() throws Exception {
        // Initialize the database
        vullingRepository.saveAndFlush(vulling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vulling using partial update
        Vulling partialUpdatedVulling = new Vulling();
        partialUpdatedVulling.setId(vulling.getId());

        partialUpdatedVulling
            .grondsoort(UPDATED_GRONDSOORT)
            .key(UPDATED_KEY)
            .keyspoor(UPDATED_KEYSPOOR)
            .kleur(UPDATED_KLEUR)
            .projectcd(UPDATED_PROJECTCD)
            .putnummer(UPDATED_PUTNUMMER)
            .spoornummer(UPDATED_SPOORNUMMER)
            .structuur(UPDATED_STRUCTUUR)
            .vlaknummer(UPDATED_VLAKNUMMER)
            .vullingnummer(UPDATED_VULLINGNUMMER);

        restVullingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVulling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVulling))
            )
            .andExpect(status().isOk());

        // Validate the Vulling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVullingUpdatableFieldsEquals(partialUpdatedVulling, getPersistedVulling(partialUpdatedVulling));
    }

    @Test
    @Transactional
    void patchNonExistingVulling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVullingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vulling.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vulling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vulling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVulling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVullingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vulling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vulling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVulling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVullingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vulling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vulling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVulling() throws Exception {
        // Initialize the database
        vullingRepository.saveAndFlush(vulling);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vulling
        restVullingMockMvc
            .perform(delete(ENTITY_API_URL_ID, vulling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vullingRepository.count();
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

    protected Vulling getPersistedVulling(Vulling vulling) {
        return vullingRepository.findById(vulling.getId()).orElseThrow();
    }

    protected void assertPersistedVullingToMatchAllProperties(Vulling expectedVulling) {
        assertVullingAllPropertiesEquals(expectedVulling, getPersistedVulling(expectedVulling));
    }

    protected void assertPersistedVullingToMatchUpdatableProperties(Vulling expectedVulling) {
        assertVullingAllUpdatablePropertiesEquals(expectedVulling, getPersistedVulling(expectedVulling));
    }
}
