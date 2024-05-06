package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LegesgrondslagAsserts.*;
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
import nl.ritense.demo.domain.Legesgrondslag;
import nl.ritense.demo.repository.LegesgrondslagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LegesgrondslagResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LegesgrondslagResourceIT {

    private static final String DEFAULT_AANGEMAAKTDOOR = "AAAAAAAAAA";
    private static final String UPDATED_AANGEMAAKTDOOR = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALOPGEGEVEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALOPGEGEVEN = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALVASTGESTELD = "AAAAAAAAAA";
    private static final String UPDATED_AANTALVASTGESTELD = "BBBBBBBBBB";

    private static final String DEFAULT_AUTOMATISCH = "AAAAAAAAAA";
    private static final String UPDATED_AUTOMATISCH = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMAANMAAK = "AAAAAAAAAA";
    private static final String UPDATED_DATUMAANMAAK = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMMUTATIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMMUTATIE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EENHEID = "AAAAAAAAAA";
    private static final String UPDATED_EENHEID = "BBBBBBBBBB";

    private static final String DEFAULT_GEMUTEERDDOOR = "AAAAAAAAAA";
    private static final String UPDATED_GEMUTEERDDOOR = "BBBBBBBBBB";

    private static final String DEFAULT_LEGESGRONDSLAG = "AAAAAAAAAA";
    private static final String UPDATED_LEGESGRONDSLAG = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/legesgrondslags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LegesgrondslagRepository legesgrondslagRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLegesgrondslagMockMvc;

    private Legesgrondslag legesgrondslag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Legesgrondslag createEntity(EntityManager em) {
        Legesgrondslag legesgrondslag = new Legesgrondslag()
            .aangemaaktdoor(DEFAULT_AANGEMAAKTDOOR)
            .aantalopgegeven(DEFAULT_AANTALOPGEGEVEN)
            .aantalvastgesteld(DEFAULT_AANTALVASTGESTELD)
            .automatisch(DEFAULT_AUTOMATISCH)
            .datumaanmaak(DEFAULT_DATUMAANMAAK)
            .datummutatie(DEFAULT_DATUMMUTATIE)
            .eenheid(DEFAULT_EENHEID)
            .gemuteerddoor(DEFAULT_GEMUTEERDDOOR)
            .legesgrondslag(DEFAULT_LEGESGRONDSLAG)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return legesgrondslag;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Legesgrondslag createUpdatedEntity(EntityManager em) {
        Legesgrondslag legesgrondslag = new Legesgrondslag()
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .aantalopgegeven(UPDATED_AANTALOPGEGEVEN)
            .aantalvastgesteld(UPDATED_AANTALVASTGESTELD)
            .automatisch(UPDATED_AUTOMATISCH)
            .datumaanmaak(UPDATED_DATUMAANMAAK)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .eenheid(UPDATED_EENHEID)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .legesgrondslag(UPDATED_LEGESGRONDSLAG)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return legesgrondslag;
    }

    @BeforeEach
    public void initTest() {
        legesgrondslag = createEntity(em);
    }

    @Test
    @Transactional
    void createLegesgrondslag() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Legesgrondslag
        var returnedLegesgrondslag = om.readValue(
            restLegesgrondslagMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(legesgrondslag)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Legesgrondslag.class
        );

        // Validate the Legesgrondslag in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLegesgrondslagUpdatableFieldsEquals(returnedLegesgrondslag, getPersistedLegesgrondslag(returnedLegesgrondslag));
    }

    @Test
    @Transactional
    void createLegesgrondslagWithExistingId() throws Exception {
        // Create the Legesgrondslag with an existing ID
        legesgrondslag.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLegesgrondslagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(legesgrondslag)))
            .andExpect(status().isBadRequest());

        // Validate the Legesgrondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLegesgrondslags() throws Exception {
        // Initialize the database
        legesgrondslagRepository.saveAndFlush(legesgrondslag);

        // Get all the legesgrondslagList
        restLegesgrondslagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(legesgrondslag.getId().intValue())))
            .andExpect(jsonPath("$.[*].aangemaaktdoor").value(hasItem(DEFAULT_AANGEMAAKTDOOR)))
            .andExpect(jsonPath("$.[*].aantalopgegeven").value(hasItem(DEFAULT_AANTALOPGEGEVEN)))
            .andExpect(jsonPath("$.[*].aantalvastgesteld").value(hasItem(DEFAULT_AANTALVASTGESTELD)))
            .andExpect(jsonPath("$.[*].automatisch").value(hasItem(DEFAULT_AUTOMATISCH)))
            .andExpect(jsonPath("$.[*].datumaanmaak").value(hasItem(DEFAULT_DATUMAANMAAK)))
            .andExpect(jsonPath("$.[*].datummutatie").value(hasItem(DEFAULT_DATUMMUTATIE.toString())))
            .andExpect(jsonPath("$.[*].eenheid").value(hasItem(DEFAULT_EENHEID)))
            .andExpect(jsonPath("$.[*].gemuteerddoor").value(hasItem(DEFAULT_GEMUTEERDDOOR)))
            .andExpect(jsonPath("$.[*].legesgrondslag").value(hasItem(DEFAULT_LEGESGRONDSLAG)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getLegesgrondslag() throws Exception {
        // Initialize the database
        legesgrondslagRepository.saveAndFlush(legesgrondslag);

        // Get the legesgrondslag
        restLegesgrondslagMockMvc
            .perform(get(ENTITY_API_URL_ID, legesgrondslag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(legesgrondslag.getId().intValue()))
            .andExpect(jsonPath("$.aangemaaktdoor").value(DEFAULT_AANGEMAAKTDOOR))
            .andExpect(jsonPath("$.aantalopgegeven").value(DEFAULT_AANTALOPGEGEVEN))
            .andExpect(jsonPath("$.aantalvastgesteld").value(DEFAULT_AANTALVASTGESTELD))
            .andExpect(jsonPath("$.automatisch").value(DEFAULT_AUTOMATISCH))
            .andExpect(jsonPath("$.datumaanmaak").value(DEFAULT_DATUMAANMAAK))
            .andExpect(jsonPath("$.datummutatie").value(DEFAULT_DATUMMUTATIE.toString()))
            .andExpect(jsonPath("$.eenheid").value(DEFAULT_EENHEID))
            .andExpect(jsonPath("$.gemuteerddoor").value(DEFAULT_GEMUTEERDDOOR))
            .andExpect(jsonPath("$.legesgrondslag").value(DEFAULT_LEGESGRONDSLAG))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingLegesgrondslag() throws Exception {
        // Get the legesgrondslag
        restLegesgrondslagMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLegesgrondslag() throws Exception {
        // Initialize the database
        legesgrondslagRepository.saveAndFlush(legesgrondslag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the legesgrondslag
        Legesgrondslag updatedLegesgrondslag = legesgrondslagRepository.findById(legesgrondslag.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLegesgrondslag are not directly saved in db
        em.detach(updatedLegesgrondslag);
        updatedLegesgrondslag
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .aantalopgegeven(UPDATED_AANTALOPGEGEVEN)
            .aantalvastgesteld(UPDATED_AANTALVASTGESTELD)
            .automatisch(UPDATED_AUTOMATISCH)
            .datumaanmaak(UPDATED_DATUMAANMAAK)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .eenheid(UPDATED_EENHEID)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .legesgrondslag(UPDATED_LEGESGRONDSLAG)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restLegesgrondslagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLegesgrondslag.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLegesgrondslag))
            )
            .andExpect(status().isOk());

        // Validate the Legesgrondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLegesgrondslagToMatchAllProperties(updatedLegesgrondslag);
    }

    @Test
    @Transactional
    void putNonExistingLegesgrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        legesgrondslag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLegesgrondslagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, legesgrondslag.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(legesgrondslag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Legesgrondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLegesgrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        legesgrondslag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLegesgrondslagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(legesgrondslag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Legesgrondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLegesgrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        legesgrondslag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLegesgrondslagMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(legesgrondslag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Legesgrondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLegesgrondslagWithPatch() throws Exception {
        // Initialize the database
        legesgrondslagRepository.saveAndFlush(legesgrondslag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the legesgrondslag using partial update
        Legesgrondslag partialUpdatedLegesgrondslag = new Legesgrondslag();
        partialUpdatedLegesgrondslag.setId(legesgrondslag.getId());

        partialUpdatedLegesgrondslag
            .aantalopgegeven(UPDATED_AANTALOPGEGEVEN)
            .automatisch(UPDATED_AUTOMATISCH)
            .datumaanmaak(UPDATED_DATUMAANMAAK);

        restLegesgrondslagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLegesgrondslag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLegesgrondslag))
            )
            .andExpect(status().isOk());

        // Validate the Legesgrondslag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLegesgrondslagUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLegesgrondslag, legesgrondslag),
            getPersistedLegesgrondslag(legesgrondslag)
        );
    }

    @Test
    @Transactional
    void fullUpdateLegesgrondslagWithPatch() throws Exception {
        // Initialize the database
        legesgrondslagRepository.saveAndFlush(legesgrondslag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the legesgrondslag using partial update
        Legesgrondslag partialUpdatedLegesgrondslag = new Legesgrondslag();
        partialUpdatedLegesgrondslag.setId(legesgrondslag.getId());

        partialUpdatedLegesgrondslag
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .aantalopgegeven(UPDATED_AANTALOPGEGEVEN)
            .aantalvastgesteld(UPDATED_AANTALVASTGESTELD)
            .automatisch(UPDATED_AUTOMATISCH)
            .datumaanmaak(UPDATED_DATUMAANMAAK)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .eenheid(UPDATED_EENHEID)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .legesgrondslag(UPDATED_LEGESGRONDSLAG)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restLegesgrondslagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLegesgrondslag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLegesgrondslag))
            )
            .andExpect(status().isOk());

        // Validate the Legesgrondslag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLegesgrondslagUpdatableFieldsEquals(partialUpdatedLegesgrondslag, getPersistedLegesgrondslag(partialUpdatedLegesgrondslag));
    }

    @Test
    @Transactional
    void patchNonExistingLegesgrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        legesgrondslag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLegesgrondslagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, legesgrondslag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(legesgrondslag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Legesgrondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLegesgrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        legesgrondslag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLegesgrondslagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(legesgrondslag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Legesgrondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLegesgrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        legesgrondslag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLegesgrondslagMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(legesgrondslag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Legesgrondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLegesgrondslag() throws Exception {
        // Initialize the database
        legesgrondslagRepository.saveAndFlush(legesgrondslag);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the legesgrondslag
        restLegesgrondslagMockMvc
            .perform(delete(ENTITY_API_URL_ID, legesgrondslag.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return legesgrondslagRepository.count();
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

    protected Legesgrondslag getPersistedLegesgrondslag(Legesgrondslag legesgrondslag) {
        return legesgrondslagRepository.findById(legesgrondslag.getId()).orElseThrow();
    }

    protected void assertPersistedLegesgrondslagToMatchAllProperties(Legesgrondslag expectedLegesgrondslag) {
        assertLegesgrondslagAllPropertiesEquals(expectedLegesgrondslag, getPersistedLegesgrondslag(expectedLegesgrondslag));
    }

    protected void assertPersistedLegesgrondslagToMatchUpdatableProperties(Legesgrondslag expectedLegesgrondslag) {
        assertLegesgrondslagAllUpdatablePropertiesEquals(expectedLegesgrondslag, getPersistedLegesgrondslag(expectedLegesgrondslag));
    }
}
