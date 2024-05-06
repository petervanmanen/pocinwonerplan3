package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OfferteaanvraagAsserts.*;
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
import nl.ritense.demo.domain.Offerteaanvraag;
import nl.ritense.demo.repository.OfferteaanvraagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OfferteaanvraagResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OfferteaanvraagResourceIT {

    private static final LocalDate DEFAULT_DATUMAANVRAAG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANVRAAG = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DATUMSLUITING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSLUITING = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/offerteaanvraags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OfferteaanvraagRepository offerteaanvraagRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfferteaanvraagMockMvc;

    private Offerteaanvraag offerteaanvraag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offerteaanvraag createEntity(EntityManager em) {
        Offerteaanvraag offerteaanvraag = new Offerteaanvraag()
            .datumaanvraag(DEFAULT_DATUMAANVRAAG)
            .datumsluiting(DEFAULT_DATUMSLUITING)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return offerteaanvraag;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offerteaanvraag createUpdatedEntity(EntityManager em) {
        Offerteaanvraag offerteaanvraag = new Offerteaanvraag()
            .datumaanvraag(UPDATED_DATUMAANVRAAG)
            .datumsluiting(UPDATED_DATUMSLUITING)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return offerteaanvraag;
    }

    @BeforeEach
    public void initTest() {
        offerteaanvraag = createEntity(em);
    }

    @Test
    @Transactional
    void createOfferteaanvraag() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Offerteaanvraag
        var returnedOfferteaanvraag = om.readValue(
            restOfferteaanvraagMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerteaanvraag)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Offerteaanvraag.class
        );

        // Validate the Offerteaanvraag in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOfferteaanvraagUpdatableFieldsEquals(returnedOfferteaanvraag, getPersistedOfferteaanvraag(returnedOfferteaanvraag));
    }

    @Test
    @Transactional
    void createOfferteaanvraagWithExistingId() throws Exception {
        // Create the Offerteaanvraag with an existing ID
        offerteaanvraag.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferteaanvraagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerteaanvraag)))
            .andExpect(status().isBadRequest());

        // Validate the Offerteaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOfferteaanvraags() throws Exception {
        // Initialize the database
        offerteaanvraagRepository.saveAndFlush(offerteaanvraag);

        // Get all the offerteaanvraagList
        restOfferteaanvraagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offerteaanvraag.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumaanvraag").value(hasItem(DEFAULT_DATUMAANVRAAG.toString())))
            .andExpect(jsonPath("$.[*].datumsluiting").value(hasItem(DEFAULT_DATUMSLUITING)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getOfferteaanvraag() throws Exception {
        // Initialize the database
        offerteaanvraagRepository.saveAndFlush(offerteaanvraag);

        // Get the offerteaanvraag
        restOfferteaanvraagMockMvc
            .perform(get(ENTITY_API_URL_ID, offerteaanvraag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offerteaanvraag.getId().intValue()))
            .andExpect(jsonPath("$.datumaanvraag").value(DEFAULT_DATUMAANVRAAG.toString()))
            .andExpect(jsonPath("$.datumsluiting").value(DEFAULT_DATUMSLUITING))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingOfferteaanvraag() throws Exception {
        // Get the offerteaanvraag
        restOfferteaanvraagMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOfferteaanvraag() throws Exception {
        // Initialize the database
        offerteaanvraagRepository.saveAndFlush(offerteaanvraag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the offerteaanvraag
        Offerteaanvraag updatedOfferteaanvraag = offerteaanvraagRepository.findById(offerteaanvraag.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOfferteaanvraag are not directly saved in db
        em.detach(updatedOfferteaanvraag);
        updatedOfferteaanvraag
            .datumaanvraag(UPDATED_DATUMAANVRAAG)
            .datumsluiting(UPDATED_DATUMSLUITING)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restOfferteaanvraagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOfferteaanvraag.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOfferteaanvraag))
            )
            .andExpect(status().isOk());

        // Validate the Offerteaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOfferteaanvraagToMatchAllProperties(updatedOfferteaanvraag);
    }

    @Test
    @Transactional
    void putNonExistingOfferteaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offerteaanvraag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferteaanvraagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offerteaanvraag.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(offerteaanvraag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offerteaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOfferteaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offerteaanvraag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferteaanvraagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(offerteaanvraag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offerteaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOfferteaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offerteaanvraag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferteaanvraagMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerteaanvraag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offerteaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOfferteaanvraagWithPatch() throws Exception {
        // Initialize the database
        offerteaanvraagRepository.saveAndFlush(offerteaanvraag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the offerteaanvraag using partial update
        Offerteaanvraag partialUpdatedOfferteaanvraag = new Offerteaanvraag();
        partialUpdatedOfferteaanvraag.setId(offerteaanvraag.getId());

        partialUpdatedOfferteaanvraag.datumsluiting(UPDATED_DATUMSLUITING).naam(UPDATED_NAAM);

        restOfferteaanvraagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOfferteaanvraag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOfferteaanvraag))
            )
            .andExpect(status().isOk());

        // Validate the Offerteaanvraag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOfferteaanvraagUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOfferteaanvraag, offerteaanvraag),
            getPersistedOfferteaanvraag(offerteaanvraag)
        );
    }

    @Test
    @Transactional
    void fullUpdateOfferteaanvraagWithPatch() throws Exception {
        // Initialize the database
        offerteaanvraagRepository.saveAndFlush(offerteaanvraag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the offerteaanvraag using partial update
        Offerteaanvraag partialUpdatedOfferteaanvraag = new Offerteaanvraag();
        partialUpdatedOfferteaanvraag.setId(offerteaanvraag.getId());

        partialUpdatedOfferteaanvraag
            .datumaanvraag(UPDATED_DATUMAANVRAAG)
            .datumsluiting(UPDATED_DATUMSLUITING)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restOfferteaanvraagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOfferteaanvraag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOfferteaanvraag))
            )
            .andExpect(status().isOk());

        // Validate the Offerteaanvraag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOfferteaanvraagUpdatableFieldsEquals(
            partialUpdatedOfferteaanvraag,
            getPersistedOfferteaanvraag(partialUpdatedOfferteaanvraag)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOfferteaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offerteaanvraag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferteaanvraagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, offerteaanvraag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(offerteaanvraag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offerteaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOfferteaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offerteaanvraag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferteaanvraagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(offerteaanvraag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offerteaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOfferteaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offerteaanvraag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferteaanvraagMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(offerteaanvraag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offerteaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOfferteaanvraag() throws Exception {
        // Initialize the database
        offerteaanvraagRepository.saveAndFlush(offerteaanvraag);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the offerteaanvraag
        restOfferteaanvraagMockMvc
            .perform(delete(ENTITY_API_URL_ID, offerteaanvraag.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return offerteaanvraagRepository.count();
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

    protected Offerteaanvraag getPersistedOfferteaanvraag(Offerteaanvraag offerteaanvraag) {
        return offerteaanvraagRepository.findById(offerteaanvraag.getId()).orElseThrow();
    }

    protected void assertPersistedOfferteaanvraagToMatchAllProperties(Offerteaanvraag expectedOfferteaanvraag) {
        assertOfferteaanvraagAllPropertiesEquals(expectedOfferteaanvraag, getPersistedOfferteaanvraag(expectedOfferteaanvraag));
    }

    protected void assertPersistedOfferteaanvraagToMatchUpdatableProperties(Offerteaanvraag expectedOfferteaanvraag) {
        assertOfferteaanvraagAllUpdatablePropertiesEquals(expectedOfferteaanvraag, getPersistedOfferteaanvraag(expectedOfferteaanvraag));
    }
}
