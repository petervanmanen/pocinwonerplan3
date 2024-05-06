package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.InzetAsserts.*;
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
import nl.ritense.demo.domain.Inzet;
import nl.ritense.demo.repository.InzetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InzetResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class InzetResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PERCENTAGE = "AAAAAAAAAA";
    private static final String UPDATED_PERCENTAGE = "BBBBBBBBBB";

    private static final String DEFAULT_UREN = "AAAAAAAAAA";
    private static final String UPDATED_UREN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inzets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InzetRepository inzetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInzetMockMvc;

    private Inzet inzet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inzet createEntity(EntityManager em) {
        Inzet inzet = new Inzet()
            .datumbegin(DEFAULT_DATUMBEGIN)
            .datumeinde(DEFAULT_DATUMEINDE)
            .percentage(DEFAULT_PERCENTAGE)
            .uren(DEFAULT_UREN);
        return inzet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inzet createUpdatedEntity(EntityManager em) {
        Inzet inzet = new Inzet()
            .datumbegin(UPDATED_DATUMBEGIN)
            .datumeinde(UPDATED_DATUMEINDE)
            .percentage(UPDATED_PERCENTAGE)
            .uren(UPDATED_UREN);
        return inzet;
    }

    @BeforeEach
    public void initTest() {
        inzet = createEntity(em);
    }

    @Test
    @Transactional
    void createInzet() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Inzet
        var returnedInzet = om.readValue(
            restInzetMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inzet)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Inzet.class
        );

        // Validate the Inzet in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInzetUpdatableFieldsEquals(returnedInzet, getPersistedInzet(returnedInzet));
    }

    @Test
    @Transactional
    void createInzetWithExistingId() throws Exception {
        // Create the Inzet with an existing ID
        inzet.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInzetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inzet)))
            .andExpect(status().isBadRequest());

        // Validate the Inzet in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInzets() throws Exception {
        // Initialize the database
        inzetRepository.saveAndFlush(inzet);

        // Get all the inzetList
        restInzetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inzet.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegin").value(hasItem(DEFAULT_DATUMBEGIN.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE)))
            .andExpect(jsonPath("$.[*].uren").value(hasItem(DEFAULT_UREN)));
    }

    @Test
    @Transactional
    void getInzet() throws Exception {
        // Initialize the database
        inzetRepository.saveAndFlush(inzet);

        // Get the inzet
        restInzetMockMvc
            .perform(get(ENTITY_API_URL_ID, inzet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inzet.getId().intValue()))
            .andExpect(jsonPath("$.datumbegin").value(DEFAULT_DATUMBEGIN.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE))
            .andExpect(jsonPath("$.uren").value(DEFAULT_UREN));
    }

    @Test
    @Transactional
    void getNonExistingInzet() throws Exception {
        // Get the inzet
        restInzetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInzet() throws Exception {
        // Initialize the database
        inzetRepository.saveAndFlush(inzet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inzet
        Inzet updatedInzet = inzetRepository.findById(inzet.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInzet are not directly saved in db
        em.detach(updatedInzet);
        updatedInzet.datumbegin(UPDATED_DATUMBEGIN).datumeinde(UPDATED_DATUMEINDE).percentage(UPDATED_PERCENTAGE).uren(UPDATED_UREN);

        restInzetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInzet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInzet))
            )
            .andExpect(status().isOk());

        // Validate the Inzet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInzetToMatchAllProperties(updatedInzet);
    }

    @Test
    @Transactional
    void putNonExistingInzet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inzet.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInzetMockMvc
            .perform(put(ENTITY_API_URL_ID, inzet.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inzet)))
            .andExpect(status().isBadRequest());

        // Validate the Inzet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInzet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inzet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInzetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inzet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inzet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInzet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inzet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInzetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inzet)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inzet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInzetWithPatch() throws Exception {
        // Initialize the database
        inzetRepository.saveAndFlush(inzet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inzet using partial update
        Inzet partialUpdatedInzet = new Inzet();
        partialUpdatedInzet.setId(inzet.getId());

        partialUpdatedInzet.datumbegin(UPDATED_DATUMBEGIN).datumeinde(UPDATED_DATUMEINDE).percentage(UPDATED_PERCENTAGE).uren(UPDATED_UREN);

        restInzetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInzet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInzet))
            )
            .andExpect(status().isOk());

        // Validate the Inzet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInzetUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedInzet, inzet), getPersistedInzet(inzet));
    }

    @Test
    @Transactional
    void fullUpdateInzetWithPatch() throws Exception {
        // Initialize the database
        inzetRepository.saveAndFlush(inzet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inzet using partial update
        Inzet partialUpdatedInzet = new Inzet();
        partialUpdatedInzet.setId(inzet.getId());

        partialUpdatedInzet.datumbegin(UPDATED_DATUMBEGIN).datumeinde(UPDATED_DATUMEINDE).percentage(UPDATED_PERCENTAGE).uren(UPDATED_UREN);

        restInzetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInzet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInzet))
            )
            .andExpect(status().isOk());

        // Validate the Inzet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInzetUpdatableFieldsEquals(partialUpdatedInzet, getPersistedInzet(partialUpdatedInzet));
    }

    @Test
    @Transactional
    void patchNonExistingInzet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inzet.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInzetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inzet.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inzet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inzet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInzet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inzet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInzetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inzet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inzet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInzet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inzet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInzetMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inzet)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inzet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInzet() throws Exception {
        // Initialize the database
        inzetRepository.saveAndFlush(inzet);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inzet
        restInzetMockMvc
            .perform(delete(ENTITY_API_URL_ID, inzet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inzetRepository.count();
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

    protected Inzet getPersistedInzet(Inzet inzet) {
        return inzetRepository.findById(inzet.getId()).orElseThrow();
    }

    protected void assertPersistedInzetToMatchAllProperties(Inzet expectedInzet) {
        assertInzetAllPropertiesEquals(expectedInzet, getPersistedInzet(expectedInzet));
    }

    protected void assertPersistedInzetToMatchUpdatableProperties(Inzet expectedInzet) {
        assertInzetAllUpdatablePropertiesEquals(expectedInzet, getPersistedInzet(expectedInzet));
    }
}
