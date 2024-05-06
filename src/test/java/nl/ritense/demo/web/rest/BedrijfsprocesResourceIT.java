package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BedrijfsprocesAsserts.*;
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
import nl.ritense.demo.domain.Bedrijfsproces;
import nl.ritense.demo.repository.BedrijfsprocesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
 * Integration tests for the {@link BedrijfsprocesResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BedrijfsprocesResourceIT {

    private static final String DEFAULT_AFGEROND = "AAAAAAAAAA";
    private static final String UPDATED_AFGEROND = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMEIND = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEIND = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bedrijfsproces";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BedrijfsprocesRepository bedrijfsprocesRepository;

    @Mock
    private BedrijfsprocesRepository bedrijfsprocesRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBedrijfsprocesMockMvc;

    private Bedrijfsproces bedrijfsproces;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bedrijfsproces createEntity(EntityManager em) {
        Bedrijfsproces bedrijfsproces = new Bedrijfsproces()
            .afgerond(DEFAULT_AFGEROND)
            .datumeind(DEFAULT_DATUMEIND)
            .datumstart(DEFAULT_DATUMSTART)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return bedrijfsproces;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bedrijfsproces createUpdatedEntity(EntityManager em) {
        Bedrijfsproces bedrijfsproces = new Bedrijfsproces()
            .afgerond(UPDATED_AFGEROND)
            .datumeind(UPDATED_DATUMEIND)
            .datumstart(UPDATED_DATUMSTART)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return bedrijfsproces;
    }

    @BeforeEach
    public void initTest() {
        bedrijfsproces = createEntity(em);
    }

    @Test
    @Transactional
    void createBedrijfsproces() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bedrijfsproces
        var returnedBedrijfsproces = om.readValue(
            restBedrijfsprocesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bedrijfsproces)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bedrijfsproces.class
        );

        // Validate the Bedrijfsproces in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBedrijfsprocesUpdatableFieldsEquals(returnedBedrijfsproces, getPersistedBedrijfsproces(returnedBedrijfsproces));
    }

    @Test
    @Transactional
    void createBedrijfsprocesWithExistingId() throws Exception {
        // Create the Bedrijfsproces with an existing ID
        bedrijfsproces.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBedrijfsprocesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bedrijfsproces)))
            .andExpect(status().isBadRequest());

        // Validate the Bedrijfsproces in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBedrijfsproces() throws Exception {
        // Initialize the database
        bedrijfsprocesRepository.saveAndFlush(bedrijfsproces);

        // Get all the bedrijfsprocesList
        restBedrijfsprocesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bedrijfsproces.getId().intValue())))
            .andExpect(jsonPath("$.[*].afgerond").value(hasItem(DEFAULT_AFGEROND)))
            .andExpect(jsonPath("$.[*].datumeind").value(hasItem(DEFAULT_DATUMEIND.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBedrijfsprocesWithEagerRelationshipsIsEnabled() throws Exception {
        when(bedrijfsprocesRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBedrijfsprocesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(bedrijfsprocesRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBedrijfsprocesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(bedrijfsprocesRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBedrijfsprocesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(bedrijfsprocesRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getBedrijfsproces() throws Exception {
        // Initialize the database
        bedrijfsprocesRepository.saveAndFlush(bedrijfsproces);

        // Get the bedrijfsproces
        restBedrijfsprocesMockMvc
            .perform(get(ENTITY_API_URL_ID, bedrijfsproces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bedrijfsproces.getId().intValue()))
            .andExpect(jsonPath("$.afgerond").value(DEFAULT_AFGEROND))
            .andExpect(jsonPath("$.datumeind").value(DEFAULT_DATUMEIND.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingBedrijfsproces() throws Exception {
        // Get the bedrijfsproces
        restBedrijfsprocesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBedrijfsproces() throws Exception {
        // Initialize the database
        bedrijfsprocesRepository.saveAndFlush(bedrijfsproces);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bedrijfsproces
        Bedrijfsproces updatedBedrijfsproces = bedrijfsprocesRepository.findById(bedrijfsproces.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBedrijfsproces are not directly saved in db
        em.detach(updatedBedrijfsproces);
        updatedBedrijfsproces
            .afgerond(UPDATED_AFGEROND)
            .datumeind(UPDATED_DATUMEIND)
            .datumstart(UPDATED_DATUMSTART)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restBedrijfsprocesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBedrijfsproces.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBedrijfsproces))
            )
            .andExpect(status().isOk());

        // Validate the Bedrijfsproces in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBedrijfsprocesToMatchAllProperties(updatedBedrijfsproces);
    }

    @Test
    @Transactional
    void putNonExistingBedrijfsproces() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bedrijfsproces.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBedrijfsprocesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bedrijfsproces.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bedrijfsproces))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bedrijfsproces in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBedrijfsproces() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bedrijfsproces.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBedrijfsprocesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bedrijfsproces))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bedrijfsproces in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBedrijfsproces() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bedrijfsproces.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBedrijfsprocesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bedrijfsproces)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bedrijfsproces in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBedrijfsprocesWithPatch() throws Exception {
        // Initialize the database
        bedrijfsprocesRepository.saveAndFlush(bedrijfsproces);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bedrijfsproces using partial update
        Bedrijfsproces partialUpdatedBedrijfsproces = new Bedrijfsproces();
        partialUpdatedBedrijfsproces.setId(bedrijfsproces.getId());

        partialUpdatedBedrijfsproces.datumeind(UPDATED_DATUMEIND).datumstart(UPDATED_DATUMSTART).omschrijving(UPDATED_OMSCHRIJVING);

        restBedrijfsprocesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBedrijfsproces.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBedrijfsproces))
            )
            .andExpect(status().isOk());

        // Validate the Bedrijfsproces in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBedrijfsprocesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBedrijfsproces, bedrijfsproces),
            getPersistedBedrijfsproces(bedrijfsproces)
        );
    }

    @Test
    @Transactional
    void fullUpdateBedrijfsprocesWithPatch() throws Exception {
        // Initialize the database
        bedrijfsprocesRepository.saveAndFlush(bedrijfsproces);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bedrijfsproces using partial update
        Bedrijfsproces partialUpdatedBedrijfsproces = new Bedrijfsproces();
        partialUpdatedBedrijfsproces.setId(bedrijfsproces.getId());

        partialUpdatedBedrijfsproces
            .afgerond(UPDATED_AFGEROND)
            .datumeind(UPDATED_DATUMEIND)
            .datumstart(UPDATED_DATUMSTART)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restBedrijfsprocesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBedrijfsproces.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBedrijfsproces))
            )
            .andExpect(status().isOk());

        // Validate the Bedrijfsproces in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBedrijfsprocesUpdatableFieldsEquals(partialUpdatedBedrijfsproces, getPersistedBedrijfsproces(partialUpdatedBedrijfsproces));
    }

    @Test
    @Transactional
    void patchNonExistingBedrijfsproces() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bedrijfsproces.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBedrijfsprocesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bedrijfsproces.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bedrijfsproces))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bedrijfsproces in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBedrijfsproces() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bedrijfsproces.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBedrijfsprocesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bedrijfsproces))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bedrijfsproces in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBedrijfsproces() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bedrijfsproces.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBedrijfsprocesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bedrijfsproces)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bedrijfsproces in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBedrijfsproces() throws Exception {
        // Initialize the database
        bedrijfsprocesRepository.saveAndFlush(bedrijfsproces);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bedrijfsproces
        restBedrijfsprocesMockMvc
            .perform(delete(ENTITY_API_URL_ID, bedrijfsproces.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bedrijfsprocesRepository.count();
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

    protected Bedrijfsproces getPersistedBedrijfsproces(Bedrijfsproces bedrijfsproces) {
        return bedrijfsprocesRepository.findById(bedrijfsproces.getId()).orElseThrow();
    }

    protected void assertPersistedBedrijfsprocesToMatchAllProperties(Bedrijfsproces expectedBedrijfsproces) {
        assertBedrijfsprocesAllPropertiesEquals(expectedBedrijfsproces, getPersistedBedrijfsproces(expectedBedrijfsproces));
    }

    protected void assertPersistedBedrijfsprocesToMatchUpdatableProperties(Bedrijfsproces expectedBedrijfsproces) {
        assertBedrijfsprocesAllUpdatablePropertiesEquals(expectedBedrijfsproces, getPersistedBedrijfsproces(expectedBedrijfsproces));
    }
}
