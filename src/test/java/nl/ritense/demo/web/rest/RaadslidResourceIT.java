package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RaadslidAsserts.*;
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
import nl.ritense.demo.domain.Raadslid;
import nl.ritense.demo.repository.RaadslidRepository;
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
 * Integration tests for the {@link RaadslidResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RaadslidResourceIT {

    private static final String DEFAULT_ACHTERNAAM = "AAAAAAAAAA";
    private static final String UPDATED_ACHTERNAAM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMAANSTELLING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANSTELLING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMUITTREDING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMUITTREDING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FRACTIE = "AAAAAAAAAA";
    private static final String UPDATED_FRACTIE = "BBBBBBBBBB";

    private static final String DEFAULT_TITEL = "AAAAAAAAAA";
    private static final String UPDATED_TITEL = "BBBBBBBBBB";

    private static final String DEFAULT_VOORNAAM = "AAAAAAAAAA";
    private static final String UPDATED_VOORNAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/raadslids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RaadslidRepository raadslidRepository;

    @Mock
    private RaadslidRepository raadslidRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRaadslidMockMvc;

    private Raadslid raadslid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Raadslid createEntity(EntityManager em) {
        Raadslid raadslid = new Raadslid()
            .achternaam(DEFAULT_ACHTERNAAM)
            .datumaanstelling(DEFAULT_DATUMAANSTELLING)
            .datumuittreding(DEFAULT_DATUMUITTREDING)
            .fractie(DEFAULT_FRACTIE)
            .titel(DEFAULT_TITEL)
            .voornaam(DEFAULT_VOORNAAM);
        return raadslid;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Raadslid createUpdatedEntity(EntityManager em) {
        Raadslid raadslid = new Raadslid()
            .achternaam(UPDATED_ACHTERNAAM)
            .datumaanstelling(UPDATED_DATUMAANSTELLING)
            .datumuittreding(UPDATED_DATUMUITTREDING)
            .fractie(UPDATED_FRACTIE)
            .titel(UPDATED_TITEL)
            .voornaam(UPDATED_VOORNAAM);
        return raadslid;
    }

    @BeforeEach
    public void initTest() {
        raadslid = createEntity(em);
    }

    @Test
    @Transactional
    void createRaadslid() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Raadslid
        var returnedRaadslid = om.readValue(
            restRaadslidMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(raadslid)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Raadslid.class
        );

        // Validate the Raadslid in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRaadslidUpdatableFieldsEquals(returnedRaadslid, getPersistedRaadslid(returnedRaadslid));
    }

    @Test
    @Transactional
    void createRaadslidWithExistingId() throws Exception {
        // Create the Raadslid with an existing ID
        raadslid.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRaadslidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(raadslid)))
            .andExpect(status().isBadRequest());

        // Validate the Raadslid in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRaadslids() throws Exception {
        // Initialize the database
        raadslidRepository.saveAndFlush(raadslid);

        // Get all the raadslidList
        restRaadslidMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raadslid.getId().intValue())))
            .andExpect(jsonPath("$.[*].achternaam").value(hasItem(DEFAULT_ACHTERNAAM)))
            .andExpect(jsonPath("$.[*].datumaanstelling").value(hasItem(DEFAULT_DATUMAANSTELLING.toString())))
            .andExpect(jsonPath("$.[*].datumuittreding").value(hasItem(DEFAULT_DATUMUITTREDING.toString())))
            .andExpect(jsonPath("$.[*].fractie").value(hasItem(DEFAULT_FRACTIE)))
            .andExpect(jsonPath("$.[*].titel").value(hasItem(DEFAULT_TITEL)))
            .andExpect(jsonPath("$.[*].voornaam").value(hasItem(DEFAULT_VOORNAAM)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRaadslidsWithEagerRelationshipsIsEnabled() throws Exception {
        when(raadslidRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRaadslidMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(raadslidRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRaadslidsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(raadslidRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRaadslidMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(raadslidRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRaadslid() throws Exception {
        // Initialize the database
        raadslidRepository.saveAndFlush(raadslid);

        // Get the raadslid
        restRaadslidMockMvc
            .perform(get(ENTITY_API_URL_ID, raadslid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(raadslid.getId().intValue()))
            .andExpect(jsonPath("$.achternaam").value(DEFAULT_ACHTERNAAM))
            .andExpect(jsonPath("$.datumaanstelling").value(DEFAULT_DATUMAANSTELLING.toString()))
            .andExpect(jsonPath("$.datumuittreding").value(DEFAULT_DATUMUITTREDING.toString()))
            .andExpect(jsonPath("$.fractie").value(DEFAULT_FRACTIE))
            .andExpect(jsonPath("$.titel").value(DEFAULT_TITEL))
            .andExpect(jsonPath("$.voornaam").value(DEFAULT_VOORNAAM));
    }

    @Test
    @Transactional
    void getNonExistingRaadslid() throws Exception {
        // Get the raadslid
        restRaadslidMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRaadslid() throws Exception {
        // Initialize the database
        raadslidRepository.saveAndFlush(raadslid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the raadslid
        Raadslid updatedRaadslid = raadslidRepository.findById(raadslid.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRaadslid are not directly saved in db
        em.detach(updatedRaadslid);
        updatedRaadslid
            .achternaam(UPDATED_ACHTERNAAM)
            .datumaanstelling(UPDATED_DATUMAANSTELLING)
            .datumuittreding(UPDATED_DATUMUITTREDING)
            .fractie(UPDATED_FRACTIE)
            .titel(UPDATED_TITEL)
            .voornaam(UPDATED_VOORNAAM);

        restRaadslidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRaadslid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRaadslid))
            )
            .andExpect(status().isOk());

        // Validate the Raadslid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRaadslidToMatchAllProperties(updatedRaadslid);
    }

    @Test
    @Transactional
    void putNonExistingRaadslid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadslid.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRaadslidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, raadslid.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(raadslid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Raadslid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRaadslid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadslid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaadslidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(raadslid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Raadslid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRaadslid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadslid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaadslidMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(raadslid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Raadslid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRaadslidWithPatch() throws Exception {
        // Initialize the database
        raadslidRepository.saveAndFlush(raadslid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the raadslid using partial update
        Raadslid partialUpdatedRaadslid = new Raadslid();
        partialUpdatedRaadslid.setId(raadslid.getId());

        partialUpdatedRaadslid
            .datumaanstelling(UPDATED_DATUMAANSTELLING)
            .datumuittreding(UPDATED_DATUMUITTREDING)
            .fractie(UPDATED_FRACTIE)
            .voornaam(UPDATED_VOORNAAM);

        restRaadslidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRaadslid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRaadslid))
            )
            .andExpect(status().isOk());

        // Validate the Raadslid in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRaadslidUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRaadslid, raadslid), getPersistedRaadslid(raadslid));
    }

    @Test
    @Transactional
    void fullUpdateRaadslidWithPatch() throws Exception {
        // Initialize the database
        raadslidRepository.saveAndFlush(raadslid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the raadslid using partial update
        Raadslid partialUpdatedRaadslid = new Raadslid();
        partialUpdatedRaadslid.setId(raadslid.getId());

        partialUpdatedRaadslid
            .achternaam(UPDATED_ACHTERNAAM)
            .datumaanstelling(UPDATED_DATUMAANSTELLING)
            .datumuittreding(UPDATED_DATUMUITTREDING)
            .fractie(UPDATED_FRACTIE)
            .titel(UPDATED_TITEL)
            .voornaam(UPDATED_VOORNAAM);

        restRaadslidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRaadslid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRaadslid))
            )
            .andExpect(status().isOk());

        // Validate the Raadslid in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRaadslidUpdatableFieldsEquals(partialUpdatedRaadslid, getPersistedRaadslid(partialUpdatedRaadslid));
    }

    @Test
    @Transactional
    void patchNonExistingRaadslid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadslid.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRaadslidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, raadslid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(raadslid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Raadslid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRaadslid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadslid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaadslidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(raadslid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Raadslid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRaadslid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadslid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaadslidMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(raadslid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Raadslid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRaadslid() throws Exception {
        // Initialize the database
        raadslidRepository.saveAndFlush(raadslid);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the raadslid
        restRaadslidMockMvc
            .perform(delete(ENTITY_API_URL_ID, raadslid.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return raadslidRepository.count();
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

    protected Raadslid getPersistedRaadslid(Raadslid raadslid) {
        return raadslidRepository.findById(raadslid.getId()).orElseThrow();
    }

    protected void assertPersistedRaadslidToMatchAllProperties(Raadslid expectedRaadslid) {
        assertRaadslidAllPropertiesEquals(expectedRaadslid, getPersistedRaadslid(expectedRaadslid));
    }

    protected void assertPersistedRaadslidToMatchUpdatableProperties(Raadslid expectedRaadslid) {
        assertRaadslidAllUpdatablePropertiesEquals(expectedRaadslid, getPersistedRaadslid(expectedRaadslid));
    }
}
