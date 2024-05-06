package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.HaltverwijzingAsserts.*;
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
import nl.ritense.demo.domain.Haltverwijzing;
import nl.ritense.demo.repository.HaltverwijzingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HaltverwijzingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HaltverwijzingResourceIT {

    private static final String DEFAULT_AFDOENING = "AAAAAAAAAA";
    private static final String UPDATED_AFDOENING = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMMUTATIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMMUTATIE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMRETOUR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMRETOUR = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/haltverwijzings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HaltverwijzingRepository haltverwijzingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHaltverwijzingMockMvc;

    private Haltverwijzing haltverwijzing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Haltverwijzing createEntity(EntityManager em) {
        Haltverwijzing haltverwijzing = new Haltverwijzing()
            .afdoening(DEFAULT_AFDOENING)
            .datummutatie(DEFAULT_DATUMMUTATIE)
            .datumretour(DEFAULT_DATUMRETOUR)
            .memo(DEFAULT_MEMO);
        return haltverwijzing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Haltverwijzing createUpdatedEntity(EntityManager em) {
        Haltverwijzing haltverwijzing = new Haltverwijzing()
            .afdoening(UPDATED_AFDOENING)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .datumretour(UPDATED_DATUMRETOUR)
            .memo(UPDATED_MEMO);
        return haltverwijzing;
    }

    @BeforeEach
    public void initTest() {
        haltverwijzing = createEntity(em);
    }

    @Test
    @Transactional
    void createHaltverwijzing() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Haltverwijzing
        var returnedHaltverwijzing = om.readValue(
            restHaltverwijzingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(haltverwijzing)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Haltverwijzing.class
        );

        // Validate the Haltverwijzing in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHaltverwijzingUpdatableFieldsEquals(returnedHaltverwijzing, getPersistedHaltverwijzing(returnedHaltverwijzing));
    }

    @Test
    @Transactional
    void createHaltverwijzingWithExistingId() throws Exception {
        // Create the Haltverwijzing with an existing ID
        haltverwijzing.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHaltverwijzingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(haltverwijzing)))
            .andExpect(status().isBadRequest());

        // Validate the Haltverwijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHaltverwijzings() throws Exception {
        // Initialize the database
        haltverwijzingRepository.saveAndFlush(haltverwijzing);

        // Get all the haltverwijzingList
        restHaltverwijzingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(haltverwijzing.getId().intValue())))
            .andExpect(jsonPath("$.[*].afdoening").value(hasItem(DEFAULT_AFDOENING)))
            .andExpect(jsonPath("$.[*].datummutatie").value(hasItem(DEFAULT_DATUMMUTATIE.toString())))
            .andExpect(jsonPath("$.[*].datumretour").value(hasItem(DEFAULT_DATUMRETOUR.toString())))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)));
    }

    @Test
    @Transactional
    void getHaltverwijzing() throws Exception {
        // Initialize the database
        haltverwijzingRepository.saveAndFlush(haltverwijzing);

        // Get the haltverwijzing
        restHaltverwijzingMockMvc
            .perform(get(ENTITY_API_URL_ID, haltverwijzing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(haltverwijzing.getId().intValue()))
            .andExpect(jsonPath("$.afdoening").value(DEFAULT_AFDOENING))
            .andExpect(jsonPath("$.datummutatie").value(DEFAULT_DATUMMUTATIE.toString()))
            .andExpect(jsonPath("$.datumretour").value(DEFAULT_DATUMRETOUR.toString()))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO));
    }

    @Test
    @Transactional
    void getNonExistingHaltverwijzing() throws Exception {
        // Get the haltverwijzing
        restHaltverwijzingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHaltverwijzing() throws Exception {
        // Initialize the database
        haltverwijzingRepository.saveAndFlush(haltverwijzing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the haltverwijzing
        Haltverwijzing updatedHaltverwijzing = haltverwijzingRepository.findById(haltverwijzing.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHaltverwijzing are not directly saved in db
        em.detach(updatedHaltverwijzing);
        updatedHaltverwijzing
            .afdoening(UPDATED_AFDOENING)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .datumretour(UPDATED_DATUMRETOUR)
            .memo(UPDATED_MEMO);

        restHaltverwijzingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHaltverwijzing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHaltverwijzing))
            )
            .andExpect(status().isOk());

        // Validate the Haltverwijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHaltverwijzingToMatchAllProperties(updatedHaltverwijzing);
    }

    @Test
    @Transactional
    void putNonExistingHaltverwijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        haltverwijzing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHaltverwijzingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, haltverwijzing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(haltverwijzing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Haltverwijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHaltverwijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        haltverwijzing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHaltverwijzingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(haltverwijzing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Haltverwijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHaltverwijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        haltverwijzing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHaltverwijzingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(haltverwijzing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Haltverwijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHaltverwijzingWithPatch() throws Exception {
        // Initialize the database
        haltverwijzingRepository.saveAndFlush(haltverwijzing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the haltverwijzing using partial update
        Haltverwijzing partialUpdatedHaltverwijzing = new Haltverwijzing();
        partialUpdatedHaltverwijzing.setId(haltverwijzing.getId());

        partialUpdatedHaltverwijzing.datumretour(UPDATED_DATUMRETOUR).memo(UPDATED_MEMO);

        restHaltverwijzingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHaltverwijzing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHaltverwijzing))
            )
            .andExpect(status().isOk());

        // Validate the Haltverwijzing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHaltverwijzingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHaltverwijzing, haltverwijzing),
            getPersistedHaltverwijzing(haltverwijzing)
        );
    }

    @Test
    @Transactional
    void fullUpdateHaltverwijzingWithPatch() throws Exception {
        // Initialize the database
        haltverwijzingRepository.saveAndFlush(haltverwijzing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the haltverwijzing using partial update
        Haltverwijzing partialUpdatedHaltverwijzing = new Haltverwijzing();
        partialUpdatedHaltverwijzing.setId(haltverwijzing.getId());

        partialUpdatedHaltverwijzing
            .afdoening(UPDATED_AFDOENING)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .datumretour(UPDATED_DATUMRETOUR)
            .memo(UPDATED_MEMO);

        restHaltverwijzingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHaltverwijzing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHaltverwijzing))
            )
            .andExpect(status().isOk());

        // Validate the Haltverwijzing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHaltverwijzingUpdatableFieldsEquals(partialUpdatedHaltverwijzing, getPersistedHaltverwijzing(partialUpdatedHaltverwijzing));
    }

    @Test
    @Transactional
    void patchNonExistingHaltverwijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        haltverwijzing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHaltverwijzingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, haltverwijzing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(haltverwijzing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Haltverwijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHaltverwijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        haltverwijzing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHaltverwijzingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(haltverwijzing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Haltverwijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHaltverwijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        haltverwijzing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHaltverwijzingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(haltverwijzing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Haltverwijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHaltverwijzing() throws Exception {
        // Initialize the database
        haltverwijzingRepository.saveAndFlush(haltverwijzing);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the haltverwijzing
        restHaltverwijzingMockMvc
            .perform(delete(ENTITY_API_URL_ID, haltverwijzing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return haltverwijzingRepository.count();
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

    protected Haltverwijzing getPersistedHaltverwijzing(Haltverwijzing haltverwijzing) {
        return haltverwijzingRepository.findById(haltverwijzing.getId()).orElseThrow();
    }

    protected void assertPersistedHaltverwijzingToMatchAllProperties(Haltverwijzing expectedHaltverwijzing) {
        assertHaltverwijzingAllPropertiesEquals(expectedHaltverwijzing, getPersistedHaltverwijzing(expectedHaltverwijzing));
    }

    protected void assertPersistedHaltverwijzingToMatchUpdatableProperties(Haltverwijzing expectedHaltverwijzing) {
        assertHaltverwijzingAllUpdatablePropertiesEquals(expectedHaltverwijzing, getPersistedHaltverwijzing(expectedHaltverwijzing));
    }
}
