package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BruikleenAsserts.*;
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
import nl.ritense.demo.domain.Bruikleen;
import nl.ritense.demo.repository.BruikleenRepository;
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
 * Integration tests for the {@link BruikleenResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BruikleenResourceIT {

    private static final String DEFAULT_AANVRAAGDOOR = "AAAAAAAAAA";
    private static final String UPDATED_AANVRAAGDOOR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMAANVRAAG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANVRAAG = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TOESTEMMINGDOOR = "AAAAAAAAAA";
    private static final String UPDATED_TOESTEMMINGDOOR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bruikleens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BruikleenRepository bruikleenRepository;

    @Mock
    private BruikleenRepository bruikleenRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBruikleenMockMvc;

    private Bruikleen bruikleen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bruikleen createEntity(EntityManager em) {
        Bruikleen bruikleen = new Bruikleen()
            .aanvraagdoor(DEFAULT_AANVRAAGDOOR)
            .datumaanvraag(DEFAULT_DATUMAANVRAAG)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .toestemmingdoor(DEFAULT_TOESTEMMINGDOOR);
        return bruikleen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bruikleen createUpdatedEntity(EntityManager em) {
        Bruikleen bruikleen = new Bruikleen()
            .aanvraagdoor(UPDATED_AANVRAAGDOOR)
            .datumaanvraag(UPDATED_DATUMAANVRAAG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .toestemmingdoor(UPDATED_TOESTEMMINGDOOR);
        return bruikleen;
    }

    @BeforeEach
    public void initTest() {
        bruikleen = createEntity(em);
    }

    @Test
    @Transactional
    void createBruikleen() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bruikleen
        var returnedBruikleen = om.readValue(
            restBruikleenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bruikleen)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bruikleen.class
        );

        // Validate the Bruikleen in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBruikleenUpdatableFieldsEquals(returnedBruikleen, getPersistedBruikleen(returnedBruikleen));
    }

    @Test
    @Transactional
    void createBruikleenWithExistingId() throws Exception {
        // Create the Bruikleen with an existing ID
        bruikleen.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBruikleenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bruikleen)))
            .andExpect(status().isBadRequest());

        // Validate the Bruikleen in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBruikleens() throws Exception {
        // Initialize the database
        bruikleenRepository.saveAndFlush(bruikleen);

        // Get all the bruikleenList
        restBruikleenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bruikleen.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanvraagdoor").value(hasItem(DEFAULT_AANVRAAGDOOR)))
            .andExpect(jsonPath("$.[*].datumaanvraag").value(hasItem(DEFAULT_DATUMAANVRAAG.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].toestemmingdoor").value(hasItem(DEFAULT_TOESTEMMINGDOOR)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBruikleensWithEagerRelationshipsIsEnabled() throws Exception {
        when(bruikleenRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBruikleenMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(bruikleenRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBruikleensWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(bruikleenRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBruikleenMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(bruikleenRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getBruikleen() throws Exception {
        // Initialize the database
        bruikleenRepository.saveAndFlush(bruikleen);

        // Get the bruikleen
        restBruikleenMockMvc
            .perform(get(ENTITY_API_URL_ID, bruikleen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bruikleen.getId().intValue()))
            .andExpect(jsonPath("$.aanvraagdoor").value(DEFAULT_AANVRAAGDOOR))
            .andExpect(jsonPath("$.datumaanvraag").value(DEFAULT_DATUMAANVRAAG.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.toestemmingdoor").value(DEFAULT_TOESTEMMINGDOOR));
    }

    @Test
    @Transactional
    void getNonExistingBruikleen() throws Exception {
        // Get the bruikleen
        restBruikleenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBruikleen() throws Exception {
        // Initialize the database
        bruikleenRepository.saveAndFlush(bruikleen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bruikleen
        Bruikleen updatedBruikleen = bruikleenRepository.findById(bruikleen.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBruikleen are not directly saved in db
        em.detach(updatedBruikleen);
        updatedBruikleen
            .aanvraagdoor(UPDATED_AANVRAAGDOOR)
            .datumaanvraag(UPDATED_DATUMAANVRAAG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .toestemmingdoor(UPDATED_TOESTEMMINGDOOR);

        restBruikleenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBruikleen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBruikleen))
            )
            .andExpect(status().isOk());

        // Validate the Bruikleen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBruikleenToMatchAllProperties(updatedBruikleen);
    }

    @Test
    @Transactional
    void putNonExistingBruikleen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bruikleen.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBruikleenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bruikleen.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bruikleen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bruikleen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBruikleen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bruikleen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBruikleenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bruikleen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bruikleen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBruikleen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bruikleen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBruikleenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bruikleen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bruikleen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBruikleenWithPatch() throws Exception {
        // Initialize the database
        bruikleenRepository.saveAndFlush(bruikleen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bruikleen using partial update
        Bruikleen partialUpdatedBruikleen = new Bruikleen();
        partialUpdatedBruikleen.setId(bruikleen.getId());

        partialUpdatedBruikleen
            .aanvraagdoor(UPDATED_AANVRAAGDOOR)
            .datumaanvraag(UPDATED_DATUMAANVRAAG)
            .datumeinde(UPDATED_DATUMEINDE)
            .toestemmingdoor(UPDATED_TOESTEMMINGDOOR);

        restBruikleenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBruikleen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBruikleen))
            )
            .andExpect(status().isOk());

        // Validate the Bruikleen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBruikleenUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBruikleen, bruikleen),
            getPersistedBruikleen(bruikleen)
        );
    }

    @Test
    @Transactional
    void fullUpdateBruikleenWithPatch() throws Exception {
        // Initialize the database
        bruikleenRepository.saveAndFlush(bruikleen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bruikleen using partial update
        Bruikleen partialUpdatedBruikleen = new Bruikleen();
        partialUpdatedBruikleen.setId(bruikleen.getId());

        partialUpdatedBruikleen
            .aanvraagdoor(UPDATED_AANVRAAGDOOR)
            .datumaanvraag(UPDATED_DATUMAANVRAAG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .toestemmingdoor(UPDATED_TOESTEMMINGDOOR);

        restBruikleenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBruikleen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBruikleen))
            )
            .andExpect(status().isOk());

        // Validate the Bruikleen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBruikleenUpdatableFieldsEquals(partialUpdatedBruikleen, getPersistedBruikleen(partialUpdatedBruikleen));
    }

    @Test
    @Transactional
    void patchNonExistingBruikleen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bruikleen.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBruikleenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bruikleen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bruikleen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bruikleen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBruikleen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bruikleen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBruikleenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bruikleen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bruikleen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBruikleen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bruikleen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBruikleenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bruikleen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bruikleen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBruikleen() throws Exception {
        // Initialize the database
        bruikleenRepository.saveAndFlush(bruikleen);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bruikleen
        restBruikleenMockMvc
            .perform(delete(ENTITY_API_URL_ID, bruikleen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bruikleenRepository.count();
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

    protected Bruikleen getPersistedBruikleen(Bruikleen bruikleen) {
        return bruikleenRepository.findById(bruikleen.getId()).orElseThrow();
    }

    protected void assertPersistedBruikleenToMatchAllProperties(Bruikleen expectedBruikleen) {
        assertBruikleenAllPropertiesEquals(expectedBruikleen, getPersistedBruikleen(expectedBruikleen));
    }

    protected void assertPersistedBruikleenToMatchUpdatableProperties(Bruikleen expectedBruikleen) {
        assertBruikleenAllUpdatablePropertiesEquals(expectedBruikleen, getPersistedBruikleen(expectedBruikleen));
    }
}
