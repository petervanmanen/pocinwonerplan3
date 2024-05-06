package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DeclaratiesoortAsserts.*;
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
import nl.ritense.demo.domain.Declaratiesoort;
import nl.ritense.demo.repository.DeclaratiesoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DeclaratiesoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeclaratiesoortResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/declaratiesoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DeclaratiesoortRepository declaratiesoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeclaratiesoortMockMvc;

    private Declaratiesoort declaratiesoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Declaratiesoort createEntity(EntityManager em) {
        Declaratiesoort declaratiesoort = new Declaratiesoort().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return declaratiesoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Declaratiesoort createUpdatedEntity(EntityManager em) {
        Declaratiesoort declaratiesoort = new Declaratiesoort().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return declaratiesoort;
    }

    @BeforeEach
    public void initTest() {
        declaratiesoort = createEntity(em);
    }

    @Test
    @Transactional
    void createDeclaratiesoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Declaratiesoort
        var returnedDeclaratiesoort = om.readValue(
            restDeclaratiesoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(declaratiesoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Declaratiesoort.class
        );

        // Validate the Declaratiesoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDeclaratiesoortUpdatableFieldsEquals(returnedDeclaratiesoort, getPersistedDeclaratiesoort(returnedDeclaratiesoort));
    }

    @Test
    @Transactional
    void createDeclaratiesoortWithExistingId() throws Exception {
        // Create the Declaratiesoort with an existing ID
        declaratiesoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeclaratiesoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(declaratiesoort)))
            .andExpect(status().isBadRequest());

        // Validate the Declaratiesoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeclaratiesoorts() throws Exception {
        // Initialize the database
        declaratiesoortRepository.saveAndFlush(declaratiesoort);

        // Get all the declaratiesoortList
        restDeclaratiesoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(declaratiesoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getDeclaratiesoort() throws Exception {
        // Initialize the database
        declaratiesoortRepository.saveAndFlush(declaratiesoort);

        // Get the declaratiesoort
        restDeclaratiesoortMockMvc
            .perform(get(ENTITY_API_URL_ID, declaratiesoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(declaratiesoort.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingDeclaratiesoort() throws Exception {
        // Get the declaratiesoort
        restDeclaratiesoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDeclaratiesoort() throws Exception {
        // Initialize the database
        declaratiesoortRepository.saveAndFlush(declaratiesoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the declaratiesoort
        Declaratiesoort updatedDeclaratiesoort = declaratiesoortRepository.findById(declaratiesoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDeclaratiesoort are not directly saved in db
        em.detach(updatedDeclaratiesoort);
        updatedDeclaratiesoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restDeclaratiesoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDeclaratiesoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDeclaratiesoort))
            )
            .andExpect(status().isOk());

        // Validate the Declaratiesoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDeclaratiesoortToMatchAllProperties(updatedDeclaratiesoort);
    }

    @Test
    @Transactional
    void putNonExistingDeclaratiesoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratiesoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeclaratiesoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, declaratiesoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(declaratiesoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Declaratiesoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeclaratiesoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratiesoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclaratiesoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(declaratiesoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Declaratiesoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeclaratiesoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratiesoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclaratiesoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(declaratiesoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Declaratiesoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeclaratiesoortWithPatch() throws Exception {
        // Initialize the database
        declaratiesoortRepository.saveAndFlush(declaratiesoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the declaratiesoort using partial update
        Declaratiesoort partialUpdatedDeclaratiesoort = new Declaratiesoort();
        partialUpdatedDeclaratiesoort.setId(declaratiesoort.getId());

        partialUpdatedDeclaratiesoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restDeclaratiesoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeclaratiesoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDeclaratiesoort))
            )
            .andExpect(status().isOk());

        // Validate the Declaratiesoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDeclaratiesoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDeclaratiesoort, declaratiesoort),
            getPersistedDeclaratiesoort(declaratiesoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateDeclaratiesoortWithPatch() throws Exception {
        // Initialize the database
        declaratiesoortRepository.saveAndFlush(declaratiesoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the declaratiesoort using partial update
        Declaratiesoort partialUpdatedDeclaratiesoort = new Declaratiesoort();
        partialUpdatedDeclaratiesoort.setId(declaratiesoort.getId());

        partialUpdatedDeclaratiesoort.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restDeclaratiesoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeclaratiesoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDeclaratiesoort))
            )
            .andExpect(status().isOk());

        // Validate the Declaratiesoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDeclaratiesoortUpdatableFieldsEquals(
            partialUpdatedDeclaratiesoort,
            getPersistedDeclaratiesoort(partialUpdatedDeclaratiesoort)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDeclaratiesoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratiesoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeclaratiesoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, declaratiesoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(declaratiesoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Declaratiesoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeclaratiesoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratiesoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclaratiesoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(declaratiesoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Declaratiesoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeclaratiesoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratiesoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclaratiesoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(declaratiesoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Declaratiesoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeclaratiesoort() throws Exception {
        // Initialize the database
        declaratiesoortRepository.saveAndFlush(declaratiesoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the declaratiesoort
        restDeclaratiesoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, declaratiesoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return declaratiesoortRepository.count();
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

    protected Declaratiesoort getPersistedDeclaratiesoort(Declaratiesoort declaratiesoort) {
        return declaratiesoortRepository.findById(declaratiesoort.getId()).orElseThrow();
    }

    protected void assertPersistedDeclaratiesoortToMatchAllProperties(Declaratiesoort expectedDeclaratiesoort) {
        assertDeclaratiesoortAllPropertiesEquals(expectedDeclaratiesoort, getPersistedDeclaratiesoort(expectedDeclaratiesoort));
    }

    protected void assertPersistedDeclaratiesoortToMatchUpdatableProperties(Declaratiesoort expectedDeclaratiesoort) {
        assertDeclaratiesoortAllUpdatablePropertiesEquals(expectedDeclaratiesoort, getPersistedDeclaratiesoort(expectedDeclaratiesoort));
    }
}
