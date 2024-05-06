package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AfvalbakAsserts.*;
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
import nl.ritense.demo.domain.Afvalbak;
import nl.ritense.demo.repository.AfvalbakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AfvalbakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AfvalbakResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/afvalbaks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AfvalbakRepository afvalbakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAfvalbakMockMvc;

    private Afvalbak afvalbak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Afvalbak createEntity(EntityManager em) {
        Afvalbak afvalbak = new Afvalbak().type(DEFAULT_TYPE).typeplus(DEFAULT_TYPEPLUS);
        return afvalbak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Afvalbak createUpdatedEntity(EntityManager em) {
        Afvalbak afvalbak = new Afvalbak().type(UPDATED_TYPE).typeplus(UPDATED_TYPEPLUS);
        return afvalbak;
    }

    @BeforeEach
    public void initTest() {
        afvalbak = createEntity(em);
    }

    @Test
    @Transactional
    void createAfvalbak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Afvalbak
        var returnedAfvalbak = om.readValue(
            restAfvalbakMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(afvalbak)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Afvalbak.class
        );

        // Validate the Afvalbak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAfvalbakUpdatableFieldsEquals(returnedAfvalbak, getPersistedAfvalbak(returnedAfvalbak));
    }

    @Test
    @Transactional
    void createAfvalbakWithExistingId() throws Exception {
        // Create the Afvalbak with an existing ID
        afvalbak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAfvalbakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(afvalbak)))
            .andExpect(status().isBadRequest());

        // Validate the Afvalbak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAfvalbaks() throws Exception {
        // Initialize the database
        afvalbakRepository.saveAndFlush(afvalbak);

        // Get all the afvalbakList
        restAfvalbakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(afvalbak.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].typeplus").value(hasItem(DEFAULT_TYPEPLUS)));
    }

    @Test
    @Transactional
    void getAfvalbak() throws Exception {
        // Initialize the database
        afvalbakRepository.saveAndFlush(afvalbak);

        // Get the afvalbak
        restAfvalbakMockMvc
            .perform(get(ENTITY_API_URL_ID, afvalbak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(afvalbak.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.typeplus").value(DEFAULT_TYPEPLUS));
    }

    @Test
    @Transactional
    void getNonExistingAfvalbak() throws Exception {
        // Get the afvalbak
        restAfvalbakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAfvalbak() throws Exception {
        // Initialize the database
        afvalbakRepository.saveAndFlush(afvalbak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the afvalbak
        Afvalbak updatedAfvalbak = afvalbakRepository.findById(afvalbak.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAfvalbak are not directly saved in db
        em.detach(updatedAfvalbak);
        updatedAfvalbak.type(UPDATED_TYPE).typeplus(UPDATED_TYPEPLUS);

        restAfvalbakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAfvalbak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAfvalbak))
            )
            .andExpect(status().isOk());

        // Validate the Afvalbak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAfvalbakToMatchAllProperties(updatedAfvalbak);
    }

    @Test
    @Transactional
    void putNonExistingAfvalbak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afvalbak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAfvalbakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, afvalbak.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(afvalbak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afvalbak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAfvalbak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afvalbak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfvalbakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(afvalbak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afvalbak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAfvalbak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afvalbak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfvalbakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(afvalbak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Afvalbak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAfvalbakWithPatch() throws Exception {
        // Initialize the database
        afvalbakRepository.saveAndFlush(afvalbak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the afvalbak using partial update
        Afvalbak partialUpdatedAfvalbak = new Afvalbak();
        partialUpdatedAfvalbak.setId(afvalbak.getId());

        partialUpdatedAfvalbak.typeplus(UPDATED_TYPEPLUS);

        restAfvalbakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAfvalbak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAfvalbak))
            )
            .andExpect(status().isOk());

        // Validate the Afvalbak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAfvalbakUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAfvalbak, afvalbak), getPersistedAfvalbak(afvalbak));
    }

    @Test
    @Transactional
    void fullUpdateAfvalbakWithPatch() throws Exception {
        // Initialize the database
        afvalbakRepository.saveAndFlush(afvalbak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the afvalbak using partial update
        Afvalbak partialUpdatedAfvalbak = new Afvalbak();
        partialUpdatedAfvalbak.setId(afvalbak.getId());

        partialUpdatedAfvalbak.type(UPDATED_TYPE).typeplus(UPDATED_TYPEPLUS);

        restAfvalbakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAfvalbak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAfvalbak))
            )
            .andExpect(status().isOk());

        // Validate the Afvalbak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAfvalbakUpdatableFieldsEquals(partialUpdatedAfvalbak, getPersistedAfvalbak(partialUpdatedAfvalbak));
    }

    @Test
    @Transactional
    void patchNonExistingAfvalbak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afvalbak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAfvalbakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, afvalbak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(afvalbak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afvalbak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAfvalbak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afvalbak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfvalbakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(afvalbak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afvalbak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAfvalbak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afvalbak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfvalbakMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(afvalbak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Afvalbak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAfvalbak() throws Exception {
        // Initialize the database
        afvalbakRepository.saveAndFlush(afvalbak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the afvalbak
        restAfvalbakMockMvc
            .perform(delete(ENTITY_API_URL_ID, afvalbak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return afvalbakRepository.count();
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

    protected Afvalbak getPersistedAfvalbak(Afvalbak afvalbak) {
        return afvalbakRepository.findById(afvalbak.getId()).orElseThrow();
    }

    protected void assertPersistedAfvalbakToMatchAllProperties(Afvalbak expectedAfvalbak) {
        assertAfvalbakAllPropertiesEquals(expectedAfvalbak, getPersistedAfvalbak(expectedAfvalbak));
    }

    protected void assertPersistedAfvalbakToMatchUpdatableProperties(Afvalbak expectedAfvalbak) {
        assertAfvalbakAllUpdatablePropertiesEquals(expectedAfvalbak, getPersistedAfvalbak(expectedAfvalbak));
    }
}
