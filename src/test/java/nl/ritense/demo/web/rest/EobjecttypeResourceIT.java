package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EobjecttypeAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Eobjecttype;
import nl.ritense.demo.repository.EobjecttypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EobjecttypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EobjecttypeResourceIT {

    private static final LocalDate DEFAULT_DATUMOPNAME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMOPNAME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEFINITIE = "AAAAAAAAAA";
    private static final String UPDATED_DEFINITIE = "BBBBBBBBBB";

    private static final String DEFAULT_EAGUID = "AAAAAAAAAA";
    private static final String UPDATED_EAGUID = "BBBBBBBBBB";

    private static final String DEFAULT_HERKOMST = "AAAAAAAAAA";
    private static final String UPDATED_HERKOMST = "BBBBBBBBBB";

    private static final String DEFAULT_HERKOMSTDEFINITIE = "AAAAAAAAAA";
    private static final String UPDATED_HERKOMSTDEFINITIE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INDICATIEABSTRACT = false;
    private static final Boolean UPDATED_INDICATIEABSTRACT = true;

    private static final String DEFAULT_KWALITEIT = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_POPULATIE = "AAAAAAAAAA";
    private static final String UPDATED_POPULATIE = "BBBBBBBBBB";

    private static final String DEFAULT_STEREOTYPE = "AAAAAAAAAA";
    private static final String UPDATED_STEREOTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String DEFAULT_UNIEKEAANDUIDING = "AAAAAAAAAA";
    private static final String UPDATED_UNIEKEAANDUIDING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/eobjecttypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EobjecttypeRepository eobjecttypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEobjecttypeMockMvc;

    private Eobjecttype eobjecttype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjecttype createEntity(EntityManager em) {
        Eobjecttype eobjecttype = new Eobjecttype()
            .datumopname(DEFAULT_DATUMOPNAME)
            .definitie(DEFAULT_DEFINITIE)
            .eaguid(DEFAULT_EAGUID)
            .herkomst(DEFAULT_HERKOMST)
            .herkomstdefinitie(DEFAULT_HERKOMSTDEFINITIE)
            .indicatieabstract(DEFAULT_INDICATIEABSTRACT)
            .kwaliteit(DEFAULT_KWALITEIT)
            .naam(DEFAULT_NAAM)
            .populatie(DEFAULT_POPULATIE)
            .stereotype(DEFAULT_STEREOTYPE)
            .toelichting(DEFAULT_TOELICHTING)
            .uniekeaanduiding(DEFAULT_UNIEKEAANDUIDING);
        return eobjecttype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eobjecttype createUpdatedEntity(EntityManager em) {
        Eobjecttype eobjecttype = new Eobjecttype()
            .datumopname(UPDATED_DATUMOPNAME)
            .definitie(UPDATED_DEFINITIE)
            .eaguid(UPDATED_EAGUID)
            .herkomst(UPDATED_HERKOMST)
            .herkomstdefinitie(UPDATED_HERKOMSTDEFINITIE)
            .indicatieabstract(UPDATED_INDICATIEABSTRACT)
            .kwaliteit(UPDATED_KWALITEIT)
            .naam(UPDATED_NAAM)
            .populatie(UPDATED_POPULATIE)
            .stereotype(UPDATED_STEREOTYPE)
            .toelichting(UPDATED_TOELICHTING)
            .uniekeaanduiding(UPDATED_UNIEKEAANDUIDING);
        return eobjecttype;
    }

    @BeforeEach
    public void initTest() {
        eobjecttype = createEntity(em);
    }

    @Test
    @Transactional
    void createEobjecttype() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Eobjecttype
        var returnedEobjecttype = om.readValue(
            restEobjecttypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjecttype)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Eobjecttype.class
        );

        // Validate the Eobjecttype in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEobjecttypeUpdatableFieldsEquals(returnedEobjecttype, getPersistedEobjecttype(returnedEobjecttype));
    }

    @Test
    @Transactional
    void createEobjecttypeWithExistingId() throws Exception {
        // Create the Eobjecttype with an existing ID
        eobjecttype.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEobjecttypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjecttype)))
            .andExpect(status().isBadRequest());

        // Validate the Eobjecttype in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEobjecttypes() throws Exception {
        // Initialize the database
        eobjecttypeRepository.saveAndFlush(eobjecttype);

        // Get all the eobjecttypeList
        restEobjecttypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eobjecttype.getId())))
            .andExpect(jsonPath("$.[*].datumopname").value(hasItem(DEFAULT_DATUMOPNAME.toString())))
            .andExpect(jsonPath("$.[*].definitie").value(hasItem(DEFAULT_DEFINITIE)))
            .andExpect(jsonPath("$.[*].eaguid").value(hasItem(DEFAULT_EAGUID)))
            .andExpect(jsonPath("$.[*].herkomst").value(hasItem(DEFAULT_HERKOMST)))
            .andExpect(jsonPath("$.[*].herkomstdefinitie").value(hasItem(DEFAULT_HERKOMSTDEFINITIE)))
            .andExpect(jsonPath("$.[*].indicatieabstract").value(hasItem(DEFAULT_INDICATIEABSTRACT.booleanValue())))
            .andExpect(jsonPath("$.[*].kwaliteit").value(hasItem(DEFAULT_KWALITEIT)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].populatie").value(hasItem(DEFAULT_POPULATIE)))
            .andExpect(jsonPath("$.[*].stereotype").value(hasItem(DEFAULT_STEREOTYPE)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)))
            .andExpect(jsonPath("$.[*].uniekeaanduiding").value(hasItem(DEFAULT_UNIEKEAANDUIDING)));
    }

    @Test
    @Transactional
    void getEobjecttype() throws Exception {
        // Initialize the database
        eobjecttypeRepository.saveAndFlush(eobjecttype);

        // Get the eobjecttype
        restEobjecttypeMockMvc
            .perform(get(ENTITY_API_URL_ID, eobjecttype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eobjecttype.getId()))
            .andExpect(jsonPath("$.datumopname").value(DEFAULT_DATUMOPNAME.toString()))
            .andExpect(jsonPath("$.definitie").value(DEFAULT_DEFINITIE))
            .andExpect(jsonPath("$.eaguid").value(DEFAULT_EAGUID))
            .andExpect(jsonPath("$.herkomst").value(DEFAULT_HERKOMST))
            .andExpect(jsonPath("$.herkomstdefinitie").value(DEFAULT_HERKOMSTDEFINITIE))
            .andExpect(jsonPath("$.indicatieabstract").value(DEFAULT_INDICATIEABSTRACT.booleanValue()))
            .andExpect(jsonPath("$.kwaliteit").value(DEFAULT_KWALITEIT))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.populatie").value(DEFAULT_POPULATIE))
            .andExpect(jsonPath("$.stereotype").value(DEFAULT_STEREOTYPE))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING))
            .andExpect(jsonPath("$.uniekeaanduiding").value(DEFAULT_UNIEKEAANDUIDING));
    }

    @Test
    @Transactional
    void getNonExistingEobjecttype() throws Exception {
        // Get the eobjecttype
        restEobjecttypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEobjecttype() throws Exception {
        // Initialize the database
        eobjecttypeRepository.saveAndFlush(eobjecttype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eobjecttype
        Eobjecttype updatedEobjecttype = eobjecttypeRepository.findById(eobjecttype.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEobjecttype are not directly saved in db
        em.detach(updatedEobjecttype);
        updatedEobjecttype
            .datumopname(UPDATED_DATUMOPNAME)
            .definitie(UPDATED_DEFINITIE)
            .eaguid(UPDATED_EAGUID)
            .herkomst(UPDATED_HERKOMST)
            .herkomstdefinitie(UPDATED_HERKOMSTDEFINITIE)
            .indicatieabstract(UPDATED_INDICATIEABSTRACT)
            .kwaliteit(UPDATED_KWALITEIT)
            .naam(UPDATED_NAAM)
            .populatie(UPDATED_POPULATIE)
            .stereotype(UPDATED_STEREOTYPE)
            .toelichting(UPDATED_TOELICHTING)
            .uniekeaanduiding(UPDATED_UNIEKEAANDUIDING);

        restEobjecttypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEobjecttype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEobjecttype))
            )
            .andExpect(status().isOk());

        // Validate the Eobjecttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEobjecttypeToMatchAllProperties(updatedEobjecttype);
    }

    @Test
    @Transactional
    void putNonExistingEobjecttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjecttype.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEobjecttypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eobjecttype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eobjecttype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eobjecttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEobjecttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjecttype.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEobjecttypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eobjecttype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eobjecttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEobjecttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjecttype.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEobjecttypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eobjecttype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eobjecttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEobjecttypeWithPatch() throws Exception {
        // Initialize the database
        eobjecttypeRepository.saveAndFlush(eobjecttype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eobjecttype using partial update
        Eobjecttype partialUpdatedEobjecttype = new Eobjecttype();
        partialUpdatedEobjecttype.setId(eobjecttype.getId());

        partialUpdatedEobjecttype
            .datumopname(UPDATED_DATUMOPNAME)
            .eaguid(UPDATED_EAGUID)
            .herkomst(UPDATED_HERKOMST)
            .herkomstdefinitie(UPDATED_HERKOMSTDEFINITIE)
            .indicatieabstract(UPDATED_INDICATIEABSTRACT)
            .kwaliteit(UPDATED_KWALITEIT)
            .populatie(UPDATED_POPULATIE)
            .toelichting(UPDATED_TOELICHTING)
            .uniekeaanduiding(UPDATED_UNIEKEAANDUIDING);

        restEobjecttypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEobjecttype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEobjecttype))
            )
            .andExpect(status().isOk());

        // Validate the Eobjecttype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEobjecttypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEobjecttype, eobjecttype),
            getPersistedEobjecttype(eobjecttype)
        );
    }

    @Test
    @Transactional
    void fullUpdateEobjecttypeWithPatch() throws Exception {
        // Initialize the database
        eobjecttypeRepository.saveAndFlush(eobjecttype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eobjecttype using partial update
        Eobjecttype partialUpdatedEobjecttype = new Eobjecttype();
        partialUpdatedEobjecttype.setId(eobjecttype.getId());

        partialUpdatedEobjecttype
            .datumopname(UPDATED_DATUMOPNAME)
            .definitie(UPDATED_DEFINITIE)
            .eaguid(UPDATED_EAGUID)
            .herkomst(UPDATED_HERKOMST)
            .herkomstdefinitie(UPDATED_HERKOMSTDEFINITIE)
            .indicatieabstract(UPDATED_INDICATIEABSTRACT)
            .kwaliteit(UPDATED_KWALITEIT)
            .naam(UPDATED_NAAM)
            .populatie(UPDATED_POPULATIE)
            .stereotype(UPDATED_STEREOTYPE)
            .toelichting(UPDATED_TOELICHTING)
            .uniekeaanduiding(UPDATED_UNIEKEAANDUIDING);

        restEobjecttypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEobjecttype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEobjecttype))
            )
            .andExpect(status().isOk());

        // Validate the Eobjecttype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEobjecttypeUpdatableFieldsEquals(partialUpdatedEobjecttype, getPersistedEobjecttype(partialUpdatedEobjecttype));
    }

    @Test
    @Transactional
    void patchNonExistingEobjecttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjecttype.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEobjecttypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eobjecttype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eobjecttype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eobjecttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEobjecttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjecttype.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEobjecttypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eobjecttype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eobjecttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEobjecttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eobjecttype.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEobjecttypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(eobjecttype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eobjecttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEobjecttype() throws Exception {
        // Initialize the database
        eobjecttypeRepository.saveAndFlush(eobjecttype);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eobjecttype
        restEobjecttypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, eobjecttype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return eobjecttypeRepository.count();
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

    protected Eobjecttype getPersistedEobjecttype(Eobjecttype eobjecttype) {
        return eobjecttypeRepository.findById(eobjecttype.getId()).orElseThrow();
    }

    protected void assertPersistedEobjecttypeToMatchAllProperties(Eobjecttype expectedEobjecttype) {
        assertEobjecttypeAllPropertiesEquals(expectedEobjecttype, getPersistedEobjecttype(expectedEobjecttype));
    }

    protected void assertPersistedEobjecttypeToMatchUpdatableProperties(Eobjecttype expectedEobjecttype) {
        assertEobjecttypeAllUpdatablePropertiesEquals(expectedEobjecttype, getPersistedEobjecttype(expectedEobjecttype));
    }
}
