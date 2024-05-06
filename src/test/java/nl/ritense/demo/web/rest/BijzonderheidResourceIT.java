package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BijzonderheidAsserts.*;
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
import nl.ritense.demo.domain.Bijzonderheid;
import nl.ritense.demo.repository.BijzonderheidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BijzonderheidResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BijzonderheidResourceIT {

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bijzonderheids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BijzonderheidRepository bijzonderheidRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBijzonderheidMockMvc;

    private Bijzonderheid bijzonderheid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bijzonderheid createEntity(EntityManager em) {
        Bijzonderheid bijzonderheid = new Bijzonderheid().omschrijving(DEFAULT_OMSCHRIJVING);
        return bijzonderheid;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bijzonderheid createUpdatedEntity(EntityManager em) {
        Bijzonderheid bijzonderheid = new Bijzonderheid().omschrijving(UPDATED_OMSCHRIJVING);
        return bijzonderheid;
    }

    @BeforeEach
    public void initTest() {
        bijzonderheid = createEntity(em);
    }

    @Test
    @Transactional
    void createBijzonderheid() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bijzonderheid
        var returnedBijzonderheid = om.readValue(
            restBijzonderheidMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bijzonderheid)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bijzonderheid.class
        );

        // Validate the Bijzonderheid in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBijzonderheidUpdatableFieldsEquals(returnedBijzonderheid, getPersistedBijzonderheid(returnedBijzonderheid));
    }

    @Test
    @Transactional
    void createBijzonderheidWithExistingId() throws Exception {
        // Create the Bijzonderheid with an existing ID
        bijzonderheid.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBijzonderheidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bijzonderheid)))
            .andExpect(status().isBadRequest());

        // Validate the Bijzonderheid in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBijzonderheids() throws Exception {
        // Initialize the database
        bijzonderheidRepository.saveAndFlush(bijzonderheid);

        // Get all the bijzonderheidList
        restBijzonderheidMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bijzonderheid.getId().intValue())))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getBijzonderheid() throws Exception {
        // Initialize the database
        bijzonderheidRepository.saveAndFlush(bijzonderheid);

        // Get the bijzonderheid
        restBijzonderheidMockMvc
            .perform(get(ENTITY_API_URL_ID, bijzonderheid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bijzonderheid.getId().intValue()))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingBijzonderheid() throws Exception {
        // Get the bijzonderheid
        restBijzonderheidMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBijzonderheid() throws Exception {
        // Initialize the database
        bijzonderheidRepository.saveAndFlush(bijzonderheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bijzonderheid
        Bijzonderheid updatedBijzonderheid = bijzonderheidRepository.findById(bijzonderheid.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBijzonderheid are not directly saved in db
        em.detach(updatedBijzonderheid);
        updatedBijzonderheid.omschrijving(UPDATED_OMSCHRIJVING);

        restBijzonderheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBijzonderheid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBijzonderheid))
            )
            .andExpect(status().isOk());

        // Validate the Bijzonderheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBijzonderheidToMatchAllProperties(updatedBijzonderheid);
    }

    @Test
    @Transactional
    void putNonExistingBijzonderheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bijzonderheid.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBijzonderheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bijzonderheid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bijzonderheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bijzonderheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBijzonderheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bijzonderheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBijzonderheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bijzonderheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bijzonderheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBijzonderheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bijzonderheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBijzonderheidMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bijzonderheid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bijzonderheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBijzonderheidWithPatch() throws Exception {
        // Initialize the database
        bijzonderheidRepository.saveAndFlush(bijzonderheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bijzonderheid using partial update
        Bijzonderheid partialUpdatedBijzonderheid = new Bijzonderheid();
        partialUpdatedBijzonderheid.setId(bijzonderheid.getId());

        partialUpdatedBijzonderheid.omschrijving(UPDATED_OMSCHRIJVING);

        restBijzonderheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBijzonderheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBijzonderheid))
            )
            .andExpect(status().isOk());

        // Validate the Bijzonderheid in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBijzonderheidUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBijzonderheid, bijzonderheid),
            getPersistedBijzonderheid(bijzonderheid)
        );
    }

    @Test
    @Transactional
    void fullUpdateBijzonderheidWithPatch() throws Exception {
        // Initialize the database
        bijzonderheidRepository.saveAndFlush(bijzonderheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bijzonderheid using partial update
        Bijzonderheid partialUpdatedBijzonderheid = new Bijzonderheid();
        partialUpdatedBijzonderheid.setId(bijzonderheid.getId());

        partialUpdatedBijzonderheid.omschrijving(UPDATED_OMSCHRIJVING);

        restBijzonderheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBijzonderheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBijzonderheid))
            )
            .andExpect(status().isOk());

        // Validate the Bijzonderheid in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBijzonderheidUpdatableFieldsEquals(partialUpdatedBijzonderheid, getPersistedBijzonderheid(partialUpdatedBijzonderheid));
    }

    @Test
    @Transactional
    void patchNonExistingBijzonderheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bijzonderheid.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBijzonderheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bijzonderheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bijzonderheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bijzonderheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBijzonderheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bijzonderheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBijzonderheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bijzonderheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bijzonderheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBijzonderheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bijzonderheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBijzonderheidMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bijzonderheid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bijzonderheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBijzonderheid() throws Exception {
        // Initialize the database
        bijzonderheidRepository.saveAndFlush(bijzonderheid);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bijzonderheid
        restBijzonderheidMockMvc
            .perform(delete(ENTITY_API_URL_ID, bijzonderheid.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bijzonderheidRepository.count();
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

    protected Bijzonderheid getPersistedBijzonderheid(Bijzonderheid bijzonderheid) {
        return bijzonderheidRepository.findById(bijzonderheid.getId()).orElseThrow();
    }

    protected void assertPersistedBijzonderheidToMatchAllProperties(Bijzonderheid expectedBijzonderheid) {
        assertBijzonderheidAllPropertiesEquals(expectedBijzonderheid, getPersistedBijzonderheid(expectedBijzonderheid));
    }

    protected void assertPersistedBijzonderheidToMatchUpdatableProperties(Bijzonderheid expectedBijzonderheid) {
        assertBijzonderheidAllUpdatablePropertiesEquals(expectedBijzonderheid, getPersistedBijzonderheid(expectedBijzonderheid));
    }
}
