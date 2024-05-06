package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GebruikerrolAsserts.*;
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
import nl.ritense.demo.domain.Gebruikerrol;
import nl.ritense.demo.repository.GebruikerrolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GebruikerrolResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GebruikerrolResourceIT {

    private static final String DEFAULT_ROL = "AAAAAAAAAA";
    private static final String UPDATED_ROL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gebruikerrols";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GebruikerrolRepository gebruikerrolRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGebruikerrolMockMvc;

    private Gebruikerrol gebruikerrol;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gebruikerrol createEntity(EntityManager em) {
        Gebruikerrol gebruikerrol = new Gebruikerrol().rol(DEFAULT_ROL);
        return gebruikerrol;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gebruikerrol createUpdatedEntity(EntityManager em) {
        Gebruikerrol gebruikerrol = new Gebruikerrol().rol(UPDATED_ROL);
        return gebruikerrol;
    }

    @BeforeEach
    public void initTest() {
        gebruikerrol = createEntity(em);
    }

    @Test
    @Transactional
    void createGebruikerrol() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Gebruikerrol
        var returnedGebruikerrol = om.readValue(
            restGebruikerrolMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebruikerrol)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Gebruikerrol.class
        );

        // Validate the Gebruikerrol in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGebruikerrolUpdatableFieldsEquals(returnedGebruikerrol, getPersistedGebruikerrol(returnedGebruikerrol));
    }

    @Test
    @Transactional
    void createGebruikerrolWithExistingId() throws Exception {
        // Create the Gebruikerrol with an existing ID
        gebruikerrol.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGebruikerrolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebruikerrol)))
            .andExpect(status().isBadRequest());

        // Validate the Gebruikerrol in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGebruikerrols() throws Exception {
        // Initialize the database
        gebruikerrolRepository.saveAndFlush(gebruikerrol);

        // Get all the gebruikerrolList
        restGebruikerrolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gebruikerrol.getId().intValue())))
            .andExpect(jsonPath("$.[*].rol").value(hasItem(DEFAULT_ROL)));
    }

    @Test
    @Transactional
    void getGebruikerrol() throws Exception {
        // Initialize the database
        gebruikerrolRepository.saveAndFlush(gebruikerrol);

        // Get the gebruikerrol
        restGebruikerrolMockMvc
            .perform(get(ENTITY_API_URL_ID, gebruikerrol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gebruikerrol.getId().intValue()))
            .andExpect(jsonPath("$.rol").value(DEFAULT_ROL));
    }

    @Test
    @Transactional
    void getNonExistingGebruikerrol() throws Exception {
        // Get the gebruikerrol
        restGebruikerrolMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGebruikerrol() throws Exception {
        // Initialize the database
        gebruikerrolRepository.saveAndFlush(gebruikerrol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebruikerrol
        Gebruikerrol updatedGebruikerrol = gebruikerrolRepository.findById(gebruikerrol.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGebruikerrol are not directly saved in db
        em.detach(updatedGebruikerrol);
        updatedGebruikerrol.rol(UPDATED_ROL);

        restGebruikerrolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGebruikerrol.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGebruikerrol))
            )
            .andExpect(status().isOk());

        // Validate the Gebruikerrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGebruikerrolToMatchAllProperties(updatedGebruikerrol);
    }

    @Test
    @Transactional
    void putNonExistingGebruikerrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebruikerrol.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGebruikerrolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gebruikerrol.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gebruikerrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebruikerrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGebruikerrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebruikerrol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebruikerrolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gebruikerrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebruikerrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGebruikerrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebruikerrol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebruikerrolMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebruikerrol)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gebruikerrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGebruikerrolWithPatch() throws Exception {
        // Initialize the database
        gebruikerrolRepository.saveAndFlush(gebruikerrol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebruikerrol using partial update
        Gebruikerrol partialUpdatedGebruikerrol = new Gebruikerrol();
        partialUpdatedGebruikerrol.setId(gebruikerrol.getId());

        restGebruikerrolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGebruikerrol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGebruikerrol))
            )
            .andExpect(status().isOk());

        // Validate the Gebruikerrol in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGebruikerrolUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGebruikerrol, gebruikerrol),
            getPersistedGebruikerrol(gebruikerrol)
        );
    }

    @Test
    @Transactional
    void fullUpdateGebruikerrolWithPatch() throws Exception {
        // Initialize the database
        gebruikerrolRepository.saveAndFlush(gebruikerrol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebruikerrol using partial update
        Gebruikerrol partialUpdatedGebruikerrol = new Gebruikerrol();
        partialUpdatedGebruikerrol.setId(gebruikerrol.getId());

        partialUpdatedGebruikerrol.rol(UPDATED_ROL);

        restGebruikerrolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGebruikerrol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGebruikerrol))
            )
            .andExpect(status().isOk());

        // Validate the Gebruikerrol in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGebruikerrolUpdatableFieldsEquals(partialUpdatedGebruikerrol, getPersistedGebruikerrol(partialUpdatedGebruikerrol));
    }

    @Test
    @Transactional
    void patchNonExistingGebruikerrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebruikerrol.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGebruikerrolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gebruikerrol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gebruikerrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebruikerrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGebruikerrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebruikerrol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebruikerrolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gebruikerrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebruikerrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGebruikerrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebruikerrol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebruikerrolMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gebruikerrol)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gebruikerrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGebruikerrol() throws Exception {
        // Initialize the database
        gebruikerrolRepository.saveAndFlush(gebruikerrol);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gebruikerrol
        restGebruikerrolMockMvc
            .perform(delete(ENTITY_API_URL_ID, gebruikerrol.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gebruikerrolRepository.count();
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

    protected Gebruikerrol getPersistedGebruikerrol(Gebruikerrol gebruikerrol) {
        return gebruikerrolRepository.findById(gebruikerrol.getId()).orElseThrow();
    }

    protected void assertPersistedGebruikerrolToMatchAllProperties(Gebruikerrol expectedGebruikerrol) {
        assertGebruikerrolAllPropertiesEquals(expectedGebruikerrol, getPersistedGebruikerrol(expectedGebruikerrol));
    }

    protected void assertPersistedGebruikerrolToMatchUpdatableProperties(Gebruikerrol expectedGebruikerrol) {
        assertGebruikerrolAllUpdatablePropertiesEquals(expectedGebruikerrol, getPersistedGebruikerrol(expectedGebruikerrol));
    }
}
