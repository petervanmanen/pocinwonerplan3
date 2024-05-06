package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LeveringsvormAsserts.*;
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
import nl.ritense.demo.domain.Leveringsvorm;
import nl.ritense.demo.repository.LeveringsvormRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LeveringsvormResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeveringsvormResourceIT {

    private static final String DEFAULT_LEVERINGSVORMCODE = "AAAAAAAAAA";
    private static final String UPDATED_LEVERINGSVORMCODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_WET = "AAAAAAAAAA";
    private static final String UPDATED_WET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/leveringsvorms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LeveringsvormRepository leveringsvormRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeveringsvormMockMvc;

    private Leveringsvorm leveringsvorm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leveringsvorm createEntity(EntityManager em) {
        Leveringsvorm leveringsvorm = new Leveringsvorm().leveringsvormcode(DEFAULT_LEVERINGSVORMCODE).naam(DEFAULT_NAAM).wet(DEFAULT_WET);
        return leveringsvorm;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leveringsvorm createUpdatedEntity(EntityManager em) {
        Leveringsvorm leveringsvorm = new Leveringsvorm().leveringsvormcode(UPDATED_LEVERINGSVORMCODE).naam(UPDATED_NAAM).wet(UPDATED_WET);
        return leveringsvorm;
    }

    @BeforeEach
    public void initTest() {
        leveringsvorm = createEntity(em);
    }

    @Test
    @Transactional
    void createLeveringsvorm() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Leveringsvorm
        var returnedLeveringsvorm = om.readValue(
            restLeveringsvormMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leveringsvorm)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Leveringsvorm.class
        );

        // Validate the Leveringsvorm in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLeveringsvormUpdatableFieldsEquals(returnedLeveringsvorm, getPersistedLeveringsvorm(returnedLeveringsvorm));
    }

    @Test
    @Transactional
    void createLeveringsvormWithExistingId() throws Exception {
        // Create the Leveringsvorm with an existing ID
        leveringsvorm.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeveringsvormMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leveringsvorm)))
            .andExpect(status().isBadRequest());

        // Validate the Leveringsvorm in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeveringsvorms() throws Exception {
        // Initialize the database
        leveringsvormRepository.saveAndFlush(leveringsvorm);

        // Get all the leveringsvormList
        restLeveringsvormMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leveringsvorm.getId().intValue())))
            .andExpect(jsonPath("$.[*].leveringsvormcode").value(hasItem(DEFAULT_LEVERINGSVORMCODE)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].wet").value(hasItem(DEFAULT_WET)));
    }

    @Test
    @Transactional
    void getLeveringsvorm() throws Exception {
        // Initialize the database
        leveringsvormRepository.saveAndFlush(leveringsvorm);

        // Get the leveringsvorm
        restLeveringsvormMockMvc
            .perform(get(ENTITY_API_URL_ID, leveringsvorm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leveringsvorm.getId().intValue()))
            .andExpect(jsonPath("$.leveringsvormcode").value(DEFAULT_LEVERINGSVORMCODE))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.wet").value(DEFAULT_WET));
    }

    @Test
    @Transactional
    void getNonExistingLeveringsvorm() throws Exception {
        // Get the leveringsvorm
        restLeveringsvormMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeveringsvorm() throws Exception {
        // Initialize the database
        leveringsvormRepository.saveAndFlush(leveringsvorm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leveringsvorm
        Leveringsvorm updatedLeveringsvorm = leveringsvormRepository.findById(leveringsvorm.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLeveringsvorm are not directly saved in db
        em.detach(updatedLeveringsvorm);
        updatedLeveringsvorm.leveringsvormcode(UPDATED_LEVERINGSVORMCODE).naam(UPDATED_NAAM).wet(UPDATED_WET);

        restLeveringsvormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeveringsvorm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLeveringsvorm))
            )
            .andExpect(status().isOk());

        // Validate the Leveringsvorm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLeveringsvormToMatchAllProperties(updatedLeveringsvorm);
    }

    @Test
    @Transactional
    void putNonExistingLeveringsvorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leveringsvorm.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeveringsvormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leveringsvorm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(leveringsvorm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leveringsvorm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeveringsvorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leveringsvorm.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeveringsvormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(leveringsvorm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leveringsvorm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeveringsvorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leveringsvorm.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeveringsvormMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leveringsvorm)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leveringsvorm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeveringsvormWithPatch() throws Exception {
        // Initialize the database
        leveringsvormRepository.saveAndFlush(leveringsvorm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leveringsvorm using partial update
        Leveringsvorm partialUpdatedLeveringsvorm = new Leveringsvorm();
        partialUpdatedLeveringsvorm.setId(leveringsvorm.getId());

        partialUpdatedLeveringsvorm.naam(UPDATED_NAAM).wet(UPDATED_WET);

        restLeveringsvormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeveringsvorm.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLeveringsvorm))
            )
            .andExpect(status().isOk());

        // Validate the Leveringsvorm in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLeveringsvormUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLeveringsvorm, leveringsvorm),
            getPersistedLeveringsvorm(leveringsvorm)
        );
    }

    @Test
    @Transactional
    void fullUpdateLeveringsvormWithPatch() throws Exception {
        // Initialize the database
        leveringsvormRepository.saveAndFlush(leveringsvorm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leveringsvorm using partial update
        Leveringsvorm partialUpdatedLeveringsvorm = new Leveringsvorm();
        partialUpdatedLeveringsvorm.setId(leveringsvorm.getId());

        partialUpdatedLeveringsvorm.leveringsvormcode(UPDATED_LEVERINGSVORMCODE).naam(UPDATED_NAAM).wet(UPDATED_WET);

        restLeveringsvormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeveringsvorm.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLeveringsvorm))
            )
            .andExpect(status().isOk());

        // Validate the Leveringsvorm in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLeveringsvormUpdatableFieldsEquals(partialUpdatedLeveringsvorm, getPersistedLeveringsvorm(partialUpdatedLeveringsvorm));
    }

    @Test
    @Transactional
    void patchNonExistingLeveringsvorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leveringsvorm.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeveringsvormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leveringsvorm.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(leveringsvorm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leveringsvorm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeveringsvorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leveringsvorm.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeveringsvormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(leveringsvorm))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leveringsvorm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeveringsvorm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leveringsvorm.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeveringsvormMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(leveringsvorm)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leveringsvorm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeveringsvorm() throws Exception {
        // Initialize the database
        leveringsvormRepository.saveAndFlush(leveringsvorm);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the leveringsvorm
        restLeveringsvormMockMvc
            .perform(delete(ENTITY_API_URL_ID, leveringsvorm.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return leveringsvormRepository.count();
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

    protected Leveringsvorm getPersistedLeveringsvorm(Leveringsvorm leveringsvorm) {
        return leveringsvormRepository.findById(leveringsvorm.getId()).orElseThrow();
    }

    protected void assertPersistedLeveringsvormToMatchAllProperties(Leveringsvorm expectedLeveringsvorm) {
        assertLeveringsvormAllPropertiesEquals(expectedLeveringsvorm, getPersistedLeveringsvorm(expectedLeveringsvorm));
    }

    protected void assertPersistedLeveringsvormToMatchUpdatableProperties(Leveringsvorm expectedLeveringsvorm) {
        assertLeveringsvormAllUpdatablePropertiesEquals(expectedLeveringsvorm, getPersistedLeveringsvorm(expectedLeveringsvorm));
    }
}
