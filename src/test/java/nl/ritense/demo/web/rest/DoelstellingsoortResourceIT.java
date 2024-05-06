package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DoelstellingsoortAsserts.*;
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
import nl.ritense.demo.domain.Doelstellingsoort;
import nl.ritense.demo.repository.DoelstellingsoortRepository;
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
 * Integration tests for the {@link DoelstellingsoortResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class DoelstellingsoortResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/doelstellingsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DoelstellingsoortRepository doelstellingsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoelstellingsoortMockMvc;

    private Doelstellingsoort doelstellingsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doelstellingsoort createEntity(EntityManager em) {
        Doelstellingsoort doelstellingsoort = new Doelstellingsoort().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return doelstellingsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doelstellingsoort createUpdatedEntity(EntityManager em) {
        Doelstellingsoort doelstellingsoort = new Doelstellingsoort().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return doelstellingsoort;
    }

    @BeforeEach
    public void initTest() {
        doelstellingsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createDoelstellingsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Doelstellingsoort
        var returnedDoelstellingsoort = om.readValue(
            restDoelstellingsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doelstellingsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Doelstellingsoort.class
        );

        // Validate the Doelstellingsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDoelstellingsoortUpdatableFieldsEquals(returnedDoelstellingsoort, getPersistedDoelstellingsoort(returnedDoelstellingsoort));
    }

    @Test
    @Transactional
    void createDoelstellingsoortWithExistingId() throws Exception {
        // Create the Doelstellingsoort with an existing ID
        doelstellingsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoelstellingsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doelstellingsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Doelstellingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDoelstellingsoorts() throws Exception {
        // Initialize the database
        doelstellingsoortRepository.saveAndFlush(doelstellingsoort);

        // Get all the doelstellingsoortList
        restDoelstellingsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doelstellingsoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getDoelstellingsoort() throws Exception {
        // Initialize the database
        doelstellingsoortRepository.saveAndFlush(doelstellingsoort);

        // Get the doelstellingsoort
        restDoelstellingsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, doelstellingsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doelstellingsoort.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingDoelstellingsoort() throws Exception {
        // Get the doelstellingsoort
        restDoelstellingsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDoelstellingsoort() throws Exception {
        // Initialize the database
        doelstellingsoortRepository.saveAndFlush(doelstellingsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doelstellingsoort
        Doelstellingsoort updatedDoelstellingsoort = doelstellingsoortRepository.findById(doelstellingsoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDoelstellingsoort are not directly saved in db
        em.detach(updatedDoelstellingsoort);
        updatedDoelstellingsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restDoelstellingsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDoelstellingsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDoelstellingsoort))
            )
            .andExpect(status().isOk());

        // Validate the Doelstellingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDoelstellingsoortToMatchAllProperties(updatedDoelstellingsoort);
    }

    @Test
    @Transactional
    void putNonExistingDoelstellingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelstellingsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoelstellingsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doelstellingsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(doelstellingsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doelstellingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDoelstellingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelstellingsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoelstellingsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(doelstellingsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doelstellingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDoelstellingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelstellingsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoelstellingsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doelstellingsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doelstellingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDoelstellingsoortWithPatch() throws Exception {
        // Initialize the database
        doelstellingsoortRepository.saveAndFlush(doelstellingsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doelstellingsoort using partial update
        Doelstellingsoort partialUpdatedDoelstellingsoort = new Doelstellingsoort();
        partialUpdatedDoelstellingsoort.setId(doelstellingsoort.getId());

        restDoelstellingsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoelstellingsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDoelstellingsoort))
            )
            .andExpect(status().isOk());

        // Validate the Doelstellingsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDoelstellingsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDoelstellingsoort, doelstellingsoort),
            getPersistedDoelstellingsoort(doelstellingsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateDoelstellingsoortWithPatch() throws Exception {
        // Initialize the database
        doelstellingsoortRepository.saveAndFlush(doelstellingsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doelstellingsoort using partial update
        Doelstellingsoort partialUpdatedDoelstellingsoort = new Doelstellingsoort();
        partialUpdatedDoelstellingsoort.setId(doelstellingsoort.getId());

        partialUpdatedDoelstellingsoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restDoelstellingsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoelstellingsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDoelstellingsoort))
            )
            .andExpect(status().isOk());

        // Validate the Doelstellingsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDoelstellingsoortUpdatableFieldsEquals(
            partialUpdatedDoelstellingsoort,
            getPersistedDoelstellingsoort(partialUpdatedDoelstellingsoort)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDoelstellingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelstellingsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoelstellingsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, doelstellingsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(doelstellingsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doelstellingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDoelstellingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelstellingsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoelstellingsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(doelstellingsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doelstellingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDoelstellingsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doelstellingsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoelstellingsoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(doelstellingsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doelstellingsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDoelstellingsoort() throws Exception {
        // Initialize the database
        doelstellingsoortRepository.saveAndFlush(doelstellingsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the doelstellingsoort
        restDoelstellingsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, doelstellingsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return doelstellingsoortRepository.count();
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

    protected Doelstellingsoort getPersistedDoelstellingsoort(Doelstellingsoort doelstellingsoort) {
        return doelstellingsoortRepository.findById(doelstellingsoort.getId()).orElseThrow();
    }

    protected void assertPersistedDoelstellingsoortToMatchAllProperties(Doelstellingsoort expectedDoelstellingsoort) {
        assertDoelstellingsoortAllPropertiesEquals(expectedDoelstellingsoort, getPersistedDoelstellingsoort(expectedDoelstellingsoort));
    }

    protected void assertPersistedDoelstellingsoortToMatchUpdatableProperties(Doelstellingsoort expectedDoelstellingsoort) {
        assertDoelstellingsoortAllUpdatablePropertiesEquals(
            expectedDoelstellingsoort,
            getPersistedDoelstellingsoort(expectedDoelstellingsoort)
        );
    }
}
