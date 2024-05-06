package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GrondslagAsserts.*;
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
import nl.ritense.demo.domain.Grondslag;
import nl.ritense.demo.repository.GrondslagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
 * Integration tests for the {@link GrondslagResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class GrondslagResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/grondslags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GrondslagRepository grondslagRepository;

    @Mock
    private GrondslagRepository grondslagRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrondslagMockMvc;

    private Grondslag grondslag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grondslag createEntity(EntityManager em) {
        Grondslag grondslag = new Grondslag().code(DEFAULT_CODE).omschrijving(DEFAULT_OMSCHRIJVING);
        return grondslag;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grondslag createUpdatedEntity(EntityManager em) {
        Grondslag grondslag = new Grondslag().code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING);
        return grondslag;
    }

    @BeforeEach
    public void initTest() {
        grondslag = createEntity(em);
    }

    @Test
    @Transactional
    void createGrondslag() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Grondslag
        var returnedGrondslag = om.readValue(
            restGrondslagMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grondslag)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Grondslag.class
        );

        // Validate the Grondslag in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGrondslagUpdatableFieldsEquals(returnedGrondslag, getPersistedGrondslag(returnedGrondslag));
    }

    @Test
    @Transactional
    void createGrondslagWithExistingId() throws Exception {
        // Create the Grondslag with an existing ID
        grondslag.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrondslagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grondslag)))
            .andExpect(status().isBadRequest());

        // Validate the Grondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGrondslags() throws Exception {
        // Initialize the database
        grondslagRepository.saveAndFlush(grondslag);

        // Get all the grondslagList
        restGrondslagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grondslag.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGrondslagsWithEagerRelationshipsIsEnabled() throws Exception {
        when(grondslagRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGrondslagMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(grondslagRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGrondslagsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(grondslagRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGrondslagMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(grondslagRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getGrondslag() throws Exception {
        // Initialize the database
        grondslagRepository.saveAndFlush(grondslag);

        // Get the grondslag
        restGrondslagMockMvc
            .perform(get(ENTITY_API_URL_ID, grondslag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grondslag.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingGrondslag() throws Exception {
        // Get the grondslag
        restGrondslagMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGrondslag() throws Exception {
        // Initialize the database
        grondslagRepository.saveAndFlush(grondslag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grondslag
        Grondslag updatedGrondslag = grondslagRepository.findById(grondslag.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGrondslag are not directly saved in db
        em.detach(updatedGrondslag);
        updatedGrondslag.code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING);

        restGrondslagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGrondslag.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGrondslag))
            )
            .andExpect(status().isOk());

        // Validate the Grondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGrondslagToMatchAllProperties(updatedGrondslag);
    }

    @Test
    @Transactional
    void putNonExistingGrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grondslag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrondslagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, grondslag.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grondslag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grondslag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrondslagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grondslag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grondslag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrondslagMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grondslag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Grondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGrondslagWithPatch() throws Exception {
        // Initialize the database
        grondslagRepository.saveAndFlush(grondslag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grondslag using partial update
        Grondslag partialUpdatedGrondslag = new Grondslag();
        partialUpdatedGrondslag.setId(grondslag.getId());

        partialUpdatedGrondslag.code(UPDATED_CODE);

        restGrondslagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrondslag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrondslag))
            )
            .andExpect(status().isOk());

        // Validate the Grondslag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrondslagUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGrondslag, grondslag),
            getPersistedGrondslag(grondslag)
        );
    }

    @Test
    @Transactional
    void fullUpdateGrondslagWithPatch() throws Exception {
        // Initialize the database
        grondslagRepository.saveAndFlush(grondslag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grondslag using partial update
        Grondslag partialUpdatedGrondslag = new Grondslag();
        partialUpdatedGrondslag.setId(grondslag.getId());

        partialUpdatedGrondslag.code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING);

        restGrondslagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrondslag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrondslag))
            )
            .andExpect(status().isOk());

        // Validate the Grondslag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrondslagUpdatableFieldsEquals(partialUpdatedGrondslag, getPersistedGrondslag(partialUpdatedGrondslag));
    }

    @Test
    @Transactional
    void patchNonExistingGrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grondslag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrondslagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, grondslag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grondslag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grondslag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrondslagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grondslag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grondslag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrondslagMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(grondslag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Grondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrondslag() throws Exception {
        // Initialize the database
        grondslagRepository.saveAndFlush(grondslag);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the grondslag
        restGrondslagMockMvc
            .perform(delete(ENTITY_API_URL_ID, grondslag.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return grondslagRepository.count();
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

    protected Grondslag getPersistedGrondslag(Grondslag grondslag) {
        return grondslagRepository.findById(grondslag.getId()).orElseThrow();
    }

    protected void assertPersistedGrondslagToMatchAllProperties(Grondslag expectedGrondslag) {
        assertGrondslagAllPropertiesEquals(expectedGrondslag, getPersistedGrondslag(expectedGrondslag));
    }

    protected void assertPersistedGrondslagToMatchUpdatableProperties(Grondslag expectedGrondslag) {
        assertGrondslagAllUpdatablePropertiesEquals(expectedGrondslag, getPersistedGrondslag(expectedGrondslag));
    }
}
