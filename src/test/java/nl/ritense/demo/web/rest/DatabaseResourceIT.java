package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DatabaseAsserts.*;
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
import nl.ritense.demo.domain.Database;
import nl.ritense.demo.repository.DatabaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DatabaseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DatabaseResourceIT {

    private static final String DEFAULT_ARCHITECTUUR = "AAAAAAAAAA";
    private static final String UPDATED_ARCHITECTUUR = "BBBBBBBBBB";

    private static final String DEFAULT_DATABASE = "AAAAAAAAAA";
    private static final String UPDATED_DATABASE = "BBBBBBBBBB";

    private static final String DEFAULT_DATABASEVERSIE = "AAAAAAAAAA";
    private static final String UPDATED_DATABASEVERSIE = "BBBBBBBBBB";

    private static final String DEFAULT_DBMS = "AAAAAAAAAA";
    private static final String UPDATED_DBMS = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_OTAP = "AAAAAAAAAA";
    private static final String UPDATED_OTAP = "BBBBBBBBBB";

    private static final String DEFAULT_VLAN = "AAAAAAAAAA";
    private static final String UPDATED_VLAN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/databases";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DatabaseRepository databaseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDatabaseMockMvc;

    private Database database;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Database createEntity(EntityManager em) {
        Database database = new Database()
            .architectuur(DEFAULT_ARCHITECTUUR)
            .database(DEFAULT_DATABASE)
            .databaseversie(DEFAULT_DATABASEVERSIE)
            .dbms(DEFAULT_DBMS)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .otap(DEFAULT_OTAP)
            .vlan(DEFAULT_VLAN);
        return database;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Database createUpdatedEntity(EntityManager em) {
        Database database = new Database()
            .architectuur(UPDATED_ARCHITECTUUR)
            .database(UPDATED_DATABASE)
            .databaseversie(UPDATED_DATABASEVERSIE)
            .dbms(UPDATED_DBMS)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .otap(UPDATED_OTAP)
            .vlan(UPDATED_VLAN);
        return database;
    }

    @BeforeEach
    public void initTest() {
        database = createEntity(em);
    }

    @Test
    @Transactional
    void createDatabase() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Database
        var returnedDatabase = om.readValue(
            restDatabaseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(database)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Database.class
        );

        // Validate the Database in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDatabaseUpdatableFieldsEquals(returnedDatabase, getPersistedDatabase(returnedDatabase));
    }

    @Test
    @Transactional
    void createDatabaseWithExistingId() throws Exception {
        // Create the Database with an existing ID
        database.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatabaseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(database)))
            .andExpect(status().isBadRequest());

        // Validate the Database in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDatabases() throws Exception {
        // Initialize the database
        databaseRepository.saveAndFlush(database);

        // Get all the databaseList
        restDatabaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(database.getId().intValue())))
            .andExpect(jsonPath("$.[*].architectuur").value(hasItem(DEFAULT_ARCHITECTUUR)))
            .andExpect(jsonPath("$.[*].database").value(hasItem(DEFAULT_DATABASE)))
            .andExpect(jsonPath("$.[*].databaseversie").value(hasItem(DEFAULT_DATABASEVERSIE)))
            .andExpect(jsonPath("$.[*].dbms").value(hasItem(DEFAULT_DBMS)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].otap").value(hasItem(DEFAULT_OTAP)))
            .andExpect(jsonPath("$.[*].vlan").value(hasItem(DEFAULT_VLAN)));
    }

    @Test
    @Transactional
    void getDatabase() throws Exception {
        // Initialize the database
        databaseRepository.saveAndFlush(database);

        // Get the database
        restDatabaseMockMvc
            .perform(get(ENTITY_API_URL_ID, database.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(database.getId().intValue()))
            .andExpect(jsonPath("$.architectuur").value(DEFAULT_ARCHITECTUUR))
            .andExpect(jsonPath("$.database").value(DEFAULT_DATABASE))
            .andExpect(jsonPath("$.databaseversie").value(DEFAULT_DATABASEVERSIE))
            .andExpect(jsonPath("$.dbms").value(DEFAULT_DBMS))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.otap").value(DEFAULT_OTAP))
            .andExpect(jsonPath("$.vlan").value(DEFAULT_VLAN));
    }

    @Test
    @Transactional
    void getNonExistingDatabase() throws Exception {
        // Get the database
        restDatabaseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDatabase() throws Exception {
        // Initialize the database
        databaseRepository.saveAndFlush(database);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the database
        Database updatedDatabase = databaseRepository.findById(database.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDatabase are not directly saved in db
        em.detach(updatedDatabase);
        updatedDatabase
            .architectuur(UPDATED_ARCHITECTUUR)
            .database(UPDATED_DATABASE)
            .databaseversie(UPDATED_DATABASEVERSIE)
            .dbms(UPDATED_DBMS)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .otap(UPDATED_OTAP)
            .vlan(UPDATED_VLAN);

        restDatabaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDatabase.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDatabase))
            )
            .andExpect(status().isOk());

        // Validate the Database in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDatabaseToMatchAllProperties(updatedDatabase);
    }

    @Test
    @Transactional
    void putNonExistingDatabase() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        database.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatabaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, database.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(database))
            )
            .andExpect(status().isBadRequest());

        // Validate the Database in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDatabase() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        database.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatabaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(database))
            )
            .andExpect(status().isBadRequest());

        // Validate the Database in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDatabase() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        database.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatabaseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(database)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Database in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDatabaseWithPatch() throws Exception {
        // Initialize the database
        databaseRepository.saveAndFlush(database);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the database using partial update
        Database partialUpdatedDatabase = new Database();
        partialUpdatedDatabase.setId(database.getId());

        partialUpdatedDatabase
            .architectuur(UPDATED_ARCHITECTUUR)
            .database(UPDATED_DATABASE)
            .dbms(UPDATED_DBMS)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restDatabaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDatabase.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDatabase))
            )
            .andExpect(status().isOk());

        // Validate the Database in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDatabaseUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDatabase, database), getPersistedDatabase(database));
    }

    @Test
    @Transactional
    void fullUpdateDatabaseWithPatch() throws Exception {
        // Initialize the database
        databaseRepository.saveAndFlush(database);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the database using partial update
        Database partialUpdatedDatabase = new Database();
        partialUpdatedDatabase.setId(database.getId());

        partialUpdatedDatabase
            .architectuur(UPDATED_ARCHITECTUUR)
            .database(UPDATED_DATABASE)
            .databaseversie(UPDATED_DATABASEVERSIE)
            .dbms(UPDATED_DBMS)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .otap(UPDATED_OTAP)
            .vlan(UPDATED_VLAN);

        restDatabaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDatabase.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDatabase))
            )
            .andExpect(status().isOk());

        // Validate the Database in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDatabaseUpdatableFieldsEquals(partialUpdatedDatabase, getPersistedDatabase(partialUpdatedDatabase));
    }

    @Test
    @Transactional
    void patchNonExistingDatabase() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        database.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatabaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, database.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(database))
            )
            .andExpect(status().isBadRequest());

        // Validate the Database in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDatabase() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        database.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatabaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(database))
            )
            .andExpect(status().isBadRequest());

        // Validate the Database in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDatabase() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        database.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatabaseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(database)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Database in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDatabase() throws Exception {
        // Initialize the database
        databaseRepository.saveAndFlush(database);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the database
        restDatabaseMockMvc
            .perform(delete(ENTITY_API_URL_ID, database.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return databaseRepository.count();
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

    protected Database getPersistedDatabase(Database database) {
        return databaseRepository.findById(database.getId()).orElseThrow();
    }

    protected void assertPersistedDatabaseToMatchAllProperties(Database expectedDatabase) {
        assertDatabaseAllPropertiesEquals(expectedDatabase, getPersistedDatabase(expectedDatabase));
    }

    protected void assertPersistedDatabaseToMatchUpdatableProperties(Database expectedDatabase) {
        assertDatabaseAllUpdatablePropertiesEquals(expectedDatabase, getPersistedDatabase(expectedDatabase));
    }
}
