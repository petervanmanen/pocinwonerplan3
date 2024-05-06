package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OrdeningsschemaAsserts.*;
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
import nl.ritense.demo.domain.Ordeningsschema;
import nl.ritense.demo.repository.OrdeningsschemaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrdeningsschemaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdeningsschemaResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ordeningsschemas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrdeningsschemaRepository ordeningsschemaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdeningsschemaMockMvc;

    private Ordeningsschema ordeningsschema;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ordeningsschema createEntity(EntityManager em) {
        Ordeningsschema ordeningsschema = new Ordeningsschema().naam(DEFAULT_NAAM).text(DEFAULT_TEXT);
        return ordeningsschema;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ordeningsschema createUpdatedEntity(EntityManager em) {
        Ordeningsschema ordeningsschema = new Ordeningsschema().naam(UPDATED_NAAM).text(UPDATED_TEXT);
        return ordeningsschema;
    }

    @BeforeEach
    public void initTest() {
        ordeningsschema = createEntity(em);
    }

    @Test
    @Transactional
    void createOrdeningsschema() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ordeningsschema
        var returnedOrdeningsschema = om.readValue(
            restOrdeningsschemaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ordeningsschema)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Ordeningsschema.class
        );

        // Validate the Ordeningsschema in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOrdeningsschemaUpdatableFieldsEquals(returnedOrdeningsschema, getPersistedOrdeningsschema(returnedOrdeningsschema));
    }

    @Test
    @Transactional
    void createOrdeningsschemaWithExistingId() throws Exception {
        // Create the Ordeningsschema with an existing ID
        ordeningsschema.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdeningsschemaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ordeningsschema)))
            .andExpect(status().isBadRequest());

        // Validate the Ordeningsschema in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdeningsschemas() throws Exception {
        // Initialize the database
        ordeningsschemaRepository.saveAndFlush(ordeningsschema);

        // Get all the ordeningsschemaList
        restOrdeningsschemaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordeningsschema.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));
    }

    @Test
    @Transactional
    void getOrdeningsschema() throws Exception {
        // Initialize the database
        ordeningsschemaRepository.saveAndFlush(ordeningsschema);

        // Get the ordeningsschema
        restOrdeningsschemaMockMvc
            .perform(get(ENTITY_API_URL_ID, ordeningsschema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordeningsschema.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT));
    }

    @Test
    @Transactional
    void getNonExistingOrdeningsschema() throws Exception {
        // Get the ordeningsschema
        restOrdeningsschemaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrdeningsschema() throws Exception {
        // Initialize the database
        ordeningsschemaRepository.saveAndFlush(ordeningsschema);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ordeningsschema
        Ordeningsschema updatedOrdeningsschema = ordeningsschemaRepository.findById(ordeningsschema.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOrdeningsschema are not directly saved in db
        em.detach(updatedOrdeningsschema);
        updatedOrdeningsschema.naam(UPDATED_NAAM).text(UPDATED_TEXT);

        restOrdeningsschemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrdeningsschema.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOrdeningsschema))
            )
            .andExpect(status().isOk());

        // Validate the Ordeningsschema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrdeningsschemaToMatchAllProperties(updatedOrdeningsschema);
    }

    @Test
    @Transactional
    void putNonExistingOrdeningsschema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordeningsschema.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdeningsschemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordeningsschema.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ordeningsschema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ordeningsschema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdeningsschema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordeningsschema.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdeningsschemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ordeningsschema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ordeningsschema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdeningsschema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordeningsschema.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdeningsschemaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ordeningsschema)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ordeningsschema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdeningsschemaWithPatch() throws Exception {
        // Initialize the database
        ordeningsschemaRepository.saveAndFlush(ordeningsschema);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ordeningsschema using partial update
        Ordeningsschema partialUpdatedOrdeningsschema = new Ordeningsschema();
        partialUpdatedOrdeningsschema.setId(ordeningsschema.getId());

        partialUpdatedOrdeningsschema.naam(UPDATED_NAAM);

        restOrdeningsschemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdeningsschema.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrdeningsschema))
            )
            .andExpect(status().isOk());

        // Validate the Ordeningsschema in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrdeningsschemaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrdeningsschema, ordeningsschema),
            getPersistedOrdeningsschema(ordeningsschema)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrdeningsschemaWithPatch() throws Exception {
        // Initialize the database
        ordeningsschemaRepository.saveAndFlush(ordeningsschema);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ordeningsschema using partial update
        Ordeningsschema partialUpdatedOrdeningsschema = new Ordeningsschema();
        partialUpdatedOrdeningsschema.setId(ordeningsschema.getId());

        partialUpdatedOrdeningsschema.naam(UPDATED_NAAM).text(UPDATED_TEXT);

        restOrdeningsschemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdeningsschema.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrdeningsschema))
            )
            .andExpect(status().isOk());

        // Validate the Ordeningsschema in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrdeningsschemaUpdatableFieldsEquals(
            partialUpdatedOrdeningsschema,
            getPersistedOrdeningsschema(partialUpdatedOrdeningsschema)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOrdeningsschema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordeningsschema.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdeningsschemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordeningsschema.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ordeningsschema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ordeningsschema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdeningsschema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordeningsschema.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdeningsschemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ordeningsschema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ordeningsschema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdeningsschema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordeningsschema.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdeningsschemaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ordeningsschema)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ordeningsschema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdeningsschema() throws Exception {
        // Initialize the database
        ordeningsschemaRepository.saveAndFlush(ordeningsschema);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ordeningsschema
        restOrdeningsschemaMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordeningsschema.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ordeningsschemaRepository.count();
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

    protected Ordeningsschema getPersistedOrdeningsschema(Ordeningsschema ordeningsschema) {
        return ordeningsschemaRepository.findById(ordeningsschema.getId()).orElseThrow();
    }

    protected void assertPersistedOrdeningsschemaToMatchAllProperties(Ordeningsschema expectedOrdeningsschema) {
        assertOrdeningsschemaAllPropertiesEquals(expectedOrdeningsschema, getPersistedOrdeningsschema(expectedOrdeningsschema));
    }

    protected void assertPersistedOrdeningsschemaToMatchUpdatableProperties(Ordeningsschema expectedOrdeningsschema) {
        assertOrdeningsschemaAllUpdatablePropertiesEquals(expectedOrdeningsschema, getPersistedOrdeningsschema(expectedOrdeningsschema));
    }
}
