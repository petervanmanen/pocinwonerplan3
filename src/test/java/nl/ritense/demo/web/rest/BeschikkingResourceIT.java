package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BeschikkingAsserts.*;
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
import nl.ritense.demo.domain.Beschikking;
import nl.ritense.demo.repository.BeschikkingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BeschikkingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BeschikkingResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAAR = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAAR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMAFGIFTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAFGIFTE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GRONDSLAGEN = "AAAAAAAAAA";
    private static final String UPDATED_GRONDSLAGEN = "BBBBBBBBBB";

    private static final String DEFAULT_WET = "AAAAAAAAAA";
    private static final String UPDATED_WET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/beschikkings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BeschikkingRepository beschikkingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeschikkingMockMvc;

    private Beschikking beschikking;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beschikking createEntity(EntityManager em) {
        Beschikking beschikking = new Beschikking()
            .code(DEFAULT_CODE)
            .commentaar(DEFAULT_COMMENTAAR)
            .datumafgifte(DEFAULT_DATUMAFGIFTE)
            .grondslagen(DEFAULT_GRONDSLAGEN)
            .wet(DEFAULT_WET);
        return beschikking;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beschikking createUpdatedEntity(EntityManager em) {
        Beschikking beschikking = new Beschikking()
            .code(UPDATED_CODE)
            .commentaar(UPDATED_COMMENTAAR)
            .datumafgifte(UPDATED_DATUMAFGIFTE)
            .grondslagen(UPDATED_GRONDSLAGEN)
            .wet(UPDATED_WET);
        return beschikking;
    }

    @BeforeEach
    public void initTest() {
        beschikking = createEntity(em);
    }

    @Test
    @Transactional
    void createBeschikking() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Beschikking
        var returnedBeschikking = om.readValue(
            restBeschikkingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beschikking)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Beschikking.class
        );

        // Validate the Beschikking in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBeschikkingUpdatableFieldsEquals(returnedBeschikking, getPersistedBeschikking(returnedBeschikking));
    }

    @Test
    @Transactional
    void createBeschikkingWithExistingId() throws Exception {
        // Create the Beschikking with an existing ID
        beschikking.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeschikkingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beschikking)))
            .andExpect(status().isBadRequest());

        // Validate the Beschikking in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBeschikkings() throws Exception {
        // Initialize the database
        beschikkingRepository.saveAndFlush(beschikking);

        // Get all the beschikkingList
        restBeschikkingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beschikking.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].commentaar").value(hasItem(DEFAULT_COMMENTAAR)))
            .andExpect(jsonPath("$.[*].datumafgifte").value(hasItem(DEFAULT_DATUMAFGIFTE.toString())))
            .andExpect(jsonPath("$.[*].grondslagen").value(hasItem(DEFAULT_GRONDSLAGEN)))
            .andExpect(jsonPath("$.[*].wet").value(hasItem(DEFAULT_WET)));
    }

    @Test
    @Transactional
    void getBeschikking() throws Exception {
        // Initialize the database
        beschikkingRepository.saveAndFlush(beschikking);

        // Get the beschikking
        restBeschikkingMockMvc
            .perform(get(ENTITY_API_URL_ID, beschikking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beschikking.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.commentaar").value(DEFAULT_COMMENTAAR))
            .andExpect(jsonPath("$.datumafgifte").value(DEFAULT_DATUMAFGIFTE.toString()))
            .andExpect(jsonPath("$.grondslagen").value(DEFAULT_GRONDSLAGEN))
            .andExpect(jsonPath("$.wet").value(DEFAULT_WET));
    }

    @Test
    @Transactional
    void getNonExistingBeschikking() throws Exception {
        // Get the beschikking
        restBeschikkingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBeschikking() throws Exception {
        // Initialize the database
        beschikkingRepository.saveAndFlush(beschikking);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beschikking
        Beschikking updatedBeschikking = beschikkingRepository.findById(beschikking.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBeschikking are not directly saved in db
        em.detach(updatedBeschikking);
        updatedBeschikking
            .code(UPDATED_CODE)
            .commentaar(UPDATED_COMMENTAAR)
            .datumafgifte(UPDATED_DATUMAFGIFTE)
            .grondslagen(UPDATED_GRONDSLAGEN)
            .wet(UPDATED_WET);

        restBeschikkingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBeschikking.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBeschikking))
            )
            .andExpect(status().isOk());

        // Validate the Beschikking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBeschikkingToMatchAllProperties(updatedBeschikking);
    }

    @Test
    @Transactional
    void putNonExistingBeschikking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschikking.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeschikkingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, beschikking.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beschikking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beschikking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBeschikking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschikking.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeschikkingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beschikking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beschikking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBeschikking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschikking.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeschikkingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beschikking)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beschikking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBeschikkingWithPatch() throws Exception {
        // Initialize the database
        beschikkingRepository.saveAndFlush(beschikking);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beschikking using partial update
        Beschikking partialUpdatedBeschikking = new Beschikking();
        partialUpdatedBeschikking.setId(beschikking.getId());

        partialUpdatedBeschikking.grondslagen(UPDATED_GRONDSLAGEN).wet(UPDATED_WET);

        restBeschikkingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeschikking.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeschikking))
            )
            .andExpect(status().isOk());

        // Validate the Beschikking in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeschikkingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBeschikking, beschikking),
            getPersistedBeschikking(beschikking)
        );
    }

    @Test
    @Transactional
    void fullUpdateBeschikkingWithPatch() throws Exception {
        // Initialize the database
        beschikkingRepository.saveAndFlush(beschikking);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beschikking using partial update
        Beschikking partialUpdatedBeschikking = new Beschikking();
        partialUpdatedBeschikking.setId(beschikking.getId());

        partialUpdatedBeschikking
            .code(UPDATED_CODE)
            .commentaar(UPDATED_COMMENTAAR)
            .datumafgifte(UPDATED_DATUMAFGIFTE)
            .grondslagen(UPDATED_GRONDSLAGEN)
            .wet(UPDATED_WET);

        restBeschikkingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeschikking.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeschikking))
            )
            .andExpect(status().isOk());

        // Validate the Beschikking in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeschikkingUpdatableFieldsEquals(partialUpdatedBeschikking, getPersistedBeschikking(partialUpdatedBeschikking));
    }

    @Test
    @Transactional
    void patchNonExistingBeschikking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschikking.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeschikkingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, beschikking.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beschikking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beschikking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBeschikking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschikking.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeschikkingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beschikking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beschikking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBeschikking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschikking.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeschikkingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(beschikking)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beschikking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBeschikking() throws Exception {
        // Initialize the database
        beschikkingRepository.saveAndFlush(beschikking);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the beschikking
        restBeschikkingMockMvc
            .perform(delete(ENTITY_API_URL_ID, beschikking.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return beschikkingRepository.count();
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

    protected Beschikking getPersistedBeschikking(Beschikking beschikking) {
        return beschikkingRepository.findById(beschikking.getId()).orElseThrow();
    }

    protected void assertPersistedBeschikkingToMatchAllProperties(Beschikking expectedBeschikking) {
        assertBeschikkingAllPropertiesEquals(expectedBeschikking, getPersistedBeschikking(expectedBeschikking));
    }

    protected void assertPersistedBeschikkingToMatchUpdatableProperties(Beschikking expectedBeschikking) {
        assertBeschikkingAllUpdatablePropertiesEquals(expectedBeschikking, getPersistedBeschikking(expectedBeschikking));
    }
}
