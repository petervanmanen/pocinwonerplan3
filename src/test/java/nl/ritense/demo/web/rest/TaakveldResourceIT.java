package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TaakveldAsserts.*;
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
import nl.ritense.demo.domain.Taakveld;
import nl.ritense.demo.repository.TaakveldRepository;
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
 * Integration tests for the {@link TaakveldResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class TaakveldResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/taakvelds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaakveldRepository taakveldRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaakveldMockMvc;

    private Taakveld taakveld;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taakveld createEntity(EntityManager em) {
        Taakveld taakveld = new Taakveld().naam(DEFAULT_NAAM);
        return taakveld;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taakveld createUpdatedEntity(EntityManager em) {
        Taakveld taakveld = new Taakveld().naam(UPDATED_NAAM);
        return taakveld;
    }

    @BeforeEach
    public void initTest() {
        taakveld = createEntity(em);
    }

    @Test
    @Transactional
    void createTaakveld() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Taakveld
        var returnedTaakveld = om.readValue(
            restTaakveldMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taakveld)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Taakveld.class
        );

        // Validate the Taakveld in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTaakveldUpdatableFieldsEquals(returnedTaakveld, getPersistedTaakveld(returnedTaakveld));
    }

    @Test
    @Transactional
    void createTaakveldWithExistingId() throws Exception {
        // Create the Taakveld with an existing ID
        taakveld.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaakveldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taakveld)))
            .andExpect(status().isBadRequest());

        // Validate the Taakveld in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaakvelds() throws Exception {
        // Initialize the database
        taakveldRepository.saveAndFlush(taakveld);

        // Get all the taakveldList
        restTaakveldMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taakveld.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getTaakveld() throws Exception {
        // Initialize the database
        taakveldRepository.saveAndFlush(taakveld);

        // Get the taakveld
        restTaakveldMockMvc
            .perform(get(ENTITY_API_URL_ID, taakveld.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taakveld.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingTaakveld() throws Exception {
        // Get the taakveld
        restTaakveldMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTaakveld() throws Exception {
        // Initialize the database
        taakveldRepository.saveAndFlush(taakveld);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taakveld
        Taakveld updatedTaakveld = taakveldRepository.findById(taakveld.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTaakveld are not directly saved in db
        em.detach(updatedTaakveld);
        updatedTaakveld.naam(UPDATED_NAAM);

        restTaakveldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTaakveld.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTaakveld))
            )
            .andExpect(status().isOk());

        // Validate the Taakveld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTaakveldToMatchAllProperties(updatedTaakveld);
    }

    @Test
    @Transactional
    void putNonExistingTaakveld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taakveld.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaakveldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taakveld.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taakveld))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taakveld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaakveld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taakveld.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaakveldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(taakveld))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taakveld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaakveld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taakveld.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaakveldMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taakveld)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Taakveld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaakveldWithPatch() throws Exception {
        // Initialize the database
        taakveldRepository.saveAndFlush(taakveld);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taakveld using partial update
        Taakveld partialUpdatedTaakveld = new Taakveld();
        partialUpdatedTaakveld.setId(taakveld.getId());

        restTaakveldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaakveld.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTaakveld))
            )
            .andExpect(status().isOk());

        // Validate the Taakveld in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaakveldUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTaakveld, taakveld), getPersistedTaakveld(taakveld));
    }

    @Test
    @Transactional
    void fullUpdateTaakveldWithPatch() throws Exception {
        // Initialize the database
        taakveldRepository.saveAndFlush(taakveld);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taakveld using partial update
        Taakveld partialUpdatedTaakveld = new Taakveld();
        partialUpdatedTaakveld.setId(taakveld.getId());

        partialUpdatedTaakveld.naam(UPDATED_NAAM);

        restTaakveldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaakveld.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTaakveld))
            )
            .andExpect(status().isOk());

        // Validate the Taakveld in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaakveldUpdatableFieldsEquals(partialUpdatedTaakveld, getPersistedTaakveld(partialUpdatedTaakveld));
    }

    @Test
    @Transactional
    void patchNonExistingTaakveld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taakveld.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaakveldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taakveld.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(taakveld))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taakveld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaakveld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taakveld.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaakveldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(taakveld))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taakveld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaakveld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taakveld.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaakveldMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(taakveld)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Taakveld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaakveld() throws Exception {
        // Initialize the database
        taakveldRepository.saveAndFlush(taakveld);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the taakveld
        restTaakveldMockMvc
            .perform(delete(ENTITY_API_URL_ID, taakveld.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return taakveldRepository.count();
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

    protected Taakveld getPersistedTaakveld(Taakveld taakveld) {
        return taakveldRepository.findById(taakveld.getId()).orElseThrow();
    }

    protected void assertPersistedTaakveldToMatchAllProperties(Taakveld expectedTaakveld) {
        assertTaakveldAllPropertiesEquals(expectedTaakveld, getPersistedTaakveld(expectedTaakveld));
    }

    protected void assertPersistedTaakveldToMatchUpdatableProperties(Taakveld expectedTaakveld) {
        assertTaakveldAllUpdatablePropertiesEquals(expectedTaakveld, getPersistedTaakveld(expectedTaakveld));
    }
}
