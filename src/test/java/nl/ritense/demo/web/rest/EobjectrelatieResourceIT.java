package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EobjectrelatieAsserts.*;
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
import nl.ritense.demo.domain.Eobjectrelatie;
import nl.ritense.demo.repository.EobjectrelatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EobjectrelatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EobjectrelatieResourceIT {

    private static final String DEFAULT_ROL = "AAAAAAAAAA";
    private static final String UPDATED_ROL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/eobjectrelaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EobjectrelatieRepository eobjectrelatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEobjectrelatieMockMvc;

    private Eobjectrelatie eobjectrelatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjectrelatie createEntity(EntityManager em) {
        Eobjectrelatie eobjectrelatie = new Eobjectrelatie().rol(DEFAULT_ROL);
        return eobjectrelatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjectrelatie createUpdatedEntity(EntityManager em) {
        Eobjectrelatie eobjectrelatie = new Eobjectrelatie().rol(UPDATED_ROL);
        return eobjectrelatie;
    }

    @BeforeEach
    public void initTest() {
        eobjectrelatie = createEntity(em);
    }

    @Test
    @Transactional
    void createEobjectrelatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Eobjectrelatie
        var returnedEobjectrelatie = om.readValue(
            restEobjectrelatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjectrelatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Eobjectrelatie.class
        );

        // Validate the Eobjectrelatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEobjectrelatieUpdatableFieldsEquals(returnedEobjectrelatie, getPersistedEobjectrelatie(returnedEobjectrelatie));
    }

    @Test
    @Transactional
    void createEobjectrelatieWithExistingId() throws Exception {
        // Create the Eobjectrelatie with an existing ID
        eobjectrelatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEobjectrelatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjectrelatie)))
            .andExpect(status().isBadRequest());

        // Validate the Eobjectrelatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEobjectrelaties() throws Exception {
        // Initialize the database
        eobjectrelatieRepository.saveAndFlush(eobjectrelatie);

        // Get all the eobjectrelatieList
        restEobjectrelatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eobjectrelatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].rol").value(hasItem(DEFAULT_ROL)));
    }

    @Test
    @Transactional
    void getEobjectrelatie() throws Exception {
        // Initialize the database
        eobjectrelatieRepository.saveAndFlush(eobjectrelatie);

        // Get the eobjectrelatie
        restEobjectrelatieMockMvc
            .perform(get(ENTITY_API_URL_ID, eobjectrelatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eobjectrelatie.getId().intValue()))
            .andExpect(jsonPath("$.rol").value(DEFAULT_ROL));
    }

    @Test
    @Transactional
    void getNonExistingEobjectrelatie() throws Exception {
        // Get the eobjectrelatie
        restEobjectrelatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEobjectrelatie() throws Exception {
        // Initialize the database
        eobjectrelatieRepository.saveAndFlush(eobjectrelatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eobjectrelatie
        Eobjectrelatie updatedEobjectrelatie = eobjectrelatieRepository.findById(eobjectrelatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEobjectrelatie are not directly saved in db
        em.detach(updatedEobjectrelatie);
        updatedEobjectrelatie.rol(UPDATED_ROL);

        restEobjectrelatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEobjectrelatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEobjectrelatie))
            )
            .andExpect(status().isOk());

        // Validate the Eobjectrelatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEobjectrelatieToMatchAllProperties(updatedEobjectrelatie);
    }

    @Test
    @Transactional
    void putNonExistingEobjectrelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjectrelatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEobjectrelatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eobjectrelatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eobjectrelatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eobjectrelatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEobjectrelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjectrelatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEobjectrelatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eobjectrelatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eobjectrelatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEobjectrelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjectrelatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEobjectrelatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjectrelatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eobjectrelatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEobjectrelatieWithPatch() throws Exception {
        // Initialize the database
        eobjectrelatieRepository.saveAndFlush(eobjectrelatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eobjectrelatie using partial update
        Eobjectrelatie partialUpdatedEobjectrelatie = new Eobjectrelatie();
        partialUpdatedEobjectrelatie.setId(eobjectrelatie.getId());

        restEobjectrelatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEobjectrelatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEobjectrelatie))
            )
            .andExpect(status().isOk());

        // Validate the Eobjectrelatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEobjectrelatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEobjectrelatie, eobjectrelatie),
            getPersistedEobjectrelatie(eobjectrelatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateEobjectrelatieWithPatch() throws Exception {
        // Initialize the database
        eobjectrelatieRepository.saveAndFlush(eobjectrelatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eobjectrelatie using partial update
        Eobjectrelatie partialUpdatedEobjectrelatie = new Eobjectrelatie();
        partialUpdatedEobjectrelatie.setId(eobjectrelatie.getId());

        partialUpdatedEobjectrelatie.rol(UPDATED_ROL);

        restEobjectrelatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEobjectrelatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEobjectrelatie))
            )
            .andExpect(status().isOk());

        // Validate the Eobjectrelatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEobjectrelatieUpdatableFieldsEquals(partialUpdatedEobjectrelatie, getPersistedEobjectrelatie(partialUpdatedEobjectrelatie));
    }

    @Test
    @Transactional
    void patchNonExistingEobjectrelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjectrelatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEobjectrelatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eobjectrelatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eobjectrelatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eobjectrelatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEobjectrelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjectrelatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEobjectrelatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eobjectrelatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eobjectrelatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEobjectrelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjectrelatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEobjectrelatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(eobjectrelatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eobjectrelatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEobjectrelatie() throws Exception {
        // Initialize the database
        eobjectrelatieRepository.saveAndFlush(eobjectrelatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eobjectrelatie
        restEobjectrelatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, eobjectrelatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return eobjectrelatieRepository.count();
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

    protected Eobjectrelatie getPersistedEobjectrelatie(Eobjectrelatie eobjectrelatie) {
        return eobjectrelatieRepository.findById(eobjectrelatie.getId()).orElseThrow();
    }

    protected void assertPersistedEobjectrelatieToMatchAllProperties(Eobjectrelatie expectedEobjectrelatie) {
        assertEobjectrelatieAllPropertiesEquals(expectedEobjectrelatie, getPersistedEobjectrelatie(expectedEobjectrelatie));
    }

    protected void assertPersistedEobjectrelatieToMatchUpdatableProperties(Eobjectrelatie expectedEobjectrelatie) {
        assertEobjectrelatieAllUpdatablePropertiesEquals(expectedEobjectrelatie, getPersistedEobjectrelatie(expectedEobjectrelatie));
    }
}
