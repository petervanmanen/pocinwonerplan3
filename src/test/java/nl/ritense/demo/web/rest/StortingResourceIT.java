package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.StortingAsserts.*;
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
import nl.ritense.demo.domain.Storting;
import nl.ritense.demo.repository.StortingRepository;
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
 * Integration tests for the {@link StortingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StortingResourceIT {

    private static final String DEFAULT_DATUMTIJD = "AAAAAAAAAA";
    private static final String UPDATED_DATUMTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_GEWICHT = "AAAAAAAAAA";
    private static final String UPDATED_GEWICHT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/stortings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StortingRepository stortingRepository;

    @Mock
    private StortingRepository stortingRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStortingMockMvc;

    private Storting storting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Storting createEntity(EntityManager em) {
        Storting storting = new Storting().datumtijd(DEFAULT_DATUMTIJD).gewicht(DEFAULT_GEWICHT);
        return storting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Storting createUpdatedEntity(EntityManager em) {
        Storting storting = new Storting().datumtijd(UPDATED_DATUMTIJD).gewicht(UPDATED_GEWICHT);
        return storting;
    }

    @BeforeEach
    public void initTest() {
        storting = createEntity(em);
    }

    @Test
    @Transactional
    void createStorting() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Storting
        var returnedStorting = om.readValue(
            restStortingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(storting)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Storting.class
        );

        // Validate the Storting in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStortingUpdatableFieldsEquals(returnedStorting, getPersistedStorting(returnedStorting));
    }

    @Test
    @Transactional
    void createStortingWithExistingId() throws Exception {
        // Create the Storting with an existing ID
        storting.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStortingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(storting)))
            .andExpect(status().isBadRequest());

        // Validate the Storting in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStortings() throws Exception {
        // Initialize the database
        stortingRepository.saveAndFlush(storting);

        // Get all the stortingList
        restStortingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storting.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumtijd").value(hasItem(DEFAULT_DATUMTIJD)))
            .andExpect(jsonPath("$.[*].gewicht").value(hasItem(DEFAULT_GEWICHT)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStortingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(stortingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStortingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(stortingRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStortingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(stortingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStortingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(stortingRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getStorting() throws Exception {
        // Initialize the database
        stortingRepository.saveAndFlush(storting);

        // Get the storting
        restStortingMockMvc
            .perform(get(ENTITY_API_URL_ID, storting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(storting.getId().intValue()))
            .andExpect(jsonPath("$.datumtijd").value(DEFAULT_DATUMTIJD))
            .andExpect(jsonPath("$.gewicht").value(DEFAULT_GEWICHT));
    }

    @Test
    @Transactional
    void getNonExistingStorting() throws Exception {
        // Get the storting
        restStortingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStorting() throws Exception {
        // Initialize the database
        stortingRepository.saveAndFlush(storting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the storting
        Storting updatedStorting = stortingRepository.findById(storting.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStorting are not directly saved in db
        em.detach(updatedStorting);
        updatedStorting.datumtijd(UPDATED_DATUMTIJD).gewicht(UPDATED_GEWICHT);

        restStortingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStorting.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStorting))
            )
            .andExpect(status().isOk());

        // Validate the Storting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStortingToMatchAllProperties(updatedStorting);
    }

    @Test
    @Transactional
    void putNonExistingStorting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        storting.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStortingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, storting.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(storting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Storting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStorting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        storting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStortingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(storting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Storting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStorting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        storting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStortingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(storting)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Storting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStortingWithPatch() throws Exception {
        // Initialize the database
        stortingRepository.saveAndFlush(storting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the storting using partial update
        Storting partialUpdatedStorting = new Storting();
        partialUpdatedStorting.setId(storting.getId());

        partialUpdatedStorting.gewicht(UPDATED_GEWICHT);

        restStortingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStorting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStorting))
            )
            .andExpect(status().isOk());

        // Validate the Storting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStortingUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedStorting, storting), getPersistedStorting(storting));
    }

    @Test
    @Transactional
    void fullUpdateStortingWithPatch() throws Exception {
        // Initialize the database
        stortingRepository.saveAndFlush(storting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the storting using partial update
        Storting partialUpdatedStorting = new Storting();
        partialUpdatedStorting.setId(storting.getId());

        partialUpdatedStorting.datumtijd(UPDATED_DATUMTIJD).gewicht(UPDATED_GEWICHT);

        restStortingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStorting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStorting))
            )
            .andExpect(status().isOk());

        // Validate the Storting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStortingUpdatableFieldsEquals(partialUpdatedStorting, getPersistedStorting(partialUpdatedStorting));
    }

    @Test
    @Transactional
    void patchNonExistingStorting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        storting.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStortingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, storting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(storting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Storting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStorting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        storting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStortingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(storting))
            )
            .andExpect(status().isBadRequest());

        // Validate the Storting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStorting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        storting.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStortingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(storting)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Storting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStorting() throws Exception {
        // Initialize the database
        stortingRepository.saveAndFlush(storting);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the storting
        restStortingMockMvc
            .perform(delete(ENTITY_API_URL_ID, storting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return stortingRepository.count();
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

    protected Storting getPersistedStorting(Storting storting) {
        return stortingRepository.findById(storting.getId()).orElseThrow();
    }

    protected void assertPersistedStortingToMatchAllProperties(Storting expectedStorting) {
        assertStortingAllPropertiesEquals(expectedStorting, getPersistedStorting(expectedStorting));
    }

    protected void assertPersistedStortingToMatchUpdatableProperties(Storting expectedStorting) {
        assertStortingAllUpdatablePropertiesEquals(expectedStorting, getPersistedStorting(expectedStorting));
    }
}
