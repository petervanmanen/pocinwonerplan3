package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.HoofdstukAsserts.*;
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
import nl.ritense.demo.domain.Hoofdstuk;
import nl.ritense.demo.repository.HoofdstukRepository;
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
 * Integration tests for the {@link HoofdstukResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class HoofdstukResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hoofdstuks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HoofdstukRepository hoofdstukRepository;

    @Mock
    private HoofdstukRepository hoofdstukRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHoofdstukMockMvc;

    private Hoofdstuk hoofdstuk;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hoofdstuk createEntity(EntityManager em) {
        Hoofdstuk hoofdstuk = new Hoofdstuk().naam(DEFAULT_NAAM).nummer(DEFAULT_NUMMER).omschrijving(DEFAULT_OMSCHRIJVING);
        return hoofdstuk;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hoofdstuk createUpdatedEntity(EntityManager em) {
        Hoofdstuk hoofdstuk = new Hoofdstuk().naam(UPDATED_NAAM).nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING);
        return hoofdstuk;
    }

    @BeforeEach
    public void initTest() {
        hoofdstuk = createEntity(em);
    }

    @Test
    @Transactional
    void createHoofdstuk() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Hoofdstuk
        var returnedHoofdstuk = om.readValue(
            restHoofdstukMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hoofdstuk)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Hoofdstuk.class
        );

        // Validate the Hoofdstuk in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHoofdstukUpdatableFieldsEquals(returnedHoofdstuk, getPersistedHoofdstuk(returnedHoofdstuk));
    }

    @Test
    @Transactional
    void createHoofdstukWithExistingId() throws Exception {
        // Create the Hoofdstuk with an existing ID
        hoofdstuk.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHoofdstukMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hoofdstuk)))
            .andExpect(status().isBadRequest());

        // Validate the Hoofdstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHoofdstuks() throws Exception {
        // Initialize the database
        hoofdstukRepository.saveAndFlush(hoofdstuk);

        // Get all the hoofdstukList
        restHoofdstukMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hoofdstuk.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHoofdstuksWithEagerRelationshipsIsEnabled() throws Exception {
        when(hoofdstukRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHoofdstukMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(hoofdstukRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHoofdstuksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(hoofdstukRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHoofdstukMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(hoofdstukRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getHoofdstuk() throws Exception {
        // Initialize the database
        hoofdstukRepository.saveAndFlush(hoofdstuk);

        // Get the hoofdstuk
        restHoofdstukMockMvc
            .perform(get(ENTITY_API_URL_ID, hoofdstuk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hoofdstuk.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingHoofdstuk() throws Exception {
        // Get the hoofdstuk
        restHoofdstukMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHoofdstuk() throws Exception {
        // Initialize the database
        hoofdstukRepository.saveAndFlush(hoofdstuk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hoofdstuk
        Hoofdstuk updatedHoofdstuk = hoofdstukRepository.findById(hoofdstuk.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHoofdstuk are not directly saved in db
        em.detach(updatedHoofdstuk);
        updatedHoofdstuk.naam(UPDATED_NAAM).nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING);

        restHoofdstukMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHoofdstuk.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHoofdstuk))
            )
            .andExpect(status().isOk());

        // Validate the Hoofdstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHoofdstukToMatchAllProperties(updatedHoofdstuk);
    }

    @Test
    @Transactional
    void putNonExistingHoofdstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hoofdstuk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoofdstukMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hoofdstuk.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hoofdstuk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hoofdstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHoofdstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hoofdstuk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoofdstukMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hoofdstuk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hoofdstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHoofdstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hoofdstuk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoofdstukMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hoofdstuk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hoofdstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHoofdstukWithPatch() throws Exception {
        // Initialize the database
        hoofdstukRepository.saveAndFlush(hoofdstuk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hoofdstuk using partial update
        Hoofdstuk partialUpdatedHoofdstuk = new Hoofdstuk();
        partialUpdatedHoofdstuk.setId(hoofdstuk.getId());

        partialUpdatedHoofdstuk.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restHoofdstukMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoofdstuk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHoofdstuk))
            )
            .andExpect(status().isOk());

        // Validate the Hoofdstuk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHoofdstukUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHoofdstuk, hoofdstuk),
            getPersistedHoofdstuk(hoofdstuk)
        );
    }

    @Test
    @Transactional
    void fullUpdateHoofdstukWithPatch() throws Exception {
        // Initialize the database
        hoofdstukRepository.saveAndFlush(hoofdstuk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hoofdstuk using partial update
        Hoofdstuk partialUpdatedHoofdstuk = new Hoofdstuk();
        partialUpdatedHoofdstuk.setId(hoofdstuk.getId());

        partialUpdatedHoofdstuk.naam(UPDATED_NAAM).nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING);

        restHoofdstukMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoofdstuk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHoofdstuk))
            )
            .andExpect(status().isOk());

        // Validate the Hoofdstuk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHoofdstukUpdatableFieldsEquals(partialUpdatedHoofdstuk, getPersistedHoofdstuk(partialUpdatedHoofdstuk));
    }

    @Test
    @Transactional
    void patchNonExistingHoofdstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hoofdstuk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoofdstukMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hoofdstuk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hoofdstuk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hoofdstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHoofdstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hoofdstuk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoofdstukMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hoofdstuk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hoofdstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHoofdstuk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hoofdstuk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoofdstukMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hoofdstuk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hoofdstuk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHoofdstuk() throws Exception {
        // Initialize the database
        hoofdstukRepository.saveAndFlush(hoofdstuk);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hoofdstuk
        restHoofdstukMockMvc
            .perform(delete(ENTITY_API_URL_ID, hoofdstuk.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hoofdstukRepository.count();
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

    protected Hoofdstuk getPersistedHoofdstuk(Hoofdstuk hoofdstuk) {
        return hoofdstukRepository.findById(hoofdstuk.getId()).orElseThrow();
    }

    protected void assertPersistedHoofdstukToMatchAllProperties(Hoofdstuk expectedHoofdstuk) {
        assertHoofdstukAllPropertiesEquals(expectedHoofdstuk, getPersistedHoofdstuk(expectedHoofdstuk));
    }

    protected void assertPersistedHoofdstukToMatchUpdatableProperties(Hoofdstuk expectedHoofdstuk) {
        assertHoofdstukAllUpdatablePropertiesEquals(expectedHoofdstuk, getPersistedHoofdstuk(expectedHoofdstuk));
    }
}
