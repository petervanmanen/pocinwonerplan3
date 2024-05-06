package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerkooppuntAsserts.*;
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
import nl.ritense.demo.domain.Verkooppunt;
import nl.ritense.demo.repository.VerkooppuntRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerkooppuntResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerkooppuntResourceIT {

    private static final String DEFAULT_WINKELFORMULE = "AAAAAAAAAA";
    private static final String UPDATED_WINKELFORMULE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verkooppunts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerkooppuntRepository verkooppuntRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerkooppuntMockMvc;

    private Verkooppunt verkooppunt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verkooppunt createEntity(EntityManager em) {
        Verkooppunt verkooppunt = new Verkooppunt().winkelformule(DEFAULT_WINKELFORMULE);
        return verkooppunt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verkooppunt createUpdatedEntity(EntityManager em) {
        Verkooppunt verkooppunt = new Verkooppunt().winkelformule(UPDATED_WINKELFORMULE);
        return verkooppunt;
    }

    @BeforeEach
    public void initTest() {
        verkooppunt = createEntity(em);
    }

    @Test
    @Transactional
    void createVerkooppunt() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verkooppunt
        var returnedVerkooppunt = om.readValue(
            restVerkooppuntMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verkooppunt)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verkooppunt.class
        );

        // Validate the Verkooppunt in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerkooppuntUpdatableFieldsEquals(returnedVerkooppunt, getPersistedVerkooppunt(returnedVerkooppunt));
    }

    @Test
    @Transactional
    void createVerkooppuntWithExistingId() throws Exception {
        // Create the Verkooppunt with an existing ID
        verkooppunt.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerkooppuntMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verkooppunt)))
            .andExpect(status().isBadRequest());

        // Validate the Verkooppunt in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerkooppunts() throws Exception {
        // Initialize the database
        verkooppuntRepository.saveAndFlush(verkooppunt);

        // Get all the verkooppuntList
        restVerkooppuntMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verkooppunt.getId().intValue())))
            .andExpect(jsonPath("$.[*].winkelformule").value(hasItem(DEFAULT_WINKELFORMULE)));
    }

    @Test
    @Transactional
    void getVerkooppunt() throws Exception {
        // Initialize the database
        verkooppuntRepository.saveAndFlush(verkooppunt);

        // Get the verkooppunt
        restVerkooppuntMockMvc
            .perform(get(ENTITY_API_URL_ID, verkooppunt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verkooppunt.getId().intValue()))
            .andExpect(jsonPath("$.winkelformule").value(DEFAULT_WINKELFORMULE));
    }

    @Test
    @Transactional
    void getNonExistingVerkooppunt() throws Exception {
        // Get the verkooppunt
        restVerkooppuntMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerkooppunt() throws Exception {
        // Initialize the database
        verkooppuntRepository.saveAndFlush(verkooppunt);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verkooppunt
        Verkooppunt updatedVerkooppunt = verkooppuntRepository.findById(verkooppunt.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerkooppunt are not directly saved in db
        em.detach(updatedVerkooppunt);
        updatedVerkooppunt.winkelformule(UPDATED_WINKELFORMULE);

        restVerkooppuntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerkooppunt.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerkooppunt))
            )
            .andExpect(status().isOk());

        // Validate the Verkooppunt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerkooppuntToMatchAllProperties(updatedVerkooppunt);
    }

    @Test
    @Transactional
    void putNonExistingVerkooppunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkooppunt.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerkooppuntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verkooppunt.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verkooppunt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verkooppunt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerkooppunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkooppunt.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerkooppuntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verkooppunt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verkooppunt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerkooppunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkooppunt.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerkooppuntMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verkooppunt)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verkooppunt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerkooppuntWithPatch() throws Exception {
        // Initialize the database
        verkooppuntRepository.saveAndFlush(verkooppunt);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verkooppunt using partial update
        Verkooppunt partialUpdatedVerkooppunt = new Verkooppunt();
        partialUpdatedVerkooppunt.setId(verkooppunt.getId());

        partialUpdatedVerkooppunt.winkelformule(UPDATED_WINKELFORMULE);

        restVerkooppuntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerkooppunt.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerkooppunt))
            )
            .andExpect(status().isOk());

        // Validate the Verkooppunt in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerkooppuntUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerkooppunt, verkooppunt),
            getPersistedVerkooppunt(verkooppunt)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerkooppuntWithPatch() throws Exception {
        // Initialize the database
        verkooppuntRepository.saveAndFlush(verkooppunt);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verkooppunt using partial update
        Verkooppunt partialUpdatedVerkooppunt = new Verkooppunt();
        partialUpdatedVerkooppunt.setId(verkooppunt.getId());

        partialUpdatedVerkooppunt.winkelformule(UPDATED_WINKELFORMULE);

        restVerkooppuntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerkooppunt.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerkooppunt))
            )
            .andExpect(status().isOk());

        // Validate the Verkooppunt in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerkooppuntUpdatableFieldsEquals(partialUpdatedVerkooppunt, getPersistedVerkooppunt(partialUpdatedVerkooppunt));
    }

    @Test
    @Transactional
    void patchNonExistingVerkooppunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkooppunt.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerkooppuntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verkooppunt.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verkooppunt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verkooppunt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerkooppunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkooppunt.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerkooppuntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verkooppunt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verkooppunt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerkooppunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkooppunt.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerkooppuntMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verkooppunt)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verkooppunt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerkooppunt() throws Exception {
        // Initialize the database
        verkooppuntRepository.saveAndFlush(verkooppunt);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verkooppunt
        restVerkooppuntMockMvc
            .perform(delete(ENTITY_API_URL_ID, verkooppunt.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verkooppuntRepository.count();
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

    protected Verkooppunt getPersistedVerkooppunt(Verkooppunt verkooppunt) {
        return verkooppuntRepository.findById(verkooppunt.getId()).orElseThrow();
    }

    protected void assertPersistedVerkooppuntToMatchAllProperties(Verkooppunt expectedVerkooppunt) {
        assertVerkooppuntAllPropertiesEquals(expectedVerkooppunt, getPersistedVerkooppunt(expectedVerkooppunt));
    }

    protected void assertPersistedVerkooppuntToMatchUpdatableProperties(Verkooppunt expectedVerkooppunt) {
        assertVerkooppuntAllUpdatablePropertiesEquals(expectedVerkooppunt, getPersistedVerkooppunt(expectedVerkooppunt));
    }
}
