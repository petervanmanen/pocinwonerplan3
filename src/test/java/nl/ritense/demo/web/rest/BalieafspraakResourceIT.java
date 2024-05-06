package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BalieafspraakAsserts.*;
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
import nl.ritense.demo.domain.Balieafspraak;
import nl.ritense.demo.repository.BalieafspraakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BalieafspraakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BalieafspraakResourceIT {

    private static final String DEFAULT_EINDTIJDGEPLAND = "AAAAAAAAAA";
    private static final String UPDATED_EINDTIJDGEPLAND = "BBBBBBBBBB";

    private static final String DEFAULT_NOTITIE = "AAAAAAAAAA";
    private static final String UPDATED_NOTITIE = "BBBBBBBBBB";

    private static final String DEFAULT_STARTTIJDGEPLAND = "AAAAAAAAAA";
    private static final String UPDATED_STARTTIJDGEPLAND = "BBBBBBBBBB";

    private static final String DEFAULT_TIJDAANGEMAAKT = "AAAAAAAAAA";
    private static final String UPDATED_TIJDAANGEMAAKT = "BBBBBBBBBB";

    private static final String DEFAULT_TIJDSDUURGEPLAND = "AAAAAAAAAA";
    private static final String UPDATED_TIJDSDUURGEPLAND = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String DEFAULT_WACHTTIJDNASTARTAFSPRAAK = "AAAAAAAAAA";
    private static final String UPDATED_WACHTTIJDNASTARTAFSPRAAK = "BBBBBBBBBB";

    private static final String DEFAULT_WACHTTIJDTOTAAL = "AAAAAAAAAA";
    private static final String UPDATED_WACHTTIJDTOTAAL = "BBBBBBBBBB";

    private static final String DEFAULT_WACHTTIJDVOORSTARTAFSPRAAK = "AAAAAAAAAA";
    private static final String UPDATED_WACHTTIJDVOORSTARTAFSPRAAK = "BBBBBBBBBB";

    private static final String DEFAULT_WERKELIJKETIJDSDUUR = "AAAAAAAAAA";
    private static final String UPDATED_WERKELIJKETIJDSDUUR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/balieafspraaks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BalieafspraakRepository balieafspraakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBalieafspraakMockMvc;

    private Balieafspraak balieafspraak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Balieafspraak createEntity(EntityManager em) {
        Balieafspraak balieafspraak = new Balieafspraak()
            .eindtijdgepland(DEFAULT_EINDTIJDGEPLAND)
            .notitie(DEFAULT_NOTITIE)
            .starttijdgepland(DEFAULT_STARTTIJDGEPLAND)
            .tijdaangemaakt(DEFAULT_TIJDAANGEMAAKT)
            .tijdsduurgepland(DEFAULT_TIJDSDUURGEPLAND)
            .toelichting(DEFAULT_TOELICHTING)
            .wachttijdnastartafspraak(DEFAULT_WACHTTIJDNASTARTAFSPRAAK)
            .wachttijdtotaal(DEFAULT_WACHTTIJDTOTAAL)
            .wachttijdvoorstartafspraak(DEFAULT_WACHTTIJDVOORSTARTAFSPRAAK)
            .werkelijketijdsduur(DEFAULT_WERKELIJKETIJDSDUUR);
        return balieafspraak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Balieafspraak createUpdatedEntity(EntityManager em) {
        Balieafspraak balieafspraak = new Balieafspraak()
            .eindtijdgepland(UPDATED_EINDTIJDGEPLAND)
            .notitie(UPDATED_NOTITIE)
            .starttijdgepland(UPDATED_STARTTIJDGEPLAND)
            .tijdaangemaakt(UPDATED_TIJDAANGEMAAKT)
            .tijdsduurgepland(UPDATED_TIJDSDUURGEPLAND)
            .toelichting(UPDATED_TOELICHTING)
            .wachttijdnastartafspraak(UPDATED_WACHTTIJDNASTARTAFSPRAAK)
            .wachttijdtotaal(UPDATED_WACHTTIJDTOTAAL)
            .wachttijdvoorstartafspraak(UPDATED_WACHTTIJDVOORSTARTAFSPRAAK)
            .werkelijketijdsduur(UPDATED_WERKELIJKETIJDSDUUR);
        return balieafspraak;
    }

    @BeforeEach
    public void initTest() {
        balieafspraak = createEntity(em);
    }

    @Test
    @Transactional
    void createBalieafspraak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Balieafspraak
        var returnedBalieafspraak = om.readValue(
            restBalieafspraakMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(balieafspraak)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Balieafspraak.class
        );

        // Validate the Balieafspraak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBalieafspraakUpdatableFieldsEquals(returnedBalieafspraak, getPersistedBalieafspraak(returnedBalieafspraak));
    }

    @Test
    @Transactional
    void createBalieafspraakWithExistingId() throws Exception {
        // Create the Balieafspraak with an existing ID
        balieafspraak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBalieafspraakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(balieafspraak)))
            .andExpect(status().isBadRequest());

        // Validate the Balieafspraak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBalieafspraaks() throws Exception {
        // Initialize the database
        balieafspraakRepository.saveAndFlush(balieafspraak);

        // Get all the balieafspraakList
        restBalieafspraakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(balieafspraak.getId().intValue())))
            .andExpect(jsonPath("$.[*].eindtijdgepland").value(hasItem(DEFAULT_EINDTIJDGEPLAND)))
            .andExpect(jsonPath("$.[*].notitie").value(hasItem(DEFAULT_NOTITIE)))
            .andExpect(jsonPath("$.[*].starttijdgepland").value(hasItem(DEFAULT_STARTTIJDGEPLAND)))
            .andExpect(jsonPath("$.[*].tijdaangemaakt").value(hasItem(DEFAULT_TIJDAANGEMAAKT)))
            .andExpect(jsonPath("$.[*].tijdsduurgepland").value(hasItem(DEFAULT_TIJDSDUURGEPLAND)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)))
            .andExpect(jsonPath("$.[*].wachttijdnastartafspraak").value(hasItem(DEFAULT_WACHTTIJDNASTARTAFSPRAAK)))
            .andExpect(jsonPath("$.[*].wachttijdtotaal").value(hasItem(DEFAULT_WACHTTIJDTOTAAL)))
            .andExpect(jsonPath("$.[*].wachttijdvoorstartafspraak").value(hasItem(DEFAULT_WACHTTIJDVOORSTARTAFSPRAAK)))
            .andExpect(jsonPath("$.[*].werkelijketijdsduur").value(hasItem(DEFAULT_WERKELIJKETIJDSDUUR)));
    }

    @Test
    @Transactional
    void getBalieafspraak() throws Exception {
        // Initialize the database
        balieafspraakRepository.saveAndFlush(balieafspraak);

        // Get the balieafspraak
        restBalieafspraakMockMvc
            .perform(get(ENTITY_API_URL_ID, balieafspraak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(balieafspraak.getId().intValue()))
            .andExpect(jsonPath("$.eindtijdgepland").value(DEFAULT_EINDTIJDGEPLAND))
            .andExpect(jsonPath("$.notitie").value(DEFAULT_NOTITIE))
            .andExpect(jsonPath("$.starttijdgepland").value(DEFAULT_STARTTIJDGEPLAND))
            .andExpect(jsonPath("$.tijdaangemaakt").value(DEFAULT_TIJDAANGEMAAKT))
            .andExpect(jsonPath("$.tijdsduurgepland").value(DEFAULT_TIJDSDUURGEPLAND))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING))
            .andExpect(jsonPath("$.wachttijdnastartafspraak").value(DEFAULT_WACHTTIJDNASTARTAFSPRAAK))
            .andExpect(jsonPath("$.wachttijdtotaal").value(DEFAULT_WACHTTIJDTOTAAL))
            .andExpect(jsonPath("$.wachttijdvoorstartafspraak").value(DEFAULT_WACHTTIJDVOORSTARTAFSPRAAK))
            .andExpect(jsonPath("$.werkelijketijdsduur").value(DEFAULT_WERKELIJKETIJDSDUUR));
    }

    @Test
    @Transactional
    void getNonExistingBalieafspraak() throws Exception {
        // Get the balieafspraak
        restBalieafspraakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBalieafspraak() throws Exception {
        // Initialize the database
        balieafspraakRepository.saveAndFlush(balieafspraak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the balieafspraak
        Balieafspraak updatedBalieafspraak = balieafspraakRepository.findById(balieafspraak.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBalieafspraak are not directly saved in db
        em.detach(updatedBalieafspraak);
        updatedBalieafspraak
            .eindtijdgepland(UPDATED_EINDTIJDGEPLAND)
            .notitie(UPDATED_NOTITIE)
            .starttijdgepland(UPDATED_STARTTIJDGEPLAND)
            .tijdaangemaakt(UPDATED_TIJDAANGEMAAKT)
            .tijdsduurgepland(UPDATED_TIJDSDUURGEPLAND)
            .toelichting(UPDATED_TOELICHTING)
            .wachttijdnastartafspraak(UPDATED_WACHTTIJDNASTARTAFSPRAAK)
            .wachttijdtotaal(UPDATED_WACHTTIJDTOTAAL)
            .wachttijdvoorstartafspraak(UPDATED_WACHTTIJDVOORSTARTAFSPRAAK)
            .werkelijketijdsduur(UPDATED_WERKELIJKETIJDSDUUR);

        restBalieafspraakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBalieafspraak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBalieafspraak))
            )
            .andExpect(status().isOk());

        // Validate the Balieafspraak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBalieafspraakToMatchAllProperties(updatedBalieafspraak);
    }

    @Test
    @Transactional
    void putNonExistingBalieafspraak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieafspraak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBalieafspraakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, balieafspraak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(balieafspraak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Balieafspraak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBalieafspraak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieafspraak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalieafspraakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(balieafspraak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Balieafspraak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBalieafspraak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieafspraak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalieafspraakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(balieafspraak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Balieafspraak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBalieafspraakWithPatch() throws Exception {
        // Initialize the database
        balieafspraakRepository.saveAndFlush(balieafspraak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the balieafspraak using partial update
        Balieafspraak partialUpdatedBalieafspraak = new Balieafspraak();
        partialUpdatedBalieafspraak.setId(balieafspraak.getId());

        partialUpdatedBalieafspraak
            .starttijdgepland(UPDATED_STARTTIJDGEPLAND)
            .tijdaangemaakt(UPDATED_TIJDAANGEMAAKT)
            .werkelijketijdsduur(UPDATED_WERKELIJKETIJDSDUUR);

        restBalieafspraakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBalieafspraak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBalieafspraak))
            )
            .andExpect(status().isOk());

        // Validate the Balieafspraak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBalieafspraakUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBalieafspraak, balieafspraak),
            getPersistedBalieafspraak(balieafspraak)
        );
    }

    @Test
    @Transactional
    void fullUpdateBalieafspraakWithPatch() throws Exception {
        // Initialize the database
        balieafspraakRepository.saveAndFlush(balieafspraak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the balieafspraak using partial update
        Balieafspraak partialUpdatedBalieafspraak = new Balieafspraak();
        partialUpdatedBalieafspraak.setId(balieafspraak.getId());

        partialUpdatedBalieafspraak
            .eindtijdgepland(UPDATED_EINDTIJDGEPLAND)
            .notitie(UPDATED_NOTITIE)
            .starttijdgepland(UPDATED_STARTTIJDGEPLAND)
            .tijdaangemaakt(UPDATED_TIJDAANGEMAAKT)
            .tijdsduurgepland(UPDATED_TIJDSDUURGEPLAND)
            .toelichting(UPDATED_TOELICHTING)
            .wachttijdnastartafspraak(UPDATED_WACHTTIJDNASTARTAFSPRAAK)
            .wachttijdtotaal(UPDATED_WACHTTIJDTOTAAL)
            .wachttijdvoorstartafspraak(UPDATED_WACHTTIJDVOORSTARTAFSPRAAK)
            .werkelijketijdsduur(UPDATED_WERKELIJKETIJDSDUUR);

        restBalieafspraakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBalieafspraak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBalieafspraak))
            )
            .andExpect(status().isOk());

        // Validate the Balieafspraak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBalieafspraakUpdatableFieldsEquals(partialUpdatedBalieafspraak, getPersistedBalieafspraak(partialUpdatedBalieafspraak));
    }

    @Test
    @Transactional
    void patchNonExistingBalieafspraak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieafspraak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBalieafspraakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, balieafspraak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(balieafspraak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Balieafspraak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBalieafspraak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieafspraak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalieafspraakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(balieafspraak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Balieafspraak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBalieafspraak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieafspraak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalieafspraakMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(balieafspraak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Balieafspraak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBalieafspraak() throws Exception {
        // Initialize the database
        balieafspraakRepository.saveAndFlush(balieafspraak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the balieafspraak
        restBalieafspraakMockMvc
            .perform(delete(ENTITY_API_URL_ID, balieafspraak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return balieafspraakRepository.count();
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

    protected Balieafspraak getPersistedBalieafspraak(Balieafspraak balieafspraak) {
        return balieafspraakRepository.findById(balieafspraak.getId()).orElseThrow();
    }

    protected void assertPersistedBalieafspraakToMatchAllProperties(Balieafspraak expectedBalieafspraak) {
        assertBalieafspraakAllPropertiesEquals(expectedBalieafspraak, getPersistedBalieafspraak(expectedBalieafspraak));
    }

    protected void assertPersistedBalieafspraakToMatchUpdatableProperties(Balieafspraak expectedBalieafspraak) {
        assertBalieafspraakAllUpdatablePropertiesEquals(expectedBalieafspraak, getPersistedBalieafspraak(expectedBalieafspraak));
    }
}
