package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LeerlingAsserts.*;
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
import nl.ritense.demo.domain.Leerling;
import nl.ritense.demo.domain.Startkwalificatie;
import nl.ritense.demo.repository.LeerlingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LeerlingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeerlingResourceIT {

    private static final Boolean DEFAULT_KWETSBAREJONGERE = false;
    private static final Boolean UPDATED_KWETSBAREJONGERE = true;

    private static final String ENTITY_API_URL = "/api/leerlings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LeerlingRepository leerlingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeerlingMockMvc;

    private Leerling leerling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leerling createEntity(EntityManager em) {
        Leerling leerling = new Leerling().kwetsbarejongere(DEFAULT_KWETSBAREJONGERE);
        // Add required entity
        Startkwalificatie startkwalificatie;
        if (TestUtil.findAll(em, Startkwalificatie.class).isEmpty()) {
            startkwalificatie = StartkwalificatieResourceIT.createEntity(em);
            em.persist(startkwalificatie);
            em.flush();
        } else {
            startkwalificatie = TestUtil.findAll(em, Startkwalificatie.class).get(0);
        }
        leerling.setHeeftStartkwalificatie(startkwalificatie);
        return leerling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leerling createUpdatedEntity(EntityManager em) {
        Leerling leerling = new Leerling().kwetsbarejongere(UPDATED_KWETSBAREJONGERE);
        // Add required entity
        Startkwalificatie startkwalificatie;
        if (TestUtil.findAll(em, Startkwalificatie.class).isEmpty()) {
            startkwalificatie = StartkwalificatieResourceIT.createUpdatedEntity(em);
            em.persist(startkwalificatie);
            em.flush();
        } else {
            startkwalificatie = TestUtil.findAll(em, Startkwalificatie.class).get(0);
        }
        leerling.setHeeftStartkwalificatie(startkwalificatie);
        return leerling;
    }

    @BeforeEach
    public void initTest() {
        leerling = createEntity(em);
    }

    @Test
    @Transactional
    void createLeerling() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Leerling
        var returnedLeerling = om.readValue(
            restLeerlingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leerling)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Leerling.class
        );

        // Validate the Leerling in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLeerlingUpdatableFieldsEquals(returnedLeerling, getPersistedLeerling(returnedLeerling));
    }

    @Test
    @Transactional
    void createLeerlingWithExistingId() throws Exception {
        // Create the Leerling with an existing ID
        leerling.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeerlingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leerling)))
            .andExpect(status().isBadRequest());

        // Validate the Leerling in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeerlings() throws Exception {
        // Initialize the database
        leerlingRepository.saveAndFlush(leerling);

        // Get all the leerlingList
        restLeerlingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leerling.getId().intValue())))
            .andExpect(jsonPath("$.[*].kwetsbarejongere").value(hasItem(DEFAULT_KWETSBAREJONGERE.booleanValue())));
    }

    @Test
    @Transactional
    void getLeerling() throws Exception {
        // Initialize the database
        leerlingRepository.saveAndFlush(leerling);

        // Get the leerling
        restLeerlingMockMvc
            .perform(get(ENTITY_API_URL_ID, leerling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leerling.getId().intValue()))
            .andExpect(jsonPath("$.kwetsbarejongere").value(DEFAULT_KWETSBAREJONGERE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingLeerling() throws Exception {
        // Get the leerling
        restLeerlingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeerling() throws Exception {
        // Initialize the database
        leerlingRepository.saveAndFlush(leerling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leerling
        Leerling updatedLeerling = leerlingRepository.findById(leerling.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLeerling are not directly saved in db
        em.detach(updatedLeerling);
        updatedLeerling.kwetsbarejongere(UPDATED_KWETSBAREJONGERE);

        restLeerlingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeerling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLeerling))
            )
            .andExpect(status().isOk());

        // Validate the Leerling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLeerlingToMatchAllProperties(updatedLeerling);
    }

    @Test
    @Transactional
    void putNonExistingLeerling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leerling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeerlingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leerling.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leerling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leerling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeerling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leerling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeerlingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(leerling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leerling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeerling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leerling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeerlingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leerling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leerling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeerlingWithPatch() throws Exception {
        // Initialize the database
        leerlingRepository.saveAndFlush(leerling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leerling using partial update
        Leerling partialUpdatedLeerling = new Leerling();
        partialUpdatedLeerling.setId(leerling.getId());

        restLeerlingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeerling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLeerling))
            )
            .andExpect(status().isOk());

        // Validate the Leerling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLeerlingUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLeerling, leerling), getPersistedLeerling(leerling));
    }

    @Test
    @Transactional
    void fullUpdateLeerlingWithPatch() throws Exception {
        // Initialize the database
        leerlingRepository.saveAndFlush(leerling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leerling using partial update
        Leerling partialUpdatedLeerling = new Leerling();
        partialUpdatedLeerling.setId(leerling.getId());

        partialUpdatedLeerling.kwetsbarejongere(UPDATED_KWETSBAREJONGERE);

        restLeerlingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeerling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLeerling))
            )
            .andExpect(status().isOk());

        // Validate the Leerling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLeerlingUpdatableFieldsEquals(partialUpdatedLeerling, getPersistedLeerling(partialUpdatedLeerling));
    }

    @Test
    @Transactional
    void patchNonExistingLeerling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leerling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeerlingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leerling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(leerling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leerling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeerling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leerling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeerlingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(leerling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leerling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeerling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leerling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeerlingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(leerling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leerling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeerling() throws Exception {
        // Initialize the database
        leerlingRepository.saveAndFlush(leerling);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the leerling
        restLeerlingMockMvc
            .perform(delete(ENTITY_API_URL_ID, leerling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return leerlingRepository.count();
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

    protected Leerling getPersistedLeerling(Leerling leerling) {
        return leerlingRepository.findById(leerling.getId()).orElseThrow();
    }

    protected void assertPersistedLeerlingToMatchAllProperties(Leerling expectedLeerling) {
        assertLeerlingAllPropertiesEquals(expectedLeerling, getPersistedLeerling(expectedLeerling));
    }

    protected void assertPersistedLeerlingToMatchUpdatableProperties(Leerling expectedLeerling) {
        assertLeerlingAllUpdatablePropertiesEquals(expectedLeerling, getPersistedLeerling(expectedLeerling));
    }
}
