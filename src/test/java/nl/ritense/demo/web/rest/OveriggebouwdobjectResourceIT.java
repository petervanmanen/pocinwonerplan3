package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OveriggebouwdobjectAsserts.*;
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
import nl.ritense.demo.domain.Overiggebouwdobject;
import nl.ritense.demo.repository.OveriggebouwdobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OveriggebouwdobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OveriggebouwdobjectResourceIT {

    private static final String DEFAULT_BOUWJAAR = "AAAAAAAAAA";
    private static final String UPDATED_BOUWJAAR = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIEPLANOBJECT = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEPLANOBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_OVERIGGEBOUWDOBJECTIDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_OVERIGGEBOUWDOBJECTIDENTIFICATIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/overiggebouwdobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OveriggebouwdobjectRepository overiggebouwdobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOveriggebouwdobjectMockMvc;

    private Overiggebouwdobject overiggebouwdobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overiggebouwdobject createEntity(EntityManager em) {
        Overiggebouwdobject overiggebouwdobject = new Overiggebouwdobject()
            .bouwjaar(DEFAULT_BOUWJAAR)
            .indicatieplanobject(DEFAULT_INDICATIEPLANOBJECT)
            .overiggebouwdobjectidentificatie(DEFAULT_OVERIGGEBOUWDOBJECTIDENTIFICATIE);
        return overiggebouwdobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overiggebouwdobject createUpdatedEntity(EntityManager em) {
        Overiggebouwdobject overiggebouwdobject = new Overiggebouwdobject()
            .bouwjaar(UPDATED_BOUWJAAR)
            .indicatieplanobject(UPDATED_INDICATIEPLANOBJECT)
            .overiggebouwdobjectidentificatie(UPDATED_OVERIGGEBOUWDOBJECTIDENTIFICATIE);
        return overiggebouwdobject;
    }

    @BeforeEach
    public void initTest() {
        overiggebouwdobject = createEntity(em);
    }

    @Test
    @Transactional
    void createOveriggebouwdobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Overiggebouwdobject
        var returnedOveriggebouwdobject = om.readValue(
            restOveriggebouwdobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overiggebouwdobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Overiggebouwdobject.class
        );

        // Validate the Overiggebouwdobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOveriggebouwdobjectUpdatableFieldsEquals(
            returnedOveriggebouwdobject,
            getPersistedOveriggebouwdobject(returnedOveriggebouwdobject)
        );
    }

    @Test
    @Transactional
    void createOveriggebouwdobjectWithExistingId() throws Exception {
        // Create the Overiggebouwdobject with an existing ID
        overiggebouwdobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOveriggebouwdobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overiggebouwdobject)))
            .andExpect(status().isBadRequest());

        // Validate the Overiggebouwdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOveriggebouwdobjects() throws Exception {
        // Initialize the database
        overiggebouwdobjectRepository.saveAndFlush(overiggebouwdobject);

        // Get all the overiggebouwdobjectList
        restOveriggebouwdobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overiggebouwdobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].bouwjaar").value(hasItem(DEFAULT_BOUWJAAR)))
            .andExpect(jsonPath("$.[*].indicatieplanobject").value(hasItem(DEFAULT_INDICATIEPLANOBJECT)))
            .andExpect(jsonPath("$.[*].overiggebouwdobjectidentificatie").value(hasItem(DEFAULT_OVERIGGEBOUWDOBJECTIDENTIFICATIE)));
    }

    @Test
    @Transactional
    void getOveriggebouwdobject() throws Exception {
        // Initialize the database
        overiggebouwdobjectRepository.saveAndFlush(overiggebouwdobject);

        // Get the overiggebouwdobject
        restOveriggebouwdobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, overiggebouwdobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(overiggebouwdobject.getId().intValue()))
            .andExpect(jsonPath("$.bouwjaar").value(DEFAULT_BOUWJAAR))
            .andExpect(jsonPath("$.indicatieplanobject").value(DEFAULT_INDICATIEPLANOBJECT))
            .andExpect(jsonPath("$.overiggebouwdobjectidentificatie").value(DEFAULT_OVERIGGEBOUWDOBJECTIDENTIFICATIE));
    }

    @Test
    @Transactional
    void getNonExistingOveriggebouwdobject() throws Exception {
        // Get the overiggebouwdobject
        restOveriggebouwdobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOveriggebouwdobject() throws Exception {
        // Initialize the database
        overiggebouwdobjectRepository.saveAndFlush(overiggebouwdobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overiggebouwdobject
        Overiggebouwdobject updatedOveriggebouwdobject = overiggebouwdobjectRepository.findById(overiggebouwdobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOveriggebouwdobject are not directly saved in db
        em.detach(updatedOveriggebouwdobject);
        updatedOveriggebouwdobject
            .bouwjaar(UPDATED_BOUWJAAR)
            .indicatieplanobject(UPDATED_INDICATIEPLANOBJECT)
            .overiggebouwdobjectidentificatie(UPDATED_OVERIGGEBOUWDOBJECTIDENTIFICATIE);

        restOveriggebouwdobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOveriggebouwdobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOveriggebouwdobject))
            )
            .andExpect(status().isOk());

        // Validate the Overiggebouwdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOveriggebouwdobjectToMatchAllProperties(updatedOveriggebouwdobject);
    }

    @Test
    @Transactional
    void putNonExistingOveriggebouwdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overiggebouwdobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOveriggebouwdobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, overiggebouwdobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overiggebouwdobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overiggebouwdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOveriggebouwdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overiggebouwdobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOveriggebouwdobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overiggebouwdobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overiggebouwdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOveriggebouwdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overiggebouwdobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOveriggebouwdobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overiggebouwdobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overiggebouwdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOveriggebouwdobjectWithPatch() throws Exception {
        // Initialize the database
        overiggebouwdobjectRepository.saveAndFlush(overiggebouwdobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overiggebouwdobject using partial update
        Overiggebouwdobject partialUpdatedOveriggebouwdobject = new Overiggebouwdobject();
        partialUpdatedOveriggebouwdobject.setId(overiggebouwdobject.getId());

        partialUpdatedOveriggebouwdobject.bouwjaar(UPDATED_BOUWJAAR).indicatieplanobject(UPDATED_INDICATIEPLANOBJECT);

        restOveriggebouwdobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOveriggebouwdobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOveriggebouwdobject))
            )
            .andExpect(status().isOk());

        // Validate the Overiggebouwdobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOveriggebouwdobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOveriggebouwdobject, overiggebouwdobject),
            getPersistedOveriggebouwdobject(overiggebouwdobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateOveriggebouwdobjectWithPatch() throws Exception {
        // Initialize the database
        overiggebouwdobjectRepository.saveAndFlush(overiggebouwdobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overiggebouwdobject using partial update
        Overiggebouwdobject partialUpdatedOveriggebouwdobject = new Overiggebouwdobject();
        partialUpdatedOveriggebouwdobject.setId(overiggebouwdobject.getId());

        partialUpdatedOveriggebouwdobject
            .bouwjaar(UPDATED_BOUWJAAR)
            .indicatieplanobject(UPDATED_INDICATIEPLANOBJECT)
            .overiggebouwdobjectidentificatie(UPDATED_OVERIGGEBOUWDOBJECTIDENTIFICATIE);

        restOveriggebouwdobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOveriggebouwdobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOveriggebouwdobject))
            )
            .andExpect(status().isOk());

        // Validate the Overiggebouwdobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOveriggebouwdobjectUpdatableFieldsEquals(
            partialUpdatedOveriggebouwdobject,
            getPersistedOveriggebouwdobject(partialUpdatedOveriggebouwdobject)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOveriggebouwdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overiggebouwdobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOveriggebouwdobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, overiggebouwdobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overiggebouwdobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overiggebouwdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOveriggebouwdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overiggebouwdobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOveriggebouwdobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overiggebouwdobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overiggebouwdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOveriggebouwdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overiggebouwdobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOveriggebouwdobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(overiggebouwdobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overiggebouwdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOveriggebouwdobject() throws Exception {
        // Initialize the database
        overiggebouwdobjectRepository.saveAndFlush(overiggebouwdobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the overiggebouwdobject
        restOveriggebouwdobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, overiggebouwdobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return overiggebouwdobjectRepository.count();
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

    protected Overiggebouwdobject getPersistedOveriggebouwdobject(Overiggebouwdobject overiggebouwdobject) {
        return overiggebouwdobjectRepository.findById(overiggebouwdobject.getId()).orElseThrow();
    }

    protected void assertPersistedOveriggebouwdobjectToMatchAllProperties(Overiggebouwdobject expectedOveriggebouwdobject) {
        assertOveriggebouwdobjectAllPropertiesEquals(
            expectedOveriggebouwdobject,
            getPersistedOveriggebouwdobject(expectedOveriggebouwdobject)
        );
    }

    protected void assertPersistedOveriggebouwdobjectToMatchUpdatableProperties(Overiggebouwdobject expectedOveriggebouwdobject) {
        assertOveriggebouwdobjectAllUpdatablePropertiesEquals(
            expectedOveriggebouwdobject,
            getPersistedOveriggebouwdobject(expectedOveriggebouwdobject)
        );
    }
}
