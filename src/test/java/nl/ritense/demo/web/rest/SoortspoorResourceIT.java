package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SoortspoorAsserts.*;
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
import nl.ritense.demo.domain.Soortspoor;
import nl.ritense.demo.repository.SoortspoorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SoortspoorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SoortspoorResourceIT {

    private static final String DEFAULT_FUNCTIESPOOR = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTIESPOOR = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIEPLUSBRPOPULATIE = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEPLUSBRPOPULATIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/soortspoors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SoortspoorRepository soortspoorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoortspoorMockMvc;

    private Soortspoor soortspoor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soortspoor createEntity(EntityManager em) {
        Soortspoor soortspoor = new Soortspoor()
            .functiespoor(DEFAULT_FUNCTIESPOOR)
            .indicatieplusbrpopulatie(DEFAULT_INDICATIEPLUSBRPOPULATIE);
        return soortspoor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soortspoor createUpdatedEntity(EntityManager em) {
        Soortspoor soortspoor = new Soortspoor()
            .functiespoor(UPDATED_FUNCTIESPOOR)
            .indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE);
        return soortspoor;
    }

    @BeforeEach
    public void initTest() {
        soortspoor = createEntity(em);
    }

    @Test
    @Transactional
    void createSoortspoor() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Soortspoor
        var returnedSoortspoor = om.readValue(
            restSoortspoorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortspoor)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Soortspoor.class
        );

        // Validate the Soortspoor in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSoortspoorUpdatableFieldsEquals(returnedSoortspoor, getPersistedSoortspoor(returnedSoortspoor));
    }

    @Test
    @Transactional
    void createSoortspoorWithExistingId() throws Exception {
        // Create the Soortspoor with an existing ID
        soortspoor.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoortspoorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortspoor)))
            .andExpect(status().isBadRequest());

        // Validate the Soortspoor in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSoortspoors() throws Exception {
        // Initialize the database
        soortspoorRepository.saveAndFlush(soortspoor);

        // Get all the soortspoorList
        restSoortspoorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soortspoor.getId().intValue())))
            .andExpect(jsonPath("$.[*].functiespoor").value(hasItem(DEFAULT_FUNCTIESPOOR)))
            .andExpect(jsonPath("$.[*].indicatieplusbrpopulatie").value(hasItem(DEFAULT_INDICATIEPLUSBRPOPULATIE)));
    }

    @Test
    @Transactional
    void getSoortspoor() throws Exception {
        // Initialize the database
        soortspoorRepository.saveAndFlush(soortspoor);

        // Get the soortspoor
        restSoortspoorMockMvc
            .perform(get(ENTITY_API_URL_ID, soortspoor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(soortspoor.getId().intValue()))
            .andExpect(jsonPath("$.functiespoor").value(DEFAULT_FUNCTIESPOOR))
            .andExpect(jsonPath("$.indicatieplusbrpopulatie").value(DEFAULT_INDICATIEPLUSBRPOPULATIE));
    }

    @Test
    @Transactional
    void getNonExistingSoortspoor() throws Exception {
        // Get the soortspoor
        restSoortspoorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSoortspoor() throws Exception {
        // Initialize the database
        soortspoorRepository.saveAndFlush(soortspoor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortspoor
        Soortspoor updatedSoortspoor = soortspoorRepository.findById(soortspoor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSoortspoor are not directly saved in db
        em.detach(updatedSoortspoor);
        updatedSoortspoor.functiespoor(UPDATED_FUNCTIESPOOR).indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE);

        restSoortspoorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSoortspoor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSoortspoor))
            )
            .andExpect(status().isOk());

        // Validate the Soortspoor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSoortspoorToMatchAllProperties(updatedSoortspoor);
    }

    @Test
    @Transactional
    void putNonExistingSoortspoor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortspoor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoortspoorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soortspoor.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortspoor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortspoor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSoortspoor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortspoor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortspoorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(soortspoor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortspoor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSoortspoor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortspoor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortspoorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortspoor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soortspoor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSoortspoorWithPatch() throws Exception {
        // Initialize the database
        soortspoorRepository.saveAndFlush(soortspoor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortspoor using partial update
        Soortspoor partialUpdatedSoortspoor = new Soortspoor();
        partialUpdatedSoortspoor.setId(soortspoor.getId());

        restSoortspoorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoortspoor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSoortspoor))
            )
            .andExpect(status().isOk());

        // Validate the Soortspoor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSoortspoorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSoortspoor, soortspoor),
            getPersistedSoortspoor(soortspoor)
        );
    }

    @Test
    @Transactional
    void fullUpdateSoortspoorWithPatch() throws Exception {
        // Initialize the database
        soortspoorRepository.saveAndFlush(soortspoor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortspoor using partial update
        Soortspoor partialUpdatedSoortspoor = new Soortspoor();
        partialUpdatedSoortspoor.setId(soortspoor.getId());

        partialUpdatedSoortspoor.functiespoor(UPDATED_FUNCTIESPOOR).indicatieplusbrpopulatie(UPDATED_INDICATIEPLUSBRPOPULATIE);

        restSoortspoorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoortspoor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSoortspoor))
            )
            .andExpect(status().isOk());

        // Validate the Soortspoor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSoortspoorUpdatableFieldsEquals(partialUpdatedSoortspoor, getPersistedSoortspoor(partialUpdatedSoortspoor));
    }

    @Test
    @Transactional
    void patchNonExistingSoortspoor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortspoor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoortspoorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, soortspoor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(soortspoor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortspoor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSoortspoor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortspoor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortspoorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(soortspoor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortspoor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSoortspoor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortspoor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortspoorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(soortspoor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soortspoor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSoortspoor() throws Exception {
        // Initialize the database
        soortspoorRepository.saveAndFlush(soortspoor);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the soortspoor
        restSoortspoorMockMvc
            .perform(delete(ENTITY_API_URL_ID, soortspoor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return soortspoorRepository.count();
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

    protected Soortspoor getPersistedSoortspoor(Soortspoor soortspoor) {
        return soortspoorRepository.findById(soortspoor.getId()).orElseThrow();
    }

    protected void assertPersistedSoortspoorToMatchAllProperties(Soortspoor expectedSoortspoor) {
        assertSoortspoorAllPropertiesEquals(expectedSoortspoor, getPersistedSoortspoor(expectedSoortspoor));
    }

    protected void assertPersistedSoortspoorToMatchUpdatableProperties(Soortspoor expectedSoortspoor) {
        assertSoortspoorAllUpdatablePropertiesEquals(expectedSoortspoor, getPersistedSoortspoor(expectedSoortspoor));
    }
}
