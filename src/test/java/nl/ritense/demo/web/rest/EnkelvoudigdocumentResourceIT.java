package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EnkelvoudigdocumentAsserts.*;
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
import nl.ritense.demo.domain.Enkelvoudigdocument;
import nl.ritense.demo.repository.EnkelvoudigdocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EnkelvoudigdocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EnkelvoudigdocumentResourceIT {

    private static final String DEFAULT_BESTANDSNAAM = "AAAAAAAAAA";
    private static final String UPDATED_BESTANDSNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTFORMAAT = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTFORMAAT = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTINHOUD = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTINHOUD = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTLINK = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTLINK = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTSTATUS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTSTATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTTAAL = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTTAAL = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTVERSIE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTVERSIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/enkelvoudigdocuments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EnkelvoudigdocumentRepository enkelvoudigdocumentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnkelvoudigdocumentMockMvc;

    private Enkelvoudigdocument enkelvoudigdocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enkelvoudigdocument createEntity(EntityManager em) {
        Enkelvoudigdocument enkelvoudigdocument = new Enkelvoudigdocument()
            .bestandsnaam(DEFAULT_BESTANDSNAAM)
            .documentformaat(DEFAULT_DOCUMENTFORMAAT)
            .documentinhoud(DEFAULT_DOCUMENTINHOUD)
            .documentlink(DEFAULT_DOCUMENTLINK)
            .documentstatus(DEFAULT_DOCUMENTSTATUS)
            .documenttaal(DEFAULT_DOCUMENTTAAL)
            .documentversie(DEFAULT_DOCUMENTVERSIE);
        return enkelvoudigdocument;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enkelvoudigdocument createUpdatedEntity(EntityManager em) {
        Enkelvoudigdocument enkelvoudigdocument = new Enkelvoudigdocument()
            .bestandsnaam(UPDATED_BESTANDSNAAM)
            .documentformaat(UPDATED_DOCUMENTFORMAAT)
            .documentinhoud(UPDATED_DOCUMENTINHOUD)
            .documentlink(UPDATED_DOCUMENTLINK)
            .documentstatus(UPDATED_DOCUMENTSTATUS)
            .documenttaal(UPDATED_DOCUMENTTAAL)
            .documentversie(UPDATED_DOCUMENTVERSIE);
        return enkelvoudigdocument;
    }

    @BeforeEach
    public void initTest() {
        enkelvoudigdocument = createEntity(em);
    }

    @Test
    @Transactional
    void createEnkelvoudigdocument() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Enkelvoudigdocument
        var returnedEnkelvoudigdocument = om.readValue(
            restEnkelvoudigdocumentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enkelvoudigdocument)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Enkelvoudigdocument.class
        );

        // Validate the Enkelvoudigdocument in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEnkelvoudigdocumentUpdatableFieldsEquals(
            returnedEnkelvoudigdocument,
            getPersistedEnkelvoudigdocument(returnedEnkelvoudigdocument)
        );
    }

    @Test
    @Transactional
    void createEnkelvoudigdocumentWithExistingId() throws Exception {
        // Create the Enkelvoudigdocument with an existing ID
        enkelvoudigdocument.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnkelvoudigdocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enkelvoudigdocument)))
            .andExpect(status().isBadRequest());

        // Validate the Enkelvoudigdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEnkelvoudigdocuments() throws Exception {
        // Initialize the database
        enkelvoudigdocumentRepository.saveAndFlush(enkelvoudigdocument);

        // Get all the enkelvoudigdocumentList
        restEnkelvoudigdocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enkelvoudigdocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].bestandsnaam").value(hasItem(DEFAULT_BESTANDSNAAM)))
            .andExpect(jsonPath("$.[*].documentformaat").value(hasItem(DEFAULT_DOCUMENTFORMAAT)))
            .andExpect(jsonPath("$.[*].documentinhoud").value(hasItem(DEFAULT_DOCUMENTINHOUD)))
            .andExpect(jsonPath("$.[*].documentlink").value(hasItem(DEFAULT_DOCUMENTLINK)))
            .andExpect(jsonPath("$.[*].documentstatus").value(hasItem(DEFAULT_DOCUMENTSTATUS)))
            .andExpect(jsonPath("$.[*].documenttaal").value(hasItem(DEFAULT_DOCUMENTTAAL)))
            .andExpect(jsonPath("$.[*].documentversie").value(hasItem(DEFAULT_DOCUMENTVERSIE)));
    }

    @Test
    @Transactional
    void getEnkelvoudigdocument() throws Exception {
        // Initialize the database
        enkelvoudigdocumentRepository.saveAndFlush(enkelvoudigdocument);

        // Get the enkelvoudigdocument
        restEnkelvoudigdocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, enkelvoudigdocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enkelvoudigdocument.getId().intValue()))
            .andExpect(jsonPath("$.bestandsnaam").value(DEFAULT_BESTANDSNAAM))
            .andExpect(jsonPath("$.documentformaat").value(DEFAULT_DOCUMENTFORMAAT))
            .andExpect(jsonPath("$.documentinhoud").value(DEFAULT_DOCUMENTINHOUD))
            .andExpect(jsonPath("$.documentlink").value(DEFAULT_DOCUMENTLINK))
            .andExpect(jsonPath("$.documentstatus").value(DEFAULT_DOCUMENTSTATUS))
            .andExpect(jsonPath("$.documenttaal").value(DEFAULT_DOCUMENTTAAL))
            .andExpect(jsonPath("$.documentversie").value(DEFAULT_DOCUMENTVERSIE));
    }

    @Test
    @Transactional
    void getNonExistingEnkelvoudigdocument() throws Exception {
        // Get the enkelvoudigdocument
        restEnkelvoudigdocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEnkelvoudigdocument() throws Exception {
        // Initialize the database
        enkelvoudigdocumentRepository.saveAndFlush(enkelvoudigdocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enkelvoudigdocument
        Enkelvoudigdocument updatedEnkelvoudigdocument = enkelvoudigdocumentRepository.findById(enkelvoudigdocument.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEnkelvoudigdocument are not directly saved in db
        em.detach(updatedEnkelvoudigdocument);
        updatedEnkelvoudigdocument
            .bestandsnaam(UPDATED_BESTANDSNAAM)
            .documentformaat(UPDATED_DOCUMENTFORMAAT)
            .documentinhoud(UPDATED_DOCUMENTINHOUD)
            .documentlink(UPDATED_DOCUMENTLINK)
            .documentstatus(UPDATED_DOCUMENTSTATUS)
            .documenttaal(UPDATED_DOCUMENTTAAL)
            .documentversie(UPDATED_DOCUMENTVERSIE);

        restEnkelvoudigdocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEnkelvoudigdocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEnkelvoudigdocument))
            )
            .andExpect(status().isOk());

        // Validate the Enkelvoudigdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEnkelvoudigdocumentToMatchAllProperties(updatedEnkelvoudigdocument);
    }

    @Test
    @Transactional
    void putNonExistingEnkelvoudigdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enkelvoudigdocument.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnkelvoudigdocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enkelvoudigdocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(enkelvoudigdocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enkelvoudigdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnkelvoudigdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enkelvoudigdocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnkelvoudigdocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(enkelvoudigdocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enkelvoudigdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnkelvoudigdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enkelvoudigdocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnkelvoudigdocumentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enkelvoudigdocument)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enkelvoudigdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnkelvoudigdocumentWithPatch() throws Exception {
        // Initialize the database
        enkelvoudigdocumentRepository.saveAndFlush(enkelvoudigdocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enkelvoudigdocument using partial update
        Enkelvoudigdocument partialUpdatedEnkelvoudigdocument = new Enkelvoudigdocument();
        partialUpdatedEnkelvoudigdocument.setId(enkelvoudigdocument.getId());

        partialUpdatedEnkelvoudigdocument
            .documentlink(UPDATED_DOCUMENTLINK)
            .documentstatus(UPDATED_DOCUMENTSTATUS)
            .documentversie(UPDATED_DOCUMENTVERSIE);

        restEnkelvoudigdocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnkelvoudigdocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnkelvoudigdocument))
            )
            .andExpect(status().isOk());

        // Validate the Enkelvoudigdocument in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnkelvoudigdocumentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEnkelvoudigdocument, enkelvoudigdocument),
            getPersistedEnkelvoudigdocument(enkelvoudigdocument)
        );
    }

    @Test
    @Transactional
    void fullUpdateEnkelvoudigdocumentWithPatch() throws Exception {
        // Initialize the database
        enkelvoudigdocumentRepository.saveAndFlush(enkelvoudigdocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enkelvoudigdocument using partial update
        Enkelvoudigdocument partialUpdatedEnkelvoudigdocument = new Enkelvoudigdocument();
        partialUpdatedEnkelvoudigdocument.setId(enkelvoudigdocument.getId());

        partialUpdatedEnkelvoudigdocument
            .bestandsnaam(UPDATED_BESTANDSNAAM)
            .documentformaat(UPDATED_DOCUMENTFORMAAT)
            .documentinhoud(UPDATED_DOCUMENTINHOUD)
            .documentlink(UPDATED_DOCUMENTLINK)
            .documentstatus(UPDATED_DOCUMENTSTATUS)
            .documenttaal(UPDATED_DOCUMENTTAAL)
            .documentversie(UPDATED_DOCUMENTVERSIE);

        restEnkelvoudigdocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnkelvoudigdocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnkelvoudigdocument))
            )
            .andExpect(status().isOk());

        // Validate the Enkelvoudigdocument in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnkelvoudigdocumentUpdatableFieldsEquals(
            partialUpdatedEnkelvoudigdocument,
            getPersistedEnkelvoudigdocument(partialUpdatedEnkelvoudigdocument)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEnkelvoudigdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enkelvoudigdocument.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnkelvoudigdocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enkelvoudigdocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enkelvoudigdocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enkelvoudigdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnkelvoudigdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enkelvoudigdocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnkelvoudigdocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enkelvoudigdocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enkelvoudigdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnkelvoudigdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enkelvoudigdocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnkelvoudigdocumentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(enkelvoudigdocument)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enkelvoudigdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnkelvoudigdocument() throws Exception {
        // Initialize the database
        enkelvoudigdocumentRepository.saveAndFlush(enkelvoudigdocument);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the enkelvoudigdocument
        restEnkelvoudigdocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, enkelvoudigdocument.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return enkelvoudigdocumentRepository.count();
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

    protected Enkelvoudigdocument getPersistedEnkelvoudigdocument(Enkelvoudigdocument enkelvoudigdocument) {
        return enkelvoudigdocumentRepository.findById(enkelvoudigdocument.getId()).orElseThrow();
    }

    protected void assertPersistedEnkelvoudigdocumentToMatchAllProperties(Enkelvoudigdocument expectedEnkelvoudigdocument) {
        assertEnkelvoudigdocumentAllPropertiesEquals(
            expectedEnkelvoudigdocument,
            getPersistedEnkelvoudigdocument(expectedEnkelvoudigdocument)
        );
    }

    protected void assertPersistedEnkelvoudigdocumentToMatchUpdatableProperties(Enkelvoudigdocument expectedEnkelvoudigdocument) {
        assertEnkelvoudigdocumentAllUpdatablePropertiesEquals(
            expectedEnkelvoudigdocument,
            getPersistedEnkelvoudigdocument(expectedEnkelvoudigdocument)
        );
    }
}
