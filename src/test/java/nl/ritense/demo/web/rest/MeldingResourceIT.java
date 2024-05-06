package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MeldingAsserts.*;
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
import nl.ritense.demo.domain.Melding;
import nl.ritense.demo.repository.MeldingRepository;
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
 * Integration tests for the {@link MeldingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MeldingResourceIT {

    private static final String DEFAULT_VIERENTWINTIGUURS = "AAAAAAAAAA";
    private static final String UPDATED_VIERENTWINTIGUURS = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMTIJD = "AAAAAAAAAA";
    private static final String UPDATED_DATUMTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_ILLEGAAL = "AAAAAAAAAA";
    private static final String UPDATED_ILLEGAAL = "BBBBBBBBBB";

    private static final String DEFAULT_MELDINGNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_MELDINGNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/meldings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MeldingRepository meldingRepository;

    @Mock
    private MeldingRepository meldingRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMeldingMockMvc;

    private Melding melding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Melding createEntity(EntityManager em) {
        Melding melding = new Melding()
            .vierentwintiguurs(DEFAULT_VIERENTWINTIGUURS)
            .datumtijd(DEFAULT_DATUMTIJD)
            .illegaal(DEFAULT_ILLEGAAL)
            .meldingnummer(DEFAULT_MELDINGNUMMER)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return melding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Melding createUpdatedEntity(EntityManager em) {
        Melding melding = new Melding()
            .vierentwintiguurs(UPDATED_VIERENTWINTIGUURS)
            .datumtijd(UPDATED_DATUMTIJD)
            .illegaal(UPDATED_ILLEGAAL)
            .meldingnummer(UPDATED_MELDINGNUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return melding;
    }

    @BeforeEach
    public void initTest() {
        melding = createEntity(em);
    }

    @Test
    @Transactional
    void createMelding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Melding
        var returnedMelding = om.readValue(
            restMeldingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(melding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Melding.class
        );

        // Validate the Melding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMeldingUpdatableFieldsEquals(returnedMelding, getPersistedMelding(returnedMelding));
    }

    @Test
    @Transactional
    void createMeldingWithExistingId() throws Exception {
        // Create the Melding with an existing ID
        melding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeldingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(melding)))
            .andExpect(status().isBadRequest());

        // Validate the Melding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMeldings() throws Exception {
        // Initialize the database
        meldingRepository.saveAndFlush(melding);

        // Get all the meldingList
        restMeldingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(melding.getId().intValue())))
            .andExpect(jsonPath("$.[*].vierentwintiguurs").value(hasItem(DEFAULT_VIERENTWINTIGUURS)))
            .andExpect(jsonPath("$.[*].datumtijd").value(hasItem(DEFAULT_DATUMTIJD)))
            .andExpect(jsonPath("$.[*].illegaal").value(hasItem(DEFAULT_ILLEGAAL)))
            .andExpect(jsonPath("$.[*].meldingnummer").value(hasItem(DEFAULT_MELDINGNUMMER)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMeldingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(meldingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMeldingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(meldingRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMeldingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(meldingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMeldingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(meldingRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMelding() throws Exception {
        // Initialize the database
        meldingRepository.saveAndFlush(melding);

        // Get the melding
        restMeldingMockMvc
            .perform(get(ENTITY_API_URL_ID, melding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(melding.getId().intValue()))
            .andExpect(jsonPath("$.vierentwintiguurs").value(DEFAULT_VIERENTWINTIGUURS))
            .andExpect(jsonPath("$.datumtijd").value(DEFAULT_DATUMTIJD))
            .andExpect(jsonPath("$.illegaal").value(DEFAULT_ILLEGAAL))
            .andExpect(jsonPath("$.meldingnummer").value(DEFAULT_MELDINGNUMMER))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingMelding() throws Exception {
        // Get the melding
        restMeldingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMelding() throws Exception {
        // Initialize the database
        meldingRepository.saveAndFlush(melding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the melding
        Melding updatedMelding = meldingRepository.findById(melding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMelding are not directly saved in db
        em.detach(updatedMelding);
        updatedMelding
            .vierentwintiguurs(UPDATED_VIERENTWINTIGUURS)
            .datumtijd(UPDATED_DATUMTIJD)
            .illegaal(UPDATED_ILLEGAAL)
            .meldingnummer(UPDATED_MELDINGNUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restMeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMelding))
            )
            .andExpect(status().isOk());

        // Validate the Melding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMeldingToMatchAllProperties(updatedMelding);
    }

    @Test
    @Transactional
    void putNonExistingMelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        melding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeldingMockMvc
            .perform(put(ENTITY_API_URL_ID, melding.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(melding)))
            .andExpect(status().isBadRequest());

        // Validate the Melding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        melding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(melding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Melding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        melding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeldingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(melding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Melding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMeldingWithPatch() throws Exception {
        // Initialize the database
        meldingRepository.saveAndFlush(melding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the melding using partial update
        Melding partialUpdatedMelding = new Melding();
        partialUpdatedMelding.setId(melding.getId());

        partialUpdatedMelding.datumtijd(UPDATED_DATUMTIJD).illegaal(UPDATED_ILLEGAAL);

        restMeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMelding))
            )
            .andExpect(status().isOk());

        // Validate the Melding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMeldingUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMelding, melding), getPersistedMelding(melding));
    }

    @Test
    @Transactional
    void fullUpdateMeldingWithPatch() throws Exception {
        // Initialize the database
        meldingRepository.saveAndFlush(melding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the melding using partial update
        Melding partialUpdatedMelding = new Melding();
        partialUpdatedMelding.setId(melding.getId());

        partialUpdatedMelding
            .vierentwintiguurs(UPDATED_VIERENTWINTIGUURS)
            .datumtijd(UPDATED_DATUMTIJD)
            .illegaal(UPDATED_ILLEGAAL)
            .meldingnummer(UPDATED_MELDINGNUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restMeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMelding))
            )
            .andExpect(status().isOk());

        // Validate the Melding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMeldingUpdatableFieldsEquals(partialUpdatedMelding, getPersistedMelding(partialUpdatedMelding));
    }

    @Test
    @Transactional
    void patchNonExistingMelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        melding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, melding.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(melding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Melding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        melding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(melding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Melding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        melding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeldingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(melding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Melding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMelding() throws Exception {
        // Initialize the database
        meldingRepository.saveAndFlush(melding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the melding
        restMeldingMockMvc
            .perform(delete(ENTITY_API_URL_ID, melding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return meldingRepository.count();
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

    protected Melding getPersistedMelding(Melding melding) {
        return meldingRepository.findById(melding.getId()).orElseThrow();
    }

    protected void assertPersistedMeldingToMatchAllProperties(Melding expectedMelding) {
        assertMeldingAllPropertiesEquals(expectedMelding, getPersistedMelding(expectedMelding));
    }

    protected void assertPersistedMeldingToMatchUpdatableProperties(Melding expectedMelding) {
        assertMeldingAllUpdatablePropertiesEquals(expectedMelding, getPersistedMelding(expectedMelding));
    }
}
