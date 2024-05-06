package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ParkeerscanAsserts.*;
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
import nl.ritense.demo.domain.Parkeerscan;
import nl.ritense.demo.repository.ParkeerscanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ParkeerscanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParkeerscanResourceIT {

    private static final String DEFAULT_CODEGEBRUIKER = "AAAAAAAAAA";
    private static final String UPDATED_CODEGEBRUIKER = "BBBBBBBBBB";

    private static final String DEFAULT_CODESCANVOERTUIG = "AAAAAAAAAA";
    private static final String UPDATED_CODESCANVOERTUIG = "BBBBBBBBBB";

    private static final String DEFAULT_COORDINATEN = "AAAAAAAAAA";
    private static final String UPDATED_COORDINATEN = "BBBBBBBBBB";

    private static final String DEFAULT_FOTO = "AAAAAAAAAA";
    private static final String UPDATED_FOTO = "BBBBBBBBBB";

    private static final String DEFAULT_KENTEKEN = "AAAAAAAAAA";
    private static final String UPDATED_KENTEKEN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PARKEERRECHT = false;
    private static final Boolean UPDATED_PARKEERRECHT = true;

    private static final String DEFAULT_TIJDSTIP = "AAAAAAAAAA";
    private static final String UPDATED_TIJDSTIP = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTIEID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTIEID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/parkeerscans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ParkeerscanRepository parkeerscanRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParkeerscanMockMvc;

    private Parkeerscan parkeerscan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parkeerscan createEntity(EntityManager em) {
        Parkeerscan parkeerscan = new Parkeerscan()
            .codegebruiker(DEFAULT_CODEGEBRUIKER)
            .codescanvoertuig(DEFAULT_CODESCANVOERTUIG)
            .coordinaten(DEFAULT_COORDINATEN)
            .foto(DEFAULT_FOTO)
            .kenteken(DEFAULT_KENTEKEN)
            .parkeerrecht(DEFAULT_PARKEERRECHT)
            .tijdstip(DEFAULT_TIJDSTIP)
            .transactieid(DEFAULT_TRANSACTIEID);
        return parkeerscan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parkeerscan createUpdatedEntity(EntityManager em) {
        Parkeerscan parkeerscan = new Parkeerscan()
            .codegebruiker(UPDATED_CODEGEBRUIKER)
            .codescanvoertuig(UPDATED_CODESCANVOERTUIG)
            .coordinaten(UPDATED_COORDINATEN)
            .foto(UPDATED_FOTO)
            .kenteken(UPDATED_KENTEKEN)
            .parkeerrecht(UPDATED_PARKEERRECHT)
            .tijdstip(UPDATED_TIJDSTIP)
            .transactieid(UPDATED_TRANSACTIEID);
        return parkeerscan;
    }

    @BeforeEach
    public void initTest() {
        parkeerscan = createEntity(em);
    }

    @Test
    @Transactional
    void createParkeerscan() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Parkeerscan
        var returnedParkeerscan = om.readValue(
            restParkeerscanMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parkeerscan)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Parkeerscan.class
        );

        // Validate the Parkeerscan in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertParkeerscanUpdatableFieldsEquals(returnedParkeerscan, getPersistedParkeerscan(returnedParkeerscan));
    }

    @Test
    @Transactional
    void createParkeerscanWithExistingId() throws Exception {
        // Create the Parkeerscan with an existing ID
        parkeerscan.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParkeerscanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parkeerscan)))
            .andExpect(status().isBadRequest());

        // Validate the Parkeerscan in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParkeerscans() throws Exception {
        // Initialize the database
        parkeerscanRepository.saveAndFlush(parkeerscan);

        // Get all the parkeerscanList
        restParkeerscanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parkeerscan.getId().intValue())))
            .andExpect(jsonPath("$.[*].codegebruiker").value(hasItem(DEFAULT_CODEGEBRUIKER)))
            .andExpect(jsonPath("$.[*].codescanvoertuig").value(hasItem(DEFAULT_CODESCANVOERTUIG)))
            .andExpect(jsonPath("$.[*].coordinaten").value(hasItem(DEFAULT_COORDINATEN)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.[*].kenteken").value(hasItem(DEFAULT_KENTEKEN)))
            .andExpect(jsonPath("$.[*].parkeerrecht").value(hasItem(DEFAULT_PARKEERRECHT.booleanValue())))
            .andExpect(jsonPath("$.[*].tijdstip").value(hasItem(DEFAULT_TIJDSTIP)))
            .andExpect(jsonPath("$.[*].transactieid").value(hasItem(DEFAULT_TRANSACTIEID)));
    }

    @Test
    @Transactional
    void getParkeerscan() throws Exception {
        // Initialize the database
        parkeerscanRepository.saveAndFlush(parkeerscan);

        // Get the parkeerscan
        restParkeerscanMockMvc
            .perform(get(ENTITY_API_URL_ID, parkeerscan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parkeerscan.getId().intValue()))
            .andExpect(jsonPath("$.codegebruiker").value(DEFAULT_CODEGEBRUIKER))
            .andExpect(jsonPath("$.codescanvoertuig").value(DEFAULT_CODESCANVOERTUIG))
            .andExpect(jsonPath("$.coordinaten").value(DEFAULT_COORDINATEN))
            .andExpect(jsonPath("$.foto").value(DEFAULT_FOTO))
            .andExpect(jsonPath("$.kenteken").value(DEFAULT_KENTEKEN))
            .andExpect(jsonPath("$.parkeerrecht").value(DEFAULT_PARKEERRECHT.booleanValue()))
            .andExpect(jsonPath("$.tijdstip").value(DEFAULT_TIJDSTIP))
            .andExpect(jsonPath("$.transactieid").value(DEFAULT_TRANSACTIEID));
    }

    @Test
    @Transactional
    void getNonExistingParkeerscan() throws Exception {
        // Get the parkeerscan
        restParkeerscanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingParkeerscan() throws Exception {
        // Initialize the database
        parkeerscanRepository.saveAndFlush(parkeerscan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parkeerscan
        Parkeerscan updatedParkeerscan = parkeerscanRepository.findById(parkeerscan.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedParkeerscan are not directly saved in db
        em.detach(updatedParkeerscan);
        updatedParkeerscan
            .codegebruiker(UPDATED_CODEGEBRUIKER)
            .codescanvoertuig(UPDATED_CODESCANVOERTUIG)
            .coordinaten(UPDATED_COORDINATEN)
            .foto(UPDATED_FOTO)
            .kenteken(UPDATED_KENTEKEN)
            .parkeerrecht(UPDATED_PARKEERRECHT)
            .tijdstip(UPDATED_TIJDSTIP)
            .transactieid(UPDATED_TRANSACTIEID);

        restParkeerscanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedParkeerscan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedParkeerscan))
            )
            .andExpect(status().isOk());

        // Validate the Parkeerscan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedParkeerscanToMatchAllProperties(updatedParkeerscan);
    }

    @Test
    @Transactional
    void putNonExistingParkeerscan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerscan.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkeerscanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parkeerscan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parkeerscan))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeerscan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParkeerscan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerscan.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeerscanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parkeerscan))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeerscan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParkeerscan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerscan.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeerscanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parkeerscan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parkeerscan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParkeerscanWithPatch() throws Exception {
        // Initialize the database
        parkeerscanRepository.saveAndFlush(parkeerscan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parkeerscan using partial update
        Parkeerscan partialUpdatedParkeerscan = new Parkeerscan();
        partialUpdatedParkeerscan.setId(parkeerscan.getId());

        partialUpdatedParkeerscan.codegebruiker(UPDATED_CODEGEBRUIKER).parkeerrecht(UPDATED_PARKEERRECHT);

        restParkeerscanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParkeerscan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParkeerscan))
            )
            .andExpect(status().isOk());

        // Validate the Parkeerscan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParkeerscanUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedParkeerscan, parkeerscan),
            getPersistedParkeerscan(parkeerscan)
        );
    }

    @Test
    @Transactional
    void fullUpdateParkeerscanWithPatch() throws Exception {
        // Initialize the database
        parkeerscanRepository.saveAndFlush(parkeerscan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parkeerscan using partial update
        Parkeerscan partialUpdatedParkeerscan = new Parkeerscan();
        partialUpdatedParkeerscan.setId(parkeerscan.getId());

        partialUpdatedParkeerscan
            .codegebruiker(UPDATED_CODEGEBRUIKER)
            .codescanvoertuig(UPDATED_CODESCANVOERTUIG)
            .coordinaten(UPDATED_COORDINATEN)
            .foto(UPDATED_FOTO)
            .kenteken(UPDATED_KENTEKEN)
            .parkeerrecht(UPDATED_PARKEERRECHT)
            .tijdstip(UPDATED_TIJDSTIP)
            .transactieid(UPDATED_TRANSACTIEID);

        restParkeerscanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParkeerscan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParkeerscan))
            )
            .andExpect(status().isOk());

        // Validate the Parkeerscan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParkeerscanUpdatableFieldsEquals(partialUpdatedParkeerscan, getPersistedParkeerscan(partialUpdatedParkeerscan));
    }

    @Test
    @Transactional
    void patchNonExistingParkeerscan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerscan.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkeerscanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parkeerscan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parkeerscan))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeerscan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParkeerscan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerscan.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeerscanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parkeerscan))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeerscan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParkeerscan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerscan.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeerscanMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(parkeerscan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parkeerscan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParkeerscan() throws Exception {
        // Initialize the database
        parkeerscanRepository.saveAndFlush(parkeerscan);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the parkeerscan
        restParkeerscanMockMvc
            .perform(delete(ENTITY_API_URL_ID, parkeerscan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return parkeerscanRepository.count();
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

    protected Parkeerscan getPersistedParkeerscan(Parkeerscan parkeerscan) {
        return parkeerscanRepository.findById(parkeerscan.getId()).orElseThrow();
    }

    protected void assertPersistedParkeerscanToMatchAllProperties(Parkeerscan expectedParkeerscan) {
        assertParkeerscanAllPropertiesEquals(expectedParkeerscan, getPersistedParkeerscan(expectedParkeerscan));
    }

    protected void assertPersistedParkeerscanToMatchUpdatableProperties(Parkeerscan expectedParkeerscan) {
        assertParkeerscanAllUpdatablePropertiesEquals(expectedParkeerscan, getPersistedParkeerscan(expectedParkeerscan));
    }
}
