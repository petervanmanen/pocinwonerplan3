package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FormuliersoortAsserts.*;
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
import nl.ritense.demo.domain.Formuliersoort;
import nl.ritense.demo.repository.FormuliersoortRepository;
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
 * Integration tests for the {@link FormuliersoortResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FormuliersoortResourceIT {

    private static final String DEFAULT_INGEBRUIK = "AAAAAAAAAA";
    private static final String UPDATED_INGEBRUIK = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERWERP = "AAAAAAAAAA";
    private static final String UPDATED_ONDERWERP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/formuliersoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FormuliersoortRepository formuliersoortRepository;

    @Mock
    private FormuliersoortRepository formuliersoortRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormuliersoortMockMvc;

    private Formuliersoort formuliersoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formuliersoort createEntity(EntityManager em) {
        Formuliersoort formuliersoort = new Formuliersoort().ingebruik(DEFAULT_INGEBRUIK).naam(DEFAULT_NAAM).onderwerp(DEFAULT_ONDERWERP);
        return formuliersoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formuliersoort createUpdatedEntity(EntityManager em) {
        Formuliersoort formuliersoort = new Formuliersoort().ingebruik(UPDATED_INGEBRUIK).naam(UPDATED_NAAM).onderwerp(UPDATED_ONDERWERP);
        return formuliersoort;
    }

    @BeforeEach
    public void initTest() {
        formuliersoort = createEntity(em);
    }

    @Test
    @Transactional
    void createFormuliersoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Formuliersoort
        var returnedFormuliersoort = om.readValue(
            restFormuliersoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formuliersoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Formuliersoort.class
        );

        // Validate the Formuliersoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFormuliersoortUpdatableFieldsEquals(returnedFormuliersoort, getPersistedFormuliersoort(returnedFormuliersoort));
    }

    @Test
    @Transactional
    void createFormuliersoortWithExistingId() throws Exception {
        // Create the Formuliersoort with an existing ID
        formuliersoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormuliersoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formuliersoort)))
            .andExpect(status().isBadRequest());

        // Validate the Formuliersoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormuliersoorts() throws Exception {
        // Initialize the database
        formuliersoortRepository.saveAndFlush(formuliersoort);

        // Get all the formuliersoortList
        restFormuliersoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formuliersoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].ingebruik").value(hasItem(DEFAULT_INGEBRUIK)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].onderwerp").value(hasItem(DEFAULT_ONDERWERP)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFormuliersoortsWithEagerRelationshipsIsEnabled() throws Exception {
        when(formuliersoortRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFormuliersoortMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(formuliersoortRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFormuliersoortsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(formuliersoortRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFormuliersoortMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(formuliersoortRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFormuliersoort() throws Exception {
        // Initialize the database
        formuliersoortRepository.saveAndFlush(formuliersoort);

        // Get the formuliersoort
        restFormuliersoortMockMvc
            .perform(get(ENTITY_API_URL_ID, formuliersoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formuliersoort.getId().intValue()))
            .andExpect(jsonPath("$.ingebruik").value(DEFAULT_INGEBRUIK))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.onderwerp").value(DEFAULT_ONDERWERP));
    }

    @Test
    @Transactional
    void getNonExistingFormuliersoort() throws Exception {
        // Get the formuliersoort
        restFormuliersoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFormuliersoort() throws Exception {
        // Initialize the database
        formuliersoortRepository.saveAndFlush(formuliersoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formuliersoort
        Formuliersoort updatedFormuliersoort = formuliersoortRepository.findById(formuliersoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFormuliersoort are not directly saved in db
        em.detach(updatedFormuliersoort);
        updatedFormuliersoort.ingebruik(UPDATED_INGEBRUIK).naam(UPDATED_NAAM).onderwerp(UPDATED_ONDERWERP);

        restFormuliersoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormuliersoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFormuliersoort))
            )
            .andExpect(status().isOk());

        // Validate the Formuliersoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFormuliersoortToMatchAllProperties(updatedFormuliersoort);
    }

    @Test
    @Transactional
    void putNonExistingFormuliersoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formuliersoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormuliersoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formuliersoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formuliersoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formuliersoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormuliersoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formuliersoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormuliersoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formuliersoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formuliersoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormuliersoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formuliersoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormuliersoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formuliersoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formuliersoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormuliersoortWithPatch() throws Exception {
        // Initialize the database
        formuliersoortRepository.saveAndFlush(formuliersoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formuliersoort using partial update
        Formuliersoort partialUpdatedFormuliersoort = new Formuliersoort();
        partialUpdatedFormuliersoort.setId(formuliersoort.getId());

        partialUpdatedFormuliersoort.ingebruik(UPDATED_INGEBRUIK).naam(UPDATED_NAAM).onderwerp(UPDATED_ONDERWERP);

        restFormuliersoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormuliersoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormuliersoort))
            )
            .andExpect(status().isOk());

        // Validate the Formuliersoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormuliersoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFormuliersoort, formuliersoort),
            getPersistedFormuliersoort(formuliersoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateFormuliersoortWithPatch() throws Exception {
        // Initialize the database
        formuliersoortRepository.saveAndFlush(formuliersoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formuliersoort using partial update
        Formuliersoort partialUpdatedFormuliersoort = new Formuliersoort();
        partialUpdatedFormuliersoort.setId(formuliersoort.getId());

        partialUpdatedFormuliersoort.ingebruik(UPDATED_INGEBRUIK).naam(UPDATED_NAAM).onderwerp(UPDATED_ONDERWERP);

        restFormuliersoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormuliersoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormuliersoort))
            )
            .andExpect(status().isOk());

        // Validate the Formuliersoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormuliersoortUpdatableFieldsEquals(partialUpdatedFormuliersoort, getPersistedFormuliersoort(partialUpdatedFormuliersoort));
    }

    @Test
    @Transactional
    void patchNonExistingFormuliersoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formuliersoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormuliersoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formuliersoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formuliersoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formuliersoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormuliersoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formuliersoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormuliersoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formuliersoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formuliersoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormuliersoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formuliersoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormuliersoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(formuliersoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formuliersoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormuliersoort() throws Exception {
        // Initialize the database
        formuliersoortRepository.saveAndFlush(formuliersoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the formuliersoort
        restFormuliersoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, formuliersoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return formuliersoortRepository.count();
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

    protected Formuliersoort getPersistedFormuliersoort(Formuliersoort formuliersoort) {
        return formuliersoortRepository.findById(formuliersoort.getId()).orElseThrow();
    }

    protected void assertPersistedFormuliersoortToMatchAllProperties(Formuliersoort expectedFormuliersoort) {
        assertFormuliersoortAllPropertiesEquals(expectedFormuliersoort, getPersistedFormuliersoort(expectedFormuliersoort));
    }

    protected void assertPersistedFormuliersoortToMatchUpdatableProperties(Formuliersoort expectedFormuliersoort) {
        assertFormuliersoortAllUpdatablePropertiesEquals(expectedFormuliersoort, getPersistedFormuliersoort(expectedFormuliersoort));
    }
}
