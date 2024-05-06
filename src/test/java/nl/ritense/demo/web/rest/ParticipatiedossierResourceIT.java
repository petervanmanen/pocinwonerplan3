package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ParticipatiedossierAsserts.*;
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
import nl.ritense.demo.domain.Participatiedossier;
import nl.ritense.demo.repository.ParticipatiedossierRepository;
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
 * Integration tests for the {@link ParticipatiedossierResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class ParticipatiedossierResourceIT {

    private static final String DEFAULT_ARBEIDSVERMOGEN = "AAAAAAAAAA";
    private static final String UPDATED_ARBEIDSVERMOGEN = "BBBBBBBBBB";

    private static final String DEFAULT_BEGELEIDERSCODE = "AAAAAAAAAA";
    private static final String UPDATED_BEGELEIDERSCODE = "BBBBBBBBBB";

    private static final String DEFAULT_BESCHUTWERK = "AAAAAAAAAA";
    private static final String UPDATED_BESCHUTWERK = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENTBEGELEIDERID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTBEGELEIDERID = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDEBEGELEIDING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDEBEGELEIDING = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMSTARTBEGELEIDING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSTARTBEGELEIDING = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIEDOELGROEPREGISTER = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEDOELGROEPREGISTER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/participatiedossiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ParticipatiedossierRepository participatiedossierRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParticipatiedossierMockMvc;

    private Participatiedossier participatiedossier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participatiedossier createEntity(EntityManager em) {
        Participatiedossier participatiedossier = new Participatiedossier()
            .arbeidsvermogen(DEFAULT_ARBEIDSVERMOGEN)
            .begeleiderscode(DEFAULT_BEGELEIDERSCODE)
            .beschutwerk(DEFAULT_BESCHUTWERK)
            .clientbegeleiderid(DEFAULT_CLIENTBEGELEIDERID)
            .datumeindebegeleiding(DEFAULT_DATUMEINDEBEGELEIDING)
            .datumstartbegeleiding(DEFAULT_DATUMSTARTBEGELEIDING)
            .indicatiedoelgroepregister(DEFAULT_INDICATIEDOELGROEPREGISTER);
        return participatiedossier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participatiedossier createUpdatedEntity(EntityManager em) {
        Participatiedossier participatiedossier = new Participatiedossier()
            .arbeidsvermogen(UPDATED_ARBEIDSVERMOGEN)
            .begeleiderscode(UPDATED_BEGELEIDERSCODE)
            .beschutwerk(UPDATED_BESCHUTWERK)
            .clientbegeleiderid(UPDATED_CLIENTBEGELEIDERID)
            .datumeindebegeleiding(UPDATED_DATUMEINDEBEGELEIDING)
            .datumstartbegeleiding(UPDATED_DATUMSTARTBEGELEIDING)
            .indicatiedoelgroepregister(UPDATED_INDICATIEDOELGROEPREGISTER);
        return participatiedossier;
    }

    @BeforeEach
    public void initTest() {
        participatiedossier = createEntity(em);
    }

    @Test
    @Transactional
    void createParticipatiedossier() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Participatiedossier
        var returnedParticipatiedossier = om.readValue(
            restParticipatiedossierMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(participatiedossier)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Participatiedossier.class
        );

        // Validate the Participatiedossier in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertParticipatiedossierUpdatableFieldsEquals(
            returnedParticipatiedossier,
            getPersistedParticipatiedossier(returnedParticipatiedossier)
        );
    }

    @Test
    @Transactional
    void createParticipatiedossierWithExistingId() throws Exception {
        // Create the Participatiedossier with an existing ID
        participatiedossier.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParticipatiedossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(participatiedossier)))
            .andExpect(status().isBadRequest());

        // Validate the Participatiedossier in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParticipatiedossiers() throws Exception {
        // Initialize the database
        participatiedossierRepository.saveAndFlush(participatiedossier);

        // Get all the participatiedossierList
        restParticipatiedossierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(participatiedossier.getId().intValue())))
            .andExpect(jsonPath("$.[*].arbeidsvermogen").value(hasItem(DEFAULT_ARBEIDSVERMOGEN)))
            .andExpect(jsonPath("$.[*].begeleiderscode").value(hasItem(DEFAULT_BEGELEIDERSCODE)))
            .andExpect(jsonPath("$.[*].beschutwerk").value(hasItem(DEFAULT_BESCHUTWERK)))
            .andExpect(jsonPath("$.[*].clientbegeleiderid").value(hasItem(DEFAULT_CLIENTBEGELEIDERID)))
            .andExpect(jsonPath("$.[*].datumeindebegeleiding").value(hasItem(DEFAULT_DATUMEINDEBEGELEIDING)))
            .andExpect(jsonPath("$.[*].datumstartbegeleiding").value(hasItem(DEFAULT_DATUMSTARTBEGELEIDING)))
            .andExpect(jsonPath("$.[*].indicatiedoelgroepregister").value(hasItem(DEFAULT_INDICATIEDOELGROEPREGISTER)));
    }

    @Test
    @Transactional
    void getParticipatiedossier() throws Exception {
        // Initialize the database
        participatiedossierRepository.saveAndFlush(participatiedossier);

        // Get the participatiedossier
        restParticipatiedossierMockMvc
            .perform(get(ENTITY_API_URL_ID, participatiedossier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(participatiedossier.getId().intValue()))
            .andExpect(jsonPath("$.arbeidsvermogen").value(DEFAULT_ARBEIDSVERMOGEN))
            .andExpect(jsonPath("$.begeleiderscode").value(DEFAULT_BEGELEIDERSCODE))
            .andExpect(jsonPath("$.beschutwerk").value(DEFAULT_BESCHUTWERK))
            .andExpect(jsonPath("$.clientbegeleiderid").value(DEFAULT_CLIENTBEGELEIDERID))
            .andExpect(jsonPath("$.datumeindebegeleiding").value(DEFAULT_DATUMEINDEBEGELEIDING))
            .andExpect(jsonPath("$.datumstartbegeleiding").value(DEFAULT_DATUMSTARTBEGELEIDING))
            .andExpect(jsonPath("$.indicatiedoelgroepregister").value(DEFAULT_INDICATIEDOELGROEPREGISTER));
    }

    @Test
    @Transactional
    void getNonExistingParticipatiedossier() throws Exception {
        // Get the participatiedossier
        restParticipatiedossierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingParticipatiedossier() throws Exception {
        // Initialize the database
        participatiedossierRepository.saveAndFlush(participatiedossier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the participatiedossier
        Participatiedossier updatedParticipatiedossier = participatiedossierRepository.findById(participatiedossier.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedParticipatiedossier are not directly saved in db
        em.detach(updatedParticipatiedossier);
        updatedParticipatiedossier
            .arbeidsvermogen(UPDATED_ARBEIDSVERMOGEN)
            .begeleiderscode(UPDATED_BEGELEIDERSCODE)
            .beschutwerk(UPDATED_BESCHUTWERK)
            .clientbegeleiderid(UPDATED_CLIENTBEGELEIDERID)
            .datumeindebegeleiding(UPDATED_DATUMEINDEBEGELEIDING)
            .datumstartbegeleiding(UPDATED_DATUMSTARTBEGELEIDING)
            .indicatiedoelgroepregister(UPDATED_INDICATIEDOELGROEPREGISTER);

        restParticipatiedossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedParticipatiedossier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedParticipatiedossier))
            )
            .andExpect(status().isOk());

        // Validate the Participatiedossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedParticipatiedossierToMatchAllProperties(updatedParticipatiedossier);
    }

    @Test
    @Transactional
    void putNonExistingParticipatiedossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        participatiedossier.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipatiedossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, participatiedossier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(participatiedossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participatiedossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParticipatiedossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        participatiedossier.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipatiedossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(participatiedossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participatiedossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParticipatiedossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        participatiedossier.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipatiedossierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(participatiedossier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Participatiedossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParticipatiedossierWithPatch() throws Exception {
        // Initialize the database
        participatiedossierRepository.saveAndFlush(participatiedossier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the participatiedossier using partial update
        Participatiedossier partialUpdatedParticipatiedossier = new Participatiedossier();
        partialUpdatedParticipatiedossier.setId(participatiedossier.getId());

        partialUpdatedParticipatiedossier
            .arbeidsvermogen(UPDATED_ARBEIDSVERMOGEN)
            .begeleiderscode(UPDATED_BEGELEIDERSCODE)
            .beschutwerk(UPDATED_BESCHUTWERK)
            .datumstartbegeleiding(UPDATED_DATUMSTARTBEGELEIDING);

        restParticipatiedossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParticipatiedossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParticipatiedossier))
            )
            .andExpect(status().isOk());

        // Validate the Participatiedossier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParticipatiedossierUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedParticipatiedossier, participatiedossier),
            getPersistedParticipatiedossier(participatiedossier)
        );
    }

    @Test
    @Transactional
    void fullUpdateParticipatiedossierWithPatch() throws Exception {
        // Initialize the database
        participatiedossierRepository.saveAndFlush(participatiedossier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the participatiedossier using partial update
        Participatiedossier partialUpdatedParticipatiedossier = new Participatiedossier();
        partialUpdatedParticipatiedossier.setId(participatiedossier.getId());

        partialUpdatedParticipatiedossier
            .arbeidsvermogen(UPDATED_ARBEIDSVERMOGEN)
            .begeleiderscode(UPDATED_BEGELEIDERSCODE)
            .beschutwerk(UPDATED_BESCHUTWERK)
            .clientbegeleiderid(UPDATED_CLIENTBEGELEIDERID)
            .datumeindebegeleiding(UPDATED_DATUMEINDEBEGELEIDING)
            .datumstartbegeleiding(UPDATED_DATUMSTARTBEGELEIDING)
            .indicatiedoelgroepregister(UPDATED_INDICATIEDOELGROEPREGISTER);

        restParticipatiedossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParticipatiedossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParticipatiedossier))
            )
            .andExpect(status().isOk());

        // Validate the Participatiedossier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParticipatiedossierUpdatableFieldsEquals(
            partialUpdatedParticipatiedossier,
            getPersistedParticipatiedossier(partialUpdatedParticipatiedossier)
        );
    }

    @Test
    @Transactional
    void patchNonExistingParticipatiedossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        participatiedossier.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipatiedossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, participatiedossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(participatiedossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participatiedossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParticipatiedossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        participatiedossier.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipatiedossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(participatiedossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participatiedossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParticipatiedossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        participatiedossier.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipatiedossierMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(participatiedossier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Participatiedossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParticipatiedossier() throws Exception {
        // Initialize the database
        participatiedossierRepository.saveAndFlush(participatiedossier);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the participatiedossier
        restParticipatiedossierMockMvc
            .perform(delete(ENTITY_API_URL_ID, participatiedossier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return participatiedossierRepository.count();
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

    protected Participatiedossier getPersistedParticipatiedossier(Participatiedossier participatiedossier) {
        return participatiedossierRepository.findById(participatiedossier.getId()).orElseThrow();
    }

    protected void assertPersistedParticipatiedossierToMatchAllProperties(Participatiedossier expectedParticipatiedossier) {
        assertParticipatiedossierAllPropertiesEquals(
            expectedParticipatiedossier,
            getPersistedParticipatiedossier(expectedParticipatiedossier)
        );
    }

    protected void assertPersistedParticipatiedossierToMatchUpdatableProperties(Participatiedossier expectedParticipatiedossier) {
        assertParticipatiedossierAllUpdatablePropertiesEquals(
            expectedParticipatiedossier,
            getPersistedParticipatiedossier(expectedParticipatiedossier)
        );
    }
}
