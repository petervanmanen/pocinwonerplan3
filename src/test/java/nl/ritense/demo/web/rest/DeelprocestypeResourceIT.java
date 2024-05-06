package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DeelprocestypeAsserts.*;
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
import nl.ritense.demo.domain.Deelprocestype;
import nl.ritense.demo.repository.DeelprocestypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DeelprocestypeResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class DeelprocestypeResourceIT {

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/deelprocestypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DeelprocestypeRepository deelprocestypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeelprocestypeMockMvc;

    private Deelprocestype deelprocestype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deelprocestype createEntity(EntityManager em) {
        Deelprocestype deelprocestype = new Deelprocestype().omschrijving(DEFAULT_OMSCHRIJVING);
        return deelprocestype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deelprocestype createUpdatedEntity(EntityManager em) {
        Deelprocestype deelprocestype = new Deelprocestype().omschrijving(UPDATED_OMSCHRIJVING);
        return deelprocestype;
    }

    @BeforeEach
    public void initTest() {
        deelprocestype = createEntity(em);
    }

    @Test
    @Transactional
    void createDeelprocestype() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Deelprocestype
        var returnedDeelprocestype = om.readValue(
            restDeelprocestypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(deelprocestype)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Deelprocestype.class
        );

        // Validate the Deelprocestype in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDeelprocestypeUpdatableFieldsEquals(returnedDeelprocestype, getPersistedDeelprocestype(returnedDeelprocestype));
    }

    @Test
    @Transactional
    void createDeelprocestypeWithExistingId() throws Exception {
        // Create the Deelprocestype with an existing ID
        deelprocestype.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeelprocestypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(deelprocestype)))
            .andExpect(status().isBadRequest());

        // Validate the Deelprocestype in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeelprocestypes() throws Exception {
        // Initialize the database
        deelprocestypeRepository.saveAndFlush(deelprocestype);

        // Get all the deelprocestypeList
        restDeelprocestypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deelprocestype.getId().intValue())))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getDeelprocestype() throws Exception {
        // Initialize the database
        deelprocestypeRepository.saveAndFlush(deelprocestype);

        // Get the deelprocestype
        restDeelprocestypeMockMvc
            .perform(get(ENTITY_API_URL_ID, deelprocestype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deelprocestype.getId().intValue()))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingDeelprocestype() throws Exception {
        // Get the deelprocestype
        restDeelprocestypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDeelprocestype() throws Exception {
        // Initialize the database
        deelprocestypeRepository.saveAndFlush(deelprocestype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the deelprocestype
        Deelprocestype updatedDeelprocestype = deelprocestypeRepository.findById(deelprocestype.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDeelprocestype are not directly saved in db
        em.detach(updatedDeelprocestype);
        updatedDeelprocestype.omschrijving(UPDATED_OMSCHRIJVING);

        restDeelprocestypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDeelprocestype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDeelprocestype))
            )
            .andExpect(status().isOk());

        // Validate the Deelprocestype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDeelprocestypeToMatchAllProperties(updatedDeelprocestype);
    }

    @Test
    @Transactional
    void putNonExistingDeelprocestype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deelprocestype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeelprocestypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deelprocestype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(deelprocestype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deelprocestype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeelprocestype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deelprocestype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeelprocestypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(deelprocestype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deelprocestype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeelprocestype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deelprocestype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeelprocestypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(deelprocestype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deelprocestype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeelprocestypeWithPatch() throws Exception {
        // Initialize the database
        deelprocestypeRepository.saveAndFlush(deelprocestype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the deelprocestype using partial update
        Deelprocestype partialUpdatedDeelprocestype = new Deelprocestype();
        partialUpdatedDeelprocestype.setId(deelprocestype.getId());

        partialUpdatedDeelprocestype.omschrijving(UPDATED_OMSCHRIJVING);

        restDeelprocestypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeelprocestype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDeelprocestype))
            )
            .andExpect(status().isOk());

        // Validate the Deelprocestype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDeelprocestypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDeelprocestype, deelprocestype),
            getPersistedDeelprocestype(deelprocestype)
        );
    }

    @Test
    @Transactional
    void fullUpdateDeelprocestypeWithPatch() throws Exception {
        // Initialize the database
        deelprocestypeRepository.saveAndFlush(deelprocestype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the deelprocestype using partial update
        Deelprocestype partialUpdatedDeelprocestype = new Deelprocestype();
        partialUpdatedDeelprocestype.setId(deelprocestype.getId());

        partialUpdatedDeelprocestype.omschrijving(UPDATED_OMSCHRIJVING);

        restDeelprocestypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeelprocestype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDeelprocestype))
            )
            .andExpect(status().isOk());

        // Validate the Deelprocestype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDeelprocestypeUpdatableFieldsEquals(partialUpdatedDeelprocestype, getPersistedDeelprocestype(partialUpdatedDeelprocestype));
    }

    @Test
    @Transactional
    void patchNonExistingDeelprocestype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deelprocestype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeelprocestypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deelprocestype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(deelprocestype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deelprocestype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeelprocestype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deelprocestype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeelprocestypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(deelprocestype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deelprocestype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeelprocestype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deelprocestype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeelprocestypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(deelprocestype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deelprocestype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeelprocestype() throws Exception {
        // Initialize the database
        deelprocestypeRepository.saveAndFlush(deelprocestype);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the deelprocestype
        restDeelprocestypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, deelprocestype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return deelprocestypeRepository.count();
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

    protected Deelprocestype getPersistedDeelprocestype(Deelprocestype deelprocestype) {
        return deelprocestypeRepository.findById(deelprocestype.getId()).orElseThrow();
    }

    protected void assertPersistedDeelprocestypeToMatchAllProperties(Deelprocestype expectedDeelprocestype) {
        assertDeelprocestypeAllPropertiesEquals(expectedDeelprocestype, getPersistedDeelprocestype(expectedDeelprocestype));
    }

    protected void assertPersistedDeelprocestypeToMatchUpdatableProperties(Deelprocestype expectedDeelprocestype) {
        assertDeelprocestypeAllUpdatablePropertiesEquals(expectedDeelprocestype, getPersistedDeelprocestype(expectedDeelprocestype));
    }
}
