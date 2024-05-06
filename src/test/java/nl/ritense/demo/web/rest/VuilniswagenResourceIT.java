package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VuilniswagenAsserts.*;
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
import nl.ritense.demo.domain.Vuilniswagen;
import nl.ritense.demo.repository.VuilniswagenRepository;
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
 * Integration tests for the {@link VuilniswagenResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class VuilniswagenResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_KENTEKEN = "AAAAAAAA";
    private static final String UPDATED_KENTEKEN = "BBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vuilniswagens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VuilniswagenRepository vuilniswagenRepository;

    @Mock
    private VuilniswagenRepository vuilniswagenRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVuilniswagenMockMvc;

    private Vuilniswagen vuilniswagen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vuilniswagen createEntity(EntityManager em) {
        Vuilniswagen vuilniswagen = new Vuilniswagen().code(DEFAULT_CODE).kenteken(DEFAULT_KENTEKEN).type(DEFAULT_TYPE);
        return vuilniswagen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vuilniswagen createUpdatedEntity(EntityManager em) {
        Vuilniswagen vuilniswagen = new Vuilniswagen().code(UPDATED_CODE).kenteken(UPDATED_KENTEKEN).type(UPDATED_TYPE);
        return vuilniswagen;
    }

    @BeforeEach
    public void initTest() {
        vuilniswagen = createEntity(em);
    }

    @Test
    @Transactional
    void createVuilniswagen() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vuilniswagen
        var returnedVuilniswagen = om.readValue(
            restVuilniswagenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vuilniswagen)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vuilniswagen.class
        );

        // Validate the Vuilniswagen in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVuilniswagenUpdatableFieldsEquals(returnedVuilniswagen, getPersistedVuilniswagen(returnedVuilniswagen));
    }

    @Test
    @Transactional
    void createVuilniswagenWithExistingId() throws Exception {
        // Create the Vuilniswagen with an existing ID
        vuilniswagen.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVuilniswagenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vuilniswagen)))
            .andExpect(status().isBadRequest());

        // Validate the Vuilniswagen in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVuilniswagens() throws Exception {
        // Initialize the database
        vuilniswagenRepository.saveAndFlush(vuilniswagen);

        // Get all the vuilniswagenList
        restVuilniswagenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vuilniswagen.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].kenteken").value(hasItem(DEFAULT_KENTEKEN)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVuilniswagensWithEagerRelationshipsIsEnabled() throws Exception {
        when(vuilniswagenRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVuilniswagenMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(vuilniswagenRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVuilniswagensWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(vuilniswagenRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVuilniswagenMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(vuilniswagenRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getVuilniswagen() throws Exception {
        // Initialize the database
        vuilniswagenRepository.saveAndFlush(vuilniswagen);

        // Get the vuilniswagen
        restVuilniswagenMockMvc
            .perform(get(ENTITY_API_URL_ID, vuilniswagen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vuilniswagen.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.kenteken").value(DEFAULT_KENTEKEN))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingVuilniswagen() throws Exception {
        // Get the vuilniswagen
        restVuilniswagenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVuilniswagen() throws Exception {
        // Initialize the database
        vuilniswagenRepository.saveAndFlush(vuilniswagen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vuilniswagen
        Vuilniswagen updatedVuilniswagen = vuilniswagenRepository.findById(vuilniswagen.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVuilniswagen are not directly saved in db
        em.detach(updatedVuilniswagen);
        updatedVuilniswagen.code(UPDATED_CODE).kenteken(UPDATED_KENTEKEN).type(UPDATED_TYPE);

        restVuilniswagenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVuilniswagen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVuilniswagen))
            )
            .andExpect(status().isOk());

        // Validate the Vuilniswagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVuilniswagenToMatchAllProperties(updatedVuilniswagen);
    }

    @Test
    @Transactional
    void putNonExistingVuilniswagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vuilniswagen.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVuilniswagenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vuilniswagen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vuilniswagen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vuilniswagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVuilniswagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vuilniswagen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVuilniswagenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vuilniswagen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vuilniswagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVuilniswagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vuilniswagen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVuilniswagenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vuilniswagen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vuilniswagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVuilniswagenWithPatch() throws Exception {
        // Initialize the database
        vuilniswagenRepository.saveAndFlush(vuilniswagen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vuilniswagen using partial update
        Vuilniswagen partialUpdatedVuilniswagen = new Vuilniswagen();
        partialUpdatedVuilniswagen.setId(vuilniswagen.getId());

        partialUpdatedVuilniswagen.code(UPDATED_CODE).kenteken(UPDATED_KENTEKEN).type(UPDATED_TYPE);

        restVuilniswagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVuilniswagen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVuilniswagen))
            )
            .andExpect(status().isOk());

        // Validate the Vuilniswagen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVuilniswagenUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVuilniswagen, vuilniswagen),
            getPersistedVuilniswagen(vuilniswagen)
        );
    }

    @Test
    @Transactional
    void fullUpdateVuilniswagenWithPatch() throws Exception {
        // Initialize the database
        vuilniswagenRepository.saveAndFlush(vuilniswagen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vuilniswagen using partial update
        Vuilniswagen partialUpdatedVuilniswagen = new Vuilniswagen();
        partialUpdatedVuilniswagen.setId(vuilniswagen.getId());

        partialUpdatedVuilniswagen.code(UPDATED_CODE).kenteken(UPDATED_KENTEKEN).type(UPDATED_TYPE);

        restVuilniswagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVuilniswagen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVuilniswagen))
            )
            .andExpect(status().isOk());

        // Validate the Vuilniswagen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVuilniswagenUpdatableFieldsEquals(partialUpdatedVuilniswagen, getPersistedVuilniswagen(partialUpdatedVuilniswagen));
    }

    @Test
    @Transactional
    void patchNonExistingVuilniswagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vuilniswagen.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVuilniswagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vuilniswagen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vuilniswagen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vuilniswagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVuilniswagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vuilniswagen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVuilniswagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vuilniswagen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vuilniswagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVuilniswagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vuilniswagen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVuilniswagenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vuilniswagen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vuilniswagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVuilniswagen() throws Exception {
        // Initialize the database
        vuilniswagenRepository.saveAndFlush(vuilniswagen);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vuilniswagen
        restVuilniswagenMockMvc
            .perform(delete(ENTITY_API_URL_ID, vuilniswagen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vuilniswagenRepository.count();
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

    protected Vuilniswagen getPersistedVuilniswagen(Vuilniswagen vuilniswagen) {
        return vuilniswagenRepository.findById(vuilniswagen.getId()).orElseThrow();
    }

    protected void assertPersistedVuilniswagenToMatchAllProperties(Vuilniswagen expectedVuilniswagen) {
        assertVuilniswagenAllPropertiesEquals(expectedVuilniswagen, getPersistedVuilniswagen(expectedVuilniswagen));
    }

    protected void assertPersistedVuilniswagenToMatchUpdatableProperties(Vuilniswagen expectedVuilniswagen) {
        assertVuilniswagenAllUpdatablePropertiesEquals(expectedVuilniswagen, getPersistedVuilniswagen(expectedVuilniswagen));
    }
}
