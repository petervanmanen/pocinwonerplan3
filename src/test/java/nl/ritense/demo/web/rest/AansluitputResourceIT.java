package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AansluitputAsserts.*;
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
import nl.ritense.demo.domain.Aansluitput;
import nl.ritense.demo.repository.AansluitputRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AansluitputResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AansluitputResourceIT {

    private static final String DEFAULT_AANSLUITPUNT = "AAAAAAAAAA";
    private static final String UPDATED_AANSLUITPUNT = "BBBBBBBBBB";

    private static final String DEFAULT_RISICOGEBIED = "AAAAAAAAAA";
    private static final String UPDATED_RISICOGEBIED = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aansluitputs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AansluitputRepository aansluitputRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAansluitputMockMvc;

    private Aansluitput aansluitput;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aansluitput createEntity(EntityManager em) {
        Aansluitput aansluitput = new Aansluitput()
            .aansluitpunt(DEFAULT_AANSLUITPUNT)
            .risicogebied(DEFAULT_RISICOGEBIED)
            .type(DEFAULT_TYPE);
        return aansluitput;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aansluitput createUpdatedEntity(EntityManager em) {
        Aansluitput aansluitput = new Aansluitput()
            .aansluitpunt(UPDATED_AANSLUITPUNT)
            .risicogebied(UPDATED_RISICOGEBIED)
            .type(UPDATED_TYPE);
        return aansluitput;
    }

    @BeforeEach
    public void initTest() {
        aansluitput = createEntity(em);
    }

    @Test
    @Transactional
    void createAansluitput() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aansluitput
        var returnedAansluitput = om.readValue(
            restAansluitputMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aansluitput)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aansluitput.class
        );

        // Validate the Aansluitput in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAansluitputUpdatableFieldsEquals(returnedAansluitput, getPersistedAansluitput(returnedAansluitput));
    }

    @Test
    @Transactional
    void createAansluitputWithExistingId() throws Exception {
        // Create the Aansluitput with an existing ID
        aansluitput.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAansluitputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aansluitput)))
            .andExpect(status().isBadRequest());

        // Validate the Aansluitput in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAansluitputs() throws Exception {
        // Initialize the database
        aansluitputRepository.saveAndFlush(aansluitput);

        // Get all the aansluitputList
        restAansluitputMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aansluitput.getId().intValue())))
            .andExpect(jsonPath("$.[*].aansluitpunt").value(hasItem(DEFAULT_AANSLUITPUNT)))
            .andExpect(jsonPath("$.[*].risicogebied").value(hasItem(DEFAULT_RISICOGEBIED)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getAansluitput() throws Exception {
        // Initialize the database
        aansluitputRepository.saveAndFlush(aansluitput);

        // Get the aansluitput
        restAansluitputMockMvc
            .perform(get(ENTITY_API_URL_ID, aansluitput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aansluitput.getId().intValue()))
            .andExpect(jsonPath("$.aansluitpunt").value(DEFAULT_AANSLUITPUNT))
            .andExpect(jsonPath("$.risicogebied").value(DEFAULT_RISICOGEBIED))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingAansluitput() throws Exception {
        // Get the aansluitput
        restAansluitputMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAansluitput() throws Exception {
        // Initialize the database
        aansluitputRepository.saveAndFlush(aansluitput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aansluitput
        Aansluitput updatedAansluitput = aansluitputRepository.findById(aansluitput.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAansluitput are not directly saved in db
        em.detach(updatedAansluitput);
        updatedAansluitput.aansluitpunt(UPDATED_AANSLUITPUNT).risicogebied(UPDATED_RISICOGEBIED).type(UPDATED_TYPE);

        restAansluitputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAansluitput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAansluitput))
            )
            .andExpect(status().isOk());

        // Validate the Aansluitput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAansluitputToMatchAllProperties(updatedAansluitput);
    }

    @Test
    @Transactional
    void putNonExistingAansluitput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aansluitput.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAansluitputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aansluitput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aansluitput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aansluitput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAansluitput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aansluitput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAansluitputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aansluitput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aansluitput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAansluitput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aansluitput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAansluitputMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aansluitput)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aansluitput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAansluitputWithPatch() throws Exception {
        // Initialize the database
        aansluitputRepository.saveAndFlush(aansluitput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aansluitput using partial update
        Aansluitput partialUpdatedAansluitput = new Aansluitput();
        partialUpdatedAansluitput.setId(aansluitput.getId());

        partialUpdatedAansluitput.risicogebied(UPDATED_RISICOGEBIED).type(UPDATED_TYPE);

        restAansluitputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAansluitput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAansluitput))
            )
            .andExpect(status().isOk());

        // Validate the Aansluitput in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAansluitputUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAansluitput, aansluitput),
            getPersistedAansluitput(aansluitput)
        );
    }

    @Test
    @Transactional
    void fullUpdateAansluitputWithPatch() throws Exception {
        // Initialize the database
        aansluitputRepository.saveAndFlush(aansluitput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aansluitput using partial update
        Aansluitput partialUpdatedAansluitput = new Aansluitput();
        partialUpdatedAansluitput.setId(aansluitput.getId());

        partialUpdatedAansluitput.aansluitpunt(UPDATED_AANSLUITPUNT).risicogebied(UPDATED_RISICOGEBIED).type(UPDATED_TYPE);

        restAansluitputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAansluitput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAansluitput))
            )
            .andExpect(status().isOk());

        // Validate the Aansluitput in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAansluitputUpdatableFieldsEquals(partialUpdatedAansluitput, getPersistedAansluitput(partialUpdatedAansluitput));
    }

    @Test
    @Transactional
    void patchNonExistingAansluitput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aansluitput.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAansluitputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aansluitput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aansluitput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aansluitput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAansluitput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aansluitput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAansluitputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aansluitput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aansluitput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAansluitput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aansluitput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAansluitputMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aansluitput)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aansluitput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAansluitput() throws Exception {
        // Initialize the database
        aansluitputRepository.saveAndFlush(aansluitput);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aansluitput
        restAansluitputMockMvc
            .perform(delete(ENTITY_API_URL_ID, aansluitput.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aansluitputRepository.count();
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

    protected Aansluitput getPersistedAansluitput(Aansluitput aansluitput) {
        return aansluitputRepository.findById(aansluitput.getId()).orElseThrow();
    }

    protected void assertPersistedAansluitputToMatchAllProperties(Aansluitput expectedAansluitput) {
        assertAansluitputAllPropertiesEquals(expectedAansluitput, getPersistedAansluitput(expectedAansluitput));
    }

    protected void assertPersistedAansluitputToMatchUpdatableProperties(Aansluitput expectedAansluitput) {
        assertAansluitputAllUpdatablePropertiesEquals(expectedAansluitput, getPersistedAansluitput(expectedAansluitput));
    }
}
