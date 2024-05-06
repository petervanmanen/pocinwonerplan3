package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BeperkingsgebiedAsserts.*;
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
import nl.ritense.demo.domain.Beperkingsgebied;
import nl.ritense.demo.repository.BeperkingsgebiedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BeperkingsgebiedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BeperkingsgebiedResourceIT {

    private static final String DEFAULT_GROEP = "AAAAAAAAAA";
    private static final String UPDATED_GROEP = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/beperkingsgebieds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BeperkingsgebiedRepository beperkingsgebiedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeperkingsgebiedMockMvc;

    private Beperkingsgebied beperkingsgebied;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beperkingsgebied createEntity(EntityManager em) {
        Beperkingsgebied beperkingsgebied = new Beperkingsgebied().groep(DEFAULT_GROEP).naam(DEFAULT_NAAM);
        return beperkingsgebied;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beperkingsgebied createUpdatedEntity(EntityManager em) {
        Beperkingsgebied beperkingsgebied = new Beperkingsgebied().groep(UPDATED_GROEP).naam(UPDATED_NAAM);
        return beperkingsgebied;
    }

    @BeforeEach
    public void initTest() {
        beperkingsgebied = createEntity(em);
    }

    @Test
    @Transactional
    void createBeperkingsgebied() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Beperkingsgebied
        var returnedBeperkingsgebied = om.readValue(
            restBeperkingsgebiedMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beperkingsgebied)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Beperkingsgebied.class
        );

        // Validate the Beperkingsgebied in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBeperkingsgebiedUpdatableFieldsEquals(returnedBeperkingsgebied, getPersistedBeperkingsgebied(returnedBeperkingsgebied));
    }

    @Test
    @Transactional
    void createBeperkingsgebiedWithExistingId() throws Exception {
        // Create the Beperkingsgebied with an existing ID
        beperkingsgebied.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeperkingsgebiedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beperkingsgebied)))
            .andExpect(status().isBadRequest());

        // Validate the Beperkingsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBeperkingsgebieds() throws Exception {
        // Initialize the database
        beperkingsgebiedRepository.saveAndFlush(beperkingsgebied);

        // Get all the beperkingsgebiedList
        restBeperkingsgebiedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beperkingsgebied.getId().intValue())))
            .andExpect(jsonPath("$.[*].groep").value(hasItem(DEFAULT_GROEP)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getBeperkingsgebied() throws Exception {
        // Initialize the database
        beperkingsgebiedRepository.saveAndFlush(beperkingsgebied);

        // Get the beperkingsgebied
        restBeperkingsgebiedMockMvc
            .perform(get(ENTITY_API_URL_ID, beperkingsgebied.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beperkingsgebied.getId().intValue()))
            .andExpect(jsonPath("$.groep").value(DEFAULT_GROEP))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingBeperkingsgebied() throws Exception {
        // Get the beperkingsgebied
        restBeperkingsgebiedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBeperkingsgebied() throws Exception {
        // Initialize the database
        beperkingsgebiedRepository.saveAndFlush(beperkingsgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beperkingsgebied
        Beperkingsgebied updatedBeperkingsgebied = beperkingsgebiedRepository.findById(beperkingsgebied.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBeperkingsgebied are not directly saved in db
        em.detach(updatedBeperkingsgebied);
        updatedBeperkingsgebied.groep(UPDATED_GROEP).naam(UPDATED_NAAM);

        restBeperkingsgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBeperkingsgebied.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBeperkingsgebied))
            )
            .andExpect(status().isOk());

        // Validate the Beperkingsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBeperkingsgebiedToMatchAllProperties(updatedBeperkingsgebied);
    }

    @Test
    @Transactional
    void putNonExistingBeperkingsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingsgebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeperkingsgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, beperkingsgebied.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beperkingsgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperkingsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBeperkingsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingsgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingsgebiedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beperkingsgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperkingsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBeperkingsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingsgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingsgebiedMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beperkingsgebied)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beperkingsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBeperkingsgebiedWithPatch() throws Exception {
        // Initialize the database
        beperkingsgebiedRepository.saveAndFlush(beperkingsgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beperkingsgebied using partial update
        Beperkingsgebied partialUpdatedBeperkingsgebied = new Beperkingsgebied();
        partialUpdatedBeperkingsgebied.setId(beperkingsgebied.getId());

        partialUpdatedBeperkingsgebied.groep(UPDATED_GROEP).naam(UPDATED_NAAM);

        restBeperkingsgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeperkingsgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeperkingsgebied))
            )
            .andExpect(status().isOk());

        // Validate the Beperkingsgebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeperkingsgebiedUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBeperkingsgebied, beperkingsgebied),
            getPersistedBeperkingsgebied(beperkingsgebied)
        );
    }

    @Test
    @Transactional
    void fullUpdateBeperkingsgebiedWithPatch() throws Exception {
        // Initialize the database
        beperkingsgebiedRepository.saveAndFlush(beperkingsgebied);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beperkingsgebied using partial update
        Beperkingsgebied partialUpdatedBeperkingsgebied = new Beperkingsgebied();
        partialUpdatedBeperkingsgebied.setId(beperkingsgebied.getId());

        partialUpdatedBeperkingsgebied.groep(UPDATED_GROEP).naam(UPDATED_NAAM);

        restBeperkingsgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeperkingsgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeperkingsgebied))
            )
            .andExpect(status().isOk());

        // Validate the Beperkingsgebied in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeperkingsgebiedUpdatableFieldsEquals(
            partialUpdatedBeperkingsgebied,
            getPersistedBeperkingsgebied(partialUpdatedBeperkingsgebied)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBeperkingsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingsgebied.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeperkingsgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, beperkingsgebied.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beperkingsgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperkingsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBeperkingsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingsgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingsgebiedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beperkingsgebied))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperkingsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBeperkingsgebied() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperkingsgebied.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingsgebiedMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(beperkingsgebied)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beperkingsgebied in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBeperkingsgebied() throws Exception {
        // Initialize the database
        beperkingsgebiedRepository.saveAndFlush(beperkingsgebied);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the beperkingsgebied
        restBeperkingsgebiedMockMvc
            .perform(delete(ENTITY_API_URL_ID, beperkingsgebied.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return beperkingsgebiedRepository.count();
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

    protected Beperkingsgebied getPersistedBeperkingsgebied(Beperkingsgebied beperkingsgebied) {
        return beperkingsgebiedRepository.findById(beperkingsgebied.getId()).orElseThrow();
    }

    protected void assertPersistedBeperkingsgebiedToMatchAllProperties(Beperkingsgebied expectedBeperkingsgebied) {
        assertBeperkingsgebiedAllPropertiesEquals(expectedBeperkingsgebied, getPersistedBeperkingsgebied(expectedBeperkingsgebied));
    }

    protected void assertPersistedBeperkingsgebiedToMatchUpdatableProperties(Beperkingsgebied expectedBeperkingsgebied) {
        assertBeperkingsgebiedAllUpdatablePropertiesEquals(
            expectedBeperkingsgebied,
            getPersistedBeperkingsgebied(expectedBeperkingsgebied)
        );
    }
}
