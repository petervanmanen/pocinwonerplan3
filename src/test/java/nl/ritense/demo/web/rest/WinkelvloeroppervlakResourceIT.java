package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WinkelvloeroppervlakAsserts.*;
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
import nl.ritense.demo.domain.Winkelvloeroppervlak;
import nl.ritense.demo.repository.WinkelvloeroppervlakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WinkelvloeroppervlakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WinkelvloeroppervlakResourceIT {

    private static final String DEFAULT_AANTALKASSA = "AAAAAAAAAA";
    private static final String UPDATED_AANTALKASSA = "BBBBBBBBBB";

    private static final String DEFAULT_BRONWVO = "AAAAAAAAAA";
    private static final String UPDATED_BRONWVO = "BBBBBBBBBB";

    private static final String DEFAULT_LEEGSTAND = "AAAAAAAAAA";
    private static final String UPDATED_LEEGSTAND = "BBBBBBBBBB";

    private static final String DEFAULT_WINKELVLOEROPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_WINKELVLOEROPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_WVOKLASSE = "AAAAAAAAAA";
    private static final String UPDATED_WVOKLASSE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/winkelvloeroppervlaks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WinkelvloeroppervlakRepository winkelvloeroppervlakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWinkelvloeroppervlakMockMvc;

    private Winkelvloeroppervlak winkelvloeroppervlak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Winkelvloeroppervlak createEntity(EntityManager em) {
        Winkelvloeroppervlak winkelvloeroppervlak = new Winkelvloeroppervlak()
            .aantalkassa(DEFAULT_AANTALKASSA)
            .bronwvo(DEFAULT_BRONWVO)
            .leegstand(DEFAULT_LEEGSTAND)
            .winkelvloeroppervlakte(DEFAULT_WINKELVLOEROPPERVLAKTE)
            .wvoklasse(DEFAULT_WVOKLASSE);
        return winkelvloeroppervlak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Winkelvloeroppervlak createUpdatedEntity(EntityManager em) {
        Winkelvloeroppervlak winkelvloeroppervlak = new Winkelvloeroppervlak()
            .aantalkassa(UPDATED_AANTALKASSA)
            .bronwvo(UPDATED_BRONWVO)
            .leegstand(UPDATED_LEEGSTAND)
            .winkelvloeroppervlakte(UPDATED_WINKELVLOEROPPERVLAKTE)
            .wvoklasse(UPDATED_WVOKLASSE);
        return winkelvloeroppervlak;
    }

    @BeforeEach
    public void initTest() {
        winkelvloeroppervlak = createEntity(em);
    }

    @Test
    @Transactional
    void createWinkelvloeroppervlak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Winkelvloeroppervlak
        var returnedWinkelvloeroppervlak = om.readValue(
            restWinkelvloeroppervlakMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(winkelvloeroppervlak)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Winkelvloeroppervlak.class
        );

        // Validate the Winkelvloeroppervlak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWinkelvloeroppervlakUpdatableFieldsEquals(
            returnedWinkelvloeroppervlak,
            getPersistedWinkelvloeroppervlak(returnedWinkelvloeroppervlak)
        );
    }

    @Test
    @Transactional
    void createWinkelvloeroppervlakWithExistingId() throws Exception {
        // Create the Winkelvloeroppervlak with an existing ID
        winkelvloeroppervlak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWinkelvloeroppervlakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(winkelvloeroppervlak)))
            .andExpect(status().isBadRequest());

        // Validate the Winkelvloeroppervlak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWinkelvloeroppervlaks() throws Exception {
        // Initialize the database
        winkelvloeroppervlakRepository.saveAndFlush(winkelvloeroppervlak);

        // Get all the winkelvloeroppervlakList
        restWinkelvloeroppervlakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(winkelvloeroppervlak.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantalkassa").value(hasItem(DEFAULT_AANTALKASSA)))
            .andExpect(jsonPath("$.[*].bronwvo").value(hasItem(DEFAULT_BRONWVO)))
            .andExpect(jsonPath("$.[*].leegstand").value(hasItem(DEFAULT_LEEGSTAND)))
            .andExpect(jsonPath("$.[*].winkelvloeroppervlakte").value(hasItem(DEFAULT_WINKELVLOEROPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].wvoklasse").value(hasItem(DEFAULT_WVOKLASSE)));
    }

    @Test
    @Transactional
    void getWinkelvloeroppervlak() throws Exception {
        // Initialize the database
        winkelvloeroppervlakRepository.saveAndFlush(winkelvloeroppervlak);

        // Get the winkelvloeroppervlak
        restWinkelvloeroppervlakMockMvc
            .perform(get(ENTITY_API_URL_ID, winkelvloeroppervlak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(winkelvloeroppervlak.getId().intValue()))
            .andExpect(jsonPath("$.aantalkassa").value(DEFAULT_AANTALKASSA))
            .andExpect(jsonPath("$.bronwvo").value(DEFAULT_BRONWVO))
            .andExpect(jsonPath("$.leegstand").value(DEFAULT_LEEGSTAND))
            .andExpect(jsonPath("$.winkelvloeroppervlakte").value(DEFAULT_WINKELVLOEROPPERVLAKTE))
            .andExpect(jsonPath("$.wvoklasse").value(DEFAULT_WVOKLASSE));
    }

    @Test
    @Transactional
    void getNonExistingWinkelvloeroppervlak() throws Exception {
        // Get the winkelvloeroppervlak
        restWinkelvloeroppervlakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWinkelvloeroppervlak() throws Exception {
        // Initialize the database
        winkelvloeroppervlakRepository.saveAndFlush(winkelvloeroppervlak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the winkelvloeroppervlak
        Winkelvloeroppervlak updatedWinkelvloeroppervlak = winkelvloeroppervlakRepository
            .findById(winkelvloeroppervlak.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedWinkelvloeroppervlak are not directly saved in db
        em.detach(updatedWinkelvloeroppervlak);
        updatedWinkelvloeroppervlak
            .aantalkassa(UPDATED_AANTALKASSA)
            .bronwvo(UPDATED_BRONWVO)
            .leegstand(UPDATED_LEEGSTAND)
            .winkelvloeroppervlakte(UPDATED_WINKELVLOEROPPERVLAKTE)
            .wvoklasse(UPDATED_WVOKLASSE);

        restWinkelvloeroppervlakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWinkelvloeroppervlak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWinkelvloeroppervlak))
            )
            .andExpect(status().isOk());

        // Validate the Winkelvloeroppervlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWinkelvloeroppervlakToMatchAllProperties(updatedWinkelvloeroppervlak);
    }

    @Test
    @Transactional
    void putNonExistingWinkelvloeroppervlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        winkelvloeroppervlak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWinkelvloeroppervlakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, winkelvloeroppervlak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(winkelvloeroppervlak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Winkelvloeroppervlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWinkelvloeroppervlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        winkelvloeroppervlak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWinkelvloeroppervlakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(winkelvloeroppervlak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Winkelvloeroppervlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWinkelvloeroppervlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        winkelvloeroppervlak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWinkelvloeroppervlakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(winkelvloeroppervlak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Winkelvloeroppervlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWinkelvloeroppervlakWithPatch() throws Exception {
        // Initialize the database
        winkelvloeroppervlakRepository.saveAndFlush(winkelvloeroppervlak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the winkelvloeroppervlak using partial update
        Winkelvloeroppervlak partialUpdatedWinkelvloeroppervlak = new Winkelvloeroppervlak();
        partialUpdatedWinkelvloeroppervlak.setId(winkelvloeroppervlak.getId());

        partialUpdatedWinkelvloeroppervlak.aantalkassa(UPDATED_AANTALKASSA);

        restWinkelvloeroppervlakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWinkelvloeroppervlak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWinkelvloeroppervlak))
            )
            .andExpect(status().isOk());

        // Validate the Winkelvloeroppervlak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWinkelvloeroppervlakUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWinkelvloeroppervlak, winkelvloeroppervlak),
            getPersistedWinkelvloeroppervlak(winkelvloeroppervlak)
        );
    }

    @Test
    @Transactional
    void fullUpdateWinkelvloeroppervlakWithPatch() throws Exception {
        // Initialize the database
        winkelvloeroppervlakRepository.saveAndFlush(winkelvloeroppervlak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the winkelvloeroppervlak using partial update
        Winkelvloeroppervlak partialUpdatedWinkelvloeroppervlak = new Winkelvloeroppervlak();
        partialUpdatedWinkelvloeroppervlak.setId(winkelvloeroppervlak.getId());

        partialUpdatedWinkelvloeroppervlak
            .aantalkassa(UPDATED_AANTALKASSA)
            .bronwvo(UPDATED_BRONWVO)
            .leegstand(UPDATED_LEEGSTAND)
            .winkelvloeroppervlakte(UPDATED_WINKELVLOEROPPERVLAKTE)
            .wvoklasse(UPDATED_WVOKLASSE);

        restWinkelvloeroppervlakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWinkelvloeroppervlak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWinkelvloeroppervlak))
            )
            .andExpect(status().isOk());

        // Validate the Winkelvloeroppervlak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWinkelvloeroppervlakUpdatableFieldsEquals(
            partialUpdatedWinkelvloeroppervlak,
            getPersistedWinkelvloeroppervlak(partialUpdatedWinkelvloeroppervlak)
        );
    }

    @Test
    @Transactional
    void patchNonExistingWinkelvloeroppervlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        winkelvloeroppervlak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWinkelvloeroppervlakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, winkelvloeroppervlak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(winkelvloeroppervlak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Winkelvloeroppervlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWinkelvloeroppervlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        winkelvloeroppervlak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWinkelvloeroppervlakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(winkelvloeroppervlak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Winkelvloeroppervlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWinkelvloeroppervlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        winkelvloeroppervlak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWinkelvloeroppervlakMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(winkelvloeroppervlak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Winkelvloeroppervlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWinkelvloeroppervlak() throws Exception {
        // Initialize the database
        winkelvloeroppervlakRepository.saveAndFlush(winkelvloeroppervlak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the winkelvloeroppervlak
        restWinkelvloeroppervlakMockMvc
            .perform(delete(ENTITY_API_URL_ID, winkelvloeroppervlak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return winkelvloeroppervlakRepository.count();
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

    protected Winkelvloeroppervlak getPersistedWinkelvloeroppervlak(Winkelvloeroppervlak winkelvloeroppervlak) {
        return winkelvloeroppervlakRepository.findById(winkelvloeroppervlak.getId()).orElseThrow();
    }

    protected void assertPersistedWinkelvloeroppervlakToMatchAllProperties(Winkelvloeroppervlak expectedWinkelvloeroppervlak) {
        assertWinkelvloeroppervlakAllPropertiesEquals(
            expectedWinkelvloeroppervlak,
            getPersistedWinkelvloeroppervlak(expectedWinkelvloeroppervlak)
        );
    }

    protected void assertPersistedWinkelvloeroppervlakToMatchUpdatableProperties(Winkelvloeroppervlak expectedWinkelvloeroppervlak) {
        assertWinkelvloeroppervlakAllUpdatablePropertiesEquals(
            expectedWinkelvloeroppervlak,
            getPersistedWinkelvloeroppervlak(expectedWinkelvloeroppervlak)
        );
    }
}
