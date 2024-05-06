package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MoormeldingAsserts.*;
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
import nl.ritense.demo.domain.Moormelding;
import nl.ritense.demo.repository.MoormeldingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MoormeldingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MoormeldingResourceIT {

    private static final String DEFAULT_ADRESAANDUIDING = "AAAAAAAAAA";
    private static final String UPDATED_ADRESAANDUIDING = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMAANMELDING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMAANMELDING = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMGOEDKEURING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMGOEDKEURING = "BBBBBBBBBB";

    private static final String DEFAULT_EINDTIJD = "AAAAAAAAAA";
    private static final String UPDATED_EINDTIJD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GOEDGEKEURD = false;
    private static final Boolean UPDATED_GOEDGEKEURD = true;

    private static final Boolean DEFAULT_HERSTELWERKZAAMHEDENVEREIST = false;
    private static final Boolean UPDATED_HERSTELWERKZAAMHEDENVEREIST = true;

    private static final String DEFAULT_OMSCHRIJVINGHERSTELWERKZAAMHEDEN = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVINGHERSTELWERKZAAMHEDEN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PUBLICEREN = false;
    private static final Boolean UPDATED_PUBLICEREN = true;

    private static final String DEFAULT_STARTTIJD = "AAAAAAAAAA";
    private static final String UPDATED_STARTTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_WEGBEHEERDER = "AAAAAAAAAA";
    private static final String UPDATED_WEGBEHEERDER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/moormeldings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MoormeldingRepository moormeldingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMoormeldingMockMvc;

    private Moormelding moormelding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Moormelding createEntity(EntityManager em) {
        Moormelding moormelding = new Moormelding()
            .adresaanduiding(DEFAULT_ADRESAANDUIDING)
            .datumaanmelding(DEFAULT_DATUMAANMELDING)
            .datumgoedkeuring(DEFAULT_DATUMGOEDKEURING)
            .eindtijd(DEFAULT_EINDTIJD)
            .goedgekeurd(DEFAULT_GOEDGEKEURD)
            .herstelwerkzaamhedenvereist(DEFAULT_HERSTELWERKZAAMHEDENVEREIST)
            .omschrijvingherstelwerkzaamheden(DEFAULT_OMSCHRIJVINGHERSTELWERKZAAMHEDEN)
            .publiceren(DEFAULT_PUBLICEREN)
            .starttijd(DEFAULT_STARTTIJD)
            .wegbeheerder(DEFAULT_WEGBEHEERDER);
        return moormelding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Moormelding createUpdatedEntity(EntityManager em) {
        Moormelding moormelding = new Moormelding()
            .adresaanduiding(UPDATED_ADRESAANDUIDING)
            .datumaanmelding(UPDATED_DATUMAANMELDING)
            .datumgoedkeuring(UPDATED_DATUMGOEDKEURING)
            .eindtijd(UPDATED_EINDTIJD)
            .goedgekeurd(UPDATED_GOEDGEKEURD)
            .herstelwerkzaamhedenvereist(UPDATED_HERSTELWERKZAAMHEDENVEREIST)
            .omschrijvingherstelwerkzaamheden(UPDATED_OMSCHRIJVINGHERSTELWERKZAAMHEDEN)
            .publiceren(UPDATED_PUBLICEREN)
            .starttijd(UPDATED_STARTTIJD)
            .wegbeheerder(UPDATED_WEGBEHEERDER);
        return moormelding;
    }

    @BeforeEach
    public void initTest() {
        moormelding = createEntity(em);
    }

    @Test
    @Transactional
    void createMoormelding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Moormelding
        var returnedMoormelding = om.readValue(
            restMoormeldingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(moormelding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Moormelding.class
        );

        // Validate the Moormelding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMoormeldingUpdatableFieldsEquals(returnedMoormelding, getPersistedMoormelding(returnedMoormelding));
    }

    @Test
    @Transactional
    void createMoormeldingWithExistingId() throws Exception {
        // Create the Moormelding with an existing ID
        moormelding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoormeldingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(moormelding)))
            .andExpect(status().isBadRequest());

        // Validate the Moormelding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMoormeldings() throws Exception {
        // Initialize the database
        moormeldingRepository.saveAndFlush(moormelding);

        // Get all the moormeldingList
        restMoormeldingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moormelding.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresaanduiding").value(hasItem(DEFAULT_ADRESAANDUIDING)))
            .andExpect(jsonPath("$.[*].datumaanmelding").value(hasItem(DEFAULT_DATUMAANMELDING)))
            .andExpect(jsonPath("$.[*].datumgoedkeuring").value(hasItem(DEFAULT_DATUMGOEDKEURING)))
            .andExpect(jsonPath("$.[*].eindtijd").value(hasItem(DEFAULT_EINDTIJD)))
            .andExpect(jsonPath("$.[*].goedgekeurd").value(hasItem(DEFAULT_GOEDGEKEURD.booleanValue())))
            .andExpect(jsonPath("$.[*].herstelwerkzaamhedenvereist").value(hasItem(DEFAULT_HERSTELWERKZAAMHEDENVEREIST.booleanValue())))
            .andExpect(jsonPath("$.[*].omschrijvingherstelwerkzaamheden").value(hasItem(DEFAULT_OMSCHRIJVINGHERSTELWERKZAAMHEDEN)))
            .andExpect(jsonPath("$.[*].publiceren").value(hasItem(DEFAULT_PUBLICEREN.booleanValue())))
            .andExpect(jsonPath("$.[*].starttijd").value(hasItem(DEFAULT_STARTTIJD)))
            .andExpect(jsonPath("$.[*].wegbeheerder").value(hasItem(DEFAULT_WEGBEHEERDER)));
    }

    @Test
    @Transactional
    void getMoormelding() throws Exception {
        // Initialize the database
        moormeldingRepository.saveAndFlush(moormelding);

        // Get the moormelding
        restMoormeldingMockMvc
            .perform(get(ENTITY_API_URL_ID, moormelding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(moormelding.getId().intValue()))
            .andExpect(jsonPath("$.adresaanduiding").value(DEFAULT_ADRESAANDUIDING))
            .andExpect(jsonPath("$.datumaanmelding").value(DEFAULT_DATUMAANMELDING))
            .andExpect(jsonPath("$.datumgoedkeuring").value(DEFAULT_DATUMGOEDKEURING))
            .andExpect(jsonPath("$.eindtijd").value(DEFAULT_EINDTIJD))
            .andExpect(jsonPath("$.goedgekeurd").value(DEFAULT_GOEDGEKEURD.booleanValue()))
            .andExpect(jsonPath("$.herstelwerkzaamhedenvereist").value(DEFAULT_HERSTELWERKZAAMHEDENVEREIST.booleanValue()))
            .andExpect(jsonPath("$.omschrijvingherstelwerkzaamheden").value(DEFAULT_OMSCHRIJVINGHERSTELWERKZAAMHEDEN))
            .andExpect(jsonPath("$.publiceren").value(DEFAULT_PUBLICEREN.booleanValue()))
            .andExpect(jsonPath("$.starttijd").value(DEFAULT_STARTTIJD))
            .andExpect(jsonPath("$.wegbeheerder").value(DEFAULT_WEGBEHEERDER));
    }

    @Test
    @Transactional
    void getNonExistingMoormelding() throws Exception {
        // Get the moormelding
        restMoormeldingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMoormelding() throws Exception {
        // Initialize the database
        moormeldingRepository.saveAndFlush(moormelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the moormelding
        Moormelding updatedMoormelding = moormeldingRepository.findById(moormelding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMoormelding are not directly saved in db
        em.detach(updatedMoormelding);
        updatedMoormelding
            .adresaanduiding(UPDATED_ADRESAANDUIDING)
            .datumaanmelding(UPDATED_DATUMAANMELDING)
            .datumgoedkeuring(UPDATED_DATUMGOEDKEURING)
            .eindtijd(UPDATED_EINDTIJD)
            .goedgekeurd(UPDATED_GOEDGEKEURD)
            .herstelwerkzaamhedenvereist(UPDATED_HERSTELWERKZAAMHEDENVEREIST)
            .omschrijvingherstelwerkzaamheden(UPDATED_OMSCHRIJVINGHERSTELWERKZAAMHEDEN)
            .publiceren(UPDATED_PUBLICEREN)
            .starttijd(UPDATED_STARTTIJD)
            .wegbeheerder(UPDATED_WEGBEHEERDER);

        restMoormeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMoormelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMoormelding))
            )
            .andExpect(status().isOk());

        // Validate the Moormelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMoormeldingToMatchAllProperties(updatedMoormelding);
    }

    @Test
    @Transactional
    void putNonExistingMoormelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moormelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoormeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moormelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(moormelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Moormelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMoormelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moormelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoormeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(moormelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Moormelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMoormelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moormelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoormeldingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(moormelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Moormelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMoormeldingWithPatch() throws Exception {
        // Initialize the database
        moormeldingRepository.saveAndFlush(moormelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the moormelding using partial update
        Moormelding partialUpdatedMoormelding = new Moormelding();
        partialUpdatedMoormelding.setId(moormelding.getId());

        partialUpdatedMoormelding
            .datumgoedkeuring(UPDATED_DATUMGOEDKEURING)
            .herstelwerkzaamhedenvereist(UPDATED_HERSTELWERKZAAMHEDENVEREIST)
            .omschrijvingherstelwerkzaamheden(UPDATED_OMSCHRIJVINGHERSTELWERKZAAMHEDEN);

        restMoormeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMoormelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMoormelding))
            )
            .andExpect(status().isOk());

        // Validate the Moormelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMoormeldingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMoormelding, moormelding),
            getPersistedMoormelding(moormelding)
        );
    }

    @Test
    @Transactional
    void fullUpdateMoormeldingWithPatch() throws Exception {
        // Initialize the database
        moormeldingRepository.saveAndFlush(moormelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the moormelding using partial update
        Moormelding partialUpdatedMoormelding = new Moormelding();
        partialUpdatedMoormelding.setId(moormelding.getId());

        partialUpdatedMoormelding
            .adresaanduiding(UPDATED_ADRESAANDUIDING)
            .datumaanmelding(UPDATED_DATUMAANMELDING)
            .datumgoedkeuring(UPDATED_DATUMGOEDKEURING)
            .eindtijd(UPDATED_EINDTIJD)
            .goedgekeurd(UPDATED_GOEDGEKEURD)
            .herstelwerkzaamhedenvereist(UPDATED_HERSTELWERKZAAMHEDENVEREIST)
            .omschrijvingherstelwerkzaamheden(UPDATED_OMSCHRIJVINGHERSTELWERKZAAMHEDEN)
            .publiceren(UPDATED_PUBLICEREN)
            .starttijd(UPDATED_STARTTIJD)
            .wegbeheerder(UPDATED_WEGBEHEERDER);

        restMoormeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMoormelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMoormelding))
            )
            .andExpect(status().isOk());

        // Validate the Moormelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMoormeldingUpdatableFieldsEquals(partialUpdatedMoormelding, getPersistedMoormelding(partialUpdatedMoormelding));
    }

    @Test
    @Transactional
    void patchNonExistingMoormelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moormelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoormeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, moormelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(moormelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Moormelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMoormelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moormelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoormeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(moormelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Moormelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMoormelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        moormelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoormeldingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(moormelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Moormelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMoormelding() throws Exception {
        // Initialize the database
        moormeldingRepository.saveAndFlush(moormelding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the moormelding
        restMoormeldingMockMvc
            .perform(delete(ENTITY_API_URL_ID, moormelding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return moormeldingRepository.count();
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

    protected Moormelding getPersistedMoormelding(Moormelding moormelding) {
        return moormeldingRepository.findById(moormelding.getId()).orElseThrow();
    }

    protected void assertPersistedMoormeldingToMatchAllProperties(Moormelding expectedMoormelding) {
        assertMoormeldingAllPropertiesEquals(expectedMoormelding, getPersistedMoormelding(expectedMoormelding));
    }

    protected void assertPersistedMoormeldingToMatchUpdatableProperties(Moormelding expectedMoormelding) {
        assertMoormeldingAllUpdatablePropertiesEquals(expectedMoormelding, getPersistedMoormelding(expectedMoormelding));
    }
}
