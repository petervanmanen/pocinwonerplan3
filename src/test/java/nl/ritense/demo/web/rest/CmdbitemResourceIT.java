package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.CmdbitemAsserts.*;
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
import nl.ritense.demo.domain.Cmdbitem;
import nl.ritense.demo.repository.CmdbitemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CmdbitemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CmdbitemResourceIT {

    private static final String DEFAULT_BESCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_BESCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cmdbitems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CmdbitemRepository cmdbitemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCmdbitemMockMvc;

    private Cmdbitem cmdbitem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cmdbitem createEntity(EntityManager em) {
        Cmdbitem cmdbitem = new Cmdbitem().beschrijving(DEFAULT_BESCHRIJVING).naam(DEFAULT_NAAM);
        return cmdbitem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cmdbitem createUpdatedEntity(EntityManager em) {
        Cmdbitem cmdbitem = new Cmdbitem().beschrijving(UPDATED_BESCHRIJVING).naam(UPDATED_NAAM);
        return cmdbitem;
    }

    @BeforeEach
    public void initTest() {
        cmdbitem = createEntity(em);
    }

    @Test
    @Transactional
    void createCmdbitem() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Cmdbitem
        var returnedCmdbitem = om.readValue(
            restCmdbitemMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cmdbitem)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Cmdbitem.class
        );

        // Validate the Cmdbitem in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCmdbitemUpdatableFieldsEquals(returnedCmdbitem, getPersistedCmdbitem(returnedCmdbitem));
    }

    @Test
    @Transactional
    void createCmdbitemWithExistingId() throws Exception {
        // Create the Cmdbitem with an existing ID
        cmdbitem.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCmdbitemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cmdbitem)))
            .andExpect(status().isBadRequest());

        // Validate the Cmdbitem in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCmdbitems() throws Exception {
        // Initialize the database
        cmdbitemRepository.saveAndFlush(cmdbitem);

        // Get all the cmdbitemList
        restCmdbitemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cmdbitem.getId().intValue())))
            .andExpect(jsonPath("$.[*].beschrijving").value(hasItem(DEFAULT_BESCHRIJVING)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getCmdbitem() throws Exception {
        // Initialize the database
        cmdbitemRepository.saveAndFlush(cmdbitem);

        // Get the cmdbitem
        restCmdbitemMockMvc
            .perform(get(ENTITY_API_URL_ID, cmdbitem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cmdbitem.getId().intValue()))
            .andExpect(jsonPath("$.beschrijving").value(DEFAULT_BESCHRIJVING))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingCmdbitem() throws Exception {
        // Get the cmdbitem
        restCmdbitemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCmdbitem() throws Exception {
        // Initialize the database
        cmdbitemRepository.saveAndFlush(cmdbitem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cmdbitem
        Cmdbitem updatedCmdbitem = cmdbitemRepository.findById(cmdbitem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCmdbitem are not directly saved in db
        em.detach(updatedCmdbitem);
        updatedCmdbitem.beschrijving(UPDATED_BESCHRIJVING).naam(UPDATED_NAAM);

        restCmdbitemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCmdbitem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCmdbitem))
            )
            .andExpect(status().isOk());

        // Validate the Cmdbitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCmdbitemToMatchAllProperties(updatedCmdbitem);
    }

    @Test
    @Transactional
    void putNonExistingCmdbitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cmdbitem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCmdbitemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cmdbitem.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cmdbitem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cmdbitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCmdbitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cmdbitem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCmdbitemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cmdbitem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cmdbitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCmdbitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cmdbitem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCmdbitemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cmdbitem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cmdbitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCmdbitemWithPatch() throws Exception {
        // Initialize the database
        cmdbitemRepository.saveAndFlush(cmdbitem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cmdbitem using partial update
        Cmdbitem partialUpdatedCmdbitem = new Cmdbitem();
        partialUpdatedCmdbitem.setId(cmdbitem.getId());

        partialUpdatedCmdbitem.beschrijving(UPDATED_BESCHRIJVING).naam(UPDATED_NAAM);

        restCmdbitemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCmdbitem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCmdbitem))
            )
            .andExpect(status().isOk());

        // Validate the Cmdbitem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCmdbitemUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCmdbitem, cmdbitem), getPersistedCmdbitem(cmdbitem));
    }

    @Test
    @Transactional
    void fullUpdateCmdbitemWithPatch() throws Exception {
        // Initialize the database
        cmdbitemRepository.saveAndFlush(cmdbitem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cmdbitem using partial update
        Cmdbitem partialUpdatedCmdbitem = new Cmdbitem();
        partialUpdatedCmdbitem.setId(cmdbitem.getId());

        partialUpdatedCmdbitem.beschrijving(UPDATED_BESCHRIJVING).naam(UPDATED_NAAM);

        restCmdbitemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCmdbitem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCmdbitem))
            )
            .andExpect(status().isOk());

        // Validate the Cmdbitem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCmdbitemUpdatableFieldsEquals(partialUpdatedCmdbitem, getPersistedCmdbitem(partialUpdatedCmdbitem));
    }

    @Test
    @Transactional
    void patchNonExistingCmdbitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cmdbitem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCmdbitemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cmdbitem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cmdbitem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cmdbitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCmdbitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cmdbitem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCmdbitemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cmdbitem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cmdbitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCmdbitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cmdbitem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCmdbitemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cmdbitem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cmdbitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCmdbitem() throws Exception {
        // Initialize the database
        cmdbitemRepository.saveAndFlush(cmdbitem);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cmdbitem
        restCmdbitemMockMvc
            .perform(delete(ENTITY_API_URL_ID, cmdbitem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cmdbitemRepository.count();
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

    protected Cmdbitem getPersistedCmdbitem(Cmdbitem cmdbitem) {
        return cmdbitemRepository.findById(cmdbitem.getId()).orElseThrow();
    }

    protected void assertPersistedCmdbitemToMatchAllProperties(Cmdbitem expectedCmdbitem) {
        assertCmdbitemAllPropertiesEquals(expectedCmdbitem, getPersistedCmdbitem(expectedCmdbitem));
    }

    protected void assertPersistedCmdbitemToMatchUpdatableProperties(Cmdbitem expectedCmdbitem) {
        assertCmdbitemAllUpdatablePropertiesEquals(expectedCmdbitem, getPersistedCmdbitem(expectedCmdbitem));
    }
}
