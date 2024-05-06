package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.IdentificatiekenmerkAsserts.*;
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
import nl.ritense.demo.domain.Identificatiekenmerk;
import nl.ritense.demo.repository.IdentificatiekenmerkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IdentificatiekenmerkResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class IdentificatiekenmerkResourceIT {

    private static final String DEFAULT_KENMERK = "AAAAAAAAAA";
    private static final String UPDATED_KENMERK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/identificatiekenmerks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IdentificatiekenmerkRepository identificatiekenmerkRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIdentificatiekenmerkMockMvc;

    private Identificatiekenmerk identificatiekenmerk;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Identificatiekenmerk createEntity(EntityManager em) {
        Identificatiekenmerk identificatiekenmerk = new Identificatiekenmerk().kenmerk(DEFAULT_KENMERK);
        return identificatiekenmerk;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Identificatiekenmerk createUpdatedEntity(EntityManager em) {
        Identificatiekenmerk identificatiekenmerk = new Identificatiekenmerk().kenmerk(UPDATED_KENMERK);
        return identificatiekenmerk;
    }

    @BeforeEach
    public void initTest() {
        identificatiekenmerk = createEntity(em);
    }

    @Test
    @Transactional
    void createIdentificatiekenmerk() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Identificatiekenmerk
        var returnedIdentificatiekenmerk = om.readValue(
            restIdentificatiekenmerkMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(identificatiekenmerk)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Identificatiekenmerk.class
        );

        // Validate the Identificatiekenmerk in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertIdentificatiekenmerkUpdatableFieldsEquals(
            returnedIdentificatiekenmerk,
            getPersistedIdentificatiekenmerk(returnedIdentificatiekenmerk)
        );
    }

    @Test
    @Transactional
    void createIdentificatiekenmerkWithExistingId() throws Exception {
        // Create the Identificatiekenmerk with an existing ID
        identificatiekenmerk.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdentificatiekenmerkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(identificatiekenmerk)))
            .andExpect(status().isBadRequest());

        // Validate the Identificatiekenmerk in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIdentificatiekenmerks() throws Exception {
        // Initialize the database
        identificatiekenmerkRepository.saveAndFlush(identificatiekenmerk);

        // Get all the identificatiekenmerkList
        restIdentificatiekenmerkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(identificatiekenmerk.getId().intValue())))
            .andExpect(jsonPath("$.[*].kenmerk").value(hasItem(DEFAULT_KENMERK)));
    }

    @Test
    @Transactional
    void getIdentificatiekenmerk() throws Exception {
        // Initialize the database
        identificatiekenmerkRepository.saveAndFlush(identificatiekenmerk);

        // Get the identificatiekenmerk
        restIdentificatiekenmerkMockMvc
            .perform(get(ENTITY_API_URL_ID, identificatiekenmerk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(identificatiekenmerk.getId().intValue()))
            .andExpect(jsonPath("$.kenmerk").value(DEFAULT_KENMERK));
    }

    @Test
    @Transactional
    void getNonExistingIdentificatiekenmerk() throws Exception {
        // Get the identificatiekenmerk
        restIdentificatiekenmerkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIdentificatiekenmerk() throws Exception {
        // Initialize the database
        identificatiekenmerkRepository.saveAndFlush(identificatiekenmerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the identificatiekenmerk
        Identificatiekenmerk updatedIdentificatiekenmerk = identificatiekenmerkRepository
            .findById(identificatiekenmerk.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedIdentificatiekenmerk are not directly saved in db
        em.detach(updatedIdentificatiekenmerk);
        updatedIdentificatiekenmerk.kenmerk(UPDATED_KENMERK);

        restIdentificatiekenmerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIdentificatiekenmerk.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedIdentificatiekenmerk))
            )
            .andExpect(status().isOk());

        // Validate the Identificatiekenmerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIdentificatiekenmerkToMatchAllProperties(updatedIdentificatiekenmerk);
    }

    @Test
    @Transactional
    void putNonExistingIdentificatiekenmerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        identificatiekenmerk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdentificatiekenmerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, identificatiekenmerk.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(identificatiekenmerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Identificatiekenmerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIdentificatiekenmerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        identificatiekenmerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdentificatiekenmerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(identificatiekenmerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Identificatiekenmerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIdentificatiekenmerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        identificatiekenmerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdentificatiekenmerkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(identificatiekenmerk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Identificatiekenmerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIdentificatiekenmerkWithPatch() throws Exception {
        // Initialize the database
        identificatiekenmerkRepository.saveAndFlush(identificatiekenmerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the identificatiekenmerk using partial update
        Identificatiekenmerk partialUpdatedIdentificatiekenmerk = new Identificatiekenmerk();
        partialUpdatedIdentificatiekenmerk.setId(identificatiekenmerk.getId());

        partialUpdatedIdentificatiekenmerk.kenmerk(UPDATED_KENMERK);

        restIdentificatiekenmerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdentificatiekenmerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIdentificatiekenmerk))
            )
            .andExpect(status().isOk());

        // Validate the Identificatiekenmerk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIdentificatiekenmerkUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedIdentificatiekenmerk, identificatiekenmerk),
            getPersistedIdentificatiekenmerk(identificatiekenmerk)
        );
    }

    @Test
    @Transactional
    void fullUpdateIdentificatiekenmerkWithPatch() throws Exception {
        // Initialize the database
        identificatiekenmerkRepository.saveAndFlush(identificatiekenmerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the identificatiekenmerk using partial update
        Identificatiekenmerk partialUpdatedIdentificatiekenmerk = new Identificatiekenmerk();
        partialUpdatedIdentificatiekenmerk.setId(identificatiekenmerk.getId());

        partialUpdatedIdentificatiekenmerk.kenmerk(UPDATED_KENMERK);

        restIdentificatiekenmerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdentificatiekenmerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIdentificatiekenmerk))
            )
            .andExpect(status().isOk());

        // Validate the Identificatiekenmerk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIdentificatiekenmerkUpdatableFieldsEquals(
            partialUpdatedIdentificatiekenmerk,
            getPersistedIdentificatiekenmerk(partialUpdatedIdentificatiekenmerk)
        );
    }

    @Test
    @Transactional
    void patchNonExistingIdentificatiekenmerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        identificatiekenmerk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdentificatiekenmerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, identificatiekenmerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(identificatiekenmerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Identificatiekenmerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIdentificatiekenmerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        identificatiekenmerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdentificatiekenmerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(identificatiekenmerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Identificatiekenmerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIdentificatiekenmerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        identificatiekenmerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdentificatiekenmerkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(identificatiekenmerk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Identificatiekenmerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIdentificatiekenmerk() throws Exception {
        // Initialize the database
        identificatiekenmerkRepository.saveAndFlush(identificatiekenmerk);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the identificatiekenmerk
        restIdentificatiekenmerkMockMvc
            .perform(delete(ENTITY_API_URL_ID, identificatiekenmerk.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return identificatiekenmerkRepository.count();
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

    protected Identificatiekenmerk getPersistedIdentificatiekenmerk(Identificatiekenmerk identificatiekenmerk) {
        return identificatiekenmerkRepository.findById(identificatiekenmerk.getId()).orElseThrow();
    }

    protected void assertPersistedIdentificatiekenmerkToMatchAllProperties(Identificatiekenmerk expectedIdentificatiekenmerk) {
        assertIdentificatiekenmerkAllPropertiesEquals(
            expectedIdentificatiekenmerk,
            getPersistedIdentificatiekenmerk(expectedIdentificatiekenmerk)
        );
    }

    protected void assertPersistedIdentificatiekenmerkToMatchUpdatableProperties(Identificatiekenmerk expectedIdentificatiekenmerk) {
        assertIdentificatiekenmerkAllUpdatablePropertiesEquals(
            expectedIdentificatiekenmerk,
            getPersistedIdentificatiekenmerk(expectedIdentificatiekenmerk)
        );
    }
}
