package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DocumentAsserts.*;
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
import nl.ritense.demo.domain.Document;
import nl.ritense.demo.repository.DocumentRepository;
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
 * Integration tests for the {@link DocumentResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class DocumentResourceIT {

    private static final String DEFAULT_COCUMENTBESCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_COCUMENTBESCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMCREATIEDOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_DATUMCREATIEDOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMONTVANGSTDOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_DATUMONTVANGSTDOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMVERZENDINGDOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_DATUMVERZENDINGDOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTAUTEUR = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTAUTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTIDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTIDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTTITEL = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTTITEL = "BBBBBBBBBB";

    private static final String DEFAULT_VERTROUWELIJKAANDUIDING = "AAAAAAAAAA";
    private static final String UPDATED_VERTROUWELIJKAANDUIDING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentMockMvc;

    private Document document;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Document createEntity(EntityManager em) {
        Document document = new Document()
            .cocumentbeschrijving(DEFAULT_COCUMENTBESCHRIJVING)
            .datumcreatiedocument(DEFAULT_DATUMCREATIEDOCUMENT)
            .datumontvangstdocument(DEFAULT_DATUMONTVANGSTDOCUMENT)
            .datumverzendingdocument(DEFAULT_DATUMVERZENDINGDOCUMENT)
            .documentauteur(DEFAULT_DOCUMENTAUTEUR)
            .documentidentificatie(DEFAULT_DOCUMENTIDENTIFICATIE)
            .documenttitel(DEFAULT_DOCUMENTTITEL)
            .vertrouwelijkaanduiding(DEFAULT_VERTROUWELIJKAANDUIDING);
        return document;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Document createUpdatedEntity(EntityManager em) {
        Document document = new Document()
            .cocumentbeschrijving(UPDATED_COCUMENTBESCHRIJVING)
            .datumcreatiedocument(UPDATED_DATUMCREATIEDOCUMENT)
            .datumontvangstdocument(UPDATED_DATUMONTVANGSTDOCUMENT)
            .datumverzendingdocument(UPDATED_DATUMVERZENDINGDOCUMENT)
            .documentauteur(UPDATED_DOCUMENTAUTEUR)
            .documentidentificatie(UPDATED_DOCUMENTIDENTIFICATIE)
            .documenttitel(UPDATED_DOCUMENTTITEL)
            .vertrouwelijkaanduiding(UPDATED_VERTROUWELIJKAANDUIDING);
        return document;
    }

    @BeforeEach
    public void initTest() {
        document = createEntity(em);
    }

    @Test
    @Transactional
    void createDocument() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Document
        var returnedDocument = om.readValue(
            restDocumentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(document)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Document.class
        );

        // Validate the Document in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDocumentUpdatableFieldsEquals(returnedDocument, getPersistedDocument(returnedDocument));
    }

    @Test
    @Transactional
    void createDocumentWithExistingId() throws Exception {
        // Create the Document with an existing ID
        document.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(document)))
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocuments() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].cocumentbeschrijving").value(hasItem(DEFAULT_COCUMENTBESCHRIJVING)))
            .andExpect(jsonPath("$.[*].datumcreatiedocument").value(hasItem(DEFAULT_DATUMCREATIEDOCUMENT)))
            .andExpect(jsonPath("$.[*].datumontvangstdocument").value(hasItem(DEFAULT_DATUMONTVANGSTDOCUMENT)))
            .andExpect(jsonPath("$.[*].datumverzendingdocument").value(hasItem(DEFAULT_DATUMVERZENDINGDOCUMENT)))
            .andExpect(jsonPath("$.[*].documentauteur").value(hasItem(DEFAULT_DOCUMENTAUTEUR)))
            .andExpect(jsonPath("$.[*].documentidentificatie").value(hasItem(DEFAULT_DOCUMENTIDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].documenttitel").value(hasItem(DEFAULT_DOCUMENTTITEL)))
            .andExpect(jsonPath("$.[*].vertrouwelijkaanduiding").value(hasItem(DEFAULT_VERTROUWELIJKAANDUIDING)));
    }

    @Test
    @Transactional
    void getDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get the document
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, document.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(document.getId().intValue()))
            .andExpect(jsonPath("$.cocumentbeschrijving").value(DEFAULT_COCUMENTBESCHRIJVING))
            .andExpect(jsonPath("$.datumcreatiedocument").value(DEFAULT_DATUMCREATIEDOCUMENT))
            .andExpect(jsonPath("$.datumontvangstdocument").value(DEFAULT_DATUMONTVANGSTDOCUMENT))
            .andExpect(jsonPath("$.datumverzendingdocument").value(DEFAULT_DATUMVERZENDINGDOCUMENT))
            .andExpect(jsonPath("$.documentauteur").value(DEFAULT_DOCUMENTAUTEUR))
            .andExpect(jsonPath("$.documentidentificatie").value(DEFAULT_DOCUMENTIDENTIFICATIE))
            .andExpect(jsonPath("$.documenttitel").value(DEFAULT_DOCUMENTTITEL))
            .andExpect(jsonPath("$.vertrouwelijkaanduiding").value(DEFAULT_VERTROUWELIJKAANDUIDING));
    }

    @Test
    @Transactional
    void getNonExistingDocument() throws Exception {
        // Get the document
        restDocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the document
        Document updatedDocument = documentRepository.findById(document.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDocument are not directly saved in db
        em.detach(updatedDocument);
        updatedDocument
            .cocumentbeschrijving(UPDATED_COCUMENTBESCHRIJVING)
            .datumcreatiedocument(UPDATED_DATUMCREATIEDOCUMENT)
            .datumontvangstdocument(UPDATED_DATUMONTVANGSTDOCUMENT)
            .datumverzendingdocument(UPDATED_DATUMVERZENDINGDOCUMENT)
            .documentauteur(UPDATED_DOCUMENTAUTEUR)
            .documentidentificatie(UPDATED_DOCUMENTIDENTIFICATIE)
            .documenttitel(UPDATED_DOCUMENTTITEL)
            .vertrouwelijkaanduiding(UPDATED_VERTROUWELIJKAANDUIDING);

        restDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDocument))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDocumentToMatchAllProperties(updatedDocument);
    }

    @Test
    @Transactional
    void putNonExistingDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        document.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, document.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(document))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        document.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(document))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        document.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(document)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Document in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentWithPatch() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the document using partial update
        Document partialUpdatedDocument = new Document();
        partialUpdatedDocument.setId(document.getId());

        partialUpdatedDocument
            .datumverzendingdocument(UPDATED_DATUMVERZENDINGDOCUMENT)
            .documentidentificatie(UPDATED_DOCUMENTIDENTIFICATIE);

        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocument))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDocument, document), getPersistedDocument(document));
    }

    @Test
    @Transactional
    void fullUpdateDocumentWithPatch() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the document using partial update
        Document partialUpdatedDocument = new Document();
        partialUpdatedDocument.setId(document.getId());

        partialUpdatedDocument
            .cocumentbeschrijving(UPDATED_COCUMENTBESCHRIJVING)
            .datumcreatiedocument(UPDATED_DATUMCREATIEDOCUMENT)
            .datumontvangstdocument(UPDATED_DATUMONTVANGSTDOCUMENT)
            .datumverzendingdocument(UPDATED_DATUMVERZENDINGDOCUMENT)
            .documentauteur(UPDATED_DOCUMENTAUTEUR)
            .documentidentificatie(UPDATED_DOCUMENTIDENTIFICATIE)
            .documenttitel(UPDATED_DOCUMENTTITEL)
            .vertrouwelijkaanduiding(UPDATED_VERTROUWELIJKAANDUIDING);

        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocument))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentUpdatableFieldsEquals(partialUpdatedDocument, getPersistedDocument(partialUpdatedDocument));
    }

    @Test
    @Transactional
    void patchNonExistingDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        document.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, document.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(document))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        document.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(document))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        document.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(document)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Document in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the document
        restDocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, document.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return documentRepository.count();
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

    protected Document getPersistedDocument(Document document) {
        return documentRepository.findById(document.getId()).orElseThrow();
    }

    protected void assertPersistedDocumentToMatchAllProperties(Document expectedDocument) {
        assertDocumentAllPropertiesEquals(expectedDocument, getPersistedDocument(expectedDocument));
    }

    protected void assertPersistedDocumentToMatchUpdatableProperties(Document expectedDocument) {
        assertDocumentAllUpdatablePropertiesEquals(expectedDocument, getPersistedDocument(expectedDocument));
    }
}
