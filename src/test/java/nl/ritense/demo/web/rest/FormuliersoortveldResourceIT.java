package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FormuliersoortveldAsserts.*;
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
import nl.ritense.demo.domain.Formuliersoortveld;
import nl.ritense.demo.repository.FormuliersoortveldRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FormuliersoortveldResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormuliersoortveldResourceIT {

    private static final String DEFAULT_HELPTEKST = "AAAAAAAAAA";
    private static final String UPDATED_HELPTEKST = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISVERPLICHT = false;
    private static final Boolean UPDATED_ISVERPLICHT = true;

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_MAXLENGTE = "AAAAAAAAAA";
    private static final String UPDATED_MAXLENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_VELDNAAM = "AAAAAAAAAA";
    private static final String UPDATED_VELDNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_VELDTYPE = "AAAAAAAAAA";
    private static final String UPDATED_VELDTYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/formuliersoortvelds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FormuliersoortveldRepository formuliersoortveldRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormuliersoortveldMockMvc;

    private Formuliersoortveld formuliersoortveld;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formuliersoortveld createEntity(EntityManager em) {
        Formuliersoortveld formuliersoortveld = new Formuliersoortveld()
            .helptekst(DEFAULT_HELPTEKST)
            .isverplicht(DEFAULT_ISVERPLICHT)
            .label(DEFAULT_LABEL)
            .maxlengte(DEFAULT_MAXLENGTE)
            .veldnaam(DEFAULT_VELDNAAM)
            .veldtype(DEFAULT_VELDTYPE);
        return formuliersoortveld;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formuliersoortveld createUpdatedEntity(EntityManager em) {
        Formuliersoortveld formuliersoortveld = new Formuliersoortveld()
            .helptekst(UPDATED_HELPTEKST)
            .isverplicht(UPDATED_ISVERPLICHT)
            .label(UPDATED_LABEL)
            .maxlengte(UPDATED_MAXLENGTE)
            .veldnaam(UPDATED_VELDNAAM)
            .veldtype(UPDATED_VELDTYPE);
        return formuliersoortveld;
    }

    @BeforeEach
    public void initTest() {
        formuliersoortveld = createEntity(em);
    }

    @Test
    @Transactional
    void createFormuliersoortveld() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Formuliersoortveld
        var returnedFormuliersoortveld = om.readValue(
            restFormuliersoortveldMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formuliersoortveld)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Formuliersoortveld.class
        );

        // Validate the Formuliersoortveld in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFormuliersoortveldUpdatableFieldsEquals(
            returnedFormuliersoortveld,
            getPersistedFormuliersoortveld(returnedFormuliersoortveld)
        );
    }

    @Test
    @Transactional
    void createFormuliersoortveldWithExistingId() throws Exception {
        // Create the Formuliersoortveld with an existing ID
        formuliersoortveld.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormuliersoortveldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formuliersoortveld)))
            .andExpect(status().isBadRequest());

        // Validate the Formuliersoortveld in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormuliersoortvelds() throws Exception {
        // Initialize the database
        formuliersoortveldRepository.saveAndFlush(formuliersoortveld);

        // Get all the formuliersoortveldList
        restFormuliersoortveldMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formuliersoortveld.getId().intValue())))
            .andExpect(jsonPath("$.[*].helptekst").value(hasItem(DEFAULT_HELPTEKST)))
            .andExpect(jsonPath("$.[*].isverplicht").value(hasItem(DEFAULT_ISVERPLICHT.booleanValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].maxlengte").value(hasItem(DEFAULT_MAXLENGTE)))
            .andExpect(jsonPath("$.[*].veldnaam").value(hasItem(DEFAULT_VELDNAAM)))
            .andExpect(jsonPath("$.[*].veldtype").value(hasItem(DEFAULT_VELDTYPE)));
    }

    @Test
    @Transactional
    void getFormuliersoortveld() throws Exception {
        // Initialize the database
        formuliersoortveldRepository.saveAndFlush(formuliersoortveld);

        // Get the formuliersoortveld
        restFormuliersoortveldMockMvc
            .perform(get(ENTITY_API_URL_ID, formuliersoortveld.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formuliersoortveld.getId().intValue()))
            .andExpect(jsonPath("$.helptekst").value(DEFAULT_HELPTEKST))
            .andExpect(jsonPath("$.isverplicht").value(DEFAULT_ISVERPLICHT.booleanValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.maxlengte").value(DEFAULT_MAXLENGTE))
            .andExpect(jsonPath("$.veldnaam").value(DEFAULT_VELDNAAM))
            .andExpect(jsonPath("$.veldtype").value(DEFAULT_VELDTYPE));
    }

    @Test
    @Transactional
    void getNonExistingFormuliersoortveld() throws Exception {
        // Get the formuliersoortveld
        restFormuliersoortveldMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFormuliersoortveld() throws Exception {
        // Initialize the database
        formuliersoortveldRepository.saveAndFlush(formuliersoortveld);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formuliersoortveld
        Formuliersoortveld updatedFormuliersoortveld = formuliersoortveldRepository.findById(formuliersoortveld.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFormuliersoortveld are not directly saved in db
        em.detach(updatedFormuliersoortveld);
        updatedFormuliersoortveld
            .helptekst(UPDATED_HELPTEKST)
            .isverplicht(UPDATED_ISVERPLICHT)
            .label(UPDATED_LABEL)
            .maxlengte(UPDATED_MAXLENGTE)
            .veldnaam(UPDATED_VELDNAAM)
            .veldtype(UPDATED_VELDTYPE);

        restFormuliersoortveldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormuliersoortveld.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFormuliersoortveld))
            )
            .andExpect(status().isOk());

        // Validate the Formuliersoortveld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFormuliersoortveldToMatchAllProperties(updatedFormuliersoortveld);
    }

    @Test
    @Transactional
    void putNonExistingFormuliersoortveld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formuliersoortveld.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormuliersoortveldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formuliersoortveld.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formuliersoortveld))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formuliersoortveld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormuliersoortveld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formuliersoortveld.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormuliersoortveldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formuliersoortveld))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formuliersoortveld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormuliersoortveld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formuliersoortveld.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormuliersoortveldMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formuliersoortveld)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formuliersoortveld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormuliersoortveldWithPatch() throws Exception {
        // Initialize the database
        formuliersoortveldRepository.saveAndFlush(formuliersoortveld);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formuliersoortveld using partial update
        Formuliersoortveld partialUpdatedFormuliersoortveld = new Formuliersoortveld();
        partialUpdatedFormuliersoortveld.setId(formuliersoortveld.getId());

        partialUpdatedFormuliersoortveld.helptekst(UPDATED_HELPTEKST).isverplicht(UPDATED_ISVERPLICHT).label(UPDATED_LABEL);

        restFormuliersoortveldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormuliersoortveld.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormuliersoortveld))
            )
            .andExpect(status().isOk());

        // Validate the Formuliersoortveld in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormuliersoortveldUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFormuliersoortveld, formuliersoortveld),
            getPersistedFormuliersoortveld(formuliersoortveld)
        );
    }

    @Test
    @Transactional
    void fullUpdateFormuliersoortveldWithPatch() throws Exception {
        // Initialize the database
        formuliersoortveldRepository.saveAndFlush(formuliersoortveld);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formuliersoortveld using partial update
        Formuliersoortveld partialUpdatedFormuliersoortveld = new Formuliersoortveld();
        partialUpdatedFormuliersoortveld.setId(formuliersoortveld.getId());

        partialUpdatedFormuliersoortveld
            .helptekst(UPDATED_HELPTEKST)
            .isverplicht(UPDATED_ISVERPLICHT)
            .label(UPDATED_LABEL)
            .maxlengte(UPDATED_MAXLENGTE)
            .veldnaam(UPDATED_VELDNAAM)
            .veldtype(UPDATED_VELDTYPE);

        restFormuliersoortveldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormuliersoortveld.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormuliersoortveld))
            )
            .andExpect(status().isOk());

        // Validate the Formuliersoortveld in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormuliersoortveldUpdatableFieldsEquals(
            partialUpdatedFormuliersoortveld,
            getPersistedFormuliersoortveld(partialUpdatedFormuliersoortveld)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFormuliersoortveld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formuliersoortveld.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormuliersoortveldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formuliersoortveld.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formuliersoortveld))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formuliersoortveld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormuliersoortveld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formuliersoortveld.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormuliersoortveldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formuliersoortveld))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formuliersoortveld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormuliersoortveld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formuliersoortveld.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormuliersoortveldMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(formuliersoortveld)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formuliersoortveld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormuliersoortveld() throws Exception {
        // Initialize the database
        formuliersoortveldRepository.saveAndFlush(formuliersoortveld);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the formuliersoortveld
        restFormuliersoortveldMockMvc
            .perform(delete(ENTITY_API_URL_ID, formuliersoortveld.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return formuliersoortveldRepository.count();
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

    protected Formuliersoortveld getPersistedFormuliersoortveld(Formuliersoortveld formuliersoortveld) {
        return formuliersoortveldRepository.findById(formuliersoortveld.getId()).orElseThrow();
    }

    protected void assertPersistedFormuliersoortveldToMatchAllProperties(Formuliersoortveld expectedFormuliersoortveld) {
        assertFormuliersoortveldAllPropertiesEquals(expectedFormuliersoortveld, getPersistedFormuliersoortveld(expectedFormuliersoortveld));
    }

    protected void assertPersistedFormuliersoortveldToMatchUpdatableProperties(Formuliersoortveld expectedFormuliersoortveld) {
        assertFormuliersoortveldAllUpdatablePropertiesEquals(
            expectedFormuliersoortveld,
            getPersistedFormuliersoortveld(expectedFormuliersoortveld)
        );
    }
}
