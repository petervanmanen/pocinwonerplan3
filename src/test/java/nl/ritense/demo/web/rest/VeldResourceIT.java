package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VeldAsserts.*;
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
import nl.ritense.demo.domain.Veld;
import nl.ritense.demo.repository.VeldRepository;
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
 * Integration tests for the {@link VeldResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class VeldResourceIT {

    private static final String ENTITY_API_URL = "/api/velds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VeldRepository veldRepository;

    @Mock
    private VeldRepository veldRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVeldMockMvc;

    private Veld veld;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Veld createEntity(EntityManager em) {
        Veld veld = new Veld();
        return veld;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Veld createUpdatedEntity(EntityManager em) {
        Veld veld = new Veld();
        return veld;
    }

    @BeforeEach
    public void initTest() {
        veld = createEntity(em);
    }

    @Test
    @Transactional
    void createVeld() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Veld
        var returnedVeld = om.readValue(
            restVeldMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(veld)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Veld.class
        );

        // Validate the Veld in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVeldUpdatableFieldsEquals(returnedVeld, getPersistedVeld(returnedVeld));
    }

    @Test
    @Transactional
    void createVeldWithExistingId() throws Exception {
        // Create the Veld with an existing ID
        veld.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVeldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(veld)))
            .andExpect(status().isBadRequest());

        // Validate the Veld in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVelds() throws Exception {
        // Initialize the database
        veldRepository.saveAndFlush(veld);

        // Get all the veldList
        restVeldMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(veld.getId().intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVeldsWithEagerRelationshipsIsEnabled() throws Exception {
        when(veldRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVeldMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(veldRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVeldsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(veldRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVeldMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(veldRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getVeld() throws Exception {
        // Initialize the database
        veldRepository.saveAndFlush(veld);

        // Get the veld
        restVeldMockMvc
            .perform(get(ENTITY_API_URL_ID, veld.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(veld.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingVeld() throws Exception {
        // Get the veld
        restVeldMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVeld() throws Exception {
        // Initialize the database
        veldRepository.saveAndFlush(veld);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the veld
        Veld updatedVeld = veldRepository.findById(veld.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVeld are not directly saved in db
        em.detach(updatedVeld);

        restVeldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVeld.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVeld))
            )
            .andExpect(status().isOk());

        // Validate the Veld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVeldToMatchAllProperties(updatedVeld);
    }

    @Test
    @Transactional
    void putNonExistingVeld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        veld.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVeldMockMvc
            .perform(put(ENTITY_API_URL_ID, veld.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(veld)))
            .andExpect(status().isBadRequest());

        // Validate the Veld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVeld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        veld.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVeldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(veld))
            )
            .andExpect(status().isBadRequest());

        // Validate the Veld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVeld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        veld.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVeldMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(veld)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Veld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVeldWithPatch() throws Exception {
        // Initialize the database
        veldRepository.saveAndFlush(veld);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the veld using partial update
        Veld partialUpdatedVeld = new Veld();
        partialUpdatedVeld.setId(veld.getId());

        restVeldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVeld.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVeld))
            )
            .andExpect(status().isOk());

        // Validate the Veld in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVeldUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVeld, veld), getPersistedVeld(veld));
    }

    @Test
    @Transactional
    void fullUpdateVeldWithPatch() throws Exception {
        // Initialize the database
        veldRepository.saveAndFlush(veld);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the veld using partial update
        Veld partialUpdatedVeld = new Veld();
        partialUpdatedVeld.setId(veld.getId());

        restVeldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVeld.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVeld))
            )
            .andExpect(status().isOk());

        // Validate the Veld in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVeldUpdatableFieldsEquals(partialUpdatedVeld, getPersistedVeld(partialUpdatedVeld));
    }

    @Test
    @Transactional
    void patchNonExistingVeld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        veld.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVeldMockMvc
            .perform(patch(ENTITY_API_URL_ID, veld.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(veld)))
            .andExpect(status().isBadRequest());

        // Validate the Veld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVeld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        veld.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVeldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(veld))
            )
            .andExpect(status().isBadRequest());

        // Validate the Veld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVeld() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        veld.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVeldMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(veld)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Veld in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVeld() throws Exception {
        // Initialize the database
        veldRepository.saveAndFlush(veld);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the veld
        restVeldMockMvc
            .perform(delete(ENTITY_API_URL_ID, veld.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return veldRepository.count();
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

    protected Veld getPersistedVeld(Veld veld) {
        return veldRepository.findById(veld.getId()).orElseThrow();
    }

    protected void assertPersistedVeldToMatchAllProperties(Veld expectedVeld) {
        assertVeldAllPropertiesEquals(expectedVeld, getPersistedVeld(expectedVeld));
    }

    protected void assertPersistedVeldToMatchUpdatableProperties(Veld expectedVeld) {
        assertVeldAllUpdatablePropertiesEquals(expectedVeld, getPersistedVeld(expectedVeld));
    }
}
