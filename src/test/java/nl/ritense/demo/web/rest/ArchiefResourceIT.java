package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ArchiefAsserts.*;
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
import nl.ritense.demo.domain.Archief;
import nl.ritense.demo.repository.ArchiefRepository;
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
 * Integration tests for the {@link ArchiefResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ArchiefResourceIT {

    private static final String DEFAULT_ARCHIEFNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_ARCHIEFNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_OPENBAARHEIDSBEPERKING = "AAAAAAAAAA";
    private static final String UPDATED_OPENBAARHEIDSBEPERKING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/archiefs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ArchiefRepository archiefRepository;

    @Mock
    private ArchiefRepository archiefRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArchiefMockMvc;

    private Archief archief;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Archief createEntity(EntityManager em) {
        Archief archief = new Archief()
            .archiefnummer(DEFAULT_ARCHIEFNUMMER)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .openbaarheidsbeperking(DEFAULT_OPENBAARHEIDSBEPERKING);
        return archief;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Archief createUpdatedEntity(EntityManager em) {
        Archief archief = new Archief()
            .archiefnummer(UPDATED_ARCHIEFNUMMER)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .openbaarheidsbeperking(UPDATED_OPENBAARHEIDSBEPERKING);
        return archief;
    }

    @BeforeEach
    public void initTest() {
        archief = createEntity(em);
    }

    @Test
    @Transactional
    void createArchief() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Archief
        var returnedArchief = om.readValue(
            restArchiefMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archief)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Archief.class
        );

        // Validate the Archief in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertArchiefUpdatableFieldsEquals(returnedArchief, getPersistedArchief(returnedArchief));
    }

    @Test
    @Transactional
    void createArchiefWithExistingId() throws Exception {
        // Create the Archief with an existing ID
        archief.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArchiefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archief)))
            .andExpect(status().isBadRequest());

        // Validate the Archief in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArchiefs() throws Exception {
        // Initialize the database
        archiefRepository.saveAndFlush(archief);

        // Get all the archiefList
        restArchiefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(archief.getId().intValue())))
            .andExpect(jsonPath("$.[*].archiefnummer").value(hasItem(DEFAULT_ARCHIEFNUMMER)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].openbaarheidsbeperking").value(hasItem(DEFAULT_OPENBAARHEIDSBEPERKING)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArchiefsWithEagerRelationshipsIsEnabled() throws Exception {
        when(archiefRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArchiefMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(archiefRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArchiefsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(archiefRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArchiefMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(archiefRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getArchief() throws Exception {
        // Initialize the database
        archiefRepository.saveAndFlush(archief);

        // Get the archief
        restArchiefMockMvc
            .perform(get(ENTITY_API_URL_ID, archief.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(archief.getId().intValue()))
            .andExpect(jsonPath("$.archiefnummer").value(DEFAULT_ARCHIEFNUMMER))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.openbaarheidsbeperking").value(DEFAULT_OPENBAARHEIDSBEPERKING));
    }

    @Test
    @Transactional
    void getNonExistingArchief() throws Exception {
        // Get the archief
        restArchiefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArchief() throws Exception {
        // Initialize the database
        archiefRepository.saveAndFlush(archief);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the archief
        Archief updatedArchief = archiefRepository.findById(archief.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArchief are not directly saved in db
        em.detach(updatedArchief);
        updatedArchief
            .archiefnummer(UPDATED_ARCHIEFNUMMER)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .openbaarheidsbeperking(UPDATED_OPENBAARHEIDSBEPERKING);

        restArchiefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedArchief.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedArchief))
            )
            .andExpect(status().isOk());

        // Validate the Archief in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedArchiefToMatchAllProperties(updatedArchief);
    }

    @Test
    @Transactional
    void putNonExistingArchief() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archief.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArchiefMockMvc
            .perform(put(ENTITY_API_URL_ID, archief.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archief)))
            .andExpect(status().isBadRequest());

        // Validate the Archief in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArchief() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archief.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchiefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(archief))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archief in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArchief() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archief.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchiefMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archief)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Archief in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArchiefWithPatch() throws Exception {
        // Initialize the database
        archiefRepository.saveAndFlush(archief);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the archief using partial update
        Archief partialUpdatedArchief = new Archief();
        partialUpdatedArchief.setId(archief.getId());

        restArchiefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArchief.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArchief))
            )
            .andExpect(status().isOk());

        // Validate the Archief in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArchiefUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedArchief, archief), getPersistedArchief(archief));
    }

    @Test
    @Transactional
    void fullUpdateArchiefWithPatch() throws Exception {
        // Initialize the database
        archiefRepository.saveAndFlush(archief);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the archief using partial update
        Archief partialUpdatedArchief = new Archief();
        partialUpdatedArchief.setId(archief.getId());

        partialUpdatedArchief
            .archiefnummer(UPDATED_ARCHIEFNUMMER)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .openbaarheidsbeperking(UPDATED_OPENBAARHEIDSBEPERKING);

        restArchiefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArchief.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArchief))
            )
            .andExpect(status().isOk());

        // Validate the Archief in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArchiefUpdatableFieldsEquals(partialUpdatedArchief, getPersistedArchief(partialUpdatedArchief));
    }

    @Test
    @Transactional
    void patchNonExistingArchief() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archief.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArchiefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, archief.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(archief))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archief in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArchief() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archief.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchiefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(archief))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archief in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArchief() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archief.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchiefMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(archief)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Archief in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArchief() throws Exception {
        // Initialize the database
        archiefRepository.saveAndFlush(archief);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the archief
        restArchiefMockMvc
            .perform(delete(ENTITY_API_URL_ID, archief.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return archiefRepository.count();
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

    protected Archief getPersistedArchief(Archief archief) {
        return archiefRepository.findById(archief.getId()).orElseThrow();
    }

    protected void assertPersistedArchiefToMatchAllProperties(Archief expectedArchief) {
        assertArchiefAllPropertiesEquals(expectedArchief, getPersistedArchief(expectedArchief));
    }

    protected void assertPersistedArchiefToMatchUpdatableProperties(Archief expectedArchief) {
        assertArchiefAllUpdatablePropertiesEquals(expectedArchief, getPersistedArchief(expectedArchief));
    }
}
