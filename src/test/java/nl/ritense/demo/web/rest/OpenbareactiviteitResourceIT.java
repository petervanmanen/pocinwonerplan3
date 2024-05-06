package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OpenbareactiviteitAsserts.*;
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
import nl.ritense.demo.domain.Openbareactiviteit;
import nl.ritense.demo.repository.OpenbareactiviteitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OpenbareactiviteitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OpenbareactiviteitResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EVENMENTNAAM = "AAAAAAAAAA";
    private static final String UPDATED_EVENMENTNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/openbareactiviteits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OpenbareactiviteitRepository openbareactiviteitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpenbareactiviteitMockMvc;

    private Openbareactiviteit openbareactiviteit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Openbareactiviteit createEntity(EntityManager em) {
        Openbareactiviteit openbareactiviteit = new Openbareactiviteit()
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .evenmentnaam(DEFAULT_EVENMENTNAAM)
            .locatieomschrijving(DEFAULT_LOCATIEOMSCHRIJVING)
            .status(DEFAULT_STATUS);
        return openbareactiviteit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Openbareactiviteit createUpdatedEntity(EntityManager em) {
        Openbareactiviteit openbareactiviteit = new Openbareactiviteit()
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .evenmentnaam(UPDATED_EVENMENTNAAM)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .status(UPDATED_STATUS);
        return openbareactiviteit;
    }

    @BeforeEach
    public void initTest() {
        openbareactiviteit = createEntity(em);
    }

    @Test
    @Transactional
    void createOpenbareactiviteit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Openbareactiviteit
        var returnedOpenbareactiviteit = om.readValue(
            restOpenbareactiviteitMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(openbareactiviteit)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Openbareactiviteit.class
        );

        // Validate the Openbareactiviteit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOpenbareactiviteitUpdatableFieldsEquals(
            returnedOpenbareactiviteit,
            getPersistedOpenbareactiviteit(returnedOpenbareactiviteit)
        );
    }

    @Test
    @Transactional
    void createOpenbareactiviteitWithExistingId() throws Exception {
        // Create the Openbareactiviteit with an existing ID
        openbareactiviteit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpenbareactiviteitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(openbareactiviteit)))
            .andExpect(status().isBadRequest());

        // Validate the Openbareactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOpenbareactiviteits() throws Exception {
        // Initialize the database
        openbareactiviteitRepository.saveAndFlush(openbareactiviteit);

        // Get all the openbareactiviteitList
        restOpenbareactiviteitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(openbareactiviteit.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].evenmentnaam").value(hasItem(DEFAULT_EVENMENTNAAM)))
            .andExpect(jsonPath("$.[*].locatieomschrijving").value(hasItem(DEFAULT_LOCATIEOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getOpenbareactiviteit() throws Exception {
        // Initialize the database
        openbareactiviteitRepository.saveAndFlush(openbareactiviteit);

        // Get the openbareactiviteit
        restOpenbareactiviteitMockMvc
            .perform(get(ENTITY_API_URL_ID, openbareactiviteit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(openbareactiviteit.getId().intValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.evenmentnaam").value(DEFAULT_EVENMENTNAAM))
            .andExpect(jsonPath("$.locatieomschrijving").value(DEFAULT_LOCATIEOMSCHRIJVING))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingOpenbareactiviteit() throws Exception {
        // Get the openbareactiviteit
        restOpenbareactiviteitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOpenbareactiviteit() throws Exception {
        // Initialize the database
        openbareactiviteitRepository.saveAndFlush(openbareactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the openbareactiviteit
        Openbareactiviteit updatedOpenbareactiviteit = openbareactiviteitRepository.findById(openbareactiviteit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOpenbareactiviteit are not directly saved in db
        em.detach(updatedOpenbareactiviteit);
        updatedOpenbareactiviteit
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .evenmentnaam(UPDATED_EVENMENTNAAM)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .status(UPDATED_STATUS);

        restOpenbareactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOpenbareactiviteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOpenbareactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Openbareactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOpenbareactiviteitToMatchAllProperties(updatedOpenbareactiviteit);
    }

    @Test
    @Transactional
    void putNonExistingOpenbareactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        openbareactiviteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpenbareactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, openbareactiviteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(openbareactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Openbareactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOpenbareactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        openbareactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpenbareactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(openbareactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Openbareactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOpenbareactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        openbareactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpenbareactiviteitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(openbareactiviteit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Openbareactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOpenbareactiviteitWithPatch() throws Exception {
        // Initialize the database
        openbareactiviteitRepository.saveAndFlush(openbareactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the openbareactiviteit using partial update
        Openbareactiviteit partialUpdatedOpenbareactiviteit = new Openbareactiviteit();
        partialUpdatedOpenbareactiviteit.setId(openbareactiviteit.getId());

        restOpenbareactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpenbareactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpenbareactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Openbareactiviteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpenbareactiviteitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOpenbareactiviteit, openbareactiviteit),
            getPersistedOpenbareactiviteit(openbareactiviteit)
        );
    }

    @Test
    @Transactional
    void fullUpdateOpenbareactiviteitWithPatch() throws Exception {
        // Initialize the database
        openbareactiviteitRepository.saveAndFlush(openbareactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the openbareactiviteit using partial update
        Openbareactiviteit partialUpdatedOpenbareactiviteit = new Openbareactiviteit();
        partialUpdatedOpenbareactiviteit.setId(openbareactiviteit.getId());

        partialUpdatedOpenbareactiviteit
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .evenmentnaam(UPDATED_EVENMENTNAAM)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .status(UPDATED_STATUS);

        restOpenbareactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpenbareactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpenbareactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Openbareactiviteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpenbareactiviteitUpdatableFieldsEquals(
            partialUpdatedOpenbareactiviteit,
            getPersistedOpenbareactiviteit(partialUpdatedOpenbareactiviteit)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOpenbareactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        openbareactiviteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpenbareactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, openbareactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(openbareactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Openbareactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOpenbareactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        openbareactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpenbareactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(openbareactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Openbareactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOpenbareactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        openbareactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpenbareactiviteitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(openbareactiviteit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Openbareactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOpenbareactiviteit() throws Exception {
        // Initialize the database
        openbareactiviteitRepository.saveAndFlush(openbareactiviteit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the openbareactiviteit
        restOpenbareactiviteitMockMvc
            .perform(delete(ENTITY_API_URL_ID, openbareactiviteit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return openbareactiviteitRepository.count();
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

    protected Openbareactiviteit getPersistedOpenbareactiviteit(Openbareactiviteit openbareactiviteit) {
        return openbareactiviteitRepository.findById(openbareactiviteit.getId()).orElseThrow();
    }

    protected void assertPersistedOpenbareactiviteitToMatchAllProperties(Openbareactiviteit expectedOpenbareactiviteit) {
        assertOpenbareactiviteitAllPropertiesEquals(expectedOpenbareactiviteit, getPersistedOpenbareactiviteit(expectedOpenbareactiviteit));
    }

    protected void assertPersistedOpenbareactiviteitToMatchUpdatableProperties(Openbareactiviteit expectedOpenbareactiviteit) {
        assertOpenbareactiviteitAllUpdatablePropertiesEquals(
            expectedOpenbareactiviteit,
            getPersistedOpenbareactiviteit(expectedOpenbareactiviteit)
        );
    }
}
