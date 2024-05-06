package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.UitnodigingAsserts.*;
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
import nl.ritense.demo.domain.Uitnodiging;
import nl.ritense.demo.repository.UitnodigingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UitnodigingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UitnodigingResourceIT {

    private static final String DEFAULT_AFGEWEZEN = "AAAAAAAAAA";
    private static final String UPDATED_AFGEWEZEN = "BBBBBBBBBB";

    private static final String DEFAULT_DATUM = "AAAAAAAAAA";
    private static final String UPDATED_DATUM = "BBBBBBBBBB";

    private static final String DEFAULT_GEACCEPTEERD = "AAAAAAAAAA";
    private static final String UPDATED_GEACCEPTEERD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/uitnodigings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UitnodigingRepository uitnodigingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUitnodigingMockMvc;

    private Uitnodiging uitnodiging;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitnodiging createEntity(EntityManager em) {
        Uitnodiging uitnodiging = new Uitnodiging().afgewezen(DEFAULT_AFGEWEZEN).datum(DEFAULT_DATUM).geaccepteerd(DEFAULT_GEACCEPTEERD);
        return uitnodiging;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitnodiging createUpdatedEntity(EntityManager em) {
        Uitnodiging uitnodiging = new Uitnodiging().afgewezen(UPDATED_AFGEWEZEN).datum(UPDATED_DATUM).geaccepteerd(UPDATED_GEACCEPTEERD);
        return uitnodiging;
    }

    @BeforeEach
    public void initTest() {
        uitnodiging = createEntity(em);
    }

    @Test
    @Transactional
    void createUitnodiging() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Uitnodiging
        var returnedUitnodiging = om.readValue(
            restUitnodigingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitnodiging)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Uitnodiging.class
        );

        // Validate the Uitnodiging in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertUitnodigingUpdatableFieldsEquals(returnedUitnodiging, getPersistedUitnodiging(returnedUitnodiging));
    }

    @Test
    @Transactional
    void createUitnodigingWithExistingId() throws Exception {
        // Create the Uitnodiging with an existing ID
        uitnodiging.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUitnodigingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitnodiging)))
            .andExpect(status().isBadRequest());

        // Validate the Uitnodiging in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUitnodigings() throws Exception {
        // Initialize the database
        uitnodigingRepository.saveAndFlush(uitnodiging);

        // Get all the uitnodigingList
        restUitnodigingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uitnodiging.getId().intValue())))
            .andExpect(jsonPath("$.[*].afgewezen").value(hasItem(DEFAULT_AFGEWEZEN)))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM)))
            .andExpect(jsonPath("$.[*].geaccepteerd").value(hasItem(DEFAULT_GEACCEPTEERD)));
    }

    @Test
    @Transactional
    void getUitnodiging() throws Exception {
        // Initialize the database
        uitnodigingRepository.saveAndFlush(uitnodiging);

        // Get the uitnodiging
        restUitnodigingMockMvc
            .perform(get(ENTITY_API_URL_ID, uitnodiging.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uitnodiging.getId().intValue()))
            .andExpect(jsonPath("$.afgewezen").value(DEFAULT_AFGEWEZEN))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM))
            .andExpect(jsonPath("$.geaccepteerd").value(DEFAULT_GEACCEPTEERD));
    }

    @Test
    @Transactional
    void getNonExistingUitnodiging() throws Exception {
        // Get the uitnodiging
        restUitnodigingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUitnodiging() throws Exception {
        // Initialize the database
        uitnodigingRepository.saveAndFlush(uitnodiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitnodiging
        Uitnodiging updatedUitnodiging = uitnodigingRepository.findById(uitnodiging.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUitnodiging are not directly saved in db
        em.detach(updatedUitnodiging);
        updatedUitnodiging.afgewezen(UPDATED_AFGEWEZEN).datum(UPDATED_DATUM).geaccepteerd(UPDATED_GEACCEPTEERD);

        restUitnodigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUitnodiging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedUitnodiging))
            )
            .andExpect(status().isOk());

        // Validate the Uitnodiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUitnodigingToMatchAllProperties(updatedUitnodiging);
    }

    @Test
    @Transactional
    void putNonExistingUitnodiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitnodiging.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUitnodigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, uitnodiging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uitnodiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitnodiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUitnodiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitnodiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitnodigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uitnodiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitnodiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUitnodiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitnodiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitnodigingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitnodiging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uitnodiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUitnodigingWithPatch() throws Exception {
        // Initialize the database
        uitnodigingRepository.saveAndFlush(uitnodiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitnodiging using partial update
        Uitnodiging partialUpdatedUitnodiging = new Uitnodiging();
        partialUpdatedUitnodiging.setId(uitnodiging.getId());

        partialUpdatedUitnodiging.geaccepteerd(UPDATED_GEACCEPTEERD);

        restUitnodigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUitnodiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUitnodiging))
            )
            .andExpect(status().isOk());

        // Validate the Uitnodiging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUitnodigingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUitnodiging, uitnodiging),
            getPersistedUitnodiging(uitnodiging)
        );
    }

    @Test
    @Transactional
    void fullUpdateUitnodigingWithPatch() throws Exception {
        // Initialize the database
        uitnodigingRepository.saveAndFlush(uitnodiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitnodiging using partial update
        Uitnodiging partialUpdatedUitnodiging = new Uitnodiging();
        partialUpdatedUitnodiging.setId(uitnodiging.getId());

        partialUpdatedUitnodiging.afgewezen(UPDATED_AFGEWEZEN).datum(UPDATED_DATUM).geaccepteerd(UPDATED_GEACCEPTEERD);

        restUitnodigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUitnodiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUitnodiging))
            )
            .andExpect(status().isOk());

        // Validate the Uitnodiging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUitnodigingUpdatableFieldsEquals(partialUpdatedUitnodiging, getPersistedUitnodiging(partialUpdatedUitnodiging));
    }

    @Test
    @Transactional
    void patchNonExistingUitnodiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitnodiging.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUitnodigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, uitnodiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uitnodiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitnodiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUitnodiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitnodiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitnodigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uitnodiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitnodiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUitnodiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitnodiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitnodigingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(uitnodiging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uitnodiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUitnodiging() throws Exception {
        // Initialize the database
        uitnodigingRepository.saveAndFlush(uitnodiging);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the uitnodiging
        restUitnodigingMockMvc
            .perform(delete(ENTITY_API_URL_ID, uitnodiging.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return uitnodigingRepository.count();
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

    protected Uitnodiging getPersistedUitnodiging(Uitnodiging uitnodiging) {
        return uitnodigingRepository.findById(uitnodiging.getId()).orElseThrow();
    }

    protected void assertPersistedUitnodigingToMatchAllProperties(Uitnodiging expectedUitnodiging) {
        assertUitnodigingAllPropertiesEquals(expectedUitnodiging, getPersistedUitnodiging(expectedUitnodiging));
    }

    protected void assertPersistedUitnodigingToMatchUpdatableProperties(Uitnodiging expectedUitnodiging) {
        assertUitnodigingAllUpdatablePropertiesEquals(expectedUitnodiging, getPersistedUitnodiging(expectedUitnodiging));
    }
}
