package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WaboaanvraagofmeldingAsserts.*;
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
import nl.ritense.demo.domain.Waboaanvraagofmelding;
import nl.ritense.demo.repository.WaboaanvraagofmeldingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WaboaanvraagofmeldingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WaboaanvraagofmeldingResourceIT {

    private static final BigDecimal DEFAULT_BOUWKOSTEN = new BigDecimal(1);
    private static final BigDecimal UPDATED_BOUWKOSTEN = new BigDecimal(2);

    private static final String DEFAULT_OLONUMMER = "AAAAAAAAAA";
    private static final String UPDATED_OLONUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PROJECTKOSTEN = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROJECTKOSTEN = new BigDecimal(2);

    private static final String DEFAULT_REGISTRATIENUMMER = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATIENUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/waboaanvraagofmeldings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WaboaanvraagofmeldingRepository waboaanvraagofmeldingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWaboaanvraagofmeldingMockMvc;

    private Waboaanvraagofmelding waboaanvraagofmelding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Waboaanvraagofmelding createEntity(EntityManager em) {
        Waboaanvraagofmelding waboaanvraagofmelding = new Waboaanvraagofmelding()
            .bouwkosten(DEFAULT_BOUWKOSTEN)
            .olonummer(DEFAULT_OLONUMMER)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .projectkosten(DEFAULT_PROJECTKOSTEN)
            .registratienummer(DEFAULT_REGISTRATIENUMMER);
        return waboaanvraagofmelding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Waboaanvraagofmelding createUpdatedEntity(EntityManager em) {
        Waboaanvraagofmelding waboaanvraagofmelding = new Waboaanvraagofmelding()
            .bouwkosten(UPDATED_BOUWKOSTEN)
            .olonummer(UPDATED_OLONUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .projectkosten(UPDATED_PROJECTKOSTEN)
            .registratienummer(UPDATED_REGISTRATIENUMMER);
        return waboaanvraagofmelding;
    }

    @BeforeEach
    public void initTest() {
        waboaanvraagofmelding = createEntity(em);
    }

    @Test
    @Transactional
    void createWaboaanvraagofmelding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Waboaanvraagofmelding
        var returnedWaboaanvraagofmelding = om.readValue(
            restWaboaanvraagofmeldingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(waboaanvraagofmelding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Waboaanvraagofmelding.class
        );

        // Validate the Waboaanvraagofmelding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWaboaanvraagofmeldingUpdatableFieldsEquals(
            returnedWaboaanvraagofmelding,
            getPersistedWaboaanvraagofmelding(returnedWaboaanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void createWaboaanvraagofmeldingWithExistingId() throws Exception {
        // Create the Waboaanvraagofmelding with an existing ID
        waboaanvraagofmelding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWaboaanvraagofmeldingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(waboaanvraagofmelding)))
            .andExpect(status().isBadRequest());

        // Validate the Waboaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWaboaanvraagofmeldings() throws Exception {
        // Initialize the database
        waboaanvraagofmeldingRepository.saveAndFlush(waboaanvraagofmelding);

        // Get all the waboaanvraagofmeldingList
        restWaboaanvraagofmeldingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(waboaanvraagofmelding.getId().intValue())))
            .andExpect(jsonPath("$.[*].bouwkosten").value(hasItem(sameNumber(DEFAULT_BOUWKOSTEN))))
            .andExpect(jsonPath("$.[*].olonummer").value(hasItem(DEFAULT_OLONUMMER)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].projectkosten").value(hasItem(sameNumber(DEFAULT_PROJECTKOSTEN))))
            .andExpect(jsonPath("$.[*].registratienummer").value(hasItem(DEFAULT_REGISTRATIENUMMER)));
    }

    @Test
    @Transactional
    void getWaboaanvraagofmelding() throws Exception {
        // Initialize the database
        waboaanvraagofmeldingRepository.saveAndFlush(waboaanvraagofmelding);

        // Get the waboaanvraagofmelding
        restWaboaanvraagofmeldingMockMvc
            .perform(get(ENTITY_API_URL_ID, waboaanvraagofmelding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(waboaanvraagofmelding.getId().intValue()))
            .andExpect(jsonPath("$.bouwkosten").value(sameNumber(DEFAULT_BOUWKOSTEN)))
            .andExpect(jsonPath("$.olonummer").value(DEFAULT_OLONUMMER))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.projectkosten").value(sameNumber(DEFAULT_PROJECTKOSTEN)))
            .andExpect(jsonPath("$.registratienummer").value(DEFAULT_REGISTRATIENUMMER));
    }

    @Test
    @Transactional
    void getNonExistingWaboaanvraagofmelding() throws Exception {
        // Get the waboaanvraagofmelding
        restWaboaanvraagofmeldingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWaboaanvraagofmelding() throws Exception {
        // Initialize the database
        waboaanvraagofmeldingRepository.saveAndFlush(waboaanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the waboaanvraagofmelding
        Waboaanvraagofmelding updatedWaboaanvraagofmelding = waboaanvraagofmeldingRepository
            .findById(waboaanvraagofmelding.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedWaboaanvraagofmelding are not directly saved in db
        em.detach(updatedWaboaanvraagofmelding);
        updatedWaboaanvraagofmelding
            .bouwkosten(UPDATED_BOUWKOSTEN)
            .olonummer(UPDATED_OLONUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .projectkosten(UPDATED_PROJECTKOSTEN)
            .registratienummer(UPDATED_REGISTRATIENUMMER);

        restWaboaanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWaboaanvraagofmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWaboaanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Waboaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWaboaanvraagofmeldingToMatchAllProperties(updatedWaboaanvraagofmelding);
    }

    @Test
    @Transactional
    void putNonExistingWaboaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waboaanvraagofmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWaboaanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, waboaanvraagofmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(waboaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waboaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWaboaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waboaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaboaanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(waboaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waboaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWaboaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waboaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaboaanvraagofmeldingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(waboaanvraagofmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Waboaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWaboaanvraagofmeldingWithPatch() throws Exception {
        // Initialize the database
        waboaanvraagofmeldingRepository.saveAndFlush(waboaanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the waboaanvraagofmelding using partial update
        Waboaanvraagofmelding partialUpdatedWaboaanvraagofmelding = new Waboaanvraagofmelding();
        partialUpdatedWaboaanvraagofmelding.setId(waboaanvraagofmelding.getId());

        restWaboaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWaboaanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWaboaanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Waboaanvraagofmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWaboaanvraagofmeldingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWaboaanvraagofmelding, waboaanvraagofmelding),
            getPersistedWaboaanvraagofmelding(waboaanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void fullUpdateWaboaanvraagofmeldingWithPatch() throws Exception {
        // Initialize the database
        waboaanvraagofmeldingRepository.saveAndFlush(waboaanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the waboaanvraagofmelding using partial update
        Waboaanvraagofmelding partialUpdatedWaboaanvraagofmelding = new Waboaanvraagofmelding();
        partialUpdatedWaboaanvraagofmelding.setId(waboaanvraagofmelding.getId());

        partialUpdatedWaboaanvraagofmelding
            .bouwkosten(UPDATED_BOUWKOSTEN)
            .olonummer(UPDATED_OLONUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .projectkosten(UPDATED_PROJECTKOSTEN)
            .registratienummer(UPDATED_REGISTRATIENUMMER);

        restWaboaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWaboaanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWaboaanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Waboaanvraagofmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWaboaanvraagofmeldingUpdatableFieldsEquals(
            partialUpdatedWaboaanvraagofmelding,
            getPersistedWaboaanvraagofmelding(partialUpdatedWaboaanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void patchNonExistingWaboaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waboaanvraagofmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWaboaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, waboaanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(waboaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waboaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWaboaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waboaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaboaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(waboaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waboaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWaboaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waboaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaboaanvraagofmeldingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(waboaanvraagofmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Waboaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWaboaanvraagofmelding() throws Exception {
        // Initialize the database
        waboaanvraagofmeldingRepository.saveAndFlush(waboaanvraagofmelding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the waboaanvraagofmelding
        restWaboaanvraagofmeldingMockMvc
            .perform(delete(ENTITY_API_URL_ID, waboaanvraagofmelding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return waboaanvraagofmeldingRepository.count();
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

    protected Waboaanvraagofmelding getPersistedWaboaanvraagofmelding(Waboaanvraagofmelding waboaanvraagofmelding) {
        return waboaanvraagofmeldingRepository.findById(waboaanvraagofmelding.getId()).orElseThrow();
    }

    protected void assertPersistedWaboaanvraagofmeldingToMatchAllProperties(Waboaanvraagofmelding expectedWaboaanvraagofmelding) {
        assertWaboaanvraagofmeldingAllPropertiesEquals(
            expectedWaboaanvraagofmelding,
            getPersistedWaboaanvraagofmelding(expectedWaboaanvraagofmelding)
        );
    }

    protected void assertPersistedWaboaanvraagofmeldingToMatchUpdatableProperties(Waboaanvraagofmelding expectedWaboaanvraagofmelding) {
        assertWaboaanvraagofmeldingAllUpdatablePropertiesEquals(
            expectedWaboaanvraagofmelding,
            getPersistedWaboaanvraagofmelding(expectedWaboaanvraagofmelding)
        );
    }
}
