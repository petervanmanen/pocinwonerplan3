package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OnderwijsloopbaanAsserts.*;
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
import nl.ritense.demo.domain.Onderwijsloopbaan;
import nl.ritense.demo.repository.OnderwijsloopbaanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OnderwijsloopbaanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OnderwijsloopbaanResourceIT {

    private static final String ENTITY_API_URL = "/api/onderwijsloopbaans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OnderwijsloopbaanRepository onderwijsloopbaanRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOnderwijsloopbaanMockMvc;

    private Onderwijsloopbaan onderwijsloopbaan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderwijsloopbaan createEntity(EntityManager em) {
        Onderwijsloopbaan onderwijsloopbaan = new Onderwijsloopbaan();
        return onderwijsloopbaan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderwijsloopbaan createUpdatedEntity(EntityManager em) {
        Onderwijsloopbaan onderwijsloopbaan = new Onderwijsloopbaan();
        return onderwijsloopbaan;
    }

    @BeforeEach
    public void initTest() {
        onderwijsloopbaan = createEntity(em);
    }

    @Test
    @Transactional
    void createOnderwijsloopbaan() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Onderwijsloopbaan
        var returnedOnderwijsloopbaan = om.readValue(
            restOnderwijsloopbaanMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderwijsloopbaan)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Onderwijsloopbaan.class
        );

        // Validate the Onderwijsloopbaan in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOnderwijsloopbaanUpdatableFieldsEquals(returnedOnderwijsloopbaan, getPersistedOnderwijsloopbaan(returnedOnderwijsloopbaan));
    }

    @Test
    @Transactional
    void createOnderwijsloopbaanWithExistingId() throws Exception {
        // Create the Onderwijsloopbaan with an existing ID
        onderwijsloopbaan.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOnderwijsloopbaanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderwijsloopbaan)))
            .andExpect(status().isBadRequest());

        // Validate the Onderwijsloopbaan in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOnderwijsloopbaans() throws Exception {
        // Initialize the database
        onderwijsloopbaanRepository.saveAndFlush(onderwijsloopbaan);

        // Get all the onderwijsloopbaanList
        restOnderwijsloopbaanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(onderwijsloopbaan.getId().intValue())));
    }

    @Test
    @Transactional
    void getOnderwijsloopbaan() throws Exception {
        // Initialize the database
        onderwijsloopbaanRepository.saveAndFlush(onderwijsloopbaan);

        // Get the onderwijsloopbaan
        restOnderwijsloopbaanMockMvc
            .perform(get(ENTITY_API_URL_ID, onderwijsloopbaan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(onderwijsloopbaan.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOnderwijsloopbaan() throws Exception {
        // Get the onderwijsloopbaan
        restOnderwijsloopbaanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOnderwijsloopbaan() throws Exception {
        // Initialize the database
        onderwijsloopbaanRepository.saveAndFlush(onderwijsloopbaan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onderwijsloopbaan
        Onderwijsloopbaan updatedOnderwijsloopbaan = onderwijsloopbaanRepository.findById(onderwijsloopbaan.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOnderwijsloopbaan are not directly saved in db
        em.detach(updatedOnderwijsloopbaan);

        restOnderwijsloopbaanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOnderwijsloopbaan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOnderwijsloopbaan))
            )
            .andExpect(status().isOk());

        // Validate the Onderwijsloopbaan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOnderwijsloopbaanToMatchAllProperties(updatedOnderwijsloopbaan);
    }

    @Test
    @Transactional
    void putNonExistingOnderwijsloopbaan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijsloopbaan.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOnderwijsloopbaanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, onderwijsloopbaan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(onderwijsloopbaan))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderwijsloopbaan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOnderwijsloopbaan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijsloopbaan.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderwijsloopbaanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(onderwijsloopbaan))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderwijsloopbaan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOnderwijsloopbaan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijsloopbaan.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderwijsloopbaanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderwijsloopbaan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Onderwijsloopbaan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOnderwijsloopbaanWithPatch() throws Exception {
        // Initialize the database
        onderwijsloopbaanRepository.saveAndFlush(onderwijsloopbaan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onderwijsloopbaan using partial update
        Onderwijsloopbaan partialUpdatedOnderwijsloopbaan = new Onderwijsloopbaan();
        partialUpdatedOnderwijsloopbaan.setId(onderwijsloopbaan.getId());

        restOnderwijsloopbaanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOnderwijsloopbaan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOnderwijsloopbaan))
            )
            .andExpect(status().isOk());

        // Validate the Onderwijsloopbaan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOnderwijsloopbaanUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOnderwijsloopbaan, onderwijsloopbaan),
            getPersistedOnderwijsloopbaan(onderwijsloopbaan)
        );
    }

    @Test
    @Transactional
    void fullUpdateOnderwijsloopbaanWithPatch() throws Exception {
        // Initialize the database
        onderwijsloopbaanRepository.saveAndFlush(onderwijsloopbaan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onderwijsloopbaan using partial update
        Onderwijsloopbaan partialUpdatedOnderwijsloopbaan = new Onderwijsloopbaan();
        partialUpdatedOnderwijsloopbaan.setId(onderwijsloopbaan.getId());

        restOnderwijsloopbaanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOnderwijsloopbaan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOnderwijsloopbaan))
            )
            .andExpect(status().isOk());

        // Validate the Onderwijsloopbaan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOnderwijsloopbaanUpdatableFieldsEquals(
            partialUpdatedOnderwijsloopbaan,
            getPersistedOnderwijsloopbaan(partialUpdatedOnderwijsloopbaan)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOnderwijsloopbaan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijsloopbaan.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOnderwijsloopbaanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, onderwijsloopbaan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(onderwijsloopbaan))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderwijsloopbaan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOnderwijsloopbaan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijsloopbaan.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderwijsloopbaanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(onderwijsloopbaan))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderwijsloopbaan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOnderwijsloopbaan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijsloopbaan.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderwijsloopbaanMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(onderwijsloopbaan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Onderwijsloopbaan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOnderwijsloopbaan() throws Exception {
        // Initialize the database
        onderwijsloopbaanRepository.saveAndFlush(onderwijsloopbaan);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the onderwijsloopbaan
        restOnderwijsloopbaanMockMvc
            .perform(delete(ENTITY_API_URL_ID, onderwijsloopbaan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return onderwijsloopbaanRepository.count();
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

    protected Onderwijsloopbaan getPersistedOnderwijsloopbaan(Onderwijsloopbaan onderwijsloopbaan) {
        return onderwijsloopbaanRepository.findById(onderwijsloopbaan.getId()).orElseThrow();
    }

    protected void assertPersistedOnderwijsloopbaanToMatchAllProperties(Onderwijsloopbaan expectedOnderwijsloopbaan) {
        assertOnderwijsloopbaanAllPropertiesEquals(expectedOnderwijsloopbaan, getPersistedOnderwijsloopbaan(expectedOnderwijsloopbaan));
    }

    protected void assertPersistedOnderwijsloopbaanToMatchUpdatableProperties(Onderwijsloopbaan expectedOnderwijsloopbaan) {
        assertOnderwijsloopbaanAllUpdatablePropertiesEquals(
            expectedOnderwijsloopbaan,
            getPersistedOnderwijsloopbaan(expectedOnderwijsloopbaan)
        );
    }
}
