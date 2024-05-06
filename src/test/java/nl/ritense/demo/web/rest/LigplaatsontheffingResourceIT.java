package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LigplaatsontheffingAsserts.*;
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
import nl.ritense.demo.domain.Ligplaatsontheffing;
import nl.ritense.demo.repository.LigplaatsontheffingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LigplaatsontheffingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LigplaatsontheffingResourceIT {

    private static final String DEFAULT_STICKERNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_STICKERNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ligplaatsontheffings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LigplaatsontheffingRepository ligplaatsontheffingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLigplaatsontheffingMockMvc;

    private Ligplaatsontheffing ligplaatsontheffing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ligplaatsontheffing createEntity(EntityManager em) {
        Ligplaatsontheffing ligplaatsontheffing = new Ligplaatsontheffing().stickernummer(DEFAULT_STICKERNUMMER);
        return ligplaatsontheffing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ligplaatsontheffing createUpdatedEntity(EntityManager em) {
        Ligplaatsontheffing ligplaatsontheffing = new Ligplaatsontheffing().stickernummer(UPDATED_STICKERNUMMER);
        return ligplaatsontheffing;
    }

    @BeforeEach
    public void initTest() {
        ligplaatsontheffing = createEntity(em);
    }

    @Test
    @Transactional
    void createLigplaatsontheffing() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ligplaatsontheffing
        var returnedLigplaatsontheffing = om.readValue(
            restLigplaatsontheffingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ligplaatsontheffing)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Ligplaatsontheffing.class
        );

        // Validate the Ligplaatsontheffing in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLigplaatsontheffingUpdatableFieldsEquals(
            returnedLigplaatsontheffing,
            getPersistedLigplaatsontheffing(returnedLigplaatsontheffing)
        );
    }

    @Test
    @Transactional
    void createLigplaatsontheffingWithExistingId() throws Exception {
        // Create the Ligplaatsontheffing with an existing ID
        ligplaatsontheffing.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLigplaatsontheffingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ligplaatsontheffing)))
            .andExpect(status().isBadRequest());

        // Validate the Ligplaatsontheffing in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLigplaatsontheffings() throws Exception {
        // Initialize the database
        ligplaatsontheffingRepository.saveAndFlush(ligplaatsontheffing);

        // Get all the ligplaatsontheffingList
        restLigplaatsontheffingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ligplaatsontheffing.getId().intValue())))
            .andExpect(jsonPath("$.[*].stickernummer").value(hasItem(DEFAULT_STICKERNUMMER)));
    }

    @Test
    @Transactional
    void getLigplaatsontheffing() throws Exception {
        // Initialize the database
        ligplaatsontheffingRepository.saveAndFlush(ligplaatsontheffing);

        // Get the ligplaatsontheffing
        restLigplaatsontheffingMockMvc
            .perform(get(ENTITY_API_URL_ID, ligplaatsontheffing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ligplaatsontheffing.getId().intValue()))
            .andExpect(jsonPath("$.stickernummer").value(DEFAULT_STICKERNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingLigplaatsontheffing() throws Exception {
        // Get the ligplaatsontheffing
        restLigplaatsontheffingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLigplaatsontheffing() throws Exception {
        // Initialize the database
        ligplaatsontheffingRepository.saveAndFlush(ligplaatsontheffing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ligplaatsontheffing
        Ligplaatsontheffing updatedLigplaatsontheffing = ligplaatsontheffingRepository.findById(ligplaatsontheffing.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLigplaatsontheffing are not directly saved in db
        em.detach(updatedLigplaatsontheffing);
        updatedLigplaatsontheffing.stickernummer(UPDATED_STICKERNUMMER);

        restLigplaatsontheffingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLigplaatsontheffing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLigplaatsontheffing))
            )
            .andExpect(status().isOk());

        // Validate the Ligplaatsontheffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLigplaatsontheffingToMatchAllProperties(updatedLigplaatsontheffing);
    }

    @Test
    @Transactional
    void putNonExistingLigplaatsontheffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligplaatsontheffing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigplaatsontheffingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ligplaatsontheffing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ligplaatsontheffing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ligplaatsontheffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLigplaatsontheffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligplaatsontheffing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigplaatsontheffingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ligplaatsontheffing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ligplaatsontheffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLigplaatsontheffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligplaatsontheffing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigplaatsontheffingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ligplaatsontheffing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ligplaatsontheffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLigplaatsontheffingWithPatch() throws Exception {
        // Initialize the database
        ligplaatsontheffingRepository.saveAndFlush(ligplaatsontheffing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ligplaatsontheffing using partial update
        Ligplaatsontheffing partialUpdatedLigplaatsontheffing = new Ligplaatsontheffing();
        partialUpdatedLigplaatsontheffing.setId(ligplaatsontheffing.getId());

        partialUpdatedLigplaatsontheffing.stickernummer(UPDATED_STICKERNUMMER);

        restLigplaatsontheffingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLigplaatsontheffing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLigplaatsontheffing))
            )
            .andExpect(status().isOk());

        // Validate the Ligplaatsontheffing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLigplaatsontheffingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLigplaatsontheffing, ligplaatsontheffing),
            getPersistedLigplaatsontheffing(ligplaatsontheffing)
        );
    }

    @Test
    @Transactional
    void fullUpdateLigplaatsontheffingWithPatch() throws Exception {
        // Initialize the database
        ligplaatsontheffingRepository.saveAndFlush(ligplaatsontheffing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ligplaatsontheffing using partial update
        Ligplaatsontheffing partialUpdatedLigplaatsontheffing = new Ligplaatsontheffing();
        partialUpdatedLigplaatsontheffing.setId(ligplaatsontheffing.getId());

        partialUpdatedLigplaatsontheffing.stickernummer(UPDATED_STICKERNUMMER);

        restLigplaatsontheffingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLigplaatsontheffing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLigplaatsontheffing))
            )
            .andExpect(status().isOk());

        // Validate the Ligplaatsontheffing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLigplaatsontheffingUpdatableFieldsEquals(
            partialUpdatedLigplaatsontheffing,
            getPersistedLigplaatsontheffing(partialUpdatedLigplaatsontheffing)
        );
    }

    @Test
    @Transactional
    void patchNonExistingLigplaatsontheffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligplaatsontheffing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigplaatsontheffingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ligplaatsontheffing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ligplaatsontheffing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ligplaatsontheffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLigplaatsontheffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligplaatsontheffing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigplaatsontheffingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ligplaatsontheffing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ligplaatsontheffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLigplaatsontheffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligplaatsontheffing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigplaatsontheffingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ligplaatsontheffing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ligplaatsontheffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLigplaatsontheffing() throws Exception {
        // Initialize the database
        ligplaatsontheffingRepository.saveAndFlush(ligplaatsontheffing);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ligplaatsontheffing
        restLigplaatsontheffingMockMvc
            .perform(delete(ENTITY_API_URL_ID, ligplaatsontheffing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ligplaatsontheffingRepository.count();
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

    protected Ligplaatsontheffing getPersistedLigplaatsontheffing(Ligplaatsontheffing ligplaatsontheffing) {
        return ligplaatsontheffingRepository.findById(ligplaatsontheffing.getId()).orElseThrow();
    }

    protected void assertPersistedLigplaatsontheffingToMatchAllProperties(Ligplaatsontheffing expectedLigplaatsontheffing) {
        assertLigplaatsontheffingAllPropertiesEquals(
            expectedLigplaatsontheffing,
            getPersistedLigplaatsontheffing(expectedLigplaatsontheffing)
        );
    }

    protected void assertPersistedLigplaatsontheffingToMatchUpdatableProperties(Ligplaatsontheffing expectedLigplaatsontheffing) {
        assertLigplaatsontheffingAllUpdatablePropertiesEquals(
            expectedLigplaatsontheffing,
            getPersistedLigplaatsontheffing(expectedLigplaatsontheffing)
        );
    }
}
