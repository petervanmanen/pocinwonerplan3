package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.HuurwoningenAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Huurwoningen;
import nl.ritense.demo.repository.HuurwoningenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HuurwoningenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HuurwoningenResourceIT {

    private static final BigDecimal DEFAULT_HUURPRIJS = new BigDecimal(1);
    private static final BigDecimal UPDATED_HUURPRIJS = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/huurwoningens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HuurwoningenRepository huurwoningenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHuurwoningenMockMvc;

    private Huurwoningen huurwoningen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Huurwoningen createEntity(EntityManager em) {
        Huurwoningen huurwoningen = new Huurwoningen().huurprijs(DEFAULT_HUURPRIJS);
        return huurwoningen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Huurwoningen createUpdatedEntity(EntityManager em) {
        Huurwoningen huurwoningen = new Huurwoningen().huurprijs(UPDATED_HUURPRIJS);
        return huurwoningen;
    }

    @BeforeEach
    public void initTest() {
        huurwoningen = createEntity(em);
    }

    @Test
    @Transactional
    void createHuurwoningen() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Huurwoningen
        var returnedHuurwoningen = om.readValue(
            restHuurwoningenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(huurwoningen)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Huurwoningen.class
        );

        // Validate the Huurwoningen in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHuurwoningenUpdatableFieldsEquals(returnedHuurwoningen, getPersistedHuurwoningen(returnedHuurwoningen));
    }

    @Test
    @Transactional
    void createHuurwoningenWithExistingId() throws Exception {
        // Create the Huurwoningen with an existing ID
        huurwoningen.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHuurwoningenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(huurwoningen)))
            .andExpect(status().isBadRequest());

        // Validate the Huurwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHuurwoningens() throws Exception {
        // Initialize the database
        huurwoningenRepository.saveAndFlush(huurwoningen);

        // Get all the huurwoningenList
        restHuurwoningenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(huurwoningen.getId().intValue())))
            .andExpect(jsonPath("$.[*].huurprijs").value(hasItem(sameNumber(DEFAULT_HUURPRIJS))));
    }

    @Test
    @Transactional
    void getHuurwoningen() throws Exception {
        // Initialize the database
        huurwoningenRepository.saveAndFlush(huurwoningen);

        // Get the huurwoningen
        restHuurwoningenMockMvc
            .perform(get(ENTITY_API_URL_ID, huurwoningen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(huurwoningen.getId().intValue()))
            .andExpect(jsonPath("$.huurprijs").value(sameNumber(DEFAULT_HUURPRIJS)));
    }

    @Test
    @Transactional
    void getNonExistingHuurwoningen() throws Exception {
        // Get the huurwoningen
        restHuurwoningenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHuurwoningen() throws Exception {
        // Initialize the database
        huurwoningenRepository.saveAndFlush(huurwoningen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the huurwoningen
        Huurwoningen updatedHuurwoningen = huurwoningenRepository.findById(huurwoningen.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHuurwoningen are not directly saved in db
        em.detach(updatedHuurwoningen);
        updatedHuurwoningen.huurprijs(UPDATED_HUURPRIJS);

        restHuurwoningenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHuurwoningen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHuurwoningen))
            )
            .andExpect(status().isOk());

        // Validate the Huurwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHuurwoningenToMatchAllProperties(updatedHuurwoningen);
    }

    @Test
    @Transactional
    void putNonExistingHuurwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        huurwoningen.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHuurwoningenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, huurwoningen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(huurwoningen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Huurwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHuurwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        huurwoningen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHuurwoningenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(huurwoningen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Huurwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHuurwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        huurwoningen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHuurwoningenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(huurwoningen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Huurwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHuurwoningenWithPatch() throws Exception {
        // Initialize the database
        huurwoningenRepository.saveAndFlush(huurwoningen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the huurwoningen using partial update
        Huurwoningen partialUpdatedHuurwoningen = new Huurwoningen();
        partialUpdatedHuurwoningen.setId(huurwoningen.getId());

        restHuurwoningenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHuurwoningen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHuurwoningen))
            )
            .andExpect(status().isOk());

        // Validate the Huurwoningen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHuurwoningenUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHuurwoningen, huurwoningen),
            getPersistedHuurwoningen(huurwoningen)
        );
    }

    @Test
    @Transactional
    void fullUpdateHuurwoningenWithPatch() throws Exception {
        // Initialize the database
        huurwoningenRepository.saveAndFlush(huurwoningen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the huurwoningen using partial update
        Huurwoningen partialUpdatedHuurwoningen = new Huurwoningen();
        partialUpdatedHuurwoningen.setId(huurwoningen.getId());

        partialUpdatedHuurwoningen.huurprijs(UPDATED_HUURPRIJS);

        restHuurwoningenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHuurwoningen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHuurwoningen))
            )
            .andExpect(status().isOk());

        // Validate the Huurwoningen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHuurwoningenUpdatableFieldsEquals(partialUpdatedHuurwoningen, getPersistedHuurwoningen(partialUpdatedHuurwoningen));
    }

    @Test
    @Transactional
    void patchNonExistingHuurwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        huurwoningen.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHuurwoningenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, huurwoningen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(huurwoningen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Huurwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHuurwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        huurwoningen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHuurwoningenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(huurwoningen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Huurwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHuurwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        huurwoningen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHuurwoningenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(huurwoningen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Huurwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHuurwoningen() throws Exception {
        // Initialize the database
        huurwoningenRepository.saveAndFlush(huurwoningen);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the huurwoningen
        restHuurwoningenMockMvc
            .perform(delete(ENTITY_API_URL_ID, huurwoningen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return huurwoningenRepository.count();
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

    protected Huurwoningen getPersistedHuurwoningen(Huurwoningen huurwoningen) {
        return huurwoningenRepository.findById(huurwoningen.getId()).orElseThrow();
    }

    protected void assertPersistedHuurwoningenToMatchAllProperties(Huurwoningen expectedHuurwoningen) {
        assertHuurwoningenAllPropertiesEquals(expectedHuurwoningen, getPersistedHuurwoningen(expectedHuurwoningen));
    }

    protected void assertPersistedHuurwoningenToMatchUpdatableProperties(Huurwoningen expectedHuurwoningen) {
        assertHuurwoningenAllUpdatablePropertiesEquals(expectedHuurwoningen, getPersistedHuurwoningen(expectedHuurwoningen));
    }
}
