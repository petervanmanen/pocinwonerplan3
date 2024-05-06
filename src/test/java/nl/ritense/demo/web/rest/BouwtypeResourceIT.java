package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BouwtypeAsserts.*;
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
import nl.ritense.demo.domain.Bouwtype;
import nl.ritense.demo.repository.BouwtypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BouwtypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BouwtypeResourceIT {

    private static final String DEFAULT_HOOFDCATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_HOOFDCATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_SUBCATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_SUBCATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bouwtypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BouwtypeRepository bouwtypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBouwtypeMockMvc;

    private Bouwtype bouwtype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bouwtype createEntity(EntityManager em) {
        Bouwtype bouwtype = new Bouwtype()
            .hoofdcategorie(DEFAULT_HOOFDCATEGORIE)
            .subcategorie(DEFAULT_SUBCATEGORIE)
            .toelichting(DEFAULT_TOELICHTING);
        return bouwtype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bouwtype createUpdatedEntity(EntityManager em) {
        Bouwtype bouwtype = new Bouwtype()
            .hoofdcategorie(UPDATED_HOOFDCATEGORIE)
            .subcategorie(UPDATED_SUBCATEGORIE)
            .toelichting(UPDATED_TOELICHTING);
        return bouwtype;
    }

    @BeforeEach
    public void initTest() {
        bouwtype = createEntity(em);
    }

    @Test
    @Transactional
    void createBouwtype() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bouwtype
        var returnedBouwtype = om.readValue(
            restBouwtypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwtype)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bouwtype.class
        );

        // Validate the Bouwtype in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBouwtypeUpdatableFieldsEquals(returnedBouwtype, getPersistedBouwtype(returnedBouwtype));
    }

    @Test
    @Transactional
    void createBouwtypeWithExistingId() throws Exception {
        // Create the Bouwtype with an existing ID
        bouwtype.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBouwtypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwtype)))
            .andExpect(status().isBadRequest());

        // Validate the Bouwtype in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBouwtypes() throws Exception {
        // Initialize the database
        bouwtypeRepository.saveAndFlush(bouwtype);

        // Get all the bouwtypeList
        restBouwtypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bouwtype.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoofdcategorie").value(hasItem(DEFAULT_HOOFDCATEGORIE)))
            .andExpect(jsonPath("$.[*].subcategorie").value(hasItem(DEFAULT_SUBCATEGORIE)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)));
    }

    @Test
    @Transactional
    void getBouwtype() throws Exception {
        // Initialize the database
        bouwtypeRepository.saveAndFlush(bouwtype);

        // Get the bouwtype
        restBouwtypeMockMvc
            .perform(get(ENTITY_API_URL_ID, bouwtype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bouwtype.getId().intValue()))
            .andExpect(jsonPath("$.hoofdcategorie").value(DEFAULT_HOOFDCATEGORIE))
            .andExpect(jsonPath("$.subcategorie").value(DEFAULT_SUBCATEGORIE))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING));
    }

    @Test
    @Transactional
    void getNonExistingBouwtype() throws Exception {
        // Get the bouwtype
        restBouwtypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBouwtype() throws Exception {
        // Initialize the database
        bouwtypeRepository.saveAndFlush(bouwtype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwtype
        Bouwtype updatedBouwtype = bouwtypeRepository.findById(bouwtype.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBouwtype are not directly saved in db
        em.detach(updatedBouwtype);
        updatedBouwtype.hoofdcategorie(UPDATED_HOOFDCATEGORIE).subcategorie(UPDATED_SUBCATEGORIE).toelichting(UPDATED_TOELICHTING);

        restBouwtypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBouwtype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBouwtype))
            )
            .andExpect(status().isOk());

        // Validate the Bouwtype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBouwtypeToMatchAllProperties(updatedBouwtype);
    }

    @Test
    @Transactional
    void putNonExistingBouwtype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwtype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBouwtypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bouwtype.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwtype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwtype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBouwtype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwtype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwtypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bouwtype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwtype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBouwtype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwtype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwtypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwtype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bouwtype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBouwtypeWithPatch() throws Exception {
        // Initialize the database
        bouwtypeRepository.saveAndFlush(bouwtype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwtype using partial update
        Bouwtype partialUpdatedBouwtype = new Bouwtype();
        partialUpdatedBouwtype.setId(bouwtype.getId());

        partialUpdatedBouwtype.toelichting(UPDATED_TOELICHTING);

        restBouwtypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBouwtype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBouwtype))
            )
            .andExpect(status().isOk());

        // Validate the Bouwtype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBouwtypeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBouwtype, bouwtype), getPersistedBouwtype(bouwtype));
    }

    @Test
    @Transactional
    void fullUpdateBouwtypeWithPatch() throws Exception {
        // Initialize the database
        bouwtypeRepository.saveAndFlush(bouwtype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwtype using partial update
        Bouwtype partialUpdatedBouwtype = new Bouwtype();
        partialUpdatedBouwtype.setId(bouwtype.getId());

        partialUpdatedBouwtype.hoofdcategorie(UPDATED_HOOFDCATEGORIE).subcategorie(UPDATED_SUBCATEGORIE).toelichting(UPDATED_TOELICHTING);

        restBouwtypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBouwtype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBouwtype))
            )
            .andExpect(status().isOk());

        // Validate the Bouwtype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBouwtypeUpdatableFieldsEquals(partialUpdatedBouwtype, getPersistedBouwtype(partialUpdatedBouwtype));
    }

    @Test
    @Transactional
    void patchNonExistingBouwtype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwtype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBouwtypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bouwtype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bouwtype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwtype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBouwtype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwtype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwtypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bouwtype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwtype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBouwtype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwtype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwtypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bouwtype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bouwtype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBouwtype() throws Exception {
        // Initialize the database
        bouwtypeRepository.saveAndFlush(bouwtype);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bouwtype
        restBouwtypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, bouwtype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bouwtypeRepository.count();
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

    protected Bouwtype getPersistedBouwtype(Bouwtype bouwtype) {
        return bouwtypeRepository.findById(bouwtype.getId()).orElseThrow();
    }

    protected void assertPersistedBouwtypeToMatchAllProperties(Bouwtype expectedBouwtype) {
        assertBouwtypeAllPropertiesEquals(expectedBouwtype, getPersistedBouwtype(expectedBouwtype));
    }

    protected void assertPersistedBouwtypeToMatchUpdatableProperties(Bouwtype expectedBouwtype) {
        assertBouwtypeAllUpdatablePropertiesEquals(expectedBouwtype, getPersistedBouwtype(expectedBouwtype));
    }
}
