package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OrganisatorischeeenheidhrAsserts.*;
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
import nl.ritense.demo.domain.Organisatorischeeenheidhr;
import nl.ritense.demo.repository.OrganisatorischeeenheidhrRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrganisatorischeeenheidhrResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganisatorischeeenheidhrResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/organisatorischeeenheidhrs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrganisatorischeeenheidhrRepository organisatorischeeenheidhrRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganisatorischeeenheidhrMockMvc;

    private Organisatorischeeenheidhr organisatorischeeenheidhr;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organisatorischeeenheidhr createEntity(EntityManager em) {
        Organisatorischeeenheidhr organisatorischeeenheidhr = new Organisatorischeeenheidhr().naam(DEFAULT_NAAM).type(DEFAULT_TYPE);
        return organisatorischeeenheidhr;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organisatorischeeenheidhr createUpdatedEntity(EntityManager em) {
        Organisatorischeeenheidhr organisatorischeeenheidhr = new Organisatorischeeenheidhr().naam(UPDATED_NAAM).type(UPDATED_TYPE);
        return organisatorischeeenheidhr;
    }

    @BeforeEach
    public void initTest() {
        organisatorischeeenheidhr = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganisatorischeeenheidhr() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Organisatorischeeenheidhr
        var returnedOrganisatorischeeenheidhr = om.readValue(
            restOrganisatorischeeenheidhrMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organisatorischeeenheidhr))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Organisatorischeeenheidhr.class
        );

        // Validate the Organisatorischeeenheidhr in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOrganisatorischeeenheidhrUpdatableFieldsEquals(
            returnedOrganisatorischeeenheidhr,
            getPersistedOrganisatorischeeenheidhr(returnedOrganisatorischeeenheidhr)
        );
    }

    @Test
    @Transactional
    void createOrganisatorischeeenheidhrWithExistingId() throws Exception {
        // Create the Organisatorischeeenheidhr with an existing ID
        organisatorischeeenheidhr.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganisatorischeeenheidhrMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organisatorischeeenheidhr)))
            .andExpect(status().isBadRequest());

        // Validate the Organisatorischeeenheidhr in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrganisatorischeeenheidhrs() throws Exception {
        // Initialize the database
        organisatorischeeenheidhrRepository.saveAndFlush(organisatorischeeenheidhr);

        // Get all the organisatorischeeenheidhrList
        restOrganisatorischeeenheidhrMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisatorischeeenheidhr.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getOrganisatorischeeenheidhr() throws Exception {
        // Initialize the database
        organisatorischeeenheidhrRepository.saveAndFlush(organisatorischeeenheidhr);

        // Get the organisatorischeeenheidhr
        restOrganisatorischeeenheidhrMockMvc
            .perform(get(ENTITY_API_URL_ID, organisatorischeeenheidhr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organisatorischeeenheidhr.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingOrganisatorischeeenheidhr() throws Exception {
        // Get the organisatorischeeenheidhr
        restOrganisatorischeeenheidhrMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganisatorischeeenheidhr() throws Exception {
        // Initialize the database
        organisatorischeeenheidhrRepository.saveAndFlush(organisatorischeeenheidhr);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organisatorischeeenheidhr
        Organisatorischeeenheidhr updatedOrganisatorischeeenheidhr = organisatorischeeenheidhrRepository
            .findById(organisatorischeeenheidhr.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOrganisatorischeeenheidhr are not directly saved in db
        em.detach(updatedOrganisatorischeeenheidhr);
        updatedOrganisatorischeeenheidhr.naam(UPDATED_NAAM).type(UPDATED_TYPE);

        restOrganisatorischeeenheidhrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrganisatorischeeenheidhr.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOrganisatorischeeenheidhr))
            )
            .andExpect(status().isOk());

        // Validate the Organisatorischeeenheidhr in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrganisatorischeeenheidhrToMatchAllProperties(updatedOrganisatorischeeenheidhr);
    }

    @Test
    @Transactional
    void putNonExistingOrganisatorischeeenheidhr() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organisatorischeeenheidhr.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganisatorischeeenheidhrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organisatorischeeenheidhr.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organisatorischeeenheidhr))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisatorischeeenheidhr in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganisatorischeeenheidhr() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organisatorischeeenheidhr.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisatorischeeenheidhrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organisatorischeeenheidhr))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisatorischeeenheidhr in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganisatorischeeenheidhr() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organisatorischeeenheidhr.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisatorischeeenheidhrMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organisatorischeeenheidhr)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organisatorischeeenheidhr in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganisatorischeeenheidhrWithPatch() throws Exception {
        // Initialize the database
        organisatorischeeenheidhrRepository.saveAndFlush(organisatorischeeenheidhr);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organisatorischeeenheidhr using partial update
        Organisatorischeeenheidhr partialUpdatedOrganisatorischeeenheidhr = new Organisatorischeeenheidhr();
        partialUpdatedOrganisatorischeeenheidhr.setId(organisatorischeeenheidhr.getId());

        partialUpdatedOrganisatorischeeenheidhr.type(UPDATED_TYPE);

        restOrganisatorischeeenheidhrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganisatorischeeenheidhr.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganisatorischeeenheidhr))
            )
            .andExpect(status().isOk());

        // Validate the Organisatorischeeenheidhr in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganisatorischeeenheidhrUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrganisatorischeeenheidhr, organisatorischeeenheidhr),
            getPersistedOrganisatorischeeenheidhr(organisatorischeeenheidhr)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrganisatorischeeenheidhrWithPatch() throws Exception {
        // Initialize the database
        organisatorischeeenheidhrRepository.saveAndFlush(organisatorischeeenheidhr);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organisatorischeeenheidhr using partial update
        Organisatorischeeenheidhr partialUpdatedOrganisatorischeeenheidhr = new Organisatorischeeenheidhr();
        partialUpdatedOrganisatorischeeenheidhr.setId(organisatorischeeenheidhr.getId());

        partialUpdatedOrganisatorischeeenheidhr.naam(UPDATED_NAAM).type(UPDATED_TYPE);

        restOrganisatorischeeenheidhrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganisatorischeeenheidhr.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganisatorischeeenheidhr))
            )
            .andExpect(status().isOk());

        // Validate the Organisatorischeeenheidhr in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganisatorischeeenheidhrUpdatableFieldsEquals(
            partialUpdatedOrganisatorischeeenheidhr,
            getPersistedOrganisatorischeeenheidhr(partialUpdatedOrganisatorischeeenheidhr)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOrganisatorischeeenheidhr() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organisatorischeeenheidhr.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganisatorischeeenheidhrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organisatorischeeenheidhr.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organisatorischeeenheidhr))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisatorischeeenheidhr in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganisatorischeeenheidhr() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organisatorischeeenheidhr.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisatorischeeenheidhrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organisatorischeeenheidhr))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisatorischeeenheidhr in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganisatorischeeenheidhr() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organisatorischeeenheidhr.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisatorischeeenheidhrMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(organisatorischeeenheidhr))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organisatorischeeenheidhr in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganisatorischeeenheidhr() throws Exception {
        // Initialize the database
        organisatorischeeenheidhrRepository.saveAndFlush(organisatorischeeenheidhr);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the organisatorischeeenheidhr
        restOrganisatorischeeenheidhrMockMvc
            .perform(delete(ENTITY_API_URL_ID, organisatorischeeenheidhr.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return organisatorischeeenheidhrRepository.count();
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

    protected Organisatorischeeenheidhr getPersistedOrganisatorischeeenheidhr(Organisatorischeeenheidhr organisatorischeeenheidhr) {
        return organisatorischeeenheidhrRepository.findById(organisatorischeeenheidhr.getId()).orElseThrow();
    }

    protected void assertPersistedOrganisatorischeeenheidhrToMatchAllProperties(
        Organisatorischeeenheidhr expectedOrganisatorischeeenheidhr
    ) {
        assertOrganisatorischeeenheidhrAllPropertiesEquals(
            expectedOrganisatorischeeenheidhr,
            getPersistedOrganisatorischeeenheidhr(expectedOrganisatorischeeenheidhr)
        );
    }

    protected void assertPersistedOrganisatorischeeenheidhrToMatchUpdatableProperties(
        Organisatorischeeenheidhr expectedOrganisatorischeeenheidhr
    ) {
        assertOrganisatorischeeenheidhrAllUpdatablePropertiesEquals(
            expectedOrganisatorischeeenheidhr,
            getPersistedOrganisatorischeeenheidhr(expectedOrganisatorischeeenheidhr)
        );
    }
}
