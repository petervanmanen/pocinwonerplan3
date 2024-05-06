package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerplichtingwmojeugdAsserts.*;
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
import nl.ritense.demo.domain.Verplichtingwmojeugd;
import nl.ritense.demo.repository.VerplichtingwmojeugdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerplichtingwmojeugdResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerplichtingwmojeugdResourceIT {

    private static final String DEFAULT_BUDGETSOORT = "AAAAAAAAAA";
    private static final String UPDATED_BUDGETSOORT = "BBBBBBBBBB";

    private static final String DEFAULT_BUDGETSOORTGROEP = "AAAAAAAAAA";
    private static final String UPDATED_BUDGETSOORTGROEP = "BBBBBBBBBB";

    private static final String DEFAULT_EINDDATUMGEPLAND = "AAAAAAAAAA";
    private static final String UPDATED_EINDDATUMGEPLAND = "BBBBBBBBBB";

    private static final String DEFAULT_FEITELIJKEEINDDATUM = "AAAAAAAAAA";
    private static final String UPDATED_FEITELIJKEEINDDATUM = "BBBBBBBBBB";

    private static final String DEFAULT_JAAR = "AAAAAAAAAA";
    private static final String UPDATED_JAAR = "BBBBBBBBBB";

    private static final String DEFAULT_PERIODICITEIT = "AAAAAAAAAA";
    private static final String UPDATED_PERIODICITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_VERPLICHTINGSOORT = "AAAAAAAAAA";
    private static final String UPDATED_VERPLICHTINGSOORT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verplichtingwmojeugds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerplichtingwmojeugdRepository verplichtingwmojeugdRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerplichtingwmojeugdMockMvc;

    private Verplichtingwmojeugd verplichtingwmojeugd;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verplichtingwmojeugd createEntity(EntityManager em) {
        Verplichtingwmojeugd verplichtingwmojeugd = new Verplichtingwmojeugd()
            .budgetsoort(DEFAULT_BUDGETSOORT)
            .budgetsoortgroep(DEFAULT_BUDGETSOORTGROEP)
            .einddatumgepland(DEFAULT_EINDDATUMGEPLAND)
            .feitelijkeeinddatum(DEFAULT_FEITELIJKEEINDDATUM)
            .jaar(DEFAULT_JAAR)
            .periodiciteit(DEFAULT_PERIODICITEIT)
            .verplichtingsoort(DEFAULT_VERPLICHTINGSOORT);
        return verplichtingwmojeugd;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verplichtingwmojeugd createUpdatedEntity(EntityManager em) {
        Verplichtingwmojeugd verplichtingwmojeugd = new Verplichtingwmojeugd()
            .budgetsoort(UPDATED_BUDGETSOORT)
            .budgetsoortgroep(UPDATED_BUDGETSOORTGROEP)
            .einddatumgepland(UPDATED_EINDDATUMGEPLAND)
            .feitelijkeeinddatum(UPDATED_FEITELIJKEEINDDATUM)
            .jaar(UPDATED_JAAR)
            .periodiciteit(UPDATED_PERIODICITEIT)
            .verplichtingsoort(UPDATED_VERPLICHTINGSOORT);
        return verplichtingwmojeugd;
    }

    @BeforeEach
    public void initTest() {
        verplichtingwmojeugd = createEntity(em);
    }

    @Test
    @Transactional
    void createVerplichtingwmojeugd() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verplichtingwmojeugd
        var returnedVerplichtingwmojeugd = om.readValue(
            restVerplichtingwmojeugdMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verplichtingwmojeugd)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verplichtingwmojeugd.class
        );

        // Validate the Verplichtingwmojeugd in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerplichtingwmojeugdUpdatableFieldsEquals(
            returnedVerplichtingwmojeugd,
            getPersistedVerplichtingwmojeugd(returnedVerplichtingwmojeugd)
        );
    }

    @Test
    @Transactional
    void createVerplichtingwmojeugdWithExistingId() throws Exception {
        // Create the Verplichtingwmojeugd with an existing ID
        verplichtingwmojeugd.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerplichtingwmojeugdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verplichtingwmojeugd)))
            .andExpect(status().isBadRequest());

        // Validate the Verplichtingwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerplichtingwmojeugds() throws Exception {
        // Initialize the database
        verplichtingwmojeugdRepository.saveAndFlush(verplichtingwmojeugd);

        // Get all the verplichtingwmojeugdList
        restVerplichtingwmojeugdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verplichtingwmojeugd.getId().intValue())))
            .andExpect(jsonPath("$.[*].budgetsoort").value(hasItem(DEFAULT_BUDGETSOORT)))
            .andExpect(jsonPath("$.[*].budgetsoortgroep").value(hasItem(DEFAULT_BUDGETSOORTGROEP)))
            .andExpect(jsonPath("$.[*].einddatumgepland").value(hasItem(DEFAULT_EINDDATUMGEPLAND)))
            .andExpect(jsonPath("$.[*].feitelijkeeinddatum").value(hasItem(DEFAULT_FEITELIJKEEINDDATUM)))
            .andExpect(jsonPath("$.[*].jaar").value(hasItem(DEFAULT_JAAR)))
            .andExpect(jsonPath("$.[*].periodiciteit").value(hasItem(DEFAULT_PERIODICITEIT)))
            .andExpect(jsonPath("$.[*].verplichtingsoort").value(hasItem(DEFAULT_VERPLICHTINGSOORT)));
    }

    @Test
    @Transactional
    void getVerplichtingwmojeugd() throws Exception {
        // Initialize the database
        verplichtingwmojeugdRepository.saveAndFlush(verplichtingwmojeugd);

        // Get the verplichtingwmojeugd
        restVerplichtingwmojeugdMockMvc
            .perform(get(ENTITY_API_URL_ID, verplichtingwmojeugd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verplichtingwmojeugd.getId().intValue()))
            .andExpect(jsonPath("$.budgetsoort").value(DEFAULT_BUDGETSOORT))
            .andExpect(jsonPath("$.budgetsoortgroep").value(DEFAULT_BUDGETSOORTGROEP))
            .andExpect(jsonPath("$.einddatumgepland").value(DEFAULT_EINDDATUMGEPLAND))
            .andExpect(jsonPath("$.feitelijkeeinddatum").value(DEFAULT_FEITELIJKEEINDDATUM))
            .andExpect(jsonPath("$.jaar").value(DEFAULT_JAAR))
            .andExpect(jsonPath("$.periodiciteit").value(DEFAULT_PERIODICITEIT))
            .andExpect(jsonPath("$.verplichtingsoort").value(DEFAULT_VERPLICHTINGSOORT));
    }

    @Test
    @Transactional
    void getNonExistingVerplichtingwmojeugd() throws Exception {
        // Get the verplichtingwmojeugd
        restVerplichtingwmojeugdMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerplichtingwmojeugd() throws Exception {
        // Initialize the database
        verplichtingwmojeugdRepository.saveAndFlush(verplichtingwmojeugd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verplichtingwmojeugd
        Verplichtingwmojeugd updatedVerplichtingwmojeugd = verplichtingwmojeugdRepository
            .findById(verplichtingwmojeugd.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedVerplichtingwmojeugd are not directly saved in db
        em.detach(updatedVerplichtingwmojeugd);
        updatedVerplichtingwmojeugd
            .budgetsoort(UPDATED_BUDGETSOORT)
            .budgetsoortgroep(UPDATED_BUDGETSOORTGROEP)
            .einddatumgepland(UPDATED_EINDDATUMGEPLAND)
            .feitelijkeeinddatum(UPDATED_FEITELIJKEEINDDATUM)
            .jaar(UPDATED_JAAR)
            .periodiciteit(UPDATED_PERIODICITEIT)
            .verplichtingsoort(UPDATED_VERPLICHTINGSOORT);

        restVerplichtingwmojeugdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerplichtingwmojeugd.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerplichtingwmojeugd))
            )
            .andExpect(status().isOk());

        // Validate the Verplichtingwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerplichtingwmojeugdToMatchAllProperties(updatedVerplichtingwmojeugd);
    }

    @Test
    @Transactional
    void putNonExistingVerplichtingwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verplichtingwmojeugd.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerplichtingwmojeugdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verplichtingwmojeugd.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verplichtingwmojeugd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verplichtingwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerplichtingwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verplichtingwmojeugd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerplichtingwmojeugdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verplichtingwmojeugd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verplichtingwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerplichtingwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verplichtingwmojeugd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerplichtingwmojeugdMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verplichtingwmojeugd)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verplichtingwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerplichtingwmojeugdWithPatch() throws Exception {
        // Initialize the database
        verplichtingwmojeugdRepository.saveAndFlush(verplichtingwmojeugd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verplichtingwmojeugd using partial update
        Verplichtingwmojeugd partialUpdatedVerplichtingwmojeugd = new Verplichtingwmojeugd();
        partialUpdatedVerplichtingwmojeugd.setId(verplichtingwmojeugd.getId());

        partialUpdatedVerplichtingwmojeugd
            .budgetsoortgroep(UPDATED_BUDGETSOORTGROEP)
            .einddatumgepland(UPDATED_EINDDATUMGEPLAND)
            .periodiciteit(UPDATED_PERIODICITEIT);

        restVerplichtingwmojeugdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerplichtingwmojeugd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerplichtingwmojeugd))
            )
            .andExpect(status().isOk());

        // Validate the Verplichtingwmojeugd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerplichtingwmojeugdUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerplichtingwmojeugd, verplichtingwmojeugd),
            getPersistedVerplichtingwmojeugd(verplichtingwmojeugd)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerplichtingwmojeugdWithPatch() throws Exception {
        // Initialize the database
        verplichtingwmojeugdRepository.saveAndFlush(verplichtingwmojeugd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verplichtingwmojeugd using partial update
        Verplichtingwmojeugd partialUpdatedVerplichtingwmojeugd = new Verplichtingwmojeugd();
        partialUpdatedVerplichtingwmojeugd.setId(verplichtingwmojeugd.getId());

        partialUpdatedVerplichtingwmojeugd
            .budgetsoort(UPDATED_BUDGETSOORT)
            .budgetsoortgroep(UPDATED_BUDGETSOORTGROEP)
            .einddatumgepland(UPDATED_EINDDATUMGEPLAND)
            .feitelijkeeinddatum(UPDATED_FEITELIJKEEINDDATUM)
            .jaar(UPDATED_JAAR)
            .periodiciteit(UPDATED_PERIODICITEIT)
            .verplichtingsoort(UPDATED_VERPLICHTINGSOORT);

        restVerplichtingwmojeugdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerplichtingwmojeugd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerplichtingwmojeugd))
            )
            .andExpect(status().isOk());

        // Validate the Verplichtingwmojeugd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerplichtingwmojeugdUpdatableFieldsEquals(
            partialUpdatedVerplichtingwmojeugd,
            getPersistedVerplichtingwmojeugd(partialUpdatedVerplichtingwmojeugd)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVerplichtingwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verplichtingwmojeugd.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerplichtingwmojeugdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verplichtingwmojeugd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verplichtingwmojeugd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verplichtingwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerplichtingwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verplichtingwmojeugd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerplichtingwmojeugdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verplichtingwmojeugd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verplichtingwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerplichtingwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verplichtingwmojeugd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerplichtingwmojeugdMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verplichtingwmojeugd)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verplichtingwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerplichtingwmojeugd() throws Exception {
        // Initialize the database
        verplichtingwmojeugdRepository.saveAndFlush(verplichtingwmojeugd);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verplichtingwmojeugd
        restVerplichtingwmojeugdMockMvc
            .perform(delete(ENTITY_API_URL_ID, verplichtingwmojeugd.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verplichtingwmojeugdRepository.count();
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

    protected Verplichtingwmojeugd getPersistedVerplichtingwmojeugd(Verplichtingwmojeugd verplichtingwmojeugd) {
        return verplichtingwmojeugdRepository.findById(verplichtingwmojeugd.getId()).orElseThrow();
    }

    protected void assertPersistedVerplichtingwmojeugdToMatchAllProperties(Verplichtingwmojeugd expectedVerplichtingwmojeugd) {
        assertVerplichtingwmojeugdAllPropertiesEquals(
            expectedVerplichtingwmojeugd,
            getPersistedVerplichtingwmojeugd(expectedVerplichtingwmojeugd)
        );
    }

    protected void assertPersistedVerplichtingwmojeugdToMatchUpdatableProperties(Verplichtingwmojeugd expectedVerplichtingwmojeugd) {
        assertVerplichtingwmojeugdAllUpdatablePropertiesEquals(
            expectedVerplichtingwmojeugd,
            getPersistedVerplichtingwmojeugd(expectedVerplichtingwmojeugd)
        );
    }
}
