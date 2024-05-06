package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BemalingsgebiedAsserts.*;
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
import nl.ritense.demo.domain.Bemalingsgebied;
import nl.ritense.demo.repository.BemalingsgebiedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BemalingsgebiedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BemalingsgebiedResourceIT {

    private static final String DEFAULT_RIOLERINGSGEBIED = "AAAAAAAAAA";
    private static final String UPDATED_RIOLERINGSGEBIED = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bemalingsgebieds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BemalingsgebiedRepository bemalingsgebiedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBemalingsgebiedMockMvc;

    private Bemalingsgebied bemalingsgebied;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bemalingsgebied createEntity(EntityManager em) {
        Bemalingsgebied bemalingsgebied = new Bemalingsgebied().rioleringsgebied(DEFAULT_RIOLERINGSGEBIED);
        return bemalingsgebied;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bemalingsgebied createUpdatedEntity(EntityManager em) {
        Bemalingsgebied bemalingsgebied = new Bemalingsgebied().rioleringsgebied(UPDATED_RIOLERINGSGEBIED);
        return bemalingsgebied;
    }

    @BeforeEach
    public void initTest() {
        bemalingsgebied = createEntity(em);
    }

    @Test
    @Transactional
    void createBemalingsgebied() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bemalingsgebied
        var returnedBemalingsgebied = om.readValue(
            restBemalingsgebiedMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bemalingsgebied)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bemalingsgebied.class
        );

        // Validate the Bemalingsgebied in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBemalingsgebiedUpdatableFieldsEquals(returnedBemalingsgebied, getPersistedBemalingsgebied(returnedBemalingsgebied));
    }

    @Test
    @Transactional
    void createBemalingsgebiedWithExistingId() throws Exception {
        // Create the Bemalingsgebied with an existing ID
        bemalingsgebied.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBemalingsgebiedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bemalingsgebied)))
            .andExpect(status().isBadRequest());

        // Validate the Bemalingsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBemalingsgebieds() throws Exception {
        // Initialize the database
        bemalingsgebiedRepository.saveAndFlush(bemalingsgebied);

        // Get all the bemalingsgebiedList
        restBemalingsgebiedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bemalingsgebied.getId().intValue())))
            .andExpect(jsonPath("$.[*].rioleringsgebied").value(hasItem(DEFAULT_RIOLERINGSGEBIED)));
    }

    @Test
    @Transactional
    void getBemalingsgebied() throws Exception {
        // Initialize the database
        bemalingsgebiedRepository.saveAndFlush(bemalingsgebied);

        // Get the bemalingsgebied
        restBemalingsgebiedMockMvc
            .perform(get(ENTITY_API_URL_ID, bemalingsgebied.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bemalingsgebied.getId().intValue()))
            .andExpect(jsonPath("$.rioleringsgebied").value(DEFAULT_RIOLERINGSGEBIED));
    }

    @Test
    @Transactional
    void getNonExistingBemalingsgebied() throws Exception {
        // Get the bemalingsgebied
        restBemalingsgebiedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBemalingsgebied() throws Exception {
        // Initialize the database
        bemalingsgebiedRepository.saveAndFlush(bemalingsgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bemalingsgebied
        Bemalingsgebied updatedBemalingsgebied = bemalingsgebiedRepository.findById(bemalingsgebied.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBemalingsgebied are not directly saved in db
        em.detach(updatedBemalingsgebied);
        updatedBemalingsgebied.rioleringsgebied(UPDATED_RIOLERINGSGEBIED);

        restBemalingsgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBemalingsgebied.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBemalingsgebied))
            )
            .andExpect(status().isOk());

        // Validate the Bemalingsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBemalingsgebiedToMatchAllProperties(updatedBemalingsgebied);
    }

    @Test
    @Transactional
    void putNonExistingBemalingsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bemalingsgebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBemalingsgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bemalingsgebied.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bemalingsgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bemalingsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBemalingsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bemalingsgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBemalingsgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bemalingsgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bemalingsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBemalingsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bemalingsgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBemalingsgebiedMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bemalingsgebied)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bemalingsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBemalingsgebiedWithPatch() throws Exception {
        // Initialize the database
        bemalingsgebiedRepository.saveAndFlush(bemalingsgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bemalingsgebied using partial update
        Bemalingsgebied partialUpdatedBemalingsgebied = new Bemalingsgebied();
        partialUpdatedBemalingsgebied.setId(bemalingsgebied.getId());

        restBemalingsgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBemalingsgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBemalingsgebied))
            )
            .andExpect(status().isOk());

        // Validate the Bemalingsgebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBemalingsgebiedUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBemalingsgebied, bemalingsgebied),
            getPersistedBemalingsgebied(bemalingsgebied)
        );
    }

    @Test
    @Transactional
    void fullUpdateBemalingsgebiedWithPatch() throws Exception {
        // Initialize the database
        bemalingsgebiedRepository.saveAndFlush(bemalingsgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bemalingsgebied using partial update
        Bemalingsgebied partialUpdatedBemalingsgebied = new Bemalingsgebied();
        partialUpdatedBemalingsgebied.setId(bemalingsgebied.getId());

        partialUpdatedBemalingsgebied.rioleringsgebied(UPDATED_RIOLERINGSGEBIED);

        restBemalingsgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBemalingsgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBemalingsgebied))
            )
            .andExpect(status().isOk());

        // Validate the Bemalingsgebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBemalingsgebiedUpdatableFieldsEquals(
            partialUpdatedBemalingsgebied,
            getPersistedBemalingsgebied(partialUpdatedBemalingsgebied)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBemalingsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bemalingsgebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBemalingsgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bemalingsgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bemalingsgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bemalingsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBemalingsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bemalingsgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBemalingsgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bemalingsgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bemalingsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBemalingsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bemalingsgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBemalingsgebiedMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bemalingsgebied)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bemalingsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBemalingsgebied() throws Exception {
        // Initialize the database
        bemalingsgebiedRepository.saveAndFlush(bemalingsgebied);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bemalingsgebied
        restBemalingsgebiedMockMvc
            .perform(delete(ENTITY_API_URL_ID, bemalingsgebied.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bemalingsgebiedRepository.count();
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

    protected Bemalingsgebied getPersistedBemalingsgebied(Bemalingsgebied bemalingsgebied) {
        return bemalingsgebiedRepository.findById(bemalingsgebied.getId()).orElseThrow();
    }

    protected void assertPersistedBemalingsgebiedToMatchAllProperties(Bemalingsgebied expectedBemalingsgebied) {
        assertBemalingsgebiedAllPropertiesEquals(expectedBemalingsgebied, getPersistedBemalingsgebied(expectedBemalingsgebied));
    }

    protected void assertPersistedBemalingsgebiedToMatchUpdatableProperties(Bemalingsgebied expectedBemalingsgebied) {
        assertBemalingsgebiedAllUpdatablePropertiesEquals(expectedBemalingsgebied, getPersistedBemalingsgebied(expectedBemalingsgebied));
    }
}
