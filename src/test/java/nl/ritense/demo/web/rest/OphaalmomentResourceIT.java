package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OphaalmomentAsserts.*;
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
import nl.ritense.demo.domain.Ophaalmoment;
import nl.ritense.demo.repository.OphaalmomentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OphaalmomentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OphaalmomentResourceIT {

    private static final String DEFAULT_GEWICHTSTOENAME = "AAAAAAAAAA";
    private static final String UPDATED_GEWICHTSTOENAME = "BBBBBBBBBB";

    private static final String DEFAULT_TIJDSTIP = "AAAAAAAAAA";
    private static final String UPDATED_TIJDSTIP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ophaalmoments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OphaalmomentRepository ophaalmomentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOphaalmomentMockMvc;

    private Ophaalmoment ophaalmoment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ophaalmoment createEntity(EntityManager em) {
        Ophaalmoment ophaalmoment = new Ophaalmoment().gewichtstoename(DEFAULT_GEWICHTSTOENAME).tijdstip(DEFAULT_TIJDSTIP);
        return ophaalmoment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ophaalmoment createUpdatedEntity(EntityManager em) {
        Ophaalmoment ophaalmoment = new Ophaalmoment().gewichtstoename(UPDATED_GEWICHTSTOENAME).tijdstip(UPDATED_TIJDSTIP);
        return ophaalmoment;
    }

    @BeforeEach
    public void initTest() {
        ophaalmoment = createEntity(em);
    }

    @Test
    @Transactional
    void createOphaalmoment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ophaalmoment
        var returnedOphaalmoment = om.readValue(
            restOphaalmomentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ophaalmoment)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Ophaalmoment.class
        );

        // Validate the Ophaalmoment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOphaalmomentUpdatableFieldsEquals(returnedOphaalmoment, getPersistedOphaalmoment(returnedOphaalmoment));
    }

    @Test
    @Transactional
    void createOphaalmomentWithExistingId() throws Exception {
        // Create the Ophaalmoment with an existing ID
        ophaalmoment.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOphaalmomentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ophaalmoment)))
            .andExpect(status().isBadRequest());

        // Validate the Ophaalmoment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOphaalmoments() throws Exception {
        // Initialize the database
        ophaalmomentRepository.saveAndFlush(ophaalmoment);

        // Get all the ophaalmomentList
        restOphaalmomentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ophaalmoment.getId().intValue())))
            .andExpect(jsonPath("$.[*].gewichtstoename").value(hasItem(DEFAULT_GEWICHTSTOENAME)))
            .andExpect(jsonPath("$.[*].tijdstip").value(hasItem(DEFAULT_TIJDSTIP)));
    }

    @Test
    @Transactional
    void getOphaalmoment() throws Exception {
        // Initialize the database
        ophaalmomentRepository.saveAndFlush(ophaalmoment);

        // Get the ophaalmoment
        restOphaalmomentMockMvc
            .perform(get(ENTITY_API_URL_ID, ophaalmoment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ophaalmoment.getId().intValue()))
            .andExpect(jsonPath("$.gewichtstoename").value(DEFAULT_GEWICHTSTOENAME))
            .andExpect(jsonPath("$.tijdstip").value(DEFAULT_TIJDSTIP));
    }

    @Test
    @Transactional
    void getNonExistingOphaalmoment() throws Exception {
        // Get the ophaalmoment
        restOphaalmomentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOphaalmoment() throws Exception {
        // Initialize the database
        ophaalmomentRepository.saveAndFlush(ophaalmoment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ophaalmoment
        Ophaalmoment updatedOphaalmoment = ophaalmomentRepository.findById(ophaalmoment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOphaalmoment are not directly saved in db
        em.detach(updatedOphaalmoment);
        updatedOphaalmoment.gewichtstoename(UPDATED_GEWICHTSTOENAME).tijdstip(UPDATED_TIJDSTIP);

        restOphaalmomentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOphaalmoment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOphaalmoment))
            )
            .andExpect(status().isOk());

        // Validate the Ophaalmoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOphaalmomentToMatchAllProperties(updatedOphaalmoment);
    }

    @Test
    @Transactional
    void putNonExistingOphaalmoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ophaalmoment.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOphaalmomentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ophaalmoment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ophaalmoment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ophaalmoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOphaalmoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ophaalmoment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOphaalmomentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ophaalmoment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ophaalmoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOphaalmoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ophaalmoment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOphaalmomentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ophaalmoment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ophaalmoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOphaalmomentWithPatch() throws Exception {
        // Initialize the database
        ophaalmomentRepository.saveAndFlush(ophaalmoment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ophaalmoment using partial update
        Ophaalmoment partialUpdatedOphaalmoment = new Ophaalmoment();
        partialUpdatedOphaalmoment.setId(ophaalmoment.getId());

        partialUpdatedOphaalmoment.gewichtstoename(UPDATED_GEWICHTSTOENAME).tijdstip(UPDATED_TIJDSTIP);

        restOphaalmomentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOphaalmoment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOphaalmoment))
            )
            .andExpect(status().isOk());

        // Validate the Ophaalmoment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOphaalmomentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOphaalmoment, ophaalmoment),
            getPersistedOphaalmoment(ophaalmoment)
        );
    }

    @Test
    @Transactional
    void fullUpdateOphaalmomentWithPatch() throws Exception {
        // Initialize the database
        ophaalmomentRepository.saveAndFlush(ophaalmoment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ophaalmoment using partial update
        Ophaalmoment partialUpdatedOphaalmoment = new Ophaalmoment();
        partialUpdatedOphaalmoment.setId(ophaalmoment.getId());

        partialUpdatedOphaalmoment.gewichtstoename(UPDATED_GEWICHTSTOENAME).tijdstip(UPDATED_TIJDSTIP);

        restOphaalmomentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOphaalmoment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOphaalmoment))
            )
            .andExpect(status().isOk());

        // Validate the Ophaalmoment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOphaalmomentUpdatableFieldsEquals(partialUpdatedOphaalmoment, getPersistedOphaalmoment(partialUpdatedOphaalmoment));
    }

    @Test
    @Transactional
    void patchNonExistingOphaalmoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ophaalmoment.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOphaalmomentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ophaalmoment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ophaalmoment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ophaalmoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOphaalmoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ophaalmoment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOphaalmomentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ophaalmoment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ophaalmoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOphaalmoment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ophaalmoment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOphaalmomentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ophaalmoment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ophaalmoment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOphaalmoment() throws Exception {
        // Initialize the database
        ophaalmomentRepository.saveAndFlush(ophaalmoment);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ophaalmoment
        restOphaalmomentMockMvc
            .perform(delete(ENTITY_API_URL_ID, ophaalmoment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ophaalmomentRepository.count();
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

    protected Ophaalmoment getPersistedOphaalmoment(Ophaalmoment ophaalmoment) {
        return ophaalmomentRepository.findById(ophaalmoment.getId()).orElseThrow();
    }

    protected void assertPersistedOphaalmomentToMatchAllProperties(Ophaalmoment expectedOphaalmoment) {
        assertOphaalmomentAllPropertiesEquals(expectedOphaalmoment, getPersistedOphaalmoment(expectedOphaalmoment));
    }

    protected void assertPersistedOphaalmomentToMatchUpdatableProperties(Ophaalmoment expectedOphaalmoment) {
        assertOphaalmomentAllUpdatablePropertiesEquals(expectedOphaalmoment, getPersistedOphaalmoment(expectedOphaalmoment));
    }
}
