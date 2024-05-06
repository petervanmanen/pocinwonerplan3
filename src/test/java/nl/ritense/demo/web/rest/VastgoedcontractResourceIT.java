package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VastgoedcontractAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Vastgoedcontract;
import nl.ritense.demo.repository.VastgoedcontractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VastgoedcontractResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VastgoedcontractResourceIT {

    private static final String DEFAULT_BESCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_BESCHRIJVING = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MAANDBEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_MAANDBEDRAG = new BigDecimal(2);

    private static final String DEFAULT_OPZEGTERMIJN = "AAAAAAAAAA";
    private static final String UPDATED_OPZEGTERMIJN = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vastgoedcontracts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VastgoedcontractRepository vastgoedcontractRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVastgoedcontractMockMvc;

    private Vastgoedcontract vastgoedcontract;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vastgoedcontract createEntity(EntityManager em) {
        Vastgoedcontract vastgoedcontract = new Vastgoedcontract()
            .beschrijving(DEFAULT_BESCHRIJVING)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .maandbedrag(DEFAULT_MAANDBEDRAG)
            .opzegtermijn(DEFAULT_OPZEGTERMIJN)
            .status(DEFAULT_STATUS)
            .type(DEFAULT_TYPE);
        return vastgoedcontract;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vastgoedcontract createUpdatedEntity(EntityManager em) {
        Vastgoedcontract vastgoedcontract = new Vastgoedcontract()
            .beschrijving(UPDATED_BESCHRIJVING)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .identificatie(UPDATED_IDENTIFICATIE)
            .maandbedrag(UPDATED_MAANDBEDRAG)
            .opzegtermijn(UPDATED_OPZEGTERMIJN)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE);
        return vastgoedcontract;
    }

    @BeforeEach
    public void initTest() {
        vastgoedcontract = createEntity(em);
    }

    @Test
    @Transactional
    void createVastgoedcontract() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vastgoedcontract
        var returnedVastgoedcontract = om.readValue(
            restVastgoedcontractMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vastgoedcontract)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vastgoedcontract.class
        );

        // Validate the Vastgoedcontract in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVastgoedcontractUpdatableFieldsEquals(returnedVastgoedcontract, getPersistedVastgoedcontract(returnedVastgoedcontract));
    }

    @Test
    @Transactional
    void createVastgoedcontractWithExistingId() throws Exception {
        // Create the Vastgoedcontract with an existing ID
        vastgoedcontract.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVastgoedcontractMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vastgoedcontract)))
            .andExpect(status().isBadRequest());

        // Validate the Vastgoedcontract in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVastgoedcontracts() throws Exception {
        // Initialize the database
        vastgoedcontractRepository.saveAndFlush(vastgoedcontract);

        // Get all the vastgoedcontractList
        restVastgoedcontractMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vastgoedcontract.getId().intValue())))
            .andExpect(jsonPath("$.[*].beschrijving").value(hasItem(DEFAULT_BESCHRIJVING)))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].maandbedrag").value(hasItem(sameNumber(DEFAULT_MAANDBEDRAG))))
            .andExpect(jsonPath("$.[*].opzegtermijn").value(hasItem(DEFAULT_OPZEGTERMIJN)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getVastgoedcontract() throws Exception {
        // Initialize the database
        vastgoedcontractRepository.saveAndFlush(vastgoedcontract);

        // Get the vastgoedcontract
        restVastgoedcontractMockMvc
            .perform(get(ENTITY_API_URL_ID, vastgoedcontract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vastgoedcontract.getId().intValue()))
            .andExpect(jsonPath("$.beschrijving").value(DEFAULT_BESCHRIJVING))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.maandbedrag").value(sameNumber(DEFAULT_MAANDBEDRAG)))
            .andExpect(jsonPath("$.opzegtermijn").value(DEFAULT_OPZEGTERMIJN))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingVastgoedcontract() throws Exception {
        // Get the vastgoedcontract
        restVastgoedcontractMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVastgoedcontract() throws Exception {
        // Initialize the database
        vastgoedcontractRepository.saveAndFlush(vastgoedcontract);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vastgoedcontract
        Vastgoedcontract updatedVastgoedcontract = vastgoedcontractRepository.findById(vastgoedcontract.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVastgoedcontract are not directly saved in db
        em.detach(updatedVastgoedcontract);
        updatedVastgoedcontract
            .beschrijving(UPDATED_BESCHRIJVING)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .identificatie(UPDATED_IDENTIFICATIE)
            .maandbedrag(UPDATED_MAANDBEDRAG)
            .opzegtermijn(UPDATED_OPZEGTERMIJN)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE);

        restVastgoedcontractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVastgoedcontract.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVastgoedcontract))
            )
            .andExpect(status().isOk());

        // Validate the Vastgoedcontract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVastgoedcontractToMatchAllProperties(updatedVastgoedcontract);
    }

    @Test
    @Transactional
    void putNonExistingVastgoedcontract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedcontract.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVastgoedcontractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vastgoedcontract.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vastgoedcontract))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vastgoedcontract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVastgoedcontract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedcontract.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVastgoedcontractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vastgoedcontract))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vastgoedcontract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVastgoedcontract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedcontract.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVastgoedcontractMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vastgoedcontract)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vastgoedcontract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVastgoedcontractWithPatch() throws Exception {
        // Initialize the database
        vastgoedcontractRepository.saveAndFlush(vastgoedcontract);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vastgoedcontract using partial update
        Vastgoedcontract partialUpdatedVastgoedcontract = new Vastgoedcontract();
        partialUpdatedVastgoedcontract.setId(vastgoedcontract.getId());

        partialUpdatedVastgoedcontract
            .beschrijving(UPDATED_BESCHRIJVING)
            .datumeinde(UPDATED_DATUMEINDE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .opzegtermijn(UPDATED_OPZEGTERMIJN);

        restVastgoedcontractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVastgoedcontract.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVastgoedcontract))
            )
            .andExpect(status().isOk());

        // Validate the Vastgoedcontract in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVastgoedcontractUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVastgoedcontract, vastgoedcontract),
            getPersistedVastgoedcontract(vastgoedcontract)
        );
    }

    @Test
    @Transactional
    void fullUpdateVastgoedcontractWithPatch() throws Exception {
        // Initialize the database
        vastgoedcontractRepository.saveAndFlush(vastgoedcontract);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vastgoedcontract using partial update
        Vastgoedcontract partialUpdatedVastgoedcontract = new Vastgoedcontract();
        partialUpdatedVastgoedcontract.setId(vastgoedcontract.getId());

        partialUpdatedVastgoedcontract
            .beschrijving(UPDATED_BESCHRIJVING)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .identificatie(UPDATED_IDENTIFICATIE)
            .maandbedrag(UPDATED_MAANDBEDRAG)
            .opzegtermijn(UPDATED_OPZEGTERMIJN)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE);

        restVastgoedcontractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVastgoedcontract.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVastgoedcontract))
            )
            .andExpect(status().isOk());

        // Validate the Vastgoedcontract in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVastgoedcontractUpdatableFieldsEquals(
            partialUpdatedVastgoedcontract,
            getPersistedVastgoedcontract(partialUpdatedVastgoedcontract)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVastgoedcontract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedcontract.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVastgoedcontractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vastgoedcontract.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vastgoedcontract))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vastgoedcontract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVastgoedcontract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedcontract.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVastgoedcontractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vastgoedcontract))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vastgoedcontract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVastgoedcontract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vastgoedcontract.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVastgoedcontractMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vastgoedcontract)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vastgoedcontract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVastgoedcontract() throws Exception {
        // Initialize the database
        vastgoedcontractRepository.saveAndFlush(vastgoedcontract);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vastgoedcontract
        restVastgoedcontractMockMvc
            .perform(delete(ENTITY_API_URL_ID, vastgoedcontract.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vastgoedcontractRepository.count();
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

    protected Vastgoedcontract getPersistedVastgoedcontract(Vastgoedcontract vastgoedcontract) {
        return vastgoedcontractRepository.findById(vastgoedcontract.getId()).orElseThrow();
    }

    protected void assertPersistedVastgoedcontractToMatchAllProperties(Vastgoedcontract expectedVastgoedcontract) {
        assertVastgoedcontractAllPropertiesEquals(expectedVastgoedcontract, getPersistedVastgoedcontract(expectedVastgoedcontract));
    }

    protected void assertPersistedVastgoedcontractToMatchUpdatableProperties(Vastgoedcontract expectedVastgoedcontract) {
        assertVastgoedcontractAllUpdatablePropertiesEquals(
            expectedVastgoedcontract,
            getPersistedVastgoedcontract(expectedVastgoedcontract)
        );
    }
}
