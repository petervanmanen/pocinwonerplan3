package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ActiviteitsoortAsserts.*;
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
import nl.ritense.demo.domain.Activiteitsoort;
import nl.ritense.demo.repository.ActiviteitsoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ActiviteitsoortResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class ActiviteitsoortResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/activiteitsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ActiviteitsoortRepository activiteitsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActiviteitsoortMockMvc;

    private Activiteitsoort activiteitsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activiteitsoort createEntity(EntityManager em) {
        Activiteitsoort activiteitsoort = new Activiteitsoort().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return activiteitsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activiteitsoort createUpdatedEntity(EntityManager em) {
        Activiteitsoort activiteitsoort = new Activiteitsoort().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return activiteitsoort;
    }

    @BeforeEach
    public void initTest() {
        activiteitsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createActiviteitsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Activiteitsoort
        var returnedActiviteitsoort = om.readValue(
            restActiviteitsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activiteitsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Activiteitsoort.class
        );

        // Validate the Activiteitsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertActiviteitsoortUpdatableFieldsEquals(returnedActiviteitsoort, getPersistedActiviteitsoort(returnedActiviteitsoort));
    }

    @Test
    @Transactional
    void createActiviteitsoortWithExistingId() throws Exception {
        // Create the Activiteitsoort with an existing ID
        activiteitsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActiviteitsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activiteitsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Activiteitsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActiviteitsoorts() throws Exception {
        // Initialize the database
        activiteitsoortRepository.saveAndFlush(activiteitsoort);

        // Get all the activiteitsoortList
        restActiviteitsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activiteitsoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getActiviteitsoort() throws Exception {
        // Initialize the database
        activiteitsoortRepository.saveAndFlush(activiteitsoort);

        // Get the activiteitsoort
        restActiviteitsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, activiteitsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activiteitsoort.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingActiviteitsoort() throws Exception {
        // Get the activiteitsoort
        restActiviteitsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingActiviteitsoort() throws Exception {
        // Initialize the database
        activiteitsoortRepository.saveAndFlush(activiteitsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activiteitsoort
        Activiteitsoort updatedActiviteitsoort = activiteitsoortRepository.findById(activiteitsoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedActiviteitsoort are not directly saved in db
        em.detach(updatedActiviteitsoort);
        updatedActiviteitsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restActiviteitsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActiviteitsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedActiviteitsoort))
            )
            .andExpect(status().isOk());

        // Validate the Activiteitsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedActiviteitsoortToMatchAllProperties(updatedActiviteitsoort);
    }

    @Test
    @Transactional
    void putNonExistingActiviteitsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activiteitsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActiviteitsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activiteitsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(activiteitsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activiteitsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActiviteitsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activiteitsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActiviteitsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(activiteitsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activiteitsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActiviteitsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activiteitsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActiviteitsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activiteitsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activiteitsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActiviteitsoortWithPatch() throws Exception {
        // Initialize the database
        activiteitsoortRepository.saveAndFlush(activiteitsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activiteitsoort using partial update
        Activiteitsoort partialUpdatedActiviteitsoort = new Activiteitsoort();
        partialUpdatedActiviteitsoort.setId(activiteitsoort.getId());

        restActiviteitsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActiviteitsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedActiviteitsoort))
            )
            .andExpect(status().isOk());

        // Validate the Activiteitsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertActiviteitsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedActiviteitsoort, activiteitsoort),
            getPersistedActiviteitsoort(activiteitsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateActiviteitsoortWithPatch() throws Exception {
        // Initialize the database
        activiteitsoortRepository.saveAndFlush(activiteitsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activiteitsoort using partial update
        Activiteitsoort partialUpdatedActiviteitsoort = new Activiteitsoort();
        partialUpdatedActiviteitsoort.setId(activiteitsoort.getId());

        partialUpdatedActiviteitsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restActiviteitsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActiviteitsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedActiviteitsoort))
            )
            .andExpect(status().isOk());

        // Validate the Activiteitsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertActiviteitsoortUpdatableFieldsEquals(
            partialUpdatedActiviteitsoort,
            getPersistedActiviteitsoort(partialUpdatedActiviteitsoort)
        );
    }

    @Test
    @Transactional
    void patchNonExistingActiviteitsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activiteitsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActiviteitsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activiteitsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(activiteitsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activiteitsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActiviteitsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activiteitsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActiviteitsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(activiteitsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activiteitsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActiviteitsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activiteitsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActiviteitsoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(activiteitsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activiteitsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActiviteitsoort() throws Exception {
        // Initialize the database
        activiteitsoortRepository.saveAndFlush(activiteitsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the activiteitsoort
        restActiviteitsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, activiteitsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return activiteitsoortRepository.count();
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

    protected Activiteitsoort getPersistedActiviteitsoort(Activiteitsoort activiteitsoort) {
        return activiteitsoortRepository.findById(activiteitsoort.getId()).orElseThrow();
    }

    protected void assertPersistedActiviteitsoortToMatchAllProperties(Activiteitsoort expectedActiviteitsoort) {
        assertActiviteitsoortAllPropertiesEquals(expectedActiviteitsoort, getPersistedActiviteitsoort(expectedActiviteitsoort));
    }

    protected void assertPersistedActiviteitsoortToMatchUpdatableProperties(Activiteitsoort expectedActiviteitsoort) {
        assertActiviteitsoortAllUpdatablePropertiesEquals(expectedActiviteitsoort, getPersistedActiviteitsoort(expectedActiviteitsoort));
    }
}
