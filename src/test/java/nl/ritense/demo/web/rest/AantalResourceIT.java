package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AantalAsserts.*;
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
import nl.ritense.demo.domain.Aantal;
import nl.ritense.demo.repository.AantalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AantalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AantalResourceIT {

    private static final String DEFAULT_AANTAL = "AAAAAAAAAA";
    private static final String UPDATED_AANTAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aantals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AantalRepository aantalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAantalMockMvc;

    private Aantal aantal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aantal createEntity(EntityManager em) {
        Aantal aantal = new Aantal().aantal(DEFAULT_AANTAL);
        return aantal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aantal createUpdatedEntity(EntityManager em) {
        Aantal aantal = new Aantal().aantal(UPDATED_AANTAL);
        return aantal;
    }

    @BeforeEach
    public void initTest() {
        aantal = createEntity(em);
    }

    @Test
    @Transactional
    void createAantal() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aantal
        var returnedAantal = om.readValue(
            restAantalMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aantal)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aantal.class
        );

        // Validate the Aantal in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAantalUpdatableFieldsEquals(returnedAantal, getPersistedAantal(returnedAantal));
    }

    @Test
    @Transactional
    void createAantalWithExistingId() throws Exception {
        // Create the Aantal with an existing ID
        aantal.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAantalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aantal)))
            .andExpect(status().isBadRequest());

        // Validate the Aantal in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAantals() throws Exception {
        // Initialize the database
        aantalRepository.saveAndFlush(aantal);

        // Get all the aantalList
        restAantalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aantal.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantal").value(hasItem(DEFAULT_AANTAL)));
    }

    @Test
    @Transactional
    void getAantal() throws Exception {
        // Initialize the database
        aantalRepository.saveAndFlush(aantal);

        // Get the aantal
        restAantalMockMvc
            .perform(get(ENTITY_API_URL_ID, aantal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aantal.getId().intValue()))
            .andExpect(jsonPath("$.aantal").value(DEFAULT_AANTAL));
    }

    @Test
    @Transactional
    void getNonExistingAantal() throws Exception {
        // Get the aantal
        restAantalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAantal() throws Exception {
        // Initialize the database
        aantalRepository.saveAndFlush(aantal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aantal
        Aantal updatedAantal = aantalRepository.findById(aantal.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAantal are not directly saved in db
        em.detach(updatedAantal);
        updatedAantal.aantal(UPDATED_AANTAL);

        restAantalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAantal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAantal))
            )
            .andExpect(status().isOk());

        // Validate the Aantal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAantalToMatchAllProperties(updatedAantal);
    }

    @Test
    @Transactional
    void putNonExistingAantal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aantal.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAantalMockMvc
            .perform(put(ENTITY_API_URL_ID, aantal.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aantal)))
            .andExpect(status().isBadRequest());

        // Validate the Aantal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAantal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aantal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAantalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aantal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aantal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAantal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aantal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAantalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aantal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aantal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAantalWithPatch() throws Exception {
        // Initialize the database
        aantalRepository.saveAndFlush(aantal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aantal using partial update
        Aantal partialUpdatedAantal = new Aantal();
        partialUpdatedAantal.setId(aantal.getId());

        restAantalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAantal.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAantal))
            )
            .andExpect(status().isOk());

        // Validate the Aantal in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAantalUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAantal, aantal), getPersistedAantal(aantal));
    }

    @Test
    @Transactional
    void fullUpdateAantalWithPatch() throws Exception {
        // Initialize the database
        aantalRepository.saveAndFlush(aantal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aantal using partial update
        Aantal partialUpdatedAantal = new Aantal();
        partialUpdatedAantal.setId(aantal.getId());

        partialUpdatedAantal.aantal(UPDATED_AANTAL);

        restAantalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAantal.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAantal))
            )
            .andExpect(status().isOk());

        // Validate the Aantal in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAantalUpdatableFieldsEquals(partialUpdatedAantal, getPersistedAantal(partialUpdatedAantal));
    }

    @Test
    @Transactional
    void patchNonExistingAantal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aantal.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAantalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aantal.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aantal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aantal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAantal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aantal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAantalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aantal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aantal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAantal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aantal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAantalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aantal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aantal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAantal() throws Exception {
        // Initialize the database
        aantalRepository.saveAndFlush(aantal);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aantal
        restAantalMockMvc
            .perform(delete(ENTITY_API_URL_ID, aantal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aantalRepository.count();
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

    protected Aantal getPersistedAantal(Aantal aantal) {
        return aantalRepository.findById(aantal.getId()).orElseThrow();
    }

    protected void assertPersistedAantalToMatchAllProperties(Aantal expectedAantal) {
        assertAantalAllPropertiesEquals(expectedAantal, getPersistedAantal(expectedAantal));
    }

    protected void assertPersistedAantalToMatchUpdatableProperties(Aantal expectedAantal) {
        assertAantalAllUpdatablePropertiesEquals(expectedAantal, getPersistedAantal(expectedAantal));
    }
}
