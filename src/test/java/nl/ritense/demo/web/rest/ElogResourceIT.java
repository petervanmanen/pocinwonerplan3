package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ElogAsserts.*;
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
import nl.ritense.demo.domain.Elog;
import nl.ritense.demo.repository.ElogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ElogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ElogResourceIT {

    private static final String DEFAULT_KORTEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_KORTEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_TIJD = "AAAAAAAAAA";
    private static final String UPDATED_TIJD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/elogs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ElogRepository elogRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restElogMockMvc;

    private Elog elog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Elog createEntity(EntityManager em) {
        Elog elog = new Elog().korteomschrijving(DEFAULT_KORTEOMSCHRIJVING).omschrijving(DEFAULT_OMSCHRIJVING).tijd(DEFAULT_TIJD);
        return elog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Elog createUpdatedEntity(EntityManager em) {
        Elog elog = new Elog().korteomschrijving(UPDATED_KORTEOMSCHRIJVING).omschrijving(UPDATED_OMSCHRIJVING).tijd(UPDATED_TIJD);
        return elog;
    }

    @BeforeEach
    public void initTest() {
        elog = createEntity(em);
    }

    @Test
    @Transactional
    void createElog() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Elog
        var returnedElog = om.readValue(
            restElogMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(elog)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Elog.class
        );

        // Validate the Elog in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertElogUpdatableFieldsEquals(returnedElog, getPersistedElog(returnedElog));
    }

    @Test
    @Transactional
    void createElogWithExistingId() throws Exception {
        // Create the Elog with an existing ID
        elog.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restElogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(elog)))
            .andExpect(status().isBadRequest());

        // Validate the Elog in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllElogs() throws Exception {
        // Initialize the database
        elogRepository.saveAndFlush(elog);

        // Get all the elogList
        restElogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elog.getId().intValue())))
            .andExpect(jsonPath("$.[*].korteomschrijving").value(hasItem(DEFAULT_KORTEOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].tijd").value(hasItem(DEFAULT_TIJD)));
    }

    @Test
    @Transactional
    void getElog() throws Exception {
        // Initialize the database
        elogRepository.saveAndFlush(elog);

        // Get the elog
        restElogMockMvc
            .perform(get(ENTITY_API_URL_ID, elog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(elog.getId().intValue()))
            .andExpect(jsonPath("$.korteomschrijving").value(DEFAULT_KORTEOMSCHRIJVING))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.tijd").value(DEFAULT_TIJD));
    }

    @Test
    @Transactional
    void getNonExistingElog() throws Exception {
        // Get the elog
        restElogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingElog() throws Exception {
        // Initialize the database
        elogRepository.saveAndFlush(elog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the elog
        Elog updatedElog = elogRepository.findById(elog.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedElog are not directly saved in db
        em.detach(updatedElog);
        updatedElog.korteomschrijving(UPDATED_KORTEOMSCHRIJVING).omschrijving(UPDATED_OMSCHRIJVING).tijd(UPDATED_TIJD);

        restElogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedElog.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedElog))
            )
            .andExpect(status().isOk());

        // Validate the Elog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedElogToMatchAllProperties(updatedElog);
    }

    @Test
    @Transactional
    void putNonExistingElog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        elog.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElogMockMvc
            .perform(put(ENTITY_API_URL_ID, elog.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(elog)))
            .andExpect(status().isBadRequest());

        // Validate the Elog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchElog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        elog.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(elog))
            )
            .andExpect(status().isBadRequest());

        // Validate the Elog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamElog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        elog.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(elog)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Elog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateElogWithPatch() throws Exception {
        // Initialize the database
        elogRepository.saveAndFlush(elog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the elog using partial update
        Elog partialUpdatedElog = new Elog();
        partialUpdatedElog.setId(elog.getId());

        partialUpdatedElog.tijd(UPDATED_TIJD);

        restElogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElog.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedElog))
            )
            .andExpect(status().isOk());

        // Validate the Elog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertElogUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedElog, elog), getPersistedElog(elog));
    }

    @Test
    @Transactional
    void fullUpdateElogWithPatch() throws Exception {
        // Initialize the database
        elogRepository.saveAndFlush(elog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the elog using partial update
        Elog partialUpdatedElog = new Elog();
        partialUpdatedElog.setId(elog.getId());

        partialUpdatedElog.korteomschrijving(UPDATED_KORTEOMSCHRIJVING).omschrijving(UPDATED_OMSCHRIJVING).tijd(UPDATED_TIJD);

        restElogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElog.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedElog))
            )
            .andExpect(status().isOk());

        // Validate the Elog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertElogUpdatableFieldsEquals(partialUpdatedElog, getPersistedElog(partialUpdatedElog));
    }

    @Test
    @Transactional
    void patchNonExistingElog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        elog.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElogMockMvc
            .perform(patch(ENTITY_API_URL_ID, elog.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(elog)))
            .andExpect(status().isBadRequest());

        // Validate the Elog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchElog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        elog.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(elog))
            )
            .andExpect(status().isBadRequest());

        // Validate the Elog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamElog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        elog.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElogMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(elog)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Elog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteElog() throws Exception {
        // Initialize the database
        elogRepository.saveAndFlush(elog);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the elog
        restElogMockMvc
            .perform(delete(ENTITY_API_URL_ID, elog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return elogRepository.count();
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

    protected Elog getPersistedElog(Elog elog) {
        return elogRepository.findById(elog.getId()).orElseThrow();
    }

    protected void assertPersistedElogToMatchAllProperties(Elog expectedElog) {
        assertElogAllPropertiesEquals(expectedElog, getPersistedElog(expectedElog));
    }

    protected void assertPersistedElogToMatchUpdatableProperties(Elog expectedElog) {
        assertElogAllUpdatablePropertiesEquals(expectedElog, getPersistedElog(expectedElog));
    }
}
