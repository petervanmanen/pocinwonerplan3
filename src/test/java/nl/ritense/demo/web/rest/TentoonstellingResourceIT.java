package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TentoonstellingAsserts.*;
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
import nl.ritense.demo.domain.Tentoonstelling;
import nl.ritense.demo.repository.TentoonstellingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TentoonstellingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TentoonstellingResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_SUBTITEL = "AAAAAAAAAA";
    private static final String UPDATED_SUBTITEL = "BBBBBBBBBB";

    private static final String DEFAULT_TITEL = "AAAAAAAAAA";
    private static final String UPDATED_TITEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tentoonstellings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TentoonstellingRepository tentoonstellingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTentoonstellingMockMvc;

    private Tentoonstelling tentoonstelling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tentoonstelling createEntity(EntityManager em) {
        Tentoonstelling tentoonstelling = new Tentoonstelling()
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .subtitel(DEFAULT_SUBTITEL)
            .titel(DEFAULT_TITEL);
        return tentoonstelling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tentoonstelling createUpdatedEntity(EntityManager em) {
        Tentoonstelling tentoonstelling = new Tentoonstelling()
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .subtitel(UPDATED_SUBTITEL)
            .titel(UPDATED_TITEL);
        return tentoonstelling;
    }

    @BeforeEach
    public void initTest() {
        tentoonstelling = createEntity(em);
    }

    @Test
    @Transactional
    void createTentoonstelling() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Tentoonstelling
        var returnedTentoonstelling = om.readValue(
            restTentoonstellingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tentoonstelling)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Tentoonstelling.class
        );

        // Validate the Tentoonstelling in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTentoonstellingUpdatableFieldsEquals(returnedTentoonstelling, getPersistedTentoonstelling(returnedTentoonstelling));
    }

    @Test
    @Transactional
    void createTentoonstellingWithExistingId() throws Exception {
        // Create the Tentoonstelling with an existing ID
        tentoonstelling.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTentoonstellingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tentoonstelling)))
            .andExpect(status().isBadRequest());

        // Validate the Tentoonstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTentoonstellings() throws Exception {
        // Initialize the database
        tentoonstellingRepository.saveAndFlush(tentoonstelling);

        // Get all the tentoonstellingList
        restTentoonstellingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tentoonstelling.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].subtitel").value(hasItem(DEFAULT_SUBTITEL)))
            .andExpect(jsonPath("$.[*].titel").value(hasItem(DEFAULT_TITEL)));
    }

    @Test
    @Transactional
    void getTentoonstelling() throws Exception {
        // Initialize the database
        tentoonstellingRepository.saveAndFlush(tentoonstelling);

        // Get the tentoonstelling
        restTentoonstellingMockMvc
            .perform(get(ENTITY_API_URL_ID, tentoonstelling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tentoonstelling.getId().intValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.subtitel").value(DEFAULT_SUBTITEL))
            .andExpect(jsonPath("$.titel").value(DEFAULT_TITEL));
    }

    @Test
    @Transactional
    void getNonExistingTentoonstelling() throws Exception {
        // Get the tentoonstelling
        restTentoonstellingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTentoonstelling() throws Exception {
        // Initialize the database
        tentoonstellingRepository.saveAndFlush(tentoonstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tentoonstelling
        Tentoonstelling updatedTentoonstelling = tentoonstellingRepository.findById(tentoonstelling.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTentoonstelling are not directly saved in db
        em.detach(updatedTentoonstelling);
        updatedTentoonstelling
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .subtitel(UPDATED_SUBTITEL)
            .titel(UPDATED_TITEL);

        restTentoonstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTentoonstelling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTentoonstelling))
            )
            .andExpect(status().isOk());

        // Validate the Tentoonstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTentoonstellingToMatchAllProperties(updatedTentoonstelling);
    }

    @Test
    @Transactional
    void putNonExistingTentoonstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tentoonstelling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTentoonstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tentoonstelling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tentoonstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tentoonstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTentoonstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tentoonstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTentoonstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tentoonstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tentoonstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTentoonstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tentoonstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTentoonstellingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tentoonstelling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tentoonstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTentoonstellingWithPatch() throws Exception {
        // Initialize the database
        tentoonstellingRepository.saveAndFlush(tentoonstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tentoonstelling using partial update
        Tentoonstelling partialUpdatedTentoonstelling = new Tentoonstelling();
        partialUpdatedTentoonstelling.setId(tentoonstelling.getId());

        partialUpdatedTentoonstelling.datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART).subtitel(UPDATED_SUBTITEL);

        restTentoonstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTentoonstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTentoonstelling))
            )
            .andExpect(status().isOk());

        // Validate the Tentoonstelling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTentoonstellingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTentoonstelling, tentoonstelling),
            getPersistedTentoonstelling(tentoonstelling)
        );
    }

    @Test
    @Transactional
    void fullUpdateTentoonstellingWithPatch() throws Exception {
        // Initialize the database
        tentoonstellingRepository.saveAndFlush(tentoonstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tentoonstelling using partial update
        Tentoonstelling partialUpdatedTentoonstelling = new Tentoonstelling();
        partialUpdatedTentoonstelling.setId(tentoonstelling.getId());

        partialUpdatedTentoonstelling
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .subtitel(UPDATED_SUBTITEL)
            .titel(UPDATED_TITEL);

        restTentoonstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTentoonstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTentoonstelling))
            )
            .andExpect(status().isOk());

        // Validate the Tentoonstelling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTentoonstellingUpdatableFieldsEquals(
            partialUpdatedTentoonstelling,
            getPersistedTentoonstelling(partialUpdatedTentoonstelling)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTentoonstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tentoonstelling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTentoonstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tentoonstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tentoonstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tentoonstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTentoonstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tentoonstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTentoonstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tentoonstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tentoonstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTentoonstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tentoonstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTentoonstellingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tentoonstelling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tentoonstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTentoonstelling() throws Exception {
        // Initialize the database
        tentoonstellingRepository.saveAndFlush(tentoonstelling);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tentoonstelling
        restTentoonstellingMockMvc
            .perform(delete(ENTITY_API_URL_ID, tentoonstelling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tentoonstellingRepository.count();
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

    protected Tentoonstelling getPersistedTentoonstelling(Tentoonstelling tentoonstelling) {
        return tentoonstellingRepository.findById(tentoonstelling.getId()).orElseThrow();
    }

    protected void assertPersistedTentoonstellingToMatchAllProperties(Tentoonstelling expectedTentoonstelling) {
        assertTentoonstellingAllPropertiesEquals(expectedTentoonstelling, getPersistedTentoonstelling(expectedTentoonstelling));
    }

    protected void assertPersistedTentoonstellingToMatchUpdatableProperties(Tentoonstelling expectedTentoonstelling) {
        assertTentoonstellingAllUpdatablePropertiesEquals(expectedTentoonstelling, getPersistedTentoonstelling(expectedTentoonstelling));
    }
}
