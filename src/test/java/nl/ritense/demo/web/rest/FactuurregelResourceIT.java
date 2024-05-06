package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FactuurregelAsserts.*;
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
import nl.ritense.demo.domain.Factuurregel;
import nl.ritense.demo.repository.FactuurregelRepository;
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
 * Integration tests for the {@link FactuurregelResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class FactuurregelResourceIT {

    private static final String DEFAULT_AANTAL = "AAAAAAAAAA";
    private static final String UPDATED_AANTAL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BEDRAGBTW = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAGBTW = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BEDRAGEXBTW = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAGEXBTW = new BigDecimal(2);

    private static final String DEFAULT_BTWPERCENTAGE = "AAAAAAAAAA";
    private static final String UPDATED_BTWPERCENTAGE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/factuurregels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FactuurregelRepository factuurregelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFactuurregelMockMvc;

    private Factuurregel factuurregel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Factuurregel createEntity(EntityManager em) {
        Factuurregel factuurregel = new Factuurregel()
            .aantal(DEFAULT_AANTAL)
            .bedragbtw(DEFAULT_BEDRAGBTW)
            .bedragexbtw(DEFAULT_BEDRAGEXBTW)
            .btwpercentage(DEFAULT_BTWPERCENTAGE)
            .nummer(DEFAULT_NUMMER)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return factuurregel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Factuurregel createUpdatedEntity(EntityManager em) {
        Factuurregel factuurregel = new Factuurregel()
            .aantal(UPDATED_AANTAL)
            .bedragbtw(UPDATED_BEDRAGBTW)
            .bedragexbtw(UPDATED_BEDRAGEXBTW)
            .btwpercentage(UPDATED_BTWPERCENTAGE)
            .nummer(UPDATED_NUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return factuurregel;
    }

    @BeforeEach
    public void initTest() {
        factuurregel = createEntity(em);
    }

    @Test
    @Transactional
    void createFactuurregel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Factuurregel
        var returnedFactuurregel = om.readValue(
            restFactuurregelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(factuurregel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Factuurregel.class
        );

        // Validate the Factuurregel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFactuurregelUpdatableFieldsEquals(returnedFactuurregel, getPersistedFactuurregel(returnedFactuurregel));
    }

    @Test
    @Transactional
    void createFactuurregelWithExistingId() throws Exception {
        // Create the Factuurregel with an existing ID
        factuurregel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactuurregelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(factuurregel)))
            .andExpect(status().isBadRequest());

        // Validate the Factuurregel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFactuurregels() throws Exception {
        // Initialize the database
        factuurregelRepository.saveAndFlush(factuurregel);

        // Get all the factuurregelList
        restFactuurregelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factuurregel.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantal").value(hasItem(DEFAULT_AANTAL)))
            .andExpect(jsonPath("$.[*].bedragbtw").value(hasItem(sameNumber(DEFAULT_BEDRAGBTW))))
            .andExpect(jsonPath("$.[*].bedragexbtw").value(hasItem(sameNumber(DEFAULT_BEDRAGEXBTW))))
            .andExpect(jsonPath("$.[*].btwpercentage").value(hasItem(DEFAULT_BTWPERCENTAGE)))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getFactuurregel() throws Exception {
        // Initialize the database
        factuurregelRepository.saveAndFlush(factuurregel);

        // Get the factuurregel
        restFactuurregelMockMvc
            .perform(get(ENTITY_API_URL_ID, factuurregel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(factuurregel.getId().intValue()))
            .andExpect(jsonPath("$.aantal").value(DEFAULT_AANTAL))
            .andExpect(jsonPath("$.bedragbtw").value(sameNumber(DEFAULT_BEDRAGBTW)))
            .andExpect(jsonPath("$.bedragexbtw").value(sameNumber(DEFAULT_BEDRAGEXBTW)))
            .andExpect(jsonPath("$.btwpercentage").value(DEFAULT_BTWPERCENTAGE))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingFactuurregel() throws Exception {
        // Get the factuurregel
        restFactuurregelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFactuurregel() throws Exception {
        // Initialize the database
        factuurregelRepository.saveAndFlush(factuurregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the factuurregel
        Factuurregel updatedFactuurregel = factuurregelRepository.findById(factuurregel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFactuurregel are not directly saved in db
        em.detach(updatedFactuurregel);
        updatedFactuurregel
            .aantal(UPDATED_AANTAL)
            .bedragbtw(UPDATED_BEDRAGBTW)
            .bedragexbtw(UPDATED_BEDRAGEXBTW)
            .btwpercentage(UPDATED_BTWPERCENTAGE)
            .nummer(UPDATED_NUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restFactuurregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFactuurregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFactuurregel))
            )
            .andExpect(status().isOk());

        // Validate the Factuurregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFactuurregelToMatchAllProperties(updatedFactuurregel);
    }

    @Test
    @Transactional
    void putNonExistingFactuurregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factuurregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactuurregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, factuurregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(factuurregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Factuurregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFactuurregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factuurregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactuurregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(factuurregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Factuurregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFactuurregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factuurregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactuurregelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(factuurregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Factuurregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFactuurregelWithPatch() throws Exception {
        // Initialize the database
        factuurregelRepository.saveAndFlush(factuurregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the factuurregel using partial update
        Factuurregel partialUpdatedFactuurregel = new Factuurregel();
        partialUpdatedFactuurregel.setId(factuurregel.getId());

        partialUpdatedFactuurregel
            .bedragbtw(UPDATED_BEDRAGBTW)
            .bedragexbtw(UPDATED_BEDRAGEXBTW)
            .btwpercentage(UPDATED_BTWPERCENTAGE)
            .nummer(UPDATED_NUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restFactuurregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactuurregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFactuurregel))
            )
            .andExpect(status().isOk());

        // Validate the Factuurregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFactuurregelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFactuurregel, factuurregel),
            getPersistedFactuurregel(factuurregel)
        );
    }

    @Test
    @Transactional
    void fullUpdateFactuurregelWithPatch() throws Exception {
        // Initialize the database
        factuurregelRepository.saveAndFlush(factuurregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the factuurregel using partial update
        Factuurregel partialUpdatedFactuurregel = new Factuurregel();
        partialUpdatedFactuurregel.setId(factuurregel.getId());

        partialUpdatedFactuurregel
            .aantal(UPDATED_AANTAL)
            .bedragbtw(UPDATED_BEDRAGBTW)
            .bedragexbtw(UPDATED_BEDRAGEXBTW)
            .btwpercentage(UPDATED_BTWPERCENTAGE)
            .nummer(UPDATED_NUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restFactuurregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactuurregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFactuurregel))
            )
            .andExpect(status().isOk());

        // Validate the Factuurregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFactuurregelUpdatableFieldsEquals(partialUpdatedFactuurregel, getPersistedFactuurregel(partialUpdatedFactuurregel));
    }

    @Test
    @Transactional
    void patchNonExistingFactuurregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factuurregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactuurregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, factuurregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(factuurregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Factuurregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFactuurregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factuurregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactuurregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(factuurregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Factuurregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFactuurregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        factuurregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactuurregelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(factuurregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Factuurregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFactuurregel() throws Exception {
        // Initialize the database
        factuurregelRepository.saveAndFlush(factuurregel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the factuurregel
        restFactuurregelMockMvc
            .perform(delete(ENTITY_API_URL_ID, factuurregel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return factuurregelRepository.count();
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

    protected Factuurregel getPersistedFactuurregel(Factuurregel factuurregel) {
        return factuurregelRepository.findById(factuurregel.getId()).orElseThrow();
    }

    protected void assertPersistedFactuurregelToMatchAllProperties(Factuurregel expectedFactuurregel) {
        assertFactuurregelAllPropertiesEquals(expectedFactuurregel, getPersistedFactuurregel(expectedFactuurregel));
    }

    protected void assertPersistedFactuurregelToMatchUpdatableProperties(Factuurregel expectedFactuurregel) {
        assertFactuurregelAllUpdatablePropertiesEquals(expectedFactuurregel, getPersistedFactuurregel(expectedFactuurregel));
    }
}
