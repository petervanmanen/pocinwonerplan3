package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TaalniveauAsserts.*;
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
import nl.ritense.demo.domain.Taalniveau;
import nl.ritense.demo.repository.TaalniveauRepository;
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
 * Integration tests for the {@link TaalniveauResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class TaalniveauResourceIT {

    private static final String DEFAULT_MONDELING = "AAAAAAAA";
    private static final String UPDATED_MONDELING = "BBBBBBBB";

    private static final String DEFAULT_SCHRIFTELIJK = "AAAAAAAA";
    private static final String UPDATED_SCHRIFTELIJK = "BBBBBBBB";

    private static final String ENTITY_API_URL = "/api/taalniveaus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaalniveauRepository taalniveauRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaalniveauMockMvc;

    private Taalniveau taalniveau;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taalniveau createEntity(EntityManager em) {
        Taalniveau taalniveau = new Taalniveau().mondeling(DEFAULT_MONDELING).schriftelijk(DEFAULT_SCHRIFTELIJK);
        return taalniveau;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taalniveau createUpdatedEntity(EntityManager em) {
        Taalniveau taalniveau = new Taalniveau().mondeling(UPDATED_MONDELING).schriftelijk(UPDATED_SCHRIFTELIJK);
        return taalniveau;
    }

    @BeforeEach
    public void initTest() {
        taalniveau = createEntity(em);
    }

    @Test
    @Transactional
    void createTaalniveau() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Taalniveau
        var returnedTaalniveau = om.readValue(
            restTaalniveauMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taalniveau)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Taalniveau.class
        );

        // Validate the Taalniveau in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTaalniveauUpdatableFieldsEquals(returnedTaalniveau, getPersistedTaalniveau(returnedTaalniveau));
    }

    @Test
    @Transactional
    void createTaalniveauWithExistingId() throws Exception {
        // Create the Taalniveau with an existing ID
        taalniveau.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaalniveauMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taalniveau)))
            .andExpect(status().isBadRequest());

        // Validate the Taalniveau in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaalniveaus() throws Exception {
        // Initialize the database
        taalniveauRepository.saveAndFlush(taalniveau);

        // Get all the taalniveauList
        restTaalniveauMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taalniveau.getId().intValue())))
            .andExpect(jsonPath("$.[*].mondeling").value(hasItem(DEFAULT_MONDELING)))
            .andExpect(jsonPath("$.[*].schriftelijk").value(hasItem(DEFAULT_SCHRIFTELIJK)));
    }

    @Test
    @Transactional
    void getTaalniveau() throws Exception {
        // Initialize the database
        taalniveauRepository.saveAndFlush(taalniveau);

        // Get the taalniveau
        restTaalniveauMockMvc
            .perform(get(ENTITY_API_URL_ID, taalniveau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taalniveau.getId().intValue()))
            .andExpect(jsonPath("$.mondeling").value(DEFAULT_MONDELING))
            .andExpect(jsonPath("$.schriftelijk").value(DEFAULT_SCHRIFTELIJK));
    }

    @Test
    @Transactional
    void getNonExistingTaalniveau() throws Exception {
        // Get the taalniveau
        restTaalniveauMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTaalniveau() throws Exception {
        // Initialize the database
        taalniveauRepository.saveAndFlush(taalniveau);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taalniveau
        Taalniveau updatedTaalniveau = taalniveauRepository.findById(taalniveau.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTaalniveau are not directly saved in db
        em.detach(updatedTaalniveau);
        updatedTaalniveau.mondeling(UPDATED_MONDELING).schriftelijk(UPDATED_SCHRIFTELIJK);

        restTaalniveauMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTaalniveau.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTaalniveau))
            )
            .andExpect(status().isOk());

        // Validate the Taalniveau in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTaalniveauToMatchAllProperties(updatedTaalniveau);
    }

    @Test
    @Transactional
    void putNonExistingTaalniveau() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taalniveau.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaalniveauMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taalniveau.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taalniveau))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taalniveau in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaalniveau() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taalniveau.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaalniveauMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(taalniveau))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taalniveau in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaalniveau() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taalniveau.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaalniveauMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taalniveau)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Taalniveau in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaalniveauWithPatch() throws Exception {
        // Initialize the database
        taalniveauRepository.saveAndFlush(taalniveau);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taalniveau using partial update
        Taalniveau partialUpdatedTaalniveau = new Taalniveau();
        partialUpdatedTaalniveau.setId(taalniveau.getId());

        partialUpdatedTaalniveau.mondeling(UPDATED_MONDELING).schriftelijk(UPDATED_SCHRIFTELIJK);

        restTaalniveauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaalniveau.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTaalniveau))
            )
            .andExpect(status().isOk());

        // Validate the Taalniveau in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaalniveauUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTaalniveau, taalniveau),
            getPersistedTaalniveau(taalniveau)
        );
    }

    @Test
    @Transactional
    void fullUpdateTaalniveauWithPatch() throws Exception {
        // Initialize the database
        taalniveauRepository.saveAndFlush(taalniveau);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taalniveau using partial update
        Taalniveau partialUpdatedTaalniveau = new Taalniveau();
        partialUpdatedTaalniveau.setId(taalniveau.getId());

        partialUpdatedTaalniveau.mondeling(UPDATED_MONDELING).schriftelijk(UPDATED_SCHRIFTELIJK);

        restTaalniveauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaalniveau.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTaalniveau))
            )
            .andExpect(status().isOk());

        // Validate the Taalniveau in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaalniveauUpdatableFieldsEquals(partialUpdatedTaalniveau, getPersistedTaalniveau(partialUpdatedTaalniveau));
    }

    @Test
    @Transactional
    void patchNonExistingTaalniveau() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taalniveau.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaalniveauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taalniveau.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(taalniveau))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taalniveau in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaalniveau() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taalniveau.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaalniveauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(taalniveau))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taalniveau in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaalniveau() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taalniveau.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaalniveauMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(taalniveau)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Taalniveau in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaalniveau() throws Exception {
        // Initialize the database
        taalniveauRepository.saveAndFlush(taalniveau);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the taalniveau
        restTaalniveauMockMvc
            .perform(delete(ENTITY_API_URL_ID, taalniveau.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return taalniveauRepository.count();
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

    protected Taalniveau getPersistedTaalniveau(Taalniveau taalniveau) {
        return taalniveauRepository.findById(taalniveau.getId()).orElseThrow();
    }

    protected void assertPersistedTaalniveauToMatchAllProperties(Taalniveau expectedTaalniveau) {
        assertTaalniveauAllPropertiesEquals(expectedTaalniveau, getPersistedTaalniveau(expectedTaalniveau));
    }

    protected void assertPersistedTaalniveauToMatchUpdatableProperties(Taalniveau expectedTaalniveau) {
        assertTaalniveauAllUpdatablePropertiesEquals(expectedTaalniveau, getPersistedTaalniveau(expectedTaalniveau));
    }
}
