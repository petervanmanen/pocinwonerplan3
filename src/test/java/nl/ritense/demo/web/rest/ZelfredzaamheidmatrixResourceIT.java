package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ZelfredzaamheidmatrixAsserts.*;
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
import nl.ritense.demo.domain.Zelfredzaamheidmatrix;
import nl.ritense.demo.repository.ZelfredzaamheidmatrixRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ZelfredzaamheidmatrixResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ZelfredzaamheidmatrixResourceIT {

    private static final String DEFAULT_DATUMEINDEGELDIGHEID = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDEGELDIGHEID = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMSTARTGELDIGHEID = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSTARTGELDIGHEID = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/zelfredzaamheidmatrices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ZelfredzaamheidmatrixRepository zelfredzaamheidmatrixRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZelfredzaamheidmatrixMockMvc;

    private Zelfredzaamheidmatrix zelfredzaamheidmatrix;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zelfredzaamheidmatrix createEntity(EntityManager em) {
        Zelfredzaamheidmatrix zelfredzaamheidmatrix = new Zelfredzaamheidmatrix()
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .datumstartgeldigheid(DEFAULT_DATUMSTARTGELDIGHEID)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return zelfredzaamheidmatrix;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zelfredzaamheidmatrix createUpdatedEntity(EntityManager em) {
        Zelfredzaamheidmatrix zelfredzaamheidmatrix = new Zelfredzaamheidmatrix()
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumstartgeldigheid(UPDATED_DATUMSTARTGELDIGHEID)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return zelfredzaamheidmatrix;
    }

    @BeforeEach
    public void initTest() {
        zelfredzaamheidmatrix = createEntity(em);
    }

    @Test
    @Transactional
    void createZelfredzaamheidmatrix() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Zelfredzaamheidmatrix
        var returnedZelfredzaamheidmatrix = om.readValue(
            restZelfredzaamheidmatrixMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zelfredzaamheidmatrix)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Zelfredzaamheidmatrix.class
        );

        // Validate the Zelfredzaamheidmatrix in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertZelfredzaamheidmatrixUpdatableFieldsEquals(
            returnedZelfredzaamheidmatrix,
            getPersistedZelfredzaamheidmatrix(returnedZelfredzaamheidmatrix)
        );
    }

    @Test
    @Transactional
    void createZelfredzaamheidmatrixWithExistingId() throws Exception {
        // Create the Zelfredzaamheidmatrix with an existing ID
        zelfredzaamheidmatrix.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restZelfredzaamheidmatrixMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zelfredzaamheidmatrix)))
            .andExpect(status().isBadRequest());

        // Validate the Zelfredzaamheidmatrix in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllZelfredzaamheidmatrices() throws Exception {
        // Initialize the database
        zelfredzaamheidmatrixRepository.saveAndFlush(zelfredzaamheidmatrix);

        // Get all the zelfredzaamheidmatrixList
        restZelfredzaamheidmatrixMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zelfredzaamheidmatrix.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID)))
            .andExpect(jsonPath("$.[*].datumstartgeldigheid").value(hasItem(DEFAULT_DATUMSTARTGELDIGHEID)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getZelfredzaamheidmatrix() throws Exception {
        // Initialize the database
        zelfredzaamheidmatrixRepository.saveAndFlush(zelfredzaamheidmatrix);

        // Get the zelfredzaamheidmatrix
        restZelfredzaamheidmatrixMockMvc
            .perform(get(ENTITY_API_URL_ID, zelfredzaamheidmatrix.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(zelfredzaamheidmatrix.getId().intValue()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID))
            .andExpect(jsonPath("$.datumstartgeldigheid").value(DEFAULT_DATUMSTARTGELDIGHEID))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingZelfredzaamheidmatrix() throws Exception {
        // Get the zelfredzaamheidmatrix
        restZelfredzaamheidmatrixMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingZelfredzaamheidmatrix() throws Exception {
        // Initialize the database
        zelfredzaamheidmatrixRepository.saveAndFlush(zelfredzaamheidmatrix);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zelfredzaamheidmatrix
        Zelfredzaamheidmatrix updatedZelfredzaamheidmatrix = zelfredzaamheidmatrixRepository
            .findById(zelfredzaamheidmatrix.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedZelfredzaamheidmatrix are not directly saved in db
        em.detach(updatedZelfredzaamheidmatrix);
        updatedZelfredzaamheidmatrix
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumstartgeldigheid(UPDATED_DATUMSTARTGELDIGHEID)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restZelfredzaamheidmatrixMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedZelfredzaamheidmatrix.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedZelfredzaamheidmatrix))
            )
            .andExpect(status().isOk());

        // Validate the Zelfredzaamheidmatrix in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedZelfredzaamheidmatrixToMatchAllProperties(updatedZelfredzaamheidmatrix);
    }

    @Test
    @Transactional
    void putNonExistingZelfredzaamheidmatrix() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zelfredzaamheidmatrix.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZelfredzaamheidmatrixMockMvc
            .perform(
                put(ENTITY_API_URL_ID, zelfredzaamheidmatrix.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(zelfredzaamheidmatrix))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zelfredzaamheidmatrix in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchZelfredzaamheidmatrix() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zelfredzaamheidmatrix.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZelfredzaamheidmatrixMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(zelfredzaamheidmatrix))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zelfredzaamheidmatrix in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamZelfredzaamheidmatrix() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zelfredzaamheidmatrix.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZelfredzaamheidmatrixMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zelfredzaamheidmatrix)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zelfredzaamheidmatrix in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateZelfredzaamheidmatrixWithPatch() throws Exception {
        // Initialize the database
        zelfredzaamheidmatrixRepository.saveAndFlush(zelfredzaamheidmatrix);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zelfredzaamheidmatrix using partial update
        Zelfredzaamheidmatrix partialUpdatedZelfredzaamheidmatrix = new Zelfredzaamheidmatrix();
        partialUpdatedZelfredzaamheidmatrix.setId(zelfredzaamheidmatrix.getId());

        partialUpdatedZelfredzaamheidmatrix
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumstartgeldigheid(UPDATED_DATUMSTARTGELDIGHEID);

        restZelfredzaamheidmatrixMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZelfredzaamheidmatrix.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZelfredzaamheidmatrix))
            )
            .andExpect(status().isOk());

        // Validate the Zelfredzaamheidmatrix in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZelfredzaamheidmatrixUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedZelfredzaamheidmatrix, zelfredzaamheidmatrix),
            getPersistedZelfredzaamheidmatrix(zelfredzaamheidmatrix)
        );
    }

    @Test
    @Transactional
    void fullUpdateZelfredzaamheidmatrixWithPatch() throws Exception {
        // Initialize the database
        zelfredzaamheidmatrixRepository.saveAndFlush(zelfredzaamheidmatrix);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zelfredzaamheidmatrix using partial update
        Zelfredzaamheidmatrix partialUpdatedZelfredzaamheidmatrix = new Zelfredzaamheidmatrix();
        partialUpdatedZelfredzaamheidmatrix.setId(zelfredzaamheidmatrix.getId());

        partialUpdatedZelfredzaamheidmatrix
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumstartgeldigheid(UPDATED_DATUMSTARTGELDIGHEID)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restZelfredzaamheidmatrixMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZelfredzaamheidmatrix.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZelfredzaamheidmatrix))
            )
            .andExpect(status().isOk());

        // Validate the Zelfredzaamheidmatrix in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZelfredzaamheidmatrixUpdatableFieldsEquals(
            partialUpdatedZelfredzaamheidmatrix,
            getPersistedZelfredzaamheidmatrix(partialUpdatedZelfredzaamheidmatrix)
        );
    }

    @Test
    @Transactional
    void patchNonExistingZelfredzaamheidmatrix() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zelfredzaamheidmatrix.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZelfredzaamheidmatrixMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, zelfredzaamheidmatrix.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(zelfredzaamheidmatrix))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zelfredzaamheidmatrix in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchZelfredzaamheidmatrix() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zelfredzaamheidmatrix.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZelfredzaamheidmatrixMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(zelfredzaamheidmatrix))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zelfredzaamheidmatrix in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamZelfredzaamheidmatrix() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zelfredzaamheidmatrix.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZelfredzaamheidmatrixMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(zelfredzaamheidmatrix)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zelfredzaamheidmatrix in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteZelfredzaamheidmatrix() throws Exception {
        // Initialize the database
        zelfredzaamheidmatrixRepository.saveAndFlush(zelfredzaamheidmatrix);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the zelfredzaamheidmatrix
        restZelfredzaamheidmatrixMockMvc
            .perform(delete(ENTITY_API_URL_ID, zelfredzaamheidmatrix.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return zelfredzaamheidmatrixRepository.count();
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

    protected Zelfredzaamheidmatrix getPersistedZelfredzaamheidmatrix(Zelfredzaamheidmatrix zelfredzaamheidmatrix) {
        return zelfredzaamheidmatrixRepository.findById(zelfredzaamheidmatrix.getId()).orElseThrow();
    }

    protected void assertPersistedZelfredzaamheidmatrixToMatchAllProperties(Zelfredzaamheidmatrix expectedZelfredzaamheidmatrix) {
        assertZelfredzaamheidmatrixAllPropertiesEquals(
            expectedZelfredzaamheidmatrix,
            getPersistedZelfredzaamheidmatrix(expectedZelfredzaamheidmatrix)
        );
    }

    protected void assertPersistedZelfredzaamheidmatrixToMatchUpdatableProperties(Zelfredzaamheidmatrix expectedZelfredzaamheidmatrix) {
        assertZelfredzaamheidmatrixAllUpdatablePropertiesEquals(
            expectedZelfredzaamheidmatrix,
            getPersistedZelfredzaamheidmatrix(expectedZelfredzaamheidmatrix)
        );
    }
}
