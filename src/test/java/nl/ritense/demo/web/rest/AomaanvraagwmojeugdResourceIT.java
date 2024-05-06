package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AomaanvraagwmojeugdAsserts.*;
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
import nl.ritense.demo.domain.Aomaanvraagwmojeugd;
import nl.ritense.demo.repository.AomaanvraagwmojeugdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AomaanvraagwmojeugdResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AomaanvraagwmojeugdResourceIT {

    private static final String DEFAULT_CLIENTREACTIE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTREACTIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBESCHIKKING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBESCHIKKING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEERSTEAFSPRAAK = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEERSTEAFSPRAAK = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMPLANVASTGESTELD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMPLANVASTGESTELD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTARTAANVRAAG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTARTAANVRAAG = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESKUNDIGHEID = "AAAAAAAAAA";
    private static final String UPDATED_DESKUNDIGHEID = "BBBBBBBBBB";

    private static final String DEFAULT_DOORLOOPMETHODIEK = "AAAAAAAAAA";
    private static final String UPDATED_DOORLOOPMETHODIEK = "BBBBBBBBBB";

    private static final String DEFAULT_MAXIMALEDOORLOOPTIJD = "AAAAAAAAAA";
    private static final String UPDATED_MAXIMALEDOORLOOPTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_REDENAFSLUITING = "AAAAAAAAAA";
    private static final String UPDATED_REDENAFSLUITING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aomaanvraagwmojeugds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AomaanvraagwmojeugdRepository aomaanvraagwmojeugdRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAomaanvraagwmojeugdMockMvc;

    private Aomaanvraagwmojeugd aomaanvraagwmojeugd;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aomaanvraagwmojeugd createEntity(EntityManager em) {
        Aomaanvraagwmojeugd aomaanvraagwmojeugd = new Aomaanvraagwmojeugd()
            .clientreactie(DEFAULT_CLIENTREACTIE)
            .datumbeschikking(DEFAULT_DATUMBESCHIKKING)
            .datumeersteafspraak(DEFAULT_DATUMEERSTEAFSPRAAK)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumplanvastgesteld(DEFAULT_DATUMPLANVASTGESTELD)
            .datumstartaanvraag(DEFAULT_DATUMSTARTAANVRAAG)
            .deskundigheid(DEFAULT_DESKUNDIGHEID)
            .doorloopmethodiek(DEFAULT_DOORLOOPMETHODIEK)
            .maximaledoorlooptijd(DEFAULT_MAXIMALEDOORLOOPTIJD)
            .redenafsluiting(DEFAULT_REDENAFSLUITING);
        return aomaanvraagwmojeugd;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aomaanvraagwmojeugd createUpdatedEntity(EntityManager em) {
        Aomaanvraagwmojeugd aomaanvraagwmojeugd = new Aomaanvraagwmojeugd()
            .clientreactie(UPDATED_CLIENTREACTIE)
            .datumbeschikking(UPDATED_DATUMBESCHIKKING)
            .datumeersteafspraak(UPDATED_DATUMEERSTEAFSPRAAK)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumplanvastgesteld(UPDATED_DATUMPLANVASTGESTELD)
            .datumstartaanvraag(UPDATED_DATUMSTARTAANVRAAG)
            .deskundigheid(UPDATED_DESKUNDIGHEID)
            .doorloopmethodiek(UPDATED_DOORLOOPMETHODIEK)
            .maximaledoorlooptijd(UPDATED_MAXIMALEDOORLOOPTIJD)
            .redenafsluiting(UPDATED_REDENAFSLUITING);
        return aomaanvraagwmojeugd;
    }

    @BeforeEach
    public void initTest() {
        aomaanvraagwmojeugd = createEntity(em);
    }

    @Test
    @Transactional
    void createAomaanvraagwmojeugd() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aomaanvraagwmojeugd
        var returnedAomaanvraagwmojeugd = om.readValue(
            restAomaanvraagwmojeugdMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aomaanvraagwmojeugd)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aomaanvraagwmojeugd.class
        );

        // Validate the Aomaanvraagwmojeugd in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAomaanvraagwmojeugdUpdatableFieldsEquals(
            returnedAomaanvraagwmojeugd,
            getPersistedAomaanvraagwmojeugd(returnedAomaanvraagwmojeugd)
        );
    }

    @Test
    @Transactional
    void createAomaanvraagwmojeugdWithExistingId() throws Exception {
        // Create the Aomaanvraagwmojeugd with an existing ID
        aomaanvraagwmojeugd.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAomaanvraagwmojeugdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aomaanvraagwmojeugd)))
            .andExpect(status().isBadRequest());

        // Validate the Aomaanvraagwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAomaanvraagwmojeugds() throws Exception {
        // Initialize the database
        aomaanvraagwmojeugdRepository.saveAndFlush(aomaanvraagwmojeugd);

        // Get all the aomaanvraagwmojeugdList
        restAomaanvraagwmojeugdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aomaanvraagwmojeugd.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientreactie").value(hasItem(DEFAULT_CLIENTREACTIE)))
            .andExpect(jsonPath("$.[*].datumbeschikking").value(hasItem(DEFAULT_DATUMBESCHIKKING.toString())))
            .andExpect(jsonPath("$.[*].datumeersteafspraak").value(hasItem(DEFAULT_DATUMEERSTEAFSPRAAK.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumplanvastgesteld").value(hasItem(DEFAULT_DATUMPLANVASTGESTELD.toString())))
            .andExpect(jsonPath("$.[*].datumstartaanvraag").value(hasItem(DEFAULT_DATUMSTARTAANVRAAG.toString())))
            .andExpect(jsonPath("$.[*].deskundigheid").value(hasItem(DEFAULT_DESKUNDIGHEID)))
            .andExpect(jsonPath("$.[*].doorloopmethodiek").value(hasItem(DEFAULT_DOORLOOPMETHODIEK)))
            .andExpect(jsonPath("$.[*].maximaledoorlooptijd").value(hasItem(DEFAULT_MAXIMALEDOORLOOPTIJD)))
            .andExpect(jsonPath("$.[*].redenafsluiting").value(hasItem(DEFAULT_REDENAFSLUITING)));
    }

    @Test
    @Transactional
    void getAomaanvraagwmojeugd() throws Exception {
        // Initialize the database
        aomaanvraagwmojeugdRepository.saveAndFlush(aomaanvraagwmojeugd);

        // Get the aomaanvraagwmojeugd
        restAomaanvraagwmojeugdMockMvc
            .perform(get(ENTITY_API_URL_ID, aomaanvraagwmojeugd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aomaanvraagwmojeugd.getId().intValue()))
            .andExpect(jsonPath("$.clientreactie").value(DEFAULT_CLIENTREACTIE))
            .andExpect(jsonPath("$.datumbeschikking").value(DEFAULT_DATUMBESCHIKKING.toString()))
            .andExpect(jsonPath("$.datumeersteafspraak").value(DEFAULT_DATUMEERSTEAFSPRAAK.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumplanvastgesteld").value(DEFAULT_DATUMPLANVASTGESTELD.toString()))
            .andExpect(jsonPath("$.datumstartaanvraag").value(DEFAULT_DATUMSTARTAANVRAAG.toString()))
            .andExpect(jsonPath("$.deskundigheid").value(DEFAULT_DESKUNDIGHEID))
            .andExpect(jsonPath("$.doorloopmethodiek").value(DEFAULT_DOORLOOPMETHODIEK))
            .andExpect(jsonPath("$.maximaledoorlooptijd").value(DEFAULT_MAXIMALEDOORLOOPTIJD))
            .andExpect(jsonPath("$.redenafsluiting").value(DEFAULT_REDENAFSLUITING));
    }

    @Test
    @Transactional
    void getNonExistingAomaanvraagwmojeugd() throws Exception {
        // Get the aomaanvraagwmojeugd
        restAomaanvraagwmojeugdMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAomaanvraagwmojeugd() throws Exception {
        // Initialize the database
        aomaanvraagwmojeugdRepository.saveAndFlush(aomaanvraagwmojeugd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aomaanvraagwmojeugd
        Aomaanvraagwmojeugd updatedAomaanvraagwmojeugd = aomaanvraagwmojeugdRepository.findById(aomaanvraagwmojeugd.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAomaanvraagwmojeugd are not directly saved in db
        em.detach(updatedAomaanvraagwmojeugd);
        updatedAomaanvraagwmojeugd
            .clientreactie(UPDATED_CLIENTREACTIE)
            .datumbeschikking(UPDATED_DATUMBESCHIKKING)
            .datumeersteafspraak(UPDATED_DATUMEERSTEAFSPRAAK)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumplanvastgesteld(UPDATED_DATUMPLANVASTGESTELD)
            .datumstartaanvraag(UPDATED_DATUMSTARTAANVRAAG)
            .deskundigheid(UPDATED_DESKUNDIGHEID)
            .doorloopmethodiek(UPDATED_DOORLOOPMETHODIEK)
            .maximaledoorlooptijd(UPDATED_MAXIMALEDOORLOOPTIJD)
            .redenafsluiting(UPDATED_REDENAFSLUITING);

        restAomaanvraagwmojeugdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAomaanvraagwmojeugd.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAomaanvraagwmojeugd))
            )
            .andExpect(status().isOk());

        // Validate the Aomaanvraagwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAomaanvraagwmojeugdToMatchAllProperties(updatedAomaanvraagwmojeugd);
    }

    @Test
    @Transactional
    void putNonExistingAomaanvraagwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aomaanvraagwmojeugd.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAomaanvraagwmojeugdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aomaanvraagwmojeugd.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aomaanvraagwmojeugd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aomaanvraagwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAomaanvraagwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aomaanvraagwmojeugd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAomaanvraagwmojeugdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aomaanvraagwmojeugd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aomaanvraagwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAomaanvraagwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aomaanvraagwmojeugd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAomaanvraagwmojeugdMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aomaanvraagwmojeugd)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aomaanvraagwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAomaanvraagwmojeugdWithPatch() throws Exception {
        // Initialize the database
        aomaanvraagwmojeugdRepository.saveAndFlush(aomaanvraagwmojeugd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aomaanvraagwmojeugd using partial update
        Aomaanvraagwmojeugd partialUpdatedAomaanvraagwmojeugd = new Aomaanvraagwmojeugd();
        partialUpdatedAomaanvraagwmojeugd.setId(aomaanvraagwmojeugd.getId());

        partialUpdatedAomaanvraagwmojeugd
            .clientreactie(UPDATED_CLIENTREACTIE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumplanvastgesteld(UPDATED_DATUMPLANVASTGESTELD)
            .datumstartaanvraag(UPDATED_DATUMSTARTAANVRAAG)
            .doorloopmethodiek(UPDATED_DOORLOOPMETHODIEK);

        restAomaanvraagwmojeugdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAomaanvraagwmojeugd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAomaanvraagwmojeugd))
            )
            .andExpect(status().isOk());

        // Validate the Aomaanvraagwmojeugd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAomaanvraagwmojeugdUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAomaanvraagwmojeugd, aomaanvraagwmojeugd),
            getPersistedAomaanvraagwmojeugd(aomaanvraagwmojeugd)
        );
    }

    @Test
    @Transactional
    void fullUpdateAomaanvraagwmojeugdWithPatch() throws Exception {
        // Initialize the database
        aomaanvraagwmojeugdRepository.saveAndFlush(aomaanvraagwmojeugd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aomaanvraagwmojeugd using partial update
        Aomaanvraagwmojeugd partialUpdatedAomaanvraagwmojeugd = new Aomaanvraagwmojeugd();
        partialUpdatedAomaanvraagwmojeugd.setId(aomaanvraagwmojeugd.getId());

        partialUpdatedAomaanvraagwmojeugd
            .clientreactie(UPDATED_CLIENTREACTIE)
            .datumbeschikking(UPDATED_DATUMBESCHIKKING)
            .datumeersteafspraak(UPDATED_DATUMEERSTEAFSPRAAK)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumplanvastgesteld(UPDATED_DATUMPLANVASTGESTELD)
            .datumstartaanvraag(UPDATED_DATUMSTARTAANVRAAG)
            .deskundigheid(UPDATED_DESKUNDIGHEID)
            .doorloopmethodiek(UPDATED_DOORLOOPMETHODIEK)
            .maximaledoorlooptijd(UPDATED_MAXIMALEDOORLOOPTIJD)
            .redenafsluiting(UPDATED_REDENAFSLUITING);

        restAomaanvraagwmojeugdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAomaanvraagwmojeugd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAomaanvraagwmojeugd))
            )
            .andExpect(status().isOk());

        // Validate the Aomaanvraagwmojeugd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAomaanvraagwmojeugdUpdatableFieldsEquals(
            partialUpdatedAomaanvraagwmojeugd,
            getPersistedAomaanvraagwmojeugd(partialUpdatedAomaanvraagwmojeugd)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAomaanvraagwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aomaanvraagwmojeugd.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAomaanvraagwmojeugdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aomaanvraagwmojeugd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aomaanvraagwmojeugd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aomaanvraagwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAomaanvraagwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aomaanvraagwmojeugd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAomaanvraagwmojeugdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aomaanvraagwmojeugd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aomaanvraagwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAomaanvraagwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aomaanvraagwmojeugd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAomaanvraagwmojeugdMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aomaanvraagwmojeugd)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aomaanvraagwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAomaanvraagwmojeugd() throws Exception {
        // Initialize the database
        aomaanvraagwmojeugdRepository.saveAndFlush(aomaanvraagwmojeugd);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aomaanvraagwmojeugd
        restAomaanvraagwmojeugdMockMvc
            .perform(delete(ENTITY_API_URL_ID, aomaanvraagwmojeugd.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aomaanvraagwmojeugdRepository.count();
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

    protected Aomaanvraagwmojeugd getPersistedAomaanvraagwmojeugd(Aomaanvraagwmojeugd aomaanvraagwmojeugd) {
        return aomaanvraagwmojeugdRepository.findById(aomaanvraagwmojeugd.getId()).orElseThrow();
    }

    protected void assertPersistedAomaanvraagwmojeugdToMatchAllProperties(Aomaanvraagwmojeugd expectedAomaanvraagwmojeugd) {
        assertAomaanvraagwmojeugdAllPropertiesEquals(
            expectedAomaanvraagwmojeugd,
            getPersistedAomaanvraagwmojeugd(expectedAomaanvraagwmojeugd)
        );
    }

    protected void assertPersistedAomaanvraagwmojeugdToMatchUpdatableProperties(Aomaanvraagwmojeugd expectedAomaanvraagwmojeugd) {
        assertAomaanvraagwmojeugdAllUpdatablePropertiesEquals(
            expectedAomaanvraagwmojeugd,
            getPersistedAomaanvraagwmojeugd(expectedAomaanvraagwmojeugd)
        );
    }
}
