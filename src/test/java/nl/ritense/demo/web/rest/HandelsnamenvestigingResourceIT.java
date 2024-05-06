package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.HandelsnamenvestigingAsserts.*;
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
import nl.ritense.demo.domain.Handelsnamenvestiging;
import nl.ritense.demo.repository.HandelsnamenvestigingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HandelsnamenvestigingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HandelsnamenvestigingResourceIT {

    private static final String DEFAULT_HANDELSNAAM = "AAAAAAAAAA";
    private static final String UPDATED_HANDELSNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_VERKORTENAAM = "AAAAAAAAAA";
    private static final String UPDATED_VERKORTENAAM = "BBBBBBBBBB";

    private static final String DEFAULT_VOLGORDE = "AAAAAAAAAA";
    private static final String UPDATED_VOLGORDE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/handelsnamenvestigings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HandelsnamenvestigingRepository handelsnamenvestigingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHandelsnamenvestigingMockMvc;

    private Handelsnamenvestiging handelsnamenvestiging;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Handelsnamenvestiging createEntity(EntityManager em) {
        Handelsnamenvestiging handelsnamenvestiging = new Handelsnamenvestiging()
            .handelsnaam(DEFAULT_HANDELSNAAM)
            .verkortenaam(DEFAULT_VERKORTENAAM)
            .volgorde(DEFAULT_VOLGORDE);
        return handelsnamenvestiging;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Handelsnamenvestiging createUpdatedEntity(EntityManager em) {
        Handelsnamenvestiging handelsnamenvestiging = new Handelsnamenvestiging()
            .handelsnaam(UPDATED_HANDELSNAAM)
            .verkortenaam(UPDATED_VERKORTENAAM)
            .volgorde(UPDATED_VOLGORDE);
        return handelsnamenvestiging;
    }

    @BeforeEach
    public void initTest() {
        handelsnamenvestiging = createEntity(em);
    }

    @Test
    @Transactional
    void createHandelsnamenvestiging() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Handelsnamenvestiging
        var returnedHandelsnamenvestiging = om.readValue(
            restHandelsnamenvestigingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(handelsnamenvestiging)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Handelsnamenvestiging.class
        );

        // Validate the Handelsnamenvestiging in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHandelsnamenvestigingUpdatableFieldsEquals(
            returnedHandelsnamenvestiging,
            getPersistedHandelsnamenvestiging(returnedHandelsnamenvestiging)
        );
    }

    @Test
    @Transactional
    void createHandelsnamenvestigingWithExistingId() throws Exception {
        // Create the Handelsnamenvestiging with an existing ID
        handelsnamenvestiging.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHandelsnamenvestigingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(handelsnamenvestiging)))
            .andExpect(status().isBadRequest());

        // Validate the Handelsnamenvestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHandelsnamenvestigings() throws Exception {
        // Initialize the database
        handelsnamenvestigingRepository.saveAndFlush(handelsnamenvestiging);

        // Get all the handelsnamenvestigingList
        restHandelsnamenvestigingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(handelsnamenvestiging.getId().intValue())))
            .andExpect(jsonPath("$.[*].handelsnaam").value(hasItem(DEFAULT_HANDELSNAAM)))
            .andExpect(jsonPath("$.[*].verkortenaam").value(hasItem(DEFAULT_VERKORTENAAM)))
            .andExpect(jsonPath("$.[*].volgorde").value(hasItem(DEFAULT_VOLGORDE)));
    }

    @Test
    @Transactional
    void getHandelsnamenvestiging() throws Exception {
        // Initialize the database
        handelsnamenvestigingRepository.saveAndFlush(handelsnamenvestiging);

        // Get the handelsnamenvestiging
        restHandelsnamenvestigingMockMvc
            .perform(get(ENTITY_API_URL_ID, handelsnamenvestiging.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(handelsnamenvestiging.getId().intValue()))
            .andExpect(jsonPath("$.handelsnaam").value(DEFAULT_HANDELSNAAM))
            .andExpect(jsonPath("$.verkortenaam").value(DEFAULT_VERKORTENAAM))
            .andExpect(jsonPath("$.volgorde").value(DEFAULT_VOLGORDE));
    }

    @Test
    @Transactional
    void getNonExistingHandelsnamenvestiging() throws Exception {
        // Get the handelsnamenvestiging
        restHandelsnamenvestigingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHandelsnamenvestiging() throws Exception {
        // Initialize the database
        handelsnamenvestigingRepository.saveAndFlush(handelsnamenvestiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the handelsnamenvestiging
        Handelsnamenvestiging updatedHandelsnamenvestiging = handelsnamenvestigingRepository
            .findById(handelsnamenvestiging.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedHandelsnamenvestiging are not directly saved in db
        em.detach(updatedHandelsnamenvestiging);
        updatedHandelsnamenvestiging.handelsnaam(UPDATED_HANDELSNAAM).verkortenaam(UPDATED_VERKORTENAAM).volgorde(UPDATED_VOLGORDE);

        restHandelsnamenvestigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHandelsnamenvestiging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHandelsnamenvestiging))
            )
            .andExpect(status().isOk());

        // Validate the Handelsnamenvestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHandelsnamenvestigingToMatchAllProperties(updatedHandelsnamenvestiging);
    }

    @Test
    @Transactional
    void putNonExistingHandelsnamenvestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        handelsnamenvestiging.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHandelsnamenvestigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, handelsnamenvestiging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(handelsnamenvestiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Handelsnamenvestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHandelsnamenvestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        handelsnamenvestiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHandelsnamenvestigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(handelsnamenvestiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Handelsnamenvestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHandelsnamenvestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        handelsnamenvestiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHandelsnamenvestigingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(handelsnamenvestiging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Handelsnamenvestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHandelsnamenvestigingWithPatch() throws Exception {
        // Initialize the database
        handelsnamenvestigingRepository.saveAndFlush(handelsnamenvestiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the handelsnamenvestiging using partial update
        Handelsnamenvestiging partialUpdatedHandelsnamenvestiging = new Handelsnamenvestiging();
        partialUpdatedHandelsnamenvestiging.setId(handelsnamenvestiging.getId());

        partialUpdatedHandelsnamenvestiging.handelsnaam(UPDATED_HANDELSNAAM);

        restHandelsnamenvestigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHandelsnamenvestiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHandelsnamenvestiging))
            )
            .andExpect(status().isOk());

        // Validate the Handelsnamenvestiging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHandelsnamenvestigingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHandelsnamenvestiging, handelsnamenvestiging),
            getPersistedHandelsnamenvestiging(handelsnamenvestiging)
        );
    }

    @Test
    @Transactional
    void fullUpdateHandelsnamenvestigingWithPatch() throws Exception {
        // Initialize the database
        handelsnamenvestigingRepository.saveAndFlush(handelsnamenvestiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the handelsnamenvestiging using partial update
        Handelsnamenvestiging partialUpdatedHandelsnamenvestiging = new Handelsnamenvestiging();
        partialUpdatedHandelsnamenvestiging.setId(handelsnamenvestiging.getId());

        partialUpdatedHandelsnamenvestiging.handelsnaam(UPDATED_HANDELSNAAM).verkortenaam(UPDATED_VERKORTENAAM).volgorde(UPDATED_VOLGORDE);

        restHandelsnamenvestigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHandelsnamenvestiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHandelsnamenvestiging))
            )
            .andExpect(status().isOk());

        // Validate the Handelsnamenvestiging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHandelsnamenvestigingUpdatableFieldsEquals(
            partialUpdatedHandelsnamenvestiging,
            getPersistedHandelsnamenvestiging(partialUpdatedHandelsnamenvestiging)
        );
    }

    @Test
    @Transactional
    void patchNonExistingHandelsnamenvestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        handelsnamenvestiging.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHandelsnamenvestigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, handelsnamenvestiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(handelsnamenvestiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Handelsnamenvestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHandelsnamenvestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        handelsnamenvestiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHandelsnamenvestigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(handelsnamenvestiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Handelsnamenvestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHandelsnamenvestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        handelsnamenvestiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHandelsnamenvestigingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(handelsnamenvestiging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Handelsnamenvestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHandelsnamenvestiging() throws Exception {
        // Initialize the database
        handelsnamenvestigingRepository.saveAndFlush(handelsnamenvestiging);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the handelsnamenvestiging
        restHandelsnamenvestigingMockMvc
            .perform(delete(ENTITY_API_URL_ID, handelsnamenvestiging.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return handelsnamenvestigingRepository.count();
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

    protected Handelsnamenvestiging getPersistedHandelsnamenvestiging(Handelsnamenvestiging handelsnamenvestiging) {
        return handelsnamenvestigingRepository.findById(handelsnamenvestiging.getId()).orElseThrow();
    }

    protected void assertPersistedHandelsnamenvestigingToMatchAllProperties(Handelsnamenvestiging expectedHandelsnamenvestiging) {
        assertHandelsnamenvestigingAllPropertiesEquals(
            expectedHandelsnamenvestiging,
            getPersistedHandelsnamenvestiging(expectedHandelsnamenvestiging)
        );
    }

    protected void assertPersistedHandelsnamenvestigingToMatchUpdatableProperties(Handelsnamenvestiging expectedHandelsnamenvestiging) {
        assertHandelsnamenvestigingAllUpdatablePropertiesEquals(
            expectedHandelsnamenvestiging,
            getPersistedHandelsnamenvestiging(expectedHandelsnamenvestiging)
        );
    }
}
