package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BalieverkoopAsserts.*;
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
import nl.ritense.demo.domain.Balieverkoop;
import nl.ritense.demo.repository.BalieverkoopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BalieverkoopResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BalieverkoopResourceIT {

    private static final String DEFAULT_AANTAL = "AAAAAAAAAA";
    private static final String UPDATED_AANTAL = "BBBBBBBBBB";

    private static final String DEFAULT_KANAAL = "AAAAAAAAAA";
    private static final String UPDATED_KANAAL = "BBBBBBBBBB";

    private static final String DEFAULT_VERKOOPTIJD = "AAAAAAAAAA";
    private static final String UPDATED_VERKOOPTIJD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/balieverkoops";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BalieverkoopRepository balieverkoopRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBalieverkoopMockMvc;

    private Balieverkoop balieverkoop;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Balieverkoop createEntity(EntityManager em) {
        Balieverkoop balieverkoop = new Balieverkoop().aantal(DEFAULT_AANTAL).kanaal(DEFAULT_KANAAL).verkooptijd(DEFAULT_VERKOOPTIJD);
        return balieverkoop;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Balieverkoop createUpdatedEntity(EntityManager em) {
        Balieverkoop balieverkoop = new Balieverkoop().aantal(UPDATED_AANTAL).kanaal(UPDATED_KANAAL).verkooptijd(UPDATED_VERKOOPTIJD);
        return balieverkoop;
    }

    @BeforeEach
    public void initTest() {
        balieverkoop = createEntity(em);
    }

    @Test
    @Transactional
    void createBalieverkoop() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Balieverkoop
        var returnedBalieverkoop = om.readValue(
            restBalieverkoopMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(balieverkoop)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Balieverkoop.class
        );

        // Validate the Balieverkoop in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBalieverkoopUpdatableFieldsEquals(returnedBalieverkoop, getPersistedBalieverkoop(returnedBalieverkoop));
    }

    @Test
    @Transactional
    void createBalieverkoopWithExistingId() throws Exception {
        // Create the Balieverkoop with an existing ID
        balieverkoop.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBalieverkoopMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(balieverkoop)))
            .andExpect(status().isBadRequest());

        // Validate the Balieverkoop in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBalieverkoops() throws Exception {
        // Initialize the database
        balieverkoopRepository.saveAndFlush(balieverkoop);

        // Get all the balieverkoopList
        restBalieverkoopMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(balieverkoop.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantal").value(hasItem(DEFAULT_AANTAL)))
            .andExpect(jsonPath("$.[*].kanaal").value(hasItem(DEFAULT_KANAAL)))
            .andExpect(jsonPath("$.[*].verkooptijd").value(hasItem(DEFAULT_VERKOOPTIJD)));
    }

    @Test
    @Transactional
    void getBalieverkoop() throws Exception {
        // Initialize the database
        balieverkoopRepository.saveAndFlush(balieverkoop);

        // Get the balieverkoop
        restBalieverkoopMockMvc
            .perform(get(ENTITY_API_URL_ID, balieverkoop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(balieverkoop.getId().intValue()))
            .andExpect(jsonPath("$.aantal").value(DEFAULT_AANTAL))
            .andExpect(jsonPath("$.kanaal").value(DEFAULT_KANAAL))
            .andExpect(jsonPath("$.verkooptijd").value(DEFAULT_VERKOOPTIJD));
    }

    @Test
    @Transactional
    void getNonExistingBalieverkoop() throws Exception {
        // Get the balieverkoop
        restBalieverkoopMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBalieverkoop() throws Exception {
        // Initialize the database
        balieverkoopRepository.saveAndFlush(balieverkoop);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the balieverkoop
        Balieverkoop updatedBalieverkoop = balieverkoopRepository.findById(balieverkoop.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBalieverkoop are not directly saved in db
        em.detach(updatedBalieverkoop);
        updatedBalieverkoop.aantal(UPDATED_AANTAL).kanaal(UPDATED_KANAAL).verkooptijd(UPDATED_VERKOOPTIJD);

        restBalieverkoopMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBalieverkoop.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBalieverkoop))
            )
            .andExpect(status().isOk());

        // Validate the Balieverkoop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBalieverkoopToMatchAllProperties(updatedBalieverkoop);
    }

    @Test
    @Transactional
    void putNonExistingBalieverkoop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieverkoop.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBalieverkoopMockMvc
            .perform(
                put(ENTITY_API_URL_ID, balieverkoop.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(balieverkoop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Balieverkoop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBalieverkoop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieverkoop.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalieverkoopMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(balieverkoop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Balieverkoop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBalieverkoop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieverkoop.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalieverkoopMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(balieverkoop)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Balieverkoop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBalieverkoopWithPatch() throws Exception {
        // Initialize the database
        balieverkoopRepository.saveAndFlush(balieverkoop);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the balieverkoop using partial update
        Balieverkoop partialUpdatedBalieverkoop = new Balieverkoop();
        partialUpdatedBalieverkoop.setId(balieverkoop.getId());

        restBalieverkoopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBalieverkoop.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBalieverkoop))
            )
            .andExpect(status().isOk());

        // Validate the Balieverkoop in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBalieverkoopUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBalieverkoop, balieverkoop),
            getPersistedBalieverkoop(balieverkoop)
        );
    }

    @Test
    @Transactional
    void fullUpdateBalieverkoopWithPatch() throws Exception {
        // Initialize the database
        balieverkoopRepository.saveAndFlush(balieverkoop);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the balieverkoop using partial update
        Balieverkoop partialUpdatedBalieverkoop = new Balieverkoop();
        partialUpdatedBalieverkoop.setId(balieverkoop.getId());

        partialUpdatedBalieverkoop.aantal(UPDATED_AANTAL).kanaal(UPDATED_KANAAL).verkooptijd(UPDATED_VERKOOPTIJD);

        restBalieverkoopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBalieverkoop.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBalieverkoop))
            )
            .andExpect(status().isOk());

        // Validate the Balieverkoop in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBalieverkoopUpdatableFieldsEquals(partialUpdatedBalieverkoop, getPersistedBalieverkoop(partialUpdatedBalieverkoop));
    }

    @Test
    @Transactional
    void patchNonExistingBalieverkoop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieverkoop.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBalieverkoopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, balieverkoop.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(balieverkoop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Balieverkoop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBalieverkoop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieverkoop.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalieverkoopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(balieverkoop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Balieverkoop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBalieverkoop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieverkoop.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalieverkoopMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(balieverkoop)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Balieverkoop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBalieverkoop() throws Exception {
        // Initialize the database
        balieverkoopRepository.saveAndFlush(balieverkoop);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the balieverkoop
        restBalieverkoopMockMvc
            .perform(delete(ENTITY_API_URL_ID, balieverkoop.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return balieverkoopRepository.count();
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

    protected Balieverkoop getPersistedBalieverkoop(Balieverkoop balieverkoop) {
        return balieverkoopRepository.findById(balieverkoop.getId()).orElseThrow();
    }

    protected void assertPersistedBalieverkoopToMatchAllProperties(Balieverkoop expectedBalieverkoop) {
        assertBalieverkoopAllPropertiesEquals(expectedBalieverkoop, getPersistedBalieverkoop(expectedBalieverkoop));
    }

    protected void assertPersistedBalieverkoopToMatchUpdatableProperties(Balieverkoop expectedBalieverkoop) {
        assertBalieverkoopAllUpdatablePropertiesEquals(expectedBalieverkoop, getPersistedBalieverkoop(expectedBalieverkoop));
    }
}
