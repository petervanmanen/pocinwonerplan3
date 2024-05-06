package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.HeffingAsserts.*;
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
import nl.ritense.demo.domain.Heffing;
import nl.ritense.demo.repository.HeffingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HeffingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HeffingResourceIT {

    private static final String DEFAULT_BEDRAG = "AAAAAAAAAA";
    private static final String UPDATED_BEDRAG = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMINDIENING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMINDIENING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GEFACTUREERD = false;
    private static final Boolean UPDATED_GEFACTUREERD = true;

    private static final String DEFAULT_INREKENING = "AAAAAAAAAA";
    private static final String UPDATED_INREKENING = "BBBBBBBBBB";

    private static final String DEFAULT_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_RUNNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_RUNNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/heffings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HeffingRepository heffingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHeffingMockMvc;

    private Heffing heffing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Heffing createEntity(EntityManager em) {
        Heffing heffing = new Heffing()
            .bedrag(DEFAULT_BEDRAG)
            .code(DEFAULT_CODE)
            .datumindiening(DEFAULT_DATUMINDIENING)
            .gefactureerd(DEFAULT_GEFACTUREERD)
            .inrekening(DEFAULT_INREKENING)
            .nummer(DEFAULT_NUMMER)
            .runnummer(DEFAULT_RUNNUMMER);
        return heffing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Heffing createUpdatedEntity(EntityManager em) {
        Heffing heffing = new Heffing()
            .bedrag(UPDATED_BEDRAG)
            .code(UPDATED_CODE)
            .datumindiening(UPDATED_DATUMINDIENING)
            .gefactureerd(UPDATED_GEFACTUREERD)
            .inrekening(UPDATED_INREKENING)
            .nummer(UPDATED_NUMMER)
            .runnummer(UPDATED_RUNNUMMER);
        return heffing;
    }

    @BeforeEach
    public void initTest() {
        heffing = createEntity(em);
    }

    @Test
    @Transactional
    void createHeffing() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Heffing
        var returnedHeffing = om.readValue(
            restHeffingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(heffing)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Heffing.class
        );

        // Validate the Heffing in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHeffingUpdatableFieldsEquals(returnedHeffing, getPersistedHeffing(returnedHeffing));
    }

    @Test
    @Transactional
    void createHeffingWithExistingId() throws Exception {
        // Create the Heffing with an existing ID
        heffing.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHeffingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(heffing)))
            .andExpect(status().isBadRequest());

        // Validate the Heffing in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHeffings() throws Exception {
        // Initialize the database
        heffingRepository.saveAndFlush(heffing);

        // Get all the heffingList
        restHeffingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(heffing.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].datumindiening").value(hasItem(DEFAULT_DATUMINDIENING)))
            .andExpect(jsonPath("$.[*].gefactureerd").value(hasItem(DEFAULT_GEFACTUREERD.booleanValue())))
            .andExpect(jsonPath("$.[*].inrekening").value(hasItem(DEFAULT_INREKENING)))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].runnummer").value(hasItem(DEFAULT_RUNNUMMER)));
    }

    @Test
    @Transactional
    void getHeffing() throws Exception {
        // Initialize the database
        heffingRepository.saveAndFlush(heffing);

        // Get the heffing
        restHeffingMockMvc
            .perform(get(ENTITY_API_URL_ID, heffing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(heffing.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(DEFAULT_BEDRAG))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.datumindiening").value(DEFAULT_DATUMINDIENING))
            .andExpect(jsonPath("$.gefactureerd").value(DEFAULT_GEFACTUREERD.booleanValue()))
            .andExpect(jsonPath("$.inrekening").value(DEFAULT_INREKENING))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.runnummer").value(DEFAULT_RUNNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingHeffing() throws Exception {
        // Get the heffing
        restHeffingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHeffing() throws Exception {
        // Initialize the database
        heffingRepository.saveAndFlush(heffing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the heffing
        Heffing updatedHeffing = heffingRepository.findById(heffing.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHeffing are not directly saved in db
        em.detach(updatedHeffing);
        updatedHeffing
            .bedrag(UPDATED_BEDRAG)
            .code(UPDATED_CODE)
            .datumindiening(UPDATED_DATUMINDIENING)
            .gefactureerd(UPDATED_GEFACTUREERD)
            .inrekening(UPDATED_INREKENING)
            .nummer(UPDATED_NUMMER)
            .runnummer(UPDATED_RUNNUMMER);

        restHeffingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHeffing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHeffing))
            )
            .andExpect(status().isOk());

        // Validate the Heffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHeffingToMatchAllProperties(updatedHeffing);
    }

    @Test
    @Transactional
    void putNonExistingHeffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        heffing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeffingMockMvc
            .perform(put(ENTITY_API_URL_ID, heffing.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(heffing)))
            .andExpect(status().isBadRequest());

        // Validate the Heffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHeffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        heffing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeffingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(heffing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Heffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHeffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        heffing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeffingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(heffing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Heffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHeffingWithPatch() throws Exception {
        // Initialize the database
        heffingRepository.saveAndFlush(heffing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the heffing using partial update
        Heffing partialUpdatedHeffing = new Heffing();
        partialUpdatedHeffing.setId(heffing.getId());

        partialUpdatedHeffing.bedrag(UPDATED_BEDRAG).code(UPDATED_CODE).nummer(UPDATED_NUMMER);

        restHeffingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeffing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHeffing))
            )
            .andExpect(status().isOk());

        // Validate the Heffing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHeffingUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedHeffing, heffing), getPersistedHeffing(heffing));
    }

    @Test
    @Transactional
    void fullUpdateHeffingWithPatch() throws Exception {
        // Initialize the database
        heffingRepository.saveAndFlush(heffing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the heffing using partial update
        Heffing partialUpdatedHeffing = new Heffing();
        partialUpdatedHeffing.setId(heffing.getId());

        partialUpdatedHeffing
            .bedrag(UPDATED_BEDRAG)
            .code(UPDATED_CODE)
            .datumindiening(UPDATED_DATUMINDIENING)
            .gefactureerd(UPDATED_GEFACTUREERD)
            .inrekening(UPDATED_INREKENING)
            .nummer(UPDATED_NUMMER)
            .runnummer(UPDATED_RUNNUMMER);

        restHeffingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeffing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHeffing))
            )
            .andExpect(status().isOk());

        // Validate the Heffing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHeffingUpdatableFieldsEquals(partialUpdatedHeffing, getPersistedHeffing(partialUpdatedHeffing));
    }

    @Test
    @Transactional
    void patchNonExistingHeffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        heffing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeffingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, heffing.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(heffing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Heffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHeffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        heffing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeffingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(heffing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Heffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHeffing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        heffing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeffingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(heffing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Heffing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHeffing() throws Exception {
        // Initialize the database
        heffingRepository.saveAndFlush(heffing);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the heffing
        restHeffingMockMvc
            .perform(delete(ENTITY_API_URL_ID, heffing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return heffingRepository.count();
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

    protected Heffing getPersistedHeffing(Heffing heffing) {
        return heffingRepository.findById(heffing.getId()).orElseThrow();
    }

    protected void assertPersistedHeffingToMatchAllProperties(Heffing expectedHeffing) {
        assertHeffingAllPropertiesEquals(expectedHeffing, getPersistedHeffing(expectedHeffing));
    }

    protected void assertPersistedHeffingToMatchUpdatableProperties(Heffing expectedHeffing) {
        assertHeffingAllUpdatablePropertiesEquals(expectedHeffing, getPersistedHeffing(expectedHeffing));
    }
}
