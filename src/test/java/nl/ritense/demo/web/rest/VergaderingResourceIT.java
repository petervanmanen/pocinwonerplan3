package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VergaderingAsserts.*;
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
import nl.ritense.demo.domain.Vergadering;
import nl.ritense.demo.repository.VergaderingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VergaderingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VergaderingResourceIT {

    private static final String DEFAULT_EINDTIJD = "AAAAAAAAAA";
    private static final String UPDATED_EINDTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIE = "BBBBBBBBBB";

    private static final String DEFAULT_STARTTIJD = "AAAAAAAAAA";
    private static final String UPDATED_STARTTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_TITEL = "AAAAAAAAAA";
    private static final String UPDATED_TITEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vergaderings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VergaderingRepository vergaderingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVergaderingMockMvc;

    private Vergadering vergadering;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vergadering createEntity(EntityManager em) {
        Vergadering vergadering = new Vergadering()
            .eindtijd(DEFAULT_EINDTIJD)
            .locatie(DEFAULT_LOCATIE)
            .starttijd(DEFAULT_STARTTIJD)
            .titel(DEFAULT_TITEL);
        return vergadering;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vergadering createUpdatedEntity(EntityManager em) {
        Vergadering vergadering = new Vergadering()
            .eindtijd(UPDATED_EINDTIJD)
            .locatie(UPDATED_LOCATIE)
            .starttijd(UPDATED_STARTTIJD)
            .titel(UPDATED_TITEL);
        return vergadering;
    }

    @BeforeEach
    public void initTest() {
        vergadering = createEntity(em);
    }

    @Test
    @Transactional
    void createVergadering() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vergadering
        var returnedVergadering = om.readValue(
            restVergaderingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vergadering)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vergadering.class
        );

        // Validate the Vergadering in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVergaderingUpdatableFieldsEquals(returnedVergadering, getPersistedVergadering(returnedVergadering));
    }

    @Test
    @Transactional
    void createVergaderingWithExistingId() throws Exception {
        // Create the Vergadering with an existing ID
        vergadering.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVergaderingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vergadering)))
            .andExpect(status().isBadRequest());

        // Validate the Vergadering in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVergaderings() throws Exception {
        // Initialize the database
        vergaderingRepository.saveAndFlush(vergadering);

        // Get all the vergaderingList
        restVergaderingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vergadering.getId().intValue())))
            .andExpect(jsonPath("$.[*].eindtijd").value(hasItem(DEFAULT_EINDTIJD)))
            .andExpect(jsonPath("$.[*].locatie").value(hasItem(DEFAULT_LOCATIE)))
            .andExpect(jsonPath("$.[*].starttijd").value(hasItem(DEFAULT_STARTTIJD)))
            .andExpect(jsonPath("$.[*].titel").value(hasItem(DEFAULT_TITEL)));
    }

    @Test
    @Transactional
    void getVergadering() throws Exception {
        // Initialize the database
        vergaderingRepository.saveAndFlush(vergadering);

        // Get the vergadering
        restVergaderingMockMvc
            .perform(get(ENTITY_API_URL_ID, vergadering.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vergadering.getId().intValue()))
            .andExpect(jsonPath("$.eindtijd").value(DEFAULT_EINDTIJD))
            .andExpect(jsonPath("$.locatie").value(DEFAULT_LOCATIE))
            .andExpect(jsonPath("$.starttijd").value(DEFAULT_STARTTIJD))
            .andExpect(jsonPath("$.titel").value(DEFAULT_TITEL));
    }

    @Test
    @Transactional
    void getNonExistingVergadering() throws Exception {
        // Get the vergadering
        restVergaderingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVergadering() throws Exception {
        // Initialize the database
        vergaderingRepository.saveAndFlush(vergadering);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vergadering
        Vergadering updatedVergadering = vergaderingRepository.findById(vergadering.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVergadering are not directly saved in db
        em.detach(updatedVergadering);
        updatedVergadering.eindtijd(UPDATED_EINDTIJD).locatie(UPDATED_LOCATIE).starttijd(UPDATED_STARTTIJD).titel(UPDATED_TITEL);

        restVergaderingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVergadering.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVergadering))
            )
            .andExpect(status().isOk());

        // Validate the Vergadering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVergaderingToMatchAllProperties(updatedVergadering);
    }

    @Test
    @Transactional
    void putNonExistingVergadering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vergadering.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVergaderingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vergadering.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vergadering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vergadering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVergadering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vergadering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVergaderingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vergadering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vergadering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVergadering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vergadering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVergaderingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vergadering)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vergadering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVergaderingWithPatch() throws Exception {
        // Initialize the database
        vergaderingRepository.saveAndFlush(vergadering);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vergadering using partial update
        Vergadering partialUpdatedVergadering = new Vergadering();
        partialUpdatedVergadering.setId(vergadering.getId());

        restVergaderingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVergadering.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVergadering))
            )
            .andExpect(status().isOk());

        // Validate the Vergadering in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVergaderingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVergadering, vergadering),
            getPersistedVergadering(vergadering)
        );
    }

    @Test
    @Transactional
    void fullUpdateVergaderingWithPatch() throws Exception {
        // Initialize the database
        vergaderingRepository.saveAndFlush(vergadering);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vergadering using partial update
        Vergadering partialUpdatedVergadering = new Vergadering();
        partialUpdatedVergadering.setId(vergadering.getId());

        partialUpdatedVergadering.eindtijd(UPDATED_EINDTIJD).locatie(UPDATED_LOCATIE).starttijd(UPDATED_STARTTIJD).titel(UPDATED_TITEL);

        restVergaderingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVergadering.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVergadering))
            )
            .andExpect(status().isOk());

        // Validate the Vergadering in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVergaderingUpdatableFieldsEquals(partialUpdatedVergadering, getPersistedVergadering(partialUpdatedVergadering));
    }

    @Test
    @Transactional
    void patchNonExistingVergadering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vergadering.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVergaderingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vergadering.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vergadering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vergadering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVergadering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vergadering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVergaderingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vergadering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vergadering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVergadering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vergadering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVergaderingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vergadering)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vergadering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVergadering() throws Exception {
        // Initialize the database
        vergaderingRepository.saveAndFlush(vergadering);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vergadering
        restVergaderingMockMvc
            .perform(delete(ENTITY_API_URL_ID, vergadering.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vergaderingRepository.count();
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

    protected Vergadering getPersistedVergadering(Vergadering vergadering) {
        return vergaderingRepository.findById(vergadering.getId()).orElseThrow();
    }

    protected void assertPersistedVergaderingToMatchAllProperties(Vergadering expectedVergadering) {
        assertVergaderingAllPropertiesEquals(expectedVergadering, getPersistedVergadering(expectedVergadering));
    }

    protected void assertPersistedVergaderingToMatchUpdatableProperties(Vergadering expectedVergadering) {
        assertVergaderingAllUpdatablePropertiesEquals(expectedVergadering, getPersistedVergadering(expectedVergadering));
    }
}
