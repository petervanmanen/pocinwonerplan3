package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AanvraagofmeldingAsserts.*;
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
import nl.ritense.demo.domain.Aanvraagofmelding;
import nl.ritense.demo.repository.AanvraagofmeldingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AanvraagofmeldingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AanvraagofmeldingResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OPMERKINGEN = "AAAAAAAAAA";
    private static final String UPDATED_OPMERKINGEN = "BBBBBBBBBB";

    private static final String DEFAULT_REDEN = "AAAAAAAAAA";
    private static final String UPDATED_REDEN = "BBBBBBBBBB";

    private static final String DEFAULT_SOORTVERZUIMOFAANVRAAG = "AAAAAAAAAA";
    private static final String UPDATED_SOORTVERZUIMOFAANVRAAG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aanvraagofmeldings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AanvraagofmeldingRepository aanvraagofmeldingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAanvraagofmeldingMockMvc;

    private Aanvraagofmelding aanvraagofmelding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanvraagofmelding createEntity(EntityManager em) {
        Aanvraagofmelding aanvraagofmelding = new Aanvraagofmelding()
            .datum(DEFAULT_DATUM)
            .opmerkingen(DEFAULT_OPMERKINGEN)
            .reden(DEFAULT_REDEN)
            .soortverzuimofaanvraag(DEFAULT_SOORTVERZUIMOFAANVRAAG);
        return aanvraagofmelding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanvraagofmelding createUpdatedEntity(EntityManager em) {
        Aanvraagofmelding aanvraagofmelding = new Aanvraagofmelding()
            .datum(UPDATED_DATUM)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .reden(UPDATED_REDEN)
            .soortverzuimofaanvraag(UPDATED_SOORTVERZUIMOFAANVRAAG);
        return aanvraagofmelding;
    }

    @BeforeEach
    public void initTest() {
        aanvraagofmelding = createEntity(em);
    }

    @Test
    @Transactional
    void createAanvraagofmelding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aanvraagofmelding
        var returnedAanvraagofmelding = om.readValue(
            restAanvraagofmeldingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraagofmelding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aanvraagofmelding.class
        );

        // Validate the Aanvraagofmelding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAanvraagofmeldingUpdatableFieldsEquals(returnedAanvraagofmelding, getPersistedAanvraagofmelding(returnedAanvraagofmelding));
    }

    @Test
    @Transactional
    void createAanvraagofmeldingWithExistingId() throws Exception {
        // Create the Aanvraagofmelding with an existing ID
        aanvraagofmelding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAanvraagofmeldingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraagofmelding)))
            .andExpect(status().isBadRequest());

        // Validate the Aanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAanvraagofmeldings() throws Exception {
        // Initialize the database
        aanvraagofmeldingRepository.saveAndFlush(aanvraagofmelding);

        // Get all the aanvraagofmeldingList
        restAanvraagofmeldingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aanvraagofmelding.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].opmerkingen").value(hasItem(DEFAULT_OPMERKINGEN)))
            .andExpect(jsonPath("$.[*].reden").value(hasItem(DEFAULT_REDEN)))
            .andExpect(jsonPath("$.[*].soortverzuimofaanvraag").value(hasItem(DEFAULT_SOORTVERZUIMOFAANVRAAG)));
    }

    @Test
    @Transactional
    void getAanvraagofmelding() throws Exception {
        // Initialize the database
        aanvraagofmeldingRepository.saveAndFlush(aanvraagofmelding);

        // Get the aanvraagofmelding
        restAanvraagofmeldingMockMvc
            .perform(get(ENTITY_API_URL_ID, aanvraagofmelding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aanvraagofmelding.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.opmerkingen").value(DEFAULT_OPMERKINGEN))
            .andExpect(jsonPath("$.reden").value(DEFAULT_REDEN))
            .andExpect(jsonPath("$.soortverzuimofaanvraag").value(DEFAULT_SOORTVERZUIMOFAANVRAAG));
    }

    @Test
    @Transactional
    void getNonExistingAanvraagofmelding() throws Exception {
        // Get the aanvraagofmelding
        restAanvraagofmeldingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAanvraagofmelding() throws Exception {
        // Initialize the database
        aanvraagofmeldingRepository.saveAndFlush(aanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanvraagofmelding
        Aanvraagofmelding updatedAanvraagofmelding = aanvraagofmeldingRepository.findById(aanvraagofmelding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAanvraagofmelding are not directly saved in db
        em.detach(updatedAanvraagofmelding);
        updatedAanvraagofmelding
            .datum(UPDATED_DATUM)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .reden(UPDATED_REDEN)
            .soortverzuimofaanvraag(UPDATED_SOORTVERZUIMOFAANVRAAG);

        restAanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAanvraagofmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Aanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAanvraagofmeldingToMatchAllProperties(updatedAanvraagofmelding);
    }

    @Test
    @Transactional
    void putNonExistingAanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagofmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aanvraagofmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraagofmeldingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraagofmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAanvraagofmeldingWithPatch() throws Exception {
        // Initialize the database
        aanvraagofmeldingRepository.saveAndFlush(aanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanvraagofmelding using partial update
        Aanvraagofmelding partialUpdatedAanvraagofmelding = new Aanvraagofmelding();
        partialUpdatedAanvraagofmelding.setId(aanvraagofmelding.getId());

        restAanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Aanvraagofmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanvraagofmeldingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAanvraagofmelding, aanvraagofmelding),
            getPersistedAanvraagofmelding(aanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void fullUpdateAanvraagofmeldingWithPatch() throws Exception {
        // Initialize the database
        aanvraagofmeldingRepository.saveAndFlush(aanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanvraagofmelding using partial update
        Aanvraagofmelding partialUpdatedAanvraagofmelding = new Aanvraagofmelding();
        partialUpdatedAanvraagofmelding.setId(aanvraagofmelding.getId());

        partialUpdatedAanvraagofmelding
            .datum(UPDATED_DATUM)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .reden(UPDATED_REDEN)
            .soortverzuimofaanvraag(UPDATED_SOORTVERZUIMOFAANVRAAG);

        restAanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Aanvraagofmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanvraagofmeldingUpdatableFieldsEquals(
            partialUpdatedAanvraagofmelding,
            getPersistedAanvraagofmelding(partialUpdatedAanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagofmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraagofmeldingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aanvraagofmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAanvraagofmelding() throws Exception {
        // Initialize the database
        aanvraagofmeldingRepository.saveAndFlush(aanvraagofmelding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aanvraagofmelding
        restAanvraagofmeldingMockMvc
            .perform(delete(ENTITY_API_URL_ID, aanvraagofmelding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aanvraagofmeldingRepository.count();
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

    protected Aanvraagofmelding getPersistedAanvraagofmelding(Aanvraagofmelding aanvraagofmelding) {
        return aanvraagofmeldingRepository.findById(aanvraagofmelding.getId()).orElseThrow();
    }

    protected void assertPersistedAanvraagofmeldingToMatchAllProperties(Aanvraagofmelding expectedAanvraagofmelding) {
        assertAanvraagofmeldingAllPropertiesEquals(expectedAanvraagofmelding, getPersistedAanvraagofmelding(expectedAanvraagofmelding));
    }

    protected void assertPersistedAanvraagofmeldingToMatchUpdatableProperties(Aanvraagofmelding expectedAanvraagofmelding) {
        assertAanvraagofmeldingAllUpdatablePropertiesEquals(
            expectedAanvraagofmelding,
            getPersistedAanvraagofmelding(expectedAanvraagofmelding)
        );
    }
}
