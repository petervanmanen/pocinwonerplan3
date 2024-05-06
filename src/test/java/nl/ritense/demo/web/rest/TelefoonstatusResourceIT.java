package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TelefoonstatusAsserts.*;
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
import nl.ritense.demo.domain.Telefoonstatus;
import nl.ritense.demo.repository.TelefoonstatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TelefoonstatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TelefoonstatusResourceIT {

    private static final String DEFAULT_CONTACTCONNECTIONSTATE = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTCONNECTIONSTATE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/telefoonstatuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TelefoonstatusRepository telefoonstatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTelefoonstatusMockMvc;

    private Telefoonstatus telefoonstatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefoonstatus createEntity(EntityManager em) {
        Telefoonstatus telefoonstatus = new Telefoonstatus().contactconnectionstate(DEFAULT_CONTACTCONNECTIONSTATE).status(DEFAULT_STATUS);
        return telefoonstatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefoonstatus createUpdatedEntity(EntityManager em) {
        Telefoonstatus telefoonstatus = new Telefoonstatus().contactconnectionstate(UPDATED_CONTACTCONNECTIONSTATE).status(UPDATED_STATUS);
        return telefoonstatus;
    }

    @BeforeEach
    public void initTest() {
        telefoonstatus = createEntity(em);
    }

    @Test
    @Transactional
    void createTelefoonstatus() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Telefoonstatus
        var returnedTelefoonstatus = om.readValue(
            restTelefoonstatusMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telefoonstatus)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Telefoonstatus.class
        );

        // Validate the Telefoonstatus in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTelefoonstatusUpdatableFieldsEquals(returnedTelefoonstatus, getPersistedTelefoonstatus(returnedTelefoonstatus));
    }

    @Test
    @Transactional
    void createTelefoonstatusWithExistingId() throws Exception {
        // Create the Telefoonstatus with an existing ID
        telefoonstatus.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelefoonstatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telefoonstatus)))
            .andExpect(status().isBadRequest());

        // Validate the Telefoonstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTelefoonstatuses() throws Exception {
        // Initialize the database
        telefoonstatusRepository.saveAndFlush(telefoonstatus);

        // Get all the telefoonstatusList
        restTelefoonstatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telefoonstatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactconnectionstate").value(hasItem(DEFAULT_CONTACTCONNECTIONSTATE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getTelefoonstatus() throws Exception {
        // Initialize the database
        telefoonstatusRepository.saveAndFlush(telefoonstatus);

        // Get the telefoonstatus
        restTelefoonstatusMockMvc
            .perform(get(ENTITY_API_URL_ID, telefoonstatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(telefoonstatus.getId().intValue()))
            .andExpect(jsonPath("$.contactconnectionstate").value(DEFAULT_CONTACTCONNECTIONSTATE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingTelefoonstatus() throws Exception {
        // Get the telefoonstatus
        restTelefoonstatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTelefoonstatus() throws Exception {
        // Initialize the database
        telefoonstatusRepository.saveAndFlush(telefoonstatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the telefoonstatus
        Telefoonstatus updatedTelefoonstatus = telefoonstatusRepository.findById(telefoonstatus.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTelefoonstatus are not directly saved in db
        em.detach(updatedTelefoonstatus);
        updatedTelefoonstatus.contactconnectionstate(UPDATED_CONTACTCONNECTIONSTATE).status(UPDATED_STATUS);

        restTelefoonstatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTelefoonstatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTelefoonstatus))
            )
            .andExpect(status().isOk());

        // Validate the Telefoonstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTelefoonstatusToMatchAllProperties(updatedTelefoonstatus);
    }

    @Test
    @Transactional
    void putNonExistingTelefoonstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoonstatus.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelefoonstatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telefoonstatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(telefoonstatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefoonstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTelefoonstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoonstatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoonstatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(telefoonstatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefoonstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTelefoonstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoonstatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoonstatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telefoonstatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Telefoonstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTelefoonstatusWithPatch() throws Exception {
        // Initialize the database
        telefoonstatusRepository.saveAndFlush(telefoonstatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the telefoonstatus using partial update
        Telefoonstatus partialUpdatedTelefoonstatus = new Telefoonstatus();
        partialUpdatedTelefoonstatus.setId(telefoonstatus.getId());

        restTelefoonstatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelefoonstatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTelefoonstatus))
            )
            .andExpect(status().isOk());

        // Validate the Telefoonstatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTelefoonstatusUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTelefoonstatus, telefoonstatus),
            getPersistedTelefoonstatus(telefoonstatus)
        );
    }

    @Test
    @Transactional
    void fullUpdateTelefoonstatusWithPatch() throws Exception {
        // Initialize the database
        telefoonstatusRepository.saveAndFlush(telefoonstatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the telefoonstatus using partial update
        Telefoonstatus partialUpdatedTelefoonstatus = new Telefoonstatus();
        partialUpdatedTelefoonstatus.setId(telefoonstatus.getId());

        partialUpdatedTelefoonstatus.contactconnectionstate(UPDATED_CONTACTCONNECTIONSTATE).status(UPDATED_STATUS);

        restTelefoonstatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelefoonstatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTelefoonstatus))
            )
            .andExpect(status().isOk());

        // Validate the Telefoonstatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTelefoonstatusUpdatableFieldsEquals(partialUpdatedTelefoonstatus, getPersistedTelefoonstatus(partialUpdatedTelefoonstatus));
    }

    @Test
    @Transactional
    void patchNonExistingTelefoonstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoonstatus.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelefoonstatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, telefoonstatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(telefoonstatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefoonstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTelefoonstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoonstatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoonstatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(telefoonstatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefoonstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTelefoonstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telefoonstatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoonstatusMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(telefoonstatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Telefoonstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTelefoonstatus() throws Exception {
        // Initialize the database
        telefoonstatusRepository.saveAndFlush(telefoonstatus);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the telefoonstatus
        restTelefoonstatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, telefoonstatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return telefoonstatusRepository.count();
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

    protected Telefoonstatus getPersistedTelefoonstatus(Telefoonstatus telefoonstatus) {
        return telefoonstatusRepository.findById(telefoonstatus.getId()).orElseThrow();
    }

    protected void assertPersistedTelefoonstatusToMatchAllProperties(Telefoonstatus expectedTelefoonstatus) {
        assertTelefoonstatusAllPropertiesEquals(expectedTelefoonstatus, getPersistedTelefoonstatus(expectedTelefoonstatus));
    }

    protected void assertPersistedTelefoonstatusToMatchUpdatableProperties(Telefoonstatus expectedTelefoonstatus) {
        assertTelefoonstatusAllUpdatablePropertiesEquals(expectedTelefoonstatus, getPersistedTelefoonstatus(expectedTelefoonstatus));
    }
}
