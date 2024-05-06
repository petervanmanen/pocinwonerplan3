package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AnderzaakobjectzaakAsserts.*;
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
import nl.ritense.demo.domain.Anderzaakobjectzaak;
import nl.ritense.demo.repository.AnderzaakobjectzaakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AnderzaakobjectzaakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnderzaakobjectzaakResourceIT {

    private static final String DEFAULT_ANDERZAAKOBJECTAANDUIDING = "AAAAAAAAAA";
    private static final String UPDATED_ANDERZAAKOBJECTAANDUIDING = "BBBBBBBBBB";

    private static final String DEFAULT_ANDERZAAKOBJECTLOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_ANDERZAAKOBJECTLOCATIE = "BBBBBBBBBB";

    private static final String DEFAULT_ANDERZAAKOBJECTOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_ANDERZAAKOBJECTOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_ANDERZAAKOBJECTREGISTRATIE = "AAAAAAAAAA";
    private static final String UPDATED_ANDERZAAKOBJECTREGISTRATIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/anderzaakobjectzaaks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AnderzaakobjectzaakRepository anderzaakobjectzaakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnderzaakobjectzaakMockMvc;

    private Anderzaakobjectzaak anderzaakobjectzaak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anderzaakobjectzaak createEntity(EntityManager em) {
        Anderzaakobjectzaak anderzaakobjectzaak = new Anderzaakobjectzaak()
            .anderzaakobjectaanduiding(DEFAULT_ANDERZAAKOBJECTAANDUIDING)
            .anderzaakobjectlocatie(DEFAULT_ANDERZAAKOBJECTLOCATIE)
            .anderzaakobjectomschrijving(DEFAULT_ANDERZAAKOBJECTOMSCHRIJVING)
            .anderzaakobjectregistratie(DEFAULT_ANDERZAAKOBJECTREGISTRATIE);
        return anderzaakobjectzaak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anderzaakobjectzaak createUpdatedEntity(EntityManager em) {
        Anderzaakobjectzaak anderzaakobjectzaak = new Anderzaakobjectzaak()
            .anderzaakobjectaanduiding(UPDATED_ANDERZAAKOBJECTAANDUIDING)
            .anderzaakobjectlocatie(UPDATED_ANDERZAAKOBJECTLOCATIE)
            .anderzaakobjectomschrijving(UPDATED_ANDERZAAKOBJECTOMSCHRIJVING)
            .anderzaakobjectregistratie(UPDATED_ANDERZAAKOBJECTREGISTRATIE);
        return anderzaakobjectzaak;
    }

    @BeforeEach
    public void initTest() {
        anderzaakobjectzaak = createEntity(em);
    }

    @Test
    @Transactional
    void createAnderzaakobjectzaak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Anderzaakobjectzaak
        var returnedAnderzaakobjectzaak = om.readValue(
            restAnderzaakobjectzaakMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anderzaakobjectzaak)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Anderzaakobjectzaak.class
        );

        // Validate the Anderzaakobjectzaak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAnderzaakobjectzaakUpdatableFieldsEquals(
            returnedAnderzaakobjectzaak,
            getPersistedAnderzaakobjectzaak(returnedAnderzaakobjectzaak)
        );
    }

    @Test
    @Transactional
    void createAnderzaakobjectzaakWithExistingId() throws Exception {
        // Create the Anderzaakobjectzaak with an existing ID
        anderzaakobjectzaak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnderzaakobjectzaakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anderzaakobjectzaak)))
            .andExpect(status().isBadRequest());

        // Validate the Anderzaakobjectzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnderzaakobjectzaaks() throws Exception {
        // Initialize the database
        anderzaakobjectzaakRepository.saveAndFlush(anderzaakobjectzaak);

        // Get all the anderzaakobjectzaakList
        restAnderzaakobjectzaakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anderzaakobjectzaak.getId().intValue())))
            .andExpect(jsonPath("$.[*].anderzaakobjectaanduiding").value(hasItem(DEFAULT_ANDERZAAKOBJECTAANDUIDING)))
            .andExpect(jsonPath("$.[*].anderzaakobjectlocatie").value(hasItem(DEFAULT_ANDERZAAKOBJECTLOCATIE)))
            .andExpect(jsonPath("$.[*].anderzaakobjectomschrijving").value(hasItem(DEFAULT_ANDERZAAKOBJECTOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].anderzaakobjectregistratie").value(hasItem(DEFAULT_ANDERZAAKOBJECTREGISTRATIE)));
    }

    @Test
    @Transactional
    void getAnderzaakobjectzaak() throws Exception {
        // Initialize the database
        anderzaakobjectzaakRepository.saveAndFlush(anderzaakobjectzaak);

        // Get the anderzaakobjectzaak
        restAnderzaakobjectzaakMockMvc
            .perform(get(ENTITY_API_URL_ID, anderzaakobjectzaak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anderzaakobjectzaak.getId().intValue()))
            .andExpect(jsonPath("$.anderzaakobjectaanduiding").value(DEFAULT_ANDERZAAKOBJECTAANDUIDING))
            .andExpect(jsonPath("$.anderzaakobjectlocatie").value(DEFAULT_ANDERZAAKOBJECTLOCATIE))
            .andExpect(jsonPath("$.anderzaakobjectomschrijving").value(DEFAULT_ANDERZAAKOBJECTOMSCHRIJVING))
            .andExpect(jsonPath("$.anderzaakobjectregistratie").value(DEFAULT_ANDERZAAKOBJECTREGISTRATIE));
    }

    @Test
    @Transactional
    void getNonExistingAnderzaakobjectzaak() throws Exception {
        // Get the anderzaakobjectzaak
        restAnderzaakobjectzaakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnderzaakobjectzaak() throws Exception {
        // Initialize the database
        anderzaakobjectzaakRepository.saveAndFlush(anderzaakobjectzaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anderzaakobjectzaak
        Anderzaakobjectzaak updatedAnderzaakobjectzaak = anderzaakobjectzaakRepository.findById(anderzaakobjectzaak.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAnderzaakobjectzaak are not directly saved in db
        em.detach(updatedAnderzaakobjectzaak);
        updatedAnderzaakobjectzaak
            .anderzaakobjectaanduiding(UPDATED_ANDERZAAKOBJECTAANDUIDING)
            .anderzaakobjectlocatie(UPDATED_ANDERZAAKOBJECTLOCATIE)
            .anderzaakobjectomschrijving(UPDATED_ANDERZAAKOBJECTOMSCHRIJVING)
            .anderzaakobjectregistratie(UPDATED_ANDERZAAKOBJECTREGISTRATIE);

        restAnderzaakobjectzaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnderzaakobjectzaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAnderzaakobjectzaak))
            )
            .andExpect(status().isOk());

        // Validate the Anderzaakobjectzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAnderzaakobjectzaakToMatchAllProperties(updatedAnderzaakobjectzaak);
    }

    @Test
    @Transactional
    void putNonExistingAnderzaakobjectzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anderzaakobjectzaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnderzaakobjectzaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anderzaakobjectzaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anderzaakobjectzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anderzaakobjectzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnderzaakobjectzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anderzaakobjectzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnderzaakobjectzaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anderzaakobjectzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anderzaakobjectzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnderzaakobjectzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anderzaakobjectzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnderzaakobjectzaakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anderzaakobjectzaak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Anderzaakobjectzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnderzaakobjectzaakWithPatch() throws Exception {
        // Initialize the database
        anderzaakobjectzaakRepository.saveAndFlush(anderzaakobjectzaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anderzaakobjectzaak using partial update
        Anderzaakobjectzaak partialUpdatedAnderzaakobjectzaak = new Anderzaakobjectzaak();
        partialUpdatedAnderzaakobjectzaak.setId(anderzaakobjectzaak.getId());

        partialUpdatedAnderzaakobjectzaak
            .anderzaakobjectaanduiding(UPDATED_ANDERZAAKOBJECTAANDUIDING)
            .anderzaakobjectlocatie(UPDATED_ANDERZAAKOBJECTLOCATIE)
            .anderzaakobjectomschrijving(UPDATED_ANDERZAAKOBJECTOMSCHRIJVING);

        restAnderzaakobjectzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnderzaakobjectzaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnderzaakobjectzaak))
            )
            .andExpect(status().isOk());

        // Validate the Anderzaakobjectzaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnderzaakobjectzaakUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAnderzaakobjectzaak, anderzaakobjectzaak),
            getPersistedAnderzaakobjectzaak(anderzaakobjectzaak)
        );
    }

    @Test
    @Transactional
    void fullUpdateAnderzaakobjectzaakWithPatch() throws Exception {
        // Initialize the database
        anderzaakobjectzaakRepository.saveAndFlush(anderzaakobjectzaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anderzaakobjectzaak using partial update
        Anderzaakobjectzaak partialUpdatedAnderzaakobjectzaak = new Anderzaakobjectzaak();
        partialUpdatedAnderzaakobjectzaak.setId(anderzaakobjectzaak.getId());

        partialUpdatedAnderzaakobjectzaak
            .anderzaakobjectaanduiding(UPDATED_ANDERZAAKOBJECTAANDUIDING)
            .anderzaakobjectlocatie(UPDATED_ANDERZAAKOBJECTLOCATIE)
            .anderzaakobjectomschrijving(UPDATED_ANDERZAAKOBJECTOMSCHRIJVING)
            .anderzaakobjectregistratie(UPDATED_ANDERZAAKOBJECTREGISTRATIE);

        restAnderzaakobjectzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnderzaakobjectzaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnderzaakobjectzaak))
            )
            .andExpect(status().isOk());

        // Validate the Anderzaakobjectzaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnderzaakobjectzaakUpdatableFieldsEquals(
            partialUpdatedAnderzaakobjectzaak,
            getPersistedAnderzaakobjectzaak(partialUpdatedAnderzaakobjectzaak)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAnderzaakobjectzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anderzaakobjectzaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnderzaakobjectzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anderzaakobjectzaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anderzaakobjectzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anderzaakobjectzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnderzaakobjectzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anderzaakobjectzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnderzaakobjectzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anderzaakobjectzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anderzaakobjectzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnderzaakobjectzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anderzaakobjectzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnderzaakobjectzaakMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(anderzaakobjectzaak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Anderzaakobjectzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnderzaakobjectzaak() throws Exception {
        // Initialize the database
        anderzaakobjectzaakRepository.saveAndFlush(anderzaakobjectzaak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the anderzaakobjectzaak
        restAnderzaakobjectzaakMockMvc
            .perform(delete(ENTITY_API_URL_ID, anderzaakobjectzaak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return anderzaakobjectzaakRepository.count();
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

    protected Anderzaakobjectzaak getPersistedAnderzaakobjectzaak(Anderzaakobjectzaak anderzaakobjectzaak) {
        return anderzaakobjectzaakRepository.findById(anderzaakobjectzaak.getId()).orElseThrow();
    }

    protected void assertPersistedAnderzaakobjectzaakToMatchAllProperties(Anderzaakobjectzaak expectedAnderzaakobjectzaak) {
        assertAnderzaakobjectzaakAllPropertiesEquals(
            expectedAnderzaakobjectzaak,
            getPersistedAnderzaakobjectzaak(expectedAnderzaakobjectzaak)
        );
    }

    protected void assertPersistedAnderzaakobjectzaakToMatchUpdatableProperties(Anderzaakobjectzaak expectedAnderzaakobjectzaak) {
        assertAnderzaakobjectzaakAllUpdatablePropertiesEquals(
            expectedAnderzaakobjectzaak,
            getPersistedAnderzaakobjectzaak(expectedAnderzaakobjectzaak)
        );
    }
}
