package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LeveringAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Levering;
import nl.ritense.demo.repository.LeveringRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LeveringResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeveringResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTOP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTOP = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EENHEID = "AAAAAAAAAA";
    private static final String UPDATED_EENHEID = "BBBBBBBBBB";

    private static final String DEFAULT_FREQUENTIE = "AAAAAAAAAA";
    private static final String UPDATED_FREQUENTIE = "BBBBBBBBBB";

    private static final String DEFAULT_OMVANG = "AAAAAAAAAA";
    private static final String UPDATED_OMVANG = "BBBBBBBBBB";

    private static final String DEFAULT_STOPREDEN = "AAAAAAAAAA";
    private static final String UPDATED_STOPREDEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/leverings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LeveringRepository leveringRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeveringMockMvc;

    private Levering levering;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Levering createEntity(EntityManager em) {
        Levering levering = new Levering()
            .code(DEFAULT_CODE)
            .datumstart(DEFAULT_DATUMSTART)
            .datumstop(DEFAULT_DATUMSTOP)
            .eenheid(DEFAULT_EENHEID)
            .frequentie(DEFAULT_FREQUENTIE)
            .omvang(DEFAULT_OMVANG)
            .stopreden(DEFAULT_STOPREDEN);
        return levering;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Levering createUpdatedEntity(EntityManager em) {
        Levering levering = new Levering()
            .code(UPDATED_CODE)
            .datumstart(UPDATED_DATUMSTART)
            .datumstop(UPDATED_DATUMSTOP)
            .eenheid(UPDATED_EENHEID)
            .frequentie(UPDATED_FREQUENTIE)
            .omvang(UPDATED_OMVANG)
            .stopreden(UPDATED_STOPREDEN);
        return levering;
    }

    @BeforeEach
    public void initTest() {
        levering = createEntity(em);
    }

    @Test
    @Transactional
    void createLevering() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Levering
        var returnedLevering = om.readValue(
            restLeveringMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(levering)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Levering.class
        );

        // Validate the Levering in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLeveringUpdatableFieldsEquals(returnedLevering, getPersistedLevering(returnedLevering));
    }

    @Test
    @Transactional
    void createLeveringWithExistingId() throws Exception {
        // Create the Levering with an existing ID
        levering.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeveringMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(levering)))
            .andExpect(status().isBadRequest());

        // Validate the Levering in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeverings() throws Exception {
        // Initialize the database
        leveringRepository.saveAndFlush(levering);

        // Get all the leveringList
        restLeveringMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(levering.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].datumstop").value(hasItem(DEFAULT_DATUMSTOP.toString())))
            .andExpect(jsonPath("$.[*].eenheid").value(hasItem(DEFAULT_EENHEID)))
            .andExpect(jsonPath("$.[*].frequentie").value(hasItem(DEFAULT_FREQUENTIE)))
            .andExpect(jsonPath("$.[*].omvang").value(hasItem(DEFAULT_OMVANG)))
            .andExpect(jsonPath("$.[*].stopreden").value(hasItem(DEFAULT_STOPREDEN)));
    }

    @Test
    @Transactional
    void getLevering() throws Exception {
        // Initialize the database
        leveringRepository.saveAndFlush(levering);

        // Get the levering
        restLeveringMockMvc
            .perform(get(ENTITY_API_URL_ID, levering.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(levering.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.datumstop").value(DEFAULT_DATUMSTOP.toString()))
            .andExpect(jsonPath("$.eenheid").value(DEFAULT_EENHEID))
            .andExpect(jsonPath("$.frequentie").value(DEFAULT_FREQUENTIE))
            .andExpect(jsonPath("$.omvang").value(DEFAULT_OMVANG))
            .andExpect(jsonPath("$.stopreden").value(DEFAULT_STOPREDEN));
    }

    @Test
    @Transactional
    void getNonExistingLevering() throws Exception {
        // Get the levering
        restLeveringMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLevering() throws Exception {
        // Initialize the database
        leveringRepository.saveAndFlush(levering);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the levering
        Levering updatedLevering = leveringRepository.findById(levering.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLevering are not directly saved in db
        em.detach(updatedLevering);
        updatedLevering
            .code(UPDATED_CODE)
            .datumstart(UPDATED_DATUMSTART)
            .datumstop(UPDATED_DATUMSTOP)
            .eenheid(UPDATED_EENHEID)
            .frequentie(UPDATED_FREQUENTIE)
            .omvang(UPDATED_OMVANG)
            .stopreden(UPDATED_STOPREDEN);

        restLeveringMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLevering.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLevering))
            )
            .andExpect(status().isOk());

        // Validate the Levering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLeveringToMatchAllProperties(updatedLevering);
    }

    @Test
    @Transactional
    void putNonExistingLevering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        levering.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeveringMockMvc
            .perform(
                put(ENTITY_API_URL_ID, levering.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(levering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Levering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLevering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        levering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeveringMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(levering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Levering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLevering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        levering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeveringMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(levering)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Levering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeveringWithPatch() throws Exception {
        // Initialize the database
        leveringRepository.saveAndFlush(levering);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the levering using partial update
        Levering partialUpdatedLevering = new Levering();
        partialUpdatedLevering.setId(levering.getId());

        partialUpdatedLevering.datumstop(UPDATED_DATUMSTOP).omvang(UPDATED_OMVANG);

        restLeveringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevering.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLevering))
            )
            .andExpect(status().isOk());

        // Validate the Levering in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLeveringUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLevering, levering), getPersistedLevering(levering));
    }

    @Test
    @Transactional
    void fullUpdateLeveringWithPatch() throws Exception {
        // Initialize the database
        leveringRepository.saveAndFlush(levering);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the levering using partial update
        Levering partialUpdatedLevering = new Levering();
        partialUpdatedLevering.setId(levering.getId());

        partialUpdatedLevering
            .code(UPDATED_CODE)
            .datumstart(UPDATED_DATUMSTART)
            .datumstop(UPDATED_DATUMSTOP)
            .eenheid(UPDATED_EENHEID)
            .frequentie(UPDATED_FREQUENTIE)
            .omvang(UPDATED_OMVANG)
            .stopreden(UPDATED_STOPREDEN);

        restLeveringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevering.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLevering))
            )
            .andExpect(status().isOk());

        // Validate the Levering in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLeveringUpdatableFieldsEquals(partialUpdatedLevering, getPersistedLevering(partialUpdatedLevering));
    }

    @Test
    @Transactional
    void patchNonExistingLevering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        levering.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeveringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, levering.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(levering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Levering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLevering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        levering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeveringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(levering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Levering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLevering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        levering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeveringMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(levering)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Levering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLevering() throws Exception {
        // Initialize the database
        leveringRepository.saveAndFlush(levering);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the levering
        restLeveringMockMvc
            .perform(delete(ENTITY_API_URL_ID, levering.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return leveringRepository.count();
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

    protected Levering getPersistedLevering(Levering levering) {
        return leveringRepository.findById(levering.getId()).orElseThrow();
    }

    protected void assertPersistedLeveringToMatchAllProperties(Levering expectedLevering) {
        assertLeveringAllPropertiesEquals(expectedLevering, getPersistedLevering(expectedLevering));
    }

    protected void assertPersistedLeveringToMatchUpdatableProperties(Levering expectedLevering) {
        assertLeveringAllUpdatablePropertiesEquals(expectedLevering, getPersistedLevering(expectedLevering));
    }
}
