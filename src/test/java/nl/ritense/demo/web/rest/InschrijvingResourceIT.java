package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.InschrijvingAsserts.*;
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
import nl.ritense.demo.domain.Inschrijving;
import nl.ritense.demo.repository.InschrijvingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InschrijvingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InschrijvingResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/inschrijvings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InschrijvingRepository inschrijvingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInschrijvingMockMvc;

    private Inschrijving inschrijving;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inschrijving createEntity(EntityManager em) {
        Inschrijving inschrijving = new Inschrijving().datum(DEFAULT_DATUM);
        return inschrijving;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inschrijving createUpdatedEntity(EntityManager em) {
        Inschrijving inschrijving = new Inschrijving().datum(UPDATED_DATUM);
        return inschrijving;
    }

    @BeforeEach
    public void initTest() {
        inschrijving = createEntity(em);
    }

    @Test
    @Transactional
    void createInschrijving() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Inschrijving
        var returnedInschrijving = om.readValue(
            restInschrijvingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inschrijving)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Inschrijving.class
        );

        // Validate the Inschrijving in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInschrijvingUpdatableFieldsEquals(returnedInschrijving, getPersistedInschrijving(returnedInschrijving));
    }

    @Test
    @Transactional
    void createInschrijvingWithExistingId() throws Exception {
        // Create the Inschrijving with an existing ID
        inschrijving.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInschrijvingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inschrijving)))
            .andExpect(status().isBadRequest());

        // Validate the Inschrijving in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInschrijvings() throws Exception {
        // Initialize the database
        inschrijvingRepository.saveAndFlush(inschrijving);

        // Get all the inschrijvingList
        restInschrijvingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inschrijving.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())));
    }

    @Test
    @Transactional
    void getInschrijving() throws Exception {
        // Initialize the database
        inschrijvingRepository.saveAndFlush(inschrijving);

        // Get the inschrijving
        restInschrijvingMockMvc
            .perform(get(ENTITY_API_URL_ID, inschrijving.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inschrijving.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()));
    }

    @Test
    @Transactional
    void getNonExistingInschrijving() throws Exception {
        // Get the inschrijving
        restInschrijvingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInschrijving() throws Exception {
        // Initialize the database
        inschrijvingRepository.saveAndFlush(inschrijving);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inschrijving
        Inschrijving updatedInschrijving = inschrijvingRepository.findById(inschrijving.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInschrijving are not directly saved in db
        em.detach(updatedInschrijving);
        updatedInschrijving.datum(UPDATED_DATUM);

        restInschrijvingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInschrijving.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInschrijving))
            )
            .andExpect(status().isOk());

        // Validate the Inschrijving in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInschrijvingToMatchAllProperties(updatedInschrijving);
    }

    @Test
    @Transactional
    void putNonExistingInschrijving() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inschrijving.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInschrijvingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inschrijving.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inschrijving))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inschrijving in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInschrijving() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inschrijving.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInschrijvingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inschrijving))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inschrijving in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInschrijving() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inschrijving.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInschrijvingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inschrijving)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inschrijving in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInschrijvingWithPatch() throws Exception {
        // Initialize the database
        inschrijvingRepository.saveAndFlush(inschrijving);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inschrijving using partial update
        Inschrijving partialUpdatedInschrijving = new Inschrijving();
        partialUpdatedInschrijving.setId(inschrijving.getId());

        restInschrijvingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInschrijving.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInschrijving))
            )
            .andExpect(status().isOk());

        // Validate the Inschrijving in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInschrijvingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInschrijving, inschrijving),
            getPersistedInschrijving(inschrijving)
        );
    }

    @Test
    @Transactional
    void fullUpdateInschrijvingWithPatch() throws Exception {
        // Initialize the database
        inschrijvingRepository.saveAndFlush(inschrijving);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inschrijving using partial update
        Inschrijving partialUpdatedInschrijving = new Inschrijving();
        partialUpdatedInschrijving.setId(inschrijving.getId());

        partialUpdatedInschrijving.datum(UPDATED_DATUM);

        restInschrijvingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInschrijving.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInschrijving))
            )
            .andExpect(status().isOk());

        // Validate the Inschrijving in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInschrijvingUpdatableFieldsEquals(partialUpdatedInschrijving, getPersistedInschrijving(partialUpdatedInschrijving));
    }

    @Test
    @Transactional
    void patchNonExistingInschrijving() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inschrijving.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInschrijvingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inschrijving.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inschrijving))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inschrijving in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInschrijving() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inschrijving.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInschrijvingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inschrijving))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inschrijving in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInschrijving() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inschrijving.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInschrijvingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inschrijving)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inschrijving in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInschrijving() throws Exception {
        // Initialize the database
        inschrijvingRepository.saveAndFlush(inschrijving);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inschrijving
        restInschrijvingMockMvc
            .perform(delete(ENTITY_API_URL_ID, inschrijving.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inschrijvingRepository.count();
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

    protected Inschrijving getPersistedInschrijving(Inschrijving inschrijving) {
        return inschrijvingRepository.findById(inschrijving.getId()).orElseThrow();
    }

    protected void assertPersistedInschrijvingToMatchAllProperties(Inschrijving expectedInschrijving) {
        assertInschrijvingAllPropertiesEquals(expectedInschrijving, getPersistedInschrijving(expectedInschrijving));
    }

    protected void assertPersistedInschrijvingToMatchUpdatableProperties(Inschrijving expectedInschrijving) {
        assertInschrijvingAllUpdatablePropertiesEquals(expectedInschrijving, getPersistedInschrijving(expectedInschrijving));
    }
}
