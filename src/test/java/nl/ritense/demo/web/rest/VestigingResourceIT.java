package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VestigingAsserts.*;
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
import nl.ritense.demo.domain.Vestiging;
import nl.ritense.demo.domain.Werkgelegenheid;
import nl.ritense.demo.repository.VestigingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VestigingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VestigingResourceIT {

    private static final String DEFAULT_COMMERCIELEVESTIGING = "AAAAAAAAAA";
    private static final String UPDATED_COMMERCIELEVESTIGING = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMAANVANG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANVANG = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMVOORTZETTING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMVOORTZETTING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FULLTIMEWERKZAMEMANNEN = "AAAAAAAAAA";
    private static final String UPDATED_FULLTIMEWERKZAMEMANNEN = "BBBBBBBBBB";

    private static final String DEFAULT_FULLTIMEWERKZAMEVROUWEN = "AAAAAAAAAA";
    private static final String UPDATED_FULLTIMEWERKZAMEVROUWEN = "BBBBBBBBBB";

    private static final String DEFAULT_HANDELSNAAM = "AAAAAAAAAA";
    private static final String UPDATED_HANDELSNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_PARTTIMEWERKZAMEMANNEN = "AAAAAAAAAA";
    private static final String UPDATED_PARTTIMEWERKZAMEMANNEN = "BBBBBBBBBB";

    private static final String DEFAULT_PARTTIMEWERKZAMEVROUWEN = "AAAAAAAAAA";
    private static final String UPDATED_PARTTIMEWERKZAMEVROUWEN = "BBBBBBBBBB";

    private static final String DEFAULT_TOEVOEGINGADRES = "AAAAAAAAAA";
    private static final String UPDATED_TOEVOEGINGADRES = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAALWERKZAMEPERSONEN = "AAAAAAAAAA";
    private static final String UPDATED_TOTAALWERKZAMEPERSONEN = "BBBBBBBBBB";

    private static final String DEFAULT_VERKORTENAAM = "AAAAAAAAAA";
    private static final String UPDATED_VERKORTENAAM = "BBBBBBBBBB";

    private static final String DEFAULT_VESTIGINGSNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_VESTIGINGSNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vestigings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VestigingRepository vestigingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVestigingMockMvc;

    private Vestiging vestiging;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vestiging createEntity(EntityManager em) {
        Vestiging vestiging = new Vestiging()
            .commercielevestiging(DEFAULT_COMMERCIELEVESTIGING)
            .datumaanvang(DEFAULT_DATUMAANVANG)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumvoortzetting(DEFAULT_DATUMVOORTZETTING)
            .fulltimewerkzamemannen(DEFAULT_FULLTIMEWERKZAMEMANNEN)
            .fulltimewerkzamevrouwen(DEFAULT_FULLTIMEWERKZAMEVROUWEN)
            .handelsnaam(DEFAULT_HANDELSNAAM)
            .parttimewerkzamemannen(DEFAULT_PARTTIMEWERKZAMEMANNEN)
            .parttimewerkzamevrouwen(DEFAULT_PARTTIMEWERKZAMEVROUWEN)
            .toevoegingadres(DEFAULT_TOEVOEGINGADRES)
            .totaalwerkzamepersonen(DEFAULT_TOTAALWERKZAMEPERSONEN)
            .verkortenaam(DEFAULT_VERKORTENAAM)
            .vestigingsnummer(DEFAULT_VESTIGINGSNUMMER);
        // Add required entity
        Werkgelegenheid werkgelegenheid;
        if (TestUtil.findAll(em, Werkgelegenheid.class).isEmpty()) {
            werkgelegenheid = WerkgelegenheidResourceIT.createEntity(em);
            em.persist(werkgelegenheid);
            em.flush();
        } else {
            werkgelegenheid = TestUtil.findAll(em, Werkgelegenheid.class).get(0);
        }
        vestiging.setHeeftWerkgelegenheid(werkgelegenheid);
        return vestiging;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vestiging createUpdatedEntity(EntityManager em) {
        Vestiging vestiging = new Vestiging()
            .commercielevestiging(UPDATED_COMMERCIELEVESTIGING)
            .datumaanvang(UPDATED_DATUMAANVANG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumvoortzetting(UPDATED_DATUMVOORTZETTING)
            .fulltimewerkzamemannen(UPDATED_FULLTIMEWERKZAMEMANNEN)
            .fulltimewerkzamevrouwen(UPDATED_FULLTIMEWERKZAMEVROUWEN)
            .handelsnaam(UPDATED_HANDELSNAAM)
            .parttimewerkzamemannen(UPDATED_PARTTIMEWERKZAMEMANNEN)
            .parttimewerkzamevrouwen(UPDATED_PARTTIMEWERKZAMEVROUWEN)
            .toevoegingadres(UPDATED_TOEVOEGINGADRES)
            .totaalwerkzamepersonen(UPDATED_TOTAALWERKZAMEPERSONEN)
            .verkortenaam(UPDATED_VERKORTENAAM)
            .vestigingsnummer(UPDATED_VESTIGINGSNUMMER);
        // Add required entity
        Werkgelegenheid werkgelegenheid;
        if (TestUtil.findAll(em, Werkgelegenheid.class).isEmpty()) {
            werkgelegenheid = WerkgelegenheidResourceIT.createUpdatedEntity(em);
            em.persist(werkgelegenheid);
            em.flush();
        } else {
            werkgelegenheid = TestUtil.findAll(em, Werkgelegenheid.class).get(0);
        }
        vestiging.setHeeftWerkgelegenheid(werkgelegenheid);
        return vestiging;
    }

    @BeforeEach
    public void initTest() {
        vestiging = createEntity(em);
    }

    @Test
    @Transactional
    void createVestiging() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vestiging
        var returnedVestiging = om.readValue(
            restVestigingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vestiging)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vestiging.class
        );

        // Validate the Vestiging in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVestigingUpdatableFieldsEquals(returnedVestiging, getPersistedVestiging(returnedVestiging));
    }

    @Test
    @Transactional
    void createVestigingWithExistingId() throws Exception {
        // Create the Vestiging with an existing ID
        vestiging.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVestigingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vestiging)))
            .andExpect(status().isBadRequest());

        // Validate the Vestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVestigings() throws Exception {
        // Initialize the database
        vestigingRepository.saveAndFlush(vestiging);

        // Get all the vestigingList
        restVestigingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vestiging.getId().intValue())))
            .andExpect(jsonPath("$.[*].commercielevestiging").value(hasItem(DEFAULT_COMMERCIELEVESTIGING)))
            .andExpect(jsonPath("$.[*].datumaanvang").value(hasItem(DEFAULT_DATUMAANVANG.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumvoortzetting").value(hasItem(DEFAULT_DATUMVOORTZETTING.toString())))
            .andExpect(jsonPath("$.[*].fulltimewerkzamemannen").value(hasItem(DEFAULT_FULLTIMEWERKZAMEMANNEN)))
            .andExpect(jsonPath("$.[*].fulltimewerkzamevrouwen").value(hasItem(DEFAULT_FULLTIMEWERKZAMEVROUWEN)))
            .andExpect(jsonPath("$.[*].handelsnaam").value(hasItem(DEFAULT_HANDELSNAAM)))
            .andExpect(jsonPath("$.[*].parttimewerkzamemannen").value(hasItem(DEFAULT_PARTTIMEWERKZAMEMANNEN)))
            .andExpect(jsonPath("$.[*].parttimewerkzamevrouwen").value(hasItem(DEFAULT_PARTTIMEWERKZAMEVROUWEN)))
            .andExpect(jsonPath("$.[*].toevoegingadres").value(hasItem(DEFAULT_TOEVOEGINGADRES)))
            .andExpect(jsonPath("$.[*].totaalwerkzamepersonen").value(hasItem(DEFAULT_TOTAALWERKZAMEPERSONEN)))
            .andExpect(jsonPath("$.[*].verkortenaam").value(hasItem(DEFAULT_VERKORTENAAM)))
            .andExpect(jsonPath("$.[*].vestigingsnummer").value(hasItem(DEFAULT_VESTIGINGSNUMMER)));
    }

    @Test
    @Transactional
    void getVestiging() throws Exception {
        // Initialize the database
        vestigingRepository.saveAndFlush(vestiging);

        // Get the vestiging
        restVestigingMockMvc
            .perform(get(ENTITY_API_URL_ID, vestiging.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vestiging.getId().intValue()))
            .andExpect(jsonPath("$.commercielevestiging").value(DEFAULT_COMMERCIELEVESTIGING))
            .andExpect(jsonPath("$.datumaanvang").value(DEFAULT_DATUMAANVANG.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumvoortzetting").value(DEFAULT_DATUMVOORTZETTING.toString()))
            .andExpect(jsonPath("$.fulltimewerkzamemannen").value(DEFAULT_FULLTIMEWERKZAMEMANNEN))
            .andExpect(jsonPath("$.fulltimewerkzamevrouwen").value(DEFAULT_FULLTIMEWERKZAMEVROUWEN))
            .andExpect(jsonPath("$.handelsnaam").value(DEFAULT_HANDELSNAAM))
            .andExpect(jsonPath("$.parttimewerkzamemannen").value(DEFAULT_PARTTIMEWERKZAMEMANNEN))
            .andExpect(jsonPath("$.parttimewerkzamevrouwen").value(DEFAULT_PARTTIMEWERKZAMEVROUWEN))
            .andExpect(jsonPath("$.toevoegingadres").value(DEFAULT_TOEVOEGINGADRES))
            .andExpect(jsonPath("$.totaalwerkzamepersonen").value(DEFAULT_TOTAALWERKZAMEPERSONEN))
            .andExpect(jsonPath("$.verkortenaam").value(DEFAULT_VERKORTENAAM))
            .andExpect(jsonPath("$.vestigingsnummer").value(DEFAULT_VESTIGINGSNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingVestiging() throws Exception {
        // Get the vestiging
        restVestigingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVestiging() throws Exception {
        // Initialize the database
        vestigingRepository.saveAndFlush(vestiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vestiging
        Vestiging updatedVestiging = vestigingRepository.findById(vestiging.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVestiging are not directly saved in db
        em.detach(updatedVestiging);
        updatedVestiging
            .commercielevestiging(UPDATED_COMMERCIELEVESTIGING)
            .datumaanvang(UPDATED_DATUMAANVANG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumvoortzetting(UPDATED_DATUMVOORTZETTING)
            .fulltimewerkzamemannen(UPDATED_FULLTIMEWERKZAMEMANNEN)
            .fulltimewerkzamevrouwen(UPDATED_FULLTIMEWERKZAMEVROUWEN)
            .handelsnaam(UPDATED_HANDELSNAAM)
            .parttimewerkzamemannen(UPDATED_PARTTIMEWERKZAMEMANNEN)
            .parttimewerkzamevrouwen(UPDATED_PARTTIMEWERKZAMEVROUWEN)
            .toevoegingadres(UPDATED_TOEVOEGINGADRES)
            .totaalwerkzamepersonen(UPDATED_TOTAALWERKZAMEPERSONEN)
            .verkortenaam(UPDATED_VERKORTENAAM)
            .vestigingsnummer(UPDATED_VESTIGINGSNUMMER);

        restVestigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVestiging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVestiging))
            )
            .andExpect(status().isOk());

        // Validate the Vestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVestigingToMatchAllProperties(updatedVestiging);
    }

    @Test
    @Transactional
    void putNonExistingVestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vestiging.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVestigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vestiging.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vestiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vestiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVestigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vestiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vestiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVestigingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vestiging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVestigingWithPatch() throws Exception {
        // Initialize the database
        vestigingRepository.saveAndFlush(vestiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vestiging using partial update
        Vestiging partialUpdatedVestiging = new Vestiging();
        partialUpdatedVestiging.setId(vestiging.getId());

        partialUpdatedVestiging
            .datumaanvang(UPDATED_DATUMAANVANG)
            .datumvoortzetting(UPDATED_DATUMVOORTZETTING)
            .fulltimewerkzamemannen(UPDATED_FULLTIMEWERKZAMEMANNEN)
            .parttimewerkzamemannen(UPDATED_PARTTIMEWERKZAMEMANNEN)
            .parttimewerkzamevrouwen(UPDATED_PARTTIMEWERKZAMEVROUWEN)
            .totaalwerkzamepersonen(UPDATED_TOTAALWERKZAMEPERSONEN)
            .verkortenaam(UPDATED_VERKORTENAAM);

        restVestigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVestiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVestiging))
            )
            .andExpect(status().isOk());

        // Validate the Vestiging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVestigingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVestiging, vestiging),
            getPersistedVestiging(vestiging)
        );
    }

    @Test
    @Transactional
    void fullUpdateVestigingWithPatch() throws Exception {
        // Initialize the database
        vestigingRepository.saveAndFlush(vestiging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vestiging using partial update
        Vestiging partialUpdatedVestiging = new Vestiging();
        partialUpdatedVestiging.setId(vestiging.getId());

        partialUpdatedVestiging
            .commercielevestiging(UPDATED_COMMERCIELEVESTIGING)
            .datumaanvang(UPDATED_DATUMAANVANG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumvoortzetting(UPDATED_DATUMVOORTZETTING)
            .fulltimewerkzamemannen(UPDATED_FULLTIMEWERKZAMEMANNEN)
            .fulltimewerkzamevrouwen(UPDATED_FULLTIMEWERKZAMEVROUWEN)
            .handelsnaam(UPDATED_HANDELSNAAM)
            .parttimewerkzamemannen(UPDATED_PARTTIMEWERKZAMEMANNEN)
            .parttimewerkzamevrouwen(UPDATED_PARTTIMEWERKZAMEVROUWEN)
            .toevoegingadres(UPDATED_TOEVOEGINGADRES)
            .totaalwerkzamepersonen(UPDATED_TOTAALWERKZAMEPERSONEN)
            .verkortenaam(UPDATED_VERKORTENAAM)
            .vestigingsnummer(UPDATED_VESTIGINGSNUMMER);

        restVestigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVestiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVestiging))
            )
            .andExpect(status().isOk());

        // Validate the Vestiging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVestigingUpdatableFieldsEquals(partialUpdatedVestiging, getPersistedVestiging(partialUpdatedVestiging));
    }

    @Test
    @Transactional
    void patchNonExistingVestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vestiging.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVestigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vestiging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vestiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vestiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVestigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vestiging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVestiging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vestiging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVestigingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vestiging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vestiging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVestiging() throws Exception {
        // Initialize the database
        vestigingRepository.saveAndFlush(vestiging);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vestiging
        restVestigingMockMvc
            .perform(delete(ENTITY_API_URL_ID, vestiging.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vestigingRepository.count();
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

    protected Vestiging getPersistedVestiging(Vestiging vestiging) {
        return vestigingRepository.findById(vestiging.getId()).orElseThrow();
    }

    protected void assertPersistedVestigingToMatchAllProperties(Vestiging expectedVestiging) {
        assertVestigingAllPropertiesEquals(expectedVestiging, getPersistedVestiging(expectedVestiging));
    }

    protected void assertPersistedVestigingToMatchUpdatableProperties(Vestiging expectedVestiging) {
        assertVestigingAllUpdatablePropertiesEquals(expectedVestiging, getPersistedVestiging(expectedVestiging));
    }
}
