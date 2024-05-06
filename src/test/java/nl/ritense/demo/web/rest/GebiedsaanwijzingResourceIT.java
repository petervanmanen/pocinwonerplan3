package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GebiedsaanwijzingAsserts.*;
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
import nl.ritense.demo.domain.Gebiedsaanwijzing;
import nl.ritense.demo.repository.GebiedsaanwijzingRepository;
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
 * Integration tests for the {@link GebiedsaanwijzingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class GebiedsaanwijzingResourceIT {

    private static final String DEFAULT_GROEP = "AAAAAAAAAA";
    private static final String UPDATED_GROEP = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_NEN_3610_ID = "AAAAAAAAAA";
    private static final String UPDATED_NEN_3610_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gebiedsaanwijzings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GebiedsaanwijzingRepository gebiedsaanwijzingRepository;

    @Mock
    private GebiedsaanwijzingRepository gebiedsaanwijzingRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGebiedsaanwijzingMockMvc;

    private Gebiedsaanwijzing gebiedsaanwijzing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gebiedsaanwijzing createEntity(EntityManager em) {
        Gebiedsaanwijzing gebiedsaanwijzing = new Gebiedsaanwijzing()
            .groep(DEFAULT_GROEP)
            .naam(DEFAULT_NAAM)
            .nen3610id(DEFAULT_NEN_3610_ID);
        return gebiedsaanwijzing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gebiedsaanwijzing createUpdatedEntity(EntityManager em) {
        Gebiedsaanwijzing gebiedsaanwijzing = new Gebiedsaanwijzing()
            .groep(UPDATED_GROEP)
            .naam(UPDATED_NAAM)
            .nen3610id(UPDATED_NEN_3610_ID);
        return gebiedsaanwijzing;
    }

    @BeforeEach
    public void initTest() {
        gebiedsaanwijzing = createEntity(em);
    }

    @Test
    @Transactional
    void createGebiedsaanwijzing() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Gebiedsaanwijzing
        var returnedGebiedsaanwijzing = om.readValue(
            restGebiedsaanwijzingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebiedsaanwijzing)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Gebiedsaanwijzing.class
        );

        // Validate the Gebiedsaanwijzing in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGebiedsaanwijzingUpdatableFieldsEquals(returnedGebiedsaanwijzing, getPersistedGebiedsaanwijzing(returnedGebiedsaanwijzing));
    }

    @Test
    @Transactional
    void createGebiedsaanwijzingWithExistingId() throws Exception {
        // Create the Gebiedsaanwijzing with an existing ID
        gebiedsaanwijzing.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGebiedsaanwijzingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebiedsaanwijzing)))
            .andExpect(status().isBadRequest());

        // Validate the Gebiedsaanwijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGebiedsaanwijzings() throws Exception {
        // Initialize the database
        gebiedsaanwijzingRepository.saveAndFlush(gebiedsaanwijzing);

        // Get all the gebiedsaanwijzingList
        restGebiedsaanwijzingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gebiedsaanwijzing.getId().intValue())))
            .andExpect(jsonPath("$.[*].groep").value(hasItem(DEFAULT_GROEP)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].nen3610id").value(hasItem(DEFAULT_NEN_3610_ID)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGebiedsaanwijzingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(gebiedsaanwijzingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGebiedsaanwijzingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(gebiedsaanwijzingRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGebiedsaanwijzingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(gebiedsaanwijzingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGebiedsaanwijzingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(gebiedsaanwijzingRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getGebiedsaanwijzing() throws Exception {
        // Initialize the database
        gebiedsaanwijzingRepository.saveAndFlush(gebiedsaanwijzing);

        // Get the gebiedsaanwijzing
        restGebiedsaanwijzingMockMvc
            .perform(get(ENTITY_API_URL_ID, gebiedsaanwijzing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gebiedsaanwijzing.getId().intValue()))
            .andExpect(jsonPath("$.groep").value(DEFAULT_GROEP))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.nen3610id").value(DEFAULT_NEN_3610_ID));
    }

    @Test
    @Transactional
    void getNonExistingGebiedsaanwijzing() throws Exception {
        // Get the gebiedsaanwijzing
        restGebiedsaanwijzingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGebiedsaanwijzing() throws Exception {
        // Initialize the database
        gebiedsaanwijzingRepository.saveAndFlush(gebiedsaanwijzing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebiedsaanwijzing
        Gebiedsaanwijzing updatedGebiedsaanwijzing = gebiedsaanwijzingRepository.findById(gebiedsaanwijzing.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGebiedsaanwijzing are not directly saved in db
        em.detach(updatedGebiedsaanwijzing);
        updatedGebiedsaanwijzing.groep(UPDATED_GROEP).naam(UPDATED_NAAM).nen3610id(UPDATED_NEN_3610_ID);

        restGebiedsaanwijzingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGebiedsaanwijzing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGebiedsaanwijzing))
            )
            .andExpect(status().isOk());

        // Validate the Gebiedsaanwijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGebiedsaanwijzingToMatchAllProperties(updatedGebiedsaanwijzing);
    }

    @Test
    @Transactional
    void putNonExistingGebiedsaanwijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebiedsaanwijzing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGebiedsaanwijzingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gebiedsaanwijzing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gebiedsaanwijzing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebiedsaanwijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGebiedsaanwijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebiedsaanwijzing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebiedsaanwijzingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gebiedsaanwijzing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebiedsaanwijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGebiedsaanwijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebiedsaanwijzing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebiedsaanwijzingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebiedsaanwijzing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gebiedsaanwijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGebiedsaanwijzingWithPatch() throws Exception {
        // Initialize the database
        gebiedsaanwijzingRepository.saveAndFlush(gebiedsaanwijzing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebiedsaanwijzing using partial update
        Gebiedsaanwijzing partialUpdatedGebiedsaanwijzing = new Gebiedsaanwijzing();
        partialUpdatedGebiedsaanwijzing.setId(gebiedsaanwijzing.getId());

        partialUpdatedGebiedsaanwijzing.groep(UPDATED_GROEP).naam(UPDATED_NAAM);

        restGebiedsaanwijzingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGebiedsaanwijzing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGebiedsaanwijzing))
            )
            .andExpect(status().isOk());

        // Validate the Gebiedsaanwijzing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGebiedsaanwijzingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGebiedsaanwijzing, gebiedsaanwijzing),
            getPersistedGebiedsaanwijzing(gebiedsaanwijzing)
        );
    }

    @Test
    @Transactional
    void fullUpdateGebiedsaanwijzingWithPatch() throws Exception {
        // Initialize the database
        gebiedsaanwijzingRepository.saveAndFlush(gebiedsaanwijzing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebiedsaanwijzing using partial update
        Gebiedsaanwijzing partialUpdatedGebiedsaanwijzing = new Gebiedsaanwijzing();
        partialUpdatedGebiedsaanwijzing.setId(gebiedsaanwijzing.getId());

        partialUpdatedGebiedsaanwijzing.groep(UPDATED_GROEP).naam(UPDATED_NAAM).nen3610id(UPDATED_NEN_3610_ID);

        restGebiedsaanwijzingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGebiedsaanwijzing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGebiedsaanwijzing))
            )
            .andExpect(status().isOk());

        // Validate the Gebiedsaanwijzing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGebiedsaanwijzingUpdatableFieldsEquals(
            partialUpdatedGebiedsaanwijzing,
            getPersistedGebiedsaanwijzing(partialUpdatedGebiedsaanwijzing)
        );
    }

    @Test
    @Transactional
    void patchNonExistingGebiedsaanwijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebiedsaanwijzing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGebiedsaanwijzingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gebiedsaanwijzing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gebiedsaanwijzing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebiedsaanwijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGebiedsaanwijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebiedsaanwijzing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebiedsaanwijzingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gebiedsaanwijzing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebiedsaanwijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGebiedsaanwijzing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebiedsaanwijzing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebiedsaanwijzingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gebiedsaanwijzing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gebiedsaanwijzing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGebiedsaanwijzing() throws Exception {
        // Initialize the database
        gebiedsaanwijzingRepository.saveAndFlush(gebiedsaanwijzing);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gebiedsaanwijzing
        restGebiedsaanwijzingMockMvc
            .perform(delete(ENTITY_API_URL_ID, gebiedsaanwijzing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gebiedsaanwijzingRepository.count();
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

    protected Gebiedsaanwijzing getPersistedGebiedsaanwijzing(Gebiedsaanwijzing gebiedsaanwijzing) {
        return gebiedsaanwijzingRepository.findById(gebiedsaanwijzing.getId()).orElseThrow();
    }

    protected void assertPersistedGebiedsaanwijzingToMatchAllProperties(Gebiedsaanwijzing expectedGebiedsaanwijzing) {
        assertGebiedsaanwijzingAllPropertiesEquals(expectedGebiedsaanwijzing, getPersistedGebiedsaanwijzing(expectedGebiedsaanwijzing));
    }

    protected void assertPersistedGebiedsaanwijzingToMatchUpdatableProperties(Gebiedsaanwijzing expectedGebiedsaanwijzing) {
        assertGebiedsaanwijzingAllUpdatablePropertiesEquals(
            expectedGebiedsaanwijzing,
            getPersistedGebiedsaanwijzing(expectedGebiedsaanwijzing)
        );
    }
}
