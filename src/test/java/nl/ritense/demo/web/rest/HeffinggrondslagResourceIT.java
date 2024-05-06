package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.HeffinggrondslagAsserts.*;
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
import nl.ritense.demo.domain.Heffinggrondslag;
import nl.ritense.demo.repository.HeffinggrondslagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HeffinggrondslagResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HeffinggrondslagResourceIT {

    private static final BigDecimal DEFAULT_BEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAG = new BigDecimal(2);

    private static final String DEFAULT_DOMEIN = "AAAAAAAAAA";
    private static final String UPDATED_DOMEIN = "BBBBBBBBBB";

    private static final String DEFAULT_HOOFDSTUK = "AAAAAAAAAA";
    private static final String UPDATED_HOOFDSTUK = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_PARAGRAAF = "AAAAAAAA";
    private static final String UPDATED_PARAGRAAF = "BBBBBBBB";

    private static final String ENTITY_API_URL = "/api/heffinggrondslags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HeffinggrondslagRepository heffinggrondslagRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHeffinggrondslagMockMvc;

    private Heffinggrondslag heffinggrondslag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Heffinggrondslag createEntity(EntityManager em) {
        Heffinggrondslag heffinggrondslag = new Heffinggrondslag()
            .bedrag(DEFAULT_BEDRAG)
            .domein(DEFAULT_DOMEIN)
            .hoofdstuk(DEFAULT_HOOFDSTUK)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .paragraaf(DEFAULT_PARAGRAAF);
        return heffinggrondslag;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Heffinggrondslag createUpdatedEntity(EntityManager em) {
        Heffinggrondslag heffinggrondslag = new Heffinggrondslag()
            .bedrag(UPDATED_BEDRAG)
            .domein(UPDATED_DOMEIN)
            .hoofdstuk(UPDATED_HOOFDSTUK)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .paragraaf(UPDATED_PARAGRAAF);
        return heffinggrondslag;
    }

    @BeforeEach
    public void initTest() {
        heffinggrondslag = createEntity(em);
    }

    @Test
    @Transactional
    void createHeffinggrondslag() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Heffinggrondslag
        var returnedHeffinggrondslag = om.readValue(
            restHeffinggrondslagMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(heffinggrondslag)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Heffinggrondslag.class
        );

        // Validate the Heffinggrondslag in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHeffinggrondslagUpdatableFieldsEquals(returnedHeffinggrondslag, getPersistedHeffinggrondslag(returnedHeffinggrondslag));
    }

    @Test
    @Transactional
    void createHeffinggrondslagWithExistingId() throws Exception {
        // Create the Heffinggrondslag with an existing ID
        heffinggrondslag.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHeffinggrondslagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(heffinggrondslag)))
            .andExpect(status().isBadRequest());

        // Validate the Heffinggrondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHeffinggrondslags() throws Exception {
        // Initialize the database
        heffinggrondslagRepository.saveAndFlush(heffinggrondslag);

        // Get all the heffinggrondslagList
        restHeffinggrondslagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(heffinggrondslag.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(sameNumber(DEFAULT_BEDRAG))))
            .andExpect(jsonPath("$.[*].domein").value(hasItem(DEFAULT_DOMEIN)))
            .andExpect(jsonPath("$.[*].hoofdstuk").value(hasItem(DEFAULT_HOOFDSTUK)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].paragraaf").value(hasItem(DEFAULT_PARAGRAAF)));
    }

    @Test
    @Transactional
    void getHeffinggrondslag() throws Exception {
        // Initialize the database
        heffinggrondslagRepository.saveAndFlush(heffinggrondslag);

        // Get the heffinggrondslag
        restHeffinggrondslagMockMvc
            .perform(get(ENTITY_API_URL_ID, heffinggrondslag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(heffinggrondslag.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(sameNumber(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.domein").value(DEFAULT_DOMEIN))
            .andExpect(jsonPath("$.hoofdstuk").value(DEFAULT_HOOFDSTUK))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.paragraaf").value(DEFAULT_PARAGRAAF));
    }

    @Test
    @Transactional
    void getNonExistingHeffinggrondslag() throws Exception {
        // Get the heffinggrondslag
        restHeffinggrondslagMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHeffinggrondslag() throws Exception {
        // Initialize the database
        heffinggrondslagRepository.saveAndFlush(heffinggrondslag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the heffinggrondslag
        Heffinggrondslag updatedHeffinggrondslag = heffinggrondslagRepository.findById(heffinggrondslag.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHeffinggrondslag are not directly saved in db
        em.detach(updatedHeffinggrondslag);
        updatedHeffinggrondslag
            .bedrag(UPDATED_BEDRAG)
            .domein(UPDATED_DOMEIN)
            .hoofdstuk(UPDATED_HOOFDSTUK)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .paragraaf(UPDATED_PARAGRAAF);

        restHeffinggrondslagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHeffinggrondslag.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHeffinggrondslag))
            )
            .andExpect(status().isOk());

        // Validate the Heffinggrondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHeffinggrondslagToMatchAllProperties(updatedHeffinggrondslag);
    }

    @Test
    @Transactional
    void putNonExistingHeffinggrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        heffinggrondslag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeffinggrondslagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, heffinggrondslag.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(heffinggrondslag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Heffinggrondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHeffinggrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        heffinggrondslag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeffinggrondslagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(heffinggrondslag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Heffinggrondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHeffinggrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        heffinggrondslag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeffinggrondslagMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(heffinggrondslag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Heffinggrondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHeffinggrondslagWithPatch() throws Exception {
        // Initialize the database
        heffinggrondslagRepository.saveAndFlush(heffinggrondslag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the heffinggrondslag using partial update
        Heffinggrondslag partialUpdatedHeffinggrondslag = new Heffinggrondslag();
        partialUpdatedHeffinggrondslag.setId(heffinggrondslag.getId());

        partialUpdatedHeffinggrondslag.bedrag(UPDATED_BEDRAG).hoofdstuk(UPDATED_HOOFDSTUK).paragraaf(UPDATED_PARAGRAAF);

        restHeffinggrondslagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeffinggrondslag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHeffinggrondslag))
            )
            .andExpect(status().isOk());

        // Validate the Heffinggrondslag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHeffinggrondslagUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHeffinggrondslag, heffinggrondslag),
            getPersistedHeffinggrondslag(heffinggrondslag)
        );
    }

    @Test
    @Transactional
    void fullUpdateHeffinggrondslagWithPatch() throws Exception {
        // Initialize the database
        heffinggrondslagRepository.saveAndFlush(heffinggrondslag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the heffinggrondslag using partial update
        Heffinggrondslag partialUpdatedHeffinggrondslag = new Heffinggrondslag();
        partialUpdatedHeffinggrondslag.setId(heffinggrondslag.getId());

        partialUpdatedHeffinggrondslag
            .bedrag(UPDATED_BEDRAG)
            .domein(UPDATED_DOMEIN)
            .hoofdstuk(UPDATED_HOOFDSTUK)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .paragraaf(UPDATED_PARAGRAAF);

        restHeffinggrondslagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeffinggrondslag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHeffinggrondslag))
            )
            .andExpect(status().isOk());

        // Validate the Heffinggrondslag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHeffinggrondslagUpdatableFieldsEquals(
            partialUpdatedHeffinggrondslag,
            getPersistedHeffinggrondslag(partialUpdatedHeffinggrondslag)
        );
    }

    @Test
    @Transactional
    void patchNonExistingHeffinggrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        heffinggrondslag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeffinggrondslagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, heffinggrondslag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(heffinggrondslag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Heffinggrondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHeffinggrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        heffinggrondslag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeffinggrondslagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(heffinggrondslag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Heffinggrondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHeffinggrondslag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        heffinggrondslag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeffinggrondslagMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(heffinggrondslag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Heffinggrondslag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHeffinggrondslag() throws Exception {
        // Initialize the database
        heffinggrondslagRepository.saveAndFlush(heffinggrondslag);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the heffinggrondslag
        restHeffinggrondslagMockMvc
            .perform(delete(ENTITY_API_URL_ID, heffinggrondslag.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return heffinggrondslagRepository.count();
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

    protected Heffinggrondslag getPersistedHeffinggrondslag(Heffinggrondslag heffinggrondslag) {
        return heffinggrondslagRepository.findById(heffinggrondslag.getId()).orElseThrow();
    }

    protected void assertPersistedHeffinggrondslagToMatchAllProperties(Heffinggrondslag expectedHeffinggrondslag) {
        assertHeffinggrondslagAllPropertiesEquals(expectedHeffinggrondslag, getPersistedHeffinggrondslag(expectedHeffinggrondslag));
    }

    protected void assertPersistedHeffinggrondslagToMatchUpdatableProperties(Heffinggrondslag expectedHeffinggrondslag) {
        assertHeffinggrondslagAllUpdatablePropertiesEquals(
            expectedHeffinggrondslag,
            getPersistedHeffinggrondslag(expectedHeffinggrondslag)
        );
    }
}
