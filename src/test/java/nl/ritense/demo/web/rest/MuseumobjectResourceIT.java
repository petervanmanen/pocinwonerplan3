package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MuseumobjectAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Museumobject;
import nl.ritense.demo.repository.MuseumobjectRepository;
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
 * Integration tests for the {@link MuseumobjectResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MuseumobjectResourceIT {

    private static final String DEFAULT_AFMETING = "AAAAAAAAAA";
    private static final String UPDATED_AFMETING = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BEZITTOT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BEZITTOT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_BEZITVANAF = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BEZITVANAF = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MEDIUM = "AAAAAAAAAA";
    private static final String UPDATED_MEDIUM = "BBBBBBBBBB";

    private static final String DEFAULT_VERKRIJGING = "AAAAAAAAAA";
    private static final String UPDATED_VERKRIJGING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/museumobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MuseumobjectRepository museumobjectRepository;

    @Mock
    private MuseumobjectRepository museumobjectRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMuseumobjectMockMvc;

    private Museumobject museumobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Museumobject createEntity(EntityManager em) {
        Museumobject museumobject = new Museumobject()
            .afmeting(DEFAULT_AFMETING)
            .bezittot(DEFAULT_BEZITTOT)
            .bezitvanaf(DEFAULT_BEZITVANAF)
            .medium(DEFAULT_MEDIUM)
            .verkrijging(DEFAULT_VERKRIJGING);
        return museumobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Museumobject createUpdatedEntity(EntityManager em) {
        Museumobject museumobject = new Museumobject()
            .afmeting(UPDATED_AFMETING)
            .bezittot(UPDATED_BEZITTOT)
            .bezitvanaf(UPDATED_BEZITVANAF)
            .medium(UPDATED_MEDIUM)
            .verkrijging(UPDATED_VERKRIJGING);
        return museumobject;
    }

    @BeforeEach
    public void initTest() {
        museumobject = createEntity(em);
    }

    @Test
    @Transactional
    void createMuseumobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Museumobject
        var returnedMuseumobject = om.readValue(
            restMuseumobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(museumobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Museumobject.class
        );

        // Validate the Museumobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMuseumobjectUpdatableFieldsEquals(returnedMuseumobject, getPersistedMuseumobject(returnedMuseumobject));
    }

    @Test
    @Transactional
    void createMuseumobjectWithExistingId() throws Exception {
        // Create the Museumobject with an existing ID
        museumobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMuseumobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(museumobject)))
            .andExpect(status().isBadRequest());

        // Validate the Museumobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMuseumobjects() throws Exception {
        // Initialize the database
        museumobjectRepository.saveAndFlush(museumobject);

        // Get all the museumobjectList
        restMuseumobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(museumobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].afmeting").value(hasItem(DEFAULT_AFMETING)))
            .andExpect(jsonPath("$.[*].bezittot").value(hasItem(DEFAULT_BEZITTOT.toString())))
            .andExpect(jsonPath("$.[*].bezitvanaf").value(hasItem(DEFAULT_BEZITVANAF.toString())))
            .andExpect(jsonPath("$.[*].medium").value(hasItem(DEFAULT_MEDIUM)))
            .andExpect(jsonPath("$.[*].verkrijging").value(hasItem(DEFAULT_VERKRIJGING)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMuseumobjectsWithEagerRelationshipsIsEnabled() throws Exception {
        when(museumobjectRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMuseumobjectMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(museumobjectRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMuseumobjectsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(museumobjectRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMuseumobjectMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(museumobjectRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMuseumobject() throws Exception {
        // Initialize the database
        museumobjectRepository.saveAndFlush(museumobject);

        // Get the museumobject
        restMuseumobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, museumobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(museumobject.getId().intValue()))
            .andExpect(jsonPath("$.afmeting").value(DEFAULT_AFMETING))
            .andExpect(jsonPath("$.bezittot").value(DEFAULT_BEZITTOT.toString()))
            .andExpect(jsonPath("$.bezitvanaf").value(DEFAULT_BEZITVANAF.toString()))
            .andExpect(jsonPath("$.medium").value(DEFAULT_MEDIUM))
            .andExpect(jsonPath("$.verkrijging").value(DEFAULT_VERKRIJGING));
    }

    @Test
    @Transactional
    void getNonExistingMuseumobject() throws Exception {
        // Get the museumobject
        restMuseumobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMuseumobject() throws Exception {
        // Initialize the database
        museumobjectRepository.saveAndFlush(museumobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the museumobject
        Museumobject updatedMuseumobject = museumobjectRepository.findById(museumobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMuseumobject are not directly saved in db
        em.detach(updatedMuseumobject);
        updatedMuseumobject
            .afmeting(UPDATED_AFMETING)
            .bezittot(UPDATED_BEZITTOT)
            .bezitvanaf(UPDATED_BEZITVANAF)
            .medium(UPDATED_MEDIUM)
            .verkrijging(UPDATED_VERKRIJGING);

        restMuseumobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMuseumobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMuseumobject))
            )
            .andExpect(status().isOk());

        // Validate the Museumobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMuseumobjectToMatchAllProperties(updatedMuseumobject);
    }

    @Test
    @Transactional
    void putNonExistingMuseumobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        museumobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMuseumobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, museumobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(museumobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Museumobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMuseumobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        museumobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuseumobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(museumobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Museumobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMuseumobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        museumobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuseumobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(museumobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Museumobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMuseumobjectWithPatch() throws Exception {
        // Initialize the database
        museumobjectRepository.saveAndFlush(museumobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the museumobject using partial update
        Museumobject partialUpdatedMuseumobject = new Museumobject();
        partialUpdatedMuseumobject.setId(museumobject.getId());

        partialUpdatedMuseumobject.afmeting(UPDATED_AFMETING).bezittot(UPDATED_BEZITTOT).verkrijging(UPDATED_VERKRIJGING);

        restMuseumobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMuseumobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMuseumobject))
            )
            .andExpect(status().isOk());

        // Validate the Museumobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMuseumobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMuseumobject, museumobject),
            getPersistedMuseumobject(museumobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateMuseumobjectWithPatch() throws Exception {
        // Initialize the database
        museumobjectRepository.saveAndFlush(museumobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the museumobject using partial update
        Museumobject partialUpdatedMuseumobject = new Museumobject();
        partialUpdatedMuseumobject.setId(museumobject.getId());

        partialUpdatedMuseumobject
            .afmeting(UPDATED_AFMETING)
            .bezittot(UPDATED_BEZITTOT)
            .bezitvanaf(UPDATED_BEZITVANAF)
            .medium(UPDATED_MEDIUM)
            .verkrijging(UPDATED_VERKRIJGING);

        restMuseumobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMuseumobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMuseumobject))
            )
            .andExpect(status().isOk());

        // Validate the Museumobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMuseumobjectUpdatableFieldsEquals(partialUpdatedMuseumobject, getPersistedMuseumobject(partialUpdatedMuseumobject));
    }

    @Test
    @Transactional
    void patchNonExistingMuseumobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        museumobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMuseumobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, museumobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(museumobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Museumobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMuseumobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        museumobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuseumobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(museumobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Museumobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMuseumobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        museumobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuseumobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(museumobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Museumobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMuseumobject() throws Exception {
        // Initialize the database
        museumobjectRepository.saveAndFlush(museumobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the museumobject
        restMuseumobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, museumobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return museumobjectRepository.count();
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

    protected Museumobject getPersistedMuseumobject(Museumobject museumobject) {
        return museumobjectRepository.findById(museumobject.getId()).orElseThrow();
    }

    protected void assertPersistedMuseumobjectToMatchAllProperties(Museumobject expectedMuseumobject) {
        assertMuseumobjectAllPropertiesEquals(expectedMuseumobject, getPersistedMuseumobject(expectedMuseumobject));
    }

    protected void assertPersistedMuseumobjectToMatchUpdatableProperties(Museumobject expectedMuseumobject) {
        assertMuseumobjectAllUpdatablePropertiesEquals(expectedMuseumobject, getPersistedMuseumobject(expectedMuseumobject));
    }
}
