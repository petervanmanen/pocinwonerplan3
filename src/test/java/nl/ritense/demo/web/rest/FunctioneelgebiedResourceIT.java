package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FunctioneelgebiedAsserts.*;
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
import nl.ritense.demo.domain.Functioneelgebied;
import nl.ritense.demo.repository.FunctioneelgebiedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FunctioneelgebiedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FunctioneelgebiedResourceIT {

    private static final String DEFAULT_FUNCTIONEELGEBIEDCODE = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTIONEELGEBIEDCODE = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCTIONEELGEBIEDNAAM = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTIONEELGEBIEDNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMTREK = "AAAAAAAAAA";
    private static final String UPDATED_OMTREK = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/functioneelgebieds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FunctioneelgebiedRepository functioneelgebiedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFunctioneelgebiedMockMvc;

    private Functioneelgebied functioneelgebied;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Functioneelgebied createEntity(EntityManager em) {
        Functioneelgebied functioneelgebied = new Functioneelgebied()
            .functioneelgebiedcode(DEFAULT_FUNCTIONEELGEBIEDCODE)
            .functioneelgebiednaam(DEFAULT_FUNCTIONEELGEBIEDNAAM)
            .omtrek(DEFAULT_OMTREK)
            .oppervlakte(DEFAULT_OPPERVLAKTE);
        return functioneelgebied;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Functioneelgebied createUpdatedEntity(EntityManager em) {
        Functioneelgebied functioneelgebied = new Functioneelgebied()
            .functioneelgebiedcode(UPDATED_FUNCTIONEELGEBIEDCODE)
            .functioneelgebiednaam(UPDATED_FUNCTIONEELGEBIEDNAAM)
            .omtrek(UPDATED_OMTREK)
            .oppervlakte(UPDATED_OPPERVLAKTE);
        return functioneelgebied;
    }

    @BeforeEach
    public void initTest() {
        functioneelgebied = createEntity(em);
    }

    @Test
    @Transactional
    void createFunctioneelgebied() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Functioneelgebied
        var returnedFunctioneelgebied = om.readValue(
            restFunctioneelgebiedMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(functioneelgebied)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Functioneelgebied.class
        );

        // Validate the Functioneelgebied in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFunctioneelgebiedUpdatableFieldsEquals(returnedFunctioneelgebied, getPersistedFunctioneelgebied(returnedFunctioneelgebied));
    }

    @Test
    @Transactional
    void createFunctioneelgebiedWithExistingId() throws Exception {
        // Create the Functioneelgebied with an existing ID
        functioneelgebied.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFunctioneelgebiedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(functioneelgebied)))
            .andExpect(status().isBadRequest());

        // Validate the Functioneelgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFunctioneelgebieds() throws Exception {
        // Initialize the database
        functioneelgebiedRepository.saveAndFlush(functioneelgebied);

        // Get all the functioneelgebiedList
        restFunctioneelgebiedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(functioneelgebied.getId().intValue())))
            .andExpect(jsonPath("$.[*].functioneelgebiedcode").value(hasItem(DEFAULT_FUNCTIONEELGEBIEDCODE)))
            .andExpect(jsonPath("$.[*].functioneelgebiednaam").value(hasItem(DEFAULT_FUNCTIONEELGEBIEDNAAM)))
            .andExpect(jsonPath("$.[*].omtrek").value(hasItem(DEFAULT_OMTREK)))
            .andExpect(jsonPath("$.[*].oppervlakte").value(hasItem(DEFAULT_OPPERVLAKTE)));
    }

    @Test
    @Transactional
    void getFunctioneelgebied() throws Exception {
        // Initialize the database
        functioneelgebiedRepository.saveAndFlush(functioneelgebied);

        // Get the functioneelgebied
        restFunctioneelgebiedMockMvc
            .perform(get(ENTITY_API_URL_ID, functioneelgebied.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(functioneelgebied.getId().intValue()))
            .andExpect(jsonPath("$.functioneelgebiedcode").value(DEFAULT_FUNCTIONEELGEBIEDCODE))
            .andExpect(jsonPath("$.functioneelgebiednaam").value(DEFAULT_FUNCTIONEELGEBIEDNAAM))
            .andExpect(jsonPath("$.omtrek").value(DEFAULT_OMTREK))
            .andExpect(jsonPath("$.oppervlakte").value(DEFAULT_OPPERVLAKTE));
    }

    @Test
    @Transactional
    void getNonExistingFunctioneelgebied() throws Exception {
        // Get the functioneelgebied
        restFunctioneelgebiedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFunctioneelgebied() throws Exception {
        // Initialize the database
        functioneelgebiedRepository.saveAndFlush(functioneelgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the functioneelgebied
        Functioneelgebied updatedFunctioneelgebied = functioneelgebiedRepository.findById(functioneelgebied.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFunctioneelgebied are not directly saved in db
        em.detach(updatedFunctioneelgebied);
        updatedFunctioneelgebied
            .functioneelgebiedcode(UPDATED_FUNCTIONEELGEBIEDCODE)
            .functioneelgebiednaam(UPDATED_FUNCTIONEELGEBIEDNAAM)
            .omtrek(UPDATED_OMTREK)
            .oppervlakte(UPDATED_OPPERVLAKTE);

        restFunctioneelgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFunctioneelgebied.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFunctioneelgebied))
            )
            .andExpect(status().isOk());

        // Validate the Functioneelgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFunctioneelgebiedToMatchAllProperties(updatedFunctioneelgebied);
    }

    @Test
    @Transactional
    void putNonExistingFunctioneelgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functioneelgebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFunctioneelgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, functioneelgebied.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(functioneelgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functioneelgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFunctioneelgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functioneelgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctioneelgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(functioneelgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functioneelgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFunctioneelgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functioneelgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctioneelgebiedMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(functioneelgebied)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Functioneelgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFunctioneelgebiedWithPatch() throws Exception {
        // Initialize the database
        functioneelgebiedRepository.saveAndFlush(functioneelgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the functioneelgebied using partial update
        Functioneelgebied partialUpdatedFunctioneelgebied = new Functioneelgebied();
        partialUpdatedFunctioneelgebied.setId(functioneelgebied.getId());

        partialUpdatedFunctioneelgebied.omtrek(UPDATED_OMTREK).oppervlakte(UPDATED_OPPERVLAKTE);

        restFunctioneelgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFunctioneelgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFunctioneelgebied))
            )
            .andExpect(status().isOk());

        // Validate the Functioneelgebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFunctioneelgebiedUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFunctioneelgebied, functioneelgebied),
            getPersistedFunctioneelgebied(functioneelgebied)
        );
    }

    @Test
    @Transactional
    void fullUpdateFunctioneelgebiedWithPatch() throws Exception {
        // Initialize the database
        functioneelgebiedRepository.saveAndFlush(functioneelgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the functioneelgebied using partial update
        Functioneelgebied partialUpdatedFunctioneelgebied = new Functioneelgebied();
        partialUpdatedFunctioneelgebied.setId(functioneelgebied.getId());

        partialUpdatedFunctioneelgebied
            .functioneelgebiedcode(UPDATED_FUNCTIONEELGEBIEDCODE)
            .functioneelgebiednaam(UPDATED_FUNCTIONEELGEBIEDNAAM)
            .omtrek(UPDATED_OMTREK)
            .oppervlakte(UPDATED_OPPERVLAKTE);

        restFunctioneelgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFunctioneelgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFunctioneelgebied))
            )
            .andExpect(status().isOk());

        // Validate the Functioneelgebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFunctioneelgebiedUpdatableFieldsEquals(
            partialUpdatedFunctioneelgebied,
            getPersistedFunctioneelgebied(partialUpdatedFunctioneelgebied)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFunctioneelgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functioneelgebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFunctioneelgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, functioneelgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(functioneelgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functioneelgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFunctioneelgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functioneelgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctioneelgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(functioneelgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functioneelgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFunctioneelgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        functioneelgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctioneelgebiedMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(functioneelgebied)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Functioneelgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFunctioneelgebied() throws Exception {
        // Initialize the database
        functioneelgebiedRepository.saveAndFlush(functioneelgebied);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the functioneelgebied
        restFunctioneelgebiedMockMvc
            .perform(delete(ENTITY_API_URL_ID, functioneelgebied.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return functioneelgebiedRepository.count();
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

    protected Functioneelgebied getPersistedFunctioneelgebied(Functioneelgebied functioneelgebied) {
        return functioneelgebiedRepository.findById(functioneelgebied.getId()).orElseThrow();
    }

    protected void assertPersistedFunctioneelgebiedToMatchAllProperties(Functioneelgebied expectedFunctioneelgebied) {
        assertFunctioneelgebiedAllPropertiesEquals(expectedFunctioneelgebied, getPersistedFunctioneelgebied(expectedFunctioneelgebied));
    }

    protected void assertPersistedFunctioneelgebiedToMatchUpdatableProperties(Functioneelgebied expectedFunctioneelgebied) {
        assertFunctioneelgebiedAllUpdatablePropertiesEquals(
            expectedFunctioneelgebied,
            getPersistedFunctioneelgebied(expectedFunctioneelgebied)
        );
    }
}
