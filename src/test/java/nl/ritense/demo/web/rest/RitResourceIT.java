package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RitAsserts.*;
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
import nl.ritense.demo.domain.Rit;
import nl.ritense.demo.repository.RitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RitResourceIT {

    private static final String DEFAULT_EINDTIJD = "AAAAAAAAAA";
    private static final String UPDATED_EINDTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_RITCODE = "AAAAAAAAAA";
    private static final String UPDATED_RITCODE = "BBBBBBBBBB";

    private static final String DEFAULT_STARTTIJD = "AAAAAAAAAA";
    private static final String UPDATED_STARTTIJD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RitRepository ritRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRitMockMvc;

    private Rit rit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rit createEntity(EntityManager em) {
        Rit rit = new Rit().eindtijd(DEFAULT_EINDTIJD).ritcode(DEFAULT_RITCODE).starttijd(DEFAULT_STARTTIJD);
        return rit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rit createUpdatedEntity(EntityManager em) {
        Rit rit = new Rit().eindtijd(UPDATED_EINDTIJD).ritcode(UPDATED_RITCODE).starttijd(UPDATED_STARTTIJD);
        return rit;
    }

    @BeforeEach
    public void initTest() {
        rit = createEntity(em);
    }

    @Test
    @Transactional
    void createRit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Rit
        var returnedRit = om.readValue(
            restRitMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rit)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Rit.class
        );

        // Validate the Rit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRitUpdatableFieldsEquals(returnedRit, getPersistedRit(returnedRit));
    }

    @Test
    @Transactional
    void createRitWithExistingId() throws Exception {
        // Create the Rit with an existing ID
        rit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rit)))
            .andExpect(status().isBadRequest());

        // Validate the Rit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRits() throws Exception {
        // Initialize the database
        ritRepository.saveAndFlush(rit);

        // Get all the ritList
        restRitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rit.getId().intValue())))
            .andExpect(jsonPath("$.[*].eindtijd").value(hasItem(DEFAULT_EINDTIJD)))
            .andExpect(jsonPath("$.[*].ritcode").value(hasItem(DEFAULT_RITCODE)))
            .andExpect(jsonPath("$.[*].starttijd").value(hasItem(DEFAULT_STARTTIJD)));
    }

    @Test
    @Transactional
    void getRit() throws Exception {
        // Initialize the database
        ritRepository.saveAndFlush(rit);

        // Get the rit
        restRitMockMvc
            .perform(get(ENTITY_API_URL_ID, rit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rit.getId().intValue()))
            .andExpect(jsonPath("$.eindtijd").value(DEFAULT_EINDTIJD))
            .andExpect(jsonPath("$.ritcode").value(DEFAULT_RITCODE))
            .andExpect(jsonPath("$.starttijd").value(DEFAULT_STARTTIJD));
    }

    @Test
    @Transactional
    void getNonExistingRit() throws Exception {
        // Get the rit
        restRitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRit() throws Exception {
        // Initialize the database
        ritRepository.saveAndFlush(rit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rit
        Rit updatedRit = ritRepository.findById(rit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRit are not directly saved in db
        em.detach(updatedRit);
        updatedRit.eindtijd(UPDATED_EINDTIJD).ritcode(UPDATED_RITCODE).starttijd(UPDATED_STARTTIJD);

        restRitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRit.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(updatedRit))
            )
            .andExpect(status().isOk());

        // Validate the Rit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRitToMatchAllProperties(updatedRit);
    }

    @Test
    @Transactional
    void putNonExistingRit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRitMockMvc
            .perform(put(ENTITY_API_URL_ID, rit.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rit)))
            .andExpect(status().isBadRequest());

        // Validate the Rit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRitWithPatch() throws Exception {
        // Initialize the database
        ritRepository.saveAndFlush(rit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rit using partial update
        Rit partialUpdatedRit = new Rit();
        partialUpdatedRit.setId(rit.getId());

        partialUpdatedRit.eindtijd(UPDATED_EINDTIJD).ritcode(UPDATED_RITCODE);

        restRitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRit))
            )
            .andExpect(status().isOk());

        // Validate the Rit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRitUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRit, rit), getPersistedRit(rit));
    }

    @Test
    @Transactional
    void fullUpdateRitWithPatch() throws Exception {
        // Initialize the database
        ritRepository.saveAndFlush(rit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rit using partial update
        Rit partialUpdatedRit = new Rit();
        partialUpdatedRit.setId(rit.getId());

        partialUpdatedRit.eindtijd(UPDATED_EINDTIJD).ritcode(UPDATED_RITCODE).starttijd(UPDATED_STARTTIJD);

        restRitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRit))
            )
            .andExpect(status().isOk());

        // Validate the Rit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRitUpdatableFieldsEquals(partialUpdatedRit, getPersistedRit(partialUpdatedRit));
    }

    @Test
    @Transactional
    void patchNonExistingRit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRitMockMvc
            .perform(patch(ENTITY_API_URL_ID, rit.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rit)))
            .andExpect(status().isBadRequest());

        // Validate the Rit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRit() throws Exception {
        // Initialize the database
        ritRepository.saveAndFlush(rit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rit
        restRitMockMvc.perform(delete(ENTITY_API_URL_ID, rit.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ritRepository.count();
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

    protected Rit getPersistedRit(Rit rit) {
        return ritRepository.findById(rit.getId()).orElseThrow();
    }

    protected void assertPersistedRitToMatchAllProperties(Rit expectedRit) {
        assertRitAllPropertiesEquals(expectedRit, getPersistedRit(expectedRit));
    }

    protected void assertPersistedRitToMatchUpdatableProperties(Rit expectedRit) {
        assertRitAllUpdatablePropertiesEquals(expectedRit, getPersistedRit(expectedRit));
    }
}
