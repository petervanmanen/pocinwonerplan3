package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AfspraakstatusAsserts.*;
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
import nl.ritense.demo.domain.Afspraakstatus;
import nl.ritense.demo.repository.AfspraakstatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AfspraakstatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AfspraakstatusResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/afspraakstatuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AfspraakstatusRepository afspraakstatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAfspraakstatusMockMvc;

    private Afspraakstatus afspraakstatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Afspraakstatus createEntity(EntityManager em) {
        Afspraakstatus afspraakstatus = new Afspraakstatus().status(DEFAULT_STATUS);
        return afspraakstatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Afspraakstatus createUpdatedEntity(EntityManager em) {
        Afspraakstatus afspraakstatus = new Afspraakstatus().status(UPDATED_STATUS);
        return afspraakstatus;
    }

    @BeforeEach
    public void initTest() {
        afspraakstatus = createEntity(em);
    }

    @Test
    @Transactional
    void createAfspraakstatus() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Afspraakstatus
        var returnedAfspraakstatus = om.readValue(
            restAfspraakstatusMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(afspraakstatus)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Afspraakstatus.class
        );

        // Validate the Afspraakstatus in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAfspraakstatusUpdatableFieldsEquals(returnedAfspraakstatus, getPersistedAfspraakstatus(returnedAfspraakstatus));
    }

    @Test
    @Transactional
    void createAfspraakstatusWithExistingId() throws Exception {
        // Create the Afspraakstatus with an existing ID
        afspraakstatus.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAfspraakstatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(afspraakstatus)))
            .andExpect(status().isBadRequest());

        // Validate the Afspraakstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAfspraakstatuses() throws Exception {
        // Initialize the database
        afspraakstatusRepository.saveAndFlush(afspraakstatus);

        // Get all the afspraakstatusList
        restAfspraakstatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(afspraakstatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getAfspraakstatus() throws Exception {
        // Initialize the database
        afspraakstatusRepository.saveAndFlush(afspraakstatus);

        // Get the afspraakstatus
        restAfspraakstatusMockMvc
            .perform(get(ENTITY_API_URL_ID, afspraakstatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(afspraakstatus.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingAfspraakstatus() throws Exception {
        // Get the afspraakstatus
        restAfspraakstatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAfspraakstatus() throws Exception {
        // Initialize the database
        afspraakstatusRepository.saveAndFlush(afspraakstatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the afspraakstatus
        Afspraakstatus updatedAfspraakstatus = afspraakstatusRepository.findById(afspraakstatus.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAfspraakstatus are not directly saved in db
        em.detach(updatedAfspraakstatus);
        updatedAfspraakstatus.status(UPDATED_STATUS);

        restAfspraakstatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAfspraakstatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAfspraakstatus))
            )
            .andExpect(status().isOk());

        // Validate the Afspraakstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAfspraakstatusToMatchAllProperties(updatedAfspraakstatus);
    }

    @Test
    @Transactional
    void putNonExistingAfspraakstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afspraakstatus.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAfspraakstatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, afspraakstatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(afspraakstatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afspraakstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAfspraakstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afspraakstatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfspraakstatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(afspraakstatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afspraakstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAfspraakstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afspraakstatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfspraakstatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(afspraakstatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Afspraakstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAfspraakstatusWithPatch() throws Exception {
        // Initialize the database
        afspraakstatusRepository.saveAndFlush(afspraakstatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the afspraakstatus using partial update
        Afspraakstatus partialUpdatedAfspraakstatus = new Afspraakstatus();
        partialUpdatedAfspraakstatus.setId(afspraakstatus.getId());

        partialUpdatedAfspraakstatus.status(UPDATED_STATUS);

        restAfspraakstatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAfspraakstatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAfspraakstatus))
            )
            .andExpect(status().isOk());

        // Validate the Afspraakstatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAfspraakstatusUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAfspraakstatus, afspraakstatus),
            getPersistedAfspraakstatus(afspraakstatus)
        );
    }

    @Test
    @Transactional
    void fullUpdateAfspraakstatusWithPatch() throws Exception {
        // Initialize the database
        afspraakstatusRepository.saveAndFlush(afspraakstatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the afspraakstatus using partial update
        Afspraakstatus partialUpdatedAfspraakstatus = new Afspraakstatus();
        partialUpdatedAfspraakstatus.setId(afspraakstatus.getId());

        partialUpdatedAfspraakstatus.status(UPDATED_STATUS);

        restAfspraakstatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAfspraakstatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAfspraakstatus))
            )
            .andExpect(status().isOk());

        // Validate the Afspraakstatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAfspraakstatusUpdatableFieldsEquals(partialUpdatedAfspraakstatus, getPersistedAfspraakstatus(partialUpdatedAfspraakstatus));
    }

    @Test
    @Transactional
    void patchNonExistingAfspraakstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afspraakstatus.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAfspraakstatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, afspraakstatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(afspraakstatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afspraakstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAfspraakstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afspraakstatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfspraakstatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(afspraakstatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afspraakstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAfspraakstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afspraakstatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfspraakstatusMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(afspraakstatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Afspraakstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAfspraakstatus() throws Exception {
        // Initialize the database
        afspraakstatusRepository.saveAndFlush(afspraakstatus);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the afspraakstatus
        restAfspraakstatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, afspraakstatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return afspraakstatusRepository.count();
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

    protected Afspraakstatus getPersistedAfspraakstatus(Afspraakstatus afspraakstatus) {
        return afspraakstatusRepository.findById(afspraakstatus.getId()).orElseThrow();
    }

    protected void assertPersistedAfspraakstatusToMatchAllProperties(Afspraakstatus expectedAfspraakstatus) {
        assertAfspraakstatusAllPropertiesEquals(expectedAfspraakstatus, getPersistedAfspraakstatus(expectedAfspraakstatus));
    }

    protected void assertPersistedAfspraakstatusToMatchUpdatableProperties(Afspraakstatus expectedAfspraakstatus) {
        assertAfspraakstatusAllUpdatablePropertiesEquals(expectedAfspraakstatus, getPersistedAfspraakstatus(expectedAfspraakstatus));
    }
}
