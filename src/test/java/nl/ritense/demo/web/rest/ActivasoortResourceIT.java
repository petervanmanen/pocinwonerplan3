package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ActivasoortAsserts.*;
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
import nl.ritense.demo.domain.Activasoort;
import nl.ritense.demo.repository.ActivasoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ActivasoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActivasoortResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/activasoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ActivasoortRepository activasoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivasoortMockMvc;

    private Activasoort activasoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activasoort createEntity(EntityManager em) {
        Activasoort activasoort = new Activasoort().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return activasoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activasoort createUpdatedEntity(EntityManager em) {
        Activasoort activasoort = new Activasoort().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return activasoort;
    }

    @BeforeEach
    public void initTest() {
        activasoort = createEntity(em);
    }

    @Test
    @Transactional
    void createActivasoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Activasoort
        var returnedActivasoort = om.readValue(
            restActivasoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activasoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Activasoort.class
        );

        // Validate the Activasoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertActivasoortUpdatableFieldsEquals(returnedActivasoort, getPersistedActivasoort(returnedActivasoort));
    }

    @Test
    @Transactional
    void createActivasoortWithExistingId() throws Exception {
        // Create the Activasoort with an existing ID
        activasoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivasoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activasoort)))
            .andExpect(status().isBadRequest());

        // Validate the Activasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActivasoorts() throws Exception {
        // Initialize the database
        activasoortRepository.saveAndFlush(activasoort);

        // Get all the activasoortList
        restActivasoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activasoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getActivasoort() throws Exception {
        // Initialize the database
        activasoortRepository.saveAndFlush(activasoort);

        // Get the activasoort
        restActivasoortMockMvc
            .perform(get(ENTITY_API_URL_ID, activasoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activasoort.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingActivasoort() throws Exception {
        // Get the activasoort
        restActivasoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingActivasoort() throws Exception {
        // Initialize the database
        activasoortRepository.saveAndFlush(activasoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activasoort
        Activasoort updatedActivasoort = activasoortRepository.findById(activasoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedActivasoort are not directly saved in db
        em.detach(updatedActivasoort);
        updatedActivasoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restActivasoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActivasoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedActivasoort))
            )
            .andExpect(status().isOk());

        // Validate the Activasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedActivasoortToMatchAllProperties(updatedActivasoort);
    }

    @Test
    @Transactional
    void putNonExistingActivasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activasoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivasoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activasoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(activasoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActivasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activasoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivasoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(activasoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActivasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activasoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivasoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activasoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActivasoortWithPatch() throws Exception {
        // Initialize the database
        activasoortRepository.saveAndFlush(activasoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activasoort using partial update
        Activasoort partialUpdatedActivasoort = new Activasoort();
        partialUpdatedActivasoort.setId(activasoort.getId());

        partialUpdatedActivasoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restActivasoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivasoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedActivasoort))
            )
            .andExpect(status().isOk());

        // Validate the Activasoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertActivasoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedActivasoort, activasoort),
            getPersistedActivasoort(activasoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateActivasoortWithPatch() throws Exception {
        // Initialize the database
        activasoortRepository.saveAndFlush(activasoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activasoort using partial update
        Activasoort partialUpdatedActivasoort = new Activasoort();
        partialUpdatedActivasoort.setId(activasoort.getId());

        partialUpdatedActivasoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restActivasoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivasoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedActivasoort))
            )
            .andExpect(status().isOk());

        // Validate the Activasoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertActivasoortUpdatableFieldsEquals(partialUpdatedActivasoort, getPersistedActivasoort(partialUpdatedActivasoort));
    }

    @Test
    @Transactional
    void patchNonExistingActivasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activasoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivasoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activasoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(activasoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActivasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activasoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivasoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(activasoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActivasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activasoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivasoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(activasoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActivasoort() throws Exception {
        // Initialize the database
        activasoortRepository.saveAndFlush(activasoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the activasoort
        restActivasoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, activasoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return activasoortRepository.count();
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

    protected Activasoort getPersistedActivasoort(Activasoort activasoort) {
        return activasoortRepository.findById(activasoort.getId()).orElseThrow();
    }

    protected void assertPersistedActivasoortToMatchAllProperties(Activasoort expectedActivasoort) {
        assertActivasoortAllPropertiesEquals(expectedActivasoort, getPersistedActivasoort(expectedActivasoort));
    }

    protected void assertPersistedActivasoortToMatchUpdatableProperties(Activasoort expectedActivasoort) {
        assertActivasoortAllUpdatablePropertiesEquals(expectedActivasoort, getPersistedActivasoort(expectedActivasoort));
    }
}
