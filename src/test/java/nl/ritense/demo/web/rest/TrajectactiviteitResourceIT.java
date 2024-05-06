package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TrajectactiviteitAsserts.*;
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
import nl.ritense.demo.domain.Trajectactiviteit;
import nl.ritense.demo.repository.TrajectactiviteitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TrajectactiviteitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrajectactiviteitResourceIT {

    private static final String DEFAULT_CONTRACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT = "BBBBBBBBBB";

    private static final String DEFAULT_CREDITEUR = "AAAAAAAAAA";
    private static final String UPDATED_CREDITEUR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTATUS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTATUS = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_KINDEROPVANG = "AAAAAAAAAA";
    private static final String UPDATED_KINDEROPVANG = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_SUCCESVOL = "AAAAAAAAAA";
    private static final String UPDATED_SUCCESVOL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/trajectactiviteits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TrajectactiviteitRepository trajectactiviteitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrajectactiviteitMockMvc;

    private Trajectactiviteit trajectactiviteit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trajectactiviteit createEntity(EntityManager em) {
        Trajectactiviteit trajectactiviteit = new Trajectactiviteit()
            .contract(DEFAULT_CONTRACT)
            .crediteur(DEFAULT_CREDITEUR)
            .datumbegin(DEFAULT_DATUMBEGIN)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstatus(DEFAULT_DATUMSTATUS)
            .kinderopvang(DEFAULT_KINDEROPVANG)
            .status(DEFAULT_STATUS)
            .succesvol(DEFAULT_SUCCESVOL);
        return trajectactiviteit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trajectactiviteit createUpdatedEntity(EntityManager em) {
        Trajectactiviteit trajectactiviteit = new Trajectactiviteit()
            .contract(UPDATED_CONTRACT)
            .crediteur(UPDATED_CREDITEUR)
            .datumbegin(UPDATED_DATUMBEGIN)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstatus(UPDATED_DATUMSTATUS)
            .kinderopvang(UPDATED_KINDEROPVANG)
            .status(UPDATED_STATUS)
            .succesvol(UPDATED_SUCCESVOL);
        return trajectactiviteit;
    }

    @BeforeEach
    public void initTest() {
        trajectactiviteit = createEntity(em);
    }

    @Test
    @Transactional
    void createTrajectactiviteit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Trajectactiviteit
        var returnedTrajectactiviteit = om.readValue(
            restTrajectactiviteitMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trajectactiviteit)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Trajectactiviteit.class
        );

        // Validate the Trajectactiviteit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTrajectactiviteitUpdatableFieldsEquals(returnedTrajectactiviteit, getPersistedTrajectactiviteit(returnedTrajectactiviteit));
    }

    @Test
    @Transactional
    void createTrajectactiviteitWithExistingId() throws Exception {
        // Create the Trajectactiviteit with an existing ID
        trajectactiviteit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrajectactiviteitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trajectactiviteit)))
            .andExpect(status().isBadRequest());

        // Validate the Trajectactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrajectactiviteits() throws Exception {
        // Initialize the database
        trajectactiviteitRepository.saveAndFlush(trajectactiviteit);

        // Get all the trajectactiviteitList
        restTrajectactiviteitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trajectactiviteit.getId().intValue())))
            .andExpect(jsonPath("$.[*].contract").value(hasItem(DEFAULT_CONTRACT)))
            .andExpect(jsonPath("$.[*].crediteur").value(hasItem(DEFAULT_CREDITEUR)))
            .andExpect(jsonPath("$.[*].datumbegin").value(hasItem(DEFAULT_DATUMBEGIN.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstatus").value(hasItem(DEFAULT_DATUMSTATUS.toString())))
            .andExpect(jsonPath("$.[*].kinderopvang").value(hasItem(DEFAULT_KINDEROPVANG)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].succesvol").value(hasItem(DEFAULT_SUCCESVOL)));
    }

    @Test
    @Transactional
    void getTrajectactiviteit() throws Exception {
        // Initialize the database
        trajectactiviteitRepository.saveAndFlush(trajectactiviteit);

        // Get the trajectactiviteit
        restTrajectactiviteitMockMvc
            .perform(get(ENTITY_API_URL_ID, trajectactiviteit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trajectactiviteit.getId().intValue()))
            .andExpect(jsonPath("$.contract").value(DEFAULT_CONTRACT))
            .andExpect(jsonPath("$.crediteur").value(DEFAULT_CREDITEUR))
            .andExpect(jsonPath("$.datumbegin").value(DEFAULT_DATUMBEGIN.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstatus").value(DEFAULT_DATUMSTATUS.toString()))
            .andExpect(jsonPath("$.kinderopvang").value(DEFAULT_KINDEROPVANG))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.succesvol").value(DEFAULT_SUCCESVOL));
    }

    @Test
    @Transactional
    void getNonExistingTrajectactiviteit() throws Exception {
        // Get the trajectactiviteit
        restTrajectactiviteitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrajectactiviteit() throws Exception {
        // Initialize the database
        trajectactiviteitRepository.saveAndFlush(trajectactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trajectactiviteit
        Trajectactiviteit updatedTrajectactiviteit = trajectactiviteitRepository.findById(trajectactiviteit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTrajectactiviteit are not directly saved in db
        em.detach(updatedTrajectactiviteit);
        updatedTrajectactiviteit
            .contract(UPDATED_CONTRACT)
            .crediteur(UPDATED_CREDITEUR)
            .datumbegin(UPDATED_DATUMBEGIN)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstatus(UPDATED_DATUMSTATUS)
            .kinderopvang(UPDATED_KINDEROPVANG)
            .status(UPDATED_STATUS)
            .succesvol(UPDATED_SUCCESVOL);

        restTrajectactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTrajectactiviteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTrajectactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Trajectactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTrajectactiviteitToMatchAllProperties(updatedTrajectactiviteit);
    }

    @Test
    @Transactional
    void putNonExistingTrajectactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectactiviteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrajectactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trajectactiviteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trajectactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trajectactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrajectactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajectactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trajectactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trajectactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrajectactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajectactiviteitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trajectactiviteit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trajectactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrajectactiviteitWithPatch() throws Exception {
        // Initialize the database
        trajectactiviteitRepository.saveAndFlush(trajectactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trajectactiviteit using partial update
        Trajectactiviteit partialUpdatedTrajectactiviteit = new Trajectactiviteit();
        partialUpdatedTrajectactiviteit.setId(trajectactiviteit.getId());

        partialUpdatedTrajectactiviteit.contract(UPDATED_CONTRACT).datumbegin(UPDATED_DATUMBEGIN).kinderopvang(UPDATED_KINDEROPVANG);

        restTrajectactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrajectactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTrajectactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Trajectactiviteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrajectactiviteitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTrajectactiviteit, trajectactiviteit),
            getPersistedTrajectactiviteit(trajectactiviteit)
        );
    }

    @Test
    @Transactional
    void fullUpdateTrajectactiviteitWithPatch() throws Exception {
        // Initialize the database
        trajectactiviteitRepository.saveAndFlush(trajectactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trajectactiviteit using partial update
        Trajectactiviteit partialUpdatedTrajectactiviteit = new Trajectactiviteit();
        partialUpdatedTrajectactiviteit.setId(trajectactiviteit.getId());

        partialUpdatedTrajectactiviteit
            .contract(UPDATED_CONTRACT)
            .crediteur(UPDATED_CREDITEUR)
            .datumbegin(UPDATED_DATUMBEGIN)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstatus(UPDATED_DATUMSTATUS)
            .kinderopvang(UPDATED_KINDEROPVANG)
            .status(UPDATED_STATUS)
            .succesvol(UPDATED_SUCCESVOL);

        restTrajectactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrajectactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTrajectactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Trajectactiviteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrajectactiviteitUpdatableFieldsEquals(
            partialUpdatedTrajectactiviteit,
            getPersistedTrajectactiviteit(partialUpdatedTrajectactiviteit)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTrajectactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectactiviteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrajectactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trajectactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trajectactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trajectactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrajectactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajectactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trajectactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trajectactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrajectactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajectactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajectactiviteitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(trajectactiviteit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trajectactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrajectactiviteit() throws Exception {
        // Initialize the database
        trajectactiviteitRepository.saveAndFlush(trajectactiviteit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the trajectactiviteit
        restTrajectactiviteitMockMvc
            .perform(delete(ENTITY_API_URL_ID, trajectactiviteit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return trajectactiviteitRepository.count();
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

    protected Trajectactiviteit getPersistedTrajectactiviteit(Trajectactiviteit trajectactiviteit) {
        return trajectactiviteitRepository.findById(trajectactiviteit.getId()).orElseThrow();
    }

    protected void assertPersistedTrajectactiviteitToMatchAllProperties(Trajectactiviteit expectedTrajectactiviteit) {
        assertTrajectactiviteitAllPropertiesEquals(expectedTrajectactiviteit, getPersistedTrajectactiviteit(expectedTrajectactiviteit));
    }

    protected void assertPersistedTrajectactiviteitToMatchUpdatableProperties(Trajectactiviteit expectedTrajectactiviteit) {
        assertTrajectactiviteitAllUpdatablePropertiesEquals(
            expectedTrajectactiviteit,
            getPersistedTrajectactiviteit(expectedTrajectactiviteit)
        );
    }
}
