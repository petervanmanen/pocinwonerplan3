package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BeslissingAsserts.*;
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
import nl.ritense.demo.domain.Beslissing;
import nl.ritense.demo.repository.BeslissingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BeslissingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BeslissingResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OPMERKINGEN = "AAAAAAAAAA";
    private static final String UPDATED_OPMERKINGEN = "BBBBBBBBBB";

    private static final String DEFAULT_REDEN = "AAAAAAAAAA";
    private static final String UPDATED_REDEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/beslissings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BeslissingRepository beslissingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeslissingMockMvc;

    private Beslissing beslissing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beslissing createEntity(EntityManager em) {
        Beslissing beslissing = new Beslissing().datum(DEFAULT_DATUM).opmerkingen(DEFAULT_OPMERKINGEN).reden(DEFAULT_REDEN);
        return beslissing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beslissing createUpdatedEntity(EntityManager em) {
        Beslissing beslissing = new Beslissing().datum(UPDATED_DATUM).opmerkingen(UPDATED_OPMERKINGEN).reden(UPDATED_REDEN);
        return beslissing;
    }

    @BeforeEach
    public void initTest() {
        beslissing = createEntity(em);
    }

    @Test
    @Transactional
    void createBeslissing() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Beslissing
        var returnedBeslissing = om.readValue(
            restBeslissingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beslissing)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Beslissing.class
        );

        // Validate the Beslissing in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBeslissingUpdatableFieldsEquals(returnedBeslissing, getPersistedBeslissing(returnedBeslissing));
    }

    @Test
    @Transactional
    void createBeslissingWithExistingId() throws Exception {
        // Create the Beslissing with an existing ID
        beslissing.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeslissingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beslissing)))
            .andExpect(status().isBadRequest());

        // Validate the Beslissing in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBeslissings() throws Exception {
        // Initialize the database
        beslissingRepository.saveAndFlush(beslissing);

        // Get all the beslissingList
        restBeslissingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beslissing.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].opmerkingen").value(hasItem(DEFAULT_OPMERKINGEN)))
            .andExpect(jsonPath("$.[*].reden").value(hasItem(DEFAULT_REDEN)));
    }

    @Test
    @Transactional
    void getBeslissing() throws Exception {
        // Initialize the database
        beslissingRepository.saveAndFlush(beslissing);

        // Get the beslissing
        restBeslissingMockMvc
            .perform(get(ENTITY_API_URL_ID, beslissing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beslissing.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.opmerkingen").value(DEFAULT_OPMERKINGEN))
            .andExpect(jsonPath("$.reden").value(DEFAULT_REDEN));
    }

    @Test
    @Transactional
    void getNonExistingBeslissing() throws Exception {
        // Get the beslissing
        restBeslissingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBeslissing() throws Exception {
        // Initialize the database
        beslissingRepository.saveAndFlush(beslissing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beslissing
        Beslissing updatedBeslissing = beslissingRepository.findById(beslissing.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBeslissing are not directly saved in db
        em.detach(updatedBeslissing);
        updatedBeslissing.datum(UPDATED_DATUM).opmerkingen(UPDATED_OPMERKINGEN).reden(UPDATED_REDEN);

        restBeslissingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBeslissing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBeslissing))
            )
            .andExpect(status().isOk());

        // Validate the Beslissing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBeslissingToMatchAllProperties(updatedBeslissing);
    }

    @Test
    @Transactional
    void putNonExistingBeslissing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beslissing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeslissingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, beslissing.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beslissing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beslissing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBeslissing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beslissing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeslissingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beslissing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beslissing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBeslissing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beslissing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeslissingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beslissing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beslissing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBeslissingWithPatch() throws Exception {
        // Initialize the database
        beslissingRepository.saveAndFlush(beslissing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beslissing using partial update
        Beslissing partialUpdatedBeslissing = new Beslissing();
        partialUpdatedBeslissing.setId(beslissing.getId());

        partialUpdatedBeslissing.opmerkingen(UPDATED_OPMERKINGEN);

        restBeslissingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeslissing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeslissing))
            )
            .andExpect(status().isOk());

        // Validate the Beslissing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeslissingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBeslissing, beslissing),
            getPersistedBeslissing(beslissing)
        );
    }

    @Test
    @Transactional
    void fullUpdateBeslissingWithPatch() throws Exception {
        // Initialize the database
        beslissingRepository.saveAndFlush(beslissing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beslissing using partial update
        Beslissing partialUpdatedBeslissing = new Beslissing();
        partialUpdatedBeslissing.setId(beslissing.getId());

        partialUpdatedBeslissing.datum(UPDATED_DATUM).opmerkingen(UPDATED_OPMERKINGEN).reden(UPDATED_REDEN);

        restBeslissingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeslissing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeslissing))
            )
            .andExpect(status().isOk());

        // Validate the Beslissing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeslissingUpdatableFieldsEquals(partialUpdatedBeslissing, getPersistedBeslissing(partialUpdatedBeslissing));
    }

    @Test
    @Transactional
    void patchNonExistingBeslissing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beslissing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeslissingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, beslissing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beslissing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beslissing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBeslissing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beslissing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeslissingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beslissing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beslissing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBeslissing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beslissing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeslissingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(beslissing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beslissing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBeslissing() throws Exception {
        // Initialize the database
        beslissingRepository.saveAndFlush(beslissing);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the beslissing
        restBeslissingMockMvc
            .perform(delete(ENTITY_API_URL_ID, beslissing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return beslissingRepository.count();
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

    protected Beslissing getPersistedBeslissing(Beslissing beslissing) {
        return beslissingRepository.findById(beslissing.getId()).orElseThrow();
    }

    protected void assertPersistedBeslissingToMatchAllProperties(Beslissing expectedBeslissing) {
        assertBeslissingAllPropertiesEquals(expectedBeslissing, getPersistedBeslissing(expectedBeslissing));
    }

    protected void assertPersistedBeslissingToMatchUpdatableProperties(Beslissing expectedBeslissing) {
        assertBeslissingAllUpdatablePropertiesEquals(expectedBeslissing, getPersistedBeslissing(expectedBeslissing));
    }
}
