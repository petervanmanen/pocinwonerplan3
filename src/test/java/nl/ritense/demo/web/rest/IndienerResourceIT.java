package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.IndienerAsserts.*;
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
import nl.ritense.demo.domain.Indiener;
import nl.ritense.demo.repository.IndienerRepository;
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
 * Integration tests for the {@link IndienerResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class IndienerResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/indieners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IndienerRepository indienerRepository;

    @Mock
    private IndienerRepository indienerRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndienerMockMvc;

    private Indiener indiener;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Indiener createEntity(EntityManager em) {
        Indiener indiener = new Indiener().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return indiener;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Indiener createUpdatedEntity(EntityManager em) {
        Indiener indiener = new Indiener().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return indiener;
    }

    @BeforeEach
    public void initTest() {
        indiener = createEntity(em);
    }

    @Test
    @Transactional
    void createIndiener() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Indiener
        var returnedIndiener = om.readValue(
            restIndienerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indiener)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Indiener.class
        );

        // Validate the Indiener in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertIndienerUpdatableFieldsEquals(returnedIndiener, getPersistedIndiener(returnedIndiener));
    }

    @Test
    @Transactional
    void createIndienerWithExistingId() throws Exception {
        // Create the Indiener with an existing ID
        indiener.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndienerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indiener)))
            .andExpect(status().isBadRequest());

        // Validate the Indiener in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIndieners() throws Exception {
        // Initialize the database
        indienerRepository.saveAndFlush(indiener);

        // Get all the indienerList
        restIndienerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indiener.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllIndienersWithEagerRelationshipsIsEnabled() throws Exception {
        when(indienerRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restIndienerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(indienerRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllIndienersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(indienerRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restIndienerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(indienerRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getIndiener() throws Exception {
        // Initialize the database
        indienerRepository.saveAndFlush(indiener);

        // Get the indiener
        restIndienerMockMvc
            .perform(get(ENTITY_API_URL_ID, indiener.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indiener.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingIndiener() throws Exception {
        // Get the indiener
        restIndienerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIndiener() throws Exception {
        // Initialize the database
        indienerRepository.saveAndFlush(indiener);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the indiener
        Indiener updatedIndiener = indienerRepository.findById(indiener.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIndiener are not directly saved in db
        em.detach(updatedIndiener);
        updatedIndiener.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restIndienerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIndiener.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedIndiener))
            )
            .andExpect(status().isOk());

        // Validate the Indiener in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIndienerToMatchAllProperties(updatedIndiener);
    }

    @Test
    @Transactional
    void putNonExistingIndiener() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indiener.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndienerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indiener.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indiener))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indiener in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndiener() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indiener.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndienerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(indiener))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indiener in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndiener() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indiener.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndienerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indiener)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Indiener in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndienerWithPatch() throws Exception {
        // Initialize the database
        indienerRepository.saveAndFlush(indiener);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the indiener using partial update
        Indiener partialUpdatedIndiener = new Indiener();
        partialUpdatedIndiener.setId(indiener.getId());

        partialUpdatedIndiener.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restIndienerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndiener.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIndiener))
            )
            .andExpect(status().isOk());

        // Validate the Indiener in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIndienerUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedIndiener, indiener), getPersistedIndiener(indiener));
    }

    @Test
    @Transactional
    void fullUpdateIndienerWithPatch() throws Exception {
        // Initialize the database
        indienerRepository.saveAndFlush(indiener);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the indiener using partial update
        Indiener partialUpdatedIndiener = new Indiener();
        partialUpdatedIndiener.setId(indiener.getId());

        partialUpdatedIndiener.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restIndienerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndiener.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIndiener))
            )
            .andExpect(status().isOk());

        // Validate the Indiener in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIndienerUpdatableFieldsEquals(partialUpdatedIndiener, getPersistedIndiener(partialUpdatedIndiener));
    }

    @Test
    @Transactional
    void patchNonExistingIndiener() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indiener.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndienerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indiener.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(indiener))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indiener in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndiener() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indiener.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndienerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(indiener))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indiener in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndiener() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indiener.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndienerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(indiener)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Indiener in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndiener() throws Exception {
        // Initialize the database
        indienerRepository.saveAndFlush(indiener);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the indiener
        restIndienerMockMvc
            .perform(delete(ENTITY_API_URL_ID, indiener.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return indienerRepository.count();
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

    protected Indiener getPersistedIndiener(Indiener indiener) {
        return indienerRepository.findById(indiener.getId()).orElseThrow();
    }

    protected void assertPersistedIndienerToMatchAllProperties(Indiener expectedIndiener) {
        assertIndienerAllPropertiesEquals(expectedIndiener, getPersistedIndiener(expectedIndiener));
    }

    protected void assertPersistedIndienerToMatchUpdatableProperties(Indiener expectedIndiener) {
        assertIndienerAllUpdatablePropertiesEquals(expectedIndiener, getPersistedIndiener(expectedIndiener));
    }
}
