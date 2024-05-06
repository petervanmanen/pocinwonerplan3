package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SubsidieprogrammaAsserts.*;
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
import nl.ritense.demo.domain.Subsidieprogramma;
import nl.ritense.demo.repository.SubsidieprogrammaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SubsidieprogrammaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubsidieprogrammaResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PROGRAMMABEGROTING = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROGRAMMABEGROTING = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/subsidieprogrammas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SubsidieprogrammaRepository subsidieprogrammaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubsidieprogrammaMockMvc;

    private Subsidieprogramma subsidieprogramma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subsidieprogramma createEntity(EntityManager em) {
        Subsidieprogramma subsidieprogramma = new Subsidieprogramma()
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .programmabegroting(DEFAULT_PROGRAMMABEGROTING);
        return subsidieprogramma;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subsidieprogramma createUpdatedEntity(EntityManager em) {
        Subsidieprogramma subsidieprogramma = new Subsidieprogramma()
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .programmabegroting(UPDATED_PROGRAMMABEGROTING);
        return subsidieprogramma;
    }

    @BeforeEach
    public void initTest() {
        subsidieprogramma = createEntity(em);
    }

    @Test
    @Transactional
    void createSubsidieprogramma() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Subsidieprogramma
        var returnedSubsidieprogramma = om.readValue(
            restSubsidieprogrammaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subsidieprogramma)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Subsidieprogramma.class
        );

        // Validate the Subsidieprogramma in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSubsidieprogrammaUpdatableFieldsEquals(returnedSubsidieprogramma, getPersistedSubsidieprogramma(returnedSubsidieprogramma));
    }

    @Test
    @Transactional
    void createSubsidieprogrammaWithExistingId() throws Exception {
        // Create the Subsidieprogramma with an existing ID
        subsidieprogramma.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubsidieprogrammaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subsidieprogramma)))
            .andExpect(status().isBadRequest());

        // Validate the Subsidieprogramma in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubsidieprogrammas() throws Exception {
        // Initialize the database
        subsidieprogrammaRepository.saveAndFlush(subsidieprogramma);

        // Get all the subsidieprogrammaList
        restSubsidieprogrammaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subsidieprogramma.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].programmabegroting").value(hasItem(sameNumber(DEFAULT_PROGRAMMABEGROTING))));
    }

    @Test
    @Transactional
    void getSubsidieprogramma() throws Exception {
        // Initialize the database
        subsidieprogrammaRepository.saveAndFlush(subsidieprogramma);

        // Get the subsidieprogramma
        restSubsidieprogrammaMockMvc
            .perform(get(ENTITY_API_URL_ID, subsidieprogramma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subsidieprogramma.getId().intValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.programmabegroting").value(sameNumber(DEFAULT_PROGRAMMABEGROTING)));
    }

    @Test
    @Transactional
    void getNonExistingSubsidieprogramma() throws Exception {
        // Get the subsidieprogramma
        restSubsidieprogrammaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubsidieprogramma() throws Exception {
        // Initialize the database
        subsidieprogrammaRepository.saveAndFlush(subsidieprogramma);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subsidieprogramma
        Subsidieprogramma updatedSubsidieprogramma = subsidieprogrammaRepository.findById(subsidieprogramma.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSubsidieprogramma are not directly saved in db
        em.detach(updatedSubsidieprogramma);
        updatedSubsidieprogramma
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .programmabegroting(UPDATED_PROGRAMMABEGROTING);

        restSubsidieprogrammaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubsidieprogramma.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSubsidieprogramma))
            )
            .andExpect(status().isOk());

        // Validate the Subsidieprogramma in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSubsidieprogrammaToMatchAllProperties(updatedSubsidieprogramma);
    }

    @Test
    @Transactional
    void putNonExistingSubsidieprogramma() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidieprogramma.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubsidieprogrammaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subsidieprogramma.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subsidieprogramma))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidieprogramma in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubsidieprogramma() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidieprogramma.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidieprogrammaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subsidieprogramma))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidieprogramma in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubsidieprogramma() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidieprogramma.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidieprogrammaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subsidieprogramma)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subsidieprogramma in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubsidieprogrammaWithPatch() throws Exception {
        // Initialize the database
        subsidieprogrammaRepository.saveAndFlush(subsidieprogramma);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subsidieprogramma using partial update
        Subsidieprogramma partialUpdatedSubsidieprogramma = new Subsidieprogramma();
        partialUpdatedSubsidieprogramma.setId(subsidieprogramma.getId());

        partialUpdatedSubsidieprogramma
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .programmabegroting(UPDATED_PROGRAMMABEGROTING);

        restSubsidieprogrammaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubsidieprogramma.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubsidieprogramma))
            )
            .andExpect(status().isOk());

        // Validate the Subsidieprogramma in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubsidieprogrammaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSubsidieprogramma, subsidieprogramma),
            getPersistedSubsidieprogramma(subsidieprogramma)
        );
    }

    @Test
    @Transactional
    void fullUpdateSubsidieprogrammaWithPatch() throws Exception {
        // Initialize the database
        subsidieprogrammaRepository.saveAndFlush(subsidieprogramma);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subsidieprogramma using partial update
        Subsidieprogramma partialUpdatedSubsidieprogramma = new Subsidieprogramma();
        partialUpdatedSubsidieprogramma.setId(subsidieprogramma.getId());

        partialUpdatedSubsidieprogramma
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .programmabegroting(UPDATED_PROGRAMMABEGROTING);

        restSubsidieprogrammaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubsidieprogramma.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubsidieprogramma))
            )
            .andExpect(status().isOk());

        // Validate the Subsidieprogramma in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubsidieprogrammaUpdatableFieldsEquals(
            partialUpdatedSubsidieprogramma,
            getPersistedSubsidieprogramma(partialUpdatedSubsidieprogramma)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSubsidieprogramma() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidieprogramma.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubsidieprogrammaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subsidieprogramma.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subsidieprogramma))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidieprogramma in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubsidieprogramma() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidieprogramma.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidieprogrammaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subsidieprogramma))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidieprogramma in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubsidieprogramma() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidieprogramma.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidieprogrammaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(subsidieprogramma)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subsidieprogramma in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubsidieprogramma() throws Exception {
        // Initialize the database
        subsidieprogrammaRepository.saveAndFlush(subsidieprogramma);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the subsidieprogramma
        restSubsidieprogrammaMockMvc
            .perform(delete(ENTITY_API_URL_ID, subsidieprogramma.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return subsidieprogrammaRepository.count();
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

    protected Subsidieprogramma getPersistedSubsidieprogramma(Subsidieprogramma subsidieprogramma) {
        return subsidieprogrammaRepository.findById(subsidieprogramma.getId()).orElseThrow();
    }

    protected void assertPersistedSubsidieprogrammaToMatchAllProperties(Subsidieprogramma expectedSubsidieprogramma) {
        assertSubsidieprogrammaAllPropertiesEquals(expectedSubsidieprogramma, getPersistedSubsidieprogramma(expectedSubsidieprogramma));
    }

    protected void assertPersistedSubsidieprogrammaToMatchUpdatableProperties(Subsidieprogramma expectedSubsidieprogramma) {
        assertSubsidieprogrammaAllUpdatablePropertiesEquals(
            expectedSubsidieprogramma,
            getPersistedSubsidieprogramma(expectedSubsidieprogramma)
        );
    }
}
