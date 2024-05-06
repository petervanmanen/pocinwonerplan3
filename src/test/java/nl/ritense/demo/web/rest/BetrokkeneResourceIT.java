package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BetrokkeneAsserts.*;
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
import nl.ritense.demo.domain.Betrokkene;
import nl.ritense.demo.repository.BetrokkeneRepository;
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
 * Integration tests for the {@link BetrokkeneResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BetrokkeneResourceIT {

    private static final String DEFAULT_ADRESBINNENLAND = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBINNENLAND = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESBUITENLAND = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBUITENLAND = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_ROL = "AAAAAAAAAA";
    private static final String UPDATED_ROL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/betrokkenes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BetrokkeneRepository betrokkeneRepository;

    @Mock
    private BetrokkeneRepository betrokkeneRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBetrokkeneMockMvc;

    private Betrokkene betrokkene;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Betrokkene createEntity(EntityManager em) {
        Betrokkene betrokkene = new Betrokkene()
            .adresbinnenland(DEFAULT_ADRESBINNENLAND)
            .adresbuitenland(DEFAULT_ADRESBUITENLAND)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .naam(DEFAULT_NAAM)
            .rol(DEFAULT_ROL);
        return betrokkene;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Betrokkene createUpdatedEntity(EntityManager em) {
        Betrokkene betrokkene = new Betrokkene()
            .adresbinnenland(UPDATED_ADRESBINNENLAND)
            .adresbuitenland(UPDATED_ADRESBUITENLAND)
            .identificatie(UPDATED_IDENTIFICATIE)
            .naam(UPDATED_NAAM)
            .rol(UPDATED_ROL);
        return betrokkene;
    }

    @BeforeEach
    public void initTest() {
        betrokkene = createEntity(em);
    }

    @Test
    @Transactional
    void createBetrokkene() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Betrokkene
        var returnedBetrokkene = om.readValue(
            restBetrokkeneMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(betrokkene)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Betrokkene.class
        );

        // Validate the Betrokkene in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBetrokkeneUpdatableFieldsEquals(returnedBetrokkene, getPersistedBetrokkene(returnedBetrokkene));
    }

    @Test
    @Transactional
    void createBetrokkeneWithExistingId() throws Exception {
        // Create the Betrokkene with an existing ID
        betrokkene.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBetrokkeneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(betrokkene)))
            .andExpect(status().isBadRequest());

        // Validate the Betrokkene in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBetrokkenes() throws Exception {
        // Initialize the database
        betrokkeneRepository.saveAndFlush(betrokkene);

        // Get all the betrokkeneList
        restBetrokkeneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(betrokkene.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresbinnenland").value(hasItem(DEFAULT_ADRESBINNENLAND)))
            .andExpect(jsonPath("$.[*].adresbuitenland").value(hasItem(DEFAULT_ADRESBUITENLAND)))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].rol").value(hasItem(DEFAULT_ROL)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBetrokkenesWithEagerRelationshipsIsEnabled() throws Exception {
        when(betrokkeneRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBetrokkeneMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(betrokkeneRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBetrokkenesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(betrokkeneRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBetrokkeneMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(betrokkeneRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getBetrokkene() throws Exception {
        // Initialize the database
        betrokkeneRepository.saveAndFlush(betrokkene);

        // Get the betrokkene
        restBetrokkeneMockMvc
            .perform(get(ENTITY_API_URL_ID, betrokkene.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(betrokkene.getId().intValue()))
            .andExpect(jsonPath("$.adresbinnenland").value(DEFAULT_ADRESBINNENLAND))
            .andExpect(jsonPath("$.adresbuitenland").value(DEFAULT_ADRESBUITENLAND))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.rol").value(DEFAULT_ROL));
    }

    @Test
    @Transactional
    void getNonExistingBetrokkene() throws Exception {
        // Get the betrokkene
        restBetrokkeneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBetrokkene() throws Exception {
        // Initialize the database
        betrokkeneRepository.saveAndFlush(betrokkene);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the betrokkene
        Betrokkene updatedBetrokkene = betrokkeneRepository.findById(betrokkene.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBetrokkene are not directly saved in db
        em.detach(updatedBetrokkene);
        updatedBetrokkene
            .adresbinnenland(UPDATED_ADRESBINNENLAND)
            .adresbuitenland(UPDATED_ADRESBUITENLAND)
            .identificatie(UPDATED_IDENTIFICATIE)
            .naam(UPDATED_NAAM)
            .rol(UPDATED_ROL);

        restBetrokkeneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBetrokkene.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBetrokkene))
            )
            .andExpect(status().isOk());

        // Validate the Betrokkene in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBetrokkeneToMatchAllProperties(updatedBetrokkene);
    }

    @Test
    @Transactional
    void putNonExistingBetrokkene() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betrokkene.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBetrokkeneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, betrokkene.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(betrokkene))
            )
            .andExpect(status().isBadRequest());

        // Validate the Betrokkene in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBetrokkene() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betrokkene.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBetrokkeneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(betrokkene))
            )
            .andExpect(status().isBadRequest());

        // Validate the Betrokkene in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBetrokkene() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betrokkene.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBetrokkeneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(betrokkene)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Betrokkene in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBetrokkeneWithPatch() throws Exception {
        // Initialize the database
        betrokkeneRepository.saveAndFlush(betrokkene);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the betrokkene using partial update
        Betrokkene partialUpdatedBetrokkene = new Betrokkene();
        partialUpdatedBetrokkene.setId(betrokkene.getId());

        partialUpdatedBetrokkene.adresbuitenland(UPDATED_ADRESBUITENLAND);

        restBetrokkeneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBetrokkene.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBetrokkene))
            )
            .andExpect(status().isOk());

        // Validate the Betrokkene in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBetrokkeneUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBetrokkene, betrokkene),
            getPersistedBetrokkene(betrokkene)
        );
    }

    @Test
    @Transactional
    void fullUpdateBetrokkeneWithPatch() throws Exception {
        // Initialize the database
        betrokkeneRepository.saveAndFlush(betrokkene);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the betrokkene using partial update
        Betrokkene partialUpdatedBetrokkene = new Betrokkene();
        partialUpdatedBetrokkene.setId(betrokkene.getId());

        partialUpdatedBetrokkene
            .adresbinnenland(UPDATED_ADRESBINNENLAND)
            .adresbuitenland(UPDATED_ADRESBUITENLAND)
            .identificatie(UPDATED_IDENTIFICATIE)
            .naam(UPDATED_NAAM)
            .rol(UPDATED_ROL);

        restBetrokkeneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBetrokkene.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBetrokkene))
            )
            .andExpect(status().isOk());

        // Validate the Betrokkene in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBetrokkeneUpdatableFieldsEquals(partialUpdatedBetrokkene, getPersistedBetrokkene(partialUpdatedBetrokkene));
    }

    @Test
    @Transactional
    void patchNonExistingBetrokkene() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betrokkene.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBetrokkeneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, betrokkene.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(betrokkene))
            )
            .andExpect(status().isBadRequest());

        // Validate the Betrokkene in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBetrokkene() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betrokkene.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBetrokkeneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(betrokkene))
            )
            .andExpect(status().isBadRequest());

        // Validate the Betrokkene in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBetrokkene() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        betrokkene.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBetrokkeneMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(betrokkene)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Betrokkene in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBetrokkene() throws Exception {
        // Initialize the database
        betrokkeneRepository.saveAndFlush(betrokkene);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the betrokkene
        restBetrokkeneMockMvc
            .perform(delete(ENTITY_API_URL_ID, betrokkene.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return betrokkeneRepository.count();
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

    protected Betrokkene getPersistedBetrokkene(Betrokkene betrokkene) {
        return betrokkeneRepository.findById(betrokkene.getId()).orElseThrow();
    }

    protected void assertPersistedBetrokkeneToMatchAllProperties(Betrokkene expectedBetrokkene) {
        assertBetrokkeneAllPropertiesEquals(expectedBetrokkene, getPersistedBetrokkene(expectedBetrokkene));
    }

    protected void assertPersistedBetrokkeneToMatchUpdatableProperties(Betrokkene expectedBetrokkene) {
        assertBetrokkeneAllUpdatablePropertiesEquals(expectedBetrokkene, getPersistedBetrokkene(expectedBetrokkene));
    }
}
