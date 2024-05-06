package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.StremmingAsserts.*;
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
import nl.ritense.demo.domain.Stremming;
import nl.ritense.demo.repository.StremmingRepository;
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
 * Integration tests for the {@link StremmingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StremmingResourceIT {

    private static final String DEFAULT_AANTALGEHINDERDEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALGEHINDERDEN = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMAANMELDING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMAANMELDING = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMSTART = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSTART = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMWIJZIGING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMWIJZIGING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELENTOEGESTAAN = false;
    private static final Boolean UPDATED_DELENTOEGESTAAN = true;

    private static final Boolean DEFAULT_GESCHIKTVOORPUBLICATIE = false;
    private static final Boolean UPDATED_GESCHIKTVOORPUBLICATIE = true;

    private static final String DEFAULT_HINDERKLASSE = "AAAAAAAAAA";
    private static final String UPDATED_HINDERKLASSE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIE = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/stremmings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StremmingRepository stremmingRepository;

    @Mock
    private StremmingRepository stremmingRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStremmingMockMvc;

    private Stremming stremming;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stremming createEntity(EntityManager em) {
        Stremming stremming = new Stremming()
            .aantalgehinderden(DEFAULT_AANTALGEHINDERDEN)
            .datumaanmelding(DEFAULT_DATUMAANMELDING)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .datumwijziging(DEFAULT_DATUMWIJZIGING)
            .delentoegestaan(DEFAULT_DELENTOEGESTAAN)
            .geschiktvoorpublicatie(DEFAULT_GESCHIKTVOORPUBLICATIE)
            .hinderklasse(DEFAULT_HINDERKLASSE)
            .locatie(DEFAULT_LOCATIE)
            .naam(DEFAULT_NAAM)
            .status(DEFAULT_STATUS);
        return stremming;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stremming createUpdatedEntity(EntityManager em) {
        Stremming stremming = new Stremming()
            .aantalgehinderden(UPDATED_AANTALGEHINDERDEN)
            .datumaanmelding(UPDATED_DATUMAANMELDING)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumwijziging(UPDATED_DATUMWIJZIGING)
            .delentoegestaan(UPDATED_DELENTOEGESTAAN)
            .geschiktvoorpublicatie(UPDATED_GESCHIKTVOORPUBLICATIE)
            .hinderklasse(UPDATED_HINDERKLASSE)
            .locatie(UPDATED_LOCATIE)
            .naam(UPDATED_NAAM)
            .status(UPDATED_STATUS);
        return stremming;
    }

    @BeforeEach
    public void initTest() {
        stremming = createEntity(em);
    }

    @Test
    @Transactional
    void createStremming() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Stremming
        var returnedStremming = om.readValue(
            restStremmingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stremming)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Stremming.class
        );

        // Validate the Stremming in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStremmingUpdatableFieldsEquals(returnedStremming, getPersistedStremming(returnedStremming));
    }

    @Test
    @Transactional
    void createStremmingWithExistingId() throws Exception {
        // Create the Stremming with an existing ID
        stremming.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStremmingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stremming)))
            .andExpect(status().isBadRequest());

        // Validate the Stremming in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStremmings() throws Exception {
        // Initialize the database
        stremmingRepository.saveAndFlush(stremming);

        // Get all the stremmingList
        restStremmingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stremming.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantalgehinderden").value(hasItem(DEFAULT_AANTALGEHINDERDEN)))
            .andExpect(jsonPath("$.[*].datumaanmelding").value(hasItem(DEFAULT_DATUMAANMELDING)))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE)))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART)))
            .andExpect(jsonPath("$.[*].datumwijziging").value(hasItem(DEFAULT_DATUMWIJZIGING)))
            .andExpect(jsonPath("$.[*].delentoegestaan").value(hasItem(DEFAULT_DELENTOEGESTAAN.booleanValue())))
            .andExpect(jsonPath("$.[*].geschiktvoorpublicatie").value(hasItem(DEFAULT_GESCHIKTVOORPUBLICATIE.booleanValue())))
            .andExpect(jsonPath("$.[*].hinderklasse").value(hasItem(DEFAULT_HINDERKLASSE)))
            .andExpect(jsonPath("$.[*].locatie").value(hasItem(DEFAULT_LOCATIE)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStremmingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(stremmingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStremmingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(stremmingRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStremmingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(stremmingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStremmingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(stremmingRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getStremming() throws Exception {
        // Initialize the database
        stremmingRepository.saveAndFlush(stremming);

        // Get the stremming
        restStremmingMockMvc
            .perform(get(ENTITY_API_URL_ID, stremming.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stremming.getId().intValue()))
            .andExpect(jsonPath("$.aantalgehinderden").value(DEFAULT_AANTALGEHINDERDEN))
            .andExpect(jsonPath("$.datumaanmelding").value(DEFAULT_DATUMAANMELDING))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART))
            .andExpect(jsonPath("$.datumwijziging").value(DEFAULT_DATUMWIJZIGING))
            .andExpect(jsonPath("$.delentoegestaan").value(DEFAULT_DELENTOEGESTAAN.booleanValue()))
            .andExpect(jsonPath("$.geschiktvoorpublicatie").value(DEFAULT_GESCHIKTVOORPUBLICATIE.booleanValue()))
            .andExpect(jsonPath("$.hinderklasse").value(DEFAULT_HINDERKLASSE))
            .andExpect(jsonPath("$.locatie").value(DEFAULT_LOCATIE))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingStremming() throws Exception {
        // Get the stremming
        restStremmingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStremming() throws Exception {
        // Initialize the database
        stremmingRepository.saveAndFlush(stremming);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stremming
        Stremming updatedStremming = stremmingRepository.findById(stremming.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStremming are not directly saved in db
        em.detach(updatedStremming);
        updatedStremming
            .aantalgehinderden(UPDATED_AANTALGEHINDERDEN)
            .datumaanmelding(UPDATED_DATUMAANMELDING)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumwijziging(UPDATED_DATUMWIJZIGING)
            .delentoegestaan(UPDATED_DELENTOEGESTAAN)
            .geschiktvoorpublicatie(UPDATED_GESCHIKTVOORPUBLICATIE)
            .hinderklasse(UPDATED_HINDERKLASSE)
            .locatie(UPDATED_LOCATIE)
            .naam(UPDATED_NAAM)
            .status(UPDATED_STATUS);

        restStremmingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStremming.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStremming))
            )
            .andExpect(status().isOk());

        // Validate the Stremming in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStremmingToMatchAllProperties(updatedStremming);
    }

    @Test
    @Transactional
    void putNonExistingStremming() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stremming.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStremmingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stremming.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stremming))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stremming in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStremming() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stremming.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStremmingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(stremming))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stremming in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStremming() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stremming.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStremmingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stremming)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stremming in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStremmingWithPatch() throws Exception {
        // Initialize the database
        stremmingRepository.saveAndFlush(stremming);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stremming using partial update
        Stremming partialUpdatedStremming = new Stremming();
        partialUpdatedStremming.setId(stremming.getId());

        partialUpdatedStremming.datumwijziging(UPDATED_DATUMWIJZIGING).status(UPDATED_STATUS);

        restStremmingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStremming.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStremming))
            )
            .andExpect(status().isOk());

        // Validate the Stremming in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStremmingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStremming, stremming),
            getPersistedStremming(stremming)
        );
    }

    @Test
    @Transactional
    void fullUpdateStremmingWithPatch() throws Exception {
        // Initialize the database
        stremmingRepository.saveAndFlush(stremming);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stremming using partial update
        Stremming partialUpdatedStremming = new Stremming();
        partialUpdatedStremming.setId(stremming.getId());

        partialUpdatedStremming
            .aantalgehinderden(UPDATED_AANTALGEHINDERDEN)
            .datumaanmelding(UPDATED_DATUMAANMELDING)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumwijziging(UPDATED_DATUMWIJZIGING)
            .delentoegestaan(UPDATED_DELENTOEGESTAAN)
            .geschiktvoorpublicatie(UPDATED_GESCHIKTVOORPUBLICATIE)
            .hinderklasse(UPDATED_HINDERKLASSE)
            .locatie(UPDATED_LOCATIE)
            .naam(UPDATED_NAAM)
            .status(UPDATED_STATUS);

        restStremmingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStremming.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStremming))
            )
            .andExpect(status().isOk());

        // Validate the Stremming in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStremmingUpdatableFieldsEquals(partialUpdatedStremming, getPersistedStremming(partialUpdatedStremming));
    }

    @Test
    @Transactional
    void patchNonExistingStremming() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stremming.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStremmingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stremming.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(stremming))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stremming in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStremming() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stremming.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStremmingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(stremming))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stremming in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStremming() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stremming.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStremmingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(stremming)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stremming in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStremming() throws Exception {
        // Initialize the database
        stremmingRepository.saveAndFlush(stremming);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the stremming
        restStremmingMockMvc
            .perform(delete(ENTITY_API_URL_ID, stremming.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return stremmingRepository.count();
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

    protected Stremming getPersistedStremming(Stremming stremming) {
        return stremmingRepository.findById(stremming.getId()).orElseThrow();
    }

    protected void assertPersistedStremmingToMatchAllProperties(Stremming expectedStremming) {
        assertStremmingAllPropertiesEquals(expectedStremming, getPersistedStremming(expectedStremming));
    }

    protected void assertPersistedStremmingToMatchUpdatableProperties(Stremming expectedStremming) {
        assertStremmingAllUpdatablePropertiesEquals(expectedStremming, getPersistedStremming(expectedStremming));
    }
}
