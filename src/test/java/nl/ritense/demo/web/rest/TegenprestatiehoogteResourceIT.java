package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TegenprestatiehoogteAsserts.*;
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
import nl.ritense.demo.domain.Tegenprestatiehoogte;
import nl.ritense.demo.repository.TegenprestatiehoogteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TegenprestatiehoogteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TegenprestatiehoogteResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tegenprestatiehoogtes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TegenprestatiehoogteRepository tegenprestatiehoogteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTegenprestatiehoogteMockMvc;

    private Tegenprestatiehoogte tegenprestatiehoogte;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tegenprestatiehoogte createEntity(EntityManager em) {
        Tegenprestatiehoogte tegenprestatiehoogte = new Tegenprestatiehoogte()
            .code(DEFAULT_CODE)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return tegenprestatiehoogte;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tegenprestatiehoogte createUpdatedEntity(EntityManager em) {
        Tegenprestatiehoogte tegenprestatiehoogte = new Tegenprestatiehoogte()
            .code(UPDATED_CODE)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return tegenprestatiehoogte;
    }

    @BeforeEach
    public void initTest() {
        tegenprestatiehoogte = createEntity(em);
    }

    @Test
    @Transactional
    void createTegenprestatiehoogte() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Tegenprestatiehoogte
        var returnedTegenprestatiehoogte = om.readValue(
            restTegenprestatiehoogteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tegenprestatiehoogte)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Tegenprestatiehoogte.class
        );

        // Validate the Tegenprestatiehoogte in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTegenprestatiehoogteUpdatableFieldsEquals(
            returnedTegenprestatiehoogte,
            getPersistedTegenprestatiehoogte(returnedTegenprestatiehoogte)
        );
    }

    @Test
    @Transactional
    void createTegenprestatiehoogteWithExistingId() throws Exception {
        // Create the Tegenprestatiehoogte with an existing ID
        tegenprestatiehoogte.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTegenprestatiehoogteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tegenprestatiehoogte)))
            .andExpect(status().isBadRequest());

        // Validate the Tegenprestatiehoogte in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTegenprestatiehoogtes() throws Exception {
        // Initialize the database
        tegenprestatiehoogteRepository.saveAndFlush(tegenprestatiehoogte);

        // Get all the tegenprestatiehoogteList
        restTegenprestatiehoogteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tegenprestatiehoogte.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getTegenprestatiehoogte() throws Exception {
        // Initialize the database
        tegenprestatiehoogteRepository.saveAndFlush(tegenprestatiehoogte);

        // Get the tegenprestatiehoogte
        restTegenprestatiehoogteMockMvc
            .perform(get(ENTITY_API_URL_ID, tegenprestatiehoogte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tegenprestatiehoogte.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingTegenprestatiehoogte() throws Exception {
        // Get the tegenprestatiehoogte
        restTegenprestatiehoogteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTegenprestatiehoogte() throws Exception {
        // Initialize the database
        tegenprestatiehoogteRepository.saveAndFlush(tegenprestatiehoogte);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tegenprestatiehoogte
        Tegenprestatiehoogte updatedTegenprestatiehoogte = tegenprestatiehoogteRepository
            .findById(tegenprestatiehoogte.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedTegenprestatiehoogte are not directly saved in db
        em.detach(updatedTegenprestatiehoogte);
        updatedTegenprestatiehoogte.code(UPDATED_CODE).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restTegenprestatiehoogteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTegenprestatiehoogte.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTegenprestatiehoogte))
            )
            .andExpect(status().isOk());

        // Validate the Tegenprestatiehoogte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTegenprestatiehoogteToMatchAllProperties(updatedTegenprestatiehoogte);
    }

    @Test
    @Transactional
    void putNonExistingTegenprestatiehoogte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tegenprestatiehoogte.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTegenprestatiehoogteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tegenprestatiehoogte.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tegenprestatiehoogte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tegenprestatiehoogte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTegenprestatiehoogte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tegenprestatiehoogte.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTegenprestatiehoogteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tegenprestatiehoogte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tegenprestatiehoogte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTegenprestatiehoogte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tegenprestatiehoogte.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTegenprestatiehoogteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tegenprestatiehoogte)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tegenprestatiehoogte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTegenprestatiehoogteWithPatch() throws Exception {
        // Initialize the database
        tegenprestatiehoogteRepository.saveAndFlush(tegenprestatiehoogte);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tegenprestatiehoogte using partial update
        Tegenprestatiehoogte partialUpdatedTegenprestatiehoogte = new Tegenprestatiehoogte();
        partialUpdatedTegenprestatiehoogte.setId(tegenprestatiehoogte.getId());

        partialUpdatedTegenprestatiehoogte.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restTegenprestatiehoogteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTegenprestatiehoogte.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTegenprestatiehoogte))
            )
            .andExpect(status().isOk());

        // Validate the Tegenprestatiehoogte in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTegenprestatiehoogteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTegenprestatiehoogte, tegenprestatiehoogte),
            getPersistedTegenprestatiehoogte(tegenprestatiehoogte)
        );
    }

    @Test
    @Transactional
    void fullUpdateTegenprestatiehoogteWithPatch() throws Exception {
        // Initialize the database
        tegenprestatiehoogteRepository.saveAndFlush(tegenprestatiehoogte);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tegenprestatiehoogte using partial update
        Tegenprestatiehoogte partialUpdatedTegenprestatiehoogte = new Tegenprestatiehoogte();
        partialUpdatedTegenprestatiehoogte.setId(tegenprestatiehoogte.getId());

        partialUpdatedTegenprestatiehoogte.code(UPDATED_CODE).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restTegenprestatiehoogteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTegenprestatiehoogte.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTegenprestatiehoogte))
            )
            .andExpect(status().isOk());

        // Validate the Tegenprestatiehoogte in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTegenprestatiehoogteUpdatableFieldsEquals(
            partialUpdatedTegenprestatiehoogte,
            getPersistedTegenprestatiehoogte(partialUpdatedTegenprestatiehoogte)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTegenprestatiehoogte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tegenprestatiehoogte.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTegenprestatiehoogteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tegenprestatiehoogte.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tegenprestatiehoogte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tegenprestatiehoogte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTegenprestatiehoogte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tegenprestatiehoogte.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTegenprestatiehoogteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tegenprestatiehoogte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tegenprestatiehoogte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTegenprestatiehoogte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tegenprestatiehoogte.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTegenprestatiehoogteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tegenprestatiehoogte)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tegenprestatiehoogte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTegenprestatiehoogte() throws Exception {
        // Initialize the database
        tegenprestatiehoogteRepository.saveAndFlush(tegenprestatiehoogte);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tegenprestatiehoogte
        restTegenprestatiehoogteMockMvc
            .perform(delete(ENTITY_API_URL_ID, tegenprestatiehoogte.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tegenprestatiehoogteRepository.count();
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

    protected Tegenprestatiehoogte getPersistedTegenprestatiehoogte(Tegenprestatiehoogte tegenprestatiehoogte) {
        return tegenprestatiehoogteRepository.findById(tegenprestatiehoogte.getId()).orElseThrow();
    }

    protected void assertPersistedTegenprestatiehoogteToMatchAllProperties(Tegenprestatiehoogte expectedTegenprestatiehoogte) {
        assertTegenprestatiehoogteAllPropertiesEquals(
            expectedTegenprestatiehoogte,
            getPersistedTegenprestatiehoogte(expectedTegenprestatiehoogte)
        );
    }

    protected void assertPersistedTegenprestatiehoogteToMatchUpdatableProperties(Tegenprestatiehoogte expectedTegenprestatiehoogte) {
        assertTegenprestatiehoogteAllUpdatablePropertiesEquals(
            expectedTegenprestatiehoogte,
            getPersistedTegenprestatiehoogte(expectedTegenprestatiehoogte)
        );
    }
}
