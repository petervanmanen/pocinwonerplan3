package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.UitstroomredenAsserts.*;
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
import nl.ritense.demo.domain.Uitstroomreden;
import nl.ritense.demo.repository.UitstroomredenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UitstroomredenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UitstroomredenResourceIT {

    private static final String DEFAULT_DATUM = "AAAAAAAAAA";
    private static final String UPDATED_DATUM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/uitstroomredens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UitstroomredenRepository uitstroomredenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUitstroomredenMockMvc;

    private Uitstroomreden uitstroomreden;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitstroomreden createEntity(EntityManager em) {
        Uitstroomreden uitstroomreden = new Uitstroomreden().datum(DEFAULT_DATUM).omschrijving(DEFAULT_OMSCHRIJVING);
        return uitstroomreden;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitstroomreden createUpdatedEntity(EntityManager em) {
        Uitstroomreden uitstroomreden = new Uitstroomreden().datum(UPDATED_DATUM).omschrijving(UPDATED_OMSCHRIJVING);
        return uitstroomreden;
    }

    @BeforeEach
    public void initTest() {
        uitstroomreden = createEntity(em);
    }

    @Test
    @Transactional
    void createUitstroomreden() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Uitstroomreden
        var returnedUitstroomreden = om.readValue(
            restUitstroomredenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitstroomreden)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Uitstroomreden.class
        );

        // Validate the Uitstroomreden in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertUitstroomredenUpdatableFieldsEquals(returnedUitstroomreden, getPersistedUitstroomreden(returnedUitstroomreden));
    }

    @Test
    @Transactional
    void createUitstroomredenWithExistingId() throws Exception {
        // Create the Uitstroomreden with an existing ID
        uitstroomreden.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUitstroomredenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitstroomreden)))
            .andExpect(status().isBadRequest());

        // Validate the Uitstroomreden in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUitstroomredens() throws Exception {
        // Initialize the database
        uitstroomredenRepository.saveAndFlush(uitstroomreden);

        // Get all the uitstroomredenList
        restUitstroomredenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uitstroomreden.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getUitstroomreden() throws Exception {
        // Initialize the database
        uitstroomredenRepository.saveAndFlush(uitstroomreden);

        // Get the uitstroomreden
        restUitstroomredenMockMvc
            .perform(get(ENTITY_API_URL_ID, uitstroomreden.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uitstroomreden.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingUitstroomreden() throws Exception {
        // Get the uitstroomreden
        restUitstroomredenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUitstroomreden() throws Exception {
        // Initialize the database
        uitstroomredenRepository.saveAndFlush(uitstroomreden);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitstroomreden
        Uitstroomreden updatedUitstroomreden = uitstroomredenRepository.findById(uitstroomreden.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUitstroomreden are not directly saved in db
        em.detach(updatedUitstroomreden);
        updatedUitstroomreden.datum(UPDATED_DATUM).omschrijving(UPDATED_OMSCHRIJVING);

        restUitstroomredenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUitstroomreden.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedUitstroomreden))
            )
            .andExpect(status().isOk());

        // Validate the Uitstroomreden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUitstroomredenToMatchAllProperties(updatedUitstroomreden);
    }

    @Test
    @Transactional
    void putNonExistingUitstroomreden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitstroomreden.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUitstroomredenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, uitstroomreden.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uitstroomreden))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitstroomreden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUitstroomreden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitstroomreden.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitstroomredenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uitstroomreden))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitstroomreden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUitstroomreden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitstroomreden.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitstroomredenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitstroomreden)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uitstroomreden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUitstroomredenWithPatch() throws Exception {
        // Initialize the database
        uitstroomredenRepository.saveAndFlush(uitstroomreden);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitstroomreden using partial update
        Uitstroomreden partialUpdatedUitstroomreden = new Uitstroomreden();
        partialUpdatedUitstroomreden.setId(uitstroomreden.getId());

        restUitstroomredenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUitstroomreden.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUitstroomreden))
            )
            .andExpect(status().isOk());

        // Validate the Uitstroomreden in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUitstroomredenUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUitstroomreden, uitstroomreden),
            getPersistedUitstroomreden(uitstroomreden)
        );
    }

    @Test
    @Transactional
    void fullUpdateUitstroomredenWithPatch() throws Exception {
        // Initialize the database
        uitstroomredenRepository.saveAndFlush(uitstroomreden);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitstroomreden using partial update
        Uitstroomreden partialUpdatedUitstroomreden = new Uitstroomreden();
        partialUpdatedUitstroomreden.setId(uitstroomreden.getId());

        partialUpdatedUitstroomreden.datum(UPDATED_DATUM).omschrijving(UPDATED_OMSCHRIJVING);

        restUitstroomredenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUitstroomreden.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUitstroomreden))
            )
            .andExpect(status().isOk());

        // Validate the Uitstroomreden in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUitstroomredenUpdatableFieldsEquals(partialUpdatedUitstroomreden, getPersistedUitstroomreden(partialUpdatedUitstroomreden));
    }

    @Test
    @Transactional
    void patchNonExistingUitstroomreden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitstroomreden.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUitstroomredenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, uitstroomreden.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uitstroomreden))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitstroomreden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUitstroomreden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitstroomreden.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitstroomredenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uitstroomreden))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitstroomreden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUitstroomreden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitstroomreden.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitstroomredenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(uitstroomreden)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uitstroomreden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUitstroomreden() throws Exception {
        // Initialize the database
        uitstroomredenRepository.saveAndFlush(uitstroomreden);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the uitstroomreden
        restUitstroomredenMockMvc
            .perform(delete(ENTITY_API_URL_ID, uitstroomreden.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return uitstroomredenRepository.count();
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

    protected Uitstroomreden getPersistedUitstroomreden(Uitstroomreden uitstroomreden) {
        return uitstroomredenRepository.findById(uitstroomreden.getId()).orElseThrow();
    }

    protected void assertPersistedUitstroomredenToMatchAllProperties(Uitstroomreden expectedUitstroomreden) {
        assertUitstroomredenAllPropertiesEquals(expectedUitstroomreden, getPersistedUitstroomreden(expectedUitstroomreden));
    }

    protected void assertPersistedUitstroomredenToMatchUpdatableProperties(Uitstroomreden expectedUitstroomreden) {
        assertUitstroomredenAllUpdatablePropertiesEquals(expectedUitstroomreden, getPersistedUitstroomreden(expectedUitstroomreden));
    }
}
