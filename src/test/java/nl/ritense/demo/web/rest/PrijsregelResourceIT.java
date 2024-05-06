package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PrijsregelAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Prijsregel;
import nl.ritense.demo.repository.PrijsregelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PrijsregelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrijsregelResourceIT {

    private static final BigDecimal DEFAULT_BEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAG = new BigDecimal(2);

    private static final String DEFAULT_CREDIT = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/prijsregels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PrijsregelRepository prijsregelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrijsregelMockMvc;

    private Prijsregel prijsregel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prijsregel createEntity(EntityManager em) {
        Prijsregel prijsregel = new Prijsregel().bedrag(DEFAULT_BEDRAG).credit(DEFAULT_CREDIT);
        return prijsregel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prijsregel createUpdatedEntity(EntityManager em) {
        Prijsregel prijsregel = new Prijsregel().bedrag(UPDATED_BEDRAG).credit(UPDATED_CREDIT);
        return prijsregel;
    }

    @BeforeEach
    public void initTest() {
        prijsregel = createEntity(em);
    }

    @Test
    @Transactional
    void createPrijsregel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Prijsregel
        var returnedPrijsregel = om.readValue(
            restPrijsregelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prijsregel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Prijsregel.class
        );

        // Validate the Prijsregel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPrijsregelUpdatableFieldsEquals(returnedPrijsregel, getPersistedPrijsregel(returnedPrijsregel));
    }

    @Test
    @Transactional
    void createPrijsregelWithExistingId() throws Exception {
        // Create the Prijsregel with an existing ID
        prijsregel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrijsregelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prijsregel)))
            .andExpect(status().isBadRequest());

        // Validate the Prijsregel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrijsregels() throws Exception {
        // Initialize the database
        prijsregelRepository.saveAndFlush(prijsregel);

        // Get all the prijsregelList
        restPrijsregelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prijsregel.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(sameNumber(DEFAULT_BEDRAG))))
            .andExpect(jsonPath("$.[*].credit").value(hasItem(DEFAULT_CREDIT)));
    }

    @Test
    @Transactional
    void getPrijsregel() throws Exception {
        // Initialize the database
        prijsregelRepository.saveAndFlush(prijsregel);

        // Get the prijsregel
        restPrijsregelMockMvc
            .perform(get(ENTITY_API_URL_ID, prijsregel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prijsregel.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(sameNumber(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.credit").value(DEFAULT_CREDIT));
    }

    @Test
    @Transactional
    void getNonExistingPrijsregel() throws Exception {
        // Get the prijsregel
        restPrijsregelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrijsregel() throws Exception {
        // Initialize the database
        prijsregelRepository.saveAndFlush(prijsregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prijsregel
        Prijsregel updatedPrijsregel = prijsregelRepository.findById(prijsregel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPrijsregel are not directly saved in db
        em.detach(updatedPrijsregel);
        updatedPrijsregel.bedrag(UPDATED_BEDRAG).credit(UPDATED_CREDIT);

        restPrijsregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrijsregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPrijsregel))
            )
            .andExpect(status().isOk());

        // Validate the Prijsregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPrijsregelToMatchAllProperties(updatedPrijsregel);
    }

    @Test
    @Transactional
    void putNonExistingPrijsregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijsregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrijsregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prijsregel.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prijsregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijsregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrijsregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijsregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijsregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prijsregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijsregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrijsregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijsregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijsregelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prijsregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prijsregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrijsregelWithPatch() throws Exception {
        // Initialize the database
        prijsregelRepository.saveAndFlush(prijsregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prijsregel using partial update
        Prijsregel partialUpdatedPrijsregel = new Prijsregel();
        partialUpdatedPrijsregel.setId(prijsregel.getId());

        restPrijsregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrijsregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrijsregel))
            )
            .andExpect(status().isOk());

        // Validate the Prijsregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPrijsregelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPrijsregel, prijsregel),
            getPersistedPrijsregel(prijsregel)
        );
    }

    @Test
    @Transactional
    void fullUpdatePrijsregelWithPatch() throws Exception {
        // Initialize the database
        prijsregelRepository.saveAndFlush(prijsregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prijsregel using partial update
        Prijsregel partialUpdatedPrijsregel = new Prijsregel();
        partialUpdatedPrijsregel.setId(prijsregel.getId());

        partialUpdatedPrijsregel.bedrag(UPDATED_BEDRAG).credit(UPDATED_CREDIT);

        restPrijsregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrijsregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrijsregel))
            )
            .andExpect(status().isOk());

        // Validate the Prijsregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPrijsregelUpdatableFieldsEquals(partialUpdatedPrijsregel, getPersistedPrijsregel(partialUpdatedPrijsregel));
    }

    @Test
    @Transactional
    void patchNonExistingPrijsregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijsregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrijsregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prijsregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prijsregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijsregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrijsregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijsregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijsregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prijsregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijsregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrijsregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijsregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijsregelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(prijsregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prijsregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrijsregel() throws Exception {
        // Initialize the database
        prijsregelRepository.saveAndFlush(prijsregel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the prijsregel
        restPrijsregelMockMvc
            .perform(delete(ENTITY_API_URL_ID, prijsregel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return prijsregelRepository.count();
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

    protected Prijsregel getPersistedPrijsregel(Prijsregel prijsregel) {
        return prijsregelRepository.findById(prijsregel.getId()).orElseThrow();
    }

    protected void assertPersistedPrijsregelToMatchAllProperties(Prijsregel expectedPrijsregel) {
        assertPrijsregelAllPropertiesEquals(expectedPrijsregel, getPersistedPrijsregel(expectedPrijsregel));
    }

    protected void assertPersistedPrijsregelToMatchUpdatableProperties(Prijsregel expectedPrijsregel) {
        assertPrijsregelAllUpdatablePropertiesEquals(expectedPrijsregel, getPersistedPrijsregel(expectedPrijsregel));
    }
}
