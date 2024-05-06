package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DatatypeAsserts.*;
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
import nl.ritense.demo.domain.Datatype;
import nl.ritense.demo.repository.DatatypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DatatypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DatatypeResourceIT {

    private static final LocalDate DEFAULT_DATUMOPNAME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMOPNAME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEFINITIE = "AAAAAAAAAA";
    private static final String UPDATED_DEFINITIE = "BBBBBBBBBB";

    private static final String DEFAULT_DOMEIN = "AAAAAAAAAA";
    private static final String UPDATED_DOMEIN = "BBBBBBBBBB";

    private static final String DEFAULT_EAGUID = "AAAAAAAAAA";
    private static final String UPDATED_EAGUID = "BBBBBBBBBB";

    private static final String DEFAULT_HERKOMST = "AAAAAAAAAA";
    private static final String UPDATED_HERKOMST = "BBBBBBBBBB";

    private static final String DEFAULT_KARDINALITEIT = "AAAAAAAAAA";
    private static final String UPDATED_KARDINALITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTE = "AAAAAAAAAA";
    private static final String UPDATED_LENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_PATROON = "AAAAAAAAAA";
    private static final String UPDATED_PATROON = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/datatypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DatatypeRepository datatypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDatatypeMockMvc;

    private Datatype datatype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Datatype createEntity(EntityManager em) {
        Datatype datatype = new Datatype()
            .datumopname(DEFAULT_DATUMOPNAME)
            .definitie(DEFAULT_DEFINITIE)
            .domein(DEFAULT_DOMEIN)
            .eaguid(DEFAULT_EAGUID)
            .herkomst(DEFAULT_HERKOMST)
            .kardinaliteit(DEFAULT_KARDINALITEIT)
            .lengte(DEFAULT_LENGTE)
            .naam(DEFAULT_NAAM)
            .patroon(DEFAULT_PATROON)
            .toelichting(DEFAULT_TOELICHTING);
        return datatype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Datatype createUpdatedEntity(EntityManager em) {
        Datatype datatype = new Datatype()
            .datumopname(UPDATED_DATUMOPNAME)
            .definitie(UPDATED_DEFINITIE)
            .domein(UPDATED_DOMEIN)
            .eaguid(UPDATED_EAGUID)
            .herkomst(UPDATED_HERKOMST)
            .kardinaliteit(UPDATED_KARDINALITEIT)
            .lengte(UPDATED_LENGTE)
            .naam(UPDATED_NAAM)
            .patroon(UPDATED_PATROON)
            .toelichting(UPDATED_TOELICHTING);
        return datatype;
    }

    @BeforeEach
    public void initTest() {
        datatype = createEntity(em);
    }

    @Test
    @Transactional
    void createDatatype() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Datatype
        var returnedDatatype = om.readValue(
            restDatatypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(datatype)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Datatype.class
        );

        // Validate the Datatype in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDatatypeUpdatableFieldsEquals(returnedDatatype, getPersistedDatatype(returnedDatatype));
    }

    @Test
    @Transactional
    void createDatatypeWithExistingId() throws Exception {
        // Create the Datatype with an existing ID
        datatype.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatatypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(datatype)))
            .andExpect(status().isBadRequest());

        // Validate the Datatype in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDatatypes() throws Exception {
        // Initialize the database
        datatypeRepository.saveAndFlush(datatype);

        // Get all the datatypeList
        restDatatypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datatype.getId())))
            .andExpect(jsonPath("$.[*].datumopname").value(hasItem(DEFAULT_DATUMOPNAME.toString())))
            .andExpect(jsonPath("$.[*].definitie").value(hasItem(DEFAULT_DEFINITIE)))
            .andExpect(jsonPath("$.[*].domein").value(hasItem(DEFAULT_DOMEIN)))
            .andExpect(jsonPath("$.[*].eaguid").value(hasItem(DEFAULT_EAGUID)))
            .andExpect(jsonPath("$.[*].herkomst").value(hasItem(DEFAULT_HERKOMST)))
            .andExpect(jsonPath("$.[*].kardinaliteit").value(hasItem(DEFAULT_KARDINALITEIT)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].patroon").value(hasItem(DEFAULT_PATROON)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)));
    }

    @Test
    @Transactional
    void getDatatype() throws Exception {
        // Initialize the database
        datatypeRepository.saveAndFlush(datatype);

        // Get the datatype
        restDatatypeMockMvc
            .perform(get(ENTITY_API_URL_ID, datatype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(datatype.getId()))
            .andExpect(jsonPath("$.datumopname").value(DEFAULT_DATUMOPNAME.toString()))
            .andExpect(jsonPath("$.definitie").value(DEFAULT_DEFINITIE))
            .andExpect(jsonPath("$.domein").value(DEFAULT_DOMEIN))
            .andExpect(jsonPath("$.eaguid").value(DEFAULT_EAGUID))
            .andExpect(jsonPath("$.herkomst").value(DEFAULT_HERKOMST))
            .andExpect(jsonPath("$.kardinaliteit").value(DEFAULT_KARDINALITEIT))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.patroon").value(DEFAULT_PATROON))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING));
    }

    @Test
    @Transactional
    void getNonExistingDatatype() throws Exception {
        // Get the datatype
        restDatatypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDatatype() throws Exception {
        // Initialize the database
        datatypeRepository.saveAndFlush(datatype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the datatype
        Datatype updatedDatatype = datatypeRepository.findById(datatype.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDatatype are not directly saved in db
        em.detach(updatedDatatype);
        updatedDatatype
            .datumopname(UPDATED_DATUMOPNAME)
            .definitie(UPDATED_DEFINITIE)
            .domein(UPDATED_DOMEIN)
            .eaguid(UPDATED_EAGUID)
            .herkomst(UPDATED_HERKOMST)
            .kardinaliteit(UPDATED_KARDINALITEIT)
            .lengte(UPDATED_LENGTE)
            .naam(UPDATED_NAAM)
            .patroon(UPDATED_PATROON)
            .toelichting(UPDATED_TOELICHTING);

        restDatatypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDatatype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDatatype))
            )
            .andExpect(status().isOk());

        // Validate the Datatype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDatatypeToMatchAllProperties(updatedDatatype);
    }

    @Test
    @Transactional
    void putNonExistingDatatype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datatype.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatatypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, datatype.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(datatype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Datatype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDatatype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datatype.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatatypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(datatype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Datatype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDatatype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datatype.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatatypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(datatype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Datatype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDatatypeWithPatch() throws Exception {
        // Initialize the database
        datatypeRepository.saveAndFlush(datatype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the datatype using partial update
        Datatype partialUpdatedDatatype = new Datatype();
        partialUpdatedDatatype.setId(datatype.getId());

        partialUpdatedDatatype
            .datumopname(UPDATED_DATUMOPNAME)
            .definitie(UPDATED_DEFINITIE)
            .eaguid(UPDATED_EAGUID)
            .kardinaliteit(UPDATED_KARDINALITEIT)
            .toelichting(UPDATED_TOELICHTING);

        restDatatypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDatatype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDatatype))
            )
            .andExpect(status().isOk());

        // Validate the Datatype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDatatypeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDatatype, datatype), getPersistedDatatype(datatype));
    }

    @Test
    @Transactional
    void fullUpdateDatatypeWithPatch() throws Exception {
        // Initialize the database
        datatypeRepository.saveAndFlush(datatype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the datatype using partial update
        Datatype partialUpdatedDatatype = new Datatype();
        partialUpdatedDatatype.setId(datatype.getId());

        partialUpdatedDatatype
            .datumopname(UPDATED_DATUMOPNAME)
            .definitie(UPDATED_DEFINITIE)
            .domein(UPDATED_DOMEIN)
            .eaguid(UPDATED_EAGUID)
            .herkomst(UPDATED_HERKOMST)
            .kardinaliteit(UPDATED_KARDINALITEIT)
            .lengte(UPDATED_LENGTE)
            .naam(UPDATED_NAAM)
            .patroon(UPDATED_PATROON)
            .toelichting(UPDATED_TOELICHTING);

        restDatatypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDatatype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDatatype))
            )
            .andExpect(status().isOk());

        // Validate the Datatype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDatatypeUpdatableFieldsEquals(partialUpdatedDatatype, getPersistedDatatype(partialUpdatedDatatype));
    }

    @Test
    @Transactional
    void patchNonExistingDatatype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datatype.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatatypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, datatype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(datatype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Datatype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDatatype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datatype.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatatypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(datatype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Datatype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDatatype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datatype.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatatypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(datatype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Datatype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDatatype() throws Exception {
        // Initialize the database
        datatypeRepository.saveAndFlush(datatype);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the datatype
        restDatatypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, datatype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return datatypeRepository.count();
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

    protected Datatype getPersistedDatatype(Datatype datatype) {
        return datatypeRepository.findById(datatype.getId()).orElseThrow();
    }

    protected void assertPersistedDatatypeToMatchAllProperties(Datatype expectedDatatype) {
        assertDatatypeAllPropertiesEquals(expectedDatatype, getPersistedDatatype(expectedDatatype));
    }

    protected void assertPersistedDatatypeToMatchUpdatableProperties(Datatype expectedDatatype) {
        assertDatatypeAllUpdatablePropertiesEquals(expectedDatatype, getPersistedDatatype(expectedDatatype));
    }
}
