package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LocatieaanduidingwozobjectAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Locatieaanduidingwozobject;
import nl.ritense.demo.repository.LocatieaanduidingwozobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LocatieaanduidingwozobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocatieaanduidingwozobjectResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LOCATIEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMAIR = "AAAAAAAAAA";
    private static final String UPDATED_PRIMAIR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/locatieaanduidingwozobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LocatieaanduidingwozobjectRepository locatieaanduidingwozobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocatieaanduidingwozobjectMockMvc;

    private Locatieaanduidingwozobject locatieaanduidingwozobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locatieaanduidingwozobject createEntity(EntityManager em) {
        Locatieaanduidingwozobject locatieaanduidingwozobject = new Locatieaanduidingwozobject()
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .locatieomschrijving(DEFAULT_LOCATIEOMSCHRIJVING)
            .primair(DEFAULT_PRIMAIR);
        return locatieaanduidingwozobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locatieaanduidingwozobject createUpdatedEntity(EntityManager em) {
        Locatieaanduidingwozobject locatieaanduidingwozobject = new Locatieaanduidingwozobject()
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .primair(UPDATED_PRIMAIR);
        return locatieaanduidingwozobject;
    }

    @BeforeEach
    public void initTest() {
        locatieaanduidingwozobject = createEntity(em);
    }

    @Test
    @Transactional
    void createLocatieaanduidingwozobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Locatieaanduidingwozobject
        var returnedLocatieaanduidingwozobject = om.readValue(
            restLocatieaanduidingwozobjectMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locatieaanduidingwozobject))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Locatieaanduidingwozobject.class
        );

        // Validate the Locatieaanduidingwozobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLocatieaanduidingwozobjectUpdatableFieldsEquals(
            returnedLocatieaanduidingwozobject,
            getPersistedLocatieaanduidingwozobject(returnedLocatieaanduidingwozobject)
        );
    }

    @Test
    @Transactional
    void createLocatieaanduidingwozobjectWithExistingId() throws Exception {
        // Create the Locatieaanduidingwozobject with an existing ID
        locatieaanduidingwozobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocatieaanduidingwozobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locatieaanduidingwozobject)))
            .andExpect(status().isBadRequest());

        // Validate the Locatieaanduidingwozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLocatieaanduidingwozobjects() throws Exception {
        // Initialize the database
        locatieaanduidingwozobjectRepository.saveAndFlush(locatieaanduidingwozobject);

        // Get all the locatieaanduidingwozobjectList
        restLocatieaanduidingwozobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locatieaanduidingwozobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].locatieomschrijving").value(hasItem(DEFAULT_LOCATIEOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].primair").value(hasItem(DEFAULT_PRIMAIR)));
    }

    @Test
    @Transactional
    void getLocatieaanduidingwozobject() throws Exception {
        // Initialize the database
        locatieaanduidingwozobjectRepository.saveAndFlush(locatieaanduidingwozobject);

        // Get the locatieaanduidingwozobject
        restLocatieaanduidingwozobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, locatieaanduidingwozobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locatieaanduidingwozobject.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.locatieomschrijving").value(DEFAULT_LOCATIEOMSCHRIJVING))
            .andExpect(jsonPath("$.primair").value(DEFAULT_PRIMAIR));
    }

    @Test
    @Transactional
    void getNonExistingLocatieaanduidingwozobject() throws Exception {
        // Get the locatieaanduidingwozobject
        restLocatieaanduidingwozobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocatieaanduidingwozobject() throws Exception {
        // Initialize the database
        locatieaanduidingwozobjectRepository.saveAndFlush(locatieaanduidingwozobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locatieaanduidingwozobject
        Locatieaanduidingwozobject updatedLocatieaanduidingwozobject = locatieaanduidingwozobjectRepository
            .findById(locatieaanduidingwozobject.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedLocatieaanduidingwozobject are not directly saved in db
        em.detach(updatedLocatieaanduidingwozobject);
        updatedLocatieaanduidingwozobject
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .primair(UPDATED_PRIMAIR);

        restLocatieaanduidingwozobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocatieaanduidingwozobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLocatieaanduidingwozobject))
            )
            .andExpect(status().isOk());

        // Validate the Locatieaanduidingwozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLocatieaanduidingwozobjectToMatchAllProperties(updatedLocatieaanduidingwozobject);
    }

    @Test
    @Transactional
    void putNonExistingLocatieaanduidingwozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieaanduidingwozobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocatieaanduidingwozobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locatieaanduidingwozobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locatieaanduidingwozobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatieaanduidingwozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocatieaanduidingwozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieaanduidingwozobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatieaanduidingwozobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locatieaanduidingwozobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatieaanduidingwozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocatieaanduidingwozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieaanduidingwozobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatieaanduidingwozobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locatieaanduidingwozobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locatieaanduidingwozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocatieaanduidingwozobjectWithPatch() throws Exception {
        // Initialize the database
        locatieaanduidingwozobjectRepository.saveAndFlush(locatieaanduidingwozobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locatieaanduidingwozobject using partial update
        Locatieaanduidingwozobject partialUpdatedLocatieaanduidingwozobject = new Locatieaanduidingwozobject();
        partialUpdatedLocatieaanduidingwozobject.setId(locatieaanduidingwozobject.getId());

        partialUpdatedLocatieaanduidingwozobject
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING);

        restLocatieaanduidingwozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocatieaanduidingwozobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocatieaanduidingwozobject))
            )
            .andExpect(status().isOk());

        // Validate the Locatieaanduidingwozobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocatieaanduidingwozobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLocatieaanduidingwozobject, locatieaanduidingwozobject),
            getPersistedLocatieaanduidingwozobject(locatieaanduidingwozobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateLocatieaanduidingwozobjectWithPatch() throws Exception {
        // Initialize the database
        locatieaanduidingwozobjectRepository.saveAndFlush(locatieaanduidingwozobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locatieaanduidingwozobject using partial update
        Locatieaanduidingwozobject partialUpdatedLocatieaanduidingwozobject = new Locatieaanduidingwozobject();
        partialUpdatedLocatieaanduidingwozobject.setId(locatieaanduidingwozobject.getId());

        partialUpdatedLocatieaanduidingwozobject
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .primair(UPDATED_PRIMAIR);

        restLocatieaanduidingwozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocatieaanduidingwozobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocatieaanduidingwozobject))
            )
            .andExpect(status().isOk());

        // Validate the Locatieaanduidingwozobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocatieaanduidingwozobjectUpdatableFieldsEquals(
            partialUpdatedLocatieaanduidingwozobject,
            getPersistedLocatieaanduidingwozobject(partialUpdatedLocatieaanduidingwozobject)
        );
    }

    @Test
    @Transactional
    void patchNonExistingLocatieaanduidingwozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieaanduidingwozobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocatieaanduidingwozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locatieaanduidingwozobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locatieaanduidingwozobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatieaanduidingwozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocatieaanduidingwozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieaanduidingwozobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatieaanduidingwozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locatieaanduidingwozobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatieaanduidingwozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocatieaanduidingwozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieaanduidingwozobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatieaanduidingwozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(locatieaanduidingwozobject))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locatieaanduidingwozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocatieaanduidingwozobject() throws Exception {
        // Initialize the database
        locatieaanduidingwozobjectRepository.saveAndFlush(locatieaanduidingwozobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the locatieaanduidingwozobject
        restLocatieaanduidingwozobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, locatieaanduidingwozobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return locatieaanduidingwozobjectRepository.count();
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

    protected Locatieaanduidingwozobject getPersistedLocatieaanduidingwozobject(Locatieaanduidingwozobject locatieaanduidingwozobject) {
        return locatieaanduidingwozobjectRepository.findById(locatieaanduidingwozobject.getId()).orElseThrow();
    }

    protected void assertPersistedLocatieaanduidingwozobjectToMatchAllProperties(
        Locatieaanduidingwozobject expectedLocatieaanduidingwozobject
    ) {
        assertLocatieaanduidingwozobjectAllPropertiesEquals(
            expectedLocatieaanduidingwozobject,
            getPersistedLocatieaanduidingwozobject(expectedLocatieaanduidingwozobject)
        );
    }

    protected void assertPersistedLocatieaanduidingwozobjectToMatchUpdatableProperties(
        Locatieaanduidingwozobject expectedLocatieaanduidingwozobject
    ) {
        assertLocatieaanduidingwozobjectAllUpdatablePropertiesEquals(
            expectedLocatieaanduidingwozobject,
            getPersistedLocatieaanduidingwozobject(expectedLocatieaanduidingwozobject)
        );
    }
}
