package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ArchiefstukAsserts.*;
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
import nl.ritense.demo.domain.Archiefstuk;
import nl.ritense.demo.repository.ArchiefstukRepository;
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
 * Integration tests for the {@link ArchiefstukResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ArchiefstukResourceIT {

    private static final String DEFAULT_BESCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_BESCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_INVENTARISNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_INVENTARISNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_OMVANG = "AAAAAAAAAA";
    private static final String UPDATED_OMVANG = "BBBBBBBBBB";

    private static final String DEFAULT_OPENBAARHEIDSBEPERKING = "AAAAAAAAAA";
    private static final String UPDATED_OPENBAARHEIDSBEPERKING = "BBBBBBBBBB";

    private static final String DEFAULT_TREFWOORDEN = "AAAAAAAAAA";
    private static final String UPDATED_TREFWOORDEN = "BBBBBBBBBB";

    private static final String DEFAULT_UITERLIJKEVORM = "AAAAAAAAAA";
    private static final String UPDATED_UITERLIJKEVORM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/archiefstuks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ArchiefstukRepository archiefstukRepository;

    @Mock
    private ArchiefstukRepository archiefstukRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArchiefstukMockMvc;

    private Archiefstuk archiefstuk;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Archiefstuk createEntity(EntityManager em) {
        Archiefstuk archiefstuk = new Archiefstuk()
            .beschrijving(DEFAULT_BESCHRIJVING)
            .inventarisnummer(DEFAULT_INVENTARISNUMMER)
            .omvang(DEFAULT_OMVANG)
            .openbaarheidsbeperking(DEFAULT_OPENBAARHEIDSBEPERKING)
            .trefwoorden(DEFAULT_TREFWOORDEN)
            .uiterlijkevorm(DEFAULT_UITERLIJKEVORM);
        return archiefstuk;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Archiefstuk createUpdatedEntity(EntityManager em) {
        Archiefstuk archiefstuk = new Archiefstuk()
            .beschrijving(UPDATED_BESCHRIJVING)
            .inventarisnummer(UPDATED_INVENTARISNUMMER)
            .omvang(UPDATED_OMVANG)
            .openbaarheidsbeperking(UPDATED_OPENBAARHEIDSBEPERKING)
            .trefwoorden(UPDATED_TREFWOORDEN)
            .uiterlijkevorm(UPDATED_UITERLIJKEVORM);
        return archiefstuk;
    }

    @BeforeEach
    public void initTest() {
        archiefstuk = createEntity(em);
    }

    @Test
    @Transactional
    void createArchiefstuk() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Archiefstuk
        var returnedArchiefstuk = om.readValue(
            restArchiefstukMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archiefstuk)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Archiefstuk.class
        );

        // Validate the Archiefstuk in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertArchiefstukUpdatableFieldsEquals(returnedArchiefstuk, getPersistedArchiefstuk(returnedArchiefstuk));
    }

    @Test
    @Transactional
    void createArchiefstukWithExistingId() throws Exception {
        // Create the Archiefstuk with an existing ID
        archiefstuk.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArchiefstukMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archiefstuk)))
            .andExpect(status().isBadRequest());

        // Validate the Archiefstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArchiefstuks() throws Exception {
        // Initialize the database
        archiefstukRepository.saveAndFlush(archiefstuk);

        // Get all the archiefstukList
        restArchiefstukMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(archiefstuk.getId().intValue())))
            .andExpect(jsonPath("$.[*].beschrijving").value(hasItem(DEFAULT_BESCHRIJVING)))
            .andExpect(jsonPath("$.[*].inventarisnummer").value(hasItem(DEFAULT_INVENTARISNUMMER)))
            .andExpect(jsonPath("$.[*].omvang").value(hasItem(DEFAULT_OMVANG)))
            .andExpect(jsonPath("$.[*].openbaarheidsbeperking").value(hasItem(DEFAULT_OPENBAARHEIDSBEPERKING)))
            .andExpect(jsonPath("$.[*].trefwoorden").value(hasItem(DEFAULT_TREFWOORDEN)))
            .andExpect(jsonPath("$.[*].uiterlijkevorm").value(hasItem(DEFAULT_UITERLIJKEVORM)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArchiefstuksWithEagerRelationshipsIsEnabled() throws Exception {
        when(archiefstukRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArchiefstukMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(archiefstukRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArchiefstuksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(archiefstukRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArchiefstukMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(archiefstukRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getArchiefstuk() throws Exception {
        // Initialize the database
        archiefstukRepository.saveAndFlush(archiefstuk);

        // Get the archiefstuk
        restArchiefstukMockMvc
            .perform(get(ENTITY_API_URL_ID, archiefstuk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(archiefstuk.getId().intValue()))
            .andExpect(jsonPath("$.beschrijving").value(DEFAULT_BESCHRIJVING))
            .andExpect(jsonPath("$.inventarisnummer").value(DEFAULT_INVENTARISNUMMER))
            .andExpect(jsonPath("$.omvang").value(DEFAULT_OMVANG))
            .andExpect(jsonPath("$.openbaarheidsbeperking").value(DEFAULT_OPENBAARHEIDSBEPERKING))
            .andExpect(jsonPath("$.trefwoorden").value(DEFAULT_TREFWOORDEN))
            .andExpect(jsonPath("$.uiterlijkevorm").value(DEFAULT_UITERLIJKEVORM));
    }

    @Test
    @Transactional
    void getNonExistingArchiefstuk() throws Exception {
        // Get the archiefstuk
        restArchiefstukMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArchiefstuk() throws Exception {
        // Initialize the database
        archiefstukRepository.saveAndFlush(archiefstuk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the archiefstuk
        Archiefstuk updatedArchiefstuk = archiefstukRepository.findById(archiefstuk.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArchiefstuk are not directly saved in db
        em.detach(updatedArchiefstuk);
        updatedArchiefstuk
            .beschrijving(UPDATED_BESCHRIJVING)
            .inventarisnummer(UPDATED_INVENTARISNUMMER)
            .omvang(UPDATED_OMVANG)
            .openbaarheidsbeperking(UPDATED_OPENBAARHEIDSBEPERKING)
            .trefwoorden(UPDATED_TREFWOORDEN)
            .uiterlijkevorm(UPDATED_UITERLIJKEVORM);

        restArchiefstukMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedArchiefstuk.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedArchiefstuk))
            )
            .andExpect(status().isOk());

        // Validate the Archiefstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedArchiefstukToMatchAllProperties(updatedArchiefstuk);
    }

    @Test
    @Transactional
    void putNonExistingArchiefstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archiefstuk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArchiefstukMockMvc
            .perform(
                put(ENTITY_API_URL_ID, archiefstuk.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(archiefstuk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archiefstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArchiefstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archiefstuk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchiefstukMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(archiefstuk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archiefstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArchiefstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archiefstuk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchiefstukMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archiefstuk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Archiefstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArchiefstukWithPatch() throws Exception {
        // Initialize the database
        archiefstukRepository.saveAndFlush(archiefstuk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the archiefstuk using partial update
        Archiefstuk partialUpdatedArchiefstuk = new Archiefstuk();
        partialUpdatedArchiefstuk.setId(archiefstuk.getId());

        partialUpdatedArchiefstuk
            .beschrijving(UPDATED_BESCHRIJVING)
            .inventarisnummer(UPDATED_INVENTARISNUMMER)
            .omvang(UPDATED_OMVANG)
            .openbaarheidsbeperking(UPDATED_OPENBAARHEIDSBEPERKING);

        restArchiefstukMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArchiefstuk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArchiefstuk))
            )
            .andExpect(status().isOk());

        // Validate the Archiefstuk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArchiefstukUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedArchiefstuk, archiefstuk),
            getPersistedArchiefstuk(archiefstuk)
        );
    }

    @Test
    @Transactional
    void fullUpdateArchiefstukWithPatch() throws Exception {
        // Initialize the database
        archiefstukRepository.saveAndFlush(archiefstuk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the archiefstuk using partial update
        Archiefstuk partialUpdatedArchiefstuk = new Archiefstuk();
        partialUpdatedArchiefstuk.setId(archiefstuk.getId());

        partialUpdatedArchiefstuk
            .beschrijving(UPDATED_BESCHRIJVING)
            .inventarisnummer(UPDATED_INVENTARISNUMMER)
            .omvang(UPDATED_OMVANG)
            .openbaarheidsbeperking(UPDATED_OPENBAARHEIDSBEPERKING)
            .trefwoorden(UPDATED_TREFWOORDEN)
            .uiterlijkevorm(UPDATED_UITERLIJKEVORM);

        restArchiefstukMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArchiefstuk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArchiefstuk))
            )
            .andExpect(status().isOk());

        // Validate the Archiefstuk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArchiefstukUpdatableFieldsEquals(partialUpdatedArchiefstuk, getPersistedArchiefstuk(partialUpdatedArchiefstuk));
    }

    @Test
    @Transactional
    void patchNonExistingArchiefstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archiefstuk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArchiefstukMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, archiefstuk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(archiefstuk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archiefstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArchiefstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archiefstuk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchiefstukMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(archiefstuk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archiefstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArchiefstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archiefstuk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchiefstukMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(archiefstuk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Archiefstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArchiefstuk() throws Exception {
        // Initialize the database
        archiefstukRepository.saveAndFlush(archiefstuk);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the archiefstuk
        restArchiefstukMockMvc
            .perform(delete(ENTITY_API_URL_ID, archiefstuk.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return archiefstukRepository.count();
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

    protected Archiefstuk getPersistedArchiefstuk(Archiefstuk archiefstuk) {
        return archiefstukRepository.findById(archiefstuk.getId()).orElseThrow();
    }

    protected void assertPersistedArchiefstukToMatchAllProperties(Archiefstuk expectedArchiefstuk) {
        assertArchiefstukAllPropertiesEquals(expectedArchiefstuk, getPersistedArchiefstuk(expectedArchiefstuk));
    }

    protected void assertPersistedArchiefstukToMatchUpdatableProperties(Archiefstuk expectedArchiefstuk) {
        assertArchiefstukAllUpdatablePropertiesEquals(expectedArchiefstuk, getPersistedArchiefstuk(expectedArchiefstuk));
    }
}
