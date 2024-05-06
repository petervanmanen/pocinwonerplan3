package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AantekeningAsserts.*;
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
import nl.ritense.demo.domain.Aantekening;
import nl.ritense.demo.repository.AantekeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AantekeningResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AantekeningResourceIT {

    private static final String DEFAULT_AARD = "AAAAAAAAAA";
    private static final String UPDATED_AARD = "BBBBBBBBBB";

    private static final String DEFAULT_BEGRENZING = "AAAAAAAAAA";
    private static final String UPDATED_BEGRENZING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BETREFTGEDEELTEVANPERCEEL = false;
    private static final Boolean UPDATED_BETREFTGEDEELTEVANPERCEEL = true;

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDERECHT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDERECHT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aantekenings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AantekeningRepository aantekeningRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAantekeningMockMvc;

    private Aantekening aantekening;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aantekening createEntity(EntityManager em) {
        Aantekening aantekening = new Aantekening()
            .aard(DEFAULT_AARD)
            .begrenzing(DEFAULT_BEGRENZING)
            .betreftgedeeltevanperceel(DEFAULT_BETREFTGEDEELTEVANPERCEEL)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumeinderecht(DEFAULT_DATUMEINDERECHT)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return aantekening;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aantekening createUpdatedEntity(EntityManager em) {
        Aantekening aantekening = new Aantekening()
            .aard(UPDATED_AARD)
            .begrenzing(UPDATED_BEGRENZING)
            .betreftgedeeltevanperceel(UPDATED_BETREFTGEDEELTEVANPERCEEL)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeinderecht(UPDATED_DATUMEINDERECHT)
            .identificatie(UPDATED_IDENTIFICATIE)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return aantekening;
    }

    @BeforeEach
    public void initTest() {
        aantekening = createEntity(em);
    }

    @Test
    @Transactional
    void createAantekening() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aantekening
        var returnedAantekening = om.readValue(
            restAantekeningMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aantekening)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aantekening.class
        );

        // Validate the Aantekening in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAantekeningUpdatableFieldsEquals(returnedAantekening, getPersistedAantekening(returnedAantekening));
    }

    @Test
    @Transactional
    void createAantekeningWithExistingId() throws Exception {
        // Create the Aantekening with an existing ID
        aantekening.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAantekeningMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aantekening)))
            .andExpect(status().isBadRequest());

        // Validate the Aantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAantekenings() throws Exception {
        // Initialize the database
        aantekeningRepository.saveAndFlush(aantekening);

        // Get all the aantekeningList
        restAantekeningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aantekening.getId().intValue())))
            .andExpect(jsonPath("$.[*].aard").value(hasItem(DEFAULT_AARD)))
            .andExpect(jsonPath("$.[*].begrenzing").value(hasItem(DEFAULT_BEGRENZING)))
            .andExpect(jsonPath("$.[*].betreftgedeeltevanperceel").value(hasItem(DEFAULT_BETREFTGEDEELTEVANPERCEEL.booleanValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumeinderecht").value(hasItem(DEFAULT_DATUMEINDERECHT.toString())))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getAantekening() throws Exception {
        // Initialize the database
        aantekeningRepository.saveAndFlush(aantekening);

        // Get the aantekening
        restAantekeningMockMvc
            .perform(get(ENTITY_API_URL_ID, aantekening.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aantekening.getId().intValue()))
            .andExpect(jsonPath("$.aard").value(DEFAULT_AARD))
            .andExpect(jsonPath("$.begrenzing").value(DEFAULT_BEGRENZING))
            .andExpect(jsonPath("$.betreftgedeeltevanperceel").value(DEFAULT_BETREFTGEDEELTEVANPERCEEL.booleanValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumeinderecht").value(DEFAULT_DATUMEINDERECHT.toString()))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingAantekening() throws Exception {
        // Get the aantekening
        restAantekeningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAantekening() throws Exception {
        // Initialize the database
        aantekeningRepository.saveAndFlush(aantekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aantekening
        Aantekening updatedAantekening = aantekeningRepository.findById(aantekening.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAantekening are not directly saved in db
        em.detach(updatedAantekening);
        updatedAantekening
            .aard(UPDATED_AARD)
            .begrenzing(UPDATED_BEGRENZING)
            .betreftgedeeltevanperceel(UPDATED_BETREFTGEDEELTEVANPERCEEL)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeinderecht(UPDATED_DATUMEINDERECHT)
            .identificatie(UPDATED_IDENTIFICATIE)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restAantekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAantekening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAantekening))
            )
            .andExpect(status().isOk());

        // Validate the Aantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAantekeningToMatchAllProperties(updatedAantekening);
    }

    @Test
    @Transactional
    void putNonExistingAantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aantekening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAantekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aantekening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aantekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aantekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAantekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aantekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aantekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAantekeningMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aantekening)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAantekeningWithPatch() throws Exception {
        // Initialize the database
        aantekeningRepository.saveAndFlush(aantekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aantekening using partial update
        Aantekening partialUpdatedAantekening = new Aantekening();
        partialUpdatedAantekening.setId(aantekening.getId());

        partialUpdatedAantekening
            .aard(UPDATED_AARD)
            .begrenzing(UPDATED_BEGRENZING)
            .betreftgedeeltevanperceel(UPDATED_BETREFTGEDEELTEVANPERCEEL)
            .datumeinderecht(UPDATED_DATUMEINDERECHT)
            .identificatie(UPDATED_IDENTIFICATIE)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restAantekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAantekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAantekening))
            )
            .andExpect(status().isOk());

        // Validate the Aantekening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAantekeningUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAantekening, aantekening),
            getPersistedAantekening(aantekening)
        );
    }

    @Test
    @Transactional
    void fullUpdateAantekeningWithPatch() throws Exception {
        // Initialize the database
        aantekeningRepository.saveAndFlush(aantekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aantekening using partial update
        Aantekening partialUpdatedAantekening = new Aantekening();
        partialUpdatedAantekening.setId(aantekening.getId());

        partialUpdatedAantekening
            .aard(UPDATED_AARD)
            .begrenzing(UPDATED_BEGRENZING)
            .betreftgedeeltevanperceel(UPDATED_BETREFTGEDEELTEVANPERCEEL)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeinderecht(UPDATED_DATUMEINDERECHT)
            .identificatie(UPDATED_IDENTIFICATIE)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restAantekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAantekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAantekening))
            )
            .andExpect(status().isOk());

        // Validate the Aantekening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAantekeningUpdatableFieldsEquals(partialUpdatedAantekening, getPersistedAantekening(partialUpdatedAantekening));
    }

    @Test
    @Transactional
    void patchNonExistingAantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aantekening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAantekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aantekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aantekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aantekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAantekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aantekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aantekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAantekeningMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aantekening)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAantekening() throws Exception {
        // Initialize the database
        aantekeningRepository.saveAndFlush(aantekening);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aantekening
        restAantekeningMockMvc
            .perform(delete(ENTITY_API_URL_ID, aantekening.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aantekeningRepository.count();
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

    protected Aantekening getPersistedAantekening(Aantekening aantekening) {
        return aantekeningRepository.findById(aantekening.getId()).orElseThrow();
    }

    protected void assertPersistedAantekeningToMatchAllProperties(Aantekening expectedAantekening) {
        assertAantekeningAllPropertiesEquals(expectedAantekening, getPersistedAantekening(expectedAantekening));
    }

    protected void assertPersistedAantekeningToMatchUpdatableProperties(Aantekening expectedAantekening) {
        assertAantekeningAllUpdatablePropertiesEquals(expectedAantekening, getPersistedAantekening(expectedAantekening));
    }
}
