package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PutAsserts.*;
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
import nl.ritense.demo.domain.Put;
import nl.ritense.demo.repository.PutRepository;
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
 * Integration tests for the {@link PutResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PutResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECTCD = "AAAAAAAAAA";
    private static final String UPDATED_PROJECTCD = "BBBBBBBBBB";

    private static final String DEFAULT_PUTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_PUTNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/puts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PutRepository putRepository;

    @Mock
    private PutRepository putRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPutMockMvc;

    private Put put;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Put createEntity(EntityManager em) {
        Put put = new Put().key(DEFAULT_KEY).projectcd(DEFAULT_PROJECTCD).putnummer(DEFAULT_PUTNUMMER);
        return put;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Put createUpdatedEntity(EntityManager em) {
        Put put = new Put().key(UPDATED_KEY).projectcd(UPDATED_PROJECTCD).putnummer(UPDATED_PUTNUMMER);
        return put;
    }

    @BeforeEach
    public void initTest() {
        put = createEntity(em);
    }

    @Test
    @Transactional
    void createPut() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Put
        var returnedPut = om.readValue(
            restPutMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(put)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Put.class
        );

        // Validate the Put in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPutUpdatableFieldsEquals(returnedPut, getPersistedPut(returnedPut));
    }

    @Test
    @Transactional
    void createPutWithExistingId() throws Exception {
        // Create the Put with an existing ID
        put.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(put)))
            .andExpect(status().isBadRequest());

        // Validate the Put in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPuts() throws Exception {
        // Initialize the database
        putRepository.saveAndFlush(put);

        // Get all the putList
        restPutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(put.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].projectcd").value(hasItem(DEFAULT_PROJECTCD)))
            .andExpect(jsonPath("$.[*].putnummer").value(hasItem(DEFAULT_PUTNUMMER)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPutsWithEagerRelationshipsIsEnabled() throws Exception {
        when(putRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPutMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(putRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPutsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(putRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPutMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(putRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPut() throws Exception {
        // Initialize the database
        putRepository.saveAndFlush(put);

        // Get the put
        restPutMockMvc
            .perform(get(ENTITY_API_URL_ID, put.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(put.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.projectcd").value(DEFAULT_PROJECTCD))
            .andExpect(jsonPath("$.putnummer").value(DEFAULT_PUTNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingPut() throws Exception {
        // Get the put
        restPutMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPut() throws Exception {
        // Initialize the database
        putRepository.saveAndFlush(put);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the put
        Put updatedPut = putRepository.findById(put.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPut are not directly saved in db
        em.detach(updatedPut);
        updatedPut.key(UPDATED_KEY).projectcd(UPDATED_PROJECTCD).putnummer(UPDATED_PUTNUMMER);

        restPutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPut.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(updatedPut))
            )
            .andExpect(status().isOk());

        // Validate the Put in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPutToMatchAllProperties(updatedPut);
    }

    @Test
    @Transactional
    void putNonExistingPut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        put.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPutMockMvc
            .perform(put(ENTITY_API_URL_ID, put.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(put)))
            .andExpect(status().isBadRequest());

        // Validate the Put in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        put.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(put))
            )
            .andExpect(status().isBadRequest());

        // Validate the Put in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        put.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPutMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(put)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Put in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePutWithPatch() throws Exception {
        // Initialize the database
        putRepository.saveAndFlush(put);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the put using partial update
        Put partialUpdatedPut = new Put();
        partialUpdatedPut.setId(put.getId());

        partialUpdatedPut.key(UPDATED_KEY).putnummer(UPDATED_PUTNUMMER);

        restPutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPut.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPut))
            )
            .andExpect(status().isOk());

        // Validate the Put in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPutUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPut, put), getPersistedPut(put));
    }

    @Test
    @Transactional
    void fullUpdatePutWithPatch() throws Exception {
        // Initialize the database
        putRepository.saveAndFlush(put);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the put using partial update
        Put partialUpdatedPut = new Put();
        partialUpdatedPut.setId(put.getId());

        partialUpdatedPut.key(UPDATED_KEY).projectcd(UPDATED_PROJECTCD).putnummer(UPDATED_PUTNUMMER);

        restPutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPut.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPut))
            )
            .andExpect(status().isOk());

        // Validate the Put in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPutUpdatableFieldsEquals(partialUpdatedPut, getPersistedPut(partialUpdatedPut));
    }

    @Test
    @Transactional
    void patchNonExistingPut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        put.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPutMockMvc
            .perform(patch(ENTITY_API_URL_ID, put.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(put)))
            .andExpect(status().isBadRequest());

        // Validate the Put in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        put.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(put))
            )
            .andExpect(status().isBadRequest());

        // Validate the Put in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        put.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPutMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(put)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Put in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePut() throws Exception {
        // Initialize the database
        putRepository.saveAndFlush(put);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the put
        restPutMockMvc.perform(delete(ENTITY_API_URL_ID, put.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return putRepository.count();
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

    protected Put getPersistedPut(Put put) {
        return putRepository.findById(put.getId()).orElseThrow();
    }

    protected void assertPersistedPutToMatchAllProperties(Put expectedPut) {
        assertPutAllPropertiesEquals(expectedPut, getPersistedPut(expectedPut));
    }

    protected void assertPersistedPutToMatchUpdatableProperties(Put expectedPut) {
        assertPutAllUpdatablePropertiesEquals(expectedPut, getPersistedPut(expectedPut));
    }
}
