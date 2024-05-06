package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BesluitAsserts.*;
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
import nl.ritense.demo.domain.Besluit;
import nl.ritense.demo.repository.BesluitRepository;
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
 * Integration tests for the {@link BesluitResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BesluitResourceIT {

    private static final String DEFAULT_BESLUIT = "AAAAAAAAAA";
    private static final String UPDATED_BESLUIT = "BBBBBBBBBB";

    private static final String DEFAULT_BESLUITIDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_BESLUITIDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_BESLUITTOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_BESLUITTOELICHTING = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMBESLUIT = "AAAAAAAAAA";
    private static final String UPDATED_DATUMBESLUIT = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMPUBLICATIE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMPUBLICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMSTART = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSTART = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMUITERLIJKEREACTIE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMUITERLIJKEREACTIE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMVERVAL = "AAAAAAAAAA";
    private static final String UPDATED_DATUMVERVAL = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMVERZENDING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMVERZENDING = "BBBBBBBBBB";

    private static final String DEFAULT_REDENVERVAL = "AAAAAAAAAA";
    private static final String UPDATED_REDENVERVAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/besluits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BesluitRepository besluitRepository;

    @Mock
    private BesluitRepository besluitRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBesluitMockMvc;

    private Besluit besluit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Besluit createEntity(EntityManager em) {
        Besluit besluit = new Besluit()
            .besluit(DEFAULT_BESLUIT)
            .besluitidentificatie(DEFAULT_BESLUITIDENTIFICATIE)
            .besluittoelichting(DEFAULT_BESLUITTOELICHTING)
            .datumbesluit(DEFAULT_DATUMBESLUIT)
            .datumpublicatie(DEFAULT_DATUMPUBLICATIE)
            .datumstart(DEFAULT_DATUMSTART)
            .datumuiterlijkereactie(DEFAULT_DATUMUITERLIJKEREACTIE)
            .datumverval(DEFAULT_DATUMVERVAL)
            .datumverzending(DEFAULT_DATUMVERZENDING)
            .redenverval(DEFAULT_REDENVERVAL);
        return besluit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Besluit createUpdatedEntity(EntityManager em) {
        Besluit besluit = new Besluit()
            .besluit(UPDATED_BESLUIT)
            .besluitidentificatie(UPDATED_BESLUITIDENTIFICATIE)
            .besluittoelichting(UPDATED_BESLUITTOELICHTING)
            .datumbesluit(UPDATED_DATUMBESLUIT)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumstart(UPDATED_DATUMSTART)
            .datumuiterlijkereactie(UPDATED_DATUMUITERLIJKEREACTIE)
            .datumverval(UPDATED_DATUMVERVAL)
            .datumverzending(UPDATED_DATUMVERZENDING)
            .redenverval(UPDATED_REDENVERVAL);
        return besluit;
    }

    @BeforeEach
    public void initTest() {
        besluit = createEntity(em);
    }

    @Test
    @Transactional
    void createBesluit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Besluit
        var returnedBesluit = om.readValue(
            restBesluitMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(besluit)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Besluit.class
        );

        // Validate the Besluit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBesluitUpdatableFieldsEquals(returnedBesluit, getPersistedBesluit(returnedBesluit));
    }

    @Test
    @Transactional
    void createBesluitWithExistingId() throws Exception {
        // Create the Besluit with an existing ID
        besluit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBesluitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(besluit)))
            .andExpect(status().isBadRequest());

        // Validate the Besluit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBesluits() throws Exception {
        // Initialize the database
        besluitRepository.saveAndFlush(besluit);

        // Get all the besluitList
        restBesluitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(besluit.getId().intValue())))
            .andExpect(jsonPath("$.[*].besluit").value(hasItem(DEFAULT_BESLUIT)))
            .andExpect(jsonPath("$.[*].besluitidentificatie").value(hasItem(DEFAULT_BESLUITIDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].besluittoelichting").value(hasItem(DEFAULT_BESLUITTOELICHTING)))
            .andExpect(jsonPath("$.[*].datumbesluit").value(hasItem(DEFAULT_DATUMBESLUIT)))
            .andExpect(jsonPath("$.[*].datumpublicatie").value(hasItem(DEFAULT_DATUMPUBLICATIE)))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART)))
            .andExpect(jsonPath("$.[*].datumuiterlijkereactie").value(hasItem(DEFAULT_DATUMUITERLIJKEREACTIE)))
            .andExpect(jsonPath("$.[*].datumverval").value(hasItem(DEFAULT_DATUMVERVAL)))
            .andExpect(jsonPath("$.[*].datumverzending").value(hasItem(DEFAULT_DATUMVERZENDING)))
            .andExpect(jsonPath("$.[*].redenverval").value(hasItem(DEFAULT_REDENVERVAL)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBesluitsWithEagerRelationshipsIsEnabled() throws Exception {
        when(besluitRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBesluitMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(besluitRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBesluitsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(besluitRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBesluitMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(besluitRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getBesluit() throws Exception {
        // Initialize the database
        besluitRepository.saveAndFlush(besluit);

        // Get the besluit
        restBesluitMockMvc
            .perform(get(ENTITY_API_URL_ID, besluit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(besluit.getId().intValue()))
            .andExpect(jsonPath("$.besluit").value(DEFAULT_BESLUIT))
            .andExpect(jsonPath("$.besluitidentificatie").value(DEFAULT_BESLUITIDENTIFICATIE))
            .andExpect(jsonPath("$.besluittoelichting").value(DEFAULT_BESLUITTOELICHTING))
            .andExpect(jsonPath("$.datumbesluit").value(DEFAULT_DATUMBESLUIT))
            .andExpect(jsonPath("$.datumpublicatie").value(DEFAULT_DATUMPUBLICATIE))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART))
            .andExpect(jsonPath("$.datumuiterlijkereactie").value(DEFAULT_DATUMUITERLIJKEREACTIE))
            .andExpect(jsonPath("$.datumverval").value(DEFAULT_DATUMVERVAL))
            .andExpect(jsonPath("$.datumverzending").value(DEFAULT_DATUMVERZENDING))
            .andExpect(jsonPath("$.redenverval").value(DEFAULT_REDENVERVAL));
    }

    @Test
    @Transactional
    void getNonExistingBesluit() throws Exception {
        // Get the besluit
        restBesluitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBesluit() throws Exception {
        // Initialize the database
        besluitRepository.saveAndFlush(besluit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the besluit
        Besluit updatedBesluit = besluitRepository.findById(besluit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBesluit are not directly saved in db
        em.detach(updatedBesluit);
        updatedBesluit
            .besluit(UPDATED_BESLUIT)
            .besluitidentificatie(UPDATED_BESLUITIDENTIFICATIE)
            .besluittoelichting(UPDATED_BESLUITTOELICHTING)
            .datumbesluit(UPDATED_DATUMBESLUIT)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumstart(UPDATED_DATUMSTART)
            .datumuiterlijkereactie(UPDATED_DATUMUITERLIJKEREACTIE)
            .datumverval(UPDATED_DATUMVERVAL)
            .datumverzending(UPDATED_DATUMVERZENDING)
            .redenverval(UPDATED_REDENVERVAL);

        restBesluitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBesluit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBesluit))
            )
            .andExpect(status().isOk());

        // Validate the Besluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBesluitToMatchAllProperties(updatedBesluit);
    }

    @Test
    @Transactional
    void putNonExistingBesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        besluit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBesluitMockMvc
            .perform(put(ENTITY_API_URL_ID, besluit.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(besluit)))
            .andExpect(status().isBadRequest());

        // Validate the Besluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        besluit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBesluitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(besluit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Besluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        besluit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBesluitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(besluit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Besluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBesluitWithPatch() throws Exception {
        // Initialize the database
        besluitRepository.saveAndFlush(besluit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the besluit using partial update
        Besluit partialUpdatedBesluit = new Besluit();
        partialUpdatedBesluit.setId(besluit.getId());

        partialUpdatedBesluit
            .datumbesluit(UPDATED_DATUMBESLUIT)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumstart(UPDATED_DATUMSTART)
            .datumverval(UPDATED_DATUMVERVAL)
            .datumverzending(UPDATED_DATUMVERZENDING)
            .redenverval(UPDATED_REDENVERVAL);

        restBesluitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBesluit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBesluit))
            )
            .andExpect(status().isOk());

        // Validate the Besluit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBesluitUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBesluit, besluit), getPersistedBesluit(besluit));
    }

    @Test
    @Transactional
    void fullUpdateBesluitWithPatch() throws Exception {
        // Initialize the database
        besluitRepository.saveAndFlush(besluit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the besluit using partial update
        Besluit partialUpdatedBesluit = new Besluit();
        partialUpdatedBesluit.setId(besluit.getId());

        partialUpdatedBesluit
            .besluit(UPDATED_BESLUIT)
            .besluitidentificatie(UPDATED_BESLUITIDENTIFICATIE)
            .besluittoelichting(UPDATED_BESLUITTOELICHTING)
            .datumbesluit(UPDATED_DATUMBESLUIT)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumstart(UPDATED_DATUMSTART)
            .datumuiterlijkereactie(UPDATED_DATUMUITERLIJKEREACTIE)
            .datumverval(UPDATED_DATUMVERVAL)
            .datumverzending(UPDATED_DATUMVERZENDING)
            .redenverval(UPDATED_REDENVERVAL);

        restBesluitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBesluit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBesluit))
            )
            .andExpect(status().isOk());

        // Validate the Besluit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBesluitUpdatableFieldsEquals(partialUpdatedBesluit, getPersistedBesluit(partialUpdatedBesluit));
    }

    @Test
    @Transactional
    void patchNonExistingBesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        besluit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBesluitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, besluit.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(besluit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Besluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        besluit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBesluitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(besluit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Besluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBesluit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        besluit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBesluitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(besluit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Besluit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBesluit() throws Exception {
        // Initialize the database
        besluitRepository.saveAndFlush(besluit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the besluit
        restBesluitMockMvc
            .perform(delete(ENTITY_API_URL_ID, besluit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return besluitRepository.count();
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

    protected Besluit getPersistedBesluit(Besluit besluit) {
        return besluitRepository.findById(besluit.getId()).orElseThrow();
    }

    protected void assertPersistedBesluitToMatchAllProperties(Besluit expectedBesluit) {
        assertBesluitAllPropertiesEquals(expectedBesluit, getPersistedBesluit(expectedBesluit));
    }

    protected void assertPersistedBesluitToMatchUpdatableProperties(Besluit expectedBesluit) {
        assertBesluitAllUpdatablePropertiesEquals(expectedBesluit, getPersistedBesluit(expectedBesluit));
    }
}
