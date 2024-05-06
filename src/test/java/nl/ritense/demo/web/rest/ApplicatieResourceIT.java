package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ApplicatieAsserts.*;
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
import nl.ritense.demo.domain.Applicatie;
import nl.ritense.demo.repository.ApplicatieRepository;
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
 * Integration tests for the {@link ApplicatieResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ApplicatieResourceIT {

    private static final String DEFAULT_APPLICATIEURL = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATIEURL = "BBBBBBBBBB";

    private static final String DEFAULT_BEHEERSTATUS = "AAAAAAAAAA";
    private static final String UPDATED_BEHEERSTATUS = "BBBBBBBBBB";

    private static final String DEFAULT_BELEIDSDOMEIN = "AAAAAAAAAA";
    private static final String UPDATED_BELEIDSDOMEIN = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_PACKAGINGSTATUS = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGINGSTATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/applicaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ApplicatieRepository applicatieRepository;

    @Mock
    private ApplicatieRepository applicatieRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicatieMockMvc;

    private Applicatie applicatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Applicatie createEntity(EntityManager em) {
        Applicatie applicatie = new Applicatie()
            .applicatieurl(DEFAULT_APPLICATIEURL)
            .beheerstatus(DEFAULT_BEHEERSTATUS)
            .beleidsdomein(DEFAULT_BELEIDSDOMEIN)
            .categorie(DEFAULT_CATEGORIE)
            .guid(DEFAULT_GUID)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .packagingstatus(DEFAULT_PACKAGINGSTATUS);
        return applicatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Applicatie createUpdatedEntity(EntityManager em) {
        Applicatie applicatie = new Applicatie()
            .applicatieurl(UPDATED_APPLICATIEURL)
            .beheerstatus(UPDATED_BEHEERSTATUS)
            .beleidsdomein(UPDATED_BELEIDSDOMEIN)
            .categorie(UPDATED_CATEGORIE)
            .guid(UPDATED_GUID)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .packagingstatus(UPDATED_PACKAGINGSTATUS);
        return applicatie;
    }

    @BeforeEach
    public void initTest() {
        applicatie = createEntity(em);
    }

    @Test
    @Transactional
    void createApplicatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Applicatie
        var returnedApplicatie = om.readValue(
            restApplicatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(applicatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Applicatie.class
        );

        // Validate the Applicatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertApplicatieUpdatableFieldsEquals(returnedApplicatie, getPersistedApplicatie(returnedApplicatie));
    }

    @Test
    @Transactional
    void createApplicatieWithExistingId() throws Exception {
        // Create the Applicatie with an existing ID
        applicatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(applicatie)))
            .andExpect(status().isBadRequest());

        // Validate the Applicatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApplicaties() throws Exception {
        // Initialize the database
        applicatieRepository.saveAndFlush(applicatie);

        // Get all the applicatieList
        restApplicatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicatieurl").value(hasItem(DEFAULT_APPLICATIEURL)))
            .andExpect(jsonPath("$.[*].beheerstatus").value(hasItem(DEFAULT_BEHEERSTATUS)))
            .andExpect(jsonPath("$.[*].beleidsdomein").value(hasItem(DEFAULT_BELEIDSDOMEIN)))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE)))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].packagingstatus").value(hasItem(DEFAULT_PACKAGINGSTATUS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApplicatiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(applicatieRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApplicatieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(applicatieRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApplicatiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(applicatieRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApplicatieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(applicatieRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getApplicatie() throws Exception {
        // Initialize the database
        applicatieRepository.saveAndFlush(applicatie);

        // Get the applicatie
        restApplicatieMockMvc
            .perform(get(ENTITY_API_URL_ID, applicatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicatie.getId().intValue()))
            .andExpect(jsonPath("$.applicatieurl").value(DEFAULT_APPLICATIEURL))
            .andExpect(jsonPath("$.beheerstatus").value(DEFAULT_BEHEERSTATUS))
            .andExpect(jsonPath("$.beleidsdomein").value(DEFAULT_BELEIDSDOMEIN))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.packagingstatus").value(DEFAULT_PACKAGINGSTATUS));
    }

    @Test
    @Transactional
    void getNonExistingApplicatie() throws Exception {
        // Get the applicatie
        restApplicatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApplicatie() throws Exception {
        // Initialize the database
        applicatieRepository.saveAndFlush(applicatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the applicatie
        Applicatie updatedApplicatie = applicatieRepository.findById(applicatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedApplicatie are not directly saved in db
        em.detach(updatedApplicatie);
        updatedApplicatie
            .applicatieurl(UPDATED_APPLICATIEURL)
            .beheerstatus(UPDATED_BEHEERSTATUS)
            .beleidsdomein(UPDATED_BELEIDSDOMEIN)
            .categorie(UPDATED_CATEGORIE)
            .guid(UPDATED_GUID)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .packagingstatus(UPDATED_PACKAGINGSTATUS);

        restApplicatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApplicatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedApplicatie))
            )
            .andExpect(status().isOk());

        // Validate the Applicatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedApplicatieToMatchAllProperties(updatedApplicatie);
    }

    @Test
    @Transactional
    void putNonExistingApplicatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicatie.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(applicatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Applicatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(applicatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Applicatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(applicatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Applicatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicatieWithPatch() throws Exception {
        // Initialize the database
        applicatieRepository.saveAndFlush(applicatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the applicatie using partial update
        Applicatie partialUpdatedApplicatie = new Applicatie();
        partialUpdatedApplicatie.setId(applicatie.getId());

        partialUpdatedApplicatie.applicatieurl(UPDATED_APPLICATIEURL).categorie(UPDATED_CATEGORIE).omschrijving(UPDATED_OMSCHRIJVING);

        restApplicatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApplicatie))
            )
            .andExpect(status().isOk());

        // Validate the Applicatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApplicatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedApplicatie, applicatie),
            getPersistedApplicatie(applicatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateApplicatieWithPatch() throws Exception {
        // Initialize the database
        applicatieRepository.saveAndFlush(applicatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the applicatie using partial update
        Applicatie partialUpdatedApplicatie = new Applicatie();
        partialUpdatedApplicatie.setId(applicatie.getId());

        partialUpdatedApplicatie
            .applicatieurl(UPDATED_APPLICATIEURL)
            .beheerstatus(UPDATED_BEHEERSTATUS)
            .beleidsdomein(UPDATED_BELEIDSDOMEIN)
            .categorie(UPDATED_CATEGORIE)
            .guid(UPDATED_GUID)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .packagingstatus(UPDATED_PACKAGINGSTATUS);

        restApplicatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApplicatie))
            )
            .andExpect(status().isOk());

        // Validate the Applicatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApplicatieUpdatableFieldsEquals(partialUpdatedApplicatie, getPersistedApplicatie(partialUpdatedApplicatie));
    }

    @Test
    @Transactional
    void patchNonExistingApplicatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(applicatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Applicatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(applicatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Applicatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(applicatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Applicatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicatie() throws Exception {
        // Initialize the database
        applicatieRepository.saveAndFlush(applicatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the applicatie
        restApplicatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return applicatieRepository.count();
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

    protected Applicatie getPersistedApplicatie(Applicatie applicatie) {
        return applicatieRepository.findById(applicatie.getId()).orElseThrow();
    }

    protected void assertPersistedApplicatieToMatchAllProperties(Applicatie expectedApplicatie) {
        assertApplicatieAllPropertiesEquals(expectedApplicatie, getPersistedApplicatie(expectedApplicatie));
    }

    protected void assertPersistedApplicatieToMatchUpdatableProperties(Applicatie expectedApplicatie) {
        assertApplicatieAllUpdatablePropertiesEquals(expectedApplicatie, getPersistedApplicatie(expectedApplicatie));
    }
}
