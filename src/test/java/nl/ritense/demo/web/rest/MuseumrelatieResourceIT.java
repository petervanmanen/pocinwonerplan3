package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MuseumrelatieAsserts.*;
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
import nl.ritense.demo.domain.Museumrelatie;
import nl.ritense.demo.repository.MuseumrelatieRepository;
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
 * Integration tests for the {@link MuseumrelatieResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MuseumrelatieResourceIT {

    private static final String DEFAULT_RELATIESOORT = "AAAAAAAAAA";
    private static final String UPDATED_RELATIESOORT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/museumrelaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MuseumrelatieRepository museumrelatieRepository;

    @Mock
    private MuseumrelatieRepository museumrelatieRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMuseumrelatieMockMvc;

    private Museumrelatie museumrelatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Museumrelatie createEntity(EntityManager em) {
        Museumrelatie museumrelatie = new Museumrelatie().relatiesoort(DEFAULT_RELATIESOORT);
        return museumrelatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Museumrelatie createUpdatedEntity(EntityManager em) {
        Museumrelatie museumrelatie = new Museumrelatie().relatiesoort(UPDATED_RELATIESOORT);
        return museumrelatie;
    }

    @BeforeEach
    public void initTest() {
        museumrelatie = createEntity(em);
    }

    @Test
    @Transactional
    void createMuseumrelatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Museumrelatie
        var returnedMuseumrelatie = om.readValue(
            restMuseumrelatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(museumrelatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Museumrelatie.class
        );

        // Validate the Museumrelatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMuseumrelatieUpdatableFieldsEquals(returnedMuseumrelatie, getPersistedMuseumrelatie(returnedMuseumrelatie));
    }

    @Test
    @Transactional
    void createMuseumrelatieWithExistingId() throws Exception {
        // Create the Museumrelatie with an existing ID
        museumrelatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMuseumrelatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(museumrelatie)))
            .andExpect(status().isBadRequest());

        // Validate the Museumrelatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMuseumrelaties() throws Exception {
        // Initialize the database
        museumrelatieRepository.saveAndFlush(museumrelatie);

        // Get all the museumrelatieList
        restMuseumrelatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(museumrelatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].relatiesoort").value(hasItem(DEFAULT_RELATIESOORT)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMuseumrelatiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(museumrelatieRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMuseumrelatieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(museumrelatieRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMuseumrelatiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(museumrelatieRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMuseumrelatieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(museumrelatieRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMuseumrelatie() throws Exception {
        // Initialize the database
        museumrelatieRepository.saveAndFlush(museumrelatie);

        // Get the museumrelatie
        restMuseumrelatieMockMvc
            .perform(get(ENTITY_API_URL_ID, museumrelatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(museumrelatie.getId().intValue()))
            .andExpect(jsonPath("$.relatiesoort").value(DEFAULT_RELATIESOORT));
    }

    @Test
    @Transactional
    void getNonExistingMuseumrelatie() throws Exception {
        // Get the museumrelatie
        restMuseumrelatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMuseumrelatie() throws Exception {
        // Initialize the database
        museumrelatieRepository.saveAndFlush(museumrelatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the museumrelatie
        Museumrelatie updatedMuseumrelatie = museumrelatieRepository.findById(museumrelatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMuseumrelatie are not directly saved in db
        em.detach(updatedMuseumrelatie);
        updatedMuseumrelatie.relatiesoort(UPDATED_RELATIESOORT);

        restMuseumrelatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMuseumrelatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMuseumrelatie))
            )
            .andExpect(status().isOk());

        // Validate the Museumrelatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMuseumrelatieToMatchAllProperties(updatedMuseumrelatie);
    }

    @Test
    @Transactional
    void putNonExistingMuseumrelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        museumrelatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMuseumrelatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, museumrelatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(museumrelatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Museumrelatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMuseumrelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        museumrelatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuseumrelatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(museumrelatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Museumrelatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMuseumrelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        museumrelatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuseumrelatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(museumrelatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Museumrelatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMuseumrelatieWithPatch() throws Exception {
        // Initialize the database
        museumrelatieRepository.saveAndFlush(museumrelatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the museumrelatie using partial update
        Museumrelatie partialUpdatedMuseumrelatie = new Museumrelatie();
        partialUpdatedMuseumrelatie.setId(museumrelatie.getId());

        restMuseumrelatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMuseumrelatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMuseumrelatie))
            )
            .andExpect(status().isOk());

        // Validate the Museumrelatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMuseumrelatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMuseumrelatie, museumrelatie),
            getPersistedMuseumrelatie(museumrelatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateMuseumrelatieWithPatch() throws Exception {
        // Initialize the database
        museumrelatieRepository.saveAndFlush(museumrelatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the museumrelatie using partial update
        Museumrelatie partialUpdatedMuseumrelatie = new Museumrelatie();
        partialUpdatedMuseumrelatie.setId(museumrelatie.getId());

        partialUpdatedMuseumrelatie.relatiesoort(UPDATED_RELATIESOORT);

        restMuseumrelatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMuseumrelatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMuseumrelatie))
            )
            .andExpect(status().isOk());

        // Validate the Museumrelatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMuseumrelatieUpdatableFieldsEquals(partialUpdatedMuseumrelatie, getPersistedMuseumrelatie(partialUpdatedMuseumrelatie));
    }

    @Test
    @Transactional
    void patchNonExistingMuseumrelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        museumrelatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMuseumrelatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, museumrelatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(museumrelatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Museumrelatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMuseumrelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        museumrelatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuseumrelatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(museumrelatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Museumrelatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMuseumrelatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        museumrelatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuseumrelatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(museumrelatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Museumrelatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMuseumrelatie() throws Exception {
        // Initialize the database
        museumrelatieRepository.saveAndFlush(museumrelatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the museumrelatie
        restMuseumrelatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, museumrelatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return museumrelatieRepository.count();
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

    protected Museumrelatie getPersistedMuseumrelatie(Museumrelatie museumrelatie) {
        return museumrelatieRepository.findById(museumrelatie.getId()).orElseThrow();
    }

    protected void assertPersistedMuseumrelatieToMatchAllProperties(Museumrelatie expectedMuseumrelatie) {
        assertMuseumrelatieAllPropertiesEquals(expectedMuseumrelatie, getPersistedMuseumrelatie(expectedMuseumrelatie));
    }

    protected void assertPersistedMuseumrelatieToMatchUpdatableProperties(Museumrelatie expectedMuseumrelatie) {
        assertMuseumrelatieAllUpdatablePropertiesEquals(expectedMuseumrelatie, getPersistedMuseumrelatie(expectedMuseumrelatie));
    }
}
