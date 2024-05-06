package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VrijstellingAsserts.*;
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
import nl.ritense.demo.domain.Vrijstelling;
import nl.ritense.demo.repository.VrijstellingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VrijstellingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VrijstellingResourceIT {

    private static final Boolean DEFAULT_AANVRAAGTOEGEKEND = false;
    private static final Boolean UPDATED_AANVRAAGTOEGEKEND = true;

    private static final String DEFAULT_BUITENLANDSESCHOOLLOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_BUITENLANDSESCHOOLLOCATIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_VERZUIMSOORT = "AAAAAAAAAA";
    private static final String UPDATED_VERZUIMSOORT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vrijstellings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VrijstellingRepository vrijstellingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVrijstellingMockMvc;

    private Vrijstelling vrijstelling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vrijstelling createEntity(EntityManager em) {
        Vrijstelling vrijstelling = new Vrijstelling()
            .aanvraagtoegekend(DEFAULT_AANVRAAGTOEGEKEND)
            .buitenlandseschoollocatie(DEFAULT_BUITENLANDSESCHOOLLOCATIE)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .verzuimsoort(DEFAULT_VERZUIMSOORT);
        return vrijstelling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vrijstelling createUpdatedEntity(EntityManager em) {
        Vrijstelling vrijstelling = new Vrijstelling()
            .aanvraagtoegekend(UPDATED_AANVRAAGTOEGEKEND)
            .buitenlandseschoollocatie(UPDATED_BUITENLANDSESCHOOLLOCATIE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .verzuimsoort(UPDATED_VERZUIMSOORT);
        return vrijstelling;
    }

    @BeforeEach
    public void initTest() {
        vrijstelling = createEntity(em);
    }

    @Test
    @Transactional
    void createVrijstelling() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vrijstelling
        var returnedVrijstelling = om.readValue(
            restVrijstellingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vrijstelling)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vrijstelling.class
        );

        // Validate the Vrijstelling in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVrijstellingUpdatableFieldsEquals(returnedVrijstelling, getPersistedVrijstelling(returnedVrijstelling));
    }

    @Test
    @Transactional
    void createVrijstellingWithExistingId() throws Exception {
        // Create the Vrijstelling with an existing ID
        vrijstelling.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVrijstellingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vrijstelling)))
            .andExpect(status().isBadRequest());

        // Validate the Vrijstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVrijstellings() throws Exception {
        // Initialize the database
        vrijstellingRepository.saveAndFlush(vrijstelling);

        // Get all the vrijstellingList
        restVrijstellingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vrijstelling.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanvraagtoegekend").value(hasItem(DEFAULT_AANVRAAGTOEGEKEND.booleanValue())))
            .andExpect(jsonPath("$.[*].buitenlandseschoollocatie").value(hasItem(DEFAULT_BUITENLANDSESCHOOLLOCATIE)))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].verzuimsoort").value(hasItem(DEFAULT_VERZUIMSOORT)));
    }

    @Test
    @Transactional
    void getVrijstelling() throws Exception {
        // Initialize the database
        vrijstellingRepository.saveAndFlush(vrijstelling);

        // Get the vrijstelling
        restVrijstellingMockMvc
            .perform(get(ENTITY_API_URL_ID, vrijstelling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vrijstelling.getId().intValue()))
            .andExpect(jsonPath("$.aanvraagtoegekend").value(DEFAULT_AANVRAAGTOEGEKEND.booleanValue()))
            .andExpect(jsonPath("$.buitenlandseschoollocatie").value(DEFAULT_BUITENLANDSESCHOOLLOCATIE))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.verzuimsoort").value(DEFAULT_VERZUIMSOORT));
    }

    @Test
    @Transactional
    void getNonExistingVrijstelling() throws Exception {
        // Get the vrijstelling
        restVrijstellingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVrijstelling() throws Exception {
        // Initialize the database
        vrijstellingRepository.saveAndFlush(vrijstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vrijstelling
        Vrijstelling updatedVrijstelling = vrijstellingRepository.findById(vrijstelling.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVrijstelling are not directly saved in db
        em.detach(updatedVrijstelling);
        updatedVrijstelling
            .aanvraagtoegekend(UPDATED_AANVRAAGTOEGEKEND)
            .buitenlandseschoollocatie(UPDATED_BUITENLANDSESCHOOLLOCATIE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .verzuimsoort(UPDATED_VERZUIMSOORT);

        restVrijstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVrijstelling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVrijstelling))
            )
            .andExpect(status().isOk());

        // Validate the Vrijstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVrijstellingToMatchAllProperties(updatedVrijstelling);
    }

    @Test
    @Transactional
    void putNonExistingVrijstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vrijstelling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVrijstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vrijstelling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vrijstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vrijstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVrijstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vrijstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVrijstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vrijstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vrijstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVrijstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vrijstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVrijstellingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vrijstelling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vrijstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVrijstellingWithPatch() throws Exception {
        // Initialize the database
        vrijstellingRepository.saveAndFlush(vrijstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vrijstelling using partial update
        Vrijstelling partialUpdatedVrijstelling = new Vrijstelling();
        partialUpdatedVrijstelling.setId(vrijstelling.getId());

        partialUpdatedVrijstelling
            .buitenlandseschoollocatie(UPDATED_BUITENLANDSESCHOOLLOCATIE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .verzuimsoort(UPDATED_VERZUIMSOORT);

        restVrijstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVrijstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVrijstelling))
            )
            .andExpect(status().isOk());

        // Validate the Vrijstelling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVrijstellingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVrijstelling, vrijstelling),
            getPersistedVrijstelling(vrijstelling)
        );
    }

    @Test
    @Transactional
    void fullUpdateVrijstellingWithPatch() throws Exception {
        // Initialize the database
        vrijstellingRepository.saveAndFlush(vrijstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vrijstelling using partial update
        Vrijstelling partialUpdatedVrijstelling = new Vrijstelling();
        partialUpdatedVrijstelling.setId(vrijstelling.getId());

        partialUpdatedVrijstelling
            .aanvraagtoegekend(UPDATED_AANVRAAGTOEGEKEND)
            .buitenlandseschoollocatie(UPDATED_BUITENLANDSESCHOOLLOCATIE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .verzuimsoort(UPDATED_VERZUIMSOORT);

        restVrijstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVrijstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVrijstelling))
            )
            .andExpect(status().isOk());

        // Validate the Vrijstelling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVrijstellingUpdatableFieldsEquals(partialUpdatedVrijstelling, getPersistedVrijstelling(partialUpdatedVrijstelling));
    }

    @Test
    @Transactional
    void patchNonExistingVrijstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vrijstelling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVrijstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vrijstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vrijstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vrijstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVrijstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vrijstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVrijstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vrijstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vrijstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVrijstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vrijstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVrijstellingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vrijstelling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vrijstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVrijstelling() throws Exception {
        // Initialize the database
        vrijstellingRepository.saveAndFlush(vrijstelling);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vrijstelling
        restVrijstellingMockMvc
            .perform(delete(ENTITY_API_URL_ID, vrijstelling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vrijstellingRepository.count();
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

    protected Vrijstelling getPersistedVrijstelling(Vrijstelling vrijstelling) {
        return vrijstellingRepository.findById(vrijstelling.getId()).orElseThrow();
    }

    protected void assertPersistedVrijstellingToMatchAllProperties(Vrijstelling expectedVrijstelling) {
        assertVrijstellingAllPropertiesEquals(expectedVrijstelling, getPersistedVrijstelling(expectedVrijstelling));
    }

    protected void assertPersistedVrijstellingToMatchUpdatableProperties(Vrijstelling expectedVrijstelling) {
        assertVrijstellingAllUpdatablePropertiesEquals(expectedVrijstelling, getPersistedVrijstelling(expectedVrijstelling));
    }
}
