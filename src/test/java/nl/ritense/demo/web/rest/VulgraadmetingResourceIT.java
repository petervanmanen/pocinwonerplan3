package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VulgraadmetingAsserts.*;
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
import nl.ritense.demo.domain.Vulgraadmeting;
import nl.ritense.demo.repository.VulgraadmetingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VulgraadmetingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VulgraadmetingResourceIT {

    private static final String DEFAULT_TIJDSTIP = "AAAAAAAAAA";
    private static final String UPDATED_TIJDSTIP = "BBBBBBBBBB";

    private static final String DEFAULT_VULGRAAD = "AAAAAAAAAA";
    private static final String UPDATED_VULGRAAD = "BBBBBBBBBB";

    private static final String DEFAULT_VULLINGGEWICHT = "AAAAAAAAAA";
    private static final String UPDATED_VULLINGGEWICHT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vulgraadmetings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VulgraadmetingRepository vulgraadmetingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVulgraadmetingMockMvc;

    private Vulgraadmeting vulgraadmeting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vulgraadmeting createEntity(EntityManager em) {
        Vulgraadmeting vulgraadmeting = new Vulgraadmeting()
            .tijdstip(DEFAULT_TIJDSTIP)
            .vulgraad(DEFAULT_VULGRAAD)
            .vullinggewicht(DEFAULT_VULLINGGEWICHT);
        return vulgraadmeting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vulgraadmeting createUpdatedEntity(EntityManager em) {
        Vulgraadmeting vulgraadmeting = new Vulgraadmeting()
            .tijdstip(UPDATED_TIJDSTIP)
            .vulgraad(UPDATED_VULGRAAD)
            .vullinggewicht(UPDATED_VULLINGGEWICHT);
        return vulgraadmeting;
    }

    @BeforeEach
    public void initTest() {
        vulgraadmeting = createEntity(em);
    }

    @Test
    @Transactional
    void createVulgraadmeting() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vulgraadmeting
        var returnedVulgraadmeting = om.readValue(
            restVulgraadmetingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vulgraadmeting)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vulgraadmeting.class
        );

        // Validate the Vulgraadmeting in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVulgraadmetingUpdatableFieldsEquals(returnedVulgraadmeting, getPersistedVulgraadmeting(returnedVulgraadmeting));
    }

    @Test
    @Transactional
    void createVulgraadmetingWithExistingId() throws Exception {
        // Create the Vulgraadmeting with an existing ID
        vulgraadmeting.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVulgraadmetingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vulgraadmeting)))
            .andExpect(status().isBadRequest());

        // Validate the Vulgraadmeting in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVulgraadmetings() throws Exception {
        // Initialize the database
        vulgraadmetingRepository.saveAndFlush(vulgraadmeting);

        // Get all the vulgraadmetingList
        restVulgraadmetingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vulgraadmeting.getId().intValue())))
            .andExpect(jsonPath("$.[*].tijdstip").value(hasItem(DEFAULT_TIJDSTIP)))
            .andExpect(jsonPath("$.[*].vulgraad").value(hasItem(DEFAULT_VULGRAAD)))
            .andExpect(jsonPath("$.[*].vullinggewicht").value(hasItem(DEFAULT_VULLINGGEWICHT)));
    }

    @Test
    @Transactional
    void getVulgraadmeting() throws Exception {
        // Initialize the database
        vulgraadmetingRepository.saveAndFlush(vulgraadmeting);

        // Get the vulgraadmeting
        restVulgraadmetingMockMvc
            .perform(get(ENTITY_API_URL_ID, vulgraadmeting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vulgraadmeting.getId().intValue()))
            .andExpect(jsonPath("$.tijdstip").value(DEFAULT_TIJDSTIP))
            .andExpect(jsonPath("$.vulgraad").value(DEFAULT_VULGRAAD))
            .andExpect(jsonPath("$.vullinggewicht").value(DEFAULT_VULLINGGEWICHT));
    }

    @Test
    @Transactional
    void getNonExistingVulgraadmeting() throws Exception {
        // Get the vulgraadmeting
        restVulgraadmetingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVulgraadmeting() throws Exception {
        // Initialize the database
        vulgraadmetingRepository.saveAndFlush(vulgraadmeting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vulgraadmeting
        Vulgraadmeting updatedVulgraadmeting = vulgraadmetingRepository.findById(vulgraadmeting.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVulgraadmeting are not directly saved in db
        em.detach(updatedVulgraadmeting);
        updatedVulgraadmeting.tijdstip(UPDATED_TIJDSTIP).vulgraad(UPDATED_VULGRAAD).vullinggewicht(UPDATED_VULLINGGEWICHT);

        restVulgraadmetingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVulgraadmeting.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVulgraadmeting))
            )
            .andExpect(status().isOk());

        // Validate the Vulgraadmeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVulgraadmetingToMatchAllProperties(updatedVulgraadmeting);
    }

    @Test
    @Transactional
    void putNonExistingVulgraadmeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulgraadmeting.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVulgraadmetingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vulgraadmeting.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vulgraadmeting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vulgraadmeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVulgraadmeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulgraadmeting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVulgraadmetingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vulgraadmeting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vulgraadmeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVulgraadmeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulgraadmeting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVulgraadmetingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vulgraadmeting)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vulgraadmeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVulgraadmetingWithPatch() throws Exception {
        // Initialize the database
        vulgraadmetingRepository.saveAndFlush(vulgraadmeting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vulgraadmeting using partial update
        Vulgraadmeting partialUpdatedVulgraadmeting = new Vulgraadmeting();
        partialUpdatedVulgraadmeting.setId(vulgraadmeting.getId());

        partialUpdatedVulgraadmeting.tijdstip(UPDATED_TIJDSTIP).vulgraad(UPDATED_VULGRAAD);

        restVulgraadmetingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVulgraadmeting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVulgraadmeting))
            )
            .andExpect(status().isOk());

        // Validate the Vulgraadmeting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVulgraadmetingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVulgraadmeting, vulgraadmeting),
            getPersistedVulgraadmeting(vulgraadmeting)
        );
    }

    @Test
    @Transactional
    void fullUpdateVulgraadmetingWithPatch() throws Exception {
        // Initialize the database
        vulgraadmetingRepository.saveAndFlush(vulgraadmeting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vulgraadmeting using partial update
        Vulgraadmeting partialUpdatedVulgraadmeting = new Vulgraadmeting();
        partialUpdatedVulgraadmeting.setId(vulgraadmeting.getId());

        partialUpdatedVulgraadmeting.tijdstip(UPDATED_TIJDSTIP).vulgraad(UPDATED_VULGRAAD).vullinggewicht(UPDATED_VULLINGGEWICHT);

        restVulgraadmetingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVulgraadmeting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVulgraadmeting))
            )
            .andExpect(status().isOk());

        // Validate the Vulgraadmeting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVulgraadmetingUpdatableFieldsEquals(partialUpdatedVulgraadmeting, getPersistedVulgraadmeting(partialUpdatedVulgraadmeting));
    }

    @Test
    @Transactional
    void patchNonExistingVulgraadmeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulgraadmeting.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVulgraadmetingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vulgraadmeting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vulgraadmeting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vulgraadmeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVulgraadmeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulgraadmeting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVulgraadmetingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vulgraadmeting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vulgraadmeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVulgraadmeting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulgraadmeting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVulgraadmetingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vulgraadmeting)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vulgraadmeting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVulgraadmeting() throws Exception {
        // Initialize the database
        vulgraadmetingRepository.saveAndFlush(vulgraadmeting);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vulgraadmeting
        restVulgraadmetingMockMvc
            .perform(delete(ENTITY_API_URL_ID, vulgraadmeting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vulgraadmetingRepository.count();
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

    protected Vulgraadmeting getPersistedVulgraadmeting(Vulgraadmeting vulgraadmeting) {
        return vulgraadmetingRepository.findById(vulgraadmeting.getId()).orElseThrow();
    }

    protected void assertPersistedVulgraadmetingToMatchAllProperties(Vulgraadmeting expectedVulgraadmeting) {
        assertVulgraadmetingAllPropertiesEquals(expectedVulgraadmeting, getPersistedVulgraadmeting(expectedVulgraadmeting));
    }

    protected void assertPersistedVulgraadmetingToMatchUpdatableProperties(Vulgraadmeting expectedVulgraadmeting) {
        assertVulgraadmetingAllUpdatablePropertiesEquals(expectedVulgraadmeting, getPersistedVulgraadmeting(expectedVulgraadmeting));
    }
}
