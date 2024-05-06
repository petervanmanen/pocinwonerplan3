package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BouwdeelelementAsserts.*;
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
import nl.ritense.demo.domain.Bouwdeelelement;
import nl.ritense.demo.repository.BouwdeelelementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BouwdeelelementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BouwdeelelementResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bouwdeelelements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BouwdeelelementRepository bouwdeelelementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBouwdeelelementMockMvc;

    private Bouwdeelelement bouwdeelelement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bouwdeelelement createEntity(EntityManager em) {
        Bouwdeelelement bouwdeelelement = new Bouwdeelelement().code(DEFAULT_CODE).omschrijving(DEFAULT_OMSCHRIJVING);
        return bouwdeelelement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bouwdeelelement createUpdatedEntity(EntityManager em) {
        Bouwdeelelement bouwdeelelement = new Bouwdeelelement().code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING);
        return bouwdeelelement;
    }

    @BeforeEach
    public void initTest() {
        bouwdeelelement = createEntity(em);
    }

    @Test
    @Transactional
    void createBouwdeelelement() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bouwdeelelement
        var returnedBouwdeelelement = om.readValue(
            restBouwdeelelementMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwdeelelement)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bouwdeelelement.class
        );

        // Validate the Bouwdeelelement in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBouwdeelelementUpdatableFieldsEquals(returnedBouwdeelelement, getPersistedBouwdeelelement(returnedBouwdeelelement));
    }

    @Test
    @Transactional
    void createBouwdeelelementWithExistingId() throws Exception {
        // Create the Bouwdeelelement with an existing ID
        bouwdeelelement.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBouwdeelelementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwdeelelement)))
            .andExpect(status().isBadRequest());

        // Validate the Bouwdeelelement in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBouwdeelelements() throws Exception {
        // Initialize the database
        bouwdeelelementRepository.saveAndFlush(bouwdeelelement);

        // Get all the bouwdeelelementList
        restBouwdeelelementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bouwdeelelement.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getBouwdeelelement() throws Exception {
        // Initialize the database
        bouwdeelelementRepository.saveAndFlush(bouwdeelelement);

        // Get the bouwdeelelement
        restBouwdeelelementMockMvc
            .perform(get(ENTITY_API_URL_ID, bouwdeelelement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bouwdeelelement.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingBouwdeelelement() throws Exception {
        // Get the bouwdeelelement
        restBouwdeelelementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBouwdeelelement() throws Exception {
        // Initialize the database
        bouwdeelelementRepository.saveAndFlush(bouwdeelelement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwdeelelement
        Bouwdeelelement updatedBouwdeelelement = bouwdeelelementRepository.findById(bouwdeelelement.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBouwdeelelement are not directly saved in db
        em.detach(updatedBouwdeelelement);
        updatedBouwdeelelement.code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING);

        restBouwdeelelementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBouwdeelelement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBouwdeelelement))
            )
            .andExpect(status().isOk());

        // Validate the Bouwdeelelement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBouwdeelelementToMatchAllProperties(updatedBouwdeelelement);
    }

    @Test
    @Transactional
    void putNonExistingBouwdeelelement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwdeelelement.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBouwdeelelementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bouwdeelelement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bouwdeelelement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwdeelelement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBouwdeelelement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwdeelelement.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwdeelelementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bouwdeelelement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwdeelelement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBouwdeelelement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwdeelelement.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwdeelelementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwdeelelement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bouwdeelelement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBouwdeelelementWithPatch() throws Exception {
        // Initialize the database
        bouwdeelelementRepository.saveAndFlush(bouwdeelelement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwdeelelement using partial update
        Bouwdeelelement partialUpdatedBouwdeelelement = new Bouwdeelelement();
        partialUpdatedBouwdeelelement.setId(bouwdeelelement.getId());

        restBouwdeelelementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBouwdeelelement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBouwdeelelement))
            )
            .andExpect(status().isOk());

        // Validate the Bouwdeelelement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBouwdeelelementUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBouwdeelelement, bouwdeelelement),
            getPersistedBouwdeelelement(bouwdeelelement)
        );
    }

    @Test
    @Transactional
    void fullUpdateBouwdeelelementWithPatch() throws Exception {
        // Initialize the database
        bouwdeelelementRepository.saveAndFlush(bouwdeelelement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwdeelelement using partial update
        Bouwdeelelement partialUpdatedBouwdeelelement = new Bouwdeelelement();
        partialUpdatedBouwdeelelement.setId(bouwdeelelement.getId());

        partialUpdatedBouwdeelelement.code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING);

        restBouwdeelelementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBouwdeelelement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBouwdeelelement))
            )
            .andExpect(status().isOk());

        // Validate the Bouwdeelelement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBouwdeelelementUpdatableFieldsEquals(
            partialUpdatedBouwdeelelement,
            getPersistedBouwdeelelement(partialUpdatedBouwdeelelement)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBouwdeelelement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwdeelelement.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBouwdeelelementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bouwdeelelement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bouwdeelelement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwdeelelement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBouwdeelelement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwdeelelement.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwdeelelementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bouwdeelelement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwdeelelement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBouwdeelelement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwdeelelement.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwdeelelementMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bouwdeelelement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bouwdeelelement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBouwdeelelement() throws Exception {
        // Initialize the database
        bouwdeelelementRepository.saveAndFlush(bouwdeelelement);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bouwdeelelement
        restBouwdeelelementMockMvc
            .perform(delete(ENTITY_API_URL_ID, bouwdeelelement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bouwdeelelementRepository.count();
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

    protected Bouwdeelelement getPersistedBouwdeelelement(Bouwdeelelement bouwdeelelement) {
        return bouwdeelelementRepository.findById(bouwdeelelement.getId()).orElseThrow();
    }

    protected void assertPersistedBouwdeelelementToMatchAllProperties(Bouwdeelelement expectedBouwdeelelement) {
        assertBouwdeelelementAllPropertiesEquals(expectedBouwdeelelement, getPersistedBouwdeelelement(expectedBouwdeelelement));
    }

    protected void assertPersistedBouwdeelelementToMatchUpdatableProperties(Bouwdeelelement expectedBouwdeelelement) {
        assertBouwdeelelementAllUpdatablePropertiesEquals(expectedBouwdeelelement, getPersistedBouwdeelelement(expectedBouwdeelelement));
    }
}
