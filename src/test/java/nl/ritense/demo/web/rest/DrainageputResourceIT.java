package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DrainageputAsserts.*;
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
import nl.ritense.demo.domain.Drainageput;
import nl.ritense.demo.repository.DrainageputRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DrainageputResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DrainageputResourceIT {

    private static final String DEFAULT_RISICOGEBIED = "AAAAAAAAAA";
    private static final String UPDATED_RISICOGEBIED = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/drainageputs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DrainageputRepository drainageputRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDrainageputMockMvc;

    private Drainageput drainageput;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drainageput createEntity(EntityManager em) {
        Drainageput drainageput = new Drainageput().risicogebied(DEFAULT_RISICOGEBIED).type(DEFAULT_TYPE);
        return drainageput;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drainageput createUpdatedEntity(EntityManager em) {
        Drainageput drainageput = new Drainageput().risicogebied(UPDATED_RISICOGEBIED).type(UPDATED_TYPE);
        return drainageput;
    }

    @BeforeEach
    public void initTest() {
        drainageput = createEntity(em);
    }

    @Test
    @Transactional
    void createDrainageput() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Drainageput
        var returnedDrainageput = om.readValue(
            restDrainageputMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(drainageput)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Drainageput.class
        );

        // Validate the Drainageput in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDrainageputUpdatableFieldsEquals(returnedDrainageput, getPersistedDrainageput(returnedDrainageput));
    }

    @Test
    @Transactional
    void createDrainageputWithExistingId() throws Exception {
        // Create the Drainageput with an existing ID
        drainageput.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrainageputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(drainageput)))
            .andExpect(status().isBadRequest());

        // Validate the Drainageput in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDrainageputs() throws Exception {
        // Initialize the database
        drainageputRepository.saveAndFlush(drainageput);

        // Get all the drainageputList
        restDrainageputMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drainageput.getId().intValue())))
            .andExpect(jsonPath("$.[*].risicogebied").value(hasItem(DEFAULT_RISICOGEBIED)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getDrainageput() throws Exception {
        // Initialize the database
        drainageputRepository.saveAndFlush(drainageput);

        // Get the drainageput
        restDrainageputMockMvc
            .perform(get(ENTITY_API_URL_ID, drainageput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(drainageput.getId().intValue()))
            .andExpect(jsonPath("$.risicogebied").value(DEFAULT_RISICOGEBIED))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingDrainageput() throws Exception {
        // Get the drainageput
        restDrainageputMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDrainageput() throws Exception {
        // Initialize the database
        drainageputRepository.saveAndFlush(drainageput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the drainageput
        Drainageput updatedDrainageput = drainageputRepository.findById(drainageput.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDrainageput are not directly saved in db
        em.detach(updatedDrainageput);
        updatedDrainageput.risicogebied(UPDATED_RISICOGEBIED).type(UPDATED_TYPE);

        restDrainageputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDrainageput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDrainageput))
            )
            .andExpect(status().isOk());

        // Validate the Drainageput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDrainageputToMatchAllProperties(updatedDrainageput);
    }

    @Test
    @Transactional
    void putNonExistingDrainageput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        drainageput.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrainageputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, drainageput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(drainageput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Drainageput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDrainageput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        drainageput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrainageputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(drainageput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Drainageput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDrainageput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        drainageput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrainageputMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(drainageput)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Drainageput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDrainageputWithPatch() throws Exception {
        // Initialize the database
        drainageputRepository.saveAndFlush(drainageput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the drainageput using partial update
        Drainageput partialUpdatedDrainageput = new Drainageput();
        partialUpdatedDrainageput.setId(drainageput.getId());

        restDrainageputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDrainageput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDrainageput))
            )
            .andExpect(status().isOk());

        // Validate the Drainageput in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDrainageputUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDrainageput, drainageput),
            getPersistedDrainageput(drainageput)
        );
    }

    @Test
    @Transactional
    void fullUpdateDrainageputWithPatch() throws Exception {
        // Initialize the database
        drainageputRepository.saveAndFlush(drainageput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the drainageput using partial update
        Drainageput partialUpdatedDrainageput = new Drainageput();
        partialUpdatedDrainageput.setId(drainageput.getId());

        partialUpdatedDrainageput.risicogebied(UPDATED_RISICOGEBIED).type(UPDATED_TYPE);

        restDrainageputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDrainageput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDrainageput))
            )
            .andExpect(status().isOk());

        // Validate the Drainageput in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDrainageputUpdatableFieldsEquals(partialUpdatedDrainageput, getPersistedDrainageput(partialUpdatedDrainageput));
    }

    @Test
    @Transactional
    void patchNonExistingDrainageput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        drainageput.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrainageputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, drainageput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(drainageput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Drainageput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDrainageput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        drainageput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrainageputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(drainageput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Drainageput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDrainageput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        drainageput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrainageputMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(drainageput)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Drainageput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDrainageput() throws Exception {
        // Initialize the database
        drainageputRepository.saveAndFlush(drainageput);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the drainageput
        restDrainageputMockMvc
            .perform(delete(ENTITY_API_URL_ID, drainageput.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return drainageputRepository.count();
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

    protected Drainageput getPersistedDrainageput(Drainageput drainageput) {
        return drainageputRepository.findById(drainageput.getId()).orElseThrow();
    }

    protected void assertPersistedDrainageputToMatchAllProperties(Drainageput expectedDrainageput) {
        assertDrainageputAllPropertiesEquals(expectedDrainageput, getPersistedDrainageput(expectedDrainageput));
    }

    protected void assertPersistedDrainageputToMatchUpdatableProperties(Drainageput expectedDrainageput) {
        assertDrainageputAllUpdatablePropertiesEquals(expectedDrainageput, getPersistedDrainageput(expectedDrainageput));
    }
}
