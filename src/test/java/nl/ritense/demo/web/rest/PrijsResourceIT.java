package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PrijsAsserts.*;
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
import nl.ritense.demo.domain.Prijs;
import nl.ritense.demo.repository.PrijsRepository;
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
 * Integration tests for the {@link PrijsResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class PrijsResourceIT {

    private static final BigDecimal DEFAULT_BEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAG = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/prijs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PrijsRepository prijsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrijsMockMvc;

    private Prijs prijs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prijs createEntity(EntityManager em) {
        Prijs prijs = new Prijs().bedrag(DEFAULT_BEDRAG).datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID).datumstart(DEFAULT_DATUMSTART);
        return prijs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prijs createUpdatedEntity(EntityManager em) {
        Prijs prijs = new Prijs().bedrag(UPDATED_BEDRAG).datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID).datumstart(UPDATED_DATUMSTART);
        return prijs;
    }

    @BeforeEach
    public void initTest() {
        prijs = createEntity(em);
    }

    @Test
    @Transactional
    void createPrijs() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Prijs
        var returnedPrijs = om.readValue(
            restPrijsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prijs)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Prijs.class
        );

        // Validate the Prijs in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPrijsUpdatableFieldsEquals(returnedPrijs, getPersistedPrijs(returnedPrijs));
    }

    @Test
    @Transactional
    void createPrijsWithExistingId() throws Exception {
        // Create the Prijs with an existing ID
        prijs.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrijsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prijs)))
            .andExpect(status().isBadRequest());

        // Validate the Prijs in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrijs() throws Exception {
        // Initialize the database
        prijsRepository.saveAndFlush(prijs);

        // Get all the prijsList
        restPrijsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prijs.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(sameNumber(DEFAULT_BEDRAG))))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())));
    }

    @Test
    @Transactional
    void getPrijs() throws Exception {
        // Initialize the database
        prijsRepository.saveAndFlush(prijs);

        // Get the prijs
        restPrijsMockMvc
            .perform(get(ENTITY_API_URL_ID, prijs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prijs.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(sameNumber(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPrijs() throws Exception {
        // Get the prijs
        restPrijsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrijs() throws Exception {
        // Initialize the database
        prijsRepository.saveAndFlush(prijs);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prijs
        Prijs updatedPrijs = prijsRepository.findById(prijs.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPrijs are not directly saved in db
        em.detach(updatedPrijs);
        updatedPrijs.bedrag(UPDATED_BEDRAG).datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID).datumstart(UPDATED_DATUMSTART);

        restPrijsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrijs.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPrijs))
            )
            .andExpect(status().isOk());

        // Validate the Prijs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPrijsToMatchAllProperties(updatedPrijs);
    }

    @Test
    @Transactional
    void putNonExistingPrijs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijs.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrijsMockMvc
            .perform(put(ENTITY_API_URL_ID, prijs.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prijs)))
            .andExpect(status().isBadRequest());

        // Validate the Prijs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrijs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijs.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prijs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrijs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijs.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prijs)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prijs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrijsWithPatch() throws Exception {
        // Initialize the database
        prijsRepository.saveAndFlush(prijs);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prijs using partial update
        Prijs partialUpdatedPrijs = new Prijs();
        partialUpdatedPrijs.setId(prijs.getId());

        partialUpdatedPrijs.datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID);

        restPrijsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrijs.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrijs))
            )
            .andExpect(status().isOk());

        // Validate the Prijs in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPrijsUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPrijs, prijs), getPersistedPrijs(prijs));
    }

    @Test
    @Transactional
    void fullUpdatePrijsWithPatch() throws Exception {
        // Initialize the database
        prijsRepository.saveAndFlush(prijs);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prijs using partial update
        Prijs partialUpdatedPrijs = new Prijs();
        partialUpdatedPrijs.setId(prijs.getId());

        partialUpdatedPrijs.bedrag(UPDATED_BEDRAG).datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID).datumstart(UPDATED_DATUMSTART);

        restPrijsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrijs.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrijs))
            )
            .andExpect(status().isOk());

        // Validate the Prijs in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPrijsUpdatableFieldsEquals(partialUpdatedPrijs, getPersistedPrijs(partialUpdatedPrijs));
    }

    @Test
    @Transactional
    void patchNonExistingPrijs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijs.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrijsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prijs.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(prijs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrijs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijs.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prijs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrijs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijs.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(prijs)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prijs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrijs() throws Exception {
        // Initialize the database
        prijsRepository.saveAndFlush(prijs);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the prijs
        restPrijsMockMvc
            .perform(delete(ENTITY_API_URL_ID, prijs.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return prijsRepository.count();
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

    protected Prijs getPersistedPrijs(Prijs prijs) {
        return prijsRepository.findById(prijs.getId()).orElseThrow();
    }

    protected void assertPersistedPrijsToMatchAllProperties(Prijs expectedPrijs) {
        assertPrijsAllPropertiesEquals(expectedPrijs, getPersistedPrijs(expectedPrijs));
    }

    protected void assertPersistedPrijsToMatchUpdatableProperties(Prijs expectedPrijs) {
        assertPrijsAllUpdatablePropertiesEquals(expectedPrijs, getPersistedPrijs(expectedPrijs));
    }
}
