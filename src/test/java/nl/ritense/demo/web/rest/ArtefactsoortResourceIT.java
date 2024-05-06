package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ArtefactsoortAsserts.*;
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
import nl.ritense.demo.domain.Artefactsoort;
import nl.ritense.demo.repository.ArtefactsoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ArtefactsoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArtefactsoortResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/artefactsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ArtefactsoortRepository artefactsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtefactsoortMockMvc;

    private Artefactsoort artefactsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artefactsoort createEntity(EntityManager em) {
        Artefactsoort artefactsoort = new Artefactsoort().code(DEFAULT_CODE).naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return artefactsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artefactsoort createUpdatedEntity(EntityManager em) {
        Artefactsoort artefactsoort = new Artefactsoort().code(UPDATED_CODE).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return artefactsoort;
    }

    @BeforeEach
    public void initTest() {
        artefactsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createArtefactsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Artefactsoort
        var returnedArtefactsoort = om.readValue(
            restArtefactsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artefactsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Artefactsoort.class
        );

        // Validate the Artefactsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertArtefactsoortUpdatableFieldsEquals(returnedArtefactsoort, getPersistedArtefactsoort(returnedArtefactsoort));
    }

    @Test
    @Transactional
    void createArtefactsoortWithExistingId() throws Exception {
        // Create the Artefactsoort with an existing ID
        artefactsoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtefactsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artefactsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Artefactsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArtefactsoorts() throws Exception {
        // Initialize the database
        artefactsoortRepository.saveAndFlush(artefactsoort);

        // Get all the artefactsoortList
        restArtefactsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artefactsoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getArtefactsoort() throws Exception {
        // Initialize the database
        artefactsoortRepository.saveAndFlush(artefactsoort);

        // Get the artefactsoort
        restArtefactsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, artefactsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artefactsoort.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingArtefactsoort() throws Exception {
        // Get the artefactsoort
        restArtefactsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtefactsoort() throws Exception {
        // Initialize the database
        artefactsoortRepository.saveAndFlush(artefactsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artefactsoort
        Artefactsoort updatedArtefactsoort = artefactsoortRepository.findById(artefactsoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArtefactsoort are not directly saved in db
        em.detach(updatedArtefactsoort);
        updatedArtefactsoort.code(UPDATED_CODE).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restArtefactsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedArtefactsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedArtefactsoort))
            )
            .andExpect(status().isOk());

        // Validate the Artefactsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedArtefactsoortToMatchAllProperties(updatedArtefactsoort);
    }

    @Test
    @Transactional
    void putNonExistingArtefactsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artefactsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtefactsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artefactsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(artefactsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artefactsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtefactsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artefactsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtefactsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(artefactsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artefactsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtefactsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artefactsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtefactsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artefactsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Artefactsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtefactsoortWithPatch() throws Exception {
        // Initialize the database
        artefactsoortRepository.saveAndFlush(artefactsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artefactsoort using partial update
        Artefactsoort partialUpdatedArtefactsoort = new Artefactsoort();
        partialUpdatedArtefactsoort.setId(artefactsoort.getId());

        partialUpdatedArtefactsoort.code(UPDATED_CODE);

        restArtefactsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtefactsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArtefactsoort))
            )
            .andExpect(status().isOk());

        // Validate the Artefactsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArtefactsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedArtefactsoort, artefactsoort),
            getPersistedArtefactsoort(artefactsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateArtefactsoortWithPatch() throws Exception {
        // Initialize the database
        artefactsoortRepository.saveAndFlush(artefactsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artefactsoort using partial update
        Artefactsoort partialUpdatedArtefactsoort = new Artefactsoort();
        partialUpdatedArtefactsoort.setId(artefactsoort.getId());

        partialUpdatedArtefactsoort.code(UPDATED_CODE).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restArtefactsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtefactsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArtefactsoort))
            )
            .andExpect(status().isOk());

        // Validate the Artefactsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArtefactsoortUpdatableFieldsEquals(partialUpdatedArtefactsoort, getPersistedArtefactsoort(partialUpdatedArtefactsoort));
    }

    @Test
    @Transactional
    void patchNonExistingArtefactsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artefactsoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtefactsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artefactsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(artefactsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artefactsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtefactsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artefactsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtefactsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(artefactsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artefactsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtefactsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artefactsoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtefactsoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(artefactsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Artefactsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtefactsoort() throws Exception {
        // Initialize the database
        artefactsoortRepository.saveAndFlush(artefactsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the artefactsoort
        restArtefactsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, artefactsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return artefactsoortRepository.count();
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

    protected Artefactsoort getPersistedArtefactsoort(Artefactsoort artefactsoort) {
        return artefactsoortRepository.findById(artefactsoort.getId()).orElseThrow();
    }

    protected void assertPersistedArtefactsoortToMatchAllProperties(Artefactsoort expectedArtefactsoort) {
        assertArtefactsoortAllPropertiesEquals(expectedArtefactsoort, getPersistedArtefactsoort(expectedArtefactsoort));
    }

    protected void assertPersistedArtefactsoortToMatchUpdatableProperties(Artefactsoort expectedArtefactsoort) {
        assertArtefactsoortAllUpdatablePropertiesEquals(expectedArtefactsoort, getPersistedArtefactsoort(expectedArtefactsoort));
    }
}
