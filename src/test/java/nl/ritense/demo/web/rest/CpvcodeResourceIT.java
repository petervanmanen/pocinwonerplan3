package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.CpvcodeAsserts.*;
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
import nl.ritense.demo.domain.Cpvcode;
import nl.ritense.demo.repository.CpvcodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CpvcodeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CpvcodeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cpvcodes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CpvcodeRepository cpvcodeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCpvcodeMockMvc;

    private Cpvcode cpvcode;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cpvcode createEntity(EntityManager em) {
        Cpvcode cpvcode = new Cpvcode().code(DEFAULT_CODE).omschrijving(DEFAULT_OMSCHRIJVING);
        return cpvcode;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cpvcode createUpdatedEntity(EntityManager em) {
        Cpvcode cpvcode = new Cpvcode().code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING);
        return cpvcode;
    }

    @BeforeEach
    public void initTest() {
        cpvcode = createEntity(em);
    }

    @Test
    @Transactional
    void createCpvcode() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Cpvcode
        var returnedCpvcode = om.readValue(
            restCpvcodeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cpvcode)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Cpvcode.class
        );

        // Validate the Cpvcode in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCpvcodeUpdatableFieldsEquals(returnedCpvcode, getPersistedCpvcode(returnedCpvcode));
    }

    @Test
    @Transactional
    void createCpvcodeWithExistingId() throws Exception {
        // Create the Cpvcode with an existing ID
        cpvcode.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCpvcodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cpvcode)))
            .andExpect(status().isBadRequest());

        // Validate the Cpvcode in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCpvcodes() throws Exception {
        // Initialize the database
        cpvcodeRepository.saveAndFlush(cpvcode);

        // Get all the cpvcodeList
        restCpvcodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cpvcode.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getCpvcode() throws Exception {
        // Initialize the database
        cpvcodeRepository.saveAndFlush(cpvcode);

        // Get the cpvcode
        restCpvcodeMockMvc
            .perform(get(ENTITY_API_URL_ID, cpvcode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cpvcode.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingCpvcode() throws Exception {
        // Get the cpvcode
        restCpvcodeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCpvcode() throws Exception {
        // Initialize the database
        cpvcodeRepository.saveAndFlush(cpvcode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cpvcode
        Cpvcode updatedCpvcode = cpvcodeRepository.findById(cpvcode.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCpvcode are not directly saved in db
        em.detach(updatedCpvcode);
        updatedCpvcode.code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING);

        restCpvcodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCpvcode.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCpvcode))
            )
            .andExpect(status().isOk());

        // Validate the Cpvcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCpvcodeToMatchAllProperties(updatedCpvcode);
    }

    @Test
    @Transactional
    void putNonExistingCpvcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cpvcode.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCpvcodeMockMvc
            .perform(put(ENTITY_API_URL_ID, cpvcode.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cpvcode)))
            .andExpect(status().isBadRequest());

        // Validate the Cpvcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCpvcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cpvcode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCpvcodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cpvcode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cpvcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCpvcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cpvcode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCpvcodeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cpvcode)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cpvcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCpvcodeWithPatch() throws Exception {
        // Initialize the database
        cpvcodeRepository.saveAndFlush(cpvcode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cpvcode using partial update
        Cpvcode partialUpdatedCpvcode = new Cpvcode();
        partialUpdatedCpvcode.setId(cpvcode.getId());

        partialUpdatedCpvcode.omschrijving(UPDATED_OMSCHRIJVING);

        restCpvcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCpvcode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCpvcode))
            )
            .andExpect(status().isOk());

        // Validate the Cpvcode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCpvcodeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCpvcode, cpvcode), getPersistedCpvcode(cpvcode));
    }

    @Test
    @Transactional
    void fullUpdateCpvcodeWithPatch() throws Exception {
        // Initialize the database
        cpvcodeRepository.saveAndFlush(cpvcode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cpvcode using partial update
        Cpvcode partialUpdatedCpvcode = new Cpvcode();
        partialUpdatedCpvcode.setId(cpvcode.getId());

        partialUpdatedCpvcode.code(UPDATED_CODE).omschrijving(UPDATED_OMSCHRIJVING);

        restCpvcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCpvcode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCpvcode))
            )
            .andExpect(status().isOk());

        // Validate the Cpvcode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCpvcodeUpdatableFieldsEquals(partialUpdatedCpvcode, getPersistedCpvcode(partialUpdatedCpvcode));
    }

    @Test
    @Transactional
    void patchNonExistingCpvcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cpvcode.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCpvcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cpvcode.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cpvcode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cpvcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCpvcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cpvcode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCpvcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cpvcode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cpvcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCpvcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cpvcode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCpvcodeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cpvcode)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cpvcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCpvcode() throws Exception {
        // Initialize the database
        cpvcodeRepository.saveAndFlush(cpvcode);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cpvcode
        restCpvcodeMockMvc
            .perform(delete(ENTITY_API_URL_ID, cpvcode.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cpvcodeRepository.count();
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

    protected Cpvcode getPersistedCpvcode(Cpvcode cpvcode) {
        return cpvcodeRepository.findById(cpvcode.getId()).orElseThrow();
    }

    protected void assertPersistedCpvcodeToMatchAllProperties(Cpvcode expectedCpvcode) {
        assertCpvcodeAllPropertiesEquals(expectedCpvcode, getPersistedCpvcode(expectedCpvcode));
    }

    protected void assertPersistedCpvcodeToMatchUpdatableProperties(Cpvcode expectedCpvcode) {
        assertCpvcodeAllUpdatablePropertiesEquals(expectedCpvcode, getPersistedCpvcode(expectedCpvcode));
    }
}
