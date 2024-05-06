package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.InburgeraarAsserts.*;
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
import nl.ritense.demo.domain.Inburgeraar;
import nl.ritense.demo.repository.InburgeraarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InburgeraarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InburgeraarResourceIT {

    private static final String DEFAULT_DOELGROEP = "AAAAAAAAAA";
    private static final String UPDATED_DOELGROEP = "BBBBBBBBBB";

    private static final String DEFAULT_GEDETAILLEERDEDOELGROEP = "AAAAAAAAAA";
    private static final String UPDATED_GEDETAILLEERDEDOELGROEP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inburgeraars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InburgeraarRepository inburgeraarRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInburgeraarMockMvc;

    private Inburgeraar inburgeraar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inburgeraar createEntity(EntityManager em) {
        Inburgeraar inburgeraar = new Inburgeraar().doelgroep(DEFAULT_DOELGROEP).gedetailleerdedoelgroep(DEFAULT_GEDETAILLEERDEDOELGROEP);
        return inburgeraar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inburgeraar createUpdatedEntity(EntityManager em) {
        Inburgeraar inburgeraar = new Inburgeraar().doelgroep(UPDATED_DOELGROEP).gedetailleerdedoelgroep(UPDATED_GEDETAILLEERDEDOELGROEP);
        return inburgeraar;
    }

    @BeforeEach
    public void initTest() {
        inburgeraar = createEntity(em);
    }

    @Test
    @Transactional
    void createInburgeraar() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Inburgeraar
        var returnedInburgeraar = om.readValue(
            restInburgeraarMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inburgeraar)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Inburgeraar.class
        );

        // Validate the Inburgeraar in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInburgeraarUpdatableFieldsEquals(returnedInburgeraar, getPersistedInburgeraar(returnedInburgeraar));
    }

    @Test
    @Transactional
    void createInburgeraarWithExistingId() throws Exception {
        // Create the Inburgeraar with an existing ID
        inburgeraar.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInburgeraarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inburgeraar)))
            .andExpect(status().isBadRequest());

        // Validate the Inburgeraar in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInburgeraars() throws Exception {
        // Initialize the database
        inburgeraarRepository.saveAndFlush(inburgeraar);

        // Get all the inburgeraarList
        restInburgeraarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inburgeraar.getId().intValue())))
            .andExpect(jsonPath("$.[*].doelgroep").value(hasItem(DEFAULT_DOELGROEP)))
            .andExpect(jsonPath("$.[*].gedetailleerdedoelgroep").value(hasItem(DEFAULT_GEDETAILLEERDEDOELGROEP)));
    }

    @Test
    @Transactional
    void getInburgeraar() throws Exception {
        // Initialize the database
        inburgeraarRepository.saveAndFlush(inburgeraar);

        // Get the inburgeraar
        restInburgeraarMockMvc
            .perform(get(ENTITY_API_URL_ID, inburgeraar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inburgeraar.getId().intValue()))
            .andExpect(jsonPath("$.doelgroep").value(DEFAULT_DOELGROEP))
            .andExpect(jsonPath("$.gedetailleerdedoelgroep").value(DEFAULT_GEDETAILLEERDEDOELGROEP));
    }

    @Test
    @Transactional
    void getNonExistingInburgeraar() throws Exception {
        // Get the inburgeraar
        restInburgeraarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInburgeraar() throws Exception {
        // Initialize the database
        inburgeraarRepository.saveAndFlush(inburgeraar);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inburgeraar
        Inburgeraar updatedInburgeraar = inburgeraarRepository.findById(inburgeraar.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInburgeraar are not directly saved in db
        em.detach(updatedInburgeraar);
        updatedInburgeraar.doelgroep(UPDATED_DOELGROEP).gedetailleerdedoelgroep(UPDATED_GEDETAILLEERDEDOELGROEP);

        restInburgeraarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInburgeraar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInburgeraar))
            )
            .andExpect(status().isOk());

        // Validate the Inburgeraar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInburgeraarToMatchAllProperties(updatedInburgeraar);
    }

    @Test
    @Transactional
    void putNonExistingInburgeraar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inburgeraar.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInburgeraarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inburgeraar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inburgeraar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inburgeraar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInburgeraar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inburgeraar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInburgeraarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inburgeraar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inburgeraar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInburgeraar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inburgeraar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInburgeraarMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inburgeraar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inburgeraar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInburgeraarWithPatch() throws Exception {
        // Initialize the database
        inburgeraarRepository.saveAndFlush(inburgeraar);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inburgeraar using partial update
        Inburgeraar partialUpdatedInburgeraar = new Inburgeraar();
        partialUpdatedInburgeraar.setId(inburgeraar.getId());

        partialUpdatedInburgeraar.doelgroep(UPDATED_DOELGROEP).gedetailleerdedoelgroep(UPDATED_GEDETAILLEERDEDOELGROEP);

        restInburgeraarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInburgeraar.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInburgeraar))
            )
            .andExpect(status().isOk());

        // Validate the Inburgeraar in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInburgeraarUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInburgeraar, inburgeraar),
            getPersistedInburgeraar(inburgeraar)
        );
    }

    @Test
    @Transactional
    void fullUpdateInburgeraarWithPatch() throws Exception {
        // Initialize the database
        inburgeraarRepository.saveAndFlush(inburgeraar);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inburgeraar using partial update
        Inburgeraar partialUpdatedInburgeraar = new Inburgeraar();
        partialUpdatedInburgeraar.setId(inburgeraar.getId());

        partialUpdatedInburgeraar.doelgroep(UPDATED_DOELGROEP).gedetailleerdedoelgroep(UPDATED_GEDETAILLEERDEDOELGROEP);

        restInburgeraarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInburgeraar.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInburgeraar))
            )
            .andExpect(status().isOk());

        // Validate the Inburgeraar in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInburgeraarUpdatableFieldsEquals(partialUpdatedInburgeraar, getPersistedInburgeraar(partialUpdatedInburgeraar));
    }

    @Test
    @Transactional
    void patchNonExistingInburgeraar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inburgeraar.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInburgeraarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inburgeraar.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inburgeraar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inburgeraar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInburgeraar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inburgeraar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInburgeraarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inburgeraar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inburgeraar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInburgeraar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inburgeraar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInburgeraarMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inburgeraar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inburgeraar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInburgeraar() throws Exception {
        // Initialize the database
        inburgeraarRepository.saveAndFlush(inburgeraar);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inburgeraar
        restInburgeraarMockMvc
            .perform(delete(ENTITY_API_URL_ID, inburgeraar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inburgeraarRepository.count();
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

    protected Inburgeraar getPersistedInburgeraar(Inburgeraar inburgeraar) {
        return inburgeraarRepository.findById(inburgeraar.getId()).orElseThrow();
    }

    protected void assertPersistedInburgeraarToMatchAllProperties(Inburgeraar expectedInburgeraar) {
        assertInburgeraarAllPropertiesEquals(expectedInburgeraar, getPersistedInburgeraar(expectedInburgeraar));
    }

    protected void assertPersistedInburgeraarToMatchUpdatableProperties(Inburgeraar expectedInburgeraar) {
        assertInburgeraarAllUpdatablePropertiesEquals(expectedInburgeraar, getPersistedInburgeraar(expectedInburgeraar));
    }
}
