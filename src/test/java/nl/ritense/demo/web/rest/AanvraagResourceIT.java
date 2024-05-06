package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AanvraagAsserts.*;
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
import nl.ritense.demo.domain.Aanvraag;
import nl.ritense.demo.repository.AanvraagRepository;
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
 * Integration tests for the {@link AanvraagResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AanvraagResourceIT {

    private static final String DEFAULT_DATUMTIJD = "AAAAAAAAAA";
    private static final String UPDATED_DATUMTIJD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aanvraags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AanvraagRepository aanvraagRepository;

    @Mock
    private AanvraagRepository aanvraagRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAanvraagMockMvc;

    private Aanvraag aanvraag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanvraag createEntity(EntityManager em) {
        Aanvraag aanvraag = new Aanvraag().datumtijd(DEFAULT_DATUMTIJD);
        return aanvraag;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanvraag createUpdatedEntity(EntityManager em) {
        Aanvraag aanvraag = new Aanvraag().datumtijd(UPDATED_DATUMTIJD);
        return aanvraag;
    }

    @BeforeEach
    public void initTest() {
        aanvraag = createEntity(em);
    }

    @Test
    @Transactional
    void createAanvraag() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aanvraag
        var returnedAanvraag = om.readValue(
            restAanvraagMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraag)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aanvraag.class
        );

        // Validate the Aanvraag in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAanvraagUpdatableFieldsEquals(returnedAanvraag, getPersistedAanvraag(returnedAanvraag));
    }

    @Test
    @Transactional
    void createAanvraagWithExistingId() throws Exception {
        // Create the Aanvraag with an existing ID
        aanvraag.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAanvraagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraag)))
            .andExpect(status().isBadRequest());

        // Validate the Aanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAanvraags() throws Exception {
        // Initialize the database
        aanvraagRepository.saveAndFlush(aanvraag);

        // Get all the aanvraagList
        restAanvraagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aanvraag.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumtijd").value(hasItem(DEFAULT_DATUMTIJD)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAanvraagsWithEagerRelationshipsIsEnabled() throws Exception {
        when(aanvraagRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAanvraagMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(aanvraagRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAanvraagsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(aanvraagRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAanvraagMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(aanvraagRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAanvraag() throws Exception {
        // Initialize the database
        aanvraagRepository.saveAndFlush(aanvraag);

        // Get the aanvraag
        restAanvraagMockMvc
            .perform(get(ENTITY_API_URL_ID, aanvraag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aanvraag.getId().intValue()))
            .andExpect(jsonPath("$.datumtijd").value(DEFAULT_DATUMTIJD));
    }

    @Test
    @Transactional
    void getNonExistingAanvraag() throws Exception {
        // Get the aanvraag
        restAanvraagMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAanvraag() throws Exception {
        // Initialize the database
        aanvraagRepository.saveAndFlush(aanvraag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanvraag
        Aanvraag updatedAanvraag = aanvraagRepository.findById(aanvraag.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAanvraag are not directly saved in db
        em.detach(updatedAanvraag);
        updatedAanvraag.datumtijd(UPDATED_DATUMTIJD);

        restAanvraagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAanvraag.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAanvraag))
            )
            .andExpect(status().isOk());

        // Validate the Aanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAanvraagToMatchAllProperties(updatedAanvraag);
    }

    @Test
    @Transactional
    void putNonExistingAanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanvraagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aanvraag.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aanvraag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraagMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAanvraagWithPatch() throws Exception {
        // Initialize the database
        aanvraagRepository.saveAndFlush(aanvraag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanvraag using partial update
        Aanvraag partialUpdatedAanvraag = new Aanvraag();
        partialUpdatedAanvraag.setId(aanvraag.getId());

        restAanvraagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanvraag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanvraag))
            )
            .andExpect(status().isOk());

        // Validate the Aanvraag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanvraagUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAanvraag, aanvraag), getPersistedAanvraag(aanvraag));
    }

    @Test
    @Transactional
    void fullUpdateAanvraagWithPatch() throws Exception {
        // Initialize the database
        aanvraagRepository.saveAndFlush(aanvraag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanvraag using partial update
        Aanvraag partialUpdatedAanvraag = new Aanvraag();
        partialUpdatedAanvraag.setId(aanvraag.getId());

        partialUpdatedAanvraag.datumtijd(UPDATED_DATUMTIJD);

        restAanvraagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanvraag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanvraag))
            )
            .andExpect(status().isOk());

        // Validate the Aanvraag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanvraagUpdatableFieldsEquals(partialUpdatedAanvraag, getPersistedAanvraag(partialUpdatedAanvraag));
    }

    @Test
    @Transactional
    void patchNonExistingAanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanvraagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aanvraag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanvraag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanvraag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraagMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aanvraag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAanvraag() throws Exception {
        // Initialize the database
        aanvraagRepository.saveAndFlush(aanvraag);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aanvraag
        restAanvraagMockMvc
            .perform(delete(ENTITY_API_URL_ID, aanvraag.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aanvraagRepository.count();
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

    protected Aanvraag getPersistedAanvraag(Aanvraag aanvraag) {
        return aanvraagRepository.findById(aanvraag.getId()).orElseThrow();
    }

    protected void assertPersistedAanvraagToMatchAllProperties(Aanvraag expectedAanvraag) {
        assertAanvraagAllPropertiesEquals(expectedAanvraag, getPersistedAanvraag(expectedAanvraag));
    }

    protected void assertPersistedAanvraagToMatchUpdatableProperties(Aanvraag expectedAanvraag) {
        assertAanvraagAllUpdatablePropertiesEquals(expectedAanvraag, getPersistedAanvraag(expectedAanvraag));
    }
}
