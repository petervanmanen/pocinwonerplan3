package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BouwstijlAsserts.*;
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
import nl.ritense.demo.domain.Bouwstijl;
import nl.ritense.demo.repository.BouwstijlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BouwstijlResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BouwstijlResourceIT {

    private static final String DEFAULT_HOOFDSTIJL = "AAAAAAAAAA";
    private static final String UPDATED_HOOFDSTIJL = "BBBBBBBBBB";

    private static final String DEFAULT_SUBSTIJL = "AAAAAAAAAA";
    private static final String UPDATED_SUBSTIJL = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String DEFAULT_ZUIVERHEID = "AAAAAAAAAA";
    private static final String UPDATED_ZUIVERHEID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bouwstijls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BouwstijlRepository bouwstijlRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBouwstijlMockMvc;

    private Bouwstijl bouwstijl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bouwstijl createEntity(EntityManager em) {
        Bouwstijl bouwstijl = new Bouwstijl()
            .hoofdstijl(DEFAULT_HOOFDSTIJL)
            .substijl(DEFAULT_SUBSTIJL)
            .toelichting(DEFAULT_TOELICHTING)
            .zuiverheid(DEFAULT_ZUIVERHEID);
        return bouwstijl;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bouwstijl createUpdatedEntity(EntityManager em) {
        Bouwstijl bouwstijl = new Bouwstijl()
            .hoofdstijl(UPDATED_HOOFDSTIJL)
            .substijl(UPDATED_SUBSTIJL)
            .toelichting(UPDATED_TOELICHTING)
            .zuiverheid(UPDATED_ZUIVERHEID);
        return bouwstijl;
    }

    @BeforeEach
    public void initTest() {
        bouwstijl = createEntity(em);
    }

    @Test
    @Transactional
    void createBouwstijl() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bouwstijl
        var returnedBouwstijl = om.readValue(
            restBouwstijlMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwstijl)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bouwstijl.class
        );

        // Validate the Bouwstijl in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBouwstijlUpdatableFieldsEquals(returnedBouwstijl, getPersistedBouwstijl(returnedBouwstijl));
    }

    @Test
    @Transactional
    void createBouwstijlWithExistingId() throws Exception {
        // Create the Bouwstijl with an existing ID
        bouwstijl.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBouwstijlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwstijl)))
            .andExpect(status().isBadRequest());

        // Validate the Bouwstijl in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBouwstijls() throws Exception {
        // Initialize the database
        bouwstijlRepository.saveAndFlush(bouwstijl);

        // Get all the bouwstijlList
        restBouwstijlMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bouwstijl.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoofdstijl").value(hasItem(DEFAULT_HOOFDSTIJL)))
            .andExpect(jsonPath("$.[*].substijl").value(hasItem(DEFAULT_SUBSTIJL)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)))
            .andExpect(jsonPath("$.[*].zuiverheid").value(hasItem(DEFAULT_ZUIVERHEID)));
    }

    @Test
    @Transactional
    void getBouwstijl() throws Exception {
        // Initialize the database
        bouwstijlRepository.saveAndFlush(bouwstijl);

        // Get the bouwstijl
        restBouwstijlMockMvc
            .perform(get(ENTITY_API_URL_ID, bouwstijl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bouwstijl.getId().intValue()))
            .andExpect(jsonPath("$.hoofdstijl").value(DEFAULT_HOOFDSTIJL))
            .andExpect(jsonPath("$.substijl").value(DEFAULT_SUBSTIJL))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING))
            .andExpect(jsonPath("$.zuiverheid").value(DEFAULT_ZUIVERHEID));
    }

    @Test
    @Transactional
    void getNonExistingBouwstijl() throws Exception {
        // Get the bouwstijl
        restBouwstijlMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBouwstijl() throws Exception {
        // Initialize the database
        bouwstijlRepository.saveAndFlush(bouwstijl);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwstijl
        Bouwstijl updatedBouwstijl = bouwstijlRepository.findById(bouwstijl.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBouwstijl are not directly saved in db
        em.detach(updatedBouwstijl);
        updatedBouwstijl
            .hoofdstijl(UPDATED_HOOFDSTIJL)
            .substijl(UPDATED_SUBSTIJL)
            .toelichting(UPDATED_TOELICHTING)
            .zuiverheid(UPDATED_ZUIVERHEID);

        restBouwstijlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBouwstijl.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBouwstijl))
            )
            .andExpect(status().isOk());

        // Validate the Bouwstijl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBouwstijlToMatchAllProperties(updatedBouwstijl);
    }

    @Test
    @Transactional
    void putNonExistingBouwstijl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwstijl.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBouwstijlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bouwstijl.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwstijl))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwstijl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBouwstijl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwstijl.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwstijlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bouwstijl))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwstijl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBouwstijl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwstijl.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwstijlMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwstijl)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bouwstijl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBouwstijlWithPatch() throws Exception {
        // Initialize the database
        bouwstijlRepository.saveAndFlush(bouwstijl);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwstijl using partial update
        Bouwstijl partialUpdatedBouwstijl = new Bouwstijl();
        partialUpdatedBouwstijl.setId(bouwstijl.getId());

        partialUpdatedBouwstijl.hoofdstijl(UPDATED_HOOFDSTIJL).substijl(UPDATED_SUBSTIJL);

        restBouwstijlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBouwstijl.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBouwstijl))
            )
            .andExpect(status().isOk());

        // Validate the Bouwstijl in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBouwstijlUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBouwstijl, bouwstijl),
            getPersistedBouwstijl(bouwstijl)
        );
    }

    @Test
    @Transactional
    void fullUpdateBouwstijlWithPatch() throws Exception {
        // Initialize the database
        bouwstijlRepository.saveAndFlush(bouwstijl);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwstijl using partial update
        Bouwstijl partialUpdatedBouwstijl = new Bouwstijl();
        partialUpdatedBouwstijl.setId(bouwstijl.getId());

        partialUpdatedBouwstijl
            .hoofdstijl(UPDATED_HOOFDSTIJL)
            .substijl(UPDATED_SUBSTIJL)
            .toelichting(UPDATED_TOELICHTING)
            .zuiverheid(UPDATED_ZUIVERHEID);

        restBouwstijlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBouwstijl.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBouwstijl))
            )
            .andExpect(status().isOk());

        // Validate the Bouwstijl in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBouwstijlUpdatableFieldsEquals(partialUpdatedBouwstijl, getPersistedBouwstijl(partialUpdatedBouwstijl));
    }

    @Test
    @Transactional
    void patchNonExistingBouwstijl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwstijl.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBouwstijlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bouwstijl.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bouwstijl))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwstijl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBouwstijl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwstijl.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwstijlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bouwstijl))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwstijl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBouwstijl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwstijl.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwstijlMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bouwstijl)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bouwstijl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBouwstijl() throws Exception {
        // Initialize the database
        bouwstijlRepository.saveAndFlush(bouwstijl);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bouwstijl
        restBouwstijlMockMvc
            .perform(delete(ENTITY_API_URL_ID, bouwstijl.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bouwstijlRepository.count();
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

    protected Bouwstijl getPersistedBouwstijl(Bouwstijl bouwstijl) {
        return bouwstijlRepository.findById(bouwstijl.getId()).orElseThrow();
    }

    protected void assertPersistedBouwstijlToMatchAllProperties(Bouwstijl expectedBouwstijl) {
        assertBouwstijlAllPropertiesEquals(expectedBouwstijl, getPersistedBouwstijl(expectedBouwstijl));
    }

    protected void assertPersistedBouwstijlToMatchUpdatableProperties(Bouwstijl expectedBouwstijl) {
        assertBouwstijlAllUpdatablePropertiesEquals(expectedBouwstijl, getPersistedBouwstijl(expectedBouwstijl));
    }
}
