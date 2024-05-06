package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RondleidingAsserts.*;
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
import nl.ritense.demo.domain.Rondleiding;
import nl.ritense.demo.repository.RondleidingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RondleidingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RondleidingResourceIT {

    private static final String DEFAULT_EINDTIJD = "AAAAAAAAAA";
    private static final String UPDATED_EINDTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_STARTTIJD = "AAAAAAAAAA";
    private static final String UPDATED_STARTTIJD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rondleidings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RondleidingRepository rondleidingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRondleidingMockMvc;

    private Rondleiding rondleiding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rondleiding createEntity(EntityManager em) {
        Rondleiding rondleiding = new Rondleiding()
            .eindtijd(DEFAULT_EINDTIJD)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .starttijd(DEFAULT_STARTTIJD);
        return rondleiding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rondleiding createUpdatedEntity(EntityManager em) {
        Rondleiding rondleiding = new Rondleiding()
            .eindtijd(UPDATED_EINDTIJD)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .starttijd(UPDATED_STARTTIJD);
        return rondleiding;
    }

    @BeforeEach
    public void initTest() {
        rondleiding = createEntity(em);
    }

    @Test
    @Transactional
    void createRondleiding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Rondleiding
        var returnedRondleiding = om.readValue(
            restRondleidingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rondleiding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Rondleiding.class
        );

        // Validate the Rondleiding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRondleidingUpdatableFieldsEquals(returnedRondleiding, getPersistedRondleiding(returnedRondleiding));
    }

    @Test
    @Transactional
    void createRondleidingWithExistingId() throws Exception {
        // Create the Rondleiding with an existing ID
        rondleiding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRondleidingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rondleiding)))
            .andExpect(status().isBadRequest());

        // Validate the Rondleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRondleidings() throws Exception {
        // Initialize the database
        rondleidingRepository.saveAndFlush(rondleiding);

        // Get all the rondleidingList
        restRondleidingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rondleiding.getId().intValue())))
            .andExpect(jsonPath("$.[*].eindtijd").value(hasItem(DEFAULT_EINDTIJD)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].starttijd").value(hasItem(DEFAULT_STARTTIJD)));
    }

    @Test
    @Transactional
    void getRondleiding() throws Exception {
        // Initialize the database
        rondleidingRepository.saveAndFlush(rondleiding);

        // Get the rondleiding
        restRondleidingMockMvc
            .perform(get(ENTITY_API_URL_ID, rondleiding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rondleiding.getId().intValue()))
            .andExpect(jsonPath("$.eindtijd").value(DEFAULT_EINDTIJD))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.starttijd").value(DEFAULT_STARTTIJD));
    }

    @Test
    @Transactional
    void getNonExistingRondleiding() throws Exception {
        // Get the rondleiding
        restRondleidingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRondleiding() throws Exception {
        // Initialize the database
        rondleidingRepository.saveAndFlush(rondleiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rondleiding
        Rondleiding updatedRondleiding = rondleidingRepository.findById(rondleiding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRondleiding are not directly saved in db
        em.detach(updatedRondleiding);
        updatedRondleiding.eindtijd(UPDATED_EINDTIJD).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING).starttijd(UPDATED_STARTTIJD);

        restRondleidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRondleiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRondleiding))
            )
            .andExpect(status().isOk());

        // Validate the Rondleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRondleidingToMatchAllProperties(updatedRondleiding);
    }

    @Test
    @Transactional
    void putNonExistingRondleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rondleiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRondleidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rondleiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rondleiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rondleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRondleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rondleiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRondleidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rondleiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rondleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRondleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rondleiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRondleidingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rondleiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rondleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRondleidingWithPatch() throws Exception {
        // Initialize the database
        rondleidingRepository.saveAndFlush(rondleiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rondleiding using partial update
        Rondleiding partialUpdatedRondleiding = new Rondleiding();
        partialUpdatedRondleiding.setId(rondleiding.getId());

        partialUpdatedRondleiding.eindtijd(UPDATED_EINDTIJD).starttijd(UPDATED_STARTTIJD);

        restRondleidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRondleiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRondleiding))
            )
            .andExpect(status().isOk());

        // Validate the Rondleiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRondleidingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRondleiding, rondleiding),
            getPersistedRondleiding(rondleiding)
        );
    }

    @Test
    @Transactional
    void fullUpdateRondleidingWithPatch() throws Exception {
        // Initialize the database
        rondleidingRepository.saveAndFlush(rondleiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rondleiding using partial update
        Rondleiding partialUpdatedRondleiding = new Rondleiding();
        partialUpdatedRondleiding.setId(rondleiding.getId());

        partialUpdatedRondleiding
            .eindtijd(UPDATED_EINDTIJD)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .starttijd(UPDATED_STARTTIJD);

        restRondleidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRondleiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRondleiding))
            )
            .andExpect(status().isOk());

        // Validate the Rondleiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRondleidingUpdatableFieldsEquals(partialUpdatedRondleiding, getPersistedRondleiding(partialUpdatedRondleiding));
    }

    @Test
    @Transactional
    void patchNonExistingRondleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rondleiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRondleidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rondleiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rondleiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rondleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRondleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rondleiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRondleidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rondleiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rondleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRondleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rondleiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRondleidingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rondleiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rondleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRondleiding() throws Exception {
        // Initialize the database
        rondleidingRepository.saveAndFlush(rondleiding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rondleiding
        restRondleidingMockMvc
            .perform(delete(ENTITY_API_URL_ID, rondleiding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rondleidingRepository.count();
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

    protected Rondleiding getPersistedRondleiding(Rondleiding rondleiding) {
        return rondleidingRepository.findById(rondleiding.getId()).orElseThrow();
    }

    protected void assertPersistedRondleidingToMatchAllProperties(Rondleiding expectedRondleiding) {
        assertRondleidingAllPropertiesEquals(expectedRondleiding, getPersistedRondleiding(expectedRondleiding));
    }

    protected void assertPersistedRondleidingToMatchUpdatableProperties(Rondleiding expectedRondleiding) {
        assertRondleidingAllUpdatablePropertiesEquals(expectedRondleiding, getPersistedRondleiding(expectedRondleiding));
    }
}
