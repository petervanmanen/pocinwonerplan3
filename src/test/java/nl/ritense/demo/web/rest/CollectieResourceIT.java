package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.CollectieAsserts.*;
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
import nl.ritense.demo.domain.Collectie;
import nl.ritense.demo.repository.CollectieRepository;
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
 * Integration tests for the {@link CollectieResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CollectieResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/collecties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CollectieRepository collectieRepository;

    @Mock
    private CollectieRepository collectieRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCollectieMockMvc;

    private Collectie collectie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collectie createEntity(EntityManager em) {
        Collectie collectie = new Collectie().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return collectie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collectie createUpdatedEntity(EntityManager em) {
        Collectie collectie = new Collectie().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return collectie;
    }

    @BeforeEach
    public void initTest() {
        collectie = createEntity(em);
    }

    @Test
    @Transactional
    void createCollectie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Collectie
        var returnedCollectie = om.readValue(
            restCollectieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(collectie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Collectie.class
        );

        // Validate the Collectie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCollectieUpdatableFieldsEquals(returnedCollectie, getPersistedCollectie(returnedCollectie));
    }

    @Test
    @Transactional
    void createCollectieWithExistingId() throws Exception {
        // Create the Collectie with an existing ID
        collectie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollectieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(collectie)))
            .andExpect(status().isBadRequest());

        // Validate the Collectie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCollecties() throws Exception {
        // Initialize the database
        collectieRepository.saveAndFlush(collectie);

        // Get all the collectieList
        restCollectieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collectie.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCollectiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(collectieRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCollectieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(collectieRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCollectiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(collectieRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCollectieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(collectieRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCollectie() throws Exception {
        // Initialize the database
        collectieRepository.saveAndFlush(collectie);

        // Get the collectie
        restCollectieMockMvc
            .perform(get(ENTITY_API_URL_ID, collectie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collectie.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingCollectie() throws Exception {
        // Get the collectie
        restCollectieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCollectie() throws Exception {
        // Initialize the database
        collectieRepository.saveAndFlush(collectie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the collectie
        Collectie updatedCollectie = collectieRepository.findById(collectie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCollectie are not directly saved in db
        em.detach(updatedCollectie);
        updatedCollectie.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restCollectieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCollectie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCollectie))
            )
            .andExpect(status().isOk());

        // Validate the Collectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCollectieToMatchAllProperties(updatedCollectie);
    }

    @Test
    @Transactional
    void putNonExistingCollectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collectie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollectieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collectie.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(collectie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCollectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collectie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(collectie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCollectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collectie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(collectie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Collectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCollectieWithPatch() throws Exception {
        // Initialize the database
        collectieRepository.saveAndFlush(collectie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the collectie using partial update
        Collectie partialUpdatedCollectie = new Collectie();
        partialUpdatedCollectie.setId(collectie.getId());

        partialUpdatedCollectie.naam(UPDATED_NAAM);

        restCollectieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollectie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCollectie))
            )
            .andExpect(status().isOk());

        // Validate the Collectie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCollectieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCollectie, collectie),
            getPersistedCollectie(collectie)
        );
    }

    @Test
    @Transactional
    void fullUpdateCollectieWithPatch() throws Exception {
        // Initialize the database
        collectieRepository.saveAndFlush(collectie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the collectie using partial update
        Collectie partialUpdatedCollectie = new Collectie();
        partialUpdatedCollectie.setId(collectie.getId());

        partialUpdatedCollectie.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restCollectieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollectie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCollectie))
            )
            .andExpect(status().isOk());

        // Validate the Collectie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCollectieUpdatableFieldsEquals(partialUpdatedCollectie, getPersistedCollectie(partialUpdatedCollectie));
    }

    @Test
    @Transactional
    void patchNonExistingCollectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collectie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollectieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, collectie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(collectie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCollectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collectie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(collectie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCollectie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collectie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(collectie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Collectie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCollectie() throws Exception {
        // Initialize the database
        collectieRepository.saveAndFlush(collectie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the collectie
        restCollectieMockMvc
            .perform(delete(ENTITY_API_URL_ID, collectie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return collectieRepository.count();
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

    protected Collectie getPersistedCollectie(Collectie collectie) {
        return collectieRepository.findById(collectie.getId()).orElseThrow();
    }

    protected void assertPersistedCollectieToMatchAllProperties(Collectie expectedCollectie) {
        assertCollectieAllPropertiesEquals(expectedCollectie, getPersistedCollectie(expectedCollectie));
    }

    protected void assertPersistedCollectieToMatchUpdatableProperties(Collectie expectedCollectie) {
        assertCollectieAllUpdatablePropertiesEquals(expectedCollectie, getPersistedCollectie(expectedCollectie));
    }
}
