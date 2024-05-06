package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SubrekeningAsserts.*;
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
import nl.ritense.demo.domain.Subrekening;
import nl.ritense.demo.repository.SubrekeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SubrekeningResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubrekeningResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/subrekenings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SubrekeningRepository subrekeningRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubrekeningMockMvc;

    private Subrekening subrekening;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subrekening createEntity(EntityManager em) {
        Subrekening subrekening = new Subrekening().naam(DEFAULT_NAAM).nummer(DEFAULT_NUMMER).omschrijving(DEFAULT_OMSCHRIJVING);
        return subrekening;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subrekening createUpdatedEntity(EntityManager em) {
        Subrekening subrekening = new Subrekening().naam(UPDATED_NAAM).nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING);
        return subrekening;
    }

    @BeforeEach
    public void initTest() {
        subrekening = createEntity(em);
    }

    @Test
    @Transactional
    void createSubrekening() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Subrekening
        var returnedSubrekening = om.readValue(
            restSubrekeningMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subrekening)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Subrekening.class
        );

        // Validate the Subrekening in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSubrekeningUpdatableFieldsEquals(returnedSubrekening, getPersistedSubrekening(returnedSubrekening));
    }

    @Test
    @Transactional
    void createSubrekeningWithExistingId() throws Exception {
        // Create the Subrekening with an existing ID
        subrekening.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubrekeningMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subrekening)))
            .andExpect(status().isBadRequest());

        // Validate the Subrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubrekenings() throws Exception {
        // Initialize the database
        subrekeningRepository.saveAndFlush(subrekening);

        // Get all the subrekeningList
        restSubrekeningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subrekening.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getSubrekening() throws Exception {
        // Initialize the database
        subrekeningRepository.saveAndFlush(subrekening);

        // Get the subrekening
        restSubrekeningMockMvc
            .perform(get(ENTITY_API_URL_ID, subrekening.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subrekening.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingSubrekening() throws Exception {
        // Get the subrekening
        restSubrekeningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubrekening() throws Exception {
        // Initialize the database
        subrekeningRepository.saveAndFlush(subrekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subrekening
        Subrekening updatedSubrekening = subrekeningRepository.findById(subrekening.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSubrekening are not directly saved in db
        em.detach(updatedSubrekening);
        updatedSubrekening.naam(UPDATED_NAAM).nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING);

        restSubrekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubrekening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSubrekening))
            )
            .andExpect(status().isOk());

        // Validate the Subrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSubrekeningToMatchAllProperties(updatedSubrekening);
    }

    @Test
    @Transactional
    void putNonExistingSubrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subrekening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubrekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subrekening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subrekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subrekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubrekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subrekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subrekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubrekeningMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subrekening)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubrekeningWithPatch() throws Exception {
        // Initialize the database
        subrekeningRepository.saveAndFlush(subrekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subrekening using partial update
        Subrekening partialUpdatedSubrekening = new Subrekening();
        partialUpdatedSubrekening.setId(subrekening.getId());

        partialUpdatedSubrekening.naam(UPDATED_NAAM);

        restSubrekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubrekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubrekening))
            )
            .andExpect(status().isOk());

        // Validate the Subrekening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubrekeningUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSubrekening, subrekening),
            getPersistedSubrekening(subrekening)
        );
    }

    @Test
    @Transactional
    void fullUpdateSubrekeningWithPatch() throws Exception {
        // Initialize the database
        subrekeningRepository.saveAndFlush(subrekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subrekening using partial update
        Subrekening partialUpdatedSubrekening = new Subrekening();
        partialUpdatedSubrekening.setId(subrekening.getId());

        partialUpdatedSubrekening.naam(UPDATED_NAAM).nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING);

        restSubrekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubrekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubrekening))
            )
            .andExpect(status().isOk());

        // Validate the Subrekening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubrekeningUpdatableFieldsEquals(partialUpdatedSubrekening, getPersistedSubrekening(partialUpdatedSubrekening));
    }

    @Test
    @Transactional
    void patchNonExistingSubrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subrekening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubrekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subrekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subrekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subrekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubrekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subrekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subrekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubrekeningMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(subrekening)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubrekening() throws Exception {
        // Initialize the database
        subrekeningRepository.saveAndFlush(subrekening);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the subrekening
        restSubrekeningMockMvc
            .perform(delete(ENTITY_API_URL_ID, subrekening.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return subrekeningRepository.count();
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

    protected Subrekening getPersistedSubrekening(Subrekening subrekening) {
        return subrekeningRepository.findById(subrekening.getId()).orElseThrow();
    }

    protected void assertPersistedSubrekeningToMatchAllProperties(Subrekening expectedSubrekening) {
        assertSubrekeningAllPropertiesEquals(expectedSubrekening, getPersistedSubrekening(expectedSubrekening));
    }

    protected void assertPersistedSubrekeningToMatchUpdatableProperties(Subrekening expectedSubrekening) {
        assertSubrekeningAllUpdatablePropertiesEquals(expectedSubrekening, getPersistedSubrekening(expectedSubrekening));
    }
}
