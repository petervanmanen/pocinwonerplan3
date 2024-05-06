package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NormwaardeAsserts.*;
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
import nl.ritense.demo.domain.Normwaarde;
import nl.ritense.demo.repository.NormwaardeRepository;
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
 * Integration tests for the {@link NormwaardeResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NormwaardeResourceIT {

    private static final String DEFAULT_KWALITATIEVEWAARDE = "AAAAAAAAAA";
    private static final String UPDATED_KWALITATIEVEWAARDE = "BBBBBBBBBB";

    private static final String DEFAULT_KWANTITATIEVEWAARDEEENHEID = "AAAAAAAAAA";
    private static final String UPDATED_KWANTITATIEVEWAARDEEENHEID = "BBBBBBBBBB";

    private static final String DEFAULT_KWANTITATIEVEWAARDEOMVANG = "AAAAAAAAAA";
    private static final String UPDATED_KWANTITATIEVEWAARDEOMVANG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/normwaardes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NormwaardeRepository normwaardeRepository;

    @Mock
    private NormwaardeRepository normwaardeRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNormwaardeMockMvc;

    private Normwaarde normwaarde;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Normwaarde createEntity(EntityManager em) {
        Normwaarde normwaarde = new Normwaarde()
            .kwalitatievewaarde(DEFAULT_KWALITATIEVEWAARDE)
            .kwantitatievewaardeeenheid(DEFAULT_KWANTITATIEVEWAARDEEENHEID)
            .kwantitatievewaardeomvang(DEFAULT_KWANTITATIEVEWAARDEOMVANG);
        return normwaarde;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Normwaarde createUpdatedEntity(EntityManager em) {
        Normwaarde normwaarde = new Normwaarde()
            .kwalitatievewaarde(UPDATED_KWALITATIEVEWAARDE)
            .kwantitatievewaardeeenheid(UPDATED_KWANTITATIEVEWAARDEEENHEID)
            .kwantitatievewaardeomvang(UPDATED_KWANTITATIEVEWAARDEOMVANG);
        return normwaarde;
    }

    @BeforeEach
    public void initTest() {
        normwaarde = createEntity(em);
    }

    @Test
    @Transactional
    void createNormwaarde() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Normwaarde
        var returnedNormwaarde = om.readValue(
            restNormwaardeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(normwaarde)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Normwaarde.class
        );

        // Validate the Normwaarde in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNormwaardeUpdatableFieldsEquals(returnedNormwaarde, getPersistedNormwaarde(returnedNormwaarde));
    }

    @Test
    @Transactional
    void createNormwaardeWithExistingId() throws Exception {
        // Create the Normwaarde with an existing ID
        normwaarde.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNormwaardeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(normwaarde)))
            .andExpect(status().isBadRequest());

        // Validate the Normwaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNormwaardes() throws Exception {
        // Initialize the database
        normwaardeRepository.saveAndFlush(normwaarde);

        // Get all the normwaardeList
        restNormwaardeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(normwaarde.getId().intValue())))
            .andExpect(jsonPath("$.[*].kwalitatievewaarde").value(hasItem(DEFAULT_KWALITATIEVEWAARDE)))
            .andExpect(jsonPath("$.[*].kwantitatievewaardeeenheid").value(hasItem(DEFAULT_KWANTITATIEVEWAARDEEENHEID)))
            .andExpect(jsonPath("$.[*].kwantitatievewaardeomvang").value(hasItem(DEFAULT_KWANTITATIEVEWAARDEOMVANG)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNormwaardesWithEagerRelationshipsIsEnabled() throws Exception {
        when(normwaardeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNormwaardeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(normwaardeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNormwaardesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(normwaardeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNormwaardeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(normwaardeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getNormwaarde() throws Exception {
        // Initialize the database
        normwaardeRepository.saveAndFlush(normwaarde);

        // Get the normwaarde
        restNormwaardeMockMvc
            .perform(get(ENTITY_API_URL_ID, normwaarde.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(normwaarde.getId().intValue()))
            .andExpect(jsonPath("$.kwalitatievewaarde").value(DEFAULT_KWALITATIEVEWAARDE))
            .andExpect(jsonPath("$.kwantitatievewaardeeenheid").value(DEFAULT_KWANTITATIEVEWAARDEEENHEID))
            .andExpect(jsonPath("$.kwantitatievewaardeomvang").value(DEFAULT_KWANTITATIEVEWAARDEOMVANG));
    }

    @Test
    @Transactional
    void getNonExistingNormwaarde() throws Exception {
        // Get the normwaarde
        restNormwaardeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNormwaarde() throws Exception {
        // Initialize the database
        normwaardeRepository.saveAndFlush(normwaarde);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the normwaarde
        Normwaarde updatedNormwaarde = normwaardeRepository.findById(normwaarde.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNormwaarde are not directly saved in db
        em.detach(updatedNormwaarde);
        updatedNormwaarde
            .kwalitatievewaarde(UPDATED_KWALITATIEVEWAARDE)
            .kwantitatievewaardeeenheid(UPDATED_KWANTITATIEVEWAARDEEENHEID)
            .kwantitatievewaardeomvang(UPDATED_KWANTITATIEVEWAARDEOMVANG);

        restNormwaardeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNormwaarde.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNormwaarde))
            )
            .andExpect(status().isOk());

        // Validate the Normwaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNormwaardeToMatchAllProperties(updatedNormwaarde);
    }

    @Test
    @Transactional
    void putNonExistingNormwaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        normwaarde.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNormwaardeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, normwaarde.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(normwaarde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Normwaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNormwaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        normwaarde.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNormwaardeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(normwaarde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Normwaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNormwaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        normwaarde.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNormwaardeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(normwaarde)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Normwaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNormwaardeWithPatch() throws Exception {
        // Initialize the database
        normwaardeRepository.saveAndFlush(normwaarde);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the normwaarde using partial update
        Normwaarde partialUpdatedNormwaarde = new Normwaarde();
        partialUpdatedNormwaarde.setId(normwaarde.getId());

        restNormwaardeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNormwaarde.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNormwaarde))
            )
            .andExpect(status().isOk());

        // Validate the Normwaarde in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNormwaardeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNormwaarde, normwaarde),
            getPersistedNormwaarde(normwaarde)
        );
    }

    @Test
    @Transactional
    void fullUpdateNormwaardeWithPatch() throws Exception {
        // Initialize the database
        normwaardeRepository.saveAndFlush(normwaarde);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the normwaarde using partial update
        Normwaarde partialUpdatedNormwaarde = new Normwaarde();
        partialUpdatedNormwaarde.setId(normwaarde.getId());

        partialUpdatedNormwaarde
            .kwalitatievewaarde(UPDATED_KWALITATIEVEWAARDE)
            .kwantitatievewaardeeenheid(UPDATED_KWANTITATIEVEWAARDEEENHEID)
            .kwantitatievewaardeomvang(UPDATED_KWANTITATIEVEWAARDEOMVANG);

        restNormwaardeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNormwaarde.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNormwaarde))
            )
            .andExpect(status().isOk());

        // Validate the Normwaarde in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNormwaardeUpdatableFieldsEquals(partialUpdatedNormwaarde, getPersistedNormwaarde(partialUpdatedNormwaarde));
    }

    @Test
    @Transactional
    void patchNonExistingNormwaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        normwaarde.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNormwaardeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, normwaarde.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(normwaarde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Normwaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNormwaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        normwaarde.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNormwaardeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(normwaarde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Normwaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNormwaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        normwaarde.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNormwaardeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(normwaarde)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Normwaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNormwaarde() throws Exception {
        // Initialize the database
        normwaardeRepository.saveAndFlush(normwaarde);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the normwaarde
        restNormwaardeMockMvc
            .perform(delete(ENTITY_API_URL_ID, normwaarde.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return normwaardeRepository.count();
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

    protected Normwaarde getPersistedNormwaarde(Normwaarde normwaarde) {
        return normwaardeRepository.findById(normwaarde.getId()).orElseThrow();
    }

    protected void assertPersistedNormwaardeToMatchAllProperties(Normwaarde expectedNormwaarde) {
        assertNormwaardeAllPropertiesEquals(expectedNormwaarde, getPersistedNormwaarde(expectedNormwaarde));
    }

    protected void assertPersistedNormwaardeToMatchUpdatableProperties(Normwaarde expectedNormwaarde) {
        assertNormwaardeAllUpdatablePropertiesEquals(expectedNormwaarde, getPersistedNormwaarde(expectedNormwaarde));
    }
}
