package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FormatieplaatsAsserts.*;
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
import nl.ritense.demo.domain.Formatieplaats;
import nl.ritense.demo.repository.FormatieplaatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FormatieplaatsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormatieplaatsResourceIT {

    private static final String DEFAULT_URENPERWEEK = "AAAAAAAAAA";
    private static final String UPDATED_URENPERWEEK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/formatieplaats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FormatieplaatsRepository formatieplaatsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormatieplaatsMockMvc;

    private Formatieplaats formatieplaats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formatieplaats createEntity(EntityManager em) {
        Formatieplaats formatieplaats = new Formatieplaats().urenperweek(DEFAULT_URENPERWEEK);
        return formatieplaats;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formatieplaats createUpdatedEntity(EntityManager em) {
        Formatieplaats formatieplaats = new Formatieplaats().urenperweek(UPDATED_URENPERWEEK);
        return formatieplaats;
    }

    @BeforeEach
    public void initTest() {
        formatieplaats = createEntity(em);
    }

    @Test
    @Transactional
    void createFormatieplaats() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Formatieplaats
        var returnedFormatieplaats = om.readValue(
            restFormatieplaatsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formatieplaats)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Formatieplaats.class
        );

        // Validate the Formatieplaats in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFormatieplaatsUpdatableFieldsEquals(returnedFormatieplaats, getPersistedFormatieplaats(returnedFormatieplaats));
    }

    @Test
    @Transactional
    void createFormatieplaatsWithExistingId() throws Exception {
        // Create the Formatieplaats with an existing ID
        formatieplaats.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormatieplaatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formatieplaats)))
            .andExpect(status().isBadRequest());

        // Validate the Formatieplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormatieplaats() throws Exception {
        // Initialize the database
        formatieplaatsRepository.saveAndFlush(formatieplaats);

        // Get all the formatieplaatsList
        restFormatieplaatsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formatieplaats.getId().intValue())))
            .andExpect(jsonPath("$.[*].urenperweek").value(hasItem(DEFAULT_URENPERWEEK)));
    }

    @Test
    @Transactional
    void getFormatieplaats() throws Exception {
        // Initialize the database
        formatieplaatsRepository.saveAndFlush(formatieplaats);

        // Get the formatieplaats
        restFormatieplaatsMockMvc
            .perform(get(ENTITY_API_URL_ID, formatieplaats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formatieplaats.getId().intValue()))
            .andExpect(jsonPath("$.urenperweek").value(DEFAULT_URENPERWEEK));
    }

    @Test
    @Transactional
    void getNonExistingFormatieplaats() throws Exception {
        // Get the formatieplaats
        restFormatieplaatsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFormatieplaats() throws Exception {
        // Initialize the database
        formatieplaatsRepository.saveAndFlush(formatieplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formatieplaats
        Formatieplaats updatedFormatieplaats = formatieplaatsRepository.findById(formatieplaats.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFormatieplaats are not directly saved in db
        em.detach(updatedFormatieplaats);
        updatedFormatieplaats.urenperweek(UPDATED_URENPERWEEK);

        restFormatieplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormatieplaats.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFormatieplaats))
            )
            .andExpect(status().isOk());

        // Validate the Formatieplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFormatieplaatsToMatchAllProperties(updatedFormatieplaats);
    }

    @Test
    @Transactional
    void putNonExistingFormatieplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formatieplaats.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormatieplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formatieplaats.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formatieplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formatieplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormatieplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formatieplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormatieplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formatieplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formatieplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormatieplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formatieplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormatieplaatsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formatieplaats)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formatieplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormatieplaatsWithPatch() throws Exception {
        // Initialize the database
        formatieplaatsRepository.saveAndFlush(formatieplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formatieplaats using partial update
        Formatieplaats partialUpdatedFormatieplaats = new Formatieplaats();
        partialUpdatedFormatieplaats.setId(formatieplaats.getId());

        restFormatieplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormatieplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormatieplaats))
            )
            .andExpect(status().isOk());

        // Validate the Formatieplaats in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormatieplaatsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFormatieplaats, formatieplaats),
            getPersistedFormatieplaats(formatieplaats)
        );
    }

    @Test
    @Transactional
    void fullUpdateFormatieplaatsWithPatch() throws Exception {
        // Initialize the database
        formatieplaatsRepository.saveAndFlush(formatieplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formatieplaats using partial update
        Formatieplaats partialUpdatedFormatieplaats = new Formatieplaats();
        partialUpdatedFormatieplaats.setId(formatieplaats.getId());

        partialUpdatedFormatieplaats.urenperweek(UPDATED_URENPERWEEK);

        restFormatieplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormatieplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormatieplaats))
            )
            .andExpect(status().isOk());

        // Validate the Formatieplaats in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormatieplaatsUpdatableFieldsEquals(partialUpdatedFormatieplaats, getPersistedFormatieplaats(partialUpdatedFormatieplaats));
    }

    @Test
    @Transactional
    void patchNonExistingFormatieplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formatieplaats.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormatieplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formatieplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formatieplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formatieplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormatieplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formatieplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormatieplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formatieplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formatieplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormatieplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formatieplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormatieplaatsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(formatieplaats)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formatieplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormatieplaats() throws Exception {
        // Initialize the database
        formatieplaatsRepository.saveAndFlush(formatieplaats);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the formatieplaats
        restFormatieplaatsMockMvc
            .perform(delete(ENTITY_API_URL_ID, formatieplaats.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return formatieplaatsRepository.count();
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

    protected Formatieplaats getPersistedFormatieplaats(Formatieplaats formatieplaats) {
        return formatieplaatsRepository.findById(formatieplaats.getId()).orElseThrow();
    }

    protected void assertPersistedFormatieplaatsToMatchAllProperties(Formatieplaats expectedFormatieplaats) {
        assertFormatieplaatsAllPropertiesEquals(expectedFormatieplaats, getPersistedFormatieplaats(expectedFormatieplaats));
    }

    protected void assertPersistedFormatieplaatsToMatchUpdatableProperties(Formatieplaats expectedFormatieplaats) {
        assertFormatieplaatsAllUpdatablePropertiesEquals(expectedFormatieplaats, getPersistedFormatieplaats(expectedFormatieplaats));
    }
}
