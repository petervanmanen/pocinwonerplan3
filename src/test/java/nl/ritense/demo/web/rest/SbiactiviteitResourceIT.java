package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SbiactiviteitAsserts.*;
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
import nl.ritense.demo.domain.Sbiactiviteit;
import nl.ritense.demo.repository.SbiactiviteitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SbiactiviteitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SbiactiviteitResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDESBIACTIVITEIT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDESBIACTIVITEIT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINGANGSBIACTIVITEIT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINGANGSBIACTIVITEIT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HOOFDNIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_HOOFDNIVEAU = "BBBBBBBBBB";

    private static final String DEFAULT_HOOFDNIVEAUOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_HOOFDNIVEAUOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_NAAMACTIVITEIT = "AAAAAAAAAA";
    private static final String UPDATED_NAAMACTIVITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_SBICODE = "AAAAAAAAAA";
    private static final String UPDATED_SBICODE = "BBBBBBBBBB";

    private static final String DEFAULT_SBIGROEP = "AAAAAAAAAA";
    private static final String UPDATED_SBIGROEP = "BBBBBBBBBB";

    private static final String DEFAULT_SBIGROEPOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_SBIGROEPOMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sbiactiviteits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SbiactiviteitRepository sbiactiviteitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSbiactiviteitMockMvc;

    private Sbiactiviteit sbiactiviteit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sbiactiviteit createEntity(EntityManager em) {
        Sbiactiviteit sbiactiviteit = new Sbiactiviteit()
            .datumeindesbiactiviteit(DEFAULT_DATUMEINDESBIACTIVITEIT)
            .datumingangsbiactiviteit(DEFAULT_DATUMINGANGSBIACTIVITEIT)
            .hoofdniveau(DEFAULT_HOOFDNIVEAU)
            .hoofdniveauomschrijving(DEFAULT_HOOFDNIVEAUOMSCHRIJVING)
            .naamactiviteit(DEFAULT_NAAMACTIVITEIT)
            .sbicode(DEFAULT_SBICODE)
            .sbigroep(DEFAULT_SBIGROEP)
            .sbigroepomschrijving(DEFAULT_SBIGROEPOMSCHRIJVING);
        return sbiactiviteit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sbiactiviteit createUpdatedEntity(EntityManager em) {
        Sbiactiviteit sbiactiviteit = new Sbiactiviteit()
            .datumeindesbiactiviteit(UPDATED_DATUMEINDESBIACTIVITEIT)
            .datumingangsbiactiviteit(UPDATED_DATUMINGANGSBIACTIVITEIT)
            .hoofdniveau(UPDATED_HOOFDNIVEAU)
            .hoofdniveauomschrijving(UPDATED_HOOFDNIVEAUOMSCHRIJVING)
            .naamactiviteit(UPDATED_NAAMACTIVITEIT)
            .sbicode(UPDATED_SBICODE)
            .sbigroep(UPDATED_SBIGROEP)
            .sbigroepomschrijving(UPDATED_SBIGROEPOMSCHRIJVING);
        return sbiactiviteit;
    }

    @BeforeEach
    public void initTest() {
        sbiactiviteit = createEntity(em);
    }

    @Test
    @Transactional
    void createSbiactiviteit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sbiactiviteit
        var returnedSbiactiviteit = om.readValue(
            restSbiactiviteitMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sbiactiviteit)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Sbiactiviteit.class
        );

        // Validate the Sbiactiviteit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSbiactiviteitUpdatableFieldsEquals(returnedSbiactiviteit, getPersistedSbiactiviteit(returnedSbiactiviteit));
    }

    @Test
    @Transactional
    void createSbiactiviteitWithExistingId() throws Exception {
        // Create the Sbiactiviteit with an existing ID
        sbiactiviteit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSbiactiviteitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sbiactiviteit)))
            .andExpect(status().isBadRequest());

        // Validate the Sbiactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSbiactiviteits() throws Exception {
        // Initialize the database
        sbiactiviteitRepository.saveAndFlush(sbiactiviteit);

        // Get all the sbiactiviteitList
        restSbiactiviteitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sbiactiviteit.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeindesbiactiviteit").value(hasItem(DEFAULT_DATUMEINDESBIACTIVITEIT.toString())))
            .andExpect(jsonPath("$.[*].datumingangsbiactiviteit").value(hasItem(DEFAULT_DATUMINGANGSBIACTIVITEIT.toString())))
            .andExpect(jsonPath("$.[*].hoofdniveau").value(hasItem(DEFAULT_HOOFDNIVEAU)))
            .andExpect(jsonPath("$.[*].hoofdniveauomschrijving").value(hasItem(DEFAULT_HOOFDNIVEAUOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].naamactiviteit").value(hasItem(DEFAULT_NAAMACTIVITEIT)))
            .andExpect(jsonPath("$.[*].sbicode").value(hasItem(DEFAULT_SBICODE)))
            .andExpect(jsonPath("$.[*].sbigroep").value(hasItem(DEFAULT_SBIGROEP)))
            .andExpect(jsonPath("$.[*].sbigroepomschrijving").value(hasItem(DEFAULT_SBIGROEPOMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getSbiactiviteit() throws Exception {
        // Initialize the database
        sbiactiviteitRepository.saveAndFlush(sbiactiviteit);

        // Get the sbiactiviteit
        restSbiactiviteitMockMvc
            .perform(get(ENTITY_API_URL_ID, sbiactiviteit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sbiactiviteit.getId().intValue()))
            .andExpect(jsonPath("$.datumeindesbiactiviteit").value(DEFAULT_DATUMEINDESBIACTIVITEIT.toString()))
            .andExpect(jsonPath("$.datumingangsbiactiviteit").value(DEFAULT_DATUMINGANGSBIACTIVITEIT.toString()))
            .andExpect(jsonPath("$.hoofdniveau").value(DEFAULT_HOOFDNIVEAU))
            .andExpect(jsonPath("$.hoofdniveauomschrijving").value(DEFAULT_HOOFDNIVEAUOMSCHRIJVING))
            .andExpect(jsonPath("$.naamactiviteit").value(DEFAULT_NAAMACTIVITEIT))
            .andExpect(jsonPath("$.sbicode").value(DEFAULT_SBICODE))
            .andExpect(jsonPath("$.sbigroep").value(DEFAULT_SBIGROEP))
            .andExpect(jsonPath("$.sbigroepomschrijving").value(DEFAULT_SBIGROEPOMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingSbiactiviteit() throws Exception {
        // Get the sbiactiviteit
        restSbiactiviteitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSbiactiviteit() throws Exception {
        // Initialize the database
        sbiactiviteitRepository.saveAndFlush(sbiactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sbiactiviteit
        Sbiactiviteit updatedSbiactiviteit = sbiactiviteitRepository.findById(sbiactiviteit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSbiactiviteit are not directly saved in db
        em.detach(updatedSbiactiviteit);
        updatedSbiactiviteit
            .datumeindesbiactiviteit(UPDATED_DATUMEINDESBIACTIVITEIT)
            .datumingangsbiactiviteit(UPDATED_DATUMINGANGSBIACTIVITEIT)
            .hoofdniveau(UPDATED_HOOFDNIVEAU)
            .hoofdniveauomschrijving(UPDATED_HOOFDNIVEAUOMSCHRIJVING)
            .naamactiviteit(UPDATED_NAAMACTIVITEIT)
            .sbicode(UPDATED_SBICODE)
            .sbigroep(UPDATED_SBIGROEP)
            .sbigroepomschrijving(UPDATED_SBIGROEPOMSCHRIJVING);

        restSbiactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSbiactiviteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSbiactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Sbiactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSbiactiviteitToMatchAllProperties(updatedSbiactiviteit);
    }

    @Test
    @Transactional
    void putNonExistingSbiactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sbiactiviteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSbiactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sbiactiviteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sbiactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sbiactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSbiactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sbiactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSbiactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sbiactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sbiactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSbiactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sbiactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSbiactiviteitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sbiactiviteit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sbiactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSbiactiviteitWithPatch() throws Exception {
        // Initialize the database
        sbiactiviteitRepository.saveAndFlush(sbiactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sbiactiviteit using partial update
        Sbiactiviteit partialUpdatedSbiactiviteit = new Sbiactiviteit();
        partialUpdatedSbiactiviteit.setId(sbiactiviteit.getId());

        partialUpdatedSbiactiviteit
            .datumingangsbiactiviteit(UPDATED_DATUMINGANGSBIACTIVITEIT)
            .hoofdniveau(UPDATED_HOOFDNIVEAU)
            .hoofdniveauomschrijving(UPDATED_HOOFDNIVEAUOMSCHRIJVING)
            .sbicode(UPDATED_SBICODE);

        restSbiactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSbiactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSbiactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Sbiactiviteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSbiactiviteitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSbiactiviteit, sbiactiviteit),
            getPersistedSbiactiviteit(sbiactiviteit)
        );
    }

    @Test
    @Transactional
    void fullUpdateSbiactiviteitWithPatch() throws Exception {
        // Initialize the database
        sbiactiviteitRepository.saveAndFlush(sbiactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sbiactiviteit using partial update
        Sbiactiviteit partialUpdatedSbiactiviteit = new Sbiactiviteit();
        partialUpdatedSbiactiviteit.setId(sbiactiviteit.getId());

        partialUpdatedSbiactiviteit
            .datumeindesbiactiviteit(UPDATED_DATUMEINDESBIACTIVITEIT)
            .datumingangsbiactiviteit(UPDATED_DATUMINGANGSBIACTIVITEIT)
            .hoofdniveau(UPDATED_HOOFDNIVEAU)
            .hoofdniveauomschrijving(UPDATED_HOOFDNIVEAUOMSCHRIJVING)
            .naamactiviteit(UPDATED_NAAMACTIVITEIT)
            .sbicode(UPDATED_SBICODE)
            .sbigroep(UPDATED_SBIGROEP)
            .sbigroepomschrijving(UPDATED_SBIGROEPOMSCHRIJVING);

        restSbiactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSbiactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSbiactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Sbiactiviteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSbiactiviteitUpdatableFieldsEquals(partialUpdatedSbiactiviteit, getPersistedSbiactiviteit(partialUpdatedSbiactiviteit));
    }

    @Test
    @Transactional
    void patchNonExistingSbiactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sbiactiviteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSbiactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sbiactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sbiactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sbiactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSbiactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sbiactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSbiactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sbiactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sbiactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSbiactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sbiactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSbiactiviteitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sbiactiviteit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sbiactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSbiactiviteit() throws Exception {
        // Initialize the database
        sbiactiviteitRepository.saveAndFlush(sbiactiviteit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sbiactiviteit
        restSbiactiviteitMockMvc
            .perform(delete(ENTITY_API_URL_ID, sbiactiviteit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sbiactiviteitRepository.count();
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

    protected Sbiactiviteit getPersistedSbiactiviteit(Sbiactiviteit sbiactiviteit) {
        return sbiactiviteitRepository.findById(sbiactiviteit.getId()).orElseThrow();
    }

    protected void assertPersistedSbiactiviteitToMatchAllProperties(Sbiactiviteit expectedSbiactiviteit) {
        assertSbiactiviteitAllPropertiesEquals(expectedSbiactiviteit, getPersistedSbiactiviteit(expectedSbiactiviteit));
    }

    protected void assertPersistedSbiactiviteitToMatchUpdatableProperties(Sbiactiviteit expectedSbiactiviteit) {
        assertSbiactiviteitAllUpdatablePropertiesEquals(expectedSbiactiviteit, getPersistedSbiactiviteit(expectedSbiactiviteit));
    }
}
