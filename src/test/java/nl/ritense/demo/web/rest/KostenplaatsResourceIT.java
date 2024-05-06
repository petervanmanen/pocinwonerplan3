package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KostenplaatsAsserts.*;
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
import nl.ritense.demo.domain.Kostenplaats;
import nl.ritense.demo.repository.KostenplaatsRepository;
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
 * Integration tests for the {@link KostenplaatsResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class KostenplaatsResourceIT {

    private static final String DEFAULT_BTWCODE = "AAAAAAAAAA";
    private static final String UPDATED_BTWCODE = "BBBBBBBBBB";

    private static final String DEFAULT_BTWOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_BTWOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_KOSTENPLAATSSOORTCODE = "AAAAAAAAAA";
    private static final String UPDATED_KOSTENPLAATSSOORTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_KOSTENPLAATSSOORTOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_KOSTENPLAATSSOORTOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_KOSTENPLAATSTYPECODE = "AAAAAAAAAA";
    private static final String UPDATED_KOSTENPLAATSTYPECODE = "BBBBBBBBBB";

    private static final String DEFAULT_KOSTENPLAATSTYPEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_KOSTENPLAATSTYPEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kostenplaats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KostenplaatsRepository kostenplaatsRepository;

    @Mock
    private KostenplaatsRepository kostenplaatsRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKostenplaatsMockMvc;

    private Kostenplaats kostenplaats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kostenplaats createEntity(EntityManager em) {
        Kostenplaats kostenplaats = new Kostenplaats()
            .btwcode(DEFAULT_BTWCODE)
            .btwomschrijving(DEFAULT_BTWOMSCHRIJVING)
            .kostenplaatssoortcode(DEFAULT_KOSTENPLAATSSOORTCODE)
            .kostenplaatssoortomschrijving(DEFAULT_KOSTENPLAATSSOORTOMSCHRIJVING)
            .kostenplaatstypecode(DEFAULT_KOSTENPLAATSTYPECODE)
            .kostenplaatstypeomschrijving(DEFAULT_KOSTENPLAATSTYPEOMSCHRIJVING)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return kostenplaats;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kostenplaats createUpdatedEntity(EntityManager em) {
        Kostenplaats kostenplaats = new Kostenplaats()
            .btwcode(UPDATED_BTWCODE)
            .btwomschrijving(UPDATED_BTWOMSCHRIJVING)
            .kostenplaatssoortcode(UPDATED_KOSTENPLAATSSOORTCODE)
            .kostenplaatssoortomschrijving(UPDATED_KOSTENPLAATSSOORTOMSCHRIJVING)
            .kostenplaatstypecode(UPDATED_KOSTENPLAATSTYPECODE)
            .kostenplaatstypeomschrijving(UPDATED_KOSTENPLAATSTYPEOMSCHRIJVING)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return kostenplaats;
    }

    @BeforeEach
    public void initTest() {
        kostenplaats = createEntity(em);
    }

    @Test
    @Transactional
    void createKostenplaats() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kostenplaats
        var returnedKostenplaats = om.readValue(
            restKostenplaatsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kostenplaats)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kostenplaats.class
        );

        // Validate the Kostenplaats in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKostenplaatsUpdatableFieldsEquals(returnedKostenplaats, getPersistedKostenplaats(returnedKostenplaats));
    }

    @Test
    @Transactional
    void createKostenplaatsWithExistingId() throws Exception {
        // Create the Kostenplaats with an existing ID
        kostenplaats.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKostenplaatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kostenplaats)))
            .andExpect(status().isBadRequest());

        // Validate the Kostenplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKostenplaats() throws Exception {
        // Initialize the database
        kostenplaatsRepository.saveAndFlush(kostenplaats);

        // Get all the kostenplaatsList
        restKostenplaatsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kostenplaats.getId().intValue())))
            .andExpect(jsonPath("$.[*].btwcode").value(hasItem(DEFAULT_BTWCODE)))
            .andExpect(jsonPath("$.[*].btwomschrijving").value(hasItem(DEFAULT_BTWOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].kostenplaatssoortcode").value(hasItem(DEFAULT_KOSTENPLAATSSOORTCODE)))
            .andExpect(jsonPath("$.[*].kostenplaatssoortomschrijving").value(hasItem(DEFAULT_KOSTENPLAATSSOORTOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].kostenplaatstypecode").value(hasItem(DEFAULT_KOSTENPLAATSTYPECODE)))
            .andExpect(jsonPath("$.[*].kostenplaatstypeomschrijving").value(hasItem(DEFAULT_KOSTENPLAATSTYPEOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllKostenplaatsWithEagerRelationshipsIsEnabled() throws Exception {
        when(kostenplaatsRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restKostenplaatsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(kostenplaatsRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllKostenplaatsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(kostenplaatsRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restKostenplaatsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(kostenplaatsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getKostenplaats() throws Exception {
        // Initialize the database
        kostenplaatsRepository.saveAndFlush(kostenplaats);

        // Get the kostenplaats
        restKostenplaatsMockMvc
            .perform(get(ENTITY_API_URL_ID, kostenplaats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kostenplaats.getId().intValue()))
            .andExpect(jsonPath("$.btwcode").value(DEFAULT_BTWCODE))
            .andExpect(jsonPath("$.btwomschrijving").value(DEFAULT_BTWOMSCHRIJVING))
            .andExpect(jsonPath("$.kostenplaatssoortcode").value(DEFAULT_KOSTENPLAATSSOORTCODE))
            .andExpect(jsonPath("$.kostenplaatssoortomschrijving").value(DEFAULT_KOSTENPLAATSSOORTOMSCHRIJVING))
            .andExpect(jsonPath("$.kostenplaatstypecode").value(DEFAULT_KOSTENPLAATSTYPECODE))
            .andExpect(jsonPath("$.kostenplaatstypeomschrijving").value(DEFAULT_KOSTENPLAATSTYPEOMSCHRIJVING))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingKostenplaats() throws Exception {
        // Get the kostenplaats
        restKostenplaatsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKostenplaats() throws Exception {
        // Initialize the database
        kostenplaatsRepository.saveAndFlush(kostenplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kostenplaats
        Kostenplaats updatedKostenplaats = kostenplaatsRepository.findById(kostenplaats.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKostenplaats are not directly saved in db
        em.detach(updatedKostenplaats);
        updatedKostenplaats
            .btwcode(UPDATED_BTWCODE)
            .btwomschrijving(UPDATED_BTWOMSCHRIJVING)
            .kostenplaatssoortcode(UPDATED_KOSTENPLAATSSOORTCODE)
            .kostenplaatssoortomschrijving(UPDATED_KOSTENPLAATSSOORTOMSCHRIJVING)
            .kostenplaatstypecode(UPDATED_KOSTENPLAATSTYPECODE)
            .kostenplaatstypeomschrijving(UPDATED_KOSTENPLAATSTYPEOMSCHRIJVING)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restKostenplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKostenplaats.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKostenplaats))
            )
            .andExpect(status().isOk());

        // Validate the Kostenplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKostenplaatsToMatchAllProperties(updatedKostenplaats);
    }

    @Test
    @Transactional
    void putNonExistingKostenplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kostenplaats.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKostenplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kostenplaats.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kostenplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kostenplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKostenplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kostenplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKostenplaatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kostenplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kostenplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKostenplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kostenplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKostenplaatsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kostenplaats)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kostenplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKostenplaatsWithPatch() throws Exception {
        // Initialize the database
        kostenplaatsRepository.saveAndFlush(kostenplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kostenplaats using partial update
        Kostenplaats partialUpdatedKostenplaats = new Kostenplaats();
        partialUpdatedKostenplaats.setId(kostenplaats.getId());

        partialUpdatedKostenplaats
            .kostenplaatssoortcode(UPDATED_KOSTENPLAATSSOORTCODE)
            .kostenplaatstypecode(UPDATED_KOSTENPLAATSTYPECODE)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restKostenplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKostenplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKostenplaats))
            )
            .andExpect(status().isOk());

        // Validate the Kostenplaats in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKostenplaatsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKostenplaats, kostenplaats),
            getPersistedKostenplaats(kostenplaats)
        );
    }

    @Test
    @Transactional
    void fullUpdateKostenplaatsWithPatch() throws Exception {
        // Initialize the database
        kostenplaatsRepository.saveAndFlush(kostenplaats);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kostenplaats using partial update
        Kostenplaats partialUpdatedKostenplaats = new Kostenplaats();
        partialUpdatedKostenplaats.setId(kostenplaats.getId());

        partialUpdatedKostenplaats
            .btwcode(UPDATED_BTWCODE)
            .btwomschrijving(UPDATED_BTWOMSCHRIJVING)
            .kostenplaatssoortcode(UPDATED_KOSTENPLAATSSOORTCODE)
            .kostenplaatssoortomschrijving(UPDATED_KOSTENPLAATSSOORTOMSCHRIJVING)
            .kostenplaatstypecode(UPDATED_KOSTENPLAATSTYPECODE)
            .kostenplaatstypeomschrijving(UPDATED_KOSTENPLAATSTYPEOMSCHRIJVING)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restKostenplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKostenplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKostenplaats))
            )
            .andExpect(status().isOk());

        // Validate the Kostenplaats in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKostenplaatsUpdatableFieldsEquals(partialUpdatedKostenplaats, getPersistedKostenplaats(partialUpdatedKostenplaats));
    }

    @Test
    @Transactional
    void patchNonExistingKostenplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kostenplaats.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKostenplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kostenplaats.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kostenplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kostenplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKostenplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kostenplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKostenplaatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kostenplaats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kostenplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKostenplaats() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kostenplaats.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKostenplaatsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kostenplaats)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kostenplaats in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKostenplaats() throws Exception {
        // Initialize the database
        kostenplaatsRepository.saveAndFlush(kostenplaats);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kostenplaats
        restKostenplaatsMockMvc
            .perform(delete(ENTITY_API_URL_ID, kostenplaats.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kostenplaatsRepository.count();
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

    protected Kostenplaats getPersistedKostenplaats(Kostenplaats kostenplaats) {
        return kostenplaatsRepository.findById(kostenplaats.getId()).orElseThrow();
    }

    protected void assertPersistedKostenplaatsToMatchAllProperties(Kostenplaats expectedKostenplaats) {
        assertKostenplaatsAllPropertiesEquals(expectedKostenplaats, getPersistedKostenplaats(expectedKostenplaats));
    }

    protected void assertPersistedKostenplaatsToMatchUpdatableProperties(Kostenplaats expectedKostenplaats) {
        assertKostenplaatsAllUpdatablePropertiesEquals(expectedKostenplaats, getPersistedKostenplaats(expectedKostenplaats));
    }
}
