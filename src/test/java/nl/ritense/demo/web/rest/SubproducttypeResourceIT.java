package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SubproducttypeAsserts.*;
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
import nl.ritense.demo.domain.Subproducttype;
import nl.ritense.demo.repository.SubproducttypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SubproducttypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubproducttypeResourceIT {

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_PRIORITEIT = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITEIT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/subproducttypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SubproducttypeRepository subproducttypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubproducttypeMockMvc;

    private Subproducttype subproducttype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subproducttype createEntity(EntityManager em) {
        Subproducttype subproducttype = new Subproducttype().omschrijving(DEFAULT_OMSCHRIJVING).prioriteit(DEFAULT_PRIORITEIT);
        return subproducttype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subproducttype createUpdatedEntity(EntityManager em) {
        Subproducttype subproducttype = new Subproducttype().omschrijving(UPDATED_OMSCHRIJVING).prioriteit(UPDATED_PRIORITEIT);
        return subproducttype;
    }

    @BeforeEach
    public void initTest() {
        subproducttype = createEntity(em);
    }

    @Test
    @Transactional
    void createSubproducttype() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Subproducttype
        var returnedSubproducttype = om.readValue(
            restSubproducttypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subproducttype)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Subproducttype.class
        );

        // Validate the Subproducttype in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSubproducttypeUpdatableFieldsEquals(returnedSubproducttype, getPersistedSubproducttype(returnedSubproducttype));
    }

    @Test
    @Transactional
    void createSubproducttypeWithExistingId() throws Exception {
        // Create the Subproducttype with an existing ID
        subproducttype.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubproducttypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subproducttype)))
            .andExpect(status().isBadRequest());

        // Validate the Subproducttype in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubproducttypes() throws Exception {
        // Initialize the database
        subproducttypeRepository.saveAndFlush(subproducttype);

        // Get all the subproducttypeList
        restSubproducttypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subproducttype.getId().intValue())))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].prioriteit").value(hasItem(DEFAULT_PRIORITEIT)));
    }

    @Test
    @Transactional
    void getSubproducttype() throws Exception {
        // Initialize the database
        subproducttypeRepository.saveAndFlush(subproducttype);

        // Get the subproducttype
        restSubproducttypeMockMvc
            .perform(get(ENTITY_API_URL_ID, subproducttype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subproducttype.getId().intValue()))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.prioriteit").value(DEFAULT_PRIORITEIT));
    }

    @Test
    @Transactional
    void getNonExistingSubproducttype() throws Exception {
        // Get the subproducttype
        restSubproducttypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubproducttype() throws Exception {
        // Initialize the database
        subproducttypeRepository.saveAndFlush(subproducttype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subproducttype
        Subproducttype updatedSubproducttype = subproducttypeRepository.findById(subproducttype.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSubproducttype are not directly saved in db
        em.detach(updatedSubproducttype);
        updatedSubproducttype.omschrijving(UPDATED_OMSCHRIJVING).prioriteit(UPDATED_PRIORITEIT);

        restSubproducttypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubproducttype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSubproducttype))
            )
            .andExpect(status().isOk());

        // Validate the Subproducttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSubproducttypeToMatchAllProperties(updatedSubproducttype);
    }

    @Test
    @Transactional
    void putNonExistingSubproducttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subproducttype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubproducttypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subproducttype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subproducttype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subproducttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubproducttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subproducttype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubproducttypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subproducttype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subproducttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubproducttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subproducttype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubproducttypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subproducttype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subproducttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubproducttypeWithPatch() throws Exception {
        // Initialize the database
        subproducttypeRepository.saveAndFlush(subproducttype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subproducttype using partial update
        Subproducttype partialUpdatedSubproducttype = new Subproducttype();
        partialUpdatedSubproducttype.setId(subproducttype.getId());

        partialUpdatedSubproducttype.omschrijving(UPDATED_OMSCHRIJVING);

        restSubproducttypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubproducttype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubproducttype))
            )
            .andExpect(status().isOk());

        // Validate the Subproducttype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubproducttypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSubproducttype, subproducttype),
            getPersistedSubproducttype(subproducttype)
        );
    }

    @Test
    @Transactional
    void fullUpdateSubproducttypeWithPatch() throws Exception {
        // Initialize the database
        subproducttypeRepository.saveAndFlush(subproducttype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subproducttype using partial update
        Subproducttype partialUpdatedSubproducttype = new Subproducttype();
        partialUpdatedSubproducttype.setId(subproducttype.getId());

        partialUpdatedSubproducttype.omschrijving(UPDATED_OMSCHRIJVING).prioriteit(UPDATED_PRIORITEIT);

        restSubproducttypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubproducttype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubproducttype))
            )
            .andExpect(status().isOk());

        // Validate the Subproducttype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubproducttypeUpdatableFieldsEquals(partialUpdatedSubproducttype, getPersistedSubproducttype(partialUpdatedSubproducttype));
    }

    @Test
    @Transactional
    void patchNonExistingSubproducttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subproducttype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubproducttypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subproducttype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subproducttype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subproducttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubproducttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subproducttype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubproducttypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subproducttype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subproducttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubproducttype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subproducttype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubproducttypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(subproducttype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subproducttype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubproducttype() throws Exception {
        // Initialize the database
        subproducttypeRepository.saveAndFlush(subproducttype);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the subproducttype
        restSubproducttypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, subproducttype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return subproducttypeRepository.count();
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

    protected Subproducttype getPersistedSubproducttype(Subproducttype subproducttype) {
        return subproducttypeRepository.findById(subproducttype.getId()).orElseThrow();
    }

    protected void assertPersistedSubproducttypeToMatchAllProperties(Subproducttype expectedSubproducttype) {
        assertSubproducttypeAllPropertiesEquals(expectedSubproducttype, getPersistedSubproducttype(expectedSubproducttype));
    }

    protected void assertPersistedSubproducttypeToMatchUpdatableProperties(Subproducttype expectedSubproducttype) {
        assertSubproducttypeAllUpdatablePropertiesEquals(expectedSubproducttype, getPersistedSubproducttype(expectedSubproducttype));
    }
}
