package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.StuwgebiedAsserts.*;
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
import nl.ritense.demo.domain.Stuwgebied;
import nl.ritense.demo.repository.StuwgebiedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StuwgebiedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StuwgebiedResourceIT {

    private static final String DEFAULT_BEMALINGSGEBIED = "AAAAAAAAAA";
    private static final String UPDATED_BEMALINGSGEBIED = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/stuwgebieds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StuwgebiedRepository stuwgebiedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStuwgebiedMockMvc;

    private Stuwgebied stuwgebied;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stuwgebied createEntity(EntityManager em) {
        Stuwgebied stuwgebied = new Stuwgebied().bemalingsgebied(DEFAULT_BEMALINGSGEBIED);
        return stuwgebied;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stuwgebied createUpdatedEntity(EntityManager em) {
        Stuwgebied stuwgebied = new Stuwgebied().bemalingsgebied(UPDATED_BEMALINGSGEBIED);
        return stuwgebied;
    }

    @BeforeEach
    public void initTest() {
        stuwgebied = createEntity(em);
    }

    @Test
    @Transactional
    void createStuwgebied() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Stuwgebied
        var returnedStuwgebied = om.readValue(
            restStuwgebiedMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stuwgebied)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Stuwgebied.class
        );

        // Validate the Stuwgebied in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStuwgebiedUpdatableFieldsEquals(returnedStuwgebied, getPersistedStuwgebied(returnedStuwgebied));
    }

    @Test
    @Transactional
    void createStuwgebiedWithExistingId() throws Exception {
        // Create the Stuwgebied with an existing ID
        stuwgebied.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStuwgebiedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stuwgebied)))
            .andExpect(status().isBadRequest());

        // Validate the Stuwgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStuwgebieds() throws Exception {
        // Initialize the database
        stuwgebiedRepository.saveAndFlush(stuwgebied);

        // Get all the stuwgebiedList
        restStuwgebiedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stuwgebied.getId().intValue())))
            .andExpect(jsonPath("$.[*].bemalingsgebied").value(hasItem(DEFAULT_BEMALINGSGEBIED)));
    }

    @Test
    @Transactional
    void getStuwgebied() throws Exception {
        // Initialize the database
        stuwgebiedRepository.saveAndFlush(stuwgebied);

        // Get the stuwgebied
        restStuwgebiedMockMvc
            .perform(get(ENTITY_API_URL_ID, stuwgebied.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stuwgebied.getId().intValue()))
            .andExpect(jsonPath("$.bemalingsgebied").value(DEFAULT_BEMALINGSGEBIED));
    }

    @Test
    @Transactional
    void getNonExistingStuwgebied() throws Exception {
        // Get the stuwgebied
        restStuwgebiedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStuwgebied() throws Exception {
        // Initialize the database
        stuwgebiedRepository.saveAndFlush(stuwgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stuwgebied
        Stuwgebied updatedStuwgebied = stuwgebiedRepository.findById(stuwgebied.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStuwgebied are not directly saved in db
        em.detach(updatedStuwgebied);
        updatedStuwgebied.bemalingsgebied(UPDATED_BEMALINGSGEBIED);

        restStuwgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStuwgebied.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStuwgebied))
            )
            .andExpect(status().isOk());

        // Validate the Stuwgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStuwgebiedToMatchAllProperties(updatedStuwgebied);
    }

    @Test
    @Transactional
    void putNonExistingStuwgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stuwgebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStuwgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stuwgebied.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stuwgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stuwgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStuwgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stuwgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStuwgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(stuwgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stuwgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStuwgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stuwgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStuwgebiedMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stuwgebied)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stuwgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStuwgebiedWithPatch() throws Exception {
        // Initialize the database
        stuwgebiedRepository.saveAndFlush(stuwgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stuwgebied using partial update
        Stuwgebied partialUpdatedStuwgebied = new Stuwgebied();
        partialUpdatedStuwgebied.setId(stuwgebied.getId());

        restStuwgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStuwgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStuwgebied))
            )
            .andExpect(status().isOk());

        // Validate the Stuwgebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStuwgebiedUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStuwgebied, stuwgebied),
            getPersistedStuwgebied(stuwgebied)
        );
    }

    @Test
    @Transactional
    void fullUpdateStuwgebiedWithPatch() throws Exception {
        // Initialize the database
        stuwgebiedRepository.saveAndFlush(stuwgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stuwgebied using partial update
        Stuwgebied partialUpdatedStuwgebied = new Stuwgebied();
        partialUpdatedStuwgebied.setId(stuwgebied.getId());

        partialUpdatedStuwgebied.bemalingsgebied(UPDATED_BEMALINGSGEBIED);

        restStuwgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStuwgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStuwgebied))
            )
            .andExpect(status().isOk());

        // Validate the Stuwgebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStuwgebiedUpdatableFieldsEquals(partialUpdatedStuwgebied, getPersistedStuwgebied(partialUpdatedStuwgebied));
    }

    @Test
    @Transactional
    void patchNonExistingStuwgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stuwgebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStuwgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stuwgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(stuwgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stuwgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStuwgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stuwgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStuwgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(stuwgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stuwgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStuwgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stuwgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStuwgebiedMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(stuwgebied)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stuwgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStuwgebied() throws Exception {
        // Initialize the database
        stuwgebiedRepository.saveAndFlush(stuwgebied);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the stuwgebied
        restStuwgebiedMockMvc
            .perform(delete(ENTITY_API_URL_ID, stuwgebied.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return stuwgebiedRepository.count();
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

    protected Stuwgebied getPersistedStuwgebied(Stuwgebied stuwgebied) {
        return stuwgebiedRepository.findById(stuwgebied.getId()).orElseThrow();
    }

    protected void assertPersistedStuwgebiedToMatchAllProperties(Stuwgebied expectedStuwgebied) {
        assertStuwgebiedAllPropertiesEquals(expectedStuwgebied, getPersistedStuwgebied(expectedStuwgebied));
    }

    protected void assertPersistedStuwgebiedToMatchUpdatableProperties(Stuwgebied expectedStuwgebied) {
        assertStuwgebiedAllUpdatablePropertiesEquals(expectedStuwgebied, getPersistedStuwgebied(expectedStuwgebied));
    }
}
