package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.UitstroomredensoortAsserts.*;
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
import nl.ritense.demo.domain.Uitstroomredensoort;
import nl.ritense.demo.repository.UitstroomredensoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UitstroomredensoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UitstroomredensoortResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/uitstroomredensoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UitstroomredensoortRepository uitstroomredensoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUitstroomredensoortMockMvc;

    private Uitstroomredensoort uitstroomredensoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitstroomredensoort createEntity(EntityManager em) {
        Uitstroomredensoort uitstroomredensoort = new Uitstroomredensoort().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return uitstroomredensoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitstroomredensoort createUpdatedEntity(EntityManager em) {
        Uitstroomredensoort uitstroomredensoort = new Uitstroomredensoort().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return uitstroomredensoort;
    }

    @BeforeEach
    public void initTest() {
        uitstroomredensoort = createEntity(em);
    }

    @Test
    @Transactional
    void createUitstroomredensoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Uitstroomredensoort
        var returnedUitstroomredensoort = om.readValue(
            restUitstroomredensoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitstroomredensoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Uitstroomredensoort.class
        );

        // Validate the Uitstroomredensoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertUitstroomredensoortUpdatableFieldsEquals(
            returnedUitstroomredensoort,
            getPersistedUitstroomredensoort(returnedUitstroomredensoort)
        );
    }

    @Test
    @Transactional
    void createUitstroomredensoortWithExistingId() throws Exception {
        // Create the Uitstroomredensoort with an existing ID
        uitstroomredensoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUitstroomredensoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitstroomredensoort)))
            .andExpect(status().isBadRequest());

        // Validate the Uitstroomredensoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUitstroomredensoorts() throws Exception {
        // Initialize the database
        uitstroomredensoortRepository.saveAndFlush(uitstroomredensoort);

        // Get all the uitstroomredensoortList
        restUitstroomredensoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uitstroomredensoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getUitstroomredensoort() throws Exception {
        // Initialize the database
        uitstroomredensoortRepository.saveAndFlush(uitstroomredensoort);

        // Get the uitstroomredensoort
        restUitstroomredensoortMockMvc
            .perform(get(ENTITY_API_URL_ID, uitstroomredensoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uitstroomredensoort.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingUitstroomredensoort() throws Exception {
        // Get the uitstroomredensoort
        restUitstroomredensoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUitstroomredensoort() throws Exception {
        // Initialize the database
        uitstroomredensoortRepository.saveAndFlush(uitstroomredensoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitstroomredensoort
        Uitstroomredensoort updatedUitstroomredensoort = uitstroomredensoortRepository.findById(uitstroomredensoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUitstroomredensoort are not directly saved in db
        em.detach(updatedUitstroomredensoort);
        updatedUitstroomredensoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restUitstroomredensoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUitstroomredensoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedUitstroomredensoort))
            )
            .andExpect(status().isOk());

        // Validate the Uitstroomredensoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUitstroomredensoortToMatchAllProperties(updatedUitstroomredensoort);
    }

    @Test
    @Transactional
    void putNonExistingUitstroomredensoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitstroomredensoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUitstroomredensoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, uitstroomredensoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uitstroomredensoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitstroomredensoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUitstroomredensoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitstroomredensoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitstroomredensoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uitstroomredensoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitstroomredensoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUitstroomredensoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitstroomredensoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitstroomredensoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitstroomredensoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uitstroomredensoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUitstroomredensoortWithPatch() throws Exception {
        // Initialize the database
        uitstroomredensoortRepository.saveAndFlush(uitstroomredensoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitstroomredensoort using partial update
        Uitstroomredensoort partialUpdatedUitstroomredensoort = new Uitstroomredensoort();
        partialUpdatedUitstroomredensoort.setId(uitstroomredensoort.getId());

        partialUpdatedUitstroomredensoort.naam(UPDATED_NAAM);

        restUitstroomredensoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUitstroomredensoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUitstroomredensoort))
            )
            .andExpect(status().isOk());

        // Validate the Uitstroomredensoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUitstroomredensoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUitstroomredensoort, uitstroomredensoort),
            getPersistedUitstroomredensoort(uitstroomredensoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateUitstroomredensoortWithPatch() throws Exception {
        // Initialize the database
        uitstroomredensoortRepository.saveAndFlush(uitstroomredensoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitstroomredensoort using partial update
        Uitstroomredensoort partialUpdatedUitstroomredensoort = new Uitstroomredensoort();
        partialUpdatedUitstroomredensoort.setId(uitstroomredensoort.getId());

        partialUpdatedUitstroomredensoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restUitstroomredensoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUitstroomredensoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUitstroomredensoort))
            )
            .andExpect(status().isOk());

        // Validate the Uitstroomredensoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUitstroomredensoortUpdatableFieldsEquals(
            partialUpdatedUitstroomredensoort,
            getPersistedUitstroomredensoort(partialUpdatedUitstroomredensoort)
        );
    }

    @Test
    @Transactional
    void patchNonExistingUitstroomredensoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitstroomredensoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUitstroomredensoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, uitstroomredensoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uitstroomredensoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitstroomredensoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUitstroomredensoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitstroomredensoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitstroomredensoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uitstroomredensoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitstroomredensoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUitstroomredensoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitstroomredensoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitstroomredensoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(uitstroomredensoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uitstroomredensoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUitstroomredensoort() throws Exception {
        // Initialize the database
        uitstroomredensoortRepository.saveAndFlush(uitstroomredensoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the uitstroomredensoort
        restUitstroomredensoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, uitstroomredensoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return uitstroomredensoortRepository.count();
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

    protected Uitstroomredensoort getPersistedUitstroomredensoort(Uitstroomredensoort uitstroomredensoort) {
        return uitstroomredensoortRepository.findById(uitstroomredensoort.getId()).orElseThrow();
    }

    protected void assertPersistedUitstroomredensoortToMatchAllProperties(Uitstroomredensoort expectedUitstroomredensoort) {
        assertUitstroomredensoortAllPropertiesEquals(
            expectedUitstroomredensoort,
            getPersistedUitstroomredensoort(expectedUitstroomredensoort)
        );
    }

    protected void assertPersistedUitstroomredensoortToMatchUpdatableProperties(Uitstroomredensoort expectedUitstroomredensoort) {
        assertUitstroomredensoortAllUpdatablePropertiesEquals(
            expectedUitstroomredensoort,
            getPersistedUitstroomredensoort(expectedUitstroomredensoort)
        );
    }
}
