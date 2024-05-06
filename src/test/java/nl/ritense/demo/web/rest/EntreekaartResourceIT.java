package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EntreekaartAsserts.*;
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
import nl.ritense.demo.domain.Entreekaart;
import nl.ritense.demo.repository.EntreekaartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EntreekaartResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EntreekaartResourceIT {

    private static final String DEFAULT_RONDLEIDING = "AAAAAAAAAA";
    private static final String UPDATED_RONDLEIDING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/entreekaarts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EntreekaartRepository entreekaartRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntreekaartMockMvc;

    private Entreekaart entreekaart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entreekaart createEntity(EntityManager em) {
        Entreekaart entreekaart = new Entreekaart().rondleiding(DEFAULT_RONDLEIDING);
        return entreekaart;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entreekaart createUpdatedEntity(EntityManager em) {
        Entreekaart entreekaart = new Entreekaart().rondleiding(UPDATED_RONDLEIDING);
        return entreekaart;
    }

    @BeforeEach
    public void initTest() {
        entreekaart = createEntity(em);
    }

    @Test
    @Transactional
    void createEntreekaart() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Entreekaart
        var returnedEntreekaart = om.readValue(
            restEntreekaartMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entreekaart)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Entreekaart.class
        );

        // Validate the Entreekaart in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEntreekaartUpdatableFieldsEquals(returnedEntreekaart, getPersistedEntreekaart(returnedEntreekaart));
    }

    @Test
    @Transactional
    void createEntreekaartWithExistingId() throws Exception {
        // Create the Entreekaart with an existing ID
        entreekaart.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntreekaartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entreekaart)))
            .andExpect(status().isBadRequest());

        // Validate the Entreekaart in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEntreekaarts() throws Exception {
        // Initialize the database
        entreekaartRepository.saveAndFlush(entreekaart);

        // Get all the entreekaartList
        restEntreekaartMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entreekaart.getId().intValue())))
            .andExpect(jsonPath("$.[*].rondleiding").value(hasItem(DEFAULT_RONDLEIDING)));
    }

    @Test
    @Transactional
    void getEntreekaart() throws Exception {
        // Initialize the database
        entreekaartRepository.saveAndFlush(entreekaart);

        // Get the entreekaart
        restEntreekaartMockMvc
            .perform(get(ENTITY_API_URL_ID, entreekaart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entreekaart.getId().intValue()))
            .andExpect(jsonPath("$.rondleiding").value(DEFAULT_RONDLEIDING));
    }

    @Test
    @Transactional
    void getNonExistingEntreekaart() throws Exception {
        // Get the entreekaart
        restEntreekaartMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEntreekaart() throws Exception {
        // Initialize the database
        entreekaartRepository.saveAndFlush(entreekaart);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entreekaart
        Entreekaart updatedEntreekaart = entreekaartRepository.findById(entreekaart.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEntreekaart are not directly saved in db
        em.detach(updatedEntreekaart);
        updatedEntreekaart.rondleiding(UPDATED_RONDLEIDING);

        restEntreekaartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEntreekaart.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEntreekaart))
            )
            .andExpect(status().isOk());

        // Validate the Entreekaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEntreekaartToMatchAllProperties(updatedEntreekaart);
    }

    @Test
    @Transactional
    void putNonExistingEntreekaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entreekaart.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntreekaartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entreekaart.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(entreekaart))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreekaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntreekaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entreekaart.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntreekaartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(entreekaart))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreekaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntreekaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entreekaart.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntreekaartMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entreekaart)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entreekaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntreekaartWithPatch() throws Exception {
        // Initialize the database
        entreekaartRepository.saveAndFlush(entreekaart);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entreekaart using partial update
        Entreekaart partialUpdatedEntreekaart = new Entreekaart();
        partialUpdatedEntreekaart.setId(entreekaart.getId());

        partialUpdatedEntreekaart.rondleiding(UPDATED_RONDLEIDING);

        restEntreekaartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntreekaart.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEntreekaart))
            )
            .andExpect(status().isOk());

        // Validate the Entreekaart in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEntreekaartUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEntreekaart, entreekaart),
            getPersistedEntreekaart(entreekaart)
        );
    }

    @Test
    @Transactional
    void fullUpdateEntreekaartWithPatch() throws Exception {
        // Initialize the database
        entreekaartRepository.saveAndFlush(entreekaart);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entreekaart using partial update
        Entreekaart partialUpdatedEntreekaart = new Entreekaart();
        partialUpdatedEntreekaart.setId(entreekaart.getId());

        partialUpdatedEntreekaart.rondleiding(UPDATED_RONDLEIDING);

        restEntreekaartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntreekaart.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEntreekaart))
            )
            .andExpect(status().isOk());

        // Validate the Entreekaart in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEntreekaartUpdatableFieldsEquals(partialUpdatedEntreekaart, getPersistedEntreekaart(partialUpdatedEntreekaart));
    }

    @Test
    @Transactional
    void patchNonExistingEntreekaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entreekaart.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntreekaartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entreekaart.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(entreekaart))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreekaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntreekaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entreekaart.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntreekaartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(entreekaart))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreekaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntreekaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entreekaart.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntreekaartMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(entreekaart)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entreekaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntreekaart() throws Exception {
        // Initialize the database
        entreekaartRepository.saveAndFlush(entreekaart);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the entreekaart
        restEntreekaartMockMvc
            .perform(delete(ENTITY_API_URL_ID, entreekaart.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return entreekaartRepository.count();
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

    protected Entreekaart getPersistedEntreekaart(Entreekaart entreekaart) {
        return entreekaartRepository.findById(entreekaart.getId()).orElseThrow();
    }

    protected void assertPersistedEntreekaartToMatchAllProperties(Entreekaart expectedEntreekaart) {
        assertEntreekaartAllPropertiesEquals(expectedEntreekaart, getPersistedEntreekaart(expectedEntreekaart));
    }

    protected void assertPersistedEntreekaartToMatchUpdatableProperties(Entreekaart expectedEntreekaart) {
        assertEntreekaartAllUpdatablePropertiesEquals(expectedEntreekaart, getPersistedEntreekaart(expectedEntreekaart));
    }
}
