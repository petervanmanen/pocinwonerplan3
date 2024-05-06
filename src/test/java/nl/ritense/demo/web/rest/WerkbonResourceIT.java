package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WerkbonAsserts.*;
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
import nl.ritense.demo.domain.Werkbon;
import nl.ritense.demo.repository.WerkbonRepository;
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
 * Integration tests for the {@link WerkbonResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class WerkbonResourceIT {

    private static final String ENTITY_API_URL = "/api/werkbons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WerkbonRepository werkbonRepository;

    @Mock
    private WerkbonRepository werkbonRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWerkbonMockMvc;

    private Werkbon werkbon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Werkbon createEntity(EntityManager em) {
        Werkbon werkbon = new Werkbon();
        return werkbon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Werkbon createUpdatedEntity(EntityManager em) {
        Werkbon werkbon = new Werkbon();
        return werkbon;
    }

    @BeforeEach
    public void initTest() {
        werkbon = createEntity(em);
    }

    @Test
    @Transactional
    void createWerkbon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Werkbon
        var returnedWerkbon = om.readValue(
            restWerkbonMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(werkbon)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Werkbon.class
        );

        // Validate the Werkbon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWerkbonUpdatableFieldsEquals(returnedWerkbon, getPersistedWerkbon(returnedWerkbon));
    }

    @Test
    @Transactional
    void createWerkbonWithExistingId() throws Exception {
        // Create the Werkbon with an existing ID
        werkbon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWerkbonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(werkbon)))
            .andExpect(status().isBadRequest());

        // Validate the Werkbon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWerkbons() throws Exception {
        // Initialize the database
        werkbonRepository.saveAndFlush(werkbon);

        // Get all the werkbonList
        restWerkbonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(werkbon.getId().intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWerkbonsWithEagerRelationshipsIsEnabled() throws Exception {
        when(werkbonRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWerkbonMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(werkbonRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWerkbonsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(werkbonRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWerkbonMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(werkbonRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getWerkbon() throws Exception {
        // Initialize the database
        werkbonRepository.saveAndFlush(werkbon);

        // Get the werkbon
        restWerkbonMockMvc
            .perform(get(ENTITY_API_URL_ID, werkbon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(werkbon.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingWerkbon() throws Exception {
        // Get the werkbon
        restWerkbonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWerkbon() throws Exception {
        // Initialize the database
        werkbonRepository.saveAndFlush(werkbon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the werkbon
        Werkbon updatedWerkbon = werkbonRepository.findById(werkbon.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWerkbon are not directly saved in db
        em.detach(updatedWerkbon);

        restWerkbonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWerkbon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWerkbon))
            )
            .andExpect(status().isOk());

        // Validate the Werkbon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWerkbonToMatchAllProperties(updatedWerkbon);
    }

    @Test
    @Transactional
    void putNonExistingWerkbon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkbon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWerkbonMockMvc
            .perform(put(ENTITY_API_URL_ID, werkbon.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(werkbon)))
            .andExpect(status().isBadRequest());

        // Validate the Werkbon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWerkbon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkbon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWerkbonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(werkbon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Werkbon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWerkbon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkbon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWerkbonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(werkbon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Werkbon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWerkbonWithPatch() throws Exception {
        // Initialize the database
        werkbonRepository.saveAndFlush(werkbon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the werkbon using partial update
        Werkbon partialUpdatedWerkbon = new Werkbon();
        partialUpdatedWerkbon.setId(werkbon.getId());

        restWerkbonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWerkbon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWerkbon))
            )
            .andExpect(status().isOk());

        // Validate the Werkbon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWerkbonUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedWerkbon, werkbon), getPersistedWerkbon(werkbon));
    }

    @Test
    @Transactional
    void fullUpdateWerkbonWithPatch() throws Exception {
        // Initialize the database
        werkbonRepository.saveAndFlush(werkbon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the werkbon using partial update
        Werkbon partialUpdatedWerkbon = new Werkbon();
        partialUpdatedWerkbon.setId(werkbon.getId());

        restWerkbonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWerkbon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWerkbon))
            )
            .andExpect(status().isOk());

        // Validate the Werkbon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWerkbonUpdatableFieldsEquals(partialUpdatedWerkbon, getPersistedWerkbon(partialUpdatedWerkbon));
    }

    @Test
    @Transactional
    void patchNonExistingWerkbon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkbon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWerkbonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, werkbon.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(werkbon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Werkbon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWerkbon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkbon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWerkbonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(werkbon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Werkbon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWerkbon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkbon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWerkbonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(werkbon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Werkbon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWerkbon() throws Exception {
        // Initialize the database
        werkbonRepository.saveAndFlush(werkbon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the werkbon
        restWerkbonMockMvc
            .perform(delete(ENTITY_API_URL_ID, werkbon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return werkbonRepository.count();
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

    protected Werkbon getPersistedWerkbon(Werkbon werkbon) {
        return werkbonRepository.findById(werkbon.getId()).orElseThrow();
    }

    protected void assertPersistedWerkbonToMatchAllProperties(Werkbon expectedWerkbon) {
        assertWerkbonAllPropertiesEquals(expectedWerkbon, getPersistedWerkbon(expectedWerkbon));
    }

    protected void assertPersistedWerkbonToMatchUpdatableProperties(Werkbon expectedWerkbon) {
        assertWerkbonAllUpdatablePropertiesEquals(expectedWerkbon, getPersistedWerkbon(expectedWerkbon));
    }
}
