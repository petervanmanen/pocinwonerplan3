package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.IncidentAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Incident;
import nl.ritense.demo.repository.IncidentRepository;
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
 * Integration tests for the {@link IncidentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class IncidentResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIE = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/incidents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IncidentRepository incidentRepository;

    @Mock
    private IncidentRepository incidentRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIncidentMockMvc;

    private Incident incident;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incident createEntity(EntityManager em) {
        Incident incident = new Incident()
            .datum(DEFAULT_DATUM)
            .locatie(DEFAULT_LOCATIE)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return incident;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incident createUpdatedEntity(EntityManager em) {
        Incident incident = new Incident()
            .datum(UPDATED_DATUM)
            .locatie(UPDATED_LOCATIE)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return incident;
    }

    @BeforeEach
    public void initTest() {
        incident = createEntity(em);
    }

    @Test
    @Transactional
    void createIncident() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Incident
        var returnedIncident = om.readValue(
            restIncidentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(incident)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Incident.class
        );

        // Validate the Incident in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertIncidentUpdatableFieldsEquals(returnedIncident, getPersistedIncident(returnedIncident));
    }

    @Test
    @Transactional
    void createIncidentWithExistingId() throws Exception {
        // Create the Incident with an existing ID
        incident.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncidentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(incident)))
            .andExpect(status().isBadRequest());

        // Validate the Incident in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIncidents() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList
        restIncidentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incident.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].locatie").value(hasItem(DEFAULT_LOCATIE)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllIncidentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(incidentRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restIncidentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(incidentRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllIncidentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(incidentRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restIncidentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(incidentRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get the incident
        restIncidentMockMvc
            .perform(get(ENTITY_API_URL_ID, incident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(incident.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.locatie").value(DEFAULT_LOCATIE))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingIncident() throws Exception {
        // Get the incident
        restIncidentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the incident
        Incident updatedIncident = incidentRepository.findById(incident.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIncident are not directly saved in db
        em.detach(updatedIncident);
        updatedIncident.datum(UPDATED_DATUM).locatie(UPDATED_LOCATIE).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restIncidentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIncident.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedIncident))
            )
            .andExpect(status().isOk());

        // Validate the Incident in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIncidentToMatchAllProperties(updatedIncident);
    }

    @Test
    @Transactional
    void putNonExistingIncident() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incident.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncidentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, incident.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(incident))
            )
            .andExpect(status().isBadRequest());

        // Validate the Incident in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIncident() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incident.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncidentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(incident))
            )
            .andExpect(status().isBadRequest());

        // Validate the Incident in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIncident() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incident.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncidentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(incident)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Incident in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIncidentWithPatch() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the incident using partial update
        Incident partialUpdatedIncident = new Incident();
        partialUpdatedIncident.setId(incident.getId());

        partialUpdatedIncident.locatie(UPDATED_LOCATIE).naam(UPDATED_NAAM);

        restIncidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIncident.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIncident))
            )
            .andExpect(status().isOk());

        // Validate the Incident in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIncidentUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedIncident, incident), getPersistedIncident(incident));
    }

    @Test
    @Transactional
    void fullUpdateIncidentWithPatch() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the incident using partial update
        Incident partialUpdatedIncident = new Incident();
        partialUpdatedIncident.setId(incident.getId());

        partialUpdatedIncident.datum(UPDATED_DATUM).locatie(UPDATED_LOCATIE).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restIncidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIncident.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIncident))
            )
            .andExpect(status().isOk());

        // Validate the Incident in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIncidentUpdatableFieldsEquals(partialUpdatedIncident, getPersistedIncident(partialUpdatedIncident));
    }

    @Test
    @Transactional
    void patchNonExistingIncident() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incident.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, incident.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(incident))
            )
            .andExpect(status().isBadRequest());

        // Validate the Incident in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIncident() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incident.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(incident))
            )
            .andExpect(status().isBadRequest());

        // Validate the Incident in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIncident() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incident.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncidentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(incident)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Incident in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the incident
        restIncidentMockMvc
            .perform(delete(ENTITY_API_URL_ID, incident.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return incidentRepository.count();
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

    protected Incident getPersistedIncident(Incident incident) {
        return incidentRepository.findById(incident.getId()).orElseThrow();
    }

    protected void assertPersistedIncidentToMatchAllProperties(Incident expectedIncident) {
        assertIncidentAllPropertiesEquals(expectedIncident, getPersistedIncident(expectedIncident));
    }

    protected void assertPersistedIncidentToMatchUpdatableProperties(Incident expectedIncident) {
        assertIncidentAllUpdatablePropertiesEquals(expectedIncident, getPersistedIncident(expectedIncident));
    }
}
