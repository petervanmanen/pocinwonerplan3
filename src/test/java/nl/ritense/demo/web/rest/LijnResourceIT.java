package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LijnAsserts.*;
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
import nl.ritense.demo.domain.Lijn;
import nl.ritense.demo.repository.LijnRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LijnResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LijnResourceIT {

    private static final String DEFAULT_LIJN = "AAAAAAAAAA";
    private static final String UPDATED_LIJN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/lijns";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LijnRepository lijnRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLijnMockMvc;

    private Lijn lijn;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lijn createEntity(EntityManager em) {
        Lijn lijn = new Lijn().lijn(DEFAULT_LIJN);
        return lijn;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lijn createUpdatedEntity(EntityManager em) {
        Lijn lijn = new Lijn().lijn(UPDATED_LIJN);
        return lijn;
    }

    @BeforeEach
    public void initTest() {
        lijn = createEntity(em);
    }

    @Test
    @Transactional
    void createLijn() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Lijn
        var returnedLijn = om.readValue(
            restLijnMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lijn)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Lijn.class
        );

        // Validate the Lijn in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLijnUpdatableFieldsEquals(returnedLijn, getPersistedLijn(returnedLijn));
    }

    @Test
    @Transactional
    void createLijnWithExistingId() throws Exception {
        // Create the Lijn with an existing ID
        lijn.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLijnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lijn)))
            .andExpect(status().isBadRequest());

        // Validate the Lijn in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLijns() throws Exception {
        // Initialize the database
        lijnRepository.saveAndFlush(lijn);

        // Get all the lijnList
        restLijnMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lijn.getId().intValue())))
            .andExpect(jsonPath("$.[*].lijn").value(hasItem(DEFAULT_LIJN)));
    }

    @Test
    @Transactional
    void getLijn() throws Exception {
        // Initialize the database
        lijnRepository.saveAndFlush(lijn);

        // Get the lijn
        restLijnMockMvc
            .perform(get(ENTITY_API_URL_ID, lijn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lijn.getId().intValue()))
            .andExpect(jsonPath("$.lijn").value(DEFAULT_LIJN));
    }

    @Test
    @Transactional
    void getNonExistingLijn() throws Exception {
        // Get the lijn
        restLijnMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLijn() throws Exception {
        // Initialize the database
        lijnRepository.saveAndFlush(lijn);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lijn
        Lijn updatedLijn = lijnRepository.findById(lijn.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLijn are not directly saved in db
        em.detach(updatedLijn);
        updatedLijn.lijn(UPDATED_LIJN);

        restLijnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLijn.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLijn))
            )
            .andExpect(status().isOk());

        // Validate the Lijn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLijnToMatchAllProperties(updatedLijn);
    }

    @Test
    @Transactional
    void putNonExistingLijn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lijn.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLijnMockMvc
            .perform(put(ENTITY_API_URL_ID, lijn.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lijn)))
            .andExpect(status().isBadRequest());

        // Validate the Lijn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLijn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lijn.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLijnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(lijn))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lijn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLijn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lijn.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLijnMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lijn)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lijn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLijnWithPatch() throws Exception {
        // Initialize the database
        lijnRepository.saveAndFlush(lijn);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lijn using partial update
        Lijn partialUpdatedLijn = new Lijn();
        partialUpdatedLijn.setId(lijn.getId());

        partialUpdatedLijn.lijn(UPDATED_LIJN);

        restLijnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLijn.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLijn))
            )
            .andExpect(status().isOk());

        // Validate the Lijn in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLijnUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLijn, lijn), getPersistedLijn(lijn));
    }

    @Test
    @Transactional
    void fullUpdateLijnWithPatch() throws Exception {
        // Initialize the database
        lijnRepository.saveAndFlush(lijn);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lijn using partial update
        Lijn partialUpdatedLijn = new Lijn();
        partialUpdatedLijn.setId(lijn.getId());

        partialUpdatedLijn.lijn(UPDATED_LIJN);

        restLijnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLijn.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLijn))
            )
            .andExpect(status().isOk());

        // Validate the Lijn in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLijnUpdatableFieldsEquals(partialUpdatedLijn, getPersistedLijn(partialUpdatedLijn));
    }

    @Test
    @Transactional
    void patchNonExistingLijn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lijn.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLijnMockMvc
            .perform(patch(ENTITY_API_URL_ID, lijn.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(lijn)))
            .andExpect(status().isBadRequest());

        // Validate the Lijn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLijn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lijn.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLijnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lijn))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lijn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLijn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lijn.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLijnMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(lijn)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lijn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLijn() throws Exception {
        // Initialize the database
        lijnRepository.saveAndFlush(lijn);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the lijn
        restLijnMockMvc
            .perform(delete(ENTITY_API_URL_ID, lijn.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return lijnRepository.count();
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

    protected Lijn getPersistedLijn(Lijn lijn) {
        return lijnRepository.findById(lijn.getId()).orElseThrow();
    }

    protected void assertPersistedLijnToMatchAllProperties(Lijn expectedLijn) {
        assertLijnAllPropertiesEquals(expectedLijn, getPersistedLijn(expectedLijn));
    }

    protected void assertPersistedLijnToMatchUpdatableProperties(Lijn expectedLijn) {
        assertLijnAllUpdatablePropertiesEquals(expectedLijn, getPersistedLijn(expectedLijn));
    }
}
