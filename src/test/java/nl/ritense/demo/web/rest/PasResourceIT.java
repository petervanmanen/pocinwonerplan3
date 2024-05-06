package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PasAsserts.*;
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
import nl.ritense.demo.domain.Pas;
import nl.ritense.demo.repository.PasRepository;
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
 * Integration tests for the {@link PasResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PasResourceIT {

    private static final String DEFAULT_ADRESAANDUIDING = "AAAAAAAAAA";
    private static final String UPDATED_ADRESAANDUIDING = "BBBBBBBBBB";

    private static final String DEFAULT_PASNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_PASNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PasRepository pasRepository;

    @Mock
    private PasRepository pasRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPasMockMvc;

    private Pas pas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pas createEntity(EntityManager em) {
        Pas pas = new Pas().adresaanduiding(DEFAULT_ADRESAANDUIDING).pasnummer(DEFAULT_PASNUMMER);
        return pas;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pas createUpdatedEntity(EntityManager em) {
        Pas pas = new Pas().adresaanduiding(UPDATED_ADRESAANDUIDING).pasnummer(UPDATED_PASNUMMER);
        return pas;
    }

    @BeforeEach
    public void initTest() {
        pas = createEntity(em);
    }

    @Test
    @Transactional
    void createPas() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Pas
        var returnedPas = om.readValue(
            restPasMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pas)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Pas.class
        );

        // Validate the Pas in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPasUpdatableFieldsEquals(returnedPas, getPersistedPas(returnedPas));
    }

    @Test
    @Transactional
    void createPasWithExistingId() throws Exception {
        // Create the Pas with an existing ID
        pas.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pas)))
            .andExpect(status().isBadRequest());

        // Validate the Pas in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPas() throws Exception {
        // Initialize the database
        pasRepository.saveAndFlush(pas);

        // Get all the pasList
        restPasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pas.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresaanduiding").value(hasItem(DEFAULT_ADRESAANDUIDING)))
            .andExpect(jsonPath("$.[*].pasnummer").value(hasItem(DEFAULT_PASNUMMER)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPasWithEagerRelationshipsIsEnabled() throws Exception {
        when(pasRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPasMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(pasRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(pasRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPasMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(pasRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPas() throws Exception {
        // Initialize the database
        pasRepository.saveAndFlush(pas);

        // Get the pas
        restPasMockMvc
            .perform(get(ENTITY_API_URL_ID, pas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pas.getId().intValue()))
            .andExpect(jsonPath("$.adresaanduiding").value(DEFAULT_ADRESAANDUIDING))
            .andExpect(jsonPath("$.pasnummer").value(DEFAULT_PASNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingPas() throws Exception {
        // Get the pas
        restPasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPas() throws Exception {
        // Initialize the database
        pasRepository.saveAndFlush(pas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pas
        Pas updatedPas = pasRepository.findById(pas.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPas are not directly saved in db
        em.detach(updatedPas);
        updatedPas.adresaanduiding(UPDATED_ADRESAANDUIDING).pasnummer(UPDATED_PASNUMMER);

        restPasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPas.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(updatedPas))
            )
            .andExpect(status().isOk());

        // Validate the Pas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPasToMatchAllProperties(updatedPas);
    }

    @Test
    @Transactional
    void putNonExistingPas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pas.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPasMockMvc
            .perform(put(ENTITY_API_URL_ID, pas.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pas)))
            .andExpect(status().isBadRequest());

        // Validate the Pas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPasMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pas)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePasWithPatch() throws Exception {
        // Initialize the database
        pasRepository.saveAndFlush(pas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pas using partial update
        Pas partialUpdatedPas = new Pas();
        partialUpdatedPas.setId(pas.getId());

        partialUpdatedPas.pasnummer(UPDATED_PASNUMMER);

        restPasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPas))
            )
            .andExpect(status().isOk());

        // Validate the Pas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPasUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPas, pas), getPersistedPas(pas));
    }

    @Test
    @Transactional
    void fullUpdatePasWithPatch() throws Exception {
        // Initialize the database
        pasRepository.saveAndFlush(pas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pas using partial update
        Pas partialUpdatedPas = new Pas();
        partialUpdatedPas.setId(pas.getId());

        partialUpdatedPas.adresaanduiding(UPDATED_ADRESAANDUIDING).pasnummer(UPDATED_PASNUMMER);

        restPasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPas))
            )
            .andExpect(status().isOk());

        // Validate the Pas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPasUpdatableFieldsEquals(partialUpdatedPas, getPersistedPas(partialUpdatedPas));
    }

    @Test
    @Transactional
    void patchNonExistingPas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pas.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPasMockMvc
            .perform(patch(ENTITY_API_URL_ID, pas.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pas)))
            .andExpect(status().isBadRequest());

        // Validate the Pas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPasMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pas)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePas() throws Exception {
        // Initialize the database
        pasRepository.saveAndFlush(pas);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pas
        restPasMockMvc.perform(delete(ENTITY_API_URL_ID, pas.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pasRepository.count();
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

    protected Pas getPersistedPas(Pas pas) {
        return pasRepository.findById(pas.getId()).orElseThrow();
    }

    protected void assertPersistedPasToMatchAllProperties(Pas expectedPas) {
        assertPasAllPropertiesEquals(expectedPas, getPersistedPas(expectedPas));
    }

    protected void assertPersistedPasToMatchUpdatableProperties(Pas expectedPas) {
        assertPasAllUpdatablePropertiesEquals(expectedPas, getPersistedPas(expectedPas));
    }
}
