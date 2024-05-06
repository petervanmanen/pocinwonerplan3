package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RaadsstukAsserts.*;
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
import nl.ritense.demo.domain.Indiener;
import nl.ritense.demo.domain.Raadsstuk;
import nl.ritense.demo.repository.RaadsstukRepository;
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
 * Integration tests for the {@link RaadsstukResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RaadsstukResourceIT {

    private static final String DEFAULT_BESLOTEN = "AAAAAAAAAA";
    private static final String UPDATED_BESLOTEN = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEXPIRATIE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEXPIRATIE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMPUBLICATIE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMPUBLICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMREGISTRATIE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMREGISTRATIE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPERAADSSTUK = "AAAAAAAAAA";
    private static final String UPDATED_TYPERAADSSTUK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/raadsstuks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RaadsstukRepository raadsstukRepository;

    @Mock
    private RaadsstukRepository raadsstukRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRaadsstukMockMvc;

    private Raadsstuk raadsstuk;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Raadsstuk createEntity(EntityManager em) {
        Raadsstuk raadsstuk = new Raadsstuk()
            .besloten(DEFAULT_BESLOTEN)
            .datumexpiratie(DEFAULT_DATUMEXPIRATIE)
            .datumpublicatie(DEFAULT_DATUMPUBLICATIE)
            .datumregistratie(DEFAULT_DATUMREGISTRATIE)
            .typeraadsstuk(DEFAULT_TYPERAADSSTUK);
        // Add required entity
        Indiener indiener;
        if (TestUtil.findAll(em, Indiener.class).isEmpty()) {
            indiener = IndienerResourceIT.createEntity(em);
            em.persist(indiener);
            em.flush();
        } else {
            indiener = TestUtil.findAll(em, Indiener.class).get(0);
        }
        raadsstuk.getHeeftIndieners().add(indiener);
        return raadsstuk;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Raadsstuk createUpdatedEntity(EntityManager em) {
        Raadsstuk raadsstuk = new Raadsstuk()
            .besloten(UPDATED_BESLOTEN)
            .datumexpiratie(UPDATED_DATUMEXPIRATIE)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumregistratie(UPDATED_DATUMREGISTRATIE)
            .typeraadsstuk(UPDATED_TYPERAADSSTUK);
        // Add required entity
        Indiener indiener;
        if (TestUtil.findAll(em, Indiener.class).isEmpty()) {
            indiener = IndienerResourceIT.createUpdatedEntity(em);
            em.persist(indiener);
            em.flush();
        } else {
            indiener = TestUtil.findAll(em, Indiener.class).get(0);
        }
        raadsstuk.getHeeftIndieners().add(indiener);
        return raadsstuk;
    }

    @BeforeEach
    public void initTest() {
        raadsstuk = createEntity(em);
    }

    @Test
    @Transactional
    void createRaadsstuk() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Raadsstuk
        var returnedRaadsstuk = om.readValue(
            restRaadsstukMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(raadsstuk)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Raadsstuk.class
        );

        // Validate the Raadsstuk in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRaadsstukUpdatableFieldsEquals(returnedRaadsstuk, getPersistedRaadsstuk(returnedRaadsstuk));
    }

    @Test
    @Transactional
    void createRaadsstukWithExistingId() throws Exception {
        // Create the Raadsstuk with an existing ID
        raadsstuk.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRaadsstukMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(raadsstuk)))
            .andExpect(status().isBadRequest());

        // Validate the Raadsstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRaadsstuks() throws Exception {
        // Initialize the database
        raadsstukRepository.saveAndFlush(raadsstuk);

        // Get all the raadsstukList
        restRaadsstukMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raadsstuk.getId().intValue())))
            .andExpect(jsonPath("$.[*].besloten").value(hasItem(DEFAULT_BESLOTEN)))
            .andExpect(jsonPath("$.[*].datumexpiratie").value(hasItem(DEFAULT_DATUMEXPIRATIE)))
            .andExpect(jsonPath("$.[*].datumpublicatie").value(hasItem(DEFAULT_DATUMPUBLICATIE)))
            .andExpect(jsonPath("$.[*].datumregistratie").value(hasItem(DEFAULT_DATUMREGISTRATIE)))
            .andExpect(jsonPath("$.[*].typeraadsstuk").value(hasItem(DEFAULT_TYPERAADSSTUK)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRaadsstuksWithEagerRelationshipsIsEnabled() throws Exception {
        when(raadsstukRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRaadsstukMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(raadsstukRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRaadsstuksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(raadsstukRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRaadsstukMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(raadsstukRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRaadsstuk() throws Exception {
        // Initialize the database
        raadsstukRepository.saveAndFlush(raadsstuk);

        // Get the raadsstuk
        restRaadsstukMockMvc
            .perform(get(ENTITY_API_URL_ID, raadsstuk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(raadsstuk.getId().intValue()))
            .andExpect(jsonPath("$.besloten").value(DEFAULT_BESLOTEN))
            .andExpect(jsonPath("$.datumexpiratie").value(DEFAULT_DATUMEXPIRATIE))
            .andExpect(jsonPath("$.datumpublicatie").value(DEFAULT_DATUMPUBLICATIE))
            .andExpect(jsonPath("$.datumregistratie").value(DEFAULT_DATUMREGISTRATIE))
            .andExpect(jsonPath("$.typeraadsstuk").value(DEFAULT_TYPERAADSSTUK));
    }

    @Test
    @Transactional
    void getNonExistingRaadsstuk() throws Exception {
        // Get the raadsstuk
        restRaadsstukMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRaadsstuk() throws Exception {
        // Initialize the database
        raadsstukRepository.saveAndFlush(raadsstuk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the raadsstuk
        Raadsstuk updatedRaadsstuk = raadsstukRepository.findById(raadsstuk.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRaadsstuk are not directly saved in db
        em.detach(updatedRaadsstuk);
        updatedRaadsstuk
            .besloten(UPDATED_BESLOTEN)
            .datumexpiratie(UPDATED_DATUMEXPIRATIE)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumregistratie(UPDATED_DATUMREGISTRATIE)
            .typeraadsstuk(UPDATED_TYPERAADSSTUK);

        restRaadsstukMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRaadsstuk.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRaadsstuk))
            )
            .andExpect(status().isOk());

        // Validate the Raadsstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRaadsstukToMatchAllProperties(updatedRaadsstuk);
    }

    @Test
    @Transactional
    void putNonExistingRaadsstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadsstuk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRaadsstukMockMvc
            .perform(
                put(ENTITY_API_URL_ID, raadsstuk.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(raadsstuk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Raadsstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRaadsstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadsstuk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaadsstukMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(raadsstuk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Raadsstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRaadsstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadsstuk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaadsstukMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(raadsstuk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Raadsstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRaadsstukWithPatch() throws Exception {
        // Initialize the database
        raadsstukRepository.saveAndFlush(raadsstuk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the raadsstuk using partial update
        Raadsstuk partialUpdatedRaadsstuk = new Raadsstuk();
        partialUpdatedRaadsstuk.setId(raadsstuk.getId());

        partialUpdatedRaadsstuk
            .besloten(UPDATED_BESLOTEN)
            .datumexpiratie(UPDATED_DATUMEXPIRATIE)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumregistratie(UPDATED_DATUMREGISTRATIE);

        restRaadsstukMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRaadsstuk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRaadsstuk))
            )
            .andExpect(status().isOk());

        // Validate the Raadsstuk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRaadsstukUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRaadsstuk, raadsstuk),
            getPersistedRaadsstuk(raadsstuk)
        );
    }

    @Test
    @Transactional
    void fullUpdateRaadsstukWithPatch() throws Exception {
        // Initialize the database
        raadsstukRepository.saveAndFlush(raadsstuk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the raadsstuk using partial update
        Raadsstuk partialUpdatedRaadsstuk = new Raadsstuk();
        partialUpdatedRaadsstuk.setId(raadsstuk.getId());

        partialUpdatedRaadsstuk
            .besloten(UPDATED_BESLOTEN)
            .datumexpiratie(UPDATED_DATUMEXPIRATIE)
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumregistratie(UPDATED_DATUMREGISTRATIE)
            .typeraadsstuk(UPDATED_TYPERAADSSTUK);

        restRaadsstukMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRaadsstuk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRaadsstuk))
            )
            .andExpect(status().isOk());

        // Validate the Raadsstuk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRaadsstukUpdatableFieldsEquals(partialUpdatedRaadsstuk, getPersistedRaadsstuk(partialUpdatedRaadsstuk));
    }

    @Test
    @Transactional
    void patchNonExistingRaadsstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadsstuk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRaadsstukMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, raadsstuk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(raadsstuk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Raadsstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRaadsstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadsstuk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaadsstukMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(raadsstuk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Raadsstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRaadsstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        raadsstuk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaadsstukMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(raadsstuk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Raadsstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRaadsstuk() throws Exception {
        // Initialize the database
        raadsstukRepository.saveAndFlush(raadsstuk);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the raadsstuk
        restRaadsstukMockMvc
            .perform(delete(ENTITY_API_URL_ID, raadsstuk.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return raadsstukRepository.count();
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

    protected Raadsstuk getPersistedRaadsstuk(Raadsstuk raadsstuk) {
        return raadsstukRepository.findById(raadsstuk.getId()).orElseThrow();
    }

    protected void assertPersistedRaadsstukToMatchAllProperties(Raadsstuk expectedRaadsstuk) {
        assertRaadsstukAllPropertiesEquals(expectedRaadsstuk, getPersistedRaadsstuk(expectedRaadsstuk));
    }

    protected void assertPersistedRaadsstukToMatchUpdatableProperties(Raadsstuk expectedRaadsstuk) {
        assertRaadsstukAllUpdatablePropertiesEquals(expectedRaadsstuk, getPersistedRaadsstuk(expectedRaadsstuk));
    }
}
