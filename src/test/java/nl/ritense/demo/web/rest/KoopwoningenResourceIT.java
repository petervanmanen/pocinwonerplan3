package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KoopwoningenAsserts.*;
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
import nl.ritense.demo.domain.Koopwoningen;
import nl.ritense.demo.repository.KoopwoningenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KoopwoningenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KoopwoningenResourceIT {

    private static final BigDecimal DEFAULT_KOOPPRIJS = new BigDecimal(1);
    private static final BigDecimal UPDATED_KOOPPRIJS = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/koopwoningens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KoopwoningenRepository koopwoningenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKoopwoningenMockMvc;

    private Koopwoningen koopwoningen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Koopwoningen createEntity(EntityManager em) {
        Koopwoningen koopwoningen = new Koopwoningen().koopprijs(DEFAULT_KOOPPRIJS);
        return koopwoningen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Koopwoningen createUpdatedEntity(EntityManager em) {
        Koopwoningen koopwoningen = new Koopwoningen().koopprijs(UPDATED_KOOPPRIJS);
        return koopwoningen;
    }

    @BeforeEach
    public void initTest() {
        koopwoningen = createEntity(em);
    }

    @Test
    @Transactional
    void createKoopwoningen() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Koopwoningen
        var returnedKoopwoningen = om.readValue(
            restKoopwoningenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(koopwoningen)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Koopwoningen.class
        );

        // Validate the Koopwoningen in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKoopwoningenUpdatableFieldsEquals(returnedKoopwoningen, getPersistedKoopwoningen(returnedKoopwoningen));
    }

    @Test
    @Transactional
    void createKoopwoningenWithExistingId() throws Exception {
        // Create the Koopwoningen with an existing ID
        koopwoningen.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKoopwoningenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(koopwoningen)))
            .andExpect(status().isBadRequest());

        // Validate the Koopwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKoopwoningens() throws Exception {
        // Initialize the database
        koopwoningenRepository.saveAndFlush(koopwoningen);

        // Get all the koopwoningenList
        restKoopwoningenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(koopwoningen.getId().intValue())))
            .andExpect(jsonPath("$.[*].koopprijs").value(hasItem(sameNumber(DEFAULT_KOOPPRIJS))));
    }

    @Test
    @Transactional
    void getKoopwoningen() throws Exception {
        // Initialize the database
        koopwoningenRepository.saveAndFlush(koopwoningen);

        // Get the koopwoningen
        restKoopwoningenMockMvc
            .perform(get(ENTITY_API_URL_ID, koopwoningen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(koopwoningen.getId().intValue()))
            .andExpect(jsonPath("$.koopprijs").value(sameNumber(DEFAULT_KOOPPRIJS)));
    }

    @Test
    @Transactional
    void getNonExistingKoopwoningen() throws Exception {
        // Get the koopwoningen
        restKoopwoningenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKoopwoningen() throws Exception {
        // Initialize the database
        koopwoningenRepository.saveAndFlush(koopwoningen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the koopwoningen
        Koopwoningen updatedKoopwoningen = koopwoningenRepository.findById(koopwoningen.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKoopwoningen are not directly saved in db
        em.detach(updatedKoopwoningen);
        updatedKoopwoningen.koopprijs(UPDATED_KOOPPRIJS);

        restKoopwoningenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKoopwoningen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKoopwoningen))
            )
            .andExpect(status().isOk());

        // Validate the Koopwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKoopwoningenToMatchAllProperties(updatedKoopwoningen);
    }

    @Test
    @Transactional
    void putNonExistingKoopwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koopwoningen.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKoopwoningenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, koopwoningen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(koopwoningen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Koopwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKoopwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koopwoningen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoopwoningenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(koopwoningen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Koopwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKoopwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koopwoningen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoopwoningenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(koopwoningen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Koopwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKoopwoningenWithPatch() throws Exception {
        // Initialize the database
        koopwoningenRepository.saveAndFlush(koopwoningen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the koopwoningen using partial update
        Koopwoningen partialUpdatedKoopwoningen = new Koopwoningen();
        partialUpdatedKoopwoningen.setId(koopwoningen.getId());

        partialUpdatedKoopwoningen.koopprijs(UPDATED_KOOPPRIJS);

        restKoopwoningenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKoopwoningen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKoopwoningen))
            )
            .andExpect(status().isOk());

        // Validate the Koopwoningen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKoopwoningenUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKoopwoningen, koopwoningen),
            getPersistedKoopwoningen(koopwoningen)
        );
    }

    @Test
    @Transactional
    void fullUpdateKoopwoningenWithPatch() throws Exception {
        // Initialize the database
        koopwoningenRepository.saveAndFlush(koopwoningen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the koopwoningen using partial update
        Koopwoningen partialUpdatedKoopwoningen = new Koopwoningen();
        partialUpdatedKoopwoningen.setId(koopwoningen.getId());

        partialUpdatedKoopwoningen.koopprijs(UPDATED_KOOPPRIJS);

        restKoopwoningenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKoopwoningen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKoopwoningen))
            )
            .andExpect(status().isOk());

        // Validate the Koopwoningen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKoopwoningenUpdatableFieldsEquals(partialUpdatedKoopwoningen, getPersistedKoopwoningen(partialUpdatedKoopwoningen));
    }

    @Test
    @Transactional
    void patchNonExistingKoopwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koopwoningen.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKoopwoningenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, koopwoningen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(koopwoningen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Koopwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKoopwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koopwoningen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoopwoningenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(koopwoningen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Koopwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKoopwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koopwoningen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoopwoningenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(koopwoningen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Koopwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKoopwoningen() throws Exception {
        // Initialize the database
        koopwoningenRepository.saveAndFlush(koopwoningen);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the koopwoningen
        restKoopwoningenMockMvc
            .perform(delete(ENTITY_API_URL_ID, koopwoningen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return koopwoningenRepository.count();
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

    protected Koopwoningen getPersistedKoopwoningen(Koopwoningen koopwoningen) {
        return koopwoningenRepository.findById(koopwoningen.getId()).orElseThrow();
    }

    protected void assertPersistedKoopwoningenToMatchAllProperties(Koopwoningen expectedKoopwoningen) {
        assertKoopwoningenAllPropertiesEquals(expectedKoopwoningen, getPersistedKoopwoningen(expectedKoopwoningen));
    }

    protected void assertPersistedKoopwoningenToMatchUpdatableProperties(Koopwoningen expectedKoopwoningen) {
        assertKoopwoningenAllUpdatablePropertiesEquals(expectedKoopwoningen, getPersistedKoopwoningen(expectedKoopwoningen));
    }
}
