package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FormulierverlenginginhuurAsserts.*;
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
import nl.ritense.demo.domain.Formulierverlenginginhuur;
import nl.ritense.demo.repository.FormulierverlenginginhuurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FormulierverlenginginhuurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormulierverlenginginhuurResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDENIEUW = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDENIEUW = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_INDICATIEREDENINHUURGEWIJZIGD = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEREDENINHUURGEWIJZIGD = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIEVERHOGENINKOOPORDER = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEVERHOGENINKOOPORDER = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/formulierverlenginginhuurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FormulierverlenginginhuurRepository formulierverlenginginhuurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormulierverlenginginhuurMockMvc;

    private Formulierverlenginginhuur formulierverlenginginhuur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formulierverlenginginhuur createEntity(EntityManager em) {
        Formulierverlenginginhuur formulierverlenginginhuur = new Formulierverlenginginhuur()
            .datumeindenieuw(DEFAULT_DATUMEINDENIEUW)
            .indicatieredeninhuurgewijzigd(DEFAULT_INDICATIEREDENINHUURGEWIJZIGD)
            .indicatieverhogeninkooporder(DEFAULT_INDICATIEVERHOGENINKOOPORDER)
            .toelichting(DEFAULT_TOELICHTING);
        return formulierverlenginginhuur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formulierverlenginginhuur createUpdatedEntity(EntityManager em) {
        Formulierverlenginginhuur formulierverlenginginhuur = new Formulierverlenginginhuur()
            .datumeindenieuw(UPDATED_DATUMEINDENIEUW)
            .indicatieredeninhuurgewijzigd(UPDATED_INDICATIEREDENINHUURGEWIJZIGD)
            .indicatieverhogeninkooporder(UPDATED_INDICATIEVERHOGENINKOOPORDER)
            .toelichting(UPDATED_TOELICHTING);
        return formulierverlenginginhuur;
    }

    @BeforeEach
    public void initTest() {
        formulierverlenginginhuur = createEntity(em);
    }

    @Test
    @Transactional
    void createFormulierverlenginginhuur() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Formulierverlenginginhuur
        var returnedFormulierverlenginginhuur = om.readValue(
            restFormulierverlenginginhuurMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formulierverlenginginhuur))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Formulierverlenginginhuur.class
        );

        // Validate the Formulierverlenginginhuur in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFormulierverlenginginhuurUpdatableFieldsEquals(
            returnedFormulierverlenginginhuur,
            getPersistedFormulierverlenginginhuur(returnedFormulierverlenginginhuur)
        );
    }

    @Test
    @Transactional
    void createFormulierverlenginginhuurWithExistingId() throws Exception {
        // Create the Formulierverlenginginhuur with an existing ID
        formulierverlenginginhuur.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormulierverlenginginhuurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formulierverlenginginhuur)))
            .andExpect(status().isBadRequest());

        // Validate the Formulierverlenginginhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormulierverlenginginhuurs() throws Exception {
        // Initialize the database
        formulierverlenginginhuurRepository.saveAndFlush(formulierverlenginginhuur);

        // Get all the formulierverlenginginhuurList
        restFormulierverlenginginhuurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formulierverlenginginhuur.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeindenieuw").value(hasItem(DEFAULT_DATUMEINDENIEUW.toString())))
            .andExpect(jsonPath("$.[*].indicatieredeninhuurgewijzigd").value(hasItem(DEFAULT_INDICATIEREDENINHUURGEWIJZIGD)))
            .andExpect(jsonPath("$.[*].indicatieverhogeninkooporder").value(hasItem(DEFAULT_INDICATIEVERHOGENINKOOPORDER)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)));
    }

    @Test
    @Transactional
    void getFormulierverlenginginhuur() throws Exception {
        // Initialize the database
        formulierverlenginginhuurRepository.saveAndFlush(formulierverlenginginhuur);

        // Get the formulierverlenginginhuur
        restFormulierverlenginginhuurMockMvc
            .perform(get(ENTITY_API_URL_ID, formulierverlenginginhuur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formulierverlenginginhuur.getId().intValue()))
            .andExpect(jsonPath("$.datumeindenieuw").value(DEFAULT_DATUMEINDENIEUW.toString()))
            .andExpect(jsonPath("$.indicatieredeninhuurgewijzigd").value(DEFAULT_INDICATIEREDENINHUURGEWIJZIGD))
            .andExpect(jsonPath("$.indicatieverhogeninkooporder").value(DEFAULT_INDICATIEVERHOGENINKOOPORDER))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING));
    }

    @Test
    @Transactional
    void getNonExistingFormulierverlenginginhuur() throws Exception {
        // Get the formulierverlenginginhuur
        restFormulierverlenginginhuurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFormulierverlenginginhuur() throws Exception {
        // Initialize the database
        formulierverlenginginhuurRepository.saveAndFlush(formulierverlenginginhuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formulierverlenginginhuur
        Formulierverlenginginhuur updatedFormulierverlenginginhuur = formulierverlenginginhuurRepository
            .findById(formulierverlenginginhuur.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFormulierverlenginginhuur are not directly saved in db
        em.detach(updatedFormulierverlenginginhuur);
        updatedFormulierverlenginginhuur
            .datumeindenieuw(UPDATED_DATUMEINDENIEUW)
            .indicatieredeninhuurgewijzigd(UPDATED_INDICATIEREDENINHUURGEWIJZIGD)
            .indicatieverhogeninkooporder(UPDATED_INDICATIEVERHOGENINKOOPORDER)
            .toelichting(UPDATED_TOELICHTING);

        restFormulierverlenginginhuurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormulierverlenginginhuur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFormulierverlenginginhuur))
            )
            .andExpect(status().isOk());

        // Validate the Formulierverlenginginhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFormulierverlenginginhuurToMatchAllProperties(updatedFormulierverlenginginhuur);
    }

    @Test
    @Transactional
    void putNonExistingFormulierverlenginginhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formulierverlenginginhuur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormulierverlenginginhuurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formulierverlenginginhuur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formulierverlenginginhuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formulierverlenginginhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormulierverlenginginhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formulierverlenginginhuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormulierverlenginginhuurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formulierverlenginginhuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formulierverlenginginhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormulierverlenginginhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formulierverlenginginhuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormulierverlenginginhuurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formulierverlenginginhuur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formulierverlenginginhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormulierverlenginginhuurWithPatch() throws Exception {
        // Initialize the database
        formulierverlenginginhuurRepository.saveAndFlush(formulierverlenginginhuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formulierverlenginginhuur using partial update
        Formulierverlenginginhuur partialUpdatedFormulierverlenginginhuur = new Formulierverlenginginhuur();
        partialUpdatedFormulierverlenginginhuur.setId(formulierverlenginginhuur.getId());

        partialUpdatedFormulierverlenginginhuur
            .datumeindenieuw(UPDATED_DATUMEINDENIEUW)
            .indicatieredeninhuurgewijzigd(UPDATED_INDICATIEREDENINHUURGEWIJZIGD)
            .toelichting(UPDATED_TOELICHTING);

        restFormulierverlenginginhuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormulierverlenginginhuur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormulierverlenginginhuur))
            )
            .andExpect(status().isOk());

        // Validate the Formulierverlenginginhuur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormulierverlenginginhuurUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFormulierverlenginginhuur, formulierverlenginginhuur),
            getPersistedFormulierverlenginginhuur(formulierverlenginginhuur)
        );
    }

    @Test
    @Transactional
    void fullUpdateFormulierverlenginginhuurWithPatch() throws Exception {
        // Initialize the database
        formulierverlenginginhuurRepository.saveAndFlush(formulierverlenginginhuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formulierverlenginginhuur using partial update
        Formulierverlenginginhuur partialUpdatedFormulierverlenginginhuur = new Formulierverlenginginhuur();
        partialUpdatedFormulierverlenginginhuur.setId(formulierverlenginginhuur.getId());

        partialUpdatedFormulierverlenginginhuur
            .datumeindenieuw(UPDATED_DATUMEINDENIEUW)
            .indicatieredeninhuurgewijzigd(UPDATED_INDICATIEREDENINHUURGEWIJZIGD)
            .indicatieverhogeninkooporder(UPDATED_INDICATIEVERHOGENINKOOPORDER)
            .toelichting(UPDATED_TOELICHTING);

        restFormulierverlenginginhuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormulierverlenginginhuur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormulierverlenginginhuur))
            )
            .andExpect(status().isOk());

        // Validate the Formulierverlenginginhuur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormulierverlenginginhuurUpdatableFieldsEquals(
            partialUpdatedFormulierverlenginginhuur,
            getPersistedFormulierverlenginginhuur(partialUpdatedFormulierverlenginginhuur)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFormulierverlenginginhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formulierverlenginginhuur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormulierverlenginginhuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formulierverlenginginhuur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formulierverlenginginhuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formulierverlenginginhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormulierverlenginginhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formulierverlenginginhuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormulierverlenginginhuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formulierverlenginginhuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formulierverlenginginhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormulierverlenginginhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formulierverlenginginhuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormulierverlenginginhuurMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(formulierverlenginginhuur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formulierverlenginginhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormulierverlenginginhuur() throws Exception {
        // Initialize the database
        formulierverlenginginhuurRepository.saveAndFlush(formulierverlenginginhuur);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the formulierverlenginginhuur
        restFormulierverlenginginhuurMockMvc
            .perform(delete(ENTITY_API_URL_ID, formulierverlenginginhuur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return formulierverlenginginhuurRepository.count();
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

    protected Formulierverlenginginhuur getPersistedFormulierverlenginginhuur(Formulierverlenginginhuur formulierverlenginginhuur) {
        return formulierverlenginginhuurRepository.findById(formulierverlenginginhuur.getId()).orElseThrow();
    }

    protected void assertPersistedFormulierverlenginginhuurToMatchAllProperties(
        Formulierverlenginginhuur expectedFormulierverlenginginhuur
    ) {
        assertFormulierverlenginginhuurAllPropertiesEquals(
            expectedFormulierverlenginginhuur,
            getPersistedFormulierverlenginginhuur(expectedFormulierverlenginginhuur)
        );
    }

    protected void assertPersistedFormulierverlenginginhuurToMatchUpdatableProperties(
        Formulierverlenginginhuur expectedFormulierverlenginginhuur
    ) {
        assertFormulierverlenginginhuurAllUpdatablePropertiesEquals(
            expectedFormulierverlenginginhuur,
            getPersistedFormulierverlenginginhuur(expectedFormulierverlenginginhuur)
        );
    }
}
