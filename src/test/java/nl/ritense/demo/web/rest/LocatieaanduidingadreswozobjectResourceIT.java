package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LocatieaanduidingadreswozobjectAsserts.*;
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
import nl.ritense.demo.domain.Locatieaanduidingadreswozobject;
import nl.ritense.demo.repository.LocatieaanduidingadreswozobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LocatieaanduidingadreswozobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocatieaanduidingadreswozobjectResourceIT {

    private static final String DEFAULT_LOCATIEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/locatieaanduidingadreswozobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LocatieaanduidingadreswozobjectRepository locatieaanduidingadreswozobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocatieaanduidingadreswozobjectMockMvc;

    private Locatieaanduidingadreswozobject locatieaanduidingadreswozobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locatieaanduidingadreswozobject createEntity(EntityManager em) {
        Locatieaanduidingadreswozobject locatieaanduidingadreswozobject = new Locatieaanduidingadreswozobject()
            .locatieomschrijving(DEFAULT_LOCATIEOMSCHRIJVING);
        return locatieaanduidingadreswozobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locatieaanduidingadreswozobject createUpdatedEntity(EntityManager em) {
        Locatieaanduidingadreswozobject locatieaanduidingadreswozobject = new Locatieaanduidingadreswozobject()
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING);
        return locatieaanduidingadreswozobject;
    }

    @BeforeEach
    public void initTest() {
        locatieaanduidingadreswozobject = createEntity(em);
    }

    @Test
    @Transactional
    void createLocatieaanduidingadreswozobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Locatieaanduidingadreswozobject
        var returnedLocatieaanduidingadreswozobject = om.readValue(
            restLocatieaanduidingadreswozobjectMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(locatieaanduidingadreswozobject))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Locatieaanduidingadreswozobject.class
        );

        // Validate the Locatieaanduidingadreswozobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLocatieaanduidingadreswozobjectUpdatableFieldsEquals(
            returnedLocatieaanduidingadreswozobject,
            getPersistedLocatieaanduidingadreswozobject(returnedLocatieaanduidingadreswozobject)
        );
    }

    @Test
    @Transactional
    void createLocatieaanduidingadreswozobjectWithExistingId() throws Exception {
        // Create the Locatieaanduidingadreswozobject with an existing ID
        locatieaanduidingadreswozobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocatieaanduidingadreswozobjectMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locatieaanduidingadreswozobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatieaanduidingadreswozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLocatieaanduidingadreswozobjects() throws Exception {
        // Initialize the database
        locatieaanduidingadreswozobjectRepository.saveAndFlush(locatieaanduidingadreswozobject);

        // Get all the locatieaanduidingadreswozobjectList
        restLocatieaanduidingadreswozobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locatieaanduidingadreswozobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].locatieomschrijving").value(hasItem(DEFAULT_LOCATIEOMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getLocatieaanduidingadreswozobject() throws Exception {
        // Initialize the database
        locatieaanduidingadreswozobjectRepository.saveAndFlush(locatieaanduidingadreswozobject);

        // Get the locatieaanduidingadreswozobject
        restLocatieaanduidingadreswozobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, locatieaanduidingadreswozobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locatieaanduidingadreswozobject.getId().intValue()))
            .andExpect(jsonPath("$.locatieomschrijving").value(DEFAULT_LOCATIEOMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingLocatieaanduidingadreswozobject() throws Exception {
        // Get the locatieaanduidingadreswozobject
        restLocatieaanduidingadreswozobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocatieaanduidingadreswozobject() throws Exception {
        // Initialize the database
        locatieaanduidingadreswozobjectRepository.saveAndFlush(locatieaanduidingadreswozobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locatieaanduidingadreswozobject
        Locatieaanduidingadreswozobject updatedLocatieaanduidingadreswozobject = locatieaanduidingadreswozobjectRepository
            .findById(locatieaanduidingadreswozobject.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedLocatieaanduidingadreswozobject are not directly saved in db
        em.detach(updatedLocatieaanduidingadreswozobject);
        updatedLocatieaanduidingadreswozobject.locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING);

        restLocatieaanduidingadreswozobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocatieaanduidingadreswozobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLocatieaanduidingadreswozobject))
            )
            .andExpect(status().isOk());

        // Validate the Locatieaanduidingadreswozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLocatieaanduidingadreswozobjectToMatchAllProperties(updatedLocatieaanduidingadreswozobject);
    }

    @Test
    @Transactional
    void putNonExistingLocatieaanduidingadreswozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieaanduidingadreswozobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocatieaanduidingadreswozobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locatieaanduidingadreswozobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locatieaanduidingadreswozobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatieaanduidingadreswozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocatieaanduidingadreswozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieaanduidingadreswozobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatieaanduidingadreswozobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locatieaanduidingadreswozobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatieaanduidingadreswozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocatieaanduidingadreswozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieaanduidingadreswozobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatieaanduidingadreswozobjectMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locatieaanduidingadreswozobject))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locatieaanduidingadreswozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocatieaanduidingadreswozobjectWithPatch() throws Exception {
        // Initialize the database
        locatieaanduidingadreswozobjectRepository.saveAndFlush(locatieaanduidingadreswozobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locatieaanduidingadreswozobject using partial update
        Locatieaanduidingadreswozobject partialUpdatedLocatieaanduidingadreswozobject = new Locatieaanduidingadreswozobject();
        partialUpdatedLocatieaanduidingadreswozobject.setId(locatieaanduidingadreswozobject.getId());

        partialUpdatedLocatieaanduidingadreswozobject.locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING);

        restLocatieaanduidingadreswozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocatieaanduidingadreswozobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocatieaanduidingadreswozobject))
            )
            .andExpect(status().isOk());

        // Validate the Locatieaanduidingadreswozobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocatieaanduidingadreswozobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLocatieaanduidingadreswozobject, locatieaanduidingadreswozobject),
            getPersistedLocatieaanduidingadreswozobject(locatieaanduidingadreswozobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateLocatieaanduidingadreswozobjectWithPatch() throws Exception {
        // Initialize the database
        locatieaanduidingadreswozobjectRepository.saveAndFlush(locatieaanduidingadreswozobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locatieaanduidingadreswozobject using partial update
        Locatieaanduidingadreswozobject partialUpdatedLocatieaanduidingadreswozobject = new Locatieaanduidingadreswozobject();
        partialUpdatedLocatieaanduidingadreswozobject.setId(locatieaanduidingadreswozobject.getId());

        partialUpdatedLocatieaanduidingadreswozobject.locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING);

        restLocatieaanduidingadreswozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocatieaanduidingadreswozobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocatieaanduidingadreswozobject))
            )
            .andExpect(status().isOk());

        // Validate the Locatieaanduidingadreswozobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocatieaanduidingadreswozobjectUpdatableFieldsEquals(
            partialUpdatedLocatieaanduidingadreswozobject,
            getPersistedLocatieaanduidingadreswozobject(partialUpdatedLocatieaanduidingadreswozobject)
        );
    }

    @Test
    @Transactional
    void patchNonExistingLocatieaanduidingadreswozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieaanduidingadreswozobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocatieaanduidingadreswozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locatieaanduidingadreswozobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locatieaanduidingadreswozobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatieaanduidingadreswozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocatieaanduidingadreswozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieaanduidingadreswozobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatieaanduidingadreswozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locatieaanduidingadreswozobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locatieaanduidingadreswozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocatieaanduidingadreswozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locatieaanduidingadreswozobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocatieaanduidingadreswozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locatieaanduidingadreswozobject))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locatieaanduidingadreswozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocatieaanduidingadreswozobject() throws Exception {
        // Initialize the database
        locatieaanduidingadreswozobjectRepository.saveAndFlush(locatieaanduidingadreswozobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the locatieaanduidingadreswozobject
        restLocatieaanduidingadreswozobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, locatieaanduidingadreswozobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return locatieaanduidingadreswozobjectRepository.count();
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

    protected Locatieaanduidingadreswozobject getPersistedLocatieaanduidingadreswozobject(
        Locatieaanduidingadreswozobject locatieaanduidingadreswozobject
    ) {
        return locatieaanduidingadreswozobjectRepository.findById(locatieaanduidingadreswozobject.getId()).orElseThrow();
    }

    protected void assertPersistedLocatieaanduidingadreswozobjectToMatchAllProperties(
        Locatieaanduidingadreswozobject expectedLocatieaanduidingadreswozobject
    ) {
        assertLocatieaanduidingadreswozobjectAllPropertiesEquals(
            expectedLocatieaanduidingadreswozobject,
            getPersistedLocatieaanduidingadreswozobject(expectedLocatieaanduidingadreswozobject)
        );
    }

    protected void assertPersistedLocatieaanduidingadreswozobjectToMatchUpdatableProperties(
        Locatieaanduidingadreswozobject expectedLocatieaanduidingadreswozobject
    ) {
        assertLocatieaanduidingadreswozobjectAllUpdatablePropertiesEquals(
            expectedLocatieaanduidingadreswozobject,
            getPersistedLocatieaanduidingadreswozobject(expectedLocatieaanduidingadreswozobject)
        );
    }
}
