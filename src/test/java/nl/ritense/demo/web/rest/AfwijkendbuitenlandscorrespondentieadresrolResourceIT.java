package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AfwijkendbuitenlandscorrespondentieadresrolAsserts.*;
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
import nl.ritense.demo.domain.Afwijkendbuitenlandscorrespondentieadresrol;
import nl.ritense.demo.repository.AfwijkendbuitenlandscorrespondentieadresrolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AfwijkendbuitenlandscorrespondentieadresrolResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AfwijkendbuitenlandscorrespondentieadresrolResourceIT {

    private static final String DEFAULT_ADRESBUITENLAND_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBUITENLAND_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESBUITENLAND_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBUITENLAND_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESBUITENLAND_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBUITENLAND_3 = "BBBBBBBBBB";

    private static final String DEFAULT_LANDPOSTADRES = "AAAAAAAAAA";
    private static final String UPDATED_LANDPOSTADRES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/afwijkendbuitenlandscorrespondentieadresrols";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AfwijkendbuitenlandscorrespondentieadresrolRepository afwijkendbuitenlandscorrespondentieadresrolRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAfwijkendbuitenlandscorrespondentieadresrolMockMvc;

    private Afwijkendbuitenlandscorrespondentieadresrol afwijkendbuitenlandscorrespondentieadresrol;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Afwijkendbuitenlandscorrespondentieadresrol createEntity(EntityManager em) {
        Afwijkendbuitenlandscorrespondentieadresrol afwijkendbuitenlandscorrespondentieadresrol =
            new Afwijkendbuitenlandscorrespondentieadresrol()
                .adresbuitenland1(DEFAULT_ADRESBUITENLAND_1)
                .adresbuitenland2(DEFAULT_ADRESBUITENLAND_2)
                .adresbuitenland3(DEFAULT_ADRESBUITENLAND_3)
                .landpostadres(DEFAULT_LANDPOSTADRES);
        return afwijkendbuitenlandscorrespondentieadresrol;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Afwijkendbuitenlandscorrespondentieadresrol createUpdatedEntity(EntityManager em) {
        Afwijkendbuitenlandscorrespondentieadresrol afwijkendbuitenlandscorrespondentieadresrol =
            new Afwijkendbuitenlandscorrespondentieadresrol()
                .adresbuitenland1(UPDATED_ADRESBUITENLAND_1)
                .adresbuitenland2(UPDATED_ADRESBUITENLAND_2)
                .adresbuitenland3(UPDATED_ADRESBUITENLAND_3)
                .landpostadres(UPDATED_LANDPOSTADRES);
        return afwijkendbuitenlandscorrespondentieadresrol;
    }

    @BeforeEach
    public void initTest() {
        afwijkendbuitenlandscorrespondentieadresrol = createEntity(em);
    }

    @Test
    @Transactional
    void createAfwijkendbuitenlandscorrespondentieadresrol() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Afwijkendbuitenlandscorrespondentieadresrol
        var returnedAfwijkendbuitenlandscorrespondentieadresrol = om.readValue(
            restAfwijkendbuitenlandscorrespondentieadresrolMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(afwijkendbuitenlandscorrespondentieadresrol))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Afwijkendbuitenlandscorrespondentieadresrol.class
        );

        // Validate the Afwijkendbuitenlandscorrespondentieadresrol in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAfwijkendbuitenlandscorrespondentieadresrolUpdatableFieldsEquals(
            returnedAfwijkendbuitenlandscorrespondentieadresrol,
            getPersistedAfwijkendbuitenlandscorrespondentieadresrol(returnedAfwijkendbuitenlandscorrespondentieadresrol)
        );
    }

    @Test
    @Transactional
    void createAfwijkendbuitenlandscorrespondentieadresrolWithExistingId() throws Exception {
        // Create the Afwijkendbuitenlandscorrespondentieadresrol with an existing ID
        afwijkendbuitenlandscorrespondentieadresrol.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAfwijkendbuitenlandscorrespondentieadresrolMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(afwijkendbuitenlandscorrespondentieadresrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afwijkendbuitenlandscorrespondentieadresrol in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAfwijkendbuitenlandscorrespondentieadresrols() throws Exception {
        // Initialize the database
        afwijkendbuitenlandscorrespondentieadresrolRepository.saveAndFlush(afwijkendbuitenlandscorrespondentieadresrol);

        // Get all the afwijkendbuitenlandscorrespondentieadresrolList
        restAfwijkendbuitenlandscorrespondentieadresrolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(afwijkendbuitenlandscorrespondentieadresrol.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresbuitenland1").value(hasItem(DEFAULT_ADRESBUITENLAND_1)))
            .andExpect(jsonPath("$.[*].adresbuitenland2").value(hasItem(DEFAULT_ADRESBUITENLAND_2)))
            .andExpect(jsonPath("$.[*].adresbuitenland3").value(hasItem(DEFAULT_ADRESBUITENLAND_3)))
            .andExpect(jsonPath("$.[*].landpostadres").value(hasItem(DEFAULT_LANDPOSTADRES)));
    }

    @Test
    @Transactional
    void getAfwijkendbuitenlandscorrespondentieadresrol() throws Exception {
        // Initialize the database
        afwijkendbuitenlandscorrespondentieadresrolRepository.saveAndFlush(afwijkendbuitenlandscorrespondentieadresrol);

        // Get the afwijkendbuitenlandscorrespondentieadresrol
        restAfwijkendbuitenlandscorrespondentieadresrolMockMvc
            .perform(get(ENTITY_API_URL_ID, afwijkendbuitenlandscorrespondentieadresrol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(afwijkendbuitenlandscorrespondentieadresrol.getId().intValue()))
            .andExpect(jsonPath("$.adresbuitenland1").value(DEFAULT_ADRESBUITENLAND_1))
            .andExpect(jsonPath("$.adresbuitenland2").value(DEFAULT_ADRESBUITENLAND_2))
            .andExpect(jsonPath("$.adresbuitenland3").value(DEFAULT_ADRESBUITENLAND_3))
            .andExpect(jsonPath("$.landpostadres").value(DEFAULT_LANDPOSTADRES));
    }

    @Test
    @Transactional
    void getNonExistingAfwijkendbuitenlandscorrespondentieadresrol() throws Exception {
        // Get the afwijkendbuitenlandscorrespondentieadresrol
        restAfwijkendbuitenlandscorrespondentieadresrolMockMvc
            .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAfwijkendbuitenlandscorrespondentieadresrol() throws Exception {
        // Initialize the database
        afwijkendbuitenlandscorrespondentieadresrolRepository.saveAndFlush(afwijkendbuitenlandscorrespondentieadresrol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the afwijkendbuitenlandscorrespondentieadresrol
        Afwijkendbuitenlandscorrespondentieadresrol updatedAfwijkendbuitenlandscorrespondentieadresrol =
            afwijkendbuitenlandscorrespondentieadresrolRepository
                .findById(afwijkendbuitenlandscorrespondentieadresrol.getId())
                .orElseThrow();
        // Disconnect from session so that the updates on updatedAfwijkendbuitenlandscorrespondentieadresrol are not directly saved in db
        em.detach(updatedAfwijkendbuitenlandscorrespondentieadresrol);
        updatedAfwijkendbuitenlandscorrespondentieadresrol
            .adresbuitenland1(UPDATED_ADRESBUITENLAND_1)
            .adresbuitenland2(UPDATED_ADRESBUITENLAND_2)
            .adresbuitenland3(UPDATED_ADRESBUITENLAND_3)
            .landpostadres(UPDATED_LANDPOSTADRES);

        restAfwijkendbuitenlandscorrespondentieadresrolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAfwijkendbuitenlandscorrespondentieadresrol.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAfwijkendbuitenlandscorrespondentieadresrol))
            )
            .andExpect(status().isOk());

        // Validate the Afwijkendbuitenlandscorrespondentieadresrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAfwijkendbuitenlandscorrespondentieadresrolToMatchAllProperties(updatedAfwijkendbuitenlandscorrespondentieadresrol);
    }

    @Test
    @Transactional
    void putNonExistingAfwijkendbuitenlandscorrespondentieadresrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afwijkendbuitenlandscorrespondentieadresrol.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAfwijkendbuitenlandscorrespondentieadresrolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, afwijkendbuitenlandscorrespondentieadresrol.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(afwijkendbuitenlandscorrespondentieadresrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afwijkendbuitenlandscorrespondentieadresrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAfwijkendbuitenlandscorrespondentieadresrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afwijkendbuitenlandscorrespondentieadresrol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfwijkendbuitenlandscorrespondentieadresrolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(afwijkendbuitenlandscorrespondentieadresrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afwijkendbuitenlandscorrespondentieadresrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAfwijkendbuitenlandscorrespondentieadresrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afwijkendbuitenlandscorrespondentieadresrol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfwijkendbuitenlandscorrespondentieadresrolMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(afwijkendbuitenlandscorrespondentieadresrol))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Afwijkendbuitenlandscorrespondentieadresrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAfwijkendbuitenlandscorrespondentieadresrolWithPatch() throws Exception {
        // Initialize the database
        afwijkendbuitenlandscorrespondentieadresrolRepository.saveAndFlush(afwijkendbuitenlandscorrespondentieadresrol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the afwijkendbuitenlandscorrespondentieadresrol using partial update
        Afwijkendbuitenlandscorrespondentieadresrol partialUpdatedAfwijkendbuitenlandscorrespondentieadresrol =
            new Afwijkendbuitenlandscorrespondentieadresrol();
        partialUpdatedAfwijkendbuitenlandscorrespondentieadresrol.setId(afwijkendbuitenlandscorrespondentieadresrol.getId());

        partialUpdatedAfwijkendbuitenlandscorrespondentieadresrol.landpostadres(UPDATED_LANDPOSTADRES);

        restAfwijkendbuitenlandscorrespondentieadresrolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAfwijkendbuitenlandscorrespondentieadresrol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAfwijkendbuitenlandscorrespondentieadresrol))
            )
            .andExpect(status().isOk());

        // Validate the Afwijkendbuitenlandscorrespondentieadresrol in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAfwijkendbuitenlandscorrespondentieadresrolUpdatableFieldsEquals(
            createUpdateProxyForBean(
                partialUpdatedAfwijkendbuitenlandscorrespondentieadresrol,
                afwijkendbuitenlandscorrespondentieadresrol
            ),
            getPersistedAfwijkendbuitenlandscorrespondentieadresrol(afwijkendbuitenlandscorrespondentieadresrol)
        );
    }

    @Test
    @Transactional
    void fullUpdateAfwijkendbuitenlandscorrespondentieadresrolWithPatch() throws Exception {
        // Initialize the database
        afwijkendbuitenlandscorrespondentieadresrolRepository.saveAndFlush(afwijkendbuitenlandscorrespondentieadresrol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the afwijkendbuitenlandscorrespondentieadresrol using partial update
        Afwijkendbuitenlandscorrespondentieadresrol partialUpdatedAfwijkendbuitenlandscorrespondentieadresrol =
            new Afwijkendbuitenlandscorrespondentieadresrol();
        partialUpdatedAfwijkendbuitenlandscorrespondentieadresrol.setId(afwijkendbuitenlandscorrespondentieadresrol.getId());

        partialUpdatedAfwijkendbuitenlandscorrespondentieadresrol
            .adresbuitenland1(UPDATED_ADRESBUITENLAND_1)
            .adresbuitenland2(UPDATED_ADRESBUITENLAND_2)
            .adresbuitenland3(UPDATED_ADRESBUITENLAND_3)
            .landpostadres(UPDATED_LANDPOSTADRES);

        restAfwijkendbuitenlandscorrespondentieadresrolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAfwijkendbuitenlandscorrespondentieadresrol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAfwijkendbuitenlandscorrespondentieadresrol))
            )
            .andExpect(status().isOk());

        // Validate the Afwijkendbuitenlandscorrespondentieadresrol in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAfwijkendbuitenlandscorrespondentieadresrolUpdatableFieldsEquals(
            partialUpdatedAfwijkendbuitenlandscorrespondentieadresrol,
            getPersistedAfwijkendbuitenlandscorrespondentieadresrol(partialUpdatedAfwijkendbuitenlandscorrespondentieadresrol)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAfwijkendbuitenlandscorrespondentieadresrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afwijkendbuitenlandscorrespondentieadresrol.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAfwijkendbuitenlandscorrespondentieadresrolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, afwijkendbuitenlandscorrespondentieadresrol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(afwijkendbuitenlandscorrespondentieadresrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afwijkendbuitenlandscorrespondentieadresrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAfwijkendbuitenlandscorrespondentieadresrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afwijkendbuitenlandscorrespondentieadresrol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfwijkendbuitenlandscorrespondentieadresrolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(afwijkendbuitenlandscorrespondentieadresrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afwijkendbuitenlandscorrespondentieadresrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAfwijkendbuitenlandscorrespondentieadresrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afwijkendbuitenlandscorrespondentieadresrol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfwijkendbuitenlandscorrespondentieadresrolMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(afwijkendbuitenlandscorrespondentieadresrol))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Afwijkendbuitenlandscorrespondentieadresrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAfwijkendbuitenlandscorrespondentieadresrol() throws Exception {
        // Initialize the database
        afwijkendbuitenlandscorrespondentieadresrolRepository.saveAndFlush(afwijkendbuitenlandscorrespondentieadresrol);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the afwijkendbuitenlandscorrespondentieadresrol
        restAfwijkendbuitenlandscorrespondentieadresrolMockMvc
            .perform(delete(ENTITY_API_URL_ID, afwijkendbuitenlandscorrespondentieadresrol.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return afwijkendbuitenlandscorrespondentieadresrolRepository.count();
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

    protected Afwijkendbuitenlandscorrespondentieadresrol getPersistedAfwijkendbuitenlandscorrespondentieadresrol(
        Afwijkendbuitenlandscorrespondentieadresrol afwijkendbuitenlandscorrespondentieadresrol
    ) {
        return afwijkendbuitenlandscorrespondentieadresrolRepository
            .findById(afwijkendbuitenlandscorrespondentieadresrol.getId())
            .orElseThrow();
    }

    protected void assertPersistedAfwijkendbuitenlandscorrespondentieadresrolToMatchAllProperties(
        Afwijkendbuitenlandscorrespondentieadresrol expectedAfwijkendbuitenlandscorrespondentieadresrol
    ) {
        assertAfwijkendbuitenlandscorrespondentieadresrolAllPropertiesEquals(
            expectedAfwijkendbuitenlandscorrespondentieadresrol,
            getPersistedAfwijkendbuitenlandscorrespondentieadresrol(expectedAfwijkendbuitenlandscorrespondentieadresrol)
        );
    }

    protected void assertPersistedAfwijkendbuitenlandscorrespondentieadresrolToMatchUpdatableProperties(
        Afwijkendbuitenlandscorrespondentieadresrol expectedAfwijkendbuitenlandscorrespondentieadresrol
    ) {
        assertAfwijkendbuitenlandscorrespondentieadresrolAllUpdatablePropertiesEquals(
            expectedAfwijkendbuitenlandscorrespondentieadresrol,
            getPersistedAfwijkendbuitenlandscorrespondentieadresrol(expectedAfwijkendbuitenlandscorrespondentieadresrol)
        );
    }
}
