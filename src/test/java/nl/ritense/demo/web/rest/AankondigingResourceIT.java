package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AankondigingAsserts.*;
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
import nl.ritense.demo.domain.Aankondiging;
import nl.ritense.demo.repository.AankondigingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AankondigingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AankondigingResourceIT {

    private static final String DEFAULT_BESCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_BESCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUM = "AAAAAAAAAA";
    private static final String UPDATED_DATUM = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aankondigings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AankondigingRepository aankondigingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAankondigingMockMvc;

    private Aankondiging aankondiging;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aankondiging createEntity(EntityManager em) {
        Aankondiging aankondiging = new Aankondiging()
            .beschrijving(DEFAULT_BESCHRIJVING)
            .categorie(DEFAULT_CATEGORIE)
            .datum(DEFAULT_DATUM)
            .naam(DEFAULT_NAAM)
            .type(DEFAULT_TYPE);
        return aankondiging;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aankondiging createUpdatedEntity(EntityManager em) {
        Aankondiging aankondiging = new Aankondiging()
            .beschrijving(UPDATED_BESCHRIJVING)
            .categorie(UPDATED_CATEGORIE)
            .datum(UPDATED_DATUM)
            .naam(UPDATED_NAAM)
            .type(UPDATED_TYPE);
        return aankondiging;
    }

    @BeforeEach
    public void initTest() {
        aankondiging = createEntity(em);
    }

    @Test
    @Transactional
    void createAankondiging() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aankondiging
        var returnedAankondiging = om.readValue(
            restAankondigingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aankondiging)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aankondiging.class
        );

        // Validate the Aankondiging in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAankondigingUpdatableFieldsEquals(returnedAankondiging, getPersistedAankondiging(returnedAankondiging));
    }

    @Test
    @Transactional
    void createAankondigingWithExistingId() throws Exception {
        // Create the Aankondiging with an existing ID
        aankondiging.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAankondigingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aankondiging)))
            .andExpect(status().isBadRequest());

        // Validate the Aankondiging in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAankondigings() throws Exception {
        // Initialize the database
        aankondigingRepository.saveAndFlush(aankondiging);

        // Get all the aankondigingList
        restAankondigingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aankondiging.getId().intValue())))
            .andExpect(jsonPath("$.[*].beschrijving").value(hasItem(DEFAULT_BESCHRIJVING)))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE)))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getAankondiging() throws Exception {
        // Initialize the database
        aankondigingRepository.saveAndFlush(aankondiging);

        // Get the aankondiging
        restAankondigingMockMvc
            .perform(get(ENTITY_API_URL_ID, aankondiging.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aankondiging.getId().intValue()))
            .andExpect(jsonPath("$.beschrijving").value(DEFAULT_BESCHRIJVING))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingAankondiging() throws Exception {
        // Get the aankondiging
        restAankondigingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAankondiging() throws Exception {
        // Initialize the database
        aankondigingRepository.saveAndFlush(aankondiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aankondiging
        Aankondiging updatedAankondiging = aankondigingRepository.findById(aankondiging.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAankondiging are not directly saved in db
        em.detach(updatedAankondiging);
        updatedAankondiging
            .beschrijving(UPDATED_BESCHRIJVING)
            .categorie(UPDATED_CATEGORIE)
            .datum(UPDATED_DATUM)
            .naam(UPDATED_NAAM)
            .type(UPDATED_TYPE);

        restAankondigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAankondiging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAankondiging))
            )
            .andExpect(status().isOk());

        // Validate the Aankondiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAankondigingToMatchAllProperties(updatedAankondiging);
    }

    @Test
    @Transactional
    void putNonExistingAankondiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aankondiging.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAankondigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aankondiging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aankondiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aankondiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAankondiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aankondiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAankondigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aankondiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aankondiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAankondiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aankondiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAankondigingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aankondiging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aankondiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAankondigingWithPatch() throws Exception {
        // Initialize the database
        aankondigingRepository.saveAndFlush(aankondiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aankondiging using partial update
        Aankondiging partialUpdatedAankondiging = new Aankondiging();
        partialUpdatedAankondiging.setId(aankondiging.getId());

        partialUpdatedAankondiging.categorie(UPDATED_CATEGORIE).datum(UPDATED_DATUM).type(UPDATED_TYPE);

        restAankondigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAankondiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAankondiging))
            )
            .andExpect(status().isOk());

        // Validate the Aankondiging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAankondigingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAankondiging, aankondiging),
            getPersistedAankondiging(aankondiging)
        );
    }

    @Test
    @Transactional
    void fullUpdateAankondigingWithPatch() throws Exception {
        // Initialize the database
        aankondigingRepository.saveAndFlush(aankondiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aankondiging using partial update
        Aankondiging partialUpdatedAankondiging = new Aankondiging();
        partialUpdatedAankondiging.setId(aankondiging.getId());

        partialUpdatedAankondiging
            .beschrijving(UPDATED_BESCHRIJVING)
            .categorie(UPDATED_CATEGORIE)
            .datum(UPDATED_DATUM)
            .naam(UPDATED_NAAM)
            .type(UPDATED_TYPE);

        restAankondigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAankondiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAankondiging))
            )
            .andExpect(status().isOk());

        // Validate the Aankondiging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAankondigingUpdatableFieldsEquals(partialUpdatedAankondiging, getPersistedAankondiging(partialUpdatedAankondiging));
    }

    @Test
    @Transactional
    void patchNonExistingAankondiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aankondiging.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAankondigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aankondiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aankondiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aankondiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAankondiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aankondiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAankondigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aankondiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aankondiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAankondiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aankondiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAankondigingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aankondiging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aankondiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAankondiging() throws Exception {
        // Initialize the database
        aankondigingRepository.saveAndFlush(aankondiging);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aankondiging
        restAankondigingMockMvc
            .perform(delete(ENTITY_API_URL_ID, aankondiging.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aankondigingRepository.count();
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

    protected Aankondiging getPersistedAankondiging(Aankondiging aankondiging) {
        return aankondigingRepository.findById(aankondiging.getId()).orElseThrow();
    }

    protected void assertPersistedAankondigingToMatchAllProperties(Aankondiging expectedAankondiging) {
        assertAankondigingAllPropertiesEquals(expectedAankondiging, getPersistedAankondiging(expectedAankondiging));
    }

    protected void assertPersistedAankondigingToMatchUpdatableProperties(Aankondiging expectedAankondiging) {
        assertAankondigingAllUpdatablePropertiesEquals(expectedAankondiging, getPersistedAankondiging(expectedAankondiging));
    }
}
