package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FormulierinhuurAsserts.*;
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
import nl.ritense.demo.domain.Formulierinhuur;
import nl.ritense.demo.repository.FormulierinhuurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FormulierinhuurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormulierinhuurResourceIT {

    private static final String DEFAULT_AKKOORDFINANCIEELADVISEUR = "AAAAAAAAAA";
    private static final String UPDATED_AKKOORDFINANCIEELADVISEUR = "BBBBBBBBBB";

    private static final String DEFAULT_AKKOORDHRADVISEUR = "AAAAAAAAAA";
    private static final String UPDATED_AKKOORDHRADVISEUR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMINGANGINHUUR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINGANGINHUUR = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FUNCTIENAAMINHUUR = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTIENAAMINHUUR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/formulierinhuurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FormulierinhuurRepository formulierinhuurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormulierinhuurMockMvc;

    private Formulierinhuur formulierinhuur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formulierinhuur createEntity(EntityManager em) {
        Formulierinhuur formulierinhuur = new Formulierinhuur()
            .akkoordfinancieeladviseur(DEFAULT_AKKOORDFINANCIEELADVISEUR)
            .akkoordhradviseur(DEFAULT_AKKOORDHRADVISEUR)
            .datuminganginhuur(DEFAULT_DATUMINGANGINHUUR)
            .functienaaminhuur(DEFAULT_FUNCTIENAAMINHUUR);
        return formulierinhuur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formulierinhuur createUpdatedEntity(EntityManager em) {
        Formulierinhuur formulierinhuur = new Formulierinhuur()
            .akkoordfinancieeladviseur(UPDATED_AKKOORDFINANCIEELADVISEUR)
            .akkoordhradviseur(UPDATED_AKKOORDHRADVISEUR)
            .datuminganginhuur(UPDATED_DATUMINGANGINHUUR)
            .functienaaminhuur(UPDATED_FUNCTIENAAMINHUUR);
        return formulierinhuur;
    }

    @BeforeEach
    public void initTest() {
        formulierinhuur = createEntity(em);
    }

    @Test
    @Transactional
    void createFormulierinhuur() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Formulierinhuur
        var returnedFormulierinhuur = om.readValue(
            restFormulierinhuurMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formulierinhuur)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Formulierinhuur.class
        );

        // Validate the Formulierinhuur in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFormulierinhuurUpdatableFieldsEquals(returnedFormulierinhuur, getPersistedFormulierinhuur(returnedFormulierinhuur));
    }

    @Test
    @Transactional
    void createFormulierinhuurWithExistingId() throws Exception {
        // Create the Formulierinhuur with an existing ID
        formulierinhuur.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormulierinhuurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formulierinhuur)))
            .andExpect(status().isBadRequest());

        // Validate the Formulierinhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormulierinhuurs() throws Exception {
        // Initialize the database
        formulierinhuurRepository.saveAndFlush(formulierinhuur);

        // Get all the formulierinhuurList
        restFormulierinhuurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formulierinhuur.getId().intValue())))
            .andExpect(jsonPath("$.[*].akkoordfinancieeladviseur").value(hasItem(DEFAULT_AKKOORDFINANCIEELADVISEUR)))
            .andExpect(jsonPath("$.[*].akkoordhradviseur").value(hasItem(DEFAULT_AKKOORDHRADVISEUR)))
            .andExpect(jsonPath("$.[*].datuminganginhuur").value(hasItem(DEFAULT_DATUMINGANGINHUUR.toString())))
            .andExpect(jsonPath("$.[*].functienaaminhuur").value(hasItem(DEFAULT_FUNCTIENAAMINHUUR)));
    }

    @Test
    @Transactional
    void getFormulierinhuur() throws Exception {
        // Initialize the database
        formulierinhuurRepository.saveAndFlush(formulierinhuur);

        // Get the formulierinhuur
        restFormulierinhuurMockMvc
            .perform(get(ENTITY_API_URL_ID, formulierinhuur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formulierinhuur.getId().intValue()))
            .andExpect(jsonPath("$.akkoordfinancieeladviseur").value(DEFAULT_AKKOORDFINANCIEELADVISEUR))
            .andExpect(jsonPath("$.akkoordhradviseur").value(DEFAULT_AKKOORDHRADVISEUR))
            .andExpect(jsonPath("$.datuminganginhuur").value(DEFAULT_DATUMINGANGINHUUR.toString()))
            .andExpect(jsonPath("$.functienaaminhuur").value(DEFAULT_FUNCTIENAAMINHUUR));
    }

    @Test
    @Transactional
    void getNonExistingFormulierinhuur() throws Exception {
        // Get the formulierinhuur
        restFormulierinhuurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFormulierinhuur() throws Exception {
        // Initialize the database
        formulierinhuurRepository.saveAndFlush(formulierinhuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formulierinhuur
        Formulierinhuur updatedFormulierinhuur = formulierinhuurRepository.findById(formulierinhuur.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFormulierinhuur are not directly saved in db
        em.detach(updatedFormulierinhuur);
        updatedFormulierinhuur
            .akkoordfinancieeladviseur(UPDATED_AKKOORDFINANCIEELADVISEUR)
            .akkoordhradviseur(UPDATED_AKKOORDHRADVISEUR)
            .datuminganginhuur(UPDATED_DATUMINGANGINHUUR)
            .functienaaminhuur(UPDATED_FUNCTIENAAMINHUUR);

        restFormulierinhuurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormulierinhuur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFormulierinhuur))
            )
            .andExpect(status().isOk());

        // Validate the Formulierinhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFormulierinhuurToMatchAllProperties(updatedFormulierinhuur);
    }

    @Test
    @Transactional
    void putNonExistingFormulierinhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formulierinhuur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormulierinhuurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formulierinhuur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formulierinhuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formulierinhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormulierinhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formulierinhuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormulierinhuurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formulierinhuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formulierinhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormulierinhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formulierinhuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormulierinhuurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formulierinhuur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formulierinhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormulierinhuurWithPatch() throws Exception {
        // Initialize the database
        formulierinhuurRepository.saveAndFlush(formulierinhuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formulierinhuur using partial update
        Formulierinhuur partialUpdatedFormulierinhuur = new Formulierinhuur();
        partialUpdatedFormulierinhuur.setId(formulierinhuur.getId());

        restFormulierinhuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormulierinhuur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormulierinhuur))
            )
            .andExpect(status().isOk());

        // Validate the Formulierinhuur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormulierinhuurUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFormulierinhuur, formulierinhuur),
            getPersistedFormulierinhuur(formulierinhuur)
        );
    }

    @Test
    @Transactional
    void fullUpdateFormulierinhuurWithPatch() throws Exception {
        // Initialize the database
        formulierinhuurRepository.saveAndFlush(formulierinhuur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formulierinhuur using partial update
        Formulierinhuur partialUpdatedFormulierinhuur = new Formulierinhuur();
        partialUpdatedFormulierinhuur.setId(formulierinhuur.getId());

        partialUpdatedFormulierinhuur
            .akkoordfinancieeladviseur(UPDATED_AKKOORDFINANCIEELADVISEUR)
            .akkoordhradviseur(UPDATED_AKKOORDHRADVISEUR)
            .datuminganginhuur(UPDATED_DATUMINGANGINHUUR)
            .functienaaminhuur(UPDATED_FUNCTIENAAMINHUUR);

        restFormulierinhuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormulierinhuur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormulierinhuur))
            )
            .andExpect(status().isOk());

        // Validate the Formulierinhuur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormulierinhuurUpdatableFieldsEquals(
            partialUpdatedFormulierinhuur,
            getPersistedFormulierinhuur(partialUpdatedFormulierinhuur)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFormulierinhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formulierinhuur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormulierinhuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formulierinhuur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formulierinhuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formulierinhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormulierinhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formulierinhuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormulierinhuurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formulierinhuur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formulierinhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormulierinhuur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formulierinhuur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormulierinhuurMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(formulierinhuur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formulierinhuur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormulierinhuur() throws Exception {
        // Initialize the database
        formulierinhuurRepository.saveAndFlush(formulierinhuur);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the formulierinhuur
        restFormulierinhuurMockMvc
            .perform(delete(ENTITY_API_URL_ID, formulierinhuur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return formulierinhuurRepository.count();
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

    protected Formulierinhuur getPersistedFormulierinhuur(Formulierinhuur formulierinhuur) {
        return formulierinhuurRepository.findById(formulierinhuur.getId()).orElseThrow();
    }

    protected void assertPersistedFormulierinhuurToMatchAllProperties(Formulierinhuur expectedFormulierinhuur) {
        assertFormulierinhuurAllPropertiesEquals(expectedFormulierinhuur, getPersistedFormulierinhuur(expectedFormulierinhuur));
    }

    protected void assertPersistedFormulierinhuurToMatchUpdatableProperties(Formulierinhuur expectedFormulierinhuur) {
        assertFormulierinhuurAllUpdatablePropertiesEquals(expectedFormulierinhuur, getPersistedFormulierinhuur(expectedFormulierinhuur));
    }
}
