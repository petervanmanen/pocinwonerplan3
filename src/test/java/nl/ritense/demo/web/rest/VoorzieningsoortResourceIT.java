package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VoorzieningsoortAsserts.*;
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
import nl.ritense.demo.domain.Voorzieningsoort;
import nl.ritense.demo.repository.VoorzieningsoortRepository;
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
 * Integration tests for the {@link VoorzieningsoortResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class VoorzieningsoortResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCTCATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTCATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCTCATEGORIECODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTCATEGORIECODE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCTCODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_WET = "AAAAAAAAAA";
    private static final String UPDATED_WET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/voorzieningsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VoorzieningsoortRepository voorzieningsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoorzieningsoortMockMvc;

    private Voorzieningsoort voorzieningsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voorzieningsoort createEntity(EntityManager em) {
        Voorzieningsoort voorzieningsoort = new Voorzieningsoort()
            .code(DEFAULT_CODE)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .productcategorie(DEFAULT_PRODUCTCATEGORIE)
            .productcategoriecode(DEFAULT_PRODUCTCATEGORIECODE)
            .productcode(DEFAULT_PRODUCTCODE)
            .wet(DEFAULT_WET);
        return voorzieningsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voorzieningsoort createUpdatedEntity(EntityManager em) {
        Voorzieningsoort voorzieningsoort = new Voorzieningsoort()
            .code(UPDATED_CODE)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .productcategorie(UPDATED_PRODUCTCATEGORIE)
            .productcategoriecode(UPDATED_PRODUCTCATEGORIECODE)
            .productcode(UPDATED_PRODUCTCODE)
            .wet(UPDATED_WET);
        return voorzieningsoort;
    }

    @BeforeEach
    public void initTest() {
        voorzieningsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createVoorzieningsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Voorzieningsoort
        var returnedVoorzieningsoort = om.readValue(
            restVoorzieningsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voorzieningsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Voorzieningsoort.class
        );

        // Validate the Voorzieningsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVoorzieningsoortUpdatableFieldsEquals(returnedVoorzieningsoort, getPersistedVoorzieningsoort(returnedVoorzieningsoort));
    }

    @Test
    @Transactional
    void createVoorzieningsoortWithExistingId() throws Exception {
        // Create the Voorzieningsoort with an existing ID
        voorzieningsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoorzieningsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voorzieningsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Voorzieningsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVoorzieningsoorts() throws Exception {
        // Initialize the database
        voorzieningsoortRepository.saveAndFlush(voorzieningsoort);

        // Get all the voorzieningsoortList
        restVoorzieningsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voorzieningsoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].productcategorie").value(hasItem(DEFAULT_PRODUCTCATEGORIE)))
            .andExpect(jsonPath("$.[*].productcategoriecode").value(hasItem(DEFAULT_PRODUCTCATEGORIECODE)))
            .andExpect(jsonPath("$.[*].productcode").value(hasItem(DEFAULT_PRODUCTCODE)))
            .andExpect(jsonPath("$.[*].wet").value(hasItem(DEFAULT_WET)));
    }

    @Test
    @Transactional
    void getVoorzieningsoort() throws Exception {
        // Initialize the database
        voorzieningsoortRepository.saveAndFlush(voorzieningsoort);

        // Get the voorzieningsoort
        restVoorzieningsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, voorzieningsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voorzieningsoort.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.productcategorie").value(DEFAULT_PRODUCTCATEGORIE))
            .andExpect(jsonPath("$.productcategoriecode").value(DEFAULT_PRODUCTCATEGORIECODE))
            .andExpect(jsonPath("$.productcode").value(DEFAULT_PRODUCTCODE))
            .andExpect(jsonPath("$.wet").value(DEFAULT_WET));
    }

    @Test
    @Transactional
    void getNonExistingVoorzieningsoort() throws Exception {
        // Get the voorzieningsoort
        restVoorzieningsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVoorzieningsoort() throws Exception {
        // Initialize the database
        voorzieningsoortRepository.saveAndFlush(voorzieningsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voorzieningsoort
        Voorzieningsoort updatedVoorzieningsoort = voorzieningsoortRepository.findById(voorzieningsoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVoorzieningsoort are not directly saved in db
        em.detach(updatedVoorzieningsoort);
        updatedVoorzieningsoort
            .code(UPDATED_CODE)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .productcategorie(UPDATED_PRODUCTCATEGORIE)
            .productcategoriecode(UPDATED_PRODUCTCATEGORIECODE)
            .productcode(UPDATED_PRODUCTCODE)
            .wet(UPDATED_WET);

        restVoorzieningsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVoorzieningsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVoorzieningsoort))
            )
            .andExpect(status().isOk());

        // Validate the Voorzieningsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVoorzieningsoortToMatchAllProperties(updatedVoorzieningsoort);
    }

    @Test
    @Transactional
    void putNonExistingVoorzieningsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voorzieningsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoorzieningsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voorzieningsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(voorzieningsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voorzieningsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoorzieningsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voorzieningsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoorzieningsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(voorzieningsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voorzieningsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoorzieningsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voorzieningsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoorzieningsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voorzieningsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voorzieningsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoorzieningsoortWithPatch() throws Exception {
        // Initialize the database
        voorzieningsoortRepository.saveAndFlush(voorzieningsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voorzieningsoort using partial update
        Voorzieningsoort partialUpdatedVoorzieningsoort = new Voorzieningsoort();
        partialUpdatedVoorzieningsoort.setId(voorzieningsoort.getId());

        partialUpdatedVoorzieningsoort
            .code(UPDATED_CODE)
            .naam(UPDATED_NAAM)
            .productcategorie(UPDATED_PRODUCTCATEGORIE)
            .productcategoriecode(UPDATED_PRODUCTCATEGORIECODE)
            .productcode(UPDATED_PRODUCTCODE);

        restVoorzieningsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoorzieningsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVoorzieningsoort))
            )
            .andExpect(status().isOk());

        // Validate the Voorzieningsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVoorzieningsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVoorzieningsoort, voorzieningsoort),
            getPersistedVoorzieningsoort(voorzieningsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateVoorzieningsoortWithPatch() throws Exception {
        // Initialize the database
        voorzieningsoortRepository.saveAndFlush(voorzieningsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voorzieningsoort using partial update
        Voorzieningsoort partialUpdatedVoorzieningsoort = new Voorzieningsoort();
        partialUpdatedVoorzieningsoort.setId(voorzieningsoort.getId());

        partialUpdatedVoorzieningsoort
            .code(UPDATED_CODE)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .productcategorie(UPDATED_PRODUCTCATEGORIE)
            .productcategoriecode(UPDATED_PRODUCTCATEGORIECODE)
            .productcode(UPDATED_PRODUCTCODE)
            .wet(UPDATED_WET);

        restVoorzieningsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoorzieningsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVoorzieningsoort))
            )
            .andExpect(status().isOk());

        // Validate the Voorzieningsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVoorzieningsoortUpdatableFieldsEquals(
            partialUpdatedVoorzieningsoort,
            getPersistedVoorzieningsoort(partialUpdatedVoorzieningsoort)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVoorzieningsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voorzieningsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoorzieningsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voorzieningsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(voorzieningsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voorzieningsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoorzieningsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voorzieningsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoorzieningsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(voorzieningsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voorzieningsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoorzieningsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voorzieningsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoorzieningsoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(voorzieningsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voorzieningsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoorzieningsoort() throws Exception {
        // Initialize the database
        voorzieningsoortRepository.saveAndFlush(voorzieningsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the voorzieningsoort
        restVoorzieningsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, voorzieningsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return voorzieningsoortRepository.count();
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

    protected Voorzieningsoort getPersistedVoorzieningsoort(Voorzieningsoort voorzieningsoort) {
        return voorzieningsoortRepository.findById(voorzieningsoort.getId()).orElseThrow();
    }

    protected void assertPersistedVoorzieningsoortToMatchAllProperties(Voorzieningsoort expectedVoorzieningsoort) {
        assertVoorzieningsoortAllPropertiesEquals(expectedVoorzieningsoort, getPersistedVoorzieningsoort(expectedVoorzieningsoort));
    }

    protected void assertPersistedVoorzieningsoortToMatchUpdatableProperties(Voorzieningsoort expectedVoorzieningsoort) {
        assertVoorzieningsoortAllUpdatablePropertiesEquals(
            expectedVoorzieningsoort,
            getPersistedVoorzieningsoort(expectedVoorzieningsoort)
        );
    }
}
