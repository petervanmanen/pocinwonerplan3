package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BelangtypeAsserts.*;
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
import nl.ritense.demo.domain.Belangtype;
import nl.ritense.demo.repository.BelangtypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BelangtypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BelangtypeResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/belangtypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BelangtypeRepository belangtypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBelangtypeMockMvc;

    private Belangtype belangtype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Belangtype createEntity(EntityManager em) {
        Belangtype belangtype = new Belangtype().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return belangtype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Belangtype createUpdatedEntity(EntityManager em) {
        Belangtype belangtype = new Belangtype().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return belangtype;
    }

    @BeforeEach
    public void initTest() {
        belangtype = createEntity(em);
    }

    @Test
    @Transactional
    void createBelangtype() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Belangtype
        var returnedBelangtype = om.readValue(
            restBelangtypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(belangtype)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Belangtype.class
        );

        // Validate the Belangtype in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBelangtypeUpdatableFieldsEquals(returnedBelangtype, getPersistedBelangtype(returnedBelangtype));
    }

    @Test
    @Transactional
    void createBelangtypeWithExistingId() throws Exception {
        // Create the Belangtype with an existing ID
        belangtype.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBelangtypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(belangtype)))
            .andExpect(status().isBadRequest());

        // Validate the Belangtype in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBelangtypes() throws Exception {
        // Initialize the database
        belangtypeRepository.saveAndFlush(belangtype);

        // Get all the belangtypeList
        restBelangtypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(belangtype.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getBelangtype() throws Exception {
        // Initialize the database
        belangtypeRepository.saveAndFlush(belangtype);

        // Get the belangtype
        restBelangtypeMockMvc
            .perform(get(ENTITY_API_URL_ID, belangtype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(belangtype.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingBelangtype() throws Exception {
        // Get the belangtype
        restBelangtypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBelangtype() throws Exception {
        // Initialize the database
        belangtypeRepository.saveAndFlush(belangtype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the belangtype
        Belangtype updatedBelangtype = belangtypeRepository.findById(belangtype.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBelangtype are not directly saved in db
        em.detach(updatedBelangtype);
        updatedBelangtype.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restBelangtypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBelangtype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBelangtype))
            )
            .andExpect(status().isOk());

        // Validate the Belangtype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBelangtypeToMatchAllProperties(updatedBelangtype);
    }

    @Test
    @Transactional
    void putNonExistingBelangtype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belangtype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBelangtypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, belangtype.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(belangtype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Belangtype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBelangtype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belangtype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBelangtypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(belangtype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Belangtype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBelangtype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belangtype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBelangtypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(belangtype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Belangtype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBelangtypeWithPatch() throws Exception {
        // Initialize the database
        belangtypeRepository.saveAndFlush(belangtype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the belangtype using partial update
        Belangtype partialUpdatedBelangtype = new Belangtype();
        partialUpdatedBelangtype.setId(belangtype.getId());

        partialUpdatedBelangtype.naam(UPDATED_NAAM);

        restBelangtypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBelangtype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBelangtype))
            )
            .andExpect(status().isOk());

        // Validate the Belangtype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBelangtypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBelangtype, belangtype),
            getPersistedBelangtype(belangtype)
        );
    }

    @Test
    @Transactional
    void fullUpdateBelangtypeWithPatch() throws Exception {
        // Initialize the database
        belangtypeRepository.saveAndFlush(belangtype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the belangtype using partial update
        Belangtype partialUpdatedBelangtype = new Belangtype();
        partialUpdatedBelangtype.setId(belangtype.getId());

        partialUpdatedBelangtype.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restBelangtypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBelangtype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBelangtype))
            )
            .andExpect(status().isOk());

        // Validate the Belangtype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBelangtypeUpdatableFieldsEquals(partialUpdatedBelangtype, getPersistedBelangtype(partialUpdatedBelangtype));
    }

    @Test
    @Transactional
    void patchNonExistingBelangtype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belangtype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBelangtypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, belangtype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(belangtype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Belangtype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBelangtype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belangtype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBelangtypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(belangtype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Belangtype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBelangtype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        belangtype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBelangtypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(belangtype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Belangtype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBelangtype() throws Exception {
        // Initialize the database
        belangtypeRepository.saveAndFlush(belangtype);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the belangtype
        restBelangtypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, belangtype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return belangtypeRepository.count();
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

    protected Belangtype getPersistedBelangtype(Belangtype belangtype) {
        return belangtypeRepository.findById(belangtype.getId()).orElseThrow();
    }

    protected void assertPersistedBelangtypeToMatchAllProperties(Belangtype expectedBelangtype) {
        assertBelangtypeAllPropertiesEquals(expectedBelangtype, getPersistedBelangtype(expectedBelangtype));
    }

    protected void assertPersistedBelangtypeToMatchUpdatableProperties(Belangtype expectedBelangtype) {
        assertBelangtypeAllUpdatablePropertiesEquals(expectedBelangtype, getPersistedBelangtype(expectedBelangtype));
    }
}
