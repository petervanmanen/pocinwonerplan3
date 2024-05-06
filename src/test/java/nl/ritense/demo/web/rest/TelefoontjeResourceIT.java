package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TelefoontjeAsserts.*;
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
import nl.ritense.demo.domain.Telefoontje;
import nl.ritense.demo.repository.TelefoontjeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TelefoontjeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TelefoontjeResourceIT {

    private static final String DEFAULT_AFHANDELTIJDNAGESPREK = "AAAAAAAAAA";
    private static final String UPDATED_AFHANDELTIJDNAGESPREK = "BBBBBBBBBB";

    private static final String DEFAULT_DELTAISDNCONNECTIE = "AAAAAAAAAA";
    private static final String UPDATED_DELTAISDNCONNECTIE = "BBBBBBBBBB";

    private static final String DEFAULT_EINDTIJD = "AAAAAAAAAA";
    private static final String UPDATED_EINDTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_STARTTIJD = "AAAAAAAAAA";
    private static final String UPDATED_STARTTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_TOTALEONHOLDTIJD = "AAAAAAAAAA";
    private static final String UPDATED_TOTALEONHOLDTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_TOTALESPREEKTIJD = "AAAAAAAAAA";
    private static final String UPDATED_TOTALESPREEKTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_TOTALEWACHTTIJD = "AAAAAAAAAA";
    private static final String UPDATED_TOTALEWACHTTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_TOTLATETIJDSDUUR = "AAAAAAAAAA";
    private static final String UPDATED_TOTLATETIJDSDUUR = "BBBBBBBBBB";

    private static final String DEFAULT_TRACKID = "AAAAAAAAAA";
    private static final String UPDATED_TRACKID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/telefoontjes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TelefoontjeRepository telefoontjeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTelefoontjeMockMvc;

    private Telefoontje telefoontje;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefoontje createEntity(EntityManager em) {
        Telefoontje telefoontje = new Telefoontje()
            .afhandeltijdnagesprek(DEFAULT_AFHANDELTIJDNAGESPREK)
            .deltaisdnconnectie(DEFAULT_DELTAISDNCONNECTIE)
            .eindtijd(DEFAULT_EINDTIJD)
            .starttijd(DEFAULT_STARTTIJD)
            .totaleonholdtijd(DEFAULT_TOTALEONHOLDTIJD)
            .totalespreektijd(DEFAULT_TOTALESPREEKTIJD)
            .totalewachttijd(DEFAULT_TOTALEWACHTTIJD)
            .totlatetijdsduur(DEFAULT_TOTLATETIJDSDUUR)
            .trackid(DEFAULT_TRACKID);
        return telefoontje;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefoontje createUpdatedEntity(EntityManager em) {
        Telefoontje telefoontje = new Telefoontje()
            .afhandeltijdnagesprek(UPDATED_AFHANDELTIJDNAGESPREK)
            .deltaisdnconnectie(UPDATED_DELTAISDNCONNECTIE)
            .eindtijd(UPDATED_EINDTIJD)
            .starttijd(UPDATED_STARTTIJD)
            .totaleonholdtijd(UPDATED_TOTALEONHOLDTIJD)
            .totalespreektijd(UPDATED_TOTALESPREEKTIJD)
            .totalewachttijd(UPDATED_TOTALEWACHTTIJD)
            .totlatetijdsduur(UPDATED_TOTLATETIJDSDUUR)
            .trackid(UPDATED_TRACKID);
        return telefoontje;
    }

    @BeforeEach
    public void initTest() {
        telefoontje = createEntity(em);
    }

    @Test
    @Transactional
    void createTelefoontje() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Telefoontje
        var returnedTelefoontje = om.readValue(
            restTelefoontjeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telefoontje)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Telefoontje.class
        );

        // Validate the Telefoontje in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTelefoontjeUpdatableFieldsEquals(returnedTelefoontje, getPersistedTelefoontje(returnedTelefoontje));
    }

    @Test
    @Transactional
    void createTelefoontjeWithExistingId() throws Exception {
        // Create the Telefoontje with an existing ID
        telefoontje.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelefoontjeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telefoontje)))
            .andExpect(status().isBadRequest());

        // Validate the Telefoontje in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTelefoontjes() throws Exception {
        // Initialize the database
        telefoontjeRepository.saveAndFlush(telefoontje);

        // Get all the telefoontjeList
        restTelefoontjeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telefoontje.getId().intValue())))
            .andExpect(jsonPath("$.[*].afhandeltijdnagesprek").value(hasItem(DEFAULT_AFHANDELTIJDNAGESPREK)))
            .andExpect(jsonPath("$.[*].deltaisdnconnectie").value(hasItem(DEFAULT_DELTAISDNCONNECTIE)))
            .andExpect(jsonPath("$.[*].eindtijd").value(hasItem(DEFAULT_EINDTIJD)))
            .andExpect(jsonPath("$.[*].starttijd").value(hasItem(DEFAULT_STARTTIJD)))
            .andExpect(jsonPath("$.[*].totaleonholdtijd").value(hasItem(DEFAULT_TOTALEONHOLDTIJD)))
            .andExpect(jsonPath("$.[*].totalespreektijd").value(hasItem(DEFAULT_TOTALESPREEKTIJD)))
            .andExpect(jsonPath("$.[*].totalewachttijd").value(hasItem(DEFAULT_TOTALEWACHTTIJD)))
            .andExpect(jsonPath("$.[*].totlatetijdsduur").value(hasItem(DEFAULT_TOTLATETIJDSDUUR)))
            .andExpect(jsonPath("$.[*].trackid").value(hasItem(DEFAULT_TRACKID)));
    }

    @Test
    @Transactional
    void getTelefoontje() throws Exception {
        // Initialize the database
        telefoontjeRepository.saveAndFlush(telefoontje);

        // Get the telefoontje
        restTelefoontjeMockMvc
            .perform(get(ENTITY_API_URL_ID, telefoontje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(telefoontje.getId().intValue()))
            .andExpect(jsonPath("$.afhandeltijdnagesprek").value(DEFAULT_AFHANDELTIJDNAGESPREK))
            .andExpect(jsonPath("$.deltaisdnconnectie").value(DEFAULT_DELTAISDNCONNECTIE))
            .andExpect(jsonPath("$.eindtijd").value(DEFAULT_EINDTIJD))
            .andExpect(jsonPath("$.starttijd").value(DEFAULT_STARTTIJD))
            .andExpect(jsonPath("$.totaleonholdtijd").value(DEFAULT_TOTALEONHOLDTIJD))
            .andExpect(jsonPath("$.totalespreektijd").value(DEFAULT_TOTALESPREEKTIJD))
            .andExpect(jsonPath("$.totalewachttijd").value(DEFAULT_TOTALEWACHTTIJD))
            .andExpect(jsonPath("$.totlatetijdsduur").value(DEFAULT_TOTLATETIJDSDUUR))
            .andExpect(jsonPath("$.trackid").value(DEFAULT_TRACKID));
    }

    @Test
    @Transactional
    void getNonExistingTelefoontje() throws Exception {
        // Get the telefoontje
        restTelefoontjeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTelefoontje() throws Exception {
        // Initialize the database
        telefoontjeRepository.saveAndFlush(telefoontje);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the telefoontje
        Telefoontje updatedTelefoontje = telefoontjeRepository.findById(telefoontje.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTelefoontje are not directly saved in db
        em.detach(updatedTelefoontje);
        updatedTelefoontje
            .afhandeltijdnagesprek(UPDATED_AFHANDELTIJDNAGESPREK)
            .deltaisdnconnectie(UPDATED_DELTAISDNCONNECTIE)
            .eindtijd(UPDATED_EINDTIJD)
            .starttijd(UPDATED_STARTTIJD)
            .totaleonholdtijd(UPDATED_TOTALEONHOLDTIJD)
            .totalespreektijd(UPDATED_TOTALESPREEKTIJD)
            .totalewachttijd(UPDATED_TOTALEWACHTTIJD)
            .totlatetijdsduur(UPDATED_TOTLATETIJDSDUUR)
            .trackid(UPDATED_TRACKID);

        restTelefoontjeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTelefoontje.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTelefoontje))
            )
            .andExpect(status().isOk());

        // Validate the Telefoontje in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTelefoontjeToMatchAllProperties(updatedTelefoontje);
    }

    @Test
    @Transactional
    void putNonExistingTelefoontje() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoontje.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelefoontjeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telefoontje.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(telefoontje))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefoontje in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTelefoontje() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoontje.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoontjeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(telefoontje))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefoontje in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTelefoontje() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoontje.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoontjeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telefoontje)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Telefoontje in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTelefoontjeWithPatch() throws Exception {
        // Initialize the database
        telefoontjeRepository.saveAndFlush(telefoontje);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the telefoontje using partial update
        Telefoontje partialUpdatedTelefoontje = new Telefoontje();
        partialUpdatedTelefoontje.setId(telefoontje.getId());

        partialUpdatedTelefoontje.afhandeltijdnagesprek(UPDATED_AFHANDELTIJDNAGESPREK).totlatetijdsduur(UPDATED_TOTLATETIJDSDUUR);

        restTelefoontjeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelefoontje.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTelefoontje))
            )
            .andExpect(status().isOk());

        // Validate the Telefoontje in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTelefoontjeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTelefoontje, telefoontje),
            getPersistedTelefoontje(telefoontje)
        );
    }

    @Test
    @Transactional
    void fullUpdateTelefoontjeWithPatch() throws Exception {
        // Initialize the database
        telefoontjeRepository.saveAndFlush(telefoontje);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the telefoontje using partial update
        Telefoontje partialUpdatedTelefoontje = new Telefoontje();
        partialUpdatedTelefoontje.setId(telefoontje.getId());

        partialUpdatedTelefoontje
            .afhandeltijdnagesprek(UPDATED_AFHANDELTIJDNAGESPREK)
            .deltaisdnconnectie(UPDATED_DELTAISDNCONNECTIE)
            .eindtijd(UPDATED_EINDTIJD)
            .starttijd(UPDATED_STARTTIJD)
            .totaleonholdtijd(UPDATED_TOTALEONHOLDTIJD)
            .totalespreektijd(UPDATED_TOTALESPREEKTIJD)
            .totalewachttijd(UPDATED_TOTALEWACHTTIJD)
            .totlatetijdsduur(UPDATED_TOTLATETIJDSDUUR)
            .trackid(UPDATED_TRACKID);

        restTelefoontjeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelefoontje.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTelefoontje))
            )
            .andExpect(status().isOk());

        // Validate the Telefoontje in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTelefoontjeUpdatableFieldsEquals(partialUpdatedTelefoontje, getPersistedTelefoontje(partialUpdatedTelefoontje));
    }

    @Test
    @Transactional
    void patchNonExistingTelefoontje() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoontje.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelefoontjeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, telefoontje.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(telefoontje))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefoontje in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTelefoontje() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoontje.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoontjeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(telefoontje))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefoontje in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTelefoontje() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoontje.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoontjeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(telefoontje)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Telefoontje in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTelefoontje() throws Exception {
        // Initialize the database
        telefoontjeRepository.saveAndFlush(telefoontje);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the telefoontje
        restTelefoontjeMockMvc
            .perform(delete(ENTITY_API_URL_ID, telefoontje.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return telefoontjeRepository.count();
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

    protected Telefoontje getPersistedTelefoontje(Telefoontje telefoontje) {
        return telefoontjeRepository.findById(telefoontje.getId()).orElseThrow();
    }

    protected void assertPersistedTelefoontjeToMatchAllProperties(Telefoontje expectedTelefoontje) {
        assertTelefoontjeAllPropertiesEquals(expectedTelefoontje, getPersistedTelefoontje(expectedTelefoontje));
    }

    protected void assertPersistedTelefoontjeToMatchUpdatableProperties(Telefoontje expectedTelefoontje) {
        assertTelefoontjeAllUpdatablePropertiesEquals(expectedTelefoontje, getPersistedTelefoontje(expectedTelefoontje));
    }
}
