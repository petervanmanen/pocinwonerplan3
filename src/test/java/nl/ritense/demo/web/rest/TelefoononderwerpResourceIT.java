package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TelefoononderwerpAsserts.*;
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
import nl.ritense.demo.domain.Telefoononderwerp;
import nl.ritense.demo.repository.TelefoononderwerpRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TelefoononderwerpResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TelefoononderwerpResourceIT {

    private static final String DEFAULT_ONDERWERP = "AAAAAAAAAA";
    private static final String UPDATED_ONDERWERP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/telefoononderwerps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TelefoononderwerpRepository telefoononderwerpRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTelefoononderwerpMockMvc;

    private Telefoononderwerp telefoononderwerp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefoononderwerp createEntity(EntityManager em) {
        Telefoononderwerp telefoononderwerp = new Telefoononderwerp().onderwerp(DEFAULT_ONDERWERP);
        return telefoononderwerp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefoononderwerp createUpdatedEntity(EntityManager em) {
        Telefoononderwerp telefoononderwerp = new Telefoononderwerp().onderwerp(UPDATED_ONDERWERP);
        return telefoononderwerp;
    }

    @BeforeEach
    public void initTest() {
        telefoononderwerp = createEntity(em);
    }

    @Test
    @Transactional
    void createTelefoononderwerp() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Telefoononderwerp
        var returnedTelefoononderwerp = om.readValue(
            restTelefoononderwerpMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telefoononderwerp)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Telefoononderwerp.class
        );

        // Validate the Telefoononderwerp in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTelefoononderwerpUpdatableFieldsEquals(returnedTelefoononderwerp, getPersistedTelefoononderwerp(returnedTelefoononderwerp));
    }

    @Test
    @Transactional
    void createTelefoononderwerpWithExistingId() throws Exception {
        // Create the Telefoononderwerp with an existing ID
        telefoononderwerp.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelefoononderwerpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telefoononderwerp)))
            .andExpect(status().isBadRequest());

        // Validate the Telefoononderwerp in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTelefoononderwerps() throws Exception {
        // Initialize the database
        telefoononderwerpRepository.saveAndFlush(telefoononderwerp);

        // Get all the telefoononderwerpList
        restTelefoononderwerpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telefoononderwerp.getId().intValue())))
            .andExpect(jsonPath("$.[*].onderwerp").value(hasItem(DEFAULT_ONDERWERP)));
    }

    @Test
    @Transactional
    void getTelefoononderwerp() throws Exception {
        // Initialize the database
        telefoononderwerpRepository.saveAndFlush(telefoononderwerp);

        // Get the telefoononderwerp
        restTelefoononderwerpMockMvc
            .perform(get(ENTITY_API_URL_ID, telefoononderwerp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(telefoononderwerp.getId().intValue()))
            .andExpect(jsonPath("$.onderwerp").value(DEFAULT_ONDERWERP));
    }

    @Test
    @Transactional
    void getNonExistingTelefoononderwerp() throws Exception {
        // Get the telefoononderwerp
        restTelefoononderwerpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTelefoononderwerp() throws Exception {
        // Initialize the database
        telefoononderwerpRepository.saveAndFlush(telefoononderwerp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the telefoononderwerp
        Telefoononderwerp updatedTelefoononderwerp = telefoononderwerpRepository.findById(telefoononderwerp.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTelefoononderwerp are not directly saved in db
        em.detach(updatedTelefoononderwerp);
        updatedTelefoononderwerp.onderwerp(UPDATED_ONDERWERP);

        restTelefoononderwerpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTelefoononderwerp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTelefoononderwerp))
            )
            .andExpect(status().isOk());

        // Validate the Telefoononderwerp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTelefoononderwerpToMatchAllProperties(updatedTelefoononderwerp);
    }

    @Test
    @Transactional
    void putNonExistingTelefoononderwerp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoononderwerp.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelefoononderwerpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telefoononderwerp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(telefoononderwerp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefoononderwerp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTelefoononderwerp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoononderwerp.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoononderwerpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(telefoononderwerp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefoononderwerp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTelefoononderwerp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoononderwerp.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoononderwerpMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telefoononderwerp)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Telefoononderwerp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTelefoononderwerpWithPatch() throws Exception {
        // Initialize the database
        telefoononderwerpRepository.saveAndFlush(telefoononderwerp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the telefoononderwerp using partial update
        Telefoononderwerp partialUpdatedTelefoononderwerp = new Telefoononderwerp();
        partialUpdatedTelefoononderwerp.setId(telefoononderwerp.getId());

        partialUpdatedTelefoononderwerp.onderwerp(UPDATED_ONDERWERP);

        restTelefoononderwerpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelefoononderwerp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTelefoononderwerp))
            )
            .andExpect(status().isOk());

        // Validate the Telefoononderwerp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTelefoononderwerpUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTelefoononderwerp, telefoononderwerp),
            getPersistedTelefoononderwerp(telefoononderwerp)
        );
    }

    @Test
    @Transactional
    void fullUpdateTelefoononderwerpWithPatch() throws Exception {
        // Initialize the database
        telefoononderwerpRepository.saveAndFlush(telefoononderwerp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the telefoononderwerp using partial update
        Telefoononderwerp partialUpdatedTelefoononderwerp = new Telefoononderwerp();
        partialUpdatedTelefoononderwerp.setId(telefoononderwerp.getId());

        partialUpdatedTelefoononderwerp.onderwerp(UPDATED_ONDERWERP);

        restTelefoononderwerpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelefoononderwerp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTelefoononderwerp))
            )
            .andExpect(status().isOk());

        // Validate the Telefoononderwerp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTelefoononderwerpUpdatableFieldsEquals(
            partialUpdatedTelefoononderwerp,
            getPersistedTelefoononderwerp(partialUpdatedTelefoononderwerp)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTelefoononderwerp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoononderwerp.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelefoononderwerpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, telefoononderwerp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(telefoononderwerp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefoononderwerp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTelefoononderwerp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoononderwerp.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoononderwerpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(telefoononderwerp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefoononderwerp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTelefoononderwerp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoononderwerp.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoononderwerpMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(telefoononderwerp)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Telefoononderwerp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTelefoononderwerp() throws Exception {
        // Initialize the database
        telefoononderwerpRepository.saveAndFlush(telefoononderwerp);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the telefoononderwerp
        restTelefoononderwerpMockMvc
            .perform(delete(ENTITY_API_URL_ID, telefoononderwerp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return telefoononderwerpRepository.count();
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

    protected Telefoononderwerp getPersistedTelefoononderwerp(Telefoononderwerp telefoononderwerp) {
        return telefoononderwerpRepository.findById(telefoononderwerp.getId()).orElseThrow();
    }

    protected void assertPersistedTelefoononderwerpToMatchAllProperties(Telefoononderwerp expectedTelefoononderwerp) {
        assertTelefoononderwerpAllPropertiesEquals(expectedTelefoononderwerp, getPersistedTelefoononderwerp(expectedTelefoononderwerp));
    }

    protected void assertPersistedTelefoononderwerpToMatchUpdatableProperties(Telefoononderwerp expectedTelefoononderwerp) {
        assertTelefoononderwerpAllUpdatablePropertiesEquals(
            expectedTelefoononderwerp,
            getPersistedTelefoononderwerp(expectedTelefoononderwerp)
        );
    }
}
