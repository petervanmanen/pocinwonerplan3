package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AanvraagvrijstellingAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Aanvraagvrijstelling;
import nl.ritense.demo.repository.AanvraagvrijstellingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AanvraagvrijstellingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AanvraagvrijstellingResourceIT {

    private static final String DEFAULT_BUITENLANDSESCHOOLLOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_BUITENLANDSESCHOOLLOCATIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMAANVRAAG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANVRAAG = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/aanvraagvrijstellings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AanvraagvrijstellingRepository aanvraagvrijstellingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAanvraagvrijstellingMockMvc;

    private Aanvraagvrijstelling aanvraagvrijstelling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanvraagvrijstelling createEntity(EntityManager em) {
        Aanvraagvrijstelling aanvraagvrijstelling = new Aanvraagvrijstelling()
            .buitenlandseschoollocatie(DEFAULT_BUITENLANDSESCHOOLLOCATIE)
            .datumaanvraag(DEFAULT_DATUMAANVRAAG);
        return aanvraagvrijstelling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanvraagvrijstelling createUpdatedEntity(EntityManager em) {
        Aanvraagvrijstelling aanvraagvrijstelling = new Aanvraagvrijstelling()
            .buitenlandseschoollocatie(UPDATED_BUITENLANDSESCHOOLLOCATIE)
            .datumaanvraag(UPDATED_DATUMAANVRAAG);
        return aanvraagvrijstelling;
    }

    @BeforeEach
    public void initTest() {
        aanvraagvrijstelling = createEntity(em);
    }

    @Test
    @Transactional
    void createAanvraagvrijstelling() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aanvraagvrijstelling
        var returnedAanvraagvrijstelling = om.readValue(
            restAanvraagvrijstellingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraagvrijstelling)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aanvraagvrijstelling.class
        );

        // Validate the Aanvraagvrijstelling in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAanvraagvrijstellingUpdatableFieldsEquals(
            returnedAanvraagvrijstelling,
            getPersistedAanvraagvrijstelling(returnedAanvraagvrijstelling)
        );
    }

    @Test
    @Transactional
    void createAanvraagvrijstellingWithExistingId() throws Exception {
        // Create the Aanvraagvrijstelling with an existing ID
        aanvraagvrijstelling.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAanvraagvrijstellingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraagvrijstelling)))
            .andExpect(status().isBadRequest());

        // Validate the Aanvraagvrijstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAanvraagvrijstellings() throws Exception {
        // Initialize the database
        aanvraagvrijstellingRepository.saveAndFlush(aanvraagvrijstelling);

        // Get all the aanvraagvrijstellingList
        restAanvraagvrijstellingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aanvraagvrijstelling.getId().intValue())))
            .andExpect(jsonPath("$.[*].buitenlandseschoollocatie").value(hasItem(DEFAULT_BUITENLANDSESCHOOLLOCATIE)))
            .andExpect(jsonPath("$.[*].datumaanvraag").value(hasItem(DEFAULT_DATUMAANVRAAG.toString())));
    }

    @Test
    @Transactional
    void getAanvraagvrijstelling() throws Exception {
        // Initialize the database
        aanvraagvrijstellingRepository.saveAndFlush(aanvraagvrijstelling);

        // Get the aanvraagvrijstelling
        restAanvraagvrijstellingMockMvc
            .perform(get(ENTITY_API_URL_ID, aanvraagvrijstelling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aanvraagvrijstelling.getId().intValue()))
            .andExpect(jsonPath("$.buitenlandseschoollocatie").value(DEFAULT_BUITENLANDSESCHOOLLOCATIE))
            .andExpect(jsonPath("$.datumaanvraag").value(DEFAULT_DATUMAANVRAAG.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAanvraagvrijstelling() throws Exception {
        // Get the aanvraagvrijstelling
        restAanvraagvrijstellingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAanvraagvrijstelling() throws Exception {
        // Initialize the database
        aanvraagvrijstellingRepository.saveAndFlush(aanvraagvrijstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanvraagvrijstelling
        Aanvraagvrijstelling updatedAanvraagvrijstelling = aanvraagvrijstellingRepository
            .findById(aanvraagvrijstelling.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAanvraagvrijstelling are not directly saved in db
        em.detach(updatedAanvraagvrijstelling);
        updatedAanvraagvrijstelling.buitenlandseschoollocatie(UPDATED_BUITENLANDSESCHOOLLOCATIE).datumaanvraag(UPDATED_DATUMAANVRAAG);

        restAanvraagvrijstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAanvraagvrijstelling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAanvraagvrijstelling))
            )
            .andExpect(status().isOk());

        // Validate the Aanvraagvrijstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAanvraagvrijstellingToMatchAllProperties(updatedAanvraagvrijstelling);
    }

    @Test
    @Transactional
    void putNonExistingAanvraagvrijstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagvrijstelling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanvraagvrijstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aanvraagvrijstelling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aanvraagvrijstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraagvrijstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAanvraagvrijstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagvrijstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraagvrijstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aanvraagvrijstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraagvrijstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAanvraagvrijstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagvrijstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraagvrijstellingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraagvrijstelling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanvraagvrijstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAanvraagvrijstellingWithPatch() throws Exception {
        // Initialize the database
        aanvraagvrijstellingRepository.saveAndFlush(aanvraagvrijstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanvraagvrijstelling using partial update
        Aanvraagvrijstelling partialUpdatedAanvraagvrijstelling = new Aanvraagvrijstelling();
        partialUpdatedAanvraagvrijstelling.setId(aanvraagvrijstelling.getId());

        partialUpdatedAanvraagvrijstelling
            .buitenlandseschoollocatie(UPDATED_BUITENLANDSESCHOOLLOCATIE)
            .datumaanvraag(UPDATED_DATUMAANVRAAG);

        restAanvraagvrijstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanvraagvrijstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanvraagvrijstelling))
            )
            .andExpect(status().isOk());

        // Validate the Aanvraagvrijstelling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanvraagvrijstellingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAanvraagvrijstelling, aanvraagvrijstelling),
            getPersistedAanvraagvrijstelling(aanvraagvrijstelling)
        );
    }

    @Test
    @Transactional
    void fullUpdateAanvraagvrijstellingWithPatch() throws Exception {
        // Initialize the database
        aanvraagvrijstellingRepository.saveAndFlush(aanvraagvrijstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanvraagvrijstelling using partial update
        Aanvraagvrijstelling partialUpdatedAanvraagvrijstelling = new Aanvraagvrijstelling();
        partialUpdatedAanvraagvrijstelling.setId(aanvraagvrijstelling.getId());

        partialUpdatedAanvraagvrijstelling
            .buitenlandseschoollocatie(UPDATED_BUITENLANDSESCHOOLLOCATIE)
            .datumaanvraag(UPDATED_DATUMAANVRAAG);

        restAanvraagvrijstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanvraagvrijstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanvraagvrijstelling))
            )
            .andExpect(status().isOk());

        // Validate the Aanvraagvrijstelling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanvraagvrijstellingUpdatableFieldsEquals(
            partialUpdatedAanvraagvrijstelling,
            getPersistedAanvraagvrijstelling(partialUpdatedAanvraagvrijstelling)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAanvraagvrijstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagvrijstelling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanvraagvrijstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aanvraagvrijstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanvraagvrijstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraagvrijstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAanvraagvrijstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagvrijstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraagvrijstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanvraagvrijstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraagvrijstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAanvraagvrijstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagvrijstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraagvrijstellingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aanvraagvrijstelling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanvraagvrijstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAanvraagvrijstelling() throws Exception {
        // Initialize the database
        aanvraagvrijstellingRepository.saveAndFlush(aanvraagvrijstelling);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aanvraagvrijstelling
        restAanvraagvrijstellingMockMvc
            .perform(delete(ENTITY_API_URL_ID, aanvraagvrijstelling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aanvraagvrijstellingRepository.count();
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

    protected Aanvraagvrijstelling getPersistedAanvraagvrijstelling(Aanvraagvrijstelling aanvraagvrijstelling) {
        return aanvraagvrijstellingRepository.findById(aanvraagvrijstelling.getId()).orElseThrow();
    }

    protected void assertPersistedAanvraagvrijstellingToMatchAllProperties(Aanvraagvrijstelling expectedAanvraagvrijstelling) {
        assertAanvraagvrijstellingAllPropertiesEquals(
            expectedAanvraagvrijstelling,
            getPersistedAanvraagvrijstelling(expectedAanvraagvrijstelling)
        );
    }

    protected void assertPersistedAanvraagvrijstellingToMatchUpdatableProperties(Aanvraagvrijstelling expectedAanvraagvrijstelling) {
        assertAanvraagvrijstellingAllUpdatablePropertiesEquals(
            expectedAanvraagvrijstelling,
            getPersistedAanvraagvrijstelling(expectedAanvraagvrijstelling)
        );
    }
}
