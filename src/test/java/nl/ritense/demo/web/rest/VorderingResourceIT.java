package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VorderingAsserts.*;
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
import nl.ritense.demo.domain.Vordering;
import nl.ritense.demo.repository.VorderingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VorderingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VorderingResourceIT {

    private static final String DEFAULT_AANGEMAAKTDOOR = "AAAAAAAAAA";
    private static final String UPDATED_AANGEMAAKTDOOR = "BBBBBBBBBB";

    private static final String DEFAULT_BEDRAGBTW = "AAAAAAAAAA";
    private static final String UPDATED_BEDRAGBTW = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMAANMAAK = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANMAAK = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMMUTATIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMMUTATIE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEACCORDEERD = "AAAAAAAAAA";
    private static final String UPDATED_GEACCORDEERD = "BBBBBBBBBB";

    private static final String DEFAULT_GEACCORDEERDDOOR = "AAAAAAAAAA";
    private static final String UPDATED_GEACCORDEERDDOOR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_GEACCORDEERDOP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GEACCORDEERDOP = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEEXPORTEERD = "AAAAAAAAAA";
    private static final String UPDATED_GEEXPORTEERD = "BBBBBBBBBB";

    private static final String DEFAULT_GEMUTEERDDOOR = "AAAAAAAAAA";
    private static final String UPDATED_GEMUTEERDDOOR = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAALBEDRAG = "AAAAAAAAAA";
    private static final String UPDATED_TOTAALBEDRAG = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAALBEDRAGINCLUSIEF = "AAAAAAAAAA";
    private static final String UPDATED_TOTAALBEDRAGINCLUSIEF = "BBBBBBBBBB";

    private static final String DEFAULT_VORDERINGNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_VORDERINGNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vorderings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VorderingRepository vorderingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVorderingMockMvc;

    private Vordering vordering;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vordering createEntity(EntityManager em) {
        Vordering vordering = new Vordering()
            .aangemaaktdoor(DEFAULT_AANGEMAAKTDOOR)
            .bedragbtw(DEFAULT_BEDRAGBTW)
            .datumaanmaak(DEFAULT_DATUMAANMAAK)
            .datummutatie(DEFAULT_DATUMMUTATIE)
            .geaccordeerd(DEFAULT_GEACCORDEERD)
            .geaccordeerddoor(DEFAULT_GEACCORDEERDDOOR)
            .geaccordeerdop(DEFAULT_GEACCORDEERDOP)
            .geexporteerd(DEFAULT_GEEXPORTEERD)
            .gemuteerddoor(DEFAULT_GEMUTEERDDOOR)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .totaalbedrag(DEFAULT_TOTAALBEDRAG)
            .totaalbedraginclusief(DEFAULT_TOTAALBEDRAGINCLUSIEF)
            .vorderingnummer(DEFAULT_VORDERINGNUMMER);
        return vordering;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vordering createUpdatedEntity(EntityManager em) {
        Vordering vordering = new Vordering()
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .bedragbtw(UPDATED_BEDRAGBTW)
            .datumaanmaak(UPDATED_DATUMAANMAAK)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .geaccordeerd(UPDATED_GEACCORDEERD)
            .geaccordeerddoor(UPDATED_GEACCORDEERDDOOR)
            .geaccordeerdop(UPDATED_GEACCORDEERDOP)
            .geexporteerd(UPDATED_GEEXPORTEERD)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .totaalbedrag(UPDATED_TOTAALBEDRAG)
            .totaalbedraginclusief(UPDATED_TOTAALBEDRAGINCLUSIEF)
            .vorderingnummer(UPDATED_VORDERINGNUMMER);
        return vordering;
    }

    @BeforeEach
    public void initTest() {
        vordering = createEntity(em);
    }

    @Test
    @Transactional
    void createVordering() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vordering
        var returnedVordering = om.readValue(
            restVorderingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vordering)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vordering.class
        );

        // Validate the Vordering in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVorderingUpdatableFieldsEquals(returnedVordering, getPersistedVordering(returnedVordering));
    }

    @Test
    @Transactional
    void createVorderingWithExistingId() throws Exception {
        // Create the Vordering with an existing ID
        vordering.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVorderingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vordering)))
            .andExpect(status().isBadRequest());

        // Validate the Vordering in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVorderings() throws Exception {
        // Initialize the database
        vorderingRepository.saveAndFlush(vordering);

        // Get all the vorderingList
        restVorderingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vordering.getId().intValue())))
            .andExpect(jsonPath("$.[*].aangemaaktdoor").value(hasItem(DEFAULT_AANGEMAAKTDOOR)))
            .andExpect(jsonPath("$.[*].bedragbtw").value(hasItem(DEFAULT_BEDRAGBTW)))
            .andExpect(jsonPath("$.[*].datumaanmaak").value(hasItem(DEFAULT_DATUMAANMAAK.toString())))
            .andExpect(jsonPath("$.[*].datummutatie").value(hasItem(DEFAULT_DATUMMUTATIE.toString())))
            .andExpect(jsonPath("$.[*].geaccordeerd").value(hasItem(DEFAULT_GEACCORDEERD)))
            .andExpect(jsonPath("$.[*].geaccordeerddoor").value(hasItem(DEFAULT_GEACCORDEERDDOOR)))
            .andExpect(jsonPath("$.[*].geaccordeerdop").value(hasItem(DEFAULT_GEACCORDEERDOP.toString())))
            .andExpect(jsonPath("$.[*].geexporteerd").value(hasItem(DEFAULT_GEEXPORTEERD)))
            .andExpect(jsonPath("$.[*].gemuteerddoor").value(hasItem(DEFAULT_GEMUTEERDDOOR)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].totaalbedrag").value(hasItem(DEFAULT_TOTAALBEDRAG)))
            .andExpect(jsonPath("$.[*].totaalbedraginclusief").value(hasItem(DEFAULT_TOTAALBEDRAGINCLUSIEF)))
            .andExpect(jsonPath("$.[*].vorderingnummer").value(hasItem(DEFAULT_VORDERINGNUMMER)));
    }

    @Test
    @Transactional
    void getVordering() throws Exception {
        // Initialize the database
        vorderingRepository.saveAndFlush(vordering);

        // Get the vordering
        restVorderingMockMvc
            .perform(get(ENTITY_API_URL_ID, vordering.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vordering.getId().intValue()))
            .andExpect(jsonPath("$.aangemaaktdoor").value(DEFAULT_AANGEMAAKTDOOR))
            .andExpect(jsonPath("$.bedragbtw").value(DEFAULT_BEDRAGBTW))
            .andExpect(jsonPath("$.datumaanmaak").value(DEFAULT_DATUMAANMAAK.toString()))
            .andExpect(jsonPath("$.datummutatie").value(DEFAULT_DATUMMUTATIE.toString()))
            .andExpect(jsonPath("$.geaccordeerd").value(DEFAULT_GEACCORDEERD))
            .andExpect(jsonPath("$.geaccordeerddoor").value(DEFAULT_GEACCORDEERDDOOR))
            .andExpect(jsonPath("$.geaccordeerdop").value(DEFAULT_GEACCORDEERDOP.toString()))
            .andExpect(jsonPath("$.geexporteerd").value(DEFAULT_GEEXPORTEERD))
            .andExpect(jsonPath("$.gemuteerddoor").value(DEFAULT_GEMUTEERDDOOR))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.totaalbedrag").value(DEFAULT_TOTAALBEDRAG))
            .andExpect(jsonPath("$.totaalbedraginclusief").value(DEFAULT_TOTAALBEDRAGINCLUSIEF))
            .andExpect(jsonPath("$.vorderingnummer").value(DEFAULT_VORDERINGNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingVordering() throws Exception {
        // Get the vordering
        restVorderingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVordering() throws Exception {
        // Initialize the database
        vorderingRepository.saveAndFlush(vordering);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vordering
        Vordering updatedVordering = vorderingRepository.findById(vordering.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVordering are not directly saved in db
        em.detach(updatedVordering);
        updatedVordering
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .bedragbtw(UPDATED_BEDRAGBTW)
            .datumaanmaak(UPDATED_DATUMAANMAAK)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .geaccordeerd(UPDATED_GEACCORDEERD)
            .geaccordeerddoor(UPDATED_GEACCORDEERDDOOR)
            .geaccordeerdop(UPDATED_GEACCORDEERDOP)
            .geexporteerd(UPDATED_GEEXPORTEERD)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .totaalbedrag(UPDATED_TOTAALBEDRAG)
            .totaalbedraginclusief(UPDATED_TOTAALBEDRAGINCLUSIEF)
            .vorderingnummer(UPDATED_VORDERINGNUMMER);

        restVorderingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVordering.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVordering))
            )
            .andExpect(status().isOk());

        // Validate the Vordering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVorderingToMatchAllProperties(updatedVordering);
    }

    @Test
    @Transactional
    void putNonExistingVordering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vordering.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVorderingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vordering.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vordering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vordering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVordering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vordering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVorderingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vordering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vordering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVordering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vordering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVorderingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vordering)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vordering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVorderingWithPatch() throws Exception {
        // Initialize the database
        vorderingRepository.saveAndFlush(vordering);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vordering using partial update
        Vordering partialUpdatedVordering = new Vordering();
        partialUpdatedVordering.setId(vordering.getId());

        partialUpdatedVordering
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .bedragbtw(UPDATED_BEDRAGBTW)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .geaccordeerd(UPDATED_GEACCORDEERD)
            .geaccordeerddoor(UPDATED_GEACCORDEERDDOOR)
            .geaccordeerdop(UPDATED_GEACCORDEERDOP)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .totaalbedrag(UPDATED_TOTAALBEDRAG)
            .totaalbedraginclusief(UPDATED_TOTAALBEDRAGINCLUSIEF)
            .vorderingnummer(UPDATED_VORDERINGNUMMER);

        restVorderingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVordering.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVordering))
            )
            .andExpect(status().isOk());

        // Validate the Vordering in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVorderingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVordering, vordering),
            getPersistedVordering(vordering)
        );
    }

    @Test
    @Transactional
    void fullUpdateVorderingWithPatch() throws Exception {
        // Initialize the database
        vorderingRepository.saveAndFlush(vordering);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vordering using partial update
        Vordering partialUpdatedVordering = new Vordering();
        partialUpdatedVordering.setId(vordering.getId());

        partialUpdatedVordering
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .bedragbtw(UPDATED_BEDRAGBTW)
            .datumaanmaak(UPDATED_DATUMAANMAAK)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .geaccordeerd(UPDATED_GEACCORDEERD)
            .geaccordeerddoor(UPDATED_GEACCORDEERDDOOR)
            .geaccordeerdop(UPDATED_GEACCORDEERDOP)
            .geexporteerd(UPDATED_GEEXPORTEERD)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .totaalbedrag(UPDATED_TOTAALBEDRAG)
            .totaalbedraginclusief(UPDATED_TOTAALBEDRAGINCLUSIEF)
            .vorderingnummer(UPDATED_VORDERINGNUMMER);

        restVorderingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVordering.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVordering))
            )
            .andExpect(status().isOk());

        // Validate the Vordering in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVorderingUpdatableFieldsEquals(partialUpdatedVordering, getPersistedVordering(partialUpdatedVordering));
    }

    @Test
    @Transactional
    void patchNonExistingVordering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vordering.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVorderingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vordering.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vordering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vordering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVordering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vordering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVorderingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vordering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vordering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVordering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vordering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVorderingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vordering)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vordering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVordering() throws Exception {
        // Initialize the database
        vorderingRepository.saveAndFlush(vordering);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vordering
        restVorderingMockMvc
            .perform(delete(ENTITY_API_URL_ID, vordering.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vorderingRepository.count();
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

    protected Vordering getPersistedVordering(Vordering vordering) {
        return vorderingRepository.findById(vordering.getId()).orElseThrow();
    }

    protected void assertPersistedVorderingToMatchAllProperties(Vordering expectedVordering) {
        assertVorderingAllPropertiesEquals(expectedVordering, getPersistedVordering(expectedVordering));
    }

    protected void assertPersistedVorderingToMatchUpdatableProperties(Vordering expectedVordering) {
        assertVorderingAllUpdatablePropertiesEquals(expectedVordering, getPersistedVordering(expectedVordering));
    }
}
