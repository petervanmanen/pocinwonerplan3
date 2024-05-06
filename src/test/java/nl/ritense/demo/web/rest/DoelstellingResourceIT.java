package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DoelstellingAsserts.*;
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
import nl.ritense.demo.domain.Doelstelling;
import nl.ritense.demo.repository.DoelstellingRepository;
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
 * Integration tests for the {@link DoelstellingResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class DoelstellingResourceIT {

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/doelstellings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DoelstellingRepository doelstellingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoelstellingMockMvc;

    private Doelstelling doelstelling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doelstelling createEntity(EntityManager em) {
        Doelstelling doelstelling = new Doelstelling().omschrijving(DEFAULT_OMSCHRIJVING);
        return doelstelling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doelstelling createUpdatedEntity(EntityManager em) {
        Doelstelling doelstelling = new Doelstelling().omschrijving(UPDATED_OMSCHRIJVING);
        return doelstelling;
    }

    @BeforeEach
    public void initTest() {
        doelstelling = createEntity(em);
    }

    @Test
    @Transactional
    void createDoelstelling() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Doelstelling
        var returnedDoelstelling = om.readValue(
            restDoelstellingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doelstelling)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Doelstelling.class
        );

        // Validate the Doelstelling in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDoelstellingUpdatableFieldsEquals(returnedDoelstelling, getPersistedDoelstelling(returnedDoelstelling));
    }

    @Test
    @Transactional
    void createDoelstellingWithExistingId() throws Exception {
        // Create the Doelstelling with an existing ID
        doelstelling.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoelstellingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doelstelling)))
            .andExpect(status().isBadRequest());

        // Validate the Doelstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDoelstellings() throws Exception {
        // Initialize the database
        doelstellingRepository.saveAndFlush(doelstelling);

        // Get all the doelstellingList
        restDoelstellingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doelstelling.getId().intValue())))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getDoelstelling() throws Exception {
        // Initialize the database
        doelstellingRepository.saveAndFlush(doelstelling);

        // Get the doelstelling
        restDoelstellingMockMvc
            .perform(get(ENTITY_API_URL_ID, doelstelling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doelstelling.getId().intValue()))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingDoelstelling() throws Exception {
        // Get the doelstelling
        restDoelstellingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDoelstelling() throws Exception {
        // Initialize the database
        doelstellingRepository.saveAndFlush(doelstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doelstelling
        Doelstelling updatedDoelstelling = doelstellingRepository.findById(doelstelling.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDoelstelling are not directly saved in db
        em.detach(updatedDoelstelling);
        updatedDoelstelling.omschrijving(UPDATED_OMSCHRIJVING);

        restDoelstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDoelstelling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDoelstelling))
            )
            .andExpect(status().isOk());

        // Validate the Doelstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDoelstellingToMatchAllProperties(updatedDoelstelling);
    }

    @Test
    @Transactional
    void putNonExistingDoelstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelstelling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoelstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doelstelling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(doelstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doelstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDoelstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoelstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(doelstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doelstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDoelstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoelstellingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doelstelling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doelstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDoelstellingWithPatch() throws Exception {
        // Initialize the database
        doelstellingRepository.saveAndFlush(doelstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doelstelling using partial update
        Doelstelling partialUpdatedDoelstelling = new Doelstelling();
        partialUpdatedDoelstelling.setId(doelstelling.getId());

        restDoelstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoelstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDoelstelling))
            )
            .andExpect(status().isOk());

        // Validate the Doelstelling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDoelstellingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDoelstelling, doelstelling),
            getPersistedDoelstelling(doelstelling)
        );
    }

    @Test
    @Transactional
    void fullUpdateDoelstellingWithPatch() throws Exception {
        // Initialize the database
        doelstellingRepository.saveAndFlush(doelstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doelstelling using partial update
        Doelstelling partialUpdatedDoelstelling = new Doelstelling();
        partialUpdatedDoelstelling.setId(doelstelling.getId());

        partialUpdatedDoelstelling.omschrijving(UPDATED_OMSCHRIJVING);

        restDoelstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoelstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDoelstelling))
            )
            .andExpect(status().isOk());

        // Validate the Doelstelling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDoelstellingUpdatableFieldsEquals(partialUpdatedDoelstelling, getPersistedDoelstelling(partialUpdatedDoelstelling));
    }

    @Test
    @Transactional
    void patchNonExistingDoelstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelstelling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoelstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, doelstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(doelstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doelstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDoelstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoelstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(doelstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doelstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDoelstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoelstellingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(doelstelling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doelstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDoelstelling() throws Exception {
        // Initialize the database
        doelstellingRepository.saveAndFlush(doelstelling);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the doelstelling
        restDoelstellingMockMvc
            .perform(delete(ENTITY_API_URL_ID, doelstelling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return doelstellingRepository.count();
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

    protected Doelstelling getPersistedDoelstelling(Doelstelling doelstelling) {
        return doelstellingRepository.findById(doelstelling.getId()).orElseThrow();
    }

    protected void assertPersistedDoelstellingToMatchAllProperties(Doelstelling expectedDoelstelling) {
        assertDoelstellingAllPropertiesEquals(expectedDoelstelling, getPersistedDoelstelling(expectedDoelstelling));
    }

    protected void assertPersistedDoelstellingToMatchUpdatableProperties(Doelstelling expectedDoelstelling) {
        assertDoelstellingAllUpdatablePropertiesEquals(expectedDoelstelling, getPersistedDoelstelling(expectedDoelstelling));
    }
}
