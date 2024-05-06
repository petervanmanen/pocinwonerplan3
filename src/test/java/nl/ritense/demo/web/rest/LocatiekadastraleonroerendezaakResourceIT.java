package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LocatiekadastraleonroerendezaakAsserts.*;
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
import nl.ritense.demo.domain.Locatiekadastraleonroerendezaak;
import nl.ritense.demo.repository.LocatiekadastraleonroerendezaakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LocatiekadastraleonroerendezaakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocatiekadastraleonroerendezaakResourceIT {

    private static final String DEFAULT_AARDCULTUURBEBOUWD = "AAAAAAAAAA";
    private static final String UPDATED_AARDCULTUURBEBOUWD = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/locatiekadastraleonroerendezaaks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LocatiekadastraleonroerendezaakRepository locatiekadastraleonroerendezaakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocatiekadastraleonroerendezaakMockMvc;

    private Locatiekadastraleonroerendezaak locatiekadastraleonroerendezaak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locatiekadastraleonroerendezaak createEntity(EntityManager em) {
        Locatiekadastraleonroerendezaak locatiekadastraleonroerendezaak = new Locatiekadastraleonroerendezaak()
            .aardcultuurbebouwd(DEFAULT_AARDCULTUURBEBOUWD)
            .locatieomschrijving(DEFAULT_LOCATIEOMSCHRIJVING);
        return locatiekadastraleonroerendezaak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locatiekadastraleonroerendezaak createUpdatedEntity(EntityManager em) {
        Locatiekadastraleonroerendezaak locatiekadastraleonroerendezaak = new Locatiekadastraleonroerendezaak()
            .aardcultuurbebouwd(UPDATED_AARDCULTUURBEBOUWD)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING);
        return locatiekadastraleonroerendezaak;
    }

    @BeforeEach
    public void initTest() {
        locatiekadastraleonroerendezaak = createEntity(em);
    }

    @Test
    @Transactional
    void createLocatiekadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Locatiekadastraleonroerendezaak
        var returnedLocatiekadastraleonroerendezaak = om.readValue(
            restLocatiekadastraleonroerendezaakMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(locatiekadastraleonroerendezaak))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Locatiekadastraleonroerendezaak.class
        );

        // Validate the Locatiekadastraleonroerendezaak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLocatiekadastraleonroerendezaakUpdatableFieldsEquals(
            returnedLocatiekadastraleonroerendezaak,
            getPersistedLocatiekadastraleonroerendezaak(returnedLocatiekadastraleonroerendezaak)
        );
    }

    @Test
    @Transactional
    void createLocatiekadastraleonroerendezaakWithExistingId() throws Exception {
        // Create the Locatiekadastraleonroerendezaak with an existing ID
        locatiekadastraleonroerendezaak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocatiekadastraleonroerendezaakMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locatiekadastraleonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatiekadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLocatiekadastraleonroerendezaaks() throws Exception {
        // Initialize the database
        locatiekadastraleonroerendezaakRepository.saveAndFlush(locatiekadastraleonroerendezaak);

        // Get all the locatiekadastraleonroerendezaakList
        restLocatiekadastraleonroerendezaakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locatiekadastraleonroerendezaak.getId().intValue())))
            .andExpect(jsonPath("$.[*].aardcultuurbebouwd").value(hasItem(DEFAULT_AARDCULTUURBEBOUWD)))
            .andExpect(jsonPath("$.[*].locatieomschrijving").value(hasItem(DEFAULT_LOCATIEOMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getLocatiekadastraleonroerendezaak() throws Exception {
        // Initialize the database
        locatiekadastraleonroerendezaakRepository.saveAndFlush(locatiekadastraleonroerendezaak);

        // Get the locatiekadastraleonroerendezaak
        restLocatiekadastraleonroerendezaakMockMvc
            .perform(get(ENTITY_API_URL_ID, locatiekadastraleonroerendezaak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locatiekadastraleonroerendezaak.getId().intValue()))
            .andExpect(jsonPath("$.aardcultuurbebouwd").value(DEFAULT_AARDCULTUURBEBOUWD))
            .andExpect(jsonPath("$.locatieomschrijving").value(DEFAULT_LOCATIEOMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingLocatiekadastraleonroerendezaak() throws Exception {
        // Get the locatiekadastraleonroerendezaak
        restLocatiekadastraleonroerendezaakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocatiekadastraleonroerendezaak() throws Exception {
        // Initialize the database
        locatiekadastraleonroerendezaakRepository.saveAndFlush(locatiekadastraleonroerendezaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locatiekadastraleonroerendezaak
        Locatiekadastraleonroerendezaak updatedLocatiekadastraleonroerendezaak = locatiekadastraleonroerendezaakRepository
            .findById(locatiekadastraleonroerendezaak.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedLocatiekadastraleonroerendezaak are not directly saved in db
        em.detach(updatedLocatiekadastraleonroerendezaak);
        updatedLocatiekadastraleonroerendezaak
            .aardcultuurbebouwd(UPDATED_AARDCULTUURBEBOUWD)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING);

        restLocatiekadastraleonroerendezaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocatiekadastraleonroerendezaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLocatiekadastraleonroerendezaak))
            )
            .andExpect(status().isOk());

        // Validate the Locatiekadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLocatiekadastraleonroerendezaakToMatchAllProperties(updatedLocatiekadastraleonroerendezaak);
    }

    @Test
    @Transactional
    void putNonExistingLocatiekadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatiekadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocatiekadastraleonroerendezaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locatiekadastraleonroerendezaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locatiekadastraleonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatiekadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocatiekadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatiekadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatiekadastraleonroerendezaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locatiekadastraleonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatiekadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocatiekadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatiekadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatiekadastraleonroerendezaakMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locatiekadastraleonroerendezaak))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locatiekadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocatiekadastraleonroerendezaakWithPatch() throws Exception {
        // Initialize the database
        locatiekadastraleonroerendezaakRepository.saveAndFlush(locatiekadastraleonroerendezaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locatiekadastraleonroerendezaak using partial update
        Locatiekadastraleonroerendezaak partialUpdatedLocatiekadastraleonroerendezaak = new Locatiekadastraleonroerendezaak();
        partialUpdatedLocatiekadastraleonroerendezaak.setId(locatiekadastraleonroerendezaak.getId());

        partialUpdatedLocatiekadastraleonroerendezaak.locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING);

        restLocatiekadastraleonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocatiekadastraleonroerendezaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocatiekadastraleonroerendezaak))
            )
            .andExpect(status().isOk());

        // Validate the Locatiekadastraleonroerendezaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocatiekadastraleonroerendezaakUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLocatiekadastraleonroerendezaak, locatiekadastraleonroerendezaak),
            getPersistedLocatiekadastraleonroerendezaak(locatiekadastraleonroerendezaak)
        );
    }

    @Test
    @Transactional
    void fullUpdateLocatiekadastraleonroerendezaakWithPatch() throws Exception {
        // Initialize the database
        locatiekadastraleonroerendezaakRepository.saveAndFlush(locatiekadastraleonroerendezaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locatiekadastraleonroerendezaak using partial update
        Locatiekadastraleonroerendezaak partialUpdatedLocatiekadastraleonroerendezaak = new Locatiekadastraleonroerendezaak();
        partialUpdatedLocatiekadastraleonroerendezaak.setId(locatiekadastraleonroerendezaak.getId());

        partialUpdatedLocatiekadastraleonroerendezaak
            .aardcultuurbebouwd(UPDATED_AARDCULTUURBEBOUWD)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING);

        restLocatiekadastraleonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocatiekadastraleonroerendezaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocatiekadastraleonroerendezaak))
            )
            .andExpect(status().isOk());

        // Validate the Locatiekadastraleonroerendezaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocatiekadastraleonroerendezaakUpdatableFieldsEquals(
            partialUpdatedLocatiekadastraleonroerendezaak,
            getPersistedLocatiekadastraleonroerendezaak(partialUpdatedLocatiekadastraleonroerendezaak)
        );
    }

    @Test
    @Transactional
    void patchNonExistingLocatiekadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatiekadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocatiekadastraleonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locatiekadastraleonroerendezaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locatiekadastraleonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatiekadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocatiekadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatiekadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatiekadastraleonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locatiekadastraleonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatiekadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocatiekadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatiekadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatiekadastraleonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locatiekadastraleonroerendezaak))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locatiekadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocatiekadastraleonroerendezaak() throws Exception {
        // Initialize the database
        locatiekadastraleonroerendezaakRepository.saveAndFlush(locatiekadastraleonroerendezaak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the locatiekadastraleonroerendezaak
        restLocatiekadastraleonroerendezaakMockMvc
            .perform(delete(ENTITY_API_URL_ID, locatiekadastraleonroerendezaak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return locatiekadastraleonroerendezaakRepository.count();
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

    protected Locatiekadastraleonroerendezaak getPersistedLocatiekadastraleonroerendezaak(
        Locatiekadastraleonroerendezaak locatiekadastraleonroerendezaak
    ) {
        return locatiekadastraleonroerendezaakRepository.findById(locatiekadastraleonroerendezaak.getId()).orElseThrow();
    }

    protected void assertPersistedLocatiekadastraleonroerendezaakToMatchAllProperties(
        Locatiekadastraleonroerendezaak expectedLocatiekadastraleonroerendezaak
    ) {
        assertLocatiekadastraleonroerendezaakAllPropertiesEquals(
            expectedLocatiekadastraleonroerendezaak,
            getPersistedLocatiekadastraleonroerendezaak(expectedLocatiekadastraleonroerendezaak)
        );
    }

    protected void assertPersistedLocatiekadastraleonroerendezaakToMatchUpdatableProperties(
        Locatiekadastraleonroerendezaak expectedLocatiekadastraleonroerendezaak
    ) {
        assertLocatiekadastraleonroerendezaakAllUpdatablePropertiesEquals(
            expectedLocatiekadastraleonroerendezaak,
            getPersistedLocatiekadastraleonroerendezaak(expectedLocatiekadastraleonroerendezaak)
        );
    }
}
