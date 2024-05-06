package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AanvraagdataAsserts.*;
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
import nl.ritense.demo.domain.Aanvraagdata;
import nl.ritense.demo.repository.AanvraagdataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AanvraagdataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AanvraagdataResourceIT {

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_VELD = "AAAAAAAAAA";
    private static final String UPDATED_VELD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aanvraagdata";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AanvraagdataRepository aanvraagdataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAanvraagdataMockMvc;

    private Aanvraagdata aanvraagdata;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanvraagdata createEntity(EntityManager em) {
        Aanvraagdata aanvraagdata = new Aanvraagdata().data(DEFAULT_DATA).veld(DEFAULT_VELD);
        return aanvraagdata;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanvraagdata createUpdatedEntity(EntityManager em) {
        Aanvraagdata aanvraagdata = new Aanvraagdata().data(UPDATED_DATA).veld(UPDATED_VELD);
        return aanvraagdata;
    }

    @BeforeEach
    public void initTest() {
        aanvraagdata = createEntity(em);
    }

    @Test
    @Transactional
    void createAanvraagdata() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aanvraagdata
        var returnedAanvraagdata = om.readValue(
            restAanvraagdataMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraagdata)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aanvraagdata.class
        );

        // Validate the Aanvraagdata in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAanvraagdataUpdatableFieldsEquals(returnedAanvraagdata, getPersistedAanvraagdata(returnedAanvraagdata));
    }

    @Test
    @Transactional
    void createAanvraagdataWithExistingId() throws Exception {
        // Create the Aanvraagdata with an existing ID
        aanvraagdata.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAanvraagdataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraagdata)))
            .andExpect(status().isBadRequest());

        // Validate the Aanvraagdata in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAanvraagdata() throws Exception {
        // Initialize the database
        aanvraagdataRepository.saveAndFlush(aanvraagdata);

        // Get all the aanvraagdataList
        restAanvraagdataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aanvraagdata.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA)))
            .andExpect(jsonPath("$.[*].veld").value(hasItem(DEFAULT_VELD)));
    }

    @Test
    @Transactional
    void getAanvraagdata() throws Exception {
        // Initialize the database
        aanvraagdataRepository.saveAndFlush(aanvraagdata);

        // Get the aanvraagdata
        restAanvraagdataMockMvc
            .perform(get(ENTITY_API_URL_ID, aanvraagdata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aanvraagdata.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA))
            .andExpect(jsonPath("$.veld").value(DEFAULT_VELD));
    }

    @Test
    @Transactional
    void getNonExistingAanvraagdata() throws Exception {
        // Get the aanvraagdata
        restAanvraagdataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAanvraagdata() throws Exception {
        // Initialize the database
        aanvraagdataRepository.saveAndFlush(aanvraagdata);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanvraagdata
        Aanvraagdata updatedAanvraagdata = aanvraagdataRepository.findById(aanvraagdata.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAanvraagdata are not directly saved in db
        em.detach(updatedAanvraagdata);
        updatedAanvraagdata.data(UPDATED_DATA).veld(UPDATED_VELD);

        restAanvraagdataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAanvraagdata.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAanvraagdata))
            )
            .andExpect(status().isOk());

        // Validate the Aanvraagdata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAanvraagdataToMatchAllProperties(updatedAanvraagdata);
    }

    @Test
    @Transactional
    void putNonExistingAanvraagdata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagdata.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanvraagdataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aanvraagdata.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aanvraagdata))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraagdata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAanvraagdata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagdata.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraagdataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aanvraagdata))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraagdata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAanvraagdata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagdata.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraagdataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraagdata)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanvraagdata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAanvraagdataWithPatch() throws Exception {
        // Initialize the database
        aanvraagdataRepository.saveAndFlush(aanvraagdata);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanvraagdata using partial update
        Aanvraagdata partialUpdatedAanvraagdata = new Aanvraagdata();
        partialUpdatedAanvraagdata.setId(aanvraagdata.getId());

        partialUpdatedAanvraagdata.data(UPDATED_DATA);

        restAanvraagdataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanvraagdata.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanvraagdata))
            )
            .andExpect(status().isOk());

        // Validate the Aanvraagdata in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanvraagdataUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAanvraagdata, aanvraagdata),
            getPersistedAanvraagdata(aanvraagdata)
        );
    }

    @Test
    @Transactional
    void fullUpdateAanvraagdataWithPatch() throws Exception {
        // Initialize the database
        aanvraagdataRepository.saveAndFlush(aanvraagdata);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanvraagdata using partial update
        Aanvraagdata partialUpdatedAanvraagdata = new Aanvraagdata();
        partialUpdatedAanvraagdata.setId(aanvraagdata.getId());

        partialUpdatedAanvraagdata.data(UPDATED_DATA).veld(UPDATED_VELD);

        restAanvraagdataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanvraagdata.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanvraagdata))
            )
            .andExpect(status().isOk());

        // Validate the Aanvraagdata in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanvraagdataUpdatableFieldsEquals(partialUpdatedAanvraagdata, getPersistedAanvraagdata(partialUpdatedAanvraagdata));
    }

    @Test
    @Transactional
    void patchNonExistingAanvraagdata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagdata.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanvraagdataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aanvraagdata.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanvraagdata))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraagdata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAanvraagdata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagdata.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraagdataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanvraagdata))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraagdata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAanvraagdata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraagdata.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraagdataMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aanvraagdata)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanvraagdata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAanvraagdata() throws Exception {
        // Initialize the database
        aanvraagdataRepository.saveAndFlush(aanvraagdata);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aanvraagdata
        restAanvraagdataMockMvc
            .perform(delete(ENTITY_API_URL_ID, aanvraagdata.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aanvraagdataRepository.count();
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

    protected Aanvraagdata getPersistedAanvraagdata(Aanvraagdata aanvraagdata) {
        return aanvraagdataRepository.findById(aanvraagdata.getId()).orElseThrow();
    }

    protected void assertPersistedAanvraagdataToMatchAllProperties(Aanvraagdata expectedAanvraagdata) {
        assertAanvraagdataAllPropertiesEquals(expectedAanvraagdata, getPersistedAanvraagdata(expectedAanvraagdata));
    }

    protected void assertPersistedAanvraagdataToMatchUpdatableProperties(Aanvraagdata expectedAanvraagdata) {
        assertAanvraagdataAllUpdatablePropertiesEquals(expectedAanvraagdata, getPersistedAanvraagdata(expectedAanvraagdata));
    }
}
