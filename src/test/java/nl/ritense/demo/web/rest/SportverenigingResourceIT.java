package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SportverenigingAsserts.*;
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
import nl.ritense.demo.domain.Sportvereniging;
import nl.ritense.demo.repository.SportverenigingRepository;
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
 * Integration tests for the {@link SportverenigingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SportverenigingResourceIT {

    private static final String DEFAULT_AANTALNORMTEAMS = "AAAAAAAAAA";
    private static final String UPDATED_AANTALNORMTEAMS = "BBBBBBBBBB";

    private static final String DEFAULT_ADRES = "AAAAAAAAAA";
    private static final String UPDATED_ADRES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BINNENSPORT = false;
    private static final Boolean UPDATED_BINNENSPORT = true;

    private static final Boolean DEFAULT_BUITENSPORT = false;
    private static final Boolean UPDATED_BUITENSPORT = true;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_LEDENAANTAL = "AAAAAAAAAA";
    private static final String UPDATED_LEDENAANTAL = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPESPORT = "AAAAAAAAAA";
    private static final String UPDATED_TYPESPORT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sportverenigings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SportverenigingRepository sportverenigingRepository;

    @Mock
    private SportverenigingRepository sportverenigingRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSportverenigingMockMvc;

    private Sportvereniging sportvereniging;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sportvereniging createEntity(EntityManager em) {
        Sportvereniging sportvereniging = new Sportvereniging()
            .aantalnormteams(DEFAULT_AANTALNORMTEAMS)
            .adres(DEFAULT_ADRES)
            .binnensport(DEFAULT_BINNENSPORT)
            .buitensport(DEFAULT_BUITENSPORT)
            .email(DEFAULT_EMAIL)
            .ledenaantal(DEFAULT_LEDENAANTAL)
            .naam(DEFAULT_NAAM)
            .typesport(DEFAULT_TYPESPORT);
        return sportvereniging;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sportvereniging createUpdatedEntity(EntityManager em) {
        Sportvereniging sportvereniging = new Sportvereniging()
            .aantalnormteams(UPDATED_AANTALNORMTEAMS)
            .adres(UPDATED_ADRES)
            .binnensport(UPDATED_BINNENSPORT)
            .buitensport(UPDATED_BUITENSPORT)
            .email(UPDATED_EMAIL)
            .ledenaantal(UPDATED_LEDENAANTAL)
            .naam(UPDATED_NAAM)
            .typesport(UPDATED_TYPESPORT);
        return sportvereniging;
    }

    @BeforeEach
    public void initTest() {
        sportvereniging = createEntity(em);
    }

    @Test
    @Transactional
    void createSportvereniging() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sportvereniging
        var returnedSportvereniging = om.readValue(
            restSportverenigingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sportvereniging)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Sportvereniging.class
        );

        // Validate the Sportvereniging in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSportverenigingUpdatableFieldsEquals(returnedSportvereniging, getPersistedSportvereniging(returnedSportvereniging));
    }

    @Test
    @Transactional
    void createSportverenigingWithExistingId() throws Exception {
        // Create the Sportvereniging with an existing ID
        sportvereniging.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSportverenigingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sportvereniging)))
            .andExpect(status().isBadRequest());

        // Validate the Sportvereniging in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSportverenigings() throws Exception {
        // Initialize the database
        sportverenigingRepository.saveAndFlush(sportvereniging);

        // Get all the sportverenigingList
        restSportverenigingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sportvereniging.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantalnormteams").value(hasItem(DEFAULT_AANTALNORMTEAMS)))
            .andExpect(jsonPath("$.[*].adres").value(hasItem(DEFAULT_ADRES)))
            .andExpect(jsonPath("$.[*].binnensport").value(hasItem(DEFAULT_BINNENSPORT.booleanValue())))
            .andExpect(jsonPath("$.[*].buitensport").value(hasItem(DEFAULT_BUITENSPORT.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].ledenaantal").value(hasItem(DEFAULT_LEDENAANTAL)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].typesport").value(hasItem(DEFAULT_TYPESPORT)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSportverenigingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(sportverenigingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSportverenigingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(sportverenigingRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSportverenigingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(sportverenigingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSportverenigingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(sportverenigingRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSportvereniging() throws Exception {
        // Initialize the database
        sportverenigingRepository.saveAndFlush(sportvereniging);

        // Get the sportvereniging
        restSportverenigingMockMvc
            .perform(get(ENTITY_API_URL_ID, sportvereniging.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sportvereniging.getId().intValue()))
            .andExpect(jsonPath("$.aantalnormteams").value(DEFAULT_AANTALNORMTEAMS))
            .andExpect(jsonPath("$.adres").value(DEFAULT_ADRES))
            .andExpect(jsonPath("$.binnensport").value(DEFAULT_BINNENSPORT.booleanValue()))
            .andExpect(jsonPath("$.buitensport").value(DEFAULT_BUITENSPORT.booleanValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.ledenaantal").value(DEFAULT_LEDENAANTAL))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.typesport").value(DEFAULT_TYPESPORT));
    }

    @Test
    @Transactional
    void getNonExistingSportvereniging() throws Exception {
        // Get the sportvereniging
        restSportverenigingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSportvereniging() throws Exception {
        // Initialize the database
        sportverenigingRepository.saveAndFlush(sportvereniging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sportvereniging
        Sportvereniging updatedSportvereniging = sportverenigingRepository.findById(sportvereniging.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSportvereniging are not directly saved in db
        em.detach(updatedSportvereniging);
        updatedSportvereniging
            .aantalnormteams(UPDATED_AANTALNORMTEAMS)
            .adres(UPDATED_ADRES)
            .binnensport(UPDATED_BINNENSPORT)
            .buitensport(UPDATED_BUITENSPORT)
            .email(UPDATED_EMAIL)
            .ledenaantal(UPDATED_LEDENAANTAL)
            .naam(UPDATED_NAAM)
            .typesport(UPDATED_TYPESPORT);

        restSportverenigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSportvereniging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSportvereniging))
            )
            .andExpect(status().isOk());

        // Validate the Sportvereniging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSportverenigingToMatchAllProperties(updatedSportvereniging);
    }

    @Test
    @Transactional
    void putNonExistingSportvereniging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportvereniging.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSportverenigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sportvereniging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sportvereniging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportvereniging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSportvereniging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportvereniging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportverenigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sportvereniging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportvereniging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSportvereniging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportvereniging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportverenigingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sportvereniging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sportvereniging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSportverenigingWithPatch() throws Exception {
        // Initialize the database
        sportverenigingRepository.saveAndFlush(sportvereniging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sportvereniging using partial update
        Sportvereniging partialUpdatedSportvereniging = new Sportvereniging();
        partialUpdatedSportvereniging.setId(sportvereniging.getId());

        partialUpdatedSportvereniging.aantalnormteams(UPDATED_AANTALNORMTEAMS);

        restSportverenigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSportvereniging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSportvereniging))
            )
            .andExpect(status().isOk());

        // Validate the Sportvereniging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSportverenigingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSportvereniging, sportvereniging),
            getPersistedSportvereniging(sportvereniging)
        );
    }

    @Test
    @Transactional
    void fullUpdateSportverenigingWithPatch() throws Exception {
        // Initialize the database
        sportverenigingRepository.saveAndFlush(sportvereniging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sportvereniging using partial update
        Sportvereniging partialUpdatedSportvereniging = new Sportvereniging();
        partialUpdatedSportvereniging.setId(sportvereniging.getId());

        partialUpdatedSportvereniging
            .aantalnormteams(UPDATED_AANTALNORMTEAMS)
            .adres(UPDATED_ADRES)
            .binnensport(UPDATED_BINNENSPORT)
            .buitensport(UPDATED_BUITENSPORT)
            .email(UPDATED_EMAIL)
            .ledenaantal(UPDATED_LEDENAANTAL)
            .naam(UPDATED_NAAM)
            .typesport(UPDATED_TYPESPORT);

        restSportverenigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSportvereniging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSportvereniging))
            )
            .andExpect(status().isOk());

        // Validate the Sportvereniging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSportverenigingUpdatableFieldsEquals(
            partialUpdatedSportvereniging,
            getPersistedSportvereniging(partialUpdatedSportvereniging)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSportvereniging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportvereniging.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSportverenigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sportvereniging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sportvereniging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportvereniging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSportvereniging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportvereniging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportverenigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sportvereniging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportvereniging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSportvereniging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sportvereniging.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportverenigingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sportvereniging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sportvereniging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSportvereniging() throws Exception {
        // Initialize the database
        sportverenigingRepository.saveAndFlush(sportvereniging);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sportvereniging
        restSportverenigingMockMvc
            .perform(delete(ENTITY_API_URL_ID, sportvereniging.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sportverenigingRepository.count();
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

    protected Sportvereniging getPersistedSportvereniging(Sportvereniging sportvereniging) {
        return sportverenigingRepository.findById(sportvereniging.getId()).orElseThrow();
    }

    protected void assertPersistedSportverenigingToMatchAllProperties(Sportvereniging expectedSportvereniging) {
        assertSportverenigingAllPropertiesEquals(expectedSportvereniging, getPersistedSportvereniging(expectedSportvereniging));
    }

    protected void assertPersistedSportverenigingToMatchUpdatableProperties(Sportvereniging expectedSportvereniging) {
        assertSportverenigingAllUpdatablePropertiesEquals(expectedSportvereniging, getPersistedSportvereniging(expectedSportvereniging));
    }
}
