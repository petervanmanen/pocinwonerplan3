package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.InonderzoekAsserts.*;
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
import nl.ritense.demo.domain.Inonderzoek;
import nl.ritense.demo.repository.InonderzoekRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InonderzoekResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InonderzoekResourceIT {

    private static final String DEFAULT_AANDUIDINGGEGEVENSINONDERZOEK = "AAAAAAAAAA";
    private static final String UPDATED_AANDUIDINGGEGEVENSINONDERZOEK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inonderzoeks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InonderzoekRepository inonderzoekRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInonderzoekMockMvc;

    private Inonderzoek inonderzoek;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inonderzoek createEntity(EntityManager em) {
        Inonderzoek inonderzoek = new Inonderzoek().aanduidinggegevensinonderzoek(DEFAULT_AANDUIDINGGEGEVENSINONDERZOEK);
        return inonderzoek;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inonderzoek createUpdatedEntity(EntityManager em) {
        Inonderzoek inonderzoek = new Inonderzoek().aanduidinggegevensinonderzoek(UPDATED_AANDUIDINGGEGEVENSINONDERZOEK);
        return inonderzoek;
    }

    @BeforeEach
    public void initTest() {
        inonderzoek = createEntity(em);
    }

    @Test
    @Transactional
    void createInonderzoek() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Inonderzoek
        var returnedInonderzoek = om.readValue(
            restInonderzoekMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inonderzoek)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Inonderzoek.class
        );

        // Validate the Inonderzoek in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInonderzoekUpdatableFieldsEquals(returnedInonderzoek, getPersistedInonderzoek(returnedInonderzoek));
    }

    @Test
    @Transactional
    void createInonderzoekWithExistingId() throws Exception {
        // Create the Inonderzoek with an existing ID
        inonderzoek.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInonderzoekMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inonderzoek)))
            .andExpect(status().isBadRequest());

        // Validate the Inonderzoek in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInonderzoeks() throws Exception {
        // Initialize the database
        inonderzoekRepository.saveAndFlush(inonderzoek);

        // Get all the inonderzoekList
        restInonderzoekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inonderzoek.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanduidinggegevensinonderzoek").value(hasItem(DEFAULT_AANDUIDINGGEGEVENSINONDERZOEK)));
    }

    @Test
    @Transactional
    void getInonderzoek() throws Exception {
        // Initialize the database
        inonderzoekRepository.saveAndFlush(inonderzoek);

        // Get the inonderzoek
        restInonderzoekMockMvc
            .perform(get(ENTITY_API_URL_ID, inonderzoek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inonderzoek.getId().intValue()))
            .andExpect(jsonPath("$.aanduidinggegevensinonderzoek").value(DEFAULT_AANDUIDINGGEGEVENSINONDERZOEK));
    }

    @Test
    @Transactional
    void getNonExistingInonderzoek() throws Exception {
        // Get the inonderzoek
        restInonderzoekMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInonderzoek() throws Exception {
        // Initialize the database
        inonderzoekRepository.saveAndFlush(inonderzoek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inonderzoek
        Inonderzoek updatedInonderzoek = inonderzoekRepository.findById(inonderzoek.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInonderzoek are not directly saved in db
        em.detach(updatedInonderzoek);
        updatedInonderzoek.aanduidinggegevensinonderzoek(UPDATED_AANDUIDINGGEGEVENSINONDERZOEK);

        restInonderzoekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInonderzoek.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInonderzoek))
            )
            .andExpect(status().isOk());

        // Validate the Inonderzoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInonderzoekToMatchAllProperties(updatedInonderzoek);
    }

    @Test
    @Transactional
    void putNonExistingInonderzoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inonderzoek.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInonderzoekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inonderzoek.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inonderzoek))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inonderzoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInonderzoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inonderzoek.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInonderzoekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inonderzoek))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inonderzoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInonderzoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inonderzoek.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInonderzoekMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inonderzoek)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inonderzoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInonderzoekWithPatch() throws Exception {
        // Initialize the database
        inonderzoekRepository.saveAndFlush(inonderzoek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inonderzoek using partial update
        Inonderzoek partialUpdatedInonderzoek = new Inonderzoek();
        partialUpdatedInonderzoek.setId(inonderzoek.getId());

        restInonderzoekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInonderzoek.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInonderzoek))
            )
            .andExpect(status().isOk());

        // Validate the Inonderzoek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInonderzoekUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInonderzoek, inonderzoek),
            getPersistedInonderzoek(inonderzoek)
        );
    }

    @Test
    @Transactional
    void fullUpdateInonderzoekWithPatch() throws Exception {
        // Initialize the database
        inonderzoekRepository.saveAndFlush(inonderzoek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inonderzoek using partial update
        Inonderzoek partialUpdatedInonderzoek = new Inonderzoek();
        partialUpdatedInonderzoek.setId(inonderzoek.getId());

        partialUpdatedInonderzoek.aanduidinggegevensinonderzoek(UPDATED_AANDUIDINGGEGEVENSINONDERZOEK);

        restInonderzoekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInonderzoek.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInonderzoek))
            )
            .andExpect(status().isOk());

        // Validate the Inonderzoek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInonderzoekUpdatableFieldsEquals(partialUpdatedInonderzoek, getPersistedInonderzoek(partialUpdatedInonderzoek));
    }

    @Test
    @Transactional
    void patchNonExistingInonderzoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inonderzoek.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInonderzoekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inonderzoek.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inonderzoek))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inonderzoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInonderzoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inonderzoek.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInonderzoekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inonderzoek))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inonderzoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInonderzoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inonderzoek.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInonderzoekMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inonderzoek)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inonderzoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInonderzoek() throws Exception {
        // Initialize the database
        inonderzoekRepository.saveAndFlush(inonderzoek);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inonderzoek
        restInonderzoekMockMvc
            .perform(delete(ENTITY_API_URL_ID, inonderzoek.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inonderzoekRepository.count();
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

    protected Inonderzoek getPersistedInonderzoek(Inonderzoek inonderzoek) {
        return inonderzoekRepository.findById(inonderzoek.getId()).orElseThrow();
    }

    protected void assertPersistedInonderzoekToMatchAllProperties(Inonderzoek expectedInonderzoek) {
        assertInonderzoekAllPropertiesEquals(expectedInonderzoek, getPersistedInonderzoek(expectedInonderzoek));
    }

    protected void assertPersistedInonderzoekToMatchUpdatableProperties(Inonderzoek expectedInonderzoek) {
        assertInonderzoekAllUpdatablePropertiesEquals(expectedInonderzoek, getPersistedInonderzoek(expectedInonderzoek));
    }
}
