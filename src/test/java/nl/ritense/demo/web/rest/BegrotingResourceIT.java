package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BegrotingAsserts.*;
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
import nl.ritense.demo.domain.Begroting;
import nl.ritense.demo.repository.BegrotingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BegrotingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BegrotingResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/begrotings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BegrotingRepository begrotingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBegrotingMockMvc;

    private Begroting begroting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Begroting createEntity(EntityManager em) {
        Begroting begroting = new Begroting().naam(DEFAULT_NAAM).nummer(DEFAULT_NUMMER).omschrijving(DEFAULT_OMSCHRIJVING);
        return begroting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Begroting createUpdatedEntity(EntityManager em) {
        Begroting begroting = new Begroting().naam(UPDATED_NAAM).nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING);
        return begroting;
    }

    @BeforeEach
    public void initTest() {
        begroting = createEntity(em);
    }

    @Test
    @Transactional
    void createBegroting() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Begroting
        var returnedBegroting = om.readValue(
            restBegrotingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(begroting)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Begroting.class
        );

        // Validate the Begroting in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBegrotingUpdatableFieldsEquals(returnedBegroting, getPersistedBegroting(returnedBegroting));
    }

    @Test
    @Transactional
    void createBegrotingWithExistingId() throws Exception {
        // Create the Begroting with an existing ID
        begroting.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBegrotingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(begroting)))
            .andExpect(status().isBadRequest());

        // Validate the Begroting in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBegrotings() throws Exception {
        // Initialize the database
        begrotingRepository.saveAndFlush(begroting);

        // Get all the begrotingList
        restBegrotingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(begroting.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getBegroting() throws Exception {
        // Initialize the database
        begrotingRepository.saveAndFlush(begroting);

        // Get the begroting
        restBegrotingMockMvc
            .perform(get(ENTITY_API_URL_ID, begroting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(begroting.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingBegroting() throws Exception {
        // Get the begroting
        restBegrotingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBegroting() throws Exception {
        // Initialize the database
        begrotingRepository.saveAndFlush(begroting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the begroting
        Begroting updatedBegroting = begrotingRepository.findById(begroting.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBegroting are not directly saved in db
        em.detach(updatedBegroting);
        updatedBegroting.naam(UPDATED_NAAM).nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING);

        restBegrotingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBegroting.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBegroting))
            )
            .andExpect(status().isOk());

        // Validate the Begroting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBegrotingToMatchAllProperties(updatedBegroting);
    }

    @Test
    @Transactional
    void putNonExistingBegroting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begroting.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBegrotingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, begroting.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(begroting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Begroting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBegroting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begroting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBegrotingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(begroting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Begroting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBegroting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begroting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBegrotingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(begroting)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Begroting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBegrotingWithPatch() throws Exception {
        // Initialize the database
        begrotingRepository.saveAndFlush(begroting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the begroting using partial update
        Begroting partialUpdatedBegroting = new Begroting();
        partialUpdatedBegroting.setId(begroting.getId());

        partialUpdatedBegroting.nummer(UPDATED_NUMMER);

        restBegrotingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBegroting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBegroting))
            )
            .andExpect(status().isOk());

        // Validate the Begroting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBegrotingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBegroting, begroting),
            getPersistedBegroting(begroting)
        );
    }

    @Test
    @Transactional
    void fullUpdateBegrotingWithPatch() throws Exception {
        // Initialize the database
        begrotingRepository.saveAndFlush(begroting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the begroting using partial update
        Begroting partialUpdatedBegroting = new Begroting();
        partialUpdatedBegroting.setId(begroting.getId());

        partialUpdatedBegroting.naam(UPDATED_NAAM).nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING);

        restBegrotingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBegroting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBegroting))
            )
            .andExpect(status().isOk());

        // Validate the Begroting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBegrotingUpdatableFieldsEquals(partialUpdatedBegroting, getPersistedBegroting(partialUpdatedBegroting));
    }

    @Test
    @Transactional
    void patchNonExistingBegroting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begroting.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBegrotingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, begroting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(begroting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Begroting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBegroting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begroting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBegrotingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(begroting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Begroting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBegroting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begroting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBegrotingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(begroting)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Begroting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBegroting() throws Exception {
        // Initialize the database
        begrotingRepository.saveAndFlush(begroting);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the begroting
        restBegrotingMockMvc
            .perform(delete(ENTITY_API_URL_ID, begroting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return begrotingRepository.count();
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

    protected Begroting getPersistedBegroting(Begroting begroting) {
        return begrotingRepository.findById(begroting.getId()).orElseThrow();
    }

    protected void assertPersistedBegrotingToMatchAllProperties(Begroting expectedBegroting) {
        assertBegrotingAllPropertiesEquals(expectedBegroting, getPersistedBegroting(expectedBegroting));
    }

    protected void assertPersistedBegrotingToMatchUpdatableProperties(Begroting expectedBegroting) {
        assertBegrotingAllUpdatablePropertiesEquals(expectedBegroting, getPersistedBegroting(expectedBegroting));
    }
}
