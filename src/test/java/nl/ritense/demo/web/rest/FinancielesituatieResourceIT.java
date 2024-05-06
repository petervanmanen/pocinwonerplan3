package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FinancielesituatieAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Financielesituatie;
import nl.ritense.demo.repository.FinancielesituatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FinancielesituatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FinancielesituatieResourceIT {

    private static final String DEFAULT_DATUMVASTGESTELD = "AAAAAAAAAA";
    private static final String UPDATED_DATUMVASTGESTELD = "BBBBBBBBBB";

    private static final String DEFAULT_SCHULD = "AAAAAAAAAA";
    private static final String UPDATED_SCHULD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/financielesituaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FinancielesituatieRepository financielesituatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFinancielesituatieMockMvc;

    private Financielesituatie financielesituatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Financielesituatie createEntity(EntityManager em) {
        Financielesituatie financielesituatie = new Financielesituatie().datumvastgesteld(DEFAULT_DATUMVASTGESTELD).schuld(DEFAULT_SCHULD);
        return financielesituatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Financielesituatie createUpdatedEntity(EntityManager em) {
        Financielesituatie financielesituatie = new Financielesituatie().datumvastgesteld(UPDATED_DATUMVASTGESTELD).schuld(UPDATED_SCHULD);
        return financielesituatie;
    }

    @BeforeEach
    public void initTest() {
        financielesituatie = createEntity(em);
    }

    @Test
    @Transactional
    void createFinancielesituatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Financielesituatie
        var returnedFinancielesituatie = om.readValue(
            restFinancielesituatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(financielesituatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Financielesituatie.class
        );

        // Validate the Financielesituatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFinancielesituatieUpdatableFieldsEquals(
            returnedFinancielesituatie,
            getPersistedFinancielesituatie(returnedFinancielesituatie)
        );
    }

    @Test
    @Transactional
    void createFinancielesituatieWithExistingId() throws Exception {
        // Create the Financielesituatie with an existing ID
        financielesituatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinancielesituatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(financielesituatie)))
            .andExpect(status().isBadRequest());

        // Validate the Financielesituatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFinancielesituaties() throws Exception {
        // Initialize the database
        financielesituatieRepository.saveAndFlush(financielesituatie);

        // Get all the financielesituatieList
        restFinancielesituatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financielesituatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumvastgesteld").value(hasItem(DEFAULT_DATUMVASTGESTELD)))
            .andExpect(jsonPath("$.[*].schuld").value(hasItem(DEFAULT_SCHULD)));
    }

    @Test
    @Transactional
    void getFinancielesituatie() throws Exception {
        // Initialize the database
        financielesituatieRepository.saveAndFlush(financielesituatie);

        // Get the financielesituatie
        restFinancielesituatieMockMvc
            .perform(get(ENTITY_API_URL_ID, financielesituatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(financielesituatie.getId().intValue()))
            .andExpect(jsonPath("$.datumvastgesteld").value(DEFAULT_DATUMVASTGESTELD))
            .andExpect(jsonPath("$.schuld").value(DEFAULT_SCHULD));
    }

    @Test
    @Transactional
    void getNonExistingFinancielesituatie() throws Exception {
        // Get the financielesituatie
        restFinancielesituatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFinancielesituatie() throws Exception {
        // Initialize the database
        financielesituatieRepository.saveAndFlush(financielesituatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the financielesituatie
        Financielesituatie updatedFinancielesituatie = financielesituatieRepository.findById(financielesituatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFinancielesituatie are not directly saved in db
        em.detach(updatedFinancielesituatie);
        updatedFinancielesituatie.datumvastgesteld(UPDATED_DATUMVASTGESTELD).schuld(UPDATED_SCHULD);

        restFinancielesituatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFinancielesituatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFinancielesituatie))
            )
            .andExpect(status().isOk());

        // Validate the Financielesituatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFinancielesituatieToMatchAllProperties(updatedFinancielesituatie);
    }

    @Test
    @Transactional
    void putNonExistingFinancielesituatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        financielesituatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinancielesituatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, financielesituatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(financielesituatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Financielesituatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFinancielesituatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        financielesituatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinancielesituatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(financielesituatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Financielesituatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFinancielesituatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        financielesituatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinancielesituatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(financielesituatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Financielesituatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFinancielesituatieWithPatch() throws Exception {
        // Initialize the database
        financielesituatieRepository.saveAndFlush(financielesituatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the financielesituatie using partial update
        Financielesituatie partialUpdatedFinancielesituatie = new Financielesituatie();
        partialUpdatedFinancielesituatie.setId(financielesituatie.getId());

        restFinancielesituatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFinancielesituatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFinancielesituatie))
            )
            .andExpect(status().isOk());

        // Validate the Financielesituatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFinancielesituatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFinancielesituatie, financielesituatie),
            getPersistedFinancielesituatie(financielesituatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateFinancielesituatieWithPatch() throws Exception {
        // Initialize the database
        financielesituatieRepository.saveAndFlush(financielesituatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the financielesituatie using partial update
        Financielesituatie partialUpdatedFinancielesituatie = new Financielesituatie();
        partialUpdatedFinancielesituatie.setId(financielesituatie.getId());

        partialUpdatedFinancielesituatie.datumvastgesteld(UPDATED_DATUMVASTGESTELD).schuld(UPDATED_SCHULD);

        restFinancielesituatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFinancielesituatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFinancielesituatie))
            )
            .andExpect(status().isOk());

        // Validate the Financielesituatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFinancielesituatieUpdatableFieldsEquals(
            partialUpdatedFinancielesituatie,
            getPersistedFinancielesituatie(partialUpdatedFinancielesituatie)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFinancielesituatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        financielesituatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinancielesituatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, financielesituatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(financielesituatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Financielesituatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFinancielesituatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        financielesituatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinancielesituatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(financielesituatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Financielesituatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFinancielesituatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        financielesituatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinancielesituatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(financielesituatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Financielesituatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFinancielesituatie() throws Exception {
        // Initialize the database
        financielesituatieRepository.saveAndFlush(financielesituatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the financielesituatie
        restFinancielesituatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, financielesituatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return financielesituatieRepository.count();
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

    protected Financielesituatie getPersistedFinancielesituatie(Financielesituatie financielesituatie) {
        return financielesituatieRepository.findById(financielesituatie.getId()).orElseThrow();
    }

    protected void assertPersistedFinancielesituatieToMatchAllProperties(Financielesituatie expectedFinancielesituatie) {
        assertFinancielesituatieAllPropertiesEquals(expectedFinancielesituatie, getPersistedFinancielesituatie(expectedFinancielesituatie));
    }

    protected void assertPersistedFinancielesituatieToMatchUpdatableProperties(Financielesituatie expectedFinancielesituatie) {
        assertFinancielesituatieAllUpdatablePropertiesEquals(
            expectedFinancielesituatie,
            getPersistedFinancielesituatie(expectedFinancielesituatie)
        );
    }
}
