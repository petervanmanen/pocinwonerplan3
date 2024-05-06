package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LeerjaarAsserts.*;
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
import nl.ritense.demo.domain.Leerjaar;
import nl.ritense.demo.repository.LeerjaarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LeerjaarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeerjaarResourceIT {

    private static final String DEFAULT_JAAREINDE = "AAAAAAAAAA";
    private static final String UPDATED_JAAREINDE = "BBBBBBBBBB";

    private static final String DEFAULT_JAARSTART = "AAAAAAAAAA";
    private static final String UPDATED_JAARSTART = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/leerjaars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LeerjaarRepository leerjaarRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeerjaarMockMvc;

    private Leerjaar leerjaar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leerjaar createEntity(EntityManager em) {
        Leerjaar leerjaar = new Leerjaar().jaareinde(DEFAULT_JAAREINDE).jaarstart(DEFAULT_JAARSTART);
        return leerjaar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leerjaar createUpdatedEntity(EntityManager em) {
        Leerjaar leerjaar = new Leerjaar().jaareinde(UPDATED_JAAREINDE).jaarstart(UPDATED_JAARSTART);
        return leerjaar;
    }

    @BeforeEach
    public void initTest() {
        leerjaar = createEntity(em);
    }

    @Test
    @Transactional
    void createLeerjaar() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Leerjaar
        var returnedLeerjaar = om.readValue(
            restLeerjaarMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leerjaar)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Leerjaar.class
        );

        // Validate the Leerjaar in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLeerjaarUpdatableFieldsEquals(returnedLeerjaar, getPersistedLeerjaar(returnedLeerjaar));
    }

    @Test
    @Transactional
    void createLeerjaarWithExistingId() throws Exception {
        // Create the Leerjaar with an existing ID
        leerjaar.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeerjaarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leerjaar)))
            .andExpect(status().isBadRequest());

        // Validate the Leerjaar in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeerjaars() throws Exception {
        // Initialize the database
        leerjaarRepository.saveAndFlush(leerjaar);

        // Get all the leerjaarList
        restLeerjaarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leerjaar.getId().intValue())))
            .andExpect(jsonPath("$.[*].jaareinde").value(hasItem(DEFAULT_JAAREINDE)))
            .andExpect(jsonPath("$.[*].jaarstart").value(hasItem(DEFAULT_JAARSTART)));
    }

    @Test
    @Transactional
    void getLeerjaar() throws Exception {
        // Initialize the database
        leerjaarRepository.saveAndFlush(leerjaar);

        // Get the leerjaar
        restLeerjaarMockMvc
            .perform(get(ENTITY_API_URL_ID, leerjaar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leerjaar.getId().intValue()))
            .andExpect(jsonPath("$.jaareinde").value(DEFAULT_JAAREINDE))
            .andExpect(jsonPath("$.jaarstart").value(DEFAULT_JAARSTART));
    }

    @Test
    @Transactional
    void getNonExistingLeerjaar() throws Exception {
        // Get the leerjaar
        restLeerjaarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeerjaar() throws Exception {
        // Initialize the database
        leerjaarRepository.saveAndFlush(leerjaar);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leerjaar
        Leerjaar updatedLeerjaar = leerjaarRepository.findById(leerjaar.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLeerjaar are not directly saved in db
        em.detach(updatedLeerjaar);
        updatedLeerjaar.jaareinde(UPDATED_JAAREINDE).jaarstart(UPDATED_JAARSTART);

        restLeerjaarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeerjaar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLeerjaar))
            )
            .andExpect(status().isOk());

        // Validate the Leerjaar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLeerjaarToMatchAllProperties(updatedLeerjaar);
    }

    @Test
    @Transactional
    void putNonExistingLeerjaar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leerjaar.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeerjaarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leerjaar.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leerjaar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leerjaar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeerjaar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leerjaar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeerjaarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(leerjaar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leerjaar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeerjaar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leerjaar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeerjaarMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leerjaar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leerjaar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeerjaarWithPatch() throws Exception {
        // Initialize the database
        leerjaarRepository.saveAndFlush(leerjaar);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leerjaar using partial update
        Leerjaar partialUpdatedLeerjaar = new Leerjaar();
        partialUpdatedLeerjaar.setId(leerjaar.getId());

        partialUpdatedLeerjaar.jaareinde(UPDATED_JAAREINDE).jaarstart(UPDATED_JAARSTART);

        restLeerjaarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeerjaar.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLeerjaar))
            )
            .andExpect(status().isOk());

        // Validate the Leerjaar in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLeerjaarUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLeerjaar, leerjaar), getPersistedLeerjaar(leerjaar));
    }

    @Test
    @Transactional
    void fullUpdateLeerjaarWithPatch() throws Exception {
        // Initialize the database
        leerjaarRepository.saveAndFlush(leerjaar);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leerjaar using partial update
        Leerjaar partialUpdatedLeerjaar = new Leerjaar();
        partialUpdatedLeerjaar.setId(leerjaar.getId());

        partialUpdatedLeerjaar.jaareinde(UPDATED_JAAREINDE).jaarstart(UPDATED_JAARSTART);

        restLeerjaarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeerjaar.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLeerjaar))
            )
            .andExpect(status().isOk());

        // Validate the Leerjaar in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLeerjaarUpdatableFieldsEquals(partialUpdatedLeerjaar, getPersistedLeerjaar(partialUpdatedLeerjaar));
    }

    @Test
    @Transactional
    void patchNonExistingLeerjaar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leerjaar.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeerjaarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leerjaar.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(leerjaar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leerjaar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeerjaar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leerjaar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeerjaarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(leerjaar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leerjaar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeerjaar() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leerjaar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeerjaarMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(leerjaar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leerjaar in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeerjaar() throws Exception {
        // Initialize the database
        leerjaarRepository.saveAndFlush(leerjaar);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the leerjaar
        restLeerjaarMockMvc
            .perform(delete(ENTITY_API_URL_ID, leerjaar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return leerjaarRepository.count();
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

    protected Leerjaar getPersistedLeerjaar(Leerjaar leerjaar) {
        return leerjaarRepository.findById(leerjaar.getId()).orElseThrow();
    }

    protected void assertPersistedLeerjaarToMatchAllProperties(Leerjaar expectedLeerjaar) {
        assertLeerjaarAllPropertiesEquals(expectedLeerjaar, getPersistedLeerjaar(expectedLeerjaar));
    }

    protected void assertPersistedLeerjaarToMatchUpdatableProperties(Leerjaar expectedLeerjaar) {
        assertLeerjaarAllUpdatablePropertiesEquals(expectedLeerjaar, getPersistedLeerjaar(expectedLeerjaar));
    }
}
