package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OpdrachtgeverAsserts.*;
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
import nl.ritense.demo.domain.Opdrachtgever;
import nl.ritense.demo.repository.OpdrachtgeverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OpdrachtgeverResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OpdrachtgeverResourceIT {

    private static final String DEFAULT_CLUSTERCODE = "AAAAAAAAAA";
    private static final String UPDATED_CLUSTERCODE = "BBBBBBBBBB";

    private static final String DEFAULT_CLUSTEROMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_CLUSTEROMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/opdrachtgevers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OpdrachtgeverRepository opdrachtgeverRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpdrachtgeverMockMvc;

    private Opdrachtgever opdrachtgever;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opdrachtgever createEntity(EntityManager em) {
        Opdrachtgever opdrachtgever = new Opdrachtgever()
            .clustercode(DEFAULT_CLUSTERCODE)
            .clusteromschrijving(DEFAULT_CLUSTEROMSCHRIJVING)
            .naam(DEFAULT_NAAM)
            .nummer(DEFAULT_NUMMER)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return opdrachtgever;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opdrachtgever createUpdatedEntity(EntityManager em) {
        Opdrachtgever opdrachtgever = new Opdrachtgever()
            .clustercode(UPDATED_CLUSTERCODE)
            .clusteromschrijving(UPDATED_CLUSTEROMSCHRIJVING)
            .naam(UPDATED_NAAM)
            .nummer(UPDATED_NUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return opdrachtgever;
    }

    @BeforeEach
    public void initTest() {
        opdrachtgever = createEntity(em);
    }

    @Test
    @Transactional
    void createOpdrachtgever() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Opdrachtgever
        var returnedOpdrachtgever = om.readValue(
            restOpdrachtgeverMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opdrachtgever)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Opdrachtgever.class
        );

        // Validate the Opdrachtgever in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOpdrachtgeverUpdatableFieldsEquals(returnedOpdrachtgever, getPersistedOpdrachtgever(returnedOpdrachtgever));
    }

    @Test
    @Transactional
    void createOpdrachtgeverWithExistingId() throws Exception {
        // Create the Opdrachtgever with an existing ID
        opdrachtgever.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpdrachtgeverMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opdrachtgever)))
            .andExpect(status().isBadRequest());

        // Validate the Opdrachtgever in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOpdrachtgevers() throws Exception {
        // Initialize the database
        opdrachtgeverRepository.saveAndFlush(opdrachtgever);

        // Get all the opdrachtgeverList
        restOpdrachtgeverMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opdrachtgever.getId().intValue())))
            .andExpect(jsonPath("$.[*].clustercode").value(hasItem(DEFAULT_CLUSTERCODE)))
            .andExpect(jsonPath("$.[*].clusteromschrijving").value(hasItem(DEFAULT_CLUSTEROMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getOpdrachtgever() throws Exception {
        // Initialize the database
        opdrachtgeverRepository.saveAndFlush(opdrachtgever);

        // Get the opdrachtgever
        restOpdrachtgeverMockMvc
            .perform(get(ENTITY_API_URL_ID, opdrachtgever.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(opdrachtgever.getId().intValue()))
            .andExpect(jsonPath("$.clustercode").value(DEFAULT_CLUSTERCODE))
            .andExpect(jsonPath("$.clusteromschrijving").value(DEFAULT_CLUSTEROMSCHRIJVING))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingOpdrachtgever() throws Exception {
        // Get the opdrachtgever
        restOpdrachtgeverMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOpdrachtgever() throws Exception {
        // Initialize the database
        opdrachtgeverRepository.saveAndFlush(opdrachtgever);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opdrachtgever
        Opdrachtgever updatedOpdrachtgever = opdrachtgeverRepository.findById(opdrachtgever.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOpdrachtgever are not directly saved in db
        em.detach(updatedOpdrachtgever);
        updatedOpdrachtgever
            .clustercode(UPDATED_CLUSTERCODE)
            .clusteromschrijving(UPDATED_CLUSTEROMSCHRIJVING)
            .naam(UPDATED_NAAM)
            .nummer(UPDATED_NUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restOpdrachtgeverMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOpdrachtgever.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOpdrachtgever))
            )
            .andExpect(status().isOk());

        // Validate the Opdrachtgever in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOpdrachtgeverToMatchAllProperties(updatedOpdrachtgever);
    }

    @Test
    @Transactional
    void putNonExistingOpdrachtgever() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opdrachtgever.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpdrachtgeverMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opdrachtgever.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(opdrachtgever))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opdrachtgever in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOpdrachtgever() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opdrachtgever.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpdrachtgeverMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(opdrachtgever))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opdrachtgever in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOpdrachtgever() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opdrachtgever.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpdrachtgeverMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opdrachtgever)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Opdrachtgever in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOpdrachtgeverWithPatch() throws Exception {
        // Initialize the database
        opdrachtgeverRepository.saveAndFlush(opdrachtgever);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opdrachtgever using partial update
        Opdrachtgever partialUpdatedOpdrachtgever = new Opdrachtgever();
        partialUpdatedOpdrachtgever.setId(opdrachtgever.getId());

        partialUpdatedOpdrachtgever
            .clustercode(UPDATED_CLUSTERCODE)
            .clusteromschrijving(UPDATED_CLUSTEROMSCHRIJVING)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restOpdrachtgeverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpdrachtgever.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpdrachtgever))
            )
            .andExpect(status().isOk());

        // Validate the Opdrachtgever in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpdrachtgeverUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOpdrachtgever, opdrachtgever),
            getPersistedOpdrachtgever(opdrachtgever)
        );
    }

    @Test
    @Transactional
    void fullUpdateOpdrachtgeverWithPatch() throws Exception {
        // Initialize the database
        opdrachtgeverRepository.saveAndFlush(opdrachtgever);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opdrachtgever using partial update
        Opdrachtgever partialUpdatedOpdrachtgever = new Opdrachtgever();
        partialUpdatedOpdrachtgever.setId(opdrachtgever.getId());

        partialUpdatedOpdrachtgever
            .clustercode(UPDATED_CLUSTERCODE)
            .clusteromschrijving(UPDATED_CLUSTEROMSCHRIJVING)
            .naam(UPDATED_NAAM)
            .nummer(UPDATED_NUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restOpdrachtgeverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpdrachtgever.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpdrachtgever))
            )
            .andExpect(status().isOk());

        // Validate the Opdrachtgever in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpdrachtgeverUpdatableFieldsEquals(partialUpdatedOpdrachtgever, getPersistedOpdrachtgever(partialUpdatedOpdrachtgever));
    }

    @Test
    @Transactional
    void patchNonExistingOpdrachtgever() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opdrachtgever.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpdrachtgeverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, opdrachtgever.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(opdrachtgever))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opdrachtgever in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOpdrachtgever() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opdrachtgever.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpdrachtgeverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(opdrachtgever))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opdrachtgever in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOpdrachtgever() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opdrachtgever.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpdrachtgeverMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(opdrachtgever)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Opdrachtgever in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOpdrachtgever() throws Exception {
        // Initialize the database
        opdrachtgeverRepository.saveAndFlush(opdrachtgever);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the opdrachtgever
        restOpdrachtgeverMockMvc
            .perform(delete(ENTITY_API_URL_ID, opdrachtgever.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return opdrachtgeverRepository.count();
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

    protected Opdrachtgever getPersistedOpdrachtgever(Opdrachtgever opdrachtgever) {
        return opdrachtgeverRepository.findById(opdrachtgever.getId()).orElseThrow();
    }

    protected void assertPersistedOpdrachtgeverToMatchAllProperties(Opdrachtgever expectedOpdrachtgever) {
        assertOpdrachtgeverAllPropertiesEquals(expectedOpdrachtgever, getPersistedOpdrachtgever(expectedOpdrachtgever));
    }

    protected void assertPersistedOpdrachtgeverToMatchUpdatableProperties(Opdrachtgever expectedOpdrachtgever) {
        assertOpdrachtgeverAllUpdatablePropertiesEquals(expectedOpdrachtgever, getPersistedOpdrachtgever(expectedOpdrachtgever));
    }
}
