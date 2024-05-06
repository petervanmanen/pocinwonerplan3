package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VastgoedcontractregelAsserts.*;
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
import nl.ritense.demo.domain.Vastgoedcontractregel;
import nl.ritense.demo.repository.VastgoedcontractregelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VastgoedcontractregelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VastgoedcontractregelResourceIT {

    private static final String DEFAULT_BEDRAG = "AAAAAAAAAA";
    private static final String UPDATED_BEDRAG = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FREQUENTIE = "AAAAAAAAAA";
    private static final String UPDATED_FREQUENTIE = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vastgoedcontractregels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VastgoedcontractregelRepository vastgoedcontractregelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVastgoedcontractregelMockMvc;

    private Vastgoedcontractregel vastgoedcontractregel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vastgoedcontractregel createEntity(EntityManager em) {
        Vastgoedcontractregel vastgoedcontractregel = new Vastgoedcontractregel()
            .bedrag(DEFAULT_BEDRAG)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .frequentie(DEFAULT_FREQUENTIE)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .status(DEFAULT_STATUS)
            .type(DEFAULT_TYPE);
        return vastgoedcontractregel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vastgoedcontractregel createUpdatedEntity(EntityManager em) {
        Vastgoedcontractregel vastgoedcontractregel = new Vastgoedcontractregel()
            .bedrag(UPDATED_BEDRAG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .frequentie(UPDATED_FREQUENTIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE);
        return vastgoedcontractregel;
    }

    @BeforeEach
    public void initTest() {
        vastgoedcontractregel = createEntity(em);
    }

    @Test
    @Transactional
    void createVastgoedcontractregel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vastgoedcontractregel
        var returnedVastgoedcontractregel = om.readValue(
            restVastgoedcontractregelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vastgoedcontractregel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vastgoedcontractregel.class
        );

        // Validate the Vastgoedcontractregel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVastgoedcontractregelUpdatableFieldsEquals(
            returnedVastgoedcontractregel,
            getPersistedVastgoedcontractregel(returnedVastgoedcontractregel)
        );
    }

    @Test
    @Transactional
    void createVastgoedcontractregelWithExistingId() throws Exception {
        // Create the Vastgoedcontractregel with an existing ID
        vastgoedcontractregel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVastgoedcontractregelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vastgoedcontractregel)))
            .andExpect(status().isBadRequest());

        // Validate the Vastgoedcontractregel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVastgoedcontractregels() throws Exception {
        // Initialize the database
        vastgoedcontractregelRepository.saveAndFlush(vastgoedcontractregel);

        // Get all the vastgoedcontractregelList
        restVastgoedcontractregelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vastgoedcontractregel.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].frequentie").value(hasItem(DEFAULT_FREQUENTIE)))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getVastgoedcontractregel() throws Exception {
        // Initialize the database
        vastgoedcontractregelRepository.saveAndFlush(vastgoedcontractregel);

        // Get the vastgoedcontractregel
        restVastgoedcontractregelMockMvc
            .perform(get(ENTITY_API_URL_ID, vastgoedcontractregel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vastgoedcontractregel.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(DEFAULT_BEDRAG))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.frequentie").value(DEFAULT_FREQUENTIE))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingVastgoedcontractregel() throws Exception {
        // Get the vastgoedcontractregel
        restVastgoedcontractregelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVastgoedcontractregel() throws Exception {
        // Initialize the database
        vastgoedcontractregelRepository.saveAndFlush(vastgoedcontractregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vastgoedcontractregel
        Vastgoedcontractregel updatedVastgoedcontractregel = vastgoedcontractregelRepository
            .findById(vastgoedcontractregel.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedVastgoedcontractregel are not directly saved in db
        em.detach(updatedVastgoedcontractregel);
        updatedVastgoedcontractregel
            .bedrag(UPDATED_BEDRAG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .frequentie(UPDATED_FREQUENTIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE);

        restVastgoedcontractregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVastgoedcontractregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVastgoedcontractregel))
            )
            .andExpect(status().isOk());

        // Validate the Vastgoedcontractregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVastgoedcontractregelToMatchAllProperties(updatedVastgoedcontractregel);
    }

    @Test
    @Transactional
    void putNonExistingVastgoedcontractregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedcontractregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVastgoedcontractregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vastgoedcontractregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vastgoedcontractregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vastgoedcontractregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVastgoedcontractregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedcontractregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVastgoedcontractregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vastgoedcontractregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vastgoedcontractregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVastgoedcontractregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedcontractregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVastgoedcontractregelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vastgoedcontractregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vastgoedcontractregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVastgoedcontractregelWithPatch() throws Exception {
        // Initialize the database
        vastgoedcontractregelRepository.saveAndFlush(vastgoedcontractregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vastgoedcontractregel using partial update
        Vastgoedcontractregel partialUpdatedVastgoedcontractregel = new Vastgoedcontractregel();
        partialUpdatedVastgoedcontractregel.setId(vastgoedcontractregel.getId());

        partialUpdatedVastgoedcontractregel
            .bedrag(UPDATED_BEDRAG)
            .frequentie(UPDATED_FREQUENTIE)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .status(UPDATED_STATUS);

        restVastgoedcontractregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVastgoedcontractregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVastgoedcontractregel))
            )
            .andExpect(status().isOk());

        // Validate the Vastgoedcontractregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVastgoedcontractregelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVastgoedcontractregel, vastgoedcontractregel),
            getPersistedVastgoedcontractregel(vastgoedcontractregel)
        );
    }

    @Test
    @Transactional
    void fullUpdateVastgoedcontractregelWithPatch() throws Exception {
        // Initialize the database
        vastgoedcontractregelRepository.saveAndFlush(vastgoedcontractregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vastgoedcontractregel using partial update
        Vastgoedcontractregel partialUpdatedVastgoedcontractregel = new Vastgoedcontractregel();
        partialUpdatedVastgoedcontractregel.setId(vastgoedcontractregel.getId());

        partialUpdatedVastgoedcontractregel
            .bedrag(UPDATED_BEDRAG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .frequentie(UPDATED_FREQUENTIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE);

        restVastgoedcontractregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVastgoedcontractregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVastgoedcontractregel))
            )
            .andExpect(status().isOk());

        // Validate the Vastgoedcontractregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVastgoedcontractregelUpdatableFieldsEquals(
            partialUpdatedVastgoedcontractregel,
            getPersistedVastgoedcontractregel(partialUpdatedVastgoedcontractregel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVastgoedcontractregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedcontractregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVastgoedcontractregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vastgoedcontractregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vastgoedcontractregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vastgoedcontractregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVastgoedcontractregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedcontractregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVastgoedcontractregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vastgoedcontractregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vastgoedcontractregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVastgoedcontractregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedcontractregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVastgoedcontractregelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vastgoedcontractregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vastgoedcontractregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVastgoedcontractregel() throws Exception {
        // Initialize the database
        vastgoedcontractregelRepository.saveAndFlush(vastgoedcontractregel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vastgoedcontractregel
        restVastgoedcontractregelMockMvc
            .perform(delete(ENTITY_API_URL_ID, vastgoedcontractregel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vastgoedcontractregelRepository.count();
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

    protected Vastgoedcontractregel getPersistedVastgoedcontractregel(Vastgoedcontractregel vastgoedcontractregel) {
        return vastgoedcontractregelRepository.findById(vastgoedcontractregel.getId()).orElseThrow();
    }

    protected void assertPersistedVastgoedcontractregelToMatchAllProperties(Vastgoedcontractregel expectedVastgoedcontractregel) {
        assertVastgoedcontractregelAllPropertiesEquals(
            expectedVastgoedcontractregel,
            getPersistedVastgoedcontractregel(expectedVastgoedcontractregel)
        );
    }

    protected void assertPersistedVastgoedcontractregelToMatchUpdatableProperties(Vastgoedcontractregel expectedVastgoedcontractregel) {
        assertVastgoedcontractregelAllUpdatablePropertiesEquals(
            expectedVastgoedcontractregel,
            getPersistedVastgoedcontractregel(expectedVastgoedcontractregel)
        );
    }
}
