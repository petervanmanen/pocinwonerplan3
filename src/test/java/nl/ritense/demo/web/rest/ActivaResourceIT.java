package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ActivaAsserts.*;
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
import nl.ritense.demo.domain.Activa;
import nl.ritense.demo.repository.ActivaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ActivaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActivaResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/activas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ActivaRepository activaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivaMockMvc;

    private Activa activa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activa createEntity(EntityManager em) {
        Activa activa = new Activa().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return activa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activa createUpdatedEntity(EntityManager em) {
        Activa activa = new Activa().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return activa;
    }

    @BeforeEach
    public void initTest() {
        activa = createEntity(em);
    }

    @Test
    @Transactional
    void createActiva() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Activa
        var returnedActiva = om.readValue(
            restActivaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Activa.class
        );

        // Validate the Activa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertActivaUpdatableFieldsEquals(returnedActiva, getPersistedActiva(returnedActiva));
    }

    @Test
    @Transactional
    void createActivaWithExistingId() throws Exception {
        // Create the Activa with an existing ID
        activa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activa)))
            .andExpect(status().isBadRequest());

        // Validate the Activa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActivas() throws Exception {
        // Initialize the database
        activaRepository.saveAndFlush(activa);

        // Get all the activaList
        restActivaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activa.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getActiva() throws Exception {
        // Initialize the database
        activaRepository.saveAndFlush(activa);

        // Get the activa
        restActivaMockMvc
            .perform(get(ENTITY_API_URL_ID, activa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activa.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingActiva() throws Exception {
        // Get the activa
        restActivaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingActiva() throws Exception {
        // Initialize the database
        activaRepository.saveAndFlush(activa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activa
        Activa updatedActiva = activaRepository.findById(activa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedActiva are not directly saved in db
        em.detach(updatedActiva);
        updatedActiva.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restActivaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActiva.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedActiva))
            )
            .andExpect(status().isOk());

        // Validate the Activa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedActivaToMatchAllProperties(updatedActiva);
    }

    @Test
    @Transactional
    void putNonExistingActiva() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivaMockMvc
            .perform(put(ENTITY_API_URL_ID, activa.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activa)))
            .andExpect(status().isBadRequest());

        // Validate the Activa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActiva() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(activa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActiva() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActivaWithPatch() throws Exception {
        // Initialize the database
        activaRepository.saveAndFlush(activa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activa using partial update
        Activa partialUpdatedActiva = new Activa();
        partialUpdatedActiva.setId(activa.getId());

        partialUpdatedActiva.naam(UPDATED_NAAM);

        restActivaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActiva.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedActiva))
            )
            .andExpect(status().isOk());

        // Validate the Activa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertActivaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedActiva, activa), getPersistedActiva(activa));
    }

    @Test
    @Transactional
    void fullUpdateActivaWithPatch() throws Exception {
        // Initialize the database
        activaRepository.saveAndFlush(activa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activa using partial update
        Activa partialUpdatedActiva = new Activa();
        partialUpdatedActiva.setId(activa.getId());

        partialUpdatedActiva.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restActivaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActiva.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedActiva))
            )
            .andExpect(status().isOk());

        // Validate the Activa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertActivaUpdatableFieldsEquals(partialUpdatedActiva, getPersistedActiva(partialUpdatedActiva));
    }

    @Test
    @Transactional
    void patchNonExistingActiva() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activa.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(activa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActiva() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(activa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActiva() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(activa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActiva() throws Exception {
        // Initialize the database
        activaRepository.saveAndFlush(activa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the activa
        restActivaMockMvc
            .perform(delete(ENTITY_API_URL_ID, activa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return activaRepository.count();
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

    protected Activa getPersistedActiva(Activa activa) {
        return activaRepository.findById(activa.getId()).orElseThrow();
    }

    protected void assertPersistedActivaToMatchAllProperties(Activa expectedActiva) {
        assertActivaAllPropertiesEquals(expectedActiva, getPersistedActiva(expectedActiva));
    }

    protected void assertPersistedActivaToMatchUpdatableProperties(Activa expectedActiva) {
        assertActivaAllUpdatablePropertiesEquals(expectedActiva, getPersistedActiva(expectedActiva));
    }
}
