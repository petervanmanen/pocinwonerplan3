package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.IndividueelkeuzebudgetAsserts.*;
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
import nl.ritense.demo.domain.Individueelkeuzebudget;
import nl.ritense.demo.repository.IndividueelkeuzebudgetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IndividueelkeuzebudgetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndividueelkeuzebudgetResourceIT {

    private static final BigDecimal DEFAULT_BEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAG = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMTOEKENNING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMTOEKENNING = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/individueelkeuzebudgets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IndividueelkeuzebudgetRepository individueelkeuzebudgetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndividueelkeuzebudgetMockMvc;

    private Individueelkeuzebudget individueelkeuzebudget;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Individueelkeuzebudget createEntity(EntityManager em) {
        Individueelkeuzebudget individueelkeuzebudget = new Individueelkeuzebudget()
            .bedrag(DEFAULT_BEDRAG)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .datumtoekenning(DEFAULT_DATUMTOEKENNING);
        return individueelkeuzebudget;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Individueelkeuzebudget createUpdatedEntity(EntityManager em) {
        Individueelkeuzebudget individueelkeuzebudget = new Individueelkeuzebudget()
            .bedrag(UPDATED_BEDRAG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumtoekenning(UPDATED_DATUMTOEKENNING);
        return individueelkeuzebudget;
    }

    @BeforeEach
    public void initTest() {
        individueelkeuzebudget = createEntity(em);
    }

    @Test
    @Transactional
    void createIndividueelkeuzebudget() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Individueelkeuzebudget
        var returnedIndividueelkeuzebudget = om.readValue(
            restIndividueelkeuzebudgetMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(individueelkeuzebudget)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Individueelkeuzebudget.class
        );

        // Validate the Individueelkeuzebudget in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertIndividueelkeuzebudgetUpdatableFieldsEquals(
            returnedIndividueelkeuzebudget,
            getPersistedIndividueelkeuzebudget(returnedIndividueelkeuzebudget)
        );
    }

    @Test
    @Transactional
    void createIndividueelkeuzebudgetWithExistingId() throws Exception {
        // Create the Individueelkeuzebudget with an existing ID
        individueelkeuzebudget.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndividueelkeuzebudgetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(individueelkeuzebudget)))
            .andExpect(status().isBadRequest());

        // Validate the Individueelkeuzebudget in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIndividueelkeuzebudgets() throws Exception {
        // Initialize the database
        individueelkeuzebudgetRepository.saveAndFlush(individueelkeuzebudget);

        // Get all the individueelkeuzebudgetList
        restIndividueelkeuzebudgetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(individueelkeuzebudget.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(sameNumber(DEFAULT_BEDRAG))))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].datumtoekenning").value(hasItem(DEFAULT_DATUMTOEKENNING.toString())));
    }

    @Test
    @Transactional
    void getIndividueelkeuzebudget() throws Exception {
        // Initialize the database
        individueelkeuzebudgetRepository.saveAndFlush(individueelkeuzebudget);

        // Get the individueelkeuzebudget
        restIndividueelkeuzebudgetMockMvc
            .perform(get(ENTITY_API_URL_ID, individueelkeuzebudget.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(individueelkeuzebudget.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(sameNumber(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.datumtoekenning").value(DEFAULT_DATUMTOEKENNING.toString()));
    }

    @Test
    @Transactional
    void getNonExistingIndividueelkeuzebudget() throws Exception {
        // Get the individueelkeuzebudget
        restIndividueelkeuzebudgetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIndividueelkeuzebudget() throws Exception {
        // Initialize the database
        individueelkeuzebudgetRepository.saveAndFlush(individueelkeuzebudget);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the individueelkeuzebudget
        Individueelkeuzebudget updatedIndividueelkeuzebudget = individueelkeuzebudgetRepository
            .findById(individueelkeuzebudget.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedIndividueelkeuzebudget are not directly saved in db
        em.detach(updatedIndividueelkeuzebudget);
        updatedIndividueelkeuzebudget
            .bedrag(UPDATED_BEDRAG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumtoekenning(UPDATED_DATUMTOEKENNING);

        restIndividueelkeuzebudgetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIndividueelkeuzebudget.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedIndividueelkeuzebudget))
            )
            .andExpect(status().isOk());

        // Validate the Individueelkeuzebudget in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIndividueelkeuzebudgetToMatchAllProperties(updatedIndividueelkeuzebudget);
    }

    @Test
    @Transactional
    void putNonExistingIndividueelkeuzebudget() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        individueelkeuzebudget.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndividueelkeuzebudgetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, individueelkeuzebudget.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(individueelkeuzebudget))
            )
            .andExpect(status().isBadRequest());

        // Validate the Individueelkeuzebudget in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndividueelkeuzebudget() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        individueelkeuzebudget.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndividueelkeuzebudgetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(individueelkeuzebudget))
            )
            .andExpect(status().isBadRequest());

        // Validate the Individueelkeuzebudget in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndividueelkeuzebudget() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        individueelkeuzebudget.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndividueelkeuzebudgetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(individueelkeuzebudget)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Individueelkeuzebudget in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndividueelkeuzebudgetWithPatch() throws Exception {
        // Initialize the database
        individueelkeuzebudgetRepository.saveAndFlush(individueelkeuzebudget);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the individueelkeuzebudget using partial update
        Individueelkeuzebudget partialUpdatedIndividueelkeuzebudget = new Individueelkeuzebudget();
        partialUpdatedIndividueelkeuzebudget.setId(individueelkeuzebudget.getId());

        partialUpdatedIndividueelkeuzebudget.bedrag(UPDATED_BEDRAG).datumeinde(UPDATED_DATUMEINDE).datumtoekenning(UPDATED_DATUMTOEKENNING);

        restIndividueelkeuzebudgetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndividueelkeuzebudget.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIndividueelkeuzebudget))
            )
            .andExpect(status().isOk());

        // Validate the Individueelkeuzebudget in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIndividueelkeuzebudgetUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedIndividueelkeuzebudget, individueelkeuzebudget),
            getPersistedIndividueelkeuzebudget(individueelkeuzebudget)
        );
    }

    @Test
    @Transactional
    void fullUpdateIndividueelkeuzebudgetWithPatch() throws Exception {
        // Initialize the database
        individueelkeuzebudgetRepository.saveAndFlush(individueelkeuzebudget);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the individueelkeuzebudget using partial update
        Individueelkeuzebudget partialUpdatedIndividueelkeuzebudget = new Individueelkeuzebudget();
        partialUpdatedIndividueelkeuzebudget.setId(individueelkeuzebudget.getId());

        partialUpdatedIndividueelkeuzebudget
            .bedrag(UPDATED_BEDRAG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumtoekenning(UPDATED_DATUMTOEKENNING);

        restIndividueelkeuzebudgetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndividueelkeuzebudget.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIndividueelkeuzebudget))
            )
            .andExpect(status().isOk());

        // Validate the Individueelkeuzebudget in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIndividueelkeuzebudgetUpdatableFieldsEquals(
            partialUpdatedIndividueelkeuzebudget,
            getPersistedIndividueelkeuzebudget(partialUpdatedIndividueelkeuzebudget)
        );
    }

    @Test
    @Transactional
    void patchNonExistingIndividueelkeuzebudget() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        individueelkeuzebudget.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndividueelkeuzebudgetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, individueelkeuzebudget.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(individueelkeuzebudget))
            )
            .andExpect(status().isBadRequest());

        // Validate the Individueelkeuzebudget in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndividueelkeuzebudget() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        individueelkeuzebudget.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndividueelkeuzebudgetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(individueelkeuzebudget))
            )
            .andExpect(status().isBadRequest());

        // Validate the Individueelkeuzebudget in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndividueelkeuzebudget() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        individueelkeuzebudget.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndividueelkeuzebudgetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(individueelkeuzebudget))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Individueelkeuzebudget in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndividueelkeuzebudget() throws Exception {
        // Initialize the database
        individueelkeuzebudgetRepository.saveAndFlush(individueelkeuzebudget);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the individueelkeuzebudget
        restIndividueelkeuzebudgetMockMvc
            .perform(delete(ENTITY_API_URL_ID, individueelkeuzebudget.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return individueelkeuzebudgetRepository.count();
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

    protected Individueelkeuzebudget getPersistedIndividueelkeuzebudget(Individueelkeuzebudget individueelkeuzebudget) {
        return individueelkeuzebudgetRepository.findById(individueelkeuzebudget.getId()).orElseThrow();
    }

    protected void assertPersistedIndividueelkeuzebudgetToMatchAllProperties(Individueelkeuzebudget expectedIndividueelkeuzebudget) {
        assertIndividueelkeuzebudgetAllPropertiesEquals(
            expectedIndividueelkeuzebudget,
            getPersistedIndividueelkeuzebudget(expectedIndividueelkeuzebudget)
        );
    }

    protected void assertPersistedIndividueelkeuzebudgetToMatchUpdatableProperties(Individueelkeuzebudget expectedIndividueelkeuzebudget) {
        assertIndividueelkeuzebudgetAllUpdatablePropertiesEquals(
            expectedIndividueelkeuzebudget,
            getPersistedIndividueelkeuzebudget(expectedIndividueelkeuzebudget)
        );
    }
}
