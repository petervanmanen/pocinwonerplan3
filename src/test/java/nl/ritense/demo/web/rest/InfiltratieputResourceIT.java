package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.InfiltratieputAsserts.*;
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
import nl.ritense.demo.domain.Infiltratieput;
import nl.ritense.demo.repository.InfiltratieputRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InfiltratieputResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InfiltratieputResourceIT {

    private static final String DEFAULT_POROSITEIT = "AAAAAAAAAA";
    private static final String UPDATED_POROSITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_RISICOGEBIED = "AAAAAAAAAA";
    private static final String UPDATED_RISICOGEBIED = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/infiltratieputs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InfiltratieputRepository infiltratieputRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInfiltratieputMockMvc;

    private Infiltratieput infiltratieput;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Infiltratieput createEntity(EntityManager em) {
        Infiltratieput infiltratieput = new Infiltratieput().porositeit(DEFAULT_POROSITEIT).risicogebied(DEFAULT_RISICOGEBIED);
        return infiltratieput;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Infiltratieput createUpdatedEntity(EntityManager em) {
        Infiltratieput infiltratieput = new Infiltratieput().porositeit(UPDATED_POROSITEIT).risicogebied(UPDATED_RISICOGEBIED);
        return infiltratieput;
    }

    @BeforeEach
    public void initTest() {
        infiltratieput = createEntity(em);
    }

    @Test
    @Transactional
    void createInfiltratieput() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Infiltratieput
        var returnedInfiltratieput = om.readValue(
            restInfiltratieputMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(infiltratieput)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Infiltratieput.class
        );

        // Validate the Infiltratieput in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInfiltratieputUpdatableFieldsEquals(returnedInfiltratieput, getPersistedInfiltratieput(returnedInfiltratieput));
    }

    @Test
    @Transactional
    void createInfiltratieputWithExistingId() throws Exception {
        // Create the Infiltratieput with an existing ID
        infiltratieput.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfiltratieputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(infiltratieput)))
            .andExpect(status().isBadRequest());

        // Validate the Infiltratieput in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInfiltratieputs() throws Exception {
        // Initialize the database
        infiltratieputRepository.saveAndFlush(infiltratieput);

        // Get all the infiltratieputList
        restInfiltratieputMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infiltratieput.getId().intValue())))
            .andExpect(jsonPath("$.[*].porositeit").value(hasItem(DEFAULT_POROSITEIT)))
            .andExpect(jsonPath("$.[*].risicogebied").value(hasItem(DEFAULT_RISICOGEBIED)));
    }

    @Test
    @Transactional
    void getInfiltratieput() throws Exception {
        // Initialize the database
        infiltratieputRepository.saveAndFlush(infiltratieput);

        // Get the infiltratieput
        restInfiltratieputMockMvc
            .perform(get(ENTITY_API_URL_ID, infiltratieput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(infiltratieput.getId().intValue()))
            .andExpect(jsonPath("$.porositeit").value(DEFAULT_POROSITEIT))
            .andExpect(jsonPath("$.risicogebied").value(DEFAULT_RISICOGEBIED));
    }

    @Test
    @Transactional
    void getNonExistingInfiltratieput() throws Exception {
        // Get the infiltratieput
        restInfiltratieputMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInfiltratieput() throws Exception {
        // Initialize the database
        infiltratieputRepository.saveAndFlush(infiltratieput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the infiltratieput
        Infiltratieput updatedInfiltratieput = infiltratieputRepository.findById(infiltratieput.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInfiltratieput are not directly saved in db
        em.detach(updatedInfiltratieput);
        updatedInfiltratieput.porositeit(UPDATED_POROSITEIT).risicogebied(UPDATED_RISICOGEBIED);

        restInfiltratieputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInfiltratieput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInfiltratieput))
            )
            .andExpect(status().isOk());

        // Validate the Infiltratieput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInfiltratieputToMatchAllProperties(updatedInfiltratieput);
    }

    @Test
    @Transactional
    void putNonExistingInfiltratieput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        infiltratieput.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfiltratieputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, infiltratieput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(infiltratieput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Infiltratieput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInfiltratieput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        infiltratieput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfiltratieputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(infiltratieput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Infiltratieput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInfiltratieput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        infiltratieput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfiltratieputMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(infiltratieput)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Infiltratieput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInfiltratieputWithPatch() throws Exception {
        // Initialize the database
        infiltratieputRepository.saveAndFlush(infiltratieput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the infiltratieput using partial update
        Infiltratieput partialUpdatedInfiltratieput = new Infiltratieput();
        partialUpdatedInfiltratieput.setId(infiltratieput.getId());

        restInfiltratieputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInfiltratieput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInfiltratieput))
            )
            .andExpect(status().isOk());

        // Validate the Infiltratieput in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInfiltratieputUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInfiltratieput, infiltratieput),
            getPersistedInfiltratieput(infiltratieput)
        );
    }

    @Test
    @Transactional
    void fullUpdateInfiltratieputWithPatch() throws Exception {
        // Initialize the database
        infiltratieputRepository.saveAndFlush(infiltratieput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the infiltratieput using partial update
        Infiltratieput partialUpdatedInfiltratieput = new Infiltratieput();
        partialUpdatedInfiltratieput.setId(infiltratieput.getId());

        partialUpdatedInfiltratieput.porositeit(UPDATED_POROSITEIT).risicogebied(UPDATED_RISICOGEBIED);

        restInfiltratieputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInfiltratieput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInfiltratieput))
            )
            .andExpect(status().isOk());

        // Validate the Infiltratieput in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInfiltratieputUpdatableFieldsEquals(partialUpdatedInfiltratieput, getPersistedInfiltratieput(partialUpdatedInfiltratieput));
    }

    @Test
    @Transactional
    void patchNonExistingInfiltratieput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        infiltratieput.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfiltratieputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, infiltratieput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(infiltratieput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Infiltratieput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInfiltratieput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        infiltratieput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfiltratieputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(infiltratieput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Infiltratieput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInfiltratieput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        infiltratieput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfiltratieputMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(infiltratieput)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Infiltratieput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInfiltratieput() throws Exception {
        // Initialize the database
        infiltratieputRepository.saveAndFlush(infiltratieput);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the infiltratieput
        restInfiltratieputMockMvc
            .perform(delete(ENTITY_API_URL_ID, infiltratieput.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return infiltratieputRepository.count();
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

    protected Infiltratieput getPersistedInfiltratieput(Infiltratieput infiltratieput) {
        return infiltratieputRepository.findById(infiltratieput.getId()).orElseThrow();
    }

    protected void assertPersistedInfiltratieputToMatchAllProperties(Infiltratieput expectedInfiltratieput) {
        assertInfiltratieputAllPropertiesEquals(expectedInfiltratieput, getPersistedInfiltratieput(expectedInfiltratieput));
    }

    protected void assertPersistedInfiltratieputToMatchUpdatableProperties(Infiltratieput expectedInfiltratieput) {
        assertInfiltratieputAllUpdatablePropertiesEquals(expectedInfiltratieput, getPersistedInfiltratieput(expectedInfiltratieput));
    }
}
