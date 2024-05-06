package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ThemaAsserts.*;
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
import nl.ritense.demo.domain.Thema;
import nl.ritense.demo.repository.ThemaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ThemaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThemaResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/themas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ThemaRepository themaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThemaMockMvc;

    private Thema thema;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thema createEntity(EntityManager em) {
        Thema thema = new Thema().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return thema;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thema createUpdatedEntity(EntityManager em) {
        Thema thema = new Thema().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return thema;
    }

    @BeforeEach
    public void initTest() {
        thema = createEntity(em);
    }

    @Test
    @Transactional
    void createThema() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Thema
        var returnedThema = om.readValue(
            restThemaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thema)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Thema.class
        );

        // Validate the Thema in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertThemaUpdatableFieldsEquals(returnedThema, getPersistedThema(returnedThema));
    }

    @Test
    @Transactional
    void createThemaWithExistingId() throws Exception {
        // Create the Thema with an existing ID
        thema.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThemaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thema)))
            .andExpect(status().isBadRequest());

        // Validate the Thema in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllThemas() throws Exception {
        // Initialize the database
        themaRepository.saveAndFlush(thema);

        // Get all the themaList
        restThemaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thema.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getThema() throws Exception {
        // Initialize the database
        themaRepository.saveAndFlush(thema);

        // Get the thema
        restThemaMockMvc
            .perform(get(ENTITY_API_URL_ID, thema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thema.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingThema() throws Exception {
        // Get the thema
        restThemaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingThema() throws Exception {
        // Initialize the database
        themaRepository.saveAndFlush(thema);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thema
        Thema updatedThema = themaRepository.findById(thema.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedThema are not directly saved in db
        em.detach(updatedThema);
        updatedThema.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restThemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedThema.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedThema))
            )
            .andExpect(status().isOk());

        // Validate the Thema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedThemaToMatchAllProperties(updatedThema);
    }

    @Test
    @Transactional
    void putNonExistingThema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thema.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThemaMockMvc
            .perform(put(ENTITY_API_URL_ID, thema.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thema)))
            .andExpect(status().isBadRequest());

        // Validate the Thema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thema.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(thema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thema.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThemaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thema)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThemaWithPatch() throws Exception {
        // Initialize the database
        themaRepository.saveAndFlush(thema);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thema using partial update
        Thema partialUpdatedThema = new Thema();
        partialUpdatedThema.setId(thema.getId());

        restThemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThema.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedThema))
            )
            .andExpect(status().isOk());

        // Validate the Thema in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertThemaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedThema, thema), getPersistedThema(thema));
    }

    @Test
    @Transactional
    void fullUpdateThemaWithPatch() throws Exception {
        // Initialize the database
        themaRepository.saveAndFlush(thema);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thema using partial update
        Thema partialUpdatedThema = new Thema();
        partialUpdatedThema.setId(thema.getId());

        partialUpdatedThema.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restThemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThema.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedThema))
            )
            .andExpect(status().isOk());

        // Validate the Thema in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertThemaUpdatableFieldsEquals(partialUpdatedThema, getPersistedThema(partialUpdatedThema));
    }

    @Test
    @Transactional
    void patchNonExistingThema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thema.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thema.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(thema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thema.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(thema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thema.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThemaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(thema)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThema() throws Exception {
        // Initialize the database
        themaRepository.saveAndFlush(thema);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the thema
        restThemaMockMvc
            .perform(delete(ENTITY_API_URL_ID, thema.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return themaRepository.count();
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

    protected Thema getPersistedThema(Thema thema) {
        return themaRepository.findById(thema.getId()).orElseThrow();
    }

    protected void assertPersistedThemaToMatchAllProperties(Thema expectedThema) {
        assertThemaAllPropertiesEquals(expectedThema, getPersistedThema(expectedThema));
    }

    protected void assertPersistedThemaToMatchUpdatableProperties(Thema expectedThema) {
        assertThemaAllUpdatablePropertiesEquals(expectedThema, getPersistedThema(expectedThema));
    }
}
