package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OnderwerpAsserts.*;
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
import nl.ritense.demo.domain.Onderwerp;
import nl.ritense.demo.repository.OnderwerpRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OnderwerpResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OnderwerpResourceIT {

    private static final String ENTITY_API_URL = "/api/onderwerps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OnderwerpRepository onderwerpRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOnderwerpMockMvc;

    private Onderwerp onderwerp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderwerp createEntity(EntityManager em) {
        Onderwerp onderwerp = new Onderwerp();
        return onderwerp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderwerp createUpdatedEntity(EntityManager em) {
        Onderwerp onderwerp = new Onderwerp();
        return onderwerp;
    }

    @BeforeEach
    public void initTest() {
        onderwerp = createEntity(em);
    }

    @Test
    @Transactional
    void createOnderwerp() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Onderwerp
        var returnedOnderwerp = om.readValue(
            restOnderwerpMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderwerp)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Onderwerp.class
        );

        // Validate the Onderwerp in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOnderwerpUpdatableFieldsEquals(returnedOnderwerp, getPersistedOnderwerp(returnedOnderwerp));
    }

    @Test
    @Transactional
    void createOnderwerpWithExistingId() throws Exception {
        // Create the Onderwerp with an existing ID
        onderwerp.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOnderwerpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderwerp)))
            .andExpect(status().isBadRequest());

        // Validate the Onderwerp in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOnderwerps() throws Exception {
        // Initialize the database
        onderwerpRepository.saveAndFlush(onderwerp);

        // Get all the onderwerpList
        restOnderwerpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(onderwerp.getId().intValue())));
    }

    @Test
    @Transactional
    void getOnderwerp() throws Exception {
        // Initialize the database
        onderwerpRepository.saveAndFlush(onderwerp);

        // Get the onderwerp
        restOnderwerpMockMvc
            .perform(get(ENTITY_API_URL_ID, onderwerp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(onderwerp.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOnderwerp() throws Exception {
        // Get the onderwerp
        restOnderwerpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOnderwerp() throws Exception {
        // Initialize the database
        onderwerpRepository.saveAndFlush(onderwerp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onderwerp
        Onderwerp updatedOnderwerp = onderwerpRepository.findById(onderwerp.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOnderwerp are not directly saved in db
        em.detach(updatedOnderwerp);

        restOnderwerpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOnderwerp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOnderwerp))
            )
            .andExpect(status().isOk());

        // Validate the Onderwerp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOnderwerpToMatchAllProperties(updatedOnderwerp);
    }

    @Test
    @Transactional
    void putNonExistingOnderwerp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwerp.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOnderwerpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, onderwerp.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderwerp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderwerp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOnderwerp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwerp.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderwerpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(onderwerp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderwerp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOnderwerp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwerp.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderwerpMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderwerp)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Onderwerp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOnderwerpWithPatch() throws Exception {
        // Initialize the database
        onderwerpRepository.saveAndFlush(onderwerp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onderwerp using partial update
        Onderwerp partialUpdatedOnderwerp = new Onderwerp();
        partialUpdatedOnderwerp.setId(onderwerp.getId());

        restOnderwerpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOnderwerp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOnderwerp))
            )
            .andExpect(status().isOk());

        // Validate the Onderwerp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOnderwerpUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOnderwerp, onderwerp),
            getPersistedOnderwerp(onderwerp)
        );
    }

    @Test
    @Transactional
    void fullUpdateOnderwerpWithPatch() throws Exception {
        // Initialize the database
        onderwerpRepository.saveAndFlush(onderwerp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onderwerp using partial update
        Onderwerp partialUpdatedOnderwerp = new Onderwerp();
        partialUpdatedOnderwerp.setId(onderwerp.getId());

        restOnderwerpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOnderwerp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOnderwerp))
            )
            .andExpect(status().isOk());

        // Validate the Onderwerp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOnderwerpUpdatableFieldsEquals(partialUpdatedOnderwerp, getPersistedOnderwerp(partialUpdatedOnderwerp));
    }

    @Test
    @Transactional
    void patchNonExistingOnderwerp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwerp.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOnderwerpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, onderwerp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(onderwerp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderwerp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOnderwerp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwerp.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderwerpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(onderwerp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderwerp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOnderwerp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwerp.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderwerpMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(onderwerp)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Onderwerp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOnderwerp() throws Exception {
        // Initialize the database
        onderwerpRepository.saveAndFlush(onderwerp);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the onderwerp
        restOnderwerpMockMvc
            .perform(delete(ENTITY_API_URL_ID, onderwerp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return onderwerpRepository.count();
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

    protected Onderwerp getPersistedOnderwerp(Onderwerp onderwerp) {
        return onderwerpRepository.findById(onderwerp.getId()).orElseThrow();
    }

    protected void assertPersistedOnderwerpToMatchAllProperties(Onderwerp expectedOnderwerp) {
        assertOnderwerpAllPropertiesEquals(expectedOnderwerp, getPersistedOnderwerp(expectedOnderwerp));
    }

    protected void assertPersistedOnderwerpToMatchUpdatableProperties(Onderwerp expectedOnderwerp) {
        assertOnderwerpAllUpdatablePropertiesEquals(expectedOnderwerp, getPersistedOnderwerp(expectedOnderwerp));
    }
}
