package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OmgevingswaarderegelAsserts.*;
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
import nl.ritense.demo.domain.Omgevingsnorm;
import nl.ritense.demo.domain.Omgevingswaarde;
import nl.ritense.demo.domain.Omgevingswaarderegel;
import nl.ritense.demo.repository.OmgevingswaarderegelRepository;
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
 * Integration tests for the {@link OmgevingswaarderegelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OmgevingswaarderegelResourceIT {

    private static final String DEFAULT_GROEP = "AAAAAAAAAA";
    private static final String UPDATED_GROEP = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/omgevingswaarderegels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OmgevingswaarderegelRepository omgevingswaarderegelRepository;

    @Mock
    private OmgevingswaarderegelRepository omgevingswaarderegelRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOmgevingswaarderegelMockMvc;

    private Omgevingswaarderegel omgevingswaarderegel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Omgevingswaarderegel createEntity(EntityManager em) {
        Omgevingswaarderegel omgevingswaarderegel = new Omgevingswaarderegel().groep(DEFAULT_GROEP).naam(DEFAULT_NAAM);
        // Add required entity
        Omgevingsnorm omgevingsnorm;
        if (TestUtil.findAll(em, Omgevingsnorm.class).isEmpty()) {
            omgevingsnorm = OmgevingsnormResourceIT.createEntity(em);
            em.persist(omgevingsnorm);
            em.flush();
        } else {
            omgevingsnorm = TestUtil.findAll(em, Omgevingsnorm.class).get(0);
        }
        omgevingswaarderegel.getBeschrijftOmgevingsnorms().add(omgevingsnorm);
        // Add required entity
        Omgevingswaarde omgevingswaarde;
        if (TestUtil.findAll(em, Omgevingswaarde.class).isEmpty()) {
            omgevingswaarde = OmgevingswaardeResourceIT.createEntity(em);
            em.persist(omgevingswaarde);
            em.flush();
        } else {
            omgevingswaarde = TestUtil.findAll(em, Omgevingswaarde.class).get(0);
        }
        omgevingswaarderegel.getBeschrijftOmgevingswaardes().add(omgevingswaarde);
        return omgevingswaarderegel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Omgevingswaarderegel createUpdatedEntity(EntityManager em) {
        Omgevingswaarderegel omgevingswaarderegel = new Omgevingswaarderegel().groep(UPDATED_GROEP).naam(UPDATED_NAAM);
        // Add required entity
        Omgevingsnorm omgevingsnorm;
        if (TestUtil.findAll(em, Omgevingsnorm.class).isEmpty()) {
            omgevingsnorm = OmgevingsnormResourceIT.createUpdatedEntity(em);
            em.persist(omgevingsnorm);
            em.flush();
        } else {
            omgevingsnorm = TestUtil.findAll(em, Omgevingsnorm.class).get(0);
        }
        omgevingswaarderegel.getBeschrijftOmgevingsnorms().add(omgevingsnorm);
        // Add required entity
        Omgevingswaarde omgevingswaarde;
        if (TestUtil.findAll(em, Omgevingswaarde.class).isEmpty()) {
            omgevingswaarde = OmgevingswaardeResourceIT.createUpdatedEntity(em);
            em.persist(omgevingswaarde);
            em.flush();
        } else {
            omgevingswaarde = TestUtil.findAll(em, Omgevingswaarde.class).get(0);
        }
        omgevingswaarderegel.getBeschrijftOmgevingswaardes().add(omgevingswaarde);
        return omgevingswaarderegel;
    }

    @BeforeEach
    public void initTest() {
        omgevingswaarderegel = createEntity(em);
    }

    @Test
    @Transactional
    void createOmgevingswaarderegel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Omgevingswaarderegel
        var returnedOmgevingswaarderegel = om.readValue(
            restOmgevingswaarderegelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omgevingswaarderegel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Omgevingswaarderegel.class
        );

        // Validate the Omgevingswaarderegel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOmgevingswaarderegelUpdatableFieldsEquals(
            returnedOmgevingswaarderegel,
            getPersistedOmgevingswaarderegel(returnedOmgevingswaarderegel)
        );
    }

    @Test
    @Transactional
    void createOmgevingswaarderegelWithExistingId() throws Exception {
        // Create the Omgevingswaarderegel with an existing ID
        omgevingswaarderegel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOmgevingswaarderegelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omgevingswaarderegel)))
            .andExpect(status().isBadRequest());

        // Validate the Omgevingswaarderegel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOmgevingswaarderegels() throws Exception {
        // Initialize the database
        omgevingswaarderegelRepository.saveAndFlush(omgevingswaarderegel);

        // Get all the omgevingswaarderegelList
        restOmgevingswaarderegelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(omgevingswaarderegel.getId().intValue())))
            .andExpect(jsonPath("$.[*].groep").value(hasItem(DEFAULT_GROEP)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOmgevingswaarderegelsWithEagerRelationshipsIsEnabled() throws Exception {
        when(omgevingswaarderegelRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOmgevingswaarderegelMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(omgevingswaarderegelRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOmgevingswaarderegelsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(omgevingswaarderegelRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOmgevingswaarderegelMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(omgevingswaarderegelRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getOmgevingswaarderegel() throws Exception {
        // Initialize the database
        omgevingswaarderegelRepository.saveAndFlush(omgevingswaarderegel);

        // Get the omgevingswaarderegel
        restOmgevingswaarderegelMockMvc
            .perform(get(ENTITY_API_URL_ID, omgevingswaarderegel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(omgevingswaarderegel.getId().intValue()))
            .andExpect(jsonPath("$.groep").value(DEFAULT_GROEP))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingOmgevingswaarderegel() throws Exception {
        // Get the omgevingswaarderegel
        restOmgevingswaarderegelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOmgevingswaarderegel() throws Exception {
        // Initialize the database
        omgevingswaarderegelRepository.saveAndFlush(omgevingswaarderegel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the omgevingswaarderegel
        Omgevingswaarderegel updatedOmgevingswaarderegel = omgevingswaarderegelRepository
            .findById(omgevingswaarderegel.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOmgevingswaarderegel are not directly saved in db
        em.detach(updatedOmgevingswaarderegel);
        updatedOmgevingswaarderegel.groep(UPDATED_GROEP).naam(UPDATED_NAAM);

        restOmgevingswaarderegelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOmgevingswaarderegel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOmgevingswaarderegel))
            )
            .andExpect(status().isOk());

        // Validate the Omgevingswaarderegel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOmgevingswaarderegelToMatchAllProperties(updatedOmgevingswaarderegel);
    }

    @Test
    @Transactional
    void putNonExistingOmgevingswaarderegel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingswaarderegel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOmgevingswaarderegelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, omgevingswaarderegel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(omgevingswaarderegel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omgevingswaarderegel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOmgevingswaarderegel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingswaarderegel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmgevingswaarderegelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(omgevingswaarderegel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omgevingswaarderegel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOmgevingswaarderegel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingswaarderegel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmgevingswaarderegelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(omgevingswaarderegel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Omgevingswaarderegel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOmgevingswaarderegelWithPatch() throws Exception {
        // Initialize the database
        omgevingswaarderegelRepository.saveAndFlush(omgevingswaarderegel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the omgevingswaarderegel using partial update
        Omgevingswaarderegel partialUpdatedOmgevingswaarderegel = new Omgevingswaarderegel();
        partialUpdatedOmgevingswaarderegel.setId(omgevingswaarderegel.getId());

        partialUpdatedOmgevingswaarderegel.groep(UPDATED_GROEP).naam(UPDATED_NAAM);

        restOmgevingswaarderegelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOmgevingswaarderegel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOmgevingswaarderegel))
            )
            .andExpect(status().isOk());

        // Validate the Omgevingswaarderegel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOmgevingswaarderegelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOmgevingswaarderegel, omgevingswaarderegel),
            getPersistedOmgevingswaarderegel(omgevingswaarderegel)
        );
    }

    @Test
    @Transactional
    void fullUpdateOmgevingswaarderegelWithPatch() throws Exception {
        // Initialize the database
        omgevingswaarderegelRepository.saveAndFlush(omgevingswaarderegel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the omgevingswaarderegel using partial update
        Omgevingswaarderegel partialUpdatedOmgevingswaarderegel = new Omgevingswaarderegel();
        partialUpdatedOmgevingswaarderegel.setId(omgevingswaarderegel.getId());

        partialUpdatedOmgevingswaarderegel.groep(UPDATED_GROEP).naam(UPDATED_NAAM);

        restOmgevingswaarderegelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOmgevingswaarderegel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOmgevingswaarderegel))
            )
            .andExpect(status().isOk());

        // Validate the Omgevingswaarderegel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOmgevingswaarderegelUpdatableFieldsEquals(
            partialUpdatedOmgevingswaarderegel,
            getPersistedOmgevingswaarderegel(partialUpdatedOmgevingswaarderegel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOmgevingswaarderegel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingswaarderegel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOmgevingswaarderegelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, omgevingswaarderegel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(omgevingswaarderegel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omgevingswaarderegel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOmgevingswaarderegel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingswaarderegel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmgevingswaarderegelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(omgevingswaarderegel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Omgevingswaarderegel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOmgevingswaarderegel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        omgevingswaarderegel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOmgevingswaarderegelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(omgevingswaarderegel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Omgevingswaarderegel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOmgevingswaarderegel() throws Exception {
        // Initialize the database
        omgevingswaarderegelRepository.saveAndFlush(omgevingswaarderegel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the omgevingswaarderegel
        restOmgevingswaarderegelMockMvc
            .perform(delete(ENTITY_API_URL_ID, omgevingswaarderegel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return omgevingswaarderegelRepository.count();
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

    protected Omgevingswaarderegel getPersistedOmgevingswaarderegel(Omgevingswaarderegel omgevingswaarderegel) {
        return omgevingswaarderegelRepository.findById(omgevingswaarderegel.getId()).orElseThrow();
    }

    protected void assertPersistedOmgevingswaarderegelToMatchAllProperties(Omgevingswaarderegel expectedOmgevingswaarderegel) {
        assertOmgevingswaarderegelAllPropertiesEquals(
            expectedOmgevingswaarderegel,
            getPersistedOmgevingswaarderegel(expectedOmgevingswaarderegel)
        );
    }

    protected void assertPersistedOmgevingswaarderegelToMatchUpdatableProperties(Omgevingswaarderegel expectedOmgevingswaarderegel) {
        assertOmgevingswaarderegelAllUpdatablePropertiesEquals(
            expectedOmgevingswaarderegel,
            getPersistedOmgevingswaarderegel(expectedOmgevingswaarderegel)
        );
    }
}
