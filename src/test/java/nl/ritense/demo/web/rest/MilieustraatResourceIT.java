package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MilieustraatAsserts.*;
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
import nl.ritense.demo.domain.Milieustraat;
import nl.ritense.demo.domain.Pas;
import nl.ritense.demo.repository.MilieustraatRepository;
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
 * Integration tests for the {@link MilieustraatResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MilieustraatResourceIT {

    private static final String DEFAULT_ADRESAANDUIDING = "AAAAAAAAAA";
    private static final String UPDATED_ADRESAANDUIDING = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/milieustraats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MilieustraatRepository milieustraatRepository;

    @Mock
    private MilieustraatRepository milieustraatRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMilieustraatMockMvc;

    private Milieustraat milieustraat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Milieustraat createEntity(EntityManager em) {
        Milieustraat milieustraat = new Milieustraat()
            .adresaanduiding(DEFAULT_ADRESAANDUIDING)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        // Add required entity
        Pas pas;
        if (TestUtil.findAll(em, Pas.class).isEmpty()) {
            pas = PasResourceIT.createEntity(em);
            em.persist(pas);
            em.flush();
        } else {
            pas = TestUtil.findAll(em, Pas.class).get(0);
        }
        milieustraat.getGeldigvoorPas().add(pas);
        return milieustraat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Milieustraat createUpdatedEntity(EntityManager em) {
        Milieustraat milieustraat = new Milieustraat()
            .adresaanduiding(UPDATED_ADRESAANDUIDING)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);
        // Add required entity
        Pas pas;
        if (TestUtil.findAll(em, Pas.class).isEmpty()) {
            pas = PasResourceIT.createUpdatedEntity(em);
            em.persist(pas);
            em.flush();
        } else {
            pas = TestUtil.findAll(em, Pas.class).get(0);
        }
        milieustraat.getGeldigvoorPas().add(pas);
        return milieustraat;
    }

    @BeforeEach
    public void initTest() {
        milieustraat = createEntity(em);
    }

    @Test
    @Transactional
    void createMilieustraat() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Milieustraat
        var returnedMilieustraat = om.readValue(
            restMilieustraatMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(milieustraat)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Milieustraat.class
        );

        // Validate the Milieustraat in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMilieustraatUpdatableFieldsEquals(returnedMilieustraat, getPersistedMilieustraat(returnedMilieustraat));
    }

    @Test
    @Transactional
    void createMilieustraatWithExistingId() throws Exception {
        // Create the Milieustraat with an existing ID
        milieustraat.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMilieustraatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(milieustraat)))
            .andExpect(status().isBadRequest());

        // Validate the Milieustraat in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMilieustraats() throws Exception {
        // Initialize the database
        milieustraatRepository.saveAndFlush(milieustraat);

        // Get all the milieustraatList
        restMilieustraatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(milieustraat.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresaanduiding").value(hasItem(DEFAULT_ADRESAANDUIDING)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMilieustraatsWithEagerRelationshipsIsEnabled() throws Exception {
        when(milieustraatRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMilieustraatMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(milieustraatRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMilieustraatsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(milieustraatRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMilieustraatMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(milieustraatRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMilieustraat() throws Exception {
        // Initialize the database
        milieustraatRepository.saveAndFlush(milieustraat);

        // Get the milieustraat
        restMilieustraatMockMvc
            .perform(get(ENTITY_API_URL_ID, milieustraat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(milieustraat.getId().intValue()))
            .andExpect(jsonPath("$.adresaanduiding").value(DEFAULT_ADRESAANDUIDING))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingMilieustraat() throws Exception {
        // Get the milieustraat
        restMilieustraatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMilieustraat() throws Exception {
        // Initialize the database
        milieustraatRepository.saveAndFlush(milieustraat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the milieustraat
        Milieustraat updatedMilieustraat = milieustraatRepository.findById(milieustraat.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMilieustraat are not directly saved in db
        em.detach(updatedMilieustraat);
        updatedMilieustraat.adresaanduiding(UPDATED_ADRESAANDUIDING).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restMilieustraatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMilieustraat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMilieustraat))
            )
            .andExpect(status().isOk());

        // Validate the Milieustraat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMilieustraatToMatchAllProperties(updatedMilieustraat);
    }

    @Test
    @Transactional
    void putNonExistingMilieustraat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        milieustraat.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMilieustraatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, milieustraat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(milieustraat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Milieustraat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMilieustraat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        milieustraat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMilieustraatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(milieustraat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Milieustraat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMilieustraat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        milieustraat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMilieustraatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(milieustraat)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Milieustraat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMilieustraatWithPatch() throws Exception {
        // Initialize the database
        milieustraatRepository.saveAndFlush(milieustraat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the milieustraat using partial update
        Milieustraat partialUpdatedMilieustraat = new Milieustraat();
        partialUpdatedMilieustraat.setId(milieustraat.getId());

        partialUpdatedMilieustraat.adresaanduiding(UPDATED_ADRESAANDUIDING);

        restMilieustraatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMilieustraat.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMilieustraat))
            )
            .andExpect(status().isOk());

        // Validate the Milieustraat in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMilieustraatUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMilieustraat, milieustraat),
            getPersistedMilieustraat(milieustraat)
        );
    }

    @Test
    @Transactional
    void fullUpdateMilieustraatWithPatch() throws Exception {
        // Initialize the database
        milieustraatRepository.saveAndFlush(milieustraat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the milieustraat using partial update
        Milieustraat partialUpdatedMilieustraat = new Milieustraat();
        partialUpdatedMilieustraat.setId(milieustraat.getId());

        partialUpdatedMilieustraat.adresaanduiding(UPDATED_ADRESAANDUIDING).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restMilieustraatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMilieustraat.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMilieustraat))
            )
            .andExpect(status().isOk());

        // Validate the Milieustraat in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMilieustraatUpdatableFieldsEquals(partialUpdatedMilieustraat, getPersistedMilieustraat(partialUpdatedMilieustraat));
    }

    @Test
    @Transactional
    void patchNonExistingMilieustraat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        milieustraat.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMilieustraatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, milieustraat.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(milieustraat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Milieustraat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMilieustraat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        milieustraat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMilieustraatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(milieustraat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Milieustraat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMilieustraat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        milieustraat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMilieustraatMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(milieustraat)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Milieustraat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMilieustraat() throws Exception {
        // Initialize the database
        milieustraatRepository.saveAndFlush(milieustraat);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the milieustraat
        restMilieustraatMockMvc
            .perform(delete(ENTITY_API_URL_ID, milieustraat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return milieustraatRepository.count();
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

    protected Milieustraat getPersistedMilieustraat(Milieustraat milieustraat) {
        return milieustraatRepository.findById(milieustraat.getId()).orElseThrow();
    }

    protected void assertPersistedMilieustraatToMatchAllProperties(Milieustraat expectedMilieustraat) {
        assertMilieustraatAllPropertiesEquals(expectedMilieustraat, getPersistedMilieustraat(expectedMilieustraat));
    }

    protected void assertPersistedMilieustraatToMatchUpdatableProperties(Milieustraat expectedMilieustraat) {
        assertMilieustraatAllUpdatablePropertiesEquals(expectedMilieustraat, getPersistedMilieustraat(expectedMilieustraat));
    }
}
