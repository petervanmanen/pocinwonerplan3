package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.UitkeringsrunAsserts.*;
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
import nl.ritense.demo.domain.Uitkeringsrun;
import nl.ritense.demo.repository.UitkeringsrunRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UitkeringsrunResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UitkeringsrunResourceIT {

    private static final LocalDate DEFAULT_DATUMRUN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMRUN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FREQUENTIE = "AAAAAAAAAA";
    private static final String UPDATED_FREQUENTIE = "BBBBBBBBBB";

    private static final String DEFAULT_PERIODERUN = "AAAAAAAAAA";
    private static final String UPDATED_PERIODERUN = "BBBBBBBBBB";

    private static final String DEFAULT_SOORTRUN = "AAAAAAAAAA";
    private static final String UPDATED_SOORTRUN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/uitkeringsruns";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UitkeringsrunRepository uitkeringsrunRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUitkeringsrunMockMvc;

    private Uitkeringsrun uitkeringsrun;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitkeringsrun createEntity(EntityManager em) {
        Uitkeringsrun uitkeringsrun = new Uitkeringsrun()
            .datumrun(DEFAULT_DATUMRUN)
            .frequentie(DEFAULT_FREQUENTIE)
            .perioderun(DEFAULT_PERIODERUN)
            .soortrun(DEFAULT_SOORTRUN);
        return uitkeringsrun;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitkeringsrun createUpdatedEntity(EntityManager em) {
        Uitkeringsrun uitkeringsrun = new Uitkeringsrun()
            .datumrun(UPDATED_DATUMRUN)
            .frequentie(UPDATED_FREQUENTIE)
            .perioderun(UPDATED_PERIODERUN)
            .soortrun(UPDATED_SOORTRUN);
        return uitkeringsrun;
    }

    @BeforeEach
    public void initTest() {
        uitkeringsrun = createEntity(em);
    }

    @Test
    @Transactional
    void createUitkeringsrun() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Uitkeringsrun
        var returnedUitkeringsrun = om.readValue(
            restUitkeringsrunMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitkeringsrun)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Uitkeringsrun.class
        );

        // Validate the Uitkeringsrun in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertUitkeringsrunUpdatableFieldsEquals(returnedUitkeringsrun, getPersistedUitkeringsrun(returnedUitkeringsrun));
    }

    @Test
    @Transactional
    void createUitkeringsrunWithExistingId() throws Exception {
        // Create the Uitkeringsrun with an existing ID
        uitkeringsrun.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUitkeringsrunMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitkeringsrun)))
            .andExpect(status().isBadRequest());

        // Validate the Uitkeringsrun in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUitkeringsruns() throws Exception {
        // Initialize the database
        uitkeringsrunRepository.saveAndFlush(uitkeringsrun);

        // Get all the uitkeringsrunList
        restUitkeringsrunMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uitkeringsrun.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumrun").value(hasItem(DEFAULT_DATUMRUN.toString())))
            .andExpect(jsonPath("$.[*].frequentie").value(hasItem(DEFAULT_FREQUENTIE)))
            .andExpect(jsonPath("$.[*].perioderun").value(hasItem(DEFAULT_PERIODERUN)))
            .andExpect(jsonPath("$.[*].soortrun").value(hasItem(DEFAULT_SOORTRUN)));
    }

    @Test
    @Transactional
    void getUitkeringsrun() throws Exception {
        // Initialize the database
        uitkeringsrunRepository.saveAndFlush(uitkeringsrun);

        // Get the uitkeringsrun
        restUitkeringsrunMockMvc
            .perform(get(ENTITY_API_URL_ID, uitkeringsrun.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uitkeringsrun.getId().intValue()))
            .andExpect(jsonPath("$.datumrun").value(DEFAULT_DATUMRUN.toString()))
            .andExpect(jsonPath("$.frequentie").value(DEFAULT_FREQUENTIE))
            .andExpect(jsonPath("$.perioderun").value(DEFAULT_PERIODERUN))
            .andExpect(jsonPath("$.soortrun").value(DEFAULT_SOORTRUN));
    }

    @Test
    @Transactional
    void getNonExistingUitkeringsrun() throws Exception {
        // Get the uitkeringsrun
        restUitkeringsrunMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUitkeringsrun() throws Exception {
        // Initialize the database
        uitkeringsrunRepository.saveAndFlush(uitkeringsrun);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitkeringsrun
        Uitkeringsrun updatedUitkeringsrun = uitkeringsrunRepository.findById(uitkeringsrun.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUitkeringsrun are not directly saved in db
        em.detach(updatedUitkeringsrun);
        updatedUitkeringsrun
            .datumrun(UPDATED_DATUMRUN)
            .frequentie(UPDATED_FREQUENTIE)
            .perioderun(UPDATED_PERIODERUN)
            .soortrun(UPDATED_SOORTRUN);

        restUitkeringsrunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUitkeringsrun.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedUitkeringsrun))
            )
            .andExpect(status().isOk());

        // Validate the Uitkeringsrun in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUitkeringsrunToMatchAllProperties(updatedUitkeringsrun);
    }

    @Test
    @Transactional
    void putNonExistingUitkeringsrun() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitkeringsrun.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUitkeringsrunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, uitkeringsrun.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uitkeringsrun))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitkeringsrun in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUitkeringsrun() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitkeringsrun.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitkeringsrunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uitkeringsrun))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitkeringsrun in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUitkeringsrun() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitkeringsrun.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitkeringsrunMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitkeringsrun)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uitkeringsrun in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUitkeringsrunWithPatch() throws Exception {
        // Initialize the database
        uitkeringsrunRepository.saveAndFlush(uitkeringsrun);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitkeringsrun using partial update
        Uitkeringsrun partialUpdatedUitkeringsrun = new Uitkeringsrun();
        partialUpdatedUitkeringsrun.setId(uitkeringsrun.getId());

        partialUpdatedUitkeringsrun.datumrun(UPDATED_DATUMRUN).perioderun(UPDATED_PERIODERUN);

        restUitkeringsrunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUitkeringsrun.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUitkeringsrun))
            )
            .andExpect(status().isOk());

        // Validate the Uitkeringsrun in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUitkeringsrunUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUitkeringsrun, uitkeringsrun),
            getPersistedUitkeringsrun(uitkeringsrun)
        );
    }

    @Test
    @Transactional
    void fullUpdateUitkeringsrunWithPatch() throws Exception {
        // Initialize the database
        uitkeringsrunRepository.saveAndFlush(uitkeringsrun);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitkeringsrun using partial update
        Uitkeringsrun partialUpdatedUitkeringsrun = new Uitkeringsrun();
        partialUpdatedUitkeringsrun.setId(uitkeringsrun.getId());

        partialUpdatedUitkeringsrun
            .datumrun(UPDATED_DATUMRUN)
            .frequentie(UPDATED_FREQUENTIE)
            .perioderun(UPDATED_PERIODERUN)
            .soortrun(UPDATED_SOORTRUN);

        restUitkeringsrunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUitkeringsrun.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUitkeringsrun))
            )
            .andExpect(status().isOk());

        // Validate the Uitkeringsrun in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUitkeringsrunUpdatableFieldsEquals(partialUpdatedUitkeringsrun, getPersistedUitkeringsrun(partialUpdatedUitkeringsrun));
    }

    @Test
    @Transactional
    void patchNonExistingUitkeringsrun() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitkeringsrun.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUitkeringsrunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, uitkeringsrun.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uitkeringsrun))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitkeringsrun in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUitkeringsrun() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitkeringsrun.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitkeringsrunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uitkeringsrun))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitkeringsrun in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUitkeringsrun() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitkeringsrun.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitkeringsrunMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(uitkeringsrun)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uitkeringsrun in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUitkeringsrun() throws Exception {
        // Initialize the database
        uitkeringsrunRepository.saveAndFlush(uitkeringsrun);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the uitkeringsrun
        restUitkeringsrunMockMvc
            .perform(delete(ENTITY_API_URL_ID, uitkeringsrun.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return uitkeringsrunRepository.count();
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

    protected Uitkeringsrun getPersistedUitkeringsrun(Uitkeringsrun uitkeringsrun) {
        return uitkeringsrunRepository.findById(uitkeringsrun.getId()).orElseThrow();
    }

    protected void assertPersistedUitkeringsrunToMatchAllProperties(Uitkeringsrun expectedUitkeringsrun) {
        assertUitkeringsrunAllPropertiesEquals(expectedUitkeringsrun, getPersistedUitkeringsrun(expectedUitkeringsrun));
    }

    protected void assertPersistedUitkeringsrunToMatchUpdatableProperties(Uitkeringsrun expectedUitkeringsrun) {
        assertUitkeringsrunAllUpdatablePropertiesEquals(expectedUitkeringsrun, getPersistedUitkeringsrun(expectedUitkeringsrun));
    }
}
