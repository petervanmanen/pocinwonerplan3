package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BedrijfsprocestypeAsserts.*;
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
import nl.ritense.demo.domain.Bedrijfsprocestype;
import nl.ritense.demo.repository.BedrijfsprocestypeRepository;
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
 * Integration tests for the {@link BedrijfsprocestypeResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class BedrijfsprocestypeResourceIT {

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bedrijfsprocestypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BedrijfsprocestypeRepository bedrijfsprocestypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBedrijfsprocestypeMockMvc;

    private Bedrijfsprocestype bedrijfsprocestype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bedrijfsprocestype createEntity(EntityManager em) {
        Bedrijfsprocestype bedrijfsprocestype = new Bedrijfsprocestype().omschrijving(DEFAULT_OMSCHRIJVING);
        return bedrijfsprocestype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bedrijfsprocestype createUpdatedEntity(EntityManager em) {
        Bedrijfsprocestype bedrijfsprocestype = new Bedrijfsprocestype().omschrijving(UPDATED_OMSCHRIJVING);
        return bedrijfsprocestype;
    }

    @BeforeEach
    public void initTest() {
        bedrijfsprocestype = createEntity(em);
    }

    @Test
    @Transactional
    void createBedrijfsprocestype() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bedrijfsprocestype
        var returnedBedrijfsprocestype = om.readValue(
            restBedrijfsprocestypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bedrijfsprocestype)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bedrijfsprocestype.class
        );

        // Validate the Bedrijfsprocestype in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBedrijfsprocestypeUpdatableFieldsEquals(
            returnedBedrijfsprocestype,
            getPersistedBedrijfsprocestype(returnedBedrijfsprocestype)
        );
    }

    @Test
    @Transactional
    void createBedrijfsprocestypeWithExistingId() throws Exception {
        // Create the Bedrijfsprocestype with an existing ID
        bedrijfsprocestype.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBedrijfsprocestypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bedrijfsprocestype)))
            .andExpect(status().isBadRequest());

        // Validate the Bedrijfsprocestype in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBedrijfsprocestypes() throws Exception {
        // Initialize the database
        bedrijfsprocestypeRepository.saveAndFlush(bedrijfsprocestype);

        // Get all the bedrijfsprocestypeList
        restBedrijfsprocestypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bedrijfsprocestype.getId().intValue())))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getBedrijfsprocestype() throws Exception {
        // Initialize the database
        bedrijfsprocestypeRepository.saveAndFlush(bedrijfsprocestype);

        // Get the bedrijfsprocestype
        restBedrijfsprocestypeMockMvc
            .perform(get(ENTITY_API_URL_ID, bedrijfsprocestype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bedrijfsprocestype.getId().intValue()))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingBedrijfsprocestype() throws Exception {
        // Get the bedrijfsprocestype
        restBedrijfsprocestypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBedrijfsprocestype() throws Exception {
        // Initialize the database
        bedrijfsprocestypeRepository.saveAndFlush(bedrijfsprocestype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bedrijfsprocestype
        Bedrijfsprocestype updatedBedrijfsprocestype = bedrijfsprocestypeRepository.findById(bedrijfsprocestype.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBedrijfsprocestype are not directly saved in db
        em.detach(updatedBedrijfsprocestype);
        updatedBedrijfsprocestype.omschrijving(UPDATED_OMSCHRIJVING);

        restBedrijfsprocestypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBedrijfsprocestype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBedrijfsprocestype))
            )
            .andExpect(status().isOk());

        // Validate the Bedrijfsprocestype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBedrijfsprocestypeToMatchAllProperties(updatedBedrijfsprocestype);
    }

    @Test
    @Transactional
    void putNonExistingBedrijfsprocestype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bedrijfsprocestype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBedrijfsprocestypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bedrijfsprocestype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bedrijfsprocestype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bedrijfsprocestype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBedrijfsprocestype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bedrijfsprocestype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBedrijfsprocestypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bedrijfsprocestype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bedrijfsprocestype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBedrijfsprocestype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bedrijfsprocestype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBedrijfsprocestypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bedrijfsprocestype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bedrijfsprocestype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBedrijfsprocestypeWithPatch() throws Exception {
        // Initialize the database
        bedrijfsprocestypeRepository.saveAndFlush(bedrijfsprocestype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bedrijfsprocestype using partial update
        Bedrijfsprocestype partialUpdatedBedrijfsprocestype = new Bedrijfsprocestype();
        partialUpdatedBedrijfsprocestype.setId(bedrijfsprocestype.getId());

        restBedrijfsprocestypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBedrijfsprocestype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBedrijfsprocestype))
            )
            .andExpect(status().isOk());

        // Validate the Bedrijfsprocestype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBedrijfsprocestypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBedrijfsprocestype, bedrijfsprocestype),
            getPersistedBedrijfsprocestype(bedrijfsprocestype)
        );
    }

    @Test
    @Transactional
    void fullUpdateBedrijfsprocestypeWithPatch() throws Exception {
        // Initialize the database
        bedrijfsprocestypeRepository.saveAndFlush(bedrijfsprocestype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bedrijfsprocestype using partial update
        Bedrijfsprocestype partialUpdatedBedrijfsprocestype = new Bedrijfsprocestype();
        partialUpdatedBedrijfsprocestype.setId(bedrijfsprocestype.getId());

        partialUpdatedBedrijfsprocestype.omschrijving(UPDATED_OMSCHRIJVING);

        restBedrijfsprocestypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBedrijfsprocestype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBedrijfsprocestype))
            )
            .andExpect(status().isOk());

        // Validate the Bedrijfsprocestype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBedrijfsprocestypeUpdatableFieldsEquals(
            partialUpdatedBedrijfsprocestype,
            getPersistedBedrijfsprocestype(partialUpdatedBedrijfsprocestype)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBedrijfsprocestype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bedrijfsprocestype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBedrijfsprocestypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bedrijfsprocestype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bedrijfsprocestype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bedrijfsprocestype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBedrijfsprocestype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bedrijfsprocestype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBedrijfsprocestypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bedrijfsprocestype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bedrijfsprocestype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBedrijfsprocestype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bedrijfsprocestype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBedrijfsprocestypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bedrijfsprocestype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bedrijfsprocestype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBedrijfsprocestype() throws Exception {
        // Initialize the database
        bedrijfsprocestypeRepository.saveAndFlush(bedrijfsprocestype);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bedrijfsprocestype
        restBedrijfsprocestypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, bedrijfsprocestype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bedrijfsprocestypeRepository.count();
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

    protected Bedrijfsprocestype getPersistedBedrijfsprocestype(Bedrijfsprocestype bedrijfsprocestype) {
        return bedrijfsprocestypeRepository.findById(bedrijfsprocestype.getId()).orElseThrow();
    }

    protected void assertPersistedBedrijfsprocestypeToMatchAllProperties(Bedrijfsprocestype expectedBedrijfsprocestype) {
        assertBedrijfsprocestypeAllPropertiesEquals(expectedBedrijfsprocestype, getPersistedBedrijfsprocestype(expectedBedrijfsprocestype));
    }

    protected void assertPersistedBedrijfsprocestypeToMatchUpdatableProperties(Bedrijfsprocestype expectedBedrijfsprocestype) {
        assertBedrijfsprocestypeAllUpdatablePropertiesEquals(
            expectedBedrijfsprocestype,
            getPersistedBedrijfsprocestype(expectedBedrijfsprocestype)
        );
    }
}
