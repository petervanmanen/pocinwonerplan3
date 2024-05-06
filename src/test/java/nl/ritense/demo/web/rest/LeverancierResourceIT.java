package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LeverancierAsserts.*;
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
import nl.ritense.demo.domain.Leverancier;
import nl.ritense.demo.repository.LeverancierRepository;
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
 * Integration tests for the {@link LeverancierResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LeverancierResourceIT {

    private static final String DEFAULT_AGBCODE = "AAAAAAAAAA";
    private static final String UPDATED_AGBCODE = "BBBBBBBBBB";

    private static final String DEFAULT_LEVERANCIERSCODE = "AAAAAAAA";
    private static final String UPDATED_LEVERANCIERSCODE = "BBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_SOORTLEVERANCIER = "AAAAAAAAAA";
    private static final String UPDATED_SOORTLEVERANCIER = "BBBBBBBBBB";

    private static final String DEFAULT_SOORTLEVERANCIERCODE = "AAAAAAAA";
    private static final String UPDATED_SOORTLEVERANCIERCODE = "BBBBBBBB";

    private static final String ENTITY_API_URL = "/api/leveranciers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LeverancierRepository leverancierRepository;

    @Mock
    private LeverancierRepository leverancierRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeverancierMockMvc;

    private Leverancier leverancier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leverancier createEntity(EntityManager em) {
        Leverancier leverancier = new Leverancier()
            .agbcode(DEFAULT_AGBCODE)
            .leverancierscode(DEFAULT_LEVERANCIERSCODE)
            .naam(DEFAULT_NAAM)
            .soortleverancier(DEFAULT_SOORTLEVERANCIER)
            .soortleveranciercode(DEFAULT_SOORTLEVERANCIERCODE);
        return leverancier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leverancier createUpdatedEntity(EntityManager em) {
        Leverancier leverancier = new Leverancier()
            .agbcode(UPDATED_AGBCODE)
            .leverancierscode(UPDATED_LEVERANCIERSCODE)
            .naam(UPDATED_NAAM)
            .soortleverancier(UPDATED_SOORTLEVERANCIER)
            .soortleveranciercode(UPDATED_SOORTLEVERANCIERCODE);
        return leverancier;
    }

    @BeforeEach
    public void initTest() {
        leverancier = createEntity(em);
    }

    @Test
    @Transactional
    void createLeverancier() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Leverancier
        var returnedLeverancier = om.readValue(
            restLeverancierMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leverancier)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Leverancier.class
        );

        // Validate the Leverancier in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLeverancierUpdatableFieldsEquals(returnedLeverancier, getPersistedLeverancier(returnedLeverancier));
    }

    @Test
    @Transactional
    void createLeverancierWithExistingId() throws Exception {
        // Create the Leverancier with an existing ID
        leverancier.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeverancierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leverancier)))
            .andExpect(status().isBadRequest());

        // Validate the Leverancier in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeveranciers() throws Exception {
        // Initialize the database
        leverancierRepository.saveAndFlush(leverancier);

        // Get all the leverancierList
        restLeverancierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leverancier.getId().intValue())))
            .andExpect(jsonPath("$.[*].agbcode").value(hasItem(DEFAULT_AGBCODE)))
            .andExpect(jsonPath("$.[*].leverancierscode").value(hasItem(DEFAULT_LEVERANCIERSCODE)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].soortleverancier").value(hasItem(DEFAULT_SOORTLEVERANCIER)))
            .andExpect(jsonPath("$.[*].soortleveranciercode").value(hasItem(DEFAULT_SOORTLEVERANCIERCODE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLeveranciersWithEagerRelationshipsIsEnabled() throws Exception {
        when(leverancierRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLeverancierMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(leverancierRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLeveranciersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(leverancierRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLeverancierMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(leverancierRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getLeverancier() throws Exception {
        // Initialize the database
        leverancierRepository.saveAndFlush(leverancier);

        // Get the leverancier
        restLeverancierMockMvc
            .perform(get(ENTITY_API_URL_ID, leverancier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leverancier.getId().intValue()))
            .andExpect(jsonPath("$.agbcode").value(DEFAULT_AGBCODE))
            .andExpect(jsonPath("$.leverancierscode").value(DEFAULT_LEVERANCIERSCODE))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.soortleverancier").value(DEFAULT_SOORTLEVERANCIER))
            .andExpect(jsonPath("$.soortleveranciercode").value(DEFAULT_SOORTLEVERANCIERCODE));
    }

    @Test
    @Transactional
    void getNonExistingLeverancier() throws Exception {
        // Get the leverancier
        restLeverancierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeverancier() throws Exception {
        // Initialize the database
        leverancierRepository.saveAndFlush(leverancier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leverancier
        Leverancier updatedLeverancier = leverancierRepository.findById(leverancier.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLeverancier are not directly saved in db
        em.detach(updatedLeverancier);
        updatedLeverancier
            .agbcode(UPDATED_AGBCODE)
            .leverancierscode(UPDATED_LEVERANCIERSCODE)
            .naam(UPDATED_NAAM)
            .soortleverancier(UPDATED_SOORTLEVERANCIER)
            .soortleveranciercode(UPDATED_SOORTLEVERANCIERCODE);

        restLeverancierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeverancier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLeverancier))
            )
            .andExpect(status().isOk());

        // Validate the Leverancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLeverancierToMatchAllProperties(updatedLeverancier);
    }

    @Test
    @Transactional
    void putNonExistingLeverancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leverancier.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeverancierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leverancier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(leverancier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leverancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeverancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leverancier.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeverancierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(leverancier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leverancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeverancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leverancier.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeverancierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leverancier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leverancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeverancierWithPatch() throws Exception {
        // Initialize the database
        leverancierRepository.saveAndFlush(leverancier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leverancier using partial update
        Leverancier partialUpdatedLeverancier = new Leverancier();
        partialUpdatedLeverancier.setId(leverancier.getId());

        partialUpdatedLeverancier
            .leverancierscode(UPDATED_LEVERANCIERSCODE)
            .naam(UPDATED_NAAM)
            .soortleverancier(UPDATED_SOORTLEVERANCIER)
            .soortleveranciercode(UPDATED_SOORTLEVERANCIERCODE);

        restLeverancierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeverancier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLeverancier))
            )
            .andExpect(status().isOk());

        // Validate the Leverancier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLeverancierUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLeverancier, leverancier),
            getPersistedLeverancier(leverancier)
        );
    }

    @Test
    @Transactional
    void fullUpdateLeverancierWithPatch() throws Exception {
        // Initialize the database
        leverancierRepository.saveAndFlush(leverancier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leverancier using partial update
        Leverancier partialUpdatedLeverancier = new Leverancier();
        partialUpdatedLeverancier.setId(leverancier.getId());

        partialUpdatedLeverancier
            .agbcode(UPDATED_AGBCODE)
            .leverancierscode(UPDATED_LEVERANCIERSCODE)
            .naam(UPDATED_NAAM)
            .soortleverancier(UPDATED_SOORTLEVERANCIER)
            .soortleveranciercode(UPDATED_SOORTLEVERANCIERCODE);

        restLeverancierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeverancier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLeverancier))
            )
            .andExpect(status().isOk());

        // Validate the Leverancier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLeverancierUpdatableFieldsEquals(partialUpdatedLeverancier, getPersistedLeverancier(partialUpdatedLeverancier));
    }

    @Test
    @Transactional
    void patchNonExistingLeverancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leverancier.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeverancierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leverancier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(leverancier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leverancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeverancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leverancier.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeverancierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(leverancier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leverancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeverancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leverancier.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeverancierMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(leverancier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leverancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeverancier() throws Exception {
        // Initialize the database
        leverancierRepository.saveAndFlush(leverancier);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the leverancier
        restLeverancierMockMvc
            .perform(delete(ENTITY_API_URL_ID, leverancier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return leverancierRepository.count();
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

    protected Leverancier getPersistedLeverancier(Leverancier leverancier) {
        return leverancierRepository.findById(leverancier.getId()).orElseThrow();
    }

    protected void assertPersistedLeverancierToMatchAllProperties(Leverancier expectedLeverancier) {
        assertLeverancierAllPropertiesEquals(expectedLeverancier, getPersistedLeverancier(expectedLeverancier));
    }

    protected void assertPersistedLeverancierToMatchUpdatableProperties(Leverancier expectedLeverancier) {
        assertLeverancierAllUpdatablePropertiesEquals(expectedLeverancier, getPersistedLeverancier(expectedLeverancier));
    }
}
