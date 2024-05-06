package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AreaalAsserts.*;
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
import nl.ritense.demo.domain.Areaal;
import nl.ritense.demo.domain.Schouwronde;
import nl.ritense.demo.repository.AreaalRepository;
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
 * Integration tests for the {@link AreaalResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AreaalResourceIT {

    private static final String DEFAULT_GEOMETRIE = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/areaals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AreaalRepository areaalRepository;

    @Mock
    private AreaalRepository areaalRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAreaalMockMvc;

    private Areaal areaal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Areaal createEntity(EntityManager em) {
        Areaal areaal = new Areaal().geometrie(DEFAULT_GEOMETRIE);
        // Add required entity
        Schouwronde schouwronde;
        if (TestUtil.findAll(em, Schouwronde.class).isEmpty()) {
            schouwronde = SchouwrondeResourceIT.createEntity(em);
            em.persist(schouwronde);
            em.flush();
        } else {
            schouwronde = TestUtil.findAll(em, Schouwronde.class).get(0);
        }
        areaal.getBinnenSchouwrondes().add(schouwronde);
        return areaal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Areaal createUpdatedEntity(EntityManager em) {
        Areaal areaal = new Areaal().geometrie(UPDATED_GEOMETRIE);
        // Add required entity
        Schouwronde schouwronde;
        if (TestUtil.findAll(em, Schouwronde.class).isEmpty()) {
            schouwronde = SchouwrondeResourceIT.createUpdatedEntity(em);
            em.persist(schouwronde);
            em.flush();
        } else {
            schouwronde = TestUtil.findAll(em, Schouwronde.class).get(0);
        }
        areaal.getBinnenSchouwrondes().add(schouwronde);
        return areaal;
    }

    @BeforeEach
    public void initTest() {
        areaal = createEntity(em);
    }

    @Test
    @Transactional
    void createAreaal() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Areaal
        var returnedAreaal = om.readValue(
            restAreaalMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(areaal)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Areaal.class
        );

        // Validate the Areaal in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAreaalUpdatableFieldsEquals(returnedAreaal, getPersistedAreaal(returnedAreaal));
    }

    @Test
    @Transactional
    void createAreaalWithExistingId() throws Exception {
        // Create the Areaal with an existing ID
        areaal.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(areaal)))
            .andExpect(status().isBadRequest());

        // Validate the Areaal in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAreaals() throws Exception {
        // Initialize the database
        areaalRepository.saveAndFlush(areaal);

        // Get all the areaalList
        restAreaalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(areaal.getId().intValue())))
            .andExpect(jsonPath("$.[*].geometrie").value(hasItem(DEFAULT_GEOMETRIE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAreaalsWithEagerRelationshipsIsEnabled() throws Exception {
        when(areaalRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAreaalMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(areaalRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAreaalsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(areaalRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAreaalMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(areaalRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAreaal() throws Exception {
        // Initialize the database
        areaalRepository.saveAndFlush(areaal);

        // Get the areaal
        restAreaalMockMvc
            .perform(get(ENTITY_API_URL_ID, areaal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(areaal.getId().intValue()))
            .andExpect(jsonPath("$.geometrie").value(DEFAULT_GEOMETRIE));
    }

    @Test
    @Transactional
    void getNonExistingAreaal() throws Exception {
        // Get the areaal
        restAreaalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAreaal() throws Exception {
        // Initialize the database
        areaalRepository.saveAndFlush(areaal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the areaal
        Areaal updatedAreaal = areaalRepository.findById(areaal.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAreaal are not directly saved in db
        em.detach(updatedAreaal);
        updatedAreaal.geometrie(UPDATED_GEOMETRIE);

        restAreaalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAreaal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAreaal))
            )
            .andExpect(status().isOk());

        // Validate the Areaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAreaalToMatchAllProperties(updatedAreaal);
    }

    @Test
    @Transactional
    void putNonExistingAreaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaal.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaalMockMvc
            .perform(put(ENTITY_API_URL_ID, areaal.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(areaal)))
            .andExpect(status().isBadRequest());

        // Validate the Areaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAreaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(areaal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Areaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAreaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(areaal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Areaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAreaalWithPatch() throws Exception {
        // Initialize the database
        areaalRepository.saveAndFlush(areaal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the areaal using partial update
        Areaal partialUpdatedAreaal = new Areaal();
        partialUpdatedAreaal.setId(areaal.getId());

        partialUpdatedAreaal.geometrie(UPDATED_GEOMETRIE);

        restAreaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAreaal.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAreaal))
            )
            .andExpect(status().isOk());

        // Validate the Areaal in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAreaalUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAreaal, areaal), getPersistedAreaal(areaal));
    }

    @Test
    @Transactional
    void fullUpdateAreaalWithPatch() throws Exception {
        // Initialize the database
        areaalRepository.saveAndFlush(areaal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the areaal using partial update
        Areaal partialUpdatedAreaal = new Areaal();
        partialUpdatedAreaal.setId(areaal.getId());

        partialUpdatedAreaal.geometrie(UPDATED_GEOMETRIE);

        restAreaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAreaal.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAreaal))
            )
            .andExpect(status().isOk());

        // Validate the Areaal in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAreaalUpdatableFieldsEquals(partialUpdatedAreaal, getPersistedAreaal(partialUpdatedAreaal));
    }

    @Test
    @Transactional
    void patchNonExistingAreaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaal.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, areaal.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(areaal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Areaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAreaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(areaal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Areaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAreaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(areaal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Areaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAreaal() throws Exception {
        // Initialize the database
        areaalRepository.saveAndFlush(areaal);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the areaal
        restAreaalMockMvc
            .perform(delete(ENTITY_API_URL_ID, areaal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return areaalRepository.count();
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

    protected Areaal getPersistedAreaal(Areaal areaal) {
        return areaalRepository.findById(areaal.getId()).orElseThrow();
    }

    protected void assertPersistedAreaalToMatchAllProperties(Areaal expectedAreaal) {
        assertAreaalAllPropertiesEquals(expectedAreaal, getPersistedAreaal(expectedAreaal));
    }

    protected void assertPersistedAreaalToMatchUpdatableProperties(Areaal expectedAreaal) {
        assertAreaalAllUpdatablePropertiesEquals(expectedAreaal, getPersistedAreaal(expectedAreaal));
    }
}
