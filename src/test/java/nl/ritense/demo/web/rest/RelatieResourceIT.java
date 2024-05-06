package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RelatieAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Relatie;
import nl.ritense.demo.repository.RelatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RelatieResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RelatieResourceIT {

    private static final String DEFAULT_RELATIESOORT = "AAAAAAAAAA";
    private static final String UPDATED_RELATIESOORT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/relaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RelatieRepository relatieRepository;

    @Mock
    private RelatieRepository relatieRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRelatieMockMvc;

    private Relatie relatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relatie createEntity(EntityManager em) {
        Relatie relatie = new Relatie().relatiesoort(DEFAULT_RELATIESOORT);
        return relatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relatie createUpdatedEntity(EntityManager em) {
        Relatie relatie = new Relatie().relatiesoort(UPDATED_RELATIESOORT);
        return relatie;
    }

    @BeforeEach
    public void initTest() {
        relatie = createEntity(em);
    }

    @Test
    @Transactional
    void createRelatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Relatie
        var returnedRelatie = om.readValue(
            restRelatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(relatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Relatie.class
        );

        // Validate the Relatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRelatieUpdatableFieldsEquals(returnedRelatie, getPersistedRelatie(returnedRelatie));
    }

    @Test
    @Transactional
    void createRelatieWithExistingId() throws Exception {
        // Create the Relatie with an existing ID
        relatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(relatie)))
            .andExpect(status().isBadRequest());

        // Validate the Relatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRelaties() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get all the relatieList
        restRelatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].relatiesoort").value(hasItem(DEFAULT_RELATIESOORT)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRelatiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(relatieRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRelatieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(relatieRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRelatiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(relatieRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRelatieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(relatieRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRelatie() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        // Get the relatie
        restRelatieMockMvc
            .perform(get(ENTITY_API_URL_ID, relatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(relatie.getId().intValue()))
            .andExpect(jsonPath("$.relatiesoort").value(DEFAULT_RELATIESOORT));
    }

    @Test
    @Transactional
    void getNonExistingRelatie() throws Exception {
        // Get the relatie
        restRelatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRelatie() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the relatie
        Relatie updatedRelatie = relatieRepository.findById(relatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRelatie are not directly saved in db
        em.detach(updatedRelatie);
        updatedRelatie.relatiesoort(UPDATED_RELATIESOORT);

        restRelatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRelatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRelatie))
            )
            .andExpect(status().isOk());

        // Validate the Relatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRelatieToMatchAllProperties(updatedRelatie);
    }

    @Test
    @Transactional
    void putNonExistingRelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        relatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatieMockMvc
            .perform(put(ENTITY_API_URL_ID, relatie.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(relatie)))
            .andExpect(status().isBadRequest());

        // Validate the Relatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        relatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(relatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        relatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(relatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Relatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRelatieWithPatch() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the relatie using partial update
        Relatie partialUpdatedRelatie = new Relatie();
        partialUpdatedRelatie.setId(relatie.getId());

        partialUpdatedRelatie.relatiesoort(UPDATED_RELATIESOORT);

        restRelatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRelatie))
            )
            .andExpect(status().isOk());

        // Validate the Relatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRelatieUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRelatie, relatie), getPersistedRelatie(relatie));
    }

    @Test
    @Transactional
    void fullUpdateRelatieWithPatch() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the relatie using partial update
        Relatie partialUpdatedRelatie = new Relatie();
        partialUpdatedRelatie.setId(relatie.getId());

        partialUpdatedRelatie.relatiesoort(UPDATED_RELATIESOORT);

        restRelatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRelatie))
            )
            .andExpect(status().isOk());

        // Validate the Relatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRelatieUpdatableFieldsEquals(partialUpdatedRelatie, getPersistedRelatie(partialUpdatedRelatie));
    }

    @Test
    @Transactional
    void patchNonExistingRelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        relatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, relatie.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(relatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        relatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(relatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        relatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(relatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Relatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRelatie() throws Exception {
        // Initialize the database
        relatieRepository.saveAndFlush(relatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the relatie
        restRelatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, relatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return relatieRepository.count();
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

    protected Relatie getPersistedRelatie(Relatie relatie) {
        return relatieRepository.findById(relatie.getId()).orElseThrow();
    }

    protected void assertPersistedRelatieToMatchAllProperties(Relatie expectedRelatie) {
        assertRelatieAllPropertiesEquals(expectedRelatie, getPersistedRelatie(expectedRelatie));
    }

    protected void assertPersistedRelatieToMatchUpdatableProperties(Relatie expectedRelatie) {
        assertRelatieAllUpdatablePropertiesEquals(expectedRelatie, getPersistedRelatie(expectedRelatie));
    }
}
