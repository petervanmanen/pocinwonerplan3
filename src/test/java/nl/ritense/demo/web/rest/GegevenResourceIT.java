package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GegevenAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.UUID;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Gegeven;
import nl.ritense.demo.repository.GegevenRepository;
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
 * Integration tests for the {@link GegevenResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class GegevenResourceIT {

    private static final String DEFAULT_ALIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS = "BBBBBBBBBB";

    private static final String DEFAULT_EAGUID = "AAAAAAAAAA";
    private static final String UPDATED_EAGUID = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_STEREOTYPE = "AAAAAAAAAA";
    private static final String UPDATED_STEREOTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gegevens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GegevenRepository gegevenRepository;

    @Mock
    private GegevenRepository gegevenRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGegevenMockMvc;

    private Gegeven gegeven;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gegeven createEntity(EntityManager em) {
        Gegeven gegeven = new Gegeven()
            .alias(DEFAULT_ALIAS)
            .eaguid(DEFAULT_EAGUID)
            .naam(DEFAULT_NAAM)
            .stereotype(DEFAULT_STEREOTYPE)
            .toelichting(DEFAULT_TOELICHTING);
        return gegeven;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gegeven createUpdatedEntity(EntityManager em) {
        Gegeven gegeven = new Gegeven()
            .alias(UPDATED_ALIAS)
            .eaguid(UPDATED_EAGUID)
            .naam(UPDATED_NAAM)
            .stereotype(UPDATED_STEREOTYPE)
            .toelichting(UPDATED_TOELICHTING);
        return gegeven;
    }

    @BeforeEach
    public void initTest() {
        gegeven = createEntity(em);
    }

    @Test
    @Transactional
    void createGegeven() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Gegeven
        var returnedGegeven = om.readValue(
            restGegevenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gegeven)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Gegeven.class
        );

        // Validate the Gegeven in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGegevenUpdatableFieldsEquals(returnedGegeven, getPersistedGegeven(returnedGegeven));
    }

    @Test
    @Transactional
    void createGegevenWithExistingId() throws Exception {
        // Create the Gegeven with an existing ID
        gegeven.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGegevenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gegeven)))
            .andExpect(status().isBadRequest());

        // Validate the Gegeven in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGegevens() throws Exception {
        // Initialize the database
        gegevenRepository.saveAndFlush(gegeven);

        // Get all the gegevenList
        restGegevenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gegeven.getId())))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS)))
            .andExpect(jsonPath("$.[*].eaguid").value(hasItem(DEFAULT_EAGUID)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].stereotype").value(hasItem(DEFAULT_STEREOTYPE)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGegevensWithEagerRelationshipsIsEnabled() throws Exception {
        when(gegevenRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGegevenMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(gegevenRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGegevensWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(gegevenRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGegevenMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(gegevenRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getGegeven() throws Exception {
        // Initialize the database
        gegevenRepository.saveAndFlush(gegeven);

        // Get the gegeven
        restGegevenMockMvc
            .perform(get(ENTITY_API_URL_ID, gegeven.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gegeven.getId()))
            .andExpect(jsonPath("$.alias").value(DEFAULT_ALIAS))
            .andExpect(jsonPath("$.eaguid").value(DEFAULT_EAGUID))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.stereotype").value(DEFAULT_STEREOTYPE))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING));
    }

    @Test
    @Transactional
    void getNonExistingGegeven() throws Exception {
        // Get the gegeven
        restGegevenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGegeven() throws Exception {
        // Initialize the database
        gegevenRepository.saveAndFlush(gegeven);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gegeven
        Gegeven updatedGegeven = gegevenRepository.findById(gegeven.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGegeven are not directly saved in db
        em.detach(updatedGegeven);
        updatedGegeven
            .alias(UPDATED_ALIAS)
            .eaguid(UPDATED_EAGUID)
            .naam(UPDATED_NAAM)
            .stereotype(UPDATED_STEREOTYPE)
            .toelichting(UPDATED_TOELICHTING);

        restGegevenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGegeven.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGegeven))
            )
            .andExpect(status().isOk());

        // Validate the Gegeven in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGegevenToMatchAllProperties(updatedGegeven);
    }

    @Test
    @Transactional
    void putNonExistingGegeven() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gegeven.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGegevenMockMvc
            .perform(put(ENTITY_API_URL_ID, gegeven.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gegeven)))
            .andExpect(status().isBadRequest());

        // Validate the Gegeven in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGegeven() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gegeven.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGegevenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gegeven))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gegeven in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGegeven() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gegeven.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGegevenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gegeven)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gegeven in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGegevenWithPatch() throws Exception {
        // Initialize the database
        gegevenRepository.saveAndFlush(gegeven);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gegeven using partial update
        Gegeven partialUpdatedGegeven = new Gegeven();
        partialUpdatedGegeven.setId(gegeven.getId());

        partialUpdatedGegeven.alias(UPDATED_ALIAS).naam(UPDATED_NAAM).stereotype(UPDATED_STEREOTYPE);

        restGegevenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGegeven.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGegeven))
            )
            .andExpect(status().isOk());

        // Validate the Gegeven in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGegevenUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedGegeven, gegeven), getPersistedGegeven(gegeven));
    }

    @Test
    @Transactional
    void fullUpdateGegevenWithPatch() throws Exception {
        // Initialize the database
        gegevenRepository.saveAndFlush(gegeven);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gegeven using partial update
        Gegeven partialUpdatedGegeven = new Gegeven();
        partialUpdatedGegeven.setId(gegeven.getId());

        partialUpdatedGegeven
            .alias(UPDATED_ALIAS)
            .eaguid(UPDATED_EAGUID)
            .naam(UPDATED_NAAM)
            .stereotype(UPDATED_STEREOTYPE)
            .toelichting(UPDATED_TOELICHTING);

        restGegevenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGegeven.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGegeven))
            )
            .andExpect(status().isOk());

        // Validate the Gegeven in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGegevenUpdatableFieldsEquals(partialUpdatedGegeven, getPersistedGegeven(partialUpdatedGegeven));
    }

    @Test
    @Transactional
    void patchNonExistingGegeven() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gegeven.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGegevenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gegeven.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gegeven))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gegeven in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGegeven() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gegeven.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGegevenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gegeven))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gegeven in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGegeven() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gegeven.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGegevenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gegeven)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gegeven in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGegeven() throws Exception {
        // Initialize the database
        gegevenRepository.saveAndFlush(gegeven);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gegeven
        restGegevenMockMvc
            .perform(delete(ENTITY_API_URL_ID, gegeven.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gegevenRepository.count();
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

    protected Gegeven getPersistedGegeven(Gegeven gegeven) {
        return gegevenRepository.findById(gegeven.getId()).orElseThrow();
    }

    protected void assertPersistedGegevenToMatchAllProperties(Gegeven expectedGegeven) {
        assertGegevenAllPropertiesEquals(expectedGegeven, getPersistedGegeven(expectedGegeven));
    }

    protected void assertPersistedGegevenToMatchUpdatableProperties(Gegeven expectedGegeven) {
        assertGegevenAllUpdatablePropertiesEquals(expectedGegeven, getPersistedGegeven(expectedGegeven));
    }
}
