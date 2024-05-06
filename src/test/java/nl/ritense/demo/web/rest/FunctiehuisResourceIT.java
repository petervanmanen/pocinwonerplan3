package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FunctiehuisAsserts.*;
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
import nl.ritense.demo.domain.Functiehuis;
import nl.ritense.demo.repository.FunctiehuisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FunctiehuisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FunctiehuisResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/functiehuis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FunctiehuisRepository functiehuisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFunctiehuisMockMvc;

    private Functiehuis functiehuis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Functiehuis createEntity(EntityManager em) {
        Functiehuis functiehuis = new Functiehuis().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return functiehuis;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Functiehuis createUpdatedEntity(EntityManager em) {
        Functiehuis functiehuis = new Functiehuis().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return functiehuis;
    }

    @BeforeEach
    public void initTest() {
        functiehuis = createEntity(em);
    }

    @Test
    @Transactional
    void createFunctiehuis() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Functiehuis
        var returnedFunctiehuis = om.readValue(
            restFunctiehuisMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(functiehuis)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Functiehuis.class
        );

        // Validate the Functiehuis in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFunctiehuisUpdatableFieldsEquals(returnedFunctiehuis, getPersistedFunctiehuis(returnedFunctiehuis));
    }

    @Test
    @Transactional
    void createFunctiehuisWithExistingId() throws Exception {
        // Create the Functiehuis with an existing ID
        functiehuis.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFunctiehuisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(functiehuis)))
            .andExpect(status().isBadRequest());

        // Validate the Functiehuis in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFunctiehuis() throws Exception {
        // Initialize the database
        functiehuisRepository.saveAndFlush(functiehuis);

        // Get all the functiehuisList
        restFunctiehuisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(functiehuis.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getFunctiehuis() throws Exception {
        // Initialize the database
        functiehuisRepository.saveAndFlush(functiehuis);

        // Get the functiehuis
        restFunctiehuisMockMvc
            .perform(get(ENTITY_API_URL_ID, functiehuis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(functiehuis.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingFunctiehuis() throws Exception {
        // Get the functiehuis
        restFunctiehuisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFunctiehuis() throws Exception {
        // Initialize the database
        functiehuisRepository.saveAndFlush(functiehuis);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the functiehuis
        Functiehuis updatedFunctiehuis = functiehuisRepository.findById(functiehuis.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFunctiehuis are not directly saved in db
        em.detach(updatedFunctiehuis);
        updatedFunctiehuis.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restFunctiehuisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFunctiehuis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFunctiehuis))
            )
            .andExpect(status().isOk());

        // Validate the Functiehuis in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFunctiehuisToMatchAllProperties(updatedFunctiehuis);
    }

    @Test
    @Transactional
    void putNonExistingFunctiehuis() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functiehuis.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFunctiehuisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, functiehuis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(functiehuis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functiehuis in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFunctiehuis() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functiehuis.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctiehuisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(functiehuis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functiehuis in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFunctiehuis() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functiehuis.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctiehuisMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(functiehuis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Functiehuis in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFunctiehuisWithPatch() throws Exception {
        // Initialize the database
        functiehuisRepository.saveAndFlush(functiehuis);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the functiehuis using partial update
        Functiehuis partialUpdatedFunctiehuis = new Functiehuis();
        partialUpdatedFunctiehuis.setId(functiehuis.getId());

        restFunctiehuisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFunctiehuis.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFunctiehuis))
            )
            .andExpect(status().isOk());

        // Validate the Functiehuis in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFunctiehuisUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFunctiehuis, functiehuis),
            getPersistedFunctiehuis(functiehuis)
        );
    }

    @Test
    @Transactional
    void fullUpdateFunctiehuisWithPatch() throws Exception {
        // Initialize the database
        functiehuisRepository.saveAndFlush(functiehuis);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the functiehuis using partial update
        Functiehuis partialUpdatedFunctiehuis = new Functiehuis();
        partialUpdatedFunctiehuis.setId(functiehuis.getId());

        partialUpdatedFunctiehuis.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restFunctiehuisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFunctiehuis.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFunctiehuis))
            )
            .andExpect(status().isOk());

        // Validate the Functiehuis in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFunctiehuisUpdatableFieldsEquals(partialUpdatedFunctiehuis, getPersistedFunctiehuis(partialUpdatedFunctiehuis));
    }

    @Test
    @Transactional
    void patchNonExistingFunctiehuis() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functiehuis.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFunctiehuisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, functiehuis.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(functiehuis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functiehuis in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFunctiehuis() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functiehuis.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctiehuisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(functiehuis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functiehuis in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFunctiehuis() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functiehuis.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctiehuisMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(functiehuis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Functiehuis in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFunctiehuis() throws Exception {
        // Initialize the database
        functiehuisRepository.saveAndFlush(functiehuis);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the functiehuis
        restFunctiehuisMockMvc
            .perform(delete(ENTITY_API_URL_ID, functiehuis.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return functiehuisRepository.count();
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

    protected Functiehuis getPersistedFunctiehuis(Functiehuis functiehuis) {
        return functiehuisRepository.findById(functiehuis.getId()).orElseThrow();
    }

    protected void assertPersistedFunctiehuisToMatchAllProperties(Functiehuis expectedFunctiehuis) {
        assertFunctiehuisAllPropertiesEquals(expectedFunctiehuis, getPersistedFunctiehuis(expectedFunctiehuis));
    }

    protected void assertPersistedFunctiehuisToMatchUpdatableProperties(Functiehuis expectedFunctiehuis) {
        assertFunctiehuisAllUpdatablePropertiesEquals(expectedFunctiehuis, getPersistedFunctiehuis(expectedFunctiehuis));
    }
}
