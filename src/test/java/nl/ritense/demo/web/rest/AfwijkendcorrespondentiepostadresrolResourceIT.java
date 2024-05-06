package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AfwijkendcorrespondentiepostadresrolAsserts.*;
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
import nl.ritense.demo.domain.Afwijkendcorrespondentiepostadresrol;
import nl.ritense.demo.repository.AfwijkendcorrespondentiepostadresrolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AfwijkendcorrespondentiepostadresrolResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AfwijkendcorrespondentiepostadresrolResourceIT {

    private static final String DEFAULT_POSTADRESTYPE = "AAAAAAAAAA";
    private static final String UPDATED_POSTADRESTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_POSTBUSOFANTWOORDNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_POSTBUSOFANTWOORDNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_POSTCODEPOSTADRES = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODEPOSTADRES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/afwijkendcorrespondentiepostadresrols";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AfwijkendcorrespondentiepostadresrolRepository afwijkendcorrespondentiepostadresrolRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAfwijkendcorrespondentiepostadresrolMockMvc;

    private Afwijkendcorrespondentiepostadresrol afwijkendcorrespondentiepostadresrol;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Afwijkendcorrespondentiepostadresrol createEntity(EntityManager em) {
        Afwijkendcorrespondentiepostadresrol afwijkendcorrespondentiepostadresrol = new Afwijkendcorrespondentiepostadresrol()
            .postadrestype(DEFAULT_POSTADRESTYPE)
            .postbusofantwoordnummer(DEFAULT_POSTBUSOFANTWOORDNUMMER)
            .postcodepostadres(DEFAULT_POSTCODEPOSTADRES);
        return afwijkendcorrespondentiepostadresrol;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Afwijkendcorrespondentiepostadresrol createUpdatedEntity(EntityManager em) {
        Afwijkendcorrespondentiepostadresrol afwijkendcorrespondentiepostadresrol = new Afwijkendcorrespondentiepostadresrol()
            .postadrestype(UPDATED_POSTADRESTYPE)
            .postbusofantwoordnummer(UPDATED_POSTBUSOFANTWOORDNUMMER)
            .postcodepostadres(UPDATED_POSTCODEPOSTADRES);
        return afwijkendcorrespondentiepostadresrol;
    }

    @BeforeEach
    public void initTest() {
        afwijkendcorrespondentiepostadresrol = createEntity(em);
    }

    @Test
    @Transactional
    void createAfwijkendcorrespondentiepostadresrol() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Afwijkendcorrespondentiepostadresrol
        var returnedAfwijkendcorrespondentiepostadresrol = om.readValue(
            restAfwijkendcorrespondentiepostadresrolMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(afwijkendcorrespondentiepostadresrol))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Afwijkendcorrespondentiepostadresrol.class
        );

        // Validate the Afwijkendcorrespondentiepostadresrol in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAfwijkendcorrespondentiepostadresrolUpdatableFieldsEquals(
            returnedAfwijkendcorrespondentiepostadresrol,
            getPersistedAfwijkendcorrespondentiepostadresrol(returnedAfwijkendcorrespondentiepostadresrol)
        );
    }

    @Test
    @Transactional
    void createAfwijkendcorrespondentiepostadresrolWithExistingId() throws Exception {
        // Create the Afwijkendcorrespondentiepostadresrol with an existing ID
        afwijkendcorrespondentiepostadresrol.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAfwijkendcorrespondentiepostadresrolMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(afwijkendcorrespondentiepostadresrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afwijkendcorrespondentiepostadresrol in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAfwijkendcorrespondentiepostadresrols() throws Exception {
        // Initialize the database
        afwijkendcorrespondentiepostadresrolRepository.saveAndFlush(afwijkendcorrespondentiepostadresrol);

        // Get all the afwijkendcorrespondentiepostadresrolList
        restAfwijkendcorrespondentiepostadresrolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(afwijkendcorrespondentiepostadresrol.getId().intValue())))
            .andExpect(jsonPath("$.[*].postadrestype").value(hasItem(DEFAULT_POSTADRESTYPE)))
            .andExpect(jsonPath("$.[*].postbusofantwoordnummer").value(hasItem(DEFAULT_POSTBUSOFANTWOORDNUMMER)))
            .andExpect(jsonPath("$.[*].postcodepostadres").value(hasItem(DEFAULT_POSTCODEPOSTADRES)));
    }

    @Test
    @Transactional
    void getAfwijkendcorrespondentiepostadresrol() throws Exception {
        // Initialize the database
        afwijkendcorrespondentiepostadresrolRepository.saveAndFlush(afwijkendcorrespondentiepostadresrol);

        // Get the afwijkendcorrespondentiepostadresrol
        restAfwijkendcorrespondentiepostadresrolMockMvc
            .perform(get(ENTITY_API_URL_ID, afwijkendcorrespondentiepostadresrol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(afwijkendcorrespondentiepostadresrol.getId().intValue()))
            .andExpect(jsonPath("$.postadrestype").value(DEFAULT_POSTADRESTYPE))
            .andExpect(jsonPath("$.postbusofantwoordnummer").value(DEFAULT_POSTBUSOFANTWOORDNUMMER))
            .andExpect(jsonPath("$.postcodepostadres").value(DEFAULT_POSTCODEPOSTADRES));
    }

    @Test
    @Transactional
    void getNonExistingAfwijkendcorrespondentiepostadresrol() throws Exception {
        // Get the afwijkendcorrespondentiepostadresrol
        restAfwijkendcorrespondentiepostadresrolMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAfwijkendcorrespondentiepostadresrol() throws Exception {
        // Initialize the database
        afwijkendcorrespondentiepostadresrolRepository.saveAndFlush(afwijkendcorrespondentiepostadresrol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the afwijkendcorrespondentiepostadresrol
        Afwijkendcorrespondentiepostadresrol updatedAfwijkendcorrespondentiepostadresrol = afwijkendcorrespondentiepostadresrolRepository
            .findById(afwijkendcorrespondentiepostadresrol.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAfwijkendcorrespondentiepostadresrol are not directly saved in db
        em.detach(updatedAfwijkendcorrespondentiepostadresrol);
        updatedAfwijkendcorrespondentiepostadresrol
            .postadrestype(UPDATED_POSTADRESTYPE)
            .postbusofantwoordnummer(UPDATED_POSTBUSOFANTWOORDNUMMER)
            .postcodepostadres(UPDATED_POSTCODEPOSTADRES);

        restAfwijkendcorrespondentiepostadresrolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAfwijkendcorrespondentiepostadresrol.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAfwijkendcorrespondentiepostadresrol))
            )
            .andExpect(status().isOk());

        // Validate the Afwijkendcorrespondentiepostadresrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAfwijkendcorrespondentiepostadresrolToMatchAllProperties(updatedAfwijkendcorrespondentiepostadresrol);
    }

    @Test
    @Transactional
    void putNonExistingAfwijkendcorrespondentiepostadresrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afwijkendcorrespondentiepostadresrol.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAfwijkendcorrespondentiepostadresrolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, afwijkendcorrespondentiepostadresrol.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(afwijkendcorrespondentiepostadresrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afwijkendcorrespondentiepostadresrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAfwijkendcorrespondentiepostadresrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afwijkendcorrespondentiepostadresrol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfwijkendcorrespondentiepostadresrolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(afwijkendcorrespondentiepostadresrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afwijkendcorrespondentiepostadresrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAfwijkendcorrespondentiepostadresrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afwijkendcorrespondentiepostadresrol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfwijkendcorrespondentiepostadresrolMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(afwijkendcorrespondentiepostadresrol))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Afwijkendcorrespondentiepostadresrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAfwijkendcorrespondentiepostadresrolWithPatch() throws Exception {
        // Initialize the database
        afwijkendcorrespondentiepostadresrolRepository.saveAndFlush(afwijkendcorrespondentiepostadresrol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the afwijkendcorrespondentiepostadresrol using partial update
        Afwijkendcorrespondentiepostadresrol partialUpdatedAfwijkendcorrespondentiepostadresrol =
            new Afwijkendcorrespondentiepostadresrol();
        partialUpdatedAfwijkendcorrespondentiepostadresrol.setId(afwijkendcorrespondentiepostadresrol.getId());

        partialUpdatedAfwijkendcorrespondentiepostadresrol.postcodepostadres(UPDATED_POSTCODEPOSTADRES);

        restAfwijkendcorrespondentiepostadresrolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAfwijkendcorrespondentiepostadresrol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAfwijkendcorrespondentiepostadresrol))
            )
            .andExpect(status().isOk());

        // Validate the Afwijkendcorrespondentiepostadresrol in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAfwijkendcorrespondentiepostadresrolUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAfwijkendcorrespondentiepostadresrol, afwijkendcorrespondentiepostadresrol),
            getPersistedAfwijkendcorrespondentiepostadresrol(afwijkendcorrespondentiepostadresrol)
        );
    }

    @Test
    @Transactional
    void fullUpdateAfwijkendcorrespondentiepostadresrolWithPatch() throws Exception {
        // Initialize the database
        afwijkendcorrespondentiepostadresrolRepository.saveAndFlush(afwijkendcorrespondentiepostadresrol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the afwijkendcorrespondentiepostadresrol using partial update
        Afwijkendcorrespondentiepostadresrol partialUpdatedAfwijkendcorrespondentiepostadresrol =
            new Afwijkendcorrespondentiepostadresrol();
        partialUpdatedAfwijkendcorrespondentiepostadresrol.setId(afwijkendcorrespondentiepostadresrol.getId());

        partialUpdatedAfwijkendcorrespondentiepostadresrol
            .postadrestype(UPDATED_POSTADRESTYPE)
            .postbusofantwoordnummer(UPDATED_POSTBUSOFANTWOORDNUMMER)
            .postcodepostadres(UPDATED_POSTCODEPOSTADRES);

        restAfwijkendcorrespondentiepostadresrolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAfwijkendcorrespondentiepostadresrol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAfwijkendcorrespondentiepostadresrol))
            )
            .andExpect(status().isOk());

        // Validate the Afwijkendcorrespondentiepostadresrol in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAfwijkendcorrespondentiepostadresrolUpdatableFieldsEquals(
            partialUpdatedAfwijkendcorrespondentiepostadresrol,
            getPersistedAfwijkendcorrespondentiepostadresrol(partialUpdatedAfwijkendcorrespondentiepostadresrol)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAfwijkendcorrespondentiepostadresrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afwijkendcorrespondentiepostadresrol.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAfwijkendcorrespondentiepostadresrolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, afwijkendcorrespondentiepostadresrol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(afwijkendcorrespondentiepostadresrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afwijkendcorrespondentiepostadresrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAfwijkendcorrespondentiepostadresrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afwijkendcorrespondentiepostadresrol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfwijkendcorrespondentiepostadresrolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(afwijkendcorrespondentiepostadresrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Afwijkendcorrespondentiepostadresrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAfwijkendcorrespondentiepostadresrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        afwijkendcorrespondentiepostadresrol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfwijkendcorrespondentiepostadresrolMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(afwijkendcorrespondentiepostadresrol))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Afwijkendcorrespondentiepostadresrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAfwijkendcorrespondentiepostadresrol() throws Exception {
        // Initialize the database
        afwijkendcorrespondentiepostadresrolRepository.saveAndFlush(afwijkendcorrespondentiepostadresrol);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the afwijkendcorrespondentiepostadresrol
        restAfwijkendcorrespondentiepostadresrolMockMvc
            .perform(delete(ENTITY_API_URL_ID, afwijkendcorrespondentiepostadresrol.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return afwijkendcorrespondentiepostadresrolRepository.count();
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

    protected Afwijkendcorrespondentiepostadresrol getPersistedAfwijkendcorrespondentiepostadresrol(
        Afwijkendcorrespondentiepostadresrol afwijkendcorrespondentiepostadresrol
    ) {
        return afwijkendcorrespondentiepostadresrolRepository.findById(afwijkendcorrespondentiepostadresrol.getId()).orElseThrow();
    }

    protected void assertPersistedAfwijkendcorrespondentiepostadresrolToMatchAllProperties(
        Afwijkendcorrespondentiepostadresrol expectedAfwijkendcorrespondentiepostadresrol
    ) {
        assertAfwijkendcorrespondentiepostadresrolAllPropertiesEquals(
            expectedAfwijkendcorrespondentiepostadresrol,
            getPersistedAfwijkendcorrespondentiepostadresrol(expectedAfwijkendcorrespondentiepostadresrol)
        );
    }

    protected void assertPersistedAfwijkendcorrespondentiepostadresrolToMatchUpdatableProperties(
        Afwijkendcorrespondentiepostadresrol expectedAfwijkendcorrespondentiepostadresrol
    ) {
        assertAfwijkendcorrespondentiepostadresrolAllUpdatablePropertiesEquals(
            expectedAfwijkendcorrespondentiepostadresrol,
            getPersistedAfwijkendcorrespondentiepostadresrol(expectedAfwijkendcorrespondentiepostadresrol)
        );
    }
}
