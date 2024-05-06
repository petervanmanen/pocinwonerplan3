package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RapportagemomentAsserts.*;
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
import nl.ritense.demo.domain.Rapportagemoment;
import nl.ritense.demo.repository.RapportagemomentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RapportagemomentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RapportagemomentResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_TERMIJN = "AAAAAAAAAA";
    private static final String UPDATED_TERMIJN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rapportagemoments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RapportagemomentRepository rapportagemomentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRapportagemomentMockMvc;

    private Rapportagemoment rapportagemoment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rapportagemoment createEntity(EntityManager em) {
        Rapportagemoment rapportagemoment = new Rapportagemoment()
            .datum(DEFAULT_DATUM)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .termijn(DEFAULT_TERMIJN);
        return rapportagemoment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rapportagemoment createUpdatedEntity(EntityManager em) {
        Rapportagemoment rapportagemoment = new Rapportagemoment()
            .datum(UPDATED_DATUM)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .termijn(UPDATED_TERMIJN);
        return rapportagemoment;
    }

    @BeforeEach
    public void initTest() {
        rapportagemoment = createEntity(em);
    }

    @Test
    @Transactional
    void createRapportagemoment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Rapportagemoment
        var returnedRapportagemoment = om.readValue(
            restRapportagemomentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rapportagemoment)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Rapportagemoment.class
        );

        // Validate the Rapportagemoment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRapportagemomentUpdatableFieldsEquals(returnedRapportagemoment, getPersistedRapportagemoment(returnedRapportagemoment));
    }

    @Test
    @Transactional
    void createRapportagemomentWithExistingId() throws Exception {
        // Create the Rapportagemoment with an existing ID
        rapportagemoment.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRapportagemomentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rapportagemoment)))
            .andExpect(status().isBadRequest());

        // Validate the Rapportagemoment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRapportagemoments() throws Exception {
        // Initialize the database
        rapportagemomentRepository.saveAndFlush(rapportagemoment);

        // Get all the rapportagemomentList
        restRapportagemomentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rapportagemoment.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].termijn").value(hasItem(DEFAULT_TERMIJN)));
    }

    @Test
    @Transactional
    void getRapportagemoment() throws Exception {
        // Initialize the database
        rapportagemomentRepository.saveAndFlush(rapportagemoment);

        // Get the rapportagemoment
        restRapportagemomentMockMvc
            .perform(get(ENTITY_API_URL_ID, rapportagemoment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rapportagemoment.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.termijn").value(DEFAULT_TERMIJN));
    }

    @Test
    @Transactional
    void getNonExistingRapportagemoment() throws Exception {
        // Get the rapportagemoment
        restRapportagemomentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRapportagemoment() throws Exception {
        // Initialize the database
        rapportagemomentRepository.saveAndFlush(rapportagemoment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rapportagemoment
        Rapportagemoment updatedRapportagemoment = rapportagemomentRepository.findById(rapportagemoment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRapportagemoment are not directly saved in db
        em.detach(updatedRapportagemoment);
        updatedRapportagemoment.datum(UPDATED_DATUM).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING).termijn(UPDATED_TERMIJN);

        restRapportagemomentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRapportagemoment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRapportagemoment))
            )
            .andExpect(status().isOk());

        // Validate the Rapportagemoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRapportagemomentToMatchAllProperties(updatedRapportagemoment);
    }

    @Test
    @Transactional
    void putNonExistingRapportagemoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rapportagemoment.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRapportagemomentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rapportagemoment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rapportagemoment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rapportagemoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRapportagemoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rapportagemoment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRapportagemomentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rapportagemoment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rapportagemoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRapportagemoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rapportagemoment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRapportagemomentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rapportagemoment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rapportagemoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRapportagemomentWithPatch() throws Exception {
        // Initialize the database
        rapportagemomentRepository.saveAndFlush(rapportagemoment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rapportagemoment using partial update
        Rapportagemoment partialUpdatedRapportagemoment = new Rapportagemoment();
        partialUpdatedRapportagemoment.setId(rapportagemoment.getId());

        partialUpdatedRapportagemoment.datum(UPDATED_DATUM).omschrijving(UPDATED_OMSCHRIJVING).termijn(UPDATED_TERMIJN);

        restRapportagemomentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRapportagemoment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRapportagemoment))
            )
            .andExpect(status().isOk());

        // Validate the Rapportagemoment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRapportagemomentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRapportagemoment, rapportagemoment),
            getPersistedRapportagemoment(rapportagemoment)
        );
    }

    @Test
    @Transactional
    void fullUpdateRapportagemomentWithPatch() throws Exception {
        // Initialize the database
        rapportagemomentRepository.saveAndFlush(rapportagemoment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rapportagemoment using partial update
        Rapportagemoment partialUpdatedRapportagemoment = new Rapportagemoment();
        partialUpdatedRapportagemoment.setId(rapportagemoment.getId());

        partialUpdatedRapportagemoment.datum(UPDATED_DATUM).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING).termijn(UPDATED_TERMIJN);

        restRapportagemomentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRapportagemoment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRapportagemoment))
            )
            .andExpect(status().isOk());

        // Validate the Rapportagemoment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRapportagemomentUpdatableFieldsEquals(
            partialUpdatedRapportagemoment,
            getPersistedRapportagemoment(partialUpdatedRapportagemoment)
        );
    }

    @Test
    @Transactional
    void patchNonExistingRapportagemoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rapportagemoment.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRapportagemomentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rapportagemoment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rapportagemoment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rapportagemoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRapportagemoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rapportagemoment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRapportagemomentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rapportagemoment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rapportagemoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRapportagemoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rapportagemoment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRapportagemomentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rapportagemoment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rapportagemoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRapportagemoment() throws Exception {
        // Initialize the database
        rapportagemomentRepository.saveAndFlush(rapportagemoment);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rapportagemoment
        restRapportagemomentMockMvc
            .perform(delete(ENTITY_API_URL_ID, rapportagemoment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rapportagemomentRepository.count();
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

    protected Rapportagemoment getPersistedRapportagemoment(Rapportagemoment rapportagemoment) {
        return rapportagemomentRepository.findById(rapportagemoment.getId()).orElseThrow();
    }

    protected void assertPersistedRapportagemomentToMatchAllProperties(Rapportagemoment expectedRapportagemoment) {
        assertRapportagemomentAllPropertiesEquals(expectedRapportagemoment, getPersistedRapportagemoment(expectedRapportagemoment));
    }

    protected void assertPersistedRapportagemomentToMatchUpdatableProperties(Rapportagemoment expectedRapportagemoment) {
        assertRapportagemomentAllUpdatablePropertiesEquals(
            expectedRapportagemoment,
            getPersistedRapportagemoment(expectedRapportagemoment)
        );
    }
}
