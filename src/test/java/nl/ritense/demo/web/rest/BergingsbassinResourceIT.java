package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BergingsbassinAsserts.*;
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
import nl.ritense.demo.domain.Bergingsbassin;
import nl.ritense.demo.repository.BergingsbassinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BergingsbassinResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BergingsbassinResourceIT {

    private static final String DEFAULT_BERGENDVERMOGEN = "AAAAAAAAAA";
    private static final String UPDATED_BERGENDVERMOGEN = "BBBBBBBBBB";

    private static final String DEFAULT_POMPLEDIGINGSVOORZIENING = "AAAAAAAAAA";
    private static final String UPDATED_POMPLEDIGINGSVOORZIENING = "BBBBBBBBBB";

    private static final String DEFAULT_POMPSPOELVOORZIENING = "AAAAAAAAAA";
    private static final String UPDATED_POMPSPOELVOORZIENING = "BBBBBBBBBB";

    private static final String DEFAULT_SPOELLEIDING = "AAAAAAAAAA";
    private static final String UPDATED_SPOELLEIDING = "BBBBBBBBBB";

    private static final String DEFAULT_VORM = "AAAAAAAAAA";
    private static final String UPDATED_VORM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bergingsbassins";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BergingsbassinRepository bergingsbassinRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBergingsbassinMockMvc;

    private Bergingsbassin bergingsbassin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bergingsbassin createEntity(EntityManager em) {
        Bergingsbassin bergingsbassin = new Bergingsbassin()
            .bergendvermogen(DEFAULT_BERGENDVERMOGEN)
            .pompledigingsvoorziening(DEFAULT_POMPLEDIGINGSVOORZIENING)
            .pompspoelvoorziening(DEFAULT_POMPSPOELVOORZIENING)
            .spoelleiding(DEFAULT_SPOELLEIDING)
            .vorm(DEFAULT_VORM);
        return bergingsbassin;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bergingsbassin createUpdatedEntity(EntityManager em) {
        Bergingsbassin bergingsbassin = new Bergingsbassin()
            .bergendvermogen(UPDATED_BERGENDVERMOGEN)
            .pompledigingsvoorziening(UPDATED_POMPLEDIGINGSVOORZIENING)
            .pompspoelvoorziening(UPDATED_POMPSPOELVOORZIENING)
            .spoelleiding(UPDATED_SPOELLEIDING)
            .vorm(UPDATED_VORM);
        return bergingsbassin;
    }

    @BeforeEach
    public void initTest() {
        bergingsbassin = createEntity(em);
    }

    @Test
    @Transactional
    void createBergingsbassin() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bergingsbassin
        var returnedBergingsbassin = om.readValue(
            restBergingsbassinMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bergingsbassin)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bergingsbassin.class
        );

        // Validate the Bergingsbassin in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBergingsbassinUpdatableFieldsEquals(returnedBergingsbassin, getPersistedBergingsbassin(returnedBergingsbassin));
    }

    @Test
    @Transactional
    void createBergingsbassinWithExistingId() throws Exception {
        // Create the Bergingsbassin with an existing ID
        bergingsbassin.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBergingsbassinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bergingsbassin)))
            .andExpect(status().isBadRequest());

        // Validate the Bergingsbassin in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBergingsbassins() throws Exception {
        // Initialize the database
        bergingsbassinRepository.saveAndFlush(bergingsbassin);

        // Get all the bergingsbassinList
        restBergingsbassinMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bergingsbassin.getId().intValue())))
            .andExpect(jsonPath("$.[*].bergendvermogen").value(hasItem(DEFAULT_BERGENDVERMOGEN)))
            .andExpect(jsonPath("$.[*].pompledigingsvoorziening").value(hasItem(DEFAULT_POMPLEDIGINGSVOORZIENING)))
            .andExpect(jsonPath("$.[*].pompspoelvoorziening").value(hasItem(DEFAULT_POMPSPOELVOORZIENING)))
            .andExpect(jsonPath("$.[*].spoelleiding").value(hasItem(DEFAULT_SPOELLEIDING)))
            .andExpect(jsonPath("$.[*].vorm").value(hasItem(DEFAULT_VORM)));
    }

    @Test
    @Transactional
    void getBergingsbassin() throws Exception {
        // Initialize the database
        bergingsbassinRepository.saveAndFlush(bergingsbassin);

        // Get the bergingsbassin
        restBergingsbassinMockMvc
            .perform(get(ENTITY_API_URL_ID, bergingsbassin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bergingsbassin.getId().intValue()))
            .andExpect(jsonPath("$.bergendvermogen").value(DEFAULT_BERGENDVERMOGEN))
            .andExpect(jsonPath("$.pompledigingsvoorziening").value(DEFAULT_POMPLEDIGINGSVOORZIENING))
            .andExpect(jsonPath("$.pompspoelvoorziening").value(DEFAULT_POMPSPOELVOORZIENING))
            .andExpect(jsonPath("$.spoelleiding").value(DEFAULT_SPOELLEIDING))
            .andExpect(jsonPath("$.vorm").value(DEFAULT_VORM));
    }

    @Test
    @Transactional
    void getNonExistingBergingsbassin() throws Exception {
        // Get the bergingsbassin
        restBergingsbassinMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBergingsbassin() throws Exception {
        // Initialize the database
        bergingsbassinRepository.saveAndFlush(bergingsbassin);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bergingsbassin
        Bergingsbassin updatedBergingsbassin = bergingsbassinRepository.findById(bergingsbassin.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBergingsbassin are not directly saved in db
        em.detach(updatedBergingsbassin);
        updatedBergingsbassin
            .bergendvermogen(UPDATED_BERGENDVERMOGEN)
            .pompledigingsvoorziening(UPDATED_POMPLEDIGINGSVOORZIENING)
            .pompspoelvoorziening(UPDATED_POMPSPOELVOORZIENING)
            .spoelleiding(UPDATED_SPOELLEIDING)
            .vorm(UPDATED_VORM);

        restBergingsbassinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBergingsbassin.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBergingsbassin))
            )
            .andExpect(status().isOk());

        // Validate the Bergingsbassin in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBergingsbassinToMatchAllProperties(updatedBergingsbassin);
    }

    @Test
    @Transactional
    void putNonExistingBergingsbassin() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bergingsbassin.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBergingsbassinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bergingsbassin.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bergingsbassin))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bergingsbassin in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBergingsbassin() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bergingsbassin.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBergingsbassinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bergingsbassin))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bergingsbassin in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBergingsbassin() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bergingsbassin.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBergingsbassinMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bergingsbassin)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bergingsbassin in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBergingsbassinWithPatch() throws Exception {
        // Initialize the database
        bergingsbassinRepository.saveAndFlush(bergingsbassin);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bergingsbassin using partial update
        Bergingsbassin partialUpdatedBergingsbassin = new Bergingsbassin();
        partialUpdatedBergingsbassin.setId(bergingsbassin.getId());

        partialUpdatedBergingsbassin.vorm(UPDATED_VORM);

        restBergingsbassinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBergingsbassin.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBergingsbassin))
            )
            .andExpect(status().isOk());

        // Validate the Bergingsbassin in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBergingsbassinUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBergingsbassin, bergingsbassin),
            getPersistedBergingsbassin(bergingsbassin)
        );
    }

    @Test
    @Transactional
    void fullUpdateBergingsbassinWithPatch() throws Exception {
        // Initialize the database
        bergingsbassinRepository.saveAndFlush(bergingsbassin);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bergingsbassin using partial update
        Bergingsbassin partialUpdatedBergingsbassin = new Bergingsbassin();
        partialUpdatedBergingsbassin.setId(bergingsbassin.getId());

        partialUpdatedBergingsbassin
            .bergendvermogen(UPDATED_BERGENDVERMOGEN)
            .pompledigingsvoorziening(UPDATED_POMPLEDIGINGSVOORZIENING)
            .pompspoelvoorziening(UPDATED_POMPSPOELVOORZIENING)
            .spoelleiding(UPDATED_SPOELLEIDING)
            .vorm(UPDATED_VORM);

        restBergingsbassinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBergingsbassin.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBergingsbassin))
            )
            .andExpect(status().isOk());

        // Validate the Bergingsbassin in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBergingsbassinUpdatableFieldsEquals(partialUpdatedBergingsbassin, getPersistedBergingsbassin(partialUpdatedBergingsbassin));
    }

    @Test
    @Transactional
    void patchNonExistingBergingsbassin() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bergingsbassin.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBergingsbassinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bergingsbassin.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bergingsbassin))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bergingsbassin in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBergingsbassin() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bergingsbassin.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBergingsbassinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bergingsbassin))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bergingsbassin in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBergingsbassin() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bergingsbassin.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBergingsbassinMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bergingsbassin)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bergingsbassin in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBergingsbassin() throws Exception {
        // Initialize the database
        bergingsbassinRepository.saveAndFlush(bergingsbassin);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bergingsbassin
        restBergingsbassinMockMvc
            .perform(delete(ENTITY_API_URL_ID, bergingsbassin.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bergingsbassinRepository.count();
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

    protected Bergingsbassin getPersistedBergingsbassin(Bergingsbassin bergingsbassin) {
        return bergingsbassinRepository.findById(bergingsbassin.getId()).orElseThrow();
    }

    protected void assertPersistedBergingsbassinToMatchAllProperties(Bergingsbassin expectedBergingsbassin) {
        assertBergingsbassinAllPropertiesEquals(expectedBergingsbassin, getPersistedBergingsbassin(expectedBergingsbassin));
    }

    protected void assertPersistedBergingsbassinToMatchUpdatableProperties(Bergingsbassin expectedBergingsbassin) {
        assertBergingsbassinAllUpdatablePropertiesEquals(expectedBergingsbassin, getPersistedBergingsbassin(expectedBergingsbassin));
    }
}
