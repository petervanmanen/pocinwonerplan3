package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SollicitatiegesprekAsserts.*;
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
import nl.ritense.demo.domain.Sollicitatiegesprek;
import nl.ritense.demo.repository.SollicitatiegesprekRepository;
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
 * Integration tests for the {@link SollicitatiegesprekResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SollicitatiegesprekResourceIT {

    private static final Boolean DEFAULT_AANGENOMEN = false;
    private static final Boolean UPDATED_AANGENOMEN = true;

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OPMERKINGEN = "AAAAAAAAAA";
    private static final String UPDATED_OPMERKINGEN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VOLGENDGESPREK = false;
    private static final Boolean UPDATED_VOLGENDGESPREK = true;

    private static final String ENTITY_API_URL = "/api/sollicitatiegespreks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SollicitatiegesprekRepository sollicitatiegesprekRepository;

    @Mock
    private SollicitatiegesprekRepository sollicitatiegesprekRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSollicitatiegesprekMockMvc;

    private Sollicitatiegesprek sollicitatiegesprek;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sollicitatiegesprek createEntity(EntityManager em) {
        Sollicitatiegesprek sollicitatiegesprek = new Sollicitatiegesprek()
            .aangenomen(DEFAULT_AANGENOMEN)
            .datum(DEFAULT_DATUM)
            .opmerkingen(DEFAULT_OPMERKINGEN)
            .volgendgesprek(DEFAULT_VOLGENDGESPREK);
        return sollicitatiegesprek;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sollicitatiegesprek createUpdatedEntity(EntityManager em) {
        Sollicitatiegesprek sollicitatiegesprek = new Sollicitatiegesprek()
            .aangenomen(UPDATED_AANGENOMEN)
            .datum(UPDATED_DATUM)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .volgendgesprek(UPDATED_VOLGENDGESPREK);
        return sollicitatiegesprek;
    }

    @BeforeEach
    public void initTest() {
        sollicitatiegesprek = createEntity(em);
    }

    @Test
    @Transactional
    void createSollicitatiegesprek() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sollicitatiegesprek
        var returnedSollicitatiegesprek = om.readValue(
            restSollicitatiegesprekMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sollicitatiegesprek)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Sollicitatiegesprek.class
        );

        // Validate the Sollicitatiegesprek in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSollicitatiegesprekUpdatableFieldsEquals(
            returnedSollicitatiegesprek,
            getPersistedSollicitatiegesprek(returnedSollicitatiegesprek)
        );
    }

    @Test
    @Transactional
    void createSollicitatiegesprekWithExistingId() throws Exception {
        // Create the Sollicitatiegesprek with an existing ID
        sollicitatiegesprek.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSollicitatiegesprekMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sollicitatiegesprek)))
            .andExpect(status().isBadRequest());

        // Validate the Sollicitatiegesprek in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSollicitatiegespreks() throws Exception {
        // Initialize the database
        sollicitatiegesprekRepository.saveAndFlush(sollicitatiegesprek);

        // Get all the sollicitatiegesprekList
        restSollicitatiegesprekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sollicitatiegesprek.getId().intValue())))
            .andExpect(jsonPath("$.[*].aangenomen").value(hasItem(DEFAULT_AANGENOMEN.booleanValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].opmerkingen").value(hasItem(DEFAULT_OPMERKINGEN)))
            .andExpect(jsonPath("$.[*].volgendgesprek").value(hasItem(DEFAULT_VOLGENDGESPREK.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSollicitatiegespreksWithEagerRelationshipsIsEnabled() throws Exception {
        when(sollicitatiegesprekRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSollicitatiegesprekMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(sollicitatiegesprekRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSollicitatiegespreksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(sollicitatiegesprekRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSollicitatiegesprekMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(sollicitatiegesprekRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSollicitatiegesprek() throws Exception {
        // Initialize the database
        sollicitatiegesprekRepository.saveAndFlush(sollicitatiegesprek);

        // Get the sollicitatiegesprek
        restSollicitatiegesprekMockMvc
            .perform(get(ENTITY_API_URL_ID, sollicitatiegesprek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sollicitatiegesprek.getId().intValue()))
            .andExpect(jsonPath("$.aangenomen").value(DEFAULT_AANGENOMEN.booleanValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.opmerkingen").value(DEFAULT_OPMERKINGEN))
            .andExpect(jsonPath("$.volgendgesprek").value(DEFAULT_VOLGENDGESPREK.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingSollicitatiegesprek() throws Exception {
        // Get the sollicitatiegesprek
        restSollicitatiegesprekMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSollicitatiegesprek() throws Exception {
        // Initialize the database
        sollicitatiegesprekRepository.saveAndFlush(sollicitatiegesprek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sollicitatiegesprek
        Sollicitatiegesprek updatedSollicitatiegesprek = sollicitatiegesprekRepository.findById(sollicitatiegesprek.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSollicitatiegesprek are not directly saved in db
        em.detach(updatedSollicitatiegesprek);
        updatedSollicitatiegesprek
            .aangenomen(UPDATED_AANGENOMEN)
            .datum(UPDATED_DATUM)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .volgendgesprek(UPDATED_VOLGENDGESPREK);

        restSollicitatiegesprekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSollicitatiegesprek.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSollicitatiegesprek))
            )
            .andExpect(status().isOk());

        // Validate the Sollicitatiegesprek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSollicitatiegesprekToMatchAllProperties(updatedSollicitatiegesprek);
    }

    @Test
    @Transactional
    void putNonExistingSollicitatiegesprek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitatiegesprek.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSollicitatiegesprekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sollicitatiegesprek.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sollicitatiegesprek))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sollicitatiegesprek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSollicitatiegesprek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitatiegesprek.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSollicitatiegesprekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sollicitatiegesprek))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sollicitatiegesprek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSollicitatiegesprek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitatiegesprek.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSollicitatiegesprekMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sollicitatiegesprek)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sollicitatiegesprek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSollicitatiegesprekWithPatch() throws Exception {
        // Initialize the database
        sollicitatiegesprekRepository.saveAndFlush(sollicitatiegesprek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sollicitatiegesprek using partial update
        Sollicitatiegesprek partialUpdatedSollicitatiegesprek = new Sollicitatiegesprek();
        partialUpdatedSollicitatiegesprek.setId(sollicitatiegesprek.getId());

        partialUpdatedSollicitatiegesprek.volgendgesprek(UPDATED_VOLGENDGESPREK);

        restSollicitatiegesprekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSollicitatiegesprek.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSollicitatiegesprek))
            )
            .andExpect(status().isOk());

        // Validate the Sollicitatiegesprek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSollicitatiegesprekUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSollicitatiegesprek, sollicitatiegesprek),
            getPersistedSollicitatiegesprek(sollicitatiegesprek)
        );
    }

    @Test
    @Transactional
    void fullUpdateSollicitatiegesprekWithPatch() throws Exception {
        // Initialize the database
        sollicitatiegesprekRepository.saveAndFlush(sollicitatiegesprek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sollicitatiegesprek using partial update
        Sollicitatiegesprek partialUpdatedSollicitatiegesprek = new Sollicitatiegesprek();
        partialUpdatedSollicitatiegesprek.setId(sollicitatiegesprek.getId());

        partialUpdatedSollicitatiegesprek
            .aangenomen(UPDATED_AANGENOMEN)
            .datum(UPDATED_DATUM)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .volgendgesprek(UPDATED_VOLGENDGESPREK);

        restSollicitatiegesprekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSollicitatiegesprek.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSollicitatiegesprek))
            )
            .andExpect(status().isOk());

        // Validate the Sollicitatiegesprek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSollicitatiegesprekUpdatableFieldsEquals(
            partialUpdatedSollicitatiegesprek,
            getPersistedSollicitatiegesprek(partialUpdatedSollicitatiegesprek)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSollicitatiegesprek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitatiegesprek.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSollicitatiegesprekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sollicitatiegesprek.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sollicitatiegesprek))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sollicitatiegesprek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSollicitatiegesprek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitatiegesprek.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSollicitatiegesprekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sollicitatiegesprek))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sollicitatiegesprek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSollicitatiegesprek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sollicitatiegesprek.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSollicitatiegesprekMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sollicitatiegesprek)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sollicitatiegesprek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSollicitatiegesprek() throws Exception {
        // Initialize the database
        sollicitatiegesprekRepository.saveAndFlush(sollicitatiegesprek);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sollicitatiegesprek
        restSollicitatiegesprekMockMvc
            .perform(delete(ENTITY_API_URL_ID, sollicitatiegesprek.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sollicitatiegesprekRepository.count();
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

    protected Sollicitatiegesprek getPersistedSollicitatiegesprek(Sollicitatiegesprek sollicitatiegesprek) {
        return sollicitatiegesprekRepository.findById(sollicitatiegesprek.getId()).orElseThrow();
    }

    protected void assertPersistedSollicitatiegesprekToMatchAllProperties(Sollicitatiegesprek expectedSollicitatiegesprek) {
        assertSollicitatiegesprekAllPropertiesEquals(
            expectedSollicitatiegesprek,
            getPersistedSollicitatiegesprek(expectedSollicitatiegesprek)
        );
    }

    protected void assertPersistedSollicitatiegesprekToMatchUpdatableProperties(Sollicitatiegesprek expectedSollicitatiegesprek) {
        assertSollicitatiegesprekAllUpdatablePropertiesEquals(
            expectedSollicitatiegesprek,
            getPersistedSollicitatiegesprek(expectedSollicitatiegesprek)
        );
    }
}
