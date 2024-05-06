package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SamenstellerAsserts.*;
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
import nl.ritense.demo.domain.Samensteller;
import nl.ritense.demo.repository.SamenstellerRepository;
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
 * Integration tests for the {@link SamenstellerResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SamenstellerResourceIT {

    private static final String DEFAULT_ROL = "AAAAAAAAAA";
    private static final String UPDATED_ROL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/samenstellers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SamenstellerRepository samenstellerRepository;

    @Mock
    private SamenstellerRepository samenstellerRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSamenstellerMockMvc;

    private Samensteller samensteller;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Samensteller createEntity(EntityManager em) {
        Samensteller samensteller = new Samensteller().rol(DEFAULT_ROL);
        return samensteller;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Samensteller createUpdatedEntity(EntityManager em) {
        Samensteller samensteller = new Samensteller().rol(UPDATED_ROL);
        return samensteller;
    }

    @BeforeEach
    public void initTest() {
        samensteller = createEntity(em);
    }

    @Test
    @Transactional
    void createSamensteller() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Samensteller
        var returnedSamensteller = om.readValue(
            restSamenstellerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(samensteller)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Samensteller.class
        );

        // Validate the Samensteller in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSamenstellerUpdatableFieldsEquals(returnedSamensteller, getPersistedSamensteller(returnedSamensteller));
    }

    @Test
    @Transactional
    void createSamenstellerWithExistingId() throws Exception {
        // Create the Samensteller with an existing ID
        samensteller.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSamenstellerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(samensteller)))
            .andExpect(status().isBadRequest());

        // Validate the Samensteller in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSamenstellers() throws Exception {
        // Initialize the database
        samenstellerRepository.saveAndFlush(samensteller);

        // Get all the samenstellerList
        restSamenstellerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(samensteller.getId().intValue())))
            .andExpect(jsonPath("$.[*].rol").value(hasItem(DEFAULT_ROL)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSamenstellersWithEagerRelationshipsIsEnabled() throws Exception {
        when(samenstellerRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSamenstellerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(samenstellerRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSamenstellersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(samenstellerRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSamenstellerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(samenstellerRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSamensteller() throws Exception {
        // Initialize the database
        samenstellerRepository.saveAndFlush(samensteller);

        // Get the samensteller
        restSamenstellerMockMvc
            .perform(get(ENTITY_API_URL_ID, samensteller.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(samensteller.getId().intValue()))
            .andExpect(jsonPath("$.rol").value(DEFAULT_ROL));
    }

    @Test
    @Transactional
    void getNonExistingSamensteller() throws Exception {
        // Get the samensteller
        restSamenstellerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSamensteller() throws Exception {
        // Initialize the database
        samenstellerRepository.saveAndFlush(samensteller);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the samensteller
        Samensteller updatedSamensteller = samenstellerRepository.findById(samensteller.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSamensteller are not directly saved in db
        em.detach(updatedSamensteller);
        updatedSamensteller.rol(UPDATED_ROL);

        restSamenstellerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSamensteller.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSamensteller))
            )
            .andExpect(status().isOk());

        // Validate the Samensteller in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSamenstellerToMatchAllProperties(updatedSamensteller);
    }

    @Test
    @Transactional
    void putNonExistingSamensteller() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        samensteller.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSamenstellerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, samensteller.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(samensteller))
            )
            .andExpect(status().isBadRequest());

        // Validate the Samensteller in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSamensteller() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        samensteller.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSamenstellerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(samensteller))
            )
            .andExpect(status().isBadRequest());

        // Validate the Samensteller in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSamensteller() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        samensteller.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSamenstellerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(samensteller)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Samensteller in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSamenstellerWithPatch() throws Exception {
        // Initialize the database
        samenstellerRepository.saveAndFlush(samensteller);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the samensteller using partial update
        Samensteller partialUpdatedSamensteller = new Samensteller();
        partialUpdatedSamensteller.setId(samensteller.getId());

        partialUpdatedSamensteller.rol(UPDATED_ROL);

        restSamenstellerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSamensteller.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSamensteller))
            )
            .andExpect(status().isOk());

        // Validate the Samensteller in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSamenstellerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSamensteller, samensteller),
            getPersistedSamensteller(samensteller)
        );
    }

    @Test
    @Transactional
    void fullUpdateSamenstellerWithPatch() throws Exception {
        // Initialize the database
        samenstellerRepository.saveAndFlush(samensteller);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the samensteller using partial update
        Samensteller partialUpdatedSamensteller = new Samensteller();
        partialUpdatedSamensteller.setId(samensteller.getId());

        partialUpdatedSamensteller.rol(UPDATED_ROL);

        restSamenstellerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSamensteller.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSamensteller))
            )
            .andExpect(status().isOk());

        // Validate the Samensteller in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSamenstellerUpdatableFieldsEquals(partialUpdatedSamensteller, getPersistedSamensteller(partialUpdatedSamensteller));
    }

    @Test
    @Transactional
    void patchNonExistingSamensteller() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        samensteller.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSamenstellerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, samensteller.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(samensteller))
            )
            .andExpect(status().isBadRequest());

        // Validate the Samensteller in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSamensteller() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        samensteller.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSamenstellerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(samensteller))
            )
            .andExpect(status().isBadRequest());

        // Validate the Samensteller in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSamensteller() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        samensteller.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSamenstellerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(samensteller)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Samensteller in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSamensteller() throws Exception {
        // Initialize the database
        samenstellerRepository.saveAndFlush(samensteller);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the samensteller
        restSamenstellerMockMvc
            .perform(delete(ENTITY_API_URL_ID, samensteller.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return samenstellerRepository.count();
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

    protected Samensteller getPersistedSamensteller(Samensteller samensteller) {
        return samenstellerRepository.findById(samensteller.getId()).orElseThrow();
    }

    protected void assertPersistedSamenstellerToMatchAllProperties(Samensteller expectedSamensteller) {
        assertSamenstellerAllPropertiesEquals(expectedSamensteller, getPersistedSamensteller(expectedSamensteller));
    }

    protected void assertPersistedSamenstellerToMatchUpdatableProperties(Samensteller expectedSamensteller) {
        assertSamenstellerAllUpdatablePropertiesEquals(expectedSamensteller, getPersistedSamensteller(expectedSamensteller));
    }
}
