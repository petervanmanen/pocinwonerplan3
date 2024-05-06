package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.InstructieregelAsserts.*;
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
import nl.ritense.demo.domain.Instructieregel;
import nl.ritense.demo.repository.InstructieregelRepository;
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
 * Integration tests for the {@link InstructieregelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InstructieregelResourceIT {

    private static final String DEFAULT_INSTRUCTIEREGELINSTRUMENT = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUCTIEREGELINSTRUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_INSTRUCTIEREGELTAAKUITOEFENING = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUCTIEREGELTAAKUITOEFENING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/instructieregels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InstructieregelRepository instructieregelRepository;

    @Mock
    private InstructieregelRepository instructieregelRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstructieregelMockMvc;

    private Instructieregel instructieregel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Instructieregel createEntity(EntityManager em) {
        Instructieregel instructieregel = new Instructieregel()
            .instructieregelinstrument(DEFAULT_INSTRUCTIEREGELINSTRUMENT)
            .instructieregeltaakuitoefening(DEFAULT_INSTRUCTIEREGELTAAKUITOEFENING);
        return instructieregel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Instructieregel createUpdatedEntity(EntityManager em) {
        Instructieregel instructieregel = new Instructieregel()
            .instructieregelinstrument(UPDATED_INSTRUCTIEREGELINSTRUMENT)
            .instructieregeltaakuitoefening(UPDATED_INSTRUCTIEREGELTAAKUITOEFENING);
        return instructieregel;
    }

    @BeforeEach
    public void initTest() {
        instructieregel = createEntity(em);
    }

    @Test
    @Transactional
    void createInstructieregel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Instructieregel
        var returnedInstructieregel = om.readValue(
            restInstructieregelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(instructieregel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Instructieregel.class
        );

        // Validate the Instructieregel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInstructieregelUpdatableFieldsEquals(returnedInstructieregel, getPersistedInstructieregel(returnedInstructieregel));
    }

    @Test
    @Transactional
    void createInstructieregelWithExistingId() throws Exception {
        // Create the Instructieregel with an existing ID
        instructieregel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstructieregelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(instructieregel)))
            .andExpect(status().isBadRequest());

        // Validate the Instructieregel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInstructieregels() throws Exception {
        // Initialize the database
        instructieregelRepository.saveAndFlush(instructieregel);

        // Get all the instructieregelList
        restInstructieregelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instructieregel.getId().intValue())))
            .andExpect(jsonPath("$.[*].instructieregelinstrument").value(hasItem(DEFAULT_INSTRUCTIEREGELINSTRUMENT)))
            .andExpect(jsonPath("$.[*].instructieregeltaakuitoefening").value(hasItem(DEFAULT_INSTRUCTIEREGELTAAKUITOEFENING)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInstructieregelsWithEagerRelationshipsIsEnabled() throws Exception {
        when(instructieregelRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInstructieregelMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(instructieregelRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInstructieregelsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(instructieregelRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInstructieregelMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(instructieregelRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getInstructieregel() throws Exception {
        // Initialize the database
        instructieregelRepository.saveAndFlush(instructieregel);

        // Get the instructieregel
        restInstructieregelMockMvc
            .perform(get(ENTITY_API_URL_ID, instructieregel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(instructieregel.getId().intValue()))
            .andExpect(jsonPath("$.instructieregelinstrument").value(DEFAULT_INSTRUCTIEREGELINSTRUMENT))
            .andExpect(jsonPath("$.instructieregeltaakuitoefening").value(DEFAULT_INSTRUCTIEREGELTAAKUITOEFENING));
    }

    @Test
    @Transactional
    void getNonExistingInstructieregel() throws Exception {
        // Get the instructieregel
        restInstructieregelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInstructieregel() throws Exception {
        // Initialize the database
        instructieregelRepository.saveAndFlush(instructieregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the instructieregel
        Instructieregel updatedInstructieregel = instructieregelRepository.findById(instructieregel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInstructieregel are not directly saved in db
        em.detach(updatedInstructieregel);
        updatedInstructieregel
            .instructieregelinstrument(UPDATED_INSTRUCTIEREGELINSTRUMENT)
            .instructieregeltaakuitoefening(UPDATED_INSTRUCTIEREGELTAAKUITOEFENING);

        restInstructieregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInstructieregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInstructieregel))
            )
            .andExpect(status().isOk());

        // Validate the Instructieregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInstructieregelToMatchAllProperties(updatedInstructieregel);
    }

    @Test
    @Transactional
    void putNonExistingInstructieregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instructieregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstructieregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, instructieregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(instructieregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Instructieregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInstructieregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instructieregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstructieregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(instructieregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Instructieregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInstructieregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instructieregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstructieregelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(instructieregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Instructieregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInstructieregelWithPatch() throws Exception {
        // Initialize the database
        instructieregelRepository.saveAndFlush(instructieregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the instructieregel using partial update
        Instructieregel partialUpdatedInstructieregel = new Instructieregel();
        partialUpdatedInstructieregel.setId(instructieregel.getId());

        partialUpdatedInstructieregel
            .instructieregelinstrument(UPDATED_INSTRUCTIEREGELINSTRUMENT)
            .instructieregeltaakuitoefening(UPDATED_INSTRUCTIEREGELTAAKUITOEFENING);

        restInstructieregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInstructieregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInstructieregel))
            )
            .andExpect(status().isOk());

        // Validate the Instructieregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInstructieregelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInstructieregel, instructieregel),
            getPersistedInstructieregel(instructieregel)
        );
    }

    @Test
    @Transactional
    void fullUpdateInstructieregelWithPatch() throws Exception {
        // Initialize the database
        instructieregelRepository.saveAndFlush(instructieregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the instructieregel using partial update
        Instructieregel partialUpdatedInstructieregel = new Instructieregel();
        partialUpdatedInstructieregel.setId(instructieregel.getId());

        partialUpdatedInstructieregel
            .instructieregelinstrument(UPDATED_INSTRUCTIEREGELINSTRUMENT)
            .instructieregeltaakuitoefening(UPDATED_INSTRUCTIEREGELTAAKUITOEFENING);

        restInstructieregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInstructieregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInstructieregel))
            )
            .andExpect(status().isOk());

        // Validate the Instructieregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInstructieregelUpdatableFieldsEquals(
            partialUpdatedInstructieregel,
            getPersistedInstructieregel(partialUpdatedInstructieregel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInstructieregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instructieregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstructieregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, instructieregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(instructieregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Instructieregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInstructieregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instructieregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstructieregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(instructieregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Instructieregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInstructieregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instructieregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstructieregelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(instructieregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Instructieregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInstructieregel() throws Exception {
        // Initialize the database
        instructieregelRepository.saveAndFlush(instructieregel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the instructieregel
        restInstructieregelMockMvc
            .perform(delete(ENTITY_API_URL_ID, instructieregel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return instructieregelRepository.count();
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

    protected Instructieregel getPersistedInstructieregel(Instructieregel instructieregel) {
        return instructieregelRepository.findById(instructieregel.getId()).orElseThrow();
    }

    protected void assertPersistedInstructieregelToMatchAllProperties(Instructieregel expectedInstructieregel) {
        assertInstructieregelAllPropertiesEquals(expectedInstructieregel, getPersistedInstructieregel(expectedInstructieregel));
    }

    protected void assertPersistedInstructieregelToMatchUpdatableProperties(Instructieregel expectedInstructieregel) {
        assertInstructieregelAllUpdatablePropertiesEquals(expectedInstructieregel, getPersistedInstructieregel(expectedInstructieregel));
    }
}
