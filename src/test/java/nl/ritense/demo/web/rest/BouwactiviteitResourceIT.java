package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BouwactiviteitAsserts.*;
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
import nl.ritense.demo.domain.Bouwactiviteit;
import nl.ritense.demo.repository.BouwactiviteitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BouwactiviteitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BouwactiviteitResourceIT {

    private static final String DEFAULT_BOUWJAARKLASSE = "AAAAAAAAAA";
    private static final String UPDATED_BOUWJAARKLASSE = "BBBBBBBBBB";

    private static final String DEFAULT_BOUWJAARTOT = "AAAAAAAAAA";
    private static final String UPDATED_BOUWJAARTOT = "BBBBBBBBBB";

    private static final String DEFAULT_BOUWJAARVAN = "AAAAAAAAAA";
    private static final String UPDATED_BOUWJAARVAN = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIE = "AAAAAAAA";
    private static final String UPDATED_INDICATIE = "BBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bouwactiviteits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BouwactiviteitRepository bouwactiviteitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBouwactiviteitMockMvc;

    private Bouwactiviteit bouwactiviteit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bouwactiviteit createEntity(EntityManager em) {
        Bouwactiviteit bouwactiviteit = new Bouwactiviteit()
            .bouwjaarklasse(DEFAULT_BOUWJAARKLASSE)
            .bouwjaartot(DEFAULT_BOUWJAARTOT)
            .bouwjaarvan(DEFAULT_BOUWJAARVAN)
            .indicatie(DEFAULT_INDICATIE)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return bouwactiviteit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bouwactiviteit createUpdatedEntity(EntityManager em) {
        Bouwactiviteit bouwactiviteit = new Bouwactiviteit()
            .bouwjaarklasse(UPDATED_BOUWJAARKLASSE)
            .bouwjaartot(UPDATED_BOUWJAARTOT)
            .bouwjaarvan(UPDATED_BOUWJAARVAN)
            .indicatie(UPDATED_INDICATIE)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return bouwactiviteit;
    }

    @BeforeEach
    public void initTest() {
        bouwactiviteit = createEntity(em);
    }

    @Test
    @Transactional
    void createBouwactiviteit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bouwactiviteit
        var returnedBouwactiviteit = om.readValue(
            restBouwactiviteitMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwactiviteit)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bouwactiviteit.class
        );

        // Validate the Bouwactiviteit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBouwactiviteitUpdatableFieldsEquals(returnedBouwactiviteit, getPersistedBouwactiviteit(returnedBouwactiviteit));
    }

    @Test
    @Transactional
    void createBouwactiviteitWithExistingId() throws Exception {
        // Create the Bouwactiviteit with an existing ID
        bouwactiviteit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBouwactiviteitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwactiviteit)))
            .andExpect(status().isBadRequest());

        // Validate the Bouwactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBouwactiviteits() throws Exception {
        // Initialize the database
        bouwactiviteitRepository.saveAndFlush(bouwactiviteit);

        // Get all the bouwactiviteitList
        restBouwactiviteitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bouwactiviteit.getId().intValue())))
            .andExpect(jsonPath("$.[*].bouwjaarklasse").value(hasItem(DEFAULT_BOUWJAARKLASSE)))
            .andExpect(jsonPath("$.[*].bouwjaartot").value(hasItem(DEFAULT_BOUWJAARTOT)))
            .andExpect(jsonPath("$.[*].bouwjaarvan").value(hasItem(DEFAULT_BOUWJAARVAN)))
            .andExpect(jsonPath("$.[*].indicatie").value(hasItem(DEFAULT_INDICATIE)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getBouwactiviteit() throws Exception {
        // Initialize the database
        bouwactiviteitRepository.saveAndFlush(bouwactiviteit);

        // Get the bouwactiviteit
        restBouwactiviteitMockMvc
            .perform(get(ENTITY_API_URL_ID, bouwactiviteit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bouwactiviteit.getId().intValue()))
            .andExpect(jsonPath("$.bouwjaarklasse").value(DEFAULT_BOUWJAARKLASSE))
            .andExpect(jsonPath("$.bouwjaartot").value(DEFAULT_BOUWJAARTOT))
            .andExpect(jsonPath("$.bouwjaarvan").value(DEFAULT_BOUWJAARVAN))
            .andExpect(jsonPath("$.indicatie").value(DEFAULT_INDICATIE))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingBouwactiviteit() throws Exception {
        // Get the bouwactiviteit
        restBouwactiviteitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBouwactiviteit() throws Exception {
        // Initialize the database
        bouwactiviteitRepository.saveAndFlush(bouwactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwactiviteit
        Bouwactiviteit updatedBouwactiviteit = bouwactiviteitRepository.findById(bouwactiviteit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBouwactiviteit are not directly saved in db
        em.detach(updatedBouwactiviteit);
        updatedBouwactiviteit
            .bouwjaarklasse(UPDATED_BOUWJAARKLASSE)
            .bouwjaartot(UPDATED_BOUWJAARTOT)
            .bouwjaarvan(UPDATED_BOUWJAARVAN)
            .indicatie(UPDATED_INDICATIE)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restBouwactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBouwactiviteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBouwactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Bouwactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBouwactiviteitToMatchAllProperties(updatedBouwactiviteit);
    }

    @Test
    @Transactional
    void putNonExistingBouwactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwactiviteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBouwactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bouwactiviteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bouwactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBouwactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bouwactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBouwactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwactiviteitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwactiviteit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bouwactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBouwactiviteitWithPatch() throws Exception {
        // Initialize the database
        bouwactiviteitRepository.saveAndFlush(bouwactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwactiviteit using partial update
        Bouwactiviteit partialUpdatedBouwactiviteit = new Bouwactiviteit();
        partialUpdatedBouwactiviteit.setId(bouwactiviteit.getId());

        partialUpdatedBouwactiviteit.bouwjaartot(UPDATED_BOUWJAARTOT).bouwjaarvan(UPDATED_BOUWJAARVAN).indicatie(UPDATED_INDICATIE);

        restBouwactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBouwactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBouwactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Bouwactiviteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBouwactiviteitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBouwactiviteit, bouwactiviteit),
            getPersistedBouwactiviteit(bouwactiviteit)
        );
    }

    @Test
    @Transactional
    void fullUpdateBouwactiviteitWithPatch() throws Exception {
        // Initialize the database
        bouwactiviteitRepository.saveAndFlush(bouwactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwactiviteit using partial update
        Bouwactiviteit partialUpdatedBouwactiviteit = new Bouwactiviteit();
        partialUpdatedBouwactiviteit.setId(bouwactiviteit.getId());

        partialUpdatedBouwactiviteit
            .bouwjaarklasse(UPDATED_BOUWJAARKLASSE)
            .bouwjaartot(UPDATED_BOUWJAARTOT)
            .bouwjaarvan(UPDATED_BOUWJAARVAN)
            .indicatie(UPDATED_INDICATIE)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restBouwactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBouwactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBouwactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Bouwactiviteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBouwactiviteitUpdatableFieldsEquals(partialUpdatedBouwactiviteit, getPersistedBouwactiviteit(partialUpdatedBouwactiviteit));
    }

    @Test
    @Transactional
    void patchNonExistingBouwactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwactiviteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBouwactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bouwactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bouwactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBouwactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bouwactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBouwactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwactiviteitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bouwactiviteit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bouwactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBouwactiviteit() throws Exception {
        // Initialize the database
        bouwactiviteitRepository.saveAndFlush(bouwactiviteit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bouwactiviteit
        restBouwactiviteitMockMvc
            .perform(delete(ENTITY_API_URL_ID, bouwactiviteit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bouwactiviteitRepository.count();
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

    protected Bouwactiviteit getPersistedBouwactiviteit(Bouwactiviteit bouwactiviteit) {
        return bouwactiviteitRepository.findById(bouwactiviteit.getId()).orElseThrow();
    }

    protected void assertPersistedBouwactiviteitToMatchAllProperties(Bouwactiviteit expectedBouwactiviteit) {
        assertBouwactiviteitAllPropertiesEquals(expectedBouwactiviteit, getPersistedBouwactiviteit(expectedBouwactiviteit));
    }

    protected void assertPersistedBouwactiviteitToMatchUpdatableProperties(Bouwactiviteit expectedBouwactiviteit) {
        assertBouwactiviteitAllUpdatablePropertiesEquals(expectedBouwactiviteit, getPersistedBouwactiviteit(expectedBouwactiviteit));
    }
}
