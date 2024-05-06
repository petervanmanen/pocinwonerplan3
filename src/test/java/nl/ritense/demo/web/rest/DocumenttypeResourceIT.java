package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DocumenttypeAsserts.*;
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
import nl.ritense.demo.domain.Documenttype;
import nl.ritense.demo.repository.DocumenttypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DocumenttypeResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class DocumenttypeResourceIT {

    private static final String DEFAULT_DATUMBEGINGELDIGHEIDDOCUMENTTYPE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMBEGINGELDIGHEIDDOCUMENTTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDEGELDIGHEIDDOCUMENTTYPE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDEGELDIGHEIDDOCUMENTTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTCATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTCATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTTYPEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTTYPEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTTYPEOMSCHRIJVINGGENERIEK = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTTYPEOMSCHRIJVINGGENERIEK = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTTYPETREFWOORD = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTTYPETREFWOORD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/documenttypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DocumenttypeRepository documenttypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumenttypeMockMvc;

    private Documenttype documenttype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documenttype createEntity(EntityManager em) {
        Documenttype documenttype = new Documenttype()
            .datumbegingeldigheiddocumenttype(DEFAULT_DATUMBEGINGELDIGHEIDDOCUMENTTYPE)
            .datumeindegeldigheiddocumenttype(DEFAULT_DATUMEINDEGELDIGHEIDDOCUMENTTYPE)
            .documentcategorie(DEFAULT_DOCUMENTCATEGORIE)
            .documenttypeomschrijving(DEFAULT_DOCUMENTTYPEOMSCHRIJVING)
            .documenttypeomschrijvinggeneriek(DEFAULT_DOCUMENTTYPEOMSCHRIJVINGGENERIEK)
            .documenttypetrefwoord(DEFAULT_DOCUMENTTYPETREFWOORD);
        return documenttype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documenttype createUpdatedEntity(EntityManager em) {
        Documenttype documenttype = new Documenttype()
            .datumbegingeldigheiddocumenttype(UPDATED_DATUMBEGINGELDIGHEIDDOCUMENTTYPE)
            .datumeindegeldigheiddocumenttype(UPDATED_DATUMEINDEGELDIGHEIDDOCUMENTTYPE)
            .documentcategorie(UPDATED_DOCUMENTCATEGORIE)
            .documenttypeomschrijving(UPDATED_DOCUMENTTYPEOMSCHRIJVING)
            .documenttypeomschrijvinggeneriek(UPDATED_DOCUMENTTYPEOMSCHRIJVINGGENERIEK)
            .documenttypetrefwoord(UPDATED_DOCUMENTTYPETREFWOORD);
        return documenttype;
    }

    @BeforeEach
    public void initTest() {
        documenttype = createEntity(em);
    }

    @Test
    @Transactional
    void createDocumenttype() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Documenttype
        var returnedDocumenttype = om.readValue(
            restDocumenttypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documenttype)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Documenttype.class
        );

        // Validate the Documenttype in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDocumenttypeUpdatableFieldsEquals(returnedDocumenttype, getPersistedDocumenttype(returnedDocumenttype));
    }

    @Test
    @Transactional
    void createDocumenttypeWithExistingId() throws Exception {
        // Create the Documenttype with an existing ID
        documenttype.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumenttypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documenttype)))
            .andExpect(status().isBadRequest());

        // Validate the Documenttype in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocumenttypes() throws Exception {
        // Initialize the database
        documenttypeRepository.saveAndFlush(documenttype);

        // Get all the documenttypeList
        restDocumenttypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documenttype.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheiddocumenttype").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDDOCUMENTTYPE)))
            .andExpect(jsonPath("$.[*].datumeindegeldigheiddocumenttype").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDDOCUMENTTYPE)))
            .andExpect(jsonPath("$.[*].documentcategorie").value(hasItem(DEFAULT_DOCUMENTCATEGORIE)))
            .andExpect(jsonPath("$.[*].documenttypeomschrijving").value(hasItem(DEFAULT_DOCUMENTTYPEOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].documenttypeomschrijvinggeneriek").value(hasItem(DEFAULT_DOCUMENTTYPEOMSCHRIJVINGGENERIEK)))
            .andExpect(jsonPath("$.[*].documenttypetrefwoord").value(hasItem(DEFAULT_DOCUMENTTYPETREFWOORD)));
    }

    @Test
    @Transactional
    void getDocumenttype() throws Exception {
        // Initialize the database
        documenttypeRepository.saveAndFlush(documenttype);

        // Get the documenttype
        restDocumenttypeMockMvc
            .perform(get(ENTITY_API_URL_ID, documenttype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documenttype.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheiddocumenttype").value(DEFAULT_DATUMBEGINGELDIGHEIDDOCUMENTTYPE))
            .andExpect(jsonPath("$.datumeindegeldigheiddocumenttype").value(DEFAULT_DATUMEINDEGELDIGHEIDDOCUMENTTYPE))
            .andExpect(jsonPath("$.documentcategorie").value(DEFAULT_DOCUMENTCATEGORIE))
            .andExpect(jsonPath("$.documenttypeomschrijving").value(DEFAULT_DOCUMENTTYPEOMSCHRIJVING))
            .andExpect(jsonPath("$.documenttypeomschrijvinggeneriek").value(DEFAULT_DOCUMENTTYPEOMSCHRIJVINGGENERIEK))
            .andExpect(jsonPath("$.documenttypetrefwoord").value(DEFAULT_DOCUMENTTYPETREFWOORD));
    }

    @Test
    @Transactional
    void getNonExistingDocumenttype() throws Exception {
        // Get the documenttype
        restDocumenttypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocumenttype() throws Exception {
        // Initialize the database
        documenttypeRepository.saveAndFlush(documenttype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documenttype
        Documenttype updatedDocumenttype = documenttypeRepository.findById(documenttype.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDocumenttype are not directly saved in db
        em.detach(updatedDocumenttype);
        updatedDocumenttype
            .datumbegingeldigheiddocumenttype(UPDATED_DATUMBEGINGELDIGHEIDDOCUMENTTYPE)
            .datumeindegeldigheiddocumenttype(UPDATED_DATUMEINDEGELDIGHEIDDOCUMENTTYPE)
            .documentcategorie(UPDATED_DOCUMENTCATEGORIE)
            .documenttypeomschrijving(UPDATED_DOCUMENTTYPEOMSCHRIJVING)
            .documenttypeomschrijvinggeneriek(UPDATED_DOCUMENTTYPEOMSCHRIJVINGGENERIEK)
            .documenttypetrefwoord(UPDATED_DOCUMENTTYPETREFWOORD);

        restDocumenttypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocumenttype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDocumenttype))
            )
            .andExpect(status().isOk());

        // Validate the Documenttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDocumenttypeToMatchAllProperties(updatedDocumenttype);
    }

    @Test
    @Transactional
    void putNonExistingDocumenttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documenttype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumenttypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documenttype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documenttype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documenttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumenttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documenttype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumenttypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documenttype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documenttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumenttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documenttype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumenttypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documenttype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Documenttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumenttypeWithPatch() throws Exception {
        // Initialize the database
        documenttypeRepository.saveAndFlush(documenttype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documenttype using partial update
        Documenttype partialUpdatedDocumenttype = new Documenttype();
        partialUpdatedDocumenttype.setId(documenttype.getId());

        partialUpdatedDocumenttype.documentcategorie(UPDATED_DOCUMENTCATEGORIE).documenttypetrefwoord(UPDATED_DOCUMENTTYPETREFWOORD);

        restDocumenttypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumenttype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumenttype))
            )
            .andExpect(status().isOk());

        // Validate the Documenttype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumenttypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDocumenttype, documenttype),
            getPersistedDocumenttype(documenttype)
        );
    }

    @Test
    @Transactional
    void fullUpdateDocumenttypeWithPatch() throws Exception {
        // Initialize the database
        documenttypeRepository.saveAndFlush(documenttype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documenttype using partial update
        Documenttype partialUpdatedDocumenttype = new Documenttype();
        partialUpdatedDocumenttype.setId(documenttype.getId());

        partialUpdatedDocumenttype
            .datumbegingeldigheiddocumenttype(UPDATED_DATUMBEGINGELDIGHEIDDOCUMENTTYPE)
            .datumeindegeldigheiddocumenttype(UPDATED_DATUMEINDEGELDIGHEIDDOCUMENTTYPE)
            .documentcategorie(UPDATED_DOCUMENTCATEGORIE)
            .documenttypeomschrijving(UPDATED_DOCUMENTTYPEOMSCHRIJVING)
            .documenttypeomschrijvinggeneriek(UPDATED_DOCUMENTTYPEOMSCHRIJVINGGENERIEK)
            .documenttypetrefwoord(UPDATED_DOCUMENTTYPETREFWOORD);

        restDocumenttypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumenttype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumenttype))
            )
            .andExpect(status().isOk());

        // Validate the Documenttype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumenttypeUpdatableFieldsEquals(partialUpdatedDocumenttype, getPersistedDocumenttype(partialUpdatedDocumenttype));
    }

    @Test
    @Transactional
    void patchNonExistingDocumenttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documenttype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumenttypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documenttype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documenttype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documenttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumenttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documenttype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumenttypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documenttype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documenttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumenttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documenttype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumenttypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(documenttype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Documenttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumenttype() throws Exception {
        // Initialize the database
        documenttypeRepository.saveAndFlush(documenttype);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the documenttype
        restDocumenttypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, documenttype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return documenttypeRepository.count();
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

    protected Documenttype getPersistedDocumenttype(Documenttype documenttype) {
        return documenttypeRepository.findById(documenttype.getId()).orElseThrow();
    }

    protected void assertPersistedDocumenttypeToMatchAllProperties(Documenttype expectedDocumenttype) {
        assertDocumenttypeAllPropertiesEquals(expectedDocumenttype, getPersistedDocumenttype(expectedDocumenttype));
    }

    protected void assertPersistedDocumenttypeToMatchUpdatableProperties(Documenttype expectedDocumenttype) {
        assertDocumenttypeAllUpdatablePropertiesEquals(expectedDocumenttype, getPersistedDocumenttype(expectedDocumenttype));
    }
}
