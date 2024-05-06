package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerblijfstitelAsserts.*;
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
import nl.ritense.demo.domain.Verblijfstitel;
import nl.ritense.demo.repository.VerblijfstitelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerblijfstitelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerblijfstitelResourceIT {

    private static final String DEFAULT_AANDUIDINGVERBLIJFSTITEL = "AAAAAAAAAA";
    private static final String UPDATED_AANDUIDINGVERBLIJFSTITEL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMOPNAME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMOPNAME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDVERBLIJFSTITEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDVERBLIJFSTITEL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_VERBLIJFSTITELCODE = "AAAAAAAAAA";
    private static final String UPDATED_VERBLIJFSTITELCODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verblijfstitels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerblijfstitelRepository verblijfstitelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerblijfstitelMockMvc;

    private Verblijfstitel verblijfstitel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfstitel createEntity(EntityManager em) {
        Verblijfstitel verblijfstitel = new Verblijfstitel()
            .aanduidingverblijfstitel(DEFAULT_AANDUIDINGVERBLIJFSTITEL)
            .datumbegin(DEFAULT_DATUMBEGIN)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumopname(DEFAULT_DATUMOPNAME)
            .datumbegingeldigheidverblijfstitel(DEFAULT_DATUMBEGINGELDIGHEIDVERBLIJFSTITEL)
            .verblijfstitelcode(DEFAULT_VERBLIJFSTITELCODE);
        return verblijfstitel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfstitel createUpdatedEntity(EntityManager em) {
        Verblijfstitel verblijfstitel = new Verblijfstitel()
            .aanduidingverblijfstitel(UPDATED_AANDUIDINGVERBLIJFSTITEL)
            .datumbegin(UPDATED_DATUMBEGIN)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumopname(UPDATED_DATUMOPNAME)
            .datumbegingeldigheidverblijfstitel(UPDATED_DATUMBEGINGELDIGHEIDVERBLIJFSTITEL)
            .verblijfstitelcode(UPDATED_VERBLIJFSTITELCODE);
        return verblijfstitel;
    }

    @BeforeEach
    public void initTest() {
        verblijfstitel = createEntity(em);
    }

    @Test
    @Transactional
    void createVerblijfstitel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verblijfstitel
        var returnedVerblijfstitel = om.readValue(
            restVerblijfstitelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfstitel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verblijfstitel.class
        );

        // Validate the Verblijfstitel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerblijfstitelUpdatableFieldsEquals(returnedVerblijfstitel, getPersistedVerblijfstitel(returnedVerblijfstitel));
    }

    @Test
    @Transactional
    void createVerblijfstitelWithExistingId() throws Exception {
        // Create the Verblijfstitel with an existing ID
        verblijfstitel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerblijfstitelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfstitel)))
            .andExpect(status().isBadRequest());

        // Validate the Verblijfstitel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerblijfstitels() throws Exception {
        // Initialize the database
        verblijfstitelRepository.saveAndFlush(verblijfstitel);

        // Get all the verblijfstitelList
        restVerblijfstitelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verblijfstitel.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanduidingverblijfstitel").value(hasItem(DEFAULT_AANDUIDINGVERBLIJFSTITEL)))
            .andExpect(jsonPath("$.[*].datumbegin").value(hasItem(DEFAULT_DATUMBEGIN.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumopname").value(hasItem(DEFAULT_DATUMOPNAME.toString())))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidverblijfstitel").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDVERBLIJFSTITEL.toString()))
            )
            .andExpect(jsonPath("$.[*].verblijfstitelcode").value(hasItem(DEFAULT_VERBLIJFSTITELCODE)));
    }

    @Test
    @Transactional
    void getVerblijfstitel() throws Exception {
        // Initialize the database
        verblijfstitelRepository.saveAndFlush(verblijfstitel);

        // Get the verblijfstitel
        restVerblijfstitelMockMvc
            .perform(get(ENTITY_API_URL_ID, verblijfstitel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verblijfstitel.getId().intValue()))
            .andExpect(jsonPath("$.aanduidingverblijfstitel").value(DEFAULT_AANDUIDINGVERBLIJFSTITEL))
            .andExpect(jsonPath("$.datumbegin").value(DEFAULT_DATUMBEGIN.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumopname").value(DEFAULT_DATUMOPNAME.toString()))
            .andExpect(jsonPath("$.datumbegingeldigheidverblijfstitel").value(DEFAULT_DATUMBEGINGELDIGHEIDVERBLIJFSTITEL.toString()))
            .andExpect(jsonPath("$.verblijfstitelcode").value(DEFAULT_VERBLIJFSTITELCODE));
    }

    @Test
    @Transactional
    void getNonExistingVerblijfstitel() throws Exception {
        // Get the verblijfstitel
        restVerblijfstitelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerblijfstitel() throws Exception {
        // Initialize the database
        verblijfstitelRepository.saveAndFlush(verblijfstitel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfstitel
        Verblijfstitel updatedVerblijfstitel = verblijfstitelRepository.findById(verblijfstitel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerblijfstitel are not directly saved in db
        em.detach(updatedVerblijfstitel);
        updatedVerblijfstitel
            .aanduidingverblijfstitel(UPDATED_AANDUIDINGVERBLIJFSTITEL)
            .datumbegin(UPDATED_DATUMBEGIN)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumopname(UPDATED_DATUMOPNAME)
            .datumbegingeldigheidverblijfstitel(UPDATED_DATUMBEGINGELDIGHEIDVERBLIJFSTITEL)
            .verblijfstitelcode(UPDATED_VERBLIJFSTITELCODE);

        restVerblijfstitelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerblijfstitel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerblijfstitel))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfstitel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerblijfstitelToMatchAllProperties(updatedVerblijfstitel);
    }

    @Test
    @Transactional
    void putNonExistingVerblijfstitel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfstitel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerblijfstitelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verblijfstitel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfstitel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfstitel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerblijfstitel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfstitel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfstitelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfstitel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfstitel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerblijfstitel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfstitel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfstitelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfstitel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verblijfstitel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerblijfstitelWithPatch() throws Exception {
        // Initialize the database
        verblijfstitelRepository.saveAndFlush(verblijfstitel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfstitel using partial update
        Verblijfstitel partialUpdatedVerblijfstitel = new Verblijfstitel();
        partialUpdatedVerblijfstitel.setId(verblijfstitel.getId());

        partialUpdatedVerblijfstitel
            .aanduidingverblijfstitel(UPDATED_AANDUIDINGVERBLIJFSTITEL)
            .datumbegin(UPDATED_DATUMBEGIN)
            .datumeinde(UPDATED_DATUMEINDE)
            .verblijfstitelcode(UPDATED_VERBLIJFSTITELCODE);

        restVerblijfstitelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerblijfstitel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerblijfstitel))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfstitel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerblijfstitelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerblijfstitel, verblijfstitel),
            getPersistedVerblijfstitel(verblijfstitel)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerblijfstitelWithPatch() throws Exception {
        // Initialize the database
        verblijfstitelRepository.saveAndFlush(verblijfstitel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfstitel using partial update
        Verblijfstitel partialUpdatedVerblijfstitel = new Verblijfstitel();
        partialUpdatedVerblijfstitel.setId(verblijfstitel.getId());

        partialUpdatedVerblijfstitel
            .aanduidingverblijfstitel(UPDATED_AANDUIDINGVERBLIJFSTITEL)
            .datumbegin(UPDATED_DATUMBEGIN)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumopname(UPDATED_DATUMOPNAME)
            .datumbegingeldigheidverblijfstitel(UPDATED_DATUMBEGINGELDIGHEIDVERBLIJFSTITEL)
            .verblijfstitelcode(UPDATED_VERBLIJFSTITELCODE);

        restVerblijfstitelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerblijfstitel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerblijfstitel))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfstitel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerblijfstitelUpdatableFieldsEquals(partialUpdatedVerblijfstitel, getPersistedVerblijfstitel(partialUpdatedVerblijfstitel));
    }

    @Test
    @Transactional
    void patchNonExistingVerblijfstitel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfstitel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerblijfstitelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verblijfstitel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verblijfstitel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfstitel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerblijfstitel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfstitel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfstitelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verblijfstitel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfstitel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerblijfstitel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfstitel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfstitelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verblijfstitel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verblijfstitel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerblijfstitel() throws Exception {
        // Initialize the database
        verblijfstitelRepository.saveAndFlush(verblijfstitel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verblijfstitel
        restVerblijfstitelMockMvc
            .perform(delete(ENTITY_API_URL_ID, verblijfstitel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verblijfstitelRepository.count();
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

    protected Verblijfstitel getPersistedVerblijfstitel(Verblijfstitel verblijfstitel) {
        return verblijfstitelRepository.findById(verblijfstitel.getId()).orElseThrow();
    }

    protected void assertPersistedVerblijfstitelToMatchAllProperties(Verblijfstitel expectedVerblijfstitel) {
        assertVerblijfstitelAllPropertiesEquals(expectedVerblijfstitel, getPersistedVerblijfstitel(expectedVerblijfstitel));
    }

    protected void assertPersistedVerblijfstitelToMatchUpdatableProperties(Verblijfstitel expectedVerblijfstitel) {
        assertVerblijfstitelAllUpdatablePropertiesEquals(expectedVerblijfstitel, getPersistedVerblijfstitel(expectedVerblijfstitel));
    }
}
