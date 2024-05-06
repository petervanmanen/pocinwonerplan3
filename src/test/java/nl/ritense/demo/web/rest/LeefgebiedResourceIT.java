package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LeefgebiedAsserts.*;
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
import nl.ritense.demo.domain.Leefgebied;
import nl.ritense.demo.repository.LeefgebiedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LeefgebiedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeefgebiedResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/leefgebieds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LeefgebiedRepository leefgebiedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeefgebiedMockMvc;

    private Leefgebied leefgebied;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leefgebied createEntity(EntityManager em) {
        Leefgebied leefgebied = new Leefgebied().naam(DEFAULT_NAAM);
        return leefgebied;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leefgebied createUpdatedEntity(EntityManager em) {
        Leefgebied leefgebied = new Leefgebied().naam(UPDATED_NAAM);
        return leefgebied;
    }

    @BeforeEach
    public void initTest() {
        leefgebied = createEntity(em);
    }

    @Test
    @Transactional
    void createLeefgebied() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Leefgebied
        var returnedLeefgebied = om.readValue(
            restLeefgebiedMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leefgebied)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Leefgebied.class
        );

        // Validate the Leefgebied in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLeefgebiedUpdatableFieldsEquals(returnedLeefgebied, getPersistedLeefgebied(returnedLeefgebied));
    }

    @Test
    @Transactional
    void createLeefgebiedWithExistingId() throws Exception {
        // Create the Leefgebied with an existing ID
        leefgebied.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeefgebiedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leefgebied)))
            .andExpect(status().isBadRequest());

        // Validate the Leefgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeefgebieds() throws Exception {
        // Initialize the database
        leefgebiedRepository.saveAndFlush(leefgebied);

        // Get all the leefgebiedList
        restLeefgebiedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leefgebied.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getLeefgebied() throws Exception {
        // Initialize the database
        leefgebiedRepository.saveAndFlush(leefgebied);

        // Get the leefgebied
        restLeefgebiedMockMvc
            .perform(get(ENTITY_API_URL_ID, leefgebied.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leefgebied.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingLeefgebied() throws Exception {
        // Get the leefgebied
        restLeefgebiedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeefgebied() throws Exception {
        // Initialize the database
        leefgebiedRepository.saveAndFlush(leefgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leefgebied
        Leefgebied updatedLeefgebied = leefgebiedRepository.findById(leefgebied.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLeefgebied are not directly saved in db
        em.detach(updatedLeefgebied);
        updatedLeefgebied.naam(UPDATED_NAAM);

        restLeefgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeefgebied.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLeefgebied))
            )
            .andExpect(status().isOk());

        // Validate the Leefgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLeefgebiedToMatchAllProperties(updatedLeefgebied);
    }

    @Test
    @Transactional
    void putNonExistingLeefgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leefgebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeefgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leefgebied.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leefgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leefgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeefgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leefgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeefgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(leefgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leefgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeefgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leefgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeefgebiedMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leefgebied)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leefgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeefgebiedWithPatch() throws Exception {
        // Initialize the database
        leefgebiedRepository.saveAndFlush(leefgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leefgebied using partial update
        Leefgebied partialUpdatedLeefgebied = new Leefgebied();
        partialUpdatedLeefgebied.setId(leefgebied.getId());

        restLeefgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeefgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLeefgebied))
            )
            .andExpect(status().isOk());

        // Validate the Leefgebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLeefgebiedUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLeefgebied, leefgebied),
            getPersistedLeefgebied(leefgebied)
        );
    }

    @Test
    @Transactional
    void fullUpdateLeefgebiedWithPatch() throws Exception {
        // Initialize the database
        leefgebiedRepository.saveAndFlush(leefgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leefgebied using partial update
        Leefgebied partialUpdatedLeefgebied = new Leefgebied();
        partialUpdatedLeefgebied.setId(leefgebied.getId());

        partialUpdatedLeefgebied.naam(UPDATED_NAAM);

        restLeefgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeefgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLeefgebied))
            )
            .andExpect(status().isOk());

        // Validate the Leefgebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLeefgebiedUpdatableFieldsEquals(partialUpdatedLeefgebied, getPersistedLeefgebied(partialUpdatedLeefgebied));
    }

    @Test
    @Transactional
    void patchNonExistingLeefgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leefgebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeefgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leefgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(leefgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leefgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeefgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leefgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeefgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(leefgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leefgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeefgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leefgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeefgebiedMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(leefgebied)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leefgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeefgebied() throws Exception {
        // Initialize the database
        leefgebiedRepository.saveAndFlush(leefgebied);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the leefgebied
        restLeefgebiedMockMvc
            .perform(delete(ENTITY_API_URL_ID, leefgebied.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return leefgebiedRepository.count();
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

    protected Leefgebied getPersistedLeefgebied(Leefgebied leefgebied) {
        return leefgebiedRepository.findById(leefgebied.getId()).orElseThrow();
    }

    protected void assertPersistedLeefgebiedToMatchAllProperties(Leefgebied expectedLeefgebied) {
        assertLeefgebiedAllPropertiesEquals(expectedLeefgebied, getPersistedLeefgebied(expectedLeefgebied));
    }

    protected void assertPersistedLeefgebiedToMatchUpdatableProperties(Leefgebied expectedLeefgebied) {
        assertLeefgebiedAllUpdatablePropertiesEquals(expectedLeefgebied, getPersistedLeefgebied(expectedLeefgebied));
    }
}
