package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BenoemdobjectAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Benoemdobject;
import nl.ritense.demo.repository.BenoemdobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BenoemdobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BenoemdobjectResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEOMETRIEPUNT = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEPUNT = "BBBBBBBBBB";

    private static final String DEFAULT_GEOMETRIEVLAK = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEVLAK = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/benoemdobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BenoemdobjectRepository benoemdobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBenoemdobjectMockMvc;

    private Benoemdobject benoemdobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Benoemdobject createEntity(EntityManager em) {
        Benoemdobject benoemdobject = new Benoemdobject()
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .geometriepunt(DEFAULT_GEOMETRIEPUNT)
            .geometrievlak(DEFAULT_GEOMETRIEVLAK)
            .identificatie(DEFAULT_IDENTIFICATIE);
        return benoemdobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Benoemdobject createUpdatedEntity(EntityManager em) {
        Benoemdobject benoemdobject = new Benoemdobject()
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .geometriepunt(UPDATED_GEOMETRIEPUNT)
            .geometrievlak(UPDATED_GEOMETRIEVLAK)
            .identificatie(UPDATED_IDENTIFICATIE);
        return benoemdobject;
    }

    @BeforeEach
    public void initTest() {
        benoemdobject = createEntity(em);
    }

    @Test
    @Transactional
    void createBenoemdobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Benoemdobject
        var returnedBenoemdobject = om.readValue(
            restBenoemdobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(benoemdobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Benoemdobject.class
        );

        // Validate the Benoemdobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBenoemdobjectUpdatableFieldsEquals(returnedBenoemdobject, getPersistedBenoemdobject(returnedBenoemdobject));
    }

    @Test
    @Transactional
    void createBenoemdobjectWithExistingId() throws Exception {
        // Create the Benoemdobject with an existing ID
        benoemdobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBenoemdobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(benoemdobject)))
            .andExpect(status().isBadRequest());

        // Validate the Benoemdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBenoemdobjects() throws Exception {
        // Initialize the database
        benoemdobjectRepository.saveAndFlush(benoemdobject);

        // Get all the benoemdobjectList
        restBenoemdobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(benoemdobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].geometriepunt").value(hasItem(DEFAULT_GEOMETRIEPUNT)))
            .andExpect(jsonPath("$.[*].geometrievlak").value(hasItem(DEFAULT_GEOMETRIEVLAK)))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)));
    }

    @Test
    @Transactional
    void getBenoemdobject() throws Exception {
        // Initialize the database
        benoemdobjectRepository.saveAndFlush(benoemdobject);

        // Get the benoemdobject
        restBenoemdobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, benoemdobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(benoemdobject.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.geometriepunt").value(DEFAULT_GEOMETRIEPUNT))
            .andExpect(jsonPath("$.geometrievlak").value(DEFAULT_GEOMETRIEVLAK))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE));
    }

    @Test
    @Transactional
    void getNonExistingBenoemdobject() throws Exception {
        // Get the benoemdobject
        restBenoemdobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBenoemdobject() throws Exception {
        // Initialize the database
        benoemdobjectRepository.saveAndFlush(benoemdobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the benoemdobject
        Benoemdobject updatedBenoemdobject = benoemdobjectRepository.findById(benoemdobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBenoemdobject are not directly saved in db
        em.detach(updatedBenoemdobject);
        updatedBenoemdobject
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .geometriepunt(UPDATED_GEOMETRIEPUNT)
            .geometrievlak(UPDATED_GEOMETRIEVLAK)
            .identificatie(UPDATED_IDENTIFICATIE);

        restBenoemdobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBenoemdobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBenoemdobject))
            )
            .andExpect(status().isOk());

        // Validate the Benoemdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBenoemdobjectToMatchAllProperties(updatedBenoemdobject);
    }

    @Test
    @Transactional
    void putNonExistingBenoemdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        benoemdobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenoemdobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, benoemdobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(benoemdobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benoemdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBenoemdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        benoemdobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenoemdobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(benoemdobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benoemdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBenoemdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        benoemdobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenoemdobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(benoemdobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Benoemdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBenoemdobjectWithPatch() throws Exception {
        // Initialize the database
        benoemdobjectRepository.saveAndFlush(benoemdobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the benoemdobject using partial update
        Benoemdobject partialUpdatedBenoemdobject = new Benoemdobject();
        partialUpdatedBenoemdobject.setId(benoemdobject.getId());

        partialUpdatedBenoemdobject.datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID).identificatie(UPDATED_IDENTIFICATIE);

        restBenoemdobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenoemdobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBenoemdobject))
            )
            .andExpect(status().isOk());

        // Validate the Benoemdobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBenoemdobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBenoemdobject, benoemdobject),
            getPersistedBenoemdobject(benoemdobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateBenoemdobjectWithPatch() throws Exception {
        // Initialize the database
        benoemdobjectRepository.saveAndFlush(benoemdobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the benoemdobject using partial update
        Benoemdobject partialUpdatedBenoemdobject = new Benoemdobject();
        partialUpdatedBenoemdobject.setId(benoemdobject.getId());

        partialUpdatedBenoemdobject
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .geometriepunt(UPDATED_GEOMETRIEPUNT)
            .geometrievlak(UPDATED_GEOMETRIEVLAK)
            .identificatie(UPDATED_IDENTIFICATIE);

        restBenoemdobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenoemdobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBenoemdobject))
            )
            .andExpect(status().isOk());

        // Validate the Benoemdobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBenoemdobjectUpdatableFieldsEquals(partialUpdatedBenoemdobject, getPersistedBenoemdobject(partialUpdatedBenoemdobject));
    }

    @Test
    @Transactional
    void patchNonExistingBenoemdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        benoemdobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenoemdobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, benoemdobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(benoemdobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benoemdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBenoemdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        benoemdobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenoemdobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(benoemdobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benoemdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBenoemdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        benoemdobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenoemdobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(benoemdobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Benoemdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBenoemdobject() throws Exception {
        // Initialize the database
        benoemdobjectRepository.saveAndFlush(benoemdobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the benoemdobject
        restBenoemdobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, benoemdobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return benoemdobjectRepository.count();
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

    protected Benoemdobject getPersistedBenoemdobject(Benoemdobject benoemdobject) {
        return benoemdobjectRepository.findById(benoemdobject.getId()).orElseThrow();
    }

    protected void assertPersistedBenoemdobjectToMatchAllProperties(Benoemdobject expectedBenoemdobject) {
        assertBenoemdobjectAllPropertiesEquals(expectedBenoemdobject, getPersistedBenoemdobject(expectedBenoemdobject));
    }

    protected void assertPersistedBenoemdobjectToMatchUpdatableProperties(Benoemdobject expectedBenoemdobject) {
        assertBenoemdobjectAllUpdatablePropertiesEquals(expectedBenoemdobject, getPersistedBenoemdobject(expectedBenoemdobject));
    }
}
