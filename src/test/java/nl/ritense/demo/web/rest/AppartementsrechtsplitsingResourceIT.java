package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AppartementsrechtsplitsingAsserts.*;
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
import nl.ritense.demo.domain.Appartementsrechtsplitsing;
import nl.ritense.demo.repository.AppartementsrechtsplitsingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AppartementsrechtsplitsingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppartementsrechtsplitsingResourceIT {

    private static final String DEFAULT_DDENTIFICATIEAPPARTEMENTSRECHTSPLITSING = "AAAAAAAAAA";
    private static final String UPDATED_DDENTIFICATIEAPPARTEMENTSRECHTSPLITSING = "BBBBBBBBBB";

    private static final String DEFAULT_TYPESPLITSING = "AAAAAAAAAA";
    private static final String UPDATED_TYPESPLITSING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/appartementsrechtsplitsings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AppartementsrechtsplitsingRepository appartementsrechtsplitsingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppartementsrechtsplitsingMockMvc;

    private Appartementsrechtsplitsing appartementsrechtsplitsing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appartementsrechtsplitsing createEntity(EntityManager em) {
        Appartementsrechtsplitsing appartementsrechtsplitsing = new Appartementsrechtsplitsing()
            .ddentificatieappartementsrechtsplitsing(DEFAULT_DDENTIFICATIEAPPARTEMENTSRECHTSPLITSING)
            .typesplitsing(DEFAULT_TYPESPLITSING);
        return appartementsrechtsplitsing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appartementsrechtsplitsing createUpdatedEntity(EntityManager em) {
        Appartementsrechtsplitsing appartementsrechtsplitsing = new Appartementsrechtsplitsing()
            .ddentificatieappartementsrechtsplitsing(UPDATED_DDENTIFICATIEAPPARTEMENTSRECHTSPLITSING)
            .typesplitsing(UPDATED_TYPESPLITSING);
        return appartementsrechtsplitsing;
    }

    @BeforeEach
    public void initTest() {
        appartementsrechtsplitsing = createEntity(em);
    }

    @Test
    @Transactional
    void createAppartementsrechtsplitsing() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Appartementsrechtsplitsing
        var returnedAppartementsrechtsplitsing = om.readValue(
            restAppartementsrechtsplitsingMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appartementsrechtsplitsing))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Appartementsrechtsplitsing.class
        );

        // Validate the Appartementsrechtsplitsing in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAppartementsrechtsplitsingUpdatableFieldsEquals(
            returnedAppartementsrechtsplitsing,
            getPersistedAppartementsrechtsplitsing(returnedAppartementsrechtsplitsing)
        );
    }

    @Test
    @Transactional
    void createAppartementsrechtsplitsingWithExistingId() throws Exception {
        // Create the Appartementsrechtsplitsing with an existing ID
        appartementsrechtsplitsing.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppartementsrechtsplitsingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appartementsrechtsplitsing)))
            .andExpect(status().isBadRequest());

        // Validate the Appartementsrechtsplitsing in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppartementsrechtsplitsings() throws Exception {
        // Initialize the database
        appartementsrechtsplitsingRepository.saveAndFlush(appartementsrechtsplitsing);

        // Get all the appartementsrechtsplitsingList
        restAppartementsrechtsplitsingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appartementsrechtsplitsing.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].ddentificatieappartementsrechtsplitsing").value(hasItem(DEFAULT_DDENTIFICATIEAPPARTEMENTSRECHTSPLITSING))
            )
            .andExpect(jsonPath("$.[*].typesplitsing").value(hasItem(DEFAULT_TYPESPLITSING)));
    }

    @Test
    @Transactional
    void getAppartementsrechtsplitsing() throws Exception {
        // Initialize the database
        appartementsrechtsplitsingRepository.saveAndFlush(appartementsrechtsplitsing);

        // Get the appartementsrechtsplitsing
        restAppartementsrechtsplitsingMockMvc
            .perform(get(ENTITY_API_URL_ID, appartementsrechtsplitsing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appartementsrechtsplitsing.getId().intValue()))
            .andExpect(jsonPath("$.ddentificatieappartementsrechtsplitsing").value(DEFAULT_DDENTIFICATIEAPPARTEMENTSRECHTSPLITSING))
            .andExpect(jsonPath("$.typesplitsing").value(DEFAULT_TYPESPLITSING));
    }

    @Test
    @Transactional
    void getNonExistingAppartementsrechtsplitsing() throws Exception {
        // Get the appartementsrechtsplitsing
        restAppartementsrechtsplitsingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppartementsrechtsplitsing() throws Exception {
        // Initialize the database
        appartementsrechtsplitsingRepository.saveAndFlush(appartementsrechtsplitsing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appartementsrechtsplitsing
        Appartementsrechtsplitsing updatedAppartementsrechtsplitsing = appartementsrechtsplitsingRepository
            .findById(appartementsrechtsplitsing.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAppartementsrechtsplitsing are not directly saved in db
        em.detach(updatedAppartementsrechtsplitsing);
        updatedAppartementsrechtsplitsing
            .ddentificatieappartementsrechtsplitsing(UPDATED_DDENTIFICATIEAPPARTEMENTSRECHTSPLITSING)
            .typesplitsing(UPDATED_TYPESPLITSING);

        restAppartementsrechtsplitsingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAppartementsrechtsplitsing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAppartementsrechtsplitsing))
            )
            .andExpect(status().isOk());

        // Validate the Appartementsrechtsplitsing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAppartementsrechtsplitsingToMatchAllProperties(updatedAppartementsrechtsplitsing);
    }

    @Test
    @Transactional
    void putNonExistingAppartementsrechtsplitsing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appartementsrechtsplitsing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppartementsrechtsplitsingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appartementsrechtsplitsing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appartementsrechtsplitsing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appartementsrechtsplitsing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppartementsrechtsplitsing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appartementsrechtsplitsing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppartementsrechtsplitsingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appartementsrechtsplitsing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appartementsrechtsplitsing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppartementsrechtsplitsing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appartementsrechtsplitsing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppartementsrechtsplitsingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appartementsrechtsplitsing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Appartementsrechtsplitsing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppartementsrechtsplitsingWithPatch() throws Exception {
        // Initialize the database
        appartementsrechtsplitsingRepository.saveAndFlush(appartementsrechtsplitsing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appartementsrechtsplitsing using partial update
        Appartementsrechtsplitsing partialUpdatedAppartementsrechtsplitsing = new Appartementsrechtsplitsing();
        partialUpdatedAppartementsrechtsplitsing.setId(appartementsrechtsplitsing.getId());

        partialUpdatedAppartementsrechtsplitsing.typesplitsing(UPDATED_TYPESPLITSING);

        restAppartementsrechtsplitsingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppartementsrechtsplitsing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppartementsrechtsplitsing))
            )
            .andExpect(status().isOk());

        // Validate the Appartementsrechtsplitsing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppartementsrechtsplitsingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAppartementsrechtsplitsing, appartementsrechtsplitsing),
            getPersistedAppartementsrechtsplitsing(appartementsrechtsplitsing)
        );
    }

    @Test
    @Transactional
    void fullUpdateAppartementsrechtsplitsingWithPatch() throws Exception {
        // Initialize the database
        appartementsrechtsplitsingRepository.saveAndFlush(appartementsrechtsplitsing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appartementsrechtsplitsing using partial update
        Appartementsrechtsplitsing partialUpdatedAppartementsrechtsplitsing = new Appartementsrechtsplitsing();
        partialUpdatedAppartementsrechtsplitsing.setId(appartementsrechtsplitsing.getId());

        partialUpdatedAppartementsrechtsplitsing
            .ddentificatieappartementsrechtsplitsing(UPDATED_DDENTIFICATIEAPPARTEMENTSRECHTSPLITSING)
            .typesplitsing(UPDATED_TYPESPLITSING);

        restAppartementsrechtsplitsingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppartementsrechtsplitsing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppartementsrechtsplitsing))
            )
            .andExpect(status().isOk());

        // Validate the Appartementsrechtsplitsing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppartementsrechtsplitsingUpdatableFieldsEquals(
            partialUpdatedAppartementsrechtsplitsing,
            getPersistedAppartementsrechtsplitsing(partialUpdatedAppartementsrechtsplitsing)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAppartementsrechtsplitsing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appartementsrechtsplitsing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppartementsrechtsplitsingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appartementsrechtsplitsing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appartementsrechtsplitsing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appartementsrechtsplitsing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppartementsrechtsplitsing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appartementsrechtsplitsing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppartementsrechtsplitsingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appartementsrechtsplitsing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appartementsrechtsplitsing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppartementsrechtsplitsing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appartementsrechtsplitsing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppartementsrechtsplitsingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(appartementsrechtsplitsing))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Appartementsrechtsplitsing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppartementsrechtsplitsing() throws Exception {
        // Initialize the database
        appartementsrechtsplitsingRepository.saveAndFlush(appartementsrechtsplitsing);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the appartementsrechtsplitsing
        restAppartementsrechtsplitsingMockMvc
            .perform(delete(ENTITY_API_URL_ID, appartementsrechtsplitsing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return appartementsrechtsplitsingRepository.count();
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

    protected Appartementsrechtsplitsing getPersistedAppartementsrechtsplitsing(Appartementsrechtsplitsing appartementsrechtsplitsing) {
        return appartementsrechtsplitsingRepository.findById(appartementsrechtsplitsing.getId()).orElseThrow();
    }

    protected void assertPersistedAppartementsrechtsplitsingToMatchAllProperties(
        Appartementsrechtsplitsing expectedAppartementsrechtsplitsing
    ) {
        assertAppartementsrechtsplitsingAllPropertiesEquals(
            expectedAppartementsrechtsplitsing,
            getPersistedAppartementsrechtsplitsing(expectedAppartementsrechtsplitsing)
        );
    }

    protected void assertPersistedAppartementsrechtsplitsingToMatchUpdatableProperties(
        Appartementsrechtsplitsing expectedAppartementsrechtsplitsing
    ) {
        assertAppartementsrechtsplitsingAllUpdatablePropertiesEquals(
            expectedAppartementsrechtsplitsing,
            getPersistedAppartementsrechtsplitsing(expectedAppartementsrechtsplitsing)
        );
    }
}
