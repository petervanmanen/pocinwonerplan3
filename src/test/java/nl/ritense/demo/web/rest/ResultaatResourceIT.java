package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ResultaatAsserts.*;
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
import nl.ritense.demo.domain.Resultaat;
import nl.ritense.demo.repository.ResultaatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ResultaatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResultaatResourceIT {

    private static final String DEFAULT_DATUM = "AAAAAAAAAA";
    private static final String UPDATED_DATUM = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/resultaats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ResultaatRepository resultaatRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResultaatMockMvc;

    private Resultaat resultaat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resultaat createEntity(EntityManager em) {
        Resultaat resultaat = new Resultaat().datum(DEFAULT_DATUM).naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return resultaat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resultaat createUpdatedEntity(EntityManager em) {
        Resultaat resultaat = new Resultaat().datum(UPDATED_DATUM).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return resultaat;
    }

    @BeforeEach
    public void initTest() {
        resultaat = createEntity(em);
    }

    @Test
    @Transactional
    void createResultaat() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Resultaat
        var returnedResultaat = om.readValue(
            restResultaatMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(resultaat)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Resultaat.class
        );

        // Validate the Resultaat in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertResultaatUpdatableFieldsEquals(returnedResultaat, getPersistedResultaat(returnedResultaat));
    }

    @Test
    @Transactional
    void createResultaatWithExistingId() throws Exception {
        // Create the Resultaat with an existing ID
        resultaat.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultaatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(resultaat)))
            .andExpect(status().isBadRequest());

        // Validate the Resultaat in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResultaats() throws Exception {
        // Initialize the database
        resultaatRepository.saveAndFlush(resultaat);

        // Get all the resultaatList
        restResultaatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultaat.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getResultaat() throws Exception {
        // Initialize the database
        resultaatRepository.saveAndFlush(resultaat);

        // Get the resultaat
        restResultaatMockMvc
            .perform(get(ENTITY_API_URL_ID, resultaat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resultaat.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingResultaat() throws Exception {
        // Get the resultaat
        restResultaatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResultaat() throws Exception {
        // Initialize the database
        resultaatRepository.saveAndFlush(resultaat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the resultaat
        Resultaat updatedResultaat = resultaatRepository.findById(resultaat.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedResultaat are not directly saved in db
        em.detach(updatedResultaat);
        updatedResultaat.datum(UPDATED_DATUM).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restResultaatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResultaat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedResultaat))
            )
            .andExpect(status().isOk());

        // Validate the Resultaat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedResultaatToMatchAllProperties(updatedResultaat);
    }

    @Test
    @Transactional
    void putNonExistingResultaat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultaat.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultaatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resultaat.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(resultaat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resultaat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResultaat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultaat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultaatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(resultaat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resultaat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResultaat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultaat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultaatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(resultaat)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Resultaat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResultaatWithPatch() throws Exception {
        // Initialize the database
        resultaatRepository.saveAndFlush(resultaat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the resultaat using partial update
        Resultaat partialUpdatedResultaat = new Resultaat();
        partialUpdatedResultaat.setId(resultaat.getId());

        partialUpdatedResultaat.naam(UPDATED_NAAM);

        restResultaatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResultaat.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedResultaat))
            )
            .andExpect(status().isOk());

        // Validate the Resultaat in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertResultaatUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedResultaat, resultaat),
            getPersistedResultaat(resultaat)
        );
    }

    @Test
    @Transactional
    void fullUpdateResultaatWithPatch() throws Exception {
        // Initialize the database
        resultaatRepository.saveAndFlush(resultaat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the resultaat using partial update
        Resultaat partialUpdatedResultaat = new Resultaat();
        partialUpdatedResultaat.setId(resultaat.getId());

        partialUpdatedResultaat.datum(UPDATED_DATUM).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restResultaatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResultaat.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedResultaat))
            )
            .andExpect(status().isOk());

        // Validate the Resultaat in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertResultaatUpdatableFieldsEquals(partialUpdatedResultaat, getPersistedResultaat(partialUpdatedResultaat));
    }

    @Test
    @Transactional
    void patchNonExistingResultaat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultaat.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultaatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resultaat.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(resultaat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resultaat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResultaat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultaat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultaatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(resultaat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resultaat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResultaat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultaat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultaatMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(resultaat)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Resultaat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResultaat() throws Exception {
        // Initialize the database
        resultaatRepository.saveAndFlush(resultaat);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the resultaat
        restResultaatMockMvc
            .perform(delete(ENTITY_API_URL_ID, resultaat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return resultaatRepository.count();
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

    protected Resultaat getPersistedResultaat(Resultaat resultaat) {
        return resultaatRepository.findById(resultaat.getId()).orElseThrow();
    }

    protected void assertPersistedResultaatToMatchAllProperties(Resultaat expectedResultaat) {
        assertResultaatAllPropertiesEquals(expectedResultaat, getPersistedResultaat(expectedResultaat));
    }

    protected void assertPersistedResultaatToMatchUpdatableProperties(Resultaat expectedResultaat) {
        assertResultaatAllUpdatablePropertiesEquals(expectedResultaat, getPersistedResultaat(expectedResultaat));
    }
}
