package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EobjectAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Eobject;
import nl.ritense.demo.repository.EobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EobjectResourceIT {

    private static final String DEFAULT_ADRESBINNENLAND = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBINNENLAND = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESBUITENLAND = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBUITENLAND = "BBBBBBBBBB";

    private static final String DEFAULT_DOMEIN = "AAAAAAAAAA";
    private static final String UPDATED_DOMEIN = "BBBBBBBBBB";

    private static final String DEFAULT_GEOMETRIE = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIE = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIERISICO = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIERISICO = "BBBBBBBBBB";

    private static final String DEFAULT_KADASTRALEAANDUIDING = "AAAAAAAAAA";
    private static final String UPDATED_KADASTRALEAANDUIDING = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_EOBJECTTYPE = "AAAAAAAAAA";
    private static final String UPDATED_EOBJECTTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/eobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EobjectRepository eobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEobjectMockMvc;

    private Eobject eobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobject createEntity(EntityManager em) {
        Eobject eobject = new Eobject()
            .adresbinnenland(DEFAULT_ADRESBINNENLAND)
            .adresbuitenland(DEFAULT_ADRESBUITENLAND)
            .domein(DEFAULT_DOMEIN)
            .geometrie(DEFAULT_GEOMETRIE)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .indicatierisico(DEFAULT_INDICATIERISICO)
            .kadastraleaanduiding(DEFAULT_KADASTRALEAANDUIDING)
            .naam(DEFAULT_NAAM)
            .eobjecttype(DEFAULT_EOBJECTTYPE)
            .toelichting(DEFAULT_TOELICHTING);
        return eobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobject createUpdatedEntity(EntityManager em) {
        Eobject eobject = new Eobject()
            .adresbinnenland(UPDATED_ADRESBINNENLAND)
            .adresbuitenland(UPDATED_ADRESBUITENLAND)
            .domein(UPDATED_DOMEIN)
            .geometrie(UPDATED_GEOMETRIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .indicatierisico(UPDATED_INDICATIERISICO)
            .kadastraleaanduiding(UPDATED_KADASTRALEAANDUIDING)
            .naam(UPDATED_NAAM)
            .eobjecttype(UPDATED_EOBJECTTYPE)
            .toelichting(UPDATED_TOELICHTING);
        return eobject;
    }

    @BeforeEach
    public void initTest() {
        eobject = createEntity(em);
    }

    @Test
    @Transactional
    void createEobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Eobject
        var returnedEobject = om.readValue(
            restEobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Eobject.class
        );

        // Validate the Eobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEobjectUpdatableFieldsEquals(returnedEobject, getPersistedEobject(returnedEobject));
    }

    @Test
    @Transactional
    void createEobjectWithExistingId() throws Exception {
        // Create the Eobject with an existing ID
        eobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobject)))
            .andExpect(status().isBadRequest());

        // Validate the Eobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEobjects() throws Exception {
        // Initialize the database
        eobjectRepository.saveAndFlush(eobject);

        // Get all the eobjectList
        restEobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresbinnenland").value(hasItem(DEFAULT_ADRESBINNENLAND)))
            .andExpect(jsonPath("$.[*].adresbuitenland").value(hasItem(DEFAULT_ADRESBUITENLAND)))
            .andExpect(jsonPath("$.[*].domein").value(hasItem(DEFAULT_DOMEIN)))
            .andExpect(jsonPath("$.[*].geometrie").value(hasItem(DEFAULT_GEOMETRIE)))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].indicatierisico").value(hasItem(DEFAULT_INDICATIERISICO)))
            .andExpect(jsonPath("$.[*].kadastraleaanduiding").value(hasItem(DEFAULT_KADASTRALEAANDUIDING)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].eobjecttype").value(hasItem(DEFAULT_EOBJECTTYPE)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)));
    }

    @Test
    @Transactional
    void getEobject() throws Exception {
        // Initialize the database
        eobjectRepository.saveAndFlush(eobject);

        // Get the eobject
        restEobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, eobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eobject.getId().intValue()))
            .andExpect(jsonPath("$.adresbinnenland").value(DEFAULT_ADRESBINNENLAND))
            .andExpect(jsonPath("$.adresbuitenland").value(DEFAULT_ADRESBUITENLAND))
            .andExpect(jsonPath("$.domein").value(DEFAULT_DOMEIN))
            .andExpect(jsonPath("$.geometrie").value(DEFAULT_GEOMETRIE))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.indicatierisico").value(DEFAULT_INDICATIERISICO))
            .andExpect(jsonPath("$.kadastraleaanduiding").value(DEFAULT_KADASTRALEAANDUIDING))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.eobjecttype").value(DEFAULT_EOBJECTTYPE))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING));
    }

    @Test
    @Transactional
    void getNonExistingEobject() throws Exception {
        // Get the eobject
        restEobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEobject() throws Exception {
        // Initialize the database
        eobjectRepository.saveAndFlush(eobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eobject
        Eobject updatedEobject = eobjectRepository.findById(eobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEobject are not directly saved in db
        em.detach(updatedEobject);
        updatedEobject
            .adresbinnenland(UPDATED_ADRESBINNENLAND)
            .adresbuitenland(UPDATED_ADRESBUITENLAND)
            .domein(UPDATED_DOMEIN)
            .geometrie(UPDATED_GEOMETRIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .indicatierisico(UPDATED_INDICATIERISICO)
            .kadastraleaanduiding(UPDATED_KADASTRALEAANDUIDING)
            .naam(UPDATED_NAAM)
            .eobjecttype(UPDATED_EOBJECTTYPE)
            .toelichting(UPDATED_TOELICHTING);

        restEobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEobject))
            )
            .andExpect(status().isOk());

        // Validate the Eobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEobjectToMatchAllProperties(updatedEobject);
    }

    @Test
    @Transactional
    void putNonExistingEobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEobjectMockMvc
            .perform(put(ENTITY_API_URL_ID, eobject.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobject)))
            .andExpect(status().isBadRequest());

        // Validate the Eobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEobjectWithPatch() throws Exception {
        // Initialize the database
        eobjectRepository.saveAndFlush(eobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eobject using partial update
        Eobject partialUpdatedEobject = new Eobject();
        partialUpdatedEobject.setId(eobject.getId());

        partialUpdatedEobject
            .adresbinnenland(UPDATED_ADRESBINNENLAND)
            .adresbuitenland(UPDATED_ADRESBUITENLAND)
            .identificatie(UPDATED_IDENTIFICATIE)
            .naam(UPDATED_NAAM);

        restEobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEobject))
            )
            .andExpect(status().isOk());

        // Validate the Eobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEobjectUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEobject, eobject), getPersistedEobject(eobject));
    }

    @Test
    @Transactional
    void fullUpdateEobjectWithPatch() throws Exception {
        // Initialize the database
        eobjectRepository.saveAndFlush(eobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eobject using partial update
        Eobject partialUpdatedEobject = new Eobject();
        partialUpdatedEobject.setId(eobject.getId());

        partialUpdatedEobject
            .adresbinnenland(UPDATED_ADRESBINNENLAND)
            .adresbuitenland(UPDATED_ADRESBUITENLAND)
            .domein(UPDATED_DOMEIN)
            .geometrie(UPDATED_GEOMETRIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .indicatierisico(UPDATED_INDICATIERISICO)
            .kadastraleaanduiding(UPDATED_KADASTRALEAANDUIDING)
            .naam(UPDATED_NAAM)
            .eobjecttype(UPDATED_EOBJECTTYPE)
            .toelichting(UPDATED_TOELICHTING);

        restEobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEobject))
            )
            .andExpect(status().isOk());

        // Validate the Eobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEobjectUpdatableFieldsEquals(partialUpdatedEobject, getPersistedEobject(partialUpdatedEobject));
    }

    @Test
    @Transactional
    void patchNonExistingEobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eobject.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(eobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(eobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEobject() throws Exception {
        // Initialize the database
        eobjectRepository.saveAndFlush(eobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eobject
        restEobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, eobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return eobjectRepository.count();
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

    protected Eobject getPersistedEobject(Eobject eobject) {
        return eobjectRepository.findById(eobject.getId()).orElseThrow();
    }

    protected void assertPersistedEobjectToMatchAllProperties(Eobject expectedEobject) {
        assertEobjectAllPropertiesEquals(expectedEobject, getPersistedEobject(expectedEobject));
    }

    protected void assertPersistedEobjectToMatchUpdatableProperties(Eobject expectedEobject) {
        assertEobjectAllUpdatablePropertiesEquals(expectedEobject, getPersistedEobject(expectedEobject));
    }
}
