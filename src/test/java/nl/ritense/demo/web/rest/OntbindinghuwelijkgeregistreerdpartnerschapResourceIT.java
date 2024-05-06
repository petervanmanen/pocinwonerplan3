package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OntbindinghuwelijkgeregistreerdpartnerschapAsserts.*;
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
import nl.ritense.demo.domain.Ontbindinghuwelijkgeregistreerdpartnerschap;
import nl.ritense.demo.repository.OntbindinghuwelijkgeregistreerdpartnerschapRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OntbindinghuwelijkgeregistreerdpartnerschapResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OntbindinghuwelijkgeregistreerdpartnerschapResourceIT {

    private static final String DEFAULT_BUITENLANDSEPLAATSEINDE = "AAAAAAAAAA";
    private static final String UPDATED_BUITENLANDSEPLAATSEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_BUITENLANDSEREGIOEINDE = "AAAAAAAAAA";
    private static final String UPDATED_BUITENLANDSEREGIOEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_GEMEENTEEINDE = "AAAAAAAAAA";
    private static final String UPDATED_GEMEENTEEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_LANDOFGEBIEDEINDE = "AAAAAAAAAA";
    private static final String UPDATED_LANDOFGEBIEDEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVINGLOCATIEEINDE = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVINGLOCATIEEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_REDENEINDE = "AAAAAAAAAA";
    private static final String UPDATED_REDENEINDE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ontbindinghuwelijkgeregistreerdpartnerschaps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OntbindinghuwelijkgeregistreerdpartnerschapRepository ontbindinghuwelijkgeregistreerdpartnerschapRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOntbindinghuwelijkgeregistreerdpartnerschapMockMvc;

    private Ontbindinghuwelijkgeregistreerdpartnerschap ontbindinghuwelijkgeregistreerdpartnerschap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ontbindinghuwelijkgeregistreerdpartnerschap createEntity(EntityManager em) {
        Ontbindinghuwelijkgeregistreerdpartnerschap ontbindinghuwelijkgeregistreerdpartnerschap =
            new Ontbindinghuwelijkgeregistreerdpartnerschap()
                .buitenlandseplaatseinde(DEFAULT_BUITENLANDSEPLAATSEINDE)
                .buitenlandseregioeinde(DEFAULT_BUITENLANDSEREGIOEINDE)
                .datumeinde(DEFAULT_DATUMEINDE)
                .gemeenteeinde(DEFAULT_GEMEENTEEINDE)
                .landofgebiedeinde(DEFAULT_LANDOFGEBIEDEINDE)
                .omschrijvinglocatieeinde(DEFAULT_OMSCHRIJVINGLOCATIEEINDE)
                .redeneinde(DEFAULT_REDENEINDE);
        return ontbindinghuwelijkgeregistreerdpartnerschap;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ontbindinghuwelijkgeregistreerdpartnerschap createUpdatedEntity(EntityManager em) {
        Ontbindinghuwelijkgeregistreerdpartnerschap ontbindinghuwelijkgeregistreerdpartnerschap =
            new Ontbindinghuwelijkgeregistreerdpartnerschap()
                .buitenlandseplaatseinde(UPDATED_BUITENLANDSEPLAATSEINDE)
                .buitenlandseregioeinde(UPDATED_BUITENLANDSEREGIOEINDE)
                .datumeinde(UPDATED_DATUMEINDE)
                .gemeenteeinde(UPDATED_GEMEENTEEINDE)
                .landofgebiedeinde(UPDATED_LANDOFGEBIEDEINDE)
                .omschrijvinglocatieeinde(UPDATED_OMSCHRIJVINGLOCATIEEINDE)
                .redeneinde(UPDATED_REDENEINDE);
        return ontbindinghuwelijkgeregistreerdpartnerschap;
    }

    @BeforeEach
    public void initTest() {
        ontbindinghuwelijkgeregistreerdpartnerschap = createEntity(em);
    }

    @Test
    @Transactional
    void createOntbindinghuwelijkgeregistreerdpartnerschap() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ontbindinghuwelijkgeregistreerdpartnerschap
        var returnedOntbindinghuwelijkgeregistreerdpartnerschap = om.readValue(
            restOntbindinghuwelijkgeregistreerdpartnerschapMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(ontbindinghuwelijkgeregistreerdpartnerschap))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Ontbindinghuwelijkgeregistreerdpartnerschap.class
        );

        // Validate the Ontbindinghuwelijkgeregistreerdpartnerschap in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOntbindinghuwelijkgeregistreerdpartnerschapUpdatableFieldsEquals(
            returnedOntbindinghuwelijkgeregistreerdpartnerschap,
            getPersistedOntbindinghuwelijkgeregistreerdpartnerschap(returnedOntbindinghuwelijkgeregistreerdpartnerschap)
        );
    }

    @Test
    @Transactional
    void createOntbindinghuwelijkgeregistreerdpartnerschapWithExistingId() throws Exception {
        // Create the Ontbindinghuwelijkgeregistreerdpartnerschap with an existing ID
        ontbindinghuwelijkgeregistreerdpartnerschap.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOntbindinghuwelijkgeregistreerdpartnerschapMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ontbindinghuwelijkgeregistreerdpartnerschap))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ontbindinghuwelijkgeregistreerdpartnerschap in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOntbindinghuwelijkgeregistreerdpartnerschaps() throws Exception {
        // Initialize the database
        ontbindinghuwelijkgeregistreerdpartnerschapRepository.saveAndFlush(ontbindinghuwelijkgeregistreerdpartnerschap);

        // Get all the ontbindinghuwelijkgeregistreerdpartnerschapList
        restOntbindinghuwelijkgeregistreerdpartnerschapMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ontbindinghuwelijkgeregistreerdpartnerschap.getId().intValue())))
            .andExpect(jsonPath("$.[*].buitenlandseplaatseinde").value(hasItem(DEFAULT_BUITENLANDSEPLAATSEINDE)))
            .andExpect(jsonPath("$.[*].buitenlandseregioeinde").value(hasItem(DEFAULT_BUITENLANDSEREGIOEINDE)))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE)))
            .andExpect(jsonPath("$.[*].gemeenteeinde").value(hasItem(DEFAULT_GEMEENTEEINDE)))
            .andExpect(jsonPath("$.[*].landofgebiedeinde").value(hasItem(DEFAULT_LANDOFGEBIEDEINDE)))
            .andExpect(jsonPath("$.[*].omschrijvinglocatieeinde").value(hasItem(DEFAULT_OMSCHRIJVINGLOCATIEEINDE)))
            .andExpect(jsonPath("$.[*].redeneinde").value(hasItem(DEFAULT_REDENEINDE)));
    }

    @Test
    @Transactional
    void getOntbindinghuwelijkgeregistreerdpartnerschap() throws Exception {
        // Initialize the database
        ontbindinghuwelijkgeregistreerdpartnerschapRepository.saveAndFlush(ontbindinghuwelijkgeregistreerdpartnerschap);

        // Get the ontbindinghuwelijkgeregistreerdpartnerschap
        restOntbindinghuwelijkgeregistreerdpartnerschapMockMvc
            .perform(get(ENTITY_API_URL_ID, ontbindinghuwelijkgeregistreerdpartnerschap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ontbindinghuwelijkgeregistreerdpartnerschap.getId().intValue()))
            .andExpect(jsonPath("$.buitenlandseplaatseinde").value(DEFAULT_BUITENLANDSEPLAATSEINDE))
            .andExpect(jsonPath("$.buitenlandseregioeinde").value(DEFAULT_BUITENLANDSEREGIOEINDE))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE))
            .andExpect(jsonPath("$.gemeenteeinde").value(DEFAULT_GEMEENTEEINDE))
            .andExpect(jsonPath("$.landofgebiedeinde").value(DEFAULT_LANDOFGEBIEDEINDE))
            .andExpect(jsonPath("$.omschrijvinglocatieeinde").value(DEFAULT_OMSCHRIJVINGLOCATIEEINDE))
            .andExpect(jsonPath("$.redeneinde").value(DEFAULT_REDENEINDE));
    }

    @Test
    @Transactional
    void getNonExistingOntbindinghuwelijkgeregistreerdpartnerschap() throws Exception {
        // Get the ontbindinghuwelijkgeregistreerdpartnerschap
        restOntbindinghuwelijkgeregistreerdpartnerschapMockMvc
            .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOntbindinghuwelijkgeregistreerdpartnerschap() throws Exception {
        // Initialize the database
        ontbindinghuwelijkgeregistreerdpartnerschapRepository.saveAndFlush(ontbindinghuwelijkgeregistreerdpartnerschap);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ontbindinghuwelijkgeregistreerdpartnerschap
        Ontbindinghuwelijkgeregistreerdpartnerschap updatedOntbindinghuwelijkgeregistreerdpartnerschap =
            ontbindinghuwelijkgeregistreerdpartnerschapRepository
                .findById(ontbindinghuwelijkgeregistreerdpartnerschap.getId())
                .orElseThrow();
        // Disconnect from session so that the updates on updatedOntbindinghuwelijkgeregistreerdpartnerschap are not directly saved in db
        em.detach(updatedOntbindinghuwelijkgeregistreerdpartnerschap);
        updatedOntbindinghuwelijkgeregistreerdpartnerschap
            .buitenlandseplaatseinde(UPDATED_BUITENLANDSEPLAATSEINDE)
            .buitenlandseregioeinde(UPDATED_BUITENLANDSEREGIOEINDE)
            .datumeinde(UPDATED_DATUMEINDE)
            .gemeenteeinde(UPDATED_GEMEENTEEINDE)
            .landofgebiedeinde(UPDATED_LANDOFGEBIEDEINDE)
            .omschrijvinglocatieeinde(UPDATED_OMSCHRIJVINGLOCATIEEINDE)
            .redeneinde(UPDATED_REDENEINDE);

        restOntbindinghuwelijkgeregistreerdpartnerschapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOntbindinghuwelijkgeregistreerdpartnerschap.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOntbindinghuwelijkgeregistreerdpartnerschap))
            )
            .andExpect(status().isOk());

        // Validate the Ontbindinghuwelijkgeregistreerdpartnerschap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOntbindinghuwelijkgeregistreerdpartnerschapToMatchAllProperties(updatedOntbindinghuwelijkgeregistreerdpartnerschap);
    }

    @Test
    @Transactional
    void putNonExistingOntbindinghuwelijkgeregistreerdpartnerschap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ontbindinghuwelijkgeregistreerdpartnerschap.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOntbindinghuwelijkgeregistreerdpartnerschapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ontbindinghuwelijkgeregistreerdpartnerschap.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ontbindinghuwelijkgeregistreerdpartnerschap))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ontbindinghuwelijkgeregistreerdpartnerschap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOntbindinghuwelijkgeregistreerdpartnerschap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ontbindinghuwelijkgeregistreerdpartnerschap.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOntbindinghuwelijkgeregistreerdpartnerschapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ontbindinghuwelijkgeregistreerdpartnerschap))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ontbindinghuwelijkgeregistreerdpartnerschap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOntbindinghuwelijkgeregistreerdpartnerschap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ontbindinghuwelijkgeregistreerdpartnerschap.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOntbindinghuwelijkgeregistreerdpartnerschapMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ontbindinghuwelijkgeregistreerdpartnerschap))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ontbindinghuwelijkgeregistreerdpartnerschap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOntbindinghuwelijkgeregistreerdpartnerschapWithPatch() throws Exception {
        // Initialize the database
        ontbindinghuwelijkgeregistreerdpartnerschapRepository.saveAndFlush(ontbindinghuwelijkgeregistreerdpartnerschap);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ontbindinghuwelijkgeregistreerdpartnerschap using partial update
        Ontbindinghuwelijkgeregistreerdpartnerschap partialUpdatedOntbindinghuwelijkgeregistreerdpartnerschap =
            new Ontbindinghuwelijkgeregistreerdpartnerschap();
        partialUpdatedOntbindinghuwelijkgeregistreerdpartnerschap.setId(ontbindinghuwelijkgeregistreerdpartnerschap.getId());

        partialUpdatedOntbindinghuwelijkgeregistreerdpartnerschap
            .buitenlandseregioeinde(UPDATED_BUITENLANDSEREGIOEINDE)
            .landofgebiedeinde(UPDATED_LANDOFGEBIEDEINDE)
            .redeneinde(UPDATED_REDENEINDE);

        restOntbindinghuwelijkgeregistreerdpartnerschapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOntbindinghuwelijkgeregistreerdpartnerschap.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOntbindinghuwelijkgeregistreerdpartnerschap))
            )
            .andExpect(status().isOk());

        // Validate the Ontbindinghuwelijkgeregistreerdpartnerschap in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOntbindinghuwelijkgeregistreerdpartnerschapUpdatableFieldsEquals(
            createUpdateProxyForBean(
                partialUpdatedOntbindinghuwelijkgeregistreerdpartnerschap,
                ontbindinghuwelijkgeregistreerdpartnerschap
            ),
            getPersistedOntbindinghuwelijkgeregistreerdpartnerschap(ontbindinghuwelijkgeregistreerdpartnerschap)
        );
    }

    @Test
    @Transactional
    void fullUpdateOntbindinghuwelijkgeregistreerdpartnerschapWithPatch() throws Exception {
        // Initialize the database
        ontbindinghuwelijkgeregistreerdpartnerschapRepository.saveAndFlush(ontbindinghuwelijkgeregistreerdpartnerschap);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ontbindinghuwelijkgeregistreerdpartnerschap using partial update
        Ontbindinghuwelijkgeregistreerdpartnerschap partialUpdatedOntbindinghuwelijkgeregistreerdpartnerschap =
            new Ontbindinghuwelijkgeregistreerdpartnerschap();
        partialUpdatedOntbindinghuwelijkgeregistreerdpartnerschap.setId(ontbindinghuwelijkgeregistreerdpartnerschap.getId());

        partialUpdatedOntbindinghuwelijkgeregistreerdpartnerschap
            .buitenlandseplaatseinde(UPDATED_BUITENLANDSEPLAATSEINDE)
            .buitenlandseregioeinde(UPDATED_BUITENLANDSEREGIOEINDE)
            .datumeinde(UPDATED_DATUMEINDE)
            .gemeenteeinde(UPDATED_GEMEENTEEINDE)
            .landofgebiedeinde(UPDATED_LANDOFGEBIEDEINDE)
            .omschrijvinglocatieeinde(UPDATED_OMSCHRIJVINGLOCATIEEINDE)
            .redeneinde(UPDATED_REDENEINDE);

        restOntbindinghuwelijkgeregistreerdpartnerschapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOntbindinghuwelijkgeregistreerdpartnerschap.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOntbindinghuwelijkgeregistreerdpartnerschap))
            )
            .andExpect(status().isOk());

        // Validate the Ontbindinghuwelijkgeregistreerdpartnerschap in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOntbindinghuwelijkgeregistreerdpartnerschapUpdatableFieldsEquals(
            partialUpdatedOntbindinghuwelijkgeregistreerdpartnerschap,
            getPersistedOntbindinghuwelijkgeregistreerdpartnerschap(partialUpdatedOntbindinghuwelijkgeregistreerdpartnerschap)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOntbindinghuwelijkgeregistreerdpartnerschap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ontbindinghuwelijkgeregistreerdpartnerschap.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOntbindinghuwelijkgeregistreerdpartnerschapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ontbindinghuwelijkgeregistreerdpartnerschap.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ontbindinghuwelijkgeregistreerdpartnerschap))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ontbindinghuwelijkgeregistreerdpartnerschap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOntbindinghuwelijkgeregistreerdpartnerschap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ontbindinghuwelijkgeregistreerdpartnerschap.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOntbindinghuwelijkgeregistreerdpartnerschapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ontbindinghuwelijkgeregistreerdpartnerschap))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ontbindinghuwelijkgeregistreerdpartnerschap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOntbindinghuwelijkgeregistreerdpartnerschap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ontbindinghuwelijkgeregistreerdpartnerschap.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOntbindinghuwelijkgeregistreerdpartnerschapMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ontbindinghuwelijkgeregistreerdpartnerschap))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ontbindinghuwelijkgeregistreerdpartnerschap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOntbindinghuwelijkgeregistreerdpartnerschap() throws Exception {
        // Initialize the database
        ontbindinghuwelijkgeregistreerdpartnerschapRepository.saveAndFlush(ontbindinghuwelijkgeregistreerdpartnerschap);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ontbindinghuwelijkgeregistreerdpartnerschap
        restOntbindinghuwelijkgeregistreerdpartnerschapMockMvc
            .perform(delete(ENTITY_API_URL_ID, ontbindinghuwelijkgeregistreerdpartnerschap.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ontbindinghuwelijkgeregistreerdpartnerschapRepository.count();
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

    protected Ontbindinghuwelijkgeregistreerdpartnerschap getPersistedOntbindinghuwelijkgeregistreerdpartnerschap(
        Ontbindinghuwelijkgeregistreerdpartnerschap ontbindinghuwelijkgeregistreerdpartnerschap
    ) {
        return ontbindinghuwelijkgeregistreerdpartnerschapRepository
            .findById(ontbindinghuwelijkgeregistreerdpartnerschap.getId())
            .orElseThrow();
    }

    protected void assertPersistedOntbindinghuwelijkgeregistreerdpartnerschapToMatchAllProperties(
        Ontbindinghuwelijkgeregistreerdpartnerschap expectedOntbindinghuwelijkgeregistreerdpartnerschap
    ) {
        assertOntbindinghuwelijkgeregistreerdpartnerschapAllPropertiesEquals(
            expectedOntbindinghuwelijkgeregistreerdpartnerschap,
            getPersistedOntbindinghuwelijkgeregistreerdpartnerschap(expectedOntbindinghuwelijkgeregistreerdpartnerschap)
        );
    }

    protected void assertPersistedOntbindinghuwelijkgeregistreerdpartnerschapToMatchUpdatableProperties(
        Ontbindinghuwelijkgeregistreerdpartnerschap expectedOntbindinghuwelijkgeregistreerdpartnerschap
    ) {
        assertOntbindinghuwelijkgeregistreerdpartnerschapAllUpdatablePropertiesEquals(
            expectedOntbindinghuwelijkgeregistreerdpartnerschap,
            getPersistedOntbindinghuwelijkgeregistreerdpartnerschap(expectedOntbindinghuwelijkgeregistreerdpartnerschap)
        );
    }
}
