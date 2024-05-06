package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ProcesverbaalonderwijsAsserts.*;
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
import nl.ritense.demo.domain.Procesverbaalonderwijs;
import nl.ritense.demo.repository.ProcesverbaalonderwijsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProcesverbaalonderwijsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProcesverbaalonderwijsResourceIT {

    private static final LocalDate DEFAULT_DATUMAFGEHANDELD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAFGEHANDELD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEPROEFTIJD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEPROEFTIJD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINGELICHT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINGELICHT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMUITSPRAAK = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMUITSPRAAK = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMZITTING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMZITTING = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_GELDBOETE = new BigDecimal(1);
    private static final BigDecimal UPDATED_GELDBOETE = new BigDecimal(2);

    private static final String DEFAULT_GELDBOETEVOORWAARDELIJK = "AAAAAAAAAA";
    private static final String UPDATED_GELDBOETEVOORWAARDELIJK = "BBBBBBBBBB";

    private static final String DEFAULT_OPMERKINGEN = "AAAAAAAAAA";
    private static final String UPDATED_OPMERKINGEN = "BBBBBBBBBB";

    private static final String DEFAULT_PROEFTIJD = "AAAAAAAAAA";
    private static final String UPDATED_PROEFTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_REDEN = "AAAAAAAAAA";
    private static final String UPDATED_REDEN = "BBBBBBBBBB";

    private static final String DEFAULT_SANCTIESOORT = "AAAAAAAAAA";
    private static final String UPDATED_SANCTIESOORT = "BBBBBBBBBB";

    private static final String DEFAULT_UITSPRAAK = "AAAAAAAAAA";
    private static final String UPDATED_UITSPRAAK = "BBBBBBBBBB";

    private static final String DEFAULT_VERZUIMSOORT = "AAAAAAAAAA";
    private static final String UPDATED_VERZUIMSOORT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/procesverbaalonderwijs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProcesverbaalonderwijsRepository procesverbaalonderwijsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcesverbaalonderwijsMockMvc;

    private Procesverbaalonderwijs procesverbaalonderwijs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Procesverbaalonderwijs createEntity(EntityManager em) {
        Procesverbaalonderwijs procesverbaalonderwijs = new Procesverbaalonderwijs()
            .datumafgehandeld(DEFAULT_DATUMAFGEHANDELD)
            .datumeindeproeftijd(DEFAULT_DATUMEINDEPROEFTIJD)
            .datumingelicht(DEFAULT_DATUMINGELICHT)
            .datumuitspraak(DEFAULT_DATUMUITSPRAAK)
            .datumzitting(DEFAULT_DATUMZITTING)
            .geldboete(DEFAULT_GELDBOETE)
            .geldboetevoorwaardelijk(DEFAULT_GELDBOETEVOORWAARDELIJK)
            .opmerkingen(DEFAULT_OPMERKINGEN)
            .proeftijd(DEFAULT_PROEFTIJD)
            .reden(DEFAULT_REDEN)
            .sanctiesoort(DEFAULT_SANCTIESOORT)
            .uitspraak(DEFAULT_UITSPRAAK)
            .verzuimsoort(DEFAULT_VERZUIMSOORT);
        return procesverbaalonderwijs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Procesverbaalonderwijs createUpdatedEntity(EntityManager em) {
        Procesverbaalonderwijs procesverbaalonderwijs = new Procesverbaalonderwijs()
            .datumafgehandeld(UPDATED_DATUMAFGEHANDELD)
            .datumeindeproeftijd(UPDATED_DATUMEINDEPROEFTIJD)
            .datumingelicht(UPDATED_DATUMINGELICHT)
            .datumuitspraak(UPDATED_DATUMUITSPRAAK)
            .datumzitting(UPDATED_DATUMZITTING)
            .geldboete(UPDATED_GELDBOETE)
            .geldboetevoorwaardelijk(UPDATED_GELDBOETEVOORWAARDELIJK)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .proeftijd(UPDATED_PROEFTIJD)
            .reden(UPDATED_REDEN)
            .sanctiesoort(UPDATED_SANCTIESOORT)
            .uitspraak(UPDATED_UITSPRAAK)
            .verzuimsoort(UPDATED_VERZUIMSOORT);
        return procesverbaalonderwijs;
    }

    @BeforeEach
    public void initTest() {
        procesverbaalonderwijs = createEntity(em);
    }

    @Test
    @Transactional
    void createProcesverbaalonderwijs() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Procesverbaalonderwijs
        var returnedProcesverbaalonderwijs = om.readValue(
            restProcesverbaalonderwijsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(procesverbaalonderwijs)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Procesverbaalonderwijs.class
        );

        // Validate the Procesverbaalonderwijs in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProcesverbaalonderwijsUpdatableFieldsEquals(
            returnedProcesverbaalonderwijs,
            getPersistedProcesverbaalonderwijs(returnedProcesverbaalonderwijs)
        );
    }

    @Test
    @Transactional
    void createProcesverbaalonderwijsWithExistingId() throws Exception {
        // Create the Procesverbaalonderwijs with an existing ID
        procesverbaalonderwijs.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcesverbaalonderwijsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(procesverbaalonderwijs)))
            .andExpect(status().isBadRequest());

        // Validate the Procesverbaalonderwijs in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProcesverbaalonderwijs() throws Exception {
        // Initialize the database
        procesverbaalonderwijsRepository.saveAndFlush(procesverbaalonderwijs);

        // Get all the procesverbaalonderwijsList
        restProcesverbaalonderwijsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procesverbaalonderwijs.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumafgehandeld").value(hasItem(DEFAULT_DATUMAFGEHANDELD.toString())))
            .andExpect(jsonPath("$.[*].datumeindeproeftijd").value(hasItem(DEFAULT_DATUMEINDEPROEFTIJD.toString())))
            .andExpect(jsonPath("$.[*].datumingelicht").value(hasItem(DEFAULT_DATUMINGELICHT.toString())))
            .andExpect(jsonPath("$.[*].datumuitspraak").value(hasItem(DEFAULT_DATUMUITSPRAAK.toString())))
            .andExpect(jsonPath("$.[*].datumzitting").value(hasItem(DEFAULT_DATUMZITTING.toString())))
            .andExpect(jsonPath("$.[*].geldboete").value(hasItem(sameNumber(DEFAULT_GELDBOETE))))
            .andExpect(jsonPath("$.[*].geldboetevoorwaardelijk").value(hasItem(DEFAULT_GELDBOETEVOORWAARDELIJK)))
            .andExpect(jsonPath("$.[*].opmerkingen").value(hasItem(DEFAULT_OPMERKINGEN)))
            .andExpect(jsonPath("$.[*].proeftijd").value(hasItem(DEFAULT_PROEFTIJD)))
            .andExpect(jsonPath("$.[*].reden").value(hasItem(DEFAULT_REDEN)))
            .andExpect(jsonPath("$.[*].sanctiesoort").value(hasItem(DEFAULT_SANCTIESOORT)))
            .andExpect(jsonPath("$.[*].uitspraak").value(hasItem(DEFAULT_UITSPRAAK)))
            .andExpect(jsonPath("$.[*].verzuimsoort").value(hasItem(DEFAULT_VERZUIMSOORT)));
    }

    @Test
    @Transactional
    void getProcesverbaalonderwijs() throws Exception {
        // Initialize the database
        procesverbaalonderwijsRepository.saveAndFlush(procesverbaalonderwijs);

        // Get the procesverbaalonderwijs
        restProcesverbaalonderwijsMockMvc
            .perform(get(ENTITY_API_URL_ID, procesverbaalonderwijs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(procesverbaalonderwijs.getId().intValue()))
            .andExpect(jsonPath("$.datumafgehandeld").value(DEFAULT_DATUMAFGEHANDELD.toString()))
            .andExpect(jsonPath("$.datumeindeproeftijd").value(DEFAULT_DATUMEINDEPROEFTIJD.toString()))
            .andExpect(jsonPath("$.datumingelicht").value(DEFAULT_DATUMINGELICHT.toString()))
            .andExpect(jsonPath("$.datumuitspraak").value(DEFAULT_DATUMUITSPRAAK.toString()))
            .andExpect(jsonPath("$.datumzitting").value(DEFAULT_DATUMZITTING.toString()))
            .andExpect(jsonPath("$.geldboete").value(sameNumber(DEFAULT_GELDBOETE)))
            .andExpect(jsonPath("$.geldboetevoorwaardelijk").value(DEFAULT_GELDBOETEVOORWAARDELIJK))
            .andExpect(jsonPath("$.opmerkingen").value(DEFAULT_OPMERKINGEN))
            .andExpect(jsonPath("$.proeftijd").value(DEFAULT_PROEFTIJD))
            .andExpect(jsonPath("$.reden").value(DEFAULT_REDEN))
            .andExpect(jsonPath("$.sanctiesoort").value(DEFAULT_SANCTIESOORT))
            .andExpect(jsonPath("$.uitspraak").value(DEFAULT_UITSPRAAK))
            .andExpect(jsonPath("$.verzuimsoort").value(DEFAULT_VERZUIMSOORT));
    }

    @Test
    @Transactional
    void getNonExistingProcesverbaalonderwijs() throws Exception {
        // Get the procesverbaalonderwijs
        restProcesverbaalonderwijsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProcesverbaalonderwijs() throws Exception {
        // Initialize the database
        procesverbaalonderwijsRepository.saveAndFlush(procesverbaalonderwijs);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the procesverbaalonderwijs
        Procesverbaalonderwijs updatedProcesverbaalonderwijs = procesverbaalonderwijsRepository
            .findById(procesverbaalonderwijs.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedProcesverbaalonderwijs are not directly saved in db
        em.detach(updatedProcesverbaalonderwijs);
        updatedProcesverbaalonderwijs
            .datumafgehandeld(UPDATED_DATUMAFGEHANDELD)
            .datumeindeproeftijd(UPDATED_DATUMEINDEPROEFTIJD)
            .datumingelicht(UPDATED_DATUMINGELICHT)
            .datumuitspraak(UPDATED_DATUMUITSPRAAK)
            .datumzitting(UPDATED_DATUMZITTING)
            .geldboete(UPDATED_GELDBOETE)
            .geldboetevoorwaardelijk(UPDATED_GELDBOETEVOORWAARDELIJK)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .proeftijd(UPDATED_PROEFTIJD)
            .reden(UPDATED_REDEN)
            .sanctiesoort(UPDATED_SANCTIESOORT)
            .uitspraak(UPDATED_UITSPRAAK)
            .verzuimsoort(UPDATED_VERZUIMSOORT);

        restProcesverbaalonderwijsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProcesverbaalonderwijs.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProcesverbaalonderwijs))
            )
            .andExpect(status().isOk());

        // Validate the Procesverbaalonderwijs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProcesverbaalonderwijsToMatchAllProperties(updatedProcesverbaalonderwijs);
    }

    @Test
    @Transactional
    void putNonExistingProcesverbaalonderwijs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        procesverbaalonderwijs.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcesverbaalonderwijsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, procesverbaalonderwijs.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(procesverbaalonderwijs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procesverbaalonderwijs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProcesverbaalonderwijs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        procesverbaalonderwijs.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcesverbaalonderwijsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(procesverbaalonderwijs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procesverbaalonderwijs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProcesverbaalonderwijs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        procesverbaalonderwijs.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcesverbaalonderwijsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(procesverbaalonderwijs)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Procesverbaalonderwijs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProcesverbaalonderwijsWithPatch() throws Exception {
        // Initialize the database
        procesverbaalonderwijsRepository.saveAndFlush(procesverbaalonderwijs);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the procesverbaalonderwijs using partial update
        Procesverbaalonderwijs partialUpdatedProcesverbaalonderwijs = new Procesverbaalonderwijs();
        partialUpdatedProcesverbaalonderwijs.setId(procesverbaalonderwijs.getId());

        partialUpdatedProcesverbaalonderwijs
            .datumafgehandeld(UPDATED_DATUMAFGEHANDELD)
            .datumingelicht(UPDATED_DATUMINGELICHT)
            .datumzitting(UPDATED_DATUMZITTING)
            .geldboetevoorwaardelijk(UPDATED_GELDBOETEVOORWAARDELIJK)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .proeftijd(UPDATED_PROEFTIJD)
            .sanctiesoort(UPDATED_SANCTIESOORT);

        restProcesverbaalonderwijsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcesverbaalonderwijs.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProcesverbaalonderwijs))
            )
            .andExpect(status().isOk());

        // Validate the Procesverbaalonderwijs in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProcesverbaalonderwijsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProcesverbaalonderwijs, procesverbaalonderwijs),
            getPersistedProcesverbaalonderwijs(procesverbaalonderwijs)
        );
    }

    @Test
    @Transactional
    void fullUpdateProcesverbaalonderwijsWithPatch() throws Exception {
        // Initialize the database
        procesverbaalonderwijsRepository.saveAndFlush(procesverbaalonderwijs);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the procesverbaalonderwijs using partial update
        Procesverbaalonderwijs partialUpdatedProcesverbaalonderwijs = new Procesverbaalonderwijs();
        partialUpdatedProcesverbaalonderwijs.setId(procesverbaalonderwijs.getId());

        partialUpdatedProcesverbaalonderwijs
            .datumafgehandeld(UPDATED_DATUMAFGEHANDELD)
            .datumeindeproeftijd(UPDATED_DATUMEINDEPROEFTIJD)
            .datumingelicht(UPDATED_DATUMINGELICHT)
            .datumuitspraak(UPDATED_DATUMUITSPRAAK)
            .datumzitting(UPDATED_DATUMZITTING)
            .geldboete(UPDATED_GELDBOETE)
            .geldboetevoorwaardelijk(UPDATED_GELDBOETEVOORWAARDELIJK)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .proeftijd(UPDATED_PROEFTIJD)
            .reden(UPDATED_REDEN)
            .sanctiesoort(UPDATED_SANCTIESOORT)
            .uitspraak(UPDATED_UITSPRAAK)
            .verzuimsoort(UPDATED_VERZUIMSOORT);

        restProcesverbaalonderwijsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcesverbaalonderwijs.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProcesverbaalonderwijs))
            )
            .andExpect(status().isOk());

        // Validate the Procesverbaalonderwijs in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProcesverbaalonderwijsUpdatableFieldsEquals(
            partialUpdatedProcesverbaalonderwijs,
            getPersistedProcesverbaalonderwijs(partialUpdatedProcesverbaalonderwijs)
        );
    }

    @Test
    @Transactional
    void patchNonExistingProcesverbaalonderwijs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        procesverbaalonderwijs.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcesverbaalonderwijsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, procesverbaalonderwijs.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(procesverbaalonderwijs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procesverbaalonderwijs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProcesverbaalonderwijs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        procesverbaalonderwijs.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcesverbaalonderwijsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(procesverbaalonderwijs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procesverbaalonderwijs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProcesverbaalonderwijs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        procesverbaalonderwijs.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcesverbaalonderwijsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(procesverbaalonderwijs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Procesverbaalonderwijs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProcesverbaalonderwijs() throws Exception {
        // Initialize the database
        procesverbaalonderwijsRepository.saveAndFlush(procesverbaalonderwijs);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the procesverbaalonderwijs
        restProcesverbaalonderwijsMockMvc
            .perform(delete(ENTITY_API_URL_ID, procesverbaalonderwijs.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return procesverbaalonderwijsRepository.count();
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

    protected Procesverbaalonderwijs getPersistedProcesverbaalonderwijs(Procesverbaalonderwijs procesverbaalonderwijs) {
        return procesverbaalonderwijsRepository.findById(procesverbaalonderwijs.getId()).orElseThrow();
    }

    protected void assertPersistedProcesverbaalonderwijsToMatchAllProperties(Procesverbaalonderwijs expectedProcesverbaalonderwijs) {
        assertProcesverbaalonderwijsAllPropertiesEquals(
            expectedProcesverbaalonderwijs,
            getPersistedProcesverbaalonderwijs(expectedProcesverbaalonderwijs)
        );
    }

    protected void assertPersistedProcesverbaalonderwijsToMatchUpdatableProperties(Procesverbaalonderwijs expectedProcesverbaalonderwijs) {
        assertProcesverbaalonderwijsAllUpdatablePropertiesEquals(
            expectedProcesverbaalonderwijs,
            getPersistedProcesverbaalonderwijs(expectedProcesverbaalonderwijs)
        );
    }
}
