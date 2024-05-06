package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerblijfbuitenlandsubjectAsserts.*;
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
import nl.ritense.demo.domain.Verblijfbuitenlandsubject;
import nl.ritense.demo.repository.VerblijfbuitenlandsubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerblijfbuitenlandsubjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerblijfbuitenlandsubjectResourceIT {

    private static final String DEFAULT_ADRESBUITENLAND_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBUITENLAND_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESBUITENLAND_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBUITENLAND_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESBUITENLAND_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBUITENLAND_3 = "BBBBBBBBBB";

    private static final String DEFAULT_LANDVERBLIJFADRES = "AAAAAAAAAA";
    private static final String UPDATED_LANDVERBLIJFADRES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verblijfbuitenlandsubjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerblijfbuitenlandsubjectRepository verblijfbuitenlandsubjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerblijfbuitenlandsubjectMockMvc;

    private Verblijfbuitenlandsubject verblijfbuitenlandsubject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfbuitenlandsubject createEntity(EntityManager em) {
        Verblijfbuitenlandsubject verblijfbuitenlandsubject = new Verblijfbuitenlandsubject()
            .adresbuitenland1(DEFAULT_ADRESBUITENLAND_1)
            .adresbuitenland2(DEFAULT_ADRESBUITENLAND_2)
            .adresbuitenland3(DEFAULT_ADRESBUITENLAND_3)
            .landverblijfadres(DEFAULT_LANDVERBLIJFADRES);
        return verblijfbuitenlandsubject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfbuitenlandsubject createUpdatedEntity(EntityManager em) {
        Verblijfbuitenlandsubject verblijfbuitenlandsubject = new Verblijfbuitenlandsubject()
            .adresbuitenland1(UPDATED_ADRESBUITENLAND_1)
            .adresbuitenland2(UPDATED_ADRESBUITENLAND_2)
            .adresbuitenland3(UPDATED_ADRESBUITENLAND_3)
            .landverblijfadres(UPDATED_LANDVERBLIJFADRES);
        return verblijfbuitenlandsubject;
    }

    @BeforeEach
    public void initTest() {
        verblijfbuitenlandsubject = createEntity(em);
    }

    @Test
    @Transactional
    void createVerblijfbuitenlandsubject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verblijfbuitenlandsubject
        var returnedVerblijfbuitenlandsubject = om.readValue(
            restVerblijfbuitenlandsubjectMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfbuitenlandsubject))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verblijfbuitenlandsubject.class
        );

        // Validate the Verblijfbuitenlandsubject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerblijfbuitenlandsubjectUpdatableFieldsEquals(
            returnedVerblijfbuitenlandsubject,
            getPersistedVerblijfbuitenlandsubject(returnedVerblijfbuitenlandsubject)
        );
    }

    @Test
    @Transactional
    void createVerblijfbuitenlandsubjectWithExistingId() throws Exception {
        // Create the Verblijfbuitenlandsubject with an existing ID
        verblijfbuitenlandsubject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerblijfbuitenlandsubjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfbuitenlandsubject)))
            .andExpect(status().isBadRequest());

        // Validate the Verblijfbuitenlandsubject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerblijfbuitenlandsubjects() throws Exception {
        // Initialize the database
        verblijfbuitenlandsubjectRepository.saveAndFlush(verblijfbuitenlandsubject);

        // Get all the verblijfbuitenlandsubjectList
        restVerblijfbuitenlandsubjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verblijfbuitenlandsubject.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresbuitenland1").value(hasItem(DEFAULT_ADRESBUITENLAND_1)))
            .andExpect(jsonPath("$.[*].adresbuitenland2").value(hasItem(DEFAULT_ADRESBUITENLAND_2)))
            .andExpect(jsonPath("$.[*].adresbuitenland3").value(hasItem(DEFAULT_ADRESBUITENLAND_3)))
            .andExpect(jsonPath("$.[*].landverblijfadres").value(hasItem(DEFAULT_LANDVERBLIJFADRES)));
    }

    @Test
    @Transactional
    void getVerblijfbuitenlandsubject() throws Exception {
        // Initialize the database
        verblijfbuitenlandsubjectRepository.saveAndFlush(verblijfbuitenlandsubject);

        // Get the verblijfbuitenlandsubject
        restVerblijfbuitenlandsubjectMockMvc
            .perform(get(ENTITY_API_URL_ID, verblijfbuitenlandsubject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verblijfbuitenlandsubject.getId().intValue()))
            .andExpect(jsonPath("$.adresbuitenland1").value(DEFAULT_ADRESBUITENLAND_1))
            .andExpect(jsonPath("$.adresbuitenland2").value(DEFAULT_ADRESBUITENLAND_2))
            .andExpect(jsonPath("$.adresbuitenland3").value(DEFAULT_ADRESBUITENLAND_3))
            .andExpect(jsonPath("$.landverblijfadres").value(DEFAULT_LANDVERBLIJFADRES));
    }

    @Test
    @Transactional
    void getNonExistingVerblijfbuitenlandsubject() throws Exception {
        // Get the verblijfbuitenlandsubject
        restVerblijfbuitenlandsubjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerblijfbuitenlandsubject() throws Exception {
        // Initialize the database
        verblijfbuitenlandsubjectRepository.saveAndFlush(verblijfbuitenlandsubject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfbuitenlandsubject
        Verblijfbuitenlandsubject updatedVerblijfbuitenlandsubject = verblijfbuitenlandsubjectRepository
            .findById(verblijfbuitenlandsubject.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedVerblijfbuitenlandsubject are not directly saved in db
        em.detach(updatedVerblijfbuitenlandsubject);
        updatedVerblijfbuitenlandsubject
            .adresbuitenland1(UPDATED_ADRESBUITENLAND_1)
            .adresbuitenland2(UPDATED_ADRESBUITENLAND_2)
            .adresbuitenland3(UPDATED_ADRESBUITENLAND_3)
            .landverblijfadres(UPDATED_LANDVERBLIJFADRES);

        restVerblijfbuitenlandsubjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerblijfbuitenlandsubject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerblijfbuitenlandsubject))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfbuitenlandsubject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerblijfbuitenlandsubjectToMatchAllProperties(updatedVerblijfbuitenlandsubject);
    }

    @Test
    @Transactional
    void putNonExistingVerblijfbuitenlandsubject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfbuitenlandsubject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerblijfbuitenlandsubjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verblijfbuitenlandsubject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfbuitenlandsubject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfbuitenlandsubject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerblijfbuitenlandsubject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfbuitenlandsubject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfbuitenlandsubjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfbuitenlandsubject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfbuitenlandsubject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerblijfbuitenlandsubject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfbuitenlandsubject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfbuitenlandsubjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfbuitenlandsubject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verblijfbuitenlandsubject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerblijfbuitenlandsubjectWithPatch() throws Exception {
        // Initialize the database
        verblijfbuitenlandsubjectRepository.saveAndFlush(verblijfbuitenlandsubject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfbuitenlandsubject using partial update
        Verblijfbuitenlandsubject partialUpdatedVerblijfbuitenlandsubject = new Verblijfbuitenlandsubject();
        partialUpdatedVerblijfbuitenlandsubject.setId(verblijfbuitenlandsubject.getId());

        partialUpdatedVerblijfbuitenlandsubject.adresbuitenland3(UPDATED_ADRESBUITENLAND_3);

        restVerblijfbuitenlandsubjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerblijfbuitenlandsubject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerblijfbuitenlandsubject))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfbuitenlandsubject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerblijfbuitenlandsubjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerblijfbuitenlandsubject, verblijfbuitenlandsubject),
            getPersistedVerblijfbuitenlandsubject(verblijfbuitenlandsubject)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerblijfbuitenlandsubjectWithPatch() throws Exception {
        // Initialize the database
        verblijfbuitenlandsubjectRepository.saveAndFlush(verblijfbuitenlandsubject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfbuitenlandsubject using partial update
        Verblijfbuitenlandsubject partialUpdatedVerblijfbuitenlandsubject = new Verblijfbuitenlandsubject();
        partialUpdatedVerblijfbuitenlandsubject.setId(verblijfbuitenlandsubject.getId());

        partialUpdatedVerblijfbuitenlandsubject
            .adresbuitenland1(UPDATED_ADRESBUITENLAND_1)
            .adresbuitenland2(UPDATED_ADRESBUITENLAND_2)
            .adresbuitenland3(UPDATED_ADRESBUITENLAND_3)
            .landverblijfadres(UPDATED_LANDVERBLIJFADRES);

        restVerblijfbuitenlandsubjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerblijfbuitenlandsubject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerblijfbuitenlandsubject))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfbuitenlandsubject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerblijfbuitenlandsubjectUpdatableFieldsEquals(
            partialUpdatedVerblijfbuitenlandsubject,
            getPersistedVerblijfbuitenlandsubject(partialUpdatedVerblijfbuitenlandsubject)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVerblijfbuitenlandsubject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfbuitenlandsubject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerblijfbuitenlandsubjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verblijfbuitenlandsubject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verblijfbuitenlandsubject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfbuitenlandsubject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerblijfbuitenlandsubject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfbuitenlandsubject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfbuitenlandsubjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verblijfbuitenlandsubject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfbuitenlandsubject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerblijfbuitenlandsubject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfbuitenlandsubject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfbuitenlandsubjectMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verblijfbuitenlandsubject))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verblijfbuitenlandsubject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerblijfbuitenlandsubject() throws Exception {
        // Initialize the database
        verblijfbuitenlandsubjectRepository.saveAndFlush(verblijfbuitenlandsubject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verblijfbuitenlandsubject
        restVerblijfbuitenlandsubjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, verblijfbuitenlandsubject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verblijfbuitenlandsubjectRepository.count();
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

    protected Verblijfbuitenlandsubject getPersistedVerblijfbuitenlandsubject(Verblijfbuitenlandsubject verblijfbuitenlandsubject) {
        return verblijfbuitenlandsubjectRepository.findById(verblijfbuitenlandsubject.getId()).orElseThrow();
    }

    protected void assertPersistedVerblijfbuitenlandsubjectToMatchAllProperties(
        Verblijfbuitenlandsubject expectedVerblijfbuitenlandsubject
    ) {
        assertVerblijfbuitenlandsubjectAllPropertiesEquals(
            expectedVerblijfbuitenlandsubject,
            getPersistedVerblijfbuitenlandsubject(expectedVerblijfbuitenlandsubject)
        );
    }

    protected void assertPersistedVerblijfbuitenlandsubjectToMatchUpdatableProperties(
        Verblijfbuitenlandsubject expectedVerblijfbuitenlandsubject
    ) {
        assertVerblijfbuitenlandsubjectAllUpdatablePropertiesEquals(
            expectedVerblijfbuitenlandsubject,
            getPersistedVerblijfbuitenlandsubject(expectedVerblijfbuitenlandsubject)
        );
    }
}
