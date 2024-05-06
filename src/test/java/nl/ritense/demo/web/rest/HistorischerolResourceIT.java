package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.HistorischerolAsserts.*;
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
import nl.ritense.demo.domain.Historischerol;
import nl.ritense.demo.repository.HistorischerolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HistorischerolResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HistorischerolResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/historischerols";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HistorischerolRepository historischerolRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistorischerolMockMvc;

    private Historischerol historischerol;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historischerol createEntity(EntityManager em) {
        Historischerol historischerol = new Historischerol().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return historischerol;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historischerol createUpdatedEntity(EntityManager em) {
        Historischerol historischerol = new Historischerol().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return historischerol;
    }

    @BeforeEach
    public void initTest() {
        historischerol = createEntity(em);
    }

    @Test
    @Transactional
    void createHistorischerol() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Historischerol
        var returnedHistorischerol = om.readValue(
            restHistorischerolMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historischerol)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Historischerol.class
        );

        // Validate the Historischerol in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHistorischerolUpdatableFieldsEquals(returnedHistorischerol, getPersistedHistorischerol(returnedHistorischerol));
    }

    @Test
    @Transactional
    void createHistorischerolWithExistingId() throws Exception {
        // Create the Historischerol with an existing ID
        historischerol.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistorischerolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historischerol)))
            .andExpect(status().isBadRequest());

        // Validate the Historischerol in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHistorischerols() throws Exception {
        // Initialize the database
        historischerolRepository.saveAndFlush(historischerol);

        // Get all the historischerolList
        restHistorischerolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historischerol.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getHistorischerol() throws Exception {
        // Initialize the database
        historischerolRepository.saveAndFlush(historischerol);

        // Get the historischerol
        restHistorischerolMockMvc
            .perform(get(ENTITY_API_URL_ID, historischerol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historischerol.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingHistorischerol() throws Exception {
        // Get the historischerol
        restHistorischerolMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHistorischerol() throws Exception {
        // Initialize the database
        historischerolRepository.saveAndFlush(historischerol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the historischerol
        Historischerol updatedHistorischerol = historischerolRepository.findById(historischerol.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHistorischerol are not directly saved in db
        em.detach(updatedHistorischerol);
        updatedHistorischerol.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restHistorischerolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHistorischerol.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHistorischerol))
            )
            .andExpect(status().isOk());

        // Validate the Historischerol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHistorischerolToMatchAllProperties(updatedHistorischerol);
    }

    @Test
    @Transactional
    void putNonExistingHistorischerol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historischerol.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistorischerolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historischerol.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historischerol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Historischerol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHistorischerol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historischerol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistorischerolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historischerol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Historischerol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHistorischerol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historischerol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistorischerolMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historischerol)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Historischerol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHistorischerolWithPatch() throws Exception {
        // Initialize the database
        historischerolRepository.saveAndFlush(historischerol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the historischerol using partial update
        Historischerol partialUpdatedHistorischerol = new Historischerol();
        partialUpdatedHistorischerol.setId(historischerol.getId());

        partialUpdatedHistorischerol.naam(UPDATED_NAAM);

        restHistorischerolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistorischerol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHistorischerol))
            )
            .andExpect(status().isOk());

        // Validate the Historischerol in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHistorischerolUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHistorischerol, historischerol),
            getPersistedHistorischerol(historischerol)
        );
    }

    @Test
    @Transactional
    void fullUpdateHistorischerolWithPatch() throws Exception {
        // Initialize the database
        historischerolRepository.saveAndFlush(historischerol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the historischerol using partial update
        Historischerol partialUpdatedHistorischerol = new Historischerol();
        partialUpdatedHistorischerol.setId(historischerol.getId());

        partialUpdatedHistorischerol.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restHistorischerolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistorischerol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHistorischerol))
            )
            .andExpect(status().isOk());

        // Validate the Historischerol in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHistorischerolUpdatableFieldsEquals(partialUpdatedHistorischerol, getPersistedHistorischerol(partialUpdatedHistorischerol));
    }

    @Test
    @Transactional
    void patchNonExistingHistorischerol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historischerol.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistorischerolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, historischerol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(historischerol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Historischerol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHistorischerol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historischerol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistorischerolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(historischerol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Historischerol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHistorischerol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historischerol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistorischerolMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(historischerol)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Historischerol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHistorischerol() throws Exception {
        // Initialize the database
        historischerolRepository.saveAndFlush(historischerol);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the historischerol
        restHistorischerolMockMvc
            .perform(delete(ENTITY_API_URL_ID, historischerol.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return historischerolRepository.count();
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

    protected Historischerol getPersistedHistorischerol(Historischerol historischerol) {
        return historischerolRepository.findById(historischerol.getId()).orElseThrow();
    }

    protected void assertPersistedHistorischerolToMatchAllProperties(Historischerol expectedHistorischerol) {
        assertHistorischerolAllPropertiesEquals(expectedHistorischerol, getPersistedHistorischerol(expectedHistorischerol));
    }

    protected void assertPersistedHistorischerolToMatchUpdatableProperties(Historischerol expectedHistorischerol) {
        assertHistorischerolAllUpdatablePropertiesEquals(expectedHistorischerol, getPersistedHistorischerol(expectedHistorischerol));
    }
}
