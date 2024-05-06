package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ProgrammaAsserts.*;
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
import nl.ritense.demo.domain.Programma;
import nl.ritense.demo.repository.ProgrammaRepository;
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
 * Integration tests for the {@link ProgrammaResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProgrammaResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/programmas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProgrammaRepository programmaRepository;

    @Mock
    private ProgrammaRepository programmaRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgrammaMockMvc;

    private Programma programma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programma createEntity(EntityManager em) {
        Programma programma = new Programma().naam(DEFAULT_NAAM);
        return programma;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programma createUpdatedEntity(EntityManager em) {
        Programma programma = new Programma().naam(UPDATED_NAAM);
        return programma;
    }

    @BeforeEach
    public void initTest() {
        programma = createEntity(em);
    }

    @Test
    @Transactional
    void createProgramma() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Programma
        var returnedProgramma = om.readValue(
            restProgrammaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programma)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Programma.class
        );

        // Validate the Programma in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProgrammaUpdatableFieldsEquals(returnedProgramma, getPersistedProgramma(returnedProgramma));
    }

    @Test
    @Transactional
    void createProgrammaWithExistingId() throws Exception {
        // Create the Programma with an existing ID
        programma.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgrammaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programma)))
            .andExpect(status().isBadRequest());

        // Validate the Programma in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProgrammas() throws Exception {
        // Initialize the database
        programmaRepository.saveAndFlush(programma);

        // Get all the programmaList
        restProgrammaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programma.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProgrammasWithEagerRelationshipsIsEnabled() throws Exception {
        when(programmaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProgrammaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(programmaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProgrammasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(programmaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProgrammaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(programmaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProgramma() throws Exception {
        // Initialize the database
        programmaRepository.saveAndFlush(programma);

        // Get the programma
        restProgrammaMockMvc
            .perform(get(ENTITY_API_URL_ID, programma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(programma.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingProgramma() throws Exception {
        // Get the programma
        restProgrammaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProgramma() throws Exception {
        // Initialize the database
        programmaRepository.saveAndFlush(programma);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the programma
        Programma updatedProgramma = programmaRepository.findById(programma.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProgramma are not directly saved in db
        em.detach(updatedProgramma);
        updatedProgramma.naam(UPDATED_NAAM);

        restProgrammaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProgramma.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProgramma))
            )
            .andExpect(status().isOk());

        // Validate the Programma in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProgrammaToMatchAllProperties(updatedProgramma);
    }

    @Test
    @Transactional
    void putNonExistingProgramma() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programma.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgrammaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, programma.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programma))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programma in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProgramma() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programma.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgrammaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(programma))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programma in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProgramma() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programma.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgrammaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programma)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Programma in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProgrammaWithPatch() throws Exception {
        // Initialize the database
        programmaRepository.saveAndFlush(programma);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the programma using partial update
        Programma partialUpdatedProgramma = new Programma();
        partialUpdatedProgramma.setId(programma.getId());

        restProgrammaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramma.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProgramma))
            )
            .andExpect(status().isOk());

        // Validate the Programma in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProgrammaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProgramma, programma),
            getPersistedProgramma(programma)
        );
    }

    @Test
    @Transactional
    void fullUpdateProgrammaWithPatch() throws Exception {
        // Initialize the database
        programmaRepository.saveAndFlush(programma);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the programma using partial update
        Programma partialUpdatedProgramma = new Programma();
        partialUpdatedProgramma.setId(programma.getId());

        partialUpdatedProgramma.naam(UPDATED_NAAM);

        restProgrammaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramma.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProgramma))
            )
            .andExpect(status().isOk());

        // Validate the Programma in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProgrammaUpdatableFieldsEquals(partialUpdatedProgramma, getPersistedProgramma(partialUpdatedProgramma));
    }

    @Test
    @Transactional
    void patchNonExistingProgramma() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programma.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgrammaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, programma.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(programma))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programma in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProgramma() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programma.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgrammaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(programma))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programma in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProgramma() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programma.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgrammaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(programma)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Programma in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProgramma() throws Exception {
        // Initialize the database
        programmaRepository.saveAndFlush(programma);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the programma
        restProgrammaMockMvc
            .perform(delete(ENTITY_API_URL_ID, programma.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return programmaRepository.count();
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

    protected Programma getPersistedProgramma(Programma programma) {
        return programmaRepository.findById(programma.getId()).orElseThrow();
    }

    protected void assertPersistedProgrammaToMatchAllProperties(Programma expectedProgramma) {
        assertProgrammaAllPropertiesEquals(expectedProgramma, getPersistedProgramma(expectedProgramma));
    }

    protected void assertPersistedProgrammaToMatchUpdatableProperties(Programma expectedProgramma) {
        assertProgrammaAllUpdatablePropertiesEquals(expectedProgramma, getPersistedProgramma(expectedProgramma));
    }
}
