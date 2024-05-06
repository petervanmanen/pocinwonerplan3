package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ProjectontwikkelaarAsserts.*;
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
import nl.ritense.demo.domain.Projectontwikkelaar;
import nl.ritense.demo.repository.ProjectontwikkelaarRepository;
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
 * Integration tests for the {@link ProjectontwikkelaarResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProjectontwikkelaarResourceIT {

    private static final String DEFAULT_ADRES = "AAAAAAAAAA";
    private static final String UPDATED_ADRES = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/projectontwikkelaars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProjectontwikkelaarRepository projectontwikkelaarRepository;

    @Mock
    private ProjectontwikkelaarRepository projectontwikkelaarRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectontwikkelaarMockMvc;

    private Projectontwikkelaar projectontwikkelaar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projectontwikkelaar createEntity(EntityManager em) {
        Projectontwikkelaar projectontwikkelaar = new Projectontwikkelaar().adres(DEFAULT_ADRES).naam(DEFAULT_NAAM);
        return projectontwikkelaar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projectontwikkelaar createUpdatedEntity(EntityManager em) {
        Projectontwikkelaar projectontwikkelaar = new Projectontwikkelaar().adres(UPDATED_ADRES).naam(UPDATED_NAAM);
        return projectontwikkelaar;
    }

    @BeforeEach
    public void initTest() {
        projectontwikkelaar = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectontwikkelaar() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Projectontwikkelaar
        var returnedProjectontwikkelaar = om.readValue(
            restProjectontwikkelaarMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(projectontwikkelaar)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Projectontwikkelaar.class
        );

        // Validate the Projectontwikkelaar in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProjectontwikkelaarUpdatableFieldsEquals(
            returnedProjectontwikkelaar,
            getPersistedProjectontwikkelaar(returnedProjectontwikkelaar)
        );
    }

    @Test
    @Transactional
    void createProjectontwikkelaarWithExistingId() throws Exception {
        // Create the Projectontwikkelaar with an existing ID
        projectontwikkelaar.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectontwikkelaarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(projectontwikkelaar)))
            .andExpect(status().isBadRequest());

        // Validate the Projectontwikkelaar in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectontwikkelaars() throws Exception {
        // Initialize the database
        projectontwikkelaarRepository.saveAndFlush(projectontwikkelaar);

        // Get all the projectontwikkelaarList
        restProjectontwikkelaarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectontwikkelaar.getId().intValue())))
            .andExpect(jsonPath("$.[*].adres").value(hasItem(DEFAULT_ADRES)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProjectontwikkelaarsWithEagerRelationshipsIsEnabled() throws Exception {
        when(projectontwikkelaarRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProjectontwikkelaarMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(projectontwikkelaarRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProjectontwikkelaarsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(projectontwikkelaarRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProjectontwikkelaarMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(projectontwikkelaarRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProjectontwikkelaar() throws Exception {
        // Initialize the database
        projectontwikkelaarRepository.saveAndFlush(projectontwikkelaar);

        // Get the projectontwikkelaar
        restProjectontwikkelaarMockMvc
            .perform(get(ENTITY_API_URL_ID, projectontwikkelaar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectontwikkelaar.getId().intValue()))
            .andExpect(jsonPath("$.adres").value(DEFAULT_ADRES))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingProjectontwikkelaar() throws Exception {
        // Get the projectontwikkelaar
        restProjectontwikkelaarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjectontwikkelaar() throws Exception {
        // Initialize the database
        projectontwikkelaarRepository.saveAndFlush(projectontwikkelaar);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the projectontwikkelaar
        Projectontwikkelaar updatedProjectontwikkelaar = projectontwikkelaarRepository.findById(projectontwikkelaar.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProjectontwikkelaar are not directly saved in db
        em.detach(updatedProjectontwikkelaar);
        updatedProjectontwikkelaar.adres(UPDATED_ADRES).naam(UPDATED_NAAM);

        restProjectontwikkelaarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProjectontwikkelaar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProjectontwikkelaar))
            )
            .andExpect(status().isOk());

        // Validate the Projectontwikkelaar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProjectontwikkelaarToMatchAllProperties(updatedProjectontwikkelaar);
    }

    @Test
    @Transactional
    void putNonExistingProjectontwikkelaar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectontwikkelaar.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectontwikkelaarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectontwikkelaar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(projectontwikkelaar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectontwikkelaar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectontwikkelaar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectontwikkelaar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectontwikkelaarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(projectontwikkelaar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectontwikkelaar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectontwikkelaar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectontwikkelaar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectontwikkelaarMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(projectontwikkelaar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projectontwikkelaar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectontwikkelaarWithPatch() throws Exception {
        // Initialize the database
        projectontwikkelaarRepository.saveAndFlush(projectontwikkelaar);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the projectontwikkelaar using partial update
        Projectontwikkelaar partialUpdatedProjectontwikkelaar = new Projectontwikkelaar();
        partialUpdatedProjectontwikkelaar.setId(projectontwikkelaar.getId());

        partialUpdatedProjectontwikkelaar.adres(UPDATED_ADRES).naam(UPDATED_NAAM);

        restProjectontwikkelaarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectontwikkelaar.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProjectontwikkelaar))
            )
            .andExpect(status().isOk());

        // Validate the Projectontwikkelaar in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProjectontwikkelaarUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProjectontwikkelaar, projectontwikkelaar),
            getPersistedProjectontwikkelaar(projectontwikkelaar)
        );
    }

    @Test
    @Transactional
    void fullUpdateProjectontwikkelaarWithPatch() throws Exception {
        // Initialize the database
        projectontwikkelaarRepository.saveAndFlush(projectontwikkelaar);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the projectontwikkelaar using partial update
        Projectontwikkelaar partialUpdatedProjectontwikkelaar = new Projectontwikkelaar();
        partialUpdatedProjectontwikkelaar.setId(projectontwikkelaar.getId());

        partialUpdatedProjectontwikkelaar.adres(UPDATED_ADRES).naam(UPDATED_NAAM);

        restProjectontwikkelaarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectontwikkelaar.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProjectontwikkelaar))
            )
            .andExpect(status().isOk());

        // Validate the Projectontwikkelaar in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProjectontwikkelaarUpdatableFieldsEquals(
            partialUpdatedProjectontwikkelaar,
            getPersistedProjectontwikkelaar(partialUpdatedProjectontwikkelaar)
        );
    }

    @Test
    @Transactional
    void patchNonExistingProjectontwikkelaar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectontwikkelaar.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectontwikkelaarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectontwikkelaar.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(projectontwikkelaar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectontwikkelaar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectontwikkelaar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectontwikkelaar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectontwikkelaarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(projectontwikkelaar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projectontwikkelaar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectontwikkelaar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        projectontwikkelaar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectontwikkelaarMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(projectontwikkelaar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projectontwikkelaar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectontwikkelaar() throws Exception {
        // Initialize the database
        projectontwikkelaarRepository.saveAndFlush(projectontwikkelaar);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the projectontwikkelaar
        restProjectontwikkelaarMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectontwikkelaar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return projectontwikkelaarRepository.count();
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

    protected Projectontwikkelaar getPersistedProjectontwikkelaar(Projectontwikkelaar projectontwikkelaar) {
        return projectontwikkelaarRepository.findById(projectontwikkelaar.getId()).orElseThrow();
    }

    protected void assertPersistedProjectontwikkelaarToMatchAllProperties(Projectontwikkelaar expectedProjectontwikkelaar) {
        assertProjectontwikkelaarAllPropertiesEquals(
            expectedProjectontwikkelaar,
            getPersistedProjectontwikkelaar(expectedProjectontwikkelaar)
        );
    }

    protected void assertPersistedProjectontwikkelaarToMatchUpdatableProperties(Projectontwikkelaar expectedProjectontwikkelaar) {
        assertProjectontwikkelaarAllUpdatablePropertiesEquals(
            expectedProjectontwikkelaar,
            getPersistedProjectontwikkelaar(expectedProjectontwikkelaar)
        );
    }
}
