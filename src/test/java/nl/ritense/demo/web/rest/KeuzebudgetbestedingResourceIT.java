package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KeuzebudgetbestedingAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Keuzebudgetbesteding;
import nl.ritense.demo.repository.KeuzebudgetbestedingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KeuzebudgetbestedingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KeuzebudgetbestedingResourceIT {

    private static final BigDecimal DEFAULT_BEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAG = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/keuzebudgetbestedings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KeuzebudgetbestedingRepository keuzebudgetbestedingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKeuzebudgetbestedingMockMvc;

    private Keuzebudgetbesteding keuzebudgetbesteding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Keuzebudgetbesteding createEntity(EntityManager em) {
        Keuzebudgetbesteding keuzebudgetbesteding = new Keuzebudgetbesteding().bedrag(DEFAULT_BEDRAG).datum(DEFAULT_DATUM);
        return keuzebudgetbesteding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Keuzebudgetbesteding createUpdatedEntity(EntityManager em) {
        Keuzebudgetbesteding keuzebudgetbesteding = new Keuzebudgetbesteding().bedrag(UPDATED_BEDRAG).datum(UPDATED_DATUM);
        return keuzebudgetbesteding;
    }

    @BeforeEach
    public void initTest() {
        keuzebudgetbesteding = createEntity(em);
    }

    @Test
    @Transactional
    void createKeuzebudgetbesteding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Keuzebudgetbesteding
        var returnedKeuzebudgetbesteding = om.readValue(
            restKeuzebudgetbestedingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(keuzebudgetbesteding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Keuzebudgetbesteding.class
        );

        // Validate the Keuzebudgetbesteding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKeuzebudgetbestedingUpdatableFieldsEquals(
            returnedKeuzebudgetbesteding,
            getPersistedKeuzebudgetbesteding(returnedKeuzebudgetbesteding)
        );
    }

    @Test
    @Transactional
    void createKeuzebudgetbestedingWithExistingId() throws Exception {
        // Create the Keuzebudgetbesteding with an existing ID
        keuzebudgetbesteding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKeuzebudgetbestedingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(keuzebudgetbesteding)))
            .andExpect(status().isBadRequest());

        // Validate the Keuzebudgetbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKeuzebudgetbestedings() throws Exception {
        // Initialize the database
        keuzebudgetbestedingRepository.saveAndFlush(keuzebudgetbesteding);

        // Get all the keuzebudgetbestedingList
        restKeuzebudgetbestedingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keuzebudgetbesteding.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(sameNumber(DEFAULT_BEDRAG))))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())));
    }

    @Test
    @Transactional
    void getKeuzebudgetbesteding() throws Exception {
        // Initialize the database
        keuzebudgetbestedingRepository.saveAndFlush(keuzebudgetbesteding);

        // Get the keuzebudgetbesteding
        restKeuzebudgetbestedingMockMvc
            .perform(get(ENTITY_API_URL_ID, keuzebudgetbesteding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(keuzebudgetbesteding.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(sameNumber(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()));
    }

    @Test
    @Transactional
    void getNonExistingKeuzebudgetbesteding() throws Exception {
        // Get the keuzebudgetbesteding
        restKeuzebudgetbestedingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKeuzebudgetbesteding() throws Exception {
        // Initialize the database
        keuzebudgetbestedingRepository.saveAndFlush(keuzebudgetbesteding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the keuzebudgetbesteding
        Keuzebudgetbesteding updatedKeuzebudgetbesteding = keuzebudgetbestedingRepository
            .findById(keuzebudgetbesteding.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedKeuzebudgetbesteding are not directly saved in db
        em.detach(updatedKeuzebudgetbesteding);
        updatedKeuzebudgetbesteding.bedrag(UPDATED_BEDRAG).datum(UPDATED_DATUM);

        restKeuzebudgetbestedingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKeuzebudgetbesteding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKeuzebudgetbesteding))
            )
            .andExpect(status().isOk());

        // Validate the Keuzebudgetbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKeuzebudgetbestedingToMatchAllProperties(updatedKeuzebudgetbesteding);
    }

    @Test
    @Transactional
    void putNonExistingKeuzebudgetbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keuzebudgetbesteding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKeuzebudgetbestedingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, keuzebudgetbesteding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(keuzebudgetbesteding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Keuzebudgetbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKeuzebudgetbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keuzebudgetbesteding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKeuzebudgetbestedingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(keuzebudgetbesteding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Keuzebudgetbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKeuzebudgetbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keuzebudgetbesteding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKeuzebudgetbestedingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(keuzebudgetbesteding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Keuzebudgetbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKeuzebudgetbestedingWithPatch() throws Exception {
        // Initialize the database
        keuzebudgetbestedingRepository.saveAndFlush(keuzebudgetbesteding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the keuzebudgetbesteding using partial update
        Keuzebudgetbesteding partialUpdatedKeuzebudgetbesteding = new Keuzebudgetbesteding();
        partialUpdatedKeuzebudgetbesteding.setId(keuzebudgetbesteding.getId());

        partialUpdatedKeuzebudgetbesteding.datum(UPDATED_DATUM);

        restKeuzebudgetbestedingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKeuzebudgetbesteding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKeuzebudgetbesteding))
            )
            .andExpect(status().isOk());

        // Validate the Keuzebudgetbesteding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKeuzebudgetbestedingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKeuzebudgetbesteding, keuzebudgetbesteding),
            getPersistedKeuzebudgetbesteding(keuzebudgetbesteding)
        );
    }

    @Test
    @Transactional
    void fullUpdateKeuzebudgetbestedingWithPatch() throws Exception {
        // Initialize the database
        keuzebudgetbestedingRepository.saveAndFlush(keuzebudgetbesteding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the keuzebudgetbesteding using partial update
        Keuzebudgetbesteding partialUpdatedKeuzebudgetbesteding = new Keuzebudgetbesteding();
        partialUpdatedKeuzebudgetbesteding.setId(keuzebudgetbesteding.getId());

        partialUpdatedKeuzebudgetbesteding.bedrag(UPDATED_BEDRAG).datum(UPDATED_DATUM);

        restKeuzebudgetbestedingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKeuzebudgetbesteding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKeuzebudgetbesteding))
            )
            .andExpect(status().isOk());

        // Validate the Keuzebudgetbesteding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKeuzebudgetbestedingUpdatableFieldsEquals(
            partialUpdatedKeuzebudgetbesteding,
            getPersistedKeuzebudgetbesteding(partialUpdatedKeuzebudgetbesteding)
        );
    }

    @Test
    @Transactional
    void patchNonExistingKeuzebudgetbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keuzebudgetbesteding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKeuzebudgetbestedingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, keuzebudgetbesteding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(keuzebudgetbesteding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Keuzebudgetbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKeuzebudgetbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keuzebudgetbesteding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKeuzebudgetbestedingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(keuzebudgetbesteding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Keuzebudgetbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKeuzebudgetbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        keuzebudgetbesteding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKeuzebudgetbestedingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(keuzebudgetbesteding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Keuzebudgetbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKeuzebudgetbesteding() throws Exception {
        // Initialize the database
        keuzebudgetbestedingRepository.saveAndFlush(keuzebudgetbesteding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the keuzebudgetbesteding
        restKeuzebudgetbestedingMockMvc
            .perform(delete(ENTITY_API_URL_ID, keuzebudgetbesteding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return keuzebudgetbestedingRepository.count();
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

    protected Keuzebudgetbesteding getPersistedKeuzebudgetbesteding(Keuzebudgetbesteding keuzebudgetbesteding) {
        return keuzebudgetbestedingRepository.findById(keuzebudgetbesteding.getId()).orElseThrow();
    }

    protected void assertPersistedKeuzebudgetbestedingToMatchAllProperties(Keuzebudgetbesteding expectedKeuzebudgetbesteding) {
        assertKeuzebudgetbestedingAllPropertiesEquals(
            expectedKeuzebudgetbesteding,
            getPersistedKeuzebudgetbesteding(expectedKeuzebudgetbesteding)
        );
    }

    protected void assertPersistedKeuzebudgetbestedingToMatchUpdatableProperties(Keuzebudgetbesteding expectedKeuzebudgetbesteding) {
        assertKeuzebudgetbestedingAllUpdatablePropertiesEquals(
            expectedKeuzebudgetbesteding,
            getPersistedKeuzebudgetbesteding(expectedKeuzebudgetbesteding)
        );
    }
}
