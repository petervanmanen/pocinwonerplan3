package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ContractAsserts.*;
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
import nl.ritense.demo.domain.Contract;
import nl.ritense.demo.repository.ContractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ContractResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class ContractResourceIT {

    private static final String DEFAULT_AUTORISATIEGROEP = "AAAAAAAAAA";
    private static final String UPDATED_AUTORISATIEGROEP = "BBBBBBBBBB";

    private static final String DEFAULT_BESCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_BESCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_CLASSIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACTREVISIE = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACTREVISIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMCREATIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMCREATIE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GROEP = "AAAAAAAAAA";
    private static final String UPDATED_GROEP = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNCONTRACTID = "AAAAAAAAAA";
    private static final String UPDATED_INTERNCONTRACTID = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNCONTRACTREVISIE = "AAAAAAAAAA";
    private static final String UPDATED_INTERNCONTRACTREVISIE = "BBBBBBBBBB";

    private static final String DEFAULT_OPMERKINGEN = "AAAAAAAAAA";
    private static final String UPDATED_OPMERKINGEN = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VOORWAARDE = "AAAAAAAAAA";
    private static final String UPDATED_VOORWAARDE = "BBBBBBBBBB";

    private static final String DEFAULT_ZOEKWOORDEN = "AAAAAAAAAA";
    private static final String UPDATED_ZOEKWOORDEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contracts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContractMockMvc;

    private Contract contract;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contract createEntity(EntityManager em) {
        Contract contract = new Contract()
            .autorisatiegroep(DEFAULT_AUTORISATIEGROEP)
            .beschrijving(DEFAULT_BESCHRIJVING)
            .categorie(DEFAULT_CATEGORIE)
            .classificatie(DEFAULT_CLASSIFICATIE)
            .contractrevisie(DEFAULT_CONTRACTREVISIE)
            .datumcreatie(DEFAULT_DATUMCREATIE)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .groep(DEFAULT_GROEP)
            .interncontractid(DEFAULT_INTERNCONTRACTID)
            .interncontractrevisie(DEFAULT_INTERNCONTRACTREVISIE)
            .opmerkingen(DEFAULT_OPMERKINGEN)
            .status(DEFAULT_STATUS)
            .type(DEFAULT_TYPE)
            .voorwaarde(DEFAULT_VOORWAARDE)
            .zoekwoorden(DEFAULT_ZOEKWOORDEN);
        return contract;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contract createUpdatedEntity(EntityManager em) {
        Contract contract = new Contract()
            .autorisatiegroep(UPDATED_AUTORISATIEGROEP)
            .beschrijving(UPDATED_BESCHRIJVING)
            .categorie(UPDATED_CATEGORIE)
            .classificatie(UPDATED_CLASSIFICATIE)
            .contractrevisie(UPDATED_CONTRACTREVISIE)
            .datumcreatie(UPDATED_DATUMCREATIE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .groep(UPDATED_GROEP)
            .interncontractid(UPDATED_INTERNCONTRACTID)
            .interncontractrevisie(UPDATED_INTERNCONTRACTREVISIE)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .voorwaarde(UPDATED_VOORWAARDE)
            .zoekwoorden(UPDATED_ZOEKWOORDEN);
        return contract;
    }

    @BeforeEach
    public void initTest() {
        contract = createEntity(em);
    }

    @Test
    @Transactional
    void createContract() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Contract
        var returnedContract = om.readValue(
            restContractMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contract)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Contract.class
        );

        // Validate the Contract in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertContractUpdatableFieldsEquals(returnedContract, getPersistedContract(returnedContract));
    }

    @Test
    @Transactional
    void createContractWithExistingId() throws Exception {
        // Create the Contract with an existing ID
        contract.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contract)))
            .andExpect(status().isBadRequest());

        // Validate the Contract in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContracts() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList
        restContractMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contract.getId().intValue())))
            .andExpect(jsonPath("$.[*].autorisatiegroep").value(hasItem(DEFAULT_AUTORISATIEGROEP)))
            .andExpect(jsonPath("$.[*].beschrijving").value(hasItem(DEFAULT_BESCHRIJVING)))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE)))
            .andExpect(jsonPath("$.[*].classificatie").value(hasItem(DEFAULT_CLASSIFICATIE)))
            .andExpect(jsonPath("$.[*].contractrevisie").value(hasItem(DEFAULT_CONTRACTREVISIE)))
            .andExpect(jsonPath("$.[*].datumcreatie").value(hasItem(DEFAULT_DATUMCREATIE.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].groep").value(hasItem(DEFAULT_GROEP)))
            .andExpect(jsonPath("$.[*].interncontractid").value(hasItem(DEFAULT_INTERNCONTRACTID)))
            .andExpect(jsonPath("$.[*].interncontractrevisie").value(hasItem(DEFAULT_INTERNCONTRACTREVISIE)))
            .andExpect(jsonPath("$.[*].opmerkingen").value(hasItem(DEFAULT_OPMERKINGEN)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].voorwaarde").value(hasItem(DEFAULT_VOORWAARDE)))
            .andExpect(jsonPath("$.[*].zoekwoorden").value(hasItem(DEFAULT_ZOEKWOORDEN)));
    }

    @Test
    @Transactional
    void getContract() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get the contract
        restContractMockMvc
            .perform(get(ENTITY_API_URL_ID, contract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contract.getId().intValue()))
            .andExpect(jsonPath("$.autorisatiegroep").value(DEFAULT_AUTORISATIEGROEP))
            .andExpect(jsonPath("$.beschrijving").value(DEFAULT_BESCHRIJVING))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE))
            .andExpect(jsonPath("$.classificatie").value(DEFAULT_CLASSIFICATIE))
            .andExpect(jsonPath("$.contractrevisie").value(DEFAULT_CONTRACTREVISIE))
            .andExpect(jsonPath("$.datumcreatie").value(DEFAULT_DATUMCREATIE.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.groep").value(DEFAULT_GROEP))
            .andExpect(jsonPath("$.interncontractid").value(DEFAULT_INTERNCONTRACTID))
            .andExpect(jsonPath("$.interncontractrevisie").value(DEFAULT_INTERNCONTRACTREVISIE))
            .andExpect(jsonPath("$.opmerkingen").value(DEFAULT_OPMERKINGEN))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.voorwaarde").value(DEFAULT_VOORWAARDE))
            .andExpect(jsonPath("$.zoekwoorden").value(DEFAULT_ZOEKWOORDEN));
    }

    @Test
    @Transactional
    void getNonExistingContract() throws Exception {
        // Get the contract
        restContractMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContract() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contract
        Contract updatedContract = contractRepository.findById(contract.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedContract are not directly saved in db
        em.detach(updatedContract);
        updatedContract
            .autorisatiegroep(UPDATED_AUTORISATIEGROEP)
            .beschrijving(UPDATED_BESCHRIJVING)
            .categorie(UPDATED_CATEGORIE)
            .classificatie(UPDATED_CLASSIFICATIE)
            .contractrevisie(UPDATED_CONTRACTREVISIE)
            .datumcreatie(UPDATED_DATUMCREATIE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .groep(UPDATED_GROEP)
            .interncontractid(UPDATED_INTERNCONTRACTID)
            .interncontractrevisie(UPDATED_INTERNCONTRACTREVISIE)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .voorwaarde(UPDATED_VOORWAARDE)
            .zoekwoorden(UPDATED_ZOEKWOORDEN);

        restContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContract.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedContract))
            )
            .andExpect(status().isOk());

        // Validate the Contract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContractToMatchAllProperties(updatedContract);
    }

    @Test
    @Transactional
    void putNonExistingContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contract.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contract.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contract))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contract.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contract))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contract.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contract)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContractWithPatch() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contract using partial update
        Contract partialUpdatedContract = new Contract();
        partialUpdatedContract.setId(contract.getId());

        partialUpdatedContract
            .categorie(UPDATED_CATEGORIE)
            .classificatie(UPDATED_CLASSIFICATIE)
            .interncontractid(UPDATED_INTERNCONTRACTID)
            .interncontractrevisie(UPDATED_INTERNCONTRACTREVISIE)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .zoekwoorden(UPDATED_ZOEKWOORDEN);

        restContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContract.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContract))
            )
            .andExpect(status().isOk());

        // Validate the Contract in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContractUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedContract, contract), getPersistedContract(contract));
    }

    @Test
    @Transactional
    void fullUpdateContractWithPatch() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contract using partial update
        Contract partialUpdatedContract = new Contract();
        partialUpdatedContract.setId(contract.getId());

        partialUpdatedContract
            .autorisatiegroep(UPDATED_AUTORISATIEGROEP)
            .beschrijving(UPDATED_BESCHRIJVING)
            .categorie(UPDATED_CATEGORIE)
            .classificatie(UPDATED_CLASSIFICATIE)
            .contractrevisie(UPDATED_CONTRACTREVISIE)
            .datumcreatie(UPDATED_DATUMCREATIE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .groep(UPDATED_GROEP)
            .interncontractid(UPDATED_INTERNCONTRACTID)
            .interncontractrevisie(UPDATED_INTERNCONTRACTREVISIE)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .voorwaarde(UPDATED_VOORWAARDE)
            .zoekwoorden(UPDATED_ZOEKWOORDEN);

        restContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContract.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContract))
            )
            .andExpect(status().isOk());

        // Validate the Contract in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContractUpdatableFieldsEquals(partialUpdatedContract, getPersistedContract(partialUpdatedContract));
    }

    @Test
    @Transactional
    void patchNonExistingContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contract.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contract.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contract))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contract.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contract))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contract.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(contract)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContract() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the contract
        restContractMockMvc
            .perform(delete(ENTITY_API_URL_ID, contract.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return contractRepository.count();
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

    protected Contract getPersistedContract(Contract contract) {
        return contractRepository.findById(contract.getId()).orElseThrow();
    }

    protected void assertPersistedContractToMatchAllProperties(Contract expectedContract) {
        assertContractAllPropertiesEquals(expectedContract, getPersistedContract(expectedContract));
    }

    protected void assertPersistedContractToMatchUpdatableProperties(Contract expectedContract) {
        assertContractAllUpdatablePropertiesEquals(expectedContract, getPersistedContract(expectedContract));
    }
}
